package rbs.modules.clinic.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import rbs.modules.clinic.service.ClinicDctService;
import rbs.modules.clinic.service.ClinicService;
import rbs.modules.recommend.RecommendDummyService;

/**
 * 능력개발클리닉(기업)
 * 	ㄴ 사용자화면의 [주치의활동지원] -> [능력개발클리닉]의 전체 기능을 관리한다. 
 * @author 이동근, 권주미
 * 
 *
 */
@Controller
@ModuleMapping(moduleId="clinic")
@RequestMapping({"/{siteId}/clinic", "/{admSiteId}/menuContents/{usrSiteId}/clinic"})
public class ClinicController extends ModuleController {
	
	@Resource(name = "clinicService")
	private ClinicService clinicService;
	
	@Resource(name = "clinicDctService")
	private ClinicDctService clinicDctService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Autowired
	RecommendDummyService recommendService;

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
	

	
/*능력개발클리닉 신청*/
	/**
	 * 능력개발클리닉 신청전 안내사항
	 * @param attrVO
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/request_info_form.do")
	public String info(@ModuleAttr ModuleAttrVO attrVO) throws Exception {				
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/info");
	}
	
	/**
	 * 능력개발클리닉 신청전 안내사항동의
	 * @param attrVO
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/request_infoAgree_form.do")
	public String infoAgree(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {	
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = "";
		
		
		System.out.println("sdfd == " + request.getContextPath());
		// 1. 기본경로
		fn_setCommonPath(attrVO);
		
		// 로그인한 회원의 정보 중 BPL_NO
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}
		
		// 기업이 기업HRD이음컨설팅을 한번이라도 받았는지 확인
		//  0 : 기업HRD이음컨설팅 받은 이력 없음 > 클리닉 기업 신청 불가 > 기업HRD이음컨설팅 화면으로 이동
		long bscIdx = clinicService.getMaxBsc(BPL_NO);
		if(bscIdx == 0) {
			String message = "기업HRD이음컨설팅을 실시하지 않았습니다. 기업HRD이음컨설팅을 먼저 진행해주세요";
			String toUrl = request.getContextPath() + "/web/bsisCnsl/init.do?mId=55";
			model.addAttribute("message", MessageUtil.getAlertAddWindow(attrVO.isAjax(), message, "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
			return RbsProperties.getProperty("Globals.message" + attrVO.getAjaxPName() + ".path");
		}
		
		//isReq(신청서 등록여부)[기업이 능력개발 클리닉 신청서를 한번이라도 등록(신청X)한 적이 있는지]
		// 1 : 등록O
		// 0 : 등록X
		int isReq = clinicService.getIsReq(BPL_NO);
		if(isReq != 0) {
			String message = "최근 3년동안 등록한 신청서가 존재합니다.";
			String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("REQUEST_LIST_FORM_URL")));
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(attrVO.isAjax(), message, "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
			return RbsProperties.getProperty("Globals.message" + attrVO.getAjaxPName() + ".path");
		}
    	
		return getViewPath("/infoAgree");
	}
	
	/**
	 * 능력개발클리닉 신청서 작성 화면
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/request_write_form.do")
	public String request_write_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {

		JSONObject itemInfo = attrVO.getItemInfo();		
		
		// 속성 setting
		// 항목설정
		String submitType = "write";
		model.addAttribute("optnHashMap", getOptionHashMap(submitType, itemInfo));
		model.addAttribute("submitType", submitType);
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = "";
		
		// 로그인한 회원의 정보 중 BPL_NO
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}
		
		//isReq(신청서 등록여부)[기업이 능력개발 클리닉 신청서를 한번이라도 등록(신청X)한 적이 있는지]
		// 1 : 등록O
		// 0 : 등록X
		int isReq = clinicService.getIsReq(BPL_NO);
		if(isReq != 0) {
			String message = "최근 3년동안 등록한 신청서가 존재합니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		// 기업정보 , HRD 담당자 정보
		DataMap corpInfo = null;
		List<Object> checkList = null;
		List<Object> insttList = null;
		
		// fnIdx
		int fnIdx = attrVO.getFnIdx();
		
		// 쿼리에 전달할 파라미터(BPL_NO)
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);		
		
		// 기업 자가 확인 체크리스트
		checkList = clinicService.getCheckList(fnIdx, param);
		
		// 신청서에서 지부지사를 선택하기 위해 지부지사 관할구역 정보 가져오기
		insttList = clinicService.getInsttList(param);
		
    	// 2. 기본경로
    	fn_setCommonPath(attrVO);

    	// 3. form action
    	model.addAttribute("corpInfo", corpInfo);
    	model.addAttribute("checkList", checkList);
    	model.addAttribute("insttList", insttList);
    	model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());
    	
		return getViewPath("/input");
	}
	
	/**
	 * 능력개발클리닉 신청서 목록 화면
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/request_list_form.do")
	public String request_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAdmMode = attrVO.isAdmMode();		
		String targetTable = "HRD_CLI_REQ";
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
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
		
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		param.put("CLI_IDX", cliIdx);
		// 2.2 목록수
    	totalCount = clinicService.getTotalCount(targetTable, param);
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
    		list = clinicService.getList(targetTable, param);
		}

    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}

	/**
	 * 능력개발클리닉 신청서 수정 화면
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

		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int reqIdx = StringUtil.getInt(request.getParameter("reqIdx"));
		
		if(reqIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "modify");
		
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		//url 변조 접근 방지
		int isRequestBplNo = clinicService.getCompareRequestAndVoByBplNo("HRD_CLI_REQ","REQ_IDX",reqIdx, BPL_NO);

		
		if(isRequestBplNo == 0) {
			String message = "정상적인 접근이 아닙니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		// 기업정보 , 지부지사 목록, 체크리스트 목록 및 답변 목록 정보
		DataMap corpInfo = null;
		List<Object> insttList = null;
		List<Object> checkList = null;
		List<Object> checkListAnswer = null;
		
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);
		
		// 체크리스트 목록
		checkList = clinicService.getCheckList(fnIdx, param);
		
		// 신청서에서 지부지사를 선택하기 위해 지부지사 관할구역 정보 가져오기
		insttList = clinicService.getInsttList(param);

		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "REQ_IDX", reqIdx));
		param.put("searchList", searchList);
		
		// 기업이 체크한 체크리스트를 가져오기(Y/N값 체크용)
		checkListAnswer = clinicService.getCheckListAnswer(fnIdx, param);
		
		// 2.2 상세정보
		dt = clinicDctService.getReqView("HRD_CLI_REQ", param);
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
		model.addAttribute("multiDataHashMap", clinicService.getMultiHashMap(fnIdx, reqIdx, settingInfo, items, itemOrder));		// multi file 목록
		model.addAttribute("multiFileHashMap", clinicService.getMultiFileHashMap("HRD_CLI_REQ_FILE", cliIdx, fnIdx, reqIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("submitType", submitType);											// 페이지구분
		model.addAttribute("corpInfo", corpInfo);
		model.addAttribute("insttList", insttList);
		model.addAttribute("checkList", checkList);
		model.addAttribute("checkListAnswer", checkListAnswer);
		model.addAttribute("optnHashMap", optnHashMap);									// 항목코드
		model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/input");
	}
	
	

	/**
	 * 능력개발클리닉 신청서 상세보기 화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/request_view_form.do")
	public String request_view_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
//		int reqIdx = CommonFunction.decSeedDecryptReturnInt(request.getParameter("reqIdx"));
		int reqIdx = StringUtil.getInt(request.getParameter("reqIdx"));
		
		if(reqIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "modify");
		
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		//url 변조 접근 방지
		int isRequestBplNo = clinicService.getCompareRequestAndVoByBplNo("HRD_CLI_REQ","REQ_IDX",reqIdx, BPL_NO);

		
		if(isRequestBplNo == 0) {
			String message = "정상적인 접근이 아닙니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		// 기업정보, 지부지사 목록, 체크리스트 목록 및 답변 목록 정보
		DataMap corpInfo = null;
		List<Object> insttList = null;
		List<Object> checkList = null;
		List<Object> checkListAnswer = null;
		

		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);
		
		// 체크리스트 목록
		checkList = clinicService.getCheckList(fnIdx, param);

		// 신청서에서 지부지사를 선택하기 위해 지부지사 관할구역 정보 가져오기
		insttList = clinicService.getInsttList(param);
		
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "REQ_IDX", reqIdx));
		param.put("searchList", searchList);
		
		// 기업이 체크한 체크리스트를 가져오기(Y/N값 체크용)
		checkListAnswer = clinicService.getCheckListAnswer(fnIdx, param);
		
		// 2.2 상세정보
		dt = clinicDctService.getReqView("HRD_CLI_REQ", param);
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
		model.addAttribute("multiDataHashMap", clinicService.getMultiHashMap(fnIdx, reqIdx, settingInfo, items, itemOrder));		// multi file 목록
		model.addAttribute("multiFileHashMap", clinicService.getMultiFileHashMap("HRD_CLI_REQ_FILE", cliIdx, fnIdx, reqIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("submitType", submitType);											// 페이지구분
		model.addAttribute("corpInfo", corpInfo);
		model.addAttribute("insttList", insttList);
		model.addAttribute("checkList", checkList);
		model.addAttribute("checkListAnswer", checkListAnswer);
		model.addAttribute("optnHashMap", optnHashMap);									// 항목코드
		model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	/**
	 * 능력개발클리닉 신청서 임시저장
	 * 	ㄴ 상태값 : 5
	 * 	ㄴ TEMPSAVE
	 * 	ㄴ 최초 임시저장 시 insert -> 이 후 임시저장 시 update
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(method=RequestMethod.POST, value = "/request_tempSave.do")
	public String request_tempSave(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();		
		
		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write";		// 항목 order명
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray cliInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "cli_insert_column_order");
		JSONArray cliUpdateColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "cli_update_column_order");
		JSONArray reqInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "req_insert_column_order");
		parameterMap.put("_itemInputTypeFlag", submitType);

		// 2. DB

		int	result = 0;
		int cliResult = 0;
		// 임시저장 상태로 setting
		String confmStatus = ConfirmProgress.TEMPSAVE.getCode();
		
		/*
		 * 		클리닉을 신청 여부 체크
		 *		ㄴ 기업(BPL_NO)을 조건으로 검색한 클리닉 데이터 중 isDelete가 0이고, 오늘이 valid_start_date와 valid_end_date사이에 있는 데이터
		 *		ㄴ 1 이상(이상으로 해놨지만 1이상이면 쿼리의 문제) : 
		 *		ㄴ 0									 : 중도포기 혹은 신청 전 상태
		 * */

