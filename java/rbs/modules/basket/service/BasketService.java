package rbs.modules.basket.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ParamForm;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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

/**
 * 샘플모듈에 관한 인터페이스클래스를 정의한다.
 * 
 * @author user
 *
 */
public interface BasketService {


	
	/**
	 * 리스트 목록
	 * @param cri
	 * @return
	 */
	public int getTotalCount(Criteria cri);
	
	/**
	 * 
	 * @param cri
	 * @return
	 */
	
	public int getWebTotalCount(Criteria cri);

	/**
	 * 전체 목록
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Basket> getList(Criteria cri);
	public List<Basket> getAllList(Criteria cri);

	/**
	 * 전체 목록 수
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getResCount(int fnIdx, Map<String, Object> param);

	/**
	 * 전체 목록
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getResList(int fnIdx, Map<String, Object> param);

	/**
	 * 상세조회
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getView(int fnIdx, Map<String, Object> param);

	/**
	 * 파일 상세조회
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getFileView(int fnIdx, Map<String, Object> param);

	/**
	 * multi파일 상세조회
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getMultiFileView(int fnIdx, Map<String, Object> param);

	/**
	 * 권한여부
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getAuthCount(int fnIdx, Map<String, Object> param);

	/**
	 * 다운로드 수 수정
	 * 
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateFileDown(int fnIdx, int keyIdx, String fileColumnName) throws Exception;

	/**
	 * multi file 다운로드 수 수정
	 * 
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateMultiFileDown(int fnIdx, int keyIdx, int fleIdx, String itemId) throws Exception;

	/**
	 * 수정 상세조회
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getModify(int fnIdx, Map<String, Object> param);

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
	public int insert(String uploadModulePath, int fnIdx, String regiIp, ParamForm parameterMap, JSONObject settingInfo,
			JSONObject items, JSONArray itemOrder) throws Exception;

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
	public int update(String uploadModulePath, int fnIdx, int keyIdx, String regiIp, ParamForm parameterMap,
			JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;

	/**
	 * 삭제 전체 목록 수
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public int getDeleteCount(int fnIdx, Map<String, Object> param);

	/**
	 * 삭제 전체 목록
	 * 
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Object> getDeleteList(int fnIdx, Map<String, Object> param);

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
	public int delete(int fnIdx, ParamForm parameterMap, int[] deleteIdxs, String regiIp, JSONObject settingInfo)
			throws Exception;

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
	public int restore(int fnIdx, int[] restoreIdxs, String regiIp, JSONObject settingInfo) throws Exception;

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
	public int cdelete(String uploadModulePath, int fnIdx, int[] deleteIdxs, JSONObject settingInfo, JSONObject items,
			JSONArray itemOrder) throws Exception;

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
			JSONObject items, JSONArray itemOrder);

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
	public Map<String, List<Object>> getMultiHashMap(int fnIdx, int keyIdx, JSONObject settingInfo, JSONObject items,
			JSONArray itemOrder);

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
	public Map<String, Object> getFileUpload(String uploadModulePath, int fnIdx, String regiIp, ParamForm parameterMap,
			JSONObject settingInfo, JSONObject items, JSONArray itemOrder) throws Exception;

	/**
	 * 기업 정보 조회
	 * 
	 * @param bplNo
	 * @return
	 */

	public Basket getBasketSelect(String bplNo);

	/**
	 * 기업 자동 대소분류 조회
	 * 
	 * @param bplNo
	 */
	public BskClAtmcDto getBskClAtmcSelect(String bplNo);

	/**
	 * 기업 수동 대소분류 조회
	 * 
	 * @param bplNo
	 */
	public List<BskClPassivDto> getBskClPassivSelect(String bplNo);

	public void getBskClLogSelect(String bplNo);


	public void getBskTrLogSelect(String bplNo);

	/**
	 * 분류 예외 기업 지정 : sw.jang
	 */

	public int addClException(HashMap<String, Object> param, String regiIp) throws Exception;

	/**
	 * 대분류,소분류 목록 조회
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ClLclasDto> getClLclasList() throws Exception;


	/**
	 * 분류 변경(수동대분류)
	 * 
	 * @param param
	 * @param regiIp
	 * @return
	 * @throws Exception
	 */
	public void bskClPassivModify(List<BskClPassivDto> dtos, String regiIp) throws Exception;

	
	
