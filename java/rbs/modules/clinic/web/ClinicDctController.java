package rbs.modules.clinic.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.clipsoft.org.apache.poi.util.SystemOutLogger;
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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import rbs.egovframework.LoginVO;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.egovframework.common.CommonFunction;
import rbs.egovframework.confirmProgress.ConfirmProgress;
import rbs.modules.basket.service.BasketService;
import rbs.modules.clinic.service.ClinicDctService;
import rbs.modules.clinic.service.ClinicService;

/**
 * 샘플모듈<br/>
 * : 통합관리시스템 > 메뉴콘텐츠관리, 통합관리시스템 > 기능등록관리, 사용자 사이트 에서 사용
 * @author user
 *
 */
@Controller
@ModuleMapping(moduleId="clinicDct", confModule="clinic", confSModule="clinicDct")
@RequestMapping({"/{siteId}/clinicDct", "/{admSiteId}/menuContents/{usrSiteId}/clinicDct"})
public class ClinicDctController extends ModuleController {
	
	@Resource(name = "basketService")
	private BasketService basketService;
	
	@Resource(name = "clinicService")
	private ClinicService clinicService;
	
	@Resource(name = "clinicDctService")
	private ClinicDctService clinicDctService;
	
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
	
	/* 능력개발클리닉 신청관리 시작*/
	/**
	 * 신청관리 목록 화면
	 *   ㄴ fnIdx : 1
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/request_list_form.do")
	public String request_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		String targetTable = "HRD_CLI_REQ";
		
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
		
		//로그인한 계정의 지부지사에 해당하는 모든 주치의 list
		List<?> doctorList = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건

		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		String listSearchId = "";
		
		// 항목설정으로 검색조건 setting
		// 본부 직원과 지부지사 직원의 검색 항목이 다름
		if(usertypeIdx == 30) {
			listSearchId = "list_search";		// 검색설정
		} else {
			listSearchId = "list_search_head";		// 검색설정
		}
	
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
		param.put("INSTT_IDX", insttIdx);
		
		// 목록 수 및 목록 가져오기 
		if (usertypeIdx == 40 || usertypeIdx == 55) {
			// 본부 직원일 때
			param.put("ISHEAD", 1);
		} else {
			// 지부지사 주치의/부장일 때 신청서에서 입력한 주소에 따라 목록 전부 가져오기
			param.put("KEY_IDX", insttIdx);
		}
		
		// 2.2 목록수
    	totalCount = clinicDctService.getTotalReqCount(targetTable, param);
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
    		list = clinicDctService.getReqList(targetTable, param);
		}
		
    	doctorList = clinicDctService.getDoctorList(param);
    	
    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("doctorList", doctorList);												// 주치의 목록
    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
	
	/**
	 * 상세조회
	 *  ㄴ fnIdx : 1
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/request_view_form.do")
	public String request_view_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		// 상세보기할 테이블
		String targetTable = "HRD_CLI_REQ";
		// 파일 테이블
		String targetFileTable = "HRD_CLI_REQ_FILE";
		
		// 1. 필수 parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("reqIdx"));
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		int insttIdx = 0;
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
//		if(loginVO != null){
//			insttIdx = StringUtil.getInt(loginVO.getInsttIdx());
//		}
				
		// 2. DB
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 기업정보를 가져오기 위해 파라미터에서 bplNo 가져오기
		String BPL_NO = request.getParameter("bplNo");
		
		// 기업정보 , 체크리스트 목록 및 답변 목록 정보
		DataMap corpInfo = null;
		List<Object> insttList = null;
		List<Object> checkList = null;
		List<Object> checkListAnswer = null;
		List<Object> jdgMnTabList = null;
		List<Object> jdgMnTabListAnswer = null;
		
		//로그인한 계정의 지부지사에 해당하는 모든 주치의 list
		List<?> doctorList = null;
		
		param.put("BPL_NO", BPL_NO);
		
		// 사업장관리번호(bplNo)로 현재 cliIdx 가져오기
		int cliIdx = clinicDctService.getCliIdx(BPL_NO);
		
		// 기업 정보
		corpInfo = clinicDctService.getCorpInfo(fnIdx, param);
		
		// 신청서에서 지부지사를 선택하기 위해 지부지사 관할구역 정보 가져오기
		insttList = clinicService.getInsttList(param);
		
		// 체크리스트 목록
		checkList = clinicDctService.getCheckList(fnIdx, param);
		
		// 기업 선정 심사표 목록
		jdgMnTabList = clinicDctService.getJdgmntabList(fnIdx, param);

		searchList.add(new DTForm("A.REQ_IDX", keyIdx));
		searchList.add(new DTForm("A.CLI_IDX", cliIdx));
		param.put("searchList", searchList);
		
		// 기업이 체크한 체크리스트를 가져오기(Y/N값 체크용)
		checkListAnswer = clinicDctService.getCheckListAnswer(fnIdx, param);
		
		// 기업 선정 심사표 답변 목록
		jdgMnTabListAnswer = clinicDctService.getJdgmntabListAnswer(fnIdx, param);
		
		
		// 상세정보
		dt = clinicDctService.getReqView(targetTable, param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}

		// 신청서에서 클리닉 지부지사 정보 가져오기
		insttIdx = StringUtil.getInt(dt.get("CLI_INSTT_IDX").toString());
		
		// 신청서에서 가져온 지부지사 정보로 주치의 목록 가져오기
		param.put("INSTT_IDX", insttIdx);
		doctorList = clinicDctService.getDoctorList(param);
		
		String confmStatus = (String) dt.get("CONFM_STATUS");
		String contents = (String) dt.get("DOCTOR_OPINION");
		
		// 접수, 반려요청, 지부지사부장&1차승인 일 때만 textarea 줄바꿈 처리하기
		if(confmStatus.equals("30") || confmStatus.equals("35") || (loginVO.getClsfCd().equals("Y") && confmStatus.equals("50"))) {
			if(contents != null) {
				contents = contents.replaceAll("<br>", "\r\n");
				dt.put("DOCTOR_OPINION", contents);
			}
		}
		
		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "view";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		// 3.2 속성 setting
		model.addAttribute("dt", dt);																				// 상세정보
		model.addAttribute("multiFileHashMap", clinicDctService.getMultiFileHashMap(targetFileTable, cliIdx, fnIdx, keyIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);			
		model.addAttribute("corpInfo", corpInfo);												// 기업정보
		model.addAttribute("insttList", insttList);												// 지부지사 목록
		model.addAttribute("checkList", checkList);												// 기업 자가 체크리스트 목록
		model.addAttribute("checkListAnswer", checkListAnswer);									// 기업 자가 체크리스트 답변 목록
		model.addAttribute("jdgMnTabList", jdgMnTabList);										// 기업 선정 심사표 정보
		model.addAttribute("jdgMnTabListAnswer", jdgMnTabListAnswer);							// 기업 선정 심사표 답변 정보
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		model.addAttribute("doctorList", doctorList);											// 로그인한 계정의 지부지사에 있는 주치의 정보
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	/**
	 * 신청관리 수정 화면
	 *   ㄴ fnIdx : 1
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(value = "/request_modify_form.do")
	public String request_modify_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		// 파일 테이블
		String targetFileTable = "HRD_CLI_REQ_FILE";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("reqIdx"));
		
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 기업정보를 가져오기 위해 파라미터에서 bplNo 가져오기
		String BPL_NO = request.getParameter("bplNo");
		
		// 사업장관리번호(bplNo)로 현재 cliIdx 가져오기
		int cliIdx = clinicDctService.getCliIdx(BPL_NO);
		
		// 기업정보 , 체크리스트 목록 및 답변 목록 정보
		DataMap corpInfo = null;
		List<Object> insttList = null;
		List<Object> checkList = null;
		List<Object> checkListAnswer = null;
		List<Object> jdgMnTabList = null;
		List<Object> jdgMnTabListAnswer = null;
		
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicDctService.getCorpInfo(fnIdx, param);
		
		// 신청서에서 지부지사를 선택하기 위해 지부지사 관할구역 정보 가져오기
		insttList = clinicService.getInsttList(param);
		
		// 체크리스트 목록
		checkList = clinicDctService.getCheckList(fnIdx, param);
		
		// 기업 선정 심사표 목록
		jdgMnTabList = clinicDctService.getJdgmntabList(fnIdx, param);
		
		searchList.add(new DTForm("A.REQ_IDX", keyIdx));
		searchList.add(new DTForm("A.CLI_IDX", cliIdx));
		param.put("searchList", searchList);
		
		// 기업이 체크한 체크리스트를 가져오기(Y/N값 체크용)
		checkListAnswer = clinicDctService.getCheckListAnswer(fnIdx, param);
		
		// 기업 선정 심사표 답변 목록
		jdgMnTabListAnswer = clinicDctService.getJdgmntabListAnswer(fnIdx, param);
		
		// 2.2 상세정보
		dt = clinicDctService.getReqView("HRD_CLI_REQ", param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		// textarea 줄바꿈 처리하기
		String contents = (String) dt.get("DOCTOR_OPINION");
		if(contents != null){
			contents = contents.replaceAll("<br>", "\r\n");
			dt.put("DOCTOR_OPINION", contents);
		}
		
		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "modify";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");

		// 3.2 속성 setting
		model.addAttribute("dt", dt);
		model.addAttribute("multiDataHashMap", clinicDctService.getMultiHashMap(fnIdx, keyIdx, settingInfo, items, itemOrder));		// multi file 목록
		model.addAttribute("multiFileHashMap", clinicDctService.getMultiFileHashMap(targetFileTable, cliIdx, fnIdx, keyIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));		// 항목코드
		model.addAttribute("submitType", submitType);											// 페이지구분
		model.addAttribute("corpInfo", corpInfo);												// 기업정보
		model.addAttribute("insttList", insttList);												// 지부지사 목록
		model.addAttribute("checkList", checkList);												// 기업 자가 체크리스트 목록
		model.addAttribute("checkListAnswer", checkListAnswer);									// 기업 자가 체크리스트 답변 목록
		model.addAttribute("jdgMnTabList", jdgMnTabList);										// 기업 선정 심사표 정보
		model.addAttribute("jdgMnTabListAnswer", jdgMnTabListAnswer);							// 기업 선정 심사표 답변 정보
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/input");
	}
	
	/**
	 * 주치의가 신청서 수정 (신청상태는 변하지 않음)	
	 *   ㄴ fnIdx : 1
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/request_modify.do")
	public String request_modify(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		boolean isAdmMode = attrVO.isAdmMode();
		String targetTable = "HRD_CLI_REQ";
		String targetColumn = "REQ_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("reqIdx"));
		String isUpdateAll = request.getParameter("isUpateAll");

		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 수정 후 상세보기 화면으로 이동하기 위해 사업장관리번호 가져오기
		String bplNo = (String) parameterMap.get("bplNo");

		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		searchList.add(new DTForm("A.REQ_IDX", keyIdx));
		param.put("searchList", searchList);

		dt = clinicDctService.getModify(targetTable, param);
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
		JSONArray reqItemOrder = null;
		if(isUpdateAll.equals("y")) {
			reqItemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		} else {
			reqItemOrder = JSONObjectUtil.getJSONArray(itemInfo, "reqJdg_" + submitType + "proc_order");
		}
		parameterMap.put("_itemInputTypeFlag", submitType);
		
		// 3.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, reqItemOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 4. DB
		int updateReq = 0;
		int updateChk = 0;
		int updateJdg = 0;
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("doctorOpinion");
		if(contents != null){
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("doctorOpinion", contents);
		}
		
		if(isUpdateAll.equals("y")) {
			// 수정화면에서 전체를 수정
			updateReq = clinicDctService.update(targetTable, targetColumn, uploadModulePath, keyIdx, request.getRemoteAddr(), parameterMap, attrVO);
		} else {
			// 상세보기 화면에서 기업 선정 심사표와 주치의 의견만 수정
			updateReq = clinicDctService.updateReqSlctnResultAndDoctorOpinion(targetTable, targetColumn, uploadModulePath, keyIdx, request.getRemoteAddr(), parameterMap, attrVO);
		}
		
    	if(updateReq > 0) {
    		// 기업 자가 체크리스트, 기업 선정 심사표 update
    		if(isUpdateAll.equals("y")) {
    			// 수정화면에서 전체를 수정했을 때만 기업 자가 체크리스트 update
    			updateChk = clinicDctService.deleteAndinsertReqChk("HRD_CLI_CHECKLIST_ANSWER", keyIdx, request.getRemoteAddr(), parameterMap);
    		} else {
    			updateChk = 1;
    		}
    		updateJdg = clinicDctService.deleteAndinsertReqJdg("HRD_CLI_JDGMNTAB_ANSWER", keyIdx, request.getRemoteAddr(), parameterMap);
    		// 4.1 저장 성공
        	// 기본경로
    		if(updateChk > 0 && updateJdg > 0) {
    			fn_setCommonPath(attrVO);
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.update"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("REQUEST_VIEW_FORM_URL") + "&reqIdx=" + keyIdx + "&bplNo=" + bplNo)) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 신청관리 목록에서 접수하기(일괄 접수)
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 30
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method = RequestMethod.GET, value = "/request_accept_all.do")
	public ModelAndView request_accept_all(@ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_REQ";
		String targetColumn = "REQ_IDX";
		
		// 신청상태를 insert할 targetTable(HRD_CLI_REQ_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_REQ_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 일괄로 접수할 cli_idx와 req_idx, 각각의 cli_idx에 지정될 전담주치의 정보
		String cliList = request.getParameter("cliList");
		String reqList = request.getParameter("reqList");
		
		// String으로 받온거 배열로 자르기
		String[] cliArray = cliList.split(",");
		String[] reqArray = reqList.split(",");
		
		int updateReq = 0;
		int insertReqConfm = 0;
		int result = 0;
		int length = cliArray.length;
		
		// 접수(ACCEPT) 상태로 setting
		String confmStatus = ConfirmProgress.ACCEPT.getCode();
		String regiIp = request.getRemoteAddr();
		
		// 각각 접수 처리 하기
		for(int i = 0; i < length; i++) {
			int cliIdx = StringUtil.getInt(cliArray[i]);
			int reqIdx = StringUtil.getInt(reqArray[i]);
			// HRD_CLI_REQ 테이블에 신청상태를 접수로 업데이트 하기
			updateReq += clinicDctService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, reqIdx, regiIp);
		
			// HRD_CLI_REQ_CONFM 테이블에 신청상태 insert하기
			insertReqConfm += clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, reqIdx, regiIp);
		}
		
		result = (updateReq == length && insertReqConfm == length) ? 1 : 0;
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		mav.addObject("result", result);
		
		return mav;
	}
	
	/**
	 * 신청관리 목록에서 주치의 지정하기(일괄 지정)
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 30
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method = RequestMethod.GET, value = "/request_appointDocAll.do")
	public ModelAndView request_appointDocAll(@ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		// 일괄로 접수할 cli_idx와 req_idx, 각각의 cli_idx에 지정될 전담주치의 정보
		String cliList = request.getParameter("cliList");
		String doctorList = request.getParameter("doctorList");
		
		// String으로 받온거 배열로 자르기
		String[] cliArray = cliList.split(",");
		String[] docArray = doctorList.split(",");
		
		int updateCli = 0;
		int updateReq = 0;
		int result = 0;
		int length = cliArray.length;
		
		String regiIp = request.getRemoteAddr();
		
		// 각각 접수 처리 하기
		for(int i = 0; i < length; i++) {
			int cliIdx = StringUtil.getInt(cliArray[i]);
			int doctorIdx = StringUtil.getInt(docArray[i]);
			// HRD_CLI 테이블에 전담주치의 정보 업데이트 하기
			updateCli += clinicDctService.updateCliDoctor(cliIdx, doctorIdx, regiIp);
			
			// HRD_CLI_DOCTOR_HST 테이블에 전담주치의 정보 insert하기(전담주치의가 변경될 때마다 insert >> 이력성 테이블)
			updateReq += clinicDctService.insertDoctorHst("HRD_CLI_DOCTOR_HST", "CHG_SN", cliIdx, doctorIdx, regiIp);
		}
		
		result = (updateCli == length && updateReq == length) ? 1 : 0;
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		mav.addObject("result", result);
		
		return mav;
	}
	
	/**
	 * 신청 상세보기 화면에서 주치의 지정하기
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 30
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method = RequestMethod.GET, value = "/request_appointDoc.do")
	public ModelAndView request_appointDoc(@ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		// 일괄로 접수할 cli_idx와 req_idx, 각각의 cli_idx에 지정될 전담주치의 정보
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));
		int doctorIdx = StringUtil.getInt(request.getParameter("doctorIdx"));
		
		int updateCli = 0;
		int updateReq = 0;
		int result = 0;
		
		String regiIp = request.getRemoteAddr();
		
		// HRD_CLI 테이블에 전담주치의 정보 업데이트 하기
		updateCli = clinicDctService.updateCliDoctor(cliIdx, doctorIdx, regiIp);
		
		if(updateCli > 0) {
			// HRD_CLI_DOCTOR_HST 테이블에 전담주치의 정보 insert하기(전담주치의가 변경될 때마다 insert >> 이력성 테이블)
			updateReq = clinicDctService.insertDoctorHst("HRD_CLI_DOCTOR_HST", "CHG_SN", cliIdx, doctorIdx, regiIp);
		}

		result = updateReq;
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		mav.addObject("result", result);
		
		return mav;
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
	@RequestMapping(method=RequestMethod.POST, value = "/request_accept.do")
	public String request_accept(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_REQ";
		String targetColumn = "REQ_IDX";
		
		// 신청상태를 insert할 targetTable(HRD_CLI_REQ_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_REQ_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("reqIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 신청 상태에서 접수 한건지, 1차승인 상태에서 전담주치의가 회수를 해서 접수로 되돌아가는지 확인
		String message = "";
		String isWithdraw = request.getParameter("isWithdraw");
		if(isWithdraw.equals("y")) {
			// 회수
			message = rbsMessageSource.getMessage("message.withdraw");
		} else {
			// 접수
			message = rbsMessageSource.getMessage("message.accept");
		}
		
		// 접수 상태로 setting
		String confmStatus = ConfirmProgress.ACCEPT.getCode();

		// 접수 상태로 HRD_CLI_REQ 신청상태 update하기
    	int updateReq = clinicDctService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updateReq > 0) {
    		// HRD_CLI_REQ_CONFM 테이블에 접수 상태 insert하기
    		int updateReqConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updateReqConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, message, "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("REQUEST_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
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
	@RequestMapping(method=RequestMethod.POST, value = "/request_return.do")
	public String request_return(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태, 주치의 의견을 업데이트할 targetTable
		String targetTable = "HRD_CLI_REQ";
		String targetColumn = "REQ_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_REQ_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_REQ_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("reqIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));
		String slctnResult = (String) parameterMap.get("slctnResult");

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
		
		int updateReq = 0;
		
		if(slctnResult != null) {
			// 처음 상세보기 화면으로 들어와서 기업 선정 심사표를 작성하고 반려처리 할 때 
			// 기업 선정 심사표 update
			int updateJdg = clinicDctService.deleteAndinsertReqJdg("HRD_CLI_JDGMNTAB_ANSWER", keyIdx, request.getRemoteAddr(), parameterMap);
			
			if(updateJdg > 0) {
				// 반려 상태로 HRD_CLI_REQ 신청상태 및 주치의 의견, 기업선정 심사표 결과 update하기
				updateReq = clinicDctService.updateStatusAndOpinion(targetTable, targetColumn, confmStatus, keyIdx, regiIp, parameterMap, slctnResult);
			}
		} else {
			// 기업 선정 심사표를 작성완료 하고 상세보기 화면으로 들어와서 주치의 의견만 입력하고 반려처리 할 때
			updateReq = clinicDctService.updateStatusAndOpinion(targetTable, targetColumn, confmStatus, keyIdx, regiIp, parameterMap);
		}
    	if(updateReq > 0) {
    		// HRD_CLI_REQ_CONFM 테이블에 반려 상태 insert하기
    		int updateReqConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updateReqConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.return"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("REQUEST_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 신청관리 상세보기 화면에서 1차승인하기
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 50
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/request_firstApprove.do")
	public String request_firstApprove(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_REQ";
		String targetColumn = "REQ_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_REQ_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_REQ_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("reqIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));
		String slctnResult = (String) parameterMap.get("slctnResult");
		
		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 1차승인 상태로 setting
		String confmStatus = ConfirmProgress.FIRSTAPPROVE.getCode();
		
		int updateReq = 0;
		
		if(slctnResult != null) {
			// 처음 상세보기 화면으로 들어와서 기업 선정 심사표를 작성하고 1차승인할 때 
			// 기업 선정 심사표 update
			int updateJdg = clinicDctService.deleteAndinsertReqJdg("HRD_CLI_JDGMNTAB_ANSWER", keyIdx, request.getRemoteAddr(), parameterMap);
			
			if(updateJdg > 0) {
				// 1차승인 상태로 HRD_CLI_REQ 신청상태 및 선정결과 update하기
				updateReq = clinicDctService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp, slctnResult);
			}
		} else {
			// 기업 선정 심사표를 작성완료 하고 상세보기 화면으로 들어와서 1차승인 할 때
			updateReq = clinicDctService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
		}
		
    	if(updateReq > 0) {
    		// HRD_CLI_REQ_CONFM 테이블에 1차승인 상태 insert하기
    		int updateReqConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updateReqConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.firstapprove"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("REQUEST_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 신청관리 상세보기 화면에서 부장이 최종승인 안하고 반려하기 > 접수 상태로 돌아감
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
	@RequestMapping(method=RequestMethod.POST, value = "/request_returnToAccept.do")
	public String request_returnToAccept(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태, 주치의 의견을 업데이트할 targetTable
		String targetTable = "HRD_CLI_REQ";
		String targetColumn = "REQ_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_REQ_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_REQ_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("reqIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

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
		
		int updateReq = 0;
		
		// 주치의 의견와 신청상태 update
		updateReq = clinicDctService.updateStatusAndOpinion(targetTable, targetColumn, confmStatus, keyIdx, regiIp, parameterMap);
		
    	if(updateReq > 0) {
    		// HRD_CLI_REQ_CONFM 테이블에 접수 상태 insert하기
    		int updateReqConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updateReqConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.return"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("REQUEST_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
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
	@RequestMapping(method=RequestMethod.GET, value = "/request_approve.do")
	public String request_approve(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_REQ";
		String targetColumn = "REQ_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_REQ_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_REQ_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("reqIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 최종승인 상태로 setting
		String confmStatus = ConfirmProgress.APPROVE.getCode();

		// 최종승인 상태로 HRD_CLI_REQ 신청상태 update하기
    	int updateReq = clinicDctService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updateReq > 0) {
    		// HRD_CLI_REQ_CONFM 테이블에 최종승인 상태 insert하기
    		int updateReqConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updateReqConfm > 0) {
    			// HRD_CLI에 유효시작날짜와 유효종료날짜 update하기
    			int updateCli = clinicDctService.updateCliStartEndDate(cliIdx, regiIp);
    			if(updateCli > 0) {
    				// 저장 성공
                	// 기본경로
                	fn_setCommonPath(attrVO);
                	
        			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.approve"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("REQUEST_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
        			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    			}
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 신청관리 상세보기 화면에서 중도포기처리 하기
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 75
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/request_dropRequest.do")
	public String request_dropRequest(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_REQ";
		String targetColumn = "REQ_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_REQ_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_REQ_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("reqIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 중도포기 상태로 setting
		String confmStatus = ConfirmProgress.DROP.getCode();

		// HRD_CLI 테이블에 ISDELETE = '1'로 업데이트(삭제처리)
		int deleteCli = clinicDctService.deleteCli("HRD_CLI", cliIdx, regiIp);
		if(deleteCli > 0) {
			// HRD_CLI_REQ 테이블에 중도포기 상태로 신청상태 update하기
	    	int updateReq = clinicDctService.updateStatusDropRequest(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
	    	if(updateReq > 0) {
	    		// HRD_CLI_REQ_CONFM 테이블에 중도포기 상태 insert하기
	    		int updateReqConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
	    		if(updateReqConfm > 0) {
	    			// 저장 성공
	            	// 기본경로
	            	fn_setCommonPath(attrVO);
	            	
	    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.drop"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("REQUEST_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
	    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
	    		}
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
	@RequestMapping(value = "/request_excel.do")
	public String request_excel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		JSONObject itemInfo = attrVO.getItemInfo();
		String targetTable = "HRD_CLI_REQ";
		
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
		
		// 2. DB
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건

		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		HashMap<String, Object> optnHashMap = CodeHelper.getItemOptnHashMap(items, itemOrder);
		
		// 항목설정으로 검색조건 setting
		String listSearchId = "list_search";		// 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		param.put("searchList", searchList);
		param.put("INSTT_IDX", insttIdx);
		
		// 목록 수 및 목록 가져오기 
		if (usertypeIdx == 40 || usertypeIdx == 55) {
			// 본부 직원일 때
			param.put("ISHEAD", 1);
		} else {
			// 지부지사 주치의/부장일 때 해당 지부지사에 소속된 기업(HRD_BSK_PSITN_INSTT)의 신청 목록 전부 가져오기
			param.put("KEY_IDX", insttIdx);
		}
    	// 2.3 목록
		list = clinicDctService.getReqList(targetTable, param);
		
    	// 3. 속성 setting
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("searchOptnHashMap", CodeHelper.getItemOptnSearchHashMap(listSearch));	// 항목 코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
    	
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
		
		return getViewPath("/excel");
	}
	
	/* 능력개발클리닉 신청관리 끝*/
	
