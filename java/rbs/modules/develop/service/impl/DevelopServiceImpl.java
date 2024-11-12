package rbs.modules.develop.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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
import rbs.egovframework.common.ChgLogMapper;
import rbs.modules.develop.mapper.DevelopDctMapper;
import rbs.modules.develop.mapper.DevelopFileMapper;
import rbs.modules.develop.mapper.DevelopMapper;
import rbs.modules.develop.mapper.DevelopMultiMapper;
import rbs.modules.develop.service.DevelopService;

/**
 * 훈련과정 맞춤개발 모듈에 관한 구현클래스를 정의한다.
 * @author LDG, KJM
 *
 */
@Service("developService")
public class DevelopServiceImpl extends EgovAbstractServiceImpl implements DevelopService {

	@Resource(name="developMapper")
	private DevelopMapper developDAO;
	
	@Resource(name="developDctMapper")
	private DevelopDctMapper developDctDAO;
	
	@Resource(name="developFileMapper")
	private DevelopFileMapper developFileDAO;
	
	@Resource(name="developMultiMapper")
	private DevelopMultiMapper developMultiDAO;
	
	@Resource(name="chgLogMapper")
	private ChgLogMapper chgLogMapper;	
	
	List<DTForm> dataList;
	
	Map<String, Object> param;
	
	List<DTForm> searchList;
//공통코드 시작
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
	 * 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalCount(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
    	return developDAO.getTotalCount(param);
    }
    
    /**
	 * 전체 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getList(String targetTable, Map<String, Object> param) {
		param.put("targetTable", targetTable);	
		return developDAO.getList(param);
	}
	
	/**
	 * 표준개발 신청 전체 목록 수
	 * @param param
	 * @return
	 */
	@Override
	public int getDevTotalCount(Map<String, Object> param) {
		return developDAO.getDevTotalCount(param);
	}

	/**
	 * 표준개발 신청 전체 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getDevList(Map<String, Object> param) {
		return developDAO.getDevList(param);
	}
	
	/**
	 * 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap  getView(String targetTable, Map<String, Object> param) {
		param.put("targetTable", targetTable);	
		DataMap viewDAO = developDAO.getView(param);
		return viewDAO;
	}

	/**
	 * 파일 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getFileView(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
		DataMap viewDAO = developDAO.getFileView(param);
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
		DataMap viewDAO = developFileDAO.getFileView(param);
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
    	param.put("targetTableIdxColumnName", "CT_IDX");
    	param.put("targetTableIdxValue", keyIdx);
		return developDAO.updateFileDown(param);
		
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
    	param.put("targetTableIdxColumnName", "CT_IDX");
    	param.put("targetTableIdxValue", keyIdx);
		param.put("KEY_IDX", keyIdx);
		return developFileDAO.updateFileDown(param);
		
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
		DataMap viewDAO = developDAO.getModify(param);
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
    	return developDAO.getAuthCount(param);
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
		int keyIdx = developDAO.getNextId(param);

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
		int result = developDAO.insert(param);
		
		if(result > 0) {
			// 3.2 multi data 저장
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			if(multiDataList != null) {
				int multiDataSize = multiDataList.size();
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)multiDataList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", keyIdx);
					result = developMultiDAO.insert(fileParam);
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
					int fleIdx = developFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = developFileDAO.insert(fileParam);
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
    	param.put("fnIdx", fnIdx);

    	// 3. DB 저장
    	// 3.1 기본 정보 테이블
		int result = developDAO.update(param);

		if(result > 0){
			// 3.2 multi data 저장
			// 3.2.1 multi data 삭제
			Map<String, Object> multiDelParam = new HashMap<String, Object>();
			multiDelParam.put("fnIdx", fnIdx);
			multiDelParam.put("KEY_IDX", keyIdx);
			developMultiDAO.cdelete(multiDelParam);

			// 3.2.2 multi data 등록
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			if(multiDataList != null) {
				int multiDataSize = multiDataList.size();
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> multiParam = (HashMap)multiDataList.get(i);
					multiParam.put("fnIdx", fnIdx);
					multiParam.put("KEY_IDX", keyIdx);
					result = developMultiDAO.insert(multiParam);
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
				int fleIdx = developFileDAO.getNextId(fileParam1);*/
				
				// 3.3.1.2 DB 저장
				int fileDataSize = fileDataList.size();
				for(int i = 0 ; i < fileDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)fileDataList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", keyIdx);
					int fleIdx = developFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = developFileDAO.insert(fileParam);
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
					result = developFileDAO.update(fileParam);
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
					List<Object> deleteFileList2 = developFileDAO.getList(fileParam);
					if(deleteFileList2 != null) deleteMultiFileList.addAll(deleteFileList2);
					// 6.2 DB 삭제
					result = developFileDAO.cdelete(fileParam);
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
    	return developDAO.getDeleteCount(param);
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
		return developDAO.getDeleteList(param);
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
		return developDAO.delete(param);
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
		return developDAO.restore(param);
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
		deleteMultiFileList = developFileDAO.getList(param);
		// 2.2 삭제할 file(단일항목) select
		List deleteFileColumnList = ModuleUtil.getFileObjectList(items, itemOrder);
		if(deleteFileColumnList != null) {
			param.put("columnList", deleteFileColumnList);
			
			deleteFileList = developDAO.getFileList(param);
		}
		
