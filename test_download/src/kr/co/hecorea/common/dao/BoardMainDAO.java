package kr.co.hecorea.common.dao;

import java.util.HashMap;

public interface BoardMainDAO {
	
	// 게시물 디테일.
	public HashMap<String, String> selectBoardDetail(String param);
	
}
