package rbs.modules.adminForm.service;

import java.util.List;
import java.util.Map;

import com.woowonsoft.egovframework.form.DataMap;

import rbs.modules.basket.dto.ExcelUploadDto;


public interface AdminFormService {

	/**
	 * 방문기업서식 전체 목록
	 */
	public List<Object> getList(Map<String, Object> param);

	/**
	 * 방문기업서식 전체 목록 수
	 */
	public int getTotalCount(Map<String, Object> param);
	
	/**
	 * 공단 담당자 정보 조회
	 */
	public Map getDoc(String memberIdx);
	
	/**
	 * 방문기업서식 상세조회
	 */
	public DataMap getView(Map<String, Object> param);
	
	/**
	 * 방문기업서식 등록처리
	 */
	public int setForm(Map<String, Object> param);

	/**
	 * 방문기업서식 삭제
	 */
	public int delete(Map<String, Object> param);
	
	/**
	 * 파일 업로드
	 */
	public int insertFile(ExcelUploadDto dto, String remoteAddr);

	/**
	 * 파일 수정 업로드
	 */
	public int updateFile(ExcelUploadDto dto, String remoteAddr);

	/**
	 * 파일 상세조회
	 */
	public DataMap getFileView(Map<String, Object> param);
	
}