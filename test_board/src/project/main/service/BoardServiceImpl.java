package project.main.service;

import java.util.List;

import javax.inject.Inject;
 
import org.springframework.stereotype.Service;

import project.main.dao.BoardDAO;
import project.main.dto.BoardDTO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
    private BoardDAO dao;
    
    @Override
    public List<BoardDTO> selectMember() throws Exception {
 
        return dao.selectMember();
    }
    
    @Override
    public BoardDTO detailBoard(Integer boardNo) throws Exception {
 
        return dao.selectBoard(boardNo);
    }
    
    @Override
    public BoardDTO writeBoard(String boardTitle, String boardContents, String boardDate) throws Exception {
 
        return dao.writeBoard(boardTitle, boardContents, boardDate);
    }
    
    @Override
    public BoardDTO deleteBoard(Integer boardNo) throws Exception {
 
        return dao.deleteBoard(boardNo);
    }
    
    @Override
    public BoardDTO updateBoard(String boardNo, String boardTitle, String boardContents) throws Exception {
 
        return dao.updateBoard(boardNo, boardTitle, boardContents);
    }

}
