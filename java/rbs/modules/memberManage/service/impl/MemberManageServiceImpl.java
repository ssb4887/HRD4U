package rbs.modules.memberManage.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import rbs.egovframework.social.kakao.api.KakaoSenderService;
import rbs.modules.memberManage.mapper.MemberManageFileMapper;
import rbs.modules.memberManage.mapper.MemberManageMapper;
import rbs.modules.memberManage.mapper.MemberManageMultiMapper;
import rbs.modules.memberManage.service.MemberManageService;


/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("memberManageService")
public class MemberManageServiceImpl extends EgovAbstractServiceImpl implements MemberManageService {

	@Resource(name="memberManageMapper")
	private MemberManageMapper memberManageDAO;
	
	@Resource(name="memberManageFileMapper")
	private MemberManageFileMapper memberManageFileDAO;
	
	@Resource(name="memberManageMultiMapper")
	private MemberManageMultiMapper memberManageMultiDAO;
	
	@Resource(name="kakaoSenderService")
	private KakaoSenderService kakaoSenderService;
	
	/**
	 * 전체 목록 수
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalCount(Map<String, Object> param) {
    	return memberManageDAO.getTotalCount(param);
    }
    
    /**
	 * 전체 목록 수
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalCenterCount(Map<String, Object> param) {
    	return memberManageDAO.getTotalCenterCount(param);
    }
    
    /**
   	 * 전체 목록 수
   	 * @param param
   	 * @return
   	 */
       @Override
   	public int getTotalCenterAuthCount(Map<String, Object> param) {
       	return memberManageDAO.getTotalCenterAuthCount(param);
       }
       
       /**
   	 * 전체 목록
   	 * @param param
   	 * @return
   	 */
   	@Override
   	public List<Object> getCodeList(Map<String, Object> param) {
   		return memberManageDAO.getCodeList(param);
   	}
     
    /**
 	 * 기업회원 소속변경 신청 전체 목록 수
 	 * @param param
 	 * @return
 	 */
     @Override
 	public int getTotalCorpRegiCount(Map<String, Object> param) {
     	return memberManageDAO.getTotalCorpRegiCount(param);
     }   
     
    /**
  	 * 공단소속 변경신청 전체 목록 수
  	 * @param param
  	 * @return
  	 */
      @Override
  	public int getTotalEmployRegiCount(Map<String, Object> param) {
      	return memberManageDAO.getTotalEmployRegiCount(param);
      }
     
      /**
 	 * 기업회원 소속기관 변경신청 이전 기록 확인 
 	 * @param param
 	 * @return
 	 */
     @Override
 	public int checkCorpReq(Map<String, Object> param) {
     	return memberManageDAO.checkCorpReq(param);
     }
     
     /**
	 * 민간센터 권한신청 이전 신청 기록 확인 
	 * @param param
	 * @return
	 */
    @Override
	public int checkCenterReq(Map<String, Object> param) {
    	return memberManageDAO.checkCenterReq(param);
    }
    
    /**
	 * 민간센터 권한신청 적용중인 권한 확인 
	 * @param param
	 * @return
	 */
    @Override
	public int checkCenterApply(Map<String, Object> param) {
    	return memberManageDAO.checkCenterApply(param);
    }
    
    /**
   	 * 소속기관, 본부직원 공단소속 주치의 등록이 되어 있는지 확인 
   	 * @param param
   	 * @return
   	 */
     @Override
   	public int checkEmpRegi(Map<String, Object> param) {
       	return memberManageDAO.checkEmpRegi(param);
     }
    
    /**
   	 * 소속기관, 본부직원 공단소속 변경신청 이전 기록 확인 
   	 * @param param
   	 * @return
   	 */
    @Override
   	public int checkEmpReq(Map<String, Object> param) {
       	return memberManageDAO.checkEmpReq(param);
     }
       
	/**
	 * 전체 목록 수
	 * @param param
	 * @return
	 */
	@Override
   	public int getTotalEmpReqCount(Map<String, Object> param) {
       	return memberManageDAO.getTotalEmpReqCount(param);
    }
    
