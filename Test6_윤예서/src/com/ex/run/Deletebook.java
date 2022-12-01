package com.ex.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Deletebook {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("삭제하고자 하는 도서 번호 : ");
		int bookNo = sc.nextInt();
		sc.nextLine();
		
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM BOOK WHERE BOOK_NO = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bookNo);
			
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
			System.out.println("성공적으로 삭제했습니다.");
		} else {
			System.out.println("도서 삭제에 실패했습니다.");
		}
	}

}
