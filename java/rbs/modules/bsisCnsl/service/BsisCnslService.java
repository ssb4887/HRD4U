package rbs.modules.bsisCnsl.service;

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
public interface BsisCnslService {

	/**
	 * 전체 목록 수(dct)
	 * @param param
	 * @return
	 */
	public int getTotalCount(Map<String, Object> param);
	
	/**
	 * 전체 목록 수(dct)
	 * @param param
	 * @return
	 */
	public int getMyTotalCount(Map<String, Object> param);
	
	/**
	 * 전체 목록 수(web)
	 * @param param
	 * @return
	 */
	public int getBscTotalCount(Map<String, Object> param);
	
	/**
	 * 전체 목록 수(web)
	 * @param param
	 * @return
	 */
	public int getBscInitTotalCount(Map<String, Object> param);
	
	/**
	 * 전체 목록
	 * @param param
	 * @return
	 */
	public List<Object> getList(Map<String, Object> param);
	
	/**
	 * (web) 기초컨설팅 이력
	 * @param param
	 * @return
	 */
	public List<Object> getMyList(Map<String, Object> param);
	
	/**
	 * 기초진단서 목록
	 * @param param
	 * @return
	 */
	public List<Object> getBscList(Map<String, Object> param);

	/**
	 * 기초진단서 목록(기초컨설팅, 설문조사를 하지 않은 기초진단서만)
	 * @param param
	 * @return
	 */
	public List<Object> getBscInitList(Map<String, Object> param);

	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	public DataMap getView(Map<String, Object> param);
	
	/**
	 * 기초진단 식별번호를 이용하여 설문조사 조회
	 * @param param
	 * @return
	 */
	public DataMap isQustnrExistsByBsc(Map<String, Object> param);
	
	/**
	 * 설문조사지 조회
	 * @param param
	 * @return
	 */
	public List<Object> getQustnrView(Map<String, Object> param);
	
	/**
	 * 설문조사지 답변 상세조회
	 * @param param
	 * @return
	 */
	public List<Object> getQustnrAnswer(Map<String, Object> param);
	
	/**
	 * 설문조사지 답변만 조회
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getQustnrAnswerOnly(Map<String, Object> param);
	

	/**
	 * 설문조사지 답변 수정
	 * @param param
	 * @return
	 */
	public int setQustnrResult(Map<String, Object> param, String regiIp);
	
	/**
	 * 설문조사지 답변 디테일 수정
	 * @param param
	 * @return
	 */
	public int updateQustnrAnswers(List<Map<String, Object>> param);
	
	/**
	 * 설문조사지 답변 디테일 등록
	 * @param param
	 * @return
	 */
	public int insertQustnrAnswers(List<Map<String, Object>> param);
	
	
	/**
	 * 설문조사지 bscIdx를 이용하여 rsltIdx 가져오기
	 * @param bscIdx
	 * @return
	 */
	public int getRsltIdx(Map<String, Object> bscIdx);
	
	
	/**
	 * 관리대장용 목록 불러오기
	 * @param param
	 * @return
	 */
	public List<?> getListForAdmin(Map<String, Object> param);
	
	// ************************************************************************
	
	/**
	 * 파일 상세조회
	 * @param param
	 * @return
	 */
	public DataMap getFileView(Map<String, Object> param);
	
	/**
	 * multi파일 상세조회
	 * @param param
	 * @return
	 */
	public DataMap getMultiFileView(Map<String, Object> param);
	
	/**
	 * 권한여부
	 * @param param
	 * @return
	 */
	public int getAuthCount(Map<String, Object> param);
	
	/**
	 * 다운로드 수 수정
	 * @param keyIdx
	 * @return
	 * @throws Exception
	 */
	public int updateFileDown(String keyIdx, String fileColumnName) throws Exception;
	
	/**
	 * multi file 다운로드 수 수정
	 * @param keyIdx
	 * @return
	 * @throws Exception
	 */
	public int updateMultiFileDown(String keyIdx, int fleIdx, String itemId) throws Exception;
		
	/**
	 * 수정 상세조회
	 * @param param
	 * @return
	 */
	public DataMap getModify(Map<String, Object> param);
	
	/**
	 * 등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int insert(String uploadModulePath,  String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;
	
	/**
	 * 수정처리 : 화면에 조회된 정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
	 * @param uploadModulePath
	 * @param keyIdx
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
//	public int update(String uploadModulePath, String keyIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;

	/**
	 * 삭제 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getDeleteCount(Map<String, Object> param);
	
	/**
	 * 삭제 전체 목록
	 * @param param
	 * @return
	 */
	public List<Object> getDeleteList(Map<String, Object> param);
	
	/**
	 * 삭제처리 : 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
	 * @param parameterMap
	 * @param deleteIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	public int delete(ParamForm parameterMap, String[] deleteIdxs, String regiIp, JSONObject settingInfo) throws Exception;
	
	/**
	 * 복원처리 : 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
	 * @param restoreIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	public int restore(String[] restoreIdxs, String regiIp, JSONObject settingInfo) throws Exception;

	/**
	 * 완전삭제처리 : 화면에 조회된 정보를 데이터베이스에서 삭제
	 * @param uploadModulePath
	 * @param deleteIdxs
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int cdelete(String uploadModulePath,  String[] deleteIdxs, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;

	/**
	 * mult file 전체 목록 : 항목ID에 대한 HashMap
	 * @param keyIdx
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	public Map<String, List<Object>> getMultiFileHashMap(String keyIdx, JSONObject settingInfo, JSONObject items, JSONArray itemOrder);
	
	/**
	 * mult data 전체 목록 : 항목ID에 대한 HashMap
	 * @param keyIdx
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	public Map<String, List<Object>> getMultiHashMap(String keyIdx, JSONObject settingInfo, JSONObject items, JSONArray itemOrder);
	
	/**
	 * 파일등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getFileUpload(String uploadModulePath,  String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;

	
	/**
	 * 해당 bplNo로 완료된 기초컨설팅 갯수 조회
	 * @param bplNo
	 * @return
	 */
	public HashMap<String, Object> getCompletedBsisCnslOne(String bplNo);

	/**
	 * bplNo로 협약 조회
	 * @param bplNo
	 * @return
	 */
	public List<HashMap<String, Object>> selectCompletedAgremBybplNo(String bplNo);
	
	/**
	 * bsiscnslIdx로 기초컨설팅 조회
	 * @param param
	 * @return
	 */
	public Map<String, Object> getBsisCnslByIdx(Map<String, Object> param);
	
	/**
	 * 보고서전달방식 수정
	 * @param param
	 * @return
	 */
	public int updateFtfYn(Map<String, Object> param);
}