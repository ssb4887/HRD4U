package rbs.modules.instt.service.impl;

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
import rbs.modules.instt.mapper.InsttFileMapper;
import rbs.modules.instt.mapper.InsttMapper;
import rbs.modules.instt.mapper.InsttMultiMapper;
import rbs.modules.instt.service.InsttService;

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("insttService")
public class InsttServiceImpl extends EgovAbstractServiceImpl implements InsttService {

	@Resource(name="insttMapper")
	private InsttMapper insttDAO;
	
	@Resource(name="insttFileMapper")
	private InsttFileMapper insttFileDAO;
	
	@Resource(name="insttMultiMapper")
	private InsttMultiMapper insttMultiDAO;
	
	/**
	 * 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalCount(Map<String, Object> param) {
    	return insttDAO.getTotalCount(param);
    }
    
    /**
	 * 우편변호별 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalMailCount(Map<String, Object> param) {
    	return insttDAO.getTotalMailCount(param);
    }
    
    /**
	 * 관할구역 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getMultiTotalCount(Map<String, Object> param) {
		return insttMultiDAO.getTotalCount(param);
	}

	/**
	 * 전체 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getList(Map<String, Object> param) {
		return insttDAO.getList(param);
	}
	
	/**
	 * 우편번호별 전체 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getMailList(Map<String, Object> param) {
		return insttDAO.getMailList(param);
	}
	
	/**
	 * 관할구역 전체 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getMultiList(Map<String, Object> param) {
		return insttMultiDAO.getList(param);
	}

	/**
	 * 관할구역 목록 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getOptionList(Map<String, Object> param) {
		return insttMultiDAO.getOptionList(param);
	}
	
	@Override
	public List<Object> getInsttList() {
		Map<String, Object> param = new HashMap<String, Object>();
		return insttDAO.getInsttList(param);
	}

	/**
	 * 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getView(Map<String, Object> param) {
		DataMap viewDAO = insttDAO.getView(param);
		return viewDAO;
	}

	/**
	 * 시/도의 군/구 갯수 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getOptionCount(Map<String, Object> param) {
		return insttDAO.getOptionCount(param);
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
		DataMap viewDAO = insttDAO.getFileView(param);
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
		DataMap viewDAO = insttFileDAO.getFileView(param);
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
		param.put("INSTT_IDX", keyIdx);
    	param.put("searchList", searchList);
    	param.put("FILE_COLUMN", fileColumnName);
		return insttDAO.updateFileDown(param);
		
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
		param.put("INSTT_IDX", keyIdx);
		return insttFileDAO.updateFileDown(param);
		
	}

	/**
	 * 수정 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getModify(Map<String, Object> param) {
		DataMap viewDAO = insttDAO.getModify(param);
		return viewDAO;
	}
	
	/**
	 * 권한여부
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getAuthCount(Map<String, Object> param) {
    	return insttDAO.getAuthCount(param);
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
	public int insert(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception  {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> dataList = new ArrayList<DTForm>();							// 저장항목

		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");	// key 컬럼
				
		// 1. key 얻기
		int keyIdx = insttDAO.getNextId(param);

		// 전화번호 항목 암호화하기
		String phone = (String) parameterMap.get("phone");
		
		// 2. 항목설정으로 저장항목 setting
		//String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(parameterMap, settingInfo, items, itemOrder);
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
		
		dataList.add(new DTForm("TELNO", "PKG_DGUARD.FN_ENC_TELNO('" + phone + "')", 1));
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
		int result = insttDAO.insert(param);
		
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
					
					int blockIdx = insttMultiDAO.getNextId(param);
					
					dataList2.add(new DTForm("INSTT_IDX", keyIdx));
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
					
					result = insttMultiDAO.insert(multiParam);
				}
			}
		}
		
		return result > 0 ? keyIdx : result;
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
	public int mailInsert(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception  {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> dataList = new ArrayList<DTForm>();							// 저장항목

		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");	// key 컬럼
				
		// 1. key 얻기
		//int keyIdx = insttDAO.getNextId(param);
		String zip = StringUtil.getString(parameterMap.get("zip"));
		String sido = StringUtil.getString(parameterMap.get("sido"));
		String gugun = StringUtil.getString(parameterMap.get("gugun"));
		String insttIdx = StringUtil.getString(parameterMap.get("insttIdx"));

		// 2. 항목설정으로 저장항목 setting
		//String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		
		//dataList.add(new DTForm(columnName, keyIdx));

		// 2.2 등록자 정보
		/*LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
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
		dataList.add(new DTForm("LAST_MODI_IP", regiIp));*/
    	
