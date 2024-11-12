<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:set var="searchFormId" value="fn_techSupportSearchForm"/>
<c:set var="listFormId" value="fn_techSupportListForm"/>
<c:set var="inputWinFlag" value=""/><%/* 등록/수정창 새창으로 띄울 경우 사용 */%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/><%/* 수정버튼 class */%>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
	<div id="cms_board_article" class="contents-wrapper">
		<div class="one-box space">
			<div class="half-box">
				<div class="title-wrapper">
					<h3 class="title-type02 ml0"><c:out value="${settingInfo.list_title}"/></h3>
					<itui:searchMultiSelect selectClass="traing" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" idx="1"/>
				</div>
				<div class="table-type02">
					<table id="tableTraing">
						<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
						<colgroup>
							<col style="width:30%">
							<col style="width:35%">
							<col style="width:35%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col"><itui:objectItemName itemId="year" itemInfo="${itemInfo}"/></th>
								<th scope="col"><itui:objectItemName itemId="corp" itemInfo="${itemInfo}"/>(개)</th>
								<th scope="col"><itui:objectItemName itemId="tot" itemInfo="${itemInfo}"/>(명)</th>
							</tr>
						</thead>
						<tbody>
							<itui:objectViewTable objDt="${list}" selectClass="traing" tableId="tableTraing" idx="1"/>
						</tbody>
					</table>
				</div>
				<div class="btns-area mt60 mb50">
					<a href="<c:out value="${URL_INPUT}"/>" class="btn-m01 btn-color03"><span>등록</span></a>
					<a href="<c:out value="${URL_LISTMODIFY}"/>" class="btn-m01 btn-color05"><span>수정</span></a>
					<a href="<c:out value="${URL_VIEW}"/>" class="btn-m01 btn-color01"><span>상세조회</span></a>
					<a href="<c:out value="${URL_DETAILLIST}"/>" class="btn-m01 btn-color01"><span>상세목록</span></a>
				</div>
			</div>
			<div class="half-box">
				<div class="title-wrapper">
					<h3 class="title-type02 ml0">최근 직업훈련 지원 현황</h3>
					<itui:searchMultiSelect selectClass="sprt" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" idx="2"/>
				</div>
				<div class="table-type02">
					<table id="tableSprt">
						<caption>최근 직업훈련 지원 현황 목록</caption>
						<colgroup>
							<col style="width:30%">
							<col style="width:70%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col"><itui:objectItemName itemId="year" itemInfo="${itemInfo}"/></th>
								<th scope="col">지급금액(원)</th>
							</tr>
						</thead>
						<tbody>
							<itui:objectViewTable objDt="${sprtList}" selectClass="sprt" tableId="tableSprt" idx="2"/>
						</tbody>
					</table>
				</div>
				<div class="btns-area mt60">
					<a href="${contextPath}/${crtSiteId}/sprt/input.do?mId=92" class="btn-m01 btn-color03"><span>등록</span></a>
					<a href="${contextPath}/${crtSiteId}/sprt/modify.do?mId=92" class="btn-m01 btn-color05"><span>수정</span></a>
					<a href="${contextPath}/${crtSiteId}/sprt/view.do?mId=92" class="btn-m01 btn-color01"><span>상세조회</span></a>
					<a href="${contextPath}/${crtSiteId}/sprt/detailList.do?mId=92" class="btn-m01 btn-color01"><span>상세목록</span></a>
				</div>
			</div>
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>