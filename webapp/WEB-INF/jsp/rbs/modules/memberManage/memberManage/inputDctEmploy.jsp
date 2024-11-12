<%@ include file="../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="listFormId" value="fn_inputForm"/>
<c:set var="itemOrderName" value="${submitType}_order"/>
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
	
	// 등록버튼 클릭시 한줄 추가
	$("#employSubmit").on("click", function() {
		if($("#status").length > 0){
			console.log("들어옴");
			alert("등록된 신청건이 있습니다.");
			return false;
		}
		console.log("안 들어옴");
       	var innerHtml = "";
       	
		
		innerHtml += '<tr><td class="docIdx" style="display:none;"></td><td class="num"></td><td class="regiDate"></td><td class="apply"></td>';
		innerHtml += '<td><select id="insttIdx" name="insttIdx" style="width:150px;"><option value="">소속기관</option><option value="1">서울지역본부</option><option value="2">서울서부지사</option><option value="3">서울남부지사</option><option value="4">서울강남지사</option><option value="5">강원지사</option>';
		innerHtml += '<option value="6">강원동부지사</option><option value="7">경인지역본부</option><option value="8">인천지사</option><option value="9">경기북부지사</option><option value="10">경기동부지사</option><option value="11">경기남부지사</option><option value="12">경기서부지사</option><option value="13">부산지역본부</option><option value="14">부산남부지사</option><option value="15">경남지사</option>';	
		innerHtml += '<option value="16">울산지사</option><option value="17">경남서부지사</option><option value="18">대구지역본부</option><option value="19">경북지사</option><option value="20">경북동부지사</option><option value="21">경북서부지사</option><option value="22">광주지역본부</option><option value="23">전북지사</option><option value="24">전남지사</option><option value="25">전남서부지사</option>';	
		innerHtml += '<option value="26">제주지사</option><option value="27">전북서부지사</option><option value="28">대전지역본부</option><option value="29">충북지사</option><option value="30">충남지사</option><option value="31">세종지사</option><option value="32">충북북부지사</option><option value="33">본부</option><option value="36">원주기업인재혁신센터</option></select></td>';
		innerHtml += '<td><select id="gradeIdx" name="gradeIdx" style="width:100px;"><option value="">직급</option><option value="N">팀원</option><option value="Y">팀장</option></select></td>';
		innerHtml += '<td><select id="status" name="status" style="width:80px;" onchange="return apply();"><option value="">선택</option><option value="3">승인</option></select></td>';	
		innerHtml += '<td></td><td></td></tr>';
	
       	 $(".alignC").prepend(innerHtml);
         
    });
	
	
	
	menuOn(5, 2, 1, 0);
	
	var docIdx = $(".alignC tr").children('td:eq(0)').text();
	var preinstt = $("#insttIdx").val();
	var preinsttName = $("#preRegi").text();
	
	// 승인, 반려 선택시 등록
	$("#status").change(function(){
		
			console.log("타나");
			var instt = $("#insttIdx").val();
			var insttName = $("#insttIdx option:checked").text();
			var gradeIdx = $("#gradeIdx").val();
			var check3 = $("#status").val();
			
			
			//승인
			if(check3 == "3"){
				if(instt == ""){
					alert("소속기관을 입력해 주십시오.");
					return false;
				}
				if(gradeIdx == ""){
					alert("직급을 입력해 주십시오.");
					return false;
				}
				
				console.log(docIdx + "ss");
				$("#stat").prop("value",check3)
				$("#docIdx").prop("value",docIdx)
				$("#instt").prop("value",instt);
				$("#preInstt").prop("value",preinstt);
				$("#gradeVal").prop("value",gradeIdx);
				if(preinstt==""){
					preinsttName = $("#preRegi").text();
					$(".modal-alert").text("공단소속이 " + preinsttName + "에서 \n" + insttName + "로 변경됩니다. \n변경하시겠습니까?");
				}
				$(".modal-alert").text("공단소속이 " + preinsttName + "에서 \n" + insttName + "로 변경됩니다. \n변경하시겠습니까?");
				$(".mask").fadeIn(150, function() {
	                 $("#modal-action01").show();
	             });
			}
			//반려
			if(check3 == "0"){
				$("#stat").prop("value",check3)
				$("#docIdx").prop("value",docIdx)
				$("#instt").prop("value",instt);
				$("#preInstt").prop("value",preinstt);
				$("#gradeVal").prop("value",gradeIdx);
				
				$(".modal-alert").text("반려처리하시겠습니까?");
				$(".mask").fadeIn(150, function() {
	                 $("#modal-action01").show();
	             });
			}
			
			//삭제
			if(check3 == "D"){
				$("#stat").prop("value",check3)
				$("#docIdx").prop("value",docIdx)
				$("#instt").prop("value",instt);
				$("#preInstt").prop("value",preinstt);
				$("#gradeVal").prop("value",gradeIdx);
				
				$(".modal-alert").text("삭제처리하시겠습니까?");
				$(".mask").fadeIn(150, function() {
	                 $("#modal-action01").show();
	             });
			}
	});
	
	$("#modal-action01 .btn-modal-close").on("click", function() {
        $("#modal-action01").hide();
        $(".mask").fadeOut(150);
    });
    
    $("#modal-action01 .btn-color02").on("click", function() {
        $("#modal-action01").hide();
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
	/* $("#<c:out value="${param.inputFormId}"/>").submit(function(){
		try {
			return fn_authInputFormSubmit();
		}catch(e){alert(e); return false;}
	}); */
	
	// 등록/수정
	$("#fn_inputForm").submit(function(){
		try {
			return fn_authInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	// 달력 선택
	try{$("#fn_btn_startDate").click(function(event){displayCalendar($('#startDate'),'', this);return false;});}catch(e){}
	try{$("#fn_btn_endDate").click(function(event){displayCalendar($('#endDate'),'', this);return false;});}catch(e){}
	
	var nobutton = $(".alignC > tr:eq(0) > .apply").text();
	if(nobutton == ""){
		$("#inputButton").prop("disabled","disable");
	}
	
	// 전화번호 정규식
	var preMobile = $("#mobile").text();
	var mobile = preMobile.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
	$("#mobile").text(mobile);
});

function chk_form(){
	
	$("#<c:out value="${param.inputFormId}"/>").submit();	
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

function apply(){
	try {
		var instt = $("#insttIdx").val();
		var insttName = $("#insttIdx option:checked").text();
		var gradeIdx = $("#gradeIdx").val();
		var check3 = $("#status").val();
		
		var docIdx = $(".alignC tr").children('td:eq(0)').text();
		var preinstt = $("#insttIdx").val();
		var preinsttName = $("#preRegi").text();
		
		//승인
		if(check3 == "3"){
			if(instt == ""){
				alert("소속기관을 입력해 주십시오.");
				return false;
			}
			if(gradeIdx == ""){
				alert("직급을 입력해 주십시오.");
				return false;
			}
			
			console.log(docIdx + "ss");
			$("#stat").prop("value",check3)
			$("#docIdx").prop("value",docIdx)
			$("#instt").prop("value",instt);
			$("#preInstt").prop("value",preinstt);
			$("#gradeVal").prop("value",gradeIdx);
			if(preinstt==""){
				preinsttName = $("#preRegi").text();
				$(".modal-alert").text("공단소속이 " + preinsttName + "에서 \n" + insttName + "로 변경됩니다. \n변경하시겠습니까?");
			}
			$(".modal-alert").text("공단소속이 " + preinsttName + "에서 \n" + insttName + "로 변경됩니다. \n변경하시겠습니까?");
			$(".mask").fadeIn(150, function() {
                 $("#modal-action01").show();
             });
		}
	}catch(e){}
	
	return true;
}
</script>