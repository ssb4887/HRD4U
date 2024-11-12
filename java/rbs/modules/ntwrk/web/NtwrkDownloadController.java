package rbs.modules.ntwrk.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.woowonsoft.egovframework.annotation.ModuleAttr;
import com.woowonsoft.egovframework.annotation.ModuleMapping;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.prop.RbsProperties;
import com.woowonsoft.egovframework.resource.RbsMessageSource;
import com.woowonsoft.egovframework.util.MessageUtil;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.web.ModuleDownloadController;

import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.modules.ntwrk.service.NtwrkService;

@Controller
@ModuleMapping(moduleId="ntwrk")
@RequestMapping({"/{siteId}/ntwrk", "/{admSiteId}/menuContents/{usrSiteId}/ntwrk"})
public class NtwrkDownloadController extends ModuleDownloadController {

	@Value("${Globals.fileStorePath}")
	private String uploadDir;
	
	@Resource(name = "ntwrkService")
	private NtwrkService ntwrkService;
	
	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;

	/**
	 * 네트워크 첨부파일 다운로드 하기
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@Auth(role = Role.R)
	@RequestMapping(value = "/download.do")
	public ModelAndView download(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		String fleIdx = request.getParameter("fleIdx");
		if(StringUtil.isEmpty(fleIdx)) {
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.error.data"), ""));
			return new ModelAndView(RbsProperties.getProperty("Globals.fail.path"));
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fleIdx", fleIdx);
		Map<String, Object> fileInfo = ntwrkService.getNtwrkFileOne(param);
		
		if(fileInfo == null || fileInfo.isEmpty()) {
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.error.data"), ""));
			return new ModelAndView(RbsProperties.getProperty("Globals.fail.path"));
		}
		String savedFileName = fileInfo.get("FILE_SAVED_NAME").toString();
		String originFileName = fileInfo.get("FILE_ORIGIN_NAME").toString();
		
		return download(attrVO, savedFileName, originFileName, model);
	}
}