		// 3. delete
		int result = developDAO.cdelete(param);
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
	 * @param targetTable
	 * @param targetTableIdxColumnName
	 * @param targetTableIdxValue
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	public  Map<String, List<Object>> getMultiFileHashMap(String targetTable, String targetTableIdxColumnName, int targetTableIdxValue) {
		Map<String, List<Object>> resultHashMap = new HashMap<String, List<Object>>();			
		param = new HashMap<String, Object>();
		searchList = new ArrayList<DTForm>();		    	
    	searchList.add(new DTForm("A.ITEM_ID", "file"));
		param.put("searchList", searchList);
    	param.put("targetTable", targetTable);
    	param.put("targetTableIdxColumnName", targetTableIdxColumnName);
    	param.put("targetTableIdxValue", targetTableIdxValue);
		resultHashMap = developFileDAO.getMapList(param);
			
		
		
		return resultHashMap;
	}
	
	public  Map<String, List<Object>> getMultiFileHashMap(String targetTable, String targetTableIdxColumnName, String targetTableIdxValue) {
		Map<String, List<Object>> resultHashMap = new HashMap<String, List<Object>>();			
		param = new HashMap<String, Object>();
		searchList = new ArrayList<DTForm>();		    	
    	searchList.add(new DTForm("A.ITEM_ID", "file"));
		param.put("searchList", searchList);
    	param.put("targetTable", targetTable);
    	param.put("targetTableIdxColumnName", targetTableIdxColumnName);
    	param.put("targetTableIdxValue", targetTableIdxValue);
		resultHashMap = developFileDAO.getMapList(param);
			
		
		
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

//공통코드 끝	

//커스텀 코드 시작
	/**
	 * HRD_DEVELOP_***_CONFM 새로운 승인상태 등록(모든 메뉴에서 승인상태를 업데이트할 때 동일하게 사용)
	 * @param targetTable
	 * @param targetColumn
	 * @param confmStatus
	 * @param cnslIdx
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
	 * HRD_CNSL_***_CONFM 새로운 승인상태 등록(모든 메뉴에서 승인상태를 업데이트할 때 동일하게 사용)
	 * @param targetTable
	 * @param targetColumn
	 * @param confmStatus
	 * @param cnslIdx
	 * @param keyIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insertConfm(String targetTable, String targetTableIdx, String confmStatus, int keyIdx, int ctIdx, String regiIp) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		
		param = new HashMap<String, Object>();				// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();						// 저장항목

		param.put("targetTable", targetTable);
		param.put("targetTableIdx", targetTableIdx);
		
		// 승인 식별색인 가져오기
		int confmIdx = developDctDAO.getNextId(param);
    	
    	dataList.add(new DTForm("CNSL_IDX", keyIdx));
    	dataList.add(new DTForm("CT_IDX", ctIdx));
    	dataList.add(new DTForm("CONFM_IDX", confmIdx));
    	dataList.add(new DTForm("CONFM_STATUS", confmStatus));

    	// 등록자 정보 setting
    	dataList = addRegiInfo(dataList, loginVO, regiIp);
    	
    	param.put("dataList", dataList);
    	
    	int confmResult = developDctDAO.insertConfm(param);
		
		return confmResult;
	}
	
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
	@Override
	public int insertDevelopForDoctorHelp(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray helpInsertColumnOrder, String confmStatus) throws Exception {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(helpInsertColumnOrder)) return -1;
		
		param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		//테이블
		String targetTable = "HRD_DEVLOP";
		//테이블키
		String targetTableIdx = "DEVLOP_IDX";
		
		param.put("targetTable", targetTable);
		param.put("targetTableIdx", targetTableIdx);
		
		// 로그인한 회원의 정보 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();				
		
		// 마지막 DEV_IDX의 다음 값(insert준비용.)
		int keyIdx = developDAO.getNextIdx(param);

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, helpInsertColumnOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		
		String bplNo = (String) parameterMap.get("bplNo");
		String etcDemandMatter = (String) parameterMap.get("etcDemandMatter");
		
		// parameterMap에서 가져온 시군구 코드로 지부지사 idx 가져오기
		String sidoCode = (String) parameterMap.get("helpSido");
		
		Map<String, Object> param2 = new HashMap<String, Object>();	
		param2.put("BLOCK_CD", sidoCode);
		
		int insttIdx = developDAO.getInsttIdx(param2);
		
//		param2.clear();
		
		// 사업장관리번호와 지부지사idx로 주치의 매칭하기
//		param2.put("INSTT_IDX", insttIdx);
//		param2.put("BPL_NO", bplNo);
//		int doctorIdx = getDoctorIdx(param2);
		
		dataList.add(new DTForm(targetTableIdx, keyIdx));
		dataList.add(new DTForm("BPL_NO", bplNo));
		dataList.add(new DTForm("ETC_DEMAND_MATTER", etcDemandMatter));
		dataList.add(new DTForm("TR_OPRTN_BRFFC_CD", insttIdx));
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
//		dataList.add(new DTForm("DOCTOR_IDX", doctorIdx));

		dataList = addRegiInfo(dataList, loginVO, regiIp);
		dataList = addLastModiInfo(dataList, loginVO, regiIp);		
    	
		param.put("dataList", dataList);

		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = developDAO.insert(param);
		
		// HRD_DEVLOP_CONFM 테이블에 1차승인 상태 insert하기
		int insertConfm = insertConfm("HRD_DEVLOP_CONFM", "CONFM_IDX", confmStatus, keyIdx, regiIp);
		
		return (result > 0 && insertConfm > 0)? 1 : 0;
	}
	/**
	 * 해당 기업의 가장 최근 기초진단지 식별번호를 가져온다
	 * @param BPL_NO
	 * @return bsisCnsl
	 * @throws Exception
	 */	
	@Override
	public long getMaxBsisCnsl(String BPL_NO) throws Exception {
		param = new HashMap<String, Object>();
		param.put("BPL_NO", BPL_NO);
		return developDAO.getMaxBsisCnsl(param);
	}
	
	/**
	 * 해당 기업의 가장 최근 기초진단지의 추천된 훈련을 가져온다
	 * @param bsisCnsl_idx
	 * @return List<Object>
	 * @throws Exception
	 */
	@Override
	public List<Object> getRecommend(long bsiscnsl_idx) throws Exception {
		List<Object> recoms = developDAO.bsisRecom(bsiscnsl_idx);
		return recoms;
	}	
	
	/**
	 * 추천된 훈련(혹은 목록에서 선택한)의 과정 정보를 가져온다
	 * @param param
	 * @return List<Object>
	 * @throws Exception
	 */
	@Override
	public DataMap getTpInfo(Map<String, Object> param) throws Exception {		
		return developDAO.getTpInfo(param);
	}
	
	/**
	 * 추천된 훈련(혹은 목록에서 선택한)의 과정 상세 정보를 가져온다
	 * @param param
	 * @return List<Object>
	 * @throws Exception
	 */
	@Override
	public List<Object> getTpSubInfo(Map<String, Object> param) throws Exception {
		return developDAO.getTpSubInfo(param);
	}
	
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
	@SuppressWarnings("unchecked")
	@Override
	public int insertDevelopAsStatusCode(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo,
			JSONObject items, JSONArray developInsertColumnOrder, String confmStatus, String siteId) throws Exception {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(developInsertColumnOrder)) return -1;
		
		param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		//테이블
		String targetTable = "HRD_DEVLOP";
		//테이블키
		String targetTableIdx = "DEVLOP_IDX";
		
		// 로그인한 회원의 정보 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String BPL_NO = loginVO.getBplNo();
		
		param.put("targetTable", targetTable);
		param.put("targetTableIdx", targetTableIdx);
		
		// 마지막 DEV_IDX의 다음 값(insert준비용.)
		int keyIdx = developDAO.getNextIdx(param);

		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, developInsertColumnOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		String sido = (String) parameterMap.get("sido");
		String insttIdx = (String) parameterMap.get("insttIdx");
		String tpDevlopSe = (String) parameterMap.get("inputType");
//		int doctorIdx = 0;
//		
//		if(siteId.equals("web")) {
//			param.put("INSTT_IDX", insttIdx);
//			param.put("BPL_NO", BPL_NO);
//			doctorIdx = getDoctorIdx(param);
//		} else {
//			doctorIdx = loginVO.getDoctorIdx();
//		}
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		dataList.add(new DTForm(targetTableIdx, keyIdx));
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		dataList.add(new DTForm("TR_OPRTN_GUGUN_CD", sido));
		dataList.add(new DTForm("TR_OPRTN_BRFFC_CD", insttIdx));
//		dataList.add(new DTForm("DOCTOR_IDX", doctorIdx));
		dataList.add(new DTForm("TP_DEVLOP_SE", tpDevlopSe));
		

		dataList = addRegiInfo(dataList, loginVO, regiIp);
		dataList = addLastModiInfo(dataList, loginVO, regiIp);		
    	
		param.put("dataList", dataList);

		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = developDAO.insert(param);
		
		// HRD_DEVLOP_CONFM 테이블에 승인 상태 insert하기
		int insertConfm = 0;
		if(!confmStatus.equals("5")) {
			insertConfm = insertConfm("HRD_DEVLOP_CONFM", "CONFM_IDX", confmStatus, keyIdx, regiIp);
		} else {
			// 임시저장일 때는 HRD_DEVLOP_CONFM에 insert 안함
			insertConfm = 1;
		}
				
		
		return (result > 0 && insertConfm > 0)? 1 : 0;
	}


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
	@Override
	public int insertDevelopTpAndTpSub(String uploadModulePath, String regiIp, ParamForm parameterMap,
			JSONObject settingInfo, JSONObject items, JSONArray developTpInsertColumnOrder)
			throws Exception {
		
		param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		//테이블
		String targetTable = "HRD_DEVLOP_TP";
		//테이블키
		String targetTableIdx = "TP_IDX";
		
		// 로그인한 회원의 정보 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		
		
		param.put("targetTable", targetTable);
		param.put("targetTableIdx", targetTableIdx);
		param.put("BPL_NO", parameterMap.get("bplNo"));
		
		int devlopIdx = developDAO.getMaxDevlopIdx(param);
		param.put("DEVLOP_IDX", devlopIdx);
		
		// DEVLOP_IDX에 해당하는 마지막 TP_IDX 의 다음 값(insert준비용.)
		int keyIdx = developDAO.getNextIdx(param);

		String tpPicTelNo = (String) parameterMap.get("tpPicTelNo");
		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, developTpInsertColumnOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		dataList.add(new DTForm(targetTableIdx, keyIdx));	
		dataList.add(new DTForm("DEVLOP_IDX", devlopIdx));	
		dataList.add(new DTForm("TP_PIC_TELNO", "PKG_DGUARD.FN_ENC_TELNO('" + tpPicTelNo + "')", 1));
		

		dataList = addRegiInfo(dataList, loginVO, regiIp);
		dataList = addLastModiInfo(dataList, loginVO, regiIp);		
    	
		param.put("dataList", dataList);
		

		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = developDAO.insert(param);
		
		deleteAndinsertTpSub("HRD_DEVLOP_TP_SUB", devlopIdx, keyIdx, regiIp, parameterMap);
				
		
		return result;
	}
	
	

