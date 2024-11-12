<%@ include file="../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="listFormId" value="fn_inputForm"/>
<c:set var="itemOrderName" value="${submitType}_order"/>
<script type="text/javascript" src="<c:out value="${contextPath}/include/js/calendar.js"/>"></script>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_authInputReset();
	
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
	
	// 승인, 반려 선택시 등록
	$("#status").change(function(){
			var reqIdx =$(".alignC > tr:eq(0)").children('td:eq(1)').text();
			var check1 = $(".alignC > tr:eq(0)").children('td:eq(8)').find('select').val();
			var auth = $(".alignC > tr:eq(0)").children('td:eq(7)').find('select').val();
			var date1 = $(".alignC > tr:eq(0)").children('td:eq(5)').find('input').val();
			var date2 = $(".alignC > tr:eq(0)").children('td:eq(6)').find('input').val();
			var apply = $("#status").val();
			
			$("#reqIdx").attr("value",reqIdx);
			$("#startDateVal").attr("value",date1);
			$("#endDateVal").attr("value",date2);
			$("#auth").attr("value",auth);
			$("#apply").attr("value",apply);
			
			
			if(check1 != ""){
				if(apply == "Y"){
					
					if(date1 == ""){
						alert("시작일 값을 입력해 주십시오.");
						return false;
					}
					if(date2 == ""){
						alert("종료일 값을 입력해 주십시오.");
						return false;
					}
					if(auth == ""){
						alert("권한그룹을 선택해 주십시오.");
						return false;
					} 
					
					$(".modal-alert").text(date1 + "부터  " + date2 + "까지\n권한이 부여됩니다.\n변경하시겠습니까?");
				} else if(apply == "N"){
					$("#auth").attr("value","0");
					$(".modal-alert").text("반려처리 하시겠습니까?");
				} else if(apply == "D"){
					$("#auth").attr("value","0");
					$(".modal-alert").text("삭제처리 하시겠습니까?");
				}
				
		        $(".mask").fadeIn(150, function() {
		            $("#modal-action01").show();
		        });
			     
			}
			
	});
	
	$(".modal-wrapper .btn-modal-close").on("click", function() {
        $(".modal-wrapper").hide();
        $(".mask").fadeOut(150);
    });
	
	// 삭제
	$(".fn_btn_delete").click(function(){
		try {
			fn_listDeleteFormSubmit("<c:out value="${listFormId}"/>", $(this).attr("href"));
		}catch(e){}
		return false;
	});
	
	// 등록/수정
	$("#<c:out value="${param.inputFormId}"/>").submit(function(){
		try {
			return fn_authInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	// 전화번호 정규식
	var preMobile = $("#mobile").text();
	var mobile = preMobile.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
	$("#mobile").text(mobile);
	
	// 달력 선택
	try{$("#fn_btn_startDate").click(function(event){displayCalendar($('#startDate'),'', this);return false;});}catch(e){}
	try{$("#fn_btn_endDate").click(function(event){displayCalendar($('#endDate'),'', this);return false;});}catch(e){}
	
	var nobutton = $(".alignC > tr:eq(0) > .apply").text();
	if(nobutton == ""){
		$("#inputButton").prop("disabled","disable");
	}
	
	 menuOn(5, 1, 1, 0);


	 
	
});

function getCenterList(){
	
	var list = new Array();
	
	
	
		
	list.push($(".alignC > tr:eq(0)").children('td:eq(0)').text());
	list.push($(".alignC > tr:eq(0)").children('td:eq(4)').find('input').val());
	list.push($(".alignC > tr:eq(0)").children('td:eq(5)').find('input').val());
	list.push($(".alignC > tr:eq(0)").children('td:eq(6)').find('select').prop('value'));
	list.push($(".alignC > tr:eq(0)").children('td:eq(7)').find('select').prop('value'));
		
		
	
	$("#centerList").attr("value",list);
	console.log(list);
}


function fn_authInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_authInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_createMaskLayer();
	return true;
}

//삭제
function fn_listDeleteFormSubmit(theFormId, theAction){
	try {
		if(!fn_isValFill(theFormId) || !fn_isValFill(theAction)) return false;
		// 선택
		if(!fn_checkElementChecked("select", "삭제")) return false;
		// 삭제여부
		var varConfirm = confirm("<spring:message code="message.select.all.delete.confirm"/>");
		if(!varConfirm) return false;
		console.log("theFormId" + theFormId + "action" + theAction);
		$("#" + theFormId).attr("action", theAction);

		$("input[name='select']").prop("disabled", false);
		$("#" + theFormId).submit();
	}catch(e){}
	
	return true;
}
</script>