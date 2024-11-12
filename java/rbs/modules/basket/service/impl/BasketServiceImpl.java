package rbs.modules.basket.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import rbs.egovframework.LoginVO;
import rbs.modules.basket.dto.Basket;
import rbs.modules.basket.dto.BskClAtmcDto;
import rbs.modules.basket.dto.BskClLogDto;
import rbs.modules.basket.dto.BskClPassivDto;
import rbs.modules.basket.dto.BskHashTagDto;
import rbs.modules.basket.dto.BskRecDto;
import rbs.modules.basket.dto.Criteria;
import rbs.modules.basket.dto.ExcelUploadDto;
import rbs.modules.basket.dto.HashTagDto;
import rbs.modules.basket.dto.ClLclasDto;
import rbs.modules.basket.dto.ClResveDto;
import rbs.modules.basket.dto.ClSclasDto;
import rbs.modules.basket.dto.TrDataDto;
import rbs.modules.basket.dto.ZipCodeDto;
import rbs.modules.basket.mapper.BasketFileMapper;
import rbs.modules.basket.mapper.BasketMapper;
import rbs.modules.basket.mapper.BasketMultiMapper;
import rbs.modules.basket.service.BasketService;

/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * 
 * @author user
 *
 */
@Service("basketService")
public class BasketServiceImpl extends EgovAbstractServiceImpl implements BasketService {

	@Resource(name = "basketMapper")
	private BasketMapper basketDAO;

	@Resource(name = "basketFileMapper")
	private BasketFileMapper basketFileDAO;

	@Resource(name = "basketMultiMapper")
	private BasketMultiMapper basketMultiDAO;

	private static final Logger log = LoggerFactory.getLogger(BasketServiceImpl.class);

