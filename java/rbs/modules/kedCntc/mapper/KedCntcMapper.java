package rbs.modules.kedCntc.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import rbs.modules.kedCntc.vo.batchLogVO;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("KedCntcMapper")
public class KedCntcMapper extends EgovAbstractMapper{
	
	/**
     * 등록된 서버 정보를 데이터베이스에서 읽어옴.
     * @param param 검색조건
     * @return List<Object> 목록정보
     */
	public List<Object> getServerInfo(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.kedCntc.KedCntcMapper.selectList", param);
    }
	
	/**
     * 당일 파일 처리 정보를 확인 후 리턴
     * @return List<Object> 목록정보
     */
	public String getProcInfo(){
		return (String)selectOne("rbs.modules.kedCntc.KedCntcMapper.selectDoneList");
    }
	
	
	public List<Object> getACNT_CD(){
		return (List<Object>)selectList("rbs.modules.kedCntc.KedCntcMapper.selectACNTCDList");
    }
	
	/**
     * 기업개요 DB반영 (Merge) 전문번호 : 0002 파일명 : KED50D1_hrdk.txt
     * @param param 입력값
     * @return 
     */
	public int insKedCorp(Map<String, Object> param){
		return insert("rbs.modules.kedCntc.KedCntcMapper.insIFS_KED_CORP", param);
    }
	
	
	/**
     * 신용등급 DB반영 (Merge) 전문번호 : 0041 파일명 : KED5041_hrdk.txt
     * @param param 입력값
     * @return 
     */
	public int insKedCre(Map<String, Object> param){
		return insert("rbs.modules.kedCntc.KedCntcMapper.insIFS_KED_CRE", param);
    }
	/**
     * 인원현황 DB반영 (Merge) 전문번호 : 0016 파일명 : KED5016_hrdk.txt
     * @param param 입력값
     * @return 
     */
	public int insKedEmp(Map<String, Object> param){
		return insert("rbs.modules.kedCntc.KedCntcMapper.insIFS_KED_EMP", param);
    }

	/**
     * 주요재무정보DB반영 (Merge) 전문번호 : 0003 파일명 : KED5003_hrdk.txt
     * @param param 입력값
     * @return 
     */
	public int insKedFnc(Map<String, Object> param){
		return insert("rbs.modules.kedCntc.KedCntcMapper.insIFS_KED_FNC", param);
    }
	
	/**
     * 신계정 재무제표 DB반영 (Merge) 전문번호 : 0058 파일명 : KED5058_hrdk.txt
     * @param param 입력값
     * @return 
     */
	public int insKedAnal(Map<String, Object> param){
		return insert("rbs.modules.kedCntc.KedCntcMapper.insIFS_KED_ANAL", param);
    }
	
	/**
     * 휴페업정보 DB반영 (Merge) 전문번호 : 0087 파일명 : KED5087_hrdk.txt
     * @param param 입력값
     * @return 
     */
	public int insKedSpcss(Map<String, Object> param){
		return insert("rbs.modules.kedCntc.KedCntcMapper.insIFS_KED_SPCSS", param);
    }
	
	public batchLogVO execPkgBatchLog (batchLogVO param){
		selectOne("rbs.modules.kedCntc.KedCntcMapper.execPKG_BATCH_LOG", param);
		return param;
    }

	public void execPkgKedCorp (HashMap<String, String> param){
		selectOne("rbs.modules.kedCntc.KedCntcMapper.execP_KED_CORP", param);
    }
	
	public void execPkgKedCre (HashMap<String, String> param){
		selectOne("rbs.modules.kedCntc.KedCntcMapper.execP_KED_CRE", param);
    }
	
	public void execPkgKedEmp (HashMap<String, String> param){
		selectOne("rbs.modules.kedCntc.KedCntcMapper.execP_KED_EMP", param);
    }
	
	public void execPkgKedFnc (HashMap<String, String> param){
		selectOne("rbs.modules.kedCntc.KedCntcMapper.execP_KED_FNC", param);
    }
	
	public void execPkgKedAnal (HashMap<String, String> param){
		selectOne("rbs.modules.kedCntc.KedCntcMapper.execP_KED_ANAL", param);
    }
	
	public void execPkgKedSpcss (HashMap<String, String> param){
		selectOne("rbs.modules.kedCntc.KedCntcMapper.execP_KED_SPCSS", param);
    }
	
}
