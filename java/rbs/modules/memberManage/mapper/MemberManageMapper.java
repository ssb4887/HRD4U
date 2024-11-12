package rbs.modules.memberManage.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("memberManageMapper")
public class MemberManageMapper extends EgovAbstractMapper{

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectList", param);
    }
	
	/**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getCenterAuthList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectCenterAuthList", param);
    }
	
	/**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * 기업회원 소속기관 변경신청 목록
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getCorpRegiList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectCorpRegiList", param);
    }
	
	/**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * 공단소속 변경신청 목록
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getEmployRegiList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectEmployRegiList", param);
    }
	
	/**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getAuthList(){
		return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectAuthList");
    }
	
	/**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getCodeList(){
		return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectCodeList");
    }
	
	/**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getGradeList(){
		return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectGradeList");
    }
	
	/**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getAuthGroupList(){
		return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectAuthGroupList");
    }
	
	 /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getCodeList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.regiCodeList", param);
    }
	
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.memberManage.memberManageMapper.selectCount", param);
    }
    
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCenterCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.memberManage.memberManageMapper.selectCenterCount", param);
    }
    
    
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCenterAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.memberManage.memberManageMapper.selectCenterAuthCount", param);
    }
    
    /**
     * 기업회원 소속변경 신청 전체 목록 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCorpRegiCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.memberManage.memberManageMapper.selectCorpRegiCount", param);
    }
    
    /**
     * 공단소속 변경신청 전체 목록 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalEmployRegiCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.memberManage.memberManageMapper.selectEmployRegiCount", param);
    }
    
    /**
     * 데이터의 존재 유무를 확인
     * @param param 검색조건
     * @return int 총갯수
     */
    public int checkData(Map<String, Object> authparam) {
    	return (Integer)selectOne("rbs.modules.memberManage.memberManageMapper.selectCenterDataCount", authparam);
    }
    
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalEmpReqCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.memberManage.memberManageMapper.selectEmpReqCount", param);
    }
    
    /**
     * 기업회원 소속기관 신청 데이터의 존재 유무를 확인
     * @param param 검색조건
     * @return int 총갯수
     */
    public int checkCorpReq(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.memberManage.memberManageMapper.selectCorpReq", param);
    }
    
    /**
     * 민간센터 권한 신청 데이터의 존재 유무를 확인
     * @param param 검색조건
     * @return int 총갯수
     */
    public int checkCenterReq(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.memberManage.memberManageMapper.selectCenterReq", param);
    }
    
    /**
     * 민간센터 적용중인 권한 유무를 확인
     * @param param 검색조건
     * @return int 총갯수
     */
    public int checkCenterApply(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.memberManage.memberManageMapper.selectCenterApply", param);
    }
    
    /**
     * 주치의등록이 되어있는지 확인
     * @param param 검색조건
     * @return int 총갯수
     */
    public int checkEmpRegi(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.memberManage.memberManageMapper.selectEmpRegi", param);
    }
    
    /**
     * 공단소속 신청 데이터의 존재 유무를 확인
     * @param param 검색조건
     * @return int 총갯수
     */
    public int checkEmpReq(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.memberManage.memberManageMapper.selectEmpReq", param);
    }
    
    /**
     * 파일항목 목록
     * @param param
     * @return
     */
	public List<Object> getFileList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectFileList", param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getFileView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.memberManage.memberManageMapper.selectFileView", param);
	}
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.memberManage.memberManageMapper.selectView", param);
	}
	
	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getViewCorp(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.memberManage.memberManageMapper.selectViewCorp", param);
	}
	
	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getPreOrg(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.memberManage.memberManageMapper.selectPreOrg", param);
	}
	
	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getViewOrg(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.memberManage.memberManageMapper.selectViewOrg", param);
	}
	
	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public List<Object> getViewRegi(Map<String, Object> param) {
         return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectViewRegi", param);
	}

	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getViewDoctor(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.memberManage.memberManageMapper.selectViewDoctor", param);
	}
	
	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public List<Object> getDoctorInfo(Map<String, Object> param) {
         return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectDoctorInfo", param);
	}
	
	/**
	 * 전담주치의 상세정보
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getViewCliDoctor(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.memberManage.memberManageMapper.selectViewCliDoctor", param);
	}
	
	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public List<Object> getAuth(Map<String, Object> param) {
         return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectAuth", param);
	}
	
	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public List<Object> getWebReq(Map<String, Object> param) {
         return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectWebReq", param);
	}
	
	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public List<Object> getDctReq(Map<String, Object> param) {
         return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectDctReq", param);
	}
	
	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public List<Object> getEmpDoctor(Map<String, Object> param) {
         return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectEmpDoctor", param);
	}
	
	/**
     * 검색을 위한 지부지사 목록 가져오기
     * @param param 검색조건
     * @return 상세정보
     */
	public List<Object> getOrgInsttList(Map<String, Object> param) {
         return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectOrgInsttList", param);
	}
	
	/**
     * 검색을 위한 지부지사 목록 가져오기
     * @param param 검색조건
     * @return 상세정보
     */
	public List<Object> getInsttList(Map<String, Object> param) {
         return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectInsttList", param);
	}
	
	/**
     * 다운로드수 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int updateFileDown(Map<String, Object> param){
        return super.update("rbs.modules.memberManage.memberManageMapper.updateFileDown",param);
    }
    
    /**
     * 기업회원의 소속을 비활성화
     * @param param 수정정보
     */
    public int updateNotCorp(Map<String, Object> param){
        return super.update("rbs.modules.memberManage.memberManageMapper.updateNotCorp",param);
    }
    
    /**
     * 기업회원의 소속을 일괄변경
     * @param param 수정정보
     */
    public int updateCorpRegion(Map<String, Object> param){
        return super.update("rbs.modules.memberManage.memberManageMapper.updateCorpRegion",param);
    }
    
    /**
     * 민간센터의 권한을 일괄변경
     * @param param 수정정보
     */
    public int updateCenterAuth(Map<String, Object> param){
        return super.update("rbs.modules.memberManage.memberManageMapper.updateCenterAuth",param);
    }
    
    /**
     * 공단소속 변경이 승인될 때 기존에 있는 주치의 데이터 수정
     * @param param 수정정보
     */
    public int updateApplyYnToN(Map<String, Object> param){
        return super.update("rbs.modules.memberManage.memberManageMapper.updateApplyYnToN",param);
    }
    
    /**
     * 공단소속의 소속을 일괄변경
     * @param param 수정정보
     */
    public int updateEmployRegion(Map<String, Object> param){
        return super.update("rbs.modules.memberManage.memberManageMapper.updateEmployRegion",param);
    }
    
    /**
     * 변경된 소속기업의 정보를 HRD_BSK_PSITN_INSTT에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insertCorpRegi(Map<String, Object> param){
    	return super.insert("rbs.modules.memberManage.memberManageMapper.insertCorpRegi", param);
    }
    
    /**
     * 변경된 민간센터의 정보를 RBS_AUTH_MEMBER에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insertCenterAuth(Map<String, Object> param){
    	return super.insert("rbs.modules.memberManage.memberManageMapper.insertCenterAuth", param);
    }
    
    /**
     * 권한여부 조회
     * @param param
     * @return
     */
    public int getAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.memberManage.memberManageMapper.authCount", param);
    }
    
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getModify(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.memberManage.memberManageMapper.selectModify", param);
	}
	
	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getMember(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.memberManage.memberManageMapper.selectMember", param);
	}

    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getNextId(Map<String, Object> param) {
    	return (int)selectOne("rbs.modules.memberManage.memberManageMapper.nextId", param);
    }
    
    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getCenterNextId(Map<String, Object> param) {
    	return (int)selectOne("rbs.modules.memberManage.memberManageMapper.nextCenterId", param);
    }
    
    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getCorpMemNextId(Map<String, Object> authparam) {
    	return (int)selectOne("rbs.modules.memberManage.memberManageMapper.nextCorpMemId", authparam);
    }
    
    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getCenMemNextId(Map<String, Object> authparam) {
    	return (int)selectOne("rbs.modules.memberManage.memberManageMapper.nextCenMemId", authparam);
    }
    
    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getOrgNextId(Map<String, Object> param) {
    	return (int)selectOne("rbs.modules.memberManage.memberManageMapper.nextOrgId", param);
    }
    
    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getEmployNextId(Map<String, Object> param) {
    	return (int)selectOne("rbs.modules.memberManage.memberManageMapper.nextEmpId", param);
    }

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.memberManage.memberManageMapper.insert", param);
    }
    
    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insertOrg(Map<String, Object> param){
    	return super.insert("rbs.modules.memberManage.memberManageMapper.insertOrg", param);
    }
    
    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insertCenter(Map<String, Object> param){
    	return super.insert("rbs.modules.memberManage.memberManageMapper.insertCenter", param);
    }
    
    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insertCenterMember(Map<String, Object> param){
    	return super.insert("rbs.modules.memberManage.memberManageMapper.insertCenterMember", param);
    }

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insertEmploy(Map<String, Object> param){
    	return super.insert("rbs.modules.memberManage.memberManageMapper.insertEmploy", param);
    }
    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.memberManage.memberManageMapper.update",param);
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.deleteList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.memberManage.memberManageMapper.deleteCount", param);
    }
    
    /**
     * 화면에 조회된 정보를 데이터베이스에서 적용 flag 반영
     * @param param 적용정보
     */
    public int apply(Map<String, Object> param){
    	return super.update("rbs.modules.memberManage.memberManageMapper.apply",param);
    }
    
    /**
     * 화면에 조회된 정보를 데이터베이스에서 적용 flag 반영
     * @param param 적용정보
     */
    public int applyCenter(Map<String, Object> param){
    	return super.update("rbs.modules.memberManage.memberManageMapper.applyCenter",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 적용 flag 반영
     * @param param 적용정보
     */
    public int applyAllNotEmp(Map<String, Object> param){
    	return super.update("rbs.modules.memberManage.memberManageMapper.applyAllNotEmp",param);
    }
    /**
     * 화면에 조회된 정보를 데이터베이스에서 적용 flag 반영
     * @param param 적용정보
     */
    public int applyEmp(Map<String, Object> param){
    	return super.update("rbs.modules.memberManage.memberManageMapper.applyEmp",param);
    }
    
    /**
     * 화면에 조회된 정보를 데이터베이스에서 적용 flag 반영
     * @param param 적용정보
     */
    public int applyCenterMember(Map<String, Object> authparam){
    	return super.update("rbs.modules.memberManage.memberManageMapper.applyCenterMember",authparam);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.memberManage.memberManageMapper.delete",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.memberManage.memberManageMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.memberManage.memberManageMapper.cdelete", param);
    }
    
    public void updatePSITN(Map param) {
    	update("rbs.modules.memberManage.memberManageMapper.updatePSITN", param);
    }
    public void updateApplyYNPSITN(Map bpl_no) {
    	update("rbs.modules.memberManage.memberManageMapper.updateApplyYNPSITN", bpl_no);
    }
    public void updateInsttPSITN(Map param) {
    	update("rbs.modules.memberManage.memberManageMapper.updateInsttPSITN", param);
    }
    public void insertPSITNbyDCT(Map param) {
    	insert("rbs.modules.memberManage.memberManageMapper.insertPSITNbyDCT", param);
    }
    public void updateDoctor(Map param) { 
    	update("rbs.modules.memberManage.memberManageMapper.updateDoctor", param);
    }
    
    /**
     * 민간세터 신청 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int centerDelete(Map<String, Object> param){
    	return super.delete("rbs.modules.memberManage.memberManageMapper.centerDelete", param);
    }
    
    /**
     * 현재 적용중인 주치의데이터를 데이터베이스에서 조회
     * @param param 완전삭제정보
     */
    public int applyNotDelete(Map<String, Object> param){
    	return (Integer)selectOne("rbs.modules.memberManage.memberManageMapper.applyNotDelete", param);
    }
    
    /**
     * 본부직원 신청 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int employDelete(Map<String, Object> param){
    	return super.delete("rbs.modules.memberManage.memberManageMapper.employDelete", param);
    }
    
    
	 /**
     * 카카오 알림톡 소속기관 일괄변경 보내기
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getCorpMailList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectCorpMailList",param);
    }
	
	/**
     * 카카오 알림톡 일괄변경 보내기
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getCenterMailList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectCenterMailList",param);
    }
	
	/**
     * 카카오 알림톡 일괄변경 보내기
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getOrgMailList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.memberManage.memberManageMapper.selectOrgMailList",param);
    }
}