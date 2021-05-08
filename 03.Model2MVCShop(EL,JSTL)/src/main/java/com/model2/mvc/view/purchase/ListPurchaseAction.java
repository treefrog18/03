package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class ListPurchaseAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		Search search = new Search();
		
		//String menu = request.getParameter("menu");
		
		HttpSession session = request.getSession();
		String buyerId = (String)session.getAttribute("userId");
		
		int currentPage=1;

		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		System.out.println("ListPurchaseAction의 써치컨디션값 : "+request.getParameter("searchCondition"));

		search.setSearchKeyword(request.getParameter("searchKeyword"));
		System.out.println("ListPurchaseAction의 써치키워드값 : "+request.getParameter("searchKeyword"));

		// web.xml  meta-data 로 부터 상수 추출 
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
	
		PurchaseService service=new PurchaseServiceImpl();
		Map<String,Object> map=service.getPurchaseList(search, buyerId);
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListPurchaseAction ::"+resultPage);
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		
		
		//request.setAttribute("map", map);
		//request.setAttribute("menu", menu);
		//request.setAttribute("search", search);
		
		return "forward:/purchase/listPurchase.jsp";
	}
}