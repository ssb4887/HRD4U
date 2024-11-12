package rbs.modules.ntwrk.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowonsoft.egovframework.util.UserDetailsHelper;

import rbs.egovframework.LoginVO;
import rbs.modules.ntwrk.mapper.NtwrkCmptinstMapper;
import rbs.modules.ntwrk.service.NtwrkCmptinstService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 지역네트워크 > 협의체 관리
 * @author minjung
 *
 */
@Service("ntwrkCmptinstService")
public class NtwrkCmptinstServiceImpl extends EgovAbstractServiceImpl implements NtwrkCmptinstService {
	
	@Resource(name = "ntwrkCmptinstMapper")
	private NtwrkCmptinstMapper ntwrkCmptinstDAO;
	
	@Transactional
	@Override
	public int cmptinstDataUpload(List<Map<String, Object>> list) throws Exception {
		int result = 0;
		for(Map<String, Object> param : list) {
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
			
			param.put("REGI_IDX", loginMemberIdx);
			param.put("REGI_ID", loginMemberId);
			param.put("REGI_NAME", loginMemberName);
			
			try {
				result += ntwrkCmptinstDAO.cmptinstDataUpload(param);
			} catch(Exception e) {
				throw new Exception(param.get("cmptinstName") + " 을(를) 등록 중 오류가 발생하였습니다.", e);
			}
		}
		
		return result;
	}

	@Override
	public Map<String, Object> getUserInsttIdx(Map<String, Object> map) {
		return ntwrkCmptinstDAO.getUserInsttIdx(map);
	}

	@Override
	public List<Object> getCmptinstList(Map<String, Object> param) {
		return ntwrkCmptinstDAO.getCmptinstList(param);
	}

	@Override
	public int getCmptinstTotalCount(Map<String, Object> param) {
		return ntwrkCmptinstDAO.getCmptinstTotalCount(param);
	}

	@Override
	public int deleteCmptinst(Map<String, Object> param) {
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
		
		param.put("LAST_MODI_IDX", loginMemberIdx);
		param.put("LAST_MODI_ID", loginMemberId);
		param.put("LAST_MODI_NAME", loginMemberName);
		return ntwrkCmptinstDAO.deleteCmptinst(param);
	}

	@Override
	public Map<String, Object> getCmptinstOne(Map<String, Object> param) {
		return ntwrkCmptinstDAO.getCmptinstOne(param);
	}

	@Override
	public int setCmptinstData(Map<String, Object> param) {
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
		
		param.put("REGI_IDX", loginMemberIdx);
		param.put("REGI_ID", loginMemberId);
		param.put("REGI_NAME", loginMemberName);
		
		param.put("LAST_MODI_IDX", loginMemberIdx);
		param.put("LAST_MODI_ID", loginMemberId);
		param.put("LAST_MODI_NAME", loginMemberName);
		
		if(param.get("cmptinstIdx").toString().isEmpty()) {
			// insert
			param.put("FLAG", "INSERT");
			return ntwrkCmptinstDAO.cmptinstDataUpload(param);
		} else {
			// update
			param.put("FLAG", "UPDATE");
			return ntwrkCmptinstDAO.updateCmptinstData(param);
		}
	}

	@Override
	public int insertAgremCorpData(Map<String, Object> param) {
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
		
		param.put("REGI_IDX", loginMemberIdx);
		param.put("REGI_ID", loginMemberId);
		param.put("REGI_NAME", loginMemberName);
		
		param.put("LAST_MODI_IDX", loginMemberIdx);
		param.put("LAST_MODI_ID", loginMemberId);
		param.put("LAST_MODI_NAME", loginMemberName);
		
		if(param.get("agremCorpIdx") != null && !param.get("agremCorpIdx").toString().isEmpty()) {
			return ntwrkCmptinstDAO.updateAgremCorpData(param);
		} else {
			return ntwrkCmptinstDAO.insertAgremCorpData(param);
		}
	}

	@Override
	public List<Object> getAgremCorpList(Map<String, Object> param) {
		return ntwrkCmptinstDAO.getAgremCorpList(param);
	}

	@Override
	public int deleteAgremCorps(Map<String, Object> param) {
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
		
		param.put("LAST_MODI_IDX", loginMemberIdx);
		param.put("LAST_MODI_ID", loginMemberId);
		param.put("LAST_MODI_NAME", loginMemberName);
		return ntwrkCmptinstDAO.deleteAgremCorps(param);
	}
	

}