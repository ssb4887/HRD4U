package rbs.modules.taskhub.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import rbs.egovframework.LoginVO;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("taskhubMapper")
public class TaskhubMapper extends EgovAbstractMapper{

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.taskhub.taskhubMapper.selectList", param);
    }
	
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.taskhub.taskhubMapper.selectCount", param);
    }
    
    /**
     * 파일항목 목록
     * @param param
     * @return
     */
	public List<Object> getFileList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.taskhub.taskhubMapper.selectFileList", param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getFileView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.taskhub.taskhubMapper.selectFileView", param);
	}
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.taskhub.taskhubMapper.selectView", param);
	}

	/**
     * 다운로드수 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int updateFileDown(Map<String, Object> param){
        return super.update("rbs.modules.taskhub.taskhubMapper.updateFileDown",param);
    }
    
    /**
     * 권한여부 조회
     * @param param
     * @return
     */
    public int getAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.taskhub.taskhubMapper.authCount", param);
    }
    
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getModify(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.taskhub.taskhubMapper.selectModify", param);
	}

    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getNextId(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.taskhub.taskhubMapper.nextId", param);
    }

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.taskhub.taskhubMapper.insert", param);
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.taskhub.taskhubMapper.update",param);
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.taskhub.taskhubMapper.deleteList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.taskhub.taskhubMapper.deleteCount", param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.taskhub.taskhubMapper.delete",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.taskhub.taskhubMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.taskhub.taskhubMapper.cdelete", param);
    }
    public Map getBSKcorp(String member_idx) {
    	return (Map)selectOne("rbs.modules.taskhub.taskhubMapper.getBSKcorp", member_idx);
    }
    public List<Map> getNotice() {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getNotice");
    }
    public List<Map> getHRDRoom() {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getHRDRoom");
    }
    public List<Map> getProgressBSC(String bpl_no) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getProgressBSC", bpl_no);
    }
    public List<Map> getProgressCNSL(Map param) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getProgressCNSL", param);
    }
    public Map getCntBPL(String bpl_no) {
    	return (Map) selectOne("rbs.modules.taskhub.taskhubMapper.getCntBPL", bpl_no);
    }
    public List<Map> getProgressClinic(String bpl_no) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getProgressClinic", bpl_no);
    }
    public Map getCntCenter(String member_idx) {
    	return (Map) selectOne("rbs.modules.taskhub.taskhubMapper.getCntCenter", member_idx);
    }
    public List<Map> getAgreement(String member_idx) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getAgreement", member_idx);
    }
    public int getNextIdSptjReq(Map param) {
    	return (int)selectOne("rbs.modules.taskhub.taskhubMapper.getNextIdSptjReq", param);
    }
    public int putSptjReq(Map param) {
    	return insert("rbs.modules.taskhub.taskhubMapper.putSptjReq", param);
    }
    public List<Map> getSptjReqListDoc(Map param) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getSptjReqListDoc", param);
    }
    public List<Map> getSptjReqListBPL(Map param) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getSptjReqListBPL", param);
    }
    public List<Map> getSptjReqList(Map param) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getSptjReqList", param);
    }
    public void updateStpjReq(Map param) {
    	update("rbs.modules.taskhub.taskhubMapper.updateStpjReq", param);
    }
    public String getMemberIdx(String req_idx) {
    	return (String) selectOne("rbs.modules.taskhub.taskhubMapper.getMemberIdx", req_idx);
    }
    public LoginVO getLoginMap(String req_idx) {
    	return (LoginVO) selectOne("rbs.modules.taskhub.taskhubMapper.getLoginMap", req_idx);
    }
    public void finishStpjReq(Map param) {
    	update("rbs.modules.taskhub.taskhubMapper.finishStpjReq", param);
    }
    public List<Map> getProgramHRDBsis(Map param) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getProgramHRDBsis", param);
    }
    public List<Map> getProgramSojt(Map param) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getProgramSojt", param);
    }
    public List<Map> getCorpCenters() {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getCorpCenters");
    }
    public List<Map> getCorpCorps(Map param) {
    	/* 2023.11.24
    	 * 전체 프로그램 현황을 조회하기 위해 outer join을 했음.
    	 * 실제 실적이 있는 관리번호를 union으로 중복제거하여 left 쪽의 크기를 줄여서 최적화 함(k 이름의 서브쿼리). 
    	 * 따라서 실적이 있는 관리번호의 갯수가 늘어날수록, 운영 시간이 지남에 따라 성능 저하가 올 수 있음.
    	 * 추후 성능이 저하된다면, SQL 레벨에서 쿼리로 한번에 처리하는거보다, inner join을 다섯번 해서 java레벨에서 모으는게 낫지 않을까? 라는 생각을 해봄.
    	*/
    	return selectList("rbs.modules.taskhub.taskhubMapper.getCorpCorps", param);
    }
    public List<Map> getHashtagsOutOfBplNos(List<String> list) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getHashtagsOutOfBplNos", list);
    }
    public List<Map> costInquiry(Map param) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.costInquiry", param);
    }
    public List<Map> getDoctorIntel(Map param) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getDoctorIntel", param);
    }
    public Map dashboardCount(Map param) {
    	return (Map)selectOne("rbs.modules.taskhub.taskhubMapper.dashboardCount", param);
    }
    public Map dashboardCountConsultant(Map param) {
    	return (Map)selectOne("rbs.modules.taskhub.taskhubMapper.dashboardCountConsultant", param);
    }
    public List<Map> getBoard(Map param) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getBoard", param);
    }
    public List<Map> getBsisCorpo(Map param) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getBsisCorpo", param);
    }
    public List<Map> getCnslCorpo(Map param) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getCnslCorpo", param);
    }
    public List<Map> getClinicCorpo(Map param) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getClinicCorpo", param);
    }
    public List<Map> getSOJTCorpo(Map param) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getSOJTCorpo", param);
    }
    public List<Map> getProgramCnsl(Map param) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getProgramCnsl", param);
    }
    public List<Map> getProgramClinic(Map param) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.getProgramClinic", param);
    }
    public List<Map> delayedJobs(Map param) {
    	return selectList("rbs.modules.taskhub.taskhubMapper.delayedJobs", param);
    }
}