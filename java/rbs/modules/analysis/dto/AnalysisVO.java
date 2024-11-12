package rbs.modules.analysis.dto;

import java.util.Date;

public class AnalysisVO {
	
	private int rsltAnalsIdx;			// 성과분석식별색인
	private int devlopIdx;				// 개발식별색인
	private String bizSe;				// 사업구분
	private String trCorpNm;			// 훈련기업명
	private String tpNm;				// 훈련과정명
	private String ncsSclasCd;			// NCS소분류코드
	private String trPeriod;			// 훈련기간
	private int trtm;					// 훈련시간
	private String trMth;				// 훈련방법
	private String trPurps;				// 훈련목적
	private int trTme;					// 훈련회차
	private String trResultReprtSe;		// 훈련결과보고서구분
	
	private char edcCrse1;				// 교육과정1
	private char edcCrse2;				// 교육과정2
	private char edcCrse3;				// 교육과정3
	private char edcCrse4;				// 교육과정4
	private char edcCrse5;				// 교육과정5
	
	private char gnrlz;					// 종합
	
	private char knwldgTchnlgyPickup1;	// 지식기술습득1
	private char knwldgTchnlgyPickup2;	// 지식기술습득2
	private char knwldgTchnlgyPickup3;	// 지식기술습득3
	private char knwldgTchnlgyPickup4;	// 지식기술습득4
		
	private char stoprtApplc1;			// 현업적용1
	private char stoprtApplc2;			// 현업적용2
	private char stoprtApplc3;			// 현업적용3
	private char stoprtApplc4;			// 현업적용4
	
	private Date reprtConfmDe;			// 보고서 승인일
	private String status;				// 상태
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
	
	// 훈련과정검색
	private String tpCd;				// 훈련코드
	private String tpTme;				// 훈련회차
	private Date trStartDate;			// 시작일
	private Date trEndDate;				// 종료일
	
