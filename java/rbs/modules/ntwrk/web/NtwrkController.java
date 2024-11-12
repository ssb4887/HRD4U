package rbs.modules.ntwrk.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import rbs.egovframework.LoginVO;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.modules.instt.service.InsttService;
import rbs.modules.ntwrk.service.NtwrkCmptinstService;
import rbs.modules.ntwrk.service.NtwrkService;

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
@ModuleMapping(moduleId="ntwrk")
@RequestMapping({"/{siteId}/ntwrk", "/{admSiteId}/menuContents/{usrSiteId}/ntwrk"})
public class NtwrkController extends ModuleController {
	
	@Value("${Globals.fileStorePath}")
	private String uploadDir;
	
	@Resource(name = "ntwrkService")
	private NtwrkService ntwrkService;
	
	@Resource(name = "ntwrkCmptinstService")
	private NtwrkCmptinstService ntwrkCmptinstService;
	
	@Resource(name = "insttService")
	private InsttService insttService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
	@Resource(name = "jsonView")
	View jsonView;
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(NtwrkController.class);
	
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
	 * 네트워크 이력 불러오기
	 * @param attrVO
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/list.do")
	public String list(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu(); 
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
    	
		// 소속기관 리스트
		Map<String, Object> param = new HashMap<String, Object>();
		List<?> insttList = null;
		insttList = insttService.getList(param);
		model.addAttribute("insttList", insttList);
		model.addAttribute("myInsttIdx", loginVO.getInsttIdx());
		
		// 1. 페이지정보 setting
		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		int usePaging = JSONObjectUtil.getInt(settingInfo, "use_paging");
		if(usePaging == 1) {
			int listUnit = ModuleUtil.getSettingValue(isAdmMode, "dset_list_count", crtMenu, settingInfo, propertiesService.getInt("DEFAULT_LIST_UNIT"));	// 페이지당 목록 수
			int listMaxUnit = listUnit * propertiesService.getInt("DEFAULT_LIST_MAX_CNT");											// 최대 페이지당 목록 수 
			int listUnitStep = listUnit;										// 페이지당 목록 수 증가값a
			
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
		totalCount = ntwrkService.getNtwrkTotalCount(param);
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
			list = ntwrkService.getNtwrkList(param);
		}
		
		// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		
		fn_setCommonPath(attrVO);
		return getViewPath("/list");
	}
	
	/**
	 * 네트워크 이력 상세보기
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/view.do", method = RequestMethod.POST)
	public String view(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
    	
		// 소속기관 리스트
		Map<String, Object> param = new HashMap<String, Object>();
		List<?> insttList = null;
		insttList = insttService.getList(param);
		model.addAttribute("insttList", insttList);
		model.addAttribute("usertype", loginVO.getUsertypeIdx());
		
		if(!request.getParameter("idx").isEmpty()) {
			param.put("ntwrkIdx", request.getParameter("idx"));
			Map<String, Object> ntwrk = new HashMap<String, Object>();
			// 1. 네트워크 이력 들고오기
			ntwrk = ntwrkService.getNtwrkOne(param);
			model.addAttribute("ntwrk", ntwrk);
			
			// 2. 기관 들고오기
			List<Map<String, Object>> cmptinstList = new ArrayList<Map<String, Object>>();
			cmptinstList = ntwrkService.getNtwrkCmptinstList(param);
			model.addAttribute("cmptinstList", cmptinstList);
			
			// 3. 파일 들고오기
			List<Map<String, Object>> ntwrkFileList = new ArrayList<Map<String, Object>>();
			ntwrkFileList = ntwrkService.getNtwrkFileList(param);
			model.addAttribute("fileList", ntwrkFileList);
			
		}
		
		return getViewPath("/view");
	}
	
	/**
	 * 네트워크 이력 등록(insert)
	 * @param files(첨부파일)
	 * @param formdata(FormData)
	 * @param cmptList(기관 리스트)
	 * @param fileSeq(첨부파일 순서)
	 * @param request
	 * @param attrVO
	 * @param model
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws ServletException
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/insert.do")
	public ModelAndView insert(
			@RequestParam(value = "files", required = false) List<MultipartFile> files, 
			@RequestParam Map<String, Object> formdata, 
			@RequestParam(value = "cmptList", required = false) List<Integer> cmptList, 
			@RequestParam(value = "fileSeq", required = false) List<Integer> fileSeq, 
			HttpServletRequest request, @ModuleAttr ModuleAttrVO attrVO, ModelMap model) throws IllegalStateException, IOException, ServletException {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		/******************* biz *******************/
		formdata.put("REGI_IP", request.getRemoteAddr());
		formdata.put("LAST_MODI_IP", request.getRemoteAddr());
		
