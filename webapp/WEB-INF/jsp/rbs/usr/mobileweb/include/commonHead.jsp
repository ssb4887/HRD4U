	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<title><c:out value="${siteInfo.site_title}"/>-<c:out value="${crtMenu.menu_name}"/></title>
	<meta name="subject" content="HRD4U 기업직업훈련 지원시스템" />
	<meta name="author" content="HRD4U 기업직업훈련 지원시스템" />
	<meta name="keywords" content="HRD4U 기업직업훈련 지원시스템, 기업훈련지원, 기초진단, 기업HRD이음컨설팅, 훈련패키지" />
	<meta name="description" content="중소기업의 혁신성장을 위한 든든한 뒷받침 능력개발전담주치의" />
	<!-- eGov Mobile CSS -->
	<link rel="stylesheet" href="${contextPath}${cssPath}/jquery.mobile-1.4.5.css"/>
	<link rel="stylesheet" href="${contextPath}${cssPath}/EgovMobile-1.4.5.css"/>
	
	<!-- eGov Mobile JS -->
	<script type="text/javascript" src="${contextPath}${jsPath}/jquery-1.11.2.js"></script>
	<script type="text/javascript" src="${contextPath}${jsPath}/jquery.mobile-1.4.5.js"></script>
	<script type="text/javascript" src="${contextPath}${jsPath}/EgovMobile-1.4.5.js"></script>
	
	<script type="text/javascript" src="<c:out value="${contextPath}/include/js/checkForm.js?cp=${pageContext.request.contextPath}&lg=${siteInfo.locale_lang}&st=${crtSiteId}"/>"></script>
	<c:set var="JAVASCRIPT_PAGE" value="${param.javascript_page}"/>
	<c:if test="${!empty JAVASCRIPT_PAGE}"><jsp:include page="${JAVASCRIPT_PAGE}" flush="false"/></c:if>