	/* 능력개발클리닉 계획관리 시작*/
	/**
	 * [계획관리] 목록 화면
	 *   ㄴ fnIdx : 3
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/plan_list_form.do")
	public String plan_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		String targetTable = "HRD_CLI_PLAN";
		
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
		String listSearchId = "";
		
		// 항목설정으로 검색조건 setting
		// 본부 직원과 지부지사 직원의 검색 항목이 다름
		if(usertypeIdx == 30) {
			listSearchId = "list_search";		// 검색설정
		} else {
			listSearchId = "list_search_head";		// 검색설정
		}
	
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		// 연차 검색
		String yearItemName = "is_year";
		String year = queryString.getString(yearItemName);
		if(!StringUtil.isEmpty(year)) {
			param.put("YEAR", year);
			model.addAttribute("isSearchList", new Boolean(true));
			model.addAttribute("year", year);
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
		param.put("INSTT_IDX", insttIdx);
		
		// 목록 수 및 목록 가져오기 
		if (usertypeIdx == 40 || usertypeIdx == 55 || clsfCd.equals("Y")){
			if (usertypeIdx == 40 || usertypeIdx == 55) {
				// 본부 직원일 때
				param.put("ISHEAD", 1);
			} else {
				// 지부지사 부장일 때 해당 지부지사에 소속된 기업(HRD_BSK_PSITN_INSTT)의 신청 목록 전부 가져오기
				param.put("KEY_IDX", insttIdx);
			}
			
			// 2.2 목록수
	    	totalCount = clinicDctService.getTotalCount(targetTable, param);
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
	    		list = clinicDctService.getList(targetTable, param);
			}
		} else {
			// 지부지사 주치의일 때 자신이 일반주치의인 기업(HRD_COM_DOCTOR_CORP)과 자신이 전담주치의인 기업(HRD_CLI)의 신청 목록만 가져오기(일반주치의일 때는 전담주치의 지정만 가능)
			param.put("DOCTOR_IDX", doctorIdx);
			
			// 2.2 목록수
	    	totalCount = clinicDctService.getTotalCountForDoctor(targetTable, param);
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
	    		list = clinicDctService.getListForDoctor(targetTable, param);
			}
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
	 * [계회관리] 훈련계획서 상세조회
	 *  ㄴ fnIdx : 3
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/plan_view_form.do")
	public String plan_view_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		int fnIdx = attrVO.getFnIdx();
		JSONObject itemInfo = attrVO.getItemInfo();
		// 상세보기할 테이블
		String targetTable = "HRD_CLI_PLAN";
		
		// 1. 필수 parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("planIdx"));
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
				
		// 2. DB
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 기업정보를 가져오기 위해 파라미터에서 bplNo 가져오기
		String BPL_NO = request.getParameter("bplNo");
		
		// 기업정보, 지원항목, HRD담당자, 연간훈련계획, 자체KPI목표
		DataMap corpInfo = null;
		List<Object> picList = null;
		List<Object> kpiList = null;
		List<Object> subList = null;
		List<Object> trList = null;
		DataMap reqList = null;
		
		param.put("BPL_NO", BPL_NO);
		
		// 사업장관리번호(bplNo)로 현재 cliIdx 가져오기
		int cliIdx = clinicDctService.getCliIdx(BPL_NO);
		
		// 기업 정보
		corpInfo = clinicDctService.getCorpInfo(fnIdx, param);
		
		searchList.add(new DTForm("A.CLI_IDX", cliIdx));
		param.put("searchList", searchList);
		reqList = clinicService.getView("HRD_CLI_REQ", param);
		
		// 작성된 신청서의 정보를 가져와 세팅한 후 searchList를 비운다.
		searchList.clear();		
		
		searchList.add(new DTForm("A.PLAN_IDX", keyIdx));
		param.put("searchList", searchList);
		
		// 클리닉 활동 계획 기업 담당자
		picList = clinicService.getList("HRD_CLI_PLAN_CORP_PIC", "PLAN_IDX", keyIdx, "PIC_IDX");
		// 클리닉 활동 계획 KPI 목표
		kpiList = clinicService.getList("HRD_CLI_PLAN_KPI","PLAN_IDX", keyIdx, "KPI_IDX");
		// 클리닉 활동 계획 세부 내용
		subList = clinicService.getList("HRD_CLI_PLAN_SUB","PLAN_IDX", keyIdx, "ESSNTL_SE, A.SPORT_ITEM_CD");
		// 클리닉 활동 계획 세부 훈련계획(연간)
		trList = clinicService.getList("HRD_CLI_PLAN_TR_SUB","PLAN_IDX", keyIdx, "TR_DTL_IDX");
		
		// 상세정보
		dt = clinicDctService.getView(targetTable, param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}

		String confmStatus = (String) dt.get("CONFM_STATUS");
		String contents = (String) dt.get("DOCTOR_OPINION");
		
		// 접수일 때만 textarea 줄바꿈 처리하기
		if(confmStatus.equals("30")) {
			if(contents != null) {
				contents = contents.replaceAll("<br>", "\r\n");
				dt.put("DOCTOR_OPINION", contents);
			}
		}
		
		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "view";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		// 3.2 속성 setting
		model.addAttribute("dt", dt);																				// 상세정보
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);			
		model.addAttribute("corpInfo", corpInfo);												// 기업정보
		model.addAttribute("reqList", reqList);
		model.addAttribute("picList", picList);
		model.addAttribute("kpiList", kpiList);
		model.addAttribute("subList", subList);
		model.addAttribute("trList", trList);		
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	/**
	 * [계획관리] 수정 화면
	 *   ㄴ fnIdx : 3
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(value = "/plan_modify_form.do")
	public String plan_modify_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		int fnIdx = attrVO.getFnIdx();

		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int planIdx = StringUtil.getInt(request.getParameter("planIdx"));
		
		if(planIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "modify");
		
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 기업정보를 가져오기 위해 파라미터에서 bplNo 가져오기
		String BPL_NO = request.getParameter("bplNo");
		
		// 사업장관리번호(bplNo)로 현재 cliIdx 가져오기
		int cliIdx = clinicDctService.getCliIdx(BPL_NO);
		
		// 기업정보, 지원항목, HRD담당자, 연간훈련계획, 자체KPI목표
		DataMap corpInfo = null;
		List<Object> picList = null;
		List<Object> kpiList = null;
		List<Object> subList = null;
		List<Object> trList = null;
		DataMap reqList = null;
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CLI_IDX", cliIdx));
		param.put("searchList", searchList);
		// 작성된 신청서에서 가져온 해당 기업정보 
		reqList = clinicDctService.getModify("HRD_CLI_REQ", param);
		
		// 작성된 신청서의 정보를 가져와 세팅한 후 searchList를 비운다.
		searchList.clear();
		
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicDctService.getCorpInfo(fnIdx, param);
		// 클리닉 활동 계획 기업 담당자
		picList = clinicDctService.getList("HRD_CLI_PLAN_CORP_PIC", "PLAN_IDX", planIdx, "PIC_IDX");
		// 클리닉 활동 계획 KPI 목표
		kpiList = clinicDctService.getList("HRD_CLI_PLAN_KPI","PLAN_IDX", planIdx, "KPI_IDX");
		// 클리닉 활동 계획 세부 내용
		subList = clinicDctService.getList("HRD_CLI_PLAN_SUB","PLAN_IDX", planIdx, "ESSNTL_SE, A.SPORT_ITEM_CD");
		// 클리닉 활동 계획 세부 훈련계획(연간)
		trList = clinicDctService.getList("HRD_CLI_PLAN_TR_SUB","PLAN_IDX", planIdx, "TR_DTL_IDX");
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "PLAN_IDX", planIdx));
		param.put("searchList", searchList);
		
		// 2.2 상세정보
		dt = clinicService.getModify("HRD_CLI_PLAN", param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		// textarea 줄바꿈 처리하기
		String contents = (String) dt.get("DOCTOR_OPINION");
		if(contents != null){
			contents = contents.replaceAll("<br>", "\r\n");
			dt.put("DOCTOR_OPINION", contents);
		}

		String contents2 = (String) dt.get("HRD_PIC_ABLTDEVLP_PLAN");
		if(contents2 != null){
			contents2 = contents2.replaceAll("<br>", "\r\n");
			dt.put("HRD_PIC_ABLTDEVLP_PLAN", contents2);
		}
		
		String contents3 = (String) dt.get("SLFTR_PLAN");
		if(contents3 != null){
			contents3 = contents3.replaceAll("<br>", "\r\n");
			dt.put("SLFTR_PLAN", contents3);
		}
		
		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "modify";

		// 3.2 속성 setting
		model.addAttribute("dt", dt);
		model.addAttribute("submitType", submitType);											// 페이지구분
		model.addAttribute("corpInfo", corpInfo);
		model.addAttribute("reqList", reqList);
		model.addAttribute("picList", picList);
		model.addAttribute("kpiList", kpiList);
		model.addAttribute("subList", subList);
		model.addAttribute("trList", trList);
		model.addAttribute("optnHashMap", optnHashMap);									// 항목코드
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/input");
	}
	
	/**
	 * [계회관리] 주치의가 계획서 수정 (신청상태는 변하지 않음)	
	 *   ㄴ fnIdx : 3
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/plan_modify.do")
	public String plan_modify(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		boolean isAdmMode = attrVO.isAdmMode();
		String targetTable = "HRD_CLI_PLAN";
		String targetColumn = "PLAN_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("planIdx"));
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 수정 후 상세보기 화면으로 이동하기 위해 사업장관리번호 가져오기
		String bplNo = (String) parameterMap.get("bplNo");
		
		// 사업장관리번호(bplNo)로 현재 cliIdx 가져오기
		int cliIdx = clinicDctService.getCliIdx(bplNo);

		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		searchList.add(new DTForm("A.PLAN_IDX", keyIdx));
		param.put("searchList", searchList);

		dt = clinicDctService.getModify(targetTable, param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}

		// textarea 개행처리 하기
		String contents2 = (String) parameterMap.get("hrdPic");
		if(contents2 != null){
			contents2 = contents2.replaceAll("\r\n", "<br>");
			parameterMap.put("hrdPic", contents2);
		}
		
		String contents3 = (String) parameterMap.get("slftr");
		if(contents3 != null){
			contents3 = contents3.replaceAll("\r\n", "<br>");
			parameterMap.put("slftr", contents3);
		}
		
		// 3. 필수입력 체크
		// 3.1 항목설정
		String submitType = "modify";		// 항목 order명
		String inputFlag = "modify";		// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray reqItemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		
		// 3.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, reqItemOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 4. DB
		int updatePlan = 0;
		int updatePlanSub = 0;
		int updatePlanCorp = 0;
		int updatePlanYearTp = 0;
		int updatePlanKPI = 0;
		
		updatePlan = clinicDctService.update(targetTable, targetColumn, uploadModulePath, keyIdx, request.getRemoteAddr(), parameterMap, attrVO);
    	if(updatePlan > 0) {
    		// 지원항목, HRD담당자 정보, 연간훈련계획, 자체KPI목표 update 하기
    		updatePlanSub = clinicDctService.deleteAndinsertPlanSub("HRD_CLI_PLAN_SUB", cliIdx, keyIdx, request.getRemoteAddr(), parameterMap);
    		updatePlanCorp = clinicDctService.deleteAndinsertPlanCorp("HRD_CLI_PLAN_CORP_PIC", cliIdx, keyIdx, request.getRemoteAddr(), parameterMap);
    		updatePlanYearTp = clinicDctService.deleteAndinsertPlanYearTp("HRD_CLI_PLAN_TR_SUB", cliIdx, keyIdx, request.getRemoteAddr(), parameterMap);
    		updatePlanKPI = clinicDctService.deleteAndinsertPlanKPI("HRD_CLI_PLAN_KPI", cliIdx, keyIdx, request.getRemoteAddr(), parameterMap);
    		// 4.1 저장 성공
        	// 기본경로
    		if(updatePlanSub > 0 && updatePlanCorp > 0 && updatePlanYearTp > 0 && updatePlanKPI > 0) {
    			fn_setCommonPath(attrVO);
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.update"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("PLAN_VIEW_FORM_URL") + "&planIdx=" + keyIdx + "&bplNo=" + bplNo)) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.update")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * [계획관리] 목록에서 접수하기(일괄 접수)
	 *   ㄴ fnIdx : 3
	 *   ㄴ CONFM_STATUS : 30
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method = RequestMethod.GET, value = "/plan_accept_all.do")
	public ModelAndView plan_accept_all(@ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_PLAN";
		String targetColumn = "PLAN_IDX";
		
		// 신청상태를 insert할 targetTable(HRD_CLI_PLAN_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_PLAN_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 일괄로 접수할 cli_idx와 plan_idx
		String cliList = request.getParameter("cliList");
		String planList = request.getParameter("planList");
		
		// String으로 받온거 배열로 자르기
		String[] cliArray = cliList.split(",");
		String[] planArray = planList.split(",");
		
		int updatePlan = 0;
		int insertPlanConfm = 0;
		int result = 0;
		int length = cliArray.length;
		
		// 접수(ACCEPT) 상태로 setting
		String confmStatus = ConfirmProgress.ACCEPT.getCode();
		String regiIp = request.getRemoteAddr();
		
		// 각각 접수 처리 하기
		for(int i = 0; i < length; i++) {
			int cliIdx = StringUtil.getInt(cliArray[i]);
			int planIdx = StringUtil.getInt(planArray[i]);
			// HRD_CLI_REQ 테이블에 신청상태를 접수로 업데이트 하기
			updatePlan += clinicDctService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, planIdx, regiIp);
		
			// HRD_CLI_REQ_CONFM 테이블에 신청상태 insert하기
			insertPlanConfm += clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, planIdx, regiIp);
		}
		
		result = (updatePlan == length && insertPlanConfm == length) ? 1 : 0;
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		mav.addObject("result", result);
		
		return mav;
	}
	
	/**
	 * [계획관리] 상세보기 화면에서 접수하기
	 *   ㄴ fnIdx : 3
	 *   ㄴ CONFM_STATUS : 30
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/plan_accept.do")
	public String plan_accept(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_PLAN";
		String targetColumn = "PLAN_IDX";
		
		// 신청상태를 insert할 targetTable(HRD_CLI_PLAN_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_PLAN_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("planIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 접수 상태로 setting
		String confmStatus = ConfirmProgress.ACCEPT.getCode();

		// 접수 상태로 HRD_CLI_PLAN 신청상태 update하기
    	int updatePlan = clinicDctService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updatePlan > 0) {
    		// HRD_CLI_PLAN_CONFM 테이블에 접수 상태 insert하기
    		int updatePlanConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updatePlanConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.accept"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("PLAN_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * [계획관리] 상세보기 화면에서 반려처리하기
	 *   ㄴ fnIdx : 3
	 *   ㄴ CONFM_STATUS : 40
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/plan_return.do")
	public String plan_return(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태, 주치의 의견을 업데이트할 targetTable
		String targetTable = "HRD_CLI_PLAN";
		String targetColumn = "PLAN_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_PLAN_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_PLAN_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("planIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("doctorOpinion");
		if(contents != null) {
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("doctorOpinion", contents);
		}
		
		// 반려 상태로 setting
		String confmStatus = ConfirmProgress.RETURN.getCode();
		
		int updatePlan = 0;
		updatePlan = clinicDctService.updateStatusAndOpinion(targetTable, targetColumn, confmStatus, keyIdx, regiIp, parameterMap);
		
    	if(updatePlan > 0) {
    		// HRD_CLI_PLAN_CONFM 테이블에 반려 상태 insert하기
    		int updatePlanConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updatePlanConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.return"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("PLAN_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * [계획관리] 상세보기 화면에서 최종승인하기
	 *   ㄴ fnIdx : 3
	 *   ㄴ CONFM_STATUS : 55
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/plan_approve.do")
	public String plan_approve(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_PLAN";
		String targetColumn = "PLAN_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_PLAN_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_PLAN_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("planIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 최종승인 상태로 setting
		String confmStatus = ConfirmProgress.APPROVE.getCode();

		// 최종승인 상태로 HRD_CLI_PLAN 신청상태 update하기
    	int updatePlan = clinicDctService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updatePlan > 0) {
    		// 최종승인 후 HRD_CLI_PLAN_CHANGE 테이블에 insert하기(변경 요청을 위해서 insert)
    		clinicDctService.planToChange(cliIdx, keyIdx);
    		// HRD_CLI_PLAN_CONFM 테이블에 최종승인 상태 insert하기
    		int updatePlanConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updatePlanConfm > 0) {
				// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.approve"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("PLAN_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * [계회관리] 훈련계획서 변경요청이 들어왔을 때 상세보기 페이지
	 *  ㄴ fnIdx : 3
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/plan_modify_view_form.do")
	public String plan_modify_view_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		int fnIdx = attrVO.getFnIdx();
		JSONObject itemInfo = attrVO.getItemInfo();
		// 상세보기할 테이블
		
		// 1. 필수 parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("planIdx"));
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
				
		// 2. DB
		DataMap dtBefore = null;
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 기업정보를 가져오기 위해 파라미터에서 bplNo 가져오기
		String BPL_NO = request.getParameter("bplNo");
		
		// 기업정보, 신청서 정보
		DataMap corpInfo = null;
		DataMap reqList = null;
		
		param.put("BPL_NO", BPL_NO);
		
		// 사업장관리번호(bplNo)로 현재 cliIdx 가져오기
		int cliIdx = clinicDctService.getCliIdx(BPL_NO);
		
		// 기업 정보
		corpInfo = clinicDctService.getCorpInfo(fnIdx, param);
		
		searchList.add(new DTForm("A.CLI_IDX", cliIdx));
		param.put("searchList", searchList);
		reqList = clinicService.getView("HRD_CLI_REQ", param);
		
		// 변경 전 지원항목, HRD담당자, 연간훈련계획, 자체KPI목표
		List<Object> picBeforeList = null;
		List<Object> kpiBeforeList = null;
		List<Object> subBeforeList = null;
		List<Object> trBeforeList = null;
		
		// 변경 요청 지원항목, HRD담당자, 연간훈련계획, 자체KPI목표
		List<Object> picList = null;
		List<Object> kpiList = null;
		List<Object> subList = null;
		List<Object> trList = null;
		
		searchList.add(new DTForm("A.PLAN_IDX", keyIdx));
		param.put("searchList", searchList);

		// 변경 요청 idx
		int chgIdx = clinicDctService.getMaxIdx("HRD_CLI_PLAN_CHANGE", "CHG_IDX", param);
		// 변경 전 idx
		int chgBeforeIdx = chgIdx - 1;
		
		searchList.add(new DTForm("A.CHG_IDX", chgBeforeIdx));
		param.put("searchList", searchList);
		
		// 변경 전 기존에 승인된 정보 가져오기
		// 변경 전 클리닉 활동 계획 기업 담당자
		picBeforeList = clinicDctService.getChangeList("HRD_CLI_PLAN_CHANGE_CORP_PIC", "CHG_IDX", param);
		// 변경 전 클리닉 활동 계획 KPI 목표
		kpiBeforeList = clinicDctService.getChangeList("HRD_CLI_PLAN_CHANGE_KPI", "CHG_IDX", param);
		// 변경 전 클리닉 활동 계획 세부 내용
		subBeforeList = clinicDctService.getChangeList("HRD_CLI_PLAN_CHANGE_SUB", "ESSNTL_SE, A.SPORT_ITEM_CD", param);
		// 변경 전 클리닉 활동 계획 세부 훈련계획(연간)
		trBeforeList = clinicDctService.getChangeList("HRD_CLI_PLAN_CHANGE_TR_SUB", "CHG_IDX", param);
		
		// 변경 전 상세정보
		dtBefore = clinicDctService.getView("HRD_CLI_PLAN_CHANGE", param);
		
		// 변경 요청 정보 가져오기
		searchList.clear();
		
		searchList.add(new DTForm("A.CLI_IDX", cliIdx));
		searchList.add(new DTForm("A.PLAN_IDX", keyIdx));
		searchList.add(new DTForm("A.CHG_IDX", chgIdx));
		param.put("searchList", searchList);
		
		// 변경 요청 클리닉 활동 계획 기업 담당자
		picList = clinicDctService.getChangeList("HRD_CLI_PLAN_CHANGE_CORP_PIC", "CHG_IDX", param);
		// 변경 요청 클리닉 활동 계획 KPI 목표
		kpiList = clinicDctService.getChangeList("HRD_CLI_PLAN_CHANGE_KPI", "CHG_IDX", param);
		// 변경 요청 클리닉 활동 계획 세부 내용
		subList = clinicDctService.getChangeList("HRD_CLI_PLAN_CHANGE_SUB", "ESSNTL_SE, A.SPORT_ITEM_CD", param);
		// 변경 요청 클리닉 활동 계획 세부 훈련계획(연간)
		trList = clinicDctService.getChangeList("HRD_CLI_PLAN_CHANGE_TR_SUB", "CHG_IDX", param);
		
		// 변경 요청 상세정보
		dt = clinicDctService.getView("HRD_CLI_PLAN_CHANGE", param);
		
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
		model.addAttribute("dtBefore", dtBefore);																	// 변경 전 상세정보
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);			
		model.addAttribute("corpInfo", corpInfo);												// 기업정보
		model.addAttribute("reqList", reqList);
		model.addAttribute("picList", picList);
		model.addAttribute("kpiList", kpiList);
		model.addAttribute("subList", subList);
		model.addAttribute("trList", trList);		
		model.addAttribute("picBeforeList", picBeforeList);
		model.addAttribute("kpiBeforeList", kpiBeforeList);
		model.addAttribute("subBeforeList", subBeforeList);
		model.addAttribute("trBeforeList", trBeforeList);		
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/modifyView");
	}
	
	/**
	 * [계획관리] 변경 요청에 대한 최종 승인하기(변경 승인)
	 *   ㄴ fnIdx : 3
	 *   ㄴ CONFM_STATUS : 55
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/plan_modify_approve.do")
	public String plan_modify_approve(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetColumn = "PLAN_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_PLAN_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_PLAN_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("planIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 최종승인 상태로 setting
		String confmStatus = ConfirmProgress.APPROVE.getCode();
		
		// 변경 요청 idx 가져오기
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		searchList.add(new DTForm("A.CLI_IDX", cliIdx));
		searchList.add(new DTForm("A.PLAN_IDX", keyIdx));
		param.put("searchList", searchList);
		
		int chgIdx = clinicDctService.getMaxIdx("HRD_CLI_PLAN_CHANGE", "CHG_IDX", param);

		// 최종승인 상태로 HRD_CLI_PLAN 신청상태 update하기
//    	int updatePlan = clinicDctService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	// 최종승인 상태로 HRD_CLI_PLAN_CHANGE 신청상태 update하기
    	int updatePlanChange = clinicDctService.updateStatus("HRD_CLI_PLAN_CHANGE", targetColumn, confmStatus, cliIdx, keyIdx, regiIp, chgIdx);
    	clinicDctService.changeToPlan(cliIdx, keyIdx, chgIdx);
    	if(updatePlanChange > 0) {
    		// HRD_CLI_PLAN_CONFM 테이블에 최종승인 상태 insert하기
    		int updatePlanConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updatePlanConfm > 0) {
				// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.approve"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("PLAN_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * [계획관리] 상세보기 화면에서 변경요청에 대한 반려처리하기
	 *   ㄴ fnIdx : 3
	 *   ㄴ CONFM_STATUS : 53
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/plan_modify_return.do")
	public String plan_modify_return(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태, 주치의 의견을 업데이트할 targetTable
		String targetTable = "HRD_CLI_PLAN";
		String targetColumn = "PLAN_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_PLAN_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_PLAN_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("planIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("doctorOpinion");
		if(contents != null) {
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("doctorOpinion", contents);
		}
		
		// 반려 상태로 setting
		String confmStatus = ConfirmProgress.APPROVEWITHOUTMODIFY.getCode();
		
		int updatePlan = 0;
		updatePlan = clinicDctService.updateStatusAndOpinion(targetTable, targetColumn, confmStatus, keyIdx, regiIp, parameterMap);
		
    	if(updatePlan > 0) {
    		// HRD_CLI_PLAN_CONFM 테이블에 반려 상태 insert하기
    		int updatePlanConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updatePlanConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.return"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("PLAN_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * [계획관리] 상세보기 화면에서 중도포기처리 하기
	 *   ㄴ fnIdx : 3
	 *   ㄴ CONFM_STATUS : 75
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/plan_dropRequest.do")
	public String plan_dropRequest(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_PLAN";
		String targetColumn = "PLAN_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_PLAN_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_PLAN_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("planIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 중도포기 상태로 setting
		String confmStatus = ConfirmProgress.DROP.getCode();

		// HRD_CLI 테이블에 ISDELETE = '1'로 업데이트(삭제처리)
		int deleteCli = clinicDctService.deleteCli("HRD_CLI", cliIdx, regiIp);
		if(deleteCli > 0) {
			// HRD_CLI_PLAN 테이블에 중도포기 상태로 신청상태 update하기
	    	int updatePlan = clinicDctService.updateStatusDropRequest(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
	    	if(updatePlan > 0) {
	    		// HRD_CLI_PLAN_CONFM 테이블에 중도포기 상태 insert하기
	    		int insertConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
	    		if(insertConfm > 0) {
	    			// 저장 성공
	            	// 기본경로
	            	fn_setCommonPath(attrVO);
	            	
	    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.drop"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("PLAN_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
	    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
	    		}
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
	@RequestMapping(value = "/plan_excel.do")
	public String plan_excel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		JSONObject itemInfo = attrVO.getItemInfo();
		String targetTable = "HRD_CLI_PLAN";
		
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
		
		// 2. DB
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건

		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		HashMap<String, Object> optnHashMap = CodeHelper.getItemOptnHashMap(items, itemOrder);
		
		// 항목설정으로 검색조건 setting
		String listSearchId = "list_search";		// 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		// 연차 검색
		String yearItemName = "is_year";
		String year = queryString.getString(yearItemName);
		if(!StringUtil.isEmpty(year)) {
			param.put("YEAR", year);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		param.put("searchList", searchList);
		param.put("INSTT_IDX", insttIdx);
		
		// 목록 수 및 목록 가져오기 
		if (usertypeIdx == 40 || usertypeIdx == 55 || clsfCd.equals("Y")){
			if (usertypeIdx == 40 || usertypeIdx == 55) {
				// 본부 직원일 때
				param.put("ISHEAD", 1);
			} else {
				// 지부지사 부장일 때 해당 지부지사에 소속된 기업(HRD_BSK_PSITN_INSTT)의 신청 목록 전부 가져오기
				param.put("KEY_IDX", insttIdx);
			}
	    	// 2.3 목록
			list = clinicDctService.getList(targetTable, param);
		} else {
			// 지부지사 주치의일 때 자신이 일반주치의인 기업(HRD_COM_DOCTOR_CORP)과 자신이 전담주치의인 기업(HRD_CLI)의 신청 목록만 가져오기(일반주치의일 때는 전담주치의 지정만 가능)
			param.put("DOCTOR_IDX", doctorIdx);
			
	    	// 2.3 목록
	    	list = clinicDctService.getListForDoctor(targetTable, param);
		} 
		
    	// 3. 속성 setting
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("searchOptnHashMap", CodeHelper.getItemOptnSearchHashMap(listSearch));	// 항목 코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
    	
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
		
		return getViewPath("/excel");
	}
	/* 능력개발클리닉 계획관리 끝*/
	
