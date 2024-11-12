package rbs.modules.consulting.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
import rbs.modules.consulting.dto.Cnsl;
import rbs.modules.consulting.dto.CnslDiaryDTO;
import rbs.modules.consulting.dto.CnslFileDTO;
import rbs.modules.consulting.dto.CnslTeamDTO;
import rbs.modules.consulting.dto.CnslTeamMemberDTO;
import rbs.modules.consulting.dto.HrpUserDTO;
import rbs.modules.consulting.mapper.ConsultingFileMapper;
import rbs.modules.consulting.mapper.ConsultingMapper;
import rbs.modules.consulting.mapper.ConsultingMultiMapper;
import rbs.modules.consulting.mapper.EvaluationAutoMapper;
import rbs.modules.consulting.service.ConsultingService;

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * 
 * @author user
 *
 */
@Service("consultingService")
public class ConsultingServiceImpl extends EgovAbstractServiceImpl
		implements
			ConsultingService {

	@Value("${Globals.fileStorePath}")
	private String uploadDir;
	
	@Resource(name = "chgLogMapper")
	private ChgLogMapper chgLogMapper;

	@Resource(name = "consultingMapper")
	private ConsultingMapper consultingDAO;

	@Resource(name = "consultingFileMapper")
	private ConsultingFileMapper consultingFileDAO;
	
	@Resource(name = "evaluationAutoMapper")
	private EvaluationAutoMapper evaluationAutoDAO;


	@Resource(name = "consultingMultiMapper")
	private ConsultingMultiMapper consultingMultiDAO;

	private static final org.slf4j.Logger log = LoggerFactory
			.getLogger(ConsultingServiceImpl.class);

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
		return consultingDAO.getTotalCount(param);
	}

	/**
	 * 전체 목록
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return consultingDAO.getList(param);
	}

	/**
	 * 상세조회
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getView(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		DataMap viewDAO = consultingDAO.getView(param);
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
		DataMap viewDAO = consultingDAO.getFileView(param);
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
		DataMap viewDAO = consultingFileDAO.getFileView(param);
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
		return consultingDAO.updateFileDown(param);

	}

	/**
	 * multi file 다운로드 수 수정
	 * 
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateMultiFileDown(int fnIdx, int keyIdx, int fleIdx,
			String itemId) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		searchList.add(new DTForm("FLE_IDX", fleIdx));
		searchList.add(new DTForm("ITEM_ID", itemId));
		param.put("searchList", searchList);
		param.put("fnIdx", fnIdx);
		param.put("KEY_IDX", keyIdx);
		return consultingFileDAO.updateFileDown(param);

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
		DataMap viewDAO = consultingDAO.getModify(param);
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
		return consultingDAO.getAuthCount(param);
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
			JSONArray itemOrder) throws Exception {
		if (JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder))
			return -1;

		Map<String, Object> param = new HashMap<String, Object>(); // mapper
																	// parameter
																	// 데이터
		List<DTForm> dataList = new ArrayList<DTForm>(); // 저장항목

		String columnName = JSONObjectUtil.getString(settingInfo, "diary_column"); // key
									// 컬럼

		// 1. key 얻기
		param.put("fnIdx", fnIdx);
		param.put("targetTable", "diary");
		int keyIdx = consultingDAO.getNextId(param);

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
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
																				// 사용자
																				// 정보
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

		dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
		dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
		dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
		dataList.add(new DTForm("LAST_MODI_IP", regiIp));

		param.put("mtgStartDt", parameterMap.getString("mtgStartDt"));
		param.put("mtgEndDt", parameterMap.getString("mtgEndDt"));
		param.put("dataList", dataList);
		
		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = consultingDAO.insert(param);

		if (result > 0) {
			// 3.2 multi data 저장
			List multiDataList = StringUtil
					.getList(dataMap.get("multiDataList"));
			if (multiDataList != null) {
				int multiDataSize = multiDataList.size();
				for (int i = 0; i < multiDataSize; i++) {
					Map<String, Object> fileParam = (HashMap) multiDataList
							.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", keyIdx);
					fileParam.put("CNSL_IDX", dataMap.get("cnslIdx"));
					result = consultingMultiDAO.insert(fileParam);
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
					int fleIdx = consultingFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = consultingFileDAO.insert(fileParam);
				}
			}
		}

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
	public int update(String uploadModulePath, int fnIdx, int keyIdx,
			String regiIp, ParamForm parameterMap, JSONObject settingInfo,
			JSONObject items, JSONArray itemOrder) throws Exception {
		if (JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder))
			return -1;

		Map<String, Object> param = new HashMap<String, Object>(); // mapper
																	// parameter
																	// 데이터
		List<DTForm> searchList = new ArrayList<DTForm>(); // 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>(); // 저장항목

		String columnName = JSONObjectUtil.getString(settingInfo, "diary_column"); // key
									// 컬럼
		// 1. 검색조건 setting
		searchList.add(new DTForm(columnName, keyIdx));

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
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
																				// 사용자
																				// 정보
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

		param.put("dataList", dataList);
		param.put("searchList", searchList);
		param.put("fnIdx", fnIdx);
		param.put("mtgStartDt", parameterMap.getString("mtgStartDt"));
		param.put("mtgEndDt", parameterMap.getString("mtgEndDt"));
		
		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = consultingDAO.update(param);

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
					Map<String, Object> fileParam = (HashMap) fileDataList
							.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", keyIdx);
					int fleIdx = consultingFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					fileParam.put("CNSL_IDX", parameterMap.get("cnslIdx"));
					result = consultingFileDAO.insert(fileParam);
				}
			}

			// 3.3.2 multi file 수정
			List fileModifyDataList = StringUtil
					.getList(dataMap.get("fileModifyDataList"));
			if (fileModifyDataList != null) {
				int fileDataSize = fileModifyDataList.size();
				for (int i = 0; i < fileDataSize; i++) {
					Map<String, Object> fileParam = (HashMap) fileModifyDataList
							.get(i);
					fileParam.put("KEY_IDX", keyIdx);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("CNSL_IDX", parameterMap.get("cnslIdx"));
					result = consultingFileDAO.update(fileParam);
				}
			}

			// 3.3.3 multi file 삭제
			List fileDeleteSearchList = StringUtil.getList(dataMap.get("fileDeleteSearchList"));
			if (fileDeleteSearchList != null) {
				List<Object> deleteMultiFileList = new ArrayList<Object>();
				int fileDataSize = fileDeleteSearchList.size();
				for (int i = 0; i < fileDataSize; i++) {
					Map<String, Object> fileParam = (HashMap) fileDeleteSearchList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", keyIdx);
					fileParam.put("CNSL_IDX", parameterMap.get("cnslIdx"));
					// 6.1 삭제목록 select
					List<Object> deleteFileList2 = consultingFileDAO.getList(fileParam);
					if (deleteFileList2 != null)
						deleteMultiFileList.addAll(deleteFileList2);
					// 6.2 DB 삭제
					result = consultingFileDAO.cdelete(fileParam);
				}

				// 3.3.4 multi file 삭제
				FileUtil.isKeyDelete(fileRealPath, deleteMultiFileList, "FILE_SAVED_NAME");
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
		return consultingDAO.getDeleteCount(param);
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
		return consultingDAO.getDeleteList(param);
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
		String columnName = JSONObjectUtil.getString(settingInfo, "diary_column");
		searchList.add(new DTForm(columnName, deleteIdxs));

		// 2. 저장 항목
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

		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("fnIdx", fnIdx);
		
		// 3. DB 저장
		return consultingDAO.delete(param);
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

		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("fnIdx", fnIdx);

		// 3. DB 저장
		return consultingDAO.restore(param);
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
		deleteMultiFileList = consultingFileDAO.getList(param);
		// 2.2 삭제할 file(단일항목) select
		List deleteFileColumnList = ModuleUtil.getFileObjectList(items,
				itemOrder);
		if (deleteFileColumnList != null) {
			param.put("columnList", deleteFileColumnList);

			deleteFileList = consultingDAO.getFileList(param);
		}

		// 3. delete
		int result = consultingDAO.cdelete(param);
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
			String columnName = JSONObjectUtil.getString(settingInfo, "diary_column");
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
				resultHashMap = consultingFileDAO.getMapList(param);
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
			String columnName = JSONObjectUtil.getString(settingInfo,
					"idx_column");
			int searchOrderSize = itemOrder.size();
			for (int i = 0; i < searchOrderSize; i++) {
				String itemId = JSONObjectUtil.getString(itemOrder, i);
				JSONObject item = JSONObjectUtil.getJSONObject(items, itemId);
				int formatType = JSONObjectUtil.getInt(item, "format_type");
				int objectType = JSONObjectUtil.getInt(item, "object_type");
				if (formatType == 0 && (objectType == 3 || objectType == 4
						|| objectType == 11)) {
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
				searchList.add(new DTForm("A.ITEM_ID", (String[]) itemIdList
						.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
				param.put("fnIdx", fnIdx);
				resultHashMap = consultingMultiDAO.getMapList(param);
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
		String fileRealPath = RbsProperties.getProperty(
				"Globals.upload.file.path") + File.separator + uploadModulePath;
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
			HashMap savedMap = StringUtil
					.getHashMap(parameterMap.get("file_saved_name"));
			HashMap sizeMap = StringUtil
					.getHashMap(parameterMap.get("file_size"));
			fileInfo = new HashMap<String, Object>();
			fileInfo.put("file_origin_name", fileOriginName);
			fileInfo.put("file_saved_name", savedMap.get(fileOriginName));
			fileInfo.put("file_size", sizeMap.get(fileOriginName));
		}
		return fileInfo;
	}

	/*
	 * 컨설팅 저장 + 팀 저장 + 첨부파일 저장
	 * 
	 */

	@Override
	@Transactional
	public void insertCnsl(Cnsl cnsl, MultipartHttpServletRequest request,
			String regiIp) {
		// 1. 컨설팅 저장
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
																				// 사용자
																				// 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		
		cnsl.setRegiId(loginMemberId);
		cnsl.setRegiIdx(loginMemberIdx);
		cnsl.setRegiIp(regiIp);
		cnsl.setRegiName(loginMemberName);
		cnsl.setLastModiId(loginMemberId);
		cnsl.setLastModiIdx(loginMemberIdx);
		cnsl.setLastModiIp(regiIp);
		cnsl.setLastModiName(loginMemberName);

		// 컨설팅 생성
		consultingDAO.insertCnslByDto(cnsl);

		// 컨설팅 첨부파일 실제 저장
		String uploadFolder = uploadDir; // globals.properties의 uploadPath

		Iterator<String> fileNames = request.getFileNames();

		while (fileNames.hasNext()) {
			String fileName = fileNames.next();

			MultipartFile file = request.getFile(fileName);
			
			if (!StringUtils.isEmpty(file.getOriginalFilename())) {
			String fileRealName = file.getOriginalFilename();
			Date currentDate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmss");
			String uniqueName = dateFormat.format(currentDate);

			File saveFile = new File(
					uploadFolder + uniqueName + "_" + fileRealName);
			try {
				file.transferTo(saveFile); // 실제 파일 저장 메서드
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 컨설팅 첨부파일 DB 저장
			CnslFileDTO cnslFileDTO = new CnslFileDTO();
			cnslFileDTO.setCnslIdx(cnsl.getCnslIdx());
			cnslFileDTO.setItemId(fileName);
			cnslFileDTO.setFileOriginName(fileRealName);
			cnslFileDTO.setFileSavedName(uniqueName + "_" + fileRealName);
			cnslFileDTO.setFileSize(file.getSize());
			cnslFileDTO.setRegiId(loginMemberId);
			cnslFileDTO.setRegiIdx(loginMemberIdx);
			cnslFileDTO.setRegiIp(regiIp);
			cnslFileDTO.setRegiName(loginMemberName);

			consultingDAO.insertCnslFileByDto(cnslFileDTO);
			}
		}

		if (cnsl.getCnslTeam() != null) {
			// 컨설팅 팀 저장
			CnslTeamDTO teamDTO = cnsl.getCnslTeam();
			teamDTO.setCnslIdx(cnsl.getCnslIdx());
			teamDTO.setRegiId(loginMemberId);
			teamDTO.setRegiIdx(loginMemberIdx);
			teamDTO.setRegiIp(regiIp);
			teamDTO.setRegiName(loginMemberName);
			consultingDAO.insertCnslTeamByDto(teamDTO);
		}

		// CONFM 이력 생성
		if (cnsl.getConfmStatus().equals("10")) {
			consultingDAO.insertCnslConfmByDto(cnsl);
		}

	}

	@Override
	@Transactional
	public void updateCnsl(Cnsl cnsl, MultipartHttpServletRequest request,
			String regiIp) {
		// 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
																				// 사용자
																				// 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		cnsl.setLastModiId(loginMemberId);
		cnsl.setLastModiIdx(loginMemberIdx);
		cnsl.setLastModiIp(regiIp);
		cnsl.setLastModiName(loginMemberName);
		
		//컨설팅 업데이트
		consultingDAO.updateCnslByDto(cnsl);
		
		

		Iterator<String> fileNames = request.getFileNames();
		
		//이전 파일
		List<CnslFileDTO> preCnslFiles = consultingDAO.selectFileByCnslIdx(cnsl.getCnslIdx());
		
		Set<String> existingItemIds = new HashSet<>();
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();

			MultipartFile file = request.getFile(fileName);

			if (file != null) {
				if (!StringUtils.isEmpty(file.getOriginalFilename())) {

					// 컨설팅 첨부파일 실제 저장
					String uploadFolder = uploadDir; // globals.properties의
														// uploadPath
					String fileRealName = file.getOriginalFilename();
					Date currentDate = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyyMMddHHmmss");
					String uniqueName = dateFormat.format(currentDate);

					File saveFile = new File(
							uploadFolder + uniqueName + "_" + fileRealName);
					try {
						file.transferTo(saveFile); // 실제 파일 저장 메서드
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					// 컨설팅 첨부파일 DB 저장
					CnslFileDTO cnslFileDTO = new CnslFileDTO();
					cnslFileDTO.setCnslIdx(cnsl.getCnslIdx());
					cnslFileDTO.setItemId(fileName);
					cnslFileDTO.setFileOriginName(fileRealName);
					cnslFileDTO
							.setFileSavedName(uniqueName + "_" + fileRealName);
					cnslFileDTO.setFileSize(file.getSize());
					cnslFileDTO.setLastModiId(loginMemberId);
					cnslFileDTO.setLastModiIdx(loginMemberIdx);
					cnslFileDTO.setLastModiIp(regiIp);
					cnslFileDTO.setLastModiName(loginMemberName);

					consultingDAO.updateCnslFileByDto(cnslFileDTO);
					
					
				}
	
			}
			
			existingItemIds.add(fileName);
			

		}
		List<CnslFileDTO> deleteFiles = new ArrayList<>();
		for(CnslFileDTO dto : preCnslFiles) {
			String itemId = dto.getItemId();
			if(!existingItemIds.contains(itemId)) {
				deleteFiles.add(dto);
			}
		}
		if(deleteFiles != null && !deleteFiles.isEmpty()) {
			consultingDAO.deleteCnslFileByDto(deleteFiles);
		}

		if (cnsl.getCnslTeam() != null) {
			//기존 저장되어 있던 팀
			CnslTeamDTO cnslTeam = consultingDAO.selectTeamByCnslIdx(cnsl.getCnslIdx());
			//새로 입력된 팀
			CnslTeamDTO teamDTO = cnsl.getCnslTeam();

			teamDTO.setCnslIdx(cnsl.getCnslIdx());
			teamDTO.setLastModiId(loginMemberId);
			teamDTO.setLastModiIdx(loginMemberIdx);
			teamDTO.setLastModiIp(regiIp);
			teamDTO.setLastModiName(loginMemberName);
			
			if (cnslTeam == null) {
				// 컨설팅 팀 저장(이전에 안만들고 최초 생성할 경우)
				consultingDAO.insertCnslTeamByDto(teamDTO);

			} else {
				// 컨설팅 팀 저장(이미 팀이 있는경우)
				consultingDAO.updateCnslTeamByDto(teamDTO);
				
				List<CnslTeamMemberDTO> preMembers = cnslTeam.getMembers();
				List<CnslTeamMemberDTO> newMembers = teamDTO.getMembers();
				
				Set<String> existingKeys = new HashSet<>();
				for(CnslTeamMemberDTO dto : newMembers) {
					existingKeys.add(dto.getTeamIdx() + "-" + dto.getTeamOrderIdx());
				}
				
				List<CnslTeamMemberDTO> deleteMembers = new ArrayList<>();
				for(CnslTeamMemberDTO dto : preMembers) {
					String key = dto.getTeamIdx() + "-" + dto.getTeamOrderIdx();
					
					if(!existingKeys.contains(key)) {
						deleteMembers.add(dto);
					}
				}
				if(deleteMembers != null && !deleteMembers.isEmpty()) {
					teamDTO.setMembers(deleteMembers);
					consultingDAO.deleteCnslTeamByDTO(teamDTO);
				}

			}

		} else {
			// 팀 저장했다가, 빈값으로 넘기면 팀 삭제
			Integer cnslIdx = cnsl.getCnslIdx();
			consultingDAO.deleteCnslTeamByCnslIdx(cnslIdx);
		}

		// CONFM 이력 생성
		if (cnsl.getConfmStatus().equals("10")) {
			cnsl.setRegiId(loginMemberId);
			cnsl.setRegiIdx(loginMemberIdx);
			cnsl.setRegiIp(regiIp);
			cnsl.setRegiName(loginMemberName);
			consultingDAO.insertCnslConfmByDto(cnsl);
		}

	}
	
	@Override
	@Transactional
	public void updateCnslBySubmit(Cnsl cnsl, MultipartHttpServletRequest request,
			String regiIp) {
		// 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
																				// 사용자
																				// 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		cnsl.setRegiId(loginMemberId);
		cnsl.setRegiIdx(loginMemberIdx);
		cnsl.setRegiIp(regiIp);
		cnsl.setRegiName(loginMemberName);
		cnsl.setLastModiId(loginMemberId);
		cnsl.setLastModiIdx(loginMemberIdx);
		cnsl.setLastModiIp(regiIp);
		cnsl.setLastModiName(loginMemberName);
		
		//컨설팅 업데이트
		consultingDAO.updateCnslSubmitByDto(cnsl);
		
		

		Iterator<String> fileNames = request.getFileNames();
		
		//이전 파일
		List<CnslFileDTO> preCnslFiles = consultingDAO.selectFileByCnslIdx(cnsl.getCnslIdx());
		
		Set<String> existingItemIds = new HashSet<>();
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();

			MultipartFile file = request.getFile(fileName);

			if (file != null) {
				if (!StringUtils.isEmpty(file.getOriginalFilename())) {

					// 컨설팅 첨부파일 실제 저장
					String uploadFolder = uploadDir; // globals.properties의
														// uploadPath
					String fileRealName = file.getOriginalFilename();
					Date currentDate = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyyMMddHHmmss");
					String uniqueName = dateFormat.format(currentDate);

					File saveFile = new File(
							uploadFolder + uniqueName + "_" + fileRealName);
					try {
						file.transferTo(saveFile); // 실제 파일 저장 메서드
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					// 컨설팅 첨부파일 DB 저장
					CnslFileDTO cnslFileDTO = new CnslFileDTO();
					cnslFileDTO.setCnslIdx(cnsl.getCnslIdx());
					cnslFileDTO.setItemId(fileName);
					cnslFileDTO.setFileOriginName(fileRealName);
					cnslFileDTO
							.setFileSavedName(uniqueName + "_" + fileRealName);
					cnslFileDTO.setFileSize(file.getSize());
					cnslFileDTO.setLastModiId(loginMemberId);
					cnslFileDTO.setLastModiIdx(loginMemberIdx);
					cnslFileDTO.setLastModiIp(regiIp);
					cnslFileDTO.setLastModiName(loginMemberName);

					consultingDAO.updateCnslFileByDto(cnslFileDTO);
					
					
				}
	
			}
			
			existingItemIds.add(fileName);
			

		}
		List<CnslFileDTO> deleteFiles = new ArrayList<>();
		for(CnslFileDTO dto : preCnslFiles) {
			String itemId = dto.getItemId();
			if(!existingItemIds.contains(itemId)) {
				deleteFiles.add(dto);
			}
		}
		if(deleteFiles != null && !deleteFiles.isEmpty()) {
			consultingDAO.deleteCnslFileByDto(deleteFiles);
		}

		if (cnsl.getCnslTeam() != null) {
			//기존 저장되어 있던 팀
			CnslTeamDTO cnslTeam = consultingDAO.selectTeamByCnslIdx(cnsl.getCnslIdx());
			//새로 입력된 팀
			CnslTeamDTO teamDTO = cnsl.getCnslTeam();

			teamDTO.setCnslIdx(cnsl.getCnslIdx());
			teamDTO.setLastModiId(loginMemberId);
			teamDTO.setLastModiIdx(loginMemberIdx);
			teamDTO.setLastModiIp(regiIp);
			teamDTO.setLastModiName(loginMemberName);
			if (cnslTeam == null) {
				// 컨설팅 팀 저장(이전에 안만들고 최초 생성할 경우)
				consultingDAO.insertCnslTeamByDto(teamDTO);

			} else {
				// 컨설팅 팀 저장(이미 팀이 있는경우)
				consultingDAO.updateCnslTeamByDto(teamDTO);
				
				List<CnslTeamMemberDTO> preMembers = cnslTeam.getMembers();
				List<CnslTeamMemberDTO> newMembers = teamDTO.getMembers();
				
				Set<String> existingKeys = new HashSet<>();
				for(CnslTeamMemberDTO dto : newMembers) {
					existingKeys.add(dto.getTeamIdx() + "-" + dto.getTeamOrderIdx());
				}
				
				List<CnslTeamMemberDTO> deleteMembers = new ArrayList<>();
				for(CnslTeamMemberDTO dto : preMembers) {
					String key = dto.getTeamIdx() + "-" + dto.getTeamOrderIdx();
					
					if(!existingKeys.contains(key)) {
						deleteMembers.add(dto);
					}
				}
				if(deleteMembers != null && !deleteMembers.isEmpty()) {
					
					teamDTO.setMembers(deleteMembers);
					consultingDAO.deleteCnslTeamByDTO(teamDTO);
				}

			}

		} else {
			// 팀 저장했다가, 빈값으로 넘기면 팀 삭제
			Integer cnslIdx = cnsl.getCnslIdx();
			consultingDAO.deleteCnslTeamByCnslIdx(cnslIdx);
		}

		// CONFM 이력 생성
		if (cnsl.getConfmStatus().equals("10")) {
			cnsl.setRegiId(loginMemberId);
			cnsl.setRegiIdx(loginMemberIdx);
			cnsl.setRegiIp(regiIp);
			cnsl.setRegiName(loginMemberName);
			consultingDAO.insertCnslConfmByDto(cnsl);
		}

	}

	@Override
	public Cnsl selectByCnslIdx(int cnslIdx) {
		return consultingDAO.selectByCnslIdx(cnslIdx);
	}

	@Override
	@Transactional
	public void updateCnslStatus(Cnsl cnsl,MultipartHttpServletRequest request, String regiIp) {
		// 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
																				// 사용자
																				// 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		cnsl.setLastModiId(loginMemberId);
		cnsl.setLastModiIdx(loginMemberIdx);
		cnsl.setLastModiIp(regiIp);
		cnsl.setLastModiName(loginMemberName);

		consultingDAO.updateStatusBydto(cnsl);

		// 컨설팅 승인의 경우 TeamHandle 및 권한 관리
		if (cnsl.getConfmStatus().equals("55")) {
			
			Iterator<String> fileNames = request.getFileNames();
			//이전 파일
			List<CnslFileDTO> preCnslFiles = consultingDAO.selectFileByCnslIdx(cnsl.getCnslIdx());
			Set<String> existingItemIds = new HashSet<>();

			while (fileNames.hasNext()) {
				String fileName = fileNames.next();
				MultipartFile file = request.getFile(fileName);

				if (file != null) {
					if (!StringUtils.isEmpty(file.getOriginalFilename())) {

						// 컨설팅 첨부파일 실제 저장
						String uploadFolder = uploadDir; 
						String fileRealName = file.getOriginalFilename();
						Date currentDate = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
						String uniqueName = dateFormat.format(currentDate);

						File saveFile = new File(uploadFolder + uniqueName + "_" + fileRealName);
						try {
							file.transferTo(saveFile); // 실제 파일 저장 메서드
						} catch (IllegalStateException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}

						// 컨설팅 첨부파일 DB 저장
						CnslFileDTO cnslFileDTO = new CnslFileDTO();
						cnslFileDTO.setCnslIdx(cnsl.getCnslIdx());
						cnslFileDTO.setItemId(fileName);
						cnslFileDTO.setFileOriginName(fileRealName);
						cnslFileDTO.setFileSavedName(uniqueName + "_" + fileRealName);
						cnslFileDTO.setFileSize(file.getSize());
						cnslFileDTO.setLastModiId(loginMemberId);
						cnslFileDTO.setLastModiIdx(loginMemberIdx);
						cnslFileDTO.setLastModiIp(regiIp);
						cnslFileDTO.setLastModiName(loginMemberName);

						consultingDAO.updateCnslFileByDto(cnslFileDTO);	
					}
				}	
				existingItemIds.add(fileName);
			}		
			List<CnslFileDTO> deleteFiles = new ArrayList<>();
			for(CnslFileDTO dto : preCnslFiles) {
				if(!dto.getItemId().startsWith("inFile")) {
					String itemId = dto.getItemId();

					if(!existingItemIds.contains(itemId)) {
						deleteFiles.add(dto);
					}
				}

			}
			if(deleteFiles != null && !deleteFiles.isEmpty()) {
				consultingDAO.deleteCnslFileByDto(deleteFiles);
			}
			
			
			CnslTeamDTO teamDTO = cnsl.getCnslTeam();
			teamDTO.setCnslIdx(cnsl.getCnslIdx());
			teamDTO.setLastModiId(loginMemberId);
			teamDTO.setLastModiIdx(loginMemberIdx);
			teamDTO.setLastModiIp(regiIp);
			teamDTO.setLastModiName(loginMemberName);
			
			consultingDAO.updateCnslTeamByDto(teamDTO);

			CnslTeamDTO cnslTeam = consultingDAO.selectTeamByCnslIdx(cnsl.getCnslIdx());
			
			if(cnslTeam != null) {
				
			
				List<CnslTeamMemberDTO> preMembers = cnslTeam.getMembers();
				List<CnslTeamMemberDTO> newMembers = teamDTO.getMembers();
				
				Set<String> existingKeys = new HashSet<>();
				for(CnslTeamMemberDTO dto : newMembers) {
					existingKeys.add(dto.getTeamIdx() + "-" + dto.getTeamOrderIdx());
				}
				
				List<CnslTeamMemberDTO> deleteMembers = new ArrayList<>();
				for(CnslTeamMemberDTO dto : preMembers) {
					if(dto.getTeamIdx() != 3) {
						String key = dto.getTeamIdx() + "-" + dto.getTeamOrderIdx();
						
						if(!existingKeys.contains(key)) {
							deleteMembers.add(dto);
						}
					}

				}
				if(deleteMembers != null && !deleteMembers.isEmpty()) {
					
					teamDTO.setMembers(deleteMembers);
					consultingDAO.deleteCnslTeamByDTO(teamDTO);
				}
			}
			}

			// TODO 이 시점에 컨설턴트 권한 부여 해야함
			Cnsl savedCnsl = consultingDAO.selectByCnslIdx(cnsl.getCnslIdx());
			savedCnsl.setRegiId(loginMemberId);
			savedCnsl.setRegiIdx(loginMemberIdx);
			savedCnsl.setRegiIp(regiIp);
			savedCnsl.setRegiName(loginMemberName);
			consultingDAO.mergeCnslTeamAuth(savedCnsl);
		

		// CONFM 이력 생성
		cnsl.setRegiId(loginMemberId);
		cnsl.setRegiIdx(loginMemberIdx);
		cnsl.setRegiIp(regiIp);
		cnsl.setRegiName(loginMemberName);
		consultingDAO.insertCnslConfmByDto(cnsl);

	}
	
	@Override
	@Transactional
	public void updateCnslStatus(Cnsl cnsl, String regiIp) {
		// 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
																				// 사용자
																				// 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		cnsl.setLastModiId(loginMemberId);
		cnsl.setLastModiIdx(loginMemberIdx);
		cnsl.setLastModiIp(regiIp);
		cnsl.setLastModiName(loginMemberName);
		consultingDAO.updateStatusBydto(cnsl);
		
		// CONFM 이력 생성
		cnsl.setRegiId(loginMemberId);
		cnsl.setRegiIdx(loginMemberIdx);
		cnsl.setRegiIp(regiIp);
		cnsl.setRegiName(loginMemberName);
		consultingDAO.insertCnslConfmByDto(cnsl);
		
	}
	

	@Override
	public HrpUserDTO selectHrpById(String hrd4uId) {
		return consultingDAO.selectHrpById(hrd4uId);
	}

	@Override
	public List<Cnsl> selectConfmCn(String cnslIdx) {
		return consultingDAO.selectConfmByCnslIdx(cnslIdx);
	}

	@Override
	@Transactional
	public void insertChangeCnsl(Cnsl cnsl, MultipartHttpServletRequest request,
			String regiIp) {
		// 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
																				// 사용자
																				// 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		cnsl.setRegiId(loginMemberId);
		cnsl.setRegiIdx(loginMemberIdx);
		cnsl.setRegiIp(regiIp);
		cnsl.setRegiName(loginMemberName);

		consultingDAO.insertChangeCnslByDto(cnsl);

		cnsl.setLastModiId(loginMemberId);
		cnsl.setLastModiIdx(loginMemberIdx);
		cnsl.setLastModiIp(regiIp);
		cnsl.setLastModiName(loginMemberName);

		consultingDAO.updateStatusBydto(cnsl);
		// 컨설팅 첨부파일 실제 저장
		String uploadFolder = uploadDir; // globals.properties의 uploadPath

		Iterator<String> fileNames = request.getFileNames();
		
		
		

		while (fileNames.hasNext()) {
			Set<String> newFilesItemIds = new HashSet<>();
			List<CnslFileDTO> insertFilesCnslToCnslChange = new ArrayList<>();
			String fileName = fileNames.next();
			MultipartFile file = request.getFile(fileName);
			if (file != null) {
				newFilesItemIds.add(fileName);
				
				
				if (!StringUtils.isEmpty(file.getOriginalFilename())) {
				String fileRealName = file.getOriginalFilename();
				Date currentDate = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyyMMddHHmmss");
				String uniqueName = dateFormat.format(currentDate);

				File saveFile = new File(
						uploadFolder + uniqueName + "_" + fileRealName);
				try {
					file.transferTo(saveFile); // 실제 파일 저장 메서드
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				// 컨설팅 첨부파일 DB 저장
				CnslFileDTO cnslFileDTO = new CnslFileDTO();
				cnslFileDTO.setCnslIdx(cnsl.getCnslIdx());
				cnslFileDTO.setChgIdx(cnsl.getChgIdx());
				cnslFileDTO.setItemId(fileName);
				cnslFileDTO.setFileOriginName(fileRealName);
				cnslFileDTO.setFileSavedName(uniqueName + "_" + fileRealName);
				cnslFileDTO.setFileSize(file.getSize());
				cnslFileDTO.setRegiId(loginMemberId);
				cnslFileDTO.setRegiIdx(loginMemberIdx);
				cnslFileDTO.setRegiIp(regiIp);
				cnslFileDTO.setRegiName(loginMemberName);

				consultingDAO.insertChangeCnslFileByDto(cnslFileDTO);
			
				}else {
					//이전 파일
					List<CnslFileDTO> preCnslFiles = consultingDAO.selectFileByCnslIdx(cnsl.getCnslIdx());
					for(CnslFileDTO dto : preCnslFiles) {
						String itemId = dto.getItemId();
						if(newFilesItemIds.contains(itemId)) {
							dto.setChgIdx(cnsl.getChgIdx());
							insertFilesCnslToCnslChange.add(dto);
						}
					}
					
					if(!insertFilesCnslToCnslChange.isEmpty()) {
						consultingDAO.insertChangeCnslFileByExistFiles(insertFilesCnslToCnslChange);
					}
					
				}
			}

		}
		

		

	

		CnslTeamDTO teamDTO = cnsl.getCnslTeam();
		log.info("teamDTO{}", teamDTO);
		teamDTO.setCnslIdx(cnsl.getCnslIdx());
		teamDTO.setChgIdx(cnsl.getChgIdx());
		teamDTO.setRegiId(loginMemberId);
		teamDTO.setRegiIdx(loginMemberIdx);
		teamDTO.setRegiIp(regiIp);
		teamDTO.setRegiName(loginMemberName);
		consultingDAO.insertChangeCnslTeamByDto(teamDTO);

		// CONFM 이력 생성
		consultingDAO.insertCnslConfmByDto(cnsl);

	}

	// CNSL_IDX로 변경신청 있는지 조회
	@Override
	public Cnsl selectChangeCnslByCnslIdx(int cnslIdx) {
		return consultingDAO.selectChangeCnslByCnslIdx(cnslIdx);
	}

	/**
	 * HRD_CNSL_CHANGE COUNT조회
	 */
	@Override
	public int getTotalChangeCnslCount(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return consultingDAO.getTotalChangeCnslCount(param);
	}

	/**
	 * HRD_CNSL_CHANGE 전체목록
	 */
	@Override
	public List<?> getChangeList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return consultingDAO.getChangeList(param);
	}

	/**
	 * 컨설팅 변경 승인
	 */
	@Override
	@Transactional
	public void changeConfirm(int cnslIdx, String regiIp) {
		// 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}

		// Cnsl로 컨설팅변경 조회 해오기
		Cnsl changedCnsl = consultingDAO.selectChangeCnslByCnslIdx(cnslIdx);
		changedCnsl.setLastModiIdx(loginMemberIdx);
		changedCnsl.setLastModiId(loginMemberId);
		changedCnsl.setLastModiIp(regiIp);
		changedCnsl.setLastModiName(loginMemberName);

		// HRD_CNSL_CHANGE 상태값 변경, HRC_CNSL_CHANGE 데이터를 HRD_CNSL로 업데이트 (필수)
		consultingDAO.updateChangeConfirmByDto(changedCnsl);

		// HRD_CNSL_CHANGE_FILE => HRD_CNSL_FILE
		
		//기존 파일들
		 List<CnslFileDTO> preCnslFiles = consultingDAO.selectFileByCnslIdx(cnslIdx);
		//변경 파일
		 List<CnslFileDTO> changeCnslFiles = changedCnsl.getCnslFiles();

			Set<String> existingItemIds = new HashSet<>();
			List<CnslFileDTO> deleteFiles = new ArrayList<>();
			
			if(changedCnsl.getCnslFiles().get(0).getFileOriginName() != null) {
				  consultingDAO.mergeChangeFileConfirmByDto(changedCnsl); 
				for(CnslFileDTO dto : changeCnslFiles) {
					String itemId = dto.getItemId();
					existingItemIds.add(itemId);
				}
			}

			for(CnslFileDTO dto : preCnslFiles) {
				String itemId = dto.getItemId();
				if(!existingItemIds.contains(itemId)) {
					deleteFiles.add(dto);
				}
			}
			
			if(deleteFiles != null && !deleteFiles.isEmpty()) {
				consultingDAO.deleteCnslFileByDto(deleteFiles);
			}
		 

		  
		  //HRD_CNSL_CHANGE_TEAM => HRD_CNSL_TEAM log.info("teamIdx {}",changedCnsl.getCnslTeam().getTeamIdx());
		  if(changedCnsl.getCnslTeam() != null) {
			  //기존 팀
			  CnslTeamDTO preTeam = consultingDAO.selectTeamByCnslIdx(cnslIdx);
			  List<CnslTeamMemberDTO> preMembers = preTeam.getMembers();
			  //변경 팀
			  CnslTeamDTO newTeam = changedCnsl.getCnslTeam();
			  List<CnslTeamMemberDTO> newMembers = newTeam.getMembers();
			  
			  
			  //merge
			  consultingDAO.mergeChangeTeamConfirmByDto(changedCnsl);
			  
			  Set<String> existingKeys = new HashSet<>();
			  
			  for(CnslTeamMemberDTO dto : newMembers) {
					existingKeys.add(dto.getTeamIdx() + "-" + dto.getTeamOrderIdx());
				}
				
				List<CnslTeamMemberDTO> deleteMembers = new ArrayList<>();
				for(CnslTeamMemberDTO dto : preMembers) {
					String key = dto.getTeamIdx() + "-" + dto.getTeamOrderIdx();
					
					if(!existingKeys.contains(key)) {
						deleteMembers.add(dto);
					}
				}
				if(deleteMembers != null && !deleteMembers.isEmpty()) {
					
					newTeam.setMembers(deleteMembers);
					consultingDAO.deleteCnslTeamByDTO(newTeam);
				}
			  
		  }

					

}
		  
		  


	/**
	 * 컨설팅 변경 반려
	 */
	@Override
	public void changeReject(Cnsl cnsl, String regiIp) {
		// 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}

		cnsl.setLastModiIdx(loginMemberIdx);
		cnsl.setLastModiId(loginMemberId);
		cnsl.setLastModiName(loginMemberName);
		cnsl.setLastModiIp(regiIp);

		cnsl.setRegiId(loginMemberId);
		cnsl.setRegiIdx(loginMemberIdx);
		cnsl.setRegiIp(regiIp);
		cnsl.setRegiName(loginMemberName);

		consultingDAO.updateChangeRejectByDto(cnsl);

	}

	/**
	 * 수행일지 목록 수
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public int getDiaryCount(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return consultingDAO.getDiaryCount(param);
	}

	/**
	 * 수행일지 목록
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getDiaryList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return consultingDAO.getDiaryList(param);
	}
	
	/**
	 * 수행일지 중복 체크
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public int chkDuplicate(int fnIdx, ParamForm parameterMap) {
		Map<String, Object> param = new HashMap<String, Object>();
		String mtgStartDt = parameterMap.getString("mtgStartDt").replaceAll(":", "").replaceAll("-", "").replaceAll(" ", "");
		String mtgEndDt = parameterMap.getString("mtgEndDt").replaceAll(":", "").replaceAll("-", "").replaceAll(" ", "");
		String chkDate = mtgStartDt.substring(0, 8);
		String chkStart = mtgStartDt.substring(8, 12);
		String chkEnd = mtgEndDt.substring(8, 12);
		
		param.put("regiIdx", parameterMap.getString("regiIdx"));
		param.put("mtgStartDt", mtgStartDt);
		param.put("mtgEndDt", mtgEndDt);
		param.put("fnIdx", fnIdx);
		param.put("chkDate", chkDate);
		param.put("chkStart", chkStart);
		param.put("chkEnd", chkEnd);
		
		if(parameterMap.getInt("diaryIdx") != 0) {
			param.put("diaryIdx", parameterMap.getInt("diaryIdx"));
		}
		
		return consultingDAO.chkDuplicate(param);
	}

	/**
	 * 인력풀 위촉 체크
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public int chkEntrstRole(int fnIdx, ParamForm parameterMap) {
		Map<String, Object> param = new HashMap<String, Object>();
		String mtgStartDt = parameterMap.getString("mtgStartDt").replaceAll(":", "").replaceAll("-", "").replaceAll(" ", "");
		String mtgEndDt = parameterMap.getString("mtgEndDt").replaceAll(":", "").replaceAll("-", "").replaceAll(" ", "");
		String chkDate = mtgStartDt.substring(0, 8);
		String chkStart = mtgStartDt.substring(8, 12);
		String chkEnd = mtgEndDt.substring(8, 12);
		
		param.put("regiIdx", parameterMap.getString("regiIdx"));
		param.put("mtgStartDt", mtgStartDt);
		param.put("mtgEndDt", mtgEndDt);
		param.put("fnIdx", fnIdx);
		param.put("chkDate", chkDate);
		param.put("chkStart", chkStart);
		param.put("chkEnd", chkEnd);
		return consultingDAO.chkEntrstRole(param);
	}

	@Override
	public HashMap<String, String> select4uById(String hrd4uId) {
		return consultingDAO.select4uById(hrd4uId);
	}

	@Override
	public List<Cnsl> getCnslListAll(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return consultingDAO.getCnslListAll(param);
	}

	@Override
	public int getCnslListAllCount(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return consultingDAO.getCnslListAllCount(param);
	}


	@Override
	public int selectCnslCountByBplNoAndCnslType(String bplNo, String cnslType) {
		HashMap<String, String> param = new HashMap<>();
		param.put("bplNo", bplNo);
		param.put("cnslType", cnslType);
		return consultingDAO.selectCnslCountByBplNoAndCnslType(param);
	}

	@Override
	public String select4URepveNmByBizrNo(String bizrNo) {
		return consultingDAO.select4URepveNmByBizrNo(bizrNo);
		
	}

	@Override
	public HashMap<String, String> selectCenterInfo(String centerIdx) {
		return consultingDAO.selectCenterInfo(centerIdx);
	}

	@Override
	public HashMap<String, String> selectInsttIdxByZip(String zip) {
		return consultingDAO.selectInsttIdxByZip(zip);
	}
	

	// hrd4u 연동
	@Override
	public int insertExpert(Map<String, Object> param) {
		return evaluationAutoDAO.insertExpert(param);
	}
	
	@Override
	public int insertBusin(Map<String, Object> param) {
		return evaluationAutoDAO.insertBusin(param);
	}
	
	@Override
	public int insertEntrst(Map<String, Object> param) {
		return evaluationAutoDAO.insertEntrst(param);
	}
	
	@Override
	public int update4uEntrst(Map<String, Object> param) {
		return evaluationAutoDAO.update4uEntrst(param);
	}
	
	@Override
	public DataMap getInstt(Map<String, Object> param) {
		return consultingDAO.getInstt(param);
	}
	
	@Override
	public List<HashMap<String, Object>> getDiaryView(Map<String, Object> param) {
		return consultingDAO.getDiaryView(param);
	}

	@Override
	public List<HashMap<String, Object>> selectDoctorInfoByInsttIdx(String insttIdx) {
		return consultingDAO.selectDoctorInfoByInsttIdx(insttIdx);
	}

	@Override
	public int changeCmptncBrffcPic(HashMap<String, String> map) {
		return consultingDAO.changeCmptncBrffcPic(map);
	}

	@Override
	public int selectSojtIsSelected(String bplNo) {
		return consultingDAO.selectSojtIsSelected(bplNo);
	}

	@Override
	public int selectCnslCountByBplNoAndCnslType2(String bplNo) {
		return consultingDAO.selectCnslCountByBplNoAndCnslType2(bplNo);
	}
	

	/**
	 * 인력풀 평가 리스트
	 * 
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getEntList(Map<String, Object> param) {
		return consultingDAO.getEntList(param);
	}
	
	/**
	 * 인력풀 평가 수
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public int getEntCnt(Map<String, Object> param) {
		return consultingDAO.getEntCnt(param);
	}
	
	@Override
	public int insertEvl(Map<String, Object> param) {
		return consultingDAO.insertEvl(param);
	}
	
	/**
	 * 컨설턴트 평가 목록 수
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public int getEvlCount(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return consultingDAO.getEvlCount(param);
	}

	/**
	 * 컨설턴트 평가 목록
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getEvlList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return consultingDAO.getEvlList(param);
	}
	
	@Override
	public int updateEntrst(Map<String, Object> param) {
		return consultingDAO.updateEntrst(param);
	}

	@Override
	public HashMap<String, String> selectCountOfConsulting(String hrd4uId) {
		return consultingDAO.selectCountOfConsulting(hrd4uId);
	}

	@Override
	public int selectSojtConfm(String bplNo) {
		return consultingDAO.selectSojtConfm(bplNo);
	}

	@Override
	public int selectCnslSubmitCountByBplNoAndCnslType(String bplNo, String cnslType) {
		HashMap<String, String> param = new HashMap<>();
		param.put("bplNo", bplNo);
		param.put("cnslType", cnslType);
		return consultingDAO.selectCnslSubmitCountByBplNoAndCnslType(param);
	}

	@Override
	public List<HashMap<String, String>> selectNcsInfo(String json) {
		return consultingDAO.selectNcsInfo(json);
	}

	@Override
	public int selectCountIndvdlInfo(String hrd4uId) {
		return consultingDAO.selectCountIndvdlInfo(hrd4uId);
	}

	@Override
	public List<HashMap<String, String>> getInsttList() {
		return consultingDAO.getInsttList();
	}

	@Override
	public void insertCnslByCenter(Cnsl cnsl, MultipartHttpServletRequest request, String regiIp) {

		// 1. 컨설팅 저장
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); 
																

		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		cnsl.setRegiId(loginMemberId);
		cnsl.setRegiIdx(loginMemberIdx);
		cnsl.setRegiIp(regiIp);
		cnsl.setRegiName(loginMemberName);

		
		List<CnslFileDTO> preFiles = consultingDAO.selectFileByCnslIdx(cnsl.getCnslIdx());
		CnslTeamDTO preTeams = consultingDAO.selectTeamByCnslIdx(cnsl.getCnslIdx());
		
		// 컨설팅 생성
		consultingDAO.insertCnslByDto(cnsl);
		// 컨설팅 첨부파일 실제 저장
		String uploadFolder = uploadDir; // globals.properties의 uploadPath
		Iterator<String> fileNames = request.getFileNames();
		
		
		
		Set<String> existingItemIds = new HashSet<>();
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile file = request.getFile(fileName);
			if (!StringUtils.isEmpty(file.getOriginalFilename())) {
			String fileRealName = file.getOriginalFilename();
			Date currentDate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmss");
			String uniqueName = dateFormat.format(currentDate);

			File saveFile = new File(
					uploadFolder + uniqueName + "_" + fileRealName);
			try {
				file.transferTo(saveFile); // 실제 파일 저장 메서드
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 컨설팅 첨부파일 DB 저장
			CnslFileDTO cnslFileDTO = new CnslFileDTO();
			cnslFileDTO.setCnslIdx(cnsl.getCnslIdx());
			cnslFileDTO.setItemId(fileName);
			cnslFileDTO.setFileOriginName(fileRealName);
			cnslFileDTO.setFileSavedName(uniqueName + "_" + fileRealName);
			cnslFileDTO.setFileSize(file.getSize());
			cnslFileDTO.setRegiId(loginMemberId);
			cnslFileDTO.setRegiIdx(loginMemberIdx);
			cnslFileDTO.setRegiIp(regiIp);
			cnslFileDTO.setRegiName(loginMemberName);

			consultingDAO.insertCnslFileByDto(cnslFileDTO);
			existingItemIds.add(fileName);
			}
		}
		
		
		for (CnslFileDTO cnslFileDTO : preFiles) {
			if(!existingItemIds.contains(cnslFileDTO.getItemId())) {
				cnslFileDTO.setCnslIdx(cnsl.getCnslIdx());
				cnslFileDTO.setRegiId(loginMemberId);
				cnslFileDTO.setRegiIdx(loginMemberIdx);
				cnslFileDTO.setRegiIp(regiIp);
				cnslFileDTO.setRegiName(loginMemberName);
				consultingDAO.insertCnslFileByDto(cnslFileDTO);
			}
		}

		if (cnsl.getCnslTeam() != null) {
			// 컨설팅 팀 저장
			CnslTeamDTO teamDTO = cnsl.getCnslTeam();
			teamDTO.setCnslIdx(cnsl.getCnslIdx());
			teamDTO.setRegiId(loginMemberId);
			teamDTO.setRegiIdx(loginMemberIdx);
			teamDTO.setRegiIp(regiIp);
			teamDTO.setRegiName(loginMemberName);
			consultingDAO.insertCnslTeamByDto(teamDTO);
		}
		preTeams.setCnslIdx(cnsl.getCnslIdx());
		preTeams.setRegiId(loginMemberId);
		preTeams.setRegiIdx(loginMemberIdx);
		preTeams.setRegiIp(regiIp);
		preTeams.setRegiName(loginMemberName);
		consultingDAO.insertCnslTeamMember(preTeams);
		consultingDAO.insertCnslConfmByDto(cnsl);	
		
		
	}

	@Override
	public int isTrainingComplecated(String hrd4uId) {
		return consultingDAO.isTrainingComplecated(hrd4uId);
	}

	@Override
	public List<CnslDiaryDTO> selectDiaryListByCnslIdx(String cnslIdx) {
		return consultingDAO.selectDiaryListByCnslIdx(cnslIdx);
	}

	@Override
	public List<Cnsl> selectCnslListByBplNoForViewAll(String bplNo) {
		return consultingDAO.selectCnslListByBplNoForViewAll(bplNo);
	}

	@Override
	public List<Cnsl> selectCnslListByMemberIdxForViewAll(String memberIdx) {
		return consultingDAO.selectCnslListByMemberIdxForViewAll(memberIdx);
	}

	@Override
	public List<Cnsl> selectCnslListBySpntMemberIdxForViewAll(
			String memberIdx) {
		return consultingDAO.selectCnslListBySpntMemberIdxForViewAll(memberIdx);
	}

	@Override
	public List<Cnsl> selectCnslListByDoctorMemberIdxForViewAll(
			String memberIdx) {
		return consultingDAO.selectCnslListByDoctorMemberIdxForViewAll(memberIdx);
	}

	@Override
	public List<Cnsl> selectCnslListAllForViewAll() {
		return consultingDAO.selectCnslListAllForViewAll();
	}

	@Override
	public List<HashMap<String, String>> selectNcsInfoDepth1() {
		return consultingDAO.selectNcsInfoDepth1();
	}

	@Override
	public List<HashMap<String, String>> selectNextNcsDepth(String json) {
		return consultingDAO.selectNextNcsDepth(json);
	}
	

	/**
	 * 기업정보 받아오기
	 */
	@Override
	public Map<String, Object> findBizData(Map<String, Object> map) {
		return consultingDAO.findBizData(map);
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
	public int applyInsert(String uploadModulePath, int fnIdx, String regiIp,
			ParamForm parameterMap, JSONObject settingInfo, JSONObject items,
			JSONArray itemOrder) throws Exception {
		if (JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder))
			return -1;

		Map<String, Object> param = new HashMap<String, Object>(); // mapper
																	// parameter
																	// 데이터
		List<DTForm> dataList = new ArrayList<DTForm>(); // 저장항목
		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column"); // key
																				// 컬럼
		// 1. key 얻기
		param.put("fnIdx", fnIdx);
		param.put("targetTable", "apply");
		int keyIdx = consultingDAO.getNextId(param);
		
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
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
																				// 사용자
																				// 정보
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

		param.put("status", "0");
		param.put("dataList", dataList);
		param.put("corpPicTelNo", parameterMap.getString("corpPicTelNo"));
		param.put("recomendTelno", parameterMap.getString("recomendTelno"));
		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = consultingDAO.applyInsert(param);
		return result > 0 ? keyIdx : result;
	}
	
	public int applyUpdate(Map<String, Object> param) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		param.put("lastModiIdx", loginVO.getMemberIdx());
		param.put("lastModiId", loginVO.getMemberId());
		param.put("lastModiName", loginVO.getMemberName());
		param.put("status", "1");
		return consultingDAO.applyUpdate(param);
	}
	
	/**
	 * 전체 목록 수
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public int getCnslApplyCnt(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return consultingDAO.getCnslApplyCnt(param);
	}

	/**
	 * 전체 목록
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getCnslApplyList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return consultingDAO.getCnslApplyList(param);
	}

	@Override
	public HashMap<String, String> selectSojtFormInfo(String bplNo) {
		return consultingDAO.selectSojtFormInfo(bplNo);
	}

	@Override
	public int selectCnslSaveCountByBplNoAndCnslType(String bplNo,
			String cnslType) {
		HashMap<String, String> param = new HashMap<>();
		param.put("bplNo", bplNo);
		param.put("cnslType", cnslType);
		 return consultingDAO.selectCnslSaveCountByBplNoAndCnslType(param);
	}

	@Override
	public List<Cnsl> selectChangeConfmCn(String cnslIdx) {
		return consultingDAO.selectChangeConfmCn(cnslIdx);
	}

	@Override
	public HashMap<String, String> selectDoctorById(String hrd4uId) {
		return consultingDAO.selectDoctorById(hrd4uId);
	}

}