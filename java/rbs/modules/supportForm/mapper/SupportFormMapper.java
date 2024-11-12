package rbs.modules.supportForm.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("supportFormMapper")
public class SupportFormMapper extends EgovAbstractMapper{

    /**
     * 서식그룹 전체 목록 조회
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.supportForm.supportFormMapper.selectList", param);
    }
	
	/**
	 * 서식그룹 전체 갯수 조회
	 */
	public int getTotalCount(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.supportForm.supportFormMapper.selectCount", param);
	}

    /**
     * 서식그룹 상세조회
     */
	public DataMap getView(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.supportForm.supportFormMapper.selectView", param);
	}
	
    /**
     * 방문기업서식 목록
     */
	public List<Object> getFormList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.supportForm.supportFormMapper.selectFormList", param);
    }
	
	/**
	 * 방문기업서식 목록 수
	 */
	public int getFormCount(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.supportForm.supportFormMapper.selectFormCount", param);
	}
	
    /**
     * 서식그룹 저장
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.supportForm.supportFormMapper.insert", param);
    }

    /**
     * 서식그룹 수정
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.supportForm.supportFormMapper.update",param);
    }

    /**
     * 서식그룹 삭제
     */
    public int delete(Map<String, Object> param){
    	return super.delete("rbs.modules.supportForm.supportFormMapper.delete",param);
    }
	
    /**
     * 서식그룹 방문기업서식 IDX  조회
     */
    public List<Map<String, Object>> getFormIdx(Map<String, Object> param) {
    	return selectList("rbs.modules.supportForm.supportFormMapper.getFormIdx", param);
    }

    /**
	 * 서식그룹별 방문기업서식 IDX 저장
	 */
    public int insertFormIdx(Map<String, Object> param){
    	return super.insert("rbs.modules.supportForm.supportFormMapper.insertFormIdx", param);
    }

    /**
     * 서식그룹별 방문기업서식 IDX 수정
     */
    public int deleteFormIdx(Map<String, Object> param){
    	return super.delete("rbs.modules.supportForm.supportFormMapper.deleteFormIdx", param);
    }
    
}