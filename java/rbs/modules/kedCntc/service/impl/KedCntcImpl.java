package rbs.modules.kedCntc.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import rbs.modules.kedCntc.service.KedCntcService;
import rbs.modules.kedCntc.vo.batchLogVO;
import sun.rmi.runtime.Log;
import rbs.modules.kedCntc.mapper.KedCntcMapper;


/**
 * 샘플모듈에 관한 구현클래스를 정의한다.
 * @author user
 *
 */
@Service("KedCntcService")
public class KedCntcImpl extends EgovAbstractServiceImpl implements KedCntcService {
	@Resource(name="KedCntcMapper")
	private KedCntcMapper kedCntcDAO;
	@Value("${Globals.fileStorePath}")
	private String dir;
	private String uploaddir= "";
	private Path fileDir ;
	private final String TYPE_ZIP = "text/zip" ;
	private String[] acntCd = null;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
    @PostConstruct
	public void postConstruct() {
    	uploaddir = dir+"KED";
		fileDir = Paths.get(uploaddir).toAbsolutePath().normalize();
		try {
			File fs = new File(uploaddir);
			if (!fs.exists()) {
				Files.createDirectories(fileDir);
			}
		} catch (IOException e) {
			//
		}
	}
    

	/**
	 * 서버정보
	 * @param param
	 * @return
	 */
	@Override
	public List<Object> getServerInfo(Map<String, Object> param) {
		return kedCntcDAO.getServerInfo(param);
	}
	
	/**
	 * 가져올 계정 정보
	 * @return
	 */
	@Override
	public List<Object> getACNT_CD() {
		return kedCntcDAO.getACNT_CD();
	}
	
