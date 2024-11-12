package rbs.egovframework.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;

import rbs.egovframework.LoginVO;
import rbs.modules.member.web.LoginController;

/**
 * 
 * @author Administrator
 *
 */
public class LoginSessionControlInterceptor extends HandlerInterceptorAdapter {
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(LoginSessionControlInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Map<?, ?> matchTemplate = (Map<?, ?>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String siteId = StringUtil.getString(matchTemplate.get("siteId"));							// siteId
		
		//로그인시 loginVO저장
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		HttpSession session = request.getSession(true);
		session.setAttribute("preLoginVO", null);
		
		if(loginVO != null) {
			if(loginVO.getUsertypeIdx() == 55) { 						// 시스템관리자는 사이트이동시 로그아웃 기능 제외
				return true;
			}else if(!StringUtil.isEquals(loginVO.getSiteId(), siteId)) {	// 사이트이동시 로그아웃 기능
				log.info("Login Session delete");
				/*session.setAttribute("loginVO", null);*/
			}
		}
		
		return true;
	}
}
