package rbs.modules.clinic.service.impl;

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
import rbs.modules.clinic.mapper.ClinicFileMapper;
import rbs.modules.clinic.mapper.ClinicMapper;
import rbs.modules.clinic.mapper.ClinicMultiMapper;
import rbs.modules.clinic.service.ClinicService;

/**
 * 능력개발 클리닉(기업)모듈에 관한 구현클래스를 정의한다.
 * @author 이동근, 권주미
 *ㅂ
 */
@Service("clinicService")
public class ClinicServiceImpl extends EgovAbstractServiceImpl implements ClinicService {

	@Resource(name="clinicMapper")
	private ClinicMapper clinicDAO;
	
	@Resource(name="clinicFileMapper")
	private ClinicFileMapper clinicFileDAO;
	
	@Resource(name="clinicMultiMapper")
	private ClinicMultiMapper clinicMultiDAO;
	
	@Resource(name="chgLogMapper")
	private ChgLogMapper chgLogMapper;
	
	List<DTForm> dataList;
	
	/*공통코드 시작*/
	/**
	 * 해당 기업의 가장 최근 기초진단지 식별번호를 가져온다
	 * @param BPL_NO
	 * @return bsc
	 * @throws Exception
	 */
	@Override
	public long getMaxBsc(String BPL_NO) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("BPL_NO", BPL_NO);
		return clinicDAO.getMaxBsc(param);
	}
	
	/**
	 * 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalCount(String targetTable, Map<String, Object> param) {
    	param.put("targetTable", targetTable);
    	return clinicDAO.getTotalCount(param);
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
		return clinicDAO.getList(param);
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
		DataMap viewDAO = clinicDAO.getView(param);
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
		DataMap viewDAO = clinicDAO.getFileView(param);
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
		DataMap viewDAO = clinicFileDAO.getFileView(param);
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
		return clinicDAO.updateFileDown(param);
		
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
		return clinicFileDAO.updateFileDown(param);
		
	}

	/**
	 * 수정 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getModify(String targetTable, Map<String, Object> param) {
    	param.put("targetTable", targetTable);
		DataMap viewDAO = clinicDAO.getModify(param);
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
    	return clinicDAO.getAuthCount(param);
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
    	return clinicDAO.getDeleteCount(param);
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
		return clinicDAO.getDeleteList(param);
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
		return clinicDAO.delete(param);
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
		return clinicDAO.restore(param);
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
		deleteMultiFileList = clinicFileDAO.getList(param);
		// 2.2 삭제할 file(단일항목) select
		List deleteFileColumnList = ModuleUtil.getFileObjectList(items, itemOrder);
		if(deleteFileColumnList != null) {
			param.put("columnList", deleteFileColumnList);
			
			deleteFileList = clinicDAO.getFileList(param);
		}
		
		// 3. delete
		int result = clinicDAO.cdelete(param);
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
				resultHashMap = clinicFileDAO.getMapList(param);
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
					resultHashMap.put(itemId, clinicMultiDAO.getList(param));
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
				resultHashMap = clinicMultiDAO.getMapList(param);
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
	 * 클리닉 신청여부
	 * @param BPL_NO
	 * @return isCli
	 */
    @Override
	public int getIsCli(String BPL_NO) {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("fnIdx", 0);
    	param.put("BPL_NO", BPL_NO);
    	return clinicDAO.getIsCli(param);
    }
    	
	/**
	 * 능력개발클리닉 기업정보
	 * @param fnIdx
	 * @param param
	 * @return
	 */	
	@Override
	public DataMap getCorpInfo(int fnIdx, Map<String, Object> param) {
		DataMap corpInfo = clinicDAO.getCorpInfo(param);
		return corpInfo;
	}	
	
	/**
	 * 등록자 정보 저장
	 * @param dataList
	 * @param loginVO
	 * @param regiIp
	 * @return dataList
	 */
	public List<Object> getInsttList(Map<String, Object> param) {
		return clinicDAO.getInsttList(param);
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
		
	private int insertFile(HashMap<String, Object> dataMap, String targetTable, String targetTableIdx, int cliIdx, int keyIdx, String fileRealPath) {
		
		int result = 0;
		
		// 3.3.3 multi file 삭제
		List fileDeleteSearchList = StringUtil.getList(dataMap.get("fileDeleteSearchList"));
		if(fileDeleteSearchList != null) {

			List<Object> deleteMultiFileList = new ArrayList<Object>();
			int fileDataSize = fileDeleteSearchList.size();
			for(int i = 0 ; i < fileDataSize ; i ++) {
				Map<String, Object> fileParam = (HashMap)fileDeleteSearchList.get(i);
				fileParam.put("targetTable", targetTable);
				fileParam.put("CLI_IDX", cliIdx);
				fileParam.put("targetTableIdx", targetTableIdx);
				fileParam.put("KEY_IDX", keyIdx);
				// 6.1 삭제목록 select
				List<Object> deleteFileList2 = clinicFileDAO.getList(fileParam);
				if(deleteFileList2 != null) deleteMultiFileList.addAll(deleteFileList2);
				// 6.2 DB 삭제
				result = clinicFileDAO.cdelete(fileParam);
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
				fileParam.put("CLI_IDX", cliIdx);
				fileParam.put("targetTableIdx", targetTableIdx);
				fileParam.put("KEY_IDX", keyIdx);
				int fleIdx = clinicFileDAO.getNextId(fileParam);
				fileParam.put("FLE_IDX", fleIdx);
				result = clinicFileDAO.insert(fileParam);
			}
		}
		
		return result;
		
	}	
	

	/**
	 * HRD_CLI에서 최종승인된 CLI_IDX(식별색인) select
	 * @param bplNo
	 * @param param
	 * @return
	 */
	@Override
	public int getCurrentCliIdx(String bplNo) throws Exception {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("BPL_NO", bplNo);
		return clinicDAO.getCurrentCliIdx(param);
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
    	return clinicDAO.getCliIdx(param);
	}
	
	
	/**
	 * 사업장관리번호로 현재 기업의 클리닉 시작날짜 가져오기
	 * @param bplNo
	 * @param param
	 * @return
	 */
	@Override
	public String getCliValidStartDate(String bplNo) throws Exception {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("BPL_NO", bplNo);
		return clinicDAO.getCliValidStartDate(param);
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

		// dataList에 LAST_MODI 정보 넣는 함수
		dataList = addLastModiInfo(dataList, loginVO, regiIp);
		
		dataList.add(new DTForm("CONFM_STATUS", confmStatus));
		
		
		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("targetTable", targetTable);
		

		
		int updateReq = clinicDAO.update(param);
		
		return updateReq;
	}
	
	/**
	 * HRD_CLI_***_CONFM 새로운 승인상태 등록(모든 메뉴에서 승인상태를 업데이트할 때 동일하게 사용)
	 * @param uploadModulePath
	 * @param fnIdx
	 * @param cliList
	 * @param doctorList
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insertConfm(String targetTable, String insertColumnKey, String targetColunm, String confmStatus, int cliIdx, int keyIdx, String regiIp) throws Exception {

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
		param.put("targetTableIdx", targetColunm);
		
		// 승인 식별색인 가져오기
		int confmIdx = clinicDAO.getNextId(param);
    	
    	dataList.add(new DTForm("CLI_IDX", cliIdx));
    	dataList.add(new DTForm(insertColumnKey, keyIdx));
    	dataList.add(new DTForm("CONFM_IDX", confmIdx));
    	dataList.add(new DTForm("CONFM_STATUS", confmStatus));

    	param.put("dataList", dataList);
    	
    	int confmResult = clinicDAO.insertConfm(param);
		
		return confmResult;
	}
		
	/*공통코드 끝*/	
	
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
		
		int cliIdx = (int) parameterMap.get("cliIdx");
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("REQ_IDX", reqIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
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
				
				insertChk += clinicMultiDAO.insert(param);
				
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
		
		int cliIdx = (int) parameterMap.get("cliIdx");
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("REQ_IDX", reqIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
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
				
				insertChk += clinicMultiDAO.insert(param);
				
				dataList.clear();
			}
		}
		
		return insertChk;
	}
	
	/**
	 * [신청관리] 기업 자가 확인 체크리스트 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getCheckList(int fnIdx, Map<String, Object> param) {
		return clinicDAO.getCheckList(param);
	}

	/**
	 * [신청관리] 기업 자가 확인 체크리스트 답변 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getCheckListAnswer(int fnIdx, Map<String, Object> param) {
		return clinicDAO.getCheckListAnswer(param);
	}
		
	/**
	 * [신청관리] 클리닉 신청서 등록 여부
	 * @param BPL_NO
	 * @return isCli
	 */
    @Override
	public int getIsReq(String BPL_NO) {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("BPL_NO", BPL_NO);
    	return clinicDAO.getIsReq(param);
    }
    
	/**
	 * [신청관리] 능력개발클리닉 신청(HRD_CLI) 테이블 insert 처리(최초 신청/ 최초 임시저장)
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
	public int insertCli(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray cliInsertColumnOrder) throws Exception  {

		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(cliInsertColumnOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		//테이블
		String targetTable = "HRD_CLI";
		//테이블키
		String targetTableIdx = "CLI_IDX";
		
		param.put("targetTable", targetTable);
		param.put("targetTableIdx", targetTableIdx);
		
		// 로그인한 회원의 정보 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();				
		
		// 마지막 CLI_IDX의 다음 값(insert준비용.)
		int keyIdx = clinicDAO.getNextCliIdx();

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, cliInsertColumnOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		dataList.add(new DTForm(targetTableIdx, keyIdx));	
		

		dataList = addRegiInfo(dataList, loginVO, regiIp);
		dataList = addLastModiInfo(dataList, loginVO, regiIp);		
    	
		param.put("dataList", dataList);

		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = clinicDAO.insert(param);
				
		
		return result;
	}
	
	/**
	 * [신청관리] 능력개발클리닉 신청(HRD_CLI) 테이블 update 처리(최초 신청/ 최초 임시저장)
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateCli(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo,JSONObject items, JSONArray cliUpdateColumnOrder) throws Exception {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(cliUpdateColumnOrder)) return -1;
		
		// 로그인한 회원의 정보 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();				
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
    	
		//cliIdx 세팅
		int cliIdx =  (int) parameterMap.get("cliIdx");
		
		/* 
		 * 기업이 최초 클리닉 신청을 할 때 A지부지사로 신청을 했음 > 전담주치의가 지정되고 접수 후 반려 됐을 때 기업이 클리닉 지부지사를 B지부지사로 바꿀 수 있음(HRD부서 주소 변경)
		 * 이때 기업이 바꾼 B지부지사로 새로운 전담주치의를 지정하기 위해서 기존의 전담주치의 정보를 지운다.
		*/ 
		// 기존의 클리닉 지부지사 가져오기
		param.put("CLI_IDX", cliIdx);
		int cliInsttIdx = clinicDAO.getCliInstt(param);
		
		// 기업이 입력한 클리닉 지부지사
		int newInsttIdx = StringUtil.getInt((String) parameterMap.get("insttIdx"));
		
		// 기존의 클리닉 지부지사와 기업이 입력한 클리닉 지부지사가 다르면 기존의 주치의 정보 지우기(새로운 지부지사의 전담주치의를 지정하기 위해서)
		if(cliInsttIdx != newInsttIdx) {
			dataList.add(new DTForm("DOCTOR_IDX", ""));
		}
		
		param.clear();
		
		// 1. 검색조건 setting
		searchList.add(new DTForm("CLI_IDX", cliIdx));	
		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, cliUpdateColumnOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);

		// LAST_MODI 항목 setting
		addLastModiInfo(dataList, loginVO, regiIp);
		
    	param.put("dataList", dataList);
    	param.put("searchList", searchList);

    	param.put("targetTable", "HRD_CLI");
    	
    	// 3. DB 저장
    	
		/*변경 이력 남기기*/
    	chgLogMapper.writeDataLog("HRD_CLI","U",searchList);
    	
    	// 3.1 기본 정보 테이블
		int cliUpdate = clinicDAO.update(param);
		
		return cliUpdate;
		
	}
	
	/**
	 * [신청관리] 능력개발클리닉 신청서(HRD_CLI_REQ) 테이블 insert 처리(최초 신청/ 최초 임시저장)
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @param status(저장할 상태값)
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int insertReqAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray reqInsertColumnOrder, String status) throws Exception  {

		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(reqInsertColumnOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		//굳이 변수로 빼니까 더 헷갈린다...
//		//테이블
//		String targetTable = "HRD_CLI_REQ";
//		//테이블 키
//		String targetTableIdx = "REQ_IDX";					
		
		// 로그인한 회원의 정보 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
				
		
		param.put("targetTable", "HRD_CLI_REQ");
		param.put("targetTableIdx", "REQ_IDX");
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
				
		param.put("BPL_NO", BPL_NO);
		
		//HRD_CLI테이블에서 가져온 BPL_NO에 해당하는 마지막 CLI_IDX를 가져온다
		int cliIdx = clinicDAO.getCliIdx(param);	
		//HRD_CLI_REQ테이블에서 마지막 REQ_IDX의 다음값 가져온다 
		int keyIdx = clinicDAO.getNextId(param);

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, reqInsertColumnOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		System.out.println("dataMap === " + dataMap);
		String telNo = (String)parameterMap.get("telNo");
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		dataList.add(new DTForm("CLI_IDX", cliIdx));	//cli_idx와 함께 인서트하기 위해 키 하드코딩
		dataList.add(new DTForm("REQ_IDX", keyIdx));
		dataList.add(new DTForm("CONFM_STATUS", status));
		dataList.add(new DTForm("TELNO", "PKG_DGUARD.FN_ENC_TELNO('" + telNo +  "')", 1));

		dataList = addRegiInfo(dataList, loginVO, regiIp);
		dataList = addLastModiInfo(dataList, loginVO, regiIp);		
		  	
		param.put("dataList", dataList);

		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = clinicDAO.insert(param);
		if(result > 0){			
			insertFile(dataMap, "HRD_CLI_REQ_FILE", "REQ_IDX", cliIdx, keyIdx, fileRealPath);
		}
		
		parameterMap.put("cliIdx", cliIdx);
		//기업 자가 체크리스트, 기업 선정 심사표 update
		deleteAndinsertReqChk("HRD_CLI_CHECKLIST_ANSWER", keyIdx, regiIp, parameterMap);
//		deleteAndinsertReqJdg("HRD_CLI_JDGMNTAB_ANSWER", keyIdx, regiIp, parameterMap);
		
		// 체크리스트 개수 가져오기
		StringUtil.getInt((String) parameterMap.get("checkListLength"));
		// 체크리스트 버전 가져오기
		StringUtil.getInt((String) parameterMap.get("checkVer"));		
		
		if(status.equals("10")) {
			insertConfm("HRD_CLI_REQ_CONFM", "REQ_IDX", "CONFM_IDX", status, cliIdx, keyIdx, regiIp);
		}

		return result;
	}
	
	/**
	 * [신청관리] 능력개발클리닉 신청서(HRD_CLI_REQ) 테이블 update 처리 (이미 등록된 신청서가 있는 경우)
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
	public int updateReqAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, String reqIdx, String status) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray reqUpdateColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "req_update_column_order");
		
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(reqUpdateColumnOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
    	
		String telNo = (String)parameterMap.get("telNo");
		
		//cliIdx 세팅
		int cliIdx =  (int) parameterMap.get("cliIdx");
		
		// 1. 검색조건 setting
		searchList.add(new DTForm("REQ_IDX", reqIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));		
		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, reqUpdateColumnOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		dataList.add(new DTForm("CONFM_STATUS", status));
		dataList.add(new DTForm("TELNO", "PKG_DGUARD.FN_ENC_TELNO('" + telNo +  "')", 1));
		if(itemDataList != null) dataList.addAll(itemDataList);

		// LAST_MODI 항목 setting
		addLastModiInfo(dataList, loginVO, regiIp);
		
    	param.put("dataList", dataList);
    	param.put("searchList", searchList);

    	param.put("targetTable", "HRD_CLI_REQ");
    	
    	// 3. DB 저장
    	
		/*변경 이력 남기기*/
    	chgLogMapper.writeDataLog("HRD_CLI_REQ","U",searchList);
