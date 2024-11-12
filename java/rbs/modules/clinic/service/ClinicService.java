package rbs.modules.clinic.service;

import java.util.List;
import java.util.Map;

import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.form.ParamForm;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 클리닉(사용자)모듈에 관한 인터페이스클래스를 정의한다.
 * @author 이동근, 권주미
 *
 */
public interface ClinicService {
	/**
	 * 해당 기업의 가장 최근 기초진단지 식별번호를 가져온다
	 * @param BPL_NO
	 * @return bsc
	 * @throws Exception
	 */
	public long getMaxBsc(String BPL_NO) throws Exception;
	
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
	 * 신청서에서 지부지사를 선택하기 위해 지부지사 관할구역 정보 가져오기
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getInsttList(Map<String, Object> param);
	
	/**
	 * 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getTotalCount(String targetTable, Map<String, Object> param);
	
	/**
	 * 전체 목록
	 * @param targetTable
	 * @param param
	 * @return
	 */
	public List<Object> getList(String targetTable, Map<String, Object> param);

	/**
	 * 상세조회
	 * @param fnIdx
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
	public DataMap getModify(String targetTable, Map<String, Object> param);
	
/*	*//**
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
	 *//*
	public int insert(String uploadModulePath, int fnIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;
	
	*//**
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
	 *//*
	public int update(String uploadModulePath, int fnIdx, int keyIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;
*/
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

	/**
	 * 기업정보
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getCorpInfo(int fnIdx, Map<String, Object> param);
	
	/**
	 * 타겟 테이블에서 조건에 대한 리스트를 불러온다
	 * @param cliIdx
	 * @param param
	 * @return
	 */
	public List<Object> getList(String targetTable, String targetTableIdx, int targetTableIdxValue, String orderColumn) throws Exception;

	/**
	 * 클리닉 신청 여부
	 * @param BPL_NO
	 * @return isCli
	 */
	public int getIsCli(String BPL_NO) throws Exception;
	
	/**
	 * 클리닉 신청서 등록 여부
	 * @param BPL_NO
	 * @return isCli
	 */
	public int getIsReq(String BPL_NO) throws Exception;

	

