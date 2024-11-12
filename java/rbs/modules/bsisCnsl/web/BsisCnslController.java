package rbs.modules.bsisCnsl.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import rbs.egovframework.LoginVO;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.modules.basket.dto.Basket;
import rbs.modules.basket.dto.Criteria;
import rbs.modules.basket.service.BasketService;
import rbs.modules.bsisCnsl.service.BsisCnslService;
import rbs.modules.develop.service.DevelopService;
import rbs.modules.diagnosis.service.DiagnosisService;
import rbs.modules.instt.service.InsttService;
import rbs.modules.recommend.PrtbizVO;
import rbs.modules.recommend.RecommendDummyService;
import rbs.modules.recommend.DoctorVO;
import rbs.modules.recommend.QrVO;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * HRD기초컨설팅 모듈
 * ㄴ 행정 프로세스 : 기초진단(diagnosis) > 설문조사(기업 훈련수요 설문) > HRD기초컨설팅(bsiscnsl)
 * @author minjung
 *	기초진단 IDX 	: BSC_IDX
 *	설문조사 IDX 	: RSLT_IDX
 *	기초컨설팅 IDX	: BSISCNSL_IDX
 */
@Controller
@ModuleMapping(moduleId="bsisCnsl")
@RequestMapping({"/{siteId}/bsisCnsl", "/{admSiteId}/menuContents/{usrSiteId}/bsisCnsl"})
public class BsisCnslController extends ModuleController {
	
	@Resource(name = "bsisCnslService")
	private BsisCnslService bsisCnslService;
	
	@Resource(name = "developService")
	protected DevelopService developService;
	
	@Resource(name = "basketService")
	private BasketService basketService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
	@Resource(name = "jsonView")
	View jsonView;
	
	@Autowired
	RecommendDummyService recommendService;
	
	@Autowired
	DiagnosisService diagnosisService;
	
