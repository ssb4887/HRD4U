package rbs.modules.clinic.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import rbs.egovframework.common.ChgLogMapper;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("clinicMapper")
public class ClinicMapper extends EgovAbstractMapper{
	/**
	 * 해당 기업의 가장 최근 기초진단지 식별번호를 가져온다
	 * @param BPL_NO
	 * @return bsc
	 * @throws Exception
	 */	
    public long getMaxBsc(Map<String, Object> param) {
    	return (long)selectOne("rbs.modules.clinic.clinicMapper.getMaxBsc", param);
    }
	
	/**
     * 기업 자가 확인 체크리스트 목록 가져오기
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getCheckList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.selectCheckList", param);
    }
	/**
     * 기업 자가 확인 체크리스트 답변 목록 가져오기
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getCheckListAnswer(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.selectCheckListAnswer", param);
    }
	
	/**
	 * 신청서에서 지부지사를 선택하기 위해 지부지사 관할구역 정보 가져오기
	 * @param param 검색조건
	 * @return List<Object> 목록정보
	 */
	public List<Object> getInsttList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.selectInsttList", param);
	}
	
	/**
	 * 기존에 등록된 클리닉 지부지사 가져오기
	 * @param param 검색조건
	 * @return 상세정보
	 */
	public int getCliInstt(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.clinic.clinicMapper.selectCliInstt", param);
	}
	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getReqView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.clinic.clinicMapper.selectReqView", param);
	}
	
	/**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getReqModify(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.clinic.clinicMapper.selectReqModify", param);
	}
	
    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.selectList", param);
    }
	
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.clinic.clinicMapper.selectCount", param);
    }
    
    /**
     * 특정 테이블 IDX의 max 값 가져오기
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getMaxIdx(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.clinic.clinicMapper.selectMaxIdx", param);
    }
    
    /**
     * 파일항목 목록
     * @param param
     * @return
     */
	public List<Object> getFileList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.selectFileList", param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getFileView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.clinic.clinicMapper.selectFileView", param);
	}
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.clinic.clinicMapper.selectView", param);
	}

	/**
     * 다운로드수 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int updateFileDown(Map<String, Object> param){
        return super.update("rbs.modules.clinic.clinicMapper.updateFileDown",param);
    }
    
    /**
     * 권한여부 조회
     * @param param
     * @return
     */
    public int getAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.clinic.clinicMapper.authCount", param);
    }
    
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getModify(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.clinic.clinicMapper.selectModify", param);
	}

    /**
     * 마지막 CLI_IDX의 다음값을 가져온다.(insert 준비용)
     * @return int key
     */
    public int getNextCliIdx() {
    	return (Integer)selectOne("rbs.modules.clinic.clinicMapper.nextCliIdx");
    }
    
    /**
     * 마지막 CHG_IDX의 다음값을 가져온다.
     * @return int key
     */
    public int getNextChgId(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.clinic.clinicMapper.nextChgIdx", param);
    }
	
    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getNextId(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.clinic.clinicMapper.nextId", param);
    }
    
    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getNextIdWithSearchList(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.clinic.clinicMapper.nextIdWithSearchList", param);
    }
    
    /**
     * 승인 테이블 key 조회하기
     * @return int key
     */
    public int getNextConfmId(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.clinic.clinicMapper.nextId", param);
    }

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.clinic.clinicMapper.insert", param);
    }
    
    /**
     * support테이블에 인서트한다(계좌번호 암호화용)
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insertSpt(Map<String, Object> param){
    	return super.insert("rbs.modules.clinic.clinicMapper.insertSpt", param);
    }
    
    /**
     * 승인 테이블에 insert하기
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insertConfm(Map<String, Object> param){
    	return super.insert("rbs.modules.clinic.clinicMapper.insertConfm", param);
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.clinic.clinicMapper.update",param);
    }
    
    /*
    * support테이블의 업데이트 한다(계좌번호 암호화용)
    * @param param 수정정보
    */
    public int updateSpt(Map<String, Object> param){
    	return super.update("rbs.modules.clinic.clinicMapper.updateSpt",param);
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.deleteList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.clinic.clinicMapper.deleteCount", param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.clinic.clinicMapper.delete",param);
    }

    /**
     * 승인 테이블 기존에 있는 데이터 삭제 처리
     * @param param 삭제정보
     */
    public int deleteConfm(Map<String, Object> param){
    	return super.update("rbs.modules.clinic.clinicMapper.deleteConfm",param);
    }
    
    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.clinic.clinicMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.clinic.clinicMapper.cdelete", param);
    }
    
    /**
     * 능력개발클리닉 참여 신청서 기업정보
     * @param param 사업장관리번호(BPL_NO)
     * @return 기업정보
     */
	public DataMap getCorpInfo(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.clinic.clinicMapper.selectCorpInfo", param);
	}

	/**
	 * HRD_CLI에서 최종승인된 CLI_IDX(식별색인) select
	 * @param param 사업장관리번호(BPL_NO)
	 * @return CLI_IDX
	 */
	public int getCurrentCliIdx(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.clinic.clinicMapper.selectCliIdx", param);
	}
	
    /**
     * HRD_CLI의 CLI_IDX(식별색인) select
     * @param param 사업장관리번호(BPL_NO)
     * @return CLI_IDX
     */
	public int getCliIdx(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.clinic.clinicMapper.selectMaxCliIdx", param);
	}
	
	/**
	 * HRD_CLI의 CLI_IDX(식별색인) select
	 * @param param 사업장관리번호(BPL_NO)
	 * @return CLI_IDX
	 */
	public String getCliValidStartDate(Map<String, Object> param) {
		return (String)selectOne("rbs.modules.clinic.clinicMapper.selectCliValidStartDate", param);
	}	
	
	/**
	 * 클리닉신청 여부
	 * @param BPL_NO
	 * @return isCli
	 */
	public int getIsCli(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.clinic.clinicMapper.selectIsCli", param);
	}
	
	/**
	 * 훈련계획서 등록 여부
	 * @param cliIdx
	 * @return isPlan
	 */
	public int getIsPlan(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.clinic.clinicMapper.selectIsPlan", param);
	}
	
	/**
	 * 신청서 등록 여부
	 * @param BPL_NO
	 * @return isReq
	 */
	public int getIsReq(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.clinic.clinicMapper.selectIsReq", param);
	}
	
	/**
	 * 성과보고서 등록여부
	 * @param cliIdx
	 * @return isResult
	 */
	public int getIsResult(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.clinic.clinicMapper.selectIsResult", param);
	}
	
	/**
	 * 지원금 신청서 등록여부
	 * @param cliIdx
	 * @return isResult
	 */
	public int getIsSupport(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.clinic.clinicMapper.selectIsSupport", param);
	}
	
	/**
	 * 특정 메뉴에서 등록을 할 때 이전 단계가 최종승인이 되었는지 확인
	 * @param BPL_NO
	 * @return isApprove
	 */
	public int getIsApprove(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.clinic.clinicMapper.selectIsApprove", param);
	}
	
	public String getCondition(Map<String, Object> param) {
		return (String)selectOne("rbs.modules.clinic.clinicMapper.getCondition",param);
	}
	
	public List<Object> getSportList() {
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.getSportList");
	}
	
	
	public List<Object> getActivityCode(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.getActivityCode", param);
	}
	
	

	public List<Object> getActivityList(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.getActivityList", param);
	}

	public List<Object> getPlanProgress(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.getPlanProgress", param);
	}
	
	public List<Object> getPlanCode(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.getPlanCode", param);
	}

	public int getActivityCount(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.clinic.clinicMapper.getActivityCount", param);
	}

	public List<Object> getResultProgress(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.getResultProgress", param);
	}
	
	public List<Object> getResultCode(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.getResultCode", param);
	}

	public List<Object> getSupportProgress(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.getSupportProgress", param);
	}
	
	public List<Object> getSptSubGroupBy(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.selectSptSub", param);
	}
	
	public List<Object> getSptPayList(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.selectSptPayList", param);
	}
	
	public List<Object> getTrainingCntList(String BPL_NO) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.getTrainingCntList", BPL_NO);
	}
	
	public List<Object> getSurveyTargetList(String BPL_NO) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.getSurveyTargetList", BPL_NO);
	}
	
	public List<Object> getAnswerList(String BPL_NO) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicMapper.getAnswerList", BPL_NO);
	}
	public int getCompareRequestAndVoByBplNo(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.clinic.clinicMapper.getCompareRequestAndVoByBplNo", param);
	}
	
}