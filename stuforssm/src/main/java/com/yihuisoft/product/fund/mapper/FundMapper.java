package com.yihuisoft.product.fund.mapper;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihuisoft.product.fund.entity.FundMonetaryYield;
import com.yihuisoft.product.fund.entity.FundProduct;
import com.yihuisoft.product.fund.entity.FundTrack;
import com.yihuisoft.product.fund.entity.dao.FundBasicDAO;
import com.yihuisoft.product.fund.entity.dao.FundProductDAO;

/**
 * Created by wangyinyuo on 2018/1/18.
 */
public interface FundMapper {
    //获取一段时间区间内某个产品的收益率
    List<FundProductDAO> getFundYieldRisk(Map<String,String> map);
    
    //获取基金产品的基本信息
    List<FundBasicDAO> getFundBasicInfo(@Param("code") String code);
 
    
    /**
     * 基金基本信息查询
     * @author zhaodc 2018-02-01
     * @param map
     * @return
     */
    List<FundBasicDAO> getFundBasicInfoList(@Param("map") Map<String,String> map);
    int getFundBasicInfoListRows(@Param("map") Map<String,String> map);
    
    /**
     * 查询基金基本信息
     * @author zhaodc 2018-03-01
     * @param code
     * @return
     */
    Map getFundDetail(String code);
   
    /**
     * 基金指数跟踪误差
     * @author zhaodc
     * @param fundcode
     * @param bidcode
     * @return
     */
    List<FundTrack> getFundTrackList(@Param("fundcode") String fundcode,@Param("bidcode") String bidcode);


    /*
     * 查询基金以及对应指数数据
     */
    List<FundTrack> findDataForDrawdown(@Param("map") Map<String,String> map);

    /**
     * 基金指数涨幅走势
     * @author zhaodc
     * @param fundcode
     * @param bidcode
     * @return
     */
    List<FundTrack> getFundIncomeTrend(@Param("map") Map<String,Object> map);
    
    /**
     * 基金单位净值走势
     * @author zhaodc
     * @param code
     * @return
     */
    List<FundTrack> getFundNetValue(@Param("map") Map<String,Object> map);
    
    /**
     * 基金收益率图
     * @author zhaodc
     * @param map
     * @return
     */
    List<FundProductDAO> getFundNetYieldList(@Param("map") Map<String,String> map);
    
    /**
     * 货币型基金万份收益、七日年化
     * @author zhaodc
     * @param map
     * @return
     */
    List<FundMonetaryYield> getFundMonetaryYieldList(@Param("map") Map<String,String> map);
    
}
