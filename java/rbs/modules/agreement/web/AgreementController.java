package rbs.modules.agreement.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import rbs.egovframework.LoginVO;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.egovframework.auth.interceptor.AuthInterceptor;
import rbs.egovframework.confirmProgress.ConfirmProgress;
import rbs.modules.agreement.dto.AgreementConfirmVO;
import rbs.modules.agreement.dto.RegisterFormVO;
import rbs.modules.agreement.service.AgreementService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowonsoft.egovframework.annotation.ModuleAttr;
import com.woowonsoft.egovframework.annotation.ModuleAuth;
import com.woowonsoft.egovframework.annotation.ModuleMapping;
import com.woowonsoft.egovframework.form.DataForm;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.prop.RbsProperties;
import com.woowonsoft.egovframework.resource.RbsMessageSource;
import com.woowonsoft.egovframework.util.CodeHelper;
import com.woowonsoft.egovframework.util.JSONObjectUtil;
import com.woowonsoft.egovframework.util.PathUtil;
import com.woowonsoft.egovframework.util.StringUtil;
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
@ModuleMapping(moduleId="agreement")
@RequestMapping({"/{siteId}/agreement", "/{admSiteId}/menuContents/{usrSiteId}/agreement"})
public class AgreementController extends ModuleController {
	
	@Resource(name = "agreementService")
	private AgreementService agreementService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(AgreementController.class);
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
	
	/**
	 * 목록조회
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value = "/list.do")
	public String list(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model, @PathVariable("siteId") String siteId) throws Exception {
		int usertype_idx = 0;
		String bplNo = "";
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if(loginVO != null) {
			usertype_idx = loginVO.getUsertypeIdx();
			bplNo = loginVO.getBplNo();
		}
		// who am i?
	    // 검색 조건 로직
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
				break;
			case 31:
			case 30:
				String bpl_no = request.getParameter("bpl_no");
				String bpl_nm = request.getParameter("bpl_nm");
				param.put("bpl_no", bpl_no);
				param.put("bpl_nm", bpl_nm);
				break;
			case 20:
				String start_dt = request.getParameter("sdate");
				String end_dt = request.getParameter("edate");
				String sign_yn = request.getParameter("sign");
				param.put("start_dt", start_dt);
				param.put("end_dt", end_dt);
				param.put("sign_yn", sign_yn);
				break;
			default: // 5
				param.put("bpl_no", bplNo);
				break;
		}
		
		// 권한에 따른 select 조건 변경
		if(usertype_idx == 20) {
			// 민간 센터는 자신에게 요청온 것을 본다
			String member_idx = loginVO.getMemberIdx();
			param.put("member_idx", member_idx);
		}
		if(usertype_idx == 30 || usertype_idx == 31) {
			// 주치의는 신청 단계 이상일 때만 볼 수 있다
			param.put("is_instt", "Y");
		}
    	List<Map> list = agreementService.getList(param);
	    
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());							// 진행단계 코드
		model.addAttribute("bpl_no", bplNo);
		model.addAttribute("usertype_idx", usertype_idx);
		model.addAttribute("siteId", siteId);
    	fn_setCommonPath(attrVO);
		return getViewPath("/list");
	}
	
	/**
	 * 목록조회
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value = "/listExcel.do")
	public String listExcel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model, @PathVariable("siteId") String siteId) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		int usertype_idx = loginVO.getUsertypeIdx();
		// who am i?
	    String bplNo = loginVO.getBplNo();
	    // 검색 조건 로직
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
				break;
			case 31:
			case 30:
				String bpl_no = request.getParameter("bpl_no");
				String bpl_nm = request.getParameter("bpl_nm");
				param.put("bpl_no", bpl_no);
				param.put("bpl_nm", bpl_nm);
				break;
			case 20:
				String start_dt = request.getParameter("sdate");
				String end_dt = request.getParameter("edate");
				String sign_yn = request.getParameter("sign");
				param.put("start_dt", start_dt);
				param.put("end_dt", end_dt);
				param.put("sign_yn", sign_yn);
				break;
			default: // 5
				param.put("bpl_no", bplNo);
				break;
		}
		
		// 권한에 따른 select 조건 변경
		if(usertype_idx == 20) {
			// 민간 센터는 자신에게 요청온 것을 본다
			String member_idx = loginVO.getMemberIdx();
			param.put("member_idx", member_idx);
		}
		if(usertype_idx == 30 || usertype_idx == 31) {
			// 주치의는 신청 단계 이상일 때만 볼 수 있다
			param.put("is_instt", "Y");
		}
		
		param.put("useExcel", "useExcel");
    	List<Map> list = agreementService.getList(param);
	    
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());							// 진행단계 코드
		model.addAttribute("bpl_no", bplNo);
		model.addAttribute("usertype_idx", usertype_idx);
		model.addAttribute("siteId", siteId);
    	fn_setCommonPath(attrVO);
		return getViewPath("/listExcel");
	}
		
	/**
	 * 기본경로
	 */
	public void fn_setCommonPath(ModuleAttrVO attrVO) {	
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		DataForm queryString = attrVO.getQueryString();
		
		boolean useSsl = JSONObjectUtil.isEquals(settingInfo, "use_ssl", "1");
		
		String idxName = JSONObjectUtil.getString(settingInfo, "idx_name");			// 상세조회 key
		String pageName = JSONObjectUtil.getString(settingInfo, "page_name");		// 목록 페이징  key

		String listSearchId = "list_search";
		String[] searchParams = PathUtil.getSearchParams(JSONObjectUtil.getJSONObject(itemInfo, listSearchId));	// 목록 검색 항목
		String[] tabBaseParams = null;
		String cateTabId = JSONObjectUtil.getString(settingInfo, "dset_cate_tab_id");
		if(!StringUtil.isEmpty(cateTabId)) tabBaseParams = new String[]{RbsProperties.getProperty("Globals.item.tab.search.pre.flag") + cateTabId};
		
		String listName = "list.do";
		String viewName = "view.do";
		String inputName = "input.do";
		String inputProcName = "inputProc.do";
		String deleteProcName = "deleteProc.do";
		String deleteListName = "deleteList.do";
		String imageName = "image.do";
		String movieName = "movie.do";
		String downloadName = "download.do";
		
		if(useSsl){
			inputProcName = PathUtil.getSslPagePath(inputProcName);
		}
		
		PathUtil.fn_setCommonPath(queryString, baseParams, tabBaseParams, searchParams, null, null, pageName, idxName, listName, viewName, null, null, null, null, inputName, inputProcName, deleteProcName, deleteListName, imageName, movieName, downloadName);
	}

