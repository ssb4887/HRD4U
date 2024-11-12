package rbs.egovframework.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbs.egovframework.LoginVO;
public class SSOUtil {
	public LoginVO SSOLogin(HttpServletRequest request, HttpServletResponse response, String id) throws Exception {
		LoginVO loginVO = null;
		return loginVO;
	}
	
	public static Cookie getCookie(HttpServletRequest request, String name) throws Exception {
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		// 쿠키 꺼내기
		for(Cookie c : cookies) {
			if(name.equals(c.getName())) {
				cookie = c;
				break;
			}
		}
		return cookie;
		
	}
	
	public static boolean validateRefreshToken(Map token, String refresh_token) throws ParseException {
		boolean isSameToken = refresh_token.equals(token.get("TOKEN"));
		return isSameToken;
	}
}
