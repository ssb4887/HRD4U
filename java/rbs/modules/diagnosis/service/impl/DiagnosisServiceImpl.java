package rbs.modules.diagnosis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woowonsoft.egovframework.form.DataMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import rbs.modules.diagnosis.mapper.DiagnosisMapper;
import rbs.modules.diagnosis.service.DiagnosisService;

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("diagnosisService")
public class DiagnosisServiceImpl extends EgovAbstractServiceImpl implements DiagnosisService {

	@Resource(name="diagnosisMapper")
	private DiagnosisMapper diagnosisDAO;
	
	/**
	 * 기초진단 이력 목록 조회
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getList(Map<String, Object> param) {
		return diagnosisDAO.getList(param);
	}
	
	/**
	 * 기초진단 이력 갯수 조회
	 * @param param
	 * @return
	 */
	@Override
	public int getTotalCount(Map<String, Object> param) {
		return diagnosisDAO.getTotalCount(param);
	}

	/**
	 * 특정기업 기초진단 이력 목록 조회
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getBplList(Map<String, Object> param) {
		return diagnosisDAO.getBplList(param);
	}
	
	/**
	 * 특정기업 기초진단 이력 갯수 조회
	 * @param param
	 * @return
	 */
	@Override
	public int getBplCount(Map<String, Object> param) {
		return diagnosisDAO.getBplCount(param);
	}

	/**
	 * 기업바구니 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getBskList(Map<String, Object> param) {
		return diagnosisDAO.getBskList(param);
	}
	
	/**
	 * 기업바구니 목록 수
	 * @param param
	 * @return
	 */
	@Override
	public int getBskCnt(Map<String, Object> param) {
		return diagnosisDAO.getBskCnt(param);
	}
	
	/**
	 * 기초진단 내역
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getBsc(Map<String, Object> param) {
		DataMap viewDAO = diagnosisDAO.getBsc(param);
		return viewDAO;
	}
	
	/**
	 * 공단 담당자 정보 조회
	 */
	@Override
	public Map getDoc(String memberIdx) {
		return diagnosisDAO.getDoc(memberIdx);
	}
	
	/**
	 * 훈련지원 내역
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getFundHis(Map<String, Object> param) {
		return diagnosisDAO.getFundHis(param);
	}
	
	/**
	 * 훈련실시 내역
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getTrHis(Map<String, Object> param) {
		return diagnosisDAO.getTrHis(param);
	}

	/**
	 * 참여가능사업 내역
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getPrtbiz(Map<String, Object> param) {
		return diagnosisDAO.getPrtbiz(param);
	}
	
	/**
	 * 기초진단 실행
	 * 
	 */
	@Override
	public Map getBSK(long bpl_no) {
		return diagnosisDAO.getBSK(bpl_no);
	}
	
	/**
	 * 권한여부
	 * @param param
	 * @return
	 */
	public int getAuthCount(Map<String, Object> param) {
    	return diagnosisDAO.getAuthCount(param);
    }

	@Override
	public HashMap<String, Object> selectByBplNo(String bplNo) {
		return diagnosisDAO.selectByBplNo(bplNo);
	}
}