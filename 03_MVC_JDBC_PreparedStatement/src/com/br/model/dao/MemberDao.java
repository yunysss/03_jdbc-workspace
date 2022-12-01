package com.br.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.br.model.vo.Member;

// Dao : DB에 직접접근해서 사용자의 요청에 맞는 sql문 실행 후 결과 받기
//		 결과를 Controller로 반환
public class MemberDao {
	
	/*
	 * * Statement와 PreparedStatement의 특징
	 * - 둘 다 sql문을 실행하고 결과를 받아내는 객체 (둘 중에 하나 이용) 
	 * - Statement가 PreparedStatement의 부모 (상속구조)
	 * 
	 * * Statement와 PreparedStatement의 차이점
	 * - Statement 같은 경우 sql문을 바로 전달하면서 실행시키는 객체
	 * 	 (즉, sql문을 완성형태로 만들어 둬야함 == 사용자가 입력한 값들이 다 채워진 형태로)
	 * 
	 * 		1) Connection 객체를 통해 Statement 객체 생성
	 * 			> stmt = conn.createStatement();
	 * 		2) Statement 객체를 통해 sql문 실행 결과 받기
	 * 			> 결과 = stmt.executeXXX(완성된sql문);
	 * 
	 * - PreparedStatement 같은 경우 "미완성된 sql문"을 잠시 보관해둘 수 있는 객체
	 * 	 (즉, 사용자가 입력한 값들을 채워두지 않고 각각 들어갈 공간만 미리 확보해놔도됨)
	 * 
	 * 		1) Connection 객체를 통해 PreparedStatement 객체 생성
	 * 			> pstmt = conn.prepareStatement([미]완성된sql문);
	 *		2) pstmt에 담긴 sql문이 미완성된 상태일 경우 우선 완성시켜야함
	 *			> pstmt.setXXX(1, "대체할값");
	 *			> pstmt.setXXX(2, "대체할값");
	 *				...
	 *		3) 해당 완성된 sql문 실행 결과 받기
	 *			> 결과 = pstmt.executeXXX();
	 */
	
	public int insertMember(Member m) {
		// insert문 => 처리된 행수 => 트랜잭션 처리
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 실행할 sql문 (미완성된 형태 가능)
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		// 미리 사용자가 입력한 값들이 들어갈 수 있게 공간 확보 (? == 홀더)만 해두면 됨
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2) Connection 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			// 3) PreparedStatement 생성
			pstmt = conn.prepareStatement(sql); // 애초에 객체 생성시 sql문을 담은채로 생성 (미완성된 sql문)
			
			// 미완성된 sql문이 담겨있을 경우
			// > 빈 공간(?)을 실제값(사용자가입력한값)으로 채워준 후
			// pstmt.setString(홀더순번, 대체할값);	=> '대체할값' (양옆에 홑따옴표 감싸준 상태로 들어감)
			// pstmt.setInt(홀더순번, 대체할값);		=> 대체할값
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			// 4, 5) sql문 실행 및 결과받기
			result = pstmt.executeUpdate();
			
			// 6) 트랜잭션 처리
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
		return result;
		
	}
	
	public ArrayList<Member> selectList(){
		// select문(여러행) => ResultSet => ArrayList담기
		ArrayList<Member> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String str =  "SELECT * FROM MEMBER"; 
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(str); // 애초에 완성된 sql문 => 곧바로 실행
			rset = pstmt.executeQuery();
			while(rset.next()) {
				list.add(new Member(rset.getInt("user_no"), 
									rset.getString("user_id"), 
									rset.getString("user_pwd"), 
									rset.getString("user_name"),
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
				pstmt.close();
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
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USER_ID = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			
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
				pstmt.close();
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
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 해결방법1.
		//String sql = "SELECT * FROM MEMBER WHERE USER_NAME LIKE ?";
		
		// 해결방법2.
		String sql = "SELECT * FROM MEMBER WHERE USER_NAME LIKE '%' || ? || '%'";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql);
			
			// 해결방법1의 sql문일 경우
			//pstmt.setString(1, "%" + keyword + "%");
			
			// 해결방법2의 sql문일 경우
			pstmt.setString(1, keyword);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Member(rset.getInt("user_no"), 
						rset.getString("user_id"), 
						rset.getString("user_pwd"), 
						rset.getString("user_name"),
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
				pstmt.close();
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
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE MEMBER SET USER_PWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ? WHERE USER_ID = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
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
		
		return result;
		
	}
	
	public int deleteMember(String userId) {
		
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USER_ID = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
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
		return result;
	}
	
}