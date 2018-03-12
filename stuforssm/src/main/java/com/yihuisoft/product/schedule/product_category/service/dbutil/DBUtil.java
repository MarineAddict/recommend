package com.yihuisoft.product.schedule.product_category.service.dbutil;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {
	// 连接数据库的路径
    public static String url;
    // 连接数据库的用户名
    public static String user;
    // 连接数据库的密码
    public static String pwd;

    public static String driver;
    
    
 // 静态块
    static {
        try {
            // 读取配置文件
            Properties prop = new Properties();
            /*
             * 这种写法是将来更加推荐的相对路径 写法。
             */
            InputStream is = DBUtil.class.getResourceAsStream("/jdbc.properties");
            
            prop.load(is);
            is.close();
            // 获取驱动
            driver = prop.getProperty("jdbc.driverClassName");
            // 获取地址
            url = prop.getProperty("jdbc.url");
            // 获取用户名
            user = prop.getProperty("jdbc.username");
            // 获取密码
            pwd = prop.getProperty("jdbc.password");

            // 注册驱动
            Class.forName(driver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    /**
     * 获取一个连接
     * 
     * @return
     * @throws Exception
     */
    public static Connection getConnection()  {
        try {
            /*
             * 通过DriverManager创建一个数据库的连接 并返回
             */
            Connection conn = DriverManager.getConnection(url, user, pwd);
            /*
             * ThreadLocal的set方法 会将当前线程作为key,并将给定的值 作为value存入内部的map中保存。
             */
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            // 通知调用者，创建连接出错
        }
		return null;
    }


    /**
     * 关闭给定的连接
     */
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    
	
}
