package rbs.modules.ntwrk.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import rbs.modules.instt.service.InsttService;
import rbs.modules.ntwrk.service.NtwrkExpertService;

/**
 * 지역네트워크 > 관할 전문가 관리
 * 전문가풀와 관계없음
 * @author minjung
 *
 */
@Controller
@ModuleMapping(moduleId="expert", confModule="ntwrk")
@RequestMapping({"/{siteId}/expert", "/{admSiteId}/menuContents/{usrSiteId}/expert"})
public class NtwrkExpertController extends ModuleController {
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
	@Resource(name = "insttService")
	private InsttService insttService;
	
	@Resource(name = "ntwrkExpertService")
	private NtwrkExpertService ntwrkExpertService;

	@Resource(name = "jsonView")
	View jsonView;
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(NtwrkExpertController.class);
	
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
	 * 관할 전문가 관리 목록
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/list.do")
	public String list(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		boolean isAdmMode = attrVO.isAdmMode();
		DataForm queryString = attrVO.getQueryString();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		
		// 0. regiCode(지부지사코드)가 없는 경우에는 볼 수 없음
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if(loginVO.getRegiCode().isEmpty()) {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('사용자의 지부지사 코드를 찾을 수 없습니다.');history.go(-1);</script>");
			w.flush();
			w.close();
		}
		
		// 소속기관 리스트
		Map<String, Object> param = new HashMap<String, Object>();
		List<?> insttList = null;
		insttList = insttService.getList(param);
		model.addAttribute("insttList", insttList);
				
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
		
		// 3. 검색조건
		// 3-1. 항목설정
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
		
		param.put("userType", loginVO.getUsertypeIdx());
		param.put("regiCode", loginVO.getRegiCode());
		param.put("searchList", searchList);
		totalCount = ntwrkExpertService.getExpertTotalCount(param);
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
			list = ntwrkExpertService.getExpertList(param);
		}
		
		// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);
    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		
		fn_setCommonPath(attrVO);
		return getViewPath("/list");
	}
	
	/**
	 * 관할전문가 상세보기
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/view.do", method = RequestMethod.POST)
	public String view(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) {
		// 소속기관 리스트
		Map<String, Object> param = new HashMap<String, Object>();
		List<?> insttList = null;
		insttList = insttService.getList(param);
		model.addAttribute("insttList", insttList);
		
		if(!request.getParameter("idx").isEmpty()) {
			param.put("expertIdx", request.getParameter("idx"));
			// 1. 관할전문가 정보
			Map<String, Object> expert = ntwrkExpertService.getExpertOne(param);
			// 2. 소속기관 목록
			List<Map<String, Object>> partnerInsttList = ntwrkExpertService.getExpertPartnerInstts(param);
			// 3. 파일목록
			List<Map<String, Object>> fileList = ntwrkExpertService.getExpertFileList(param);
			model.addAttribute("expert", expert);
			model.addAttribute("partnerInsttList", partnerInsttList);
			model.addAttribute("fileList", fileList);
		}
		
		return getViewPath("/view");
	}
	
	/**
	 * 관할전문가 입력(insert/update)
	 * @param files
	 * @param formdata
	 * @param fileSeq
	 * @param request
	 * @param attrVO
	 * @param model
	 * @return
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public ModelAndView insert(
			@RequestParam(value = "files", required = false) List<MultipartFile> files, 
			@RequestParam Map<String, Object> formdata, 
			@RequestParam(value = "fileSeq", required = false) List<Integer> fileSeq, 
			@RequestParam(value = "partnerInstt", required = false) List<Integer> partnerInsttList,
			HttpServletRequest request, @ModuleAttr ModuleAttrVO attrVO, ModelMap model) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		formdata.put("REGI_IP", request.getRemoteAddr());
		formdata.put("LAST_MODI_IP", request.getRemoteAddr());
		
		// 전문가 추가 (service단에서 insert인지 update인지 분기)
		Map<String, Object> insertExpertResult = ntwrkExpertService.inserExpert(formdata, partnerInsttList);
		mav.addObject("INSERT_EXPERT_RESULT", insertExpertResult);
		
		// 파일 추가
		if(!files.isEmpty()) {
			// 파일 확장자 체크 (FIX:16914)
			boolean extensionCheck = true;
			for(int i=0;i < files.size();i++) {
				String fileOriginName = files.get(i).getOriginalFilename();
				String fileExtension = fileOriginName.substring(fileOriginName.lastIndexOf(".") + 1);
				if(!isExtensionAllowed(fileExtension)) {
					extensionCheck = false;
					break;
				}
			}
			mav.addObject("extension", extensionCheck);
			
			if(fileSeq == null) fileSeq = new ArrayList<Integer>();
			Map<String, Object> insertExpertFilesResult = ntwrkExpertService.saveExpertFiles(attrVO, formdata, files, fileSeq);
			mav.addObject("INSERT_EXPERT_FILE_RESULT", insertExpertFilesResult);
		}

		return mav;
	}
	
	/**
	 * 관할전문가 선택 삭제하기
	 * @param request
	 * @param values
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Auth(role = Role.D)
	@RequestMapping(value = "/delete.do")
	public ModelAndView delete(HttpServletRequest request, @RequestParam("selectedValues") String values) throws JsonParseException, JsonMappingException, IOException {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		ObjectMapper objectMapper = new ObjectMapper();
		int[] deleteIdxs = objectMapper.readValue(values, int[].class);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("deleteIdxs", deleteIdxs);
		param.put("LAST_MODI_IP", request.getRemoteAddr());
		int result = ntwrkExpertService.deleteExpert(param);
		mav.addObject("result", result);
		return mav;
	}
	
	
	@Auth(role = Role.R)
	@RequestMapping(value = "/downloadExcel.do")
	public String downloadExcel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) {
		DataForm queryString = attrVO.getQueryString();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		Map<String, Object> param = new HashMap<String, Object>();
			
		// 2. DB
		List<?> list = null;
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 3. 검색조건
		// 3-1. 항목설정
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
		
		// 본부 이하인 경우, 전문가의 관할기관이 자신의 관할기관이거나 + 전문가의 소속기관목록에 포함되어있어야 함
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if(loginVO.getUsertypeIdx() < 40) {
			DTForm searchItem = new DTForm();
			searchItem.put("columnId", "VIEW_INSTT_IDX");
			searchItem.put("columnValue", loginVO.getInsttIdx());
			searchList.add(searchItem);
			
		}
		
		param.put("searchList", searchList);
		
		list = ntwrkExpertService.getExpertList(param);
		
		// 3. 속성 setting
    	model.addAttribute("list", list);
		
		fn_setCommonPath(attrVO);
		return getViewPath("/excel");
	}
	
	/**
	 * 관할전문가 엑셀업로드 샘플파일 다운로드
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/downloadSample.do")
	public String downloadSample() {
		return getViewPath("/sample");
	}
	
	/**
	 * 관할전문가 엑셀업로드
	 * @param file
	 * @param request
	 * @param attrVO
	 * @return
	 * @throws IOException 
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/uploadExcel.do")
	public ModelAndView uploadExcel(@RequestParam("excelfile") MultipartFile file, HttpServletRequest request, HttpServletResponse response, @ModuleAttr ModuleAttrVO attrVO) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		// 로그인한 사용자의 지부지사(INSTT_IDX)필요 > 추가하고자 하는 전문가의 소속기관에 추가하기 위함임
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if(loginVO.getInsttIdx() == null || loginVO.getInsttIdx().isEmpty()) {
			mav.addObject("result", "fail");
			mav.addObject("msg", "사용자의 지부지사 코드를 찾을 수 없습니다.");
			return mav;
		}
		String myInsttIdx = "";
		myInsttIdx = loginVO.getInsttIdx();
		
		// 1. 엑셀 내용 DB에 저장 & 결과 엑셀 생성
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(file.getInputStream());
		} catch (InvalidFormatException e1) {
			log.info("ERROR : Exception", e1.getMessage());
		} catch (IOException e) {
			log.info("ERROR : Exception", e.getMessage());
		} catch (Exception e) {
			log.info("ERROR : Exception", e.getMessage());
		}
		
		// 최종 성공시 몇 건을 업로드하였는지 담는 변수
		int result = 0;
		
		if(workbook != null) {
			Sheet sheet = workbook.getSheetAt(0);
			String[] column = {"name", "brthdy", "psitnGrpName", "psitnGrpType", "zip", "addr", "addrDtl", "spcltyRealm", "dept", "ofcps", "mbtlnum", "email", "insttIdx", "remarks"};
			Iterator<Row> rowIterator = sheet.iterator();
			DataFormatter dataFormatter = new DataFormatter();
			
			// 0. 소속기관 비교용 insttList
			Map<String, Object> temp = new HashMap<String, Object>();
			List<Object> insttList = null;
			insttList = insttService.getList(temp);
			
			List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				
				// 제일 첫번째 행은 헤더이므로 건너뜀
				if(row.getRowNum() == 0) continue;
				
				// 한 줄이 비어있으면 종료
				if(isRowEmpty(row)) break;
				
				HashMap<String, Object> map = new HashMap<>();

				for(int i=0;i < row.getLastCellNum(); i++) {
					Cell cell = row.getCell(i);
					
					map.put(column[i], dataFormatter.formatCellValue(row.getCell(i)));
					
					// 전문가명 필수
					if(i == 0 && (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
						mav.addObject("result", "fail");
						mav.addObject("msg", "전문가 이름은 필수입력값입니다." + getCellPosition(i, row.getRowNum()));
						return mav;
					}
					
					// 소속기관은 필수(엑셀:소속기관명 > DB:소속기관식별번호)
					if(column[i].equals("insttIdx")) {
						String insttIdx = "";
						if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
							mav.addObject("result", "fail");
							mav.addObject("msg", "관할기관은 필수입력값입니다." + getCellPosition(i, row.getRowNum()));
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
							mav.addObject("msg", "\"" + insttNameInRow + "\" 는(은) 관할기관이 아닙니다." + getCellPosition(i, row.getRowNum()));
							return mav;
						}
						map.put("insttIdx", insttIdx);
					}
				}
				
				map.put("partnerInsttIdx", myInsttIdx);
				map.put("REGI_IP", request.getRemoteAddr());
				paramList.add(map);
			}
			
			try {
				result = ntwrkExpertService.uploadManyExpertData(paramList);
			} catch(Exception e) {
				mav.addObject("result", "fail");
				mav.addObject("msg", e.getMessage());
				return mav;
			}
		} else {
			mav.addObject("result", "fail");
			mav.addObject("msg", "읽을 수 없는 엑셀파일입니다.");
			return mav;
		}
		
		mav.addObject("result", "success");
		mav.addObject("msg", result + "명의 관할 전문가가 등록되었습니다.");
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
		return "[" + getColumnAlphabet(columnIndex) + (rowIndex + 1) + "]";
	}
		
	// 엑셀의 Row(줄)이 모두 비어있는지 확인하는 함수
	private static boolean isRowEmpty(Row row) {
		for(int i = row.getFirstCellNum();i < row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
			if(cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
				return false;
			}
		}
		return true;
	}
	/******************************************* biz *******************************************/
	
	/**
	 * 기본경로
	 */
	public void fn_setCommonPath(ModuleAttrVO attrVO) {	
		// 모듈명으로 디자인템플릿 폴더 지정
		attrVO.setDesign(attrVO.getModuleId());
		
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
