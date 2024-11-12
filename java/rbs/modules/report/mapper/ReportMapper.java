package rbs.modules.report.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.woowonsoft.egovframework.form.DataMap;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import rbs.modules.consulting.dto.Cnsl;
import rbs.modules.report.dto.Report;
import rbs.modules.report.dto.ReportFileDTO;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("reportMapper")
public class ReportMapper extends EgovAbstractMapper{

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.report.reportMapper.selectList", param);
    }
	
	/**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.report.reportMapper.selectCount", param);
    }
    
    /**
     * 파일항목 목록
     * @param param
     * @return
     */
	public List<Object> getFileList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.report.reportMapper.selectFileList", param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getFileView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.report.reportMapper.selectFileView", param);
	}
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.report.reportMapper.selectView", param);
	}
	
	/**
     * 다운로드수 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int updateFileDown(Map<String, Object> param){
        return super.update("rbs.modules.report.reportMapper.updateFileDown",param);
    }
    
    /**
     * 권한여부 조회
     * @param param
     * @return
     */
    public int getAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.report.reportMapper.authCount", param);
    }
    
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getModify(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.report.reportMapper.selectModify", param);
	}

    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getNextId(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.report.reportMapper.nextId", param);
    }    

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.report.reportMapper.insert", param);
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.report.reportMapper.update",param);
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.report.reportMapper.deleteList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.report.reportMapper.deleteCount", param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.report.reportMapper.delete",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.report.reportMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.report.reportMapper.cdelete", param);
    }

	/**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insertDevReport(Map<String, Object> param){
    	return super.insert("rbs.modules.report.reportMapper.insertDevReport", param);
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int updateDevReport(Map<String, Object> param){
    	return super.update("rbs.modules.report.reportMapper.updateDevReport",param);
    }
    
    /**
	 * @param param
	 * @return List<Object>
	 * @throws Exception
	 */
	public DataMap getTpInfo(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.report.reportMapper.getTpInfo", param);
	}

	/**
	 * @param param
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> getTpSubInfo(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.report.reportMapper.getTpSubInfo", param);
	}
	
	public void insertReprtConfmByDto(Map<String, Object> param) {
		insert("rbs.modules.report.reportMapper.insertReprtConfmByDto", param);
	}

	public void saveReportByDto(Report report) {
		update("rbs.modules.report.reportMapper.saveReportByDto", report);
		
	}

	public void saveReportFileByDTO(ReportFileDTO reportFileDTO) {
		update("rbs.modules.report.reportMapper.saveReportFileByDTO", reportFileDTO);
	}

	public void insertReportConfm(Report report) {
		insert("rbs.modules.report.reportMapper.insertReportConfm", report);
	}
	public void insertReportConfmByMap(Map<String, Object> param) {
		insert("rbs.modules.report.reportMapper.insertReportConfmByMap", param);
	}

	public List<HashMap<String, Object>> getListByAnotherTypeCnsl(int cnslIdx) {
		return selectList("rbs.modules.report.reportMapper.getListByAnotherTypeCnsl", cnslIdx);
	}
	
	/***********************임시***********************/
	public int insertReportData_TEMP(Map<String, Object> param) {
		return insert("rbs.modules.report.reportMapper.insertReportData_TEMP", param);
	}
	
	public Map<String, Object> selectReportData_TEMP(Map<String, Object> param) {
		return selectOne("rbs.modules.report.reportMapper.selectReportData_TEMP", param);
	}
	/***********************임시 끝***********************/

	public Report selectReportData(String cnslIdx) {
		return selectOne("rbs.modules.report.reportMapper.selectReportData", cnslIdx);
	}
	
	public int updateProgress(Map<String, Object> param) {
		return super.update("rbs.modules.report.reportMapper.updateProgress", param);
	}

	public List<ReportFileDTO> selectReportFiles(Integer cnslIdx) {
		return selectList("rbs.modules.report.reportMapper.selectReportFiles", cnslIdx);
	}
	
	public Map selectBSCbase(String bscIdx) {
		return selectOne("rbs.modules.report.reportMapper.selectBSCbase", bscIdx);
	}
	public List<Map> selectBSCtrainHis(String bscIdx) {
		return selectList("rbs.modules.report.reportMapper.selectBSCtrainHis", bscIdx);
	}
	public List<Map> selectBSCfundHis(String bscIdx) {
		return selectList("rbs.modules.report.reportMapper.selectBSCfundHis", bscIdx);
	}
	public List<Map> selectBSISrctr(String bscIdx) {
		return selectList("rbs.modules.report.reportMapper.selectBSISrctr", bscIdx);
	}


	public void deleteReportFileByDto(List<ReportFileDTO> deleteFiles) {
		delete("rbs.modules.report.reportMapper.deleteReportFileByDto", deleteFiles);
		
	}
	public List<Map> selectDiaryPBL(String cnsl_idx) {
		return selectList("rbs.modules.report.reportMapper.selectDiaryPBL", cnsl_idx);
	}

	public int chkDiaryCnt(int cnslIdx) {
		return selectOne("rbs.modules.report.reportMapper.chkDiaryCnt", cnslIdx);
	}
}



