package rbs.modules.recommend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RecommendDummyService {
	public String getName(int type, int order);
	public boolean getSojtible(int code);
	public List<Map> diagnosisBizNames(Integer regulars, Integer code, PRISUP type);
	public List<Map> basicBizNames(long idx);
	public Map getBSK(String bpl_no);
	public List<Map> getTrends(Map bsk);
	public boolean insertQR(Map mapped);
	public List<QrVO> getQR();
	public List<Map> trainHistory(String bpl_no);
	public List<Map> moneyHistory(String bpl_no);
	public Map getDoctor(String bpl_no);
	public void insertBSC(Map<String,Object> model, String BPL_NO, String regiIp);
	public String getBSCIDX(long RLST_IDX);
	public List<Map> getNCSCODES();
	public List<Map> getPRTBIZ();
	public List<Map> getPRTBIZTP(PrtbizVO prtbizVO);
	public void insertChart(Map charts);
	public int insertBSISCNSLinform(Map<String,Object> model, long RLST_IDX, String regiIp);
	public Map getBsisByRslt(long rslt_idx);
	
	public List<Map> getRCTRBizs(long bsiscnsl_idx);
	public List<Map> getTrendsByBsis(long bsiscnsl_idx);
	public List<Map> trainHistoryBSIS(long bsiscnsl_idx);
	public List<Map> moneyHistoryBSIS(String bpl_no, long bsiscnsl_idx);
	public Map getDoctorBSIS(long bsiscnsl_idx);
	public List<Map> bsisList(); // 임시. 테스트 및 운영에선 안쓸거임
	public long getRSLTbyBsis(long bsiscnsl_idx);
	public List<Map> bsisRecom(long bsiscnsl_idx);
	public void updateBsiscnsl(Map map, String regiIp);
	public Map getCorpPIC(long rslt_idx);
	public void updateMax(List<Map> inputs, String name);
	public Map getLimitInduty(String bpl_no);
	public void updateQR(List<Map> list);
	public List<String> getInsttList();
	public List<Map> searchDoc(DoctorVO params);
	public Map getBplNo(String bizNum);
	public List<Map> getDocByMemberidx(String memberidx);
	public List<Map> getDocByZipcode(String bpl_no);
	public Map getPicDoctor(String bpl_no);
	public Map getDoctorInPicInstt(String bpl_no);
	public Map getBscDoc(long rslt_idx);
}