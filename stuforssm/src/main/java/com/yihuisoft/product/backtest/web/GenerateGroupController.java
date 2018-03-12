package com.yihuisoft.product.backtest.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yihuisoft.product.backtest.entity.GroupInfo;
import com.yihuisoft.product.backtest.service.GenerateGroupService;

@Controller
@RequestMapping("/generateGroup/")
public class GenerateGroupController {

    @Autowired
    private GenerateGroupService generateGroupService;


    /*
     * @author yongquan.xiong
     * @Description:通过MVO生成组合数据
     * @param up：权重上限
     *        down：权重下限
     *        startTime：开始时间
     *        endTime：结束时间
     *        bidCodeArr：组合中产品code
     * @return  List<GroupInfo>
     * @date:   2018年02月27日
     */
    @RequestMapping("/generateGroupByMVO")
    @ResponseBody
    public List<GroupInfo> getBacktestBasicData(Double up, Double down, String startTime, String endTime,int[] bidCodeArr){
        //默认初始上下限
        if(up==null || "".equals(up)){
            up=0.9;
        }
        if(down==null || "".equals(down)){
            down=0.05;
        }

        String params=startTime+","+endTime+","+up.toString()+","+down.toString();
        List<GroupInfo> list=generateGroupService.generateGroupByMVO(params);
        //前端模拟测试数据
//        List<GroupInfo> list=new ArrayList<>();
//        GroupInfo groupInfo1=new GroupInfo();
//        groupInfo1.setGroupCode("001");  // 组合id
//        groupInfo1.setYieldRatio(0.034);   //组合收益率
//        groupInfo1.setRiskRatio(0.0017);   //组合风险率
//        groupInfo1.setCreateDate("2018-02-26");  //创建时间
//        groupInfo1.setRiskLevel("保守型");   //风险等级
//
////        List<GroupInfo> list2=new ArrayList<>();
//        GroupInfo groupInfo2=new GroupInfo();
//        groupInfo2.setGroupCode("002");  // 组合id
//        groupInfo2.setYieldRatio(0.064);   //组合收益率
//        groupInfo2.setRiskRatio(0.0047);   //组合风险率
//        groupInfo2.setCreateDate("2018-02-26");  //创建时间
//        groupInfo2.setRiskLevel("平衡型");   //风险等级
//
//        list.add(groupInfo1);
//        list.add(groupInfo2);

        return list;
    }

    /*
    * @author yongquan.xiong
    * @Description:跳转至 MVO 界面
    * @param bidCode：产品code
    * @date:   2018年02月27日
    */
    @RequestMapping(value = "/tomvoModel")
    @ResponseBody
    public ModelAndView tomvoModel(int[] bidCode){
    	ModelAndView mv= new ModelAndView();
    	String bidCodeArr = Arrays.toString(bidCode);
    	mv.addObject("bid", bidCodeArr.substring(1, bidCodeArr.length()-1));
    	mv.setViewName("modelstrategy/mvoModel");
    	return mv;
    }

     /*
     * @author yongquan.xiong
     * @Description:查询MVO组合数据(默认前 5 条记录)
     * @param
     * @return  List<GroupInfo>
     * @date:   2018年02月27日
     */
    @RequestMapping(value = "/selectGroupInfo")
    @ResponseBody
    public List<GroupInfo> selectGroupInfo(){

        List<GroupInfo> list=generateGroupService.selectGroupInfo();

        return list;
    }


}
