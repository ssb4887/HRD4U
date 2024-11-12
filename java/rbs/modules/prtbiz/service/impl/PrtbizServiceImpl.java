package rbs.modules.prtbiz.service.impl;

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
import rbs.modules.prtbiz.mapper.PrtbizFileMapper;
import rbs.modules.prtbiz.mapper.PrtbizMapper;
import rbs.modules.prtbiz.mapper.PrtbizMultiMapper;
import rbs.modules.prtbiz.service.PrtbizService;

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("prtbizService")
public class PrtbizServiceImpl extends EgovAbstractServiceImpl implements PrtbizService {

	@Resource(name="prtbizMapper")
	private PrtbizMapper prtbizDAO;
	
	@Resource(name="prtbizFileMapper")
	private PrtbizFileMapper prtbizFileDAO;
	
	@Resource(name="prtbizMultiMapper")
	private PrtbizMultiMapper prtbizMultiDAO;
	
	/**
	 * 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalCount(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
    	return prtbizDAO.getTotalCount(param);
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
		return prtbizDAO.getList(param);
	}
	
	/**
	 * 훈련과정 목록 가져오기
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getTpSubList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return prtbizDAO.getTpSubList(param);
	}
	
	/**
	 * 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getView(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
		DataMap viewDAO = prtbizDAO.getView(param);
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
		DataMap viewDAO = prtbizDAO.getFileView(param);
		return viewDAO;
	}

	/**
	 * multi파일 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getMultiFileView(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
		DataMap viewDAO = prtbizFileDAO.getFileView(param);
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
		return prtbizDAO.updateFileDown(param);
		
	}
	
	/**
	 * multi file 다운로드 수 수정
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateMultiFileDown(int fnIdx, int keyIdx, int fleIdx, String itemId) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		searchList.add(new DTForm("FLE_IDX", fleIdx));
		searchList.add(new DTForm("ITEM_ID", itemId));
    	param.put("searchList", searchList);
    	param.put("fnIdx", fnIdx);
		param.put("TP_IDX", keyIdx);
		return prtbizFileDAO.updateFileDown(param);
		
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
		DataMap viewDAO = prtbizDAO.getModify(param);
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
    	return prtbizDAO.getAuthCount(param);
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
		List<DTForm> dataList = new ArrayList<DTForm>();							// 저장항목

		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");	// key 컬럼
		
		// 1. key 얻기
		param.put("fnIdx", fnIdx);
		int keyIdx = prtbizDAO.getNextId(param);

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
    	
		// 훈련과정 NCS 대분류, 중분류 & 적용업종 입력하기
		if(fnIdx == 2) {
			String ncsLclsCd = parameterMap.get("ncsSclasCode").toString().substring(0, 2);
			String ncsMclsCd = parameterMap.get("ncsSclasCode").toString().substring(0, 4);
			// 추후에 적용업종을 코드로 관리하는 걸로 변경될 때 아래 코드 사용
			//String applyIndust = industIdxs[0] + "," + industIdxs[1];
			
			dataList.add(new DTForm("NCS_LCLAS_CD", ncsLclsCd));
			dataList.add(new DTForm("NCS_MCLAS_CD", ncsMclsCd));
			// 추후에 적용업종을 코드로 관리하는 걸로 변경될 때 아래 코드 사용
			//dataList.add(new DTForm("APPLY_INDUTY", applyIndust));
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
		int result = prtbizDAO.insert(param);
		
		if(result > 0 && fnIdx == 2) {
			// 3.2 multi data 저장
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			if(multiDataList != null) {
				int multiDataSize = multiDataList.size() / 4;
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> multiParam = new HashMap<String, Object>();	
					List<DTForm> dataList2 = new ArrayList<DTForm>();		
					
					// 훈련내용 데이터 리스트 가져오기
					Map<String, Object> tpSubNameData = (HashMap) multiDataList.get(i);
					List<DTForm> tpSubNameList = (List<DTForm>) tpSubNameData.get("dataList");
					
					Map<String, Object> tpSubTimeData = (HashMap) multiDataList.get(i+multiDataSize);
					List<DTForm> tpSubTimeList = (List<DTForm>) tpSubTimeData.get("dataList");
					
					Map<String, Object> tpSubContentData = (HashMap) multiDataList.get(i+(multiDataSize*2));
					List<DTForm> tpSubContentList = (List<DTForm>) tpSubContentData.get("dataList");
					
					Map<String, Object> tpSubTeachData = (HashMap) multiDataList.get(i+(multiDataSize*3));
					List<DTForm> tpSubTeachList = (List<DTForm>) tpSubTeachData.get("dataList");
					
					// 훈련내용 데이터 추출
					DTForm tpSubNameInfo = (DTForm) tpSubNameList.get(1);
					String tpSubName = (String)tpSubNameInfo.get("columnValue");
					
					DTForm tpSubTimeInfo = (DTForm) tpSubTimeList.get(1);
					String tpSubTime = (String)tpSubTimeInfo.get("columnValue");
					
					DTForm tpSubContentInfo = (DTForm) tpSubContentList.get(1);
					String tpSubContent = (String)tpSubContentInfo.get("columnValue");
					// 줄바꿈 개행문자를 <br> 태그로 변경 
					String tpSubContent2 = tpSubContent.replaceAll("\r\n", "<br>");
					
					DTForm tpSubTeachInfo = (DTForm) tpSubTeachList.get(1);
					String tpSubTeach = (String)tpSubTeachInfo.get("columnValue");
					
					int tpSubIdx = prtbizMultiDAO.getNextId(param);
					
					dataList2.add(new DTForm("PRTBIZ_IDX", StringUtil.getInt((String) parameterMap.get("prtbizIdx"))));
					dataList2.add(new DTForm("TP_COURSE_IDX", tpSubIdx));
					dataList2.add(new DTForm("TP_IDX", keyIdx));
					dataList2.add(new DTForm("COURSE_NO", "M" + (i+1)));
					dataList2.add(new DTForm("COURSE_NAME", tpSubName));
					dataList2.add(new DTForm("TIME", tpSubTime));
					dataList2.add(new DTForm("CN", tpSubContent2));
					dataList2.add(new DTForm("TCHMETHOD", tpSubTeach));
					
					
					dataList2.add(new DTForm("REGI_IDX", loginMemberIdx));
					dataList2.add(new DTForm("REGI_ID", loginMemberId));
					dataList2.add(new DTForm("REGI_NAME", loginMemberName));
					dataList2.add(new DTForm("REGI_IP", regiIp));
					
					dataList2.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
					dataList2.add(new DTForm("LAST_MODI_ID", loginMemberId));
					dataList2.add(new DTForm("LAST_MODI_NAME", loginMemberName));
					dataList2.add(new DTForm("LAST_MODI_IP", regiIp));
					
					multiParam.put("dataList", dataList2);
					
					result = prtbizMultiDAO.insert(multiParam);
				}
			}
			
			// 3.3 multi file 저장
			List fileDataList = StringUtil.getList(dataMap.get("fileDataList"));
			if(fileDataList != null) {
				int fileDataSize = fileDataList.size();
				for(int i = 0 ; i < fileDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)fileDataList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("PRTBIZ_IDX", StringUtil.getInt((String) parameterMap.get("prtbizIdx")));
					fileParam.put("TP_IDX", keyIdx);
					int fleIdx = prtbizFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = prtbizFileDAO.insert(fileParam);
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
	public int update(String uploadModulePath, int fnIdx, int keyIdx, String regiIp, ParamForm parameterMap, ModuleAttrVO attrVO) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, "modifyproc_order");
		
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
    	
    	// 훈련과정 NCS 대분류, 중분류 & 적용업종 입력하기
		if(fnIdx == 2) {
			String ncsLclsCd = parameterMap.get("ncsSclasCode").toString().substring(0, 2);
			String ncsMclsCd = parameterMap.get("ncsSclasCode").toString().substring(0, 4);
			// 추후에 적용업종을 코드로 관리하는 걸로 변경될 때 아래 코드 사용
			//String applyIndust = industIdxs[0] + "," + industIdxs[1];
			
			dataList.add(new DTForm("NCS_LCLAS_CD", ncsLclsCd));
			dataList.add(new DTForm("NCS_MCLAS_CD", ncsMclsCd));
			// 추후에 적용업종을 코드로 관리하는 걸로 변경될 때 아래 코드 사용
			//dataList.add(new DTForm("APPLY_INDUTY", applyIndust));
		}
    	
    	param.put("dataList", dataList);
    	param.put("searchList", searchList);
    	param.put("fnIdx", fnIdx);

    	// 3. DB 저장
    	// 3.1 기본 정보 테이블
		int result = prtbizDAO.update(param);

		if(result > 0 && fnIdx == 2){
			// 3.2 multi data 저장
			// 3.2.1 multi data 삭제
			//int result1 = 0;
			Map<String, Object> multiDelParam = new HashMap<String, Object>();
			multiDelParam.put("fnIdx", fnIdx);
			multiDelParam.put("TP_IDX", keyIdx);
			prtbizMultiDAO.cdelete(multiDelParam);

			// 3.2.2 multi data 등록
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			if(multiDataList != null) {
				int multiDataSize = multiDataList.size() / 4;
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> multiParam = new HashMap<String, Object>();	
					List<DTForm> dataList2 = new ArrayList<DTForm>();		
					
					// 훈련내용 데이터 리스트 가져오기
					Map<String, Object> tpSubNameData = (HashMap) multiDataList.get(i);
					List<DTForm> tpSubNameList = (List<DTForm>) tpSubNameData.get("dataList");
					
					Map<String, Object> tpSubTimeData = (HashMap) multiDataList.get(i+multiDataSize);
					List<DTForm> tpSubTimeList = (List<DTForm>) tpSubTimeData.get("dataList");
					
					Map<String, Object> tpSubContentData = (HashMap) multiDataList.get(i+(multiDataSize*2));
					List<DTForm> tpSubContentList = (List<DTForm>) tpSubContentData.get("dataList");
					
					Map<String, Object> tpSubTeachData = (HashMap) multiDataList.get(i+(multiDataSize*3));
					List<DTForm> tpSubTeachList = (List<DTForm>) tpSubTeachData.get("dataList");
					
					// 훈련내용 데이터 추출
					DTForm tpSubNameInfo = (DTForm) tpSubNameList.get(1);
					String tpSubName = (String)tpSubNameInfo.get("columnValue");
					
					DTForm tpSubTimeInfo = (DTForm) tpSubTimeList.get(1);
					String tpSubTime = (String)tpSubTimeInfo.get("columnValue");
					
					DTForm tpSubContentInfo = (DTForm) tpSubContentList.get(1);
					String tpSubContent = (String)tpSubContentInfo.get("columnValue");
					// 줄바꿈 개행문자를 <br> 태그로 변경 
					String tpSubContent2 = tpSubContent.replaceAll("\r\n", "<br>");
					
					DTForm tpSubTeachInfo = (DTForm) tpSubTeachList.get(1);
					String tpSubTeach = (String)tpSubTeachInfo.get("columnValue");
					
					int tpSubIdx = prtbizMultiDAO.getNextId(param);
					
					dataList2.add(new DTForm("PRTBIZ_IDX", StringUtil.getInt((String) parameterMap.get("prtbizIdx"))));
					dataList2.add(new DTForm("TP_COURSE_IDX", tpSubIdx));
					dataList2.add(new DTForm("TP_IDX", keyIdx));
					dataList2.add(new DTForm("COURSE_NO", "M" + (i+1)));
					dataList2.add(new DTForm("COURSE_NAME", tpSubName));
					dataList2.add(new DTForm("TIME", tpSubTime));
					dataList2.add(new DTForm("CN", tpSubContent2));
					dataList2.add(new DTForm("TCHMETHOD", tpSubTeach));
					
					
					dataList2.add(new DTForm("REGI_IDX", loginMemberIdx));
					dataList2.add(new DTForm("REGI_ID", loginMemberId));
					dataList2.add(new DTForm("REGI_NAME", loginMemberName));
					dataList2.add(new DTForm("REGI_IP", regiIp));
					
					dataList2.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
					dataList2.add(new DTForm("LAST_MODI_ID", loginMemberId));
					dataList2.add(new DTForm("LAST_MODI_NAME", loginMemberName));
					dataList2.add(new DTForm("LAST_MODI_IP", regiIp));
					
					multiParam.put("dataList", dataList2);
					
					result = prtbizMultiDAO.insert(multiParam);
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
				int fleIdx = prtbizFileDAO.getNextId(fileParam1);*/
				
