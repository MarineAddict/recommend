package com.yihuisoft.product.schedule.product_category.service.dbutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yihuisoft.product.schedule.product_category.entity.ProductCategoryData;
import com.yihuisoft.product.util.ProductType;

public class FundClassificationDBUtil {
    
	
	private static Logger logger= LoggerFactory.getLogger(FundClassificationDBUtil.class);
	private static Connection conn;
	public static final String INSERTCATEGORYSTATEMENT="INSERT INTO PRODUCT_CATEGORY VALUES (?,?,?,?,?)";
	public static final String DELETECATEGORYSTATEMENT="DELETE FROM PRODUCT_CATEGORY WhERE PRDTYPE="+ProductType.基金产品.getIndex();
    static{
    	conn=DBUtil.getConnection();
    }
	
	/**
	 * 批量存入基金分类（小类）表
	 * @param categoryDataList
	 */
	public static void insertBatches(List<ProductCategoryData> categoryDataList ){
		
		try {
		     PreparedStatement  statement = conn.prepareStatement(INSERTCATEGORYSTATEMENT);
		 for(ProductCategoryData data:categoryDataList){
			 statement.setString(1,data.getSystemCode());
			 statement.setInt(2, data.getCategorySmall());
			 statement.setString(3, data.getPrdCode());
			 statement.setInt(4, data.getPrdType());
			 statement.setInt(5, 10); //评分，默认10.。。。需要修改
			 statement.addBatch();
		 }
		 statement.executeBatch();
		 statement.clearBatch();
		}
		catch(Exception e){
			e.printStackTrace();
			DBUtil.closeConnection(conn);
		}	
	}
	
	
	public static void deleteFundCategory(){
		
		//清空数据库
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(DELETECATEGORYSTATEMENT);
			Integer num=statement.executeUpdate();
			if(num==0){
				logger.error("删除PRODUCT_CATEGORY时删除数据为0，可能发生了错误");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DBUtil.closeConnection(conn);
		}
		 
	}
	
	
}
