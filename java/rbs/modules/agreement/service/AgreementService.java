package rbs.modules.agreement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ParamForm;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import rbs.modules.agreement.dto.RegisterFormVO;
import rbs.modules.consulting.dto.Cnsl;


/**
 * 샘플모듈에 관한 인터페이스클래스를 정의한다.
 * @author user
 *
 */
public interface AgreementService {
	
	/**
	 * 전체 목록
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public List<Map> getList(Map param);
	
	/**
	 * 파일 상세조회
	 * @param fnIdx
	 * @param param
	 * @return
	 */
	public DataMap getFileView(int agrem_idx, int fle_idx);
	
	/**
	 * 다운로드 수 수정
	 * @param fnIdx
	 * @param brdIdx
	 * @return
	 * @throws Exception
	 */
	public int updateFileDown(int agrem_idx) throws Exception;
	
	public List<Map> getCenters(String name);
	public void requestAgreement(Map param);
	public int uploadFile(MultipartFile file, int agrem_idx, String ip);
	public String getAgremNo(Map param);
	public Map getRegister(Map param);
	public int registerAgreement(RegisterFormVO vo);
	public int confirmAgreement(Map param);
	public Map getBsk(String bpl_no);

	public void mergeAgremByCnsl(Cnsl cnsl, String regiIp);

	public int getAgremCount(String bplNo);

	public List<HashMap<String, String>> getCompltedAgremList(String bplNo);

}