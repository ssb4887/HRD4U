package rbs.modules.ntwrk.dto;

public class CmptinstDto {
	
	private int cmptinstIdx;
	private int insttIdx;
	private String cmptinstName;
	private String cmptinstReperNm;
	private String cmptinstType;
	private String cmptinstPicName;
	private String cmptinstPicTelno;
	private String cmptinstPicEmail;
	private String zip;
	private String addr;
	private String addrDtl;
	private String remarks;

	public CmptinstDto() {
		super();
	}
	
	public int getCmptinstIdx() {
		return cmptinstIdx;
	}
	public void setCmptinstIdx(int cmptinstIdx) {
		this.cmptinstIdx = cmptinstIdx;
	}
	public int getInsttIdx() {
		return insttIdx;
	}
	public void setInsttIdx(int insttIdx) {
		this.insttIdx = insttIdx;
	}
	public String getCmptinstName() {
		return cmptinstName;
	}
	public void setCmptinstName(String cmptinstName) {
		this.cmptinstName = cmptinstName;
	}
	public String getCmptinstReperNm() {
		return cmptinstReperNm;
	}
	public void setCmptinstReperNm(String cmptinstReperNm) {
		this.cmptinstReperNm = cmptinstReperNm;
	}
	public String getCmptinstType() {
		return cmptinstType;
	}
	public void setCmptinstType(String cmptinstType) {
		this.cmptinstType = cmptinstType;
	}
	public String getCmptinstPicName() {
		return cmptinstPicName;
	}
	public void setCmptinstPicName(String cmptinstPicName) {
		this.cmptinstPicName = cmptinstPicName;
	}
	public String getCmptinstPicTelno() {
		return cmptinstPicTelno;
	}
	public void setCmptinstPicTelno(String cmptinstPicTelno) {
		this.cmptinstPicTelno = cmptinstPicTelno;
	}
	public String getCmptinstPicEmail() {
		return cmptinstPicEmail;
	}
	public void setCmptinstPicEmail(String cmptinstPicEmail) {
		this.cmptinstPicEmail = cmptinstPicEmail;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAddrDtl() {
		return addrDtl;
	}
	public void setAddrDtl(String addrDtl) {
		this.addrDtl = addrDtl;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Override
	public String toString() {
		return "CmptinstDto [cmptinstIdx=" + cmptinstIdx + ", insttIdx=" + insttIdx + ", cmptinstName=" + cmptinstName
				+ ", cmptinstReperNm=" + cmptinstReperNm + ", cmptinstType=" + cmptinstType + ", cmptinstPicName="
				+ cmptinstPicName + ", cmptinstPicTelno=" + cmptinstPicTelno + ", cmptinstPicEmail=" + cmptinstPicEmail
				+ ", zip=" + zip + ", addr=" + addr + ", addrDtl=" + addrDtl + ", remarks=" + remarks + "]";
	}
	
}
