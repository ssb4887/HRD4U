<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="itemOrderName" value="develop_info_column_order"/>
<itui:submitScript items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
<div class="mask"></div>
<div class="modal-wrapper" id="modal-action01">
	<h2>주치의 지원</h2>
	<div class="modal-area">
		<div class="modal-alert">
			<!-- <p>훈련과정 개발에 어려움이 있으신가요?<br>주치의 지원을 받아<br> <a href="#"><strong class="point-color01">주치의 훈련과정 개발</strong></a>이 진행 가능합니다.</p> -->
			<p>훈련과정 개발에 어려움이 있으신가요?<br><strong class="point-color01">주치의</strong>가 대신 <strong class="point-color01">훈련과정 개발</strong>을 해드릴 수 있습니다.</p>
		</div>
		<form name="doctorHelpForm" id="doctorHelpForm" action="${DEVELOP_DOCTOR_HELP_URL}" method="post">
			<div class="contents-box pl0">
				<div class="basic-search-wrapper">
					<div class="one-box">
						<dl>
							<dt>
								<label for="modal-textfield0202"> 훈련실시주소 </label>
							</dt>
							<dd>
								<itui:objectSelectClassCustom itemId="helpSido" itemInfo="${itemInfo}"/>
							</dd>
						</dl>
					</div>
					<div class="one-box">
						<dl>
							<dt>
								<label for="modal-textfield0203"> 훈련직무 </label>
							</dt>
							<dd>
								<itui:objectSelectClassCustom itemId="trDty" itemInfo="${itemInfo}"/>
							</dd>
						</dl>
					</div>
					<div class="one-box">
						<dl>
							<dt>
								<label for="modal-textfield0204"> 기타요구사항 </label>
							</dt>
							<dd>
								<textarea name="etcDemandMatter" id="etcDemandMatter"></textarea>
							</dd>
						</dl>
					</div>
				</div>
			</div>
			<div class="btns-area">
				<button type="submit" class="btn-b02 round01 btn-color03 left btn-submit">
					<span> 주치의 지원요청 </span> <img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01">
				</button>
			</div>
		</form>
	</div>
	<button type="button" class="btn-modal-close">모달 창 닫기</button>
</div>
<script type="text/javascript">
$(function(){
	<itui:submitReset items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	
	<itui:submitInit items="${itemInfo.items}" itemOrder="${itemInfo[itemOrderName]}"/>
	// css 적용
	$("#helpSido1, #trDty1, #trDty2").css("margin-right", "5px");
	
	// 모달창 열기
	$("#open-modal01").on("click", function() {
        $(".mask").fadeIn(150, function() {
        	$("#modal-action01").show();
        });
    });
	
	// 모달창 닫기
	$("#modal-action01 .btn-modal-close").on("click", function() {
        $("#modal-action01").hide();
        $(".mask").fadeOut(150);
    });
	
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
	
	// 지원요청
	$("#doctorHelpForm .btn-submit").click(function() {
		var varConfirm = confirm("주치의 지원을 요청하시겠습니까?");
		if(!varConfirm) return false;
		
		if($("#helpSido option:selected").val() == "" || $("#helpSido2 option:selected").val() == ""){
			alert("훈련을 실시할 주소를 선택해 주세요.");
			return false;
		}
		
		if($("#trDty option:selected").val() == "" || $("#trDty2 option:selected").val() == "" || $("#trDty3 option:selected").val() == ""){
			alert("훈련 직무를 선택해 주세요.");
			return false;
		}
		
		return true;
	});
});
</script>