		int isCli = clinicService.getIsReq(BPL_NO);
		if(isCli == 0) {
			//클리닉 insert
			cliResult = clinicService.insertCli(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, cliInsertColumnOrder);
			//클리닉신청서 insert(
			if(cliResult != 0) {
				result = clinicService.insertReqAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, reqInsertColumnOrder, confmStatus);
			}
		}else {			
			//클리닉신청서 update
			String reqIdx = StringUtil.getString(parameterMap.get("reqIdx"));
			int cliIdx = clinicService.getCliIdx(BPL_NO);
			parameterMap.put("cliIdx", cliIdx);
			cliResult = clinicService.updateCli(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, cliUpdateColumnOrder);
			if(cliResult != 0) {
				result = clinicService.updateReqAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, reqIdx, confmStatus);
			}
		}		    	    	    	
    	
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("REQUEST_LIST_FORM_URL")));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.tempsave"), "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
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
		
	/**
	 * 능력개발클리닉 신청서 신청
	 * 	ㄴ 상태값 : 10
	 * 	ㄴ APPLY
	 * 	ㄴ 최초 신청 시 insert -> 임시저장 이후 신청 시 update
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(method=RequestMethod.POST, value = "/request_apply.do")
	public String request_apply(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();		
		

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write";		// 항목 order명
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray cliInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "cli_insert_column_order");
		JSONArray cliUpdateColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "cli_update_column_order");
		JSONArray reqInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "req_insert_column_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		// 1.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, cliInsertColumnOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		// 2. DB

		int	result = 0;
		int cliResult = 0;
		
		// 신청 상태로 setting
		String confmStatus = ConfirmProgress.APPLY.getCode();
		
		/*
		 * 		클리닉 신청 여부 체크
		 *		ㄴ 기업(BPL_NO)을 조건으로 검색한 클리닉 데이터 중 isDelete가 0이고, 오늘이 valid_start_date와 valid_end_date사이에 있는 데이터
		 *		ㄴ 1 이상(이상으로 해놨지만 1이상이면 쿼리의 문제) : 
		 *		ㄴ 0									 : 중도포기 혹은 신청 전 상태
		 * */
		int isCli = clinicService.getIsReq(BPL_NO);
		if(isCli == 0) {	
			//클리닉 insert
			cliResult = clinicService.insertCli(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, cliInsertColumnOrder);
			//클리닉신청서 insert(
			if(cliResult != 0) {
				result = clinicService.insertReqAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, reqInsertColumnOrder, confmStatus);
			}
		} else {			
			//클리닉신청서 update
			String reqIdx = StringUtil.getString(parameterMap.get("reqIdx"));
			int cliIdx = clinicService.getCliIdx(BPL_NO);
			parameterMap.put("cliIdx", cliIdx);
			cliResult = clinicService.updateCli(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, cliUpdateColumnOrder);
			if(cliResult != 0) {
				result = clinicService.updateReqAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, reqIdx, confmStatus);
			}
		}		    	    	    	
    	
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.apply2"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("REQUEST_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
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
	
	/**
	 * 능력개발클리닉 신청서 회수
	 * 	ㄴ 상태값 : 20
	 *  ㄴ WITHDRAW
	 *  ㄴ 상태값만 변경한다.
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/request_withdraw.do")
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
		
		// 접수 상태로 setting
		String confmStatus = ConfirmProgress.WITHDRAW.getCode();
		
		
		
		
		// HRD_CLI_REQ 승인상태 update하기
    	int updateReq = clinicService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updateReq > 0) {
    		// HRD_CLI_REQ_CONFM 테이블에 status insert하기
    		int updateReqConfm = clinicService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updateReqConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
        		model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.withdraw"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("REQUEST_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 능력개발클리닉 신청서 반려요청
	 * 	ㄴ 상태값 : 35
	 *  ㄴ RETURNREQUEST
	 *  ㄴ 상태값만 변경한다.
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/request_returnRequest.do")
	public String request_returnRequest(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
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
		
		// 반려요청 상태로 setting
		String confmStatus = ConfirmProgress.RETURNREQUEST.getCode();

		// 반려요청 상태로 HRD_CLI_REQ 승인상태 update하기
    	int updateReq = clinicService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updateReq > 0) {
    		// HRD_CLI_REQ_CONFM 테이블에 접수 상태 insert하기
    		int updateReqConfm = clinicService.insertConfm(targetTable2,targetColumn , targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updateReqConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
        		model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.returnrequest"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("REQUEST_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 능력개발클리닉 중도포기요청
	 * 	ㄴ 상태값 : 75
	 *  ㄴ DROPREQUEST
	 *  ㄴ 상태값만 변경한다.
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
		
		// 중도포기요청 상태로 setting
		String confmStatus = ConfirmProgress.DROPREQUEST.getCode();

		// 중도포기요청 상태로 HRD_CLI_REQ 승인상태 update하기
    	int updateReq = clinicService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updateReq > 0) {
    		// HRD_CLI_REQ_CONFM 테이블에 접수 상태 insert하기
    		int updateReqConfm = clinicService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updateReqConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
        		model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.droprequest"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("REQUEST_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}	
/*능력개발클리닉 신청 끝*/

	
/*능력개발클리닉 계획 */
	/**
	 * 능력개발클리닉 훈련계획서 목록 화면
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/plan_list_form.do")
	public String plan_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAdmMode = attrVO.isAdmMode();		
		String targetTable = "HRD_CLI_PLAN";
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		//isCli(클리닉 선정여부)[기업이 능력개발클리닉 기업으로 최종 선정이 됐는지 확인]
		// 1 : 선정O
		// 0 : 선정X
		int isCli = clinicService.getIsCli(BPL_NO);		
		if(isCli == 0) {
			String message = "해당 메뉴는 능력개발클리닉 기업으로 선정이 되어야 이용할 수 있습니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
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
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		int cliIdx  = clinicService.getCurrentCliIdx(BPL_NO);
		param.put("CLI_IDX", cliIdx);
		// 2.2 목록수
    	totalCount = clinicService.getTotalCount(targetTable, param);
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
    		list = clinicService.getList(targetTable, param);
		}

    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
	
	/**
	 * 능력개발클리닉 훈련계획서 작성 화면
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/plan_write_form.do")
	public String plan_write_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {


		JSONObject itemInfo = attrVO.getItemInfo();		
		
		// 속성 setting
		// 항목설정
		String submitType = "write";
		model.addAttribute("optnHashMap", getOptionHashMap(submitType, itemInfo));
		model.addAttribute("submitType", submitType);
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = "";
		
		// 로그인한 회원의 정보 중 BPL_NO
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}
		
		//isCli(클리닉 선정여부)[기업이 능력개발클리닉 기업으로 최종 선정이 됐는지 확인]
		// 1 : 선정O
		// 0 : 선정X
		int isCli = clinicService.getIsCli(BPL_NO);		
		if(isCli == 0) {
			String message = "능력개발클리닉 기업으로 선정되지 않아서 훈련계획서를 작성할 수 없습니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
						
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		int isPlan = clinicService.getIsPlan(cliIdx);
		if(isPlan > 0) {
			String message = "해당연도에 등록된 훈련계획서가 이미 존재합니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		DataMap reqList = null;
		DataMap corpInfo = null;
		
		// fnIdx
		int fnIdx = attrVO.getFnIdx();
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CLI_IDX", cliIdx));
		param.put("searchList", searchList);
		// 작성된 신청서에서 가져온 해당 기업정보 
		reqList = clinicService.getModify("HRD_CLI_REQ", param);

		param.put("BPL_NO", BPL_NO);
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);		

    	// 2. 기본경로
    	fn_setCommonPath(attrVO);

    	// 3. form action
    	model.addAttribute("corpInfo", corpInfo);
    	model.addAttribute("reqList", reqList);
    	model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());
    	
		return getViewPath("/input");
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
	@RequestMapping(value = "/plan_view_form.do")
	public String plan_view_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
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
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		int isRequestBplNo = clinicService.getCompareRequestAndVoByBplNo("HRD_CLI_PLAN","PLAN_IDX",planIdx, BPL_NO);

		
		if(isRequestBplNo == 0) {
			String message = "정상적인 접근이 아닙니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		
		// 기업정보 
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
		reqList = clinicService.getView("HRD_CLI_REQ", param);
		
		// 작성된 신청서의 정보를 가져와 세팅한 후 searchList를 비운다.
		searchList.clear();
		
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);
		// 클리닉 활동 계획 기업 담당자
		picList = clinicService.getList("HRD_CLI_PLAN_CORP_PIC", "PLAN_IDX", planIdx, "PIC_IDX");
		// 클리닉 활동 계획 KPI 목표
		kpiList = clinicService.getList("HRD_CLI_PLAN_KPI","PLAN_IDX", planIdx, "KPI_IDX");
		// 클리닉 활동 계획 세부 내용
		subList = clinicService.getList("HRD_CLI_PLAN_SUB","PLAN_IDX", planIdx, "ESSNTL_SE, A.SPORT_ITEM_CD");
		// 클리닉 활동 계획 세부 훈련계획(연간)
		trList = clinicService.getList("HRD_CLI_PLAN_TR_SUB","PLAN_IDX", planIdx, "TR_DTL_IDX");
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "PLAN_IDX", planIdx));
		param.put("searchList", searchList);
		
		// 2.2 상세정보
		dt = clinicService.getView("HRD_CLI_PLAN", param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "view";

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
		model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	/**
	 * 능력개발클리닉 훈련계획서 수정 화면
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
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		int isRequestBplNo = clinicService.getCompareRequestAndVoByBplNo("HRD_CLI_PLAN","PLAN_IDX",planIdx, BPL_NO);

		
		if(isRequestBplNo == 0) {
			String message = "정상적인 접근이 아닙니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		
		// 기업정보 
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
		reqList = clinicService.getModify("HRD_CLI_REQ", param);
		
		// 작성된 신청서의 정보를 가져와 세팅한 후 searchList를 비운다.
		searchList.clear();
		
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);
		// 클리닉 활동 계획 기업 담당자
		picList = clinicService.getList("HRD_CLI_PLAN_CORP_PIC", "PLAN_IDX", planIdx, "PIC_IDX");
		// 클리닉 활동 계획 KPI 목표
		kpiList = clinicService.getList("HRD_CLI_PLAN_KPI","PLAN_IDX", planIdx, "KPI_IDX");
		// 클리닉 활동 계획 세부 내용
		subList = clinicService.getList("HRD_CLI_PLAN_SUB","PLAN_IDX", planIdx, "ESSNTL_SE, A.SPORT_ITEM_CD");
		// 클리닉 활동 계획 세부 훈련계획(연간)
		trList = clinicService.getList("HRD_CLI_PLAN_TR_SUB","PLAN_IDX", planIdx, "TR_DTL_IDX");
		
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
		String contents = (String) dt.get("HRD_PIC_ABLTDEVLP_PLAN");
		if(contents != null) {
			contents = contents.replaceAll("<br>", "\r\n");
			dt.put("HRD_PIC_ABLTDEVLP_PLAN", contents);
		}
		
		String contents2 = (String) dt.get("SLFTR_PLAN");
		if(contents2 != null) {
			contents2 = contents2.replaceAll("<br>", "\r\n");
			dt.put("SLFTR_PLAN", contents2);
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
		model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/input");
	}
	
	/**
	 * 능력개발클리닉 훈련계획서 임시저장
	 * 	ㄴ 상태값 : 5
	 * 	ㄴ TEMPSAVE
	 * 	ㄴ 최초 임시저장 시 insert -> 이 후 임시저장 시 update
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(method=RequestMethod.POST, value = "/plan_tempSave.do")
	public String plan_tempSave(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();		
		

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write";		// 항목 order명
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray planInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "plan_insert_column_order");
		parameterMap.put("_itemInputTypeFlag", submitType);

		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("hrdPic");
		if(contents != null) {
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("hrdPic", contents);
		}
		
		String contents2 = (String) parameterMap.get("slftr");
		if(contents2 != null) {
			contents2 = contents2.replaceAll("\r\n", "<br>");
			parameterMap.put("slftr", contents2);
		}

		// 2. DB
		int	result = 0;
		// 임시저장 상태로 setting
		String confmStatus = ConfirmProgress.TEMPSAVE.getCode();
		Map<String, Object> param = new HashMap<String, Object>();
		
		
		param.put("BPL_NO", BPL_NO);
		//클리닉 테이블에서 기업의 CLI_IDX를 가져온다.
		int cliIdx  = clinicService.getCliIdx(BPL_NO);

		//HRD_CLI_PLAN 테이블에서 등록된 훈련계획서가 있는지 확인한다
		int isPlan = clinicService.getIsPlan(cliIdx);
		if(isPlan == 0) {
			//클리닉 훈련계획서 insert(
			result = clinicService.insertPlanAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, cliIdx, confmStatus);
			
		}else {			
			//클리닉신청서 update
			parameterMap.put("cliIdx", cliIdx);
			result = clinicService.updatePlanAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, cliIdx, confmStatus);
		}		    	    	    	
    	
    	
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("PLAN_LIST_FORM_URL")));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.tempsave"), "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
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
	
	/**
	 * 능력개발클리닉 훈련계획서 신청
	 * 	ㄴ 상태값 : 10
	 * 	ㄴ APPLY
	 * 	ㄴ 최초 신청 시 insert -> 임시저장 이후 신청 시 update
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(method=RequestMethod.POST, value = "/plan_apply.do")
	public String plan_apply(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();		
		

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write";		// 항목 order명
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray planInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "plan_insert_column_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		// 1.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, planInsertColumnOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("hrdPic");
		if(contents != null){
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("hrdPic", contents);
		}
		
		String contents2 = (String) parameterMap.get("slftr");
		if(contents2 != null){
			contents2 = contents2.replaceAll("\r\n", "<br>");
			parameterMap.put("slftr", contents2);
		}
				
		// 2. DB
		int	result = 0;
		// 임시저장 상태로 setting
		String confmStatus = ConfirmProgress.APPLY.getCode();
		Map<String, Object> param = new HashMap<String, Object>();
		
		
		param.put("BPL_NO", BPL_NO);
		//클리닉 테이블에서 기업의 CLI_IDX를 가져온다.
		int cliIdx  = clinicService.getCliIdx(BPL_NO);

		//HRD_CLI_PLAN 테이블에서 등록된 훈련계획서가 있는지 확인한다
		int isPlan = clinicService.getIsPlan(cliIdx);
		if(isPlan == 0) {
			//클리닉 훈련계획서 insert(
			result = clinicService.insertPlanAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, cliIdx, confmStatus);
			
		}else {			
			//클리닉신청서 update
			parameterMap.put("cliIdx", cliIdx);
			result = clinicService.updatePlanAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, cliIdx, confmStatus);
		}		    	    	    	
    	
    	
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("PLAN_LIST_FORM_URL")));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.apply2"), "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
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
	
	/**
	 * 능력개발클리닉 훈련계획서 회수
	 * 	ㄴ 상태값 : 20
	 *  ㄴ WITHDRAW
	 *  ㄴ 상태값만 변경한다.
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/plan_withdraw.do")
	public String plan_withdraw(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_PLAN";
		String targetColumn = "PLAN_IDX";
		
		// 신청상태를 insert할 targetTable(HRD_CLI_REQ_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
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
		
		// 회수 상태로 setting
		String confmStatus = ConfirmProgress.WITHDRAW.getCode();
		
		
		// 접수 상태로 HRD_CLI_REQ 승인상태 update하기
    	int updatePlan = clinicService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updatePlan > 0) {
    		// HRD_CLI_REQ_CONFM 테이블에 접수 상태 insert하기
    		int updatePlanConfm = clinicService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updatePlanConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
        		model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.withdraw"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("PLAN_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 능력개발클리닉 훈련계획서 반려요청
	 * 	ㄴ 상태값 : 35
	 *  ㄴ RETURNREQUEST
	 *  ㄴ 상태값만 변경한다.
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/plan_returnRequest.do")
	public String plan_returnRequest(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_PLAN";
		String targetColumn = "PLAN_IDX";
		
		// 신청상태를 insert할 targetTable(HRD_CLI_REQ_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
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
		
		// 반려요청 상태로 setting
		String confmStatus = ConfirmProgress.RETURNREQUEST.getCode();
		
		
		// 접수 상태로 HRD_CLI_REQ 승인상태 update하기
    	int updatePlan = clinicService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updatePlan > 0) {
    		// HRD_CLI_REQ_CONFM 테이블에 접수 상태 insert하기
    		int updatePlanConfm = clinicService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updatePlanConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
        		model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.returnrequest"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("PLAN_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
		
	/**
	 * 능력개발클리닉 훈련계획서 중도포기요청
	 * 	ㄴ 상태값 : 75
	 *  ㄴ DROPREQUEST
	 *  ㄴ 상태값만 변경한다.
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
		
		// 신청상태를 insert할 targetTable(HRD_CLI_REQ_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
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
		
		// 중도포기요청 상태로 setting
		String confmStatus = ConfirmProgress.DROPREQUEST.getCode();
		
		// 접수 상태로 HRD_CLI_REQ 승인상태 update하기
    	int updatePlan = clinicService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updatePlan > 0) {
    		// HRD_CLI_REQ_CONFM 테이블에 접수 상태 insert하기
    		int updatePlanConfm = clinicService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updatePlanConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
        		model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.droprequest"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("PLAN_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 능력개발클리닉 훈련계획서 변경요청
	 * 	ㄴ 상태값 : 70
	 * 	ㄴ MODIFYREQUEST
	 * 	ㄴ 최초 신청과 흐름이 같지만, insert될 테이블에 접미사 CHANGE가 붙는다
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(method=RequestMethod.POST, value = "/plan_modifyRequest.do")
	public String plan_modifyRequest(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		String regiIp = request.getRemoteAddr();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();		
		
		// 신청상태를 insert할 targetTable(HRD_CLI_PLAN_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
		String targetTable2 = "HRD_CLI_PLAN_CONFM";
		String targetColumn2 = "CONFM_IDX";

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "modify";		// 항목 order명
		String inputFlag = "modify";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray planInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "plan_insert_column_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		// 1.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, planInsertColumnOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("hrdPic");
		if(contents != null){
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("hrdPic", contents);
		}
		
		String contents2 = (String) parameterMap.get("slftr");
		if(contents2 != null){
			contents2 = contents2.replaceAll("\r\n", "<br>");
			parameterMap.put("slftr", contents2);
		}
		
		// 2. DB
		int	result = 0;
		// 변경요청 상태로 setting
		String confmStatus = ConfirmProgress.MODIFYREQUEST.getCode();
		Map<String, Object> param = new HashMap<String, Object>();
		
		
		param.put("BPL_NO", BPL_NO);
		//클리닉 테이블에서 기업의 CLI_IDX를 가져온다.
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		int planIdx = StringUtil.getInt(request.getParameter("planIdx"));
		
		// CHANGE테이블에 insert
		result = clinicService.insertPlanChangeAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, cliIdx, confmStatus);
		
		// HRD_CLI_PLAN 승인상태 update하기
    	clinicService.updateStatus("HRD_CLI_PLAN", "PLAN_IDX", confmStatus, cliIdx, planIdx, regiIp);
    	
    	if(result > 0) {
    		// 2.1 저장 성공
    		clinicService.insertConfm(targetTable2, "PLAN_IDX", targetColumn2, confmStatus, cliIdx, planIdx, regiIp);
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("PLAN_LIST_FORM_URL")));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.modifyrequest"), "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
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

	
/*능력개발클리닉 계획 끝 */

