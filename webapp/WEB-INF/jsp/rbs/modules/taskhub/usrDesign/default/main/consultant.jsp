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
<style>
 .redgrad {
 	background: rgb(131,58,180);
 	background: linear-gradient(90deg,
 		rgba(131,58,180,1) 0%,
 		rgba(253,29,29,1) 50%,
 		rgba(252,176,69,1) 100%);
 	-webkit-background-clip: text;
 	color: transparent;
 	line-height: 1.2;
 	padding: 5px 0;
 }
</style>
<div class="contents-wrapper">
<!-- CMS 시작 -->
	<jsp:include page="../header/consultant.jsp" flush="false">
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
						<p>보고서 작성</p>
						<div class="result">
							<strong>${empty cnt.PROGRAM_CNT ? '0' : cnt.PROGRAM_CNT}</strong>건
						</div>
					</div>
					<div class="case-list-box">
						<p>비용 신청</p>
						<div class="result">
							<strong>${empty cnt.COST_CNT ? '0' : cnt.COST_CNT}</strong>건
						</div>
					</div>
					<div class="case-list-box br0">
						<p>처리 임박 업무</p>
						<div class="result">
							<a href="#" id="open-modal02"><strong class="point-color04"><c:out value="${fn:length(delayed)}" /></strong>건</a>
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
				<a href="${contextPath }/web/board/list.do?mId=34" class="btn-more">더보기</a>
				<ul>
					<c:choose>
					<c:when test="${fn:length(notice) >0 }">
					<c:forEach items="${notice}" var="item">
					<li>
						<a href="${contextPath }/web/board/view.do?mId=34&brdIdx=${item.IDX}">
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
				<a href="${contextPath }/web/board/list.do?mId=35" class="btn-more">더보기</a>
				<ul>
					<c:choose>
					<c:when test="${fn:length(hrdroom) > 0 }">
					<c:forEach items="${hrdroom }" var="item">
					<li>
						<a href="${contextPath }/web/board/view.do?mId=35&brdIdx=${item.IDX}">
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
			<div class="enterprise-board-area type02">
				<h2 class="enterprise-board-title">컨설턴트 자료실</h2>
				<a href="${contextPath }/web/board/list.do?mId=111" class="btn-more">더보기</a>
				<ul>
					<c:choose>
					<c:when test="${fn:length(consult) > 0 }">
					<c:forEach items="${consult }" var='item'>
					<li>
						<a href="${contextPath }/web/board/view.do?mId=111&brdIdx=${item.IDX}">
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
		<div class="enterprise-board-area-wrapper" style="justify-content: flex-start;">
			<c:forEach items="${progressCNSL }" var="item">
			<div class="enterprise-board-area">
				<h2 class="enterprise-board-title">${item.TYPE_GROUP } ${item.SOJT_STATUS eq 55 ? "<span style='font-size:20px;margin-left:4px;' class='redgrad'>S-OJT</span>" : "" }</h2>
				<div class="enterprise-step-wrapper">
					<ul class="enterprise-step-area">
						<li class="progressbar bar01">
							<a class="${item.CNSL_STATUS ge 10 ? "active" : "" }">
								<span class="radio-bar" title="시작"></span>
								<p>신청</p>
							</a>
						</li>
						<li class="progressbar bar02">
							<a class="${item.CNSL_STATUS eq 55 ? "active" : "" }">
								<span class="radio-bar" title="컨설팅"></span>
								<p>컨설팅</p>
							</a>
						</li>
						<li class="progressbar bar03">
							<a class="${item.RERPT_STATUS ge 10 ? "active" : "" }">
								<span class="radio-bar" title="보고서 확인"></span>
								<p>보고서 확인</p>
							</a>
						</li>
						<li class="progressbar bar04">
							<a class="${item.REPRT_STATUS eq 55 ? "active" : "" }">
								<span class="radio-bar" title="완료"></span>
								<p>완료</p>
							</a>
						</li>
					</ul>
				</div>
			</div>
			</c:forEach>
		</div>
	</div>
		<!-- //CMS 끝 -->
</div>
<!-- //contents -->
<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="modal-action01">
	<h2>지원요청</h2>
	<div class="modal-area">
		<form action="">
			<div class="modal-alert">
				<p>능력개발전담주치의에게 지원 요청 하시겠습니까?</p>
			</div>
			<div class="basic-search-wrapper">
				<div class="one-box mt0">
					<dl class="pl0">
						<dt style="display:none">
							<label for="modal-textfield01"></label>
						</dt>
						<dd>
							<select id="modal-textfield01" name="" class="w100">
								<option value="">HRD 기초 컨설팅, 컨설팅 단계</option>
							</select>
						</dd>
					</dl>
				</div>
				<div class="one-box">
					<dl class="pl0">
						<dt style="display:none">
							<label for="modal-textfield02"></label>
						</dt>
						<dd>
							<textarea id="modal-textfield02" name="" placeholder="문의 사항"></textarea>
						</dd>
						</dl>
				</div>
			</div>
			<div class="btns-area">
				<button type="button" class="btn-m02 round01 btn-color03">
					<span>확인</span>
				</button>
	 			<button type="button" class="btn-m02 round01 btn-color02">
	 				<span>취소</span>
				</button>
			</div>
		</form>
	</div>
 	<button type="button" class="btn-modal-close">모달 창 닫기</button>
</div>
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
