package rbs.modules.agreement.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import rbs.modules.consulting.dto.Cnsl;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("agreementMapper")
public class AgreementMapper extends EgovAbstractMapper{

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Map> getList(Map param){
		return selectList("rbs.modules.agreement.agreementMapper.selectList", param);
    }
	    
    /**
     * 파일항목 목록
     * @param param
     * @return
     */
	public List<Object> getFileList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.agreement.agreementMapper.selectFileList", param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getFileView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.agreement.agreementMapper.selectFileView", param);
	}
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.agreement.agreementMapper.selectView", param);
	}

	/**
     * 다운로드수 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int updateFileDown(Map<String, Object> param){
        return super.update("rbs.modules.agreement.agreementMapper.updateFileDown",param);
    }
    
    /**
     * 권한여부 조회
     * @param param
     * @return
     */
    public int getAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.agreement.agreementMapper.authCount", param);
    }
    
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getModify(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.agreement.agreementMapper.selectModify", param);
	}

    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getNextId(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.agreement.agreementMapper.nextId", param);
    }

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.agreement.agreementMapper.insert", param);
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.agreement.agreementMapper.update",param);
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.agreement.agreementMapper.deleteList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.agreement.agreementMapper.deleteCount", param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.agreement.agreementMapper.delete",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.agreement.agreementMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.agreement.agreementMapper.cdelete", param);
    }
    
    public List<Map> getCenters(String name) {
    	return selectList("rbs.modules.agreement.agreementMapper.getCenters", name);
    }
    
    public void requestAgreement(Map param) {
    	insert("rbs.modules.agreement.agreementMapper.requestAgreement", param);
    }
    
    public String getMemberIdxByPrvtcntrno(Map param) {
    	return (String)selectOne("rbs.modules.agreement.agreementMapper.getMemberIdxByPrvtcntrno", param);
    }
    
    public int fileUpload(Map param) {
    	return insert("rbs.modules.agreement.agreementMapper.fileUpload", param);
    }
    
    public String getAgremNo(Map param) {
    	return (String)selectOne("rbs.modules.agreement.agreementMapper.getAgremNo", param);
    }
    
    public Map getRegister(Map param) {
    	return (Map)selectOne("rbs.modules.agreement.agreementMapper.getRegister", param);
    }
    public void updateRequest(Map param) {
    	update("rbs.modules.agreement.agreementMapper.updateRequest", param);
    }
    public int confirmAgreement(Map param) {
    	return update("rbs.modules.agreement.agreementMapper.confirmAgreement", param);
    }
    public Map getBsk(String bpl_no) {
    	return (Map)selectOne("rbs.modules.agreement.agreementMapper.getBsk", bpl_no);
    }
    public void requestAgreementbyCenter(Map param) {
    	insert("rbs.modules.agreement.agreementMapper.requestAgreementbyCenter", param);
    }

	public void mergeAgremByCnsl(Cnsl cnsl) {
		update("rbs.modules.agreement.agreementMapper.mergeAgremByCnsl", cnsl);
	}

	public int getAgremCount(String bplNo) {
		return selectOne("rbs.modules.agreement.agreementMapper.getAgremCount", bplNo);
	}

	public List<HashMap<String, String>> getCompltedAgremList(String bplNo) {
		return selectList("rbs.modules.agreement.agreementMapper.getCompltedAgremList", bplNo);
	}
}