package com.ex.model.service;

import static com.ex.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ex.model.dao.BookDao;
import com.ex.model.vo.Book;

public class BookService {

	public ArrayList<Book> selectByTitle(String keyword) {
		
		Connection conn = getConnection();
		
		ArrayList<Book> list = new BookDao().selectByTitle(conn, keyword);
		
		close(conn);
		
		return list;
		
	}
	
	public int insertBook(Book b) {
		Connection conn = getConnection();
		
		int result = new BookDao().insertBook(conn, b);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	public int updateBook(Book b) {
		Connection conn = getConnection();
		
		int result = new BookDao().updateBook(conn, b);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		return result;
	}
	
	public int deleteBook(int bookNo) {
		Connection conn = getConnection();
		
		int result = new BookDao().deleteBook(conn, bookNo);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		return result;
	}
}