	/**
	 * 당일 파일 처리 상태 정보
	 * @return
	 */
	@Override
	public String getProcInfo() {
		return kedCntcDAO.getProcInfo();
	}
	
    
	/**
	 * 전달받은 파일을 업로드 후 처리
	 * @file MultipartFile
	 * @return
	 */	
	public String uploadFile(MultipartFile file) {
		String strMsg = "정상 처리 되었습니다.";
		batchLogVO logVo = null;
		String programName = "uploadFile";
		String programDesc = "파일 업로드";
		Timestamp startDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
		if ( file.getContentType().equals("application/zip")  ) {
			String uploadFileName = StringUtils.cleanPath(file.getOriginalFilename());
			//로그 기록
			logVo = inslog(programName, programDesc, uploadFileName, startDt);
			
			String realName = UUID.randomUUID().toString() + "_" + uploadFileName;
			Path targetLocation = fileDir.resolve(realName);
			try {
				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				return e.getMessage();
			}

			// 압축파일 풀기
			String zipDir = uploaddir+File.separator+uploadFileName.replace(".zip", "");
			Path zipFileDir = Paths.get(zipDir).toAbsolutePath().normalize();
			try {
				File fs = new File(zipDir);
				if (!fs.exists()) {
					Files.createDirectories(zipFileDir);
				} else {
					//기존에 있는 자료 삭제
					File exsistZipdir = new File(zipDir);
					File[] exsistfiles = exsistZipdir.listFiles();
					if(exsistfiles != null) {
						for(File f : exsistfiles) {
							f.delete();
						}
					}
				}
				upCompressZip(zipDir, uploaddir+File.separator+realName);
			} catch (Exception e) {
				strMsg = e.getMessage();
			}
			//
			
			
			try {
				File unZipdir = new File(zipDir);
				File[] files = sort(unZipdir.listFiles()); // 기업개요를 제일먼저 처리하기 위해 sort 
				int totRowCnt = 0;
				for(File f : files) {
					int rowCnt = 0;
					if ( f.getName().equals("KED50D1_hrdk.txt") ) {        //기업개요
						rowCnt = KED50D1(f);
					} else if ( f.getName().equals("KED5003_hrdk.txt") ) { //주요재무정보
						rowCnt = KED5003(f);
					} else if ( f.getName().equals("KED5016_hrdk.txt") ) { //인원현황
						rowCnt = KED5016(f);
					} else if ( f.getName().equals("KED5041_hrdk.txt") ) { //신용등급
						rowCnt = KED5041(f);
					} else if ( f.getName().equals("KED5058_hrdk.txt") ) { //신계정_재무제표
						// 해당 계정코드만 INSERT 하기위한 전 처리 
						List<Object> acntCdlist = getACNT_CD();
						acntCd = new String[acntCdlist.size()];
						for(int i=0 ; i < acntCdlist.size(); i++) {
							@SuppressWarnings("unchecked")
							HashMap<String, String> hm = (HashMap<String, String>)acntCdlist.get(i);
							acntCd[i] = hm.get("ACNT_FULL_CD").toString();
						}
						rowCnt = KED5058(f);
					} else if ( f.getName().equals("KED5087_hrdk.txt") ) { //휴폐업정보
						rowCnt = KED5087(f);
					}
					
					totRowCnt += rowCnt; 
				}
				logVo.setOPERT_CO(totRowCnt);
			} catch (IOException e) {
				strMsg = e.getMessage();
			} catch (NullPointerException e) {
				strMsg = e.getMessage();
			}
			
			// 작업 종료 로그 기록
			Timestamp endDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
			logVo.setOPERT_END_DT(endDt);
			logVo.setRESULT_MSSAGE(strMsg);
			if (strMsg.equals("정상 처리 되었습니다.")) {
				logVo.setOPERT_RESULT("DONE");
				strMsg = "OK";
			} else {
				logVo.setOPERT_RESULT("FAIL");
			}
		} else {
			strMsg = "It's allow not file.";
			logVo = inslog(programName, programDesc, file.getContentType().toString(), startDt);
			Timestamp endDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
			logVo.setOPERT_RESULT("REJECT");
			logVo.setOPERT_END_DT(endDt);
			logVo.setRESULT_MSSAGE(strMsg);
		}
		
		logVo = updlog(logVo);
		return strMsg;
		
	}
	
	
	public String onlyUploadFile(MultipartFile file) {
		
		String strMsg = "정상 처리 되었습니다.";
		batchLogVO logVo = null;
		String programName = "onlyUploadFile";
		String programDesc = "파일만 업로드";
		Timestamp startDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
		
		if ( file.getContentType().equals("application/zip")  ) {
			
			String uploadFileName = StringUtils.cleanPath(file.getOriginalFilename());
			//로그 기록
			logVo = inslog(programName, programDesc, uploadFileName, startDt);		
						
			String realName = UUID.randomUUID().toString() + "_" + uploadFileName;
			Path targetLocation = fileDir.resolve(realName);
			
			try {
				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				return e.getMessage();
			}
			
			// 압축파일 풀기
			String zipDir = uploaddir+File.separator+uploadFileName.replace(".zip", "");
			Path zipFileDir = Paths.get(zipDir).toAbsolutePath().normalize();
			try {
				File fs = new File(zipDir);
				if (!fs.exists()) {
					Files.createDirectories(zipFileDir);
				} else {
					//기존에 있는 자료 삭제
					File exsistZipdir = new File(zipDir);
					File[] exsistfiles = exsistZipdir.listFiles();
					if(exsistfiles != null) {
						for(File f : exsistfiles) {
							f.delete();
						}
					}
				}
				upCompressZip(zipDir, uploaddir+File.separator+realName);
			} catch (Exception e) {
				strMsg = e.getMessage();
			}
			try {
				File unZipdir = new File(zipDir);
				sort(unZipdir.listFiles());
			} catch (NullPointerException e) {
				return e.getMessage();
			}
			
			// 로그 업데이트
			Timestamp endDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
			logVo.setOPERT_END_DT(endDt);
			if (strMsg.equals("정상 처리 되었습니다.")) {
				logVo.setOPERT_RESULT("DONE");
				logVo.setRESULT_MSSAGE(strMsg);
				strMsg = "OK";
			} else {
				logVo.setOPERT_RESULT("FAIL");
				logVo.setRESULT_MSSAGE(strMsg);
			}
		} else {
			strMsg = "It's allow not file.";
			logVo = inslog(programName, programDesc, file.getContentType().toString(), startDt);
			Timestamp endDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
			logVo.setOPERT_RESULT("REJECT");
			logVo.setOPERT_END_DT(endDt);
			logVo.setRESULT_MSSAGE(strMsg);
			
		}
		updlog( logVo);
		return strMsg;
	}
	
