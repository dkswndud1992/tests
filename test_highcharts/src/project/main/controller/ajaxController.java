package project.main.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import project.main.dao.ajaxDAO;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

@Controller
@RequestMapping("/*")
public class ajaxController {
	
	@Autowired
	@Resource(name="sqlSessionMysql")
	private SqlSession sqlSessionMySql;
	
	@Autowired
	HttpSession session;
	
	private final Logger log = Logger.getLogger(getClass());
	
	
	// main페이지 테이블종류 출력
	@RequestMapping("/tableComp.do")
	public ModelAndView main() throws Exception {
		ArrayList<HashMap<String, String>> tableList = new ArrayList<HashMap<String, String>>();
		
		try {
			ajaxDAO dao = sqlSessionMySql.getMapper(ajaxDAO.class);
			tableList = dao.selectTable();
			
		} catch(Exception e) {
			log.error(e.toString());
		}
		
	    ModelAndView mav = new ModelAndView("main");
        mav.addObject("tableList", tableList);
        return mav;
	    
	}
	
	// ajax 테스트
	@RequestMapping("/test.ajax")
	public String test() throws Exception {
		System.out.println("json!");
	    return "json";
	}
	
	
	/**
	 *  컬럼명, data 리스트
	 * @param params
	 * @param req
	 * @return jsonView
	 * @throws Exception
	*/
	@RequestMapping(value = "/dbList.ajax", method = RequestMethod.POST)
	public String insertSessionDataInfo(HttpServletRequest req, Model model) throws Exception {
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> columnList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> param = new HashMap<String, String>();
		
		String table_name = req.getParameter("table_name")==null?"":req.getParameter("table_name");
		
		param.put("table_name", table_name);
		
		try {
			ajaxDAO dao = sqlSessionMySql.getMapper(ajaxDAO.class);
			columnList = dao.selectColumn(param);
			param.put("field", columnList.get(0).get("Field"));
			dataList = dao.selectData(param);
			
			
		} catch(Exception e) {
			log.error(e.toString());
		}
		
		model.addAttribute("dataList", dataList);
		model.addAttribute("columnList", columnList);
		
		return "jsonView";
	}
    
    

	/**
	 * csv 리스트 샘플 테스트
	 * @param params
	 * @param req
	 * @return ModelAndView
	 * @throws Exception
	*/
    @RequestMapping(value = "csv.ajax", method = RequestMethod.GET)
    public ModelAndView view30(HttpServletRequest req,
    		@RequestParam HashMap<String, String> paramMap) {
    	String seq = paramMap.get("seq");

        List<?> resultDay = null;

        ajaxDAO applyDao = sqlSessionMySql.getMapper(ajaxDAO.class);
        try {        	
        	resultDay = applyDao.selectData(paramMap);
	    } catch (Exception e ) {
	        log.error(e.getMessage());
	        e.getStackTrace();
	    }

		ModelAndView mav = new ModelAndView("/stat/apply/view30");
        mav.addObject("seq", seq);
        mav.addObject("resultDay", resultDay);
        return mav;
    }
    
    

    // 건설현장 const_site 페이지 
	@RequestMapping("/constSite.do")
	public String constSite() throws Exception {
        return "const_site";
	}


    // 건설현장 강우 데이터 실황
	@RequestMapping("/selectObsRainList.do")
	public String selectObsRain(HttpServletRequest req, Model model) throws Exception {
		String yyyymmddhhmm = req.getParameter("yyyymmddhhmm")==null? "": req.getParameter("yyyymmddhhmm");
		
		ArrayList<HashMap<String, String>> obsRainList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> param = new HashMap<String, String>();
		
		param.put("yyyymmddhhmm", yyyymmddhhmm);
		
		try {
			ajaxDAO dao = sqlSessionMySql.getMapper(ajaxDAO.class);
			obsRainList = dao.selectObsRainList(param);
			
		} catch(Exception e) {
			log.error(e.toString());
		}
		
	    model.addAttribute("obsRainList", obsRainList);
	    return "jsonView";
	}


	// 건설현장 풍속, 기온 데이터 실황
	@RequestMapping("/selectObsWindWithTmpList.do")
	public String selectObsWindWithTmp(HttpServletRequest req, Model model) throws Exception {
		String yyyymmddhh = req.getParameter("yyyymmddhh")==null? "": req.getParameter("yyyymmddhh");
		
		ArrayList<HashMap<String, String>> obsWindWithTmpList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> param = new HashMap<String, String>();
		
		param.put("yyyymmddhh", yyyymmddhh);
		
		try {
			ajaxDAO dao = sqlSessionMySql.getMapper(ajaxDAO.class);
			obsWindWithTmpList = dao.selectObsWindWithTmpList(param);
			
		} catch(Exception e) {
			log.error(e.toString());
		}
		
	    model.addAttribute("obsWindWithTmpList", obsWindWithTmpList);
	    return "jsonView";
	}

}
