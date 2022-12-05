package com.ex.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.ex.controller.BookController;
import com.ex.model.vo.Book;

public class BookMenu {
	
	private Scanner sc = new Scanner(System.in);
	private BookController bc = new BookController();
	
	public void mainMenu() {
		
		while(true) {
			System.out.println("\n== 도서 관리 프로그램 ==");
			System.out.println("1. 도서 제목으로 검색");
			System.out.println("2. 도서 추가");
			System.out.println("3. 도서 수정");
			System.out.println("4. 도서 삭제");
			System.out.println("0. 프로그램 종료");
			System.out.print(">> 메뉴 : ");
			int menu = sc.nextInt();
			sc.nextLine();
			
			switch(menu) {
			case 1: bc.selectByTitle(inputTitle()); break;
			case 2: insertBook(); break;
			case 3: updateBook(); break;
			case 4: bc.deleteBook(inputBookNo()); break;
			case 0: System.out.println("\n프로그램을 종료합니다."); return;
			default: System.out.println("\n잘못 입력하셨습니다. 다시 입력해주세요.");
			}
		}
		
	}
	
	public String inputTitle() {
		System.out.print("\n제목 입력 : ");
		String keyword = sc.nextLine();
		
		return keyword;
	}

	public void insertBook() {
		System.out.println("\n== 도서 추가 ==");
		System.out.print("\n추가할 도서명 : ");
		String title = sc.nextLine();
		System.out.print("추가할 저자명 : ");
		String author = sc.nextLine();
		System.out.print("추가할 출판사명 : ");
		String publisher = sc.nextLine();
		System.out.print("추가할 가격 : ");
		int price = sc.nextInt();
		System.out.print("추가할 재고수량 : ");
		int stock = sc.nextInt();
		sc.nextLine();
		
		bc.insertBook(title, author, publisher, price, stock);
		
	}
	
	public int inputBookNo() {
		System.out.print("\n도서번호 입력 : ");
		int bookNo = sc.nextInt();
		sc.nextLine();
		
		return bookNo;
	}
	// 도서명, 저자명, 출판사, 가격, 재고수량
	public void updateBook() {
		System.out.println("\n== 도서 수정 ==");
		int bookNo = inputBookNo();
		System.out.print("\n수정할 도서명 : ");
		String title = sc.nextLine();
		System.out.print("수정할 저자명 : ");
		String author = sc.nextLine();
		System.out.print("수정할 출판사명 : ");
		String publisher = sc.nextLine();
		System.out.print("수정할 가격 : ");
		int price = sc.nextInt();
		System.out.print("수정할 재고수량 : ");
		int stock = sc.nextInt();
		sc.nextLine();
		
		bc.updateBook(bookNo, title, author, publisher, price, stock);
		
		
	}
	
	// -------------------------------------------------------------------
	public void displayNoData(String message) {
		System.out.println("\n" + message);
	}
	
	public void displayBookList(ArrayList<Book> list) {
		for(int i=0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
	public void displaySuccess(String message) {
		System.out.println("\n서비스 요청 성공 : " + message); 
	}
	
	public void displayFail(String message) {
		System.out.println("\n서비스 요청 실패 : " + message);
	}

}
