package rbs.egovframework.social.kakao.api;

import java.util.List;
import java.util.Map;


public interface KakaoSenderService {

	void sendKaKaoTalk(Map<String, Object> map) throws Exception;
	
	void checkKaKaoTalk(Map<String, Object> map) throws Exception;
}
