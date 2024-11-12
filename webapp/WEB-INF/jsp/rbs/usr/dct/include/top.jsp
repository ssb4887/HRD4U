<%@ include file="commonTop.jsp" %>
<%@ taglib prefix="mnui" tagdir="/WEB-INF/tags/menu/usr" %>
<!doctype html>
<html lang="ko">
<head>
	<%@ include file="commonHead.jsp" %>
	<script type="text/javascript" src="<c:out value="${contextPath}${jsPath}/ui.js"/>"></script>
	<link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}${cssPath}/common.css"/>"/>

	<link rel="stylesheet" href="<c:out value="${contextPath}/dct/assets/css/reset.css"/>">
	<link rel="stylesheet" href="<c:out value="${contextPath}/dct/assets/css/board.css"/>">

	<link rel="stylesheet" href="<c:out value="${contextPath}${cssPath}/owl.carousel.min.css"/>"/>
	<link rel="stylesheet" href="<c:out value="${contextPath}${cssPath}/contents.css"/>">
	<link rel="stylesheet" href="<c:out value="${contextPath}${cssPath}/popup.css"/>">

	<script src="<c:out value="${contextPath}/dct/assets/js/jquery.min.js"/>"></script>
	<script src="<c:out value="${contextPath}/dct/assets/js/jquery.easing.1.3.js"/>"></script>
	<script src="<c:out value="${contextPath}/dct/assets/js/jquery-migrate-1.2.1.min.js"/>"></script>

	<script src="<c:out value="${contextPath}${jsPath}/owl.carousel.min.js"/>"></script>
	<script src="<c:out value="${contextPath}${jsPath}/common.js"/>"></script>
	<script src="<c:out value="${contextPath}${jsPath}/popup.js"/>"></script>


	<script src="<c:out value="${contextPath}/dct/assets/js/jquery-ui.min.js"/>"></script>
	<script src="<c:out value="${contextPath}/dct/assets/js/calendar.js"/>"></script>
	<script src="<c:out value="${contextPath}/dct/assets/js/jquery.ui.monthpicker.js"/>"></script>

	<link rel="stylesheet" href="<c:out value="${contextPath}/dct/assets/js/jquery-ui.min.css"/>">
	<script type="text/javascript">
	var gVarM = true;
	</script>
	<%@ include file="../../../include/designPath.jsp" %>
	<%@ include file="../../../include/contact.jsp"%>
	<%@ include file="../../../include/login_check_js.jsp"%>
	
	<link rel="stylesheet" href="<c:out value="${contextPath}/dct/assets/js/datepicker-custom.css"/>" type="text/css">
</head>
<body>

<!-- skip navi -->
<ul id="skipNavi" class="skip-navigation">
	<li><a href="#header-wrapper">주메뉴 바로가기</a></li>
	<li><a href="#contents">본문내용 바로가기</a></li>
</ul>
<!-- //skip navi -->

<div class="wrapper" id="wrapper">

<%@ include file="header.jsp" %>
	<!-- container -->
	<div id="container" class="container">
		<div class="contents-navigation-wrapper">
			<div class="contents-navigation">
				<a href="<%=MenuUtil.getMenuUrl(48) %>" class="home"><span>HOME</span></a>
				<c:set var="lnbMenuList" value="${elfn:getLevelMenuList(crtMenu.menu_idx, 3)}"/>
				<mnui:lnbForDct gid="${crtMenu.menu_idx2}" sid="${crtMenu.menu_idx3}" tid="${crtMenu.menu_idx4}" totalMenuList="${siteMenuList}" menuList="${lnbMenuList}" menus="${siteMenus}"/>
			</div>
		</div>		
		<div id="content" class="container-wrapper">
			<article>
				<div class="contents">
					<h2 class="contents-title">${crtMenu.menu_name}</h2>