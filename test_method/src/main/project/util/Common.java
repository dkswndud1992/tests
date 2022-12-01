package main.project.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;


public class Common {
	
	/**
	 *  updateSHA256 암호화
	 * @author  안주영
	 * @version 1.0
	 * @param String str
	 * @return String str
	*/
	public static String updateSHA256(String str){
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(str.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			str = sb.toString();
		}catch(Exception e){
			e.printStackTrace(); 
			str = null; 
		}
		return str;
	}
	
	/**
	 * 날짜 빈 곳 채우기 (채울 리스트, 포맷, 모드, 단위)
	 * @author  안주영
	 * @version 1.0
	 * @param String format
	 * @param ArrayList <String> returnArrayList
	 * @param String fillMod
	 * @param int timeUnit
	 * @return ArrayList <String> returnArrayList
	*/
	public static ArrayList <String> insertDateList(ArrayList <String> returnArrayList, String format, String fillMod, int timeUnit) {
		ArrayList <Date> dateArrayList = new ArrayList <Date>();
		ArrayList <Date> compareArrayList = new ArrayList <Date>();
		Calendar calMin = Calendar.getInstance();
		Calendar calMax = Calendar.getInstance();
		SimpleDateFormat transFormat;
		int calMod;
		try {
			switch (fillMod) {
				case "year":
					calMod = Calendar.YEAR;
					break;
				case "month":
					calMod = Calendar.MONTH;
					break;
				case "day":
					calMod = Calendar.DAY_OF_YEAR;
					break;
				case "hour":
					calMod = Calendar.HOUR;
					break;
				case "minute":
					calMod = Calendar.MINUTE;
					break;
				default:
					System.out.println("fillMod 확인필요.");
					return null;
			}
			// 포맷 확인 및 날짜리스트로 치환
			transFormat = new SimpleDateFormat(format);
			transFormat.setLenient(false);
			for (String value : returnArrayList) {
				dateArrayList.add(transFormat.parse(value));
			}
			
			// 최대 최소 구하고 채우기
			calMin.setTime(Collections.min(dateArrayList));
			calMax.setTime(Collections.max(dateArrayList));
			while(calMin.compareTo(calMax) == -1) {
				compareArrayList.add(calMin.getTime());
				calMin.add(calMod, Math.abs(timeUnit));
			}
			compareArrayList.add(calMax.getTime());
			
			// 초기화 후 대입하고 반환
            returnArrayList.clear();
            for (Date value : compareArrayList) {
            	returnArrayList.add(transFormat.format(value));
			}
            return returnArrayList;
		}catch(Exception e) {
			// e.printStackTrace();
        	System.out.println(e);
        	return null;
		}
	}
	
