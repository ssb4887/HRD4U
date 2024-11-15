package rbs.modules.traing.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("traingMapper")
public class TraingMapper extends EgovAbstractMapper{

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력 - 실시 현황
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.traing.traingMapper.selectList", param);
    }

	/**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력 - 지원 현황
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getSprtList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.traing.traingMapper.selectSprtList", param);
    }
	
	/**
	 * 실시현황 전체 목록 가져오기
	 * @param param 검색조건
	 * @return List<Object> 목록정보
	 */
	public List<Object> getDetailList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.traing.traingMapper.selectDetailList", param);
	}
	
    /**
     * 실시 현황 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.traing.traingMapper.selectCount", param);
    }
    
    /**
     * 지원 현황 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getSprtTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.traing.traingMapper.selectSprtCount", param);
    }
    
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public List<Object> getView(Map<String, Object> param) {
         return (List<Object>)selectList("rbs.modules.traing.traingMapper.selectView", param);
	}
    
    /**
     * 권한여부 조회
     * @param param
     * @return
     */
    public int getAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.traing.traingMapper.authCount", param);
    }
    
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public List<Object> getModify(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.traing.traingMapper.selectModify", param);
	}

    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getNextId(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.traing.traingMapper.nextId", param);
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력 - 등록된 실시 현황 업종코드 리스트
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getIndustCdList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.traing.traingMapper.selectIndustCdList", param);
    }
    
    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력 - 등록된 실시 현황 연도 리스트
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getIndustCdYearList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.traing.traingMapper.selectIndustCdYearList", param);
    }
    
    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.traing.traingMapper.insert", param);
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.traing.traingMapper.update",param);
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.traing.traingMapper.deleteList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.traing.traingMapper.deleteCount", param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.traing.traingMapper.delete",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.traing.traingMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.traing.traingMapper.cdelete", param);
    }
}