package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.util.HttpUtil;
import com.model2.mvc.common.Visit;


public class ActionServlet extends HttpServlet {
	
	///Field
	private RequestMapping requestMapping;
	int i = 0;
	///Method
	@Override
	public void init() throws ServletException {
		super.init();
		String resources=getServletConfig().getInitParameter("resources");
		requestMapping=RequestMapping.getInstance(resources);
		String fileName = "./visit";
		/*
		Visit visit = new Visit();
		try {
			i = visit.getVisit(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																						throws ServletException, IOException {
		
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String reqeustPath = url.substring(contextPath.length());
		System.out.println("\nActionServlet.service() RequestURI : "+reqeustPath);
		HttpSession session=request.getSession();
		session.setAttribute("visit", i);
		try{
			Action action = requestMapping.getAction(reqeustPath);
			action.setServletContext(getServletContext());
			
			String resultPage=action.execute(request, response);
			String path=resultPage.substring(resultPage.indexOf(":")+1);
			
			if(resultPage.startsWith("forward:")){
				HttpUtil.forward(request, response, path);
			}else{
				HttpUtil.redirect(response, path);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}