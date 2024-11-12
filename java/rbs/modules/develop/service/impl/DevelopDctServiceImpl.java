package rbs.modules.develop.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woowonsoft.egovframework.form.DTForm;
import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
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
import rbs.modules.develop.mapper.DevelopDctFileMapper;
import rbs.modules.develop.mapper.DevelopDctMapper;
import rbs.modules.develop.mapper.DevelopDctMultiMapper;
import rbs.modules.develop.mapper.DevelopMultiMapper;
import rbs.modules.develop.service.DevelopDctService;

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("developDctService")
public class DevelopDctServiceImpl extends EgovAbstractServiceImpl implements DevelopDctService {

	@Resource(name="developDctMapper")
	private DevelopDctMapper developDctDAO;
	
	@Resource(name="developDctFileMapper")
	private DevelopDctFileMapper developDctFileDAO;
	
	@Resource(name="developDctMultiMapper")
	private DevelopDctMultiMapper developDctMultiDAO;
	
	@Resource(name="developMultiMapper")
	private DevelopMultiMapper developMultiDAO;
	
	@Resource(name="chgLogMapper")
	private ChgLogMapper chgLogMapper;
	
	Map<String, Object> param;
	List<DTForm> dataList;
	List<DTForm> searchList;
	
	/**
	 * 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalCount(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
    	return developDctDAO.getTotalCount(param);
    }
    
    /**
	 * 전체 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getList(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
		return developDctDAO.getList(param);
	}
	
	/**
	 * 표준개발 신청 전체 목록 수
	 * @param param
	 * @return
	 */
	@Override
	public int getDevTotalCount(Map<String, Object> param) {
		return developDctDAO.getDevTotalCount(param);
	}

