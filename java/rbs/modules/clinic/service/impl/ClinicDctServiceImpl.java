package rbs.modules.clinic.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import rbs.modules.clinic.mapper.ClinicDctFileMapper;
import rbs.modules.clinic.mapper.ClinicDctMapper;
import rbs.modules.clinic.mapper.ClinicDctMultiMapper;
import rbs.modules.clinic.mapper.ClinicMapper;
import rbs.modules.clinic.mapper.ClinicMultiMapper;
import rbs.modules.clinic.service.ClinicDctService;

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("clinicDctService")
public class ClinicDctServiceImpl extends EgovAbstractServiceImpl implements ClinicDctService {

	@Resource(name="clinicMapper")
	private ClinicMapper clinicDAO;
	
	@Resource(name="clinicMultiMapper")
	private ClinicMultiMapper clinicMultiDAO;
	
	@Resource(name="clinicDctMapper")
	private ClinicDctMapper clinicDctDAO;
	
	@Resource(name="clinicDctFileMapper")
	private ClinicDctFileMapper clinicDctFileDAO;
	
	@Resource(name="clinicDctMultiMapper")
	private ClinicDctMultiMapper clinicDctMultiDAO;		
	
	@Resource(name="chgLogMapper")
	private ChgLogMapper chgLogMapper;
	
	List<DTForm> dataList;
	
	/*공통코드 시작*/
	
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
	
	
	/*공통코드 끝*/	
	
	/**
	 * 특정 테이블 IDX의 max 값 가져오기
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getMaxIdx(String targetTable, String targetTableIdx, Map<String, Object> param) {
    	param.put("targetTable", targetTable);
    	param.put("targetTableIdx", targetTableIdx);
    	return clinicDctDAO.getMaxIdx(param);
    }
	
	/**
	 * 지부지사 부장, 본부 직원이 로그인했을 때 신청서 전체 목록 수(신청관리에서 사용)
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalReqCount(String targetTable, Map<String, Object> param) {
    	param.put("targetTable", targetTable);
    	return clinicDctDAO.getTotalReqCount(param);
    }
    
    /**
	 * 지부지사 직원이 로그인했을 때 신청서 전체 목록 수(신청관리에서 사용)
	 * @param targetTable
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalReqCountForDoctor(String targetTable, Map<String, Object> param) {
    	param.put("targetTable", targetTable);
    	return clinicDctDAO.getTotalReqCountForDoctor(param);
	}

    
    /**
	 * 지부지사 부장, 본부 직원이 로그인했을 때 전체 목록 수(계획관리, 성과관리, 비용관리에서 사용)
	 * @param targetTable
	 * @param param
	 * @return
	 */
	@Override
	public int getTotalCount(String targetTable, Map<String, Object> param) {
		param.put("targetTable", targetTable);
    	return clinicDctDAO.getTotalCount(param);
	}

	/**
	 * 지부지사 직원이 로그인했을 때 전체 목록 수(계획관리, 성과관리, 비용관리에서 사용)
	 * @param targetTable
	 * @param param
	 * @return
	 */
	@Override
	public int getTotalCountForDoctor(String targetTable, Map<String, Object> param) {
		param.put("targetTable", targetTable);
    	return clinicDctDAO.getTotalCountForDoctor(param);
	}