	/**
	 * 능력개발클리닉 신청서를 최초 신청, 최초 임시저장 시 HRD_CLI테이블에 해당 데이터를 insert한다.
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int insertCli(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray cliInsertColumnOrder) throws Exception;
	
	/**
	 * 능력개발클리닉 신청서를 수정을 통해 신청, 임시저장 시 HRD_CLI테이블에 해당 데이터를 update한다.
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int updateCli(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray cliUpdateColumnOrder) throws Exception;
	
	/**
	 * 최초 임시 저장 시 HRD_CLI_REQ 테이블에 해당 데이터를 insert한다.
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @param status 
	 * @return
	 * @throws Exception
	 */
	public int insertReqAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray reqInsertColumnOrder, String status) throws Exception;
	
	/**
	 * (최초 X)임시 저장시 HRD_CLI_REQ에 해당하는 기업의 신청서를 update한다.
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int updateReqAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, String reqIdx, String status) throws Exception;

	/**
	 * HRD_CLI_*** 신청상태 업데이트(회수, 반려요청, 중도포기)
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
	 * HRD_CLI_***_CONFM 새로운 신청상태 등록(모든 메뉴에서 신청상태를 업데이트할 때 동일하게 사용)
	 * @param targetTable
	 * @param insertColumnKey
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
	 * HRD_CLI에서 최종승인된 CLI_IDX(식별색인) select
	 * @param bplNo
	 * @param param
	 * @return
	 */
	int getCurrentCliIdx(String bplNo) throws Exception;
	
	/**
	 * 사업장관리번호로 현재 cliIdx 가져오기
	 * @param bplNo
	 * @param param
	 * @return
	 */
	int getCliIdx(String bplNo) throws Exception;
	
	/**
	 * 사업장관리번호로 현재 클리닉의 시작날짜 가져오기
	 * @param bplNo
	 * @param param
	 * @return
	 */
	String getCliValidStartDate(String bplNo) throws Exception;


	
	/**
	 * 계획서에서 지원항목 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertPlanSub(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap, int chgIdx) throws Exception;
	
	/**
	 * 계획서에서 HRD담당자 정보 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertPlanCorp(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap, int chgIdx) throws Exception;
	
	/**
	 * 계획서에서 연간훈련계획 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertPlanYearTp(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap, int chgIdx) throws Exception;
	
	/**
	 * 계획서에서 자체 KPI목표 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertPlanKPI(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap, int chgIdx) throws Exception;

	
	
	/**
	 * 현재 cliIdx로 plan등록 여부 가져오기
	 * @param cliIdx
	 * @param param
	 * @return
	 */
	public int getIsPlan(int cliIdx) throws Exception;

	
	/**
	 * 현재 cliIdx로 성과보고서 등록 여부 가져오기
	 * @param cliIdx
	 * @param param
	 * @return
	 */
	public int getIsResult(int cliIdx) throws Exception;
	
	/**
	 * 현재 cliIdx로 지원금 신청서 등록 여부 가져오기
	 * @param cliIdx
	 * @param param
	 * @return
	 */
	public int getIsSupport(int cliIdx) throws Exception;
	
	/**
	 * 특정 메뉴에서 등록을 할 때 이전 단계가 최종승인이 되었는지 확인
	 * @param cliIdx
	 * @param param
	 * @return
	 */
	public int getIsApprove(String targetTable, int cliIdx) throws Exception;

	/**
	 * 최초 임시 저장 / 신청 시 HRD_CLI_PLAN 테이블에 해당 데이터를 insert한다.
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @param planInsertColumnOrder
	 * @param status 
	 * @return
	 * @throws Exception
	 */
	public int insertPlanAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx , String status) throws Exception;

	/**
	 * 능력개발클리닉 훈련계획서변경(HRD_CLI_PLAN_CHANGE) 테이블 insert 처리(최초 신청과 동일하지만 변경요청에 대한 건이 insert된다)
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @param planInsertColumnOrder
	 * @param status 
	 * @return
	 * @throws Exception
	 */
	public int insertPlanChangeAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx , String status) throws Exception;

	
	/**
	 * (최초 X)임시 저장 / 수정 시 HRD_CLI_PLAN에 해당하는 기업의 훈련계획서를 update한다.
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param planUpdateColumnOrder
	 * @param cliIdx
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int updatePlanAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx, String status) throws Exception;

	/**
	 * 활동일지 등록하기
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param planUpdateColumnOrder
	 * @param cliIdx
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int insertAcmsltAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx, String status) throws Exception;
	
	/**
	 * 활동일지 수정하기
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param planUpdateColumnOrder
	 * @param cliIdx
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int updateAcmsltAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx, int acmsltIdx, String status) throws Exception;
	
	/**
	 * 최초 임시 저장 / 신청 시 HRD_CLI_RESULT 테이블에 해당 데이터를 insert한다.
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @param planInsertColumnOrder
	 * @param status 
	 * @return
	 * @throws Exception
	 */
	public int insertResultAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx , String status) throws Exception;
	
	
	
	/**
	 * (최초 X)임시 저장 / 수정 시 HRD_CLI_RESULT에 해당하는 기업의 성과보고서를 update한다.
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param planUpdateColumnOrder
	 * @param cliIdx
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int updateResultAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx, String status) throws Exception;
		
	/**
	 * 최초 임시 저장 / 신청 시 HRD_CLI_RESULT 테이블에 해당 데이터를 insert한다.
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @param planInsertColumnOrder
	 * @param status 
	 * @return
	 * @throws Exception
	 */
	public int insertSupportAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx , String status) throws Exception;
	
	
	
	/**
	 * (최초 X)임시 저장 / 수정 시 HRD_CLI_RESULT에 해당하는 기업의 성과보고서를 update한다.
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param planUpdateColumnOrder
	 * @param cliIdx
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int updateSupportAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx, String status) throws Exception;
	
	/**
	 * 지원항목 + 금액
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<Object> getSportList();
	
	/**
	 * 활동일지 List
	 * @param cliIdx
	 * @return
	 * @throws Exception
	 */
	public List<Object> getActivityCode(int cliIdx, String optionCode, int sportIdx);

	
	/**
	 * 주요 활동일지 목록
	 * @param BPL_NO
	 * @return
	 * @throws Exception
	 */
	public List<Object> getActivityList(Map<String, Object> param);
	
	/**
	 * 훈련계획 수립 진행도(종합관리, 종합이력)
	 * @param cliIdx
	 * @return
	 * @throws Exception
	 */
	public List<Object> getPlanProgress(int cliIdx);
	
	/**
	 * 훈련계획 수립 진행도(비용 청구)
	 * @param cliIdx
	 * @return
	 * @throws Exception
	 */
	public List<Object> getPlanCode(int cliIdx, int sportIdx);
	
	/**
	 * 활동일지 진행도
	 * @param cliIdx
	 * @return
	 * @throws Exception
	 */
	public int getActivityCount(int cliIdx); 
	
	/**
	 * 성과보고 진행도(종합관리, 종합이력)
	 * @param cliIdx
	 * @return
	 * @throws Exception
	 */
	public List<Object> getResultProgress(int cliIdx); 
	
	/**
	 * 성과보고 진행도(비용청구)
	 * @param cliIdx
	 * @return
	 * @throws Exception
	 */
	public List<Object> getResultCode(int cliIdx, int sportIdx); 
	
	/**
	 * 비용청구 진행도
	 * @param cliIdx
	 * @return
	 * @throws Exception
	 */
	public List<Object> getSupportProgress(int cliIdx);

	/**
	 * [비용청구] 지원금 신청내역 지원항목 그룹화 시켜서 목록 조회
	 * @param cliIdx
	 * @param sportIdx
	 * @return
	 * @throws Exception
	 */
	public List<Object> getSptSubGroupBy(int cliIdx, int sportIdx);	
	
	/**
	 * [비용청구] 당해연도 기지원금액 조회
	 * @param cliIdx
	 * @param sportIdx
	 * @return
	 * @throws Exception
	 */
	public List<Object> getSptPayList(int cliIdx, int sportIdx);	
	
	/**
	 * 특정테이블 IDX의 max값 가져오기
	 * @param cliIdx
	 * @param sportIdx
	 * @return
	 * @throws Exception
	 */
	public int getMaxIdx(String targetTable, String targetTableIdx, int cliIdx);

	/**
	 * 해당기업의 컨설팅에 대한 만족도 조사 결과 리스트(실시 예정 과정 수(개))
	 * @param BPL_NO
	 * @return
	 * @throws Exception
	 */
	public List<Object> getTrainingCntList(String BPL_NO);

	/**
	 * 해당기업의 컨설팅에 대한 만족도 조사 결과 리스트(실시대상 리스트(명))
	 * @param BPL_NO
	 * @return
	 * @throws Exception
	 */
	public List<Object> getSurveyTargetList(String BPL_NO);

	/**
	 * 해당기업의 컨설팅에 대한 만족도 조사 결과 리스트(답변리스트)
	 * @param BPL_NO
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAnswerList(String BPL_NO);

	public int getCompareRequestAndVoByBplNo(String targetTable, String targetTableKey, int ratgetTableKeyValue, String BPL_NO);	


}