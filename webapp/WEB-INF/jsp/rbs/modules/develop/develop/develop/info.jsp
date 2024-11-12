<%@ include file="../../../../include/commonTop.jsp"%>
<script type="text/javascript">
$(function(){
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
	
	$(".btn-recommend").click(function() {
		location.href = "${DEVELOP_RECOMMEND_FORM_URL}";
	});
	
	$(".btn-list").click(function() {
		location.href = "${DEVELOP_LIST_FORM_URL}";
	});
	
	$(".btn-cnsl").click(function() {
		var contextPath ="${contextPath}";
		var crtSiteId = "${crtSiteId}";
		var bplNo = "${bplNo}";
		
		location.href = contextPath + "/" + crtSiteId + "/consulting/cnslApplyForm.do?mId=85&bplNo=" + bplNo + "&type=A";
	});
});
</script>