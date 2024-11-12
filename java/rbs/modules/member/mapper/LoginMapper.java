package rbs.modules.member.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.ParamForm;

import rbs.egovframework.LoginVO;
import rbs.modules.basket.dto.BskClAtmcDto;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("loginMapper")
public class LoginMapper extends EgovAbstractMapper {

	/**
	 * 일반 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    public LoginVO login(String siteMode, Map<String, Object> param) {
    	return (LoginVO)selectOne("rbs." + siteMode + ".modules.member.loginMapper.login", param);
    }
    
    public List<Object> getGroupList(Map<String, Object> param) {
    	return (List<Object>)selectList("rbs.usr.modules.member.loginMapper.groupList", param);
    }

    public int loginUpdate(Map<String, Object> param){
        return super.update("rbs.usr.modules.member.loginMapper.loginUpdate", param);
    }
    
    public int loginFail(Map<String, Object> param){
        return super.update("rbs.usr.modules.member.loginMapper.loginFail", param);
    }
    
    public Map getRefreshToken(String id) {
    	return selectOne("rbs.usr.modules.member.loginMapper.getRefreshToken" , id);
    }
    
    public LoginVO loginSSO(String siteMode, Map<String, Object> param) {
    	return (LoginVO)selectOne("rbs." + siteMode + ".modules.member.loginMapper.loginSSO", param);
    }
    public void setRefreshToken(Map param) {
    	insert("rbs.usr.modules.member.loginMapper.setRefreshToken", param);
    }

	public String selectRbsAuthMember(String memberIdx) {
		return selectOne("rbs.usr.modules.member.loginMapper.selectRbsAuthMember", memberIdx);
	}
	
	public String checkAgree(String memberIdx) {
		return selectOne("rbs.usr.modules.member.loginMapper.checkAgree", memberIdx);
	}
	
	public int checkSabun(String memberIdx) {
		return selectOne("rbs.usr.modules.member.loginMapper.checkSabun", memberIdx);
	}
	/**
     * 개인정보제공동의 저장
     * @param param 등록정보
     * @return int 등록결과
     */
    public int agreeInsert(Map<String, Object> param){
    	return super.insert("rbs.usr.modules.member.loginMapper.agreeInsert", param);
    }
}
