package project.main.common.dao;

import java.util.ArrayList;
import java.util.HashMap;

public interface SituationDAO {
	public ArrayList<HashMap<String, String>> selectMetList(HashMap<String, Integer> param) throws Exception;

}
