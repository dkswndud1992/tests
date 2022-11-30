package project.main.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class CommonDateUtil {
	
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
	
	//현재시간호출 selectCurrentFullDate return String (yyyyMMddHHmm)
	
	//현재 시간 호출 selectCurrentHourDate() return String (HH)
	
	//현재 분 호출 selectCurrentMinDate() return String (mm)
	
	//날짜일계산 selectCurrentDateAgo(String input, String mode, int value) return return String (yyyyMMddHHmm)
	//input(yyyyMMddHHmm), mod(PLUS, MINUS), value(0, 1, 2...)
	
	//날짜시계산 selectCurrentHourAgo(String input, String mode, int value) return return String (yyyyMMddHHmm)
	//input(yyyyMMddHHmm), mod(PLUS, MINUS), value(10)
	
	//날짜분계산 selectCurrentMinuteAgo(String input, String mode, int value) return return String (yyyyMMddHHmm)
	//input(yyyyMMddHHmm), mod(PLUS, MINUS), value(10)
	
}