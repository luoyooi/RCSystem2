package com.luoyooi.service;

import java.util.ArrayList;
import java.util.Arrays;

import com.luoyooi.dao.MvInfoDAO;
import com.luoyooi.pojo.MvInfoDO;

public class MvInfoService {
	private MvInfoDAO dao = new MvInfoDAO();
	
	public MvInfoDO[] ListByScore(String tableName)
	{
		ArrayList<MvInfoDO> mdoList = dao.getMvList(tableName);
		MvInfoDO[] mdoArr = (MvInfoDO[])mdoList.toArray(new MvInfoDO[mdoList.size()]);
		
		//排序
		Arrays.sort(mdoArr);
		return mdoArr;
	}
}
