package rbs.modules.kedCntc.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface KedCntcService {
	/**
	 * 서버 정보
	 * @param param
	 * @return
	 */
	public List<Object> getServerInfo(Map<String, Object> param);
	public List<Object> getACNT_CD();
	public void postConstruct() ;
	public String uploadFile(MultipartFile file);
	public String onlyUploadFile(MultipartFile file);
	public String onlyDBinsert(String dir);
	public String getProcInfo();
}
