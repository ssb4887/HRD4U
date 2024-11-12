package rbs.modules.report.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
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
import rbs.modules.basket.dto.CMRespDto;
import rbs.modules.basket.service.BasketService;
import rbs.modules.clinic.service.ClinicService;
import rbs.modules.consulting.dto.Cnsl;
import rbs.modules.consulting.dto.CnslDiaryDTO;
import rbs.modules.consulting.dto.CnslTeamMemberDTO;
import rbs.modules.consulting.service.ConsultingService;
import rbs.modules.instt.service.InsttService;
import rbs.modules.plagiarism.PlagiarismCore;
import rbs.modules.recommend.RecommendDummyService;
import rbs.modules.report.dto.Report;
import rbs.modules.report.service.ReportService;

/**
 * 샘플모듈<br/>
 * : 통합관리시스템 > 메뉴콘텐츠관리, 통합관리시스템 > 기능등록관리, 사용자 사이트 에서 사용
 * @author user
 *
 */
@Controller
@ModuleMapping(moduleId="report")
@RequestMapping({"/{siteId}/report", "/{admSiteId}/menuContents/{usrSiteId}/report"})
public class ReportController extends ModuleController {
	
	@Value("${Globals.fileStorePath}")
	private String uploadDir;
	
	@Resource(name = "reportService")
	private ReportService reportService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
	@Resource(name = "recommendDummyService")
	private RecommendDummyService recommendService;
	
	@Resource(name = "basketService")
	private BasketService basketService;
	
	@Resource(name = "consultingService")
	private ConsultingService consultingService;
	
	@Resource(name = "jsonView")
	View jsonView;
	
	@Resource(name = "clinicService")
	private ClinicService clinicService;

	@Resource(name = "insttService")
	private InsttService insttService;
	
