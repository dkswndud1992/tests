package project.main.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SampleInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest reqt, HttpServletResponse res, Object handler, Exception e)
			throws Exception {
		// after (화면처리후)
		System.out.println(getClass().getSimpleName()+".afterCompletion()");
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mav)
			throws Exception {
		// post (화면처리전)
		System.out.println(getClass().getSimpleName()+".postHandle()");
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		// pre (컨트롤러 받기 전)
		System.out.println(getClass().getSimpleName()+".preHandle()");
		if(req.getParameter("requestParam") == null) {
			return true;
		} else if (req.getParameter("requestParam").equals("")) {
			req.setAttribute("Param", "Interceptor");
			return true;
		} else return false;
	}

}
