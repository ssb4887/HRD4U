package rbs.modules.basket.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import com.woowonsoft.egovframework.annotation.ModuleAttr;
import com.woowonsoft.egovframework.annotation.ModuleAuth;
import com.woowonsoft.egovframework.annotation.ModuleMapping;
import com.woowonsoft.egovframework.annotation.ParamMap;
import com.woowonsoft.egovframework.form.DataMap;
import com.woowonsoft.egovframework.form.ModuleAttrVO;
import com.woowonsoft.egovframework.form.ParamForm;
import com.woowonsoft.egovframework.resource.RbsMessageSource;
import com.woowonsoft.egovframework.tag.ui.RbsPaginationInfo;
import com.woowonsoft.egovframework.util.JSONObjectUtil;
import com.woowonsoft.egovframework.util.ModuleUtil;
import com.woowonsoft.egovframework.util.StringUtil;
import com.woowonsoft.egovframework.util.UserDetailsHelper;
import com.woowonsoft.egovframework.web.ModuleController;

import egovframework.rte.fdl.property.EgovPropertyService;
import net.sf.json.JSONObject;
import rbs.egovframework.LoginVO;
import rbs.egovframework.auth.Auth;
import rbs.egovframework.auth.Auth.Role;
import rbs.modules.basket.dto.Basket;
import rbs.modules.basket.dto.BskClAtmcDto;
import rbs.modules.basket.dto.BskClLogDto;
import rbs.modules.basket.dto.BskClPassivDto;
import rbs.modules.basket.dto.BskHashTagDto;
import rbs.modules.basket.dto.BskRecDto;
import rbs.modules.basket.dto.CMRespDto;
import rbs.modules.basket.dto.ClLclasDto;
import rbs.modules.basket.dto.ClResveDto;
import rbs.modules.basket.dto.ClSclasDto;
import rbs.modules.basket.dto.Criteria;
import rbs.modules.basket.dto.ExcelUploadDto;
import rbs.modules.basket.dto.HashTagDto;
import rbs.modules.basket.dto.PageVO;
import rbs.modules.basket.dto.TrDataDto;
import rbs.modules.basket.dto.ZipCodeDto;
import rbs.modules.basket.excel.ExcelExport;
import rbs.modules.basket.service.BasketService;
import rbs.modules.instt.service.InsttService;
import rbs.modules.member.service.LoginService;

/**
 * 샘플모듈<br/>
 * : 통합관리시스템 > 메뉴콘텐츠관리, 통합관리시스템 > 기능등록관리, 사용자 사이트 에서 사용
 * 
 * @author user
 *
 */
@Controller
@ModuleMapping(moduleId = "basket")
@RequestMapping({ "/{siteId}/basket", "/{admSiteId}/menuContents/{usrSiteId}/basket" })
public class BasketController extends ModuleController {

	@Value("${Globals.fileStorePath}")
	private String uploadDir;

	@Resource(name = "basketService")
	private BasketService basketService;

