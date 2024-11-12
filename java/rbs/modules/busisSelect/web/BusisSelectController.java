package rbs.modules.busisSelect.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
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
import rbs.modules.busisSelect.service.BusisSelectService;
import rbs.modules.recommend.RecommendDummyService;
import rbs.modules.simpleSign.serivce.SignatureDocumentService;

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
import com.woowonsoft.egovframework.util.ModuleUtil;
import com.woowonsoft.egovframework.util.PathUtil;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;
import com.woowonsoft.egovframework.web.ModuleController;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
@ModuleMapping(moduleId="busisSelect")
@RequestMapping({"/{siteId}/busisSelect", "/{admSiteId}/menuContents/{usrSiteId}/busisSelect"})
public class BusisSelectController extends ModuleController {
	
	@Resource(name = "busisSelectService")
	private BusisSelectService busisSelectService;

	@Resource(name = "signatureService")
	private SignatureDocumentService signatureService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
	@Resource(name = "jsonView")
	View jsonView;
	
	@Autowired
	RecommendDummyService recommendService;
	
    static final String SIMPLESIGN_API_USER_ID = "admin";
    static final String SIMPLESIGN_API_USER_PASSWORD = "admin12#$";
	
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
	 *
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value = {"/list.do"})
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

		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		
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
			if(loginInfo != null) {
				String insttIdx = loginVO.getInsttIdx();
				if(insttIdx != null) {
					param.put("insttIdx",insttIdx);
				}
				model.addAttribute("login", true);
			}else {
				model.addAttribute("login", false);
			}
			
			totalCount = busisSelectService.getTotalCount(param);
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
	    		list = busisSelectService.getList(param);
			}
	    	
		}

    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);
    	model.addAttribute("list", list);
