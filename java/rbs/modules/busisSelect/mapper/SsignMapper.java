package rbs.modules.busisSelect.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import rbs.modules.consulting.mapper.EgovAbstractMapper2;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("ssignMapper")
public class SsignMapper extends EgovAbstractMapper2{

	/**
	 * 전자서명 등록 정보 조회
	 */
	public Map<String, Object> getSSInfo(String formatId) {
		return selectOne("rbs.modules.busisSelect.ssignMapper.selectSSInfo", formatId);
	}
	
}