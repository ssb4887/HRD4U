package rbs.modules.develop.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.form.ParamForm;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 샘플모듈에 관한 인터페이스클래스를 정의한다.
 * @author user
 *
 */
public interface DevelopDctService {
	
	/**
	 * 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getTotalCount(int fnIdx, Map<String, Object> param);
	
	/**
	 * 전체 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getList(int fnIdx, Map<String, Object> param);
	
	/**
	 * 표준개발 신청 전체 목록 수
	 * @param param
	 * @return
	 */
	public int getDevTotalCount(Map<String, Object> param);
	
	/**
	 * 표준개발 신청 전체 목록
	 * @param param
	 * @return
	 */
	public List<Object> getDevList(Map<String, Object> param);
	
	/**
	 * 미처리 상태인 목록 가져오기(표준, 맞춤 전부다)
	 * @param param
	 * @return
	 */
	public List<Object> getNoHandlingList(Map<String, Object> param);
	
	/**
	 * 훈련과정 종합관리 집계 목록(표준 + 맞춤)
	 * @param param
	 * @return
	 */
	public DataMap getSummary(Map<String, Object> param);
	
	/**
	 * 맞춤개발 비용청구 신청 목록 가져오기
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getTotalSupportCount(Map<String, Object> param);
	
	/**
	 * 맞춤개발 비용청구 신청 목록 갯수 가져오기
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getSupportList(Map<String, Object> param);
	
	/**
	 * 주치의 지원요청 내용 가져오기
	 * @param targetTable
	 * @param param
	 * @return
	 */
	public DataMap getHelpInfo(String targetTable, Map<String, Object> param);

	/**
	 * 상세조회
	 * @param targetTable
	 * @param param
	 * @return
	 */
	public DataMap getView(String targetTable, Map<String, Object> param);
	
	/**
	 * 파일 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getFileView(String targetTable, Map<String, Object> param);
	
	/**
	 * multi파일 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getMultiFileView(String targetTable, Map<String, Object> param);
	
	/**
	 * 권한여부
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getAuthCount(int fnIdx, Map<String, Object> param);
	
	/**
	 * 다운로드 수 수정
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateFileDown(String targetTable, int keyIdx, String fileColumnName) throws Exception;
	
	/**
	 * multi file 다운로드 수 수정
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateMultiFileDown(String targetTable, int keyIdx, int fleIdx, String itemId, String targetTableIdx) throws Exception;
		
	/**
	 * 수정 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getModify(int fnIdx, Map<String, Object> param);
	
	/**
	 * 등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param uploadModulePath
	 * @param fnIdx
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int insert(String uploadModulePath, int fnIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;
	
	/**
	 * 수정처리 : 화면에 조회된 정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
	 * @param uploadModulePath
	 * @param fnIdx
	 * @param keyIdx
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int update(String uploadModulePath, int fnIdx, int keyIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;

	/**
	 * 삭제 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getDeleteCount(int fnIdx, Map<String, Object> param);
	
	/**
	 * 삭제 전체 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getDeleteList(int fnIdx, Map<String, Object> param);
	
	/**
	 * 삭제처리 : 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
	 * @param fnIdx
	 * @param parameterMap
	 * @param deleteIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	public int delete(int fnIdx, ParamForm parameterMap, int[] deleteIdxs, String regiIp, JSONObject settingInfo) throws Exception;
	
	/**
	 * 복원처리 : 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
	 * @param fnIdx
	 * @param restoreIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	public int restore(int fnIdx, int[] restoreIdxs, String regiIp, JSONObject settingInfo) throws Exception;

	/**
	 * 완전삭제처리 : 화면에 조회된 정보를 데이터베이스에서 삭제
	 * @param uploadModulePath
	 * @param fnIdx
	 * @param deleteIdxs
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int cdelete(String uploadModulePath, int fnIdx, int[] deleteIdxs, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;

	/**
	 * mult file 전체 목록 : 항목ID에 대한 HashMap
	 * @param fnIdx
	 * @param keyIdx
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	public Map<String, List<Object>> getMultiFileHashMap(int fnIdx, int keyIdx, JSONObject settingInfo, JSONObject items, JSONArray itemOrder);
	
	/**
	 * mult data 전체 목록 : 항목ID에 대한 HashMap
	 * @param fnIdx
	 * @param keyIdx
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	public Map<String, List<Object>> getMultiHashMap(int fnIdx, int keyIdx, JSONObject settingInfo, JSONObject items, JSONArray itemOrder);
	
	/**
	 * 파일등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param uploadModulePath
	 * @param fnIdx
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getFileUpload(String uploadModulePath, int fnIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;

	/**
	 * HRD_DEVELOP_*** 승인상태 및 주치의 의견 업데이트(반려처리할 때 사용)
	 * @param targetTable
	 * @param targetColumn
	 * @param confmStatus
	 * @param reqIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int updateStatusAndOpinion(String targetTable, String targetTableIdx, String confmStatus, int keyIdx, String regiIp, ParamForm parameterMap) throws Exception;
	
	/**
	 * HRD_DEVELOP_*** 승인상태 업데이트(접수, 1차승인, 최종승인할 때 사용)
	 * @param targetTable
	 * @param targetColumn
	 * @param confmStatus
	 * @param cliIdx
	 * @param reqIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	public int updateStatus(String targetTable, String targetTableIdx, String confmStatus, int keyIdx, String regiIp) throws Exception;
	
	/**
	 * HRD_DEVELOP_***_CONFM 새로운 승인상태 등록(모든 메뉴에서 승인상태를 업데이트할 때 동일하게 사용)
	 * @param targetTable
	 * @param targetColumn
	 * @param confmStatus
	 * @param cliIdx
	 * @param keyIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	public int insertConfm(String targetTable, String targetTableIdx, String confmStatus, int keyIdx, String regiIp) throws Exception;
	
	/**
	 * HRD_CNSL_COST 승인상태 및 주치의 의견 업데이트(반려처리할 때 사용)
	 * @param confmStatus
	 * @param reqIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int updateStatusAndOpinionCost(String confmStatus, int keyIdx, int cnslIdx, String regiIp, ParamForm parameterMap) throws Exception;
	
	/**
	 * HRD_CNSL_COST 승인상태 업데이트(접수, 1차승인, 최종승인할 때 사용)
	 * @param confmStatus
	 * @param cliIdx
	 * @param reqIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	public int updateStatusCost(String confmStatus, int keyIdx, int cnslIdx, String regiIp) throws Exception;
	
	/**
	 * HRD_CNSL_COST_CONFM 새로운 승인상태 등록(모든 메뉴에서 승인상태를 업데이트할 때 동일하게 사용)
	 * @param confmStatus
	 * @param cliIdx
	 * @param keyIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	public int insertConfmCost(String confmStatus, int keyIdx, int cnslIdx, String regiIp) throws Exception;
	
	
	/**
	 * HRD_CNSL_COST 수정(item_info에서 modifyproc_order에 있는 컬럼만 update를 할 때 사용)
	 * @param targetTable
	 * @param targetColumn
	 * @param uploadModulePath
	 * @param fnIdx
	 * @param cliList
	 * @param doctorList
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	public int update(String targetTable, String targetColumn, String uploadModulePath, int keyIdx, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO) throws Exception;
}