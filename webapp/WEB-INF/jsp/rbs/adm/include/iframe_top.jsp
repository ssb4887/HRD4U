<%@ include file="commonTop.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
	<%@ include file="commonHead.jsp" %>
	<link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}/include/js/jquery/css/jquery-ui.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}${cssPath}/iframe.css"/>"/>
	<c:choose>
		<c:when test="${menuType == 2}">
		<script type="text/javascript">
		<!--
		$(function(){
			fn_contactSubmitForm('<c:out value="${contextPath}/${crtSiteId}/menuContents/stats/contact.do?mId=${queryString.mId}"/>&mn=${elfn:encode(usrCrtMenu.menu_name, 'UTF-8')}');
		});
		//-->
		</script>
		
		</c:when>
		<c:otherwise>
			<%@ include file="../../include/contact.jsp"%>
		</c:otherwise>
	</c:choose>
</head>
<body>
<div id="wrapper">
	<!-- container -->
	<div id="container">
		<div id="contentsWrap">
			<div id="content">
