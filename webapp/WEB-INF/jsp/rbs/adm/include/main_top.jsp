<%@ include file="commonTop.jsp" %>
<%@ taglib prefix="mnui" tagdir="/WEB-INF/tags/menu/adm" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
	<%@ include file="commonHead.jsp" %>
	<script type="text/javascript" src="<c:out value="${contextPath}/include/js/jquery.cycle.all.js"/>"></script>
	<script type="text/javascript" src="<c:out value="${contextPath}${jsPath}/ui.js"/>"></script>
	<link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}/include/js/jquery/css/jquery-ui.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}${cssPath}/main.css"/>"/>
	<script type="text/javascript">
	var gVarM = true;
	</script>
	<%@ include file="../../include/contact.jsp"%>
</head>
<body>
<div id="wrapper">
<%@ include file="header.jsp" %>
	<!-- container -->
	<div id="mainContainer">
		<div id="mainContentsWrap">
