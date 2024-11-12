package rbs.modules.supportForm.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import rbs.egovframework.LoginVO;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.modules.supportForm.service.SupportFormService;

import com.woowonsoft.egovframework.annotation.ModuleAttr;
import com.woowonsoft.egovframework.annotation.ModuleAuth;
import com.woowonsoft.egovframework.annotation.ModuleMapping;
import com.woowonsoft.egovframework.form.DTForm;
import com.woowonsoft.egovframework.form.DataForm;
import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.prop.RbsProperties;
import com.woowonsoft.egovframework.resource.RbsMessageSource;
import com.woowonsoft.egovframework.tag.ui.RbsPaginationInfo;
import com.woowonsoft.egovframework.util.AuthHelper;
import com.woowonsoft.egovframework.util.CodeHelper;
import com.woowonsoft.egovframework.util.JSONObjectUtil;
import com.woowonsoft.egovframework.util.MessageUtil;
import com.woowonsoft.egovframework.util.ModuleUtil;
import com.woowonsoft.egovframework.util.PathUtil;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;
import com.woowonsoft.egovframework.web.ModuleController;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
@ModuleMapping(moduleId="supportForm")
@RequestMapping({"/{siteId}/supportForm", "/{admSiteId}/menuContents/{usrSiteId}/supportForm"})
public class SupportFormController extends ModuleController {
	
	@Resource(name = "supportFormService")
	private SupportFormService supportFormService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
	@Resource(name = "jsonView")
	View jsonView;
	
	
	/**
	 * 코드
	 * 
	 */
	public HashMap<String, Object> getOptionHashMap(String submitType, JSONObject itemInfo) {
		return getOptionHashMap(submitType, itemInfo, null);
	}
	
	/**
	 * 코드
	 * 
	 */
	public HashMap<String, Object> getOptionHashMap(String submitType, JSONObject itemInfo, HashMap<String, Object> addOptionHashMap) {
		// 코드
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		HashMap<String, Object> optnHashMap = (addOptionHashMap != null)?CodeHelper.getItemOptnHashMap(items, itemOrder, addOptionHashMap):CodeHelper.getItemOptnHashMap(items, itemOrder);
		return  optnHashMap;
	}
	
