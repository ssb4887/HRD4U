package rbs.egovframework.util;

import java.util.Date;

import javax.servlet.http.Cookie;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	private static byte[] ACCESS_KEY = "SeCrEtKeY1!#ACCESS".getBytes();
	private static byte[] REFRESH_KEY = "SeCrEtKeY2@#REFRESH".getBytes();
	private static long ACCESS_EXPIRATION = 1000L * 60 * 10;   // 600,000 ms = 600 s = 10 min
	private static long REFRESH_EXPIRATION = 1000L * 60 * 60; // 3,600,000 ms = 3,600 s = 60 min = 1 hr
	
	public static String createAccessToken(String id) {
		Claims claims = Jwts.claims().setId("access-token");
		claims.put("user-id", id);
		return createToken(claims, ACCESS_EXPIRATION, ACCESS_KEY);
	}
	
	public static String createRefreshToken(String id) {
		Claims claims = Jwts.claims().setId("refresh-token");
		claims.put("user-id", id);
		return createToken(claims, REFRESH_EXPIRATION, REFRESH_KEY);
	}
	
	public static String createToken(Claims claims, long expiration, byte[] secret) {
		Date now = new Date();
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + expiration))
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	public static Jws<Claims> parseAccessJWT(String token) {
		return parseJWT(token, ACCESS_KEY);
	}
	
	public static Jws<Claims> parseRefreshJWT(String token) {
		return parseJWT(token, REFRESH_KEY);
	}
	
	public static Jws<Claims> parseJWT(String token, byte[] key) {
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
	}
	
	public static boolean validateToken(Jws<Claims> claims) {
		return !claims.getBody()
				.getExpiration()
				.before(new Date());
	}
	
	public static String getId(Jws<Claims> claims) {
		return (String) claims.getBody()
				.get("user-id");
	}
	
	public static Object getClaims(Jws<Claims> claims, String key) {
		return claims.getBody()
				.get(key);
	}
	
	public static Cookie createCookie(String name, String token, long aCCESS_EXPIRATION2) {
		Cookie result = new Cookie(name, token);
		result.setHttpOnly(true);
		result.setPath("/");
		result.setMaxAge((int)(aCCESS_EXPIRATION2/1000));
		return result;
	}
	
	public static Cookie createAccessCookie(String id) {
		return createCookie("access-token", createAccessToken(id), ACCESS_EXPIRATION);
	}
	public static Cookie createRefreshCookie(String id) {
		return createCookie("refresh-token", createRefreshToken(id), REFRESH_EXPIRATION);
	}
}
