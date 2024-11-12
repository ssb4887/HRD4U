package rbs.modules.consulting.dto;
public class CnslDiaryAttDTO {
	private int cnslIdx;			// 컨설팅 식별색인
	private int diaryIdx;			// 일지 식별색인
	private int attIdx;				// 참석 식별색인
	private int teamIdx;			// 팀 식별색인
	
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
	public int getAttIdx() {
		return attIdx;
	}
	public void setAttIdx(int attIdx) {
		this.attIdx = attIdx;
	}
	public int getTeamIdx() {
		return teamIdx;
	}
	public void setTeamIdx(int teamIdx) {
		this.teamIdx = teamIdx;
	}
	
	@Override
	public String toString() {
		return "CnslDiaryAttDTO [cnslIdx=" + cnslIdx + ", diaryIdx=" + diaryIdx + ", attIdx=" + attIdx + ", teamIdx="
				+ teamIdx + "]";
	}
}
