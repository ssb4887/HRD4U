package rbs.modules.edu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import rbs.modules.edu.mapper.EduMapper;
import rbs.modules.edu.service.EduService;

@Service("eduService")
public class EduServiceImple extends EgovAbstractServiceImpl implements EduService {

	@Resource(name = "eduMapper")
	private EduMapper eduDAO;
	
	
}
