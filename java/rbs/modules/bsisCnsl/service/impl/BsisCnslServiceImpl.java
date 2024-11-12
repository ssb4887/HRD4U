package rbs.modules.bsisCnsl.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import rbs.egovframework.LoginVO;
import rbs.modules.bsisCnsl.mapper.BsisCnslFileMapper;
import rbs.modules.bsisCnsl.mapper.BsisCnslMapper;
import rbs.modules.bsisCnsl.mapper.BsisCnslMultiMapper;
import rbs.modules.bsisCnsl.service.BsisCnslService;

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

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("bsisCnslService")
public class BsisCnslServiceImpl extends EgovAbstractServiceImpl implements BsisCnslService {

	@Resource(name="bsisCnslMapper")
	private BsisCnslMapper bsisCnslDAO;
	
	@Resource(name="bsisCnslFileMapper")
	private BsisCnslFileMapper bsisCnslFileDAO;
	
	@Resource(name="bsisCnslMultiMapper")
	private BsisCnslMultiMapper bsisCnslMultiDAO;
	
	/**
	 * 전체 목록 수
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalCount(Map<String, Object> param) {
    	return bsisCnslDAO.getTotalCount(param);
    }
    
    /**
	 * 전체 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getList(Map<String, Object> param) {
		return bsisCnslDAO.getList(param);
	}
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getView(Map<String, Object> param) {
		DataMap viewDAO = bsisCnslDAO.getView(param);
		return viewDAO;
	}

	/**
	 * 파일 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getFileView(Map<String, Object> param) {
		DataMap viewDAO = bsisCnslDAO.getFileView(param);
		return viewDAO;
	}

	/**
	 * multi파일 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getMultiFileView(Map<String, Object> param) {
		DataMap viewDAO = bsisCnslFileDAO.getFileView(param);
		return viewDAO;
	}
	
	/**
	 * 다운로드 수 수정
	 * @param keyIdx
	 * @return
	 * @throws Exception
	 */
	public int updateFileDown(String keyIdx, String fileColumnName) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		param.put("IDX", keyIdx);
    	param.put("searchList", searchList);
    	param.put("FILE_COLUMN", fileColumnName);
		return bsisCnslDAO.updateFileDown(param);
		
	}
	
	/**
	 * multi file 다운로드 수 수정
	 * @param keyIdx
	 * @return
	 * @throws Exception
	 */
	public int updateMultiFileDown(String keyIdx, int fleIdx, String itemId) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		searchList.add(new DTForm("FLE_IDX", fleIdx));
		searchList.add(new DTForm("ITEM_ID", itemId));
    	param.put("searchList", searchList);
		param.put("IDX", keyIdx);
		return bsisCnslFileDAO.updateFileDown(param);
		
	}

	/**
	 * 수정 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getModify(Map<String, Object> param) {
		DataMap viewDAO = bsisCnslDAO.getModify(param);
		return viewDAO;
	}
	
	/**
	 * 권한여부
	 * @param param
	 * @return
	 */
	public int getAuthCount(Map<String, Object> param) {
    	return bsisCnslDAO.getAuthCount(param);
    }

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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int insert(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception  {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> dataList = new ArrayList<DTForm>();							// 저장항목

		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");	// key 컬럼
				
		// 1. key 얻기
		String keyIdx = bsisCnslDAO.getNextId(param);

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
		int result = bsisCnslDAO.insert(param);
		
		if(result > 0) {
			// 3.2 multi data 저장
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			if(multiDataList != null) {
				int multiDataSize = multiDataList.size();
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)multiDataList.get(i);
					fileParam.put("IDX", keyIdx);
					result = bsisCnslMultiDAO.insert(fileParam);
				}
			}
			
			// 3.3 multi file 저장
			List fileDataList = StringUtil.getList(dataMap.get("fileDataList"));
			if(fileDataList != null) {
				int fileDataSize = fileDataList.size();
				for(int i = 0 ; i < fileDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)fileDataList.get(i);
					fileParam.put("IDX", keyIdx);
					int fleIdx = bsisCnslFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = bsisCnslFileDAO.insert(fileParam);
				}
			}
		}
		
		return result;
	}

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
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Override
//	public int update(String uploadModulePath, String keyIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception {
//		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
//		
//		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
//		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
//		List<DTForm> dataList = new ArrayList<DTForm>();							// 저장항목
//    	
//    	String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");	// key 컬럼
//		// 1. 검색조건 setting
//		searchList.add(new DTForm(columnName, keyIdx));
//
//		// 2. 항목설정으로 저장항목 setting
//		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
//		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
//		if(dataMap == null || dataMap.size() == 0) return -1;
//		
//		// 2.1 저장항목
//		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
//		if(itemDataList != null) dataList.addAll(itemDataList);
//
//		// 2.2 등록자 정보
//		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
//		String loginMemberIdx = null;
//		String loginMemberId = null;
//		String loginMemberName = null;
//		if(loginVO != null) {
//			loginMemberIdx = loginVO.getMemberIdx();
//			loginMemberId = loginVO.getMemberId();
//			loginMemberName = loginVO.getMemberName();
//		}
//		
//    	dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
//    	dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
//    	dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
//    	dataList.add(new DTForm("LAST_MODI_IP", regiIp));
//    	
//    	param.put("dataList", dataList);
//    	param.put("searchList", searchList);
//
//    	// 3. DB 저장
//    	// 3.1 기본 정보 테이블
//		int result = bsisCnslDAO.update(param);
//
//		if(result > 0){
//			// 3.2 multi data 저장
//			// 3.2.1 multi data 삭제
//			int result1 = 0;
//			Map<String, Object> multiDelParam = new HashMap<String, Object>();
//			multiDelParam.put("IDX", keyIdx);
//			result1 = bsisCnslMultiDAO.cdelete(multiDelParam);
//
//			// 3.2.2 multi data 등록
//			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
//			if(multiDataList != null) {
//				int multiDataSize = multiDataList.size();
//				for(int i = 0 ; i < multiDataSize ; i ++) {
//					Map<String, Object> multiParam = (HashMap)multiDataList.get(i);
//					multiParam.put("IDX", keyIdx);
//					result = bsisCnslMultiDAO.insert(multiParam);
//				}
//			}
//
//			// 3.3 multi file 저장
//			// 3.3.1 multi file 신규 저장
//			List fileDataList = StringUtil.getList(dataMap.get("fileDataList"));
//			if(fileDataList != null) {				
//				// 3.3.1.2 DB 저장
//				int fileDataSize = fileDataList.size();
//				for(int i = 0 ; i < fileDataSize ; i ++) {
//					Map<String, Object> fileParam = (HashMap)fileDataList.get(i);
//					fileParam.put("IDX", keyIdx);
//					int fleIdx = bsisCnslFileDAO.getNextId(fileParam);
//					fileParam.put("FLE_IDX", fleIdx);
//					result = bsisCnslFileDAO.insert(fileParam);
//				}
//			}
//	
//			// 3.3.2 multi file 수정
//			List fileModifyDataList = StringUtil.getList(dataMap.get("fileModifyDataList"));
//			if(fileModifyDataList != null) {
//				int fileDataSize = fileModifyDataList.size();
//				for(int i = 0 ; i < fileDataSize ; i ++) {
//					Map<String, Object> fileParam = (HashMap)fileModifyDataList.get(i);
//					fileParam.put("IDX", keyIdx);
//					result = bsisCnslFileDAO.update(fileParam);
//				}
//			}
//			
//			// 3.3.3 multi file 삭제
//			List fileDeleteSearchList = StringUtil.getList(dataMap.get("fileDeleteSearchList"));
//			if(fileDeleteSearchList != null) {
//	
//				List<Object> deleteMultiFileList = new ArrayList<Object>();
//				int fileDataSize = fileDeleteSearchList.size();
//				for(int i = 0 ; i < fileDataSize ; i ++) {
//					Map<String, Object> fileParam = (HashMap)fileDeleteSearchList.get(i);
//					fileParam.put("IDX", keyIdx);
//					// 6.1 삭제목록 select
//					List<Object> deleteFileList2 = bsisCnslFileDAO.getList(fileParam);
//					if(deleteFileList2 != null) deleteMultiFileList.addAll(deleteFileList2);
//					// 6.2 DB 삭제
//					result = bsisCnslFileDAO.cdelete(fileParam);
//				}
//				
//				// 3.3.4 multi file 삭제
//				FileUtil.isKeyDelete(fileRealPath, deleteMultiFileList, "FILE_SAVED_NAME");
//			}
//			
//			// 4. file(단일항목) 삭제
//			List deleteFileList = StringUtil.getList(dataMap.get("deleteFileList"));
//			if(deleteFileList != null) {
//				FileUtil.isDelete(fileRealPath, deleteFileList);
//			}
//		}
//		return result;
//	}

	/**
	 * 삭제 전체 목록 수
	 * @param param
	 * @return
	 */
    @Override
	public int getDeleteCount(Map<String, Object> param) {
    	return bsisCnslDAO.getDeleteCount(param);
    }

    /**
	 * 삭제 전체 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getDeleteList(Map<String, Object> param) {
		return bsisCnslDAO.getDeleteList(param);
	}

	/**
	 * 삭제처리 : 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
	 * @param parameterMap
	 * @param deleteIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public int delete(ParamForm parameterMap, String[] deleteIdxs, String regiIp, JSONObject settingInfo) throws Exception {
		if(deleteIdxs == null) return 0;
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>();						// 저장항목

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
		
		// 3. DB 저장
		return bsisCnslDAO.delete(param);
	}

	/**
	 * 복원처리 : 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
	 * @param restoreIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public int restore(String[] restoreIdxs, String regiIp, JSONObject settingInfo) throws Exception {
		if(restoreIdxs == null) return 0;

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>();						// 저장항목

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
		
		// 3. DB 저장
		return bsisCnslDAO.restore(param);
	}

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
	@Override
	public int cdelete(String uploadModulePath, String[] deleteIdxs, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception {
		if(deleteIdxs == null) return 0;

		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		
    	// 1. 저장조건
		searchList.add(new DTForm(JSONObjectUtil.getString(settingInfo, "idx_column"), deleteIdxs));
		param.put("searchList", searchList);
		
		List<Object> deleteMultiFileList = null;
		List<Object> deleteFileList = null;
		// 2. 삭제할 파일 select
		// 2.1 삭제할  multi file 목록 select
		deleteMultiFileList = bsisCnslFileDAO.getList(param);
		// 2.2 삭제할 file(단일항목) select
		List deleteFileColumnList = ModuleUtil.getFileObjectList(items, itemOrder);
		if(deleteFileColumnList != null) {
			param.put("columnList", deleteFileColumnList);
			
			deleteFileList = bsisCnslDAO.getFileList(param);
		}
		
		// 3. delete
		int result = bsisCnslDAO.cdelete(param);
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
	 * @param keyIdx
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	public Map<String, List<Object>> getMultiFileHashMap(String keyIdx, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) {
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
					Map<String, Object> param = new HashMap<String, Object>();
					List<DTForm> searchList = new ArrayList<DTForm>();
			    	searchList.add(new DTForm("A." + columnName, keyIdx));
			    	searchList.add(new DTForm("A.ITEM_ID", itemId));
					param.put("searchList", searchList);
					resultHashMap.put(itemId, bsisCnslFileDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A." + columnName, keyIdx));
		    	searchList.add(new DTForm("A.ITEM_ID", (String[])itemIdList.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
				resultHashMap = bsisCnslFileDAO.getMapList(param);
			}
		}
		
		return resultHashMap;
	}
	
	/**
	 * mult data 전체 목록 : 항목ID에 대한 HashMap
	 * @param keyIdx
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	@Override
	public Map<String, List<Object>> getMultiHashMap(String keyIdx, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) {
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
					Map<String, Object> param = new HashMap<String, Object>();
					List<DTForm> searchList = new ArrayList<DTForm>();
			    	searchList.add(new DTForm("A." + columnName, keyIdx));
			    	searchList.add(new DTForm("A.ITEM_ID", itemId));
					param.put("searchList", searchList);
					resultHashMap.put(itemId, bsisCnslMultiDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A." + columnName, keyIdx));
		    	searchList.add(new DTForm("A.ITEM_ID", (String[])itemIdList.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
				resultHashMap = bsisCnslMultiDAO.getMapList(param);
			}
		}
		
		return resultHashMap;
	}

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
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> getFileUpload(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception  {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return null;

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return null;
		/*System.out.println("-----------itemOrder:" + itemOrder);
		System.out.println("-----------dataMap:" + dataMap);
		System.out.println("-----------file_origin_name:" + parameterMap.get("file_origin_name"));
		System.out.println("-----------file_saved_name:" + parameterMap.get("file_saved_name"));
		System.out.println("-----------file_size:" + parameterMap.get("file_size"));*/

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

	@Override
	public List<Object> getQustnrView(Map<String, Object> param) {
		return bsisCnslDAO.getQustnrView(param);
	}

	@Override
	public List<Object> getQustnrAnswer(Map<String, Object> param) {
		return bsisCnslDAO.getQustnrAnswer(param);
	}

	@Override
	public List<Object> getBscList(Map<String, Object> param) {
		return bsisCnslDAO.getBscList(param);
	}

	@Override
	public int setQustnrResult(Map<String, Object> param, String regiIp) {
		if(param.get("status").equals("1")) {
			Map<String, Object> issue = bsisCnslDAO.getIssue(param);
			param.put("issueNo", issue.get("ISSUE_NO"));
			param.put("issueDate", issue.get("ISSUE_DATE"));
		}
		
		// 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}

		param.put("REGI_IDX", loginMemberIdx);
		param.put("REGI_ID", loginMemberId);
		param.put("REGI_NAME", loginMemberName);
		param.put("REGI_IP", regiIp);

		param.put("LAST_MODI_IDX", loginMemberIdx);
		param.put("LAST_MODI_ID", loginMemberId);
		param.put("LAST_MODI_NAME", loginMemberName);
		param.put("LAST_MODI_IP", regiIp);
		
		if(param.containsKey("rsltIdx")) {
			return bsisCnslDAO.updateQustnrResult(param);
		} else {
			return bsisCnslDAO.insertQustnrResult(param);
		}
	}

	@Override
	public int updateQustnrAnswers(List<Map<String, Object>> param) {
		return bsisCnslDAO.updateQustnrAnswers(param);
	}

	@Override
	public int insertQustnrAnswers(List<Map<String, Object>> param) {
		return bsisCnslDAO.insertQustnrAnswers(param);
	}

	@Override
	public List<Map<String, Object>> getQustnrAnswerOnly(Map<String, Object> param) {
		return bsisCnslDAO.getQustnrAnswerOnly(param);
	}

	@Override
	public int getBscTotalCount(Map<String, Object> param) {
		return bsisCnslDAO.getBscTotalCount(param);
	}

	@Override
	public DataMap isQustnrExistsByBsc(Map<String, Object> param) {
		return bsisCnslDAO.isQustnrExistsByBsc(param);
	}
	
	@Override
	public int getMyTotalCount(Map<String, Object> param) {
		return bsisCnslDAO.getMyTotalCount(param);
	}

	@Override
	public List<Object> getMyList(Map<String, Object> param) {
		return bsisCnslDAO.getMyList(param);
	}

	@Override
	public int getBscInitTotalCount(Map<String, Object> param) {
		return bsisCnslDAO.getBscInitTotalCount(param);
	}

	@Override
	public List<Object> getBscInitList(Map<String, Object> param) {
		return bsisCnslDAO.getBscInitList(param);
	}

	@Override
	public int getRsltIdx(Map<String, Object> param) {
		return bsisCnslDAO.getRsltIdx(param);
	}

	@Override
	public HashMap<String, Object> getCompletedBsisCnslOne(String bplNo) {
		return bsisCnslDAO.getCompletedBsisCnslOne(bplNo);
	}

	@Override
	public List<HashMap<String, Object>> selectCompletedAgremBybplNo(String bplNo) {
		return bsisCnslDAO.selectCompletedAgremBybplNo(bplNo);
	}

	@Override
	public List<?> getListForAdmin(Map<String, Object> param) {
		return bsisCnslDAO.getListForAdmin(param);
	}

	@Override
	public Map<String, Object> getBsisCnslByIdx(Map<String, Object> param) {
		return bsisCnslDAO.getBsisCnslByIdx(param);
	}

	@Override
	public int updateFtfYn(Map<String, Object> param) {
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
		param.put("LAST_MODI_IDX", loginMemberIdx);
		param.put("LAST_MODI_ID", loginMemberId);
		param.put("LAST_MODI_NAME", loginMemberName);
		
		return bsisCnslDAO.updateFtfYn(param);
	}

}