	/**
	 * 전체 목록 수
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public int getTotalCount(Criteria cri) {
		return basketDAO.getTotalCount(cri);
	}

	/**
	 * 전체 목록
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Basket> getList(Criteria cri) {
		return basketDAO.getList(cri);
	}
	
	@Override
	public List<Basket> getAllList(Criteria cri) {
		return basketDAO.getAllList(cri);
	}

	/**
	 * 전체 목록 수
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public int getResCount(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return basketDAO.getResCount(param);
	}

	/**
	 * 전체 목록
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getResList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		log.info("param={}", param);
		return basketDAO.getResList(param);
	}

	/**
	 * 상세조회
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getView(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		DataMap viewDAO = basketDAO.getView(param);
		return viewDAO;
	}

	/**
	 * 파일 상세조회
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getFileView(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		DataMap viewDAO = basketDAO.getFileView(param);
		return viewDAO;
	}

	/**
	 * multi파일 상세조회
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getMultiFileView(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		DataMap viewDAO = basketFileDAO.getFileView(param);
		return viewDAO;
	}

	/**
	 * 다운로드 수 수정
	 * 
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateFileDown(int fnIdx, int keyIdx, String fileColumnName) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		param.put("fnIdx", fnIdx);
		param.put("BPL_NO", keyIdx);
		param.put("searchList", searchList);
		param.put("FILE_COLUMN", fileColumnName);
		return basketDAO.updateFileDown(param);

	}

	/**
	 * multi file 다운로드 수 수정
	 * 
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateMultiFileDown(int fnIdx, int keyIdx, int fleIdx, String itemId) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		List<DTForm> searchList = new ArrayList<DTForm>();
		searchList.add(new DTForm("FLE_IDX", fleIdx));
		searchList.add(new DTForm("ITEM_ID", itemId));
		param.put("searchList", searchList);
		param.put("fnIdx", fnIdx);
		param.put("BPL_NO", keyIdx);
		return basketFileDAO.updateFileDown(param);

	}

	/**
	 * 수정 상세조회
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public DataMap getModify(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		DataMap viewDAO = basketDAO.getModify(param);
		return viewDAO;
	}

	/**
	 * 권한여부
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getAuthCount(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return basketDAO.getAuthCount(param);
	}

	/**
	 * 등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * 
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int insert(String uploadModulePath, int fnIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo,
			JSONObject items, JSONArray itemOrder) throws Exception {
		if (JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder))
			return -1;

		Map<String, Object> param = new HashMap<String, Object>(); // mapper parameter 데이터
		List<DTForm> dataList = new ArrayList<DTForm>(); // 저장항목

		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column"); // key 컬럼

		// 1. key 얻기
		param.put("fnIdx", fnIdx);
		int keyIdx = basketDAO.getNextId(param);

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items,
				itemOrder);
		if (dataMap == null || dataMap.size() == 0)
			return -1;

		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if (itemDataList != null)
			dataList.addAll(itemDataList);

		dataList.add(new DTForm(columnName, keyIdx));

		// 2.2 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}

		dataList.add(new DTForm("REGI_IDX", loginMemberIdx));
		dataList.add(new DTForm("REGI_ID", loginMemberId));
		dataList.add(new DTForm("REGI_NAME", loginMemberName));
		dataList.add(new DTForm("REGI_IP", regiIp));

		dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
		dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
		dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
		dataList.add(new DTForm("LAST_MODI_IP", regiIp));

		param.put("dataList", dataList);

		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = basketDAO.insert(param);

		if (result > 0) {
			// 3.2 multi data 저장
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			if (multiDataList != null) {
				int multiDataSize = multiDataList.size();
				for (int i = 0; i < multiDataSize; i++) {
					Map<String, Object> fileParam = (HashMap) multiDataList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("BPL_NO", keyIdx);
					result = basketMultiDAO.insert(fileParam);
				}
			}

			// 3.3 multi file 저장
			List fileDataList = StringUtil.getList(dataMap.get("fileDataList"));
			if (fileDataList != null) {
				int fileDataSize = fileDataList.size();
				for (int i = 0; i < fileDataSize; i++) {
					Map<String, Object> fileParam = (HashMap) fileDataList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("BPL_NO", keyIdx);
					int fleIdx = basketFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = basketFileDAO.insert(fileParam);
				}
			}
		}

		return result > 0 ? keyIdx : result;
	}

	/**
	 * 수정처리 : 화면에 조회된 정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
	 * 
	 * @param uploadModulePath
	 * @param fnIdx
	 * @param keyIdx
	 * @param regiIp
	 * @param parameterMap
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int update(String uploadModulePath, int fnIdx, int keyIdx, String regiIp, ParamForm parameterMap,
			JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception {
		if (JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder))
			return -1;

		Map<String, Object> param = new HashMap<String, Object>(); // mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>(); // 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>(); // 저장항목

		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column"); // key 컬럼
		// 1. 검색조건 setting
		searchList.add(new DTForm(columnName, keyIdx));

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items,
				itemOrder);
		if (dataMap == null || dataMap.size() == 0)
			return -1;

		// 2.1 저장항목
		List itemDataList = StringUtil.getList(dataMap.get("dataList"));
		if (itemDataList != null)
			dataList.addAll(itemDataList);

		// 2.2 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}

		dataList.add(new DTForm("LAST_MODI_IDX", loginMemberIdx));
		dataList.add(new DTForm("LAST_MODI_ID", loginMemberId));
		dataList.add(new DTForm("LAST_MODI_NAME", loginMemberName));
		dataList.add(new DTForm("LAST_MODI_IP", regiIp));

		param.put("dataList", dataList);
		param.put("searchList", searchList);
		param.put("fnIdx", fnIdx);

		// 3. DB 저장
		// 3.1 기본 정보 테이블
		int result = basketDAO.update(param);

		if (result > 0) {
			// 3.2 multi data 저장
			// 3.2.1 multi data 삭제
			Map<String, Object> multiDelParam = new HashMap<String, Object>();
			multiDelParam.put("fnIdx", fnIdx);
			multiDelParam.put("BPL_NO", keyIdx);
			basketMultiDAO.cdelete(multiDelParam);

			// 3.2.2 multi data 등록
			List multiDataList = StringUtil.getList(dataMap.get("multiDataList"));
			if (multiDataList != null) {
				int multiDataSize = multiDataList.size();
				for (int i = 0; i < multiDataSize; i++) {
					Map<String, Object> multiParam = (HashMap) multiDataList.get(i);
					multiParam.put("fnIdx", fnIdx);
					multiParam.put("BPL_NO", keyIdx);
					result = basketMultiDAO.insert(multiParam);
				}
			}

			// 3.3 multi file 저장
			// 3.3.1 multi file 신규 저장
			List fileDataList = StringUtil.getList(dataMap.get("fileDataList"));
			if (fileDataList != null) {

				// 3.3.1.1 key 얻기
				/*
				 * Map<String, Object> fileParam1 = new HashMap<String, Object>();
				 * fileParam1.put("BPL_NO", keyIdx); fileParam1.put("fnIdx", fnIdx); int fleIdx
				 * = basketFileDAO.getNextId(fileParam1);
				 */

