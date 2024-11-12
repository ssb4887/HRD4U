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
	<jsp:include page="../header/instt.jsp" flush="false">
		<jsp:param name="cPath" value="main" />
	</jsp:include>
	<div class="enterprise-menu-wrapper">
		<ul>
			<li>
				<a href="${contextPath }/dct/diagnosis/list.do?mId=36">HRD기초진단</a>
			</li>
			<li>
				<a href="${contextPath }/dct/bsisCnsl/list.do?mId=37">기업HRD이음컨설팅</a>
			</li>
			<li>
				<a href="${contextPath }/dct/consulting/cnslListAll.do?mId=126">심화단계 컨설팅</a>
			</li>
			<li>
				<a href="${contextPath}/dct/developDct/develop_list_form.do?mId=62">맞춤과정개발</a>
			</li>
			<li>
				<a href="${contextPath }/dct/clinicDct/request_list_form.do?mId=66">능력개발클리닉</a>
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
							<strong id="program-cnt">${cnt.PROGRAM_CNT }</strong>건
						</div>
					</div>
					<div class="case-list-box">
						<p>협약</p>
						<div class="result">
							<strong id="agrem-cnt">${cnt.AGREM_CNT }</strong>건
						</div>
					</div>
					<div class="case-list-box">
						<p>비용 신청</p>
						<div class="result">
							<strong id="cost-cnt">${cnt.COST_CNT }</strong>건
						</div>
					</div>
					<div class="case-list-box">
						<p>지원 신청</p>
						<div class="result">
							<strong id="support-cnt">${cnt.SUPPORT_CNT }</strong>건
						</div>
					</div>
					<div class="case-list-box br0">
						<p>처리 임박 업무</p>
						<div class="result">
							<a href="#" id="open-modal02"><strong id="passed-cnt" class="point-color04"><c:out value="${fn:length(delayed) }" /></strong>건</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="enterprise-board-wrapper">
		<div class="enterprise-board-area-wrapper" style="justify-content:flex-start;">
			<div class="enterprise-board-area">
				<h2 class="enterprise-board-title">공지사항</h2>
				<a href="${contextPath }/dct/board/list.do?mId=33" class="btn-more">더보기</a>
				<ul>
					<c:choose>
					<c:when test="${fn:length(notice) > 0 }">
					<c:forEach items="${notice}" var="item">
					<li>
						<a href="${contextPath }/dct/board/view.do?mId=33&brdIdx=${item.IDX}">
							<strong class="title"><span style="color:black">[${item.INSTT_NAME }]</span> ${item.SUBJECT }</strong>
							<span class="date">${item.BOARD_DATE }</span>
						</a>
					</li>
					</c:forEach>
					</c:when>
					<c:otherwise>
					<li>
						<span>작성 글이 없습니다.</span>
					</li>
					</c:otherwise>
					</c:choose>
				</ul>
			</div>
			<div class="enterprise-board-area">
				<h2 class="enterprise-board-title">HRD 자료실</h2>
				<a href="${contextPath }/dct/board/list.do?mId=34" class="btn-more">더보기</a>
				<ul>
					<c:choose>
					<c:when test="${fn:length(hrdroom) > 0 }">
					<c:forEach items="${hrdroom }" var="item">
					<li>
						<a href="${contextPath }/dct/board/view.do?mId=34&brdIdx=${item.IDX}">
							<strong class="title"><span style="color:black">[${item.INSTT_NAME }]</span> ${item.SUBJECT }</strong>
							<span class="date">${item.BOARD_DATE }</span>
						</a>
					</li>
					</c:forEach>
					</c:when>
					<c:otherwise>
					<li>
						<span>작성 글이 없습니다.</span>
					</li>
					</c:otherwise>
					</c:choose>
				</ul>
			</div>
			<!-- 
			<div class="enterprise-board-area type02">
				<h2 class="enterprise-board-title">CEO 교육과정</h2>
				<a href="${contextPath}/dct/eduDct/list.do?mId=108" class="btn-more">더보기</a>
				<ul>
					<c:choose>
					<c:when test="${fn:length(ceo) > 0}">
					<c:forEach items="${ceo }" var='item'>
					<li>
						<a href="${contextPath}/dct/eduDct/view.do?mId=108&idx=${item.IDX}">
							<strong class="title">${item.SUBJECT }</strong>
							<span class="name">${item.INSTT_NAME }</span>
							<span class="date">${item.BOARD_DATE }</span>
						</a>
					</li>
					</c:forEach>
					</c:when>
					<c:otherwise>
					<li>
						<span>작성 글이 없습니다.</span>
					</li>
					</c:otherwise>
					</c:choose>
				</ul>
			</div>
			 -->
		</div>
		<div class="enterprise-board-area-wrapper" style="justify-content:flex-start;">
			<div class="enterprise-board-area type02">
				<h2 class="enterprise-board-title">주치의 게시판</h2>
				<a href="${contextPath}/dct/board/list.do?mId=58" class="btn-more">더보기</a>
				<ul>
					<c:choose>
					<c:when test="${fn:length(docBoard) > 0 }">
					<c:forEach items="${docBoard }" var='item'>
					<li>
						<a href="${contextPath}/dct/board/view.do?mId=58&brdIdx=${item.IDX}">
							<strong class="title">${item.SUBJECT }</strong>
							<span class="date">${item.BOARD_DATE }</span>
						</a>
					</li>
					</c:forEach>
					</c:when>
					<c:otherwise>
					<li>
						<span>작성 글이 없습니다.</span>
					</li>
					</c:otherwise>
					</c:choose>
				</ul>
			</div>
			<div class="enterprise-board-area type02">
				<h2 class="enterprise-board-title">지원 요청</h2>
				<a href="${contextPath}/dct/taskhub/reqlist.do?mId=48" class="btn-more">더보기</a>
				<ul>
					<c:choose>
					<c:when test="${fn:length(sptj) > 0 }">
					<c:forEach items="${sptj }" var='item'>
					<li>
						<a href="${contextPath}/dct/taskhub/reqlist.do?mId=48">
							<strong class="title">${item.SUBJECT }</strong>
							<span class="date">${item.BOARD_DATE }</span>
						</a>
					</li>
					</c:forEach>
					</c:when>
					<c:otherwise>
					<li>
						<span>작성 글이 없습니다.</span>
					</li>
					</c:otherwise>
					</c:choose>
				</ul>
			</div>
			<div class="enterprise-board-area type02">
				<h2 class="enterprise-board-title">주치의 교육과정</h2>
				<a href="${contextPath}/dct/edu/list.do?mId=138" class="btn-more">더보기</a>
				<ul>
					<c:choose>
					<c:when test="${fn:length(dct) > 0 }">
					<c:forEach items="${dct }" var='item'>
					<li>
						<a href="${contextPath}/dct/edu/view.do?mId=138&idx=${item.IDX}">
							<strong class="title">${item.SUBJECT }</strong>
							<span class="name">${item.INSTT_NAME }</span>
							<span class="date">${item.BOARD_DATE }</span>
						</a>
					</li>
					</c:forEach>
					</c:when>
					<c:otherwise>
					<li>
						<span>작성 글이 없습니다.</span>
					</li>
					</c:otherwise>
					</c:choose>
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
					<tbody id="delayed">
						<c:forEach var="item" items="${delayed }">
						<tr>
							<td>${item.RN }</td>
							<td>${item.BPL_NO }</td>
							<td></td>
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
	
	lazyloading();
 });
 
 async function lazyloading() {
	 let response = await fetch(`${contextPath}/dct/taskhub/count.do?mId=48`, {
		 method: "POST"
	 })
	 let result = await response.json();
	 render_counts({...result.cnts, 'DELAYED': result.delayed.length});
	 render_delayedjobs(result.delayed)
 }
 function render_counts(res) {
	 const { SUPPORT_CNT, COST_CNT, PASSED_CNT, AGREM_CNT, PROGRAM_CNT, DELAYED } = res;
	 const support_e = document.querySelector('strong#support-cnt');
	 const cost_e = document.querySelector('strong#cost-cnt');
	 const program_e = document.querySelector('strong#program-cnt');
	 const agrem_e = document.querySelector('strong#agrem-cnt');
	 const passed_e = document.querySelector('strong#passed-cnt');
	 support_e.textContent = SUPPORT_CNT;
	 cost_e.textContent = COST_CNT;
	 program_e.textContent = PROGRAM_CNT;
	 agrem_e.textContent = AGREM_CNT;
	 passed_e.textContent = DELAYED;
 }
 function render_delayedjobs(delayed) {
	 const tbody = document.querySelector('tbody#delayed');
	 delayed.forEach(e => {
		 const { RN, BPL_NO, BPL_NM, TNAME, STAGE, START_DATE, DELAY } = e;
		 const elem = document.createElement('tr');
		 elem.innerHTML = `
			 <tr>
			 	<td>\${RN}</td>
				<td>\${BPL_NO}</td>
				<td>\${BPL_NM}</td>
				<td>\${TNAME}</td>
				<td>\${STAGE}</td>
				<td>\${START_DATE}</td>
				<td class="point-color04">\${DELAY}</td>
			 </tr>
			 `;
		 tbody.appendChild(elem);
	 })
 }
</script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
