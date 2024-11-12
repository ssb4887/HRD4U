package rbs.modules.consulting.web;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import com.sun.star.io.IOException;
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
import rbs.modules.agreement.service.AgreementService;
import rbs.modules.basket.dto.Basket;
import rbs.modules.basket.dto.CMRespDto;
import rbs.modules.basket.service.BasketService;
import rbs.modules.bsisCnsl.service.BsisCnslService;
import rbs.modules.consulting.dto.Cnsl;
import rbs.modules.consulting.dto.HrpUserDTO;
import rbs.modules.consulting.service.ConsultingService;
import rbs.modules.diagnosis.service.DiagnosisService;
import rbs.modules.instt.service.InsttService;
import rbs.modules.report.service.ReportService;

/**
 * 샘플모듈<br/>
 * : 통합관리시스템 > 메뉴콘텐츠관리, 통합관리시스템 > 기능등록관리, 사용자 사이트 에서 사용
 * 
 * @author user
 *
 */
@Controller
@ModuleMapping(moduleId = "consulting")
@RequestMapping({"/{siteId}/consulting",
		"/{admSiteId}/menuContents/{usrSiteId}/consulting"})
public class ConsultingController extends ModuleController {

	@Value("${Globals.fileStorePath}")
	private String uploadDir;

	@Resource(name = "consultingService")
	private ConsultingService consultingService;

