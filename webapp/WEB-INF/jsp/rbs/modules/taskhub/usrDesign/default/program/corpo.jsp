<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>							
<c:set var="searchFormId" value="fn_calendarSearchForm"/>							
<c:set var="listFormId" value="fn_calendarListForm"/>						
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/>
<c:set var="curPage" value="${empty param.page ? 1 : param.page }" />	
<c:set var="start" value="${(((curPage-1)/5.0)-(((curPage-1)/5.0)%1))*5+1 }" />
<c:set var="end" value="${(((curPage-1)/5.0)-(((curPage-1)/5.0)%1))*5+5 }" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>	
		<jsp:param name="searchFormId" value="${searchFormId}"/>				
		<jsp:param name="listFormId" value="${listFormId}"/>					
	</jsp:include>
</c:if>
<div class="contents-wrapper">
<!-- CMS 시작 -->
	<jsp:include page="../header/corpo.jsp" flush="false">
		<jsp:param name="usertype_idx" value="${usertype_idx }" />
		<jsp:param name="cPath" value="program" />
	</jsp:include>
	<div id="overlay"></div>
	<div class="loader"></div>
	<div class="contents-area">
		<div class="contents-box">
			<h4 class="title-type02">기업HRD이음컨설팅</h4>
			<c:choose>
			<c:when test="${not empty listHrdbsis }">
			<div class="progress-step-wrapper">
				<c:forEach var="item" begin="0" step="1" end="2" varStatus="status">
				<c:if test="${not empty listHrdbsis[status.index] }">
				<div class="progress-step-area">
					<ul class="progress-step-box">
						<li class="step">
							<a href="javascript:void(0);" class='<c:out value="${empty listHrdbsis[status.index].BSC_STATUS ? '' : 'active btn-bsc' }" />' data-idx="${listHrdbsis[status.index].BSC_IDX}">
								<span class="radio-bar"></span>
								<p>기초진단</p>
							</a>
						</li>
						<li class="step">
							<a href="javascript:void(0);" class='<c:out value="${empty listHrdbsis[status.index].RSLT_STATUS ? '' : 'active btn-qustnr' }" />' data-rslt="${listHrdbsis[status.index].RSLT_IDX }" data-bsc="${listHrdbsis[status.index].BSC_IDX }">
								<span class="radio-bar"></span>
								<p>설문조사</p>
							</a>
						</li>
						<li class="step">
							<a href="javascript:void(0);" class='<c:out value="${empty listHrdbsis[status.index].BSIS_STATUS ? '' : 'active btn-cnslt' }" />' data-rslt="${listHrdbsis[status.index].RSLT_IDX }" data-bsc="${listHrdbsis[status.index].BSC_IDX }">
								<span class="radio-bar"></span>
								<p>컨설팅</p>
							</a>
						</li>
						<li class="step">
							<a href="javascript:void(0);" class='<c:out value="${not empty listHrdbsis[status.index].BSIS_STATUS and listHrdbsis[status.index].BSIS_STATUS eq 1 ? 'active btn-cnslt' : '' }" />' data-rslt="${listHrdbsis[status.index].RSLT_IDX }" data-bsc="${listHrdbsis[status.index].BSC_IDX }"">
								<span class="radio-bar"></span>
								<p>완료</p>
							</a>
						</li>
					</ul>
					<a href="javascript:void(0);" class="word-linked-info01 fr btn-bsc" data-idx="${listHrdbsis[status.index].BSC_IDX }">기초진단 <c:out value="${listHrdbsis[status.index].ISSUE_DATE }" /></a>
				</div>
				</c:if>
				<c:if test="${empty listHrdbsis[status.index] }">
				<div class="progress-step-area"></div>
				</c:if>
				</c:forEach>
			</div>
			</c:when>
			<c:otherwise>
			<div class="progress-step-wrapper">
				<div class="progress-step-area">
					<a href="${contextPath }/web/diagnosis/apply.do?mId=53">
						<button type="button" class="step-start-btn">
							<span>시작하기</span>
						</button>
					</a>
				</div>
			</div>
			</c:otherwise>
			</c:choose>
		</div>
		<div class="contents-box">
			<h4 class="title-type02">심화단계 컨설팅</h4>
			<c:choose>
			<c:when test="${not empty listCnsl }">
			<div class="progress-step-wrapper">
				<c:forEach var="item" begin="0" step="1" end="2" varStatus="status">
				<c:if test="${not empty listCnsl[status.index] }">
				<div class="progress-step-area" >
					<ul class="progress-step-box">
						<li class="step" style="width:33.3%">
							<a href="${contextPath }/web/consulting/viewAll.do?mId=95&cnslIdx=${listCnsl[status.index].CNSL_IDX}" class='<c:out value="${listCnsl[status.index].CNSL_STATUS ge 10 ? 'active' : '' }" />'>
								<span class="radio-bar"></span>
								<p>신청</p>
							</a>
						</li>
						<li class="step" style="width:33.3%">
							<a href="${contextPath }/web/consulting/viewAll.do?mId=95&cnslIdx=${listCnsl[status.index].CNSL_IDX}#tab4" class='<c:out value="${listCnsl[status.index].REPRT_STATUS ge 10 ? 'active' : '' }" />'>
								<span class="radio-bar"></span>
								<p>보고서</p>
							</a>
						</li>
						<li class="step" style="width:33.3%">
							<a href="${contextPath }/web/consulting/viewAll.do?mId=95&cnslIdx=${listCnsl[status.index].CNSL_IDX}#tab4" class='<c:out value="${listCnsl[status.index].REPRT_STATUS eq 55 ? 'active' : '' }" />'>
								<span class="radio-bar"></span>
								<p>완료</p>
							</a>
						</li>
					</ul>
					<a href="javascript:void(0);" class="word-linked-info01 fr btn-bsc" data-idx="${listCnsl[status.index].BSC_IDX }">기초진단 <c:out value="${listCnsl[status.index].ISSUE_DATE }" /></a>
				</div>					
				</c:if>
				<c:if test="${empty listCnsl[status.index] }">
				<div class="progress-step-area"></div>
				</c:if>
				</c:forEach>
			</div>
			</c:when>
			<c:otherwise>
			<div class="progress-step-wrapper">
				<div class="progress-step-area">
					<a href="${contextPath }/web/consulting/main.do?mId=63">
						<button type="button" class="step-start-btn">
							<span>시작하기</span>
						</button>
					</a>
				</div>
			</div>
			</c:otherwise>
			</c:choose>
		</div>
		<div class="contents-box">
			<h4 class="title-type02">맞춤과정개발</h4>
			<c:choose>
			<c:when test="${not empty listCustom }">
				<div class="progress-step-wrapper">
					<c:forEach begin="0" step="1" end="2" varStatus="status">
					<c:if test="${not empty listCustom[status.index] }">
					<div class="progress-step-area">
						<ul class="progress-step-box">
							<li class="step">
								<a href="${contextPath }/web/consulting/viewAll.do?mId=95&cnslIdx=${listCustom[status.index].CNSL_IDX}" class='<c:out value="${listCustom[status.index].CNSL_STATUS ge 10 ? 'active' : '' }" />'>
									<span class="radio-bar"></span>
									<p>신청</p>
								</a>
							</li>
							<li class="step">
								<a href="${contextPath }/web/consulting/viewAll.do?mId=95&cnslIdx=${listCustom[status.index].CNSL_IDX}" class='<c:out value="${listCustom[status.index].REPRT_STATUS ge 10 ? 'active' : '' }" />'>
									<span class="radio-bar"></span>
									<p>보고서</p>
								</a>
							</li>
							<li class="step">
								<a href="${contextPath }/web/develop/support_view_form.do?mId=60&cnslIdx=${listCustom[status.index].CNSL_IDX }&ctIdx=1" class='<c:out value="${listCustom[status.index].COST_STATUS ge 10? 'active' : '' }" />'>
									<span class="radio-bar"></span>
									<p>비용</p>
								</a>
							</li>
							<li class="step">
								<a href="${contextPath }/web/develop/support_view_form.do?mId=60&cnslIdx=${listCustom[status.index].CNSL_IDX }&ctIdx=1" class='<c:out value="${listCustom[status.index].COST_STATUS eq 55 ? 'active' : '' }" />'>
									<span class="radio-bar"></span>
									<p>완료</p>
								</a>
							</li>
						</ul>
						<a href="javascript:void(0);" class="word-linked-info01 fr btn-bsc" data-idx="${listCustom[status.index].BSC_IDX }">기초진단 <c:out value="${listCustom[status.index].ISSUE_DATE }" /></a>
					</div>
					</c:if>
					<c:if test="${empty listCustom[status.index] }">
					<div class="progress-step-area"></div>
					</c:if>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
			<div class="progress-step-wrapper">
				<div class="progress-step-area">
					<a href="${contextPath }/web/develop/develop_customized_info.do?mId=112">
						<button type="button" class="step-start-btn">
							<span>시작하기</span>
						</button>
					</a>
				</div>
			</div>
			</c:otherwise>
			</c:choose>
		</div>
		<div class="contents-box">
			<h4 class="title-type02">능력개발클리닉</h4>
			<c:choose>
			<c:when test="${not empty listClinic }">
			<div class="progress-step-wrapper">	
				<c:forEach begin="0" step="1" end="2" varStatus="status">
				<c:if test="${not empty listClinic[status.index] }">
				<div class="progress-step-area">
					<ul class="progress-step-box">
						<li class="step">
							<a href="${contextPath }/web/clinic/request_list_form.do?mId=66" class='<c:out value="${listClinic[status.index].REQ_STATUS ge 10 ? 'active' : '' }" />'>
								<span class="radio-bar"></span>
								<p>신청</p>
							</a>
						</li>
						<li class="step">
							<a href="${contextPath }/web/clinic/dashboard_list_form.do?mId=66" class='<c:out value="${listClinic[status.index].PLAN_STATUS ge 10 ? 'active' : '' }" />'>
								<span class="radio-bar"></span>
								<p>훈련 계획</p>
							</a>
						</li>
						<li class="step">
							<a href="${contextPath }/web/clinic/dashboard_list_form.do?mId=66" class='<c:out value="${listClinic[status.index].RESLT_STATUS ge 10 ? 'active' : '' }" />'>
								<span class="radio-bar"></span>
								<p>성과 보고</p>
							</a>
						</li>
						<li class="step">
							<a href="${contextPath }/web/clinic/dashboard_list_form.do?mId=66" class='<c:out value="${listClinic[status.index].SPORT_STATUS ge 10 ? 'active' : '' }" />'>
								<span class="radio-bar"></span>
								<p>비용 청구</p>
							</a>
						</li>
					</ul>
					<a href="${contextPath }/web/clinic/dashboard_list_form.do?mId=66" class="word-linked-info01 fr">능력개발 클리닉 <c:out value="${listClinic[status.index].ISSUE_DATE }" /></a>
				</div>
				</c:if>
				<c:if test="${empty listClinic[status.index] }">
				<div class="progress-step-area"></div>
				</c:if>
				</c:forEach>
			</div>
			</c:when>
			<c:otherwise>
			<div class="progress-step-wrapper">
				<div class="progress-step-area">
					<a href="${contextPath }/web/clinic/request_info_form.do?mId=65">
						<button type="button" class="step-start-btn">
							시작하기
						</button>
					</a>
				</div>
			</div>
			</c:otherwise>
			</c:choose>
		</div>
		<div class="contents-box">
			<h4 class="title-type02">체계적 현장훈련(S-OJT)</h4>
			<c:choose>
			<c:when test="${not empty listSojt }">
			<div class="progress-step-wrapper">
				<c:forEach begin="0" step="1" end="2" varStatus="status">
				<c:if test="${not empty listSojt[status.index] }">
				<div class="progress-step-area">
					<ul class="progress-step-box">
						<li class="step" style="width: 33.3%;">
							<a href="${contextPath }/web/sojt/applyList.do?mId=121" class='<c:out value="${listSojt[status.index].STATUS ge 10  ? 'active' : '' }" />'>
								<span class="radio-bar"></span>
								<p>신청</p>
							</a>
						</li>
						<li class="step" style="width: 33.3%;">
							<a href="${contextPath }/web/sojt/applyList.do?mId=121" class='<c:out value="${listSojt[status.index].STATUS eq 30 or listSojt[status.index].STATUS eq 50 or listSojt[status.index].STATUS eq 55 ? 'active' : '' }" />'>
								<span class="radio-bar"></span>
								<p>접수</p>
							</a>
						</li>
						<li class="step" style="width: 33.3%;">
							<a href="${contextPath }/web/sojt/applyList.do?mId=121" class='<c:out value="${listSojt[status.index].STATUS eq 55 ? 'active' : '' }" />' style="line-height: 20px;">
								<span class="radio-bar"></span>
								<p>승인</p>
							</a>
						</li>
					</ul>
					<a href="javascript:void(0);" class="word-linked-info01 fr btn-bsc" data-idx="${listSojt[status.index].BSC_IDX }">기초진단 <c:out value="${listSojt[status.index].ISSUE_DATE }" /></a>
				</div>
				</c:if>
				<c:if test="${empty listSojt[status.index] }">
				<div class="progress-step-area"></div>
				</c:if>
				</c:forEach>
			</div>
			</c:when>
			<c:otherwise>
			<div class="progress-step-wrapper">
				<div class="progress-step-area">
					<a href="${contextPath }/web/sojt/init.do?mId=110">
						<button type="button" class="step-start-btn">
							시작하기
						</button>
					</a>
				</div>
			</div>
			</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
