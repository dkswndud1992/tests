package kr.co.hecorea.common.dao;

import java.util.ArrayList;
import java.util.HashMap;

public interface BoardContDAO {

	// 게시물 등록.
	public int insertBoard(HashMap<String, String> param);
	
	// 게시물 수정.
	public void updateBoard(HashMap<String, String> param);
	
	// 게시물 리스트 개수.
	public int selectBoardListCnt(HashMap<String, String> param);
	
	// 게시물 리스트.
	public ArrayList<HashMap<String, String>> selectBoardList(HashMap<String, String> param);
	
	// 게시물 삭제.
	public void deleteBoard(HashMap<String, String> param);
	
	// 파일 업로드.
	public void insertFile(HashMap<String, String> param);
	
	// 파일 다운로드.
	public HashMap<String, String> selectFile(String param);
	
	// 파일 정보 수정.
	public void updateFile(HashMap<String, String> param);
	
	// 파일 정보 삭제.
	public void deleteFile(HashMap<String, String> param);
	
}