	public int getRsltAnalsIdx() {
		return rsltAnalsIdx;
	}
	public void setRsltAnalsIdx(int rsltAnalsIdx) {
		this.rsltAnalsIdx = rsltAnalsIdx;
	}
	public int getDevlopIdx() {
		return devlopIdx;
	}
	public void setDevlopIdx(int devlopIdx) {
		this.devlopIdx = devlopIdx;
	}
	public String getBizSe() {
		return bizSe;
	}
	public void setBizSe(String bizSe) {
		this.bizSe = bizSe;
	}
	public String getTrCorpNm() {
		return trCorpNm;
	}
	public void setTrCorpNm(String trCorpNm) {
		this.trCorpNm = trCorpNm;
	}
	public String getTpNm() {
		return tpNm;
	}
	public void setTpNm(String tpNm) {
		this.tpNm = tpNm;
	}
	public String getNcsSclasCd() {
		return ncsSclasCd;
	}
	public void setNcsSclasCd(String ncsSclasCd) {
		this.ncsSclasCd = ncsSclasCd;
	}
	public String getTrPeriod() {
		return trPeriod;
	}
	public void setTrPeriod(String trPeriod) {
		this.trPeriod = trPeriod;
	}
	public int getTrtm() {
		return trtm;
	}
	public void setTrtm(int trtm) {
		this.trtm = trtm;
	}
	public String getTrMth() {
		return trMth;
	}
	public void setTrMth(String trMth) {
		this.trMth = trMth;
	}
	public String getTrPurps() {
		return trPurps;
	}
	public void setTrPurps(String trPurps) {
		this.trPurps = trPurps;
	}
	public int getTrTme() {
		return trTme;
	}
	public void setTrTme(int trTme) {
		this.trTme = trTme;
	}
	public String getTrResultReprtSe() {
		return trResultReprtSe;
	}
	public void setTrResultReprtSe(String trResultReprtSe) {
		this.trResultReprtSe = trResultReprtSe;
	}
	public char getEdcCrse1() {
		return edcCrse1;
	}
	public void setEdcCrse1(char edcCrse1) {
		this.edcCrse1 = edcCrse1;
	}
	public char getEdcCrse2() {
		return edcCrse2;
	}
	public void setEdcCrse2(char edcCrse2) {
		this.edcCrse2 = edcCrse2;
	}
	public char getEdcCrse3() {
		return edcCrse3;
	}
	public void setEdcCrse3(char edcCrse3) {
		this.edcCrse3 = edcCrse3;
	}
	public char getEdcCrse4() {
		return edcCrse4;
	}
	public void setEdcCrse4(char edcCrse4) {
		this.edcCrse4 = edcCrse4;
	}
	public char getEdcCrse5() {
		return edcCrse5;
	}
	public void setEdcCrse5(char edcCrse5) {
		this.edcCrse5 = edcCrse5;
	}
	public char getGnrlz() {
		return gnrlz;
	}
	public void setGnrlz(char gnrlz) {
		this.gnrlz = gnrlz;
	}
	public char getKnwldgTchnlgyPickup1() {
		return knwldgTchnlgyPickup1;
	}
	public void setKnwldgTchnlgyPickup1(char knwldgTchnlgyPickup1) {
		this.knwldgTchnlgyPickup1 = knwldgTchnlgyPickup1;
	}
	public char getKnwldgTchnlgyPickup2() {
		return knwldgTchnlgyPickup2;
	}
	public void setKnwldgTchnlgyPickup2(char knwldgTchnlgyPickup2) {
		this.knwldgTchnlgyPickup2 = knwldgTchnlgyPickup2;
	}
	public char getKnwldgTchnlgyPickup3() {
		return knwldgTchnlgyPickup3;
	}
	public void setKnwldgTchnlgyPickup3(char knwldgTchnlgyPickup3) {
		this.knwldgTchnlgyPickup3 = knwldgTchnlgyPickup3;
	}
	public char getKnwldgTchnlgyPickup4() {
		return knwldgTchnlgyPickup4;
	}
	public void setKnwldgTchnlgyPickup4(char knwldgTchnlgyPickup4) {
		this.knwldgTchnlgyPickup4 = knwldgTchnlgyPickup4;
	}
	public char getStoprtApplc1() {
		return stoprtApplc1;
	}
	public void setStoprtApplc1(char stoprtApplc1) {
		this.stoprtApplc1 = stoprtApplc1;
	}
	public char getStoprtApplc2() {
		return stoprtApplc2;
	}
	public void setStoprtApplc2(char stoprtApplc2) {
		this.stoprtApplc2 = stoprtApplc2;
	}
	public char getStoprtApplc3() {
		return stoprtApplc3;
	}
	public void setStoprtApplc3(char stoprtApplc3) {
		this.stoprtApplc3 = stoprtApplc3;
	}
	public char getStoprtApplc4() {
		return stoprtApplc4;
	}
	public void setStoprtApplc4(char stoprtApplc4) {
		this.stoprtApplc4 = stoprtApplc4;
	}
	public Date getReprtConfmDe() {
		return reprtConfmDe;
	}
	public void setReprtConfmDe(Date reprtConfmDe) {
		this.reprtConfmDe = reprtConfmDe;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getTpCd() {
		return tpCd;
	}
	public void setTpCd(String tpCd) {
		this.tpCd = tpCd;
	}
	public String getTpTme() {
		return tpTme;
	}
	public void setTpTme(String tpTme) {
		this.tpTme = tpTme;
	}
	public Date getTrStartDate() {
		return trStartDate;
	}
	public void setTrStartDate(Date trStartDate) {
		this.trStartDate = trStartDate;
	}
	public Date getTrEndDate() {
		return trEndDate;
	}
	public void setTrEndDate(Date trEndDate) {
		this.trEndDate = trEndDate;
	}
	
	@Override
	public String toString() {
		return "AnalysisVO [rsltAnalsIdx=" + rsltAnalsIdx + ", devlopIdx=" + devlopIdx + ", bizSe=" + bizSe
				+ ", trCorpNm=" + trCorpNm + ", tpNm=" + tpNm + ", ncsSclasCd=" + ncsSclasCd + ", trPeriod=" + trPeriod
				+ ", trtm=" + trtm + ", trMth=" + trMth + ", trPurps=" + trPurps + ", trTme=" + trTme
				+ ", trResultReprtSe=" + trResultReprtSe + ", edcCrse1=" + edcCrse1 + ", edcCrse2=" + edcCrse2
				+ ", edcCrse3=" + edcCrse3 + ", edcCrse4=" + edcCrse4 + ", edcCrse5=" + edcCrse5 + ", gnrlz=" + gnrlz
				+ ", knwldgTchnlgyPickup1=" + knwldgTchnlgyPickup1 + ", knwldgTchnlgyPickup2=" + knwldgTchnlgyPickup2
				+ ", knwldgTchnlgyPickup3=" + knwldgTchnlgyPickup3 + ", knwldgTchnlgyPickup4=" + knwldgTchnlgyPickup4
				+ ", stoprtApplc1=" + stoprtApplc1 + ", stoprtApplc2=" + stoprtApplc2 + ", stoprtApplc3=" + stoprtApplc3
				+ ", stoprtApplc4=" + stoprtApplc4 + ", reprtConfmDe=" + reprtConfmDe + ", status=" + status
				+ ", isDelete=" + isDelete + ", regiIdx=" + regiIdx + ", regiId=" + regiId + ", regiName=" + regiName
				+ ", regiDate=" + regiDate + ", regiIp=" + regiIp + ", lastModiId=" + lastModiId + ", lastModiIdx="
				+ lastModiIdx + ", lastModiName=" + lastModiName + ", lastModiDate=" + lastModiDate + ", lastModiIp="
				+ lastModiIp + ", tpCd=" + tpCd + ", tpTme=" + tpTme + ", trStartDate=" + trStartDate + ", trEndDate="
				+ trEndDate + "]";
	}
}
