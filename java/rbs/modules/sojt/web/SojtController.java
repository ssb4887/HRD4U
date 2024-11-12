package rbs.modules.sojt.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import rbs.egovframework.LoginVO;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.egovframework.confirmProgress.ConfirmProgress;
import rbs.modules.basket.dto.CMRespDto;
import rbs.modules.recommend.RecommendDummyService;
import rbs.modules.sojt.dto.AcceptFormVO;
import rbs.modules.sojt.dto.ApplyFormVO;
import rbs.modules.sojt.dto.RejectFormVO;
import rbs.modules.sojt.service.SojtService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowonsoft.egovframework.annotation.ModuleMapping;
import com.woowonsoft.egovframework.resource.RbsMessageSource;
import com.woowonsoft.egovframework.util.CodeHelper;
import com.woowonsoft.egovframework.util.JSONObjectUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;
import com.woowonsoft.egovframework.web.ModuleController;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 샘플모듈<br/>
 * : 통합관리시스템 > 메뉴콘텐츠관리, 통합관리시스템 > 기능등록관리, 사용자 사이트 에서 사용
 * @author user
 *
 */
@Controller
@ModuleMapping(moduleId="sojt")
@RequestMapping({"/{siteId}/sojt", "/{admSiteId}/menuContents/{usrSiteId}/sojt"})
public class SojtController extends ModuleController {
	
	@Resource(name = "sojtService")
	private SojtService sojtService;
	
	@Resource(name = "recommendDummyService")
	private RecommendDummyService recommendDummyService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Resource(name = "jsonView")
	View jsonView;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
	private final Map<Integer, String> usertypes;
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(SojtController.class);
	
	public SojtController() {
		usertypes = new HashMap<Integer,String>();
		usertypes.put(5, "기업회원");
		usertypes.put(10,  "컨설턴트");
		usertypes.put(20,  "민간센터 회원");
		usertypes.put(30,  "주치의");
		usertypes.put(40,  "본부");
		usertypes.put(55,  "시스템 관리자");
	}
	
	/**
	 * 코드
	 * @param submitType
	 * @param itemInfo
	 * @return
	 */
	public HashMap<String, Object> getOptionHashMap(String submitType, JSONObject itemInfo) {
		return getOptionHashMap(submitType, itemInfo, null);
	}
	
	/**
	 * 코드
	 * @param submitType
	 * @param itemInfo
	 * @param addOptionHashMap
	 * @return
	 */
	public HashMap<String, Object> getOptionHashMap(String submitType, JSONObject itemInfo, HashMap<String, Object> addOptionHashMap) {
		// 코드
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		HashMap<String, Object> optnHashMap = (addOptionHashMap != null)?CodeHelper.getItemOptnHashMap(items, itemOrder, addOptionHashMap):CodeHelper.getItemOptnHashMap(items, itemOrder);
		return  optnHashMap;
	}
		
	@RequestMapping(value="/applyForm.do")	
	public String sojt(String id, String sojtIdx, Model model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		Map param = new HashMap();
		if(loginVO != null) {
			param.put("member_idx", loginVO.getMemberIdx());
		}
		param.put("bsiscnsl_idx", id);
		Map apply_content = sojtService.getApplyContent(param);
		List<Map> docs = recommendDummyService.getDocByZipcode((String)apply_content.get("BPL_NO"));
		List<Map> amIapplying = sojtService.amIapplying(param);
		if(!amIapplying.isEmpty()) {
			String confmStatus = (String) amIapplying.get(0).get("CONFM_STATUS");
			if(confmStatus.equals("40")) {
				model.addAttribute("amIapplying", amIapplying);
			}
		}
		
		if(sojtIdx != null) {
			param.put("sojt_idx", sojtIdx);
			Map accept_content = sojtService.getAcceptContent(param);
			model.addAttribute("accept_content", accept_content);
		}
		model.addAttribute("content", apply_content);
		model.addAttribute("docs", docs);
		model.addAttribute("bsiscnsl_idx", id);
		return getViewPath("/apply_form");
	}
	
