package com.ex.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateBook {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("수정하고자 하는 도서 번호 : ");
		int bookNo = sc.nextInt();
		sc.nextLine();
		System.out.print("수정할 도서명 : ");
		String title = sc.nextLine();
		System.out.print("수정할 저자명 : ");
		String author = sc.nextLine();
		System.out.print("수정할 출판사 : ");
		String publisher = sc.nextLine();
		System.out.print("수정할 가격 : ");
		int price = sc.nextInt();
		System.out.print("수정할 재고수량 : ");
		int stock = sc.nextInt();
		sc.nextLine();
		
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE BOOK SET TITLE = ?, AUTHOR = ?, PUBLISHER = ?, PRICE = ?, STOCK = ? WHERE BOOK_NO = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			pstmt.setString(3, publisher);
			pstmt.setInt(4, price);
			pstmt.setInt(5, stock);
			pstmt.setInt(6, bookNo);
			
			result = pstmt.executeUpdate();
			
			if(result > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(result > 0) {
			System.out.println("성공적으로 수정했습니다.");
		} else {
			System.out.println("도서 수정에 실패했습니다.");
		}
		
	}

}
