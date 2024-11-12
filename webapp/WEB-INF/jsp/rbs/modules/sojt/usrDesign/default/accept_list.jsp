<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>						
<c:set var="searchFormId" value="fn_calendarSearchForm"/>
<c:set var="listFormId" value="fn_calendarListForm"/>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/>
<c:set var="curPage" value="${empty param.page ? 1 : param.page }" />
<c:set var="start" value="${(((curPage-1)/5.0)-(((curPage-1)/5.0)%1))*5+1 }" />
<c:set var="end" value="${(((curPage-1)/5.0)-(((curPage-1)/5.0)%1))*5+5 }" />
<c:set var="total_page" value="${empty list[0].TOTAL_PAGE ? 1 : list[0].TOTAL_PAGE}" />
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
<div class="contents-area02">
	<form id="main" action="${contextPath }/${siteId}/agreement/list.do?mId=52" method="GET">
		<legend class="blind">협약 검색양식</legend>
		<input type="hidden" name="mId" value="52" />
		<input type="hidden" name="page" id="page" value="1" />
		<c:if test="${usertype_idx != 5 }">
		<div class="basic-search-wrapper">
			<c:if test="${usertype_idx >= 30 }">
			<div class="one-box">
				<div class="half-box">
					<dl>
						<dt>
							<label for="bpl_nm">사업장명</label>
						</dt>
						<dd>
							<input type="text" id="bpl_nm" name="bpl_nm" value="" title="사업장명 입력" placeholder="사업장명" value="${param.bpl_nm }">
						</dd>
					</dl>
				</div>
				<div class="half-box">
					<dl>
						<dt>
							<label for="bpl_no">고용보험 관리번호</label>
						</dt>
						<dd>
							<input type="text" id="bpl_no" name="bpl_no" value="${param.bpl_no }" title="고용보험 관리번호 입력" placeholder="고용보험 관리번호" >
						</dd>
					</dl>
				</div>
			</div>
			</c:if>
			<div class="one-box">
				<div class="half-box">
					<dl>
						<dt>
							<label>체결일</label>
						</dt>
						<dd>
							<div class="input-calendar-wrapper">
								<div class="input-calendar-area">
									<input type="text" id="sdate" name="sdate" class="sdate" title="시작일 입력" placeholder="시작일" value="${param.sdate }"/>
								</div>
								<div class="word-unit">~</div>
								<div class="input-calendar-area">
									<input type="text" id="edate" name="edate" class="edate" title="종료일 입력" placeholder="종료일" value="${param.edate }" />
								</div>
							</div>
						</dd>
					</dl>
				</div>
				<div class="half-box">
					<dl>
						<dt>
							<label>협약 체결</label>
						</dt>
						<dd>
							<div class="input-radio-wrapper ratio">
								<div class="input-radio-area">
									<input type="radio" class="radio-type01" id="signed" name="sign" value="Y" ${param.sign == 'Y' ? 'checked' : '' }>
									<label for="signed">체결</label>
								</div>
								<div class="input-radio-area">
									<input type="radio" class="radio-type01" id="nosigned" name="sign" value="N" ${param.sign == 'N' ? 'checked' : '' }>
									<label for="nosigned">미체결</label>
								</div>
							</div>
						</dd>
					</dl>
				</div>
			</div>
			<c:if test="${usertype_idx >= 40 }">
			<div class="one-box">
				<div class="half-box">
					<dl>
						<dt>
							<label for="instt">소속기관</label>
						</dt>
						<dd>
							<input type="text" id="instt" name="instt" value="${param.instt }" title="소속기관 입력" placeholder="소속기관">
						</dd>
					</dl>
				</div>
				<div class="half-box">
					<dl>
						<dt>
							<label for="center">지원기관</label>
						</dt>
						<dd>
							<input type="text" id="center" name="center" value="${param.center }" title="지원기관 입력" placeholder="지원기관">
						</dd>
					</dl>
				</div>
			</div>
			</c:if>
		</div>
		<div class="btns-area">
			<button type="submit" class="btn-search02">
				<img src="../../assets/img/icon/icon_search03.png" alt="">
				<strong>검색</strong>
			</button>
		</div>
		</c:if>
	</form>
