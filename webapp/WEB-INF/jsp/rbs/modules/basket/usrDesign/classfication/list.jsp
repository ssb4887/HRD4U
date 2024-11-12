<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}" />
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}" />
<c:set var="searchFormId" value="fn_techSupportSearchForm" />
<c:set var="listFormId" value="fn_techSupportListForm" />
<c:set var="inputWinFlag" value="" />
<%
	/* 등록/수정창 새창으로 띄울 경우 사용 */
%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}" />
<%
	/* 수정버튼 class */
%>
<script type="text/javascript" src="${contextPath}${jsPath}/basket.js"></script>
<script type="text/javascript">
	document.addEventListener("DOMContentLoaded", function() {
		var lclasCd = document.getElementById("id_lclasCd").value;
		var num = document.getElementsByClassName("topmenu3-3-"+lclasCd);
		document.getElementById("tab"+lclasCd).className += " active";
	});
	
</script>
<link rel="stylesheet" href="${contextPath}${cssPath}/modal.css" />

<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp" />
		<jsp:param name="searchFormId" value="${searchFormId}" />
		<jsp:param name="listFormId" value="${listFormId}" />
	</jsp:include>
</c:if>

<div class="tabmenu-wrapper">
	<form id="tab" name="tab" method="get" action="${contextPath}/dct/basket/resList.do">
		<input type="hidden" id="id_mId" name="mId" value="${mId}" />
		<input type="hidden" id="id_lclasCd" name="lclasCd" value="${lclasCd}" />
	</form>
	<ul>
		<c:forEach var="clLclasList" items="${clLclasList}" varStatus="i">
			<li>
				<a href="#" id="tab${i.index+1}" class="topmenu3-3-${i.index+1}" onclick="tabMove(${mId},${clLclasList.lclasCd})" >
					<c:out value="${clLclasList.lclasNm}" />
				</a>
			</li>
		</c:forEach>
	</ul>
</div>

	<div class="contents-area">
		<div class="table-type02 horizontal-scroll">
			<table>
				<caption>
					분류 관리 적극 발굴 정보표 : No, 제목, 등록자, 등록일에 관한 정보표
				</caption>
				<colgroup>
					<col style="width: 10%" />
					<col style="width: 60%" />
					<col style="width: 10%" />
					<col style="width: 20%" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">No</th>
						<th scope="col"><itui:objectItemName itemId="sclasNm" itemInfo="${itemInfo}" /></th>
						<th scope="col"><itui:objectItemName itemId="regiName" itemInfo="${itemInfo}" /></th>
						<th scope="col"><itui:objectItemName itemId="regiDate" itemInfo="${itemInfo}" /></th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${empty list}">
					<tr>
						<td colspan="4" class="bllist">
							<spring:message code="message.no.list" />
						</td>
					</tr>
				</c:if>
				<c:set var="listIdxName" value="${settingInfo.idx_name}" />
				<c:set var="listColumnName" value="${settingInfo.idx_column}" />
				<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
					<c:forEach var="listDt" items="${list}" varStatus="i">
						<c:set var="listKey" value="${listDt[listColumnName]}" />
						<tr id="column">
							<td class="num">
								<c:out value="${i.count}" />
							</td>
							<td class="sclasNm">
								<a href="#" class="open-modal01" onclick="openClassification(${listKey},${listDt.LCLAS_CD})" >
									<strong class="point-color01">
										<itui:objectView itemId="sclasNm" itemInfo="${itemInfo}" objDt="${listDt}" />
									</strong>
								</a>
							</td>
							<td class="regiName">
								<itui:objectView itemId="regiName" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
							<td class="regiDate">
								<itui:objectView itemId="regiDate" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
						</tr>
						<c:set var="listNo" value="${listNo - 1}" />
					</c:forEach>
				</tbody>
			</table>
		</div>

			<div class="paging-navigation-wrapper">
				<form action="list.do" name="pageForm" method="get">
					<!-- 페이징 네비게이션 -->
					<p class="paging-navigation" onclick="paginationHandler('pageForm')">
						<pgui:pagination listUrl="${contextPath}/dct/basket/resList.do?mId=${mId}&lclasCd=${lclasCd}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}" />
					</p>
					<!-- //페이징 네비게이션 -->
	
					<div class="sort-page-wrapper">
			            <select id="lunit" name="lunit" class="select" title="목록수" onchange="searchList();">
							<c:forEach var="listUnit" begin="${paginationInfo.unitBeginCount}" end="${paginationInfo.unitEndCount}" step="${paginationInfo.unitStep}">
								<option value="${listUnit}"<c:if test="${listUnit == paginationInfo.recordCountPerPage}"> selected="selected"</c:if>>${listUnit}개씩 조회</option>
							</c:forEach>
						</select>
					</div>
				</form>
			</div>
			<div class="btn-area">
				<div class="btns-right">
					<a href="${contextPath}/dct/basket/resList.do?mId=44" class="btn-m01 btn-color01">
						분류 예약
					</a>
				</div>
			</div>
		</div>

<!-- 모달 창 -->
<div class="mask"></div>
<!-- 분류 예외 지정 모달 -->
<div class="modal-wrapper" id="modal-action05">
	<h2>
        	소분류 관리
    </h2>
	<div class="modal-area">
        <form action="" id="editExceptForm">
        	<input type="hidden" name="resveIdx" id="resveIdx" />
        	<input type="hidden" name="sclasCd" id="id_sclasCd" />
        	<button type="button" class="btn-modal-close" onclick="closeModal('closeBtn_05')">모달창 닫기</button>
            <div class="contents-box pl0">
            	<div class="table-type02 horizontal-scroll">
            		<table>
            			<caption>
            				12
            			</caption>
            			<colgroup>
            				<col style="width: 20%;">
            				<col style="width: 80%;">
            			</colgroup>
            			<tbody>
            				<tr>
            					<th scope="row">
            						등록자
            					</th>
            					<td class="left" id="regiName">
            					
            					</td>
            				</tr>
            				<tr>
            					<th scope="row">
            						등록일
            					</th>
            					<td class="left" id="regiDate">
            					
            					</td>
            				</tr>   
            				<tr>
            					<th scope="row">
            						대분류
            					</th>
            					<td class="left" id="lclasNm">
            					
            					</td>
            				</tr>
            				<tr>
            					<th scope="row">
            						소분류명 <strong>*</strong>
            					</th>
            					<td class="left" id="sclasNm">
            					
            					</td>
            				</tr>
            				<tr>
            					<th scope="row">
            						분류 조건 <strong class="point-important">*</strong>
            					</th>
            					<td class="left" id="sclasVal">
            					
            					</td>
            				</tr>
            				<tr>
            					<th scope="row">
            						비고 (Comment)
            					</th>
            					<td class="left" id="sclasCnd">
            						<input type="text" name="sclasCnd" id="id_sclasCnd" value="" maxlength="50" />
            					</td>
            				</tr>            				            				       				            				           			
            			</tbody>
            		</table>
            	</div>
            </div>
            <div class="btns-area">
				<!-- <button type="button" id="editClassifyPostBtn" onclick="classifyEdit()" class="btn-m02 round01 btn-color03">
					<span>
						수정
					</span>
				</button>
				<button type="button" id="delClassifyPostBtn" onclick="classifyDel()" class="btn-m02 round01 btn-color04">
					<span>
						삭제
					</span>
				</button> -->
				<button type="button" id="closeBtn_05" onclick="closeModal(this.id)" class="btn-m02 round01 btn-color02">
					<span>
						취소
					</span>
				</button>
			</div>
		</form>
	</div>
</div>

<!-- //CMS 끝 -->
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>