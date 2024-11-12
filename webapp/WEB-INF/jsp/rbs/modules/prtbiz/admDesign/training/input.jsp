<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_sampleInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/input.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
	<div id="cms_board_article">
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
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
		
		<%-- 1. 전체 항목 출력하는 경우 --%>
		<%-- <table class="tbWriteA" summary="${summary}">
			<caption>
			글쓰기 서식
			</caption>
			<colgroup>
			<col style="width:150px;" />
			<col />
			</colgroup>
			<tbody>
				<itui:itemInputAll itemInfo="${itemInfo}" itemOrderName="${submitType}_order" exceptIds="${exceptIds}"/>
			</tbody>
		</table> --%>
		
		<%-- 2. 디자인에 맞게 필요한 항목만 출력하는 경우 --%>
		<table class="tbWriteA" summary="${summary}">
			<caption>
			글쓰기 서식
			</caption>
			<colgroup>
			<col style="width:150px;" />
			<col />
			<col style="width:150px;" />
			<col />
			</colgroup>
			<tbody>
				<tr>
					<itui:itemText itemId="name" itemInfo="${itemInfo}" colspan="3" objStyle="width:400px"/>
				</tr>
				<tr>
					<th scope="col"><itui:objectItemName itemId="prtbizIdx" itemInfo="${itemInfo}"/></th>
					<td colspan="3"><itui:objectSelect2 itemId="prtbizIdx" itemInfo="${itemInfo}" objDt="${prtbizList}" objVal="${submitType}" defVal="${dt}" idx="${prtbizIdx}"/></td>
				</tr>
				<tr>
					<th>작성자</th>
					<td colspan="3">${nameDt.NAME}</td>
				</tr>
				<tr>
					<itui:itemText itemId="crdns" itemInfo="${itemInfo}"/>
					<itui:itemText itemId="techCate" itemInfo="${itemInfo}"/>
				</tr>
				<tr>
					<itui:itemSelectClass itemId="ncsSclsCd" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<itui:itemText itemId="dcnt" itemInfo="${itemInfo}"/>
					<itui:itemText itemId="applyInduty" itemInfo="${itemInfo}"/>
				</tr>
				<tr>
					<itui:itemMultiFile itemId="file" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<itui:itemTextarea itemId="feat" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<itui:itemTextarea itemId="expectEffect" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<itui:itemTextarea itemId="goal" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<itui:itemTextarea itemId="target" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<th scope="row">훈련내용</th>
					<td colspan="3">
						<itui:objectTableView objDt="${objDt}" objVal="${submitType}"/>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="btnCenter">
			<input type="submit" class="btnTypeA fn_btn_submit" value="저장" title="저장"/>
			<input type=button value="취소"  class="btnTypeB fn_btn_cancel">
		</div>
		</form>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>