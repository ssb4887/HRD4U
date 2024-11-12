package rbs.modules.taskhub.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rbs.egovframework.LoginVO;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.egovframework.confirmProgress.ConfirmProgress;
import rbs.egovframework.util.JwtUtil;
import rbs.modules.agreement.service.AgreementService;
import rbs.modules.member.service.LoginService;
import rbs.modules.taskhub.service.TaskhubService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowonsoft.egovframework.annotation.ModuleAttr;
import com.woowonsoft.egovframework.annotation.ModuleAuth;
import com.woowonsoft.egovframework.annotation.ModuleMapping;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.util.UserDetailsHelper;
import com.woowonsoft.egovframework.web.ModuleController;

/**
 * 샘플모듈<br/>
 * : 통합관리시스템 > 메뉴콘텐츠관리, 통합관리시스템 > 기능등록관리, 사용자 사이트 에서 사용
 * @author user
 *
 */
@Controller
@ModuleMapping(moduleId="taskhub")
@RequestMapping({"/{siteId}/taskhub", "/{admSiteId}/menuContents/{usrSiteId}/taskhub"})
public class TaskhubController extends ModuleController {
	
	@Resource(name = "taskhubService")
	private TaskhubService taskhubService;
	@Resource(name = "rbsLoginService")
	protected LoginService loginService;
	@Resource(name = "agreementService")
	private AgreementService agreementService;
	
	@RequestMapping(value="/main.do")
	public String main(Model model) throws ClassNotFoundException {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
		Map param = new HashMap();
		Map cnts = null;
		List<Map> delayed = null;
		String type_name = null;
		param.put("usertype_idx", loginVO.getUsertypeIdx());
		param.put("memberid", loginVO.getMemberId());
		Map board_ = taskhubService.getBoard(param);
		
		switch(loginVO.getUsertypeIdx()) {
		case 5:
			Map bsk = taskhubService.getBSK(loginVO);
			List<Map> progressBSC = taskhubService.getProgressBSC(loginVO.getBplNo());
			// List<Map> progressClinic = taskhubService.getProgressClinic(loginVO.getBplNo());
			type_name = "corpo";
			param.put("type_name", type_name);
			param.put("bpl_no", loginVO.getBplNo());
			param.put("sign_yn", "N");
			List<Map> progressCNSL = taskhubService.getProgressCNSL(param);
			List<Map> ceo = (List<Map>)board_.get("CEO");
			model.addAttribute("bsk", bsk);
			model.addAttribute("progressBSC", progressBSC);
			model.addAttribute("progressCNSL", progressCNSL);
			// model.addAttribute("progressClinic", progressClinic);
			cnts = taskhubService.dashboardCount(param, type_name);
			delayed = taskhubService.delayedJobs(param);
			model.addAttribute("cnt", cnts);
			model.addAttribute("delayed", delayed);
			model.addAttribute("ceo", ceo);
			break;
		case 10:
			type_name = "consultant";
			param.put("type_name", type_name);
			param.put("member_idx", loginVO.getMemberIdx());
			progressCNSL = taskhubService.getProgressCNSL(param);
			List<Map> consult = (List<Map>)board_.get("consult");
			model.addAttribute("progressCNSL", progressCNSL);
			model.addAttribute("consult", consult);
			cnts = taskhubService.dashboardCount(param, type_name);
			delayed = taskhubService.delayedJobs(param);
			model.addAttribute("cnt", cnts);
			model.addAttribute("delayed", delayed);
			break;
		case 20:
			cnts = taskhubService.getCntCenter(loginVO.getMemberIdx());
			List<Map> agrems = taskhubService.getAgreement(loginVO.getMemberIdx());
			model.addAttribute("agrem", agrems);
			type_name = "center";
			param.put("type_name", type_name);
			param.put("member_idx", loginVO.getMemberIdx());
			cnts = taskhubService.dashboardCount(param, type_name);
			delayed = taskhubService.delayedJobs(param);
			model.addAttribute("cnt", cnts);
			model.addAttribute("delayed", delayed);
			break;
		case 30:
		case 31:
			type_name = "instt";
			param.put("type_name", type_name);
			param.put("member_idx", loginVO.getMemberIdx());
			// 하드코딩이 너무 많아서 죄송
			List<Map> ceo_ = (List<Map>)board_.get("CEO");
			List<Map> dct_ = (List<Map>)board_.get("DCT");
			List<Map> doc_board = (List<Map>)board_.get("docBoard");
			List<Map> sptj = (List<Map>)board_.get("sptj");
			model.addAttribute("ceo", ceo_);
			model.addAttribute("dct", dct_);
			model.addAttribute("docBoard", doc_board);
			model.addAttribute("sptj", sptj);
			break;
		case 40:
		case 41:
			type_name = "headquarter";
			param.put("type_name", type_name);
			param.put("member_idx", loginVO.getMemberIdx());
			ceo_ = (List<Map>)board_.get("CEO");
			dct_ = (List<Map>)board_.get("DCT");
			doc_board = (List<Map>)board_.get("docBoard");
			sptj = (List<Map>)board_.get("sptj");
			model.addAttribute("ceo", ceo_);
			model.addAttribute("dct", dct_);
			model.addAttribute("docBoard", doc_board);
			model.addAttribute("sptj", sptj);
			break;
		default:
			type_name = "headquarter";
			param.put("type_name", type_name);
			ceo_ = (List<Map>)board_.get("CEO");
			dct_ = (List<Map>)board_.get("DCT");
			doc_board = (List<Map>)board_.get("docBoard");
			sptj = (List<Map>)board_.get("sptj");
			model.addAttribute("ceo", ceo_);
			model.addAttribute("dct", dct_);
			model.addAttribute("docBoard", doc_board);
			model.addAttribute("sptj", sptj);
			break;
		}
		model.addAttribute("notice", board_.get("notice"));
		model.addAttribute("hrdroom", board_.get("hrdroom"));
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("orgName", loginVO.getMemberNameOrg());
		return getViewPath("/main/"+type_name);
	}
	