    /**
	 * 전체 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getList(Map<String, Object> param) {
		return memberManageDAO.getList(param);
	}
	
	/**
	 * 전체 권한부여 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getCenterAuthList(Map<String, Object> param) {
		return memberManageDAO.getCenterAuthList(param);
	}
	
	/**
	 * 기업회원 소속기관 변경신청 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getCorpRegiList(Map<String, Object> param) {
		return memberManageDAO.getCorpRegiList(param);
	}
	
	/**
	 * 공단소속 변경신청 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getEmployRegiList(Map<String, Object> param) {
		return memberManageDAO.getEmployRegiList(param);
	}
	
	/**
	 * 전체 메뉴 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getAuthList() {
		return memberManageDAO.getAuthList();
	}
	
	/**
	 * 기관코드 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getCodeList() {
		return memberManageDAO.getCodeList();
	}
	
	/**
	 * 기관코드 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getGradeList() {
		return memberManageDAO.getGradeList();
	}
	
	/**
	 * 권한 그룹 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getAuthGroupList() {
		return memberManageDAO.getAuthGroupList();
	}
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getView(Map<String, Object> param) {
		DataMap viewDAO = memberManageDAO.getView(param);
		return viewDAO;
	}
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getViewCorp(Map<String, Object> param) {
		DataMap viewDAO = memberManageDAO.getViewCorp(param);
		return viewDAO;
	}
	

	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getPreOrg(Map<String, Object> param) {
		DataMap viewDAO = memberManageDAO.getPreOrg(param);
		return viewDAO;
	}
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getViewOrg(Map<String, Object> param) {
		DataMap viewDAO = memberManageDAO.getViewOrg(param);
		return viewDAO;
	}

	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getViewRegi(Map<String, Object> param) {
		return memberManageDAO.getViewRegi(param);
	}
	
	/**
	 * 주치의 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getViewDoctor(Map<String, Object> param) {
		DataMap viewDAO = memberManageDAO.getViewDoctor(param); 
		return viewDAO;
		
	}
	
	/**
	 * 주치의 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getDoctorInfo(Map<String, Object> param) {
		return memberManageDAO.getDoctorInfo(param);
	}
	
	/**
	 * 전담 주치의 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getViewCliDoctor(Map<String, Object> param) {
		DataMap viewDAO = memberManageDAO.getViewCliDoctor(param);
		return viewDAO;
	}
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getWebReq(Map<String, Object> param) {
		return memberManageDAO.getWebReq(param);
	}
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getAuth(Map<String, Object> param) {
		return memberManageDAO.getAuth(param);
	}
	
	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getDctReq(Map<String, Object> param) {
		return memberManageDAO.getDctReq(param);
	}

	/**
	 * 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getEmpDoctor(Map<String, Object> param) {
		return memberManageDAO.getEmpDoctor(param);
	}
	
	/**
	 * 파일 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getFileView(Map<String, Object> param) {
		DataMap viewDAO = memberManageDAO.getFileView(param);
		return viewDAO;
	}

	/**
	 * multi파일 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getMultiFileView(Map<String, Object> param) {
		DataMap viewDAO = memberManageFileDAO.getFileView(param);
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
		return memberManageDAO.updateFileDown(param);
		
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
		return memberManageFileDAO.updateFileDown(param);
		
	}

	/**
	 * 수정 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getModify(Map<String, Object> param) {
		DataMap viewDAO = memberManageDAO.getModify(param);
		return viewDAO;
	}
	
	/**
	 * 수정 상세조회
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getMember(Map<String, Object> param) {
		DataMap viewDAO = memberManageDAO.getMember(param);
		return viewDAO;
	}
	
	/**
	 * 권한여부
	 * @param param
	 * @return
	 */
	public int getAuthCount(Map<String, Object> param) {
    	return memberManageDAO.getAuthCount(param);
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
		int keyIdx = memberManageDAO.getNextId(param);

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
    	
		dataList.add(new DTForm("APPLY_YN", 0));
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
		int result = memberManageDAO.insert(param);
		

		/*String preAuthList = StringUtil.getString(parameterMap.get("authList"));
		System.out.println("12312312132"+preAuthList);
		String[] authList = StringUtil.stringToArray(preAuthList, ",");
		System.out.println("stringToArray "+authList);
		System.out.println("getList "+authList[0]);
		System.out.println("getList "+authList[20]);
		*/
		/*if(result > 0) {
			
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
						
						System.out.println(fileParam);
						result = memberManageMultiDAO.insert(fileParam);
						
						
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
					int fleIdx = memberManageFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = memberManageFileDAO.insert(fileParam);
				}
			}
		}*/
		
		return result;
	}
	
	/**
	 * 기관 등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
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
	public int insertOrg(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception  {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> dataList = new ArrayList<DTForm>();							// 저장항목

		//String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");	// key 컬럼
				
		// 1. key 얻기
		int keyIdx = memberManageDAO.getOrgNextId(param);

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		
		//int preInstt = StringUtil.getInt(parameterMap.get("PRE_INSTT_IDX"));
		//int instt = StringUtil.getInt(parameterMap.get("INSTT_IDX"));
		
		dataList.add(new DTForm("PSITN_INSTT_IDX", keyIdx));

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
    	
		dataList.add(new DTForm("APPLY_YN", "N"));
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
		int result = memberManageDAO.insertOrg(param);
		

		/*String preAuthList = StringUtil.getString(parameterMap.get("authList"));
		System.out.println("12312312132"+preAuthList);
		String[] authList = StringUtil.stringToArray(preAuthList, ",");
		System.out.println("stringToArray "+authList);
		System.out.println("getList "+authList[0]);
		System.out.println("getList "+authList[20]);
		*/
		/*if(result > 0) {
			
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
						
						System.out.println(fileParam);
						result = memberManageMultiDAO.insert(fileParam);
						
						
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
					int fleIdx = memberManageFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = memberManageFileDAO.insert(fileParam);
				}
			}
		}*/
		
