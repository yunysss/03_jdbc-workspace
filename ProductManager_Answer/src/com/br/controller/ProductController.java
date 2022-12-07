package com.br.controller;

import java.util.ArrayList;

import com.br.model.service.ProductService;
import com.br.model.vo.Product;
import com.br.view.ProductMenu;

public class ProductController {
	
	public void selectProductList() {
		
		ArrayList<Product> list = new ProductService().selectProductList();
		
		if(list.isEmpty()) {
			new ProductMenu().displayNoData("조회된 상품이 없습니다.");
		}else {
			new ProductMenu().displayProductList(list);
		}
		
	}
	
	public void insertProduct(String productId, String productName, String price, String description, String stock) {
		
		//Product p = new Product(productId, productName, Integer.parseInt(price), description, Integer.parseInt(stock));
		//int result = new ProductService().insertProduct(p);
		
		// 위의 두줄을 아래처럼 한줄로도 가능
		int result = new ProductService().insertProduct(new Product(productId, productName, Integer.parseInt(price), description, Integer.parseInt(stock)));
		
		if(result > 0) {
			new ProductMenu().displaySuccess("상품 추가 성공");
		}else {
			new ProductMenu().displayFail("상품 추가 실패");
		}
		
	}
	
	public void updateProduct(String productId, String productName, String price, String description, String stock) {
		
		//Product p = new Product(productId, productName, Integer.parseInt(price), description, Integer.parseInt(stock));
		//int result = new ProductService().updateProduct(p);
		
		// 위의 두줄을 아래처럼 한줄로도 가능
		int result = new ProductService().updateProduct(new Product(productId, productName, Integer.parseInt(price), description, Integer.parseInt(stock)));
		
		if(result > 0) {
			new ProductMenu().displaySuccess("상품 변경 성공");
		}else {
			new ProductMenu().displayFail("상품 변경 실패");
		}
		
	}
	
	public void deleteProduct(String productId) {
		
		int result = new ProductService().deleteProduct(productId);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("상품 삭제 성공");
		}else {
			new ProductMenu().displayFail("상품 삭제 실패");
		}
		
	}
	
	public void searchProduct(String productName) {
		
		ArrayList<Product> list = new ProductService().searchProduct(productName);
		
		if(list.isEmpty()) {
			new ProductMenu().displayNoData(productName + "에 대한 검색 결과가 없습니다.");
		}else {
			new ProductMenu().displayProductList(list);
		}
		
	}

	

	

}
