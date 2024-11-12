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
import com.woowonsoft.egovframework.util.JSONObjectUtil;
import com.woowonsoft.egovframework.util.ModuleUtil;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import rbs.egovframework.LoginVO;
import rbs.egovframework.social.kakao.api.KakaoSenderService;
import rbs.modules.authManage.mapper.AuthManageFileMapper;
import rbs.modules.authManage.mapper.AuthManageMapper;
import rbs.modules.authManage.mapper.AuthManageMultiMapper;
import rbs.modules.authManage.service.AuthManageService;


/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("authManageService")
public class AuthManageServiceImpl extends EgovAbstractServiceImpl implements AuthManageService {

	@Resource(name="authManageMapper")
	private AuthManageMapper authManageDAO;
	
	@Resource(name="authManageFileMapper")
	private AuthManageFileMapper authManageFileDAO;
	
	@Resource(name="authManageMultiMapper")
	private AuthManageMultiMapper authManageMultiDAO;
	
	@Resource(name="kakaoSenderService")
	private KakaoSenderService kakaoSenderService;
	
	/**
	 * 전체 목록 수
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalCount(Map<String, Object> param) {
    	return authManageDAO.getTotalCount(param);
    }
    
    /**
	 * 전체 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getList(Map<String, Object> param) {
		return authManageDAO.getList(param);
	}
	
    /**
	 * 전체 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getCodeList(Map<String, Object> param) {
		return authManageDAO.getCodeList(param);
	}
	
	 /**
	 * 로그인한 회원의 authIdx
	 * @param param
	 * @return
	 */
    @Override
	public Integer getAuthIdx(Map<String, Object> param) {
		return authManageDAO.getAuthIdx(param);
	}

    /**
	 * 로그인한 회원의 authIdx, siteId, menuIdx에 따른 권한 가져오기
	 * @param param
	 * @return String
	 */
	@Override
	public DataMap getAuthMenu(Map<String, Object> param) {
		return authManageDAO.getAuthMenu(param);
	}
	
	/**
	 * 전체 권한부여 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getAuthGrant(Map<String, Object> param) {
		return authManageDAO.getAuthGrant(param);
	}
	
	/**
	 * 전체 메뉴 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getAuthList() {
		return authManageDAO.getAuthList();
	}
	
	/**
	 * 권한 그룹 목록
	 * @return
	 */
	@Override
	public List<Object> getAuthGroupList() {
		return authManageDAO.getAuthGroupList();
	}
	
	/**
	 * 권한 그룹 목록
	 * @author 이동근
	 * @param param "not in " 에 부합하는 조건
	 * @return
	 */
	@Override
	public List<Object> getAuthGroupList(Map<String, Object> param) {
		return authManageDAO.getAuthGroupListWithInCondition(param);
	}

	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getView(Map<String, Object> param) {
		DataMap viewDAO = authManageDAO.getView(param);
		return viewDAO;
	}

