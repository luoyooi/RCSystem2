package com.luoyooi.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luoyooi.dao.LoginDAO;

public class SiginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SiginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		LoginDAO lgdao = new LoginDAO();
		
		response.setContentType("text/html;charset=utf-8");
		if(lgdao.getUser(username, password) == null)
		{
			if(lgdao.setAcount(username, password, email))
			{
				response.getWriter().write("SIGIN_SUCCESS");
			}
			else
			{
				response.getWriter().write("SIGIN_FAILED");
			}
			
		}
		else
		{
			response.getWriter().write("ACOUNT_EXIST");
		}
	}

}
