package rbs.modules.member.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;

import com.woowonsoft.egovframework.annotation.ModuleAttr;
import com.woowonsoft.egovframework.annotation.ModuleAuth;
import com.woowonsoft.egovframework.annotation.ModuleMapping;
import com.woowonsoft.egovframework.annotation.ParamMap;
import com.woowonsoft.egovframework.form.DTForm;
import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.form.ParamForm;
import com.woowonsoft.egovframework.prop.RbsProperties;
import com.woowonsoft.egovframework.util.CodeHelper;
import com.woowonsoft.egovframework.util.CookieUtil;
import com.woowonsoft.egovframework.util.FormValidatorUtil;
import com.woowonsoft.egovframework.util.JSONObjectUtil;
import com.woowonsoft.egovframework.util.MenuUtil;
import com.woowonsoft.egovframework.util.MessageUtil;
import com.woowonsoft.egovframework.util.ModuleUtil;
import com.woowonsoft.egovframework.util.PathUtil;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import rbs.egovframework.Defines;
import rbs.egovframework.LoginVO;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.egovframework.com.utl.slm.RbsHttpSessionBindingListener;
import rbs.egovframework.util.JwtUtil;
import rbs.egovframework.util.SSOUtil;
import rbs.modules.diagnosis.service.DiagnosisService;
import rbs.modules.member.service.MemberLogService;

@Controller
@RequestMapping({"/{siteId}"})
@ModuleMapping(moduleId="login", confModule="member", useSDesign=true)

public class LoginController extends LoginComController{
	
	@Resource(name = "memberLogService")
	protected MemberLogService memberLogService;
	
	@Resource(name = "loginValidator")
	protected LoginValidator loginValidator;
	
