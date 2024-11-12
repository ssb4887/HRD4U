package rbs.modules.bsisCnsl.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class RankComparator implements Comparator<Map> {

	@Override
	public int compare(Map o1, Map o2) {
		Integer rank1 = Integer.valueOf((String)o1.get("TP_CD_RANK"));
		Integer rank2 = Integer.valueOf((String)o2.get("TP_CD_RANK"));
		if(rank1 == null) {
			return (rank2 == null) ? 0 : -1;
		}
		if(rank2 == null) {
			return 1;
		}
		return rank1.compareTo(rank2);
	}
	
}
