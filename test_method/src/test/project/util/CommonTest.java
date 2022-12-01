package test.project.util;

import java.io.File;
import java.util.ArrayList;

import main.project.util.Common;

class CommonTest {
	
	// @Test
	void testETC() {
		try {
			System.out.println("name : ");
			System.out.println(Common.updateSHA256("name"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	// @Test
	void testInsertDateList() {
		try{
			ArrayList <String> dateArrayList = new ArrayList <String>();
			dateArrayList.add("20060900");
			dateArrayList.add("20061000");
			Common.insertDateList(dateArrayList, "yyMMddHH", "hour", 1);
			System.out.println(dateArrayList.toString());
        }catch(Exception e){
            System.out.println(e);
        }
	}
	
	// @Test
	void testInsertStnListFile() {
		try{
			String S = File.separator;
			String baseArrayListPath = "src"+S+"resource"+S+"ASOS_AWS_STN_LIST.txt";
			String returnArrayListPath = "src"+S+"resource"+S+"ASOS_AWS_STN_LIST2.txt";
			String downloadFilePath = "src"+S+"resource"+S+"ASOS_AWS_STN_LIST5.txt";
			Common.insertStnListFile(baseArrayListPath, returnArrayListPath, downloadFilePath);
            
        }catch(Exception e){
            System.out.println(e);
        }
	}
	
	// @Test
	void testUpdateNullToString() {
		try{
			Double test = Double.valueOf("NaN");
			String test1 = Common.updateNullToString(test);
			System.out.println("test1 = "+test1);
        }catch(Exception e){
        	System.out.println(e);
        }
	}
	
	// @Test
	void testSelectNowDate() {
		try{
			System.out.println(Common.selectNowDate(2,4));
        }catch(Exception e){
        	System.out.println(e);
        }
	}
	
	//@Test
	void testSelectArrayDate() {
		try{
			String[] test = Common.selectArrayDate("201212010000", "hour", 2, 30);
			System.out.println(test.length);
			for(String a :test) {
				System.out.println(a);
			}
        }catch(Exception e){
        	System.out.println(e);
        }
	}

}
