<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="inputFormId" value="fn_sampleInputForm" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}" />
		<jsp:param name="javascript_page" value="${moduleJspRPath}/input.jsp" />
		<jsp:param name="inputFormId" value="${inputFormId}" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />

<c:set var="today" value="${cnsl.regiDate}"/>
<c:set var="year"><fmt:formatDate value="${today}" pattern="yyyy" /></c:set>
<c:set var="month"><fmt:formatDate value="${today}" pattern="MM" /></c:set>
<c:set var="day"><fmt:formatDate value="${today}" pattern="dd" /></c:set>

<style>
.modal {
	display: none;
	position: fixed;
	z-index: 1;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
	background-color: #fff;
	margin: 15% auto;
	padding: 20px;
	border: 1px solid #888;
	width: 80%
}

.close {
	color: #888;
	float: right;
	font-size: 20px;
	cursor: pointer;
}
</style>


<c:choose>
	<c:when test="${cnsl.cnslType eq '1' or cnsl.cnslType eq '2' or cnsl.cnslType eq '3'}">
		<%@ include file="../corp/cnslTypeA/cnslAdminViewTypeA.jsp"%>
	</c:when>	
	<c:when test="${cnsl.cnslType eq '4' or cnsl.cnslType eq '5' or cnsl.cnslType eq '6'}">
		<%@ include file="../corp/cnslTypeB/cnslAdminViewTypeB.jsp"%>
	</c:when>
</c:choose>


		
					<div class="btns-area">
					<div class="btns-right">
							<button type="button" class="btn-m01 btn-color03 depth3"
								onclick="approveCnsl(55)">승인</button>
							<button type="button" class="btn-m01 btn-color02 depth3"
								onclick="openModal('myModal')">반려</button>

						<a href="/web/consulting/applyList.do?mId=96"
							class="btn-m01 btn-color01 depth3"> 목록 </a>
					</div>
				</div>


	<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="myModal">
	<h2>반려의견 입력</h2>
	<div class="modal-area">
		<div class="contents-box pl0">
			<form id="rejectionForm">
				<input type="hidden" id="cnslIdxByModal" value="${cnsl.cnslIdx}">
				<input type="hidden" id="statusByModal" value="40">
				<textarea id="confmCn" name="confmCn" row="4"> </textarea>
			</form>
			
		<div class="btns-area">
		<button class="btn-m02 btn-color03" onclick="rejectCnsl()">반려</button>
		<button type="button" id="closeBtn_05" onclick="closeModal('myModal')" class="btn-m02 btn-color02">
				<span> 닫기 </span>
			</button>
				</div>
		</div>
	</div>
</div>


	<script type="text/javascript" src="${contextPath}${jsPath}/cnsl.js"></script>
	<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
			page="${BOTTOM_PAGE}" flush="false" /></c:if>