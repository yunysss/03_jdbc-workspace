package com.br.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import static com.br.common.JDBCTemplate.*;
import com.br.model.dao.MemberDao;
import com.br.model.vo.Member;

public class MemberService {
	
	public int insertMember(Member m) {
		// 1) jdbc driver 등록
		// 2) Connection 객체 생성 (DB에 접속)
		/*
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
		Connection conn = getConnection();
		
		int result = new MemberDao().insertMember(conn, m);
		
		// 6) 트랜잭션 처리
		if(result > 0) {
			// commit 처리
			/*
			try {
				if(conn != null && !conn.isClosed()) {
					conn.commit();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			*/
			commit(conn);
		} else {
			// rollback 처리
			/*
			try {
				if(conn != null && !conn.isClosed()) {
					conn.rollback();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			*/
			rollback(conn);
		}
		
		// conn 자원반납
		/*
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
		close(conn);
		
		return result;
	}
	
	public ArrayList<Member> selectList(){
		Connection conn = getConnection();
		
		ArrayList<Member> list = new MemberDao().selectList(conn);

		close(conn);
		
		return list;
	}
	
	public Member selectByUserId(String userId) {
		Connection conn = getConnection();
		
		Member m = new MemberDao().selectByUserId(conn, userId);
		
		close(conn);
		
		return m;
	}
	
	public ArrayList<Member> selectByUserName(String keyword){
		Connection conn = getConnection();
		
		ArrayList<Member> list = new MemberDao().selectByUserName(conn, keyword);
		
		close(conn);
		
		return list;
	}
	
	public int updateMember(Member m) {
		Connection conn = getConnection();
		int result = new MemberDao().updateMember(conn, m);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		return result;
		
	}
	
	public int deleteMember(String userId) {
		Connection conn = getConnection();
		int result = new MemberDao().deleteMember(conn, userId);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
		
	}

}
