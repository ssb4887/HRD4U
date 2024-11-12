package rbs.usr.main.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import rbs.egovframework.mapper.RbsAbstractMapper;

/**
 * 다기능게시판에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("usrMainMapper")
public class MainMapper extends RbsAbstractMapper{
	
	public List<Object> getModuleList(String moduleId, Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.dashboard.dashboardMapper." + moduleId + "List", param);
    }

	public Map<Object, List<Object>> getBoardHashMap(Map<String, Object> param){
		List<Object> list = (List<Object>)selectList("rbs.usr.main.mainMapper.boardHashMap", getLangParam(param));
        return selectMapList(list, "MENU_IDX", 1);
    }

	public Map<Object, List<Object>> getContentsHashMap(Map<String, Object> param){
		List<Object> list = (List<Object>)selectList("rbs.usr.main.mainMapper.contentsHashMap", getLangParam(param));
        return selectMapList(list, "MENU_IDX", 1);
    }
}