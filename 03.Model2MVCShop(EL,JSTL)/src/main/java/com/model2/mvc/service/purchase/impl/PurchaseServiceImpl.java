package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDAO;

public class PurchaseServiceImpl implements PurchaseService{
	
	private PurchaseDAO purchaseDAO;
	private ProductDAO productDAO;
	
	
	public PurchaseServiceImpl() {
		purchaseDAO=new PurchaseDAO();
	}

	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDAO.insertPurchase(purchase);
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDAO.findPurchase(tranNo);
	}

	@Override
	public Purchase getPurchase2(int prodNo) throws Exception {
		return purchaseDAO.findPurchase2(prodNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		return purchaseDAO.getPurchaseList(search, buyerId);
	}

	@Override
	public Map<String, Object> getSaleList(Search search) throws Exception {
		return purchaseDAO.getSaleList(search);
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception {
		purchaseDAO.updatePurchase(purchase);
		
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDAO.updateTranCode(purchase);
		
	}
}