package rbs.modules.diagnosis.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import rbs.egovframework.LoginVO;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.modules.basket.dto.Basket;
import rbs.modules.basket.dto.Criteria;
import rbs.modules.basket.service.BasketService;
import rbs.modules.bsisCnsl.web.RankComparator;
import rbs.modules.develop.service.DevelopService;
import rbs.modules.diagnosis.service.DiagnosisService;
import rbs.modules.instt.service.InsttService;
import rbs.modules.recommend.PRISUP;
import rbs.modules.recommend.RecommendDummyService;
import rbs.modules.recommend.QrVO;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.woowonsoft.egovframework.util.ModuleUtil;
import com.woowonsoft.egovframework.util.PathUtil;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;
import com.woowonsoft.egovframework.web.ModuleController;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
@ModuleMapping(moduleId="diagnosis")
@RequestMapping({"/{siteId}/diagnosis", "/{admSiteId}/menuContents/{usrSiteId}/diagnosis"})
public class DiagnosisController extends ModuleController {
	
	@Resource(name = "diagnosisService")
	private DiagnosisService diagnosisService;
	
	@Resource(name = "basketService")
	private BasketService basketService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Resource(name = "developService")
	protected DevelopService developService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
	@Resource(name = "jsonView")
	View jsonView;
	
