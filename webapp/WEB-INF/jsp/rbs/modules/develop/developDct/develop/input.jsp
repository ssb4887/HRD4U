<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_sampleInputReset();
	

	
	$(".accordion-wrapper > button").on("click", function() {
        if ($(this).next().css("display") == "none") {
            $(".accordion-wrapper > button").removeClass('active');
            $(".accordion-wrapper .accordion-list").slideUp(150);
            $(this).next().slideDown(150);
            $(this).addClass('active');

        } else {
            $(".accordion-wrapper>button").removeClass('active');
            $(".accordion-wrapper .accordion-list").slideUp(150);
        }
    });
	
	// reset
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_reset").click(function(){
		try {
			$("#<c:out value="${param.inputFormId}"/>").reset();
			fn_sampleInputReset();
		}catch(e){alert(e); return false;}
	});
	<c:if test="${isAdmMode}">
	// cancel
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_cancel").click(function(){
		try {
			<c:choose>
				<c:when test="${param.dl == '1'}">
				self.close();
				</c:when>
				<c:otherwise>
				location.href="${elfn:replaceScriptLink(URL_LIST)}";
				</c:otherwise>
			</c:choose>
		}catch(e){return false;}
	});
	</c:if>

	// 훈련시간 readonly(교과목 프로필에서 입력한 시간의 합계)
	$(".readonly").attr("readonly", true);
	
	// placeholder 추가
	$("input.onlyNum").attr("placeholder", "숫자만 입력하세요");
	$("input.onlyNumDash").attr("placeholder", "숫자와 '-'만 입력하세요");
	
	// 숫자만 입력
	$(".onlyNum").keyup(function(event){
		if($(this).val() == "0"){
			alert("최소 1이상 입력해 주세요.");
			$(this).val("");
		}
		fn_onlyNumNoCopy(event);
	});
	
	// 전화번호 입력
	$(".onlyNumDash").keyup(function(event){
		if($(this).val().length > 13){
			$(this).val($(this).val().substr(0, 13));
		}
		fn_onlyNumDashNoCopy(event);
	});
	
	// 복사 붙여넣기 시 숫자만 입력
	$(".onlyNum").change(function(event){
		if($(this).val() == "0"){
			alert("최소 1이상 입력해 주세요.");
			$(this).val("");
		}
		fn_onlyNumNoCopy(event);
	});
	
	// 복사 붙여넣기 시 전화번호 입력
	$(".onlyNumDash").change(function(event){
		if($(this).val().length > 13){
			$(this).val($(this).val().substr(0, 13));
		}		
		fn_onlyNumDashNoCopy(event);
	});
	
	// 9999까지 입력
	$("#trDayCnt, #trTm, #clasNmpr, #clasCnt").keyup(function(event) {
		if($(this).val() > 99999){
			alert("99999이하로 입력해 주세요.");
			$(this).val(99999);
		}
	});
	
	// textarea 1000자까지 입력
// 	$("textarea").keyup(function(e) {
// 		var content = $(this).val();
		
// 		if(content.length > 1000){
// 			alert("1000자까지 입력가능합니다.");
// 			$(this).val($(this).val().substr(0, 1000));
// 		}
// 	});
	
// 	// 복사 붙여넣기할 때 1000자까지 가능
// 	$("textarea").change(function(e) {
// 		var content = $(this).val();
		
