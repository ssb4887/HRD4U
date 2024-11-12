package rbs.modules.plagiarism;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

enum Cells {
	ID(1),
	BPL_NAME(2),
	BPL_NO(3);
		
	private final int value;
	
	Cells(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}

@Component("plagiarism")
public class PlagiarismCore {
	private final List<Map> cache;
	private final String path;
	private final String filename;
	private final HashMap<String,Map> store;
	
	public PlagiarismCore() throws IOException {
		filename = "sojt.xlsx";
		String path_ = "/data1/hrdDoctor/WEB-INF/jsp/rbs/usr/recommend/";
		File file_ = new File(path_, this.filename);
		if(file_.exists()) {
			path = path_;
		} else {
			System.out.println("No FILE! @ " + file_.getAbsolutePath());
			File dir2 = new File(this.getClass().getResource("/").getPath());
			String dir2_path = dir2.getParentFile().getAbsolutePath();
			StringBuilder sb = new StringBuilder();
			sb.append(dir2_path);
			sb.append("\\jsp\\rbs\\usr\\recommend\\");
			path = sb.toString();
		}
		cache = ExcelRun();
		store = new HashMap<String,Map>();
	}
	
	@Scheduled(cron = "0 0/10 * * * ?")
	public void ticktok() {
		Date now = new Date();
		String nowTime = now.toString();
		System.out.println("Tik. Tok. " + nowTime);
		// 데이터를 케쉬에 추가해야됨 자료구조는 GetList 참고
	}
	
	public List<Map> run(String cnsl_idx, String target) {
		List<Map> result = new ArrayList();
		for(Map m_ : this.cache) {
			Set<String> target_ = makeSet(target);
			List<String> matched = new ArrayList<String>();
			for(String t : target_) {
				for(String c : (Set<String>)m_.get("set")) {
					if(t.equals(c)) {
						if(!c.isEmpty()) {
							matched.add(c);
						}
					}
				}
			}
			Map result_ = new HashMap();
			float rate = (float)((float)matched.size() / (float)target_.size())*100L;
			result_.put("rate", rate);
			result_.put("similar", matched.size());
			result_.put("total", target_.size());
			result_.put("totals", target_);
			result_.put("matched", matched);
			result_.put("source", m_.get("source"));
			result.add(result_);
		}
		Comparator<Map> comparator = new RateComparator();
		Collections.sort(result, comparator);
		store.put(cnsl_idx, result.get(0));
		return result;
	}
	
	private Set<String> makeSet(String source) {
		String escapeRemoved = source.replaceAll("\r", "").replaceAll("\n", "");
		String[] splitted = escapeRemoved.split(" ");
		Set<String> set_ = new HashSet<>(Arrays.asList(splitted));
		return set_;
	}
	
	public List<Map> GetExcelData() {
		return this.cache;
	}
	public String GetPath() {
		return this.path;
	}
	public String GetFilename() {
		return this.filename;
	}
	
	private List<Map> GetList(XSSFSheet sheet) {
		List<Map> result = new ArrayList<Map>();
		String require_name = "";
		String job_name = "";
		
		int i = 0;
		
		for(Row row : sheet) {
			Map map = new HashMap();
			Iterator<Cell> iterator = row.cellIterator();
			Cell c_id = row.getCell(0);
			Cell c_bpl_name = row.getCell(1);
			Cell c_bpl_no = row.getCell(2);
			List<String> details = new ArrayList<String>();
			for(int n=0; n<23;n++) {
				Cell c_ = row.getCell(3+n);
				if(c_ != null && !c_.getStringCellValue().isEmpty()) {
					details.add(c_.getStringCellValue());
				}
			}			
			map.put("id", c_id.toString());
			map.put("bpl_name", c_bpl_name.getStringCellValue());
			map.put("bpl_no", c_bpl_no.toString());
			map.put("details", details);
			result.add(map);
		}
		return result;
	}
	
	private List<Map> ExcelRun() throws java.io.IOException {
		try(FileInputStream file = new FileInputStream(new File(this.path, this.filename))) {
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			List<Map> result = GetList(sheet);
			for(Map map_ : result) {
				StringBuilder sb = new StringBuilder();
				List<String> details = (List<String>) map_.get("details");
				for(String s_ : details) {
					sb.append(s_).append(" ");
				}
				String text_ = sb.toString();
				Set<String> set_ = makeSet(text_);
				map_.put("set", set_);
				map_.put("source", text_);
			}
			return result;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param cnsl_idx 은 컨설팅 IDX를 의미합니다.
	 * @return 검사를 할 때 입력된 cnsl_idx에 대한 결과 중 rate 값이 가장 높은 결과를 반환합니다.  
	 */
	public Map getRecordFromIDX(String cnsl_idx) {
		Map result = this.store.get(cnsl_idx);
		return result;
	}
}