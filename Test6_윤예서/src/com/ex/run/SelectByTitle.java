package com.ex.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.ex.model.vo.Book;

public class SelectByTitle {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("검색하고자 하는 제목 : ");
		String title = sc.nextLine();
		
		ArrayList<Book> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM BOOK WHERE TITLE LIKE ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + title + "%");
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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(list.isEmpty()) {
			System.out.println("검색 결과가 없습니다.");
		} else {
			for(int i=0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		}
	}

}
