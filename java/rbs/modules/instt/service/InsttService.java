package rbs.modules.instt.service;

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
public interface InsttService {
	
	/**
	 * 전체 목록 수
	 * @param param
	 * @return
	 */
	public int getTotalCount(Map<String, Object> param);
	
	/**
	 * 우편번호별 전체 목록 수
	 * @param param
	 * @return
	 */
	public int getTotalMailCount(Map<String, Object> param);
	
	/**
	 * 관할구역 전체 목록 수
	 * @param param
	 * @return
	 */
	public int getMultiTotalCount(Map<String, Object> param);
	
	/**
	 * 전체 목록
	 * @param param
	 * @return
	 */
	public List<Object> getList(Map<String, Object> param);
	
	/**
	 * 우편번호별 전체 목록
	 * @param param
	 * @return
	 */
	public List<Object> getMailList(Map<String, Object> param);
	
	/**
	 * 관할구역 전체 목록
	 * @param param
	 * @return
	 */
	public List<Object> getMultiList(Map<String, Object> param);
	
	/**
	 * 관할구역 전체 목록
	 * @param param
	 * @return
	 */
	public List<Object> getOptionList(Map<String, Object> param);
	
	/**
	 * 검색을 위한 지부지사 목록 가져오기
	 * @param param
	 * @return
	 */
	public List<Object> getInsttList();

	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	public DataMap getView(Map<String, Object> param);
	
	/**
	 * 시/도의 군/구 갯수 목록
	 * @param param
	 * @return
	 */
	public List<Object> getOptionCount(Map<String, Object> param);
	
	/**
	 * 파일 상세조회
	 * @param param
	 * @return
	 */
	public DataMap getFileView(int fnIdx, Map<String, Object> param);
	
	/**
	 * multi파일 상세조회
	 * @param param
	 * @return
	 */
	public DataMap getMultiFileView(int fnIdx, Map<String, Object> param);
	
	/**
	 * 권한여부
	 * @param param
	 * @return
	 */
	public int getAuthCount(Map<String, Object> param);
	
	/**
	 * 다운로드 수 수정
	 * @param insttIdx
	 * @return
	 * @throws Exception
	 */
	public int updateFileDown(int fnIdx, int keyIdx, String fileColumnName) throws Exception;
	
	/**
	 * multi file 다운로드 수 수정
	 * @param insttIdx
	 * @return
	 * @throws Exception
	 */
	public int updateMultiFileDown(int fnIdx, int keyIdx, int fleIdx, String itemId) throws Exception;
		
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
	public int insert(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;
	
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
	public int mailInsert(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;
	
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
	public int update(String uploadModulePath, int keyIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;

	/**
	 * 삭제 전체 목록 수
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
	public int delete(ParamForm parameterMap, int[] deleteIdxs, String regiIp, JSONObject settingInfo) throws Exception;
	
	/**
	 * 삭제처리 : 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
	 * @param parameterMap
	 * @param deleteIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	public int mailDelete(ParamForm parameterMap, String[] deleteIdxs, String regiIp, JSONObject settingInfo) throws Exception;
	
	/**
	 * 복원처리 : 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
	 * @param restoreIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	public int restore(int[] restoreIdxs, String regiIp, JSONObject settingInfo) throws Exception;

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
	public int cdelete(String uploadModulePath, int[] deleteIdxs, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;

	/**
	 * mult data 전체 목록 : 항목ID에 대한 HashMap
	 * @param keyIdx
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	public List<Object> getMultiHashMap(int keyIdx, JSONObject settingInfo, JSONObject items, JSONArray itemOrder);
	
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
	public Map<String, Object> getFileUpload(String uploadModulePath, int fnIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;
	
	
	/**
	 * 우편번호별 지부지사 일괄변경
	 * @author 석수빈
	 * @param zip 우편번호, insttIdx 지부지사, zipList 변경할 우편번호
	 * @param regiIp 
	 * @return
	 */
	public int zipAllUpdate (String insttIdx, String zipList, String regiIp);
}