		param.put("ZIP", zip);
		param.put("CTPRVN", sido);
		param.put("SIGNGU", gugun);
		param.put("INSTT_IDX", insttIdx);

		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = insttDAO.mailInsert(param);
		
		/*if(result > 0) {
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
					
					int blockIdx = insttMultiDAO.getNextId(param);
					
					dataList2.add(new DTForm("INSTT_IDX", keyIdx));
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
					
					result = insttMultiDAO.insert(multiParam);
				}
			}
		}*/
		
		return result;
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
	public int update(String uploadModulePath, int keyIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();							// 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>();							// 저장항목
    	
		// 전화번호 항목 암호화하기
		String phone = (String) parameterMap.get("phone");
		
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
		
		dataList.add(new DTForm("TELNO", "PKG_DGUARD.FN_ENC_TELNO('" + phone + "')", 1));
    	dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
    	dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
    	dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
    	dataList.add(new DTForm("LAST_MODI_IP", regiIp));
    	
    	param.put("dataList", dataList);
    	param.put("searchList", searchList);

    	// 3. DB 저장
    	// 3.1 기본 정보 테이블
		int result = insttDAO.update(param);

		if(result > 0){
			// 3.2 multi data 저장
			// 3.2.1 multi data 삭제
			//int result1 = 0;
			Map<String, Object> multiDelParam = new HashMap<String, Object>();
			multiDelParam.put("INSTT_IDX", keyIdx);
			insttMultiDAO.cdelete(multiDelParam);

			// 3.2.2 multi data 등록
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
					
					int blockIdx = insttMultiDAO.getNextId(param);
					
					dataList2.add(new DTForm("INSTT_IDX", keyIdx));
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
					
					result = insttMultiDAO.insert(multiParam);
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
	public int getDeleteCount(Map<String, Object> param) {
    	return insttDAO.getDeleteCount(param);
    }

    /**
	 * 삭제 전체 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getDeleteList(Map<String, Object> param) {
		return insttDAO.getDeleteList(param);
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
	public int delete(ParamForm parameterMap, int[] deleteIdxs, String regiIp, JSONObject settingInfo) throws Exception {
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
		insttMultiDAO.delete(param);
		
		return insttDAO.delete(param);
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
	public int mailDelete(ParamForm parameterMap, String[] deleteIdxs, String regiIp, JSONObject settingInfo) throws Exception {
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
		//insttMultiDAO.delete(param);
		
		param.put("DELLIST", deleteIdxs);
		
		return insttDAO.mailDelete(param);
	}
	
	
	//기업회원 일괄변경
	@Override
	public int zipAllUpdate(String insttIdx, String zipList,  String regiIp) {
		Map<String, Object> param = new HashMap<String, Object>();
		int result = 0;
		
		String[] zipArray = zipList.split(",");
		
		param.put("ZIPLIST", zipArray);
		param.put("INSTT_IDX", insttIdx);
		
		
		result = insttDAO.zipAllUpdate(param);
		
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
	public int restore(int[] restoreIdxs, String regiIp, JSONObject settingInfo) throws Exception {
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
		insttMultiDAO.restore(param);
		
		return insttDAO.restore(param);
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
	public int cdelete(String uploadModulePath, int[] deleteIdxs, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception {
		if(deleteIdxs == null) return 0;

		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		
    	// 1. 저장조건
		searchList.add(new DTForm(JSONObjectUtil.getString(settingInfo, "idx_column"), deleteIdxs));
		param.put("searchList", searchList);
		
		// 2. delete
		insttMultiDAO.cdelete(param);
		
		int result = insttDAO.cdelete(param);
		
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
	public List<Object> getMultiHashMap(int keyIdx, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) {
		List<Object> resultHashMap = new ArrayList<>();
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
					resultHashMap.put(itemId, insttMultiDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A." + columnName, keyIdx));
				param.put("searchList", searchList);
				resultHashMap = insttMultiDAO.getMapList(param);
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