	/**
	 * 휴지통 경로
	 * @param attrVO
	 */
	public void fn_setDeleteListPath(ModuleAttrVO attrVO) {

		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		DataForm queryString = attrVO.getQueryString();
		
		String pageName = JSONObjectUtil.getString(settingInfo, "page_name");			// 목록 페이징  key

		// 항목 설정 정보
		String listSearchId = "list_search";
		String[] searchParams = StringUtil.concatenateStringArrays(PathUtil.getSearchParams(JSONObjectUtil.getJSONObject(itemInfo, listSearchId)), deleteListSearchParams);	// 검색 항목
		PathUtil.fn_setDeleteListPath(queryString, baseParams, searchParams, pageName);
	}
	
	/**
	 * 저장 후 되돌려줄 페이지 속성명
	 * @param settingInfo
	 * @return
	 */
	public String fn_dsetInputNpname(JSONObject settingInfo){
		String dsetInputNpname = JSONObjectUtil.getString(settingInfo, "dset_input_npname");
		if(StringUtil.isEmpty(dsetInputNpname)){
			dsetInputNpname = "LIST";
			return dsetInputNpname;
		}
		
		return dsetInputNpname;
	}
		
	@RequestMapping(value="/getCenters.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getCenters(@RequestParam(value="name") String name) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		name = "1";
		List<Map> centers = agreementService.getCenters(name);
		String jsonString = mapper.writeValueAsString(centers);
		return jsonString;
	}
	
	@RequestMapping(value="/request.do", method=RequestMethod.POST)
	@ResponseBody
	public String requestAgreement(String bpl_no, int prvtcntr_no, int status, HttpServletRequest request, HttpServletResponse res) throws JsonProcessingException {
		Map param = new HashMap();
		param.put("bpl_no", bpl_no);
		param.put("prvtcntr_no", prvtcntr_no);
		param.put("status", status);
		param.put("regi_ip", request.getRemoteAddr());
		agreementService.requestAgreement(param);
		ObjectMapper mapper = new ObjectMapper();
		Map result = new HashMap();
		result.put("status", "success");
		String jsonString = mapper.writeValueAsString(result);
		return jsonString;
	}
	
	@RequestMapping(value="/upload.do", method=RequestMethod.POST)
	public String uploadFile(RegisterFormVO registerFormVo, @RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse res) throws IOException {
		int result = agreementService.registerAgreement(registerFormVo);
		result = agreementService.uploadFile(file, registerFormVo.getAgremIdx(), req.getRemoteAddr());
		if(result > 0) {
			res.setStatus(200);
			return getViewPath("/list"); 
		} else {
			res.setStatus(500);
			PrintWriter pw = res.getWriter();
			pw.write("file uploading is failed");
			pw.close();
		}
		return null;
	}
	
	@RequestMapping(value="/register.do")
	public String register(HttpServletRequest req, Model model) {
		String data = (String)req.getParameter("a");
		String memberIdx = null;
		Map param = new HashMap();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if(loginVO != null) {
			memberIdx = loginVO.getMemberIdx();
			param.put("member_idx", memberIdx);
			Map register = agreementService.getRegister(param);
			String agremNo = agreementService.getAgremNo(param);
			model.addAttribute("agrem_no", agremNo);
			model.addAttribute("register", register);
		} else {
			return null;
		}
		if(data != null) {
			model.addAttribute("data", data);
		}
		return getViewPath("/register");
	}
	
	@RequestMapping(value="/updateStatus.do")
	public void updateStatus(AgreementConfirmVO confirmVO, HttpServletRequest request, 
			@RequestParam Map<String, Object> param, HttpServletResponse response) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		param.put("loginvo", loginVO);
		param.put("agrem_idx", param.get("agremIdx"));
		param.put("status", confirmVO.getStatus());
		param.put("ip", request.getRemoteAddr());
		int result = agreementService.confirmAgreement(param);
		if(result > 0) {
			response.setStatus(200);
		} else {
			response.setStatus(500);
		}
	}
	
	@RequestMapping(value="/getBsk", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getbsk(@RequestParam String bpl_no) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Map centers = agreementService.getBsk(bpl_no);
		String jsonString = mapper.writeValueAsString(centers);
		return jsonString;
	}
	
	@RequestMapping(value="/sojt.do")
	public String sojt() {
		return getViewPath("/apply_form");
	}
	
}
