package rbs.modules.report.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.woowonsoft.egovframework.form.DTForm;
import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ParamForm;
import com.woowonsoft.egovframework.prop.RbsProperties;
import com.woowonsoft.egovframework.util.FileUtil;
import com.woowonsoft.egovframework.util.JSONObjectUtil;
import com.woowonsoft.egovframework.util.ModuleUtil;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import rbs.egovframework.LoginVO;
import rbs.egovframework.common.ChgLogMapper;
import rbs.modules.consulting.dto.CnslFileDTO;
import rbs.modules.report.dto.Report;
import rbs.modules.report.dto.ReportFileDTO;
import rbs.modules.report.mapper.ReportFileMapper;
import rbs.modules.report.mapper.ReportMapper;
import rbs.modules.report.mapper.ReportMultiMapper;
import rbs.modules.report.service.ReportService;

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * 
 * @author user
 *
 */
@Service("reportService")
public class ReportServiceImpl extends EgovAbstractServiceImpl
		implements
			ReportService {

	@Value("${Globals.fileStorePath}")
	private String uploadDir;

	@Resource(name = "reportMapper")
	private ReportMapper reportDAO;

	@Resource(name = "chgLogMapper")
	private ChgLogMapper chgLogMapper;

	@Resource(name = "reportMultiMapper")
	private ReportMultiMapper reportMultiDAO;
	
	@Resource(name = "reportFileMapper")
	private ReportFileMapper reportFileDAO;

	private static final org.slf4j.Logger log = LoggerFactory
			.getLogger(ReportServiceImpl.class);

	List<DTForm> dataList;

	Map<String, Object> param;

	List<DTForm> searchList;

	// 공통코드 시작
	/**
	 * 등록자 정보 저장
	 * 
	 * @param dataList
	 * @param loginVO
	 * @param regiIp
	 * @return dataList
	 */
	private List<DTForm> addRegiInfo(List<DTForm> dataList, LoginVO loginVO,
			String regiIp) {
		// 2.2 등록자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		
		dataList.add(new DTForm("REGI_IDX", loginMemberIdx));
		dataList.add(new DTForm("REGI_ID", loginMemberId));
		dataList.add(new DTForm("REGI_NAME", loginMemberName));
		dataList.add(new DTForm("REGI_IP", regiIp));

		return dataList;
	}

	/**
	 * 마지막수정자 정보 저장
	 * 
	 * @param dataList
	 * @param loginVO
	 * @param regiIp
	 * @return dataList
	 */
	private List<DTForm> addLastModiInfo(List<DTForm> dataList, LoginVO loginVO,
			String regiIp) {
		// 2.2 등록자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}

		dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
		dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
		dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
		dataList.add(new DTForm("LAST_MODI_IP", regiIp));
		return dataList;
	}

	/**
	 * 전체 목록 수
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public int getTotalCount(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return reportDAO.getTotalCount(param);
	}

	/**
	 * 전체 목록
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getList(String targetTable, Map<String, Object> param) {
		param.put("targetTable", targetTable);
		return reportDAO.getList(param);
	}
	/**
	 * 상세조회
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getView(String targetTable, Map<String, Object> param) {
		param.put("targetTable", targetTable);
		DataMap viewDAO = reportDAO.getView(param);
		return viewDAO;
	}

	/**
	 * 파일 상세조회
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getFileView(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		DataMap viewDAO = reportDAO.getFileView(param);
		return viewDAO;
	}
	
	/**
	 * multi파일 상세조회
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getMultiFileView(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		DataMap viewDAO = reportFileDAO.getFileView(param);
		return viewDAO;
	}

	/**
	 * 다운로드 수 수정
	 * 
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateFileDown(int fnIdx, int keyIdx, String fileColumnName)
			throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		param.put("fnIdx", fnIdx);
		param.put("KEY_IDX", keyIdx);
		param.put("searchList", searchList);
		param.put("FILE_COLUMN", fileColumnName);
		return reportDAO.updateFileDown(param);

	}
	
	/**
	 * multi file 다운로드 수 수정
	 * 
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateMultiFileDown(int fnIdx, int keyIdx, int fleIdx, String itemId) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		searchList.add(new DTForm("FLE_IDX", fleIdx));
		searchList.add(new DTForm("ITEM_ID", itemId));
		param.put("searchList", searchList);
		param.put("fnIdx", fnIdx);
		param.put("KEY_IDX", keyIdx);
		return reportFileDAO.updateFileDown(param);
	}

	/**
	 * 수정 상세조회
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getModify(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		DataMap viewDAO = reportDAO.getModify(param);
		return viewDAO;
	}

	/**
	 * 권한여부
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getAuthCount(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return reportDAO.getAuthCount(param);
	}

	/**
	 * 등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * 
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
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public int insert(String uploadModulePath, int fnIdx, String regiIp,
			ParamForm parameterMap, JSONObject settingInfo, JSONObject items,
			JSONArray itemOrder, String confmStatus) throws Exception {
		if (JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder))
			return -1;
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
																				// 사용자
																				// 정보
		Map<String, Object> param = new HashMap<String, Object>(); // mapper
																	// parameter
																	// 데이터
		List<DTForm> dataList = new ArrayList<DTForm>(); // 저장항목
		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column"); // key
																					// 컬럼

		// 테이블
		String targetTable = "HRD_CNSL_REPORT";
		// 테이블키
		String targetTableIdx = "REPRT_IDX";

		param.put("targetTable", targetTable);
		param.put("targetTableIdx", targetTableIdx);
		// 1. key 얻기
		param.put("fnIdx", fnIdx);
		int keyIdx = reportDAO.getNextId(param);
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if (dataMap == null || dataMap.size() == 0)
			return -1;

		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if (itemDataList != null)
			dataList.addAll(itemDataList);

		dataList.add(new DTForm(columnName, keyIdx));

		// 2.2 등록자 정보
		dataList = addRegiInfo(dataList, loginVO, regiIp);
		dataList.add(new DTForm("CNSL_IDX", parameterMap.getString("cnslIdx")));
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		param.put("dataList", dataList);
		param.put("confmStatus", confmStatus);
		param.put("bplNo", parameterMap.getString("bplNo"));
		param.put("jsonValue", parameterMap.get("jsonValue"));
		
				
		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = reportDAO.insert(param);
		if (result > 0) {
			// 3.2 multi data 저장
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			if (multiDataList != null) {
				int multiDataSize = multiDataList.size();
				for (int i = 0; i < multiDataSize; i++) {
					Map<String, Object> fileParam = (HashMap) multiDataList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", keyIdx);
					fileParam.put("CNSL_IDX", dataMap.get("cnslIdx"));
					result = reportMultiDAO.insert(fileParam);
				}
			}

			// 3.3 multi file 저장
			List fileDataList = StringUtil.getList(dataMap.get("fileDataList"));
			if (fileDataList != null) {
				int fileDataSize = fileDataList.size();
				for (int i = 0; i < fileDataSize; i++) {
					Map<String, Object> fileParam = (HashMap) fileDataList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", keyIdx);
					fileParam.put("CNSL_IDX", parameterMap.get("cnslIdx"));
					int fleIdx = reportFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					fileParam.put("REGI_IDX", loginVO.getMemberIdx());
					fileParam.put("REGI_ID", loginVO.getMemberId());
					fileParam.put("REGI_NAME", loginVO.getMemberName());
					fileParam.put("REGI_IP", regiIp);
					result = reportFileDAO.insert(fileParam);
				}
			}
		}
		
		reportDAO.insertReprtConfmByDto(param);
		return result > 0 ? keyIdx : result;
	}

	/**
	 * 수정처리 : 화면에 조회된 정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
	 * 
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
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public int update(String uploadModulePath, int fnIdx, String regiIp,
			ParamForm parameterMap, JSONObject settingInfo, JSONObject items,
			JSONArray itemOrder, String confmStatus) throws Exception {
		if (JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder))
			return -1;
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
																				// 사용자
																				// 정보
		Map<String, Object> param = new HashMap<String, Object>(); // mapper
																	// parameter
																	// 데이터
		List<DTForm> searchList = new ArrayList<DTForm>(); // 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>(); // 저장항목
		int result = 0;

		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column"); // key
																					// 컬럼
		// 테이블
		String targetTable = "HRD_CNSL_REPORT";
		// 테이블키
		String targetTableIdx = "REPRT_IDX";

		param.put("targetTable", targetTable);
		param.put("targetTableIdx", targetTableIdx);
		// 1. key 얻기
		param.put("fnIdx", fnIdx);

		int keyIdx = reportDAO.getNextId(param);
		int reprtIdx = parameterMap.getInt("reprtIdx");
		param.put("REPRT_IDX", reprtIdx);
		param.put("reprtIdx", reprtIdx);
		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if (dataMap == null || dataMap.size() == 0)
			return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if (itemDataList != null)
			dataList.addAll(itemDataList);

		// 2.2 등록자 정보
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		param.put("dataList", dataList);
		param.put("searchList", searchList);
		param.put("fnIdx", fnIdx);
		// 3. DB 저장
		// 3.1 기본 정보 테이블

		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		param.put("jsonValue", parameterMap.get("jsonValue"));
		if(parameterMap.getString("change") != null) {
			param.put("confmStatus", confmStatus);
			param.put("confmCn", parameterMap.getString("confmCn"));
			param.put("cnslIdx", parameterMap.getInt("cnslIdx"));
			result = updateProgress(param, regiIp);
		} else {
			// 이력 저장
			result = reportDAO.update(param);
		}

		dataList.remove(1);
		dataList.remove(1);
		dataList.remove(1);
		dataList.remove(1);
		dataList = addRegiInfo(dataList, loginVO, regiIp);

		dataList.add(new DTForm("REPRT_IDX", parameterMap.getInt("reprtIdx")));
		dataList.add(new DTForm("CNSL_IDX", parameterMap.getInt("cnslIdx")));
		dataList.add(new DTForm("CONFM_CN", parameterMap.getString("confmCn")));

		if(parameterMap.getString("change") == null) {
			reportDAO.insertReprtConfmByDto(param);
		}
		
		if (result > 0) {
			// 3.2 multi data 저장
			// 3.2.1 multi data 삭제
			/*
			 * int result1 = 0; Map<String, Object> multiDelParam = new
			 * HashMap<String, Object>(); multiDelParam.put("fnIdx", fnIdx);
			 * multiDelParam.put("KEY_IDX", keyIdx); result1 =
			 * consultingMultiDAO.cdelete(multiDelParam);
			 */

			// 3.2.2 multi data 등록
			/*
			 * List multiDataList =
			 * StringUtil.getList(dataMap.get("multiDataList")); if
			 * (multiDataList != null) { int multiDataSize =
			 * multiDataList.size(); for (int i = 0; i < multiDataSize; i++) {
			 * Map<String, Object> multiParam = (HashMap) multiDataList.get(i);
			 * multiParam.put("fnIdx", fnIdx); multiParam.put("KEY_IDX",
			 * keyIdx); multiParam.put("CNSL_IDX", parameterMap.get("cnslIdx"));
			 * result = consultingMultiDAO.insert(multiParam); } }
			 */

			// 3.3 multi file 저장
			// 3.3.1 multi file 신규 저장
			List fileDataList = StringUtil.getList(dataMap.get("fileDataList"));
			if (fileDataList != null) {
				// 3.3.1.1 key 얻기
				/*
				 * Map<String, Object> fileParam1 = new HashMap<String,
				 * Object>(); fileParam1.put("KEY_IDX", keyIdx);
				 * fileParam1.put("fnIdx", fnIdx); int fleIdx =
				 * consultingFileDAO.getNextId(fileParam1);
				 */

				// 3.3.1.2 DB 저장
				int fileDataSize = fileDataList.size();
				for (int i = 0; i < fileDataSize; i++) {
					Map<String, Object> fileParam = (HashMap) fileDataList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", parameterMap.getInt("reprtIdx"));
					int fleIdx = reportFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					fileParam.put("CNSL_IDX", parameterMap.get("cnslIdx"));
					result = reportFileDAO.insert(fileParam);
				}
			}

			// 3.3.2 multi file 수정
			List fileModifyDataList = StringUtil
					.getList(dataMap.get("fileModifyDataList"));
			if (fileModifyDataList != null) {
				int fileDataSize = fileModifyDataList.size();
				for (int i = 0; i < fileDataSize; i++) {
					Map<String, Object> fileParam = (HashMap) fileModifyDataList.get(i);
					fileParam.put("KEY_IDX", parameterMap.getInt("reprtIdx"));
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("CNSL_IDX", parameterMap.get("cnslIdx"));
					result = reportFileDAO.update(fileParam);
				}
			}

			// 3.3.3 multi file 삭제
			List fileDeleteSearchList = StringUtil.getList(dataMap.get("fileDeleteSearchList"));
			if (fileDeleteSearchList != null) {
				List<Object> deleteMultiFileList = new ArrayList<Object>();
				int fileDataSize = fileDeleteSearchList.size();
				for (int i = 0; i < fileDataSize; i++) {
					Map<String, Object> fileParam = (HashMap) fileDeleteSearchList
							.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", parameterMap.getInt("reprtIdx"));
					fileParam.put("CNSL_IDX", parameterMap.get("cnslIdx"));
					// 6.1 삭제목록 select
					List<Object> deleteFileList2 = reportFileDAO.getList(fileParam);
					if (deleteFileList2 != null)
						deleteMultiFileList.addAll(deleteFileList2);
					// 6.2 DB 삭제
					result = reportFileDAO.cdelete(fileParam);
				}

				// 3.3.4 multi file 삭제
				FileUtil.isKeyDelete(fileRealPath, deleteMultiFileList,"FILE_SAVED_NAME");
			}

			// 4. file(단일항목) 삭제
			List deleteFileList = StringUtil.getList(dataMap.get("deleteFileList"));
			if (deleteFileList != null) {
				FileUtil.isDelete(fileRealPath, deleteFileList);
			}

		}

		return result > 0 ? keyIdx : result;
	}

	/**
	 * 삭제 전체 목록 수
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public int getDeleteCount(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return reportDAO.getDeleteCount(param);
	}

	/**
	 * 삭제 전체 목록
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getDeleteList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return reportDAO.getDeleteList(param);
	}

	/**
	 * 삭제처리 : 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
	 * 
	 * @param fnIdx
	 * @param parameterMap
	 * @param deleteIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public int delete(int fnIdx, ParamForm parameterMap, int[] deleteIdxs,
			String regiIp, JSONObject settingInfo) throws Exception {
		if (deleteIdxs == null)
			return 0;

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
																				// 사용자
																				// 정보
		Map<String, Object> param = new HashMap<String, Object>(); // mapper
																	// parameter
																	// 데이터
		List<DTForm> searchList = new ArrayList<DTForm>(); // 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>(); // 저장항목

		// 1. 저장조건
		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");
		searchList.add(new DTForm(columnName, deleteIdxs));

		// 2. 저장 항목
		dataList = addLastModiInfo(dataList, loginVO, regiIp);

		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("fnIdx", fnIdx);
		
		// 3. DB 저장
		return reportDAO.delete(param);
	}

	/**
	 * 복원처리 : 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
	 * 
	 * @param fnIdx
	 * @param restoreIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public int restore(int fnIdx, int[] restoreIdxs, String regiIp,
			JSONObject settingInfo) throws Exception {
		if (restoreIdxs == null)
			return 0;

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
																				// 사용자
																				// 정보
		Map<String, Object> param = new HashMap<String, Object>(); // mapper
																	// parameter
																	// 데이터
		List<DTForm> searchList = new ArrayList<DTForm>(); // 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>(); // 저장항목

		// 1. 저장조건
		searchList.add(
				new DTForm(JSONObjectUtil.getString(settingInfo, "idx_column"),
						restoreIdxs));

		// 2. 저장 항목
		dataList = addLastModiInfo(dataList, loginVO, regiIp);

		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("fnIdx", fnIdx);

		// 3. DB 저장
		return reportDAO.restore(param);
	}

	/**
	 * 완전삭제처리 : 화면에 조회된 정보를 데이터베이스에서 삭제
	 * 
	 * @param uploadModulePath
	 * @param fnIdx
	 * @param deleteIdxs
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	@Override
	public int cdelete(String uploadModulePath, int fnIdx, int[] deleteIdxs,
			JSONObject settingInfo, JSONObject items, JSONArray itemOrder)
			throws Exception {
		if (deleteIdxs == null)
			return 0;

		Map<String, Object> param = new HashMap<String, Object>(); // mapper
																	// parameter
																	// 데이터
		List<DTForm> searchList = new ArrayList<DTForm>(); // 검색조건

		// 1. 저장조건
		searchList.add(
				new DTForm(JSONObjectUtil.getString(settingInfo, "idx_column"),
						deleteIdxs));
		param.put("searchList", searchList);
		param.put("fnIdx", fnIdx);

		List<Object> deleteMultiFileList = null;
		List<Object> deleteFileList = null;
		// 2. 삭제할 파일 select
		// 2.1 삭제할 multi file 목록 select
		deleteMultiFileList = reportFileDAO.getList(param);
		// 2.2 삭제할 file(단일항목) select
		List deleteFileColumnList = ModuleUtil.getFileObjectList(items,
				itemOrder);
		if (deleteFileColumnList != null) {
			param.put("columnList", deleteFileColumnList);

			deleteFileList = reportDAO.getFileList(param);
		}

		// 3. delete
		int result = reportDAO.cdelete(param);
		if (result > 0) {
			// 4. 파일 삭제
			// 4.1 multi file 삭제
			String fileRealPath = RbsProperties
					.getProperty("Globals.upload.file.path") + File.separator
					+ uploadModulePath;
			if (deleteMultiFileList != null) {
				FileUtil.isKeyDelete(fileRealPath, deleteMultiFileList,
						"FILE_SAVED_NAME");
			}

			// 4.2 file(단일항목) 삭제
			if (deleteFileList != null) {
				FileUtil.isKeyDelete(fileRealPath, deleteFileList);
			}
		}

		return result;
	}
	
	/**
	 * mult file 전체 목록 : 항목ID에 대한 HashMap
	 * 
	 * @param fnIdx
	 * @param keyIdx
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	public Map<String, List<Object>> getMultiFileHashMap(int fnIdx, int keyIdx,
			JSONObject settingInfo, JSONObject items, JSONArray itemOrder) {
		Map<String, List<Object>> resultHashMap = new HashMap<String, List<Object>>();
		if (!JSONObjectUtil.isEmpty(itemOrder) && !JSONObjectUtil.isEmpty(items)) {
			List<String> itemIdList = new ArrayList<String>();
			String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");
			int searchOrderSize = itemOrder.size();

			for (int i = 0; i < searchOrderSize; i++) {
				String itemId = JSONObjectUtil.getString(itemOrder, i);
				JSONObject item = JSONObjectUtil.getJSONObject(items, itemId);
				int formatType = JSONObjectUtil.getInt(item, "format_type");
				int objectType = JSONObjectUtil.getInt(item, "object_type");
				if (formatType == 0 && objectType == 9) {
					// mult file
					itemIdList.add(itemId);
					/*
					 * Map<String, Object> param = new HashMap<String,
					 * Object>(); List<DTForm> searchList = new
					 * ArrayList<DTForm>(); searchList.add(new DTForm("A." +
					 * columnName, keyIdx)); searchList.add(new
					 * DTForm("A.ITEM_ID", itemId)); param.put("searchList",
					 * searchList); param.put("fnIdx", fnIdx);
					 * resultHashMap.put(itemId,
					 * consultingFileDAO.getList(param));
					 */
				}
			}
			
			if (itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
				searchList.add(new DTForm("A." + columnName, keyIdx));
				searchList.add(new DTForm("A.ITEM_ID", (String[]) itemIdList.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
				param.put("fnIdx", fnIdx);
				param.put("KEY_IDX", keyIdx);
				resultHashMap = reportFileDAO.getMapList(param);
			}
		}

		return resultHashMap;
	}

	/**
	 * mult data 전체 목록 : 항목ID에 대한 HashMap
	 * 
	 * @param fnIdx
	 * @param keyIdx
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	@Override
	public Map<String, List<Object>> getMultiHashMap(int fnIdx, int keyIdx,
			JSONObject settingInfo, JSONObject items, JSONArray itemOrder) {
		Map<String, List<Object>> resultHashMap = new HashMap<String, List<Object>>();
		if (!JSONObjectUtil.isEmpty(itemOrder)
				&& !JSONObjectUtil.isEmpty(items)) {
			List<String> itemIdList = new ArrayList<String>();
			String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");
			int searchOrderSize = itemOrder.size();
			for (int i = 0; i < searchOrderSize; i++) {
				String itemId = JSONObjectUtil.getString(itemOrder, i);
				JSONObject item = JSONObjectUtil.getJSONObject(items, itemId);
				int formatType = JSONObjectUtil.getInt(item, "format_type");
				int objectType = JSONObjectUtil.getInt(item, "object_type");
				if (formatType == 0 && (objectType == 3 || objectType == 4 || objectType == 11)) {
					itemIdList.add(itemId);
					/*
					 * Map<String, Object> param = new HashMap<String,
					 * Object>(); List<DTForm> searchList = new
					 * ArrayList<DTForm>(); searchList.add(new DTForm("A." +
					 * columnName, keyIdx)); searchList.add(new
					 * DTForm("A.ITEM_ID", itemId)); param.put("searchList",
					 * searchList); param.put("fnIdx", fnIdx);
					 * resultHashMap.put(itemId,
					 * consultingMultiDAO.getList(param));
					 */
				}
			}
			if (itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
				searchList.add(new DTForm("A." + columnName, keyIdx));
				searchList.add(new DTForm("A.ITEM_ID", (String[]) itemIdList.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
				param.put("fnIdx", fnIdx);
				resultHashMap = reportMultiDAO.getMapList(param);
			}
		}

		return resultHashMap;
	}

	/**
	 * 파일등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * 
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
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> getFileUpload(String uploadModulePath, int fnIdx,
			String regiIp, ParamForm parameterMap, JSONObject settingInfo,
			JSONObject items, JSONArray itemOrder) throws Exception {
		if (JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder))
			return null;

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(
				fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if (dataMap == null || dataMap.size() == 0)
			return null;
		/*
		 * System.out.println("-----------itemOrder:" + itemOrder);
		 * System.out.println("-----------dataMap:" + dataMap);
		 * System.out.println("-----------file_origin_name:" +
		 * parameterMap.get("file_origin_name"));
		 * System.out.println("-----------file_saved_name:" +
		 * parameterMap.get("file_saved_name"));
		 * System.out.println("-----------file_size:" +
		 * parameterMap.get("file_size"));
		 */

		List originList = StringUtil
				.getList(parameterMap.get("file_origin_name"));
		Map<String, Object> fileInfo = null;
		if (originList != null && !originList.isEmpty()) {
			String fileOriginName = StringUtil.getString(originList.get(0));
			HashMap savedMap = StringUtil.getHashMap(parameterMap.get("file_saved_name"));
			HashMap sizeMap = StringUtil.getHashMap(parameterMap.get("file_size"));
			fileInfo = new HashMap<String, Object>();
			fileInfo.put("file_origin_name", fileOriginName);
			fileInfo.put("file_saved_name", savedMap.get(fileOriginName));
			fileInfo.put("file_size", sizeMap.get(fileOriginName));
		}
		return fileInfo;
	}

	/**
	 * 등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * 
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
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public int insertDevReport(String uploadModulePath, int fnIdx,
			String regiIp, ParamForm parameterMap, JSONObject settingInfo,
			JSONObject items, JSONArray itemOrder) throws Exception {
		if (JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder))
			return -1;
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
																				// 사용자
																				// 정보
		Map<String, Object> param = new HashMap<String, Object>(); // mapper
																	// parameter
																	// 데이터
		List<DTForm> dataList = new ArrayList<DTForm>(); // 저장항목

		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column"); // key
																					// 컬럼

		// 테이블
		String targetTable = "HRD_CNSL_REPORT_TP";
		// 테이블키
		String targetTableIdx = "TP_IDX";

		param.put("targetTable", targetTable);
		param.put("targetTableIdx", targetTableIdx);
		// 1. key 얻기
		param.put("fnIdx", fnIdx);

		int keyIdx = reportDAO.getNextId(param);
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty(
				"Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(
				fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if (dataMap == null || dataMap.size() == 0)
			return -1;

		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if (itemDataList != null)
			dataList.addAll(itemDataList);

		dataList.add(new DTForm("TP_IDX", keyIdx));
		dataList.add(new DTForm("CNSL_IDX", parameterMap.getInt("cnslIdx")));
		dataList.add(new DTForm("BRFFC_CD", parameterMap.getString("brffcCd")));

		// 2.2 등록자 정보
		dataList = addRegiInfo(dataList, loginVO, regiIp);
		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		param.put("dataList", dataList);
		param.put("tpPicTelNo", parameterMap.getString("tpPicTelNo"));
		param.put("brffcCd", parameterMap.getString("brffcCd"));
		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = reportDAO.insertDevReport(param);
		parameterMap.put("type", "insert");
		deleteAndinsertTpSub("HRD_CNSL_REPORT_TP_SUB", parameterMap.getInt("cnslIdx"), keyIdx, regiIp, parameterMap);

		return result > 0 ? keyIdx : result;
	}

	/**
	 * 수정처리 : 화면에 조회된 정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
	 * 
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
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public int updateDevReport(String uploadModulePath, int fnIdx,
			String regiIp, ParamForm parameterMap, JSONObject settingInfo,
			JSONObject items, JSONArray itemOrder) throws Exception {
		if (JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder))
			return -1;
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
																				// 사용자
																				// 정보
		Map<String, Object> param = new HashMap<String, Object>(); // mapper
																	// parameter
																	// 데이터
		List<DTForm> searchList = new ArrayList<DTForm>(); // 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>(); // 저장항목

		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column"); // key
																					// 컬럼
		// 1. 검색조건 setting
		// 테이블
		String targetTable = "HRD_CNSL_REPORT_TP";
		// 테이블키
		String targetTableIdx = "TP_IDX";

		param.put("targetTable", targetTable);
		param.put("targetTableIdx", targetTableIdx);
		// 1. key 얻기
		param.put("fnIdx", fnIdx);

		String cnslIdx = parameterMap.getString("cnslIdx");
		int reprtIdx = parameterMap.getInt("reprtIdx");
		param.put("REPRT_IDX", reprtIdx);
		int tpIdx = parameterMap.getInt("TP_IDX");

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty(
				"Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(
				fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if (dataMap == null || dataMap.size() == 0)
			return -1;

		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if (itemDataList != null)
			dataList.addAll(itemDataList);

		// 2.2 등록자 정보
		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		param.put("dataList", dataList);
		param.put("searchList", searchList);
		param.put("fnIdx", fnIdx);
		param.put("tpPicTelNo", parameterMap.getString("tpPicTelNo"));
		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = reportDAO.updateDevReport(param);
		parameterMap.put("type", "update");
		deleteAndinsertTpSub("HRD_CNSL_REPORT_TP_SUB", StringUtil.getInt((String) cnslIdx), StringUtil.getInt(tpIdx), regiIp, parameterMap);
		return result;
	}

	/**
	 * [훈련과정맞춤개발] TP_SUB에 인서트
	 * 
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertTpSub(String targetTable, int cnslIdx, int tpIdx,
			String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
																				// 사용자
																				// 정보

		// HRD담당자 정보는 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로
		// insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>(); // mapper
																		// parameter
																		// 기존꺼
																		// 삭제용
		searchList = new ArrayList<DTForm>(); // 삭제조건

		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("CNSL_IDX", cnslIdx));
		searchList.add(new DTForm("TP_IDX", tpIdx));

		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
		// 기존 데이터 삭제하기
		reportMultiDAO.cdelete(delParam);
		int insertSub = 0;
		param = new HashMap<String, Object>(); // mapper parameter 데이터
		dataList = new ArrayList<DTForm>(); // 데이터 항목
		param.put("targetTable", targetTable);
		int tpSubLength = StringUtil
				.getInt((String) parameterMap.get("tpSubLength"));

		for (int i = 0; i <= tpSubLength; i++) {
			String courseNo = (String) parameterMap.get("courseNo" + i);
			String courseName = (String) parameterMap.get("courseName" + i);
			String cn = (String) parameterMap.get("cn" + i);
			String time = (String) parameterMap.get("time" + i);
			String tchMethod = (String) parameterMap.get("tchMethod" + i);
			String prtbizIdx = (String) parameterMap.get("prtbizIdx");

			// REGI, LAST_MODI 항목 setting
			dataList = addRegiInfo(dataList, loginVO, regiIp);
			dataList = addLastModiInfo(dataList, loginVO, regiIp);
			dataList.add(new DTForm("TP_COURSE_IDX", i + 1));
			dataList.add(new DTForm("CNSL_IDX", cnslIdx));
			dataList.add(new DTForm("TP_IDX", tpIdx));
			if (parameterMap.getString("type").equals("update")) {
				dataList.add(new DTForm("REPRT_IDX",
						parameterMap.getInt("reprtIdx")));
			}
			dataList.add(new DTForm("PRTBIZ_IDX", prtbizIdx));
			dataList.add(new DTForm("COURSE_NO", courseNo));
			dataList.add(new DTForm("COURSE_NAME", courseName));
			dataList.add(new DTForm("TR_CN", cn));
			dataList.add(new DTForm("TRTM", time));
			dataList.add(new DTForm("TCHMETHOD", tchMethod));

			param.put("dataList", dataList);
			param.put("type", parameterMap.getString("type"));

			insertSub += reportMultiDAO.insert(param);
			dataList.clear();
		}
		
		return insertSub;
	}

	/**
	 * @param param
	 * @return List<Object>
	 * @throws Exception
	 */
	@Override
	public DataMap getTpInfo(Map<String, Object> param) throws Exception {
		return reportDAO.getTpInfo(param);
	}

	/**
	 * @param param
	 * @return List<Object>
	 * @throws Exception
	 */
	@Override
	public List<Object> getTpSubInfo(Map<String, Object> param) throws Exception {
		return reportDAO.getTpSubInfo(param);
	}

	@Override
	@Transactional
	public void saveReportByDto(Report report, MultiValueMap<String, MultipartFile> files, String regiIp) {
		
		log.info("files2{}", files);
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}

		// 1. HRD_CNSL_REPORT 등록
		log.info("Report 인서트 전 {}", report);
		reportDAO.saveReportByDto(report);
		log.info("Report 인서트 후 {}", report);

		// 2. HRD_CNSL_REPORT_FILE 등록
		String uploadPath = uploadDir + "consulting/" + report.getCnslIdx(); // globals.properties의									
		
		//이전 파일
		List<ReportFileDTO> prefiles = reportDAO.selectReportFiles(report.getCnslIdx());
		Set<String> existingItemIds = new HashSet<>();
		log.info("넘어온 파일들 {}",files);
		
		for (String key : files.keySet()) {
			log.info("keys {}", key);
			if(files.get(key) != null) {
			for (MultipartFile file : files.get(key)) {
				// 파일 저장 로직
				String fileName = file.getName();
				log.info("fileName {}", fileName);
				String fileRealName = file.getOriginalFilename();
				Date currentDate = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyyMMddHHmmss");
				String uniqueName = dateFormat.format(currentDate);

				File uploadFolder = new File(uploadPath);
				if (!uploadFolder.exists()) {
					uploadFolder.mkdirs();
				}

				File saveFile = new File(uploadFolder + "/" + uniqueName + "_" + fileRealName);
				log.info("saveFile {}", saveFile);
				try {
					file.transferTo(saveFile); // 실제 파일 저장 메서드
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// 리포트 첨부파일 DB 저장
				ReportFileDTO reportFileDTO = new ReportFileDTO.Builder(
						report.getCnslIdx()).setReprtIdx(report.getReprtIdx())
								.setItemId(fileName)
								.setFileOriginName(fileRealName)
								.setFileSavedName(
										uniqueName + "_" + fileRealName)
								.setFileSize(file.getSize())
								.setRegiId(loginMemberId)
								.setRegiIdx(loginMemberIdx).setRegiIp(regiIp)
								.setRegiName(loginMemberName)
								.setLastModiId(loginMemberId)
								.setLastModiIdx(loginMemberIdx)
								.setLastModiIp(regiIp)
								.setLastModiName(loginMemberName).build();

				reportDAO.saveReportFileByDTO(reportFileDTO);
				log.info("새로 들어온 파일 {}", fileName);
				
			}
			
		}
			existingItemIds.add(key);
			
		}
		
		List<ReportFileDTO> deleteFiles = new ArrayList<>();
		for(ReportFileDTO dto : prefiles) {
			String itemId = dto.getItemId();
			if(!existingItemIds.contains(itemId)) {
				deleteFiles.add(dto);
			}
		}
		if(deleteFiles != null && !deleteFiles.isEmpty()) {
			log.info("지울 파일들 {}", deleteFiles);
			reportDAO.deleteReportFileByDto(deleteFiles);
		}
		
		
		// confm 이력 생성
		reportDAO.insertReportConfm(report);

	}

	@Override
	public List<HashMap<String, Object>> getListByAnotherTypeCnsl(int cnslIdx) {
		return reportDAO.getListByAnotherTypeCnsl(cnslIdx);
	}

	/*********************** 임시 ***********************/
	@Override
	public int saveReportWithFiles_TEMP(Map<String, Object> param,
			MultiValueMap<String, MultipartFile> files, String reqIp)
			throws Exception {
		int result = 0;
		try {
			// TODO: 1. files 가공 후 저장

			// 2. formdata 저장
			result = reportDAO.insertReportData_TEMP(param);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public Map<String, Object> selectReportData_TEMP(
			Map<String, Object> param) {
		return reportDAO.selectReportData_TEMP(param);
	}
	/*********************** 임시 끝 ***********************/

	@Override
	public Report selectReportData(String cnslIdx) {
		return reportDAO.selectReportData(cnslIdx);
	}
	
	@Override
	public int updateProgress(Map<String, Object> param, String regiIp) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		param.put("memberIdx", loginVO.getMemberIdx());
		param.put("memberId", loginVO.getMemberId());
		param.put("memberName", loginVO.getMemberName());
		param.put("regiIp", regiIp);
		reportDAO.insertReportConfmByMap(param);
		
		// 이력추가
		return reportDAO.updateProgress(param);
	}

	@Override
	public Map getBSC(String bscIdx) {
		Map result = new HashMap();
		Map base = reportDAO.selectBSCbase(bscIdx);
		List<Map> trainHis = reportDAO.selectBSCtrainHis(bscIdx);
		List<Map> fundHis = reportDAO.selectBSCfundHis(bscIdx);
		List<Map> rctr = reportDAO.selectBSISrctr(bscIdx);
		result.put("base", base);
		result.put("trainHis", trainHis);
		result.put("fundHis", fundHis);
		result.put("rctr", rctr);
		return result;
	}

	@Override
	public List<Map> getDiaryPBL(String cnslIdx) {
		
		return reportDAO.selectDiaryPBL(cnslIdx);
	}
	
	@Override
	public int chkDiaryCnt(int cnslIdx) throws Exception {
		return reportDAO.chkDiaryCnt(cnslIdx);
	}
	
}
