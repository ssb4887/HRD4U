package rbs.modules.consulting.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ParamForm;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import rbs.modules.consulting.dto.Cnsl;
import rbs.modules.consulting.dto.CnslDiaryDTO;
import rbs.modules.consulting.dto.HrpUserDTO;


/**
 * 샘플모듈에 관한 인터페이스클래스를 정의한다.
 * @author user
 *
 */
public interface ConsultingService {
	
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
	 * multi파일 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getMultiFileView(int fnIdx, Map<String, Object> param);
	
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
	public int updateMultiFileDown(int fnIdx, int keyIdx, int fleIdx, String itemId) throws Exception;
		
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

	
	
	
	/** sw.jang
	 * 컨설팅 생성 : (최초)임시저장 및 제출
	 * @param dto
	 * @param file
	 * @param teamDTO 
	 * @param regiIp
	 */
	public void insertCnsl(Cnsl cnsl, MultipartHttpServletRequest request, String regiIp);

	/** sw.jang
	 * 컨설팅 업데이트 : 이미 저장 된 컨설팅 임시저장
	 * @param cnsl
	 * @param file
	 * @param regiIp
	 */
	public void updateCnsl(Cnsl cnsl, MultipartHttpServletRequest request, String regiIp);
	
	/** sw.jang
	 * 컨설팅 업데이트 : 이미 저장 된 컨설팅및 제출
	 * @param cnsl
	 * @param file
	 * @param regiIp
	 */
	public void updateCnslBySubmit(Cnsl cnsl, MultipartHttpServletRequest request, String regiIp);
	
	
	
	/** sw.jang
	 * 컨설팅 상태값 변경 : 컨설팅 접수, 회수, 승인, 반려
	 * @param cnsl
	 * @param regiIp
	 */
	public void updateCnslStatus(Cnsl cnsl, MultipartHttpServletRequest request, String regiIp);
	
	
	/** sw.jang
	 * 컨설팅 조회 : 컨설팅/파일/팀 관계 데이터 한꺼번에 조회
	 * @param cnslIdx
	 * @return
	 */
	public Cnsl selectByCnslIdx(int cnslIdx);

	

	public HrpUserDTO selectHrpById(String hrd4uId);



	public List<Cnsl> selectConfmCn(String cnslIdx);
	
	/**
	 * 컨설팅 변경신청
	 * @param dto
	 * @param file
	 * @param teamDTO
	 * @param regiIp
	 */
	public void insertChangeCnsl(Cnsl cnsl, MultipartHttpServletRequest request, String regiIp);
	
	public Cnsl selectChangeCnslByCnslIdx(int cnslIdx);


	public int getTotalChangeCnslCount(int fnIdx, Map<String, Object> param);
	

	public void changeConfirm(int cnslIdx, String regiIp);

	public void changeReject(Cnsl cnsl, String regiIp);
	
	
	
	
	/**
	 * 수행일지 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getDiaryCount(int fnIdx, Map<String, Object> param);

	/**
	 * 수행일지 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getDiaryList(int fnIdx, Map<String, Object> param);





	public List<?> getChangeList(int fnIdx, Map<String, Object> param);
	
	/**
	 * 수행일지 중복 체크
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int chkDuplicate(int fnIdx, ParamForm parameterMap);
	
	/**
	 * 인력풀 위촉 체크
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int chkEntrstRole(int fnIdx, ParamForm parameterMap);

	public HashMap<String, String> select4uById(String hrd4uId);

	public List<Cnsl> getCnslListAll(int fnIdx, Map<String, Object> param);

	public int getCnslListAllCount(int fnIdx, Map<String, Object> param);

	public int selectCnslCountByBplNoAndCnslType(String bplNo, String cnslType);
	
	public int selectCnslSubmitCountByBplNoAndCnslType(String bplNo, String cnslType);

	public String select4URepveNmByBizrNo(String bizrNo);

	public void updateCnslStatus(Cnsl cnsl, String regiIp);

	public HashMap<String, String> selectCenterInfo(String centerIdx);

	public HashMap<String, String> selectInsttIdxByZip(String zip);
	
	public int insertExpert(Map<String, Object> paramMap);

	public int insertBusin(Map<String, Object> paramMap);
	
	public int insertEntrst(Map<String, Object> paramMap);
	
	public DataMap getInstt(Map<String, Object> paramMap);

	public List<HashMap<String, Object>> getDiaryView(Map<String, Object> paramMap);

	public List<HashMap<String, Object>> selectDoctorInfoByInsttIdx(String insttIdx);

	public int changeCmptncBrffcPic(HashMap<String, String> map);

	public int selectSojtIsSelected(String bplNo);

	public int selectCnslCountByBplNoAndCnslType2(String bplNo);
	
	/**
	 * 인력풀 리스트
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getEntList(Map<String, Object> param);
	
	/**
	 * 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getEntCnt(Map<String, Object> param);

	public int insertEvl(Map<String, Object> paramMap);
	
	/**
	 * 컨설턴트 평가 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getEvlCount(int fnIdx, Map<String, Object> param);

	/**
	 * 컨설턴트 평가 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getEvlList(int fnIdx, Map<String, Object> param);
	
	public int updateEntrst(Map<String, Object> paramMap);

	public HashMap<String, String> selectCountOfConsulting(String hrd4uId);

	public int selectSojtConfm(String bplNo);

	public List<HashMap<String, String>> selectNcsInfo(String json);

	public int selectCountIndvdlInfo(String hrd4uId);

	public List<HashMap<String, String>> getInsttList();

	public void insertCnslByCenter(Cnsl cnsl,
			MultipartHttpServletRequest request, String regiIp);

	public int update4uEntrst(Map<String, Object> paramMap);

	public int isTrainingComplecated(String hrd4uId);

	public List<CnslDiaryDTO> selectDiaryListByCnslIdx(String cnslIdx);

	public List<Cnsl> selectCnslListByBplNoForViewAll(String bplNo);

	public List<Cnsl> selectCnslListByMemberIdxForViewAll(String memberIdx);

	public List<Cnsl> selectCnslListBySpntMemberIdxForViewAll(String memberIdx);

	public List<Cnsl> selectCnslListByDoctorMemberIdxForViewAll(
			String memberIdx);

	public List<Cnsl> selectCnslListAllForViewAll();

	public List<HashMap<String, String>> selectNcsInfoDepth1();

	public List<HashMap<String, String>> selectNextNcsDepth(String json);
	
	/**
	 * 기업정보 받아오기
	 * @param map
	 * @return
	 */
	public Map<String, Object> findBizData(Map<String, Object> map);
	
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
	public int applyInsert(String uploadModulePath, int fnIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;
	
	public int applyUpdate(Map<String, Object> param);
	
	/**
	 * 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getCnslApplyCnt(int fnIdx, Map<String, Object> param);
	
	/**
	 * 전체 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getCnslApplyList(int fnIdx, Map<String, Object> param);

	public HashMap<String, String> selectSojtFormInfo(String bplNo);

	public int selectCnslSaveCountByBplNoAndCnslType(String bplNo,
			String cnslType);

	public List<Cnsl> selectChangeConfmCn(String cnslIdx);

	public HashMap<String, String> selectDoctorById(String hrd4uId);




}