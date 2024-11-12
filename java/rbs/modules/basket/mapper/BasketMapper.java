package rbs.modules.basket.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.woowonsoft.egovframework.form.DataMap;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import rbs.modules.basket.dto.Basket;
import rbs.modules.basket.dto.BskClAtmcDto;
import rbs.modules.basket.dto.BskClLogDto;
import rbs.modules.basket.dto.BskClPassivDto;
import rbs.modules.basket.dto.BskHashTagDto;
import rbs.modules.basket.dto.BskRecDto;
import rbs.modules.basket.dto.Criteria;
import rbs.modules.basket.dto.ExcelUploadDto;
import rbs.modules.basket.dto.HashTagDto;
import rbs.modules.basket.dto.ClLclasDto;
import rbs.modules.basket.dto.ClResveDto;
import rbs.modules.basket.dto.ClSclasDto;
import rbs.modules.basket.dto.TrDataDto;
import rbs.modules.basket.dto.ZipCodeDto;

/**
 * 샘플모듈에 관한 데이터 접근 클래스를 정의한다.
 */
@Repository("basketMapper")
public class BasketMapper extends EgovAbstractMapper {

	@Resource(name = "sqlSession")
	private SqlSessionFactory sqlSessionFactory;

	/**
	 * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
	 * 
	 * @param param
	 *            검색조건
	 * @return List<Object> 목록정보
	 */
	public List<Basket> getList(Criteria cri) {
		return selectList("rbs.modules.basket.basketMapper.selectList", cri);
	}

	public List<Basket> getAllList(Criteria cri) {
		return selectList("rbs.modules.basket.basketMapper.selectAllList", cri);
	}

	/**
	 * 총 갯수를 조회한다.
	 * 
	 * @param param
	 *            검색조건
	 * @return int 총갯수
	 */
	public int getTotalCount(Criteria cri) {
		return (Integer) selectOne("rbs.modules.basket.basketMapper.selectCount", cri);
	}

	/**
	 * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
	 * 
	 * @param param
	 *            검색조건
	 * @return List<Object> 목록정보
	 */
	public List<Object> getResList(Map<String, Object> param) {
		return (List<Object>) selectList("rbs.modules.basket.basketMapper.selResList", param);
	}

	/**
	 * 총 갯수를 조회한다.
	 * 
	 * @param param
	 *            검색조건
	 * @return int 총갯수
	 */
	public int getResCount(Map<String, Object> param) {
		return (Integer) selectOne("rbs.modules.basket.basketMapper.selResCount", param);
	}

	/**
	 * 파일항목 목록
	 * 
	 * @param param
	 * @return
	 */
	public List<Object> getFileList(Map<String, Object> param) {
		return (List<Object>) selectList("rbs.modules.basket.basketMapper.selectFileList", param);
	}

	/**
	 * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
	 * 
	 * @param param
	 *            검색조건
	 * @return 상세정보
	 */
	public DataMap getFileView(Map<String, Object> param) {
		return (DataMap) selectOne("rbs.modules.basket.basketMapper.selectFileView", param);
	}

	/**
	 * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
	 * 
	 * @param param
	 *            검색조건
	 * @return 상세정보
	 */
	public DataMap getView(Map<String, Object> param) {
		return (DataMap) selectOne("rbs.modules.basket.basketMapper.selectView", param);
	}

	/**
	 * 다운로드수 데이터를 데이터베이스에 반영
	 * 
	 * @param param
	 *            수정정보
	 */
	public int updateFileDown(Map<String, Object> param) {
		return super.update("rbs.modules.basket.basketMapper.updateFileDown", param);
	}

	/**
	 * 권한여부 조회
	 * 
	 * @param param
	 * @return
	 */
	public int getAuthCount(Map<String, Object> param) {
		return (Integer) selectOne("rbs.modules.basket.basketMapper.authCount", param);
	}

