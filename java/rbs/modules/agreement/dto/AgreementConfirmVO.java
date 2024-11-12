package rbs.modules.agreement.dto;

public class AgreementConfirmVO {
	int agremIdx;
	int status;
	String doctorIdx;
	public int getAgremIdx() {
		return agremIdx;
	}
	public void setAgremIdx(int agrem_idx) {
		this.agremIdx = agrem_idx;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDoctorIdx() {
		return doctorIdx;
	}
	public void setDoctorIdx(String doctor_idx) {
		this.doctorIdx = doctor_idx;
	}
}
