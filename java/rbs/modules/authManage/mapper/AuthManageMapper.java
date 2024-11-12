package rbs.modules.authManage.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("authManageMapper")
public class AuthManageMapper extends EgovAbstractMapper{

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.authManage.authManageMapper.selectList", param);
    }
	
	  /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getCodeList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.authManage.authManageMapper.selectCodeList", param);
    }
	
	
	/**
     * 로그인한 회원의 authIdx 가져오기(RBS_AUTH_MEMBER)
     * @param param 검색조건
     * @return String authIdx 권한일련번호
     */
    public Integer getAuthIdx(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.authManage.authManageMapper.selectAuthIdx", param);
    }
    
    /**
     * 로그인한 회원의 authIdx, siteId, menuIdx에 따른 권한 가져오기
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getAuthMenu(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.authManage.authManageMapper.selectAuthMenu", param);
	}
	
	/**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getAuthGrant(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.authManage.authManageMapper.selectAuthGrant", param);
    }
	
	/**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getAuthList(){
		return (List<Object>)selectList("rbs.modules.authManage.authManageMapper.selectAuthList");
    }
	
	/**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @return List<Object> 목록정보
     */
	public List<Object> getAuthGroupList(){
		return (List<Object>)selectList("rbs.modules.authManage.authManageMapper.selectAuthGroupList");
    }
	
	
	/**
	 * 권한 그룹리스트를 가져온다
	 * @author 이동근
	 * @param param "not in " 에 부합하는 조건
	 * @return List<Object> 목록정보
	 */
	public List<Object> getAuthGroupListWithInCondition(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.authManage.authManageMapper.selectAuthGroupListWithInCondition", param);
	}
	
	
	
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.authManage.authManageMapper.selectCount", param);
    }
    
    /**
     * 파일항목 목록
     * @param param
     * @return
     */
	public List<Object> getFileList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.authManage.authManageMapper.selectFileList", param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getFileView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.authManage.authManageMapper.selectFileView", param);
	}
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.authManage.authManageMapper.selectView", param);
	}

	/**
     * 다운로드수 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int updateFileDown(Map<String, Object> param){
        return super.update("rbs.modules.authManage.authManageMapper.updateFileDown",param);
    }
    
    /**
     * 권한여부 조회
     * @param param
     * @return
     */
    public int getAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.authManage.authManageMapper.authCount", param);
    }
    
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getModify(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.authManage.authManageMapper.selectModify", param);
	}
	
	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getMember(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.authManage.authManageMapper.selectMember", param);
	}

    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getNextId(Map<String, Object> param) {
    	return (int)selectOne("rbs.modules.authManage.authManageMapper.nextId", param);
    }

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.authManage.authManageMapper.insert", param);
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.authManage.authManageMapper.update",param);
    }
    
    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int updateAll(Map<String, Object> param){
    	return super.update("rbs.modules.authManage.authManageMapper.updateAll",param);
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.authManage.authManageMapper.deleteList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.authManage.authManageMapper.deleteCount", param);
    }
    
    /**
     * 화면에 조회된 정보를 데이터베이스에서 적용 flag 반영
     * @param param 적용정보
     */
    public int apply(Map<String, Object> param){
    	return super.update("rbs.modules.authManage.authManageMapper.apply",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.authManage.authManageMapper.delete",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.authManage.authManageMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.authManage.authManageMapper.cdelete", param);
    }
    
    /**
     * 카카오 알림톡 등록,수정  보내기
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getMailList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.authManage.authManageMapper.selectMailList",param);
    }
	
	 /**
     * 카카오 알림톡 일괄변경 보내기
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getMultiMailList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.authManage.authManageMapper.selectMultiMailList",param);
    }
}