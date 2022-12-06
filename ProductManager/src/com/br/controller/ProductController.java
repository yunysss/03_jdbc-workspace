package com.br.controller;

import java.util.ArrayList;

import com.br.model.service.ProductService;
import com.br.model.vo.Product;
import com.br.model.vo.ProductIO;
import com.br.view.ProductMenu;

public class ProductController {
	
	public void selectProduct() {
		ArrayList<Product> list = new ProductService().selectProduct();
		
		if(list.isEmpty()) {
			new ProductMenu().displayNoData("조회 결과가 없습니다.");
		} else {
			new ProductMenu().displayProductList(list);
		}
	}
	
	public void insertProduct(String productId, String productName, int price, String description, int stock) {
		
		Product p = new Product(productId, productName, price, description, stock);
		
		int result = new ProductService().insertProduct(p);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("성공적으로 추가되었습니다.");
		} else {
			new ProductMenu().displayFail("추가 실패하였습니다.");
		}
	}
	
	public void updateProduct(String productId, String productName, int price, String description, int stock) {
		
		Product p = new Product(productId, productName, price, description, stock);
		
		int result = new ProductService().updateProduct(p);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("성공적으로 수정되었습니다.");
		} else {
			new ProductMenu().displayFail("수정 실패하였습니다.");
		}
	}

	public void deleteProduct(String productId) {
		
		int result = new ProductService().deleteProduct(productId);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("성공적으로 삭제되었습니다.");
		} else {
			new ProductMenu().displayFail("삭제 실패하였습니다.");
		}
	}
	
	public void selectByProductName(String keyword) {
		
		ArrayList<Product> list = new ProductService().selectByProductName(keyword);
		
		if(list.isEmpty()) {
			new ProductMenu().displayNoData(keyword + "에 해당하는 조회 결과가 없습니다.");
		} else {
			new ProductMenu().displayProductList(list);
		}
	}
	
	public void selectProductIO() {
		ArrayList<ProductIO> list = new ProductService().selectProductIO();
		
		if(list.isEmpty()) {
			new ProductMenu().displayNoData("조회 결과가 없습니다.");
		} else {
			new ProductMenu().displayProductIOList(list);
		}
	}
	
	public void selectInput() {
		ArrayList<ProductIO> list = new ProductService().selectInput();
		
		if(list.isEmpty()) {
			new ProductMenu().displayNoData("조회 결과가 없습니다.");
		} else {
			new ProductMenu().displayProductIOList(list);
		}
	}
	
	public void selectOutput() {
		ArrayList<ProductIO> list = new ProductService().selectOutput();
		
		if(list.isEmpty()) {
			new ProductMenu().displayNoData("조회 결과가 없습니다.");
		} else {
			new ProductMenu().displayProductIOList(list);
		}
	}
	
	public void productInput(String productId, int amount) {
		
		int result = new ProductService().productInput(productId, amount);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("성공적으로 입고하였습니다.");
		} else {
			new ProductMenu().displayFail("입고 실패하였습니다");
		}
	}
	
	public void productOutput(String productId, int amount) {
		
		int result = new ProductService().productOutput(productId, amount);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("성공적으로 출고하였습니다.");
		} else if(result == 0) {
			new ProductMenu().displayFail("출고 실패하였습니다");
		} else if(result == -1) {
			new ProductMenu().displayFail("출고하고자 하는 제품의 재고수량이 부족합니다.");
		}
	}
}
