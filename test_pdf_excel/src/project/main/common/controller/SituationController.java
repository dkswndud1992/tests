package project.main.common.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.ibatis.session.SqlSession;

@Controller
@RequestMapping("/situation/*")
public class SituationController {
	@Autowired
	@Resource(name="sqlSessionMysql")
	private SqlSession sqlSessionMySql;
	
	@Autowired
	HttpSession session;
	
	/**
	 * mainSample
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/main.do")
	public String sampleMain() throws Exception {
        return "common/metdata/Situation";
	}
    
    
}
