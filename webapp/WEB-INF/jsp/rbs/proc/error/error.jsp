<%@page import="com.woowonsoft.egovframework.util.StringUtil"%>
<%@ include file="../../include/common.jsp"%>
<%
if(response.isCommitted()) return;
response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
boolean isAjax = StringUtil.isEquals(request.getHeader("Ajax"), "true");

if(isAjax) {
%>
오류가 발생했습니다
<%}else{ %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><c:choose><c:when test="${!empty siteInfo}">${siteInfo.site_title}</c:when><c:otherwise><spring:message code="Globals.site.default.title"/></c:otherwise></c:choose> - 오류가 발생했습니다.</title>
	<script type="text/javascript">
	try{
		if(window != top && window.innerWidth == 0) {
			alert("오류가 발생했습니다.<c:if test="${!empty errorCode}"> (${errorCode})</c:if>");
		}
	}catch(e){}
	</script>
</head>
<body>
	<h1>오류가 발생했습니다.<c:if test="${!empty errorCode}"> (${errorCode})</c:if></h1>
	<p><c:choose><c:when test="${!empty errorCode}"><spring:message code="errors.message.${errorCode}"/></c:when><c:otherwise>알 수 없는 오류가 발생했습니다.</c:otherwise></c:choose></p>
	<p><button type="button" onclick="history.go(-1);">이전페이지로 돌아가기</button></p>
</body>
</html>
<%} %> 