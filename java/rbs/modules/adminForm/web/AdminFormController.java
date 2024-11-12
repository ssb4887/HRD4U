package rbs.modules.adminForm.web;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import rbs.egovframework.LoginVO;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.modules.adminForm.service.AdminFormService;
import rbs.modules.basket.dto.ExcelUploadDto;

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
@ModuleMapping(moduleId="adminForm")
@RequestMapping({"/{siteId}/adminForm", "/{admSiteId}/menuContents/{usrSiteId}/adminForm"})
public class AdminFormController extends ModuleController {
	
	@Value("${Globals.fileStorePath}")
	private String uploadDir;
	
	@Resource(name = "adminFormService")
	private AdminFormService adminFormService;
	
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
	 * 방문기업서식 목록조회
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
		
		System.out.println("searchList : "+searchList);
		
		// 2.2 목록수
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		HttpSession session = request.getSession(true);
		LoginVO loginInfo = (LoginVO) session.getAttribute("loginVO");
		int userTypeIdx = loginVO.getUsertypeIdx();
		
		System.out.println("userTypeIdx : "+userTypeIdx);
		
		if(siteId.equals("dct")) {
			if(loginInfo == null) {
				request.setAttribute("msg", "로그인이 필요합니다.");
				request.setAttribute("url", "/dct/login/login.do?mId=3");
				model.addAttribute("login", false);
				return getViewPath("/alert");
				
			}else if(userTypeIdx== 40 || userTypeIdx == 30 || userTypeIdx == 55) { 
				String insttIdx = loginVO.getInsttIdx();
				if(insttIdx != null) {
					param.put("insttIdx",insttIdx);
					model.addAttribute("insttIdx",insttIdx);
				}else {
					param.put("userTypeIdx", userTypeIdx);
					model.addAttribute("userTypeIdx",userTypeIdx);
				}
				model.addAttribute("login", true);
			
				totalCount = adminFormService.getTotalCount(param);
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
					list = adminFormService.getList(param);
				}
				
			}else {
				request.setAttribute("msg", "접근 권한이 없습니다.");
				request.setAttribute("url", "/web/main/main.do?mId=1");
				return getViewPath("/alert");
			}
			
		}
		
		/**
		 * select box 데이터 put
		 */
		// 사업구분
		Map<Integer, Object> prtbizIdx = new HashMap<>();
		prtbizIdx.put(1, "S-OJT");
		prtbizIdx.put(2, "일학습병행");
		prtbizIdx.put(4, "사업주훈련");
		prtbizIdx.put(11, "컨소시엄");
		prtbizIdx.put(6, "대부");
		prtbizIdx.put(16, "인증");
		
		// 업무구분
		Map<Integer, Object> jobType = new HashMap<>();
		jobType.put(0, "모니터링");
		jobType.put(1, "동의(서약)서");
		jobType.put(2, "동의서");
		jobType.put(3, "신청서");
		jobType.put(4, "수당명세서");
		
		// 서명대상자
		Map<Integer, Object> sgntr = new HashMap<>();
		sgntr.put(0, "기업");
		sgntr.put(1, "주치의(공단지원)");
		sgntr.put(2, "공단직원");
		sgntr.put(3, "외부전문가");
		sgntr.put(4, "훈련기관");
		sgntr.put(5, "불특정");

		// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);
    	model.addAttribute("list", list);
    	model.addAttribute("prtbizIdx",prtbizIdx);
    	model.addAttribute("jobType",jobType);
    	model.addAttribute("sgntr",sgntr);
    	
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
	
	/**
	 * 방문기업서식 상세조회
	 * 
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(method=RequestMethod.POST,value = "/view.do")
	public String view(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		HttpSession session = request.getSession(true);
		LoginVO loginInfo = (LoginVO) session.getAttribute("loginVO");
		
		int userTypeIdx = loginInfo.getUsertypeIdx();

		// 1. 필수 parameter 검사
		String keyIdx = request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name"));
		if(StringUtil.isEmpty(keyIdx)) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		String idxColumnName = JSONObjectUtil.getString(settingInfo, "idx_column");

		
		// 2. DB
		DataMap dt = null;
		DataMap ft = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();

		searchList.add(new DTForm(idxColumnName, keyIdx));
		
		if(!StringUtil.isEmpty(keyIdx)) {
			param.put("admsfmIdx", keyIdx);
		}
		param.put("searchList", searchList);		
		
		String memberIdx = loginInfo.getMemberIdx();
		Map doc = adminFormService.getDoc(memberIdx);

		// 상세정보
		dt = adminFormService.getView(param);
		System.out.println("#ehoh > dt :"+dt);
		
		// 파일 상세정보
		ft = adminFormService.getFileView(param);
		
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
		model.addAttribute("ft", ft);																				// 파일 상세정보
    	model.addAttribute("doc", doc);
    	model.addAttribute("idx", keyIdx);
    	model.addAttribute("userTypeIdx", userTypeIdx);
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);																// 페이지구분
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	/**
	 * 방문기업서식 등록 페이지
	 * 
	 */
	@Auth(role = Role.R)
	@RequestMapping(method= {RequestMethod.GET, RequestMethod.POST}, value = "/input.do")
	public ModelAndView input(@RequestParam Map<String, Object> formData
												, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		ModelAndView mav = new ModelAndView();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		DataMap form = null;
		DataMap fileForm = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		HttpSession session = request.getSession(true);
		LoginVO loginInfo = (LoginVO) session.getAttribute("loginVO");
		
		String idx = request.getParameter("idx");
		formData.put("admsfmIdx", idx);

		param.put("searchList", searchList);
		param.put("admsfmIdx", idx);
		
		//임시저장 데이터
		form = adminFormService.getView(param);
		// 임시저장 파일 데이터
		fileForm = adminFormService.getFileView(param);
		
		if(idx != null) {
	    	mav.addObject("fileForm",fileForm);
	    	mav.addObject("form",form);
	    	mav.addObject("idx",idx);
		}
		
		// 최초 등록자 정보 조회
		String memberIdx = loginInfo.getMemberIdx();
		Map doc = adminFormService.getDoc(memberIdx);
		String memberName = loginInfo.getMemberName();
		
		/**
		 * select box 데이터 put
		 */
		// 사업구분
		Map<Integer, Object> prtbizIdx = new HashMap<>();
		prtbizIdx.put(1, "S-OJT");
		prtbizIdx.put(2, "일학습병행");
		prtbizIdx.put(4, "사업주훈련");
		prtbizIdx.put(6, "컨소시엄");
		prtbizIdx.put(7, "대부");
		prtbizIdx.put(16, "인증");
		
		// 업무구분
		Map<Integer, Object> jobType = new HashMap<>();
		jobType.put(0, "모니터링");
		jobType.put(1, "동의(서약)서");
		jobType.put(2, "동의서");
		jobType.put(3, "신청서");
		jobType.put(4, "수당명세서");
		
		// 서명대상자
		Map<Integer, Object> sgntr = new HashMap<>();
		sgntr.put(0, "기업");
		sgntr.put(1, "주치의(공단지원)");
		sgntr.put(2, "공단직원");
		sgntr.put(3, "외부전문가");
		sgntr.put(4, "훈련기관");
		sgntr.put(5, "불특정");
		
    	
		// 1. 속성 setting
		// 1.1 항목설정
		String submitType = "write";
		mav.addObject("optnHashMap", getOptionHashMap(submitType, itemInfo));
		mav.addObject("submitType", submitType);
		
		mav.addObject("memberName", memberName);
    	mav.addObject("doc", doc);
    	mav.addObject("prtbizIdx",prtbizIdx);
    	mav.addObject("jobType",jobType);
    	mav.addObject("sgntr",sgntr);
		mav.addObject(formData);
		mav.addObject("idx", idx);
		mav.setViewName(getViewPath("/input"));
		
    	// 2. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return mav;
	}
	
	/**
	 * 방문기업서식 등록처리
	 * 
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.POST, value = "/inputProc.do")
	public ModelAndView inputProc(@ModuleAttr ModuleAttrVO attrVO, @RequestParam Map<String, Object> formData,MultipartHttpServletRequest request) throws IOException {
		String itemId = "file";
		MultipartFile file = request.getFile("file");
		
		Map<String, Object> param = new HashMap<String, Object>();
		DataMap fileForm = null;
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(formData);
		mav.setViewName(getViewPath("/list"));

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

		// 서식 등록
		adminFormService.setForm(formData);

		Integer admsfmIdx = Integer.parseInt(formData.get("admsfmIdx").toString());
		param.put("admsfmIdx", admsfmIdx);
		
		// 1. 업로드 경로 체크(폴더없으면 폴더생성)
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		
		String uploadPath =  RbsProperties.getProperty("Globals.upload.file.path") + File.separator  + attrVO.getConfModule() + File.separator + year;
		File fileDir = new File(uploadPath);
		if(!fileDir.exists()) {
			fileDir.mkdirs();
		}
		
		// 1. 업로드 파일 저장
		ExcelUploadDto dto = new ExcelUploadDto();
		String fileRealName = file.getOriginalFilename();
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String uniqueName = dateFormat.format(currentDate);
		String fileSaveName = uniqueName + "_" + fileRealName;
		
		if(fileRealName != null) {
			int fileSize = (int) file.getSize();
			
			File saveFile = new File(uploadPath + File.separator + fileSaveName);
			try {
				file.transferTo(saveFile); // 실제 파일 저장 메서드
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// 2. 업로드 파일 정보 DB 저장
			dto.setUploadFileName(fileRealName);
			dto.setResultFileName(uniqueName + "_" + fileRealName);
			dto.setItemId(itemId);
			dto.setFileSize(fileSize);
			dto.setAdmsfmIdx(admsfmIdx);
			
		}
		
		// 3. 방문기업서식 파일 업로드
		fileForm = adminFormService.getFileView(param);
		
		if (fileForm != null) {
			String oldFileName = fileForm.get("FILE_SAVED_NAME").toString();
			
			// 이미 등록된 파일 있음, 등록된 파일을 변경하지 않음
			if(!oldFileName.equals(dto.getResultFileName()) && !fileRealName.isEmpty() && fileRealName != null) {
				
				// 기존 fileIdx 추가
				BigDecimal fileIdxDecimal = (BigDecimal) fileForm.get("FLE_IDX");
				Integer fleIdx = (fileIdxDecimal != null)? fileIdxDecimal.intValue() : null;
				dto.setFleIdx(fleIdx);
				
				adminFormService.updateFile(dto, request.getRemoteAddr());
				
			}
			
		}else {
			String resultFileName = uniqueName + "_" + fileRealName;
			formData.put("oldFileName", resultFileName);
			
			adminFormService.insertFile(dto, request.getRemoteAddr());
		}
		
		
		return mav;
	}
	
	/**
	 * 방문기업서식 삭제
	 * 
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/delete.do")
	public String delete(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		JSONObject settingInfo = attrVO.getSettingInfo();
		String ajaxPName = attrVO.getAjaxPName();
		
		
		String admsfmIdx = request.getParameter("idx");
		String regiIp = request.getRemoteAddr();
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("admsfmIdx", admsfmIdx);
		param.put("lastModiIp", regiIp);
		
		int result = adminFormService.delete(param);
		
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
		list = adminFormService.getList(param);
    	
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
		
		if(!StringUtil.isEmpty(baseQueryString)) {
			clipReportUrl += baseQueryString;
			excelUrl += baseQueryString;
		}
		request.setAttribute("URL_CLIPREPORT", clipReportUrl);
		request.setAttribute("URL_EXCEL", excelUrl);
		
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
			return (modiCnt > 0);
		}
		
		return true;
	}
}