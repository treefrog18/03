package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.User;

public class AddPurchaseViewAction extends Action{


	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service=new ProductServiceImpl();
		Product product = service.getProduct(prodNo);
		
		request.setAttribute("product", product);
		
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		
		UserService service2 = new UserServiceImpl();
		User user = service2.getUser(userId);
		
		request.setAttribute("user", user);
		
		
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
}
