<%@ include file="../../../../include/commonTop.jsp"%>
<script type="text/javascript">
$(function(){
	// 안쓰는 신청상태 삭제
	$("#is_confmStatus").find("option[value='5']").hide();
	$("#is_confmStatus").find("option[value='7']").hide();
	$("#is_confmStatus").find("option[value='20']").hide();
	$("#is_confmStatus").find("option[value='35']").hide();
	$("#is_confmStatus").find("option[value='50']").hide();
	$("#is_confmStatus").find("option[value='53']").hide();
	$("#is_confmStatus").find("option[value='70']").hide();
	$("#is_confmStatus").find("option[value='72']").hide();
	$("#is_confmStatus").find("option[value='73']").hide();
	
	// 검색창 css추가
	$(".half-box:gt(1)").css("margin-top", "9px");
	
	// 주치의 지원요청 모달창 placeholder 추가
	$("#doctorOpinion").attr("placeholder", "주치의 지원요청을 반려할 때만 입력해 주세요.");
	// 주치의 지원요청 승인
	$(".btn-approve").click(function(){
		try {
			var varConfirm = confirm("해당 주치의 지원요청을 승인하시겠습니까?");
			if(!varConfirm) return false;
			
			var apporoveUrl = "${DEVELOP_HELP_ACCEPT_URL}";
			$("#doctorHelpForm").attr("action", apporoveUrl);
		}catch(e){return false;}
		return true;
	});
	
	// 변경요청에 대한 반려
	$(".btn-return").click(function(){
		try {
			var varConfirm = confirm("해당 주치의 지원요청을 반려하시겠습니까?");
			if(!varConfirm) return false;
			
			if($("#doctorOpinion").val() == ""){
				alert("주치의 의견을 입력해 주세요.");
				$("#doctorOpinion").focus();
				return false;
			}
			
			var returnUrl = "${DEVELOP_HELP_RETURN_URL}";
			$("#doctorHelpForm").attr("action", returnUrl);
		}catch(e){return false;}
		return true;
	});
	
	// 주치의 지원 요청 버튼 클릭시 모달창 열기
	$("[id^='open-modal']").on("click", function() {
		var devlopIdx = $(this).attr("data-devIdx");
		$("#devlopIdx").val(devlopIdx);
		
		$.ajax({
			url:'${DEVELOP_HELP_FORM_URL}',
			type: 'GET',
			data: {
				devlopIdx : devlopIdx
			},success: function(data) {
				var sidoName = data.sidoName;
				var ncsName = data.ncsName;
				var insttName = data.insttName;
				var cn = data.cn;
				var doctorOpinion = data.doctorOpinion;
				
				$("#sidoName").text(sidoName);
				$("#ncsName").text(ncsName);
				$("#insttName").text(insttName);
				$("#cn").html(cn);
				
				if(doctorOpinion != null){
					$("#doctorOpinion").text(doctorOpinion);
					$("#doctorOpinion").attr("readonly", true);
					$(".btns-area").hide();
				}
			},
			error: function(e) {
				alert("주치의 지원요청 내용을 불러오는 데에 실패하였습니다.");
			}
		});
		
        $(".mask").fadeIn(150, function() {
        	$("#modal-action01").show();
        });
    });
	
	// 모달창 닫기
    $("#modal-action01 .btn-modal-close").on("click", function() {
    	$("#devlopIdx").val("");
    	$("#sidoName").text("");
		$("#ncsName").text("");
		$("#insttName").text("");
		$("#cd").text("");
		$("#doctorOpinion").text("");
		
		$("#doctorOpinion").attr("readonly", false);
		$(".btns-area").show();
    	
    	$("#modal-action01").hide();
        $(".mask").fadeOut(150);
    });
});

</script>