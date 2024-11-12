package rbs.modules.consulting.dto;

import java.util.Date;
import java.util.List;

public class CnslTeamDTO {
	private Integer cnslIdx;
	private Integer chgIdx;
	
	private List<CnslTeamMemberDTO> members;
	
	private String confmStatus;
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
	public void setCnslIdx(Integer cnslIdx) {
		this.cnslIdx = cnslIdx;
	}
	public Integer getChgIdx() {
		return chgIdx;
	}
	public void setChgIdx(Integer chgIdx) {
		this.chgIdx = chgIdx;
	}
	public List<CnslTeamMemberDTO> getMembers() {
		return members;
	}
	public void setMembers(List<CnslTeamMemberDTO> members) {
		this.members = members;
	}
	public String getConfmStatus() {
		return confmStatus;
	}
	public void setConfmStatus(String confmStatus) {
		this.confmStatus = confmStatus;
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
	public CnslTeamDTO(Integer cnslIdx, Integer chgIdx,
			List<CnslTeamMemberDTO> members, String confmStatus,
			String isdelete, String regiIdx, String regiId, String regiName,
			Date regiDate, String regiIp, String lastModiId, String lastModiIdx,
			String lastModiName, Date lastModiDate, String lastModiIp) {
		super();
		this.cnslIdx = cnslIdx;
		this.chgIdx = chgIdx;
		this.members = members;
		this.confmStatus = confmStatus;
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
	public CnslTeamDTO() {
		super();
	}
	@Override
	public String toString() {
		return "CnslTeamDTO [cnslIdx=" + cnslIdx + ", chgIdx=" + chgIdx
				+ ", members=" + members + ", confmStatus=" + confmStatus
				+ ", isdelete=" + isdelete + ", regiIdx=" + regiIdx
				+ ", regiId=" + regiId + ", regiName=" + regiName
				+ ", regiDate=" + regiDate + ", regiIp=" + regiIp
				+ ", lastModiId=" + lastModiId + ", lastModiIdx=" + lastModiIdx
				+ ", lastModiName=" + lastModiName + ", lastModiDate="
				+ lastModiDate + ", lastModiIp=" + lastModiIp + "]";
	}
	
	
	
	
}