	/* 능력개발클리닉 활동관리 시작*/
	
	/**
	 * [활동관리] 목록 화면
	 *   ㄴ fnIdx : 4
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/activity_list_form.do")
	public String activity_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		String targetTable = "HRD_CLI_ACMSLT";
		
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
		String listSearchId = "";
		
		// 항목설정으로 검색조건 setting
		// 본부 직원과 지부지사 직원의 검색 항목이 다름
		if(usertypeIdx == 30) {
			listSearchId = "list_search";		// 검색설정
		} else {
			listSearchId = "list_search_head";		// 검색설정
		}
	
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		// 연차 검색
		String yearItemName = "is_year";
		String year = queryString.getString(yearItemName);
		if(!StringUtil.isEmpty(year)) {
			param.put("YEAR", year);
			model.addAttribute("isSearchList", new Boolean(true));
			model.addAttribute("year", year);
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
		param.put("INSTT_IDX", insttIdx);
		
		// 목록 수 및 목록 가져오기 
		if (usertypeIdx == 40 || usertypeIdx == 55 || clsfCd.equals("Y")){
			if (usertypeIdx == 40 || usertypeIdx == 55) {
				// 본부 직원일 때
				param.put("ISHEAD", 1);
			} else {
				// 지부지사 부장일 때 해당 지부지사에 소속된 기업(HRD_BSK_PSITN_INSTT)의 신청 목록 전부 가져오기
				param.put("KEY_IDX", insttIdx);
			}
			
			// 2.2 목록수
	    	totalCount = clinicDctService.getTotalCount(targetTable, param);
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
	    		list = clinicDctService.getList(targetTable, param);
			}
		} else {
			// 지부지사 주치의일 때 자신이 일반주치의인 기업(HRD_COM_DOCTOR_CORP)과 자신이 전담주치의인 기업(HRD_CLI)의 신청 목록만 가져오기(일반주치의일 때는 전담주치의 지정만 가능)
			param.put("DOCTOR_IDX", doctorIdx);
			
			// 2.2 목록수
	    	totalCount = clinicDctService.getTotalCountForDoctor(targetTable, param);
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
	    		list = clinicDctService.getListForDoctor(targetTable, param);
			}
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
	 * 능력개발클리닉 훈련계획서 상세보기 화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/activity_view_form.do")
	public String activity_view_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int acmsltIdx = StringUtil.getInt(request.getParameter("acmsltIdx"));
		
		if(acmsltIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "view");
		
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 로그인한 회원의 정보 중 BPL_NO
		
		// 기업정보를 가져오기 위해 파라미터에서 bplNo 가져오기
		String BPL_NO = request.getParameter("bplNo");		
		
		param.put("BPL_NO", BPL_NO);
		
		// 사업장관리번호(bplNo)로 현재 cliIdx 가져오기
		int cliIdx = clinicDctService.getCliIdx(BPL_NO);
		
		// 기업정보 
		DataMap corpInfo = null;
		
		// 기업 정보 조회 조건 setting
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicDctService.getCorpInfo(fnIdx, param);
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CLI_IDX", cliIdx));
		searchList.add(new DTForm("A." + "ACMSLT_IDX", acmsltIdx));
		param.put("searchList", searchList);
		
		// 2.2 상세정보
		dt = clinicDctService.getView("HRD_CLI_ACMSLT", param);
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
		
		String uploadPath = RbsProperties.getProperty("Globals.upload.file.path");
				
		
		// 3.2 속성 setting
		model.addAttribute("dt", dt);
		model.addAttribute("submitType", submitType);											// 페이지구분
		model.addAttribute("corpInfo", corpInfo);
		model.addAttribute("uploadPath", uploadPath);
		model.addAttribute("optnHashMap", optnHashMap);									// 항목코드
		model.addAttribute("multiFileHashMap", clinicService.getMultiFileHashMap("HRD_CLI_ACMSLT_FILE", cliIdx, fnIdx, acmsltIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
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
	@RequestMapping(value = "/activity_excel.do")
	public String activity_excel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		JSONObject itemInfo = attrVO.getItemInfo();
		String targetTable = "HRD_CLI_ACMSLT";
		
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
		
		// 2. DB
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건

		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		HashMap<String, Object> optnHashMap = CodeHelper.getItemOptnHashMap(items, itemOrder);
		
		// 항목설정으로 검색조건 setting
		String listSearchId = "list_search";		// 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		// 연차 검색
		String yearItemName = "is_year";
		String year = queryString.getString(yearItemName);
		if(!StringUtil.isEmpty(year)) {
			param.put("YEAR", year);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		param.put("searchList", searchList);
		param.put("INSTT_IDX", insttIdx);
		
		// 목록 수 및 목록 가져오기 
		if (usertypeIdx == 40|| usertypeIdx == 55 || clsfCd.equals("Y")){
			if (usertypeIdx == 40 || usertypeIdx == 55) {
				// 본부 직원일 때
				param.put("ISHEAD", 1);
			} else {
				// 지부지사 부장일 때 해당 지부지사에 소속된 기업(HRD_BSK_PSITN_INSTT)의 신청 목록 전부 가져오기
				param.put("KEY_IDX", insttIdx);
			}
	    	// 2.3 목록
			list = clinicDctService.getList(targetTable, param);
		} else {
			// 지부지사 주치의일 때 자신이 일반주치의인 기업(HRD_COM_DOCTOR_CORP)과 자신이 전담주치의인 기업(HRD_CLI)의 신청 목록만 가져오기(일반주치의일 때는 전담주치의 지정만 가능)
			param.put("DOCTOR_IDX", doctorIdx);
			
	    	// 2.3 목록
	    	list = clinicDctService.getListForDoctor(targetTable, param);
		} 
		
    	// 3. 속성 setting
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("searchOptnHashMap", CodeHelper.getItemOptnSearchHashMap(listSearch));	// 항목 코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
    	
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
		
		return getViewPath("/excel");
	}
	
	/* 능력개발클리닉 활동관리 끝*/
	
