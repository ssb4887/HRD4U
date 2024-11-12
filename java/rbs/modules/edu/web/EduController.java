package rbs.modules.edu.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.woowonsoft.egovframework.annotation.ModuleAttr;
import com.woowonsoft.egovframework.annotation.ModuleMapping;
import com.woowonsoft.egovframework.form.DTForm;
import com.woowonsoft.egovframework.form.DataForm;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.prop.RbsProperties;
import com.woowonsoft.egovframework.resource.RbsMessageSource;
import com.woowonsoft.egovframework.tag.ui.RbsPaginationInfo;
import com.woowonsoft.egovframework.util.CodeHelper;
import com.woowonsoft.egovframework.util.JSONObjectUtil;
import com.woowonsoft.egovframework.util.MessageUtil;
import com.woowonsoft.egovframework.util.ModuleUtil;
import com.woowonsoft.egovframework.util.PathUtil;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;
import com.woowonsoft.egovframework.web.ModuleController;

import egovframework.rte.fdl.property.EgovPropertyService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import rbs.egovframework.LoginVO;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.modules.doctor.service.DoctorService;
import rbs.modules.edu.service.EduDctService;

/**
 * 사용자(usr) 교육신청
 * 1. CEO 교육 신청
 * (사용자페이지 없음 : 2. 주치의 교육 신청)
 * @author minjung
 *
 */
@Controller
@ModuleMapping(moduleId="edu", confModule="edu")
@RequestMapping({"/{siteId}/edu", "/{admSiteId}/menuContents/{usrSiteId}/edu"})
public class EduController extends ModuleController {
	
	@Resource(name = "eduDctService")
	private EduDctService eduDctService;
	
	@Resource(name = "doctorService")
	private DoctorService doctorService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
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
	
	/******************************************* biz *******************************************/
	
