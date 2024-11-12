package rbs.modules.report.dto;

import java.util.Date;
import java.util.List;

public class Report{
	private Integer cnslIdx;
	private Integer reprtIdx;
	private String confmStatus;
	private String isdelete;
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
	private String reprtCn;
	private String confmCn;
	private String bplNo;
	
	private List<ReportFileDTO> files;
	
	public List<ReportFileDTO> getFiles() {
		return files;
	}

	public Integer getCnslIdx() {
		return cnslIdx;
	}
	public Integer getReprtIdx() {
		return reprtIdx;
	}
	public String getConfmStatus() {
		return confmStatus;
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
	public String getLastModiIdx() {
		return lastModiIdx;
	}
	public String getLastModiId() {
		return lastModiId;
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
	public String getReprtCn() {
		return reprtCn;
	}
	public String getConfmCn() {
		return confmCn;
	}
	public String getBplNo() {
		return bplNo;
	}
	
	public static class Builder {
		private Integer cnslIdx;
		private Integer reprtIdx;
		private String confmStatus;
		private String isdelete;
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
		private String reprtCn;
		private String confmCn;
		private String bplNo;
		private List<ReportFileDTO> files;
		
		public Builder(Integer cnslIdx) {
			this.cnslIdx = cnslIdx;
		}

		public Builder setCnslIdx(Integer cnslIdx) {
			this.cnslIdx = cnslIdx;
			return this;
		}

		public Builder setReprtIdx(Integer reprtIdx) {
			this.reprtIdx = reprtIdx;
			return this;
		}

		public Builder setConfmStatus(String confmStatus) {
			this.confmStatus = confmStatus;
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

		public Builder setLastModiIdx(String lastModiIdx) {
			this.lastModiIdx = lastModiIdx;
			return this;
		}

		public Builder setLastModiId(String lastModiId) {
			this.lastModiId = lastModiId;
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

		public Builder setReprtCn(String reprtCn) {
			this.reprtCn = reprtCn;
			return this;
		}

		public Builder setConfmCn(String confmCn) {
			this.confmCn = confmCn;
			return this;
		}
		
		public Builder setBplNo(String bplNo) {
			this.bplNo = bplNo;
			return this;
		}
		
		public Builder setFiles(List<ReportFileDTO> files) {
			this.files = files;
			return this;
		}

		public Report build() {
			Report report = new Report();
			report.cnslIdx = cnslIdx;
			report.reprtIdx = reprtIdx;
			report.confmStatus = confmStatus;
			report.isdelete = isdelete;
			report.regiIdx = regiIdx;
			report.regiId = regiId;
			report.regiName = regiName;
			report.regiDate = regiDate;
			report.regiIp = regiIp;
			report.lastModiIdx = lastModiIdx;
			report.lastModiId = lastModiId;
			report.lastModiName = lastModiName;
			report.lastModiDate = lastModiDate;
			report.lastModiIp = lastModiIp;
			report.reprtCn = reprtCn;
			report.confmCn = confmCn;
			report.files = files;
			report.bplNo = bplNo;
			return report;
			
		}
		
		
	}

	@Override
	public String toString() {
		return "Report [cnslIdx=" + cnslIdx + ", reprtIdx=" + reprtIdx
				+ ", confmStatus=" + confmStatus + ", isdelete=" + isdelete
				+ ", regiIdx=" + regiIdx + ", regiId=" + regiId + ", regiName="
				+ regiName + ", regiDate=" + regiDate + ", regiIp=" + regiIp
				+ ", lastModiIdx=" + lastModiIdx + ", lastModiId=" + lastModiId
				+ ", lastModiName=" + lastModiName + ", lastModiDate="
				+ lastModiDate + ", lastModiIp=" + lastModiIp + ", reprtCn="
				+ reprtCn + ", bplNo=" + bplNo + ", confmCn=" + confmCn + ", files=" + files + "]";
	}


	
	
	
	
	

}
