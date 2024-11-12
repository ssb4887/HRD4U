<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/develop/develop/info.jsp"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
<div class="contents-wrapper">
		<div class="contents-area">
		<div class="title-wrapper02">
			<h3 class="title-type04 center">훈련과정 맞춤개발이란?</h3>
		</div>
		<div class="contents-box pl0">
			<div class="border-box">
				<div class="accordion-wrapper">
					<button type="button"><strong><span class="number">1</span> 훈련과정 맞춤개발은 무엇인가요?</strong></button>
					<ul class="accordion-list" style="display:none;">
						<li>훈련과정을 외부전문가에게 컨설팅 받아 맞춤형으로 개발하고 싶은 기업을 지원해 드려요.</li>
					</ul>
				</div>
				<div class="accordion-wrapper">
					<button type="button"><strong><span class="number">2</span> 훈련과정 맞춤개발은 언제 활용하나요?</strong></button>
					<ul class="accordion-list" style="display:none;">
						<li>표준훈련 과정만으로는 부족하고 기업만의 특징을 못 담을 때, 과제수행 OJT훈련(PBL)을 참여할 때 활용해 보세요.</li>
					</ul>
				</div>
				<div class="accordion-wrapper">
					<button type="button"><strong><span class="number">3</span> 훈련과정 맞춤개발의 특징은 무엇인가요?</strong></button>
					<ul class="accordion-list" style="display:none;">
						<li>기업진단 결과를 기반으로 주치의 및 외부전문가가 기업에 필요한  과정을 맞춤형으로 지원해드려요.<br><br>과제수행 OJT훈련(PBL)은 필수로 개발해야 해요.</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="btns-area">
		<button type="button" class="btn-b01 one round01 btn-color03 left btn-cnsl">
			<span>맞춤개발 신청</span><img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01">
		</button>
	</div>
</div>
<%@ include file="doctorHelpModal.jsp" %>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>