	@Autowired
	RecommendDummyService recommendService;
	
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
	 *
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value = {"/list.do"})
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
		
		// 2.2 목록수
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		if(siteId.equals("dct")) {
			if(loginVO == null) {
				request.setAttribute("msg", "로그인이 필요합니다.");
				request.setAttribute("url", "/dct/login/login.do?mId=3");
				model.addAttribute("login", false);
				return getViewPath("/alert");
				
			}else if(loginVO.getUsertypeIdx() == 40 || loginVO.getUsertypeIdx() == 30 || loginVO.getUsertypeIdx() == 55) { 
				String insttIdx = loginVO.getInsttIdx();
				if(insttIdx != null) {
					param.put("insttIdx",insttIdx);
				}
				model.addAttribute("login", true);
			
				totalCount = diagnosisService.getTotalCount(param);
				paginationInfo.setTotalRecordCount(totalCount);
				

				if(totalCount > 0) {
					if(usePaging == 1) {
						param.put("firstIndex", paginationInfo.getFirstRecordIndex());
						param.put("lastIndex", paginationInfo.getLastRecordIndex());
						param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
					}
					
					
					// 소속기관 리스트
					Map<String, Object> temp = new HashMap<String, Object>();
					List<?> insttList = null;
					insttList = insttService.getList(temp);
					model.addAttribute("insttList", insttList);
					model.addAttribute("usertype", loginVO.getUsertypeIdx());

					// 정렬컬럼 setting
					param.put("dsetOrderField", JSONObjectUtil.getString(settingInfo, "dset_order_field"));
					
					// 2.3 목록
					list = diagnosisService.getList(param);
					
				}
				
			}else {
				request.setAttribute("msg", "접근 권한이 없습니다.");
				request.setAttribute("url", "/web/main/main.do?mId=1");
				return getViewPath("/alert");
			}
			
		}else {
			// web
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
			} else if(loginVO == null) {
				model.addAttribute("login", false);
				return getViewPath("/list");
			}
			
			model.addAttribute("usertype", loginVO.getUsertypeIdx());
			
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
					for(int i=0;i < searchList.size();i++) {
						if("BPL_NO".equals(searchList.get(i).get("columnId"))) searchList.remove(i);
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
			
	    	totalCount = diagnosisService.getBplCount(param);
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
	    		list = diagnosisService.getBplList(param);
			}
		}

    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
		model.addAttribute("siteId", siteId);
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
	
	/**
	 * 목록조회
	 *
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="LST")
	@RequestMapping(value = {"/apply.do"})
	public String apply(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("siteId") String siteId) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		
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
				return getViewPath("/apply");
			}
			
			param.put("bplNo", bplNo);
			String memberName = loginVO.getMemberName();
			String memberEmail = loginVO.getMemberEmail();
			
			if(bplNo.equals("00000000000") != true) {
				model.addAttribute("noneBplNo", false);
			}else {
				model.addAttribute("noneBplNo", true);
				
			}
			
			if(loginVO.getUsertypeIdx() == 10) {
				model.addAttribute("constl", true); 
			} else {
				model.addAttribute("constl", false);
			}
			model.addAttribute("usertype", loginVO.getUsertypeIdx());
			
			model.addAttribute("bplNo", bplNo);
			model.addAttribute("memberName", memberName);
			model.addAttribute("memberEmail", memberEmail);
			model.addAttribute("login", true);
			
		}else {
			model.addAttribute("login", false);
		}
		
		model.addAttribute("siteId", siteId);
		
    	// 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/apply");
	}
	

	/**
	 * 기초진단 이력 상세보기
	 * 
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(method = RequestMethod.POST, value = "/view.do")
	public String view(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("siteId") String siteId) throws Exception {
		
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

		searchList.add(new DTForm(idxColumnName, keyIdx));
		
		param.put("searchList", searchList);

		// 상세정보
		param.put("BSC_IDX", keyIdx);
		bsc = diagnosisService.getBsc(param);
		fundHis = diagnosisService.getFundHis(param);
		trHis = diagnosisService.getTrHis(param);
		prtbiz = diagnosisService.getPrtbiz(param);
		List<QrVO> qr = recommendService.getQR();
		
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
		model.addAttribute("siteId", siteId);
		model.addAttribute("bscIdx", keyIdx);
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	return getViewPath("/view");
	}
	
	
	/* 
	 * 기초진단 신청-기업바구니
	 */
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.GET, value = "/bskList.do")
	public ModelAndView bskList(@ModuleAttr ModuleAttrVO attrVO, @RequestParam(value="page", defaultValue="1", required=false) int page,
			@RequestBody String json, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		List<?> bskList = null;
		int totalCnt = 0;
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		String insttIdx = loginVO.getInsttIdx();
		
		if(insttIdx != null) {
			param.put("insttIdx",insttIdx);
		}
		
		String searchText1 = request.getParameter("searchText1");
		String searchText2 = request.getParameter("searchText2");
		
		// 기업 검색시 UTF-8 적용 안될경우 사용
//		String encoded_text1 = new String(searchText1.getBytes("iso-8859-1"), "utf-8");
		param.put("searchText1", searchText1);
		param.put("searchText2", searchText2);
		
		bskList = diagnosisService.getBskList(param);
		totalCnt = diagnosisService.getBskCnt(param);

		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		mav.addObject("bskList", bskList);
		mav.addObject("searchText1", searchText1);
		mav.addObject("searchText2", searchText2);
		
		mav.addObject("totalCnt", totalCnt);
		
		return mav;
	}
	
	private void insertBSC(Map<String, Object> model, String bpl_no, String regiIp) {
		recommendService.insertBSC(model, bpl_no, regiIp);
	}
	
	/**
	 * 기초진단 실행
	 * @throws Exception 
	 * 
	 */
	@Auth(role = Role.C)
	@ModuleAuth(name="VEW")
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET},value ="/detailView.do")
	public String detailView(@ModuleAttr ModuleAttrVO attrVO, RedirectAttributes rtt, HttpServletRequest req, Model model, @PathVariable("siteId") String siteId ) throws Exception {
		
		
		HttpSession session = req.getSession(true);
		
		String bplNo = req.getParameter("BPL_NO");
		
		String corpOfcps = req.getParameter("corpOfcps");
		String corpPicNm = req.getParameter("corpPicNm");
		String corpPicTel = req.getParameter("corpPicTel");
		String corpPicEmail = req.getParameter("corpPicEmail");
		
		Map<String, Object> corInfo = new  HashMap<>();
		corInfo.put("corpOfcps", corpOfcps);
		corInfo.put("corpPicNm", corpPicNm);
		corInfo.put("corpPicTel", corpPicTel);
		corInfo.put("corpPicEmail", corpPicEmail);
		
		Map basket = recommendService.getBSK(bplNo);
		List<Map> trends;
		List<QrVO> d_;
		
		
		if(basket != null) {
			basket.put("RANGE", getRange(basket));
			trends = recommendService.getTrends(basket);
			d_ = recommendService.getQR();
			
			int regulars = ((BigDecimal)basket.get("TOT_WORK_CNT")).intValue();
			int code = Integer.valueOf((String)basket.get("INDUTY_CD")); 
			int prisup_code = Integer.parseInt((String)basket.get("PRI_SUP_CD"));
			PRISUP prisup = PRISUP.fromValue(prisup_code);
			
			model.addAttribute("trends", trends);
			model.addAttribute("qrs", d_);
		}
		
		// List<Map> names = recommendService.diagnosisBizNames(regulars, code, prisup);
		String apiName = "hrdk_bizr_reco_system";
		List<Object> recommends = developService.getAiRecommendList(apiName, bplNo, 0);
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
				item_.put("PRTBIZ_NAME", (String)map_.get("RCTR_NAME"));
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
			String replaced = input.replaceAll("\n", "<br/>");
			n.put("CONSIDERATION", replaced);
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
		
		List<Map> h_ = recommendService.trainHistory(bplNo);
		List<Map> m_ = recommendService.moneyHistory(bplNo);
		
		/** 
		 * 주치의 선정 방법
		 * 기존: hrd_bsk.bpl_zip을 기준으로 해당되는 소속기관(hrd_com_instt.instt_idx)에 소속된 주치의 중 아무나 1명
		 * ㄴ변경1: 로그인한 사용자가 주치의인 경우 주치의로 지정하고, 아니라면 기존 방법대로 한다.
		 * ㄴ변경2: 기업의 담당구역을 기준으로 해당 기준에 속하는 주치의를 선정한다.
		 * ㄴ변경3(2024.01.15): 담당주치의가 있는 경우 담당주치의, 담당이 없는 경우 기업의 소속기관을 기준으로 해당 소속기관의 주치의 중 한명으로, 그것마저없으면 우편번호를 기준으로
		 * ㄴ변경4(2024.01.25): 핵심주치의(doctor_yn='Y')가 최우선(무조건적)으로 주치의가 될 것
		 */
		 
		Map doc = null;
		// 1. 담당주치의 찾기
		doc = recommendService.getPicDoctor(bplNo);
//		if(doc != null) System.out.println("담당주치의 : "+ doc.toString());
		if(doc == null || doc.isEmpty()) {
			// 2. 담당주치의 없으면 담당 소속기관으로 찾기(부장 제외)
			doc = recommendService.getDoctorInPicInstt(bplNo);
//			if(doc != null) System.out.println("소속기관내 주치의(랜덤) : " + doc.toString() + "[bplNo:" + bplNo + "]");
			if(doc == null || doc.isEmpty()) {
				// 3. 소속기관에 주치의가 없으면 > 우편번호를 기준으로 소속기관 주치의 탐색
				List<Map> docs = recommendService.getDocByZipcode(bplNo);
				if(docs == null || docs.size() == 0) {
					// 4. 기업바구니(BSK)의 사업장우편번호를 기준&'부장'을 제외하여 탐색
					doc = recommendService.getDoctor(bplNo);
				} else {
					doc = docs.get(0);
				}
			}
		}
		
		if(doc != null && !doc.isEmpty() && doc.get("DOCTOR_TELNO") != null && doc.get("DOCTOR_TELNO") != "") {
			String doctorTelno = doc.get("DOCTOR_TELNO").toString();
			String formattedPhoneNumber = formatPhoneNumber(doctorTelno);
			doc.put("DOCTOR_TELNO", formattedPhoneNumber);
		}
		
		Map<String, Object> bscInfo = new  HashMap<>();
		bscInfo.put("bplNo", bplNo);
		
		// 추후 생성할 보고서 제출 방식
		if(siteId.equals("dct")) {
			String submitMtd = req.getParameter("submitMtd");		
			bscInfo.put("submitMtd", submitMtd);
			
		}
		
		model.addAttribute("recommend", names);
		model.addAttribute("basket", basket);
		model.addAttribute("trainHis", h_);
		model.addAttribute("moneyHis", m_);
		model.addAttribute("recommend", names);
		model.addAttribute("doc", doc);
		model.addAttribute("corInfo", corInfo);
		model.addAttribute("bscInfo", bscInfo);
		model.addAttribute("siteId", siteId);
			
		insertBSC(model.asMap(), bplNo, req.getRemoteAddr());
		model.addAttribute("bscIdx", model.asMap().get("BSC_IDX"));
		 
		 // 기본경로
		fn_setCommonPath(attrVO);
		

		// redirect로 페이지 url을 바꾸기 위해 Model 객체를 세션에 담아서 이동시킴.
		// 새로고침 시 insertBSC가 재 호출되는 문제를 방지 하기 위해 url을 바꾸기 위함이지만 브라우저의 쿠키 정책에 따라 세션 사용에 문제가 될 수 있음 
		// HttpSession session = req.getSession();
		session.setAttribute("model", model.asMap());
		
		if(siteId.equals("dct")) {
			return "redirect:/dct/diagnosis/detail.do?mId=36";
		}else{
			return "redirect:/web/diagnosis/detail.do?mId=53";
		}
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
	
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(value ="/detail.do")
	public String details(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest req, Map model, @PathVariable("siteId") String siteId) {
		
		HttpSession session = req.getSession();
		Map<String,Object> model_ = (Map<String,Object>)session.getAttribute("model");
		
		for(String key : model_.keySet()) {
			model.put(key, model_.get(key));
		}

		// session remove후 새로고침 시 NullPoint 오류방지위한 세션유지시간 설정
		// 30분
		Object sessionData = session.getAttribute("model");
		if(sessionData != null) {
			session.setMaxInactiveInterval(30 * 60);
		}
		
		 // 기본경로
		fn_setCommonPath(attrVO);
		
		if(siteId.equals("dct")) {
			return "rbs/modules/diagnosis/usrDesign/dct/detailView";
		}else{
			return "rbs/modules/diagnosis/usrDesign/apply/detailView";
		}
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

	@Auth(role = Role.C)
	@RequestMapping(value="/charts.do", method=RequestMethod.POST)
	@ResponseBody
	public void setCharts(HttpServletRequest req, HttpServletResponse res) {
		String EXEC_PIC = req.getParameter("exec_pic");
		String SPRT_PIC = req.getParameter("sprt_pic");
		String bsc_idx = req.getParameter("bsc_idx");
		
		Map charts = new HashMap<String, String>();
		charts.put("exec_pic", EXEC_PIC);
		charts.put("sprt_pic", SPRT_PIC);
		charts.put("bsc_idx", bsc_idx);
		
		recommendService.insertChart(charts);
	}
	
	 @Auth(role = Role.U)
	@RequestMapping(value="/updateMax.do", method=RequestMethod.POST)
	public void updateMax(@RequestBody String json, HttpServletRequest req, HttpServletResponse res) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data_ = mapper.readValue(json, Map.class);
		List<Map> inputs = new ArrayList<Map>();
		String idx = (String) data_.get("bscIdx");
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
		recommendService.updateMax(inputs, "BSC");
		res.setStatus(200);
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
		// 항목설정으로 검색조건 setting
		String listSearchId = "list_search";		// 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
//		// 2.3 목록
//		list = diagnosisService.getBplList(param);
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		HttpSession session = request.getSession(true);
		LoginVO loginInfo = (LoginVO) session.getAttribute("loginVO");
		
		String memberIdx = loginInfo.getMemberIdx();
		Map doc = diagnosisService.getDoc(memberIdx);

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
		list = diagnosisService.getList(param);
    	
    	// 항목 설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		HashMap<String, Object> optnHashMap = CodeHelper.getItemOptnHashMap(items, itemOrder);
    	
    	// 3. 속성 setting
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("doc", doc);
    	model.addAttribute("searchOptnHashMap", CodeHelper.getItemOptnSearchHashMap(listSearch));	// 항목 코드
		model.addAttribute("optnHashMap", optnHashMap);
    	
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/excel");
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

	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(method = RequestMethod.POST, value="/hope.do")
	public ModelAndView hope(@ModuleAttr ModuleAttrVO attrVO, Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		String bpl_no = request.getParameter("bpl_no");
		Map bsk = recommendService.getBSK(bpl_no);
		Map limit = recommendService.getLimitInduty(bpl_no);
		model.addAttribute("bsk", bsk);
		model.addAttribute("limit", limit);
		mav.addObject("bsk", bsk);
		mav.addObject("limit", limit);
		
		fn_setCommonPath(attrVO);
		mav.setViewName(getViewPath("/hope"));
		
		return mav; 
	}
	
	@Auth(role = Role.R)
	@ModuleAuth(name="VEW")
	@RequestMapping(value="/qrmanage.do")
	public String qrmanage(@ModuleAttr ModuleAttrVO attrVO, Model model) {
		List<QrVO> qrs = recommendService.getQR();
		model.addAttribute("qrs", qrs);
		return getViewPath("/qrmanage");
	}
	
	@Auth(role = Role.U)
	@ModuleAuth(name="VEW")
	@RequestMapping(value="/updateQR.do", method=RequestMethod.POST)
	public void updateqr(@RequestBody String json, HttpServletResponse res) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<Map> qrs = (List<Map>)mapper.readValue(json, List.class);
		recommendService.updateQR(qrs);
		res.setStatus(200);
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
			modiCnt = diagnosisService.getAuthCount(param);
			
			return (modiCnt > 0);
		}
		
		return true;
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
	
	private static String formatPhoneNumber(String input) {
		String inputValue = input.replaceAll("\\D", "");
		if(inputValue.length() == 8) {
			return inputValue.replaceAll("(\\d{4})(\\d{4})", "$1-$2");
		} else if(inputValue.length() == 9) {
			return inputValue.replaceAll("(\\d{2})(\\d{3})(\\d{4})", "$1-$2-$3");
		} else if(inputValue.length() == 10) {
			return inputValue.replaceAll("(\\d{3})(\\d{3})(\\d{4})", "$1-$2-$3");
		} else if(inputValue.length() == 11) {
			return inputValue.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
		} else {
			return input;
		}
	}
}