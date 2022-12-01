package kr.co.hecorea.common.dao;

import java.util.HashMap;

public interface UserContDAO {
	
	// 로그인 요청.
	public HashMap<String, String> selectUserConfirm(HashMap<String, String> param);
	
	// 아이디 중복 확인.
	public String selectUserIdConfirm(String param);
	
	// 회원가입.
	public void insertUserInfo(HashMap<String, String> param);
	
	// 사용자 정보 수정.
	public void updateUserInfo(HashMap<String, String> param);
	
	// 사용자 비밀번호 수정.
	public void updateUserPw(HashMap<String, String> param);
	
}