	/**
	 * [훈련과정맞춤개발] TP_SUB에 인서트
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertTpSub(String targetTable, int devlopIdx, int keyIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		// HRD담당자 정보는 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		searchList = new ArrayList<DTForm>();							// 삭제조건
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("DEVLOP_IDX", devlopIdx));
		searchList.add(new DTForm("TP_IDX", keyIdx));
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
		
		chgLogMapper.writeDataLog("HRD_DEVLOP_TP_SUB","D",searchList);
		// 기존 데이터 삭제하기
		developMultiDAO.cdelete(delParam);
		
		int insertSub = 0;
		
		param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 데이터 항목
		param.put("targetTable", targetTable);		
		int tpSubLength = StringUtil.getInt((String) parameterMap.get("tpSubLength"));

		for(int i = 0; i < tpSubLength; i++) {
			
			if(parameterMap.get("courseNo" + i) != null) {
				String courseNo = (String) parameterMap.get("courseNo" + i);
				String courseName = (String) parameterMap.get("courseName" + i);				
				String cn = (String) parameterMap.get("cn" + i);				
				String time = (String) parameterMap.get("time" + i);				
				String tchMethod = (String) parameterMap.get("tchMethod" + i);				
				String prtbizIdx = (String) parameterMap.get("prtbizIdx");				
				
				// REGI, LAST_MODI 항목 setting
				dataList = addRegiInfo(dataList, loginVO, regiIp);
				dataList = addLastModiInfo(dataList, loginVO, regiIp);
				
				dataList.add(new DTForm("TP_COURSE_IDX", courseNo));
				dataList.add(new DTForm("DEVLOP_IDX", devlopIdx));
				dataList.add(new DTForm("TP_IDX", keyIdx));
				dataList.add(new DTForm("PRTBIZ_IDX", prtbizIdx));
				dataList.add(new DTForm("COURSE_NO", courseNo));
				dataList.add(new DTForm("COURSE_NAME", courseName));
				dataList.add(new DTForm("TR_CN", cn));
				dataList.add(new DTForm("TRTM", time));
				dataList.add(new DTForm("TCHMETHOD", tchMethod));
				
				param.put("dataList", dataList);
				
				insertSub += developMultiDAO.insert(param);
				
				dataList.clear();
			}
			
		}
		
		return insertSub;
	}

	/**
	 * [훈련과정맞춤개발] S-OJT 한도 체크
	 * @param BPL_NO
	 * @return String
	 * @throws Exception
	 */
	@Override
	public String getSojtAvailableFlag(String BPL_NO) throws Exception {		
		return developDAO.getSojtAvailableFlag(BPL_NO);
	}

