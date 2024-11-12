<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/loginWeb.jsp"/>
	</jsp:include>
</c:if>
<div class="contents-wrapper">
	<div class="login-area">
		<div class="login-box">
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
					
                    <div class="members-menu-list">
                        <a href="https://hrd4u.or.kr/portal/member/memberKnd.do" class="btn-join01">
                            <span class="icon"></span>
                            <strong>회원가입</strong>
                            <span class="arrow"></span>
                        </a>
                        <div class="word-find">
                            <a href="https://hrd4u.or.kr/portal/member/find_id.do"> 아이디찾기</a>
                            <a href="https://hrd4u.or.kr/portal/member/find_pw.do"> 비밀번호찾기 </a>
                        </div>
                    </div>
				</fieldset>
			</form>
		</div>
	</div>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>