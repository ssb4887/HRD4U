package rbs.modules.agreement.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import rbs.egovframework.LoginVO;
import rbs.modules.agreement.dto.RegisterFormVO;
import rbs.modules.agreement.mapper.AgreementFileMapper;
import rbs.modules.agreement.mapper.AgreementMapper;
import rbs.modules.agreement.service.AgreementService;
import rbs.modules.agreement.web.AgreementController;
import rbs.modules.consulting.dto.Cnsl;

import com.woowonsoft.egovframework.form.DTForm;
import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.prop.RbsProperties;
import com.woowonsoft.egovframework.util.JSONObjectUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("agreementService")
public class AgreementServiceImpl extends EgovAbstractServiceImpl implements AgreementService {

	@Resource(name="agreementMapper")
	private AgreementMapper agreementDAO;
	
	@Resource(name="agreementFileMapper")
	private AgreementFileMapper agreementFileDAO;
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(AgreementServiceImpl.class);
	
    /**
	 * 전체 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Map> getList(Map param) {
		return agreementDAO.getList(param);
	}

	/**
	 * 파일 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getFileView(int agrem_idx, int fle_idx) {
		Map param = new HashMap();
    	param.put("agrem_idx", agrem_idx);
    	param.put("fle_idx", fle_idx);
		DataMap viewDAO = agreementDAO.getFileView(param);
		return viewDAO;
	}
	
	/**
	 * 다운로드 수 수정
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateFileDown(int agrem_idx) throws Exception{
		Map param = new HashMap();
    	param.put("agrem_idx", agrem_idx);
		param.put("fle_idx", 0);
		return agreementDAO.updateFileDown(param);
		
	}
	
	/**
	 * 권한여부
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getAuthCount(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
    	return agreementDAO.getAuthCount(param);
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
					resultHashMap.put(itemId, agreementFileDAO.getList(param));
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
				resultHashMap = agreementFileDAO.getMapList(param);
			}
		}
		
		return resultHashMap;
	}
	
	@Override
	public List<Map> getCenters(String name) {
		return agreementDAO.getCenters(name);
	}

	@Override
	public void requestAgreement(Map param) {
		int nextId = agreementDAO.getNextId(param);
		String member_idx = agreementDAO.getMemberIdxByPrvtcntrno(param);
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if(loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		
		param.put("agrem_idx", nextId);
		param.put("member_idx", member_idx);
		param.put("regi_idx", loginMemberIdx);
		param.put("regi_id", loginMemberId);
		param.put("regi_name", loginMemberName);
		agreementDAO.requestAgreement(param);
	}

	@Override
	public int uploadFile(MultipartFile file, int agremIdx, String ip) {
		String uploadModulePath = "agreement\\1";
		if(!file.isEmpty()) {
			BufferedOutputStream stream = null;
			try {
				byte[] bytes = file.getBytes();
				// globals에서 파일 업로드 위치 가져오기
				String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
				String originalName = file.getOriginalFilename();
				String extention = originalName.substring(originalName.lastIndexOf("."));
				String newFileName = UUID.randomUUID().toString() + extention;
				File dir = new File(fileRealPath);
				if(!dir.exists()) dir.mkdirs();
				if(newFileName.toLowerCase().endsWith(".doc") || newFileName.toLowerCase().endsWith(".hwp") || newFileName.toLowerCase().endsWith(".pdf") || newFileName.toLowerCase().endsWith(".xls") || newFileName.toLowerCase().endsWith(".png") || newFileName.toLowerCase().endsWith(".jpg")) {
					File serverFile = new File(dir.getAbsoluteFile() + File.separator + newFileName);
					stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					
					// DB에 저장
					LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
					String loginMemberIdx = null;
					String loginMemberId = null;
					String loginMemberName = null;
					if(loginVO != null) {
						loginMemberIdx = loginVO.getMemberIdx();
						loginMemberId = loginVO.getMemberId();
						loginMemberName = loginVO.getMemberName();
					}
					Map param = new HashMap();
					param.put("regi_idx", loginMemberIdx);
					param.put("regi_id", loginMemberId);
					param.put("regi_name", loginMemberName);
					param.put("regi_ip", ip);
					param.put("agrem_idx", agremIdx);
					param.put("file_saved_name", newFileName);
					param.put("file_origin_name", originalName);
					param.put("fle_idx", 0);
					return agreementDAO.fileUpload(param); 
				} else {
					throw new Exception("No proper file extention"); 
				}
			}  catch(FileNotFoundException e) {
				log.debug("FileNotFoundException occurred!");
				return -1;
			} catch(IOException e) {
				log.debug("IOException occurred!");
				return -1;
			} catch(Exception e) {
				log.debug("Exception occurred! " + e.getMessage());
				return -1;
			} finally {
				try {
					if(stream != null) {
						stream.close();
					}
				} catch (IOException e) {
					log.debug("IOException occurred.");
				}
			}
		}
		return 0;
	}

	@Override
	public String getAgremNo(Map param) {
		return agreementDAO.getAgremNo(param);
	}

	@Override
	public Map getRegister(Map param) {
		return agreementDAO.getRegister(param);
	}

	@Override
	public int registerAgreement(RegisterFormVO vo) {
		Map param = new HashMap();
		int nextId = vo.getAgremIdx();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if(loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		param.put("agrem_no", vo.getAgremNo());
		param.put("bpl_no", vo.getBplNo());
		param.put("member_idx", loginMemberIdx);
		param.put("regi_idx", loginMemberIdx);
		param.put("regi_id", loginMemberId);
		param.put("regi_name", loginMemberName);
		if(nextId == -1) {
			nextId = agreementDAO.getNextId(param);
			param.put("agrem_idx", nextId);
			vo.setAgremIdx(nextId);
			agreementDAO.requestAgreementbyCenter(param);
		} else {
			param.put("agrem_idx", nextId);
			agreementDAO.updateRequest(param);
		}
		return nextId;
	}

	@Override
	public int confirmAgreement(Map param) {
		LoginVO loginVO = (LoginVO) param.get("loginvo");
		param.put("member_idx", loginVO.getMemberIdx());
		param.put("member_id", loginVO.getMemberId());
		param.put("member_name",  loginVO.getMemberName());
		return agreementDAO.confirmAgreement(param);
	}

	@Override
	public Map getBsk(String bpl_no) {
		Map result = agreementDAO.getBsk(bpl_no);
		result.put("agrem_idx", -1);
		return result;
	}

	@Override
	public void mergeAgremByCnsl(Cnsl cnsl, String regiIp) {
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인
			String loginMemberIdx = null;
			String loginMemberId = null;
			String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
			}
			cnsl.setRegiId(loginMemberId);
			cnsl.setRegiIdx(loginMemberIdx);
			cnsl.setRegiIp(regiIp);
			cnsl.setRegiName(loginMemberName);
		agreementDAO.mergeAgremByCnsl(cnsl);
		
	}

	@Override
	public int getAgremCount(String bplNo) {
		return agreementDAO.getAgremCount(bplNo);
	}

	@Override
	public List<HashMap<String, String>> getCompltedAgremList(String bplNo) {
		return agreementDAO.getCompltedAgremList(bplNo);
	}
	
}