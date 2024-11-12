package rbs.modules.adminForm.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.util.UserDetailsHelper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import rbs.egovframework.LoginVO;
import rbs.modules.adminForm.mapper.AdminFormFileMapper;
import rbs.modules.adminForm.mapper.AdminFormMapper;
import rbs.modules.adminForm.mapper.AdminFormMultiMapper;
import rbs.modules.adminForm.service.AdminFormService;
import rbs.modules.basket.dto.ExcelUploadDto;


@Service("adminFormService")
public class AdminFormServiceImpl extends EgovAbstractServiceImpl implements AdminFormService {

	@Resource(name="adminFormMapper")
	private AdminFormMapper adminFormDAO;
	
	@Resource(name="adminFormFileMapper")
	private AdminFormFileMapper adminFormFileDAO;
	
	@Resource(name="adminFormMultiMapper")
	private AdminFormMultiMapper adminFormMultiDAO;
	
    /**
	 * 방문기업서식 목록
	 */
	@Override
	public List<Object> getList(Map<String, Object> param) {
		return adminFormDAO.getList(param);
	}
	
	/**
	 * 방문기업서식 목록 수
	 */
	@Override
	public int getTotalCount(Map<String, Object> param) {
		return adminFormDAO.getTotalCount(param);
	}
	
	/**
	 * 공단 담당자 정보 조회
	 */
	@Override
	public Map getDoc(String memberIdx) {
		return adminFormDAO.getDoc(memberIdx);
	}
	
	/**
	 * 상세조회
	 */
	@Override
	public DataMap getView(Map<String, Object> param) {
		DataMap viewDAO = adminFormDAO.getView(param);
		return viewDAO;
	}
	
	/**
	 * 방문기업서식 등록
	 */
	@Override
	public int setForm(Map<String, Object> param) {
		
		// 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		String regiIp =param.get("regiIp").toString();
		
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}

		param.put("regiIdx", loginMemberIdx);
		param.put("regiId", loginMemberId);
		param.put("regiName", loginMemberName);
		param.put("regiIp", regiIp);
		
		param.put("lastModiIdx", loginMemberIdx);
		param.put("lastModiId", loginMemberId);
		param.put("lastModiName", loginMemberName);
		param.put("lastModiIp", regiIp);
		
		String value = param.get("admsfmIdx").toString();
		
		// 중복 조건 설정
		boolean tmprSaveYCondition = param.get("tmprSaveYn").equals("Y") && value != null && !value.isEmpty();
		boolean dcsnYCondition = param.get("dcsnYn").equals("Y") && value != null && !value.isEmpty();
		
		// 임시저장
		if(param.get("action").equals("temp")){
			if(tmprSaveYCondition) {
				return adminFormDAO.update(param);
				
			}else if(dcsnYCondition) {
				param.put("tmprSaveYn", "Y");
				param.put("dcsnYn", "N");
				return adminFormDAO.update(param);
			}else {
				param.put("tmprSaveYn", "Y");
				return adminFormDAO.insert(param);
			}
		// 등록
		}else {
			if(tmprSaveYCondition) {
				param.put("dcsnYn", "Y");
				return adminFormDAO.update(param);
				
			}else if(dcsnYCondition) {
				return adminFormDAO.update(param);
			}else {
				param.put("dcsnYn", "Y");
				return adminFormDAO.insert(param);
			}
			
		}
	}
	
	/**
	 * 방문기업서식 삭제
	 */
	@Override
	public int delete(Map<String, Object> param) {
		
		// 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}

		param.put("regiIdx", loginMemberIdx);
		param.put("regiId", loginMemberId);
		param.put("regiName", loginMemberName);
		
		param.put("lastModiIdx", loginMemberIdx);
		param.put("lastModiId", loginMemberId);
		param.put("lastModiName", loginMemberName);
		
		return adminFormDAO.delete(param);
	}
	

	/**
	 * 파일 업로드
	 */
	@Override
	public int insertFile(ExcelUploadDto dto, String remoteAddr) {
		// 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		dto.setRegiId(loginMemberId);
		dto.setRegiIdx(loginMemberIdx);
		dto.setRegiIp(remoteAddr);
		dto.setRegiName(loginMemberName);
		return adminFormDAO.insertFile(dto);
	}
	
	/**
	 * 파일 수정 업로드
	 */
	@Override
	public int updateFile(ExcelUploadDto dto, String remoteAddr) {
		// 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		dto.setLastModiId(loginMemberId);
		dto.setLastModiIdx(loginMemberIdx);
		dto.setLastModiIp(remoteAddr);
		dto.setLastModiName(loginMemberName);
		return adminFormDAO.updateFile(dto);
	}

	/**
	 * 파일 상세조회
	 */
	@Override
	public DataMap getFileView(Map<String, Object> param) {
		return adminFormDAO.getFileView(param);
	}

}