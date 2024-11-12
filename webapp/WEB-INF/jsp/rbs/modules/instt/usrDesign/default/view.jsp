<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/view.jsp"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
	<div id="cms_board_article" class="contents-wrapper">
		<div class="contents-area">
			<div class="contents-box pl0">
				<h3 class="title-type01 ml0">기본정보</h3>
				<div class="board-write">
					<div class="one-box">
						<itui:itemViewCustom2 itemId="insttName" itemInfo="${itemInfo}" />
					</div>
					<div class="one-box">
						<itui:itemViewCustom2 itemId="insttNo" itemInfo="${itemInfo}" />
					</div>
					<div class="one-box">
						<itui:itemViewCustom2 itemId="locCode" itemInfo="${itemInfo}" />
					</div>
					<div class="one-box">
						<itui:itemViewCustom2 itemId="phone" itemInfo="${itemInfo}" />
					</div>
					<div class="one-box">
						<itui:itemViewCustom2 itemId="homepage" itemInfo="${itemInfo}" homepageUrl="yes" contextPath="${contextPath}" imgPath="${imgPath}"/>
					</div>
					<div class="one-box">
						<itui:itemViewCustom2 itemId="remarks" itemInfo="${itemInfo}" />
					</div>
<!-- 					<div class="one-box"> -->
<%-- 						<itui:itemViewCustom2 itemId="orderIdx" itemInfo="${itemInfo}" /> --%>
<!-- 					</div> -->
				</div>
			</div>
		</div>
		<div class="contents-area">	
			<h3 class="title-type01 ml0">관할구역</h3>
			<div class="board-write" id="board-view">
				<div class="one-box">
					<itui:itemViewMultiCheckbox itemId="blockCode" itemInfo="${itemInfo}" objDt="${dt}" optionInfo="${optionInfo}"/>
				</div>
			</div>
		</div>
		<div class="btnTopFull">
			<div class="right">
				<a href="<c:out value="${URL_MODIFY}"/>&insttIdx=${dt.INSTT_IDX}" class="btn-m01 btn-color03 depth3">수정</a>
				<a href="<c:out value="${URL_DELETEPROC}"/>&insttIdx=${dt.INSTT_IDX}" title="삭제" class="btn-m01 btn-color02 depth3 fn_btn_delete">삭제</a>
				<a href="<c:out value="${URL_LIST}"/>" title="목록" class="btn-m01 btn-color01 depth3 fn_btn_write">목록</a>
			</div>
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>