/*능력개발클리닉 활동일지 시작*/
	
	/**
	 * 능력개발클리닉 활동일지 목록 화면
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/activity_list_form.do")
	public String activity_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAdmMode = attrVO.isAdmMode();		
		String targetTable = "HRD_CLI_ACMSLT";
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		//isCli(클리닉 선정여부)[기업이 능력개발클리닉 기업으로 최종 선정이 됐는지 확인]
		// 1 : 선정O
		// 0 : 선정X
		int isCli = clinicService.getIsCli(BPL_NO);		
		if(isCli == 0) {
			String message = "해당 메뉴는 능력개발클리닉 기업으로 선정이 되어야 이용할 수 있습니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
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
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		int cliIdx  = clinicService.getCurrentCliIdx(BPL_NO);
		param.put("CLI_IDX", cliIdx);
		// 2.2 목록수
    	totalCount = clinicService.getTotalCount(targetTable, param);
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
    		list = clinicService.getList(targetTable, param);
		}

    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());
    	model.addAttribute("optnHashMap", getOptionHashMap("list", itemInfo));
    	
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
	
	/**
	 * 능력개발클리닉 훈련계획서 작성 화면
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/activity_write_form.do")
	public String activity_write_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		JSONObject itemInfo = attrVO.getItemInfo();		
		
		// 속성 setting
		// 항목설정
		String submitType = "write";
		model.addAttribute("optnHashMap", getOptionHashMap(submitType, itemInfo));
		model.addAttribute("submitType", submitType);
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = "";
		
		// 로그인한 회원의 정보 중 BPL_NO
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}
		
		//isCli(클리닉기업 선정여부)[기업이 클리닉기업에 최종 선정이 됐는지 확인]
		// 1 : 등록O
		// 0 : 등록X
		int isCli = clinicService.getIsCli(BPL_NO);		
		if(isCli == 0) {
			String message = "능력개발클리닉 기업으로 선정되지 않아서 활동일지를 작성할 수 없습니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		
		int isPlan = clinicService.getIsPlan(cliIdx);
		if(isPlan == 0) {
			String message = "해당연도에 등록된 훈련계획서가 존재하지 않습니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		// isPlan(기업의 올해 능력개발클리닉 훈련계획서가 최종승인이 됐는지 확인)
		// 1 : 최종승인O
		// 0 : 최종승인X
		int isPlanApprove = clinicService.getIsApprove("HRD_CLI_PLAN", cliIdx);
		if(isPlanApprove == 0) {
			String message = "능력개발클리닉 훈련계획서가 최종승인되지 않았습니다. 훈련계획서를 최종승인 받고 활동일지를 작성해 주세요.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		DataMap corpInfo = null;
		
		// fnIdx
		int fnIdx = attrVO.getFnIdx();
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CLI_IDX", cliIdx));
		param.put("searchList", searchList);

		param.put("BPL_NO", BPL_NO);
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);		

		// 훈련계획서에서 신청한 지원유형 목록 가져오기
		List<Object> planSubList = null; 
		int planIdx = clinicService.getMaxIdx("HRD_CLI_PLAN", "PLAN_IDX", cliIdx);
		planSubList = clinicService.getList("HRD_CLI_PLAN_SUB","PLAN_IDX", planIdx, "ESSNTL_SE, A.SPORT_ITEM_CD");
		
    	// 2. 기본경로
    	fn_setCommonPath(attrVO);

    	// 3. form action
    	model.addAttribute("submitType", "write");
    	model.addAttribute("planSubList", planSubList);
    	model.addAttribute("corpInfo", corpInfo);
    	model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());
    	
		return getViewPath("/input");
	}
	
	/**
	 * 능력개발클리닉 활동일지 등록(등록과 동시에 자동으로 최종승인)
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(method=RequestMethod.POST, value = "/activity_apply.do")
	public String activity_apply(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();		
		
		// cliIdx 가져오기
		int cliIdx = clinicService.getCliIdx(BPL_NO);
		
		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write";		// 항목 order명
		String inputFlag = "write";		// 항목 order명
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		// 1.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, itemOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}

		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("cn");
		if(contents != null){
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("cn", contents);
		}
		
		// 2. DB
		int	result = 0;
		
		// 최종승인 상태로 setting(활동일지는 자동 승인)
		String confmStatus = ConfirmProgress.APPROVE.getCode();
    	
		result = clinicService.insertAcmsltAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, cliIdx, confmStatus);
		
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.apply"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("ACTIVITY_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
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
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		//url 변조 접근 방지
		int isRequestBplNo = clinicService.getCompareRequestAndVoByBplNo("HRD_CLI_ACMSLT","ACMSLT_IDX",acmsltIdx, BPL_NO);

		
		if(isRequestBplNo == 0) {
			String message = "정상적인 접근이 아닙니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		
		// 기업정보 
		DataMap corpInfo = null;
		
		// 기업 정보 조회 조건 setting
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CLI_IDX", cliIdx));
		searchList.add(new DTForm("A." + "ACMSLT_IDX", acmsltIdx));
		param.put("searchList", searchList);
		
		// 2.2 상세정보
		dt = clinicService.getView("HRD_CLI_ACMSLT", param);
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
		model.addAttribute("dt", dt);
		model.addAttribute("submitType", submitType);											// 페이지구분
		model.addAttribute("corpInfo", corpInfo);
		model.addAttribute("optnHashMap", optnHashMap);									// 항목코드
		model.addAttribute("multiFileHashMap", clinicService.getMultiFileHashMap("HRD_CLI_ACMSLT_FILE", cliIdx, fnIdx, acmsltIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	/**
	 * 능력개발클리닉 훈련계획서 수정 화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(value = "/activity_modify_form.do")
	public String activity_modify_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();

		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int acmsltIdx = StringUtil.getInt(request.getParameter("acmsltIdx"));
		
		if(acmsltIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "modify");
		
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		//url 변조 접근 방지
		int isRequestBplNo = clinicService.getCompareRequestAndVoByBplNo("HRD_CLI_ACMSLT","ACMSLT_IDX",acmsltIdx, BPL_NO);

		
		if(isRequestBplNo == 0) {
			String message = "정상적인 접근이 아닙니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		
		// 기업정보 
		DataMap corpInfo = null;
		
		// 기업정보 조회 조건 setting
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CLI_IDX", cliIdx));
		searchList.add(new DTForm("A." + "ACMSLT_IDX", acmsltIdx));
		param.put("searchList", searchList);
		
		// 2.2 상세정보
		dt = clinicService.getModify("HRD_CLI_ACMSLT", param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		// textarea 줄바꿈 처리하기
		String contents = (String) dt.get("ACMSLT_CN");
		if(contents != null){
			contents = contents.replaceAll("<br>", "\r\n");
			dt.put("ACMSLT_CN", contents);
		}
		
		// 훈련계획서에서 신청한 지원유형 목록 가져오기
		List<Object> planSubList = null; 
		int planIdx = clinicService.getMaxIdx("HRD_CLI_PLAN", "PLAN_IDX", cliIdx);
		planSubList = clinicService.getList("HRD_CLI_PLAN_SUB","PLAN_IDX", planIdx, "ESSNTL_SE, A.SPORT_ITEM_CD");
		
		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "modify";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");

		// 3.2 속성 setting
		model.addAttribute("dt", dt);
		model.addAttribute("submitType", submitType);											// 페이지구분
		model.addAttribute("corpInfo", corpInfo);
		model.addAttribute("planSubList", planSubList);
		model.addAttribute("optnHashMap", optnHashMap);									// 항목코드
		model.addAttribute("multiFileHashMap", clinicService.getMultiFileHashMap("HRD_CLI_ACMSLT_FILE", cliIdx, fnIdx, acmsltIdx, settingInfo, items, itemOrder));	// multi file 목록
		model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/input");
	}
	
	/**
	 * 능력개발클리닉 활동일지 수정
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/activity_modify.do")
	public String activity_modify(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int acmsltIdx = StringUtil.getInt(request.getParameter("acmsltIdx"));
		
		if(acmsltIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();		
		
		// cliIdx 가져오기
		int cliIdx = clinicService.getCliIdx(BPL_NO);
		
		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "modify";		// 항목 order명
		String inputFlag = "modify";		// 항목 order명
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		// 1.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, itemOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("cn");
		if(contents != null){
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("cn", contents);
		}
		
		// 2. DB
		int	result = 0;
		
		// 최종승인 상태로 setting(활동일지는 자동 승인)
		String confmStatus = ConfirmProgress.APPROVE.getCode();
    	
		result = clinicService.updateAcmsltAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, cliIdx, acmsltIdx, confmStatus);
		
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.apply"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("ACTIVITY_VIEW_FORM_URL") + "&acmsltIdx=" + acmsltIdx)) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
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
/*능력개발클리닉 활동일지 끝*/
	