	public void upCompressZip(String zipFolder, String zipfile)  throws Exception {
		//로그 기록
		batchLogVO logVo = null;
		String programName = "UploadFileProc";
		String programDesc = "upCompressZip";
		int    fileCnt = 0 ;
		
		Timestamp startDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());		
		logVo = inslog(programName, programDesc, zipfile, startDt);
		
		File zipFile = new File(zipfile);
		
		try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(zipFile))){
			try ( ZipInputStream zipInputStream = new ZipInputStream(in)) {
				ZipEntry zipEntry = null;
				while ((zipEntry = zipInputStream.getNextEntry()) != null) {
					int length = 0;
					try ( BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(zipFolder + File.separator + zipEntry.getName() ))) {
						while (( length = zipInputStream.read()) != -1 ) {
							out.write(length);
						}
						zipInputStream.closeEntry();
					}
					fileCnt ++;
				}
			}
		}
		
		//로그 기록
		Timestamp endDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
		logVo.setOPERT_END_DT(endDt);
		logVo.setOPERT_RESULT("DONE");
		logVo.setRESULT_MSSAGE("정상 처리 되었습니다.");
		logVo.setOPERT_CO(fileCnt);
		updlog(logVo);
		//===================
	}
	
	/**
     * 기업개요 파일 파싱 전문번호 : 0002 
     * @param File  파일명 : KED50D1.txt
     * @return 
     */
	public int KED50D1(File file) throws IOException  {
		BufferedReader br = null;
		int rowCnt = 0;
		//로그 기록
		batchLogVO logVo = null;
		String programName = "UploadFileProc";
		String programDesc = "기업개요 파일";
		
		Timestamp startDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());		
		logVo = inslog(programName, programDesc, file.getName(), startDt);
		
		String line = "";
		String[] strKey= {"KED_CD", "ENP_NM", "ENP_NM_TRD","ENP_NM_ENG","ENP_TYP", "ENP_SZE", "GRDT_PLN_DT", "ENP_FCD", "ESTB_FCD", "ENP_SCD",
		                  "ENP_SCD_CHG_DT", "PUBI_FCD", "VENP_YN", "ENP_FORM_FR", "BZC_CD", "FS_BZC_CD", "GRP_CD", "GRP_NM", "CONO_PID",
		                  "ESTB_DT", "IPO_CD", "TRDBZ_RPT_NO", "LIST_DT", "DLIST_DT", "MTX_BNK_CD", "MTX_BNK_NM", "OVD_TX_BNK_CD", "OVD_TX_BNK_NM",
		                  "ACCT_EDDT", "HPAGE_URL", "EMAIL", "STD_DT", "BZNO", "LOC_ZIP", "LOC_ADDRA", "LOC_ADDRB", "TEL_NO", "FAX_NO",
		                  "LABORER_SUM", "PD_NM", "KSIC9_BZC_CD", "REL_KEDCD", "REL_ESTB_DT", "LOC_RDNM_ZIP", "LOC_RDNM_ADDRA", "LOC_RDNM_ADDRB",
		                  "LOC_RDNM_ADDRB_CONF_YN", "LOC_ADDRB_CONF_YN"};
		
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, "MS949");
		br = new BufferedReader(isr);