//		int logResult = chgLogMapper.writeDataLog("HRD_CLI_REQ","U","WHERE CLI_IDX = '1' AND REQ_IDX = '1'");
    	
    	// 3.1 기본 정보 테이블
		int reqUpdate = clinicDAO.update(param);
		if(reqUpdate > 0){			
			insertFile(dataMap, "HRD_CLI_REQ_FILE", "REQ_IDX", cliIdx, StringUtil.getInt(reqIdx), fileRealPath);
		}
		parameterMap.put("cliIdx", cliIdx);
		//기업 자가 체크리스트
		deleteAndinsertReqChk("HRD_CLI_CHECKLIST_ANSWER", StringUtil.getInt((String)reqIdx), regiIp, parameterMap);
		
		if(status.equals("10")) {
			insertConfm("HRD_CLI_REQ_CONFM", "REQ_IDX", "CONFM_IDX", status, cliIdx, StringUtil.getInt((String)reqIdx), regiIp);
		}
		
		return reqUpdate;
	}

	/**
	 * [계획관리] 계획서에서 지원항목 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteAndinsertPlanSub(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap, int chgIdx) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		// HRD담당자 정보는 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		List<DTForm> searchList = new ArrayList<DTForm>();							// 삭제조건
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("PLAN_IDX", planIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		if(chgIdx != 0) {
			searchList.add(new DTForm("CHG_IDX", chgIdx));
		}
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
		// 기존 데이터 삭제하기
		clinicMultiDAO.cdelete(delParam);
		
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
			if(chgIdx != 0) {
				dataList.add(new DTForm("CHG_IDX", chgIdx));
			}
			dataList.add(new DTForm("SPORT_ITEM_CD", itemCd));
			dataList.add(new DTForm("ESSNTL_SE", essntlSe));
			dataList.add(new DTForm("REQST_YN", reqstYn));
			
			param.put("dataList", dataList);
			
			insertSub += clinicMultiDAO.insert(param);
			
			dataList.clear();
				
			
		}
		
		return insertSub;
	}
	
	/**
	 * [계획관리] 계획서에서 HRD담당자 정보 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteAndinsertPlanCorp(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap, int chgIdx) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		// HRD담당자 정보는 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		List<DTForm> searchList = new ArrayList<DTForm>();							// 삭제조건
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("PLAN_IDX", planIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		if(chgIdx != 0) {
			searchList.add(new DTForm("CHG_IDX", chgIdx));
		}
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
		// 기존 데이터 삭제하기
		clinicMultiDAO.cdelete(delParam);
		
		int insertCorp = 0;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 데이터 항목
		param.put("targetTable", targetTable);
		
		// 입력한 HRD담당자 갯수 가져오기
		int corpLength = StringUtil.getInt((String) parameterMap.get("maxSubIdx")); 
		
		for(int i = 1; i <= corpLength; i++) {
			param.put("targetTableIdx", "PIC_IDX");
			
			int picIdx = clinicDAO.getNextId(param);
			
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
			if(chgIdx != 0) {
				dataList.add(new DTForm("CHG_IDX", chgIdx));
			}
			dataList.add(new DTForm("PIC_IDX", picIdx));
			dataList.add(new DTForm("PIC_NM", picNm));
			dataList.add(new DTForm("DEPT_NAME", psitnDept));
			dataList.add(new DTForm("OFCPS", ofcps));
			dataList.add(new DTForm("HFFC_CAREER", hffcCareer));
			dataList.add(new DTForm("TELNO", "PKG_DGUARD.FN_ENC_TELNO('" + telNo +  "')", 1));
			dataList.add(new DTForm("EMAIL", email));
			
			param.put("dataList", dataList);
			
			insertCorp += clinicMultiDAO.insert(param);
			
			dataList.clear();
			
		}
		
		return insertCorp;
	}

	/**
	 * [계획관리] 계획서에서 연간훈련계획 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteAndinsertPlanYearTp(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap, int chgIdx) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		// 연간훈련계획 정보는 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		List<DTForm> searchList = new ArrayList<DTForm>();							// 삭제조건
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("PLAN_IDX", planIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		if(chgIdx != 0) {
			searchList.add(new DTForm("CHG_IDX", chgIdx));
		}
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
		// 기존 데이터 삭제하기
		clinicMultiDAO.cdelete(delParam);
		
		int insertYearPlan = 0;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 데이터 항목
		param.put("targetTable", targetTable);
		
		// 입력한 HRD담당자 갯수 가져오기
		int yearPlanLength = StringUtil.getInt((String) parameterMap.get("maxYearPlanIdx")); 
		
		for(int i = 1; i <= yearPlanLength; i++) {
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
			String gvrnSportYn = (String) parameterMap.get("gvrnSportYn" + i);
			String remark = (String) parameterMap.get("remark" + i);
			
			// REGI, LAST_MODI 항목 setting
			dataList = addRegiInfo(dataList, loginVO, regiIp);
			dataList = addLastModiInfo(dataList, loginVO, regiIp);
			
			dataList.add(new DTForm("CLI_IDX", cliIdx));
			dataList.add(new DTForm("PLAN_IDX", planIdx));
			if(chgIdx != 0) {
				dataList.add(new DTForm("CHG_IDX", chgIdx));
			}
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
			
			insertYearPlan += clinicMultiDAO.insert(param);
			
			dataList.clear();
			
		}
		
		return insertYearPlan;
	}

	/**
	 * [계획관리] 계획서에서 자체 KPI목표 입력하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public int deleteAndinsertPlanKPI(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap, int chgIdx) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보

		// 자체 KPI목표는 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		List<DTForm> searchList = new ArrayList<DTForm>();							// 삭제조건
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("PLAN_IDX", planIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));
		if(chgIdx != 0) {
			searchList.add(new DTForm("CHG_IDX", chgIdx));
		}
		
		delParam.put("searchList", searchList);
		delParam.put("targetTable", targetTable);
		
		// 기존 데이터 삭제하기
		clinicMultiDAO.cdelete(delParam);
		
		int insertKPI = 0;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 데이터 항목
		param.put("targetTable", targetTable);
		
		// 입력한 HRD담당자 갯수 가져오기
		int kpiLength = StringUtil.getInt((String) parameterMap.get("maxKpiIdx")); 
		
		for(int i = 1; i <= kpiLength; i++) {
			param.put("targetTableIdx", "KPI_IDX");
			
			int kpiIdx = clinicDAO.getNextId(param);
			
			String kpiEssntl = (String) parameterMap.get("kpiEssntl" + i);
			String kpiCn = (String) parameterMap.get("kpiCn" + i);
			String kpiGoal = (String) parameterMap.get("kpiGoal" + i);
			
			// REGI, LAST_MODI 항목 setting
			dataList = addRegiInfo(dataList, loginVO, regiIp);
			dataList = addLastModiInfo(dataList, loginVO, regiIp);
			
			dataList.add(new DTForm("CLI_IDX", cliIdx));
			dataList.add(new DTForm("PLAN_IDX", planIdx));
			if(chgIdx != 0) {
				dataList.add(new DTForm("CHG_IDX", chgIdx));
			}
			dataList.add(new DTForm("KPI_IDX", kpiIdx));
			dataList.add(new DTForm("ESSNTL_SE", kpiEssntl));
			dataList.add(new DTForm("KPI_CN", kpiCn));
			dataList.add(new DTForm("KPI_GOAL", kpiGoal));
			
			param.put("dataList", dataList);
			
			insertKPI += clinicMultiDAO.insert(param);
			
			dataList.clear();
			
		}
		
		return insertKPI;
	}

	/**
	 * [계획관리] 현재 cliIdx로 plan등록 여부 가져오기
	 * @param cliIdx
	 * @param param
	 * @return
	 */
	@Override
	public int getIsPlan(int cliIdx) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
    	param.put("CLI_IDX", cliIdx);
    	return clinicDAO.getIsPlan(param);
	}
	
	/**
	 * [성과관리] 현재 cliIdx로 result등록 여부 가져오기
	 * @param cliIdx
	 * @param param
	 * @return
	 */
	@Override
	public int getIsResult(int cliIdx) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
    	param.put("CLI_IDX", cliIdx);
    	return clinicDAO.getIsResult(param);
	}
	/**
	 * [비용청구] 현재 cliIdx로 지원금 신청서 등록 여부 가져오기
	 * @param cliIdx
	 * @param param
	 * @return
	 */
	@Override
	public int getIsSupport(int cliIdx) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("CLI_IDX", cliIdx);
		return clinicDAO.getIsSupport(param);
	}
	
	/**
	 * 특정 메뉴에서 등록을 할 때 이전 단계가 최종승인이 되었는지 확인
	 * @param cliIdx
	 * @param param
	 * @return
	 */
	@Override
	public int getIsApprove(String targetTable, int cliIdx) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("targetTable", targetTable);
    	param.put("CLI_IDX", cliIdx);
    	return clinicDAO.getIsApprove(param);
	}
	
	/**
	 * [계획관리] 능력개발클리닉 훈련계획서(HRD_CLI_PLAN) 테이블 insert 처리(최초 신청/ 최초 임시저장)
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @param cliIdx
	 * @param status(저장할 상태값)
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int insertPlanAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx , String status) throws Exception  {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray planInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "plan_insert_column_order");
		
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(planInsertColumnOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 저장항목
						
		
		// 로그인한 회원의 정보 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
				
		
		param.put("targetTable", "HRD_CLI_PLAN");
		param.put("targetTableIdx", "PLAN_IDX");
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
				
		param.put("BPL_NO", BPL_NO);
		
		//HRD_CLI_PLAN테이블에서 마지막 PLAN_IDX의 다음값 가져온다 
		int keyIdx = clinicDAO.getNextId(param);

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, planInsertColumnOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		dataList.add(new DTForm("CLI_IDX", cliIdx));	//cli_idx와 함께 인서트하기 위해 키 하드코딩
		dataList.add(new DTForm("PLAN_IDX", keyIdx));
		dataList.add(new DTForm("CONFM_STATUS", status));

		dataList = addRegiInfo(dataList, loginVO, regiIp);
		dataList = addLastModiInfo(dataList, loginVO, regiIp);		
		  	
		param.put("dataList", dataList);
		

		// 3. DB 저장
		// 3.1 기본 정보 테이블		
		int result = clinicDAO.insert(param);
		
		if(status.equals("10")) {
			insertConfm("HRD_CLI_PLAN_CONFM", "PLAN_IDX", "CONFM_IDX", status, cliIdx, keyIdx, regiIp);
		}
//		public int deleteAndinsertPlanSub(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap)
		result = deleteAndinsertPlanSub("HRD_CLI_PLAN_SUB", cliIdx, keyIdx, regiIp, parameterMap, 0);
		result = deleteAndinsertPlanCorp("HRD_CLI_PLAN_CORP_PIC", cliIdx, keyIdx, regiIp, parameterMap, 0);
		result = deleteAndinsertPlanYearTp("HRD_CLI_PLAN_TR_SUB", cliIdx, keyIdx, regiIp, parameterMap, 0);
		result = deleteAndinsertPlanKPI("HRD_CLI_PLAN_KPI", cliIdx, keyIdx, regiIp, parameterMap, 0);
		
		return result;
	}
	

	/**
	 * [계획관리] 능력개발클리닉 훈련계획서변경(HRD_CLI_PLAN_CHANGE) 테이블 insert 처리(최초 신청과 동일하지만 변경요청에 대한 건이 insert된다)
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @param cliIdx
	 * @param status(저장할 상태값)
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int insertPlanChangeAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx , String status) throws Exception  {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray planInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "plan_insert_column_order");
		
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(planInsertColumnOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 저장항목
						
		
		// 로그인한 회원의 정보 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
				
		
		param.put("targetTable", "HRD_CLI_PLAN_CHANGE");
		param.put("targetTableIdx", "PLAN_IDX");
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
				
		param.put("BPL_NO", BPL_NO);

		
		//HRD_CLI_PLAN테이블에서 가져온 plan_idx를 설정한다 
		int keyIdx = StringUtil.getInt((String) parameterMap.get("planIdx"));
		
		
		//HRD_CLI_PLAN_CHG테이블에서 조건에 해당하는 CHG_IDX의 다음값을 가져온다.
		param.put("CLI_IDX", cliIdx);
		param.put("PLAN_IDX", keyIdx);
		int chgIdx = clinicDAO.getNextChgId(param);
		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, planInsertColumnOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		dataList.add(new DTForm("CLI_IDX", cliIdx));	//cli_idx와 함께 인서트하기 위해 키 하드코딩
		dataList.add(new DTForm("PLAN_IDX", keyIdx));					
		dataList.add(new DTForm("CONFM_STATUS", status));
		dataList.add(new DTForm("CHG_IDX", chgIdx));

		dataList = addRegiInfo(dataList, loginVO, regiIp);
		dataList = addLastModiInfo(dataList, loginVO, regiIp);		
		  	
		param.put("dataList", dataList);
		

		// 3. DB 저장
		// 3.1 기본 정보 테이블		
		int result = clinicDAO.insert(param);
		
		if(status.equals("10")) {
			insertConfm("HRD_CLI_PLAN_CHANGE_SUB", "PLAN_IDX", "CONFM_IDX", status, cliIdx, keyIdx, regiIp);
		}
		
		deleteAndinsertPlanSub("HRD_CLI_PLAN_CHANGE_SUB", cliIdx, keyIdx, regiIp, parameterMap, chgIdx);
		deleteAndinsertPlanCorp("HRD_CLI_PLAN_CHANGE_CORP_PIC", cliIdx, keyIdx, regiIp, parameterMap, chgIdx);
		deleteAndinsertPlanYearTp("HRD_CLI_PLAN_CHANGE_TR_SUB", cliIdx, keyIdx, regiIp, parameterMap, chgIdx);
		deleteAndinsertPlanKPI("HRD_CLI_PLAN_CHANGE_KPI", cliIdx, keyIdx, regiIp, parameterMap, chgIdx);
		

		return result;
	}
	
	/**
	 * [계획관리] 능력개발클리닉 훈련계획서(HRD_CLI_PLAN) 테이블 update 처리 (이미 등록된 신청서가 있는 경우)
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
	public int updatePlanAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx, String status) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray planUpdateColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "plan_update_column_order");
		
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(planUpdateColumnOrder)) return -1;
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		

//		int planIdx = (int) parameterMap.get("planIdx");
		int planIdx = StringUtil.getInt((String) parameterMap.get("planIdx"));		
		// 1. 검색조건 setting
//		searchList.add(new DTForm("PLAN_IDX", planIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));		
		
		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, planUpdateColumnOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		dataList.add(new DTForm("CONFM_STATUS", status));
		if(itemDataList != null) dataList.addAll(itemDataList);

		// LAST_MODI 항목 setting
		addLastModiInfo(dataList, loginVO, regiIp);
		
    	param.put("dataList", dataList);
    	param.put("searchList", searchList);

    	param.put("targetTable", "HRD_CLI_PLAN");
    	
    	// 3. DB 저장
    	// 3.1 기본 정보 테이블
		int planUpdate = clinicDAO.update(param);
		
		if(status.equals("10")) {
			insertConfm("HRD_CLI_PLAN_CONFM", "PLAN_IDX", "CONFM_IDX", status, cliIdx, planIdx, regiIp);
		}
		
		deleteAndinsertPlanSub("HRD_CLI_PLAN_SUB", cliIdx, planIdx, regiIp, parameterMap, 0);
		deleteAndinsertPlanCorp("HRD_CLI_PLAN_CORP_PIC", cliIdx, planIdx, regiIp, parameterMap, 0);
		deleteAndinsertPlanYearTp("HRD_CLI_PLAN_TR_SUB", cliIdx, planIdx, regiIp, parameterMap, 0);
		deleteAndinsertPlanKPI("HRD_CLI_PLAN_KPI", cliIdx, planIdx, regiIp, parameterMap, 0);
		

//		parameterMap.put("cliIdx", cliIdx);
		
		return planUpdate;
	}
	

	@Override
	public List<Object> getList(String targetTable, String targetTableIdx, int targetTableIdxValue, String orderColumn) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();		
		
		param.put("targetTable", targetTable);
		param.put("orderColumn", orderColumn);
		
		searchList.add(new DTForm(targetTableIdx, targetTableIdxValue));
		param.put("searchList", searchList);
		
		List<Object> resultList = clinicMultiDAO.getList(param);
		return resultList;
	}

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
	@Override
	public int insertAcmsltAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx, String status) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, "writeproc_order");
		
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색항목
		dataList = new ArrayList<DTForm>();							// 저장항목
						
		// 로그인한 회원의 정보 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		
				
		// cliIdx를 기준으로 가장 최근의 planIdx 가져오기
		searchList.add(new DTForm("A.CLI_IDX", cliIdx));
		param.put("searchList", searchList);
		
		param.put("targetTable", "HRD_CLI_PLAN");
		param.put("targetTableIdx", "PLAN_IDX");
		
		int planIdx = clinicDAO.getMaxIdx(param);
		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		String startDate = (String) parameterMap.get("startDate");
		String endDate = (String) parameterMap.get("endDate");
		
		param.put("targetTable", "HRD_CLI_ACMSLT");
		param.put("targetTableIdx", "ACMSLT_IDX");
		
		// 실적식별색인 setting
		int acmsltIdx = clinicDAO.getNextId(param);
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		dataList.add(new DTForm("CLI_IDX", cliIdx));	//cli_idx와 함께 인서트하기 위해 키 하드코딩
		dataList.add(new DTForm("ACMSLT_IDX", acmsltIdx));					
		dataList.add(new DTForm("PLAN_IDX", planIdx));					
		dataList.add(new DTForm("ACMSLT_START_DT", "TO_DATE('" + startDate + "', 'YYYY-MM-DD')", 1));					
		dataList.add(new DTForm("ACMSLT_END_DT", "TO_DATE('" + endDate + "', 'YYYY-MM-DD')", 1));					
		dataList.add(new DTForm("CONFM_STATUS", status));

		dataList = addRegiInfo(dataList, loginVO, regiIp);
		dataList = addLastModiInfo(dataList, loginVO, regiIp);		
		  	
		param.put("dataList", dataList);

		// 3. DB 저장
		// 3.1 기본 정보 테이블		
		int result = clinicDAO.insert(param);
		if(result > 0){			
			insertFile(dataMap, "HRD_CLI_ACMSLT_FILE", "ACMSLT_IDX", cliIdx, acmsltIdx, fileRealPath);
		}
		
		// 활동일지는 현재 자동승인이라서 HRD_CLI_ACMSLT_CONFM에 승인상태 insert 안함, 추후에 프로세스가 바뀌면 아래 코드에서 status만 수정해서 사용
		//insertConfm("HRD_CLI_ACMSLT_CONFM", "ACMSLT_IDX", "CONFM_IDX", status, cliIdx, acmsltIdx, regiIp);
		
		return result;
	}

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
	@Override
	public int updateAcmsltAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx, int acmsltIdx, String status) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, "modifyproc_order");
		
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		// 1. 검색조건 setting
		searchList.add(new DTForm("ACMSLT_IDX", acmsltIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));		
		
		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		
		String startDate = (String) parameterMap.get("startDate");
		String endDate = (String) parameterMap.get("endDate");
		
		startDate = startDate.substring(0, 10);
		endDate = endDate.substring(0, 10);

		// LAST_MODI 항목 setting
		addLastModiInfo(dataList, loginVO, regiIp);
		
		dataList.add(new DTForm("ACMSLT_START_DT", "TO_DATE('" + startDate + "', 'YYYY-MM-DD')", 1));					
		dataList.add(new DTForm("ACMSLT_END_DT", "TO_DATE('" + endDate + "', 'YYYY-MM-DD')", 1));									
		
    	param.put("dataList", dataList);
    	param.put("searchList", searchList);

    	param.put("targetTable", "HRD_CLI_ACMSLT");
    	
    	// 3. DB 저장
    	// 3.1 기본 정보 테이블
		int planUpdate = clinicDAO.update(param);
		
		if(planUpdate > 0){			
			insertFile(dataMap, "HRD_CLI_ACMSLT_FILE", "ACMSLT_IDX", cliIdx, acmsltIdx, fileRealPath);
		}
		
		// 활동일지는 현재 자동승인이라서 HRD_CLI_ACMSLT_CONFM에 승인상태 insert 안함, 추후에 프로세스가 바뀌면 아래 코드에서 status만 수정해서 사용
		//insertConfm("HRD_CLI_ACMSLT_CONFM", "ACMSLT_IDX", "CONFM_IDX", status, cliIdx, acmsltIdx, regiIp);
		
		return planUpdate;
	}
	
	
	/**
	 * [계획관리] 능력개발클리닉 성과보고서(HRD_CLI_RESULT) 테이블 insert 처리(최초 신청/ 최초 임시저장)
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @param cliIdx
	 * @param status(저장할 상태값)
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int insertResultAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx , String status) throws Exception  {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray resultInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "result_insert_column_order");
		
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(resultInsertColumnOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 저장항목
						
		
		// 로그인한 회원의 정보 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
				
		
		param.put("targetTable", "HRD_CLI_RSLT");
		param.put("targetTableIdx", "RESLT_IDX");
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
				
		param.put("BPL_NO", BPL_NO);
		
		//HRD_CLI_RSLT테이블에서 마지막 RESLT_IDX의 다음값 가져온다 
		int keyIdx = clinicDAO.getNextId(param);
		

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, resultInsertColumnOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		dataList.add(new DTForm("CLI_IDX", cliIdx));	//cli_idx와 함께 인서트하기 위해 키 하드코딩
		dataList.add(new DTForm("RESLT_IDX", keyIdx));
		dataList.add(new DTForm("CONFM_STATUS", status));

		dataList = addRegiInfo(dataList, loginVO, regiIp);
		dataList = addLastModiInfo(dataList, loginVO, regiIp);		
		  	
		param.put("dataList", dataList);
		

		// 3. DB 저장
		// 3.1 기본 정보 테이블		
		int result = clinicDAO.insert(param);
		
		if(status.equals("10")) {
			insertConfm("HRD_CLI_RSLT_CONFM", "RESLT_IDX", "CONFM_IDX", status, cliIdx, keyIdx, regiIp);
		}
		
//		public int deleteAndinsertPlanSub(String targetTable, int cliIdx, int planIdx, String regiIp, ParamForm parameterMap)
		result = deleteAndinsertResultYearTp("HRD_CLI_RSLT_TR_SUB", cliIdx, keyIdx, regiIp, parameterMap);
		result = deleteAndinsertResultKPI("HRD_CLI_RSLT_KPI", cliIdx, keyIdx, regiIp, parameterMap);
		

		return result;
	}
	
	
	/**
	 * [계획관리] 능력개발클리닉 성과보고서(HRD_CLI_RESULT) 테이블 update 처리 (이미 등록된 보고서가 있는 경우)
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
	public int updateResultAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx, String status) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray resultUpdateColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "result_update_column_order");
		
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(resultUpdateColumnOrder)) return -1;
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		

//		int planIdx = (int) parameterMap.get("planIdx");
		int resultIdx = StringUtil.getInt((String) parameterMap.get("resltIdx"));		
		// 1. 검색조건 setting
//		searchList.add(new DTForm("PLAN_IDX", planIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));		
		
		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, resultUpdateColumnOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		dataList.add(new DTForm("CONFM_STATUS", status));
		if(itemDataList != null) dataList.addAll(itemDataList);

		// LAST_MODI 항목 setting
		addLastModiInfo(dataList, loginVO, regiIp);
		
    	param.put("dataList", dataList);
    	param.put("searchList", searchList);

    	param.put("targetTable", "HRD_CLI_RSLT");
    	
    	// 3. DB 저장
    	// 3.1 기본 정보 테이블
    	
    	chgLogMapper.writeDataLog("HRD_CLI_RSLT","U",searchList);
		int planUpdate = clinicDAO.update(param);
		
		if(status.equals("10")) {
			insertConfm("HRD_CLI_RSLT_CONFM", "RESLT_IDX", "CONFM_IDX", status, cliIdx, resultIdx, regiIp);
		}
		
		deleteAndinsertResultYearTp("HRD_CLI_RSLT_TR_SUB", cliIdx, resultIdx, regiIp, parameterMap);
		deleteAndinsertResultKPI("HRD_CLI_RSLT_KPI", cliIdx, resultIdx, regiIp, parameterMap);
		

//		parameterMap.put("cliIdx", cliIdx);
		
		return planUpdate;
	}
	
	/**
	 * [비용청구] 능력개발클리닉 비용청구(HRD_CLI_SPT) 테이블 insert 처리(최초 신청/ 최초 임시저장)
	 * @param uploadModulePath
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @param cliIdx
	 * @param status(저장할 상태값)
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int insertSupportAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx , String status) throws Exception  {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, "writeproc_order");
		
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		dataList = new ArrayList<DTForm>();							// 저장항목
		
		// 로그인한 회원의 정보 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		param.put("targetTable", "HRD_CLI_SPT");
		param.put("targetTableIdx", "SPORT_IDX");
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
				
		param.put("BPL_NO", BPL_NO);
		
		//HRD_CLI_RSLT테이블에서 마지막 RESLT_IDX의 다음값 가져온다 
		int keyIdx = clinicDAO.getNextId(param);
		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		String acnutNo = (String) parameterMap.get("acnutNo");	
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		dataList.add(new DTForm("CLI_IDX", cliIdx));	//cli_idx와 함께 인서트하기 위해 키 하드코딩
		dataList.add(new DTForm("SPORT_IDX", keyIdx));
		dataList.add(new DTForm("CONFM_STATUS", status));
		dataList.add(new DTForm("ACNUTNO", "PKG_DGUARD.FN_ENC_ACNUTNO('" + acnutNo + "')", 1));

		dataList = addRegiInfo(dataList, loginVO, regiIp);
		dataList = addLastModiInfo(dataList, loginVO, regiIp);		
		  	
		param.put("dataList", dataList);
		

		// 3. DB 저장
		// 3.1 기본 정보 테이블		
		int result = clinicDAO.insert(param);
		
		if(status.equals("10")) {
			insertConfm("HRD_CLI_SPT_CONFM", "SPORT_IDX", "CONFM_IDX", status, cliIdx, keyIdx, regiIp);
		}
		
		result = deleteAndinsertSupportSub("HRD_CLI_SPT_SUB", cliIdx, keyIdx, regiIp, parameterMap);
		
		return result;
	}
	
	
	/**
	 * [비용청구] 능력개발클리닉 비용청구(HRD_CLI_SPT) 테이블 update 처리 (이미 등록된 보고서가 있는 경우)
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
	public int updateSupportAsParamCode(String uploadModulePath, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO, int cliIdx, String status) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, "modifyproc_order");
		
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		dataList = new ArrayList<DTForm>();							// 저장항목
		

		int sportIdx = StringUtil.getInt((String) parameterMap.get("sportIdx"));		
		String acnutNo = (String) parameterMap.get("acnutNo");	
		

		// 1. 검색조건 setting
		searchList.add(new DTForm("SPORT_IDX", sportIdx));
		searchList.add(new DTForm("CLI_IDX", cliIdx));		
		
		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		dataList.add(new DTForm("CONFM_STATUS", status));
		if(itemDataList != null) dataList.addAll(itemDataList);

		// LAST_MODI 항목 setting
		addLastModiInfo(dataList, loginVO, regiIp);
		
    	param.put("dataList", dataList);
    	param.put("searchList", searchList);

    	param.put("targetTable", "HRD_CLI_SPT");
    	
    	// 3. DB 저장
    	// 3.1 기본 정보 테이블
    	
    	chgLogMapper.writeDataLog("HRD_CLI_SPT","U",searchList);
    	dataList.add(new DTForm("ACNUTNO", "PKG_DGUARD.FN_ENC_ACNUTNO('" + acnutNo + "')", 1));
		int sptUpdate = clinicDAO.update(param);
		
		if(status.equals("10")) {
			insertConfm("HRD_CLI_SPT_CONFM", "SPORT_IDX", "CONFM_IDX", status, cliIdx, sportIdx, regiIp);
		}
		
		deleteAndinsertSupportSub("HRD_CLI_SPT_SUB", cliIdx, sportIdx, regiIp, parameterMap);
		
		return sptUpdate;
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
	public int deleteAndinsertResultYearTp(String targetTable, int cliIdx, int resultIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보
		
		// 연간훈련계획 정보는 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		List<DTForm> searchList = new ArrayList<DTForm>();							// 삭제조건
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("RESLT_IDX", resultIdx));
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
			dataList.add(new DTForm("RESLT_IDX", resultIdx));
			dataList.add(new DTForm("TR_DTL_IDX", trDtlIdx));
			dataList.add(new DTForm("TR_MT", trmt));
			dataList.add(new DTForm("TR_TRGET", trTarget));
			dataList.add(new DTForm("TR_CN", trCn));
			dataList.add(new DTForm("TR_MBY", trMby));
			dataList.add(new DTForm("TR_MTH", trMth));
			dataList.add(new DTForm("TRINSTT_NM", trInsttNm));
			dataList.add(new DTForm("TR_NMPR", trNmpr));
			dataList.add(new DTForm("TRTM", trTm));
			dataList.add(new DTForm("TR_START_DATE", "TO_DATE('" + trStartDate + "', 'YYYY-MM-DD')", 1));
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
	public int deleteAndinsertResultKPI(String targetTable, int cliIdx, int resultIdx, String regiIp, ParamForm parameterMap) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();		// 로그인 사용자 정보

		// 자체 KPI목표는 수정이 될 때 기존 데이터를 update 하는 것이 아니라 기존 데이터를 delete하고 새로 insert 한다.
		Map<String, Object> delParam = new HashMap<String, Object>();				// mapper parameter 기존꺼 삭제용
		List<DTForm> searchList = new ArrayList<DTForm>();							// 삭제조건
		
		// 기존 데이터를 삭제할 기준
		searchList.add(new DTForm("RESLT_IDX", resultIdx));
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
			dataList.add(new DTForm("RESLT_IDX", resultIdx));
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
	 * [비용청구] 지원금 신청서에서 입력한 지원금 신청내역 HRD_CLI_SPT_SUB에 insert하기
	 * @param targetTable
	 * @param planIdx
	 * @param regiIp
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public int deleteAndinsertSupportSub(String targetTable, int cliIdx, int sportIdx, String regiIp, ParamForm parameterMap) throws Exception {
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

	
	@Override
	public List<Object> getActivityCode(int cliIdx, String optionCode, int sportIdx) {
		Map<String, Object> param = new HashMap<String, Object>();
    	param.put("CLI_IDX", cliIdx);
    	param.put("OPTION_CODE", optionCode);
    	param.put("SPORT_IDX", sportIdx);
		return clinicDAO.getActivityCode(param);
	}
	
	@Override
	public List<Object> getActivityList(Map<String, Object> param) {
		return clinicDAO.getActivityList(param);
	}
	
	@Override
	public List<Object> getPlanProgress(int cliIdx) {
		Map<String, Object> param = new HashMap<String, Object>();
    	param.put("CLI_IDX", cliIdx);
		return clinicDAO.getPlanProgress(param);
	}
	
	@Override
	public List<Object> getPlanCode(int cliIdx, int sportIdx) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("CLI_IDX", cliIdx);
		param.put("SPORT_IDX", sportIdx);
		return clinicDAO.getPlanCode(param);
	}
	
	@Override
	public int getActivityCount(int cliIdx) {
		Map<String, Object> param = new HashMap<String, Object>();
    	param.put("CLI_IDX", cliIdx);
		return clinicDAO.getActivityCount(param);
	}
	
	@Override
	public List<Object> getResultProgress(int cliIdx) {
		Map<String, Object> param = new HashMap<String, Object>();
    	param.put("CLI_IDX", cliIdx);
		return clinicDAO.getResultProgress(param);
	}
	
	@Override
	public List<Object> getResultCode(int cliIdx, int sportIdx) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("CLI_IDX", cliIdx);
		param.put("SPORT_IDX", sportIdx);
		return clinicDAO.getResultCode(param);
	}
	
	@Override
	public List<Object> getSupportProgress(int cliIdx) {
		Map<String, Object> param = new HashMap<String, Object>();
    	param.put("CLI_IDX", cliIdx);
		return clinicDAO.getSupportProgress(param);
	}

	@Override
	public List<Object> getSportList() {
		return clinicDAO.getSportList();
	}

	@Override
	public List<Object> getSptSubGroupBy(int cliIdx, int sportIdx) {
		Map<String, Object> param = new HashMap<String, Object>();
    	param.put("CLI_IDX", cliIdx);
    	param.put("SPORT_IDX", sportIdx);
		return clinicDAO.getSptSubGroupBy(param);
	}

	@Override
	public List<Object> getSptPayList(int cliIdx, int sportIdx) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("CLI_IDX", cliIdx);
		param.put("SPORT_IDX", sportIdx);
		
		return clinicDAO.getSptPayList(param);
	}

	@Override
	public int getMaxIdx(String targetTable, String targetTableIdx, int cliIdx) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();		
		
		searchList.add(new DTForm("A.CLI_IDX", cliIdx));
		param.put("searchList", searchList);
		
		param.put("targetTable", targetTable);
		param.put("targetTableIdx", targetTableIdx);
		
		return clinicDAO.getMaxIdx(param);
	}

	@Override
	public List<Object> getTrainingCntList(String BPL_NO) {
		return clinicDAO.getTrainingCntList(BPL_NO);
	}

	@Override
	public List<Object> getSurveyTargetList(String BPL_NO) {
		return clinicDAO.getSurveyTargetList(BPL_NO);
	}

	@Override
	public List<Object> getAnswerList(String BPL_NO) {
		return clinicDAO.getAnswerList(BPL_NO);
	}

	@Override
	public int getCompareRequestAndVoByBplNo(String targetTable, String targetTableKey, int targetTableKeyValue, String BPL_NO) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("targetTable", targetTable);
		param.put("targetTableKey", targetTableKey);
		param.put("targetTableKeyValue", targetTableKeyValue);
		param.put("BPL_NO", BPL_NO);
		return clinicDAO.getCompareRequestAndVoByBplNo(param);
	}


//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ코드 리펙토링 전 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ	




}