	/**
	 * 표준개발 신청 전체 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getDevList(Map<String, Object> param) {
		return developDctDAO.getDevList(param);
	}
	
	/**
	 * 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalSupportCount(Map<String, Object> param) {
    	return developDctDAO.getTotalSupportCount(param);
    }
    
    /**
	 * 전체 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getSupportList(Map<String, Object> param) {
		return developDctDAO.getSupportList(param);
	}
	
	/**
	 * 미처리 상태인 목록 가져오기(표준, 맞춤 전부다)
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getNoHandlingList(Map<String, Object> param) {
		return developDctDAO.getNoHandlingList(param);
	}
	
	/**
	 * 훈련과정 종합관리 집계 목록(표준 + 맞춤)
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getSummary(Map<String, Object> param) {
		return developDctDAO.getSummary(param);
	}
	
	/**
	 * 주치의 지원요청 내용 가져오기
	 * @param targetTable
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getHelpInfo(String targetTable, Map<String, Object> param) {
		param.put("targetTable", targetTable);
		DataMap viewDAO = developDctDAO.getHelpInfo(param);
		return viewDAO;
	}
	
	/**
	 * 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getView(String targetTable, Map<String, Object> param) {
		param.put("targetTable", targetTable);
		DataMap viewDAO = developDctDAO.getView(param);
		return viewDAO;
	}

	/**
	 * 파일 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getFileView(String targetTable, Map<String, Object> param) {
		param.put("targetTable", targetTable);
		DataMap viewDAO = developDctDAO.getFileView(param);
		return viewDAO;
	}

	/**
	 * multi파일 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getMultiFileView(String targetTable, Map<String, Object> param) {
		param.put("targetTable", targetTable);
		DataMap viewDAO = developDctFileDAO.getFileView(param);
		return viewDAO;
	}
	
	/**
	 * 다운로드 수 수정
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateFileDown(String targetTable, int keyIdx, String fileColumnName) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
    	param.put("targetTable", targetTable);
		param.put("KEY_IDX", keyIdx);
    	param.put("searchList", searchList);
    	param.put("FILE_COLUMN", fileColumnName);
		return developDctDAO.updateFileDown(param);
		
	}
	
	/**
	 * multi file 다운로드 수 수정
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateMultiFileDown(String targetTable, int keyIdx, int fleIdx, String itemId, String targetTableIdx) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		searchList.add(new DTForm("FLE_IDX", fleIdx));
		searchList.add(new DTForm("ITEM_ID", itemId));
    	param.put("searchList", searchList);
    	param.put("targetTable", targetTable);
    	param.put("targetTableIdx", targetTableIdx);
		param.put("KEY_IDX", keyIdx);
		return developDctFileDAO.updateFileDown(param);
		
	}

	/**
	 * 수정 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getModify(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
		DataMap viewDAO = developDctDAO.getModify(param);
		return viewDAO;
	}
	
	/**
	 * 권한여부
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getAuthCount(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
    	return developDctDAO.getAuthCount(param);
    }

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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int insert(String uploadModulePath, int fnIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception  {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 저장항목

		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");	// key 컬럼
				
		// 1. key 얻기
		param.put("fnIdx", fnIdx);
		int keyIdx = developDctDAO.getNextId(param);

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		
		dataList.add(new DTForm(columnName, keyIdx));

		// 2.2 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if(loginVO != null) {
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
    	
		param.put("dataList", dataList);

		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = developDctDAO.insert(param);
		
		if(result > 0) {
			// 3.2 multi data 저장
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			if(multiDataList != null) {
				int multiDataSize = multiDataList.size();
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)multiDataList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", keyIdx);
					result = developDctMultiDAO.insert(fileParam);
				}
			}
			
			// 3.3 multi file 저장
			List fileDataList = StringUtil.getList(dataMap.get("fileDataList"));
			if(fileDataList != null) {
				int fileDataSize = fileDataList.size();
				for(int i = 0 ; i < fileDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)fileDataList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", keyIdx);
					int fleIdx = developDctFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = developDctFileDAO.insert(fileParam);
				}
			}
		}
		
		return result > 0 ? keyIdx : result;
	}

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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int update(String uploadModulePath, int fnIdx, int keyIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		param = new HashMap<String, Object>();					// mapper parameter 데이터
		searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
    	
    	String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");	// key 컬럼
		// 1. 검색조건 setting
		searchList.add(new DTForm(columnName, keyIdx));

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		String acnutNo = (String) parameterMap.get("acnutNo");	
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);

		// 2.2 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if(loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		
		dataList.add(new DTForm("ACNUTNO", "PKG_DGUARD.FN_ENC_ACNUTNO('" + acnutNo + "')", 1));
    	dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
    	dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
    	dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
    	dataList.add(new DTForm("LAST_MODI_IP", regiIp));
    	
    	param.put("dataList", dataList);
    	param.put("searchList", searchList);
    	param.put("fnIdx", fnIdx);

    	// 3. DB 저장
    	// 3.1 기본 정보 테이블
		int result = developDctDAO.update(param);

		if(result > 0){
			// 3.2 multi data 저장
			// 3.2.1 multi data 삭제
			int result1 = 0;
			Map<String, Object> multiDelParam = new HashMap<String, Object>();
			multiDelParam.put("fnIdx", fnIdx);
			multiDelParam.put("KEY_IDX", keyIdx);
			result1 = developDctMultiDAO.cdelete(multiDelParam);

			// 3.2.2 multi data 등록
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			if(multiDataList != null) {
				int multiDataSize = multiDataList.size();
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> multiParam = (HashMap)multiDataList.get(i);
					multiParam.put("fnIdx", fnIdx);
					multiParam.put("KEY_IDX", keyIdx);
					result = developDctMultiDAO.insert(multiParam);
				}
			}

			// 3.3 multi file 저장
			// 3.3.1 multi file 신규 저장
			List fileDataList = StringUtil.getList(dataMap.get("fileDataList"));
			if(fileDataList != null) {
	
				// 3.3.1.1 key 얻기
				/*Map<String, Object> fileParam1 = new HashMap<String, Object>();
				fileParam1.put("KEY_IDX", keyIdx);
				fileParam1.put("fnIdx", fnIdx);
				int fleIdx = developDctFileDAO.getNextId(fileParam1);*/
				
