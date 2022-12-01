package com.br.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class E_Delete {

	public static void main(String[] args) {
		
		int result = 0;
		
		Connection conn = null;
		Statement stmt = null;
		
		Scanner sc = new Scanner(System.in);
		System.out.print("삭제하고자하는 번호 : ");
		int no = sc.nextInt();
		String sql = "DELETE FROM TEST WHERE TNO = " + no;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
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
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(result > 0) {
			System.out.println("삭제 성공했습니다.");
		} else {
			System.out.println("삭제 실패했습니다.");
		}
		

	}

}