	/**
	 * 교육과정 신청목록 불러오기
	 * @param attrVO
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/list.do")
	public String list(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAdmMode = attrVO.isAdmMode();
		DataForm queryString = attrVO.getQueryString();
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
		
		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		
		// 항목설정으로 검색조건 setting
		String listSearchId = "list_search";		// 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		
		// 탭코드
		String cateTabItemId = JSONObjectUtil.getString(settingInfo, "dset_cate_tab_id");
		String cateTabColumnId = JSONObjectUtil.getString(JSONObjectUtil.getJSONObject(items, cateTabItemId), "column_id");
		String cateTabMasterCode = JSONObjectUtil.getString(JSONObjectUtil.getJSONObject(items, cateTabItemId), "master_code");
		int cateTabObjectType = JSONObjectUtil.getInt(settingInfo, "dset_cate_tab_object_type");
		// 검색 코드
		HashMap<String, Object> searchOptnHashMap = CodeHelper.getItemOptnSearchHashMap(listSearch, cateTabItemId, cateTabColumnId, cateTabObjectType, cateTabMasterCode);
		// 목록 코드
		HashMap<String, Object> optnHashMap = getOptionHashMap(submitType, itemInfo, searchOptnHashMap);//CodeHelper.getItemOptnHashMap(items, itemOrder, searchOptnHashMap);
		// 탭 검색 조건
		List<DTForm> cateTabSearchList = ModuleUtil.getCateTabSearchList(settingInfo, items, queryString, optnHashMap);// fn_getCateTabSearchList(queryString, settingInfo, items, optnHashMap);
		if(cateTabSearchList != null){
			searchList.addAll(cateTabSearchList);
		}
		// 항목설정으로 검색조건 setting
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		param.put("searchList", searchList);
		
		// (web) 사용자페이지에서 '미공개'는 노출X
		param.put("siteId", attrVO.getSiteId());
		
		// setting_info의 edc_cd(교육코드)에 맞는 목록만 출력
		String edcCd = JSONObjectUtil.getString(settingInfo, "edc_cd");
		param.put("edcCd", edcCd);
		
		// 자신의 신청여부 체크를 위한 파라미터 추가
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		param.put("myMemberIdx", loginVO.getMemberIdx());
		
		totalCount = eduDctService.getEdcTotalCount(param);
		paginationInfo.setTotalRecordCount(totalCount);
		
		// 컨설턴트신청은 개인회원만(=기업회원 미만) 신청가능
//		if(loginVO != null) {
//			if(5 <= loginVO.getUsertypeIdx() && "CNST".equals(edcCd)) {
//				totalCount = 0;
//				response.setContentType("text/html; charset=utf-8;");
//				PrintWriter w = response.getWriter();
//				w.write("<script>alert('컨설턴트교육은 개인회원만 가능합니다. 기업회원의 경우, 개인회원 계정으로 재로그인해주세요.');history.go(-1);</script>");
//				w.flush();
//				w.close();
//				return getViewPath("/list");
//			}
//		}
		
		if(totalCount > 0) {
			if(usePaging == 1) {
				param.put("firstIndex", paginationInfo.getFirstRecordIndex());
				param.put("lastIndex", paginationInfo.getLastRecordIndex());
				param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
			}
			
			// 정렬컬럼 setting
			param.put("dsetOrderField", JSONObjectUtil.getString(settingInfo, "dset_order_field"));
			
			// 2.3 목록
			list = eduDctService.getEdcList(param);
		}
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("list", list);
		model.addAttribute("searchOptnHashMap", searchOptnHashMap);
		model.addAttribute("optnHashMap", optnHashMap);
		fn_setCommonPath(attrVO);
		return getViewPath("/list");
	}
	
	/**
	 * 교육과정 신청이력 불러오기
	 * @param attrVO
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/history.do")
	public String historyList(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		// 1. 로그인 정보 확인
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if(!loginVO.isLogin() || loginVO.getMemberIdx() == null || loginVO.getMemberIdx().isEmpty()) {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('사용자 정보를 찾을 수 없습니다. 다시 로그인해주세요.');history.go(-1);</script>");
			w.flush();
			w.close();
			return getViewPath("history");
		}
		
		// 본부직원 이상만 타인의 교육이력 열람가능(jsp)
		model.addAttribute("usertype", loginVO.getUsertypeIdx());
		
		boolean isAdmMode = attrVO.isAdmMode();
		DataForm queryString = attrVO.getQueryString();
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
		
		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		
		// 항목설정으로 검색조건 setting
		String listSearchId = "list_search";		// 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		
		// 탭코드
		String cateTabItemId = JSONObjectUtil.getString(settingInfo, "dset_cate_tab_id");
		String cateTabColumnId = JSONObjectUtil.getString(JSONObjectUtil.getJSONObject(items, cateTabItemId), "column_id");
		String cateTabMasterCode = JSONObjectUtil.getString(JSONObjectUtil.getJSONObject(items, cateTabItemId), "master_code");
		int cateTabObjectType = JSONObjectUtil.getInt(settingInfo, "dset_cate_tab_object_type");
		// 검색 코드
		HashMap<String, Object> searchOptnHashMap = CodeHelper.getItemOptnSearchHashMap(listSearch, cateTabItemId, cateTabColumnId, cateTabObjectType, cateTabMasterCode);
		// 목록 코드
		HashMap<String, Object> optnHashMap = getOptionHashMap(submitType, itemInfo, searchOptnHashMap);//CodeHelper.getItemOptnHashMap(items, itemOrder, searchOptnHashMap);
		// 탭 검색 조건
		List<DTForm> cateTabSearchList = ModuleUtil.getCateTabSearchList(settingInfo, items, queryString, optnHashMap);// fn_getCateTabSearchList(queryString, settingInfo, items, optnHashMap);
		if(cateTabSearchList != null){
			searchList.addAll(cateTabSearchList);
		}
		// 항목설정으로 검색조건 setting
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		param.put("searchList", searchList);

		// 자기자신의 신청이력 확인 파라미터 추가
		param.put("memberIdx", loginVO.getMemberIdx());
		
		// 파라미터 중 doctorIdx가 있는 경우, 해당 doctorIdx(주치의) 것만 보이도록 파라미터 수정
		if(request.getParameter("is_doctorIdx") != null && !request.getParameter("is_doctorIdx").isEmpty()) {
			// 본부직원(40) 미만은 특정주치의 교육이력 조회 불가
			if(loginVO.getUsertypeIdx() < 40) {
				response.setContentType("text/html; charset=utf-8;");
				PrintWriter w = response.getWriter();
				w.write("<script>alert('잘못된 접근입니다.');history.go(-1);</script>");
				w.flush();
				w.close();
				return getViewPath("/history");
			}
			param.put("memberIdx", request.getParameter("is_doctorIdx"));
		}
		
		// setting_info의 edc_cd(교육코드)에 맞는 목록만 출력
		String edcCd = JSONObjectUtil.getString(settingInfo, "edc_cd");
		param.put("edcCd", edcCd);
				
		totalCount = eduDctService.getMyEdcHistoryCount(param);
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
			list = eduDctService.getMyEdcHistoryList(param);
		}
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("list", list);
		model.addAttribute("searchOptnHashMap", searchOptnHashMap);
		model.addAttribute("optnHashMap", optnHashMap);
		fn_setCommonPath(attrVO);
		return getViewPath("/history");
	}
	
	/**
	 * 교육과정 신청 페이지 가져오기
	 * @param attrVO
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/input.do")
	public String input(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		if(request.getParameter("idx") == null || request.getParameter("idx").isEmpty()) {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('교육과정을 찾을 수 없습니다. 다시 시도해주세요.');history.go(-1);</script>");
			w.flush();
			w.close();
			return getViewPath("list");
		}
		
		// 1. 교육과정 정보 상세보기
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("edcIdx", request.getParameter("idx"));
		model.addAttribute("mId", request.getParameter("mId"));
		model.addAttribute("idx", request.getParameter("idx"));
		
		// 1-1. 교육과정 정보
		Map<String, Object> edc = eduDctService.getEdc(param);
		model.addAttribute("edc", edc);
		
		// 교육정보가 없음
		if(edc == null || edc.isEmpty()) {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('교육과정을 찾을 수 없습니다. 다시 시도해주세요.');history.go(-1);</script>");
			w.flush();
			w.close();
			return getViewPath("/list");
		}
		
		// 비공개거나 접수기간이 아님
		if(edc.get("RECPT_POSSIBLE_YN").equals("N")) {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('접수 가능기간이 아닙니다.');history.go(-1);</script>");
			w.flush();
			w.close();
			return getViewPath("/list");
		}
		
		// 1-2. 기신청 여부
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if(!loginVO.isLogin() || loginVO.getMemberIdx() == null || loginVO.getMemberIdx().isEmpty()) {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('로그인 정보를 찾을 수 없습니다. 다시 로그인해주세요.');history.go(-1);</script>");
			w.flush();
			w.close();
			return getViewPath("input");
		}
		param.put("memberIdx", loginVO.getMemberIdx());
		Map<String, Object> checkMember = eduDctService.getEdcMember(param);
		boolean check = true;
		if(checkMember == null || checkMember.get("ISDELETE").equals("1")) {
			check = true;
		} else {
			check = false;
		}
		model.addAttribute("check", check);
		
		fn_setCommonPath(attrVO);
		return getViewPath("/input");
	}
	
	/**
	 * 교육과정 상세 페이지 가져오기
	 * @param attrVO
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/view.do")
	public String view(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		if(request.getParameter("idx") == null || request.getParameter("idx").isEmpty()) {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('교육과정을 찾을 수 없습니다. 다시 시도해주세요.');history.go(-1);</script>");
			w.flush();
			w.close();
			return getViewPath("list");
		}
		
		// 1. 교육과정 정보 상세보기
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("edcIdx", request.getParameter("idx"));
		model.addAttribute("mId", request.getParameter("mId"));
		model.addAttribute("idx", request.getParameter("idx"));
		
		// 1-1. 교육과정 정보
		Map<String, Object> edc = eduDctService.getEdc(param);
		
		// 교육정보가 없음
		if(edc == null || edc.isEmpty() || edc.get("ISDELETE").equals("Y")) {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('교육과정을 찾을 수 없습니다. 다시 시도해주세요.');history.go(-1);</script>");
			w.flush();
			w.close();
			return getViewPath("/list");
		}
		
		// 미공개 상태인 교육
		if(edc != null && edc.get("OTHBC_YN").equals("N")) {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('교육과정을 찾을 수 없습니다. 다시 시도해주세요.');history.go(-1);</script>");
			w.flush();
			w.close();
			return getViewPath("/list");
		}
		
		// 1-2. 파일목록
		List<Map<String, Object>> fileList = eduDctService.getEdcFileList(param);
		model.addAttribute("edc", edc);
		model.addAttribute("fileList", fileList);
		
		// 1-3. 기신청 여부
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if(!loginVO.isLogin() || loginVO.getMemberIdx() == null || loginVO.getMemberIdx().isEmpty()) {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('로그인 정보를 찾을 수 없습니다. 다시 로그인해주세요.');history.go(-1);</script>");
			w.flush();
			w.close();
			return getViewPath("/view");
		}
		
		// 1-4. 접수가능여부
		boolean isValid = isValidForApply(edc);
		model.addAttribute("isValid", isValid);
		
		param.put("memberIdx", loginVO.getMemberIdx());
		Map<String, Object> checkMember = eduDctService.getEdcMember(param);
		boolean check = true;
		if(checkMember == null || checkMember.get("ISDELETE").equals("1")) {
			check = true;
		} else {
			check = false;
		}
		model.addAttribute("check", check);
		
		fn_setCommonPath(attrVO);
		return getViewPath("/view");
	}
	
	/**
	 * 교육과정 신청
	 * @param request
	 * @param formdata
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public ModelAndView registerMemberToEdc(HttpServletRequest request, @RequestParam Map<String, Object> formdata) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		// 결과(result)
		int result = 0;
		String msg = "";
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if(!loginVO.isLogin() || loginVO.getMemberIdx() == null || loginVO.getMemberIdx().isEmpty()) {
			result = 0;
			msg = "로그인 정보가 없습니다. 다시 로그인 해주세요.";
			mav.addObject("result", result);
			mav.addObject("msg", msg);
			return mav;
		}
		
		String memberIdx = loginVO.getMemberIdx();
		
		// 컨설턴트교육은 개인회원만 가능
		if("CNST".equals(formdata.get("edcCd"))) {
			if(5 <= loginVO.getUsertypeIdx()) {
				msg = "컨설턴트 교육은 개인회원만 신청가능합니다. 개인회원으로 신청해주세요.";
				mav.addObject("result", result);
				mav.addObject("msg", msg);
				return mav;
			}
		}
		
		formdata.put("memberIdx", memberIdx);
		formdata.put("confmStatus", 10); // 승인상태: 신청(10) [MASTER_CODE: EDC_CONFM_STATUS]
		
		// 접수불가기간 확인
		Map<String, Object> edc = eduDctService.getEdc(formdata);
		boolean isValid = isValidForApply(edc);
		if(!isValid) {
			msg = "접수기간이 아닙니다. 다시 확인해주세요.";
			mav.addObject("result", 0);
			mav.addObject("msg", msg);
			return mav;
		}
		
		result = eduDctService.insertEdcMember(formdata);
		if(result < 1) {
			msg = "신청 중 오류가 발생하였습니다. 다시 시도해주세요.";
		} else {
			msg = "신청이 완료되었습니다.";
		}
		mav.addObject("result", result);
		mav.addObject("msg", msg);
		return mav;
	}
	
	/**
	 * 교육 신청 취소하기
	 * @param request
	 * @param formdata
	 * @return
	 */
	@Auth(role = Role.D)
	@RequestMapping(value = "/cancel.do")
	public ModelAndView cancel(HttpServletRequest request, @RequestParam Map<String, Object> formdata) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		int result = 0;
		String msg = "";
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if(!loginVO.isLogin() || loginVO.getMemberIdx() == null || loginVO.getMemberIdx().isEmpty()) {
			result = 0;
			msg = "로그인 정보가 없습니다. 다시 로그인 해주세요.";
			mav.addObject("result", result);
			mav.addObject("msg", msg);
			return mav;
		}
		
