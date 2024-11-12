package rbs.modules.develop.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

import rbs.egovframework.LoginVO;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.egovframework.common.CommonFunction;
import rbs.egovframework.confirmProgress.ConfirmProgress;
import rbs.modules.clinic.service.ClinicService;
import rbs.modules.develop.service.DevelopDctService;
import rbs.modules.develop.service.DevelopService;

import com.clipsoft.clipreport.engine.data.parameter.Parameter;
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
import com.woowonsoft.egovframework.web.ModuleController;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 샘플모듈<br/>
 * : 통합관리시스템 > 메뉴콘텐츠관리, 통합관리시스템 > 기능등록관리, 사용자 사이트 에서 사용
 * @author user
 *
 */
@Controller
@ModuleMapping(moduleId="developDct", confModule="develop", confSModule="developDct")
@RequestMapping({"/{siteId}/developDct", "/{admSiteId}/menuContents/{usrSiteId}/developDct"})
public class DevelopDctController extends ModuleController {
	
	@Resource(name = "developService")
	private DevelopService developService;
	
	@Resource(name = "developDctService")
	private DevelopDctService developDctService;
	
	@Resource(name = "clinicService")
	private ClinicService clinicService;	
	
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
	
	/***************************훈련과정 맞춤개발 신청관리 시작************************************/
	/**
	 * 표준개발 신청관리 목록 화면
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/develop_list_form.do")
	public String develop_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		
		int insttIdx = 0;
		int usertypeIdx = 0;
		int doctorIdx = 0;
		String clsfCd = "";
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		if(loginVO != null){
			insttIdx = StringUtil.getInt(loginVO.getInsttIdx());
			usertypeIdx = StringUtil.getInt(loginVO.getUsertypeIdx());
			doctorIdx = StringUtil.getInt(loginVO.getDoctorIdx());
			clsfCd = loginVO.getClsfCd();
		}
		
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
		//클리닉 신청서를 제출한 기업중 로그인한 계정의 지부지사에 해당하는 기업 list
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
		
		param.put("searchList", searchList);
		
		if(usertypeIdx == 40 || usertypeIdx == 55) {
			// 본부 직원일 때
			param.put("ISHEAD", 1);
		} else {
			// 지부지사 주치의/부장일 때 
			param.put("INSTT_IDX", insttIdx);
			
//			if(clsfCd.equals("N")) {
//				// 부장이 아닌 지부지사 주치의 일 때
//				param.put("DOCTOR_IDX", doctorIdx);
//			} else {
//				// 부장일 때
//				param.put("INSTT_IDX", insttIdx);
//			}
		}
		
		
		// 목록 수 및 목록 가져오기 
		// 2.2 목록수
    	totalCount = developDctService.getDevTotalCount(param);
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
    		list = developDctService.getDevList(param);
		}
		
    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
	
	/**
	 * 신청관리 목록에서 주치의 지원요청을 클릭했을 때 요청한 내용 가져오기
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.GET, value = "/develop_help_form.do")
	public ModelAndView develop_help_form(@ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		int devlopIdx = 0;
		DataMap dt = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		devlopIdx = StringUtil.getInt(request.getParameter("devlopIdx"));
		
		searchList.add(new DTForm("A.DEVLOP_IDX", devlopIdx));
		param.put("searchList", searchList);
		
		dt = developDctService.getHelpInfo("HRD_DEVLOP", param);
		
		String sidoName = (String) dt.get("SIDO_NAME");
		String ncsName = (String) dt.get("NCS_NAME");
		String insttName = (String) dt.get("INSTT_NAME");
		String cn = (String) dt.get("ETC_DEMAND_MATTER");
		String doctorOpinion = (String) dt.get("DOCTOR_OPINION");
		
		// textarea 줄바꿈 처리하기
		if(doctorOpinion != null) {
			doctorOpinion = doctorOpinion.replaceAll("<br>", "\r\n");
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		mav.addObject("sidoName", sidoName);
		mav.addObject("ncsName", ncsName);
		mav.addObject("insttName", insttName);
		mav.addObject("cn", cn);
		mav.addObject("doctorOpinion", doctorOpinion);
		
		return mav;
	}
	
	/**
	 * [훈련과정맞춤개발] 신청서 상세보기 화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/develop_view_form.do")
	public String develop_view_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();		
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		//신청정보 ,신청과정 정보 , 신청과정 상세정보 리스트 
		DataMap devlopList = null;
		DataMap tpList = null;
		List<Object> tpSubList = null;
				
		// 기업정보 
		DataMap corpInfo = null;		
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int devlopIdx = StringUtil.getInt(request.getParameter("devlopIdx"));				
		String BPL_NO = request.getParameter("bplNo");

		if(devlopIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "develop_form_column");										
		
		param.put("DEVLOP_IDX", devlopIdx);
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(99 , param);	
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "DEVLOP_IDX", devlopIdx));
		param.put("searchList", searchList);
		param.put("orderColumn", "TP_COURSE_IDX");
		
		devlopList = developService.getView("HRD_DEVLOP", param);
		tpList = developService.getView("HRD_DEVLOP_TP", param);				
		tpSubList = developService.getList("HRD_DEVLOP_TP_SUB", param);
		
		String confmStatus = (String) devlopList.get("CONFM_STATUS");
		String doctorOpinion = (String) devlopList.get("DOCTOR_OPINION");
		
		// 접수상태일 때만 줄바꿈 처리하기
		if(confmStatus.equals("30")) {
			// textarea 줄바꿈 처리하기
			if(doctorOpinion != null) {
				doctorOpinion = doctorOpinion.replaceAll("<br>", "\r\n");
				devlopList.put("DOCTOR_OPINION", doctorOpinion);
			}
		}

		if(tpList == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}			
				
		model.addAttribute("submitType", "develop_form_column");		
		model.addAttribute("corpInfo", corpInfo);			
		model.addAttribute("devlopList", devlopList);			
		model.addAttribute("tpList", tpList);			
		model.addAttribute("tpSubList", tpSubList);	
		model.addAttribute("optnHashMap", optnHashMap);	
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	
	/**
	 * 신청관리 주치의 지원접수하기
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 28
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/develop_supportAccept.do")
	public String develop_supportAccept(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("devlopIdx"));
		System.out.println(keyIdx);
		System.out.println(keyIdx);
		System.out.println(keyIdx);
		
		
		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 주치의 지원접수 상태로 setting
		String confmStatus = ConfirmProgress.SUPPORTACCEPT.getCode();
		
		// 주치의 지원접수 상태로 HRD_DEVLEOP 신청상태 update하기
		int updateDev = developDctService.updateStatus("HRD_DEVLOP", "DEVLOP_IDX", confmStatus, keyIdx, regiIp);
		if(updateDev > 0) {
			// HRD_DEVLEOP_CONFM 테이블에 주치의 지원접수 상태 insert하기
			int updateDevConfm = developDctService.insertConfm("HRD_DEVLOP_CONFM", "CONFM_IDX", confmStatus, keyIdx, regiIp);
			if(updateDevConfm > 0) {
				// 저장 성공
				// 기본경로
				fn_setCommonPath(attrVO);
				
				model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, "주치의 지원요청이 정상적으로 승인되었습니다.", "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
				return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
			}
		} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	/**
	 * 신청관리 상세보기 화면에서 접수하기
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 30
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/develop_accept.do")
	public String develop_accept(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("devlopIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 접수 상태로 setting
		String confmStatus = ConfirmProgress.ACCEPT.getCode();

		// 접수 상태로 HRD_DEVLEOP 신청상태 update하기
    	int updateDev = developDctService.updateStatus("HRD_DEVLOP", "DEVLOP_IDX", confmStatus, keyIdx, regiIp);
    	if(updateDev > 0) {
    		// HRD_DEVLEOP_CONFM 테이블에 접수 상태 insert하기
    		int updateDevConfm = developDctService.insertConfm("HRD_DEVLOP_CONFM", "CONFM_IDX", confmStatus, keyIdx, regiIp);
    		if(updateDevConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.accept"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 신청관리 상세보기 화면에서 반려처리하기
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 40
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/develop_return.do")
	public String develop_return(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("devlopIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("doctorOpinion");
		if(contents != null){
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("doctorOpinion", contents);
		}
		
		// 반려 상태로 setting
		String confmStatus = ConfirmProgress.RETURN.getCode();
		
		int updateDev = 0;
		
		// 반려 상태로 신청상태 및 주치의 의견 update하기
		updateDev = developDctService.updateStatusAndOpinion("HRD_DEVLOP", "DEVLOP_IDX", confmStatus, keyIdx, regiIp, parameterMap);
		
    	if(updateDev > 0) {
    		// HRD_DEVLOP_CONFM 테이블에 반려 상태 insert하기
    		int updateDevConfm = developDctService.insertConfm("HRD_DEVLOP_CONFM", "CONFM_IDX", confmStatus, keyIdx, regiIp);
    		if(updateDevConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.return"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 신청관리 주치의 지원 반려하기
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 42
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/develop_supportReturn.do")
	public String develop_supportReturn(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("devlopIdx"));
		
		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("doctorOpinion");
		if(contents != null){
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("doctorOpinion", contents);
		}
		
		// 반려 상태로 setting
		String confmStatus = ConfirmProgress.SUPPORTRETURN.getCode();
		
		int updateDev = 0;
		
		// 반려 상태로 신청상태 및 주치의 의견 update하기
		updateDev = developDctService.updateStatusAndOpinion("HRD_DEVLOP", "DEVLOP_IDX", confmStatus, keyIdx, regiIp, parameterMap);
		
		if(updateDev > 0) {
			// HRD_DEVLOP_CONFM 테이블에 반려 상태 insert하기
			int updateDevConfm = developDctService.insertConfm("HRD_DEVLOP_CONFM", "CONFM_IDX", confmStatus, keyIdx, regiIp);
			if(updateDevConfm > 0) {
				// 저장 성공
				// 기본경로
				fn_setCommonPath(attrVO);
				
				model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, "주치의 지원요청이 정상적으로 반려되었습니다.", "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
				return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
			}
		} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 신청관리 상세보기 화면에서 최종승인하기
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 55
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/develop_approve.do")
	public String develop_approve(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("devlopIdx"));
		
		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 1차승인 상태로 setting
		String confmStatus = ConfirmProgress.APPROVE.getCode();
		
		int updateDev = 0;
		
		updateDev = developDctService.updateStatus("HRD_DEVLOP", "DEVLOP_IDX", confmStatus, keyIdx, regiIp);
		
    	if(updateDev > 0) {
    		// HRD_DEVLOP_CONFM 테이블에 1차승인 상태 insert하기
    		int updateDevConfm = developDctService.insertConfm("HRD_DEVLOP_CONFM", "CONFM_IDX", confmStatus, keyIdx, regiIp);
    		if(updateDevConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.approve"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 신청관리 상세보기 화면에서 중도포기하기
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 80
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/develop_drop.do")
	public String develop_drop(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("devlopIdx"));
		
		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 1차승인 상태로 setting
		String confmStatus = ConfirmProgress.DROP.getCode();
		
		int updateDev = 0;
		
		updateDev = developDctService.updateStatus("HRD_DEVLOP", "DEVLOP_IDX", confmStatus, keyIdx, regiIp);
		
		if(updateDev > 0) {
			// HRD_DEVLOP_CONFM 테이블에 1차승인 상태 insert하기
			int updateDevConfm = developDctService.insertConfm("HRD_DEVLOP_CONFM", "CONFM_IDX", confmStatus, keyIdx, regiIp);
			if(updateDevConfm > 0) {
				// 저장 성공
				// 기본경로
				fn_setCommonPath(attrVO);
				
				model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.drop"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
				return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
			}
		} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
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
	@RequestMapping(value = "/develop_excel.do")
	public String develop_excel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		int insttIdx = 0;
		int usertypeIdx = 0;
		int doctorIdx = 0;
		String clsfCd = "";
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		if(loginVO != null){
			insttIdx = StringUtil.getInt(loginVO.getInsttIdx());
			usertypeIdx = StringUtil.getInt(loginVO.getUsertypeIdx());
			doctorIdx = StringUtil.getInt(loginVO.getDoctorIdx());
			clsfCd = loginVO.getClsfCd();
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "list");
		
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
		
		param.put("searchList", searchList);
		
		if(usertypeIdx == 40 || usertypeIdx == 55) {
			// 본부 직원일 때
			param.put("ISHEAD", 1);
		} else {
			// 지부지사 주치의/부장일 때 
			param.put("INSTT_IDX", insttIdx);
			
//			if(clsfCd.equals("N")) {
//				// 부장이 아닌 지부지사 주치의 일 때
//				param.put("DOCTOR_IDX", doctorIdx);
//			} else {
//				// 부장일 때
//				param.put("INSTT_IDX", insttIdx);
//			}
		}
		
		// 목록 수 및 목록 가져오기 
		list = developDctService.getDevList(param);
		
    	// 3. 속성 setting
    	model.addAttribute("list", list);															// 목록
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/excel");
	}
	
	/**
	 * [훈련과정표준개발] 표준개발 신청서 리포트 출력하기
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/develop_report_form.do")
	public String clipReport(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String devlopIdx = request.getParameter("devlopIdx");
		String BPL_NO = request.getParameter("bplNo");
		
    	// 5. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	// 기초진단지 번호 setting
    	request.setAttribute("DEVLOP_IDX", devlopIdx);
    	request.setAttribute("BPL_NO", BPL_NO);
    	
    	return getViewPath("/clipReport");
	}
	
	/**
	 * [훈련과정맞춤개발] 표준개발 추천훈련 화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/develop_recommend_form.do")
	public String develop_recommend_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		String BPL_NO = request.getParameter("bplNo");
		String devlopIdx = request.getParameter("devlopIdx");
		
		// 기업정보 
		DataMap corpInfo = null;		
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(99 , param);	
		
		long bsisCnsl = developService.getMaxBsisCnsl(BPL_NO);
		int rsltIdx = developService.getRsltIdxByBsiscnslIdx(bsisCnsl);
		List<Object> trainingRecommend = null;
		List<Object> trainingCodeList = new ArrayList<>();
		//로그인한 회원의 해당연도의 가장 최근 기초 컨설팅을 가져온다.(없으면 0 = AI훈련추천 api콜 / 있으면 기업HRD이음컨설팅 정보를 불러온다)
		if(bsisCnsl != 0) {
			trainingRecommend = developService.getRecommend(bsisCnsl);
//			trainingRecommend = (List<Object>) developService.getAiRecommendList("hrdk_edu_reco_system", BPL_NO, rsltIdx);
		}else {
			trainingRecommend = (List<Object>) developService.getAiRecommendList("hrdk_edu_develop_page", BPL_NO, 0);
			
			if(trainingRecommend.size() == 0) {
				model.addAttribute("message",MessageUtil.getAlert("해당기업의 사업장관리번호에 대한 AI추천 정보가 없습니다."));
				return RbsProperties.getProperty("Globals.fail.path");
			}
			
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObj = new JSONObject();
			//훈련과정 전체 리스트에 사용할 TP_IDX 리스트 parse
			for(int i = 0; i < trainingRecommend.size();i++) {
				jsonArray.add( trainingRecommend.get(i));				
			}
			for(int i = 0; i < jsonArray.size();i++) {
				jsonObj = jsonArray.getJSONObject(i);
				trainingCodeList.add(jsonObj.get("TP_IDX"));
			} 
			
		}
		
		// 3.2 속성 setting
		model.addAttribute("devlopIdx", devlopIdx);
		model.addAttribute("corpInfo", corpInfo);
		model.addAttribute("bsisCnsl", bsisCnsl);
		model.addAttribute("trainingRecommend", trainingRecommend);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/recommendView");
	}	
	
	/**
	 * [훈련과정표준개발]표준개발 추천 과정 상세보기 화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/develop_training_view_form.do")
	public String develop_training_view_form(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		
		Map<String, Object> param = new HashMap<String, Object>();
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "develop_info_column");
		// 선택한 과정의 idx
		int tpIdx = 0;
		int prtbizIdx = 0;		
		
		// 과정 정보 , 과정 상세정보 리스트 , 파일
		DataMap tpList = null;
		List<Object> tpSubList = null;
				
		// 기업정보 
		DataMap corpInfo = null;
		
		String BPL_NO = request.getParameter("bplNo");
		String devlopIdx = request.getParameter("devlopIdx");
		
		String isFromDevelop = request.getParameter("isFromDevelop");
		
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		tpIdx = StringUtil.getInt(request.getParameter("tpIdx"));
		prtbizIdx = StringUtil.getInt(request.getParameter("prtbizIdx"));		
		
		
		if(tpIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}else if(prtbizIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
													

		
		param.put("TP_IDX", tpIdx);
		param.put("PRTBIZ_IDX", prtbizIdx);
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(99 , param);	
		
		tpList = developService.getTpInfo(param);
		tpSubList = developService.getTpSubInfo(param);
		
		if(tpList == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
				
		model.addAttribute("devlopIdx", devlopIdx);			
		model.addAttribute("optnHashMap", optnHashMap);			
		model.addAttribute("corpInfo", corpInfo);			
		model.addAttribute("tpList", tpList);			
		model.addAttribute("tpSubList", tpSubList);			
		model.addAttribute("isFromDevelop", isFromDevelop);			
		model.addAttribute("multiFileHashMap", developService.getMultiFileHashMap("HRD_DGNS_PRTBIZ_TP_FILE", "TP_IDX", tpIdx));	// multi file 목록
		model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/trainingView");
	}
	
	/**
	 * [훈련과정표준개발] 표준개발 신청서 화면
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/develop_write_form.do")
	public String develop_write_form(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		String devlopIdx = request.getParameter("devlopIdx");
		String BPL_NO = request.getParameter("bplNo");
		
		// 선택한 과정의 idx
		int tpIdx = 0;
		int prtbizIdx = 0;
		
		// 추천 활용 / 과정 수정 flag
		String useFlag = null;
		
		// 과정 정보 , 과정 상세정보 리스트 , 파일
		DataMap tpList = null;
		List<Object> tpSubList = null;
				
		// 기업정보 
		DataMap corpInfo = null;
		// 지부지사 idx
		List<Object> insttList = null;
		
		DataMap devlopList = null;
		
		//리턴 페이지 이름
		String pageName = "";
		
		long bsisCnsl = developService.getMaxBsisCnsl(BPL_NO);
		
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		tpIdx = StringUtil.getInt(request.getParameter("tpIdx"));
		prtbizIdx = StringUtil.getInt(request.getParameter("prtbizIdx"));
		useFlag = StringUtil.getString(request.getParameter("useFlag"));		
		
		//해당 기업이 S-OJT선정 기업인지 확인 
		if(prtbizIdx == 1) {			
			int isSojt = developService.getIsSojt(BPL_NO);
			if(isSojt == 0) {
					model.addAttribute("message",MessageUtil.getAlert("해당기업은 SOJT선정 기업이 아닙니다."));
					return RbsProperties.getProperty("Globals.fail.path");
			}	
		}
		
		//S-OJT의 경우 일반으로 3회까지 신청가능.(맞춤개발에서의 일반 포함)
		//ex) 표준개발에서 일반 S-OJT 2회 신청 + 맞춤개발에서 일반 S-OJT 1회 신청 시 한도 끝
		if(prtbizIdx == 4) {
			String sojtAvailableFlag = developService.getSojtAvailableFlag(BPL_NO);
			if(sojtAvailableFlag.equals("N")) {
				model.addAttribute("message",MessageUtil.getAlert("해당연도의 [S-OJT 일반] 과정개발 신청한도를 초과하였습니다(연간 3회)."));
				return RbsProperties.getProperty("Globals.fail.path");
			}
		}
		
		
		if(useFlag.equals("use")) {
			pageName = "/recomUseWriteForm";			
		}else if(useFlag.equals("reuse")) {
			pageName = "/recomReuseWriteForm";			
		}else if(useFlag.equals("new")) {
			pageName = "/newWriteForm";					
			model.addAttribute("PRTBIZ_IDX",prtbizIdx);

				if(prtbizIdx == 1) {
					model.addAttribute("BIZ_TYPE2", "S-OJT");
					model.addAttribute("TRAINING_TYPE", "일반 훈련");
					model.addAttribute("TRAINING_METHOD", "S-OJT 훈련");
				}else if(prtbizIdx == 4) {
					model.addAttribute("BIZ_TYPE2", "사업주");
					model.addAttribute("TRAINING_TYPE", "사업주훈련");
					model.addAttribute("TRAINING_METHOD", "집체훈련");
				}

		}
		
		//신규작성일 경우 파라미터 널체크를 하지않는다
		if(!useFlag.equals("new")) {
			if(tpIdx <= 0) {
				return RbsProperties.getProperty("Globals.error.400.path");
			}else if(prtbizIdx <= 0) {
				return RbsProperties.getProperty("Globals.error.400.path");
			}
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "develop_form_column");										

		
		param.put("TP_IDX", tpIdx);
		param.put("PRTBIZ_IDX", prtbizIdx);
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(99 , param);	
		
		insttList = clinicService.getInsttList(param);
		
		//기업이 주치의 지원요청한 내역
		searchList.add(new DTForm("A." + "DEVLOP_IDX", devlopIdx));
		param.put("searchList", searchList);
		
		devlopList = developService.getView("HRD_DEVLOP", param);
		
		/*신규작성일 경우 가져올 훈련 과정 데이터가 없기때문에 건너뛴다.
		대신 이전 선택화면에서 입력받은 brpbiz_idx를 가지고 값을 따로 세팅한다.
		prtbiz_idx 
		1: 사업주
		4: S-OJT*/		
		if(!useFlag.equals("new")) {			
			tpList = developService.getTpInfo(param);
			tpSubList = developService.getTpSubInfo(param);

			if(tpList == null) {
				// 해당글이 없는 경우
				model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
				return RbsProperties.getProperty("Globals.fail.path");
			}			
		}
				
