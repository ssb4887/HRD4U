package rbs.egovframework.common;

import java.util.HashMap;

import javax.annotation.Resource;

import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.util.CodeHelper;
import com.woowonsoft.egovframework.util.DataSecurityUtil;
import com.woowonsoft.egovframework.util.JSONObjectUtil;
import com.woowonsoft.egovframework.util.StringUtil;

import egovframework.rte.fdl.property.EgovPropertyService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CommonFunction {
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	public static String encSeedEncrypt(Object key) {
		String keyStr;
		keyStr = key.toString();
		String encListKeyString = DataSecurityUtil.getSeedEncrypt(keyStr); 		
		
		return encListKeyString;		
	}
	
	public static int decSeedDecryptReturnInt(String key) {
		String keyStr;
		String decListKeyString;
		int decListKeyInt;
		keyStr = key.toString();
		decListKeyString = DataSecurityUtil.getSeedDecrypt(keyStr); 	
		decListKeyInt = StringUtil.getInt(decListKeyString);
		
		return decListKeyInt;		
	}
	
	public static String decSeedDecryptReturnString(String key) {
		String keyStr;
		String decListKeyString;
		keyStr = key.toString();
		decListKeyString = DataSecurityUtil.getSeedDecrypt(keyStr); 	
		
		return decListKeyString;		
	}
	
	/**
	 * 코드
	 * @param submitType
	 * @param itemInfo
	 * @return
	 */
	public HashMap<String, Object> getOptionHashMap(String submitType, JSONObject itemInfo) {
		return getOptionHashMap(submitType, itemInfo, null);
	}
	
	/**
	 * 코드
	 * @param submitType
	 * @param itemInfo
	 * @param addOptionHashMap
	 * @return
	 */
	public static HashMap<String, Object> getOptionHashMap(String submitType, JSONObject itemInfo, HashMap<String, Object> addOptionHashMap) {
		// 코드
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		HashMap<String, Object> optnHashMap = (addOptionHashMap != null)?CodeHelper.getItemOptnHashMap(items, itemOrder, addOptionHashMap):CodeHelper.getItemOptnHashMap(items, itemOrder);
		return  optnHashMap;
	}
	
	
	//아이템인포에 마스터코드가 등록되어있을 시 해당 마스터코드를 사용하기 위한 함수 
	public static HashMap<String, Object> fn_getOptnHashMap(ModuleAttrVO pAttrVO, String pSubmitType){
		JSONObject settingInfo = pAttrVO.getSettingInfo();
		JSONObject itemInfo = pAttrVO.getItemInfo();
		JSONObject items = JSONObjectUtil.getJSONObject(itemInfo, "items");
		String submitType = pSubmitType;
		JSONArray itemOrder = JSONObjectUtil.getJSONArray(itemInfo, submitType + "_order");
		// 탭코드
		String cateTabItemId = JSONObjectUtil.getString(settingInfo, "dset_cate_tab_id");
		String cateTabColumnId = JSONObjectUtil.getString(JSONObjectUtil.getJSONObject(items, cateTabItemId), "column_id");
		String cateTabMasterCode = JSONObjectUtil.getString(JSONObjectUtil.getJSONObject(items, cateTabItemId), "master_code");
		int cateTabObjectType = JSONObjectUtil.getInt(settingInfo, "dset_cate_tab_object_type");
		
		String listSearchId = "list_search";
		JSONObject listSearch = JSONObjectUtil.getJSONObject(itemInfo, listSearchId);
		
		HashMap<String, Object> searchOptnHashMap = CodeHelper.getItemOptnSearchHashMap(listSearch, cateTabItemId, cateTabColumnId, cateTabObjectType, cateTabMasterCode);
		
		HashMap<String, Object> optnHashMap = null;
		if(pSubmitType.equals("list")) {
			optnHashMap = getOptionHashMap(submitType, itemInfo, searchOptnHashMap);
		}else if(pSubmitType.equals("view")) {			
			optnHashMap = CodeHelper.getItemOptnHashMap(items, itemOrder);
		}else if(pSubmitType.equals("modify")) {
			optnHashMap = CodeHelper.getItemOptnHashMap(items, itemOrder);
		}else {
			optnHashMap = CodeHelper.getItemOptnHashMap(items, itemOrder);
		}
			return optnHashMap;
		}
	


}
