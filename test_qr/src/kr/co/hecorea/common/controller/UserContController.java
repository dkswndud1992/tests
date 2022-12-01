package kr.co.hecorea.common.controller;

import java.util.HashMap;

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

import kr.co.hecorea.common.dao.UserContDAO;
import kr.co.hecorea.common.util.CommonStringUtil;

@Controller
@RequestMapping("/userCont/*")
public class UserContController {
	@Autowired
	@Resource(name="sqlSessionPostgre")
	private SqlSession sqlSessionPostgre;
	
	@Autowired
	HttpSession session;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Value("#{config['cryptoKey']}") private String cryptoKey;
	
    /**
	 *  로그인 세션 생성.
	 * @param HttpServletRequest
	 * @param RedirectAttributes
	 * @return String
	*/
	@RequestMapping(value = "/insertUserSession.do", method = RequestMethod.POST)
	public String insertUserSession(HttpServletRequest req, Model model) {
		String userId = CommonStringUtil.updateNullToString(req.getParameter("userId"));
		String userPwBefore = CommonStringUtil.updateNullToString(req.getParameter("userPw"));
		
		HashMap<String, String> param = new HashMap<String, String>();
		HashMap<String, String> returnParam = new HashMap<String, String>();
		HashMap<String, String> userConfm = new HashMap<String, String>();
		int failCount = session.getAttribute("fail") != null ?  (int) session.getAttribute("fail"): 0;
		
		param.put("userId", userId);
		
		// 비밀번호 암호화(이중).
		String userPwAfter1 = CommonStringUtil.updateAES256(userPwBefore, cryptoKey);
		String userPwAfter2 = CommonStringUtil.updateAES256(userPwAfter1, cryptoKey);
		
		try{
			UserContDAO userContDAO = sqlSessionPostgre.getMapper(UserContDAO.class);
			
			// 5회 이상 로그인 실패 시 막기.
			if (failCount == 5) {
				returnParam.put("login", "false");
				returnParam.put("msg", "5회 이상 로그인에 실패하였습니다. 일정 시간이 지난 후 다시 로그인 하세요.");
				model.addAllAttributes(returnParam);
				
				return "jsonView";
			}
			
			param.put("userPw", userPwAfter2);
			
			userConfm = userContDAO.selectUserConfirm(param);
			
			// 로그인 프로세스.
			if(!userConfm.get("userNm").equals("-")) {
				returnParam.put("login", "true");
				
				// 세션 있다면 삭제.
				session.invalidate();
				
				// 세션시간 30분
				session.setMaxInactiveInterval(30*60);
				session.setAttribute("userId", userId);
				session.setAttribute("userNm", userConfm.get("userNm"));
				
			}else {
				// 실패시 세션 시간 5분.
				session.setMaxInactiveInterval(5*60);
				session.setAttribute("fail",failCount + 1);
				
				returnParam.put("login", "false");
				returnParam.put("msg", "가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.");
			}
			
		}catch(NullPointerException e) {
			log.error(e.toString());
		}
		
		model.addAllAttributes(returnParam);
		
		return "jsonView";
	}
	
	
	/**
	 *  로그인 세션 삭제.
	 * @return String
	*/
	@RequestMapping(value = "/deleteUserSession.do")
	public String deleteUserSession() {
		try {
			session.invalidate();
			
		}catch (IllegalStateException e) {
			log.error(e.toString());
		}
		
		return "redirect:/main/intro.do";
	}
	
	
	/**
	 *  아이디 중복 확인.
	 * @param HttpServletRequest
	 * @param RedirectAttributes
	 * @return String
	*/
	@RequestMapping(value = "/selectUserIdConfirm.do", method = RequestMethod.POST)
	public String selectUserIdConfirm(HttpServletRequest req, Model model) {
		String userId = CommonStringUtil.updateNullToString(req.getParameter("userId"));
		
		try {
			UserContDAO userContDAO = sqlSessionPostgre.getMapper(UserContDAO.class);
			model.addAttribute("useYn", userContDAO.selectUserIdConfirm(userId));
			
		} catch (NullPointerException e) {
			log.error(e.toString());
		}
		
		return "jsonView";
	}
	
	
	/**
	 *  회원가입.
	 * @param HttpServletRequest
	 * @param RedirectAttributes
	 * @return String
	*/
	@RequestMapping(value = "/insertUserInfo.do", method = RequestMethod.POST)
	public String insertUserInfo(HttpServletRequest req, Model model) {
		String userId = CommonStringUtil.updateNullToString(req.getParameter("userId"));
		String userNm = CommonStringUtil.updateNullToString(req.getParameter("userNm"));
		String userPwBefore = CommonStringUtil.updateNullToString(req.getParameter("userPwd"));
		
		HashMap<String, String> param = new HashMap<String, String>();
		
		// 비밀번호 암호화.
		String userPwAfter1 = CommonStringUtil.updateAES256(userPwBefore, cryptoKey);
		String userPwAfter2 = CommonStringUtil.updateAES256(userPwAfter1, cryptoKey);
		
		param.put("userId", userId);
		param.put("userNm", userNm);
		param.put("userPw", userPwAfter2);
		
		try {
			UserContDAO userContDAO = sqlSessionPostgre.getMapper(UserContDAO.class);
			userContDAO.insertUserInfo(param);
			
		} catch (NullPointerException e) {
			log.error(e.toString());
		}
		
		return "jsonView";
	}
	
	
	/**
	 *  사용자정보 수정.
	 * @param HttpServletRequest
	 * @param Model
	 * @return String
	*/
	@RequestMapping(value = "/updateUserInfo.do", method = RequestMethod.POST)
	public String updateUserInfo(HttpServletRequest req, Model model) {
		String userId = (String) session.getAttribute("userId");
		String userNm = CommonStringUtil.updateNullToString(req.getParameter("userNm"));
		
		HashMap<String, String> param = new HashMap<String, String>();
		
		param.put("userId", userId);
		param.put("userNm", userNm);
		
		try {
			UserContDAO userContDAO = sqlSessionPostgre.getMapper(UserContDAO.class);
			userContDAO.updateUserInfo(param);
			
			session.setAttribute("userNm", param.get("userNm"));
			
		} catch (NullPointerException e) {
			log.error(e.toString());
		}
		
		return "jsonView";
	}
	
	
	/**
	 *  비밀번호 수정.
	 * @param HttpServletRequest
	 * @param Model
	 * @return String
	*/
	@RequestMapping(value = "/updateUserPw.do", method = RequestMethod.POST)
	public String updateUserPw(HttpServletRequest req, Model model) {
		String userId = (String) session.getAttribute("userId");
		String userPwBefore = CommonStringUtil.updateNullToString(req.getParameter("userPwd"));
		
		HashMap<String, String> param = new HashMap<String, String>();
		
		// 비밀번호 암호화.
		String userPwAfter1 = CommonStringUtil.updateAES256(userPwBefore, cryptoKey);
		String userPwAfter2 = CommonStringUtil.updateAES256(userPwAfter1, cryptoKey);
		
		param.put("userId", userId);
		param.put("userPw", userPwAfter2);
		
		try {
			UserContDAO userContDAO = sqlSessionPostgre.getMapper(UserContDAO.class);
			userContDAO.updateUserPw(param);
			
		} catch (NullPointerException e) {
			log.error(e.toString());
		}
		
		return "jsonView";
	}
	
}
