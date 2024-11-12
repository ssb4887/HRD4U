package rbs.modules.traing.service.impl;

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
import com.woowonsoft.egovframework.util.JSONObjectUtil;
import com.woowonsoft.egovframework.util.ModuleUtil;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import rbs.egovframework.LoginVO;
import rbs.modules.traing.mapper.TraingMapper;
import rbs.modules.traing.service.TraingService;

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("traingService")
public class TraingServiceImpl extends EgovAbstractServiceImpl implements TraingService {

	@Resource(name="traingMapper")
	private TraingMapper traingDAO;
	
	/**
	 * 전체 목록 수 - 실시 현황
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalCount(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
    	return traingDAO.getTotalCount(param);
    }
    
    /**
	 * 전체 목록 수 - 지원 현황
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getSprtTotalCount(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
    	return traingDAO.getSprtTotalCount(param);
	}

	/**
	 * 전체 목록 - 실시 현황
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getList(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
		return traingDAO.getList(param);
	}
	
	/**
	 * 전체 목록 - 지원 현황
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getSprtList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return traingDAO.getSprtList(param);
	}
	
	/**
	 * 실시 현황 전체 목록(상세 목록에서 사용)
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getDetailList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return traingDAO.getDetailList(param);
	}
	
	/**
	 * 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getView(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
    	List<Object> viewDAO = traingDAO.getView(param);
		return viewDAO;
	}

	/**
	 * 수정 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getModify(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
		return traingDAO.getModify(param);
	}
	
	/**
	 * 업종코드 조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getIndustCdList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return traingDAO.getIndustCdList(param);
	}

	/**
	 * 연도 조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getIndustCdYearList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return traingDAO.getIndustCdYearList(param);
	}

	/**
	 * 권한여부
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getAuthCount(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
    	return traingDAO.getAuthCount(param);
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
		int result = 0;
		int keyIdx = 0;
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
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
		
		String industCd = (String) parameterMap.get("indutyCode");
		String year = (String) parameterMap.get("year"); 
		
		List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
		if(multiDataList != null) {
			int multiDataSize = multiDataList.size() / 3;
			for(int i = 0 ; i < multiDataSize ; i ++) {
				Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
				List<DTForm> dataList = new ArrayList<DTForm>();							// 저장항목
				
				param.put("fnIdx", fnIdx);
				keyIdx = traingDAO.getNextId(param);
				
				// 입력한 데이터 리스트 가져오기
				Map<String, Object> rangeData = (HashMap) multiDataList.get(i);
				List<DTForm> rangeList = (List<DTForm>) rangeData.get("dataList");
				
				Map<String, Object> corpData = (HashMap) multiDataList.get(i+multiDataSize);
				List<DTForm> corpList = (List<DTForm>) corpData.get("dataList");
				
				Map<String, Object> totData = (HashMap) multiDataList.get(i+(multiDataSize*2));
				List<DTForm> totList = (List<DTForm>) totData.get("dataList");
				
				// 데이터 추출
				DTForm rangeInfo = (DTForm) rangeList.get(1);
				int range = StringUtil.getInt((String)rangeInfo.get("columnValue"));
				
				DTForm corpInfo = (DTForm) corpList.get(1);
				String corpValue = (String)corpInfo.get("columnValue");
				int corp = StringUtil.getInt(corpValue.replace(",", ""));
				
				DTForm totInfo = (DTForm) totList.get(1);
				String totValue = (String)totInfo.get("columnValue");
				int tot = StringUtil.getInt(totValue.replace(",", "")); 
				
				// 데이터 입력 정보
				dataList.add(new DTForm("EXEC_IDX", keyIdx));
				dataList.add(new DTForm("INDUTY_CD", industCd));
				dataList.add(new DTForm("TOT_WORK_CNT_SCOPE", range));
				dataList.add(new DTForm("YEAR", year));
				dataList.add(new DTForm("NMCORP", corp));
				dataList.add(new DTForm("TOTAL_NMPR", tot));
				
				dataList.add(new DTForm("REGI_IDX", loginMemberIdx));
				dataList.add(new DTForm("REGI_ID", loginMemberId));
				dataList.add(new DTForm("REGI_NAME", loginMemberName));
				dataList.add(new DTForm("REGI_IP", regiIp));
				
				dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
				dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
				dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
				dataList.add(new DTForm("LAST_MODI_IP", regiIp));
		    	
				param.put("dataList", dataList);
				
				result = traingDAO.insert(param);
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
	public int update(String uploadModulePath, int fnIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		int result = 0;
		
		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
				
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
		
    	String industCd = (String) parameterMap.get("indutyCode");
		String year = (String) parameterMap.get("year"); 
		
		searchList.add(new DTForm("INDUTY_CD", industCd));
		searchList.add(new DTForm("YEAR", year));
		
		param.put("searchList", searchList);
		
		// 해당 업종의 상시 인원 수 범위 체크하기 
		int count = traingDAO.getTotalCount(param);
		
		// 상시 인원 수 범위(~30인, 30~100인, 100~300인, 300~1000인, 1000인~)가 하나라도 없으면 수정 불가
		if(count < 5) {
			result = 0;
		} else {
			// 2.1 저장항목
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			if(multiDataList != null) {
				int multiDataSize = multiDataList.size() / 3;
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> param2 = new HashMap<String, Object>();					// mapper parameter 데이터
					List<DTForm> dataList2 = new ArrayList<DTForm>();							// 저장항목
					List<DTForm> searchList2 = new ArrayList<DTForm>();	
					
					param.put("fnIdx", fnIdx);
					
					// 입력한 데이터 리스트 가져오기
					Map<String, Object> rangeData = (HashMap) multiDataList.get(i);
					List<DTForm> rangeList = (List<DTForm>) rangeData.get("dataList");
					
					Map<String, Object> corpData = (HashMap) multiDataList.get(i+multiDataSize);
					List<DTForm> corpList = (List<DTForm>) corpData.get("dataList");
					
					Map<String, Object> totData = (HashMap) multiDataList.get(i+(multiDataSize*2));
					List<DTForm> totList = (List<DTForm>) totData.get("dataList");
					
					// 데이터 추출
					DTForm rangeInfo = (DTForm) rangeList.get(1);
					int range = StringUtil.getInt((String)rangeInfo.get("columnValue"));
					
					DTForm corpInfo = (DTForm) corpList.get(1);
					String corpValue = (String)corpInfo.get("columnValue");
					int corp = StringUtil.getInt(corpValue.replace(",", ""));
					
					DTForm totInfo = (DTForm) totList.get(1);
					String totValue = (String)totInfo.get("columnValue");
					int tot = StringUtil.getInt(totValue.replace(",", ""));  
					
					// 데이터 입력 정보
					dataList2.add(new DTForm("NMCORP", corp));
					dataList2.add(new DTForm("TOTAL_NMPR", tot));
					
					dataList2.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
					dataList2.add(new DTForm("LAST_MODI_ID", loginMemberId));
					dataList2.add(new DTForm("LAST_MODI_NAME", loginMemberName));
					dataList2.add(new DTForm("LAST_MODI_IP", regiIp));
			    	
					searchList2.add(new DTForm("INDUTY_CD", industCd));
					searchList2.add(new DTForm("TOT_WORK_CNT_SCOPE", range));
					searchList2.add(new DTForm("YEAR", year));
					
					param2.put("dataList", dataList2);
					param2.put("searchList", searchList2);
					
					result = traingDAO.update(param2);
				}
			}
		}
		
		return result;
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
    	return traingDAO.getDeleteCount(param);
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
		return traingDAO.getDeleteList(param);
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
	public int delete(int fnIdx, ParamForm parameterMap, String regiIp, JSONObject settingInfo) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>();						// 저장항목

		// 1. 삭제조건
    	String indutyCd = (String) parameterMap.get("indutyCode");
    	String year = (String) parameterMap.get("year");
    	
    	searchList.add(new DTForm("INDUTY_CD", indutyCd));
    	searchList.add(new DTForm("YEAR", year));
    	
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
		return traingDAO.delete(param);
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
		
		// 3. DB 저장
		return traingDAO.restore(param);
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
		
		// 3. delete
		int result = traingDAO.cdelete(param);
		
		return result;
	}

}