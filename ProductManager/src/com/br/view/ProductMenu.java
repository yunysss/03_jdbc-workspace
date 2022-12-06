package com.br.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.br.controller.ProductController;
import com.br.model.vo.Product;
import com.br.model.vo.ProductIO;

public class ProductMenu {
	Scanner sc = new Scanner(System.in);
	ProductController pc = new ProductController();
	
	public void mainMenu() {
		
		while(true) {
			System.out.println("\n== 상품 관리 프로그램 ==");
			System.out.println("1. 상품 전체 조회");
			System.out.println("2. 상품 추가");
			System.out.println("3. 상품 수정");
			System.out.println("4. 상품 삭제");
			System.out.println("5. 상품 검색");
			System.out.println("6. 상품 입출고");
			System.out.println("0. 프로그램 종료");
			System.out.print("메뉴 선택 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1: pc.selectProduct(); break;
			case 2: insertProduct(); break;
			case 3: updateProduct(); break;
			case 4: pc.deleteProduct(inputProductId()); break;
			case 5: selectByProductName(); break;
			case 6: inOutProduct(); break;
			case 0: System.out.println("프로그램을 종료합니다."); return;
			default: System.out.println("잘못 선택하셨습니다. 메뉴를 다시 선택해주세요.");
			}
		}
		
	}
	
	public void insertProduct() {
		System.out.println("\n=== 상품 추가 ===");
		System.out.print("상품 아이디 : ");
		String productId = sc.nextLine();
		System.out.print("상품명 : ");
		String productName = sc.nextLine();
		System.out.print("상품가격 : ");
		int price = sc.nextInt();
		sc.nextLine();
		System.out.print("상품상세정보 : ");
		String description = sc.nextLine();
		System.out.print("재고수량 : ");
		int stock = sc.nextInt();
		sc.nextLine();
		
		pc.insertProduct(productId, productName, price, description, stock);
	}
	
	public String inputProductId() {
		System.out.print("\n상품 아이디 : ");
		return sc.nextLine();
	}
	
	public String inputpruoductName() {
		System.out.println("\n상품명 : ");
		return sc.nextLine();
	}
	
	public void updateProduct() {
		System.out.println("\n=== 상품 수정 ===");
		String productId = inputProductId();
		System.out.print("수정할 상품명 : ");
		String productName = sc.nextLine();
		System.out.print("수정할 상품가격 : ");
		int price = sc.nextInt();
		sc.nextLine();
		System.out.print("수정할 상품상세정보 : ");
		String description = sc.nextLine();
		System.out.print("수정할 재고수량 : ");
		int stock = sc.nextInt();
		sc.nextLine();
		
		pc.updateProduct(productId, productName, price, description, stock);
	}
	
	public void selectByProductName() {
		System.out.println("\n=== 상품 검색 ===");
		System.out.print("검색할 상품명 : ");
		String keyword = sc.nextLine();
		
		pc.selectByProductName(keyword);
	}
	
	public void inOutProduct() {
		
		while(true){
			System.out.println("\n======= 상품 관리 프로그램 =======");
			System.out.println("1. 전체 입출고 내역 조회");
			System.out.println("2. 입고 내역만 조회");
			System.out.println("3. 출고 내역만 조회");
			System.out.println("4. 상품 입고");
			System.out.println("5. 상품 출고");
			System.out.println("9. 메인 메뉴로 돌아가기");
			System.out.print(">> 메뉴 선택 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1: pc.selectProductIO(); break;
			case 2: pc.selectInput(); break;
			case 3: pc.selectOutput(); break;
			case 4: productInput(); break;
			case 5: productOutput(); break;
			case 9: System.out.println("메인 메뉴로 돌아갑니다."); return;
			default: System.out.println("잘못 선택하셨습니다. 메뉴를 다시 선택해주세요.");
			}
		}
		
	}
	
	public void productInput() {
		String productId = inputProductId();
		System.out.print("입고 수량 : ");
		int amount = sc.nextInt();
		sc.nextLine();
		
		pc.productInput(productId, amount);
	}
	
	public void productOutput() {
		String productId = inputProductId();
		System.out.print("출고 수량 : ");
		int amount = sc.nextInt();
		sc.nextLine();
		
		pc.productOutput(productId, amount);
	}
	
	
	//----------------------------------------------------------------
	
	public void displayNoData(String message) {
		System.out.println("\n" + message);
	}
	
	public void displayProductList(ArrayList<Product> list) {
		System.out.println("\n============== 상품 리스트 ==============\n");
		System.out.println("상품ID\t상품명\t가격\t부가설명\t재고수량");
		
		for(int i=0; i<list.size(); i++) {
			System.out.printf("%s\t%s\t%d\t%s\t%d\t\n", list.get(i).getProductID()
					                                  , list.get(i).getProductName()
					                                  , list.get(i).getPrice()
					                                  , list.get(i).getDescription()
					                                  , list.get(i).getStock());
		}
	}
	
	public void displayProductIOList(ArrayList<ProductIO> list) {
		System.out.println("\n======================= " + list.get(0).getStatus() + " 리스트 =======================");
		System.out.println("입출고번호\t상품ID\t상품명\t입출고일\t\t입출고수량\t입출고상태");
		for(int i=0; i<list.size(); i++) {
			System.out.printf("%d\t%s\t%s\t%s\t%d\t%s\n", list.get(i).getIoNum()
														, list.get(i).getProductId()
														, list.get(i).getProductName()
														, list.get(i).getIoDate()
														, list.get(i).getAmount()
														, list.get(i).getStatus());
		}
	}
	
	public void displaySuccess(String message) {
		System.out.println("\n사용자 요청 성공 : " + message);
	}
	
	public void displayFail(String message) {
		System.out.println("\n사용자 요청 실패 : " + message);
	}

}
