package rbs.modules.develop.service;

import java.util.List;
import java.util.Map;

import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ParamForm;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 훈련과정 맞춤개발 모듈에 관한 인터페이스클래스를 정의한다.
 * @author LDG, KJM
 *
 */
public interface DevelopService {
	
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
	public List<Object> getList(String targetTable, Map<String, Object> param);
	
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
	 * 상세조회
	 * @param targetTable
	 * @param param
	 * @return
	 */	
	public DataMap  getView(String targetTable, Map<String, Object> param);
	
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
	 * @param targetTable
	 * @param targetTableIdxColumnName
	 * @param targetTableIdxValue
	 * @return
	 */
	public Map<String, List<Object>> getMultiFileHashMap(String targetTable, String targetTableIdxColumnName, int targetTableIdxValue);

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
	 * [훈련과정맞춤개발] 표준개발 주치의 지원요청 insert
	 * @param uploadModulePath
	 * @param remoteAddr
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param helpInsertColumnOrder
	 * @return 
	 * @throws Exception
	 */
	public int insertDevelopForDoctorHelp(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo,
			JSONObject items, JSONArray helpInsertColumnOrder, String confmStatus) throws Exception;
	
	/**
	 * 해당 기업의 가장 최근 기초진단지 식별번호를 가져온다
	 * @param BPL_NO
	 * @return bsisCnsl
	 * @throws Exception
	 */
	public long getMaxBsisCnsl(String BPL_NO) throws Exception;
	
	/**
	 * 해당 기업의 가장 최근 기초진단지의 추천된 훈련을 가져온다
	 * @param bsisCnsl_idx
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> getRecommend(long bsiscnsl_idx) throws Exception;
	
	/**
	 * 추천된 훈련(혹은 목록에서 선택한)의 과정 정보를 가져온다
	 * @param param
	 * @return DataMap
	 * @throws Exception
	 */
	public DataMap getTpInfo(Map<String, Object> param) throws Exception;
	
	/**
	 * 추천된 훈련(혹은 목록에서 선택한)의 과정 상세 정보를 가져온다
	 * @param param
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> getTpSubInfo(Map<String, Object> param) throws Exception;

	/**
	 * [훈련과정맞춤개발] 표준개발 신청서 신청시 insert
	 * @param uploadModulePath
	 * @param remoteAddr
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param developInsertColumnOrder
	 * @return 
	 * @throws Exception
	 */
	public int insertDevelopAsStatusCode(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo,
			JSONObject items, JSONArray developInsertColumnOrder, String confmStatus, String siteId) throws Exception;

	/**
	 * [훈련과정맞춤개발] 표준개발 신청서 신청시 insert(상세정보)
	 * @param uploadModulePath
	 * @param remoteAddr
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param developTpInsertColumnOrder
	 * @param confmStatus
	 * @return 
	 * @throws Exception
	 */
	public int insertDevelopTpAndTpSub(String uploadModulePath, String regiIp, ParamForm parameterMap,
			JSONObject settingInfo, JSONObject items, JSONArray developTpInsertColumnOrder) throws Exception;

	/**
	 * [훈련과정맞춤개발] S-OJT 한도 체크
	 * @param BPL_NO
	 * @return String
	 * @throws Exception
	 */
	public String getSojtAvailableFlag(String BPL_NO) throws Exception;

	
	/**
	 * [훈련과정맞춤개발] 종합이력 목록
	 * @param 
	 * @return List
	 * @throws Exception
	 */
	public List<Object> getTotalList(Map<String, Object> param) throws Exception;

	public int getTotalListCount(Map<String, Object> param);

	/**
	 * (최초 X)임시 저장시 HRD_DEVLOP_TP 와 TP_SUB 에 해당하는 기업의 신청서를 update한다.
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int updateDevelopTpAndTpSub(String uploadModulePath, String regiIp, ParamForm parameterMap,
			JSONObject settingInfo, JSONObject items, JSONArray developTpInsertColumnOrder) throws Exception;

	/**
	 * (최초 X)임시 저장시 HRD_DEVLOP 에 해당하는 기업의 신청서를 update한다.
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	public int updateDevelopAsStatusCode(String uploadModulePath, String regiIp, ParamForm parameterMap,
			JSONObject settingInfo, JSONObject items, JSONArray developInsertColumnOrder, String confmStatus, String siteId) throws Exception;
	
	/**
	 * HRD_DEVELOP_*** 승인상태 및 기업 의견 업데이트(검토요청 반려처리할 때 사용)
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
	
	public int getTrainingListCount(Map<String, Object> param);

	/**
	 * 훈련과정 목록
	 * @param param
	 * @return List
	 * @throws Exception
	 */
	public List<?> getTrainingList(Map<String, Object> param);
	
	/**
	 * 비용청구 리스트
	 * @param param
	 * @return List
	 * @throws Exception
	 */
	public List<?> getSupportList(Map<String, Object> param);
	
	public int getSupportListCount(Map<String, Object> param);

	public List<Object> getAiRecommendList(String apiName, String BPL_NO, int rsltIdx) throws Exception;

	/**
	 * 비용청구 컨설팅 리포트
	 * @param param
	 * @return List
	 * @throws Exception
	 */
	public DataMap getCnslReportInfo(Map<String, Object> param) throws Exception;

	/**
	 * 비용청구 컨설팅 팀 리스트
	 * @param param
	 * @return List
	 * @throws Exception
	 */
	public List<Object> getCnslTeamInfo(Map<String, Object> param) throws Exception;

	/**
	 * 비용신청때 선택한 훈련과정을 제외한 모든 해당 기업의 훈련과정 리스트
	 * @param param
	 * @return List
	 * @throws Exception
	 */
	public List<Object> getCorpTpList(Map<String, Object> param) throws Exception;

	/**
	 * 비용청구 신청서 신청 / 임시저장(최초신청)
	 * @param uploadModulePath
	 * @param remoteAddr
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param developTpInsertColumnOrder
	 * @param confmStatus
	 * @return 
	 * @throws Exception
	 */
	public int insertCnslCostAsStatusCode(String uploadModulePath, String regiIp, ParamForm parameterMap,
			JSONObject settingInfo, JSONObject items, JSONArray insertColumnOrder, String confmStatus) throws Exception;

	
	/**
	 * 비용청구 신청서 신청 / 임시저장(최초신청 X)
	 * @param uploadModulePath
	 * @param remoteAddr
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param developTpInsertColumnOrder
	 * @param confmStatus
	 * @return 
	 * @throws Exception
	 */
	public int updateCnslCostAsStatusCode(String uploadModulePath, String regiIp, ParamForm parameterMap,
			JSONObject settingInfo, JSONObject items, JSONArray insertColumnOrder, String confmStatus) throws Exception;

	public int insertConfm(String string, String string2, String confmStatus, int cnslIdx, int ctIdx, String regiIp) throws Exception;

	public Object getMultiFileHashMap(String targetTable, String targetTableIdxColumnName, String tpIdx);

	public int getCompareRequestAndVoByBplNo(int devlopIdx, String BPL_NO);

	public int getIsSojt(String BPL_NO);

	public int getIsBsc(String BPL_NO);

	public int getRsltIdxByBsiscnslIdx(long bsiscnslIdx);

	public List<Object> getBsisWithAiRecommendList(String jsonString, long bsiscnslIdx);

	public String getEduRecoSystemValue(String BPL_NO, int rsltIdx);

	public String getAiEduRecoSystemRawValue(String BPL_NO, int rsltIdx) throws Exception;


}