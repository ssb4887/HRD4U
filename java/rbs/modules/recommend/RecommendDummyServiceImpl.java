package rbs.modules.recommend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woowonsoft.egovframework.util.UserDetailsHelper;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import rbs.egovframework.LoginVO;

@Service("recommendDummyService")
public class RecommendDummyServiceImpl extends EgovAbstractServiceImpl implements RecommendDummyService {
	@Resource(name = "recommendDummyMapper")
	RecommendDummyMapper recommendDAO;
	
	@Override
	public String getName(int type, int ranking) {
		HashMap<String, Integer> param = new HashMap<String, Integer>();
		param.put("type", type);
		param.put("ranking", ranking);
		String result = recommendDAO.getName(param);
		return result;
	}

	@Override
	public boolean getSojtible(int code) {
		HashMap<String, Integer> param = new HashMap<String, Integer>();
		param.put("code", code);
		Integer result = recommendDAO.getSojtible(param);
		return result == 1;
	}

	@Override
	public List<Map> diagnosisBizNames(Integer regulars, Integer code, PRISUP type) {
		int rank = getRecommendNumber(regulars, code, type);
		HashMap<String, Integer> param = new HashMap<String, Integer>();
		param.put("type", rank);
		List<Map> result = recommendDAO.diagnosisBizNames(param);
		return result;
	}
	
	@Override
	public List<Map> basicBizNames(long idx) {
		List<Map> result = recommendDAO.basicBizNames(idx);
		return result;
	}
	
	private int getRecommendNumber(Integer regulars, Integer code, PRISUP type) {
		boolean sojtible = getSojtible(code);
		int result = 0;
		result = regulars < 20 && !sojtible ? 1 : result;
		result = regulars < 20 && sojtible ? 2 : result;
		result = regulars >= 20 && !sojtible ? 3 : result;
		result = regulars >= 20 && sojtible ? 4 : result;
		result = regulars >= 50 && !sojtible ? 5 : result;
		result = regulars >= 50 && sojtible ? 6 : result;
		result = type == PRISUP.MAJOR ? 7 : result;
		return result;
	}

	@Override
	public Map getBSK(String bpl_no) {
		return recommendDAO.getBSK(bpl_no);
	}

	@Override
	public List<Map> getTrends(Map bsk) {
		return recommendDAO.getTrends(bsk);
	}

	@Override
	public boolean insertQR(Map mapped) {
		recommendDAO.insertQR(mapped);
		return true;
	}

	@Override
	public List<QrVO> getQR() {
		return recommendDAO.selectQR();
	}

	@Override
	public List<Map> trainHistory(String bpl_no) {
		return recommendDAO.trainHistory(bpl_no);
	}
	
	@Override
	public List<Map> moneyHistory(String bpl_no) {
		List<Map> maxpays = recommendDAO.getMaxpays(bpl_no);
		List<Map> result = recommendDAO.moneyHistory(bpl_no);
		for(Map r : result) {
			String year = String.valueOf(r.get("YEAR"));
			for(Map m : maxpays) {
				String m_year = String.valueOf(m.get("YEAR"));
				if(year.equals(m_year)) {
					
					long max_pay = Long.valueOf(String.valueOf(m.get("MAX_PAY")));
					long tot_pay = Long.valueOf(String.valueOf(r.get("TOT_PAY")));
					float percent = Float.valueOf(((float)tot_pay)/(float)max_pay)*100l;
					r.put("PERCENT", String.format("%.1f",percent));
					r.put("MAX_PAY", max_pay);
				}
			}
		}
		return result;
	}

	@Override
	public Map getDoctor(String bpl_no) {
		return recommendDAO.getDoctor(bpl_no);
	}

	@Override
	@Transactional
	public void insertBSC(Map<String, Object> model, String BPL_NO, String regiIp) {
		Map doc = (Map) model.get("doc");
		Map issue = null;
		if(doc != null) {
			issue = recommendDAO.getIssueByDoc(doc);
		} else {
			issue = recommendDAO.getIssue(BPL_NO);
		}
		model.put("issue", issue);
		
		// 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}

		model.put("REGI_IDX", loginMemberIdx);
		model.put("REGI_ID", loginMemberId);
		model.put("REGI_NAME", loginMemberName);
		model.put("REGI_IP", regiIp);

		model.put("LAST_MODI_IDX", loginMemberIdx);
		model.put("LAST_MODI_ID", loginMemberId);
		model.put("LAST_MODI_NAME", loginMemberName);
		model.put("LAST_MODI_IP", regiIp);
		
		recommendDAO.insertBSC(model);
		recommendDAO.insertCandid(model);
	}

	@Override
	public String getBSCIDX(long RLST_IDX) {
		return recommendDAO.getBSCIDX(RLST_IDX);
	}