	@RequestMapping(value="/apply.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String apply(ApplyFormVO applyVO, HttpServletRequest request) throws JsonProcessingException {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		Map param = new HashMap();
		param.put("bpl_no", applyVO.getBplNo());
		param.put("recommend_name", applyVO.getRecommendName());
		param.put("tr_addr", applyVO.getPostcode() + "||" + applyVO.getTrainAddr() + "||" + applyVO.getTrainAddrDetail());
		param.put("corp_name", applyVO.getCorpName());
		param.put("corp_ofcps", applyVO.getCorpOfcps());
		param.put("corp_telno", applyVO.getCorpTelno());
		param.put("corp_email", applyVO.getCorpEmail());
		param.put("insttIdx", request.getParameter("insttIdx"));
		param.put("doctorTelno", request.getParameter("doctorTelno"));
		param.put("regi_idx", loginVO.getMemberIdx());
		param.put("regi_id", loginVO.getMemberId());
		param.put("regi_name", loginVO.getMemberName());
		param.put("regi_ip", request.getRemoteAddr());
		param.put("bsiscnsl_idx", applyVO.getBsiscnslIdx());
		
		int sojtIdx_ = sojtService.getSojtIdxFromWithdraw(String.valueOf(applyVO.getBsiscnslIdx()));
		
		if(request.getParameter("sojt_idx") != null) {
			int sojt_idx = Integer.parseInt(request.getParameter("sojt_idx"));
			param.put("sojt_idx", sojt_idx);
			sojtService.editApply(param);
		} else if(sojtIdx_ != -1) {
			param.put("sojt_idx", sojtIdx_);
			sojtService.editApply(param);
		} else {
			sojtService.putApply(param);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Map result = new HashMap();
		result.put("status", "success");
		String jsonString = mapper.writeValueAsString(result);
		return jsonString;
	}
	
	@RequestMapping(value="/init.do")
	public String init(Model model, HttpServletRequest req) {
		LoginVO loginVO = (LoginVO)UserDetailsHelper.getAuthenticatedUser();
		String page_ = (String)req.getParameter("page");
		int page = page_ == null ? 1 : Integer.parseInt(page_);
		Map param = new HashMap();
		if(loginVO != null) {
			param.put("member_idx", loginVO.getMemberIdx());
			model.addAttribute("login", loginVO.isLogin());
			model.addAttribute("userType", loginVO.getUsertypeIdx());
		}
		param.put("page", page);
		List<Map> amIapplying = sojtService.amIapplying(param);
		List<Map> initList = sojtService.getInitList(param);
		model.addAttribute("list", initList);
		model.addAttribute("amIapplying", amIapplying);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		return getViewPath("/init");
	}
	
	@RequestMapping(value="/applyList.do")
	public String applyList(Model model, HttpServletRequest req, @PathVariable("siteId") String siteId) {
		LoginVO loginVO = (LoginVO)UserDetailsHelper.getAuthenticatedUser();
		String page_ = (String)req.getParameter("page");
		int page = page_ == null ? 1 : Integer.parseInt(page_);
		int usertype = 0;
		Map param = new HashMap();
		if(loginVO != null) {
			usertype = loginVO.getUsertypeIdx();
			param.put("member_idx", loginVO.getMemberIdx());
			model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
		}
		param.put("page", page);
		
		String bplNm = req.getParameter("bpl_nm");
		String bplNo = req.getParameter("bpl_no");
		String sdate = req.getParameter("sdate");
		String edate = req.getParameter("edate");
		String sign = req.getParameter("sign");
		String instt = req.getParameter("instt");
		String center = req.getParameter("center");
		
		param.put("bplNm", bplNm);
		param.put("bplNo", bplNo);
		param.put("sdate", sdate);
		param.put("edate", edate);
		param.put("sign", sign);
		param.put("instt", instt);
		param.put("center", center);
		
		
		
		
		List<Map> list = new ArrayList<Map>();
		switch(usertype) {
		case 5:
			param.put("bpl_no", loginVO.getBplNo());
			list = sojtService.getUserList(param);
			break;
		case 30:
		case 31:
			list = sojtService.getApplyList4Instt(param);
			break;
		case 40:
		case 41:
		case 55:
			list = sojtService.getApplyList4Head(param);
			break;
		default:
			log.debug("Unkown usertype: " + usertype);
			break;
		}
		model.addAttribute("list", list);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		model.addAttribute("siteId", siteId);
		return getViewPath("/apply_list");
	}
	
	@RequestMapping(value="/confirm.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String confirm(@RequestParam String id, HttpServletRequest request) throws JsonProcessingException {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		ObjectMapper mapper = new ObjectMapper();
		Map param = new HashMap();
		Map result = new HashMap();
		int usertype = 0;
		String memberIdx = "";
		if(loginVO != null) {
			usertype = loginVO.getUsertypeIdx();
			memberIdx = loginVO.getMemberIdx();
		}
		
		param.put("member_idx", memberIdx);
		param.put("sojt_idx", id);
		int validity = sojtService.checkValidity(param);
		
		if(loginVO != null) {
			param.put("regi_idx", loginVO.getMemberIdx());
			param.put("regi_id", loginVO.getMemberId());
			param.put("regi_name", loginVO.getMemberName());
		}
		param.put("regi_ip", request.getRemoteAddr());
		param.put("confm_status", 30);
		
		boolean valid_request = usertype == 30 && id != null &&  validity == 1;
		if(valid_request) {
			sojtService.updateStatus(param);
			result.put("status", "success");
		} else {
			result.put("status", "failed");
			result.put("message", "check if ID of the item or logined is valid or not");
		}
		
		String jsonString = mapper.writeValueAsString(result);
		return jsonString;
	}
	
	@RequestMapping(value="/accept.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String accept(AcceptFormVO acceptVO, HttpServletRequest request) throws JsonProcessingException {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		ObjectMapper mapper = new ObjectMapper();
		Map param = new HashMap();
		Map result = new HashMap();
		int usertype = loginVO.getUsertypeIdx();
		int status_ = acceptVO.getStatus();
		
		param.put("regi_idx", loginVO.getMemberIdx());
		param.put("regi_id", loginVO.getMemberId());
		param.put("regi_name", loginVO.getMemberName());
		param.put("regi_ip", request.getRemoteAddr());
		param.put("sojt_idx", acceptVO.getId());
		param.put("corpName", acceptVO.getCorpName());
		param.put("corp_ofcps", acceptVO.getCorpOfcps());
		param.put("corp_telno", acceptVO.getCorpTelno());
		param.put("corp_email", acceptVO.getCorpEmail());
		param.put("pri_sup_trget_corp_yn", acceptVO.getPriSupTrgetCorpYn());
		param.put("spcss_bpl_yn", acceptVO.getSpcssBplYn());
		param.put("wgdly_nm_stg_othbc_bpl_yn", acceptVO.getWgdlyNmStgOthbcBplYn());
		param.put("indacmt_bpl_yn", acceptVO.getIndacmtBplYn());
		param.put("sport_excl_induty_yn", acceptVO.getSportExclIndutyYn());
		
		param.put("member_idx", loginVO.getMemberIdx());
		int validity = sojtService.checkValidity(param);
		if(validity == 1 && status_ == 30) {
			param.put("confm_status", 50);
			sojtService.confirmAccept(param);
			result.put("status", "success");
		} else if(validity == 1 && status_ == 50 && "Y".equals(loginVO.getClsfCd())) {
			param.put("confm_status", 55);
			sojtService.confirmFinal(param);
			result.put("status", "success");
		}
		if(!"success".equals(result.get("status"))) {
			result.put("status", "failed");
			result.put("message", "권한을 확인해주세요. 현재 권한: " + usertypes.get(usertype));
		}
		String jsonString = mapper.writeValueAsString(result);
		return jsonString;
	}
	
	@RequestMapping(value="/reject.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String reject(RejectFormVO rejectVO, HttpServletRequest request) throws JsonProcessingException {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		ObjectMapper mapper = new ObjectMapper();
		Map param = new HashMap();
		Map result = new HashMap();
		int usertype = loginVO.getUsertypeIdx();
		int status_ = rejectVO.getStatus();
		String isBoss = loginVO.getClsfCd();
		
		param.put("regi_idx", loginVO.getMemberIdx());
		param.put("regi_id", loginVO.getMemberId());
		param.put("regi_name", loginVO.getMemberName());
		param.put("regi_ip", request.getRemoteAddr());
		param.put("sojt_idx", rejectVO.getId());
		param.put("cn", rejectVO.getContent());
		if(usertype == 30 && status_ == 30) {
			param.put("confm_status", 40);
			sojtService.confirmFinal(param);
			result.put("status", "success");
		} else if(usertype == 30 && status_ == 50 && "Y".equals(isBoss)) {
			param.put("confm_status", 40);
			sojtService.confirmFinal(param);
			result.put("status", "success");
		}
		if(!"success".equals(result.get("status"))) {
			result.put("status", "failed");
			result.put("message", "권한을 확인해주세요. 현재 권한: " + usertypes.get(usertype));
		}
		String jsonString = mapper.writeValueAsString(result);
		return jsonString;
	}
	
	@RequestMapping(value="/resultList.do")
	public String acceptList(Model model, HttpServletRequest request, @PathVariable("siteId") String siteId) {
		LoginVO loginVO = (LoginVO)UserDetailsHelper.getAuthenticatedUser();
		String page_ = (String)request.getParameter("page");
		int page = page_ == null ? 1 : Integer.parseInt(page_);
		int usertype = loginVO.getUsertypeIdx();
		Map param = new HashMap();
		param.put("member_idx", loginVO.getMemberIdx());
		param.put("page", page);
		
		String bplNm = request.getParameter("bpl_nm");
		String bplNo = request.getParameter("bpl_no");
		String sdate = request.getParameter("sdate");
		String edate = request.getParameter("edate");
		String sign = request.getParameter("sign");
		String instt = request.getParameter("instt");
		String center = request.getParameter("center");
		
		param.put("bplNm", bplNm);
		param.put("bplNo", bplNo);
		param.put("sdate", sdate);
		param.put("edate", edate);
		param.put("sign", sign);
		param.put("instt", instt);
		param.put("center", center);
		
		
		List<Map> list = new ArrayList<Map>();
		switch(usertype) {
		case 30:
		case 31:
			list = sojtService.getResultList4Instt(param);
			break;
		case 40:
		case 41:
		case 55:
			list = sojtService.getResultList4Head(param);
			break;
		default:
			log.debug("usertype not expected: " + usertype);
		}
		model.addAttribute("list", list);
		model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		model.addAttribute("clsf_cd", loginVO.getClsfCd());
		model.addAttribute("siteId", siteId);
		return getViewPath("/result_list");
	}
	
	@RequestMapping(value="/acceptForm.do")	
	public String acceptForm(String id, Model model, HttpServletRequest request) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		Map param = new HashMap();
		param.put("member_idx", loginVO.getMemberIdx());
		param.put("sojt_idx", id);
		param.put("bplNo", request.getParameter("bplNo"));
		int validity = sojtService.checkValidity(param);
		Map accept_content = sojtService.getAcceptContent(param);
		List<Map> docs = recommendDummyService.getDocByZipcode((String)accept_content.get("BPL_NO"));
		model.addAttribute("content", accept_content);
		model.addAttribute("docs", docs);
		model.addAttribute("sojt_idx", id);
		model.addAttribute("validity", validity);
		model.addAttribute("loginVO", loginVO);
		return getViewPath("/accept_form");
	}
	
