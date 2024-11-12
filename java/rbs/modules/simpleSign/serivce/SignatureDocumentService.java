package rbs.modules.simpleSign.serivce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.clipsoft.org.json.simple.JSONObject;



public interface SignatureDocumentService {

	// 토큰발급
	 public String getToken(String id, String password) throws Exception;
	 
	 // 대량 서식문서 상세조회
	 public Map<String,Object> getTempList(String formatId, String token) throws Exception;

	 // 대량 서식문서 등록
	 public JSONObject insertTemp(String docKey, MultipartFile file, String token) throws Exception;
	 
	// 대량 서명문서 발행
	 public List<String> publishSignature(Map<String, Object> param, String token) throws Exception;
	 
	 // 대량 서명문서 목록 조회
	 public List<Map<String, Object>> getSignatureList(List<String> reqSetKeyList, String token) throws Exception;
	 
    // 서명자ID 조회
	 public List<String> getSignerId(String signatureId) throws Exception;
	
	 // 내용 파싱
	 public JSONObject getContent(String response, String name) throws Exception;
	 
	 // 필드출력
	 public void printFields(JSONObject parent, String key);
}
