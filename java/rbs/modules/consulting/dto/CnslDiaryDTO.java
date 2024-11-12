package rbs.modules.consulting.dto;

import java.util.Date;

public class CnslDiaryDTO {
	private int cnslIdx;				// 컨설팅식별색인
	private int diaryIdx;				// 일지식별색인
	private Date mtgStartDt;			// 회의시작일시
	private Date mtgEndDt;				// 회의종료일시
	private String mtgWeekExplsn1;		// 회의주제명1
	private String mtgCn1;				// 회의내용1
	private int excOdr;					// 수행차수
	private String mtgWeekExplsn2;		// 회의주제명2
	private String mtgCn2;				// 회의내용2
	private char sportType;				// 지원유형
	private char operMthd;				// 운영방식
	private String pmNm;				// PM성명
	private String cnExpert;			// 내용전문가
	private String corpInnerExpert;		// 기업내부전문가
	private String spntPic;				// 지원기관담당자
	private String bplNm;				// 사업장명
	private String excMth;				// 수행방법
	private char isDelete;				// 삭제가부
	private String regiIdx;				// 등록식별색인
	private String regiId;				// 등록아이디
	private String regiName;			// 등록이름
	private Date regiDate;				// 등록아이피
	private String regiIp;				// 최종수정식별색인
	private String lastModiId;			// 최종수정아이디
	private String lastModiIdx;			// 최종수정식별색인
	private String lastModiName;		// 최종수정이름
	private Date lastModiDate;			// 최종수정일자
	private String lastModiIp;			// 최종수정아이피
	
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
	public Date getMtgStartDt() {
		return mtgStartDt;
	}
	public void setMtgStartDt(Date mtgStartDt) {
		this.mtgStartDt = mtgStartDt;
	}
	public Date getMtgEndDt() {
		return mtgEndDt;
	}
	public void setMtgEndDt(Date mtgEndDt) {
		this.mtgEndDt = mtgEndDt;
	}
	public String getMtgWeekExplsn1() {
		return mtgWeekExplsn1;
	}
	public void setMtgWeekExplsn1(String mtgWeekExplsn1) {
		this.mtgWeekExplsn1 = mtgWeekExplsn1;
	}
	public String getMtgCn1() {
		return mtgCn1;
	}
	public void setMtgCn1(String mtgCn1) {
		this.mtgCn1 = mtgCn1;
	}
	public int getExcOdr() {
		return excOdr;
	}
	public void setExcOdr(int excOdr) {
		this.excOdr = excOdr;
	}
	public String getMtgWeekExplsn2() {
		return mtgWeekExplsn2;
	}
	public void setMtgWeekExplsn2(String mtgWeekExplsn2) {
		this.mtgWeekExplsn2 = mtgWeekExplsn2;
	}
	public String getMtgCn2() {
		return mtgCn2;
	}
	public void setMtgCn2(String mtgCn2) {
		this.mtgCn2 = mtgCn2;
	}
	public char getSportType() {
		return sportType;
	}
	public void setSportType(char sportType) {
		this.sportType = sportType;
	}
	public char getOperMthd() {
		return operMthd;
	}
	public void setOperMthd(char operMthd) {
		this.operMthd = operMthd;
	}
	public String getPmNm() {
		return pmNm;
	}
	public void setPmNm(String pmNm) {
		this.pmNm = pmNm;
	}
	public String getCnExpert() {
		return cnExpert;
	}
	public void setCnExpert(String cnExpert) {
		this.cnExpert = cnExpert;
	}
	public String getCorpInnerExpert() {
		return corpInnerExpert;
	}
	public void setCorpInnerExpert(String corpInnerExpert) {
		this.corpInnerExpert = corpInnerExpert;
	}
	public String getSpntPic() {
		return spntPic;
	}
	public void setSpntPic(String spntPic) {
		this.spntPic = spntPic;
	}
	public String getBplNm() {
		return bplNm;
	}
	public void setBplNm(String bplNm) {
		this.bplNm = bplNm;
	}
	public String getExcMth() {
		return excMth;
	}
	public void setExcMth(String excMth) {
		this.excMth = excMth;
	}
	public char getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(char isDelete) {
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
	
	@Override
	public String toString() {
		return "CnslDiaryDTO [cnslIdx=" + cnslIdx + ", diaryIdx=" + diaryIdx + ", mtgStartDt=" + mtgStartDt
				+ ", mtgEndDt=" + mtgEndDt + ", mtgWeekExplsn1=" + mtgWeekExplsn1 + ", mtgCn1=" + mtgCn1 + ", excOdr="
				+ excOdr + ", mtgWeekExplsn2=" + mtgWeekExplsn2 + ", mtgCn2=" + mtgCn2 + ", sportType=" + sportType
				+ ", operMthd=" + operMthd + ", pmNm=" + pmNm + ", cnExpert=" + cnExpert + ", corpInnerExpert="
				+ corpInnerExpert + ", spntPic=" + spntPic + ", bplNm=" + bplNm + ", excMth=" + excMth + ", isDelete="
				+ isDelete + ", regiIdx=" + regiIdx + ", regiId=" + regiId + ", regiName=" + regiName + ", regiDate="
				+ regiDate + ", regiIp=" + regiIp + ", lastModiId=" + lastModiId + ", lastModiIdx=" + lastModiIdx
				+ ", lastModiName=" + lastModiName + ", lastModiDate=" + lastModiDate + ", lastModiIp=" + lastModiIp
				+ "]";
	}
}
