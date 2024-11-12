package rbs.modules.recommend;

import java.io.Serializable;

public class QrVO implements Serializable {
	private String image;
	private String url;
	private String title;
	private int manageIdx;
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getManageIdx() {
		return manageIdx;
	}
	public void setManageIdx(int manageIdx) {
		this.manageIdx = manageIdx;
	}
	
}
