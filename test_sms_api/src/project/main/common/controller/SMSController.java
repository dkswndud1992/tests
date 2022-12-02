package project.main.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import project.main.common.util.SmsApi;

@Controller
@RequestMapping("/sms/*")
public class SMSController {
	@Autowired
	@Resource(name="sqlSessionMysql")
	private SqlSession sqlSessionMySql;
	
	@Value("#{config['authId']}") String authId;
	@Value("#{config['authKey']}") String authKey;
	
	@Autowired
	HttpSession session;
	
	private final Logger log = Logger.getLogger(getClass());
	
	/**
	 * mainSample
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/aligo/curl_send.do")
	public String sampleMain(HttpServletRequest req, Model model) throws Exception {
		String result = "";
		
		// 보낼 메세지%받는사람이름치환됨.%
		String sendMsg = "%고객명%님. 안녕하세요. API TEST2 SEND";
		
		// 보낼 사람 목록. {받는번호: 받는사람이름}
		Map<String, String> receiverMap = new HashMap<String, String>();
		receiverMap.put("01012341234", "홍길동");
		
		// 메세지 보내기 (보낼메세지%받는사람이름%, {받는번호: 받는사람이름}, 아이디, 키)
		result = SmsApi.InsertSms(sendMsg, receiverMap, authId, authKey);
		
		// 메세지 보내기 대량 ({받는번호: 보낼메세지}, 아이디, 키)
		// result = SmsApi.InsertSms(receiverMap, authId, authKey);
		
		// 메세지 이력보기 (페이지, 아이디, 키) - 기본 출력갯수 30
		result = SmsApi.SelectSms("1", authId, authKey);
		
		// 메세지 이력보기 상세 (메세지아이디, 페이지, 아이디, 키) - 기본 출력갯수 30, 기본 최근일자
		// result = SmsApi.SelectSmsDetail("123014559", "1", authId, authKey);
		
		// 남은 메세지건수 보기 (아이디, 키)
		// result = SmsApi.SelectSmsLimit(authId, authKey);
		
		log.info(result);
		
		/* 보기 좋게 치환하기.
		result = result.replace("result_code", "결과코드");
		result = result.replace("message", "결과문구");
		result = result.replace("msg_id", "메세지ID");
		result = result.replace("error_cnt", "에러갯수");
		result = result.replace("success_cnt", "성공갯수");
		result = result.replace("msg_type", "메세지타입");
		log.info(result);
		*/
		
		// json 으로 반환.
		JSONParser parser = new JSONParser();
		Object obj = parser.parse( result );
		JSONObject jsonObj = (JSONObject) obj;
		
		model.addAttribute("Param", jsonObj);
        return "jsonView";
	}
	
    
}
