package rbs.modules.consulting.dto;

import java.util.Date;

public class CnslEvlDTO {
	private int evlIdx;				// 평가식별색인
	private String hrpId;			// 전문가인력풀ID
	private String entrstRoleCd;	// 위촉역할코드
	private int sortSn;				// 정렬순번
	private int cnslIdx;			// 컨설팅식별색인
	private int diaryIdx;			// 일지식별색인
	private Date evlDe;				// 평가일자
	private int evlItem1;			// 평가항목1
	private int evlItem2;			// 평가항목2
	private int evlItem3;			// 평가항목3
	private int evlItem4;			// 평가항목4
	private int evlItem5;			// 평가항목5
	private String inptBgndt;		// 투입시작일
	private String inputEnddt;		// 투입종료일
	private String inptBgntm;		// 투입시작시간
	private String inptEndtm;		// 투입종료시간
	private int evlTotPoint;		// 평가총점
	private String evlGrad;			// 평가등급
	private String gnrlzOption;		// 종합의견
	private String bizNm;			// 사업명
	private Date actDe;				// 활동일자
	private int actDaycnt;			// 활동일수
	private String actRoleCd;		// 활동역할코드
	private int allwnc;				// 수당
	private String brffcNm;			// 지부지사명
	private String fatherSign;		// 부서명
	private String picNm;			// 담당자명
	private char rspnsYn;			// 응답여부
	private String inoutSe;			// 내외구분
	private String hrpSe;			// 전문가인력풀 구분
	private String evlStatus;		// 평가상태
	private char isDelete;			// 삭제가부
	private String regiIdx;			// 등록식별색인
	private String regiId;			// 등록아이디
	private String regiName;		// 등록이름
	private Date regiDate;			// 등록아이피
	private String regiIp;			// 최종수정식별색인
	private String lastModiId;		// 최종수정아이디
	private String lastModiIdx;		// 최종수정식별색인
	private String lastModiName;	// 최종수정이름
	private Date lastModiDate;		// 최종수정일자
	private String lastModiIp;		// 최종수정아이피
	
