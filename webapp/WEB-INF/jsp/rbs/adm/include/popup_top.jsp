<%@ include file="commonTop.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
	<%@ include file="commonHead.jsp" %>
	<link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}/include/js/jquery/css/jquery-ui.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}${cssPath}/popup.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}/include/js/editor/css/smart_editor2_out.css"/>"/>
	<script type="text/javascript">
	$(function(){
		// 닫기
		$('#fn_btn_close').click(function(){
			closePopup();
			return false;
		});
	});

	function closePopup() {
		try {
			self.close();
		} catch(e) {
		}
	}
	</script>
	<c:choose>
		<c:when test="${menuType == 2}">
	
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
