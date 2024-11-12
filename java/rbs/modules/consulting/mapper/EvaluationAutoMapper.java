package rbs.modules.consulting.mapper;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("evaluationAutoMapper")
public class EvaluationAutoMapper extends EgovAbstractMapper2{

	public int insertExpert(Map<String, Object> param) {
		return super.insert("rbs.modules.evaluation.evaluationAutoMapper.insertExpert", param);
	}
	
	public int insertBusin(Map<String, Object> param) {
		return super.insert("rbs.modules.evaluation.evaluationAutoMapper.insertBusin", param);
	}
	
	public int insertEntrst(Map<String, Object> param) {
		return super.insert("rbs.modules.evaluation.evaluationAutoMapper.insertEntrst", param);
	}
	
	public int update4uEntrst(Map<String, Object> param) {
		return super.insert("rbs.modules.evaluation.evaluationAutoMapper.update4uEntrst", param);
	}
	
}





