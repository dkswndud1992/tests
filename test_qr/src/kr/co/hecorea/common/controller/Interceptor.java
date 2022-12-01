package kr.co.hecorea.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kr.co.hecorea.common.util.CommonDateUtil;

public class Interceptor implements HandlerInterceptor {
	/*
	 * Interceptor를 사용하기 위해선 config -> servlet-context 의 mvc:interceptors 주석을 제거.
	 */
	
	@Autowired
	HttpSession session;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public void afterCompletion(HttpServletRequest reqt, HttpServletResponse res, Object handler, Exception e) {
		// after (화면처리후)
		// System.out.println(getClass().getSimpleName()+".afterCompletion()");
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mav) {
		// post (화면처리전)
		// System.out.println(getClass().getSimpleName()+".postHandle()");
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
		// pre (컨트롤러 받기 전)
		String userIp = req.getHeader("X-Forwarded-For");
	    // String userReq = req.getServletPath(); 아래와 같음.
	    String requestUrl = req.getRequestURI();
	    String timeString = CommonDateUtil.selectNowDate();
	    String userId = (String) session.getAttribute("userId");
	    
	    if (userIp == null) userIp = req.getRemoteAddr();

		// 현재시간 + ip + 요청url 로그.
		log.info(timeString +"_"+ userIp +"_"+requestUrl+"_"+userId);
		
		
		return true;
	}

}
