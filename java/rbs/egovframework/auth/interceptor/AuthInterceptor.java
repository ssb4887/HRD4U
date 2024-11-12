package rbs.egovframework.auth.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.woowonsoft.egovframework.form.DTForm;
import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.resource.RbsMessageSource;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;

import rbs.egovframework.LoginVO;
import rbs.egovframework.auth.Auth;
import rbs.modules.authManage.service.AuthManageService;
import rbs.modules.basket.web.BasketController;

/**
 * 
 * @author Administrator
 *
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Resource(name = "authManageService")
	private AuthManageService authManageService;
	
	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		Map<?, ?> matchTemplate = (Map<?, ?>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String siteId = StringUtil.getString(matchTemplate.get("siteId"));							// siteId
		// Auth 어노테이션 정보 가져오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		// 컨트롤러에 @Auth 어노테이션이 없을 때 (권한설정 어노테이션 추가해야 함)
		if(auth == null) {
			try {
				response.setContentType("text/html; charset=utf-8;");
				PrintWriter w = response.getWriter();
				w.write("<script>alert('권한오류입니다. 관리자에게 문의해 주십시오. : error(2)');history.go(-1);</script>");
				w.flush();
				w.close();
			} catch(IOException e) {
				log.debug("IOException Occurred");
			}
			return false;
		}
		
		// 가져온 Auth 어노테이션 값을 문자열로 변환
		String authRole = auth.role().toString();
		
		// 현재 메뉴의 정보 가져오기
		/*String siteURI = request.getRequestURI();
		String siteId = siteURI.split("/")[1];*/
		// 운영서버는 /hrddoctor이 추가가 되어서 아래 코드로 적용
		//String siteId = siteURI.split("/")[2];
		String menuIdx= request.getParameter("mId");

		// 로그인한 회원의 정보 가져오기 
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		// 로그인 세션이 만료된 경우 재로그인 
		if(loginVO == null) {
			
			// 사용자페이지와 관리자(주치의)페이지의 url 구조가 상이하여 밑의 구조처럼 분리
			String loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/" + siteId + "/login/login.do?mId=3";
			
			// 로그인 후 이전 페이지로 이동하기 위해 현재 메뉴의 url setting 및 encoding
			// 파라미터를 포함한 full url 가져오기
			String query = request.getQueryString();
			String fullUrl = request.getRequestURL().append("?").append(query).toString();
			// 자바스크립트 인코딩(encodeURIComponent)과 맟추기
			String encodeUrl = URLEncoder.encode(fullUrl, "UTF-8").replace("\\+", "%20").replace("\\%21", "!").replace("\\%27", "'").replace("\\%28", "(").replace("\\%29", ")").replace("\\%7E", "~");
			//System.out.println("encodeUrl === " + encodeUrl);
			
			//System.out.println("loginUrl === " + loginUrl);
			try {
				String loginMsg = rbsMessageSource.getMessage("message.no.login.confirm");
				response.setContentType("text/html; charset=utf-8;");
				PrintWriter w = response.getWriter();
				w.write("<script>var varConfirm = confirm('" + loginMsg + "'); if(varConfirm) {location.href='" + loginUrl + "&url=" + encodeUrl + "'} else {history.go(-1);}</script>");
				w.flush();
				w.close();
			} catch(IOException e) {
				log.debug("IOException Occurred");
			}
			return false;
		}
		
		String memberIdx = loginVO.getMemberIdx();
		int userTypeIdx = loginVO.getUsertypeIdx();
		
		// 주치의, 본부직원(userTypeIdx 30이상)은 사용자 페이지(web)에 접근할 수 없고 기업회원, 민간센터, 컨설턴트는 관리자 페이지(dct)에 접근할 수 없음
