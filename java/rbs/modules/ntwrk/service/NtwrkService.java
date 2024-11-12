package rbs.modules.ntwrk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ParamForm;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 샘플모듈에 관한 인터페이스클래스를 정의한다.
 * @author user
 *
 */
public interface NtwrkService {

	/**
	 * 네트워크 이력 저장
	 * @param formdata
	 * @param cmptList
	 * @return
	 */
	public Map<String, Object> insertNtwrkData(Map<String, Object> formdata, List<Integer> cmptList);
	
	/**
	 * 네트워크 이력 관련 문서(첨부파일)에 대한 데이터 DB저장
	 * @param param
	 * @return
	 */
	public int insertFileData(Map<String, Object> param);
	
	/**
	 * 네투워크 이력 가져오기
	 * @param param
	 * @return
	 */
	public List<Object> getNtwrkList(Map<String, Object> param);
	
	/**
	 * 네트워크 이력 총갯수 가져오기
	 * @param param
	 * @return
	 */
	public int getNtwrkTotalCount(Map<String, Object> param);
	
	/**
	 * 네트워크 이력 상세보기
	 * @param param
	 * @return
	 */
	public Map<String, Object> getNtwrkOne(Map<String, Object> param);
	
	/**
	 * 네트워크 이력 상세보기(관련 기관 가져오기)
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getNtwrkCmptinstList(Map<String, Object> param);
	
	/**
	 * 네트워크 이력 삭제하기
	 * @param param
	 * @return
	 */
	public int deleteNtwrk(Map<String, Object> param);
	
	/**
	 * 협의체(혹은 유관기관)별 네트워크 이력 가져오기
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getNtwrkListByCmptinst(Map<String, Object> param);
	
	/**
	 * 협의체(혹은 유관기관)별 네트워크 첨부파일 가져오기
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getNtwrkFileList(Map<String, Object> param);
	
	/**
	 * 네트워크 첨부파일 가져오기
	 * @param param
	 * @return
	 */
	public Map<String, Object> getNtwrkFileOne(Map<String, Object> param);
	
	
	/**
	 * 네트워크 첨부파일 업데이트
	 * @param param
	 * @return
	 */
	public int updateNtwrkFileData(List<Integer> param, int ntwrkIdx);
	
	/**
	 * 네트워크 목록 엑셀 다운로드
	 * @param param
	 * @return
	 */
	public List<Object> getNtwrkListForExcel(Map<String, Object> param);
	
}