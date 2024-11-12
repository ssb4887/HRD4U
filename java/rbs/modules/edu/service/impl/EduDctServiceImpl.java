package rbs.modules.edu.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.prop.RbsProperties;
import com.woowonsoft.egovframework.util.UserDetailsHelper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import rbs.egovframework.LoginVO;
import rbs.modules.edu.mapper.EduDctMapper;
import rbs.modules.edu.service.EduDctService;

@Service("eduDctService")
public class EduDctServiceImpl extends EgovAbstractServiceImpl implements EduDctService {
	
	@Resource(name = "eduDctMapper")
	private EduDctMapper eduDctDAO;

	@Transactional
	@Override
	public Map<String, Object> insertEdc(Map<String, Object> formdata) {
		// 공통: 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		
		formdata.put("REGI_IDX", loginMemberIdx);
		formdata.put("REGI_ID", loginMemberId);
		formdata.put("REGI_NAME", loginMemberName);
		formdata.put("LAST_MODI_IDX", loginMemberIdx);
		formdata.put("LAST_MODI_ID", loginMemberId);
		formdata.put("LAST_MODI_NAME", loginMemberName);
		
		if(formdata.get("maxRecptNmpr") == null || formdata.get("maxRecptNmpr").toString().isEmpty()) {
			formdata.put("maxRecptNmpr", 0);
		}
		
		// 1. 교육과정 등록(INSERT/UPDATE)
		int edcInsertResult = 0;
		if(!formdata.containsKey("edcIdx") || formdata.get("edcIdx").toString().isEmpty()) {
			// INSERT
			edcInsertResult = eduDctDAO.insertEdc(formdata);
		} else {
			// UPDATE
			edcInsertResult = eduDctDAO.updateEdc(formdata);
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("EDC_IDX", formdata.get("edcIdx"));
		result.put("EDC_INSERT_RESULT", edcInsertResult);
		return result;
	}

	@Transactional
	@Override
	public Map<String, Object> saveEdcFiles(ModuleAttrVO attrVO, Map<String, Object> formdata,
			List<MultipartFile> files, List<Integer> fileSeq) {
		// 0. Return
		Map<String, Object> result = new HashMap<>();
		
		// 공통: 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		formdata.put("REGI_IDX", loginMemberIdx);
		formdata.put("REGI_ID", loginMemberId);
		formdata.put("REGI_NAME", loginMemberName);
		formdata.put("LAST_MODI_IDX", loginMemberIdx);
		formdata.put("LAST_MODI_ID", loginMemberId);
		formdata.put("LAST_MODI_NAME", loginMemberName);
		
		// 기존 파일 목록과 새로운 파일 목록 비교
		if(fileSeq != null && !fileSeq.isEmpty()) {
			List<Map<String, Object>> originFileList = eduDctDAO.getEdcFileList(formdata);
			for(Map<String, Object> originFile : originFileList) {
				int fleIdx = Integer.valueOf(originFile.get("FLE_IDX").toString());
				formdata.put("fleIdx", fleIdx);
				if(fileSeq.contains(fleIdx)) {
					formdata.put("isdelete", 0);
					formdata.put("orderIdx", fileSeq.indexOf(fleIdx));
				} else {
					formdata.put("isdelete", 1);
					formdata.put("orderIdx", 0);
				}
				
				int updatFileToDBResult = eduDctDAO.updateEdcFileData(formdata);
				if(updatFileToDBResult > 0) {
					result.put("FILE_SAVE_TO_DB(" + originFile.get("FILE_ORIGIN_NAME").toString() + ")", "success");
				} else {
					result.put("FILE_SAVE_TO_DB(" + originFile.get("FILE_ORIGIN_NAME").toString() + ")", "fail");
				}
			}
		}
		
		// 1. 업로드 경로 체크(폴더없으면 폴더생성)
		String edcCd = attrVO.getSettingInfo().getString("edc_cd").toLowerCase();
		String uploadPath =  RbsProperties.getProperty("Globals.upload.file.path") + File.separator  + attrVO.getConfModule() + File.separator + edcCd;
		File fileDir = new File(uploadPath);
		if(!fileDir.exists()) {
			fileDir.mkdirs();
		}
		
		// 2. 업로드 파일
		int fileUploadResult = 0;
		for(int i=0;i < files.size();i++) {
			MultipartFile file = files.get(i);
			if(file.isEmpty()) continue;
			
			// 2-1. 업로드 파일명(yyyyMMddHHmmss_실제파일명)
			String fileOriginName = file.getOriginalFilename();
			Date currentDate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String uniqueName = dateFormat.format(currentDate);
			String fileSaveName = uniqueName + "_" + fileOriginName;
			
			// 2-2. 실제 파일 저장
			File saveFile = new File(uploadPath + File.separator + fileSaveName);
			try {
				file.transferTo(saveFile);
				result.put("FILE_TO_SERVER(" + file.getOriginalFilename() + ")", "success");
				
				// 2-3. 파일 저장 데이터 DB에 저장
				formdata.put("fileSavedName", fileSaveName);
				formdata.put("fileOriginName", fileOriginName);
				formdata.put("fileSize", file.getSize());
				formdata.put("replcText", "교육과정 관련 문서");
				formdata.put("orderIdx", i);
				int saveFileDataToDBResult = eduDctDAO.insertEdcFileData(formdata);
				if(saveFileDataToDBResult > 0) {
					fileUploadResult++;
					result.put("FILE_TO_DB(" + file.getOriginalFilename() + ")", "success");
				} else {
					result.put("FILE_TO_DB(" + file.getOriginalFilename() + ")", "fail");
				}
			} catch (IOException e) {
				result.put("FILE_TO_SERVER(" + file.getOriginalFilename() + ")", "fail:" + e.getLocalizedMessage());
			}
		}
		result.put("FILE_UPLOAD_RESULT", fileUploadResult);
				
		return result;
	}

	@Override
	public List<Map<String, Object>> getEdcList(Map<String, Object> param) {
		return eduDctDAO.getEdcList(param);
	}

	@Override
	public int getEdcTotalCount(Map<String, Object> param) {
		return eduDctDAO.getEdcTotalCount(param);
	}

	@Override
	public int deleteEdc(Map<String, Object> param) {
		// 공통: 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		
		param.put("LAST_MODI_IDX", loginMemberIdx);
		param.put("LAST_MODI_ID", loginMemberId);
		param.put("LAST_MODI_NAME", loginMemberName);
		return eduDctDAO.deleteEdc(param);
	}

	@Override
	public Map<String, Object> getEdc(Map<String, Object> param) {
		return eduDctDAO.getEdc(param);
	}

	@Override
	public List<Map<String, Object>> getEdcFileList(Map<String, Object> param) {
		return eduDctDAO.getEdcFileList(param);
	}

	@Override
	public Map<String, Object> getEdcFileOne(Map<String, Object> param) {
		return eduDctDAO.getEdcFileOne(param);
	}

	@Override
	public List<Object> getMemberList(Map<String, Object> param) {
		return eduDctDAO.getMemberList(param);
	}

	@Transactional
	@Override
	public int insertEdcMember(Map<String, Object> param) {
		int result = 0;
		// 공통: 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		param.put("REGI_IDX", loginMemberIdx);
		param.put("REGI_ID", loginMemberId);
		param.put("REGI_NAME", loginMemberName);
		
		// 1. 기존재 여부 확인
		int count = eduDctDAO.isExistsEdcMember(param);
		
		// 2. 존재시 update, 없을시 insert
		if(count > 0) {
			param.put("isdelete", "0");
			result = eduDctDAO.updateEdcMember(param);
		} else {
			result = eduDctDAO.insertEdcMember(param);
		}
		return result;
	}

	@Override
	public List<Object> getEdcMemberList(Map<String, Object> param) {
		return eduDctDAO.getEdcMemberList(param);
	}

	@Override
	public int getEdcMemberListCount(Map<String, Object> param) {
		return eduDctDAO.getEdcMemberListCount(param);
	}

	@Override
	public int deleteEdcMembers(Map<String, Object> param) {
		// 공통: 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		
		param.put("LAST_MODI_IDX", loginMemberIdx);
		param.put("LAST_MODI_ID", loginMemberId);
		param.put("LAST_MODI_NAME", loginMemberName);
		return eduDctDAO.deleteEdcMembers(param);
	}

	@Transactional
	@Override
	public int changeEdcMemberStatus(Map<String, Object> param) {
		// 공통: 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
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
		
		eduDctDAO.chagneEdcMemberStatusConfm(param);
		return eduDctDAO.changeEdcMemberStatus(param);
	}

	@Override
	public Map<String, Object> getEdcMember(Map<String, Object> param) {
		return eduDctDAO.getEdcMember(param);
	}

	@Transactional
	@Override
	public int changeManyEdcMemberStatus(Map<String, Object> param) {
		// 공통: 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
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
		
		if(param.get("confmStatus") != null) {
			eduDctDAO.changeManyEdcMemberStatusConfm(param);
		}
		return eduDctDAO.changeManyEdcMemberStatus(param);
	}

	@Transactional
	@Override
	public int issueCertificate(Map<String, Object> param) {
		// 공통: 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		
		param.put("LAST_MODI_IDX", loginMemberIdx);
		param.put("LAST_MODI_ID", loginMemberId);
		param.put("LAST_MODI_NAME", loginMemberName);
		
		// 같은 교육과정 신청자 중 몇번째 사람인지
		Map<String, Object> map = eduDctDAO.getMemberOrderInEdc(param);
		if(map.isEmpty() || map.get("MEMBER_NO") == null || !map.containsKey("MEMBER_NO")) {
			return 0;
		}
		
		param.put("memberNo", map.get("MEMBER_NO").toString());
		
		return eduDctDAO.issueCertificate(param);
	}

	@Override
	public int getMyEdcHistoryCount(Map<String, Object> param) {
		return eduDctDAO.getMyEdcHistoryCount(param);
	}

	@Override
	public List<Object> getMyEdcHistoryList(Map<String, Object> param) {
		return eduDctDAO.getMyEdcHistoryList(param);
	}

	@Transactional
	@Override
	public List<Map<String, Object>> insertManyEdc(List<Map<String, Object>> list) throws Exception {
		List<Map<String, Object>> result = new ArrayList<>();

		for(Map<String, Object> param : list) {
			try {
				eduDctDAO.insertEdc(param);
			} catch(Exception e) {
				String edcName = param.get("edcName").toString();
				throw new Exception("교육과정 등록 중 에러가 발생하였습니다.[교육명:" + edcName + "]", e);
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getEdcListForExcel(Map<String, Object> param) {
		return eduDctDAO.getEdcListForExcel(param);
	}

	@Transactional
	@Override
	public List<Map<String, Object>> insertEdcMembers(List<Map<String, Object>> list) throws Exception {
		// 공통: 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		
		List<Map<String, Object>> result = new ArrayList<>();

		for(Map<String, Object> param : list) {
			param.put("REGI_IDX", loginMemberIdx);
			param.put("REGI_ID", loginMemberId);
			param.put("REGI_NAME", loginMemberName);
			param.put("LAST_MODI_IDX", loginMemberIdx);
			param.put("LAST_MODI_ID", loginMemberId);
			param.put("LAST_MODI_NAME", loginMemberName);
			
			Map<String, Object> member = eduDctDAO.getMemberIdxById(param);
			if(member == null || member.isEmpty()) {
				throw new Exception("교육생 정보가 없습니다.[교육생 아이디:" + param.get("memberId").toString() + "]");
			}
			
			param.put("memberIdx", member.get("MEMBER_IDX").toString());
			try {
				eduDctDAO.insertEdcMembers(param);
				result.add(param);
			} catch(Exception e) {
				String memberId = param.get("memberId").toString();
				throw new Exception("교육생 등록 중 에러가 발생하였습니다. 관리자에게 문의해주세요. [교육생 아이디:" + memberId + "]", e);
			}
		}
		return result;
	}

	@Override
	public int deleteCertificate(Map<String, Object> param) throws Exception {
		int result = 0;
		// 공통: 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		
		param.put("LAST_MODI_IDX", loginMemberIdx);
		param.put("LAST_MODI_ID", loginMemberId);
		param.put("LAST_MODI_NAME", loginMemberName);
		
		try {
			eduDctDAO.deleteCertificate(param);
		} catch(Exception e) {
			String memberName = param.get("memberName").toString();
			throw new Exception("수료증 발급취소 중 에러가 발생하였습니다. 관리자에게 문의해주세요. [교육생명:" + memberName + "]", e);
		}
		
		return result;
	}
}