	@Resource(name = "rbsLoginService")
	private LoginService loginService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "rbsMessageSource")
	RbsMessageSource rbsMessageSource;

	@Resource(name = "jsonView")
	View jsonView;

	@Resource(name = "insttService")
	private InsttService insttService;
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(BasketController.class);

	/**
	 * 목록조회
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "LST")
	@RequestMapping(value = "/list.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String getList(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model,
			@ModelAttribute Criteria cri) throws Exception {

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		
		log.info("queryString {}", attrVO.getQueryString().size());

		// dct web 분기
		if (attrVO.getSiteId().equals("dct")) {
			if (loginVO == null) {
				request.setAttribute("msg", "로그인이 필요합니다.");
				request.setAttribute("url", "/dct/login/login.do?mId=3");
				return getViewPath("/alert");
			} else if (loginVO.getUsertypeIdx() == 40 || loginVO.getUsertypeIdx() == 30
					|| loginVO.getUsertypeIdx() == 55) {

				model.addAttribute("loginVO", loginVO);

				if (loginVO.getUsertypeIdx() == 30 && (cri.getIsInsttIdx() == null || cri.getIsInsttIdx().equals(""))) {
					String insttIdx = loginVO.getInsttIdx(); // 지부지사코드
					cri.setInsttIdx(insttIdx);
				}
						
				if(attrVO.getQueryString().size() > 2) {
				int totalCount = basketService.getTotalCount(cri);
				PageVO pageVO = new PageVO(cri, totalCount);
				List<Basket> basket = basketService.getList(cri);
				model.addAttribute("pageVO", pageVO); // 페이징
				model.addAttribute("list", basket); // 리스트

				
				//
				model.addAttribute("totalCount", totalCount);

				// 출력칼럼 리스트
				model.addAttribute("showColumn", cri.getShowColumn());
				}
				// 지부지사 리스트
				List<Object> insttList = insttService.getList(null);
				model.addAttribute("insttList", insttList); // 지부지사

				// 업종
				List<HashMap<String, Object>> industCd = basketService.getIndustCd();
				model.addAttribute("industCd", industCd);

				// 시도 데이터
				List<ZipCodeDto> zips = basketService.getZipcode();
				model.addAttribute("zips", zips); // 우편번호

				// 대분류 목록
				List<ClLclasDto> clLclasList = basketService.getClLclasList();
				model.addAttribute("clLclasList", clLclasList);

				// 엑셀 파일 리스트 정보
				int totalExcelCount = basketService.getTotalExcelCount();
				PageVO excelPageVO = new PageVO(cri, totalExcelCount);
				model.addAttribute("excelPageVO", excelPageVO);
				List<ExcelUploadDto> excelUploadList = basketService.getExcelUploadList(cri);
				model.addAttribute("excelUploadList", excelUploadList);

				return getViewPath("/list");

			} else {
				request.setAttribute("msg", "접근 권한이 없습니다.");
				request.setAttribute("url", "/dct/main/main.do?mId=1");
				return getViewPath("/alert");
			}

		} else if (attrVO.getSiteId().equals("web")) {
			if (loginVO == null) {
				request.setAttribute("msg", "로그인이 필요합니다.");
				request.setAttribute("url", "/web/login/login.do?mId=3");
				return getViewPath("/alert");
			} else if (loginVO.getUsertypeIdx() == 20) {

					// 민간센터 - 협약한 기업만 출력
					String memberIdx = loginVO.getMemberIdx();
					cri.setMemberId(memberIdx);
					int totalCount = basketService.getWebTotalCount(cri);
					PageVO pageVO = new PageVO(cri, totalCount);
					List<Basket> basket = basketService.getWebList(cri);
					model.addAttribute("pageVO", pageVO); // 페이징
					model.addAttribute("list", basket); // 리스트


				// 출력칼럼 리스트
				model.addAttribute("showColumn", cri.getShowColumn());

				// 지부지사 리스트
				List<Object> insttList = insttService.getList(null);
				model.addAttribute("insttList", insttList); // 지부지사

				// 업종
				List<HashMap<String, Object>> industCd = basketService.getIndustCd();
				model.addAttribute("industCd", industCd);

				// 시도 데이터
				List<ZipCodeDto> zips = basketService.getZipcode();
				model.addAttribute("zips", zips); // 우편번호

				// 대분류 목록
				List<ClLclasDto> clLclasList = basketService.getClLclasList();
				model.addAttribute("clLclasList", clLclasList);

				return getViewPath("/list");
			}

		}

		request.setAttribute("msg", "접근 권한이 없습니다.");
		request.setAttribute("url", "/web/main/main.do?mId=1");
		return getViewPath("/alert");
	}

	/**
	 * Ajax 로 검색된 BplNo 조회
	 */
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.POST, value = "/getBplNoList.do")
	public ModelAndView getBplNoList(HttpServletRequest request, @ModelAttribute Criteria cri, ModelMap model)
			throws Exception {

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();

		if (loginVO.getUsertypeIdx() == 30) {
			String insttIdx = loginVO.getInsttIdx(); // 지부지사코드
			cri.setInsttIdx(insttIdx);
		}

		ModelAndView mav = new ModelAndView();
		List<String> bplNoList = basketService.getBplNoList(cri);
		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("BplNoList 조회 성공").setBody(bplNoList).build();
		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;

	}

	/**
	 * 목록조회 - 분류 예약목록 및 소분류관리 목록
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "LST")
	@RequestMapping(value = "/resList.do")
	public String resList(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model,
			@ParamMap ParamForm parameterMap) throws Exception {
		boolean isAdmMode = attrVO.isAdmMode();
		int fnIdx = attrVO.getFnIdx();
		JSONObject settingInfo = attrVO.getSettingInfo();
		JSONObject crtMenu = attrVO.getCrtMenu();
		model.addAttribute("mId", parameterMap.get("mId")); // 목록

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> param = new HashMap<String, Object>();
		if (loginVO != null) {
			param.put("memberId", loginVO.getMemberId().toString());
			param.put("memberPwd", loginVO.getMemberPwd().toString());
			param.put("insttIdx", loginVO.getInsttIdx());
		}

		if (parameterMap.get("lclasCd") == null) {
			parameterMap.put("lclasCd", "1");
		}

		// 1. 페이지정보 setting
		RbsPaginationInfo paginationInfo = new RbsPaginationInfo();
		int usePaging = JSONObjectUtil.getInt(settingInfo, "use_paging");
		if (usePaging == 1) {
			int listUnit = ModuleUtil.getSettingValue(isAdmMode, "dset_list_count", crtMenu, settingInfo,
					propertiesService.getInt("DEFAULT_LIST_UNIT")); // 페이지당 목록 수
			int listMaxUnit = listUnit * propertiesService.getInt("DEFAULT_LIST_MAX_CNT"); // 최대 페이지당 목록 수
			int listUnitStep = listUnit; // 페이지당 목록 수 증가값

			int pageUnit = StringUtil.getInt(request.getParameter("lunit"));
			if (pageUnit == 0)
				pageUnit = listUnit;// JSONObjectUtil.getInt(settingInfo, "dset_list_count",
									// propertiesService.getInt("PAGE_UNIT")); // 페이지당 목록 수
			int pageSize = ModuleUtil.getSettingValue(isAdmMode, "dset_list_block", crtMenu, settingInfo,
					propertiesService.getInt("PAGE_SIZE"));

			String pageName = JSONObjectUtil.getString(settingInfo, "page_name");
			int page = StringUtil.getInt(request.getParameter(pageName), 1); // 현재 페이지 index

			paginationInfo.setUnitBeginCount(listUnit);
			paginationInfo.setUnitEndCount(listMaxUnit);
			paginationInfo.setUnitStep(listUnitStep);
			paginationInfo.setCurrentPageNo(page);
			paginationInfo.setRecordCountPerPage(pageUnit);
			paginationInfo.setPageSize(pageSize);

		}

		// 2. DB
		int totalCount = 0;
		List<?> list = null;

		// 대분류 목록
		List<ClLclasDto> clLclasList = basketService.getClLclasList();
		model.addAttribute("clLclasList", clLclasList);
		param.put("clLclasList", clLclasList);
		param.put("lclasCd", parameterMap.get("lclasCd"));
		model.addAttribute("lclasCd", parameterMap.get("lclasCd"));

		// 2.2 목록수
		totalCount = basketService.getResCount(fnIdx, param);
		paginationInfo.setTotalRecordCount(totalCount);

		if (totalCount > 0) {
			if (usePaging == 1) {
				param.put("firstIndex", paginationInfo.getFirstRecordIndex());
				param.put("lastIndex", paginationInfo.getLastRecordIndex());
				param.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
			}

			// 정렬컬럼 setting
			param.put("dsetOrderField", JSONObjectUtil.getString(settingInfo, "dset_order_field"));

			// 2.3 목록
			list = basketService.getResList(fnIdx, param);

			if (!list.isEmpty()) {
				DataMap listMap = (DataMap) list.get(0);
				String status = StringUtil.getString(listMap.get("STATUS"));
				model.addAttribute("status", status);
			}
		}

		// 3. 속성 setting
		model.addAttribute("paginationInfo", paginationInfo); // 페이징정보
		model.addAttribute("list", list); // 목록

		return getViewPath("/list");
	}

	/**
	 * 상세조회
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "VEW")
	@RequestMapping(value = "/view.do")
	public String view(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, HttpServletResponse response,
			ModelMap model, @RequestParam String bplNo, @ModelAttribute Criteria cri) throws Exception {

		// 기업 상세정보
		Basket basket = basketService.getBasketSelect(bplNo);
		model.addAttribute("basket", basket);

		// 자동 분류 정보
		BskClAtmcDto bskClAtmcs = basketService.getBskClAtmcSelect(bplNo);
		model.addAttribute("bskClAtmcs", bskClAtmcs);

		// 수동 분류 정보
		List<BskClPassivDto> bskClpassiv = basketService.getBskClPassivSelect(bplNo);
		model.addAttribute("bskClpassiv", bskClpassiv);

		// 해시 태그
		List<BskHashTagDto> hashtags = basketService.getBskHashtagSelect(bplNo);
		model.addAttribute("hashtags", hashtags);

		// 분류 이력
		List<BskClLogDto> bskClLogs = basketService.getBskClLogList(bplNo);
		model.addAttribute("bskClLogs", bskClLogs);

		// 훈련 실시 이력
		int trDatasTotal = basketService.getTrCount(bplNo);
		PageVO pageVO = new PageVO(cri, trDatasTotal);
		cri.setBplNo(bplNo);
		List<TrDataDto> trDatas = basketService.getTr(cri);
		model.addAttribute("pageVO", pageVO); // 페이징
		model.addAttribute("trDatas", trDatas);

		// 컨설팅 실시 이력

		// 채용 정보
		List<BskRecDto> bskRecList = basketService.getRecList(bplNo);
		model.addAttribute("bskRecList", bskRecList);

		// 재무 정보
		List<HashMap<String, String>> fncInfo = basketService.getBskFncInfo(basket.getBizrNo());
		model.addAttribute("fncInfo", fncInfo);
		
		log.info("fncInfo {}", fncInfo);
		// 지부지사 정보

		return getViewPath("/view");
	}

	/**
	 * 분류 예외 지정 sw.jang
	 **/

	@Auth(role = Role.C)
	@RequestMapping(method = RequestMethod.POST, value = "/addExcept.do", produces = "application/json; charset=UTF-8")
	public ModelAndView addExcept(@RequestBody String json, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String, Object> jsonData = objectMapper.readValue(json, HashMap.class);
		int result = basketService.addClException(jsonData, request.getRemoteAddr());

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("예외기업 등록 성공").setBody(result).build();

		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;
	}

	/**
	 * 소분류 목록 조회
	 */
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.POST, value = "/getClSclas.do")
	public ModelAndView getClassification(@RequestBody String json) throws Exception {

		Integer clLclasCd = Integer.parseInt(json);

		List<ClSclasDto> dto = basketService.getClSclasList(clLclasCd);

		ModelAndView mav = new ModelAndView();
		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("대분류 전체 조회 성공").setBody(dto).build();

		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;
	}

	/**
	 * 분류 선택변경, 일괄변경(수동분류)
	 */
	@Auth(role = Role.C)
	@RequestMapping(method = RequestMethod.POST, value = "/insertClPassiv.do")
	public ModelAndView modfiyClassification(@RequestBody String json, HttpServletRequest request) throws Exception {

		log.info("json{}", json);

		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		List<BskClPassivDto> dtos = objectMapper.readValue(json, new TypeReference<List<BskClPassivDto>>() {
		});

		basketService.bskClPassivModify(dtos, request.getRemoteAddr());

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("수동 분류 성공").setBody(1).build();

		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;
	}

	/**
	 * 지부 지사별 해시태그 리턴
	 */
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.POST, value = "/getHashTag.do")
	public ModelAndView getHashTag(@RequestBody String selectedBranch, HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView();
		HashMap<String, Object> param = new HashMap<>();
		int insttIdx = Integer.parseInt(selectedBranch);
		param.put("insttIdx", insttIdx);

		List<HashTagDto> dtos = basketService.getHashTag(param);
		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("해시태그 조회 성공").setBody(dtos).build();

		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;
	}

	/**
	 * 시도별 구군 리턴
	 */
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.POST, value = "/getSigngu.do")
	public ModelAndView getSigngu(@RequestBody String selectedCtprvn, HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView();
		List<ZipCodeDto> dtos = basketService.getSigngu(selectedCtprvn);

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("구/군 조회 성공").setBody(dtos).build();

		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;
	}

	/**
	 * 해시태그 선택 할당
	 */
	@Auth(role = Role.C)
	@RequestMapping(method = RequestMethod.POST, value = "/assignHashTag.do")
	public ModelAndView assignHashTag(@RequestBody String json, HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		List<BskHashTagDto> dtos = objectMapper.readValue(json, new TypeReference<List<BskHashTagDto>>() {
		});

		// 해시태그 등록
		basketService.assignHashTag(dtos, request.getRemoteAddr());

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("해시태그 선택 할당").setBody(1).build();

		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;
	}

	/**
	 * 해시태그 할당 삭제
	 */

	@Auth(role = Role.D)
	@RequestMapping(method = RequestMethod.POST, value = "/deleteHashtag.do")
	public ModelAndView deleteHashtag(@RequestBody String hashtagIdx, HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		BskHashTagDto dto = objectMapper.readValue(hashtagIdx, BskHashTagDto.class);

		// 해시태그 등록
		basketService.deleteHashtag(dto, request.getRemoteAddr());

		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("해시태그  할당 삭제 성공").setBody(1).build();

		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);
		return mav;
	}

	/**
	 * 엑셀 파일 업로드
	 */

	@Auth(role = Role.C)
	@RequestMapping(method = RequestMethod.POST, value = "/excelUpload.do")
	public ModelAndView excelupload(@RequestParam("excelfile") MultipartFile file,
			@RequestParam("createdAt") String createdAt, @RequestParam("createdBy") String createdBy,
			HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();

		String uploadFolder = uploadDir; // globals.properties의 uploadPath

		// 1. 업로드 파일 저장
		String fileRealName = file.getOriginalFilename();
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddHHmmss");
		String uniqueName = dateFormat.format(currentDate);
		
		if(fileRealName.toLowerCase() != null) {
			if(fileRealName.toLowerCase().endsWith(".xlsx")) {
				

				File saveFile = new File(uploadFolder + uniqueName + "_" + fileRealName);
				try {
					
					file.transferTo(saveFile); // 실제 파일 저장 메서드
				} catch (IllegalStateException e) {
					log.info("ERROR : Exception", e.getMessage());
				} catch (IOException e) {
					log.info("ERROR : Exception", e.getMessage());
				}
			}else {
				throw new IllegalStateException();
			}
		}

	

		// 2. 업로드 파일 정보 DB 저장
		ExcelUploadDto dto = new ExcelUploadDto();
		dto.setUploadFilePath(uploadFolder);
		dto.setUploadFileName(uniqueName + "_" + fileRealName);
		basketService.bskDataFileUpload(dto, request.getRemoteAddr());

		Integer fleIdx = dto.getFleIdx();

		// 3. 엑셀 내용 DB에 저장 & 결과 엑셀 생성
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(file.getInputStream());
		} catch (InvalidFormatException e1) {
			log.info("ERROR : Exception", e1.getMessage());
		} catch (IOException e) {
			log.info("ERROR : Exception", e.getMessage());
		}

		try {
			Sheet sheet = workbook.getSheetAt(0);
			if(sheet != null) {
				Row headerRow = sheet.getRow(0);
				String[] column = { "bplNm", "bplNo", "excentYn", "besthrdYn", "wrdclsYn", "idscpxYn", "indacmtYn", "wgdlyYn",
						"mergeYn" };
				Cell errorCell = headerRow.createCell(headerRow.getLastCellNum());

				errorCell.setCellValue("오류 메시지");

				Iterator<Row> rowIterator = sheet.iterator();


				DataFormatter dataFormatter = new DataFormatter();

				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					HashMap<String, Object> map = new HashMap<>();
					map.put("fleIdx", fleIdx);
					map.put("regiIp", request.getRemoteAddr());
					for (int i = 0; i < row.getLastCellNum(); i++) {
						map.put(column[i], dataFormatter.formatCellValue(row.getCell(i)));
					}

					try {
						if (map.get("mergeYn").equals("Y")) {
							basketService.bskDataUploadMerge(map);
						} else {
							basketService.bskDataUpload(map);
						}
					} catch (Exception e) {
						row.createCell(row.getLastCellNum()).setCellValue(e.getMessage());
					}

				}
				}

				// 결과 파일 저장
				String resultFilePath = uploadFolder + uniqueName + "_Result_" + fileRealName;
				try (FileOutputStream outputStream = new FileOutputStream(resultFilePath)) {
					workbook.write(outputStream);
				} catch (FileNotFoundException e) {
					log.info("ERROR : Exception", e.getMessage());
				} catch (IOException e) {
					log.info("ERROR : Exception", e.getMessage());
				}

				// 결과 파일 정보 DB 저장
				dto.setResultFileName(uniqueName + "_Result_" + fileRealName);
				dto.setResultFilePath(uploadFolder);
				basketService.bskDataFileResultUpdate(dto);

		}catch (NullPointerException e) {
			log.info("ERROR : Exception", e.getMessage());
		}
		
		
		CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("파일업로드 성공").setBody(1).build();

		mav.setView(jsonView);
		mav.addObject("result", cmRespDto);

		return mav;
	}

	/**
	 * 엑셀 파일 다운로드
	 */
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.GET, value = "/downloadExcel.do")
	public void downloadExcel(@RequestParam String fileName, HttpServletResponse response) throws Exception {

		try {
			String filePath = uploadDir + fileName;

			FileInputStream fileInputStream = new FileInputStream(filePath);

			response.setContentType("ms-vnd/excel");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
			ByteStreams.copy(fileInputStream, response.getOutputStream());
			response.flushBuffer();
			fileInputStream.close();
		} catch (Exception e) {
			log.info("ERROR : Exception", e.getMessage());
			throw new Exception("download error");
		}

	}

	/**
	 * 분류관리, 분류예약 조회 mj.kim
	 */
	@Auth(role = Role.R)
	@RequestMapping(method = RequestMethod.GET, value = "/{path}Get.do")
	public ModelAndView getClassify(@RequestParam Map<String, Object> param, @PathVariable("path") String path)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		if (path.equals("res")) {
			DataMap dataMap = basketService.getResevCl(param);
			log.info("dataMap{}", dataMap);
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("대분류,소분류 전체 조회 성공").setBody(dataMap).build();
			mav.setView(jsonView);
			mav.addObject("result", cmRespDto);
		} else if (path.equals("class")) {
			DataMap dataMap = basketService.getClassify(param);
			log.info("dataMap{}", dataMap);
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("소분류 상세조회 성공").setBody(dataMap).build();
			mav.setView(jsonView);
			mav.addObject("result", cmRespDto);
		} else if (path.equals("hash")) {
			DataMap dataMap = basketService.getHashTag(param);
			log.info("dataMap{}", dataMap);
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("해시태그 상세 조회 성공").setBody(dataMap).build();
			mav.setView(jsonView);
			mav.addObject("result", cmRespDto);
		}

		return mav;
	}

	/**
	 * 분류관리, 분류예약, 해시태그 처리 mj.kim
	 */
	@Auth(role = Role.C)
	@RequestMapping(method = RequestMethod.POST, value = "/{path}Proc.do")
	public ModelAndView procReserve(@PathVariable("path") String path, @RequestBody String json,
			HttpServletRequest request) throws Exception {

		log.info("json {}", json);
		ModelAndView mav = new ModelAndView();
		ObjectMapper objectMapper = new ObjectMapper();
		int result = 0;
		int getInsttIdx = 0;
		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();
		Map<String, Object> param = new HashMap<String, Object>();
		if (loginVO != null) {
			param.put("memberId", loginVO.getMemberId().toString());
			param.put("memberPwd", loginVO.getMemberPwd().toString());
			param.put("insttIdx", loginVO.getInsttIdx());

		ClResveDto dto = new ClResveDto();
		ClSclasDto dso = new ClSclasDto();
		HashTagDto hto = new HashTagDto();

		if (path.equals("resDel") || path.equals("resEdit") || path.equals("resAdd")) {
			dto = objectMapper.readValue(json, new TypeReference<ClResveDto>() {
			});
			if (path.equals("resAdd")) {
				dto.setRegiId(loginVO.getMemberId());
				dto.setRegiIdx(loginVO.getMemberIdx());
				dto.setRegiName(loginVO.getMemberName());
				dto.setRegiIp(request.getRemoteAddr());
			} else {
				dto.setLastModiId(loginVO.getMemberId());
				dto.setLastModiIdx(loginVO.getMemberIdx());
				dto.setLastModiName(loginVO.getMemberName());
				dto.setLastModiIp(request.getRemoteAddr());
			}
		} else if (path.equals("hashAdd") || path.equals("hashDel") || path.equals("hashEdit")) {
			hto = objectMapper.readValue(json, new TypeReference<HashTagDto>() {
			});
			if (path.equals("hashAdd")) {
				hto.setRegiId(loginVO.getMemberId());
				hto.setRegiIdx(loginVO.getMemberIdx());
				hto.setRegiName(loginVO.getMemberName());
				hto.setRegiIp(request.getRemoteAddr());
			} else {
				hto.setLastModiId(loginVO.getMemberId());
				hto.setLastModiIdx(loginVO.getMemberIdx());
				hto.setLastModiName(loginVO.getMemberName());
				hto.setLastModiIp(request.getRemoteAddr());
			}
			hto.setInsttIdx(getInsttIdx);
		} else {
			dso = objectMapper.readValue(json, new TypeReference<ClSclasDto>() {
			});
			dso.setLastModiId(loginVO.getMemberId());
			dso.setLastModiIdx(loginVO.getMemberIdx());
			dso.setLastModiName(loginVO.getMemberName());
			dso.setLastModiIp(request.getRemoteAddr());
		}

		if (path.equals("resEdit")) {
			basketService.editResve(dto);
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("분류예약수정 성공").setBody(result).build();
			mav.setView(jsonView);
			mav.addObject("result", cmRespDto);
		} else if (path.equals("resDel")) {
			basketService.delResve(dto);
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("분류예약삭제 성공").setBody(result).build();
			mav.setView(jsonView);
			mav.addObject("result", cmRespDto);
		} else if (path.equals("resAdd")) {
			basketService.addResve(dto);
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("분류예약성공").setBody(result).build();
			mav.setView(jsonView);
			mav.addObject("result", cmRespDto);
		} else if (path.equals("editClass")) {
			basketService.editClass(dso);
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("소분류 수정 성공").setBody(result).build();
			mav.setView(jsonView);
			mav.addObject("result", cmRespDto);
		} else if (path.equals("delClass")) {
			basketService.delClass(dso);
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("소분류 삭제 성공").setBody(result).build();
			mav.setView(jsonView);
			mav.addObject("result", cmRespDto);
		} else if (path.equals("hashAdd")) {
			basketService.addHash(hto);
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("해시태그등록 성공").setBody(result).build();
			mav.setView(jsonView);
			mav.addObject("result", cmRespDto);
		} else if (path.equals("hashDel")) {
			basketService.delHash(hto);
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("해시태그삭제 성공").setBody(result).build();
			mav.setView(jsonView);
			mav.addObject("result", cmRespDto);
		} else if (path.equals("hashEdit")) {
			basketService.editHash(hto);
			CMRespDto cmRespDto = new CMRespDto.Builder(1).setMsg("해시태그수정 성공").setBody(result).build();
			mav.setView(jsonView);
			mav.addObject("result", cmRespDto);
		}
	}

		return mav;
	}

	/**
	 * 엑셀 다운로드
	 * 
	 * @param attrVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Auth(role = Role.R)
	@ModuleAuth(name = "VEW")
	@RequestMapping(value = "/excel.do")
	public String excel(@ModuleAttr ModuleAttrVO attrVO, HttpServletRequest request, ModelMap model,
			HttpServletResponse response, @ModelAttribute Criteria cri) throws Exception {

		LoginVO loginVO = (LoginVO) UserDetailsHelper.getAuthenticatedUser();

		int totalBskCount = Integer.parseInt(request.getParameter("totalCount"));

		if (totalBskCount > 300000) {
			request.setAttribute("msg", "30만건 이상의 데이터는 다운로드 할수 없습니다.");
			request.setAttribute("url", "/dct/basket/list.do?mId=42");
			return getViewPath("/alert");
		}

		// dct web 분기
		if (attrVO.getSiteId().equals("dct")) {
			
			
			if (loginVO == null) {
				request.setAttribute("msg", "로그인이 필요합니다.");
				request.setAttribute("url", "/dct/login/login.do?mId=3");
				return getViewPath("/alert");
			} else if (loginVO.getUsertypeIdx() == 40 || loginVO.getUsertypeIdx() == 30
					|| loginVO.getUsertypeIdx() == 55) {

				model.addAttribute("loginVO", loginVO);

				if (loginVO.getUsertypeIdx() == 30) {
					String insttIdx = loginVO.getInsttIdx(); // 지부지사코드
					cri.setInsttIdx(insttIdx);
				}

				// 기업 데이터 조회
				List<Basket> basket = basketService.getAllList(cri);
				// 데이터 엑셀 생성 & 다운로드
				ExcelExport excelExport = new ExcelExport<>(basket, cri.getShowColumn(), Basket.class);
				excelExport.write(response, "기업바구니검색결과");

			} else {
				request.setAttribute("msg", "접근 권한이 없습니다.");
				request.setAttribute("url", "/dct/main/main.do?mId=1");
				return getViewPath("/alert");
			}

		} else if (attrVO.getSiteId().equals("web")) {
		
			
			if (loginVO == null) {
				request.setAttribute("msg", "로그인이 필요합니다.");
				request.setAttribute("url", "/web/login/login.do?mId=3");
				return getViewPath("/alert");
			} else if (loginVO.getUsertypeIdx() == 20 ) {
					// 민간센터 - 협약한 기업만 출력
					String memberId = loginVO.getMemberId();
					cri.setMemberId(memberId);
					List<Basket> basket = basketService.getWebList(cri);
					ExcelExport excelExport = new ExcelExport<>(basket, cri.getShowColumn(), Basket.class);
					excelExport.write(response, "기업바구니검색결과");

			}
			request.setAttribute("msg", "접근 권한이 없습니다.");
			request.setAttribute("url", "/web/main/main.do?mId=1");
			return getViewPath("/alert");
		}
		return null;

	}

}
