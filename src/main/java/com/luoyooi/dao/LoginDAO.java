package com.luoyooi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.luoyooi.pojo.UserDO;
import com.luoyooi.util.DBUtils;

public class LoginDAO {
	public UserDO getUser(String username, String password)
	{
		String sql = "select * from acount where username= ? and password = ?";
		UserDO user = null;
		
		try( Connection conn = DBUtils.getConn(); )
		{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				user = new UserDO();
				user.setUsername(rs.getString(1));
				user.setPassword(rs.getString(2));
			}
			
		} catch (Exception e) {
			
		}
		return user;
	}
	
	public boolean setAcount(String username, String password, String email)
	{
		boolean judge = false;
		String sql = "INSERT INTO acount(username,password,email) VALUES(?,?,?)";
		
		try (Connection conn = DBUtils.getConn()){
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, email);
			
			pstmt.execute();
			
			judge  = true;
			
		} catch (Exception e) {
			System.out.println("注册错误");
		}
		
		return judge;
	}
}
