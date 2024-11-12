package rbs.modules.supportForm.service;

import java.util.List;
import java.util.Map;

import com.woowonsoft.egovframework.form.DataMap;


public interface SupportFormService {

	/**
	 * 서식그룹 전체 목록
	 */
	public List<Object> getList(Map<String, Object> param);

	/**
	 * 서식그룹 전체 목록 수
	 */
	public int getTotalCount(Map<String, Object> param);
	
	/**
	 * 서식그룹 상세조회
	 */
	public DataMap getView(Map<String, Object> param);
	
	/**
	 * 방문기업서식 목록
	 */
	public List<Object> getFormList(Map<String, Object> param);
	
	/**
	 * 방문기업서식 목록 수
	 */
	public int getFormCount(Map<String, Object> param);
	
	/**
	 * 서식그룹 등록처리
	 */
	public void setFormAndIdx(Map<String, Object> param, String sptjIdx, Map<String, Object> dataMap);
	
	/**
	 * 서식그룹 삭제
	 */
	public int delete(Map<String, Object> param);

	/**
	 * 서식그룹별 방문기업서식 IDX 조회
	 */
	public List<Map<String, Object>> getFormIdx(Map<String, Object> param);

}