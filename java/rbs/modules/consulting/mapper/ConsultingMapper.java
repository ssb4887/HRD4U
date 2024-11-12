package rbs.modules.consulting.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import rbs.modules.consulting.dto.Cnsl;
import rbs.modules.consulting.dto.CnslDiaryDTO;
import rbs.modules.consulting.dto.CnslFileDTO;
import rbs.modules.consulting.dto.CnslTeamDTO;
import rbs.modules.consulting.dto.CnslTeamMemberDTO;
import rbs.modules.consulting.dto.HrpUserDTO;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("consultingMapper")
public class ConsultingMapper extends EgovAbstractMapper{

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.consulting.consultingMapper.selectList", param);
    }
	
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getTotalCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.consulting.consultingMapper.selectCount", param);
    }
    
    /**
     * 파일항목 목록
     * @param param
     * @return
     */
	public List<Object> getFileList(Map<String, Object> param){
        return (List<Object>)selectList("rbs.modules.consulting.consultingMapper.selectFileList", param);
    }
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getFileView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.consulting.consultingMapper.selectFileView", param);
	}
	
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getView(Map<String, Object> param) {
         return (DataMap)selectOne("rbs.modules.consulting.consultingMapper.selectView", param);
	}

	/**
     * 다운로드수 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int updateFileDown(Map<String, Object> param){
        return super.update("rbs.modules.consulting.consultingMapper.updateFileDown",param);
    }
    
    /**
     * 권한여부 조회
     * @param param
     * @return
     */
    public int getAuthCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.consulting.consultingMapper.authCount", param);
    }
    
    /**
     * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return 상세정보
     */
	public DataMap getModify(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.consulting.consultingMapper.selectModify", param);
	}

    /**
     * 게시물 key 조회한다.
     * @return int key
     */
    public int getNextId(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.consulting.consultingMapper.nextId", param);
    }

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.consulting.consultingMapper.insert", param);
    }

    /**
     * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
     * @param param 수정정보
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.consulting.consultingMapper.update",param);
    }

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 삭제목록정보
     */
	public List<Object> getDeleteList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.consulting.consultingMapper.deleteList", param);
    }

    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDeleteCount(Map<String, Object> param) {
        return (Integer)selectOne("rbs.modules.consulting.consultingMapper.deleteCount", param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
     * @param param 삭제정보
     */
    public int delete(Map<String, Object> param){
    	return super.update("rbs.modules.consulting.consultingMapper.delete",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
     * @param param 복원정보
     */
    public int restore(Map<String, Object> param){
    	return super.update("rbs.modules.consulting.consultingMapper.restore",param);
    }

    /**
     * 화면에 조회된 정보를 데이터베이스에서 삭제
     * @param param 완전삭제정보
     */
    public int cdelete(Map<String, Object> param){
    	return super.delete("rbs.modules.consulting.consultingMapper.cdelete", param);
    }

    
    
    
    
    /**
     * 컨설팅 저장
     * @param dto
     */
	public int insertCnslByDto(Cnsl cnsl) {
		return insert("rbs.modules.consulting.consultingMapper.insertCnslByDto", cnsl);
		
	}
	
	/**
	 * 컨설팅 저장(업데이트)
	 * @param dto
	 */
	public void updateCnslByDto(Cnsl cnsl) {
		update("rbs.modules.consulting.consultingMapper.updateCnslByDto", cnsl);
		
	}
	
	public void updateCnslSubmitByDto(Cnsl cnsl) {
		update("rbs.modules.consulting.consultingMapper.updateCnslSubmitByDto", cnsl);
		
	}
	
	
	

	public Cnsl selectByCnslIdx(int cnslIdx) {
		return selectOne("rbs.modules.consulting.consultingMapper.selectByCnslIdx", cnslIdx);
	}



	/**
	 * 컨설팅 상태값 변경, 
	 * @param dto
	 */
	public void updateStatusBydto(Cnsl cnsl) {
		update("rbs.modules.consulting.consultingMapper.updateStatusBydto", cnsl);
		
	}


	@Transactional
	public void rejectConsulting(Cnsl cnsl) {
		update("rbs.modules.consulting.consultingMapper.updateStatusBydto", cnsl);
		
	}

	
	public void insertCnslFileByDto(CnslFileDTO cnslFileDTO) {
		insert("rbs.modules.consulting.consultingMapper.insertCnslFileByDto", cnslFileDTO);
		
	}

	public void updateCnslFileByDto(CnslFileDTO cnslFileDTO) {
		update("rbs.modules.consulting.consultingMapper.mergeCnslFileByDto", cnslFileDTO);
		
	}


	public void insertCnslTeamByDto(CnslTeamDTO teamDTO) {
		 insert("rbs.modules.consulting.consultingMapper.insertCnslTeamByDto", teamDTO);
		
	}

	public void updateCnslTeamByDto(CnslTeamDTO teamDTO) {
		update("rbs.modules.consulting.consultingMapper.mergeCnslTeamByDto", teamDTO);
	}
	
	public HashMap<String, String> select4uById(String hrd4uId) {
		return selectOne("rbs.modules.consulting.consultingMapper.select4uById", hrd4uId);
	}

	public HrpUserDTO selectHrpById(String hrd4uId) {
		return selectOne("rbs.modules.consulting.consultingMapper.selectHrpById", hrd4uId);
	}

	public CnslTeamDTO selectTeamByCnslIdx(int cnslIdx) {
		return selectOne("rbs.modules.consulting.consultingMapper.selectTeamByCnslIdx", cnslIdx);
	}

	public void deleteCnslTeamByCnslIdx(int cnslIdx) {
		delete("rbs.modules.consulting.consultingMapper.deleteCnslTeamByCnslIdx", cnslIdx);
		
	}


	public void insertConfm(Cnsl cnsl) {
		insert("rbs.modules.consulting.consultingMapper.insertConfmBydto", cnsl);
		
	}

	public List<Cnsl> selectConfmByCnslIdx(String cnslIdx) {
		return selectList("rbs.modules.consulting.consultingMapper.selectConfmByCnslIdx", cnslIdx);
	}

    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getDiaryList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.consulting.consultingMapper.getDiaryList", param);
    }
	
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getDiaryCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.consulting.consultingMapper.getDiaryCount", param);
    }

	public void insertCnslConfmByDto(Cnsl cnsl) {
		insert("rbs.modules.consulting.consultingMapper.insertCnslConfmByDto", cnsl);
	}

	public void insertChangeCnslByDto(Cnsl cnsl) {
		 insert("rbs.modules.consulting.consultingMapper.insertChangeCnslByDto", cnsl);
		
	}

	public void insertChangeCnslFileByDto(CnslFileDTO cnslFileDTO) {
		insert("rbs.modules.consulting.consultingMapper.inserChangeCnslFileByDto", cnslFileDTO);
		
	}

	public void insertChangeCnslTeamByDto(CnslTeamDTO teamDTO) {
		 insert("rbs.modules.consulting.consultingMapper.inserChangeCnslTeamByDto", teamDTO);
	}

	public Cnsl selectChangeCnslByCnslIdx(int cnslIdx) {
		return selectOne("rbs.modules.consulting.consultingMapper.selectChangeCnslByCnslIdx", cnslIdx);
	}

	public int getTotalChangeCnslCount(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.consulting.consultingMapper.selectChangeCount", param);
	}

	public List<?> getChangeList(Map<String, Object> param) {
		return (List<Object>)selectList("rbs.modules.consulting.consultingMapper.selectChangeList", param);
	}

	public void updateChangeConfirmByDto(Cnsl cnsl) {
		update("rbs.modules.consulting.consultingMapper.updateChangeConfirmByDto", cnsl);
	}
	
	public void mergeChangeFileConfirmByDto(Cnsl cnsl) {
		update("rbs.modules.consulting.consultingMapper.mergeChangeFileConfirmByDto", cnsl);
	}
	
	public void mergeChangeTeamConfirmByDto(Cnsl cnsl) {
		update("rbs.modules.consulting.consultingMapper.mergeChangeTeamConfirmByDto", cnsl);
	}
	

	public void updateChangeRejectByDto(Cnsl cnsl) {
		update("rbs.modules.consulting.consultingMapper.updateChangeRejectByDto", cnsl);
		
	}

	/**
     * 수행일지 중복 체크
     * @param param 검색조건
     * @return int 총갯수
     */
    public int chkDuplicate(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.consulting.consultingMapper.chkDuplicate", param);
    }

    /**
     * 인력풀 위촉 조회
     * @param param 검색조건
     * @return int 총갯수
     */
    public int chkEntrstRole(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.consulting.consultingMapper.chkEntrstRole", param);
    }

	public List<Cnsl> getCnslListAll(Map<String, Object> param) {
		return selectList("rbs.modules.consulting.consultingMapper.selectCnslListAll", param);
	}

	public int getCnslListAllCount(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.consulting.consultingMapper.selectCnslListAllCount", param);
	}

	public int selectCnslCountByBplNoAndCnslType(HashMap<String, String> param) {
		return (Integer)selectOne("rbs.modules.consulting.consultingMapper.selectCnslCountByBplNoAndCnslType", param);
	}

	public String select4URepveNmByBizrNo(String bizrNo) {
		return selectOne("rbs.modules.consulting.consultingMapper.select4URepveNmByBizrNo", bizrNo);
	}

	public void deleteCnslTeamByDTO(CnslTeamDTO teamDTO) {
		delete("rbs.modules.consulting.consultingMapper.deleteCnslTeamByDTO", teamDTO);
	}

	public void deleteCnslFileByDto(List<CnslFileDTO> cnslFileDTO) {
		delete("rbs.modules.consulting.consultingMapper.deleteCnslFileByDto", cnslFileDTO);
		
	}

	public List<CnslFileDTO> selectFileByCnslIdx(Integer cnslIdx) {
		return selectList("rbs.modules.consulting.consultingMapper.selectFileByCnslIdx", cnslIdx);
	}

	public void insertChangeCnslFileByExistFiles(List<CnslFileDTO> cnslFiles) {
		insert("rbs.modules.consulting.consultingMapper.insertChangeCnslFileByExistFiles", cnslFiles);
	}

	public CnslFileDTO selectFileByCnslIdxAndItemId(CnslFileDTO cnslFileDTO) {
		return selectOne("rbs.modules.consulting.consultingMapper.selectFileByCnslIdxAndItemId", cnslFileDTO);
	}

	public HashMap<String, String> selectCenterInfo(String centerIdx) {
		return selectOne("rbs.modules.consulting.consultingMapper.selectCenterInfo", centerIdx);
	}

	public HashMap<String, String> selectInsttIdxByZip(String zip) {
		return selectOne("rbs.modules.consulting.consultingMapper.selectInsttIdxByZip", zip);
	}
	
	public DataMap getInstt(Map<String, Object> param) {
        return (DataMap)selectOne("rbs.modules.consulting.consultingMapper.getInstt", param);
	}
	
	public List<HashMap<String, Object>> getDiaryView(Map<String, Object> param) {
        return selectList("rbs.modules.consulting.consultingMapper.getDiaryView", param);
	}

	public List<HashMap<String, Object>> selectDoctorInfoByInsttIdx(String insttIdx) {
		 return selectList("rbs.modules.consulting.consultingMapper.selectDoctorInfoByInsttIdx", insttIdx);
	}

	public int changeCmptncBrffcPic(HashMap<String, String> map) {
		return update("rbs.modules.consulting.consultingMapper.changeCmptncBrffcPic", map);
	}
	
	public int selectSojtIsSelected(String bplNo) {
		return selectOne("rbs.modules.consulting.consultingMapper.selectSojtIsSelected", bplNo);
	}

	public int selectCnslCountByBplNoAndCnslType2(String bplNo) {
		return (Integer)selectOne("rbs.modules.consulting.consultingMapper.selectCnslCountByBplNoAndCnslType2", bplNo);
	}
	
	/**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getEntList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.consulting.consultingMapper.getEntList", param);
    }

	/**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getEntCnt(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.consulting.consultingMapper.getEntCnt", param);
    }
    
    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int insertEvl(Map<String, Object> param){
    	return super.insert("rbs.modules.consulting.consultingMapper.insertEvl", param);
    }
    
    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getEvlList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.consulting.consultingMapper.getEvlList", param);
    }
	
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getEvlCount(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.consulting.consultingMapper.getEvlCount", param);
    }
    
    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int updateEntrst(Map<String, Object> param){
    	return super.update("rbs.modules.consulting.consultingMapper.updateEntrst", param);
    }

	public HashMap<String, String> selectCountOfConsulting(String hrd4uId) {
		return selectOne("rbs.modules.consulting.consultingMapper.selectCountOfConsulting", hrd4uId);
	}

	public void mergeCnslTeamAuth(Cnsl savedCnsl) {
		update("rbs.modules.consulting.consultingMapper.mergeCnslTeamAuth", savedCnsl);
	}

	public int selectSojtConfm(String bplNo) {
		return selectOne("rbs.modules.consulting.consultingMapper.selectSojtConfm", bplNo);
	}

	public int selectCnslSubmitCountByBplNoAndCnslType(HashMap<String, String> param) {
		return selectOne("rbs.modules.consulting.consultingMapper.selectCnslSubmitCountByBplNoAndCnslType", param);
	}

	public List<HashMap<String, String>> selectNcsInfo(String json) {
		return selectList("rbs.modules.consulting.consultingMapper.selectNcsInfo", json);
	}

	public int selectCountIndvdlInfo(String hrd4uId) {
		return selectOne("rbs.modules.consulting.consultingMapper.selectCountIndvdlInfo", hrd4uId);
	}

	public List<HashMap<String, String>> getInsttList() {
		return selectList("rbs.modules.consulting.consultingMapper.getInsttList");
	}

	public void insertCnslTeamMember(CnslTeamDTO preTeams) {
		 insert("rbs.modules.consulting.consultingMapper.insertCnslTeamMember", preTeams);
		
	}

	public int isTrainingComplecated(String hrd4uId) {
		return selectOne("rbs.modules.consulting.consultingMapper.isTrainingComplecated", hrd4uId);
	}

	public List<CnslDiaryDTO> selectDiaryListByCnslIdx(String cnslIdx) {
		return selectList("rbs.modules.consulting.consultingMapper.selectDiaryListByCnslIdx", cnslIdx);
	}

	public List<Cnsl> selectCnslListByBplNoForViewAll(String bplNo) {
		return selectList("rbs.modules.consulting.consultingMapper.selectCnslListByBplNoForViewAll", bplNo);
	}

	public List<Cnsl> selectCnslListByMemberIdxForViewAll(String memberIdx) {
		return selectList("rbs.modules.consulting.consultingMapper.selectCnslListByMemberIdxForViewAll", memberIdx);
	}

	public List<Cnsl> selectCnslListBySpntMemberIdxForViewAll(
			String memberIdx) {
		return selectList("rbs.modules.consulting.consultingMapper.selectCnslListBySpntMemberIdxForViewAll", memberIdx);
	}

	public List<Cnsl> selectCnslListByDoctorMemberIdxForViewAll(
			String memberIdx) {
		return selectList("rbs.modules.consulting.consultingMapper.selectCnslListByDoctorMemberIdxForViewAll", memberIdx);
	}

	public List<Cnsl> selectCnslListAllForViewAll() {
		return selectList("rbs.modules.consulting.consultingMapper.selectCnslListAllForViewAll");
	}

	public List<HashMap<String, String>> selectNcsInfoDepth1() {
		return selectList("rbs.modules.consulting.consultingMapper.selectNcsInfoDepth1");
	}

	public List<HashMap<String, String>> selectNextNcsDepth(String json) {
		return selectList("rbs.modules.consulting.consultingMapper.selectNextNcsDepth", json);
	}

	 /**
     * 기업정보 받아오기
     * @param map
     * @return
     */
    public Map<String, Object> findBizData(Map<String, Object> map) {
		return selectOne("rbs.modules.consulting.consultingMapper.findBizData", map);
	}

    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int applyInsert(Map<String, Object> param){
    	return super.insert("rbs.modules.consulting.consultingMapper.applyInsert", param);
    }
    
    /**
     * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
     * @param param 등록정보
     * @return String 등록결과
     */
    public int applyUpdate(Map<String, Object> param){
    	return super.update("rbs.modules.consulting.consultingMapper.applyUpdate", param);
    }
    
    /**
     * 총 갯수를 조회한다.
     * @param param 검색조건
     * @return int 총갯수
     */
    public int getCnslApplyCnt(Map<String, Object> param) {
    	return (Integer)selectOne("rbs.modules.consulting.consultingMapper.getCnslApplyCnt", param);
    }
    
    /**
     * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getCnslApplyList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.consulting.consultingMapper.getCnslApplyList", param);
    }

	public HashMap<String, String> selectSojtFormInfo(String bplNo) {
		return selectOne("rbs.modules.consulting.consultingMapper.selectSojtFormInfo", bplNo);
	}

	public int selectCnslSaveCountByBplNoAndCnslType(
			HashMap<String, String> param) {
		return selectOne("rbs.modules.consulting.consultingMapper.selectCnslSaveCountByBplNoAndCnslType", param);
	}

	public List<Cnsl> selectChangeConfmCn(String cnslIdx) {
		return selectList("rbs.modules.consulting.consultingMapper.selectChangeConfmCn", cnslIdx);
	}

	public HashMap<String, String> selectDoctorById(String hrd4uId) {
		return selectOne("rbs.modules.consulting.consultingMapper.selectDoctorById", hrd4uId);
	}
	

	
	
}