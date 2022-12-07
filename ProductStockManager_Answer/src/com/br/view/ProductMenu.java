package com.br.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.br.controller.ProductController;
import com.br.model.vo.Product;
import com.br.model.vo.ProductIO;

public class ProductMenu {
	
	private Scanner sc = new Scanner(System.in);
	private ProductController pc = new ProductController();
	
	public void mainMenu() {
		
		while(true) {
			System.out.println("\n======= 상품 관리 프로그램 =======");
			System.out.println("1. 상품 전체 조회");
			System.out.println("2. 상품 추가");
			System.out.println("3. 상품 수정");
			System.out.println("4. 상품 삭제");
			System.out.println("5. 상품 검색");
			System.out.println("6. 상품 입출고 메뉴");
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
			case 6: ioSubMenu(); break;
			case 0: System.out.print("정말로 끝내겠습니까 ? (y/n) : ");
					if(sc.next().toUpperCase().charAt(0) == 'Y') return;
					break;
			default: System.out.println("잘못입력하였습니다. 다시 입력해주세요");
			}
		}
		
	}
	
	/**
	 * ** 상품 입출고 메뉴 (서브메뉴) **
	 */
	public void ioSubMenu() {
		while(true) {
			System.out.println("\n=== 입출고 메뉴 ===");
			System.out.println("1. 전체 입출고 내역 조회");
			System.out.println("2. 입고 내역 조회");
			System.out.println("3. 출고 내역 조회");
			System.out.println("4. 상품 입고");
			System.out.println("5. 상품 출고");
			System.out.println("9. 메인 메뉴로 돌아가기");
			System.out.print("번호 선택 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			// 1~3번 유사한 기능이므로 controller에 같은 메소드 호출(단, 메뉴번호를 전달해서 controller에서 그 메뉴 번호에 맞춰서 각 서비스 호출하기 위해)
			case 1: 
			case 2: 
			case 3: pc.selectIOList(menu); break;
			// 4~5번 유사한 기능으로 같은 서브메뉴 호출(단, 메뉴 번호를 전달해서 각 번호에 맞춰서 사용자에게 값 입력 받도록)
			case 4: ioProduct(menu); break;
			case 5: ioProduct(menu); break;
			case 9: return;
			default: System.out.println("잘못입력하였습니다. 다시 입력해주세요.");
			}
			
		}
	}
	
	/**
	 * 상품 입고, 상품 출고시 사용자에게 값 입력 받는 화면 
	 * @param menu
	 */
	public void ioProduct(int menu) {
		ProductIO pIo = new ProductIO(); 
		
		pIo.setProductId(inputProductId()); // 입고 또는 출고 하고자 하는 상품 아이디 입력받는 메소드 재사용하여 수정
		
		if(menu == 4) { // 상품 입고 메뉴일 경우
			pIo.setStatus("입고"); // status 값에 입고 대입
			
			System.out.print("입고 수량 : "); // 사용자에게 입고수량을 입력받도록 출력문 출력
			
		}else { // 상품 출고 메뉴일 경우
			pIo.setStatus("출고"); // status 값에 출고 대입 
			
			System.out.print("출고 수량 : "); // 사용자에게 출고수량을 입력받도록 출력문 출력
			
		}
		pIo.setAmount(sc.nextInt()); // 사용자에게 입고 또는 출고하고자 하는 수량 입력받아서 대입
		
		pc.ioProduct(pIo); // controller의 입출고 기능으로 pIo 전달
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
	
	
	/**
	 * 상품 검색 결과 리스트를 출력해주는 메소드
	 * @param list
	 */
	public void displayProductList(ArrayList<Product> list) {
		System.out.println("\n============ 상품 리스트 ===========");
		
		System.out.println("상품ID\t상품명\t가격\t부가설명\t재고");
		for(Product p : list) {
			System.out.println(p);
		}
	}
	
	
	/**
	 * 입출고 내역 검색 결과 리스트를 출력해주는 메소드 
	 * @param list
	 */
	public void displayIOList(ArrayList<ProductIO> list, int menu) {
		
		// 각 메뉴 번호마다 위에 보여지는 문구만 달라지게끔
		if(menu == 1) { 	
			System.out.println("\n====================== 입출고 리스트 ======================");
		}else if(menu == 2) { 
			System.out.println("\n====================== 입고 리스트 ======================");
		}else {				  
			System.out.println("\n====================== 출고 리스트 ======================");
		}
		System.out.println("입출고번호\t상품ID\t상품명\t입출고일\t\t입출고수량\t입출고상태");
		for(ProductIO pIo : list) {
			System.out.println(pIo);
		}
	}
	
	
	/**
	 * 조회된 데이터가 없을 때 실행되는 메소드
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
		System.err.println("\n서비스 요청 실패 : " + message); // System.err로 출력문을 작성하면 빨간문구로 출력됨!! 
	}
}