package kr.co.hecorea.common.controller;

import java.util.ArrayList;
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

import kr.co.hecorea.common.dao.UserMainDAO;


@Controller
@RequestMapping(value={"/userMain/*"})
public class UserMainController {
	@Autowired
	@Resource(name="sqlSessionPostgre")
	private SqlSession sqlSessionPostgre;
	
	@Autowired
	HttpSession session;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * 로그인 페이지.
	 * @return String
	*/
	@RequestMapping(value = {"/login.do"})
	public String login(HttpServletRequest req, Model model) {
		// 사용자 분기 메소드.
		HashMap<String,String> returnParam = selectDivideUser(2, "common/user/user_login"); 
		
		return returnParam.get("returnUrl");
	}
	
	
	/**
	 * 회원정보 수정 페이지.
	 * @return String
	*/
	@RequestMapping(value = "/modify.do")
	public String modify(HttpServletRequest req, Model model) {
		String userId = (String) session.getAttribute("userId");
		
		// 사용자 분기 메소드.
		HashMap<String,String> returnParam = selectDivideUser(1, "common/user/user_modify"); 
		
		HashMap<String, String> userInfo = new HashMap<String, String>();
		ArrayList<HashMap<String, String>> affiliationList = new ArrayList<HashMap<String, String>>();
		
		try {
			UserMainDAO userMainDAO = sqlSessionPostgre.getMapper(UserMainDAO.class);
			
			userInfo = userMainDAO.selectUserInfo(userId);
			
		} catch (NullPointerException e) {
			log.error(e.toString());
		}
		
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("affiliationList", affiliationList);
		
		return returnParam.get("returnUrl");
	}
	
	
	/**
	 * 회원 비밀번호 변경 페이지.
	 * @return String
	*/
	@RequestMapping(value = "/modifyPassword.do")
	public String modifyPassword(HttpServletRequest req, Model model) {
		// 사용자 분기 메소드.
		HashMap<String,String> returnParam = selectDivideUser(1, "common/user/user_password"); 
		
		return returnParam.get("returnUrl");
	}
	
	
	/**
	 * 회원가입 페이지.
	 * @return String
	*/
	@RequestMapping(value = "/regist.do")
	public String regist(HttpServletRequest req, Model model) {
		ArrayList<HashMap<String, String>> affiliationList = new ArrayList<HashMap<String, String>>();
		
		// 사용자 분기 메소드.
		HashMap<String,String> returnParam = selectDivideUser(2, "common/user/user_regist"); 
		
		model.addAttribute("affiliationList", affiliationList);
		
		return returnParam.get("returnUrl");
	}
	
	
	/**
	 * 사용자 정보 세션에 따른 분류 처리
	 * <pre>
	 * ex)
	 * HashMap<String, String> returnParam = CommonUserUtil.selectDivideUser(1, confirmUrl); 
	 * 1일때,(로그인 전용 페이지)
	 * - 사용자권한 맞지않으면 인트로페이지
	 * - 사용자 정보가 있으면 'A.do' 이동(회원정보 수정페이지, 비밀번호 수정페이지)
	 * - 사용자 정보가 없으면 '/userMain/login.do' 이동(로그인페이지)
	 * 2일때,(비로그인 전용 페이지)
	 * - 사용자권한 맞지않으면 인트로페이지
	 * - 사용자 정보가 있으면 '/introMain/intro.do' 이동(인트로페이지)
	 * - 사용자 정보가 없으면 'B.do' 이동(로그인페이지, 회원가입페이지)
	 * 3일때,(로그인/비로그인 분기 페이지)
	 * - 사용자권한 맞지않으면 인트로페이지
	 * - 사용자 정보가 있으면 'A.do?param=1' 이동
	 * - 사용자 정보가 없으면 'A.do?param=2' 이동
	 * </pre>
	 * @author  안주영
	 * @version 1.0
	 * @param <b>divideFlag (int)</b> ex) 1
	 * @param <b>confirmUrl (String)</b> ex) 1
	 * @return <b>HashMap<String, String> returnParam</b> ex) {"returnUrl":"main/public/public_intro", "loginYn":"y"}
	*/
	public HashMap<String, String> selectDivideUser(int divideFlag, String confirmUrl) {
		String loginPageUrl = "redirect:/userMain/login.do";
		String introPageUrl = "redirect:/main/intro.do";
		String returnUrl = introPageUrl;
		HashMap<String, String> returnParam = new HashMap<String, String>();
		String userId = (String) session.getAttribute("userId");
		String isLogin = "n";
		
		try {
			// 로그인 했는지 확인.
			if(!"".equals(userId) && userId != null){
				isLogin = "y";
			}
			
			// 분기처리.
			switch (divideFlag) {
			// 	1일때,(로그인 전용 페이지)
			case 1:
				returnUrl = isLogin.equals("y")? confirmUrl: loginPageUrl;
				break;
				
			// 2일때,(비로그인 전용 페이지)
			case 2:
				returnUrl = isLogin.equals("n")? confirmUrl: introPageUrl;
				break;
				
			// 	3일때,(로그인/비로그인 분기 페이지)
			case 3:
				returnUrl = confirmUrl;
				break;
				
			default:
				break;
			}
			
			
		}catch(NullPointerException e) {
			log.error(e.toString());
			// e.printStackTrace();
			
		}finally {
			returnParam.put("loginYn", isLogin);
			returnParam.put("returnUrl", returnUrl);
		}
		
		return returnParam;
	}
	
}
