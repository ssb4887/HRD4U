<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<!-- 연간 훈련실시 결과 작성 예시 -->
	<div class="mask1 zIndex850"></div>
	<div class="modal-wrapper zIndex900" id="modal-image1">
		<h2>연간 훈련실시 결과 작성 예시</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<img src="${contextPath}${imgPath}/contents/yearResult.PNG" alt="연간 훈련실시 결과 작성 예시" width="850px"/>
			</div>
		</div>
		<button type="button" class="btn-modal-close" data-idx="1">모달 창 닫기</button>
	</div>
<!-- 자체 KPI이행 결과 작성 예시 -->
	<div class="mask2 zIndex850"></div>
	<div class="modal-wrapper zIndex900" id="modal-image2">
		<h2>자체 KPI이행 결과 작성 예시</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<img src="${contextPath}${imgPath}/contents/KPIResult.PNG" alt="자체 KPI이행 결과 작성 예시" width="850px"/>
			</div>
		</div>
		<button type="button" class="btn-modal-close" data-idx="2">모달 창 닫기</button>
	</div>
<script type="text/javascript">
$(function(){
	// css 수정
	$(".modal-wrapper").css("left", "40%");
	$(".modal-wrapper").css("width", "900px");
	// 모달창 열기
	$("[id^='open-image']").on("click", function() {
        var index = $(this).attr("data-idx");
		$(".mask" + index).fadeIn(150, function() {
        	$("#modal-image" + index).show();
        });
    });
	
	// 모달창 닫기
	$("[id^='modal-image'] .btn-modal-close").on("click", function() {
		var index = $(this).attr("data-idx");
		$("#modal-image" + index).hide();
        $(".mask" + index).fadeOut(150);
    });
});
</script>