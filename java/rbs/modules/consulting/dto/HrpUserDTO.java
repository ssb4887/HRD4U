package rbs.modules.consulting.dto;

public class HrpUserDTO {
	
	
	//memberIdx
	private String hrpId;
	//로그인아이디
	private String memberId;
	//이름
	private String hrpNm;
	//소속
	private String wrkAreaNm;
	
	//직위
	private String wrkAreaOfcps;
	
	//전화번호
	private String telNo;

	public String getHrpId() {
		return hrpId;
	}

	public void setHrpId(String hrpId) {
		this.hrpId = hrpId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getHrpNm() {
		return hrpNm;
	}

	public void setHrpNm(String hrpNm) {
		this.hrpNm = hrpNm;
	}

	public String getWrkAreaNm() {
		return wrkAreaNm;
	}

	public void setWrkAreaNm(String wrkAreaNm) {
		this.wrkAreaNm = wrkAreaNm;
	}

	public String getWrkAreaOfcps() {
		return wrkAreaOfcps;
	}

	public void setWrkAreaOfcps(String wrkAreaOfcps) {
		this.wrkAreaOfcps = wrkAreaOfcps;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public HrpUserDTO(String hrpId, String memberId, String hrpNm,
			String wrkAreaNm, String wrkAreaOfcps, String telNo) {
		super();
		this.hrpId = hrpId;
		this.memberId = memberId;
		this.hrpNm = hrpNm;
		this.wrkAreaNm = wrkAreaNm;
		this.wrkAreaOfcps = wrkAreaOfcps;
		this.telNo = telNo;
	}

	public HrpUserDTO() {
		super();
	}

	@Override
	public String toString() {
		return "HrpUserDTO [hrpId=" + hrpId + ", memberId=" + memberId
				+ ", hrpNm=" + hrpNm + ", wrkAreaNm=" + wrkAreaNm
				+ ", wrkAreaOfcps=" + wrkAreaOfcps + ", telNo=" + telNo + "]";
	}

	

	

}

