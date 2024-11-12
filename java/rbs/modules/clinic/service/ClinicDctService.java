package rbs.modules.clinic.service;

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
public interface ClinicDctService {
	

	/**
	 * 타겟 테이블에서 조건에 대한 리스트를 불러온다
	 * @param cliIdx
	 * @param param
	 * @return
	 */
	public List<Object> getList(String targetTable, String targetTableIdx, int targetTableIdxValue, String orderColumn) throws Exception;
	
	/**
	 * 기업 자가 확인 체크리스트 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getCheckList(int fnIdx, Map<String, Object> param);
	
	/**
	 * 기업 자가 확인 체크리스트 답변 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getCheckListAnswer(int fnIdx, Map<String, Object> param);
	/**
	 * 기업 선정 심사표 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getJdgmntabList(int fnIdx, Map<String, Object> param);
	
	/**
	 * 기업 선정 심사표 답변 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getJdgmntabListAnswer(int fnIdx, Map<String, Object> param);

	/**
	 * 특정 테이블 IDX의 max 값 가져오기
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getMaxIdx(String targetTable, String tagetTableIdx, Map<String, Object> param);
	
	/**
	 * 지부지사 부장, 본부 직원이 로그인했을 때 전체 목록 수(신청관리에서 사용)
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getTotalReqCount(String targetTable, Map<String, Object> param);
	
	/**
	 * 지부지사 직원이 로그인했을 때 신청서 전체 목록 수(신청관리에서 사용)
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getTotalReqCountForDoctor(String targetTable, Map<String, Object> param);
	
	/**
	 * 지부지사 부장, 본부 직원이 로그인했을 때 전체 목록 수(계획관리, 성과보고, 비용관리에서 사용)
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getTotalCount(String targetTable, Map<String, Object> param);
	
	/**
	 * 지부지사 직원이 로그인했을 때 전체 목록 수(계획관리, 성과보고, 비용관리에서 사용)
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getTotalCountForDoctor(String targetTable, Map<String, Object> param);
	
	/**
	 * 지부지사 부장, 본부 직원이 로그인했을 때 전체 목록(신청관리에서 사용)
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getReqList(String targetTable, Map<String, Object> param);
	
	/**
	 * 지부지사 직원이 로그인했을 때 전체 목록(신청관리에서 사용)
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getReqListForDoctor(String targetTable, Map<String, Object> param);
	
	/**
	 * 지부지사 부장, 본부 직원이 로그인했을 때 전체 목록(계획관리, 성과보고, 비용관리에서 사용)
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getList(String targetTable, Map<String, Object> param);
	
	/**
	 * 지부지사 직원이 로그인했을 때 전체 목록(계획관리, 성과보고, 비용관리에서 사용)
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getListForDoctor(String targetTable, Map<String, Object> param);
	
	/**
	 * HRD_CLI_PLAN_***_CHANGE 테이블에서 가져오기(계획관리에서 변경요청이 들어왔을 때 사용)
	 * @param targetTable
	 * @param param
	 * @return
	 */
	public List<Object> getChangeList(String targetTable,  String orderColumn, Map<String, Object> param);

	/**
	 * 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getView(String targetTable, Map<String, Object> param);
	
	/**
	 * 신청서 상세조회(전담주치의 정보 포함)
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getReqView(String targetTable, Map<String, Object> param);
	
	/**
	 * 파일 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getFileView(int fnIdx, Map<String, Object> param);
	
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
	public int updateFileDown(int fnIdx, int keyIdx, String fileColumnName) throws Exception;
	
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
	public DataMap getModify(String targetTable, Map<String, Object> param);
	
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
	public Map<String, List<Object>> getMultiFileHashMap(String targetTable, int cliIdx, int fnIdx, int keyIdx, JSONObject settingInfo, JSONObject items, JSONArray itemOrder);
	
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

	List<Object> getDoctorList(Map<String, Object> param);

	int getTotalDoctorCount(Map<String, Object> param);
	
	/**
	 * 사업장관리번호로 현재 cliIdx 가져오기
	 * @param bplNo
	 * @return
	 * @throws Exception
	 */
	public int getCliIdx(String bplNo) throws Exception;
	
