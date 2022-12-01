package project.main.dao;

// import java.util.ArrayList;
// import java.util.HashMap;

import java.util.List;

import project.main.dto.BoardDTO;

public interface BoardDAO {
	

	// public ArrayList<HashMap<String, String>> selectBasinRealList(HashMap<String, String> param) throws Exception;

	public List<BoardDTO> selectMember() throws Exception;
	public BoardDTO selectBoard(Integer boardNo) throws Exception;
	public BoardDTO writeBoard(String boardTitle, String boardContents, String boardDate) throws Exception;
	public BoardDTO deleteBoard(Integer boardNo) throws Exception;
	public BoardDTO updateBoard(String boardNo, String boardTitle, String boardContents) throws Exception;

}