	@RequestMapping(value="/req.do", method=RequestMethod.POST)
	public void sptjreq(@RequestParam String cn, HttpServletRequest req, HttpServletResponse res) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		Map param = new HashMap();
		param.put("bpl_no", loginVO.getBplNo());
		int nextId = taskhubService.getNextIdSptjReq(param);
		param.put("member_idx", loginVO.getMemberIdx());
		param.put("cn", cn);
		param.put("regi_idx", loginVO.getMemberIdx());
		param.put("regi_id", loginVO.getMemberId());
		param.put("regi_name", loginVO.getMemberName());
		param.put("regi_ip", req.getRemoteAddr());
		param.put("nextId", nextId);
		taskhubService.putSptjReq(param);
	}
	@RequestMapping(value="/reqlist.do")
	public String reqlist(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, Model model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		Map param = new HashMap();
		String type_name;
		switch(loginVO.getUsertypeIdx()) {
		case 5:
			Map bsk = taskhubService.getBSK(loginVO);
			model.addAttribute("bsk", bsk);
			model.addAttribute("name", bsk.get("BPL_NM"));
			type_name = "corpo";
			break;
		case 30:
		case 31:
			type_name = "instt";
			model.addAttribute("name", loginVO.getMemberName());
			param.put("member_idx", loginVO.getMemberIdx());
			break;
		case 40:
		case 41:
			type_name = "headquarter";
			model.addAttribute("name", loginVO.getMemberName());
			param.put("member_idx", loginVO.getMemberIdx());
			break;
		default:
			type_name = "headquarter";
			model.addAttribute("name", loginVO.getMemberName());
			param.put("member_idx", loginVO.getMemberIdx());
		}
		String page = (String)request.getParameter("page");
		int page_ = page != null ? Integer.parseInt(page) : 1;
		
		String bplNo = (String)request.getParameter("bplNo");
		String bplNm = (String)request.getParameter("bplNm");
		String status = (String)request.getParameter("status");
		String cn = (String)request.getParameter("cn");
		param.put("bplNo", bplNo);
		param.put("bplNm", bplNm);
		param.put("status", status);
		param.put("cn", cn);
		param.put("loginVO", loginVO);
		param.put("page", page_);
		param.put("type_name", type_name);
		List<Map> reqlist = taskhubService.getSptjReqList(param);
		model.addAttribute("loginVO", loginVO);
		model.addAttribute("list", reqlist);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		model.addAttribute("siteId", attrVO.getSiteId());
		return getViewPath("/reqlist");
	}
	@RequestMapping(value="/reqlistExcel.do")
	public String reqlistExcel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, Model model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		switch(loginVO.getUsertypeIdx()) {
		case 5:
			Map bsk = taskhubService.getBSK(loginVO);
			model.addAttribute("bsk", bsk);
			model.addAttribute("name", bsk.get("BPL_NM"));
			break;
		case 30:
		case 31:
			model.addAttribute("name", loginVO.getMemberName());
			break;
		case 40:
		case 41:
			model.addAttribute("name", loginVO.getMemberName());
			break;
		default:
			model.addAttribute("name", loginVO.getMemberName());
		}
		String page = (String)request.getParameter("page");
		int page_ = page != null ? Integer.parseInt(page) : 1;
		Map param = new HashMap();
		param.put("loginVO", loginVO);
		param.put("page", page_);
		List<Map> reqlist = taskhubService.getSptjReqList(param);
		model.addAttribute("loginVO", loginVO);
		model.addAttribute("list", reqlist);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		model.addAttribute("siteId", attrVO.getSiteId());
		return getViewPath("/reqlistExcel");
	}
	@RequestMapping(value="/support.do", method=RequestMethod.POST)
	@ResponseBody
	public String support(@RequestParam String req_idx, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
		Map param = new HashMap();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		param.put("member_idx", loginVO.getMemberIdx());
		param.put("member_id", loginVO.getMemberId());
		param.put("member_name", loginVO.getMemberName());
		param.put("member_ip", request.getRemoteAddr());
		param.put("status", 30);
		param.put("req_idx", req_idx);
		taskhubService.updateStpjReq(param);
		ObjectMapper mapper = new ObjectMapper();
		Map resp = new HashMap();
		resp.put("status", "success");
		resp.put("message", "done");
		String jsonString = mapper.writeValueAsString(resp);
		return jsonString;
	}
	@RequestMapping(value="/layer.do", method=RequestMethod.POST)
	@ResponseBody
	public String layer(@RequestParam String req_idx, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String member_idx = loginVO.getMemberIdx();
		String memberIdxFromREQ = taskhubService.getMemberIdx(req_idx);
		if(member_idx.equals(member_idx)) {
			LoginVO replace_vo = taskhubService.getLoginMap(req_idx);
			String id_ = replace_vo.getMemberId();
			Cookie access_cookie = JwtUtil.createAccessCookie(id_);
			Cookie refresh_cookie = JwtUtil.createRefreshCookie(id_);
			Map param = new HashMap();
			param.put("user_id", id_);
			param.put("token", refresh_cookie.getValue());
			loginService.setRefreshToken(param);
			response.addCookie(access_cookie);
			response.addCookie(refresh_cookie);
		}
		ObjectMapper mapper = new ObjectMapper();
		Map resp = new HashMap();
		resp.put("status", "success");
		resp.put("message", request.getContextPath()+"/web/logout/logoutSSO.do?mId=4");
		String jsonString = mapper.writeValueAsString(resp);
		return jsonString;
	}
	
	@RequestMapping(value="/finish.do", method=RequestMethod.POST)
	@ResponseBody
	public String testo(@RequestParam String req_idx, HttpServletRequest request) throws JsonProcessingException {
		Map param = new HashMap();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		param.put("member_idx", loginVO.getMemberIdx());
		param.put("member_id", loginVO.getMemberId());
		param.put("member_name", loginVO.getMemberName());
		param.put("member_ip", request.getRemoteAddr());
		param.put("status", 55);
		param.put("req_idx", req_idx);
		taskhubService.finishStpjReq(param);
		ObjectMapper mapper = new ObjectMapper();
		Map resp = new HashMap();
		resp.put("status", "success");
		resp.put("message", "done");
		String jsonString = mapper.writeValueAsString(resp);
		return jsonString;
	}
	
	@RequestMapping(value="/loginSupport.do", method=RequestMethod.POST)
	public void loginSupportPreProc(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		loginVO.setMemberId(id);
		session.setAttribute("loginVO", loginVO);
	}
		
	@RequestMapping(value="/program/hrdbsis.do")
	public String hrdbsis(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, Model model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String type_name = null;
		String path_ = null;
		Map param = new HashMap();
		switch(loginVO.getUsertypeIdx()) {
		case 5:
			Map bsk = taskhubService.getBSK(loginVO);
			model.addAttribute("bsk", bsk);
			type_name = "corpo";
			param.put("bpl_no", bsk.get("BPL_NO"));
			List<Map> hrdbsis_ = taskhubService.getBsisCorpo(param);
			Map cnsls = taskhubService.getCnslCorpo(param);
			List<Map> listCnsl = (List<Map>) cnsls.get("cnsl");
			List<Map> listCustom = (List<Map>) cnsls.get("custom");
			List<Map> listClinic = taskhubService.getClinicCorpor(param);
			List<Map> listSojt = taskhubService.getSOJTCorpo(param);
			model.addAttribute("listHrdbsis", hrdbsis_);
			model.addAttribute("listCnsl", listCnsl);
			model.addAttribute("listCustom",listCustom);
			model.addAttribute("listClinic", listClinic);
			model.addAttribute("listSojt", listSojt);
			model.addAttribute("name", loginVO.getMemberName());
			model.addAttribute("orgName", loginVO.getMemberNameOrg());
			model.addAttribute("siteId", attrVO.getSiteId());
			model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
			model.addAttribute("type_name", type_name);
			return getViewPath("/program/corpo");
		case 20:
			type_name = "center";
			param.put("member_idx", loginVO.getMemberIdx());
			
			path_ = "/program/hrdbsis";
			break;
		case 30:
		case 31:
			type_name = "instt";
			path_ = "/program/hrdbsis";
			param.put("member_idx", loginVO.getMemberIdx());
			break;
		case 40:
		case 41:
			type_name = "headquarter";
			path_ = "/program/hrdbsis";
			break;
		default:
			type_name = "headquarter";
			path_ = "/program/hrdbsis";
		}
		String page = request.getParameter("page");
		int page_ = page == null ? 1 : Integer.parseInt(page);
		String bpl_no = (String)request.getParameter("bpl_no");
		String corp_name = (String)request.getParameter("corp_name");
		String instt_name = (String)request.getParameter("instt_name");
		String status = (String)request.getParameter("status");
		String passed = (String)request.getParameter("passed");
		
		param.put("page", page_);
		param.put("bpl_no", bpl_no);
		param.put("corp_name", corp_name);
		param.put("instt_name", instt_name);
		param.put("status", status);
		param.put("passed", passed);
		param.put("type_name", type_name);
		List<Map> list = taskhubService.getProgramHRDBsis(param);
		model.addAttribute("list", list);
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("orgName", loginVO.getMemberNameOrg());
		model.addAttribute("siteId", attrVO.getSiteId());
		model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
		model.addAttribute("type_name", type_name);
		return getViewPath(path_);
	}
	
	@RequestMapping(value="/program/hrdbsisExcel.do")
	public String hrdbsisExcel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, Model model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String type_name = null;
		String path_ = null;
		Map param = new HashMap();
		switch(loginVO.getUsertypeIdx()) {
		case 5:
			Map bsk = taskhubService.getBSK(loginVO);
			model.addAttribute("bsk", bsk);
			type_name = "corpo";
			param.put("bpl_no", bsk.get("BPL_NO"));
			List<Map> hrdbsis_ = taskhubService.getBsisCorpo(param);
			Map cnsls = taskhubService.getCnslCorpo(param);
			List<Map> listCnsl = (List<Map>) cnsls.get("cnsl");
			List<Map> listCustom = (List<Map>) cnsls.get("custom");
			List<Map> listClinic = taskhubService.getClinicCorpor(param);
			List<Map> listSojt = taskhubService.getSOJTCorpo(param);
			model.addAttribute("listHrdbsis", hrdbsis_);
			model.addAttribute("listCnsl", listCnsl);
			model.addAttribute("listCustom",listCustom);
			model.addAttribute("listClinic", listClinic);
			model.addAttribute("listSojt", listSojt);
			model.addAttribute("name", loginVO.getMemberName());
			model.addAttribute("orgName", loginVO.getMemberNameOrg());
			model.addAttribute("siteId", attrVO.getSiteId());
			model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
			model.addAttribute("type_name", type_name);
			return getViewPath("/program/corpo");
		case 20:
			type_name = "center";
			param.put("member_idx", loginVO.getMemberIdx());
			
			path_ = "/program/hrdbsisExcel";
			break;
		case 30:
		case 31:
			type_name = "instt";
			path_ = "/program/hrdbsisExcel";
			param.put("member_idx", loginVO.getMemberIdx());
			break;
		case 40:
		case 41:
			type_name = "headquarter";
			path_ = "/program/hrdbsisExcel";
			break;
		default:
			type_name = "headquarter";
			path_ = "/program/hrdbsisExcel";
		}
		String page = request.getParameter("page");
		int page_ = page == null ? 1 : Integer.parseInt(page);
		String bpl_no = (String)request.getParameter("bpl_no");
		String corp_name = (String)request.getParameter("corp_name");
		String instt_name = (String)request.getParameter("instt_name");
		String status = (String)request.getParameter("status");
		String passed = (String)request.getParameter("passed");
		
		param.put("page", page_);
		param.put("bpl_no", bpl_no);
		param.put("corp_name", corp_name);
		param.put("instt_name", instt_name);
		param.put("status", status);
		param.put("passed", passed);
		param.put("type_name", type_name);
		param.put("useExcel", "useExcel");
		List<Map> list = taskhubService.getProgramHRDBsis(param);
		model.addAttribute("list", list);
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("orgName", loginVO.getMemberNameOrg());
		model.addAttribute("siteId", attrVO.getSiteId());
		model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
		model.addAttribute("type_name", type_name);
		return getViewPath(path_);
	}
	
	@RequestMapping(value="/getDoc.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getDoc() throws JsonProcessingException {
		Map param = new HashMap();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		param.put("member_idx", loginVO.getMemberIdx());
		Map result = taskhubService.getDoctorIntel(param);
		Map resp = new HashMap();
		if(result != null) {
			resp.put("status", "success");
			resp.put("message", result);
		} else {
			resp.put("status", "failed");
			resp.put("message", "nah");
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(resp);
		return jsonString;
	}
	
	@RequestMapping(value="/program/sojt.do")
	public String sojt(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, Model model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String type_name = "corpo";
		Map param = new HashMap();
		switch(loginVO.getUsertypeIdx()) {
		case 5:
			Map bsk = taskhubService.getBSK(loginVO);
			model.addAttribute("bsk", bsk);
			break;
		case 30:
		case 31:
			type_name = "instt";
			param.put("member_idx", loginVO.getMemberIdx());
			break;
		case 40:
		case 41:
			type_name = "headquarter";
			break;
		default:
			type_name = "headquarter";
			break;
			
		}
		String page = request.getParameter("page");
		int page_ = page == null ? 1 : Integer.parseInt(page);
		String bpl_no = (String)request.getParameter("bpl_no");
		String corp_name = (String)request.getParameter("corp_name");
		String instt_name = (String)request.getParameter("instt_name");
		String passed = (String)request.getParameter("passed");
		
		param.put("page", page_);
		param.put("bpl_no", bpl_no);
		param.put("corp_name", corp_name);
		param.put("instt_name", instt_name);
		param.put("passed", passed);
		param.put("type_name", type_name);
		List<Map> list = taskhubService.getProgramSojt(param);
		model.addAttribute("list", list);
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("orgName", loginVO.getMemberNameOrg());
		model.addAttribute("siteId", attrVO.getSiteId());
		model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
		return getViewPath("/program/sojt");
	}
	
	@RequestMapping(value="/program/sojtExcel.do")
	public String sojtExcel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, Model model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String type_name = "corpo";
		Map param = new HashMap();
		switch(loginVO.getUsertypeIdx()) {
		case 5:
			Map bsk = taskhubService.getBSK(loginVO);
			model.addAttribute("bsk", bsk);
			break;
		case 30:
		case 31:
			type_name = "instt";
			param.put("member_idx", loginVO.getMemberIdx());
			break;
		case 40:
		case 41:
			type_name = "headquarter";
			break;
		default:
			type_name = "headquarter";
			break;
			
		}
		String page = request.getParameter("page");
		int page_ = page == null ? 1 : Integer.parseInt(page);
		String bpl_no = (String)request.getParameter("bpl_no");
		String corp_name = (String)request.getParameter("corp_name");
		String instt_name = (String)request.getParameter("instt_name");
		String passed = (String)request.getParameter("passed");
		
		param.put("page", page_);
		param.put("bpl_no", bpl_no);
		param.put("corp_name", corp_name);
		param.put("instt_name", instt_name);
		param.put("passed", passed);
		param.put("type_name", type_name);
		param.put("useExcel", "useExcel");
		List<Map> list = taskhubService.getProgramSojt(param);
		model.addAttribute("list", list);
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("orgName", loginVO.getMemberNameOrg());
		model.addAttribute("siteId", attrVO.getSiteId());
		model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
		return getViewPath("/program/sojtExcel");
	}
	
	@RequestMapping(value="/program/cnslExcel.do")
	public String cnslExcel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, Model model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String type_name = "corpo";
		Map param = new HashMap();
		switch(loginVO.getUsertypeIdx()) {
		case 5:
			Map bsk = taskhubService.getBSK(loginVO);
			model.addAttribute("bsk", bsk);
			param.put("bplNo", loginVO.getBplNo());
			type_name = "corpo";
			break;
		case 10:
			type_name = "consultant";
			break;
		case 20:
			type_name = "center";
			break;
		case 30:
		case 31:
			type_name = "instt";
			break;
		case 40:
		case 41:
			type_name = "headquarter";
			break;
		default:
			type_name = "headquarter";
			break;
			
		}
		String page = request.getParameter("page");
		int page_ = page == null ? 1 : Integer.parseInt(page);
		String bpl_no = (String)request.getParameter("bpl_no");
		String corp_name = (String)request.getParameter("corp_name");
		String instt_name = (String)request.getParameter("instt_name");
		String status = (String)request.getParameter("status");
		String passed = (String)request.getParameter("passed");
		String cnsl_type = (String)request.getParameter("type");
		
		param.put("page", page_);
		param.put("bpl_no", bpl_no);
		param.put("corp_name", corp_name);
		param.put("instt_name", instt_name);
		param.put("status", status);
		param.put("passed", passed);
		param.put("type_name", type_name);
		param.put("cnslType", cnsl_type);
		param.put("member_idx", loginVO.getMemberIdx());
		param.put("useExcel", "useExcel");
		List<Map> list = taskhubService.getProgramCnsl(param);
		model.addAttribute("list", list);
		model.addAttribute("type_name" , type_name);
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("orgName", loginVO.getMemberNameOrg());
		model.addAttribute("siteId", attrVO.getSiteId());
		model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		return getViewPath("/program/cnslExcel");
	}
	
	@RequestMapping(value="/program/cnsl.do")
	public String cnsl(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, Model model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String type_name = "corpo";
		Map param = new HashMap();
		switch(loginVO.getUsertypeIdx()) {
		case 5:
			Map bsk = taskhubService.getBSK(loginVO);
			model.addAttribute("bsk", bsk);
			param.put("bplNo", loginVO.getBplNo());
			type_name = "corpo";
			break;
		case 10:
			type_name = "consultant";
			break;
		case 20:
			type_name = "center";
			break;
		case 30:
		case 31:
			type_name = "instt";
			break;
		case 40:
		case 41:
			type_name = "headquarter";
			break;
		default:
			type_name = "headquarter";
			break;
			
		}
		String page = request.getParameter("page");
		int page_ = page == null ? 1 : Integer.parseInt(page);
		String bpl_no = (String)request.getParameter("bpl_no");
		String corp_name = (String)request.getParameter("corp_name");
		String instt_name = (String)request.getParameter("instt_name");
		String status = (String)request.getParameter("status");
		String passed = (String)request.getParameter("passed");
		String cnsl_type = (String)request.getParameter("type");
		
		param.put("page", page_);
		param.put("bpl_no", bpl_no);
		param.put("corp_name", corp_name);
		param.put("instt_name", instt_name);
		param.put("status", status);
		param.put("passed", passed);
		param.put("type_name", type_name);
		param.put("cnslType", cnsl_type);
		param.put("member_idx", loginVO.getMemberIdx());
		List<Map> list = taskhubService.getProgramCnsl(param);
		model.addAttribute("list", list);
		model.addAttribute("type_name" , type_name);
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("orgName", loginVO.getMemberNameOrg());
		model.addAttribute("siteId", attrVO.getSiteId());
		model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		return getViewPath("/program/cnsl");
	}
	
	@RequestMapping(value="/program/clinic.do")
	public String programaClinic(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, Model model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String type_name = "corpo";
		Map param = new HashMap();
		switch(loginVO.getUsertypeIdx()) {
		case 5:
			Map bsk = taskhubService.getBSK(loginVO);
			model.addAttribute("bsk", bsk);
			param.put("bplNo", loginVO.getBplNo());
			type_name = "corpo";
			break;
		case 10:
			type_name = "consultant";
			break;
		case 20:
			type_name = "center";
			break;
		case 30:
		case 31:
			
			type_name = "instt";
			break;
		case 40:
		case 41:
			type_name = "headquarter";
			break;
		default:
			type_name = "headquarter";
			break;
			
		}
		String page = request.getParameter("page");
		int page_ = page == null ? 1 : Integer.parseInt(page);
		String bpl_no = (String)request.getParameter("bpl_no");
		String corp_name = (String)request.getParameter("corp_name");
		String instt_name = (String)request.getParameter("instt_name");
		String passed = (String)request.getParameter("passed");
		
		param.put("page", page_);
		param.put("bpl_no", bpl_no);
		param.put("corp_name", corp_name);
		param.put("instt_name", instt_name);
		param.put("passed", passed);
		param.put("type_name", type_name);
		param.put("member_idx", loginVO.getMemberIdx());
		List<Map> list = taskhubService.getProgramClinic(param);
		model.addAttribute("list", list);
		model.addAttribute("type_name" , type_name);
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("orgName", loginVO.getMemberNameOrg());
		model.addAttribute("siteId", attrVO.getSiteId());
		model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		return getViewPath("/program/clinic");
	}
	
	@RequestMapping(value="/program/clinicExcel.do")
	public String clinicExcel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, Model model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String type_name = "corpo";
		Map param = new HashMap();
		switch(loginVO.getUsertypeIdx()) {
		case 5:
			Map bsk = taskhubService.getBSK(loginVO);
			model.addAttribute("bsk", bsk);
			param.put("bplNo", loginVO.getBplNo());
			type_name = "corpo";
			break;
		case 10:
			type_name = "consultant";
			break;
		case 20:
			type_name = "center";
			break;
		case 30:
		case 31:
			
			type_name = "instt";
			break;
		case 40:
		case 41:
			type_name = "headquarter";
			break;
		default:
			type_name = "headquarter";
			break;
			
		}
		String page = request.getParameter("page");
		int page_ = page == null ? 1 : Integer.parseInt(page);
		String bpl_no = (String)request.getParameter("bpl_no");
		String corp_name = (String)request.getParameter("corp_name");
		String instt_name = (String)request.getParameter("instt_name");
		String passed = (String)request.getParameter("passed");
		
		param.put("page", page_);
		param.put("bpl_no", bpl_no);
		param.put("corp_name", corp_name);
		param.put("instt_name", instt_name);
		param.put("passed", passed);
		param.put("type_name", type_name);
		param.put("member_idx", loginVO.getMemberIdx());
		param.put("useExcel", "useExcel");
		List<Map> list = taskhubService.getProgramClinic(param);
		model.addAttribute("list", list);
		model.addAttribute("type_name" , type_name);
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("orgName", loginVO.getMemberNameOrg());
		model.addAttribute("siteId", attrVO.getSiteId());
		model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		return getViewPath("/program/clinicExcel");
	}
	
	@RequestMapping(value="/corp/centers.do")
	public String centers(@ModuleAttr ModuleAttrVO attrVO, Model model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		switch(loginVO.getUsertypeIdx()) {
		case 5:
			Map bsk = taskhubService.getBSK(loginVO);
			model.addAttribute("bsk", bsk);
			break;
		case 30:
		case 31:
			break;
		case 40:
		case 41:
			
			break;
		}
		List<Map> list = taskhubService.getCorpCenters();
		model.addAttribute("list", list);
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("orgName", loginVO.getMemberNameOrg());
		model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
		model.addAttribute("siteId", attrVO.getSiteId());
		return getViewPath("/corporation/centers");
	}
	@RequestMapping(value="/corp/corps.do")
	public String corps(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, Model model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		Map param = new HashMap();
		switch(loginVO.getUsertypeIdx()) {
		case 5:
			Map bsk = taskhubService.getBSK(loginVO);
			model.addAttribute("bsk", bsk);
			break;
		case 30:
		case 31:
			model.addAttribute("type_name", "instt");
			param.put("instt_idx", loginVO.getInsttIdx());
			param.put("type_name", "instt");
			break;
		case 40:
		case 41:
			model.addAttribute("type_name", "headquarter");
			String instt_name = (String)request.getParameter("insttName");
			param.put("instt_name", instt_name);
			param.put("type_name", "headquarter");
			break;
		default:
			model.addAttribute("type_name", "headquarter");
			instt_name = (String)request.getParameter("insttName");
			param.put("instt_name", instt_name);
			param.put("type_name", "headquarter");
			break;
		}
		String page = request.getParameter("page");
		int page_ = page == null ? 1 : Integer.parseInt(page);
		String bpl_no = (String)request.getParameter("bplNo");
		String corp_name = (String)request.getParameter("corpName");
		String member_yn = (String)request.getParameter("member_yn");
		String passed = (String)request.getParameter("passed");
		String hashtag = (String)request.getParameter("hashtag");
		param.put("page", page_);
		param.put("bpl_no", bpl_no);
		param.put("corp_name", corp_name);
		param.put("member_yn", member_yn);
		param.put("passed", passed);
		param.put("hashtag", hashtag);
		List<Map> list = taskhubService.getCorpCorps(param);
		model.addAttribute("list", list);
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("siteId", attrVO.getSiteId());
		model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
		return getViewPath("/corporation/corps");
	}
	
	@RequestMapping(value="/corp/corpsExcel.do")
	public String corpsExcel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, Model model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		Map param = new HashMap();
		switch(loginVO.getUsertypeIdx()) {
		case 5:
			Map bsk = taskhubService.getBSK(loginVO);
			model.addAttribute("bsk", bsk);
			break;
		case 30:
		case 31:
			model.addAttribute("type_name", "instt");
			param.put("instt_idx", loginVO.getInsttIdx());
			param.put("type_name", "instt");
			break;
		case 40:
		case 41:
			model.addAttribute("type_name", "headquarter");
			String instt_name = (String)request.getParameter("insttName");
			param.put("instt_name", instt_name);
			param.put("type_name", "headquarter");
			break;
		default:
			model.addAttribute("type_name", "headquarter");
			instt_name = (String)request.getParameter("insttName");
			param.put("instt_name", instt_name);
			param.put("type_name", "headquarter");
			break;
		}
		String page = request.getParameter("page");
		int page_ = page == null ? 1 : Integer.parseInt(page);
		String bpl_no = (String)request.getParameter("bplNo");
		String corp_name = (String)request.getParameter("corpName");
		String member_yn = (String)request.getParameter("member_yn");
		String passed = (String)request.getParameter("passed");
		String hashtag = (String)request.getParameter("hashtag");
		param.put("page", page_);
		param.put("bpl_no", bpl_no);
		param.put("corp_name", corp_name);
		param.put("member_yn", member_yn);
		param.put("passed", passed);
		param.put("hashtag", hashtag);
		param.put("useExcel", "useExcel");
		List<Map> list = taskhubService.getCorpCorps(param);
		model.addAttribute("list", list);
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("siteId", attrVO.getSiteId());
		model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
		return getViewPath("/corporation/corpsExcel");
	}
	
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value = "/corp/agreement.do")
	public String list(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model, @PathVariable("siteId") String siteId) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		int usertype_idx = loginVO.getUsertypeIdx();
		// who am i?
	    String bplNo = loginVO.getBplNo();
		Map param = new HashMap();
		String page = request.getParameter("page");
		int page_ = page == null ? 1 : Integer.parseInt(page);
		param.put("page", page_);
		switch(usertype_idx) {
			case 55:
			case 41:
			case 40:
				String instt_nm = request.getParameter("instt");
				String center_nm = request.getParameter("center");
				param.put("instt_name", instt_nm);
				param.put("center_name", center_nm);
				model.addAttribute("type_name", "headquarter");
				break;
			case 31:
			case 30:
				String bpl_no = request.getParameter("bplNo");
				String bpl_nm = request.getParameter("bplNm");
				center_nm = request.getParameter("center");
				param.put("bpl_no", bpl_no);
				param.put("bpl_nm", bpl_nm);
				param.put("center_name", center_nm);
				param.put("type_name", "instt");
				param.put("instt_idx", loginVO.getInsttIdx());
				model.addAttribute("type_name", "instt");
				break;
			case 20:
				break;
			default: //5
				Map bsk = taskhubService.getBSK(loginVO);
				model.addAttribute("bsk", bsk);
				break;
		}
		if(usertype_idx == 20) {
			String member_idx = loginVO.getMemberIdx();
			param.put("member_idx", member_idx);
		}
		if(usertype_idx == 30 || usertype_idx == 31) {
			// 주치의는 신청 단계 이상일 때만 볼 수 있다
			param.put("is_instt", "Y");
		}
		String start_dt = request.getParameter("sdate");
		String end_dt = request.getParameter("edate");
		String sign_yn = request.getParameter("sign");
		param.put("start_dt", start_dt);
		param.put("end_dt", end_dt);
		param.put("sign_yn", sign_yn);
    	List<Map> list = agreementService.getList(param);
	    
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());							// 진행단계 코드
		model.addAttribute("bpl_no", bplNo);
		model.addAttribute("usertype_idx", usertype_idx);
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("orgName", loginVO.getMemberNameOrg());
		model.addAttribute("siteId", siteId);
		return getViewPath("/corporation/agreement");
	}
	
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value = "/corp/agreementExcel.do")
	public String agreementExcel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model, @PathVariable("siteId") String siteId) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		int usertype_idx = loginVO.getUsertypeIdx();
		// who am i?
	    String bplNo = loginVO.getBplNo();
		Map param = new HashMap();
		String page = request.getParameter("page");
		int page_ = page == null ? 1 : Integer.parseInt(page);
		param.put("page", page_);
		switch(usertype_idx) {
			case 55:
			case 41:
			case 40:
				String instt_nm = request.getParameter("instt");
				String center_nm = request.getParameter("center");
				param.put("instt_name", instt_nm);
				param.put("center_name", center_nm);
				model.addAttribute("type_name", "headquarter");
				break;
			case 31:
			case 30:
				String bpl_no = request.getParameter("bplNo");
				String bpl_nm = request.getParameter("bplNm");
				center_nm = request.getParameter("center");
				param.put("bpl_no", bpl_no);
				param.put("bpl_nm", bpl_nm);
				param.put("center_name", center_nm);
				param.put("type_name", "instt");
				param.put("instt_idx", loginVO.getInsttIdx());
				model.addAttribute("type_name", "instt");
				break;
			case 20:
				break;
			default: //5
				Map bsk = taskhubService.getBSK(loginVO);
				model.addAttribute("bsk", bsk);
				break;
		}
		if(usertype_idx == 20) {
			String member_idx = loginVO.getMemberIdx();
			param.put("member_idx", member_idx);
		}
		if(usertype_idx == 30 || usertype_idx == 31) {
			// 주치의는 신청 단계 이상일 때만 볼 수 있다
			param.put("is_instt", "Y");
		}
		String start_dt = request.getParameter("sdate");
		String end_dt = request.getParameter("edate");
		String sign_yn = request.getParameter("sign");
		param.put("start_dt", start_dt);
		param.put("end_dt", end_dt);
		param.put("sign_yn", sign_yn);
		param.put("useExcel", "useExcel");
    	List<Map> list = agreementService.getList(param);
	    
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());							// 진행단계 코드
		model.addAttribute("bpl_no", bplNo);
		model.addAttribute("usertype_idx", usertype_idx);
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("orgName", loginVO.getMemberNameOrg());
		model.addAttribute("siteId", siteId);
		return getViewPath("/corporation/agreementExcel");
	}
	
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value = "/agreement.do")
	public String agremlist(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model, @PathVariable("siteId") String siteId) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		int usertype_idx = loginVO.getUsertypeIdx();
		// who am i?
	    String bplNo = loginVO.getBplNo();
		Map param = new HashMap();
		String page = request.getParameter("page");
		int page_ = page == null ? 1 : Integer.parseInt(page);
		param.put("page", page_);
		String type_name = "corpo";
		switch(usertype_idx) {
			case 20:
				String member_idx = loginVO.getMemberIdx();
				param.put("member_idx", member_idx);
				type_name = "center";
				break;
			default: //5
				Map bsk = taskhubService.getBSK(loginVO);
				model.addAttribute("bsk", bsk);
				param.put("type_name", "corpo");
				param.put("bpl_no", bsk.get("BPL_NO"));
				break;
		}
		String start_dt = request.getParameter("sdate");
		String end_dt = request.getParameter("edate");
		String sign_yn = request.getParameter("sign");
		param.put("start_dt", start_dt);
		param.put("end_dt", end_dt);
		param.put("sign_yn", sign_yn);
    	List<Map> list = agreementService.getList(param);
	    
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());							// 진행단계 코드
		model.addAttribute("bpl_no", bplNo);
		model.addAttribute("usertype_idx", usertype_idx);
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("orgName", loginVO.getMemberNameOrg());
		model.addAttribute("siteId", siteId);
		return getViewPath("/agreement/" + type_name);
	}
	
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value="/inquiry.do")
	public String inquery(@ModuleAttr ModuleAttrVO attrVO, Model model, HttpServletRequest request, @PathVariable("siteId") String siteId) {
		String type_name = null;
		LoginVO loginVO = (LoginVO)UserDetailsHelper.getAuthenticatedUser();
		int usertype_idx = loginVO.getUsertypeIdx();
		Map param = new HashMap();
		
		switch(usertype_idx) {
		case 55:
		case 41:
		case 40:
			type_name = "headquarter";
			break;
		case 31:
		case 30:
			type_name = "instt";
			param.put("member_idx", loginVO.getMemberIdx());
			break;
		case 20:
			type_name = "center";
			param.put("member_idx", loginVO.getMemberIdx());
			break;
		case 10:
			type_name = "consultant";
			param.put("member_idx", loginVO.getMemberIdx());
			break;
		default:
			type_name = "corpo";
			param.put("bpl_no", loginVO.getBplNo());
			Map bsk = taskhubService.getBSK(loginVO);
			model.addAttribute("bsk", bsk);
			break;
		}
		String page = request.getParameter("page");
		int page_ = page == null ? 1 : Integer.parseInt(page);
		String bpl_no = (String)request.getParameter("bplNo");
		String corp_name = (String)request.getParameter("corpName");
		param.put("type_name", type_name);
		param.put("page", page_);
		param.put("bpl_nm", corp_name);
		param.put("bplNo", bpl_no);
		List<Map> list = taskhubService.costInquiry(param);
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("orgName", loginVO.getMemberNameOrg());
		model.addAttribute("type_name", type_name);
		model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
		model.addAttribute("list", list);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		model.addAttribute("siteId", siteId);
		return getViewPath("/inquiry/" + type_name);
	}
	
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value="/inquiryExcel.do")
	public String inqueryExcel(@ModuleAttr ModuleAttrVO attrVO, Model model, HttpServletRequest request, @PathVariable("siteId") String siteId) {
		String type_name = null;
		LoginVO loginVO = (LoginVO)UserDetailsHelper.getAuthenticatedUser();
		int usertype_idx = loginVO.getUsertypeIdx();
		Map param = new HashMap();
		
		type_name = "headquarter";
		String page = request.getParameter("page");
		int page_ = page == null ? 1 : Integer.parseInt(page);
		String bpl_no = (String)request.getParameter("bplNo");
		String corp_name = (String)request.getParameter("corpName");
		param.put("type_name", type_name);
		param.put("page", page_);
		param.put("bpl_nm", corp_name);
		param.put("bplNo", bpl_no);
		param.put("useExcel", "useExcel");
		List<Map> list = taskhubService.costInquiry(param);
		model.addAttribute("name", loginVO.getMemberName());
		model.addAttribute("orgName", loginVO.getMemberNameOrg());
		model.addAttribute("type_name", type_name);
		model.addAttribute("usertype_idx", loginVO.getUsertypeIdx());
		model.addAttribute("list", list);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		model.addAttribute("siteId", siteId);
		return getViewPath("/inquiry/" + "headquarterExcel");
	}
	
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value="/count.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String dashboardCount(Model model) throws JsonProcessingException {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		Map param = new HashMap();
		String type_name = null;
		switch(loginVO.getUsertypeIdx()) {
		case 30:
			type_name = "instt";
			
			break;
		default:
			type_name = "headquarter";
			break;
		}
		param.put("type_name", type_name);
		param.put("member_idx", loginVO.getMemberIdx());
		Map cnts = taskhubService.dashboardCount(param, type_name);
		List<Map> delayed = taskhubService.delayedJobs(param);
		ObjectMapper mapper = new ObjectMapper();
		Map resp = new HashMap();
		resp.put("cnts", cnts);
		resp.put("delayed", delayed);
		String jsonString = mapper.writeValueAsString(resp);
		return jsonString;
	}
}