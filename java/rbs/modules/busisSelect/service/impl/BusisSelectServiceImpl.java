package rbs.modules.busisSelect.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.util.UserDetailsHelper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import rbs.egovframework.LoginVO;
import rbs.modules.busisSelect.mapper.BusisSelectFileMapper;
import rbs.modules.busisSelect.mapper.BusisSelectMapper;
import rbs.modules.busisSelect.mapper.BusisSelectMultiMapper;
import rbs.modules.busisSelect.mapper.SsignMapper;
import rbs.modules.busisSelect.service.BusisSelectService;


@Service("busisSelectService")
public class BusisSelectServiceImpl extends EgovAbstractServiceImpl implements BusisSelectService {

	@Resource(name="busisSelectMapper")
	private BusisSelectMapper busisSelectDAO;

	@Resource(name="ssignMapper")
	private SsignMapper ssignDAO;
	
	@Resource(name="busisSelectFileMapper")
	private BusisSelectFileMapper busisSelectFileDAO;
	
	@Resource(name="busisSelectMultiMapper")
	private BusisSelectMultiMapper busisSelectMultiDAO;
	
    /**
     * 방문기업관리 이력 조회
     */
	@Override
	public List<Object> getList(Map<String, Object> param) {
		return busisSelectDAO.getList(param);
	}
	
	/**
	 * 방문기업관리 이력 갯수 조회
	 */
	@Override
	public int getTotalCount(Map<String, Object> param) {
		return busisSelectDAO.getTotalCount(param);
	}

	/**
	 * 기업바구니 목록 조회
	 */
	@Override
	public List<Object> getBskList(Map<String, Object> param) {
		return busisSelectDAO.getBskList(param);
	}
	
	/**
	 * 기업바구니 갯수 조회
	 */
	@Override
	public int getBskCount(Map<String, Object> param) {
		return busisSelectDAO.getBskCount(param);
	}
	
	@Override
	public Map<String, Object> getSptjdgns(Map<String, Object> param) {
		return busisSelectDAO.getSptjdgns(param);
	}

	/**
	 * 방문기업서식 목록 조회
	 */
	@Override
	public List<Object> getSupportList(Map<String, Object> param) {
		return busisSelectDAO.getSupportList(param);
	}
	
	/**
	 * 방문기업서식 갯수 조회
	 */
	@Override
	public int getSupportCount(Map<String, Object> param) {
		return busisSelectDAO.getSupportCount(param);
	}

	/**
	 * 방문기업서식 상세 조회
	 */
	@Override
	public DataMap getSptj(Map<String, Object> param) {
		return busisSelectDAO.getSptj(param);
	}

	/**
	 * 전자서명문서 발행여부 조회
	 */
	@Override
	public DataMap getPublished(Map<String, Object> param) {
		return busisSelectDAO.getPublished(param);
	}
	
	/**
	 * 기업정보 상세 조회
	 */
	@Override
	public DataMap getBpl(Map<String, Object> param) {
		return busisSelectDAO.getBpl(param);
	}
	
	/**
	 * 훈련이력 조회
	 */
	@Override
	public DataMap getTr(Map<String, Object> param) {
		return busisSelectDAO.getTr(param);
	}
	
	/**
	 * 소속기관 목록
	 */
	@Override
	public List<Object> getInsttList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return busisSelectDAO.getInsttList(param);
	}
	
	/**
	 * 업종 목록
	 */
	@Override
	public List<HashMap<String, Object>> getIndustCd() {
		return busisSelectDAO.getIndustCd();
	}
	
	/**
	 * 자동분류 조회
	 */
	@Override
	public DataMap getALSclas(Map<String, Object> param) {
		return busisSelectDAO.getALSclas(param);
	}
	
	/**
	 * 수동분류 조회
	 */
	@Override
	public DataMap getPLSclas(Map<String, Object> param) {
		return busisSelectDAO.getPLSclas(param);
	}

	/**
	 * 전자서명 등록 정보 조회
	 */
	@Override
	public Map<String, Object> getSSInfo(String formatId) {
		return ssignDAO.getSSInfo(formatId);
	}
	
	/**
	 * 방문기업관리 등록
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
		
		String value = param.get("sptdgnsIdx").toString();
		
		// 중복 조건 설정
		boolean result = value != null && !value.isEmpty();
		boolean tempYn = param.get("action").equals("temp");

		if(!result){
			if(tempYn) {
				param.put("tmprSaveYn", "Y");
				
				return busisSelectDAO.insert(param);
			}else {
				param.put("tmprSaveYn", "N");
				
				return busisSelectDAO.update(param);
			}
				
		}else {
			if(tempYn) {
				param.put("tmprSaveYn", "Y");

			}else {
				param.put("tmprSaveYn", "N");
				
			}
			return busisSelectDAO.update(param);
		}
		
	}
	
	/**
	 * 권한여부
	 */
	public int getAuthCount(Map<String, Object> param) {
    	return busisSelectDAO.getAuthCount(param);
    }
	
}