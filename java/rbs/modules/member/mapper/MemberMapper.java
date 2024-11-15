package rbs.modules.member.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import rbs.egovframework.mapper.RbsAbstractMapper;

import com.woowonsoft.egovframework.form.DataMap;

/**
 * 일반회원관리에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("memberMapper")
public class MemberMapper extends RbsAbstractMapper{

	/**
	 * 회원약관
	 * @param param
	 * @return
	 */
	public DataMap getMemberClause(Map<String, Object> param) {
        return (DataMap)selectOne("rbs.modules.member.memberMapper.selectMemberClause", param);
	}
	
	/**
	 * 중복확인
	 * @param param
	 * @return
	 */
    public int getDuplicateCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.member.memberTotMapper.duplicateCount", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotTotalCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.member.memberTotMapper.selectCount", param);
    }
    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getTotSearchList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.member.memberTotMapper.selectList", getLangParam(param));
    }
	
    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.member.memberMapper.selectList", getLangParam(param));
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.member.memberMapper.selectCount", param);
    }
    
    /**
     * 주치의 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDoctorCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.member.memberMapper.selectDoctorCount", param);
    }

	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.member.memberMapper.selectView", getLangParam(param));
	}
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	@SuppressWarnings("unchecked")
	public DataMap getModify(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.member.memberMapper.selectModify", param);
	}
	
	public List<Object> getMemberGrupList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.member.memberTotMapper.selectMemberGrupList", param);
    }

	public Map<String, Object> getGrupMemberMap(Map<String, Object> param){
		return selectMap("rbs.modules.member.memberTotMapper.selectGrupMemberList", getLangParam(param), "MEMBER_IDX");
    }

    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public String getNextId(Map<String, Object> param) {
        return (String)selectOne("rbs.modules.member.memberMapper.nextId", param);
    }

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	Object result = super.insert("rbs.modules.member.memberTotMapper.insert", param);
    	if(result != null) {
    		return ((Integer)result).intValue();
    	} else return 0;
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
        return super.update("rbs.modules.member.memberTotMapper.update",param);
    }

	
	/**
	 * 중복확인
	 * @param param
	 * @return
	 */
    public int getSnsDuplicateCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.member.memberTotMapper.snsDuplicateCount", param);
    }
    
    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int snsInsert(Map<String, Object> param){
        return super.insert("rbs.modules.member.memberTotMapper.snsInsert",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdeleteGrp(Map<String, Object> param){
    	return super.delete("rbs.modules.member.memberTotMapper.cdeleteGrp", param);
    }

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insertGrp(Map<String, Object> param){
    	Object result = super.insert("rbs.modules.member.memberTotMapper.insertGrp", param);
    	if(result != null) {
    		return ((Integer)result).intValue();
    	} else return 0;
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.member.memberMapper.deleteList", getLangParam(param));
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.member.memberMapper.deleteCount", param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.member.memberTotMapper.delete",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.member.memberTotMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.member.memberTotMapper.cdelete", param);
    }
    
    /**
     * 아이디 중복 확인
     * @param param 검색조건
     * @return int 총갯수
     */
    
    public int pwupdate(Map<String, Object> param){
        return super.update("rbs.modules.member.memberTotMapper.pwupdate", param);
    }

    public int pwupdate1(Map<String, Object> param){
        return super.update("rbs.modules.member.memberTotMapper.pwupdate1", param);
    }

	public List<Object> getReAgreeList(String dDays){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dDays", dDays);
        return (List<Object>)selectList("rbs.modules.member.memberTotMapper.selectReAgreeList", param);
    }

    public int reAgreeCdelete(){
    	return super.delete("rbs.modules.member.memberTotMapper.reAgreeCdelete");
    }

    /**
     * max key 조회한다.
     * @return int key
     */
    public String getReAgreeMaxMemberIdx() {
        return (String)selectOne("rbs.modules.member.memberTotMapper.reAgreeMaxMemberIdx");
    }

    /**
     * max key 조회한다.
     * @return int key
     */
    public String getMaxMemberIdx() {
        return (String)selectOne("rbs.modules.member.memberTotMapper.maxMemberIdx");
    }
    
}