package com.br.controller;

import java.util.ArrayList;

import com.br.model.service.MemberService;
import com.br.model.vo.Member;
import com.br.view.MemberMenu;

public class MemberController {
	
	public void insertMember(String userId, String userPwd, String userName, 
							 String gender, String age, String email,
							 String phone, String address, String hobby) {
		
		Member m = new Member(userId, userPwd, userName, gender, Integer.parseInt(age), email, phone, address, hobby);
		
		int result = new MemberService().insertMember(m);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("성공적으로 회원추가 되었습니다.");
		} else {
			new MemberMenu().displayFail("회원 추가에 실패하였습니다.");
		}
	}
	
	public void selectList() {

		ArrayList<Member> list = new MemberService().selectList();
		
		if(list.isEmpty()) {
			new MemberMenu().displayNoData("전체 조회된 결과가 없습니다.");
		} else {
			new MemberMenu().displayMemberList(list);
		}
	}
	
	public void selectByUserId(String userId) {
		
		Member m = new MemberService().selectByUserId(userId);
		
		if(m == null) {
			new MemberMenu().displayNoData(userId + "로 조회된 결과가 없습니다.");
		} else {
			new MemberMenu().displayMember(m);
		}
	}
	
	public void selectByUserName(String keyword) {
		
		ArrayList<Member> list = new MemberService().selectByUserName(keyword);
		
		if(list.isEmpty()) {
			new MemberMenu().displayNoData("조회된 결과가 없습니다");
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
		
		int result = new MemberService().updateMember(m);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("성공적으로 회원 정보 변경 되었습니다.");
		} else {
			new MemberMenu().displayFail("회원 정보 변경 실패");
		}
	}
	
	public void deleteMember(String userId) {
		
		int result = new MemberService().deleteMember(userId);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("성공적으로 회원 탈퇴 되었습니다.");
		} else {
			new MemberMenu().displayFail("회원 탈퇴에 실패했습니다.");
		}
		
	}
	
	public void memberLogin(String userId, String userPwd) {
		
		Member m = new MemberService().memberLogin(userId, userPwd);
		
		if(m == null) {
			new MemberMenu().displayFail("로그인 실패");
		} else {
			new MemberMenu().displayMyPage(m);
		}
	}

	
}