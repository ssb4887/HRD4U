package rbs.egovframework.confirmProgress;

import java.util.HashMap;

import com.woowonsoft.egovframework.util.StringUtil;
/*
 * 
 *  상태코드에 대한 상태 값 추출 예시
 *  
 * modelAndView에 등록된 정적 맵을 호출, 추가하여 화면으로 보낸다
 * 					ㄴmodel.addAttribute("confirmProgressMap", ConfirmProgress.GetLabels());
 * 상태코드(예:${listDt.CONFM_STATUS})를 화면에서 해당 맵의 키로 넣는다
 * 					ㄴ ${confirmProgressMap[listDt.CONFM_STATUS]}
 * 
 * 
 * 
 * */
public enum ConfirmProgress {
	
	TEMPSAVE("5","임시 저장"),								//임시저장
	REQUEST("7","요청"),									//요청
	SUPPORTREQUEST("9","주치의지원 요청"),					//주치의지원요청
	APPLY("10","신청"),									//신청
	WITHDRAW("20","회수"), 								//회수(철수)
	SUPPORTACCEPT("28","주치의지원 접수"), 					//주치의 지원접수
	ACCEPT("30","접수"),									//접수(수락)
	CONSIDERREQUEST("33","검토요청"),						//검토요청(주치의->기업)
	RETURNREQUEST("35","반려요청"),						//반려요청
	CONSIDERRETURN("37","검토요청 반려"),					//검토요청반려(기업->주치의)	
	RETURN("40","반려"),									//반려
	SUPPORTRETURN("42","주치의지원 반려"),					//주치의 지원반려
	FIRSTAPPROVE("50","1차 승인"),							//1차 승인(1차 수락)
	APPROVEWITHOUTMODIFY("53","최종승인(변경 반려)"),		//최종승인
	APPROVE("55","최종 승인"),								//최종승인
	MODIFYREQUEST("70","변경 요청"),						//변경요청(수정요청)	
	MODIFYAPPROVE("72","변경 승인"),						//변경승인(수정승인)	
	MODIFYRETURN("73","변경 요청 반려"),						//변경요청 반려(수정요청 반려)	
	DROPREQUEST("75","중도 포기 요청"),						//중도포기요청(포기요청)
	DROP("80","중도 포기 ");								//중도포기
	
	
	
	
	
	private final String code;
	private final String description;	
	private static HashMap<String, String> map = new HashMap<String,String>();
	static {
				
				
		for(ConfirmProgress confirmstatus : ConfirmProgress.values()) {
			map.put(confirmstatus.getCode(), confirmstatus.getDescription());
		}
		
		
	}
		
	
	ConfirmProgress(String code, String description){
		this.code = code;
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static HashMap<String,String> GetLabels() {
		return map;
	}


}
