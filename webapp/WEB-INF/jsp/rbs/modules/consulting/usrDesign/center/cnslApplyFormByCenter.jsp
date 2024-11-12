<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="inputFormId" value="fn_sampleInputForm" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}" />
		<jsp:param name="javascript_page" value="${moduleJspRPath}/input.jsp" />
		<jsp:param name="inputFormId" value="${inputFormId}" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />

<c:set var="today" value="<%=new java.util.Date()%>"/>
<c:set var="year"><fmt:formatDate value="${today}" pattern="yyyy" /></c:set>
<c:set var="month"><fmt:formatDate value="${today}" pattern="MM" /></c:set>
<c:set var="day"><fmt:formatDate value="${today}" pattern="dd" /></c:set>

		<%@ include file="../corp/cnslTypeC/cnslApplyFormTypeC.jsp"%>

	<div class="btns-right">
		<button type="button" class="btn-m01 btn-color03 depth3"
			onclick="saveByCenter('55')">승인</button>
		<a href="/web/consulting/cnslListAll.do?mId=95" ><button type="button" class="btn-m01 btn-color03 depth3"
			>목록</button></a>
	</div>
	
	
	<script type="text/javascript" src="${contextPath}${jsPath}/cnsl.js"></script>
	<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
			page="${BOTTOM_PAGE}" flush="false" /></c:if>