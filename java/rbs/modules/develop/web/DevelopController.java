package rbs.modules.develop.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import rbs.egovframework.LoginVO;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.egovframework.common.CommonFunction;
import rbs.egovframework.confirmProgress.ConfirmProgress;
import rbs.modules.clinic.service.ClinicService;
import rbs.modules.develop.service.DevelopDctService;
import rbs.modules.develop.service.DevelopService;

/**
 * 훈련과정 맞춤개발 모듈<br/>
 * @author LDG, KJM
 *
 */
@Controller
@ModuleMapping(moduleId="develop")
@RequestMapping({"/{siteId}/develop", "/{admSiteId}/menuContents/{usrSiteId}/develop"})
public class DevelopController extends ModuleController {
	
	@Resource(name = "developService")
	private DevelopService developService;
	
	@Resource(name = "developDctService")
	private DevelopDctService developDctService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
	@Resource(name = "jsonView")
	View jsonView;
	
	@Resource(name = "clinicService")
	private ClinicService clinicService;	
	
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
	 * [훈련과정표준개발] 표준개발 안내 콘텐츠 화면
	 * @param attrVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/develop_standard_info.do")
	public String standard_info(@ModuleAttr ModuleAttrVO attrVO, ModelMap model) throws Exception {				
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "develop_info_column");			
				
		
		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	model.addAttribute("optnHashMap", optnHashMap);	
    	model.addAttribute("submitType", "write");	
    	
