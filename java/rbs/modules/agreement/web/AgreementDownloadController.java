package rbs.modules.agreement.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import rbs.modules.agreement.service.AgreementService;

import com.woowonsoft.egovframework.annotation.ModuleAttr;
import com.woowonsoft.egovframework.annotation.ModuleAuth;
import com.woowonsoft.egovframework.annotation.ModuleMapping;
import com.woowonsoft.egovframework.form.DTForm;
import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.prop.RbsProperties;
import com.woowonsoft.egovframework.resource.RbsMessageSource;
import com.woowonsoft.egovframework.util.JSONObjectUtil;
import com.woowonsoft.egovframework.util.MessageUtil;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.web.ModuleDownloadController;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 샘플모듈 파일다운로드
 * @author user
 *
 */
@Controller
@ModuleMapping(moduleId="agreement")
@RequestMapping({"/{siteId}/agreement", "/{admSiteId}/menuContents/{usrSiteId}/agreement"})
public class AgreementDownloadController extends ModuleDownloadController{

	@Resource(name = "agreementService")
	private AgreementService agreementService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;

	@ModuleAuth(name={"DWN"})
	@RequestMapping(value = "/download.do", method=RequestMethod.GET)
	public ModelAndView download(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {
		int fleIdx = 0;
		int agrem_idx = Integer.parseInt(request.getParameter("agrem_idx"));
		
		// 파일명 select
		String columnId = null;
		String savedFileName = null;
		String originFileName = null;
		DataMap dt = null;
		
		// single file : columnItem
		columnId = "FILE";
		dt = agreementService.getFileView(agrem_idx, fleIdx);
		if(dt != null) {
			agreementService.updateFileDown(agrem_idx);
		} else {
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.error.data"), ""));
			return new ModelAndView(RbsProperties.getProperty("Globals.fail.path"));
		}

		savedFileName = StringUtil.getString(dt.get(columnId + "_SAVED_NAME"));
		originFileName = StringUtil.getString(dt.get(columnId + "_ORIGIN_NAME"));

		return download(attrVO, savedFileName, originFileName, model);
	}
}
