package project.main.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
// import java.util.ArrayList;
import java.util.HashMap;

// import javax.inject.Inject;
// import java.util.Locale;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import project.main.dto.BoardDTO;
import project.main.service.BoardService;

// import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
// import org.apache.ibatis.session.SqlSession;
// import org.apache.log4j.Logger;
// import org.apache.poi.util.SystemOutLogger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;

//TODO : 쿠키사용하기.
//TODO : 간략하고 깔끔하게 코드 재작성.
//TODO : dto없애기.
@Controller
@RequestMapping("/*")
public class BoardController {
	
	private HttpSession session;
	
	@Autowired
    private BoardService service;
	
	@RequestMapping("/hello.do")
	public String basinRealList() throws Exception {
		System.out.println("hello test!");
		return "test/hello";
	}
	
	@RequestMapping(value="/loginPage.do")
	public String loginPage() throws Exception {
		Date date = new Date();
		
		System.out.println(date + "/loginPage.do");
		return "test/login";
	}
	
	@RequestMapping("/insert.do")
	public String insert() throws Exception {
		Date date = new Date();
		System.out.println(date + "/insert.do");
		return "test/insert";
	}
	
	// TODO : 서버시작시 호출되는 문제.
	@RequestMapping("/home.do")
    public String home(Model model) throws Exception{
		
		try{ //에러가 발생할 수 있는 코드
			List<BoardDTO> boardList = service.selectMember();
	        
	        model.addAttribute("boardList", boardList);
	        
	        Date date = new Date();
	        System.out.println(date + "/home.do");
	        return "test/home";
			
		    // throw new Exception(); //강제 에러 출력 
		}catch (Exception e){
		    //에러시 수행
		     e.printStackTrace(); //오류 출력(방법은 여러가지)
		     throw e; //최상위 클래스가 아니라면 무조건 던져주자
		}finally{
		    //무조건 수행
		}
		
    }
	
	@RequestMapping(value="/detail.do", method=RequestMethod.GET)
    public String detail(@RequestParam(value = "boardNo",required=false) Integer boardNo, Model model) throws Exception{
		// RequestParam 여러개 가능.
		try{
			if(boardNo == null) {
				List<BoardDTO> boardList = service.selectMember();
				model.addAttribute("boardList", boardList);
				return"test/redirect";
			}
			
			BoardDTO boardDetail = service.detailBoard(boardNo);
	        model.addAttribute("boardDetail", boardDetail);
	        
	        Date date = new Date();
	        
	        System.out.println(date+"/detail.do?boardNo="+boardNo);
	        return "test/detail";
			
		}catch (Exception e){
		    //에러시 수행
		     e.printStackTrace(); 
		     throw e; 
		}
		
    }
	
	@RequestMapping(value="/write.do", method=RequestMethod.POST)
    public String write(@RequestParam  HashMap<String, String> paramMap, Model model) throws Exception{
 
		try{
			String boardTitle = (String)paramMap.get("title");
			String boardContents = (String)paramMap.get("contents");
			// System.out.println("paramMap= "+paramMap.toString());
			Date date = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String boardDate = transFormat.format(date);
			List<BoardDTO> boardList = service.selectMember();
			model.addAttribute("boardList", boardList);
			if(boardTitle == null) {return"test/redirect";}
			
			// TODO : 엔터 넘기고 불러오기.
			BoardDTO boardDTO = service.writeBoard(boardTitle, boardContents, boardDate);
	        // TODO : 디테일페이지로.
	        System.out.println("boardDTO: {"+boardDTO+"}");
	        return "test/redirect";
			
		}catch (Exception e){
		    //에러시 수행
		     e.printStackTrace(); 
		     throw e; 
		}
		
    }
	
	@RequestMapping(value="/delete.do", method=RequestMethod.GET)
    public String delete(@RequestParam(value="no",required=false) Integer boardNo, Model model) throws Exception{
		try{
			Date date = new Date();

			service.deleteBoard(boardNo);
	        
			List<BoardDTO> boardList = service.selectMember();
	        
	        model.addAttribute("boardList", boardList);
			
	        System.out.println(date+"/delete.do?boardNo="+boardNo);
	        return "test/redirect";
			
		}catch (Exception e){
		    //에러시 수행
		     e.printStackTrace(); 
		     throw e; 
		}
		
    }
	
