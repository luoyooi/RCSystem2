package com.luoyooi.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luoyooi.dao.LoginDAO;
import com.luoyooi.pojo.UserDO;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		LoginDAO lgdao = new LoginDAO();
		
		response.setContentType("text/html;charset=utf-8");
		UserDO user = lgdao.getUser(username, password);
		
		if(user != null)
		{
			response.getWriter().write("TRUE");
		}
		else 
		{
			response.getWriter().write("FALSE");
		}
		
	}
}
