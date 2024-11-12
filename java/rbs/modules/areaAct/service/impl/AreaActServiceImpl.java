package rbs.modules.areaAct.service.impl;

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
import rbs.egovframework.common.ChgLogMapper;
import rbs.modules.areaAct.mapper.AreaActMapper;
import rbs.modules.areaAct.mapper.AreaActMultiMapper;
import rbs.modules.areaAct.service.AreaActService;

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("areaActService")
public class AreaActServiceImpl extends EgovAbstractServiceImpl implements AreaActService {

	@Resource(name="areaActMapper")
	private AreaActMapper areaActDAO;
	
	//@Resource(name="areaActFileMapper")
	//private AreaActFileMapper areaActFileDAO;
	
	@Resource(name="areaActMultiMapper")
	private AreaActMultiMapper areaActMultiDAO;
	
	@Resource(name="chgLogMapper")
	private ChgLogMapper chgLogMapper;
	
	/**
	 * 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalCount(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
    	return areaActDAO.getTotalCount(param);
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
		return areaActDAO.getList(param);
	}
	
	 /**
	 * 등록된 지부지사 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getInsttList(Map<String, Object> param) {
		return areaActDAO.getInsttList(param);
	}
	
	/**
	 * 집계 데이터 목록 가져오기
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getAccumList(Map<String, Object> param) {
		return areaActDAO.getAccumList(param);
	}
	
	/**
	 * 년도별 기업수 목록 가져오기 
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getYearList(Map<String, Object> param) {
		return areaActDAO.getYearList(param);
	}
	
	 /**
	 * 등록된 지부지사 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getInstt(Map<String, Object> param) {
		return areaActDAO.getInstt(param);
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
		DataMap viewDAO = areaActDAO.getView(param);
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
		DataMap viewDAO = areaActDAO.getFileView(param);
		return viewDAO;
	}

	/**
	 * multi파일 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	/*@Override
	public DataMap getMultiFileView(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
		DataMap viewDAO = areaActFileDAO.getFileView(param);
		return viewDAO;
	}*/
	
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
		return areaActDAO.updateFileDown(param);
		
	}
	
	/**
	 * multi file 다운로드 수 수정
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	/*public int updateMultiFileDown(int fnIdx, int keyIdx, int fleIdx, String itemId) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		searchList.add(new DTForm("FLE_IDX", fleIdx));
		searchList.add(new DTForm("ITEM_ID", itemId));
    	param.put("searchList", searchList);
    	param.put("fnIdx", fnIdx);
		param.put("KEY_IDX", keyIdx);
		return areaActFileDAO.updateFileDown(param);
		
	}*/

	/**
	 * 수정 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getModify(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
		DataMap viewDAO = areaActDAO.getModify(param);
		return viewDAO;
	}
	
	/**
	 * 지역활성화 기업바구니 데이터 불러오기
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getBskData(Map<String, Object> param) {
		DataMap viewDAO = areaActDAO.getBskData(param);
		return viewDAO;
	}
	
	/**
	 * 등록기간 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getRegi(Map<String, Object> param) {
		DataMap viewDAO = areaActDAO.getRegi(param);
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
    	return areaActDAO.getAuthCount(param);
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
		int keyIdx = areaActDAO.getNextId(param);

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
		DataMap bskData = areaActMultiDAO.getBskList();
		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = areaActDAO.insert(param);
		
		if(result > 0) {
			// 3.2 multi data 저장
			//지역활성화계획 기업바구니 DB에 INSERT 하기 위한 소스
			
			for(int j = 1; j < 4; j ++) {
				dataList = new ArrayList<DTForm>();
				Map<String, Object> bskParam = new HashMap<String, Object>();					// mapper parameter 데이터
				int keySn = areaActDAO.getNextSn(param);
				
				int LCLAS_CO = 0;
				int LCLAS_MDAT_CO = 0;
				if(j == 1) {
					LCLAS_CO = StringUtil.getInt(bskData.get("GENERALAUTO"));
					LCLAS_MDAT_CO = StringUtil.getInt(bskData.get("GENERALPASSIVE"));
				} else if(j == 2) {
					LCLAS_CO = StringUtil.getInt(bskData.get("EXCAVATIONAUTO"));
					LCLAS_MDAT_CO = StringUtil.getInt(bskData.get("EXCAVATIONPASSIVE"));
				} else if(j == 3) {
					LCLAS_CO = StringUtil.getInt(bskData.get("TRAININGAUTO"));
					LCLAS_MDAT_CO = StringUtil.getInt(bskData.get("TRAININGPASSIVE"));
				}
				
				dataList.add(new DTForm(columnName, keyIdx));
				dataList.add(new DTForm("LCLAS_CD", j));
				dataList.add(new DTForm("ARACTPLN_SN", keySn));

				dataList.add(new DTForm("LCLAS_CO", LCLAS_CO ));
				dataList.add(new DTForm("LCLAS_MDAT_CO", LCLAS_MDAT_CO ));
				
				bskParam.put("dataList",dataList);
				
				areaActMultiDAO.bskInsert(bskParam);
			}
			
			
			//지역활성화 기관 DB에 INSERT 하기 위한 소스
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			String check = StringUtil.getString(parameterMap.get("totalinsttIdx"));
			if(multiDataList != null) {
				int multiDataSize = multiDataList.size();
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)multiDataList.get(i);
					
					String smrize = null;
					String insttIdx = null;
					//멀티데이터의 column명을 수정하기 위한 소스
					List<Map> temp = (List<Map>) fileParam.get("dataList");
					
					Map<String, Object> tempMap2 = temp.get(1);
					tempMap2.put("columnId", "INSTT_IDX");
					insttIdx = StringUtil.getString(tempMap2.get("columnValue"));
					if(StringUtil.isEquals(tempMap2.get("columnValue"), check)) {
						smrize = "Y";
					} else {
						smrize = "N";
					}
					
					fileParam.put("SMRIZE", smrize);
					
					Map<String, Object> tempMap1 = temp.get(0);
					
					/*tempMap1.put("columnValue", smrize);*/
					
					List<Map> temp2 = new ArrayList<>();
					temp2.add(tempMap1);
					temp2.add(tempMap2);
					
					fileParam.put("dataList", temp2);
					
					fileParam.put("KEY_IDX", keyIdx);
					fileParam.put("INSTT_IDX", insttIdx);
					result = areaActMultiDAO.insert(fileParam);
					areaActMultiDAO.policyInsert(fileParam);

					areaActMultiDAO.bsnsInsert(fileParam);
					
					areaActMultiDAO.trngInsert(fileParam);
				}
			}
			
			// 3.3 multi file 저장
			/*List fileDataList = StringUtil.getList(dataMap.get("fileDataList"));
			if(fileDataList != null) {
				int fileDataSize = fileDataList.size();
				for(int i = 0 ; i < fileDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)fileDataList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", keyIdx);
					int fleIdx = areaActFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = areaActFileDAO.insert(fileParam);
				}
			}*/
		}
		
		return result > 0 ? keyIdx : result;
	}
	
	/**
	 * 권한여부
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getRegiDateChk() {
    	return areaActDAO.getRegiDateChk();
    }
	
	
	/**
	 * 등록기간 등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
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
	public int regiInsert(String uploadModulePath, int fnIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception  {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> dataList = new ArrayList<DTForm>();							// 저장항목

		//String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");	// key 컬럼
				
		// 1. key 얻기
		param.put("fnIdx", fnIdx);
		int keyIdx = 1;

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		
		//dataList.add(new DTForm("SCHDUL_IDX", 1));
		param.put("SCHDUL_IDX", 1);

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
		
		//이력남기기
    	chgLogMapper.writeDataLog("HRD_SPTJ_ARACTPLN_SCHDUL","U", "SCHDUL_IDX, 1");

		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = areaActDAO.regiInsert(param);
		
		/*if(result > 0) {
			// 3.2 multi data 저장getView
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			String check = StringUtil.getString(parameterMap.get("totalinsttIdx"));
			if(multiDataList != null) {
				int multiDataSize = multiDataList.size();
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)multiDataList.get(i);
					
					String smrize = null;
					
					//멀티데이터의 column명을 수정하기 위한 소스
					List<Map> temp = (List<Map>) fileParam.get("dataList");
					
					Map<String, Object> tempMap2 = temp.get(1);
					tempMap2.put("columnId", "INSTT_IDX");
					if(StringUtil.isEquals(tempMap2.get("columnValue"), check)) {
						smrize = "Y";
					} else {
						smrize = "N";
					}
					
					Map<String, Object> tempMap1 = temp.get(0);
					tempMap1.put("columnId", "SMRIZE_INSTT_YN");
					tempMap1.put("columnValue", smrize);
					
					List<Map> temp2 = new ArrayList<>();
					temp2.add(tempMap1);
					temp2.add(tempMap2);
					
					fileParam.put("dataList", temp2);
					
					fileParam.put("KEY_IDX", keyIdx);
					result = areaActMultiDAO.insert(fileParam);
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
					int fleIdx = areaActFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = areaActFileDAO.insert(fileParam);
				}
			}
		}*/
		
		return result > 0 ? keyIdx : result;
	}
	

	/**
	 * 계획서 작성처리 : 화면에 조회된 정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
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
	public int planUpdate(String uploadModulePath, int fnIdx, int keyIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception {
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
		//List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		//if(itemDataList != null) dataList.addAll(itemDataList);
		String implication = StringUtil.getString(parameterMap.get("implication"));
		String adjustment = StringUtil.getString(parameterMap.get("adjustment"));
		String groupCurrent = StringUtil.getString(parameterMap.get("groupCurrent"));
		String strategy = StringUtil.getString(parameterMap.get("strategy"));
		
		String [] insttList = StringUtil.getStringArray(parameterMap.get("instt"));
		String [] policyList = StringUtil.getStringArray(parameterMap.get("policy"));
		String [] distriList = StringUtil.getStringArray(parameterMap.get("distribution"));
		String [] trainingList = StringUtil.getStringArray(parameterMap.get("training"));
		String [] goalList = StringUtil.getStringArray(parameterMap.get("goal"));
		String [] detailList = StringUtil.getStringArray(parameterMap.get("detail"));

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
		
		dataList.add(new DTForm("IMPLT_CN", implication));
		dataList.add(new DTForm("MDAT_CN", adjustment));
		dataList.add(new DTForm("TRGRPSTT_CN", groupCurrent));
		dataList.add(new DTForm("TRGRPSTRGACC_CN", strategy));
		
		
    	dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
    	dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
    	dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
    	dataList.add(new DTForm("LAST_MODI_IP", regiIp));
    	
    	param.put("dataList", dataList);
    	param.put("searchList", searchList);
    	param.put("fnIdx", fnIdx);
    	
    	//이력남기기
    	chgLogMapper.writeDataLog("HRD_SPTJ_ARACTPLN","U", searchList);

    	// 3. DB 저장    	
    	// 3.1 기본 정보 테이블
		int result = areaActDAO.update(param);
		

		//지역활성화계획 기업바구니 DB에 INSERT 하기 위한 소스
		
		
		if(result > 0){
			// 3.2 multi data 저장
			// 3.2.1 multi data 삭제
			
			// 지역활성화 기업바구니
			for(int j = 1; j < 4; j ++) {
				dataList = new ArrayList<DTForm>();
				Map<String, Object> bskParam = new HashMap<String, Object>();					// mapper parameter 데이터
				
				
				String goal = null;
				String detail = null;
				if(j == 1) {
					goal = goalList[0];
					detail = detailList[0];
				} else if(j == 2) {
					goal = goalList[1];
					detail = detailList[1];
				} else if(j == 3) {
					goal = goalList[2];
					detail = detailList[2];
				}

				dataList.add(new DTForm("GOAL_CN", goal ));
				dataList.add(new DTForm("DETAIL_CN", detail ));
				
				dataList.add(new DTForm("REGI_IDX", loginMemberIdx));
				dataList.add(new DTForm("REGI_ID", loginMemberId));
				dataList.add(new DTForm("REGI_NAME", loginMemberName));
				dataList.add(new DTForm("REGI_IP", regiIp));
				
				dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
		    	dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
		    	dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
		    	dataList.add(new DTForm("LAST_MODI_IP", regiIp));
				
				bskParam.put("LCLAS_CD",j);
				bskParam.put("dataList",dataList);
				bskParam.put("searchList",searchList);
				
				areaActMultiDAO.bskUpdate(bskParam);
			}
			
			//지역활성화 주요시책
			for(int i = 0; i < insttList.length; i ++) {
				dataList = new ArrayList<DTForm>();
				Map<String, Object> policyParam = new HashMap<String, Object>();					// mapper parameter 데이터
				
				String instt = insttList[i];
				String policy = policyList[i];
				
				
				dataList.add(new DTForm("POLICI_CN", policy ));
				
				dataList.add(new DTForm("REGI_IDX", loginMemberIdx));
				dataList.add(new DTForm("REGI_ID", loginMemberId));
				dataList.add(new DTForm("REGI_NAME", loginMemberName));
				dataList.add(new DTForm("REGI_IP", regiIp));
				
				dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
		    	dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
		    	dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
		    	dataList.add(new DTForm("LAST_MODI_IP", regiIp));
				
				
		    	policyParam.put("INSTT_IDX", instt);
				policyParam.put("dataList", dataList);
				policyParam.put("searchList", searchList);
				
				areaActMultiDAO.policyUpdate(policyParam);
			}
			
			//지역활성화 기업분포
			for(int i = 0; i < insttList.length; i ++) {
				dataList = new ArrayList<DTForm>();
				Map<String, Object> distriParam = new HashMap<String, Object>();					// mapper parameter 데이터
				
				String instt = insttList[i];
				String distri = distriList[i];
				
				dataList.add(new DTForm("CORP_DISTRB_CN", distri ));
				
				dataList.add(new DTForm("REGI_IDX", loginMemberIdx));
				dataList.add(new DTForm("REGI_ID", loginMemberId));
				dataList.add(new DTForm("REGI_NAME", loginMemberName));
				dataList.add(new DTForm("REGI_IP", regiIp));
				
				dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
		    	dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
		    	dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
		    	dataList.add(new DTForm("LAST_MODI_IP", regiIp));
				
				
		    	distriParam.put("INSTT_IDX", instt);
		    	distriParam.put("dataList", dataList);
		    	distriParam.put("searchList", searchList);
				
				areaActMultiDAO.distriUpdate(distriParam);
			}
			
			//지역활성화 직업훈련
			for(int i = 0; i < insttList.length; i ++) {
				dataList = new ArrayList<DTForm>();
				Map<String, Object> trainParam = new HashMap<String, Object>();					// mapper parameter 데이터
				
				String instt = insttList[i];
				String training = trainingList[i];
				
				
				dataList.add(new DTForm("TR_CN", training ));
				
				dataList.add(new DTForm("REGI_IDX", loginMemberIdx));
				dataList.add(new DTForm("REGI_ID", loginMemberId));
				dataList.add(new DTForm("REGI_NAME", loginMemberName));
				dataList.add(new DTForm("REGI_IP", regiIp));
				
				dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
		    	dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
		    	dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
		    	dataList.add(new DTForm("LAST_MODI_IP", regiIp));
				
				
		    	trainParam.put("INSTT_IDX", instt);
		    	trainParam.put("dataList", dataList);
		    	trainParam.put("searchList", searchList);
				
				areaActMultiDAO.trainingUpdate(trainParam);
			}
			/*int result1 = 0;
			Map<String, Object> multiDelParam = new HashMap<String, Object>();
			multiDelParam.put("fnIdx", fnIdx);
			multiDelParam.put("KEY_IDX", keyIdx);
			
			//
			result1 = areaActMultiDAO.mulDelete(multiDelParam);

			// 3.2.2 multi data 등록
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			String check = StringUtil.getString(parameterMap.get("totalinsttIdx"));
			if(multiDataList != null) {
				int multiDataSize = multiDataList.size();
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)multiDataList.get(i);
					
					String smrize = null;
					String insttIdx = null;
					//멀티데이터의 column명을 수정하기 위한 소스
					List<Map> temp = (List<Map>) fileParam.get("dataList");
					
					Map<String, Object> tempMap2 = temp.get(1);
					tempMap2.put("columnId", "INSTT_IDX");
					insttIdx = StringUtil.getString(tempMap2.get("columnValue"));
					if(StringUtil.isEquals(tempMap2.get("columnValue"), check)) {
						smrize = "Y";
					} else {
						smrize = "N";
					}
					
					fileParam.put("SMRIZE", smrize);
					
					Map<String, Object> tempMap1 = temp.get(0);
					
					List<Map> temp2 = new ArrayList<>();
					temp2.add(tempMap1);
					temp2.add(tempMap2);
					
					fileParam.put("dataList", temp2);
					
					fileParam.put("KEY_IDX", keyIdx);
					fileParam.put("INSTT_IDX", insttIdx);
					result = areaActMultiDAO.mulInsert(fileParam);	
					
				}
			}
		*/
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
    	param.put("fnIdx", fnIdx);
    	
    	//이력남기기
    	chgLogMapper.writeDataLog("HRD_SPTJ_ARACTPLN","U", searchList);

    	// 3. DB 저장
    	// 3.1 기본 정보 테이블
		int result = areaActDAO.update(param);

		if(result > 0){
			// 3.2 multi data 저장
			// 3.2.1 multi data 삭제
			
			Map<String, Object> multiDelParam = new HashMap<String, Object>();
			multiDelParam.put("fnIdx", fnIdx);
			multiDelParam.put("KEY_IDX", keyIdx);
			
			//기존 데이터들 ISDELETE 처리
			areaActMultiDAO.mulDelete(multiDelParam);
			areaActMultiDAO.bsnsDelete(multiDelParam);
			areaActMultiDAO.policyDelete(multiDelParam);
			areaActMultiDAO.trngDelete(multiDelParam);

			// 3.2.2 multi data 등록
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			String check = StringUtil.getString(parameterMap.get("totalinsttIdx"));
			if(multiDataList != null) {
				int multiDataSize = multiDataList.size();
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)multiDataList.get(i);
					
					String smrize = null;
					String insttIdx = null;
					//멀티데이터의 column명을 수정하기 위한 소스
					List<Map> temp = (List<Map>) fileParam.get("dataList");
					
					Map<String, Object> tempMap2 = temp.get(1);
					tempMap2.put("columnId", "INSTT_IDX");
					insttIdx = StringUtil.getString(tempMap2.get("columnValue"));
					if(StringUtil.isEquals(tempMap2.get("columnValue"), check)) {
						smrize = "Y";
					} else {
						smrize = "N";
					}
					
					fileParam.put("SMRIZE", smrize);
					
					Map<String, Object> tempMap1 = temp.get(0);
					
					List<Map> temp2 = new ArrayList<>();
					temp2.add(tempMap1);
					temp2.add(tempMap2);
					
					fileParam.put("dataList", temp2);
					
					fileParam.put("KEY_IDX", keyIdx);
					fileParam.put("INSTT_IDX", insttIdx);
					areaActMultiDAO.mulInsert(fileParam);
					areaActMultiDAO.bsnsInsert(fileParam);	
					areaActMultiDAO.policyInsert(fileParam);	
					areaActMultiDAO.trngInsert(fileParam);	
					
				}
			}

			// 3.3 multi file 저장
			// 3.3.1 multi file 신규 저장
			//List fileDataList = StringUtil.getList(dataMap.get("fileDataList"));
			/*if(fileDataList != null) {
	
				// 3.3.1.1 key 얻기
				Map<String, Object> fileParam1 = new HashMap<String, Object>();
				fileParam1.put("KEY_IDX", keyIdx);
				fileParam1.put("fnIdx", fnIdx);
				int fleIdx = areaActFileDAO.getNextId(fileParam1);
				
				// 3.3.1.2 DB 저장
				int fileDataSize = fileDataList.size();
				for(int i = 0 ; i < fileDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)fileDataList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", keyIdx);
					int fleIdx = areaActFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = areaActFileDAO.insert(fileParam);
				}
			}*/
	
			// 3.3.2 multi file 수정
			/*List fileModifyDataList = StringUtil.getList(dataMap.get("fileModifyDataList"));
			if(fileModifyDataList != null) {
				int fileDataSize = fileModifyDataList.size();
				for(int i = 0 ; i < fileDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)fileModifyDataList.get(i);
					fileParam.put("KEY_IDX", keyIdx);
					fileParam.put("fnIdx", fnIdx);
					result = areaActFileDAO.update(fileParam);
				}
			}*/
			
			// 3.3.3 multi file 삭제
			/*List fileDeleteSearchList = StringUtil.getList(dataMap.get("fileDeleteSearchList"));
			if(fileDeleteSearchList != null) {
	
				List<Object> deleteMultiFileList = new ArrayList<Object>();
				int fileDataSize = fileDeleteSearchList.size();
				for(int i = 0 ; i < fileDataSize ; i ++) {
					Map<String, Object> fileParam = (HashMap)fileDeleteSearchList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("KEY_IDX", keyIdx);
					// 6.1 삭제목록 select
					List<Object> deleteFileList2 = areaActFileDAO.getList(fileParam);
					if(deleteFileList2 != null) deleteMultiFileList.addAll(deleteFileList2);
					// 6.2 DB 삭제
					result = areaActFileDAO.cdelete(fileParam);
				}
				
				// 3.3.4 multi file 삭제
				FileUtil.isKeyDelete(fileRealPath, deleteMultiFileList, "FILE_SAVED_NAME");
			}*/
			
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
    	return areaActDAO.getDeleteCount(param);
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
		return areaActDAO.getDeleteList(param);
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
    	param.put("fnIdx", fnIdx);
		
    	//이력남기기
    	chgLogMapper.writeDataLog("HRD_SPTJ_ARACTPLN","D", searchList);
    	
		// 3. DB 저장
		return areaActDAO.delete(param);
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
		return areaActDAO.restore(param);
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
		//deleteMultiFileList = areaActFileDAO.getList(param);
		// 2.2 삭제할 file(단일항목) select
		List deleteFileColumnList = ModuleUtil.getFileObjectList(items, itemOrder);
		if(deleteFileColumnList != null) {
			param.put("columnList", deleteFileColumnList);
			
			deleteFileList = areaActDAO.getFileList(param);
		}
		
		// 3. delete
		int result = areaActDAO.cdelete(param);
		if(result > 0) {
			// 4. 파일 삭제
			// 4.1 multi file 삭제
			String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
			//if(deleteMultiFileList != null) {
				FileUtil.isKeyDelete(fileRealPath, deleteMultiFileList, "FILE_SAVED_NAME");
			//}
			
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
					Map<String, Object> param = new HashMap<String, Object>();
					List<DTForm> searchList = new ArrayList<DTForm>();
			    	searchList.add(new DTForm("A." + columnName, keyIdx));
			    	searchList.add(new DTForm("A.ITEM_ID", itemId));
					param.put("searchList", searchList);
			    	param.put("fnIdx", fnIdx);
					resultHashMap.put(itemId, areaActFileDAO.getList(param));
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
				//resultHashMap = areaActFileDAO.getMapList(param);
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
					resultHashMap.put(itemId, areaActMultiDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A." + columnName, keyIdx));
		    	//searchList.add(new DTForm("A.ARACTPLN_IDX", (String[])itemIdList.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
		    	param.put("fnIdx", fnIdx);
		    	resultHashMap = areaActMultiDAO.getMapList(param);
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