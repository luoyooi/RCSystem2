package com.luoyooi.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.luoyooi.pojo.COMMVO;
import com.luoyooi.service.CommService;

/**
 * Servlet implementation class AnalysisServlet
 */
public class AnalysisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnalysisServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		CommService cs = new CommService();
		//获取电影名
		String mvName = request.getParameter("name");
		COMMVO vo = cs.listCOMM(mvName);
		//如果查询成功
		if(vo != null)
		{
			String json = JSON.toJSONString(vo);
			response.setContentType("text/html;charset=utf-8");
			try {
				response.getWriter().write(json);
			} catch (IOException e) {
				System.out.println("评论信息发送失败");
			}
		}
		else 
		{
			System.out.println("vo为null");
		}
	}

}
