package com.model2.mvc.view.product;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;



public class UpdateProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		int prodNo= Integer.parseInt(request.getParameter("prodNo"));
		
		Product product = new Product();
		product.setProdNo(prodNo);
		product.setProdName(request.getParameter("prodName"));
		product.setFileName(request.getParameter("fileName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		

		String menu = request.getParameter("menu");
		System.out.println("업뎃프로덕트액션의메뉴값:"+menu);
		
		request.setAttribute("menu", menu);
		request.setAttribute("product", product);
		
		ProductService service=new ProductServiceImpl();
		service.updateProduct(product);
		//HttpSession session = request.getSession();
		//Integer sessionId= (((ProductVO)session.getAttribute("productVO")).getProdNo());
		//Integer sessionId=((ProductVO)session.getAttribute("productVO")).getProdNo();
	
		
		return "forward:/getProduct.do?prodNo="+prodNo+"&menu=manage";
	}
}