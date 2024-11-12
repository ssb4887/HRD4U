<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>							
<c:set var="searchFormId" value="fn_calendarSearchForm"/>							
<c:set var="listFormId" value="fn_calendarListForm"/>						
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/>
<c:set var="curPage" value="${empty param.page ? 1 : param.page }" />	
<c:set var="start" value="${(((curPage-1)/5.0)-(((curPage-1)/5.0)%1))*5+1 }" />
<c:set var="end" value="${(((curPage-1)/5.0)-(((curPage-1)/5.0)%1))*5+5 }" />
<%
	String[] cnslTypes = {"-", "사업주훈련", "일반직무전수 OJT", "과제수행 OJT", "심층진단", "훈련체계수립", "현장활용" };
	pageContext.setAttribute("cnslTypes", cnslTypes);
%>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>	
		<jsp:param name="searchFormId" value="${searchFormId}"/>				
		<jsp:param name="listFormId" value="${listFormId}"/>					
	</jsp:include>
</c:if>
<div class="contents-wrapper">
<!-- CMS 시작 -->
	<jsp:include page="../header/${type_name }.jsp" flush="false">
		<jsp:param name="usertype_idx" value="${usertype_idx }" />
		<jsp:param name="cPath" value="program" />
	</jsp:include>
	<c:if test="${not (usertype_idx eq 10) }">
	<div class="tabmenu-wrapper01 depth6">
		<ul>
			<li>
				<a href="${contextPath}/${siteId }/taskhub/program/hrdbsis.do?mId=48" >기업HRD이음컨설팅</a>
			</li>
			<li>
				<a href="${contextPath}/${siteId }/taskhub/program/cnsl.do?mId=48&type=cnsl" class="${param.type eq 'cnsl' ? 'active' : '' }">심화단계 컨설팅</a>
			</li>
			<li>
				<a href="${contextPath}/${siteId }/taskhub/program/cnsl.do?mId=48&type=custom" class="${param.type eq 'custom' ? 'active' : '' }">맞춤과정개발</a>
			</li>
			<li>
				<a href="${contextPath}/${siteId }/taskhub/program/sojt.do?mId=48" >체계적현장훈련(S-OJT)</a>
			</li>
			<li>
				<a href="${contextPath}/${siteId }/taskhub/program/clinic.do?mId=48">능력개발클리닉</a>
			</li>
		</ul>
	</div>
	</c:if>
	<div class="contents-area02">
		<form action="${contextPath }/${siteId}/taskhub/program/cnsl.do?mId=48" method="GET" id="main">
			<legend class="blind">HRD 기초 컨설팅 검색양식</legend>
			<input type="hidden" name="mId" value="48" />
			<input type="hidden" name="type" value="${param.type }" />
			<input type="hidden" name="page" id="page" value="1" />
			<div class="basic-search-wrapper">
				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label for="textfield01">사업장명</label>
							</dt>
							<dd>
								<input type="text" id="textfield01" name="corp_name" value="${param.corp_name }" title="사업장명 입력" placeholder="사업장명">
							</dd>
						</dl>
					</div>
					<div class="half-box">
						<dl>
							<dt>
								<label for="textfield02">소속기관</label>
							</dt>
							<dd>
								<input type="text" id="textfield02" name="instt_name" value="${param.instt_name }" title="소속기관 입력" placeholder="소속기관">
							</dd>
						</dl>
					</div>
				</div>
				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label for="textfield03">고용보험 관리번호</label>
							</dt>
							<dd>
								<input type="text" id="textfield03" name="bpl_no" value="${param.bpl_no }" title="고용보험 관리번호 입력" placeholder="고용보험 관리번호">
							</dd>
						</dl>
					</div>
					<div class="half-box">
						<dl>
							<dt>
								<label for="textfield04">경과일</label>
							</dt>
							<dd>
								<input type="text" id="textfield04" name="passed" value="${param.passed }" title="경과일 입력" placeholder="경과일" class="w-plus50 mr10">
								<p class="word-type02">일 이상</p>
							</dd>
						</dl>
					</div>
				</div>
			</div>
			<div class="btns-area">
				<button type="submit" class="btn-search02">
					<img src="../../../assets/img/icon/icon_search03.png" alt="">
					<strong>검색</strong>
				</button>
			</div>
			
		</form>
	</div>
	<div class="contents-area">
		<div class="title-wrapper">
			<p class="total fl">
				총 <strong>${list[0].TOTAL_COUNT}</strong> 건 ( ${empty param.page ? '1' : param.page } / ${list[0].TOTAL_PAGE } 페이지 ) <a href="#" class="word-linked-notice02" id="open-modal01"></a>
			</p>
			<div class="fr">
		    	<a href="${contextPath}/${siteId}/taskhub/program/cnslExcel.do?mId=${param.mId}
		    	<c:if test="${not empty param.corp_name}">&corp_name=${param.corp_name}</c:if>
		    	<c:if test="${not empty param.instt_name}">&instt_name=${param.instt_name}</c:if>
		    	<c:if test="${not empty param.bpl_no}">&bpl_no=${param.bpl_no}</c:if>
		    	<c:if test="${not empty param.passed}">&passed=${param.passed}</c:if>
		    	<c:if test="${not empty param.type}">&type=${param.type}</c:if>" class="btn-m01 btn-color01">엑셀다운로드</a>
		    </div>
		</div>
		<div class="table-type01 horizontal-scroll">
			<table>
				<caption>HRD 기초 컨설팅표 : 연번, 고용보험 관리번호, 사업장명, 소속기관, 경과일, HRD 기초 진단, 설문조사, 기초 컨설팅에 관한 정보 제공표</caption>
				<colgroup>
					<col style="width: 5%">
					<col style="width: 12%">
					<col style="width: 22%">
					<c:if test="${usertype_idx > 31 }">
					<col style="width: 12%">
					<col style="width: 5%">
					<col style="width: 8%">
					</c:if>
					<c:if test="${usertype_idx eq 30 or usertype_idx  eq 31 }">
					<col style="width: 5%">
					<col style="width: 15%">
					</c:if>
					<col style="width: 12%">
					<col style="width: 12%">
					<col style="width: 12%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">연번</th>
						<th scope="col">고용보험 관리번호</th>
						<th scope="col">사업장명</th>
						<c:if test="${usertype_idx > 31 }">
						<th scope="col">소속기관</th>
						</c:if>
						<th scope="col">경과일</th>
						<th scope="col">종류</th>
						<th scope="col">접수</th>
						<th scope="col">보고서 승인</th>
						<th scope="col">비용 처리</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list }" var="item">
					<tr>
						<td><c:out value="${item.TOTAL_COUNT-item.RN + 1 }" /></td>
						<td><c:out value="${item.BPL_NO }" /></td>
						<td><c:out value="${item.CORP_NAME }" /></td>
						<c:if test="${usertype_idx > 31 }">
						<td><c:out value="${empty item.INSTT_NAME ? '-' : item.INSTT_NAME}" /></td>
						</c:if>
						<td>
							<c:out value="D+${empty item.PASSED ? 0 : item.PASSED}" />
						</td>
						<td>
							<c:out value="${item.CNSL_TYPE <= 3 ? '과정개발' : '심화' }" /><br>
							<c:out value="${cnslTypes[item.CNSL_TYPE] }" />
						</td>
						<td>
							<c:choose>
								<c:when test="${empty item.CNSL_STATUS }">
									<span>-</span>
								</c:when>
								<c:otherwise>
									<a href="${contextPath }/${siteId }/consulting/${siteId eq 'web' ? 'cnslListAll' : 'viewAll' }.do?mId=${siteId eq 'dct' ? '124' : '95' }&${siteId eq 'web' ? 'is_bplNo' : 'cnslIdx' }=${siteId eq 'web' ? item.BPL_NO : item.CNSL_IDX}" data-cnsl="${item.CNSL_IDX}">									
										<c:choose>
											<c:when test="${(item.CNSL_STATUS eq 55) or (item.CNSL_STATUS eq 70) or (item.CNSL_STATUS eq 72) or (item.CNSL_STATUS eq 75)}">
												<c:out value="${item.CNSL_DATE }" />
											</c:when>
											<c:when test="${item.CNSL_STATUS eq 80 }">
												<c:out value="${item.CNSL_DATE }" />
											</c:when>
											<c:otherwise>
												<c:out value="진행 중" />
											</c:otherwise>
										</c:choose>
									</a>
								</c:otherwise>
							</c:choose>
							<c:set var="passed" value="${item.PASSED >= 10 ? 3 : item.PASSED >= 7 ? 2 : item.PASSED >= 5 ? 1 : 0 }" />
							<c:if test="${(item.NOW == 'cnsl') and (item.PASSED >= 5)}">
							<p>
								<c:forEach begin="1" end="${passed }">
									<span class="fire"></span>
								</c:forEach>
							</p> 
							</c:if>
						</td>
						<td>
							<c:choose>
								<c:when test="${empty item.REPORT_STATUS }">
									<span>-</span>
								</c:when>
								<c:otherwise>
									<a href="${contextPath }/${siteId }/consulting/viewAll.do?mId=${siteId eq 'dct' ? '124' : '95' }&cnslIdx=${item.CNSL_IDX }#tab4" >
										<c:choose>
											<c:when test="${(item.REPORT_STATUS eq 55) or (item.REPORT_STATUS eq 70) or (item.REPORT_STATUS eq 72) or (item.REPORT_STATUS eq 75)}">
												<c:out value="${item.REPORT_DATE }" />
											</c:when>
											<c:when test="${item.REPORT_STATUS eq 80 }">
												<c:out value="${item.REPORT_DATE }" />
											</c:when>
											<c:otherwise>
												<c:out value="${confirmProgress.get(item.REPORT_STATUS) }" />
											</c:otherwise>
										</c:choose>
									</a>
								</c:otherwise>
							</c:choose>
							<c:set var="passed" value="${item.PASSED >= 10 ? 3 : item.PASSED >= 7 ? 2 : item.PASSED >= 5 ? 1 : 0 }" />
							<c:if test="${(item.NOW == 'report') and (item.PASSED >= 5)}">
							<p>
								<c:forEach begin="1" end="${passed }">
									<span class="fire"></span>
								</c:forEach>
							</p> 
							</c:if>
						</td>
						<td>
							<c:choose>
								<c:when test="${empty item.COST_STATUS }">
									<span>-</span>
								</c:when>
								<c:otherwise>
									<a href="${contextPath }/${siteId }/${siteId eq 'web' ? 'develop' : 'developDct' }/support_list_form.do?mId=${siteId eq 'web' ? '60' : '63' }" >
										<c:choose>
											<c:when test="${(item.COST_STATUS eq 55) or (item.COST_STATUS eq 70) or (item.COST_STATUS eq 72) or (item.COST_STATUS eq 75)}">
												<c:out value="${item.COST_DATE }" />
											</c:when>
											<c:when test="${item.COST_STATUS eq 80 }">
												<c:out value="${item.COST_DATE }" />
											</c:when>
											<c:otherwise>
												<c:out value="${confirmProgress.get(item.COST_STATUS) }" />
											</c:otherwise>
										</c:choose>
									</a>
								</c:otherwise>
							</c:choose>
							<c:set var="passed" value="${item.PASSED >= 10 ? 3 : item.PASSED >= 7 ? 2 : item.PASSED >= 5 ? 1 : 0 }" />
							<c:if test="${(item.NOW == 'cost') and (item.PASSED >= 5)}">
							<p>
								<c:forEach begin="1" end="${passed }">
									<span class="fire"></span>
								</c:forEach>
							</p> 
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="paging-navigation-wrapper">
			<!-- 페이징 네비게이션 -->
			<p class="paging-navigation">
				<a href="#" data-page="1" class="btn-first btn-page">맨 처음 페이지로 이동</a>
				<a href="#" data-page="${curPage - 1 eq 0 ? 1 : curPage-1}" class="btn-preview btn-page">이전 페이지로 이동</a>
				<c:forEach var="i" begin="${start }" end="${end >= list[0].TOTAL_PAGE ? list[0].TOTAL_PAGE : end }">
					<c:choose>
						<c:when test="${i eq curPage }">
							<strong>${i }</strong>
						</c:when>
						<c:otherwise>
							<a href="#" data-page="${i}" class="btn-page">${i }</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<a href="#" data-page="${curPage + 1 eq list[0].TOTAL_PAGE+1 ? list[0].TOTAL_PAGE : curPage+1}" class="btn-next btn-page">다음 페이지로 이동</a>
				<a href="#" data-page="${list[0].TOTAL_PAGE}" class="btn-last btn-page">맨 마지막 페이지로 이동</a>
			</p>
			<!-- //페이징 네비게이션 -->
		</div>
	</div>
		<!-- //CMS 끝 -->
