package rbs.modules.basket.dto;

import java.util.Date;

public class HashTagDto {
	
	private int insttIdx;
	
	private String hashtagCd;
	private String hashtagNm;
	private String hashtagRemarks;
	private String isDelete;
	
	private String regiIdx;
	private String regiId;
	private String regiName;
	private Date regiDate;
	private String regiIp;
	
	private String lastModiIdx;
	private String lastModiId;
	private String lastModiName;
	private Date lastModiDate;
	private String lastModiIp;
	
	
	public int getInsttIdx() {
		return insttIdx;
	}
	public void setInsttIdx(int insttIdx) {
		this.insttIdx = insttIdx;
	}
	public String getHashtagCd() {
		return hashtagCd;
	}
	public void setHashtagCd(String hashtagCd) {
		this.hashtagCd = hashtagCd;
	}
	public String getHashtagNm() {
		return hashtagNm;
	}
	public void setHashtagNm(String hashtagNm) {
		this.hashtagNm = hashtagNm;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getRegiIdx() {
		return regiIdx;
	}
	public void setRegiIdx(String regiIdx) {
		this.regiIdx = regiIdx;
	}
	public String getHashtagRemarks() {
		return hashtagRemarks;
	}
	public void setHashtagRemarks(String hashtagRemarks) {
		this.hashtagRemarks = hashtagRemarks;
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
	public String getLastModiIdx() {
		return lastModiIdx;
	}
	public void setLastModiIdx(String lastModiIdx) {
		this.lastModiIdx = lastModiIdx;
	}
	public String getLastModiId() {
		return lastModiId;
	}
	public void setLastModiId(String lastModiId) {
		this.lastModiId = lastModiId;
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
	public HashTagDto(int insttIdx, String hashtagCd, String hashtagNm, String isDelete, String regiIdx, String regiId, String hashtagRemarks,
			String regiName, Date regiDate, String regiIp, String lastModiIdx, String lastModiId, String lastModiName,
			Date lastModiDate, String lastModiIp) {
		super();
		this.insttIdx = insttIdx;
		this.hashtagCd = hashtagCd;
		this.hashtagNm = hashtagNm;
		this.isDelete = isDelete;
		this.hashtagRemarks = hashtagRemarks;
		this.regiIdx = regiIdx;
		this.regiId = regiId;
		this.regiName = regiName;
		this.regiDate = regiDate;
		this.regiIp = regiIp;
		this.lastModiIdx = lastModiIdx;
		this.lastModiId = lastModiId;
		this.lastModiName = lastModiName;
		this.lastModiDate = lastModiDate;
		this.lastModiIp = lastModiIp;
	}
	public HashTagDto() {
		super();
	}
	@Override
	public String toString() {
		return "HashTagDto [insttIdx=" + insttIdx + ", hashtagCd=" + hashtagCd + ", hashtagNm=" + hashtagNm + ", hashtagRemarks=" + hashtagRemarks
				+ ", isDelete=" + isDelete + ", regiIdx=" + regiIdx + ", regiId=" + regiId + ", regiName=" + regiName
				+ ", regiDate=" + regiDate + ", regiIp=" + regiIp + ", lastModiIdx=" + lastModiIdx + ", lastModiId="
				+ lastModiId + ", lastModiName=" + lastModiName + ", lastModiDate=" + lastModiDate + ", lastModiIp="
				+ lastModiIp + "]";
	}
	
	
	

}
