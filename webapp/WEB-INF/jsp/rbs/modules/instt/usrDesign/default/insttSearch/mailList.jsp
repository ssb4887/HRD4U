<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:set var="searchFormId" value="fn_mailListSearchForm"/>
<c:set var="listFormId" value="fn_mailListListForm"/>
<c:set var="inputWinFlag" value=""/><%/* 등록/수정창 새창으로 띄울 경우 사용 */%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/><%/* 수정버튼 class */%>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/mailList.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>

	<div id="cms_board_article" class="contents-wrapper">
		<!-- search -->
		<itui:searchFormItemZip divClass="tbMSearch fn_techSupportSearch contents-area02" contextPath="${contextPath}" imgPath="${imgPath}"  formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_MAILLIST}" itemListSearch="${itemInfo.mailList_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch btn-search02" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList btn-search02 btn-color03"/>
		<!-- //search -->
		<div class="title-wrapper">
			<p class="total fl">총 <strong>${paginationInfo.totalRecordCount}</strong>건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)</p>
			<div class="fr">
				<!-- 검색조건이 있을 때 -->
				<c:if test="${!empty param.key || !empty param.is_insttIdx || !empty param.is_blockCode || !empty param.sido}"><c:set var="searchList" value="&is_insttIdx=${param.is_insttIdx}&sido=${param.sido}&gugun=${param.gugun}&is_blockCode=${param.is_blockCode}&keyField=${param.keyField}&key=${param.key}"/></c:if>
				<button type="button" class="btn-m01 btn-color03" id="excel_download" onclick="location.href='excel.do?mId=${crtMenu.menu_idx}${searchList}'" style="margin-right: 10px;">엑셀 다운로드</button>
                <a href="#" class="btn-m01 btn-color01 open-modal01" id="open-modal01" >일괄변경</a>
                <a href="#" class="btn-m01 btn-color01 open-modal02" id="open-modal02">등록</a>
				<%-- <a href="<c:out value="${URL_DELETEPROC}"/>" title="삭제" class="btnTD fn_btn_delete btn-m01 btn-color01">삭제</a> --%>
            </div>
		</div>
		<div class="table-type01 horizontal-scroll">
		<!-- //페이지 내비게이션 -->
		<form id="${listFormId}" name="${listFormId}" method="post" target="list_target">
		<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
			<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
			<colgroup>
				<col width="6%" />
				<col width="10%" />
				<col width="10%" />
				<col width="15%" />
				<col width="15%" />
				<col width="10%" />
				<col width="20%" />
			</colgroup>
			<thead>
			<tr>
				<th scope="col"><input type="checkbox" class="checkbox-type01" id="selectAll" name="selectAll" title="<spring:message code="item.select.all"/>"/></th>
				<th scope="col">No</th>
				<th scope="col"><spring:message code="item.modify.name"/></th>
				<th scope="col"><itui:objectItemName itemId="locCode" itemInfo="${itemInfo}"/></th>
				<th scope="col"><itui:objectItemName itemId="locCode" itemInfo="${itemInfo}"/></th>
				<th scope="col">우편번호</th>
				<th scope="col"><itui:objectItemName itemId="insttName" itemInfo="${itemInfo}"/></th>
			</tr>
			</thead>
			<tbody class="alignC">
				<c:if test="${empty list}">
				<tr>
					<td colspan="7" class="bllist"><spring:message code="message.no.list"/></td>
				</tr>
				</c:if>
				<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
				<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
				<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
				<c:forEach var="listDt" items="${list}" varStatus="i">
				<%-- <c:if test="${listDt.INSTT_IDX ne list[i.index -1].INSTT_IDX}"> --%>
				<c:set var="listKey" value="${listDt[listColumnName]}"/>
				<tr>
					<td><input type="checkbox" name="select" class="checkbox-type01" title="<spring:message code="item.select"/><c:out value="${listNo}"/>" value="${listDt.ZIP}" data-zip="${listDt.ZIP}" data-ctp="${listDt.CTPRVN}" data-signgu="${listDt.SIGNGU}" data-insttIdx="${listDt.INSTT_IDX}"/></td>
					<td class="num">${listNo}</td>
					<td><a href="#" class="btn-m03 btn-color03 open-modal03" data-zip="${listDt.ZIP}" data-ctp="${listDt.CTPRVN}" data-signgu="${listDt.SIGNGU}" data-insttIdx="${listDt.INSTT_IDX}">수정</a></td>
					<td>${listDt.CTPRVN}</td>
					<td>${listDt.SIGNGU}</td>
					<td>${listDt.ZIP}</td>
					<td>${listDt.INSTT_NAME}</td>
					<%-- <td><itui:objectView itemId="insttName" itemInfo="${itemInfo}" objDt="${listDt}"/></td> --%>
				</tr>
				<c:set var="listNo" value="${listNo - 1}"/>
				
				</c:forEach>
			</tbody>
		</table>
		</form>
		</div>
		<!-- 페이지 내비게이션 -->
		<div class="paging-navigation-wrapper">
			<pgui:pagination listUrl="${URL_MAILLIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
		</div>
	</div>
	
	<!-- 모달창 : 지부지사 일괄변경 -->
	<div class="mask"></div>
    <div class="modal-wrapper" id="modal-action01">
        <h2>
            지부지사 일괄변경
        </h2>
        <div class="modal-area">
            <form action="">
                <div class="contents-box pl0">
                    <div class="basic-search-wrapper">
                        <div class="one-box">
                            <dl>
                                <dt>
                                    <label for="insttAll">지부지사</label>
                                </dt>
                                <dd>
                                    <select id="insttAll" name="">
                                        <c:forEach var="listDt" items="${insttList}" varStatus="i">
                                    		<option value="${listDt.OPTION_CODE }">${listDt.OPTION_NAME }</option>
                                    	</c:forEach>
                                    </select>
                                </dd>
                            </dl>
                        </div>
                        <div class="one-box">
                            <dl>
                                <dt>
                                	<label>우편번호</label>
                                </dt>
                                <dd>
                                    <ul id="mailList">
                                    </ul>
                                </dd>
                            </dl>
                        </div>
                    </div>
                    <div class="btns-area">
                        <button type="button" class="btn-m02 round01 btn-color03" id="fn-Allmail-submit"><span>저장</span></button>
                        <button type="button" class="btn-m02 round01 btn-color02 fn-search-cancel"><span>취소</span></button>
                    </div>
                </div>
            </form>
        </div>

        <button type="button" class="btn-modal-close">
            모달 창 닫기
        </button>
    </div>
    <!-- 모달창 : 지부지사 일괄변경 -->
	
	 <!-- 모달창 : 우편번호 등록 -->
    <div class="modal-wrapper" id="modal-action02">
        <h2>우편번호 등록</h2>
        <div class="modal-area">
            <form id="mailInsertProc" method="post" action="${URL_SUBMITPROC}" target="submit_target" enctype="multipart/form-data">
				
                <div class="contents-box pl0">
                    <div class="basic-search-wrapper">
                        <div class="one-box">
                            <dl>
                                <dt>
                                    <label for="zip">우편번호</label>
                                </dt>
                                <dd>
                                    <div class="w100">
                                        <input type="text" id="zip" name="zip" value="" oninput="this.value= this.value.replace(/[^0-9.]/g,'').replace(/(|..*)\./g, '$1');">
                                        <!-- <button type="button" class="btn-color02" style="height:100%;">중복체크</button> -->
                                    </div>
                                </dd>
                            </dl>
                        </div>
                        <div class="one-box">
                            <dl>
                                <dt>
                                    <label for="sido">관할구역</label>
                                </dt>
                                <dd>
                                    <select id="sido" name="sido" class="w50"></select>
                                    <select id="gugun" name="gugun" class="w50"></select>
                                </dd>
                            </dl>
                        </div>

                        <div class="one-box">
                            <dl>
                                <dt>
                                    <label for="instt">소속기관</label>
                                </dt>
                                <dd>
                                    <select id="instt" name="insttIdx">
                                    	<c:forEach var="listDt" items="${insttList}" varStatus="i">
                                    		<option value="${listDt.OPTION_CODE }">${listDt.OPTION_NAME }</option>
                                    	</c:forEach>
                                    </select>
                                </dd>
                            </dl>
                        </div>
                    </div>
                    <div class="btns-area">
                        <button type="submit" class="btn-m02 round01 btn-color03 fn-input"><span>저장</span></button>
                        <button type="button" class="btn-m02 round01 btn-color02 fn-search-cancel"><span>취소</span></button>
                    </div>
                </div>
            </form>
        </div>

        <button type="button" class="btn-modal-close">
            모달 창 닫기
        </button>
    </div>
    <!-- 모달창 : 우편번호 등록 -->
	
	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>