	/**
	 * 등록된 정보 중 검색조건에 맞는 정보를 데이터베이스에서 읽어와 화면에 출력
	 * 
	 * @param param
	 *            검색조건
	 * @return 상세정보
	 */
	public DataMap getModify(Map<String, Object> param) {
		return (DataMap) selectOne("rbs.modules.basket.basketMapper.selectModify", param);
	}

	/**
	 * 게시물 key 조회한다.
	 * 
	 * @return int key
	 */
	public int getNextId(Map<String, Object> param) {
		return (Integer) selectOne("rbs.modules.basket.basketMapper.nextId", param);
	}

	/**
	 * 정보를 화면에서 입력하여 항목의 정합성을 체크하고 데이터베이스에 저장
	 * 
	 * @param param
	 *            등록정보
	 * @return String 등록결과
	 */
	public int insert(Map<String, Object> param) {
		return super.insert("rbs.modules.basket.basketMapper.insert", param);
	}

	/**
	 * 화면에 조회된일반회원의 기본정보를 수정하여 항목의 정합성을 체크하고 수정된 데이터를 데이터베이스에 반영
	 * 
	 * @param param
	 *            수정정보
	 */
	public int update(Map<String, Object> param) {
		return super.update("rbs.modules.basket.basketMapper.update", param);
	}

	/**
	 * 등록된 정보를 데이터베이스에서 읽어와 화면에 출력
	 * 
	 * @param param
	 *            검색조건
	 * @return List<Object> 삭제목록정보
	 */
	public List<Object> getDeleteList(Map<String, Object> param) {
		return (List<Object>) selectList("rbs.modules.basket.basketMapper.deleteList", param);
	}

	/**
	 * 총 갯수를 조회한다.
	 * 
	 * @param param
	 *            검색조건
	 * @return int 총갯수
	 */
	public int getDeleteCount(Map<String, Object> param) {
		return (Integer) selectOne("rbs.modules.basket.basketMapper.deleteCount", param);
	}

	/**
	 * 화면에 조회된 정보를 데이터베이스에서 삭제 flag 반영
	 * 
	 * @param param
	 *            삭제정보
	 */
	public int delete(Map<String, Object> param) {
		return super.update("rbs.modules.basket.basketMapper.delete", param);
	}

	/**
	 * 화면에 조회된 정보를 데이터베이스에서 복원 flag 반영
	 * 
	 * @param param
	 *            복원정보
	 */
	public int restore(Map<String, Object> param) {
		return super.update("rbs.modules.basket.basketMapper.restore", param);
	}

	/**
	 * 화면에 조회된 정보를 데이터베이스에서 삭제
	 * 
	 * @param param
	 *            완전삭제정보
	 */
	public int cdelete(Map<String, Object> param) {
		return super.delete("rbs.modules.basket.basketMapper.cdelete", param);
	}

	public int addClException(HashMap<String, Object> param) {
		return insert("rbs.modules.basket.basketMapper.clExceptionInsert", param);
	}

	public void insertBskClPassivLclas(List<BskClPassivDto> dtos) {
		SqlSession sqlSession = null;
		sqlSession = this.sqlSessionFactory.openSession(ExecutorType.BATCH);
		try {
			for (BskClPassivDto dto : dtos) {
				insert("rbs.modules.basket.basketMapper.clPassivLclasInsert2", dto);
			}
		} finally {
			sqlSession.flushStatements();
			sqlSession.close();
			sqlSession.clearCache();
		}
	}

	public void insertBskClPassivSclas(List<BskClPassivDto> dtos) {

		SqlSession sqlSession = null;
		sqlSession = this.sqlSessionFactory.openSession(ExecutorType.BATCH);
		try {
			for (BskClPassivDto dto : dtos) {
				if (dto.getSclasCd() != null) {
					insert("rbs.modules.basket.basketMapper.clPassivSclasInsert2", dto);
				}
			}
		} finally {
			sqlSession.flushStatements();
			sqlSession.close();
			sqlSession.clearCache();
		}
	}