</div>
<form id="mover" method="POST">
	<input type="hidden" id="idx" name="idx" value="">
	<input type="hidden" id="bsc" name="bsc" value="">
	<input type="hidden" id="rslt" name="rslt" value="">
</form>
<!-- //contents -->
<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="modal-action01">
	<h2>HRD 기초 컨설팅</h2>
	<div class="modal-area">
		<div class="modal-alert type02">
			<p>
				진행 : 기초진단 -> 설문조사 -> 기초진단<br>
				경과일 : <span class="fire"></span> (5일),
				<span class="fire"></span><span class="fire"></span> (7일),
				<span class="span-mobile-br"></span>
				<span class="fire"></span><span class="fire"></span><span class="fire"></span> (10일)
			</p>
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
	$('span#item').text($("select#modal-textfield01 option:selected").val());
	$("select#modal-textfield01").on("change", function() {
		console.log($(this).val());
		$('span#item').text($(this).val());
	})
	$('button#cancel').on("click", function() {
		$("#modal-action01").hide();
		$(".mask").fadeOut(150);
	})
	$('button#submit').on('click', function() {
		const contextPath = '${contextPath}'
		const text = '{{'+$('select#modal-textfield01 option:selected').val() + '}}' + $('textarea#modal-textfield02').val() 
		$.ajax({
			url: `\${contextPath}/web/taskhub/req.do?mId=48`,
			type: 'POST',
			contentType: 'application/x-www-form-urlencoded',
			data: {cn: text},
			success: function(response) {
				alert('지원요청 등록 완료')
			},
			error: function(xhr, status, error) {
				console.error('Error occurred:', error);
			}
		});
		$("#modal-action01").hide();
		$(".mask").fadeOut(150);
		cleanup();
	})
	$('.btn-page').click(function(e) {
		e.preventDefault();
		$('form#main input#page').val($(this).data('page'))
		$('form#main').submit();
	})
 });
 
 function cleanup() {
	 $('textarea#modal-textfield02').val('');
 }
 
</script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
