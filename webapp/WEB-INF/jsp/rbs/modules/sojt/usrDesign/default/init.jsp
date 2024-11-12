<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}" />
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}" />
<c:set var="searchFormId" value="fn_techSupportSearchForm" />
<c:set var="listFormId" value="fn_techSupportListForm" />
<c:set var="inputWinFlag" value="" />
<%
	/* 등록/수정창 새창으로 띄울 경우 사용 */
%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}" />
<%
	/* 수정버튼 class */
%>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp" />
		<jsp:param name="searchFormId" value="${searchFormId}" />
		<jsp:param name="listFormId" value="${listFormId}" />
	</jsp:include>
</c:if>
<style>
.redgrad {
	background: rgb(131, 58, 180);
	background: linear-gradient(90deg, rgba(131, 58, 180, 1) 0%,
		rgba(253, 29, 29, 1) 50%, rgba(252, 176, 69, 1) 100%);
	-webkit-background-clip: text;
	color: transparent;
	line-height: 1.2;
	padding: 5px 0;
}
</style>
<div id="cms_board_article" class="subConBox">
	<!-- contents  -->
	<article>
		<div class="contents" id="contents">
			<div class="contents-wrapper">

				<!-- CMS 시작 -->

				<div class="contents-area">
					<div class="contents-box pl0">
						<dl class="introduction-wrapper001">
							<dt>
								<img src="${contextPath}${imgPath}/icon/icon_symbol02.png"
									alt="" /> <strong>체계적 현장훈련(S-OJT)이란?</strong>
							</dt>
							<dd>
								중소기업 근로자를 대상으로 <strong class="point-color08">기업맞춤형
									현장훈련(기술숙련, 현장문제 해결)</strong>을 지원합니다.<span class="span-br"></span> 기업HRD이음컨설팅을
								받은 기업이라면 누구나 참여 가능합니다. 지금 바로 신청하세요.
							</dd>
						</dl>
					</div>
					<c:choose>
						<c:when test="${not empty amIapplying}">
							<div class="btns-area">
								<h2 class="redgrad" style="font-size: 32px;">${amIapplying[0].CORP_NAME }의 ${amIapplying[0].CORP_PIC_NM }님이 <span style="margin: 0 0px;">S-OJT를 
								<fmt:parseDate var="chkDate" value="${amIapplying[0].REGI_DATE}" pattern="yyyy.MM.dd" />
								<fmt:formatDate value="${chkDate}" pattern="yyyy.MM.dd(E)"/></span>에 신청했습니다.<br/>
								현재 진행 상태는 
								<span>
								<c:choose>
									<c:when test="${amIapplying[0].CONFM_STATUS eq 10}">
									[신청]
									</c:when>
									<c:when test="${amIapplying[0].CONFM_STATUS eq 20}">
									[회수]
									</c:when>
									<c:when test="${amIapplying[0].CONFM_STATUS eq 30}">
									[접수]
									</c:when>
									<c:when test="${amIapplying[0].CONFM_STATUS eq 50}">
									[접수]
									</c:when>
									<c:when test="${amIapplying[0].CONFM_STATUS eq 55}">
									[승인]
									</c:when>
									<c:when test="${amIapplying[0].CONFM_STATUS eq 40}">
									[반려]
									</c:when>
								</c:choose>
							</span>입니다.</h2>
							</div>
							<c:if test="${amIapplying[0].CONFM_STATUS eq 55}">
								<div class="btns-area" style="margin-top: 80px;">
									<button type="button" id="open-modal01"
										class="btn-b01 round01 btn-color03 left"
										onclick="location.href='${contextPath}/web/develop/develop_standard_info.do?mId=59'">
										<span>과정개발 신청</span> <img
											src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt=""
											class="arrow01">
									</button>
								</div>
							</c:if>
						</c:when>
						<c:otherwise>
							<div class="contents-box pl0">
								<div class="title-wrapper">
									<p class="total fl">
										총 <strong>${list[0].TOTAL_COUNT}</strong> 건 ( ${empty param.page ? 1 : param.page }
										/ ${list[0].TOTAL_PAGE } 페이지 ) <a href="#"
											class="word-linked-notice02" id="open-modal01"></a>
									</p>
								</div>
								<div class="table-type01 horizontal-scroll">
									<form id="main"
										action="${contextPath }/web/sojt/applyForm.do?mId=110"
										method="POST">
										<input type="hidden" name="mId" value="110">
										<table class="width-type02">
											<caption>기업HRD이음컨설팅 소개 정보표 : 선택, 기초진단서 발급번호, 발급일자,
												업체명, 사업장번호에 관한 정보 제공표</caption>
											<colgroup>
												<col style="width: 5%" />
												<col style="width: 5%" />
												<col style="width: 30%" />
												<col style="width: 20%" />
												<col style="width: 20%" />
												<col style="width: 20%" />
											</colgroup>
											<thead>
												<tr>
													<th scope="col">선택</th>
													<th scope="col">연번</th>
													<th scope="col">발급 번호</th>
													<th scope="col">발급 일자</th>
													<th scope="col">기업명</th>
													<th scope="col">고용보험 관리번호</th>
												</tr>
											</thead>
											<tbody>
												<c:if
													test="${(empty list && login eq true) or (userType eq 10)}">
													<tr>
														<td colspan="6" class="bllist"><span class="redgrad">체계적
																현장훈련을 신청하려면 기업HRD이음컨설팅이 필요합니다.</span></td>
													</tr>
												</c:if>

												<c:if test="${login eq false}">
													<script>
														alert("로그인 후 기업HRD이음컨설팅 신청이 가능합니다.");
														window.location.href = `${contextPath}/web/login/login.do?mId=3`;
													</script>
												</c:if>

												<c:if test="${login eq true && userType ne 10}">
													<c:forEach var="listDt" items="${list}" varStatus="status">
														<tr>
															<td><input type="radio" id="radio01${status.index}"
																name="id" value="${listDt.BSISCNSL_IDX}" class="radio-type01"
																<c:if test="${empty amIapplying }"> ${listDt.BSISCNSL_IDX == amIapplying[0].BSISCNSL_IDX ? 'selected' : '' }</c:if>></td>
															<td>${list[0].TOTAL_COUNT-listDt.RN +1 }</td>
															<td><label for="radio01${status.index}">${listDt.ISSUE_NO }</label></td>
															<td><fmt:formatDate pattern="yyyy-MM-dd"
																	value="${listDt.ISSUE_DATE}" /></td>
															<td><span class="point-color01">${listDt.CORP_NAME }</span></td>
															<td>${listDt.BPL_NO }</td>
														</tr>
														<c:set var="listNo" value="${listNo - 1}" />
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</form>
								</div>
								<div class="paging-navigation-wrapper" style="margin: 20px 0">
									<!-- 페이징 네비게이션 -->
									<form id="page">
										<input type="hidden" name="mId" value="110"> <input
											type="hidden" name="page" id="page">
									</form>
									<c:set var="curPage"
										value="${empty param.page ? 1 : param.page }" />
									<c:set var="start"
										value="${(((curPage-1)/5.0)-(((curPage-1)/5.0)%1))*5+1 }" />
									<c:set var="end"
										value="${(((curPage-1)/5.0)-(((curPage-1)/5.0)%1))*5+5 }" />
									<c:set var="total_page"
										value="${empty list[0].TOTAL_PAGE ? 1 : list[0].TOTAL_PAGE}" />
									<p class="paging-navigation" style="margin-top: 0px">
										<a href="#" data-page="1" class="btn-first btn-page">맨 처음
											페이지로 이동</a> <a href="#"
											data-page="${curPage - 1 eq 0 ? 1 : curPage-1}"
											class="btn-preview btn-page">이전 페이지로 이동</a>
										<c:forEach var="i" begin="${start }"
											end="${end >= total_page ? total_page : end }">
											<c:choose>
												<c:when test="${i eq curPage }">
													<strong>${i }</strong>
												</c:when>
												<c:otherwise>
													<a href="#" data-page="${i}" class="btn-page">${i }</a>
												</c:otherwise>
											</c:choose>
										</c:forEach>
										<a href="#"
											data-page="${curPage + 1 eq list[0].TOTAL_PAGE+1 ? list[0].TOTAL_PAGE : curPage+1}"
											class="btn-next btn-page">다음 페이지로 이동</a> <a href="#"
											data-page="${list[0].TOTAL_PAGE}" class="btn-last btn-page">맨
											마지막 페이지로 이동</a>
									</p>
									<!-- //페이징 네비게이션 -->
								</div>
								<c:if test="${login eq true && userType ne 10}">
								<div class="btns-area">
									<button type="button" class="btn-b01 round01 btn-color03 left"
										id="btn-apply">
										<span>신청하기</span> <img
											src="${contextPath}${imgPath}/icon/icon_arrow_right03.png"
											alt="" class="arrow01" />
									</button>
								</div>
								</c:if>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<!-- //CMS 끝 -->
			</div>
		</div>
	</article>
