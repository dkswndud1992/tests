package kr.co.hecorea.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.co.hecorea.common.dao.BoardContDAO;
import kr.co.hecorea.common.util.CommonStringUtil;

@Controller
@RequestMapping("/boardCont/*")
public class BoardContController {
	@Autowired
	@Resource(name="sqlSessionPostgre")
	private SqlSession sqlSessionPostgre;
	
	@Autowired
	HttpSession session;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Value("#{config['boardListlimit']}") private String boardListlimit;
	@Value("#{config['boardPagelimit']}") private String boardPagelimit;
	@Value("#{config['uploadPath']}") private String uploadPath;
	
	/**
	 *  게시물 등록.
	 * @param HttpServletRequest
	 * @param Model
	 * @return String
	*/
	@RequestMapping(value = "/insertBoard.do", method = RequestMethod.POST)
	public String insertBoard(HttpServletRequest req, Model model) {
		HashMap<String, String> param = new HashMap<String, String>();
		int nttCode = -999;
		String userId= "";
		
		// 파일 업로드 관련
		String filePath = uploadPath;
	    int size = 1024 * 1024 * 500; // 파일 사이즈 설정 : 500Mb
	    String fileNm = "";    //  서버에 중복된 파일 이름이 존재할 경우 처리하기 위해
	    String realFileNm = "";    // 업로드한 파일 이름
	    
	    // cos.jar라이브러리 클래스를 가지고 실제 파일을 업로드하는 과정
		try {
			BoardContDAO boardContDAO = sqlSessionPostgre.getMapper(BoardContDAO.class);
			
			if(Objects.isNull(session.getAttribute("userId"))) {
				model.addAttribute("nttCode", nttCode);
				return "jsonView";
				
			}else {
				userId = (String)session.getAttribute("userId");
			}
			
			// DefaultFileRenamePolicy 처리는 중복된 이름이 존재할 경우 처리할 때
	        // request, 파일저장경로, 용량, 인코딩타입, 중복파일명에 대한 정책 
	        MultipartRequest multi = new MultipartRequest(req, filePath, size, "utf-8", new DefaultFileRenamePolicy());
	        param.put("userId", userId);
			param.put("nttSj", multi.getParameter("nttSj").replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;").replaceAll("'", "&apos;"));
			param.put("nttCn", multi.getParameter("nttCn").replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;").replaceAll("'", "&apos;"));
			
			nttCode = boardContDAO.insertBoard(param);			
			param.put("nttCode", Integer.toString(nttCode));
	        
	        // 전송한 전체 파일이름들을 가져온다.
	        Enumeration<?> files = multi.getFileNames();
	        
	        if(!files.hasMoreElements()) {
	        	model.addAttribute("nttCode", nttCode);
				return "jsonView";
	        }
	        
	        String str = (String)files.nextElement();
	        
	        //파일명 중복이 발생했을 때 정책에 의해 뒤에 1,2,3 처럼 숫자가 붙어 고유 파일명을 생성한다.
	        // 이때 생성된 이름을 FilesystemName이라고 하여 그 이름 정보를 가져온다. (중복 처리)
	        fileNm = multi.getFilesystemName(str);
	        //실제 파일 이름을 가져온다.
	        realFileNm = multi.getOriginalFileName(str);
			
			// DB 파일 저장.
	        param.put("fileNm", fileNm);
	        param.put("realFileNm", realFileNm);
	        
	        boardContDAO.insertFile(param);
			
		} catch (NullPointerException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		
		model.addAttribute("nttCode", nttCode);
		return "jsonView";
	}
	
	
	/**
	 *  게시물 수정.
	 * @param HttpServletRequest
	 * @param Model
	 * @return String
	*/
	@RequestMapping(value = "/updateBoard.do", method = RequestMethod.POST)
	public String updateBoard(HttpServletRequest req, Model model) {
		HashMap<String, String> param = new HashMap<String, String>();
		String nttCode = "-999";
		
		// 파일 업로드 관련
		String filePath = uploadPath;
	    
	    int size = 1024 * 1024 * 500; // 파일 사이즈 설정 : 500Mb
	    String fileNm = "";    //  서버에 중복된 파일 이름이 존재할 경우 처리하기 위해
	    String realFileNm = "";    // 업로드한 파일 이름
	    
	    // cos.jar라이브러리 클래스를 가지고 실제 파일을 업로드하는 과정
		try {
			BoardContDAO boardContDAO = sqlSessionPostgre.getMapper(BoardContDAO.class);
			
			// DefaultFileRenamePolicy 처리는 중복된 이름이 존재할 경우 처리할 때
	        // request, 파일저장경로, 용량, 인코딩타입, 중복파일명에 대한 정책
	        MultipartRequest multi = new MultipartRequest(req, filePath, size, "utf-8", new DefaultFileRenamePolicy());
	        nttCode = multi.getParameter("nttCode");
			param.put("nttSj", multi.getParameter("nttSj").replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;").replaceAll("'", "&apos;"));
			param.put("nttCn", multi.getParameter("nttCn").replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;").replaceAll("'", "&apos;"));
			param.put("nttCode", multi.getParameter("nttCode"));
			param.put("fileCode", multi.getParameter("fileCode"));
			
			boardContDAO.updateBoard(param);	
			
			if("true".equals(multi.getParameter("removeBefore"))) {
	        	boardContDAO.deleteFile(param);
	        }
			
	        // 전송한 전체 파일이름들을 가져온다.
	        Enumeration<?> files = multi.getFileNames();
	        
	        // 올릴 파일이 없을 경우
	        if(!files.hasMoreElements()) {
	        	model.addAttribute("nttCode", nttCode);
	    		
	    		return "jsonView";
	        }
	        
	        String str = (String)files.nextElement();
	        
	        //파일명 중복이 발생했을 때 정책에 의해 뒤에 1,2,3 처럼 숫자가 붙어 고유 파일명을 생성한다.
	        // 이때 생성된 이름을 FilesystemName이라고 하여 그 이름 정보를 가져온다. (중복 처리)
	        fileNm = multi.getFilesystemName(str);
	        //실제 파일 이름을 가져온다.
	        realFileNm = multi.getOriginalFileName(str);
			
			// DB 파일 저장.
	        param.put("fileNm", fileNm);
	        param.put("realFileNm", realFileNm);
	        
	        boardContDAO.insertFile(param);
			
		} catch (NullPointerException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		} 
		
		model.addAttribute("nttCode", nttCode);
		
		return "jsonView";
	}
	
	
	/**
	 * 게시물 리스트 개수.
	 * @return String
	*/
	@RequestMapping(value = "/selectBoardListCnt.do", method = RequestMethod.POST)
	public String selectBoardListCnt(HttpServletRequest req, Model model) {
		String searchType = CommonStringUtil.updateNullToString(req.getParameter("searchType"));
		String searchValue = CommonStringUtil.updateNullToString(req.getParameter("searchValue"));
		
		int boardListCnt = 0;
		HashMap<String, String> param = new HashMap<String, String>();
		
		param.put("searchType", searchType);
		param.put("searchValue", searchValue);
		
		try {
			BoardContDAO boardContDAO = sqlSessionPostgre.getMapper(BoardContDAO.class);
			boardListCnt = boardContDAO.selectBoardListCnt(param);
			
		} catch (NullPointerException e) {
			log.error(e.toString());
		}
		
		model.addAttribute("boardListCnt", boardListCnt);
		model.addAttribute("limitNum", boardListlimit);
		model.addAttribute("pageLimit", boardPagelimit);
		
		return "jsonView";
	}
	
	
	/**
	 * 게시물 리스트.
	 * @return String
	*/
	@RequestMapping(value = "/selectBoardList.do", method = RequestMethod.POST)
	public String selectBoardList(HttpServletRequest req, Model model) {
		String searchType = CommonStringUtil.updateNullToString(req.getParameter("searchType"));
		String searchValue = CommonStringUtil.updateNullToString(req.getParameter("searchValue"));
		String pageNum = CommonStringUtil.updateNullToString(req.getParameter("pageNum"));
		String limitNum = boardListlimit;
		String startNum = "";
		int startNumInt = 0;
		int pageNumInt = 0;
		int limitNumInt = 0;
		
		ArrayList<HashMap<String, String>> boardList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> param = new HashMap<String, String>();
		
		param.put("searchType", searchType);
		param.put("searchValue", searchValue);
		param.put("pageNum", pageNum);
		param.put("limitNum", limitNum);
		
		
		try {
			BoardContDAO boardContDAO = sqlSessionPostgre.getMapper(BoardContDAO.class);
			
			// 파라미터 없을경우 페이지 번호 1.
			if(param.get("pageNum").equals("")) {
				param.put("pageNum", "1");
			}
			
			// 계산하기 위해 숫자로 변환.
			limitNumInt = Integer.parseInt(param.get("limitNum"));
			pageNumInt =  Integer.parseInt(param.get("pageNum"));
			
			// 시작 번호 계산 및 변환.
			startNumInt = limitNumInt * (pageNumInt - 1);
			startNum = String.valueOf(startNumInt);
			
			param.put("startNum", startNum);
			boardList = boardContDAO.selectBoardList(param);
			
		} catch(NumberFormatException e) {
			log.error(e.toString());
		}
		
		model.addAttribute("boardList", boardList);
		
		return "jsonView";
	}
	

	/**
	 *  게시물 삭제.
	 * @param HttpServletRequest
	 * @return String
	*/
	@RequestMapping(value = "/deleteBoard.do", method = RequestMethod.POST)
	public String deleteBoard(HttpServletRequest req) {
		String userId = CommonStringUtil.updateNullToString((String)session.getAttribute("userId"));
		String nttCode = CommonStringUtil.updateNullToString(req.getParameter("nttCode"));
		
		HashMap<String, String> param = new HashMap<String, String>();
		
		param.put("userId", userId);
		param.put("nttCode", nttCode);
		
		try {
			BoardContDAO boardContDAO = sqlSessionPostgre.getMapper(BoardContDAO.class);
			boardContDAO.deleteBoard(param);
			
		} catch (NullPointerException e) {
			log.error(e.toString());
		} 
		
		return "jsonView";
	}
	
	
	/**
	 * 파일 다운로드.
	 * @return String
	*/
	@RequestMapping(value = "/selectFile.do", method = RequestMethod.POST)
	public String selectFile(HttpServletRequest req, Model model) {
		String fileCode = CommonStringUtil.updateNullToString(req.getParameter("index"));
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		if(fileCode.equals("") || fileCode.equals("-999")) {
			return "redirect:/main/intro.do";
		}
		
		try {
			BoardContDAO boardContDAO = sqlSessionPostgre.getMapper(BoardContDAO.class);
			result = boardContDAO.selectFile(fileCode);
			
			result.put("fileCours", uploadPath);
		} catch (NullPointerException e) {
			log.error(e.toString());
		}
		
		model.addAttribute("filePath", result.get("fileCours"));
		model.addAttribute("fileName", result.get("fileNm"));
		model.addAttribute("originalFileName", result.get("realFileNm"));
		
		return "common/board/board_download";
	}
	
}