		return getViewPath("/standardInfo");
	}
	
	/**
	 * [훈련과정표준개발] 맞춤개발 안내 콘텐츠 화면
	 * @param attrVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/develop_customized_info.do")
	public String customized_info(@ModuleAttr ModuleAttrVO attrVO, ModelMap model) throws Exception {				
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "develop_info_column");			
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 사업장관리번호 가져오기
		String bplNo = loginVO.getBplNo();
		// 4. 기본경로
		fn_setCommonPath(attrVO);
		
		model.addAttribute("bplNo", bplNo);	
		model.addAttribute("optnHashMap", optnHashMap);	
		
		return getViewPath("/customizedInfo");
	}
	
	/**
	 * [훈련과정표준개발] 표준개발 주치의 지원요청
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(method=RequestMethod.POST, value = "/develop_doctor_help.do")
	public String develop_doctor_help(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
		
		
		//로그인한 기업이 기업HRD이음컨설팅을 실시했는지 확인 
		int isBsc = developService.getIsBsc(BPL_NO);
		if(isBsc == 0) {
			String message = "기업HRD이음컨설팅을 실시하지 않았습니다. 기업HRD이음컨설팅을 먼저 진행해주세요";
			String toUrl = request.getContextPath() + "/web/bsisCnsl/init.do?mId=55";
				model.addAttribute("message", MessageUtil.getAlertAddWindow(attrVO.isAjax(), message, "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
				return RbsProperties.getProperty("Globals.message" + attrVO.getAjaxPName() + ".path");		
		}
		
		parameterMap.put("bplNo", BPL_NO);
		
		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write";		// 항목 order명
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray helpInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "develop_help_insert_column_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		// 1.2 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, helpInsertColumnOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("etcDemandMatter");
		if(contents != null) {
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("etcDemandMatter", contents);
		}
		
		// 2. DB
		int	result = 0;
		
		// 주치의 지원요청 상태로 setting
		String confmStatus = ConfirmProgress.SUPPORTREQUEST.getCode();
		
		// HRD_DEVELOP에 insert하기
		result = developService.insertDevelopForDoctorHelp(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, helpInsertColumnOrder, confmStatus);
    	
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	//String toUrl = PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_STANDARD_INFO_URL")));
        	model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, "주치의 지원을 성공적으로 요청하였습니다.", "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} 
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * [훈련과정표준개발] 표준개발 추천훈련 화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/develop_recommend_form.do")
	public String develop_recommend_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = null;
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}
		
		
		//로그인한 기업이 기업HRD이음컨설팅을 실시했는지 확인 
		int isBsc = developService.getIsBsc(BPL_NO);
		if(isBsc == 0) {
			String message = "기업HRD이음컨설팅을 실시하지 않았습니다. 기업HRD이음컨설팅을 먼저 진행해주세요";
				String toUrl = request.getContextPath() + "/web/bsisCnsl/init.do?mId=55";
				//String toUrl = "/web/diagnosis/apply.do?mId=53";
				model.addAttribute("message", MessageUtil.getAlertAddWindow(attrVO.isAjax(), message, "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
				return RbsProperties.getProperty("Globals.message" + attrVO.getAjaxPName() + ".path");		
		}
		
		

		
		long bsisCnsl = developService.getMaxBsisCnsl(BPL_NO);
		int rsltIdx = developService.getRsltIdxByBsiscnslIdx(bsisCnsl);
		List<Object> trainingRecommend = null;
		List<Object> trainingCodeList = new ArrayList<>();
		//로그인한 회원의 해당연도의 가장 최근 기업HRD이음컨설팅을 가져온다.(없으면 0 = AI훈련추천 api콜 / 있으면 기업HRD이음컨설팅 정보를 불러온다)
		if(bsisCnsl != 0) {
			trainingRecommend = developService.getRecommend(bsisCnsl);
//			trainingRecommend = (List<Object>) developService.getAiRecommendList("hrdk_edu_reco_system", BPL_NO, rsltIdx);
			
		}else {
			trainingRecommend = (List<Object>) developService.getAiRecommendList("hrdk_edu_develop_page", BPL_NO, 0);

			if(trainingRecommend.size() == 0) {
				model.addAttribute("message",MessageUtil.getAlert("귀사의 사업장관리번호에 대한 AI추천 정보가 없습니다. 한국산업인력공단에 문의바랍니다."));
				return RbsProperties.getProperty("Globals.fail.path");
			}
			
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObj = new JSONObject();
			//훈련과정 전체 리스트에 사용할 TP_IDX 리스트 parse
			for(int i = 0; i < trainingRecommend.size();i++) {
				jsonArray.add( trainingRecommend.get(i));				
			}
			for(int i = 0; i < jsonArray.size();i++) {
				jsonObj = jsonArray.getJSONObject(i);
				trainingCodeList.add(jsonObj.get("TP_IDX"));
			} 
			
		}
		
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "develop_info_column");

		// 3.2 속성 setting
		model.addAttribute("trainingRecommend", trainingRecommend);
		model.addAttribute("trainingCodeList", trainingCodeList);
		model.addAttribute("bsisCnsl", bsisCnsl);
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/recommendView");
	}	
	

	
	/**
	 * [훈련과정표준개발]표준개발 추천 과정 상세보기 화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/develop_training_view_form.do")
	public String develop_training_view_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		
		Map<String, Object> param = new HashMap<String, Object>();
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "develop_info_column");
		// 선택한 과정의 idx
		String tpIdx = "";
		int prtbizIdx = 0;		
		
		// 과정 정보 , 과정 상세정보 리스트 , 파일
		DataMap tpList = null;
		List<Object> tpSubList = null;
				
		// 기업정보 
		DataMap corpInfo = null;
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = "";
		
		String isFromDevelop = request.getParameter("isFromDevelop");
		
		// 로그인한 회원의 정보 중 BPL_NO
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}		
		
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		tpIdx = StringUtil.getString(request.getParameter("tpIdx"));
		prtbizIdx = StringUtil.getInt(request.getParameter("prtbizIdx"));		
		
		
		if(tpIdx == null) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}else if(prtbizIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
					
		
		
		param.put("TP_IDX", tpIdx);
		param.put("PRTBIZ_IDX", prtbizIdx);
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(99 , param);	
		
		tpList = developService.getTpInfo(param);
		tpSubList = developService.getTpSubInfo(param);
		
		if(tpList == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}
				
		model.addAttribute("optnHashMap", optnHashMap);			
		model.addAttribute("corpInfo", corpInfo);			
		model.addAttribute("tpList", tpList);			
		model.addAttribute("isFromDevelop", isFromDevelop);			
		model.addAttribute("tpSubList", tpSubList);			
		model.addAttribute("multiFileHashMap", developService.getMultiFileHashMap("HRD_DGNS_PRTBIZ_TP_FILE", "TP_IDX", tpIdx));	// multi file 목록
		model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/trainingView");
	}
			
	/**
	 * [훈련과정표준개발] 표준개발 신청서 화면
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/develop_write_form.do")
	public String develop_write_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {		
		Map<String, Object> param = new HashMap<String, Object>();
		
		// 선택한 과정의 idx
		String tpIdx = "";
		int prtbizIdx = 0;
		
		// 추천 활용 / 과정 수정 flag
		String useFlag = null;
		
		// 과정 정보 , 과정 상세정보 리스트 , 파일
		DataMap tpList = null;
		List<Object> tpSubList = null;
				
		// 기업정보 
		DataMap corpInfo = null;
		// 지부지사 idx
		List<Object> insttList = null;
		
		//리턴 페이지 이름
		String pageName = "";
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = "";
		
		// 로그인한 회원의 정보 중 BPL_NO
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}		
		long bsisCnsl = developService.getMaxBsisCnsl(BPL_NO);
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		tpIdx = StringUtil.getString(request.getParameter("tpIdx"));
		prtbizIdx = StringUtil.getInt(request.getParameter("prtbizIdx"));
		useFlag = StringUtil.getString(request.getParameter("useFlag"));
		
		//로그인한 기업이 S-OJT선정 기업인지 확인 
		if(prtbizIdx == 1) {			
			int isSojt = developService.getIsSojt(BPL_NO);
			if(isSojt == 0) {
					String message = "해당기업은 SOJT선정 기업이 아닙니다. SOJT선정 화면으로 이동합니다.";
					String toUrl = request.getContextPath() +  "/web/sojt/init.do?mId=110";
					model.addAttribute("message", MessageUtil.getAlertAddWindow(attrVO.isAjax(), message, "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
					return RbsProperties.getProperty("Globals.message" + attrVO.getAjaxPName() + ".path");		
			}	
		}
		
		//S-OJT의 경우 일반으로 3회까지 신청가능.(맞춤개발에서의 일반 포함)
		//ex) 표준개발에서 일반 S-OJT 2회 신청 + 맞춤개발에서 일반 S-OJT 1회 신청 시 한도 끝
		if(prtbizIdx == 1) {
			String sojtAvailableFlag = developService.getSojtAvailableFlag(BPL_NO);
			if(sojtAvailableFlag.equals("N")) {
				model.addAttribute("message",MessageUtil.getAlert("해당연도의 [S-OJT 일반] 과정개발 신청한도를 초과하였습니다(연간 3회)."));
				return RbsProperties.getProperty("Globals.fail.path");
			}
		}
		
		if(useFlag.equals("use")) {
			pageName = "/recomUseWriteForm";			
		}else if(useFlag.equals("reuse")) {
			pageName = "/recomReuseWriteForm";			
		}else if(useFlag.equals("new")) {
			pageName = "/newWriteForm";					
			model.addAttribute("PRTBIZ_IDX",prtbizIdx);

				if(prtbizIdx == 1) {
					model.addAttribute("BIZ_TYPE2", "S-OJT");
					model.addAttribute("TRAINING_TYPE", "일반 훈련");
					model.addAttribute("TRAINING_METHOD", "S-OJT 훈련");
				}else {
					model.addAttribute("BIZ_TYPE2", "사업주");
					model.addAttribute("TRAINING_TYPE", "사업주훈련");
					model.addAttribute("TRAINING_METHOD", "집체훈련");
				}

		}
		
		//신규작성일 경우 파라미터 널체크를 하지않는다
		if(!useFlag.equals("new")) {
			if(tpIdx == null) {
				return RbsProperties.getProperty("Globals.error.400.path");
			}else if(prtbizIdx <= 0) {
				return RbsProperties.getProperty("Globals.error.400.path");
			}
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "develop_form_column");										

		
		param.put("TP_IDX", tpIdx);
		param.put("PRTBIZ_IDX", prtbizIdx);
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(99 , param);	
		
		insttList = clinicService.getInsttList(param);
		
		/*신규작성일 경우 가져올 훈련 과정 데이터가 없기때문에 건너뛴다.
		대신 이전 선택화면에서 입력받은 brpbiz_idx를 가지고 값을 따로 세팅한다.
		prtbiz_idx 
		1: 사업주
		4: S-OJT*/		
		if(!useFlag.equals("new")) {			
			tpList = developService.getTpInfo(param);
			tpSubList = developService.getTpSubInfo(param);

			if(tpList == null) {
				// 해당글이 없는 경우
				model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
				return RbsProperties.getProperty("Globals.fail.path");
			}			
		}
				
		model.addAttribute("submitType", "develop_form_column");			
		model.addAttribute("bsisCnsl", bsisCnsl);			
		model.addAttribute("useFlag", useFlag);			
		model.addAttribute("corpInfo", corpInfo);			
		model.addAttribute("insttList", insttList);			
		model.addAttribute("tpList", tpList);			
		model.addAttribute("tpSubList", tpSubList);	
		model.addAttribute("optnHashMap", optnHashMap);	
		model.addAttribute("multiFileHashMap", developService.getMultiFileHashMap("HRD_DGNS_PRTBIZ_TP_FILE", "TP_IDX", tpIdx));	// multi file 목록
		model.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath(pageName);
	}
	
	/* 
	 * AI훈련추천 ajax 통신용
	 */ 
	@Auth(role = Role.C)
	@RequestMapping(method = RequestMethod.GET, value = "/develop_write_form.do", params="isAjax")
	public ModelAndView develop_write_form( @RequestParam("isAjax") String isAjax, @ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		long bsiscnslIdx = StringUtil.getInt(request.getParameter("bsiscnslIdx"));
		int rsltIdx = developService.getRsltIdxByBsiscnslIdx(bsiscnslIdx);
				
		List<Object> trainingRecommend = null;
						
		//기업HRD이음컨설팅 번호를 파라미터로 받은 후 api호출을 분기한다.
		if(rsltIdx == 0) {			
			trainingRecommend = (List<Object>) developService.getAiRecommendList("hrdk_edu_develop_page", BPL_NO, 0);
		}else {
			//기업HRD이음컨설팅에서 [추천 시 저장된 훈련] + [추가 된 훈련까지] 같이 가져온다
			//저장된 훈련이 없을 시(2차 서비스 오픈 전 데이터) 다시 ai추천을 받는다.
			String EduRecoSystemValue = developService.getEduRecoSystemValue(BPL_NO, rsltIdx);
			
			if(EduRecoSystemValue == null) {
				EduRecoSystemValue = developService.getAiEduRecoSystemRawValue(BPL_NO, rsltIdx);				
			}
			
			trainingRecommend = (List<Object>) developService.getBsisWithAiRecommendList(EduRecoSystemValue, bsiscnslIdx);
		}
		
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		mav.addObject("trainingRecommend", trainingRecommend);
		
		return mav;
	}

	/**
	 * 신청관리 목록에서 주치의 지원요청 내용 가져오기
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.GET, value = "/develop_help_form.do")
	public ModelAndView develop_help_form(@ModuleAttr ModuleAttrVO attrVO, @RequestBody String json, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		int devlopIdx = 0;
		DataMap dt = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		devlopIdx = StringUtil.getInt(request.getParameter("devlopIdx"));
		
		searchList.add(new DTForm("A.DEVLOP_IDX", devlopIdx));
		param.put("searchList", searchList);
		
		dt = developDctService.getHelpInfo("HRD_DEVLOP", param);
		
		String confmStatus = (String) dt.get("CONFM_STATUS");
		String status = "";
		if(confmStatus.equals("28")) {
			status = "주치의지원 접수";
		} else if(confmStatus.equals("42")) {
			status = "주치의지원 반려";
		} else {
			status = "주치의지원 요청";
		}
		
		String sidoName = (String) dt.get("SIDO_NAME");
		String ncsName = (String) dt.get("NCS_NAME");
		String insttName = (String) dt.get("INSTT_NAME");
		String cn = (String) dt.get("ETC_DEMAND_MATTER");
		String doctorOpinion = (String) dt.get("DOCTOR_OPINION");
		
		ModelAndView mav = new ModelAndView();
		mav.setView(jsonView);
		
		mav.addObject("status", status);
		mav.addObject("sidoName", sidoName);
		mav.addObject("ncsName", ncsName);
		mav.addObject("insttName", insttName);
		mav.addObject("cn", cn);
		mav.addObject("doctorOpinion", doctorOpinion);
		
		return mav;
	}
	
	/**
	 * [훈련과정표준개발] 표준개발 신청서 임시저장
	 * 	ㄴ 상태값 : 5
	 * 	ㄴ TEMPSAVE
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(method=RequestMethod.POST, value = "/develop_tempSave.do")
	public String develop_tempSave(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();		
		
		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "develop_form_column";		// 항목 order명
		//devlopIdx : 수정으로 들어왔을땐 존재함
		String devlopIdx = parameterMap.getString("devlopIdx");

		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray developInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "develop_insert_column_order");
//		JSONArray developUpdateColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "develop_update_columns");
		JSONArray developTpInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "develop_tp_insert_column_order");
//		JSONArray developTpUpdateColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "req_update_column_order");
		parameterMap.put("_itemInputTypeFlag", submitType);		
		

		// 2. DB
		
		int	developResult = 0;
		int developTpResult = 0;
		
		// 신청 상태로 setting
		String confmStatus = ConfirmProgress.TEMPSAVE.getCode();
		if(devlopIdx == null) {
			//devlopIdx가 null일때 : 최초신청 및 임시저장
			// textarea 개행처리 하기
			String trSfe = (String) parameterMap.get("trSfe");
			String trGoal = (String) parameterMap.get("trGoal");
			String trnReqm = (String) parameterMap.get("trnReqm");
			trSfe = trSfe.replaceAll("\r\n", "<br>");
			parameterMap.put("trSfe", trSfe);
			trGoal = trGoal.replaceAll("\r\n", "<br>");
			parameterMap.put("trGoal", trGoal);
			trnReqm = trnReqm.replaceAll("\r\n", "<br>");
			parameterMap.put("trnReqm", trnReqm);
			
			int tpSubLength = StringUtil.getInt((String) parameterMap.get("tpSubLength"));
			for(int i = 0; i < tpSubLength; i++) {
				String cn = (String) parameterMap.get("cn" + i);
				if(cn != null) {
					cn = cn.replaceAll("\r\n", "<br>");
					parameterMap.put("cn"+i, cn);
				}
			}
			
			//HRD_DEV insert
			developResult = developService.insertDevelopAsStatusCode(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, developInsertColumnOrder, confmStatus, "web");
			//HRD_DEV_TP + HRD_DEV_TP_SUB insert
			if(developResult != 0) {
				developTpResult = developService.insertDevelopTpAndTpSub(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, developTpInsertColumnOrder);
			}
		}else {						
			//devlopIdx가 null이 아닐때 : 수정을 통한 신청 및 임시저장
			System.out.println("parameterMap ::: " + parameterMap);
			// textarea 개행처리 하기
			String trSfe = (String) parameterMap.get("trSfe");
			String trGoal = (String) parameterMap.get("trGoal");
			String trnReqm = (String) parameterMap.get("trnReqm");
			trSfe = trSfe.replaceAll("\r\n", "</p>");
			trSfe = trSfe.replaceAll("\f", "<p>");
			parameterMap.put("trSfe", trSfe);
			trGoal = trGoal.replaceAll("\r\n", "</p>");
			trGoal = trGoal.replaceAll("\f", "<p>");
			parameterMap.put("trGoal", trGoal);
			trnReqm = trnReqm.replaceAll("\r\n", "</p>");
			trnReqm = trnReqm.replaceAll("\f", "<p>");
			parameterMap.put("trnReqm", trnReqm);
			
			int tpSubLength = StringUtil.getInt((String) parameterMap.get("tpSubLength"));
			for(int i = 0; i < tpSubLength; i++) {
				String cn = (String) parameterMap.get("cn" + i);
				if(cn != null) {
					cn = cn.replaceAll("\r\n", "<br>");
					parameterMap.put("cn"+i, cn);
				}
			}
			
			//HRD_DEV update
			developResult = developService.updateDevelopAsStatusCode(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, developInsertColumnOrder, confmStatus, "web");
			//HRD_DEV_TP + HRD_DEV_TP_SUB update
			developTpResult = developService.updateDevelopTpAndTpSub(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, developTpInsertColumnOrder);
		}
		
		
		if(developTpResult > 0) {
			// 2.1 저장 성공
			// 기본경로
			fn_setCommonPath(attrVO);
        	model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.tempsave"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));			
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
		} else if(developTpResult == -1) {
			// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) return fileFailView;
		}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
		
	/**
	 * [훈련과정표준개발] 표준개발 신청서 신청
	 * 	ㄴ 상태값 : 10
	 * 	ㄴ APPLY
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(method=RequestMethod.POST, value = "/develop_apply.do")
	public String develop_apply(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();
		String uploadModulePath = attrVO.getUploadModulePath();		
		int prtbizIdx = 0;
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = "";
		
		// 로그인한 회원의 정보 중 BPL_NO
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}		
		
		prtbizIdx = StringUtil.getInt(request.getParameter("prtbizIdx"));
		//devlopIdx : 수정으로 들어왔을땐 존재함
		String devlopIdx = parameterMap.getString("devlopIdx");		
		
		//S-OJT의 경우 일반으로 3회까지 신청가능.(맞춤개발에서의 일반 포함)
		//ex) 표준개발에서 일반 S-OJT 2회 신청 + 맞춤개발에서 일반 S-OJT 1회 신청 시 한도 끝
		if(prtbizIdx == 1) {
			String sojtAvailableFlag = developService.getSojtAvailableFlag(BPL_NO);
			if(sojtAvailableFlag.equals("N")) {
				model.addAttribute("message",MessageUtil.getAlert("해당연도의 [S-OJT 일반] 과정개발 신청한도를 초과하였습니다(연간 3회)."));
				return RbsProperties.getProperty("Globals.fail.path");
			}
		}

		// 1. 필수입력 체크
		// 1.1 항목설정
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		
		JSONArray developInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "develop_insert_column_order");				
		parameterMap.put("_itemInputTypeFlag", "develop_insert_column_order");
		// 1.2 필수입력 체크(develop_insert_column_order)
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, developInsertColumnOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		
		JSONArray developTpInsertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "develop_tp_insert_column_order");		
		parameterMap.put("_itemInputTypeFlag", "develop_tp_insert_column_order");
		// 1.2 필수입력 체크(develop_tp_insert_column_order)
		errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, developTpInsertColumnOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 2. DB

		int	developResult = 0;
		int developTpResult = 0;
		
		// 신청 상태로 setting
		String confmStatus = ConfirmProgress.APPLY.getCode();
		
		
		if(devlopIdx == null) {
			//devlopIdx가 null일때 : 최초신청 및 임시저장
			// textarea 개행처리 하기
			String trSfe = (String) parameterMap.get("trSfe");
			String trGoal = (String) parameterMap.get("trGoal");
			String trnReqm = (String) parameterMap.get("trnReqm");
			trSfe = trSfe.replaceAll("\r\n", "<br>");
			parameterMap.put("trSfe", trSfe);
			trGoal = trGoal.replaceAll("\r\n", "</br>");
			parameterMap.put("trGoal", trGoal);
			trnReqm = trnReqm.replaceAll("\r\n", "</br>");
			parameterMap.put("trnReqm", trnReqm);
			
			int tpSubLength = StringUtil.getInt((String) parameterMap.get("tpSubLength"));
			for(int i = 0; i < tpSubLength; i++) {
				String cn = (String) parameterMap.get("cn" + i);
				if(cn != null) {
					cn = cn.replaceAll("\r\n", "<br>");
					parameterMap.put("cn"+i, cn);
				}
			}
			
			//HRD_DEV insert
			developResult = developService.insertDevelopAsStatusCode(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, developInsertColumnOrder, confmStatus, "web");
			//HRD_DEV_TP + HRD_DEV_TP_SUB insert
			if(developResult != 0) {
				developTpResult = developService.insertDevelopTpAndTpSub(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, developTpInsertColumnOrder);
			}
		}else {
			//devlopIdx가 null이 아닐때 : 수정을 통한 신청 및 임시저장						
			
			// textarea 개행처리 하기
			String trSfe = (String) parameterMap.get("trSfe");
			String trGoal = (String) parameterMap.get("trGoal");
			String trnReqm = (String) parameterMap.get("trnReqm");
			trSfe = trSfe.replaceAll("\r\n", "</p>");
			trSfe = trSfe.replaceAll("\f", "<p>");
			parameterMap.put("trSfe", trSfe);
			trGoal = trGoal.replaceAll("\r\n", "</p>");
			trGoal = trGoal.replaceAll("\f", "<p>");
			parameterMap.put("trGoal", trGoal);
			trnReqm = trnReqm.replaceAll("\r\n", "</p>");
			trnReqm = trnReqm.replaceAll("\f", "<p>");
			parameterMap.put("trnReqm", trnReqm);
			
			int tpSubLength = StringUtil.getInt((String) parameterMap.get("tpSubLength"));
			for(int i = 0; i < tpSubLength; i++) {
				String cn = (String) parameterMap.get("cn" + i);
				if(cn != null) {
					cn = cn.replaceAll("\r\n", "<br>");
					parameterMap.put("cn"+i, cn);
				}
			}
			
			//HRD_DEV update
			developResult = developService.updateDevelopAsStatusCode(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, developInsertColumnOrder, confmStatus, "web");
			//HRD_DEV_TP + HRD_DEV_TP_SUB update
			developTpResult = developService.updateDevelopTpAndTpSub(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, developTpInsertColumnOrder);
		}
			    	    
    	
    	if(developTpResult > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.apply2"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} else if(developTpResult == -1) {
    		// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) return fileFailView;
    	}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}

	/**
	 * [훈련과정표준개발] 표준개발 신청서 리포트 출력하기
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/develop_report_form.do")
	public String clipReport(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String devlopIdx = request.getParameter("devlopIdx");
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인한 회원의 정보 중 BPL_NO
		String BPL_NO = loginVO.getBplNo();
		
    	// 5. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	// 기초진단지 번호 setting
    	request.setAttribute("DEVLOP_IDX", devlopIdx);
    	request.setAttribute("BPL_NO", BPL_NO);
    	
    	return getViewPath("/clipReport");
	}
	
	/**
	 * [훈련과정표준개발] 종합이력 화면
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
		List<DTForm> searchList = new ArrayList<DTForm>();
		DataForm queryString = attrVO.getQueryString();
		
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
		
		// 2.1 검색조건
		// 항목설정으로 검색조건 setting
		String listSearchId = "list_search";		// 검색설정
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		List<DTForm> itemInfoSearchList = ModuleUtil.getItemInfoSearchList(listSearch, queryString);
		if(itemInfoSearchList != null) {
			searchList.addAll(itemInfoSearchList);
			model.addAttribute("isSearchList", new Boolean(true));
		}
		
		// 탭코드
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		String cateTabItemId = JSONObjectUtil.getString(settingInfo, "dset_cate_tab_id");
		String cateTabColumnId = JSONObjectUtil.getString(JSONObjectUtil.getJSONObject(items, cateTabItemId), "column_id");
		String cateTabMasterCode = JSONObjectUtil.getString(JSONObjectUtil.getJSONObject(items, cateTabItemId), "master_code");
		int cateTabObjectType = JSONObjectUtil.getInt(settingInfo, "dset_cate_tab_object_type");
		// 검색 코드
		HashMap<String, Object> searchOptnHashMap = CodeHelper.getItemOptnSearchHashMap(listSearch, cateTabItemId, cateTabColumnId, cateTabObjectType, cateTabMasterCode);
		
		// 2. DB
		List<Object> totalList = null;
		DataMap corpInfo = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		searchList.add(new DTForm("A." + "BPL_NO", BPL_NO));
		param.put("searchList", searchList);


		int totalCount = 0;
    	totalCount = developService.getTotalListCount(param);
		paginationInfo.setTotalRecordCount(totalCount);
    	if(totalCount > 0) {
    		if(usePaging == 1) {
	    		param.put("firstIndex", paginationInfo.getFirstRecordIndex());
	    		param.put("lastIndex", paginationInfo.getLastRecordIndex());
	    		param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
	    	}
    	}
				
		// 종합이력 목록 
		totalList = developService.getTotalList(param);
		

    	// 3. 속성 setting
		model.addAttribute("searchOptnHashMap", searchOptnHashMap);	
    	model.addAttribute("paginationInfo", paginationInfo);											// 페이징정보
    	model.addAttribute("corpInfo", corpInfo);														// 기업정보
    	model.addAttribute("totalList", totalList);														// 종합이력
    	model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
    	model.addAttribute("optnHashMap", getOptionHashMap("list", itemInfo));    	

 
    	
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
		
	/**
	 * [훈련과정표준개발] 신청서 상세보기 화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/develop_view_form.do")
	public String develop_view_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();		
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 선택한 과정의 idx
		int devlopIdx = 0;	
		
		//신청정보 ,신청과정 정보 , 신청과정 상세정보 리스트 
		DataMap devlopList = null;
		DataMap tpList = null;
		List<Object> tpSubList = null;
				
		// 기업정보 
		DataMap corpInfo = null;		
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = "";
		
		// 로그인한 회원의 정보 중 BPL_NO
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}		
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		devlopIdx = StringUtil.getInt(request.getParameter("devlopIdx"));				
		

		if(devlopIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		//url 변조 접근 방지
		int isRequestBplNo = developService.getCompareRequestAndVoByBplNo(devlopIdx, BPL_NO);

		
		if(isRequestBplNo == 0) {
			String message = "정상적인 접근이 아닙니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "develop_form_column");										

		
		param.put("DEVLOP_IDX", devlopIdx);
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(99 , param);	
		
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "DEVLOP_IDX", devlopIdx));
		param.put("searchList", searchList);
		param.put("orderColumn", "TP_COURSE_IDX");
		
		devlopList = developService.getView("HRD_DEVLOP", param);
		tpList = developService.getView("HRD_DEVLOP_TP", param);				
		tpSubList = developService.getList("HRD_DEVLOP_TP_SUB", param);

		if(tpList == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}			
		
				
		model.addAttribute("submitType", "develop_form_column");		
		model.addAttribute("corpInfo", corpInfo);			
		model.addAttribute("devlopList", devlopList);			
		model.addAttribute("tpList", tpList);			
		model.addAttribute("tpSubList", tpSubList);	
		model.addAttribute("optnHashMap", optnHashMap);	
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
	
	/**
	 * [훈련과정표준개발] 신청서 수정 화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/develop_modify_form.do")
	public String develop_modify_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();		
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 선택한 과정의 idx
		int devlopIdx = 0;	
		
		//신청정보 ,신청과정 정보 , 신청과정 상세정보 리스트 
		DataMap devlopList = null;
		DataMap tpList = null;
		List<Object> tpSubList = null;
		
		// 기업정보 
		DataMap corpInfo = null;	
		
		// 지부지사 idx
		List<Object> insttList = null;
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = "";
		
		// 로그인한 회원의 정보 중 BPL_NO
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}		
		
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		devlopIdx = StringUtil.getInt(request.getParameter("devlopIdx"));						
		
		//url 변조 접근 방지
		int isRequestBplNo = developService.getCompareRequestAndVoByBplNo(devlopIdx, BPL_NO);

		
		if(isRequestBplNo == 0) {
			String message = "정상적인 접근이 아닙니다.";
			model.addAttribute("message", MessageUtil.getAlert(message));
			return RbsProperties.getProperty("Globals.fail" + attrVO.getAjaxPName() + ".path");
		}
		
		
		if(devlopIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "develop_modify_form_column");										
		
		
		param.put("DEVLOP_IDX", devlopIdx);
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(99 , param);	
		
		insttList = clinicService.getInsttList(param);
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "DEVLOP_IDX", devlopIdx));
		param.put("searchList", searchList);
		param.put("orderColumn", "TP_COURSE_IDX");
		
		devlopList = developService.getView("HRD_DEVLOP", param);
		tpList = developService.getView("HRD_DEVLOP_TP", param);				
		tpSubList = developService.getList("HRD_DEVLOP_TP_SUB", param);
		
		if(tpList == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}			
		
		// 과정활용, 과정수정, 신규작성 구분하기
		String tpDevlopSe = (String) devlopList.get("TP_DEVLOP_SE");
		String pageName = "";
		String useFlag = "";
		
		if(tpDevlopSe.equals("use")) {
			pageName = "/useModifyForm";	
			useFlag = "use";
		} else {
			pageName = "/modifyForm";
			useFlag = "modify";
		}
		
		model.addAttribute("submitType", "develop_modify_form_column");		
		model.addAttribute("useFlag", useFlag);			
		model.addAttribute("corpInfo", corpInfo);			
		model.addAttribute("devlopList", devlopList);			
		model.addAttribute("insttList", insttList);			
		model.addAttribute("tpList", tpList);			
		model.addAttribute("tpSubList", tpSubList);	
		model.addAttribute("optnHashMap", optnHashMap);	
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
		// 4. 기본경로
		fn_setCommonPath(attrVO);
		
		return getViewPath(pageName);
	}
	
	/**
	 * [훈련과정표준개발] 신청관리 목록 화면
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/develop_list_form.do")
	public String develop_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String BPL_NO = "";
		if(loginVO != null){
			BPL_NO = StringUtil.getString(loginVO.getBplNo());
		}
		
		
		//로그인한 기업이 기업HRD이음컨설팅을 실시했는지 확인 
		int isBsc = developService.getIsBsc(BPL_NO);
		if(isBsc == 0) {
			String message = "기업HRD이음컨설팅을 실시하지 않았습니다. 기업HRD이음컨설팅을 먼저 진행해주세요";
				String toUrl = request.getContextPath() + "/web/bsisCnsl/init.do?mId=55";
				model.addAttribute("message", MessageUtil.getAlertAddWindow(attrVO.isAjax(), message, "fn_procAction(\"" + toUrl + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
				return RbsProperties.getProperty("Globals.message" + attrVO.getAjaxPName() + ".path");		
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
		//로그인한 계정의 신청서 리스트
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
		searchList.add(new DTForm("BPL_NO", BPL_NO));
		param.put("searchList", searchList);		
		
		
		// 목록 수 및 목록 가져오기 
		// 2.2 목록수
    	totalCount = developService.getDevTotalCount(param);
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
    		list = developService.getDevList(param);
		}
		
    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
	
	/**
	 * [훈련과정표준개발] 훈련과정 전체 목록 화면
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/develop_training_list_form.do")
	public String develop_training_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		
		//AI훈련추천 전체보기 POST용 대안 코드
//		String trainingCodeList = request.getParameter("trainingCodeList");
//		trainingCodeList = trainingCodeList.substring(1, trainingCodeList.length() - 1);
//		String[] parsedTrainingCodeList = trainingCodeList.split(","); 
//		ArrayList<Integer> numberedTrainingCodeList = new ArrayList<>();
//		for(String number : parsedTrainingCodeList) {
//			if(!number.equals("null")) {				
//				numberedTrainingCodeList.add(Integer.parseInt(number.trim()));
//			}
//		}
		
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
		//로그인한 계정의 신청서 리스트
		List<?> list = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 2.1 검색조건
		
		// 항목설정
		String submitType = "list";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		
		// 항목설정으로 검색조건 setting
		String listSearchId = "training_list_search";		// 검색설정
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
		//AI훈련추천 전체보기 POST용 대안 코드
//		param.put("trainingCodeList", numberedTrainingCodeList);
		
		// ai추천 훈련과정 tpIdx 리스트 가져오기
		String tpList = "";
		tpList = request.getParameter("tpList");
		String isFromDevelop = request.getParameter("isFromDevelop");
		
		param.put("tpList", tpList);
		param.put("isFromDevelop", isFromDevelop);
		
		// 목록 수 및 목록 가져오기 
		// 2.2 목록수
		totalCount = developService.getTrainingListCount(param);
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
			list = developService.getTrainingList(param);
		}
		
		// 3. 속성 setting
		model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
		model.addAttribute("list", list);															// 목록
		model.addAttribute("tpList", tpList);
		model.addAttribute("isFromDevelop", isFromDevelop);
		model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
		// 4. 기본경로
		fn_setCommonPath(attrVO);
		
		return getViewPath("/trainingList");
	}
	
	/**
	 * 신청관리 상세보기 화면에서 회수 처리
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 20
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/develop_withdraw.do")
	public String develop_withdraw(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("devlopIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 반려 상태로 setting
		String confmStatus = ConfirmProgress.WITHDRAW.getCode();
		
		int updateDev = 0;
		
		// 반려 상태로 신청상태 및 주치의 의견 update하기
		updateDev = developDctService.updateStatus("HRD_DEVLOP", "DEVLOP_IDX", confmStatus, keyIdx, regiIp);
		
    	if(updateDev > 0) {
    		// HRD_DEVLOP_CONFM 테이블에 반려 상태 insert하기
    		int updateDevConfm = developDctService.insertConfm("HRD_DEVLOP_CONFM", "CONFM_IDX", confmStatus, keyIdx, regiIp);
    		if(updateDevConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.withdraw"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 신청관리 상세보기 화면에서 반려요청 처리
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 35
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/develop_returnRequest.do")
	public String develop_returnRequest(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("devlopIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 반려 상태로 setting
		String confmStatus = ConfirmProgress.RETURNREQUEST.getCode();
		
		int updateDev = 0;
		
		// 반려 상태로 신청상태 및 주치의 의견 update하기
		updateDev = developDctService.updateStatus("HRD_DEVLOP", "DEVLOP_IDX", confmStatus, keyIdx, regiIp);
		
    	if(updateDev > 0) {
    		// HRD_DEVLOP_CONFM 테이블에 반려 상태 insert하기
    		int updateDevConfm = developDctService.insertConfm("HRD_DEVLOP_CONFM", "CONFM_IDX", confmStatus, keyIdx, regiIp);
    		if(updateDevConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.returnrequest"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 신청관리 상세보기 화면에서 검토요청 승인 처리
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 55
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/develop_consider_approve.do")
	public String develop_consider_approve(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("devlopIdx"));
		
		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
			
		
		// 반려 상태로 setting
		String confmStatus = ConfirmProgress.APPROVE.getCode();
		
		int updateDev = 0;
		
		// 반려 상태로 신청상태 및 주치의 의견 update하기
		updateDev = developDctService.updateStatus("HRD_DEVLOP", "DEVLOP_IDX", confmStatus, keyIdx, regiIp);
		
		if(updateDev > 0) {
			// HRD_DEVLOP_CONFM 테이블에 반려 상태 insert하기
			int updateDevConfm = developDctService.insertConfm("HRD_DEVLOP_CONFM", "CONFM_IDX", confmStatus, keyIdx, regiIp);
			if(updateDevConfm > 0) {
				// 저장 성공
				// 기본경로
				fn_setCommonPath(attrVO);
				
				model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, "정상적으로 검토요청 승인 되었습니다.", "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
				return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
			}
		} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 신청관리 상세보기 화면에서 중도포기요청 처리
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 75
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.GET, value = "/develop_dropRequest.do")
	public String develop_dropRequest(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("devlopIdx"));
		
		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}		
		
		// 반려 상태로 setting
		String confmStatus = ConfirmProgress.DROPREQUEST.getCode();
		
		int updateDev = 0;
		
		// 반려 상태로 신청상태 및 주치의 의견 update하기
		updateDev = developDctService.updateStatus("HRD_DEVLOP", "DEVLOP_IDX", confmStatus, keyIdx, regiIp);
		
		if(updateDev > 0) {
			// HRD_DEVLOP_CONFM 테이블에 반려 상태 insert하기
			int updateDevConfm = developDctService.insertConfm("HRD_DEVLOP_CONFM", "CONFM_IDX", confmStatus, keyIdx, regiIp);
			if(updateDevConfm > 0) {
				// 저장 성공
				// 기본경로
				fn_setCommonPath(attrVO);
				
				model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.droprequest"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
				return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
			}
		} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * 신청관리 상세보기 화면에서 검토요청 반려처리
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 37
	 * @param mode
	 * @param parameterMap
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(method=RequestMethod.POST, value = "/develop_consider_return.do")
	public String develop_consider_return(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String regiIp = request.getRemoteAddr();
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int keyIdx = StringUtil.getInt(request.getParameter("devlopIdx"));

		// 필수 key가 없을 때
		if(keyIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// textarea 개행처리 하기
		String contents = (String) parameterMap.get("corpOpinion");
		if(contents != null){
			contents = contents.replaceAll("\r\n", "<br>");
			parameterMap.put("corpOpinion", contents);
		}
		
		// 반려 상태로 setting
		String confmStatus = ConfirmProgress.CONSIDERRETURN.getCode();
		
		int updateDev = 0;
		
		// 반려 상태로 신청상태 및 주치의 의견 update하기
		updateDev = developService.updateStatusAndOpinion("HRD_DEVLOP", "DEVLOP_IDX", confmStatus, keyIdx, regiIp, parameterMap);
		
    	if(updateDev > 0) {
    		// HRD_DEVLOP_CONFM 테이블에 상태 insert하기
    		int updateDevConfm = developDctService.insertConfm("HRD_DEVLOP_CONFM", "CONFM_IDX", confmStatus, keyIdx, regiIp);
    		if(updateDevConfm > 0) {
    			// 저장 성공
            	// 기본경로
            	fn_setCommonPath(attrVO);
            	
    			model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, "정상적으로 검토요청 반려처리 되었습니다.", "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("DEVELOP_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
    			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    		}
    	} 
		// 4.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * [훈련과정표준개발] 맞춤개발 비용청구 목록 화면
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/support_list_form.do")
	public String support_list_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		DataForm queryString = attrVO.getQueryString();
		boolean isAdmMode = attrVO.isAdmMode();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		String BPL_NO = "";
		if(loginVO != null){
			BPL_NO = StringUtil.getString(loginVO.getBplNo());
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
		//로그인한 계정의 신청서 리스트
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
		
		// 사업연도 검색
		String yearItemName = "is_startYear";
		String startYear = queryString.getString(yearItemName);
		if(!StringUtil.isEmpty(startYear)) {
			param.put("YEAR", startYear);
			model.addAttribute("isSearchList", new Boolean(true));
			model.addAttribute("startYear", startYear);
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
		param.put("BPL_NO", BPL_NO);		
		
		
		// 목록 수 및 목록 가져오기 
		// 2.2 목록수
    	totalCount = developService.getSupportListCount(param);
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
    		list = developService.getSupportList(param);
		}
		
    	// 3. 속성 setting
    	model.addAttribute("paginationInfo", paginationInfo);										// 페이징정보
    	model.addAttribute("list", list);															// 목록
    	model.addAttribute("searchOptnHashMap", searchOptnHashMap);									// 항목코드
		model.addAttribute("optnHashMap", optnHashMap);
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		
    	// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
		return getViewPath("/list");
	}
	
	/**
	 * [훈련과정표준개발] 맞춤개발 비용청구 신청화면
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.C)
	@RequestMapping(value = "/support_write_form.do")
	public String support_write_form(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {		
		Map<String, Object> param = new HashMap<String, Object>();
		
		// 선택한 신청서의 컨설팅 idx
		int cnslIdx = 0;
//		int ctIdx = 0;
		
		
		// 컨설팅 리포트 리스트 / 팀 리스트
		DataMap cnslReportInfo = null;
		List<Object> cnslTeamList = null;
		List<Object> corpTpList = null;
				
		// 기업정보 
		DataMap corpInfo = null;
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = "";
		
		// 로그인한 회원의 정보 중 BPL_NO
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}		
		
		cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));
		
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "write");										

		
		param.put("BPL_NO", BPL_NO);
		param.put("CNSL_IDX", cnslIdx);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(99 , param);	
		
		cnslReportInfo = developService.getCnslReportInfo(param);
		cnslTeamList = developService.getCnslTeamInfo(param);
		corpTpList = developService.getCorpTpList(param);
	
		
				
		model.addAttribute("submitType", "write");			
		model.addAttribute("corpInfo", corpInfo);			
		model.addAttribute("cnslReportInfo", cnslReportInfo);			
		model.addAttribute("cnslTeamList", cnslTeamList);	
		model.addAttribute("optnHashMap", optnHashMap);	
		model.addAttribute("corpTpList", corpTpList);	
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/input");
	}
	
	/**
	 * [훈련과정표준개발] 맞춤개발 비용청구 신청서 신청
	 * 	ㄴ 상태값 : 10
	 * 	ㄴ APPLY
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
		int cnslIdx = 0;
		int ctIdx = 0;
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = "";
		
		// 로그인한 회원의 정보 중 BPL_NO
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}		
		
		cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));
		ctIdx = StringUtil.getInt(request.getParameter("ctIdx"));
		
		// 1. 필수입력 체크
		// 1.1 항목설정
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		
		JSONArray insertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "writeproc_order");				
		parameterMap.put("_itemInputTypeFlag", "writeproc_order");
		// 1.2 필수입력 체크(develop_insert_column_order)
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, insertColumnOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		// 2. DB

		int	cnslResult = 0;
		
		// 신청 상태로 setting
		String confmStatus = ConfirmProgress.APPLY.getCode();
		
		// textarea 개행처리
		String doctorOpinion = (String) parameterMap.get("doctorOpinion");
		if(doctorOpinion != null) {
			doctorOpinion = doctorOpinion.replaceAll("\r\n", "<br>");
			parameterMap.put("doctorOpinion", doctorOpinion);
		}			
		String remark = (String) parameterMap.get("remark");
		if(remark != null) {
			remark = remark.replaceAll("\r\n", "<br>");
			parameterMap.put("remark", remark);
		}
		
		if(ctIdx == 0) {
			//ctIdx가 null일때 : 최초신청 및 임시저장
			//HRD_COST insert								
			cnslResult = developService.insertCnslCostAsStatusCode(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, insertColumnOrder, confmStatus);
		}else {
			//ctIdx가 null이 아닐때 : 수정을 통한 신청 및 임시저장												
			//HRD_COST update
			cnslResult = developService.updateCnslCostAsStatusCode(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, insertColumnOrder, confmStatus);
		}
			    	    
    	
    	if(cnslResult > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.apply2"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} else if(cnslResult == -1) {
    		// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) return fileFailView;
    	}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * [훈련과정표준개발] 맞춤개발 비용청구 신청서 임시저장
	 * 	ㄴ 상태값 : 5
	 * 	ㄴ TEMPSAVE
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
		int cnslIdx = 0;
		int ctIdx = 0;
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = "";
		
		// 로그인한 회원의 정보 중 BPL_NO
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}		
		
		cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));
		ctIdx = StringUtil.getInt(request.getParameter("ctIdx"));
		
		// 1. 필수입력 체크
		// 1.1 항목설정
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		
		JSONArray insertColumnOrder = JSONObjectUtil.getJSONArray(itemInfo, "writeproc_order");				
		parameterMap.put("_itemInputTypeFlag", "writeproc_order");
		// 1.2 필수입력 체크(develop_insert_column_order)
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, moduleValidator, new Object[]{isAdmMode, inputFlag, items, insertColumnOrder, settingInfo});
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		// 2. DB

		int	cnslResult = 0;
		
		// 신청 상태로 setting
		String confmStatus = ConfirmProgress.TEMPSAVE.getCode();
		
		// textarea 개행처리
		String doctorOpinion = (String) parameterMap.get("doctorOpinion");
		if(doctorOpinion != null) {
			doctorOpinion = doctorOpinion.replaceAll("\r\n", "<br>");
			parameterMap.put("doctorOpinion", doctorOpinion);
		}			
		String remark = (String) parameterMap.get("remark");
		if(remark != null) {
			remark = remark.replaceAll("\r\n", "<br>");
			parameterMap.put("remark", remark);
		}
		
		if(ctIdx == 0) {
			//ctIdx가 null일때 : 최초신청 및 임시저장
			//HRD_COST insert								
			cnslResult = developService.insertCnslCostAsStatusCode(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, insertColumnOrder, confmStatus);
		}else {
			//ctIdx가 null이 아닐때 : 수정을 통한 신청 및 임시저장												
			//HRD_COST update
			cnslResult = developService.updateCnslCostAsStatusCode(uploadModulePath, request.getRemoteAddr(), parameterMap, settingInfo, items, insertColumnOrder, confmStatus);
		}
			    	    
    	
    	if(cnslResult > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO);
        	model.addAttribute("message", MessageUtil.getAlertAddWindow(isAjax, rbsMessageSource.getMessage("message.tempsave"), "fn_procAction(\"" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("SUPPORT_LIST_FORM_URL"))) + "\", " + StringUtil.getInt(request.getParameter("mdl")) + ");"));
			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
    	} else if(cnslResult == -1) {
    		// 2.2 파일업로드 오류
			String fileFailView = getFileFailViewPath(request);
			if(!StringUtil.isEmpty(fileFailView)) return fileFailView;
    	}
		// 2.3 저장 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.insert")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	/**
	 * [훈련과정표준개발] 맞춤개발 비용청구 상세보기 화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/support_view_form.do")
	public String support_view_form(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();		
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 선택한  cnslIdx
		int cnslIdx = 0;
		// ctIdx
		int ctIdx = 0;
		
		DataMap cnslReportInfo = null;
		
		//신청정보 ,신청과정 정보 , 신청과정 상세정보 리스트 
		DataMap cnslCostInfo = null;
		List<Object> cnslCostSubList = null;
				
		// 기업정보 
		DataMap corpInfo = null;		
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = "";
		
		// 로그인한 회원의 정보 중 BPL_NO
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}		
		
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));				
		ctIdx = StringUtil.getInt(request.getParameter("ctIdx"));				
		

		if(cnslIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "view");										

		
		param.put("CNSL_IDX", cnslIdx);
		param.put("BPL_NO", BPL_NO);
		param.put("CT_IDX", ctIdx);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(99 , param);	
		
		cnslReportInfo = developService.getCnslReportInfo(param);
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CNSL_IDX", cnslIdx));
		searchList.add(new DTForm("A." + "CT_IDX", ctIdx));
		param.put("searchList", searchList);
		param.put("orderColumn", "CNSL_IDX");
		
		cnslCostInfo = developService.getView("HRD_CNSL_COST", param);			
		cnslCostSubList = developService.getList("HRD_CNSL_COST_SUB", param);

		if(cnslCostSubList == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}			
		
				
		model.addAttribute("submitType", "view");		
		model.addAttribute("corpInfo", corpInfo);			
		model.addAttribute("dt", cnslCostInfo);			
		model.addAttribute("cnslReportInfo", cnslReportInfo);			
		model.addAttribute("cnslCostSubList", cnslCostSubList);	
		model.addAttribute("optnHashMap", optnHashMap);	
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		model.addAttribute("multiFileHashMap", developService.getMultiFileHashMap("HRD_CNSL_COST_FILE", "CNSL_IDX", cnslIdx));	// multi file 목록

		// 4. 기본경로
    	fn_setCommonPath(attrVO);
    	
    	return getViewPath("/view");
	}
		
	/**
	 * [훈련과정표준개발] 맞춤개발 비용청구 수정 화면
	 * @param mode
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.U)
	@RequestMapping(value = "/support_modify_form.do")
	public String support_modify_form(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		// 선택한 신청서의 컨설팅 idx
		int cnslIdx = 0;
		int ctIdx = 0;
		
		
		// 컨설팅 리포트 리스트 / 팀 리스트
		DataMap cnslReportInfo = null;
		DataMap cnslCostInfo = null;
		List<Object> cnslTeamList = null;
		List<Object> corpTpList = null;
				
		// 기업정보 
		DataMap corpInfo = null;	
		
		// 지부지사 idx
		List<Object> insttList = null;
		
		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		String BPL_NO = "";
		
		// 로그인한 회원의 정보 중 BPL_NO
		if(loginVO != null) {			
			BPL_NO = loginVO.getBplNo();
		}		
		
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		ctIdx = StringUtil.getInt(request.getParameter("ctIdx"));				
		cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));				
		
		
		if(cnslIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400.path");
		}
		
		HashMap<String, Object> optnHashMap = CommonFunction.fn_getOptnHashMap(attrVO, "modify");										
		
		param.put("CNSL_IDX", cnslIdx);
		param.put("BPL_NO", BPL_NO);
		
		// 기업 정보
		corpInfo = clinicService.getCorpInfo(99 , param);	
		
		//리스트 테이블의 select 조건을 설정한다.
		searchList.add(new DTForm("A." + "CNSL_IDX", cnslIdx));
		param.put("searchList", searchList);
		
		cnslReportInfo = developService.getCnslReportInfo(param);
		
		searchList.add(new DTForm("A." + "CT_IDX", ctIdx));		
		param.put("searchList", searchList);
		
		cnslCostInfo = developService.getView("HRD_CNSL_COST",param);
		
		param.put("orderColumn", "TEAM_IDX");
		cnslTeamList = developService.getList("HRD_CNSL_COST_SUB",param);
		corpTpList = developService.getCorpTpList(param);
		
		if(cnslCostInfo == null) {
			// 해당글이 없는 경우
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.no.contents")));
			return RbsProperties.getProperty("Globals.fail.path");
		}			
		
		
		model.addAttribute("submitType", "modify");		
		model.addAttribute("corpInfo", corpInfo);			
		model.addAttribute("dt", cnslCostInfo);			
		model.addAttribute("insttList", insttList);			
		model.addAttribute("cnslReportInfo", cnslReportInfo);			
		model.addAttribute("cnslTeamList", cnslTeamList);	
		model.addAttribute("corpTpList", corpTpList);	
		model.addAttribute("optnHashMap", optnHashMap);	
		model.addAttribute("confirmProgress", ConfirmProgress.GetLabels());
		model.addAttribute("multiFileHashMap", developService.getMultiFileHashMap("HRD_CNSL_COST_FILE", "CT_IDX", ctIdx));	// multi file 목록
		
		// 4. 기본경로
		fn_setCommonPath(attrVO);
		
		return getViewPath("/input");
	}
	
	/**
	 * 신청관리 상세보기 화면에서 회수 처리
	 *   ㄴ fnIdx : 1
	 *   ㄴ CONFM_STATUS : 20
	 * @param mode
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
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int ctIdx = StringUtil.getInt(request.getParameter("ctIdx"));
		int cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));

		// 필수 key가 없을 때
		if(cnslIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 반려 상태로 setting
		String confmStatus = ConfirmProgress.WITHDRAW.getCode();
		
		int updateDev = 0;
		
		// 반려 상태로 신청상태 및 주치의 의견 update하기
		updateDev = developDctService.updateStatus("HRD_CNSL_COST", "CNSL_IDX", confmStatus, cnslIdx, regiIp);
		
    	if(updateDev > 0) {
    		// HRD_DEVLOP_CONFM 테이블에 반려 상태 insert하기
    		int updateDevConfm = developService.insertConfm("HRD_CNSL_COST_CONFM", "CONFM_IDX", confmStatus, cnslIdx, ctIdx, regiIp);
    		if(updateDevConfm > 0) {
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
	 * 맞춤개발 비용청구 상세보기 화면에서 반려요청 처리
	 *   ㄴ CONFM_STATUS : 35
	 * @param mode
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
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));
		int ctIdx = StringUtil.getInt(request.getParameter("cnslIdx"));

		// 필수 key가 없을 때
		if(cnslIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}
		
		// 반려 상태로 setting
		String confmStatus = ConfirmProgress.RETURNREQUEST.getCode();
		
		int updateDev = 0;
		
		// 반려 상태로 신청상태 및 주치의 의견 update하기
		updateDev = developDctService.updateStatus("HRD_CNSL_COST", "CNSL_IDX", confmStatus, cnslIdx, regiIp);
		
    	if(updateDev > 0) {
    		// HRD_DEVLOP_CONFM 테이블에 반려 상태 insert하기
    		int updateDevConfm = developService.insertConfm("HRD_CNSL_COST_CONFM", "CONFM_IDX", confmStatus, cnslIdx, ctIdx, regiIp);
    		if(updateDevConfm > 0) {
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
	 * 맞춤개발 비용청구 상세보기 화면에서 중도포기요청 처리
	 *   ㄴ CONFM_STATUS : 75
	 * @param mode
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
		
		// 1. 필수 parameter 검사
		// 1.1 필수 key parameter 검사
		int cnslIdx = StringUtil.getInt(request.getParameter("cnslIdx"));
		int ctIdx = StringUtil.getInt(request.getParameter("ctIdx"));
		
		// 필수 key가 없을 때
		if(cnslIdx <= 0) {
			return RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path");
		}		
		
		// 반려 상태로 setting
		String confmStatus = ConfirmProgress.DROPREQUEST.getCode();
		
		int updateDev = 0;
		
		// 반려 상태로 신청상태 및 주치의 의견 update하기
		updateDev = developDctService.updateStatus("HRD_CNSL_COST", "CNSL_IDX", confmStatus, cnslIdx, regiIp);
		
		if(updateDev > 0) {
			// HRD_DEVLOP_CONFM 테이블에 반려 상태 insert하기
			int updateDevConfm = developService.insertConfm("HRD_CNSL_COST_CONFM", "CONFM_IDX", confmStatus, cnslIdx, ctIdx, regiIp);
			if(updateDevConfm > 0) {
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
		 *  		ㄴ (ex: 신청_임시저장   -> develop_tempSave.do)
		 *  		ㄴ (ex: 신청_신청서상세보기 -> develop_view_form.do)
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
		String developSelectFormUrl = "develop_select_form.do";						// 훈련과정개발 표준 특화 선택화면
		String developInfoFormUrl = "develop_info_form.do";							// 훈련과정개발 표준 안내화면				
		String developRecommendFormUrl = "develop_recommend_form.do";				// 훈련과정개발 훈련추천 화면
		String developTrainingViewFormUrl = "develop_training_view_form.do";		// 훈련과정개발 과정상세 화면
		String developTrainingListFormUrl = "develop_training_list_form.do";		// 훈련과정개발 과정상세 화면
		String developHelpFormUrl = "develop_help_form.do";		// 훈련과정개발 과정상세 화면
		String developTempSaveUrl = "develop_tempSave.do";							// 훈련과정개발 신청서 임시저장		
		String developApplyUrl = "develop_apply.do";								// 훈련과정개발 신청서 신청	
		String developWithDrawUrl = "develop_withdraw.do";							// 훈련과정개발 신청서 회수		
		String developReturnReqeustUrl = "develop_returnRequest.do";				// 훈련과정개발 신청서 반려요청
		String developConsiderReturnUrl = "develop_consider_return.do";				// 훈련과정개발 검토요청 반려
		String developConsiderApproveUrl = "develop_consider_approve.do";			// 훈련과정개발 검토요청 승인
		String developDropReqeustUrl = "develop_dropRequest.do";					// 훈련과정개발 신청서 중도포기요청
		String developViewFormUrl 	= "develop_view_form.do";						// 훈련과정개발 상세보기 화면		
		String developModifyFormUrl = "develop_modify_form.do";						// 훈련과정개발 신청서 수정 화면		
		String developListFormUrl = "develop_list_form.do";							// 훈련과정개발 신청서 목록 화면
		String totalListFormUrl = "total_list_form.do";								// 훈련과정개발 종합이력 목록 화면
		String supportListFormUrl = "support_list_form.do";							// 훈련과정개발 맞춤개발 비용청구 목록 화면
		String supportWriteFormUrl = "support_write_form.do";						// 훈련과정개발 맞춤개발 비용청구 신청 화면
		String supportModifyFormUrl = "support_modify_form.do";						// 훈련과정개발 맞춤개발 비용청구 수정 화면
		String supportApplyUrl = "support_apply.do";								// 훈련과정개발 맞춤개발 비용청구 신청
		String supportViewFormUrl = "support_view_form.do";							// 훈련과정개발 맞춤개발 비용청구 상세보기 화면
		String supportWithDrawUrl = "support_withdraw.do";							// 훈련과정개발 맞춤개발 비용청구 회수
		String supportReturnRequestUrl = "support_returnRequest.do";				// 훈련과정개발 맞춤개발 비용청구 반려요청
		String supportdropRequestUrl = "support_dropRequest.do";					// 훈련과정개발 맞춤개발 비용청구 중도포기요청
		String supportTempSaveUrl = "support_tempSave.do";							// 훈련과정개발 맞춤개발 비용청구 중도포기요청
		
		String developReportUrl = "develop_report_form.do";							// 훈련과정개발 맞춤개발 리포트 출력
		String developWriteFormUrl 	= "develop_write_form.do";						// 훈련과정개발 추천활용(과정 수정) 화면		
			
		String developModifyUrl = "develop_modify.do";								// 신청서 수정
		String standardInfoUrl = "develop_standard_info.do";						// 표준개발 설명 콘텐츠
		String customizedInfoUrl = "develop_customized_info.do";					// 맞춤개발 설명 콘텐츠
		String doctorHelpUrl = "develop_doctor_help.do";							// 표준개발 주치의 지원요청
//		String downloadUrl = "develop_download.do";									// 파일 다운로드
		
		
		
		if(!StringUtil.isEmpty(baseQueryString)) {
			developSelectFormUrl += baseQueryString;
			developInfoFormUrl += baseQueryString;
			developRecommendFormUrl += baseQueryString;
			developTrainingViewFormUrl += baseQueryString;
			developTrainingListFormUrl += baseQueryString;
			developHelpFormUrl += baseQueryString;
			developWriteFormUrl += baseQueryString;
			developViewFormUrl += baseQueryString;
			developModifyFormUrl += baseQueryString;
			developModifyUrl += baseQueryString;
			developTempSaveUrl += baseQueryString;
			developWithDrawUrl += baseQueryString;
			developConsiderApproveUrl += baseQueryString;
			developApplyUrl += baseQueryString;
			developReturnReqeustUrl += baseQueryString;
			developDropReqeustUrl += baseQueryString;
			standardInfoUrl += baseQueryString;
			customizedInfoUrl += baseQueryString;
			doctorHelpUrl += baseQueryString;
			developConsiderReturnUrl += baseQueryString;
			developReportUrl += baseQueryString;
			
			
			totalListFormUrl += baseQueryString;
			supportListFormUrl += baseQueryString;
			supportWriteFormUrl += baseQueryString;
			supportModifyFormUrl += baseQueryString;
			developListFormUrl += baseQueryString;
			supportApplyUrl += baseQueryString;
			supportViewFormUrl += baseQueryString;
			supportWithDrawUrl += baseQueryString;
			supportReturnRequestUrl += baseQueryString;
			supportdropRequestUrl += baseQueryString;
			supportTempSaveUrl += baseQueryString;
			
			

		}
		
		/*
		 * 1. common path 명명 패턴
		 * 		ㄴ 경로 이름_URL 
		 * 			ㄴ (ex: develop_tempSave.do -> {REQUEST_TEMPSAVE_URL}
		 * */
		request.setAttribute("DEVELOP_INFO_FORM_URL", developSelectFormUrl);
		request.setAttribute("DEVELOP_INFOAGREE_FORM_URL", developInfoFormUrl);
		request.setAttribute("DEVELOP_RECOMMEND_FORM_URL", developRecommendFormUrl);
		request.setAttribute("DEVELOP_TRAINING_VIEW_FORM_URL", developTrainingViewFormUrl);
		request.setAttribute("DEVELOP_TRAINING_LIST_FORM_URL", developTrainingListFormUrl);
		request.setAttribute("DEVELOP_HELP_FORM_URL", developHelpFormUrl);
		request.setAttribute("DEVELOP_WRITE_FORM_URL", developWriteFormUrl);
		request.setAttribute("DEVELOP_VIEW_FORM_URL", developViewFormUrl);
		request.setAttribute("DEVELOP_MODIFY_FORM_URL", developModifyFormUrl);
		request.setAttribute("DEVELOP_MODIFY_URL", developModifyUrl);
		request.setAttribute("DEVELOP_TEMPSAVE_URL", developTempSaveUrl);
		request.setAttribute("DEVELOP_WITHDRAW_URL", developWithDrawUrl);
		request.setAttribute("DEVELOP_CONSIDERAPPROVE_URL", developConsiderApproveUrl);
		request.setAttribute("DEVELOP_APPLY_URL", developApplyUrl);
		request.setAttribute("DEVELOP_RETURNREQUEST_URL", developReturnReqeustUrl);
		request.setAttribute("DEVELOP_DROPREQUEST_URL", developDropReqeustUrl);
		request.setAttribute("DEVELOP_STANDARD_INFO_URL", standardInfoUrl);
		request.setAttribute("DEVELOP_CUSTOMIZED_INFO_URL", customizedInfoUrl);
		request.setAttribute("DEVELOP_DOCTOR_HELP_URL", doctorHelpUrl);
		request.setAttribute("DEVELOP_REPORT_FORM_URL", developReportUrl);
		
		request.setAttribute("DEVELOP_CONSIDERRETURN_URL", developConsiderReturnUrl);
		
		request.setAttribute("TOTAL_LIST_FORM_URL", totalListFormUrl);
		request.setAttribute("DEVELOP_LIST_FORM_URL", developListFormUrl);
		request.setAttribute("SUPPORT_LIST_FORM_URL", supportListFormUrl);
		request.setAttribute("SUPPORT_WRITE_FORM_URL", supportWriteFormUrl);
		request.setAttribute("SUPPORT_MODIFY_FORM_URL", supportModifyFormUrl);
		request.setAttribute("SUPPORT_APPLY_URL", supportApplyUrl);
		request.setAttribute("SUPPORT_VIEW_FORM_URL", supportViewFormUrl);
		request.setAttribute("SUPPORT_WITHDRAW_URL", supportWithDrawUrl);
		request.setAttribute("SUPPORT_RETURNREQUEST_URL", supportReturnRequestUrl);
		request.setAttribute("SUPPORT_DROPREQUEST_URL", supportdropRequestUrl);
		request.setAttribute("SUPPORT_TEMPSAVE_URL", supportTempSaveUrl);
		
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
			modiCnt = developService.getAuthCount(fnIdx, param);
			
			return (modiCnt > 0);
		}
		
		return true;
	}
}
