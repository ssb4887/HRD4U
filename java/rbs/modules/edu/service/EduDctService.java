package rbs.modules.edu.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.woowonsoft.egovframework.form.ModuleAttrVO;

/**
 * (dct) 교육과정 관리
 * 1. 지역네트워크 관리 > CEO 교육과정 관리
 * 2. 행정지원 > 주치의 > 주치의 교육내역
 * @author minjung
 *
 */
public interface EduDctService {

	/**
	 * 교육과정 저장
	 * @param param
	 * @return
	 */
	public Map<String, Object> insertEdc(Map<String, Object> param);

	/**
	 * 교육과정 관련 파일 저장
	 * @param attrVO
	 * @param formdata
	 * @param files
	 * @param fileSeq
	 * @return
	 */
	public Map<String, Object> saveEdcFiles(ModuleAttrVO attrVO, Map<String, Object> formdata, List<MultipartFile> files, List<Integer> fileSeq);

	/**
	 * 교육과정 목록 불러오기
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getEdcList(Map<String, Object> param);
	
	/**
	 * 교육과정 목록 총 갯수
	 * @param param
	 * @return
	 */
	public int getEdcTotalCount(Map<String, Object> param);
	
	/**
	 * 교육과정 선택 삭제
	 * @param param
	 * @return
	 */
	public int deleteEdc(Map<String, Object> param);
	
	/**
	 * 교육과정 상세 가져오기
	 * @param param
	 * @return
	 */
	public Map<String, Object> getEdc(Map<String, Object> param);
	
	/**
	 * 교육과정 첨부파일 가져오기
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getEdcFileList(Map<String, Object> param);
	
	/**
	 * 교육과정 첨부파일 다운로드
	 * @param param
	 * @return
	 */
	public Map<String, Object> getEdcFileOne(Map<String, Object> param);
	
	/**
	 * 회원 목록 검색하기
	 * @param param
	 * @return
	 */
	public List<Object> getMemberList(Map<String, Object> param);
	
	/**
	 * 교육과정 신청자 등록하기(INSERT/UPDATE)
	 * @param param
	 * @return
	 */
	public int insertEdcMember(Map<String, Object> param);
	
	/**
	 * 교육과정 신청자 엑셀 일괄업로드
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> insertEdcMembers(List<Map<String, Object>> param) throws Exception;
	
	/**
	 * 교육과정 신청자 목록 가져오기
	 * @param param
	 * @return
	 */
	public List<Object> getEdcMemberList(Map<String, Object> param);
	
	/**
	 * 교육과정 신청자 총 카운트 수
	 * @param param
	 * @return
	 */
	public int getEdcMemberListCount(Map<String, Object> param);
	
	/**
	 * 교육과정 신청자 선택 삭제
	 * @param param
	 * @return
	 */
	public int deleteEdcMembers(Map<String, Object> param);
	
	/**
	 * 교육과정 신청자 상태 변경(UPDATE)
	 * @param param
	 * @return
	 */
	public int changeEdcMemberStatus(Map<String, Object> param);
	
	/**
	 * 교육과정 신청자 상세 보기
	 * @param param
	 * @return
	 */
	public Map<String, Object> getEdcMember(Map<String, Object> param);

	/**
	 * 교육과정 신청자 상태 일괄변경(신청상태:CONFM_STATUS, 참석여부:ATT_YN)
	 * @param param
	 * @return
	 */
	public int changeManyEdcMemberStatus(Map<String, Object> param);
	
	/**
	 * 수료증 발급하기(발급번호:현재날짜의 YYYYMM-EDC_IDX-SEQ)
	 * @param param
	 * @return
	 */
	public int issueCertificate(Map<String, Object> param);

	/**
	 * 교육과정 신청 이력 카운트
	 * @param param
	 * @return
	 */
	public int getMyEdcHistoryCount(Map<String, Object> param);
	
	/**
	 * 교육과정 신청 이력 목록 불러오기
	 * @param param
	 * @return
	 */
	public List<Object> getMyEdcHistoryList(Map<String, Object> param);
	
	/**
	 * 교육과정 일괄 업로드
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> insertManyEdc(List<Map<String, Object>> param) throws Exception;
	
	/**
	 * 교육과정 목록 불러오기(엑셀다운로드용)
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getEdcListForExcel(Map<String, Object> param);
	
	/**
	 * 수료증 발급취소하기
	 * @param param
	 * @return
	 */
	public int deleteCertificate(Map<String, Object> param) throws Exception;
	
}
