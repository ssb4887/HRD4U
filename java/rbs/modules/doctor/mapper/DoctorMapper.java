package rbs.modules.doctor.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("doctorMapper")
public class DoctorMapper extends EgovAbstractMapper{

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.doctor.doctorMapper.selectList", param);
    }
	
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.doctor.doctorMapper.selectCount", param);
    }
    
    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getActList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.doctor.doctorMapper.selectActList", param);
    }
	
    /**
     * 활동내역 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getActCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.doctor.doctorMapper.selectActCount", param);
    }
    
    /**
     * 파일항목 목록
     * @param param
     * @return
     */
	public List<Object> getFileList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.doctor.doctorMapper.selectFileList", param);
    }
	
	/**
     * 현재 사용 중인 주치의 요건 목록
     * @param param
     * @return
     */
	public List<Object> getReqMngList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.doctor.doctorMapper.selectReqMngList", param);
    }
	
	/**
     * 주치의 요건 목록
     * @param param
     * @return
     */
	public List<Object> getReqList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.doctor.doctorMapper.selectReqList", param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getFileView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.doctor.doctorMapper.selectFileView", param);
	}
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.doctor.doctorMapper.selectView", param);
	}

	/**
     * 다운로드수 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int updateFileDown(Map<String, Object> param){
        return super.update("rbs.modules.doctor.doctorMapper.updateFileDown",param);
    }
    
    /**
     * 권한여부 조회
     * @param param
     * @return
     */
    public int getAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.doctor.doctorMapper.authCount", param);
    }
    
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getModify(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.doctor.doctorMapper.selectModify", param);
	}

    /**
     * 해당 주치의의 소속기관이 변경되었는지 확인한다.
     * @return int key
     */
    public DataMap getIsChange(Map<String, Object> param) {
    	return (DataMap)selectOne("rbs.modules.doctor.doctorMapper.selectIsChange", param);
    }

    /**
     * 회원 목록
     * @param param
     * @return
     */
	public List<Object> getMemberList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.doctor.doctorMapper.selectMemberList", param);
    }
	
	/**
     * 지부지사 관할구역 목록
     * @param param
     * @return
     */
	public List<Object> getBlockList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.doctor.doctorMapper.selectBlockList", param);
    }
	
	/**
	 * 지부지사 관할구역에 배정된 주치의 인원 수
	 * @param param
	 * @return
	 */
	public List<Object> getDoctorList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.doctor.doctorMapper.selectDoctorList", param);
	}
	
	/**
     * 지부지사 관할구역 목록
     * @param param
     * @return
     */
	public List<Object> getInsttList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.doctor.doctorMapper.selectInsttList", param);
    }
    
	/**
     * 회원 목록 갯수 조회한다.
     * @return int key
     */
    public int getMemberCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.doctor.doctorMapper.selectCountMember", param);
    }
	
    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getNextId(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.doctor.doctorMapper.nextId", param);
    }
    
    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.doctor.doctorMapper.insert", param);
    }
    
    /**
     * 주치의 요건 입력
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insertReq(Map<String, Object> param){
    	return super.insert("rbs.modules.doctor.doctorMapper.insertReq", param);
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.doctor.doctorMapper.update",param);
    }
    
    /**
     * 주치의 요건 수정
     * @param param 수정정보
     */
    public int updateReq(Map<String, Object> param){
    	return super.update("rbs.modules.doctor.doctorMapper.updateReq",param);
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.doctor.doctorMapper.deleteList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.doctor.doctorMapper.deleteCount", param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.doctor.doctorMapper.delete",param);
    }
    
    /**
     * 주치의 요건 삭제
     * @param param 삭제정보
     */
    public int deleteReq(Map<String, Object> param){
    	return super.update("rbs.modules.doctor.doctorMapper.deleteReq",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.doctor.doctorMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.doctor.doctorMapper.cdelete", param);
    }
    
    /**
     * 주치의 요건 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdeleteReq(Map<String, Object> param){
    	return super.delete("rbs.modules.doctor.doctorMapper.cdeleteReq", param);
    }
}