package rbs.modules.simpleSign.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.woowonsoft.egovframework.annotation.ModuleMapping;
import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.resource.RbsMessageSource;
import com.woowonsoft.egovframework.web.ModuleController;

import com.clipsoft.org.json.simple.JSONObject;

import egovframework.rte.fdl.property.EgovPropertyService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.modules.busisSelect.service.BusisSelectService;
import rbs.modules.simpleSign.serivce.SignatureDocumentService;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


@Controller
@ModuleMapping(moduleId="simpleSign")
@RequestMapping({"/{siteId}/simpleSign", "/{admSiteId}/menuContents/{usrSiteId}/simpleSign"})
public class SignatureDocumentController extends ModuleController{
	
	@Resource(name = "signatureService")
	private SignatureDocumentService signatureService;

	@Resource(name = "busisSelectService")
	private BusisSelectService busisSelectService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
	@Resource(name = "jsonView")
	View jsonView;
	
	private OkHttpClient client = new OkHttpClient();
	
    static final String SIMPLESIGN_API_USER_ID = "admin";
    static final String SIMPLESIGN_API_USER_PASSWORD = "admin12#$";
    
    /**
     *** 참고
     * 서식문서 = 방문기업서식 문서
     * 서명문서 = 방문기업관리 문서
     * 
     * 서식ID = formatId
     * 서명ID = signatureId
     * 서명자ID = signerId
     */
    
    /**
     * 방문기업서식
     */
    
    // 서식문서 상세조회
	@Auth(role = Role.R)
    @RequestMapping("/edit.do")
    public ModelAndView formEdit(@RequestBody String formatId) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	
    	// 01. 토큰발급
    	String token = signatureService.getToken(SIMPLESIGN_API_USER_ID, SIMPLESIGN_API_USER_PASSWORD);
    	
    	// 02. 대량 서식문서 목록 조회 응답
    	Map<String, Object> url = signatureService.getTempList(formatId, token);
    	
    	String editUrl = url.get("editUrl").toString();
		
    	mav.addObject("token", token);
    	mav.addObject("editUrl", editUrl);
		mav.setView(jsonView);
    	
