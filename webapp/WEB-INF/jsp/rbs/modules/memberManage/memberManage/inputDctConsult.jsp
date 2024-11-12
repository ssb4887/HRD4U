<%@ include file="../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="inputFormId1" value="fn_InputForm1"/>
<c:set var="inputFormId2" value="fn_inputForm2"/>
<c:set var="inputFormId3" value="fn_inputForm3"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<script type="text/javascript">
$(function(){
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_authInputReset();
	
	
	/* ============================= form1 ============================= */
	// reset
	$("#<c:out value="${inputFormId1}"/> .fn_btn_reset").click(function(){
		try {
			$("#<c:out value="${param.inputFormId}"/>").reset();
			fn_authInputReset();
		}catch(e){alert(e); return false;}
	});
	<c:if test="${isAdmMode}">
	// cancel
	$("#<c:out value="${inputFormId1}"/> .fn_btn_cancel").click(function(){
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
	$(".status").change(function(){
			var applyVal = $(this).val();
			var hiddenVal = $(this).prev().val();
			var hiddenVal = $(this).prev().val();
			var insttVal = $("#INSTT_IDX").text();
			var preInsttVal = $("#PRE_INSTT_IDX").text();
			
			$("#statusVal").prop("value", applyVal);
			$("#PSITN").prop("value", hiddenVal);
			
			if(applyVal != ""){
				if(confirm('소속이 "' + preInsttVal + '"에서 "' + insttVal + '"로 변경됩니다.\n변경하시겠습니까?')){
					$("#<c:out value="${inputFormId1}"/>").submit();	
				}
				
			}
	});
	
	var regionVal = $("#region").val();
	
	console.log(regionVal);
	
	menuOn(5, 2, 1, 0);
	$(".open-modal01").on("click", function() {
        $(".modal-wrapper").hide();
        $(".mask").fadeIn(150, function() {
            $("#modal-alert01").show();
        });
    });

    $(".open-modal02").on("click", function() {
        $(".modal-wrapper").hide();
        $(".mask").fadeIn(150, function() {
            $("#modal-alert02").show();
        });
    });

    $(".open-modal03").on("click", function() {
        $(".modal-wrapper").hide();
        $(".mask").fadeIn(150, function() {
            $("#modal-member").show();
        });
    });

    $(".modal-close").on("click", function() {
        $(".modal-wrapper").hide();
        $(".mask").hide();
    });

	
	// 등록/수정
	$("#<c:out value="${inputFormId1}"/>").submit(function(){
		try {
			console.log("ddd");
			return fn_authInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	/* ============================= form2 ============================= */
	// reset
	$("#<c:out value="${inputFormId2}"/> .fn_btn_reset").click(function(){
		try {
			$("#<c:out value="${inputFormId2}"/>").reset();
			fn_authInputReset();
		}catch(e){alert(e); return false;}
	});
	<c:if test="${isAdmMode}">
	// cancel
	$("#<c:out value="${inputFormId2}"/> .fn_btn_cancel").click(function(){
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
	$("#<c:out value="${inputFormId2}"/>").submit(function(){
		try {
			console.log("ddd");
			return fn_authInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	
	/* ============================= form3 ============================= */
	// reset
	$("#<c:out value="${inputFormId3}"/> .fn_btn_reset").click(function(){
		try {
			$("#<c:out value="${inputFormId3}"/>").reset();
			fn_authInputReset();
		}catch(e){alert(e); return false;}
	});
	<c:if test="${isAdmMode}">
	// cancel
	$("#<c:out value="${inputFormId3}"/> .fn_btn_cancel").click(function(){
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
	$("#<c:out value="${inputFormId3}"/>").submit(function(){
		try {
			console.log("ddd");
			return fn_authInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
					
	// 전화번호 정규식
	var preMobile = $("#mobile").text();
	var mobile = preMobile.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
	$("#mobile").text(mobile);

	
});


function fn_authInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_authInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_createMaskLayer();
	return true;
}
</script>