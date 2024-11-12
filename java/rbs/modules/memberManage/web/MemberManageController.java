package rbs.modules.memberManage.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.woowonsoft.egovframework.annotation.ModuleAttr;
import com.woowonsoft.egovframework.annotation.ModuleAuth;
import com.woowonsoft.egovframework.annotation.ModuleMapping;
import com.woowonsoft.egovframework.annotation.ParamMap;
import com.woowonsoft.egovframework.form.DTForm;
import com.woowonsoft.egovframework.form.DataForm;
import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.form.ParamForm;
import com.woowonsoft.egovframework.prop.RbsProperties;
import com.woowonsoft.egovframework.resource.RbsMessageSource;
import com.woowonsoft.egovframework.tag.ui.RbsPaginationInfo;
import com.woowonsoft.egovframework.util.AuthHelper;
import com.woowonsoft.egovframework.util.CodeHelper;
import com.woowonsoft.egovframework.util.FormValidatorUtil;
import com.woowonsoft.egovframework.util.JSONObjectUtil;
import com.woowonsoft.egovframework.util.MessageUtil;
import com.woowonsoft.egovframework.util.ModuleUtil;
import com.woowonsoft.egovframework.util.PathUtil;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;
import com.woowonsoft.egovframework.util.WebsiteDetailsHelper;
import com.woowonsoft.egovframework.web.ModuleController;

import egovframework.rte.fdl.property.EgovPropertyService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import rbs.egovframework.LoginVO;
import rbs.egovframework.WebsiteVO;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.modules.authManage.service.AuthGroupManageService;
import rbs.modules.authManage.service.AuthManageService;
import rbs.modules.member.service.MemberService;
import rbs.modules.memberManage.service.MemberManageService;

/**
 * 샘플모듈<br/>
 * : 통합관리시스템 > 메뉴콘텐츠관리, 통합관리시스템 > 기능등록관리, 사용자 사이트 에서 사용
 * @author user
 *
 */
@Controller
@ModuleMapping(moduleId="memberManage", useSDesign=true)
@RequestMapping({"/{siteId}/memberManage", "/{admSiteId}/menuContents/{usrSiteId}/memberManage"})
public class MemberManageController extends ModuleController {
	
	@Resource(name = "memberManageService")
	private MemberManageService memberManageService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
	@Resource(name = "memberService")
	private MemberService memberService;
	
	@Resource(name = "authGroupManageService")
	private AuthGroupManageService authGroupManageService;
	
	@Resource(name = "authManageService")
	private AuthManageService authManageService;
	
