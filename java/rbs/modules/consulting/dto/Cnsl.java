package rbs.modules.consulting.dto;

import java.util.Date;
import java.util.List;

import rbs.modules.report.dto.Report;

public class Cnsl {
	
	private Integer cnslIdx;					// 컨설팅식별색인
	private Integer chgIdx;						// 컨설팅변경식별색인
	private Integer bsiscnslIdx;				// 기초컨설팅식별색인
	private Integer bscIdx;						// 기초진단지식별색인

	private String bplNo;					// 사업장관리번호
	private String cnslType;				// 컨설팅타입
	private Integer cnslTme;				// 컨설팅회차
	private String cnslStartDate;			// 컨설팅시작일자
	private String cnslEndDate;				// 컨설팅종료일자
	private String corpNm; 					// 기업명
	private String reperNm; 				// 대표자명
	private String bizrNo;					// 사업자등록번호
	private String zip;						// 우편번호
	private String bplAddr;					// 사업장 주소
	private String bplAddrDtl;				// 사업장상세주소
	private String telno;					// 전화번호
	
	private Integer totWorkCnt;				// 상시근로자수
	private String indutyNm; 				// 업종명
	
	private String trOprtnRegionZip;		// 훈련실시지역시도
	private String trOprtnRegionAddr;		// 훈련실시지역구군
	private String trOprtnRegionAddrDtl;	// 훈련실시지역구군
	
	private String recomendInsttNm;			// 추천기관명
	private String cnslDemandMatter;		// 컨설팅요구사항
	
	private String corpPicNm;				// 기업담당자명
	private String corpPicOfcps;			// 기업담당자직위
	private String corpPicTelno;			// 기업담당자전화번호
	private String corpPicEmail;			// 기업담당자이메일
	
	private String spnt;					// 지원기관명
	private String spntNm;					// 지원기관코드
	private String spntTelno;				// 지원기관전화번호
	
	private String cmptncBrffcIdx;			// 관할지부지사식별색인
	private String cmptncBrffcNm;			// 관할지부지사식별색인
	private String cmptncBrffcPicIdx;		// 관할지부지사담당자식별색인
	private String cmptncBrffcPicTelno;		// 관할지부지사담당자식별색인
	
	private String dtyCl;					// 직무분류(NCS코드)
	private String dtyClNm;					// 직무분류(NCS이름)
	private String mainKwrd1;				// 주요키워드1
	private String mainKwrd2;				// 주요키워드2
	private String mainKwrd3;				// 주요키워드3
	
	
	private String confmStatus;				// 상태
	
	
	private String isdelete;				// 삭제가부
	
	private String confmCn; 				// 반려의견
	
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
	
	private Integer limitCount;
	
	
	private CnslTeamDTO cnslTeam;
	
	private Report report;
	
	private List<CnslFileDTO> cnslFiles;
	
	//수행일지
	
	
	

	public Integer getCnslIdx() {
		return cnslIdx;
	}

	public Integer getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
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

	public Integer getBsiscnslIdx() {
		return bsiscnslIdx;
	}

	public void setBsiscnslIdx(Integer bsiscnslIdx) {
		this.bsiscnslIdx = bsiscnslIdx;
	}

	public Integer getBscIdx() {
		return bscIdx;
	}

	public void setBscIdx(Integer bscIdx) {
		this.bscIdx = bscIdx;
	}