	@Resource(name = "jsonView")
	View jsonView;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "basketService")
	private BasketService basketService;

	// 기초컨설팅
	@Resource(name = "bsisCnslService")
	private BsisCnslService bsisCnslService;

	// 기초진단
	@Resource(name = "diagnosisService")
	private DiagnosisService diagnosisService;

	@Resource(name = "agreementService")
	private AgreementService agreementService;

	@Resource(name = "reportService")
	private ReportService reportService;

	@Resource(name = "insttService")
	private InsttService insttService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;

	private static final org.slf4j.Logger log = LoggerFactory
			.getLogger(ConsultingController.class);

	/**
	 * 코드
	 * 
	 * @param submitType
	 * @param itemInfo
	 * @return
	 */
	public HashMap<String, Object> getOptionHashMap(String submitType,
			JSONObject itemInfo) {
		return getOptionHashMap(submitType, itemInfo, null);
	}

	/**
	 * 코드
	 * 
	 * @param submitType
	 * @param itemInfo
	 * @param addOptionHashMap
	 * @return
	 */
	public HashMap<String, Object> getOptionHashMap(String submitType,
			JSONObject itemInfo, HashMap<String, Object> addOptionHashMap) {
		// 코드
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo,
				submitType + "_order");
		HashMap<String, Object> optnHashMap = (addOptionHashMap != null)
				? CodeHelper.getItemOptnHashMap(items, itemOrder,
						addOptionHashMap)
				: CodeHelper.getItemOptnHashMap(items, itemOrder);
		return optnHashMap;
	}

	/**
	 * 컨텐츠 페이지
	 * 
	 * @author sw.jang
	 * @param attrVO
	 * @param parameterMap
	 * @param param
	 * @param request
	 * @param model
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "LST")
	@RequestMapping(value = "/main.do")
	public String getMain(@ModuleAttr ModuleAttrVO attrVO, ModelMap model)
			throws Exception {
		// 4. 기본경로

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();

		if (loginVO != null) {
			model.addAttribute("loginVO", loginVO);
		}

		fn_setCommonPath(attrVO);

		return getViewPath("/main");
	}

	/**
	 * 목록조회 (기업)
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "LST")
	@RequestMapping(value = "/list.do")
	public String list(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		int fnIdx = attrVO.getFnIdx();
		log.info("list fnIdx", fnIdx);

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String bplNo = loginVO.getBplNo();

		if (bplNo == null || bplNo.equals("")) {
			request.setAttribute("msg", "사업장관리번호가 존재하지 않거나 올바르지 않습니다.");
			request.setAttribute("url", "/web/main/main.do?mId=1");
			return getViewPath("/alert");
		} else {
			model.addAttribute("bplNo", bplNo);
		}

		if (loginVO.getUsertypeIdx() != 5) {
			request.setAttribute("msg", "컨설팅신청은 기업회원만 가능합니다.");
			request.setAttribute("url", "/web/main/main.do?mId=1");
			return getViewPath("/alert");
		}

		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		// 1. 페이지정보 setting
		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		int usePaging = JSONObjectUtil.getInt(settingInfo, "use_paging");
		if (usePaging == 1) {
			int listUnit = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_count", crtMenu, settingInfo,
					propertiesService.getInt("DEFAULT_LIST_UNIT")); // 페이지당 목록 수
			int listMaxUnit = listUnit
					* propertiesService.getInt("DEFAULT_LIST_MAX_CNT"); // 최대
																		// 페이지당
																		// 목록 수
			int listUnitStep = listUnit; // 페이지당 목록 수 증가값

			int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
			if (pageUnit == 0)
				pageUnit = listUnit;// JSONObjectUtil.getInt(settingInfo,
									// "dset_list_count",
									// propertiesService.getInt("PAGE_UNIT"));
									// // 페이지당 목록 수
			int pageSize = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_block", crtMenu, settingInfo,
					propertiesService.getInt("PAGE_SIZE"));

			String pageName = JSONObjectUtil.getString(settingInfo,
					"page_name");
			int page = StringUtil.getInt(request.getParameter(pageName), 1); // 현재
																				// 페이지
																				// index

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

		// BPL_NO 삽입
		param.put("bplNo", bplNo);
		param.put("startDate", startDate);
		param.put("endDate", endDate);

		// 2.1 검색조건

		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");

		// 항목설정으로 검색조건 setting
		String listSearchId = "list_search"; // 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo,
				listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil
				.getItemInfoSearchList(listSearch, queryString);
		if (itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}

		// 탭코드
		String cateTabItemId = JSONObjectUtil.getString(settingInfo,
				"dset_cate_tab_id");
		String cateTabColumnId = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"column_id");
		String cateTabMasterCode = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"master_code");
		int cateTabObjectType = JSONObjectUtil.getInt(settingInfo,
				"dset_cate_tab_object_type");
		// 검색 코드
		HashMap<String, Object> searchOptnHashMap = CodeHelper
				.getItemOptnSearchHashMap(listSearch, cateTabItemId,
						cateTabColumnId, cateTabObjectType, cateTabMasterCode);
		// 목록 코드
		HashMap<String, Object> optnHashMap = getOptionHashMap(submitType,
				itemInfo, searchOptnHashMap);// CodeHelper.getItemOptnHashMap(items,
												// itemOrder,
												// searchOptnHashMap);
		// 탭 검색 조건
		List<DTForm> cateTabSearchList = ModuleUtil.getCateTabSearchList(
				settingInfo, items, queryString, optnHashMap);// fn_getCateTabSearchList(queryString,
																// settingInfo,
																// items,
																// optnHashMap);
		if (cateTabSearchList != null) {
			searchList.addAll(cateTabSearchList);
		}

		param.put("searchList", searchList);

		// 2.2 목록수
		totalCount = consultingService.getTotalCount(fnIdx, param);
		paginationInfo.setTotalRecordCount(totalCount);

		if (totalCount > 0) {
			if (usePaging == 1) {
				param.put("firstIndex", paginationInfo.getFirstRecordIndex());
				param.put("lastIndex", paginationInfo.getLastRecordIndex());
				param.put("recordCountPerPage",
						paginationInfo.getRecordCountPerPage());
			}

			// 정렬컬럼 setting
			param.put("dsetOrderField",
					JSONObjectUtil.getString(settingInfo, "dset_order_field"));

			// 2.3 목록
			list = consultingService.getList(fnIdx, param);
		}

		// 3. 속성 setting
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("totalCount", totalCount); // 페이징정보
		model.addAttribute("list", list); // 목록
		model.addAttribute("searchOptnHashMap", searchOptnHashMap); // 항목코드
		model.addAttribute("optnHashMap", optnHashMap);

		return getViewPath("/list");
	}

	/**
	 * 신청내역조회(관리자)
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "LST")
	@RequestMapping(value = "/applyList.do")
	public String applyList(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		int fnIdx = attrVO.getFnIdx();

		log.info("applyList fnIdx", fnIdx);

		String siteId = attrVO.getSiteId();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();

		String memberIdx = loginVO.getMemberIdx();

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();

		List<HashMap<String, String>> insttList = consultingService
				.getInsttList();

		// 1. 페이지정보 setting
		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		int usePaging = JSONObjectUtil.getInt(settingInfo, "use_paging");
		if (usePaging == 1) {
			int listUnit = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_count", crtMenu, settingInfo,
					propertiesService.getInt("DEFAULT_LIST_UNIT")); // 페이지당 목록 수
			int listMaxUnit = listUnit
					* propertiesService.getInt("DEFAULT_LIST_MAX_CNT"); // 최대
																		// 페이지당
																		// 목록 수
			int listUnitStep = listUnit; // 페이지당 목록 수 증가값

			int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
			if (pageUnit == 0)
				pageUnit = listUnit;// JSONObjectUtil.getInt(settingInfo,
									// "dset_list_count",
									// propertiesService.getInt("PAGE_UNIT"));
									// // 페이지당 목록 수
			int pageSize = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_block", crtMenu, settingInfo,
					propertiesService.getInt("PAGE_SIZE"));

			String pageName = JSONObjectUtil.getString(settingInfo,
					"page_name");
			int page = StringUtil.getInt(request.getParameter(pageName), 1); // 현재
																				// 페이지
																				// index

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
		String listSearchId = "list_search"; // 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo,
				listSearchId);

		List<DTForm> itemInfoSearchList = ModuleUtil
				.getItemInfoSearchList(listSearch, queryString);

		if (itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}

		// 탭코드
		String cateTabItemId = JSONObjectUtil.getString(settingInfo,
				"dset_cate_tab_id");
		String cateTabColumnId = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"column_id");
		String cateTabMasterCode = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"master_code");
		int cateTabObjectType = JSONObjectUtil.getInt(settingInfo,
				"dset_cate_tab_object_type");
		// 검색 코드
		HashMap<String, Object> searchOptnHashMap = CodeHelper
				.getItemOptnSearchHashMap(listSearch, cateTabItemId,
						cateTabColumnId, cateTabObjectType, cateTabMasterCode);
		// 목록 코드
		HashMap<String, Object> optnHashMap = getOptionHashMap(submitType,
				itemInfo, searchOptnHashMap);// CodeHelper.getItemOptnHashMap(items,
												// itemOrder,
												// searchOptnHashMap);
		// 탭 검색 조건
		List<DTForm> cateTabSearchList = ModuleUtil.getCateTabSearchList(
				settingInfo, items, queryString, optnHashMap);// fn_getCateTabSearchList(queryString,
																// settingInfo,
																// items,
																// optnHashMap);
		if (cateTabSearchList != null) {
			searchList.addAll(cateTabSearchList);
		}

		param.put("searchList", searchList);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		param.put("memberIdx", memberIdx);
		param.put("siteId", siteId);

		// 2.2 목록수
		totalCount = consultingService.getTotalCount(fnIdx, param);
		paginationInfo.setTotalRecordCount(totalCount);

		if (totalCount > 0) {
			if (usePaging == 1) {
				param.put("firstIndex", paginationInfo.getFirstRecordIndex());
				param.put("lastIndex", paginationInfo.getLastRecordIndex());
				param.put("recordCountPerPage",
						paginationInfo.getRecordCountPerPage());
			}

			// 정렬컬럼 setting
			param.put("dsetOrderField",
					JSONObjectUtil.getString(settingInfo, "dset_order_field"));

			// 2.3 목록
			list = consultingService.getList(fnIdx, param);

		}

		// 3. 속성 setting
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("totalCount", totalCount); // 페이징정보
		model.addAttribute("list", list); // 목록
		model.addAttribute("searchOptnHashMap", searchOptnHashMap); // 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("insttList", insttList);

		// 4. 기본경로

		return getViewPath("/applyList");
	}

	/**
	 * 컨설팅 관리
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "LST")
	@RequestMapping(value = "/cnslListAll.do")
	public String cnslListAll(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();

		boolean isAdmMode = attrVO.isAdmMode();
		int fnIdx = attrVO.getFnIdx();

		log.info("fnIdx {}", fnIdx);

		String siteId = attrVO.getSiteId();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if (loginVO == null) {
			request.setAttribute("msg", "로그인이 필요 합니다.");
			request.setAttribute("url", "/" + siteId + "/login/login.do?mId=3");
			return getViewPath("/alert");
		}

		String memberIdx = loginVO.getMemberIdx();
		int userTypeIdx = loginVO.getUsertypeIdx();
		String bplNo = loginVO.getBplNo();
		String searchMemberIdx = request.getParameter("memberIdx");
		log.info("searchMemberIdx", searchMemberIdx);
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();

		// 1. 페이지정보 setting
		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		int usePaging = JSONObjectUtil.getInt(settingInfo, "use_paging");
		if (usePaging == 1) {
			int listUnit = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_count", crtMenu, settingInfo,
					propertiesService.getInt("DEFAULT_LIST_UNIT")); // 페이지당 목록 수
			int listMaxUnit = listUnit
					* propertiesService.getInt("DEFAULT_LIST_MAX_CNT"); // 최대
																		// 페이지당
																		// 목록 수
			int listUnitStep = listUnit; // 페이지당 목록 수 증가값

			int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
			if (pageUnit == 0)
				pageUnit = listUnit;// JSONObjectUtil.getInt(settingInfo,
									// "dset_list_count",
									// propertiesService.getInt("PAGE_UNIT"));
									// // 페이지당 목록 수
			int pageSize = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_block", crtMenu, settingInfo,
					propertiesService.getInt("PAGE_SIZE"));

			String pageName = JSONObjectUtil.getString(settingInfo,
					"page_name");
			int page = StringUtil.getInt(request.getParameter(pageName), 1); // 현재
																				// 페이지
																				// index

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

		List<HashMap<String, String>> insttList = consultingService
				.getInsttList();

		// 2.1 검색조건

		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");

		// 항목설정으로 검색조건 setting
		String listSearchId = "list_search"; // 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo,
				listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil
				.getItemInfoSearchList(listSearch, queryString);
		if (itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}

		// 탭코드
		String cateTabItemId = JSONObjectUtil.getString(settingInfo,
				"dset_cate_tab_id");
		String cateTabColumnId = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"column_id");
		String cateTabMasterCode = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"master_code");
		int cateTabObjectType = JSONObjectUtil.getInt(settingInfo,
				"dset_cate_tab_object_type");
		// 검색 코드
		HashMap<String, Object> searchOptnHashMap = CodeHelper
				.getItemOptnSearchHashMap(listSearch, cateTabItemId,
						cateTabColumnId, cateTabObjectType, cateTabMasterCode);
		// 목록 코드
		HashMap<String, Object> optnHashMap = getOptionHashMap(submitType,
				itemInfo, searchOptnHashMap);// CodeHelper.getItemOptnHashMap(items,
												// itemOrder,
												// searchOptnHashMap);
		// 탭 검색 조건
		List<DTForm> cateTabSearchList = ModuleUtil.getCateTabSearchList(
				settingInfo, items, queryString, optnHashMap);// fn_getCateTabSearchList(queryString,
																// settingInfo,
																// items,
																// optnHashMap);
		if (cateTabSearchList != null) {
			searchList.addAll(cateTabSearchList);
		}

		if (request.getParameter("is_bplNo") != null
				&& request.getParameter("is_bplNo") != "") {
			String searchBplNo = request.getParameter("is_bplNo");

			param.put("searchBplNo", searchBplNo);
		}

		if (request.getParameter("is_confmStatus") != null
				&& request.getParameter("is_confmStatus") != "") {
			String searchConfmStatus = request.getParameter("is_confmStatus");
			
			param.put("searchConfmStatus", searchConfmStatus);
			
			log.info("searchConfmStatus", searchConfmStatus);
		}


		
		param.put("searchList", searchList);
		param.put("siteId", siteId);
		param.put("memberIdx", memberIdx);
		param.put("userTypeIdx", userTypeIdx);
		if (bplNo != null) {
			param.put("bplNo", bplNo);
		}

		if (searchMemberIdx != null) {
			param.put("searchMemberIdx", searchMemberIdx);
		}

		log.info("param {}", param);

		// 2.2 목록수
		totalCount = consultingService.getCnslListAllCount(fnIdx, param);
		paginationInfo.setTotalRecordCount(totalCount);

		if (totalCount > 0) {
			if (usePaging == 1) {
				param.put("firstIndex", paginationInfo.getFirstRecordIndex());
				param.put("lastIndex", paginationInfo.getLastRecordIndex());
				param.put("recordCountPerPage",
						paginationInfo.getRecordCountPerPage());
			}

			// 정렬컬럼 setting
			param.put("dsetOrderField",
					JSONObjectUtil.getString(settingInfo, "dset_order_field"));

			// 2.3 목록
			list = consultingService.getCnslListAll(fnIdx, param);
		}

		// 3. 속성 setting
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("totalCount", totalCount); // 페이징정보
		model.addAttribute("list", list); // 목록
		log.info("list {}", list);
		model.addAttribute("searchOptnHashMap", searchOptnHashMap); // 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("insttList", insttList);
		model.addAttribute("userTypeIdx", userTypeIdx);

		// 4. 기본경로

		return getViewPath("/cnslListAll");
	}

	/**
	 * 변경신청 리스트
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "LST")
	@RequestMapping(value = "/changeList.do")
	public String changeList(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		int fnIdx = attrVO.getFnIdx();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();

		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();

		// 1. 페이지정보 setting
		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		int usePaging = JSONObjectUtil.getInt(settingInfo, "use_paging");
		if (usePaging == 1) {
			int listUnit = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_count", crtMenu, settingInfo,
					propertiesService.getInt("DEFAULT_LIST_UNIT")); // 페이지당 목록 수
			int listMaxUnit = listUnit
					* propertiesService.getInt("DEFAULT_LIST_MAX_CNT"); // 최대
																		// 페이지당
																		// 목록 수
			int listUnitStep = listUnit; // 페이지당 목록 수 증가값

			int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
			if (pageUnit == 0)
				pageUnit = listUnit;// JSONObjectUtil.getInt(settingInfo,
									// "dset_list_count",
									// propertiesService.getInt("PAGE_UNIT"));
									// // 페이지당 목록 수
			int pageSize = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_block", crtMenu, settingInfo,
					propertiesService.getInt("PAGE_SIZE"));

			String pageName = JSONObjectUtil.getString(settingInfo,
					"page_name");
			int page = StringUtil.getInt(request.getParameter(pageName), 1); // 현재
																				// 페이지
																				// index

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
		String listSearchId = "list_search"; // 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo,
				listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil
				.getItemInfoSearchList(listSearch, queryString);
		if (itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}

		// 탭코드
		String cateTabItemId = JSONObjectUtil.getString(settingInfo,
				"dset_cate_tab_id");
		String cateTabColumnId = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"column_id");
		String cateTabMasterCode = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"master_code");
		int cateTabObjectType = JSONObjectUtil.getInt(settingInfo,
				"dset_cate_tab_object_type");
		// 검색 코드
		HashMap<String, Object> searchOptnHashMap = CodeHelper
				.getItemOptnSearchHashMap(listSearch, cateTabItemId,
						cateTabColumnId, cateTabObjectType, cateTabMasterCode);
		// 목록 코드
		HashMap<String, Object> optnHashMap = getOptionHashMap(submitType,
				itemInfo, searchOptnHashMap);// CodeHelper.getItemOptnHashMap(items,
												// itemOrder,
												// searchOptnHashMap);
		// 탭 검색 조건
		List<DTForm> cateTabSearchList = ModuleUtil.getCateTabSearchList(
				settingInfo, items, queryString, optnHashMap);// fn_getCateTabSearchList(queryString,
																// settingInfo,
																// items,
																// optnHashMap);
		if (cateTabSearchList != null) {
			searchList.addAll(cateTabSearchList);
		}

		param.put("searchList", searchList);

		// 2.2 목록수
		totalCount = consultingService.getTotalChangeCnslCount(fnIdx, param);
		paginationInfo.setTotalRecordCount(totalCount);

		if (totalCount > 0) {
			if (usePaging == 1) {
				param.put("firstIndex", paginationInfo.getFirstRecordIndex());
				param.put("lastIndex", paginationInfo.getLastRecordIndex());
				param.put("recordCountPerPage",
						paginationInfo.getRecordCountPerPage());
			}

			// 정렬컬럼 setting
			param.put("dsetOrderField",
					JSONObjectUtil.getString(settingInfo, "dset_order_field"));

			// 2.3 목록
			list = consultingService.getChangeList(fnIdx, param);
		}

		// 3. 속성 setting
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("totalCount", totalCount); // 페이징정보
		model.addAttribute("list", list); // 목록
		model.addAttribute("searchOptnHashMap", searchOptnHashMap); // 항목코드
		model.addAttribute("optnHashMap", optnHashMap);

		// 4. 기본경로

		return getViewPath("/changeList");
	}

	/**
	 * 심화컨설팅 신청서 상세조회 - 관리자
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "VEW")
	@RequestMapping(value = "/cnslAdminView.do")
	public String cnslAdminViewTypeB(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {

		
		String siteId = attrVO.getSiteId();
		
		model.addAttribute("siteId", siteId);
		
		String cnslIdx = request.getParameter("cnslIdx") == null
				? ""
				: request.getParameter("cnslIdx");

		// 컨설팅Idx
		if (!cnslIdx.isEmpty()) {
			Cnsl cnsl = consultingService
					.selectByCnslIdx(Integer.parseInt(cnslIdx));
			model.addAttribute("cnsl", cnsl);

			String bplNo = cnsl.getBplNo();
			String cnslType = cnsl.getCnslType();

			if (cnslType.equals("1") || cnslType.equals("2")
					|| cnslType.equals("3")) {
				// 1-3, SOJT 협약 여부
				

				// 1-4, SOJT일반, SOJT특화 갯수 여부
				int sojtNomalCount = consultingService
						.selectCnslCountByBplNoAndCnslType(bplNo, "2");
				model.addAttribute("sojtNomalCount", sojtNomalCount);
				int sojtPblCount = consultingService
						.selectCnslCountByBplNoAndCnslType(bplNo, "3");
				model.addAttribute("sojtPblCount", sojtPblCount);
				return getViewPath("/cnslAdminView");

			} else if (cnslType.equals("4")) {
				return getViewPath("/cnslAdminView");
			} else {
				request.setAttribute("msg", "존재하지 않는 컨설팅 타입입니다.");
				request.setAttribute("url", "/web/consulting/list.do?mId=64");
				return getViewPath("/alert");
			}

		} else {
			request.setAttribute("msg", "컨설팅이 존재하지 않습니다.");
			request.setAttribute("url", "/web/consulting/list.do?mId=64");
			return getViewPath("/alert");

		}
	}

	/**
	 * 컨설팅 관리 디테일페이지
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "VEW")
	@RequestMapping(value = "/viewAll.do")
	public String viewAll(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, HttpServletResponse response,
			@ParamMap ParamForm parameterMap,
			@RequestParam Map<String, Object> map,
			@PathVariable("siteId") String siteId, ModelMap model)
			throws Exception {

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if (loginVO != null) {
			model.addAttribute("loginVO", loginVO);
			int usertypeIdx = loginVO.getUsertypeIdx();
			String loginBplNo = loginVO.getBplNo();
			String memberIdx = loginVO.getMemberIdx();

			List<Cnsl> cnslList = new ArrayList<>();

			// 기업회원
			if (usertypeIdx == 5) {
				cnslList = consultingService
						.selectCnslListByBplNoForViewAll(loginBplNo);
				// 컨설턴트
			} else if (usertypeIdx == 10) {
				cnslList = consultingService
						.selectCnslListByMemberIdxForViewAll(memberIdx);
				// 민간센터
			} else if (usertypeIdx == 20) {
				cnslList = consultingService
						.selectCnslListBySpntMemberIdxForViewAll(memberIdx);
				// 소속기관
			} else if (usertypeIdx == 30) {
				cnslList = consultingService
						.selectCnslListByDoctorMemberIdxForViewAll(memberIdx);
				// 본부 및 최고관리자
			} else if (usertypeIdx == 40 || usertypeIdx == 55) {
				cnslList = consultingService.selectCnslListAllForViewAll();
			} else {
				request.setAttribute("msg", "정상적인 접근이 아닙니다.");
				request.setAttribute("url", siteId + "/main/main.do?mId=1");
				return getViewPath("/alert");

			}

			log.info("cnslList {}", cnslList);

			int cnslIdx = parameterMap.getInt("cnslIdx");

			log.info("cnslIdx {}", cnslIdx);

			int result = 0;
			for (Cnsl cnsl : cnslList) {
				Integer permitCnslIdx = cnsl.getCnslIdx();
				if (permitCnslIdx == cnslIdx) {
					result = +1;
				}
			}

			log.info("result {}", result);

			if (result > 0) {
				// bplNo로 기업정보 조회
				String bplNo = request.getParameter("bplNo");
				Basket basket = basketService.getBasketSelect(bplNo);
				model.addAttribute("basket", basket);

				// cnslIdx로 컨설팅 조회
				Cnsl cnsl = consultingService.selectByCnslIdx(cnslIdx);
				model.addAttribute("cnsl", cnsl);

				// cnslIdx로 변경신청 이력 있는지 확인
				Cnsl changeCnsl = consultingService.selectChangeCnslByCnslIdx(cnslIdx);
				model.addAttribute("changeCnsl", changeCnsl);
				model.addAttribute("loginVO", loginVO);
				model.addAttribute("map", map);
				diaryList(attrVO, parameterMap, map, request, model, siteId);
				reportList(attrVO, parameterMap, map, request, model, siteId);
			} else {
				log.info("접근제한2");
				request.setAttribute("msg", "정상적인 접근이 아닙니다.");
				request.setAttribute("url",
						"/" + attrVO.getSiteId() + "/main/main.do?mId=1");
				return getViewPath("/alert");
			}

		} else {
			request.setAttribute("msg", "로그인이 필요합니다.");
			request.setAttribute("url",
					"/" + attrVO.getSiteId() + "/login/login.do?mId=3");
			return getViewPath("/alert");
		}

		return getViewPath("/viewAll");
	}

	/**
	 * 컨설팅 신청서
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */

	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(value = "/cnslApplyForm.do")
	public String cnslApplyForm(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, ModelMap model) throws Exception {

		String type = request.getParameter("type") == null
				? ""
				: request.getParameter("type");

		// bplNo로 해당기업 정보 조회
		String bplNo = request.getParameter("bplNo");
		Basket basket = basketService.getBasketSelect(bplNo);
		// 기업 대표명 조회
		String reperNm = consultingService
				.select4URepveNmByBizrNo(basket.getBizrNo());
		model.addAttribute("reperNm", reperNm);
		model.addAttribute("basket", basket);

		// TODO NULL CHECK
		// TODO NULL CHECK
		// TODO NULL CHECK
		// TODO NULL CHECK
		// TODO NULL CHECK
		// TODO NULL CHECK
		// TODO NULL CHECK
		// TODO NULL CHECK
		// TODO NULL CHECK

		// 1-1. 기초컨설팅 여부
		HashMap<String, Object> completedBsisCnslOne = bsisCnslService
				.getCompletedBsisCnslOne(bplNo);
		if (completedBsisCnslOne != null) {
			int completedBsisCnslCount = Integer.parseInt(
					String.valueOf(completedBsisCnslOne.get("COUNT")));

			if (completedBsisCnslCount > 0) {
				int bsiscnslIdx = Integer.parseInt(String
						.valueOf(completedBsisCnslOne.get("BSISCNSL_IDX")));
				model.addAttribute("bsiscnslIdx", bsiscnslIdx);
				model.addAttribute("bsisCnsl", completedBsisCnslOne);
			} else {
				request.setAttribute("msg", "기업HRD이음컨설팅을 실시하지 않았습니다. 기업HRD이음컨설팅을 먼저 진행해주세요.");
				request.setAttribute("url", "/web/bsisCnsl/init.do?mId=55");
				return getViewPath("/alert");
			}
		} else {
			request.setAttribute("msg", "기업HRD이음컨설팅을 실시하지 않았습니다. 기업HRD이음컨설팅을 먼저 진행해주세요.");
			request.setAttribute("url", "/web/bsisCnsl/init.do?mId=55");
			return getViewPath("/alert");
		}

		// 1-2. 기초진단지 여부
		HashMap<String, Object> completedDgnsBscOne = diagnosisService
				.selectByBplNo(bplNo);
		if (completedDgnsBscOne != null) {
			int completedDgnsBscCount = Integer
					.parseInt(String.valueOf(completedDgnsBscOne.get("COUNT")));

			if (completedDgnsBscCount > 0) {
				int bscIdx = Integer.parseInt(
						String.valueOf(completedDgnsBscOne.get("BSC_IDX")));
				model.addAttribute("bscIdx", bscIdx);
				model.addAttribute("bsc", completedDgnsBscOne);
			} else {
				request.setAttribute("msg", "완료 된 기초진단이 없습니다.");
				request.setAttribute("url", "/web/diagnosis/apply.do?mId=53");
				return getViewPath("/alert");
			}
		} else {
			request.setAttribute("msg", "완료 된 기초진단이 없습니다.");
			request.setAttribute("url", "/web/diagnosis/apply.do?mId=53");
			return getViewPath("/alert");
		}

		if (type.equals("A")) {
			model.addAttribute("type", "A");

			HashMap<String, String> sojtFormInfo = consultingService
					.selectSojtFormInfo(bplNo);
			if (sojtFormInfo != null) {
				model.addAttribute("sojtFormInfo", sojtFormInfo);
			}

			List<HashMap<String, String>> ncsInfoDepth1 = consultingService
					.selectNcsInfoDepth1();
			model.addAttribute("ncsInfoDepth1", ncsInfoDepth1);

			return getViewPath("/cnslApplyForm");

		} else if (type.equals("B")) {
			
			// 1-3, 협약 체크
			int agremCount = agreementService.getAgremCount(bplNo);
			
			if(agremCount > 0) {
				List<HashMap<String, String>> completedAgremList = agreementService.getCompltedAgremList(bplNo);
				model.addAttribute("completedAgremList", completedAgremList);
			}else {
				request.setAttribute("msg", "협약 된 민간센터가 없습니다.");
				request.setAttribute("url", "/web/agreement/list.do?mId=52");
				return getViewPath("/alert");
			}
			
			
			int count = consultingService.selectCnslCountByBplNoAndCnslType(bplNo, "4"); //신청된 카운트
			int saveCount = consultingService.selectCnslSaveCountByBplNoAndCnslType(bplNo, "4"); //저장된 카운트
			log.info("saveCount", saveCount);
			
			if (count > 0) {
				request.setAttribute("msg", "심화컨설팅은 연 1회만 신청이 가능합니다.");
				request.setAttribute("url", "/web/consulting/list.do?mId=64");
				return getViewPath("/alert");
			} else if (saveCount > 0){
				request.setAttribute("msg", "저장 또는 반려 된 심화컨설팅이 있습니다.");
				request.setAttribute("url", "/web/consulting/list.do?mId=64");
				return getViewPath("/alert");
			} else {
				model.addAttribute("type", "B");
				return getViewPath("/cnslApplyForm");
			}

		} else {
			request.setAttribute("msg", "올바른 컨설팅 신청이 아닙니다..");
			request.setAttribute("url", "/web/consulting/list.do?mId=64");
			return getViewPath("/alert");
		}

	}

	/**
	 * 컨설팅 신청서 수정폼
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */

	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(value = "/cnslModifyForm.do")
	public String cnslModifyForm(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, ModelMap model) throws Exception {

		String cnslIdx = request.getParameter("cnslIdx") == null
				? ""
				: request.getParameter("cnslIdx");

		// 컨설팅Idx
		if (!cnslIdx.isEmpty()) {
			Cnsl cnsl = consultingService
					.selectByCnslIdx(Integer.parseInt(cnslIdx));
			model.addAttribute("cnsl", cnsl);

			String bplNo = cnsl.getBplNo();
			String cnslType = cnsl.getCnslType();

			if (cnslType.equals("1") || cnslType.equals("2")
					|| cnslType.equals("3")) {
				return getViewPath("/cnslModifyForm");

			} else if (cnslType.equals("4")) {
				// 1-3, 협약 체크
				int agremCount = agreementService.getAgremCount(bplNo);
				
				if(agremCount > 0) {
					List<HashMap<String, String>> completedAgremList = agreementService.getCompltedAgremList(bplNo);
					model.addAttribute("completedAgremList", completedAgremList);
				}else {
					request.setAttribute("msg", "협약 된 민간센터가 없습니다.");
					request.setAttribute("url", "/web/agreement/list.do?mId=52");
					return getViewPath("/alert");
				}
				
				return getViewPath("/cnslModifyForm");
			} else {
				request.setAttribute("msg", "존재하지 않는 컨설팅 타입입니다.");
				request.setAttribute("url", "/web/consulting/list.do?mId=64");
				return getViewPath("/alert");
			}

		} else {
			request.setAttribute("msg", "컨설팅이 존재하지 않습니다.");
			request.setAttribute("url", "/web/consulting/list.do?mId=64");
			return getViewPath("/alert");

		}

	}

	/**
	 * 민간센터 컨설팅 추가신청
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */

	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(value = "/cnslApplyFormByCenter.do")
	public String cnslApplyFormByCenter(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, ModelMap model) throws Exception {

		String cnslIdx = request.getParameter("cnslIdx") == null
				? ""
				: request.getParameter("cnslIdx");

		// 컨설팅Idx
		if (!cnslIdx.isEmpty()) {
			Cnsl cnsl = consultingService
					.selectByCnslIdx(Integer.parseInt(cnslIdx));

			// 훈련체계수립 카운팅
			int cnslType5Count = consultingService
					.selectCnslCountByBplNoAndCnslType(cnsl.getBplNo(), "5");
			// 현장개선 카운팅
			int cnslType6Count = consultingService
					.selectCnslCountByBplNoAndCnslType(cnsl.getBplNo(), "6");

			model.addAttribute("cnsl", cnsl);
			model.addAttribute("cnslType5Count", cnslType5Count);
			model.addAttribute("cnslType6Count", cnslType6Count);

			return getViewPath("/cnslApplyFormByCenter");
		} else {
			request.setAttribute("msg", "잘못된 컨설팅 신청입니다..");
			request.setAttribute("url",
					"/web/consulting/cnslListAll.do?mId=95");
			return getViewPath("/alert");
		}

	}

	/**
	 * 신청서 상세조회(승인/반려/접수)
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "VEW")
	@RequestMapping(value = "/cnslViewForm.do")
	public String cnslViewForm(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		String siteId = attrVO.getSiteId();
		model.addAttribute("siteId", siteId);
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String cnslIdx = request.getParameter("cnslIdx") == null
				? ""
				: request.getParameter("cnslIdx");

		// 컨설팅Idx
		if (!cnslIdx.isEmpty()) {
			Cnsl cnsl = consultingService
					.selectByCnslIdx(Integer.parseInt(cnslIdx));
			model.addAttribute("cnsl", cnsl);

			String bplNo = cnsl.getBplNo();
			String cnslType = cnsl.getCnslType();

			if (cnslType.equals("1") || cnslType.equals("2")
					|| cnslType.equals("3")) {
				// 1-3, SOJT 협약 여부
				return getViewPath("/cnslViewForm");

			} else if (cnslType.equals("4")) {
				model.addAttribute("loginVO", loginVO);
				return getViewPath("/cnslViewForm");
			} else {
				request.setAttribute("msg", "존재하지 않는 컨설팅 타입입니다.");
				request.setAttribute("url", "/web/consulting/list.do?mId=64");
				return getViewPath("/alert");
			}

		} else {
			request.setAttribute("msg", "컨설팅이 존재하지 않습니다.");
			request.setAttribute("url", "/web/consulting/list.do?mId=64");
			return getViewPath("/alert");

		}
	}

	/**
	 * 컨설팅 저장/신청/승인
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @param cnsl
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/save.do")
	public ModelAndView save(@ModuleAttr ModuleAttrVO attrVO,
			MultipartHttpServletRequest request, ModelMap model,
			@ModelAttribute Cnsl cnsl) throws Exception {
		ModelAndView mav = new ModelAndView();
		String regiIp = request.getRemoteAddr();

		if (cnsl.getConfmStatus().equals("5")) { // 임시저장
				int count = consultingService.selectCnslSubmitCountByBplNoAndCnslType(cnsl.getBplNo(), cnsl.getCnslType());
				cnsl.setCnslTme(count + 1);

			if (cnsl.getCnslIdx() != null) { // 한번이라도 임시저장한 경우
				consultingService.updateCnsl(cnsl, request, regiIp);
				CMRespDto cmRespDto = new CMRespDto.Builder(1)
						.setMsg("저장된 컨설팅 업데이트").setBody(null).build();
				mav.setView(jsonView);
				mav.addObject("result", cmRespDto);
				return mav;
			}
			consultingService.insertCnsl(cnsl, request, regiIp);
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("최초 컨설팅 저장")
					.setBody(null).build();
			mav.setView(jsonView);
			mav.addObject("result", cmRespDto);
			return mav;

		} else if (cnsl.getConfmStatus().equals("10")) { // 제출
			int count = consultingService.selectCnslSubmitCountByBplNoAndCnslType(cnsl.getBplNo(), cnsl.getCnslType());
			cnsl.setCnslTme(count + 1);
			if (cnsl.getCnslIdx() != null) { // 한번이라도 임시저장한 경우
				consultingService.updateCnslBySubmit(cnsl, request, regiIp);
				CMRespDto cmRespDto = new CMRespDto.Builder(1)
						.setMsg("저장된 컨설팅 제출성공").setBody(null).build();
				mav.setView(jsonView);
				mav.addObject("result", cmRespDto);
				return mav;
			}
			consultingService.insertCnsl(cnsl, request, regiIp);
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("최초 컨설팅 제출성공")
					.setBody(null).build();
			mav.setView(jsonView);
			mav.addObject("result", cmRespDto);
			return mav;

		} else if (cnsl.getConfmStatus().equals("55")) {
			consultingService.updateCnslStatus(cnsl, request, regiIp);
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("컨설팅 승인")
					.setBody(null).build();
			mav.setView(jsonView);
			mav.addObject("result", cmRespDto);
			return mav;

		}

		return mav;
	}

	/**
	 * 심화컨설팅 추가신청(민간센터)
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @param cnsl
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/saveByCenter.do")
	public ModelAndView saveByCenter(@ModuleAttr ModuleAttrVO attrVO,
			MultipartHttpServletRequest request, ModelMap model,
			@ModelAttribute Cnsl cnsl) throws Exception {
		ModelAndView mav = new ModelAndView();
		String regiIp = request.getRemoteAddr();

		consultingService.insertCnslByCenter(cnsl, request, regiIp);
		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("심화컨설팅 추가신청")
				.setBody(null).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;
	}

	/**
	 * 컨설팅 회수
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/cancel.do", produces = "application/json; charset=UTF-8")
	public ModelAndView cancel(HttpServletRequest request,
			@RequestBody String json) throws Exception {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		Cnsl cnsl = objectMapper.readValue(json, Cnsl.class);
		String regiIp = request.getRemoteAddr();

		consultingService.updateCnslStatus(cnsl, regiIp);

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("컨설팅 상태변경 성공")
				.setBody(null).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}

	/**
	 * 컨설팅 접수
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/receive.do", produces = "application/json; charset=UTF-8")
	public ModelAndView receive(HttpServletRequest request,
			@RequestBody String json) throws Exception {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		Cnsl cnsl = objectMapper.readValue(json, Cnsl.class);
		String regiIp = request.getRemoteAddr();

		consultingService.updateCnslStatus(cnsl, regiIp);

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("컨설팅 접수 성공")
				.setBody(null).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}

	/**
	 * 컨설팅 반려
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/reject.do", produces = "application/json; charset=UTF-8")
	public ModelAndView reject(HttpServletRequest request,
			@RequestBody String json) throws Exception {
		ModelAndView mav = new ModelAndView();

		ObjectMapper objectMapper = new ObjectMapper();
		Cnsl cnsl = objectMapper.readValue(json, Cnsl.class);
		String regiIp = request.getRemoteAddr();

		consultingService.updateCnslStatus(cnsl, regiIp);

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("컨설팅 반려 성공")
				.setBody(null).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}

	/**
	 * 컨설팅 변경신청
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @param cnsl
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/changeCnsl.do")
	public ModelAndView changeCnsl(@ModuleAttr ModuleAttrVO attrVO,
			MultipartHttpServletRequest request, ModelMap model,
			@ModelAttribute Cnsl cnsl) throws Exception {
		ModelAndView mav = new ModelAndView();
		String cnslIdx = request.getParameter("cnslIdx") == null
				? ""
				: request.getParameter("cnslIdx");
		MultipartFile file = request.getFile("file");
		String regiIp = request.getRemoteAddr();

		consultingService.insertChangeCnsl(cnsl, request, regiIp);
		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("컨설팅 변경신청")
				.setBody(null).build();

		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}

	/**
	 * 컨설팅변경신청 승인
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/changeConfirm.do")
	public ModelAndView changeCnslConfirm(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, ModelMap model,
			@RequestBody String json) throws Exception {
		ModelAndView mav = new ModelAndView();
		/**
		 * 승인 0. cnslIdx로 변경점 조회해오기 1. HRD_CNSL_CHANGE 상태값 변경(70 -> 72) 2.
		 * HRD_CNSL/TEAM/FILE 에 데이터를 덮어씀 3. log 처리? TODO
		 */
		int cnslIdx = Integer.parseInt(json);
		String regiIp = request.getRemoteAddr();
		consultingService.changeConfirm(cnslIdx, regiIp);

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("컨설팅 변경신청 승인")
				.setBody(null).build();

		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;
	}

	/**
	 * 컨설팅변경신청 반려
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @param cnsl
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/changeReject.do")
	public ModelAndView changeCnslReject(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, ModelMap model,
			@RequestBody String json) throws Exception {
		ModelAndView mav = new ModelAndView();

		ObjectMapper objectMapper = new ObjectMapper();
		Cnsl cnsl = objectMapper.readValue(json, Cnsl.class);
		String regiIp = request.getRemoteAddr();
		consultingService.changeReject(cnsl, regiIp);

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("컨설팅 변경신청 반려")
				.setBody(null).build();

		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;
	}

	/**
	 * 민간센터 정보 조회
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/selectCenterInfo.do", produces = "application/json; charset=UTF-8")
	public ModelAndView selectCenterInfo(HttpServletRequest request,
			@RequestBody String centerIdx) throws Exception {
		ModelAndView mav = new ModelAndView();

		HashMap<String, String> centerInfo = consultingService
				.selectCenterInfo(centerIdx);

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("민간센터 정보 조회")
				.setBody(centerInfo).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}

	/**
	 * 주소 입력시 해당 지부지사 select
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/selectInsttIdxByZip.do", produces = "application/json; charset=UTF-8")
	public ModelAndView selectInsttIdxByZip(HttpServletRequest request,
			@RequestBody String zip) throws Exception {
		ModelAndView mav = new ModelAndView();
		HashMap<String, String> insttInfo = consultingService
				.selectInsttIdxByZip(zip);

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("지부지사조회")
				.setBody(insttInfo).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}

	/**
	 * NCS 코드 검색
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/selectNcsInfo.do", produces = "application/json; charset=UTF-8")
	public ModelAndView selectNcsInfo(HttpServletRequest request,
			@RequestBody String json) throws Exception {
		ModelAndView mav = new ModelAndView();

		List<HashMap<String, String>> ncsInfo = consultingService
				.selectNcsInfo(json);

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("NCS조회")
				.setBody(ncsInfo).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}

	/**
	 * NCS Depth1 코드 검색
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/selectNcsInfoDepth1.do", produces = "application/json; charset=UTF-8")
	public ModelAndView selectNcsInfoDepth1(HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView();

		List<HashMap<String, String>> ncsInfo = consultingService
				.selectNcsInfoDepth1();

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("NCS 1Depth 조회")
				.setBody(ncsInfo).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}

	/**
	 * NCS 다음 Depth 검색
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/selectNextNcsDepth.do", produces = "application/json; charset=UTF-8")
	public ModelAndView selectNextNcsDepth(HttpServletRequest request,
			@RequestBody String json) throws Exception {
		ModelAndView mav = new ModelAndView();

		log.info("json", json);

		List<HashMap<String, String>> ncsInfo = consultingService
				.selectNextNcsDepth(json);

		CMRespDto cmRespDto = new CMRespDto.Builder(1)
				.setMsg("NCS next Depth 조회").setBody(ncsInfo).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}

	/**
	 * 해당 insttIdx로 주치의 조회
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/selectDoctorInfo.do", produces = "application/json; charset=UTF-8")
	public ModelAndView selectDoctorInfo(HttpServletRequest request,
			@RequestBody String insttIdx) throws Exception {
		ModelAndView mav = new ModelAndView();

		List<HashMap<String, Object>> doctorInfo = consultingService
				.selectDoctorInfoByInsttIdx(insttIdx);

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("지부지사 주치의 조회")
				.setBody(doctorInfo).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}

	/**
	 * 해당 컨설팅 주치의 변경
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/changeCmptncBrffcPic.do", produces = "application/json; charset=UTF-8")
	public ModelAndView changeCmptncBrffcPic(HttpServletRequest request,
			@RequestBody String json) throws Exception {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String, String> map = objectMapper.readValue(json,
				HashMap.class);

		int count = consultingService.changeCmptncBrffcPic(map);

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("지부지사 주치의 조회")
				.setBody(count).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}

	/**
	 * 파일 다운로드
	 */
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.GET, value = "/download.do")
	public void download(@RequestParam String fileName,
			HttpServletResponse response) throws Exception {
		try {
			String filePath = uploadDir + fileName;
			File file = new File(filePath);

			FileInputStream fileInputStream = new FileInputStream(filePath);

			response.setContentType("application/download");
			response.setHeader("Content-Disposition",
					"attachment; filename=" + new String(
							fileName.getBytes(StandardCharsets.UTF_8),
							StandardCharsets.ISO_8859_1));
			ByteStreams.copy(fileInputStream, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("download error");
		}

	}

	/**
	 * 컨설팅팀 적합여부
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws Exception
	 */

	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/searchSuitable.do", produces = "application/json; charset=UTF-8")
	public ModelAndView searchSuitable(HttpServletRequest request,
			@RequestBody String hrd4uId) throws Exception {
		ModelAndView mav = new ModelAndView();

		String result = "";
		int code = 0;
		HashMap<String, String> count = new HashMap<>();
		int limitCount = 0;
		// 1. 보안서약서
		try {
			int indvdInfo = consultingService.selectCountIndvdlInfo(hrd4uId);
			log.info("indvdInfo {}", indvdInfo);
			int trainingInfo = consultingService.isTrainingComplecated(hrd4uId);
			log.info("trainingInfo {}", trainingInfo);
			count = consultingService.selectCountOfConsulting(hrd4uId);
			log.info("count {}", count);
			if (count != null) {
				limitCount = Integer
						.parseInt(String.valueOf(count.get("TOTALCOUNT")));
			}
			log.info("limitCount {}", limitCount);
			if (indvdInfo > 0 && trainingInfo > 0 && limitCount < 20) {
				result = "true";
				code = 1;
			} else if (indvdInfo < 1) {
				result = "false";
				code = 2;
			} else if (trainingInfo < 1) {
				result = "false";
				code = 3;
			} else if (limitCount > 19) {
				result = "false";
				code = 4;
			}

		} catch (Exception e) {
			log.info("ERROR {}", e.getMessage());
		}

		CMRespDto cmRespDto = new CMRespDto.Builder(code).setMsg(result)
				.setBody(count).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}

	/**
	 * 전문가 인력풀 조회
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws Exception
	 */

	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/searchHRP.do", produces = "application/json; charset=UTF-8")
	public ModelAndView searchHRP(HttpServletRequest request,
			@RequestBody String hrd4uId) throws Exception {
		ModelAndView mav = new ModelAndView();

		// Hrd4U 아이디 유효성 검사
		HashMap<String, String> hrd4uInfo = consultingService
				.select4uById(hrd4uId);
		if (hrd4uInfo != null) {
			// 전문가 인력풀 유효성 검사
			HrpUserDTO dto = consultingService.selectHrpById(hrd4uId);

			if (dto == null) {
				CMRespDto cmRespDto = new CMRespDto.Builder(2)
						.setMsg("인력풀 미등록 유저").setBody(hrd4uInfo).build();
				mav.setView(jsonView);
				mav.addObject("result", cmRespDto);
				return mav;

			} else {
				dto.setMemberId(hrd4uId);
				CMRespDto cmRespDto = new CMRespDto.Builder(1)
						.setMsg("인력풀 등록 유저").setBody(dto).build();
				mav.setView(jsonView);
				mav.addObject("result", cmRespDto);
				return mav;
			}

		} else {
			CMRespDto cmRespDto = new CMRespDto.Builder(-1)
					.setMsg("HRD4U ID가 없습니다.").setBody(null).build();
			mav.setView(jsonView);
			mav.addObject("result", cmRespDto);
			return mav;

		}

	}
	
	/**
	 * 주치의 조회(PM 위촉시)
	 * 
	 * @param request
	 * @param json
	 * @return
	 * @throws Exception
	 */

	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/searchDoctor.do", produces = "application/json; charset=UTF-8")
	public ModelAndView searchDoctor(HttpServletRequest request,
			@RequestBody String hrd4uId) throws Exception {
		ModelAndView mav = new ModelAndView();

		// Hrd4U 아이디 유효성 검사
		HashMap<String, String> hrd4uInfo = consultingService.select4uById(hrd4uId);
		if (hrd4uInfo != null) {
			// 전문가 인력풀 유효성 검사
			HashMap<String, String> doctorInfo = consultingService.selectDoctorById(hrd4uId);

			if (doctorInfo == null) {
				CMRespDto cmRespDto = new CMRespDto.Builder(0)
						.setMsg("해당 ID의 주치의가 없습니다.").setBody(null).build();
				mav.setView(jsonView);
				mav.addObject("result", cmRespDto);
				return mav;

			} else {
				CMRespDto cmRespDto = new CMRespDto.Builder(1)
						.setMsg("주치의 유저").setBody(doctorInfo).build();
				mav.setView(jsonView);
				mav.addObject("result", cmRespDto);
				return mav;
			}

		} else {
			CMRespDto cmRespDto = new CMRespDto.Builder(-1)
					.setMsg("HRD4U ID가 없습니다.").setBody(null).build();
			mav.setView(jsonView);
			mav.addObject("result", cmRespDto);
			return mav;

		}

	}

	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/sojtLimitCheck.do", produces = "application/json; charset=UTF-8")
	public ModelAndView sojtLimitCheck(HttpServletRequest request,
			@RequestBody String json) throws Exception {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();

		HashMap<String, String> map = objectMapper.readValue(json, HashMap.class);

		String bplNo = map.get("bplNo");
		String cnslType = map.get("cnslType");

		HashMap<String, Object> result = new HashMap<>();
		int sojtConfm = consultingService.selectSojtConfm(bplNo);
		result.put("sojtConfm", sojtConfm);
		int sojtNomalCount = consultingService
				.selectCnslCountByBplNoAndCnslType2(bplNo);
		result.put("sojtNomalCount", sojtNomalCount);
		int sojtPblCount = consultingService
				.selectCnslCountByBplNoAndCnslType(bplNo, cnslType);
		result.put("sojtPblCount", sojtPblCount);

		CMRespDto cmRespDto = new CMRespDto.Builder(2).setMsg("SOJT체크 결과")
				.setBody(result).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}
	
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/sojtSaveCheck.do", produces = "application/json; charset=UTF-8")
	public ModelAndView sojtSaveCheck(HttpServletRequest request,
			@RequestBody String json) throws Exception {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();

		HashMap<String, String> map = objectMapper.readValue(json, HashMap.class);

		String bplNo = map.get("bplNo");
		String cnslType = map.get("cnslType");

		int savedCount = consultingService.selectCnslSaveCountByBplNoAndCnslType(bplNo, cnslType);

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("과정개발 save 체크").setBody(savedCount).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}

	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/checkLimitCnslType.do", produces = "application/json; charset=UTF-8")
	public ModelAndView checkLimitCnslType(HttpServletRequest request,
			@RequestBody String json) throws Exception {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();

		HashMap<String, String> map = objectMapper.readValue(json,
				HashMap.class);

		String bplNo = map.get("bplNo");
		String cnslType = map.get("cnslType");

		int count = consultingService.selectCnslCountByBplNoAndCnslType(bplNo,
				cnslType);

		CMRespDto cmRespDto = new CMRespDto.Builder(2).setMsg("추가컨설팅 가능여부")
				.setBody(count).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}

	/**
	 * 컨설팅 반려의견 조회
	 * 
	 * @param request
	 * @param cnslIdx
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/selectConfmCn.do", produces = "application/json; charset=UTF-8")
	public ModelAndView selectConfmCn(HttpServletRequest request,
			@RequestBody String cnslIdx) throws Exception {
		ModelAndView mav = new ModelAndView();

		List<Cnsl> confmCnList = consultingService.selectConfmCn(cnslIdx);

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("컨설팅 반려의견 조회결과")
				.setBody(confmCnList).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}
	
	/**
	 * 컨설팅변경 반려의견 조회
	 * 
	 * @param request
	 * @param cnslIdx
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/selectChangeConfmCn.do", produces = "application/json; charset=UTF-8")
	public ModelAndView selectChangeConfmCn(HttpServletRequest request,
			@RequestBody String cnslIdx) throws Exception {
		ModelAndView mav = new ModelAndView();

		List<Cnsl> confmCnList = consultingService.selectChangeConfmCn(cnslIdx);

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("컨설팅 반려의견 조회결과")
				.setBody(confmCnList).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}

	/**
	 * 목록조회
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "LST")
	@RequestMapping(value = "/diaryList.do")
	public String diaryList(@ModuleAttr ModuleAttrVO attrVO,
			@ParamMap ParamForm parameterMap,
			@RequestParam Map<String, Object> map, HttpServletRequest request,
			ModelMap model, @PathVariable("siteId") String siteId)
			throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		model.addAttribute("mId", parameterMap.get("mId")); // 목록

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		// 1. 페이지정보 setting
		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		int usePaging = JSONObjectUtil.getInt(settingInfo, "use_paging");
		if (usePaging == 1) {
			int listUnit = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_count", crtMenu, settingInfo,
					propertiesService.getInt("DEFAULT_LIST_UNIT")); // 페이지당 목록 수
			int listMaxUnit = listUnit
					* propertiesService.getInt("DEFAULT_LIST_MAX_CNT"); // 최대
																		// 페이지당
																		// 목록 수
			int listUnitStep = listUnit; // 페이지당 목록 수 증가값

			int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
			if (pageUnit == 0)
				pageUnit = listUnit;// JSONObjectUtil.getInt(settingInfo,
									// "dset_list_count",
									// propertiesService.getInt("PAGE_UNIT"));
									// // 페이지당 목록 수
			int pageSize = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_block", crtMenu, settingInfo,
					propertiesService.getInt("PAGE_SIZE"));

			String pageName = JSONObjectUtil.getString(settingInfo,
					"page_name");
			int page = StringUtil.getInt(request.getParameter(pageName), 1); // 현재
																				// 페이지
																				// index

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
		String listSearchId = "list_search"; // 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo,
				listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil
				.getItemInfoSearchList(listSearch, queryString);
		if (itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}

		// 탭코드
		String cateTabItemId = JSONObjectUtil.getString(settingInfo,
				"dset_cate_tab_id");
		String cateTabColumnId = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"column_id");
		String cateTabMasterCode = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"master_code");
		int cateTabObjectType = JSONObjectUtil.getInt(settingInfo,
				"dset_cate_tab_object_type");
		// 검색 코드
		HashMap<String, Object> searchOptnHashMap = CodeHelper
				.getItemOptnSearchHashMap(listSearch, cateTabItemId,
						cateTabColumnId, cateTabObjectType, cateTabMasterCode);
		// 목록 코드
		HashMap<String, Object> optnHashMap = getOptionHashMap(submitType,
				itemInfo, searchOptnHashMap);// CodeHelper.getItemOptnHashMap(items,
												// itemOrder,
												// searchOptnHashMap);
		// 탭 검색 조건
		List<DTForm> cateTabSearchList = ModuleUtil.getCateTabSearchList(
				settingInfo, items, queryString, optnHashMap);// fn_getCateTabSearchList(queryString,
																// settingInfo,
																// items,
																// optnHashMap);
		if (cateTabSearchList != null) {
			searchList.addAll(cateTabSearchList);
		}

		if (siteId.equals("dct")) {
			param.put("brffcCd", loginVO.getRegiCode());
			if (parameterMap.get("insttIdx") != null) {
				param.put("insttIdx", parameterMap.get("insttIdx"));
			}
		}
		param.put("searchList", searchList);
		param.put("bplNo", loginVO.getBplNo());
		param.put("cnslIdx", parameterMap.getInt("cnslIdx"));

		// 2.2 목록수
		totalCount = consultingService.getDiaryCount(fnIdx, param);
		paginationInfo.setTotalRecordCount(totalCount);

		if (totalCount > 0) {
			if (usePaging == 1) {
				param.put("firstIndex", paginationInfo.getFirstRecordIndex());
				param.put("lastIndex", paginationInfo.getLastRecordIndex());
				param.put("recordCountPerPage",
						paginationInfo.getRecordCountPerPage());
			}

			// 정렬컬럼 setting
			param.put("dsetOrderField",
					JSONObjectUtil.getString(settingInfo, "dset_order_field"));
			param.put("regiId", loginVO.getMemberId());
			param.put("regiIdx", loginVO.getMemberIdx());

			// 2.3 목록
			list = consultingService.getDiaryList(fnIdx, param);
		}

		// 3. 속성 setting
		model.addAttribute("map", map);
		model.addAttribute("param", param);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("totalCount", totalCount); // 페이징정보
		model.addAttribute("list", list); // 목록
		model.addAttribute("searchOptnHashMap", searchOptnHashMap); // 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("loginVO", loginVO);

		// 4. 기본경로
		fn_setCommonPath(attrVO);

		return getViewPath("/diaryList");
	}

	/**
	 * 수정 : 관리권한 없는 경우 - 자신이 등록한 글만 수정
	 * 
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@ModuleAuth(name = {"WRT"})
	@RequestMapping(value = "/diaryInput.do", params = "mode")
	public String diaryInput(@RequestParam(value = "mode") String mode,
			@ModuleAttr ModuleAttrVO attrVO,
			@RequestParam Map<String, Object> map, HttpServletRequest request,
			ModelMap model, @PathVariable("siteId") String siteId)
			throws Exception {
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if (siteId.equals("dct")) {
			if (loginVO != null) {
				model.addAttribute("loginVO", loginVO);
			}
		}

		// 과정개발정보 받아오기

		// int getDevIdx = consultingService.getDevelope(loginVO.getBplNo());
		// model.addAttribute("getDev", getDevIdx);

		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(
				request.getParameter(
						JSONObjectUtil.getString(settingInfo, "diary_name")),
				0);

		// 1.2 수정모드 여부 검사
		boolean isModify = (StringUtil.isEquals(mode, "m")) ? true : false;
		if (isModify == true) {
			model.addAttribute("mode", "&mode=m");
		}

		if (keyIdx <= 0 || !isModify) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}

		// 2. DB
		String idxColumnName = JSONObjectUtil.getString(settingInfo,
				"diary_column"); // key column명

		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();

		// 2.1 수정권한 체크
		Boolean isMngAuth = isMngProcAuth(settingInfo, fnIdx, keyIdx);
		if (!isMngAuth) {
			// 수정권한 없는 경우
			model.addAttribute("message", MessageUtil
					.getAlert(rbsMessageSource.getMessage("message.no.auth")));
			return RbsProperties.getProperty("Globals.fail.path");
		}

		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		param.put("searchList", searchList);

		// 2.2 상세정보
		dt = consultingService.getModify(fnIdx, param);

		if (dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(
					rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}

		// 3 속성 setting
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject crtMenu = attrVO.getCrtMenu();

		// 3.1 항목설정
		String submitType = "modify";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo,
				submitType + "_order");

		// 3.2 속성 setting
		model.addAttribute("dt", dt);
		model.addAttribute("multiDataHashMap", consultingService
				.getMultiHashMap(fnIdx, keyIdx, settingInfo, items, itemOrder)); // multi
																					// file
																					// 목록
		model.addAttribute("multiFileHashMap",
				consultingService.getMultiFileHashMap(fnIdx, keyIdx,
						settingInfo, items, itemOrder)); // multi file 목록

		model.addAttribute("optnHashMap",
				CodeHelper.getItemOptnHashMap(items, itemOrder)); // 항목코드
		model.addAttribute("submitType", submitType); // 페이지구분

		// 기업 기본정보 받아오기
		if (map.get("cnslIdx") != null) {
			Cnsl dto = consultingService.selectByCnslIdx(
					Integer.parseInt((String) map.get("cnslIdx")));
			model.addAttribute("cnsl", dto);
		}

		// 4. 기본경로
		fn_setCommonPath(attrVO);

		// 5. form action
		model.addAttribute("URL_SUBMITPROC",
				request.getAttribute("URL_IDX_MODIFYPROC"));

		return getViewPath("/diaryInput");
	}

	/**
	 * 등록
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(value = "/diaryInput.do")
	public String diaryInput(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, @RequestParam Map<String, Object> map,
			ModelMap model, @PathVariable("siteId") String siteId)
			throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();

		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		String submitType = "write";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo,
				submitType + "_order");
		int totalCount = 0;

		// 3.2 속성 setting
		model.addAttribute("submitType", submitType); // 페이지구분

		// 기업 기본정보 받아오기
		if (map.get("cnslIdx") != null) {
			Cnsl dto = consultingService.selectByCnslIdx(
					Integer.parseInt((String) map.get("cnslIdx")));
			model.addAttribute("cnsl", dto);
		}

		totalCount = consultingService.getDiaryCount(fnIdx, map);
		totalCount = totalCount + 1;
		model.addAttribute("totalCount", totalCount);

		// 2. 기본경로
		fn_setCommonPath(attrVO);
		return getViewPath("/diaryInput");
	}

	/**
	 * 수정처리 : 관리권한 없는 경우 - 자신이 등록한 글만 수정
	 * 
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@ModuleAuth(name = {"WRT"})
	@RequestMapping(method = RequestMethod.POST, value = "/inputProc.do", params = "mode")
	public String inputProc(@RequestParam(value = "mode") String mode,
			@ParamMap ParamForm parameterMap,
			@PathVariable("siteId") String siteId,
			@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request,
			ModelMap model) throws Exception {
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
		int keyIdx = StringUtil.getInt(request.getParameter(JSONObjectUtil.getString(settingInfo, "diary_name")),0);

		// 1.2 수정모드 여부 검사
		boolean isModify = (StringUtil.isEquals(mode, "m")) ? true : false;

		if (keyIdx <= 0 || !isModify) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}

		// 2. DB
		// 2.1 상세정보 - 해당글이 없는 경우 return
		String idxColumnName = JSONObjectUtil.getString(settingInfo,
				"diary_column"); // key column명

		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();

		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		param.put("searchList", searchList);

		dt = consultingService.getModify(fnIdx, param);
		if (dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(isAjax,rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}

		// 2.2 전체관리/완전삭제 권한 체크 - 수정권한 없는 경우 return
		Boolean isMngAuth = isMngProcAuth(settingInfo, fnIdx, keyIdx);
		if (!isMngAuth) {
			// 수정권한 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(isAjax,rbsMessageSource.getMessage("message.no.auth")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}

		// 3. 필수입력 체크
		// 3.1 항목설정
		String submitType = "modify"; // 항목 order명
		String inputFlag = "modify"; // 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo,submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		parameterMap.put("trTme", 1);
		parameterMap.put("regiIdx", loginVO.getMemberIdx());

		int cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));
		String bplNo = StringUtil.getString(request.getParameter("bplNo"));
		String toUrl = "/hrddoctor/web/consulting/viewAll.do?mId=95&cnslIdx=" + cnslIdx+ "&bplNo=" + bplNo + "#tab3";
		String mtgStartDt = parameterMap.getString("mtgStartDt") + " "+ parameterMap.getString("mtgStartTime");
		String mtgEndDt = parameterMap.getString("mtgEndDt") + " "+ parameterMap.getString("mtgEndTime");
		parameterMap.put("mtgStartDt", mtgStartDt);
		parameterMap.put("mtgEndDt", mtgEndDt);

		// 컨설턴트 수행일지 중복 체크
		int chkDuplicate = consultingService.chkDuplicate(fnIdx, parameterMap);
			if (chkDuplicate > 0) {
				request.setAttribute("msg", "해당 시간에 참여되어 있는 수행일지가 존재합니다.");
				request.setAttribute("url", toUrl);
				model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, "해당 시간에 참여되어 있는 수행일지가 존재합니다.","fn_procAction(\"" + toUrl + "\", "+ StringUtil.getInt(request.getParameter("mdl"))+ ");")+ "#tab3");
				return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
			} else {
				// 인력풀 회의 체크
				int chkEntrstRole = consultingService.chkEntrstRole(fnIdx, parameterMap);
				if (chkEntrstRole > 0) {
					request.setAttribute("msg", "해당 시간에 위촉되어 있는 회의가 존재합니다.");
					request.setAttribute("url", toUrl);
					model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, "해당 시간에 위촉되어 있는 회의가 존재합니다.","fn_procAction(\"" + toUrl + "\", "+ StringUtil.getInt(request.getParameter("mdl"))+ ");")+ "#tab3");
					return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
					
				} else {
				// 4. DB
				int result = consultingService.update(uploadModulePath, fnIdx, keyIdx,request.getRemoteAddr(), parameterMap, settingInfo, items,itemOrder);
				if (result > 0) {
					// 4.1 저장 성공
					// 기본경로
					fn_setCommonPath(attrVO);
					model.addAttribute("message",MessageUtil.getAlertAddWindow(isAjax,rbsMessageSource.getMessage("message.insert"),"fn_procAction(\"" + toUrl + "\", "+ StringUtil.getInt(request.getParameter("mdl"))+ ");")+ "#tab3");
					return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
				} else if (result == -1) {
					// 4.2 파일업로드 오류
					String fileFailView = getFileFailViewPath(request);
					if (!StringUtil.isEmpty(fileFailView))
						return fileFailView;
				}
			}
		}
			
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax,rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		
	}

	/**
	 * 등록처리
	 * 
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/inputProc.do")
	public String inputProc(@ParamMap ParamForm parameterMap,
			@PathVariable("siteId") String siteId,
			@RequestParam Map<String, Object> paramMap,
			@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		int fnIdx = attrVO.getFnIdx();

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write"; // 항목 order명
		String inputFlag = "write"; // 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo,submitType + "proc_order");
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();

		parameterMap.put("_itemInputTypeFlag", submitType);
		parameterMap.put("regiIdx", loginVO.getMemberIdx());
		parameterMap.put("regiId", loginVO.getMemberId());
		parameterMap.put("regiName", loginVO.getMemberName());
		parameterMap.put("siteId", siteId);
		
		int cnslIdx = parameterMap.getInt("cnslIdx");
		String bplNo = parameterMap.getString("bplNo");
		String mtgStartDt = parameterMap.getString("mtgStartDt") + " "+ parameterMap.getString("mtgStartTime");
		String mtgEndDt = parameterMap.getString("mtgEndDt") + " "+ parameterMap.getString("mtgEndTime");
		String toUrl = "/hrddoctor/web/consulting/viewAll.do?mId=95&cnslIdx=" + cnslIdx + "&bplNo=" + bplNo + "#tab3";
		parameterMap.put("mtgStartDt", mtgStartDt);
		parameterMap.put("mtgEndDt", mtgEndDt);
		
		// 컨설턴트 수행일지 중복 체크
		int chkDuplicate = consultingService.chkDuplicate(fnIdx, parameterMap);
		if (chkDuplicate > 0) {
			request.setAttribute("msg", "해당 시간에 참여되어 있는 수행일지가 존재합니다.");
			request.setAttribute("url", toUrl);
			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, "해당 시간에 참여되어 있는 수행일지가 존재합니다.","fn_procAction(\"" + toUrl + "\", "+ StringUtil.getInt(request.getParameter("mdl"))+ ");")+ "#tab3");
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
		} else {
			// 인력풀 회의 체크
			int chkEntrstRole = consultingService.chkEntrstRole(fnIdx, parameterMap);
			if (chkEntrstRole > 0) {
				request.setAttribute("msg", "해당 시간에 위촉되어 있는 회의가 존재합니다.");
				request.setAttribute("url", toUrl);
				model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, "해당 시간에 위촉되어 있는 회의가 존재합니다.","fn_procAction(\"" + toUrl + "\", "+ StringUtil.getInt(request.getParameter("mdl"))+ ");")+ "#tab3");
				return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
				
			} else {
				int diaryInsert = consultingService.insert(uploadModulePath, fnIdx, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
				if (diaryInsert > 0) {
					// 2.1 저장 성공
					// 기본경로
					fn_setCommonPath(attrVO);
					String inputNpname = fn_dsetInputNpname(settingInfo);
					model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"),"fn_procAction(\"" + toUrl + "\", "+ StringUtil.getInt(request.getParameter("mdl"))+ ");")+ "#tab3");
					return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
				} else if (diaryInsert == -1) {
					// 2.2 파일업로드 오류
					String fileFailView = getFileFailViewPath(request);
					if (!StringUtil.isEmpty(fileFailView))
						return fileFailView;
				}
			}
			// 2.3 저장 실패
			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
	}

	/**
	 * 기본경로
	 */
	public void fn_setCommonPath(ModuleAttrVO attrVO) {
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		DataForm queryString = attrVO.getQueryString();

		boolean useSsl = JSONObjectUtil.isEquals(settingInfo, "use_ssl", "1");

		String idxName = JSONObjectUtil.getString(settingInfo, "idx_name"); // 상세조회
																			// key
		String pageName = JSONObjectUtil.getString(settingInfo, "page_name"); // 목록
																				// 페이징
																				// key

		String listSearchId = "list_search";
		String[] searchParams = PathUtil.getSearchParams(
				JSONObjectUtil.getJSONObject(itemInfo, listSearchId)); // 목록 검색
																		// 항목
		String[] tabBaseParams = null;
		String cateTabId = JSONObjectUtil.getString(settingInfo,
				"dset_cate_tab_id");
		if (!StringUtil.isEmpty(cateTabId))
			tabBaseParams = new String[]{RbsProperties.getProperty(
					"Globals.item.tab.search.pre.flag") + cateTabId};

		String listName = "list.do";
		String viewName = "view.do";
		String inputName = "input.do";
		String inputProcName = "inputProc.do";
		String deleteProcName = "deleteProc.do";
		String deleteListName = "deleteList.do";
		String imageName = "image.do";
		String movieName = "movie.do";
		String downloadName = "download.do";

		if (useSsl) {
			inputProcName = PathUtil.getSslPagePath(inputProcName);
		}

		PathUtil.fn_setCommonPath(queryString, baseParams, tabBaseParams,
				searchParams, null, null, pageName, idxName, listName, viewName,
				null, null, null, null, inputName, inputProcName,
				deleteProcName, deleteListName, imageName, movieName,
				downloadName);
	}

	/**
	 * 저장 후 되돌려줄 페이지 속성명
	 * 
	 * @param settingInfo
	 * @return
	 */
	public String fn_dsetInputNpname(JSONObject settingInfo) {
		String dsetInputNpname = JSONObjectUtil.getString(settingInfo,
				"dset_input_npname");
		if (StringUtil.isEmpty(dsetInputNpname)) {
			dsetInputNpname = "LIST";
			return dsetInputNpname;
		}

		return dsetInputNpname;
	}

	/**
	 * 관리권한 체크
	 * 
	 * @param settingInfo
	 * @param fnIdx
	 * @param keyIdx
	 * @param memIdx
	 * @param useReply
	 * @param pwd
	 * @return
	 */
	public boolean isMngProcAuth(JSONObject settingInfo, int fnIdx,
			int keyIdx) {
		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");

		int modiCnt = 0;
		// 권한 체크
		Boolean isMngAuth = AuthHelper.isAuthenticated("MNG"); // 전체관리

		if (!isMngAuth) {
			Map<String, Object> param = new HashMap<String, Object>();
			List<DTForm> searchList = new ArrayList<DTForm>();

			searchList.add(new DTForm("A." + columnName, keyIdx));

			// 전체관리권한 없는 경우 : 자신글만 수정
			boolean isLogin = UserDetailsHelper.isLogin(); // 로그인한 경우
			if (isLogin) {
				// 로그인한 경우
				String memberIdx = null;

				LoginVO loginVO = (LoginVO) UserDetailsHelper
						.getAuthenticatedUser();
				if (loginVO != null) {
					memberIdx = loginVO.getMemberIdx();
				}

				if (StringUtil.isEmpty(memberIdx))
					return false;
				param.put("AUTH_MEMBER_IDX", memberIdx);
			} else {
				// 본인인증 or 로그인 안 한 경우
				return false;
			}

			param.put("searchList", searchList);
			modiCnt = consultingService.getAuthCount(fnIdx, param);

			return (modiCnt > 0);
		}

		return true;
	}

	/**
	 * 파일업로드
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(value = "/fileUpload.do")
	public ModelAndView fileUpload(@ParamMap ParamForm parameterMap,
			@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
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
		String submitType = StringUtil.getString(parameterMap.get("itemId")); // 항목
																				// order명
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo,
				submitType + "proc_order");

		// 1.2 필수입력 체크
		if (parameterMap.get(submitType) == null) {
			model.addAttribute("message", MessageUtil.getAlert(isAjax,
					rbsMessageSource.getMessage("message.error.data")));
			return mav;
		}

		// 2. DB
		Map<String, Object> fileInfo = consultingService.getFileUpload(
				uploadModulePath, fnIdx, request.getRemoteAddr(), parameterMap,
				settingInfo, items, itemOrder);

		if (fileInfo != null && !fileInfo.isEmpty()) {
			// 2.1 저장 성공
			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax,
					rbsMessageSource.getMessage("message.insert"), ""));
			model.addAttribute("data", fileInfo);
			return mav;
		} else {
			// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if (!StringUtil.isEmpty(fileFailView)) {
				model.addAttribute("message", fileFailView);
				return mav;
			}
		}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax,
				rbsMessageSource.getMessage("message.no.insert")));
		return mav;
	}

	/**
	 * 상세조회
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "VEW")
	@RequestMapping(value = "/diaryView.do")
	public String diaryView(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model, @PathVariable("siteId") String siteId)
			throws Exception {
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		// 1. 필수 parameter 검사
		int keyIdx = StringUtil.getInt(
				request.getParameter(
						JSONObjectUtil.getString(settingInfo, "diary_name")),
				0);
		if (keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		String idxColumnName = JSONObjectUtil.getString(settingInfo,
				"diary_column");

		// 2. DB
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		param.put("searchList", searchList);
		// 상세정보
		dt = consultingService.getView(fnIdx, param);
		if (dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(
					rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}

		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "view";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo,
				submitType + "_order");

		// 3.2 속성 setting
		model.addAttribute("dt", dt); // 상세정보
		model.addAttribute("multiFileHashMap",
				consultingService.getMultiFileHashMap(fnIdx, keyIdx,
						settingInfo, items, itemOrder)); // multi file 목록
		model.addAttribute("multiDataHashMap", consultingService
				.getMultiHashMap(fnIdx, keyIdx, settingInfo, items, itemOrder)); // multi
																					// file
																					// 목록
		model.addAttribute("optnHashMap",
				CodeHelper.getItemOptnHashMap(items, itemOrder)); // 항목코드
		model.addAttribute("submitType", submitType); // 페이지구분

		// 4. 기본경로
		fn_setCommonPath(attrVO);

		return getViewPath("/diaryView");
	}

	/**
	 * 삭제처리 : 관리권한 없는 경우 - 자신이 등록한 글만 수정
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.D)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/deleteProc.do")
	public String delete(@ParamMap ParamForm parameterMap,
			@PathVariable("siteId") String siteId,
			@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request,
			ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		JSONObject settingInfo = attrVO.getSettingInfo();
		int fnIdx = attrVO.getFnIdx();
		// 1. 필수 parameter 검사
		int keyIdx = StringUtil.getInt(
				request.getParameter(
						JSONObjectUtil.getString(settingInfo, "diary_name")),
				0);
		if (keyIdx <= 0) {
			return RbsProperties
					.getProperty("Globals.error.400" + ajaxPName + ".path");
		}

		// 2. 전체관리/완전삭제 권한 체크
		Boolean isMngAuth = isMngProcAuth(settingInfo, fnIdx, keyIdx);
		if (!isMngAuth) {
			// 자신이 등록한 글이 아닌 경우
			model.addAttribute("message", MessageUtil.getAlert(isAjax,
					rbsMessageSource.getMessage("message.no.auth")));
			return RbsProperties
					.getProperty("Globals.fail" + ajaxPName + ".path");
		}

		// 3. DB
		int[] deleteIdxs = {keyIdx};
		int result = consultingService.delete(fnIdx, parameterMap, deleteIdxs,
				request.getRemoteAddr(), settingInfo);

		int cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));
		String bplNo = StringUtil.getString(request.getParameter("bplNo"));
		String toUrl = "";
		if (siteId.equals("dct")) {
			toUrl = "/dct/consulting/diaryList.do?mId=133&cnslIdx=" + cnslIdx
					+ "#tab3";
		} else {
			toUrl = "/hrddoctor/web/consulting/viewAll.do?mId=95&cnslIdx=" + cnslIdx
					+ "&bplNo=" + bplNo + "#tab3";
		}

		if (result > 0) {
			// 3.1 저장 성공
			// 기본경로
			fn_setCommonPath(attrVO);
			model.addAttribute("message",
					MessageUtil.getAlertAddWindow(isAjax,
							rbsMessageSource.getMessage("message.delete"),
							"fn_procAction(\"" + toUrl + "\", "
									+ StringUtil.getInt(
											request.getParameter("mdl"))
									+ ");")
							+ "#tab3");
			return RbsProperties
					.getProperty("Globals.message" + ajaxPName + ".path");
		}
		// 3.2 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax,
				rbsMessageSource.getMessage("message.no.delete")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}

	/**
	 * 목록조회
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "LST")
	@RequestMapping(value = "/evalList.do")
	public String evalList(@ModuleAttr ModuleAttrVO attrVO,
			@ParamMap ParamForm parameterMap,
			@RequestParam Map<String, Object> map, HttpServletRequest request,
			ModelMap model, @PathVariable("siteId") String siteId)
			throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		model.addAttribute("mId", parameterMap.get("mId")); // 목록

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		// 1. 페이지정보 setting
		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		int usePaging = JSONObjectUtil.getInt(settingInfo, "use_paging");
		if (usePaging == 1) {
			int listUnit = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_count", crtMenu, settingInfo,
					propertiesService.getInt("DEFAULT_LIST_UNIT")); // 페이지당 목록 수
			int listMaxUnit = listUnit
					* propertiesService.getInt("DEFAULT_LIST_MAX_CNT"); // 최대
																		// 페이지당
																		// 목록 수
			int listUnitStep = listUnit; // 페이지당 목록 수 증가값

			int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
			if (pageUnit == 0)
				pageUnit = listUnit;// JSONObjectUtil.getInt(settingInfo,
									// "dset_list_count",
									// propertiesService.getInt("PAGE_UNIT"));
									// // 페이지당 목록 수
			int pageSize = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_block", crtMenu, settingInfo,
					propertiesService.getInt("PAGE_SIZE"));

			String pageName = JSONObjectUtil.getString(settingInfo,
					"page_name");
			int page = StringUtil.getInt(request.getParameter(pageName), 1); // 현재
																				// 페이지
																				// index

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
		String listSearchId = "list_search"; // 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo,
				listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil
				.getItemInfoSearchList(listSearch, queryString);
		if (itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}

		// 탭코드
		String cateTabItemId = JSONObjectUtil.getString(settingInfo,
				"dset_cate_tab_id");
		String cateTabColumnId = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"column_id");
		String cateTabMasterCode = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"master_code");
		int cateTabObjectType = JSONObjectUtil.getInt(settingInfo,
				"dset_cate_tab_object_type");
		// 검색 코드
		HashMap<String, Object> searchOptnHashMap = CodeHelper
				.getItemOptnSearchHashMap(listSearch, cateTabItemId,
						cateTabColumnId, cateTabObjectType, cateTabMasterCode);
		// 목록 코드
		HashMap<String, Object> optnHashMap = getOptionHashMap(submitType,
				itemInfo, searchOptnHashMap);// CodeHelper.getItemOptnHashMap(items,
												// itemOrder,
												// searchOptnHashMap);
		// 탭 검색 조건
		List<DTForm> cateTabSearchList = ModuleUtil.getCateTabSearchList(
				settingInfo, items, queryString, optnHashMap);// fn_getCateTabSearchList(queryString,
																// settingInfo,
																// items,
																// optnHashMap);
		if (cateTabSearchList != null) {
			searchList.addAll(cateTabSearchList);
		}

		if (siteId.equals("dct")) {
			param.put("brffcCd", loginVO.getRegiCode());
			param.put("insttIdx", loginVO.getInsttIdx());
		}

		param.put("searchList", searchList);
		param.put("bplNo", loginVO.getBplNo());
		param.put("cnslIdx", parameterMap.getInt("cnslIdx"));

		// 2.2 목록수
		totalCount = consultingService.getEvlCount(fnIdx, param);
		paginationInfo.setTotalRecordCount(totalCount);

		if (totalCount > 0) {
			if (usePaging == 1) {
				param.put("firstIndex", paginationInfo.getFirstRecordIndex());
				param.put("lastIndex", paginationInfo.getLastRecordIndex());
				param.put("recordCountPerPage",
						paginationInfo.getRecordCountPerPage());
			}

			// 정렬컬럼 setting
			param.put("dsetOrderField",
					JSONObjectUtil.getString(settingInfo, "dset_order_field"));
			param.put("regiId", loginVO.getMemberId());
			param.put("regiIdx", loginVO.getMemberIdx());

			// 2.3 목록
			list = consultingService.getEvlList(fnIdx, param);
		}

		if (map.get("cnslIdx") != null) {
			Cnsl dto = consultingService.selectByCnslIdx(
					Integer.parseInt((String) map.get("cnslIdx")));
			model.addAttribute("cnsl", dto);
		}

		// 3. 속성 setting
		model.addAttribute("map", map);
		model.addAttribute("param", param);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("totalCount", totalCount); // 페이징정보
		model.addAttribute("list", list); // 목록
		model.addAttribute("searchOptnHashMap", searchOptnHashMap); // 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("loginVO", loginVO);

		// 4. 기본경로
		fn_setCommonPath(attrVO);

		return getViewPath("/evalList");
	}

	/**
	 * 목록조회
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "LST")
	@RequestMapping(value = "/evalExcel.do")
	public String evalExcel(@ModuleAttr ModuleAttrVO attrVO,
			@ParamMap ParamForm parameterMap,
			@RequestParam Map<String, Object> map, HttpServletRequest request,
			ModelMap model, @PathVariable("siteId") String siteId)
			throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		model.addAttribute("mId", parameterMap.get("mId")); // 목록

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		// 1. 페이지정보 setting
		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		int usePaging = JSONObjectUtil.getInt(settingInfo, "use_paging");
		if (usePaging == 1) {
			int listUnit = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_count", crtMenu, settingInfo,
					propertiesService.getInt("DEFAULT_LIST_UNIT")); // 페이지당 목록 수
			int listMaxUnit = listUnit
					* propertiesService.getInt("DEFAULT_LIST_MAX_CNT"); // 최대
																		// 페이지당
																		// 목록 수
			int listUnitStep = listUnit; // 페이지당 목록 수 증가값

			int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
			if (pageUnit == 0)
				pageUnit = listUnit;// JSONObjectUtil.getInt(settingInfo,
									// "dset_list_count",
									// propertiesService.getInt("PAGE_UNIT"));
									// // 페이지당 목록 수
			int pageSize = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_block", crtMenu, settingInfo,
					propertiesService.getInt("PAGE_SIZE"));

			String pageName = JSONObjectUtil.getString(settingInfo,
					"page_name");
			int page = StringUtil.getInt(request.getParameter(pageName), 1); // 현재
																				// 페이지
																				// index

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
		String listSearchId = "list_search"; // 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo,
				listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil
				.getItemInfoSearchList(listSearch, queryString);
		if (itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}

		// 탭코드
		String cateTabItemId = JSONObjectUtil.getString(settingInfo,
				"dset_cate_tab_id");
		String cateTabColumnId = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"column_id");
		String cateTabMasterCode = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"master_code");
		int cateTabObjectType = JSONObjectUtil.getInt(settingInfo,
				"dset_cate_tab_object_type");
		// 검색 코드
		HashMap<String, Object> searchOptnHashMap = CodeHelper
				.getItemOptnSearchHashMap(listSearch, cateTabItemId,
						cateTabColumnId, cateTabObjectType, cateTabMasterCode);
		// 목록 코드
		HashMap<String, Object> optnHashMap = getOptionHashMap(submitType,
				itemInfo, searchOptnHashMap);// CodeHelper.getItemOptnHashMap(items,
												// itemOrder,
												// searchOptnHashMap);
		// 탭 검색 조건
		List<DTForm> cateTabSearchList = ModuleUtil.getCateTabSearchList(
				settingInfo, items, queryString, optnHashMap);// fn_getCateTabSearchList(queryString,
																// settingInfo,
																// items,
																// optnHashMap);
		if (cateTabSearchList != null) {
			searchList.addAll(cateTabSearchList);
		}

		param.put("brffcCd", loginVO.getRegiCode());
		if (parameterMap.get("insttIdx") != null) {
			param.put("insttIdx", parameterMap.get("insttIdx"));
		}

		param.put("searchList", searchList);
		param.put("bplNo", loginVO.getBplNo());
		param.put("cnslIdx", parameterMap.getInt("cnslIdx"));

		// 2.2 목록수
		totalCount = consultingService.getEvlCount(fnIdx, param);
		paginationInfo.setTotalRecordCount(totalCount);

		if (totalCount > 0) {
			if (usePaging == 1) {
				param.put("firstIndex", paginationInfo.getFirstRecordIndex());
				param.put("lastIndex", paginationInfo.getLastRecordIndex());
				param.put("recordCountPerPage",
						paginationInfo.getRecordCountPerPage());
			}

			// 정렬컬럼 setting
			param.put("dsetOrderField",
					JSONObjectUtil.getString(settingInfo, "dset_order_field"));
			param.put("regiId", loginVO.getMemberId());
			param.put("regiIdx", loginVO.getMemberIdx());

			// 2.3 목록
			param.put("useExcel", "useExcel");
			list = consultingService.getEvlList(fnIdx, param);
		}

		if (map.get("cnslIdx") != null) {
			Cnsl dto = consultingService.selectByCnslIdx(
					Integer.parseInt((String) map.get("cnslIdx")));
			model.addAttribute("cnsl", dto);
		}

		// 3. 속성 setting
		model.addAttribute("map", map);
		model.addAttribute("param", param);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("totalCount", totalCount); // 페이징정보
		model.addAttribute("list", list); // 목록
		model.addAttribute("searchOptnHashMap", searchOptnHashMap); // 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("loginVO", loginVO);

		// 4. 기본경로
		fn_setCommonPath(attrVO);

		return getViewPath("/evalExcel");
	}

	/**
	 * 인력풀 회의 추가
	 */
	@Auth(role = Role.C)
	@RequestMapping(method = RequestMethod.POST, value = "/insertExpert.do")
	public ModelAndView insertExpert(@ModuleAttr ModuleAttrVO attrVO,
			@RequestBody String json,
			@RequestParam Map<String, Object> paramMap,
			@ParamMap ParamForm parameterMap,
			@PathVariable("siteId") String siteId, HttpServletRequest request)
			throws Exception {
		log.info("json {}", json);
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		int result = 0;
		Cnsl cnsl = consultingService.selectByCnslIdx(
				Integer.parseInt(paramMap.get("cnslIdx").toString()));
		String title = "";
		String cnslType = "";

		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		int fnIdx = attrVO.getFnIdx();
		String submitType = "write"; // 항목 order명
		String inputFlag = "write"; // 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo,
				submitType + "proc_order");

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

		if (cnsl.getCnslType().equals("1") || cnsl.getCnslType().equals("2")
				|| cnsl.getCnslType().equals("3")) {
			cnslType = "성과";
		} else if (cnsl.getCnslType().equals("4")
				|| cnsl.getCnslType().equals("5")
				|| cnsl.getCnslType().equals("6")) {
			cnslType = "심화";
		}

		// 년도
		String YYYY = "";
		YYYY = ServletRequestUtils.getStringParameter(request, "yyyy", "");

		if (YYYY.equals("")) {
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
		paramMap.put("indSel", "7");
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

		if (cnslType == "성과") {
			expert = consultingService.insertExpert(paramMap);
		}

		paramMap.put("fnIdx", 7);
		List<HashMap<String, Object>> dt = consultingService
				.getDiaryView(paramMap);
		if (dt != null) {
			int dtSize = dt.size();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
			for (int i = 0; i < dtSize; i++) {
				String exTime1 = sdf.format(dt.get(i).get("MTG_START_DT"));
				String exTime2 = sdf.format(dt.get(i).get("MTG_END_DT"));
				if (cnsl.getCmptncBrffcPicTelno() == null) {
					chrgrTel = getInstt.get("TELNO").toString().replaceAll("-",
							"");
				} else {
					chrgrTel = cnsl.getCmptncBrffcPicTelno().replaceAll("-",
							"");
				}
				paramMap.put("chrgrTel", chrgrTel);
				paramMap.put("firstId", loginVO.getMemberId());
				paramMap.put("receiptSdate", exTime1.substring(0, 8));
				paramMap.put("receiptEdate", exTime2.substring(0, 8));
				paramMap.put("exTime1", exTime1.substring(8, 12));
				paramMap.put("exTime2", exTime2.substring(8, 12));
				if (cnslType == "성과") {
					busin = consultingService.insertBusin(paramMap);
				}

				String hrpId = cnsl.getCnslTeam().getMembers().get(0)
						.getMemberIdx();
				if (hrpId != null) {
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
					if (cnslType == "성과") {
						entrst = consultingService.insertEntrst(paramMap);
					}
				}
			}
		}

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("인력풀 회의 추가")
				.setBody(result).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);

		return mav;
	}

	/**
	 * 수정 : 관리권한 없는 경우 - 자신이 등록한 글만 수정
	 * 
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@ModuleAuth(name = {"WRT"})
	@RequestMapping(value = "/evalInput.do", params = "mode")
	public String evalInput(@RequestParam(value = "mode") String mode,
			@ModuleAttr ModuleAttrVO attrVO,
			@RequestParam Map<String, Object> map, HttpServletRequest request,
			ModelMap model, @PathVariable("siteId") String siteId)
			throws Exception {
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if (siteId.equals("dct")) {
			if (loginVO != null) {
				model.addAttribute("loginVO", loginVO);
			}
		} 

		// 과정개발정보 받아오기

		// int getDevIdx = consultingService.getDevelope(loginVO.getBplNo());
		// model.addAttribute("getDev", getDevIdx);

		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(
				request.getParameter(
						JSONObjectUtil.getString(settingInfo, "diary_name")),
				0);

		// 1.2 수정모드 여부 검사
		boolean isModify = (StringUtil.isEquals(mode, "m")) ? true : false;
		if (isModify == true) {
			model.addAttribute("mode", "&mode=m");
		}

		if (keyIdx <= 0 || !isModify) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}

		// 2. DB
		String idxColumnName = JSONObjectUtil.getString(settingInfo,
				"diary_column"); // key column명

		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();

		// 2.1 수정권한 체크
		Boolean isMngAuth = isMngProcAuth(settingInfo, fnIdx, keyIdx);
		if (!isMngAuth) {
			// 수정권한 없는 경우
			model.addAttribute("message", MessageUtil
					.getAlert(rbsMessageSource.getMessage("message.no.auth")));
			return RbsProperties.getProperty("Globals.fail.path");
		}

		searchList.add(new DTForm("A." + idxColumnName, keyIdx));
		param.put("searchList", searchList);

		// 2.2 상세정보
		dt = consultingService.getModify(fnIdx, param);

		if (dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(
					rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}

		// 3 속성 setting
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject crtMenu = attrVO.getCrtMenu();

		// 3.1 항목설정
		String submitType = "modify";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo,
				submitType + "_order");

		// 3.2 속성 setting
		model.addAttribute("dt", dt);
		model.addAttribute("map", map);
		model.addAttribute("multiDataHashMap", consultingService
				.getMultiHashMap(fnIdx, keyIdx, settingInfo, items, itemOrder)); // multi
																					// file
																					// 목록
		model.addAttribute("multiFileHashMap",
				consultingService.getMultiFileHashMap(fnIdx, keyIdx,
						settingInfo, items, itemOrder)); // multi file 목록

		model.addAttribute("optnHashMap",
				CodeHelper.getItemOptnHashMap(items, itemOrder)); // 항목코드
		model.addAttribute("submitType", submitType); // 페이지구분

		// 기업 기본정보 받아오기
		if (map.get("cnslIdx") != null) {
			Cnsl dto = consultingService.selectByCnslIdx(
					Integer.parseInt((String) map.get("cnslIdx")));
			model.addAttribute("cnsl", dto);
		}

		// 4. 기본경로
		fn_setCommonPath(attrVO);

		// 5. form action
		model.addAttribute("URL_SUBMITPROC",
				request.getAttribute("URL_IDX_MODIFYPROC"));

		return getViewPath("/evalInput");
	}

	/**
	 * 등록
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(value = "/evalInput.do")
	public String evalInput(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, @RequestParam Map<String, Object> map,
			ModelMap model, @PathVariable("siteId") String siteId)
			throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();

		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		String submitType = "write";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo,
				submitType + "_order");
		int totalCount = 0;

		// 3.2 속성 setting
		model.addAttribute("submitType", submitType); // 페이지구분

		// 기업 기본정보 받아오기
		if (map.get("cnslIdx") != null) {
			Cnsl dto = consultingService.selectByCnslIdx(
					Integer.parseInt((String) map.get("cnslIdx")));
			model.addAttribute("cnsl", dto);
		}

		// 2. 기본경로
		fn_setCommonPath(attrVO);
		return getViewPath("/evalInput");
	}

	/**
	 * 인력풀 회의 추가
	 */
	@Auth(role = Role.C)
	@RequestMapping(method = RequestMethod.POST, value = "/updateEntrst.do")
	public String updateEntrst(@RequestBody String json,
			@RequestParam Map<String, Object> paramMap, ModelMap model,
			@ModuleAttr ModuleAttrVO attrVO,
			@PathVariable("siteId") String siteId, HttpServletRequest request)
			throws Exception {
		log.info("json {}", json);
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();

		paramMap.put("lastModiIp", request.getRemoteAddr());
		paramMap.put("lastModiIdx", loginVO.getMemberIdx());
		paramMap.put("lastModiId", loginVO.getMemberId());
		paramMap.put("lastModiName", loginVO.getMemberName());

		int entrst = consultingService.updateEntrst(paramMap);
		int cnslIdx = Integer.parseInt(paramMap.get("cnslIdx").toString());
		String bplNo = paramMap.get("bplNo").toString();
		String toUrl = "";
		if (siteId.equals("dct")) {
			toUrl = "/dct/consulting/evalList.do?mId=127&cnslIdx=" + cnslIdx
					+ "&bplNo=" + bplNo;
		} else if (siteId.equals("web")) {
			toUrl = "/hrddoctor/web/consulting/evalList.do?mId=94&cnslIdx=" + cnslIdx
					+ "&bplNo=" + bplNo;
		}
		// 4. DB
		if (entrst > 0) {
			int ent4urst = consultingService.update4uEntrst(paramMap);
			fn_setCommonPath(attrVO);
			model.addAttribute("message",
					MessageUtil.getAlertAddWindow(isAjax,
							rbsMessageSource.getMessage("message.insert"),
							"fn_procAction(\"" + toUrl + "\", "
									+ StringUtil.getInt(
											request.getParameter("mdl"))
									+ ");")
							+ "#tab3");
			return RbsProperties
					.getProperty("Globals.message" + ajaxPName + ".path");
		} else if (entrst == -1) {
			// 4.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if (!StringUtil.isEmpty(fileFailView))
				return fileFailView;
		}

		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax,
				rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}

	/**
	 * 목록조회
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "LST")
	@RequestMapping(value = "/reportList.do")
	public String reportList(@ModuleAttr ModuleAttrVO attrVO,
			@ParamMap ParamForm parameterMap,
			@RequestParam Map<String, Object> param, HttpServletRequest request,
			ModelMap model, @PathVariable("siteId") String siteId)
			throws Exception {
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

		if (siteId.equals("dct")) {
			// 지부지사 리스트
			List<Object> insttList = insttService.getList(null);
			model.addAttribute("insttList", insttList);
		} 

		// 1. 페이지정보 setting
		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		int usePaging = JSONObjectUtil.getInt(settingInfo, "use_paging");
		if (usePaging == 1) {
			int listUnit = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_count", crtMenu, settingInfo,
					propertiesService.getInt("DEFAULT_LIST_UNIT")); // 페이지당 목록 수
			int listMaxUnit = listUnit
					* propertiesService.getInt("DEFAULT_LIST_MAX_CNT"); // 최대
																		// 페이지당
																		// 목록 수
			int listUnitStep = listUnit; // 페이지당 목록 수 증가값

			int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
			if (pageUnit == 0)
				pageUnit = listUnit;// JSONObjectUtil.getInt(settingInfo,
									// "dset_list_count",
									// propertiesService.getInt("PAGE_UNIT"));
									// // 페이지당 목록 수
			int pageSize = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_block", crtMenu, settingInfo,
					propertiesService.getInt("PAGE_SIZE"));

			String pageName = JSONObjectUtil.getString(settingInfo,
					"page_name");
			int page = StringUtil.getInt(request.getParameter(pageName), 1); // 현재
																				// 페이지
																				// index

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
		String listSearchId = "list_search"; // 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo,
				listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil
				.getItemInfoSearchList(listSearch, queryString);
		if (itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}

		// 탭코드
		String cateTabItemId = JSONObjectUtil.getString(settingInfo,
				"dset_cate_tab_id");
		String cateTabColumnId = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"column_id");
		String cateTabMasterCode = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"master_code");
		int cateTabObjectType = JSONObjectUtil.getInt(settingInfo,
				"dset_cate_tab_object_type");
		// 검색 코드
		HashMap<String, Object> searchOptnHashMap = CodeHelper
				.getItemOptnSearchHashMap(listSearch, cateTabItemId,
						cateTabColumnId, cateTabObjectType, cateTabMasterCode);
		// 목록 코드
		HashMap<String, Object> optnHashMap = getOptionHashMap(submitType,
				itemInfo, searchOptnHashMap);// CodeHelper.getItemOptnHashMap(items,
												// itemOrder,
												// searchOptnHashMap);
		// 탭 검색 조건
		List<DTForm> cateTabSearchList = ModuleUtil.getCateTabSearchList(
				settingInfo, items, queryString, optnHashMap);// fn_getCateTabSearchList(queryString,
																// settingInfo,
																// items,
																// optnHashMap);
		if (cateTabSearchList != null) {
			searchList.addAll(cateTabSearchList);
		}

		if (siteId.equals("dct")) {
			param.put("brffcCd", loginVO.getRegiCode());

			if (parameterMap.get("trCorpNm") != null) {
				param.put("trCorpNm", parameterMap.get("trCorpNm"));
			}

			if (parameterMap.get("schBplNo") != null) {
				param.put("schBplNo", parameterMap.get("schBplNo"));
			}

			if (parameterMap.get("insttIdx") != null) {
				param.put("insttIdx", parameterMap.get("insttIdx"));
			}
			param.put("dct", "dct");
		}

		int cnslIdx = parameterMap.getInt("cnslIdx");
		param.put("searchList", searchList);
		param.put("bplNo", loginVO.getBplNo());
		param.put("cnslIdx", cnslIdx);

		Cnsl cnsl = consultingService.selectByCnslIdx(cnslIdx);
		param.put("orderColumn", "REPRT_IDX");
		confmStatus = reportService.getList("HRD_CNSL_REPORT_CONFM", param);
		model.addAttribute("confmStatus", confmStatus);

		if (cnsl.getCnslType().equals("1") || cnsl.getCnslType().equals("2")) {
			// 2.2 목록수
			totalCount = reportService.getTotalCount(fnIdx, param);
			paginationInfo.setTotalRecordCount(totalCount);

			if (totalCount > 0) {
				if (usePaging == 1) {
					param.put("firstIndex",
							paginationInfo.getFirstRecordIndex());
					param.put("lastIndex", paginationInfo.getLastRecordIndex());
					param.put("recordCountPerPage",
							paginationInfo.getRecordCountPerPage());
				}

				// 정렬컬럼 setting
				param.put("dsetOrderField", JSONObjectUtil
						.getString(settingInfo, "dset_order_field"));
				param.put("regiId", loginVO.getMemberId());
				param.put("regiIdx", loginVO.getMemberIdx());

				// 2.3 목록
				list = reportService.getList("HRD_CNSL_REPORT_TP", param);
			}

			model.addAttribute("param", param);
			model.addAttribute("cnsl", cnsl);
			model.addAttribute("paginationInfo", paginationInfo);
			model.addAttribute("ReporttotalCount", totalCount); // 페이징정보
			model.addAttribute("listReport", list);
			model.addAttribute("searchOptnHashMap", searchOptnHashMap); // 항목코드
			model.addAttribute("optnHashMap", optnHashMap);
			model.addAttribute("loginVO", loginVO);

		} else {
			// 2.2 목록수
			list = reportService.getListByAnotherTypeCnsl(cnslIdx);
			model.addAttribute("listReport", list);

		}

		// 4. 기본경로
		fn_setCommonPath(attrVO);

		return getViewPath("/list");
	}

	/**
	 * 주치의 컨설팅 신청
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(value = "/applyInput.do")
	public String applyInput(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, @RequestParam Map<String, Object> param,
			ModelMap model, @PathVariable("siteId") String siteId)
			throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		String submitType = "write";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo,
				submitType + "_order");
		int totalCount = 0;

		// 지부지사 리스트
		List<Object> insttList = insttService.getList(null);
		model.addAttribute("insttList", insttList);

		// 3.2 속성 setting
		model.addAttribute("submitType", submitType); // 페이지구분
		model.addAttribute("param", param);
		// 2. 기본경로
		fn_setCommonPath(attrVO);
		return getViewPath("/applyInput");
	}

	/**
	 * 주치의 컨설팅 신청 - 사업장 관리번호 조회
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.POST, value = "/findBizData.do")
	public ModelAndView findBizData(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, @RequestParam Map<String, Object> param,
			ModelMap model, @PathVariable("siteId") String siteId)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		param.put("bplNo", param.get("bplNo"));
		Map<String, Object> resultData = consultingService.findBizData(param);
		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("사업장 관리번호 조회")
				.setBody(resultData).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);

		return mav;
	}

	/**
	 * 등록처리
	 * 
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name = "WRT")
	@RequestMapping(method = RequestMethod.POST, value = "/applyProc.do")
	public String applyProc(@ParamMap ParamForm parameterMap,
			@PathVariable("siteId") String siteId,
			@RequestParam Map<String, Object> paramMap,
			@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		int fnIdx = attrVO.getFnIdx();

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write"; // 항목 order명
		String inputFlag = "write"; // 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo,
				submitType + "proc_order");

		parameterMap.put("_itemInputTypeFlag", submitType);
		parameterMap.put("siteId", siteId);
		
		String toUrl = "/hrddoctor/web/main/main.do?mId=1";
		int applyInsert = consultingService.applyInsert(uploadModulePath, fnIdx,
				request.getRemoteAddr(), parameterMap, settingInfo, items,
				itemOrder);
		if (applyInsert > 0) {
			// 2.1 저장 성공
			// 기본경로
			fn_setCommonPath(attrVO);
			String inputNpname = fn_dsetInputNpname(settingInfo);
			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl"))+ ");"));
			return RbsProperties
					.getProperty("Globals.message" + ajaxPName + ".path");
		} else if (applyInsert == -1) {
			// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if (!StringUtil.isEmpty(fileFailView))
				return fileFailView;
		}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax,
				rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	// 상태 업데이트
	@Auth(role = Role.U)
	@RequestMapping(method = RequestMethod.POST, value = "/cnslApplyUpdate.do")
	public ModelAndView cnslApplyUpdate(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		String applyIdx = request.getParameter("applyIdx");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applyIdx", applyIdx);
		param.put("lastModiIp", request.getRemoteAddr());
		int result = consultingService.applyUpdate(param);
		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("상태완료 업데이트").setBody(result).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;
	}
	
	/**
	 * 목록조회
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "LST")
	@RequestMapping(value = "/cnslApplyList.do")
	public String cnslApplyList(@ModuleAttr ModuleAttrVO attrVO,
			@ParamMap ParamForm parameterMap,
			@RequestParam Map<String, Object> map, HttpServletRequest request,
			ModelMap model, @PathVariable("siteId") String siteId)
			throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		model.addAttribute("mId", parameterMap.get("mId")); // 목록

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();

		// 지부지사 리스트
		List<Object> insttList = insttService.getList(null);
		model.addAttribute("insttList", insttList);

		// 1. 페이지정보 setting
		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		int usePaging = JSONObjectUtil.getInt(settingInfo, "use_paging");
		if (usePaging == 1) {
			int listUnit = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_count", crtMenu, settingInfo,
					propertiesService.getInt("DEFAULT_LIST_UNIT")); // 페이지당 목록 수
			int listMaxUnit = listUnit
					* propertiesService.getInt("DEFAULT_LIST_MAX_CNT"); // 최대
																		// 페이지당
																		// 목록 수
			int listUnitStep = listUnit; // 페이지당 목록 수 증가값

			int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
			if (pageUnit == 0)
				pageUnit = listUnit;// JSONObjectUtil.getInt(settingInfo,
									// "dset_list_count",
									// propertiesService.getInt("PAGE_UNIT"));
									// // 페이지당 목록 수
			int pageSize = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_block", crtMenu, settingInfo,
					propertiesService.getInt("PAGE_SIZE"));

			String pageName = JSONObjectUtil.getString(settingInfo,
					"page_name");
			int page = StringUtil.getInt(request.getParameter(pageName), 1); // 현재
																				// 페이지
																				// index

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
		String listSearchId = "list_search"; // 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo,
				listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil
				.getItemInfoSearchList(listSearch, queryString);
		if (itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}

		// 탭코드
		String cateTabItemId = JSONObjectUtil.getString(settingInfo,
				"dset_cate_tab_id");
		String cateTabColumnId = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"column_id");
		String cateTabMasterCode = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"master_code");
		int cateTabObjectType = JSONObjectUtil.getInt(settingInfo,
				"dset_cate_tab_object_type");
		// 검색 코드
		HashMap<String, Object> searchOptnHashMap = CodeHelper
				.getItemOptnSearchHashMap(listSearch, cateTabItemId,
						cateTabColumnId, cateTabObjectType, cateTabMasterCode);
		// 목록 코드
		HashMap<String, Object> optnHashMap = getOptionHashMap(submitType,
				itemInfo, searchOptnHashMap);// CodeHelper.getItemOptnHashMap(items,
												// itemOrder,
												// searchOptnHashMap);
		// 탭 검색 조건
		List<DTForm> cateTabSearchList = ModuleUtil.getCateTabSearchList(
				settingInfo, items, queryString, optnHashMap);// fn_getCateTabSearchList(queryString,
																// settingInfo,
																// items,
																// optnHashMap);
		if (cateTabSearchList != null) {
			searchList.addAll(cateTabSearchList);
		}

		if (siteId.equals("dct")) {
			param.put("brffcCd", loginVO.getRegiCode());
			param.put("insttIdx", loginVO.getInsttIdx());
		}

		if (parameterMap.get("trCorpNm") != null) {
			param.put("trCorpNm", parameterMap.get("trCorpNm"));
		}

		if (parameterMap.get("schBplNo") != null) {
			param.put("schBplNo", parameterMap.get("schBplNo"));
		}

		if (parameterMap.get("getInsttIdx") != null) {
			param.put("insttIdx", parameterMap.get("getInsttIdx"));
		}
		
		if (parameterMap.get("getStatus") != null) {
			param.put("getStatus", parameterMap.get("getStatus"));
		}

		param.put("searchList", searchList);

		// 2.2 목록수
		totalCount = consultingService.getCnslApplyCnt(fnIdx, param);
		paginationInfo.setTotalRecordCount(totalCount);

		if (totalCount > 0) {
			if (usePaging == 1) {
				param.put("firstIndex", paginationInfo.getFirstRecordIndex());
				param.put("lastIndex", paginationInfo.getLastRecordIndex());
				param.put("recordCountPerPage",
						paginationInfo.getRecordCountPerPage());
			}

			// 정렬컬럼 setting
			param.put("dsetOrderField",
					JSONObjectUtil.getString(settingInfo, "dset_order_field"));
			param.put("regiId", loginVO.getMemberId());
			param.put("regiIdx", loginVO.getMemberIdx());

			// 2.3 목록
			list = consultingService.getCnslApplyList(fnIdx, param);
		}

		// 3. 속성 setting
		model.addAttribute("map", map);
		model.addAttribute("param", param);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("totalCount", totalCount); // 페이징정보
		model.addAttribute("list", list); // 목록
		model.addAttribute("searchOptnHashMap", searchOptnHashMap); // 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("loginVO", loginVO);

		// 4. 기본경로
		fn_setCommonPath(attrVO);

		return getViewPath("/cnslApplyList");
	}

	/**
	 * 목록조회
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "LST")
	@RequestMapping(value = "/cnslApplyExcel.do")
	public String cnslApplyExcel(@ModuleAttr ModuleAttrVO attrVO,
			@ParamMap ParamForm parameterMap,
			@RequestParam Map<String, Object> param, HttpServletRequest request,
			ModelMap model, @PathVariable("siteId") String siteId)
			throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		model.addAttribute("mId", parameterMap.get("mId")); // 목록

		// 로그인한 회원의 정보 가져오기
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		HashMap<String, Object> optnHashMap = CommonFunction
				.fn_getOptnHashMap(attrVO, "list");

		// 지부지사 리스트
		List<Object> insttList = insttService.getList(null);
		model.addAttribute("insttList", insttList);

		// 2. DB
		int totalCount = 0;
		List<?> list = null;
		List<DTForm> searchList = new ArrayList<DTForm>();

		// 2.1 검색조건

		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");

		// 항목설정으로 검색조건 setting
		String listSearchId = "list_search"; // 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo,
				listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil
				.getItemInfoSearchList(listSearch, queryString);
		if (itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}

		// 탭코드
		String cateTabItemId = JSONObjectUtil.getString(settingInfo,
				"dset_cate_tab_id");
		String cateTabColumnId = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"column_id");
		String cateTabMasterCode = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"master_code");
		int cateTabObjectType = JSONObjectUtil.getInt(settingInfo,
				"dset_cate_tab_object_type");
		// 검색 코드
		HashMap<String, Object> searchOptnHashMap = CodeHelper
				.getItemOptnSearchHashMap(listSearch, cateTabItemId,
						cateTabColumnId, cateTabObjectType, cateTabMasterCode);
		// 탭 검색 조건
		List<DTForm> cateTabSearchList = ModuleUtil.getCateTabSearchList(
				settingInfo, items, queryString, optnHashMap);// fn_getCateTabSearchList(queryString,
																// settingInfo,
																// items,
																// optnHashMap);
		if (cateTabSearchList != null) {
			searchList.addAll(cateTabSearchList);
		}

		param.put("brffcCd", loginVO.getRegiCode());
		param.put("insttIdx", loginVO.getInsttIdx());

		if (parameterMap.get("trCorpNm") != null) {
			param.put("trCorpNm", parameterMap.get("trCorpNm"));
		}

		if (parameterMap.get("schBplNo") != null) {
			param.put("schBplNo", parameterMap.get("schBplNo"));
		}

		if (parameterMap.get("getInsttIdx") != null) {
			param.put("insttIdx", parameterMap.get("getInsttIdx"));
		}
		
		if (parameterMap.get("getStatus") != null) {
			param.put("getStatus", parameterMap.get("getStatus"));
		}

		param.put("searchList", searchList);

		// 2.3 목록
		param.put("useExcel", "useExcel");
		list = consultingService.getCnslApplyList(fnIdx, param);

		// 3. 속성 setting
		model.addAttribute("param", param);
		model.addAttribute("list", list); // 목록
		model.addAttribute("optnHashMap", optnHashMap);

		// 4. 기본경로
		fn_setCommonPath(attrVO);

		return getViewPath("/cnslApplyExcel");
	}
	
	/**
	 * 컨설팅 관리
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "LST")
	@RequestMapping(value = "/cnslExcel.do")
	public String cnslExcel(@ModuleAttr ModuleAttrVO attrVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();

		boolean isAdmMode = attrVO.isAdmMode();
		int fnIdx = attrVO.getFnIdx();

		log.info("fnIdx {}", fnIdx);

		String siteId = attrVO.getSiteId();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if (loginVO == null) {
			request.setAttribute("msg", "로그인이 필요 합니다.");
			request.setAttribute("url", "/" + siteId + "/login/login.do?mId=3");
			return getViewPath("/alert");
		}

		String memberIdx = loginVO.getMemberIdx();
		int userTypeIdx = loginVO.getUsertypeIdx();
		String bplNo = loginVO.getBplNo();
		String searchMemberIdx = request.getParameter("memberIdx");
		log.info("searchMemberIdx", searchMemberIdx);
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();

		// 1. 페이지정보 setting
		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		int usePaging = JSONObjectUtil.getInt(settingInfo, "use_paging");
		if (usePaging == 1) {
			int listUnit = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_count", crtMenu, settingInfo,
					propertiesService.getInt("DEFAULT_LIST_UNIT")); // 페이지당 목록 수
			int listMaxUnit = listUnit
					* propertiesService.getInt("DEFAULT_LIST_MAX_CNT"); // 최대
																		// 페이지당
																		// 목록 수
			int listUnitStep = listUnit; // 페이지당 목록 수 증가값

			int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
			if (pageUnit == 0)
				pageUnit = listUnit;// JSONObjectUtil.getInt(settingInfo,
									// "dset_list_count",
									// propertiesService.getInt("PAGE_UNIT"));
									// // 페이지당 목록 수
			int pageSize = ModuleUtil.getSettingValue(isAdmMode,
					"dset_list_block", crtMenu, settingInfo,
					propertiesService.getInt("PAGE_SIZE"));

			String pageName = JSONObjectUtil.getString(settingInfo,
					"page_name");
			int page = StringUtil.getInt(request.getParameter(pageName), 1); // 현재
																				// 페이지
																				// index

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

		List<HashMap<String, String>> insttList = consultingService
				.getInsttList();

		// 2.1 검색조건

		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");

		// 항목설정으로 검색조건 setting
		String listSearchId = "list_search"; // 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo,
				listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil
				.getItemInfoSearchList(listSearch, queryString);
		if (itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}

		// 탭코드
		String cateTabItemId = JSONObjectUtil.getString(settingInfo,
				"dset_cate_tab_id");
		String cateTabColumnId = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"column_id");
		String cateTabMasterCode = JSONObjectUtil.getString(
				JSONObjectUtil.getJSONObject(items, cateTabItemId),
				"master_code");
		int cateTabObjectType = JSONObjectUtil.getInt(settingInfo,
				"dset_cate_tab_object_type");
		// 검색 코드
		HashMap<String, Object> searchOptnHashMap = CodeHelper
				.getItemOptnSearchHashMap(listSearch, cateTabItemId,
						cateTabColumnId, cateTabObjectType, cateTabMasterCode);
		// 목록 코드
		HashMap<String, Object> optnHashMap = getOptionHashMap(submitType,
				itemInfo, searchOptnHashMap);// CodeHelper.getItemOptnHashMap(items,
												// itemOrder,
												// searchOptnHashMap);
		// 탭 검색 조건
		List<DTForm> cateTabSearchList = ModuleUtil.getCateTabSearchList(
				settingInfo, items, queryString, optnHashMap);// fn_getCateTabSearchList(queryString,
																// settingInfo,
																// items,
																// optnHashMap);
		if (cateTabSearchList != null) {
			searchList.addAll(cateTabSearchList);
		}

		if (request.getParameter("is_bplNo") != null
				&& request.getParameter("is_bplNo") != "") {
			String searchBplNo = request.getParameter("is_bplNo");

			param.put("searchBplNo", searchBplNo);
		}

		if (request.getParameter("is_confmStatus") != null
				&& request.getParameter("is_confmStatus") != "") {
			String searchConfmStatus = request.getParameter("is_confmStatus");
			
			param.put("searchConfmStatus", searchConfmStatus);
			
			log.info("searchConfmStatus", searchConfmStatus);
		}


		
		param.put("searchList", searchList);
		param.put("siteId", siteId);
		param.put("memberIdx", memberIdx);
		param.put("userTypeIdx", userTypeIdx);
		if (bplNo != null) {
			param.put("bplNo", bplNo);
		}

		if (searchMemberIdx != null) {
			param.put("searchMemberIdx", searchMemberIdx);
		}

		log.info("param {}", param);

		// 2.2 목록수
		totalCount = consultingService.getCnslListAllCount(fnIdx, param);
		paginationInfo.setTotalRecordCount(totalCount);

		if (totalCount > 0) {
			if (usePaging == 1) {
				param.put("firstIndex", paginationInfo.getFirstRecordIndex());
				param.put("lastIndex", paginationInfo.getLastRecordIndex());
				param.put("recordCountPerPage",
						paginationInfo.getRecordCountPerPage());
			}

			// 정렬컬럼 setting
			param.put("dsetOrderField",
					JSONObjectUtil.getString(settingInfo, "dset_order_field"));

			// 2.3 목록
			param.put("useExcel", "useExcel");
			list = consultingService.getCnslListAll(fnIdx, param);
		}

		// 3. 속성 setting
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("totalCount", totalCount); // 페이징정보
		model.addAttribute("list", list); // 목록
		log.info("list {}", list);
		model.addAttribute("searchOptnHashMap", searchOptnHashMap); // 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("insttList", insttList);
		model.addAttribute("userTypeIdx", userTypeIdx);

		// 4. 기본경로

		return getViewPath("/cnslExcel");
	}

}
