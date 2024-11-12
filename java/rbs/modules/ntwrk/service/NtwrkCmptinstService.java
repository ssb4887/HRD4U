package rbs.modules.ntwrk.service;

import java.util.List;
import java.util.Map;

/**
 * 지역네트워크 > 협의체 관리
 * @author minjung
 *
 */
public interface NtwrkCmptinstService {
	
	/**
	 * 협의체 목록 엑셀 업로드
	 * @param map
	 * @return
	 */
	public int cmptinstDataUpload(List<Map<String, Object>> param) throws Exception;
	
	/***
	 * 자신의 기관 식별 데이터 가져오기
	 * @param map
	 * @return
	 */
	public Map<String, Object> getUserInsttIdx(Map<String, Object> map);
	
	/**
	 * 협의체 목록 가져오기
	 * @param param
	 * @return
	 */
	public List<Object> getCmptinstList(Map<String, Object> param);
	
	/**
	 * 협의체 목록 총 갯수
	 * @param param
	 * @return
	 */
	public int getCmptinstTotalCount(Map<String, Object> param);
	
	/**
	 * 협의체 선택 삭제
	 * @param param
	 * @return
	 */
	public int deleteCmptinst(Map<String, Object> param);
	
	/**
	 * 협의체 상세 보기(one)
	 * @param param
	 * @return
	 */
	public Map<String, Object> getCmptinstOne(Map<String, Object> param);
	
	/**
	 * 협의체 저장(insert/update)
	 * @param param
	 * @return
	 */
	public int setCmptinstData(Map<String, Object> param);
	
	/**
	 * 협약기업(협의체 하위) 저장(insert)
	 * @param param
	 * @return
	 */
	public int insertAgremCorpData(Map<String, Object> param);
	
	/**
	 * 협약기업(협의체 하위) 가져오기
	 * @param param
	 * @return
	 */
	public List<Object> getAgremCorpList(Map<String, Object> param);
	
	/**
	 * 협약기업 삭제하기
	 * @param param
	 * @return
	 */
	public int deleteAgremCorps(Map<String, Object> param);
}