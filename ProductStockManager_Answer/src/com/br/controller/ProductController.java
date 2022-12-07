package com.br.controller;

import java.util.ArrayList;

import com.br.model.service.ProductService;
import com.br.model.vo.Product;
import com.br.model.vo.ProductIO;
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
		
		int result = new ProductService().insertProduct(new Product(productId, productName, Integer.parseInt(price), description, Integer.parseInt(stock)));
		
		if(result > 0) {
			new ProductMenu().displaySuccess("상품 추가 성공");
		}else {
			new ProductMenu().displayFail("상품 추가 실패");
		}
	}
	
	
	public void updateProduct(String productId, String productName, String price, String description, String stock) {
		
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
	
	public void selectIOList(int menu) { 
		
		ArrayList<ProductIO> list = null;
		
		if(menu == 1) { 	  // 1번 메뉴일 경우 즉, 입출고 전체 리스트 조회일 경우 그에 해당하는 서비스 호출
			list = new ProductService().selectIOList();
		}else if(menu == 2) { // 2번 메뉴일 경우 즉, 입고 리스트만 조회일 경우 그에 해당하는 서비스 호출
			list = new ProductService().selectIList();
		}else { 			  // 3번 메뉴일 경우 즉, 출고 리스트만 조회일 경우 그에 해당하는 서비스 호출
			list = new ProductService().selectOList();
		}
		
		if(list.isEmpty()) {
			new ProductMenu().displayNoData("조회된 내역이 없습니다.");
		}else{
			new ProductMenu().displayIOList(list, menu); // 조회된 결과 리스트와 메뉴 번호를 같은 뷰 메소드에 전달 (뷰에서 해당 메뉴 번호에 맞춰서 출력문이 다르게 출력되도록)
		}
	}
	
	public void ioProduct(ProductIO pIo) {
		
		int result = 0;
		
		// 전달 받아온 입출고 객체(pIo)의 status 값이 "입고" 일 경우 
		if(pIo.getStatus().equals("입고")) {
			
			result = new ProductService().iProduct(pIo); // 입고 서비스 호출
			
			if(result > 0) {
				new ProductMenu().displaySuccess("성공적으로 입고하였습니다.");
			}else {
				new ProductMenu().displayFail("입고에 실패하였습니다.");
			}
			
		// 전달 받아온 입출고 객체(pIo)의 status 값이 "출고" 일 경우 
		}else if(pIo.getStatus().equals("출고")) {
			
			result = new ProductService().oProduct(pIo); // 출고 서비스 호출
			
			// 출고 같은 경우는 세가지 결과값이 반환될 것 
			if(result == -1) { // 재고수량이 부족한 경우 result가 -1일 것
				new ProductMenu().displayFail("출고하고자 하는 제품의 재고수량이 부족합니다.");
			}else if(result > 0) {
				new ProductMenu().displaySuccess("성공적으로 출고하였습니다.");
			}else {
				new ProductMenu().displayFail("출고에 실패하였습니다.");
			}
			
		}
		
		
	}
	
	
}