	@RequestMapping(value="/resultForm.do")	
	public String resultForm(String id, Model model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		Map param = new HashMap();
		param.put("member_idx", loginVO.getMemberIdx());
		param.put("sojt_idx", id);
		int validity = sojtService.checkValidity(param);
		Map accept_content = sojtService.getAcceptContent(param);
		List<Map> docs = recommendDummyService.getDocByZipcode((String)accept_content.get("BPL_NO"));
		model.addAttribute("content", accept_content);
		model.addAttribute("docs", docs);
		model.addAttribute("sojt_idx", id);
		model.addAttribute("validity", validity);
		return getViewPath("/result_form");
	}
	
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.POST, value = "/getBrffcCd.do")
	public ModelAndView getBrffcCd(@RequestParam String postNo) throws Exception {
		ModelAndView mav = new ModelAndView();
		if(postNo == null) {
			return mav;
		}
		Map <String, Object> map = sojtService.getBrffcCd(postNo);
		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("지부지사 조회 성공").setBody(map).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;
	}
	
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.POST, value = "/getBrffcTel.do")
	public ModelAndView getBrffcTel(@RequestParam Map<String, Object> paramMap, Model model) throws Exception {
		ModelAndView mav = new ModelAndView();
		String brffcTel = sojtService.getBrffcTel(paramMap);
		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("지부지사 전화번호 조회 성공").setBody(brffcTel).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		model.addAttribute("brffcTel", brffcTel);
		return mav;
	}
	
	@RequestMapping(value="/withdraw.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String withdraw(String id, HttpServletRequest request) throws JsonProcessingException {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		ObjectMapper mapper = new ObjectMapper();
		Map param = new HashMap();
		Map result = new HashMap();
		String memberIdx = loginVO.getMemberIdx();
		param.put("regi_idx", memberIdx);
		param.put("regi_id", loginVO.getMemberId());
		param.put("regi_name", loginVO.getMemberName());
		param.put("regi_ip", request.getRemoteAddr());
		param.put("sojt_idx", id);
		
		if(sojtService.withdraw(param)) {
			result.put("status", "success");
			result.put("message", "회수 성공");
		} else {
			result.put("status", "fail");
			result.put("message", "회수 실패");
		}
		String jsonString = mapper.writeValueAsString(result);
		return jsonString;
	}
}
