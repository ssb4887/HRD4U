package rbs.modules.consulting.dto;

public class CnslTeamMemberDTO {
	private Integer teamIdx;
	private Integer teamOrderIdx;
	private String memberIdx;
	private String loginId;
	private String rspnberYn;
	private String innerExtrlCd;
	private String mberName;
	private String mberPsitn;
	private String mberOfcps;
	private String mberTelno;
	public Integer getTeamIdx() {
		return teamIdx;
	}
	public void setTeamIdx(Integer teamIdx) {
		this.teamIdx = teamIdx;
	}
	public Integer getTeamOrderIdx() {
		return teamOrderIdx;
	}
	public void setTeamOrderIdx(Integer teamOrderIdx) {
		this.teamOrderIdx = teamOrderIdx;
	}
	public String getMemberIdx() {
		return memberIdx;
	}
	public void setMemberIdx(String memberIdx) {
		this.memberIdx = memberIdx;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getRspnberYn() {
		return rspnberYn;
	}
	public void setRspnberYn(String rspnberYn) {
		this.rspnberYn = rspnberYn;
	}
	public String getInnerExtrlCd() {
		return innerExtrlCd;
	}
	public void setInnerExtrlCd(String innerExtrlCd) {
		this.innerExtrlCd = innerExtrlCd;
	}
	public String getMberName() {
		return mberName;
	}
	public void setMberName(String mberName) {
		this.mberName = mberName;
	}
	public String getMberPsitn() {
		return mberPsitn;
	}
	public void setMberPsitn(String mberPsitn) {
		this.mberPsitn = mberPsitn;
	}
	public String getMberOfcps() {
		return mberOfcps;
	}
	public void setMberOfcps(String mberOfcps) {
		this.mberOfcps = mberOfcps;
	}
	public String getMberTelno() {
		return mberTelno;
	}
	public void setMberTelno(String mberTelno) {
		this.mberTelno = mberTelno;
	}
	public CnslTeamMemberDTO(Integer teamIdx, Integer teamOrderIdx,
			String memberIdx, String loginId, String rspnberYn,
			String innerExtrlCd, String mberName, String mberPsitn,
			String mberOfcps, String mberTelno) {
		super();
		this.teamIdx = teamIdx;
		this.teamOrderIdx = teamOrderIdx;
		this.memberIdx = memberIdx;
		this.loginId = loginId;
		this.rspnberYn = rspnberYn;
		this.innerExtrlCd = innerExtrlCd;
		this.mberName = mberName;
		this.mberPsitn = mberPsitn;
		this.mberOfcps = mberOfcps;
		this.mberTelno = mberTelno;
	}
	public CnslTeamMemberDTO() {
		super();
	}
	@Override
	public String toString() {
		return "CnslTeamMemberDTO [teamIdx=" + teamIdx + ", teamOrderIdx="
				+ teamOrderIdx + ", memberIdx=" + memberIdx + ", loginId="
				+ loginId + ", rspnberYn=" + rspnberYn + ", innerExtrlCd="
				+ innerExtrlCd + ", mberName=" + mberName + ", mberPsitn="
				+ mberPsitn + ", mberOfcps=" + mberOfcps + ", mberTelno="
				+ mberTelno + "]";
	}
	
	
	
	

}
