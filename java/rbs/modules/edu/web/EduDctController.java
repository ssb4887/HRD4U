package rbs.modules.edu.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import rbs.modules.edu.service.EduDctService;
import rbs.modules.instt.service.InsttService;
import rbs.modules.member.service.MemberGrupService;
import rbs.modules.member.service.MemberUstpService;
import rbs.modules.memberManage.service.MemberManageService;
import rbs.modules.ntwrk.web.NtwrkCmptinstController;

/**
 * (dct) 교육과정 관리
 * 1. 지역네트워크 관리 > CEO 교육과정 관리
 * 2. 행정지원 > 주치의 > 주치의 교육내역
 * * 참고 : 디자인템플릿명.toUpperCase() == HRD_SPTJ_EDC.EDC_CD
 * @author minjung
 *
 */
@Controller
@ModuleMapping(moduleId="eduDct", confModule="edu")
@RequestMapping({"/{siteId}/eduDct", "/{admSiteId}/menuContents/{usrSiteId}/eduDct"})
public class EduDctController extends ModuleController {

	@Resource(name = "eduDctService")
	private EduDctService eduDctService;
	
	@Resource(name = "insttService")
	private InsttService insttService;
	
	@Resource(name = "memberGrupService")
	private MemberGrupService memberGrupService;
	
	@Resource(name = "memberManageService")
	private MemberManageService memberManageService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
	@Resource(name = "jsonView")
	View jsonView;
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(EduDctController.class);
	
	// 업로드 가능한 파일 확장자
	private static final String[] ALLOWED_EXTENSIONS = {"txt", "xlsx", "xls", "pdf", "hwp", "doc", "ppt", "pptx", "jpeg", "jpg", "png"};
		
	
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
	 * 주치의(dct) 교육과정 관리 목록 불러오기
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
		
		// setting_info의 edc_cd(교육코드)에 맞는 목록만 출력
		String edcCd = JSONObjectUtil.getString(settingInfo, "edc_cd");
		param.put("edcCd", edcCd);
			
		totalCount = eduDctService.getEdcTotalCount(param);
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
	 * 교육과정 관리 입력/수정 페이지
	 * @param request
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/input.do")
	public String input(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 소속기관 리스트
		Map<String, Object> param = new HashMap<String, Object>();
		List<?> insttList = null;
		insttList = insttService.getList(param);
		model.addAttribute("insttList", insttList);
		model.addAttribute("usertype", loginVO.getUsertypeIdx());
		
		if(request.getParameter("idx") != null) {
			param.put("edcIdx", request.getParameter("idx"));
			// 1. 교육과정 정보
			Map<String, Object> edc = eduDctService.getEdc(param);
			// 2. 파일목록
			List<Map<String, Object>> fileList = eduDctService.getEdcFileList(param);
			model.addAttribute("edc", edc);
			model.addAttribute("fileList", fileList);
		}
		fn_setCommonPath(attrVO);
		return getViewPath("/input");
	}
	