		// 1. 네트워크 이력 저장(HRD_NTWRK > generatedKey로 HRD_NTWRK_CMPTINST_ATT까지 저장)
		Map<String, Object> result = ntwrkService.insertNtwrkData(formdata, cmptList);
		
		// 2. 첨부파일 있다면 HRD_NTWRK_FILE 저장
		if(result.containsKey("NTWRK_RESULT") && (int) result.get("NTWRK_RESULT") > 0 && formdata.containsKey("ntwrkIdx")) {
			// 1. 업로드 경로 체크(폴더없으면 폴더생성)
			String uploadPath =  RbsProperties.getProperty("Globals.upload.file.path") + File.separator  + attrVO.getUploadModulePath();
			File ntwrkDir = new File(uploadPath);
			if(!ntwrkDir.exists()) {
				ntwrkDir.mkdirs();
			}
			
			// 2. 업로드 파일명(yyyymmddHHmmss_파일명)
			if(fileSeq == null) fileSeq = new ArrayList<Integer>();
			
			int fileSaveResult = 0;
			fileSaveResult += ntwrkService.updateNtwrkFileData(fileSeq, Integer.valueOf(formdata.get("ntwrkIdx").toString()));
			for(int i=0;i < files.size(); i++) {
				MultipartFile file = files.get(i);
				
				if(file.isEmpty()) continue;

				String fileOriginName = file.getOriginalFilename();

				// 2-1. 파일 형식 체크 (FIX:16835)
				String fileExtension = fileOriginName.substring(fileOriginName.lastIndexOf(".") + 1);
				if(!isExtensionAllowed(fileExtension)) {
					mav.addObject("flag", 0);
					mav.addObject("msg", "잘못된 파일 형식 업로드");
					return mav;
				}
				
				Date currentDate = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String uniqueName = dateFormat.format(currentDate);
				String fileSaveName = uniqueName + "_" + fileOriginName;
				// 3. 실제 파일 저장 로직
				try {
					
					File saveFile = new File(uploadPath + File.separator + fileSaveName);
					file.transferTo(saveFile);
					
					// 4. 파일 저장 데이터 DB에 저장
					formdata.put("fileSavedName", fileSaveName);
					formdata.put("fileOriginName", fileOriginName);
					formdata.put("fileSize", file.getSize());
					formdata.put("replcText", "네트워크 이력 관련 문서");
					formdata.put("orderIdx", i);
					fileSaveResult += ntwrkService.insertFileData(formdata);
					if(fileSaveResult > 0) {
						result.put("FILE_RESULT", "SUCCESS");
					} else {
						result.put("FILE_RESULT", "FAIL: 파일은 저장되었으나 파일 정보를 DB에 저장하던 중 오류가 발생하였습니다.");
					}
				} catch (IllegalStateException e) {
					log.info("ERROR : Exception", e.getMessage());
					result.put("FILE_RESULT", "fail");
				} catch (IOException e) {
					log.info("ERROR : Exception", e.getMessage());
					result.put("FILE_RESULT", "fail");
				}
			}
			
		}
		
		result.put("IDX", formdata.get("ntwrkIdx"));
		mav.addObject("result", result);
		return mav;
	}
	
	
	/**
	 * 네트워크이력 등록 중 모달에서의 네트워크 기관(HRD_NTWRK_CMPTINST) 검색
	 * @param formdata
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/networkSearch.do", method = RequestMethod.POST)
	public ModelAndView networkSearch(@RequestParam Map<String, Object> formdata) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>> searchList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("columnId", "CMPTINST_NAME");
		map1.put("columnVFlag", "LIKE");
		map1.put("columnValue", "%"+formdata.get("cmptinstName") +"%");
		searchList.add(map1);

		if(formdata.containsKey("insttIdx")) {
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("columnId", "INSTT_IDX");
			map2.put("columnVFlag", "=");
			map2.put("columnValue", formdata.get("insttIdx"));
			searchList.add(map2);
		}
		param.put("searchList", searchList);
		
		List<?> result = ntwrkCmptinstService.getCmptinstList(param);
		int totalCount = ntwrkCmptinstService.getCmptinstTotalCount(param);
		mav.addObject("result", result);
		mav.addObject("count", totalCount);
		return mav;
	}
	
	/**
	 * 네트워크 이력 삭제하기
	 * @param request
	 * @param values
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
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
		int result = ntwrkService.deleteNtwrk(param);
		mav.addObject("result", result);
		return mav;
	}
	
	/**
	 * 네트워크 이력 엑셀 다운로드하기
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/downloadExcel.do")
	public String downloadExcel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) {
		DataForm queryString = attrVO.getQueryString();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		// 2. DB
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();

		// 2.1 검색조건
		// 항목 설정
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
		
		// 2.3 목록
		list = ntwrkService.getNtwrkListForExcel(param);
		
		model.addAttribute("list", list);
		
		fn_setCommonPath(attrVO);
		return getViewPath("/excel");
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
