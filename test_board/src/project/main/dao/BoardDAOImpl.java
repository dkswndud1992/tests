package project.main.dao;
import java.util.List;

import javax.inject.Inject;
 
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import project.main.dto.BoardDTO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
    private SqlSession sqlSession;
    
    private static final String Namespace = "project.main.dao.mapper.BoardMapper";
    
    @Override
    public List<BoardDTO> selectMember() throws Exception {
    	// TODO : 리턴시 객체생성해 변수로 넘기기.
        return sqlSession.selectList(Namespace+".listBoard");
    }

    @Override
    public BoardDTO selectBoard(Integer boardNo) throws Exception {
 
        return sqlSession.selectOne(Namespace+".selectBoard", boardNo);
    }
    
    @Override
    public BoardDTO writeBoard(String boardTitle, String boardContents, String boardDate) throws Exception {
    	BoardDTO boardDTO = new BoardDTO();
    	boardDTO.setBoardTitle(boardTitle);
    	boardDTO.setBoardContents(boardContents);
    	boardDTO.setBoardDate(boardDate);
    	try {
    		BoardDTO test = sqlSession.selectOne(Namespace+".writeBoard", boardDTO);
    	
    		System.out.println("test: "+test);
    	}catch(Exception e) {
    		System.out.println("test error: "+e.getMessage());
    	}
    	
        
    	return boardDTO;
    }
    
    @Override
    public BoardDTO deleteBoard(Integer boardNo) throws Exception {
        return sqlSession.selectOne(Namespace+".deleteBoard", boardNo);
    }
    
    @Override
    public BoardDTO updateBoard(String boardNo, String boardTitle, String boardContents) throws Exception {
    	BoardDTO boardDTO = new BoardDTO();
    	boardDTO.setBoardNo(boardNo);
    	boardDTO.setBoardTitle(boardTitle);
    	boardDTO.setBoardContents(boardContents);
        return sqlSession.selectOne(Namespace+".updateBoard", boardDTO);
    }
    
}
