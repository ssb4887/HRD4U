package rbs.modules.clinic.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("clinicDctMapper")
public class ClinicDctMapper extends EgovAbstractMapper{

    /**
     * 지부지사 부장, 본부 직원이 로그인했을 때 신청 목록 가져오기
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getReqList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.clinic.clinicDctMapper.selectReqList", param);
    }
	
	/**
     * 지부지사 부장, 본부 직원이 로그인했을 때 목록 가져오기(계획관리, 성과관리, 비용관리에서 사용)
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.clinic.clinicDctMapper.selectList", param);
    }
	
	/**
     * 지부지사 직원이 로그인했을 때 신청 목록 가져오기
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getReqListForDoctor(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.clinic.clinicDctMapper.selectReqListForDoctor", param);
    }
	
	/**
     * 지부지사 직원이 로그인했을 때 목록 가져오기(계획관리, 성과관리, 비용관리에서 사용)
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getListForDoctor(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.clinic.clinicDctMapper.selectListForDoctor", param);
    }
	
	/**
     * 기업 자가 확인 체크리스트 목록 가져오기
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getCheckList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.clinic.clinicDctMapper.selectCheckList", param);
    }
	/**
     * 기업 자가 확인 체크리스트 답변 목록 가져오기
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getCheckListAnswer(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.clinic.clinicDctMapper.selectCheckListAnswer", param);
    }
	
	/**
     * 기업 선정 심사표 목록 가져오기
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getJdgmntabList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.clinic.clinicDctMapper.selectJdgmntabList", param);
    }
	/**
     * 기업 선정 심사표 답변 목록 가져오기
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getJdgmntabListAnswer(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.clinic.clinicDctMapper.selectJdgmntabListAnswer", param);
    }
	
	/**
     * 특정 테이블 IDX의 max 값 가져오기
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getMaxIdx(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.clinic.clinicDctMapper.selectMaxIdx", param);
    }
	
    /**
     * 지부지사 부장, 본부 직원이 로그인했을 때 신청 목록 갯수 가져오기
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalReqCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.clinic.clinicDctMapper.selectReqCount", param);
    }
    
    /**
     * 지부지사 부장, 본부 직원이 로그인했을 때 목록 갯수 가져오기(계획관리, 성과관리, 비용관리에서 사용)
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.clinic.clinicDctMapper.selectCount", param);
    }
    
    /**
     * 지부지사 직원이 로그인했을 때 신청 목록 갯수 가져오기
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalReqCountForDoctor(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.clinic.clinicDctMapper.selectReqCountForDoctor", param);
    }
    
    /**
     * 지부지사 직원이 로그인했을 때 신청 목록 갯수 가져오기(계획관리, 성과관리, 비용관리에서 사용)
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCountForDoctor(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.clinic.clinicDctMapper.selectCountForDoctor", param);
    }
    
    /**
     * 파일항목 목록
     * @param param
     * @return
     */
	public List<Object> getFileList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.clinic.clinicDctMapper.selectFileList", param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getFileView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.clinic.clinicDctMapper.selectFileView", param);
	}
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.clinic.clinicDctMapper.selectView", param);
	}
	
	/**
     * 신청서 상세보기
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getReqView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.clinic.clinicDctMapper.selectReqView", param);
	}

	/**
     * 다운로드수 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int updateFileDown(Map<String, Object> param){
        return super.update("rbs.modules.clinic.clinicDctMapper.updateFileDown",param);
    }
    
    /**
     * 권한여부 조회
     * @param param
     * @return
     */
    public int getAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.clinic.clinicDctMapper.authCount", param);
    }
    
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getModify(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.clinic.clinicDctMapper.selectModify", param);
	}
	
	/**
     * 신청서 수정하기
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getReqModify(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.clinic.clinicDctMapper.selectReqModify", param);
	}

    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getNextId(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.clinic.clinicDctMapper.nextId", param);
    }
    
    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.clinic.clinicDctMapper.insert", param);
    }
    
    /**
     * HRD_CLI_*** 테이블에 신청상태 insert
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insertConfm(Map<String, Object> param){
    	return super.insert("rbs.modules.clinic.clinicDctMapper.insertConfm", param);
    }
    
    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.clinic.clinicDctMapper.update",param);
    }

    /**
     * 계획서 최종승인이 되면 해당 내용을 HRD_CLI_PLAN_***_CHANGE에 새로 insert
     * @param param 수정정보
     */
    public int planToChange(Map<String, Object> param){
    	return super.update("rbs.modules.clinic.clinicDctMapper.PlanToChange",param);
    }

    /**
     * 계획서 변경승인이 되면 해당 내용을 HRD_CLI_PLAN_***에 update
     * @param param 수정정보
     */
    public int changeToPlan(Map<String, Object> param){
    	return super.update("rbs.modules.clinic.clinicDctMapper.ChangeToPlan",param);
    }
    
    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.clinic.clinicDctMapper.deleteList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.clinic.clinicDctMapper.deleteCount", param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.clinic.clinicDctMapper.delete",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.clinic.clinicDctMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.clinic.clinicDctMapper.cdelete", param);
    }

	/**
	 * 주치의 지정 -> 기업이 담당하는 지부지사 주치의 전체 목록
	 * @param param
	 * @return
	 */
	public List<Object> getDoctorList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.clinic.clinicDctMapper.selectDoctorList", param);
    }
	
	/**
	 * 주치의 지정 -> 기업이 담당하는 지부지사 주치의 전체 목록 수
	 * @param param
	 * @return
	 */
    public int getTotalDoctorCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.clinic.clinicDctMapper.selectDoctorCount", param);
    }
    
    /**
     * 능력개발클리닉 참여 신청서 기업정보
     * @param param 사업장관리번호(BPL_NO)
     * @return 기업정보
     */
	public DataMap getCorpInfo(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.clinic.clinicDctMapper.selectCorpInfo", param);
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
	 * [대시보드] 연차별 클리닉 기업 전체 리스트(본부 - 전체 지부지사 / 지부지사 부장 - 본인 소속기관 전체 / 지부지사 주치의 - 본인이 전담주치의인 기업)
	 * @param param
	 * @return
	 */
	public List<Object> getAllCliCorpCountList(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicDctMapper.getAllCliCorpCountList", param);
	}
	
	/**
	 * [대시보드] 연차별 나의 클리닉 기업 전체 리스트(지부지사 부장, 지부지사 주치의만 사용)
	 * @param param
	 * @return
	 */
	public List<Object> getMyCliCorpCountList(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicDctMapper.getMyCliCorpCountList", param);
	}
	
	/**
	 * [대시보드] 연차별 중도포기한 클리닉 기업 전체 리스트(본부 - 전체 지부지사 / 지부지사 부장 - 본인 소속기관 전체 / 지부지사 주치의 - 본인이 전담주치의인 기업)
	 * @param param
	 * @return
	 */
	public List<Object> getAllDropCliCorpCountList(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicDctMapper.getAllDropCliCorpCountList", param);
	}
	
	/**
	 * [대시보드] 연차별 중도포기한 나의 클리닉 기업 전체 리스트(지부지사 부장, 지부지사 주치의만 사용)
	 * @param param
	 * @return
	 */
	public List<Object> getMyDropCliCorpCountList(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicDctMapper.getMyDropCliCorpCountList", param);
	}
	
	/**
	 * [대시보드] 클리닉 기업 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Object> getCliCorpList(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.clinic.clinicDctMapper.getCliCorpList", param);
	}

	public int getCliCorpCount(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.clinic.clinicDctMapper.getCliCorpCount", param);
	}
}