	public int getEvlIdx() {
		return evlIdx;
	}
	public void setEvlIdx(int evlIdx) {
		this.evlIdx = evlIdx;
	}
	public String getHrpId() {
		return hrpId;
	}
	public void setHrpId(String hrpId) {
		this.hrpId = hrpId;
	}
	public String getEntrstRoleCd() {
		return entrstRoleCd;
	}
	public void setEntrstRoleCd(String entrstRoleCd) {
		this.entrstRoleCd = entrstRoleCd;
	}
	public int getSortSn() {
		return sortSn;
	}
	public void setSortSn(int sortSn) {
		this.sortSn = sortSn;
	}
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
	public Date getEvlDe() {
		return evlDe;
	}
	public void setEvlDe(Date evlDe) {
		this.evlDe = evlDe;
	}
	public int getEvlItem1() {
		return evlItem1;
	}
	public void setEvlItem1(int evlItem1) {
		this.evlItem1 = evlItem1;
	}
	public int getEvlItem2() {
		return evlItem2;
	}
	public void setEvlItem2(int evlItem2) {
		this.evlItem2 = evlItem2;
	}
	public int getEvlItem3() {
		return evlItem3;
	}
	public void setEvlItem3(int evlItem3) {
		this.evlItem3 = evlItem3;
	}
	public int getEvlItem4() {
		return evlItem4;
	}
	public void setEvlItem4(int evlItem4) {
		this.evlItem4 = evlItem4;
	}
	public int getEvlItem5() {
		return evlItem5;
	}
	public void setEvlItem5(int evlItem5) {
		this.evlItem5 = evlItem5;
	}
	public String getInptBgndt() {
		return inptBgndt;
	}
	public void setInptBgndt(String inptBgndt) {
		this.inptBgndt = inptBgndt;
	}
	public String getInputEnddt() {
		return inputEnddt;
	}
	public void setInputEnddt(String inputEnddt) {
		this.inputEnddt = inputEnddt;
	}
	public String getInptBgntm() {
		return inptBgntm;
	}
	public void setInptBgntm(String inptBgntm) {
		this.inptBgntm = inptBgntm;
	}
	public String getInptEndtm() {
		return inptEndtm;
	}
	public void setInptEndtm(String inptEndtm) {
		this.inptEndtm = inptEndtm;
	}
	public int getEvlTotPoint() {
		return evlTotPoint;
	}
	public void setEvlTotPoint(int evlTotPoint) {
		this.evlTotPoint = evlTotPoint;
	}
	public String getEvlGrad() {
		return evlGrad;
	}
	public void setEvlGrad(String evlGrad) {
		this.evlGrad = evlGrad;
	}
	public String getGnrlzOption() {
		return gnrlzOption;
	}
	public void setGnrlzOption(String gnrlzOption) {
		this.gnrlzOption = gnrlzOption;
	}
	public String getBizNm() {
		return bizNm;
	}
	public void setBizNm(String bizNm) {
		this.bizNm = bizNm;
	}
	public Date getActDe() {
		return actDe;
	}
	public void setActDe(Date actDe) {
		this.actDe = actDe;
	}
	public int getActDaycnt() {
		return actDaycnt;
	}
	public void setActDaycnt(int actDaycnt) {
		this.actDaycnt = actDaycnt;
	}
	public String getActRoleCd() {
		return actRoleCd;
	}
	public void setActRoleCd(String actRoleCd) {
		this.actRoleCd = actRoleCd;
	}
	public int getAllwnc() {
		return allwnc;
	}
	public void setAllwnc(int allwnc) {
		this.allwnc = allwnc;
	}
	public String getBrffcNm() {
		return brffcNm;
	}
	public void setBrffcNm(String brffcNm) {
		this.brffcNm = brffcNm;
	}
	public String getFatherSign() {
		return fatherSign;
	}
	public void setFatherSign(String fatherSign) {
		this.fatherSign = fatherSign;
	}
	public String getPicNm() {
		return picNm;
	}
	public void setPicNm(String picNm) {
		this.picNm = picNm;
	}
	public char getRspnsYn() {
		return rspnsYn;
	}
	public void setRspnsYn(char rspnsYn) {
		this.rspnsYn = rspnsYn;
	}
	public String getInoutSe() {
		return inoutSe;
	}
	public void setInoutSe(String inoutSe) {
		this.inoutSe = inoutSe;
	}
	public String getHrpSe() {
		return hrpSe;
	}
	public void setHrpSe(String hrpSe) {
		this.hrpSe = hrpSe;
	}
	public String getEvlStatus() {
		return evlStatus;
	}
	public void setEvlStatus(String evlStatus) {
		this.evlStatus = evlStatus;
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
		return "CnslEvlDTO [evlIdx=" + evlIdx + ", hrpId=" + hrpId + ", entrstRoleCd=" + entrstRoleCd + ", sortSn="
				+ sortSn + ", cnslIdx=" + cnslIdx + ", diaryIdx=" + diaryIdx + ", evlDe=" + evlDe + ", evlItem1="
				+ evlItem1 + ", evlItem2=" + evlItem2 + ", evlItem3=" + evlItem3 + ", evlItem4=" + evlItem4
				+ ", evlItem5=" + evlItem5 + ", inptBgndt=" + inptBgndt + ", inputEnddt=" + inputEnddt + ", inptBgntm="
				+ inptBgntm + ", inptEndtm=" + inptEndtm + ", evlTotPoint=" + evlTotPoint + ", evlGrad=" + evlGrad
				+ ", gnrlzOption=" + gnrlzOption + ", bizNm=" + bizNm + ", actDe=" + actDe + ", actDaycnt=" + actDaycnt
				+ ", actRoleCd=" + actRoleCd + ", allwnc=" + allwnc + ", brffcNm=" + brffcNm + ", fatherSign="
				+ fatherSign + ", picNm=" + picNm + ", rspnsYn=" + rspnsYn + ", inoutSe=" + inoutSe + ", hrpSe=" + hrpSe
				+ ", evlStatus=" + evlStatus + ", isDelete=" + isDelete + ", regiIdx=" + regiIdx + ", regiId=" + regiId
				+ ", regiName=" + regiName + ", regiDate=" + regiDate + ", regiIp=" + regiIp + ", lastModiId="
				+ lastModiId + ", lastModiIdx=" + lastModiIdx + ", lastModiName=" + lastModiName + ", lastModiDate="
				+ lastModiDate + ", lastModiIp=" + lastModiIp + "]";
	}
}
