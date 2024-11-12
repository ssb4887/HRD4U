package rbs.modules.sojt.service;

import java.util.List;
import java.util.Map;

public interface SojtService {
	public Map getApplyContent(Map param);
	public void putApply(Map param);
	public void editApply(Map param);
	public List<Map> getInitList(Map param);
	public List<Map> getUserList(Map param);
	public List<Map> amIapplying(Map param);
	public List<Map> getApplyList4Instt(Map param);
	public void updateStatus(Map param);
	public List<Map> getAcceptList4Head(Map param);
	public List<Map> getAcceptList4Instt(Map param);
	public List<Map> getApplyList4Head(Map param);
	public Map getAcceptContent(Map param);
	public boolean confirmAccept(Map param);
	public boolean confirmFinal(Map param);
	public List<Map> getResultList4Head(Map param);
	public List<Map> getResultList4Instt(Map param);
	public int checkValidity(Map param);
	public Map<String, Object> getBrffcCd(String substring) throws Exception;
	public String getBrffcTel(Map<String, Object> param) throws Exception;
	public boolean withdraw(Map param);
	public int getSojtIdxFromWithdraw(String bsiscnsl_idx);
}