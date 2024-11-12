<%@ include file="../../../../include/commonTop.jsp"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<script type="text/javascript">
var pageNumber = 1;
$(function(){
	$("#nowPage").text(pageNumber + " / 5");
	// 반려
	$(".btn-fn-return").click(function(){
		try {
			var varConfirm = confirm("해당 능력개발클리닉 성과보고서를 반려처리 하시겠습니까?");
			if(!varConfirm) return false;
			if($("#doctorOpinion").val() == ""){
				alert("주치의 의견을 입력 후 반려처리해 주세요.");
				pageNumber = 5;
				pageMoveControl(pageNumber);
				$("#doctorOpinion").focus();
				return false;
			}
			var returnUrl = "${RESULT_RETURN_URL}&resltIdx=${dt.RESLT_IDX}&cliIdx=${dt.CLI_IDX}";
			$("#clinic_result").attr("action", returnUrl);
		}catch(e){return false;}
		return true;
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
</script>