<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<!-- 만족도 조사지 양식 모달창 -->
	<div class="mask1 zIndex850"></div>
	<div class="modal-wrapper zIndex900" id="modal-image1">
		<h2>만족도 조사지 양식</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<img src="${contextPath}${imgPath}/contents/questionnaire01.png" alt="만족도 조사지 구분 운영 항목 본 훈련과정에 대하여 전반적으로 만족한다. 매우아니다 아니다 보통 그렇다 매우그렇다 항목 본 훈련과정의 교육방법에 대해 만족하십니까?(이론, 실습 PBLEM 등) 매우아니다 아니다 보통 그렇다 매우그렇다 항목 본 훈련과저의 교육시간에 대해 만족하십니까?(교육 내용 대비 시간 부족, 과다 등) 매우아니다 아니다 보통 그렇다 매우그렇다 구분 환경 항목 본 훈련과정의 교육환경에 대해 만족하십니까?(교육 장소, 시설, 장비, 교육자료 등 ) 매우아니다 아니다 보통 그렇다 매우그렇다 구분 훈련내용 항목 본 훈련과정의 교육내용에 만족하십니까?(기업 요구와의 부합여부, 구성 내용, 교육 수준 등) 매우아니다 아니다 보통 그렇다 매우그렇다 구분 강사 항목 본 훈련과정의 교육강사에 대해 만족하십니까?(강사 전문성, 강의 내용 전달능력 등) 매우아니다 아니다 보통 그렇다 매우그렇다" width="850px"/>
			</div>
		</div>
		<button type="button" class="btn-modal-close" data-idx="1">모달 창 닫기</button>
	</div>
<!-- 현업성취도 조사지 양식 모달창 -->
	<div class="mask2 zIndex850"></div>
	<div class="modal-wrapper zIndex900" id="modal-image2">
		<h2>현업성취도 조사지 양식</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<img src="${contextPath}${imgPath}/contents/questionnaire02.png" alt="현업성취도 조사지 구분 지식기술습득 항목 본 훈련과정의 학습목표 및 학습내용을 충분히 이해하였다. 매우아니다 아니다 보통 그렇다 매우그렇다 항목 본 훈련과정을 통해 새로운 지식 및 기술을 습득하였다. 매우아니다 아니다 보통 그렇다 매우그렇다 항목 본 훈련과정을 통해 습득한 지식 및 기술을 실무에 적용할 수 있다. 매우아니다 아니다 보통 그렇다 매우그렇다 구분 태도변화 항목 본 훈련과정을 통해 직무 전문성 및 업무 수행 자신감이 향상되었다.매우아니다 아니다 보통 그렇다 매우그렇다" width="850px"/>
			</div>
		</div>
		<button type="button" class="btn-modal-close" data-idx="2">모달 창 닫기</button>
	</div>
<!-- 현업적용도 조사지 양식 모달창 -->
	<div class="mask3 zIndex850"></div>
	<div class="modal-wrapper zIndex900" id="modal-image3">
		<h2>현업적용도 조사지 양식</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<img src="${contextPath}${imgPath}/contents/questionnaire03.png" alt="현업적용도 조사지 구분 훈련전이 항목 본 훈련과정 이수 후 훈련생에게 관련 업무수행 기회를 제공하였나요? 매우아니다 아니다 보통 그렇다 매우그렇다 항목 본 훈련과정 이수 후 훈련생의 업무수행 역량이 향상되었나요? 매우아니다 아니다 보통 그렇다 매우그렇다 항목 본 훈련과정 이수 후 훈련생의 업무수행에 실질적인 도움이 되었나요? 매우아니다 아니다 보통 그렇다 매우그렇다 구분 환경 항목 경영상 이슈(예 불량률 감소/매출액 증대 등) 해결에 본 훈련과정이 기여했다고 생각하시나요? 매우아니다 아니다 보통 그렇다 매우그렇다" width="850px"/>
			</div>
		</div>
		<button type="button" class="btn-modal-close" data-idx="3">모달 창 닫기</button>
	</div>
<!-- 연간 훈련계획 작성 예시 -->
	<div class="mask4 zIndex850"></div>
	<div class="modal-wrapper zIndex900" id="modal-image4">
		<h2>연간 훈련계획 작성 예시</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<img src="${contextPath}${imgPath}/contents/yearPlan.PNG" alt="연간 훈련계획 예시" width="850px"/>
			</div>
		</div>
		<button type="button" class="btn-modal-close" data-idx="4">모달 창 닫기</button>
	</div>
<!-- 자체 KPI목표 작성 예시 -->
	<div class="mask5 zIndex850"></div>
	<div class="modal-wrapper zIndex900" id="modal-image5">
		<h2>자체 KPI목표 작성 예시</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<img src="${contextPath}${imgPath}/contents/KPI.PNG" alt="자체 KPI목표 예시" width="850px"/>
			</div>
		</div>
		<button type="button" class="btn-modal-close" data-idx="5">모달 창 닫기</button>
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