<%@ include file="../../../include/commonTop.jsp"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<script type="text/javascript">
$(function(){
	$(".contents-title").text("훈련동향");
	// 수정
	$(".fn_btn_modify").click(function(){
		try {
			var indutyName = $("#indutyCode").find("option:selected").text();
			var indutyCode = $("#indutyCode").find("option:selected").val();
			var year = $("#year").find("option:selected").val();
			
			if(indutyName == "" || year == ""){
				alert("데이터 수정을 위해서는 업종과 연도를 선택해 주세요.");
				return false;
			}
			
			 var url = $(this).attr("href");
			 url += "&indutyCd=" + indutyCode + "&year=" + year;
			 $(this).attr("href", url);
		}catch(e){return false;}
		return true;
	});
	// 삭제
	$(".fn_btn_delete").click(function(){
		try {
			var indutyName = $("#indutyCode").find("option:selected").text();
			var indutyCode = $("#indutyCode").find("option:selected").val();
			var year = $("#year").find("option:selected").val();
			if(indutyName == "" || year == ""){
				alert("데이터 삭제를 위해서는 업종과 연도를 선택해 주세요.");
				return false;
			}
			var message = indutyName + "의 " + year + "년 직업훈련 실시 현황을 삭제하겠습니까?";
			var varConfirm = confirm(message);
			if(!varConfirm) return false;
			 
			 var url = $(this).attr("href");
			 url += "&indutyCode=" + indutyCode + "&year=" + year;
			 $(this).attr("href", url);
			
		}catch(e){
			return false;
		}
		return true;
	});
});
</script>