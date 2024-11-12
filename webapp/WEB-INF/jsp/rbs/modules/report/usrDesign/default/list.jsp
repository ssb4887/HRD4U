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

<script>
	var context = location.pathname.split('/')[1]
	var contextPath = context == 'web' ? '' : `/${context}`;

	const btnIng = (idx) => {
		const ingBtn = document.getElementById('btn-ing');
		const parameters = new URLSearchParams(window.location.search);
		const mId = parameters.get('mId');
		window.location.href = contextPath + '/web/analysis/input.do?mId='+mId+'&rsltAnalsIdx='+idx+"&mode=m";
	}
</script>

	<div class="contents-area">
		<div class="table-type02 horizontal-scroll">
			<table>
				<caption>
					분류 관리 적극 발굴 정보표 : No, 제목, 등록자, 등록일에 관한 정보표
				</caption>
				<colgroup>
					<col style="width: 10%" />
					<col style="width: 15%" />
					<col style="width: 15%" />
					<col style="width: 15%" />
					<col style="width: 10%" />
					<col style="width: 15%" />
					<col style="width: 10%" />
					<col style="width: 10%" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">No</th>
						<th scope="col">소속기관</th>
						<th scope="col"><itui:objectItemName itemId="trCorpNm" itemInfo="${itemInfo}" /></th>
						<th scope="col"><itui:objectItemName itemId="tpNm" itemInfo="${itemInfo}" /></th>
						<th scope="col"><itui:objectItemName itemId="trTme" itemInfo="${itemInfo}" /></th>
						<th scope="col"><itui:objectItemName itemId="bizSe" itemInfo="${itemInfo}" /></th>
						<th scope="col">등록일</th>
						<th scope="col"><itui:objectItemName itemId="status" itemInfo="${itemInfo}" /></th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${empty list}">
					<tr>
						<td colspan="8" class="bllist">
							<spring:message code="message.no.list" />
						</td>
					</tr>
				</c:if>
				<c:set var="listIdxName" value="${settingInfo.idx_name}" />
				<c:set var="listColumnName" value="${settingInfo.idx_column}" />
				<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
					<c:forEach var="listDt" items="${list}" varStatus="i">
						<c:set var="listKey" value="${listDt[listColumnName]}" />
						<tr id="column">
							<td class="num">
								<c:out value="${listNo}" />
							</td>
							<td class="tpNm">
								 <c:out value="${listDt.INSTT_NAME}" />
							</td>
							<td class="trCorpNm">
								<a href="<c:out value="${URL_VIEW}"/>&${listIdxName}=${listKey}" class="fn_btn_view">
									<strong class="point-color01">	
										<itui:objectView itemId="trCorpNm" itemInfo="${itemInfo}" objDt="${listDt}" />
									</strong>
								</a>
							</td>
							<td class="tpNm">
								<itui:objectView itemId="tpNm" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>	
							<td>
								1차
							</td>
							<td class="trResultReprtSe">
								<c:out value="${listDt.TR_RESULT_REPRT_SE eq '1' ? '만족도 조사' : '현업적용'}" />
							</td>
							<td class="regiDate">
								<c:out value="${fn:substring(listDt.REGI_DATE,0,10)}" />
							</td>
							<td>
								<c:choose>
									<c:when test="${listDt.STATUS eq '0'}">
										<button type="button" class="btn-ing btn-m02 btn-color03" onclick="btnIng(${listDt.RSLT_ANALS_IDX});" >
											작성중
										</button>
									</c:when>
									<c:otherwise>
										신청
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<c:set var="listNo" value="${listNo - 1}" />
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="paging-navigation-wrapper">
			<form action="list.do" name="pageForm" method="get">
				<!-- 페이징 네비게이션 -->
				<p class="paging-navigation" onclick="paginationHandler('pageForm')">
					<pgui:pagination listUrl="${contextPath}/dct/analysis/list.do?mId=${mId}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}" />
				</p>
				<!-- //페이징 네비게이션 -->
			</form>
		</div>
	</div>

<!-- //CMS 끝 -->
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>