<%@ include file="commonTop.jsp" %>
<%@ taglib prefix="mnui" tagdir="/WEB-INF/tags/menu/usr" %>
<!doctype html>
<html lang="ko">
<head>
	<%@ include file="commonHead.jsp" %>
	<script type="text/javascript" src="<c:out value="${contextPath}${jsPath}/ui.js"/>"></script>
	<link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}${cssPath}/common.css"/>"/>
	<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css" />

	<link rel="stylesheet" href="<c:out value="${contextPath}/web/assets/css/reset.css"/>">
	<link rel="stylesheet" href="<c:out value="${contextPath}/web/assets/css/board.css"/>">

	<link rel="stylesheet" href="<c:out value="${contextPath}${cssPath}/owl.carousel.min.css"/>"/>
	<link rel="stylesheet" href="<c:out value="${contextPath}${cssPath}/contents.css"/>">
	<link rel="stylesheet" href="<c:out value="${contextPath}${cssPath}/popup.css"/>">

	<script src="<c:out value="${contextPath}/web/assets/js/jquery.min.js"/>"></script>
	<script src="<c:out value="${contextPath}/web/assets/js/jquery.easing.1.3.js"/>"></script>
	<script src="<c:out value="${contextPath}/web/assets/js/jquery-migrate-1.2.1.min.js"/>"></script>

	<script src="<c:out value="${contextPath}${jsPath}/owl.carousel.min.js"/>"></script>
	<script src="<c:out value="${contextPath}${jsPath}/common.js"/>"></script>
	<script src="<c:out value="${contextPath}${jsPath}/popup.js"/>"></script>


	<script src="<c:out value="${contextPath}/web/assets/js/jquery-ui.min.js"/>"></script>
	<script src="<c:out value="${contextPath}/web/assets/js/calendar.js"/>"></script>
	<script src="<c:out value="${contextPath}/web/assets/js/jquery.ui.monthpicker.js"/>"></script>

	<link rel="stylesheet" href="<c:out value="${contextPath}/web/assets/js/jquery-ui.min.css"/>">
	<link rel="stylesheet" href="<c:out value="${contextPath}/web/assets/js/datepicker-custom.css"/>" type="text/css">
	<script type="text/javascript">
	var gVarM = true;
	</script>
	<%@ include file="../../../include/designPath.jsp" %>
	<%@ include file="../../../include/contact.jsp"%>
	<%@ include file="../../../include/login_check_js.jsp"%>
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
	<div class="sub-visual active">
		<h2>${crtMenu.menu_name}</h2>
		<ul class="additional-function-wrapper">
			<li>
				<!-- <button type="button" class="share" id="open-share-list">공유</button> -->
				    <div class="share-list-wrapper">
			            <ul>
			                <li><a class="facebook" data-sns="facebook" href="#share-list" title="페이스북 새 창 열림">페이스북</a></li>
			                <li><a class="twitter" data-sns="twitter" href="#share-list" title="트위터 새 창 열림">트위터</a></li>
			                <li><a class="kakaostory" data-sns="kakaotalk" href="#share-list" title="카카오스토리 새 창 열림">카카오스토리</a></li>
			                <li><a class="blog" data-sns="band" href="#share-list" title="네이버밴드 새 창 열림">네이버밴드</a></li>
			            </ul>
			            <button class="close" type="button"><span class="blind">sns 공유 리스트 닫기</span></button>
				    </div>
			</li>
		</ul>
	</div>
	<div id="container" class="container">
		<div class="contents-navigation-wrapper">
			<div class="contents-navigation">
				<a href="<c:out value="${contextPath}${indexUrl}"/>" class="home"><span>HOME</span></a>
				<c:set var="lnbMenuList" value="${elfn:getLevelMenuList(crtMenu.menu_idx, 3)}"/>
				<mnui:lnbForDct gid="${crtMenu.menu_idx2}" sid="${crtMenu.menu_idx3}" tid="${crtMenu.menu_idx4}" totalMenuList="${siteMenuList}" menuList="${lnbMenuList}" menus="${siteMenus}"/>
			</div>
		</div>		
		<div id="content" class="container-wrapper">
			<article>
				<div class="contents">