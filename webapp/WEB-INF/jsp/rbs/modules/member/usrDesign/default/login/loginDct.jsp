<%@ include file="../../../../../usr/dct/include/commonTop.jsp" %>
<%@ taglib prefix="mnui" tagdir="/WEB-INF/tags/menu/usr" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta http-equiv="Content-Script-Type" content="text/javascript" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<!-- url공유 썸네일 이미지 지정-->
	<%@ include file="../../../../../usr/dct/include/commonHead.jsp" %>

	<link rel="stylesheet" href="${contextPath}/assets/css/reset.css">
	<link rel="stylesheet" href="${contextPath}/assets/css/board.css">

	<link rel="stylesheet" href="${contextPath}${siteLocalPath}/css/owl.carousel.min.css">
	<link rel="stylesheet" href="${contextPath}${siteLocalPath}/css/common.css">
	<link rel="stylesheet" href="${contextPath}${siteLocalPath}/css/contents.css">
	<link rel="stylesheet" href="${contextPath}${siteLocalPath}/css/contents_pc.css">
	<link rel="stylesheet" href="${contextPath}${siteLocalPath}/css/popup.css">

	<script src="${contextPath}/assets/js/jquery.min.js"></script>
	<script src="${contextPath}/assets/js/jquery.easing.1.3.js"></script>
	<script src="${contextPath}/assets/js/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${contextPath}/include/js/login.js"></script>

	<script src="${contextPath}${siteLocalPath}/js/owl.carousel.min.js"></script>
	<script src="${contextPath}${siteLocalPath}/js/common.js"></script>
	<script src="${contextPath}${siteLocalPath}/js/popup.js"></script>


	<script src="${contextPath}/assets/js/jquery-ui.min.js"></script>
	<script src="${contextPath}/assets/js/calendar.js"></script>
	<script src="${contextPath}/assets/js/jquery.ui.monthpicker.js"></script>

	<link rel="stylesheet" href="${contextPath}/assets/js/jquery-ui.min.css">
	<link rel="stylesheet" href="${contextPath}/assets/js/datepicker-custom.css" type="text/css">
        <title>
            로그인 &lt; 기업직업훈련 지원시스템 (관리자)
        </title>
</head>

<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ include file="../../../login/loginDct.jsp"%>
<div class="login-wrapper" id="wrapper">
	<div class="login-area">
		 <div class="login-box">
                <h1>
                    <img src="${contextPath}${siteLocalPath}/images/common/logo02.png" alt="HRDK 한국산업인력공단" />
                    <strong>
                        <img src="${contextPath}${siteLocalPath}/images/common/logo01.svg" alt="" />
                        <span>기업직업훈련 지원시스템</span>
                    </strong>
                </h1>
		
			<spring:message var="useSnsLogin" code="Globals.sns.login.use" text="0"/>
			<c:if test="${useSnsLogin == '1'}">
			<div>
			<c:forEach var="snsId" items="${settingInfo.sns_info.list}">
				<c:set var="useSnsLoginItemName" value="${snsId}_login"/>
				<c:if test="${siteInfo[useSnsLoginItemName] == 1}">
				<c:set var="snsInfo" value="${settingInfo.sns_info.items[snsId]}"/>
				<c:set var="snsUrlName" value="URL_${snsInfo.sns_url_flag}LOGIN"/>
				<button type="button" data-url="<c:out value="${requestScope[snsUrlName]}"/>" class="fn_btn_snsLogin">${snsInfo.sns_name} 로그인</button>
				</c:if>
			</c:forEach>
			</div>
			<form id="fn_snsLoginForm" name="snsLoginForm" method="post" target="submit_target">
			</form>
			</c:if>
			<form id="loginFrm" name="loginFrm" method="post" action="<c:out value="${URL_LOGINPREPROC}"/>" target="submit_target">
				<fieldset class="loginForm">
					<legend class="blind">로그인 폼</legend>
					<label for="mbrId" class="blind">아이디</label>
                    <input id="mbrId" name="mbrId" title="아이디를 입력하세요." type="search" placeholder="아이디" value="">
                    <label for="mbrPwd" class="blind"> 비밀번호</label>
                    <input id="mbrPwd" name="mbrPwd" title="비밀번호를 입력하세요." type="password" placeholder="비밀번호" value="">
					<p class="save-id">
                            <input type="checkbox" id="checkId" name="checkId">
							<label for="checkId"><span>아이디 저장</span></label>
                    </p>
					<input type="submit" id="fn_logIn_btn" class="btnLogin" value="로그인"/>
				</fieldset>
			</form>
		
		</div>
	</div>
</div>