	/**
	 * 교육과정 상세 페이지
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
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("usertype", loginVO.getUsertypeIdx());
		
		// 1. 교육과정 정보 상세보기
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("edcIdx", request.getParameter("idx"));
		model.addAttribute("mId", request.getParameter("mId"));
		model.addAttribute("idx", request.getParameter("idx"));
		
		// 1-1. 교육과정 정보
		Map<String, Object> edc = eduDctService.getEdc(param);
		// 1-2. 파일목록
		List<Map<String, Object>> fileList = eduDctService.getEdcFileList(param);
		model.addAttribute("edc", edc);
		model.addAttribute("fileList", fileList);

		
		// 2. 교육생 정보 목록
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
		List<?> memberList = null;
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 항목설정
		String submitType = "view";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		
		// 항목설정으로 검색조건 setting
		String listSearchId = "view_search";		// 검색설정
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
		totalCount = eduDctService.getEdcMemberListCount(param);
		paginationInfo.setTotalRecordCount(totalCount);
		
		if(totalCount > 0) {
			if(usePaging == 1) {
				param.put("firstIndex", paginationInfo.getFirstRecordIndex());
				param.put("lastIndex", paginationInfo.getLastRecordIndex());
				param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
			}
			
			// 정렬컬럼 setting
			param.put("dsetOrderField", JSONObjectUtil.getString(settingInfo, "dset_order_field"));
			
			// 목록
			memberList = eduDctService.getEdcMemberList(param);
		}
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("edcMemberList", memberList);
		model.addAttribute("searchOptnHashMap", searchOptnHashMap);
		model.addAttribute("optnHashMap", optnHashMap);
		
		// 회원 검색 모달창 > 사용자유형 목록
		Map<String, Object> temp = new HashMap<>();
		List<?> userGroupList = memberGrupService.getOptnList(temp);
		model.addAttribute("userGroupList", userGroupList);

		fn_setCommonPath(attrVO);
		return getViewPath("/view");
	}
	
	/**
	 * 교육과정 등록하기(INSERT/UPDATE)
	 * @param files
	 * @param formdata
	 * @param fileSeq
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public ModelAndView insert(
			@RequestParam(value = "files", required = false) List<MultipartFile> files, 
			@RequestParam Map<String, Object> formdata, 
			@RequestParam(value = "fileSeq", required = false) List<Integer> fileSeq, 
			@ModuleAttr ModuleAttrVO attrVO, 
			HttpServletRequest request, 
			ModelMap model) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		/******************* biz *******************/
		formdata.put("REGI_IP", request.getRemoteAddr());
		formdata.put("LAST_MODI_IP", request.getRemoteAddr());
		
		// 1. 교육과정 저장(INSERT/UPDATE)
		Map<String, Object> insertEdcResult = eduDctService.insertEdc(formdata);
		mav.addObject("INSERT_EDC_RESULT", insertEdcResult);
		
		// 2. 파일 추가
		if(!files.isEmpty()) {
			// 2-1. 확장자 체크
			for(int i=0;i < files.size();i++) {
				if(files.get(i).isEmpty()) continue;
				String fileName = files.get(i).getOriginalFilename();
				String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
				if(!isExtensionAllowed(fileExtension)) {
					mav.addObject("INSERT_EDC_FILE_RESULT", 0);
					return mav;
				}
			}
		}
		
		// 3. 파일 업로드(INSERT/UPDATE)
		if(fileSeq == null) fileSeq = new ArrayList<Integer>();
		Map<String, Object> insertEdcFilesResult = eduDctService.saveEdcFiles(attrVO, formdata, files, fileSeq);
		mav.addObject("INSERT_EDC_FILE_RESULT", insertEdcFilesResult);
		
