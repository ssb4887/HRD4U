package rbs.modules.basket.dto;

import rbs.modules.basket.excel.ExcelColumn;

public class Basket {

	@ExcelColumn(HeaderName="순서", DefaultColumn="Y") private String seq;
	@ExcelColumn(HeaderName="사업장관리번호", DefaultColumn="Y") private String bplNo;
	@ExcelColumn(HeaderName="기업명", DefaultColumn="Y") private String bplNm;
	@ExcelColumn(HeaderName="고용업종명", DefaultColumn="Y") private String bplIndustNm;
	@ExcelColumn(HeaderName="고용상시인원수", DefaultColumn="Y") private int totEmpCnt;
	@ExcelColumn(HeaderName="우선지원구분", DefaultColumn="Y") private String priSupCd;
	@ExcelColumn(HeaderName="우선지원변경코드") private String priSupChgCd;

	@ExcelColumn(HeaderName="대분류") private String lclasNm; // 대분류명
	@ExcelColumn(HeaderName="소분류") private String sclasNm; // 소분류명
	
	
	@ExcelColumn(HeaderName="자영업구분") private String selfEmpCd;
	@ExcelColumn(HeaderName="사업장원부번호") private String bplOrgbkNo;
	@ExcelColumn(HeaderName="법인등록번호") private String jurirno;
	@ExcelColumn(HeaderName="사업자등록번호") private String bizrNo;
	@ExcelColumn(HeaderName="본지사 구분") private String headBplCd;
	@ExcelColumn(HeaderName="본사 사업장 관리번호") private String hedofcBplNo;
	
	@ExcelColumn(HeaderName="기업 우편번호") private String bplZip;
	@ExcelColumn(HeaderName="도로명주소") private String bplAddr;
	@ExcelColumn(HeaderName="기업 상세주소") private String addrDtl;

	@ExcelColumn(HeaderName="업종코드") private String indutyCd;
	

	@ExcelColumn(HeaderName="고용성립일자") private String emplymFormatnDe;
	@ExcelColumn(HeaderName="고용보험 소멸일자") private String empinsExtshDe;
	@ExcelColumn(HeaderName="고용사업장상태코드") private String bplStatusCd;
	@ExcelColumn(HeaderName="총 상시근로자수") private int totWorkCnt;
	@ExcelColumn(HeaderName="총사업장수") private int totBplCnt;
	

	@ExcelColumn(HeaderName="직업훈련 여부") private String occpTrYn;
	@ExcelColumn(HeaderName="대규모기업구분") private String lrsclCorpSe;
	@ExcelColumn(HeaderName="이메일") private String email;
	@ExcelColumn(HeaderName="사업장이메일") private String bplEmail;
	@ExcelColumn(HeaderName="사업장지역번호") private String bplAreaNo;
	@ExcelColumn(HeaderName="사업장팩스앞자리") private String bplFaxNo1;
	@ExcelColumn(HeaderName="사업장팩스뒷자리") private String bplFaxNo2;
	@ExcelColumn(HeaderName="사업장전화번호앞자리") private String bplTelno1;
	@ExcelColumn(HeaderName="사업장전화번호뒷자리") private String bplTelno2;
	@ExcelColumn(HeaderName="사업장팩스번호") private String bplFaxNo;
	@ExcelColumn(HeaderName="사업장전화번호") private String bplTelno;
	
	
	@ExcelColumn(HeaderName="사업장URL") private String bplUrl;

	@ExcelColumn(HeaderName="고용특별법적용구분") private String spemplymApplcSe;
	@ExcelColumn(HeaderName="우선지원시작일자") private String priSupStartDate;
	@ExcelColumn(HeaderName="예술인사업장 여부") private String artbplYn;
	@ExcelColumn(HeaderName="작성일자") private String writngDe;
	@ExcelColumn(HeaderName="소속기관", DefaultColumn="Y") private String insttIdx;


	

