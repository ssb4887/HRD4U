package rbs.modules.ntwrk.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import rbs.egovframework.LoginVO;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.modules.instt.service.InsttService;
import rbs.modules.memberManage.service.MemberManageService;
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
import com.woowonsoft.egovframework.service.CodeService;
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
 * 지역네트워크 > 협의체 관리(only dct)
 * @author mjpark
 *
 */
@Controller
@ModuleMapping(moduleId="cmptinst", confModule="ntwrk")
@RequestMapping({"/{siteId}/cmptinst", "/{admSiteId}/menuContents/{usrSiteId}/cmptinst"})
public class NtwrkCmptinstController extends ModuleController {
	
	@Value("${Globals.fileStorePath}")
	private String uploadDir;
	
	@Resource(name = "ntwrkService")
	private NtwrkService ntwrkService;
	
	@Resource(name = "ntwrkCmptinstService")
	private NtwrkCmptinstService ntwrkCmptinstService;
	
	@Resource(name = "memberManageService")
	private MemberManageService memberManageService;
	
	@Resource(name = "insttService")
	private InsttService insttService;
	
	@Autowired
	private CodeService codeService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
	@Resource(name = "jsonView")
	View jsonView;
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(NtwrkCmptinstController.class);
	
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
	 * 협의체 목록 불러오기
	 * @param attrVO
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/list.do")
	public String list(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("siteId") String siteId) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu(); 
    	
    	LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
    	
    	if(loginVO == null) {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('접근할 수 없습니다.');history.go(-1);</script>");
			w.flush();
			w.close();
			return getViewPath("/list");
		}
    	
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
		
		// 본부가 아닌 경우, 본인 지부지사의 소속 협의체들만 조획 가능
		param.put("TYPE", "CMPTINST");
		if(loginVO.getUsertypeIdx() < 40) {
			// 본부 미만은 자신의 소속만 조회가능
			param.put("REGI_CODE", loginVO.getRegiCode());
		}
		
