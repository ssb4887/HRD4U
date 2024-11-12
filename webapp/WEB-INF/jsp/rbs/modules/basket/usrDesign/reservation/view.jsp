<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}" />
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/view.jsp" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />
<div id="cms_board_article">
	<c:set var="exceptIdStr">제외할 항목id를 구분자(,)로 구분하여 입력(예:name,notice,subject,file,contents,listImg)</c:set>
	<c:set var="exceptIds" value="${fn:split(exceptIdStr,',')}" />


	<c:set var="summary">
		<itui:objectItemName itemInfo="${itemInfo}" itemId="subject" />, <spring:message
			code="item.reginame1.name" />, <spring:message
			code="item.regidate1.name" />, <spring:message
			code="item.board.views.name" />, <c:if test="${useFile}">
			<spring:message code="item.file.name" />, </c:if>
		<itui:tableSummary items="${items}" itemOrder="${itemOrder}"
			exceptIds="${exceptIds}" />
		<spring:message code="item.contents.name" />을 제공하는 표</c:set>

	<c:set var="summary">
		<itui:tableSummary items="${items}" itemOrder="${itemOrder}"
			exceptIds="${exceptIds}" />을 제공하는 표</c:set>

	<table class="tbViewA" summary="${summary}">
		<caption>상세보기</caption>
		<colgroup>
			<col style="width: 150px;" />
			<col />
		</colgroup>
		<tbody>
			<itui:itemViewAll itemInfo="${itemInfo}"
				itemOrderName="${submitType}_order" exceptIds="${exceptIds}" />
		</tbody>
	</table>


	<table class="tbViewA" summary="${summary}">
		<caption>상세보기</caption>
		<colgroup>
			<col style="width: 170px">
			<col>
			<col style="width: 170px">
			<col>
		</colgroup>
		<tbody>
			<tr>
				<itui:itemView itemId="sampleItemId" itemInfo="${itemInfo}"
					colspan="3" />
			</tr>
			<tr>
				<itui:itemView itemId="sampleItemId" itemInfo="${itemInfo}"
					colspan="3" />
			</tr>
			<tr>
				<itui:itemView itemId="sampleItemId" itemInfo="${itemInfo}"
					colspan="3" />
			</tr>
			<tr>
				<itui:itemView itemId="sampleItemId" itemInfo="${itemInfo}"
					colspan="3" />
			</tr>
			<tr>
				<itui:itemView itemId="sampleItemId" itemInfo="${itemInfo}" />
				<itui:itemView itemId="sampleItemId" itemInfo="${itemInfo}" />
			</tr>
			<tr>
				<itui:itemView itemId="sampleItemId" itemInfo="${itemInfo}" />
				<itui:itemView itemId="sampleItemId" itemInfo="${itemInfo}" />
			</tr>
			<tr>
			</tr>
		</tbody>
	</table>
	
<!--  자동 분류 -->
<!--  자동 분류 end -->

<!--  수동 분류 -->
<!--  수동분류 end -->

<!--  해시 태그 -->
<!--  해시 태그 end -->

	
	


<!-- 기업 훈련 실시 이력 -->
	<table>
		<tr>
			<th>No</th>
			<th>훈련과정명</th>
			<th>훈련방법</th>
			<th>훈련기간</th>
		</tr>
		<c:forEach var="item" items="${trList}">
			<tr>
				<td>${item.ifSeq}</td>
				<td>${item.trPrNm}</td>
				<td>${item.trMethCd}</td>
				<td>${item.trStaDt}~ ${item.trEndDt}</td>
			</tr>
		</c:forEach>

	</table>
<!-- 기업 훈련 실시 이력 end-->
	
	
<!--  컨설팅 실시 이력 -->
<!--  컨설팅 실시 이력 end -->

<!--  채용정보 -->
<!--  채용정보 end -->

<!--  재무정보 -->
<!--  재무정보 end -->
	
	<div class="btnList">
		<span class="btnBc"><a href="<c:out value="${URL_LIST}"/>"
			class="fn_btn_list">목록</a></span>
		<c:if test="${mngAuth || wrtAuth && dt.AUTH_MNG == '1'}">
			<span class="btnBc orange"><a
				href="<c:out value="${URL_IDX_MODIFY}"/>" class="fn_btn_modify">수정</a></span>
			<span class="btnBc gray"><a
				href="<c:out value="${URL_IDX_DELETEPROC}"/>" class="fn_btn_delete">삭제</a></span>
		</c:if>
	</div>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>
