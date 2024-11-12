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

<c:set var="today" value="${cnsl.regiDate}"/>
<c:set var="year"><fmt:formatDate value="${today}" pattern="yyyy" /></c:set>
<c:set var="month"><fmt:formatDate value="${today}" pattern="MM" /></c:set>
<c:set var="day"><fmt:formatDate value="${today}" pattern="dd" /></c:set>

<c:choose>
	<c:when test="${cnsl.cnslType eq '1' or cnsl.cnslType eq '2' or cnsl.cnslType eq '3'}">
		<%@ include file="cnslTypeA/cnslViewFormTypeA.jsp"%>
	</c:when>	
	<c:when test="${cnsl.cnslType eq '4'}">
		<%@ include file="cnslTypeB/cnslViewFormTypeB.jsp"%>
	</c:when>
</c:choose>

	<div class="btns-area">
	
		<div class="btns-right">
			<c:if test="${cnsl.confmStatus eq 30}">
				<button type="button" class="btn-m01 btn-color03" onclick="cancelCnsl(`${cnsl.cnslIdx}`,`20`)">회수</button>			
			</c:if>
			<a href="/web/consulting/list.do?mId=64" class="btn-m01 btn-color01 depth3"> 목록 </a>
		</div>
</div>


<!-- //CMS 끝 -->


<script type="text/javascript" src="${contextPath}${jsPath}/cnsl.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>