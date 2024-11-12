package rbs.modules.basket.dto;

import java.util.Date;
import java.util.List;

public class BskClAtmcDto {
	
	private String bplNo;
	
	private int lclasIdx;
	
	private Date startDate;
	private Date endDate;
	
	private String status;
	
	private String isdelete;
	
	private int lclasCd;
	
	private String lclasNm;
	
	private List<BskClAtmcSclasDto> bskClAtmcSclases;

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public int getLclasCd() {
		return lclasCd;
	}

	public void setLclasCd(int lclasCd) {
		this.lclasCd = lclasCd;
	}

	public List<BskClAtmcSclasDto> getBskClAtmcSclases() {
		return bskClAtmcSclases;
	}

	public void setBskClAtmcSclases(List<BskClAtmcSclasDto> bskClAtmcSclases) {
		this.bskClAtmcSclases = bskClAtmcSclases;
	}

	public String getLclasNm() {
		return lclasNm;
	}

	public void setLclasNm(String lclasNm) {
		this.lclasNm = lclasNm;
	}

	public BskClAtmcDto(String bplNo, int lclasIdx, Date startDate, Date endDate, String status, String isdelete,
			int lclasCd, String lclasNm, List<BskClAtmcSclasDto> bskClAtmcSclases) {
		super();
		this.bplNo = bplNo;
		this.lclasIdx = lclasIdx;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.isdelete = isdelete;
		this.lclasCd = lclasCd;
		this.lclasNm = lclasNm;
		this.bskClAtmcSclases = bskClAtmcSclases;
	}

	public BskClAtmcDto() {
		super();
	}

	@Override
	public String toString() {
		return "BskClAtmcDto [bplNo=" + bplNo + ", lclasIdx=" + lclasIdx + ", startDate=" + startDate + ", endDate="
				+ endDate + ", status=" + status + ", isdelete=" + isdelete + ", lclasCd=" + lclasCd + ", lclasNm="
				+ lclasNm + ", bskClAtmcSclases=" + bskClAtmcSclases + "]";
	}

	
	
	

}
