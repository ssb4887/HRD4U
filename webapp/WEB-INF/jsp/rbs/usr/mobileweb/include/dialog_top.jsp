<%@ include file="commonTop.jsp" %>
<!doctype html>
<html lang="ko">
<head>
	<%@ include file="commonHead.jsp" %>
	<%@ include file="../../../include/designPath.jsp" %>
	<link rel="stylesheet" type="text/css" href="<c:url value="/include/js/jquery/css/jquery-ui.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="${cssPath}/dialog.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/include/js/editor/css/smart_editor2_out.css"/>"/>
	<script type="text/javascript">
	$(function(){
		$(".fn_btn_topClose").click(function(){
			self.close();
		});
	});
	</script>
</head>
<body>
<div id="wrapper">
	<!-- container -->
	<div id="container">
		<div id="contentsWrap">
			<div id="content">
			<h3><c:choose><c:when test="${!empty param.page_tit}"><c:out value="${param.page_tit}"/></c:when><c:when test="${!empty queryString.mt}"><c:out value="${queryString.mt}"/></c:when><c:when test="${!empty queryString.tit}"><c:out value="${queryString.tit}"/></c:when><c:otherwise><c:out value="${crtMenu.menu_name}"/></c:otherwise></c:choose></h3>
			<button type="button" class="fn_btn_topClose" title="닫기">닫기</button>