	@RequestMapping(value="/update.do", method=RequestMethod.POST)
    public String update(@RequestParam  HashMap<String, String> paramMap, Model model) throws Exception{
 
		try{
			
			String boardNo = (String)paramMap.get("no");
			String boardTitle = (String)paramMap.get("title");
			String boardContents = (String)paramMap.get("contents");
			// System.out.println("paramMap= "+paramMap.toString());
			Date date = new Date();
			List<BoardDTO> boardList = service.selectMember();
			model.addAttribute("boardList", boardList);
			
			if(boardTitle == null) {return"test/redirect";}
			// TODO : 해시로 넘기고 받기.
			service.updateBoard(boardNo, boardTitle, boardContents);
	        System.out.println(date+"/update.do?boardNo="+boardNo);
	        
	        return "test/redirect";
			
		}catch (Exception e){
		    //에러시 수행
		     e.printStackTrace(); 
		     throw e; 
		}
		
    }
	
	@RequestMapping(value="/updatePage.do", method=RequestMethod.POST)
    public String updatePage(@RequestParam  HashMap<String, String> paramMap, Model model) throws Exception{
 
		try{
			String boardNo = (String)paramMap.get("no");
			String boardTitle = (String)paramMap.get("title");
			String boardContents = (String)paramMap.get("contents");
			// System.out.println("paramMap= "+paramMap.toString());
			Date date = new Date();
			
			if(boardTitle == null) {
				List<BoardDTO> boardList = new ArrayList<BoardDTO>();
				boardList = service.selectMember();
				model.addAttribute("boardList", boardList);
				return"test/redirect";
			}
			
			model.addAttribute("boardNo", boardNo);
			model.addAttribute("boardTitle", boardTitle);
			model.addAttribute("boardContents", boardContents);
	        
	        System.out.println(date+"/updatePage.do?boardNo="+boardNo);
	        
	        return "test/update";
			
		}catch (Exception e){
		    //에러시 수행
		     e.printStackTrace(); 
		     throw e; 
		}
		
    }
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
    public String login(@RequestParam  HashMap<String, String> paramMap, Model model, HttpServletRequest request) throws Exception{
		
		
		try{
			String boardId = (String)paramMap.get("id");
			String boardPassword = (String)paramMap.get("password");
			String alertValue = "";
			
			Date date = new Date();
			int checkValue = loginCheck(boardId, boardPassword);
			
			switch(checkValue){
		        case 0: 
		        	session = request.getSession(true);
		        	session.setAttribute("id", boardId);
		        	alertValue = "관리자로 로그인 하셨습니다.";
		            break;
		        case 1:
		        	alertValue = "아이디를 확인해 주세요.";
		            break;
		        case 2 :
		        	alertValue = "비밀번호를 확인해 주세요.";
		            break;
		        default :
		            System.out.println("0, 1, 2 그 외의 숫자..");
			}
			
			model.addAttribute("alertValue",alertValue);
	        System.out.println(date + "login.do?" + boardId+"/"+alertValue+"/"+checkValue);
		}catch (Exception e){
		    //에러시 수행
		     e.printStackTrace(); 
		     throw e; 
		}
		
		return "test/redirect_alert";
    }
	
	@RequestMapping(value="/logout.do")
    public String logout(Model model) throws Exception{
		try{
			Date date = new Date();
			String alertValue = "";
			String id = (String)session.getAttribute("id");  

			if(id==null||id.equals("")){         
				alertValue = "잘못된 요청입니다.";  
			}else {
				session.invalidate();
				alertValue = "로그아웃 하셨습니다.";
			}
			model.addAttribute("alertValue", alertValue);
	        System.out.println(date + "logout.do?" +alertValue);
		}catch (Exception e){
		    //에러시 수행
		     e.printStackTrace(); 
		     throw e; 
		}
		
		return "test/redirect_alert";
    }
	
	public int loginCheck(String id, String password) throws Exception {
		int checkValue = 0;
		String dbId = "admin";
		String dbPassword = "heco";
		
		if(!id.equals(dbId)) {
			checkValue = 1;
		} else if (!password.equals(dbPassword)) {
			checkValue = 2;
		}
		
		return checkValue;
	}
	
	
}
