package com.br.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.br.controller.ProductController;
import com.br.model.vo.Product;

public class ProductMenu {
	
	private Scanner sc = new Scanner(System.in);
	private ProductController pc = new ProductController();
	
	public void mainMenu() {
		
		while(true) {
			System.out.println("\n=== 상품 관리 프로그램 ===");
			System.out.println("1. 상품 전체 조회");
			System.out.println("2. 상품 추가");
			System.out.println("3. 상품 수정");
			System.out.println("4. 상품 삭제");
			System.out.println("5. 상품 검색");
			System.out.println("0. 프로그램 종료하기");
			
			System.out.print("번호 선택 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1: pc.selectProductList(); break;
			case 2: insertProduct(); break;
			case 3: updateProduct(); break;
			case 4: pc.deleteProduct(inputProductId()); break;
			case 5: pc.searchProduct(inputProductName()); break;
			case 0: System.out.print("정말로 끝내겠습니까 ? (y/n) : ");
					if(sc.nextLine().toUpperCase().charAt(0) == 'Y') {
						System.out.println("이용해주셔서 감사합니다. 프로그램을 종료합니다.");
						return;
					}
			default: System.out.println("잘못입력하였습니다. 다시 입력해주세요");
			}
		}
		
	}
	
	/**
	 *  사용자에게 신규 상품 정보 입력받아 요청하는 화면
	 */
	public void insertProduct() {
		
		System.out.println("\n=== 신규 상품 정보 입력 ==");
		
		System.out.print("상품아이디 : ");
		String productId = sc.nextLine();
		
		System.out.print("상품명 : ");
		String productName = sc.nextLine();
		
		System.out.print("가격 : ");
		String price = sc.nextLine();
		
		System.out.print("부가설명 : ");
		String description = sc.nextLine();
		
		System.out.print("재고 : ");
		String stock = sc.nextLine();
		
		pc.insertProduct(productId, productName, price, description, stock);
		
	}

	/**
	 * 사용자에게 상품 아이디 입력받는 화면
	 * @return 사용자가 입력한 상품아이디
	 */
	public String inputProductId() {
		System.out.print("\n상품 아이디 : ");
		return sc.nextLine();
	}
	
	/**
	 * 사용자에게 상품명 입력받는 화면
	 * @return 사용자가 입력한 상품명
	 */
	public String inputProductName() {
		System.out.print("\n상품명 : ");
		return sc.nextLine();
	}
	
	/**
	 * 사용자에게 수정할 상품의 정보를 입력받아 요청하는 화면
	 */
	public void updateProduct() {
		
		System.out.println("\n=== 수정할 상품 정보 입력 ==");
		
		String productId = inputProductId();
		
		System.out.print("변경할 상품명 : ");
		String productName = sc.nextLine();
		
		System.out.print("변경할 가격 : ");
		String price = sc.nextLine();
		
		System.out.print("변경할 부가설명 : ");
		String description = sc.nextLine();
		
		System.out.print("변경할 재고 : ");
		String stock = sc.nextLine();
		
		pc.updateProduct(productId, productName, price, description, stock);
	}
	
	
	
	//-------------------------------------------------------------------------------
	
	/**
	 * 조회 결과에 해당하는 리스트 전달받아서 출력해주는 메소드
	 * @param list	=> 검색 결과가 여러행 담겨잇는 list
	 */
	public void displayProductList(ArrayList<Product> list) {
		
		System.out.println("\n=========== 상품 리스트 ===========");
		
		System.out.println("상품ID\t상품명\t가격\t부가설명\t재고");
		for(Product p : list) {
			System.out.println(p);
		}
	}
	
	
	/**
	 * 조회된 데이터가 없을 때 실행되는 메소드
	 * @param message
	 */
	public void displayNoData(String message) {
		System.out.println("\n" + message);
	}

	/**
	 * 서비스 요청 처리 성공 메세지 출력 메소드 
	 * @param message
	 */
	public void displaySuccess(String message) {
		System.out.println("\n서비스 요청 성공 : " + message);
	}
	
	/**
	 * 서비스 요청 처리 오류 메세지 출력 메소드
	 * @param message
	 */
	public void displayFail(String message) {
		System.out.println("\n서비스 요청 실패 : " + message);
	}
}