	@Resource(name = "diagnosisService")
	private DiagnosisService diagnosisService;
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping("/login/login.do")
	public String login(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String siteId = attrVO.getSiteId();
		
		if(UserDetailsHelper.isLogin()){
			model.addAttribute("message",   MessageUtil.getAlert(isAjax, null, "fn_procAction('" + PathUtil.getAddProtocolToFullPath() + "');"));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		JSONObject itemInfo = getItemInfo(attrVO.getModuleItem());
		model.addAttribute("itemInfo", itemInfo);

		JSONObject settingInfo = getSettingInfo(attrVO.getModuleSetting());
		
		boolean useSsl = JSONObjectUtil.isEquals(settingInfo, "use_ssl", "1");
		
		// 기본경로
		fn_setCommonPath(attrVO, useSsl);
		
		if(getIdSSO(request, response) != null) return getViewPath("/loginSSO");
		if(StringUtil.isEquals(siteId, "web")) return getViewPath("/loginWeb"); 
		return getViewPath("/loginDct");
	}

	@RequestMapping(value="/login/loginPreProc.do", method=RequestMethod.POST)
	public String loginPreProc(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		String siteMode = attrVO.getSiteMode();
		String ajaxPName = attrVO.getAjaxPName();
		JSONObject itemInfo = getItemInfo(attrVO.getModuleItem());
		JSONObject settingInfo = getSettingInfo(attrVO.getModuleSetting());
		
		Map<?, ?> matchTemplate = (Map<?, ?>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String siteId = StringUtil.getString(matchTemplate.get("siteId"));							// siteId
		
		boolean useSsl = JSONObjectUtil.isEquals(settingInfo, "use_ssl", "1");
		
		// 기본경로
		fn_setCommonPath(attrVO, useSsl);
		
		if(UserDetailsHelper.isLogin()){
			model.addAttribute("message", MessageUtil.getAlert(isAjax, null, "fn_procAction('" + PathUtil.getAddProtocolToFullPath() + "');"));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 아이디, 비밀번호 필수입력 체크
		String errorMessage = FormValidatorUtil.getErrorMessage(ajaxPName, parameterMap, loginValidator, itemInfo);
		if(!StringUtil.isEmpty(errorMessage)) {
			model.addAttribute("message", errorMessage);
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 1. 로그인 처리
 		LoginVO resultVO = loginService.login(siteMode, request.getRemoteAddr(), parameterMap, itemInfo, settingInfo, attrVO.getItemInfo());
		
 		log.info("resultVO {}", resultVO);
 		
 		if(resultVO != null) {
 			//RBS_AUTH_MEMBER 조회
 	 		String memberIdx = resultVO.getMemberIdx() == null ? "" : resultVO.getMemberIdx();
 	 		if(!memberIdx.isEmpty()) { //RBS_AUTH_MEMBER에 지정된 userTypeIdx 조회
 	 			String userTypeIdx = loginService.selectRbsAuthMember(memberIdx);
 	 			log.info("userTypeIdx {}", userTypeIdx);
 	 			if(userTypeIdx != null) {
 	 				resultVO.setUsertypeIdx(Integer.parseInt(userTypeIdx));
 	 			}
 	 			
 	 		}
 	 		// web으로 접속시 공단직원인지 아닌지 체크, 존재한다면 컨설턴트권한 부여
 	 		if(StringUtil.isEquals(siteId, "web")) {
 	 			int checkSabun = loginService.checkSabun(memberIdx);
 	 			if(checkSabun > 0) {
 	 				resultVO.setUsertypeIdx(Integer.parseInt("10"));
 	 			}
 	 		}
 	 		
 		}
 	
 		if (resultVO != null) {
			resultVO.setSiteId(siteId);
			if(!resultVO.isLogin()) {
				// 로그 저장 - 로그인 실패
				Logger logger = null;
	    		if(resultVO.getUsertypeIdx() >= 50) logger = LoggerFactory.getLogger("admLogin");
	    		else logger = LoggerFactory.getLogger("usrLogin");

	    		JSONObject moduleItem = JSONObjectUtil.getJSONObject(request.getAttribute("moduleItem"));
	    		JSONObject memberItemInfo = JSONObjectUtil.getJSONObject(moduleItem, "item_info");
	    		JSONObject memberItems = JSONObjectUtil.getJSONObject(memberItemInfo, "items");
	    		
	    		memberLogService.setEprivacy(logger, rbsMessageSource.getMessage("message.member.log.login.fail"), null, null, memberItems, resultVO.getMemberIdx(), resultVO.getMemberIdx(), resultVO.getMemberId(), resultVO.getMemberName());
			}
			
			if(resultVO.getLoginFail() >= 5){
				if(isAdmMode){
					model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.adm.logIn.fail.count"), "fn_procAction('" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_LOGIN"))) + "');"));
				}else{
					model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.logIn.fail.count"), "fn_procAction('" + PathUtil.getAddProtocolToFullPath(MenuUtil.getMenuUrl(Defines.CODE_MENU_PWSEARCH)) + "');"));
				}
				return RbsProperties.getProperty("Globals.f ail" + ajaxPName + ".path");
			}

			if(resultVO.isLogin()) {
				HttpSession session = request.getSession();
				
	    		if(StringUtil.isEquals(siteId, "dct") && (resultVO.getUsertypeIdx() < 30)) {
	    			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.auth")));
	    			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	    		}
	    		if(StringUtil.isEquals(siteId, "web") && (resultVO.getUsertypeIdx() >= 30)) {
    				model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.auth")));
    				return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	    		}
				
				/*
				// 암/복호화 여부에 따른 원본명 저장
				JSONObject memberItemInfo = attrVO.getItemInfo();
				JSONObject items = JSONObjectUtil.getJSONObject(memberItemInfo, "items");
				JSONObject item = null;
				
				// 아이디
				item = JSONObjectUtil.getJSONObject(items, "mbrId");
				resultVO.setMemberIdOrg(ModuleUtil.getPrivDecValue(item, resultVO.getMemberId()));
	
				// 이름
				item = JSONObjectUtil.getJSONObject(items, "mbrName");
				resultVO.setMemberNameOrg(ModuleUtil.getPrivDecValue(item, resultVO.getMemberName()));
				
				// 이메일
				item = JSONObjectUtil.getJSONObject(items, "mbrEmail");
				resultVO.setMemberEmailOrg(ModuleUtil.getPrivDecValue(item, resultVO.getMemberEmail()));
				
				resultVO.setGroupList(loginService.getGroupList(resultVO.getMemberIdx()));*/
				
				// 2-1. 로그인 정보를 세션에 저장
				session.invalidate();
				session = request.getSession(true);
				session.setAttribute("preLoginVO", resultVO);
				// 중복 로그인 방지
				/*RbsHttpSessionBindingListener listener = RbsHttpSessionBindingListener.INSTANCE;
				if(listener.findByLoginId(resultVO.getMemberId()))
				{
					model.addAttribute("message", MessageUtil.getConfirm(isAjax, rbsMessageSource.getMessage("message.duplicate.login"), "fn_procAction('" + request.getAttribute("URL_LOGINPROC") + "');", "fn_procAction('" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_LOGIN"))) + "');"));
					return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
				}*/
	
				model.addAttribute("message", MessageUtil.getAlert(isAjax, null, "fn_procAction('" + request.getAttribute("URL_LOGINPROC") + "');"));
				return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
			}
		}

		// 로그인 실패 - ID / PW 모두 일치하지 않는 경우
		if (resultVO == null) {
			// 로그 저장 - 로그인 시도
			Logger logger = LoggerFactory.getLogger("usrLogin");
	
			JSONObject moduleItem = JSONObjectUtil.getJSONObject(request.getAttribute("moduleItem"));
			JSONObject memberItemInfo = JSONObjectUtil.getJSONObject(moduleItem, "item_info");
			JSONObject memberItems = JSONObjectUtil.getJSONObject(memberItemInfo, "items");
	
			int useRSA = JSONObjectUtil.getInt(settingInfo, "use_rsa");
			JSONObject mbrIdItem = JSONObjectUtil.getJSONObject(JSONObjectUtil.getJSONObject(itemInfo, "items"), "mbrId");
			String encId = ModuleUtil.getParamToDBValue(useRSA, mbrIdItem, parameterMap.getString("mbrId"));
			DataMap dt = new DataMap();
			dt.put("MEMBER_ID", encId);
			memberLogService.setEprivacy(logger, rbsMessageSource.getMessage("message.member.log.login.access"), dt, null, memberItems, "OTHER", "OTHER", encId, null);
		}
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.member")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	

	@RequestMapping(value="/login/loginProc.do")
	public String loginProc(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		String ajaxPName = attrVO.getAjaxPName();
		String siteId = attrVO.getSiteId();
		JSONObject settingInfo = getSettingInfo(attrVO.getModuleSetting());
		
		boolean useSsl = JSONObjectUtil.isEquals(settingInfo, "use_ssl", "1");
		
		if(UserDetailsHelper.isLogin()){
			model.addAttribute("message",   MessageUtil.getAlert(isAjax, null, "fn_procAction('" + PathUtil.getAddProtocolToFullPath() + "');"));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		HttpSession session = request.getSession();
		LoginVO resultVO = (LoginVO)session.getAttribute("preLoginVO");
		// 1. 로그인 처리
		if (resultVO != null) {
			
			loginService.loginProc(resultVO);
			/*
			// 2-1. 로그인 정보를 세션에 저장
			session.setAttribute("loginVO", resultVO);
			
			// 중복 로그인 방지
			EgovHttpSessionBindingListener listener = EgovHttpSessionBindingListener.INSTANCE;
			session.setAttribute(resultVO.getMemberId(), listener);
			*/
			// 로그 저장
			Logger logger = null;
			int admMbrType = RbsProperties.getPropertyInt("Globals.code.USERTYPE_ADMIN");
    		if(resultVO.getUsertypeIdx() >= admMbrType) logger = LoggerFactory.getLogger("admLogin");
    		else logger = LoggerFactory.getLogger("usrLogin");

    		JSONObject moduleItem = JSONObjectUtil.getJSONObject(request.getAttribute("moduleItem"));
    		JSONObject memberItemInfo = JSONObjectUtil.getJSONObject(moduleItem, "item_info");
    		JSONObject memberItems = JSONObjectUtil.getJSONObject(memberItemInfo, "items");
    		
    		memberLogService.setEprivacy(logger, rbsMessageSource.getMessage("message.member.log.login"), null, null, memberItems, resultVO.getMemberIdx(), resultVO.getMemberIdx(), resultVO.getMemberId(), resultVO.getMemberName());
    		
    		loginService.loginUpdate(resultVO.getMemberIdx(), request.getRemoteAddr());
    		
    		
    		// 비밀번호 수정일자 확인 
    		String url = null;
    		double pwdModiIntv = resultVO.getPwdModiIntv();
    		double pwdModiIntv2 = resultVO.getPwdModiIntv2();
    		boolean isNextChangePwd = StringUtil.isEquals(resultVO.getPwdModiType(), "1");
    		if(!attrVO.isAdmMode() && ((pwdModiIntv > 0 && (!isNextChangePwd || (isNextChangePwd && pwdModiIntv2 > 0))) || StringUtil.isEquals(resultVO.getPwdModiType(), "2"))){
        		// 기본경로
        		fn_setCommonPath(attrVO, useSsl);
    			url = PathUtil.getAddProtocolToFullPath(StringUtil.getString(request.getAttribute("URL_PWDMODI")));
    		} else url= PathUtil.getAddProtocolToFullPath();
    		
    		//siteId가 dct 일때는 기초진단 페이지로 이동
    		if(StringUtil.isEquals(siteId, "dct") ) {
    			url = MenuUtil.getMenuUrl(48);
    		} else {
    			// 개인회원, 컨설턴트, 민간센터 개인정보제공동의(기업회원을 제외한)
    			if(resultVO.getUsertypeIdx() == 3 || resultVO.getUsertypeIdx() == 0 || resultVO.getUsertypeIdx() == 10 || resultVO.getUsertypeIdx() == 20) {
    				// 개인정보제공 테이블에 데이터가 있는지 확인
    				String checkAgree = loginService.checkAgree(resultVO.getMemberIdx());
            		// 데이터 존재 무 또는 동의 하지 않았을 시
            		if(!StringUtil.isEquals(checkAgree, "Y")) {
            			url = MenuUtil.getMenuUrl(117);
            			fn_setCommonPath(attrVO, useSsl);
            			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.agree"), "fn_procAction('" + url + "', '1');"));
            			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
            		};
    			}	
    			
    			// 기초진단이 하나라도 있으면 종합관리로
    			if(resultVO.getUsertypeIdx() == 5) {
    				List<DTForm> searchList = new ArrayList<DTForm>();
    				Map<String, Object> param = new HashMap<String, Object>();
    				param.put("bplNo", resultVO.getBplNo());
    				param.put("searchList", searchList);
    				int totalCount = diagnosisService.getBplCount(param);
    				if(totalCount > 0) {
    					url = MenuUtil.getMenuUrl(48);
    				}
    			} else if(resultVO.getUsertypeIdx() == 10) {
    				url = MenuUtil.getMenuUrl(48);
    			}
    			
    			
    		}
			model.addAttribute("message", MessageUtil.getAlert(isAjax, null, "fn_procAction('" + url + "', '1');"));

			return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
		}

		
		// 기본경로
		fn_setCommonPath(attrVO, useSsl);
		
		// 로그인 실패
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.member"), "fn_procAction('" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_LOGIN"))) + "', '1');"));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	
	/**
	 * 개인정보제공동의
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/login/agree.do")
	public String agree(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		JSONObject settingInfo = getSettingInfo(attrVO.getModuleSetting());
		JSONObject itemInfo = attrVO.getItemInfo();

		// 1. 작성자명 setting
		DataMap dt = new DataMap();

		// 2. 항목설정
		String submitType = "agree";
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		
		if(loginVO != null)	{
			String memberIdx = loginVO.getMemberIdx();
			JSONObject nameItem = JSONObjectUtil.getJSONObject(items, "name");
			
			dt.put("NAME", memberIdx);
		}

		// 3. 속성 setting
		model.addAttribute("dt", dt);
		model.addAttribute("optnHashMap", CodeHelper.getItemOptnHashMap(items, itemOrder));
		model.addAttribute("submitType", submitType);

    	// 4. 기본경로
		boolean useSsl = JSONObjectUtil.isEquals(settingInfo, "use_ssl", "1");
		
		// 기본경로
		fn_setCommonPath(attrVO, useSsl);

    	// 5. form action
    	model.addAttribute("URL_AGREEPROC", request.getAttribute("URL_AGREEPROC"));
    	
		return getViewPath("/agree");
	}

	
	/**
	 * 등록처리
	 * @param parameterMap
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ModuleAuth(name="WRT")
	@RequestMapping(method=RequestMethod.POST, value = "/login/agreeProc.do")
	public String agreeProc(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject itemInfo = attrVO.getItemInfo();
		String ajaxPName = attrVO.getAjaxPName();

		// 1. 필수입력 체크
		// 1.1 항목설정
		String submitType = "write";		// 항목 order명
		String inputFlag = "write";			// 항목설정 중 write_type, modify_type 구분
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "proc_order");
		parameterMap.put("_itemInputTypeFlag", submitType);
		
		
		boolean useSsl = JSONObjectUtil.isEquals(settingInfo, "use_ssl", "1");
		// 1.2 필수입력 체크
		
		
		// 2. DB
    	int result = loginService.agreeInsert(request.getRemoteAddr(), parameterMap, settingInfo, items, itemOrder);
    	if(result > 0) {
    		// 2.1 저장 성공
        	// 기본경로
        	fn_setCommonPath(attrVO, useSsl);
			model.addAttribute("message",   MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.insert"), "fn_procAction('" + PathUtil.getAddProtocolToFullPath() + "', '1');"));
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
	

	@RequestMapping("/login/nmauth.do")
	public String nmauth(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();

		boolean isAjax = attrVO.isAjax();
		
		String ajaxPName = attrVO.getAjaxPName();
		
		String siteId = attrVO.getSiteId();
		
		int loginUsertypeIdx = 0;
		if(loginVO != null) loginUsertypeIdx = loginVO.getUsertypeIdx();
		if(UserDetailsHelper.isLogin() || loginUsertypeIdx == RbsProperties.getPropertyInt("Globals.code.USERTYPE_SMEMBER")){
			model.addAttribute("message",   MessageUtil.getAlert(isAjax, null, "fn_procAction('" + PathUtil.getAddProtocolToFullPath() + "');"));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}

		JSONObject itemInfo = getItemInfo(attrVO.getModuleItem());
		model.addAttribute("itemInfo", itemInfo);

		JSONObject settingInfo = getSettingInfo(attrVO.getModuleSetting());
		
		boolean useSsl = JSONObjectUtil.isEquals(settingInfo, "use_ssl", "1");
		
		// 기본경로
		fn_setCommonPath(attrVO, useSsl);

		if(StringUtil.isEquals(siteId, "web")) return getViewPath("/loginWeb"); 
		return getViewPath("/loginDct");
	}
	
	/**
	 * 로그아웃
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout/logout.do")
	public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		loginService.logout();
		// 자동로그아웃 시간 삭제
		CookieUtil.deleteCookie(request, response, "rbis_auto_logout");
		
		// SSO 로그아웃
		CookieUtil.deleteCookie(request, response, "access-token");
		CookieUtil.deleteCookie(request, response, "refresh-token");
		
		/*HttpSession session = request.getSession();
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		if(loginVO != null) session.setAttribute(loginVO.getMemberId(), null);
		session.setAttribute("loginVO", null);
		session.setAttribute("selSiteVO", null);*/

		// dashboard 기본으로 설정된 접근권한 체크 : 현재 dashboard 접근권한 설정 안하기때문
		JSONObject indexInfo = MenuUtil.getMenuInfo(Defines.CODE_MENU_INDEX);
		int usertypeIdx = JSONObjectUtil.getInt(indexInfo, "usertype_idx");
		if(usertypeIdx > 0) {
			try {
				response.sendRedirect(MenuUtil.getMenuUrl(true, 3, null));
			} catch(Exception e) {}
		} else {
			try {
				response.sendRedirect(MenuUtil.getMenuUrl(true, 1, null));
			} catch(Exception e) {}
		}
		
		return null;
	}
	
	@RequestMapping(value = "/logout/logoutSSO.do")
	public String logoutSSO(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		loginService.logout();
		// 자동로그아웃 시간 삭제
		CookieUtil.deleteCookie(request, response, "rbis_auto_logout");
		
		/*int usertypeIdx = JSONObjectUtil.getInt(indexInfo, "usertype_idx");
		if(usertypeIdx > 0) {
			try {
				response.sendRedirect(MenuUtil.getMenuUrl(true, 3, null));
			} catch(Exception e) {}
		} else {
			try {
				response.sendRedirect(MenuUtil.getMenuUrl(true, 1, null));
			} catch(Exception e) {}
		}*/
		response.sendRedirect(request.getContextPath() + "/web/login/login.do?mId=3");
		
		return null;
	}
	
	@RequestMapping(value="/login/loginSSOPreProc.do", method=RequestMethod.POST)
	public String loginSSOPreProc(@ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		boolean isAjax = attrVO.isAjax();
		boolean isAdmMode = attrVO.isAdmMode();
		String siteMode = attrVO.getSiteMode();
		String ajaxPName = attrVO.getAjaxPName();
		JSONObject itemInfo = getItemInfo(attrVO.getModuleItem());
		JSONObject settingInfo = getSettingInfo(attrVO.getModuleSetting());
		
		Map<?, ?> matchTemplate = (Map<?, ?>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String siteId = StringUtil.getString(matchTemplate.get("siteId"));							// siteId
		
		boolean useSsl = JSONObjectUtil.isEquals(settingInfo, "use_ssl", "1");
		
		// 기본경로
		fn_setCommonPath(attrVO, useSsl);
		
		if(UserDetailsHelper.isLogin()){
			model.addAttribute("message", MessageUtil.getAlert(isAjax, null, "fn_procAction('" + PathUtil.getAddProtocolToFullPath() + "');"));
			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
		}
		
		// 아이디 꺼내기
		String id = getIdSSO(request, response);
		
		// 1. 로그인 처리
		LoginVO resultVO = loginService.loginSSO(siteMode, request.getRemoteAddr(), id, itemInfo, settingInfo, attrVO.getItemInfo());
		if (resultVO != null) {
			resultVO.setSiteId(siteId);
			if(!resultVO.isLogin()) {
				// 로그 저장 - 로그인 실패
				Logger logger = null;
	    		if(resultVO.getUsertypeIdx() >= 50) logger = LoggerFactory.getLogger("admLogin");
	    		else logger = LoggerFactory.getLogger("usrLogin");

	    		JSONObject moduleItem = JSONObjectUtil.getJSONObject(request.getAttribute("moduleItem"));
	    		JSONObject memberItemInfo = JSONObjectUtil.getJSONObject(moduleItem, "item_info");
	    		JSONObject memberItems = JSONObjectUtil.getJSONObject(memberItemInfo, "items");
	    		
	    		memberLogService.setEprivacy(logger, rbsMessageSource.getMessage("message.member.log.login.fail"), null, null, memberItems, resultVO.getMemberIdx(), resultVO.getMemberIdx(), resultVO.getMemberId(), resultVO.getMemberName());
			}
			
			if(resultVO.getLoginFail() >= 5){
				if(isAdmMode){
					model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.adm.logIn.fail.count"), "fn_procAction('" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_LOGIN"))) + "');"));
				}else{
					model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.logIn.fail.count"), "fn_procAction('" + PathUtil.getAddProtocolToFullPath(MenuUtil.getMenuUrl(Defines.CODE_MENU_PWSEARCH)) + "');"));
				}
				return RbsProperties.getProperty("Globals.f ail" + ajaxPName + ".path");
			}

			if(resultVO.isLogin()) {
				HttpSession session = request.getSession();
	    		if(StringUtil.isEquals(siteId, "dct") && (resultVO.getUsertypeIdx() < 30)) {
	    			model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.auth")));
	    			return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	    		}
	    		if(StringUtil.isEquals(siteId, "web") && (resultVO.getUsertypeIdx() >= 30)) {
    				model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.auth")));
    				return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	    		}
				
				/*
				// 암/복호화 여부에 따른 원본명 저장
				JSONObject memberItemInfo = attrVO.getItemInfo();
				JSONObject items = JSONObjectUtil.getJSONObject(memberItemInfo, "items");
				JSONObject item = null;
				
				// 아이디
				item = JSONObjectUtil.getJSONObject(items, "mbrId");
				resultVO.setMemberIdOrg(ModuleUtil.getPrivDecValue(item, resultVO.getMemberId()));
	
				// 이름
				item = JSONObjectUtil.getJSONObject(items, "mbrName");
				resultVO.setMemberNameOrg(ModuleUtil.getPrivDecValue(item, resultVO.getMemberName()));
				
				// 이메일
				item = JSONObjectUtil.getJSONObject(items, "mbrEmail");
				resultVO.setMemberEmailOrg(ModuleUtil.getPrivDecValue(item, resultVO.getMemberEmail()));
				
				resultVO.setGroupList(loginService.getGroupList(resultVO.getMemberIdx()));*/
				
				// 2-1. 로그인 정보를 세션에 저장
				session.invalidate();
				session = request.getSession(true);
				session.setAttribute("preLoginVO", resultVO);
				// 중복 로그인 방지
				/*RbsHttpSessionBindingListener listener = RbsHttpSessionBindingListener.INSTANCE;
				if(listener.findByLoginId(resultVO.getMemberId()))
				{
					model.addAttribute("message", MessageUtil.getConfirm(isAjax, rbsMessageSource.getMessage("message.duplicate.login"), "fn_procAction('" + request.getAttribute("URL_LOGINPROC") + "');", "fn_procAction('" + PathUtil.getAddProtocolToPagePath(StringUtil.getString(request.getAttribute("URL_LOGIN"))) + "');"));
					return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
					
				}*/
	
				model.addAttribute("message", MessageUtil.getAlert(isAjax, null, "fn_procAction('" + request.getAttribute("URL_LOGINPROC") + "');"));
				return RbsProperties.getProperty("Globals.message" + ajaxPName + ".path");
			}
		}
		// 로그인 실패 - ID / PW 모두 일치하지 않는 경우
		if (resultVO == null) {
			// 로그 저장 - 로그인 시도
			Logger logger = LoggerFactory.getLogger("usrLogin");
	
			JSONObject moduleItem = JSONObjectUtil.getJSONObject(request.getAttribute("moduleItem"));
			JSONObject memberItemInfo = JSONObjectUtil.getJSONObject(moduleItem, "item_info");
			JSONObject memberItems = JSONObjectUtil.getJSONObject(memberItemInfo, "items");
	
			int useRSA = JSONObjectUtil.getInt(settingInfo, "use_rsa");
			JSONObject mbrIdItem = JSONObjectUtil.getJSONObject(JSONObjectUtil.getJSONObject(itemInfo, "items"), "mbrId");
			String encId = ModuleUtil.getParamToDBValue(useRSA, mbrIdItem, parameterMap.getString("mbrId"));
			DataMap dt = new DataMap();
			dt.put("MEMBER_ID", encId);
			memberLogService.setEprivacy(logger, rbsMessageSource.getMessage("message.member.log.login.access"), dt, null, memberItems, "OTHER", "OTHER", encId, null);
		}
		model.addAttribute("message", MessageUtil.getAlert(isAjax, rbsMessageSource.getMessage("message.no.member")));
		return RbsProperties.getProperty("Globals.fail" + ajaxPName + ".path");
	}
	
	public String getIdSSO(HttpServletRequest request, HttpServletResponse response) {
		String id = null;
		Cookie access_cookie = null;
		try {
			access_cookie = SSOUtil.getCookie(request, "access-token");
			Cookie refresh_cookie = SSOUtil.getCookie(request, "refresh-token");
			if(access_cookie == null && refresh_cookie != null) {
				String refresh_token = refresh_cookie.getValue();
				Jws<Claims> claims = JwtUtil.parseRefreshJWT(refresh_token);
				id = JwtUtil.getId(claims);
				Map token = loginService.getRefreshToken(id);
				if(SSOUtil.validateRefreshToken(token, refresh_token)) {
					access_cookie = JwtUtil.createAccessCookie(id);
				}
			}
		} catch(Exception e) {
			log.debug("Exception occurred");
		}
		
		try {
			if(access_cookie != null) {
				Jws<Claims> claims = JwtUtil.parseAccessJWT(access_cookie.getValue());
				id = JwtUtil.getId(claims);
				return id;
			}
		} catch(ExpiredJwtException e) {
			log.debug("ExpiredJwtException Occurred!");
			
		}
		
		return id;
	}
}