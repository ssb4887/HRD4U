package rbs.modules.member.web;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import nl.captcha.Captcha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import rbs.egovframework.Defines;
import rbs.egovframework.LoginVO;
import rbs.egovframework.util.PrivAuthUtil;
import rbs.modules.member.service.MemberLogService;
import rbs.modules.member.service.MemberService;

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
import com.woowonsoft.egovframework.util.CodeHelper;
import com.woowonsoft.egovframework.util.DataSecurityUtil;
import com.woowonsoft.egovframework.util.FormValidatorUtil;
import com.woowonsoft.egovframework.util.JSONObjectUtil;
import com.woowonsoft.egovframework.util.MenuUtil;
import com.woowonsoft.egovframework.util.MessageUtil;
import com.woowonsoft.egovframework.util.ModuleUtil;
import com.woowonsoft.egovframework.util.PathUtil;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;
import com.woowonsoft.egovframework.web.ModuleController;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 사용자모드 : 회원가입/내정보수정/아이디찾기/비밀번호찾기/회원탈퇴
 * @author user
 *
 */
public class MemberJoinComController extends ModuleController{
	
	@Resource(name = "memberService")
	protected MemberService memberService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "rbsMessageSource")
	protected RbsMessageSource rbsMessageSource;
	
	/**
	 * 내정보수정 경로
	 */
	public void fn_setMyInfoPath(ModuleAttrVO attrVO) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		DataForm queryString = attrVO.getQueryString();
		
		String inputName = "myInfo.do";
		String inputProcName = "myInfoProc.do";
		String pwdmodiName = "pwdmodi.do";
		String pwdmodiProcName = "pwdmodiProc.do";

		JSONObject settingInfo = attrVO.getSettingInfo();

		boolean useSsl = JSONObjectUtil.isEquals(settingInfo, "use_ssl", "1");
		
		if(useSsl){
			inputProcName = PathUtil.getSslPagePath(inputProcName);
			pwdmodiProcName = PathUtil.getSslPagePath(pwdmodiProcName);
		}
		
		PathUtil.fn_setInputPath(queryString, baseParams, inputName, inputProcName);
		
		String allQueryString = StringUtil.getString(request.getAttribute("allQueryString"));

		request.setAttribute("URL_PWDMODI", pwdmodiName + allQueryString);
		request.setAttribute("URL_PWDMODIPROC", pwdmodiProcName + allQueryString);
		
		//request.setAttribute("URL_FACEBOOKLOGIN", facebookOAuth2Parameters.getRedirectUri());
		request.setAttribute("URL_FACEBOOKLOGIN", "facebookJoin.do" + allQueryString);
		request.setAttribute("URL_NAVERLOGIN", "naverJoin.do" + allQueryString);
		request.setAttribute("URL_KAKAOLOGIN", "kakaoJoin.do" + allQueryString);
		request.setAttribute("URL_GOOGLELOGIN", "googleJoin.do" + allQueryString);
		request.setAttribute("URL_SNSJOINOK", "snsJoinProc.do" + allQueryString);
	}
}
