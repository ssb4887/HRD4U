<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
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
	// 등록/수정
	$("#<c:out value="${param.inputFormId}"/> .fn_btn_submit").click(function(){
		try {
			return fn_requestInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	var planIdx = $("#planIdx").val();
	var resltIdx = $("#resltIdx").val();
	var bplNo = $("#bplNo").val();
	
	// 활동일지 찾기 모달창 열기
	$("[id^='open-modal']").on("click", function() {
		var modalIndex = $(this).attr("data-idx");
		// 훈련계획서에서 신청했는지 여부 체크
		var isPlan = $("#reqstYn" + modalIndex).attr("data-isPlan");
		if(isPlan == "0"){
			alert("훈련계획서에서 신청하지 않은 항목입니다. 활동일지가 존재하지 않습니다.");
			return false;
		}
		
		var activityList = $("#activity" + modalIndex).val();
		if(activityList == "0"){
			alert("활동일지에 대한 지원금 신청은 최초 한번만 가능합니다. 해당 항목은 더이상 지원금 신청을 할 수 없습니다.");
			return false;
		}
		
		var modalNum = $(this).attr("data-idx");
        $(".mask" + modalNum).fadeIn(150, function() {
        	$("#modal-action0" + modalNum).show();
        });
        
        // 기존에 선택한 활동일지 체크표시하기
        $(this).parent().find("span").find("input[name^='acmsltIdx']").each(function() {
        	var acmsltIdx = $(this).val();
        	$("[id^='check0" + modalNum + "'][value='" + acmsltIdx + "']").prop("checked", true);
        });
    });
	
	// 모달창에서 전체 선택 해제 하기
	$("[id^='modal-action'] #selectAll").change(function(){
		try {
			var selectCode = $(this).attr("data-code");
			$("input[name='select'][data-code='" + selectCode + "']").prop("checked", $(this).prop("checked"));
		}catch(e){}
	});
	
	// 모달창에서 활동일지 선택하기
	$("[id^='modal-action'] .fn-search-submit").on("click", function() {
    	var innerHtml = "";
    	var totalPay = 0;
    	var modalNum = $(this).attr("data-idx");
    	var sportItemCd = "";
    	var itemName = "";
    	switch (modalNum){
    	case "3":
    		sportItemCd = "04";
    		itemName = "HRD 역량개발";
    		break;
    	case "4":
    		sportItemCd = "08";
    		itemName = "판로개척/인력채용";
    		break;
    	case "5":
    		sportItemCd = "09";
    		itemName = "HRD 성과교류";
    		break;
    	case "6":
    		sportItemCd = "10";
    		itemName = "훈련과정 자체개발";
    		break;
    	}
    	$("input[id^='check0" + modalNum + "']:checked").each(function (){
    		var acmsltIdx = $(this).val();
    		var maxIdx = Number($("#maxIdx").val());
    		var acexp = $(this).attr("data-acexp");
    		var sptPay = $(this).attr("data-pay");
    		$("#maxIdx").val(maxIdx + 1);
    		totalPay += Number(sptPay);
    		innerHtml += '<span style="display:block;"><strong class="point-color01"><a href="${contextPath}/${crtSiteId}/clinicDct/activity_view_form.do?mId=68&acmsltIdx=' + acmsltIdx + '&bplNo=' + bplNo + '" target="_blank">' + itemName + ' 활동일지</a></strong></span>' +
    					'<input type="hidden" name="acmsltIdx' + (maxIdx + 1) + '" value="' + acmsltIdx + '">' +
    					'<input type="hidden" name="sportItemCd' + (maxIdx + 1) + '" value="' + sportItemCd + '">' +
    					'<input type="hidden" name="sptPay' + (maxIdx + 1) + '" id="sptPay' + (maxIdx + 1) + '" value="' + sptPay + '">' +
    					'<input type="hidden" name="acexpYn' + (maxIdx + 1) + '" value="' + acexp + '">';
    	});
    	
    	// 선택한 활동일지의 총 금액이 신청가능금액보다 작은지 체크
    	var possiblePay = Number($("#possiblePay" + modalNum).attr("data-posblPay"));
    	if(possiblePay >= totalPay){
    		$("#pay" + modalNum).text(Number(totalPay).toLocaleString("ko-KR"));
    	} else {
    		alert("신청가능한 금액을 초과했습니다. 신청가능한 금액은 최대 " + possiblePay + "원입니다.");
    		return false;
    	}
    	
    	$("#actUrl" + modalNum).text("");
    	$("#actUrl" + modalNum).append(innerHtml);
    	
    	alert(itemName + " 활동일지가 선택되었습니다.");
    	$("#reqstYn" + modalNum).prop("checked", true);
    	
    	// 지원금액 합계 구하기
    	fn_sum_sptPay();
    	
    	$("#modal-action0" + modalNum).hide();
        $(".mask" + modalNum).fadeOut(150);
	})
	
   	// 모달창 닫기
    $("[id^='modal-action'] .btn-modal-close").on("click", function() {
    	var modalNum = $(this).attr("data-idx");
    	if($("input[id^='check0" + modalNum + "']:checked").length == 0){
    		$("#actUrl" + modalNum).text("");
    		$("#reqstYn" + modalNum).prop("checked", false);
    	}
    	
    	$("input[id^='check0" + modalNum + "']").prop("checked", false);
        $("#modal-action0" + modalNum).hide();
        $(".mask" + modalNum).fadeOut(150);
    });
	
	// 은행 선택에 따라 hidden에 은행 이름 setting(bankNm)
	$("#bankCd").change(function() {
		var bankNm = $("#bankCd option:selected").text();
		$("#bankNm").val(bankNm);
	});
	
	// 클리닉 성과보고 최종승인 여부
	var clinicResult = "${isResultApprove}";
	
	// 훈련계획서에서 신청하지 않은 지원항목은 지원금 신청 막기
	$("[id^='reqstYn']").click(function() {
		var index = $(this).attr("id");
		index = index.slice(-1);
		// 신청가능금액이 0인 지원항목 선택 막기
		var posblPay = $("#possiblePay" + index).attr("data-posblPay");
		if(posblPay == "0"){
			alert("해당 항목은 올해의 지원금이 모두 지급되었습니다. 지원금을 신청할 수 없습니다.");
			return false;
		}
		
		var activityList = "";
		if(index == "1"){
			// 훈련계획서 지원금 신청을 이미 했는지 확인
			activityList = $("#activity" + index).val();
			if(activityList == "0"){
				alert("훈련계획서 지원금 신청은 최초 한번만 가능합니다. 더이상 지원금 신청을 할 수 없습니다.");
				return false;
			}
			// 훈련계획 수립
			if($(this).is(":checked")){
				var maxIdx = Number($("#maxIdx").val());
	    		$("#maxIdx").val(maxIdx + 1);
				var innerHtml = '<a href="${contextPath}/${crtSiteId}/clinicDct/plan_view_form.do?mId=67&planIdx=' + planIdx + '&bplNo=' + bplNo + '" target="_blank"><strong class="point-color01">훈련계획서</strong></a>' +
								'<input type="hidden" name="acmsltIdx' + (maxIdx + 1) + '" value="' + planIdx + '">' +
								'<input type="hidden" name="sportItemCd' + (maxIdx + 1) + '" value="01">' +
								'<input type="hidden" name="sptPay' + (maxIdx + 1) + '" id="sptPay' + (maxIdx + 1) + '" value="500000">' +
								'<input type="hidden" name="acexpYn' + (maxIdx + 1) + '" value="N">';
				
				$("#actUrl1").append(innerHtml);				
				$("#pay1").text("500,000");
			} else {
				$("#actUrl1").text("");
				$("#pay1").text("");
			}
		} else if(index == "2"){
			// 성과보고서 지원금 신청을 이미 했는지 확인
			activityList = $("#activity" + index).val();
			if(activityList == "0"){
				alert("성과보고서 지원금 신청은 최초 한번만 가능합니다. 더이상 지원금 신청을 할 수 없습니다.");
				return false;
			}
			// 클리닉 성과보고
			if(clinicResult != "0"){
				if($(this).is(":checked")){
					var maxIdx = Number($("#maxIdx").val());
		    		$("#maxIdx").val(maxIdx + 1);
					var innerHtml = '<a href="${contextPath}/${crtSiteId}/clinicDct/result_view_form.do?mId=87&relstIdx=' + resltIdx + '&bplNo=' + bplNo + '" target="_blank"><strong class="point-color01">성과보고서</strong></a>' +
									'<input type="hidden" name="acmsltIdx' + (maxIdx + 1) + '" value="' + resltIdx + '">' +
									'<input type="hidden" name="sportItemCd' + (maxIdx + 1) + '" value="05">' +
									'<input type="hidden" name="sptPay' + (maxIdx + 1) + '" id="sptPay' + (maxIdx + 1) + '" value="1000000">' +
									'<input type="hidden" name="acexpYn' + (maxIdx + 1) + '" value="N">';
					
					$("#actUrl2").append(innerHtml);				
					$("#pay2").text("1,000,000");
				} else {
					$("#actUrl2").text("");
					$("#pay2").text("");
				}
			} else {
				alert("클리닉 성과보고서가 최종승인되지 않았습니다. 성과보고서 최종승인을 받고 신청해 주세요.");
				return false;
			}
		} else {
			// 나머지 활동일지
			var isPlan = $(this).attr("data-isPlan");
			if(isPlan == "0"){
				alert("훈련계획서에서 신청하지 않은 항목입니다. 지원금을 신청할 수 없습니다.");
				return false;
			} else {
				// 항목별 모든 활동일지의 지원금 신청을 이미 했는지 확인
				activityList = $("#activity" + index).val();
				if(activityList == "0"){
					alert("활동일지에 대한 지원금 신청은 최초 한번만 가능합니다. 해당 항목은 더이상 지원금 신청을 할 수 없습니다.");
					return false;
				}
				if(!$(this).is(":checked")){
					var varConfirm = confirm("지원금 신청여부를 체크 해제하면 선택한 증빙서류가 모두 삭제됩니다. 체크 해제하시겠습니까?");
					if(!varConfirm) return false;
					
					$("#actUrl" + index).text("");
					$("#pay" + index).text("");
				} 
			}
		}
	});
	
	// 체크박스가 변경될 때마다 합계지원금액 새로 계산하기
	$("[id^='reqstYn']").change(function() {
		// 지원금액 합계 구하기
		fn_sum_sptPay();
	});
	
	// placeholder 추가
	$("input.onlyNum").attr("placeholder", "숫자만 입력하세요");
	
	// 숫자만 입력
	$(".onlyNum").keyup(function(event){
		fn_onlyNumNoCopy(event);
	});
   	
 	// 복사 붙여넣기 시 숫자만 입력
	$(".onlyNum").change(function(event){
		fn_onlyNumNoCopy(event);
	});
});

function fn_sampleInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_requestInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	
	// 지원금 신청여부가 하나라도 체크되어 있는지 확인
	var checkCount = 0;
	// 지원금 신청여부가 체크되어있을 때 증빙서류가 있는지 확인
	var isActUrl = true;
	var itemName = "";
	$("[id^='reqstYn']").each(function() {
		if($(this).is(":checked")){
			var varId = $(this).attr("id");
			var index = varId.slice(-1);
			checkCount += 1;
			
			if(index > 2){
				var actUrl = $("#actUrl" + index).text();
				
				if(actUrl == ""){
			    	switch (index){
			    	case "3":
			    		itemName = "HRD 역량개발";
			    		break;
			    	case "4":
			    		itemName = "판로개척/인력채용";
			    		break;
			    	case "5":
			    		itemName = "HRD 성과교류";
			    		break;
			    	case "6":
			    		itemName = "훈련과정 자체개발";
			    		break;
			    	}
			    	
			    	isActUrl = false;
			    	return false;
				}
			}
		}
	});
	
	if(!isActUrl){
		alert(itemName + "의 증빙서류를 추가해 주세요.");
		return false;
	}
	if(checkCount == 0){
		alert("지원금을 신청할 항목을 선택하고 증빙서류를 선택해 주세요.\r훈련계획 수립과 클리닉 성과보고는 증빙서류를 선택하지 않아도 됩니다.");
		return false;
	}
	
	fn_createMaskLayer();
	return true;
}

function fn_sum_sptPay(){
	var totalPay = 0;
	$("[id^='reqstYn']").each(function() {
		if($(this).is(":checked")){
			var varId = $(this).attr("id");
			var index = varId.slice(-1);
			var sptPay = $("#pay" + index).text();
			sptPay = Number(sptPay.replace(/,/g, ''));
			totalPay += sptPay;
		}
	});
	
	$("#totalPay").text(Number(totalPay).toLocaleString("ko-KR"));
	$("#totPay").val(totalPay);
}
</script>