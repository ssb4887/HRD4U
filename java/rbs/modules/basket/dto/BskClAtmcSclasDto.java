package rbs.modules.basket.dto;

public class BskClAtmcSclasDto {
	
	private String bplNo;
	
	private int lclasIdx;
	private int sclasIdx;
	
	private String isdelete;
	
	private int lclasCd;
	private int sclasCd;
	
	private String sclasNm;
	
	
	
	
	public String getSclasNm() {
		return sclasNm;
	}
	public void setSclasNm(String sclasNm) {
		this.sclasNm = sclasNm;
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
	public int getSclasIdx() {
		return sclasIdx;
	}
	public void setSclasIdx(int sclasIdx) {
		this.sclasIdx = sclasIdx;
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
	public int getSclasCd() {
		return sclasCd;
	}
	public void setSclasCd(int sclasCd) {
		this.sclasCd = sclasCd;
	}
	public BskClAtmcSclasDto(String bplNo, int lclasIdx, int sclasIdx, String isdelete, int lclasCd, int sclasCd,
			String sclasNm) {
		super();
		this.bplNo = bplNo;
		this.lclasIdx = lclasIdx;
		this.sclasIdx = sclasIdx;
		this.isdelete = isdelete;
		this.lclasCd = lclasCd;
		this.sclasCd = sclasCd;
		this.sclasNm = sclasNm;
	}
	public BskClAtmcSclasDto() {
		super();
	}
	@Override
	public String toString() {
		return "BskClAtmcSclasDto [bplNo=" + bplNo + ", lclasIdx=" + lclasIdx + ", sclasIdx=" + sclasIdx + ", isdelete="
				+ isdelete + ", lclasCd=" + lclasCd + ", sclasCd=" + sclasCd + ", sclasNm=" + sclasNm + "]";
	}
	


	
	
}
