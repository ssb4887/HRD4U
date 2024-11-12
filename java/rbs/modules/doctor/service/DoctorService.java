package rbs.modules.doctor.service;

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
public interface DoctorService {
	
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
	 * 전체 활동내역 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getActCount(int fnIdx, Map<String, Object> param);
	
	/**
	 * 활동 내역 전체 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getActList(int fnIdx, Map<String, Object> param);
	
	/**
	 * 회원 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getMemberList(int fnIdx, Map<String, Object> param);
	
	/**
	 * 회원 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getMemberCount(int fnIdx, Map<String, Object> param);
	
	/**
	 * 해당 주치의의 소속기관 변경 유무 확인
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getIsChange(int fnIdx, Map<String, Object> param);
	
	/**
	 * 지부지사 관할구역 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getBlockList(int fnIdx, Map<String, Object> param);
	
	/**
	 * 지부지사 관할구역에 배정된 주치의 인원 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getDoctorList(int fnIdx, Map<String, Object> param);

	/**
	 * 지부지사 관할구역 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getInsttList(int fnIdx, Map<String, Object> param);
	
	/**
	 * 주치의 관할구역 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getMultiList(int fnIdx, Map<String, Object> param);
	
	/**
	 * 주치의 요건 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getReqList(int fnIdx, Map<String, Object> param);
	
	/**
	 * 현재 사용 중인 주치의 요건 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getReqMngList(int fnIdx, Map<String, Object> param);
	
	/**
	 * 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getView(int fnIdx, Map<String, Object> param);
	
	/**
	 * 파일 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getFileView(int fnIdx, Map<String, Object> param);
	
	/**
	 * 권한여부
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getAuthCount(int fnIdx, Map<String, Object> param);
	
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
	public int update(String uploadModulePath, int fnIdx, int keyIdx, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO) throws Exception;

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
	 * mult data 전체 목록 : 항목ID에 대한 HashMap
	 * @param fnIdx
	 * @param keyIdx
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	public Map<String, List<Object>> getMultiHashMap(int fnIdx, int keyIdx, JSONObject settingInfo, JSONObject items, JSONArray itemOrder);
	
}