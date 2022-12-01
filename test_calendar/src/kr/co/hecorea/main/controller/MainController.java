package kr.co.hecorea.main.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value={"/main/*"})
public class MainController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Value("#{config['geoserverPath']}") private String geoserverPath;
	
	/**
	 * 인트로 페이지.
	 * @return String
	*/
	@RequestMapping(value = {"/intro.do"})
	public String login(HttpServletRequest req, Model model) {
		return "main/intro";
	}
	
	
}