	/* 능력개발클리닉 성과관리 시작*/
	/**
	 * [성과관리] 목록 화면
	 *   ㄴ fnIdx : 5
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/result_list_form.do")
	public String result_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		String targetTable = "HRD_CLI_RSLT";
		
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
		String listSearchId = "";
		
		// 항목설정으로 검색조건 setting
		// 본부 직원과 지부지사 직원의 검색 항목이 다름
		if(usertypeIdx == 30) {
			listSearchId = "list_search";		// 검색설정
		} else {
			listSearchId = "list_search_head";		// 검색설정
		}
	
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		// 연차 검색
		String yearItemName = "is_year";
		String year = queryString.getString(yearItemName);
		if(!StringUtil.isEmpty(year)) {
			param.put("YEAR", year);
			model.addAttribute("isSearchList", new Boolean(true));
			model.addAttribute("year", year);
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
		param.put("INSTT_IDX", insttIdx);
		
		// 목록 수 및 목록 가져오기 
		if (usertypeIdx == 40 || usertypeIdx == 55 || clsfCd.equals("Y")){
			if (usertypeIdx == 40 || usertypeIdx == 55) {
				// 본부 직원일 때
				param.put("ISHEAD", 1);
			} else {
				// 지부지사 부장일 때 해당 지부지사에 소속된 기업(HRD_BSK_PSITN_INSTT)의 신청 목록 전부 가져오기
				param.put("KEY_IDX", insttIdx);
			}
			
			// 2.2 목록수
	    	totalCount = clinicDctService.getTotalCount(targetTable, param);
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
	    		list = clinicDctService.getList(targetTable, param);
			}
		} else {
			// 지부지사 주치의일 때 자신이 일반주치의인 기업(HRD_COM_DOCTOR_CORP)과 자신이 전담주치의인 기업(HRD_CLI)의 신청 목록만 가져오기(일반주치의일 때는 전담주치의 지정만 가능)
			param.put("DOCTOR_IDX", doctorIdx);
			
			// 2.2 목록수
	    	totalCount = clinicDctService.getTotalCountForDoctor(targetTable, param);
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
	    		list = clinicDctService.getListForDoctor(targetTable, param);
			}
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
	 * [성과관리] 훈련계획서 상세조회
	 *  ㄴ fnIdx : 3
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/result_view_form.do")
	public String result_view_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		int fnIdx = attrVO.getFnIdx();
		JSONObject itemInfo = attrVO.getItemInfo();
		// 상세보기할 테이블
		String targetTable = "HRD_CLI_RSLT";
		
		// 1. 필수 parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("resltIdx"));
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
				
		// 2. DB
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 기업정보를 가져오기 위해 파라미터에서 bplNo 가져오기
		String BPL_NO = request.getParameter("bplNo");
		
		// 기업정보, 지원항목, HRD담당자, 연간훈련계획, 자체KPI목표
		DataMap corpInfo = null;
		List<Object> picList = null;
		List<Object> kpiList = null;
		List<Object> subList = null;
		List<Object> trList = null;
		DataMap reqList = null;
		
		param.put("BPL_NO", BPL_NO);
		
		// 사업장관리번호(bplNo)로 현재 cliIdx 가져오기
		int cliIdx = clinicDctService.getCliIdx(BPL_NO);
		
		// 기업 정보
		corpInfo = clinicDctService.getCorpInfo(fnIdx, param);
		
		searchList.add(new DTForm("A.CLI_IDX", cliIdx));
		param.put("searchList", searchList);
		
		//해당기업의 컨설팅에 대한 만족도 조사 결과 리스트
		List<Object> trainingCntList = null;
		List<Object> surveyTargetList = null;
		List<Object> answerList = null;
		trainingCntList = clinicService.getTrainingCntList(BPL_NO);
		surveyTargetList = clinicService.getSurveyTargetList(BPL_NO);
		answerList = clinicService.getAnswerList(BPL_NO);
		
		model.addAttribute("trainingCntList", trainingCntList);
		model.addAttribute("surveyTargetList", surveyTargetList);
		model.addAttribute("answerList", answerList);
		
		// 신청서 정보 가져오기
		reqList = clinicService.getView("HRD_CLI_REQ", param);
		
		// 기업 담당자와 지원항목 세부 내용은 클리닉 활동계획 테이블에서 가져오기 위해 planIdx setting
		int planIdx = clinicDctService.getMaxIdx("HRD_CLI_PLAN", "PLAN_IDX", param);
		
		// 클리닉 활동 계획 기업 담당자
		picList = clinicService.getList("HRD_CLI_PLAN_CORP_PIC", "PLAN_IDX", planIdx, "PIC_IDX");
		// 클리닉 활동 계획 세부 내용
		subList = clinicService.getList("HRD_CLI_PLAN_SUB","PLAN_IDX", planIdx, "ESSNTL_SE, A.SPORT_ITEM_CD");
		
		// KPI 이행결과와 연간 훈련실시 결과는 성과보고 테이블에서 가져옴
		// 클리닉 활동 계획 KPI 목표
		kpiList = clinicService.getList("HRD_CLI_RSLT_KPI","RESLT_IDX", keyIdx, "KPI_IDX");
		// 클리닉 활동 계획 세부 훈련계획(연간)
		trList = clinicService.getList("HRD_CLI_RSLT_TR_SUB","RESLT_IDX", keyIdx, "TR_DTL_IDX");
		
		// 상세정보를 가져오는 검색 조건 setting
		searchList.clear();
		searchList.add(new DTForm("A.CLI_IDX", cliIdx));
		searchList.add(new DTForm("A.RESLT_IDX", keyIdx));
		param.put("searchList", searchList);
		
		// 상세정보
		dt = clinicDctService.getView(targetTable, param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		String confmStatus = (String) dt.get("CONFM_STATUS");
		String contents = (String) dt.get("DOCTOR_OPINION");
		
		// 접수일 때만 textarea 줄바꿈 처리하기
		if(confmStatus.equals("30")) {
			if(contents != null) {
				contents = contents.replaceAll("<br>", "\r\n");
				dt.put("DOCTOR_OPINION", contents);
			}
		}

		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "view";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		// 3.2 속성 setting
		model.addAttribute("dt", dt);																				// 상세정보
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);			
		model.addAttribute("corpInfo", corpInfo);												// 기업정보
		model.addAttribute("reqList", reqList);
		model.addAttribute("picList", picList);
		model.addAttribute("kpiList", kpiList);
		model.addAttribute("subList", subList);
		model.addAttribute("trList", trList);		
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	/**
	 * [성과관리] 수정 화면
	 *   ㄴ fnIdx : 5
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(value = "/result_modify_form.do")
	public String result_modify_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		int fnIdx = attrVO.getFnIdx();

		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int resltIdx = StringUtil.getInt(request.getParameter("resltIdx"));
		
		if(resltIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "modify");
		
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 기업정보를 가져오기 위해 파라미터에서 bplNo 가져오기
		String BPL_NO = request.getParameter("bplNo");
		
		// 사업장관리번호(bplNo)로 현재 cliIdx 가져오기
		int cliIdx = clinicDctService.getCliIdx(BPL_NO);
		
		// 기업정보, 지원항목, HRD담당자, 연간훈련계획, 자체KPI목표
		DataMap corpInfo = null;
		List<Object> picList = null;
		List<Object> kpiList = null;
		List<Object> subList = null;
		List<Object> trList = null;
		DataMap reqList = null;
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CLI_IDX", cliIdx));
		param.put("searchList", searchList);
		
		// 작성된 신청서에서 가져온 해당 기업정보 
		reqList = clinicDctService.getModify("HRD_CLI_REQ", param);
		
		// 기업 담당자와 지원항목 세부 내용은 클리닉 활동계획 테이블에서 가져오기 위해 planIdx setting
		int planIdx = clinicDctService.getMaxIdx("HRD_CLI_PLAN", "PLAN_IDX", param);
		
		// 작성된 신청서의 정보를 가져와 세팅한 후 searchList를 비운다.
		searchList.clear();
		
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicDctService.getCorpInfo(fnIdx, param);
		
		//해당기업의 컨설팅에 대한 만족도 조사 결과 리스트
		List<Object> trainingCntList = null;
		List<Object> surveyTargetList = null;
		List<Object> answerList = null;
		trainingCntList = clinicService.getTrainingCntList(BPL_NO);
		surveyTargetList = clinicService.getSurveyTargetList(BPL_NO);
		answerList = clinicService.getAnswerList(BPL_NO);
		
		model.addAttribute("trainingCntList", trainingCntList);
		model.addAttribute("surveyTargetList", surveyTargetList);
		model.addAttribute("answerList", answerList);
		
		// 기업 담당자, 지원항목은 클리닉 활동계획 테이블에서 가져옴
		// 기업 담당자
		picList = clinicDctService.getList("HRD_CLI_PLAN_CORP_PIC", "PLAN_IDX", planIdx, "PIC_IDX");
		// 지원항목
		subList = clinicDctService.getList("HRD_CLI_PLAN_SUB","PLAN_IDX", planIdx, "ESSNTL_SE, A.SPORT_ITEM_CD");
		
		// KPI이행 결과와 연간훈련실시 결과는 클리닉 성과보고 테이블에서 가져옴
		// KPI이행 결과 목표
		kpiList = clinicDctService.getList("HRD_CLI_RSLT_KPI","RESLT_IDX", resltIdx, "KPI_IDX");
		// 연간훈련실시 결과
		trList = clinicDctService.getList("HRD_CLI_RSLT_TR_SUB","RESLT_IDX", resltIdx, "TR_DTL_IDX");
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "RESLT_IDX", resltIdx));
		param.put("searchList", searchList);
		
		// 2.2 상세정보
		dt = clinicService.getModify("HRD_CLI_RSLT", param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		// textarea 줄바꿈 처리하기
		String contents = (String) dt.get("DOCTOR_OPINION");
		if(contents != null){
			contents = contents.replaceAll("<br>", "\r\n");
			dt.put("DOCTOR_OPINION", contents);
		}
		
		String contents2 = (String) dt.get("HRD_PIC_ABILITY_DEVLOP_RESULT");
		if(contents2 != null){
			contents2 = contents2.replaceAll("<br>", "\r\n");
			dt.put("HRD_PIC_ABILITY_DEVLOP_RESULT", contents2);
		}
		
		String contents3 = (String) dt.get("SLFTR_RESULT");
		if(contents3 != null){
			contents3 = contents3.replaceAll("<br>", "\r\n");
			dt.put("SLFTR_RESULT", contents3);
		}
		
		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "modify";

		// 3.2 속성 setting
		model.addAttribute("dt", dt);
		model.addAttribute("submitType", submitType);											// 페이지구분
		model.addAttribute("corpInfo", corpInfo);
		model.addAttribute("reqList", reqList);
		model.addAttribute("picList", picList);
		model.addAttribute("kpiList", kpiList);
		model.addAttribute("subList", subList);
		model.addAttribute("trList", trList);
		model.addAttribute("optnHashMap", optnHashMap);									// 항목코드
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/input");
	}
	
	/**
	 * [성과관리] 주치의가 성과보고서 수정 (신청상태는 변하지 않음)	
	 *   ㄴ fnIdx : 5
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/result_modify.do")
	public String result_modify(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		boolean isAdmMode = attrVO.isAdmMode();
		String targetTable = "HRD_CLI_RSLT";
		String targetColumn = "RESLT_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("resltIdx"));
		
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 수정 후 상세보기 화면으로 이동하기 위해 사업장관리번호 가져오기
		String bplNo = (String) parameterMap.get("bplNo");
		
		// 사업장관리번호(bplNo)로 현재 cliIdx 가져오기
		int cliIdx = clinicDctService.getCliIdx(bplNo);

		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		searchList.add(new DTForm("A.RESLT_IDX", keyIdx));
		param.put("searchList", searchList);

		dt = clinicDctService.getModify(targetTable, param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("doctorOpinion");
		if(contents != null){
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("doctorOpinion", contents);
		}
		
		String contents2 = (String) parameterMap.get("hrdPic");
		if(contents2 != null){
			contents2 = contents2.replaceAll("\r\n", "<br>");
			parameterMap.put("hrdPic", contents2);
		}
		
		String contents3 = (String) parameterMap.get("slftr");
		if(contents3 != null){
			contents3 = contents3.replaceAll("\r\n", "<br>");
			parameterMap.put("slftr", contents3);
		}

		// 3. 필수입력 체크
		// 3.1 항목설정
		String submitType = "modify";		// 항목 order명
		String inputFlag = "modify";		// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray reqItemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		
		// 3.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, reqItemOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 4. DB
		int updateReslt = 0;
		int updateResltYearTp = 0;
		int updateResltKPI = 0;
		
		updateReslt = clinicDctService.update(targetTable, targetColumn, uploadModulePath, keyIdx, request.getRemoteAddr(), parameterMap, attrVO);
    	if(updateReslt > 0) {
    		// 지원항목, HRD담당자 정보, 연간훈련계획, 자체KPI목표 update 하기
    		updateResltYearTp = clinicDctService.deleteAndinsertResultYearTp("HRD_CLI_RSLT_TR_SUB", cliIdx, keyIdx, request.getRemoteAddr(), parameterMap);
    		updateResltKPI = clinicDctService.deleteAndinsertResultKPI("HRD_CLI_RSLT_KPI", cliIdx, keyIdx, request.getRemoteAddr(), parameterMap);
    		// 4.1 저장 성공
        	// 기본경로
    		if(updateResltYearTp > 0 && updateResltKPI > 0) {
    			fn_setCommonPath(attrVO);
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.update"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("RESULT_VIEW_FORM_URL") + "&resltIdx=" + keyIdx + "&bplNo=" + bplNo)) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.update")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * [계획관리] 목록에서 접수하기(일괄 접수)
	 *   ㄴ fnIdx : 5
	 *   ㄴ CONFM_STATUS : 30
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method = RequestMethod.GET, value = "/result_accept_all.do")
	public ModelAndView result_accept_all(@ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_RSLT";
		String targetColumn = "RESLT_IDX";
		
		// 신청상태를 insert할 targetTable(HRD_CLI_RSLT_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_RSLT_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 일괄로 접수할 cli_idx와 plan_idx
		String cliList = request.getParameter("cliList");
		String resltList = request.getParameter("resltList");
		
		// String으로 받온거 배열로 자르기
		String[] cliArray = cliList.split(",");
		String[] resltArray = resltList.split(",");
		
		int updateReslt = 0;
		int insertResltConfm = 0;
		int result = 0;
		int length = cliArray.length;
		
		// 접수(ACCEPT) 상태로 setting
		String confmStatus = ConfirmProgress.ACCEPT.getCode();
		String regiIp = request.getRemoteAddr();
		
		// 각각 접수 처리 하기
		for(int i = 0; i < length; i++) {
			int cliIdx = StringUtil.getInt(cliArray[i]);
			int resltIdx = StringUtil.getInt(resltArray[i]);
			// HRD_CLI_RSLT 테이블에 신청상태를 접수로 업데이트 하기
			updateReslt += clinicDctService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, resltIdx, regiIp);
		
			// HRD_CLI_RSLT_CONFM 테이블에 신청상태 insert하기
			insertResltConfm += clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, resltIdx, regiIp);
		}
		
		result = (updateReslt == length && insertResltConfm == length) ? 1 : 0;
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		mav.addObject("result", result);
		
		return mav;
	}

	/**
	 * [성과관리] 상세보기 화면에서 접수하기
	 *   ㄴ fnIdx : 5
	 *   ㄴ CONFM_STATUS : 30
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/result_accept.do")
	public String result_accept(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_RSLT";
		String targetColumn = "RESLT_IDX";
		
		// 신청상태를 insert할 targetTable(HRD_CLI_RSLT_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_RSLT_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("resltIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 접수 상태로 setting
		String confmStatus = ConfirmProgress.ACCEPT.getCode();

		// 접수 상태로 HRD_CLI_PLAN 신청상태 update하기
    	int updatePlan = clinicDctService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updatePlan > 0) {
    		// HRD_CLI_PLAN_CONFM 테이블에 접수 상태 insert하기
    		int updatePlanConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updatePlanConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.accept"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("RESULT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * [성과관리] 상세보기 화면에서 반려처리하기
	 *   ㄴ fnIdx : 3
	 *   ㄴ CONFM_STATUS : 40
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/result_return.do")
	public String result_return(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태, 주치의 의견을 업데이트할 targetTable
		String targetTable = "HRD_CLI_RSLT";
		String targetColumn = "RESLT_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_PLAN_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_RSLT_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("resltIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("doctorOpinion");
		if(contents != null) {
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("doctorOpinion", contents);
		}
		
		// 반려 상태로 setting
		String confmStatus = ConfirmProgress.RETURN.getCode();
		
		int updatePlan = 0;
		updatePlan = clinicDctService.updateStatusAndOpinion(targetTable, targetColumn, confmStatus, keyIdx, regiIp, parameterMap);
		
    	if(updatePlan > 0) {
    		// HRD_CLI_PLAN_CONFM 테이블에 반려 상태 insert하기
    		int updatePlanConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updatePlanConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.return"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("RESULT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * [성과관리] 상세보기 화면에서 최종승인하기
	 *   ㄴ fnIdx : 3
	 *   ㄴ CONFM_STATUS : 55
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/result_approve.do")
	public String result_approve(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_RSLT";
		String targetColumn = "RESLT_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_PLAN_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_RSLT_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("resltIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 최종승인 상태로 setting
		String confmStatus = ConfirmProgress.APPROVE.getCode();

		// 최종승인 상태로 HRD_CLI_PLAN 신청상태 update하기
    	int updatePlan = clinicDctService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updatePlan > 0) {
    		// 최종승인 후 HRD_CLI_PLAN_CHANGE 테이블에 insert하기(변경 요청을 위해서 insert)
    		clinicDctService.planToChange(cliIdx, keyIdx);
    		// HRD_CLI_PLAN_CONFM 테이블에 최종승인 상태 insert하기
    		int updatePlanConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updatePlanConfm > 0) {
				// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.approve"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("RESULT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * [성과관리] 상세보기 화면에서 중도포기처리 하기
	 *   ㄴ fnIdx : 3
	 *   ㄴ CONFM_STATUS : 80
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/result_drop.do")
	public String result_drop(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_RSLT";
		String targetColumn = "RESLT_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_RSLT_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_RSLT_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("resltIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 중도포기 상태로 setting
		String confmStatus = ConfirmProgress.DROP.getCode();

		// HRD_CLI 테이블에 ISDELETE = '1'로 업데이트(삭제처리)
		int deleteCli = clinicDctService.deleteCli("HRD_CLI", cliIdx, regiIp);
		if(deleteCli > 0) {
			// HRD_CLI_RSLT 테이블에 중도포기 상태로 신청상태 update하기
	    	int updateResult = clinicDctService.updateStatusDropRequest(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
	    	if(updateResult > 0) {
	    		// HRD_CLI_RSLT_CONFM 테이블에 중도포기 상태 insert하기
	    		int insertConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
	    		if(insertConfm > 0) {
	    			// 저장 성공
	            	// 기본경로
	            	fn_setCommonPath(attrVO);
	            	
	    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.drop"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("RESULT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
	    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
	    		}
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
	@RequestMapping(value = "/result_excel.do")
	public String result_excel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		JSONObject itemInfo = attrVO.getItemInfo();
		String targetTable = "HRD_CLI_RSLT";
		
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
		
		// 2. DB
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건

		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		HashMap<String, Object> optnHashMap = CodeHelper.getItemOptnHashMap(items, itemOrder);
		
		// 항목설정으로 검색조건 setting
		String listSearchId = "list_search";		// 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		// 연차 검색
		String yearItemName = "is_year";
		String year = queryString.getString(yearItemName);
		if(!StringUtil.isEmpty(year)) {
			param.put("YEAR", year);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		param.put("searchList", searchList);
		param.put("INSTT_IDX", insttIdx);
		
		// 목록 수 및 목록 가져오기 
		if (usertypeIdx == 40 || usertypeIdx == 55 || clsfCd.equals("Y")){
			if (usertypeIdx == 40 || usertypeIdx == 55) {
				// 본부 직원일 때
				param.put("ISHEAD", 1);
			} else {
				// 지부지사 부장일 때 해당 지부지사에 소속된 기업(HRD_BSK_PSITN_INSTT)의 신청 목록 전부 가져오기
				param.put("KEY_IDX", insttIdx);
			}
	    	// 2.3 목록
			list = clinicDctService.getList(targetTable, param);
		} else {
			// 지부지사 주치의일 때 자신이 일반주치의인 기업(HRD_COM_DOCTOR_CORP)과 자신이 전담주치의인 기업(HRD_CLI)의 신청 목록만 가져오기(일반주치의일 때는 전담주치의 지정만 가능)
			param.put("DOCTOR_IDX", doctorIdx);
			
	    	// 2.3 목록
	    	list = clinicDctService.getListForDoctor(targetTable, param);
		} 
		
    	// 3. 속성 setting
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("searchOptnHashMap", CodeHelper.getItemOptnSearchHashMap(listSearch));	// 항목 코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
    	
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
		
		return getViewPath("/excel");
	}

	/* 능력개발클리닉 성과관리 끝*/
	