</div>
<div id="cms_calendar_article"> 	
	<div class="title-wrapper">
		<p class="total fl">총 <strong>${ empty list ? '0' : list[0].TOTAL_COUNT }</strong> 건 ( ${empty param.page ? '1' : param.page } / ${ empty list ? '0' : list[0].TOTAL_PAGE } 페이지 ) </p>
	</div>
	<table class="tbListA" summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 제목 링크를 통해서 게시물 상세 내용으로 이동합니다.">
		<caption>협약표 : 체크박스, 협약번호, 사업연도, 지원기관, 소속기관, 고용보험 관리번호, 사업장명, 상태, 체결일, 완료일, 협약서, 승인에 관한 정보 제공표</caption>
		<colgroup id="main-colgroup">
			<col style="width: 17%">
			<col style="width: 17%">
			<col style="width: 19%">
			<col style="width: 17%">
			<col style="width: 19%">
			<col style="width: 16%">
			<col style="width: 13%">
			<c:if test="${usertype_idx eq 30 }">
			<col style="width: 16%">
			</c:if>
		</colgroup>
		<thead>
		<tr>
			<th scope="col">지원기관</th>
			<th scope="col">소속기관</th>
			<th scope="col">고용보험 관리번호</th>
			<th scope="col">기업명</th>
			<th scope="col">기업구분</th>
			<th scope="col">신청일자</th>
			<th scope="col" class="${usertype_idx eq 30 ? '' : 'end' }">현황</th>
			<c:if test="${usertype_idx eq 30 }">
			<th scope="col" class="end">확인</th>
			</c:if>
		</tr>
		</thead>
		<tbody class="alignC" id="main">
			<c:if test="${empty list}">
			<tr class="no-item">
				<td colspan="${usertype_idx eq 30 ? '8' : '7' }" class="bllist"><spring:message code="message.no.list"/></td>
			</tr>
			</c:if>
			<c:forEach var="listDt" items="${list}" varStatus="i">
			<tr class="real-item">
				<td>${empty listDt.RECOMEND_INSTT_NM ? '-' : listDt.RECOMEND_INSTT_NM }</td>
				<td>${empty listDt.INSTT_NAME ? '-' : listDt.INSTT_NAME}</td>
				<td data-key="bpl_no" data-value="${listDt.BPL_NO }"><a href="#" id="sojt-link">${empty listDt.BPL_NO ? '-' : listDt.BPL_NO }</a></td>	
				<td data-key="bpl_nm" data-value="${listDt.CORP_NAME }"><label for="sojt-link">${empty listDt.CORP_NAME ? '-' : listDt.CORP_NAME }</label></td>
				<td data-key="pri_sup_cd" data-value="${listDt.PRI_SUP_CD }">${empty listDt.PRI_SUP_CD ? '-' : listDt.PRI_SUP_CD eq 1 ? '대규모기업' : '우선지원기업' }</td>
				<td class="date">${empty listDt.REGI_DATE ? null : listDt.REGI_DATE}</td>
				<td class=""><span class="point-color01">${confirmProgress.get(listDt.CONFM_STATUS) }</span></td>
				<c:choose>
					<c:when test="${(usertype_idx eq 30) and (listDt.CONFM_STATUS eq 30)}">
						<td>
							<a href="${contextPath}/dct/sojt/acceptForm.do?mId=151&id=${listDt.SOJT_IDX }&status=30" class="redgrad" style="text-decoration: underline;">1차 승인 검토</a>
						</td>
					</c:when>
					<c:when test="${(usertype_idx eq 30) and (clsf_cd eq 'Y') and (listDt.CONFM_STATUS eq 50)}">
						<td>
							<a href="${contextPath}/dct/sojt/acceptForm.do?mId=151&id=${listDt.SOJT_IDX }&status=50" class="redgrad" style="text-decoration: underline;">최종 승인 검토</a>
						</td>
					</c:when>
					<c:otherwise>
						<td></td>
					</c:otherwise>
				</c:choose>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="paging-navigation-wrapper">
		<!-- 페이징 네비게이션 -->
		<p class="paging-navigation">
			<a href="#" data-page="1" class="btn-first btn-page">맨 처음 페이지로 이동</a>
			<a href="#" data-page="${curPage - 1 eq 0 ? 1 : curPage-1}" class="btn-preview btn-page">이전 페이지로 이동</a>
			<c:forEach var="i" begin="${start }" end="${end >= total_page ? total_page : end }">
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
<form id="apply">
	<input type="hidden" id="id" name="id" value="" />
</form>
<!-- //모달 창 -->
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