	/**
	 * 관측소 빈곳 채워 새로운 파일 만들기
	 * @author  안주영
	 * @version 1.0
	 * @param String baseArrayListPath
	 * @param String returnArrayListPath
	 * @param String downloadFilepath 
	*/
	public static void insertStnListFile(String baseArrayListFilepath, String compareArrayListFilepath, String downloadFilepath) {
		ArrayList <String> baseArrayList = new ArrayList <String>();
		ArrayList <String> compareArrayList = new ArrayList <String>();
		ArrayList <Integer> intArrayList = new ArrayList <Integer>();
        File useFile;
        FileReader useFilereader;
        BufferedReader useBufReader;
        String useline;
        
		try {
			// 파일을 arrayList로
			useFile = new File(baseArrayListFilepath);
			useFilereader = new FileReader(useFile);
			useBufReader = new BufferedReader(useFilereader);
			useline = "";
            while((useline = useBufReader.readLine()) != null){
                baseArrayList.add(useline);
            }          
            useBufReader.close();
            
            useFile = new File(compareArrayListFilepath);
			useFilereader = new FileReader(useFile);
			useBufReader = new BufferedReader(useFilereader);
			useline = "";
            while((useline = useBufReader.readLine()) != null){
                baseArrayList.add(useline);
            }          
            useBufReader.close();
			
			// 리스트 비교 후 채운뒤 없는 부분 채우기
			for(String baseItem : baseArrayList) {
	            if(!compareArrayList.contains(baseItem)) {
	            	compareArrayList.add(baseItem);
	            }
	        }
			
			// String 리스트를 Integer 리스트로 변환 후 정렬
            intArrayList = new ArrayList<Integer>(compareArrayList.size()) ;
            for (String myInt : compareArrayList) { 
            	intArrayList.add(Integer.valueOf(myInt)); 
            }
            Collections.sort(intArrayList);
            
            // Integer 리스트를 String 리스트로
            compareArrayList = new ArrayList<String>(intArrayList.size()) ;
            for (Integer myString : intArrayList) { 
            	compareArrayList.add(Integer.toString(myString)); 
            }
            
            // 채운 리스트 파일쓰기
            useFile = new File(downloadFilepath);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(useFile));
            if(useFile.isFile() && useFile.canWrite()){
                for(String i : compareArrayList) {
                    bufferedWriter.write(i);
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
		}catch(Exception e) {
			// e.printStackTrace();
        	System.out.println(e);
		}
	}
	
	/**
	 * null 체크(string반환)
	 * @author  안주영
	 * @version 1.0
	 * @param Object obj 
	 * @return String returnString
	*/
	public static String updateNullToString(Object obj) {
		String returnString = "";
		try {
			if (obj instanceof String) { 
				returnString = (String)obj;
			}  else if (Objects.isNull(obj) ||  Double.isNaN((Double)obj) ||  Double.isInfinite((Double)obj)) { 
				System.out.println("null값 확인 필요.");
			}  else {
				returnString = obj.toString();
				System.out.println("값 확인 필요.");
			}
			return returnString;
		}catch(Exception e) {
			// e.printStackTrace();
        	// System.out.println(e);
        	return returnString;
		}
	}
	
	/**
	 * 현재 시간 호출
	 * @author  안주영
	 * @version 1.0
	 * @return String returnString
	*/
	public static String selectNowDate() {
		String returnString = "";
		String dateFormat = "";
		Date useDate;
		try {
			dateFormat = "yyyyMMddHHmmss";
			useDate = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat(dateFormat);
			returnString = transFormat.format(useDate);
		}catch(Exception e) {
			// e.printStackTrace();
        	System.out.println(e);
		}
		return returnString;
	}
	
	/**
	 * 현재 시간 호출 오버로딩(뒤에서 자르는 길이)
	 * @author  안주영
	 * @version 1.0
	 * @param int backSlice 
	 * @return String returnString
	*/
	public static String selectNowDate(int backSlice) {
		String returnString = "";
		String dateFormat = "";
		Date useDate;
		try {
			dateFormat = "yyyyMMddHHmmss";
			dateFormat = dateFormat.substring(0,dateFormat.length()-backSlice);
			useDate = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat(dateFormat);
			returnString = transFormat.format(useDate);
		}catch(Exception e) {
			// e.printStackTrace();
        	System.out.println(e);
		}
		return returnString;
	}
	
	/**
	 * 현재 시간 호출 오버로딩(앞에서 자르는 길이, 뒤에서 자르는 길이)
	 * @author  안주영
	 * @version 1.0
	 * @param int frontSlice
	 * @param int backSlice
	 * @return String returnString
	*/
	public static String selectNowDate(int frontSlice, int backSlice) {
		String returnString = "";
		String dateFormat = "";
		Date useDate;
		try {
			dateFormat = "yyyyMMddHHmmss";
			dateFormat = dateFormat.substring(0,dateFormat.length()-backSlice);
			dateFormat = dateFormat.substring(frontSlice,dateFormat.length());
			useDate = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat(dateFormat);
			returnString = transFormat.format(useDate);
		}catch(Exception e) {
			// e.printStackTrace(); 
        	System.out.println(e); 
		}
		return returnString;
	}
	
	/**
	 * 전, 후 시간 호출(시간, 모드, +-단위, 갯수)
	 * @author  안주영
	 * @version 1.0
	 * @param String selectDate
	 * @param String dateMod
	 * @param Int timeUnit
	 * @param Int dateCount
	 * @return String[] returnStringArray
	*/
	public static String[] selectArrayDate(String selectDate, String dateMod, int timeUnit, int dateCount) {
		String[] returnStringArray = {};
		String dateFormat = "";
		Date toDate;
		SimpleDateFormat transFormat;
		Calendar cal = Calendar.getInstance();
		dateFormat = "yyyyMMddHHmm";
		int calMod;
		try {
			if (dateCount <= 0) {
				System.out.println("dateCount 값 확인 필요.");
				return null;
			}
			switch (dateMod) {
				case "year":
					calMod = Calendar.YEAR;
					break;
				case "month":
					calMod = Calendar.MONTH;
					break;
				case "day":
					calMod = Calendar.DAY_OF_YEAR;
					break;
				case "hour":
					calMod = Calendar.HOUR;
					break;
				case "minute":
					calMod = Calendar.MINUTE;
					break;
				default:
					System.out.println("fillMod 확인필요.");
					return null;
			}
			returnStringArray = new String[dateCount];
			returnStringArray[0] = selectDate;
			transFormat = new SimpleDateFormat(dateFormat);
			transFormat.setLenient(false);
			toDate = transFormat.parse(selectDate);
			cal.setTime(toDate);
			for (int i = 1; i < dateCount ; i++) {
				cal.add(calMod, timeUnit);
				returnStringArray[i] = transFormat.format(cal.getTime());
			}
		}catch(Exception e) {
			// e.printStackTrace(); 
			System.out.println(e);
			return null;
		}
		return returnStringArray;
	}
	
}
