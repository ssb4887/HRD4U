<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:set var="searchFormId" value="fn_techSupportSearchForm"/>
<c:set var="listFormId" value="fn_techSupportListForm"/>
<c:set var="inputFormId" value="fn_sampleInputForm"/>
<c:set var="inputWinFlag" value=""/><%/* 등록/수정창 새창으로 띄울 경우 사용 */%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/><%/* 수정버튼 class */%>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/modify.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
	<div id="cms_board_article" class="contents-wrapper">
		<h3 class="title-type02 h3_textbox">최근 직업훈련 지원 현황 수정</h3>
		<div class="one-box space">
			<div class="half-box half-box-margin">
				<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_LISTMODIFYPROC}"/>" target="submit_target" enctype="multipart/form-data">
				<%-- table summary, 항목출력에 사용 --%>
				<c:set var="exceptIdStr">제외할 항목id를 구분자(,)로 구분하여 입력(예:name,notice,subject,file,contents,listImg)</c:set>
				<c:set var="exceptIds" value="${fn:split(exceptIdStr,',')}"/>
				<%-- 
					table summary값 setting - 테이블 사용하지 않는 경우는 필요 없음
					디자인 문제로 제외한 항목(exceptIdStr에 추가했으나 table내에 추가되는 항목)은 수동으로 summary에 추가
					예시)
					<c:set var="summary"><itui:objectItemName itemInfo="${itemInfo}" itemId="subject"/>, <spring:message code="item.reginame1.name"/>, <spring:message code="item.regidate1.name"/>, <spring:message code="item.board.views.name"/>, <c:if test="${useFile}"><spring:message code="item.file.name"/>, </c:if><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/><spring:message code="item.contents.name"/>을 제공하는 표</c:set>
				--%>
				<c:set var="summary"><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/> 입력표</c:set>
					<div class="table-type02">
					<table id="tableSprt">
						<caption><c:out value="${settingInfo.list_title}"/> 수정</caption>
						<colgroup>
							<col style="width:30%">
							<col style="width:70%">
						</colgroup>
						<tbody>
							<itui:itemSelectModify indutyCd="${indutyCd}" year="${year}" objDt="${yearList}" itemList="${industCdList}" selectYear="year" itemInfo="${itemInfo}" itemId="indutyCode" moduleId="sprt"/>
							<tr>
								<th scope="col"><itui:objectItemName itemId="range" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></th>
								<th scope="col"><itui:objectItemName itemId="pay" itemInfo="${itemInfo}"/>(원)</th>
							</tr>
							<itui:objectInputModify indutyCd="${indutyCd}" year="${year}" objDt="${list}" selectClass="sprt" tableId="tableSprt" idx="2" submitType="${submitType}"/>
						</tbody>
					</table>
					</div>
					<div class="btns-area mt60">
						<button type="submit" class="btn-m01 btn-color05 depth3_1 fn_btn_modify">저장</button>
						<a href="${contextPath}/${crtSiteId}/traing/list.do?mId=40" class="btn-m01 btn-color01 depth3_2">목록</a>
					</div>
				</form>
			</div>
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>