		return result;
	}
	
	/**
	 * 민간센터 등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
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
	public int insertCenter(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception  {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> dataList = new ArrayList<DTForm>();							// 저장항목

		//String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");	// key 컬럼
				
		// 1. key 얻기
		int keyIdx = memberManageDAO.getCenterNextId(param);

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		
		dataList.add(new DTForm("AUTH_REQ_IDX", keyIdx));

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
    	
		dataList.add(new DTForm("STATUS", "2"));
		dataList.add(new DTForm("APPLY_YN", "N"));
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
		int result = memberManageDAO.insertCenter(param);
		

		/*String preAuthList = StringUtil.getString(parameterMap.get("authList"));
		System.out.println("12312312132"+preAuthList);
		String[] authList = StringUtil.stringToArray(preAuthList, ",");
		System.out.println("stringToArray "+authList);
		System.out.println("getList "+authList[0]);
		System.out.println("getList "+authList[20]);
		*/
		/*if(result > 0) {
			
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
						
						System.out.println(fileParam);
						result = memberManageMultiDAO.insert(fileParam);
						
						
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
					int fleIdx = memberManageFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = memberManageFileDAO.insert(fileParam);
				}
			}
		}*/
		
		return result;
	}
	
	/**
	 * 컨설턴트 등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
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
	public int insertConsult(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception  {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> dataList = new ArrayList<DTForm>();							// 저장항목

		//String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");	// key 컬럼
				
		// 1. key 얻기
		
		String keyIdx = StringUtil.getString(parameterMap.get("memberIdx"));

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
    	
		/*dataList.add(new DTForm("APPLY_YN", "Y"));*/
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
		int result = 0;
		
		List fileDataList = StringUtil.getList(dataMap.get("fileDataList"));
		if(fileDataList != null) {
			int fileDataSize = fileDataList.size();
			for(int i = 0 ; i < fileDataSize ; i ++) {
				Map<String, Object> fileParam = (HashMap)fileDataList.get(i);
				fileParam.put("KEY_IDX", keyIdx);
				int fleIdx = memberManageFileDAO.getNextId(fileParam);
				fileParam.put("FLE_IDX", fleIdx);
				result = memberManageFileDAO.insert(fileParam);
			}
		}
		
		
		// 5 multi file 수정
		List fileModifyDataList = StringUtil.getList(dataMap.get("fileModifyDataList"));
		if(fileModifyDataList != null) {
			int fileDataSize = fileModifyDataList.size();
			for(int i = 0 ; i < fileDataSize ; i ++) {
				Map<String, Object> fileParam = (HashMap)fileModifyDataList.get(i);
				fileParam.put("KEY_IDX", keyIdx);
				int fleIdx = memberManageFileDAO.getNextId(fileParam);
				fileParam.put("FLE_IDX", fleIdx);
				result = memberManageFileDAO.update(fileParam);
			}
		}

		
				
		// 6. multi file 삭제
		List fileDeleteSearchList = StringUtil.getList(dataMap.get("fileDeleteSearchList"));
		if(fileDeleteSearchList != null) {

			List<Object> deleteMultiFileList = new ArrayList<Object>();
			int fileDataSize = fileDeleteSearchList.size();
			for(int i = 0 ; i < fileDataSize ; i ++) {
				Map<String, Object> fileParam = (HashMap)fileDeleteSearchList.get(i);
				fileParam.put("KEY_IDX", keyIdx);
				int fleIdx = memberManageFileDAO.getNextId(fileParam);
				fileParam.put("FLE_IDX", fleIdx);
				// 6.1 삭제목록 select
				List<Object> deleteFileList2 = memberManageFileDAO.getList(fileParam);
				if(deleteFileList2 != null) deleteMultiFileList.addAll(deleteFileList2);
				// 6.2 DB 삭제
				result = memberManageFileDAO.cdelete(fileParam);
			}
			
			// 6.3 multi file 삭제
			FileUtil.isKeyDelete(fileRealPath, deleteMultiFileList, "FILE_SAVED_NAME");
			/*
			int deleteFileListSize = deleteFileList.size();
			if(deleteFileListSize > 0) {
				for(int i = 0 ; i < deleteFileListSize ; i ++) {
					DataMap listDt = (DataMap)deleteFileList.get(i);
					FileUtil.isDelete(fileRealPath, StringUtil.getString(listDt.get("FILE_SAVED_NAME")));
				}
			}*/
				}
				
		return result;
	}
	
	/**
	 * 본부직원 등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
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
	public int insertEmploy(String uploadModulePath, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception  {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return -1;
		
		Map<String, Object> param = new HashMap<String, Object>();					// mapper parameter 데이터
		List<DTForm> dataList = new ArrayList<DTForm>();							// 저장항목

		//String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");	// key 컬럼
				
		// 1. key 얻기
		int keyIdx = memberManageDAO.getEmployNextId(param);
		String gradeCode = StringUtil.getString(parameterMap.get("grade"));
		String ofcps = StringUtil.getString(parameterMap.get("ofcps"));
		String deptName = StringUtil.getString(parameterMap.get("deptName"));
		String telNo = StringUtil.getString(parameterMap.get("telNo"));

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return -1;
		
		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if(itemDataList != null) dataList.addAll(itemDataList);
		
		dataList.add(new DTForm("DOCTOR_IDX", keyIdx));
		dataList.add(new DTForm("CLSF_CD", gradeCode));
		dataList.add(new DTForm("STATUS", "2"));
		dataList.add(new DTForm("DOCTOR_OFCPS", ofcps));
		dataList.add(new DTForm("DOCTOR_DEPT_NAME", deptName));
		dataList.add(new DTForm("DOCTOR_TELNO", telNo));
		
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
    	
		/*dataList.add(new DTForm("APPLY_YN", "N"));*/
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
		int result = memberManageDAO.insertEmploy(param);
		

		/*String preAuthList = StringUtil.getString(parameterMap.get("authList"));
		System.out.println("12312312132"+preAuthList);
		String[] authList = StringUtil.stringToArray(preAuthList, ",");
		System.out.println("stringToArray "+authList);
		System.out.println("getList "+authList[0]);
		System.out.println("getList "+authList[20]);
		*/
		/*if(result > 0) {
			
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
						
						System.out.println(fileParam);
						result = memberManageMultiDAO.insert(fileParam);
						
						
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
					int fleIdx = memberManageFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = memberManageFileDAO.insert(fileParam);
				}
			}
		}*/
		
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
		int result = memberManageDAO.update(param);

		/*if(result > 0){
			// 3.2 multi data 저장
			// 3.2.1 multi data 삭제
			int result1 = 0;
			Map<String, Object> multiDelParam = new HashMap<String, Object>();
			multiDelParam.put("AUTH_IDX", keyIdx);
			result1 = memberManageMultiDAO.cdelete(multiDelParam);

			// 3.2.2 multi data 등록
			String preAuthList = StringUtil.getString(parameterMap.get("authList"));
			String[] authList = StringUtil.stringToArray(preAuthList, ",");
			if(authList != null) {
				int multiDataSize = authList.length;
				System.out.println("얼마냐" + multiDataSize);
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
						
						System.out.println(fileParam);
						result = memberManageMultiDAO.insert(fileParam);
						
						
					}
					
				}
			}

		

		}*/
		return result;
	}

	/**
	 * 삭제 전체 목록 수
	 * @param param
	 * @return
	 */
    @Override
	public int getDeleteCount(Map<String, Object> param) {
    	return memberManageDAO.getDeleteCount(param);
    }

    /**
	 * 삭제 전체 목록
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getDeleteList(Map<String, Object> param) {
		return memberManageDAO.getDeleteList(param);
	}

	/**
	 * 승인처리 : 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
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
		return memberManageDAO.apply(param);
	}
	
	/**
	 * 민간센터 승인처리 : 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
	 * @param parameterMap
	 * @param deleteIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int applyCenter(ParamForm parameterMap, String regiIp, JSONObject settingInfo) throws Exception {
		
		String reqIdx = StringUtil.getString(parameterMap.get("reqIdx"));
		String memberIdx = StringUtil.getString(parameterMap.get("memberIdx"));
		String start = StringUtil.getString(parameterMap.get("startDateVal")); 
		String end = StringUtil.getString(parameterMap.get("endDateVal"));  
		String auth = StringUtil.getString(parameterMap.get("auth")); 
		String apply = StringUtil.getString(parameterMap.get("apply"));
		String status = null;
		
		if(StringUtil.isEquals(apply, "Y")) status = "1";
		if(StringUtil.isEquals(apply, "N")) status = "0";
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		Map<String, Object> authparam = new HashMap<String, Object>();			// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>();						// 저장항목

		// 1. 저장조건
    	/*String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");*/
    	
    	
		// 2. 저장 항목
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if(loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		
    	dataList.add(new DTForm("STATUS", status));
    	dataList.add(new DTForm("APPLY_YN", apply));
		
    	dataList.add(new DTForm("CONFMER_IDX", loginMemberIdx));
    	dataList.add(new DTForm("CONFMER_ID", loginMemberId));
    	dataList.add(new DTForm("CONFMER_NAME", loginMemberName));
    	dataList.add(new DTForm("CONFMER_IP", regiIp));
    	
    	dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
    	dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
    	dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
    	dataList.add(new DTForm("LAST_MODI_IP", regiIp));
    	
		param.put("searchList", searchList);
		param.put("dataList", dataList);
		param.put("AUTH_REQ_IDX", reqIdx);
						
		
		if(StringUtil.isEquals(apply, "Y")) {
			
			memberManageDAO.applyCenter(param);
			
			dataList = new ArrayList<DTForm>();
			

			dataList.add(new DTForm("START_DATE", start));
			dataList.add(new DTForm("END_DATE", end));
	    	dataList.add(new DTForm("APPLY_YN", apply));
	    	
	    	dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
	    	dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
	    	dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
	    	dataList.add(new DTForm("LAST_MODI_IP", regiIp));
			
	    	
			authparam.put("AUTH_REQ_IDX", reqIdx);
			authparam.put("AUTH_IDX", auth);
			authparam.put("MEMBER_IDX", memberIdx);
			
			int keyIdx = memberManageDAO.getCenMemNextId(authparam);
			
			dataList.add(new DTForm("AUTH_MEMBER_IDX", keyIdx));
				
			int checkData = memberManageDAO.checkData(authparam);
			
			if(checkData == 0) {
				dataList.add(new DTForm("REGI_IDX", loginMemberIdx));
		    	dataList.add(new DTForm("REGI_ID", loginMemberId));
		    	dataList.add(new DTForm("REGI_NAME", loginMemberName));
		    	dataList.add(new DTForm("REGI_IP", regiIp));
		    	
		    	authparam.put("dataList", dataList);
				return memberManageDAO.insertCenterMember(authparam);
			}
			
			authparam.put("dataList", dataList);
			
			// 3. DB 저장
			return memberManageDAO.applyCenterMember(authparam);
		}
		int result = memberManageDAO.applyCenter(param);
		
		// 카카오톡 알림보내기
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> mailParam = new HashMap<String, Object>();				// mapper parameter 데이터
			List<Object> resultMap = new ArrayList<Object>();							// 결과값을 저장하기 위한 Map
			
			
			mailParam.put("MEMBER_IDX", memberIdx);
						
			// 연락처 목록 가져오기
			resultMap = memberManageDAO.getOrgMailList(mailParam);
			
			List resultList = StringUtil.getList(resultMap);
			int resultSize = resultList.size();
			String APPLY = "";
			
			String title = "권한변경 알림톡";
			String templateCode = "SJB_076911";
			
			for(int i = 0 ; i < resultSize ; i ++) {
				
				Map<String, Object> resultParam = (Map<String, Object>) resultList.get(i);
				
				String recevier = StringUtil.getString(resultParam.get("MOBILE_PHONE"));
	 
	        	String NAME =  StringUtil.getString(resultParam.get("MEMBER_NAME"));
				
				
	        	if(StringUtil.isEquals(status, "3")) {
	        		APPLY = "승인";
	        	} else {
	        		APPLY = "반려";
	        	}
				
	    		String msg = ""; 
	    		msg += "[한국산업인력공단] ";
	    		msg += NAME +"님의 공단소속 변경신청건이 " + APPLY +"되었습니다.";
			
				map.put("userCode", "orgMulti" + i);               // 알림톡 PK
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
	 * 소속기관, 본부 승인처리 : 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
	 * @param parameterMap
	 * @param deleteIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int applyEmp(ParamForm parameterMap, String regiIp, JSONObject settingInfo) throws Exception {
		
		String memberIdx = StringUtil.getString(parameterMap.get("memIdx"));
		String docIdx = StringUtil.getString(parameterMap.get("docIdx")); 
		String instt = StringUtil.getString(parameterMap.get("instt"));  
		String preInstt = StringUtil.getString(parameterMap.get("preInstt"));  
		String gradeVal = StringUtil.getString(parameterMap.get("gradeVal")); 
		String stat = StringUtil.getString(parameterMap.get("stat"));
		String apply = null;
		
		if(StringUtil.isEquals(stat, "0")) apply = "N";
		if(StringUtil.isEquals(stat, "3")) apply = "Y";

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>();						// 저장항목

		// 1. 저장조건
    	/*String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");*/
    	
    	
		// 2. 저장 항목
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if(loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		
		dataList.add(new DTForm("APPLY_YN", apply));
		dataList.add(new DTForm("STATUS", stat));
		dataList.add(new DTForm("INSTT_IDX", instt));
		dataList.add(new DTForm("PRE_INSTT_IDX", preInstt));
		dataList.add(new DTForm("CLSF_CD", gradeVal));
		
    	dataList.add(new DTForm("CONFMER_IDX", loginMemberIdx));
    	dataList.add(new DTForm("CONFMER_ID", loginMemberId));
    	dataList.add(new DTForm("CONFMER_NAME", loginMemberName));
    	dataList.add(new DTForm("CONFMER_IP", regiIp));
    	
    	dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
    	dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
    	dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
    	dataList.add(new DTForm("LAST_MODI_IP", regiIp));
    	
		param.put("searchList", searchList);
		param.put("dataList", dataList);
		
		param.put("DOCTOR_IDX", docIdx);
		param.put("MEMBER_IDX", memberIdx);
		
		
		//주치의가 직접 등록했을 경우의 값
		if(StringUtil.isEquals(docIdx, "")) {
			int keyIdx = memberManageDAO.getEmployNextId(param);
			String ofcps = StringUtil.getString(parameterMap.get("ofcps"));
			String deptName = StringUtil.getString(parameterMap.get("deptName"));
			String telNo = StringUtil.getString(parameterMap.get("telNo"));
			
			
			
			dataList.add(new DTForm("DOCTOR_OFCPS", ofcps));
			dataList.add(new DTForm("DOCTOR_DEPT_NAME", deptName));
			dataList.add(new DTForm("DOCTOR_TELNO", telNo));
			
			dataList.add(new DTForm("REGI_IDX", loginMemberIdx));
			dataList.add(new DTForm("REGI_ID", loginMemberId));
			dataList.add(new DTForm("REGI_NAME", loginMemberName));
			dataList.add(new DTForm("REGI_IP", regiIp));
			
			param.put("DOCTOR_IDX", keyIdx);
		}
		
		if(StringUtil.isEquals(apply, "Y")) {
			memberManageDAO.applyAllNotEmp(param);
		}
		
		int result = 0;
		// 3. DB 저장
		result = memberManageDAO.applyEmp(param);		
		
		// 카카오톡 알림보내기
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> mailParam = new HashMap<String, Object>();				// mapper parameter 데이터
			List<Object> resultMap = new ArrayList<Object>();							// 결과값을 저장하기 위한 Map
			
			
			mailParam.put("MEMBER_IDX", memberIdx);
						
			// 연락처 목록 가져오기
			resultMap = memberManageDAO.getOrgMailList(mailParam);
			
			List resultList = StringUtil.getList(resultMap);
			int resultSize = resultList.size();
			String APPLY = "";
			
			String title = "권한변경 알림톡";
			String templateCode = "SJB_076911";
			
			for(int i = 0 ; i < resultSize ; i ++) {
				
				Map<String, Object> resultParam = (Map<String, Object>) resultList.get(i);
				
				String recevier = StringUtil.getString(resultParam.get("MOBILE_PHONE"));
	 
	        	String NAME =  StringUtil.getString(resultParam.get("MEMBER_NAME"));
				
				
	        	if(StringUtil.isEquals(stat, "3")) {
	        		APPLY = "승인";
	        	} else {
	        		APPLY = "반려";
	        	}
				
	    		String msg = ""; 
	    		msg += "[한국산업인력공단] ";
	    		msg += NAME +"님의 공단소속 변경신청건이 " + APPLY +"되었습니다.";
			
				map.put("userCode", "orgMulti" + i);               // 알림톡 PK
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
	 * 적용중인 주치의 확인 : 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
	 * @param parameterMap
	 * @param deleteIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public int applyNotDelete(ParamForm parameterMap, String[] deleteIdxs, String regiIp, JSONObject settingInfo) throws Exception {
		if(deleteIdxs == null) return 0;
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>();						// 저장항목

		// 1. 저장조건
    	//String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");
    	searchList.add(new DTForm("DOCTOR_IDX", deleteIdxs));
    	
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
		return memberManageDAO.applyNotDelete(param);
	}
	
	/**적용중인 주치의 확인
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
    	//String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");
    	searchList.add(new DTForm("DOCTOR_IDX", deleteIdxs));
    	
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
		return memberManageDAO.employDelete(param);
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
		return memberManageDAO.restore(param);
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
		deleteMultiFileList = memberManageFileDAO.getList(param);
		// 2.2 삭제할 file(단일항목) select
		List deleteFileColumnList = ModuleUtil.getFileObjectList(items, itemOrder);
		if(deleteFileColumnList != null) {
			param.put("columnList", deleteFileColumnList);
			
			deleteFileList = memberManageDAO.getFileList(param);
		}*/
		
		// 3. delete
		int result = memberManageDAO.cdelete(param);
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
					resultHashMap.put(itemId, memberManageFileDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A." + columnName, keyIdx));
		    	searchList.add(new DTForm("A.ITEM_ID", (String[])itemIdList.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
				resultHashMap = memberManageFileDAO.getMapList(param);
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
					resultHashMap.put(itemId, memberManageMultiDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A." + columnName, keyIdx));
		    	//searchList.add(new DTForm("A.ITEM_ID", (String[])itemIdList.toArray(new String[itemIdList.size()])));
				param.put(columnName, keyIdx);
				resultHashMap = memberManageMultiDAO.getMapList(param);
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
	
	//기업회원 일괄변경
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int updateCorp(String status, String psitnList, String bplList, String memberList, String regiIp) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> dataList = new ArrayList<DTForm>();
		int result = 0;
		
		String[] bplArray = bplList.split(",");
		String[] psitnArray = psitnList.split(",");
		String[] memberArray = memberList.split(",");
		
		String apply = null;
		if(StringUtil.isEquals(status, "1")) {
			apply = "Y";
		} else {
			apply = "N";
		}
		
		
		param.put("PSITNLIST", psitnArray);
		param.put("BPLLIST", bplArray);
		param.put("STATUS", status);
		param.put("APPLY_YN", apply);
		if(StringUtil.isEquals(apply, "Y")) {
			memberManageDAO.updateNotCorp(param);
		}
		
		
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
		
		
			
		dataList.add(new DTForm("CONFMER_IDX", loginMemberIdx));
		dataList.add(new DTForm("CONFMER_ID", loginMemberId));
		dataList.add(new DTForm("CONFMER_NAME", loginMemberName));
		dataList.add(new DTForm("CONFMER_IP", regiIp));
		
		dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
		dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
		dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
		dataList.add(new DTForm("LAST_MODI_IP", regiIp));
		
		dataList.add(new DTForm("STATUS", status));
		
		param.put("dataList", dataList);
		
		result = memberManageDAO.updateCorpRegion(param);
		
		// 카카오톡 알림보내기
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> mailParam = new HashMap<String, Object>();				// mapper parameter 데이터
			List<Object> resultMap = new ArrayList<Object>();							// 결과값을 저장하기 위한 Map
			
			
			mailParam.put("MEMBER_IDX", memberArray);
						
			// 연락처 목록 가져오기
			resultMap = memberManageDAO.getCorpMailList(mailParam);
			
			List resultList = StringUtil.getList(resultMap);
			int resultSize = resultList.size();
			String APPLY = "";
			
			String title = "권한변경 알림톡";
			String templateCode = "SJB_076913";
			
			for(int i = 0 ; i < resultSize ; i ++) {
				
				Map<String, Object> resultParam = (Map<String, Object>) resultList.get(i);
				
				String recevier = StringUtil.getString(resultParam.get("MOBILE_PHONE"));
	 
	        	String NAME =  StringUtil.getString(resultParam.get("MEMBER_NAME"));
				
				
	        	if(StringUtil.isEquals(status, "1")) {
	        		APPLY = "승인";
	        	} else {
	        		APPLY = "반려";
	        	}
				
				
	    		String msg = ""; 
	    		msg += "[한국산업인력공단] ";
	    		msg += NAME +"님의 소속기관 변경신청건이 " + APPLY +"되었습니다.";
			
				map.put("userCode", "corpMulti" + i);               // 알림톡 PK
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

	// 민간센터 일괄변경
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int updateCenter(String authIdx, String status, String startDate, String endDate, String memberList, String authReqList, String regiIp) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> dataList = new ArrayList<DTForm>();
		int result = 0;
		String apply = null;
		
		if(StringUtil.isEquals(status, "1")) {
			apply = "Y";
		} else {
			apply = "N";
		}
		
		String[] memberArray = memberList.split(",");
		String[] authReqArray = authReqList.split(",");
		
		param.put("AUTH_IDX", authIdx);
		param.put("STATUS", status);
		param.put("START_DATE", startDate);
		param.put("END_DATE", endDate);
		param.put("MEMBERLIST", memberArray);
		param.put("AUTHREQLIST", authReqArray);
		
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
		
		param.put("LAST_MODI_IDX", loginMemberIdx);
		param.put("LAST_MODI_ID", loginMemberId);
		param.put("LAST_MODI_NAME", loginMemberName);
		param.put("LAST_MODI_IP", regiIp);
		
		param.put("CONFMER_IDX", loginMemberIdx);
		param.put("CONFMER_ID", loginMemberId);
		param.put("CONFMER_NAME", loginMemberName);
		param.put("CONFMER_IP", regiIp);
		
		
		if(StringUtil.isEquals(status, "1")) {
			memberManageDAO.updateCenterAuth(param);
			
			dataList.clear();
			
			for(int i = 0; i < memberArray.length; i++) {
				int authMemberIdx = memberManageDAO.getCenMemNextId(param);
				
				dataList.add(new DTForm("AUTH_IDX", authIdx));
				dataList.add(new DTForm("START_DATE", startDate));
				dataList.add(new DTForm("END_DATE", endDate));
				dataList.add(new DTForm("APPLY_YN", apply));
				
				dataList.add(new DTForm("REGI_IDX", loginMemberIdx));
				dataList.add(new DTForm("REGI_ID", loginMemberId));
				dataList.add(new DTForm("REGI_NAME", loginMemberName));
				dataList.add(new DTForm("REGI_IP", regiIp));
				
				dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
				dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
				dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
				dataList.add(new DTForm("LAST_MODI_IP", regiIp));
				
				
				dataList.add(new DTForm("AUTH_MEMBER_IDX", authMemberIdx));
				dataList.add(new DTForm("AUTH_REQ_IDX", authReqArray[i]));
				dataList.add(new DTForm("MEMBER_IDX", memberArray[i]));
				
				param.put("dataList", dataList);
				
				result = memberManageDAO.insertCenterAuth(param);
				
				dataList.clear();
			}
		}
	
		result = memberManageDAO.updateCenterAuth(param);
		
		// 카카오톡 알림보내기
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> mailParam = new HashMap<String, Object>();				// mapper parameter 데이터
			List<Object> resultMap = new ArrayList<Object>();							// 결과값을 저장하기 위한 Map
			
			
			mailParam.put("MEMBER_IDX", memberArray);
						
			// 연락처 목록 가져오기
			resultMap = memberManageDAO.getCenterMailList(mailParam);
			
			List resultList = StringUtil.getList(resultMap);
			int resultSize = resultList.size();
			String APPLY = "";
			
			String title = "권한변경 알림톡";
			String templateCode = "SJB_076912";
			
			for(int i = 0 ; i < resultSize ; i ++) {
				
				Map<String, Object> resultParam = (Map<String, Object>) resultList.get(i);
				
				String recevier = StringUtil.getString(resultParam.get("MOBILE_PHONE"));
	 
	        	String NAME =  StringUtil.getString(resultParam.get("MEMBER_NAME"));
	        	
	        	if(StringUtil.isEquals(status, "1")) {
	        		APPLY = "승인";
	        	} else {
	        		APPLY = "반려";
	        	}
				
				
	    		String msg = ""; 
	    		msg += "[한국산업인력공단] ";
	    		msg += NAME +"님의 민간센터 권한신청건이 " + APPLY +"되었습니다.";
			
				map.put("userCode", "centerMulti" + authIdx + i);               // 알림톡 PK
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
	
	// 공단소속 일괄변경
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int updateEmploy(String status, String memberList, String doctorList, String regiIp) {
		Map<String, Object> param = new HashMap<String, Object>();
		int result = 0;
		
		String[] memberArray = memberList.split(",");
		String[] doctorArray = doctorList.split(",");
		
		
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
		
		param.put("STATUS", status);
		
		param.put("LAST_MODI_IDX", loginMemberIdx);
		param.put("LAST_MODI_ID", loginMemberId);
		param.put("LAST_MODI_NAME", loginMemberName);
		param.put("LAST_MODI_IP", regiIp);
		
		// status가 3(승인)이면 이전에 등록된 데이터의 applyYn N으로 변경(주치의관리 목록에서 안나오게 하기 위해)
		if(status.equals("3")) {
			
			param.put("MEMBERLIST", memberArray);
			
			memberManageDAO.updateApplyYnToN(param);
		}
		
		param.put("CONFMER_IDX", loginMemberIdx);
		param.put("CONFMER_ID", loginMemberId);
		param.put("CONFMER_NAME", loginMemberName);
		param.put("CONFMER_IP", regiIp);
		
		param.put("DOCTORLIST", doctorArray);
		
		result = memberManageDAO.updateEmployRegion(param);
		
		// 카카오톡 알림보내기
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> mailParam = new HashMap<String, Object>();				// mapper parameter 데이터
			List<Object> resultMap = new ArrayList<Object>();							// 결과값을 저장하기 위한 Map
			
			
			mailParam.put("MEMBER_IDX", memberArray);
						
			// 연락처 목록 가져오기
			resultMap = memberManageDAO.getOrgMailList(mailParam);
			
			List resultList = StringUtil.getList(resultMap);
			int resultSize = resultList.size();
			String APPLY = "";
			
			String title = "권한변경 알림톡";
			String templateCode = "SJB_076911";
			
			for(int i = 0 ; i < resultSize ; i ++) {
				
				Map<String, Object> resultParam = (Map<String, Object>) resultList.get(i);
				
				String recevier = StringUtil.getString(resultParam.get("MOBILE_PHONE"));
	 
	        	String NAME =  StringUtil.getString(resultParam.get("MEMBER_NAME"));
				
				
	        	if(StringUtil.isEquals(status, "3")) {
	        		APPLY = "승인";
	        	} else {
	        		APPLY = "반려";
	        	}
				
	    		String msg = ""; 
	    		msg += "[한국산업인력공단] ";
	    		msg += NAME +"님의 공단소속 변경신청건이 " + APPLY +"되었습니다.";
			
				map.put("userCode", "orgMulti" + i);               // 알림톡 PK
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
	 * 민간센터 권한신청 삭제  
	 * @param param
	 * @return
	 */
    @Override
	public int centerDelete(Map<String, Object> param) {
    	return memberManageDAO.centerDelete(param);
    }
    
    /**
	 * 본부직원 소속기관 삭제  
	 * @param param
	 * @return
	 */
    @Override
	public int employDelete(Map<String, Object> param) {
    	return memberManageDAO.employDelete(param);
    }
    
    @Override
	public List<Object> getOrgInsttList() {
		Map<String, Object> param = new HashMap<String, Object>();
		
		return memberManageDAO.getOrgInsttList(param);
	}

	@Override
	public List<Object> getInsttList() {
		Map<String, Object> param = new HashMap<String, Object>();
		
		return memberManageDAO.getInsttList(param);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional
	public void updatePSITN(Map param) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if(loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		
		param.put("CONFMER_IDX", loginMemberIdx);
		param.put("CONFMER_ID", loginMemberId);
		param.put("CONFMER_NAME", loginMemberName);
		if("Y".equals(param.get("statusVal"))) {
			memberManageDAO.updateApplyYNPSITN(param);
			memberManageDAO.updateInsttPSITN(param);
		}
		memberManageDAO.updatePSITN(param);
		
		// 카카오톡 알림보내기
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> mailParam = new HashMap<String, Object>();				// mapper parameter 데이터
			List<Object> resultMap = new ArrayList<Object>();							// 결과값을 저장하기 위한 Map
			String memberIdx = StringUtil.getString(param.get("memIdx"));
			String status = StringUtil.getString(param.get("statusVal"));
			
			mailParam.put("MEMBER_IDX", memberIdx);
						
			// 연락처 목록 가져오기
			resultMap = memberManageDAO.getCorpMailList(mailParam);
			
			List resultList = StringUtil.getList(resultMap);
			int resultSize = resultList.size();
			String APPLY = "";
			
			String title = "권한변경 알림톡";
			String templateCode = "SJB_076913";
			
			for(int i = 0 ; i < resultSize ; i ++) {
				
				Map<String, Object> resultParam = (Map<String, Object>) resultList.get(i);
				
				String recevier = StringUtil.getString(resultParam.get("MOBILE_PHONE"));
	 
	        	String NAME =  StringUtil.getString(resultParam.get("MEMBER_NAME"));
				
				
	        	if(StringUtil.isEquals(status, "Y")) {
	        		APPLY = "승인";
	        	} else {
	        		APPLY = "반려";
	        	}
				
				
	    		String msg = ""; 
	    		msg += "[한국산업인력공단] ";
	    		msg += NAME +"님의 소속기관 변경신청건이 " + APPLY +"되었습니다.";
			
				map.put("userCode", "corpMulti" + i);               // 알림톡 PK
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
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional
	public void insertPSITNbyDCT(Map param) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if(loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		
		param.put("REGI_IDX", loginMemberIdx);
		param.put("REGI_ID", loginMemberId);
		param.put("REGI_NAME", loginMemberName);
		Map<String,Object> blank = new HashMap<String, Object>();
		int keyIdx = memberManageDAO.getOrgNextId(blank);
		param.put("PSITN", keyIdx);
		memberManageDAO.updateApplyYNPSITN(param);
		memberManageDAO.insertPSITNbyDCT(param);
		memberManageDAO.updateInsttPSITN(param);
		
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void updateDoctor(Map param) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if(loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		
		param.put("REGI_IDX", loginMemberIdx);
		param.put("REGI_ID", loginMemberId);
		param.put("REGI_NAME", loginMemberName);
		
		param.put("LAST_MODI_IDX", loginMemberIdx);
		param.put("LAST_MODI_ID", loginMemberId);
		param.put("LAST_MODI_NAME", loginMemberName);
		
		
		memberManageDAO.updateDoctor(param);
	}
}