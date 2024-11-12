package rbs.modules.recommend;

public enum PRISUP {
	MAJOR(1),
	SMALL(2);
	
	private int value;
	
	private PRISUP(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static PRISUP fromValue(int value) {
		for (PRISUP prisup : PRISUP.values()) {
			if(prisup.getValue() == value) {
				return prisup;
			}
		}
		throw new IllegalArgumentException("Invalid value: " + value);
	}
	
}
