<!-- header -->
	<%@page import="com.woowonsoft.egovframework.util.MenuUtil"%>
<header>
	<div id="header" class="header">
		<div class="gnb-wrapper">
			<div class="gnb-area">
				<div class="right-gnb-list">
					<ul>
						<c:set var="listIdxName" value="memberIdx"/>
						<c:set var="listKey" value="${loginVO.memberIdx}"/>
						<c:choose>
							<c:when test="${elfn:isLoginAuth()}">
								<c:if test="${elfn:isLogin()}">
								<%-- <li><a href="<%=MenuUtil.getMenuUrl(5) %>">내정보수정</a></li> --%>
								<li><a href="<%=MenuUtil.getMenuUrl(4) %>">로그아웃</a></li>
								<li><a href="../memberManage/inputFstEmploy.do?mId=5&${listIdxName}=${listKey}">마이페이지</a>
								</li>
								</c:if>
							</c:when>
							<c:otherwise>
								<li><a href="<%=MenuUtil.getMenuUrl(3) %>">로그인</a></li>
							</c:otherwise>
						</c:choose>		
					</ul>		
				</div>
			</div>
		</div>
	
		<div id="header-wrapper" class="header-wrapper">
			<div class="bg"></div>
			<div class="header-area">
				<h1>
					<a href="<%=MenuUtil.getMenuUrl(48) %>"><img src="<c:out value="${contextPath}${imgPath}/common/logo01_white.svg"/>" alt="기업직원훈련 지원시스템" /><strong>기업직업훈련 지원시스템</strong></a>
				</h1>
				<div class="top-menu-wrapper">
					<mnui:gnbForDct ulId="gnb" gid="${crtMenu.menu_idx2}" sid="${crtMenu.menu_idx3}" menuList="${siteMenuList}" menus="${siteMenus}" />
				</div>
				<div class="right-btns">
					<button type="button" class="btn-totalmenu">
						<span class="menu">
							<span></span>
							<span></span>
							<span></span>
						</span>
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 모바일 전체메뉴 -->
	<div class="mask-totalmenu"></div>
	<div class="totalmenu-wrapper">
		<div class="mobile-gnb-wrapper">
			<ul class="gnb-menu">
				<c:set var="listIdxName" value="memberIdx"/>
				<c:set var="listKey" value="${loginVO.memberIdx}"/>
				<c:choose>
					<c:when test="${elfn:isLoginAuth()}">
						<c:if test="${elfn:isLogin()}">
						<%-- <li><a href="<%=MenuUtil.getMenuUrl(5) %>">내정보수정</a></li> --%>
						<li><a href="<%=MenuUtil.getMenuUrl(4) %>">로그아웃</a></li>
						<li><a href="../memberManage/inputFstEmploy.do?mId=5&${listIdxName}=${listKey}">마이페이지</a>
						</li>
						</c:if>
					</c:when>
					<c:otherwise>
						<li><a href="<%=MenuUtil.getMenuUrl(3) %>">로그인</a></li>
					</c:otherwise>
				</c:choose>		
			</ul>
		</div>
		<div class="totalmenu-area">
			<mnui:gnbForDctMobile ulId="gnb" gid="${crtMenu.menu_idx2}" sid="${crtMenu.menu_idx3}" tid="${crtMenu.menu_idx4}" menuList="${siteMenuList}" menus="${siteMenus}" />
		</div>
		<button type="button" class="btn-mobile-close">
			<img src="<c:out value="${contextPath}${imgPath}/common/btn_totalmenu_close_mobile.png"/>" alt="전체메뉴 담기">
		</button>
	</div>
</header>
	<!-- //header -->