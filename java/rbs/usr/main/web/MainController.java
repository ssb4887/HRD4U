package rbs.usr.main.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woowonsoft.egovframework.annotation.MenuMapping;
import com.woowonsoft.egovframework.annotation.ModuleAttr;
import com.woowonsoft.egovframework.form.DataForm;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.prop.RbsProperties;
import com.woowonsoft.egovframework.resource.RbsMessageSource;
import com.woowonsoft.egovframework.util.JSONObjectUtil;
import com.woowonsoft.egovframework.util.MenuUtil;
import com.woowonsoft.egovframework.util.ModuleUtil;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;
import com.woowonsoft.egovframework.web.NonModuleController;

import rbs.egovframework.LoginVO;
import rbs.modules.consulting.web.ConsultingController;
import rbs.usr.main.service.MainService;
 
@Controller
@MenuMapping(uriPattern={"/{siteId}/main/"})
@RequestMapping("/{siteId}/main")
public class MainController extends NonModuleController{
	
	public String getViewModulePath() {
		return "/main";
	}

	@Resource(name = "usrMainService")
	private MainService mainService;
	
	/** EgovMessageSource */
	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;
	
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(MainController.class);
	
	@RequestMapping("/main.do")
	public String main(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) {
		//TODO
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		log.info("로그인한 유저 {}", loginVO);
		
		
		return getViewPath("/main");
	}
	
	@RequestMapping("/waitopen.do")
	public String wait(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) {
		
		return getViewPath("/waitopen");
	}
		
	@RequestMapping("/search.do")
	public String search(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model) {
		//System.out.println("-MENU_AUTH:" + request.getAttribute("MENU_AUTH"));
		DataForm queryString = attrVO.getQueryString();
		
		JSONArray siteMenuJsonArray = JSONObjectUtil.getJSONArray(JSONObjectUtil.getJSONObject(JSONObjectUtil.getJSONArray(request.getAttribute("siteMenuList")), 0), "menu-list");		// 전체 메뉴
		JSONObject siteMenus = JSONObjectUtil.getJSONObject(request.getAttribute("siteMenus"));																							// 메뉴정보
		JSONObject boardTotalSetting = ModuleUtil.getModuleTotalSettingObject(RbsProperties.getProperty("Globals.design.NAME_MODULE_ID_BOARD"));
		
		JSONArray boardMenuJsonArray = fn_getBoardMenuJsonArray(null, siteMenuJsonArray, siteMenus, boardTotalSetting);

		Map<Object, List<Object>> boardListHashMap = mainService.getBoardListHashMap(queryString, 5, boardMenuJsonArray, boardTotalSetting);
		
		model.addAttribute("boardMenuJsonArray", boardMenuJsonArray);
		model.addAttribute("boardListHashMap", boardListHashMap);
		
		JSONArray contentsMenuJsonArray = fn_getContentsMenuJsonArray(null, siteMenuJsonArray, siteMenus);

		Map<Object, List<Object>> contentsListHashMap = mainService.getContentsListHashMap(queryString, contentsMenuJsonArray);
		
		model.addAttribute("contentsMenuJsonArray", contentsMenuJsonArray);
		model.addAttribute("contentsListHashMap", contentsListHashMap);
		
		return getViewPath("/search");
	}
	