	@Resource(name = "plagiarism")
	private PlagiarismCore plagiarismCore;
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(ReportController.class);
	
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
	public String list(@ModuleAttr ModuleAttrVO attrVO, @ParamMap ParamForm parameterMap, @RequestParam Map<String, Object> param,
			HttpServletRequest request, ModelMap model, @PathVariable("siteId") String siteId) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		int fnIdx = attrVO.getFnIdx();
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		model.addAttribute("mId", parameterMap.get("mId")); // 목록
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		if(siteId.equals("dct")) {
			// 지부지사 리스트
			List<Object> insttList = insttService.getList(null);
			model.addAttribute("insttList", insttList);
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
		List<?> list = null;
		List<?> confmStatus = null;
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
		
		if(siteId.equals("dct")) {
			param.put("brffcCd", loginVO.getRegiCode());
			
			if(parameterMap.get("trCorpNm") != null) {
				param.put("trCorpNm", parameterMap.get("trCorpNm"));
			}
			
			if(parameterMap.get("schBplNo") != null) {
				param.put("schBplNo", parameterMap.get("schBplNo"));
			}
			
			if(parameterMap.get("insttIdx") != null) {
				param.put("insttIdx", parameterMap.get("insttIdx"));
			}
			param.put("dct", "dct");
		}
		
		int cnslIdx = parameterMap.getInt("cnslIdx");
		param.put("searchList", searchList);
		param.put("bplNo", loginVO.getBplNo());
		param.put("cnslIdx", cnslIdx);
		
		Cnsl cnsl = consultingService.selectByCnslIdx(cnslIdx);
    	model.addAttribute("cnsl", cnsl);	
		
		// 2.2 목록수
    	totalCount = reportService.getTotalCount(fnIdx, param);
		paginationInfo.setTotalRecordCount(totalCount);

    	if(totalCount > 0) {
    		if(usePaging == 1) {
	    		param.put("firstIndex", paginationInfo.getFirstRecordIndex());
	    		param.put("lastIndex", paginationInfo.getLastRecordIndex());
	    		param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
	    	}
    		
    		// 정렬컬럼 setting
    		param.put("dsetOrderField", JSONObjectUtil.getString(settingInfo, "dset_order_field"));
    		param.put("regiId", loginVO.getMemberId());
    		param.put("regiIdx", loginVO.getMemberIdx());
    		
    		// 2.3 목록
    		param.put("orderColumn", "REPRT_IDX");
    		list = reportService.getList("HRD_CNSL_REPORT_TP", param);
    		confmStatus = reportService.getList("HRD_CNSL_REPORT_CONFM", param);
		}
    	
    	// 3. 속성 setting
    	model.addAttribute("param", param);	
    	model.addAttribute("paginationInfo", paginationInfo);	
    	model.addAttribute("totalCount", totalCount);												// 페이징정보
    	model.addAttribute("list", list);		
    	model.addAttribute("confmStatus", confmStatus);	// 목록
    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("loginVO", loginVO); 
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
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
	public String view(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, @ParamMap ParamForm parameterMap,
			HttpServletResponse response, ModelMap model, @PathVariable("siteId") String siteId) throws Exception {
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 1. 필수 parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name")), 0);
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		int cnslIdx = parameterMap.getInt("cnslIdx");
		String idxColumnName = JSONObjectUtil.getString(settingInfo, "idx_column");
		String pageName = "";
		String submitType = "view";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		// 2. DB
		DataMap progress = null;
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<Object> tpSubList = null;
		List<DTForm> searchList = new ArrayList<DTForm>();
		Cnsl cnsl = consultingService.selectByCnslIdx(cnslIdx);
		
		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		param.put("searchList", searchList);
		param.put("cnslIdx", cnslIdx);
		// 상세정보
		progress = reportService.getView("HRD_CNSL_REPORT", param);
		dt = reportService.getView("HRD_CNSL_REPORT_TP", param);
		param.put("orderColumn", "REPRT_IDX");
		tpSubList = reportService.getList("HRD_CNSL_REPORT_TP_SUB", param);
		
		if(progress == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		if(cnsl.getCnslType().equals("6")) {
			param.put("orderColumn", "DIARY_IDX");
			List<?> diaryList = reportService.getList("HRD_CNSL_DIARY", param);
			
			Report report = reportService.selectReportData(parameterMap.getString("cnslIdx"));
			log.info("report {}", report);
			
			if(report != null) {
				String reprtCn = report.getReprtCn();
				
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, Object> map = objectMapper.readValue(reprtCn, Map.class);
				model.addAttribute("report", report);
				model.addAttribute("data", map);
				model.addAttribute("diaryList", diaryList);
				model.addAttribute("multiDataHashMap", reportService.getMultiHashMap(fnIdx, keyIdx, settingInfo, items, itemOrder));		// multi file 목록
				model.addAttribute("multiFileHashMap", reportService.getMultiFileHashMap(fnIdx, keyIdx, settingInfo, items, itemOrder));	// multi file 목록
			}
		}

		// 3 속성 setting
		model.addAttribute("dt", dt);
		model.addAttribute("progress", progress);	
		model.addAttribute("cnsl", cnsl);		// 상세정보
		model.addAttribute("tpSubList", tpSubList);
		model.addAttribute("loginVO", loginVO);
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);																// 페이지구분
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	if(cnsl.getCnslType().equals("1") || cnsl.getCnslType().equals("2")) {
    		pageName = "/basicView";
    	} else if(cnsl.getCnslType().equals("4")) {
    		pageName = "/indepthDgns";
    	} else if(cnsl.getCnslType().equals("6")) {
    		pageName = "/fieldView";
    	} else {
    		pageName = "/deepInput";
    	}
    	
		return getViewPath(pageName);
	}
	
	/**
	 * 등록 및 수정
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name="WRT")
	@RequestMapping(value = "/input.do")
	public String input(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, @RequestParam Map<String, Object> param,
			@ParamMap ParamForm parameterMap, ModelMap model, @PathVariable("siteId") String siteId) throws Exception {
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if(siteId.equals("web")) {
			if (loginVO != null) {
				param.put("bplNo", loginVO.getBplNo());
			}
		}
		
		// 기업정보 
		// 1. 필수 parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name")), 0);
		
		// 2. DB
		String idxColumnName = JSONObjectUtil.getString(settingInfo, "idx_column");		// key column명
		String submitType = "develop_form_column";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		String progress = "";
		int prtbizIdx = 0;
		int tpIdx = 0;
		int cnslIdx = parameterMap.getInt("cnslIdx");
		DataMap dt = null;
		DataMap tpDt = null;
		DataMap tpList = null;
		List<?> diaryList = null;
		List<Object> tpSubList = null;
		List<DTForm> searchList = new ArrayList<DTForm>();
		//리턴 페이지 이름
		String pageName = "";
		
		// 2.1 수정권한 체크
		Boolean isMngAuth = isMngProcAuth(settingInfo, fnIdx, keyIdx);
		if(!isMngAuth) {
			// 수정권한 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.auth")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		List<Object> insttList = null;
		Cnsl cnsl = consultingService.selectByCnslIdx(cnslIdx);
		insttList = clinicService.getInsttList(param);
		
		if(cnsl.getCnslType().equals("6")) {
			param.put("orderColumn", "DIARY_IDX");
			diaryList = reportService.getList("HRD_CNSL_DIARY", param);
		}
		
		// 2.2 상세정보
		param.put("cnslIdx", parameterMap.getInt("cnslIdx"));
		dt = reportService.getView("HRD_CNSL_REPORT", param);
		
		if(dt != null) {
			searchList.add(new DTForm("A." + idxColumnName, Integer.parseInt(dt.get("REPRT_IDX").toString())));
			param.put("searchList", searchList);
			
			if(cnsl.getCnslType().equals("6")) {
				Report report = reportService.selectReportData(parameterMap.getString("cnslIdx"));
				log.info("report {}", report);
				
				if(report != null) {
					String reprtCn = report.getReprtCn();
					ObjectMapper objectMapper = new ObjectMapper();
					Map<String, Object> map = objectMapper.readValue(reprtCn, Map.class);
					model.addAttribute("report", report);
					model.addAttribute("data", map);
				}
				
				if(report != null) {
					model.addAttribute("multiDataHashMap", reportService.getMultiHashMap(fnIdx, keyIdx, settingInfo, items, itemOrder));		// multi file 목록
					model.addAttribute("multiFileHashMap", reportService.getMultiFileHashMap(fnIdx, keyIdx, settingInfo, items, itemOrder));	// multi file 목록
					model.addAttribute("modify", "&mode=m");
				}
				
			} else {
				tpDt = reportService.getView("HRD_CNSL_REPORT_TP", param);
				if(tpDt != null) {
					tpIdx = Integer.parseInt(tpDt.get("TP_IDX").toString());
					System.out.println("cnsl " + cnsl);
					if(cnsl.getCnslType().equals("1")) {
						param.put("PRTBIZ_IDX", 1);
						param.put("TP_IDX", tpIdx);
					} else if(cnsl.getCnslType().equals("2")) {
						param.put("PRTBIZ_IDX", 4);
						param.put("TP_IDX", tpIdx);
					}
					tpList = reportService.getTpInfo(param);
					tpSubList = reportService.getTpSubInfo(param);
					model.addAttribute("tpList", tpList);			
					model.addAttribute("tpSubList", tpSubList);
					model.addAttribute("modify", "&mode=m");
				}
			}
			
			progress = (String) dt.get("CONFM_STATUS");
			model.addAttribute("progress", progress);
			model.addAttribute("dt", dt);
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, submitType);
		model.addAttribute("submitType", submitType);
		model.addAttribute("cnsl", cnsl);			
		model.addAttribute("insttList", insttList);		
		model.addAttribute("diaryList", diaryList);	
		model.addAttribute("optnHashMap", optnHashMap);	
		model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());
		
    	// 2. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	if(cnsl.getCnslType().equals("1") || cnsl.getCnslType().equals("2")) {
    		pageName = "/basicInput";
    	} else if(cnsl.getCnslType().equals("4")) {
    		pageName = "/indepthDgns";
    	} else if(cnsl.getCnslType().equals("6")) {
    		pageName = "/fieldInput";
    	} else if(cnsl.getCnslType().equals("5")) {
    		pageName = "/cnslType5Report/cnslType5ReportForm";
    	}else{
    		pageName = "/deepInput";
    	}
    	
		return getViewPath(pageName);
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
	public String inputProc(@RequestParam(value="mode") String mode, @ParamMap ParamForm parameterMap, @PathVariable("siteId") String siteId,
			@RequestParam MultiValueMap<String, String> formdata, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		int fnIdx = attrVO.getFnIdx();
		boolean isAdmMode = attrVO.isAdmMode();
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name")), 0);
		// 1.2 수정모드 여부 검사
		boolean isModify = (StringUtil.isEquals(mode, "m"))?true:false;
		if(isModify == true) {
			model.addAttribute("mode", "&mode=m");
		}
		
		if(keyIdx <= 0 || !isModify) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}

		// 2. DB
		// 2.1 상세정보 - 해당글이 없는 경우 return
		String idxColumnName = JSONObjectUtil.getString(settingInfo, "idx_column");		// key column명
		int cnslIdx = parameterMap.getInt("cnslIdx");
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		param.put("searchList", searchList);
		param.put("cnslIdx", cnslIdx);
		
		Cnsl cnsl = consultingService.selectByCnslIdx(cnslIdx);
		if(!cnsl.getCnslType().equals("6")) {
			dt = reportService.getView("HRD_CNSL_REPORT_TP", param);
			if(dt == null) {
				// 해당글이 없는 경우
				model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.contents")));
				return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
			} else {
				parameterMap.put("TP_IDX", Integer.parseInt(dt.get("TP_IDX").toString()));
			}
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
		JSONArray devOrder = JSONObjectUtil.getJSONArray(itemInfo, "develop_insert_column_order");
		JSONArray devTpOrder = JSONObjectUtil.getJSONArray(itemInfo, "develop_tp_insert_column_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		parameterMap.put("trTme", 1);
		String confmStatus = "";
		int result = 0;
		int devTp = 0;
		String toUrl = "";
		
		cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));
		String bplNo = StringUtil.getString(request.getParameter("bplNo"));
		if(siteId.equals("dct")) {
			toUrl = "/dct/consulting/viewAll.do?mId=126&cnslIdx="+cnslIdx+"&bplNo="+bplNo+"#tab4";
		} else if(siteId.equals("web")) {
			toUrl = "/hrddoctor/web/consulting/viewAll.do?mId=95&cnslIdx="+cnslIdx+"&bplNo="+bplNo+"#tab4";
		}
		
		if(parameterMap.getString("progress").equals("5")) {
			confmStatus = ConfirmProgress.TEMPSAVE.getCode();
		} else if(parameterMap.getString("progress").equals("10")){
			int chkDiaryCnt = reportService.chkDiaryCnt(cnslIdx);
			if(chkDiaryCnt == 0) {
				request.setAttribute("msg", "수행일지가 존재 하지 않습니다 작성 후 제출 해주십시오");
				request.setAttribute("url", toUrl);
				model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, "수행일지가 존재 하지 않습니다 작성 후 제출 해주십시오","fn_procAction(\"" + toUrl + "\", "+ StringUtil.getInt(request.getParameter("mdl"))+ ");")+ "#tab4");
				return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
			}
			confmStatus = ConfirmProgress.APPLY.getCode();
		} else if(parameterMap.getString("progress").equals("40")){
			confmStatus = ConfirmProgress.RETURN.getCode();
		} else if(parameterMap.getString("progress").equals("50")){
			confmStatus = ConfirmProgress.FIRSTAPPROVE.getCode();
		} else if(parameterMap.getString("progress").equals("55")){
			confmStatus = ConfirmProgress.APPROVE.getCode();
		}
		
		// 4. DB
		if(!cnsl.getCnslType().equals("6")) {
			if(parameterMap.getString("change") == null) {
				String trSfe = parameterMap.getString("trSfe");
				String trGoal = parameterMap.getString("trGoal");
				String xptEffect = parameterMap.getString("xptEffect");
				String trnReqm = parameterMap.getString("trnReqm");
				trSfe = trSfe.replaceAll("\r\n", "</p>");
				trSfe = trSfe.replaceAll("\f", "<p>");
				parameterMap.put("trSfe", trSfe);
				trGoal = trGoal.replaceAll("\r\n", "</p>");
				trGoal = trGoal.replaceAll("\f", "<p>");
				parameterMap.put("trGoal", trGoal);
				xptEffect = xptEffect.replaceAll("\r\n", "</p>");
				xptEffect = xptEffect.replaceAll("\f", "<p>");
				parameterMap.put("xptEffect", xptEffect);
				trnReqm = trnReqm.replaceAll("\r\n", "</p>");
				trnReqm = trnReqm.replaceAll("\f", "<p>");
				parameterMap.put("trnReqm", trnReqm);
				parameterMap.put("tpPicTelNo", parameterMap.getString("tpPicTelNo"));
			}
		} else {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = objectMapper.writeValueAsString(formdata);
			parameterMap.put("jsonValue", jsonString);
		}
		
		result = reportService.update(uploadModulePath, fnIdx, request.getRemoteAddr(), parameterMap, settingInfo, items, devOrder, confmStatus);
		if(result != 0) {
			if(parameterMap.getString("change") == null) {
				if(!cnsl.getCnslType().equals("6")) {
					devTp = reportService.updateDevReport(uploadModulePath, fnIdx, request.getRemoteAddr(), parameterMap, settingInfo, items, devTpOrder);
				}
			} 
		}
		
		if(confmStatus.equals("55") ) {
			insertExpert(attrVO, param, parameterMap, siteId, request);
		}
    	
    	if(result > 0) {
    		// 4.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	
    		String inputNpname = fn_dsetInputNpname(settingInfo);
			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
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
	public String inputProc(@ParamMap ParamForm parameterMap, @PathVariable("siteId") String siteId, @RequestParam MultiValueMap<String, String> formdata,
			@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		int fnIdx = attrVO.getFnIdx();
		
		// 1. 필수입력 체크
		Map<String, Object> param = new HashMap<String, Object>();
		
		// 1.1 항목설정
		String submitType = "develop_form_column";		// 항목 order명
		String inputFlag = "develop_form_column";			// 항목설정 중 write_type, modify_type 구분
		String tpIdx = parameterMap.getString("tpIdx");
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray devOrder = JSONObjectUtil.getJSONArray(itemInfo, "develop_insert_column_order");
		JSONArray devTpOrder = JSONObjectUtil.getJSONArray(itemInfo, "develop_tp_insert_column_order");
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		int cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));
		String bplNo = StringUtil.getString(request.getParameter("bplNo"));
		String toUrl = "/hrddoctor/web/consulting/viewAll.do?mId=95&cnslIdx="+cnslIdx+"&bplNo="+bplNo+"#tab4";
		String confmStatus = "";
		
		parameterMap.put("_itemInputTypeFlag", submitType);
		if(loginVO != null) {
			parameterMap.put("regiIdx", loginVO.getMemberIdx());
			parameterMap.put("regiId", loginVO.getMemberId());
			parameterMap.put("regiName", loginVO.getMemberName());
		}
		parameterMap.put("bplNo", bplNo);
		parameterMap.put("siteId", siteId);
		parameterMap.put("tpPicTelNo", parameterMap.getString("tpPicTelNo"));
		
		if(parameterMap.getString("progress").equals("5")) {
			confmStatus = ConfirmProgress.TEMPSAVE.getCode();
		} else if(parameterMap.getString("progress").equals("10")){
			int chkDiaryCnt = reportService.chkDiaryCnt(cnslIdx);
			if(chkDiaryCnt == 0) {
				request.setAttribute("msg", "수행일지가 존재 하지 않습니다 작성 후 제출 해주십시오");
				request.setAttribute("url", toUrl);
				model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, "수행일지가 존재 하지 않습니다 작성 후 제출 해주십시오","fn_procAction(\"" + toUrl + "\", "+ StringUtil.getInt(request.getParameter("mdl"))+ ");")+ "#tab4");
				return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
			}
			confmStatus = ConfirmProgress.APPLY.getCode();
		}
		
		int result = 0;
		int devTp = 0;
		
		// 2. 데이터(FormData) > JSON형식으로 변환
		
		Cnsl cnsl = consultingService.selectByCnslIdx(cnslIdx);
		if(cnsl.getCnslType().equals("6")) {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = objectMapper.writeValueAsString(formdata);
			parameterMap.put("jsonValue", jsonString);
			result = reportService.insert(uploadModulePath, fnIdx, request.getRemoteAddr(), parameterMap, settingInfo, items, devOrder, confmStatus);
		} else {
			if(tpIdx == null) {
				result = reportService.insert(uploadModulePath, fnIdx, request.getRemoteAddr(), parameterMap, settingInfo, items, devOrder, confmStatus);
				if(result != 0) {
					devTp = reportService.insertDevReport(uploadModulePath, fnIdx, request.getRemoteAddr(), parameterMap, settingInfo, items, devTpOrder);
				}
			} 
		}
		
		if(result > 0) {
			// 2.1 저장 성공
	    	// 기본경로
	    	fn_setCommonPath(attrVO);
	    	String inputNpname = fn_dsetInputNpname(settingInfo);
	    	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + devTp;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
		} else if(devTp == -1) {
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
		int result = reportService.delete(fnIdx, parameterMap, deleteIdxs, request.getRemoteAddr(), settingInfo);
    	
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
	@RequestMapping(method=RequestMethod.POST, value = "/deleteProc.do")
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
		
		/*// 2. 전체관리/완전삭제 권한 체크
		Boolean isMngAuth = isMngProcAuth(settingInfo, fnIdx, keyIdx);
		if(!isMngAuth) {
			// 자신이 등록한 글이 아닌 경우
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.auth")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}*/
		
		// 3. DB
		int[] deleteIdxs = {keyIdx};
		int result = reportService.delete(fnIdx, parameterMap, deleteIdxs, request.getRemoteAddr(), settingInfo);
    	
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
		int result = reportService.restore(fnIdx, restoreIdxs, request.getRemoteAddr(), settingInfo);
    	
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
		int result = reportService.cdelete(uploadModulePath, fnIdx, deleteIdxs, settingInfo, items, itemOrder);
    	
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
    	totalCount = reportService.getDeleteCount(fnIdx, param);
		paginationInfo.setTotalRecordCount(totalCount);
    	
    	if(totalCount > 0) {
    		param.put("firstIndex", paginationInfo.getFirstRecordIndex());
    		param.put("lastIndex", paginationInfo.getLastRecordIndex());
    		param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
    		
    		// 2.3 목록
    		list = reportService.getDeleteList(fnIdx, param);
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
		Map<String, Object> fileInfo = reportService.getFileUpload(uploadModulePath, fnIdx, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
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
			modiCnt = reportService.getAuthCount(fnIdx, param);
			
			return (modiCnt > 0);
		}
		
		return true;
	}
	
	
	/**
	 * 인력풀 회의 추가
	 */
	@Auth(role = Role.C)
	@RequestMapping(method = RequestMethod.POST, value = "/insertExpert.do")
	public ModelAndView insertExpert(@ModuleAttr ModuleAttrVO attrVO, @RequestParam Map<String, Object> paramMap,
			@ParamMap ParamForm parameterMap, @PathVariable("siteId") String siteId, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		int result = 0;
		Cnsl cnsl = consultingService.selectByCnslIdx(Integer.parseInt(paramMap.get("cnslIdx").toString()));
		String title = "";
		String cnslType = "";
		
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		int fnIdx = attrVO.getFnIdx();
		String submitType = "write";		// 항목 order명
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
			
		if(cnsl.getCnslType().equals("1")) {
			title = "사업주 컨설팅";
		} else if(cnsl.getCnslType().equals("2")) {
			title = "일반직무수행 OJT";
		} else if(cnsl.getCnslType().equals("3")) {
			title = "과제수행 OJT";
		} else if(cnsl.getCnslType().equals("4")) {
			title = "심층진단 컨설팅";
		} else if(cnsl.getCnslType().equals("5")) {
			title = "훈련체계수립 컨설팅";
		} else if(cnsl.getCnslType().equals("6")) {
			title = "현장활용 컨설팅";
		}
		
		if(cnsl.getCnslType().equals("1") || cnsl.getCnslType().equals("2") || cnsl.getCnslType().equals("3")) {
			cnslType = "성과";
		} else if(cnsl.getCnslType().equals("4") || cnsl.getCnslType().equals("5") || cnsl.getCnslType().equals("6")) {
			cnslType = "심화";
		}
		
		// 년도
		String YYYY = "";
		YYYY = ServletRequestUtils.getStringParameter(request,"yyyy", "");
		
		if(YYYY.equals("")){
			SimpleDateFormat toYYYY = new SimpleDateFormat("yyyy");
			Date toYear = new Date();
			String yyyy = toYYYY.format(toYear);
			YYYY = yyyy;
		}
		
		int expert = 0;
		int busin = 0;
		int entrst = 0;
		int evl = 0;
		String chrgrTel = "";
		
		paramMap.put("yyyy", YYYY);
		paramMap.put("title", title);
		paramMap.put("explCont", title);
		paramMap.put("delYn", "N");
		paramMap.put("indSel", "1");
		paramMap.put("indChker", "Y");
		paramMap.put("bizCode", "29");
		paramMap.put("dept", "22");
		paramMap.put("bizSecret", "N");
		paramMap.put("certHrpChk", "N");
		paramMap.put("insttIdx", cnsl.getCmptncBrffcIdx());
		DataMap getInstt = consultingService.getInstt(paramMap);
		paramMap.put("indNm", getInstt.get("BRFFC_CD"));
		paramMap.put("chrgrNm", cnsl.getCmptncBrffcNm());
		paramMap.put("chrgrTel", cnsl.getCmptncBrffcPicTelno());
		paramMap.put("fnIdx", 7);
		List<HashMap<String, Object>> dt = consultingService.getDiaryView(paramMap);
		
		int dtSize = dt.size();
		if (dtSize > 0) {
			
			if(cnslType == "성과") {
				expert = consultingService.insertExpert(paramMap);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
			for (int i = 0; i < dtSize; i++) {
				String exTime1 = sdf.format(dt.get(i).get("MTG_START_DT"));
				String exTime2 = sdf.format(dt.get(i).get("MTG_END_DT"));
				if(cnsl.getCmptncBrffcPicTelno() == null) {
					chrgrTel = getInstt.get("TELNO").toString().replaceAll("-", "");
				} else {
					chrgrTel = cnsl.getCmptncBrffcPicTelno().replaceAll("-", "");
				}
				paramMap.put("chrgrTel", chrgrTel);
				paramMap.put("firstId", loginVO.getMemberId());
				paramMap.put("receiptSdate", exTime1.substring(0, 8));
				paramMap.put("receiptEdate", exTime2.substring(0, 8));
				paramMap.put("exTime1", exTime1.substring(8, 12));
				paramMap.put("exTime2", exTime2.substring(8, 12));
				if(cnslType == "성과") {
					busin = consultingService.insertBusin(paramMap);
				}
				
				String hrpId = cnsl.getCnslTeam().getMembers().get(0).getMemberIdx();
				if(hrpId != null) {
					paramMap.put("hrpId", hrpId);
					paramMap.put("cnslIdx", cnsl.getCnslIdx());
					paramMap.put("diaryIdx", dt.get(i).get("DIARY_IDX"));
					paramMap.put("entrstRoleCd", "R100");
					paramMap.put("entrstRoleSttusCd", "01");
					paramMap.put("sortSn", i);
					paramMap.put("hrpRespone", "S");
					paramMap.put("hrpOjt", "Y");
					paramMap.put("hrpPooldiv", "DIV");
					paramMap.put("indSub", "DOC");
					paramMap.put("registIp", request.getRemoteAddr());
					paramMap.put("regiIdx", loginVO.getMemberIdx());
					paramMap.put("regiId", loginVO.getMemberId());
					paramMap.put("regiName", loginVO.getMemberName());
					evl = consultingService.insertEvl(paramMap);
					if(cnslType == "성과") {
						entrst = consultingService.insertEntrst(paramMap);
					}
				}
			}
		}
		
		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("인력풀 회의 추가").setBody(result).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);

		return mav;
	}
	
	/**
	 * 보고서 저장/제출
	 * @author sw.jang
	 * @param attrVO
	 * @param request
	 * @param formdata
	 * @return
	 * @throws Exception
	 */

	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/save.do")
	public ModelAndView saveReport(@ModuleAttr ModuleAttrVO attrVO, MultipartHttpServletRequest request, @RequestParam MultiValueMap<String, String> formdata) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		String regiIp = request.getRemoteAddr();

		MultiValueMap<String, MultipartFile> files =  request.getMultiFileMap();
		
		for(Map.Entry<String,List<String>> entry : formdata.entrySet() ) {
			String key = entry.getKey();
			if(key.contains("file")) {
				files.put(key, null);
			}
		}
		
		log.info("files {}", files);
		log.info("formdata {}", formdata);

		ObjectMapper objectMapper = new ObjectMapper();
		String result = "";
		try {
			Integer cnslIdx = Integer.parseInt(formdata.getFirst("cnslIdx"));
			String confmStatus = formdata.getFirst("confmStatus");
			String bplNo = formdata.getFirst("bplNo");
			Integer reprtIdx = null;
			if(formdata.getFirst("reprtIdx") != null && !formdata.getFirst("reprtIdx").equals("")) {
				reprtIdx = Integer.parseInt(formdata.getFirst("reprtIdx"));
				formdata.remove("reprtIdx");
			}
			
			formdata.remove("cnslIdx");
			formdata.remove("confmStatus");
			String reprtCn = objectMapper.writeValueAsString(formdata);
			
			Report report = new Report.Builder(cnslIdx).setReprtIdx(reprtIdx)
					.setConfmStatus(confmStatus).setReprtCn(reprtCn).setBplNo(bplNo)
					.setRegiId(loginMemberId).setRegiIdx(loginMemberIdx).setRegiName(loginMemberName).setRegiIp(regiIp)
					.setLastModiId(loginMemberId).setLastModiIdx(loginMemberIdx).setLastModiName(loginMemberName).setLastModiIp(regiIp).build();
			
			reportService.saveReportByDto(report, files, regiIp);
			
			// 저장 성공
			result = "success";
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("보고서 저장완료").setBody(result).build();
			mav.addObject("result", cmRespDto);
			return mav;
		
		} catch(Exception e) {
			e.printStackTrace();

			// 저장 실패
			result = "fail";
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("보고서 저장실패").setBody(result).build();
			mav.addObject("result", cmRespDto);
			return mav;
		}
	}
	
	/**
	 * 보고서 저장/제출
	 * @author sw.jang
	 * @param attrVO
	 * @param request
	 * @param formdata
	 * @return
	 * @throws Exception
	 */

	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/checkPBL.do")
	public ModelAndView checkPlagiarism(@ModuleAttr ModuleAttrVO attrVO, MultipartHttpServletRequest request, @RequestParam MultiValueMap<String, String> formdata) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		String regiIp = request.getRemoteAddr();
		
		MultiValueMap<String, MultipartFile> files =  request.getMultiFileMap();
		
		for(Map.Entry<String,List<String>> entry : formdata.entrySet() ) {
			String key = entry.getKey();
			if(key.contains("file")) {
				files.put(key, null);
			}
		}
		
		log.info("files {}", files);
		log.info("formdata {}", formdata);

		ObjectMapper objectMapper = new ObjectMapper();
		String result = "";
		try {
			Integer cnslIdx = Integer.parseInt(formdata.getFirst("cnslIdx"));
			String confmStatus = formdata.getFirst("confmStatus");
			String bplNo = formdata.getFirst("bplNo");
			Integer reprtIdx = null;
			if(formdata.getFirst("reprtIdx") != null && !formdata.getFirst("reprtIdx").equals("")) {
				reprtIdx = Integer.parseInt(formdata.getFirst("reprtIdx"));
				formdata.remove("reprtIdx");
			}
			
			formdata.remove("cnslIdx");
			formdata.remove("confmStatus");
			String profileTarget = formdata.getFirst("profileTarget");
			List<String> profileDetails = formdata.get("profileDetailContent");
			StringBuilder sb = new StringBuilder();
			sb.append(profileTarget).append(" ");
			for(String s : profileDetails) {
				sb.append(s).append(" ");
			}
			String text = sb.toString();
			List<Map> result_ = plagiarismCore.run(cnslIdx.toString(), text);
			convertPlagiarismData(formdata, result_.get(0));
			
			String reprtCn = objectMapper.writeValueAsString(formdata);
			Report report = new Report.Builder(cnslIdx).setReprtIdx(reprtIdx)
					.setConfmStatus(confmStatus).setReprtCn(reprtCn).setBplNo(bplNo)
					.setRegiId(loginMemberId).setRegiIdx(loginMemberIdx).setRegiName(loginMemberName).setRegiIp(regiIp)
					.setLastModiId(loginMemberId).setLastModiIdx(loginMemberIdx).setLastModiName(loginMemberName).setLastModiIp(regiIp).build();
			
			reportService.saveReportByDto(report, files, regiIp);
			
			// 저장 성공
			result = "success";
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg(reprtCn).setBody(result).build();
			mav.addObject("result", cmRespDto);
			return mav;
		
		} catch(Exception e) {
			e.printStackTrace();

			// 저장 실패
			result = "fail";
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("보고서 저장실패").setBody(result).build();
			mav.addObject("result", cmRespDto);
			return mav;
		}
	}
	
