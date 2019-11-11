package com.luoyooi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.luoyooi.pojo.MvInfoDO;
import com.luoyooi.util.DBUtils;
import com.luoyooi.util.PYListUtils;

public class MvInfoDAO {
//	获取所有电影信息，返回一个list
	public ArrayList<MvInfoDO> getMvList(String tableName) {
		// 用来装封装的电影信息对象
		ArrayList<MvInfoDO> arr = new ArrayList<>();
		// 从连接池获取Connection对象
		try (Connection conn = DBUtils.getConn()) {
			String sql = "SELECT * FROM " + tableName;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				//如果评分不为0
				if (rs.getDouble(11) != 0) {
					MvInfoDO mdo = new MvInfoDO();
					mdo.setName(rs.getString(1));
					mdo.setDirectors(PYListUtils.Parse(rs.getString(2)));
					mdo.setScreenwriters(PYListUtils.Parse(rs.getString(3)));
					mdo.setTostars(PYListUtils.Parse(rs.getString(4)));
					mdo.setTypes(PYListUtils.Parse(rs.getString(5)));
					mdo.setCountry(PYListUtils.Parse(rs.getString(6)).get(0));
					mdo.setLanguage(PYListUtils.Parse(rs.getString(7)).get(0));
					mdo.setRedate(rs.getString(8));
					mdo.setMvLong(rs.getString(9));
					mdo.setOthername(PYListUtils.Parse(rs.getString(10)).get(0));
					mdo.setScore(rs.getDouble(11));
					mdo.setCommPeopleNum(rs.getLong(12));
					// 初始化等级list
					mdo.setStars(new ArrayList<String>());
					mdo.getStars().add(rs.getString(13));
					mdo.getStars().add(rs.getString(14));
					mdo.getStars().add(rs.getString(15));
					mdo.getStars().add(rs.getString(16));
					mdo.getStars().add(rs.getString(17));
					mdo.setPosterAdrr(PYListUtils.Parse(rs.getString(18)).get(0));

					// 装进总容器中
					arr.add(mdo);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return arr;
	}
}
