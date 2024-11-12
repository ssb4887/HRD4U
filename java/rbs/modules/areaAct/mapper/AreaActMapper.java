package rbs.modules.areaAct.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("areaActMapper")
public class AreaActMapper extends EgovAbstractMapper{

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.areaAct.areaActMapper.selectList", param);
    }
	
	/**
     * 등록된 지부지사 목록
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getInsttList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.areaAct.areaActMapper.selectInsttList", param);
    }
	
	/**
     * 집계 데이터 목록 가져오기
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getAccumList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.areaAct.areaActMapper.selectAccumList", param);
    }
	
	/**
     * 년도별 기업수 목록 가져오기 
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getYearList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.areaAct.areaActMapper.selectYearList", param);
    }
	
	/**
     * 등록된 지부지사 목록
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getInstt(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.areaAct.areaActMapper.selectInstt", param);
    }
	
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.areaAct.areaActMapper.selectCount", param);
    }
    
    /**
     * 파일항목 목록
     * @param param
     * @return
     */
	public List<Object> getFileList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.areaAct.areaActMapper.selectFileList", param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getFileView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.areaAct.areaActMapper.selectFileView", param);
	}
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.areaAct.areaActMapper.selectView", param);
	}

	/**
     * 다운로드수 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int updateFileDown(Map<String, Object> param){
        return super.update("rbs.modules.areaAct.areaActMapper.updateFileDown",param);
    }
    
    /**
     * 권한여부 조회
     * @param param
     * @return
     */
    public int getAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.areaAct.areaActMapper.authCount", param);
    }
    
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getModify(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.areaAct.areaActMapper.selectModify", param);
	}
	
	 /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getBskData(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.areaAct.areaActMapper.selectBskData", param);
	}
	
	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getRegi(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.areaAct.areaActMapper.selectRegi", param);
	}

    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getNextId(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.areaAct.areaActMapper.nextId", param);
    }
    
    /**
     * 지역활성화 기업바구니 게시물 key 조회한다.
     * @return int key
     */
    public int getNextSn(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.areaAct.areaActMapper.nextSn", param);
    }

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.areaAct.areaActMapper.insert", param);
    }
    
    /**
     * 등록기간을 등록하여 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int regiInsert(Map<String, Object> param){
    	return super.insert("rbs.modules.areaAct.areaActMapper.regiInsert", param);
    }
    
    
    /**
     * 등록기간인지 확인
     * @param param
     * @return
     */
    public int getRegiDateChk() {
    	return (Integer)selectOne("rbs.modules.areaAct.areaActMapper.regiDateChk");
    }
    
    
    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int planUpdate(Map<String, Object> param){
    	return super.update("rbs.modules.areaAct.areaActMapper.planUpdate",param);
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.areaAct.areaActMapper.update",param);
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.areaAct.areaActMapper.deleteList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.areaAct.areaActMapper.deleteCount", param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.areaAct.areaActMapper.delete",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.areaAct.areaActMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.areaAct.areaActMapper.cdelete", param);
    }
}