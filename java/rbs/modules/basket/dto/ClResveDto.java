package rbs.modules.basket.dto;

import java.util.Date;

public class ClResveDto {
	
	private int resveIdx;			// 예약일련번호
		
	private String resveDate;		// 예약일자
	
	private String resveHour;		// 예약시간
	
	private String resveMinute;		// 예약분
	
	private Date startDate;			// 시작일자
	
	private Date endDate;			// 종료일자
	
	private String status;			// 상태
	
	private String result;			// 결과
	
	private String isDelete;		// 삭제여부
	
	private String regiIdx;			// 등록자일련번호
	
	private String regiId;			// 등록자ID
	
	private String regiName;		// 등록자명
	
	private Date regiDate;			// 등록일자
	
	private String regiIp;			// 등록IP
	
	private String lastModiIdx;		// 최중수정자일련번호
								
	private Date lastModiDate;		// 최중수정일자
	
	private String lastModiId;		// 최종수정자ID
	
	private String lastModiIp;		// 최종수정IP
		
	private String lastModiName;	// 최종수정자명
	
	@Override
	public String toString() {
		return "ClResveDto [resveIdx=" + resveIdx + ", resveDate=" + resveDate + ", resveHour=" + resveHour + ", status=" + status
				+ ", resveMinute=" + resveMinute + ", startDate=" + startDate + ", endDate=" + endDate + ", result="
				+ result + ", isDelete=" + isDelete + ", regiIdx=" + regiIdx + ", regiId=" + regiId + ", regiName="
				+ regiName + ", regiDate=" + regiDate + ", regiIp=" + regiIp + ", lastModiIdx=" + lastModiIdx
				+ ", lastModiDate=" + lastModiDate + ", lastModiId=" + lastModiId + ", lastModiIp=" + lastModiIp
				+ ", lastModiName=" + lastModiName + "]";
	}
	
	
	public int getResveIdx() {
		return resveIdx;
	}

	public void setResveIdx(int resveIdx) {
		this.resveIdx = resveIdx;
	}

	public String getResveDate() {
		return resveDate;
	}

	public void setResveDate(String resveDate) {
		this.resveDate = resveDate;
	}

	public String getResveHour() {
		return resveHour;
	}

	public void setResveHour(String resveHour) {
		this.resveHour = resveHour;
	}

	public String getResveMinute() {
		return resveMinute;
	}

	public void setResveMinute(String resveMinute) {
		this.resveMinute = resveMinute;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public Date getLastModiDate() {
		return lastModiDate;
	}

	public void setLastModiDate(Date lastModiDate) {
		this.lastModiDate = lastModiDate;
	}

	public String getLastModiId() {
		return lastModiId;
	}

	public void setLastModiId(String lastModiId) {
		this.lastModiId = lastModiId;
	}

	public String getLastModiIp() {
		return lastModiIp;
	}

	public void setLastModiIp(String lastModiIp) {
		this.lastModiIp = lastModiIp;
	}

	public String getLastModiName() {
		return lastModiName;
	}

	public void setLastModiName(String lastModiName) {
		this.lastModiName = lastModiName;
	}

}