//		br = new BufferedReader(new FileReader(file));
		
		while( (line = br.readLine()) != null ) {
			String[] strtmp = line.split("\\|") ;
			Map<String, Object> param = new HashMap<String, Object>();
			int i = 0;
			
			for (String strvalue: strtmp) {
				param.put(strKey[i], strvalue);
				i++;
			}
			kedCntcDAO.insKedCorp(param);
			rowCnt ++;
		}
		
		br.close();
		
		HashMap<String, String> hm = new HashMap<String, String>(); 
		hm.put("P_WRT_DATE", sdf.format(startDt));
		kedCntcDAO.execPkgKedCorp(hm);
		
		//로그 기록
		Timestamp endDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
		logVo.setOPERT_END_DT(endDt);
		logVo.setOPERT_RESULT("DONE");
		logVo.setRESULT_MSSAGE("정상 처리 되었습니다.");
		logVo.setOPERT_CO(rowCnt);
		updlog(logVo);
		//===================
		return rowCnt;
	}
	
	/**
     * 주요재무정보 파일 파싱 전문번호 : 0003 
     * @param File  파일명 : KED5003.txt
     * @return 
     */
	public int KED5003(File file) throws IOException {
		BufferedReader br = null;
		String[] strKey = {"KED_CD","ACCT_DT","SUMASSET","PAYMENTFUND","FUNDTOTAL","SALES","PROFIT","TERMNETPROFIT"};
		String line = "";
		int  rowCnt = 0;
		
		batchLogVO logVo = null;
		String programName = "UploadFileProc";
		String programDesc = "주요재무정보";
		
		Timestamp startDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
		//로그 기록
		logVo = inslog(programName, programDesc, file.getName(), startDt);
		
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, "MS949");
		br = new BufferedReader(isr);
//		br = new BufferedReader(new FileReader(file));
		
		while( (line = br.readLine()) != null ) {
			String[] strtmp = line.split("\\|") ;
			Map<String, Object> param = new HashMap<String, Object>();
			int i = 0;

			for (String strvalue: strtmp) {
				param.put(strKey[i], strvalue);
				i++;
			}
			kedCntcDAO.insKedFnc(param);
			rowCnt ++;
		}
		br.close();
		HashMap<String, String> hm = new HashMap<String, String>(); 
		hm.put("P_WRT_DATE", sdf.format(startDt));
		kedCntcDAO.execPkgKedFnc(hm);
		//로그 기록
		Timestamp endDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
		logVo.setOPERT_END_DT(endDt);
		logVo.setOPERT_RESULT("DONE");
		logVo.setRESULT_MSSAGE("정상 처리 되었습니다.");
		logVo.setOPERT_CO(rowCnt);
		updlog(logVo);
		//===================
			
		return rowCnt;
		
	}
	
	/**
     * 신용등급 파일 파싱 전문번호 : 0041
     * @param File   파일명 : KED5041.txt
     * @return 
     */
	
	public int KED5041(File file) throws IOException  {	
		BufferedReader br = null;
		String line = "";
		int rowCnt = 0;
		batchLogVO logVo = null;
		String programName = "UploadFileProc";
		String programDesc = "신용등급";
		
		Timestamp startDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
		//로그 기록
		logVo = inslog(programName, programDesc, file.getName(), startDt);
		
		String[] strKey = {"KED_CD", "REG_DT", "EVL_DT", "STTL_BASE_DD", "GRD_CLS", "CR_GRD", "MODL_EVL_DT", "IVG_MTD_CLS", "GRD_SV_ST_DD", "GRD_SV_EDDT"};
		
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, "MS949");
		br = new BufferedReader(isr);
//		br = new BufferedReader(new FileReader(file));
		
		while( (line = br.readLine()) != null ) {
			String[] strtmp = line.split("\\|") ;
			Map<String, Object> param = new HashMap<String, Object>();
			int i = 0;
			for (String strvalue: strtmp) {
				param.put(strKey[i], strvalue);
				i++;
			}
			kedCntcDAO.insKedCre(param);
			rowCnt ++;
		}
		br.close();
		HashMap<String, String> hm = new HashMap<String, String>(); 
		hm.put("P_WRT_DATE", sdf.format(startDt));
		kedCntcDAO.execPkgKedCre(hm);
		//로그 기록
		Timestamp endDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
		logVo.setOPERT_END_DT(endDt);
		logVo.setOPERT_RESULT("DONE");
		logVo.setRESULT_MSSAGE("정상 처리 되었습니다.");
		logVo.setOPERT_CO(rowCnt);
		updlog(logVo);
		//===================
	
		return rowCnt;
	}
	
	/**
     * 인원현황 파일파싱 전문번호 : 0016 
     * @param file 파일명 : KED5016.txt
     * @return 
     */
	public int KED5016(File file) throws IOException  {		
		BufferedReader br = null;
		String line = "";
		String[] strKey = {"KED_CD","STD_DT","TTL_CD","ORDN_MEM","ORDN_FEM","ORDN_EM","T_YSLRY_MEM","T_YSLRY_FM","T_YSLRY","AVG_SLRY_PE_MEM","AVG_SLRY_PE_FE","AVG_SLRY_PE","AVG_WK_PRD_MEM","AVG_WK_PRD_FM","AVG_WK_PRD"};
		int rowCnt=0;
		
		batchLogVO logVo = null;
		String programName = "UploadFileProc";
		String programDesc = "인원현황";
		
		Timestamp startDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
		//로그 기록
		logVo = inslog(programName, programDesc, file.getName(), startDt);

		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, "MS949");
		br = new BufferedReader(isr);		
