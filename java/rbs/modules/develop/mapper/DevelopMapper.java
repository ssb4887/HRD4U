package rbs.modules.develop.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 훈련과정 맞춤개발 모듈에 관한 데이터 접근 클래스를 정의한다.
 * @author LDG, KJM
 */
@Repository("developMapper")
public class DevelopMapper extends EgovAbstractMapper{

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.develop.developMapper.selectList", param);
    }
	
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.develop.developMapper.selectCount", param);
    }
    
    /**
	 * 표준개발 신청 목록 가져오기
	 * @param param 검색조건
	 * @return List<Object> 목록정보
	 */
	public List<Object> getDevList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.develop.developMapper.selectDevList", param);
	}
    
	/**
     * 표준개발 신청 목록 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDevTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.develop.developMapper.selectDevCount", param);
    }
	
    /**
     * 파일항목 목록
     * @param param
     * @return
     */
	public List<Object> getFileList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.develop.developMapper.selectFileList", param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getFileView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.develop.developMapper.selectFileView", param);
	}
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap  getView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.develop.developMapper.selectView", param);
	}

	/**
     * 다운로드수 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int updateFileDown(Map<String, Object> param){
        return super.update("rbs.modules.develop.developMapper.updateFileDown",param);
    }
    
    /**
     * 권한여부 조회
     * @param param
     * @return
     */
    public int getAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.develop.developMapper.authCount", param);
    }
    
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getModify(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.develop.developMapper.selectModify", param);
	}

    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getNextId(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.develop.developMapper.nextId", param);
    }    

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.develop.developMapper.insert", param);
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.develop.developMapper.update",param);
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.develop.developMapper.deleteList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.develop.developMapper.deleteCount", param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.develop.developMapper.delete",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.develop.developMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.develop.developMapper.cdelete", param);
    }
    
    /**
	 * 입력한 시군구 코드에 해당하는 지부지사  코드 가져오기
	 * @param BPL_NO
	 * @return bsisCnsl
	 * @throws Exception
	 */	
    public int getInsttIdx(Map<String, Object> param) {
    	return (int)selectOne("rbs.modules.develop.developMapper.getInsttIdx", param);
    }
    
	/**
	 * 해당 기업의 가장 최근 기초진단지 식별번호를 가져온다
	 * @param BPL_NO
	 * @return bsisCnsl
	 * @throws Exception
	 */	
    public long getMaxBsisCnsl(Map<String, Object> param) {
    	return (long)selectOne("rbs.modules.develop.developMapper.getMaxBsisCnsl", param);
    }

	/**
	 * 해당 기업의 가장 최근 기초진단지의 추천된 훈련을 가져온다
	 * @param bsisCnsl_idx
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> bsisRecom(long bsiscnsl_idx) {
		return (List<Object>)selectList("rbs.modules.develop.developMapper.getRecommend", bsiscnsl_idx);
	}

	/**
	 * 추천된 훈련(혹은 목록에서 선택한)의 과정 정보를 가져온다
	 * @param param
	 * @return List<Object>
	 * @throws Exception
	 */
	public DataMap getTpInfo(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.develop.developMapper.getTpInfo", param);
	}

	/**
	 * 추천된 훈련(혹은 목록에서 선택한)의 과정 상세 정보를 가져온다
	 * @param param
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> getTpSubInfo(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.develop.developMapper.getTpSubInfo", param);
	}

	/**
	 * 가장 마지막 IDX의 다음값을 가져온다.
	 * @param 
	 * @return 
	 * @throws Exception
	 */
	public int getNextIdx(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.develop.developMapper.getNextIdx", param);
	}

	
	/**
	 * 해당 기업의 가장 최근 살아있는 IDX값을 가져온다.
	 * @param 
	 * @return 
	 * @throws Exception
	 */
	public int getMaxDevlopIdx(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.develop.developMapper.getMaxDevlopIdx", param);
	}

	/**
	 * [훈련과정맞춤개발] S-OJT 한도 체크
	 * @param 
	 * @return 
	 * @throws Exception
	 */
	public String getSojtAvailableFlag(String BPL_NO) {
		return (String)selectOne("rbs.modules.develop.developMapper.getSojtAvailableFlag", BPL_NO);
	}

	/**
	 * [훈련과정맞춤개발] 종합이력 목록
	 * @param 
	 * @return List
	 * @throws Exception
	 */
	public List<Object> getTotalList(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.develop.developMapper.getTotalList", param);
	}

	public int getTotalListCount(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.develop.developMapper.getTotalListCount", param);
	}

	public int getDoctorIdx(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.develop.developMapper.getDoctorIdx", param);
	}
	
	public int getTrainingListCount(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.develop.developMapper.getTrainingListCount", param);
	}

	/**
	 * 훈련과정 목록
	 * @param param
	 * @return List
	 * @throws Exception
	 */
	public List<?> getTrainingList(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.develop.developMapper.getTrainingList", param);
	}

	public List<?> getSupportList(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.develop.developMapper.getSupportList", param);
	}

	public int getSupportListCount(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.develop.developMapper.getSupportListCount", param);
	}

	
	/**
	 * [비용청구] 비용청구 컨설팅 리포트 가져오기
	 * @param param
	 * @return 
	 * @throws Exception
	 */
	public DataMap getCnslReportInfo(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.develop.developMapper.getCnslReportInfo", param);
	}

	/**
	 * 비용청구 컨설팅 팀 리스트
	 * @param param
	 * @return List
	 * @throws Exception
	 */
	public List<Object> getCnslTeamInfo(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.develop.developMapper.getCnslTeamInfo", param);
	}

	/**
	 * 비용신청때 선택한 훈련과정을 제외한 모든 해당 기업의 훈련과정 리스트
	 * @param param
	 * @return List
	 * @throws Exception
	 */
	public List<Object> getCorpTpList(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.develop.developMapper.getCorpTpList", param);
	}

	/**
	 * AI추천으로 받은 JSON문자열을 기존의 기업HRD이음컨설팅 결과서의 형식으로 리턴한다
	 * @param param
	 * @return List
	 * @throws Exception
	 */
	public HashMap<String, Object> getAiRcommendRankList(Map<String, Object> param) {
		return selectOne("rbs.modules.develop.developMapper.getAiRcommendRankList", param);
	}
	
	public HashMap<String, Object> getBsisWithAiRecommendList(Map<String, Object> param) {
		return selectOne("rbs.modules.develop.developMapper.getBsisWithAiRecommendList", param);		
	}

	public int getCompareRequestAndVoByBplNo(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.develop.developMapper.getCompareRequestAndVoByBplNo", param);
	}

	public int getIsSojt(String BPL_NO) {
		return (Integer)selectOne("rbs.modules.develop.developMapper.getIsSojt", BPL_NO);
	}

	public int getIsBsc(String BPL_NO) {
		return (Integer)selectOne("rbs.modules.develop.developMapper.getIsBsc", BPL_NO);
	}

	public int getRsltIdxByBsiscnslIdx(Map<String, Object> param) {
		return (int)selectOne("rbs.modules.develop.developMapper.getRsltIdxByBsiscnslIdx", param);
	}

	public void upsertEduRecoSystemValue(Map<String, Object> param) {
		super.update("rbs.modules.develop.developMapper.upsertEduRecoSystemValue",param);		
	}

	public String getEduRecoSystemValue(Map<String, Object> param) {
		return (String)selectOne("rbs.modules.develop.developMapper.getEduRecoSystemValue", param);
	}


}