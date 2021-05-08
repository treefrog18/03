package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;


public class GetProductAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		Integer prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String menu = request.getParameter("menu");
		ProductService service=new ProductServiceImpl();
		Product product = service.getProduct(prodNo);
		
		request.setAttribute("product", product);
		request.setAttribute("menu", menu);
		
		String prodNo2 = Integer.toString(prodNo);
		
		Cookie[] cookies = request.getCookies();
		String recent = null;
		for(int i=0; i<cookies.length; i++) {
			Cookie cookie2 = cookies[i];
			if(cookie2.getName().equals("history")) {
				recent = cookie2.getValue();
				cookie2.setValue(cookie2.getValue()+","+prodNo2);
				response.addCookie(cookie2); 
				String[] h = recent.split(","); 
				for (int j = 0; j < h.length; j++) {
					if (h[j].equals(prodNo2)) {
						Cookie cookie3 = new Cookie("history", recent);
						response.addCookie(cookie3);
						break;
					}
				}
			}else {
				Cookie cookie = new Cookie("history", prodNo2);
				cookie.setMaxAge(-1);
				response.addCookie(cookie);
			}
		}
		
			
		return "forward:/product/getProduct.jsp";
		
	}
}