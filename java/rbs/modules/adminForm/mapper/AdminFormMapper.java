package rbs.modules.adminForm.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import rbs.modules.basket.dto.ExcelUploadDto;


@Repository("adminFormMapper")
public class AdminFormMapper extends EgovAbstractMapper{

    /**
     * 방문기업서식 목록 조회
     */
	public List<Object> getList(Map<String, Object> param){
		return (List<Object>)selectList("rbs.modules.adminForm.adminFormMapper.selectList", param);
    }
	
	/**
	 * 방문기업서식 갯수 조회
	 */
	public int getTotalCount(Map<String, Object> param) {
		return (Integer)selectOne("rbs.modules.adminForm.adminFormMapper.selectCount", param);
	}

    /**
     * 공단 담당자 정보 조회
     */
	public Map getDoc(String memberIdx) {
		return (Map)selectOne("rbs.modules.adminForm.adminFormMapper.selectDoc", memberIdx);
	}
	
    /**
     * 상세조회
     */
	public DataMap getView(Map<String, Object> param) {
		return (DataMap)selectOne("rbs.modules.adminForm.adminFormMapper.selectView", param);
	}
	
    /**
     * 방문기업서식 등록
     */
    public int setForm(List<Map<String, Object>> param){
    	return super.update("rbs.modules.adminForm.adminFormMapper.update", param);
    }
	
    /**
     * 방문기업서식 저장
     */
    public int insert(Map<String, Object> param){
    	return super.insert("rbs.modules.adminForm.adminFormMapper.insert", param);
    }

    /**
     * 방문기업서식 수정
     */
    public int update(Map<String, Object> param){
    	return super.update("rbs.modules.adminForm.adminFormMapper.update",param);
    }

    /**
     * 방문기업서식 삭제
     */
    public int delete(Map<String, Object> param){
    	return super.delete("rbs.modules.adminForm.adminFormMapper.delete",param);
    }

    /**
     * 방문기업서식 파일저장
     */
    public int insertFile(ExcelUploadDto dto){
    	return super.insert("rbs.modules.adminForm.adminFormMapper.insertFile", dto);
    }

    /**
     * 방문기업서식 파일수정
     */
    public int updateFile(ExcelUploadDto dto){
    	return super.update("rbs.modules.adminForm.adminFormMapper.updateFile",dto);
    }
    
    /**
     * 방문기업서식 파일 상세조회
     */
    public DataMap getFileView(Map<String, Object> param){
    	return (DataMap)selectOne("rbs.modules.adminForm.adminFormMapper.selectFileView",param);
    }

}