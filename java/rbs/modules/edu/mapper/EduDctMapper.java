package rbs.modules.edu.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("eduDctMapper")
public class EduDctMapper extends EgovAbstractMapper {
	
	public int insertEdc(Map<String, Object> param) {
		return super.update("rbs.modules.edu.eduDctMapper.insertEdc", param);
	}

	public int updateEdc(Map<String, Object> param) {
		return super.update("rbs.modules.edu.eduDctMapper.updateEdc", param);
	}
	
	public List<Map<String, Object>> getEdcFileList(Map<String, Object> param) {
		return selectList("rbs.modules.edu.eduDctMapper.selectEdcFileList", param);
	}
	
	public int updateEdcFileData(Map<String, Object> param) {
		return super.update("rbs.modules.edu.eduDctMapper.updateEdcFileData", param);
	}
	
	public int insertEdcFileData(Map<String, Object> param) {
		return super.update("rbs.modules.edu.eduDctMapper.insertEdcFileData", param);
	}
	
	public List<Map<String, Object>> getEdcList(Map<String, Object> param) {
		return selectList("rbs.modules.edu.eduDctMapper.selectEdcList", param);
	}
	
	public int getEdcTotalCount(Map<String, Object> param) {
		return selectOne("rbs.modules.edu.eduDctMapper.selectEdcTotalCount", param);
	}
	
	public int deleteEdc(Map<String, Object> param) {
		return super.update("rbs.modules.edu.eduDctMapper.deleteEdc", param);
	}
	
	public Map<String, Object> getEdc(Map<String, Object> param) {
		return selectOne("rbs.modules.edu.eduDctMapper.selectEdc", param);
	}
	
	public Map<String, Object> getEdcFileOne(Map<String, Object> param) {
		return selectOne("rbs.modules.edu.eduDctMapper.selectEdcFileOne", param);
	}
	
	public List<Object> getMemberList(Map<String, Object> param) {
		return selectList("rbs.modules.edu.eduDctMapper.selectMemberList", param);
	}
	
	public int insertEdcMember(Map<String, Object> param) {
		return super.update("rbs.modules.edu.eduDctMapper.insertEdcMember", param);
	}
	
	public int isExistsEdcMember(Map<String, Object> param) {
		return selectOne("rbs.modules.edu.eduDctMapper.isExistsEdcMember", param);
	}
	
	public int updateEdcMember(Map<String, Object> param) {
		return super.update("rbs.modules.edu.eduDctMapper.updateEdcMember", param);
	}
	
	public List<Object> getEdcMemberList(Map<String, Object> param) {
		return selectList("rbs.modules.edu.eduDctMapper.selectEdcMemberList", param);
	}
	
	public int getEdcMemberListCount(Map<String, Object> param) {
		return selectOne("rbs.modules.edu.eduDctMapper.selectEdcMemberListCount", param);
	}
	
	public int deleteEdcMembers(Map<String, Object> param) {
		return super.update("rbs.modules.edu.eduDctMapper.deleteEdcMembers", param);
	}
	
	public int changeEdcMemberStatus(Map<String, Object> param) {
		return super.update("rbs.modules.edu.eduDctMapper.changeEdcMemberStatus", param);
	}
	
	public Map<String, Object> getEdcMember(Map<String, Object> param) {
		return selectOne("rbs.modules.edu.eduDctMapper.selectEdcMember", param);
	}
	
	public int changeManyEdcMemberStatus(Map<String, Object> param) {
		return super.update("rbs.modules.edu.eduDctMapper.changeManyEdcMemberStatus", param);
	}
	
	public Map<String, Object> getMemberOrderInEdc(Map<String, Object> param) {
		return selectOne("rbs.modules.edu.eduDctMapper.selectMemberOrderInEdc", param);
	}
	
	public int issueCertificate(Map<String, Object> param) {
		return super.update("rbs.modules.edu.eduDctMapper.issueCertificate", param);
	}
	
	public int getMyEdcHistoryCount(Map<String, Object> param) {
		return selectOne("rbs.modules.edu.eduDctMapper.selectMyEdcHistoryCount", param);
	}

	public List<Object> getMyEdcHistoryList(Map<String, Object> param) {
		return selectList("rbs.modules.edu.eduDctMapper.selectMyEdcHistoryList", param);
	}
	
	public int chagneEdcMemberStatusConfm(Map<String, Object> param) {
		return super.update("rbs.modules.edu.eduDctMapper.chagneEdcMemberStatusConfm", param);
	}
	
	public int changeManyEdcMemberStatusConfm(Map<String, Object> param) {
		return super.update("rbs.modules.edu.eduDctMapper.changeManyEdcMemberStatusConfm", param);
	}
	
	public List<Map<String, Object>> getEdcListForExcel(Map<String, Object> param) {
		return selectList("rbs.modules.edu.eduDctMapper.selectEdcListForExcel", param);
	}
	
	public Map<String, Object> getMemberIdxById(Map<String, Object> param) {
		return selectOne("rbs.modules.edu.eduDctMapper.selectMemberIdxById", param);
	}
	
	public int insertEdcMembers(Map<String, Object> param) {
		return super.update("rbs.modules.edu.eduDctMapper.insertEdcMembers", param);
	}
	
	public int deleteCertificate(Map<String, Object> param) {
		return super.update("rbs.modules.edu.eduDctMapper.deleteCertificate", param);
	}
}