	/**
	 * 해당 기업 훈련리스트 조회
	 */
	public List<TrDataDto> getTr(Criteria cri) throws Exception;
	
	
	/**
	 * 해당 기업 훈련 카운트 조회
	 */
	public int getTrCount(String bplNo);


	/**
	 * 지부지사코드로 해시태그 조회
	 * 
	 * @param insttIdx
	 * @return
	 * @throws Exception
	 */
	public List<HashTagDto> getHashTag(HashMap<String, Object> param) throws Exception;

	public void assignHashTag(List<BskHashTagDto> dtos, String regiIp);

	/**
	 * 분류 예약 등록
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int addResve(ClResveDto dto) throws Exception;

	/**
	 * 분류 예약 조회
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public DataMap getResevCl(Map<String, Object> param) throws Exception;

	/**
	 * 분류 예약 수정
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int editResve(ClResveDto dto) throws Exception;

	/**
	 * 분류 예약 삭제
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int delResve(ClResveDto dto) throws Exception;

	/**
	 * 소분류 수정
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int editClass(ClSclasDto dto) throws Exception;

	/**
	 * 소분류 삭제
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int delClass(ClSclasDto dto) throws Exception;

	/**
	 * 소분류 조회
	 * 
	 * @param clLclasCd
	 * @return
	 */
	public List<ClSclasDto> getClSclasList(Integer clLclasCd);

	/**
	 * 시도 데이터 조회
	 * 
	 * @return
	 */
	public List<ZipCodeDto> getZipcode();

	/**
	 * 시도 데이터로 구군 조회
	 * 
	 * @param selectedCtprvn
	 * @return
	 */
	public List<ZipCodeDto> getSigngu(String selectedCtprvn);

	/**
	 * 해당 기업 해시태그 조회
	 * 
	 * @param bplNo
	 * @return
	 */
	public List<BskHashTagDto> getBskHashtagSelect(String bplNo);

	/**
	 * 해시태그 할당 삭제
	 * 
	 * @param dto
	 * @param remoteAddr
	 */
	public void deleteHashtag(BskHashTagDto dto, String remoteAddr);

	/**
	 * 분류관리 상세조회
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public DataMap getClassify(Map<String, Object> param) throws Exception;

	/**
	 * 분류 로그 조회
	 * 
	 * @param bplNo
	 * @return
	 */
	public List<BskClLogDto> getBskClLogList(String bplNo);

	public List<HashMap<String, Object>> getIndustCd();

	/**
	 * 엑셀 내용 업로드(insert)
	 * 
	 * @param map
	 * @return
	 */
	public int bskDataUpload(HashMap<String, Object> map);

	/**
	 * 엑셀 내용 업로드(merge)
	 * 
	 * @param map
	 * @return
	 */
	public int bskDataUploadMerge(HashMap<String, Object> map);

	/**
	 * 엑셀 파일 업로드
	 * 
	 * @param dto
	 * @param remoteAddr
	 * @return
	 */

	public int bskDataFileUpload(ExcelUploadDto dto, String remoteAddr);

	/**
	 * 엑셀 업로드 파일 데이터 리스트
	 * 
	 * @return
	 */
	public List<ExcelUploadDto> getExcelUploadList(Criteria cri);

	public int bskDataFileResultUpdate(ExcelUploadDto dto);

	/**
	 * 엑셀 업로드 파일 데이터 카운트
	 * 
	 * @return
	 */
	public int getTotalExcelCount();

	/**
	 * 채용정보
	 * 
	 * @return
	 */

	public List<BskRecDto> getRecList(String bplNo);

	/**
	 * 해시태그 등록
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int addHash(HashTagDto hto) throws Exception;
	

	/**
	 * 기관번호 조회
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int getInsttIdx(String sbCd) throws Exception;

	/**
	 * 해시태그 삭제
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int delHash(HashTagDto hto) throws Exception;

	/**
	 * 해시태그 수정
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int editHash(HashTagDto hto) throws Exception;

	/**
	 * 해시태그 상세조회
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public DataMap getHashTag(Map<String, Object> param) throws Exception;

	public List<String> getBplNoList(Criteria cri);

	
	/**
	 * web 기업리스트 조회
	 * @param cri
	 * @return
	 */
	public List<Basket> getWebList(Criteria cri);
	
	/**
	 * web 기업리스트 조회
	 * @param cri
	 * @return
	 */
	public List<Basket> getWebAllList(Criteria cri);

	public List<HashMap<String, String>> getBskFncInfo(String bizrNo);

	



}
