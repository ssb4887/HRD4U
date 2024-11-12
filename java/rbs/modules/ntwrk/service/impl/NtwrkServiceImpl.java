package rbs.modules.ntwrk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rbs.egovframework.LoginVO;
import rbs.modules.ntwrk.mapper.NtwrkMapper;
import rbs.modules.ntwrk.service.NtwrkService;

import com.woowonsoft.egovframework.util.UserDetailsHelper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("ntwrkService")
public class NtwrkServiceImpl extends EgovAbstractServiceImpl implements NtwrkService {

	@Resource(name="ntwrkMapper")
	private NtwrkMapper ntwrkDAO;

	@Transactional
	@Override
	public Map<String, Object> insertNtwrkData(Map<String, Object> formdata, List<Integer> cmptList) {
		Map<String, Object> result = new HashMap<String, Object>();
		
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
		
		// 1. 네트워크 이력 등록(INSERT/UPDATE)
		int ntwrkResult = 0;
		if(formdata.get("ntwrkIdx").toString().isEmpty()) {
			ntwrkResult = ntwrkDAO.insertNtwrkData(formdata);
		} else {
			ntwrkResult = ntwrkDAO.updateNtwrkData(formdata);
		}
		result.put("NTWRK_RESULT", ntwrkResult);
		
		// 2. 네트워크 관련 기관 등록(INSERT)
		int insertNtwrkCmptResult = 0;
		int delete = ntwrkDAO.deleteNtwrkCmptinst(formdata);
		Map<String, Object> param = new HashMap<String, Object>();
		if(cmptList != null && !cmptList.isEmpty() && cmptList.size() > 0) {
			for(int cmpt : cmptList) {
				param.put("ntwrkIdx", formdata.get("ntwrkIdx"));
				param.put("cmptinstIdx", cmpt);
				insertNtwrkCmptResult += ntwrkDAO.insertNtwrkCmptinst(param);
			}
		}
		result.put("NTWRK_RESULT", insertNtwrkCmptResult);
		result.put("DELETE", delete);
		return result;
	}

	@Override
	public int insertFileData(Map<String, Object> param) {
		return ntwrkDAO.insertFileData(param);
	}

	@Override
	public List<Object> getNtwrkList(Map<String, Object> param) {
		return ntwrkDAO.getNtwrkList(param);
	}

	@Override
	public int getNtwrkTotalCount(Map<String, Object> param) {
		return ntwrkDAO.getNtwrkTotalCount(param);
	}

	@Override
	public Map<String, Object> getNtwrkOne(Map<String, Object> param) {
		return ntwrkDAO.getNtwrkOne(param);
	}

	@Override
	public List<Map<String, Object>> getNtwrkCmptinstList(Map<String, Object> param) {
		return ntwrkDAO.getNtwrkCmptinstList(param);
	}

	@Override
	public int deleteNtwrk(Map<String, Object> param) {
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
				
		return ntwrkDAO.deleteNtwrk(param);
	}

	@Override
	public List<Map<String, Object>> getNtwrkListByCmptinst(Map<String, Object> param) {
		return ntwrkDAO.getNtwrkListByCmptinst(param);
	}

	@Override
	public List<Map<String, Object>> getNtwrkFileList(Map<String, Object> param) {
		return ntwrkDAO.getNtwrkFileList(param);
	}

	@Override
	public Map<String, Object> getNtwrkFileOne(Map<String, Object> param) {
		return ntwrkDAO.getNtwrkFileOne(param);
	}

	@Transactional
	@Override
	public int updateNtwrkFileData(List<Integer> fileSeq, int ntwrkIdx) {
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
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("LAST_MODI_IDX", loginMemberIdx);
		param.put("LAST_MODI_ID", loginMemberId);
		param.put("LAST_MODI_NAME", loginMemberName);
		param.put("ntwrkIdx", ntwrkIdx);
		List<Map<String, Object>> originFileList = getNtwrkFileList(param);
		for(Map<String, Object> originFile : originFileList) {
			int fleIdx = Integer.valueOf(originFile.get("FLE_IDX").toString());
			param.put("fleIdx", fleIdx);
			if(fileSeq.contains(fleIdx)) {
				param.put("isdelete", 0);
				param.put("orderIdx", fileSeq.indexOf(fleIdx));
			} else {
				param.put("isdelete", 1);
				param.put("orderIdx", 0);
			}
			result += ntwrkDAO.updateNtwrkFileData(param);
		}
		return result;
	}

	@Override
	public List<Object> getNtwrkListForExcel(Map<String, Object> param) {
		return ntwrkDAO.getNtwrkListForExcel(param);
	}
}