	private void convertPlagiarismData(MultiValueMap<String,String> formdata, Map plagiarism) {
		formdata.remove("plagiarismRate");
		formdata.add("plagiarismTotal", String.valueOf((Integer)plagiarism.get("total")));
		formdata.add("plagiarismRate", String.valueOf((float)plagiarism.get("rate")));
		formdata.add("plagiarismSimilar", String.valueOf((Integer)plagiarism.get("similar")));
		formdata.add("plagiarismSource", String.valueOf(plagiarism.get("source")));
		Set<String> totals_ = (Set<String>)plagiarism.get("totals");
		for(String s : totals_) {
			formdata.add("plagiarismTotals", s);
		}
		List<String> matched = (List<String>)plagiarism.get("matched");
		for(String s : matched) {
			formdata.add("plagiarismMatched", s);
		}
	}
	
	@Auth(role = Role.R)
	@RequestMapping(value = "/viewPBL.do")
	public String viewPlagiarism(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws JsonParseException, JsonMappingException, IOException {
		String pageName="/plagiarism";
		
		fn_setCommonPath(attrVO);
		return getViewPath(pageName);
	}
	
	
	
	/**
	 * 보고서 작성/수정 폼
	 * @author sw.jang
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/inputReport.do")
	public String inputReport(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws JsonParseException, JsonMappingException, IOException {
		String pageName="";
		
		if(request.getParameter("cnslIdx") != null && !request.getParameter("cnslIdx").isEmpty()) {
			String cnslIdx = request.getParameter("cnslIdx");
			Cnsl cnsl = consultingService.selectByCnslIdx(Integer.parseInt(cnslIdx));
			model.addAttribute("cnsl", cnsl);
				
			List<CnslDiaryDTO> diaryList = consultingService.selectDiaryListByCnslIdx(cnslIdx);
			model.addAttribute("diaryList", diaryList);
			
			Report report = reportService.selectReportData(cnslIdx);
			if(report != null) {
				String reprtCn = report.getReprtCn();
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, Object> map = objectMapper.readValue(reprtCn, Map.class);
				model.addAttribute("report", report);
				model.addAttribute("data", map);
				model.addAttribute("json", reprtCn);
				System.out.println(reprtCn);
			}

	    	if(cnsl.getCnslType().equals("1") || cnsl.getCnslType().equals("2")) {
	    		pageName = "/basicInput";
	    	} else if("3".equals(cnsl.getCnslType())) {
	    		Map bsc = reportService.getBSC(cnsl.getBscIdx().toString());
	    		List<Map> diary = reportService.getDiaryPBL(cnslIdx);
	    		model.addAttribute("bsc", bsc);
	    		model.addAttribute("diary", diary);
	    		Map plagiarism_ = plagiarismCore.getRecordFromIDX(cnslIdx);
	    		model.addAttribute("plagiarism", plagiarism_);
	    		pageName = "/pblInput";
	    	} else if(cnsl.getCnslType().equals("4")) {
	    		pageName = "/indepthInput";
	    	} else if(cnsl.getCnslType().equals("6")) {
	    		pageName = "/fieldInput";
	    	} else if(cnsl.getCnslType().equals("5")) {
	    		pageName = "/cnslType5Report/cnslType5ReportForm";
	    	}else{
	    		pageName = "/deepInput";
	    	}
		}
		
		fn_setCommonPath(attrVO);
		return getViewPath(pageName);
	}
	
	/**
	 * 보고서 View page
	 * @author sw.jang
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/viewReport.do")
	public String viewReport(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws JsonParseException, JsonMappingException, IOException {
		String pageName="";
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		if(request.getParameter("cnslIdx") != null && !request.getParameter("cnslIdx").isEmpty()) {
			String cnslIdx = request.getParameter("cnslIdx");
			Cnsl cnsl = consultingService.selectByCnslIdx(Integer.parseInt(cnslIdx));
			
			List<CnslDiaryDTO> diaryList = consultingService.selectDiaryListByCnslIdx(cnslIdx);
			model.addAttribute("diaryList", diaryList);
			
			List<CnslTeamMemberDTO> teamMembers = cnsl.getCnslTeam().getMembers();
			CnslTeamMemberDTO pm = new CnslTeamMemberDTO();
			List<CnslTeamMemberDTO> exExperts = new ArrayList<>();
			List<CnslTeamMemberDTO> inExperts = new ArrayList<>();
			

			for(CnslTeamMemberDTO dto : teamMembers) {
				switch(dto.getTeamIdx()) {
					case 1 : pm = dto;
						break;
					case 2 : exExperts.add(dto);
						break;
					case 3 : inExperts.add(dto);
						break;
				}
			}
			model.addAttribute("cnsl", cnsl);
			model.addAttribute("pm", pm);
			model.addAttribute("exExperts", exExperts);
			model.addAttribute("inExperts", inExperts);
			Report report = reportService.selectReportData(cnslIdx);
			log.info("report {}", report);
			
			if(report != null) {
				String reprtCn = report.getReprtCn();
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, Object> map = objectMapper.readValue(reprtCn, Map.class);
				model.addAttribute("report", report);
				model.addAttribute("data", map);
				model.addAttribute("json", reprtCn);
				model.addAttribute("loginVO", loginVO);
			}

	    	if(cnsl.getCnslType().equals("1") || cnsl.getCnslType().equals("2")) {
	    		pageName = "/basicInput";
	    	} else if("3".equals(cnsl.getCnslType())) {
	    		Map bsc = reportService.getBSC(cnsl.getBscIdx().toString());
	    		List<Map> diary = reportService.getDiaryPBL(cnslIdx);
	    		model.addAttribute("bsc", bsc);
	    		model.addAttribute("diary", diary);
	    		Map plagiarism_ = plagiarismCore.getRecordFromIDX(cnslIdx);
	    		model.addAttribute("plagiarism", plagiarism_);
	    		pageName = "/pblView";
	    	} else if(cnsl.getCnslType().equals("4")) {
	    		pageName = "/indepthView";
	    	} else if(cnsl.getCnslType().equals("6")) {
	    		pageName = "/fieldInput";
	    	} else if(cnsl.getCnslType().equals("5")) {
	    		pageName = "/cnslType5Report/cnslType5ReportView";
	    	}else{
	    		pageName = "/deepInput";
	    	}
		}
		fn_setCommonPath(attrVO);
		return getViewPath(pageName);
	}
	
	/**
	 * 컨설팅보고서 프린트
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/printReport.do")
	public String printReport(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws JsonParseException, JsonMappingException, IOException {
		String pageName="";
		
		if(request.getParameter("cnslIdx") != null && !request.getParameter("cnslIdx").isEmpty()) {
			String cnslIdx = request.getParameter("cnslIdx");
			Cnsl cnsl = consultingService.selectByCnslIdx(Integer.parseInt(cnslIdx));
			model.addAttribute("cnsl", cnsl);
			Report report = reportService.selectReportData(cnslIdx);
			log.info("report {}", report);
			
			if(report != null) {
				String reprtCn = report.getReprtCn();
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, Object> map = objectMapper.readValue(reprtCn, Map.class);
				model.addAttribute("report", report);
				model.addAttribute("data", map);		
			}

	    	if(cnsl.getCnslType().equals("1") || cnsl.getCnslType().equals("2")) {
	    		pageName = "/basicPrint";
	    	} else if(cnsl.getCnslType().equals("4")) {
	    		pageName = "/indepthPrint";
	    	} else if(cnsl.getCnslType().equals("6")) {
	    		pageName = "/fieldPrint";
	    	} else if(cnsl.getCnslType().equals("5")) {
	    		pageName = "/cnslType5Report/cnslType5ReportFormPrint";
	    	}else{
	    		pageName = "/deepPrint";
	    	}
		}
		
		fn_setCommonPath(attrVO);
		return getViewPath(pageName);
	}
	
	/**
	 * 첨부파일 다운로드
	 * @author sw.jang
	 * @param fileName
	 * @param cnslIdx
	 * @param response
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.GET, value = "/download.do")
	public void download(@RequestParam Map<String, Object> param, HttpServletResponse response) throws Exception {
		// 변경(24.01.19): 파일명을 파라미터로 받으면 서버내 다른 파일 다운로드 가능성 있으므로 > cnslIdx & reprtIdx & fleIdx로 파일 찾은 후 다운로드 되도록
		// 1. 필수 파라미터: cnslIdx, reprtIdx, fleIdx
		if(!param.containsKey("cnslIdx") || !param.containsKey("reprtIdx") || !param.containsKey("fleIdx") ||
			param.get("cnslIdx") == null || param.get("reprtIdx") == null || param.get("fleIdx") == null || 
			param.get("cnslIdx").toString().isEmpty() || param.get("reprtIdx").toString().isEmpty() || param.get("fleIdx").toString().isEmpty()) {
			
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('파일을 다운로드 받을 수 없습니다. 새로고침 후 다시 시도해주세요.');history.go(-1);</script>");
			w.flush();
			w.close();
			
		} else {
			Map<String, Object> searchParam = new HashMap<>();
			List<DTForm> searchList = new ArrayList<DTForm>();
			searchList.add(new DTForm("A.CNSL_IDX", param.get("cnslIdx").toString()));
			searchList.add(new DTForm("A.REPRT_IDX", param.get("reprtIdx").toString()));
			searchList.add(new DTForm("A.FLE_IDX", param.get("fleIdx").toString()));
			searchParam.put("searchList", searchList);

			// 2. 파일 결과(FILE_SAVED_NAME, FILE_ORIGIN_NAME)
			DataMap fileData = null;
			fileData = reportService.getMultiFileView(1, searchParam);
			
			if(fileData == null || fileData.isEmpty() || !fileData.containsKey("FILE_SAVED_NAME") || fileData.get("FILE_SAVED_NAME").toString().isEmpty()) {
				response.setContentType("text/html; charset=utf-8;");
				PrintWriter w = response.getWriter();
				w.write("<script>alert('파일을 찾을 수 없습니다. 새로고침 후 다시 시도해주세요.');history.go(-1);</script>");
				w.flush();
				w.close();
				
			} else {
				String cnslIdx = param.get("cnslIdx").toString();
				String fileSavedName = fileData.get("FILE_SAVED_NAME").toString();
				String fileOriginName = fileData.get("FILE_ORIGIN_NAME").toString();
				
				try {
					String filePath = uploadDir + "consulting/" + cnslIdx +"/"+ fileSavedName;
					File file = new File(filePath);
					
					FileInputStream fileInputStream = new FileInputStream(filePath);
					response.setContentType("application/download");
					response.setHeader("Content-Disposition", "attachment; filename="
								+ new String(fileOriginName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
					ByteStreams.copy(fileInputStream, response.getOutputStream());
					response.flushBuffer();
					
				} catch (Exception e) {
					e.printStackTrace();

					response.setContentType("text/html; charset=utf-8;");
					PrintWriter w = response.getWriter();
					w.write("<script>alert('파일 다운로드 중 에러가 발생했습니다. 새로고침 후 다시 시도해주세요.');history.go(-1);</script>");
					w.flush();
					w.close();
					throw new Exception("download error");
				}
			}
		}

	}
	
	/**
	 * 보고서 승인/반려
	 * @param attrVO
	 * @param request
	 * @param model
	 * @param cnsl
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/updateReportStatus.do")
	public ModelAndView updateReportStatus(HttpServletRequest request, @RequestBody String json,
			@ParamMap ParamForm parameterMap, @PathVariable("siteId") String siteId, @ModuleAttr ModuleAttrVO attrVO) throws Exception {
		ModelAndView mav = new ModelAndView();
		int result = 0;
		try {
			String regiIp = request.getRemoteAddr();
			ObjectMapper objectMapper = new ObjectMapper();
			HashMap<String,Object> param = objectMapper.readValue(json, HashMap.class);
			log.info("param {}", param);
			reportService.updateProgress(param, regiIp);

			if(param.get("confmStatus").equals(55)) {
				insertExpert(attrVO, param, parameterMap, siteId, request);
			}
			
		} catch (Exception e) {
			log.info("Error {}", e.getMessage());
		}


		
		
		CMRespDto cmRespDto = new CMRespDto.Builder(result).setMsg("보고서 상태변경").setBody(null).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;
	}

}











