package com.br.controller;

import java.util.ArrayList;

import com.br.model.dao.MemberDao;
import com.br.model.vo.Member;
import com.br.view.MemberMenu;

// Controller : 사용자가 요청한 기능에 대해서 처리
//				해당 메소드로 전달된 데이터 [가공처리] => Dao 메소드 호출
//				dao로 부터 결과 받고 성공/실패 판단 후 => 응답뷰(View) 지정
public class MemberController {
	
	public void insertMember(String userId, String userPwd, String userName, 
							 String gender, String age, String email,
							 String phone, String address, String hobby) {
		
		Member m = new Member(userId, userPwd, userName, gender, Integer.parseInt(age), email, phone, address, hobby);
		
		int result = new MemberDao().insertMember(m);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("성공적으로 회원 추가되었습니다.");
		} else {
			new MemberMenu().displayFail("회원 추가를 실패했습니다.");
		}
		
	}
	
	public void selectList() {
		
		ArrayList<Member> list = new MemberDao().selectList();
		
		if(list.isEmpty()) {
			new MemberMenu().displayNoData("전체 조회 결과가 없습니다.");
		} else {
			new MemberMenu().displayMemberList(list);
		}
	}
	
	public void selectByUserId(String userId) {
		
		Member m = new MemberDao().selectByUserId(userId);
		
		if(m == null) {
			new MemberMenu().displayNoData(userId + "에 해당하는 조회 결과가 없습니다.");
		} else {
			new MemberMenu().displayMember(m);
		}
		
	}
	
	public void selectByUserName(String keyword) {
		
		ArrayList<Member> list = new MemberDao().selectByUserName(keyword);
		
		if(list.isEmpty()) {
			new MemberMenu().displayNoData(keyword + "에 해당하는 조회 결과가 없습니다.");
		} else {
			new MemberMenu().displayMemberList(list);
		}
	}
	
	public void updateMember(String userId, String userPwd, String email, String phone, String address) {
		
		Member m = new Member();
		m.setUserId(userId);
		m.setUserPwd(userPwd);
		m.setEmail(email);
		m.setPhone(phone);
		m.setAddress(address);
		
		int result = new MemberDao().updateMember(m);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("성공적으로 회원 정보 변경되었습니다.");
		} else {
			new MemberMenu().displayFail("회원 정보 변경 실패");
		}
	}
	
	public void deleteMember(String userId) {
		int result = new MemberDao().deleteMember(userId);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("성공적으로 회원 탈퇴되었습니다.");
		} else {
			new MemberMenu().displayFail("회원 탈퇴 실패");
		}
	}

	
}