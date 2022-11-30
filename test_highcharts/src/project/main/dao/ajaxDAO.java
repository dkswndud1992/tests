package project.main.dao;

import java.util.ArrayList;
import java.util.HashMap;

public interface ajaxDAO {
	public ArrayList<HashMap<String, String>> selectData(HashMap<String, String> param) throws Exception;
	public ArrayList<HashMap<String, String>> selectTable() throws Exception;
	public ArrayList<HashMap<String, String>> selectColumn(HashMap<String, String> param) throws Exception;

	public ArrayList<HashMap<String, String>> selectObsRainList(HashMap<String, String> param) throws Exception;
	public ArrayList<HashMap<String, String>> selectObsWindWithTmpList(HashMap<String, String> param) throws Exception;
}
