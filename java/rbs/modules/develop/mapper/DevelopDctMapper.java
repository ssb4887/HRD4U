package rbs.modules.develop.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("developDctMapper")
public class DevelopDctMapper extends EgovAbstractMapper{

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.develop.developDctMapper.selectList", param);
    }
	
	/**
	 * 표준개발 신청 목록 가져오기
	 * @param param 검색조건
	 * @return List<Object> 목록정보
	 */
	public List<Object> getDevList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.develop.developDctMapper.selectDevList", param);
	}
	
	/**
	 * 표준개발 신청 목록 가져오기
	 * @param param 검색조건
	 * @return List<Object> 목록정보
	 */
	public DataMap getSummary(Map<String, Object> param){
		return (DataMap)selectOne("rbs.modules.develop.developDctMapper.selectSummary", param);
	}
	
	/**
	 * 미처리 상태인 목록 가져오기(표준, 맞춤 전부다)
	 * @param param 검색조건
	 * @return List<Object> 목록정보
	 */
	public List<Object> getNoHandlingList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.develop.developDctMapper.selectNoHandlingList", param);
	}
	
	/**
	 * 맞춤개발 비용청구 신청 목록 가져오기
	 * @param param 검색조건
	 * @return List<Object> 목록정보
	 */
	public List<Object> getSupportList(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.develop.developDctMapper.getSupportList", param);
	}

	/**
	 * 맞춤개발 비용청구 신청 목록 갯수 가져오기
	 * @param param 검색조건
	 * @return List<Object> 목록정보
	 */
	public int getTotalSupportCount(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.develop.developDctMapper.getSupportListCount", param);
	}
	
	/**
     * 주치의 지원요청 내용 가져오기
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getHelpInfo(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.develop.developDctMapper.selectHelpInfo", param);
	}
	
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.develop.developDctMapper.selectCount", param);
    }
    
    /**
     * 표준개발 신청 목록 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDevTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.develop.developDctMapper.selectDevCount", param);
    }
    
    /**
     * 파일항목 목록
     * @param param
     * @return
     */
	public List<Object> getFileList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.develop.developDctMapper.selectFileList", param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getFileView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.develop.developDctMapper.selectFileView", param);
	}
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.develop.developDctMapper.selectView", param);
	}

	/**
     * 다운로드수 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int updateFileDown(Map<String, Object> param){
        return super.update("rbs.modules.develop.developDctMapper.updateFileDown",param);
    }
    
    /**
     * 권한여부 조회
     * @param param
     * @return
     */
    public int getAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.develop.developDctMapper.authCount", param);
    }
    
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getModify(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.develop.developDctMapper.selectModify", param);
	}

    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getNextId(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.develop.developDctMapper.nextId", param);
    }

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.develop.developDctMapper.insert", param);
    }
    
    /**
     * HRD_DEVELOP_***_CONFM에 승인상태 새로 insert하기
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insertConfm(Map<String, Object> param){
    	return super.insert("rbs.modules.develop.developDctMapper.insertConfm", param);
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.develop.developDctMapper.update",param);
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.develop.developDctMapper.deleteList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.develop.developDctMapper.deleteCount", param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.develop.developDctMapper.delete",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.develop.developDctMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.develop.developDctMapper.cdelete", param);
    }

}