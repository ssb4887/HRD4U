package rbs.modules.ntwrk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.form.ParamForm;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 지역네트워크 > 관할전문가 관리 인터페이스
 * @author minjung
 *
 */
public interface NtwrkExpertService {

	/**
	 * 관할전문가 등록(insert/update)
	 * @param formdata(formData)
	 * @param partnerInsttList(소속기관 목록:해당 전문가를 조회할 수 있는 소속기관들)
	 * @return
	 */
	public Map<String, Object> inserExpert(Map<String, Object> formdata, List<Integer> partnerInsttList);
	
	/**
	 * 관할전문가 목록 불러오기
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getExpertList(Map<String, Object> param);
	
	/**
	 * 관할전문가 목록 카운트
	 * @param param
	 * @return
	 */
	public int getExpertTotalCount(Map<String, Object> param);
	
	/**
	 * 관할전문가 파일 저장
	 * @param attrVO
	 * @param formdata
	 * @param files
	 * @param fileSeq
	 * @return
	 */
	public Map<String, Object> saveExpertFiles(ModuleAttrVO attrVO, Map<String, Object> formdata, List<MultipartFile> files, List<Integer> fileSeq);

	/**
	 * 관할전문가 불러오기
	 * @param param
	 * @return
	 */
	public Map<String, Object> getExpertOne(Map<String, Object> param);
	
	/**
	 * 관할전문가의 소속기관 목록 불러오기
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getExpertPartnerInstts(Map<String, Object> param);
	
	/**
	 * 관할전문가 파일 목록 불러오기
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getExpertFileList(Map<String, Object> param);
	
	/**
	 * 관할전문가 선택 삭제하기
	 * @param param
	 * @return
	 */
	public int deleteExpert(Map<String, Object> param);
	
	/**
	 * 관할전문가 일괄 추가
	 * @param param
	 * @return
	 */
	public int uploadManyExpertData(List<Map<String, Object>> param) throws Exception;
	
}