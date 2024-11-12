package rbs.modules.basket.dto;

import java.util.Arrays;

public class ZipCodeDto {
	private String ctprvn;
	
	private String signgu;
	
	private String[] zips;

	public String getCtprvn() {
		return ctprvn;
	}

	public void setCtprvn(String ctprvn) {
		this.ctprvn = ctprvn;
	}

	public String getSigngu() {
		return signgu;
	}

	public void setSigngu(String signgu) {
		this.signgu = signgu;
	}

	public String[] getZips() {
		String[] safetyZips = null;
		if(this.zips != null) {
			safetyZips = new String[zips.length];
			for(int i= 0; i< zips.length; i++) {safetyZips[i] = this.zips[i];}
		}
		return safetyZips;
	}

	public void setZips(String zips) {
		this.zips = zips.split(zips, ',');
	}

	public ZipCodeDto(String ctprvn, String signgu, String[] zips) {
		super();
		this.ctprvn = ctprvn;
		this.signgu = signgu;
		this.zips = zips;
	}

	public ZipCodeDto() {
		super();
	}

	@Override
	public String toString() {
		return "ZipCodeDto [ctprvn=" + ctprvn + ", signgu=" + signgu + ", zips=" + Arrays.toString(zips) + "]";
	}

	
	
}