/*능력개발클리닉 성과보고 시작*/
	/**
	 * 능력개발클리닉 성과보고서 목록 화면
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/result_list_form.do")
	public String result_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAdmMode = attrVO.isAdmMode();		
		String targetTable = "HRD_CLI_RSLT";
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		//isCli(클리닉 선정여부)[기업이 능력개발클리닉 기업으로 최종 선정이 됐는지 확인]
		// 1 : 선정O
		// 0 : 선정X
		int isCli = clinicService.getIsCli(BPL_NO);		
		if(isCli == 0) {
			String message = "해당 메뉴는 능력개발클리닉 기업으로 선정이 되어야 이용할 수 있습니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
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
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		int cliIdx  = clinicService.getCurrentCliIdx(BPL_NO);
		param.put("CLI_IDX", cliIdx);
		// 2.2 목록수
    	totalCount = clinicService.getTotalCount(targetTable, param);
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
    		list = clinicService.getList(targetTable, param);
		}

    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
		
	/**
	 * 능력개발클리닉 성과보고서 작성 화면
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/result_write_form.do")
	public String result_write_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {


		JSONObject itemInfo = attrVO.getItemInfo();		
		
		// 속성 setting
		// 항목설정
		String submitType = "write";
		model.addAttribute("optnHashMap", getOptionHashMap(submitType, itemInfo));
		model.addAttribute("submitType", submitType);
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = "";
		
		// 로그인한 회원의 정보 중 BPL_NO
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}
		
		//isReq(신청서 등록여부)[기업이 능력개발 클리닉 신청서를 한번이라도 등록(신청X)한 적이 있는지]
		// 1 : 등록O
		// 0 : 등록X
		int isCli = clinicService.getIsCli(BPL_NO);		
		if(isCli == 0) {
			String message = "능력개발클리닉 기업으로 선정되지 않아서 성과보고서를 작성할 수 없습니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
						
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		int isPlan = clinicService.getIsPlan(cliIdx);
		if(isPlan == 0) {
			String message = "해당연도에 등록된 훈련계획서가 존재하지 않습니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		// isPlan(기업의 올해 능력개발클리닉 훈련계획서가 최종승인이 됐는지 확인)
		// 1 : 최종승인O
		// 0 : 최종승인X
		int isPlanApprove = clinicService.getIsApprove("HRD_CLI_PLAN", cliIdx);
		if(isPlanApprove == 0) {
			String message = "능력개발클리닉 훈련계획서가 최종승인되지 않았습니다. 훈련계획서를 최종승인 받고 성과보고서를 작성해 주세요.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		// isResult
		// 1 : 최종승인O
		// 0 : 최종승인X
		int isResult = clinicService.getIsResult(cliIdx);
		if(isResult > 0) {
			String message = "해당연도에 작성한 능력개발클리닉 성과보고서가 이미 존재합니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		DataMap reqList = null;
		DataMap corpInfo = null;
		
		// fnIdx
		int fnIdx = attrVO.getFnIdx();
		
		List<Object> picList = null;
		// 클리닉 활동 계획 기업 담당자
		picList = clinicService.getList("HRD_CLI_PLAN_CORP_PIC", "CLI_IDX", cliIdx, "PIC_IDX");
		
		// 훈련계획서에서 선택한 지원항목 목록 가져오기
		List<Object> subList = null;
		int planIdx = clinicService.getMaxIdx("HRD_CLI_PLAN", "PLAN_IDX", cliIdx);
		subList = clinicService.getList("HRD_CLI_PLAN_SUB","PLAN_IDX", planIdx, "ESSNTL_SE, A.SPORT_ITEM_CD");
				
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CLI_IDX", cliIdx));
		param.put("searchList", searchList);
		// 작성된 신청서에서 가져온 해당 기업정보 
		reqList = clinicService.getModify("HRD_CLI_REQ", param);

		param.put("BPL_NO", BPL_NO);
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);		

    	// 2. 기본경로
    	fn_setCommonPath(attrVO);

    	// 3. form action
    	model.addAttribute("corpInfo", corpInfo);
    	model.addAttribute("reqList", reqList);
    	model.addAttribute("subList", subList);
    	model.addAttribute("picList", picList);
    	model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());
    	
		return getViewPath("/input");
	}
		
	/**
	 * 능력개발클리닉 성과보고서 신청
	 * 	ㄴ 상태값 : 10
	 * 	ㄴ APPLY
	 * 	ㄴ 최초 신청 시 insert -> 임시저장 이후 신청 시 update
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(method=RequestMethod.POST, value = "/result_apply.do")
	public String result_apply(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();		
		

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write";		// 항목 order명
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray planInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "result_insert_column_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		// 1.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, planInsertColumnOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
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
		
		// 2. DB
		int	result = 0;
		// 임시저장 상태로 setting
		String confmStatus = ConfirmProgress.APPLY.getCode();
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("BPL_NO", BPL_NO);
		//클리닉 테이블에서 기업의 CLI_IDX를 가져온다.
		int cliIdx  = clinicService.getCliIdx(BPL_NO);

		//HRD_CLI_RESULT 테이블에서 등록된 훈련계획서가 있는지 확인한다
		int isResult = clinicService.getIsResult(cliIdx);
		if(isResult == 0) {
			//클리닉 훈련계획서 insert(
			result = clinicService.insertResultAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, cliIdx, confmStatus);
			
		}else {			
			//클리닉신청서 update
			parameterMap.put("cliIdx", cliIdx);
			result = clinicService.updateResultAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, cliIdx, confmStatus);
		}		    	    	    	
    	
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("RESULT_LIST_FORM_URL")));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.apply2"), "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
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
		
	/**
	 * 능력개발클리닉 성과보고서 임시저장
	 * 	ㄴ 상태값 : 5
	 * 	ㄴ TEMPSAVE
	 * 	ㄴ 최초 임시저장 시 insert -> 이 후 임시저장 시 update
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(method=RequestMethod.POST, value = "/result_tempSave.do")
	public String result_tempSave(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();		
		

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write";		// 항목 order명
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray planInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "result_insert_column_order");
		parameterMap.put("_itemInputTypeFlag", submitType);

		
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
				
		// 2. DB
		int	result = 0;
		// 임시저장 상태로 setting
		String confmStatus = ConfirmProgress.TEMPSAVE.getCode();
		Map<String, Object> param = new HashMap<String, Object>();
		
		
		param.put("BPL_NO", BPL_NO);
		//클리닉 테이블에서 기업의 CLI_IDX를 가져온다.
		int cliIdx  = clinicService.getCliIdx(BPL_NO);

		//HRD_CLI_RESULT 테이블에서 등록된 훈련계획서가 있는지 확인한다
		int isResult = clinicService.getIsResult(cliIdx);
		if(isResult == 0) {
			//클리닉 훈련계획서 insert(
			result = clinicService.insertResultAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, cliIdx, confmStatus);
			
		}else {			
			//클리닉신청서 update
			parameterMap.put("cliIdx", cliIdx);
			result = clinicService.updateResultAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, cliIdx, confmStatus);
		}		    	    	    	
    	
    	
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("RESULT_LIST_FORM_URL")));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.tempsave"), "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
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
				
	/**
	 * 능력개발클리닉 성과보고서서 상세보기 화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/result_view_form.do")
	public String result_view_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
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
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		//url 변조 접근 방지
		int isRequestBplNo = clinicService.getCompareRequestAndVoByBplNo("HRD_CLI_RSLT","RESLT_IDX",resltIdx, BPL_NO);

		
		if(isRequestBplNo == 0) {
			String message = "정상적인 접근이 아닙니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		
		// 기업정보 
		DataMap corpInfo = null;
		List<Object> picList = null;
		List<Object> kpiList = null;
		List<Object> trList = null;
		DataMap reqList = null;
		
		//해당기업의 컨설팅에 대한 만족도 조사 결과 리스트
		List<Object> trainingCntList = null;
		List<Object> surveyTargetList = null;
		List<Object> answerList = null;
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CLI_IDX", cliIdx));
		param.put("searchList", searchList);
		// 작성된 신청서에서 가져온 해당 기업정보 
		reqList = clinicService.getView("HRD_CLI_REQ", param);
		
		// 작성된 신청서의 정보를 가져와 세팅한 후 searchList를 비운다.
		searchList.clear();
		
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);
		// 클리닉 활동 계획 기업 담당자
		picList = clinicService.getList("HRD_CLI_PLAN_CORP_PIC", "CLI_IDX", cliIdx, "PIC_IDX");
		// 클리닉 활동 계획 KPI 결과
		kpiList = clinicService.getList("HRD_CLI_RSLT_KPI","RESLT_IDX", resltIdx, "RESLT_IDX");
		// 클리닉 활동 계획 세부 훈련결과(연간)
		trList = clinicService.getList("HRD_CLI_RSLT_TR_SUB","RESLT_IDX", resltIdx, "TR_DTL_IDX");
		
		//해당기업의 컨설팅에 대한 만족도 조사 결과 리스트
		trainingCntList = clinicService.getTrainingCntList(BPL_NO);
		surveyTargetList = clinicService.getSurveyTargetList(BPL_NO);
		answerList = clinicService.getAnswerList(BPL_NO);
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "RESLT_IDX", resltIdx));
		param.put("searchList", searchList);
		
		// 훈련계획서에서 선택한 지원항목 목록 가져오기
		List<Object> subList = null;
		int planIdx = clinicService.getMaxIdx("HRD_CLI_PLAN", "PLAN_IDX", cliIdx);
		subList = clinicService.getList("HRD_CLI_PLAN_SUB","PLAN_IDX", planIdx, "ESSNTL_SE, A.SPORT_ITEM_CD");
		
		// 2.2 상세정보
		dt = clinicService.getView("HRD_CLI_RSLT", param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "view";

		// 3.2 속성 setting
		model.addAttribute("dt", dt);
		model.addAttribute("submitType", submitType);											// 페이지구분
		model.addAttribute("corpInfo", corpInfo);
		model.addAttribute("reqList", reqList);
		model.addAttribute("picList", picList);
		model.addAttribute("kpiList", kpiList);
		model.addAttribute("subList", subList);
		model.addAttribute("trList", trList);
		model.addAttribute("trainingCntList", trainingCntList);
		model.addAttribute("surveyTargetList", surveyTargetList);
		model.addAttribute("answerList", answerList);
		model.addAttribute("optnHashMap", optnHashMap);									// 항목코드
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
		
	/**
	 * 능력개발클리닉 성과보고서 수정 화면
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
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		//url 변조 접근 방지
		int isRequestBplNo = clinicService.getCompareRequestAndVoByBplNo("HRD_CLI_RSLT","RESLT_IDX",resltIdx, BPL_NO);

		
		if(isRequestBplNo == 0) {
			String message = "정상적인 접근이 아닙니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		
		// 기업정보 
		DataMap corpInfo = null;
		List<Object> picList = null;
		List<Object> kpiList = null;
		List<Object> trList = null;
		DataMap reqList = null;
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CLI_IDX", cliIdx));
		param.put("searchList", searchList);
		// 작성된 신청서에서 가져온 해당 기업정보 
		reqList = clinicService.getModify("HRD_CLI_REQ", param);
		
		// 작성된 신청서의 정보를 가져와 세팅한 후 searchList를 비운다.
		searchList.clear();
		
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);
		// 클리닉 활동 계획 기업 담당자
		picList = clinicService.getList("HRD_CLI_PLAN_CORP_PIC", "CLI_IDX", cliIdx, "PIC_IDX");
		// 클리닉 활동 계획 KPI 결과
		kpiList = clinicService.getList("HRD_CLI_RSLT_KPI","RESLT_IDX", resltIdx, "KPI_IDX");
		// 클리닉 활동 계획 세부 훈련결과(연간)
		trList = clinicService.getList("HRD_CLI_RSLT_TR_SUB","RESLT_IDX", resltIdx, "TR_DTL_IDX");
		
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
		
		// 훈련계획서에서 선택한 지원항목 목록 가져오기
		List<Object> subList = null;
		int planIdx = clinicService.getMaxIdx("HRD_CLI_PLAN", "PLAN_IDX", cliIdx);
		subList = clinicService.getList("HRD_CLI_PLAN_SUB","PLAN_IDX", planIdx, "ESSNTL_SE, A.SPORT_ITEM_CD");
						
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
	 * 능력개발클리닉 성과보고서 회수
	 * 	ㄴ 상태값 : 20
	 *  ㄴ WITHDRAW
	 *  ㄴ 상태값만 변경한다.
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/result_withdraw.do")
	public String result_withdraw(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_RSLT";
		String targetColumn = "RESLT_IDX";
		
		// 신청상태를 insert할 targetTable(HRD_CLI_REQ_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
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
		
		// 회수 상태로 setting
		String confmStatus = ConfirmProgress.WITHDRAW.getCode();
		
		
		// 접수 상태로 HRD_CLI_RSLT 승인상태 update하기
    	int updatePlan = clinicService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updatePlan > 0) {
    		// HRD_CLI_RSLT_CONFM 테이블에 접수 상태 insert하기
    		int updatePlanConfm = clinicService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updatePlanConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
        		model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.withdraw"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("RESULT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 능력개발클리닉 성과보고서 중도포기요청
	 * 	ㄴ 상태값 : 75
	 *  ㄴ DROPREQUEST
	 *  ㄴ 상태값만 변경한다.
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/result_dropRequest.do")
	public String result_dropRequest(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_RSLT";
		String targetColumn = "RESLT_IDX";
		
		// 신청상태를 insert할 targetTable(HRD_CLI_REQ_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
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
		
		// 회수 상태로 setting
		String confmStatus = ConfirmProgress.DROPREQUEST.getCode();
		
		// 접수 상태로 HRD_CLI_RSLT 승인상태 update하기
    	int updatePlan = clinicService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updatePlan > 0) {
    		// HRD_CLI_RSLT_CONFM 테이블에 접수 상태 insert하기
    		int updatePlanConfm = clinicService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updatePlanConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
        		model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.droprequest"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("RESULT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
		
	/**
	 * 능력개발클리닉 성과보고서 반려요청
	 * 	ㄴ 상태값 : 35
	 *  ㄴ RETURNREQUEST
	 *  ㄴ 상태값만 변경한다.
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/result_returnRequest.do")
	public String result_returnRequest(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		// 신청상태를 업데이트할 targetTable
		String targetTable = "HRD_CLI_RSLT";
		String targetColumn = "RESLT_IDX";
		
		// 신청상태를 insert할 targetTable(HRD_CLI_REQ_CONFM 테이블은 신청상태가 변경될 때마다 새로 insert)
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
		
		// 회수 상태로 setting
		String confmStatus = ConfirmProgress.RETURNREQUEST.getCode();
		
		// 접수 상태로 HRD_CLI_RSLT 승인상태 update하기
    	int updatePlan = clinicService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updatePlan > 0) {
    		// HRD_CLI_RSLT_CONFM 테이블에 접수 상태 insert하기
    		int updatePlanConfm = clinicService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updatePlanConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
        		model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.returnrequest"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("RESULT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
				
/*능력개발클리닉 성과보고 끝*/