//		br = new BufferedReader(new FileReader(file));	
		
		while( (line = br.readLine()) != null ) {
			String[] strtmp = line.split("\\|") ;
			Map<String, Object> param = new HashMap<String, Object>();
			int i = 0;				
			for (String strvalue: strtmp) {
				param.put(strKey[i], strvalue);
				i++;
			}

			kedCntcDAO.insKedEmp(param);
			rowCnt++;
		}		
		br.close();
		HashMap<String, String> hm = new HashMap<String, String>(); 
		hm.put("P_WRT_DATE", sdf.format(startDt));
		kedCntcDAO.execPkgKedEmp(hm);
		//로그 기록
		Timestamp endDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
		logVo.setOPERT_END_DT(endDt);
		logVo.setOPERT_RESULT("DONE");
		logVo.setRESULT_MSSAGE("정상 처리 되었습니다.");
		logVo.setOPERT_CO(rowCnt);
		updlog(logVo);
		//===================
		return rowCnt;
	}

	/**
     * 신계정 재무제표 파일파싱 전문번호 : 0058 
     * @param file 파일명 : KED5058.txt
     * @return 
     */
	public int KED5058(File file) throws IOException {		
		BufferedReader br = null;
		String[] strKey = {"KED_CD","ACCT_DT","FS_CCD","ACCT_CCD","ACC_CD_FULL","VAL","RT" };
		String line = "";
		int rowCnt = 0;
		
		batchLogVO logVo = null;
		String programName = "UploadFileProc";
		String programDesc = "신계정 재무제표";
		
		Timestamp startDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
		//로그 기록
		logVo = inslog(programName, programDesc, file.getName(), startDt);
	
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, "MS949");
		br = new BufferedReader(isr);		
//		br = new BufferedReader(new FileReader(file));
		
		while( (line = br.readLine()) != null ) {
			String[] strtmp = line.split("\\|") ;
			Map<String, Object> param = new HashMap<String, Object>();
			int i = 0;
			for (String strvalue: strtmp) {
				param.put(strKey[i], strvalue);
				i++;
			}
			
			if ( Arrays.asList(acntCd).contains(param.get("ACC_CD_FULL").toString())) {				
				kedCntcDAO.insKedAnal(param);
				rowCnt ++;
			} 
		}
	
		br.close();
		
		HashMap<String, String> hm = new HashMap<String, String>(); 
		hm.put("P_WRT_DATE", sdf.format(startDt));
		kedCntcDAO.execPkgKedAnal(hm);
		
		//로그 기록
		Timestamp endDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
		logVo.setOPERT_END_DT(endDt);
		logVo.setOPERT_RESULT("DONE");
		logVo.setRESULT_MSSAGE("정상 처리 되었습니다.");
		logVo.setOPERT_CO(rowCnt);
		updlog(logVo);
		//===================
		
		return rowCnt;
	}
	
	/**
     * 휴폐업정보 파일파싱 전문번호 : 0087 
     * @param file 파일명 : KED5087_hrdk.txt
     * @return 
     */
	public int KED5087(File file) throws IOException {		
		BufferedReader br = null;
		String[] strKey = {"BZNO","LT_IQ_DT","BASE_DT","LQDT_CLS","LQDT_DT" };
		String line = "";
		int rowCnt = 0;
		
		batchLogVO logVo = null;
		String programName = "UploadFileProc";
		String programDesc = "휴폐업정보";
		
		Timestamp startDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
		//로그 기록
		logVo = inslog(programName, programDesc, file.getName(), startDt);
		
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, "MS949");
		br = new BufferedReader(isr);		
