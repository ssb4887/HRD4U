<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/view.jsp"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
	<div id="cms_board_article" class="contents-wrapper">
		<h3 class="title-type02 h3_textbox2">최근 직업훈련 실시 현황 상세보기</h3>
		<div class="one-box space">
			<div class="half-box half-box-margin">
<!-- 				<form id="detailViewForm" name="detailViewForm" method="post" target="list_target"> -->
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
					<table id="tableTraing">
						<caption><c:out value="${settingInfo.list_title}"/> 수정</caption>
						<colgroup>
							<col style="width:30%">
							<col style="width:35%">
							<col style="width:35%">
						</colgroup>
						<tbody>
							<itui:itemSelectModify indutyCd="${indutyCd}" year="${year}" objDt="${yearList}" itemList="${industCdList}" selectYear="year" itemInfo="${itemInfo}" itemId="indutyCode" moduleId="traing"/>
							<tr>
								<th scope="col"><itui:objectItemName itemId="range" itemInfo="${itemInfo}"/></th>
								<th scope="col"><itui:objectItemName itemId="corp" itemInfo="${itemInfo}"/>(개)</th>
								<th scope="col"><itui:objectItemName itemId="tot" itemInfo="${itemInfo}"/>(명)</th>
							</tr>
							<itui:objectInputView indutyCd="${indutyCd}" year="${year}" objDt="${dt}" selectClass="traing" tableId="tableTraing" idx="1" submitType="${submitType}"/>
						</tbody>
					</table>
					</div>
					<div class="btns-area mt60">

						<a href="<c:out value="${URL_LISTMODIFY}"/>" class="btn-m01 btn-color05 fn_btn_modify"><span>수정</span></a>
						<a href="<c:out value="${URL_DELETEPROC}"/>" class="btn-m01 btn-color02 fn_btn_delete"><span>삭제</span></a>
						<a href="<c:out value="${URL_LIST}"/>" class="btn-m01 btn-color01">목록</a>
					</div>
<!-- 				</form> -->
			</div>
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>