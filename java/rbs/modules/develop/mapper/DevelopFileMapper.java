package rbs.modules.develop.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import rbs.egovframework.mapper.RbsAbstractMapper;

/**
 * 샘플모듈 파일에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("developFileMapper")
public class DevelopFileMapper extends RbsAbstractMapper{

    public Map<String, List<Object>> getMapList(Map<String, Object> param) {
        List<Object> list = (List<Object>)selectList("rbs.modules.develop.developFileMapper.selectList", getLangParam(param));
    	return selectMapList(list, "ITEM_ID");
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.develop.developFileMapper.selectList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.develop.developFileMapper.selectCount", param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
	public DataMap getView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.develop.developFileMapper.selectView", param);
	}
     */
	
	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
	 * @param param
	 * @return
	 */
	public DataMap getFileView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.develop.developFileMapper.selectFileView", param);
	}

	/**
     * 다운로드수 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int updateFileDown(Map<String, Object> param){
        return super.update("rbs.modules.develop.developFileMapper.updateFileDown",param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getModify(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.develop.developFileMapper.selectModify", param);
	}

    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getNextId(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.develop.developFileMapper.nextId", param);
    }

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.develop.developFileMapper.insert", param);
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
        return super.update("rbs.modules.develop.developFileMapper.update",param);
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.develop.developFileMapper.deleteList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.develop.developFileMapper.deleteCount", param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.develop.developFileMapper.delete",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.develop.developFileMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.develop.developFileMapper.cdelete", param);
    }
}