<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>

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
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/edu/eduDct.js"/>"></script>
<div id="overlay"></div>
<div class="loader"></div>
<div class="contents-wrapper">
	<!-- CMS 시작 -->
	
	<div class="contents-area">
		<div class="contents-box pl0">
			<div class="temp-contets-area type02">신청서 양식</div>
	
			<div class="table-type02 horizontal-scroll">
				<table class="width-type02">
					<caption>교육생 정보표 : 신청정보, 신청일자, 신청자, 신청상태, 참석여부</caption>
					<colgroup>
						<col style="width: 20%">
						<col style="width: 20%">
						<col style="width: 60%">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" rowspan="4" class="bg01">신청정보</th>
							<th scope="row">신청일자</th>
							<td class="left"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${member.REGI_DATE}"/></td>
						</tr>
						<tr>
							<th scope="row">신청자</th>
							<td class="left"><c:out value='${member.MEMBER_NAME}' /></td>
	
						</tr>
						<tr>
							<th scope="row">신청상태</th>
							<td class="left"><c:out value='${member.CONFM_STATUS_NAME}' /></td>
						</tr>
						<tr>
							<th scope="row">참석여부</th>
							<td class="left">
								<c:if test="${member.ATT_YN eq 'Y'}">참석</c:if>
								<c:if test="${member.ATT_YN eq 'N'}">불참석</c:if>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	
	
		<div class="btns-area">
			<div class="btns-right">
				<c:if test="${not empty member.CTFHV_NO}">
					<a href="javascript:void(0);" class="btn-m01 btn-color02" data-idx="<c:out value='${member.MEMBER_IDX}' />" onclick="printCertificate(event)">출력하기</a>
				</c:if>
				<a href="${URL_VIEW}&idx=<c:out value='${member.EDC_IDX}' />" class="btn-m01 btn-color01"> 목록 </a>
			</div>
		</div>
	</div>
	
	<!-- //CMS 끝 -->
	
</div>
<form action="" method="post" style="display: none;" id="form-box">
	<input type="hidden" name="idx" id="idx" value="" />
</form>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>