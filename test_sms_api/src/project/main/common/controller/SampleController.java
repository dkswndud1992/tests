package project.main.common.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import project.main.common.util.Common;
import project.main.common.dao.SampleDAO;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

@Controller
@RequestMapping("/sample/*")
public class SampleController {
	@Autowired
	@Resource(name="sqlSessionMysql")
	private SqlSession sqlSessionMySql;
	
	@Autowired
	HttpSession session;
	
	@Value("#{config['localPath']}") String localPath;
	@Value("#{config['samplePath']}") String samplePath;
	
	private final Logger log = Logger.getLogger(getClass());
	
	/**
	 * mainSample
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/mainSample.do")
	public String sampleMain() throws Exception {
		System.out.println(localPath+samplePath);
        return "common/sample/Sample";
	}
	
	/**
	 * selectSample
	 * @param HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	*/
    @RequestMapping(value = "/selectSample.do", method = RequestMethod.POST)
    public ModelAndView  selectSample(HttpServletRequest req) {
    	HashMap<String, String> sampleParam = new HashMap<String, String>();
		String selectParam = req.getParameter("selectParam") ==null ? "" : req.getParameter("selectParam");
		String returnParam = "";
		sampleParam.put("selectParam", selectParam);
		try {
			SampleDAO dao = sqlSessionMySql.getMapper(SampleDAO.class);
			returnParam = dao.selectSample(sampleParam);
		} catch(Exception e) {
			log.error(e.toString());
		}
		ModelAndView  mav = new ModelAndView ("common/sample/Sample");
		mav.addObject("Param", returnParam);
        return mav;
    }
    
    /**
	 *  selectSampleList
	 * @param HttpServletRequest
	 * @return ModelAndView
	 * @throws Exception
	*/
	@RequestMapping(value = "/selectSampleList.do", method = RequestMethod.GET)
	public ModelAndView selectSampleList(HttpServletRequest req) throws Exception {
		HashMap<String, Integer> sampleParam = new HashMap<String, Integer>();
		ArrayList<HashMap<String, String>> returnParamList = new ArrayList<HashMap<String, String>>();
		int startNum = req.getParameter("startNum")==""?0:Integer.parseInt(req.getParameter("startNum"));
		int limitNum = req.getParameter("limitNum")==""?10:Integer.parseInt(req.getParameter("limitNum"));
		sampleParam.put("startNum", startNum);
		sampleParam.put("limitNum", limitNum);
		try {
			SampleDAO dao = sqlSessionMySql.getMapper(SampleDAO.class);
			returnParamList = dao.selectSampleList(sampleParam);
		} catch(Exception e) {
			log.error(e.toString());
		}
		ModelAndView mav = new ModelAndView("common/sample/Sample");
		mav.addObject("ParamList", returnParamList);
		return mav;
	}
    
    /**
	 * insertSample
	 * @param HttpServletRequest
	 * @param Model
	 * @return String
	 * @throws Exception
	*/
    @RequestMapping(value = "/insertSample.do", method = RequestMethod.POST)
    public String insertSample(HttpServletRequest req, Model model) {
    	HashMap<String, String> sampleParam = new HashMap<String, String>();
		String insertParam = req.getParameter("insertParam") ==null?"": req.getParameter("insertParam");
		String insertEncryptionParam = "";
		Date nowDate = new Date();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String nowDateStr = transFormat.format(nowDate);
		// 암호화 sample
		insertEncryptionParam = Common.updateSHA256(insertParam);
		sampleParam.put("insertParam", insertParam);
		sampleParam.put("insertEncryptionParam", insertEncryptionParam);
		sampleParam.put("nowDateStr", nowDateStr);
		try {
			SampleDAO dao = sqlSessionMySql.getMapper(SampleDAO.class);
			dao.insertSample(sampleParam);
		} catch(Exception e) {
			log.error(e.toString());
		}
		model.addAttribute("Param", sampleParam.get("nowDateStr"));
		return "common/sample/Sample";
    }
    
    /**
	 * updateSample
	 * @param paramMap
	 * @return String
	 * @throws Exception
	*/
    @RequestMapping(value = "/updateSample.do", method = RequestMethod.POST)
    public String updateSample(@RequestParam HashMap<String, String> paramMap) {
		try {
			SampleDAO dao = sqlSessionMySql.getMapper(SampleDAO.class);
			dao.updateSample(paramMap);
		} catch(Exception e) {
			log.error(e.toString());
		}
		return "common/sample/Sample";
    }
    
    /**
	 * deleteSample
	 * @param HttpServletRequest
	 * @return String
	 * @throws Exception
	*/
    @RequestMapping(value = "/deleteSample.do", method = RequestMethod.POST)
    public String deleteSample(HttpServletRequest req) {
		String deleteParam = req.getParameter("deleteParam") ==null?"": req.getParameter("deleteParam");
		try {
			SampleDAO dao = sqlSessionMySql.getMapper(SampleDAO.class);
			dao.deleteSample(deleteParam);
		} catch(Exception e) {
			log.error(e.toString());
		}
		return "common/sample/Sample";
    }

	/**
	 * redirectSample
	 * @param RedirectAttributes
	 * @return RedirectView
	 * @throws Exception
	*/
    @RequestMapping(value = "/redirectSample.do")
    public RedirectView redirectSample(RedirectAttributes redirectAttributes) {
    	String Param = "sampleRedirectParam";
		RedirectView  rv = new RedirectView ("mainSample.do");
		redirectAttributes.addFlashAttribute("Param", Param);
        return rv;
    }
    
    /**
	 * sessionSample
	 * @param HttpServletRequest
	 * @return String
	 * @throws Exception
	*/
    @RequestMapping(value = "/sessionSample.do")
    public String sessionSample(HttpServletRequest req) {
		String sampleSession = (String)session.getAttribute("SampleKey");
		if (sampleSession == null) {
			session = req.getSession(true);
        	session.setAttribute("SampleKey", "SampleAttribute");
		}else {
			session.invalidate();
		}
        return "common/sample/Sample";
    }
    
}
