package rbs.egovframework;

import java.io.Serializable;
import java.util.List;

public class LoginVO implements Serializable{
	
	private static final long serialVersionUID = -8274004534207618049L;

	/** 고유아이디 */
	private String memberIdx;
	/** 아이디(암호화) */
	private String memberId;
	/** 아이디(원본) */
	private String memberIdOrg;
	/** 비밀번호 */
	private String memberPwd;
	/** 이름(암호화) */
	private String memberName;
	/** 이름(원본) */
	private String memberNameOrg;
	/** 이메일주소(암호화) */
	private String memberEmail;
	/** 이메일주소(원본) */
	private String memberEmailOrg;
	/** 고유기업아이디 */
	private String corpNum;
	/** 사용자유형 */
	private int usertypeIdx;
	/** 부서코드 */
	private int departIdx;
	/** 부서코드:하위단계까지 ','로 구분 */
	private String departIdxs;
	
	private List<Object> groupList;
	
	/** 지부지사 */
	private String regiCode;
	
	/** instt_idx(주치의 instt_idx)*/
	private String insttIdx;
	
	/** clsfCd 주치의 부장여부 확인*/
	private String clsfCd;
	
	/** clsfCd 주치의 부장여부 확인*/
	private int doctorIdx;
	
	/** siteId */
	private String siteId;

	/** 비밀번호 수정 안한 기간 */
	private double pwdModiIntv;
	
	/** 비밀번호 다음에 변경하기 한 기간 */
	private double pwdModiIntv2;
	
	/** 비밀번호 다음에 변경하기(1:다음에 변경하기) */
	private String pwdModiType;
	
	private int loginFail;
	
	/** 로그인 여부 */
	private boolean isLogin;
	
	/** SNS구분(facebook 등) */
	private String snsType;
	/** sns별 ID */
	private String snsId;

	
	/** 사업자등록번호 */
	private String bizNum;
	
	/** 사업장관리번호 */
	private String bplNo;

	public String getRegiCode() {
		return regiCode;
	}

	public void setRegiCode(String regiCode) {
		this.regiCode = regiCode;
	}

	public String getMemberIdx() {
		return memberIdx;
	}

	public void setMemberIdx(String memberIdx) {
		this.memberIdx = memberIdx;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberIdOrg() {
		return memberIdOrg;
	}

	public void setMemberIdOrg(String memberIdOrg) {
		this.memberIdOrg = memberIdOrg;
	}

	public String getMemberPwd() {
		return memberPwd;
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberNameOrg() {
		return memberNameOrg;
	}

	public void setMemberNameOrg(String memberNameOrg) {
		this.memberNameOrg = memberNameOrg;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberEmailOrg() {
		return memberEmailOrg;
	}

	public void setMemberEmailOrg(String memberEmailOrg) {
		this.memberEmailOrg = memberEmailOrg;
	}

	public String getCorpNum() {
		return corpNum;
	}
	public void setCorpNum(String corpNum) {
		this.corpNum = corpNum;
	}
	public int getUsertypeIdx() {
		return usertypeIdx;
	}

	public void setUsertypeIdx(int usertypeIdx) {
		this.usertypeIdx = usertypeIdx;
	}

	public int getDepartIdx() {
		return departIdx;
	}

	public void setDepartIdx(int departIdx) {
		this.departIdx = departIdx;
	}

	public String getDepartIdxs() {
		return departIdxs;
	}

	public void setDepartIdxs(String departIdxs) {
		this.departIdxs = departIdxs;
	}

	public List<Object> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Object> groupList) {
		this.groupList = groupList;
	}

	public double getPwdModiIntv() {
		return pwdModiIntv;
	}

	public void setPwdModiIntv(int pwdModiIntv) {
		this.pwdModiIntv = pwdModiIntv;
	}

	public double getPwdModiIntv2() {
		return pwdModiIntv2;
	}

	public void setPwdModiIntv2(int pwdModiIntv2) {
		this.pwdModiIntv2 = pwdModiIntv2;
	}
	
	public String getPwdModiType() {
		return pwdModiType;
	}

	public void setPwdModiType(String pwdModiType) {
		this.pwdModiType = pwdModiType;
	}
	
	public int getLoginFail() {
		return loginFail;
	}

	public void setLoginFail(int loginFail) {
		this.loginFail = loginFail;
	}

	public String getSnsType() {
		return snsType;
	}

	public void setSnsType(String snsType) {
		this.snsType = snsType;
	}

	public String getSnsId() {
		return snsId;
	}

	public void setSnsId(String snsId) {
		this.snsId = snsId;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	

	public String getBizNum() {
		return bizNum;
	}

	public void setBizNum(String bizNum) {
		this.bizNum = bizNum;
	}
	
	
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	

	public String getBplNo() {
		return bplNo;
	}

	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}
	
	public String getInsttIdx() {
		return insttIdx;
	}

	public void setInsttIdx(String insttIdx) {
		this.insttIdx = insttIdx;
	}
	
	public String getClsfCd() {
		return clsfCd;
	}

	public void setClsfCd(String clsfCd) {
		this.clsfCd = clsfCd;
	}
	
	public int getDoctorIdx() {
		return doctorIdx;
	}

	public void setDoctorIdx(int doctorIdx) {
		this.doctorIdx = doctorIdx;
	}

	@Override
	public String toString() {
		return "LoginVO [memberIdx=" + memberIdx + ", memberId=" + memberId + ", memberIdOrg=" + memberIdOrg
				+ ", memberPwd=" + memberPwd + ", memberName=" + memberName + ", memberNameOrg=" + memberNameOrg
				+ ", memberEmail=" + memberEmail + ", memberEmailOrg=" + memberEmailOrg + ", usertypeIdx=" + usertypeIdx
				+ ", departIdx=" + departIdx + ", departIdxs=" + departIdxs + ", groupList=" + groupList + ", regiCode="
				+ regiCode + ", pwdModiIntv=" + pwdModiIntv + ", pwdModiIntv2=" + pwdModiIntv2 + ", pwdModiType="
				+ pwdModiType + ", loginFail=" + loginFail + ", isLogin=" + isLogin + ", snsType=" + snsType
				+ ", snsId=" + snsId + ", bizNum=" + bizNum + ", bplNo=" + bplNo + ", siteId=" + siteId + ",insttIdx = " + insttIdx + ", clsfCd = " + clsfCd + ", doctorIdx = " + doctorIdx + "]"; 
	}

	
}
