package rbs.modules.authManage.service;

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
public interface AuthManageService {

	/**
	 * 전체 목록 수
	 * @param param
	 * @return
	 */
	public int getTotalCount(Map<String, Object> param);
	
	/**
	 * 전체 목록
	 * @param param
	 * @return
	 */
	public List<Object> getList(Map<String, Object> param);
	
	/**
	 * 전체 목록
	 * @param param
	 * @return
	 */
	public List<Object> getCodeList(Map<String, Object> param);
	
	/**
	 * 로그인한 회원의 authIdx
	 * @param param
	 * @return String
	 */
	public Integer getAuthIdx(Map<String, Object> param);
	
	/**
	 * 로그인한 회원의 authIdx, siteId, menuIdx에 따른 권한 가져오기
	 * @param param
	 * @return String
	 */
	public DataMap getAuthMenu(Map<String, Object> param);
	
	/**
	 * 전체 권한부여 목록
	 * @param param
	 * @return
	 */
	public List<Object> getAuthGrant(Map<String, Object> param);

	/**
	 * 전체 메뉴 목록
	 * @param param
	 * @return
	 */
	public List<Object> getAuthList();
	
	/**
	 * 권한 그룹 목록
	 * @param param
	 * @return
	 */
	public List<Object> getAuthGroupList();
	
	/**
	 * 권한 그룹 목록
	 * @author 이동근
	 * @param param "not in " 에 부합하는 조건
	 * @return
	 */
	public List<Object> getAuthGroupList(Map<String, Object> param);
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	public DataMap getView(Map<String, Object> param);
	
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
	public int updateFileDown(int keyIdx, String fileColumnName) throws Exception;
	
	/**
	 * multi file 다운로드 수 수정
	 * @param keyIdx
	 * @return
	 * @throws Exception
	 */
	public int updateMultiFileDown(int keyIdx, int fleIdx, String itemId) throws Exception;
		
	/**
	 * 수정 상세조회
	 * @param param
	 * @return
	 */
	public DataMap getModify(Map<String, Object> param);
	
	/**
	 * 회원정보 조회
	 * @param param
	 * @return
	 */
	public DataMap getMember(Map<String, Object> param);
	
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
	public int update(String uploadModulePath, String keyIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;

	/**
	 * 수정처리 : 화면에 조회된 정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
	 * @param authIdx
	 * @param memberList
	 * @return
	 * @throws Exception
	 */
	public int updateAll(String authIdx, String memberList) throws Exception;
	
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
	 * 적용처리 : 화면에 조회된 정보를 데이터베이스에서 적용불가처리 flag 반영
	 * @param parameterMap
	 * @param deleteIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	public int apply(ParamForm parameterMap, String keyIdx, String regiIp, JSONObject settingInfo) throws Exception;
	
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
}