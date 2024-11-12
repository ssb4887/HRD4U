<%@ include file="../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
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
	
	// 전화번호 정규식
	var preMobile = $("#mobile").text();
	var mobile = preMobile.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3");
	$("#mobile").text(mobile);
	
	// 등록/수정
	$("#<c:out value="${param.inputFormId}"/>").submit(function(){
		try {
			var check = $("#insttIdx").val();
			if(check == ""){
				alert("소속기관을 선택해 주십시오.");
				return false;
			}
			return fn_authInputFormSubmit();
		}catch(e){alert(e); return false;}
	});
	
	menuOn(5, 2, 1, 0);
	
	 $(function() {
         $("#open-modal01").on("click", function() {
             $(".mask").fadeIn(150, function() {
                 $("#modal-action01").show();
             });
         });

         $("#modal-action01 .btn-modal-close").on("click", function() {
             $("#modal-action01").hide();
             $(".mask").fadeOut(150);
         });
         
         $("#modal-action01 .btn-m02 round01 btn-color02").on("click", function() {
             $("#modal-action01").hide();
             $(".mask").fadeOut(150);
         });
         
     });
	 
       $("#movePage").click(function(){
  		 var result = confirm("회원정보를 수정할려면 4u페이지에서 수정을 하셔야합니다. 4u페이지로 이동하시겠습니까?");
  		 console.log(result);
  		 if(result){
  			 window.open('https://hrd4u.or.kr/portal/member/myPage.do');
  		 } else{
  			 return false;
  		 }
  		 
  		
  	});

	
});

function closeModal() {
  	$("#modal-action01").hide();
    $(".mask").fadeOut(150);
}


function fn_authInputReset(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
}
function fn_authInputFormSubmit(){
	<itui:submitValid items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	fn_createMaskLayer();
	return true;
}
</script>