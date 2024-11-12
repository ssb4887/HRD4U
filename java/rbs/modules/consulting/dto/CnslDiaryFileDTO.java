package rbs.modules.consulting.dto;

import java.util.Date;

public class CnslDiaryFileDTO {
	private int cnslIdx;					// 컨설팅식별색인
	private int diaryIdx;					// 일지식별색인
	private int fleIdx;						// 파일식별색인
	private String itemId;					// 항목 ID
	private String fileSavedName;			// 저장파일이름
	private String fileOriginName;			// 원본파일이름
	private long fileSize;					// 파일사이즈
	private String fileText;				// 파일텍스트
	private int dwnCnt;						// 다운로드횟수
	private int orderIdx;					// 순서
	private String isdelete;				// 삭제가부
	private String regiIdx;					// 등록식별색인
	private String regiId;					// 등록아이디
	private String regiName;				// 등록이름
	private Date regiDate;					// 등록아이피
	private String regiIp;					// 최종수정식별색인
	private String lastModiId;				// 최종수정아이디
	private String lastModiIdx;				// 최종수정식별색인
	private String lastModiName;			// 최종수정이름
	private Date lastModiDate;				// 최종수정일자
	private String lastModiIp;				// 최종수정아이피
	
	public int getCnslIdx() {
		return cnslIdx;
	}
	public void setCnslIdx(int cnslIdx) {
		this.cnslIdx = cnslIdx;
	}
	public int getDiaryIdx() {
		return diaryIdx;
	}
	public void setDiaryIdx(int diaryIdx) {
		this.diaryIdx = diaryIdx;
	}
	public int getFleIdx() {
		return fleIdx;
	}
	public void setFleIdx(int fleIdx) {
		this.fleIdx = fleIdx;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getFileSavedName() {
		return fileSavedName;
	}
	public void setFileSavedName(String fileSavedName) {
		this.fileSavedName = fileSavedName;
	}
	public String getFileOriginName() {
		return fileOriginName;
	}
	public void setFileOriginName(String fileOriginName) {
		this.fileOriginName = fileOriginName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileText() {
		return fileText;
	}
	public void setFileText(String fileText) {
		this.fileText = fileText;
	}
	public int getDwnCnt() {
		return dwnCnt;
	}
	public void setDwnCnt(int dwnCnt) {
		this.dwnCnt = dwnCnt;
	}
	public int getOrderIdx() {
		return orderIdx;
	}
	public void setOrderIdx(int orderIdx) {
		this.orderIdx = orderIdx;
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
}
