package rbs.modules.basket.dto;

import java.util.Date;

public class ClSclasDto {

	private int lclasCd; //대분류코드

	private int sclasCd; //소분류코드

	private String sclasNm; //소분류명
	private String sclasCnd; //소분류조건
	private String sclasVal; //소분류조건값

	private String isdelete; // 삭제여부

	private String regiIdx;
	private String regiId;
	private String regiName;
	private Date regiDate;
	private String regiIp;

	private String lastModiId;
	private String lastModiIdx;
	private String lastModiName;
	private Date lastModiDate;
	private String lastModiIp;
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
	public String getSclasNm() {
		return sclasNm;
	}
	public void setSclasNm(String sclasNm) {
		this.sclasNm = sclasNm;
	}
	public String getSclasCnd() {
		return sclasCnd;
	}
	public void setSclasCnd(String sclasCnd) {
		this.sclasCnd = sclasCnd;
	}
	public String getSclasVal() {
		return sclasVal;
	}
	public void setSclasVal(String sclasVal) {
		this.sclasVal = sclasVal;
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
	public String getLastModiId() {
		return lastModiId;
	}
	public void setLastModiId(String lastModiId) {
		this.lastModiId = lastModiId;
	}
	public String getLastModiIdx() {
		return lastModiIdx;
	}
	public void setLastModiIdx(String lastModiIdx) {
		this.lastModiIdx = lastModiIdx;
	}
	public String getLastModiName() {
		return lastModiName;
	}
	public void setLastModiName(String lastModiName) {
		this.lastModiName = lastModiName;
	}
	public Date getLastModiDate() {
		return lastModiDate;
	}
	public void setLastModiDate(Date lastModiDate) {
		this.lastModiDate = lastModiDate;
	}
	public String getLastModiIp() {
		return lastModiIp;
	}
	public void setLastModiIp(String lastModiIp) {
		this.lastModiIp = lastModiIp;
	}
	public ClSclasDto(int lclasCd, int sclasCd, String sclasNm, String sclasCnd, String sclasVal, String isdelete,
			String regiIdx, String regiId, String regiName, Date regiDate, String regiIp, String lastModiId,
			String lastModiIdx, String lastModiName, Date lastModiDate, String lastModiIp) {
		super();
		this.lclasCd = lclasCd;
		this.sclasCd = sclasCd;
		this.sclasNm = sclasNm;
		this.sclasCnd = sclasCnd;
		this.sclasVal = sclasVal;
		this.isdelete = isdelete;
		this.regiIdx = regiIdx;
		this.regiId = regiId;
		this.regiName = regiName;
		this.regiDate = regiDate;
		this.regiIp = regiIp;
		this.lastModiId = lastModiId;
		this.lastModiIdx = lastModiIdx;
		this.lastModiName = lastModiName;
		this.lastModiDate = lastModiDate;
		this.lastModiIp = lastModiIp;
	}
	public ClSclasDto() {
		super();
	}
	@Override
	public String toString() {
		return "ClSclasDto [lclasCd=" + lclasCd + ", sclasCd=" + sclasCd + ", sclasNm=" + sclasNm + ", sclasCnd="
				+ sclasCnd + ", sclasVal=" + sclasVal + ", isdelete=" + isdelete + ", regiIdx=" + regiIdx + ", regiId="
				+ regiId + ", regiName=" + regiName + ", regiDate=" + regiDate + ", regiIp=" + regiIp + ", lastModiId="
				+ lastModiId + ", lastModiIdx=" + lastModiIdx + ", lastModiName=" + lastModiName + ", lastModiDate="
				+ lastModiDate + ", lastModiIp=" + lastModiIp + "]";
	}
	
	
	
	
	
	

}
