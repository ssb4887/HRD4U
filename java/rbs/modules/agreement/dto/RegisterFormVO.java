package rbs.modules.agreement.dto;

public class RegisterFormVO {
	String agremNo;
	String bplNo;
	int agremIdx;
	public String getAgremNo() {
		return agremNo;
	}
	public void setAgremNo(String agrem_no) {
		this.agremNo = agrem_no;
	}
	public String getBplNo() {
		return bplNo;
	}
	public void setBplNo(String bpl_no) {
		this.bplNo = bpl_no;
	}
	public void setAgremIdx(int agrem_idx) {
		this.agremIdx = agrem_idx;
	}
	public int getAgremIdx() {
		return this.agremIdx;
	}
}
