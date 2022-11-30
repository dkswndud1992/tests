package project.main.common.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import project.main.common.dao.SampleDAO;

@Controller
@RequestMapping("/sampleCont/*")
public class SampleContController {
	@Autowired
	@Resource(name="sqlSessionMysql")
	private SqlSession sqlSessionMySql;
	/* 오라클 또는 포스트그레 사용 할 경우
	@Autowired
	@Resource(name="sqlSessionOracle")
	private SqlSession sqlSessionOracle;
	
	@Autowired
	@Resource(name="sqlSessionPostgre")
	private SqlSession sqlSessionPostgre;
	*/
	private final Logger log = Logger.getLogger(getClass());
	
    /**
	 *  ajaxSample
	 * @param HttpServletRequest
	 * @param Model
	 * @return jsonView
	 * @throws Exception
	*/
	@RequestMapping(value = "/ajaxSample.do", method = RequestMethod.POST)
	public String ajaxSample(HttpServletRequest req, Model model) throws Exception {
		String SampleParam = req.getParameter("Param")==null?"":req.getParameter("Param");
		String returnParam = "";
		returnParam = SampleParam+"retrun";
		model.addAttribute("Param", returnParam);
		return "jsonView";
	}
	
	/**
	 *  dbTestSample
	 * @param HttpServletRequest
	 * @param Model
	 * @return jsonView
	 * @throws Exception
	*/
	@RequestMapping(value = "/dbTestSample.do", method = RequestMethod.POST)
	public String dbTestSample(HttpServletRequest req, Model model) throws Exception {
		String dbValue = req.getParameter("Param")==null?"":req.getParameter("Param");
		int connectValue = 0;
		SampleDAO dao = sqlSessionMySql.getMapper(SampleDAO.class);
		try {
			/* 오라클 또는 포스트그레 사용 할 경우
			switch (dbValue) {
			case "oracle":
				dao = sqlSessionOracle.getMapper(SampleDAO.class);
				break;
			case "mysql":
				dao = sqlSessionMySql.getMapper(SampleDAO.class);
				break;
			case "postgresql":
				dao = sqlSessionPostgre.getMapper(SampleDAO.class);
				break;

			default:
				dao = sqlSessionMySql.getMapper(SampleDAO.class);
				break;
			}
			*/
			connectValue = dao.dbTestSample(dbValue);
		} catch(Exception e) {
			log.error(e.toString());
		}
		model.addAttribute("Param", connectValue);
		return "jsonView";
	}
}
