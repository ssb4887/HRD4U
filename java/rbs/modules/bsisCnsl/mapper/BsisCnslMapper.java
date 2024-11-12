package rbs.modules.bsisCnsl.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("bsisCnslMapper")
public class BsisCnslMapper extends EgovAbstractMapper{

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.bsisCnsl.bsisCnslMapper.selectList", param);
    }
	
	/**
	 * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
	 * @param param 검색조건
	 * @return List<Object> 목록정보
	 */
	public List<Object> getMyList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.bsisCnsl.bsisCnslMapper.selectMyList", param);
	}
	
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.selectTotalCount", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getMyTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.selectMyTotalCount", param);
    }
    
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getBscTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.selectBscTotalCount", param);
    }
    
    /**
     * 파일항목 목록
     * @param param
     * @return
     */
	public List<Object> getFileList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.bsisCnsl.bsisCnslMapper.selectFileList", param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getFileView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.selectFileView", param);
	}
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.selectView", param);
	}
	

	/**
     * 다운로드수 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int updateFileDown(Map<String, Object> param){
        return super.update("rbs.modules.bsisCnsl.bsisCnslMapper.updateFileDown",param);
    }
    
    /**
     * 권한여부 조회
     * @param param
     * @return
     */
    public int getAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.authCount", param);
    }
    
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getModify(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.selectModify", param);
	}

    /**
     * 게시물 key 조회한다.
     * @return String key
     */
    public String getNextId(Map<String, Object> param) {
    	return (String)selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.nextId", param);
    }

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.bsisCnsl.bsisCnslMapper.insert", param);
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.bsisCnsl.bsisCnslMapper.update",param);
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.bsisCnsl.bsisCnslMapper.deleteList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.deleteCount", param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.bsisCnsl.bsisCnslMapper.delete",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.bsisCnsl.bsisCnslMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.bsisCnsl.bsisCnslMapper.cdelete", param);
    }
    
    /**
     * 등록된 정보(설문조사) 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보(설문조사)
     */
	public List<Object> getQustnrView(Map<String, Object> param) {
         return (List<Object>)selectList("rbs.modules.bsisCnsl.bsisCnslMapper.selectQustnrView", param);
	}

	/**
	 * 설문지 답변 가져오기
     * @param param 검색조건
     * @return 상세정보(설문조사 답변)
     */
	public List<Object> getQustnrAnswer(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.bsisCnsl.bsisCnslMapper.selectQustnrAnswer", param);
	}
	
	/**
	 * 자신의 기초진단서 가져오기
     * @param param 검색조건
     * @return 자신의 기초진단서 목록
     */
	public List<Object> getBscList(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.bsisCnsl.bsisCnslMapper.selectBscList", param);
	}
	
	/**
	 * 자신의 기초진단서 가져오기
     * @param param 검색조건
     * @return 자신의 기초진단서 목록
     */
	public int setQustnrAnswer(List<Map<String, Object>> param) {
		return super.update("rbs.modules.bsisCnsl.bsisCnslMapper.updateQustnrAnswer", param);
	}
	
	/**
	 * 설문조사 답변 저장하기
     * @param param 저장값
     * @return 등록 결과 
     */
	public int insertQustnrResult(Map<String, Object> param) {
		return super.insert("rbs.modules.bsisCnsl.bsisCnslMapper.insertQustnrResult", param);
	}

	/**
	 * 설문조사 답변 수정하기
	 * @param param 수정값
	 * @return 업데이트 결과 
	 */
	public int updateQustnrResult(Map<String, Object> param) {
		return super.update("rbs.modules.bsisCnsl.bsisCnslMapper.updateQustnrResult", param);
	}
	
	/**
	 * 설문조사 답변 디테일 수정하기
	 * @param param 
	 * @return 업데이트 결과 
	 */
	public int updateQustnrAnswers(List<Map<String, Object>> param) {
		return super.update("rbs.modules.bsisCnsl.bsisCnslMapper.updateQustnrAnswers", param);
	}
	
	/**
	 * 설문조사 답변 디테일 입력하기
	 * @param param 
	 * @return 등록 결과 
	 */
	public int insertQustnrAnswers(List<Map<String, Object>> param) {
		int answerIdx = (Integer)selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.getMaxAnswerIdx");
		for(int i=0; i < param.size();i++) {
			param.get(i).put("answerIdx", answerIdx + i);
		}
		return super.insert("rbs.modules.bsisCnsl.bsisCnslMapper.insertQustnrAnswers", param);
	}

	public List<Map<String, Object>> getQustnrAnswerOnly(Map<String, Object> param) {
		return selectList("rbs.modules.bsisCnsl.bsisCnslMapper.selectQustnrAnswerOnly", param);
	}
	
	
	/**
     * BSC_IDX(기초진단 식별번호)를 이용한 설문조사 입력된 게 있는지 체크하기
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap isQustnrExistsByBsc(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.isQustnrExistsByBsc", param);
	}
	
	/**
     * BSC_IDX(기초진단 식별번호)를 이용한 설문조사 입력된 게 있는지 체크하기
     * @param param 검색조건
     * @return 상세정보
     */
	public String getBplNo(Map<String, Object> param) {
         return selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.getBplNo", param);
	}
	
	
	/**
     * BPL_NO(사업장관리번호)를 이용해 발급번호(issueNo)&발급날짜(isseuDate)가져오기
     * @param param 검색조건
     * @return 상세정보
     */
	public Map<String, Object> getIssue(Map<String, Object> param) {
         return selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.getIssue", param);
	}
	
	/**
	 * 기초컨설팅및설문을 진행하지 않은 기초진단 갯수 가져오기
	 * @param param 검색조건
	 * @return 상세정보
	 */
	public int getBscInitTotalCount(Map<String, Object> param) {
		return selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.getBscInitTotalCount", param);
	}

	/**
	 * 기초컨설팅및설문을 진행하지 않은 기초진단 갯수 가져오기
	 * @param param 검색조건
	 * @return 상세정보
	 */
	public List<Object> getBscInitList(Map<String, Object> param) {
		return selectList("rbs.modules.bsisCnsl.bsisCnslMapper.getBscInitList", param);
	}
	
	/**
	 * 기초컨설팅 idx에 맞는 설문조사지 idx 가져오기
	 * @param param (bscIdx)
	 * @return RSLT_IDX
	 */
	public int getRsltIdx(Map<String, Object> param) {
		return selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.getRsltIdx", param);
	}

	/**
	 * bplNo로 완료된 기초컨설팅 Count 조회
	 * @param bplNo
	 * @return
	 */
	public HashMap<String, Object> getCompletedBsisCnslOne(String bplNo) {
		return selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.getCompletedBsisCnslOne", bplNo);
	}

	public List<HashMap<String, Object>> selectCompletedAgremBybplNo(String bplNo) {
		return selectList("rbs.modules.bsisCnsl.bsisCnslMapper.selectCompletedAgremBybplNo", bplNo);
	}
	
	/**
	 * 관리대장엑셀용 목록 불러오기
	 * @param param
	 * @return
	 */
	public List<Object> getListForAdmin(Map<String, Object> param) {
		return selectList("rbs.modules.bsisCnsl.bsisCnslMapper.selectListForAdmin", param);
	}
	
	/**
	 * bsiscnslIdx로 HRD기초컨설팅 조회
	 * @param param
	 * @return
	 */
	public Map<String, Object> getBsisCnslByIdx(Map<String, Object> param) {
		return selectOne("rbs.modules.bsisCnsl.bsisCnslMapper.selectBsisCnslByIdx", param);
	}
	
	public int updateFtfYn(Map<String, Object> param) {
		return update("rbs.modules.bsisCnsl.bsisCnslMapper.updateFtfYn", param);
	}
}