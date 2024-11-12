package rbs.modules.authManage.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
import rbs.modules.authManage.mapper.AuthGroupManageFileMapper;
import rbs.modules.authManage.mapper.AuthGroupManageMapper;
import rbs.modules.authManage.mapper.AuthGroupManageMultiMapper;
import rbs.modules.authManage.service.AuthGroupManageService;


/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("authGroupManageService")
public class AuthGroupManageServiceImpl extends EgovAbstractServiceImpl implements AuthGroupManageService {

	@Resource(name="authGroupManageMapper")
	private AuthGroupManageMapper authGroupManageDAO;
	
	@Resource(name="authGroupManageFileMapper")
	private AuthGroupManageFileMapper authGroupManageFileDAO;
	
	@Resource(name="authGroupManageMultiMapper")
	private AuthGroupManageMultiMapper authGroupManageMultiDAO;
	
	/**
	 * 전체 목록 수
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalCount(Map<String, Object> param) {
    	return authGroupManageDAO.getTotalCount(param);
    }
    
    /**
	 * 전체 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getList(Map<String, Object> param) {
		return authGroupManageDAO.getList(param);
	}
	
	/**
	 * 권한 그룹 전체 목록(검색 이용을 위해서)
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getGroupList(Map<String, Object> param) {
		return authGroupManageDAO.getGroupList(param);
	}
	
	/**
	 * 전체 권한부여 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getAuthGrant(Map<String, Object> param) {
		return authGroupManageDAO.getAuthGrant(param);
	}
	
	/**
	 * 전체 메뉴 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getAuthList() {
		return authGroupManageDAO.getAuthList();
	}
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getView(Map<String, Object> param) {
		DataMap viewDAO = authGroupManageDAO.getView(param);
		return viewDAO;
	}

	/**
	 * 파일 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getFileView(Map<String, Object> param) {
		DataMap viewDAO = authGroupManageDAO.getFileView(param);
		return viewDAO;
	}

	/**
	 * multi파일 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getMultiFileView(Map<String, Object> param) {
		DataMap viewDAO = authGroupManageFileDAO.getFileView(param);
		return viewDAO;
	}
	
	/**
	 * 다운로드 수 수정
	 * @param keyIdx
	 * @return
	 * @throws Exception
	 */
	public int updateFileDown(int keyIdx, String fileColumnName) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		param.put("KEY_IDX", keyIdx);
    	param.put("searchList", searchList);
    	param.put("FILE_COLUMN", fileColumnName);
		return authGroupManageDAO.updateFileDown(param);
		
	}
	
	/**
	 * multi file 다운로드 수 수정
	 * @param keyIdx
	 * @return
	 * @throws Exception
	 */
	public int updateMultiFileDown(int keyIdx, int fleIdx, String itemId) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		searchList.add(new DTForm("FLE_IDX", fleIdx));
		searchList.add(new DTForm("ITEM_ID", itemId));
    	param.put("searchList", searchList);
		param.put("KEY_IDX", keyIdx);
		return authGroupManageFileDAO.updateFileDown(param);
		
	}

	/**
	 * 수정 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getModify(Map<String, Object> param) {
		DataMap viewDAO = authGroupManageDAO.getModify(param);
		return viewDAO;
	}
	
	/**
	 * 권한여부
	 * @param param
	 * @return
	 */
	public int getAuthCount(Map<String, Object> param) {
    	return authGroupManageDAO.getAuthCount(param);
    }

	/**
	 * 등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @param siteId 
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
		int keyIdx = authGroupManageDAO.getNextId(param);

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
		int result = authGroupManageDAO.insert(param);
		

		/*String preAuthList = StringUtil.getString(parameterMap.get("authList"));
		System.out.println("12312312132"+preAuthList);
		String[] authList = StringUtil.stringToArray(preAuthList, ",");
		System.out.println("stringToArray "+authList);
		System.out.println("getList "+authList[0]);
		System.out.println("getList "+authList[20]);
		*/
		if(result > 0) {
			
		// 3.2 multi data 저장
			
			String preAuthList = StringUtil.getString(parameterMap.get("authList"));
			
 			String[] authList = StringUtil.stringToArray(preAuthList, ",");
			if(authList != null) {
				int multiDataSize = authList.length;
				String[] preList = new String[9];
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> fileParam = new HashMap();
					
					int j = i%9;
					preList[j] = authList[i];
					if(j == 8) {
						fileParam.put("AUTH_IDX", keyIdx);
						fileParam.put("MENU_IDX", preList[0]);
						fileParam.put("VER_IDX", preList[1]);
						fileParam.put("SITE_ID", preList[2]);
						fileParam.put("M", preList[3]);
						fileParam.put("C", preList[4]);
						fileParam.put("R", preList[5]);
						fileParam.put("U", preList[6]);
						fileParam.put("D", preList[7]);
						fileParam.put("F", preList[8]);

						fileParam.put("REGI_IDX", loginMemberIdx);
						fileParam.put("REGI_ID", loginMemberId);
						fileParam.put("REGI_NAME", loginMemberName);
						fileParam.put("REGI_IP", regiIp);
						
						fileParam.put("LAST_MODI_IDX", loginMemberIdx);
						fileParam.put("LAST_MODI_ID", loginMemberId);
						fileParam.put("LAST_MODI_NAME", loginMemberName);
						fileParam.put("LAST_MODI_IP", regiIp);
						
						result = authGroupManageMultiDAO.insert(fileParam);
						
						
					}
					
				}
			}
			
			// 3.3 multi file 저장
  			List fileDataList = StringUtil.getList(dataMap.get("fileDataList"));
			if(fileDataList != null) {
				int fileDataSize = fileDataList.size();
				for(int i = 0 ; i < fileDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)fileDataList.get(i);
					fileParam.put("KEY_IDX", keyIdx);
					int fleIdx = authGroupManageFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = authGroupManageFileDAO.insert(fileParam);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int update(String uploadModulePath, String keyIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>();							// 저장항목
    	
    	String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");	// key 컬럼
		// 1. 검색조건 setting
		searchList.add(new DTForm(columnName, keyIdx));

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
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
		
    	dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
    	dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
    	dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
    	dataList.add(new DTForm("LAST_MODI_IP", regiIp));
    	
    	param.put("dataList", dataList);
    	param.put("searchList", searchList);

    	// 3. DB 저장
    	// 3.1 기본 정보 테이블
		int result = authGroupManageDAO.update(param);

		if(result > 0){
			// 3.2 multi data 저장
			// 3.2.1 multi data 삭제
			
			Map<String, Object> multiDelParam = new HashMap<String, Object>();
			multiDelParam.put("AUTH_IDX", keyIdx);
			authGroupManageMultiDAO.cdelete(multiDelParam);

			// 3.2.2 multi data 등록
			String preAuthList = StringUtil.getString(parameterMap.get("authList"));
			String[] authList = StringUtil.stringToArray(preAuthList, ",");
			if(authList != null) {
				int multiDataSize = authList.length;
				String[] preList = new String[9];
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> fileParam = new HashMap();
					
					int j = i%9;
					preList[j] = authList[i];
					if(j == 8) {
						fileParam.put("AUTH_IDX", keyIdx);
						fileParam.put("MENU_IDX", preList[0]);
						fileParam.put("VER_IDX", preList[1]);
						fileParam.put("SITE_ID", preList[2]);
						fileParam.put("M", preList[3]);
						fileParam.put("C", preList[4]);
						fileParam.put("R", preList[5]);
						fileParam.put("U", preList[6]);
						fileParam.put("D", preList[7]);
						fileParam.put("F", preList[8]);

						fileParam.put("REGI_IDX", loginMemberIdx);
						fileParam.put("REGI_ID", loginMemberId);
						fileParam.put("REGI_NAME", loginMemberName);
						fileParam.put("REGI_IP", regiIp);
						
						fileParam.put("LAST_MODI_IDX", loginMemberIdx);
						fileParam.put("LAST_MODI_ID", loginMemberId);
						fileParam.put("LAST_MODI_NAME", loginMemberName);
						fileParam.put("LAST_MODI_IP", regiIp);
						
						result = authGroupManageMultiDAO.insert(fileParam);
						
						
					}
					
				}
			}

		

		}
		return result;
	}

	/**
	 * 삭제 전체 목록 수
	 * @param param
	 * @return
	 */
    @Override
	public int getDeleteCount(Map<String, Object> param) {
    	return authGroupManageDAO.getDeleteCount(param);
    }

    /**
	 * 삭제 전체 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getDeleteList(Map<String, Object> param) {
		return authGroupManageDAO.getDeleteList(param);
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
		return authGroupManageDAO.delete(param);
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
		return authGroupManageDAO.restore(param);
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
		
		/*List<Object> deleteMultiFileList = null;
		List<Object> deleteFileList = null;
		// 2. 삭제할 파일 select
		// 2.1 삭제할  multi file 목록 select
		deleteMultiFileList = authGroupManageFileDAO.getList(param);
		// 2.2 삭제할 file(단일항목) select
		List deleteFileColumnList = ModuleUtil.getFileObjectList(items, itemOrder);
		if(deleteFileColumnList != null) {
			param.put("columnList", deleteFileColumnList);
			
			deleteFileList = authGroupManageDAO.getFileList(param);
		}*/
		
		// 3. delete
		int result = authGroupManageDAO.cdelete(param);
		/*if(result > 0) {
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
		}*/
		
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
					resultHashMap.put(itemId, authGroupManageFileDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A." + columnName, keyIdx));
		    	searchList.add(new DTForm("A.ITEM_ID", (String[])itemIdList.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
				resultHashMap = authGroupManageFileDAO.getMapList(param);
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
					resultHashMap.put(itemId, authGroupManageMultiDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A." + columnName, keyIdx));
		    	//searchList.add(new DTForm("A.ITEM_ID", (String[])itemIdList.toArray(new String[itemIdList.size()])));
				param.put(columnName, keyIdx);
				resultHashMap = authGroupManageMultiDAO.getMapList(param);
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
}