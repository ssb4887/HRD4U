package rbs.egovframework.social.kakao.api.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;

import org.springframework.stereotype.Service;



import framework.exception.InfraException;
import framework.exception.ServiceException;
import framework.util.ResultList;
import rbs.egovframework.social.kakao.api.KakaoSenderService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("kakaoSenderService")
public class KakaoSenderServiceImpl implements KakaoSenderService{

	//카카오톡 보내기
	@Override
	public void sendKaKaoTalk(Map<String, Object> map) throws Exception {

		String GET_URL = "https://apimsg.wideshot.co.kr/api/v1/message/alimtalk";
		//String GET_URL = "https://apimsg-dev.wideshot.co.kr/api/v1/message/alimtalk";
		
		
		URL url = new URL(GET_URL);
			
		String apiKey = "SjQwaGQ2NHJSRnpiNmRiL2VjSjcxOU4weVNPMXlqclNjVnRNUnh3SklnRFRROGNQRzdSTEFKL2VMTE45RGZSQQ==";
			
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			
		System.setProperty("https.protocols", "TLSv1.2");
		
		con.setRequestMethod("POST"); 
		// HTTP POST 메소드 설정 
		con.setRequestProperty("content-type", "application/x-www-form-urlencoded");
		con.setRequestProperty("sejongApiKey", apiKey);
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setConnectTimeout(5000);
		
	
		String param ="";
		param +="plusFriendId="+map.get("plusFriendId");
		param +="&senderKey=1b4a9798707859fef2745aada6919fd84ee1b8a9";
		param +="&templateCode="+map.get("templateCode");
		param +="&title="+map.get("title");
		param +="&contents="+map.get("contents");
		param +="&receiverTelNo="+map.get("recevier");
		param +="&userKey="+map.get("userCode");
		

		System.out.println(param);
		
		//DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		
		OutputStream os = con.getOutputStream();		
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(os, "UTF-8")); // 캐릭터셋 설정
		
		wr.write(param);
				
		wr.flush(); 
		wr.close();
		os.close();
		
		int responseCode = con.getResponseCode(); 
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String inputLine; 
		
		StringBuffer response2 = new StringBuffer(); 

		while ((inputLine = in.readLine()) != null) { 
			response2.append(inputLine); 
		}
		
		System.out.println("HTTP 응답 코드 : " + responseCode); 
		System.out.println("HTTP body : " + response2.toString());
		
		in.close(); 
		
		con.disconnect();
	}	
	
	// 카카오톡 결과값
	@Override
	public void checkKaKaoTalk(Map<String, Object> map) throws Exception {

		String GET_URL = "https://apimsg.wideshot.co.kr/api/v1/message/results";
		String param ="";
		param +="sendCode="+map.get("userCode");
		
		//GET_URL+=param;
		URL url = new URL(GET_URL);
			
		String apiKey = "SjQwaGQ2NHJSRnpiNmRiL2VjSjcxOU4weVNPMXlqclNjVnRNUnh3SklnRFRROGNQRzdSTEFKL2VMTE45RGZSQQ==";
			
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			
		//System.setProperty("https.protocols", "TLSv1.2");
		
		con.setRequestMethod("GET"); 
		// HTTP POST 메소드 설정 
		//con.setRequestProperty("content-type", "application/x-www-form-urlencoded");
		con.setRequestProperty("sejongApiKey", apiKey);
		//con.setDoInput(true);
		con.setDoOutput(true);
		con.setConnectTimeout(5000);
		
	
		
		
		
		System.out.println(param);
		
		//DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		
		/*OutputStream os = con.getOutputStream();		
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(os, "UTF-8")); // 캐릭터셋 설정
		
		wr.write(param);
				
		wr.flush(); 
		wr.close();
		os.close();*/
		
		int responseCode = con.getResponseCode(); 
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String inputLine; 
		
		StringBuffer response2 = new StringBuffer(); 

		while ((inputLine = in.readLine()) != null) { 
			response2.append(inputLine); 
		}
		
		System.out.println("HTTP 응답 코드 : " + responseCode); 
		System.out.println("HTTP body : " + response2.toString());
		
		in.close(); 
		
		con.disconnect();
	}	
	
}
