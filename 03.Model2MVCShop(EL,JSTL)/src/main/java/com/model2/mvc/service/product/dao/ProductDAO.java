package com.model2.mvc.service.product.dao;

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


public class ProductDAO {
	
	public ProductDAO(){
	}

	public void insertProduct(Product product) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into PRODUCT values (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate().replace("-", ""));
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
		stmt.executeUpdate();
		
		con.close();
	}

	public Product findProduct(Integer prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select p.prod_no, p.prod_name, p.image_file, p.prod_detail,"
				+ "p.manufacture_day, p.price, p.reg_date, nvl(t.tran_status_code, 0) tran_status_code from PRODUCT p, TRANSACTION t "
				+ "where p.prod_no = t.prod_no(+) AND p.PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Product product = null;
		while (rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setRegDate(rs.getDate("REG_DATE"));
			product.setProTranCode(rs.getString("tran_status_code"));

		}
		
		con.close();

		return product;
	}
	
	public Map<String,Object> getProductList(Search search) throws Exception {
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		// Original Query 구성
		String sql = "select p.prod_no, p.prod_name, p.reg_date, p.price,"
				+ " nvl(t.tran_status_code, 0) tran_status_code  "
				+ "from product p, transaction t"
				+ " where p.prod_no = t.prod_no(+) ";
		
		if (search.getSearchCondition() != null) {
			if ( search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("") ) {
				sql += " AND p.prod_no = '" + search.getSearchKeyword()+"'"
						+ " ORDER BY p.prod_no";
			
			} else if ( search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " AND p.prod_name LIKE '" + search.getSearchKeyword() + "%'"
						+ " ORDER BY p.prod_no";
			} else if ( search.getSearchCondition().equals("2") && !search.getSearchKeyword().equals("")) {
				sql += " AND p.price ='" + search.getSearchKeyword() + "'"
						+ " ORDER BY p.prod_no";
			} else if (search.getSearchCondition().equals("3")) {
				sql += " ORDER BY p.price desc";
			} else if (search.getSearchCondition().equals("4")) {
				sql += " ORDER BY p.price";
			}			
		}else {
			sql += "ORDER BY p.prod_no";
		}

		
		System.out.println("ProductDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);

		List<Product> list = new ArrayList<Product>();
		
		while(rs.next()){
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setRegDate(rs.getDate("reg_date"));
			product.setPrice(rs.getInt("price"));
			product.setProTranCode(rs.getString("tran_status_code").trim());
			list.add(product);
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
	
	public void updateProduct(Product product) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "update PRODUCT set PROD_NAME=?, IMAGE_FILE=?, PROD_DETAIL=?, "
				+ "MANUFACTURE_DAY=?, PRICE=? where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getFileName());
		stmt.setString(3, product.getProdDetail());
		stmt.setString(4, product.getManuDate().replace("-", ""));
		stmt.setInt(5, product.getPrice());
		//stmt.setDate(6, productVO.getRegDate());
		stmt.setInt(6, product.getProdNo());
		stmt.executeUpdate();
		
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
		
		System.out.println("ProductDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
	
}