// 		if(content.length > 1000){
// 			alert("1000자까지 입력가능합니다.");
// 			$(this).val($(this).val().substr(0, 1000));
// 		}
// 	});
	
	// 검토요청
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_submit").click(function(){
		try {
			// 승인 상태에 따라 confirm창에 다른 내용이 나오도록 설정
			var confmStatus = $(this).attr("data-status");
			var statusName = "";
			if(confmStatus == "33"){
				statusName = "검토요청";
				$("#fn_developInputForm").attr("action", "${DEVELOP_CONSIDERREQUEST_URL}");

			}
			
			var varConfirm = confirm("훈련과정 표준개발 신청서를 " + statusName + "하시겠습니까?");
			if(!varConfirm) return false;
			
			return fn_developInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	// 검토요청 반려 상태에서 수정
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_modify").click(function(){
		try {
			// 승인 상태에 따라 confirm창에 다른 내용이 나오도록 설정
			var confmStatus = $(this).attr("data-status");
			var statusName = "";
			if(confmStatus == "33"){
				statusName = "검토요청";
				$("#fn_developInputForm").attr("action", "${DEVELOP_MODIFY_URL}");

			}
			
			var varConfirm = confirm("훈련과정 표준개발 신청서를 재" + statusName + "하시겠습니까?");
			if(!varConfirm) return false;
			
			return fn_developInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	var submitType = "${submitType}";
	
	var insttIdx = "${corpInfo.INSTT_IDX}"; 
	
	// 주치의 지원요청에서 선택한 훈련실시 지역에 해당하는 지부지사 setting
	if(submitType == "develop_form_column"){
		var devInsttIdx = "${devlopList.TR_OPRTN_BRFFC_CD}";
		var blockCd = "${devlopList.TR_OPRTN_GUGUN_CD}";
		$("#insttIdx option[value='" + devInsttIdx + "']:eq(0)").prop("selected", true);
		var insttName = $("#insttIdx option[value='" + devInsttIdx + "']:eq(0)").text();
		$("#insttName").text(insttName);
		// 주소 선택창 비활성화
		if(blockCd != ""){
			$("#sido1, #sido2").attr("disabled", "disabled");
		}
	}
	
});


function fn_sampleInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_developInputFormSubmit(confmStatus){
	var useFlag = "${param.useFlag}";
	
	// 과정활용일 때 작성하는 항목이 적어서 필수값 따로 체크
	if(useFlag == "use"){
	
		// 관할 지부지사&훈련실시 주소 체크
		var insttIdx = $("#insttIdx").val();
		if(insttIdx == ""){
			alert("훈련실시 주소을/를 선택해 주세요.");
			$("#sido1").focus();
			return false;
		}
		
		// 과정담당자명, 과정담당자 전화번호 체크
		var tpPicName = $("#tpPicName").val();
		if(tpPicName == ""){
			alert("과정담당자명을/를 선택해 주세요.");
			$("#tpPicName").focus();
			return false;
		}
		
		var tpPicTelNo = $("#tpPicTelNo").val();
		if(tpPicTelNo == ""){
			alert("과정담당자 전화번호을/를 선택해 주세요.");
			$("#tpPicTelNo").focus();
			return false;
		}
		
		// 학급정원, 학급수 체크
		var clasNmpr = $("#clasNmpr").val();
		if(clasNmpr == ""){
			alert("학급정원(명)을/를 선택해 주세요.");
			$("#clasNmpr").focus();
			return false;
		}
		
		var clasCnt = $("#clasCnt").val();
		if(clasCnt == ""){
			alert("학급수(개)을/를 선택해 주세요.");
			$("#clasCnt").focus();
			return false;
		}
	} else {
		// 과정수정, 신규작성일 때는 모든 항목 필수값 체크
		<itui:submitValidCustom confirmStatus="10" items="${itemInfo.items}" itemOrder="${itemInfo['develop_write_order']}" inputTypeName="write"/>
		
		// 교과프로필 필수값 체크
		var subIndex = $("#tpSubContent tbody tr").length;
		
		var subInputList = ["courseName", "cn", "time"];
		var subInputName = ["교과목명", "세부내용 단원(과제명)", "시간"];
		
		for(var j = 1; j <= subIndex; j++){
			for(var i = 0; i < subInputList.length; i++){
				if($("#" + subInputList[i] + j).length != 0){
					if(!$("#" + subInputList[i] + j).val()){
						alert("교과프로필 " + subInputName[i] + "을 입력해 주세요.");
						$("#" + subInputList[i] + j).focus();
						return false;
					}
				}
			}
		}
	}
	
	fn_createMaskLayer();
	return true;
}
</script>