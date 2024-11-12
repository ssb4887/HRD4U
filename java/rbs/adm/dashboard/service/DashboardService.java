package rbs.adm.dashboard.service;

import java.util.List;
import java.util.Map;

import com.woowonsoft.egovframework.form.DataMap;


public interface DashboardService {
	
	public List<Object> getWebsiteList(String siteMode, Map<String, Object> param);
	public DataMap getWebsiteView(String siteMode, Map<String, Object> param);
	public List<Object> getContentsList(String siteMode, String lang, Map<String, Object> param);
	public List<Object> getStatsList(String siteMode, Map<String, Object> param);
}