<%@ include file="../../../../include/commonTop.jsp"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<script type="text/javascript">
$(function(){
	<c:if test="${mngAuth || wrtAuth && dt.AUTH_MNG == '1'}">	
	// 삭제
	$(".fn_btn_delete").click(function(){
		try {
			var varConfirm = confirm("<spring:message code="message.select.delete.confirm"/>");
			if(!varConfirm) return false;
		}catch(e){return false;}
		return true;
	});
	</c:if>
	
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
});
</script>