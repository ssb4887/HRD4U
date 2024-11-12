package rbs.modules.taskhub.dto;

public class SearchFormVO {
	int page;
	String bplNo;
	String insttName;
	String corpName;
	int bscStatus;
	int rsltStatus;
	int bsisStatus;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getBplNo() {
		return bplNo;
	}
	public void setBplNo(String bpl_no) {
		this.bplNo = bpl_no;
	}
	public String getInsttName() {
		return insttName;
	}
	public void setInsttName(String instt_name) {
		this.insttName = instt_name;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corp_name) {
		this.corpName = corp_name;
	}
	public int getBscStatus() {
		return bscStatus;
	}
	public void setBscStatus(int bsc_status) {
		this.bscStatus = bsc_status;
	}
	public int getRsltStatus() {
		return rsltStatus;
	}
	public void setRsltStatus(int rslt_status) {
		this.rsltStatus = rslt_status;
	}
	public int getBsisStatus() {
		return bsisStatus;
	}
	public void setBsisStatus(int bsis_status) {
		this.bsisStatus = bsis_status;
	}
	
}
