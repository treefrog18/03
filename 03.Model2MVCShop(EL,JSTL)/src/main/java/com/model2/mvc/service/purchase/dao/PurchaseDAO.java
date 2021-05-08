package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.*;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDAO {
	
	public PurchaseDAO(){
	}

	public void insertPurchase(Purchase purchase) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into TRANSACTION values (seq_transaction_tran_no.nextval, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, sysdate, ?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase.getProdNo());
		stmt.setString(2, (purchase.getBuyer()).getUserId());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		stmt.setString(8, "1");
		stmt.setString(9, purchase.getDivyDate().replace("-", ""));
		
		stmt.executeUpdate();
		
		con.close();
	}

	public Purchase findPurchase(int tranNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from TRANSACTION where TRAN_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();

		Purchase purchase = null;
		User user = null;
		while (rs.next()) {
			purchase = new Purchase();
			user = new User();
			user.setUserId(rs.getString("BUYER_ID"));
			purchase.setTranNo(rs.getInt("TRAN_NO"));
			purchase.setProdNo(rs.getInt("PROD_NO"));
			purchase.setBuyer(user);
			purchase.setPaymentOption(rs.getString("PAYMENT_OPTION").trim());
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setDivyAddr(rs.getString("demailaddr"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setTranCode(rs.getString("tran_status_code").trim());
			purchase.setOrderDate(rs.getDate("order_data"));
			purchase.setDivyDate(String.valueOf(rs.getDate("dlvy_Date")));

		}
		
		con.close();

		return purchase;
	}
	
	
public Purchase findPurchase2(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from TRANSACTION where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Purchase purchase = null;
		User user = null;
		while (rs.next()) {
			purchase = new Purchase();
			user = new User();
			user.setUserId(rs.getString("BUYER_ID"));
			purchase.setTranNo(rs.getInt("TRAN_NO"));
			purchase.setProdNo(rs.getInt("PROD_NO"));
			purchase.setBuyer(user);
			purchase.setPaymentOption(rs.getString("PAYMENT_OPTION").trim());
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setDivyAddr(rs.getString("demailaddr"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setTranCode(rs.getString("tran_status_code").trim());
			purchase.setOrderDate(rs.getDate("order_data"));
			purchase.setDivyDate(String.valueOf(rs.getDate("dlvy_Date")));

		}
		
		con.close();
		System.out.println("DAO findPurchase:"+purchase);
		return purchase;
	}
	
	
	
	public Map<String,Object> getPurchaseList(Search search, String buyerId) throws Exception {
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select user_id, tran_no, receiver_name, receiver_phone, tran_status_code from users, TRANSACTION"
				+ " where user_id = buyer_id and user_id = '" + buyerId + "'";
		sql += " order by order_data";

		System.out.println("PurchaseDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurchaseDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);

		List<Purchase> list = new ArrayList<Purchase>();
		
		while(rs.next()){
			UserService service=new UserServiceImpl();
			User user=service.getUser(buyerId);
			Purchase purchase = new Purchase();
			purchase.setBuyer(user);
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setTranCode(rs.getString("tran_status_code").trim());
			purchase.setTranNo(rs.getInt("tran_no"));
			list.add(purchase);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();

		return map;
		
	}
	
	
public Map<String,Object> getSaleList(Search search) throws Exception {
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select user_id, tran_no, receiver_name, receiver_phone, tran_status_code from users, TRANSACTION"
				+ " where user_id = buyer_id order by order_data";

		System.out.println("PurchaseDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurchaseDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);

		List<Purchase> list = new ArrayList<Purchase>();
		
		while(rs.next()){
			UserService service=new UserServiceImpl();
			User user=service.getUser(rs.getString("user_id"));
			Purchase purchase = new Purchase();
			purchase.setBuyer(user);
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setTranCode(rs.getString("tran_status_code").trim());
			purchase.setTranNo(rs.getInt("tran_no"));
			list.add(purchase);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();

		return map;
		
	}
	
	
	public void updatePurchase(Purchase purchase) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "update transaction set payment_option=?, receiver_name=?, receiver_phone=?, demailaddr=?, "
				+ "dlvy_request=?, dlvy_date=? where tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getPaymentOption().trim());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6, purchase.getDivyDate().replace("-", ""));
		stmt.setInt(7, purchase.getTranNo());
		stmt.executeUpdate();
		
		con.close();
	}
	
	
	public void updateTranCode(Purchase purchase) throws Exception {
		
		Connection con = DBUtil.getConnection();
		System.out.println("updatetrancode 시작");
		String sql = "update transaction set ";
		if(purchase.getTranCode().equals("1")) {
			sql += " tran_status_code = 2";
		}else if(purchase.getTranCode().equals("2")) {
			sql += " tran_status_code = 3";
		}
		
		sql += " where tran_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase.getTranNo());
		int confirm = stmt.executeUpdate();
		if(confirm == 1) {
		System.out.println("업데이트 완료");
		}
		con.close();
	}
	
private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	// 게시판 currentPage Row 만  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("PurchaseDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
	
}