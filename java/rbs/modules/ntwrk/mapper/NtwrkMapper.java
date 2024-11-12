package rbs.modules.ntwrk.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("ntwrkMapper")
public class NtwrkMapper extends EgovAbstractMapper{
    
	public int insertNtwrkData(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkMapper.insertNtwrkData", param);
	}
	
	public int insertNtwrkCmptinst(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkMapper.insertNtwrkCmptinst", param);
	}
	
	public int insertFileData(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkMapper.insertFileData", param);
	}
	
	public List<Object> getNtwrkList(Map<String, Object> param) {
		return selectList("rbs.modules.ntwrk.ntwrkMapper.selectNtwrkList", param);
	}
	
	public int getNtwrkTotalCount(Map<String, Object> param) {
		return selectOne("rbs.modules.ntwrk.ntwrkMapper.selectNtwrkTotalCount", param);
	}
	
	public Map<String, Object> getNtwrkOne(Map<String, Object> param) {
		return selectOne("rbs.modules.ntwrk.ntwrkMapper.selectNtwrkOne", param);
	}
	
	public List<Map<String, Object>> getNtwrkCmptinstList(Map<String, Object> param) {
		return selectList("rbs.modules.ntwrk.ntwrkMapper.selectNtwrkCmptinstList", param);
	}
	
	public int deleteNtwrk(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkMapper.deleteNtwrk", param);
	}
	
	public List<Map<String, Object>> getNtwrkListByCmptinst(Map<String, Object> param) {
		return selectList("rbs.modules.ntwrk.ntwrkMapper.selectNtwrkListByCmptinst", param);
	}

	public List<Map<String, Object>> getNtwrkFileList(Map<String, Object> param) {
		return selectList("rbs.modules.ntwrk.ntwrkMapper.selectNtwrkFileList", param);
	}
	
	public Map<String, Object> getNtwrkFileOne(Map<String, Object> param) {
		return selectOne("rbs.modules.ntwrk.ntwrkMapper.selectNtwrkFileOne", param);
	}
	
	public int updateNtwrkData(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkMapper.updateNtwrkData", param);
	}
	
	public int deleteNtwrkCmptinst(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkMapper.deleteNtwrkCmptinst", param);
	}
	
	public int updateNtwrkFileData(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkMapper.updateNtwrkFileData", param);
	}
	
	public List<Object> getNtwrkListForExcel(Map<String, Object> param) {
		return selectList("rbs.modules.ntwrk.ntwrkMapper.selectNtwrkListForExcel", param);
	}
}