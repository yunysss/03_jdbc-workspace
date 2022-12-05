package com.ex.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.ex.common.JDBCTemplate.*;
import com.ex.model.vo.Book;

public class BookDao {
	
	public ArrayList<Book> selectByTitle(Connection conn, String keyword) {
		
		ArrayList<Book> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "SELECT * FROM BOOK WHERE TITLE LIKE ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Book(rset.getInt("book_no"),
								  rset.getString("title"),
								  rset.getString("author"),
								  rset.getString("publisher"),
								  rset.getInt("price"),
								  rset.getInt("stock"),
								  rset.getDate("regist_date")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	
	public int insertBook(Connection conn, Book b) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO BOOK VALUES(SEQ_BOOKNO.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getTitle());
			pstmt.setString(2, b.getAuthor());
			pstmt.setString(3, b.getPublisher());
			pstmt.setInt(4, b.getPrice());
			pstmt.setInt(5, b.getStock());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	public int updateBook(Connection conn, Book b) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE BOOK SET TITLE = ?, AUTHOR = ?, PUBLISHER = ?, PRICE =?, STOCK = ? WHERE BOOK_NO = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getTitle());
			pstmt.setString(2, b.getAuthor());
			pstmt.setString(3, b.getPublisher());
			pstmt.setInt(4, b.getPrice());
			pstmt.setInt(5, b.getStock());
			pstmt.setInt(6, b.getBookNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	public int deleteBook(Connection conn, int bookNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM BOOK WHERE BOOK_NO = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

}
