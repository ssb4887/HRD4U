package rbs.modules.basket.dto;

import java.util.List;

public class Criteria {

	private int pageNum;
	private int amount;
	
	//검색에 필요한 키워드 선언
	private String isBplNm; //기업명
	private String isBplNo; //사업장관리번호
	private String isBizrNo; 
	private String isPriSupCd;
	private String isInsttIdx; //소속기관
	private String isIndustCd; //업종코드
	private String isTotEmpCntMin; //고용상시인원 범위 시작값
	private String isTotEmpCntMax; //고용상시인원 범위 끝값
	private String isAddr; // 주소?
	private String isSrRecord;
	private String isBranch;
	private String isHashTag;
	private Integer isLclas;
	private Integer isSclas;
	private String isTrRecord1;
	private String isTrRecord2;
	private String isTrRecord3;
	
	private String isCtprvn;
	private String isSigngu;
	
	private String isDoctorIdx;
	
	
	//출력칼럼
	private List<String> showColumn;
	
	
	//User 지부지사코드
	private String insttIdx;
	
	//User Id(민간센터)
	private String memberId;
	
	
	
	//훈련 이력 조회시 bplNo
	
	private String bplNo;
	
	

	public String getIsDoctorIdx() {
		return isDoctorIdx;
	}


	

	public String getIsBizrNo() {
		return isBizrNo;
	}




	public void setIsBizrNo(String isBizrNo) {
		this.isBizrNo = isBizrNo;
	}




	public String getIsPriSupCd() {
		return isPriSupCd;
	}




	public void setIsPriSupCd(String isPriSupCd) {
		this.isPriSupCd = isPriSupCd;
	}




	public void setIsDoctorIdx(String isDoctorIdx) {
		this.isDoctorIdx = isDoctorIdx;
	}



	public String getBplNo() {
		return bplNo;
	}



	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}



	public String getMemberId() {
		return memberId;
	}



	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	


	public int getPageNum() {
		return pageNum;
	}



	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}



	public int getAmount() {
		return amount;
	}



	public void setAmount(int amount) {
		this.amount = amount;
	}



	public String getIsBplNm() {
		return isBplNm;
	}



	public void setIsBplNm(String isBplNm) {
		this.isBplNm = isBplNm;
	}



	public String getIsBplNo() {
		return isBplNo;
	}



	public void setIsBplNo(String isBplNo) {
		this.isBplNo = isBplNo;
	}



	public String getIsInsttIdx() {
		return isInsttIdx;
	}



	public void setIsInsttIdx(String isInsttIdx) {
		this.isInsttIdx = isInsttIdx;
	}



	public String getIsIndustCd() {
		return isIndustCd;
	}



	public void setIsIndustCd(String isIndustCd) {
		this.isIndustCd = isIndustCd;
	}



	public String getIsTotEmpCntMin() {
		return isTotEmpCntMin;
	}



	public void setIsTotEmpCntMin(String isTotEmpCntMin) {
		this.isTotEmpCntMin = isTotEmpCntMin;
	}



	public String getIsTotEmpCntMax() {
		return isTotEmpCntMax;
	}



	public void setIsTotEmpCntMax(String isTotEmpCntMax) {
		this.isTotEmpCntMax = isTotEmpCntMax;
	}



	public String getIsAddr() {
		return isAddr;
	}



	public void setIsAddr(String isAddr) {
		this.isAddr = isAddr;
	}



	public String getIsSrRecord() {
		return isSrRecord;
	}



	public void setIsSrRecord(String isSrRecord) {
		this.isSrRecord = isSrRecord;
	}



	public String getIsBranch() {
		return isBranch;
	}

	


	public String getInsttIdx() {
		return insttIdx;
	}




	public void setInsttIdx(String insttIdx) {
		this.insttIdx = insttIdx;
	}




	public void setIsBranch(String isBranch) {
		this.isBranch = isBranch;
	}



	public String getIsHashTag() {
		return isHashTag;
	}



	public void setIsHashTag(String isHashTag) {
		this.isHashTag = isHashTag;
	}



	public Integer getIsLclas() {
		return isLclas;
	}



	public void setIsLclas(Integer isLclas) {
		this.isLclas = isLclas;
	}



	public Integer getIsSclas() {
		return isSclas;
	}



	public void setIsSclas(Integer isSclas) {
		this.isSclas = isSclas;
	}



	public String getIsTrRecord1() {
		return isTrRecord1;
	}



	public void setIsTrRecord1(String isTrRecord1) {
		this.isTrRecord1 = isTrRecord1;
	}



	public String getIsTrRecord2() {
		return isTrRecord2;
	}



	public void setIsTrRecord2(String isTrRecord2) {
		this.isTrRecord2 = isTrRecord2;
	}



	public String getIsTrRecord3() {
		return isTrRecord3;
	}



	public void setIsTrRecord3(String isTrRecord3) {
		this.isTrRecord3 = isTrRecord3;
	}



	public String getIsCtprvn() {
		return isCtprvn;
	}



	public void setIsCtprvn(String isCtprvn) {
		this.isCtprvn = isCtprvn;
	}



	public String getIsSigngu() {
		return isSigngu;
	}



	public void setIsSigngu(String isSigngu) {
		this.isSigngu = isSigngu;
	}



	public List<String> getShowColumn() {
		return showColumn;
	}



	public void setShowColumn(List<String> showColumn) {
		this.showColumn = showColumn;
	}



	public Criteria(int pageNum, int amount) {
		super();
		this.pageNum = pageNum;
		this.amount = amount;
	}



	public Criteria() {
		this(1, 10);
	}




	@Override
	public String toString() {
		return "Criteria [pageNum=" + pageNum + ", amount=" + amount
				+ ", isBplNm=" + isBplNm + ", isBplNo=" + isBplNo
				+ ", isBizrNo=" + isBizrNo + ", isPriSupCd=" + isPriSupCd
				+ ", isInsttIdx=" + isInsttIdx + ", isIndustCd=" + isIndustCd
				+ ", isTotEmpCntMin=" + isTotEmpCntMin + ", isTotEmpCntMax="
				+ isTotEmpCntMax + ", isAddr=" + isAddr + ", isSrRecord="
				+ isSrRecord + ", isBranch=" + isBranch + ", isHashTag="
				+ isHashTag + ", isLclas=" + isLclas + ", isSclas=" + isSclas
				+ ", isTrRecord1=" + isTrRecord1 + ", isTrRecord2="
				+ isTrRecord2 + ", isTrRecord3=" + isTrRecord3 + ", isCtprvn="
				+ isCtprvn + ", isSigngu=" + isSigngu + ", isDoctorIdx="
				+ isDoctorIdx + ", showColumn=" + showColumn + ", insttIdx="
				+ insttIdx + ", memberId=" + memberId + ", bplNo=" + bplNo
				+ "]";
	}




	
	
	







	
	
	
}
