package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;


public class UpdateTranCodeAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		//String menu = request.getParameter("menu");
		
		if(request.getParameter("prodNo") == null) {
			int tranNo = Integer.parseInt(request.getParameter("tranNo"));
			PurchaseService service=new PurchaseServiceImpl();
			Purchase purchase=service.getPurchase(tranNo);
			System.out.println(purchase);
			
			service.updateTranCode(purchase);
			System.out.println(purchase);
			
			request.setAttribute("purchase", purchase);
			if(request.getParameter("menu") != null) {
				return "forward:/listSale.do";
			}
			
				return "forward:/listPurchase.do";
			
			
		}else {
			int prodNo = Integer.parseInt(request.getParameter("prodNo"));
			PurchaseService service=new PurchaseServiceImpl();
			Purchase purchase=service.getPurchase2(prodNo);
			System.out.println(purchase);
			
			
			service.updateTranCode(purchase);
			
			request.setAttribute("purchase", purchase);
			System.out.println(purchase);
			return "forward:/listProduct.do?menu=manage";
		}
	}
}