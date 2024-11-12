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
<script type="text/javascript" src="${contextPath}${jsPath}/cnsl.js"></script>


<div class="contents-wrapper">
	<!-- CMS 시작 -->


	<div class="contents-area">
		<h3 class="title-type01 ml0">~~~~ 참여신청서</h3>

		<c:choose>
			<c:when test="${!empty cnsl}">
				<form id="applyForm" action="save.do?mId=85" method="post"
					enctype="multipart/form-data" modelAttribute="dto">
					
					<input type="hidden" id="bsiscnslIdx" name="bsiscnslIdx" value="${cnsl.bsiscnslIdx}"> 
					<input type="hidden" id="cnslType" name="cnslType" value="${cnsl.cnslType}"> 
					<input type="hidden" id="status" name="status" value="${cnsl.status}"> 
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
								id="corpPicOfcps" name="corpPicOfcps"
								value="${cnsl.corpPicOfcps}">
						</div>
						<div>
							<label for="file">파일첨부</label> <input type="file" id="file"
								name="file">
						</div>
				</form>

				<button type="button" onclick="saveCnsl(0)">저장</button>
				<button type="button" onclick="saveCnsl(1)">제출</button>
				<button type="button" oncliCk="deleteCnsl()">삭제</button>
			</c:when>



			<c:otherwise>


				<form id="applyForm" action="save.do?mId=85" method="post"
					enctype="multipart/form-data" modelAttribute="dto">
					<input type="hidden" id="bsiscnslIdx" name="bsiscnslIdx"
						value="${bsiscnslIdx}"> <input type="hidden" id="cnslType"
						name="cnslType" value="1"> <input type="hidden"
						id="status" name="status" value="1">
					<div>
						<label for="corpNm">기업명</label> <input type="text" id="corpNm"
							name="corpNm" value="${basket.bplNm}">
					</div>
					<div>
						<label for="reperNm">대표자명</label> <input type="text" id="reperNm"
							name="reperNm">
					</div>
					<div>
						<label for="bizrNo">사업자등록번호</label> <input type="text" id="bizrNo"
							name="bizrNo" value="${basket.bizrNo}">
						<div>
							<label for="bplNo">사업장관리번호</label> <input type="text" id="bplNo"
								name="bplNo" value="${basket.bplNo}">
						</div>
						<div>
							<label for="bplAddr">주소</label> <input type="text" id="bplAddr"
								name="bplAddr" value="${basket.bplAddr}">
						</div>
						<div>
							<label for="bplAddrDtl">상세주소</label> <input type="text"
								id="bplAddrDtl" name="bplAddrDtl" value="${basket.addrDtl}">
						</div>
						<div>
							<label for="corpPicNm">기업 담당자 성명</label> <input type="text"
								id="corpPicNm" name="corpPicNm">
						</div>
						<div>
							<label for="corpPicOfcps">직위</label> <input type="text"
								id="corpPicOfcps" name="corpPicOfcps">
						</div>
						<div>
							<label for="file">파일첨부</label> <input type="file" id="file"
								name="file">
						</div>
				</form>

				<button type="button" onclick="saveCnsl(0)">저장</button>
				<button type="button" onclick="saveCnsl(1)">제출</button>

			</c:otherwise>
		</c:choose>



	</div>


	<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
			page="${BOTTOM_PAGE}" flush="false" /></c:if>