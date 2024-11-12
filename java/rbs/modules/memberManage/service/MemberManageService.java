package rbs.modules.memberManage.service;

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
public interface MemberManageService {

	/**
	 * 전체 목록 수
	 * @param param
	 * @return
	 */
	public int getTotalCount(Map<String, Object> param);
	
	/**
	 * 전체 목록 수
	 * @param param
	 * @return
	 */
	public int getTotalCenterCount(Map<String, Object> param);
	
	/**
	 * 전체 목록 수
	 * @param param
	 * @return
	 */
	public int getTotalCenterAuthCount(Map<String, Object> param);
	
	/**
	 * 기업회원 소속변경 신청 전체 목록 수
	 * @param param
	 * @return
	 */
	public int getTotalCorpRegiCount(Map<String, Object> param);
	
	/**
	 * 공단소속 변경신청 전체 목록 수
	 * @param param
	 * @return
	 */
	public int getTotalEmployRegiCount(Map<String, Object> param);
	
	/**
	 * 전체 목록
	 * @param param
	 * @return
	 */
	public List<Object> getCodeList(Map<String, Object> param);
	
	/**
	 * 기업회원 소속기관 변경신청 이전 기록 확인 
	 * @param param
	 * @return
	 */
	public int checkCorpReq(Map<String, Object> param);
	
	/**
	 * 민간센터 권한신청 이전 신청 기록 확인 
	 * @param param
	 * @return
	 */
	public int checkCenterReq(Map<String, Object> param);
	
	/**
	 * 민간센터 권한신청 적용중인 권한 확인 
	 * @param param
	 * @return
	 */
	public int checkCenterApply(Map<String, Object> param);
	
	/**
	 * 소속기관, 본부직원 공단소속 주치의등록이 되어있는지 확인 
	 * @param param
	 * @return
	 */
	public int checkEmpRegi(Map<String, Object> param);
	
	/**
	 * 소속기관, 본부직원 공단소속 변경신청 이전 기록 확인 
	 * @param param
	 * @return
	 */
	public int checkEmpReq(Map<String, Object> param);
	
	/**
	 * 전체 목록 수
	 * @param param
	 * @return
	 */
	public int getTotalEmpReqCount(Map<String, Object> param);
	
	/**
	 * 전체 목록
	 * @param param
	 * @return
	 */
	public List<Object> getList(Map<String, Object> param);
	
	/**
	 * 전체 권한부여 목록
	 * @param param
	 * @return
	 */
	public List<Object> getCenterAuthList(Map<String, Object> param);
	
	/**
	 * 기업회원 소속 변경신청 목록
	 * @param param
	 * @return
	 */
	public List<Object> getCorpRegiList(Map<String, Object> param);
	
	/**
	 * 공단소속 변경신청 목록
	 * @param param
	 * @return
	 */
	public List<Object> getEmployRegiList(Map<String, Object> param);

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
	 * 검색을 위한 지부지사 목록 가져오기
	 * @param param
	 * @return
	 */
	public List<Object> getOrgInsttList();
	
	/**
	 * 검색을 위한 지부지사 목록 가져오기
	 * @param param
	 * @return
	 */
	public List<Object> getInsttList();
	
	/**
	 * 기업회원 소속기관 일괄변경
	 * @author 권주미
	 * @param authIdx 권한일련번호, status 상태, startDate 시작일, endDate 종료일, memberList 변경할 회원일련번호, authReqList 권한신청일련번호
	 * @param regiIp 
	 * @return
	 */
	public int updateCorp (String status, String psitnList, String bplList, String memberList,  String regiIp);
	
	
	/**
	 * 민간센터 권한 일괄변경
	 * @author 권주미
	 * @param authIdx 권한일련번호, status 상태, startDate 시작일, endDate 종료일, memberList 변경할 회원일련번호, authReqList 권한신청일련번호
	 * @param regiIp 
	 * @return
	 */
	public int updateCenter(String authIdx, String status, String startDate, String endDate, String memberList, String authReqList, String regiIp);
	
	/**
	 * 소속기관, 본부직원 공단소속 일괄변경
	 * @author 권주미
	 * @param astatus 상태, memberList 변경할 회원일련번호
	 * @param regiIp 
	 * @return
	 */
	public int updateEmploy(String status, String memberList, String doctorList, String regiIp);
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	public List<Object> getCodeList();
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	public List<Object> getGradeList();
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	public DataMap getView(Map<String, Object> param);
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	public DataMap getViewCorp(Map<String, Object> param);
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	public DataMap getPreOrg(Map<String, Object> param);
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	public DataMap getViewOrg(Map<String, Object> param);

	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	public List<Object> getViewRegi(Map<String, Object> param);
	
	/**
	 * 주치의 상세조회
	 * @param param
	 * @return
	 */
	public DataMap getViewDoctor(Map<String, Object> param);
	
	/**
	 * 주치의 상세조회
	 * @param param
	 * @return
	 */
	public List<Object> getDoctorInfo(Map<String, Object> param);
	
	/**
	 * 전담주치의 상세조회
	 * @param param
	 * @return
	 */
	public DataMap getViewCliDoctor(Map<String, Object> param);
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	public List<Object> getAuth(Map<String, Object> param);
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	public List<Object> getWebReq(Map<String, Object> param);
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	public List<Object> getDctReq(Map<String, Object> param);
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	public List<Object> getEmpDoctor(Map<String, Object> param);
	
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
	 * 기관 등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int insertOrg(String uploadModulePath,  String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;
	
	/**
	 * 민간센터 등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int insertCenter(String uploadModulePath,  String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;
	
	/**
	 * 컨설턴트 등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int insertConsult(String uploadModulePath,  String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;
	
	/**
	 * 본부직원 등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int insertEmploy(String uploadModulePath,  String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;
	
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
	 * 적용중인 주치의 확인 : 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
	 * @param parameterMap
	 * @param deleteIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	public int applyNotDelete(ParamForm parameterMap, String[] deleteIdxs, String regiIp, JSONObject settingInfo) throws Exception;
	
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
	 * 적용처리 : 화면에 조회된 정보를 데이터베이스에서 적용불가처리 flag 반영
	 * @param parameterMap
	 * @param deleteIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	public int applyCenter(ParamForm parameterMap, String regiIp, JSONObject settingInfo) throws Exception;
	
	/**
	 * 적용처리 : 화면에 조회된 정보를 데이터베이스에서 적용불가처리 flag 반영
	 * @param parameterMap
	 * @param deleteIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	public int applyEmp(ParamForm parameterMap, String regiIp, JSONObject settingInfo) throws Exception;
	
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
	
	public void updatePSITN(Map param);
	public void insertPSITNbyDCT(Map param);
	public void updateDoctor(Map param);
	
	/**
	 * 완전삭제처리 : 민간센터 권한 화면에 조회된 정보를 데이터베이스에서 삭제
	 * @param uploadModulePath
	 * @param deleteIdxs
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int centerDelete(Map<String, Object> param) throws Exception;
	
	/**
	 * 완전삭제처리 : 본부직원 화면에 조회된 정보를 데이터베이스에서 삭제
	 * @param uploadModulePath
	 * @param deleteIdxs
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int employDelete(Map<String, Object> param) throws Exception;
}