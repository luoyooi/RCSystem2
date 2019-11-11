package com.luoyooi.test;

import java.sql.SQLException;

import com.luoyooi.dao.CommDAO;
import com.luoyooi.pojo.MvInfoDO;
import com.luoyooi.service.CommService;
import com.luoyooi.service.MvInfoService;

public class Test {
	public static void main(String[] args) throws SQLException {
//		commDAO cdo = new commDAO();
//		System.out.println(cdo.getCommList("红星照耀中国"));
		CommService cs = new CommService();
		System.out.println(cs.listCOMM("红花绿叶"));
		System.out.println(System.getProperty("java.library.path"));
//		System.out.println(ClassLoader.getSystemClassLoader());
//		MvInfoService s = new MvInfoService();
//		for (MvInfoDO it : s.ListByScore("最新电影信息")) {
//			System.out.println(it);
//		}
	}
}
