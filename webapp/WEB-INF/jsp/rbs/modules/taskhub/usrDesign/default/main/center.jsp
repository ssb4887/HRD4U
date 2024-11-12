<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>							
<c:set var="searchFormId" value="fn_calendarSearchForm"/>							
<c:set var="listFormId" value="fn_calendarListForm"/>						
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/>				
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>	
		<jsp:param name="searchFormId" value="${searchFormId}"/>				
		<jsp:param name="listFormId" value="${listFormId}"/>					
	</jsp:include>
</c:if>
<div class="contents-wrapper">
<!-- CMS 시작 -->
	<jsp:include page="../header/center.jsp" flush="false">
		<jsp:param name="cPath" value="main" />
	</jsp:include>
	<div class="enterprise-menu-wrapper">
		<ul>
			<li>
				<a href="${contextPath }/web/consulting/cnslListAll.do?mId=95">심화단계 컨설팅</a>
			</li>
			<li>
				<a href="${contextPath }/web/develop/develop_standard_info.do?mId=59">맞춤과정개발</a>
			</li>
		</ul>
	</div>
	<div class="enterprise-case-wrapper">
		<div class="enterprise-case-area">
			<div class="enterprise-case-box">
				<div class="enterprise-case-list depth3">
					<div class="case-list-box">
						<p>사업 관리</p>
						<div class="result">
							<strong><c:out value="${cnt.PROGRAM_CNT }" /></strong>건
						</div>
					</div>
					<div class="case-list-box">
						<p>협약</p>
						<div class="result">
							<strong><c:out value="${cnt.AGREM_CNT }" /></strong>건
						</div>
					</div>
					<div class="case-list-box">
						<p>비용 신청</p>
						<div class="result">
							<strong><c:out value="${cnt.COST_CNT }" /></strong>건
						</div>
					</div>
					<div class="case-list-box">
						<p>활동 일지</p>
						<div class="result">
							<strong>0</strong>건
						</div>
					</div>
					<div class="case-list-box br0">
						<p>처리 임박 업무</p>
						<div class="result">
							<a href="#" id="open-modal02"><strong class="point-color04"><c:out value="${fn:length(delayed)}"/></strong>건</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="enterprise-board-wrapper">
		<div class="enterprise-board-area-wrapper">
			<div class="enterprise-board-area">
				<h2 class="enterprise-board-title">협약</h2>
				<a href="${contextPath }/web/agreement/list.do?mId=52" class="btn-more">더보기</a>
				<ul>
					<c:if test="${not empty agrem }">
					<c:forEach items="${agrem}" var="item">
					<li>
						<a href="${contextPath }/web/agreement/list.do?mId=52">
							<strong class="title">${item.NAME }</strong>
							<span class="date">${item.PERIOD }</span>
						</a>
					</li>
					</c:forEach>
					</c:if>
					<c:if test="${empty agrem }">
					<li>
						작성 글이 없습니다.
					</li>
					</c:if>
				</ul>
			</div>
			<div class="enterprise-board-area">
				<h2 class="enterprise-board-title">활동일지</h2>
				<a href="${contextPath}/web/board/list.do?mId=34" class="btn-more">더보기</a>
				<ul>
					<c:if test="${not empty diary }">
					<c:forEach items="${diary}" var="item">
					<li>
						<a href="${contextPath }/web/board/view.do?mId=34&brdIdx=${item.IDX}">
							<strong class="title"><span style="color:black">[${item.INSTT_NAME }]</span> ${item.SUBJECT }</strong>
							<span class="date">${item.BOARD_DATE }</span>
						</a>
					</li>
					</c:forEach>
					</c:if>
					<c:if test="${empty diary }">
					<li>
						작성 글이 없습니다.
					</li>
					</c:if>
				</ul>
			</div>
		</div>
		<div class="enterprise-board-area-wrapper" style="justify-content:flex-start;">
			<div class="enterprise-board-area">
				<h2 class="enterprise-board-title">공지사항</h2>
				<a href="${contextPath}/web/board/list.do?mId=34" class="btn-more">더보기</a>
				<ul>
					<c:if test="${not empty notice }">
					<c:forEach items="${notice}" var="item">
					<li>
						<a href="${contextPath }/web/board/view.do?mId=34&brdIdx=${item.IDX}">
							<strong class="title"><span style="color:black">[${item.INSTT_NAME }]</span> ${item.SUBJECT }</strong>
							<span class="date">${item.BOARD_DATE }</span>
						</a>
					</li>
					</c:forEach>
					</c:if>
					<c:if test="${empty notice }">
					<li>
						작성 글이 없습니다.
					</li>					
					</c:if>
				</ul>
			</div>
			<div class="enterprise-board-area">
				<h2 class="enterprise-board-title">HRD 자료실</h2>
				<a href="${contextPath}/web/board/list.do?mId=35" class="btn-more">더보기</a>
				<ul>
					<c:if test="${not empty hrdroom }">
					<c:forEach items="${hrdroom }" var="item">
					<li>
						<a href="${contextPath }/web/board/view.do?mId=35&brdIdx=${item.IDX}">
							<strong class="title"><span style="color:black">[${item.INSTT_NAME }]</span> ${item.SUBJECT }</strong>
							<span class="date">${item.BOARD_DATE }</span>
						</a>
					</li>
					</c:forEach>
					</c:if>
					<c:if test="${empty hrdroom }">
					<li>
						작성 글이 없습니다.
					</li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
		<!-- //CMS 끝 -->
</div>
<!-- //contents -->
<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="modal-action02" style="width: 1000px; margin-left: -500px">
	<h2>처리 임박 업무</h2>
	<div class="modal-area">
		<div class="contents-box pl0">
			<div class="table-type01 horizontal-scroll">
				<table class="width-type03">
					<caption>처리 임박 업무표 : 번호, 종류, 단계, 시작일, 경과일에 관한 정보 제공표</caption>
					<colgroup>
						<col style="width: 10%">
						<col style="width: 25%">
						<col style="width: 15%">
						<col style="width: 15%">
						<col style="width: 15%">
						<col style="width: 15%">
						<col style="width: 15%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">관리번호</th>
							<th scope="col">업체</th>
							<th scope="col">종류</th>
							<th scope="col">단계</th>
							<th scope="col">시작일</th>
							<th scope="col">경과일</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${delayed }">
						<tr>
							<td>${item.RN }</td>
							<td>${item.BPL_NO }</td>
							<td>${item.BPL_NM }</td>
							<td>
								<a href="">${item.TNAME }</a>
							</td>
							<td>${item.STAGE }</td>
							<td>${item.START_DATE }</td>
							<td class="point-color04"><c:out value="${item.DELAY }일" /></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<button type="button" class="btn-modal-close">모달 창 닫기</button>
</div>
<!-- //모달 창 -->
<script>
$(function() {
	$("#open-modal01").on("click", function() {
		$(".mask").fadeIn(150, function() {
			$("#modal-action01").show();
 		});
 	});
	$("#open-modal02").on("click", function() {
		$(".mask").fadeIn(150, function() {
			$("#modal-action02").show();
 		});
	});
	$("#modal-action01 .btn-modal-close").on("click", function() {
		$("#modal-action01").hide();
		$(".mask").fadeOut(150);
 	});
	$("#modal-action02 .btn-modal-close").on("click", function() {
		$("#modal-action02").hide();
		$(".mask").fadeOut(150);
 	});
 });
</script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
