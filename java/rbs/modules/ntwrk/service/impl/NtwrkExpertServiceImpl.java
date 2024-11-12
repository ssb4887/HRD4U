package rbs.modules.ntwrk.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sun.org.apache.xerces.internal.utils.Objects;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.prop.RbsProperties;
import com.woowonsoft.egovframework.util.UserDetailsHelper;

import rbs.egovframework.LoginVO;
import rbs.modules.ntwrk.mapper.NtwrkExpertMapper;
import rbs.modules.ntwrk.service.NtwrkExpertService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("ntwrkExpertService")
public class NtwrkExpertServiceImpl extends EgovAbstractServiceImpl implements NtwrkExpertService {

	@Resource(name="ntwrkExpertMapper")
	private NtwrkExpertMapper ntwrkExpertDAO;

	@Transactional
	@Override
	public Map<String, Object> inserExpert(Map<String, Object> formdata, List<Integer> partnerInsttList) {
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
		
		// 1. 관할 전문가 등록(INSERT/UPDATE)
		int expertResult = 0;
		int partnerInsttResult = 0;
		if(!formdata.containsKey("expertIdx") || formdata.get("expertIdx").toString().isEmpty()) {
			// INSERT
			expertResult += ntwrkExpertDAO.insertExpert(formdata);
			for(Integer partnerInsttIdx : partnerInsttList) {
				formdata.put("partnerInsttIdx", partnerInsttIdx);
				partnerInsttResult += ntwrkExpertDAO.insertExpertPartnerInstt(formdata);
			}
			formdata.remove("partnerInsttIdx");
		} else {
			// UPDATE
			expertResult += ntwrkExpertDAO.updateExpert(formdata);
			
			// 기존 소속기관 목록(NTWRK_EXPERT_INSTT)을 새로운 소속기관 목록(partnerInsttList)와 비교
			formdata.put("ALL", true);
			List<Map<String, Object>> originPartnerInsttList = ntwrkExpertDAO.getExpertPartnerInstts(formdata);
			formdata.remove("ALL");
			
			/**
			 * 기존소속기관에 포함O & 기존ISDELETE = 0 > continue
			 * 기존소속기관에 포함O & 기존ISDELETE = 1 > ISDELETE=0 으로 update
			 * 기존소속기관에 포함X > insert
			 */
			Iterator<Integer> newPartnerIterator = partnerInsttList.iterator();
			while(newPartnerIterator.hasNext()) {
				int newPartnerInstt = newPartnerIterator.next();
				Iterator<Map<String, Object>> originIterator = originPartnerInsttList.iterator();
				while(originIterator.hasNext()) {
					Map<String, Object> originPartnerInstt = originIterator.next();
					int originPartnerInsttIdx = Integer.valueOf(originPartnerInstt.get("INSTT_IDX").toString());
					boolean isEqual = (newPartnerInstt == originPartnerInsttIdx);
					if(isEqual && originPartnerInstt.get("ISDELETE").equals("0")) {
						newPartnerIterator.remove();
						originIterator.remove();
					} else if(isEqual && originPartnerInstt.get("ISDELETE").equals("1")) {
						formdata.put("isdelete", 0);
						formdata.put("partnerInsttIdx", newPartnerInstt);
						partnerInsttResult += ntwrkExpertDAO.updateExpertPartnerInstt(formdata);
						formdata.remove("partnerInsttIdx");
						newPartnerIterator.remove();
						originIterator.remove();
					}
				}
			}
			
			// 기존목록에 없는 새로운 소속기관 목록(INSERT)
			for(Integer partnerInsttIdx : partnerInsttList) {
				formdata.put("partnerInsttIdx", partnerInsttIdx);
				partnerInsttResult += ntwrkExpertDAO.insertExpertPartnerInstt(formdata);
				formdata.remove("partnerInsttIdx");
			}

			// 새로운 소속기관 목록에 없는 기존목록(UPDATE-for delete)
			for(Map<String, Object> origin : originPartnerInsttList) {
				formdata.put("isdelete", 1);
				formdata.put("partnerInsttIdx", origin.get("INSTT_IDX"));
				partnerInsttResult += ntwrkExpertDAO.updateExpertPartnerInstt(formdata);
				formdata.remove("partnerInsttIdx");
			}
		}
		
		// 2. 결과
		Map<String, Object> result = new HashMap<>();
		result.put("EXPERT_RESULT", expertResult); // 전문가 변겅 건수
		result.put("PARTNER_INSTT_RESULT", partnerInsttResult); // 소속기관 목록 변경 건수
		return result;
	}

