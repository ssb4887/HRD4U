package rbs.modules.basket.dto;

import java.util.Date;
import java.util.List;

public class BskClPassivDto {

	private String bplNo;

	private int lclasIdx;
	private int lclasCd;

	private String lclasNm;
	private String sclasNm;
	
	private int sclasIdx;
	private List<Integer> sclasCd;
	

	private String status;


	
	//수동분류 시 예외기업 등록
	private String startDate;
	private String endDate;
	


	private String isdelete;

	private String regiIdx;

	private String regiId;

	private String regiName;

	private Date regiDate;

	private String regiIp;
	
	
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<Integer> getSclasCd() {
		return sclasCd;
	}

	public void setSclasCd(List<Integer> sclasCd) {
		this.sclasCd = sclasCd;
	}

	public String getSclasNm() {
		return sclasNm;
	}

	public void setSclasNm(String sclasNm) {
		this.sclasNm = sclasNm;
	}

	public String getLclasNm() {
		return lclasNm;
	}

	public void setLclasNm(String lclasNm) {
		this.lclasNm = lclasNm;
	}

	public String getBplNo() {
		return bplNo;
	}

	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}

	public int getLclasIdx() {
		return lclasIdx;
	}

	public void setLclasIdx(int lclasIdx) {
		this.lclasIdx = lclasIdx;
	}

	public int getLclasCd() {
		return lclasCd;
	}

	public void setLclasCd(int lclasCd) {
		this.lclasCd = lclasCd;
	}

	public int getSclasIdx() {
		return sclasIdx;
	}

	public void setSclasIdx(int sclasIdx) {
		this.sclasIdx = sclasIdx;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}

	public String getRegiIdx() {
		return regiIdx;
	}

	public void setRegiIdx(String regiIdx) {
		this.regiIdx = regiIdx;
	}

	public String getRegiId() {
		return regiId;
	}

	public void setRegiId(String regiId) {
		this.regiId = regiId;
	}

	public String getRegiName() {
		return regiName;
	}

	public void setRegiName(String regiName) {
		this.regiName = regiName;
	}

	public Date getRegiDate() {
		return regiDate;
	}

	public void setRegiDate(Date regiDate) {
		this.regiDate = regiDate;
	}

	public String getRegiIp() {
		return regiIp;
	}

	public void setRegiIp(String regiIp) {
		this.regiIp = regiIp;
	}

	public BskClPassivDto(String bplNo, int lclasIdx, int lclasCd, String lclasNm, String sclasNm, int sclasIdx,
			List<Integer> sclasCd, String status, String startDate, String endDate, String isdelete, String regiIdx,
			String regiId, String regiName, Date regiDate, String regiIp) {
		super();
		this.bplNo = bplNo;
		this.lclasIdx = lclasIdx;
		this.lclasCd = lclasCd;
		this.lclasNm = lclasNm;
		this.sclasNm = sclasNm;
		this.sclasIdx = sclasIdx;
		this.sclasCd = sclasCd;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isdelete = isdelete;
		this.regiIdx = regiIdx;
		this.regiId = regiId;
		this.regiName = regiName;
		this.regiDate = regiDate;
		this.regiIp = regiIp;
	}

	public BskClPassivDto() {
		super();
	}

	@Override
	public String toString() {
		return "BskClPassivDto [bplNo=" + bplNo + ", lclasIdx=" + lclasIdx + ", lclasCd=" + lclasCd + ", lclasNm="
				+ lclasNm + ", sclasNm=" + sclasNm + ", sclasIdx=" + sclasIdx + ", sclasCd=" + sclasCd + ", status="
				+ status + ", startDate=" + startDate + ", endDate=" + endDate + ", isdelete=" + isdelete + ", regiIdx="
				+ regiIdx + ", regiId=" + regiId + ", regiName=" + regiName + ", regiDate=" + regiDate + ", regiIp="
				+ regiIp + "]";
	}

	

}
