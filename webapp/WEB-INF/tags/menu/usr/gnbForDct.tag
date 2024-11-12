<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="elfn" uri="/WEB-INF/tlds/el-fn.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="mnui" tagdir="/WEB-INF/tags/menu/usr" %>
<%@ attribute name="ulId" required="true"%>
<%@ attribute name="gid"%>
<%@ attribute name="sid"%>
<%@ attribute name="menuList" type="net.sf.json.JSONArray" required="true"%>
<%@ attribute name="menus" type="net.sf.json.JSONObject" required="true"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<% /* 주메뉴 */ %>
<c:forEach var="mainInfo" items="${menuList}">
	<c:if test="${!empty mainInfo['menu-list']}">
<ul>
<c:forEach var="menuListInfo" items="${mainInfo['menu-list']}" varStatus="i">
	<c:set var="menuLink" value=""/>
	<c:set var="menuIdx" value="${menuListInfo.menu_idx}"/>
	<c:set var="menuInfoName" value="menu${menuIdx}"/>
	<c:set var="menuInfo" value="${menus[menuInfoName]}"/>
	<c:set var="menuHidden" value="${menuInfo['ishidden']}"/>
	<c:if test="${menuHidden != 1}">
	<c:set var="menuLink" value="${menuInfo['menu_link']}"/>
	<c:set var="menuAuth" value="${elfn:isMenuAuth(menuInfo)}"/>
	<c:set var="usertypeIdx" value="${menuInfo['usertyp_idx']}"/>
	<c:if test="${menuAuth}">
		<c:set var="menuLink" value="${elfn:getAuthMenuLink(menuIdx, menuListInfo, menus)}"/>
		<c:if test="${empty menuLink}"><c:set var="menuLink" value="#"/></c:if>
	<li<c:if test="${gid == menuIdx}"> class="active"</c:if>>
		<a href="<c:out value="${contextPath}${menuLink}"/>" title="${menuInfo['menu_name']}" class="topmenu${i.count} <c:if test="${gid == menuIdx}"> active</c:if>">
			<span class="title"><span data-hover="${menuInfo['menu_name']}">${menuInfo['menu_name']}</span></span>
		</a>
		<div class="top-submenu topmenu${i.count} <c:if test="${gid == menuIdx}"> active</c:if>">
			<h2><a href="<c:out value="${contextPath}${menuLink}"/>" title="${menuInfo['menu_name']}" class="topmenu${i.count} <c:if test="${gid == menuIdx}"> active</c:if>">${menuInfo['menu_name']}<span class="arrow"></span></a></h2>
			<c:if test="${!empty menuListInfo['menu-list']}">
				<ul>
					<c:forEach var="menuListInfo2" items="${menuListInfo['menu-list']}" varStatus="j">
						<c:set var="menuLink2" value=""/>
						<c:set var="menuIdx2" value="${menuListInfo2.menu_idx}"/>
						<c:set var="menuInfoName2" value="menu${menuIdx2}"/>
						<c:set var="menuInfo2" value="${menus[menuInfoName2]}"/>
						<c:set var="menuHidden" value="${menuInfo2['ishidden']}"/>
						<c:if test="${menuHidden != 1}">
						<c:set var="menuLink2" value="${menuInfo2['menu_link']}"/>
						<c:set var="menuAuth2" value="${elfn:isMenuAuth(menuInfo2)}"/>
						<c:if test="${menuAuth2}">
							<c:set var="menuLink2" value="${elfn:getAuthMenuLink(menuIdx2, menuListInfo2, menus)}"/>
							<c:if test="${empty menuLink2}"><c:set var="menuLink2" value="#"/></c:if>
						<li><a href="<c:out value="${contextPath}${menuLink2}"/>" title="${menuInfo2['menu_name']}" class="topmenu${i.count}-${j.count} <c:if test="${sid == menuIdx2}"> active</c:if>"><span class="title">${menuInfo2['menu_name']}</span><span class="arrow"></span></a></li>
						</c:if>
						</c:if>
					</c:forEach>
				</ul>
			</c:if>
		</div>
	</li>
	</c:if>
	</c:if>
</c:forEach>
</ul>
	</c:if>
</c:forEach>