    	return mav;
    }

	// 서식문서 편집
	@Auth(role = Role.R)
	@RequestMapping("/view.do")
	public ModelAndView formList(@RequestBody String formatId) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		// 01. 토큰발급
		String token = signatureService.getToken(SIMPLESIGN_API_USER_ID, SIMPLESIGN_API_USER_PASSWORD);
		
		// 02. 대량 서식문서 목록 조회 응답
		Map<String, Object> url = signatureService.getTempList(formatId, token);
		
		String viewUrl = url.get("viewUrl").toString();
		
		mav.addObject("token", token);
		mav.addObject("viewUrl", viewUrl);
		mav.setView(jsonView);
		
		return mav;
	}
	
	// 대량 발행 방문기업서식 등록
	@Auth(role = Role.C)
	@RequestMapping(method=RequestMethod.POST, value="/input.do")
	public ModelAndView formInsert(@RequestParam final String docKey, final MultipartFile file) throws Exception {
		ModelAndView mav = new ModelAndView();

		// 01. 토큰발급
		String token = signatureService.getToken(SIMPLESIGN_API_USER_ID, SIMPLESIGN_API_USER_PASSWORD);
		
		System.out.println("file :" +file);
		
        // 02. 등록
		if(file == null) {
//			busis
		}
		JSONObject contents = signatureService.insertTemp(docKey, file, token);
		
        String formId = contents.get("id").toString();
        String editUrl = contents.get("editUrl").toString();
        
        mav.addObject("formId", formId);
        mav.addObject("editUrl", editUrl);
        mav.addObject("token", token);
        mav.setView(jsonView);
		
		return mav;
	}
	
	/**
	 * 방문기업관리
	 */
	
	// 서명문서 발행
	@Auth(role = Role.C)
	@RequestMapping(method=RequestMethod.POST, value="/publish.do")
    public ModelAndView SignatureDocumentSet(@RequestParam Map<String, Object> param) throws Exception{
		ModelAndView mav = new ModelAndView();
		
    	// 01. 토큰발급
        String token = signatureService.getToken(SIMPLESIGN_API_USER_ID, SIMPLESIGN_API_USER_PASSWORD);

        // 02. 대량 발행
        List<String> reqSetKeyList = signatureService.publishSignature(param, token);
        
        String publishedYn = (reqSetKeyList != null) ? "Y" : "N";
        
        mav.setView(jsonView);
        mav.addObject("reqSetKeyList", reqSetKeyList);
        mav.addObject("publishedYn", publishedYn);
        
        // 03. 발행정보 저장
        
        return mav;
    }
	
	// 서명문서 작성
	@Auth(role = Role.R)
    @RequestMapping(method=RequestMethod.POST, value="/apply.do")
	public ModelAndView SignatureDocumentList(@RequestBody String reqSetKeyValue) throws Exception{
		
		final String SIMPLESIGN_SIGN_APPLY_URL = "http://165.213.109.69:18080/ssign/viewer/s/";
		
		String cleanedData = reqSetKeyValue.substring(1, reqSetKeyValue.length()-1);
		String[] reqSetKeyArray = cleanedData.split(",");
		
		List<String> reqSetKeyList = new ArrayList<>();
		for(String reqSetKey : reqSetKeyArray) {
			reqSetKeyList.add(reqSetKey.trim());
		}
		
		ModelAndView mav = new ModelAndView();
		
		List<Map<String, Object>> contentsList = new ArrayList<>();
		List<String> applyUrlList = new ArrayList<>();
		List<String> signerIdList = new ArrayList<>();
		
		// 01. 토큰발급
        String token = signatureService.getToken(SIMPLESIGN_API_USER_ID, SIMPLESIGN_API_USER_PASSWORD);
		
        // 02. 대량 서명문서 조회
        contentsList = signatureService.getSignatureList(reqSetKeyList, token);
        
        for (int i = 0; i < contentsList.size(); i++) {
			String signatureId = contentsList.get(i).get("id").toString();
			String progressStatus = contentsList.get(i).get("progressStatus").toString();

			signerIdList = signatureService.getSignerId(signatureId);
			
			if(progressStatus.equals("PROGRESS")) {
				for(String signerId : signerIdList) {
					String applyUrl = SIMPLESIGN_SIGN_APPLY_URL + signatureId + "/"+ signerId;
					
					System.out.println("applyUrl : "+applyUrl);
					
					applyUrlList.add(applyUrl);
					mav.addObject("applyUrlList", applyUrlList);
					mav.addObject("token", token);
					mav.addObject("progressStatus", "inProgress");
					
					
				}
			}else {
				mav.addObject("progressStatus", "done");
			}
		}
        
		mav.setView(jsonView);
		
		return mav;
	}
	
	// 서명문서 상세조회
	@Auth(role = Role.R)
    @RequestMapping(method=RequestMethod.POST, value="/signView.do")
	public ModelAndView SignatureDocumentView(@RequestBody String sptdgnsIdx) throws Exception{
		
		ModelAndView mav = new ModelAndView();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sptdgnsIdx", sptdgnsIdx);
		
		List<Map<String, Object>> contentsList = new ArrayList<>();
		List<String> viewUrlList = new ArrayList<>();
		
		DataMap publishList = busisSelectService.getPublished(param);
		String getPblicteId = publishList.get("ELCTRN_FORMAT_PBLICTE_ID").toString();
		List<String> reqSetKeyList = Arrays.asList(getPblicteId.split(","));
		
		// 01. 토큰발급
        String token = signatureService.getToken(SIMPLESIGN_API_USER_ID, SIMPLESIGN_API_USER_PASSWORD);
		
        // 02. 대량 서명문서 조회
        contentsList = signatureService.getSignatureList(reqSetKeyList, token);
        
        for (int i = 0; i < contentsList.size(); i++) {
			String progressStatus = contentsList.get(i).get("progressStatus").toString();
			
			if(progressStatus.equals("COMPLETE")) {
				
				String viewUrl = contentsList.get(i).get("viewUrl").toString();
				viewUrlList.add(viewUrl);
				
				mav.addObject("viewUrlList", viewUrlList);
				
			}
		}
        
		mav.setView(jsonView);
		
		return mav;
	}
	
	// 서명문서 PDF 출력
	@Auth(role = Role.R)
    @RequestMapping(method=RequestMethod.POST, value="/pdfDownload.do")
	public void SignatureDocumentInsert(@RequestBody String sptdgnsIdx, HttpServletResponse response) throws Exception{
		
		final String SIMPLESIGN_PDF_URL = "http://165.213.109.69:18080/ssign/v1/signatures/";
		
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sptdgnsIdx", sptdgnsIdx);
		
		List<Map<String, Object>> contentsList = new ArrayList<>();
		DataMap publishList = busisSelectService.getPublished(param);
		String getPblicteId = publishList.get("ELCTRN_FORMAT_PBLICTE_ID").toString();
		List<String> reqSetKeyList = Arrays.asList(getPblicteId.split(","));
		
		// 01. 토큰발급
        String token = signatureService.getToken(SIMPLESIGN_API_USER_ID, SIMPLESIGN_API_USER_PASSWORD);
		
        // 02. 대량 서명문서 조회
        contentsList = signatureService.getSignatureList(reqSetKeyList, token);

        List<String> fileUrls = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        String title = null;
        for (int i = 0; i < contentsList.size(); i++) {
			String id = contentsList.get(i).get("id").toString();
			title = contentsList.get(i).get("title").toString();
			String url = SIMPLESIGN_PDF_URL + id + "/file";
			fileUrls.add(url);
			titles.add(title);
			
			System.out.println("fileUrls : "+fileUrls);
			System.out.println("titles : "+titles);
			
		}
        
        Map<String, Object> sptjdgnsInfo = busisSelectService.getSptjdgns(param);
        String sptjNm = sptjdgnsInfo.get("SPTJ_NAME").toString();
        String bplNm = sptjdgnsInfo.get("BPL_NM").toString();
        
        // zip만들기
        String zipFileName = bplNm +"_"+ sptjNm + ".zip";
        
        System.out.println("zipFileName : "+zipFileName);
        response.setContentType("application/zip");
        response.setHeader("Content-Disponsition", "attachment;filename="+URLEncoder.encode(zipFileName, "UTF-8"));
        
        try(ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
        	for (int i=0; i < fileUrls.size(); i++) {
        		String url = fileUrls.get(i).toString();
        		title = titles.get(i).toString();
        		
        		System.out.println("title : "+title);
        		Request request = new Request.Builder().url(url).build();
        		Response okHttpResponse = client.newCall(request).execute();
        		try {
        			if(!okHttpResponse.isSuccessful())
        				throw new IOException("Failed to download file : "+okHttpResponse);
        			
    				// zip에 파일 추가
    				ZipEntry zipEntry = new ZipEntry(title+".pdf");
    				zos.putNextEntry(zipEntry);
    				zos.write(okHttpResponse.body().bytes());
    				zos.closeEntry();
        		} finally {
        			okHttpResponse.close();
        		}
        	}
        	zos.finish();
        }
        
        
//		mav.setView(jsonView);
//		
//		return mav;
	}
	
}