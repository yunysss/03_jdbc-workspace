package com.ex.controller;

import java.util.ArrayList;

import com.ex.model.service.BookService;
import com.ex.model.vo.Book;
import com.ex.view.BookMenu;

public class BookController {
	
	public void selectByTitle(String keyword) {
		
		ArrayList<Book> list = new BookService().selectByTitle(keyword);
		
		if(list.isEmpty()) {
			new BookMenu().displayNoData("검색 결과가 없습니다.");
		} else {
			new BookMenu().displayBookList(list);
		}
	}
	
	public void insertBook(String title, String author, String publisher, int price, int stock) {
		
		Book b = new Book();
		b.setTitle(title);
		b.setAuthor(author);
		b.setPublisher(publisher);
		b.setPrice(price);
		b.setStock(stock);
		
		int result = new BookService().insertBook(b);
		
		if(result > 0) {
			new BookMenu().displaySuccess("성공적으로 추가되었습니다.");
		} else {
			new BookMenu().displayFail("도서 추가에 실패하였습니다.");
		}
	}
	
	public void updateBook(int bookNo, String title, String author, String publisher, int price, int stock) {
		
		Book b = new Book();
		b.setBookNo(bookNo);
		b.setTitle(title);
		b.setAuthor(author);
		b.setPublisher(publisher);
		b.setPrice(price);
		b.setStock(stock);
		
		int result = new BookService().updateBook(b);
		
		if(result > 0) {
			new BookMenu().displaySuccess("성공적으로 수정했습니다.");
		} else {
			new BookMenu().displayFail("도서 수정에 실패하였습니다.");
		}
	}
	
	public void deleteBook(int bookNo) {
		
		int result = new BookService().deleteBook(bookNo);
		
		if(result > 0) {
			new BookMenu().displaySuccess("성공적으로 삭제했습니다.");
		} else {
			new BookMenu().displayFail("도서 삭제에 실패했습니다.");
		}
	}
}