	/**
	 * HRD_CLI DOCTOR_IDX 수정(접수할 때 전담주치의 지정)
	 * @param cliIdx
	 * @param doctorIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	public int updateCliDoctor(int cliIdx, int doctorIdx, String regiIp) throws Exception;
	
	/**
	 * HRD_CLI VALID_START_DATE, VALID_END_DATE 수정(최종승인할 때 시작날짜와 최종날짜 수정)
	 * @param cliIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	public int updateCliStartEndDate(int cliIdx, String regiIp) throws Exception;
	
	/**
	 * HRD_CLI_*** 수정(item_info에서 modifyproc_order에 있는 컬럼만 update를 할 때 사용)
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
	
	/**
	 * HRD_CLI_REQ 수정(기업 선정 심사표와 주치의 의견만 update)
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
	public int updateReqSlctnResultAndDoctorOpinion(String targetTable, String targetColumn, String uploadModulePath, int keyIdx, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO) throws Exception;
	
	/**
	 * HRD_CLI_*** 승인상태 및 주치의 의견 업데이트(반려처리할 때 사용)
	 * @param targetTable
	 * @param targetColumn
	 * @param confmStatus
	 * @param reqIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int updateStatusAndOpinion(String targetTable, String targetColumn, String confmStatus, int keyIdx, String regiIp, ParamForm parameterMap) throws Exception;
	
	/**
	 * HRD_CLI_*** 승인상태 및 주치의 의견 + 기업선정 심사표 결과 업데이트(반려처리할 때 사용)
	 * @param targetTable
	 * @param targetColumn
	 * @param confmStatus
	 * @param reqIdx
	 * @param regiIp
	 * @param parameterMap
	 * @param slctnResult
	 * @return
	 * @throws Exception
	 */
	public int updateStatusAndOpinion(String targetTable, String targetColumn, String confmStatus, int keyIdx, String regiIp, ParamForm parameterMap, String slctnResult) throws Exception;
	
	/**
	 * HRD_CLI_*** 승인상태 업데이트(접수, 1차승인, 최종승인할 때 사용)
	 * @param targetTable
	 * @param targetColumn
	 * @param confmStatus
	 * @param cliIdx
	 * @param reqIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	public int updateStatus(String targetTable, String targetColumn, String confmStatus, int cliIdx, int keyIdx, String regiIp) throws Exception;
	
	/**
	 * HRD_CLI_REQ 에서 기업선정 심사표 입력 후 승인할 때 사용
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
	public int updateStatus(String targetTable, String targetColumn, String confmStatus, int cliIdx, int keyIdx, String regiIp, String slctnResult) throws Exception;
	
	/**
	 * HRD_CLI_PLAN_CHANGE에 신청 상태 update 하기
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
	public int updateStatus(String targetTable, String targetColumn, String confmStatus, int cliIdx, int keyIdx, String regiIp, int chgIdx) throws Exception;
	
	/**
	 * HRD_CLI_*** 승인상태, 삭제가부 업데이트(중도포기할 때 사용)
	 * @param targetTable
	 * @param targetColumn
	 * @param confmStatus
	 * @param cliIdx
	 * @param reqIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	public int updateStatusDropRequest(String targetTable, String targetColumn, String confmStatus, int cliIdx, int keyIdx, String regiIp) throws Exception;
	
	/**
	 * HRD_CLI_***_CONFM 새로운 승인상태 등록(모든 메뉴에서 승인상태를 업데이트할 때 동일하게 사용)
	 * @param targetTable
	 * @param targetColumn
	 * @param confmStatus
	 * @param cliIdx
	 * @param keyIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	public int insertConfm(String targetTable, String insertColumnKey, String targetColumn, String confmStatus, int cliIdx, int keyIdx, String regiIp) throws Exception;
	
	/**
	 * HRD_CLI_DOCTOR_HST 테이블에 변경된 전담주치의 정보 등록(전담주치의 정보를 업데이트할 때마다 insert)
	 * @param targetTable
	 * @param targetColumn
	 * @param cliIdx
	 * @param memberIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	public int insertDoctorHst(String targetTable, String targetColumn, int cliIdx, int memberIdx, String regiIp) throws Exception;
	
	/**
	 * 신청서에서 기업 자가 체크리스트 답변 업데이트
	 * @param uploadModulePath
	 * @param fnIdx
	 * @param cliList
	 * @param doctorList
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertReqChk(String targetTable, int reqIdx, String regiIp, ParamForm parameterMap) throws Exception;
	
	/**
	 * 신청서에서 기업 선정 심사표 답변 등록
	 * @param uploadModulePath
	 * @param fnIdx
	 * @param cliList
	 * @param doctorList
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertReqJdg(String targetTable, int reqIdx, String regiIp, ParamForm parameterMap) throws Exception;
	
	/**
	 * 능력개발클리닉 신청서 기업정보
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getCorpInfo(int fnIdx, Map<String, Object> param);
	
	/**
	 * 중도포기 시 HRD_CLI 테이블에 ISDELETE = '1'로 업데이트 하기(삭제처리)
	 * @param targetTable
	 * @param cliIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	public int deleteCli(String targetTable, int cliIdx, String regiIp) throws Exception;
	
	/**
	 * 계획서에서 지원항목 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertPlanSub(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap) throws Exception;
	
	/**
	 * 계획서에서 HRD담당자 정보 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertPlanCorp(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap) throws Exception;
	
	/**
	 * 계획서에서 연간훈련계획 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertPlanYearTp(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap) throws Exception;
	
	/**
	 * 계획서에서 자체 KPI목표 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertPlanKPI(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap) throws Exception;
	
	/**
	 * 계획서 최종승인 후 HRD_CLI_PLAN_CHANGE에 insert 하기
	 * @param cliIdx
	 * @param planIdx
	 * @return
	 * @throws Exception
	 */
	public int planToChange(int cliIdx, int planIdx) throws Exception;
	