	/**
	 * 서식그룹 목록조회
	 *
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value = "/list.do")
	public String list(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model, @PathVariable("siteId") String siteId) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		
		// 1. 페이지정보 setting
		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		int usePaging = JSONObjectUtil.getInt(settingInfo, "use_paging");
		if(usePaging == 1) {
			int listUnit = ModuleUtil.getSettingValue(isAdmMode, "dset_list_count", crtMenu, settingInfo, propertiesService.getInt("DEFAULT_LIST_UNIT"));	// 페이지당 목록 수
			int listMaxUnit = listUnit * propertiesService.getInt("DEFAULT_LIST_MAX_CNT");											// 최대 페이지당 목록 수 
			int listUnitStep = listUnit;										// 페이지당 목록 수 증가값
			
			int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
			if(pageUnit == 0) pageUnit = listUnit;//JSONObjectUtil.getInt(settingInfo, "dset_list_count", propertiesService.getInt("PAGE_UNIT"));	// 페이지당 목록 수
			int pageSize = ModuleUtil.getSettingValue(isAdmMode, "dset_list_block", crtMenu, settingInfo, propertiesService.getInt("PAGE_SIZE"));	
			
			String pageName = JSONObjectUtil.getString(settingInfo, "page_name");
			int page = StringUtil.getInt(request.getParameter(pageName), 1);				// 현재 페이지 index
	
			paginationInfo.setUnitBeginCount(listUnit);
			paginationInfo.setUnitEndCount(listMaxUnit);
			paginationInfo.setUnitStep(listUnitStep);
			paginationInfo.setCurrentPageNo(page);
			paginationInfo.setRecordCountPerPage(pageUnit);
			paginationInfo.setPageSize(pageSize);
		}
		
		// 2. DB
		int totalCount = 0;
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건

		// 항목설정으로 검색조건 setting
		String listSearchId = "list_search";		// 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		param.put("searchList", searchList);
		
		// 2.2 목록수
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		HttpSession session = request.getSession(true);
		LoginVO loginInfo = (LoginVO) session.getAttribute("loginVO");
		
		if(siteId.equals("dct")) {
			if(loginInfo == null) {
				request.setAttribute("msg", "로그인이 필요합니다.");
				request.setAttribute("url", "/dct/login/login.do?mId=3");
				model.addAttribute("login", false);
				return getViewPath("/alert");
				
			}else if(loginVO.getUsertypeIdx() == 40 || loginVO.getUsertypeIdx() == 30 || loginVO.getUsertypeIdx() == 55) { 
				String insttIdx = loginVO.getInsttIdx();
				if(insttIdx != null) {
					param.put("insttIdx",insttIdx);
				}
				model.addAttribute("login", true);
			
				totalCount = supportFormService.getTotalCount(param);
				paginationInfo.setTotalRecordCount(totalCount);
				
				if(totalCount > 0) {
					if(usePaging == 1) {
						param.put("firstIndex", paginationInfo.getFirstRecordIndex());
						param.put("lastIndex", paginationInfo.getLastRecordIndex());
						param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
					}
					
					// 정렬컬럼 setting
					param.put("dsetOrderField", JSONObjectUtil.getString(settingInfo, "dset_order_field"));
					
					// 2.3 목록
					list = supportFormService.getList(param);
				}
				
			}else {
				request.setAttribute("msg", "접근 권한이 없습니다.");
				request.setAttribute("url", "/web/main/main.do?mId=1");
				return getViewPath("/alert");
			}
			
		}

		// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);
    	model.addAttribute("list", list);
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
	
	/**
	 * 서식그룹 상세조회
	 * 
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(value = "/view.do")
	public String view(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		// 1. 필수 parameter 검사
		String keyIdx = request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name"));
		if(StringUtil.isEmpty(keyIdx)) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		String idxColumnName = JSONObjectUtil.getString(settingInfo, "idx_column");

		
		// 2. DB
		DataMap dt = null;
		List<?> it = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();

		searchList.add(new DTForm(idxColumnName, keyIdx));
		
//		String admsfmIdx = request.getParameter("admsfmIdx");
		param.put("searchList", searchList);		
		param.put("sptjIdx", keyIdx);
		
		// 상세정보
		dt = supportFormService.getView(param);
		it = supportFormService.getFormIdx(param);
		
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}

		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "view";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		// 3.2 속성 setting
		model.addAttribute("dt", dt);
		model.addAttribute("it", it);
    	model.addAttribute("idx", keyIdx);
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);																// 페이지구분
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	/**
	 * 서식그룹 등록 페이지
	 * 
	 */
	@Auth(role = Role.R)
	@RequestMapping(method= {RequestMethod.GET, RequestMethod.POST}, value = "/input.do")
	public ModelAndView input(@RequestParam Map<String, Object> formData
									, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		ModelAndView mav = new ModelAndView();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		List<Object> form = null;
		DataMap support = null;
		List<Map<String, Object>> formIdx = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		String idx = request.getParameter("idx");
		formData.put("sptjIdx", idx);

		param.put("searchList", searchList);
		param.put("sptjIdx", idx);
		
		//기등록된 방문기업서식 목록
		form = supportFormService.getFormList(param);
//		formCount = supportFormService.getFormCount(param);
		
		//임시저장 데이터
		support = supportFormService.getView(param);
		formIdx = supportFormService.getFormIdx(param);
		
		/**
		 * select box 데이터 put
		 */
		// 사업구분
		Map<Integer, Object> prtbizIdx = new HashMap<>();
		prtbizIdx.put(1, "S-OJT");
		prtbizIdx.put(2, "일학습병행");
		prtbizIdx.put(4, "사업주훈련");
		prtbizIdx.put(6, "컨소시엄");
		prtbizIdx.put(7, "대부사업");
		
		// 업무구분
		Map<Integer, Object> jobType = new HashMap<>();
		jobType.put(0, "모니터링");
		jobType.put(1, "업무수행");
		jobType.put(2, "동의(서약)서");
		
		// 서명대상자
		Map<Integer, Object> sgntr = new HashMap<>();
		sgntr.put(0, "기업");
		sgntr.put(1, "주치의(공단지원)");
		sgntr.put(2, "주치의/외부전문가");
		sgntr.put(3, "외부전문가");
		sgntr.put(4, "훈련기관");
		sgntr.put(5, "불특정");
		
		if(idx != null) {
	    	mav.addObject("support",support);
	    	mav.addObject("formIdx",formIdx);
		}
    	
		// 1. 속성 setting
		// 1.1 항목설정
		String submitType = "write";
		model.addAttribute("optnHashMap", getOptionHashMap(submitType, itemInfo));
		model.addAttribute("submitType", submitType);
		
		mav.setViewName(getViewPath("/input"));
		model.addAttribute("form", form);
    	model.addAttribute("prtbizIdx",prtbizIdx);
    	model.addAttribute("jobType",jobType);
    	model.addAttribute("sgntr",sgntr);
//		model.addAttribute("formCount", formCount);
		mav.addObject("idx", idx);
		
		
    	// 2. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return mav;
	}
	