</div>
<!-- //contents  -->
<script>
	$(function() {
		$('.btn-page').click(function(e) {
			e.preventDefault();
			$('form#page input#page').val($(this).data('page'))
			$('form#page').submit();
		})
		
		$('button#btn-newApply').click(function(e) {
			var form = document.createElement("form");
			form.setAttribute("charset", "UTF-8");
			form.setAttribute("method", "Post");
			form.setAttribute("action", "${contextPath}/web/sojt/applyForm.do?mId=110");
			
			var bscIdx = document.createElement('input');
			bscIdx.setAttribute("type", "hidden");
			bscIdx.setAttribute("name", "id");
			bscIdx.setAttribute("value", $("#btn-newApply").val());
			
			form.appendChild(bscIdx);
			document.body.appendChild(form);
			form.submit();
		});
		
		$('button#btn-apply').click(function(e) {
			if($("input:radio[name='id']:checked").length<1){
				alert("하나의 기업HRD이음컨설팅을 선택하시기 바랍니다.");
				$(".radio-type01").focus();
				return false;
			}
			
			let selected = $('input.radio-type01:checked').val()
			if (selected) {
				$('form#main').submit()
			} else {
				alert('기업HRD이음컨설팅을 진행해주세요')
				window.location.href = `${contextPath}/web/diagnosis/apply.do?mId=53`;
			}

		})
	})
</script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>
