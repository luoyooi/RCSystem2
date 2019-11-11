package com.luoyooi.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.luoyooi.dao.MvInfoDAO;
import com.luoyooi.pojo.MvInfoDO;
import com.luoyooi.service.MvInfoService;

public class MvInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MvInfoService service = new MvInfoService();
		//获取根据评分排序后的电影信息
		String jsonStr=JSON.toJSONString(service.ListByScore("最新电影信息"));
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(jsonStr);
	}

}
