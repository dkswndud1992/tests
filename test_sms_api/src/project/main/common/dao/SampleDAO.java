package project.main.common.dao;

import java.util.ArrayList;
import java.util.HashMap;

public interface SampleDAO {
	public int dbTestSample(String param) throws Exception;
	public String selectSample(HashMap<String, String> param) throws Exception;
	public ArrayList<HashMap<String, String>> selectSampleList(HashMap<String, Integer> param) throws Exception;
	public void insertSample(HashMap<String, String> param) throws Exception;
	public void updateSample(HashMap<String, String> param) throws Exception;
	public void deleteSample(String param) throws Exception;

}
