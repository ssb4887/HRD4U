<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ attribute name="ulClass"%>
<%@ attribute name="gid"%>
<%@ attribute name="sid"%>
<%@ attribute name="tid"%>
<%@ attribute name="totalMenuList" type="net.sf.json.JSONArray" required="true"%>
<%@ attribute name="menuList" type="net.sf.json.JSONArray" required="true"%>
<%@ attribute name="menus" type="net.sf.json.JSONObject" required="true"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<% /* 상단메뉴 */ %>
<ul>
<c:set var="menuLevel" value="1" />
<c:forEach var="mainInfo" items="${totalMenuList}" varStatus="i">
	<c:set var="menuIdx" value="${mainInfo.menu_idx}"/>
	<c:set var="menuLink" value="${elfn:getAuthMenuLink(menuIdx, mainInfo, menus)}"/>
	<c:if test="${empty menuLink}"><c:set var="menuLink" value="#"/></c:if>
	<c:if test="${!empty mainInfo['menu-list']}">
	<!-- 1차 메뉴 -->
	<c:set var="menuListLevel" value="${menuLevel + 1}" />
	<c:set var="menuNameLevelName" value="menu_name${menuListLevel}" />
	<li>
		<button type="button">${crtMenu[menuNameLevelName]}</button>
		<ul>
			<c:forEach var="menuListInfo" items="${mainInfo['menu-list']}" varStatus="j">
				<c:set var="menuLink" value=""/>
				<c:set var="menuIdx" value="${menuListInfo.menu_idx}"/>
				<c:set var="menuInfoName" value="menu${menuIdx}"/>
				<c:set var="menuInfo" value="${menus[menuInfoName]}"/>
				<c:set var="menuTarget" value="${menuInfo['menu-target']}"/>
				<c:set var="menuHidden" value="${menuInfo['ishidden']}"/>
				<c:if test="${menuHidden != 1}">
				<c:set var="menuLink" value="${menuInfo['menu_link']}"/>
				<c:set var="menuAuth" value="${elfn:isMenuAuth(menuInfo)}"/>
				<c:if test="${menuAuth}">
					<c:set var="menuLink" value="${elfn:getAuthMenuLink(menuIdx, menuListInfo, menus)}"/>
					<c:if test="${empty menuLink}"><c:set var="menuLink" value="#"/></c:if>
				<li><a href="<c:out value="${contextPath}${menuLink}"/>" title="${menuInfo['menu_name']}" class="topmenu${j.count} <c:if test="${gid == menuIdx}"> active</c:if>"><strong>${menuInfo['menu_name']}</strong></a></li>
				</c:if>
				</c:if>
			</c:forEach>
		</ul>
	</li>
	<c:set var="menuLevel" value="${menuLevel + 1}" />
	
	<c:if test="${!empty menuList}">
	<!-- 2차 메뉴 -->
	<c:set var="menuListLevel" value="${menuLevel + 1}" />
	<c:set var="menuNameLevelName" value="menu_name${menuListLevel}" />
	<li>
		<button type="button">${crtMenu[menuNameLevelName]}</button>
		<ul>
			<c:forEach var="menuListInfo" items="${menuList}" varStatus="j">
				<c:set var="menuLink" value=""/>
				<c:set var="menuIdx" value="${menuListInfo.menu_idx}"/>
				<c:set var="menuInfoName" value="menu${menuIdx}"/>
				<c:set var="menuInfo" value="${menus[menuInfoName]}"/>
				<c:set var="menuTarget" value="${menuInfo['menu-target']}"/>
				<c:set var="menuHidden" value="${menuInfo['ishidden']}"/>
				<c:if test="${menuHidden != 1}">
				<c:set var="menuLink" value="${menuInfo['menu_link']}"/>
				<c:set var="menuAuth" value="${elfn:isMenuAuth(menuInfo)}"/>
				<c:if test="${menuAuth}">
					<c:set var="menuLink" value="${elfn:getAuthMenuLink(menuIdx, menuListInfo, menus)}"/>
					<c:if test="${empty menuLink}"><c:set var="menuLink" value="#"/></c:if>
				<li><a href="<c:out value="${contextPath}${menuLink}"/>" title="${menuInfo['menu_name']}" class="topmenu${j.count} <c:if test="${sid == menuIdx}"> active</c:if>"><strong>${menuInfo['menu_name']}</strong></a>
					<c:if test="${!empty menuListInfo['menu-list'] && sid == menuIdx && menuInfo['child_hidden'] != '1'}">
						<c:set var="trdMenuList" value="${menuListInfo['menu-list']}" />
					</c:if>
				</li>
				</c:if>
				</c:if>
			</c:forEach>
		</ul>
	</li>
	<c:set var="menuLevel" value="${menuLevel + 1}" />
	
	<c:if test="${!empty trdMenuList}">
	<!-- 3차 메뉴 -->
	<c:set var="menuListLevel" value="${menuLevel + 1}" />
	<c:set var="menuNameLevelName" value="menu_name${menuListLevel}" />
	<li>
		<button type="button">${crtMenu[menuNameLevelName]}</button>
		<ul>
			<c:forEach var="menuListInfo" items="${trdMenuList}" varStatus="j">
				<c:set var="menuLink" value=""/>
				<c:set var="menuIdx" value="${menuListInfo.menu_idx}"/>
				<c:set var="menuInfoName" value="menu${menuIdx}"/>
				<c:set var="menuInfo" value="${menus[menuInfoName]}"/>
				<c:set var="menuTarget" value="${menuInfo['menu-target']}"/>
				<c:set var="menuHidden" value="${menuInfo['ishidden']}"/>
				<c:if test="${menuHidden != 1}">
				<c:set var="menuLink" value="${menuInfo['menu_link']}"/>
				<c:set var="menuAuth" value="${elfn:isMenuAuth(menuInfo)}"/>
				<c:if test="${menuAuth}">
					<c:set var="menuLink" value="${elfn:getAuthMenuLink(menuIdx, menuListInfo, menus)}"/>
					<c:if test="${empty menuLink}"><c:set var="menuLink" value="#"/></c:if>
				<li><a href="<c:out value="${contextPath}${menuLink}"/>" title="${menuInfo['menu_name']}" class="topmenu${j.count} <c:if test="${sid == menuIdx}"> active</c:if>"><strong>${menuInfo['menu_name']}</strong></a>
					<c:if test="${!empty menuListInfo['menu-list'] && tid == menuIdx && menuInfo['child_hidden'] != '1'}">
						<c:set var="fthMenuList" value="${menuListInfo['menu-list']}" />
					</c:if>
				</li>
				</c:if>
				</c:if>
			</c:forEach>
		</ul>
	</li>
	</c:if>
	</c:if>
	</c:if>
</c:forEach>
</ul>