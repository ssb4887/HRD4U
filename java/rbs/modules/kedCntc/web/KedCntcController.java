package rbs.modules.kedCntc.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.woowonsoft.egovframework.annotation.ModuleMapping;
import com.woowonsoft.egovframework.resource.RbsMessageSource;
import com.woowonsoft.egovframework.web.ModuleController;
import egovframework.rte.fdl.property.EgovPropertyService;
import net.sf.json.JSONObject;
import rbs.modules.kedCntc.service.KedCntcService;



@Controller
@ResponseBody
@ModuleMapping(moduleId="kedCntc")
@RequestMapping(value="/web/kedCntc")
public class KedCntcController extends ModuleController {
	@Resource(name = "KedCntcService")
	private KedCntcService kedCntcService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	

	@RequestMapping(value="/serverInfo.do" , method= RequestMethod.GET)
	public String getSeverInfo(@RequestParam("idx") int idx, HttpServletRequest request, ModelMap model) {
		JSONObject js = new JSONObject();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("SERVER_IDX", idx);
		List<Object> list = kedCntcService.getServerInfo(param);
		
		
		model.addAttribute("list", list);		
		@SuppressWarnings("rawtypes")
		HashMap hm = (HashMap) list.get(0);
		js.put("SERVER_IP", hm.get("SERVER_IP").toString());
		js.put("SERVER_PORT", hm.get("SERVER_PORT").toString());
		js.put("USER_ID", hm.get("USER_ID").toString());
		js.put("PASSWORD", hm.get("PASSWORD").toString());
		

		return js.toString();
		
	}
	
	
	@RequestMapping(value="/procInfo.do" , method= RequestMethod.GET)
	public String getProcInfo(HttpServletRequest request, ModelMap model) {
		
		String strMsg = kedCntcService.getProcInfo();
		
		return strMsg;
		
	}
	
	

	@RequestMapping(value="/fileUpload.do" , method= RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
	public String fileupLoad(@RequestParam("key") String key, MultipartHttpServletRequest request, ModelMap model) throws IOException {
		String strMsg = "OK";
		
		if (key.equals("VL0mPG58AkGOllZ0aOri0A==")) {
			
			Iterator<String> i = request.getFileNames();
			while(i.hasNext()) {
				String fileName = i.next();
					MultipartFile file = request.getFile(fileName);		
					strMsg = kedCntcService.uploadFile(file);
					try {
						file.getInputStream().close();
					} catch (IOException e) {
						strMsg = e.getMessage();
					}
			}
		} else {
			return "key Error!";
		}
 
		return strMsg ;
	}
	
	@RequestMapping(value="/onlyFileUpload.do" , method= RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
	public String onlyFileupLoad(@RequestParam("key") String key, MultipartHttpServletRequest request, ModelMap model) throws IOException {
		String strMsg = "OK";
		request.setCharacterEncoding("UTF-8");

		if (key.equals("VL0mPG58AkGOllZ0aOri0A==")) {
			
			Iterator<String> i = request.getFileNames();
			while(i.hasNext()) {
				String fileName = i.next();
					MultipartFile file = request.getFile(fileName);		
				    kedCntcService.onlyUploadFile(file);
				    try {
						file.getInputStream().close();
					} catch (IOException e) {
						strMsg = e.getMessage();
					}
			}
		} else {
			strMsg = "key Error!";
		}
 
		return strMsg ;
	}
	
	
	@RequestMapping(value="/onlyDBinsert.do" , method= RequestMethod.GET)
	public String onlyDBinsert(@RequestParam("idx") String dir, HttpServletRequest request, ModelMap model) {
		String strMsg = "OK";
		strMsg = kedCntcService.onlyDBinsert(dir);
	
		return "OK";
	}
	

}
