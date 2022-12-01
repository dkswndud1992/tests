package kr.co.hecorea.common.dao;

import java.util.HashMap;

public interface UserMainDAO {
	
	// 사용자 정보 확인.
	public HashMap<String, String> selectUserInfo(String param);
	
}
