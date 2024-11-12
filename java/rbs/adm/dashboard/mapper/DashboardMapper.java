package rbs.adm.dashboard.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import rbs.egovframework.mapper.RbsAbstractMapper;

/**
 * 다기능게시판에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("admDashboardMapper")
public class DashboardMapper extends RbsAbstractMapper{

	public List<Object> getWebsiteList(String siteMode, Map<String, Object> param){
        return (List<Object>)selectList("rbs." + siteMode + ".dashboard.dashboardMapper.websiteList", getLangParam(param));
    }
	
	public DataMap getWebsiteView(String siteMode, Map<String, Object> param) {
        return (DataMap)selectOne("rbs." + siteMode + ".dashboard.dashboardMapper.websiteView", getLangParam(param));
	}

	public List<Object> getContentsList(String siteMode, String lang, Map<String, Object> param){
        return (List<Object>)selectList("rbs." + siteMode + ".dashboard.dashboardMapper.contentsList", getLangParam(lang, param));
    }

	public List<Object> getStatsList(String siteMode, Map<String, Object> param){
        return (List<Object>)selectList("rbs." + siteMode + ".dashboard.dashboardMapper.statsList", param);
    }
}