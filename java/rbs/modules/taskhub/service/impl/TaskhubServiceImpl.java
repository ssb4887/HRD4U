package rbs.modules.taskhub.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import rbs.egovframework.LoginVO;
import rbs.modules.taskhub.mapper.TaskhubFileMapper;
import rbs.modules.taskhub.mapper.TaskhubMapper;
import rbs.modules.taskhub.mapper.TaskhubMultiMapper;
import rbs.modules.taskhub.service.TaskhubService;

import com.woowonsoft.egovframework.form.DTForm;
import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ParamForm;
import com.woowonsoft.egovframework.prop.RbsProperties;
import com.woowonsoft.egovframework.util.FileUtil;
import com.woowonsoft.egovframework.util.JSONObjectUtil;
import com.woowonsoft.egovframework.util.ModuleUtil;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("taskhubService")
public class TaskhubServiceImpl extends EgovAbstractServiceImpl implements TaskhubService {

	@Resource(name="taskhubMapper")
	private TaskhubMapper taskhubDAO;
	
	@Resource(name="taskhubFileMapper")
	private TaskhubFileMapper taskhubFileDAO;
	
	@Resource(name="taskhubMultiMapper")
	private TaskhubMultiMapper taskhubMultiDAO;
	
	/**
	 * 전체 목록 수
	 * @param fnIdx
	 * @param param
	 * @return
	 */
    @Override
	public int getTotalCount(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
    	return taskhubDAO.getTotalCount(param);
    }
    