	/**
	 * 변경승인 후 HRD_CLI_PLAN에 update하기
	 * @param cliIdx
	 * @param planIdx
	 * @return
	 * @throws Exception
	 */
	public int changeToPlan(int cliIdx, int planIdx, int chgIdx) throws Exception;
	
	/**
	 * [성과관리] 성과보고서에서 연간훈련실시결과 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertResultYearTp(String targetTable, int cliIdx, int resltIdx, String regiIp, ParamForm parameterMap) throws Exception;
	
	/**
	 * [성과관리] 성과보고서에서 자체 KPI목표 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertResultKPI(String targetTable, int cliIdx, int resltIdx, String regiIp, ParamForm parameterMap) throws Exception;
	

	/**
	 * [대시보드] 연차별 클리닉 기업 전체 리스트(본부 - 전체 지부지사 / 지부지사 부장 - 본인 소속기관 전체 / 지부지사 주치의 - 본인이 전담주치의인 기업)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAllCliCorpCountList(Map<String, Object> param);
	
	/**
	 * [대시보드] 연차별 나의 클리닉 기업 전체 리스트(지부지사 부장, 지부지사 주치의만 사용)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Object> getMyCliCorpCountList(Map<String, Object> param);
	
	/**
	 * [대시보드] 연차별 중도포기한 클리닉 기업 전체 리스트(본부 - 전체 지부지사 / 지부지사 부장 - 본인 소속기관 전체 / 지부지사 주치의 - 본인이 전담주치의인 기업)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAllDropCliCorpCountList(Map<String, Object> param);
	
	/**
	 * [대시보드] 연차별 중도포기한 나의 클리닉 기업 전체 리스트(지부지사 부장, 지부지사 주치의만 사용)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Object> getMyDropCliCorpCountList(Map<String, Object> param);

	/**
	 * [대시보드]  클리닉 기업 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<?> getCliCorpList(Map<String, Object> param);

	public int getCliCorpCount(Map<String, Object> param);
	

	/**
	 * [비용관리] 지원금 신청내역 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertSptSub(String targetTable, int cliIdx, int sportIdx, String regiIp, ParamForm parameterMap) throws Exception;
	
}