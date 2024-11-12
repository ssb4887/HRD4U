<!-- header -->
<%@page import="com.woowonsoft.egovframework.util.MenuUtil"%>

		<!-- 팝업알림 -->
		<%-- <div class="mask-popup"></div>
		<div class="popupzone-wrapper">
			<div class="popupzone-area">
				<div class="owl-carousel" id="popupzone-slider">
					<div class="item">
						<img src="${contextPath}${imgPath}/popup/popup02_banner01.jpg" alt="" />
					</div>

					<div class="item">
						<a href="#">
							<img src="${contextPath}${imgPath}/popup/popup02_banner02.jpg" alt="" />
						</a>
					</div>

					<div class="item">
						<a href="#">
							<img src="${contextPath}${imgPath}/popup/popup02_banner03.jpg" alt="" />
						</a>
					</div>

					<div class="item">
						<a href="#">
							<img src="${contextPath}${imgPath}/popup/popup02_banner03.jpg" alt="" />
						</a>
					</div>
				</div>

				<div class="popupzone-footer-wrapper">
					<div class="today-checked-close">
						<input type="checkbox" id="checkbox-top-popupzone02" name="" value="" />
						<label for="checkbox-top-popupzone02">
							오늘하루 열지않기
						</label>
					</div>
					
					<button type="button" class="btn-close">
						<span>
							닫기
						</span>
					</button>
					
				</div>
			</div>
		</div>		 --%>
<header>

	<div id="header" class="header">
		<div class="gnb-wrapper">
			<div class="gnb-area">
				<dl>
					<dt>화면설정</dt>
                <dd>
                    <button type="button" class="big" onclick="zoomOut(); return false;">화면크기 확대</button>
                    <button type="button" class="small" onclick="zoomIn(); return false;">화면크기 축소</button>
                    <button type="button" class="reset" onclick="zoomReset(); return false;">화면 초기화</button>
                </dd>
				</dl>
				<div class="right-gnb-list">
					<ul>
						<c:set var="listIdxName" value="memberIdx"/>
						<c:set var="listKey" value="${loginVO.memberIdx}"/>
						<c:set var="corpIdxName" value="corpNum"/>
						<c:set var="corpKey" value="${loginVO.corpNum}"/>
						<c:choose>
							<c:when test="${elfn:isLoginAuth()}">
								<li><c:out value="${loginVO.memberNameOrg}" />님</li>
								<c:if test="${elfn:isLogin()}">
									<li><a href="<%=MenuUtil.getMenuUrl(4) %>">로그아웃</a></li>
									<li>
										<c:choose>  
											<c:when test="${loginVO.usertypeIdx >= 20}"><a href="../memberManage/inputCenter.do?mId=5&${listIdxName}=${listKey}&${corpIdxName}=${corpKey}" class="btnTypeF fn_btn_modify">마이페이지</a></c:when>
											<c:when test="${loginVO.usertypeIdx >= 10}"><a href="../memberManage/inputConsult.do?mId=5&${listIdxName}=${listKey}" class="btnTypeF fn_btn_modify">마이페이지</a></c:when>
											<c:otherwise><a href="../memberManage/inputCorp.do?mId=5&${listIdxName}=${listKey}&${corpIdxName}=${corpKey}">마이페이지</a></c:otherwise>
										</c:choose>
										
									</li>
								</c:if>
								<c:if test="${loginVO.usertypeIdx >= 30}">
<!-- 									<li><a href="/dct/main/main.do?mId=1" target="_blank">관리자페이지</a></li> -->
								</c:if>
							</c:when>
							<c:otherwise>
								<li><a href="<%=MenuUtil.getMenuUrl(3) %>">로그인</a></li>
								<li><a href="https://hrd4u.or.kr/portal/member/memberKnd.do">회원가입</a></li>
							</c:otherwise>
						</c:choose>
								<!-- <li>
									<button type="button" class="btn-popup">
					                    <strong>
					                        POPUP
					                    </strong>
					                    <span class="icon-bell">
					                        <img src="../images/icon/icon_bell01.png"alt=""/>
					                        <span class="badge"></span>
					                    </span>
				                	</button>	
								</li>	 -->	
					</ul>	
				</div>
			</div>
		</div>
	
		<div id="header-wrapper" class="header-wrapper">
			<div class="bg"></div>
			<div class="header-area">
				<h1>
					<a href="<c:out value="${contextPath}${indexUrl}"/>"><img src="<c:out value="${contextPath}${imgPath}/common/logo01.svg"/>" alt="기업직원훈련 지원시스템" /><strong>기업직업훈련 지원시스템</strong></a>
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
				<c:set var="corpIdxName" value="corpNum"/>
				<c:set var="corpKey" value="${loginVO.corpNum}"/>
				<c:choose>
					<c:when test="${elfn:isLoginAuth()}">
						<c:if test="${elfn:isLogin()}">
						<%-- <li><a href="<%=MenuUtil.getMenuUrl(5) %>">내정보수정</a></li> --%>
						<li><span style="color:#fff;"><c:out value="${loginVO.memberNameOrg}" />님</span></li>
						<li><a href="<%=MenuUtil.getMenuUrl(4) %>">로그아웃</a></li>
						<%-- <li><a href="../memberManage/inputFstEmploy.do?mId=5&${listIdxName}=${listKey}">마이페이지</a>
						</li> --%>
						<li>
							<c:choose>  
								<c:when test="${loginVO.usertypeIdx >= 20}"><a href="../memberManage/inputCenter.do?mId=5&${listIdxName}=${listKey}&${corpIdxName}=${corpKey}" class="btnTypeF fn_btn_modify">마이페이지</a></c:when>
								<c:when test="${loginVO.usertypeIdx >= 10}"><a href="../memberManage/inputConsult.do?mId=5&${listIdxName}=${listKey}" class="btnTypeF fn_btn_modify">마이페이지</a></c:when>
								<c:otherwise><a href="../memberManage/inputCorp.do?mId=5&${listIdxName}=${listKey}&${corpIdxName}=${corpKey}">마이페이지</a></c:otherwise>
							</c:choose>
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