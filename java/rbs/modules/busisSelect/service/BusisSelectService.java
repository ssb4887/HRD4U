package rbs.modules.busisSelect.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woowonsoft.egovframework.form.DataMap;


public interface BusisSelectService {

    /**
     * 방문기업관리 이력 조회
     */
	public List<Object> getList(Map<String, Object> param);

	/**
	 * 방문기업관리 이력 갯수 조회
	 */
	public int getTotalCount(Map<String, Object> param);

	/**
	 * 기업바구니 목록 조회
	 */
	public List<Object> getBskList(Map<String, Object> param);
	
	/**
	 * 기업바구니 갯수 조회
	 */
	public int getBskCount(Map<String, Object> param);

	/**
	 * 방문기업관리 상세조회
	 */
	public Map<String, Object> getSptjdgns(Map<String, Object> param);
	
	/**
	 * 방문기업서식 목록 조회
	 */
	public List<Object> getSupportList(Map<String, Object> param);
	
	/**
	 * 방문기업서식 갯수 조회
	 */
	public int getSupportCount(Map<String, Object> param);
	
	/**
	 * 방문기업서식 상세 조회
	 */
	public DataMap getSptj(Map<String, Object> param);

	/**
	 * 전자서명문서 발행여부 조회
	 */
	public DataMap getPublished(Map<String, Object> param);

	/**
	 * 기업정보 상세 조회
	 */
	public DataMap getBpl(Map<String, Object> param);
	
	/**
	 * 훈련이력 조회
	 */
	public DataMap getTr(Map<String, Object> param);
	
	
	/**
	 * 소속기관 목록
	 */
	public List<Object> getInsttList(int fnIdx, Map<String, Object> param);
	
	/**
	 * 업종 목록
	 */
	public List<HashMap<String, Object>> getIndustCd();
	
	/**
	 * 자동분류 조회
	 */
	public DataMap getALSclas(Map<String, Object> param);
	
	/**
	 * 수동분류 조회
	 */
	public DataMap getPLSclas(Map<String, Object> param);

	/**
	 * 전자서명 등록 정보 조회
	 */
	public Map<String, Object> getSSInfo(String formatId);
	
	/**
	 * 방문기업관리 등록처리
	 */
	public int setForm(Map<String, Object> param);
	
	/**
	 * 권한여부
	 */
	public int getAuthCount(Map<String, Object> param);

}