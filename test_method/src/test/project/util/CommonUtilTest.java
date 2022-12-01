package test.project.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import main.project.util.CommonUtil;

class CommonUtilTest {
	
	// @Test
	void testETC() {
		// System.out.println(0/10+1); // 1
		//정확히 일치해야 한다
		// String str = "my java test";
        // System.out.println( str.matches(".*java.*") );  // false
		try {
			CommonUtil test = new CommonUtil();
			System.out.println(test.updateSHA256("name"));
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	// @Test
	void testCopyFile() {
		// 만들 날짜
        String makeDateStr = "202005100000"; 
		
		String S = File.separator; // 구분자
		String filePath1 = "ncl_data_image"+S+"radar_qpf_10m_map"+S+"2020"+S+"05"+S+"11";
		String fileName1 = "RADAR_QPF_10m_MAP_PRCP_202005110000_202005110010_SEOUL.png";
		String oriFilePath = filePath1+S+fileName1;
		//파일객체생성
        File oriFile = new File(oriFilePath);
        
        // 만들 날짜 치환
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmm"); // 날짜 포맷
		Calendar cal = Calendar.getInstance(); // 날짜 계산 위해
		Date tranDate; // 계산 될 날짜를 임시로 담아 줄 변수
		String[] makeTimeArr = new String[5]; // 년, 월, 일, 시, 분
		
		// 계산 준비
		try{
			tranDate = transFormat.parse(makeDateStr);
	        cal.setTime(tranDate);
        }catch(Exception e){
            System.out.println(e);
        }
        
        // 지정날짜 받고 예측시간은 3시간 지정날짜 10분씩 24*6 
        for (int i = 0;i <24*6; i++) {
        	makeDateStr =  transFormat.format(cal.getTime());
        	cal.add(Calendar.MINUTE, +10);
        	makeTimeArr[0] = makeDateStr.substring(0,4);
        	makeTimeArr[1] = makeDateStr.substring(4,6);
        	makeTimeArr[2] = makeDateStr.substring(6,8);
        	makeTimeArr[3] = makeDateStr.substring(8,10);
        	makeTimeArr[4] = makeDateStr.substring(10,12);
        	
        	Calendar cal2 = Calendar.getInstance(); // 날짜 계산 위해
    		Date tranDate2; // 계산 될 날짜를 임시로 담아 줄 변수
    		
    		try{
    			tranDate2 = transFormat.parse(makeDateStr);
    			cal2.setTime(tranDate2);
            }catch(Exception e){
                System.out.println(e);
            }
			for (int j = 0;j <3*6; j++) {
				cal2.add(Calendar.MINUTE, +10);
				String makeDateStr2 =  transFormat.format(cal2.getTime());
	        	
				String filePath2 = "ncl_data_image"+S+"radar_qpf_10m_map"+S+makeTimeArr[0]+S+makeTimeArr[1]+S+makeTimeArr[2];
	    		String fileName2 = "RADAR_QPF_10m_MAP_PRCP_"+makeDateStr+"_"+makeDateStr2+"_SEOUL.png";
	    		String copyFilePath = filePath2+S+fileName2;


	    		File Folder = new File(filePath2);

	    		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
	    		if (!Folder.exists()) {
	    			try{
	    			    Folder.mkdir(); //폴더 생성합니다.
	    			    // System.out.println("폴더가 생성되었습니다.");
	    		        } 
	    		        catch(Exception e){
	    			    e.getStackTrace();
	    			}        
	    	         }else {
	    			// System.out.println("이미 폴더가 생성되어 있습니다.");
	    		}
	            
	            //복사파일객체생성
	            File copyFile = new File(copyFilePath);
	            
	            try {
	            	FileInputStream fis = new FileInputStream(oriFile);
	                FileOutputStream fos = new FileOutputStream(copyFile); //복사할파일
	                
	                int fileByte = 0; 
	                // fis.read()가 -1 이면 파일을 다 읽은것
	                while((fileByte = fis.read()) != -1) {
	                    fos.write(fileByte);
	                }
	                //자원사용종료
	                fis.close();
	                fos.close();
	                
	            } catch (FileNotFoundException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
			}
        	
        }
	}
	
	// @Test
	void updateFutureImg() {
		String S = File.separator; // 구분자
		
		// 받을 파일 경로와 이름 (매개변수로)
		String selectPathName = "ncl_data_image/radar_qpf_10m_map/2020/05/11/RADAR_QPF_10m_MAP_PRCP_202005110400_202005110410_SEOUL.png";
		
		File searchFile = new File(selectPathName.replace("/", S));
		
		// 있을 경우  path 반환
		if (searchFile.exists()) { 
			System.out.println("first : ");
			System.out.println(selectPathName);
			// return selectPathName;
		}
		
		// 없다면 10분 전 시간 미래 img 찾기 (최대 3시간)
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmm"); // 날짜 포맷
		Calendar cal = Calendar.getInstance(); // 날짜 계산 위해
		Date tranDate; // 계산 될 날짜를 임시로 담아 줄 변수
		String[] futureTimeArr = new String[5]; // 년, 월, 일, 시, 분
		String filePath = "";
		String fileName = "";
		String pathName = "";
		String defaultPathName = "default"; // 전 3시간 값 없을시 들어갈 경로(selectPathName)

		String convertPathArr[] = selectPathName.split("/");
		String searchName = convertPathArr[5]; // 파일 이름 추출
		String convertNameArr[] = searchName.split("_");
		String dateStr = convertNameArr[5]; // 시간 추출
		
		// 계산 준비
		try{
			tranDate = transFormat.parse(dateStr);
	        cal.setTime(tranDate);
        }catch(Exception e){
            System.out.println(e);
        }
		
		for (int i = 1; i < 18; i++) {
			// 10분 빼고 찾기 최대 3시간 반복
			cal.add(Calendar.MINUTE, -10);
			dateStr = transFormat.format(cal.getTime());
			
			futureTimeArr[0] = dateStr.substring(0,4);
			futureTimeArr[1] = dateStr.substring(4,6);
			futureTimeArr[2] = dateStr.substring(6,8);
			futureTimeArr[3] = dateStr.substring(8,10);
			futureTimeArr[4] = dateStr.substring(10,12);
			
			filePath = convertPathArr[0]+S+convertPathArr[1]+S+futureTimeArr[0]+S+futureTimeArr[1]+S+futureTimeArr[2]+S;
			fileName = convertNameArr[0]+"_"+convertNameArr[1]+"_"+convertNameArr[2]+"_"+convertNameArr[3]+"_"+convertNameArr[4]+"_"+dateStr+"_"+convertNameArr[6]+"_"+convertNameArr[7];
			pathName = filePath+fileName;
			searchFile = new File(pathName);
			if (searchFile.exists()) { // 있을 경우  path 반환
				pathName = pathName.replace(S, "/");
				System.out.println(i+"nd : ");
				System.out.println(pathName);
				// return pathName;
			} else {
				System.out.println("xxx");
			}
		}
		
		// 3시간 없을경우 default 반환
		System.out.println("default : ");
		System.out.println(defaultPathName);
		// return defaultPathName;
	}
	
	// @Test
	void testMakeTime() {
		ArrayList <String> returnArrayList = new ArrayList<String>();
		for(int i=0;i<24;i++) {
			String message;
			String hour = i<10 ? "0" + Integer.toString(i): Integer.toString(i);
	        Scanner scan = new Scanner(System.in); 
	        System.out.println(i+"시 데이터를 입력하세요:");
	        
	        message = scan.nextLine();  
	        
	        if ( !message.isEmpty() ) {
	        	String convert = message.replaceAll(","," ");
		        String convertArray[] = convert.split(" ");
		        
		        for (String string : convertArray) {
					returnArrayList.add(hour+":"+string);
				}
	        }
	        scan.close();
		}
		
		try {
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println(returnArrayList.toString());
	}
	
	// @Test
	void testUpdateStn() {
		try{
			String S = File.separator;
			String baseArrayListPath = "src"+S+"resource"+S+"ASOS_AWS_STN_LIST.txt";
			String returnArrayListPath = "src"+S+"resource"+S+"ASOS_AWS_STN_LIST2.txt";
			String downloadFilePath = "src"+S+"resource"+S+"ASOS_AWS_STN_LIST4.txt";
			CommonUtil.updateStn(baseArrayListPath, returnArrayListPath, downloadFilePath);
            
        }catch(Exception e){
            System.out.println(e);
        }
	}
	
	// @Test
	void testSelectNull() {
		ArrayList <String> returnArrayList = new ArrayList<String>();
		ArrayList <Integer> newArrayList = new ArrayList<Integer>();
		
		returnArrayList.add("1");
		returnArrayList.add("3");
		returnArrayList.add("5");
		
		newArrayList = CommonUtil.updateStringListToIntList(returnArrayList);
		System.out.println(newArrayList.toString());
	}
	
	// @Test
	void testSelectNullCheck() {
		Double a = Double.valueOf("NaN");
		assertEquals(false, CommonUtil.selectNullCheck(a));
	}

	// @Test
	void testSelectCurrentDate() {
		assertEquals("200331", CommonUtil.selectCurrentDate(2,6));
	}

	// @Test
	void testSelectCurrentHour() {
		assertEquals("18", CommonUtil.selectCurrentHour());
	}

	// @Test
	void testSelectCurrentMinute() {
		assertEquals("50", CommonUtil.selectCurrentMinute());
	}

	// @Test
	void testSelectArrayCalHour() {
		assertArrayEquals(new String[] {"2019092203", "2019092202"}, CommonUtil.selectArrayCalHour("2019092203","m",2));
	}

}
