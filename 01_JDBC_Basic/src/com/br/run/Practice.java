package com.br.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.br.modle.vo.Test;

public class Practice {

	public static void main(String[] args) {
		
		Test t = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		Scanner sc = new Scanner(System.in);
		System.out.print("조회할 번호 : ");
		int no = sc.nextInt();
		
		String sql = "SELECT * FROM TEST WHERE TNO = " + no;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			if(rset.next()) {
				t = new Test(rset.getInt("TNO"), rset.getString("TNAME"), rset.getDate("tdate"));
			} 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(t == null) {
			System.out.println("조회된 데이터가 없습니다.");
		} else {
			System.out.println(t);
		}
	}
}
