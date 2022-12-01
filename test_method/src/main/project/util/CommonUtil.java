package main.project.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommonUtil {
	/**
	 *  updateSHA256 암호화
	 * @param String
	 * @return String
	*/
	public String updateSHA256(String str){
		String SHA = ""; 
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(str.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();
		}catch(Exception e){
			e.printStackTrace(); 
			SHA = null; 
		}
		return SHA;
	}
	
	/**
	 * 날짜 빈곳 채우기
	 * @author  안주영
	 * @version 1.0
	 * @param String format
	 * @param ArrayList <String> returnArrayList
	*/
	public static void updateFillDate(String format, ArrayList <String> returnArrayList, String fillMod) throws FillModException{
		// test branch
		ArrayList <Date> dateArrayList = new ArrayList <Date>();
		ArrayList <Date> compareArrayList = new ArrayList <Date>();
		Calendar calMin = Calendar.getInstance();
		Calendar calMax = Calendar.getInstance();
		SimpleDateFormat transFormat;
		int mod;
		
		try {
			transFormat = new SimpleDateFormat(format);
			
			if(fillMod.equals("y")){
				mod = Calendar.YEAR;
			}else if(fillMod.equals("M")){
				mod = Calendar.MONTH;
			}else if(fillMod.equals("d")){
				mod = Calendar.DAY_OF_YEAR;
			}else if(fillMod.equals("h")){
				mod = Calendar.HOUR;
			}else if(fillMod.equals("m")){
				mod = Calendar.MINUTE;
			}else{
				throw new FillModException("fillmod가 y또는 M또는 d또는 h또는 m이 맞는지 확인해주세요.");
			}
			
			for (String value : returnArrayList) {
				// 1. 데이터 포맷 맞는지.(yyyyMMddHHmmss)
				if(!dateCheck(value, format)) {
					System.out.println("format: "+ format+ "// value: "+value);
					System.out.println("데이터 포멧이 맞지 않습니다.");
					return;
				}else {
					// 2. 맞다면 dateArrayList 에 넣기.
					dateArrayList.add(transFormat.parse(value));
				}
			}
			
			calMin.setTime(Collections.min(dateArrayList));
			calMax.setTime(Collections.max(dateArrayList));
			
			// 3. 가장 큰날짜와 작은날짜를 통해 비교 date리스트 생성
			while(calMin.compareTo(calMax) == -1) {
				compareArrayList.add(calMin.getTime());
				calMin.add(mod, 1);
			}
			compareArrayList.add(calMax.getTime());
            // 초기화
            returnArrayList.clear();
            
			// 6. date리스트를 string리스트로 변환.
            for (Date value : compareArrayList) {
            	returnArrayList.add(transFormat.format(value));
			}
		}catch(Exception e) {
			// e.printStackTrace();
        	System.out.println(e);
		}finally {
			System.out.println("updateFillDate 호출");
		}
		return;
	}
	
	
	/**
	 *파일을 arrayList로
	 * @author  안주영
	 * @version 1.0
	 * @param String filePath
	 * @param ArrayList <String> baseArrayList
	*/
	public static ArrayList <String> updateFilepathToList (String filePath) {
		ArrayList <String> baseArrayList = new ArrayList <String>();
		//파일 객체 생성
        File baseFile = new File(filePath);
        FileReader baseFilereader;
        BufferedReader baseBufReader;
		try {
			//입력 스트림 생성
			baseFilereader = new FileReader(baseFile);
			//입력 버퍼 생성
			baseBufReader = new BufferedReader(baseFilereader);
			
			String line = "";
            while((line = baseBufReader.readLine()) != null){
                baseArrayList.add(line);
            }
            //.readLine()은 끝에 개행문자를 읽지 않는다.            
            baseBufReader.close();
            
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}catch(IOException e){
            System.out.println(e);
        }
		return baseArrayList;
	}
	
	/**
	 * 리스트 비교 후 채운뒤 없는 부분 채우기
	 * @author  안주영
	 * @version 1.0
	 * @param ArrayList<T> baseArrayList
	 * @param ArrayList<T> returnArrayList
	*/
	public static <T> void updateListCompareFill(ArrayList<T> baseArrayList, ArrayList<T> returnArrayList) {
		for(T b : baseArrayList) {
            if(!returnArrayList.contains(b)) {
            	returnArrayList.add(b);
            }
        }
		return;
	}	
	
	/**
	 * String 리스트를 Integer 리스트로
	 * @author  안주영
	 * @version 1.0
	 * @param ArrayList <String> returnArrayList
	 * @return ArrayList <Integer> newList
	*/
	public static ArrayList <Integer> updateStringListToIntList(ArrayList <String> returnArrayList) {
		ArrayList<Integer> newList = new ArrayList<Integer>(returnArrayList.size()) ;
        for (String myInt : returnArrayList) { 
            newList.add(Integer.valueOf(myInt)); 
        }
        return newList;
	}	
	
	/**
	 * Integer 리스트를 String 리스트로
	 * @author  안주영
	 * @version 1.0
	 * @param ArrayList <Integer> returnArrayList
	 * @return ArrayList <String> newList
	*/
	public static ArrayList <String> updateIntListToStringList(ArrayList <Integer> returnArrayList) {
		ArrayList<String> newList = new ArrayList<String>(returnArrayList.size()) ;
        for (Integer myString : returnArrayList) { 
            newList.add(Integer.toString(myString)); 
        }
        return newList;
	}
	
	/**
	 * 리스트를 파일로
	 * @author  안주영
	 * @version 1.0
	 * @param String filePath
	 * @param ArrayList<String> arrayList
	*/
	public static <T> void updateListToFile(String filePath, ArrayList<String> arrayList) {
		try{
            //파일 객체 생성
            File file = new File(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            
            if(file.isFile() && file.canWrite()){
                for(String i : arrayList) {
                	//쓰기
                    bufferedWriter.write(i);
                    //개행문자쓰기
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
        }catch (IOException e) {
            System.out.println(e);
        }
	}
	
	
	
	/**
	 * 관측소 빈곳 채우기
	 * @author  안주영
	 * @version 1.0
	 * @param String baseArrayListPath
	 * @param String returnArrayListPath
	 * @param String downloadFilepath 
	*/
	public static void updateStn(String baseArrayListPath, String returnArrayListPath, String downloadFilepath) {
		ArrayList <String> baseArrayList = new ArrayList <String>();
		ArrayList <String> returnArrayList = new ArrayList <String>();
		ArrayList <Integer> intArrayList = new ArrayList <Integer>();
		
		try {
			baseArrayList = updateFilepathToList(baseArrayListPath);
			returnArrayList = updateFilepathToList(returnArrayListPath);
			
			System.out.println("baseArrayList.size : "+baseArrayList.size());
			System.out.println("returnArrayList.size : "+returnArrayList.size());
			
			updateListCompareFill(baseArrayList, returnArrayList);

            intArrayList = updateStringListToIntList(returnArrayList);
            
            // 리스트를 정렬합니다. 
            Collections.sort(intArrayList);
            
            returnArrayList = updateIntListToStringList(intArrayList);
            
            updateListToFile(downloadFilepath, returnArrayList);
            
            System.out.println("newList : "+returnArrayList.toString());
            System.out.println("newList.size : "+returnArrayList.size());
		}catch(Exception e) {
			// e.printStackTrace();
        	System.out.println(e);
		}finally {
			System.out.println("updateStn 호출");
		}
		return;
	}
	
	/**
	 * null 체크(string반환)
	 * @author  안주영
	 * @version 1.0
	 * @param Object obj 
	 * @return String returnString
	*/
	public static String selectNull(Object obj) {
		String returnString = "";
		
		try {
			System.out.println("object_type: "+obj.getClass());
			
			if (obj instanceof Integer) { 
				returnString = Integer.toString((Integer)obj);
			} else if (obj instanceof Boolean) {
				returnString = String.valueOf((Boolean)obj);
			} else if (obj instanceof String) {
				returnString = (String)obj;
			}  else if (obj instanceof String[]) {
				returnString = Arrays.toString((String[])obj);
			}  else if (obj instanceof Integer[]) {
				returnString = Arrays.toString((Integer[])obj);	
			}  else if (obj instanceof Boolean[]) {
				returnString = Arrays.toString((Boolean[])obj);	
			} else {
				returnString = obj.toString();
				System.out.println("nullCheck 값이 Int 또는 String 또는 boolean 또는 [] 값에서 벗어납니다.");
			}
			
		}catch(Exception e) {
			// e.printStackTrace();
        	System.out.println(e);
        	return returnString;
		}finally {
			System.out.println("selectNull 호출");
		}
		
		return returnString;
	}
	
	/**
	 * null 체크(boolean반환)
	 * @author  안주영
	 * @version 1.0
	 * @param Object obj 
	 * @return String returnBoolean
	*/
	public static boolean selectNullCheck(Object obj) {
		boolean returnBoolean = false;
		try {
			returnBoolean = obj == null || (obj.getClass()==Double.class?Double.isNaN((double)obj) || Double.isInfinite((double)obj):false)? false : true;
		}catch(Exception e) {
			// e.printStackTrace();
        	System.out.println(e);
		}finally {
			System.out.println("selectNullCheck 호출");
		}
		return returnBoolean;
	}
	
	
	/**
	 * 현재 시간 호출(자르는 길이)
	 * @author  안주영
	 * @version 1.0
	 * @param int num 
	 * @return String returnString
	*/
	public static String selectCurrentDate(int num) {
		String returnString = "";
		String dateFormat = "";
		Date date;
		
		try {
			dateFormat = "yyyyMMddHHmmss";
			dateFormat = dateFormat.substring(0,dateFormat.length()-num);
			date = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat(dateFormat);
			returnString = transFormat.format(date);
			
		}catch(Exception e) {
			// e.printStackTrace();
        	System.out.println(e);
		}finally {
			System.out.println("selectCurrentDate 호출");
		}
		return returnString;
	}
	
	/**
	 * 현재 시간 호출(앞에서 자르는 길이, 뒤에서 자르는 길이)
	 * @author  안주영
	 * @version 1.0
	 * @param int num1
	 * @param int num2
	 * @return String returnString
	*/
	public static String selectCurrentDate(int num1, int num2) {
		String returnString = "";
		String dateFormat = "";
		Date date;
		
		try {
			dateFormat = "yyyyMMddHHmmss";
			dateFormat = dateFormat.substring(0,dateFormat.length()-num2);
			dateFormat = dateFormat.substring(num1,dateFormat.length());
			date = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat(dateFormat);
			returnString = transFormat.format(date);
			
		}catch(Exception e) {
			// e.printStackTrace(); 
        	System.out.println(e); 
		}finally {
			System.out.println("selectCurrentDate 호출");
		}
		return returnString;
	}
	
	
	/**
	 * 현재 시 호출 (ex: 오후8시 = '20')
	 * @author  안주영
	 * @version 1.0
	 * @return String returnString
	*/
	public static String selectCurrentHour() {
		String returnString = "";
		String dateFormat = "";
		Date date;
		try {
			dateFormat = "HH";
			date = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat(dateFormat);
			returnString = transFormat.format(date);
		}catch(Exception e) {
			// e.printStackTrace(); 
        	System.out.println(e); // 
		}finally {
			System.out.println("selectCurrentHour 호출");
		}
		return returnString;
	}
	
	
	/**
	 * 현재 분 호출
	 * @author  안주영
	 * @version 1.0
	 * @return String returnString
	*/
	public static String selectCurrentMinute() {
		String returnString = "";
		String dateFormat = "";
		Date date;
		try {
			dateFormat = "mm";
			date = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat(dateFormat);
			returnString = transFormat.format(date);
		}catch(Exception e) {
			// e.printStackTrace(); 
        	System.out.println(e); 
		}finally {
			System.out.println("selectCurrentMinute 호출");
		}
		return returnString;
	}
	
	
	/**
	 * 전, 후 시간 호출(시간, 모드, 갯수)
	 * @author  안주영
	 * @version 1.0
	 * @param String str
	 * @param String mod
	 * @param int num
	 * @return String[] returnStringArray
	*/
	public static String[] selectArrayCalHour(String str, String mod, int num) {
		String[] returnStringArray = {};
		String dateFormat = "";
		Date toDate;
		SimpleDateFormat transFormat;
		Calendar cal = Calendar.getInstance();
		int  modCheck = 0;

		dateFormat = "yyyyMMddHH";
		
		try {
			
			if (!dateCheck(str, dateFormat)) {
				System.out.println("date format 이 'yyyyMMddHH'인지 확인필요.");
				return null;
			}
			if (num <= 0) {
				System.out.println("num 의 값 확인 필요.");
				return null;
			}
			
			if (mod.equals("m")) {
				modCheck = -1;
			}else if (mod.equals("p")) {
				modCheck = 1;
			}else {
				System.out.println("mode 가 'm'또는 'p'인지 확인필요.");
				return null;
			}
			returnStringArray = new String[num];
			returnStringArray[0] = str;
			
			transFormat = new SimpleDateFormat(dateFormat);
			toDate = transFormat.parse(str);
			cal.setTime(toDate);
			
			for (int i = 1; i < num ; i++) {
				cal.add(Calendar.HOUR, modCheck);
				returnStringArray[i] = transFormat.format(cal.getTime());
			}
			
		}catch(Exception e) {
			// e.printStackTrace(); 
			System.out.println(e);
		}finally {
			System.out.println("selectArrayCalHour 호출");
		}
		return returnStringArray;
	}
	
	
	/**
	 * 날짜 형식 맞는지 검사(시간, 형식)
	 * @author  안주영
	 * @version 1.0
	 * @param String date
	 * @param String format
	 * @return boolean returnBoolean
	*/
	public static boolean dateCheck(String date, String format) {
		boolean returnBoolean = false;
        SimpleDateFormat dateFormatParser = new SimpleDateFormat(format, Locale.KOREA);
        dateFormatParser.setLenient(false);
        try {
            dateFormatParser.parse(date);
            returnBoolean = true;
            return returnBoolean;
        } catch (Exception e) {
        	// e.printStackTrace(); 
        	System.out.println(e);
        	return returnBoolean;
        }
    }
	
	/**
	 * 메소드 종류 호출
	 * @author  안주영
	 * @version 1.0
	 * @return String returnString
	*/
	@Override
	public String toString() {
		String returnString = "불러오기 성공!";
		Method[] methods = this.getClass().getMethods();
		for(Method m : methods ) { 
			System.out.println( "Found a method: " + m );
		}
		return returnString;
	}
	
}

class FillModException extends Exception {
    /**
     *  사용자 정의 Exception
     */
    private static final long serialVersionUID = 1L;
	private final static Logger LOG = Logger.getGlobal();

    public FillModException() {}
    public FillModException(String msg) {
        // super(msg);

		LOG.setLevel(Level.INFO);
        LOG.warning(msg);
    }
}