//		if((userTypeIdx >= 30 && siteId.equals("web")) || (userTypeIdx < 30 && siteId.equals("dct"))) {
//			try {
//				response.setContentType("text/html; charset=utf-8;");
//				PrintWriter w = response.getWriter();
//				w.write("<script>var varConfirm = confirm('해당 아이디로는 접근할 수 없습니다.'); history.go(-1);</script>");
//				w.flush();
//				w.close();
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//			return false;
//		} 
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		Map<String, Object> param2 = new HashMap<String, Object>();
		List<DTForm> searchList2 = new ArrayList<DTForm>();
		DataMap authList = null;
		
		// 해당 회원의 auth_idx 가져오기
		searchList.add(new DTForm("A.MEMBER_IDX", memberIdx));
		searchList.add(new DTForm("A.APPLY_YN", "Y"));
		param.put("searchList", searchList);
		param.put("findAuth", "member");
		
		Integer authIdx = authManageService.getAuthIdx(param);
		
		if(authIdx != null) {
			// auth_idx가 있다면(userTypeIdx 30이상 = 주치의, 본부직원) 가져온 auth_idx, siteId, menuIdx로 해당 메뉴의 권한 가져오기
			searchList2.add(new DTForm("A.AUTH_IDX", authIdx));
			searchList2.add(new DTForm("A.SITE_ID", siteId));
			searchList2.add(new DTForm("A.MENU_IDX", menuIdx));
			param2.put("searchList", searchList2);
			
			authList = authManageService.getAuthMenu(param2);
			// System.out.println("authList === " + authList);
		} else {
			// auth_idx가 없다면(userTypeIdx 30미만 = 기업회원, 민간센터, 컨설턴트) userTypeIdx로 RBS_AUTH에서 auth_idx 가져오기
			param.clear();
			searchList.clear();

			searchList.add(new DTForm("A.USERTYPE_IDX", userTypeIdx));
			param.put("searchList", searchList);
			param.put("USERTYPE_IDX", userTypeIdx);
			param.put("findAuth", "auth");
			
			authIdx = authManageService.getAuthIdx(param);
			
			// 가져온 auth_idx, siteId, menuIdx로 해당 메뉴의 권한 가져오기
			searchList2.add(new DTForm("A.AUTH_IDX", authIdx));
			searchList2.add(new DTForm("A.SITE_ID", siteId));
			searchList2.add(new DTForm("A.MENU_IDX", menuIdx));
			param2.put("searchList", searchList2);
			
			authList = authManageService.getAuthMenu(param2);
		}
		
		// 어노테이션은 있는데 rbs_auth_menu에 없을 때(auth_idx가 rbs_auth_menu에 등록이 되지 않았음) > 권한관리에서 등록 필요
		if(authList == null) {
			try {
				response.setContentType("text/html; charset=utf-8;");
				PrintWriter w = response.getWriter();
				w.write("<script>alert('해당 그룹에 대한 권한이 부여되지 않았습니다. 관리자에게 문의해 주십시오.');history.go(-1);</script>");
				w.flush();
				w.close();
			} catch(IOException e) {
				log.debug("IOException Occurred");
			}
			return false;
		}
		
		String msg = rbsMessageSource.getMessage("message.no.auth");
		
		// menuType의 권한을 확인하기 전에 메뉴에 대한 권한 먼저 확인하기, Y-허용 N-권한없음
		String auth_m = (String) authList.get("M");
		if(auth_m == null || !auth_m.equals("Y")) {
			//System.out.println("menuno");
			try {
				response.setContentType("text/html; charset=utf-8;");
				PrintWriter w = response.getWriter();
				w.write("<script>alert('해당 아이디로는 접근할 수 없습니다.');history.go(-1);</script>");
				w.flush();
				w.close();
			} catch(IOException e) {
				log.debug("IOException Occurred");
			}
			return false;
		}
		
		// menuType의 권한 확인, Y-허용 N-권한없음
		String auth_crudf = (String) authList.get(authRole);
		//System.out.println("auth_crudf -==== " + auth_crudf);
		if(auth_crudf == null || !auth_crudf.equals("Y")) {
			 // System.out.println("crudno");
			try {
				response.setContentType("text/html; charset=utf-8;");
				PrintWriter w = response.getWriter();
				w.write("<script>alert('" + msg + "');history.go(-1);</script>");
				w.flush();
				w.close();
			} catch(IOException e) {
				log.debug("IOException Occurred");
			}
			return false;
		}
		
		return true;
	}
}
