package kr.co.hecorea.common.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.hecorea.common.dao.BoardMainDAO;
import kr.co.hecorea.common.util.CommonStringUtil;

@Controller
@RequestMapping("/boardMain/*")
public class BoardMainController {
	@Autowired
	@Resource(name="sqlSessionPostgre")
	private SqlSession sqlSessionPostgre;
	
	@Autowired
	HttpSession session;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired private UserMainController userMainController;
	
	/**
	 * 홍수위험지도 자료실 홍수위험지도 페이지.
	 * @return String
	*/
	@RequestMapping(value = "/list.do")
	public String archiveMap(HttpServletRequest req, Model model) {
		
		// 사용자 분기 메소드.
		HashMap<String,String> returnParam = userMainController.selectDivideUser( 3, "common/board/board_list"); 
		
		return returnParam.get("returnUrl");
	}
	
	
	/**
	 * 게시판 디테일 페이지.
	 * @return String
	*/
	@RequestMapping(value = "/detail.do", method = RequestMethod.GET)
	public String detail(HttpServletRequest req, Model model) {
		String nttCode = CommonStringUtil.updateNullToString(req.getParameter("index"));
		HashMap<String, String> result = new HashMap<String, String>();
		
		// 사용자 분기 메소드.
		HashMap<String,String> returnParam = userMainController.selectDivideUser(3, "common/board/board_detail"); 
		
		try {
			BoardMainDAO boardMainDAO = sqlSessionPostgre.getMapper(BoardMainDAO.class);
			
			// 값이 비였을경우.
			if(nttCode.isEmpty()) {
				return "redirect:/main/intro.do";
			}
			
			result = boardMainDAO.selectBoardDetail(nttCode);
			
		} catch (NullPointerException e) {
			log.error(e.toString());
		}
		
		model.addAttribute("nttCode", nttCode);
		model.addAttribute("result", result);
		
        return returnParam.get("returnUrl");
	}
	
	
	/**
	 * 게시판 홍수위험지도 작성 페이지.
	 * @return String
	*/
	@RequestMapping(value = "/write.do")
	public String writeMap(HttpServletRequest req, Model model) {
		// 사용자 분기 메소드.
		HashMap<String,String> returnParam = userMainController.selectDivideUser(1, "common/board/board_write"); 
		
		return returnParam.get("returnUrl");
	}
	
	
	/**
	 * 게시판 수정 페이지.
	 * @return String
	*/
	@RequestMapping(value = "/update.do", method = RequestMethod.GET)
	public String update(HttpServletRequest req, Model model) {
		HashMap<String, String> result = new HashMap<String, String>();
		
		// 사용자 분기 메소드.
		HashMap<String,String> returnParam = userMainController.selectDivideUser(1, "common/board/board_write"); 
		
		try {
			BoardMainDAO boardMainDAO = sqlSessionPostgre.getMapper(BoardMainDAO.class);
			String nttCode = CommonStringUtil.updateNullToString(req.getParameter("index"));
			
			// 값이 비였을경우.
			if(nttCode.isEmpty()) {
				return "redirect:/main/intro.do";
			}
			
			result = boardMainDAO.selectBoardDetail(nttCode);
			model.addAttribute("nttCode", nttCode);
			
		} catch (NullPointerException e) {
			log.error(e.toString());
		}
		
		model.addAttribute("result", result);
		
		return returnParam.get("returnUrl");
	}
	
	
}