	public List<TrDataDto> getTr(Criteria cri) {
		return selectList("rbs.modules.basket.basketMapper.trSelectList", cri);
	}

	public List<HashTagDto> getHashTag(HashMap<String, Object> param) {
		return selectList("rbs.modules.basket.basketMapper.hashTagSelectList", param);
	}

	public void assignHashTag(List<BskHashTagDto> dtos) {
		SqlSession sqlSession = null;
		sqlSession = this.sqlSessionFactory.openSession(ExecutorType.BATCH);
		try {
			for (BskHashTagDto dto : dtos) {
				insert("rbs.modules.basket.basketMapper.hashTagToCorpInsert2", dto);
			}
		} finally {
			sqlSession.flushStatements();
			sqlSession.close();
			sqlSession.clearCache();
		}

	}

	public int updateHashTag(List<BskHashTagDto> dtos) {
		return update("rbs.modules.basket.basketMapper.hashTagUpdate", dtos);
	}

	public Basket getBasketSelect(String bplNo) {
		return selectOne("rbs.modules.basket.basketMapper.basketSelectOne", bplNo);
	}

	public List<ClLclasDto> getClLclasList() {
		return selectList("rbs.modules.basket.basketMapper.clLclasSelectlist");
	}

	public int addResve(ClResveDto dto) {
		return insert("rbs.modules.basket.basketMapper.addResve", dto);
	}

	public DataMap getResevCl(Map<String, Object> param) {
		return (DataMap) selectOne("rbs.modules.basket.basketMapper.getResevCl", param);
	}

	public int editResve(ClResveDto dto) {
		return update("rbs.modules.basket.basketMapper.editResve", dto);
	}

	public int delResve(ClResveDto dto) {
		return update("rbs.modules.basket.basketMapper.delResve", dto);
	}

	public int editClass(ClSclasDto dto) {
		return update("rbs.modules.basket.basketMapper.editClass", dto);
	}

	public int delClass(ClSclasDto dto) {
		return update("rbs.modules.basket.basketMapper.delClass", dto);
	}

	public BskClAtmcDto getBskClAtmcSelect(String bplNo) {
		return selectOne("rbs.modules.basket.basketMapper.bskClAtmsSelect", bplNo);
	}

	public List<BskClPassivDto> getBskClPassivSelect(String bplNo) {
		return selectList("rbs.modules.basket.basketMapper.bskClPassivSelect", bplNo);
	}

	public Object getBskClLogSelect(String bplNo) {
		return selectList("rbs.modules.basket.basketMapper.bskClLogSelect", bplNo);
	}

	public Object getBskHashTagSelect(String bplNo) {
		return selectList("rbs.modules.basket.basketMapper.bskHashTagSelect", bplNo);
	}

	public Object getBskTrLogSelect(String bplNo) {
		return null;
	}

	public int updateBskClPassivLclas(List<BskClPassivDto> dtos) {
		return update("rbs.modules.basket.basketMapper.clPassivLclasUpdate", dtos);
	}

	public List<ClSclasDto> getClSclasList(Integer clLclasCd) {
		return selectList("rbs.modules.basket.basketMapper.clSclasSelectlist", clLclasCd);
	}

	public List<ZipCodeDto> getZipcode() {
		return selectList("rbs.modules.basket.basketMapper.zipSelectList");
	}

	public List<ZipCodeDto> getSigngu(String selectedCtprvn) {
		return selectList("rbs.modules.basket.basketMapper.signguSelect", selectedCtprvn);
	}

	public List<BskHashTagDto> getBskHashtagSelect(String bplNo) {
		return selectList("rbs.modules.basket.basketMapper.bskHashtagSelectList", bplNo);
	}

	public Object deleteHashtag(BskHashTagDto dto) {
		return update("rbs.modules.basket.basketMapper.bskHashtagDelete", dto);
	}