	// 기업 추가 정보 데이터
	@ExcelColumn(HeaderName="우량기업여부") private String excentYn;
	@ExcelColumn(HeaderName="BEST HRD 선정 유무") private String besthrdYn;
	@ExcelColumn(HeaderName="산업단지여부") private String idscpxYn;
	@ExcelColumn(HeaderName="산재다발") private String indacmtYn;
	@ExcelColumn(HeaderName="임금체불") private String wgdlyYn;

	// HRD-NET 데이터
	@ExcelColumn(HeaderName="사업주 참여 여부(횟수)") private int bprCount; // 사업주 참여 여부(횟수)
	@ExcelColumn(HeaderName="S-OJT참여 여부(횟수)") private int sojtCount; // S-OJT참여 여부(횟수)
	@ExcelColumn(HeaderName="컨소시엄 참여 여부(횟수)") private int conCount; // 컨소시엄 참여 여부(횟수)
	@ExcelColumn(HeaderName="지산맞 참여 여부(횟수)") private int regCount; // 지산맞 참여 여부(횟수)
	@ExcelColumn(HeaderName="일학습 참여 여부(횟수)") private int pdmsCount; // 일학습 참여 여부(횟수)

	@ExcelColumn(HeaderName="학습조직화 참여 여부") private int egCount; // 학습조직화 참여 여부
	@ExcelColumn(HeaderName="우수기관 인증 여부") private int certCount; // 우수기관 인증 여부

	// 워크넷 데이터
	@ExcelColumn(HeaderName="강소기업 여부") private int sgCount; // 강소기업 여부
	@ExcelColumn(HeaderName="청년친화 강소기업 여부") private int yfCount; // 청년친화 강소기업 여부

	// 채용 데이터(워크넷)
	@ExcelColumn(HeaderName="채용제목") private String title;
	@ExcelColumn(HeaderName="임금형태") private String salaryStle;
	@ExcelColumn(HeaderName="급여") private String salaryScope;
	@ExcelColumn(HeaderName="지역") private String region;
	@ExcelColumn(HeaderName="근무형태") private String workStle;
	
	// 재무데이터
	@ExcelColumn(HeaderName="기업신용등급") private String credtGrad;

	@ExcelColumn(HeaderName="자산총계(2022년)") private String totAssets2022;
	@ExcelColumn(HeaderName="자산총계(2021년)") private String totAssets2021;
	@ExcelColumn(HeaderName="자산총계(2020년)") private String totAssets2020;
	@ExcelColumn(HeaderName="자산총계(20219년)") private String totAssets2019;

	@ExcelColumn(HeaderName="자본총계(2022년)") private String totCaplAmt2022;
	@ExcelColumn(HeaderName="자본총계(2021년)") private String totCaplAmt2021;
	@ExcelColumn(HeaderName="자본총계(2020년)") private String totCaplAmt2020;
	@ExcelColumn(HeaderName="자본총계(2019년)") private String totCaplAmt2019;

	@ExcelColumn(HeaderName="매출액(2022년)") private String selngAmt2022;
	@ExcelColumn(HeaderName="매출액(2021년)") private String selngAmt2021;
	@ExcelColumn(HeaderName="매출액(2020년)") private String selngAmt2020;
	@ExcelColumn(HeaderName="매출액(2019년)") private String selngAmt2019;

	@ExcelColumn(HeaderName="영업이익(2022년)") private String bsnProfit2022;
	@ExcelColumn(HeaderName="영업이익(2021년)") private String bsnProfit2021;
	@ExcelColumn(HeaderName="영업이익(2020년)") private String bsnProfit2020;
	@ExcelColumn(HeaderName="영업이익(2019년)") private String bsnProfit2019;

