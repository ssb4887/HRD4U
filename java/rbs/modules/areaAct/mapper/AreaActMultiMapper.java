package rbs.modules.areaAct.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import rbs.egovframework.mapper.RbsAbstractMapper;

/**
 * 샘플모듈 다중값에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("areaActMultiMapper")
public class AreaActMultiMapper extends RbsAbstractMapper{

    public Map<String, List<Object>> getMapList(Map<String, Object> param) {
        List<Object> list = (List<Object>)selectList("rbs.modules.areaAct.areaActMultiMapper.selectList", param);
    	return selectMapList(list, "ITEM_ID");
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.areaAct.areaActMultiMapper.selectList", param);
    }
	
	/**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public DataMap getBskList(){
        return (DataMap)selectOne("rbs.modules.areaAct.areaActMultiMapper.selectBsk");
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.areaAct.areaActMultiMapper.selectCount", param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
	public DataMap getView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.areaAct.areaActMultiMapper.selectView", param);
	}
     */
	
	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
	 * @param param
	 * @return
	 */
	public DataMap getFileView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.areaAct.areaActMultiMapper.selectFileView", param);
	}
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getModify(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.areaAct.areaActMultiMapper.selectModify", param);
	}

    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getNextId(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.areaAct.areaActMultiMapper.nextId", param);
    }

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장(지역활성화계획 기관)
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.areaAct.areaActMultiMapper.insert", param);
    }
    
    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장(지역활성화계획 기업바구니)
     * @param param 등록정보
     * @return String 등록결과
     */
    public int bskInsert(Map<String, Object> param){
    	return super.insert("rbs.modules.areaAct.areaActMultiMapper.bskInsert", param);
    }
    
    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장(지역활성화계획 기업바구니)
     * @param param 등록정보
     * @return String 등록결과
     */
    public int policyInsert(Map<String, Object> param){
    	return super.insert("rbs.modules.areaAct.areaActMultiMapper.policyInsert", param);
    }
    
    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장(지역활성화계획 기업바구니)
     * @param param 등록정보
     * @return String 등록결과
     */
    public int bsnsInsert(Map<String, Object> param){
    	return super.insert("rbs.modules.areaAct.areaActMultiMapper.bsnsInsert", param);
    }
    
    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장(지역활성화계획 기업바구니)
     * @param param 등록정보
     * @return String 등록결과
     */
    public int trngInsert(Map<String, Object> param){
    	return super.insert("rbs.modules.areaAct.areaActMultiMapper.trngInsert", param);
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
        return super.update("rbs.modules.areaAct.areaActMultiMapper.update",param);
    }
    
    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int bskUpdate(Map<String, Object> param){
        return super.update("rbs.modules.areaAct.areaActMultiMapper.bskUpdate",param);
    }
    
    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int policyUpdate(Map<String, Object> param){
        return super.update("rbs.modules.areaAct.areaActMultiMapper.policyUpdate",param);
    }
    
    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int distriUpdate(Map<String, Object> param){
        return super.update("rbs.modules.areaAct.areaActMultiMapper.distriUpdate",param);
    }
    
    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int trainingUpdate(Map<String, Object> param){
        return super.update("rbs.modules.areaAct.areaActMultiMapper.trainingUpdate",param);
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.areaAct.areaActMultiMapper.deleteList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.areaAct.areaActMultiMapper.deleteCount", param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.areaAct.areaActMultiMapper.delete",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.areaAct.areaActMultiMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.areaAct.areaActMultiMapper.cdelete", param);
    }
    
    
    /**
     * 지역활성화계획 기관 삭제처리
     * @param param 수정정보
     */
    public int mulDelete(Map<String, Object> param){
        return super.update("rbs.modules.areaAct.areaActMultiMapper.mulDelete",param);
    }
    
    /**
     * 지역활성화계획 기관 삭제처리
     * @param param 수정정보
     */
    public int bsnsDelete(Map<String, Object> param){
        return super.update("rbs.modules.areaAct.areaActMultiMapper.bsnsDelete",param);
    }
    
    /**
     * 지역활성화계획 기관 삭제처리
     * @param param 수정정보
     */
    public int policyDelete(Map<String, Object> param){
        return super.update("rbs.modules.areaAct.areaActMultiMapper.policyDelete",param);
    }
    
    /**
     * 지역활성화계획 기관 삭제처리
     * @param param 수정정보
     */
    public int trngDelete(Map<String, Object> param){
        return super.update("rbs.modules.areaAct.areaActMultiMapper.trngDelete",param);
    }
    
    
    /**
     * 지역활성화계획 기관 일치하는 데이터 존재 시 수정 처리, 없으면 등록 처리 
     * @param param 등록정보
     * @return String 등록결과
     */
    public int mulInsert(Map<String, Object> param){
    	return super.insert("rbs.modules.areaAct.areaActMultiMapper.mulInsert", param);
    }
}