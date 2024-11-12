package rbs.modules.busisSelect.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("busisSelectMapper")
public class BusisSelectMapper extends EgovAbstractMapper{

    /**
     * 방문기업관리 이력 조회
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.busisSelect.busisSelectMapper.selectList", param);
    }
	
	/**
	 * 방문기업관리 이력 갯수 조회
	 */
	public int getTotalCount(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.busisSelect.busisSelectMapper.selectCount", param);
	}

	/**
	 * 기업바구니 목록 조회
	 */
	public List<Object> getBskList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.busisSelect.busisSelectMapper.selectBskList", param);
	}
	
	/**
	 * 기업바구니 갯수 조회
	 */
	public int getBskCount(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.busisSelect.busisSelectMapper.selectBskCount", param);
	}
	
	/**
	 * 방문기업관리 상세조회
	 */
	public Map<String, Object> getSptjdgns(Map<String, Object> param) {
		return selectOne("rbs.modules.busisSelect.busisSelectMapper.selectSptjdgns", param);
	}

	/**
	 * 방문기업서식 목록 조회
	 */
	public List<Object> getSupportList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.busisSelect.busisSelectMapper.selectSupportList", param);
	}
	
	/**
	 * 방문기업서식 갯수 조회
	 */
	public int getSupportCount(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.busisSelect.busisSelectMapper.selectSupportCount", param);
	}
		
	/**
	 * 방문기업서식 상세 조회
	 */
	public DataMap getSptj(Map<String, Object> param){
		return selectOne("rbs.modules.busisSelect.busisSelectMapper.selectSptj", param);
	}
	
	/**
	 * 전자서명문서 발행여부 조회
	 */
	public DataMap getPublished(Map<String, Object> param){
		return selectOne("rbs.modules.busisSelect.busisSelectMapper.selectPublish", param);
	}
	
	/**
	 * 기업정보 상세 조회
	 */
	public DataMap getBpl(Map<String, Object> param){
		return selectOne("rbs.modules.busisSelect.busisSelectMapper.selectBpl", param);
	}
	
	/**
	 * 훈련이력 조회
	 */
	public DataMap getTr(Map<String, Object> param){
		return selectOne("rbs.modules.busisSelect.busisSelectMapper.selectTr", param);
	}
	
	/**
     * 소속기관 목록
     */
	public List<Object> getInsttList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.busisSelect.busisSelectMapper.selectInsttList", param);
    }
	
	/**
	 * 업종 목록
	 */
	public List<HashMap<String, Object>> getIndustCd() {
		return selectList("rbs.modules.busisSelect.busisSelectMapper.industCdSelectList");
	}

	/**
	 * 업종 목록
	 */
	public DataMap getLSclas(Map<String, Object> param) {
		return selectOne("rbs.modules.busisSelect.busisSelectMapper.selectLSclas", param);
	}

	/**
	 * 자동분류 조회
	 */
	public DataMap getALSclas(Map<String, Object> param) {
		return selectOne("rbs.modules.busisSelect.busisSelectMapper.selectALSclas", param);
	}

	/**
	 * 수동분류 조회
	 */
	public DataMap getPLSclas(Map<String, Object> param) {
		return selectOne("rbs.modules.busisSelect.busisSelectMapper.selectPLSclas", param);
	}

    /**
     * 방문기업관리 등록
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.busisSelect.busisSelectMapper.insert", param);
    }

    /**
     * 방문기업관리 수정
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.busisSelect.busisSelectMapper.update",param);
    }
	
    /**
     * 권한여부 조회
     * @param param
     * @return
     */
    public int getAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.busisSelect.busisSelectMapper.authCount", param);
    }
}