	/**
	 * 파일 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getFileView(Map<String, Object> param) {
		DataMap viewDAO = authManageDAO.getFileView(param);
		return viewDAO;
	}

	/**
	 * multi파일 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getMultiFileView(Map<String, Object> param) {
		DataMap viewDAO = authManageFileDAO.getFileView(param);
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
		return authManageDAO.updateFileDown(param);
		
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
		return authManageFileDAO.updateFileDown(param);
		
	}

	/**
	 * 수정 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getModify(Map<String, Object> param) {
		DataMap viewDAO = authManageDAO.getModify(param);
		return viewDAO;
	}
	
	/**
	 * 수정 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getMember(Map<String, Object> param) {
		DataMap viewDAO = authManageDAO.getMember(param);
		return viewDAO;
	}
	
	/**
	 * 권한여부
	 * @param param
	 * @return
	 */
	public int getAuthCount(Map<String, Object> param) {
    	return authManageDAO.getAuthCount(param);
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
		int keyIdx = authManageDAO.getNextId(param);

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
    	
		dataList.add(new DTForm("APPLY_YN", "Y"));
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
		int result = authManageDAO.insert(param);
		

		// 카카오톡 알림보내기
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> mailParam = new HashMap<String, Object>();				// mapper parameter 데이터
			List<Object> resultMap = new ArrayList<Object>();							// 결과값을 저장하기 위한 Map
			
			String memeberIdx = StringUtil.getString(parameterMap.get("memberIdx"));
			
			mailParam.put("MEMBER_IDX", memeberIdx);
						
			// 연락처 목록 가져오기
    		resultMap = authManageDAO.getMailList(mailParam);
    		List resultList = StringUtil.getList(resultMap);
    		int resultSize = resultList.size();
    		
    		String title = "권한변경 알림톡";
    		String templateCode = "SJB_076910";
    		
    		for(int i = 0 ; i < resultSize ; i ++) {
    			Map<String, Object> resultParam = (Map<String, Object>) resultList.get(i);
    			
    			String recevier = StringUtil.getString(resultParam.get("MOBILE_PHONE"));
 
        		String NAME =  StringUtil.getString(resultParam.get("MEMBER_NAME"));
    			
    			
	    		String msg = ""; 
	    		msg += "[한국산업인력공단] ";
	    		msg += NAME +"님의 권한이 변경되었습니다.";
    		
    			map.put("userCode", "auth" + keyIdx + i);               // 알림톡 PK
    			map.put("plusFriendId", "@hrd콘텐츠네트워크");			  // 발신자명 (홈페이지에서 발신자명 수정 시 , 수정필요)
    			map.put("templateCode", templateCode);				  // 텔픔릿코드 (홈페이지 참고)
    			map.put("title", title);							  // 제목
    			map.put("contents", msg);							  // 내용(템플릿 형식과 동일해야 발송이 됨)
    			map.put("recevier", recevier.replaceAll("-", ""));	  // 수신자 전화번호
    			kakaoSenderService.sendKaKaoTalk(map);				  // 카카오 알리톡 보내기 => 개발서버에서는 확인이 불가하니 운영서버에서만 확인 가능
    			kakaoSenderService.checkKaKaoTalk(map);				  // API 연동 결과 코드 값 확인용
    		}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("카카오톡 알림톡 오류 발생");
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
		int result = authManageDAO.update(param);

		// 카카오톡 알림보내기
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> mailParam = new HashMap<String, Object>();				// mapper parameter 데이터
			List<Object> resultMap = new ArrayList<Object>();							// 결과값을 저장하기 위한 Map
			
			String memeberIdx = StringUtil.getString(parameterMap.get("memberIdx"));
			
			mailParam.put("MEMBER_IDX", memeberIdx);
						
			// 연락처 목록 가져오기
    		resultMap = authManageDAO.getMailList(mailParam);
    		List resultList = StringUtil.getList(resultMap);
    		int resultSize = resultList.size();
    		
    		String title = "권한변경 알림톡";
    		String templateCode = "SJB_076910";
    		
    		for(int i = 0 ; i < resultSize ; i ++) {
    			Map<String, Object> resultParam = (Map<String, Object>) resultList.get(i);
    			
    			String recevier = StringUtil.getString(resultParam.get("MOBILE_PHONE"));
 
        		String NAME =  StringUtil.getString(resultParam.get("MEMBER_NAME"));
    			
    			
	    		String msg = ""; 
	    		msg += "[한국산업인력공단] ";
	    		msg += NAME +"님의 권한이 변경되었습니다.";
    		
    			map.put("userCode", "auth" + keyIdx + i);               // 알림톡 PK
    			map.put("plusFriendId", "@hrd콘텐츠네트워크");			  // 발신자명 (홈페이지에서 발신자명 수정 시 , 수정필요)
    			map.put("templateCode", templateCode);				  // 텔픔릿코드 (홈페이지 참고)
    			map.put("title", title);							  // 제목
    			map.put("contents", msg);							  // 내용(템플릿 형식과 동일해야 발송이 됨)
    			map.put("recevier", recevier.replaceAll("-", ""));	  // 수신자 전화번호
    			kakaoSenderService.sendKaKaoTalk(map);				  // 카카오 알리톡 보내기 => 개발서버에서는 확인이 불가하니 운영서버에서만 확인 가능
    			kakaoSenderService.checkKaKaoTalk(map);				  // API 연동 결과 코드 값 확인용
    		}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("카카오톡 알림톡 오류 발생");
		}
		return result;
	}
	
	/**
	 * 회원의 권한그룹 일괄변경
	 * @param authIdx
	 * @param memberList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int updateAll(String authIdx, String memberList) throws Exception {
		String[] memberArray = memberList.split(",");
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터	
		
		param.put("AUTH_IDX", authIdx);
		param.put("MEMBER_LIST", memberArray);
		
		int result = authManageDAO.updateAll(param);
		
		
		// 카카오톡 알림보내기
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> mailParam = new HashMap<String, Object>();				// mapper parameter 데이터
			List<Object> resultMap = new ArrayList<Object>();							// 결과값을 저장하기 위한 Map
			
			
			mailParam.put("AUTH_MEMBER_IDX", memberArray);
						
			// 연락처 목록 가져오기
			resultMap = authManageDAO.getMultiMailList(mailParam);
			
			List resultList = StringUtil.getList(resultMap);
			int resultSize = resultList.size();
			
			String title = "권한변경 알림톡";
			String templateCode = "SJB_076910";
			
			for(int i = 0 ; i < resultSize ; i ++) {
				
				Map<String, Object> resultParam = (Map<String, Object>) resultList.get(i);
				
				String recevier = StringUtil.getString(resultParam.get("MOBILE_PHONE"));
	 
	        	String NAME =  StringUtil.getString(resultParam.get("MEMBER_NAME"));
				
				
	    		String msg = ""; 
	    		msg += "[한국산업인력공단] ";
	    		msg += NAME +"님의 권한이 변경되었습니다.";
			
				map.put("userCode", "auth" + authIdx + i);               // 알림톡 PK
				map.put("plusFriendId", "@hrd콘텐츠네트워크");			  // 발신자명 (홈페이지에서 발신자명 수정 시 , 수정필요)
				map.put("templateCode", templateCode);				  // 텔픔릿코드 (홈페이지 참고)
				map.put("title", title);							  // 제목
				map.put("contents", msg);							  // 내용(템플릿 형식과 동일해야 발송이 됨)
				map.put("recevier", recevier.replaceAll("-", ""));	  // 수신자 전화번호
				kakaoSenderService.sendKaKaoTalk(map);				  // 카카오 알리톡 보내기 => 개발서버에서는 확인이 불가하니 운영서버에서만 확인 가능
				kakaoSenderService.checkKaKaoTalk(map);				  // API 연동 결과 코드 값 확인용
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("카카오톡 알림톡 오류 발생");
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
    	return authManageDAO.getDeleteCount(param);
    }

    /**
	 * 삭제 전체 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getDeleteList(Map<String, Object> param) {
		return authManageDAO.getDeleteList(param);
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
	public int apply(ParamForm parameterMap, String keyIdx, String regiIp, JSONObject settingInfo) throws Exception {
		if(keyIdx == null) return 0;
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>();						// 저장항목

		// 1. 저장조건
    	/*String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");*/
    	searchList.add(new DTForm("MEMBER_IDX", keyIdx));
    	
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
		return authManageDAO.apply(param);
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
		return authManageDAO.delete(param);
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
		return authManageDAO.restore(param);
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
		deleteMultiFileList = authManageFileDAO.getList(param);
		// 2.2 삭제할 file(단일항목) select
		List deleteFileColumnList = ModuleUtil.getFileObjectList(items, itemOrder);
		if(deleteFileColumnList != null) {
			param.put("columnList", deleteFileColumnList);
			
			deleteFileList = authManageDAO.getFileList(param);
		}*/
		
		// 3. delete
		int result = authManageDAO.cdelete(param);
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
					resultHashMap.put(itemId, authManageFileDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A." + columnName, keyIdx));
		    	searchList.add(new DTForm("A.ITEM_ID", (String[])itemIdList.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
				resultHashMap = authManageFileDAO.getMapList(param);
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
					resultHashMap.put(itemId, authManageMultiDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A." + columnName, keyIdx));
		    	//searchList.add(new DTForm("A.ITEM_ID", (String[])itemIdList.toArray(new String[itemIdList.size()])));
				param.put(columnName, keyIdx);
				resultHashMap = authManageMultiDAO.getMapList(param);
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