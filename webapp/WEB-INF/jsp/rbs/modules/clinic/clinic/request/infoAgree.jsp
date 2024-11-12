<%@ include file="../../../../include/commonTop.jsp"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<script type="text/javascript">
$(function(){
	$("#next").click(function(){
		var isAgree = $('#agree').is(':checked')
		if(isAgree == false){
			alert("능력개발클리닉 운영에 관한 안내사항에 동의하지 않으면 신청을 할 수 없습니다.");
			$("#agree").focus();
			return false;
		}		
		
		location.href = "${REQUEST_WRITE_FORM_URL}";
	});
	
	$(".btn-cancel").click(function () {
		location.href = "${REQUEST_INFO_FORM_URL}";
	});
	
});
</script>