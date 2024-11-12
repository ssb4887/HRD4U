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
<link rel="stylesheet" href="${contextPath}${cssPath}/modal.css" />

<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp" />
		<jsp:param name="searchFormId" value="${searchFormId}" />
		<jsp:param name="listFormId" value="${listFormId}" />
	</jsp:include>
</c:if>

<script type="text/javascript">
	$(document).ready(function(){
		var today = $.datepicker.formatDate('yy-mm-dd', new Date());
		$.datepicker.setDefaults($.datepicker.regional['ko']);
		$('#resve_date').datepicker("option", "minDate", today);
		$('#resve_date2').datepicker("option", "minDate", today);
	});
</script>

<div class="contents-area">
	<div class="title-wrapper">
		<c:if test="${wrtAuth}">
			<button class="btn-m01 btn-color01 fr" id="open-modal02" onclick="reserveModalOpen(this.id, '${status}')">등록</button>
		</c:if>
	</div>
	<div class="table-type02 horizontal-scroll">
	<form id="${listFormId}" action="${contextPath}/dct/basket/resList.do?mId=${mId}" method="get" name="${listFormId}" method="post" target="list_target">
		<input type="hidden" id="id_mId" name="mId" value="${mId}" />
		<table class="listTypeA" summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
			<caption>
				<c:out value="${settingInfo.list_title}" />
				목록
			</caption>
			<colgroup>
				<col style="width: 20%;">
				<col style="width: 20%;">
				<col style="width: 20%;">
				<col style="width: 25%;">
				<col style="width: 15%;">
			</colgroup>
			<thead>
				<tr id="column">
					<th scope="col">번호</th>
					<th scope="col"><itui:objectItemName itemId="resveDate" itemInfo="${itemInfo}" /></th>
					<th scope="col"><itui:objectItemName itemId="resveHour" itemInfo="${itemInfo}" /></th>
					<th scope="col"><itui:objectItemName itemId="regiName" itemInfo="${itemInfo}" /></th>
					<th scope="col"><itui:objectItemName itemId="status" itemInfo="${itemInfo}" /></th>
				</tr>
			</thead>
			<tbody class="alignC">
				<c:if test="${empty list}">
					<tr>
						<td colspan="6" class="bllist">
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
							<strong class="point-color01">
								<c:out value="${i.count}" />
							</strong>
							<a href="#" class="btn-linked open-modal01" onclick="reserveModalEdit(${listKey},${i.count})" >
								<img src="${contextPath}${imgPath}/icon/icon_search04.png" alt="아이콘" />
							</a>
						</td>
						<td class="resveDate">
							<c:out value="${fn:substring(listDt.RESVE_DATE,0,4)}" />-<c:out value="${fn:substring(listDt.RESVE_DATE,4,6)}" />-<c:out value="${fn:substring(listDt.RESVE_DATE,6,8)}" />
						</td>
						<td class="resveMinute">
							<itui:objectView itemId="resveHour" itemInfo="${itemInfo}" objDt="${listDt}" />:<c:out value="${fn:substring(listDt.RESVE_MINUTE,0,2)}" />
						</td>
						<td class="regiName">
							<itui:objectView itemId="regiName" itemInfo="${itemInfo}" objDt="${listDt}" />
						</td>
						<td class="result">
							<itui:objectView itemId="status" itemInfo="${itemInfo}" objDt="${listDt}" />
						</td>
					</tr>
					<c:set var="listNo" value="${listNo - 1}" />
				</c:forEach>
			</tbody>
		</table>

		<!-- 페이지 내비게이션 -->
		<div class="paging-navigation-wrapper">
			<p class="paging-navigation">
				<pgui:pagination listUrl="${contextPath}/dct/basket/resList.do?mId=${mId}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}" />
			</p>
			<div class="sort-page-wrapper">
	            <select id="lunit" name="lunit" class="select" title="목록수" onchange="searchList();">
				<c:forEach var="listUnit" begin="${paginationInfo.unitBeginCount}" end="${paginationInfo.unitEndCount}" step="${paginationInfo.unitStep}">
					<option value="${listUnit}"<c:if test="${listUnit == paginationInfo.recordCountPerPage}"> selected="selected"</c:if>>${listUnit}개씩 조회</option>
				</c:forEach>
				</select>
	        </div>
		</div>
	</form>
	<!-- //페이지 내비게이션 -->
