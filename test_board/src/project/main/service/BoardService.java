package project.main.service;

import java.util.List;

import project.main.dto.BoardDTO;

public interface BoardService {
	public List<BoardDTO> selectMember() throws Exception;
	public BoardDTO detailBoard(Integer boardNo) throws Exception;
	public BoardDTO writeBoard(String boardTitle, String boardContents, String boardDate) throws Exception;
	public BoardDTO deleteBoard(Integer boardNo) throws Exception;
	public BoardDTO updateBoard(String boardNo, String boardTitle, String boardContents) throws Exception;
}