				// 3.3.1.2 DB 저장
				int fileDataSize = fileDataList.size();
				for(int i = 0 ; i < fileDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)fileDataList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", keyIdx);
					int fleIdx = developDctFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = developDctFileDAO.insert(fileParam);
				}
			}
	
			// 3.3.2 multi file 수정
			List fileModifyDataList = StringUtil.getList(dataMap.get("fileModifyDataList"));
			if(fileModifyDataList != null) {
				int fileDataSize = fileModifyDataList.size();
				for(int i = 0 ; i < fileDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)fileModifyDataList.get(i);
					fileParam.put("KEY_IDX", keyIdx);
					fileParam.put("fnIdx", fnIdx);
					result = developDctFileDAO.update(fileParam);
				}
			}
			
			// 3.3.3 multi file 삭제
			List fileDeleteSearchList = StringUtil.getList(dataMap.get("fileDeleteSearchList"));
			if(fileDeleteSearchList != null) {
	
				List<Object> deleteMultiFileList = new ArrayList<Object>();
				int fileDataSize = fileDeleteSearchList.size();
				for(int i = 0 ; i < fileDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)fileDeleteSearchList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", keyIdx);
					// 6.1 삭제목록 select
					List<Object> deleteFileList2 = developDctFileDAO.getList(fileParam);
					if(deleteFileList2 != null) deleteMultiFileList.addAll(deleteFileList2);
					// 6.2 DB 삭제
					result = developDctFileDAO.cdelete(fileParam);
				}
				
				// 3.3.4 multi file 삭제
				FileUtil.isKeyDelete(fileRealPath, deleteMultiFileList, "FILE_SAVED_NAME");
			}
			
			// 4. file(단일항목) 삭제
			List deleteFileList = StringUtil.getList(dataMap.get("deleteFileList"));
			if(deleteFileList != null) {
				FileUtil.isDelete(fileRealPath, deleteFileList);
			}
		}
		return result > 0 ? keyIdx : result;
	}

	/**
	 * 삭제 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getDeleteCount(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
    	return developDctDAO.getDeleteCount(param);
    }

    /**
	 * 삭제 전체 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getDeleteList(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
		return developDctDAO.getDeleteList(param);
	}

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
	@Override
	public int delete(int fnIdx, ParamForm parameterMap, int[] deleteIdxs, String regiIp, JSONObject settingInfo) throws Exception {
		if(deleteIdxs == null) return 0;
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		param = new HashMap<String, Object>();				// mapper parameter 데이터
		searchList = new ArrayList<DTForm>();						// 검색조건
		dataList = new ArrayList<DTForm>();						// 저장항목

		// 1. 저장조건
    	String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");
    	searchList.add(new DTForm(columnName, deleteIdxs));
    	
		// 2. 저장 항목
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if(loginVO != null) {
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
		return developDctDAO.delete(param);
	}

	/**
	 * 복원처리 : 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
	 * @param fnIdx
	 * @param restoreIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public int restore(int fnIdx, int[] restoreIdxs, String regiIp, JSONObject settingInfo) throws Exception {
		if(restoreIdxs == null) return 0;

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		param = new HashMap<String, Object>();				// mapper parameter 데이터
		searchList = new ArrayList<DTForm>();						// 검색조건
		dataList = new ArrayList<DTForm>();						// 저장항목

		// 1. 저장조건
		searchList.add(new DTForm(JSONObjectUtil.getString(settingInfo, "idx_column"), restoreIdxs));

		// 2. 저장 항목
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if(loginVO != null) {
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
		return developDctDAO.restore(param);
	}

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
	@Override
	public int cdelete(String uploadModulePath, int fnIdx, int[] deleteIdxs, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception {
		if(deleteIdxs == null) return 0;

		param = new HashMap<String, Object>();				// mapper parameter 데이터
		searchList = new ArrayList<DTForm>();						// 검색조건
		
    	// 1. 저장조건
		searchList.add(new DTForm(JSONObjectUtil.getString(settingInfo, "idx_column"), deleteIdxs));
		param.put("searchList", searchList);
    	param.put("fnIdx", fnIdx);
		
		List<Object> deleteMultiFileList = null;
		List<Object> deleteFileList = null;
		// 2. 삭제할 파일 select
		// 2.1 삭제할  multi file 목록 select
		deleteMultiFileList = developDctFileDAO.getList(param);
		// 2.2 삭제할 file(단일항목) select
		List deleteFileColumnList = ModuleUtil.getFileObjectList(items, itemOrder);
		if(deleteFileColumnList != null) {
			param.put("columnList", deleteFileColumnList);
			
			deleteFileList = developDctDAO.getFileList(param);
		}
		
		// 3. delete
		int result = developDctDAO.cdelete(param);
		if(result > 0) {
			// 4. 파일 삭제
			// 4.1 multi file 삭제
			String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
			if(deleteMultiFileList != null) {
				FileUtil.isKeyDelete(fileRealPath, deleteMultiFileList, "FILE_SAVED_NAME");
			}
			
			// 4.2 file(단일항목) 삭제
			if(deleteFileList != null) {
				FileUtil.isKeyDelete(fileRealPath, deleteFileList);
			}
		}
		
		return result;
	}
	
	/**
	 * mult file 전체 목록 : 항목ID에 대한 HashMap
	 * @param fnIdx
	 * @param keyIdx
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	public  Map<String, List<Object>> getMultiFileHashMap(int fnIdx, int keyIdx, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) {
		Map<String, List<Object>> resultHashMap = new HashMap<String, List<Object>>();
		if(!JSONObjectUtil.isEmpty(itemOrder) && !JSONObjectUtil.isEmpty(items)) {
			List<String> itemIdList = new ArrayList<String>();
	    	String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");
			int searchOrderSize = itemOrder.size();
			for(int i = 0 ; i < searchOrderSize ; i ++) {
				String itemId = JSONObjectUtil.getString(itemOrder, i);
				JSONObject item = JSONObjectUtil.getJSONObject(items, itemId);
				int formatType = JSONObjectUtil.getInt(item, "format_type");
				int objectType = JSONObjectUtil.getInt(item, "object_type");
				if(formatType == 0 && objectType == 9) {
					// mult file
					itemIdList.add(itemId);
					/*
					param = new HashMap<String, Object>();
					searchList = new ArrayList<DTForm>();
			    	searchList.add(new DTForm("A." + columnName, keyIdx));
			    	searchList.add(new DTForm("A.ITEM_ID", itemId));
					param.put("searchList", searchList);
			    	param.put("fnIdx", fnIdx);
					resultHashMap.put(itemId, developDctFileDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				param = new HashMap<String, Object>();
				searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A." + columnName, keyIdx));
		    	searchList.add(new DTForm("A.ITEM_ID", (String[])itemIdList.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
		    	param.put("fnIdx", fnIdx);
				resultHashMap = developDctFileDAO.getMapList(param);
			}
		}
		
		return resultHashMap;
	}
	
	/**
	 * mult data 전체 목록 : 항목ID에 대한 HashMap
	 * @param fnIdx
	 * @param keyIdx
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	@Override
	public Map<String, List<Object>> getMultiHashMap(int fnIdx, int keyIdx, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) {
		Map<String, List<Object>> resultHashMap = new HashMap<String, List<Object>>();
		if(!JSONObjectUtil.isEmpty(itemOrder) && !JSONObjectUtil.isEmpty(items)) {
			List<String> itemIdList = new ArrayList<String>();
	    	String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");
			int searchOrderSize = itemOrder.size();
			for(int i = 0 ; i < searchOrderSize ; i ++) {
				String itemId = JSONObjectUtil.getString(itemOrder, i);
				JSONObject item = JSONObjectUtil.getJSONObject(items, itemId);
				int formatType = JSONObjectUtil.getInt(item, "format_type");
				int objectType = JSONObjectUtil.getInt(item, "object_type");
				if(formatType == 0 && (objectType == 3 || objectType == 4 || objectType == 11)) {
					itemIdList.add(itemId);
					/*
					param = new HashMap<String, Object>();
					searchList = new ArrayList<DTForm>();
			    	searchList.add(new DTForm("A." + columnName, keyIdx));
			    	searchList.add(new DTForm("A.ITEM_ID", itemId));
					param.put("searchList", searchList);
			    	param.put("fnIdx", fnIdx);
					resultHashMap.put(itemId, developDctMultiDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				param = new HashMap<String, Object>();
				searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A." + columnName, keyIdx));
		    	searchList.add(new DTForm("A.ITEM_ID", (String[])itemIdList.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
		    	param.put("fnIdx", fnIdx);
				resultHashMap = developDctMultiDAO.getMapList(param);
			}
		}
		
		return resultHashMap;
	}

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
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> getFileUpload(String uploadModulePath, int fnIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception  {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return null;

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return null;

		List originList = StringUtil.getList(parameterMap.get("file_origin_name"));
		Map<String, Object> fileInfo = null;
		if(originList != null && !originList.isEmpty()) {
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
	 * 등록자 정보 저장
	 * @param dataList
	 * @param loginVO
	 * @param regiIp
	 * @return dataList
	 */
	private List<DTForm> addRegiInfo(List<DTForm> dataList, LoginVO loginVO, String regiIp) {
		// 2.2 등록자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if(loginVO != null) {
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
	 * @param dataList
	 * @param loginVO
	 * @param regiIp
	 * @return dataList
	 */
	private List<DTForm> addLastModiInfo(List<DTForm> dataList, LoginVO loginVO, String regiIp) {
		// 2.2 등록자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if(loginVO != null) {
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
	 * HRD_DEVELOP_*** 승인상태 및 주치의 의견 업데이트(반려처리할 때 사용)
	 * @param targetTable
	 * @param targetColumn
	 * @param confmStatus
	 * @param reqIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateStatusAndOpinion(String targetTable, String targetTableIdx, String confmStatus, int keyIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		param = new HashMap<String, Object>();					// mapper parameter 데이터
		searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		String doctorOpinion = (String) parameterMap.get("doctorOpinion");
		
		// update 조건
		searchList.add(new DTForm(targetTableIdx, keyIdx));
		
		// 이력 남기기
		chgLogMapper.writeDataLog(targetTable, "U", searchList);

		// dataList에 LAST_MODI 정보 넣는 함수
		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		
		// update할 데이터 항목
		dataList.add(new DTForm("DOCTOR_OPINION", doctorOpinion));
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		
		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("targetTable", targetTable);
		
		int updateReq = developDctDAO.update(param);
		
		return updateReq;
	}

	/**
	 * HRD_DEVELOP_*** 승인상태 업데이트(접수, 1차승인, 최종승인할 때 사용)
	 * @param targetTable
	 * @param targetColumn
	 * @param confmStatus
	 * @param cliIdx
	 * @param reqIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateStatus(String targetTable, String targetTableIdx, String confmStatus, int keyIdx, String regiIp) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		param = new HashMap<String, Object>();					// mapper parameter 데이터
		searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		// update 조건
		searchList.add(new DTForm(targetTableIdx, keyIdx));
		
		// 이력 남기기
		chgLogMapper.writeDataLog(targetTable, "U", searchList);

		// dataList에 LAST_MODI 정보 넣는 함수
		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		
		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("targetTable", targetTable);
		
		int updateReq = developDctDAO.update(param);
		
		return updateReq;
	}

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
	@Override
	public int insertConfm(String targetTable, String targetTableIdx, String confmStatus, int keyIdx, String regiIp) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		
		param = new HashMap<String, Object>();				// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();						// 저장항목

		param.put("targetTable", targetTable);
		param.put("targetTableIdx", targetTableIdx);
		
		// 승인 식별색인 가져오기
		int confmIdx = developDctDAO.getNextId(param);
    	
    	dataList.add(new DTForm("DEVLOP_IDX", keyIdx));
    	dataList.add(new DTForm("CONFM_IDX", confmIdx));
    	dataList.add(new DTForm("CONFM_STATUS", confmStatus));

    	// 등록자 정보 setting
    	dataList = addRegiInfo(dataList, loginVO, regiIp);
    	
    	param.put("dataList", dataList);
    	
    	int confmResult = developDctDAO.insertConfm(param);
		
		return confmResult;
	}

	/**
	 * HRD_CNSL_COST 승인상태 및 주치의 의견 업데이트(반려처리할 때 사용)
	 * @param confmStatus
	 * @param reqIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateStatusAndOpinionCost(String confmStatus, int keyIdx, int cnslIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		param = new HashMap<String, Object>();					// mapper parameter 데이터
		searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		String doctorOpinion = (String) parameterMap.get("doctorOpinion");
		
		// update 조건
		searchList.add(new DTForm("CNSL_IDX", cnslIdx));
		searchList.add(new DTForm("CT_IDX", keyIdx));
		
		// 이력 남기기
		chgLogMapper.writeDataLog("HRD_CNSL_COST", "U", searchList);

		// dataList에 LAST_MODI 정보 넣는 함수
		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		
		// update할 데이터 항목
		dataList.add(new DTForm("DOCTOR_OPINION", doctorOpinion));
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		
		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("targetTable", "HRD_CNSL_COST");
		
		int updateReq = developDctDAO.update(param);
		
		return updateReq;
	}

	/**
	 * HRD_CNSL_COST 승인상태 업데이트(접수, 1차승인, 최종승인할 때 사용)
	 * @param confmStatus
	 * @param cliIdx
	 * @param reqIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateStatusCost(String confmStatus, int keyIdx, int cnslIdx, String regiIp) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		param = new HashMap<String, Object>();					// mapper parameter 데이터
		searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		// update 조건
		searchList.add(new DTForm("CNSL_IDX", cnslIdx));
		searchList.add(new DTForm("CT_IDX", keyIdx));
		
		// 이력 남기기
		chgLogMapper.writeDataLog("HRD_CNSL_COST", "U", searchList);

		// dataList에 LAST_MODI 정보 넣는 함수
		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		
		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("targetTable", "HRD_CNSL_COST");
		
		int updateCost = developDctDAO.update(param);
		
		return updateCost;
	}

	/**
	 * HRD_CNSL_COST_CONFM 새로운 승인상태 등록(모든 메뉴에서 승인상태를 업데이트할 때 동일하게 사용)
	 * @param confmStatus
	 * @param cliIdx
	 * @param keyIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insertConfmCost(String confmStatus, int keyIdx, int cnslIdx, String regiIp) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		
		param = new HashMap<String, Object>();				// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();						// 저장항목

		param.put("targetTable", "HRD_CNSL_COST_CONFM");
		param.put("targetTableIdx", "CONFM_IDX");
		
		// 승인 식별색인 가져오기
		int confmIdx = developDctDAO.getNextId(param);
    	
		dataList.add(new DTForm("CNSL_IDX", cnslIdx));
    	dataList.add(new DTForm("CT_IDX", keyIdx));
    	dataList.add(new DTForm("CONFM_IDX", confmIdx));
    	dataList.add(new DTForm("CONFM_STATUS", confmStatus));

    	// 등록자 정보 setting
    	dataList = addRegiInfo(dataList, loginVO, regiIp);
    	
    	param.put("dataList", dataList);
    	
    	int confmResult = developDctDAO.insertConfm(param);
		
		return confmResult;
	}

	@Override
	public int update(String targetTable, String targetColumn, String uploadModulePath, int keyIdx, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		// 3.1 항목설정
		String submitType = "modify";		// 항목 order명
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();											// 저장항목
    	
		int cnslIdx = StringUtil.getInt((String) parameterMap.get("cnslIdx"));
		
		// 1. 검색조건 setting
		searchList.add(new DTForm(targetColumn, keyIdx));
		searchList.add(new DTForm("CNSL_IDX", cnslIdx));
		
		// 이력 남기기
		chgLogMapper.writeDataLog(targetTable, "U", searchList);
		
		// LAST_MODI 항목 setting
		dataList = addLastModiInfo(dataList, loginVO, regiIp);

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);

    	param.put("dataList", dataList);
    	param.put("searchList", searchList);
    	param.put("targetTable", targetTable);

    	// 3. DB 저장
    	// 3.1 기본 정보 테이블
		int costUpdate = developDctDAO.update(param);
		
		if(costUpdate > 0) {
			deleteAndInsertCnslCostSub("HRD_CNSL_COST_SUB", keyIdx, regiIp, parameterMap);
		}
		
		return costUpdate;
	}

	/**
	 * [훈련과정맞춤개발] COST_SUB에 인서트
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndInsertCnslCostSub(String targetTable, int keyIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		// HRD담당자 정보는 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		searchList = new ArrayList<DTForm>();							// 삭제조건
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("CT_IDX", keyIdx));
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
		
		chgLogMapper.writeDataLog("HRD_CNSL_COST_SUB","D",searchList);
		// 기존 데이터 삭제하기
		developMultiDAO.cdelete(delParam);
		
		int insertSub = 0;
		
		param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 데이터 항목
		param.put("targetTable", targetTable);		
		int teamSubLength = StringUtil.getInt((String) parameterMap.get("teamSubLength"));
		int cnslIdx = StringUtil.getInt((String) parameterMap.get("cnslIdx"));
		
		for(int i = 1; i <= teamSubLength; i++) {
						
				String teamNm = (String) parameterMap.get("teamNm" + i);
				String teamIdx = (String) parameterMap.get("teamIdx" + i);				
				String teamCost = (String) parameterMap.get("teamCost" + i);				
				String trOprtnDe = (String) parameterMap.get("trOprtnDe" + i);				
			
				if(teamIdx != null) {
					// REGI, LAST_MODI 항목 setting
					dataList = addRegiInfo(dataList, loginVO, regiIp);
					
					dataList.add(new DTForm("TEAM_ORDER_IDX", i));
					dataList.add(new DTForm("CT_IDX", keyIdx));
					dataList.add(new DTForm("CNSL_IDX", cnslIdx));
					dataList.add(new DTForm("TEAM_MBER_NAME", teamNm));
					dataList.add(new DTForm("TEAM_IDX", teamIdx));
					dataList.add(new DTForm("SPT_PAY", teamCost));
					dataList.add(new DTForm("COMPUT_DTLS", trOprtnDe));
					
					param.put("dataList", dataList);
					
					insertSub += developMultiDAO.insert(param);
					
					dataList.clear();
					
				}
		}
		
		return insertSub;
	}
}