	@Resource(name = "jsonView")
	View jsonView;
	
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
	@RequestMapping(value = "/list.do")
	public String list(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		DataForm queryString = attrVO.getQueryString();
		
		String listSearchId = "list_search";		// 검색설정
    	
		// 1. 페이지정보 setting
		int listUnit = JSONObjectUtil.getInt(settingInfo, "dset_list_count", propertiesService.getInt("DEFAULT_LIST_UNIT"));	// 페이지당 목록 수
		int listMaxUnit = listUnit * propertiesService.getInt("DEFAULT_LIST_MAX_CNT");											// 최대 페이지당 목록 수 
		int listUnitStep = listUnit;																							// 페이지당 목록 수 증가값
		
		int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
		if(pageUnit == 0) pageUnit = listUnit;																					// 페이지당 목록 수
		int pageSize = JSONObjectUtil.getInt(settingInfo, "dset_list_block", propertiesService.getInt("PAGE_SIZE"));			// 페이징 크기
		int page = StringUtil.getInt(request.getParameter("page"), 1);															// 현재 페이지 index

		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		paginationInfo.setUnitBeginCount(listUnit);
		paginationInfo.setUnitEndCount(listMaxUnit);
		paginationInfo.setUnitStep(listUnitStep);
		paginationInfo.setCurrentPageNo(page);
		paginationInfo.setRecordCountPerPage(pageUnit);
		paginationInfo.setPageSize(pageSize);
		
		// 2. DB
		int totalCount = 0;
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건
		
		// 항목설정으로 검색조건 setting
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		// 사용자 그룹 검색
		String searchKeyFlag = RbsProperties.getProperty("Globals.item.search.pre.flag");
		String mbrGrpItemName = "mbrGrp";
		String mbrGrpCd = queryString.getString(searchKeyFlag + mbrGrpItemName);
		if(!StringUtil.isEmpty(mbrGrpCd)) {
			param.put("mbrGrpCd", mbrGrpCd);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		param.put("searchList", searchList);

		// 2.2 목록수
    	totalCount = memberManageService.getTotalCount(param);
		paginationInfo.setTotalRecordCount(totalCount);
    	
    	if(totalCount > 0) {
    		param.put("firstIndex", paginationInfo.getFirstRecordIndex());
    		param.put("lastIndex", paginationInfo.getLastRecordIndex());
    		param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
    		
    		// 2.3 목록
    		list = memberManageService.getList(param);
    	}
    	
    	// 검색을 위한 지부지사 목록 가져오기
		//List<Object> insttList = null;
		//insttList = memberManageService.getOrgInsttList();
		//model.addAttribute("insttList", insttList);
    	
    	model.addAttribute("paginationInfo", paginationInfo);					// 페이징정보
    	model.addAttribute("list", list);										// 목록
    	HashMap<String, Object> searchOptnHashMap = CodeHelper.getItemOptnSearchHashMap(listSearch);	// 검색항목코드
		// 사용자그룹 추가
		JSONArray searchOrder = JSONObjectUtil.getJSONArray(listSearch, "searchAddOptn_order");
		JSONObject keyInfo = JSONObjectUtil.getJSONObject(listSearch, "key");
		JSONObject keyItems = JSONObjectUtil.getJSONObject(keyInfo, "items");
		HashMap<String, Object> searchAddOptnHashMap = CodeHelper.getItemOptnHashMap(true, keyItems, searchOrder);
		if(searchAddOptnHashMap != null) {
			if(searchOptnHashMap == null) searchOptnHashMap = new HashMap<String, Object>();
			searchOptnHashMap.putAll(searchAddOptnHashMap);
		}
		model.addAttribute("searchOptnHashMap", searchOptnHashMap);	// 검색항목코드
    	// 3. 경로 setting
    	// 3.1 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
	
	/**
	 * 기업 소속변경목록 조회
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value = "/corpList.do")
	public String corpList(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		DataForm queryString = attrVO.getQueryString();
		
		String listSearchId = "corpList_search";		// 검색설정
    	
		// 1. 페이지정보 setting
		int listUnit = JSONObjectUtil.getInt(settingInfo, "dset_list_count", propertiesService.getInt("DEFAULT_LIST_UNIT"));	// 페이지당 목록 수
		int listMaxUnit = listUnit * propertiesService.getInt("DEFAULT_LIST_MAX_CNT");											// 최대 페이지당 목록 수 
		int listUnitStep = listUnit;																							// 페이지당 목록 수 증가값
		
		int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
		if(pageUnit == 0) pageUnit = listUnit;																					// 페이지당 목록 수
		int pageSize = JSONObjectUtil.getInt(settingInfo, "dset_list_block", propertiesService.getInt("PAGE_SIZE"));			// 페이징 크기
		int page = StringUtil.getInt(request.getParameter("page"), 1);															// 현재 페이지 index

		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		paginationInfo.setUnitBeginCount(listUnit);
		paginationInfo.setUnitEndCount(listMaxUnit);
		paginationInfo.setUnitStep(listUnitStep);
		paginationInfo.setCurrentPageNo(page);
		paginationInfo.setRecordCountPerPage(pageUnit);
		paginationInfo.setPageSize(pageSize);
		
		// 2. DB
		int totalCount = 0;
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건
		
		// 항목설정으로 검색조건 setting
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		// 사용자 그룹 검색
		String searchKeyFlag = RbsProperties.getProperty("Globals.item.search.pre.flag");
		String mbrGrpItemName = "mbrGrp";
		String mbrGrpCd = queryString.getString(searchKeyFlag + mbrGrpItemName);
		if(!StringUtil.isEmpty(mbrGrpCd)) {
			param.put("mbrGrpCd", mbrGrpCd);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		param.put("searchList", searchList);

		// 2.2 목록수
    	totalCount = memberManageService.getTotalCorpRegiCount(param);
		paginationInfo.setTotalRecordCount(totalCount);
    	
    	if(totalCount > 0) {
    		param.put("firstIndex", paginationInfo.getFirstRecordIndex());
    		param.put("lastIndex", paginationInfo.getLastRecordIndex());
    		param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
    		
    		// 2.3 목록
    		list = memberManageService.getCorpRegiList(param);
    		
    	}
    	
    	// 검색을 위한 지부지사 목록 가져오기
		List<Object> insttList = null;
		insttList = memberManageService.getInsttList();
		model.addAttribute("insttList", insttList);
    	
    	model.addAttribute("paginationInfo", paginationInfo);					// 페이징정보
    	model.addAttribute("list", list);										// 목록
    	HashMap<String, Object> searchOptnHashMap = CodeHelper.getItemOptnSearchHashMap(listSearch);	// 검색항목코드

		// 사용자그룹 추가
		JSONArray searchOrder = JSONObjectUtil.getJSONArray(listSearch, "searchAddOptn_order");
		JSONObject keyInfo = JSONObjectUtil.getJSONObject(listSearch, "key");
		JSONObject keyItems = JSONObjectUtil.getJSONObject(keyInfo, "items");
		HashMap<String, Object> searchAddOptnHashMap = CodeHelper.getItemOptnHashMap(true, keyItems, searchOrder);
		if(searchAddOptnHashMap != null) {
			if(searchOptnHashMap == null) searchOptnHashMap = new HashMap<String, Object>();
			searchOptnHashMap.putAll(searchAddOptnHashMap);
		}
		model.addAttribute("searchOptnHashMap", searchOptnHashMap);	// 검색항목코드
    	
		// 3. 경로 setting
    	// 3.1 기본경로
    	fn_setCommonPath(attrVO);

		return getViewPath("/corpList");
	}
	
	/* 
	 * 기업회원 소속기관 일괄변경
	 */
	@Auth(role = Role.U)
	@RequestMapping(method = RequestMethod.GET, value = "/corpListProc.do")
	public ModelAndView corpListProc(@ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String status = request.getParameter("status");
		String psitnList = request.getParameter("psitnList");
		String bplList = request.getParameter("bplList");
		String memberList = request.getParameter("memberList");
		
		int result = memberManageService.updateCorp(status, psitnList, bplList, memberList, request.getRemoteAddr());

		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		mav.addObject("result", result);
		
		return mav;
	}
	
	/**
	 * 민간센터 권한목록 조회
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value = "/centerList.do")
	public String centerList(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		DataForm queryString = attrVO.getQueryString();
		JSONObject centerItemInfo = JSONObjectUtil.getJSONObject(itemInfo, "center_item_info");

		String listSearchId = "list_search";		// 검색설정
		
 		// 1. 페이지정보 setting
		int listUnit = JSONObjectUtil.getInt(settingInfo, "dset_list_count", propertiesService.getInt("DEFAULT_LIST_UNIT"));	// 페이지당 목록 수
		int listMaxUnit = listUnit * propertiesService.getInt("DEFAULT_LIST_MAX_CNT");											// 최대 페이지당 목록 수 
		int listUnitStep = listUnit;																							// 페이지당 목록 수 증가값
		
		int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
		if(pageUnit == 0) pageUnit = listUnit;																					// 페이지당 목록 수
		int pageSize = JSONObjectUtil.getInt(settingInfo, "dset_list_block", propertiesService.getInt("PAGE_SIZE"));			// 페이징 크기
		int page = StringUtil.getInt(request.getParameter("page"), 1);															// 현재 페이지 index

		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		paginationInfo.setUnitBeginCount(listUnit);
		paginationInfo.setUnitEndCount(listMaxUnit);
		paginationInfo.setUnitStep(listUnitStep);
		paginationInfo.setCurrentPageNo(page);
		paginationInfo.setRecordCountPerPage(pageUnit);
		paginationInfo.setPageSize(pageSize);
		
		// 2. DB
		int totalCount = 0;
		List<?> list = null;
		//권한그룹 리스트
		List<?> authList = null;
		Map<String, Object> param = new HashMap<String, Object>();
		//권한그룹 리스트용 param
		Map<String, Object> authParam = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		
		// 2.1 검색조건
		
		// 항목설정으로 검색조건 setting 시작일&종료일 검색 조건 추가로 기존의 검색조건 setting과 다르게 설정
		if(queryString.size() != 2) {
			model.addAttribute("isSearchList", new Boolean(true));
		}

		JSONObject listSearch = JSONObjectUtil.getJSONObject(centerItemInfo, listSearchId);
//		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
//		if(itemInfoSearchList != null) {
//			searchList.addAll(itemInfoSearchList);
//			model.addAttribute("isSearchList", new Boolean(true));
//		}
		
		// 신청일 검색
		// 신청일을 검색할 때 queryString 파라미터에 이전 검색기록까지 같이 나와서 가장 최근에 검색한 파라미터 값을 가져오는 것으로 변경
		String searchKeyFlag = RbsProperties.getProperty("Globals.item.search.pre.flag");
		String dateRegi1ItemName = "dateRegi1";
		String[] dateRegi1Values =  request.getParameterValues(searchKeyFlag + dateRegi1ItemName);
		if(dateRegi1Values != null) {
			String dateRegi1 = dateRegi1Values[dateRegi1Values.length -1];
			if(!dateRegi1.isEmpty()) {
				param.put("REGI_DATE1", dateRegi1);
				model.addAttribute("isSearchList", new Boolean(true));
			}
		}
		String dateRegi2ItemName = "dateRegi2";
		String[] dateRegi2Values =  request.getParameterValues(searchKeyFlag + dateRegi2ItemName);
		if(dateRegi2Values != null) {
			String dateRegi2 = dateRegi2Values[dateRegi2Values.length -1];
			if(!dateRegi2.isEmpty()) {
				param.put("REGI_DATE2", dateRegi2);
				model.addAttribute("isSearchList", new Boolean(true));
			}
		}
		
		// 기간 검색
		// 기간을 검색할 때 queryString 파라미터에 이전 검색기록까지 같이 나와서 가장 최근에 검색한 파라미터 값을 가져오는 것으로 변경
		String date1ItemName = "date1";
		String[] date1Values =  request.getParameterValues(searchKeyFlag + date1ItemName);
		if(date1Values != null) {
			String date1 = date1Values[date1Values.length -1];
			if(!date1.isEmpty()) {
				param.put("START_DATE", date1);
				model.addAttribute("isSearchList", new Boolean(true));
			}
		}
		String date2ItemName = "date2";
		String[] date2Values =  request.getParameterValues(searchKeyFlag + date2ItemName);
		if(date2Values != null) {
			String date2 = date2Values[date2Values.length -1];
			if(!date2.isEmpty()) {
				param.put("END_DATE", date2);
				model.addAttribute("isSearchList", new Boolean(true));
			}
		}
		
		// 아이디 또는 이름으로 검색
		String keyFieldCode = queryString.getString("keyField");
		String keyCode = queryString.getString("key");
		if(!StringUtil.isEmpty(keyFieldCode) && !StringUtil.isEmpty(keyCode)) {
			if(keyFieldCode.equals("mbrId")) {
				param.put("KEY_FIELD", "MEMBER_ID");
			} else {
				param.put("KEY_FIELD", "MEMBER_NAME");
			}
			param.put("KEY", "%" + keyCode + "%");
			model.addAttribute("isSearchList", new Boolean(true));
		}
		param.put("searchList", searchList);

		// 2.2 목록수
    	totalCount = memberManageService.getTotalCenterAuthCount(param);
		paginationInfo.setTotalRecordCount(totalCount);
    	
    	if(totalCount > 0) {
    		param.put("firstIndex", paginationInfo.getFirstRecordIndex());
    		param.put("lastIndex", paginationInfo.getLastRecordIndex());
    		param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
    		
    		//권한그룹 select의 not in 조건에 부합하는 파라미터 설정
    		String[] usertype_idx_list = new String[] {"5", "10", "20", "55"};
    		authParam.put("USERTYPE_IDX_LIST", usertype_idx_list);
    		
    		

    		// 2.3 목록
    		list = memberManageService.getCenterAuthList(param);
    		authList = authManageService.getAuthGroupList(authParam);
    	}
    	
    	model.addAttribute("paginationInfo", paginationInfo);					// 페이징정보
    	model.addAttribute("list", list);										// 목록
    	HashMap<String, Object> searchOptnHashMap = CodeHelper.getItemOptnSearchHashMap(listSearch);	// 검색항목코드

		// 사용자그룹 추가
		JSONArray searchOrder = JSONObjectUtil.getJSONArray(listSearch, "searchAddOptn_order");
		JSONObject keyInfo = JSONObjectUtil.getJSONObject(listSearch, "key");
		JSONObject keyItems = JSONObjectUtil.getJSONObject(keyInfo, "items");
		HashMap<String, Object> searchAddOptnHashMap = CodeHelper.getItemOptnHashMap(true, keyItems, searchOrder);
		if(searchAddOptnHashMap != null) {
			if(searchOptnHashMap == null) searchOptnHashMap = new HashMap<String, Object>();
			searchOptnHashMap.putAll(searchAddOptnHashMap);
		}
		model.addAttribute("searchOptnHashMap", searchOptnHashMap);	// 검색항목코드
		//model.addAttribute("URL_LISTCENTER", request.getAttribute("URL_LISTCENTER"));
		
		model.addAttribute("centerItemInfo", centerItemInfo);
		model.addAttribute("submitType", "centerList");
		model.addAttribute("authList", authList);
		
    	// 3. 경로 setting
    	// 3.1 기본경로
    	fn_setCommonPath(attrVO);

		return getViewPath("/centerList");
	}
	/* 
	 * 민간센터 일괄변경
	 */
	@Auth(role = Role.U)
	@RequestMapping(method = RequestMethod.GET, value = "/centerListProc.do")
	public ModelAndView centerListProc(@ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String authIdx = request.getParameter("authIdx");
		String status = request.getParameter("status");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String memberList = request.getParameter("memberList");
		String authReqList = request.getParameter("authReqList");
		
		int result = memberManageService.updateCenter(authIdx, status, startDate, endDate, memberList, authReqList, request.getRemoteAddr());

		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		mav.addObject("result", result);
		
		return mav;
	}
	/**
	 * 본부직원 소속변경목록 조회
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value = "/employList.do")
	public String employList(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		DataForm queryString = attrVO.getQueryString();
		
		String listSearchId = "employList_search";		// 검색설정
    	
		// 1. 페이지정보 setting
		int listUnit = JSONObjectUtil.getInt(settingInfo, "dset_list_count", propertiesService.getInt("DEFAULT_LIST_UNIT"));	// 페이지당 목록 수
		int listMaxUnit = listUnit * propertiesService.getInt("DEFAULT_LIST_MAX_CNT");											// 최대 페이지당 목록 수 
		int listUnitStep = listUnit;																							// 페이지당 목록 수 증가값
		
		int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
		if(pageUnit == 0) pageUnit = listUnit;																					// 페이지당 목록 수
		int pageSize = JSONObjectUtil.getInt(settingInfo, "dset_list_block", propertiesService.getInt("PAGE_SIZE"));			// 페이징 크기
		int page = StringUtil.getInt(request.getParameter("page"), 1);															// 현재 페이지 index

		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		paginationInfo.setUnitBeginCount(listUnit);
		paginationInfo.setUnitEndCount(listMaxUnit);
		paginationInfo.setUnitStep(listUnitStep);
		paginationInfo.setCurrentPageNo(page);
		paginationInfo.setRecordCountPerPage(pageUnit);
		paginationInfo.setPageSize(pageSize);
		
		// 2. DB
		int totalCount = 0;
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건
		
		// 항목설정으로 검색조건 setting
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		// 사용자 그룹 검색
		String searchKeyFlag = RbsProperties.getProperty("Globals.item.search.pre.flag");
		String mbrGrpItemName = "mbrGrp";
		String mbrGrpCd = queryString.getString(searchKeyFlag + mbrGrpItemName);
		if(!StringUtil.isEmpty(mbrGrpCd)) {
			param.put("mbrGrpCd", mbrGrpCd);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		param.put("searchList", searchList);

		// 2.2 목록수
    	totalCount = memberManageService.getTotalEmployRegiCount(param);
		paginationInfo.setTotalRecordCount(totalCount);
    	
    	if(totalCount > 0) {
    		param.put("firstIndex", paginationInfo.getFirstRecordIndex());
    		param.put("lastIndex", paginationInfo.getLastRecordIndex());
    		param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
    		
    		// 2.3 목록
    		list = memberManageService.getEmployRegiList(param);
    	}
    	
    	model.addAttribute("paginationInfo", paginationInfo);					// 페이징정보
    	model.addAttribute("list", list);										// 목록
    	HashMap<String, Object> searchOptnHashMap = CodeHelper.getItemOptnSearchHashMap(listSearch);	// 검색항목코드

		// 사용자그룹 추가
		JSONArray searchOrder = JSONObjectUtil.getJSONArray(listSearch, "searchAddOptn_order");
		JSONObject keyInfo = JSONObjectUtil.getJSONObject(listSearch, "key");
		JSONObject keyItems = JSONObjectUtil.getJSONObject(keyInfo, "items");
		HashMap<String, Object> searchAddOptnHashMap = CodeHelper.getItemOptnHashMap(true, keyItems, searchOrder);
		if(searchAddOptnHashMap != null) {
			if(searchOptnHashMap == null) searchOptnHashMap = new HashMap<String, Object>();
			searchOptnHashMap.putAll(searchAddOptnHashMap);
		}
		model.addAttribute("searchOptnHashMap", searchOptnHashMap);	// 검색항목코드
		
		// 검색을 위한 지부지사 목록 가져오기
		List<Object> insttList = null;
		insttList = memberManageService.getInsttList();
		model.addAttribute("insttList", insttList);
		
    	// 3. 경로 setting
    	// 3.1 기본경로
    	fn_setCommonPath(attrVO);

		return getViewPath("/employList");
	}
	
	/* 
	 * 공단소속 일괄변경 
	 */
	@Auth(role = Role.U)
	@RequestMapping(method = RequestMethod.GET, value = "/employListProc.do")
	public ModelAndView employListProc(@ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String status = request.getParameter("status");
		String memberList = request.getParameter("memberList");
		String doctorList = request.getParameter("doctorList");
		
		int result = memberManageService.updateEmploy(status, memberList, doctorList, request.getRemoteAddr());

		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		mav.addObject("result", result);
		
		return mav;
	}

	/**
	 * 상세조회
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
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
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();

		/*// 전체관리권한여부
		Boolean isMngAuth = AuthHelper.isAuthenticated("MNG");		// 전체관리
		if(!isMngAuth){
			LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
			String loginMemberIdx = null;
			if(loginVO != null){
				loginMemberIdx = loginVO.getMemberIdx();
			}
			param.put("AUTH_MEMBER_IDX", loginMemberIdx);
		}*/
		
		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		
		param.put("searchList", searchList);

		// 상세정보
		dt = memberManageService.getView(param);
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
		model.addAttribute("dt", dt);																				// 상세정보
		model.addAttribute("multiFileHashMap", memberManageService.getMultiFileHashMap(keyIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("multiDataHashMap", memberManageService.getMultiHashMap(keyIdx, settingInfo, items, itemOrder));		// multi file 목록
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);																// 페이지구분
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	/**
	 * 수정 : 관리권한 없는 경우 - 자신이 등록한 글만 수정
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@ModuleAuth(name={"WRT"})
	@RequestMapping(value = "/input.do", params="mode")
	public String input(@RequestParam(value="mode") String mode, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		String keyIdx = request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name"));
		
		// 1.2 수정모드 여부 검사
		boolean isModify = (StringUtil.isEquals(mode, "m"));

		if(StringUtil.isEmpty(keyIdx) || !isModify) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		// 2. DB
		String idxColumnName = JSONObjectUtil.getString(settingInfo, "idx_column");		// key column명
		
		DataMap dt = null;
		DataMap dtMem = null;
		List<Object> list = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();

		
		// 2.1 수정권한 체크
		Boolean isMngAuth = isMngProcAuth(settingInfo, keyIdx);
		if(!isMngAuth) {
			// 수정권한 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.auth")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		param.put("searchList", searchList);
		
		// 2.2 상세정보
		dtMem = memberManageService.getModify(param);
		searchList = new ArrayList<DTForm>();
		searchList.add(new DTForm("A.USERID", dtMem.get("MEMBER_IDX")));
		param.put("searchList", searchList);
		dt = memberManageService.getMember(param);
		list = memberManageService.getAuthGroupList();
		if(list == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "modify";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");

		// 3.2 속성 setting
		model.addAttribute("dt", dt);
		model.addAttribute("list", list);
		model.addAttribute("multiDataHashMap", memberManageService.getMultiHashMap(keyIdx, settingInfo, items, itemOrder));		// multi file 목록
		//model.addAttribute("multiFileHashMap", memberManageService.getMultiFileHashMap(keyIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));		// 항목코드
		model.addAttribute("submitType", submitType);											// 페이지구분
		
		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	// 5. form action
    	model.addAttribute("URL_SUBMITPROC", request.getAttribute("URL_IDX_MODIFYPROC"));
		
    	return getViewPath("/input");
	}
	
	/**
	 * 기업회원 조회 및 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="WRT")
	@RequestMapping(value = "/inputCorp.do")
	public String inputCorp(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String siteId = attrVO.getSiteId();
		
		// 1. 필수 parameter 검사
		String keyIdx = request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name"));
		String corpIdx = request.getParameter("corpNum");
		if(StringUtil.isEmpty(keyIdx)) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		String idxColumnName = JSONObjectUtil.getString(settingInfo, "idx_column");

		
		// 2. DB
		DataMap dt = null;
		DataMap dtCorp = null;
		DataMap dtOrg = null;
		List<?> dtRegi = null;
		List<?> dtDoctor = null;
		DataMap dtPreDoctor = null;
		DataMap dtCliDoctor = null;
		List<?> codeList = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		
		param.put("searchList", searchList);
		param.put("CORP_NUM", corpIdx);

		// 상세정보
		dt = memberManageService.getView(param);
		dtCorp = memberManageService.getViewCorp(param);
		String bizIdx = StringUtil.getString(dtCorp.get("IND_LOCATIONNUM"));
		param.put("bizNum", bizIdx);
		dtOrg = memberManageService.getViewOrg(param);
		if(dtOrg != null) {
			String bplIdx = StringUtil.getString(dtOrg.get("BPL_NO"));
			param.put("bplNum", bplIdx);
			dtRegi = memberManageService.getViewRegi(param);
			dtPreDoctor = memberManageService.getViewDoctor(param);
			if(dtPreDoctor != null) {
				String docIdx = StringUtil.getString(dtPreDoctor.get("MEMBER_IDX"));
				param.put("MEMBER_IDX", docIdx);
			}
			
			dtDoctor = memberManageService.getDoctorInfo(param);
			dtCliDoctor = memberManageService.getViewCliDoctor(param);
		}
		codeList = memberManageService.getCodeList();
		
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}

		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "write1";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		// 3.2 속성 setting
		model.addAttribute("dt", dt);																				// 상세정보
		model.addAttribute("dtCorp", dtCorp);																		// 포털 THR_CORP 기업정보
		model.addAttribute("dtOrg", dtOrg);																			// 기업정보
		model.addAttribute("dtRegi", dtRegi);																		// 소속기관 변경이력 
		model.addAttribute("dtDoctor", dtDoctor);																	// 주치의정보(개인정보)
		model.addAttribute("doctorNormal", dtPreDoctor);															// 주치의정보(소속기관)
		model.addAttribute("dtCliDoctor", dtCliDoctor);																// 전담주치의정보
		model.addAttribute("codeList", codeList);	
		model.addAttribute("multiFileHashMap", memberManageService.getMultiFileHashMap(keyIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("multiDataHashMap", memberManageService.getMultiHashMap(keyIdx, settingInfo, items, itemOrder));		// multi file 목록
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);																// 페이지구분
		
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	if(StringUtil.isEquals(siteId, "web")) {
    		
    		model.addAttribute("URL_SUBMITPROC", request.getAttribute("URL_INPUTCORPPROC"));
    		
    		return getViewPath("/inputWebCorp");
    	}
    	
    	model.addAttribute("URL_SUBMITPROC", request.getAttribute("URL_INPUTDCTCORP1PROC"));
    	model.addAttribute("URL_SUBMITPROC2", request.getAttribute("URL_INPUTDCTCORP2PROC"));
    	model.addAttribute("URL_SUBMITPROC3", request.getAttribute("URL_INPUTDCTCORP3PROC"));

    	
    	return getViewPath("/inputDctCorp");
	}
	
	/**
	 * 컨설턴트 조회 및 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="WRT")
	@RequestMapping(value = "/inputConsult.do")
	public String inputConsult(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String siteId = attrVO.getSiteId();
		
		// 1. 필수 parameter 검사
		String keyIdx = request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name"));
		String corpIdx = request.getParameter("corpNum");
		if(StringUtil.isEmpty(keyIdx)) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		String idxColumnName = JSONObjectUtil.getString(settingInfo, "idx_column");

		
		// 2. DB
		DataMap dt = null;
		DataMap dtConsult = null;
		List<?> dtReq = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		
		param.put("searchList", searchList);
		param.put("CORP_NUM", corpIdx);
		param.put("memberIdx", keyIdx);

		// 상세정보
		dt = memberManageService.getView(param);
		dtConsult = memberManageService.getViewCorp(param);
		dtReq = memberManageService.getWebReq(param);
		
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}

		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "write2";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		// 3.2 속성 setting
		model.addAttribute("dt", dt);																				// 상세정보
		model.addAttribute("dtConsult", dtConsult);																		// 포털 THR_CORP 기업정보
		model.addAttribute("dtReq", dtReq);		
		model.addAttribute("multiFileHashMap", memberManageService.getMultiFileHashMap(keyIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("multiDataHashMap", memberManageService.getMultiHashMap(keyIdx, settingInfo, items, itemOrder));		// multi file 목록
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);																// 페이지구분
		
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	model.addAttribute("URL_SUBMITPROC", request.getAttribute("URL_INPUTCONSULTPROC"));
    	
    	if(StringUtil.isEquals(siteId, "web")) return getViewPath("/inputWebConsult");
    	
    	return getViewPath("/inputDctConsult");
	}
	
	/**
	 * 민간센터 조회 및 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="WRT")
	@RequestMapping(value = "/inputCenter.do")
	public String inputCenter(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String siteId = attrVO.getSiteId();
		
		// 1. 페이지정보 setting
		int listUnit = JSONObjectUtil.getInt(settingInfo, "dset_list_count", propertiesService.getInt("DEFAULT_LIST_UNIT"));	// 페이지당 목록 수
		int listMaxUnit = listUnit * propertiesService.getInt("DEFAULT_LIST_MAX_CNT");											// 최대 페이지당 목록 수 
		int listUnitStep = listUnit;																							// 페이지당 목록 수 증가값
		
		int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
		if(pageUnit == 0) pageUnit = listUnit;																					// 페이지당 목록 수
		int pageSize = JSONObjectUtil.getInt(settingInfo, "dset_list_block", propertiesService.getInt("PAGE_SIZE"));			// 페이징 크기
		int page = StringUtil.getInt(request.getParameter("page"), 1);															// 현재 페이지 index

		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		paginationInfo.setUnitBeginCount(listUnit);
		paginationInfo.setUnitEndCount(listMaxUnit);
		paginationInfo.setUnitStep(listUnitStep);
		paginationInfo.setCurrentPageNo(page);
		paginationInfo.setRecordCountPerPage(pageUnit);
		paginationInfo.setPageSize(pageSize);

		
		// 1. 필수 parameter 검사
		String keyIdx = request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name"));
		String corpIdx = request.getParameter("corpNum");
		if(StringUtil.isEmpty(keyIdx)) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		String idxColumnName = JSONObjectUtil.getString(settingInfo, "idx_column");

		int totalCount = 0;
		
		// 2. DB
		DataMap dt = null;
		DataMap dtCenter = null;
		List<?> dtReq = null;
		List<?> dtAuth = null;
		List<?> authList = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		
		param.put("searchList", searchList);
		param.put("CORP_NUM", corpIdx);
		param.put("memberIdx", keyIdx);
		
		// 2.2 목록수
    	totalCount = memberManageService.getTotalCenterCount(param);
		paginationInfo.setTotalRecordCount(totalCount);
    	
    	if(totalCount > 0) {
    		param.put("firstIndex", paginationInfo.getFirstRecordIndex());
    		param.put("lastIndex", paginationInfo.getLastRecordIndex());
    		param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
    		
    		
    	}
    	
    	

		// 상세정보
		dt = memberManageService.getView(param);
		dtCenter = memberManageService.getViewCorp(param);
		dtAuth = memberManageService.getAuth(param);
		if(StringUtil.isEquals(siteId, "web")) {
			dtReq = memberManageService.getWebReq(param);
		}
		
		if(StringUtil.isEquals(siteId, "dct")) {
			dtReq = memberManageService.getDctReq(param);
		}
		authList = memberManageService.getAuthGroupList();
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		

		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "write3";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		// 3.2 속성 setting
		model.addAttribute("dt", dt);																				// 상세정보
		model.addAttribute("dtCenter", dtCenter);																		// 포털 THR_CORP 기업정보
		model.addAttribute("dtReq", dtReq);
		model.addAttribute("dtAuth", dtAuth);
		model.addAttribute("authList", authList);
		model.addAttribute("multiFileHashMap", memberManageService.getMultiFileHashMap(keyIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("multiDataHashMap", memberManageService.getMultiHashMap(keyIdx, settingInfo, items, itemOrder));		// multi file 목록
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);																// 페이지구분
		model.addAttribute("paginationInfo", paginationInfo);														// 페이징정보
		
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	if(StringUtil.isEquals(siteId, "web")) {

        	model.addAttribute("URL_SUBMITPROC", request.getAttribute("URL_INPUTCENTERPROC"));
        	
    		return getViewPath("/inputWebCenter");
    	}
    	
    	model.addAttribute("URL_SUBMITPROC", request.getAttribute("URL_APPLYCENTERPROC"));
    	model.addAttribute("URL_SECSUBMITPROC", request.getAttribute("URL_INPUTCENTERPROC"));
    	
    	return getViewPath("/inputDctCenter");
	}
	
	
	/**
	 * 직원 조회 및 수정(내 정보수정) 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="WRT")
	@RequestMapping(value = "/inputFstEmploy.do")
	public String inputFstEmploy(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		// 1. 필수 parameter 검사
		String keyIdx = request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name"));
		//String corpIdx = request.getParameter("corpNum");
		if(StringUtil.isEmpty(keyIdx)) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		String idxColumnName = JSONObjectUtil.getString(settingInfo, "idx_column");

		
		// 2. DB
		DataMap dt = null;
		List<?> dtDoctor = null;
		List<?> codeList = null;
		List<?> gradeList = null;

		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		
		param.put("searchList", searchList);
		param.put("memberIdx", keyIdx);

		// 상세정보
		dt = memberManageService.getView(param);
		dtDoctor = memberManageService.getEmpDoctor(param);
		codeList = memberManageService.getCodeList();
		gradeList = memberManageService.getGradeList();
		
		
		
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}

		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "write4";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		// 3.2 속성 setting
		model.addAttribute("dt", dt);																				// 상세정보
		model.addAttribute("dtDoctor", dtDoctor);																	// 주치의 정보
		model.addAttribute("codeList", codeList);
		model.addAttribute("gradeList", gradeList);
		model.addAttribute("multiFileHashMap", memberManageService.getMultiFileHashMap(keyIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("multiDataHashMap", memberManageService.getMultiHashMap(keyIdx, settingInfo, items, itemOrder));		// multi file 목록
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);																// 페이지구분
		
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	model.addAttribute("URL_SUBMITPROC", request.getAttribute("URL_INPUTEMPLOYPROC"));
    	 
    	
    	return getViewPath("/inputWebEmploy");
	}
	
	/**
	 * 직원 조회 및 수정 (회원관리 - 정보수정)
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="WRT")
	@RequestMapping(value = "/inputSecEmploy.do")
	public String inputSecEmploy(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		// 1. 페이지정보 setting
		int listUnit = JSONObjectUtil.getInt(settingInfo, "dset_list_count", propertiesService.getInt("DEFAULT_LIST_UNIT"));	// 페이지당 목록 수
		int listMaxUnit = listUnit * propertiesService.getInt("DEFAULT_LIST_MAX_CNT");											// 최대 페이지당 목록 수 
		int listUnitStep = listUnit;																							// 페이지당 목록 수 증가값
		
		int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
		if(pageUnit == 0) pageUnit = listUnit;																					// 페이지당 목록 수
		int pageSize = JSONObjectUtil.getInt(settingInfo, "dset_list_block", propertiesService.getInt("PAGE_SIZE"));			// 페이징 크기
		int page = StringUtil.getInt(request.getParameter("page"), 1);															// 현재 페이지 index

		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		paginationInfo.setUnitBeginCount(listUnit);
		paginationInfo.setUnitEndCount(listMaxUnit);
		paginationInfo.setUnitStep(listUnitStep);
		paginationInfo.setCurrentPageNo(page);
		paginationInfo.setRecordCountPerPage(pageUnit);
		paginationInfo.setPageSize(pageSize);

		
		// 1. 필수 parameter 검사
		String keyIdx = request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name"));
		if(StringUtil.isEmpty(keyIdx)) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		String idxColumnName = JSONObjectUtil.getString(settingInfo, "idx_column");

		int totalCount = 0;
		
		// 2. DB
		DataMap dt = null;
		List<?> dtDoctor = null;
		List<?> codeList = null;
		List<?> gradeList = null;

		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		
		param.put("searchList", searchList);
		param.put("memberIdx", keyIdx);

		// 2.2 목록수
    	totalCount = memberManageService.getTotalEmpReqCount(param);
		paginationInfo.setTotalRecordCount(totalCount);
    	
    	if(totalCount > 0) {
    		param.put("firstIndex", paginationInfo.getFirstRecordIndex());
    		param.put("lastIndex", paginationInfo.getLastRecordIndex());
    		param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
    		
    		
    	}
		
		// 상세정보
		dt = memberManageService.getView(param);
		dtDoctor = memberManageService.getEmpDoctor(param);
		codeList = memberManageService.getCodeList();
		gradeList = memberManageService.getGradeList();
		
		
		
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}

		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "write41";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		// 3.2 속성 setting
		model.addAttribute("dt", dt);																				// 상세정보
		model.addAttribute("dtDoctor", dtDoctor);																	// 주치의 정보
		model.addAttribute("codeList", codeList);
		model.addAttribute("gradeList", gradeList);
		model.addAttribute("multiFileHashMap", memberManageService.getMultiFileHashMap(keyIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("multiDataHashMap", memberManageService.getMultiHashMap(keyIdx, settingInfo, items, itemOrder));		// multi file 목록
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);																// 페이지구분
		model.addAttribute("paginationInfo", paginationInfo);														// 페이징정보
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	model.addAttribute("URL_SUBMITPROC", request.getAttribute("URL_INPUTEMPLOYPROC"));
    	model.addAttribute("URL_SECSUBMITPROC", request.getAttribute("URL_APPLYEMPLOYPROC"));
    	
    	return getViewPath("/inputDctEmploy");
	}
	
	/**
	 * 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="WRT")
	@RequestMapping(value = "/input.do")
	public String input(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject itemInfo = attrVO.getItemInfo();
		
		// 1. 속성 setting
		// 1.1 항목설정
		String submitType = "write";
		model.addAttribute("optnHashMap", getOptionHashMap(submitType, itemInfo));
		model.addAttribute("submitType", submitType);

		List<Object> list = null;
		list = memberManageService.getAuthGroupList();
    	// 2. 기본경로
    	fn_setCommonPath(attrVO);

    	// 3. form action
    	model.addAttribute("URL_SUBMITPROC", request.getAttribute("URL_INPUTPROC"));
    	model.addAttribute("list", list);
    	
		return getViewPath("/input");
	}
	
	/**
	 * 일괄등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ModuleAuth(name="WRT")
	@RequestMapping(value = "/inputGroup.do")
	public String inputGroup(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject itemInfo = attrVO.getItemInfo();
		
		// 1. 속성 setting
		// 1.1 항목설정
		String submitType = "write";
		model.addAttribute("optnHashMap", getOptionHashMap(submitType, itemInfo));
		model.addAttribute("submitType", submitType);

		List<Object> list = null;
		list = memberManageService.getAuthGroupList();
    	// 2. 기본경로
    	fn_setCommonPath(attrVO);

    	// 3. form action
    	model.addAttribute("URL_SUBMITPROC", request.getAttribute("URL_INPUTGROUPPROC"));
    	model.addAttribute("list", list);
    	
		return getViewPath("/inputGroup");
	}

	/**
	 * 수정처리 : 관리권한 없는 경우 - 자신이 등록한 글만 수정
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@ModuleAuth(name={"WRT"})
	@RequestMapping(method=RequestMethod.POST, value = "/inputProc.do", params="mode")
	public String inputProc(@RequestParam(value="mode") String mode, @ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		
		boolean isAdmMode = attrVO.isAdmMode();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		String keyIdx = request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name"));
		
		// 1.2 수정모드 여부 검사
		boolean isModify = (StringUtil.isEquals(mode, "m"));

		if(StringUtil.isEmpty(keyIdx) || !isModify) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}

		// 2. DB
		// 2.1 상세정보 - 해당글이 없는 경우 return
		String idxColumnName = JSONObjectUtil.getString(settingInfo, "idx_column");		// key column명
		
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		param.put("searchList", searchList);

		dt = memberManageService.getModify(param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 2.2 전체관리/완전삭제 권한 체크 - 수정권한 없는 경우 return
		Boolean isMngAuth = isMngProcAuth(settingInfo, keyIdx);
		if(!isMngAuth) {
			// 수정권한 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.auth")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}

		// 3. 필수입력 체크
		// 3.1 항목설정
		String submitType = "modify";		// 항목 order명
		String inputFlag = "modify";		// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		
		// 3.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, itemOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 4. DB
    	int result = memberManageService.update(uploadModulePath, keyIdx, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
    	if(result > 0) {
    		// 4.1 저장 성공
    		
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	
    		//String inputNpname = fn_dsetInputNpname(settingInfo);
			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "self.close();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} else if(result == -1) {
    		// 4.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) return fileFailView;
    	}
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}

	/**
	 * 등록처리
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.POST, value = "/inputProc.do")
	public String inputProc(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write";		// 항목 order명
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		
		// 1.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, itemOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		String keyIdx = request.getParameter("memberIdx");
		
		memberManageService.apply(parameterMap, keyIdx, request.getRemoteAddr(), settingInfo);
		// 2. DB
    	int result = memberManageService.insert(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_" + inputNpname)));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "self.close();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} else if(result == -1) {
    		// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) return fileFailView;
    	}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	@Auth(role = Role.C)
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.POST, value = "/inputDctCorp1Proc.do")
	public String inputDctCorp1Proc(@RequestParam Map param, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		if("Y".equals(param.get("statusVal"))) {
			param.put("status", 1);
		} else {
			param.put("status", 0);
		}
		memberManageService.updatePSITN(param);
		
		response.setStatus(200);
		
		model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "location.reload();"));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 기업회원 등록처리
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.POST, value = "/inputDctCorp2Proc.do")
	public String inputDctCorp2Proc(@RequestParam Map param, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		memberManageService.insertPSITNbyDCT(param);
		response.setStatus(200);
		
		model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "location.reload();"));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	
	/**
	 * 기업회원 주치의 및 전담주치의 등록처리
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.POST, value="/updateDoctorProc.do")
	public String updateDoctorProc(@RequestParam Map param, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		
		memberManageService.updateDoctor(param);
		response.setStatus(200);
		
		model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "location.reload();"));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 기업회원 등록처리
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.POST, value = "/inputDctCorp3Proc.do")
	public String inputDctCorp3Proc(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write1";		// 항목 order명
		String inputFlag = "write1";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		
		// 1.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, itemOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		String keyIdx = request.getParameter("bplNo");
		String region = request.getParameter("region");
		
		DataMap dtCorp = null;
		Map<String, Object> param = new HashMap<String, Object>();		
		
		param.put("BPL_NO", keyIdx);
		
		dtCorp = memberManageService.getPreOrg(param);
		int preIns = StringUtil.getInt(dtCorp.get("INSTT_IDX"));
		param.put("preIns", preIns);
		
		parameterMap.put("BPL_NO", keyIdx);
		parameterMap.put("preInsttIdx", preIns);
		parameterMap.put("insttIdx", region);
		
		// 2. DB
     	int result = memberManageService.insertOrg(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_" + inputNpname)));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "location.reload();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} else if(result == -1) {
    		// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) return fileFailView;
    	}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 기업회원(사용자) 등록처리
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.POST, value = "/inputCorpProc.do")
	public String inputCorpProc(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write1";		// 항목 order명
		String inputFlag = "write1";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		
		// 1.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, itemOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		String keyIdx = request.getParameter("bplNo");
		String insttIdx = request.getParameter("insttIdx");
		
		DataMap dtCorp = null;
		Map<String, Object> param = new HashMap<String, Object>();		
		
		param.put("BPL_NO", keyIdx);
		
		// 신청 데이터 존재 유무 확인
		int check = memberManageService.checkCorpReq(param);
		if(check > 0) {
			// 신청 데이터 존재
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.application")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		dtCorp = memberManageService.getPreOrg(param);
		
		int preIns = StringUtil.getInt(dtCorp.get("INSTT_IDX"));
		param.put("preIns", preIns);
		
		parameterMap.put("BPL_NO", keyIdx);
		parameterMap.put("preInsttIdx", preIns);
		parameterMap.put("insttIdx", insttIdx);
		parameterMap.put("status", 2);
		
		// 2. DB
     	int result = memberManageService.insertOrg(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_" + inputNpname)));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "location.reload();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} else if(result == -1) {
    		// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) return fileFailView;
    	}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 컨설턴트 등록처리
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.POST, value = "/inputConsultProc.do")
	public String inputConsultProc(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write2";		// 항목 order명
		String inputFlag = "write2";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		
		// 1.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, itemOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		//String keyIdx = request.getParameter("memberIdx");
		
		
		// 2. DB
    	int result = memberManageService.insertConsult(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);

    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_" + inputNpname)));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "location.reload();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} else if(result == -1) {
    		// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) return fileFailView;
    	}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 민간센터 등록처리
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.POST, value = "/inputCenterProc.do")
	public String inputCenterProc(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		
		
		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write";		// 항목 order명
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		
		// 1.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, itemOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		Map<String, Object> param = new HashMap<String, Object>();
		
		String keyIdx = request.getParameter("memberIdx");
		
		param.put("MEMBER_IDX", keyIdx);
		
		// 신청 데이터 존재 유무 확인
		int check = memberManageService.checkCenterReq(param);
		if(check > 0) {
			// 신청 데이터 존재
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.application")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		/*int result = memberManageService.applyCenter(parameterMap, request.getRemoteAddr(), settingInfo);*/
		// 2. DB
		int result = memberManageService.insertCenter(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_" + inputNpname)));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "location.reload();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} else if(result == -1) {
    		// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) return fileFailView;
    	}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 민간센터 승인처리
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.POST, value = "/applyCenterProc.do")
	public String applyCenterProc(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		
		
		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write3";		// 항목 order명
		String inputFlag = "write3";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		
		// 1.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, itemOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		Map<String, Object> param = new HashMap<String, Object>();
		
		String keyIdx = request.getParameter("memberIdx");
		String apply = StringUtil.getString(parameterMap.get("apply"));
		String reqIdx = StringUtil.getString(parameterMap.get("reqIdx"));
		
		//삭제처리
		if(StringUtil.isEquals(apply, "D")) {
			param.put("AUTH_REQ_IDX", reqIdx);
			int delete = memberManageService.centerDelete(param);
			if(delete > 0) {
				// 신청 데이터 삭제
				model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.delete"), "location.reload();"));
				return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
			}
		}
		
		
		param.put("MEMBER_IDX", keyIdx);
		
		// 현재 적용중인 데이터 존재 유무 확인
		int check = memberManageService.checkCenterApply(param);
		if(check > 0) {
			// 신청 데이터 존재
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.already.apply")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		int result = memberManageService.applyCenter(parameterMap, request.getRemoteAddr(), settingInfo);
		// 2. DB
    	/*= memberManageService.insertCenter(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);*/
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_" + inputNpname)));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "location.reload();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} else if(result == -1) {
    		// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) return fileFailView;
    	}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 직원 등록처리
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.POST, value = "/inputEmployProc.do")
	public String inputEmployProc(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write4";		// 항목 order명
		String inputFlag = "write4";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		
		// 1.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, itemOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		String keyIdx = request.getParameter("memberIdx");
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("MEMBER_IDX", keyIdx);
		
		// 주치의 등록이 되어 있는지 체크
		int checkRegi = memberManageService.checkEmpRegi(param);
		if(checkRegi == 0) {
			// 신청 데이터 존재
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.doctor")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 신청 기록이 있는지 체크
		int checkApply = memberManageService.checkEmpReq(param);
		if(checkApply > 0) {
			// 신청 데이터 존재
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.application")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 2. DB
    	int result = memberManageService.insertEmploy(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_" + inputNpname)));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "location.reload();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} else if(result == -1) {
    		// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) return fileFailView;
    	}
    	
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 직원 승인처리
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.POST, value = "/applyEmployProc.do")
	public String applyEmployProc(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		//String uploadModulePath = attrVO.getUploadModulePath();
		

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write42";		// 항목 order명
		String inputFlag = "write42";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		
		// 1.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, itemOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		Map<String, Object> param = new HashMap<String, Object>();
		String stat = StringUtil.getString(parameterMap.get("stat"));
		String docIdx = StringUtil.getString(parameterMap.get("docIdx"));
		
		//삭제처리
		if(StringUtil.isEquals(stat, "D")) {
			param.put("DOCTOR_IDX", docIdx);
			int delete = memberManageService.employDelete(param);
			if(delete > 0) {
				// 신청 데이터 삭제
				model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.delete"), "location.reload();"));
				return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
			}
		}
		
		
		// 2. DB
    	int result = memberManageService.applyEmp(parameterMap, request.getRemoteAddr(), settingInfo);
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_" + inputNpname)));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "location.reload();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} else if(result == -1) {
    		// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) return fileFailView;
    	}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	
	
	/**
	 * 일괄등록처리
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.POST, value = "/inputGroupProc.do")
	public String inputGroupProc(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write";		// 항목 order명
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		
		// 1.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, itemOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		String keyIdx = request.getParameter("memberIdx");
		
		memberManageService.apply(parameterMap, keyIdx, request.getRemoteAddr(), settingInfo);
		// 2. DB
    	int result = memberManageService.insert(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_" + inputNpname)));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "self.close();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} else if(result == -1) {
    		// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) return fileFailView;
    	}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}

	/**
	 * 삭제처리
	 * @param deleteIdxs
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.D)
	@ModuleAuth(name="MNG")
	@RequestMapping(method=RequestMethod.POST, value = "/deleteProc.do", params="select")
	public String delete(@ParamMap ParamForm parameterMap, @RequestParam(value="select") String[] deleteIdxs, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		JSONObject settingInfo = attrVO.getSettingInfo();
		String ajaxPName = attrVO.getAjaxPName();
		
		// 활성화 되어있는 
		int employApply = memberManageService.applyNotDelete(parameterMap, deleteIdxs, request.getRemoteAddr(), settingInfo);
    	
    	if(employApply > 0) {
    		// 1.1 저장 성공
			model.addAttribute("message",   MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.applyNow"), "top.location.reload();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	}
		
		
		// 1. DB
		int result = memberManageService.delete(parameterMap, deleteIdxs, request.getRemoteAddr(), settingInfo);
    	
    	if(result > 0) {
    		// 1.1 저장 성공
			model.addAttribute("message",   MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.delete"), "top.location.reload();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	}
		// 1.2 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.delete")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}

	/**
	 * 삭제처리 : 관리권한 없는 경우 - 자신이 등록한 글만 수정
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.D)
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.GET, value = "/deleteProc.do")
	public String delete(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		JSONObject settingInfo = attrVO.getSettingInfo();
		
		
		// 1. 필수 parameter 검사
		String keyIdx = request.getParameter("mId");
		if(StringUtil.isEmpty(keyIdx)) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 2. 전체관리/완전삭제 권한 체크
		Boolean isMngAuth = isMngProcAuth(settingInfo, keyIdx);
		if(!isMngAuth) {
			// 자신이 등록한 글이 아닌 경우
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.auth")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 3. DB
		String[] deleteIdxs = {keyIdx + ""};
		int result = memberManageService.delete(parameterMap, deleteIdxs, request.getRemoteAddr(), settingInfo);
    	
    	if(result > 0) {
    		// 3.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.delete"), "fn_procAction('" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_LIST"))) + "', " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	}
		// 3.2 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.delete")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 복원처리
	 * @param restoreIdxs
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.D)
	@ModuleAuth(name="MNG")
	@RequestMapping(method=RequestMethod.POST, value = "/restoreProc.do", params="select")
	public String restore(@RequestParam(value="select") String[] restoreIdxs, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {

		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		JSONObject settingInfo = attrVO.getSettingInfo();
		
		
		// 1. DB
		int result = memberManageService.restore(restoreIdxs, request.getRemoteAddr(), settingInfo);
    	
    	if(result > 0) {
    		// 1.1 저장 성공
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.restore"), "fn_procReload();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	}
		// 1.2 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.restore")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 완전삭제처리
	 * @param deleteIdxs
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ModuleAuth(name="CDT")
	@RequestMapping(method=RequestMethod.POST, value = "/cdeleteProc.do", params="select")
	public String cdelete(@RequestParam(value="select") String[] deleteIdxs, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {

		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		JSONObject itemInfo = attrVO.getItemInfo();
		String uploadModulePath = attrVO.getUploadModulePath();
		JSONObject settingInfo = attrVO.getSettingInfo();
		
		
		// 1. DB
		// 1.1 항목설정
		String submitType = "write";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		// 1.2 삭제처리
		int result = memberManageService.cdelete(uploadModulePath, deleteIdxs, settingInfo, items, itemOrder);
    	
    	if(result > 0) {
    		// 1.2.1 저장 성공
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.cdelete"), "location.reload();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	}
		// 1.2.2 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.cdelete")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}

	/**
	 * 삭제목록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ModuleAuth(name="MNG")
	@RequestMapping(value = "/deleteList.do")
	public String deleteList(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		DataForm queryString = attrVO.getQueryString();
		

    	// 1. 페이지정보 setting
		int listUnit = JSONObjectUtil.getInt(settingInfo, "dset_list_count", propertiesService.getInt("DEFAULT_LIST_UNIT"));	// 페이지당 목록 수
		int listMaxUnit = listUnit * propertiesService.getInt("DEFAULT_LIST_MAX_CNT");											// 최대 페이지당 목록 수 
		int listUnitStep = listUnit;										// 페이지당 목록 수 증가값
		
		int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
		if(pageUnit == 0) pageUnit = listUnit;	// 페이지당 목록 수
		int pageSize = JSONObjectUtil.getInt(settingInfo, "dset_list_block", propertiesService.getInt("PAGE_SIZE"));	// 페이징 크기
		int page = StringUtil.getInt(request.getParameter("page"), 1);				// 현재 페이지 index

		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		paginationInfo.setUnitBeginCount(listUnit);
		paginationInfo.setUnitEndCount(listMaxUnit);
		paginationInfo.setUnitStep(listUnitStep);
		paginationInfo.setCurrentPageNo(page);
		paginationInfo.setRecordCountPerPage(pageUnit);
		paginationInfo.setPageSize(pageSize);
		
		// 2. DB
		int totalCount = 0;
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건
		
		// 항목설정으로 검색조건 setting
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, "list_search");
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoDeleteSearchList("A.LAST_MODI_DATE", deleteListSearchParams, listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		param.put("searchList", searchList);

		// 2.2 목록수
    	totalCount = memberManageService.getDeleteCount(param);
		paginationInfo.setTotalRecordCount(totalCount);
    	
    	if(totalCount > 0) {
    		param.put("firstIndex", paginationInfo.getFirstRecordIndex());
    		param.put("lastIndex", paginationInfo.getLastRecordIndex());
    		param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
    		
    		// 2.3 목록
    		list = memberManageService.getDeleteList(param);
    	}

    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);					// 페이징정보
    	model.addAttribute("list", list);										// 목록
    	HashMap<String, Object> searchOptnHashMap = CodeHelper.getItemOptnSearchHashMap(listSearch);
    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);	// 코드
    	model.addAttribute("optnHashMap", getOptionHashMap("list", itemInfo, searchOptnHashMap));
    	// 4. 휴지통 경로
    	fn_setDeleteListPath(attrVO);
    	
		return getViewPath("/deleteList");
	}

	/**
	 * 파일업로드
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ModuleAuth(name="WRT")
	@RequestMapping(value = "/fileUpload.do")
	public ModelAndView fileUpload(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		boolean isAjax = true;
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String uploadModulePath = attrVO.getUploadModulePath();
		

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = StringUtil.getString(parameterMap.get("itemId"));		// 항목 order명
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		
		// 1.2 필수입력 체크
		if (parameterMap.get(submitType) == null) {
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.error.data")));
			return mav;
		}
		
		// 2. DB
		Map<String, Object> fileInfo = memberManageService.getFileUpload(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
    	if(fileInfo != null && !fileInfo.isEmpty()) {
    		// 2.1 저장 성공
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), ""));
			model.addAttribute("data", fileInfo);
			return mav;
    	} else{
    		// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) {
				model.addAttribute("message", fileFailView);
				return mav;
			}
    	}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return mav;
	}
	
	/**
	 * 엑셀 다운로드
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(value = "/excel.do")
	public String excel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		DataForm queryString = attrVO.getQueryString();
		
		String listSearchId = "list_search";		// 검색설정
		
		// 2. DB
		int totalCount = 0;
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건
		
		// 항목설정으로 검색조건 setting
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		// 사용자 그룹 검색
		String searchKeyFlag = RbsProperties.getProperty("Globals.item.search.pre.flag");
		String mbrGrpItemName = "mbrGrp";
		String mbrGrpCd = queryString.getString(searchKeyFlag + mbrGrpItemName);
		if(!StringUtil.isEmpty(mbrGrpCd)) {
			param.put("mbrGrpCd", mbrGrpCd);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		param.put("searchList", searchList);

		// 2.2 목록수
    	totalCount = memberManageService.getTotalCount(param);
    	
    	if(totalCount > 0) {
    		// 2.3 목록
    		list = memberManageService.getList(param);
    	}
    	
    	// 검색을 위한 지부지사 목록 가져오기
		//List<Object> insttList = null;
		//insttList = memberManageService.getOrgInsttList();
		//model.addAttribute("insttList", insttList);
    	
    	model.addAttribute("list", list);										// 목록
    	HashMap<String, Object> searchOptnHashMap = CodeHelper.getItemOptnSearchHashMap(listSearch);	// 검색항목코드
		// 사용자그룹 추가
		JSONArray searchOrder = JSONObjectUtil.getJSONArray(listSearch, "searchAddOptn_order");
		JSONObject keyInfo = JSONObjectUtil.getJSONObject(listSearch, "key");
		JSONObject keyItems = JSONObjectUtil.getJSONObject(keyInfo, "items");
		HashMap<String, Object> searchAddOptnHashMap = CodeHelper.getItemOptnHashMap(true, keyItems, searchOrder);
		if(searchAddOptnHashMap != null) {
			if(searchOptnHashMap == null) searchOptnHashMap = new HashMap<String, Object>();
			searchOptnHashMap.putAll(searchAddOptnHashMap);
		}
		model.addAttribute("searchOptnHashMap", searchOptnHashMap);	// 검색항목코드
    	// 3. 경로 setting
    	// 3.1 기본경로
    	fn_setCommonPath(attrVO);
		
		return getViewPath("/excel");
	}
	
	
	
	/**
	 * 엑셀 다운로드
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(value = "/corpExcel.do")
	public String corpExcel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		DataForm queryString = attrVO.getQueryString();
		
		String listSearchId = "corpList_search";		// 검색설정
		
		// 2. DB
		int totalCount = 0;
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건
		
		// 항목설정으로 검색조건 setting
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		// 사용자 그룹 검색
		String searchKeyFlag = RbsProperties.getProperty("Globals.item.search.pre.flag");
		String mbrGrpItemName = "mbrGrp";
		String mbrGrpCd = queryString.getString(searchKeyFlag + mbrGrpItemName);
		if(!StringUtil.isEmpty(mbrGrpCd)) {
			param.put("mbrGrpCd", mbrGrpCd);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		param.put("searchList", searchList);

		// 2.2 목록수
    	totalCount = memberManageService.getTotalCorpRegiCount(param);
    	if(totalCount > 0) {
    		
    		// 2.3 목록
    		list = memberManageService.getCorpRegiList(param);
    		
    	}
    	
    	// 검색을 위한 지부지사 목록 가져오기
		List<Object> insttList = null;
		insttList = memberManageService.getInsttList();
		model.addAttribute("insttList", insttList);
    	
    	model.addAttribute("list", list);										// 목록
    	HashMap<String, Object> searchOptnHashMap = CodeHelper.getItemOptnSearchHashMap(listSearch);	// 검색항목코드

		// 사용자그룹 추가
		JSONArray searchOrder = JSONObjectUtil.getJSONArray(listSearch, "searchAddOptn_order");
		JSONObject keyInfo = JSONObjectUtil.getJSONObject(listSearch, "key");
		JSONObject keyItems = JSONObjectUtil.getJSONObject(keyInfo, "items");
		HashMap<String, Object> searchAddOptnHashMap = CodeHelper.getItemOptnHashMap(true, keyItems, searchOrder);
		if(searchAddOptnHashMap != null) {
			if(searchOptnHashMap == null) searchOptnHashMap = new HashMap<String, Object>();
			searchOptnHashMap.putAll(searchAddOptnHashMap);
		}
		model.addAttribute("searchOptnHashMap", searchOptnHashMap);	// 검색항목코드
    	
		// 3. 경로 setting
    	// 3.1 기본경로
    	fn_setCommonPath(attrVO);

		
		return getViewPath("/corpExcel");
	}
	
	/**
	 * 엑셀 다운로드
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(value = "/centerExcel.do")
	public String centerExcel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		DataForm queryString = attrVO.getQueryString();
		JSONObject centerItemInfo = JSONObjectUtil.getJSONObject(itemInfo, "center_item_info");

		String listSearchId = "list_search";		// 검색설정
		
		// 2. DB
		int totalCount = 0;
		List<?> list = null;
		//권한그룹 리스트
		List<?> authList = null;
		Map<String, Object> param = new HashMap<String, Object>();
		//권한그룹 리스트용 param
		Map<String, Object> authParam = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		
		// 2.1 검색조건
		
		// 항목설정으로 검색조건 setting 시작일&종료일 검색 조건 추가로 기존의 검색조건 setting과 다르게 설정
		if(queryString.size() != 2) {
			model.addAttribute("isSearchList", new Boolean(true));
		}

		JSONObject listSearch = JSONObjectUtil.getJSONObject(centerItemInfo, listSearchId);
//		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
//		if(itemInfoSearchList != null) {
//			searchList.addAll(itemInfoSearchList);
//			model.addAttribute("isSearchList", new Boolean(true));
//		}
		
		// 신청일 검색
		// 신청일을 검색할 때 queryString 파라미터에 이전 검색기록까지 같이 나와서 가장 최근에 검색한 파라미터 값을 가져오는 것으로 변경
		String searchKeyFlag = RbsProperties.getProperty("Globals.item.search.pre.flag");
		String dateRegi1ItemName = "dateRegi1";
		String[] dateRegi1Values =  request.getParameterValues(searchKeyFlag + dateRegi1ItemName);
		if(dateRegi1Values != null) {
			String dateRegi1 = dateRegi1Values[dateRegi1Values.length -1];
			if(!dateRegi1.isEmpty()) {
				param.put("REGI_DATE1", dateRegi1);
				model.addAttribute("isSearchList", new Boolean(true));
			}
		}
		String dateRegi2ItemName = "dateRegi2";
		String[] dateRegi2Values =  request.getParameterValues(searchKeyFlag + dateRegi2ItemName);
		if(dateRegi2Values != null) {
			String dateRegi2 = dateRegi2Values[dateRegi2Values.length -1];
			if(!dateRegi2.isEmpty()) {
				param.put("REGI_DATE2", dateRegi2);
				model.addAttribute("isSearchList", new Boolean(true));
			}
		}
		
		// 기간 검색
		// 기간을 검색할 때 queryString 파라미터에 이전 검색기록까지 같이 나와서 가장 최근에 검색한 파라미터 값을 가져오는 것으로 변경
		String date1ItemName = "date1";
		String[] date1Values =  request.getParameterValues(searchKeyFlag + date1ItemName);
		if(date1Values != null) {
			String date1 = date1Values[date1Values.length -1];
			if(!date1.isEmpty()) {
				param.put("START_DATE", date1);
				model.addAttribute("isSearchList", new Boolean(true));
			}
		}
		String date2ItemName = "date2";
		String[] date2Values =  request.getParameterValues(searchKeyFlag + date2ItemName);
		if(date2Values != null) {
			String date2 = date2Values[date2Values.length -1];
			if(!date2.isEmpty()) {
				param.put("END_DATE", date2);
				model.addAttribute("isSearchList", new Boolean(true));
			}
		}
		
		// 아이디 또는 이름으로 검색
		String keyFieldCode = queryString.getString("keyField");
		String keyCode = queryString.getString("key");
		if(!StringUtil.isEmpty(keyFieldCode) && !StringUtil.isEmpty(keyCode)) {
			if(keyFieldCode.equals("mbrId")) {
				param.put("KEY_FIELD", "MEMBER_ID");
			} else {
				param.put("KEY_FIELD", "MEMBER_NAME");
			}
			param.put("KEY", "%" + keyCode + "%");
			model.addAttribute("isSearchList", new Boolean(true));
		}
		param.put("searchList", searchList);

		// 2.2 목록수
    	totalCount = memberManageService.getTotalCenterAuthCount(param);
    	
    	if(totalCount > 0) {
    		
    		//권한그룹 select의 not in 조건에 부합하는 파라미터 설정
    		String[] usertype_idx_list = new String[] {"5", "10", "20", "55"};
    		authParam.put("USERTYPE_IDX_LIST", usertype_idx_list);
    		
    		

    		// 2.3 목록
    		list = memberManageService.getCenterAuthList(param);
    		authList = authManageService.getAuthGroupList(authParam);
    	}
    	
    	model.addAttribute("list", list);										// 목록
    	HashMap<String, Object> searchOptnHashMap = CodeHelper.getItemOptnSearchHashMap(listSearch);	// 검색항목코드

		// 사용자그룹 추가
		JSONArray searchOrder = JSONObjectUtil.getJSONArray(listSearch, "searchAddOptn_order");
		JSONObject keyInfo = JSONObjectUtil.getJSONObject(listSearch, "key");
		JSONObject keyItems = JSONObjectUtil.getJSONObject(keyInfo, "items");
		HashMap<String, Object> searchAddOptnHashMap = CodeHelper.getItemOptnHashMap(true, keyItems, searchOrder);
		if(searchAddOptnHashMap != null) {
			if(searchOptnHashMap == null) searchOptnHashMap = new HashMap<String, Object>();
			searchOptnHashMap.putAll(searchAddOptnHashMap);
		}
		model.addAttribute("searchOptnHashMap", searchOptnHashMap);	// 검색항목코드
		//model.addAttribute("URL_LISTCENTER", request.getAttribute("URL_LISTCENTER"));
		
		model.addAttribute("centerItemInfo", centerItemInfo);
		model.addAttribute("submitType", "centerList");
		model.addAttribute("authList", authList);
		
    	// 3. 경로 setting
    	// 3.1 기본경로
    	fn_setCommonPath(attrVO);
		
		return getViewPath("/centerExcel");
	}
	
	/**
	 * 엑셀 다운로드
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(value = "/employExcel.do")
	public String employExcel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		DataForm queryString = attrVO.getQueryString();
		
		String listSearchId = "employList_search";		// 검색설정
		
		// 2. DB
		int totalCount = 0;
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건
		
		// 항목설정으로 검색조건 setting
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		// 사용자 그룹 검색
		String searchKeyFlag = RbsProperties.getProperty("Globals.item.search.pre.flag");
		String mbrGrpItemName = "mbrGrp";
		String mbrGrpCd = queryString.getString(searchKeyFlag + mbrGrpItemName);
		if(!StringUtil.isEmpty(mbrGrpCd)) {
			param.put("mbrGrpCd", mbrGrpCd);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		param.put("searchList", searchList);

		// 2.2 목록수
    	totalCount = memberManageService.getTotalEmployRegiCount(param);
    	
    	if(totalCount > 0) {
    		
    		// 2.3 목록
    		list = memberManageService.getEmployRegiList(param);
    	}
    	
    	model.addAttribute("list", list);										// 목록
    	HashMap<String, Object> searchOptnHashMap = CodeHelper.getItemOptnSearchHashMap(listSearch);	// 검색항목코드

		// 사용자그룹 추가
		JSONArray searchOrder = JSONObjectUtil.getJSONArray(listSearch, "searchAddOptn_order");
		JSONObject keyInfo = JSONObjectUtil.getJSONObject(listSearch, "key");
		JSONObject keyItems = JSONObjectUtil.getJSONObject(keyInfo, "items");
		HashMap<String, Object> searchAddOptnHashMap = CodeHelper.getItemOptnHashMap(true, keyItems, searchOrder);
		if(searchAddOptnHashMap != null) {
			if(searchOptnHashMap == null) searchOptnHashMap = new HashMap<String, Object>();
			searchOptnHashMap.putAll(searchAddOptnHashMap);
		}
		model.addAttribute("searchOptnHashMap", searchOptnHashMap);	// 검색항목코드
		
		// 검색을 위한 지부지사 목록 가져오기
		List<Object> insttList = null;
		insttList = memberManageService.getInsttList();
		model.addAttribute("insttList", insttList);
		
    	// 3. 경로 setting
    	// 3.1 기본경로
    	fn_setCommonPath(attrVO);
		
		return getViewPath("/employExcel");
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
		
		PathUtil.fn_setCommonPath(queryString, baseParams, tabBaseParams, searchParams, null, null, pageName, idxName, listName, viewName, null, null, null, null, inputName, inputProcName,  deleteProcName, deleteListName, imageName, movieName, downloadName);
 		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		fn_setAddCommonPath(attrVO, request);
	}

	public void fn_setAddCommonPath(ModuleAttrVO attrVO, HttpServletRequest request) {
		String baseQueryString = StringUtil.getString(request.getAttribute("baseQueryString"));
		WebsiteVO usrSiteVO = (WebsiteVO) WebsiteDetailsHelper.getWebsiteInfo();
		String prePath = request.getContextPath() + "/" + request.getAttribute("crtSiteId");
		String memberSearchListName = "/member/usrSearchList.do";
		
		String listCorpName = "corpList.do";
		String listCorpProcName = "corpListProc.do";
		String inputCorpName = "inputCorp.do";
		String inputCorpProcName = "inputCorpProc.do";
		String inputDctCorp1ProcName = "inputDctCorp1Proc.do";
		String inputDctCorp2ProcName = "inputDctCorp2Proc.do";
		String inputDctCorp3ProcName = "inputDctCorp3Proc.do";
		
		String inputConsultName = "inputConsult.do";
		String inputConsultProcName = "inputConsultProc.do";
		
		String listCenterName = "centerList.do";
		String listCenterProcName = "centerListProc.do";
		String inputCenterName = "inputCenter.do";
		String inputCenterProcName = "inputCenterProc.do";
		String applyCenterProcName = "applyCenterProc.do";
		
		String listEmployName = "employList.do";
		String listEmployProcName = "employListProc.do";
		String inputFstEmployName = "inputFstEmploy.do";
		String inputSecEmployName = "inputSecEmploy.do";
		String inputEmployProcName = "inputEmployProc.do";
		String applyEmployProcName = "applyEmployProc.do";
		
		String excelName = "excel.do";
		String corpExcelName = "corpExcel.do";
		String centerExcelName = "centerExcel.do";
		String employExcelName = "employExcel.do";
		if(attrVO.isAdmMode()) {
			memberSearchListName = prePath + "/menuContents/" + usrSiteVO.getSiteId() + memberSearchListName;
 		}else {
 			memberSearchListName = prePath + memberSearchListName;
 		}
		
		String memberSearchListNameUrl = memberSearchListName;
		
		String listCorpNameUrl = listCorpName;
		String listCorpProcNameUrl = listCorpProcName;
		String inputCorpNameUrl = inputCorpName;
		String inputCorpProcNameUrl = inputCorpProcName;
		String inputDctCorp1ProcNameUrl = inputDctCorp1ProcName;
		String inputDctCorp2ProcNameUrl = inputDctCorp2ProcName;
		String inputDctCorp3ProcNameUrl = inputDctCorp3ProcName;
		
		String inputConsultNameUrl = inputConsultName;
		String inputConsultProcNameUrl = inputConsultProcName;
		
		String listCenterNameUrl = listCenterName;
		String inputCenterNameUrl = inputCenterName;
		String inputCenterProcNameUrl = inputCenterProcName;
		String applyCenterProcNameUrl = applyCenterProcName;
		
		String listEmployNameUrl = listEmployName;
		String inputFstEmployNameUrl = inputFstEmployName;
		String inputSecEmployNameUrl = inputSecEmployName;
		String inputEmployProcNameUrl = inputEmployProcName;
		String applyEmployProcNameUrl = applyEmployProcName;
		
		String excelNameUrl = excelName;
		String corpExcelNameUrl = corpExcelName;
		String centerExcelNameUrl = centerExcelName;
		String employExcelUrl = employExcelName;
		
		if(!StringUtil.isEmpty(baseQueryString)) {
			memberSearchListNameUrl += baseQueryString;
			
			listCorpNameUrl += baseQueryString;
			listCorpProcNameUrl += baseQueryString;
			inputCorpNameUrl += baseQueryString;
			inputCorpProcNameUrl += baseQueryString;
			inputDctCorp1ProcNameUrl += baseQueryString;
			inputDctCorp2ProcNameUrl += baseQueryString;
			inputDctCorp3ProcNameUrl += baseQueryString;
			
			inputConsultNameUrl += baseQueryString;
			inputConsultProcNameUrl += baseQueryString;
			
			listCenterNameUrl += baseQueryString;
			listCenterProcName += baseQueryString;
			inputCenterNameUrl += baseQueryString;
			inputCenterProcNameUrl += baseQueryString;
			applyCenterProcNameUrl += baseQueryString;
			
			listEmployNameUrl += baseQueryString;
			listEmployProcName += baseQueryString;
			inputFstEmployNameUrl += baseQueryString;
			inputSecEmployNameUrl += baseQueryString;
			inputEmployProcNameUrl += baseQueryString;
			applyEmployProcNameUrl += baseQueryString;
			
			excelNameUrl += baseQueryString;
			corpExcelNameUrl += baseQueryString;
			centerExcelNameUrl += baseQueryString;
			employExcelUrl += baseQueryString;
		}
		
		request.setAttribute("URL_LISTCORP", listCorpNameUrl);
		request.setAttribute("URL_LISTCORPPROC", listCorpProcNameUrl);
		request.setAttribute("URL_INPUTCORP", inputCorpNameUrl);
		request.setAttribute("URL_INPUTCORPPROC", inputCorpProcNameUrl);
		request.setAttribute("URL_INPUTDCTCORP1PROC", inputDctCorp1ProcNameUrl);
		request.setAttribute("URL_INPUTDCTCORP2PROC", inputDctCorp2ProcNameUrl);
		request.setAttribute("URL_INPUTDCTCORP3PROC", inputDctCorp3ProcNameUrl);
		
		request.setAttribute("URL_INPUTCONSULT", inputConsultNameUrl);
		request.setAttribute("URL_INPUTCONSULTPROC", inputConsultProcNameUrl);
		
		request.setAttribute("URL_LISTCENTER", listCenterNameUrl);
		request.setAttribute("URL_LISTCENTERPROC", listCenterProcName);
		request.setAttribute("URL_INPUTCENTER", inputCenterNameUrl);
		request.setAttribute("URL_INPUTCENTERPROC", inputCenterProcNameUrl);
		request.setAttribute("URL_APPLYCENTERPROC", applyCenterProcNameUrl);
		
		request.setAttribute("URL_LISTEMPLOY", listEmployNameUrl);
		request.setAttribute("URL_LISTEMPLOYPROC", listEmployProcName);
		request.setAttribute("URL_INPUTFSTEMPLOY", inputFstEmployNameUrl);
		request.setAttribute("URL_INPUTSECEMPLOY", inputSecEmployNameUrl);
		request.setAttribute("URL_INPUTEMPLOYPROC", inputEmployProcNameUrl);
		request.setAttribute("URL_APPLYEMPLOYPROC", applyEmployProcNameUrl);
		
		request.setAttribute("URL_EXCEL", excelNameUrl);
		request.setAttribute("URL_CORPEXCEL", corpExcelNameUrl);
		request.setAttribute("URL_CENTEREXCEL", centerExcelNameUrl);
		request.setAttribute("URL_EMPLOYEXCEL", employExcelUrl);
		
		request.setAttribute("URL_MBRSEARCHLIST", memberSearchListNameUrl);
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
			modiCnt = memberManageService.getAuthCount(param);
			
			return (modiCnt > 0);
		}
		
		return true;
	}
}
