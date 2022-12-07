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
	
	
	public ArrayList<Product> selectProductList(){
		
		Connection conn = getConnection();
		ArrayList<Product> list = new ProductDao().selectProductList(conn);
	
		close(conn);
		
		return list;
	}
	
	public int insertProduct(Product p) {
		
		Connection conn = getConnection();
		int result = new ProductDao().insertProduct(conn, p);
		
		if(result > 0) {
			commit(conn);
		}else {
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
		}else {
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
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	public ArrayList<Product> searchProduct(String productName){
		
		Connection conn = getConnection();
		ArrayList<Product> list = new ProductDao().searchProduct(conn, productName);
		
		close(conn);
		
		return list;
	}
	
	public ArrayList<ProductIO> selectIOList(){
		Connection conn = getConnection();
		
		ArrayList<ProductIO> list = new ProductDao().selectIOList(conn);
		
		close(conn);
		
		return list;
		
	}
	
	public ArrayList<ProductIO> selectIList(){
		Connection conn = getConnection();
		
		ArrayList<ProductIO> list = new ProductDao().selectIList(conn);
		
		close(conn);
		
		return list;
		
	}
	
	public ArrayList<ProductIO> selectOList(){
		Connection conn = getConnection();
		
		ArrayList<ProductIO> list = new ProductDao().selectOList(conn);
		
		close(conn);
		
		return list;
		
	}
	
	public int iProduct(ProductIO pIo) {
		Connection conn = getConnection();
		
		int result = new ProductDao().ioProduct(conn, pIo);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	
	public int oProduct(ProductIO pIo) {
		
		Connection conn = getConnection();
		int result = 0;
		
		// 출고는 위의 입고와 다른 점이 PRODUCT_IO 테이블에 값을 바로 INSERT 하기전에 
		// 재고수량이 출고할 수량보다 많은지 먼저 검사를 해봐야 된다. 
		
		// 1. 출고하고자 하는 상품 ID를 전달하여 해당 출고하고자 상품의 재고수량을 먼저 받아오는 DAO 호출
		int stock = new ProductDao().selectProductStock(conn, pIo.getProductId());
		
		// 2. 1번과정을 통해 알아온 해당 상품의 stock이 출고하고자 하는 수량(pIo의 amount)보다 클경우  (즉, 출고가 가능한 경우)
		//    PRODUCT_IO 테이블에 값을 INSERT하는 DAO 호출하도록..
		if(stock >= pIo.getAmount()) { // 재고가 출고수량보다 큰 경우(즉, 출고가 가능한 경우)
			result = new ProductDao().ioProduct(conn, pIo); // 출고내역 insert하는 dao 실행
		}else { // 재고가 출고수량보다 적을 경우 (즉, 출고할 수 없는 경우)
			result = -1; // result를 -1로 대입해서 controller에 리턴할 것
		}
	
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
		
	}
}
