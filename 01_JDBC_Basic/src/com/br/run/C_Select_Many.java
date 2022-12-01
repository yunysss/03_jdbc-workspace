package com.br.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.br.modle.vo.Test;

public class C_Select_Many {

	public static void main(String[] args) {
		
		// SELECT 문 => 실행결과 ResultSet으로 먼저 받기 => ResultSet으로부터 데이터 뽑아서 자바 객체에 세팅
		
		// 최종 조회결과를 담을 자바 객체 세팅
		ArrayList<Test> list = new ArrayList<>(); // 텅 빈 리스트
		
		// JDBC 과정 중에 필요한 객체 미리 세팅
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		// 앞으로 실행할 sql문
		String sql = "SELECT TNO, TNAME, TDATE FROM TEST";
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 객체 생성 == DB에 접속 (url, 계정명, 비밀번호)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// 3) Statement 객체 생성 (sql문 실행을 위한 객체)
			stmt = conn.createStatement();
			
			// 4, 5) sql문을 전달하면서 실행 후 결과 받기 => ResultSet 객체에
			rset = stmt.executeQuery(sql); 
			
			// 6) ResultSet으로부터 데이터 뽑아서 자바 객체에 옮겨 담기
			while(rset.next()) { // 조회된 모든 행을 스캔하기 위해서
				/*
				Test t = new Test();
				t.setTestNo(rset.getInt("tno"));
				t.setTestName(rset.getString("tname"));
				t.setTestDate(rset.getDate("tdate"));
				
				list.add(t);
				*/
				
				list.add(new Test(rset.getInt("tno"), 
						          rset.getString("tname"), 
						          rset.getDate("tdate")));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 7) 다 쓴 자원 반납
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(list.isEmpty()) {
			System.out.println("조회 결과가 없습니다.");
		} else {
			for(int i=0; i<list.size(); i++) {
				System.out.println(list.get(i));
			}
		}
	}

}