//    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);
//		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("siteId", siteId);
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
	
	/**
	 * 상세조회
	 * 
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(method=RequestMethod.POST,value = "/view.do")
	public String view(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String insttIdx = loginVO.getInsttIdx();

		// 1. 필수 parameter 검사
		String keyIdx = request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name"));
		
		if(StringUtil.isEmpty(keyIdx)) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		String idxColumnName = JSONObjectUtil.getString(settingInfo, "idx_column");

		
		// 2. DB
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();

		searchList.add(new DTForm(idxColumnName, keyIdx));
		
		String bplNo = request.getParameter("bplNo");
		String sptjIdx = request.getParameter("sptjIdx");
		
		param.put("searchList", searchList);		
		param.put("sptdgnsIdx", keyIdx);
		
		param.put("sptjIdx", sptjIdx);
		param.put("bplNo", bplNo);
		param.put("insttIdx", insttIdx);
		
		// 상세정보
		DataMap publishList = null;
		DataMap sptjList = null;
		DataMap bplList = null;
		DataMap trList = null;
		DataMap aLSclas = null;
		DataMap pLSclas = null;
		
		publishList = busisSelectService.getPublished(param);
		sptjList = busisSelectService.getSptj(param);
		bplList = busisSelectService.getBpl(param);
		trList = busisSelectService.getTr(param);
		aLSclas = busisSelectService.getALSclas(param);
		pLSclas = busisSelectService.getPLSclas(param);

		// 쉼표(,)로 데이터 분리
		String ADMSFM_NM = sptjList.get("ADMSFM_NM").toString();
		List<String> sptjNmList = Arrays.asList(ADMSFM_NM.split(","));

		String ADMSFM_IDX = sptjList.get("ADMSFM_IDX").toString();
		List<String> sptjIdxList = Arrays.asList(ADMSFM_IDX.split(","));
		
		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "view";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		
		// 3.2 속성 setting
    	model.addAttribute("idx", keyIdx);
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);																// 페이지구분
		model.addAttribute("publishList", publishList);
		model.addAttribute("sptjList", sptjList);
		model.addAttribute("sptjNmList", sptjNmList);
		model.addAttribute("sptjIdxList", sptjIdxList);
		model.addAttribute("bplList", bplList);
		model.addAttribute("trList", trList);
		model.addAttribute("aLSclas", aLSclas);
		model.addAttribute("pLSclas", pLSclas);

		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	/**
	 * 기업목록조회
	 *
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value = {"/bskList.do"})
	public String bskList(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model, @PathVariable("siteId") String siteId) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		int fnIdx = attrVO.getFnIdx();
		
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

		// 항목설정
//		String submitType = "list";
//		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		
		// 항목설정으로 검색조건 setting
		String listSearchId = "list_search";		// 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		// 소속기관 검색
		String searchKeyFlag = RbsProperties.getProperty("Globals.item.search.pre.flag");
		String insttIdxName = "insttIdx";
		String insttIdx1 = queryString.getString(searchKeyFlag + insttIdxName);
		if(!StringUtil.isEmpty(insttIdx1)) {
			param.put("isInsttIdx", insttIdx1);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		// 업종 검색
		String indutyCodeItemName = "indutyCd";
		String isIndutyCd = queryString.getString(searchKeyFlag + indutyCodeItemName);
		if(!StringUtil.isEmpty(isIndutyCd)) {
			param.put("isIndutyCd",  isIndutyCd);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		param.put("searchList", searchList);
		
		System.out.println("#ehoh > searchList : "+searchList);
		System.out.println("#ehoh > listSearch : "+listSearch);
		
		// 소속기관 목록
		List<?> insttList = null;
		insttList = busisSelectService.getInsttList(fnIdx, param);
		model.addAttribute("insttList", insttList);
		
		// 업종
		List<HashMap<String, Object>> indutyCd = busisSelectService.getIndustCd();
		model.addAttribute("indutyCd", indutyCd);
		
		// 2.2 목록수
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		HttpSession session = request.getSession(true);
		LoginVO loginInfo = (LoginVO) session.getAttribute("loginVO");
		
		String insttIdx = loginVO.getInsttIdx();
		int userTypeIdx = loginVO.getUsertypeIdx();
		
		System.out.println("insttIdx : "+insttIdx);
		
		if(siteId.equals("dct")) {
			if(loginInfo != null) {
				if(insttIdx != null) {
					param.put("insttIdx",insttIdx);
				}
				model.addAttribute("login", true);
			}else {
				model.addAttribute("login", false);
			}
			
			if(attrVO.getQueryString().size() > 2) {
				totalCount = busisSelectService.getBskCount(param);
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
		    		list = busisSelectService.getBskList(param);
				}
			}
		}
		
    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);
    	model.addAttribute("list", list);
		model.addAttribute("siteId", siteId);
		model.addAttribute("insttIdx", insttIdx);
		model.addAttribute("userTypeIdx", userTypeIdx);
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	model.addAttribute("URL_BSK", request.getAttribute("URL_BSK"));
    	
		return getViewPath("/bskList");
	}
	
	/**
	 * 서식그룹 목록조회
	 *
	 */
	@Auth(role = Role.R)
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = {"/supportList.do"})
	public String supportList(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request , ModelMap model
												, @PathVariable("siteId") String siteId, HttpSession session) throws Exception {
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

		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "support_items");
		
		// 항목설정으로 검색조건 setting
		String listSearchId = "support_list_search";		// 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		
		param.put("searchList", searchList);
		
		// 2.2 목록수
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		request.getSession(true);
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
			
				totalCount = busisSelectService.getSupportCount(param);
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
					list = busisSelectService.getSupportList(param);
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
		model.addAttribute("siteId", siteId);
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/supportList");
	}
	
	/**
	 * 방문기업관리 문서 등록
	 * 
	 */
	@Auth(role = Role.R)
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/input.do")
	public ModelAndView input(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		ModelAndView mav = new ModelAndView();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String insttIdx = loginVO.getInsttIdx();
		
		Map<String, Object> param = new HashMap<String, Object>();
		DataMap sptjList = null;
		DataMap bplList = null;
		DataMap trList = null;
		DataMap aLSclas = null;
		DataMap pLSclas = null;
		DataMap publishList = null;
		
		String sptdgnsIdx = request.getParameter("idx");
		String sptjIdx = request.getParameter("sptjIdx");
		String bplNo = request.getParameter("bplNo");
		param.put("sptdgnsIdx", sptdgnsIdx);
		param.put("sptjIdx", sptjIdx);
		param.put("bplNo", bplNo);
		param.put("insttIdx", insttIdx);
		
		mav.addObject("sptdgnsIdx", sptdgnsIdx);
		mav.addObject("sptjIdx", sptjIdx);
		mav.addObject("bplNo", bplNo);
		
		// 1. 속성 setting
		// 1.1 항목설정
		String submitType = "write";
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		
		sptjList = busisSelectService.getSptj(param);
		bplList = busisSelectService.getBpl(param);
		trList = busisSelectService.getTr(param);
		aLSclas = busisSelectService.getALSclas(param);
		pLSclas = busisSelectService.getPLSclas(param);
		
		publishList = busisSelectService.getPublished(param);
		
		// 전자서식 발행여부
		if(publishList != null && publishList.get("ELCTRN_FORMAT_PBLICTE_ID") != null) {
			String reqSetKeyList = publishList.get("ELCTRN_FORMAT_PBLICTE_ID").toString();
			
			String publishedYn = "Y";
			mav.addObject("publishedYn", publishedYn);
			mav.addObject("reqSetKeyList", reqSetKeyList);

			System.out.println("#ehoh > reqSetKeyList : "+reqSetKeyList);
		}

		String sptjName = sptjList.get("ADMSFM_NM").toString();
		List<String> sptjNmList = Arrays.asList(sptjName.split(","));

		String sptjIdx_ = sptjList.get("ADMSFM_IDX").toString();
		List<String> sptjIdxList = Arrays.asList(sptjIdx_.split(","));

		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));
		model.addAttribute("submitType", submitType);
		mav.addObject("sptjList", sptjList);
		mav.addObject("sptjNmList", sptjNmList);
		mav.addObject("sptjIdxList", sptjIdxList);
		mav.addObject("bplList", bplList);
		mav.addObject("trList", trList);
		mav.addObject("aLSclas", aLSclas);
		mav.addObject("pLSclas", pLSclas);
		mav.setViewName(getViewPath("/input"));

    	// 2. 기본경로
    	fn_setCommonPath(attrVO);
		
		return mav;
	}
	
	/**
	 * 방문기업관리 문서  등록처리
	 * 
	 * 
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.POST, value = "/inputProc.do")
	public ModelAndView inputProc(@RequestParam Map<String, Object> formData, HttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(formData);
		
		String regiIp = request.getRemoteAddr();
		String sptjIdx = formData.get("sptjIdx").toString();
		String bplNo = formData.get("bplNo").toString();
		
		// 방문기업관리 문서  전자문서 발행
		Map<String, Object> param = new HashMap<>();
		param.put("sptjIdx", sptjIdx);
		param.put("bplNo", bplNo);
		
		List<String> reqSetKeyList = null;
		
		boolean done = formData.get("action").toString().equals("done");
		
		if(!done) {
			// 01. 토큰발급
			String token = signatureService.getToken(SIMPLESIGN_API_USER_ID, SIMPLESIGN_API_USER_PASSWORD);
			
			// 02. 대량 발행
			reqSetKeyList = signatureService.publishSignature(param, token);
			
			StringBuilder resultBuilder = new StringBuilder();
			for(String item : reqSetKeyList) {
				resultBuilder.append(item).append(", ");
			}
			
			if(resultBuilder.length() >0) {
				resultBuilder.setLength(resultBuilder.length() -2);
			}
			String reqSetKeyValue = resultBuilder.toString();
			formData.put("reqSetKeyList", reqSetKeyValue);
			
		}
		
		// 방문기업관리 문서 등록
        String publishedYn = (reqSetKeyList != null) ? "Y" : "N";
        formData.put("publishedYn", publishedYn);
		formData.put("regiIp", regiIp);
        
		// return sptjIdx
		int result = busisSelectService.setForm(formData);
		
		if(result > 0) {
			String sptdgnsIdx = formData.get("sptdgnsIdx").toString();
			mav.addObject("sptdgnsIdx", sptdgnsIdx);

		}
		
        mav.setView(jsonView);

		return mav;
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
		list = busisSelectService.getList(param);
    	
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
		
		String supportUrl = "supportList.do";	
		String bskUrl = "bskList.do";
		
		if(!StringUtil.isEmpty(baseQueryString)) {
			supportUrl += baseQueryString;
			bskUrl += baseQueryString;
		}
		request.setAttribute("URL_SUPPORT", supportUrl);
		request.setAttribute("URL_BSK", bskUrl);

	}


	/**
	 * 관리권한 체크
	 * @param settingInfo
	 * @param fnIdx
	 * @param keyIdx
	 * @param memIdx
	 * @param useReply
	 * @param pwd
	 * @return
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
			modiCnt = busisSelectService.getAuthCount(param);
			
			return (modiCnt > 0);
		}
		
		return true;
	}
}