	@Override
	public List<Map<String, Object>> getExpertList(Map<String, Object> param) {
		return ntwrkExpertDAO.getExpertList(param);
	}

	@Override
	public int getExpertTotalCount(Map<String, Object> param) {
		return ntwrkExpertDAO.getExpertTotalCount(param);
	}

	@Transactional
	@Override
	public Map<String, Object> saveExpertFiles(ModuleAttrVO attrVO, Map<String, Object> formdata,
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
		if(!fileSeq.isEmpty()) {
			List<Map<String, Object>> originFileList = ntwrkExpertDAO.getExpertFileList(formdata);
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
				
				int updatFileToDBResult = ntwrkExpertDAO.updateExpertFileData(formdata);
				if(updatFileToDBResult > 0) {
					result.put("FILE_SAVE_TO_DB(" + originFile.get("FILE_ORIGIN_NAME").toString() + ")", "success");
				} else {
					result.put("FILE_SAVE_TO_DB(" + originFile.get("FILE_ORIGIN_NAME").toString() + ")", "fail");
				}
			}
		}
		
		// 1. 업로드 경로 체크(폴더없으면 폴더생성)
		String uploadPath =  RbsProperties.getProperty("Globals.upload.file.path") + File.separator  + attrVO.getUploadModulePath();
		File fileDir = new File(uploadPath);
		if(!fileDir.exists()) {
			fileDir.mkdirs();
		}
		
		// 2. 업로드 파일
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
				formdata.put("replcText", "관할전문가 관련 문서");
				formdata.put("orderIdx", i);
				int saveFileDataToDBResult = ntwrkExpertDAO.insertExpertFileData(formdata);
				if(saveFileDataToDBResult > 0) {
					result.put("FILE_TO_DB(" + file.getOriginalFilename() + ")", "success");
				} else {
					result.put("FILE_TO_DB(" + file.getOriginalFilename() + ")", "fail");
				}
			} catch (IOException e) {
				result.put("FILE_TO_SERVER(" + file.getOriginalFilename() + ")", "fail:" + e.getLocalizedMessage());
			}
		}
		
		return result;
	}

	@Override
	public Map<String, Object> getExpertOne(Map<String, Object> param) {
		return ntwrkExpertDAO.getExpertOne(param);
	}

	@Override
	public List<Map<String, Object>> getExpertPartnerInstts(Map<String, Object> param) {
		return ntwrkExpertDAO.getExpertPartnerInstts(param);
	}

	@Override
	public List<Map<String, Object>> getExpertFileList(Map<String, Object> param) {
		return ntwrkExpertDAO.getExpertFileList(param);
	}

	@Override
	public int deleteExpert(Map<String, Object> param) {
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
		
		return ntwrkExpertDAO.deleteExpert(param);
	}

	@Transactional
	@Override
	public int uploadManyExpertData(List<Map<String, Object>> list) throws Exception {
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
		
		for(Map<String, Object> param : list) {
			param.put("REGI_IDX", loginMemberIdx);
			param.put("REGI_ID", loginMemberId);
			param.put("REGI_NAME", loginMemberName);
			
			try {
				result += ntwrkExpertDAO.insertExpert(param);
				ntwrkExpertDAO.insertExpertPartnerInstt(param);
			} catch(Exception e) {
				e.printStackTrace();
				throw new Exception(param.get("name") + " 을(를) 등록하던 중 오류가 발생했습니다.", e);
			}
		}
		return result;
	}
	
}