//		br = new BufferedReader(new FileReader(file));
		
		while( (line = br.readLine()) != null ) {
			String[] strtmp = line.split("\\|") ;
			Map<String, Object> param = new HashMap<String, Object>();
			int i = 0;
			for (String strvalue: strtmp) {
				param.put(strKey[i], strvalue);
				i++;
			}
			
			kedCntcDAO.insKedSpcss(param);
			rowCnt ++;
			 
		}
			
		br.close();
		
		HashMap<String, String> hm = new HashMap<String, String>(); 
		hm.put("P_WRT_DATE", sdf.format(startDt));
		kedCntcDAO.execPkgKedSpcss(hm);
		
		//로그 기록
		Timestamp endDt = new Timestamp(java.util.Calendar.getInstance().getTimeInMillis());
		logVo.setOPERT_END_DT(endDt);
		logVo.setOPERT_RESULT("DONE");
		logVo.setRESULT_MSSAGE("정상 처리 되었습니다.");
		logVo.setOPERT_CO(rowCnt);
		updlog(logVo);
		//===================
		
		
		return rowCnt;
	}
	
	public String onlyDBinsert(String strdir) {
		try {
			String zipDir = uploaddir+File.separator+strdir;
	
			File unZipdir = new File(zipDir);
	//		File[] files = unZipdir.listFiles();
			
			File[] files = sort(unZipdir.listFiles()); // 기업개요를 제일먼저 처리하기 위해 sort 
			for(File f : files) {
				int rowCnt = 0;
				
				if ( f.getName().equals("KED50D1_hrdk.txt") ) {        //기업개요
					rowCnt = KED50D1(f);
				} else if ( f.getName().equals("KED5003_hrdk.txt") ) { //주요재무정보
					KED5003(f);
				} else if ( f.getName().equals("KED5016_hrdk.txt") ) { //인원현황
					KED5016(f);
				} else if ( f.getName().equals("KED5041_hrdk.txt") ) { //신용등급
					KED5041(f);
				} else if ( f.getName().equals("KED5058_hrdk.txt") ) { //신계정_재무제표
					// 해당 계정코드만 INSERT 하기위한 전 처리 
					List<Object> acntCdlist = getACNT_CD();
					acntCd = new String[acntCdlist.size()];
					for(int i=0 ; i < acntCdlist.size(); i++) {
						@SuppressWarnings("unchecked")
						HashMap<String, String> hm = (HashMap<String, String>)acntCdlist.get(i);
						acntCd[i] = hm.get("ACNT_FULL_CD").toString();
					}
					KED5058(f);
				}  else if ( f.getName().equals("KED5087_hrdk.txt") ) { //휴폐업정보
					rowCnt = KED5087(f);
				}
			
			}
		} catch (IOException e) {
			return e.getMessage();
		} catch (NullPointerException e) {
			return e.getMessage();
		}
		return "OK";
	}
	
   /**
    * 작업 로그 기록 
    * @param programName: 프로그램명, programDesc: 프로그램 설명, parameter: 호출파라메터, startDt: 처리시작시간
    * @return batchLogVO
    */
	public batchLogVO inslog(String programName, String programDesc, String parameter, Timestamp startDt) {
		batchLogVO logVo = new batchLogVO(programName, programDesc, parameter, startDt);
		return kedCntcDAO.execPkgBatchLog(logVo);
		
	}
	
   /**
    * 작업 로그 죵료 기록 
    * @param batchLogVO
    * @return batchLogVO
    */
	public batchLogVO updlog(batchLogVO logVo) {		
		return kedCntcDAO.execPkgBatchLog(logVo);		
	}
	
	public File[] sort(File[] files ) {
		int i = 0;
		int startidx=0;
		for (File f: files) {
			if( f.getName().indexOf("50D") > 0 ) {
				File tmpfile = files[i];				
				for(int j = i; j > startidx ; j--) {
					files[j] = files[j-1] ;
				}
				files[startidx] = tmpfile;
				startidx++;
			}			
			i++ ;
		}
		
		return files;
	}
	
}