	@Override
	public List<Map> getNCSCODES() {
		return recommendDAO.getNCSCODES();
	}

	@Override
	public List<Map> getPRTBIZ() {
		return recommendDAO.getPRTBIZ();
	}

	@Override
	public List<Map> getPRTBIZTP(PrtbizVO prtbizVO) {
		return recommendDAO.getPRTBIZTP(prtbizVO);
	}

	@Override
	public void insertChart(Map charts) {
		int cnt = recommendDAO.getChartsCount(charts);
		if(cnt == 0) {
			recommendDAO.insertCharts(charts);
		} else {
			recommendDAO.updateCharts(charts);
		}
		
	}

	@Override
	@Transactional
	public int insertBSISCNSLinform(Map<String, Object> model, long rlst_idx, String regiIp) {
		Map doc = (Map) model.get("doc");
		Map issue = null;
		if(doc != null) {
			issue = recommendDAO.getIssueBSISCNSLByDoc(doc);
			if(doc.get("DOCTOR_TELNO") != null && doc.get("DOCTOR_TELNO") != "") {
				String doctorTelno = doc.get("DOCTOR_TELNO").toString();
				String formattedPhoneNumber = formatPhoneNumber(doctorTelno);
				doc.put("DOCTOR_TELNO", formattedPhoneNumber);
			}
			
		} else {
			issue = recommendDAO.getIssueBSISCNSL(rlst_idx);
		}
		model.put("issue", issue);
		
		// 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}

		model.put("REGI_IDX", loginMemberIdx);
		model.put("REGI_ID", loginMemberId);
		model.put("REGI_NAME", loginMemberName);
		model.put("REGI_IP", regiIp);

		model.put("LAST_MODI_IDX", loginMemberIdx);
		model.put("LAST_MODI_ID", loginMemberId);
		model.put("LAST_MODI_NAME", loginMemberName);
		model.put("LAST_MODI_IP", regiIp);
		
		recommendDAO.insertBSISCNSLinform(model);
		recommendDAO.insertBSISOthers(model);
		try {
			recommendDAO.insertBSIStps(model);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Map getBsisByRslt(long rslt_idx) {
		Map bsis = recommendDAO.getBsisByRslt(rslt_idx);
		// bsis gonna be null if the data having the rslt_idx wasn't in the table hrd_bsiscnsl
		return bsis;
	}

	@Override
	public List<Map> getRCTRBizs(long bsiscnsl_idx) {
		List<Map> rctrbiz = recommendDAO.getRCTRBizs(bsiscnsl_idx);
		List<Map> courses = recommendDAO.getRCTRCourses(bsiscnsl_idx);
		if(courses != null) {
			for(Map r : rctrbiz) {
				long biz_idx = Long.valueOf(String.valueOf(r.get("BIZ_IDX")));
				List<Map> c_list = new ArrayList<Map>();
				for(Map c : courses) {
					long c_biz_idx = Long.valueOf(String.valueOf(c.get("BIZ_IDX")));
					if(biz_idx == c_biz_idx) {
						c_list.add(c);
					}
				}
				r.put("courses", c_list);
			}
		}
		return rctrbiz;
	}

	@Override
	public List<Map> getTrendsByBsis(long bsiscnsl_idx) {
		return recommendDAO.getTrendsByBsis(bsiscnsl_idx);
	}

	@Override
	public List<Map> trainHistoryBSIS(long bsiscnsl_idx) {
		return recommendDAO.trainHistoryBsis(bsiscnsl_idx);
	}

	@Override
	public List<Map> moneyHistoryBSIS(String bpl_no, long bsiscnsl_idx) {
		List<Map> r = recommendDAO.moneyHistoryBsis(bsiscnsl_idx);
		for(Map m : r) {
			long max_pay = Long.valueOf(String.valueOf(m.get("MAX_PAY")));
			long tot_pay = Long.valueOf(String.valueOf(m.get("TOT_PAY")));
			float percent = Float.valueOf(((float)tot_pay)/(float)max_pay)*100l;
			m.put("PERCENT", String.format("%.1f",percent));
		}
		return r;
	}

	@Override
	public Map getDoctorBSIS(long bsiscnsl_idx) {
		return recommendDAO.getDoctorBsis(bsiscnsl_idx);
	}

	@Override
	public List<Map> bsisList() {
		return recommendDAO.bsisList();
	}

	@Override
	public long getRSLTbyBsis(long bsiscnsl_idx) {
		return (long)recommendDAO.getRSLTbyBsis(bsiscnsl_idx);
	}

	@Override
	public List<Map> bsisRecom(long bsiscnsl_idx) {
		List<Map> recoms = recommendDAO.bsisRecom(bsiscnsl_idx);
		for(Map r : recoms) {
			Map indexes = new HashMap();
			indexes.put("BSISCNSL_IDX", bsiscnsl_idx);
			indexes.put("BIZ_IDX", r.get("BIZ_IDX"));
			List<Map> courses = new ArrayList<Map>();
			if(r.containsKey("BIZ_IDX") && r.get("BIZ_IDX") != null) {
				courses = recommendDAO.bsisRecomCourse(indexes);
			}
			r.put("TPS", courses);
		}
		return recoms;
	}

	@Override
	public void updateBsiscnsl(Map map, String regiIp) {
		// 등록자 정보
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser(); // 로그인 사용자 정보
		String loginMemberIdx = null;
		String loginMemberId = null;
		String loginMemberName = null;
		if (loginVO != null) {
			loginMemberIdx = loginVO.getMemberIdx();
			loginMemberId = loginVO.getMemberId();
			loginMemberName = loginVO.getMemberName();
		}

		map.put("LAST_MODI_IDX", loginMemberIdx);
		map.put("LAST_MODI_ID", loginMemberId);
		map.put("LAST_MODI_NAME", loginMemberName);
		map.put("LAST_MODI_IP", regiIp);
				
		// 겸사 겸사 updateDoc에서 corp_pic 도 같이 한다.
		recommendDAO.updateDoc(map);
		List<Map> recommends = (List) map.get("recommends");
		for(Map r : recommends) {
			r.put("bsiscnsl_idx", map.get("bsis_idx"));
			recommendDAO.updateRCTRbiz(r);
			recommendDAO.cleanupRCTbizTP(r);
			List<Map> tps = (List<Map>)r.get("TPS");
			int course_idx = 0;
			for(Map tp : tps) {
				tp.put("bsiscnsl_idx", map.get("bsis_idx"));
				tp.put("biz_idx", r.get("BIZ_IDX"));
				if(tp.get("course_idx") == null) {
					tp.put("course_idx", course_idx++);
				}
				recommendDAO.updateRCTbizTP(tp);
			}
		}
	}

	@Override
	public Map getCorpPIC(long rslt_idx) {
		return recommendDAO.getCorpPIC(rslt_idx);
	}

	@Override
	public void updateMax(List<Map> inputs, String name) {
		if("BSC".equals(name)) {
			recommendDAO.updateMaxBSC(inputs);
		} else {
			recommendDAO.updateMaxBSIS(inputs);
		}
		
	}

	@Override
	public Map getLimitInduty(String bpl_no) {
		return recommendDAO.getLimitInduty(bpl_no);
	}

	@Override
	public void updateQR(List<Map> list) {
		for(Map map : list) {
			QrVO qr = new QrVO();
			qr.setImage((String)map.get("image"));
			qr.setTitle((String)map.get("title"));
			qr.setUrl((String)map.get("url"));
			qr.setManageIdx(Integer.parseInt((String)map.get("manageIdx")));
			recommendDAO.updateQR(qr);
		}
		
	}

	@Override
	public List<String> getInsttList() {
		return recommendDAO.getInsttList();
	}

	@Override
	public List<Map> searchDoc(DoctorVO params) {
		return recommendDAO.searchDoc(params);
	}

	@Override
	public Map getBplNo(String bizNum) {
		return recommendDAO.getBplNo(bizNum);
	}

	@Override
	public List<Map> getDocByMemberidx(String memberidx) {
		return recommendDAO.getDocByMemberidx(memberidx);
	}

	@Override
	public List<Map> getDocByZipcode(String bpl_no) {
		return recommendDAO.getDocByZipcode(bpl_no);
	}

	@Override
	public Map getPicDoctor(String bpl_no) {
		Map doc = recommendDAO.getCoreDoctor(bpl_no);
		if(doc == null || doc.isEmpty()) {
			doc = recommendDAO.getPicDoctor(bpl_no);
		}
		return doc;
	}

	@Override
	public Map getDoctorInPicInstt(String bpl_no) {
		return recommendDAO.getDoctorInPicInstt(bpl_no);
	}

	@Override
	public Map getBscDoc(long rslt_idx) {
		return recommendDAO.getBscDoc(rslt_idx);
	}
	
	private static String formatPhoneNumber(String input) {
		String inputValue = input.replaceAll("\\D", "");
		if(inputValue.length() == 8) {
			return inputValue.replaceAll("(\\d{4})(\\d{4})", "$1-$2");
		} else if(inputValue.length() == 9) {
			return inputValue.replaceAll("(\\d{2})(\\d{3})(\\d{4})", "$1-$2-$3");
		} else if(inputValue.length() == 10) {
			return inputValue.replaceAll("(\\d{3})(\\d{3})(\\d{4})", "$1-$2-$3");
		} else if(inputValue.length() == 11) {
			return inputValue.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
		} else {
			return input;
		}
	}
}