	/**
	 * 지부지사 부장, 본부 직원이 로그인했을 때 신청서 전체 목록(신청관리에서 사용)
	 * @param targetTable
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getReqList(String targetTable, Map<String, Object> param) {
    	param.put("targetTable", targetTable);
		return clinicDctDAO.getReqList(param);
	}
	
	/**
	 * 지부지사 직원이 로그인했을 때 신청서 전체 목록(신청관리에서 사용)
	 * @param targetTable
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getReqListForDoctor(String targetTable, Map<String, Object> param) {
		param.put("targetTable", targetTable);
		return clinicDctDAO.getReqListForDoctor(param);
	}
	
	/**
	 * 지부지사 부장, 본부 직원이 로그인했을 때 전체 목록 (계획관리, 성과관리, 비용관리에서 사용)
	 * @param targetTable
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getList(String targetTable, Map<String, Object> param) {
		param.put("targetTable", targetTable);
		return clinicDctDAO.getList(param);
	}
	
	/**
	 * 지부지사 직원이 로그인했을 때 전체 목록 (계획관리, 성과관리, 비용관리에서 사용)
	 * @param targetTable
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getListForDoctor(String targetTable, Map<String, Object> param) {
		param.put("targetTable", targetTable);
		return clinicDctDAO.getListForDoctor(param);
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
		DataMap viewDAO = clinicDctDAO.getFileView(param);
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
		DataMap viewDAO = clinicDctFileDAO.getFileView(param);
		return viewDAO;
	}
	
	/**
	 * 다운로드 수 수정
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateFileDown(int fnIdx, int keyIdx, String fileColumnName) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
    	param.put("fnIdx", fnIdx);
		param.put("KEY_IDX", keyIdx);
    	param.put("searchList", searchList);
    	param.put("FILE_COLUMN", fileColumnName);
		return clinicDctDAO.updateFileDown(param);
		
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
		return clinicDctFileDAO.updateFileDown(param);
		
	}

	/**
	 * 권한여부
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getAuthCount(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
    	return clinicDctDAO.getAuthCount(param);
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
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 저장항목

		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");	// key 컬럼
				
		// 1. key 얻기
		param.put("fnIdx", fnIdx);
		int keyIdx = clinicDctDAO.getNextId(param);

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
		int result = clinicDctDAO.insert(param);
		
		if(result > 0) {
			// 3.2 multi data 저장
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			if(multiDataList != null) {
				int multiDataSize = multiDataList.size();
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)multiDataList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", keyIdx);
					result = clinicDctMultiDAO.insert(fileParam);
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
					int fleIdx = clinicDctFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = clinicDctFileDAO.insert(fileParam);
				}
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
    	return clinicDctDAO.getDeleteCount(param);
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
		return clinicDctDAO.getDeleteList(param);
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
		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
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
		return clinicDctDAO.delete(param);
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
		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
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
		return clinicDctDAO.restore(param);
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

		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		
    	// 1. 저장조건
		searchList.add(new DTForm(JSONObjectUtil.getString(settingInfo, "idx_column"), deleteIdxs));
		param.put("searchList", searchList);
    	param.put("fnIdx", fnIdx);
		
		List<Object> deleteMultiFileList = null;
		List<Object> deleteFileList = null;
		// 2. 삭제할 파일 select
		// 2.1 삭제할  multi file 목록 select
		deleteMultiFileList = clinicDctFileDAO.getList(param);
		// 2.2 삭제할 file(단일항목) select
		List deleteFileColumnList = ModuleUtil.getFileObjectList(items, itemOrder);
		if(deleteFileColumnList != null) {
			param.put("columnList", deleteFileColumnList);
			
			deleteFileList = clinicDctDAO.getFileList(param);
		}
		
		// 3. delete
		int result = clinicDctDAO.cdelete(param);
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
	public  Map<String, List<Object>> getMultiFileHashMap(String targetTable, int cliIdx, int fnIdx, int keyIdx, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) {
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
			    	param.put("fnIdx", fnIdx);
					resultHashMap.put(itemId, clinicDctFileDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A." + columnName, keyIdx));
		    	searchList.add(new DTForm("A.ITEM_ID", (String[])itemIdList.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
		    	param.put("fnIdx", fnIdx);
		    	param.put("targetTable", targetTable);
		    	param.put("CLI_IDX", cliIdx);
				resultHashMap = clinicDctFileDAO.getMapList(param);
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
					Map<String, Object> param = new HashMap<String, Object>();
					List<DTForm> searchList = new ArrayList<DTForm>();
			    	searchList.add(new DTForm("A." + columnName, keyIdx));
			    	searchList.add(new DTForm("A.ITEM_ID", itemId));
					param.put("searchList", searchList);
			    	param.put("fnIdx", fnIdx);
					resultHashMap.put(itemId, clinicDctMultiDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A." + columnName, keyIdx));
		    	searchList.add(new DTForm("A.ITEM_ID", (String[])itemIdList.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
		    	param.put("fnIdx", fnIdx);
				resultHashMap = clinicDctMultiDAO.getMapList(param);
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
	 * 기업 자가 확인 체크리스트 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getCheckList(int fnIdx, Map<String, Object> param) {
		return clinicDctDAO.getCheckList(param);
	}

	/**
	 * 기업 자가 확인 체크리스트 답변 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getCheckListAnswer(int fnIdx, Map<String, Object> param) {
		return clinicDctDAO.getCheckListAnswer(param);
	}

	/**
	 * 기업 선정 심사표 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getJdgmntabList(int fnIdx, Map<String, Object> param) {
		return clinicDctDAO.getJdgmntabList(param);
	}
	
	/**
	 * 기업 선정 심사표 답변 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getJdgmntabListAnswer(int fnIdx, Map<String, Object> param) {
		return clinicDctDAO.getJdgmntabListAnswer(param);
	}
	
	
	/**
	 * 주치의 지정 -> 기업이 담당하는 지부지사 주치의 전체 목록 수
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalDoctorCount(Map<String, Object> param) {
    	return clinicDctDAO.getTotalDoctorCount(param);
    }
    
	/**
	 * 주치의 지정 -> 기업이 담당하는 지부지사 주치의 전체 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getDoctorList(Map<String, Object> param) {
		return clinicDctDAO.getDoctorList(param);
	}
	
	/**
	 * 상세보기 화면에서 조회 
	 * @param targetTable
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getView(String targetTable, Map<String, Object> param) {
		param.put("targetTable", targetTable);
		DataMap viewDAO = clinicDctDAO.getView(param);
		return viewDAO;
	}
	
	/**
	 * 신청서 상세보기 화면에서 조회(전담주치의 정보 포함) 
	 * @param targetTable
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getReqView(String targetTable, Map<String, Object> param) {
		param.put("targetTable", targetTable);
		DataMap viewDAO = clinicDctDAO.getReqView(param);
		return viewDAO;
	}

	/**
	 * 수정 화면에서 조회 
	 * @param targetTable
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getModify(String targetTable, Map<String, Object> param) {
		param.put("targetTable", targetTable);
		DataMap viewDAO = clinicDctDAO.getModify(param);
		return viewDAO;
	}
	
	/**
	 * 능력개발클리닉 신청서 기업정보
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getCorpInfo(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);		
		DataMap corpInfo = clinicDctDAO.getCorpInfo(param);
		return corpInfo;
	}

	/**
	 * 사업장관리번호로 현재 cliIdx 가져오기
	 * @param bplNo
	 * @param param
	 * @return
	 */
	@Override
	public int getCliIdx(String bplNo) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
    	param.put("BPL_NO", bplNo);
    	return clinicDctDAO.getCliIdx(param);
	}

	/**
	 * [신청관리] HRD_CLI DOCTOR_IDX 수정(접수할 때 전담주치의 지정)
	 * @param cliIdx
	 * @param doctorIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateCliDoctor(int cliIdx, int doctorIdx, String regiIp) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		String targetTable = "HRD_CLI";											// update할 테이블 지정
		
		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		dataList = new ArrayList<DTForm>();						// 저장항목
		
		// dataList에 LAST_MODI 정보 넣는 함수
		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		
		// update 조건
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		
		// update할 데이터 항목
		dataList.add(new DTForm("DOCTOR_IDX", doctorIdx));
		
		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("targetTable", targetTable);
		
		int cliUpdate = clinicDctDAO.update(param);
		
		return cliUpdate;
	}

	/**
	 * [신청관리] HRD_CLI VALID_START_DATE, VALID_END_DATE 수정(최종승인할 때 시작날짜와 최종날짜 수정)
	 * @param cliIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateCliStartEndDate(int cliIdx, String regiIp) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		String targetTable = "HRD_CLI";											// update할 테이블 지정
		
		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		dataList = new ArrayList<DTForm>();						// 저장항목
		
		// 최종승인이 되고 유효시작날짜와 유효종료날짜 설정하기 
		String startDate = "";
		String endDate = "";
		
		Calendar cal = Calendar.getInstance();
		Date curDate = cal.getTime();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		startDate = sdf.format(curDate);
		cal.add(Calendar.YEAR, 3);												// 종료날짜는 시작날짜로부터 3년뒤로 setting
		Date curDatePlus3 = cal.getTime();
		endDate = sdf.format(curDatePlus3);
		
		// dataList에 LAST_MODI 정보 넣는 함수
		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		
		// update 조건
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		
		// update할 데이터 항목
		dataList.add(new DTForm("VALID_START_DATE", startDate));
		dataList.add(new DTForm("VALID_END_DATE", endDate));
		
		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("targetTable", targetTable);
		
		int cliUpdate = clinicDctDAO.update(param);
		
		return cliUpdate;
	}

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
			dataList = new ArrayList<DTForm>();							// 저장항목
	    	
			int cliIdx = StringUtil.getInt((String) parameterMap.get("cliIdx"));
			String telNo = null;
			telNo = (String) parameterMap.get("telNo");
			// 1. 검색조건 setting
			searchList.add(new DTForm(targetColumn, keyIdx));
			searchList.add(new DTForm("CLI_IDX", cliIdx));
			
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
			
			if(telNo != null) {
				dataList.add(new DTForm("TELNO", "PKG_DGUARD.FN_ENC_TELNO('" + telNo +  "')", 1));
			}
			
			if(targetTable.equals("HRD_CLI_REQ")) {
				String slctnResult = (String) parameterMap.get("slctnResult");
				dataList.add(new DTForm("SLCTN_RESULT", slctnResult));
			}
			
	    	param.put("dataList", dataList);
	    	param.put("searchList", searchList);
	    	param.put("targetTable", targetTable);
	    	
	    	// 3. DB 저장
	    	// 3.1 기본 정보 테이블
			int reqUdate = clinicDctDAO.update(param);
			return reqUdate;
	}
	
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
	@Override
	public int updateReqSlctnResultAndDoctorOpinion(String targetTable, String targetColumn, String uploadModulePath, int keyIdx, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		// 3.1 항목설정
		String submitType = "modify";		// 항목 order명
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, "reqJdg_" + submitType + "proc_order");
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		int cliIdx = StringUtil.getInt((String) parameterMap.get("cliIdx"));
		// 1. 검색조건 setting
		searchList.add(new DTForm(targetColumn, keyIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		
		String slctnResult = (String) parameterMap.get("slctnResult");
		
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
		
		dataList.add(new DTForm("SLCTN_RESULT", slctnResult));
		
		param.put("dataList", dataList);
		param.put("searchList", searchList);
		param.put("targetTable", targetTable);
		
		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int reqUdate = clinicDctDAO.update(param);
		return reqUdate;
	}

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
	@Override
	public int updateStatusAndOpinion(String targetTable, String targetColumn, String confmStatus, int keyIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
			
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		String doctorOpinion = (String) parameterMap.get("doctorOpinion");
		int cliIdx = StringUtil.getInt((String) parameterMap.get("cliIdx"));
		
		// update 조건
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		searchList.add(new DTForm(targetColumn, keyIdx));
		
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
		
		int updateReq = clinicDctDAO.update(param);
		
		return updateReq;
	}
	
	/**
	 * HRD_CLI_*** 승인상태 및 주치의 의견, 기업 선정 심사표 결과 업데이트(반려처리할 때 사용)
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
	public int updateStatusAndOpinion(String targetTable, String targetColumn, String confmStatus, int keyIdx, String regiIp, ParamForm parameterMap, String slctnResult) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
			
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		String doctorOpinion = (String) parameterMap.get("doctorOpinion");
		int cliIdx = StringUtil.getInt((String) parameterMap.get("cliIdx"));
		
		// update 조건
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		searchList.add(new DTForm(targetColumn, keyIdx));
		
		// 이력 남기기
		chgLogMapper.writeDataLog(targetTable, "U", searchList);

		// dataList에 LAST_MODI 정보 넣는 함수
		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		
		// update할 데이터 항목
		dataList.add(new DTForm("DOCTOR_OPINION", doctorOpinion));
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		dataList.add(new DTForm("SLCTN_RESULT", slctnResult));
		
		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("targetTable", targetTable);
		
		int updateReq = clinicDctDAO.update(param);
		
		return updateReq;
	}
	
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
	@Override
	public int updateStatus(String targetTable, String targetColumn,  String confmStatus, int cliIdx, int keyIdx, String regiIp) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		// update 조건
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		searchList.add(new DTForm(targetColumn, keyIdx));
		
		// 이력 남기기
		chgLogMapper.writeDataLog(targetTable, "U", searchList);

		// dataList에 LAST_MODI 정보 넣는 함수
		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		
		
		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("targetTable", targetTable);
		
		int updateReq = clinicDctDAO.update(param);
		
		return updateReq;
	}

	/**
	 * HRD_CLI_REQ 에서 기업선정 심사표 입력 후 승인할 때 사용
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
	public int updateStatus(String targetTable, String targetColumn,  String confmStatus, int cliIdx, int keyIdx, String regiIp, String slctnResult) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		// update 조건
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		searchList.add(new DTForm(targetColumn, keyIdx));
		
		// 이력 남기기
		chgLogMapper.writeDataLog(targetTable, "U", searchList);

		// dataList에 LAST_MODI 정보 넣는 함수
		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		dataList.add(new DTForm("SLCTN_RESULT", slctnResult));
		
		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("targetTable", targetTable);
		
		int updateReq = clinicDctDAO.update(param);
		
		return updateReq;
	}
	
	/**
	 * HRD_CLI_PLAN_CHANGE에 신청 상태 update 하기
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
	public int updateStatus(String targetTable, String targetColumn,  String confmStatus, int cliIdx, int keyIdx, String regiIp, int chgIdx) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		// update 조건
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		searchList.add(new DTForm(targetColumn, keyIdx));
		searchList.add(new DTForm("CHG_IDX", chgIdx));
		
		// 이력 남기기
		chgLogMapper.writeDataLog(targetTable, "U", searchList);

		// dataList에 LAST_MODI 정보 넣는 함수
		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		
		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("targetTable", targetTable);
		
		int updateReq = clinicDctDAO.update(param);
		
		return updateReq;
	}

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
	@Override
	public int updateStatusDropRequest(String targetTable, String targetColumn, String confmStatus, int cliIdx, int keyIdx, String regiIp) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		// update 조건
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		searchList.add(new DTForm(targetColumn, keyIdx));
		
		// 이력 남기기
		chgLogMapper.writeDataLog(targetTable, "U", searchList);

		// dataList에 LAST_MODI 정보 넣는 함수
		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		dataList.add(new DTForm("ISDELETE", "1"));
		
		
		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("targetTable", targetTable);
		
		int updateReq = clinicDctDAO.update(param);
		
		return updateReq;
	}

	/**
	 * HRD_CLI_***_CONFM 새로운 승인상태 등록(모든 메뉴에서 승인상태를 업데이트할 때 동일하게 사용)
	 * @param targetTable
	 * @param targetColunm
	 * @param confmStatus
	 * @param cliIdx
	 * @param keyIdx
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insertConfm(String targetTable, String insertColumnKey, String targetColumn, String confmStatus, int cliIdx, int keyIdx, String regiIp) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		
		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();						// 저장항목
		
		// ※현재는 안씀 cli_idx와 req_idx를 기준으로 현재 있는 데이터는 isdelete=0으로 설정한다.(삭제처리)
		/*List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		searchList.add(new DTForm("REQ_IDX", reqIdx));
		param.put("searchList", searchList);
		int isDeleteUpdate = clinicDctDAO.deleteConfm(param);*/
		
		// 등록자 정보 setting
		dataList = addRegiInfo(dataList, loginVO, regiIp);

		param.put("targetTable", targetTable);
		param.put("targetTableIdx", targetColumn);
		
		// 승인 식별색인 가져오기
		int confmIdx = clinicDctDAO.getNextId(param);
    	
    	dataList.add(new DTForm("CLI_IDX", cliIdx));
    	dataList.add(new DTForm(insertColumnKey, keyIdx));
    	dataList.add(new DTForm("CONFM_IDX", confmIdx));
    	dataList.add(new DTForm("CONFM_STATUS", confmStatus));

    	param.put("dataList", dataList);
    	
    	int confmResult = clinicDctDAO.insertConfm(param);
		
		return confmResult;
	}

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
	@Override
	public int insertDoctorHst(String targetTable, String targetColumn, int cliIdx, int memberIdx, String regiIp) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		
		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();						// 저장항목
		
		// 등록자 정보 setting
		dataList = addRegiInfo(dataList, loginVO, regiIp);
		dataList = addLastModiInfo(dataList, loginVO, regiIp);

		param.put("targetTable", targetTable);
		param.put("targetTableIdx", targetColumn);
		
		// 승인 식별색인 가져오기
		int chgSn = clinicDctDAO.getNextId(param);
    	
    	dataList.add(new DTForm("CLI_IDX", cliIdx));
    	dataList.add(new DTForm("CHG_SN", chgSn));
    	dataList.add(new DTForm("DOCTOR_IDX", memberIdx));

    	param.put("dataList", dataList);
    	
    	int hstResult = clinicDctDAO.insert(param);
		
		return hstResult;
	}

	/**
	 * [신청관리] 신청서에서 기업 자가 체크리스트 답변 업데이트
	 * @param uploadModulePath
	 * @param fnIdx
	 * @param cliList
	 * @param doctorList
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteAndinsertReqChk(String targetTable, int reqIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		// 체크리스트 답변은 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		List<DTForm> searchList = new ArrayList<DTForm>();							// 삭제조건
		
		int cliIdx = StringUtil.getInt((String) parameterMap.get("cliIdx"));
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("REQ_IDX", reqIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
		// 이력 남기기
		chgLogMapper.writeDataLog(targetTable, "D", searchList);
		
		// 기존 데이터 삭제하기
		clinicMultiDAO.cdelete(delParam);
		
		// 수정된 체크리스트 답변으로 새로 insert하기
		// 체크리스트 개수 가져오기
		int listLength = StringUtil.getInt((String) parameterMap.get("checkListLength"));
		// 체크리스트 버전 가져오기
		int checkListVer = StringUtil.getInt((String) parameterMap.get("checkVer"));
		
		int insertChk = 0;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 데이터 항목
		param.put("targetTable", targetTable);
		
		for(int i = 1; i <= listLength; i++) {
			
			String isSelfEmp = (String) parameterMap.get("isSelfEmp" + i);
			String selfEmpCn = (String) parameterMap.get("selfEmpCn" + i);
			int checkListIdx = StringUtil.getInt((String) parameterMap.get("checkListIdx" + i));
			
			if(isSelfEmp != null) {
				// REGI, LAST_MODI 항목 setting
				dataList = addRegiInfo(dataList, loginVO, regiIp);
				dataList = addLastModiInfo(dataList, loginVO, regiIp);
				
				dataList.add(new DTForm("CLI_IDX", cliIdx));
				dataList.add(new DTForm("REQ_IDX", reqIdx));
				dataList.add(new DTForm("CHKLST_VER", checkListVer));
				dataList.add(new DTForm("CHKLST_NO", checkListIdx));
				dataList.add(new DTForm("CHKLST_ANSW_YN", isSelfEmp));
				if(selfEmpCn != null) {
					dataList.add(new DTForm("CHKLST_ANSWER_CN", selfEmpCn));
				}
				
				param.put("dataList", dataList);
				
				insertChk += clinicDctMultiDAO.insert(param);
				
				dataList.clear();
			}
		}
		
		return insertChk;
	}

	/**
	 * [신청관리] 신청서에서 기업 선정 심사표 답변 등록
	 * @param uploadModulePath
	 * @param fnIdx
	 * @param cliList
	 * @param doctorList
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteAndinsertReqJdg(String targetTable, int reqIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		// 체크리스트 답변은 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		List<DTForm> searchList = new ArrayList<DTForm>();							// 삭제조건
		
		int cliIdx = StringUtil.getInt((String) parameterMap.get("cliIdx"));
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("REQ_IDX", reqIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
		// 이력 남기기
		chgLogMapper.writeDataLog(targetTable, "D", searchList);
		
		// 기존 데이터 삭제하기
		clinicMultiDAO.cdelete(delParam);
		
		// 수정된 체크리스트 답변으로 새로 insert하기
		// 체크리스트 개수 가져오기
		int listLength = StringUtil.getInt((String) parameterMap.get("jdgListLength"));
		// 체크리스트 버전 가져오기
		int jdgListVer = StringUtil.getInt((String) parameterMap.get("jdgVer"));
		
		int insertChk = 0;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 데이터 항목
		param.put("targetTable", targetTable);
		
		for(int i = 1; i <= listLength; i++) {
			
			String isJdgMn = (String) parameterMap.get("isJdgMn" + i);
			int jdgListIdx = StringUtil.getInt((String) parameterMap.get("jdgListIdx" + i));
			
			if(isJdgMn != null) {
				// REGI, LAST_MODI 항목 setting
				dataList = addRegiInfo(dataList, loginVO, regiIp);
				dataList = addLastModiInfo(dataList, loginVO, regiIp);
				
				dataList.add(new DTForm("CLI_IDX", cliIdx));
				dataList.add(new DTForm("REQ_IDX", reqIdx));
				dataList.add(new DTForm("JDGMNTAB_VER", jdgListVer));
				dataList.add(new DTForm("JDGMNTAB_NO", jdgListIdx));
				dataList.add(new DTForm("JDGMNTAB_RESULT", isJdgMn));
				
				param.put("dataList", dataList);
				
				insertChk += clinicDctMultiDAO.insert(param);
				
				dataList.clear();
			}
		}
		
		return insertChk;
	}

	/**
	 * [신청관리] 신청서 중도포기 처리할 때 해당 클리닉 기업 삭제
	 * @param uploadModulePath
	 * @param cliList
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteCli(String targetTable, int cliIdx, String regiIp) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		dataList = new ArrayList<DTForm>();							// 데이터 항목
		List<DTForm> searchList = new ArrayList<DTForm>();							// 삭제조건
		
		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		
		delParam.put("targetTable", targetTable);
		delParam.put("searchList", searchList);
		
		// 이력 남기기
		chgLogMapper.writeDataLog(targetTable, "U", searchList);
		
		int deleteCli = clinicDctDAO.delete(delParam);
		
		return deleteCli;
	}
	
	/**
	 * 목록 가져오기
	 * @param targetTable
	 * @param targetTableIdx
	 * @param targetTableIdxValue
	 * @param orderColumn
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Object> getList(String targetTable, String targetTableIdx, int targetTableIdxValue, String orderColumn) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();		
		
		param.put("targetTable", targetTable);
		param.put("orderColumn", orderColumn);
		
		searchList.add(new DTForm(targetTableIdx, targetTableIdxValue));
		param.put("searchList", searchList);
		
		List<Object> resultList = clinicDctMultiDAO.getList(param);
		return resultList;
	}
	
	/**
	 * HRD_CLI_PLAN_***_CHANGE 테이블에서 가져오기(계획관리에서 변경요청이 들어왔을 때 사용)
	 * @param targetTable
	 * @param orderColumn
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Object> getChangeList(String targetTable, String orderColumn, Map<String, Object> param) {
		param.put("targetTable", targetTable);
		param.put("orderColumn", orderColumn);
		
		List<Object> resultList = clinicDctMultiDAO.getList(param);
		return resultList;
	}
	
	/**
	 * 계획서에서 지원항목 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteAndinsertPlanSub(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		// HRD담당자 정보는 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		List<DTForm> searchList = new ArrayList<DTForm>();							// 삭제조건
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("PLAN_IDX", planIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
		// 이력 남기기
		chgLogMapper.writeDataLog(targetTable, "D", searchList);
		
		// 기존 데이터 삭제하기
		clinicDctMultiDAO.cdelete(delParam);
		
		int insertSub = 0;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 데이터 항목
		param.put("targetTable", targetTable);
		
		// 지원유형은 현재 10개라서 10으로 하드코딩, 추후에 지원유형 갯수가 변경된다면 10 변경 필요
		for(int i = 1; i <= 10; i++) {

			String reqstYn = "";
			String itemCd = (String) parameterMap.get("itemCd" + i);
			String essntlSe = (String) parameterMap.get("essntlSe" + i);
			//필수사항이면 선택값은  Y
			if(essntlSe.equals("R")) {				
				reqstYn = "Y";
			}else {
				reqstYn = (String) parameterMap.get("reqstYn" + i);
			}
			if(reqstYn == null) {
				// 신청여부에 체크를 안했을 때 N으로 넣기
				reqstYn = "N";
			}
			
			// REGI, LAST_MODI 항목 setting
			dataList = addRegiInfo(dataList, loginVO, regiIp);
			dataList = addLastModiInfo(dataList, loginVO, regiIp);
			
			dataList.add(new DTForm("CLI_IDX", cliIdx));
			dataList.add(new DTForm("PLAN_IDX", planIdx));
			dataList.add(new DTForm("SPORT_ITEM_CD", itemCd));
			dataList.add(new DTForm("ESSNTL_SE", essntlSe));
			dataList.add(new DTForm("REQST_YN", reqstYn));
			
			param.put("dataList", dataList);
			
			insertSub += clinicDctMultiDAO.insert(param);
			
			dataList.clear();
				
			
		}
		
		return insertSub;
	}

	/**
	 * 계획서에서 HRD담당자 정보 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteAndinsertPlanCorp(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		// HRD담당자 정보는 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		List<DTForm> searchList = new ArrayList<DTForm>();							// 삭제조건
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("PLAN_IDX", planIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
		// 이력 남기기
		chgLogMapper.writeDataLog(targetTable, "D", searchList);
		
		// 기존 데이터 삭제하기
		clinicDctMultiDAO.cdelete(delParam);
		
		int insertCorp = 0;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 데이터 항목
		param.put("targetTable", targetTable);
		
		// 입력한 HRD담당자 갯수 가져오기
		int corpLength = StringUtil.getInt((String) parameterMap.get("maxSubIdx")); 
		
		for(int i = 1; i <= corpLength; i++) {
			param.put("targetTableIdx", "PIC_IDX");
			
			int picIdx = clinicDctDAO.getNextId(param);
			
			String psitnDept = (String) parameterMap.get("psitnDept" + i);
			String ofcps = (String) parameterMap.get("ofcps" + i);
			String picNm = (String) parameterMap.get("picNm" + i);
			String hffcCareer = (String) parameterMap.get("hffcCareer" + i);
			String telNo = (String) parameterMap.get("telNo" + i);
			String email = (String) parameterMap.get("email" + i);
			
			// REGI, LAST_MODI 항목 setting
			dataList = addRegiInfo(dataList, loginVO, regiIp);
			dataList = addLastModiInfo(dataList, loginVO, regiIp);
			
			dataList.add(new DTForm("CLI_IDX", cliIdx));
			dataList.add(new DTForm("PLAN_IDX", planIdx));
			dataList.add(new DTForm("PIC_IDX", picIdx));
			dataList.add(new DTForm("PIC_NM", picNm));
			dataList.add(new DTForm("DEPT_NAME", psitnDept));
			dataList.add(new DTForm("OFCPS", ofcps));
			dataList.add(new DTForm("HFFC_CAREER", hffcCareer));
			dataList.add(new DTForm("TELNO", "PKG_DGUARD.FN_ENC_TELNO('" + telNo +  "')", 1));
			dataList.add(new DTForm("EMAIL", email));
			
			param.put("dataList", dataList);

			
			insertCorp += clinicDctMultiDAO.insert(param);
			
			dataList.clear();
			
		}
		
		return insertCorp;
	}

	/**
	 * 계획서에서 연간훈련계획 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteAndinsertPlanYearTp(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		// 연간훈련계획 정보는 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		List<DTForm> searchList = new ArrayList<DTForm>();							// 삭제조건
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("PLAN_IDX", planIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
		// 이력 남기기
		chgLogMapper.writeDataLog(targetTable, "D", searchList);
		
		// 기존 데이터 삭제하기
		clinicDctMultiDAO.cdelete(delParam);
		
		int insertYearPlan = 0;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 데이터 항목
		param.put("targetTable", targetTable);
		
		// 입력한 HRD담당자 갯수 가져오기
		int yearPlanLength = StringUtil.getInt((String) parameterMap.get("maxYearPlanIdx")); 
		
		for(int i = 1; i <= yearPlanLength; i++) {
			param.put("targetTableIdx", "TR_DTL_IDX");
			
			int trDtlIdx = clinicDctDAO.getNextId(param);
			
			String trmt = (String) parameterMap.get("trMt" + i);
			String trTarget = (String) parameterMap.get("trTarget" + i);
			String trCn = (String) parameterMap.get("trCn" + i);
			String trMby = (String) parameterMap.get("trMby" + i);
			String trMth = (String) parameterMap.get("trMth" + i);
			String trInsttNm = (String) parameterMap.get("trInsttNm" + i);
			String trNmpr = (String) parameterMap.get("trNmpr" + i);
			String trTm = (String) parameterMap.get("trTm" + i);
			String gvrnSportYn = (String) parameterMap.get("gvrnSportYn" + i);
			String remark = (String) parameterMap.get("remark" + i);
			
			// REGI, LAST_MODI 항목 setting
			dataList = addRegiInfo(dataList, loginVO, regiIp);
			dataList = addLastModiInfo(dataList, loginVO, regiIp);
			
			dataList.add(new DTForm("CLI_IDX", cliIdx));
			dataList.add(new DTForm("PLAN_IDX", planIdx));
			dataList.add(new DTForm("TR_DTL_IDX", trDtlIdx));
			dataList.add(new DTForm("TR_MT", trmt));
			dataList.add(new DTForm("TR_TRGET", trTarget));
			dataList.add(new DTForm("TR_CN", trCn));
			dataList.add(new DTForm("TR_MBY", trMby));
			dataList.add(new DTForm("TR_MTH", trMth));
			dataList.add(new DTForm("TRINSTT_NM", trInsttNm));
			dataList.add(new DTForm("TR_NMPR", trNmpr));
			dataList.add(new DTForm("TRTM", trTm));
			dataList.add(new DTForm("GVRN_SPORT_YN", gvrnSportYn));
			dataList.add(new DTForm("REMARKS", remark));
			
			param.put("dataList", dataList);
			
			insertYearPlan += clinicDctMultiDAO.insert(param);
			
			dataList.clear();
			
		}
		
		return insertYearPlan;
	}

	/**
	 * 계획서에서 자체 KPI목표 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteAndinsertPlanKPI(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보

		// 자체 KPI목표는 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		List<DTForm> searchList = new ArrayList<DTForm>();							// 삭제조건
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("PLAN_IDX", planIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
		// 이력 남기기
		chgLogMapper.writeDataLog(targetTable, "D", searchList);
		
		// 기존 데이터 삭제하기
		clinicDctMultiDAO.cdelete(delParam);
		
		int insertKPI = 0;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 데이터 항목
		param.put("targetTable", targetTable);
		
		// 입력한 HRD담당자 갯수 가져오기
		int yearPlanLength = StringUtil.getInt((String) parameterMap.get("maxKpiIdx")); 
		
		for(int i = 1; i <= yearPlanLength; i++) {
			param.put("targetTableIdx", "KPI_IDX");
			
			int kpiIdx = clinicDctDAO.getNextId(param);
			
			String kpiEssntl = (String) parameterMap.get("kpiEssntl" + i);
			String kpiCn = (String) parameterMap.get("kpiCn" + i);
			String kpiGoal = (String) parameterMap.get("kpiGoal" + i);
			
			// REGI, LAST_MODI 항목 setting
			dataList = addRegiInfo(dataList, loginVO, regiIp);
			dataList = addLastModiInfo(dataList, loginVO, regiIp);
			
			dataList.add(new DTForm("CLI_IDX", cliIdx));
			dataList.add(new DTForm("PLAN_IDX", planIdx));
			dataList.add(new DTForm("KPI_IDX", kpiIdx));
			dataList.add(new DTForm("ESSNTL_SE", kpiEssntl));
			dataList.add(new DTForm("KPI_CN", kpiCn));
			dataList.add(new DTForm("KPI_GOAL", kpiGoal));
			
			param.put("dataList", dataList);
			
			insertKPI += clinicDctMultiDAO.insert(param);
			
			dataList.clear();
			
		}
		
		return insertKPI;
	}

	/**
	 * 계획서 최종승인 후 HRD_CLI_PLAN_CHANGE에 insert 하기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public int planToChange(int cliIdx, int planIdx) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("CLI_IDX", cliIdx);
		param.put("PLAN_IDX", planIdx);
		
		return clinicDctDAO.planToChange(param);
	}

	/**
	 * 변경승인 후 HRD_CLI_PLAN에 update하기
	 * @param cliIdx
	 * @param planIdx
	 * @return
	 * @throws Exception
	 */
	@Override
	public int changeToPlan(int cliIdx, int planIdx, int chgIdx) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("CLI_IDX", cliIdx);
		param.put("PLAN_IDX", planIdx);
		param.put("CHG_IDX", chgIdx);
		
		return clinicDctDAO.changeToPlan(param);
	}
	
	/**
	 * [성과관리] 성과보고서에서 연간훈련실시결과 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertResultYearTp(String targetTable, int cliIdx, int resltIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		// 연간훈련계획 정보는 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		List<DTForm> searchList = new ArrayList<DTForm>();							// 삭제조건
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("RESLT_IDX", resltIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
		chgLogMapper.writeDataLog("HRD_CLI_RSLT_TR_SUB","D",searchList);
		// 기존 데이터 삭제하기
		clinicMultiDAO.cdelete(delParam);
		
		int insertYearResult = 0;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 데이터 항목
		param.put("targetTable", targetTable);
		
		// 입력한 HRD담당자 갯수 가져오기
		int yearResultLength = StringUtil.getInt((String) parameterMap.get("maxYearResultIdx")); 
		
		for(int i = 1; i <= yearResultLength; i++) {
			param.put("targetTableIdx", "TR_DTL_IDX");
			
			int trDtlIdx = clinicDAO.getNextId(param);
			
			String trmt = (String) parameterMap.get("trMt" + i);
			String trTarget = (String) parameterMap.get("trTarget" + i);
			String trCn = (String) parameterMap.get("trCn" + i);
			String trMby = (String) parameterMap.get("trMby" + i);
			String trMth = (String) parameterMap.get("trMth" + i);
			String trInsttNm = (String) parameterMap.get("trInsttNm" + i);
			String trNmpr = (String) parameterMap.get("trNmpr" + i);
			String trTm = (String) parameterMap.get("trTm" + i);
			String trStartDate = (String) parameterMap.get("trDate" + i);
			String comNmPr = (String) parameterMap.get("comNmpr" + i);
			String gvrnSportYn = (String) parameterMap.get("gvrnSportYn" + i);
			String remark = (String) parameterMap.get("remark" + i);
			
			// REGI, LAST_MODI 항목 setting
			dataList = addRegiInfo(dataList, loginVO, regiIp);
			dataList = addLastModiInfo(dataList, loginVO, regiIp);
			
			dataList.add(new DTForm("CLI_IDX", cliIdx));
			dataList.add(new DTForm("RESLT_IDX", resltIdx));
			dataList.add(new DTForm("TR_DTL_IDX", trDtlIdx));
			dataList.add(new DTForm("TR_MT", trmt));
			dataList.add(new DTForm("TR_TRGET", trTarget));
			dataList.add(new DTForm("TR_CN", trCn));
			dataList.add(new DTForm("TR_MBY", trMby));
			dataList.add(new DTForm("TR_MTH", trMth));
			dataList.add(new DTForm("TRINSTT_NM", trInsttNm));
			dataList.add(new DTForm("TR_NMPR", trNmpr));
			dataList.add(new DTForm("TRTM", trTm));
			dataList.add(new DTForm("TR_START_DATE", "TO_DATE('" +  trStartDate + "', 'YYYY-MM-DD')", 1));
			dataList.add(new DTForm("COMPL_NMPR", comNmPr));
			dataList.add(new DTForm("GVRN_SPORT_YN", gvrnSportYn));
			dataList.add(new DTForm("REMARKS", remark));
			
			param.put("dataList", dataList);
			
			insertYearResult += clinicMultiDAO.insert(param);
			
			dataList.clear();
			
		}
		
		return insertYearResult;
	}
	
	
	/**
	 * [성과관리] 성과보고서에서 자체 KPI목표 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertResultKPI(String targetTable, int cliIdx, int resltIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보

		// 자체 KPI목표는 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		List<DTForm> searchList = new ArrayList<DTForm>();							// 삭제조건
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("RESLT_IDX", resltIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
		// 기존 데이터 삭제하기
		chgLogMapper.writeDataLog("HRD_CLI_RSLT_KPI","D",searchList);
		clinicMultiDAO.cdelete(delParam);
		
		int insertKPI = 0;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 데이터 항목
		param.put("targetTable", targetTable);
		
		// 입력한 HRD담당자 갯수 가져오기
		int yearPlanLength = StringUtil.getInt((String) parameterMap.get("maxKpiIdx")); 
		
		for(int i = 1; i <= yearPlanLength; i++) {
			param.put("targetTableIdx", "KPI_IDX");
			
			int kpiIdx = clinicDAO.getNextId(param);
			
			String kpiEssntl = (String) parameterMap.get("kpiEssntl" + i);
			String kpiCn = (String) parameterMap.get("kpiCn" + i);
			String kpiGoal = (String) parameterMap.get("kpiGoal" + i);
			String kpiResult = (String) parameterMap.get("kpiResult" + i);
			
			// REGI, LAST_MODI 항목 setting
			dataList = addRegiInfo(dataList, loginVO, regiIp);
			dataList = addLastModiInfo(dataList, loginVO, regiIp);
			
			dataList.add(new DTForm("CLI_IDX", cliIdx));
			dataList.add(new DTForm("RESLT_IDX", resltIdx));
			dataList.add(new DTForm("KPI_IDX", kpiIdx));
			dataList.add(new DTForm("ESSNTL_SE", kpiEssntl));
			dataList.add(new DTForm("KPI_CN", kpiCn));
			dataList.add(new DTForm("KPI_GOAL", kpiGoal));
			dataList.add(new DTForm("KPI_RESULT", kpiResult));
			
			param.put("dataList", dataList);
			
			insertKPI += clinicMultiDAO.insert(param);
			
			dataList.clear();
			
		}
		
		return insertKPI;
	}
	
	/**
	 * [대시보드] 연차별 클리닉 기업 전체 리스트(본부 - 전체 지부지사 / 지부지사 부장 - 본인 소속기관 전체 / 지부지사 주치의 - 본인이 전담주치의인 기업)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Object> getAllCliCorpCountList(Map<String, Object> param) {
		return clinicDctDAO.getAllCliCorpCountList(param);
	}
	
	/**
	 * [대시보드] 연차별 나의 클리닉 기업 전체 리스트(지부지사 부장, 지부지사 주치의만 사용)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Object> getMyCliCorpCountList(Map<String, Object> param) {
		return clinicDctDAO.getMyCliCorpCountList(param);
	}
	
	/**
	 * [대시보드] 연차별 중도포기한 클리닉 기업 전체 리스트(본부 - 전체 지부지사 / 지부지사 부장 - 본인 소속기관 전체 / 지부지사 주치의 - 본인이 전담주치의인 기업)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Object> getAllDropCliCorpCountList(Map<String, Object> param) {
		return clinicDctDAO.getAllDropCliCorpCountList(param);
	}

	/**
	 * [대시보드] 연차별 중도포기한 나의 클리닉 기업 전체 리스트(지부지사 부장, 지부지사 주치의만 사용)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Object> getMyDropCliCorpCountList(Map<String, Object> param) {
		return clinicDctDAO.getMyDropCliCorpCountList(param);
	}
	
	/**
	 * [대시보드] 클리닉 기업 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Object> getCliCorpList(Map<String, Object> param) {
		return clinicDctDAO.getCliCorpList(param);
	}

	@Override
	public int getCliCorpCount(Map<String, Object> param) {
		return clinicDctDAO.getCliCorpCount(param);
	}

	/**
	 * [비용관리] 지원금 신청내역 입력하기
	 * @param targetTable
	 * @param sportIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteAndinsertSptSub(String targetTable, int cliIdx, int sportIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		// 지원금 신청내역 정보는 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		List<DTForm> searchList = new ArrayList<DTForm>();							// 삭제조건
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("SPORT_IDX", sportIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
		chgLogMapper.writeDataLog("HRD_CLI_SPT_SUB","D",searchList);
		// 기존 데이터 삭제하기
		clinicMultiDAO.cdelete(delParam);
		
		int insertSupportSub = 0;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 데이터 항목
		param.put("targetTable", targetTable);
		
		// 신청내역 총 갯수 가져오기
		int supportLength = StringUtil.getInt((String) parameterMap.get("maxIdx")); 
		
		for(int i = 1; i <= supportLength; i++) {
			
			param.put("searchList", searchList);
			param.put("targetTableIdx", "DTL_SN");
			
			int dtlSnIdx = clinicDAO.getNextIdWithSearchList(param);
			
			// 필수/선택 구분
			String essntlSe = "";
			// 지원금 신청여부
			String reqstYn = "Y";
			// 지원항목 코드
			String sportItemCd = (String) parameterMap.get("sportItemCd" + i);
			// 실비 지원 여부
			String acexpYn = (String) parameterMap.get("acexpYn" + i);
			// 계획서, 성과보고서, 활동일지 식별색인
			String acmsltIdx = (String) parameterMap.get("acmsltIdx" + i);
			// 신청금액
			String sptPay = (String) parameterMap.get("sptPay" + i);
			
			if(sportItemCd != null && !sportItemCd.isEmpty()) {				
				if(sportItemCd.equals("01") || sportItemCd.equals("05") || sportItemCd.equals("04")) {
					// 훈련계획 수립, 성과보고서, HRD 역량개발은 필수
					essntlSe = "Y";
				} else {
					essntlSe = "N";
				}
				
				// REGI, LAST_MODI 항목 setting
				dataList = addRegiInfo(dataList, loginVO, regiIp);
				dataList = addLastModiInfo(dataList, loginVO, regiIp);
				
				dataList.add(new DTForm("CLI_IDX", cliIdx));
				dataList.add(new DTForm("SPORT_IDX", sportIdx));
				dataList.add(new DTForm("DTL_SN", dtlSnIdx));
				dataList.add(new DTForm("SPORT_ITEM_CD", sportItemCd));
				dataList.add(new DTForm("SPORT_ITEM_IDX", acmsltIdx));
				dataList.add(new DTForm("ESSNTL_SE", essntlSe));
				dataList.add(new DTForm("ACEXP_SPORT_YN", acexpYn));
				dataList.add(new DTForm("SPT_PAY", sptPay));
				dataList.add(new DTForm("SPRMNY_REQST_YN", reqstYn));
				
				param.put("dataList", dataList);
				
				insertSupportSub += clinicMultiDAO.insert(param);
				
				dataList.clear();
			}
			
		}
		
		return insertSupportSub;
	}

}