	/**
	 * [훈련과정맞춤개발] 종합이력 목록
	 * @param 
	 * @return List
	 * @throws Exception
	 */
	@Override
	public List<Object> getTotalList(Map<String, Object> param) throws Exception {
		return developDAO.getTotalList(param);
	}

	@Override
	public int getTotalListCount(Map<String, Object> param) {
		return developDAO.getTotalListCount(param);
	}
	
	/*신청(혹은 주치의 지원 신청)시 입력한 instt_idx(지부지사)와 사업장관리번호의 지부지사를 비교한다.
	 * 같으면 기업에 배정된 주치의 return
	 * 다르면 입력한 지부지사의 주치의들 중 가장 기업과 매칭이 적게 된 주치의 return*/
	private int getDoctorIdx(Map<String, Object> param) {
		return developDAO.getDoctorIdx(param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateDevelopAsStatusCode(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo,
			JSONObject items, JSONArray developInsertColumnOrder, String confmStatus, String siteId) throws Exception {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(developInsertColumnOrder)) return -1;
		
		param = new HashMap<String, Object>();					// mapper parameter 데이터
		searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		//테이블
		String targetTable = "HRD_DEVLOP";
		
		// 로그인한 회원의 정보 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String BPL_NO = loginVO.getBplNo();
		
		param.put("targetTable", targetTable);
		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, developInsertColumnOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		String sido = (String) parameterMap.get("sido");
		String insttIdx = (String) parameterMap.get("insttIdx");
		String tpDevlopSe = (String) parameterMap.get("inputType");
		
		int doctorIdx = 0;
		if(siteId.equals("web")) {
			param.put("INSTT_IDX", insttIdx);
			param.put("BPL_NO", BPL_NO);
			doctorIdx = getDoctorIdx(param);
		} else {
			doctorIdx = loginVO.getDoctorIdx();
		}
		
		String devlopIdx = (String) parameterMap.get("devlopIdx");
		searchList.add(new DTForm("DEVLOP_IDX", devlopIdx));
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		dataList.add(new DTForm("TR_OPRTN_GUGUN_CD", sido));
		dataList.add(new DTForm("TR_OPRTN_BRFFC_CD", insttIdx));
		dataList.add(new DTForm("DOCTOR_IDX", doctorIdx));
		dataList.add(new DTForm("TP_DEVLOP_SE", tpDevlopSe));
		

		dataList = addLastModiInfo(dataList, loginVO, regiIp);		
    	
		param.put("dataList", dataList);
		param.put("searchList", searchList);

		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = developDAO.update(param);
				
		// HRD_DEVLOP_CONFM 테이블에 승인 상태 insert하기
		int insertConfm = 0;
		if(!confmStatus.equals("5")) {
			insertConfm = insertConfm("HRD_DEVLOP_CONFM", "CONFM_IDX", confmStatus, StringUtil.getInt(devlopIdx), regiIp);
		} else {
			// 임시저장일 때는 HRD_DEVLOP_CONFM에 insert 안함
			insertConfm = 1;
		}
				
		
		return (result > 0 && insertConfm > 0)? 1 : 0;
	}
	
	/**
	 * [훈련과정맞춤개발] 표준개발 신청서 신청시 update(상세정보)
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
	@Override
	public int updateDevelopTpAndTpSub(String uploadModulePath, String regiIp, ParamForm parameterMap,
			JSONObject settingInfo, JSONObject items, JSONArray developTpInsertColumnOrder)
			throws Exception {
		
		param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 저장항목
		searchList = new ArrayList<DTForm>();							// 검색조건
		
		//테이블
		String targetTable = "HRD_DEVLOP_TP";
		//테이블키
		String targetTableIdx = "TP_IDX";
		
		// 로그인한 회원의 정보 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		
		
		param.put("targetTable", targetTable);
		param.put("targetTableIdx", targetTableIdx);
		
		String devlopIdx = (String) parameterMap.get("devlopIdx");
		param.put("DEVLOP_IDX", devlopIdx);
		
		String tpIdx = (String) parameterMap.get("tpIdx");
		
		String tpPicTelNo = (String) parameterMap.get("tpPicTelNo");
		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, developTpInsertColumnOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		
		searchList.add(new DTForm("DEVLOP_IDX", devlopIdx));
		searchList.add(new DTForm("TP_IDX", tpIdx));		
		dataList.add(new DTForm("TP_PIC_TELNO", "PKG_DGUARD.FN_ENC_TELNO('" + tpPicTelNo + "')", 1));		
		

		dataList = addLastModiInfo(dataList, loginVO, regiIp);		
    	
		param.put("dataList", dataList);
		param.put("searchList", searchList);

		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = developDAO.update(param);
		
		deleteAndinsertTpSub("HRD_DEVLOP_TP_SUB", StringUtil.getInt((String)devlopIdx), StringUtil.getInt((String)tpIdx), regiIp, parameterMap);
				
		
		return result;
	}
	
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
	@Override
	public int updateStatusAndOpinion(String targetTable, String targetTableIdx, String confmStatus, int keyIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		String corpOpinion = (String) parameterMap.get("corpOpinion");
		
		// update 조건
		searchList.add(new DTForm(targetTableIdx, keyIdx));
		
		// 이력 남기기
		chgLogMapper.writeDataLog(targetTable, "U", searchList);

		// dataList에 LAST_MODI 정보 넣는 함수
		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		
		// update할 데이터 항목
		dataList.add(new DTForm("CORP_OPINION", corpOpinion));
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		
		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("targetTable", targetTable);
		
		int updateDev = developDAO.update(param);
		
		// HRD_DEVLOP_CONFM 테이블에 승인 상태 insert하기
		int insertConfm = 0;
		insertConfm = insertConfm("HRD_DEVLOP_CONFM", "CONFM_IDX", confmStatus, keyIdx, regiIp);
		
		return (updateDev > 0 && insertConfm > 0)? 1 : 0;
	}
	
	
	@Override
	public int getTrainingListCount(Map<String, Object> param) {
		return developDAO.getTrainingListCount(param);
	}

	/**
	 * 훈련과정 목록
	 * @param param
	 * @return List
	 * @throws Exception
	 */
	@Override
	public List<?> getTrainingList(Map<String, Object> param) {
		return developDAO.getTrainingList(param);
	}

	@Override
	public List<?> getSupportList(Map<String, Object> param) {
		return developDAO.getSupportList(param);
	}

	@Override
	public int getSupportListCount(Map<String, Object> param) {
		return developDAO.getSupportListCount(param);
	}
	
	public List<Object> getAiRecommendList(String apiName, String BPL_NO, int rsltIdx) throws Exception{
		String rankUrl = "";
		String response = "";
		List<Object> result = new ArrayList<>();
		if("hrdk_bizr_reco_system".equals(apiName)) {
			rankUrl = "http://165.213.109.91:35601/hrdk_bizr_reco_system";			
		}else if("hrdk_edu_reco_system".equals(apiName)) {
			//운영
			rankUrl = "http://165.213.109.91:35602/hrdk_edu_reco_system";
			//개발
//			rankUrl = "http://165.213.109.91:35604/hrdk_edu_reco_system";
		}else if("hrdk_edu_develop_page".equals(apiName)) {
			rankUrl = "http://165.213.109.91:35603/hrdk_edu_develop_page";								
		}
		
		if(!rankUrl.equals("")) {					
			response = postRequest(rankUrl, BPL_NO, rsltIdx);
			if(response == null) {
				// nothing
				
			} else {
				response = response.substring(1, response.length());
				param = new HashMap<String, Object>();
				param.put("RESPONSE", response);
				param.put("apiName", apiName);
				param.put("cursor", null);				
				developDAO.getAiRcommendRankList(param);
				Object value = param.get("cursor");
				//기초컨설팅 AI추천 시 받은 리턴값을 insert(과정개발에서 기초컨설팅 이력이 있는 기업에게 AI추천 내역을 보여줄때 사용함)
				if("hrdk_edu_reco_system".equals(apiName)){
					param.put("BPL_NO", BPL_NO);
					param.put("rsltIdx", rsltIdx);
					developDAO.upsertEduRecoSystemValue(param);
				}
				result.addAll((List) value);
			}
			
		}
		return result;
		
	}
	
	@Override
	public String getAiEduRecoSystemRawValue(String BPL_NO, int rsltIdx) throws Exception {
		String rankUrl = "http://165.213.109.91:35602/hrdk_edu_reco_system";
		String response = postRequest(rankUrl, BPL_NO, rsltIdx);
		response = response.substring(1, response.length());
		param.put("RESPONSE", response);
		param.put("BPL_NO", BPL_NO);
		param.put("rsltIdx", rsltIdx);
		developDAO.upsertEduRecoSystemValue(param);
		return response;
	}

	
	@Override
	public List<Object> getBsisWithAiRecommendList(String trainingRecommend, long bsiscnslIdx) {
		List<Object> result = new ArrayList<>();
		param = new HashMap<String, Object>();
		
		param.put("bsiscnslIdx", bsiscnslIdx);
		param.put("trainingRecommend", trainingRecommend);
		param.put("cursor", null);
		
		developDAO.getBsisWithAiRecommendList(param);
		
		Object value = param.get("cursor");
		result.addAll((List) value);
		return result;
	}
	
	@Override
	public String getEduRecoSystemValue(String BPL_NO, int rsltIdx) {
		param = new HashMap<String, Object>();
		
		param.put("BPL_NO", BPL_NO);
		param.put("rsltIdx", rsltIdx);
		
		return developDAO.getEduRecoSystemValue(param);
	}

	

	private String postRequest(String rankUrl, String BPL_NO, int rsltIdx) throws Exception {
		//setting
		URL url = new URL(rankUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestProperty("Accept-Charset", "UTF-8");
		
		con.setDoOutput(true);
		
		//call
		JSONObject param = new JSONObject();
		param.put("user_id", BPL_NO);
		if(rsltIdx != 0) {			
			param.put("rslt_idx", rsltIdx);
		}
		
		try ( BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream())) ) {
			bw.write(param.toString());
			bw.flush();
			bw.close();			
		}
		
		int responseCode = con.getResponseCode();
		if(responseCode == HttpURLConnection.HTTP_OK) {
			// OK > Parse!
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			con.disconnect();
			
			String response = sb.toString();
			
			return response;
		} else {
			// 에러처리
			try( BufferedReader errorIn = new BufferedReader(new InputStreamReader(con.getErrorStream())) ) {
				StringBuilder errorSb = new StringBuilder();
				String errorLine;
				while((errorLine = errorIn.readLine()) != null) {
					errorSb.append(errorLine);
				}
				
				// 에러응답에 대한 처리를 어떻게 할것인가  > log남기고 & null 반환
//				System.out.println("Error Response : "+ errorSb.toString());
				return null;
			}
		}
	}

	
	/**
	 * [비용청구] 비용청구 컨설팅 리포트 가져오기
	 * @param param
	 * @return 
	 * @throws Exception
	 */
	@Override
	public DataMap getCnslReportInfo(Map<String, Object> param) {
		return developDAO.getCnslReportInfo(param);
	}