	@ExcelColumn(HeaderName="당기순이익(2022년)") private String thstrmNtpf2022;
	@ExcelColumn(HeaderName="당기순이익(2022년)") private String thstrmNtpf2021;
	@ExcelColumn(HeaderName="당기순이익(2022년)") private String thstrmNtpf2020;
	@ExcelColumn(HeaderName="당기순이익(2022년)") private String thstrmNtpf2019;
	
	
	
	
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getBplNo() {
		return bplNo;
	}
	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}
	public String getBizrNo() {
		return bizrNo;
	}
	public void setBizrNo(String bizrNo) {
		this.bizrNo = bizrNo;
	}
	public String getPriSupCd() {
		return priSupCd;
	}
	public void setPriSupCd(String priSupCd) {
		this.priSupCd = priSupCd;
	}
	public String getPriSupChgCd() {
		return priSupChgCd;
	}
	public void setPriSupChgCd(String priSupChgCd) {
		this.priSupChgCd = priSupChgCd;
	}
	public String getLclasNm() {
		return lclasNm;
	}
	public void setLclasNm(String lclasNm) {
		this.lclasNm = lclasNm;
	}
	public String getSclasNm() {
		return sclasNm;
	}
	public void setSclasNm(String sclasNm) {
		this.sclasNm = sclasNm;
	}
	public String getSelfEmpCd() {
		return selfEmpCd;
	}
	public void setSelfEmpCd(String selfEmpCd) {
		this.selfEmpCd = selfEmpCd;
	}
	public String getBplOrgbkNo() {
		return bplOrgbkNo;
	}
	public void setBplOrgbkNo(String bplOrgbkNo) {
		this.bplOrgbkNo = bplOrgbkNo;
	}
	public String getJurirno() {
		return jurirno;
	}
	public void setJurirno(String jurirno) {
		this.jurirno = jurirno;
	}
	public String getHeadBplCd() {
		return headBplCd;
	}
	public void setHeadBplCd(String headBplCd) {
		this.headBplCd = headBplCd;
	}
	public String getHedofcBplNo() {
		return hedofcBplNo;
	}
	public void setHedofcBplNo(String hedofcBplNo) {
		this.hedofcBplNo = hedofcBplNo;
	}
	public String getBplNm() {
		return bplNm;
	}
	public void setBplNm(String bplNm) {
		this.bplNm = bplNm;
	}
	public String getBplZip() {
		return bplZip;
	}
	public void setBplZip(String bplZip) {
		this.bplZip = bplZip;
	}
	public String getBplAddr() {
		return bplAddr;
	}
	public void setBplAddr(String bplAddr) {
		this.bplAddr = bplAddr;
	}
	public String getAddrDtl() {
		return addrDtl;
	}
	public void setAddrDtl(String addrDtl) {
		this.addrDtl = addrDtl;
	}
	public String getIndutyCd() {
		return indutyCd;
	}
	public void setIndutyCd(String indutyCd) {
		this.indutyCd = indutyCd;
	}
	public String getBplIndustNm() {
		return bplIndustNm;
	}
	public void setBplIndustNm(String bplIndustNm) {
		this.bplIndustNm = bplIndustNm;
	}
	public String getEmplymFormatnDe() {
		return emplymFormatnDe;
	}
	public void setEmplymFormatnDe(String emplymFormatnDe) {
		this.emplymFormatnDe = emplymFormatnDe;
	}
	public String getEmpinsExtshDe() {
		return empinsExtshDe;
	}
	public void setEmpinsExtshDe(String empinsExtshDe) {
		this.empinsExtshDe = empinsExtshDe;
	}
	public String getBplStatusCd() {
		return bplStatusCd;
	}
	public void setBplStatusCd(String bplStatusCd) {
		this.bplStatusCd = bplStatusCd;
	}
	public int getTotWorkCnt() {
		return totWorkCnt;
	}
	public void setTotWorkCnt(int totWorkCnt) {
		this.totWorkCnt = totWorkCnt;
	}
	public int getTotBplCnt() {
		return totBplCnt;
	}
	public void setTotBplCnt(int totBplCnt) {
		this.totBplCnt = totBplCnt;
	}
	public int getTotEmpCnt() {
		return totEmpCnt;
	}
	public void setTotEmpCnt(int totEmpCnt) {
		this.totEmpCnt = totEmpCnt;
	}
	public String getOccpTrYn() {
		return occpTrYn;
	}
	public void setOccpTrYn(String occpTrYn) {
		this.occpTrYn = occpTrYn;
	}
	public String getLrsclCorpSe() {
		return lrsclCorpSe;
	}
	public void setLrsclCorpSe(String lrsclCorpSe) {
		this.lrsclCorpSe = lrsclCorpSe;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBplEmail() {
		return bplEmail;
	}
	public void setBplEmail(String bplEmail) {
		this.bplEmail = bplEmail;
	}
	public String getBplAreaNo() {
		return bplAreaNo;
	}
	public void setBplAreaNo(String bplAreaNo) {
		this.bplAreaNo = bplAreaNo;
	}
	public String getBplFaxNo1() {
		return bplFaxNo1;
	}
	public void setBplFaxNo1(String bplFaxNo1) {
		this.bplFaxNo1 = bplFaxNo1;
	}
	public String getBplFaxNo2() {
		return bplFaxNo2;
	}
	public void setBplFaxNo2(String bplFaxNo2) {
		this.bplFaxNo2 = bplFaxNo2;
	}
	public String getBplTelno1() {
		return bplTelno1;
	}
	public void setBplTelno1(String bplTelno1) {
		this.bplTelno1 = bplTelno1;
	}
	public String getBplTelno2() {
		return bplTelno2;
	}
	public void setBplTelno2(String bplTelno2) {
		this.bplTelno2 = bplTelno2;
	}
	public String getBplFaxNo() {
		return bplFaxNo;
	}
	public void setBplFaxNo(String bplFaxNo) {
		this.bplFaxNo = bplFaxNo;
	}
	public String getBplTelno() {
		return bplTelno;
	}
	public void setBplTelno(String bplTelno) {
		this.bplTelno = bplTelno;
	}
	public String getBplUrl() {
		return bplUrl;
	}
	public void setBplUrl(String bplUrl) {
		this.bplUrl = bplUrl;
	}
	public String getSpemplymApplcSe() {
		return spemplymApplcSe;
	}
	public void setSpemplymApplcSe(String spemplymApplcSe) {
		this.spemplymApplcSe = spemplymApplcSe;
	}
	public String getPriSupStartDate() {
		return priSupStartDate;
	}
	public void setPriSupStartDate(String priSupStartDate) {
		this.priSupStartDate = priSupStartDate;
	}
	public String getArtbplYn() {
		return artbplYn;
	}
	public void setArtbplYn(String artbplYn) {
		this.artbplYn = artbplYn;
	}
	public String getWritngDe() {
		return writngDe;
	}
	public void setWritngDe(String writngDe) {
		this.writngDe = writngDe;
	}
	public String getInsttIdx() {
		return insttIdx;
	}
	public void setInsttIdx(String insttIdx) {
		this.insttIdx = insttIdx;
	}
	public String getExcentYn() {
		return excentYn;
	}
	public void setExcentYn(String excentYn) {
		this.excentYn = excentYn;
	}
	public String getBesthrdYn() {
		return besthrdYn;
	}
	public void setBesthrdYn(String besthrdYn) {
		this.besthrdYn = besthrdYn;
	}
	public String getIdscpxYn() {
		return idscpxYn;
	}
	public void setIdscpxYn(String idscpxYn) {
		this.idscpxYn = idscpxYn;
	}
	public String getIndacmtYn() {
		return indacmtYn;
	}
	public void setIndacmtYn(String indacmtYn) {
		this.indacmtYn = indacmtYn;
	}
	public String getWgdlyYn() {
		return wgdlyYn;
	}
	public void setWgdlyYn(String wgdlyYn) {
		this.wgdlyYn = wgdlyYn;
	}
	public int getBprCount() {
		return bprCount;
	}
	public void setBprCount(int bprCount) {
		this.bprCount = bprCount;
	}
	public int getSojtCount() {
		return sojtCount;
	}
	public void setSojtCount(int sojtCount) {
		this.sojtCount = sojtCount;
	}
	public int getConCount() {
		return conCount;
	}
	public void setConCount(int conCount) {
		this.conCount = conCount;
	}
	public int getRegCount() {
		return regCount;
	}
	public void setRegCount(int regCount) {
		this.regCount = regCount;
	}
	public int getPdmsCount() {
		return pdmsCount;
	}
	public void setPdmsCount(int pdmsCount) {
		this.pdmsCount = pdmsCount;
	}
	public int getEgCount() {
		return egCount;
	}
	public void setEgCount(int egCount) {
		this.egCount = egCount;
	}
	public int getCertCount() {
		return certCount;
	}
	public void setCertCount(int certCount) {
		this.certCount = certCount;
	}
	public int getSgCount() {
		return sgCount;
	}
	public void setSgCount(int sgCount) {
		this.sgCount = sgCount;
	}
	public int getYfCount() {
		return yfCount;
	}
	public void setYfCount(int yfCount) {
		this.yfCount = yfCount;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSalaryStle() {
		return salaryStle;
	}
	public void setSalaryStle(String salaryStle) {
		this.salaryStle = salaryStle;
	}
	public String getSalaryScope() {
		return salaryScope;
	}
	public void setSalaryScope(String salaryScope) {
		this.salaryScope = salaryScope;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getWorkStle() {
		return workStle;
	}
	public void setWorkStle(String workStle) {
		this.workStle = workStle;
	}
	public String getCredtGrad() {
		return credtGrad;
	}
	public void setCredtGrad(String credtGrad) {
		this.credtGrad = credtGrad;
	}
	public String getTotAssets2022() {
		return totAssets2022;
	}
	public void setTotAssets2022(String totAssets2022) {
		this.totAssets2022 = totAssets2022;
	}
	public String getTotAssets2021() {
		return totAssets2021;
	}
	public void setTotAssets2021(String totAssets2021) {
		this.totAssets2021 = totAssets2021;
	}
	public String getTotAssets2020() {
		return totAssets2020;
	}
	public void setTotAssets2020(String totAssets2020) {
		this.totAssets2020 = totAssets2020;
	}
	public String getTotAssets2019() {
		return totAssets2019;
	}
	public void setTotAssets2019(String totAssets2019) {
		this.totAssets2019 = totAssets2019;
	}
	public String getTotCaplAmt2022() {
		return totCaplAmt2022;
	}
	public void setTotCaplAmt2022(String totCaplAmt2022) {
		this.totCaplAmt2022 = totCaplAmt2022;
	}
	public String getTotCaplAmt2021() {
		return totCaplAmt2021;
	}
	public void setTotCaplAmt2021(String totCaplAmt2021) {
		this.totCaplAmt2021 = totCaplAmt2021;
	}
	public String getTotCaplAmt2020() {
		return totCaplAmt2020;
	}
	public void setTotCaplAmt2020(String totCaplAmt2020) {
		this.totCaplAmt2020 = totCaplAmt2020;
	}
	public String getTotCaplAmt2019() {
		return totCaplAmt2019;
	}
	public void setTotCaplAmt2019(String totCaplAmt2019) {
		this.totCaplAmt2019 = totCaplAmt2019;
	}
	public String getSelngAmt2022() {
		return selngAmt2022;
	}
	public void setSelngAmt2022(String selngAmt2022) {
		this.selngAmt2022 = selngAmt2022;
	}
	public String getSelngAmt2021() {
		return selngAmt2021;
	}
	public void setSelngAmt2021(String selngAmt2021) {
		this.selngAmt2021 = selngAmt2021;
	}
	public String getSelngAmt2020() {
		return selngAmt2020;
	}
	public void setSelngAmt2020(String selngAmt2020) {
		this.selngAmt2020 = selngAmt2020;
	}
	public String getSelngAmt2019() {
		return selngAmt2019;
	}
	public void setSelngAmt2019(String selngAmt2019) {
		this.selngAmt2019 = selngAmt2019;
	}
	public String getBsnProfit2022() {
		return bsnProfit2022;
	}
	public void setBsnProfit2022(String bsnProfit2022) {
		this.bsnProfit2022 = bsnProfit2022;
	}
	public String getBsnProfit2021() {
		return bsnProfit2021;
	}
	public void setBsnProfit2021(String bsnProfit2021) {
		this.bsnProfit2021 = bsnProfit2021;
	}
	public String getBsnProfit2020() {
		return bsnProfit2020;
	}
	public void setBsnProfit2020(String bsnProfit2020) {
		this.bsnProfit2020 = bsnProfit2020;
	}
	public String getBsnProfit2019() {
		return bsnProfit2019;
	}
	public void setBsnProfit2019(String bsnProfit2019) {
		this.bsnProfit2019 = bsnProfit2019;
	}
	public String getThstrmNtpf2022() {
		return thstrmNtpf2022;
	}
	public void setThstrmNtpf2022(String thstrmNtpf2022) {
		this.thstrmNtpf2022 = thstrmNtpf2022;
	}
	public String getThstrmNtpf2021() {
		return thstrmNtpf2021;
	}
	public void setThstrmNtpf2021(String thstrmNtpf2021) {
		this.thstrmNtpf2021 = thstrmNtpf2021;
	}
	public String getThstrmNtpf2020() {
		return thstrmNtpf2020;
	}
	public void setThstrmNtpf2020(String thstrmNtpf2020) {
		this.thstrmNtpf2020 = thstrmNtpf2020;
	}
	public String getThstrmNtpf2019() {
		return thstrmNtpf2019;
	}
	public void setThstrmNtpf2019(String thstrmNtpf2019) {
		this.thstrmNtpf2019 = thstrmNtpf2019;
	}
	public Basket(String seq, String bplNo, String bplNm, String bplIndustNm,
			int totEmpCnt, String priSupCd, String priSupChgCd, String lclasNm,
			String sclasNm, String selfEmpCd, String bplOrgbkNo, String jurirno,
			String bizrNo, String headBplCd, String hedofcBplNo, String bplZip,
			String bplAddr, String addrDtl, String indutyCd,
			String emplymFormatnDe, String empinsExtshDe, String bplStatusCd,
			int totWorkCnt, int totBplCnt, String occpTrYn, String lrsclCorpSe,
			String email, String bplEmail, String bplAreaNo, String bplFaxNo1,
			String bplFaxNo2, String bplTelno1, String bplTelno2,
			String bplFaxNo, String bplTelno, String bplUrl,
			String spemplymApplcSe, String priSupStartDate, String artbplYn,
			String writngDe, String insttIdx, String excentYn, String besthrdYn,
			String idscpxYn, String indacmtYn, String wgdlyYn, int bprCount,
			int sojtCount, int conCount, int regCount, int pdmsCount,
			int egCount, int certCount, int sgCount, int yfCount, String title,
			String salaryStle, String salaryScope, String region,
			String workStle, String credtGrad, String totAssets2022,
			String totAssets2021, String totAssets2020, String totAssets2019,
			String totCaplAmt2022, String totCaplAmt2021, String totCaplAmt2020,
			String totCaplAmt2019, String selngAmt2022, String selngAmt2021,
			String selngAmt2020, String selngAmt2019, String bsnProfit2022,
			String bsnProfit2021, String bsnProfit2020, String bsnProfit2019,
			String thstrmNtpf2022, String thstrmNtpf2021, String thstrmNtpf2020,
			String thstrmNtpf2019) {
		super();
		this.seq = seq;
		this.bplNo = bplNo;
		this.bplNm = bplNm;
		this.bplIndustNm = bplIndustNm;
		this.totEmpCnt = totEmpCnt;
		this.priSupCd = priSupCd;
		this.priSupChgCd = priSupChgCd;
		this.lclasNm = lclasNm;
		this.sclasNm = sclasNm;
		this.selfEmpCd = selfEmpCd;
		this.bplOrgbkNo = bplOrgbkNo;
		this.jurirno = jurirno;
		this.bizrNo = bizrNo;
		this.headBplCd = headBplCd;
		this.hedofcBplNo = hedofcBplNo;
		this.bplZip = bplZip;
		this.bplAddr = bplAddr;
		this.addrDtl = addrDtl;
		this.indutyCd = indutyCd;
		this.emplymFormatnDe = emplymFormatnDe;
		this.empinsExtshDe = empinsExtshDe;
		this.bplStatusCd = bplStatusCd;
		this.totWorkCnt = totWorkCnt;
		this.totBplCnt = totBplCnt;
		this.occpTrYn = occpTrYn;
		this.lrsclCorpSe = lrsclCorpSe;
		this.email = email;
		this.bplEmail = bplEmail;
		this.bplAreaNo = bplAreaNo;
		this.bplFaxNo1 = bplFaxNo1;
		this.bplFaxNo2 = bplFaxNo2;
		this.bplTelno1 = bplTelno1;
		this.bplTelno2 = bplTelno2;
		this.bplFaxNo = bplFaxNo;
		this.bplTelno = bplTelno;
		this.bplUrl = bplUrl;
		this.spemplymApplcSe = spemplymApplcSe;
		this.priSupStartDate = priSupStartDate;
		this.artbplYn = artbplYn;
		this.writngDe = writngDe;
		this.insttIdx = insttIdx;
		this.excentYn = excentYn;
		this.besthrdYn = besthrdYn;
		this.idscpxYn = idscpxYn;
		this.indacmtYn = indacmtYn;
		this.wgdlyYn = wgdlyYn;
		this.bprCount = bprCount;
		this.sojtCount = sojtCount;
		this.conCount = conCount;
		this.regCount = regCount;
		this.pdmsCount = pdmsCount;
		this.egCount = egCount;
		this.certCount = certCount;
		this.sgCount = sgCount;
		this.yfCount = yfCount;
		this.title = title;
		this.salaryStle = salaryStle;
		this.salaryScope = salaryScope;
		this.region = region;
		this.workStle = workStle;
		this.credtGrad = credtGrad;
		this.totAssets2022 = totAssets2022;
		this.totAssets2021 = totAssets2021;
		this.totAssets2020 = totAssets2020;
		this.totAssets2019 = totAssets2019;
		this.totCaplAmt2022 = totCaplAmt2022;
		this.totCaplAmt2021 = totCaplAmt2021;
		this.totCaplAmt2020 = totCaplAmt2020;
		this.totCaplAmt2019 = totCaplAmt2019;
		this.selngAmt2022 = selngAmt2022;
		this.selngAmt2021 = selngAmt2021;
		this.selngAmt2020 = selngAmt2020;
		this.selngAmt2019 = selngAmt2019;
		this.bsnProfit2022 = bsnProfit2022;
		this.bsnProfit2021 = bsnProfit2021;
		this.bsnProfit2020 = bsnProfit2020;
		this.bsnProfit2019 = bsnProfit2019;
		this.thstrmNtpf2022 = thstrmNtpf2022;
		this.thstrmNtpf2021 = thstrmNtpf2021;
		this.thstrmNtpf2020 = thstrmNtpf2020;
		this.thstrmNtpf2019 = thstrmNtpf2019;
	}
	public Basket() {
		super();
	}
	@Override
	public String toString() {
		return "Basket [seq=" + seq + ", bplNo=" + bplNo + ", bplNm=" + bplNm
				+ ", bplIndustNm=" + bplIndustNm + ", totEmpCnt=" + totEmpCnt
				+ ", priSupCd=" + priSupCd + ", priSupChgCd=" + priSupChgCd
				+ ", lclasNm=" + lclasNm + ", sclasNm=" + sclasNm
				+ ", selfEmpCd=" + selfEmpCd + ", bplOrgbkNo=" + bplOrgbkNo
				+ ", jurirno=" + jurirno + ", bizrNo=" + bizrNo + ", headBplCd="
				+ headBplCd + ", hedofcBplNo=" + hedofcBplNo + ", bplZip="
				+ bplZip + ", bplAddr=" + bplAddr + ", addrDtl=" + addrDtl
				+ ", indutyCd=" + indutyCd + ", emplymFormatnDe="
				+ emplymFormatnDe + ", empinsExtshDe=" + empinsExtshDe
				+ ", bplStatusCd=" + bplStatusCd + ", totWorkCnt=" + totWorkCnt
				+ ", totBplCnt=" + totBplCnt + ", occpTrYn=" + occpTrYn
				+ ", lrsclCorpSe=" + lrsclCorpSe + ", email=" + email
				+ ", bplEmail=" + bplEmail + ", bplAreaNo=" + bplAreaNo
				+ ", bplFaxNo1=" + bplFaxNo1 + ", bplFaxNo2=" + bplFaxNo2
				+ ", bplTelno1=" + bplTelno1 + ", bplTelno2=" + bplTelno2
				+ ", bplFaxNo=" + bplFaxNo + ", bplTelno=" + bplTelno
				+ ", bplUrl=" + bplUrl + ", spemplymApplcSe=" + spemplymApplcSe
				+ ", priSupStartDate=" + priSupStartDate + ", artbplYn="
				+ artbplYn + ", writngDe=" + writngDe + ", insttIdx=" + insttIdx
				+ ", excentYn=" + excentYn + ", besthrdYn=" + besthrdYn
				+ ", idscpxYn=" + idscpxYn + ", indacmtYn=" + indacmtYn
				+ ", wgdlyYn=" + wgdlyYn + ", bprCount=" + bprCount
				+ ", sojtCount=" + sojtCount + ", conCount=" + conCount
				+ ", regCount=" + regCount + ", pdmsCount=" + pdmsCount
				+ ", egCount=" + egCount + ", certCount=" + certCount
				+ ", sgCount=" + sgCount + ", yfCount=" + yfCount + ", title="
				+ title + ", salaryStle=" + salaryStle + ", salaryScope="
				+ salaryScope + ", region=" + region + ", workStle=" + workStle
				+ ", credtGrad=" + credtGrad + ", totAssets2022="
				+ totAssets2022 + ", totAssets2021=" + totAssets2021
				+ ", totAssets2020=" + totAssets2020 + ", totAssets2019="
				+ totAssets2019 + ", totCaplAmt2022=" + totCaplAmt2022
				+ ", totCaplAmt2021=" + totCaplAmt2021 + ", totCaplAmt2020="
				+ totCaplAmt2020 + ", totCaplAmt2019=" + totCaplAmt2019
				+ ", selngAmt2022=" + selngAmt2022 + ", selngAmt2021="
				+ selngAmt2021 + ", selngAmt2020=" + selngAmt2020
				+ ", selngAmt2019=" + selngAmt2019 + ", bsnProfit2022="
				+ bsnProfit2022 + ", bsnProfit2021=" + bsnProfit2021
				+ ", bsnProfit2020=" + bsnProfit2020 + ", bsnProfit2019="
				+ bsnProfit2019 + ", thstrmNtpf2022=" + thstrmNtpf2022
				+ ", thstrmNtpf2021=" + thstrmNtpf2021 + ", thstrmNtpf2020="
				+ thstrmNtpf2020 + ", thstrmNtpf2019=" + thstrmNtpf2019 + "]";
	}
	
	
	
	

}
