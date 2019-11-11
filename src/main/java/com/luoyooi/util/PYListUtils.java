package com.luoyooi.util;

import java.util.ArrayList;

public class PYListUtils {
	public static ArrayList<String> Parse(String list)
	{
		list = list.replace("[", "");
		list = list.replace("]", "");
		list = list.replace("'", "");
		String[] listArr = list.split(",");
		
		ArrayList<String> infos = new ArrayList<>();
		
		for (String str : listArr) {
			infos.add(str);
		}
		
		return infos;
	}
}