	public String getBplNo() {
		return bplNo;
	}

	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}

	public String getCnslType() {
		return cnslType;
	}

	public void setCnslType(String cnslType) {
		this.cnslType = cnslType;
	}

	public Integer getCnslTme() {
		return cnslTme;
	}

	public void setCnslTme(Integer cnslTme) {
		this.cnslTme = cnslTme;
	}

	public String getCnslStartDate() {
		return cnslStartDate;
	}

	public void setCnslStartDate(String cnslStartDate) {
		this.cnslStartDate = cnslStartDate;
	}

	public String getCnslEndDate() {
		return cnslEndDate;
	}

	public void setCnslEndDate(String cnslEndDate) {
		this.cnslEndDate = cnslEndDate;
	}

	public String getCorpNm() {
		return corpNm;
	}

	public void setCorpNm(String corpNm) {
		this.corpNm = corpNm;
	}

	public String getReperNm() {
		return reperNm;
	}

	public void setReperNm(String reperNm) {
		this.reperNm = reperNm;
	}

	public String getBizrNo() {
		return bizrNo;
	}

	public void setBizrNo(String bizrNo) {
		this.bizrNo = bizrNo;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getBplAddr() {
		return bplAddr;
	}

	public void setBplAddr(String bplAddr) {
		this.bplAddr = bplAddr;
	}

	public String getBplAddrDtl() {
		return bplAddrDtl;
	}

	public void setBplAddrDtl(String bplAddrDtl) {
		this.bplAddrDtl = bplAddrDtl;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public Integer getTotWorkCnt() {
		return totWorkCnt;
	}

	public void setTotWorkCnt(Integer totWorkCnt) {
		this.totWorkCnt = totWorkCnt;
	}

	public String getIndutyNm() {
		return indutyNm;
	}

	public void setIndutyNm(String indutyNm) {
		this.indutyNm = indutyNm;
	}

	public String getTrOprtnRegionZip() {
		return trOprtnRegionZip;
	}

	public void setTrOprtnRegionZip(String trOprtnRegionZip) {
		this.trOprtnRegionZip = trOprtnRegionZip;
	}

	public String getTrOprtnRegionAddr() {
		return trOprtnRegionAddr;
	}

	public void setTrOprtnRegionAddr(String trOprtnRegionAddr) {
		this.trOprtnRegionAddr = trOprtnRegionAddr;
	}

	public String getTrOprtnRegionAddrDtl() {
		return trOprtnRegionAddrDtl;
	}

	public void setTrOprtnRegionAddrDtl(String trOprtnRegionAddrDtl) {
		this.trOprtnRegionAddrDtl = trOprtnRegionAddrDtl;
	}

	public String getRecomendInsttNm() {
		return recomendInsttNm;
	}

	public void setRecomendInsttNm(String recomendInsttNm) {
		this.recomendInsttNm = recomendInsttNm;
	}

	public String getCnslDemandMatter() {
		return cnslDemandMatter;
	}

	public void setCnslDemandMatter(String cnslDemandMatter) {
		this.cnslDemandMatter = cnslDemandMatter;
	}

	public String getCorpPicNm() {
		return corpPicNm;
	}

	public void setCorpPicNm(String corpPicNm) {
		this.corpPicNm = corpPicNm;
	}

	public String getCorpPicOfcps() {
		return corpPicOfcps;
	}

	public void setCorpPicOfcps(String corpPicOfcps) {
		this.corpPicOfcps = corpPicOfcps;
	}

	public String getCorpPicTelno() {
		return corpPicTelno;
	}

	public void setCorpPicTelno(String corpPicTelno) {
		this.corpPicTelno = corpPicTelno;
	}

	public String getCorpPicEmail() {
		return corpPicEmail;
	}

	public void setCorpPicEmail(String corpPicEmail) {
		this.corpPicEmail = corpPicEmail;
	}

	public String getSpnt() {
		return spnt;
	}

	public void setSpnt(String spnt) {
		this.spnt = spnt;
	}

	public String getSpntNm() {
		return spntNm;
	}

	public void setSpntNm(String spntNm) {
		this.spntNm = spntNm;
	}

	public String getSpntTelno() {
		return spntTelno;
	}

	public void setSpntTelno(String spntTelno) {
		this.spntTelno = spntTelno;
	}

	public String getCmptncBrffcIdx() {
		return cmptncBrffcIdx;
	}

	public void setCmptncBrffcIdx(String cmptncBrffcIdx) {
		this.cmptncBrffcIdx = cmptncBrffcIdx;
	}

	public String getCmptncBrffcNm() {
		return cmptncBrffcNm;
	}

	public void setCmptncBrffcNm(String cmptncBrffcNm) {
		this.cmptncBrffcNm = cmptncBrffcNm;
	}

	public String getCmptncBrffcPicIdx() {
		return cmptncBrffcPicIdx;
	}

	public void setCmptncBrffcPicIdx(String cmptncBrffcPicIdx) {
		this.cmptncBrffcPicIdx = cmptncBrffcPicIdx;
	}

	public String getCmptncBrffcPicTelno() {
		return cmptncBrffcPicTelno;
	}

	public void setCmptncBrffcPicTelno(String cmptncBrffcPicTelno) {
		this.cmptncBrffcPicTelno = cmptncBrffcPicTelno;
	}

	public String getDtyCl() {
		return dtyCl;
	}

	public void setDtyCl(String dtyCl) {
		this.dtyCl = dtyCl;
	}

	public String getDtyClNm() {
		return dtyClNm;
	}

	public void setDtyClNm(String dtyClNm) {
		this.dtyClNm = dtyClNm;
	}

	public String getMainKwrd1() {
		return mainKwrd1;
	}

	public void setMainKwrd1(String mainKwrd1) {
		this.mainKwrd1 = mainKwrd1;
	}

	public String getMainKwrd2() {
		return mainKwrd2;
	}

	public void setMainKwrd2(String mainKwrd2) {
		this.mainKwrd2 = mainKwrd2;
	}

	public String getMainKwrd3() {
		return mainKwrd3;
	}

	public void setMainKwrd3(String mainKwrd3) {
		this.mainKwrd3 = mainKwrd3;
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

	public String getConfmCn() {
		return confmCn;
	}

	public void setConfmCn(String confmCn) {
		this.confmCn = confmCn;
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

	public CnslTeamDTO getCnslTeam() {
		return cnslTeam;
	}

	public void setCnslTeam(CnslTeamDTO cnslTeam) {
		this.cnslTeam = cnslTeam;
	}

	public List<CnslFileDTO> getCnslFiles() {
		return cnslFiles;
	}

	public void setCnslFiles(List<CnslFileDTO> cnslFiles) {
		this.cnslFiles = cnslFiles;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public Cnsl(Integer cnslIdx, Integer chgIdx, Integer bsiscnslIdx,
			Integer bscIdx, String bplNo, String cnslType, Integer cnslTme,
			String cnslStartDate, String cnslEndDate, String corpNm,
			String reperNm, String bizrNo, String zip, String bplAddr,
			String bplAddrDtl, String telno, Integer totWorkCnt,
			String indutyNm, String trOprtnRegionZip, String trOprtnRegionAddr,
			String trOprtnRegionAddrDtl, String recomendInsttNm,
			String cnslDemandMatter, String corpPicNm, String corpPicOfcps,
			String corpPicTelno, String corpPicEmail, String spnt,
			String spntNm, String spntTelno, String cmptncBrffcIdx,
			String cmptncBrffcNm, String cmptncBrffcPicIdx,
			String cmptncBrffcPicTelno, String dtyCl, String dtyClNm,
			String mainKwrd1, String mainKwrd2, String mainKwrd3,
			String confmStatus, String isdelete, String confmCn, String regiIdx,
			String regiId, String regiName, Date regiDate, String regiIp,
			String lastModiId, String lastModiIdx, String lastModiName,
			Date lastModiDate, String lastModiIp, Integer limitCount,
			CnslTeamDTO cnslTeam, Report report, List<CnslFileDTO> cnslFiles) {
		super();
		this.cnslIdx = cnslIdx;
		this.chgIdx = chgIdx;
		this.bsiscnslIdx = bsiscnslIdx;
		this.bscIdx = bscIdx;
		this.bplNo = bplNo;
		this.cnslType = cnslType;
		this.cnslTme = cnslTme;
		this.cnslStartDate = cnslStartDate;
		this.cnslEndDate = cnslEndDate;
		this.corpNm = corpNm;
		this.reperNm = reperNm;
		this.bizrNo = bizrNo;
		this.zip = zip;
		this.bplAddr = bplAddr;
		this.bplAddrDtl = bplAddrDtl;
		this.telno = telno;
		this.totWorkCnt = totWorkCnt;
		this.indutyNm = indutyNm;
		this.trOprtnRegionZip = trOprtnRegionZip;
		this.trOprtnRegionAddr = trOprtnRegionAddr;
		this.trOprtnRegionAddrDtl = trOprtnRegionAddrDtl;
		this.recomendInsttNm = recomendInsttNm;
		this.cnslDemandMatter = cnslDemandMatter;
		this.corpPicNm = corpPicNm;
		this.corpPicOfcps = corpPicOfcps;
		this.corpPicTelno = corpPicTelno;
		this.corpPicEmail = corpPicEmail;
		this.spnt = spnt;
		this.spntNm = spntNm;
		this.spntTelno = spntTelno;
		this.cmptncBrffcIdx = cmptncBrffcIdx;
		this.cmptncBrffcNm = cmptncBrffcNm;
		this.cmptncBrffcPicIdx = cmptncBrffcPicIdx;
		this.cmptncBrffcPicTelno = cmptncBrffcPicTelno;
		this.dtyCl = dtyCl;
		this.dtyClNm = dtyClNm;
		this.mainKwrd1 = mainKwrd1;
		this.mainKwrd2 = mainKwrd2;
		this.mainKwrd3 = mainKwrd3;
		this.confmStatus = confmStatus;
		this.isdelete = isdelete;
		this.confmCn = confmCn;
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
		this.limitCount = limitCount;
		this.cnslTeam = cnslTeam;
		this.report = report;
		this.cnslFiles = cnslFiles;
	}

	public Cnsl() {
		super();
	}

	@Override
	public String toString() {
		return "Cnsl [cnslIdx=" + cnslIdx + ", chgIdx=" + chgIdx
				+ ", bsiscnslIdx=" + bsiscnslIdx + ", bscIdx=" + bscIdx
				+ ", bplNo=" + bplNo + ", cnslType=" + cnslType + ", cnslTme="
				+ cnslTme + ", cnslStartDate=" + cnslStartDate
				+ ", cnslEndDate=" + cnslEndDate + ", corpNm=" + corpNm
				+ ", reperNm=" + reperNm + ", bizrNo=" + bizrNo + ", zip=" + zip
				+ ", bplAddr=" + bplAddr + ", bplAddrDtl=" + bplAddrDtl
				+ ", telno=" + telno + ", totWorkCnt=" + totWorkCnt
				+ ", indutyNm=" + indutyNm + ", trOprtnRegionZip="
				+ trOprtnRegionZip + ", trOprtnRegionAddr=" + trOprtnRegionAddr
				+ ", trOprtnRegionAddrDtl=" + trOprtnRegionAddrDtl
				+ ", recomendInsttNm=" + recomendInsttNm + ", cnslDemandMatter="
				+ cnslDemandMatter + ", corpPicNm=" + corpPicNm
				+ ", corpPicOfcps=" + corpPicOfcps + ", corpPicTelno="
				+ corpPicTelno + ", corpPicEmail=" + corpPicEmail + ", spnt="
				+ spnt + ", spntNm=" + spntNm + ", spntTelno=" + spntTelno
				+ ", cmptncBrffcIdx=" + cmptncBrffcIdx + ", cmptncBrffcNm="
				+ cmptncBrffcNm + ", cmptncBrffcPicIdx=" + cmptncBrffcPicIdx
				+ ", cmptncBrffcPicTelno=" + cmptncBrffcPicTelno + ", dtyCl="
				+ dtyCl + ", dtyClNm=" + dtyClNm + ", mainKwrd1=" + mainKwrd1
				+ ", mainKwrd2=" + mainKwrd2 + ", mainKwrd3=" + mainKwrd3
				+ ", confmStatus=" + confmStatus + ", isdelete=" + isdelete
				+ ", confmCn=" + confmCn + ", regiIdx=" + regiIdx + ", regiId="
				+ regiId + ", regiName=" + regiName + ", regiDate=" + regiDate
				+ ", regiIp=" + regiIp + ", lastModiId=" + lastModiId
				+ ", lastModiIdx=" + lastModiIdx + ", lastModiName="
				+ lastModiName + ", lastModiDate=" + lastModiDate
				+ ", lastModiIp=" + lastModiIp + ", limitCount=" + limitCount
				+ ", cnslTeam=" + cnslTeam + ", report=" + report
				+ ", cnslFiles=" + cnslFiles + "]";
	}

	
	



}