	/**
	 *  방문기업서식 검색
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/formList.do", method = RequestMethod.POST)
	public ModelAndView getFormList(HttpServletRequest request, @RequestParam Map<String, Object> formData) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		List<?> list = null;
		list = supportFormService.getFormList(formData);
		mav.addObject("formList", list);
		return mav;
	}
	
	/**
	 * 서식그룹 등록처리
	 * 
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.POST, value = "/inputProc.do")
	public ModelAndView inputProc(@RequestParam Map<String, Object> formData, HttpServletRequest request) throws IOException {
		Map<String, Object> dataMap = new HashMap<>();
		ModelAndView mav = new ModelAndView();
		mav.addObject(formData);
		mav.setViewName(getViewPath("/list"));
		
		String sptjIdx = request.getParameter("sptjIdx");
		String regiIp = request.getRemoteAddr();
		formData.put("regiIp", regiIp);
		
		// 임시저장 & 등록시 값 주입
		if(formData.get("action").equals("temp")){
			formData.put("tmprSaveYn", "Y");
			formData.put("dcsnYn", "N");
		}else {
			formData.put("dcsnYn", "Y");
			formData.put("tmprSaveYn", "N");
		}

		
		// 방문기업서식 idx 저장
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if(paramName.startsWith("hidden-value")) {
				String paramValue = request.getParameter(paramName);
				dataMap.put(paramName, paramValue);
			}
		}
		
		// 서식그룹 등록
		supportFormService.setFormAndIdx(formData, sptjIdx, dataMap);

		return mav;
	}
	
	/**
	 * 서식그룹 삭제
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/delete.do")
	public String delete(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		JSONObject settingInfo = attrVO.getSettingInfo();
		String ajaxPName = attrVO.getAjaxPName();
		
		
		String sptjIdx = request.getParameter("idx");
		String regiIp = request.getRemoteAddr();
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sptjIdx", sptjIdx);
		param.put("lastModiIp", regiIp);
		
		int result = supportFormService.delete(param);
		
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_" + inputNpname)));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.delete"), "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
			
			return getViewPath("/list");
    	} 
			// 2.3 저장 실패
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.delete")));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
	}
	
	
	/**
	 * 엑셀 다운로드
	 * 
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(value = "/excel.do")
	public String excel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		// 2. DB
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();

		// 2.1 검색조건
		// 항목설정으로 검색조건 setting
		String listSearchId = "list_search";		// 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
//		// 2.3 목록
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		HttpSession session = request.getSession(true);
		LoginVO loginInfo = (LoginVO) session.getAttribute("loginVO");

		if(loginInfo != null) {
			String insttIdx = loginVO.getInsttIdx();
			if(insttIdx != null) {
				param.put("insttIdx",insttIdx);
			}
			model.addAttribute("login", true);
		}else {
			model.addAttribute("login", false);
		}
		
		param.put("searchList", searchList);
		param.put("dsetOrderField", JSONObjectUtil.getString(settingInfo, "dset_order_field"));
		
		// 2.3 목록
		list = supportFormService.getList(param);
    	
    	// 항목 설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		HashMap<String, Object> optnHashMap = CodeHelper.getItemOptnHashMap(items, itemOrder);
    	
    	// 3. 속성 setting
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("searchOptnHashMap", CodeHelper.getItemOptnSearchHashMap(listSearch));	// 항목 코드
		model.addAttribute("optnHashMap", optnHashMap);
    	
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
		
		return getViewPath("/excel");
	}
	
	/**
	 * 기본경로
	 * 
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
		
		// 추가경로
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String baseQueryString = StringUtil.getString(request.getAttribute("baseQueryString"));

		
		String clipReportUrl = "clipReport.do";				// 클립리포트
		String excelUrl = "excel.do";
		String insertUrl = "insert.do";
		
		if(!StringUtil.isEmpty(baseQueryString)) {
			clipReportUrl += baseQueryString;
			excelUrl += baseQueryString;
			insertUrl += baseQueryString;
		}
		request.setAttribute("URL_CLIPREPORT", clipReportUrl);
		request.setAttribute("URL_EXCEL", excelUrl);
		request.setAttribute("URL_INSERT", insertUrl);
		
	}
	
	/**
	 * 저장 후 되돌려줄 페이지 속성명
	 * 
	 */
	public String fn_dsetInputNpname(JSONObject settingInfo){
		String dsetInputNpname = JSONObjectUtil.getString(settingInfo, "dset_input_npname");
		if(StringUtil.isEmpty(dsetInputNpname)){
			dsetInputNpname = "LIST";
			return dsetInputNpname;
		}
		
		return dsetInputNpname;
	}

	
	/**
	 * 관리권한 체크
	 * 
	 */
	public boolean isMngProcAuth(JSONObject settingInfo, String keyIdx) {
		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");
		
		int modiCnt = 0;
		// 권한 체크
		Boolean isMngAuth = AuthHelper.isAuthenticated("MNG");		// 전체관리
		
		if(!isMngAuth){
			Map<String, Object> param = new HashMap<String, Object>();
			List<DTForm> searchList = new ArrayList<DTForm>();
			
			searchList.add(new DTForm("A." + columnName, keyIdx));
			
			// 전체관리권한 없는 경우 : 자신글만 수정
			boolean isLogin = UserDetailsHelper.isLogin();		// 로그인한 경우
			if(isLogin){
				// 로그인한 경우
				String memberIdx = null;
				
				LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
				if(loginVO != null) {
					memberIdx = loginVO.getMemberIdx();
				}
				
				if(StringUtil.isEmpty(memberIdx)) return false;
				param.put("AUTH_MEMBER_IDX", memberIdx);
			} else {
				// 본인인증 or 로그인 안 한 경우
				return false;
			}

			param.put("searchList", searchList);
//			modiCnt = supportFormService.getAuthCount(param);
			
			return (modiCnt > 0);
		}
		
		return true;
	}
}