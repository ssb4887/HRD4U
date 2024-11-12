<%@ include file="../../../../include/commonTop.jsp"%>
<script type="text/javascript">
$(function() {
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
    
    $(".btn-agreeInfo").on("click", function () {
    	location.href = "${REQUEST_INFOAGREE_FORM_URL}";
    });
    $(".btn-list").on("click", function () {
    	location.href = "${REQUEST_LIST_FORM_URL}";
    });
});
</script>