		model.addAttribute("submitType", "develop_form_column");			
		model.addAttribute("bsisCnsl", bsisCnsl);			
		model.addAttribute("useFlag", useFlag);			
		model.addAttribute("devlopIdx", devlopIdx);			
		model.addAttribute("corpInfo", corpInfo);			
		model.addAttribute("insttList", insttList);			
		model.addAttribute("tpList", tpList);			
		model.addAttribute("tpSubList", tpSubList);	
		model.addAttribute("devlopList", devlopList);	
		model.addAttribute("optnHashMap", optnHashMap);	
		model.addAttribute("multiFileHashMap", developService.getMultiFileHashMap("HRD_DGNS_PRTBIZ_TP_FILE", "TP_IDX", tpIdx));	// multi file 목록
		model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath(pageName);
	}
	
	/* 
	 * AI훈련추천 ajax 통신용
	 */ 
	@Auth(role = Role.C)
	@RequestMapping(method = RequestMethod.GET, value = "/develop_write_form.do", params="isAjax")
	public ModelAndView develop_write_form( @RequestParam("isAjax") String isAjax, @ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		String BPL_NO = StringUtil.getString(request.getParameter("BPL_NO"));
		long bsiscnslIdx = StringUtil.getInt(request.getParameter("bsiscnslIdx"));
		int rsltIdx = developService.getRsltIdxByBsiscnslIdx(bsiscnslIdx);
				
		List<Object> trainingRecommend = null;
		
		//기업HRD이음컨설팅 번호를 파라미터로 받은 후 api호출을 분기한다.
		if(rsltIdx == 0) {			
			trainingRecommend = (List<Object>) developService.getAiRecommendList("hrdk_edu_develop_page", BPL_NO, 0);			
		}else {
			//기업HRD이음컨설팅에서 [추천 시 저장된 훈련] + [추가 된 훈련까지] 같이 가져온다
			//저장된 훈련이 없을 시(2차 서비스 오픈 전 데이터) 다시 ai추천을 받는다.
			String EduRecoSystemValue = developService.getEduRecoSystemValue(BPL_NO, rsltIdx);
			
			if(EduRecoSystemValue == null) {
				EduRecoSystemValue = developService.getAiEduRecoSystemRawValue(BPL_NO, rsltIdx);				
			}
			
			trainingRecommend = (List<Object>) developService.getBsisWithAiRecommendList(EduRecoSystemValue, bsiscnslIdx);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		mav.addObject("trainingRecommend", trainingRecommend);
		
		return mav;
	}
	
	/**
	 * [훈련과정표준개발] 표준개발 검토요청
	 * 	ㄴ 상태값 : 33
	 * 	ㄴ CONSIDERREQUEST
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(method=RequestMethod.POST, value = "/develop_considerRequest.do")
	public String develop_considerRequest(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();		
		int prtbizIdx = 0;
		
		String BPL_NO = StringUtil.getString(request.getParameter("BPL_NO"));
		
		prtbizIdx = StringUtil.getInt(request.getParameter("prtbizIdx"));
		

		
		//S-OJT의 경우 일반으로 3회까지 신청가능.(맞춤개발에서의 일반 포함)
		//ex) 표준개발에서 일반 S-OJT 2회 신청 + 맞춤개발에서 일반 S-OJT 1회 신청 시 한도 끝
		if(prtbizIdx == 4) {
			String sojtAvailableFlag = developService.getSojtAvailableFlag(BPL_NO);
			if(sojtAvailableFlag.equals("N")) {
				model.addAttribute("message", "해당연도의 [S-OJT 일반] 과정개발 신청한도를 초과하였습니다(연간 3회).");
				return RbsProperties.getProperty("Globals.fail.path");
			}
		}

		// 1. 필수입력 체크
		// 1.1 항목설정
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		
		JSONArray developInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "develop_insert_column_order");				
		parameterMap.put("_itemInputTypeFlag", "develop_insert_column_order");
		// 1.2 필수입력 체크(develop_insert_column_order)
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, developInsertColumnOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		
		JSONArray developTpInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "develop_tp_insert_column_order");		
		parameterMap.put("_itemInputTypeFlag", "develop_tp_insert_column_order");
		// 1.2 필수입력 체크(develop_tp_insert_column_order)
		errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, developTpInsertColumnOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 2. DB

		int	developResult = 0;
		int developTpResult = 0;
		
		// 신청 상태로 setting
		String confmStatus = ConfirmProgress.CONSIDERREQUEST.getCode();
		
		// textarea 개행처리 하기
		String trSfe = (String) parameterMap.get("trSfe");
		String trGoal = (String) parameterMap.get("trGoal");
		String trnReqm = (String) parameterMap.get("trnReqm");
		trSfe = trSfe.replaceAll("\r\n", "</p>");
		trSfe = trSfe.replaceAll("\f", "<p>");
		parameterMap.put("trSfe", trSfe);
		trGoal = trGoal.replaceAll("\r\n", "</p>");
		trGoal = trGoal.replaceAll("\f", "<p>");
		parameterMap.put("trGoal", trGoal);
		trnReqm = trnReqm.replaceAll("\r\n", "</p>");
		trnReqm = trnReqm.replaceAll("\f", "<p>");
		parameterMap.put("trnReqm", trnReqm);
		
		int tpSubLength = StringUtil.getInt((String) parameterMap.get("tpSubLength"));
		for(int i = 0; i < tpSubLength; i++) {
			String cn = (String) parameterMap.get("cn" + i);
			if(cn != null) {
				cn = cn.replaceAll("\r\n", "<br>");
				parameterMap.put("cn"+i, cn);
			}
		}
		
		//검토요청은 이미 devlopIdx가 들어가있기때문에 DEVLOP테이블은 update, TP와 TP_SUB는 insert
		//HRD_DEV update
		developResult = developService.updateDevelopAsStatusCode(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, developInsertColumnOrder, confmStatus, "dct");
		//HRD_DEV_TP + HRD_DEV_TP_SUB insert
		if(developResult != 0) {
			developTpResult = developService.insertDevelopTpAndTpSub(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, developTpInsertColumnOrder);
		}
		
    	if(developTpResult > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, "정상적으로 검토요청 처리 되었습니다.", "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} else if(developTpResult == -1) {
    		// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) return fileFailView;
    	}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}

	/**
	 * [훈련과정표준개발] 표준개발 재검토요청
	 * 	ㄴ 상태값 : 33
	 * 	ㄴ CONSIDERREQUEST
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/develop_modify.do")
	public String develop_modify(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();		
		int prtbizIdx = 0;
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = (String) parameterMap.get("bplNo");		
		
		prtbizIdx = StringUtil.getInt(request.getParameter("prtbizIdx"));
		
		//S-OJT의 경우 일반으로 3회까지 신청가능.(맞춤개발에서의 일반 포함)
		//ex) 표준개발에서 일반 S-OJT 2회 신청 + 맞춤개발에서 일반 S-OJT 1회 신청 시 한도 끝
		if(prtbizIdx == 4) {
			String sojtAvailableFlag = developService.getSojtAvailableFlag(BPL_NO);
			if(sojtAvailableFlag.equals("N")) {
				model.addAttribute("message", "해당연도의 [S-OJT 일반] 과정개발 신청한도를 초과하였습니다(연간 3회).");
				return RbsProperties.getProperty("Globals.fail.path");
			}
		}

		// 1. 필수입력 체크
		// 1.1 항목설정
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		
		JSONArray developInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "develop_insert_column_order");				
		parameterMap.put("_itemInputTypeFlag", "develop_insert_column_order");
		// 1.2 필수입력 체크(develop_insert_column_order)
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, developInsertColumnOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		
		JSONArray developTpInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "develop_tp_insert_column_order");		
		parameterMap.put("_itemInputTypeFlag", "develop_tp_insert_column_order");
		// 1.2 필수입력 체크(develop_tp_insert_column_order)
		errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, developTpInsertColumnOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 2. DB

		int	developResult = 0;
		int developTpResult = 0;
		
		// 신청 상태로 setting
		String confmStatus = ConfirmProgress.CONSIDERREQUEST.getCode();
		
		// textarea 개행처리 하기
		String trSfe = (String) parameterMap.get("trSfe");
		String trGoal = (String) parameterMap.get("trGoal");
		String trnReqm = (String) parameterMap.get("trnReqm");
		trSfe = trSfe.replaceAll("\r\n", "</p>");
		trSfe = trSfe.replaceAll("\f", "<p>");
		parameterMap.put("trSfe", trSfe);
		trGoal = trGoal.replaceAll("\r\n", "</p>");
		trGoal = trGoal.replaceAll("\f", "<p>");
		parameterMap.put("trGoal", trGoal);
		trnReqm = trnReqm.replaceAll("\r\n", "</p>");
		trnReqm = trnReqm.replaceAll("\f", "<p>");
		parameterMap.put("trnReqm", trnReqm);
		
		int tpSubLength = StringUtil.getInt((String) parameterMap.get("tpSubLength"));
		for(int i = 0; i < tpSubLength; i++) {
			String cn = (String) parameterMap.get("cn" + i);
			if(cn != null) {
				cn = cn.replaceAll("\r\n", "<br>");
				parameterMap.put("cn"+i, cn);
			}
		}
		
		//검토요청은 이미 devlopIdx가 들어가있기때문에 DEVLOP,TP테이블은 update, TP_SUB는 insert
		//HRD_DEV update
		developResult = developService.updateDevelopAsStatusCode(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, developInsertColumnOrder, confmStatus, "dct");
		//HRD_DEV_TP update + HRD_DEV_TP_SUB insert
		developTpResult = developService.updateDevelopTpAndTpSub(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, developTpInsertColumnOrder);
		
    	if(developTpResult > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, "정상적으로 재검토요청 처리 되었습니다.", "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} else if(developTpResult == -1) {
    		// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) return fileFailView;
    	}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}

	/**
	 * [훈련과정표준개발] 신청서 수정 화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(value = "/develop_modify_form.do")
	public String develop_modify_form(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();		
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 선택한 과정의 idx
		int devlopIdx = 0;	
		
		//신청정보 ,신청과정 정보 , 신청과정 상세정보 리스트 
		DataMap devlopList = null;
		DataMap tpList = null;
		List<Object> tpSubList = null;
		
		// 기업정보 
		DataMap corpInfo = null;	
		
		// 지부지사 idx
		List<Object> insttList = null;
		
		String BPL_NO = (String) parameterMap.get("bplNo");
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		devlopIdx = StringUtil.getInt(request.getParameter("devlopIdx"));				
		
		if(devlopIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "develop_modify_form_column");										
		
		param.put("DEVLOP_IDX", devlopIdx);
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(99 , param);	
		
		insttList = clinicService.getInsttList(param);
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "DEVLOP_IDX", devlopIdx));
		param.put("searchList", searchList);
		param.put("orderColumn", "TP_COURSE_IDX");
		
		devlopList = developService.getView("HRD_DEVLOP", param);
		tpList = developService.getView("HRD_DEVLOP_TP", param);				
		tpSubList = developService.getList("HRD_DEVLOP_TP_SUB", param);
		
		if(tpList == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		// 과정활용, 과정수정, 신규작성 구분하기
		String tpDevlopSe = (String) devlopList.get("TP_DEVLOP_SE");
		String pageName = "";
		String useFlag = "";
		
		if(tpDevlopSe.equals("use")) {
			pageName = "/useModifyForm";	
			useFlag = "use";
		} else {
			pageName = "/modifyForm";
			useFlag = "modify";
		}
		
		model.addAttribute("submitType", "develop_form_column");		
		model.addAttribute("useFlag", useFlag);			
		model.addAttribute("corpInfo", corpInfo);			
		model.addAttribute("devlopList", devlopList);			
		model.addAttribute("insttList", insttList);			
		model.addAttribute("tpList", tpList);			
		model.addAttribute("tpSubList", tpSubList);	
		model.addAttribute("optnHashMap", optnHashMap);	
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
		// 4. 기본경로
		fn_setCommonPath(attrVO);
		
		return getViewPath(pageName);
	}
	
	/**
	 * [훈련과정표준개발] 종합이력 화면
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/total_list_form.do")
	public String total_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();		
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		
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
		List<Object> totalList = null;
		List<Object> noHandlingList = null;
		DataMap corpInfo = null;
		DataMap insttSummary = null;
		DataMap doctorSummary = null;
		DataMap totalSummary = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		int insttIdx = 0;
		int usertypeIdx = 0;
		int doctorIdx = 0;
		String clsfCd = null;
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		if(loginVO != null){
			insttIdx = StringUtil.getInt(loginVO.getInsttIdx());
			usertypeIdx = StringUtil.getInt(loginVO.getUsertypeIdx());
			doctorIdx = StringUtil.getInt(loginVO.getDoctorIdx());
			clsfCd = loginVO.getClsfCd();
		}
		
		if(usertypeIdx != 40) {
			// 지부지사 부장&주치의일 때
			// 소속기관의 훈련과정 집계 가져오기
			param.put("INSTT_IDX", insttIdx);
			insttSummary = developDctService.getSummary(param);
			
			param.clear();
			
			// 주치의의 훈련과정 집계 가져오기
//			param.put("DOCTOR_IDX", doctorIdx);
//			doctorSummary = developDctService.getSummary(param);
			
			// 미처리 상태인 과정개발목록 가져오기(표준, 맞춤 전부다)
//			noHandlingList = developDctService.getNoHandlingList(param);
			
			// 지부지사 주치의/부장일 때 
			param.put("INSTT_IDX", insttIdx);
			
//			if(clsfCd.equals("Y")) {
//				// 부장일 때는 지부지사의 과정개발 목록 전부 가져오기
//				searchList.add(new DTForm("A.INSTT_IDX", insttIdx));
//			} else {
//				// 주치의일 때는 자기가 주치의인 기업의 과정개발 목록만 가져오기
//				searchList.add(new DTForm("A.DOCTOR_IDX", doctorIdx));
//			}
		} else {
			// 본부 직원일 때는 모든 지부지사의 훈련과정 집계 가져오기
			totalSummary = developDctService.getSummary(param);
		}
		
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
		
		param.put("searchList", searchList);

		int totalCount = 0;
    	totalCount = developService.getTotalListCount(param);
		paginationInfo.setTotalRecordCount(totalCount);
    	if(totalCount > 0) {
    		if(usePaging == 1) {
	    		param.put("firstIndex", paginationInfo.getFirstRecordIndex());
	    		param.put("lastIndex", paginationInfo.getLastRecordIndex());
	    		param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
	    	}
    	}
				
		// 종합이력 목록 
		totalList = developService.getTotalList(param);
		
    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);											// 페이징정보
    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);										
    	model.addAttribute("corpInfo", corpInfo);														// 기업정보
    	model.addAttribute("totalList", totalList);														// 종합이력
    	model.addAttribute("noHandlingList", noHandlingList);											// 미처리 상태인 과정개발 목록
    	model.addAttribute("totalSummary", totalSummary);												// 모든 지부지사의 훈련과정 개발 집계
    	model.addAttribute("insttSummary", insttSummary);												// 주치의가 소속된 지부지사의 훈련과정 개발 집계
//    	model.addAttribute("doctorSummary", doctorSummary);												// 주치의의 훈련과정 개발 집계
    	model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
    	model.addAttribute("optnHashMap", getOptionHashMap("list", itemInfo));    	

    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
	
	/**
	 * [훈련과정표준개발] 훈련과정 목록 화면
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/develop_training_list_form.do")
	public String develop_training_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		
		int devlopIdx = StringUtil.getInt(request.getParameter("devlopIdx"));
		String bplNo = request.getParameter("bplNo");

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
		//로그인한 계정의 신청서 리스트
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건
		
		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		
		// 항목설정으로 검색조건 setting
		String listSearchId = "training_list_search";		// 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		
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
		param.put("searchList", searchList);		
		// ai추천 훈련과정 tpIdx 리스트 가져오기
		String tpList = "";
		tpList = request.getParameter("tpList");
		String isFromDevelop = request.getParameter("isFromDevelop");
		
		param.put("tpList", tpList);
		param.put("isFromDevelop", isFromDevelop);
		
		// 목록 수 및 목록 가져오기 
		// 2.2 목록수
		totalCount = developService.getTrainingListCount(param);
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
			list = developService.getTrainingList(param);
		}
		
		// 3. 속성 setting
		model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
		model.addAttribute("list", list);															// 목록
		model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("devlopIdx", devlopIdx);
		model.addAttribute("isFromDevelop", isFromDevelop);
		model.addAttribute("bplNo", bplNo);
		model.addAttribute("tpList", tpList);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
		// 4. 기본경로
		fn_setCommonPath(attrVO);
		
		return getViewPath("/trainingList");
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
	@RequestMapping(value = "/total_excel.do")
	public String total_excel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		int insttIdx = 0;
		int usertypeIdx = 0;
		int doctorIdx = 0;
		String clsfCd = "";
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		if(loginVO != null){
			insttIdx = StringUtil.getInt(loginVO.getInsttIdx());
			usertypeIdx = StringUtil.getInt(loginVO.getUsertypeIdx());
			doctorIdx = StringUtil.getInt(loginVO.getDoctorIdx());
			clsfCd = loginVO.getClsfCd();
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "list");
		
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
		
		if(usertypeIdx != 40) {
			// 지부지사 주치의/부장일 때 
			param.put("INSTT_IDX", insttIdx);
			
//			if(clsfCd.equals("Y")) {
//				// 부장일 때는 지부지사의 과정개발 목록 전부 가져오기
//				searchList.add(new DTForm("A.INSTT_IDX", insttIdx));
//			} else {
//				// 주치의일 때는 자기가 주치의인 기업의 과정개발 목록만 가져오기
//				searchList.add(new DTForm("A.DOCTOR_IDX", doctorIdx));
//			}
		} 
		
		param.put("searchList", searchList);
		
		// 목록 수 및 목록 가져오기 
		list = developService.getTotalList(param);
		
    	// 3. 속성 setting
    	model.addAttribute("list", list);															// 목록
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/excel");
	}
	
	/**
	 * [훈련과정표준개발] 맞춤개발 비용관리 목록 화면
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/support_list_form.do")
	public String support_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
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
		//로그인한 계정의 신청서 리스트
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
		
		// 사업연도 검색
		String yearItemName = "is_startYear";
		String startYear = queryString.getString(yearItemName);
		if(!StringUtil.isEmpty(startYear)) {
			param.put("YEAR", startYear);
			model.addAttribute("isSearchList", new Boolean(true));
			model.addAttribute("startYear", startYear);
		}
		
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
		param.put("searchList", searchList);		
		
		int insttIdx = 0;
		int usertypeIdx = 0;
		int doctorIdx = 0;
		String memberIdx = "";
		String clsfCd = null;
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		if(loginVO != null){
			insttIdx = StringUtil.getInt(loginVO.getInsttIdx());
			usertypeIdx = StringUtil.getInt(loginVO.getUsertypeIdx());
			doctorIdx = StringUtil.getInt(loginVO.getDoctorIdx());
			memberIdx = StringUtil.getString(loginVO.getMemberIdx());
			clsfCd = loginVO.getClsfCd();
		}
		
		// 지부지사 부장&주치의일 때 검색 조건 추가 (본부 직원은 모든 지부지사의 비용청구 신청 목록 가져옴)
		if(usertypeIdx != 40) {
			// 지부지사 주치의/부장일 때 
			param.put("INSTT_IDX", insttIdx);
						
//			if(clsfCd.equals("Y")) {
//				// 지부지사 부장일 때는 지부지사의 모든  비용청구 신청 목록 가져오기
//				searchList.add(new DTForm("A.CMPTNC_BRFFC_IDX", insttIdx));
//			} else {
//				// 주치의일 때는 자기가 주치의인 기업의 비용청구 신청 목록만 가져오기(memberIdx로)
//				searchList.add(new DTForm("A.CMPTNC_BRFFC_PIC_IDX", memberIdx));
//			}
		} 
		
		// 목록 수 및 목록 가져오기 
		// 2.2 목록수
		totalCount = developDctService.getTotalSupportCount(param);
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
			list = developDctService.getSupportList(param);
		}
		
		// 3. 속성 setting
		model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
		model.addAttribute("list", list);															// 목록
		model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
		// 4. 기본경로
		fn_setCommonPath(attrVO);
		
		return getViewPath("/list");
	}
	
	/**
	 * 맞춤개발 비용관리 목록 엑셀 다운로드
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/support_excel.do")
	public String support_excel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		int insttIdx = 0;
		int usertypeIdx = 0;
		int doctorIdx = 0;
		String clsfCd = "";
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		if(loginVO != null){
			insttIdx = StringUtil.getInt(loginVO.getInsttIdx());
			usertypeIdx = StringUtil.getInt(loginVO.getUsertypeIdx());
			doctorIdx = StringUtil.getInt(loginVO.getDoctorIdx());
			clsfCd = loginVO.getClsfCd();
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "list");
		
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
		
		// 사업연도 검색
		String yearItemName = "is_startYear";
		String startYear = queryString.getString(yearItemName);
		if(!StringUtil.isEmpty(startYear)) {
			param.put("YEAR", startYear);
			model.addAttribute("isSearchList", new Boolean(true));
			model.addAttribute("startYear", startYear);
		}
		
		// 지부지사 부장&주치의일 때 검색 조건 추가 (본부 직원은 모든 지부지사의 비용청구 신청 목록 가져옴)
		if(usertypeIdx != 40) {
			// 지부지사 주치의/부장일 때 
			param.put("INSTT_IDX", insttIdx);
						
//			if(clsfCd.equals("Y")) {
//				// 지부지사 부장일 때는 지부지사의 모든  비용청구 신청 목록 가져오기
//				searchList.add(new DTForm("A.INSTT_IDX", insttIdx));
//			} else {
//				// 주치의일 때는 자기가 주치의인 기업의 비용청구 신청 목록만 가져오기
//				searchList.add(new DTForm("A.DOCTOR_IDX", doctorIdx));
//			}
		}
		
		param.put("searchList", searchList);
		
		// 목록 수 및 목록 가져오기 
		list = developService.getSupportList(param);
		
    	// 3. 속성 setting
    	model.addAttribute("list", list);															// 목록
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/excel");
	}
	
	/**
	 * 맞춤개발 비용관리 상세보기 화면에서 접수하기
	 *   ㄴ fnIdx : 2
	 *   ㄴ CONFM_STATUS : 30
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/support_accept.do")
	public String support_accept(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("ctIdx"));
		int cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 접수 상태로 setting
		String confmStatus = ConfirmProgress.ACCEPT.getCode();

		// 접수 상태로 HRD_CNSL_COST 신청상태 update하기
    	int updateCost = developDctService.updateStatusCost(confmStatus, keyIdx, cnslIdx, regiIp);
    	if(updateCost > 0) {
    		// HRD_CNSL_COST_CONFM 테이블에 접수 상태 insert하기
    		int updateCostConfm = developDctService.insertConfmCost(confmStatus, keyIdx, cnslIdx, regiIp);
    		if(updateCostConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.accept"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 맞춤개발 비용관리 상세보기 화면에서 반려처리하기
	 *   ㄴ fnIdx : 2
	 *   ㄴ CONFM_STATUS : 40
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/support_return.do")
	public String support_return(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("ctIdx"));
		int cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("doctorOpinion");
		if(contents != null){
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("doctorOpinion", contents);
		}
		
		// 반려 상태로 setting
		String confmStatus = ConfirmProgress.RETURN.getCode();
		
		int updateCost = 0;
		
		// 반려 상태로 신청상태 및 주치의 의견 update하기
		updateCost = developDctService.updateStatusAndOpinionCost(confmStatus, keyIdx, cnslIdx, regiIp, parameterMap);
		
    	if(updateCost > 0) {
    		// HRD_CNSL_COST_CONFM 테이블에 반려 상태 insert하기
    		int updateCostConfm = developDctService.insertConfmCost(confmStatus, keyIdx, cnslIdx, regiIp);
    		if(updateCostConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.return"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 맞춤개발 비용관리 상세보기 화면에서 1차승인하기
	 *   ㄴ fnIdx : 2
	 *   ㄴ CONFM_STATUS : 50
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/support_firstApprove.do")
	public String support_firstApprove(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("ctIdx"));
		int cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));
		
		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 1차승인 상태로 setting
		String confmStatus = ConfirmProgress.FIRSTAPPROVE.getCode();
		
		int updateCost = 0;
		
		updateCost = developDctService.updateStatusCost(confmStatus, keyIdx, cnslIdx, regiIp);
		
    	if(updateCost > 0) {
    		// HRD_CNSL_COST_CONFM 테이블에 1차승인 상태 insert하기
    		int updateCostConfm = developDctService.insertConfmCost(confmStatus, keyIdx, cnslIdx, regiIp);
    		if(updateCostConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, "정상적으로 1차승인 되었습니다.", "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 맞춤개발 비용관리 상세보기 화면에서 부장이 최종승인 안하고 반려하기 > 접수 상태로 돌아감
	 *   ㄴ fnIdx : 2
	 *   ㄴ CONFM_STATUS : 30
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/support_returnToAccept.do")
	public String support_returnToAccept(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("ctIdx"));
		int cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));
		
		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("doctorOpinion");
		if(contents != null){
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("doctorOpinion", contents);
		}
		
		// 접수 상태로 setting
		String confmStatus = ConfirmProgress.ACCEPT.getCode();
		
		int updateCost = 0;
		
		// 접수 상태로 신청상태 및 주치의 의견 update하기
		updateCost = developDctService.updateStatusAndOpinionCost(confmStatus, keyIdx, cnslIdx, regiIp, parameterMap);
		
		if(updateCost > 0) {
			// HRD_CNSL_COST_CONFM 테이블에 접수 상태 insert하기
			int updateCostConfm = developDctService.insertConfmCost(confmStatus, keyIdx, cnslIdx, regiIp);
			if(updateCostConfm > 0) {
				// 저장 성공
				// 기본경로
				fn_setCommonPath(attrVO);
				
				model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.return"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
				return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
			}
		} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 맞춤개발 비용관리 상세보기 화면에서 최종승인하기
	 *   ㄴ fnIdx : 2
	 *   ㄴ CONFM_STATUS : 55
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/support_approve.do")
	public String support_approve(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("ctIdx"));
		int cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));
		
		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 최종승인 상태로 setting
		String confmStatus = ConfirmProgress.APPROVE.getCode();
		
		int updateDev = 0;
		
		updateDev = developDctService.updateStatusCost(confmStatus, keyIdx, cnslIdx, regiIp);
		
    	if(updateDev > 0) {
    		// HRD_CNSL_COST_CONFM 테이블에 1차승인 상태 insert하기
    		int updateDevConfm = developDctService.insertConfmCost(confmStatus, keyIdx, cnslIdx, regiIp);
    		if(updateDevConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.approve"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 맞춤개발 비용관리 상세보기 화면에서 중도포기하기
	 *   ㄴ fnIdx : 2
	 *   ㄴ CONFM_STATUS : 80
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/support_drop.do")
	public String support_drop(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("ctIdx"));
		int cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));
		
		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 1차승인 상태로 setting
		String confmStatus = ConfirmProgress.DROP.getCode();
		
		int updateDev = 0;
		
		updateDev = developDctService.updateStatusCost(confmStatus, keyIdx, cnslIdx, regiIp);
		
		if(updateDev > 0) {
			// HRD_DEVLOP_CONFM 테이블에 1차승인 상태 insert하기
			int updateDevConfm = developDctService.insertConfmCost(confmStatus, keyIdx, cnslIdx, regiIp);
			if(updateDevConfm > 0) {
				// 저장 성공
				// 기본경로
				fn_setCommonPath(attrVO);
				
				model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.drop"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
				return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
			}
		} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * [훈련과정표준개발] 맞춤개발 비용청구 상세보기 화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/support_view_form.do")
	public String support_view_form(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();		
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 선택한  cnslIdx
		int cnslIdx = 0;
		// ctIdx
		int ctIdx = 0;
		
		DataMap cnslReportInfo = null;
		
		//신청정보 ,신청과정 정보 , 신청과정 상세정보 리스트 
		DataMap cnslCostInfo = null;
		List<Object> cnslCostSubList = null;
				
		// 기업정보 
		DataMap corpInfo = null;		
		
		
		String BPL_NO =  StringUtil.getString(request.getParameter("bplNo"));	
		
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));				
		ctIdx = StringUtil.getInt(request.getParameter("ctIdx"));				
		

		if(cnslIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "view");										

		
		param.put("CNSL_IDX", cnslIdx);
		param.put("BPL_NO", BPL_NO);
		param.put("CT_IDX", ctIdx);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(99 , param);	
		
		cnslReportInfo = developService.getCnslReportInfo(param);
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CNSL_IDX", cnslIdx));
		searchList.add(new DTForm("A." + "CT_IDX", ctIdx));
		param.put("searchList", searchList);
		param.put("orderColumn", "CNSL_IDX");
		
		cnslCostInfo = developService.getView("HRD_CNSL_COST", param);			
		cnslCostSubList = developService.getList("HRD_CNSL_COST_SUB", param);

		if(cnslCostSubList == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}			
		
				
		model.addAttribute("submitType", "view");		
		model.addAttribute("corpInfo", corpInfo);			
		model.addAttribute("dt", cnslCostInfo);			
		model.addAttribute("cnslReportInfo", cnslReportInfo);			
		model.addAttribute("cnslCostSubList", cnslCostSubList);	
		model.addAttribute("optnHashMap", optnHashMap);	
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		model.addAttribute("multiFileHashMap", developService.getMultiFileHashMap("HRD_CNSL_COST_FILE", "CNSL_IDX", cnslIdx));	// multi file 목록

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	/**
	 * [훈련과정표준개발] 맞춤개발 비용청구 수정 화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(value = "/support_modify_form.do")
	public String support_modify_form(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 선택한 신청서의 컨설팅 idx
		int cnslIdx = 0;
		int ctIdx = 0;
		
		// 컨설팅 리포트 리스트 / 팀 리스트
		DataMap cnslReportInfo = null;
		DataMap cnslCostInfo = null;
		List<Object> cnslTeamList = null;
		List<Object> corpTpList = null;
				
		// 기업정보 
		DataMap corpInfo = null;	
		
		// 지부지사 idx
		List<Object> insttList = null;
		
		String BPL_NO =  StringUtil.getString(request.getParameter("bplNo"));
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		ctIdx = StringUtil.getInt(request.getParameter("ctIdx"));				
		cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));				
		
		if(cnslIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "modify");										
		
		param.put("CNSL_IDX", cnslIdx);
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(99 , param);	
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CNSL_IDX", cnslIdx));
		param.put("searchList", searchList);
		
		cnslReportInfo = developService.getCnslReportInfo(param);
		
		searchList.add(new DTForm("A." + "CT_IDX", ctIdx));		
		param.put("searchList", searchList);
		
		cnslCostInfo = developService.getView("HRD_CNSL_COST",param);
		
		param.put("orderColumn", "TEAM_IDX");
		cnslTeamList = developService.getList("HRD_CNSL_COST_SUB",param);
		corpTpList = developService.getCorpTpList(param);
		
		if(cnslCostInfo == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}			
		
		model.addAttribute("submitType", "modify");		
		model.addAttribute("corpInfo", corpInfo);			
		model.addAttribute("dt", cnslCostInfo);			
		model.addAttribute("insttList", insttList);			
		model.addAttribute("cnslReportInfo", cnslReportInfo);			
		model.addAttribute("cnslTeamList", cnslTeamList);	
		model.addAttribute("corpTpList", corpTpList);	
		model.addAttribute("optnHashMap", optnHashMap);	
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		model.addAttribute("multiFileHashMap", developService.getMultiFileHashMap("HRD_CNSL_COST_FILE", "CT_IDX", ctIdx));	// multi file 목록
		
		// 4. 기본경로
		fn_setCommonPath(attrVO);
		
		return getViewPath("/input");
	}
	
	/**
	 * 주치의가 지원금 신청서 수정 (신청상태는 변하지 않음)	
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/support_modify.do")
	public String support_modify(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		boolean isAdmMode = attrVO.isAdmMode();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int cnslIdx = StringUtil.getInt((String) parameterMap.get("cnslIdx"));
		int keyIdx = StringUtil.getInt((String) parameterMap.get("ctIdx"));

		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 수정 후 상세보기 화면으로 이동하기 위해 사업장관리번호 가져오기
		String bplNo = (String) parameterMap.get("bplNo");
		
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		searchList.add(new DTForm("A.CT_IDX", keyIdx));
		searchList.add(new DTForm("A.CNSL_IDX", cnslIdx));
		param.put("searchList", searchList);

		dt = developDctService.getView("HRD_CNSL_COST", param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.contents")));
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
		int updateSpt = 0;
		
		updateSpt = developDctService.update("HRD_CNSL_COST", "CT_IDX", uploadModulePath, keyIdx, request.getRemoteAddr(), parameterMap, attrVO);
    	if(updateSpt > 0) {
    		// 4.1 저장 성공
        	// 기본경로
			fn_setCommonPath(attrVO);
			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.update"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_SUPPORT_VIEW_FORM_URL") + "&cnslIdx=" + cnslIdx + "&ctIdx=" + keyIdx + "&bplNo=" + bplNo)) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/***************************훈련과정 맞춤개발 신청관리 끝************************************/
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
	public String list(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
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
		
		param.put("searchList", searchList);
		
		// 2.2 목록수
    	totalCount = developDctService.getTotalCount(fnIdx, param);
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
    		list = developDctService.getList(fnIdx, param);
		}

    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
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
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name")), 0);
		
		// 1.2 수정모드 여부 검사
		boolean isModify = (StringUtil.isEquals(mode, "m"))?true:false;

		if(keyIdx <= 0 || !isModify) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		// 2. DB
		String idxColumnName = JSONObjectUtil.getString(settingInfo, "idx_column");		// key column명
		
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();

		
		// 2.1 수정권한 체크
		Boolean isMngAuth = isMngProcAuth(settingInfo, fnIdx, keyIdx);
		if(!isMngAuth) {
			// 수정권한 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.auth")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		param.put("searchList", searchList);
		
		// 2.2 상세정보
		dt = developDctService.getModify(fnIdx, param);
		if(dt == null) {
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
		model.addAttribute("multiDataHashMap", developDctService.getMultiHashMap(fnIdx, keyIdx, settingInfo, items, itemOrder));		// multi file 목록
		model.addAttribute("multiFileHashMap", developDctService.getMultiFileHashMap(fnIdx, keyIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));		// 항목코드
		model.addAttribute("submitType", submitType);											// 페이지구분
		
		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	// 5. form action
    	model.addAttribute("URL_SUBMITPROC", request.getAttribute("URL_IDX_MODIFYPROC"));
		
    	return getViewPath("/input");
	}
	
	/**
	 * 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name="WRT")
	@RequestMapping(value = "/input.do")
	public String input(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject itemInfo = attrVO.getItemInfo();
		
		// 1. 속성 setting
		// 1.1 항목설정
		String submitType = "write";
		model.addAttribute("optnHashMap", getOptionHashMap(submitType, itemInfo));
		model.addAttribute("submitType", submitType);

    	// 2. 기본경로
    	fn_setCommonPath(attrVO);

    	// 3. form action
    	model.addAttribute("URL_SUBMITPROC", request.getAttribute("URL_INPUTPROC"));
    	
		return getViewPath("/input");
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
		int fnIdx = attrVO.getFnIdx();
		boolean isAdmMode = attrVO.isAdmMode();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name")), 0);
		
		// 1.2 수정모드 여부 검사
		boolean isModify = (StringUtil.isEquals(mode, "m"))?true:false;

		if(keyIdx <= 0 || !isModify) {
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

		dt = developDctService.getModify(fnIdx, param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 2.2 전체관리/완전삭제 권한 체크 - 수정권한 없는 경우 return
		Boolean isMngAuth = isMngProcAuth(settingInfo, fnIdx, keyIdx);
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
    	int result = developDctService.update(uploadModulePath, fnIdx, keyIdx, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
    	if(result > 0) {
    		// 4.1 저장 성공
    		
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	
    		String inputNpname = fn_dsetInputNpname(settingInfo);
			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_" + inputNpname))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
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
		int fnIdx = attrVO.getFnIdx();

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
		
		// 2. DB
    	int result = developDctService.insert(uploadModulePath, fnIdx, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_" + inputNpname)));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
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
	public String delete(@ParamMap ParamForm parameterMap, @RequestParam(value="select") int[] deleteIdxs, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		JSONObject settingInfo = attrVO.getSettingInfo();
		String ajaxPName = attrVO.getAjaxPName();
		int fnIdx = attrVO.getFnIdx();
		
		// 1. DB
		int result = developDctService.delete(fnIdx, parameterMap, deleteIdxs, request.getRemoteAddr(), settingInfo);
    	
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
		int fnIdx = attrVO.getFnIdx();

		// 1. 필수 parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name")), 0);
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 2. 전체관리/완전삭제 권한 체크
		Boolean isMngAuth = isMngProcAuth(settingInfo, fnIdx, keyIdx);
		if(!isMngAuth) {
			// 자신이 등록한 글이 아닌 경우
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.auth")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 3. DB
		int[] deleteIdxs = {keyIdx};
		int result = developDctService.delete(fnIdx, parameterMap, deleteIdxs, request.getRemoteAddr(), settingInfo);
    	
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
	@Auth(role = Role.U)
	@ModuleAuth(name="MNG")
	@RequestMapping(method=RequestMethod.POST, value = "/restoreProc.do", params="select")
	public String restore(@RequestParam(value="select") int[] restoreIdxs, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {

		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		JSONObject settingInfo = attrVO.getSettingInfo();
		int fnIdx = attrVO.getFnIdx();
		
		// 1. DB
		int result = developDctService.restore(fnIdx, restoreIdxs, request.getRemoteAddr(), settingInfo);
    	
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
	@Auth(role = Role.D)
	@ModuleAuth(name="CDT")
	@RequestMapping(method=RequestMethod.POST, value = "/cdeleteProc.do", params="select")
	public String cdelete(@RequestParam(value="select") int[] deleteIdxs, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {

		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		JSONObject itemInfo = attrVO.getItemInfo();
		String uploadModulePath = attrVO.getUploadModulePath();
		JSONObject settingInfo = attrVO.getSettingInfo();
		int fnIdx = attrVO.getFnIdx();
		
		// 1. DB
		// 1.1 항목설정
		String submitType = "write";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		// 1.2 삭제처리
		int result = developDctService.cdelete(uploadModulePath, fnIdx, deleteIdxs, settingInfo, items, itemOrder);
    	
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
	@Auth(role = Role.R)
	@ModuleAuth(name="MNG")
	@RequestMapping(value = "/deleteList.do")
	public String deleteList(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		DataForm queryString = attrVO.getQueryString();
		int fnIdx = attrVO.getFnIdx();

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
    	totalCount = developDctService.getDeleteCount(fnIdx, param);
		paginationInfo.setTotalRecordCount(totalCount);
    	
    	if(totalCount > 0) {
    		param.put("firstIndex", paginationInfo.getFirstRecordIndex());
    		param.put("lastIndex", paginationInfo.getLastRecordIndex());
    		param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
    		
    		// 2.3 목록
    		list = developDctService.getDeleteList(fnIdx, param);
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
	@Auth(role = Role.C)
	@ModuleAuth(name="WRT")
	@RequestMapping(value = "/fileUpload.do")
	public ModelAndView fileUpload(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		boolean isAjax = true;
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		int fnIdx = attrVO.getFnIdx();

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
		Map<String, Object> fileInfo = developDctService.getFileUpload(uploadModulePath, fnIdx, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
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
	
		/*
		 * 1. 경로 명명 패턴
		 *  	ㄴ 메뉴_행위(+@).do
		 *  		ㄴ (ex: 신청_임시저장   -> develop_tempSave.do)
		 *  		ㄴ (ex: 신청_신청서상세보기 -> develop_view_form.do)
		 *  
		 *  
		 * 2. 메뉴    : 서비스 안의 메뉴
		 *    행위   : 요청 행위(ex:버튼)의 이름(목적)   
		 *    
		 * */
		
		// 추가경로 
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String baseQueryString = StringUtil.getString(request.getAttribute("baseQueryString"));
		
		/* 능력개발 클리닉 관련 경로 */
		
		/* 신청 */		
		String developListUrl = "develop_list_form.do";								// 훈련과정개발 신청목록
		String developViewFormUrl = "develop_view_form.do";							// 훈련과정개발 과정상세 화면
		String developModifyFormUrl = "develop_modify_form.do";						// 훈련과정개발 수정 화면
		String developHelpFormUrl = "develop_help_form.do";							// 주치의 지원 요청 내용 가져오기
		String developSupportAcceptUrl = "develop_supportAccept.do";				// 주치의 지원요청 승인		
		String developSupportReturnUrl = "develop_supportReturn.do";				// 주치의 지원요청 반려	
		String developAcceptUrl = "develop_accept.do";								// 신청서 접수		
		String developReturnUrl = "develop_return.do";								// 신청서 반려	
		String developApproveUrl = "develop_approve.do";							// 신청서 승인
		String developDropUrl = "develop_drop.do";									// 신청서 중도포기
		String developExcelUrl = "develop_excel.do";								// 맞춤개발 신청목록 엑셀 다운로드
		String developReportUrl = "develop_report_form.do";							// 훈련과정개발 맞춤개발 리포트 출력
		String developRecommendUrl = "develop_recommend_form.do";					// 주치의 지원-기업HRD이음컨설팅 훈련과정 추천 화면
		String developTrainingViewFormUrl = "develop_training_view_form.do";		// 과정상세 화면
		String developWriteFormUrl = "develop_write_form.do";						// 추천활용(과정 수정, 신규작성) 화면
		String developConsiderRequestUrl = "develop_considerRequest.do";			// 추천활용(과정 수정, 신규작성) 화면
		String developModifyRequestUrl = "develop_modify.do";						// 추천활용(과정 수정, 신규작성) 화면
		String developTrainingListFormUrl = "develop_training_list_form.do";		// 추천활용(과정 수정, 신규작성) 화면
		String developTotalListFormUrl 	= "total_list_form.do";						// 추천활용(과정 수정, 신규작성) 화면
		String developTotalExcelFormUrl = "total_excel.do";							// 추천활용(과정 수정, 신규작성) 화면
		String developSupportListFormUrl = "support_list_form.do";					// 맞춤개발 비용청구 신청 목록
		String developSupportViewFormUrl = "support_view_form.do";					// 맞춤개발 비용청구 상세목록
		String developSupportModifyFormUrl = "support_modify_form.do";				// 맞춤개발 비용청구 수정화면
		String developSupportModifyUrl = "support_modify.do";						// 맞춤개발 비용청구 수정
		String developSupportExcelUrl = "support_excel.do";							// 맞춤개발 비용청구 신청 목록 엑셀다운로드
		String developCostAccpetUrl = "support_accept.do";							// 맞춤개발 비용청구 접수
		String developCostReturnUrl = "support_return.do";							// 맞춤개발 비용청구 반려
		String developCostFirstApporveUrl = "support_firstApprove.do";				// 맞춤개발 비용청구 1차승인
		String developCostReturnToAcceptUrl = "support_returnToAccept.do";			// 맞춤개발 비용청구 반려(부장이 최종승인 안하고 반려)
		String developCostApporveUrl = "support_approve.do";						// 맞춤개발 비용청구 최종승인
		String developCostDropUrl = "support_drop.do";								// 맞춤개발 비용청구 중도포기

		
		if(!StringUtil.isEmpty(baseQueryString)) {
			developListUrl += baseQueryString;
			developViewFormUrl += baseQueryString;
			developModifyFormUrl += baseQueryString;
			developHelpFormUrl += baseQueryString;
			developSupportAcceptUrl += baseQueryString;
			developSupportReturnUrl += baseQueryString;
			developAcceptUrl += baseQueryString;
			developReturnUrl += baseQueryString;
			developApproveUrl += baseQueryString;
			developDropUrl += baseQueryString;
			developExcelUrl += baseQueryString;
			developReportUrl += baseQueryString;
			developRecommendUrl += baseQueryString;
			developTrainingViewFormUrl += baseQueryString;
			developWriteFormUrl += baseQueryString;
			developConsiderRequestUrl += baseQueryString;
			developModifyRequestUrl += baseQueryString;
			developTrainingListFormUrl += baseQueryString;
			developTotalListFormUrl += baseQueryString;
			developTotalExcelFormUrl += baseQueryString;
			developSupportViewFormUrl += baseQueryString;
			developSupportListFormUrl += baseQueryString;
			developSupportModifyFormUrl += baseQueryString;
			developSupportModifyUrl += baseQueryString;
			developSupportExcelUrl += baseQueryString;
			developCostAccpetUrl += baseQueryString;
			developCostReturnUrl += baseQueryString;
			developCostFirstApporveUrl += baseQueryString;
			developCostReturnToAcceptUrl += baseQueryString;
			developCostApporveUrl += baseQueryString;
			developCostDropUrl += baseQueryString;
		}
		
		/*
		 * 1. common path 명명 패턴
		 * 		ㄴ 경로 이름_URL 
		 * 			ㄴ (ex: develop_tempSave.do -> {REQUEST_TEMPSAVE_URL}
		 * */
		request.setAttribute("DEVELOP_LIST_FORM_URL", developListUrl);
		request.setAttribute("DEVELOP_VIEW_FORM_URL", developViewFormUrl);
		request.setAttribute("DEVELOP_MODIFY_FORM_URL", developModifyFormUrl);
		request.setAttribute("DEVELOP_HELP_FORM_URL", developHelpFormUrl);
		request.setAttribute("DEVELOP_HELP_ACCEPT_URL", developSupportAcceptUrl);
		request.setAttribute("DEVELOP_HELP_RETURN_URL", developSupportReturnUrl);
		request.setAttribute("DEVELOP_ACCEPT_URL", developAcceptUrl);
		request.setAttribute("DEVELOP_RETURN_URL", developReturnUrl);
		request.setAttribute("DEVELOP_APPROVE_URL", developApproveUrl);
		request.setAttribute("DEVELOP_DROP_URL", developDropUrl);
		request.setAttribute("DEVELOP_EXCEL_URL", developExcelUrl);
		request.setAttribute("DEVELOP_REPORT_FORM_URL", developReportUrl);
		request.setAttribute("DEVELOP_RECOMMEND_FORM_URL", developRecommendUrl);
		request.setAttribute("DEVELOP_TRAINING_VIEW_FORM_URL", developTrainingViewFormUrl);
		request.setAttribute("DEVELOP_WRITE_FORM_URL", developWriteFormUrl);
		request.setAttribute("DEVELOP_CONSIDERREQUEST_URL", developConsiderRequestUrl);
		request.setAttribute("DEVELOP_MODIFY_URL", developModifyRequestUrl);
		request.setAttribute("DEVELOP_TRAINING_LIST_FORM_URL", developTrainingListFormUrl);
		request.setAttribute("DEVELOP_TOTAL_LIST_FORM_URL", developTotalListFormUrl);
		request.setAttribute("DEVELOP_TOTAL_EXCEL_URL", developTotalExcelFormUrl);
		request.setAttribute("DEVELOP_SUPPORT_LIST_FORM_URL", developSupportListFormUrl);
		request.setAttribute("DEVELOP_SUPPORT_VIEW_FORM_URL", developSupportViewFormUrl);
		request.setAttribute("DEVELOP_SUPPORT_EXCEL_URL", developSupportExcelUrl);
		request.setAttribute("DEVELOP_SUPPORT_ACCEPT_URL", developCostAccpetUrl);
		request.setAttribute("DEVELOP_SUPPORT_RETURN_URL", developCostReturnUrl);
		request.setAttribute("DEVELOP_SUPPORT_FIRSTAPPROVE_URL", developCostFirstApporveUrl);
		request.setAttribute("DEVELOP_SUPPORT_RETURN_TO_ACCEPT_URL", developCostReturnToAcceptUrl);
		request.setAttribute("DEVELOP_SUPPORT_APPROVE_URL", developCostApporveUrl);
		request.setAttribute("DEVELOP_SUPPORT_DROP_URL", developCostDropUrl);
		request.setAttribute("DEVELOP_SUPPORT_MODIFY_FORM_URL", developSupportModifyFormUrl);
		request.setAttribute("DEVELOP_SUPPORT_MODIFY_URL", developSupportModifyUrl);
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
	public boolean isMngProcAuth(JSONObject settingInfo, int fnIdx, int keyIdx) {
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
			modiCnt = developDctService.getAuthCount(fnIdx, param);
			
			return (modiCnt > 0);
		}
		
		return true;
	}
}
