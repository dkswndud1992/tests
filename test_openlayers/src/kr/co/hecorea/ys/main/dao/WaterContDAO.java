package kr.co.hecorea.ys.main.dao;

import java.util.ArrayList;
import java.util.HashMap;

public interface WaterContDAO {
	
	public void selectDamInfo(HashMap<String, String> param) throws Exception;
	
	public void insertGeom(ArrayList<HashMap<String, String>> param) throws Exception;
	
	public String selectData() throws Exception;
	
}

