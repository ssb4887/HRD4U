package rbs.modules.report.dto;

import java.util.Date;

public class ReportFileDTO {	
	private Integer cnslIdx;
	private Integer reprtIdx;
	private Integer fleIdx;
	private String itemId;
	private String fileSavedName;
	private String fileOriginName;
	private long fileSize;
	private String fileText;
	private Integer dwnCnt;
	private Integer orderIdx;
	private String isdelete;	
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
	
	
	public Integer getCnslIdx() {
		return cnslIdx;
	}
	public Integer getReprtIdx() {
		return reprtIdx;
	}

	public Integer getFleIdx() {
		return fleIdx;
	}
	public String getItemId() {
		return itemId;
	}
	public String getFileSavedName() {
		return fileSavedName;
	}
	public String getFileOriginName() {
		return fileOriginName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public String getFileText() {
		return fileText;
	}
	public Integer getDwnCnt() {
		return dwnCnt;
	}
	public Integer getOrderIdx() {
		return orderIdx;
	}
	public String getIsdelete() {
		return isdelete;
	}
	public String getRegiIdx() {
		return regiIdx;
	}
	public String getRegiId() {
		return regiId;
	}
	public String getRegiName() {
		return regiName;
	}
	public Date getRegiDate() {
		return regiDate;
	}
	public String getRegiIp() {
		return regiIp;
	}
	public String getLastModiId() {
		return lastModiId;
	}
	public String getLastModiIdx() {
		return lastModiIdx;
	}
	public String getLastModiName() {
		return lastModiName;
	}
	public Date getLastModiDate() {
		return lastModiDate;
	}
	public String getLastModiIp() {
		return lastModiIp;
	}
	
	public static class Builder {
		private Integer cnslIdx;
		private Integer reprtIdx;
		private Integer fleIdx;
		private String itemId;
		private String fileSavedName;
		private String fileOriginName;
		private long fileSize;
		private String fileText;
		private Integer dwnCnt;
		private Integer orderIdx;
		private String isdelete;	
		private String regiIdx;					// 등록식별색인
		private String regiId;					// 등록아이디
		private String regiName;				// 등록이름
		private Date regiDate;					// 등록아이피
		private String regiIp;					// 최종수정식별색인
		private String lastModiId;				// 최종수정아이디
		private String lastModiIdx;				// 최종수정식별색인
		private String lastModiName;			// 최종수정이름
		private Date lastModiDate;				// 최종수정일자
		private String lastModiIp;	
		
		public Builder(Integer cnslIdx) {
			this.cnslIdx = cnslIdx;
		}



		public Builder setReprtIdx(Integer reprtIdx) {
			this.reprtIdx = reprtIdx;
			return this;
		}

		public Builder setFleIdx(Integer fleIdx) {
			this.fleIdx = fleIdx;
			return this;
		}

		public Builder setItemId(String itemId) {
			this.itemId = itemId;
			return this;
		}

		public Builder setFileSavedName(String fileSavedName) {
			this.fileSavedName = fileSavedName;
			return this;
		}

		public Builder setFileOriginName(String fileOriginName) {
			this.fileOriginName = fileOriginName;
			return this;
		}

		public Builder setFileSize(long fileSize) {
			this.fileSize = fileSize;
			return this;
		}

		public Builder setFileText(String fileText) {
			this.fileText = fileText;
			return this;
		}

		public Builder setDwnCnt(Integer dwnCnt) {
			this.dwnCnt = dwnCnt;
			return this;
		}

		public Builder setOrderIdx(Integer orderIdx) {
			this.orderIdx = orderIdx;
			return this;
		}

		public Builder setIsdelete(String isdelete) {
			this.isdelete = isdelete;
			return this;
		}

		public Builder setRegiIdx(String regiIdx) {
			this.regiIdx = regiIdx;
			return this;
		}

		public Builder setRegiId(String regiId) {
			this.regiId = regiId;
			return this;
		}

		public Builder setRegiName(String regiName) {
			this.regiName = regiName;
			return this;
		}

		public Builder setRegiDate(Date regiDate) {
			this.regiDate = regiDate;
			return this;
		}

		public Builder setRegiIp(String regiIp) {
			this.regiIp = regiIp;
			return this;
		}

		public Builder setLastModiId(String lastModiId) {
			this.lastModiId = lastModiId;
			return this;
		}

		public Builder setLastModiIdx(String lastModiIdx) {
			this.lastModiIdx = lastModiIdx;
			return this;
		}

		public Builder setLastModiName(String lastModiName) {
			this.lastModiName = lastModiName;
			return this;
		}

		public Builder setLastModiDate(Date lastModiDate) {
			this.lastModiDate = lastModiDate;
			return this;
		}

		public Builder setLastModiIp(String lastModiIp) {
			this.lastModiIp = lastModiIp;
			return this;
		}
		
		public ReportFileDTO build() {
			ReportFileDTO reportFileDTO = new ReportFileDTO();
			reportFileDTO.cnslIdx = cnslIdx;
			reportFileDTO.reprtIdx = reprtIdx;
			reportFileDTO.fleIdx = fleIdx;
			reportFileDTO.itemId = itemId;
			reportFileDTO.fileSavedName = fileSavedName;
			reportFileDTO.fileOriginName = fileOriginName;
			reportFileDTO.fileSize = fileSize;
			reportFileDTO.fileText = fileText;
			reportFileDTO.dwnCnt = dwnCnt;
			reportFileDTO.orderIdx = orderIdx;
			reportFileDTO.isdelete = isdelete;
			reportFileDTO.regiIdx = regiIdx;			
			reportFileDTO.regiId = regiId;
			reportFileDTO.regiName = regiName;			
			reportFileDTO.regiDate = regiDate;				
			reportFileDTO.regiIp = regiIp;			
			reportFileDTO.lastModiId = lastModiId;			
			reportFileDTO.lastModiIdx = lastModiIdx;		
			reportFileDTO.lastModiName = lastModiName;	
			reportFileDTO.lastModiDate = lastModiDate;		
			reportFileDTO.lastModiIp = lastModiIp;
			
			return reportFileDTO;
			
		}
	}

	@Override
	public String toString() {
		return "ReportFileDTO [cnslIdx=" + cnslIdx + ", reprtIdx=" + reprtIdx
				+ ", fleIdx=" + fleIdx + ", itemId=" + itemId
				+ ", fileSavedName=" + fileSavedName + ", fileOriginName="
				+ fileOriginName + ", fileSize=" + fileSize + ", fileText="
				+ fileText + ", dwnCnt=" + dwnCnt + ", orderIdx=" + orderIdx
				+ ", isdelete=" + isdelete + ", regiIdx=" + regiIdx
				+ ", regiId=" + regiId + ", regiName=" + regiName
				+ ", regiDate=" + regiDate + ", regiIp=" + regiIp
				+ ", lastModiId=" + lastModiId + ", lastModiIdx=" + lastModiIdx
				+ ", lastModiName=" + lastModiName + ", lastModiDate="
				+ lastModiDate + ", lastModiIp=" + lastModiIp + "]";
	}
	
	
	

}
