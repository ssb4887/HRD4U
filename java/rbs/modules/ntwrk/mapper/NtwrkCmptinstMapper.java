package rbs.modules.ntwrk.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("ntwrkCmptinstMapper")
public class NtwrkCmptinstMapper extends EgovAbstractMapper{
	
	public int cmptinstDataUpload(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkCmptinstMapper.insertCmptinstData", param);
	}
	
	public Map<String, Object> getUserInsttIdx(Map<String, Object> param) {
		return selectOne("rbs.modules.ntwrk.ntwrkCmptinstMapper.selectUserInsttIdx", param);
	}
	
	public List<Object> getCmptinstList(Map<String, Object> param) {
		return selectList("rbs.modules.ntwrk.ntwrkCmptinstMapper.selectCmptinstList", param);
	}
	
	public int getCmptinstTotalCount(Map<String, Object> param) {
		return selectOne("rbs.modules.ntwrk.ntwrkCmptinstMapper.selectCmptinstTotalCount", param);
	}
	
	public int deleteCmptinst(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkCmptinstMapper.deleteCmptinst", param);
	}

	public Map<String, Object> getCmptinstOne(Map<String, Object> param) {
		return selectOne("rbs.modules.ntwrk.ntwrkCmptinstMapper.selectCmptinstOne", param);
	}
	
	public int updateCmptinstData(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkCmptinstMapper.updateCmptinstData", param);
	}
	
	public int ntwrkDataFileInsert(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkCmptinstMapper.ntwrkDataFileInsert", param);
	}
	
	public int insertAgremCorpData(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkCmptinstMapper.insertAgremCorpData", param);
	}
	
	public List<Object> getAgremCorpList(Map<String, Object> param) {
		return selectList("rbs.modules.ntwrk.ntwrkCmptinstMapper.selectAgremCorpList", param);
	}
	
	public int deleteAgremCorps(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkCmptinstMapper.deleteAgremCorps", param);
	}
	
	public int updateAgremCorpData(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkCmptinstMapper.updateAgremCorpData", param);
	}
}
