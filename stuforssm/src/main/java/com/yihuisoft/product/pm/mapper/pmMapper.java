package com.yihuisoft.product.pm.mapper;

import java.util.List;
import java.util.Map;

import com.yihuisoft.product.fund.entity.FundTrack;
import com.yihuisoft.product.fund.entity.dao.FundProductDAO;
import com.yihuisoft.product.pm.entity.PmProductDAO;
import com.yihuisoft.product.pm.entity.PmTrack;
import org.apache.ibatis.annotations.Param;

import com.yihuisoft.product.pm.entity.PmBasic;
import com.yihuisoft.product.pm.entity.pmProduct;

/** 
 * @Description:贵金属Mapper
 * 
 * @author 	:lixiaosong
 * @date 	:2018年1月16日 下午5:07:51 
 * @version :V1.0 
 */
public interface pmMapper {
	
	//获取一段时间区间内某个产品的收益率
	List<pmProduct> getYieldRatioLine(Map map);
	
	//查询所有产品代码
	List<String> getProductCodeList();
	
	//查询所有产品
	List<PmBasic> getPmList(Map map);
	
	/**
	 * @author zhaodc
	 * @param map
	 * @return
	 */
	List<PmBasic> getPmBasicList(@Param("map")Map map);


	int getPmBasicListRows(@Param("map")Map map);


	Map getPmDetail(@Param("code") String code);


	List<PmTrack> getPmTrackList(@Param("code") String code);

	List<PmTrack> getPmIncomeTrend(@Param("map") Map<String, Object> map);

	List<PmTrack> getPmNetValue(@Param("map") Map<String, Object> map);

    List<PmTrack> findDataForDrawdown(@Param("map") Map<String, String> map);

	List<PmProductDAO> getPmNetYieldList(@Param("map") Map map);
}
