package rbs.modules.recommend;

public class PrtbizVO {
	String prtbiz;
	String lclas;
	String mclas;
	String sclas;
	String name;
	public String getPrtbiz() {
		return prtbiz;
	}
	public void setPrtbiz(String prtbiz) {
		this.prtbiz = prtbiz;
	}
	public String getLclas() {
		return lclas;
	}
	public void setLclas(String lclas) {
		this.lclas = lclas;
	}
	public String getMclas() {
		return mclas;
	}
	public void setMclas(String mclas) {
		this.mclas = mclas;
	}
	public String getSclas() {
		return sclas;
	}
	public void setSclas(String sclas) {
		this.sclas = sclas;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "PrtbizVO [prtbiz=" + prtbiz + ", lclas=" + lclas + ", mclas=" + mclas + ", sclas=" + sclas + ", name="
				+ name + "]";
	}
	
}