	public DataMap getClassify(Map<String, Object> param) {
		return (DataMap) selectOne("rbs.modules.basket.basketMapper.getClassify", param);
	}

	public List<BskClLogDto> getBskClLogList(String bplNo) {
		return selectList("rbs.modules.basket.basketMapper.bskClLogSelect", bplNo);
	}

	public List<HashMap<String, Object>> getIndustCd() {
		return selectList("rbs.modules.basket.basketMapper.industCdSelectList");
	}

	public int bskDataUpload(HashMap<String, Object> map) {
		return insert("rbs.modules.basket.basketMapper.bskDataInsert", map);
	}

	public int bskDataUploadMerge(HashMap<String, Object> map) {
		return insert("rbs.modules.basket.basketMapper.bskDataMerge", map);
	}

	public int bskDataFileUpload(ExcelUploadDto dto) {
		return insert("rbs.modules.basket.basketMapper.bskDataFileInsert", dto);
	}

	public List<ExcelUploadDto> getExcelUploadList(Criteria cri) {
		return selectList("rbs.modules.basket.basketMapper.bskExcelUploadSelectList", cri);
	}

	public int getTotalExcelCount() {
		return (Integer) selectOne("rbs.modules.basket.basketMapper.bskExcelSelectCount");
	}

	public int bskDataFileResultUpdate(ExcelUploadDto dto) {
		return update("rbs.modules.basket.basketMapper.bskDataFileUpdate", dto);
	}

	public List<BskRecDto> getRecList(String bplNo) {
		return selectList("rbs.modules.basket.basketMapper.bskRecSelectList", bplNo);
	}

	public int addHash(HashTagDto hto) {
		return insert("rbs.modules.basket.basketMapper.addHash", hto);
	}

	public int getInsttIdx(String sbCd) {
		return selectOne("rbs.modules.basket.basketMapper.getInsttIdx", sbCd);
	}

	public int delHash(HashTagDto hto) {
		return update("rbs.modules.basket.basketMapper.delHash", hto);
	}

	public int editHash(HashTagDto hto) {
		return update("rbs.modules.basket.basketMapper.editHash", hto);
	}

	public DataMap getHashTag(Map<String, Object> param) {
		return (DataMap) selectOne("rbs.modules.basket.basketMapper.getHashTag", param);
	}

	public List<String> getBplNoList(Criteria cri) {
		return selectList("rbs.modules.basket.basketMapper.bplNoSelectList", cri);
	}

	public int getWebTotalCount(Criteria cri) {
		return (Integer) selectOne("rbs.modules.basket.basketMapper.selectWebCount", cri);
	}

	public int getTrCount(String bplNo) {
		return (Integer) selectOne("rbs.modules.basket.basketMapper.selectTrCount", bplNo);
	}

	public List<Basket> getWebList(Criteria cri) {
		return selectList("rbs.modules.basket.basketMapper.selectWebList", cri);
	}

	public void updateBskClPassivSclas(List<BskClPassivDto> dtos) {
		update("rbs.modules.basket.basketMapper.clPassivSclasUpdate", dtos);
	}

	public List<Basket> getWebAllList(Criteria cri) {
		return selectList("rbs.modules.basket.basketMapper.selectWebAllList", cri);
	}

	public void updateClException(HashMap<String, Object> param) {
		update("rbs.modules.basket.basketMapper.updateClException", param);

	}

	public void updateClExceptionDtos(List<BskClPassivDto> dtos) {
		update("rbs.modules.basket.basketMapper.updateClExceptionDtos", dtos);
		
	}

	public void addClExceptionDtos(List<BskClPassivDto> dtos) {
		insert("rbs.modules.basket.basketMapper.clExceptionInsertDtos", dtos);
		
	}

	public List<HashMap<String, String>> getBskFncInfo(String bizrNo) {
		return selectList("rbs.modules.basket.basketMapper.selectBskFncInfo", bizrNo);
	}

}