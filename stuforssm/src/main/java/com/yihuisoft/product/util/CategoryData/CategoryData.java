package com.yihuisoft.product.util.CategoryData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.yihuisoft.product.schedule.product_category.service.dbutil.DBUtil;

/**
 * 专门针对取Category_Data表的公用方法
 * @author X&Q
 *
 */
public class CategoryData {

	
	/**
	 * 通过标的code和对应的开始结束时间得到数据list，如果没有返回null
	 * @param code
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List<Map<String,Object>> getAllRequiredCategoryData(Integer bidCode,String startTime,String endTime){
		Connection conn=DBUtil.getConnection();
		String sql="select data,day from category_data where code=? and day > to_date(?,'yyyy-mm-dd') and day<to_date(?,'yyyy-mm-dd')";
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, bidCode);
			pst.setString(2, startTime);
			pst.setString(3, endTime);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("data",rs.getDouble(1));
				map.put("date", rs.getDate(2));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			DBUtil.closeConnection(conn);
		}
	   return list;
	}
	
}