<form method="POST" id="form-box">
	<input type="hidden" name="rslt" id="rslt">
	<input type="hidden" name="bsc" id="bsc">
	<input type="hidden" name="idx" id="idx">
</form>
<script>
document.addEventListener('DOMContentLoaded', function() {
	const parameters = new URLSearchParams(window.location.search);
	for(const [key, value] of parameters) {
		const element = document.querySelector(`#\${key}`);
		if(element) element.value = value;
	}
	
	// 기초컨설팅
	const bscButtons = document.querySelectorAll('.btn-bsc');
	bscButtons.forEach(function(bscBtn) {
		bscBtn.addEventListener('click', function(event) {
			const idx = bscBtn.getAttribute('data-idx');
			const form = document.getElementById('form-box');
			const actionUrl = '${contextPath}/web/bsisCnsl/view.do?mId=56';
			form.action = actionUrl;
			document.getElementById('idx').value = idx;
			form.submit(); 
		});
	});
	
	// 설문조사 
	const qustnrButtons = document.querySelectorAll('.btn-qustnr');
	qustnrButtons.forEach(function(qustnrBtn) {
		qustnrBtn.addEventListener('click', function(event) {
			const rslt = qustnrBtn.getAttribute('data-rslt');
			const bsc = qustnrBtn.getAttribute('data-bsc');
			const form = document.getElementById('form-box');
			const actionUrl = '${contextPath}/web/bsisCnsl/qustnr.do?mId=56';
			form.action = actionUrl;
			document.getElementById('rslt').value = rslt;
			document.getElementById('bsc').value = bsc;
			form.submit();
		});
	});
	
	// 기초컨설팅
	const cnsltButtons = document.querySelectorAll('.btn-cnslt');
	cnsltButtons.forEach(function(cnsltBtn) {
		cnsltBtn.addEventListener('click', function(event) {
			const rslt = cnsltBtn.getAttribute('data-rslt');
			const bsc = cnsltBtn.getAttribute('data-bsc');
			const form = document.getElementById('form-box');
			const actionUrl = '${contextPath}/web/bsisCnsl/cnslt.do?mId=56';
			form.action = actionUrl;
			document.getElementById('rslt').value = rslt;
			document.getElementById('bsc').value = bsc;
			form.submit();
		});
	});
});
</script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
