<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_sampleInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/input.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
<style>
	#overlay {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0,0,0,0.5);
		display: none;
		z-index: 9999;
	}
	
	.loader {
		border:4px solid #f3f3f3;
		border-top: 4px solid #3498db;
		border-radius: 50%;
		width: 50px;
		height: 50px;
		animation: spin 2s linear infinite;
		position: fixed;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
		z-index: 10000;
	}
	span.update-max { margin-left: 32px; border: solid 1px; padding: 4px; border-radius: 8px; color: white; background-color: gray; cursor: pointer; }
	
	@keyframes spin {
		0% { transform: translate(-50%, -50%) rotate(0deg); }
		100% { transform: translate(-50%, -50%) rotate(360deg); }
	}
</style>

<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/edu/eduUsr.js"/>"></script>
<div id="overlay"></div>
<div class="loader"></div>

<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<div class="contents-box pl0">
<!-- 		<div class="temp-contets-area type02">신청서 양식</div> -->
	</div>
	<div class="table-type02 horizontal-scroll mb20">
	<elui:hiddenInput inputInfo="${queryString}" exceptNames="${searchFormExceptParams}"/>
		<table class="width-type02">
			<caption>교육 정보표 : 교육과정명, 교육장소, 교육기간, 접수기간</caption>
			<colgroup>
				<col style="width: 20%">
				<col style="width: 20%">
				<col style="width: 60%">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" rowspan="4" class="bg01">교육 정보</th>
					<th scope="row">교육과정명</th>
					<td class="left" id="edcName"><c:out value='${edc.EDC_NAME}' /></td>
				</tr>
				<tr>
					<th scope="row">교육장소</th>
					<td class="left"><c:out value='${edc.EDC_PLACE}' /></td>

				</tr>
				<tr>
					<th scope="row">교육기간</th>
					<td class="left">
						<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${edc.EDC_START_DATE}"/> 
						~ 
						<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${edc.EDC_START_DATE}"/> 
					</td>
				</tr>
				<tr>
					<th scope="row">접수기간</th>
					<td class="left">
						<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${edc.RECPT_BGNDT}"/>
						~
						<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${edc.RECPT_ENDDT}"/>
					</td>
				</tr>
				<c:if test="${!check}">
					<tr>
						<th scope="col" colspan="4">
							이미 신청한 교육입니다.
						</th>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

	<div class="btns-area">
		<c:if test="${check}">
			<button type="button" class="btn-b01 round01 btn-color03 left" onclick="registerMember(event)">
				<span> 신청하기 </span>
				<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" class="arrow01">
			</button>
		</c:if>
		<c:if test="${!check}">
			<button type="button" class="btn-b01 round01 btn-color06 left" onclick="cancelRegister(event)">
				<span> 취소하기 </span>
				<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01">
			</button>
		</c:if>
		<button type="button" class="btn-b01 round01 btn-color02 left" onclick="history.back();">
			<span> 목록으로 </span>
			<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01">
		</button>
	</div>

	<!-- //CMS 끝 -->
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>