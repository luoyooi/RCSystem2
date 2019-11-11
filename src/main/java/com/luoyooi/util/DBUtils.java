package com.luoyooi.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

public class DBUtils {
	private static String DRIVER;
	private static String URL;
	private static String USER;
	private static String PASS;
	
	//加载properties文件，初始化基本参数
	static
	{
		Properties pro = new Properties();
		try {
			pro.load(new FileInputStream("D:\\xunlianying\\workspace\\RcSystem2\\src\\main\\resources\\jdbc.properties"));
		} catch (IOException e) {
			System.out.println("properties文件打开有问题");
			e.printStackTrace();
		}
		
		DRIVER = pro.getProperty("DRIVER");
		URL = pro.getProperty("URL");
		USER = pro.getProperty("USER");
		PASS = pro.getProperty("PASS");
		
//		//加载驱动
//		try {
//			Class.forName(DRIVER);
//		} catch (ClassNotFoundException e) {
//			System.out.println("驱动加载有问题");
//			e.printStackTrace();
//		}
	}
	
	public static Connection getConn() throws SQLException {
		/**
		 * 以下是连接池初始化部分
		 */
		//创建数据源对象
		BasicDataSource bs = new BasicDataSource();
		//设置连接池所需要的驱动
		bs.setDriverClassName(DRIVER);
		//设置连接数据库的URL
		bs.setUrl(URL);
		//设置连接池的用户名
		bs.setUsername(USER);
		//设置连接池密码
		bs.setPassword(PASS);
		//设置连接池的初始连接数
		bs.setInitialSize(5);
		//设置连接池最多可有多少个活动连接数
		bs.setMaxActive(20);
		//设置连接池最少有2个空闲的连接
		bs.setMinIdle(2);
		
		/**
		 * 返回一个Connecter对象
		 */
		return bs.getConnection();
	}
}
