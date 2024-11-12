package rbs.modules.supportForm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.util.UserDetailsHelper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import rbs.egovframework.LoginVO;
import rbs.modules.supportForm.mapper.SupportFormFileMapper;
import rbs.modules.supportForm.mapper.SupportFormMapper;
import rbs.modules.supportForm.mapper.SupportFormMultiMapper;
import rbs.modules.supportForm.service.SupportFormService;


@Service("supportFormService")
public class SupportFormServiceImpl extends EgovAbstractServiceImpl implements SupportFormService {

	@Resource(name="supportFormMapper")
	private SupportFormMapper supportFormDAO;
	
	@Resource(name="supportFormFileMapper")
	private SupportFormFileMapper supportFormFileDAO;
	
	@Resource(name="supportFormMultiMapper")
	private SupportFormMultiMapper supportFormMultiDAO;

    /**
	 * 서식그룹 전체 목록 조회
	 */
	@Override
	public List<Object> getList(Map<String, Object> param) {
		return supportFormDAO.getList(param);
	}
	
	/**
	 * 서식그룹 전체 갯수 조회
	 */
	@Override
	public int getTotalCount(Map<String, Object> param) {
		return supportFormDAO.getTotalCount(param);
	}
	
	/**
	 * 서식그룹 상세조회
	 */
	@Override
	public DataMap getView(Map<String, Object> param) {
		DataMap viewDAO = supportFormDAO.getView(param);
		return viewDAO;
	}

	/**
	 * 방문기업서식 목록
	 */
	@Override
	public List<Object> getFormList(Map<String, Object> param) {
		return  supportFormDAO.getFormList(param);
	}

	/**
	 * 방문기업서식 목록 수
	 */
	@Override
	public int getFormCount(Map<String, Object> param) {
		return  supportFormDAO.getFormCount(param);
	}

	/**
	 * 서식그룹 등록처리
	 */
	@Override
	public void setFormAndIdx(Map<String, Object> param, String sptjIdx, Map<String, Object> dataMap) {
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
		
		
		// 중복 조건 설정
		boolean cdt = sptjIdx !=null && !sptjIdx.isEmpty();
		boolean tmprSaveYCondition = param.get("tmprSaveYn").equals("Y") && cdt;
		boolean dcsnYCondition = param.get("dcsnYn").equals("Y") && cdt;
		
		
		/**
		 * 요구사항서식 insert || update처리
		 */
		// 임시저장
		if(param.get("action").equals("temp")){
			// 임시저장한적 있음
			if(tmprSaveYCondition) {
				supportFormDAO.update(param);
			}else if(dcsnYCondition) {
				// 등록완료한적 있음
				param.put("tmprSaveYn", "Y");
				param.put("dcsnYn", "N");
				
				supportFormDAO.update(param);
			}else {
				// 처음 임시저장
				param.put("tmprSaveYn", "Y");
				
				supportFormDAO.insert(param);
			}
			
			// 등록
		}else {
			// 임시저장한적 있음
			if(tmprSaveYCondition) {
				param.put("dcsnYn", "Y");
				
				supportFormDAO.update(param);
			}else if(dcsnYCondition) {
				// 등록완료한적 있음
				supportFormDAO.update(param);
			}else {
				// 처음 등록완료
				param.put("dcsnYn", "Y");
				
				supportFormDAO.insert(param);
			}
		}
		
		/**
		 *  방문기업서식 insert || delete처리
		 */
		int idx = Integer.parseInt(param.get("sptjIdx").toString());
		param.put("sptjIdx", idx);
		List<Map<String, Object>> formIdx = null;
		formIdx = supportFormDAO.getFormIdx(param);
		
		if(sptjIdx != null && !sptjIdx.isEmpty()) {
			// 이미 저장된 방문기업서식 값 조회
			List<String> valueList = new ArrayList<>();
			for(Map<String, Object> row : formIdx) {
				String value = row.get("ADMSFM_IDX").toString();
				if(value != null) {
					valueList.add(value);
				}
			}
			
			// 저장될 방문기업서식 값 조회
			String data = null;
			List<String> dataList= new ArrayList<>();
			for(Object obj : dataMap.values()) {
				if(obj != null) {
					data = obj.toString();
					dataList.add(data);
				}
			}
			
			// 새로운 방문기업서식을 추가하는 경우
			for(String inputData : dataList) {
				if(!valueList.contains(inputData)) {
					param.put("admsfmIdx", inputData);
					
					supportFormDAO.insertFormIdx(param);
				}
			}
			
			// 기존 방문기업서식을 삭제하는 경우
			for(String inputValue : valueList) {
				if(!dataList.contains(inputValue)) {
					param.put("admsfmIdx", inputValue);
					supportFormDAO.deleteFormIdx(param);
				}
			}
		}else {
			String data = null;
			for(Object obj : dataMap.values()) {
				if(obj != null) {
					data = obj.toString();
					param.put("admsfmIdx", data);
					supportFormDAO.insertFormIdx(param);
				}
			}
		}
	}
	
	/**
	 * 서식그룹 삭제
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
		
		return supportFormDAO.delete(param);
	}
	
	/**
	 * 서식그룹별 방문기업서식 IDX 조회
	 */
	@Override
	public List<Map<String, Object>> getFormIdx(Map<String, Object> param) {
		return 	supportFormDAO.getFormIdx(param);
	}
	
}