package rbs.modules.diagnosis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woowonsoft.egovframework.form.DataMap;


/**
 * 샘플모듈에 관한 인터페이스클래스를 정의한다.
 * @author user
 *
 */
public interface DiagnosisService {

	/**
	 * 기초진단 이력 목록 조회
	 * @param param
	 * @return
	 */
	public List<Object> getList(Map<String, Object> param);

	/**
	 * 기초진단 이력 갯수 조회
	 * @param param
	 * @return
	 */
	public int getTotalCount(Map<String, Object> param);

	/**
	 * 특정기업 기초진단 이력 목록 조회
	 * @param param
	 * @return
	 */
	public List<Object> getBplList(Map<String, Object> param);
	
	/**
	 * 특정기업 기초진단 이력 갯수 조회
	 * @param param
	 * @return
	 */
	public int getBplCount(Map<String, Object> param);

	/**
	 * 기업바구니 목록
	 * @param param
	 * @return
	 */
	public List<Object> getBskList(Map<String, Object> param);
	
	/**
	 * 기업바구니 목록 수
	 * @param param
	 * @return
	 */
	public int getBskCnt(Map<String, Object> param);

	/**
	 * 기초진단 내역
	 * @param param
	 * @return
	 */
	public DataMap getBsc(Map<String, Object> param);
	
	/**
	 * 로그인 대상 정보 조회
	 */
	public Map getDoc(String memberIdx);
	
	/**
	 * 훈련지원 내역
	 * @param param
	 * @return
	 */
	public List<Object> getFundHis(Map<String, Object> param);
	
	/**
	 * 훈련실시 내역
	 * @param param
	 * @return
	 */
	public List<Object> getTrHis(Map<String, Object> param);

	/**
	 * 참여가능사업 내역
	 * @param param
	 * @return
	 */
	public List<Object> getPrtbiz(Map<String, Object> param);
	
	
	/**
	 * 기초진단 실행
	 * 
	 */
	public Map getBSK(long bpl_no);
	
	/**
	 * 권한여부
	 * @param param
	 * @return
	 */
	public int getAuthCount(Map<String, Object> param);
	
	/**
	 * bplNo로 기초진단 이력 있는지 확인
	 * @param bplNo
	 * @return
	 */
	public HashMap<String, Object> selectByBplNo(String bplNo);


}