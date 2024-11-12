package rbs.modules.recommend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("recommendDummyMapper")
public class RecommendDummyMapper extends EgovAbstractMapper {
	public String getName(Map<String, Integer> param) {
		return (String)selectOne("rbs.modules.recommend.recommendDummyMapper.selectName", param);
	}
	
	public Integer getSojtible(Map<String, Integer> param) {
		return (Integer)selectOne("rbs.modules.recommend.recommendDummyMapper.sojtible", param);
	}
	
	public List<Map> diagnosisBizNames(Map<String, Integer> param) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.diagnosisBizName", param);
	}
	
	public List<Map> basicBizNames(long bsc_idx) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.basicBizName", bsc_idx);
	}
	
	public Map getBSK(String BPL_NO) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getBSK", BPL_NO);
	}
	
	public List<Map> getTrends(Map bsk) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.getTrends", bsk);
	}
	
	public void insertQR(Map mapped) {
		insert("rbs.modules.recommend.recommendDummyMapper.insertQR", mapped);
	}
	
	public List<QrVO> selectQR() {
		return selectList("rbs.modules.recommend.recommendDummyMapper.selectQR");
	}
	
	public List<Map> trainHistory(long BPL_NO) {
		return selectList("rbs.modules.recommend.recommendDummyMapper2.pilot", BPL_NO);
	}
	public List<Map> moneyHistory(long BPL_NO) {
		return selectList("rbs.modules.recommend.recommendDummyMapper2.money", BPL_NO);
	}
	public Map getDoctor(String BPL_NO) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getDoc", BPL_NO);
	}
	public void insertBSC(Map<String,Object> model) {
		insert("rbs.modules.recommend.recommendDummyMapper.insertBSC", model);
	}
	public void insertCandid(Map<String,Object> model) {
		// throw new Exception("take it easy~");
		insert("rbs.modules.recommend.recommendDummyMapper.insertCandid", model);
	}
	
	public Map getIssue(String BPL_NO) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getIssue", BPL_NO);
	}
	public String getBSCIDX(long RLST_IDX) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getBSCIDX", RLST_IDX);
	}
	public List<Map> getNCSCODES() {
		return selectList("rbs.modules.recommend.recommendDummyMapper.getNCSCODES");
	}
	public List<Map> getPRTBIZ() {
		return selectList("rbs.modules.recommend.recommendDummyMapper.getPRTBIZ");
	}
	public List<Map> getPRTBIZTP(PrtbizVO prtbizVO) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.getPRTBIZTP", prtbizVO);
	}
	public void insertCharts(Map charts) {
		insert("rbs.modules.recommend.recommendDummyMapper.insertCharts", charts);
	}
	public int getBSCIDX(String issue_no) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getBSCIDXbyISSUE", issue_no);
	}
	public Map getIssueBSISCNSL(long RLST_IDX) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getIssueBSISCNSL", RLST_IDX);
	}
	public void insertBSISOthers(Map model) {
		insert("rbs.modules.recommend.recommendDummyMapper.insertBSISOthers", model);
	}
	public Map getBsisByRslt(long rslt_idx) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getBsisByRSLT", rslt_idx);
	}
	public List<Map> getRCTRBizs(long bsiscnsl_idx) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.getRCTRBizs", bsiscnsl_idx);
	}
	public List<Map> getRCTRCourses(long bsiscnsl_idx) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.getRCTRCourses", bsiscnsl_idx);
	}
	public List<Map> getTrendsByBsis(long bsiscnsl_idx) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.getTrendsByBsis", bsiscnsl_idx);
	}
	public List<Map> trainHistoryBsis(long bsiscnsl_idx) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.trainHistoryBsis", bsiscnsl_idx);
	}
	public List<Map> moneyHistoryBsis(long bsiscnsl_idx) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.moneyHistoryBsis", bsiscnsl_idx);
	}
	public Map getDoctorBsis(long bsiscnsl_idx) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getDoctorBsis", bsiscnsl_idx);
	}
	public long insertBSISCNSLinform(Map model) {
		return insert("rbs.modules.recommend.recommendDummyMapper.insertBSISCNSLinform", model);
	}
	public List<Map> bsisList() {
		return selectList("rbs.modules.recommend.recommendDummyMapper.bsisList");
	}
	public long getRSLTbyBsis(long bsiscnsl_idx) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getRSLTbyBsis", bsiscnsl_idx);
	}
	
	public List<Map> bsisRecom(long bsiscnsl_idx) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.bsisRecom", bsiscnsl_idx);
	}
	
	public void updateDoc(Map map) {
		update("rbs.modules.recommend.recommendDummyMapper.updateDoc", map);
	}
	public void updateRCTRbiz(Map map) {
		update("rbs.modules.recommend.recommendDummyMapper.updateRCTRbiz", map);
	}
	public List<Map> getMaxpays(String bpl_no) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.getMaxPay", bpl_no);
	}
	public Map getCorpPIC(long rslt_idx) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getCorpPIC", rslt_idx);
	}
	
	public void updateMaxBSC(List<Map> inputs) {
		update("rbs.modules.recommend.recommendDummyMapper.updateMaxBSC", inputs);
	}
	public void updateMaxBSIS(List<Map> inputs) {
		update("rbs.modules.recommend.recommendDummyMapper.updateMaxBSIS", inputs);
	}
	public Map getLimitInduty(String bpl_no) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getLimitInduty", bpl_no);
	}
	public void updateQR(QrVO qr) {
		update("rbs.modules.recommend.recommendDummyMapper.updateQR", qr);
	}
	public void updateRCTbizTP(Map tp) {
		update("rbs.modules.recommend.recommendDummyMapper.updateRCTbizTP", tp);
	}
	public void cleanupRCTbizTP(Map recommend) {
		delete("rbs.modules.recommend.recommendDummyMapper.cleanupRCTbizTP", recommend);
	}
	public List<Map> bsisRecomCourse(Map recom) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.bsisRecomCourse", recom);
	}
	public List<String> getInsttList() {
		return selectList("rbs.modules.recommend.recommendDummyMapper.getInsttList");
	}
	public List<Map> searchDoc(DoctorVO params) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.searchDoc", params);
	}
	public Map getBplNo(String bizNum) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getBplNo", bizNum);
	}
	public List<Map> trainHistory(String BPL_NO) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.getTrainHis", BPL_NO);
	}
	
	public List<Map> moneyHistory(String BPL_NO) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.getMoneyHis", BPL_NO);
	}
	
	public int getChartsCount(Map param) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getChartsCount", param);
	}
	
	public void updateCharts(Map param) {
		update("rbs.modules.recommend.recommendDummyMapper.updateCharts", param);
	}
	public List<Map> getDocByMemberidx(String memberidx) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.getDocByMemberidx", memberidx);
	}
	public List<Map> getDocByZipcode(String bpl_no) {
		return selectList("rbs.modules.recommend.recommendDummyMapper.getDocByZipcode", bpl_no);
	}
	public void insertBSIStps(Map param) {
		insert("rbs.modules.recommend.recommendDummyMapper.insertBSIStps", param);
	}
	
	public Map getPicDoctor(String bpl_no) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getPicDoctor", bpl_no);
	}
	
	public Map getDoctorInPicInstt(String bpl_no) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getDoctorInPicInstt", bpl_no);
	}
	
	public Map getCoreDoctor(String bpl_no) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getCoreDoctor", bpl_no);
	}
	
	public Map getIssueByDoc(Map param) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getIssueByDoc", param);
	}
	
	public Map getIssueBSISCNSLByDoc(Map param) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getIssueBSISCNSLByDoc", param);
	}
	
	public Map getBscDoc(long rslt_idx) {
		return selectOne("rbs.modules.recommend.recommendDummyMapper.getBscDoc", rslt_idx);
	}
}