		int[] deleteIdxs = {Integer.valueOf(loginVO.getMemberIdx())};
		formdata.put("deleteIdxs", deleteIdxs);
		formdata.put("LAST_MODI_IP", request.getRemoteAddr());
		result = eduDctService.deleteEdcMembers(formdata);
		if(result > 0) {
			msg = "신청 취소를 완료하였습니다.";
		} else {
			msg = "신청을 취소하던 중 문제가 발생하였습니다. 다시 시도해주세요.";
		}
		mav.addObject("result", result);
		mav.addObject("msg", msg);
		return mav;
	}
	
	/**
	 * 교육 수료증 출력
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/printCertificate.do", method = RequestMethod.POST)
	public String printCertificate(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		// 1. edcIdx(교육과정식별번호) 혹은 memberIdx(교육생식별번호)가 없는 경우
		if(request.getParameter("idx") == null || request.getParameter("member") == null || request.getParameter("idx").isEmpty() || request.getParameter("member").isEmpty()) {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('교육과정을 찾을 수 없습니다. 다시 시도해주세요.');history.go(-1);</script>");
			w.flush();
			w.close();
			return getViewPath("/history");
		}
		
		// 1-1. 자기것만 확인가능
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String paramMemberIdx = request.getParameter("member");
		String myMemberIdx = loginVO.getMemberIdx();
		if(!paramMemberIdx.equals(myMemberIdx)) {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('나의 수료증이 아닙니다. 다시 시도해주세요.');history.go(-1);</script>");
			w.flush();
			w.close();
			return getViewPath("/history");
		}
		
		// 2. 교육생 정보 + 교육과정 정보
		Map<String, Object> param = new HashMap<>();
		param.put("edcIdx", request.getParameter("idx"));
		param.put("memberIdx", request.getParameter("member"));
		
		Map<String, Object> member = eduDctService.getEdcMember(param);
		Map<String, Object> edc = eduDctService.getEdc(param);
		model.addAttribute("member", member);
		model.addAttribute("edc", edc);
		
		return getViewPath("/certificate");
	}
	
	/**
	 * 주치의 검색
	 * @param attrVO
	 * @param request
	 * @param formdata
	 * @param model
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/searchDoctor.do", method = RequestMethod.POST)
	public ModelAndView searchDoctor(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, @RequestParam Map<String, Object> formdata, ModelMap model) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);

		Map<String, Object> param = new HashMap<>();
		if(formdata.get("insttIdx") != null && !formdata.get("insttIdx").toString().isEmpty()) {
			param.put("INSTT_IDX", formdata.get("insttIdx"));
		}
		
		if(formdata.get("key") != null &&  !formdata.get("key").toString().isEmpty()) {
			param.put("KEY_FIELD", formdata.get("keyField"));
			param.put("KEY", "%" + formdata.get("key") + "%");
		}
		
		List<?> doctor = null;
		doctor = doctorService.getList(1, param);
		mav.addObject("doctor", doctor);
		
		return mav;
	}
	
	/******************************************* biz *******************************************/
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
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String baseQueryString = StringUtil.getString(request.getAttribute("baseQueryString"));
		if(!StringUtil.isEmpty(baseQueryString)) {
			listName += baseQueryString;
			inputName += baseQueryString;
			viewName += baseQueryString;
		}
		request.setAttribute("URL_LIST", listName);
		request.setAttribute("URL_INPUT", inputName);
		request.setAttribute("URL_VIEW", viewName);
	}

	/**
	 * 현재시간이 접수 유효한지 확인
	 * @param param
	 * @return
	 */
	private boolean isValidForApply(Map<String, Object> param) {
		Date startDate = (Date) param.get("RECPT_BGNDT");
		Date endDate = (Date) param.get("RECPT_ENDDT");
		Date currentDate = new Date();
		return currentDate.after(startDate) && currentDate.before(endDate);
	}
}