		return mav;
	}
	
	/**
	 * 교육과정 선택 삭제하기
	 * @param request
	 * @param values
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Auth(role = Role.D)
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public ModelAndView delete(HttpServletRequest request, @RequestParam("selectedValues") String values) throws JsonParseException, JsonMappingException, IOException {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		ObjectMapper objectMapper = new ObjectMapper();
		int[] deleteIdxs = objectMapper.readValue(values, int[].class);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("deleteIdxs", deleteIdxs);
		param.put("LAST_MODI_IP", request.getRemoteAddr());
		int result = eduDctService.deleteEdc(param);
		mav.addObject("result", result);
		
		return mav;
	}
	
	/**
	 * 교육신청할 사용자(회원) 검색
	 * @param request
	 * @param formdata
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/member.do", method = RequestMethod.POST)
	public ModelAndView getMember(HttpServletRequest request, @RequestParam Map<String, Object> formdata) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		List<?> list = null;
		if(formdata.get("keyField").equals("username")) {
			formdata.put("keyFieldColumn", "MEMBER_NAME");
		} else {
			formdata.put("keyFieldColumn", "MEMBER_ID");
		}
		list = eduDctService.getMemberList(formdata);
		mav.addObject("memberList", list);
		return mav;
	}
	
	/**
	 * 교육신청 작성
	 * @param request
	 * @param formdata
	 * @return
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public ModelAndView registerMemberToEdc(HttpServletRequest request, @RequestParam Map<String, Object> formdata) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		formdata.put("memberIdx", formdata.get("userIdx"));
		formdata.put("confmStatus", 10); // 승인상태: 신청(10) [MASTER_CODE: EDC_CONFM_STATUS]
		int result = eduDctService.insertEdcMember(formdata);
		mav.addObject("result", result);
		return mav;
	}
	
	/**
	 * 교육신청자 선택 삭제
	 * @param request
	 * @param formdata
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Auth(role = Role.D)
	@RequestMapping(value = "/deleteMember.do", method = RequestMethod.POST)
	public ModelAndView deleteEdcMember(HttpServletRequest request, @RequestParam Map<String, Object> formdata) throws JsonParseException, JsonMappingException, IOException {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		ObjectMapper objectMapper = new ObjectMapper();
		int[] deleteIdxs = objectMapper.readValue(formdata.get("selectedValues").toString(), int[].class);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("deleteIdxs", deleteIdxs);
		param.put("edcIdx", formdata.get("edcIdx"));
		param.put("LAST_MODI_IP", request.getRemoteAddr());
		
		int result = eduDctService.deleteEdcMembers(param);
		mav.addObject("result", result);
		return mav;
	}
	
	
	/**
	 * 교육생 상태 변화(신청상태/참석여부)
	 * @param request
	 * @param formdata
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Auth(role = Role.U)
	@RequestMapping(value = "/changeMemberStatus.do", method = RequestMethod.POST)
	public ModelAndView changeMemberStatus(HttpServletRequest request, @RequestParam Map<String, Object> formdata) throws JsonParseException, JsonMappingException, IOException {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		formdata.put("REGI_IP", request.getRemoteAddr());
		formdata.put("LAST_MODI_IP", request.getRemoteAddr());
		
		if(formdata.get("statusName").equals("edcConfmStatus")) {
			formdata.put("statusName", "CONFM_STATUS");
		} else {
			formdata.put("statusName", "ATT_YN");
		}
		int result = eduDctService.changeEdcMemberStatus(formdata);
		mav.addObject("result", result);
		return mav;
	}
	
	/**
	 * 교육생 엑셀 다운로드
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "downloadEdcMembers.do", method = RequestMethod.POST)
	public String downloadEdcMembers(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) {
		DataForm queryString = attrVO.getQueryString();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		// 2. DB
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("edcIdx", request.getParameter("idx"));
		List<DTForm> searchList = new ArrayList<DTForm>();

		// 2.1 검색조건
		// 항목 설정
		String submitType = "view";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		
		// 항목설정으로 검색조건 setting
		String listSearchId = "view_search";		// 검색설정
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
		
		// 1. 목록(교육생 목록)
		list = eduDctService.getEdcMemberList(param);
		model.addAttribute("list", list);
		
		// 2. 교육과정 정보
		Map<String, Object> edc = eduDctService.getEdc(param);
		model.addAttribute("edc", edc);
		
		fn_setCommonPath(attrVO);
		return getViewPath("/membersExcel");
	}
	
	/**
	 * 교육생 신청 상세 페이지
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/memberDetail.do")
	public String memberDetail(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) {
		Map<String, Object> param = new HashMap<>();
		param.put("edcIdx", request.getParameter("idx"));
		param.put("memberIdx", request.getParameter("member"));
		
		Map<String, Object> member = eduDctService.getEdcMember(param);
		model.addAttribute("member", member);
		
		fn_setCommonPath(attrVO);
		return getViewPath("/memberDetail");
	}
	
	/**
	 * 교육 수료증 출력
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/printCertificate.do", method = RequestMethod.POST)
	public String printCertificate(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) {
		// 1. edcIdx(교육과정식별번호) 혹은 memberIdx(교육생식별번호)가 없는 경우
		if(request.getParameter("idx") == null || request.getParameter("member") == null || request.getParameter("idx").isEmpty() || request.getParameter("member").isEmpty()) {
			String ajaxPName = attrVO.getAjaxPName();
			boolean isAjax = attrVO.isAjax();
			model.addAttribute("message",
					MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
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
	 * 수료증 발급
	 * @param attrVO
	 * @param request
	 * @param formdata
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/issueCertificate.do", method = RequestMethod.POST)
	public ModelAndView issueCertificate(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, @RequestParam Map<String, Object> formdata) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		// 결과 반환(result) : 0=실패
		int result = 0;

		if(request.getParameter("edcIdx") == null || request.getParameter("memberIdx") == null || request.getParameter("edcIdx").isEmpty() || request.getParameter("memberIdx").isEmpty()) {
			String msg = "잘못된 접근입니다. 새로고침 후 다시 시도해주세요.";
			String msgForAdmin = "수료증 발급에 필요한 파라미터가 부족합니다.";
			mav.addObject("result", result);
			mav.addObject("msg", msg);
			mav.addObject("admin", msgForAdmin);
			return mav;
		}
		
		// 수료증 발급 == 발급번호 생성
		// 발급번호 규칙 : (현재날짜)YYYYMM-(교육과정식별번호)edcIdx-(교육생번호)no
		result = eduDctService.issueCertificate(formdata);
		mav.addObject("result", result);
		return mav;
	}
	
	/**
	 * 발급된 수료증 취소하기(삭제)
	 * @param attrVO
	 * @param request
	 * @param formdata
	 * @return
	 */
	@Auth(role = Role.D)
	@RequestMapping(value = "/deleteCertificate.do", method = RequestMethod.POST)
	public ModelAndView deleteCertificate(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, @RequestParam Map<String, Object> formdata) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		// 결과 반환(result) : 0=실패
		int result = 0;

		if(request.getParameter("edcIdx") == null || request.getParameter("memberIdx") == null || request.getParameter("edcIdx").isEmpty() || request.getParameter("memberIdx").isEmpty()) {
			String msg = "잘못된 접근입니다. 새로고침 후 다시 시도해주세요.";
			String msgForAdmin = "수료증 발급 취소에 필요한 파라미터가 부족합니다.";
			mav.addObject("result", result);
			mav.addObject("msg", msg);
			mav.addObject("admin", msgForAdmin);
			return mav;
		}
		
		formdata.put("LAST_MODI_IP", request.getRemoteAddr());
		try {
			eduDctService.deleteCertificate(formdata);
			mav.addObject("result", "success");
			mav.addObject("resultData", formdata);
			mav.addObject("msg", "수료증 발급이 취소되었습니다.");
		} catch(Exception e) {
			mav.addObject("result", "fail");
			mav.addObject("msg", e.getMessage());
		}
		
		return mav;
	}
	
	/**
	 * 교육생 일괄 상태변경하기(UPDATE)
	 * @param request
	 * @param formdata
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Auth(role = Role.U)
	@RequestMapping(value = "/changeMemberStatusAll.do", method = RequestMethod.POST)
	public ModelAndView changeMemberStatusAll(HttpServletRequest request, @RequestParam Map<String, Object> formdata, @RequestParam("memberIdx") String memberIdxs) throws JsonParseException, JsonMappingException, IOException {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		ObjectMapper objectMapper = new ObjectMapper();
		int[] updateMemberIdxs = objectMapper.readValue(memberIdxs, int[].class);
		formdata.put("memberIdxs", updateMemberIdxs);
		formdata.put("REGI_IP", request.getRemoteAddr());
		formdata.put("LAST_MODI_IP", request.getRemoteAddr());
		
		int result = eduDctService.changeManyEdcMemberStatus(formdata);
		mav.addObject("result", result);
		return mav;
	}
	
	/**
	 * 교육과정&교육생 일괄업로드 샘플파일 다운로드
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/downloadSample.do")
	public String downloadSample(HttpServletRequest request) {
		String pageName = "/sample"; // 교육관리 업로드 엑셀 샘플
		if(request.getParameter("sample") != null && request.getParameter("sample").equals("2")) {
			pageName = "/sample2"; // 교육생관리 업로드 엑셀 샘플
		}
		return getViewPath(pageName);
	}
	
	/**
	 * 교육과정 일괄 업로드(엑셀)
	 * @param file
	 * @param request
	 * @param attrVO
	 * @return
	 * @throws Exception 
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/uploadExcel.do")
	public ModelAndView uploadExcel(@RequestParam("excelfile") MultipartFile file, HttpServletRequest request, @ModuleAttr ModuleAttrVO attrVO) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		// 0. 소속기관 비교용 insttList
		Map<String, Object> param = new HashMap<String, Object>();
		List<Object> insttList = null;
		insttList = insttService.getList(param);
		
		// setting_info의 edc_cd(교육코드)에 맞는 목록만 출력
		JSONObject settingInfo = attrVO.getSettingInfo();
		String edcCd = JSONObjectUtil.getString(settingInfo, "edc_cd").toUpperCase();
					
		// 1. 엑셀 내용 DB에 저장
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(file.getInputStream());
		} catch (InvalidFormatException e1) {
			log.info("ERROR : Exception", e1.getMessage());
		} catch (IOException e) {
			log.info("ERROR : Exception", e.getMessage());
		}
		
		if(workbook != null) {
			Sheet sheet = workbook.getSheetAt(0);
			String[] column = {"edcName", "insttName", "edcPlace", "edcStartDate", "edcEndDate", "totEdcTime", "instrctrName", "instrctrTelno", "instrctrEmail", "instrctrIntrcn", "othbcYn", "recptBgndt", "recptEnddt", "ctfhvIssueYn", "maxRecptNmpr", "cn"};
			Iterator<Row> rowIterator = sheet.iterator();
			DataFormatter dataFormatter = new DataFormatter();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			// Excel Row 돌면서 저장할 map 만들기
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if(row.getRowNum() == 0) continue;
				
				Map<String, Object> map = new HashMap<>(); // DB에 저장할 map
				
				for(int i=0;i < column.length; i++) {
					Cell cell = row.getCell(i);
					
					// 교육명은 필수값 
					if(column[i].equals("edcName") && (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
						mav.addObject("result", "fail");
						mav.addObject("msg", "교육명은 필수입력값입니다." + getCellPosition(i, row.getRowNum()));
						return mav;
					}
					
					// 소속기관(엑셀:소속기관명 > DB:소속기관식별번호)
					if(column[i].equals("insttName")) {
						String insttIdx = "";
						// 소속기관은 필수값
						if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							mav.addObject("result", "fail");
							mav.addObject("msg", "소속기관은 필수입력값입니다." + getCellPosition(i, row.getRowNum()));
							return mav;
						}
						
						String insttNameInRow = dataFormatter.formatCellValue(cell);
						for(Object instt: insttList) {
							if(instt instanceof Map) {
								Map<?, ?> instance = (Map<?, ?>) instt;
								if(instance.get("INSTT_NAME").equals(insttNameInRow)) {
									insttIdx = instance.get("INSTT_IDX").toString();
								}
							}
						}
						
						// 올바르지 않은 소속기관명
						if(insttIdx.equals("")) {
							mav.addObject("result", "fail");
							mav.addObject("msg", "\"" + insttNameInRow + "\" 는(은) 소속기관이 아닙니다." + getCellPosition(i, row.getRowNum()));
							return mav;
						}
						
						map.put("insttIdx", insttIdx);
						continue;
					}
					
					// 교육장소는 필수값
					if(column[i].equals("edcPlace") && (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
						mav.addObject("result", "fail");
						mav.addObject("msg", "교육장소는 필수입력값입니다." + getCellPosition(i, row.getRowNum()));
						return mav;
					}
					
					// 공개여부(Y/N)은 필수값
					if(column[i].equals("othbcYn")) {
						if((cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
							mav.addObject("result", "fail");
							mav.addObject("msg", "공개여부(Y/N)는 필수입력값입니다." + getCellPosition(i, row.getRowNum()));
							return mav;
						} else if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String cellValue = cell.getStringCellValue().toUpperCase();
							if(!"Y".equals(cellValue) && !"N".equals(cellValue)) {
								mav.addObject("result", "fail");
								mav.addObject("msg", "공개여부(Y/N)는 Y 또는 N으로만 입력하여야 합니다." + getCellPosition(i, row.getRowNum()));
								return mav;
							}
						}
						
						map.put(column[i], dataFormatter.formatCellValue(cell).toUpperCase());
						continue;
					}
					
					// 수료증발급여부(Y/N)은 필수값
					if(column[i].equals("ctfhvIssueYn")) {
						if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							mav.addObject("result", "fail");
							mav.addObject("msg", "수료증발급여부(Y/N)는 필수입력값입니다." + getCellPosition(i, row.getRowNum()));
							return mav;
						} else if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String cellValue = cell.getStringCellValue().toUpperCase();
							if(!"Y".equals(cellValue) && !"N".equals(cellValue)) {
								mav.addObject("result", "fail");
								mav.addObject("msg", "수료증발급여부(Y/N)는 Y 또는 N으로만 입력하여야 합니다." + getCellPosition(i, row.getRowNum()));
								return mav;
							}
						}
						
						map.put(column[i], dataFormatter.formatCellValue(cell).toUpperCase());
						continue;
					}
					
					// 교육일자 및 접수일자는 날짜형식으로 파싱
					if(column[i].equals("edcStartDate") || column[i].equals("edcEndDate") || column[i].equals("recptBgndt") || column[i].equals("recptEnddt")) {
						// 교육일자 및 접수일자는 필수값
						if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							mav.addObject("result", "fail");
							mav.addObject("msg", "교육기간 및 접수기간은 필수입력값입니다." + getCellPosition(i, row.getRowNum()));
							return mav;
						}
						
						// 일자(date) 포맷 변환
						String formattedValue = "";
						try {
							if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)) {
								Date dateValue = cell.getDateCellValue();
								formattedValue = sdf.format(dateValue);
							} else if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
								Date dateValue = sdf.parse(cell.getStringCellValue());
								formattedValue = sdf.format(dateValue);
							}
							map.put(column[i], formattedValue);
						} catch(ParseException e) {
							mav.addObject("result", "fail");
							mav.addObject("msg", "날짜 형식이 맞지 않습니다." + getCellPosition(i, row.getRowNum()));
							return mav;
						}
						
						continue;
					}
					
					if(column[i].equals("maxRecptNmpr") && (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
						map.put(column[i], 0);
						continue;
					}
					
					map.put(column[i], dataFormatter.formatCellValue(cell));
				}
				
				map.put("REGI_IP", request.getRemoteAddr());
				map.put("edcCd", edcCd);
				list.add(map);
			}
			
			try {
				List<Map<String, Object>> result = eduDctService.insertManyEdc(list);
				mav.addObject("result", "success");
				mav.addObject("resultData", result);
				mav.addObject("total", sheet.getPhysicalNumberOfRows() -1);
			} catch(Exception e) {
				mav.addObject("result", "fail");
				mav.addObject("msg", e.getMessage());
			}
		}
		
		return mav;
	}
	
	/**
	 * 교육과정 목록 엑셀 다운로드
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/downloadExcel.do")
	public String downloadExcel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) {
		boolean isAdmMode = attrVO.isAdmMode();
		DataForm queryString = attrVO.getQueryString();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		
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
		
		// setting_info의 edc_cd(교육코드)에 맞는 목록만 출력
		String edcCd = JSONObjectUtil.getString(settingInfo, "edc_cd");
		param.put("edcCd", edcCd);
		
		// 목록
//		list = eduDctService.getEdcList(param);
		list = eduDctService.getEdcListForExcel(param);
		model.addAttribute("list", list);
		
		return getViewPath("/excel");
	}
	
	
	/**
	 * 교육생 일괄 업로드
	 * @return
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/uploadMemberExcel.do")
	public ModelAndView uploadMemberExcel(@RequestParam("excelfile") MultipartFile file, HttpServletRequest request, @ModuleAttr ModuleAttrVO attrVO) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		if(request.getParameter("idx") == null || request.getParameter("idx").isEmpty()) {
			mav.addObject("result", "fail");
			mav.addObject("msg", "교육관리번호가 존재하지 않습니다. 새로고침 후 다시 시도해주세요.");
			return mav;
		}
		String edcIdx = request.getParameter("idx");
		
		// 1. 엑셀 내용 DB에 저장
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(file.getInputStream());
		} catch (InvalidFormatException e1) {
			log.info("ERROR : Exception", e1.getMessage());
		} catch (IOException e) {
			log.info("ERROR : Exception", e.getMessage());
		}
		
		if(workbook != null) {
			Sheet sheet = workbook.getSheetAt(0);
			String[] column = {"memberId", "confmStatus", "attYn"};
			Iterator<Row> rowIterator = sheet.iterator();
			DataFormatter dataFormatter = new DataFormatter();
			
			// Excel Row 돌면서 저장할 map 만들기
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if(row.getRowNum() == 0) continue;
				
				Map<String, Object> map = new HashMap<>(); // DB에 저장할 map
				
				for(int i=0;i < column.length; i++) {
					Cell cell = row.getCell(i);
					
					// 교육생 아이디는 필수값 
					if(column[i].equals("memberId")) {
						if(column[i].equals("memberId") && (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
							mav.addObject("result", "fail");
							mav.addObject("msg", "교육생의 HRD4U 아이디는 필수입력값입니다." + getCellPosition(i, row.getRowNum()));
							return mav;
						}
						
						map.put(column[i], dataFormatter.formatCellValue(cell).trim());
						continue;
					}
					
					// 신청상태는 필수값
					if(column[i].equals("confmStatus")) {
						String confmStatus = "";
						if((cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
							mav.addObject("result", "fail");
							mav.addObject("msg", "신청상태(신청/승인/반려)는 필수입력값입니다." + getCellPosition(i, row.getRowNum()));
							return mav;
							
						}
						
						if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String cellValue = cell.getStringCellValue().trim();
							if(!("신청".equals(cellValue) || "승인".equals(cellValue) || "반려".equals(cellValue))) {
								mav.addObject("result", "fail");
								mav.addObject("msg", "신청상태는 '신청/승인/반려' 중 하나여야 합니다." + getCellPosition(i, row.getRowNum()));
								return mav;
							}
							
							if("신청".equals(cellValue)) confmStatus = "10";
							if("승인".equals(cellValue)) confmStatus = "55";
							if("반려".equals(cellValue)) confmStatus = "40";
						}
						
						if("".equals(confmStatus)) {
							mav.addObject("result", "fail");
							mav.addObject("msg", "신청상태는 '신청/승인/반려' 중 하나여야 합니다." + getCellPosition(i, row.getRowNum()));
							return mav;
						}
						
						map.put(column[i], confmStatus);
						continue;
					}
					
					// 참석여부(Y/N)은 필수값
					if(column[i].equals("attYn")) {
						if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							mav.addObject("result", "fail");
							mav.addObject("msg", "참석여부(Y/N)는 필수입력값입니다." + getCellPosition(i, row.getRowNum()));
							return mav;
						} else if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
							String cellValue = cell.getStringCellValue().toUpperCase();
							if(!"Y".equals(cellValue) && !"N".equals(cellValue)) {
								mav.addObject("result", "fail");
								mav.addObject("msg", "참석여부(Y/N)는 Y 또는 N 으로만 입력하여야 합니다." + getCellPosition(i, row.getRowNum()));
								return mav;
							}
						}
						
						map.put(column[i], dataFormatter.formatCellValue(cell).toUpperCase().trim());
						continue;
					}
				}
				
				map.put("REGI_IP", request.getRemoteAddr());
				map.put("edcIdx", edcIdx);
				list.add(map);
			}
			
			try {
				List<Map<String, Object>> result = eduDctService.insertEdcMembers(list);
				mav.addObject("result", "success");
				mav.addObject("resultData", result);
				mav.addObject("total", sheet.getPhysicalNumberOfRows() -1);
			} catch(Exception e) {
				mav.addObject("result", "fail");
				mav.addObject("msg", e.getMessage());
			}
		}
		
		return mav;
	}
	
	/**
	 * 회원 기본정보 가져오기
	 * @param memberIdx
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/getMemberData.do", method = RequestMethod.POST)
	public ModelAndView getMemberData(@RequestParam Map<String, Object> formdata) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		String memberIdx = formdata.get("memberIdx").toString();

		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		searchList.add(new DTForm("A.MEMBER_IDX", memberIdx));
		param.put("searchList", searchList);
		
		// 회원 상세정보
		List<?> list = null;
		list = memberManageService.getList(param);
		
		if(list.isEmpty() || list.size() < 1) {
			mav.addObject("result", "fail");
			mav.addObject("msg", "회원정보가 없습니다.새로고침 후 다시 시도해주세요.");
		} else {
			mav.addObject("result", "success");
			mav.addObject("member", list.get(0));
		}
		return mav;
	}
	
	/**
	 * 파일 업로드 확장자 체크
	 * @param extension
	 * @return
	 */
	private static boolean isExtensionAllowed(String extension) {
		for(String allowedExtension : ALLOWED_EXTENSIONS) {
			if(allowedExtension.equals(extension)) {
				return true;
			}
		}
		return false;
	}
	
	// 열 인덱스를 알파벳으로 변환하는 메서드
	private String getColumnAlphabet(int columnIndex) {
		StringBuilder result = new StringBuilder();
		while(columnIndex >= 0) {
			result.insert(0,  (char)('A' + columnIndex % 26));
			columnIndex = (columnIndex / 26) - 1;
		}
		return result.toString();
	}
	
	// 셀 위치를 [A1] 같은 형태로 반환하는 메서드
	private String getCellPosition(int columnIndex, int rowIndex) {
		return "[" + getColumnAlphabet(columnIndex) + (rowIndex + 1) + "]셀";
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
		
		// 추가경로
		// memberDetail : 교육생 신청 상세페이지
		String memberDetail = "memberDetail.do"; 
		String sampleDownload = "downloadSample.do";
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String baseQueryString = StringUtil.getString(request.getAttribute("baseQueryString"));
		if(!StringUtil.isEmpty(baseQueryString)) {
			listName += baseQueryString;
			inputName += baseQueryString;
			viewName += baseQueryString;
			memberDetail += baseQueryString;
			sampleDownload += baseQueryString;
		}
		request.setAttribute("URL_LIST", listName);
		request.setAttribute("URL_INPUT", inputName);
		request.setAttribute("URL_VIEW", viewName);
		request.setAttribute("URL_MEMBER_DETAIL", memberDetail);
		request.setAttribute("URL_SAMPLE", sampleDownload);
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
}
