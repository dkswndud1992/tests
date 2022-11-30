package project.main.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class CommonStringUtil {
	
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
	
	//한글검사 isHangul(String input) return boolean (true, false)
	
	//영문검사 isEng(String input) return boolean (true, false)
	
	//숫자검사 isNumber(String input) return boolean (true, false)
	
}