				// 3.3.1.2 DB 저장
				int fileDataSize = fileDataList.size();
				for (int i = 0; i < fileDataSize; i++) {
					Map<String, Object> fileParam = (HashMap) fileDataList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("BPL_NO", keyIdx);
					int fleIdx = basketFileDAO.getNextId(fileParam);
					fileParam.put("FLE_IDX", fleIdx);
					result = basketFileDAO.insert(fileParam);
				}
			}

			// 3.3.2 multi file 수정
			List fileModifyDataList = StringUtil.getList(dataMap.get("fileModifyDataList"));
			if (fileModifyDataList != null) {
				int fileDataSize = fileModifyDataList.size();
				for (int i = 0; i < fileDataSize; i++) {
					Map<String, Object> fileParam = (HashMap) fileModifyDataList.get(i);
					fileParam.put("BPL_NO", keyIdx);
					fileParam.put("fnIdx", fnIdx);
					result = basketFileDAO.update(fileParam);
				}
			}

			// 3.3.3 multi file 삭제
			List fileDeleteSearchList = StringUtil.getList(dataMap.get("fileDeleteSearchList"));
			if (fileDeleteSearchList != null) {

				List<Object> deleteMultiFileList = new ArrayList<Object>();
				int fileDataSize = fileDeleteSearchList.size();
				for (int i = 0; i < fileDataSize; i++) {
					Map<String, Object> fileParam = (HashMap) fileDeleteSearchList.get(i);
					fileParam.put("fnIdx", fnIdx);
					fileParam.put("BPL_NO", keyIdx);
					// 6.1 삭제목록 select
					List<Object> deleteFileList2 = basketFileDAO.getList(fileParam);
					if (deleteFileList2 != null)
						deleteMultiFileList.addAll(deleteFileList2);
					// 6.2 DB 삭제
					result = basketFileDAO.cdelete(fileParam);
				}

				// 3.3.4 multi file 삭제
				FileUtil.isKeyDelete(fileRealPath, deleteMultiFileList, "FILE_SAVED_NAME");
			}

			// 4. file(단일항목) 삭제
			List deleteFileList = StringUtil.getList(dataMap.get("deleteFileList"));
			if (deleteFileList != null) {
				FileUtil.isDelete(fileRealPath, deleteFileList);
			}
		}
		return result > 0 ? keyIdx : result;
	}

	/**
	 * 삭제 전체 목록 수
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public int getDeleteCount(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return basketDAO.getDeleteCount(param);
	}

	/**
	 * 삭제 전체 목록
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getDeleteList(int fnIdx, Map<String, Object> param) {
		param.put("fnIdx", fnIdx);
		return basketDAO.getDeleteList(param);
	}

	/**
	 * 삭제처리 : 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
	 * 
	 * @param fnIdx
	 * @param parameterMap
	 * @param deleteIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public int delete(int fnIdx, ParamForm parameterMap, int[] deleteIdxs, String regiIp, JSONObject settingInfo)
			throws Exception {
		if (deleteIdxs == null)
			return 0;

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인 사용자 정보
		Map<String, Object> param = new HashMap<String, Object>(); // mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>(); // 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>(); // 저장항목

		// 1. 저장조건
		String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");
		searchList.add(new DTForm(columnName, deleteIdxs));

		// 2. 저장 항목
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
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
		return basketDAO.delete(param);
	}

	/**
	 * 복원처리 : 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
	 * 
	 * @param fnIdx
	 * @param restoreIdxs
	 * @param regiIp
	 * @param settingInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public int restore(int fnIdx, int[] restoreIdxs, String regiIp, JSONObject settingInfo) throws Exception {
		if (restoreIdxs == null)
			return 0;

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인 사용자 정보
		Map<String, Object> param = new HashMap<String, Object>(); // mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>(); // 검색조건
		List<DTForm> dataList = new ArrayList<DTForm>(); // 저장항목

		// 1. 저장조건
		searchList.add(new DTForm(JSONObjectUtil.getString(settingInfo, "idx_column"), restoreIdxs));

		// 2. 저장 항목
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
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
		return basketDAO.restore(param);
	}

	/**
	 * 완전삭제처리 : 화면에 조회된 정보를 데이터베이스에서 삭제
	 * 
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
	public int cdelete(String uploadModulePath, int fnIdx, int[] deleteIdxs, JSONObject settingInfo, JSONObject items,
			JSONArray itemOrder) throws Exception {
		if (deleteIdxs == null)
			return 0;

		Map<String, Object> param = new HashMap<String, Object>(); // mapper parameter 데이터
		List<DTForm> searchList = new ArrayList<DTForm>(); // 검색조건

		// 1. 저장조건
		searchList.add(new DTForm(JSONObjectUtil.getString(settingInfo, "idx_column"), deleteIdxs));
		param.put("searchList", searchList);
		param.put("fnIdx", fnIdx);

		List<Object> deleteMultiFileList = null;
		List<Object> deleteFileList = null;
		// 2. 삭제할 파일 select
		// 2.1 삭제할 multi file 목록 select
		deleteMultiFileList = basketFileDAO.getList(param);
		// 2.2 삭제할 file(단일항목) select
		List deleteFileColumnList = ModuleUtil.getFileObjectList(items, itemOrder);
		if (deleteFileColumnList != null) {
			param.put("columnList", deleteFileColumnList);

			deleteFileList = basketDAO.getFileList(param);
		}

		// 3. delete
		int result = basketDAO.cdelete(param);
		if (result > 0) {
			// 4. 파일 삭제
			// 4.1 multi file 삭제
			String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator
					+ uploadModulePath;
			if (deleteMultiFileList != null) {
				FileUtil.isKeyDelete(fileRealPath, deleteMultiFileList, "FILE_SAVED_NAME");
			}

			// 4.2 file(단일항목) 삭제
			if (deleteFileList != null) {
				FileUtil.isKeyDelete(fileRealPath, deleteFileList);
			}
		}

		return result;
	}

	/**
	 * mult file 전체 목록 : 항목ID에 대한 HashMap
	 * 
	 * @param fnIdx
	 * @param keyIdx
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	public Map<String, List<Object>> getMultiFileHashMap(int fnIdx, int keyIdx, JSONObject settingInfo,
			JSONObject items, JSONArray itemOrder) {
		Map<String, List<Object>> resultHashMap = new HashMap<String, List<Object>>();
		if (!JSONObjectUtil.isEmpty(itemOrder) && !JSONObjectUtil.isEmpty(items)) {
			List<String> itemIdList = new ArrayList<String>();
			String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");
			int searchOrderSize = itemOrder.size();
			for (int i = 0; i < searchOrderSize; i++) {
				String itemId = JSONObjectUtil.getString(itemOrder, i);
				JSONObject item = JSONObjectUtil.getJSONObject(items, itemId);
				int formatType = JSONObjectUtil.getInt(item, "format_type");
				int objectType = JSONObjectUtil.getInt(item, "object_type");
				if (formatType == 0 && objectType == 9) {
					// mult file
					itemIdList.add(itemId);
					/*
					 * Map<String, Object> param = new HashMap<String, Object>(); List<DTForm>
					 * searchList = new ArrayList<DTForm>(); searchList.add(new DTForm("A." +
					 * columnName, keyIdx)); searchList.add(new DTForm("A.ITEM_ID", itemId));
					 * param.put("searchList", searchList); param.put("fnIdx", fnIdx);
					 * resultHashMap.put(itemId, basketFileDAO.getList(param));
					 */
				}
			}
			if (itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
				searchList.add(new DTForm("A." + columnName, keyIdx));
				searchList.add(new DTForm("A.ITEM_ID", (String[]) itemIdList.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
				param.put("fnIdx", fnIdx);
				resultHashMap = basketFileDAO.getMapList(param);
			}
		}

		return resultHashMap;
	}

	/**
	 * mult data 전체 목록 : 항목ID에 대한 HashMap
	 * 
	 * @param fnIdx
	 * @param keyIdx
	 * @param settingInfo
	 * @param items
	 * @param itemOrder
	 * @return
	 */
	@Override
	public Map<String, List<Object>> getMultiHashMap(int fnIdx, int keyIdx, JSONObject settingInfo, JSONObject items,
			JSONArray itemOrder) {
		Map<String, List<Object>> resultHashMap = new HashMap<String, List<Object>>();
		if (!JSONObjectUtil.isEmpty(itemOrder) && !JSONObjectUtil.isEmpty(items)) {
			List<String> itemIdList = new ArrayList<String>();
			String columnName = JSONObjectUtil.getString(settingInfo, "idx_column");
			int searchOrderSize = itemOrder.size();
			for (int i = 0; i < searchOrderSize; i++) {
				String itemId = JSONObjectUtil.getString(itemOrder, i);
				JSONObject item = JSONObjectUtil.getJSONObject(items, itemId);
				int formatType = JSONObjectUtil.getInt(item, "format_type");
				int objectType = JSONObjectUtil.getInt(item, "object_type");
				if (formatType == 0 && (objectType == 3 || objectType == 4 || objectType == 11)) {
					itemIdList.add(itemId);
					/*
					 * Map<String, Object> param = new HashMap<String, Object>(); List<DTForm>
					 * searchList = new ArrayList<DTForm>(); searchList.add(new DTForm("A." +
					 * columnName, keyIdx)); searchList.add(new DTForm("A.ITEM_ID", itemId));
					 * param.put("searchList", searchList); param.put("fnIdx", fnIdx);
					 * resultHashMap.put(itemId, basketMultiDAO.getList(param));
					 */
				}
			}
			if (itemIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				List<DTForm> searchList = new ArrayList<DTForm>();
				searchList.add(new DTForm("A." + columnName, keyIdx));
				searchList.add(new DTForm("A.ITEM_ID", (String[]) itemIdList.toArray(new String[itemIdList.size()])));
				param.put("searchList", searchList);
				param.put("fnIdx", fnIdx);
				resultHashMap = basketMultiDAO.getMapList(param);
			}
		}

		return resultHashMap;
	}

	/**
	 * 파일등록처리 : 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * 
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
	public Map<String, Object> getFileUpload(String uploadModulePath, int fnIdx, String regiIp, ParamForm parameterMap,
			JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception {
		if (JSONObjectUtil.isEmpty(items) || JSONObjectUtil.isEmpty(itemOrder))
			return null;

		// 2. 항목설정으로 저장항목 setting
		String fileRealPath = RbsProperties.getProperty("Globals.upload.file.path") + File.separator + uploadModulePath;
		HashMap<String, Object> dataMap = ModuleUtil.getItemInfoDataMap(fileRealPath, parameterMap, settingInfo, items,
				itemOrder);
		if (dataMap == null || dataMap.size() == 0)
			return null;
		/*
		 * System.out.println("-----------itemOrder:" + itemOrder);
		 * System.out.println("-----------dataMap:" + dataMap);
		 * System.out.println("-----------file_origin_name:" +
		 * parameterMap.get("file_origin_name"));
		 * System.out.println("-----------file_saved_name:" +
		 * parameterMap.get("file_saved_name"));
		 * System.out.println("-----------file_size:" + parameterMap.get("file_size"));
		 */

		List originList = StringUtil.getList(parameterMap.get("file_origin_name"));
		Map<String, Object> fileInfo = null;
		if (originList != null && !originList.isEmpty()) {
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
	@Transactional
	public int addClException(HashMap<String, Object> param, String regiIp) {

		// 2.2 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}

		param.put("REGI_IDX", loginMemberIdx);
		param.put("REGI_ID", loginMemberId);
		param.put("REGI_NAME", loginMemberName);
		param.put("REGI_IP", regiIp);

		param.put("LAST_MODI_IDX", loginMemberIdx);
		param.put("LAST_MODI_ID", loginMemberId);
		param.put("LAST_MODI_NAME", loginMemberName);
		param.put("LAST_MODI_IP", regiIp);

		log.info("param {}", param);
		//이전 예외지정 기록 삭제
		basketDAO.updateClException(param);
		
		return basketDAO.addClException(param);
	}


	// 수동분류 변경
	@Override
	@Transactional
	public void bskClPassivModify(List<BskClPassivDto> dtos, String regiIp) throws Exception {
		
		//수동분류 이전데이터 비활성화
		basketDAO.updateBskClPassivLclas(dtos);
		basketDAO.updateBskClPassivSclas(dtos);

		
		// 2.2 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}

		for (BskClPassivDto dto : dtos) {

			dto.setRegiId(loginMemberId);
			dto.setRegiIdx(loginMemberIdx);
			dto.setRegiIp(regiIp);
			dto.setRegiName(loginMemberName);
		}
		

		
		basketDAO.updateClExceptionDtos(dtos);
		basketDAO.addClExceptionDtos(dtos);

		basketDAO.insertBskClPassivLclas(dtos);
		basketDAO.insertBskClPassivSclas(dtos);
	}


	// 훈련이력 조회
	@Override
	public List<TrDataDto> getTr(Criteria cri) {
		List<TrDataDto> dtos = basketDAO.getTr(cri);
		return dtos;
	}
	
	// 훈련이력 카운트 조회
	@Override
	public int getTrCount(String bplNo) {
		int trCount = basketDAO.getTrCount(bplNo);
		return trCount;
	}

	// 해시태그 조회
	@Override
	public List<HashTagDto> getHashTag(HashMap<String, Object> param) throws Exception {
		List<HashTagDto> dtos = basketDAO.getHashTag(param);
		return dtos;
	}

	@Override
	@Transactional
	public void assignHashTag(List<BskHashTagDto> dtos, String regiIp) {
		
		//해시태그 이전상태 변경
		basketDAO.updateHashTag(dtos);
		
		// 2.2 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		for (BskHashTagDto dto : dtos) {
			dto.setRegiId(loginMemberId);
			dto.setRegiIdx(loginMemberIdx);
			dto.setRegiIp(regiIp);
			dto.setRegiName(loginMemberName);
		}

		 basketDAO.assignHashTag(dtos);
	}

	@Override
	public Basket getBasketSelect(String bplNo) {
		return basketDAO.getBasketSelect(bplNo);
	}

	@Override
	public List<ClLclasDto> getClLclasList() throws Exception {
		return basketDAO.getClLclasList();
	}

	// 분류예약 등록
	@Override
	public int addResve(ClResveDto dto) throws Exception {
		return basketDAO.addResve(dto);
	}

	// 분류예약 조회
	@Override
	public DataMap getResevCl(Map<String, Object> param) throws Exception {
		return basketDAO.getResevCl(param);
	}

	// 분류예약 수정
	@Override
	public int editResve(ClResveDto dto) throws Exception {
		return basketDAO.editResve(dto);
	}

	// 분류예약 삭제
	@Override
	public int delResve(ClResveDto dto) throws Exception {
		return basketDAO.delResve(dto);
	}

	// 소분류 수정
	@Override
	public int editClass(ClSclasDto dto) throws Exception {
		return basketDAO.editClass(dto);
	}

	// 소분류 삭제
	@Override
	public int delClass(ClSclasDto dto) throws Exception {
		return basketDAO.delClass(dto);
	}

	// 소분류 조회
	@Override
	public DataMap getClassify(Map<String, Object> param) throws Exception {
		return basketDAO.getClassify(param);
	}

	// 자동 대소분류 조회
	@Override
	public BskClAtmcDto getBskClAtmcSelect(String bplNo) {

		return basketDAO.getBskClAtmcSelect(bplNo);

	}

	// 수동 대소분류 조회
	@Override
	public List<BskClPassivDto> getBskClPassivSelect(String bplNo) {
		return basketDAO.getBskClPassivSelect(bplNo);

	}

	@Override
	public List<ClSclasDto> getClSclasList(Integer clLclasCd) {
		return basketDAO.getClSclasList(clLclasCd);
	}

	@Override
	public List<ZipCodeDto> getZipcode() {
		return basketDAO.getZipcode();
	}

	@Override
	public List<ZipCodeDto> getSigngu(String selectedCtprvn) {
		return basketDAO.getSigngu(selectedCtprvn);
	}

	// 해당 기업 해시태그 조회
	@Override
	public List<BskHashTagDto> getBskHashtagSelect(String bplNo) {
		return basketDAO.getBskHashtagSelect(bplNo);
	}

	// 해당 기업 해시태그 삭제
	@Override
	public void deleteHashtag(BskHashTagDto dto, String remoteAddr) {
		// 2.2 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		dto.setLastModiId(loginMemberId);
		dto.setLastModiIdx(loginMemberIdx);
		dto.setLastModiIp(remoteAddr);
		dto.setLastModiName(loginMemberName);

		basketDAO.deleteHashtag(dto);

	}

	@Override
	public List<BskClLogDto> getBskClLogList(String bplNo) {
		return basketDAO.getBskClLogList(bplNo);
	}

	@Override
	public List<HashMap<String, Object>> getIndustCd() {
		return basketDAO.getIndustCd();
	}

	@Override
	public int bskDataUpload(HashMap<String, Object> map) {
		return basketDAO.bskDataUpload(map);
	}
	
	@Override
	public int bskDataUploadMerge(HashMap<String, Object> map) {
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		map.put("regiId", loginMemberId);
		map.put("regiIdx", loginMemberIdx);
		map.put("regiName", loginMemberName);

		return basketDAO.bskDataUploadMerge(map);
	}

	@Override
	public int bskDataFileUpload(ExcelUploadDto dto, String remoteAddr) {
		// 2.2 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}
		dto.setRegiId(loginMemberId);
		dto.setRegiIdx(loginMemberIdx);
		dto.setRegiIp(remoteAddr);
		dto.setRegiName(loginMemberName);

		return basketDAO.bskDataFileUpload(dto);
	}

	@Override
	public List<ExcelUploadDto> getExcelUploadList(Criteria cri) {
		return basketDAO.getExcelUploadList(cri);
	}
	
	@Override
	public int getTotalExcelCount() {
		return basketDAO.getTotalExcelCount();
	}

	@Override
	public int bskDataFileResultUpdate(ExcelUploadDto dto) {
		return basketDAO.bskDataFileResultUpdate(dto);
	}

	@Override
	public List<BskRecDto> getRecList(String bplNo) {
		return basketDAO.getRecList(bplNo);
	}

	// 해시태그 등록
	@Override
	public int addHash(HashTagDto hto) throws Exception {
		return basketDAO.addHash(hto);
	}
	
	
	// 소속기관번호 조회
	@Override
	public int getInsttIdx(String sbCd) throws Exception {
		return basketDAO.getInsttIdx(sbCd);
	}
	
	// 해시태그 삭제
	@Override
	public int delHash(HashTagDto hto) throws Exception {
		return basketDAO.delHash(hto);
	}
	
	// 해시태그 수정
	@Override
	public int editHash(HashTagDto hto) throws Exception {
		return basketDAO.editHash(hto);
	}
	
	// 해시태그 상세조회
	@Override
	public DataMap getHashTag(Map<String, Object> param) throws Exception {
		return basketDAO.getHashTag(param);
	}

	@Override
	public void getBskClLogSelect(String bplNo) {
		
	}


	@Override
	public void getBskTrLogSelect(String bplNo) {
		
	}

	@Override
	public List<String> getBplNoList(Criteria cri) {
		return basketDAO.getBplNoList(cri);
	}

	@Override
	public int getWebTotalCount(Criteria cri) {
		return basketDAO.getWebTotalCount(cri);
	}

	@Override
	public List<Basket> getWebList(Criteria cri) {
		return basketDAO.getWebList(cri);
	}

	@Override
	public List<Basket> getWebAllList(Criteria cri) {
		return basketDAO.getWebAllList(cri);
	}

	@Override
	public List<HashMap<String, String>> getBskFncInfo(String bizrNo) {
		return basketDAO.getBskFncInfo(bizrNo);
	}




}
