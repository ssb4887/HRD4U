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

<c:choose>
	<c:when
		test="${cnsl.cnslType eq '1' or cnsl.cnslType eq '2' or cnsl.cnslType eq '3'}">
		<%@ include file="../corp/cnslTypeA/cnslViewFormTypeA.jsp"%>
	</c:when>
	<c:when test="${cnsl.cnslType eq '4'}">
		<%@ include file="../corp/cnslTypeB/cnslViewFormTypeB.jsp"%>
	</c:when>
</c:choose>

<div class="btns-area">

	<div class="btns-right">
		<button type="button" class="btn-m01 btn-color03"
			onclick="receiveCnsl(`${cnsl.cnslIdx}`, 30)">접수</button>
		<a href="/web/consulting/applyList.do?mId=96"
			class="btn-m01 btn-color01 depth3"> 목록 </a>
	</div>
</div>


<!-- //CMS 끝 -->


<div class="mask"></div>
<div class="modal-wrapper" id="changeCmptncBrffcPicModal">
	<h2>주치의 리스트</h2>
	<div class="modal-area">
		<div id="overlay"></div>
		<div class="loader"></div>

		<div class="contents-box pl0">

			<div class="table-type01 horizontal-scroll table-container">
				<table class="width-type03 modal-table">
					<caption>업체정보표 : 권역, 사업지역, 지원센터 에 관한 정보 제공표</caption>
					<colgroup>
						<col style="width: 20%" />
						<col style="width: 20%" />
						<col style="width: 40%" />
						<col style="width: 20%" />
					</colgroup>
					<thead class="modal-thead">
						<tr>
							<th scope="col">담당자명</th>
							<th scope="col">아이디</th>
							<th scope="col">연락처</th>
							<th scope="col">선택</th>
						</tr>
					</thead>
					<tbody id="doctorInfoList">
						
					</tbody>
				</table>
			</div>
		</div>
	</div>


	<button type="button" class="btn-modal-close"
		onclick="closeModal('changeCmptncBrffcPicModal')">모달 창 닫기</button>
</div>



<script type="text/javascript" src="${contextPath}${jsPath}/cnsl.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>