		totalCount = ntwrkCmptinstService.getCmptinstTotalCount(param);
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
			list = ntwrkCmptinstService.getCmptinstList(param);
		}
		
		// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		
		// 소속기관 리스트
		List<?> insttList = null;
		insttList = insttService.getList(param);
		model.addAttribute("instt", insttList);
		if(loginVO != null) {
			model.addAttribute("usertype", loginVO.getUsertypeIdx());
		}
		
		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
	
	/**
	 * 협의체 삭제하기(완전삭제X,ISDELETE=1로 업데이트)
	 * @param request
	 * @param values
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.D)
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public ModelAndView delete(HttpServletRequest request, @RequestParam("selectedValues") String values) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		ObjectMapper objectMapper = new ObjectMapper();
		int[] cmptinstIdxs = objectMapper.readValue(values, int[].class);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cmptinstIdxs", cmptinstIdxs);
		param.put("LAST_MODI_IP", request.getRemoteAddr());
		int result = ntwrkCmptinstService.deleteCmptinst(param);
		mav.addObject("result", result);
		return mav;
	}
	
	/**
	 * 협의체 목록을 엑셀로 업로드하기
	 * @param file
	 * @param excelfile
	 * @return
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/uploadExcel.do", method = RequestMethod.POST)
	public ModelAndView uploadExcel(@RequestParam("excelfile") MultipartFile file, HttpServletRequest request, @ModuleAttr ModuleAttrVO attrVO) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		// 2. 엑셀 내용 DB에 저장 & 결과 엑셀 생성
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
			String[] column = {"cmptinstName", "insttName", "cmptinstReperNm", "cmptinstPicName", "cmptinstPicTelno", "cmptinstPicEmail", "zip", "addr", "addrDtl", "remarks"};
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
					
					// 협의체명 필수
					if(i == 0 && (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)) {
						mav.addObject("result", "fail");
						mav.addObject("msg", "교육명은 필수입력값입니다." + getCellPosition(i, row.getRowNum()));
						return mav;
					}
					
					// 소속기관은 필수(엑셀:소속기관명 > DB:소속기관식별번호)
					if(i == 1) {
						String insttIdx = "";
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
					}
					
					map.put(column[i], dataFormatter.formatCellValue(row.getCell(i)));
				}
				
				map.put("REGI_IP", request.getRemoteAddr());
				map.put("cmptinstType", "CMPTINST"); // 협의체
				paramList.add(map);
			}
			
			try {
				result = ntwrkCmptinstService.cmptinstDataUpload(paramList);
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
		mav.addObject("msg", result + "건의 협의체가 등록되었습니다.");
		return mav;
	}
	
	@Auth(role = Role.C)
	@RequestMapping(value = "/uploadAgremExcel.do", method = RequestMethod.POST)
	public ModelAndView uploadAgremExcel(@RequestParam("excelfile") MultipartFile file, HttpServletRequest request, @ModuleAttr ModuleAttrVO attrVO) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		// 0. 소속기관 비교용 insttList
		Map<String, Object> param = new HashMap<String, Object>();
		List<Object> insttList = null;
		insttList = insttService.getList(param);
		StringBuilder insttNames = new StringBuilder();
		insttNames = getInsttNameInList(insttList);
		
		// 1. 엑셀 내용 DB에 저장
		List<Map<String, Object>> errors = new ArrayList<Map<String, Object>>();
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
			String[] column = {"insttName", "agremNo", "bplNo", "cmpnyNm", "agremYear", "mberId", "agremYn", "agremSe", "bizrNo", "agremCnclsDe"};
			Iterator<Row> rowIterator = sheet.iterator();
			DataFormatter dataFormatter = new DataFormatter();
			
			int insertedCount = 0;
			// Excel Row 돌면서 저장할 map 만들기
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				
				// 첫행은 헤더이므로 생략
				if(row.getRowNum() == 0) continue;
				
				Map<String, Object> map = new HashMap<>(); // DB에 저장할 map
				Map<String, Object> error = new HashMap<>(); // 에러 담아서 클라이언트에 제공
				
				for(int i=0;i < row.getLastCellNum(); i++) {
					Cell cell = row.getCell(i);
					// INSTT_IDX
					String insttIdx = "";
					if(i == 0 && cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
						String insttNameInRow = dataFormatter.formatCellValue(cell);
						for(Object instt: insttList) {
							if(instt instanceof Map) {
								Map<?, ?> instance = (Map<?, ?>) instt;
								if(instance.get("INSTT_NAME").equals(insttNameInRow)) {
									insttIdx = instance.get("INSTT_IDX").toString();
								}
							}
						}
						if(insttIdx.isEmpty()) {
							String cellPosition = getCellPosition(i, row.getRowNum());
							error.put("(" + i + ") INSTT", "위치:" + cellPosition + "셀의 값은 소속기관에 속하지 않습니다.(소속기관:" + insttNames + ")");
						}
						map.put("insttIdx", insttIdx);
					}
					map.put("cmptinstIdx", request.getParameter("cmptinstIdx"));
					map.put(column[i], dataFormatter.formatCellValue(cell));
				}
				map.put("REGI_IP", request.getRemoteAddr());
				insertedCount += ntwrkCmptinstService.insertAgremCorpData(map);
				if(!error.isEmpty()) errors.add(error);
			}
			
			mav.addObject("result", insertedCount);
			mav.addObject("errors", errors);
		}
		
		return mav;
	}
	
	/**
	 * 협의체 목록 혹은 엑셀 다운로드
	 * @throws Exception 
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/downloadExcel.do")
	public String downloadExcel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model, HttpServletResponse response) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		
		// 샘플다운로드
		String sample = request.getParameter("sample");
		if(sample != null && !sample.isEmpty()) {
			return getViewPath("/sample" + sample);
		}
    	
    	LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
    	
    	// 0. regiCode(지부지사코드)가 없는 경우에는 볼 수 없음
    	if(loginVO == null) {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('다운로드 받을 수 없습니다.');history.go(-1);</script>");
			w.flush();
			w.close();
		}
    	
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
		
		param.put("TYPE", "CMPTINST");

		// 본부가 아닌 경우, 본인 지부지사의 소속 협의체들만 조획 가능
		if(loginVO != null && loginVO.getUsertypeIdx() < 40) {
			// 본부 미만은 자신의 소속만 조회가능
			param.put("INSTT", loginVO.getInsttIdx());
		}
		
		// 정렬컬럼 setting
		param.put("dsetOrderField", JSONObjectUtil.getString(settingInfo, "dset_order_field"));
		
		// 2.3 목록
		List<?> list = ntwrkCmptinstService.getCmptinstList(param);
		
		// 3. 속성 setting
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/excel");
	}
	
	/**
	 * 협의체 상세보기
	 * @param attrVO
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/view.do", method = RequestMethod.POST)
	public String viewPost(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) {
		Map<String, Object> param = new HashMap<String, Object>();
		String cmptinstIdx = request.getParameter("idx");
		if(cmptinstIdx != null) {
			String type = "CMPTINST";
			param.put("cmptinstIdx", cmptinstIdx);
			param.put("type", type);
			
			Map<String, Object> result = ntwrkCmptinstService.getCmptinstOne(param);
			model.addAttribute("result", result);
		}
		
		// 본부일 경우 소속기관 변경할 수 있도록 표시
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if(loginVO.getUsertypeIdx() > 30) {
			List<?> insttCodeList = memberManageService.getCodeList();
			model.addAttribute("insttCodeList", insttCodeList);
		}
		model.addAttribute("myInsttIdx", loginVO.getInsttIdx());
		String myInsttName = codeService.getOptnName("ORG_CODE", loginVO.getInsttIdx());
		model.addAttribute("myInsttName", myInsttName);
		
		// 네트워크 이력 불러와서 뿌리기
		List<Map<String,Object>> ntwrkList = ntwrkService.getNtwrkListByCmptinst(param);
		model.addAttribute("ntwrkList", ntwrkList);
		
		// 협의체의 경우, 협약 기업 불러오기
		List<?> agremCorpList = ntwrkCmptinstService.getAgremCorpList(param);
		model.addAttribute("agremCorpList", agremCorpList);
		return getViewPath("/view");
	}
	
	/**
	 * 협의체 등록 혹은 수정(insert/update)
	 * @param formdata
	 * @param request
	 * @return
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	public ModelAndView insert(@RequestParam Map<String, Object> formdata, HttpServletRequest request) {
		formdata.put("REGI_IP", request.getRemoteAddr());
		formdata.put("LAST_MODI_IP", request.getRemoteAddr());
		formdata.put("cmptinstType", "CMPTINST");
		
		int success = ntwrkCmptinstService.setCmptinstData(formdata);
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		mav.addObject("success", success);
		mav.addObject("idx", formdata.get("cmptinstIdx"));
		mav.addObject("flag", formdata.get("FLAG"));
		return mav;
	}
	
	/**
	 * 협약기업 등록(insert)
	 * @param formdata
	 * @param request
	 * @return
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/insertAgremCorp.do", method = RequestMethod.POST)
	public ModelAndView insertAgremCorp(@RequestParam Map<String, Object> formdata, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		formdata.put("REGI_IP", request.getRemoteAddr());
		int result = ntwrkCmptinstService.insertAgremCorpData(formdata);
		mav.addObject("result", result);
		return mav;
	}
	
	/**
	 * 협약기업 선택삭제
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Auth(role = Role.D)
	@RequestMapping(value = "/deleteAgremCorp.do", method = RequestMethod.POST)
	public ModelAndView deleteAgremCorp(HttpServletRequest request, @RequestParam("selectedValues") String values) throws JsonParseException, JsonMappingException, IOException {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		ObjectMapper objectMapper = new ObjectMapper();
		int[] agremCorpIdxs = objectMapper.readValue(values, int[].class);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("agremCorpIdxs", agremCorpIdxs);
		param.put("LAST_MODI_IP", request.getRemoteAddr());
		int result = ntwrkCmptinstService.deleteAgremCorps(param);
		mav.addObject("result", result);
		return mav;
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
	
	private StringBuilder getInsttNameInList(List<Object> list) {
		StringBuilder result = new StringBuilder();
		for(Object obj : list) {
			if(obj instanceof Map) {
				Map<?, ?> map = (Map<?, ?>) obj;
				Object insttName = map.get("INSTT_NAME");
				if(insttName != null) {
					result.append(insttName).append(", ");
				}
			}
		}
		
		// 마지막 ", "를 제거
		if(result.length() > 0) {
			result.setLength(result.length() - 2);
		}
		
		return result;
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
