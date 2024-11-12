package rbs.modules.clinic.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
import net.sf.json.JSONObject;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.modules.clinic.service.ClinicService;

/**
 * 샘플모듈 파일다운로드
 * @author user
 *
 */
@Controller
@ModuleMapping(moduleId="clinic")
@RequestMapping({"/{siteId}/clinic", "/{admSiteId}/menuContents/{usrSiteId}/clinic"})
public class ClinicDownloadController extends ModuleDownloadController{

	@Resource(name = "clinicService")
	private ClinicService clinicService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;

	@Auth(role = Role.R)
	@RequestMapping(value = "/download.do")
	public ModelAndView download(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) throws Exception {

		String targetTable = "";		
		String targetTableIdx = "";
		switch (attrVO.getFnIdx()){
			case(1): 
				targetTable = "HRD_CLI_REQ_FILE";
				targetTableIdx = "REQ_IDX";
				break;
			case(3): 
				targetTable = "HRD_CLI_PLAN_FILE";
				targetTableIdx = "PLAN_IDX";
				break;
			case(4): 
				targetTable = "HRD_CLI_ACMSLT_FILE";
				targetTableIdx = "ACMSLT_IDX";
				break;
			case(5): 
				targetTable = "HRD_CLI_RSLT_FILE";
				targetTableIdx = "RSLT_IDX";
				break;	
			case(6): 
				targetTable = "HRD_CLI_SPT_FILE";
				targetTableIdx = "SPT_IDX";
				break;						
			
		}
		
		String ajaxPName = attrVO.getAjaxPName();
		JSONObject settingInfo = attrVO.getSettingInfo();
		
		int keyIdx = StringUtil.getInt(request.getParameter(JSONObjectUtil.getString(settingInfo, "idx_name")));
		int fidx = StringUtil.getInt(request.getParameter("fidx"), 0);
		String itemId = request.getParameter("itId");
		if(fidx > 0 && StringUtil.isEmpty(itemId)) itemId = "file";
		
		if(StringUtil.isEmpty(itemId) || keyIdx <= 0) {
			return new ModelAndView(RbsProperties.getProperty("Globals.error.400" + ajaxPName + ".path"));
		}
		
		// 파일명 select
		String columnId = null;
		String savedFileName = null;
		String originFileName = null;
		DataMap dt = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		searchList.add(new DTForm("A." + JSONObjectUtil.getString(settingInfo, "idx_column"), keyIdx));
		if(fidx > 0) {
			// multi file
			columnId = "FILE";
			searchList.add(new DTForm("A.FLE_IDX", fidx));
			searchList.add(new DTForm("A.ITEM_ID", itemId));
//			searchList.add(new DTForm(targetTableIdx, keyIdx));
			param.put("searchList", searchList);
			param.put("targetTableIdx", targetTableIdx);
			param.put("KEY_IDX", keyIdx);
			dt = clinicService.getMultiFileView(targetTable, param);
			if(dt != null) {
				clinicService.updateMultiFileDown(targetTable, keyIdx, fidx, itemId, targetTableIdx);
			}
		} else {
//			// single file : columnItem
//			JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
//			JSONObject item = JSONObjectUtil.getJSONObject(items, itemId);
//			columnId = JSONObjectUtil.getString(item, "column_id");
//			param.put("columnItem", columnId);
//			param.put("searchList", searchList);
//			param.put("targetTableIdx", targetTableIdx);
//			param.put("KEY_IDX", keyIdx);
//			dt = clinicService.getFileView(targetTable, param);
//			if(dt != null) {
//				int result = clinicService.updateFileDown(targetTable, keyIdx, columnId);
//			}
		}
		
		if(dt == null) {
			model.addAttribute("message", MessageUtil.getAlert(rbsMessageSource.getMessage("message.error.data"), ""));
			return new ModelAndView(RbsProperties.getProperty("Globals.fail.path"));
		}

		savedFileName = StringUtil.getString(dt.get(columnId + "_SAVED_NAME"));
		originFileName = StringUtil.getString(dt.get(columnId + "_ORIGIN_NAME"));

		return download(attrVO, savedFileName, originFileName, model);
	}
}
