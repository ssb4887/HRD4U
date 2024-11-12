package rbs.modules.basket.dto;

import java.util.Date;

public class BskClLogDto {
	
	private String bplNo;
	private Date regiDate;
	private String sort;
	private String lclasNm;
	private String regiName;
	private String sclasNm;
	public String getBplNo() {
		return bplNo;
	}
	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}
	public Date getRegiDate() {
		return regiDate;
	}
	public void setRegiDate(Date regiDate) {
		this.regiDate = regiDate;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getLclasNm() {
		return lclasNm;
	}
	public void setLclasNm(String lclasNm) {
		this.lclasNm = lclasNm;
	}
	public String getRegiName() {
		return regiName;
	}
	public void setRegiName(String regiName) {
		this.regiName = regiName;
	}
	public String getSclasNm() {
		return sclasNm;
	}
	public void setSclasNm(String sclasNm) {
		this.sclasNm = sclasNm;
	}
	public BskClLogDto(String bplNo, Date regiDate, String sort, String lclasNm, String regiName, String sclasNm) {
		super();
		this.bplNo = bplNo;
		this.regiDate = regiDate;
		this.sort = sort;
		this.lclasNm = lclasNm;
		this.regiName = regiName;
		this.sclasNm = sclasNm;
	}
	public BskClLogDto() {
		super();
	}
	@Override
	public String toString() {
		return "ClLogDto [bplNo=" + bplNo + ", regiDate=" + regiDate + ", sort=" + sort + ", lclasNm=" + lclasNm
				+ ", regiName=" + regiName + ", sclasNm=" + sclasNm + "]";
	}
	
	
	

}
