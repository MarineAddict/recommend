package com.yihuisoft.product.backtest.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihuisoft.product.backtest.entity.GroupInfo;
import com.yihuisoft.product.backtest.entity.GroupMetrics;
import com.yihuisoft.product.backtest.mapper.GenerateGroupMapper;
import com.yihuisoft.product.group.service.ProductGroupTimeService;
@Service
public class GenerateGroupService {

    @Autowired
    private GenerateGroupMapper generateGroupMapper;

    private static Logger logger= LoggerFactory.getLogger(GenerateGroupService.class);
    private static String pyexe;
    private static String pypmsrc;

    /*
     * @Desc:调用MVO 生成组合
     * @Author: yongquan.xiong
     * @Date: 2018-02-11
     * @Param:
     * */
    public  List<GroupInfo> generateGroupByMVO(String params) {

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
        String exe_param=pypmsrc+"\\"+"GenerateGroupByMVO"+".py";
        logger.info("脚本文件路径:"+exe_param);
        ProcessBuilder pb=null;
//        String params1="2015-01-01,2017-12-31,1,0";
        pb=new ProcessBuilder(pyexe,exe_param,params);
        pb.directory(new File(pypmsrc));
        Process proc=null;

        List<GroupInfo> listGroupInfo=new ArrayList<>();
        try {
            proc=pb.start();
//            String res="5";
            String res = readProcessOutput(proc);
            logger.info(res);
            //解析指标
            if(res==null || res.length()==0 || "Failed to call python".equals(res)){
                return listGroupInfo;
            }
            listGroupInfo=selectGroupInfo();

            proc.waitFor();

        } catch (IOException e) {
            logger.error("调用Python文件出错:"+e.getMessage());
            return null;
        } catch (InterruptedException e) {
            logger.error("调用Python文件出错:"+e.getMessage());
            return null;
        }
        logger.info("调用Python文件结束");
        return listGroupInfo;
    }

    //查询组合数据(数据库中最新 5 条记录)
    public  List<GroupInfo> selectGroupInfo(){
        List<GroupInfo> listGroupInfo=new ArrayList<>();
        listGroupInfo=generateGroupMapper.selectGroupInfo();
        //风险等级赋值
        String[] riskLevelArr={"R1（谨慎型）","R1（谨慎型）","R2（稳健型）","R2（稳健型）","R3（平衡型）",
                "R3（平衡型）","R4（进取型）","R4（进取型）","R5（激进型）","R5（激进型）"};
        //组合名称赋值
        String[] groupNameArr={"'御•安守'YH","'御•自若'YH","'稳•泰然'YH","'稳•增华'YH","'衡•若定'YH",
                "'衡•不息'YH","'进•不迫'YH","'进•当先'YH","'博•不惊'YH","'博•精进'YH"};
        if(listGroupInfo!=null && listGroupInfo.size()>0){
            int i=0;
            for(GroupInfo groupInfo:listGroupInfo){
                groupInfo.setRiskLevel(riskLevelArr[i]);
                groupInfo.setGroupName(groupNameArr[i]+groupInfo.getGroupCode());
                i++;
            }
        }
        return listGroupInfo;
    }

    private static String readProcessOutput(final Process process) {
//        return read(process.getErrorStream(), System.out);
        return read(process.getInputStream(), System.out);
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