	/* 능력개발클리닉 대시보드 시작 */
	
	/**
	 * [대시보드] 목록 화면
	 *   ㄴ fnIdx : 5
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/dashboard_list_form.do")
	public String dashboard_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
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
		List<Object> allCliCorpList = null;
		List<Object> myCliCorpList = null;
		List<Object> allDropCliCorpList = null;
		List<Object> myDropCliCorpList = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건

		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		String listSearchId = "";
		
		// 항목설정으로 검색조건 setting
		// 본부 직원과 지부지사 직원의 검색 항목이 다름
		if(usertypeIdx == 30) {
			listSearchId = "list_search";		// 검색설정
		} else {
			listSearchId = "list_search_head";		// 검색설정
		}
	
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		// 연차 검색
		String yearItemName = "is_year";
		String year = queryString.getString(yearItemName);
		if(!StringUtil.isEmpty(year)) {
			param.put("YEAR", year);
			model.addAttribute("isSearchList", new Boolean(true));
			model.addAttribute("year", year);
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
		param.put("INSTT_IDX", insttIdx);
		
		// 목록 수 및 목록 가져오기 
		if (usertypeIdx == 40 || usertypeIdx == 55){
			// 본부 직원일 때
			param.put("all", "Y");
			
			// 2.2 목록수
	    	totalCount = clinicDctService.getCliCorpCount(param);
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
	    		list = clinicDctService.getCliCorpList(param);
	    		
	    		allCliCorpList = clinicDctService.getAllCliCorpCountList(param);
	    		allDropCliCorpList = clinicDctService.getAllDropCliCorpCountList(param);
			}
		} else {
			// 지부지사 주치의일 때 자신이 일반주치의인 기업(HRD_COM_DOCTOR_CORP)과 자신이 전담주치의인 기업(HRD_CLI)의 신청 목록만 가져오기(일반주치의일 때는 전담주치의 지정만 가능)
			param.put("DOCTOR_IDX", doctorIdx);
			// 부장 flag
			param.put("clsfCd",clsfCd);
			
			// 2.2 목록수
	    	totalCount = clinicDctService.getCliCorpCount(param);
			paginationInfo.setTotalRecordCount(totalCount);
    		//연차별 클리닉 기업 리스트
    		myCliCorpList = clinicDctService.getMyCliCorpCountList(param);
    		myDropCliCorpList = clinicDctService.getMyDropCliCorpCountList(param);
    		param.put("all", "N");
    		allCliCorpList = clinicDctService.getAllCliCorpCountList(param);
    		allDropCliCorpList = clinicDctService.getAllDropCliCorpCountList(param);
	    	if(totalCount > 0) {
	    		if(usePaging == 1) {
		    		param.put("firstIndex", paginationInfo.getFirstRecordIndex());
		    		param.put("lastIndex", paginationInfo.getLastRecordIndex());
		    		param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
		    	}
	    		
	    		// 정렬컬럼 setting
	    		param.put("dsetOrderField", JSONObjectUtil.getString(settingInfo, "dset_order_field"));
	    		
	    		// 2.3 목록
	    		list = clinicDctService.getCliCorpList(param);
			}
		} 
		
    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("allCliCorpCountList", allCliCorpList);									// 연차별 지부지사 클리닉 기업 수 목록
    	model.addAttribute("myCliCorpCountList", myCliCorpList);									// 연차별 나의 클리닉 기업 수 목록
    	model.addAttribute("allDropCliCorpCountList", allDropCliCorpList);							// 연차별 중도포기한 지부지사 클리닉 기업 수 목록
    	model.addAttribute("myDropCliCorpCountList", myDropCliCorpList);							// 연차별 중도포기한 나의 클리닉 기업 수 목록
    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
	
	/**
	 * [대시보드] 기업 상세 현황 화면
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/dashboard_detail_form.do")
	public String dashboard_detail_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAdmMode = attrVO.isAdmMode();		
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		
		//BPL_NO
		String BPL_NO = StringUtil.getString(request.getParameter("bpl_no"));
		
		
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
		List<Object> activityList = null;
		List<Object> planProgress = null;
		int activityCount= 0;
		List<Object> resultProgress = null;
		List<Object> supportProgress = null;
		DataMap corpInfo = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		int cliIdx  = clinicService.getCurrentCliIdx(BPL_NO);
		param.put("CLI_IDX", cliIdx);
		param.put("BPL_NO",BPL_NO);


		// 기업 정보
		corpInfo = clinicService.getCorpInfo(99, param);
		// 2.3 목록
		
		
		String yearly = StringUtil.getString(request.getParameter("is_year"));
		String essntl_se = StringUtil.getString(request.getParameter("is_essntl"));
		String gubunCd = StringUtil.getString(request.getParameter("is_actType"));    		
		
		param.put("YEARLY", yearly);
		param.put("ESSNTL_SE", essntl_se);
		param.put("GUBUNCD", gubunCd);
		// 주요 활동일지 목록 
		activityList = clinicService.getActivityList(param);
		// 훈련계획 수립
		planProgress = clinicService.getPlanProgress(cliIdx);
		// 활동일지
		activityCount= clinicService.getActivityCount(cliIdx);
		// 성과보고
		resultProgress = clinicService.getResultProgress(cliIdx);
		// 비용청구
		supportProgress = clinicService.getSupportProgress(cliIdx);
		

    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("corpInfo", corpInfo);																	// 기업정보
    	model.addAttribute("activityList", activityList);															// 목록
    	model.addAttribute("planProgress", planProgress);															// 목록
    	model.addAttribute("activityCount", activityCount);															// 목록
    	model.addAttribute("resultProgress", resultProgress);															// 목록
    	model.addAttribute("supportProgress", supportProgress);															// 목록
    	model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
    	model.addAttribute("optnHashMap", getOptionHashMap("list", itemInfo));    	

 
    	
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/detailList");
	}
	
	
	
	/* 
	 * [대시보드] 기업 상세 현황 화면 검색
	 */ 
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.GET, value = "/dashboard_detail_form.do", params="isAjax")
	public ModelAndView dashboard_detail_form( @RequestParam("isAjax") String isAjax, @ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = StringUtil.getString(request.getParameter("bpl_no"));
		
		
		List<Object> activityList = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		int cliIdx  = clinicService.getCurrentCliIdx(BPL_NO);
		param.put("CLI_IDX", cliIdx);
		param.put("BPL_NO",BPL_NO);
		
		String yearly = StringUtil.getString(request.getParameter("is_year"));
		String essntl_se = StringUtil.getString(request.getParameter("is_essntl"));
		String gubunCd = StringUtil.getString(request.getParameter("is_actType"));    		
		
		param.put("YEARLY", yearly);
		param.put("ESSNTL_SE", essntl_se);
		param.put("GUBUNCD", gubunCd);
		// 주요 활동일지 목록 
		activityList = clinicService.getActivityList(param);
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		mav.addObject("activityList", activityList);
		
		return mav;
	}
	
	/* 능력개발클리닉 대시보드 끝 */
	
	/* 능력개발클리닉 종합관리 시작 */
	
	/**
	 * [종합관리] 목록 화면
	 *   ㄴ fnIdx : 5
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
		List<Object> allCliCorpList = null;
		List<Object> myCliCorpList = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건

		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		String listSearchId = "";
		
		// 항목설정으로 검색조건 setting
		// 본부 직원과 지부지사 직원의 검색 항목이 다름
		if(usertypeIdx == 30) {
			listSearchId = "list_search";		// 검색설정
		} else {
			listSearchId = "list_search_head";		// 검색설정
		}
	
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		// 연차 검색
		String yearItemName = "is_year";
		String year = queryString.getString(yearItemName);
		if(!StringUtil.isEmpty(year)) {
			param.put("YEAR", year);
			model.addAttribute("isSearchList", new Boolean(true));
			model.addAttribute("year", year);
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
		param.put("INSTT_IDX", insttIdx);
		
		// 목록 수 및 목록 가져오기 
		if (usertypeIdx == 40 || usertypeIdx == 55){
			// 본부 직원일 때
			param.put("all", "Y");
			
			// 2.2 목록수
	    	totalCount = clinicDctService.getCliCorpCount(param);
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
	    		list = clinicDctService.getCliCorpList(param);
	    		
	    		allCliCorpList = clinicDctService.getAllCliCorpCountList(param);
			}
		} else {
			// 지부지사 주치의일 때 자신이 일반주치의인 기업(HRD_COM_DOCTOR_CORP)과 자신이 전담주치의인 기업(HRD_CLI)의 신청 목록만 가져오기(일반주치의일 때는 전담주치의 지정만 가능)
			param.put("DOCTOR_IDX", doctorIdx);
			// 부장 flag
			param.put("clsfCd",clsfCd);
			
			// 2.2 목록수
	    	totalCount = clinicDctService.getCliCorpCount(param);
			paginationInfo.setTotalRecordCount(totalCount);
    		//연차별 클리닉 기업 리스트
    		myCliCorpList = clinicDctService.getMyCliCorpCountList(param);
    		param.put("all", "N");
    		allCliCorpList = clinicDctService.getAllCliCorpCountList(param);
	    	if(totalCount > 0) {
	    		if(usePaging == 1) {
		    		param.put("firstIndex", paginationInfo.getFirstRecordIndex());
		    		param.put("lastIndex", paginationInfo.getLastRecordIndex());
		    		param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
		    	}
	    		
	    		// 정렬컬럼 setting
	    		param.put("dsetOrderField", JSONObjectUtil.getString(settingInfo, "dset_order_field"));
	    		
	    		// 2.3 목록
	    		list = clinicDctService.getCliCorpList(param);
	    		

			}
		} 
		
    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("allCliCorpCountList", allCliCorpList);										// 연차별 지부지사 클리닉 기업 수 목록
    	model.addAttribute("myCliCorpCountList", myCliCorpList);											// 연차별 나의 클리닉 기업 수 목록
    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
	

	/**
	 * [종합관리] 상세 화면
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/total_detail_form.do")
	public String total_detail_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAdmMode = attrVO.isAdmMode();		
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		// 로그인한 회원의 정보 가져오기 
		
		//BPL_NO
		String BPL_NO = StringUtil.getString(request.getParameter("bpl_no"));
		
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
		List<Object> activityList = null;
		List<Object> planProgress = null;
		int activityCount= 0;
		List<Object> resultProgress = null;
		List<Object> supportProgress = null;
		DataMap corpInfo = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		int cliIdx  = clinicService.getCurrentCliIdx(BPL_NO);
		param.put("CLI_IDX", cliIdx);
		param.put("BPL_NO",BPL_NO);
		param.put("total", "Y");


		// 기업 정보
		corpInfo = clinicService.getCorpInfo(99, param);
		// 2.3 목록
		
		
		String yearly = StringUtil.getString(request.getParameter("is_year"));
		String essntl_se = StringUtil.getString(request.getParameter("is_essntl"));
		String gubunCd = StringUtil.getString(request.getParameter("is_actType"));    		
		
		param.put("YEARLY", yearly);
		param.put("ESSNTL_SE", essntl_se);
		param.put("GUBUNCD", gubunCd);
		param.put("DCTFLAG","Y");
		// 주요 활동일지 목록 
		activityList = clinicService.getActivityList(param);
		// 훈련계획 수립
		planProgress = clinicService.getPlanProgress(cliIdx);
		// 활동일지
		activityCount= clinicService.getActivityCount(cliIdx);
		// 성과보고
		resultProgress = clinicService.getResultProgress(cliIdx);
		// 비용청구
		supportProgress = clinicService.getSupportProgress(cliIdx);
		

    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("corpInfo", corpInfo);																	// 기업정보
    	model.addAttribute("activityList", activityList);															// 목록
    	model.addAttribute("planProgress", planProgress);															// 목록
    	model.addAttribute("activityCount", activityCount);															// 목록
    	model.addAttribute("resultProgress", resultProgress);															// 목록
    	model.addAttribute("supportProgress", supportProgress);															// 목록
    	model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
    	model.addAttribute("optnHashMap", getOptionHashMap("list", itemInfo));    	

 
    	
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/detailList");
	}
	
	
	/* 
	 * [종합관리] 상세 화면 검색
	 */ 
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.GET, value = "/total_detail_form.do", params="isAjax")
	public ModelAndView total_detail_form( @RequestParam("isAjax") String isAjax, @ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		//BPL_NO
		String BPL_NO = StringUtil.getString(request.getParameter("bpl_no"));
		
		
		List<Object> activityList = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		int cliIdx  = clinicService.getCurrentCliIdx(BPL_NO);
		param.put("CLI_IDX", cliIdx);
		param.put("BPL_NO",BPL_NO);
		
		
		String yearly = StringUtil.getString(request.getParameter("is_year"));
		String essntl_se = StringUtil.getString(request.getParameter("is_essntl"));
		String gubunCd = StringUtil.getString(request.getParameter("is_actType"));    
		
		param.put("YEARLY", yearly);
		param.put("ESSNTL_SE", essntl_se);
		param.put("GUBUNCD", gubunCd);
		param.put("total", "Y");
		// 주요 활동일지 목록 
		activityList = clinicService.getActivityList(param);
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		mav.addObject("activityList", activityList);
		
		return mav;
	}
	
	
	/* 능력개발클리닉 종합관리 끝 */
	
	/* 능력개발클리닉 비용관리 시작*/
	/**
	 * 비용관리  목록 화면
	 *   ㄴ fnIdx : 6
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
		String targetTable = "HRD_CLI_SPT";
		
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
		// 지원금 신청서 
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건

		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		String listSearchId = "";
		
		// 항목설정으로 검색조건 setting
		// 본부 직원과 지부지사 직원의 검색 항목이 다름
		if(usertypeIdx == 30) {
			listSearchId = "list_search";		// 검색설정
		} else {
			listSearchId = "list_search_head";		// 검색설정
		}
	
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		// 연차 검색
		String yearItemName = "is_year";
		String year = queryString.getString(yearItemName);
		if(!StringUtil.isEmpty(year)) {
			param.put("YEAR", year);
			model.addAttribute("isSearchList", new Boolean(true));
			model.addAttribute("year", year);
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
		param.put("INSTT_IDX", insttIdx);
		
		// 목록 수 및 목록 가져오기 
		if (usertypeIdx == 40 || usertypeIdx == 55 || clsfCd.equals("Y")){
			if (usertypeIdx == 40 || usertypeIdx == 55) {
				// 본부 직원일 때
				param.put("ISHEAD", 1);
			} else {
				// 지부지사 부장일 때 해당 지부지사에 소속된 기업(HRD_BSK_PSITN_INSTT)의 신청 목록 전부 가져오기
				param.put("KEY_IDX", insttIdx);
			}
			
			// 2.2 목록수
	    	totalCount = clinicDctService.getTotalCount(targetTable, param);
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
	    		list = clinicDctService.getList(targetTable, param);
			}
		} else {
			// 지부지사 주치의일 때 자신이 일반주치의인 기업(HRD_COM_DOCTOR_CORP)과 자신이 전담주치의인 기업(HRD_CLI)의 신청 목록만 가져오기(일반주치의일 때는 전담주치의 지정만 가능)
			param.put("DOCTOR_IDX", doctorIdx);
			
			// 2.2 목록수
	    	totalCount = clinicDctService.getTotalCountForDoctor(targetTable, param);
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
	    		list = clinicDctService.getListForDoctor(targetTable, param);
			}
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
	 * 지원금 신청서 상세조회
	 *  ㄴ fnIdx : 6
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/support_view_form.do")
	public String support_view_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		int fnIdx = attrVO.getFnIdx();
		JSONObject itemInfo = attrVO.getItemInfo();
		// 상세보기할 테이블
		String targetTable = "HRD_CLI_SPT";
		
		// 1. 필수 parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("sportIdx"));
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
				
		// 2. DB
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 기업정보를 가져오기 위해 파라미터에서 bplNo 가져오기
		String BPL_NO = request.getParameter("bplNo");
		
		// 기업정보 , 체크리스트 목록 및 답변 목록 정보
		DataMap corpInfo = null;
		
		param.put("BPL_NO", BPL_NO);
		
		// 사업장관리번호(bplNo)로 현재 cliIdx 가져오기
		int cliIdx = clinicDctService.getCliIdx(BPL_NO);
		
		// 기업 정보
		corpInfo = clinicDctService.getCorpInfo(fnIdx, param);
		
		// 기업정보 
		DataMap reqList = null;
		
		//지원항목 + 금액
		List<Object> sportList = null;
		List<Object> subList = null;
		List<Object> subListGroup = null;
		
		// 당해연도 기지원금액
		List<Object> alreadyPayList = null;
		alreadyPayList = clinicService.getSptPayList(cliIdx, keyIdx);
		
		sportList = clinicService.getSportList();
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CLI_IDX", cliIdx));
		param.put("searchList", searchList);

		// 작성된 신청서에서 가져온 해당 기업정보 
		reqList = clinicService.getModify("HRD_CLI_REQ", param);
		
		// 2.2 상세정보
		searchList.add(new DTForm("A." + "SPORT_IDX", keyIdx));
		param.put("searchList", searchList);
		
		// 상세정보
		dt = clinicService.getView(targetTable, param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		String confmStatus = (String) dt.get("CONFM_STATUS");
		String contents = (String) dt.get("DOCTOR_OPINION");
		
		// 접수, 반려요청, 지부지사부장&1차승인 일 때만 textarea 줄바꿈 처리하기
		if(confmStatus.equals("30") || confmStatus.equals("35") || (loginVO.getClsfCd().equals("Y") && confmStatus.equals("50"))) {
			if(contents != null) {
				contents = contents.replaceAll("<br>", "\r\n");
				dt.put("DOCTOR_OPINION", contents);
			}
		}

		subList = clinicService.getList("HRD_CLI_SPT_SUB","SPORT_IDX", keyIdx, "DTL_SN");
		subListGroup = clinicService.getSptSubGroupBy(cliIdx, keyIdx);
		
		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "view";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		// 3.2 속성 setting
		model.addAttribute("dt", dt);																				// 상세정보
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);			
		model.addAttribute("sportList", sportList);
		model.addAttribute("alreadyPayList", alreadyPayList);
		model.addAttribute("corpInfo", corpInfo);
		model.addAttribute("reqList", reqList);
		model.addAttribute("subList", subList);
		model.addAttribute("subListGroup", subListGroup);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	/**
	 * 비용관리 수정 화면
	 *   ㄴ fnIdx : 6
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(value = "/support_modify_form.do")
	public String support_modify_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		int fnIdx = attrVO.getFnIdx();
		JSONObject itemInfo = attrVO.getItemInfo();
		String targetTable = "HRD_CLI_SPT";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("sportIdx"));
		
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 기업정보를 가져오기 위해 파라미터에서 bplNo 가져오기
		String BPL_NO = request.getParameter("bplNo");
		
		// 사업장관리번호(bplNo)로 현재 cliIdx 가져오기
		int cliIdx = clinicDctService.getCliIdx(BPL_NO);
		
		// 기업정보 , 체크리스트 목록 및 답변 목록 정보
		DataMap corpInfo = null;
		
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicDctService.getCorpInfo(fnIdx, param);
		
		// 기업정보 
		DataMap reqList = null;
		
		//지원항목 + 금액
		List<Object> sportList = null;
		List<Object> subList = null;
		List<Object> subListGroup = null;
		
		sportList = clinicService.getSportList();
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CLI_IDX", cliIdx));
		param.put("searchList", searchList);

		// 작성된 신청서에서 가져온 해당 기업정보 
		reqList = clinicService.getModify("HRD_CLI_REQ", param);
		
		// 2.2 상세정보
		searchList.add(new DTForm("A." + "SPORT_IDX", keyIdx));
		param.put("searchList", searchList);
		
		// 2.2 상세정보
		dt = clinicDctService.getModify(targetTable, param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		// textarea 줄바꿈 처리하기
		String contents = (String) dt.get("DOCTOR_OPINION");
		if(contents != null) {
			contents = contents.replaceAll("<br>", "\r\n");
			dt.put("DOCTOR_OPINION", contents);
		}
		
		subList = clinicService.getList("HRD_CLI_SPT_SUB","SPORT_IDX", keyIdx, "DTL_SN");
		subListGroup = clinicService.getSptSubGroupBy(cliIdx, keyIdx);
		
		//훈련계획수립  List
		List<Object> activityCode01 = null;
		//HRD역량개발 List		
		List<Object> activityCode04 = null;
		//클리닉 성과보고 List		
		List<Object> activityCode05 = null;
		//인력채용/판로개척 List
		List<Object> activityCode08 = null;
		//성과교류 대내외 List
		List<Object> activityCode09 = null;
		
		sportList = clinicService.getSportList();
		activityCode01 = clinicService.getPlanCode(cliIdx, keyIdx);
		activityCode04 = clinicService.getActivityCode(cliIdx, "04", keyIdx);
		activityCode05 = clinicService.getResultCode(cliIdx, keyIdx);
		activityCode08 = clinicService.getActivityCode(cliIdx, "08", keyIdx);
		activityCode09 = clinicService.getActivityCode(cliIdx, "09", keyIdx);
		
		// 훈련계획서에서 선택한 지원항목 목록 가져오기
		List<Object> planSubList = null;
		int planIdx = clinicService.getMaxIdx("HRD_CLI_PLAN", "PLAN_IDX", cliIdx);
		planSubList = clinicDctService.getList("HRD_CLI_PLAN_SUB","PLAN_IDX", planIdx, "ESSNTL_SE, A.SPORT_ITEM_CD");
		
		// 당해연도 기지원금액
		List<Object> alreadyPayList = null;
		alreadyPayList = clinicService.getSptPayList(cliIdx, keyIdx);
		
		// 성과보고서 최종승인 유무
		int isResultApprove = clinicService.getIsApprove("HRD_CLI_RSLT", cliIdx);
		
		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "modify";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");

		// 3.2 속성 setting
		model.addAttribute("dt", dt);
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));		// 항목코드
		model.addAttribute("submitType", submitType);											// 페이지구분
		model.addAttribute("sportList", sportList);
		model.addAttribute("corpInfo", corpInfo);
		model.addAttribute("reqList", reqList);
		model.addAttribute("subList", subList);
		model.addAttribute("activityList01", activityCode01);
    	model.addAttribute("activityList04", activityCode04);
    	model.addAttribute("activityList05", activityCode05);
    	model.addAttribute("activityList08", activityCode08);
    	model.addAttribute("activityList09", activityCode09);
		model.addAttribute("subListGroup", subListGroup);											
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		model.addAttribute("isResultApprove", isResultApprove);
		model.addAttribute("alreadyPayList", alreadyPayList);
		model.addAttribute("planSubList", planSubList);
		
		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/input");
	}
	
	/**
	 * 주치의가 지원금 신청서 수정 (신청상태는 변하지 않음)	
	 *   ㄴ fnIdx : 6
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
		String targetTable = "HRD_CLI_SPT";
		String targetColumn = "SPORT_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt((String) parameterMap.get("sportIdx"));
		int cliIdx = StringUtil.getInt((String) parameterMap.get("cliIdx"));

		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 수정 후 상세보기 화면으로 이동하기 위해 사업장관리번호 가져오기
		String bplNo = (String) parameterMap.get("bplNo");
		
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		searchList.add(new DTForm("A.SPORT_IDX", keyIdx));
		param.put("searchList", searchList);

		dt = clinicDctService.getModify(targetTable, param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("doctorOpinion");
		if(contents != null) {
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("doctorOpinion", contents);
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
		int updateSptSub = 0;
		
		updateSpt = clinicDctService.update(targetTable, targetColumn, uploadModulePath, keyIdx, request.getRemoteAddr(), parameterMap, attrVO);
    	if(updateSpt > 0) {
    		updateSptSub = clinicDctService.deleteAndinsertSptSub("HRD_CLI_SPT_SUB", cliIdx, keyIdx, request.getRemoteAddr(), parameterMap);
    		// 4.1 저장 성공
        	// 기본경로
    		if(updateSptSub > 0 ) {
    			fn_setCommonPath(attrVO);
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.update"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("SUPPORT_VIEW_FORM_URL") + "&sportIdx=" + keyIdx + "&bplNo=" + bplNo)) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 비용관리 목록에서 접수하기(일괄 접수)
	 *   ㄴ fnIdx : 6
	 *   ㄴ CONFM_STATUS : 30
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method = RequestMethod.GET, value = "/support_accept_all.do")
	public ModelAndView support_accept_all(@ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_SPT";
		String targetColumn = "SPORT_IDX";
		
		// 신청상태를 insert할 targetTable(HRD_CLI_REQ_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_SPT_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 일괄로 접수할 cli_idx와 req_idx, 각각의 cli_idx에 지정될 전담주치의 정보
		String cliList = request.getParameter("cliList");
		String sptList = request.getParameter("sptList");
		
		// String으로 받온거 배열로 자르기
		String[] cliArray = cliList.split(",");
		String[] sptArray = sptList.split(",");
		
		int updateSpt = 0;
		int insertSptConfm = 0;
		int result = 0;
		int length = cliArray.length;
		
		// 접수(ACCEPT) 상태로 setting
		String confmStatus = ConfirmProgress.ACCEPT.getCode();
		String regiIp = request.getRemoteAddr();
		
		// 각각 접수 처리 하기
		for(int i = 0; i < length; i++) {
			int cliIdx = StringUtil.getInt(cliArray[i]);
			int sptIdx = StringUtil.getInt(sptArray[i]);
			// HRD_CLI_SPT 테이블에 신청상태를 접수로 업데이트 하기
			updateSpt += clinicDctService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, sptIdx, regiIp);
		
			// HRD_CLI_SPT_CONFM 테이블에 신청상태 insert하기
			insertSptConfm += clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, sptIdx, regiIp);
		}
		
		result = (updateSpt == length && insertSptConfm == length) ? 1 : 0;
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		mav.addObject("result", result);
		
		return mav;
	}
	
	/**
	 * 비용관리 상세보기 화면에서 접수하기
	 *   ㄴ fnIdx : 6
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
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_SPT";
		String targetColumn = "SPORT_IDX";
		
		// 신청상태를 insert할 targetTable(HRD_CLI_SPT_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_SPT_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("sportIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 접수 상태로 setting
		String confmStatus = ConfirmProgress.ACCEPT.getCode();

		// 접수 상태로 HRD_CLI_SPT 신청상태 update하기
    	int updateSpt = clinicDctService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updateSpt > 0) {
    		// HRD_CLI_SPT_CONFM 테이블에 접수 상태 insert하기
    		int updateSptConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updateSptConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.accept"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 비용관리 상세보기 화면에서 반려처리하기
	 *   ㄴ fnIdx : 6
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
		// 신청상태, 주치의 의견을 업데이트할 targetTable
		String targetTable = "HRD_CLI_SPT";
		String targetColumn = "SPORT_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_SPT_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_SPT_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("sportIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("doctorOpinion");
		if(contents != null) {
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("doctorOpinion", contents);
		}
		
		// 반려 상태로 setting
		String confmStatus = ConfirmProgress.RETURN.getCode();
				
		int updateSpt = 0;
		// HRD_CLI_SPT 테이블에 주치의 의견과 반려 상태 update하기
		updateSpt = clinicDctService.updateStatusAndOpinion(targetTable, targetColumn, confmStatus, keyIdx, regiIp, parameterMap);
		
    	if(updateSpt > 0) {
    		// HRD_CLI_SPT_CONFM 테이블에 반려 상태 insert하기
    		int updateSptConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updateSptConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.return"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 비용관리 상세보기 화면에서 1차승인하기
	 *   ㄴ fnIdx : 6
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
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_SPT";
		String targetColumn = "SPORT_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_SPT_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_SPT_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("sportIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));
		
		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 1차승인 상태로 setting
		String confmStatus = ConfirmProgress.FIRSTAPPROVE.getCode();
		
		// 1차승인 상태로 HRD_CLI_SPT 신청상태 update하기
    	int updateSpt = clinicDctService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
		
    	if(updateSpt > 0) {
    		// HRD_CLI_SPT_CONFM 테이블에 1차승인 상태 insert하기
    		int updateSptConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updateSptConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.firstapprove"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 비용관리 상세보기 화면에서 부장이 최종승인 안하고 반려하기 > 접수상태로 돌아감
	 *   ㄴ fnIdx : 6
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
		// 신청상태, 주치의 의견을 업데이트할 targetTable
		String targetTable = "HRD_CLI_SPT";
		String targetColumn = "SPORT_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_SPT_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_SPT_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("sportIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("doctorOpinion");
		if(contents != null) {
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("doctorOpinion", contents);
		}
		
		// 접수 상태로 setting
		String confmStatus = ConfirmProgress.ACCEPT.getCode();
				
		int updateSpt = 0;
		// HRD_CLI_SPT 테이블에 주치의 의견과 신청 상태 update하기
		updateSpt = clinicDctService.updateStatusAndOpinion(targetTable, targetColumn, confmStatus, keyIdx, regiIp, parameterMap);
		
    	if(updateSpt > 0) {
    		// HRD_CLI_SPT_CONFM 테이블에 접수 상태 insert하기
    		int updateSptConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updateSptConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.return"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
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
	@RequestMapping(method=RequestMethod.GET, value = "/support_approve.do")
	public String support_approve(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_SPT";
		String targetColumn = "SPORT_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_SPT_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_SPT_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("sportIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 최종승인 상태로 setting
		String confmStatus = ConfirmProgress.APPROVE.getCode();

		// 최종승인 상태로 HRD_CLI_SPT 신청상태 update하기
    	int updateSpt = clinicDctService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updateSpt > 0) {
    		// HRD_CLI_SPT_CONFM 테이블에 최종승인 상태 insert하기
    		int updateSptConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updateSptConfm > 0) {
				// 저장 성공
	        	// 기본경로
	        	fn_setCommonPath(attrVO);
	        	
				model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.approve"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
				return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 신청관리 상세보기 화면에서 중도포기처리 하기
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 75
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/support_dropRequest.do")
	public String support_dropRequest(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_SPT";
		String targetColumn = "SPORT_IDX";
				
		// 신청상태를 insert할 targetTable(HRD_CLI_SPT_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_SPT_CONFM";
		String targetColumn2 = "CONFM_IDX";
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("sportIdx"));
		int cliIdx = StringUtil.getInt(request.getParameter("cliIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 중도포기 상태로 setting
		String confmStatus = ConfirmProgress.DROP.getCode();

		// HRD_CLI 테이블에 ISDELETE = '1'로 업데이트(삭제처리)
		int deleteCli = clinicDctService.deleteCli("HRD_CLI", cliIdx, regiIp);
		if(deleteCli > 0) {
			// HRD_CLI_SPT 테이블에 중도포기 상태로 신청상태 update하기
	    	int updateSpt = clinicDctService.updateStatusDropRequest(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
	    	if(updateSpt > 0) {
	    		// HRD_CLI_SPT_CONFM 테이블에 중도포기 상태 insert하기
	    		int updateSptConfm = clinicDctService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
	    		if(updateSptConfm > 0) {
	    			// 저장 성공
	            	// 기본경로
	            	fn_setCommonPath(attrVO);
	            	
	    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.drop"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
	    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
	    		}
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
	@RequestMapping(value = "/support_excel.do")
	public String support_excel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		JSONObject itemInfo = attrVO.getItemInfo();
		String targetTable = "HRD_CLI_SPT";
		
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
		
		// 2. DB
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건

		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		HashMap<String, Object> optnHashMap = CodeHelper.getItemOptnHashMap(items, itemOrder);
		
		// 항목설정으로 검색조건 setting
		String listSearchId = "list_search";		// 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		// 연차 검색
		String yearItemName = "is_year";
		String year = queryString.getString(yearItemName);
		if(!StringUtil.isEmpty(year)) {
			param.put("YEAR", year);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		param.put("searchList", searchList);
		param.put("INSTT_IDX", insttIdx);
		
		// 목록 수 및 목록 가져오기 
		if (usertypeIdx == 40 || usertypeIdx == 55 || clsfCd.equals("Y")){
			if (usertypeIdx == 40 || usertypeIdx == 55) {
				// 본부 직원일 때
				param.put("ISHEAD", 1);
			} else {
				// 지부지사 부장일 때 해당 지부지사에 소속된 기업(HRD_BSK_PSITN_INSTT)의 신청 목록 전부 가져오기
				param.put("KEY_IDX", insttIdx);
			}
	    	// 2.3 목록
			list = clinicDctService.getList(targetTable, param);
		} else {
			// 지부지사 주치의일 때 자신이 일반주치의인 기업(HRD_COM_DOCTOR_CORP)과 자신이 전담주치의인 기업(HRD_CLI)의 신청 목록만 가져오기(일반주치의일 때는 전담주치의 지정만 가능)
			param.put("DOCTOR_IDX", doctorIdx);
			
	    	// 2.3 목록
	    	list = clinicDctService.getListForDoctor(targetTable, param);
		} 
		
    	// 3. 속성 setting
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("searchOptnHashMap", CodeHelper.getItemOptnSearchHashMap(listSearch));	// 항목 코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
    	
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
		
		return getViewPath("/excel");
	}
	
	/* 능력개발클리닉 비용관리 끝*/
