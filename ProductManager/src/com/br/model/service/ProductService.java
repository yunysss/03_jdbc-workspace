package com.br.model.service;

import static com.br.common.JDBCTemplate.close;
import static com.br.common.JDBCTemplate.commit;
import static com.br.common.JDBCTemplate.getConnection;
import static com.br.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import com.br.model.dao.ProductDao;
import com.br.model.vo.Product;
import com.br.model.vo.ProductIO;

public class ProductService {
	
	public ArrayList<Product> selectProduct(){
		
		Connection conn = getConnection();
		ArrayList<Product> list = new ProductDao().selectProduct(conn);
		
		close(conn);
		
		return list;
	}
	
	public int insertProduct(Product p) {
		
		Connection conn = getConnection();
		int result = new ProductDao().insertProduct(conn, p);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	public int updateProduct(Product p) {
		Connection conn = getConnection();
		
		int result = new ProductDao().updateProduct(conn, p);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	public int deleteProduct(String productId) {
		Connection conn = getConnection();
		
		int result = new ProductDao().deleteProduct(conn, productId);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	public ArrayList<Product> selectByProductName(String keyword){
		Connection conn = getConnection();
		
		ArrayList<Product> list = new ProductDao().selectByProductName(conn, keyword);
		
		close(conn);
		
		return list;
	}
	
	public ArrayList<ProductIO> selectProductIO(){
		Connection conn = getConnection();
		
		ArrayList<ProductIO> list = new ProductDao().selectProductIO(conn);
		
		close(conn);
		 
		return list;
	}
	
	public ArrayList<ProductIO> selectInput(){
		Connection conn = getConnection();
		
		ArrayList<ProductIO> list = new ProductDao().selectInput(conn);
		
		close(conn);
		 
		return list;
	}
	
	public ArrayList<ProductIO> selectOutput(){
		Connection conn = getConnection();
		
		ArrayList<ProductIO> list = new ProductDao().selectOutput(conn);
		
		close(conn);
		 
		return list;
	}
	
	public int productInput(String productId, int amount) {
		
		Connection conn = getConnection();
		
		int result = new ProductDao().productInput(conn, productId, amount);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

public int productOutput(String productId, int amount) {
		
		Connection conn = getConnection();
		
		int result = new ProductDao().productOutput(conn, productId, amount);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
}
