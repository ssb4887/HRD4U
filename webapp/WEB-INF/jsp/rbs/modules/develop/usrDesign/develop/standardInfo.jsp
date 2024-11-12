<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}" />
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/develop/develop/info.jsp" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />
<div class="contents-wrapper">
	<div class="contents-area">
		<div class="title-wrapper">
			<a href="#none" class="btn-m01 btn-color01 fr" id="open-modal01">주치의 지원 (HELP)</a>
		</div>
		<div class="title-wrapper02">
			<h3 class="title-type04 center">훈련과정 표준개발이란?</h3>
		</div>
		<div class="contents-box pl0">
			<div class="border-box">
				<div class="accordion-wrapper">
					<button type="button"><strong><span class="number">1</span> 훈련과정 표준개발은 무엇인가요?</strong></button>
					<ul class="accordion-list" style="display:none;">
						<li>훈련 추천 AI를 활용하여 기업이 원하는 훈련을 빠르고 쉽게 지원해 드려요.</li>
					</ul>
				</div>
				<div class="accordion-wrapper">
					<button type="button"><strong><span class="number">2</span> 훈련과정 표준개발은 언제 활용하나요?</strong></button>
					<ul class="accordion-list" style="display:none;">
						<li>훈련을 하고싶은 기업이 훈련과정이 없거나 부족할 때 필요한 과정을 손쉽게 활용해 보세요.</li>
					</ul>
				</div>
				<div class="accordion-wrapper">
					<button type="button"><strong><span class="number">3</span> 훈련과정 표준개발의 특징은 무엇인가요?</strong></button>
					<ul class="accordion-list" style="display:none;">
						<li>우리 기업에게 필요한 과정을 쉽고 빠르게 지원 받을 수 있고, 필요하다면 상세항목(교과목/시간)을 손쉽게 수정할 수 있어요.</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="btns-area">
		<button type="button" class="btn-b01 one round01 btn-color03 left btn-recommend">
			<span>표준개발 신청</span><img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01">
		</button>
		<button type="button" class="btn-b01 one round01 btn-color02 left btn-list">
			<span>내 신청 목록</span><img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01">
		</button>
	</div>
</div>
<%@ include file="doctorHelpModal.jsp" %>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>