//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ밑에부터 리펙토링 전 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ	
/*	*//**
	 * 목록조회
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 *//*
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
		
		int insttIdx = 0;
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		if(loginVO != null){
			//로그인한 회원의 regiCode(Hrd4u의 지부지사코드)로 insttIdx(주치의서비스의 지부지사코드)를 가져온다
			insttIdx = basketService.getInsttIdx(loginVO.getRegiCode());
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
		
		//로그인한 계정의 지부지사에 해당하는 모든 주치의 list
		List<?> doctorList = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		param.put("INSTTIDX", insttIdx);
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
    	totalCount = clinicDctService.getTotalCount(fnIdx, param);
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
    		list = clinicDctService.getList(fnIdx, param);
    		doctorList = clinicDctService.getDoctorList(param);
		}

    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("doctorList", doctorList);															// 목록
    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}

	*//**
	 * 상세조회
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 *//*
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(value = "/view.do")
	public String view(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		// 1. 필수 parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name")), 0);
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		String idxColumnName = JSONObjectUtil.getString(settingInfo, "idx_column");
		
		// 2. DB
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 기업정보를 가져오기 위해 파라미터에서 bplNo 가져오기
		String BPL_NO = request.getParameter("bplNo");
		
		// 기업정보 , 체크리스트 목록 및 답변 목록 정보
		DataMap corpInfo = null;
		List<Object> checkList = null;
		List<Object> checkListAnswer = null;
		List<Object> jdgMnTabList = null;
		List<Object> jdgMnTabListAnswer = null;
		
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);
		
		// 체크리스트 목록
		checkList = clinicService.getCheckList(fnIdx, param);
		
		// 기업 선정 심사표 목록
		jdgMnTabList = clinicDctService.getJdgmntabList(fnIdx, param);

		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		param.put("searchList", searchList);
		
		// 기업이 체크한 체크리스트를 가져오기(Y/N값 체크용)
		checkListAnswer = clinicService.getCheckListAnswer(fnIdx, param);
		
		// 기업 선정 심사표 답변 목록
		jdgMnTabListAnswer = clinicDctService.getJdgmntabListAnswer(fnIdx, param);
		
		// 상세정보
		dt = clinicDctService.getView(fnIdx, param);
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
		model.addAttribute("multiFileHashMap", clinicDctService.getMultiFileHashMap(fnIdx, keyIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("multiDataHashMap", clinicDctService.getMultiHashMap(fnIdx, keyIdx, settingInfo, items, itemOrder));		// multi file 목록
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);			
		model.addAttribute("corpInfo", corpInfo);												// 기업정보
		model.addAttribute("checkList", checkList);												// 기업 자가 체크리스트 목록
		model.addAttribute("checkListAnswer", checkListAnswer);									// 기업 자가 체크리스트 답변 목록
		model.addAttribute("jdgMnTabList", jdgMnTabList);										// 기업 선정 심사표 정보
		model.addAttribute("jdgMnTabListAnswer", jdgMnTabListAnswer);							// 기업 선정 심사표 답변 정보
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	*//**
	 * 수정 : 관리권한 없는 경우 - 자신이 등록한 글만 수정
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 *//*
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

		// 기업정보를 가져오기 위해 파라미터에서 bplNo 가져오기
		String BPL_NO = request.getParameter("bplNo");
		
		// 기업정보 , 체크리스트 목록 및 답변 목록 정보
		DataMap corpInfo = null;
		List<Object> checkList = null;
		List<Object> checkListAnswer = null;
		List<Object> jdgMnTabList = null;
		List<Object> jdgMnTabListAnswer = null;
		
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);
		
		// 체크리스트 목록
		checkList = clinicService.getCheckList(fnIdx, param);
		
		// 기업 선정 심사표 목록
		jdgMnTabList = clinicDctService.getJdgmntabList(fnIdx, param);
		
		// 2.1 수정권한 체크
		Boolean isMngAuth = isMngProcAuth(settingInfo, fnIdx, keyIdx);
		if(!isMngAuth) {
			// 수정권한 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.auth")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		param.put("searchList", searchList);
		
		// 기업이 체크한 체크리스트를 가져오기(Y/N값 체크용)
		checkListAnswer = clinicService.getCheckListAnswer(fnIdx, param);
		
		// 기업 선정 심사표 답변 목록
		jdgMnTabListAnswer = clinicDctService.getJdgmntabListAnswer(fnIdx, param);
		
		// 2.2 상세정보
		dt = clinicDctService.getModify(fnIdx, param);
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
		model.addAttribute("multiDataHashMap", clinicDctService.getMultiHashMap(fnIdx, keyIdx, settingInfo, items, itemOrder));		// multi file 목록
		model.addAttribute("multiFileHashMap", clinicDctService.getMultiFileHashMap(fnIdx, keyIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));		// 항목코드
		model.addAttribute("submitType", submitType);											// 페이지구분
		model.addAttribute("corpInfo", corpInfo);												// 기업정보
		model.addAttribute("checkList", checkList);												// 기업 자가 체크리스트 목록
		model.addAttribute("checkListAnswer", checkListAnswer);									// 기업 자가 체크리스트 답변 목록
		model.addAttribute("jdgMnTabList", jdgMnTabList);										// 기업 선정 심사표 정보
		model.addAttribute("jdgMnTabListAnswer", jdgMnTabListAnswer);							// 기업 선정 심사표 답변 정보
		
		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	// 5. form action
    	model.addAttribute("URL_SUBMITPROC", request.getAttribute("URL_IDX_MODIFYPROC"));
		
    	return getViewPath("/input");
	}
	
	*//**
	 * 등록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 *//*
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

	*//**
	 * 수정처리 : 관리권한 없는 경우 - 자신이 등록한 글만 수정
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 *//*
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

		dt = clinicDctService.getModify(fnIdx, param);
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
		JSONArray reqItemOrder = JSONObjectUtil.getJSONArray(itemInfo, "req_" + submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		
		// 3.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, itemOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 4. DB
    	int result = clinicDctService.update(uploadModulePath, fnIdx, keyIdx, request.getRemoteAddr(), parameterMap, settingInfo, items, reqItemOrder);
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

	*//**
	 * 등록처리
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 *//*
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
    	int result = clinicDctService.insert(uploadModulePath, fnIdx, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
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

	*//**
	 * 삭제처리
	 * @param deleteIdxs
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 *//*
	@Auth(role = Role.D)
	@ModuleAuth(name="MNG")
	@RequestMapping(method=RequestMethod.POST, value = "/deleteProc.do", params="select")
	public String delete(@ParamMap ParamForm parameterMap, @RequestParam(value="select") int[] deleteIdxs, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		JSONObject settingInfo = attrVO.getSettingInfo();
		String ajaxPName = attrVO.getAjaxPName();
		int fnIdx = attrVO.getFnIdx();
		
		// 1. DB
		int result = clinicDctService.delete(fnIdx, parameterMap, deleteIdxs, request.getRemoteAddr(), settingInfo);
    	
    	if(result > 0) {
    		// 1.1 저장 성공
			model.addAttribute("message",   MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.delete"), "top.location.reload();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	}
		// 1.2 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.delete")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}

	*//**
	 * 삭제처리 : 관리권한 없는 경우 - 자신이 등록한 글만 수정
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 *//*
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
		int result = clinicDctService.delete(fnIdx, parameterMap, deleteIdxs, request.getRemoteAddr(), settingInfo);
    	
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
	
	*//**
	 * 복원처리
	 * @param restoreIdxs
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 *//*
	@Auth(role = Role.U)
	@ModuleAuth(name="MNG")
	@RequestMapping(method=RequestMethod.POST, value = "/restoreProc.do", params="select")
	public String restore(@RequestParam(value="select") int[] restoreIdxs, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {

		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		JSONObject settingInfo = attrVO.getSettingInfo();
		int fnIdx = attrVO.getFnIdx();
		
		// 1. DB
		int result = clinicDctService.restore(fnIdx, restoreIdxs, request.getRemoteAddr(), settingInfo);
    	
    	if(result > 0) {
    		// 1.1 저장 성공
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.restore"), "fn_procReload();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	}
		// 1.2 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.restore")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	*//**
	 * 완전삭제처리
	 * @param deleteIdxs
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 *//*
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
		int result = clinicDctService.cdelete(uploadModulePath, fnIdx, deleteIdxs, settingInfo, items, itemOrder);
    	
    	if(result > 0) {
    		// 1.2.1 저장 성공
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.cdelete"), "location.reload();"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	}
		// 1.2.2 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.cdelete")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}

	*//**
	 * 삭제목록
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 *//*
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
    	totalCount = clinicDctService.getDeleteCount(fnIdx, param);
		paginationInfo.setTotalRecordCount(totalCount);
    	
    	if(totalCount > 0) {
    		param.put("firstIndex", paginationInfo.getFirstRecordIndex());
    		param.put("lastIndex", paginationInfo.getLastRecordIndex());
    		param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
    		
    		// 2.3 목록
    		list = clinicDctService.getDeleteList(fnIdx, param);
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
	} */

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
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
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
		Map<String, Object> fileInfo = clinicDctService.getFileUpload(uploadModulePath, fnIdx, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
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
		 *  		ㄴ (ex: 신청_임시저장   -> request_tempSave.do)
		 *  		ㄴ (ex: 신청_신청서상세보기 -> request_view_form.do)
		 *  
		 *  
		 * 2. 메뉴    : 서비스 안의 메뉴
		 *    행위   : 요청 행위(ex:버튼)의 이름(목적)   
		 *    
		 *  */
		
		// 추가경로
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String baseQueryString = StringUtil.getString(request.getAttribute("baseQueryString"));
		
		/* 신청관리 */		
		String listFormUrl 	= "request_list_form.do";							// 신청 목록 화면
		String viewFormUrl 	= "request_view_form.do";							// 신청 상세보기 화면		
		String modifyFormUrl = "request_modify_form.do";						// 신청 수정 화면	
		String modifyUrl = "request_modify.do";									// 신청 수정
		String acceptUrl = "request_accept.do";									// 신청 접수		
		String returnUrl = "request_return.do";									// 신청 반려		
		String firstApproveUrl = "request_firstApprove.do";						// 신청 1차승인		
		String returnToAcceptnUrl = "request_returnToAccept.do";				// 신청 반려(부장이 최종승인 안하고 반려시킴)		
		String approveUrl = "request_approve.do";								// 신청 최종승인	
		String accpetAllUrl = "request_accept_all.do";							// 신청서 일괄접수 
		String dropRequestUrl = "request_dropRequest.do";						// 클리닉기업 중도포기
		String appointDocAllUrl = "request_appointDocAll.do";					// 전담주치의 일괄지정
		String appointDocUrl = "request_appointDoc.do";							// 전담주치의 지정
		String excelUrl = "request_excel.do";									// 신청 목록 엑셀다운로드
		
		/* 계획관리 */
		String plan_listFormUrl = "plan_list_form.do";							// 계획 목록 화면
		String plan_viewFormUrl	= "plan_view_form.do";							// 계획 상세보기 화면		
		String plan_modifyFormUrl = "plan_modify_form.do";						// 계획 수정 화면	
		String plan_modifyUrl = "plan_modify.do";								// 계획 수정
		String plan_acceptUrl = "plan_accept.do";								// 계획 접수		
		String plan_acceptAllUrl = "plan_accept_all.do";						// 계획 일괄접수		
		String plan_returnUrl = "plan_return.do";								// 계획 반려		
		String plan_approveUrl = "plan_approve.do";								// 계획 최종승인	
		String plan_modifyApproveUrl = "plan_modify_approve.do";				// 계획 변경승인	
		String plan_modifyVieweUrl = "plan_modify_view_form.do";				// 계획 변경요청	
		String plan_modifyReturnUrl = "plan_modify_return.do";					// 계획 변경요청에 대한 반려
		String plan_excelUrl = "plan_excel.do";									// 계획 목록 엑셀다운로드
		String plan_dropUrl = "plan_dropRequest.do";							// 중도포기
		
		/* 활동일지 */
		String activity_listFormUrl = "activity_list_form.do";					// 활동일지 목록 화면
		String activity_viewFormUrl	= "activity_view_form.do";					// 활동일지 상세보기 화면		
		String activity_excelUrl	= "activity_excel.do";						// 활동일지 목록 엑셀다운로드	
		
		/* 성과관리 */
		String result_listFormUrl = "result_list_form.do";						// 성과 목록 화면
		String result_viewFormUrl	= "result_view_form.do";					// 성과 상세보기 화면		
		String result_modifyFormUrl = "result_modify_form.do";					// 성과 수정 화면	
		String result_modifyUrl = "result_modify.do";							// 성과 수정
		String result_acceptUrl = "result_accept.do";							// 성과 접수		
		String result_acceptAllUrl = "result_accept_all.do";					// 성과 일괄접수		
		String result_returnUrl = "result_return.do";							// 성과 반려		
		String result_approveUrl = "result_approve.do";							// 성과 최종승인	
		String result_excelUrl = "result_excel.do";								// 성과 목록 엑셀다운로드
		String result_dropUrl = "result_drop.do";								// 중도포기
		
		/* 비용관리 */
		String support_listFormUrl = "support_list_form.do";					// 비용 목록 화면
		String support_viewFormUrl	= "support_view_form.do";					// 비용 상세보기 화면		
		String support_modifyFormUrl = "support_modify_form.do";				// 비용 수정 화면	
		String support_modifyUrl = "support_modify.do";							// 비용 수정
		String support_acceptUrl = "support_accept.do";							// 비용 접수		
		String support_acceptAllUrl = "support_accept_all.do";					// 비용 일괄접수		
		String support_returnUrl = "support_return.do";							// 비용 반려		
		String support_firstApproveUrl = "support_firstApprove.do";				// 비용 1차승인	
		String support_returnToAcceptUrl = "support_returnToAccept.do";			// 비용 반려(부장이 최종승인 안하고 반려시킴)		
		String support_approveUrl = "support_approve.do";						// 비용 최종승인	
		String support_dropUrl = "support_drop.do";								// 중도포기
		String support_excelUrl = "support_excel.do";							// 비용 목록 엑셀다운로드
		
		/* 대시보드 */
		String dashboard_listFormUrl = "dashboard_list_form.do";				// 대시보드 목록 화면
		String dashboard_detailFormUrl = "dashboard_detail_form.do";
		
		/* 종합이력 */
		String total_listFormUrl = "total_list_form.do";						// 종합이력 목록 화면
		String total_detailFormUrl = "total_detail_form.do";						
		
		if(!StringUtil.isEmpty(baseQueryString)) {
			listFormUrl += baseQueryString;
			viewFormUrl += baseQueryString;
			modifyFormUrl += baseQueryString;
			modifyUrl += baseQueryString;
			acceptUrl += baseQueryString;
			returnUrl += baseQueryString;
			returnToAcceptnUrl += baseQueryString;
			firstApproveUrl += baseQueryString;
			approveUrl += baseQueryString;
			accpetAllUrl += baseQueryString;
			dropRequestUrl += baseQueryString;
			appointDocAllUrl += baseQueryString;
			appointDocUrl += baseQueryString;
			excelUrl += baseQueryString;
			
			plan_listFormUrl += baseQueryString;
			plan_viewFormUrl += baseQueryString;
			plan_modifyFormUrl += baseQueryString;
			plan_modifyUrl += baseQueryString;
			plan_acceptUrl += baseQueryString;
			plan_acceptAllUrl += baseQueryString;
			plan_returnUrl += baseQueryString;
			plan_approveUrl += baseQueryString;
			plan_modifyApproveUrl += baseQueryString;
			plan_modifyVieweUrl += baseQueryString;
			plan_modifyReturnUrl += baseQueryString;
			plan_excelUrl += baseQueryString;
			plan_dropUrl += baseQueryString;
			
			activity_listFormUrl += baseQueryString;
			activity_viewFormUrl += baseQueryString;
			activity_excelUrl += baseQueryString;
			
			result_listFormUrl += baseQueryString;
			result_viewFormUrl += baseQueryString;
			result_modifyFormUrl += baseQueryString;
			result_modifyUrl += baseQueryString;
			result_acceptUrl += baseQueryString;
			result_acceptAllUrl += baseQueryString;
			result_returnUrl += baseQueryString;
			result_approveUrl += baseQueryString;
			result_excelUrl += baseQueryString;
			result_dropUrl += baseQueryString;
			
			support_listFormUrl += baseQueryString;
			support_viewFormUrl += baseQueryString;
			support_modifyFormUrl += baseQueryString;
			support_modifyUrl += baseQueryString;
			support_acceptUrl += baseQueryString;
			support_acceptAllUrl += baseQueryString;
			support_returnUrl += baseQueryString;
			support_firstApproveUrl += baseQueryString;
			support_returnToAcceptUrl += baseQueryString;
			support_approveUrl += baseQueryString;
			support_dropUrl += baseQueryString;
			support_excelUrl += baseQueryString;
			
			dashboard_listFormUrl += baseQueryString;
			dashboard_detailFormUrl += baseQueryString;
			
			total_listFormUrl += baseQueryString;
			total_detailFormUrl += baseQueryString;
		}
		
		/*
		 * 1. common path 명명 패턴
		 * 		ㄴ 경로 이름_URL 
		 * 			ㄴ (ex: request_tempSave.do -> {REQUEST_TEMPSAVE_URL}
		 * */
		
		request.setAttribute("REQUEST_LIST_FORM_URL", listFormUrl);
		request.setAttribute("REQUEST_VIEW_FORM_URL", viewFormUrl);
		request.setAttribute("REQUEST_MODIFY_FORM_URL", modifyFormUrl);
		request.setAttribute("REQUEST_MODIFY_URL", modifyUrl);
		request.setAttribute("REQUEST_ACCEPT_URL", acceptUrl);
		request.setAttribute("REQUEST_RETURN_URL", returnUrl);
		request.setAttribute("REQUEST_FIRSTAPPROVE_URL", firstApproveUrl);
		request.setAttribute("REQUEST_RETURN_TO_ACCEPT_URL", returnToAcceptnUrl);
		request.setAttribute("REQUEST_APPROVE_URL", approveUrl);
		request.setAttribute("REQUEST_ACCEPT_ALL_URL", accpetAllUrl);
		request.setAttribute("REQUEST_DROPREQUEST_URL", dropRequestUrl);
		request.setAttribute("REQUEST_APPOINTDOCAll_URL", appointDocAllUrl);
		request.setAttribute("REQUEST_APPOINTDOC_URL", appointDocUrl);
		request.setAttribute("REQUEST_EXCEL_URL", excelUrl);
		
		request.setAttribute("PLAN_LIST_FORM_URL", plan_listFormUrl);
		request.setAttribute("PLAN_VIEW_FORM_URL", plan_viewFormUrl);
		request.setAttribute("PLAN_MODIFY_FORM_URL", plan_modifyFormUrl);
		request.setAttribute("PLAN_MODIFY_URL", plan_modifyUrl);
		request.setAttribute("PLAN_ACCEPT_URL", plan_acceptUrl);
		request.setAttribute("PLAN_ACCEPT_ALL_URL", plan_acceptAllUrl);
		request.setAttribute("PLAN_RETURN_URL", plan_returnUrl);
		request.setAttribute("PLAN_APPROVE_URL", plan_approveUrl);
		request.setAttribute("PLAN_MODIFY_APPROVE_URL", plan_modifyApproveUrl);
		request.setAttribute("PLAN_MODIFY_VIEW_FORM_URL", plan_modifyVieweUrl);
		request.setAttribute("PLAN_MODIFY_RETURN_URL", plan_modifyReturnUrl);
		request.setAttribute("PLAN_EXCEL_URL", plan_excelUrl);
		request.setAttribute("PLAN_DROP_URL", plan_dropUrl);
		
		request.setAttribute("ACTIVITY_LIST_FORM_URL", activity_listFormUrl);
		request.setAttribute("ACTIVITY_VIEW_FORM_URL", activity_viewFormUrl);
		request.setAttribute("ACTIVITY_EXCEL_URL", activity_excelUrl);
		
		request.setAttribute("RESULT_LIST_FORM_URL", result_listFormUrl);
		request.setAttribute("RESULT_VIEW_FORM_URL", result_viewFormUrl);
		request.setAttribute("RESULT_MODIFY_FORM_URL", result_modifyFormUrl);
		request.setAttribute("RESULT_MODIFY_URL", result_modifyUrl);
		request.setAttribute("RESULT_ACCEPT_URL", result_acceptUrl);
		request.setAttribute("RESULT_ACCEPT_ALL_URL", result_acceptAllUrl);
		request.setAttribute("RESULT_RETURN_URL", result_returnUrl);
		request.setAttribute("RESULT_APPROVE_URL", result_approveUrl);
		request.setAttribute("RESULT_EXCEL_URL", result_excelUrl);
		request.setAttribute("RESULT_DROP_URL", result_dropUrl);
		
		request.setAttribute("SUPPORT_LIST_FORM_URL", support_listFormUrl);
		request.setAttribute("SUPPORT_VIEW_FORM_URL", support_viewFormUrl);
		request.setAttribute("SUPPORT_MODIFY_FORM_URL", support_modifyFormUrl);
		request.setAttribute("SUPPORT_MODIFY_URL", support_modifyUrl);
		request.setAttribute("SUPPORT_ACCEPT_URL", support_acceptUrl);
		request.setAttribute("SUPPORT_ACCEPT_ALL_URL", support_acceptAllUrl);
		request.setAttribute("SUPPORT_RETURN_URL", support_returnUrl);
		request.setAttribute("SUPPORT_FIRSTAPPROVE_URL", support_firstApproveUrl);
		request.setAttribute("SUPPORT_RETURN_TO_ACCEPT_URL", support_returnToAcceptUrl);
		request.setAttribute("SUPPORT_APPROVE_URL", support_approveUrl);
		request.setAttribute("SUPPORT_DROP_URL", support_dropUrl);
		request.setAttribute("SUPPORT_EXCEL_URL", support_excelUrl);
		
		request.setAttribute("DASHBOARD_LIST_FORM_URL", dashboard_listFormUrl);
		request.setAttribute("DASHBOARD_DETAIL_FORM_URL", dashboard_detailFormUrl);
		
		request.setAttribute("TOTAL_LIST_FORM_URL", total_listFormUrl);
		request.setAttribute("TOTAL_DETAIL_FORM_URL", total_detailFormUrl);
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
			modiCnt = clinicDctService.getAuthCount(fnIdx, param);
			
			return (modiCnt > 0);
		}
		
		return true;
	}

}
