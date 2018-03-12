package com.yihuisoft.product.backtest.service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihuisoft.product.backtest.entity.GroupInfo;
import com.yihuisoft.product.backtest.entity.GroupMetrics;
import com.yihuisoft.product.backtest.mapper.BackTestMapper;
import com.yihuisoft.product.group.entity.ProductGroupDetails;
import com.yihuisoft.product.group.service.ProductGroupTimeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class BackTestService {

    @Autowired
    private BackTestMapper backTestMapper;

    private static Logger logger= LoggerFactory.getLogger(BackTestService.class);
    private static String pyexe;
    private static String pypmsrc;
    private static String errorInfo="Failed to call python";

    /*
     * @Author: yongquan.xiong
     * @Date: 2018-02-28
     * @Desc: 根据产品类型查询产品代码
     * @Param: productType 产品代码
     *         productCode 产品类别
     * */
    public List<String> findProductCode(String productType){
        List<String> codeList=new ArrayList<>();
        String tableName="";
        if("finance".equals(productType)){      //理财
            tableName="FINANCE_BASIC_DATA";
        }else if("fund".equals(productType)){   //基金
            tableName="FUND_BASIC_DATA";
        }else if("pm".equals(productType)){     //贵金属
            tableName="PM_BASIC_DATA";
        }else if("deposit".equals(productType)){    //存单
            tableName="DEPOSIT_BASIC_DATA";
        }
        if(!"".equals(tableName)){
            codeList=backTestMapper.findProductCode(tableName);
        }

        return codeList;
    }

    /*
     * @Author: yongquan.xiong
     * @Date: 2018-02-28
     * @Desc: 查询组合基本数据
     * @Param: groupCode 组合代码（Id）
     *         groupType 组合类别
     * */
    public List<GroupInfo> selectGroupBasicInfo(String groupCode,String groupType){
        List<GroupInfo> listGroupInfo =new ArrayList<>();
        if("mvo".equals(groupType)){
            listGroupInfo=backTestMapper.selectGroupBasicInfoForMvo(groupCode);
        }else if("financial".equals(groupType)){
            listGroupInfo=backTestMapper.selectGroupBasicInfoForFinance(groupCode);
        }else if("asset".equals(groupType)){
            listGroupInfo=backTestMapper.selectGroupBasicInfoForAsset(groupCode);
        }


        return listGroupInfo;
    }


    /*
     * @Author: yongquan.xiong
     * @Date: 2018-03-02
     * @Desc: 自定义回测组合数据入库
     * @Param: name 组合名称
     * @Param：groupType 组合类型
     * @Param: pgd 组合信息
     * */
    public String insertBacktestProductGroup(String name,String groupType,ProductGroupDetails pgd){

       //组合基本数据入库
        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("groupName",name);
        map.put("createTime",time);
        map.put("groupType",groupType);
        backTestMapper.insertBacktestProductGroup(map);
        //组合详细数据入库
        String productGroupId = ((Integer)(Integer.parseInt(map.get("id").toString())+1)).toString();    //组合Id
        String[] codeArray = pgd.getProduct_code().split(",");      //产品代码
        String[] proportionArray = pgd.getProportion().split(",");      //产品权重
        String[] productTypeArray = pgd.getProduct_type().split(",");      //产品类型
        List<GroupInfo> list = new ArrayList<GroupInfo>();
        for (int i = 0 ;i < codeArray.length;i++){

            GroupInfo groupInfo = new GroupInfo();
            groupInfo.setProductCode(codeArray[i].trim());     //产品代码
            groupInfo.setWeights(Double.parseDouble(proportionArray[i]));   //产品权重
            groupInfo.setProductType(productTypeArray[i]);       //产品类型
            groupInfo.setCreateDate(time);  //创建时间

            list.add(groupInfo);
        }
        backTestMapper.insertProductGroupDetails(productGroupId,list);


       return productGroupId;
    }


    /*
     * @Author: yongquan.xiong
     * @Date: 2018-02-28
     * @Desc: 查询组合基本数据
     * @Param: groupCode 组合代码（Id）
     * */
    public GroupMetrics calculateBackTestData(String params,String benchmark,String dateType) {

        //返回指标数据model
        GroupMetrics groupMetrics=new GroupMetrics();
        //读取python配置文件
        Properties prop=new Properties();
        InputStream in = ProductGroupTimeService.class.getClassLoader().getResourceAsStream("python.properties");
        try {
            prop.load(in);
            pyexe=prop.getProperty("PythonExePath").trim();
            pypmsrc=prop.getProperty("PythonBacktestPath").trim();
            logger.info("Python路径:"+pyexe);
        } catch (IOException e) {
            logger.error("获取python配置文件出错:"+e.getMessage());
        }
        String exe_param=pypmsrc+"\\"+"BacktestGroupData"+".py";
        logger.info("脚本文件路径:"+exe_param);
        ProcessBuilder pb=null;

//        params="71,2015-01-01,2017-12-31,10000,rebalance_semi-annually";
        pb=new ProcessBuilder(pyexe,exe_param,params);
        pb.directory(new File(pypmsrc));
        Process proc=null;
        try {
            proc=pb.start();
            //错误信息
            String error = readProcessErrorput(proc);
            logger.info(error);
            //正常输出内容

//            InputStreamReader ir = new InputStreamReader(proc.getInputStream());
//            LineNumberReader input = new LineNumberReader(ir);
//            String result = "";
//            result = input.readLine();
//            String[] RiskAndYield = result.split(",");


            String res = readProcessOutput(proc);
            logger.info(res);
            //解析指标
            if(res==null || res.length()==0 || "Failed to call python".equals(res)){
                groupMetrics.setErrorInfo(errorInfo);
                return groupMetrics;
            }
            JSONObject jsonObj = JSONObject.fromObject(res);
            String bestYear=jsonObj.get("BestYear").toString();
            groupMetrics.setBestYear(bestYear);

            String cagr=jsonObj.get("CAGR").toString();
            groupMetrics.setCagr(cagr);

            String marketCorrelation=jsonObj.get("MarketCorrelation").toString();
            groupMetrics.setMarketCorrelation(marketCorrelation);

            String maxDrawdown=jsonObj.get("MaxDrawdown").toString();
            groupMetrics.setMaxDrawdown(maxDrawdown);

            String sharpeRatio=jsonObj.get("SharpeRatio").toString();
            groupMetrics.setSharpeRatio(sharpeRatio);

            String sortinoRatio=jsonObj.get("SortinoRatio").toString();
            groupMetrics.setSortinoRatio(sortinoRatio);

            String stdev=jsonObj.get("Stdev").toString();
            groupMetrics.setStdev(stdev);

            String worstYear=jsonObj.get("WorstYear").toString();
            groupMetrics.setWorstYear(worstYear);

            String alphaAnnualized=jsonObj.get("Alpha(annualized)").toString();
            groupMetrics.setAlphaAnnualized(alphaAnnualized);

            String beta=jsonObj.get("Beta").toString();
            groupMetrics.setBeta(beta);

            String compoundReturnAnnualized=jsonObj.get("CompoundReturn(annualized)").toString();
            groupMetrics.setCompoundReturnAnnualized(compoundReturnAnnualized);

            String compoundReturnMonthly=jsonObj.get("CompoundReturn(monthly)").toString();
            groupMetrics.setCompoundReturnMonthly(compoundReturnMonthly);

            String conditionalValue=jsonObj.get("ConditionalValue").toString();
            groupMetrics.setConditionalValue(conditionalValue);

            String deltaNormalValue=jsonObj.get("DeltaNormalValue").toString();
            groupMetrics.setDeltaNormalValue(deltaNormalValue);

            String downsideDeviationMonthly=jsonObj.get("DownsideDeviation(monthly)").toString();
            groupMetrics.setDownsideDeviationMonthly(downsideDeviationMonthly);

            String excessKurtosis=jsonObj.get("ExcessKurtosis").toString();
            groupMetrics.setExcessKurtosis(excessKurtosis);

            String financialBalance=jsonObj.get("Financial_Balance").toString();
            groupMetrics.setFinancialBalance(financialBalance);

            String gainLossRatio=jsonObj.get("Gain_LossRatio").toString();
            groupMetrics.setGainLossRatio(gainLossRatio);

            String historicalValue=jsonObj.get("HistoricalValue").toString();
            groupMetrics.setHistoricalValue(historicalValue);

            String meanReturnAnnualized=jsonObj.get("MeanReturn(annualized)").toString();
            groupMetrics.setMeanReturnAnnualized(meanReturnAnnualized);

            String meanReturnMonthly=jsonObj.get("MeanReturn(monthly)").toString();
            groupMetrics.setMeanReturnMonthly(meanReturnMonthly);

            String positivePeriods=jsonObj.get("PositivePeriods").toString();
            groupMetrics.setPositivePeriods(positivePeriods);

            String R2=jsonObj.get("R2").toString();
            groupMetrics.setR2(R2);

            String skewness=jsonObj.get("Skewness").toString();
            groupMetrics.setSkewness(skewness);

            String treynorRatio=jsonObj.get("TreynorRatio").toString();
            groupMetrics.setTreynorRatio(treynorRatio);

            String volatilityMonthly=jsonObj.get("Volatility(monthly)").toString();
            groupMetrics.setVolatilityMonthly(volatilityMonthly);

            String volatilityYearly=((Double)(Double.parseDouble(jsonObj.get("Volatility(monthly)").toString())*Math.sqrt(12))).toString(); //年化波动率
            groupMetrics.setVolatilityYearly(volatilityYearly);

            String diversificationRatio=jsonObj.get("diversification_ratio").toString();
            groupMetrics.setDiversificationRatio(diversificationRatio);
            //-------------------------------- 以上 为 标量-----------------------------------

            //组合中产品代码
            String groupCode=jsonObj.get("group_code").toString();
            if(groupCode!=null && groupCode.length()>0){
                String[] groupCodeArr=groupCode.split(",");
                groupMetrics.setCodeList(groupCodeArr);
                logger.info("groupCodeArr:"+groupCodeArr);
            }

            //组合中产品名称
            String codeName=jsonObj.get("code_name").toString();
            if(codeName!=null && codeName.length()>0){
                String[] codeNameArr=codeName.split(",");
                groupMetrics.setCodeNameList(codeNameArr);
                logger.info("codeNameArr:"+codeNameArr);
            }

            //组合数据   查询 PORTFOLIO_CALDATA 表 获取 资产金额、收益、涨幅、回撤等数据
            List<GroupInfo> portfolioInfoList=backTestMapper.selectPortfolioInfo();
            if(portfolioInfoList !=null && portfolioInfoList.size()>0){
                String[] dateArr=new String[portfolioInfoList.size()];  //日期
                String[] moneyArr=new String[portfolioInfoList.size()]; //金额
                String[] retArr=new String[portfolioInfoList.size()];   //收益率
                String[] growthArr=new String[portfolioInfoList.size()];    //涨幅
                String[] drawDownsArr=new String[portfolioInfoList.size()]; //回撤

                for(int i=0;i<portfolioInfoList.size();i++){

                    dateArr[i]=portfolioInfoList.get(i).getCreateDate();
                    moneyArr[i]=portfolioInfoList.get(i).getMoneySum();
                    retArr[i]=portfolioInfoList.get(i).getYieldRatio().toString();
                    growthArr[i]=portfolioInfoList.get(i).getGrowth();
                    drawDownsArr[i]=portfolioInfoList.get(i).getDrawDowns();

                }

                groupMetrics.setMonthEndMoneyDate(dateArr);
                logger.info("monthEndMoneyDateArr:"+dateArr);

                groupMetrics.setMonthEndMoneyValueArr(moneyArr);
                logger.info("monthEndMoneyValueArr:"+moneyArr);

                groupMetrics.setMonthRetValueArr(retArr);
                logger.info("monthRetValueArr:"+retArr);

                groupMetrics.setDrawdownsArr(drawDownsArr);
                logger.info("drawDownsArr:"+drawDownsArr);

                groupMetrics.setMonthMoneyGrowth(growthArr);
                logger.info("growthArr:"+growthArr);

            }





            //月度资产数据
//            if("month".equals(dateType)){
//
//                JSONObject monthEndMoney=(JSONObject)jsonObj.get("month_end_money");
//                if(monthEndMoney !=null ){
//                    String monthEndMoneyDate=monthEndMoney.get("month_end_money_date").toString();
//                    String monthEndMoneyValue=monthEndMoney.get("month_end_money_value").toString();
//                    if(monthEndMoneyDate!=null && monthEndMoneyDate.length()>0){
//                        String[] monthEndMoneyDateArr=monthEndMoneyDate.split(",");
//                        groupMetrics.setMonthEndMoneyDate(monthEndMoneyDateArr);
//                        logger.info("monthEndMoneyDateArr:"+monthEndMoneyDateArr);
//                    }
//
//
//                    if(monthEndMoneyValue!=null && monthEndMoneyValue.length()>0) {
//                        String[] monthEndMoneyValueArr = monthEndMoneyValue.split(",");
//                        groupMetrics.setMonthEndMoneyValueArr(monthEndMoneyValueArr);
//                        logger.info("monthEndMoneyValueArr:"+monthEndMoneyValueArr);
//                    }
//
//                }
//
//                //月度收益率
//                JSONObject monthRet=(JSONObject)jsonObj.get("month_ret");
//                String monthRetDateArr[];
//                String monthRetValueArr[];
//                if(monthRet !=null ){
//                    String monthRetDate=monthRet.get("month_ret_date").toString();
//                    String monthRetValue=monthRet.get("month_ret_value").toString();
//
//                    if(monthRetDate!=null && monthRetDate.length()>0){
//                        monthRetDateArr=monthRetDate.split(",");
//
//                        logger.info("monthRetDateArr:"+monthRetDateArr);
//                    }
//
//                    if(monthRetValue!=null && monthRetValue.length()>0) {
//                        monthRetValueArr = monthRetValue.split(",");
//                        groupMetrics.setMonthRetValueArr(monthRetValueArr);
//
//                        logger.info("monthRetValueArr:"+monthRetValueArr);
//                    }
//
//                }
//
//                //最大回撤序列数据
//                String drawdowns=jsonObj.get("drawdowns").toString();
//                String drawdownsArr[];
//                if(drawdowns!=null && drawdowns.length()>0){
//                    drawdownsArr=drawdowns.split(",");
//                    groupMetrics.setDrawdownsArr(drawdownsArr);
//
//                    logger.info("drawdownsArr:"+drawdownsArr);
//                }
//
//                //组合资金涨幅数据
//                String monthMoneyGrowth=jsonObj.get("month_money_growth").toString();
//                String[] monthMoneyGrowthArr={};
//                if(monthMoneyGrowth != null){
//                    monthMoneyGrowthArr=monthMoneyGrowth.split(",");
//                }
//                groupMetrics.setMonthMoneyGrowth(monthMoneyGrowthArr);
//
//            }else{
//                //每天资产数据
//
//                //日期
//                String portfolioDayDate=jsonObj.get("portfolio_day_date").toString();
//                String[] portfolioDayDateArr={};
//                if(portfolioDayDate !=null ){
//                    portfolioDayDateArr=portfolioDayDate.split(",");
//                    groupMetrics.setMonthEndMoneyDate(portfolioDayDateArr);
//                }
//
//                //金额
//                String portfolioDayMoney=jsonObj.get("portfolio_day_money").toString();
//                String[] portfolioDayMoneyArr={};
//                if(portfolioDayMoney !=null ){
//                    portfolioDayMoneyArr=portfolioDayMoney.split(",");
//                    groupMetrics.setMonthEndMoneyValueArr(portfolioDayMoneyArr);
//                }
//
//                //收益率
//                String portfolioDayRet=jsonObj.get("portfolio_day_ret").toString();
//                String[] portfolioDayRetArr={};
//                if(portfolioDayRet !=null ){
//                    portfolioDayRetArr=portfolioDayRet.split(",");
//                    groupMetrics.setMonthRetValueArr(portfolioDayRetArr);
//                }
//
//                //回撤
//                String dayDrawdowns=jsonObj.get("portfolio_day_drawdowns").toString();
//                String[] dayDrawdownsArr={};
//                if(dayDrawdowns !=null ){
//                    dayDrawdownsArr=dayDrawdowns.split(",");
//                    groupMetrics.setDrawdownsArr(dayDrawdownsArr);
//                }
//
//                //涨幅
//                String dayMoneyGrowth=jsonObj.get("day_money_growth").toString();
//                String[] dayMoneyGrowthArr={};
//                if(dayMoneyGrowth !=null ){
//                    dayMoneyGrowthArr=dayMoneyGrowth.split(",");
//                    groupMetrics.setMonthMoneyGrowth(dayMoneyGrowthArr);
//                }
//            }

            //相关系数矩阵
//            JSONArray corr=(JSONArray )jsonObj.get("corr");
//            List<Object[]> corrList=new ArrayList();
//            if(corr.size()>0){
//                for(int i=0;i<corr.size();i++){
//                    corrList.add(corr.getJSONArray(i).toArray());
//                }
//            }
//            groupMetrics.setCorrList(corrList);
            //表格数据
            JSONObject assetIndex=(JSONObject)jsonObj.get("asset_index");
            String assetIndexTitleArr[];
            List<String[]> assetIndexValuesList=new ArrayList<>();
            String assetIndexValuesArr[];
            if(assetIndex !=null ){
                String assetIndexTitle=assetIndex.get("title").toString();
                String assetIndexValues=assetIndex.get("values").toString();
                //表格1标题
                if(assetIndexTitle!=null && assetIndexTitle.length()>0){
                    assetIndexTitleArr=assetIndexTitle.split(",");
                    groupMetrics.setAssetIndexTitleArr(assetIndexTitleArr);
                    logger.info("assetIndexTitleArr:"+assetIndexTitleArr);
                }
                //表格1数据
                if(assetIndexValues!=null && assetIndexValues.length()>0) {
                    assetIndexValuesArr = assetIndexValues.split("#,");

                    for(int j=0;j<assetIndexValuesArr.length;j++){
                        String[] tempAssetIndexValuesArr={};
                        if(j==assetIndexValuesArr.length-1){
                            tempAssetIndexValuesArr=assetIndexValuesArr[j].substring(0,assetIndexValuesArr[j].length()-1).split(",");
                        }else {
                            tempAssetIndexValuesArr=assetIndexValuesArr[j].split(",");
                        }
                        assetIndexValuesList.add(tempAssetIndexValuesArr);
                    }
                    groupMetrics.setAssetIndexValuesList(assetIndexValuesList);
                    logger.info("assetIndexValuesList:"+assetIndexValuesList);
                }
            }

            //资产数据
//            JSONObject asset=(JSONObject)jsonObj.get("asset");
//            String assetTitleArr[];
//            String assetValuesArr[];
//            List<String[]>  assetValuesList=new ArrayList<>();
//            if(asset !=null ) {
//                String assetTitle = asset.get("title").toString();
//                String assetValues = asset.get("values").toString();
//                //表格2标题
//                if (assetTitle != null && assetTitle.length() > 0) {
//                    assetTitleArr = assetTitle.split(",");
//                    groupMetrics.setAssetTitleArr(assetTitleArr);
//                    logger.info("assetTitleArr:" + assetTitleArr);
//                }
//                //表格2数据
//                if (assetValues != null && assetValues.length() > 0) {
//                    assetValuesArr = assetValues.split("#,");
//                    for (int j = 0; j < assetValuesArr.length; j++) {
//                        String[] tempAssetValuesArr = {};
//                        if (j == assetValuesArr.length - 1) {
//                            tempAssetValuesArr = assetValuesArr[j].substring(0, assetValuesArr[j].length() - 1).split(",");
//                        } else {
//                            tempAssetValuesArr = assetValuesArr[j].split(",");
//                        }
//                        assetValuesList.add(tempAssetValuesArr);
//                    }
//                    groupMetrics.setAssetValuesList(assetValuesList);
//                    logger.info("assetIndexValuesList:" + assetValuesList);
//                }
//            }
                //基准数据   查询 BENCHMARK_CALDATA 表 获取 资产金额、收益、涨幅、回撤等数据

            String[] dateArr={};  //日期
            String[] moneyArr={}; //金额
            String[] retArr={};   //收益率
            String[] growthArr={};    //涨幅
            String[] drawDownsArr={}; //回撤
                if(!"null".equals(benchmark)){
                    List<GroupInfo> benchmarkInfoList=backTestMapper.selectBenchmarkInfo();
                    if(benchmarkInfoList !=null && benchmarkInfoList.size()>0){
                        dateArr=new String[benchmarkInfoList.size()];  //日期
                        moneyArr=new String[benchmarkInfoList.size()]; //金额
                        retArr=new String[benchmarkInfoList.size()];   //收益率
                        growthArr=new String[benchmarkInfoList.size()];    //涨幅
                        drawDownsArr=new String[benchmarkInfoList.size()]; //回撤

                        for(int i=0;i<benchmarkInfoList.size();i++){

                            dateArr[i]=benchmarkInfoList.get(i).getCreateDate();
                            moneyArr[i]=benchmarkInfoList.get(i).getMoneySum();
                            retArr[i]=benchmarkInfoList.get(i).getYieldRatio().toString();
                            growthArr[i]=benchmarkInfoList.get(i).getGrowth();
                            drawDownsArr[i]=benchmarkInfoList.get(i).getDrawDowns();

                        }
                    }
                }
            groupMetrics.setMonthEndBenchmarkMoneyValueArr(moneyArr);
            logger.info("setMonthEndBenchmarkMoneyValueArr:"+moneyArr);

            groupMetrics.setMonthRetBenchmarkRetValueArr(retArr);
            logger.info("setMonthRetBenchmarkRetValueArr:"+retArr);

            groupMetrics.setBenchmarkMoneyGrowth(growthArr);
            logger.info("drawdownsArr:"+growthArr);

            groupMetrics.setMonthBenchmarkDrawdownArr(drawDownsArr);
            logger.info("drawdownsArr:"+drawDownsArr);



                //基准money数据
//                String benchmarkMoney=jsonObj.get("benchmark_money").toString();
//                String[] BenchmarkMoneyArr={};
//                if(benchmarkMoney !=null ){
//                    BenchmarkMoneyArr=benchmarkMoney.split(",");
//                }
//
//                groupMetrics.setMonthEndBenchmarkMoneyValueArr(BenchmarkMoneyArr);
//
//                //基准收益率数据
//                String benchmarkYield=jsonObj.get("benchmark_yield").toString();
//                String[] benchmarkYieldArr={};
//                if(benchmarkYield != null){
//                    benchmarkYieldArr=benchmarkYield.split(",");
//                }
//                groupMetrics.setMonthRetBenchmarkRetValueArr(benchmarkYieldArr);
//
//                //基准回撤数据
//                String benchmarkDrawdown=jsonObj.get("benchmark_drawdown").toString();
//                String[] benchmarkDrawdownArr={};
//                if(benchmarkDrawdown != null){
//                    benchmarkDrawdownArr=benchmarkDrawdown.split(",");
//                }
//                groupMetrics.setMonthBenchmarkDrawdownArr(benchmarkDrawdownArr);
//
//                //基准资金涨幅数据
//                String benchmarkMoneyGrowth=jsonObj.get("benchmark_money_growth").toString();
//                String[] benchmarkMoneyGrowthArr={};
//                if(benchmarkMoneyGrowth != null){
//                    benchmarkMoneyGrowthArr=benchmarkMoneyGrowth.split(",");
//                }
//                groupMetrics.setBenchmarkMoneyGrowth(benchmarkMoneyGrowthArr);

//            }

            proc.waitFor();

        } catch (IOException e) {
            logger.error("调用Python文件出错:"+e.getMessage());
            return null;
        } catch (InterruptedException e) {
            logger.error("调用Python文件出错:"+e.getMessage());
            return null;
        }
        logger.info("调用Python文件结束");
        return groupMetrics;
    }

    private static String readProcessOutput(final Process process) {
//        return process.getOutputStream().toString();
//        return read(process.getErrorStream(), System.out);
        return read(process.getInputStream(), System.out);
    }

    private static String readProcessErrorput(final Process process) {
//        return process.getOutputStream().toString();
        return read(process.getErrorStream(), System.out);
//        return read(process.getInputStream(), System.out);
    }

    private static String read(InputStream inputStream, PrintStream out) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


}
