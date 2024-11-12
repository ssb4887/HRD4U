<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/view.jsp"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
	<div id="cms_board_article">
		<%-- table summary, 항목출력에 사용 --%>
		<c:set var="exceptIdStr">제외할 항목id를 구분자(,)로 구분하여 입력(예:name,notice,subject,file,contents,listImg)</c:set>
		<c:set var="exceptIds" value="${fn:split(exceptIdStr,',')}"/>
		<%-- 
			table summary값 setting - 테이블 사용하지 않는 경우는 필요 없음
			디자인 문제로 제외한 항목(exceptIdStr에 추가했으나 table내에 추가되는 항목)은 수동으로 summary에 추가
			예시)
			<c:set var="summary"><itui:objectItemName itemInfo="${itemInfo}" itemId="subject"/>, <spring:message code="item.reginame1.name"/>, <spring:message code="item.regidate1.name"/>, <spring:message code="item.board.views.name"/>, <c:if test="${useFile}"><spring:message code="item.file.name"/>, </c:if><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/><spring:message code="item.contents.name"/>을 제공하는 표</c:set>
		--%>
		<c:set var="summary"><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/>을 제공하는 표</c:set>
		
		<%-- 1. 전체 항목 출력하는 경우 --%>
		<%-- <table class="tbViewA" summary="${summary}">
			<caption>
			상세보기
			</caption>
			<colgroup>
			<col style="width:150px;" />
			<col />
			</colgroup>
			<tbody>
				<itui:itemViewAll itemInfo="${itemInfo}" itemOrderName="${submitType}_order" exceptIds="${exceptIds}"/>
			</tbody>
		</table> --%>
		
		<%-- 2. 디자인에 맞게 필요한 항목만 출력하는 경우 --%>
		<table class="tbViewA" summary="${summary}">
			<caption>
			상세보기
			</caption>
			<colgroup>
			<col style="width:150px;" />
			<col />
			<col style="width:150px;" />
			<col />
			</colgroup>
			<tbody>
				<tr>
					<td colspan="4">
						<itui:objectView itemId="name" itemInfo="${itemInfo}" objDt="${dt}" objVal="${dt[NAME]}" optnHashMap="${optnHashMap}"/>
					</td>
				</tr>
				<tr>
					<th scope="row">참여가능사업명</th>
					<td colspan="3"><itui:objectSelect2 itemId="prtbizIdx" itemInfo="${itemInfo}" objDt="${prtbizList}" objVal="${submitType}" defVal="${dt}" /></td>
				</tr>
				<tr>
					<th scope="row">작성자</th>
					<td>${dt.REGI_NAME}</td>
					<th scope="row">작성일</th>
					<td><fmt:formatDate value="${dt.REGI_DATE}" pattern="yyyy-MM-dd"/></td>
				</tr>
				<tr>
					<itui:itemView itemId="crdns" itemInfo="${itemInfo}" />
					<itui:itemView itemId="techCate" itemInfo="${itemInfo}" />
				</tr>
				<tr>
					<itui:itemView itemId="ncsSclsCd" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<itui:itemView itemId="dcnt" itemInfo="${itemInfo}" />
					<itui:itemView itemId="applyInduty" itemInfo="${itemInfo}"/>
				</tr>
				<tr>
					<itui:itemView itemId="file" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<itui:itemView itemId="feat" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<itui:itemView itemId="expectEffect" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<itui:itemView itemId="goal" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<itui:itemView itemId="target" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<th scope="row">훈련내용</th>
					<td colspan="3">
						<table id="tpSubContent">
							<tr>
								<th>번호</th>
								<th>교과목</th>
								<th>시간</th>
								<th>주요내용</th>
								<th>교수 기법</th>
							</tr>
							<c:set var="subIdxList" value="${fn:split(dt.SUB_NO, ', ')}" />
							<c:set var="subNameList" value="${fn:split(dt.SUB_NAME, ', ')}" />
							<c:set var="timeList" value="${fn:split(dt.TIME, ', ')}" />
							<c:set var="contentsList" value="${fn:split(dt.CONTENTS, ', ')}" />
							<c:set var="teachList" value="${fn:split(dt.TEACH_METHD, ', ')}" />
							<c:forEach var="subIdx" items="${subIdxList}" varStatus="i">
								<tr>
									<td>M<c:out value="${subIdx}" /></td>
									<td><c:out value="${subNameList[i.index]}" /></td>
									<td><c:out value="${timeList[i.index]}" /></td>
									<td><c:out value="${contentsList[i.index]}" /></td>
									<td><c:out value="${teachList[i.index]}" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="btnTopFull">
			<div class="right">
				<a href="<c:out value="${URL_MODIFY}"/>&tpIdx=${dt.TP_IDX}" class="btnTypeA">수정</a>
				<a href="<c:out value="${URL_DELETEPROC}&tpIdx=${dt.TP_IDX}"/>" title="삭제" class="btnTypeA fn_btn_delete">삭제</a>
				<a href="<c:out value="${URL_LIST}"/>" title="목록" class="btnTypeA fn_btn_write">목록</a>
			</div>
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>