				// 3.3.1.2 DB 저장
				int fileDataSize = fileDataList.size();
				for(int i = 0 ; i < fileDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)fileDataList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("PRTBIZ_IDX", StringUtil.getInt((String) parameterMap.get("prtbizIdx")));
					fileParam.put("TP_IDX", keyIdx);
					int fleIdx = prtbizFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = prtbizFileDAO.insert(fileParam);
				}
			}
	
			// 3.3.2 multi file 수정
			/*List fileModifyDataList = StringUtil.getList(dataMap.get("fileModifyDataList"));
			if(fileModifyDataList != null) {
				int fileDataSize = fileModifyDataList.size();
				for(int i = 0 ; i < fileDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)fileModifyDataList.get(i);
					fileParam.put("TP_IDX", keyIdx);
					fileParam.put("fnIdx", fnIdx);
					result = prtbizFileDAO.update(fileParam);
				}
			}*/
			
			// 3.3.3 multi file 삭제
			List fileDeleteSearchList = StringUtil.getList(dataMap.get("fileDeleteSearchList"));
			if(fileDeleteSearchList != null) {
	
				List<Object> deleteMultiFileList = new ArrayList<Object>();
				int fileDataSize = fileDeleteSearchList.size();
				for(int i = 0 ; i < fileDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)fileDeleteSearchList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("TP_IDX", keyIdx);
					// 6.1 삭제목록 select
					List<Object> deleteFileList2 = prtbizFileDAO.getList(fileParam);
					if(deleteFileList2 != null) deleteMultiFileList.addAll(deleteFileList2);
					// 6.2 DB 삭제
					result = prtbizFileDAO.cdelete(fileParam);
				}
				
				// 3.3.4 multi file 삭제
				FileUtil.isKeyDelete(fileRealPath, deleteMultiFileList, "FILE_SAVED_NAME");
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
    	return prtbizDAO.getDeleteCount(param);
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
		return prtbizDAO.getDeleteList(param);
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
		int result = 0;
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		Map<String, Object> param = new HashMap<String, Object>();		// mapper parameter 데이터
		Map<String, Object> param2 = new HashMap<String, Object>();		// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>();						// 저장항목
		
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
		
		param2.put("searchList", searchList);
		param2.put("dataList", dataList);
    	param2.put("fnIdx", 2);
    	
    	// 훈련과정 중 첨부파일 및 훈련내용 삭제
    	prtbizMultiDAO.delete(param);
    	prtbizFileDAO.delete(param);
		
    	if(fnIdx == 1) {
    		// 참여가능사업 삭제 시 훈련과정도 함께 삭제 
    		prtbizDAO.delete(param2);
    	} 
    	result = prtbizDAO.delete(param);
    	
		// 3. DB 저장
		return result;
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
		Map<String, Object> param2 = new HashMap<String, Object>();				// mapper parameter 데이터
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
    	param.put("fnIdx", fnIdx);
		
    	if(fnIdx == 1) {
    		param2.put("searchList", searchList);
    		param2.put("dataList", dataList);
        	param2.put("fnIdx", 2);
        	
        	prtbizDAO.restore(param2);
    	}
    	
		// 3. DB 저장
		return prtbizDAO.restore(param);
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
		deleteMultiFileList = prtbizFileDAO.getList(param);
		// 2.2 삭제할 file(단일항목) select
		List deleteFileColumnList = ModuleUtil.getFileObjectList(items, itemOrder);
		if(deleteFileColumnList != null) {
			param.put("columnList", deleteFileColumnList);
			
			deleteFileList = prtbizDAO.getFileList(param);
		}
		
		// 3. delete
		int result = prtbizDAO.cdelete(param);
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
	 * 참여가능사업 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getPrtbizList(int fnIdx) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fnIdx", fnIdx);
		
		return prtbizDAO.getPrtbizList(param);
	}

	/**
	 * 업종코드 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getIndustList(int fnIdx) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fnIdx", fnIdx);
		
		return prtbizDAO.getIndustList(param);
	}
	
	/**
	 * 업종코드(대분류) 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getIndustMasterCode(int fnIdx) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fnIdx", fnIdx);
		
		return prtbizDAO.getIndustMasterCode(param);
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
					Map<String, Object> param = new HashMap<String, Object>();
					List<DTForm> searchList = new ArrayList<DTForm>();
			    	searchList.add(new DTForm("A." + columnName, keyIdx));
			    	searchList.add(new DTForm("A.ITEM_ID", itemId));
					param.put("searchList", searchList);
			    	param.put("fnIdx", fnIdx);
					resultHashMap.put(itemId, prtbizFileDAO.getList(param));
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
				resultHashMap = prtbizFileDAO.getMapList(param);
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
					resultHashMap.put(itemId, prtbizMultiDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A.TP_IDX", keyIdx));
				param.put("searchList", searchList);
		    	param.put("fnIdx", fnIdx);
				resultHashMap = prtbizMultiDAO.getMapList(param);
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
}