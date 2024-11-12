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
	
	// 검색창 css추가
	$(".half-box:gt(1)").css("margin-top", "9px");
	
	$(".btn-recommend").click(function() {
		location.href = "${DEVELOP_RECOMMEND_FORM_URL}";
	});
	
	$(".btn-list").click(function() {
		location.href = "${DEVELOP_LIST_FORM_URL}";
	});
	
	// 주치의 지원 요청 버튼 클릭시 모달창 열기
	$("[id^='open-help']").on("click", function() {
		var devlopIdx = $(this).attr("data-devIdx");
		
		$.ajax({
			url:'${DEVELOP_HELP_FORM_URL}',
			type: 'GET',
			data: {
				devlopIdx : devlopIdx
			},success: function(data) {
				var status = data.status;
				var sidoName = data.sidoName;
				var ncsName = data.ncsName;
				var insttName = data.insttName;
				var cn = data.cn;
				var doctorOpinion = data.doctorOpinion;
				
				$("#status").text(status);
				$("#sidoName").text(sidoName);
				$("#ncsName").text(ncsName);
				$("#insttName").text(insttName);
				$("#cn").html(cn);
				$("#doctorOpinion").html(doctorOpinion);
				
			},
			error: function(e) {
				alert("주치의 지원요청 내용을 불러오는 데에 실패하였습니다.");
			}
		});
		
        $(".mask2").fadeIn(150, function() {
        	$("#modal-action02").show();
        });
    });
	
	// 모달창 닫기
    $("#modal-action02 .btn-modal-close").on("click", function() {
    	$("#sidoName").text("");
		$("#ncsName").text("");
		$("#insttName").text("");
		$("#cd").text("");
		$("#doctorOpinion").text("");
		
		$("#doctorOpinion").attr("readonly", false);
		$(".btns-area").show();
    	
    	$("#modal-action02").hide();
        $(".mask2").fadeOut(150);
    });
});
</script>