	public static JSONArray fn_getBoardMenuJsonArray(String menuName, JSONArray menuArray, JSONObject menus, JSONObject boardTotalSetting){
		if(JSONObjectUtil.isEmpty(menuArray)) return null;

		//JSONArray authManagerArray = ModuleUtil.getModuleAuthManagerArray("board");								// 모듈 권한 항목정보
		JSONArray boardMenuJsonArray = null;
		
		int menuLen = menuArray.size();
		for(int i = 0; i < menuLen; i++ ){
			JSONObject menu = JSONObjectUtil.getJSONObject(menus, "menu" + JSONObjectUtil.getInt(JSONObjectUtil.getJSONObject(menuArray, i), "menu_idx"));
			if(JSONObjectUtil.isEmpty(menu)) continue;

			StringBuffer totalMenuName = new StringBuffer();
			
			boolean isHidden = JSONObjectUtil.isEquals(menu, "ishidden", "1");
			if(isHidden) continue;
			
			if(!StringUtil.isEmpty(menuName)) totalMenuName.append(menuName + " > ");
			totalMenuName.append(JSONObjectUtil.getString(menu, "menu_name"));
			
			menu.put("total_menu_name", totalMenuName.toString());
			
			JSONObject boardTotalSettingItem = JSONObjectUtil.getJSONObject(boardTotalSetting, "item" + JSONObjectUtil.getInt(menu, "fn_idx"));
			menu.put("idx_name", JSONObjectUtil.getString(boardTotalSettingItem, "idx_name"));

			boolean isBoard = JSONObjectUtil.isEquals(menu, "module_id", RbsProperties.getProperty("Globals.design.NAME_MODULE_ID_BOARD"));
			boolean isMenuAuth = MenuUtil.isAuth(menu);
			//boolean isListAuth = AuthHelper.isModuleSearchAuthenticated(menu, authManagerArray, "LST");
			// 통합검색 사용여부
			boolean useTotSearch = JSONObjectUtil.isEquals(menu, "use_totsearch", "1");
			
			if(isBoard && isMenuAuth && useTotSearch/* && isListAuth*/){
				if(JSONObjectUtil.isEmpty(boardMenuJsonArray)) boardMenuJsonArray = new JSONArray();
				boardMenuJsonArray.add(menu);
			}
			
			boolean isChildHidden = JSONObjectUtil.isEquals(menu, "child_hidden", "1");
			if(isChildHidden) continue;
			
			JSONArray childBoardMenuJsonArray = fn_getBoardMenuJsonArray(totalMenuName.toString(), JSONObjectUtil.getJSONArray(JSONObjectUtil.getJSONObject(menuArray, i), "menu-list"), menus, boardTotalSetting);
			
			if(!JSONObjectUtil.isEmpty(childBoardMenuJsonArray)){
				if(JSONObjectUtil.isEmpty(boardMenuJsonArray)) boardMenuJsonArray = new JSONArray();
				boardMenuJsonArray.addAll(childBoardMenuJsonArray);
			}
		}
		return boardMenuJsonArray;
	}
	
	public static JSONArray fn_getContentsMenuJsonArray(String menuName, JSONArray menuArray, JSONObject menus){
		if(JSONObjectUtil.isEmpty(menuArray)) return null;

		JSONArray contentsMenuJsonArray = null;
		
		int menuLen = menuArray.size();
		for(int i = 0; i < menuLen; i++ ){
			JSONObject menu = JSONObjectUtil.getJSONObject(menus, "menu" + JSONObjectUtil.getInt(JSONObjectUtil.getJSONObject(menuArray, i), "menu_idx"));
			if(JSONObjectUtil.isEmpty(menu)) continue;

			StringBuffer totalMenuName = new StringBuffer();
			
			boolean isHidden = JSONObjectUtil.isEquals(menu, "ishidden", "1");
			if(isHidden) continue;
			
			if(!StringUtil.isEmpty(menuName)) totalMenuName.append(menuName + " > ");
			totalMenuName.append(JSONObjectUtil.getString(menu, "menu_name"));
			
			menu.put("total_menu_name", totalMenuName.toString());
			
			boolean isContents = JSONObjectUtil.isEquals(menu, "module_id", RbsProperties.getProperty("Globals.design.NAME_MODULE_ID_CONTENTS"));
			boolean isMenuAuth = MenuUtil.isAuth(menu);
			// 통합검색 사용여부
			boolean useTotSearch = JSONObjectUtil.isEquals(menu, "use_totsearch", "1");
			
			if(isContents && isMenuAuth && useTotSearch){
				if(JSONObjectUtil.isEmpty(contentsMenuJsonArray)) contentsMenuJsonArray = new JSONArray();
				contentsMenuJsonArray.add(menu);
			}
			
			boolean isChildHidden = JSONObjectUtil.isEquals(menu, "child_hidden", "1");
			if(isChildHidden) continue;
			
			JSONArray childContentsMenuJsonArray = fn_getContentsMenuJsonArray(totalMenuName.toString(), JSONObjectUtil.getJSONArray(JSONObjectUtil.getJSONObject(menuArray, i), "menu-list"), menus);
			
			if(!JSONObjectUtil.isEmpty(childContentsMenuJsonArray)){
				if(JSONObjectUtil.isEmpty(contentsMenuJsonArray)) contentsMenuJsonArray = new JSONArray();
				contentsMenuJsonArray.addAll(childContentsMenuJsonArray);
			}
		}
		return contentsMenuJsonArray;
	}
}
