package com.br.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.br.model.vo.Member;

// Dao : DB에 직접접근해서 사용자의 요청에 맞는 sql문 실행 후 결과 받기
//		 결과를 Controller로 반환
public class MemberDao {
	
	public int insertMember(Member m) {
		
		// insert문 => 처리된 행수(int) => 트랜잭션처리
		
		// 필요한 변수들 세팅
		int result = 0;
		Connection conn = null;
		Statement stmt = null;
		
		// 실행할 sql문 (완성형태로 만들기 => Statement : sql문이 완성형태여야함)
		// INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'XXX', 'XXXX', 'XXXX', 'X', XX, 'XXXXX', 'XXXXXX', 'XXXXX', 'XXXX', SYSDATE);
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, "
				+ "'" + m.getUserId()   + "', " 
				+ "'" + m.getUserPwd()  + "', "
				+ "'" + m.getUserName() + "', "
				+ "'" + m.getGender()   + "', " 
					  + m.getAge()      + ", "
				+ "'" + m.getEmail()    + "', " 
				+ "'" + m.getPhone()    + "', "
				+ "'" + m.getAddress()  + "', "
				+ "'" + m.getHobby()    + "', SYSDATE)";
		
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
		
		return result; // 1 아니면 0 반환
	}
	
	public ArrayList<Member> selectList() {
		// select문 (여러행 조회) => ResultSet객체 => ArrayList에 담기
		
		ArrayList<Member> list = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) {
				list.add(new Member(rset.getInt("USER_NO")
									, rset.getString("USER_ID")
									, rset.getString("USER_PWD")
									, rset.getString("user_name")
									, rset.getString("gender")
									, rset.getInt("age")
									, rset.getString("EMAIL")
									, rset.getString("phone")
									, rset.getString("address")
									, rset.getString("hobby")
									, rset.getDate("enroll_date")));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public Member selectByUserId(String userId) {
		
		Member m = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USER_ID = '" + userId + "'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			
			if(rset.next()) {
				m = new Member(rset.getInt("user_no"),
							   rset.getString("user_id"),
							   rset.getString("user_pwd"),
							   rset.getString("user_name"),
							   rset.getString("gender"),
							   rset.getInt("age"),
							   rset.getString("email"),
							   rset.getString("phone"),
							   rset.getString("address"),
							   rset.getString("hobby"),
							   rset.getDate("enroll_date"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return m;
	}
	
	public ArrayList<Member> selectByUserName(String keyword) {
		
		ArrayList<Member> list = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USER_NAME LIKE '%" + keyword + "%'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) {
				list.add(new Member(rset.getInt("user_no"),
									rset.getString("user_id"),
									rset.getString("user_pwd"),
									rset.getString("USER_NAME"),
									rset.getString("gender"),
									rset.getInt("age"),
									rset.getString("email"),
									rset.getString("phone"),
									rset.getString("address"),
									rset.getString("hobby"),
									rset.getDate("enroll_date")));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
		
	}
	
	public int updateMember(Member m) {
		
		int result = 0;
		Connection conn = null;
		Statement stmt = null;
		
		String sql = "UPDATE MEMBER "
					  + "SET USER_PWD = '" + m.getUserPwd() + "'"
					  +   ", EMAIL = '" + m.getEmail() + "'"
					  +   ", PHONE = '" + m.getPhone() + "'"
					  +   ", ADDRESS = '" + m.getAddress() + "'"
					  + " WHERE USER_ID = '" + m.getUserId() + "'";
		
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
		
		return result;
		
	}
	
	public int deleteMember(String userId) {
		
		int result = 0;
		Connection conn = null;
		Statement stmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USER_ID = '" + userId + "'";
		
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
		return result;
		
	}

}