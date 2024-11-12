<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="itemOrderName" value="develop_info_column_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<div class="mask"></div>
<div class="modal-wrapper" id="modal-action02">
	<h2>주치의 지원</h2>
	<div class="modal-area">
		<div class="modal-alert">
			<!-- <p>훈련과정 개발에 어려움이 있으신가요?<br>주치의 지원을 받아<br> <a href="#"><strong class="point-color01">주치의 훈련과정 개발</strong></a>이 진행 가능합니다.</p> -->
			<p>표준개발 사업을 선택해주세요.<br><strong class="point-color01">(사업주 </strong> 혹은 <strong class="point-color01">S-OJT)</strong></p>
		</div>
		<div class="btns-area">
			<a href="<c:out value="${DEVELOP_WRITE_FORM_URL}"/>&useFlag=new&prtbizIdx=4&devlopIdx=${devlopIdx}&bplNo=${bplNo}"  class="btn-m01 btn-color03 depth2 fn_btn_submit">사업주</a>
			<a href="<c:out value="${DEVELOP_WRITE_FORM_URL}"/>&useFlag=new&prtbizIdx=1&devlopIdx=${devlopIdx}&bplNo=${bplNo}" class="btn-m01 btn-color03 depth2 fn_btn_submit">S-OJT</a>				
		</div>
	</div>
	<button type="button" class="btn-modal-close">모달 창 닫기</button>
</div>
<script type="text/javascript">
$(function(){
// 	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	
// 	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	
	// 모달창 열기
	$("#open-modal02").on("click", function() {
        $(".mask").fadeIn(150, function() {
        	$("#modal-action02").show();
        });
    });
	
	// 모달창 닫기
	$("#modal-action02 .btn-modal-close").on("click", function() {
        $("#modal-action02").hide();
        $(".mask").fadeOut(150);
    });
	

});
</script>