</div>

<!-- 모달 창 -->
<div class="mask"></div>
<!-- 분류 예외 지정 모달 -->
<div class="modal-wrapper" id="modal-action05">
	<h2>
            분류 예약 수정
    </h2>
	<div class="modal-area">
        <form action="" id="editExceptForm">
        	<input type="hidden" name="resveIdx" id="resveIdx" />
            <div class="contents-box pl0">
            	<div class="basic-search-wrapper">
            		<div class="one-box">
            			<dl>
            				<dt>
								<label>
									No
								</label>
            				</dt>
            				<dd>
            					<p class="word" id="editNo">
            						
            					</p>
            				</dd>
            			</dl>
            		</div>
            		<div class="one-box">
            			<dl>
            				<dt>
								<label>
									등록자
								</label>
            				</dt>
            				<dd>
            					<p class="word" id="editName">
            						
            					</p>
            				</dd>
            			</dl>
            		</div>
            		<div class="one-box">
            			<dl>
            				<dt>
								<label for="resve_date">
									일시
								</label>
            				</dt>
            				<dd>
            					<div class="input-calendar-area m-w100">
                                    <input type="text" id="resve_date" name="resve_date" class="sdate" />
                                </div>

                                <div class="input-time-area">
                                    <input type="time" id="resve_time" name="resve_time" />
                                </div>
            				</dd>
            			</dl>
            		</div>
            	</div>
            </div>
            <div class="btns-area">
				<button type="button" id="editExceptPostBtn" onclick="reserveEdit()" class="btn-m02 round01 btn-color03">
					<span>
						수정
					</span>
				</button>
				<button type="button" id="delExceptPostBtn" onclick="reserveDel()" class="btn-m02 round01 btn-color04">
					<span>
						삭제
					</span>
				</button>
				<button type="button" id="closeBtn_05" onclick="closeModal(this.id)" class="btn-m02 round01 btn-color02">
					<span>
						취소
					</span>
				</button>
			</div>
		</form>
	</div>
</div>

<!-- 분류 예외 지정 모달 -->
<div class="modal-wrapper" id="modal-action06">
	<h2>
            분류 예약 등록
    </h2>
	<div class="modal-area">
        <form action="" id="addExceptForm">
        	<button type="button" class="btn-modal-close" onclick="closeModal('closeBtn_06')">모달창 닫기</button>
            <div class="contents-box pl0">
            	<div class="basic-search-wrapper">
            		<div class="one-box">
            			<dl>
            				<dt>
								<label>
									등록자
								</label>
            				</dt>
            				<dd>
            					<p class="word">
            						<c:out value="${loginVO.memberName}" />      
            					</p>
            				</dd>
            			</dl>
            		</div>
            		<div class="one-box">
            			<dl>
            				<dt>
								<label for="resve_date">
									일시
								</label>
            				</dt>
            				<dd>
            					<div class="input-calendar-area m-w100">
                                    <input type="text" id="resve_date2" name="resve_date" class="sdate" readonly />
                                </div>

                                <div class="input-time-area">
                                    <input type="time" id="resve_time2" name="resve_time" />
                                </div>
            				</dd>
            			</dl>
            		</div>
            	</div>
            </div>
            <div class="btns-area">
				<button type="button" id="addExceptPostBtn" onclick="reserveExcept()" class="btn-m02 round01 btn-color03">
					<span>
						등록
					</span>
				</button>
				<button type="button" id="closeBtn_06" onclick="closeModal(this.id)" class="btn-m02 round01 btn-color02">
					<span>
						취소
					</span>
				</button>
			</div>
			</form>
		</div>
	</div>
</div>


<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>