/*능력개발클리닉 비용청구 시작*/
	/*
	 *  sport_item_cd
	 * 01	훈련계획 수립
	 * 02	성과컨설팅
	 * 03	자체훈련 1회
	 * 04	HRD 역량개발
	 * 05	클리닉 성과보고
	 * 06	심화컨설팅
	 * 07	과정개발컨설팅
	 * 08	판로개척/인력채용
	 * 09	HRD 성과교류
	 * 10	훈련과정 자체개발
	 * 
	 * */
	
	
	/**
	 * 능력개발클리닉 비용청구 목록 화면
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/support_list_form.do")
	public String support_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAdmMode = attrVO.isAdmMode();		
		String targetTable = "HRD_CLI_SPT";
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		//isCli(클리닉 선정여부)[기업이 능력개발클리닉 기업으로 최종 선정이 됐는지 확인]
		// 1 : 선정O
		// 0 : 선정X
		int isCli = clinicService.getIsCli(BPL_NO);		
		if(isCli == 0) {
			String message = "해당 메뉴는 능력개발클리닉 기업으로 선정이 되어야 이용할 수 있습니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
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
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		int cliIdx  = clinicService.getCurrentCliIdx(BPL_NO);
		param.put("CLI_IDX", cliIdx);
		// 2.2 목록수
    	totalCount = clinicService.getTotalCount(targetTable, param);
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
    		list = clinicService.getList(targetTable, param);
		}

    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
	
	/**
	 * 능력개발클리닉 비용청구 작성 화면
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/support_write_form.do")
	public String support_write_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {


		JSONObject itemInfo = attrVO.getItemInfo();		
		
		// 속성 setting
		// 항목설정
		String submitType = "write";
		model.addAttribute("optnHashMap", getOptionHashMap(submitType, itemInfo));
		model.addAttribute("submitType", submitType);
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = "";
		
		// 로그인한 회원의 정보 중 BPL_NO
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}
		
		//isReq(신청서 등록여부)[기업이 능력개발 클리닉 신청서를 한번이라도 등록(신청X)한 적이 있는지]
		// 1 : 등록O
		// 0 : 등록X
		int isCli = clinicService.getIsCli(BPL_NO);		
		if(isCli == 0) {
			String message = "능력개발클리닉 기업으로 선정되지 않아서 지원금 신청서를 작성할 수 없습니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
						
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		clinicService.getCliValidStartDate(BPL_NO);
		int isPlan = clinicService.getIsPlan(cliIdx);
		if(isPlan == 0) {
			String message = "해당연도에 등록된 훈련계획서가 존재하지 않습니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		// 훈련계획서가 최종승인이 되었는지 확인
		int isPlanApprove = clinicService.getIsApprove("HRD_CLI_PLAN", cliIdx);
		if(isPlanApprove == 0) {
			String message = "해당연도에 등록된 훈련계획서가 최종승인 되지 않았습니다. 최종승인이 된 후 비용청구를 진행해 주세요.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		// 성과보고서 최종승인 유무
		int isResultApprove = clinicService.getIsApprove("HRD_CLI_RSLT", cliIdx);
		
		DataMap reqList = null;
		DataMap corpInfo = null;
		
		// fnIdx
		int fnIdx = attrVO.getFnIdx();

		//지원항목 + 금액
		List<Object> sportList = null;
		// 당해연도 기지원금액
		List<Object> alreadyPayList = null;
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
		alreadyPayList = clinicService.getSptPayList(cliIdx, 0);
		activityCode01 = clinicService.getPlanCode(cliIdx, 0);
		activityCode04 = clinicService.getActivityCode(cliIdx, "04", 0);
		activityCode05 = clinicService.getResultCode(cliIdx, 0);
		activityCode08 = clinicService.getActivityCode(cliIdx, "08", 0);
		activityCode09 = clinicService.getActivityCode(cliIdx, "09", 0);
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CLI_IDX", cliIdx));
		param.put("searchList", searchList);
		// 작성된 신청서에서 가져온 해당 기업정보 
		reqList = clinicService.getModify("HRD_CLI_REQ", param);

		param.put("BPL_NO", BPL_NO);
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);	
		
		// 훈련계획서에서 선택한 지원항목 목록 가져오기
		List<Object> planSubList = null;
		int planIdx = clinicService.getMaxIdx("HRD_CLI_PLAN", "PLAN_IDX", cliIdx);
		planSubList = clinicService.getList("HRD_CLI_PLAN_SUB","PLAN_IDX", planIdx, "ESSNTL_SE, A.SPORT_ITEM_CD");

    	// 2. 기본경로
    	fn_setCommonPath(attrVO);

    	// 3. form action
    	model.addAttribute("isResultApprove", isResultApprove);
    	model.addAttribute("corpInfo", corpInfo);
    	model.addAttribute("planSubList", planSubList);
    	model.addAttribute("reqList", reqList);
    	model.addAttribute("sportList", sportList);
    	model.addAttribute("alreadyPayList", alreadyPayList);
    	model.addAttribute("activityList01", activityCode01);
    	model.addAttribute("activityList04", activityCode04);
    	model.addAttribute("activityList05", activityCode05);
    	model.addAttribute("activityList08", activityCode08);
    	model.addAttribute("activityList09", activityCode09);
    	model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
    	
		return getViewPath("/input");
	}
	
	/**
	 * 능력개발클리닉 비용신청 상세보기화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/support_view_form.do")
	public String support_view_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		int fnIdx = attrVO.getFnIdx();

		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int sportIdx = StringUtil.getInt(request.getParameter("sportIdx"));
		
		if(sportIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "modify");
		
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		//url 변조 접근 방지
		int isRequestBplNo = clinicService.getCompareRequestAndVoByBplNo("HRD_CLI_SPT","SPORT_IDX",sportIdx, BPL_NO);

		
		if(isRequestBplNo == 0) {
			String message = "정상적인 접근이 아닙니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		
		// 기업정보 
		DataMap reqList = null;
		DataMap corpInfo = null;
		
		//지원항목 + 금액
		List<Object> sportList = null;
		List<Object> subList = null;
		List<Object> subListGroup = null;

		// 당해연도 기지원금액
		List<Object> alreadyPayList = null;
		
		sportList = clinicService.getSportList();
		alreadyPayList = clinicService.getSptPayList(cliIdx, sportIdx);
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CLI_IDX", cliIdx));
		param.put("searchList", searchList);

		param.put("BPL_NO", BPL_NO);
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);	
		param.put("searchList", searchList);
		// 작성된 신청서에서 가져온 해당 기업정보 
		reqList = clinicService.getModify("HRD_CLI_REQ", param);
		
		// 2.2 상세정보
		searchList.add(new DTForm("A." + "SPORT_IDX", sportIdx));
		param.put("searchList", searchList);
		
		dt = clinicService.getView("HRD_CLI_SPT", param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		
		subList = clinicService.getList("HRD_CLI_SPT_SUB","SPORT_IDX", sportIdx, "DTL_SN");
		subListGroup = clinicService.getSptSubGroupBy(cliIdx, sportIdx);
		
		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "view";

		// 3.2 속성 setting
		model.addAttribute("dt", dt);
		model.addAttribute("submitType", submitType);											// 페이지구분
		model.addAttribute("sportList", sportList);
		model.addAttribute("alreadyPayList", alreadyPayList);
		model.addAttribute("corpInfo", corpInfo);
		model.addAttribute("reqList", reqList);
		model.addAttribute("subList", subList);
		model.addAttribute("subListGroup", subListGroup);
		model.addAttribute("optnHashMap", optnHashMap);									// 항목코드
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	/**
	 * 능력개발클리닉 훈련계획서 수정 화면
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

		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int sportIdx = StringUtil.getInt(request.getParameter("sportIdx"));
		
		if(sportIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "modify");
		
		DataMap dt = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		//url 변조 접근 방지
		int isRequestBplNo = clinicService.getCompareRequestAndVoByBplNo("HRD_CLI_SPT","SPORT_IDX",sportIdx, BPL_NO);

		
		if(isRequestBplNo == 0) {
			String message = "정상적인 접근이 아닙니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		
		// 기업정보 
		DataMap reqList = null;
		DataMap corpInfo = null;
		
		//지원항목 + 금액
		List<Object> sportList = null;
		List<Object> subList = null;
		List<Object> subListGroup = null;
		
		// 당해연도 기지원금액
		List<Object> alreadyPayList = null;
		
		sportList = clinicService.getSportList();
		alreadyPayList = clinicService.getSptPayList(cliIdx, sportIdx);
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CLI_IDX", cliIdx));
		param.put("searchList", searchList);

		param.put("BPL_NO", BPL_NO);
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(fnIdx, param);	
		param.put("searchList", searchList);
		// 작성된 신청서에서 가져온 해당 기업정보 
		reqList = clinicService.getModify("HRD_CLI_REQ", param);
		
		// 2.2 상세정보
		searchList.add(new DTForm("A." + "SPORT_IDX", sportIdx));
		param.put("searchList", searchList);
		
		dt = clinicService.getModify("HRD_CLI_SPT", param);
		if(dt == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
		// 훈련계획서에서 선택한 지원항목 목록 가져오기
		List<Object> planSubList = null;
		int planIdx = clinicService.getMaxIdx("HRD_CLI_PLAN", "PLAN_IDX", cliIdx);
		planSubList = clinicDctService.getList("HRD_CLI_PLAN_SUB","PLAN_IDX", planIdx, "ESSNTL_SE, A.SPORT_ITEM_CD");
		
		subList = clinicService.getList("HRD_CLI_SPT_SUB","SPORT_IDX", sportIdx, "DTL_SN");
		subListGroup = clinicService.getSptSubGroupBy(cliIdx, sportIdx);
		
		// 성과보고서 최종승인 유무
		int isResultApprove = clinicService.getIsApprove("HRD_CLI_RSLT", cliIdx);
		
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
		activityCode01 = clinicService.getPlanCode(cliIdx, sportIdx);
		activityCode04 = clinicService.getActivityCode(cliIdx, "04", sportIdx);
		activityCode05 = clinicService.getResultCode(cliIdx, sportIdx);
		activityCode08 = clinicService.getActivityCode(cliIdx, "08", sportIdx);
		activityCode09 = clinicService.getActivityCode(cliIdx, "09", sportIdx);
		
		// 3 속성 setting
		// 3.1 항목설정
		String submitType = "modify";

		// 3.2 속성 setting
		model.addAttribute("dt", dt);
		model.addAttribute("submitType", submitType);											// 페이지구분
		model.addAttribute("isResultApprove", isResultApprove);
		model.addAttribute("sportList", sportList);
		model.addAttribute("alreadyPayList", alreadyPayList);
		model.addAttribute("corpInfo", corpInfo);
		model.addAttribute("reqList", reqList);
		model.addAttribute("planSubList", planSubList);
		model.addAttribute("subList", subList);
		model.addAttribute("activityList01", activityCode01);
    	model.addAttribute("activityList04", activityCode04);
    	model.addAttribute("activityList05", activityCode05);
    	model.addAttribute("activityList08", activityCode08);
    	model.addAttribute("activityList09", activityCode09);
		model.addAttribute("subListGroup", subListGroup);
		model.addAttribute("optnHashMap", optnHashMap);									// 항목코드
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/input");
	}
	
	/**
	 * 능력개발클리닉 지원금 신청서 임시저장
	 * 	ㄴ 상태값 : 5
	 * 	ㄴ TEMPSAVE
	 * 	ㄴ 최초 임시저장 시 insert -> 이 후 임시저장 시 update
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(method=RequestMethod.POST, value = "/support_tempSave.do")
	public String support_tempSave(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();		

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write";		// 항목 order명
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);

		// 2. DB

		int	result = 0;
		// 임시저장 상태로 setting
		String confmStatus = ConfirmProgress.TEMPSAVE.getCode();
		
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
		// 지원금 신청서는 여러개 작성할 수 있음 parameteMap에 sportIdx 값이 있으면 수정, 없으면 새로 등록
		String sportIdx = (String) parameterMap.get("sportIdx"); 
		if(sportIdx.isEmpty()) {
			//클리닉 훈련계획서 insert(
			result = clinicService.insertSupportAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, cliIdx, confmStatus);
			
		}else {			
			//클리닉신청서 update
			parameterMap.put("cliIdx", cliIdx);
			result = clinicService.updateSupportAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, cliIdx, confmStatus);
		}    	    	    	
    	
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	String inputNpname = fn_dsetInputNpname(settingInfo);
        	String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("SUPPORT_LIST_FORM_URL")));
        	if(StringUtil.isEquals(inputNpname, "IDX_VIEW")) toUrl += "&" + JSONObjectUtil.getString(settingInfo, "idx_name") + "=" + result;
			model.addAttribute("message",   MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.tempsave"), "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} 
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
		
	/**
	 * 능력개발클리닉 비용신청서 신청
	 * 	ㄴ 상태값 : 10
	 * 	ㄴ APPLY
	 * 	ㄴ 최초 신청 시 insert -> 임시저장 이후 신청 시 update
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(method=RequestMethod.POST, value = "/support_apply.do")
	public String support_apply(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();		
		
		int cliIdx = clinicService.getCliIdx(BPL_NO);

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
		int	result = 0;
		
		// 신청 상태로 setting
		String confmStatus = ConfirmProgress.APPLY.getCode();
		
		// 지원금 신청서는 여러개 작성할 수 있음 parameteMap에 sportIdx 값이 있으면 수정, 없으면 새로 등록
		String sportIdx = (String) parameterMap.get("sportIdx"); 
		if(sportIdx.isEmpty()) {
			//클리닉 훈련계획서 insert(
			result = clinicService.insertSupportAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, cliIdx, confmStatus);
			
		}else {			
			//클리닉신청서 update
			parameterMap.put("cliIdx", cliIdx);
			result = clinicService.updateSupportAsParamCode(uploadModulePath, request.getRemoteAddr(), parameterMap, attrVO, cliIdx, confmStatus);
		}
		
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.apply2"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} 
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 능력개발클리닉 신청서 회수
	 * 	ㄴ 상태값 : 20
	 *  ㄴ WITHDRAW
	 *  ㄴ 상태값만 변경한다.
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/support_withdraw.do")
	public String support_withdraw(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
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
		String confmStatus = ConfirmProgress.WITHDRAW.getCode();
		
		// HRD_CLI_REQ 승인상태 update하기
    	int updateSpt = clinicService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updateSpt > 0) {
    		// HRD_CLI_SPT_CONFM 테이블에 status insert하기
    		int updateSptConfm = clinicService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updateSptConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
        		model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.withdraw"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 능력개발클리닉 신청서 반려요청
	 * 	ㄴ 상태값 : 35
	 *  ㄴ RETURNREQUEST
	 *  ㄴ 상태값만 변경한다.
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/support_returnRequest.do")
	public String support_returnRequest(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
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
		
		// 반려요청 상태로 setting
		String confmStatus = ConfirmProgress.RETURNREQUEST.getCode();

		// 반려요청 상태로 HRD_CLI_REQ 승인상태 update하기
    	int updateSpt = clinicService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updateSpt > 0) {
    		// HRD_CLI_SPT_CONFM 테이블에 접수 상태 insert하기
    		int updateSptConfm = clinicService.insertConfm(targetTable2,targetColumn , targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updateSptConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
        		model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.returnrequest"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 능력개발클리닉 중도포기요청
	 * 	ㄴ 상태값 : 75
	 *  ㄴ DROPREQUEST
	 *  ㄴ 상태값만 변경한다.
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
		
		// 중도포기요청 상태로 setting
		String confmStatus = ConfirmProgress.DROPREQUEST.getCode();

		// 중도포기요청 상태로 HRD_CLI_REQ 승인상태 update하기
    	int updateReq = clinicService.updateStatus(targetTable, targetColumn, confmStatus, cliIdx, keyIdx, regiIp);
    	if(updateReq > 0) {
    		// HRD_CLI_REQ_CONFM 테이블에 접수 상태 insert하기
    		int updateReqConfm = clinicService.insertConfm(targetTable2, targetColumn, targetColumn2, confmStatus, cliIdx, keyIdx, regiIp);
    		if(updateReqConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
        		model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.droprequest"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}	
/*능력개발클리닉 비용청구 끝*/
	
