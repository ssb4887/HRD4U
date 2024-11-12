package rbs.modules.plagiarism;

import java.util.Comparator;
import java.util.Map;

public class RateComparator implements Comparator<Map> {

	@Override
	public int compare(Map o1, Map o2) {
		float firstValue = (float)o1.get("rate");
		float secondValue = (float)o2.get("rate");
		if(firstValue > secondValue) {
			return -1;
		} else if(firstValue < secondValue) {
			return 1;
		}
		return 0;
	}

}
