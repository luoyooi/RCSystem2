package com.luoyooi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.luoyooi.pojo.CommDO;
import com.luoyooi.util.DBUtils;

public class CommDAO {
	public ArrayList<CommDO> getCommList(String mvName)
	{
		ArrayList<CommDO> commList = new ArrayList<>();
		try(Connection conn = DBUtils.getConn())
		{
			Statement stmt = conn.createStatement();
			String sql = "SELECT mv.评论日期, mv.评论 FROM `"+mvName+"` mv;";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				CommDO CDO = new CommDO();
				CDO.setCommDate(rs.getString(1));
				CDO.setComment(rs.getString(2));
				commList.add(CDO);
			}
			
		}catch (Exception e) {
			System.out.println("数据库查询失败");
		}
		return commList;
	}
}