    /**
	 * 전체 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getList(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
		return taskhubDAO.getList(param);
	}
	
	/**
	 * 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getView(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
		DataMap viewDAO = taskhubDAO.getView(param);
		return viewDAO;
	}

	/**
	 * 파일 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getFileView(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
		DataMap viewDAO = taskhubDAO.getFileView(param);
		return viewDAO;
	}

	/**
	 * multi파일 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getMultiFileView(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
		DataMap viewDAO = taskhubFileDAO.getFileView(param);
		return viewDAO;
	}
	
	/**
	 * 다운로드 수 수정
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateFileDown(int fnIdx, int keyIdx, String fileColumnName) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
    	param.put("fnIdx", fnIdx);
		param.put("KEY_IDX", keyIdx);
    	param.put("searchList", searchList);
    	param.put("FILE_COLUMN", fileColumnName);
		return taskhubDAO.updateFileDown(param);
		
	}
	
	/**
	 * multi file 다운로드 수 수정
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateMultiFileDown(int fnIdx, int keyIdx, int fleIdx, String itemId) throws Exception{
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		searchList.add(new DTForm("FLE_IDX", fleIdx));
		searchList.add(new DTForm("ITEM_ID", itemId));
    	param.put("searchList", searchList);
    	param.put("fnIdx", fnIdx);
		param.put("KEY_IDX", keyIdx);
		return taskhubFileDAO.updateFileDown(param);
		
	}

	/**
	 * 수정 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getModify(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
		DataMap viewDAO = taskhubDAO.getModify(param);
		return viewDAO;
	}
	
	/**
	 * 권한여부
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getAuthCount(int fnIdx, Map<String, Object> param) {
    	param.put("fnIdx", fnIdx);
    	return taskhubDAO.getAuthCount(param);
    }

	/**
	 * 삭제처리 : 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
	 * @param fnIdx
	 * @param parameterMap
	 * @param deleteIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public int delete(int fnIdx, ParamForm parameterMap, int[] deleteIdxs, String regiIp, JSONObject settingInfo) throws Exception {
		if(deleteIdxs == null) return 0;
		
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>();						// 저장항목

		// 1. 저장조건
    	String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");
    	searchList.add(new DTForm(columnName, deleteIdxs));
    	
		// 2. 저장 항목
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if(loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
    	
    	dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
    	dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
    	dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
    	dataList.add(new DTForm("LAST_MODI_IP", regiIp));
    	
		param.put("searchList", searchList);
		param.put("dataList", dataList);
    	param.put("fnIdx", fnIdx);
		
		// 3. DB 저장
		return taskhubDAO.delete(param);
	}

	/**
	 * 복원처리 : 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
	 * @param fnIdx
	 * @param restoreIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public int restore(int fnIdx, int[] restoreIdxs, String regiIp, JSONObject settingInfo) throws Exception {
		if(restoreIdxs == null) return 0;

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();	// 로그인 사용자 정보
		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>();						// 저장항목

		// 1. 저장조건
		searchList.add(new DTForm(JSONObjectUtil.getString(settingInfo, "idx_column"), restoreIdxs));

		// 2. 저장 항목
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if(loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
    	
    	dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
    	dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
    	dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
    	dataList.add(new DTForm("LAST_MODI_IP", regiIp));

		param.put("searchList", searchList);
		param.put("dataList", dataList);
    	param.put("fnIdx", fnIdx);
		
		// 3. DB 저장
		return taskhubDAO.restore(param);
	}

	/**
	 * 완전삭제처리 : 화면에 조회된 정보를 데이터베이스에서 삭제
	 * @param uploadModulePath
	 * @param fnIdx
	 * @param deleteIdxs
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	@Override
	public int cdelete(String uploadModulePath, int fnIdx, int[] deleteIdxs, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception {
		if(deleteIdxs == null) return 0;

		Map<String, Object> param = new HashMap<String, Object>();				// mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>();						// 검색조건
		
    	// 1. 저장조건
		searchList.add(new DTForm(JSONObjectUtil.getString(settingInfo, "idx_column"), deleteIdxs));
		param.put("searchList", searchList);
    	param.put("fnIdx", fnIdx);
		
		List<Object> deleteMultiFileList = null;
		List<Object> deleteFileList = null;
		// 2. 삭제할 파일 select
		// 2.1 삭제할  multi file 목록 select
		deleteMultiFileList = taskhubFileDAO.getList(param);
		// 2.2 삭제할 file(단일항목) select
		List deleteFileColumnList = ModuleUtil.getFileObjectList(items, itemOrder);
		if(deleteFileColumnList != null) {
			param.put("columnList", deleteFileColumnList);
			
			deleteFileList = taskhubDAO.getFileList(param);
		}
		
		// 3. delete
		int result = taskhubDAO.cdelete(param);
		if(result > 0) {
			// 4. 파일 삭제
			// 4.1 multi file 삭제
			String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
			if(deleteMultiFileList != null) {
				FileUtil.isKeyDelete(fileRealPath, deleteMultiFileList, "FILE_SAVED_NAME");
			}
			
			// 4.2 file(단일항목) 삭제
			if(deleteFileList != null) {
				FileUtil.isKeyDelete(fileRealPath, deleteFileList);
			}
		}
		
		return result;
	}
	
	/**
	 * mult file 전체 목록 : 항목ID에 대한 HashMap
	 * @param fnIdx
	 * @param keyIdx
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	public  Map<String, List<Object>> getMultiFileHashMap(int fnIdx, int keyIdx, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) {
		Map<String, List<Object>> resultHashMap = new HashMap<String, List<Object>>();
		if(!JSONObjectUtil.isEmpty(itemOrder) && !JSONObjectUtil.isEmpty(items)) {
			List<String> itemIdList = new ArrayList<String>();
	    	String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");
			int searchOrderSize = itemOrder.size();
			for(int i = 0 ; i < searchOrderSize ; i ++) {
				String itemId = JSONObjectUtil.getString(itemOrder, i);
				JSONObject item = JSONObjectUtil.getJSONObject(items, itemId);
				int formatType = JSONObjectUtil.getInt(item, "format_type");
				int objectType = JSONObjectUtil.getInt(item, "object_type");
				if(formatType == 0 && objectType == 9) {
					// mult file
					itemIdList.add(itemId);
					/*
					Map<String, Object> param = new HashMap<String, Object>();
					List<DTForm> searchList = new ArrayList<DTForm>();
			    	searchList.add(new DTForm("A." + columnName, keyIdx));
			    	searchList.add(new DTForm("A.ITEM_ID", itemId));
					param.put("searchList", searchList);
			    	param.put("fnIdx", fnIdx);
					resultHashMap.put(itemId, taskhubFileDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A." + columnName, keyIdx));
		    	searchList.add(new DTForm("A.ITEM_ID", (String[])itemIdList.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
		    	param.put("fnIdx", fnIdx);
				resultHashMap = taskhubFileDAO.getMapList(param);
			}
		}
		
		return resultHashMap;
	}
	
	/**
	 * mult data 전체 목록 : 항목ID에 대한 HashMap
	 * @param fnIdx
	 * @param keyIdx
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	@Override
	public Map<String, List<Object>> getMultiHashMap(int fnIdx, int keyIdx, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) {
		Map<String, List<Object>> resultHashMap = new HashMap<String, List<Object>>();
		if(!JSONObjectUtil.isEmpty(itemOrder) && !JSONObjectUtil.isEmpty(items)) {
			List<String> itemIdList = new ArrayList<String>();
	    	String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");
			int searchOrderSize = itemOrder.size();
			for(int i = 0 ; i < searchOrderSize ; i ++) {
				String itemId = JSONObjectUtil.getString(itemOrder, i);
				JSONObject item = JSONObjectUtil.getJSONObject(items, itemId);
				int formatType = JSONObjectUtil.getInt(item, "format_type");
				int objectType = JSONObjectUtil.getInt(item, "object_type");
				if(formatType == 0 && (objectType == 3 || objectType == 4 || objectType == 11)) {
					itemIdList.add(itemId);
					/*
					Map<String, Object> param = new HashMap<String, Object>();
					List<DTForm> searchList = new ArrayList<DTForm>();
			    	searchList.add(new DTForm("A." + columnName, keyIdx));
			    	searchList.add(new DTForm("A.ITEM_ID", itemId));
					param.put("searchList", searchList);
			    	param.put("fnIdx", fnIdx);
					resultHashMap.put(itemId, taskhubMultiDAO.getList(param));
					*/
				}
			}
			if(itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
		    	searchList.add(new DTForm("A." + columnName, keyIdx));
		    	searchList.add(new DTForm("A.ITEM_ID", (String[])itemIdList.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
		    	param.put("fnIdx", fnIdx);
				resultHashMap = taskhubMultiDAO.getMapList(param);
			}
		}
		
		return resultHashMap;
	}

	/**
	 * 파일등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * @param uploadModulePath
	 * @param fnIdx
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> getFileUpload(String uploadModulePath, int fnIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception  {
		if(JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder)) return null;

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items, itemOrder);
		if(dataMap == null || dataMap.size() == 0) return null;

		List originList = StringUtil.getList(parameterMap.get("file_origin_name"));
		Map<String, Object> fileInfo = null;
		if(originList != null && !originList.isEmpty()) {
			String fileOriginName = StringUtil.getString(originList.get(0));
			HashMap savedMap = StringUtil.getHashMap(parameterMap.get("file_saved_name"));
			HashMap sizeMap = StringUtil.getHashMap(parameterMap.get("file_size"));
			fileInfo = new HashMap<String, Object>();
			fileInfo.put("file_origin_name", fileOriginName);
			fileInfo.put("file_saved_name", savedMap.get(fileOriginName));
			fileInfo.put("file_size", sizeMap.get(fileOriginName));
		}
		return fileInfo;
	}

	@Override
	public Map getBSK(LoginVO loginVO) {
		if(loginVO.getUsertypeIdx() == 5) {
			return taskhubDAO.getBSKcorp(loginVO.getMemberIdx());
		}
		return null;
	}

	@Override
	public List<Map> getNotice() {
		return taskhubDAO.getNotice();
	}

	@Override
	public List<Map> getHRDRoom() {
		return taskhubDAO.getHRDRoom();
	}

	@Override
	public List<Map> getProgressBSC(String bpl_no) {
		return taskhubDAO.getProgressBSC(bpl_no);
	}

	@Override
	public List<Map> getProgressCNSL(Map param) {
		return taskhubDAO.getProgressCNSL(param);
	}

	@Override
	public Map getCntBPL(String bpl_no) {
		return taskhubDAO.getCntBPL(bpl_no);
	}

	@Override
	public List<Map> getProgressClinic(String bpl_no) {
		return taskhubDAO.getProgressClinic(bpl_no);
	}

	@Override
	public Map getCntCenter(String member_idx) {
		return taskhubDAO.getCntCenter(member_idx);
	}

	@Override
	public List<Map> getAgreement(String member_idx) {
		return taskhubDAO.getAgreement(member_idx);
	}

	@Override
	public int getNextIdSptjReq(Map param) {
		return taskhubDAO.getNextIdSptjReq(param);
	}

	@Override
	public int putSptjReq(Map param) {
		return taskhubDAO.putSptjReq(param);
	}

	@Override
	public List<Map> getSptjReqList(Map param) {
		LoginVO loginVO = (LoginVO)param.get("loginVO");
		int usertype = loginVO.getUsertypeIdx();
		List<Map> map = new ArrayList<Map>();
		if(usertype == 5) {
			String bpl_no = loginVO.getBplNo();
			param.put("bpl_no", bpl_no);
			map = taskhubDAO.getSptjReqListBPL(param);
		} else if(usertype == 30 || usertype == 31) {
			String member_idx = loginVO.getMemberIdx();
			param.put("member_idx", member_idx);
			map = taskhubDAO.getSptjReqListDoc(param);
		} else {
			map = taskhubDAO.getSptjReqList(param);
		}
		return mapping(map);
	}
	
	private List<Map> mapping(List<Map> maps) {
		Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
		for(Map m_ : maps) {
			String cn_ = (String)m_.get("CN");
			Matcher matcher = pattern.matcher(cn_);
			if(matcher.find()) {
				String job = matcher.group(1);
				String desc = cn_.replace("\\{\\{.*?\\}\\}", "");
				m_.put("JOB", job);
				m_.put("desc", desc);
			}
		}
		return maps;
	}

	@Override
	public void updateStpjReq(Map param) {
		taskhubDAO.updateStpjReq(param);
	}

	@Override
	public String getMemberIdx(String req_idx) {
		return taskhubDAO.getMemberIdx(req_idx);
	}

	@Override
	public LoginVO getLoginMap(String req_idx) {
		return taskhubDAO.getLoginMap(req_idx);
	}

	@Override
	public void finishStpjReq(Map param) {
		taskhubDAO.finishStpjReq(param);
	}

	@Override
	public List<Map> getProgramHRDBsis(Map param) {
		return taskhubDAO.getProgramHRDBsis(param);
	}

	@Override
	public List<Map> getCorpCenters() {
		return taskhubDAO.getCorpCenters();
	}

	@Override
	public List<Map> getCorpCorps(Map param) {
		List<Map> list = taskhubDAO.getCorpCorps(param);
		List<String> param_list = new ArrayList<String>();
		Map<String,Map> bpl_list = new HashMap();
		for(Map m : list) {
			String bplNo = (String)m.get("BPL_NO");
			param_list.add(bplNo);
			bpl_list.put(bplNo, m);
		}
		if(list.size() > 0) {
			List<Map> hashtags = taskhubDAO.getHashtagsOutOfBplNos(param_list);
			if(hashtags.size() > 0) {
					
				for(Map h : hashtags) {
					String bplNo_ = (String)h.get("BPL_NO");
					if(bpl_list.containsKey(bplNo_)) {
						Map m_ = bpl_list.get(bplNo_);
						m_.put("HASHTAGS", h.get("HASHTAGS"));
					}
				}
			}
			for(Map m : list) {
				String bplNo = (String)m.get("BPL_NO");
				Map m_ = bpl_list.get(bplNo);
				if(m_ != null) {
					m.put("HASHTAGS", m_.get("HASHTAGS"));
				}
			}
		}	
		return list;
	}

	@Override
	public List<Map> costInquiry(Map param) {
		return taskhubDAO.costInquiry(param);
	}

	@Override
	public Map getDoctorIntel(Map param) {
		List<Map> result = taskhubDAO.getDoctorIntel(param);
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	@Override
	public Map dashboardCount(Map param, String type_name) {
		if("consultant".equals(type_name)) {
			return taskhubDAO.dashboardCountConsultant(param);
		}
		return taskhubDAO.dashboardCount(param);
	}

	@Override
	public Map getBoard(Map param) {
		Map result = new HashMap();
		for(Map item : taskhubDAO.getBoard(param)) {
			String key = (String)item.get("BOARD_NAME");
			// key 값이 있는지 확인하고 있으면 add 없으면 put 왜? 다른 사람이 작업하는 게시판에서 board_name이 어떻게 올지 확신할 수 없다.
			// for loop 내부에 if-else 구문이 있어서 불리하긴하겠지만, 최대 게시글이 3개이기 때문에 게시판의 갯수가 10개 이하일 것. 30개 정도는 괜찮다고 판단했음.
			if(result.containsKey(key)) {
				((List<Map>)result.get(key)).add(item);
			} else {
				List<Map> arr_ = new ArrayList<Map>();
				arr_.add(item);
				result.put(key, arr_);
			}
		}
		return result;
	}

	@Override
	public List<Map> getBsisCorpo(Map param) {
		return taskhubDAO.getBsisCorpo(param);
	}

	@Override
	public Map getCnslCorpo(Map param) {
		Map<String, List<Map>> result = new HashMap<String,List<Map>>();
		result.put("cnsl", new ArrayList<Map>());
		result.put("custom", new ArrayList<Map>());
		for(Map item : taskhubDAO.getCnslCorpo(param)) {
			String key = (String)item.get("CNSL_SP");
			((List<Map>)result.get(key)).add(item);
		}
		return result;
	}

	@Override
	public List<Map> getClinicCorpor(Map param) {
		return taskhubDAO.getClinicCorpo(param);
	}

	@Override
	public List<Map> getSOJTCorpo(Map param) {
		return taskhubDAO.getSOJTCorpo(param);
	}

	@Override
	public List<Map> getProgramSojt(Map param) {
		return taskhubDAO.getProgramSojt(param);
	}

	@Override
	public List<Map> getProgramCnsl(Map param) {
		return taskhubDAO.getProgramCnsl(param);
	}

	@Override
	public List<Map> getProgramClinic(Map param) {
		return taskhubDAO.getProgramClinic(param);
	}

	@Override
	public List<Map> delayedJobs(Map param) {
		return taskhubDAO.delayedJobs(param);
	}
}