package rbs.modules.doctor.service.impl;

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
import rbs.modules.doctor.mapper.DoctorMapper;
import rbs.modules.doctor.mapper.DoctorMultiMapper;
import rbs.modules.doctor.service.DoctorService;

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("doctorService")
public class DoctorServiceImpl extends EgovAbstractServiceImpl implements DoctorService {

	@Resource(name="doctorMapper")
	private DoctorMapper doctorDAO;
	
	@Resource(name="doctorMultiMapper")
	private DoctorMultiMapper doctorMultiDAO;
	
	/**
	 * 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalCount(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
    	return doctorDAO.getTotalCount(param);
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
		return doctorDAO.getList(param);
	}
	
	/**
	 * 활동내역 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getActCount(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
    	return doctorDAO.getActCount(param);
    }
    
    /**
   	 * 활동내역 전체 목록
   	 * @param fnIdx
   	 * @param param
   	 * @return
   	 */
   	@Override
   	public List<Object> getActList(int fnIdx, Map<String, Object> param) {
       	param.put("fnIdx", fnIdx);
   		return doctorDAO.getActList(param);
   	}
	
	/**
	 * 회원 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getMemberList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return doctorDAO.getMemberList(param);
	}

	/**
	 * 회원 목록수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public int getMemberCount(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
    	return doctorDAO.getMemberCount(param);
	}

	/**
	 * 해당 주치의의 소속기관 변경 유무 확인
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getIsChange(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return doctorDAO.getIsChange(param);
	}

	/**
	 * 지부지사 관할구역 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getBlockList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return doctorDAO.getBlockList(param);
	}
	
	/**
	 * 지부지사 관할구역 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getDoctorList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return doctorDAO.getDoctorList(param);
	}
	
	/**
	 * 지부지사 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getInsttList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return doctorDAO.getInsttList(param);
	}

	/**
	 * 주치의 관할구역 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getMultiList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return doctorMultiDAO.getList(param);
	}
	
	/**
	 * 주치의 요건 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getReqList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return doctorDAO.getReqList(param);
	}

	/**
	 * 현재 사용 중인 주치의 요건 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getReqMngList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return doctorDAO.getReqMngList(param);
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
		DataMap viewDAO = doctorDAO.getView(param);
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
		DataMap viewDAO = doctorDAO.getFileView(param);
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
		return doctorDAO.updateFileDown(param);
		
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
		DataMap viewDAO = doctorDAO.getModify(param);
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
    	return doctorDAO.getAuthCount(param);
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
		int keyIdx = doctorDAO.getNextId(param);

		// 전화번호 항목 암호화 하기
		String doctorTel = (String) parameterMap.get("doctorTel");
		
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
    	
		dataList.add(new DTForm("DOCTOR_TELNO", "PKG_DGUARD.FN_ENC_TELNO('" + doctorTel + "')", 1));
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
		int result = doctorDAO.insert(param);
		
		if(result > 0) {
			// 3.2 multi data 저장
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			if(multiDataList != null) {
				int multiDataSize = multiDataList.size();
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> multiParam = new HashMap<String, Object>();	
					List<DTForm> dataList2 = new ArrayList<DTForm>();		
					
					// 데이터 리스트 가져오기
					Map<String, Object> blockData = (HashMap) multiDataList.get(i);
					List<DTForm> blockList = (List<DTForm>) blockData.get("dataList");
					
					// 관할구역 코드 데이터 추출
					DTForm blockInfo = (DTForm) blockList.get(1);
					String blockCode = (String)blockInfo.get("columnValue");
					
					int blockIdx = doctorMultiDAO.getNextId(param);
					
					dataList2.add(new DTForm("DOCTOR_IDX", keyIdx));
					dataList2.add(new DTForm("BLOCK_IDX", blockIdx));
					dataList2.add(new DTForm("BLOCK_CD", blockCode));
					
					dataList2.add(new DTForm("REGI_IDX", loginMemberIdx));
					dataList2.add(new DTForm("REGI_ID", loginMemberId));
					dataList2.add(new DTForm("REGI_NAME", loginMemberName));
					dataList2.add(new DTForm("REGI_IP", regiIp));
					
					dataList2.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
					dataList2.add(new DTForm("LAST_MODI_ID", loginMemberId));
					dataList2.add(new DTForm("LAST_MODI_NAME", loginMemberName));
					dataList2.add(new DTForm("LAST_MODI_IP", regiIp));
					
					multiParam.put("dataList", dataList2);
					
					doctorMultiDAO.insert(multiParam);
				}
			}
			
			// 주치의 요건 저장
			for(int i = 1; i <= 8; i++) {
				Map<String, Object> param2 = new HashMap<String, Object>();					// 주치의 요건 테이블에 저장할 데이터
				List<DTForm> dataList3 = new ArrayList<DTForm>();							// 주치의 요건 테이블 저장항목
				
				String isSatisfy = (String) parameterMap.get("isSatisfy" + i);
				int reqMngIdx = StringUtil.getInt((String) parameterMap.get("reqMngIdx" + i));
				
				if(isSatisfy != null) {
					dataList3.add(new DTForm("DOCTOR_RQISIT_IDX", reqMngIdx));
					dataList3.add(new DTForm("DOCTOR_IDX", keyIdx));
					dataList3.add(new DTForm("ANSWER_CN", isSatisfy));
					
					dataList3.add(new DTForm("REGI_IDX", loginMemberIdx));
					dataList3.add(new DTForm("REGI_ID", loginMemberId));
					dataList3.add(new DTForm("REGI_NAME", loginMemberName));
					dataList3.add(new DTForm("REGI_IP", regiIp));
					
					dataList3.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
					dataList3.add(new DTForm("LAST_MODI_ID", loginMemberId));
					dataList3.add(new DTForm("LAST_MODI_NAME", loginMemberName));
					dataList3.add(new DTForm("LAST_MODI_IP", regiIp));
					
					param2.put("dataList", dataList3);
					
					doctorDAO.insertReq(param2);
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
    	
		// 전화번호 항목 암호화 하기
		String doctorTel = (String) parameterMap.get("doctorTel");
		
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
		
		dataList.add(new DTForm("DOCTOR_TELNO", "PKG_DGUARD.FN_ENC_TELNO('" + doctorTel + "')", 1));
    	dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
    	dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
    	dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
    	dataList.add(new DTForm("LAST_MODI_IP", regiIp));
    	
    	param.put("dataList", dataList);
    	param.put("searchList", searchList);
    	param.put("fnIdx", fnIdx);

    	// 3. DB 저장
    	// 3.1 기본 정보 테이블
		int result = doctorDAO.update(param);

		if(result > 0){
			// 3.2 multi data 저장
			// 3.2.1 multi data 삭제
			//int result1 = 0;
			Map<String, Object> multiDelParam = new HashMap<String, Object>();
			multiDelParam.put("fnIdx", fnIdx);
			multiDelParam.put("DOCTOR_IDX", keyIdx);
			doctorMultiDAO.cdelete(multiDelParam);

			// 3.2.2 multi data 등록
			// 3.2 multi data 저장
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			if(multiDataList != null) {
				int multiDataSize = multiDataList.size();
				for(int i = 0 ; i < multiDataSize ; i ++) {
					Map<String, Object> multiParam = new HashMap<String, Object>();	
					List<DTForm> dataList2 = new ArrayList<DTForm>();		
					
					// 데이터 리스트 가져오기
					Map<String, Object> blockData = (HashMap) multiDataList.get(i);
					List<DTForm> blockList = (List<DTForm>) blockData.get("dataList");
					
					// 관할구역 코드 데이터 추출
					DTForm blockInfo = (DTForm) blockList.get(1);
					String blockCode = (String)blockInfo.get("columnValue");
					
					int blockIdx = doctorMultiDAO.getNextId(param);
					
					dataList2.add(new DTForm("DOCTOR_IDX", keyIdx));
					dataList2.add(new DTForm("BLOCK_IDX", blockIdx));
					dataList2.add(new DTForm("BLOCK_CD", blockCode));
					
					dataList2.add(new DTForm("REGI_IDX", loginMemberIdx));
					dataList2.add(new DTForm("REGI_ID", loginMemberId));
					dataList2.add(new DTForm("REGI_NAME", loginMemberName));
					dataList2.add(new DTForm("REGI_IP", regiIp));
					
					dataList2.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
					dataList2.add(new DTForm("LAST_MODI_ID", loginMemberId));
					dataList2.add(new DTForm("LAST_MODI_NAME", loginMemberName));
					dataList2.add(new DTForm("LAST_MODI_IP", regiIp));
					
					multiParam.put("dataList", dataList2);
					
					doctorMultiDAO.insert(multiParam);
				}
			}
			
			// 기존의 주치의 요건 삭제
			Map<String, Object> reqDelParam = new HashMap<String, Object>();
			reqDelParam.put("fnIdx", fnIdx);
			reqDelParam.put("DOCTOR_IDX", keyIdx);
			doctorDAO.cdeleteReq(reqDelParam);
			
			// 주치의 요건 저장
			for(int i = 1; i <= 8; i++) {
				Map<String, Object> param2 = new HashMap<String, Object>();					// 주치의 요건 테이블에 저장할 데이터
				List<DTForm> dataList3 = new ArrayList<DTForm>();							// 주치의 요건 테이블 저장항목
				
				String isSatisfy = (String) parameterMap.get("isSatisfy" + i);
				int reqMngIdx = StringUtil.getInt((String) parameterMap.get("reqMngIdx" + i));
				
				if(isSatisfy != null) {
					dataList3.add(new DTForm("DOCTOR_RQISIT_IDX", reqMngIdx));
					dataList3.add(new DTForm("DOCTOR_IDX", keyIdx));
					dataList3.add(new DTForm("ANSWER_CN", isSatisfy));
					
					dataList3.add(new DTForm("REGI_IDX", loginMemberIdx));
					dataList3.add(new DTForm("REGI_ID", loginMemberId));
					dataList3.add(new DTForm("REGI_NAME", loginMemberName));
					dataList3.add(new DTForm("REGI_IP", regiIp));
					
					dataList3.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
					dataList3.add(new DTForm("LAST_MODI_ID", loginMemberId));
					dataList3.add(new DTForm("LAST_MODI_NAME", loginMemberName));
					dataList3.add(new DTForm("LAST_MODI_IP", regiIp));
					
					param2.put("dataList", dataList3);
					
					doctorDAO.insertReq(param2);
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
    	return doctorDAO.getDeleteCount(param);
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
		return doctorDAO.getDeleteList(param);
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
		
		// 3. DB 저장
    	doctorMultiDAO.delete(param);
    	doctorDAO.deleteReq(param);
    	
		return doctorDAO.delete(param);
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
		return doctorDAO.restore(param);
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
		int result = doctorDAO.cdelete(param);
		
		return result;
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
					resultHashMap.put(itemId, doctorMultiDAO.getList(param));
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
				resultHashMap = doctorMultiDAO.getMapList(param);
			}
		}
		
		return resultHashMap;
	}

}