	/**
	 * 비용청구 컨설팅 팀 리스트
	 * @param param
	 * @return List
	 * @throws Exception
	 */
	@Override
	public List<Object> getCnslTeamInfo(Map<String, Object> param) throws Exception {
		return developDAO.getCnslTeamInfo(param);
	}
	
	/**
	 * 비용신청때 선택한 훈련과정을 제외한 모든 해당 기업의 훈련과정 리스트
	 * @param param
	 * @return List
	 * @throws Exception
	 */
	@Override
	public List<Object> getCorpTpList(Map<String, Object> param) throws Exception {
		return developDAO.getCorpTpList(param);
	}

	
	/**
	 * 비용청구 신청서 최초신청 / 최초임시저장
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
	@Override
	public int insertCnslCostAsStatusCode(String uploadModulePath, String regiIp, ParamForm parameterMap,
			JSONObject settingInfo, JSONObject items, JSONArray insertColumnOrder, String confmStatus) throws Exception {
		param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		//테이블
		String targetTable = "HRD_CNSL_COST";
		//테이블키
		String targetTableIdx = "CT_IDX";
		
		// 로그인한 회원의 정보 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String BPL_NO = loginVO.getBplNo();
		
		param.put("targetTable", targetTable);
		param.put("targetTableIdx", targetTableIdx);
		
		// 마지막 DEV_IDX의 다음 값(insert준비용.)
		int keyIdx = developDAO.getNextIdx(param);

		int cnslIdx = StringUtil.getInt((String)parameterMap.get("cnslIdx"));
		
		String acnutNo = (String) parameterMap.get("acnutNo");	
		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, insertColumnOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		param.put("BPL_NO", BPL_NO);
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		dataList.add(new DTForm(targetTableIdx, keyIdx));
		dataList.add(new DTForm("CNSL_IDX", cnslIdx));
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		dataList.add(new DTForm("ACNUTNO", "PKG_DGUARD.FN_ENC_ACNUTNO('" + acnutNo + "')", 1));			// 계좌번호 암호화

		dataList = addRegiInfo(dataList, loginVO, regiIp);
		dataList = addLastModiInfo(dataList, loginVO, regiIp);		
    	
		param.put("dataList", dataList);

		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = developDAO.insert(param);
		deleteAndInsertCnslCostSub("HRD_CNSL_COST_SUB", keyIdx, regiIp, parameterMap);
		
		if(result > 0){			
			insertFile(dataMap, "HRD_CNSL_COST_FILE", "CT_IDX", cnslIdx, keyIdx, fileRealPath);
		}
		
		// HRD_DEVLOP_CONFM 테이블에 승인 상태 insert하기
		int insertConfm = 0;
		if(!confmStatus.equals("5")) {
			insertConfm = insertConfm("HRD_CNSL_COST_CONFM", "CONFM_IDX", confmStatus, cnslIdx, keyIdx, regiIp);
		} else {
			// 임시저장일 때는 HRD_DEVLOP_CONFM에 insert 안함
			insertConfm = 1;
		}
				
		
		return (result > 0 && insertConfm > 0)? 1 : 0;
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

	
	/**
	 * 비용청구 신청 / 임시저장(임시저장 후)
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
	@Override
	public int updateCnslCostAsStatusCode(String uploadModulePath, String regiIp, ParamForm parameterMap,
			JSONObject settingInfo, JSONObject items, JSONArray insertColumnOrder, String confmStatus) throws Exception {
		
		param = new HashMap<String, Object>();					// mapper parameter 데이터
		searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		//테이블
		String targetTable = "HRD_CNSL_COST";
		
		// 로그인한 회원의 정보 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String BPL_NO = loginVO.getBplNo();
		
		param.put("targetTable", targetTable);
		
		String acnutNo = (String) parameterMap.get("acnutNo");	
		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, insertColumnOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		int cnslIdx = StringUtil.getInt((String)parameterMap.get("cnslIdx"));
		int ctIdx = StringUtil.getInt((String)parameterMap.get("ctIdx"));
		param.put("BPL_NO", BPL_NO);
		
		searchList.add(new DTForm("CNSL_IDX", cnslIdx));
		searchList.add(new DTForm("CT_IDX", ctIdx));
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		dataList.add(new DTForm("ACNUTNO", "PKG_DGUARD.FN_ENC_ACNUTNO('" + acnutNo + "')", 1));			// 계좌번호 암호화

		dataList = addLastModiInfo(dataList, loginVO, regiIp);		
    	
		param.put("dataList", dataList);
		param.put("searchList", searchList);

		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = developDAO.update(param);
		
		if(result > 0){			
			insertFile(dataMap, "HRD_CNSL_COST_FILE", "CT_IDX", cnslIdx, ctIdx, fileRealPath);
		}
		
		deleteAndInsertCnslCostSub("HRD_CNSL_COST_SUB", ctIdx, regiIp, parameterMap);
				
		// HRD_DEVLOP_CONFM 테이블에 승인 상태 insert하기
		int insertConfm = 0;
		if(!confmStatus.equals("5")) {
			insertConfm = insertConfm("HRD_CNSL_COST_CONFM", "CONFM_IDX", confmStatus, StringUtil.getInt(cnslIdx), ctIdx, regiIp);
		} else {
			// 임시저장일 때는 HRD_DEVLOP_CONFM에 insert 안함
			insertConfm = 1;
		}
				
		
		return (result > 0 && insertConfm > 0)? 1 : 0;
	}

	private int insertFile(HashMap<String, Object> dataMap, String targetTable, String targetTableIdx, int cnslIdx, int keyIdx, String fileRealPath) {
		int result = 0;
		
		// 3.3.3 multi file 삭제
		List fileDeleteSearchList = StringUtil.getList(dataMap.get("fileDeleteSearchList"));
		if(fileDeleteSearchList != null) {

			List<Object> deleteMultiFileList = new ArrayList<Object>();
			int fileDataSize = fileDeleteSearchList.size();
			for(int i = 0 ; i < fileDataSize ; i ++) {
				Map<String, Object> fileParam = (HashMap)fileDeleteSearchList.get(i);
				fileParam.put("targetTable", targetTable);
				fileParam.put("CNSL_IDX", cnslIdx);
				fileParam.put("targetTableIdxColumnName", "CT_IDX");
				fileParam.put("targetTableIdxValue", keyIdx);
				// 6.1 삭제목록 select
				List<Object> deleteFileList2 = developFileDAO.getList(fileParam);
				if(deleteFileList2 != null) deleteMultiFileList.addAll(deleteFileList2);
				// 6.2 DB 삭제
				result = developFileDAO.cdelete(fileParam);
			}
			
			// 3.3.4 multi file 삭제
			FileUtil.isKeyDelete(fileRealPath, deleteMultiFileList, "FILE_SAVED_NAME");
		}
		// 3.3 multi file 저장
		List fileDataList = StringUtil.getList(dataMap.get("fileDataList"));
		if(fileDataList != null) {
			int fileDataSize = fileDataList.size();
			for(int i = 0 ; i < fileDataSize ; i ++) {
				Map<String, Object> fileParam = (HashMap)fileDataList.get(i);
				fileParam.put("targetTable", targetTable);
				fileParam.put("CNSL_IDX", cnslIdx);
				fileParam.put("targetTableIdxColumnName", targetTableIdx);
				fileParam.put("targetTableIdxValue", keyIdx);
				int fleIdx = developFileDAO.getNextId(fileParam);
				fileParam.put("FLE_IDX", fleIdx);
				result = developFileDAO.insert(fileParam);
			}
		}
		
		return result;
		
	}

	@Override
	public int getCompareRequestAndVoByBplNo(int devlopIdx, String BPL_NO) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("devlopIdx", devlopIdx);
		param.put("BPL_NO", BPL_NO);
		return developDAO.getCompareRequestAndVoByBplNo(param);
	}

	@Override
	public int getIsSojt(String BPL_NO) {
		return developDAO.getIsSojt(BPL_NO);
	}

	@Override
	public int getIsBsc(String BPL_NO) {
		return developDAO.getIsBsc(BPL_NO);
	}

	@Override
	public int getRsltIdxByBsiscnslIdx(long bsiscnslIdx) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bsiscnslIdx", bsiscnslIdx);
		return developDAO.getRsltIdxByBsiscnslIdx(param);
	}









}