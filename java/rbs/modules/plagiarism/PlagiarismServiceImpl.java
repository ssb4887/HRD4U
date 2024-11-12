package rbs.modules.plagiarism;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("plagiarismService")
public class PlagiarismServiceImpl extends EgovAbstractServiceImpl implements PlagiarismService {
	@Resource(name = "plagiarismMapper")
	PlagiarismMapper plagiarismDAO;
}
