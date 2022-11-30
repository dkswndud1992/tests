package project.main.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.NameValuePair;

public class SmsApi {
	
	/**
	 *  aligo sms 문자전송
	 * @author  안주영
	 * @version 1.0
	 * @param String sendMsg (%receiverName%)
	 * @param Map receiverMap (receiverPhoneNumber, receiverName)
	 * @param String authId
	 * @param String authKey
	 * @return String result
	*/
	public static String InsertSms(String sendMsg, Map<String, String> receiverMap, String authId, String authKey){
		String result = "";		
		String sendReceiver = "";
		String sendDestination = "";
		try{
			final String encodingType = "utf-8";
			final String boundary = "____boundary____";
			
			for(Iterator<String> i = receiverMap.keySet().iterator(); i.hasNext();){
				String key = i.next();
				sendReceiver += key+",";
				sendDestination += key+"|"+receiverMap.get(key)+",";
			}
			sendReceiver = sendReceiver.substring(0, sendReceiver.length() - 1);
			sendDestination = sendDestination.substring(0, sendDestination.length() - 1);
		
			/**************** 문자전송하기 예제 ******************/
			/* "result_code":결과코드,"message":결과문구, */
			/* "msg_id":메세지ID,"error_cnt":에러갯수,"success_cnt":성공갯수 */
			/* 동일내용 > 전송용 입니다.  
			/******************** 인증정보 ********************/
			String sms_url = "https://apis.aligo.in/send/"; // 전송요청 URL
			
			Map<String, String> sms = new HashMap<String, String>();
			
			sms.put("user_id", authId); // SMS 아이디
			sms.put("key", authKey); //인증키
			
			/******************** 인증정보 ********************/
			
			/******************** 전송정보 ********************/
			sms.put("msg", sendMsg); // 메세지 내용
			sms.put("receiver", sendReceiver); // 수신번호
			sms.put("destination", sendDestination); // 수신인 %고객명% 치환
			// TODO : 추 후 설정 필요.
			sms.put("sender", "025724320"); // 발신번호
			sms.put("msg_type", "SMS"); // 메세지 타입 
			// sms.put("testmode_yn", "Y"); // Y 인경우 실제문자 전송X , 자동취소(환불) 처리
			// sms.put("title", ""); //  LMS, MMS 제목 (미입력시 본문중 44Byte 또는 엔터 구분자 첫라인)
			// sms.put("rdate", ""); // 예약일자 - 20161004 : 2016-10-04일기준
			// sms.put("rtime", ""); // 예약시간 - 1930 : 오후 7시30분
			String image = "";
			//image = "/tmp/pic_57f358af08cf7_sms_.jpg"; // MMS 이미지 파일 위치
			
			/******************** 전송정보 ********************/
			
			// 헤더 설정.
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			
			builder.setBoundary(boundary);
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.setCharset(Charset.forName(encodingType));
			
			for(Iterator<String> i = sms.keySet().iterator(); i.hasNext();){
				String key = i.next();
				builder.addTextBody(key, sms.get(key), ContentType.create("Multipart/related", encodingType));
			}
			
			// 이미지 전송.
			File imageFile = new File(image);
			if(image!=null && image.length()>0 && imageFile.exists()){
				builder.addPart("image", new FileBody(imageFile, ContentType.create("application/octet-stream"), URLEncoder.encode(imageFile.getName(), encodingType)));
			}
			
			// post 전송.
			HttpEntity entity = builder.build();
			
			HttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(sms_url);
			post.setEntity(entity);
			
			HttpResponse res = client.execute(post);
			
			// 반환값.
			if(res != null){
				BufferedReader in = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), encodingType));
				String buffer = null;
				while((buffer = in.readLine())!=null){
					result += buffer;
				}
				in.close();
			}
		}catch(Exception e){
			System.out.print(e.getMessage());
		}
		return result;
	}
	
	
	/**
	 *  aligo sms 문자전송 대량
	 * @author  안주영
	 * @version 1.0
	 * @param Map receiverMap (receiverPhoneNumber, receiverMsg)
	 * @param String authId
	 * @param String authKey
	 * @return String result
	*/
	public static String InsertSms(Map<String, String> receiverMap, String authId, String authKey){
		String result = "";
		try{
			final String encodingType = "utf-8";
			final String boundary = "____boundary____";
			Map<String, String> sms = new HashMap<String, String>();
		
			/**************** 문자전송하기 대량 예제 ******************/
			/* "result_code":결과코드,"message":결과문구, */
			/* "msg_id":메세지ID,"error_cnt":에러갯수,"success_cnt":성공갯수 */
			/* 각각 다른 개별 내용 > 동시 전송용 입니다.  
			/******************** 인증정보 ********************/
			String sms_url = "https://apis.aligo.in/send_mass/"; // 전송요청 URL
			
			sms.put("user_id", authId); // SMS 아이디
			sms.put("key", authKey); //인증키
			
			/******************** 인증정보 ********************/
			
			/******************** 전송정보 ********************/
			// TODO : 추 후 설정 필요.
			sms.put("sender", "025724320"); // 발신번호
			sms.put("msg_type", "SMS"); // SMS(단문) , LMS(장문), MMS(그림문자)  = 필수항목
			// sms.put("testmode_yn", "Y"); // Y 인경우 실제문자 전송X , 자동취소(환불) 처리
			// sms.put("rdate", ""); // 예약일자 - 20161004 : 2016-10-04일기준
			// sms.put("rtime", ""); // 예약시간 - 1930 : 오후 7시30분
			
			int cnt =1;
			for(Iterator<String> i = receiverMap.keySet().iterator(); i.hasNext();){
				String key = i.next();
				sms.put("rec_" + cnt, key ); // 수신번호_$i 번째  = 필수항목
				sms.put("msg_" + cnt, receiverMap.get(key)); // 내용_$i번째  = 필수항목
				cnt++;
			}
			
			sms.put("cnt", String.valueOf(cnt-1));
			// sms.put("title", "제목입력"); //  LMS, MMS 제목 (미입력시 본문중 44Byte 또는 엔터 구분자 첫라인)
			
			String image = "";
			//image = "/tmp/pic_57f358af08cf7_sms_.jpg"; // MMS 이미지 파일 위치
			
			/******************** 전송정보 ********************/
			
			/*****/
			/*** ※ 중요 - 기존 send 와 다른 부분  ***
			 *  msg_type 추가 : SMS 와 LMS 구분자 = 필수항목
			 *  receiver(수신번호) 와 msg(내용) 가 rec_1 ~ rec_500 과 msg_1 ~ msg_500 으로 설정가능 = 필수입력(최소 1개이상)
			 * cnt 추가 : 위 rec_갯수 와 msg_갯수에 지정된 갯수정보 지정 = 필수항목 (최대 500개)
			/******/
			
			// 헤더 설정.
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			
			builder.setBoundary(boundary);
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.setCharset(Charset.forName(encodingType));
			
			for(Iterator<String> i = sms.keySet().iterator(); i.hasNext();){
				String key = i.next();
				builder.addTextBody(key, sms.get(key), ContentType.create("Multipart/related", encodingType));
			}
			
			// 이미지 전송.
			File imageFile = new File(image);
			if(image!=null && image.length()>0 && imageFile.exists()){
				builder.addPart("image", new FileBody(imageFile, ContentType.create("application/octet-stream"), URLEncoder.encode(imageFile.getName(), encodingType)));
			}
			
			// post 전송.
			HttpEntity entity = builder.build();
			
			HttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(sms_url);
			post.setEntity(entity);
			
			HttpResponse res = client.execute(post);
			
			// 반환값.
			if(res != null){
				BufferedReader in = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), encodingType));
				String buffer = null;
				while((buffer = in.readLine())!=null){
					result += buffer;
				}
				in.close();
			}
		}catch(Exception e){
			System.out.print(e.getMessage());
		}
		return result;
	}
	
	/**
	 *  aligo sms 조회
	 * @author  안주영
	 * @version 1.0
	 * @param String page(default = 1)
	 * @param String authId
	 * @param String authKey	 
	 * @return String result
	*/
	public static String SelectSms(String page, String authId, String authKey){
		String result = "";
		try{
			final String encodingType = "utf-8";
			
			/**************** 최근 전송 목록 ******************/
			/* "result_code":결과코드,"message":결과문구, */
			/** list : 전송된 목록 배열 ***/
			/******************** 인증정보 ********************/
			String sms_url = "https://apis.aligo.in/list/"; // 전송요청 URL
			
			List<NameValuePair> sms = new ArrayList<NameValuePair>();
			
			sms.add(new BasicNameValuePair("user_id", authId)); // SMS 아이디 
			sms.add(new BasicNameValuePair("key", authKey)); //인증키
			/******************** 인증정보 ********************/
			
			sms.add(new BasicNameValuePair("page", page)); //조회 시작번호 (기본 1)
			// sms.add(new BasicNameValuePair("page_size", "")); //출력 갯수 (기본 30)
			// sms.add(new BasicNameValuePair("start_date", "")); //조회일 시작 YYYYMMDD (기본 최근일자)
			// sms.add(new BasicNameValuePair("limit_day", "7")); //조회일수
		
			HttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(sms_url);
			post.setEntity(new UrlEncodedFormEntity(sms, encodingType));
			
			HttpResponse res = client.execute(post);
			
			if(res != null){
				BufferedReader in = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), encodingType));
				String buffer = null;
				while((buffer = in.readLine())!=null){
					result += buffer;
				}
				in.close();
			}
		}catch(Exception e){
			System.out.print(e.getMessage());
		}
		return result;
	}
	
	/**
	 *  aligo sms 조회 상세
	 * @author  안주영
	 * @version 1.0
	 * @param String mid
	 * @param String page (default = 1)
	 * @param String authId
	 * @param String authKey
	 * @return String result
	*/
	public static String SelectSmsDetail(String mid, String page, String authId, String authKey){
		String result = "";
		try{
			final String encodingType = "utf-8";
				
			/*************  문자전송 결과 상세보기 *****************/
			/** SMS_CNT / LMS_CNT / MMS_CNT : 전송유형별 잔여건수 ***/
			/******************** 인증정보 ********************/
			String sms_url = "https://apis.aligo.in/sms_list/"; // 전송요청 URL
			
			List<NameValuePair> sms = new ArrayList<NameValuePair>();
			
			sms.add(new BasicNameValuePair("user_id", authId)); // SMS 아이디 
			sms.add(new BasicNameValuePair("key", authKey)); //인증키
			/******************** 인증정보 ********************/
			
			sms.add(new BasicNameValuePair("mid", mid)); // 메세지ID
			sms.add(new BasicNameValuePair("page", page)); // 조회 시작번호1
			sms.add(new BasicNameValuePair("page_size", "")); // 출력 갯수(기본 30)
			
			HttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(sms_url);
			post.setEntity(new UrlEncodedFormEntity(sms, encodingType));
			
			HttpResponse res = client.execute(post);
			
			if(res != null){
				BufferedReader in = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), encodingType));
				String buffer = null;
				while((buffer = in.readLine())!=null){
					result += buffer;
				}
				in.close();
			}
		}catch(Exception e){
			System.out.print(e.getMessage());
		}
		return result;
	}
	
	/**
	 *  aligo sms 발송가능건수 조회
	 * @author  안주영
	 * @version 1.0
	 * @param String authId
	 * @param String authKey
	 * @return String result
	*/
	public static String SelectSmsLimit(String authId, String authKey){
		String result = "";
		try{
			/**************** 발송 가능 건수 ******************/
			/* "result_code":결과코드,"message":결과문구, */
			/** list : 전송된 목록 배열 ***/
			/******************** 인증정보 ********************/
			String sms_url = "https://apis.aligo.in/remain/"; // 전송요청 URL
			
			String sms = "";
			sms += "user_id=" + authId; // SMS 아이디 
			sms += "&key=" + authKey; //인증키
			/******************** 인증정보 ********************/
			
			URL url = new URL(sms_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			OutputStream os = conn.getOutputStream();
			os.write(sms.getBytes());
			os.flush();
			os.close();
			
			String buffer = null;
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			while((buffer = in.readLine())!=null){
				result += buffer;
			}
			
			in.close();
		}catch(MalformedURLException e1){
			System.out.print(e1.getMessage());
		}catch(IOException e2){
			System.out.print(e2.getMessage());
		}
		return result;
	}
	
}