/*능력개발클리닉 종합관리 시작*/
	
	/**
	 * 능력개발클리닉 대시보드 목록 화면
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/dashboard_list_form.do")
	public String dashboard_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAdmMode = attrVO.isAdmMode();		
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		//isCli(클리닉 선정여부)[기업이 능력개발클리닉 기업으로 최종 선정이 됐는지 확인]
		// 1 : 선정O
		// 0 : 선정X
		int isCli = clinicService.getIsCli(BPL_NO);		
		if(isCli == 0) {
			String message = "해당 메뉴는 능력개발클리닉 기업으로 선정이 되어야 이용할 수 있습니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
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
    	
		return getViewPath("/list");
	}
	
	
	/* 
	 * 대시보드 검색
	 */ 
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.GET, value = "/dashboard_list_form.do", params="isAjax")
	public ModelAndView dashboard_list_form( @RequestParam("isAjax") String isAjax, @ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		
		List<Object> activityList = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
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
/*능력개발클리닉 종합관리 끝*/
	
/*능력개발클리닉 이력관리 시작*/
	
	/**
	 * 능력개발클리닉 이력관리 목록 화면
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/total_list_form.do")
	public String total_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAdmMode = attrVO.isAdmMode();		
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		//isCli(클리닉 선정여부)[기업이 능력개발클리닉 기업으로 최종 선정이 됐는지 확인]
		// 1 : 선정O
		// 0 : 선정X
		int isCli = clinicService.getIsCli(BPL_NO);		
		if(isCli == 0) {
			String message = "해당 메뉴는 능력개발클리닉 기업으로 선정이 되어야 이용할 수 있습니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
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
    	
		return getViewPath("/list");
	}
	
	
	/* 
	 * 이력관리 검색
	 */ 
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.GET, value = "/total_list_form.do", params="isAjax")
	public ModelAndView total_list_form( @RequestParam("isAjax") String isAjax, @ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
		
		List<Object> activityList = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		int cliIdx  = clinicService.getCliIdx(BPL_NO);
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
	

	
/*능력개발클리닉 이력관리 끝*/
	
//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ밑에부터 리펙토링 전 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ	
	
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
		Map<String, Object> fileInfo = clinicService.getFileUpload(uploadModulePath, fnIdx, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
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
		 * */
		
		// 추가경로 
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String baseQueryString = StringUtil.getString(request.getAttribute("baseQueryString"));
		
		/* 능력개발 클리닉 관련 경로 */
		
		/* 신청 */		
		String infoFormUrl 		= "request_info_form.do";						// 안내사항 화면
		String infoAgreeFormUrl = "request_infoAgree_form.do";					// 안내사항 동의 화면				
		String writeFormUrl = "request_write_form.do";							// 신청서 작성 화면	
		String listFormUrl 	= "request_list_form.do";							// 신청서 목록 화면
		String viewFormUrl 	= "request_view_form.do";							// 신청서 상세보기 화면		
		String modifyFormUrl = "request_modify_form.do";						// 신청서 수정 화면	
		String modifyUrl = "request_modify.do";									// 신청서 수정
		String tempSaveUrl = "request_tempSave.do";								// 신청서 임시저장		
		String withDrawUrl = "request_withdraw.do";							// 신청서 회수		
		String applyUrl = "request_apply.do";									// 신청서 신청	
		String returnReqeustUrl = "request_returnRequest.do";					// 신청서 반려요청
		String dropReqeustUrl = "request_dropRequest.do";						// 신청서 중도포기
//		String downloadUrl = "request_download.do";						// 파일 다운로드
		
		/* 훈련계획 수립*/
		String plan_writeFormUrl = "plan_write_form.do";						// 계획서 작성 화면	
		String plan_listFormUrl = "plan_list_form.do";							// 계획서 목록 화면
		String plan_viewFormUrl = "plan_view_form.do";							// 계획서 상세보기 화면		
		String plan_modifyFormUrl = "plan_modify_form.do";						// 계획서 수정 화면	
		String plan_modifyUrl = "plan_modify.do";								// 계획서 수정
		String plan_tempSaveUrl = "plan_tempSave.do";							// 계획서 임시저장		
		String plan_withDrawUrl = "plan_withdraw.do";						// 계획서 회수		
		String plan_applyUrl = "plan_apply.do";									// 계획서 신청	
		String plan_returnReqeustUrl = "plan_returnRequest.do";					// 계획서 반려요청
		String plan_modifyReqeustUrl = "plan_modifyRequest.do";					// 계획서 변경요청
		String plan_dropReqeustUrl = "plan_dropRequest.do";						// 계획서 중도포기
//		String plan_downloadUrl = "plan_download.do";							// 계획서 다운로드
		
		
		/* 활동일지 */
		String activity_writeFormUrl = "activity_write_form.do";				// 활동일지 작성 화면	
		String activity_listFormUrl = "activity_list_form.do";					// 활동일지 목록 화면
		String activity_viewFormUrl = "activity_view_form.do";					// 활동일지 상세보기 화면		
		String activity_modifyFormUrl = "activity_modify_form.do";				// 활동일지 수정 화면	
		String activity_modifyUrl = "activity_modify.do";						// 활동일지 수정
		String activity_tempSaveUrl = "activity_tempSave.do";					// 활동일지 임시저장		
		String activity_applyUrl = "activity_apply.do";							// 활동일지 신청	
//		String activity_downloadUrl = "activity_download.do";					// 활동일지 다운로드
		
		/* 성과보고 */
		String result_writeFormUrl = "result_write_form.do";					// 성과보고 작성 화면	
		String result_listFormUrl = "result_list_form.do";						// 성과보고 목록 화면
		String result_viewFormUrl = "result_view_form.do";						// 성과보고 상세보기 화면		
		String result_modifyFormUrl = "result_modify_form.do";					// 성과보고 수정 화면	
		String result_modifyUrl = "result_modify.do";							// 성과보고 수정
		String result_tempSaveUrl = "result_tempSave.do";						// 성과보고 임시저장		
		String result_withDrawUrl = "result_withdraw.do";						// 성과보고 회수		
		String result_applyUrl = "result_apply.do";								// 성과보고 신청	
		String result_returnReqeustUrl = "result_returnRequest.do";				// 성과보고 반려요청
		String result_dropReqeustUrl = "result_dropRequest.do";					// 성과보고 중도포기
//		String result_downloadUrl = "result_download.do";						// 성과보고 다운로드
		
		/* 비용청구 */
		String support_writeFormUrl = "support_write_form.do";					// 비용청구 작성 화면	
		String support_listFormUrl = "support_list_form.do";					// 비용청구 목록 화면
		String support_viewFormUrl = "support_view_form.do";					// 비용청구 상세보기 화면		
		String support_modifyFormUrl = "support_modify_form.do";				// 비용청구 수정 화면	
		String support_modifyUrl = "support_modify.do";							// 비용청구 수정
		String support_tempSaveUrl = "support_tempSave.do";						// 비용청구 임시저장		
		String support_withDrawUrl = "support_withdraw.do";						// 비용청구 회수		
		String support_applyUrl = "support_apply.do";							// 비용청구 신청	
		String support_returnReqeustUrl = "support_returnRequest.do";			// 비용청구 반려요청
		String support_dropReqeustUrl = "support_dropRequest.do";				// 비용청구 중도포기
//		String support_downloadUrl = "support_download.do";						// 비용청구 다운로드
		
		/* 종합관리 */
		String dashboard_listFormUrl = "dashboard_list_form.do";				// 종합관리 목록 화면
		
		/* 종합이력 */ 
		String total_listFormUrl = "total_list_form.do";						// 종합이력 목록 화면
		
			
		
		
		if(!StringUtil.isEmpty(baseQueryString)) {
			infoFormUrl += baseQueryString;
			infoAgreeFormUrl += baseQueryString;
			writeFormUrl += baseQueryString;
			listFormUrl += baseQueryString;
			viewFormUrl += baseQueryString;
			modifyFormUrl += baseQueryString;
			modifyUrl += baseQueryString;
			tempSaveUrl += baseQueryString;
			withDrawUrl += baseQueryString;
			applyUrl += baseQueryString;
			returnReqeustUrl += baseQueryString;
			dropReqeustUrl += baseQueryString;
//			downloadUrl += baseQueryString;
			
			plan_writeFormUrl += baseQueryString;
			plan_listFormUrl += baseQueryString;
			plan_viewFormUrl += baseQueryString;
			plan_modifyFormUrl += baseQueryString;
			plan_modifyUrl += baseQueryString;
			plan_tempSaveUrl += baseQueryString;
			plan_withDrawUrl += baseQueryString;
			plan_applyUrl += baseQueryString;
			plan_returnReqeustUrl += baseQueryString;
			plan_modifyReqeustUrl += baseQueryString;
			plan_dropReqeustUrl += baseQueryString;
//			plan_downloadUrl += baseQueryString;
			
			activity_writeFormUrl += baseQueryString;
			activity_listFormUrl += baseQueryString;
			activity_viewFormUrl += baseQueryString;
			activity_modifyFormUrl += baseQueryString;
			activity_modifyUrl += baseQueryString;
			activity_tempSaveUrl += baseQueryString;
			activity_applyUrl += baseQueryString;
//			activity_downloadUrl += baseQueryString;
			
			result_writeFormUrl += baseQueryString;
			result_listFormUrl += baseQueryString;
			result_viewFormUrl += baseQueryString;
			result_modifyFormUrl += baseQueryString;
			result_modifyUrl += baseQueryString;
			result_tempSaveUrl += baseQueryString;
			result_withDrawUrl += baseQueryString;
			result_applyUrl += baseQueryString;
			result_returnReqeustUrl += baseQueryString;
			result_dropReqeustUrl += baseQueryString;
//			result_downloadUrl += baseQueryString;
			
			support_writeFormUrl += baseQueryString;
			support_listFormUrl += baseQueryString;
			support_viewFormUrl += baseQueryString;
			support_modifyFormUrl += baseQueryString;
			support_modifyUrl += baseQueryString;
			support_tempSaveUrl += baseQueryString;
			support_withDrawUrl += baseQueryString;
			support_applyUrl += baseQueryString;
			support_returnReqeustUrl += baseQueryString;
			support_dropReqeustUrl += baseQueryString;
//			support_downloadUrl += baseQueryString;
			
			dashboard_listFormUrl += baseQueryString;
			
			total_listFormUrl += baseQueryString;
		}
		
		/*
		 * 1. common path 명명 패턴
		 * 		ㄴ 경로 이름_URL 
		 * 			ㄴ (ex: request_tempSave.do -> {REQUEST_TEMPSAVE_URL}
		 * */
		request.setAttribute("REQUEST_INFO_FORM_URL", infoFormUrl);
		request.setAttribute("REQUEST_INFOAGREE_FORM_URL", infoAgreeFormUrl);
		request.setAttribute("REQUEST_WRITE_FORM_URL", writeFormUrl);
		request.setAttribute("REQUEST_LIST_FORM_URL", listFormUrl);
		request.setAttribute("REQUEST_VIEW_FORM_URL", viewFormUrl);
		request.setAttribute("REQUEST_MODIFY_FORM_URL", modifyFormUrl);
		request.setAttribute("REQUEST_MODIFY_URL", modifyUrl);
		request.setAttribute("REQUEST_TEMPSAVE_URL", tempSaveUrl);
		request.setAttribute("REQUEST_WITHDRAW_URL", withDrawUrl);
		request.setAttribute("REQUEST_APPLY_URL", applyUrl);
		request.setAttribute("REQUEST_RETURNREQUEST_URL", returnReqeustUrl);
		request.setAttribute("REQUEST_DROPREQUEST_URL", dropReqeustUrl);
//		request.setAttribute("REQUEST_DOWNLOAD_URL", downloadUrl);
		
		request.setAttribute("PLAN_WRITE_FORM_URL", plan_writeFormUrl);
		request.setAttribute("PLAN_LIST_FORM_URL", plan_listFormUrl);
		request.setAttribute("PLAN_VIEW_FORM_URL", plan_viewFormUrl);
		request.setAttribute("PLAN_MODIFY_FORM_URL", plan_modifyFormUrl);
		request.setAttribute("PLAN_MODIFY_URL", plan_modifyUrl);
		request.setAttribute("PLAN_TEMPSAVE_URL", plan_tempSaveUrl);
		request.setAttribute("PLAN_WITHDRAW_URL", plan_withDrawUrl);
		request.setAttribute("PLAN_APPLY_URL", plan_applyUrl);
		request.setAttribute("PLAN_RETURNREQUEST_URL", plan_returnReqeustUrl);
		request.setAttribute("PLAN_MODIFYREQUEST_URL", plan_modifyReqeustUrl);
		request.setAttribute("PLAN_DROPREQUEST_URL", plan_dropReqeustUrl);
//		request.setAttribute("PLAN_DOWNLOAD_URL", plan_downloadUrl);
		
		request.setAttribute("ACTIVITY_WRITE_FORM_URL", activity_writeFormUrl);
		request.setAttribute("ACTIVITY_LIST_FORM_URL", activity_listFormUrl);
		request.setAttribute("ACTIVITY_VIEW_FORM_URL", activity_viewFormUrl);
		request.setAttribute("ACTIVITY_MODIFY_FORM_URL", activity_modifyFormUrl);
		request.setAttribute("ACTIVITY_MODIFY_URL", activity_modifyUrl);
		request.setAttribute("ACTIVITY_TEMPSAVE_URL", activity_tempSaveUrl);
		request.setAttribute("ACTIVITY_APPLY_URL", activity_applyUrl);
//		request.setAttribute("ACTIVITY_DOWNLOAD_URL", activity_downloadUrl);
		
		request.setAttribute("RESULT_WRITE_FORM_URL", result_writeFormUrl);
		request.setAttribute("RESULT_LIST_FORM_URL", result_listFormUrl);
		request.setAttribute("RESULT_VIEW_FORM_URL", result_viewFormUrl);
		request.setAttribute("RESULT_MODIFY_FORM_URL", result_modifyFormUrl);
		request.setAttribute("RESULT_MODIFY_URL", result_modifyUrl);
		request.setAttribute("RESULT_TEMPSAVE_URL", result_tempSaveUrl);
		request.setAttribute("RESULT_WITHDRAW_URL", result_withDrawUrl);
		request.setAttribute("RESULT_APPLY_URL", result_applyUrl);
		request.setAttribute("RESULT_RETURNREQUEST_URL", result_returnReqeustUrl);
		request.setAttribute("RESULT_DROPREQUEST_URL", result_dropReqeustUrl);
//		request.setAttribute("RESULT_DOWNLOAD_URL", result_downloadUrl);
		
		request.setAttribute("SUPPORT_WRITE_FORM_URL", support_writeFormUrl);
		request.setAttribute("SUPPORT_LIST_FORM_URL", support_listFormUrl);
		request.setAttribute("SUPPORT_VIEW_FORM_URL", support_viewFormUrl);
		request.setAttribute("SUPPORT_MODIFY_FORM_URL", support_modifyFormUrl);
		request.setAttribute("SUPPORT_MODIFY_URL", support_modifyUrl);
		request.setAttribute("SUPPORT_TEMPSAVE_URL", support_tempSaveUrl);
		request.setAttribute("SUPPORT_WITHDRAW_URL", support_withDrawUrl);
		request.setAttribute("SUPPORT_APPLY_URL", support_applyUrl);
		request.setAttribute("SUPPORT_RETURNREQUEST_URL", support_returnReqeustUrl);
		request.setAttribute("SUPPORT_DROPREQUEST_URL", support_dropReqeustUrl);
//		request.setAttribute("SUPPORT_DOWNLOAD_URL", support_downloadUrl);
		
		request.setAttribute("DASHBOARD_LIST_FORM_URL", dashboard_listFormUrl);
		
		request.setAttribute("TOTAL_LIST_FORM_URL", total_listFormUrl);
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
	 * 상태값(status)변경 : 회수 / 신청취소
	 *  ㄴ 상태값만 변경한다. 즉, 화면단에서 변경할 정보를 넘길때부터 상태값만 넘기는 방식 (기존의 service를 그대로 쓴다)
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
//	@Auth(role = Role.U)
//	@RequestMapping(method=RequestMethod.POST, value = "/updateStatus.do", params="mode")
//	public String updateStatus(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
//		boolean isAjax = attrVO.isAjax();
//		JSONObject settingInfo = attrVO.getSettingInfo();
//		JSONObject itemInfo = attrVO.getItemInfo();
//		String ajaxPName = attrVO.getAjaxPName();
//		String uploadModulePath = attrVO.getUploadModulePath();
//		int fnIdx = attrVO.getFnIdx();
//		
//		// 1. 필수 parameter 검사
//		// 1.1 필수 key parameter 검사
//		int keyIdx = StringUtil.getInt(request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name")), 0);
//		
//
//		// 3. 필수입력 체크
//		// 3.1 항목설정
//		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
//		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, "status_modify_order");
//		
//		// 4. DB		
//    	int result = clinicService.updateStatus(uploadModulePath, fnIdx, keyIdx, request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
//    	if(result > 0) {
//    		// 4.1 저장 성공
//    		
//        	// 기본경로
//        	fn_setCommonPath(attrVO);
//        	
//    		String inputNpname = fn_dsetInputNpname(settingInfo);
//			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.insert"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_" + inputNpname))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
//			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
//    	} else {
//    		// 4.3 저장 실패
//    		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
//    		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
//    	}
//
//	}
//	

	
}
