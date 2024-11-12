package rbs.modules.diagnosis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("diagnosisMapper")
public class DiagnosisMapper extends EgovAbstractMapper{

    /**
     * 기초진단 이력 목록 조회
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.diagnosis.diagnosisMapper.selectList", param);
    }
	
	/**
	 * 기초진단 이력 갯수 조회
	 */
	public int getTotalCount(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.diagnosis.diagnosisMapper.selectCount", param);
	}

	/**
	 * 특정기업 기초진단 이력 목록 조회
	 */
	public List<Object> getBplList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.diagnosis.diagnosisMapper.selectBplList", param);
	}
	
	/**
	 * 특정기업 기초진단 이력 갯수 조회
	 */
	public int getBplCount(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.diagnosis.diagnosisMapper.selectBplCount", param);
	}

	/**
	 * 기업바구니 목록
	 */
	public List<Object> getBskList(Map<String, Object> param){
		return selectList("rbs.modules.diagnosis.diagnosisMapper.bskList", param);
    }
	
	/**
	 * 기업바구니 목록 수
	 */
	public int getBskCnt(Map<String, Object> param){
		return (Integer)selectOne("rbs.modules.diagnosis.diagnosisMapper.bskCount", param);
    }
    
    /**
     * 파일항목 목록
     * @param param
     * @return
     */
	public List<Object> getFileList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.diagnosis.diagnosisMapper.selectFileList", param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getFileView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.diagnosis.diagnosisMapper.selectFileView", param);
	}
	
    /**
     * 기초진단 내역
     */
	public DataMap getBsc(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.diagnosis.diagnosisMapper.selectBsc", param);
	}
	
    /**
     * 로그인 대상 정보 조회
     */
	public Map getDoc(String memberIdx) {
		return (Map)selectOne("rbs.modules.diagnosis.diagnosisMapper.selectDoc", memberIdx);
	}
	
	/**
	 * 훈련지원 내역
	 */
	public List<Object> getFundHis(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.diagnosis.diagnosisMapper.selectFund", param);
	}
	
	/**
	 * 훈련실시 내역
	 */
	public List<Object> getTrHis(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.diagnosis.diagnosisMapper.selectTr", param);
	}
	
	/**
	 * 참여가능사업 내역
	 */
	public List<Object> getPrtbiz(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.diagnosis.diagnosisMapper.selectPrtbiz", param);
	}
	
	/**
	 * 기초진단 실행
	 * 
	 */
	public Map getBSK(long BPL_NO) {
		return selectOne("rbs.modules.diagnosis.diagnosisMapper.getBSK", BPL_NO);
	}

	/**
     * 권한여부 조회
     * @param param
     * @return
     */
    public int getAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.diagnosis.diagnosisMapper.authCount", param);
    }
    
	public HashMap<String, Object> selectByBplNo(String bplNo) {
		return selectOne("rbs.modules.diagnosis.diagnosisMapper.selectByBplNo", bplNo);
	}
}