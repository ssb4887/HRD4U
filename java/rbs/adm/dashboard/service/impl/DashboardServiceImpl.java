package rbs.adm.dashboard.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import rbs.adm.dashboard.mapper.DashboardMapper;
import rbs.adm.dashboard.service.DashboardService;

@Service("admDashboardService")
public class DashboardServiceImpl extends EgovAbstractServiceImpl implements DashboardService {

	@Resource(name="admDashboardMapper")
	private DashboardMapper dashboardDao;
	
	@Override
	public List<Object> getWebsiteList(String siteMode, Map<String, Object> param) {
		return dashboardDao.getWebsiteList(siteMode, param);
	}
	
	@Override
	public DataMap getWebsiteView(String siteMode, Map<String, Object> param) {
		return dashboardDao.getWebsiteView(siteMode, param);
	}
	@Override
	public List<Object> getContentsList(String siteMode, String lang, Map<String, Object> param) {
		return dashboardDao.getContentsList(siteMode, lang, param);
	}
	@Override
	public List<Object> getStatsList(String siteMode, Map<String, Object> param) {
		return dashboardDao.getStatsList(siteMode, param);
	}
}