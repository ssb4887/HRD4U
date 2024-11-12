package rbs.modules.sojt.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import rbs.egovframework.LoginVO;
import rbs.modules.agreement.dto.RegisterFormVO;
import rbs.modules.agreement.mapper.AgreementFileMapper;
import rbs.modules.agreement.mapper.AgreementMapper;
import rbs.modules.agreement.service.AgreementService;
import rbs.modules.sojt.mapper.SojtMapper;
import rbs.modules.sojt.service.SojtService;

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

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("sojtService")
public class SojtServiceImpl extends EgovAbstractServiceImpl implements SojtService {

	@Resource(name="sojtMapper")
	private SojtMapper sojtDAO;

	@Override
	public Map getApplyContent(Map param) {
		Map result = new HashMap();
		result = sojtDAO.getApplyContent(param);
		List<Map> prvtcntr_list = sojtDAO.getPrvtcntrList();
		result.put("prvtcntr_list", prvtcntr_list);
		return result;
	}

	@Override
	@Transactional
	public void putApply(Map param) {
		int sojt_idx = sojtDAO.getNextIdSojt();
		param.put("sojt_idx", sojt_idx);
		int confm_idx = sojtDAO.getNextIdSojtConfm(param);
		param.put("confm_idx", confm_idx);
		param.put("confm_status", 10);
		sojtDAO.putApply(param);
		sojtDAO.putINSERTsojtLog(param);
	}
	
	@Override
	@Transactional
	public void editApply(Map param) {
		int confm_idx = sojtDAO.getNextIdSojtConfm(param);
		param.put("confm_idx", confm_idx);
		param.put("confm_status", 10);
		sojtDAO.editApply(param);
		sojtDAO.putINSERTsojtLog(param);
	}

	@Override
	public List<Map> getInitList(Map param) {
		return sojtDAO.initList(param);
	}

	@Override
	public List<Map> getUserList(Map param) {
		return sojtDAO.getUserList(param);
	}

	@Override
	public List<Map> amIapplying(Map param) {
		return sojtDAO.amIapplying(param);
	}

	@Override
	public List<Map> getApplyList4Instt(Map param) {
		return sojtDAO.getApplyList4Instt(param);
	}

	@Override
	@Transactional
	public void updateStatus(Map param) {
		int confm_idx = sojtDAO.getNextIdSojtConfm(param);
		param.put("confm_idx", confm_idx);
		sojtDAO.updateStatus(param);
		sojtDAO.putINSERTsojtLog(param);
	}

	@Override
	public List<Map> getAcceptList4Head(Map param) {
		return sojtDAO.getAcceptList4Head(param);
	}

	@Override
	public List<Map> getAcceptList4Instt(Map param) {
		return sojtDAO.getAcceptList4Instt(param);
	}

	@Override
	public List<Map> getApplyList4Head(Map param) {
		return sojtDAO.getApplyList4Head(param);
	}

	@Override
	public Map getAcceptContent(Map param) {
		Map result = sojtDAO.getAcceptContnt(param);
		BigDecimal bsiscnsl_idx = (BigDecimal)result.get("BSISCNSL_IDX");
		param.put("bsiscnsl_idx", bsiscnsl_idx);
		param.put("bpl_no", result.get("BPL_NO"));
		param.put("member_idx", result.get("REGI_IDX"));
		Map result2 = sojtDAO.getApplyContent(param);
		if(result2 != null) {
			for(Object k : result2.keySet()) {
				result.put(k, result2.get(k));
			}
		}
		int confm_status = Integer.parseInt((String)result.get("CONFM_STATUS"));
		if(confm_status < 50) {
			result2 = sojtDAO.getAcceptChecklist(param);
			if(result2 != null) {
				for(Object k : result2.keySet()) {
					result.put(k, result2.get(k));
				}
			}
		}
		return result;
	}

	@Override
	@Transactional
	public boolean confirmAccept(Map param) {
		int confm_idx = sojtDAO.getNextIdSojtConfm(param);
		param.put("confm_idx", confm_idx);
		sojtDAO.updateSOJTreq(param);
		sojtDAO.updateStatus(param);
		sojtDAO.putINSERTsojtLog(param);
		return false;
	}

	@Override
	@Transactional
	public boolean confirmFinal(Map param) {
		int confm_idx = sojtDAO.getNextIdSojtConfm(param);
		param.put("confm_idx", confm_idx);
		sojtDAO.updateStatus(param);
		sojtDAO.putINSERTsojtLog(param);
		return false;
	}

	@Override
	public List<Map> getResultList4Head(Map param) {
		return sojtDAO.getResultList4Head(param);
	}

	@Override
	public List<Map> getResultList4Instt(Map param) {
		return sojtDAO.getResultList4Instt(param);
	}

	@Override
	public int checkValidity(Map param) {
		return sojtDAO.checkValidity(param);
	}
	
	@Override
	public Map<String, Object> getBrffcCd(String substring) throws Exception {
		return sojtDAO.getBrffcCd(substring);
	}
	
	@Override
	public String getBrffcTel(Map<String, Object> param) throws Exception {
		return sojtDAO.getBrffcTel(param);
	}

	@Override
	@Transactional
	public boolean withdraw(Map param) {
		String regiIdx = sojtDAO.getRegiIdx(param);
		String memberIdx = (String)param.get("regi_idx");
		if(memberIdx.equals(regiIdx)) {
			int confm_idx = sojtDAO.getNextIdSojtConfm(param);
			param.put("confm_idx", confm_idx);
			param.put("confm_status", 20);
			sojtDAO.updateStatus(param);
			sojtDAO.putINSERTsojtLog(param);
			return true;
		}
		return false;
	}

	@Override
	public int getSojtIdxFromWithdraw(String bsiscnsl_idx) {
		return sojtDAO.getSojtIdxFromWithdraw(bsiscnsl_idx);
	}
}