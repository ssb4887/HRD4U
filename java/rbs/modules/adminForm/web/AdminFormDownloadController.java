package rbs.modules.adminForm.web;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import rbs.modules.adminForm.service.AdminFormService;

import com.woowonsoft.egovframework.annotation.ModuleAttr;
import com.woowonsoft.egovframework.annotation.ModuleAuth;
import com.woowonsoft.egovframework.annotation.ModuleMapping;
import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.prop.RbsProperties;
import com.woowonsoft.egovframework.resource.RbsMessageSource;
import com.woowonsoft.egovframework.util.MessageUtil;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.web.ModuleDownloadController;

import egovframework.rte.fdl.property.EgovPropertyService;


@Controller
@ModuleMapping(moduleId="adminForm")
@RequestMapping({"/{siteId}/adminForm", "/{admSiteId}/menuContents/{usrSiteId}/adminForm"})
public class AdminFormDownloadController extends ModuleDownloadController{

	@Resource(name = "adminFormService")
	private AdminFormService adminFormService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;

	@ModuleAuth(name={"DWN"})
	@RequestMapping(value = "/download.do")
	public ModelAndView download(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		String admsfmIdx = request.getParameter("idx").toString();
		
		if(StringUtil.isEmpty(admsfmIdx)) {
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.error.data"), ""));
			return new ModelAndView(RbsProperties.getProperty("Globals.fail.path"));
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("admsfmIdx", admsfmIdx);
		DataMap fileInfo = adminFormService.getFileView(param);
		
		if(fileInfo == null || fileInfo.isEmpty()) {
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.error.data"), ""));
			return new ModelAndView(RbsProperties.getProperty("Globals.fail.path"));
		}
		String savedFileName = fileInfo.get("FILE_SAVED_NAME").toString();
		String originFileName = fileInfo.get("FILE_ORIGIN_NAME").toString();
		
		// 파일 위치
		String year = savedFileName.substring(0, 4);
		attrVO.setUploadModulePath(attrVO.getConfModule() + File.separator + year);
		
		return download(attrVO, savedFileName, originFileName, model);
		
	}
}
