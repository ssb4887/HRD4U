package rbs.adm.dashboard.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woowonsoft.egovframework.annotation.ModuleAttr;
import com.woowonsoft.egovframework.annotation.ParamMap;
import com.woowonsoft.egovframework.form.DTForm;
import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.form.ParamForm;
import com.woowonsoft.egovframework.prop.RbsProperties;
import com.woowonsoft.egovframework.resource.RbsMessageSource;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;
import com.woowonsoft.egovframework.util.WebsiteDetailsHelper;
import com.woowonsoft.egovframework.web.NonModuleController;

import rbs.adm.dashboard.service.DashboardService;
import rbs.egovframework.LoginVO;
import rbs.egovframework.WebsiteVO;

@Controller
public class DashboardController extends NonModuleController{
	
	public String getViewModulePath() {
		return "/dashboard";
	}
	
	@Resource(name = "admDashboardService")
	private DashboardService dashboardService;
	
	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

	@RequestMapping("/{admSiteId}/dashboard/dashboard.do")
	public String admDashBoard(HttpServletRequest request, ModelMap model, @ParamMap ParamForm parameterMap, @ModuleAttr ModuleAttrVO attrVO) {

		String memberIdx = null;
		int memberUsertypeIdx = 0;
		int superUsertypeIdx = RbsProperties.getPropertyInt("Globals.code.USERTYPE_HADMIN");		// user관리자 등급
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		if(loginVO != null) {
			memberIdx = loginVO.getMemberIdx();
			memberUsertypeIdx = loginVO.getUsertypeIdx();
		}
		
		String siteMode = attrVO.getSiteMode();
		List<Object> websiteList = null;
		DataMap websiteDt = null;
		List<Object> contentsList = null;
		List<Object> statsList = null;
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		
		int pageUnit = 5;
		// 사이트
		//int memberUsertypeIdx = loginVO.getUsertypeIdx();
		if(!StringUtil.isEmpty(memberIdx)) {
			if(memberUsertypeIdx < superUsertypeIdx) {
				// 사이트 관리 권한 있는 목록만 select
				searchList.add(new DTForm("MANAGER_MEMBER_IDXS", "NULL", 1, "IS", "AND", "(", ""));
				searchList.add(new DTForm(new String[]{"','", "MANAGER_MEMBER_IDXS", "','"}, "%," + memberIdx + ",%", "LIKE", "OR", "", ")"));
			}
			param.put("searchList", searchList);
			param.put("firstIndex", 0);
			param.put("lastIndex", pageUnit);
    		param.put("recordCountPerPage", pageUnit);
	
			websiteList = dashboardService.getWebsiteList(siteMode, param); 
		}
		WebsiteVO usrSiteVO = (WebsiteVO) WebsiteDetailsHelper.getWebsiteInfo();
		if(usrSiteVO != null) {
			String siteId = usrSiteVO.getSiteId();
			String lang = usrSiteVO.getLocaleLang();
			
			// 사이트 정보
			param = new HashMap<String, Object>();
			param.put("siteId", siteId);
			websiteDt = dashboardService.getWebsiteView(siteMode, param);
			
			// 콘텐츠
			param = new HashMap<String, Object>();
			searchList = new ArrayList<DTForm>();
			searchList.add(new DTForm("MENU.SITE_ID", siteId));
			param.put("searchList", searchList);
			param.put("firstIndex", 0);
			param.put("lastIndex", pageUnit);
			param.put("recordCountPerPage", pageUnit);
			contentsList = dashboardService.getContentsList(siteMode, lang, param); 
			
			// 현재접속자
			param = new HashMap<String, Object>();
			searchList = new ArrayList<DTForm>();
			param.put("searchList", searchList);
			param.put("firstIndex", 0);
			param.put("lastIndex", pageUnit);
			param.put("recordCountPerPage", pageUnit);
			param.put("siteId", StringUtil.toUpperCase(siteId));
			statsList = dashboardService.getStatsList(siteMode, param); 
		}
		
		model.addAttribute("websiteList", websiteList);
		model.addAttribute("websiteDt", websiteDt);
		model.addAttribute("contentsList", contentsList);
		model.addAttribute("statsList", statsList);
		
		return getViewPath("/dashboard");
	}
}
