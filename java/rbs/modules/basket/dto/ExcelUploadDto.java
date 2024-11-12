package rbs.modules.basket.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class ExcelUploadDto {
	
	private Integer fleIdx;
	
	private String itemId;
	private String uploadFileName;
	private String uploadFilePath;
	private String resultFileName;
	private String resultFilePath;
	
	private Integer fileSize;
	
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
	
	private Integer admsfmIdx;
	
	

	public Integer getFleIdx() {
		return fleIdx;
	}

	public void setFleIdx(Integer fleIdx) {
		this.fleIdx = fleIdx;
	}

	public String getItemId() {
		return itemId;
	}
	
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}
	
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getUploadFilePath() {
		return uploadFilePath;
	}
	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}
	public String getResultFileName() {
		return resultFileName;
	}
	public void setResultFileName(String resultFileName) {
		this.resultFileName = resultFileName;
	}
	public String getResultFilePath() {
		return resultFilePath;
	}
	public void setResultFilePath(String resultFilePath) {
		this.resultFilePath = resultFilePath;
	}
	
	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
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
	
	public Integer getAdmsfmIdx() {
		return admsfmIdx;
	}

	public void setAdmsfmIdx(Integer admsfmIdx) {
		this.admsfmIdx = admsfmIdx;
	}

	public ExcelUploadDto(Integer fleIdx, String itemId, String uploadFileName, String uploadFilePath, String resultFileName,
			String resultFilePath, int fileSize, String regiIdx, String regiId, String regiName, Date regiDate, String regiIp,
			String lastModiIdx, String lastModiId, String lastModiName, Date lastModiDate, String lastModiIp, int admsfmIdx) {
		super();
		this.fleIdx = fleIdx;
		this.itemId = itemId;
		this.uploadFileName = uploadFileName;
		this.uploadFilePath = uploadFilePath;
		this.resultFileName = resultFileName;
		this.resultFilePath = resultFilePath;
		this.fileSize = fileSize;
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
		this.admsfmIdx = admsfmIdx;
	}

	public ExcelUploadDto() {
		super();
	}

	@Override
	public String toString() {
		return "ExcelUploadDto [fleIdx=" + fleIdx + ", itemId=" + itemId + ", uploadFileName=" + uploadFileName + ", uploadFilePath="
				+ uploadFilePath + ", resultFileName=" + resultFileName + ", resultFilePath=" + resultFilePath+ ", fileSize=" + fileSize
				+ ", regiIdx=" + regiIdx + ", regiId=" + regiId + ", regiName=" + regiName + ", regiDate=" + regiDate
				+ ", regiIp=" + regiIp + ", lastModiIdx=" + lastModiIdx + ", lastModiId=" + lastModiId
				+ ", lastModiName=" + lastModiName + ", lastModiDate=" + lastModiDate + ", lastModiIp=" + lastModiIp + ", admsfmIdx=" + admsfmIdx
				+ "]";
	}
	
	
	
	

	

}
