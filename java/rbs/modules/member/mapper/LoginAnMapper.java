package rbs.modules.member.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import rbs.egovframework.LoginVO;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("loginAnMapper")
public class LoginAnMapper extends EgovAbstractMapper {

	/**
	 * 일반 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    public LoginVO login(String siteMode, Map<String, Object> param) {
    	return (LoginVO)selectOne("rbs.adm.modules.member.loginMapper.login", param);
    }

    public List<Object> getGroupList(Map<String, Object> param) {
    	return (List<Object>)selectList("rbs.adm.modules.member.loginMapper.groupList", param);
    }

    public int loginUpdate(Map<String, Object> param){
        return super.update("rbs.adm.modules.member.loginMapper.loginUpdate", param);
    }
    
    public int loginFail(Map<String, Object> param){
        return super.update("rbs.adm.modules.member.loginMapper.loginFail", param);
    }
}
