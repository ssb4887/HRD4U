<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}" />
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/view.jsp" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />
<script type="text/javascript" src="${contextPath}${jsPath}/cnsl.js"></script>

<div class="tabmenu-wrapper">
	<ul>
		<li><a href="#" class="tablinks" onclick="openTab(event, 'tab1')">
				컨설팅 변경 신청 </a></li>
		<li><a href="#" class="tablinks" onclick="openTab(event, 'tab2')">
				컨설팅 수행일지 </a></li>
		<li><a href="#" class="tablinks" onclick="openTab(event, 'tab3')">
				보고서 검토 </a></li>
	</ul>
</div>

<input type="hidden" name="mId" id="id_mId" value="64">

<!-- 컨설팅 변경 신청 탭 -->
<div class="tabcontent" id="tab1">
<div class="contents-wrapper">
	<!-- CMS 시작 -->

	<div class="contents-area">
		<form id="applyForm" action="save.do?mId=85" method="post"
			enctype="multipart/form-data" modelAttribute="dto">
			<input type="hidden" id="bsiscnslIdx" name="bsiscnslIdx"
				value="${cnsl.bsiscnslIdx}"> <input type="hidden" id="cnslType"
				name="cnslType" value="1"> <input type="hidden" id="status"
				name="status" value="1">
				<input type="hidden" id="cnslIdx" name="cnslIdx" value="${cnsl.cnslIdx}">
			<div>
				<label for="corpNm">기업명</label> <input type="text" id="corpNm"
					name="corpNm" value="${cnsl.corpNm}">
			</div>
			<div>
				<label for="reperNm">대표자명</label> <input type="text" id="reperNm"
					name="reperNm" value="${cnsl.reperNm}">

			</div>
			<div>
				<label for="bizrNo">사업자등록번호</label> <input type="text" id="bizrNo"
					name="bizrNo" value="${cnsl.bizrNo}">
				<div>
					<label for="bplNo">사업장관리번호</label> <input type="text" id="bplNo"
						name="bplNo" value="${cnsl.bplNo}">
				</div>
				<div>
					<label for="bplAddr">주소</label> <input type="text" id="bplAddr"
						name="bplAddr" value="${cnsl.bplAddr}">
				</div>
				<div>
					<label for="bplAddrDtl">상세주소</label> <input type="text"
						id="bplAddrDtl" name="bplAddrDtl" value="${cnsl.bplAddrDtl}">
				</div>
				<div>
					<label for="corpPicNm">기업 담당자 성명</label> <input type="text"
						id="corpPicNm" name="corpPicNm" value="${cnsl.corpPicNm}">
				</div>
				<div>
					<label for="corpPicOfcps">직위</label> <input type="text"
						id="corpPicOfcps" name="corpPicOfcps" value="${cnsl.corpPicOfcps}">
				</div>
				<div>
					<label for="file">파일첨부</label> <input type="file" id="file"
						name="file">
				</div>
		</form>

		<button type="button" onclick="saveCnsl(0)">저장</button>
		<button type="button">제출</button>
	</div>

</div>

<!-- 컨설팅 수행일지 탭 -->
<div class="tabcontent" id="tab2"></div>

<!-- 보고서 검토 탭 -->
<div class="tabcontent" id="tab3"></div>



</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>