	@Autowired
	InsttService insttService;
	
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
	public String list(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("siteId") String siteId) throws Exception {
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
		
		
		if(siteId.equals("dct")) {
			// dct
			LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
			
			if(loginVO == null) {
				request.setAttribute("msg", "로그인이 필요합니다.");
				request.setAttribute("url", "/dct/login/login.do?mId=3");
				return getViewPath("/alert");
			} else if(loginVO.getUsertypeIdx() == 40 || loginVO.getUsertypeIdx() == 30 || loginVO.getUsertypeIdx() == 55) {
				param.put("login", loginVO);
				totalCount = bsisCnslService.getTotalCount(param);
				paginationInfo.setTotalRecordCount(totalCount);
				
				// 소속기관 리스트
				Map<String, Object> temp = new HashMap<String, Object>();
				List<?> insttList = null;
				insttList = insttService.getList(temp);
				model.addAttribute("insttList", insttList);
				model.addAttribute("usertype", loginVO.getUsertypeIdx());
				
				if(totalCount > 0) {
					if(usePaging == 1) {
						param.put("firstIndex", paginationInfo.getFirstRecordIndex());
						param.put("lastIndex", paginationInfo.getLastRecordIndex());
						param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
					}
					
					// 정렬컬럼 setting
					param.put("dsetOrderField", JSONObjectUtil.getString(settingInfo, "dset_order_field"));
					
					// 2.3 목록
					list = bsisCnslService.getList(param);
				}
				
			} else {
				request.setAttribute("msg", "접근 권한이 없습니다.");
				request.setAttribute("url", "/web/main/main.do?mId=1");
				return getViewPath("/alert");
			}
			
			
		} else {
			// web
			LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
			
			String bplNo = "";
			if(loginVO != null && loginVO.getUsertypeIdx() < 20) {
				bplNo = loginVO.getBplNo();
				
				if(bplNo == null || bplNo.isEmpty())  {
					response.setContentType("text/html; charset=utf-8;");
					PrintWriter w = response.getWriter();
					w.write("<script>alert('사업장관리번호가 없습니다. 마이페이지에서 확인해주세요.');history.go(-1);</script>");
					w.flush();
					w.close();
					return getViewPath("/list");
				}
				
				param.put("bplNo", bplNo);
				model.addAttribute("bplNo", bplNo);
				model.addAttribute("login", true);
				if("".equals(bplNo)) return getViewPath("/list");
			} else if(loginVO == null){
				model.addAttribute("login", false);
				return getViewPath("/list");
			}
			
			// 민간센터는 협약맺은 기업에 한해 조회가능
			if(loginVO.getUsertypeIdx() == 20) {
				Criteria cri = new Criteria(1,100);
				String memberIdx = loginVO.getMemberIdx();
				cri.setMemberId(memberIdx);
				// 협약맺은 기업 목록
				List<Basket> bizList = basketService.getWebList(cri);
				model.addAttribute("bizList", bizList);
				
				// 검색조건 : 협약기업
				String bplNoForQuery = "";
				if(request.getParameter("is_bplNo") != null && !request.getParameter("is_bplNo").isEmpty()) {
					String bplNoParam = request.getParameter("is_bplNo");
					
					// 협약되지 않은 기업 검색 방지
					boolean isBplNoExists = false;
					for(Basket biz : bizList) {
						if(biz.getBplNo() != null && biz.getBplNo().equals(bplNoParam)) {
							isBplNoExists = true;
							break;
						}
					}
					
					if(!isBplNoExists) {
						response.setContentType("text/html; charset=utf-8;");
						PrintWriter w = response.getWriter();
						w.write("<script>alert('협약되지 않은 기업입니다. 검색조건을 다시 확인해주세요.');history.go(-1);</script>");
						w.flush();
						w.close();
						return getViewPath("/list");
					}
					
					bplNoForQuery = "('" + request.getParameter("is_bplNo") + "')";
				} else {
					List<String> bplNoList = new ArrayList<>();
					for(Basket biz : bizList) {
						bplNoList.add(biz.getBplNo());
					}
					StringBuilder sb = new StringBuilder();
					sb.append("('");
					sb.append(joinListToString(bplNoList, "', '"));
					sb.append("')");
					bplNoForQuery = sb.toString();

				}
				
				DTForm map1 = new DTForm();
				map1.put("columnId", "BPL_NO");
				map1.put("columnValue", bplNoForQuery);
				searchList.add(map1);
			}
			
			totalCount = bsisCnslService.getMyTotalCount(param);
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
				list = bsisCnslService.getMyList(param);
			}
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
	 * 목록조회
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value = "/init.do")
	public String init(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String bplNo = "";
		if(loginVO != null) {
			bplNo = loginVO.getBplNo();
			if(loginVO.getUsertypeIdx() < 10 && (bplNo == null || bplNo.isEmpty()))  {
				response.setContentType("text/html; charset=utf-8;");
				PrintWriter w = response.getWriter();
				w.write("<script>alert('사업장관리번호가 없습니다. 마이페이지에서 확인해주세요.');history.go(-1);</script>");
				w.flush();
				w.close();
				return getViewPath("/init");
			}
			
			param.put("bplNo", bplNo);
			model.addAttribute("login", true);
			model.addAttribute("userType", loginVO.getUsertypeIdx());
			model.addAttribute("bplNo", bplNo);
			if("".equals(bplNo)) return getViewPath("/init");
		} else {
			model.addAttribute("login", false);
			return getViewPath("/init");
		}
		
		// 2.2 목록수
    	totalCount = bsisCnslService.getBscInitTotalCount(param);
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
    		list = bsisCnslService.getBscInitList(param);
		}


    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/init");
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
	public String view(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		// 1. 필수 parameter 검사
		String keyIdx = request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name"));
		if(StringUtil.isEmpty(keyIdx)) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		String idxColumnName = JSONObjectUtil.getString(settingInfo, "idx_column");

		
		// 2. DB
		DataMap bsc = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		List<?> fundHis = null;
		List<?> trHis = null;
		List<?> prtbiz = null;

		/*// 전체관리권한여부
		Boolean isMngAuth = AuthHelper.isAuthenticated("MNG");		// 전체관리
		if(!isMngAuth){
			LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
			String loginMemberIdx = null;
			if(loginVO != null){
				loginMemberIdx = loginVO.getMemberIdx();
			}
			param.put("AUTH_MEMBER_IDX", loginMemberIdx);
		}*/
		
		searchList.add(new DTForm(idxColumnName, keyIdx));
		
		param.put("searchList", searchList);

		// 상세정보
		param.put("BSC_IDX", keyIdx);
		bsc = diagnosisService.getBsc(param);
		fundHis = diagnosisService.getFundHis(param);
		trHis = diagnosisService.getTrHis(param);
		prtbiz = diagnosisService.getPrtbiz(param);
		List<QrVO> qr = recommendService.getQR();
		
//		if(param == null) {
//			// 해당글이 없는 경우
//			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
//			return RbsProperties.getProperty("Globals.fail.path");
//		}

		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "view";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		// 3.2 속성 setting
		model.addAttribute("bsc", bsc);																				// 상세정보
		model.addAttribute("fundHis", fundHis);																				// 상세정보
		model.addAttribute("trHis", trHis);																				// 상세정보
		model.addAttribute("prtbiz", prtbiz);																				// 상세정보
		model.addAttribute("qrs", qr);
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));							// 항목코드
		model.addAttribute("submitType", submitType);																// 페이지구분
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	/**
	 * 설문조사지 상세조회
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(method = RequestMethod.POST, value = "/qustnr.do")
	public ModelAndView qustnr(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		ModelAndView mav = new ModelAndView();
		JSONObject settingInfo = attrVO.getSettingInfo();
		
		
		// 2. DB
		List<?> qustnr = null;
		List<?> answer = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();

		// 1. 필수 parameter 검사
		String rsltIdx = request.getParameter(JSONObjectUtil.getString(settingInfo, "rslt_idx_name"));
		String qustnrIdx = request.getParameter(JSONObjectUtil.getString(settingInfo, "qustnr_idx_name"));
		String bscIdx = request.getParameter(JSONObjectUtil.getString(settingInfo, "bsc_idx_name"));
		if(StringUtil.isEmpty(bscIdx)) {
			mav.addObject("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			mav.setViewName(RbsProperties.getProperty("Globals.fail.path"));
			return mav;
		}
		if(!StringUtil.isEmpty(rsltIdx)) param.put("rsltIdx", rsltIdx);	
		else rsltIdx = "";
		
		
		param.put("searchList", searchList);
		
		// 설문조사지(UI) SELECT
		qustnr = bsisCnslService.getQustnrView(param);
		
		// 불러올 설문조사가 없는 경우 예외처리
		if(qustnr == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			mav.setViewName(RbsProperties.getProperty("Globals.fail.path"));
			return mav;
		}
		
		// 설문조사결과(ANSWER) SELECT
		answer = bsisCnslService.getQustnrAnswer(param);

		// 3.2 속성 setting
		mav.addObject("qustnr", qustnr);
		mav.addObject("answer", answer);
		mav.addObject("rsltIdx", rsltIdx);
		mav.addObject("qustnrIdx", qustnrIdx);
		mav.addObject("bscIdx", bscIdx);
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	mav.setViewName(getViewPath("/qustnr"));
    	return mav;
	}
	
	
	/**
	 * 설문조사지 상세조회
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(method = RequestMethod.GET,value = "/qustnrData.do")
	public ModelAndView qustnData(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		List<?> qustnr = null; // 설문지
		List<?> answer = null; // 설문답변
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 질문지
		String rsltIdx = request.getParameter("rsltIdx");
		if(!StringUtil.isEmpty(rsltIdx)) {
			param.put("rsltIdx", rsltIdx);	
		}
		
		param.put("searchList", searchList);
		qustnr = bsisCnslService.getQustnrView(param);
		answer = bsisCnslService.getQustnrAnswer(param);
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		mav.addObject("qustnr", qustnr);
		mav.addObject("answer", answer);
		mav.addObject("rsltIdx", rsltIdx);
		return mav;
	}
	
	@Auth(role = Role.C)
	@ModuleAuth(name="VEW")
	@RequestMapping(method = RequestMethod.POST, value = "/insertQustnr.do")
	@ResponseBody
	public ModelAndView insertQustnr(MultipartHttpServletRequest req, HttpServletRequest request, ModelMap model) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		// formData
		Map<String, String[]> params = req.getParameterMap();
		Boolean flag = true; // true: update , false: insert
		
		// 1. HRD_QUSTNR_RSLT 저장(insert || update)
		Map<String, Object> rsltParam = new HashMap<String, Object>();
		if(!params.get("rsltIdx")[0].isEmpty()) {
			rsltParam.put("rsltIdx", params.get("rsltIdx")[0]);
			flag = true;
		} else {
			flag = false;
		}
		
		if(!params.get("bscIdx")[0].isEmpty()) {
			rsltParam.put("bscIdx", params.get("bscIdx")[0]);
		} else {
			// 기초진단 식별색인(bscIdx)가 없으면 설문 불가능. view단에서 alert후 뒤로가기 처리
			mav.addObject("fail", "BSC");
			mav.addObject("msg", "empty bsc index");
			return mav;
		}
		
		rsltParam.put("qustnrIdx", params.get("qustnrIdx")[0]);
		rsltParam.put("status", params.get("status")[0]);
		
		if(!flag) {
			int isExists = bsisCnslService.getRsltIdx(rsltParam);
			if(isExists > 0) {
				mav.addObject("fail", "CHK");
				mav.addObject("msg", "is already checked");
				return mav;
			}
		}
		
		int result = bsisCnslService.setQustnrResult(rsltParam, request.getRemoteAddr());
		
		// 2. HRD_QUSTNR_RSLT_ANSWER 저장(insert || update)
		List<Map<String, Object>> answers = new ArrayList<Map<String, Object>>();
		for(Map.Entry<String, String[]> entry: params.entrySet()) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			for(String value : values) {
				if(tryParseToInt(key)) {
					Map<String, Object> map = new HashMap<String, Object>();
					if(params.get("rsltIdx")[0].isEmpty()) {
						map.put("rsltIdx", rsltParam.get("rsltIdx"));
					} else {
						map.put("rsltIdx", params.get("rsltIdx")[0]);
					}
					map.put("qustnrIdx", params.get("qustnrIdx")[0]);
					map.put("questionIdx", key);
					map.put("answer", value);
					answers.add(map);
				}
			}
		}
		
		Comparator<Map<String, Object>> comparator = new AnswerComparator();
		Collections.sort(answers, comparator);
		
		
		if(flag) {
			// update
			String answerJson = req.getParameter("answers");
			ObjectMapper objectMapper = new ObjectMapper();
			List<Map> readValue = objectMapper.readValue(answerJson, List.class);
			for(int i=0;i < readValue.size();i++) {
				answers.get(i).put("answerIdx", readValue.get(i).get("ANSWER_IDX"));
			}
			
			result = bsisCnslService.updateQustnrAnswers(answers);
		} else {
			// insert
			result = bsisCnslService.insertQustnrAnswers(answers);
		}
		
		mav.addObject("result", result);
		mav.addObject("param", rsltParam);
		
		return mav;
	}
	
	
	/**
	 * HRD기초컨설팅 상세조회
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(method = RequestMethod.POST, value = "/cnslt.do")
	public String cnslt(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		long rslt_idx = Long.parseLong(request.getParameter("rslt"));
		String bscIdx = request.getParameter("bsc");
		model.addAttribute("BSC_IDX", bscIdx);
		// 이 번호를 쓰는게 있는지 일단 한번 확인하고 ... 있으면 보여주고 없으면 저장한다.
		Map basket = recommendService.getBsisByRslt(rslt_idx);
		Map corppic = recommendService.getCorpPIC(rslt_idx);
		model.addAttribute("RSLT_IDX", rslt_idx);
		model.addAttribute("corpPIC", corppic);
		if(basket != null) {
			model.addAttribute("basket", basket);
			long bsiscnsl_idx = Long.valueOf(String.valueOf(basket.get("BSISCNSL_IDX")));
			consultKnown(model, bsiscnsl_idx);
			model.addAttribute("BSISCNSL_IDX", bsiscnsl_idx);
		} else {
			HttpSession session = request.getSession();
			String memberidx = null;
			if(session != null) {
				LoginVO loginVO = (LoginVO) session.getAttribute("loginVO");
				memberidx = loginVO.getMemberIdx();
			}
			consultStranger(model, rslt_idx, memberidx);
			// 가정: 설문조사로 이미 생성한 경우 기업 정보, 최근 훈련 이력, 최근 훈련 실시 동향, 최근 훈련 지원 동향은 변하지 않는다.
			// MERGE 없이 INSERT만 함.
			insertBSISCNSL(model.asMap(), rslt_idx, request.getRemoteAddr());
			basket = recommendService.getBsisByRslt(rslt_idx);
			long bsiscnsl_idx = Long.valueOf(String.valueOf(basket.get("BSISCNSL_IDX")));
			model.addAttribute("BSISCNSL_IDX", bsiscnsl_idx);
			model.addAttribute("basket", basket);
		}
		List<String> instts = recommendService.getInsttList();
		model.addAttribute("instt", instts);
		// 기본경로
    	fn_setCommonPath(attrVO);
    	return getViewPath("/cnslt");
	}
	
	@Auth(role = Role.R)
	@RequestMapping(value="/recommends.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String recommends(HttpServletRequest req) throws JsonProcessingException {
		long RSLT_IDX = Long.valueOf(req.getParameter("RSLT_IDX"));
		List<Map> names = recommendService.basicBizNames(RSLT_IDX);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(names);
		return jsonString;
	}
	
	@Auth(role = Role.R)
	@RequestMapping(value="/ncscodes.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String ncscodes() throws JsonProcessingException {
		List<Map> codes = recommendService.getNCSCODES();
		List<Map> biz = recommendService.getPRTBIZ();
		Map<String, List<Map>> result = new HashMap<String, List<Map>>();
		result.put("codes", codes);
		result.put("biz", biz);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(result);
		return jsonString;
	}
	
	@Auth(role = Role.R)
	@RequestMapping(value="/trends.do", method=RequestMethod.POST)
	@ResponseBody
	public String trends(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException {
		String BPL_NO = req.getParameter("BPL_NO");
		Map basket = recommendService.getBSK(BPL_NO);
		basket.put("RANGE", getRange(basket));
		List<Map> trends = recommendService.getTrends(basket);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(trends);
		return jsonString;
		
	}
	
	@Auth(role = Role.R)
	@RequestMapping(value="/trsearch.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String trsearch(@ModelAttribute PrtbizVO prtbizVO) throws JsonProcessingException {
		List<Map> tps = recommendService.getPRTBIZTP(prtbizVO);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(tps);
		return jsonString;
	}
	
	@Auth(role = Role.R)
	@RequestMapping(value="/saveConsult.do", method=RequestMethod.POST)
	public void saveConsult(@RequestBody String json, HttpServletRequest req, HttpServletResponse res) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map data_ = mapper.readValue(json, Map.class);
		// update 해야지
		int status = "temp".equals(data_.get("temp")) ? 0 : 1;
		data_.put("status", status);
		recommendService.updateBsiscnsl(data_, req.getRemoteAddr());
		res.setStatus(200);
	}
	
	@Auth(role = Role.R)
	@RequestMapping(value="/recomBiz.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String recomBiz(HttpServletRequest req) throws JsonProcessingException {
		long bsiscnsl_idx = Long.valueOf(req.getParameter("bsiscnsl_idx"));
		List<Map> names = recommendService.bsisRecom(bsiscnsl_idx);
		for(Map name : names) {
			if(name.get("INTRO") != null) {
				name.put("INTRO", StringEscapeUtils.unescapeHtml4(name.get("INTRO").toString()));
			}
			if(name.get("CONSIDER") != null) {
				name.put("CONSIDER", StringEscapeUtils.unescapeHtml4(name.get("CONSIDER").toString()));
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(names);
		return jsonString;
	}

	/**
	 * 클립리포트 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(value = "/clipReport.do")
	public String clipReport(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String bscIdx = request.getParameter("bscIdx");
		
    	// 5. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	// 기초진단지 번호 setting
    	request.setAttribute("BSC_IDX", bscIdx);
    	
    	return getViewPath("/clipReport");
	}
	

	/**
	 * 클립리포트 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(value = "/consultingClipReport.do")
	public String clipReportConsult(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String bsiscnslIdx = request.getParameter("bsiscnslIdx");
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if(loginVO == null) {
			model.addAttribute("msg", "로그인 정보가 없습니다.");
			return getViewPath("/alert");
		}
		
		if(loginVO.getUsertypeIdx() < 1) {
			model.addAttribute("msg", "권한이 없습니다.");
			return getViewPath("/alert");
		}
		
		Map<String, Object> param = new HashMap<>();
		param.put("bsiscnslIdx", bsiscnslIdx);
		
		Map<String, Object> result = new HashMap<>();
		result = bsisCnslService.getBsisCnslByIdx(param);
		
		// 기초컨설팅이 없는 경우
		if(result == null || result.isEmpty()) {
			model.addAttribute("msg", "기업HRD이음컨설팅이 존재하지 않습니다.");
			return getViewPath("/alert");
		}
 
		boolean isAccessible = false;
		String msg = "권한이 없습니다.";
		String myBplNo = loginVO.getBplNo();
		String bsisCnslBplNo = result.get("BPL_NO").toString();
		if(loginVO.getUsertypeIdx() < 10) {
			// 기업회원은 자기자신것만 조회가능(bplNo)
			// 사업장관리번호없이 조회 불가
			if(myBplNo == null || myBplNo.isEmpty()) {
				msg = "본인의 사업장관리번호가 등록되어있는지 확인해주세요.";
			} else if (bsisCnslBplNo == null || bsisCnslBplNo.isEmpty()) {
				msg = "기업HRD이음컨설팅의 사업장관리번호가 등록되어있지 않습니다. 관리자에게 문의해주세요.";
			} else if (myBplNo.equals(bsisCnslBplNo)){
				isAccessible = true;
			}
			
		} else if(loginVO.getUsertypeIdx() < 20) {
			// 컨설턴트는 조회불가 (혹시 사업장관리번호있으면 조회가능)
			if(myBplNo == null || myBplNo.isEmpty()) {
				msg = "컨설턴트 회원은 조회할 수 없습니다. 기업회원 권한으로 로그인 해주세요.";
			} else if(myBplNo.equals(bsisCnslBplNo)) {
				isAccessible = true;
			}
			
		} else if(loginVO.getUsertypeIdx() < 30) {
			// 민간센터는 협약된 기업만 조회가능(bizList)
			// 협약맺은 기업 목록
			Criteria cri = new Criteria(1,100);
			String memberIdx = loginVO.getMemberIdx();
			cri.setMemberId(memberIdx);
			List<Basket> bizList = basketService.getWebList(cri);
			
			// 협약되지 않은 기업은 조회불가
			boolean isBplNoExists = false;
			for(Basket biz : bizList) {
				if(biz.getBplNo() != null && biz.getBplNo().equals(bsisCnslBplNo)) {
					isBplNoExists = true;
					break;
				}
			}
			
			if(!isBplNoExists) {
				msg = "협약맺은 기업이 아닙니다.";
			} else {
				isAccessible = true;
			}
			
		} else if(loginVO.getUsertypeIdx() < 40) {
			// 소속기관 직원은 본인지부지사만 조회가능
			String insttIdx = loginVO.getInsttIdx(); // 주치의 소속기관 식별번호
			String bsiscnslInsttIdx = result.get("BSISCNSL_INSTT_IDX").toString(); 		// 기초컨설팅 소속기관 식별번호 
			
			if(insttIdx == null || insttIdx.isEmpty()) {
				msg = "로그인한 사용자의 소속기관을 알 수 없습니다. 관리자에게 문의하세요.";
			} else if(bsiscnslInsttIdx == null || bsiscnslInsttIdx.isEmpty()) {
				msg = "해당 기업HRD이음컨설팅의 담당 소속기관을 알 수 없습니다. 관리자에게 문의하세요.";
			} else if(insttIdx.equals(bsiscnslInsttIdx)) {
				isAccessible = true;
			} else {
				msg = "조회 권한이 없습니다. 해당 기초컨설팅의 담당 소속기관만 조회 가능합니다.";
			}
			
		} else if(loginVO.getUsertypeIdx() >= 40) {
			// 본부직원 이상은 전체 조회가능
			isAccessible = true;
		}
		
		if(loginVO != null && !isAccessible) {
			model.addAttribute("msg", msg);
			return getViewPath("/alert");
		}
		
    	// 5. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	// 기초진단지 번호 setting
    	request.setAttribute("BSISCNSL_IDX", bsiscnslIdx);
    	
    	return getViewPath("/consultingClipReport");
	}
	
	@Auth(role = Role.U)
	@RequestMapping(value="/updateMax.do", method=RequestMethod.POST)
	public void updateMax(@RequestBody String json, HttpServletRequest req, HttpServletResponse res) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data_ = mapper.readValue(json, Map.class);
		List<Map> inputs = new ArrayList<Map>();
		String idx = (String) data_.get("bsis_idx");
		for(Map.Entry<String, Object> entry : data_.entrySet()) {
			if(entry.getKey().contains("actualNumber")) {
				String year = entry.getKey().split("_")[1];
				long fund_limit = Long.valueOf(String.valueOf(entry.getValue()));
				Map input_ = new HashMap();
				input_.put("YEAR", year);
				input_.put("FUND_LIMIT", fund_limit);
				input_.put("IDX", idx);
				inputs.add(input_);
			}
		}
		recommendService.updateMax(inputs, "BSIS");
		res.setStatus(200);
	}
	
	@Auth(role = Role.R)
	@RequestMapping(value="/docsearch.do", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String docsearch(@ModelAttribute DoctorVO docVO) throws JsonProcessingException {
		boolean no_empty_params = docVO.getInsttName() != null || docVO.getDoctorName() != null;
		List<Map> result = new ArrayList<Map>();
		if(no_empty_params) {
			result = recommendService.searchDoc(docVO);
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(result);
		return jsonString;
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
		
		// 사용자정보 파라미터에 추가
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		param.put("login", loginVO);

		// 2.3 목록
		list =  bsisCnslService.getList(param);
    	
    	// 3. 속성 setting
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("searchOptnHashMap", CodeHelper.getItemOptnSearchHashMap(listSearch));	// 항목 코드
		model.addAttribute("optnHashMap", optnHashMap);
    	
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
		
		return getViewPath("/excel");
	}
	
	/**
	 * 관리대장 엑셀 다운로드
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(value = "/excelForAdmin.do")
	public String excelForAdmin(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if(loginVO == null || !loginVO.isLogin() || loginVO.getUsertypeIdx() < 40) {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('권한이 없습니다.');history.go(-1);</script>");
			w.flush();
			w.close();
			return getViewPath("/list");
		}
		
		if(request.getParameter("date") != null && !request.getParameter("date").isEmpty()) {
			Map<String, Object> param = new HashMap<>();
			param.put("date", request.getParameter("date"));
			List<?> list = bsisCnslService.getListForAdmin(param);
			model.addAttribute("list", list);
			return getViewPath("/excelForAdmin");
		} else {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('기준 날짜가 입력되지 않았습니다.다시 시도해주세요.');history.go(-1);</script>");
			w.flush();
			w.close();
			return getViewPath("/list");
		}
	}
	
	/**
	 * 추천개발과정 상세보기
	 * @return
	 * @throws Exception 
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/traing.do")
	public String getTraingView(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		HashMap<String, Object> param = new HashMap<>();
		
		// 1. 필수 parameter
		String tpIdx = "";
		int prtbizIdx = 0;
		tpIdx = StringUtil.getString(request.getParameter("tpIdx"));
		prtbizIdx = StringUtil.getInt(request.getParameter("prtbizIdx"));
		
		if(tpIdx == null) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}else if(prtbizIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		param.put("TP_IDX", tpIdx);
		param.put("PRTBIZ_IDX", prtbizIdx);
		
		// 2. 세부내용
		// 과정 정보 , 과정 상세정보 리스트
		DataMap tpList = null;
		List<Object> tpSubList = null;
		tpList = developService.getTpInfo(param);
		tpSubList = developService.getTpSubInfo(param);
		
		if(tpList == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		model.addAttribute("tpList", tpList);
		model.addAttribute("tpSubList", tpSubList);
		model.addAttribute("multiFileHashMap", developService.getMultiFileHashMap("HRD_DGNS_PRTBIZ_TP_FILE", "TP_IDX", tpIdx));	// multi file 목록
		
		return getViewPath("/traing");
	}
	
	/**
	 * 보고서 전달방식 변경(대면/비대면)
	 * @param ftfYn
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@Auth(role = Role.U)
	@RequestMapping(value = "/change.do", method = RequestMethod.POST)
	public ModelAndView change(@RequestParam Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if(loginVO == null) {
			response.setContentType("text/html; charset=utf-8;");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('권한이 없습니다.');history.go(-1);</script>");
			w.flush();
			w.close();
			return mav;
		}
		
		param.put("REGI_IP", request.getRemoteAddr());
		int result = bsisCnslService.updateFtfYn(param);
		if(result > 0) {
			mav.addObject("result", "success");
		} else {
			mav.addObject("result", "fail");
		}
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
	
		// 추가경로  : 설문조사 view, HRD기초컨설팅 관리자 view, HRD기초컨설팅 사용자 view
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String baseQueryString = StringUtil.getString(request.getAttribute("baseQueryString"));
		
		String qustnrUrl = "qustnr.do";
		String bsisCnsltUrl = "cnslt.do";
		String initUrl = "init.do";
		String consultingClipReportUrl = "consultingClipReport.do";				// 클립리포트
		
		String clipReportUrl = "clipReport.do";		// 클립리포트
		String excelUrl = "excel.do";
		if(!StringUtil.isEmpty(baseQueryString)) {
			qustnrUrl += baseQueryString;
			bsisCnsltUrl += baseQueryString;
			initUrl += baseQueryString;
			clipReportUrl += baseQueryString;
			consultingClipReportUrl += baseQueryString;
			excelUrl += baseQueryString;
		}
		request.setAttribute("URL_QUSTNR", qustnrUrl);
		request.setAttribute("URL_CNSLT", bsisCnsltUrl);
		request.setAttribute("URL_INIT", initUrl);
		request.setAttribute("URL_CLIPREPORT", clipReportUrl);
		request.setAttribute("URL_CONSULTINGCLIPREPORT", consultingClipReportUrl);
		request.setAttribute("URL_EXCEL", excelUrl);
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
	
	public static Boolean tryParseToInt(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	private void consultKnown(Model model, long bsiscnsl_idx) {
		Map basket = (Map) model.asMap().get("basket");
		String bpl_no = String.valueOf(basket.get("BPL_NO"));
		List<Map> names = recommendService.getRCTRBizs(bsiscnsl_idx);
		List<Map> trends = recommendService.getTrendsByBsis(bsiscnsl_idx);
		List<Map> h_ = recommendService.trainHistoryBSIS(bsiscnsl_idx);
		List<Map> m_ = recommendService.moneyHistoryBSIS(bpl_no, bsiscnsl_idx);
		Map doc = recommendService.getDoctorBSIS(bsiscnsl_idx);
		Map corpPIC = new HashMap<String,Object>();
		corpPIC.put("CORP_PIC_NAME", basket.get("CORP_PIC_NAME"));
		corpPIC.put("CORP_PIC_OFCPS", basket.get("CORP_PIC_OFCPS"));
		corpPIC.put("CORP_PIC_TELNO", basket.get("CORP_PIC_TELNO"));
		corpPIC.put("CORP_PIC_EMAIL", basket.get("CORP_PIC_EMAIL"));
		model.addAttribute("trainHis", h_);
		model.addAttribute("moneyHis", m_);
		model.addAttribute("trends", trends);
		model.addAttribute("recommend", names);
		model.addAttribute("doc", doc);
		model.addAttribute("corpPIC", corpPIC);
	}
	
	private void consultStranger(Model model, long rslt_idx, String memberidx) throws Exception {
		String BPL_NO = recommendService.getBSCIDX(rslt_idx);
		Map basket = recommendService.getBSK(BPL_NO);
		basket.put("RANGE", getRange(basket));
		List<Map> trends = recommendService.getTrends(basket);
		
		// AI훈련추천사업 조회 > 만약없으면 alert창띄워주고 직접입력하거나 다시 시도하게끔 유도(AI추천 불러오기 버튼)
		String apiName = "hrdk_edu_reco_system";
		List<Object> recommends = developService.getAiRecommendList(apiName, BPL_NO, (int)rslt_idx);
		if(recommends == null || recommends.isEmpty()) {
			model.addAttribute("isRecommended", false);
		}
		
		List<Map> names = new ArrayList<>(); 
		// 데이터를 돌면서 rank와 기초 정보를 map에 넣는다
		// 같은 rank이면 tp 정보를 TPS에 넣는다.
		for(Object r : recommends) {
			DataMap map_ = (DataMap)r;
			String rank_s = map_.get("RANK").toString();
			int index_ = findIndexOfRank(names, rank_s);
			if(index_ == -1) {
				Map item_ = new HashMap();
				item_.put("RANK", rank_s);
				item_.put("PRTBIZ_IDX", map_.get("PRTBIZ_IDX"));
				item_.put("NAME", (String)map_.get("RCTR_NAME"));
				item_.put("CONSIDER", (String)map_.get("CONSIDER"));
				item_.put("DC", (String)map_.get("INTRO"));
				item_.put("TPS", new ArrayList<>());
				names.add(item_);
				index_ = findIndexOfRank(names, rank_s);
			}
			if(map_.containsKey("TP_IDX")) {
				Map tp_ = new HashMap();
				tp_.put("TP_NAME", (String)map_.get("TP_NAME"));
				tp_.put("TP_IDX", map_.get("TP_IDX"));
				tp_.put("PRTBIZ_IDX", map_.get("PRTBIZ_IDX"));
				tp_.put("TP_CD_RANK", map_.get("TP_CD_RANK"));
				((List) names.get(index_).get("TPS")).add(tp_);
			}
		}
				
		for(Map n : names) {
			String input = (String)n.get("CONSIDER");
			if(input != null) {
				String replaced = input.replaceAll("\n", "<br/>");
				n.put("CONSIDERATION", replaced);
			} else {
				n.put("CONSIDERATION", input);
			}
		}
		
		int count = 3;
		if(names.size() < 3) count = names.size();
		names.subList(count, names.size()).clear();
		for(Map name : names) {
			List<Map> list_ = (List<Map>)name.get("TPS");
			Comparator<Map> comparator = new RankComparator();
			Collections.sort(list_, comparator);
			if(list_.size() > 3) {
				list_.subList(3, list_.size()).clear();
			}
		}
		
		List<Map> h_ = recommendService.trainHistory(BPL_NO);
		List<Map> m_ = recommendService.moneyHistory(BPL_NO);
		Map doc = null;
		doc = recommendService.getBscDoc(rslt_idx);
		if(doc == null || doc.isEmpty()) {
			List<Map> docs = recommendService.getDocByMemberidx(memberidx);
			if(docs == null || docs.size() == 0) {
				doc = recommendService.getDoctor(BPL_NO);
			} else {
				doc = docs.get(0);
			}
		}
		// BSC에서 기업회원 정보 가져오기 by rslt_idx
		model.addAttribute("basket", basket);
		model.addAttribute("trainHis", h_);
		model.addAttribute("moneyHis", m_);
		model.addAttribute("trends", trends);
		model.addAttribute("recommend", names);
		model.addAttribute("doc", doc);
	}
	
	/**
	 * 기업HRD이음컨설팅 AI추천 불러오기
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/callAiRecommend.do", method = RequestMethod.POST)
	public ModelAndView callAiRecommend(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> param) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		String apiName = "hrdk_edu_reco_system";
		String bplNo = param.get("bplNo").toString();
		int rsltIdx = StringUtil.getInt(param.get("rsltIdx").toString());
		try {
			List<Object> recommends = developService.getAiRecommendList(apiName, bplNo, rsltIdx);
			// 추천훈련사업+과정을 루프돌며 기초정보를 map에 담음. 같은 rank면 tp정보를 TPS(array)에 넣는다.
			List<Map> names = new ArrayList<>();
			for(Object r : recommends) {
				DataMap map_ = (DataMap)r;
				String rank_s = map_.get("RANK").toString();
				int index_ = findIndexOfRank(names, rank_s);
				if(index_ == -1) {
					Map item_ = new HashMap();
					item_.put("RANK", rank_s);
					item_.put("PRTBIZ_IDX", map_.get("PRTBIZ_IDX"));
					item_.put("NAME", (String)map_.get("RCTR_NAME"));
					item_.put("CONSIDER", (String)map_.get("CONSIDER"));
					item_.put("DC", (String)map_.get("INTRO"));
					item_.put("TPS", new ArrayList<>());
					names.add(item_);
					index_ = findIndexOfRank(names, rank_s);
				}
				if(map_.containsKey("TP_IDX")) {
					Map tp_ = new HashMap();
					tp_.put("TP_NAME", (String)map_.get("TP_NAME"));
					tp_.put("TP_IDX", map_.get("TP_IDX"));
					tp_.put("PRTBIZ_IDX", map_.get("PRTBIZ_IDX"));
					tp_.put("TP_CD_RANK", map_.get("TP_CD_RANK"));
					((List) names.get(index_).get("TPS")).add(tp_);
				}
			}
					
			for(Map n : names) {
				String input = (String)n.get("CONSIDER");
				if(input != null) {
					String replaced = input.replaceAll("\n", "<br/>");
					n.put("CONSIDERATION", replaced);
				} else {
					n.put("CONSIDERATION", input);
				}
			}
			
			int count = 3;
			if(names.size() < 3) count = names.size();
			names.subList(count, names.size()).clear();
			for(Map name : names) {
				List<Map> list_ = (List<Map>)name.get("TPS");
				Comparator<Map> comparator = new RankComparator();
				Collections.sort(list_, comparator);
				if(list_.size() > 3) {
					list_.subList(3, list_.size()).clear();
				}
			}
			
			mav.addObject("recommends", names);
			mav.addObject("result", "success");
		} catch(Exception e) {
			mav.addObject("result", "fail");
		}
		
		return mav;
	}
	
	private int findIndexOfRank(List<Map> list, String rank) {
		for(int i=0; i<list.size(); i++) {
			Map item_ = list.get(i);
			if(item_.containsKey("RANK") && item_.get("RANK").equals(rank)) {
				return i;
			}
		}
		return -1;
	}
	
	private int insertBSISCNSL(Map<String, Object> model, long rslt_idx, String regiIp) {
		return recommendService.insertBSISCNSLinform(model, rslt_idx, regiIp);
	}
	
	private int getRange(Map bsk) {
		int employees = Integer.parseInt(String.valueOf(bsk.get("TOT_WORK_CNT")));
		if("Z".equals(bsk.get("TREND_LCLAS")))
			return 0;
		if(employees < 5)
			return 5;
		if(employees < 30)
			return 30;
		if(employees < 100)
			return 100;
		if(employees < 300)
			return 300;
		if(employees < 1000)
			return 1000;
		return 10000000;
		
	}
	
	private static String joinListToString(List<String> list, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i < list.size();i++) {
			if(i > 0) {
				sb.append(delimiter);
			}
			sb.append(list.get(i));
		}
		return sb.toString();
	}
}
