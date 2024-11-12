<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<script type="text/javascript" src="<c:out value="${contextPath}/include/js/calendar.js"/>"></script>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
var pageNumber = 1;
$(function(){
	$("#nowPage").text(pageNumber + " / 5");
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_sampleInputReset();
	
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
			var varConfirm = confirm("목록으로 이동하시겠습니까? \n임시저장을 하지 않으면 작성한 내용은 저장되지 않습니다.");
			if(!varConfirm) return false;
		
			location.href="${elfn:replaceScriptLink(RESULT_LIST_FORM_URL)}";
		}catch(e){return false;}
	});
	</c:if>
	
	// 등록/수정
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_submit").click(function(){
		try {
			return fn_sampleInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	// 지원유형 중 필수사항 체크해제 막기
	$("[id^='reqstYn']").click(function() {
		alert("지원유형은 훈련계획서에서 신청한 항목과 동일하여 수정할 수 없습니다.");
		return false;
	});
	
	//화면 이동 제어
	$("#goBefore").click(function(){
		pageNumber = pageNumber - 1;
		pageMoveControl(pageNumber);
		
	});
	
	$("#goNext").click(function(){
		pageNumber = pageNumber + 1;
		pageMoveControl(pageNumber);
	});
	
	$("[id^='tab']").click(function(){
		pageNumber = Number($(this).attr("id").substr(3,1));	
		pageMoveControl(pageNumber);
	});
	
	// placeholder 추가
	$("input.onlyNum").attr("placeholder", "숫자만 입력하세요");
	$("input.onlyNumDash").attr("placeholder", "숫자와 '-'만 입력하세요");
	
	// 숫자만 입력
	$(".onlyNum").keyup(function(event){
		// 정부 미지원 과정 수를 제외하고 0 입력 안됨
		if($(this).attr("id") != 'gvrnUnSport'){
			if($(this).val() == "0"){
				alert("최소 1이상 입력해 주세요.");
				$(this).val("");
			}
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
		// 정부 미지원 과정 수를 제외하고 0 입력 안됨
		if($(this).attr("id") != 'gvrnUnSport'){
			if($(this).val() == "0"){
				alert("최소 1이상 입력해 주세요.");
				$(this).val("");
			}
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
	
	// 경력 1~99까지 입력
	$("[id^='hffcCareer']").keyup(function(event) {
		if($(this).val() > 99){
			alert("경력은 100이하로 입력해 주세요.");
			$(this).val(99);
		}
	});
	
	// 만족도 조사 실시 예정자 수, 실시 예정 과정 수 9999까지 입력
	$("#gvrnSport, #gvrnUnSport, [id^='trNmpr'], [id^='trTm']").keyup(function(event) {
		if($(this).val() > 9999){
			alert("9999이하로 입력해 주세요.");
			$(this).val(9999);
		}
	});
	
	// 글자 수 채워넣기
	var hrdPicCn = $("#hrdPic").val();
	if(hrdPicCn != ""){
		$(".hrdPicCount").text(hrdPicCn.length);
	}
	
	var slftrCn = $("#slftr").val();
	if(slftrCn != ""){
		$(".slftrCount").text(slftrCn.length);
	}
	
	// HRD 담당자 역량개발계획, 자체훈련계획 1500자까지 입력 및 현재 글자 수 보여주기
	$("#hrdPic").keyup(function(e) {
		var content = $(this).val();
		if(content.length == 0 || content == ""){
			$(".hrdPicCount").text("0");
		} else {
			$(".hrdPicCount").text(content.length);
		}
		
		if(content.length > 1500){
			alert("HRD담당자 역량개발 결과은 1500자까지 입력가능합니다.");
			$(this).val($(this).val().substr(0, 1500));
		}
	});
	
	$("#slftr").keyup(function(e) {
		var content = $(this).val();
		if(content.length == 0 || content == ""){
			$(".slftrCount").text("0");
		} else {
			$(".slftrCount").text(content.length);
		}
		
		if(content.length > 1500){
			alert("자체훈련실시 결과는 1500자까지 입력가능합니다.");
			$(this).val($(this).val().substr(0, 1500));
		}
	});
	
	// 복사 붙여넣기할 때 글자수 체크
	$("#hrdPic").change(function(e) {
		var content = $(this).val();
		if(content.length == 0 || content == ""){
			$(".hrdPicCount").text("0");
		} else {
			$(".hrdPicCount").text(content.length);
		}
		
		if(content.length > 1500){
			alert("HRD담당자 역량개발 결과은 1500자까지 입력가능합니다.");
			$(this).val($(this).val().substr(0, 1500));
		}
	});
	
	$("#slftr").change(function(e) {
		var content = $(this).val();
		if(content.length == 0 || content == ""){
			$(".slftrCount").text("0");
		} else {
			$(".slftrCount").text(content.length);
		}
		
		if(content.length > 1500){
			alert("자체훈련실시 결과는 1500자까지 입력가능합니다.");
			$(this).val($(this).val().substr(0, 1500));
		}
	});
	
	// 훈련시기 1~12월까지 입력 받기
	$("[id^='trMt']").keyup(function(e) {
		var month = $(this).val();
		if(month > 12) {
			alert("1~12월 사이로 입력해 주세요.");
			$(this).val(12);
		}
	});
});

function pageMoveControl(pageNumber){
	$("#nowPage").text(pageNumber + " / 5");
	$("#1").addClass("hide");
	$("#2").addClass("hide");
	$("#3").addClass("hide");
	$("#4").addClass("hide");
	$("#5").addClass("hide");
	$("#" + pageNumber).removeClass("hide");
	
	$("[id^='tab']").removeClass("active");
	// 웹접근성 : 기존의 탭에 '선택됨' 제거하기
	$("[id^='tab']").each(function() {
		$(this).attr("title", function(i, currentTitle){
			return currentTitle.replace(' 선택됨', '');
		});
	});
	
	$("#tab" + pageNumber).addClass("active");
	// 웹접근성 : 선택된 탭에 '선택됨' 추가하기
	$("#tab" + pageNumber).attr("title", function(i, currentTitle){
		return currentTitle + " 선택됨";
	});
	
	if(pageNumber == 1){
		$("#goNext").css("display", "inline");
		$("#goBefore").css("display", "none");
	} else if(pageNumber == 5){
		$("#goNext").css("display", "none");
		$("#goBefore").css("display", "inline");
	} else {
		$("#goNext").css("display", "inline");
		$("#goBefore").css("display", "inline");
	}
}

function fn_sampleInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_sampleInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	
	// 연간훈련실시 결과 필수 값 체크
	var yearIndex = $("#yearResultSubContent tbody tr").length;

	var yearInputList = ["trMt", "trTarget", "trCn", "trMby", "trMth", "trNmpr", "trTm", "trDate", "comNmpr", "gvrnSportYn"];
	var yearInputName = ["훈련시기(월)", "훈련대상", "훈련내용", "훈련주체", "훈련방법", "훈련인원", "훈련시간", "훈련일", "수료인원", "정부지원 훈련여부"];
	
	var today = new Date();
	var year = today.getFullYear();
	
	for(var j = 1; j <= yearIndex; j++){
		for(var i = 0; i < yearInputList.length; i++){
			if(!$("#" + yearInputList[i] + j).val()){
				alert("연간 훈련실시 결과 탭에서 " + yearInputName[i] + " 항목을 입력해 주세요.");
				pageNumber = 4;
				pageMoveControl(pageNumber);
				$("#" + yearInputList[i] + j).focus();
				return false;
			}
		}
		
		// 훈련일 올해만 선택가능
		var trDate = $("#trDate" + j).val();
		if(trDate.substr(0,4) != year){
			alert("올해의 훈련만 등록할 수 있습니다.(" + year + "-01-01 ~ " + year + "-12-31) 연간 훈련실시 결과 탭에서 훈련일을 수정해 주세요.");
			pageNumber = 4;
			pageMoveControl(pageNumber);
			$("#trDate" + j).focus();
			return false;
		}
	}
	
	// 자체 KPI 이행결과 필수 값 체크
	var kpiIndex = $("#resultKpi tbody tr").length;
	
	var kpiInputList = ["kpiCn", "kpiGoal", "kpiResult"];
	var kpiInputName = ["성과지표", "목표", "결과"];
	
	for(var j = 1; j <= kpiIndex; j++){
		for(var i = 0; i < kpiInputList.length; i++){
			if(!$("#" + kpiInputList[i] + j).val()){
				alert("자체 KPI 이행결과 탭에서 " + kpiInputName[i] + " 항목을 입력해 주세요.");
				pageNumber = 5;
				pageMoveControl(pageNumber);
				$("#" + kpiInputList[i] + j).focus();
				return false;
			}
		}
	}
	
	fn_createMaskLayer();
	return true;
}
</script>