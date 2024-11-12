package rbs.modules.ntwrk.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 지역네트워크 내 관할 전문가 모듈에 관한 데이터 접근 클래스를 정의
 * @author minjung
 *
 */
@Repository("ntwrkExpertMapper")
public class NtwrkExpertMapper extends EgovAbstractMapper{
    
	public int insertExpert(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkExpertMapper.insertExpert", param);
	}
	
	public int updateExpert(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkExpertMapper.updateExpert", param);
	}
	
	public int insertExpertPartnerInstt(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkExpertMapper.insertExpertPartnerInstt", param);
	}
	
	public int updateExpertPartnerInstt(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkExpertMapper.updateExpertPartnerInstt", param);
	}
	
	public List<Map<String, Object>> getExpertPartnerInstts(Map<String, Object> param) {
		return selectList("rbs.modules.ntwrk.ntwrkExpertMapper.selectExpertPartnerInstts", param);
	}
	
	public List<Map<String, Object>> getExpertList(Map<String, Object> param) {
		return selectList("rbs.modules.ntwrk.ntwrkExpertMapper.selectExpertList", param);
	}
	
	public int getExpertTotalCount(Map<String, Object> param) {
		return selectOne("rbs.modules.ntwrk.ntwrkExpertMapper.selectExpertTotalCount", param);
	}
	
	public int insertExpertFileData(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkExpertMapper.insertExpertFileData", param);
	}
	
	public int updateExpertFileData(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkExpertMapper.updateExpertFileData", param);
	}
	
	public List<Map<String, Object>> getExpertFileList(Map<String, Object> param) {
		return selectList("rbs.modules.ntwrk.ntwrkExpertMapper.selectExpertFileList", param);
	}
	
	public Map<String, Object> getExpertOne(Map<String, Object> param) {
		return selectOne("rbs.modules.ntwrk.ntwrkExpertMapper.selectExpertOne", param);
	}
	
	public int deleteExpert(Map<String, Object> param) {
		return super.update("rbs.modules.ntwrk.ntwrkExpertMapper.deleteExpert", param);
	}
	
}