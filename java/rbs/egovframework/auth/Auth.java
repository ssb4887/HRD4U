package rbs.egovframework.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 통합관리시스템에서 선택한 사용자 사이트 정보
 * @author user
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auth{
	Role role() default Role.M;
	
	enum Role{
		M,
		C,
		R,
		U,
		D,
		F
	}
}
