package rbs.usr.main.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.woowonsoft.egovframework.form.DataForm;


public interface MainService {
	public List<Object> getModuleList(String moduleId, int fnIdx, int limitNumber, String itemMasterCode);
	
	public Map<Object, List<Object>> getBoardListHashMap(DataForm queryString, int lastIndex, JSONArray menuArray, JSONObject boardTotalSetting);
	
	public Map<Object, List<Object>> getContentsListHashMap(DataForm queryString, JSONArray menuArray);
}