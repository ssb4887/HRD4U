package rbs.modules.sojt.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("sojtMapper")
public class SojtMapper extends EgovAbstractMapper{
	public Map getApplyContent(Map param) {
		return (Map)selectOne("rbs.modules.sojt.sojtMapper.getApplyContent", param);
	}
	
	public Map getAcceptContnt(Map param) {
		return (Map)selectOne("rbs.modules.sojt.sojtMapper.getAcceptContent", param);
	}
	
	public Map getAcceptChecklist(Map param) {
		return (Map)selectOne("rbs.modules.sojt.sojtMapper.getAcceptChecklist", param);
	}
	
	public int getNextIdSojt() {
		return (int)selectOne("rbs.modules.sojt.sojtMapper.getNextIdSojt");
	}
	
	public int getNextIdSojtConfm(Map param) {
		return (int)selectOne("rbs.modules.sojt.sojtMapper.getNextIdSojtConfm", param);
	}
	
	public void putINSERTsojtLog(Map param) {
		insert("rbs.modules.sojt.sojtMapper.putINSERTsojtLog", param);
	}
	
	public List<Map> getPrvtcntrList() {
		return selectList("rbs.modules.sojt.sojtMapper.getPrvtcntrList");
	}
	
	public void putApply(Map param) {
		insert("rbs.modules.sojt.sojtMapper.putApply", param);
	}
	
	public void editApply(Map param) {
		update("rbs.modules.sojt.sojtMapper.editApply", param);
	}
	
	public List<Map> initList(Map param) {
		return selectList("rbs.modules.sojt.sojtMapper.initList", param);
	}
	
	public List<Map> getUserList(Map param) {
		return selectList("rbs.modules.sojt.sojtMapper.getUserList", param);
	}
	
	public List<Map> amIapplying(Map param) {
		return selectList("rbs.modules.sojt.sojtMapper.amIapplying", param);
	}
	public List<Map> getApplyList4Instt(Map param) {
		return selectList("rbs.modules.sojt.sojtMapper.getApplyList4Instt", param);
	}
	public void updateStatus(Map param) {
		update("rbs.modules.sojt.sojtMapper.updateStatus", param);
	}
	public List<Map> getApplyList4Head(Map param) {
		return selectList("rbs.modules.sojt.sojtMapper.getApplyList4Head", param);
	}
	public List<Map> getAcceptList4Head(Map param) {
		return selectList("rbs.modules.sojt.sojtMapper.getAcceptList4Head", param);
	}
	public List<Map> getAcceptList4Instt(Map param) {
		return selectList("rbs.modules.sojt.sojtMapper.getAcceptList4Instt", param);
	}
	public List<Map> getResultList4Head(Map param) {
		return selectList("rbs.modules.sojt.sojtMapper.getResultList4Head", param);
	}
	public List<Map> getResultList4Instt(Map param) {
		return selectList("rbs.modules.sojt.sojtMapper.getResultList4Instt", param);
	}
	public void updateSOJTreq(Map param) {
		update("rbs.modules.sojt.sojtMapper.updateSOJTreq", param);
	}
	public int checkValidity(Map param) {
		return (int)selectOne("rbs.modules.sojt.sojtMapper.checkValidity", param);
	}
	public Map<String, Object> getBrffcCd(String substring) {
		return (Map<String, Object>) selectOne("rbs.modules.sojt.sojtMapper.getBrffcCd", substring);
	}
	public String getBrffcTel(Map<String, Object> param) {
		return (String) selectOne("rbs.modules.sojt.sojtMapper.getBrffcTel", param);
	}
	public String getRegiIdx(Map param) {
		return (String) selectOne("rbs.modules.sojt.sojtMapper.getRegiIdx", param);
	}
	public int getSojtIdxFromWithdraw(String bsiscnsl_idx) {
		List result = (List)selectList("rbs.modules.sojt.sojtMapper.getSojtIdxFromWithdraw", bsiscnsl_idx);
		if(result.size() > 0) {
			int idx = Integer.parseInt((String) result.get(0));
			return idx;
		}
		return -1;
		
	}
}