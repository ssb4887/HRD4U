package rbs.modules.bsisCnsl.web;

import java.util.Comparator;
import java.util.Map;

public class AnswerComparator implements Comparator<Map<String, Object>> {

	@Override
	public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		int questionIdx1 = Integer.parseInt((String) o1.get("questionIdx"));
		int questionIdx2 = Integer.parseInt((String) o2.get("questionIdx"));
		return Integer.compare(questionIdx1, questionIdx2);
	}
	
}
