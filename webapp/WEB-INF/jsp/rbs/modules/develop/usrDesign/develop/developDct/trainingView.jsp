<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/develop/trainingView.jsp"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
	<div id="cms_board_article">
		<div class="contents-area" class="contents-wrapper">
		<h3 class="title-type01 ml0">훈련과정 상세보기</h3>

		<%-- 2. 디자인에 맞게 필요한 항목만 출력하는 경우 --%>
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
            	<table class="width-type02">
            		<caption>능력개발클리닉 참여 신청서 상세보기 폼</caption>
            		<colgroup>
                        <col width="15%">
                        <col width="35%">
                        <col width="15%">
                        <col width="35%">
                    </colgroup>
                    <tbody>
                    	<tr>
                    		<th scope="row" colspan="4" style="font-size:25px;">${tpList.TP_NAME}</th>
                    	</tr>
                    	<tr>
                    		<th scope="row">사업구분</th>
                    		<td colspan="3" class="left">${tpList.BIZ_TYPE2 }</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">NCS 대분류</th>
                    		<td class="left">${tpList.L_NCS_NM }</td>
                    		<th scope="row">NCS 소분류</th>
                    		<td class="left">${tpList.S_NCS_NM }</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">훈련일수</th>
                    		<td class="left">${tpList.TR_DAYCNT }일<c:if test="${!empty tpList.TRTM }">(${tpList.TRTM} 시간)</c:if></td>
                    		<th scope="row">적용업종</th>
                    		<td class="left">${tpList.APPLY_INDUTY }</td>
                    	</tr>
<!--                     	<tr> -->
<!--                     		<th scope="row">파일</th> -->
<!--                     		<td colspan="3"> -->
<%--                     			<itui:objectViewCustom itemId="file" itemInfo="${itemInfo}"/> --%>
<!--                     		</td> -->
<!--                     	</tr> -->
                    	<tr>
                    		<th scope="row">훈련목표</th>
                    		<td colspan="3" class="left">${tpList.TR_GOAL }</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">주요 훈련내용</th>
                    		<td colspan="3" class="left">${tpList.TR_SFE }</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">훈련대상요건</th>
                    		<td colspan="3" class="left">${tpList.TRNREQM }</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">훈련내용 </th>
                    		<td colspan="3">
                    			<div class="table-type02 horizontal-scroll">
                    			<table class="width-type02">
                    				<colgroup>
				                        <col style="width:6%">	
										<col style="width:25%">	
										<col style="width:44%">	
										<col style="width:8%">	
				                    </colgroup>
				                    <tbody>
				                    	<tr>
				                    		<th>번호</th>
				                    		<th>교과목</th>
				                    		<th>내용</th>
				                    		<th>시간</th>
				                    	</tr>
				                    	<c:forEach items="${tpSubList }" varStatus="i">
				                    	<tr>
				                    		<td>${i.count }</td>
				                    		<td>${tpSubList[i.index].COURSE_NAME }</td>
				                    		<td class="left">${tpSubList[i.index].CN }</td>
				                    		<td>${tpSubList[i.index].TIME }시간</td>
				                    	</tr>
				                    	</c:forEach>
				                    </tbody>
                    			</table>
                    			</div>
                    		</td>
                    	</tr>
                    </tbody>
            	</table>
            </div>
		</div>
		<div class="btns-area">
			<div class="btns-right">
				<c:if test="${isFromDevelop eq 'Y' }">
				<a href="<c:out value="${DEVELOP_WRITE_FORM_URL}"/>&tpIdx=${tpList.TP_IDX}&prtbizIdx=${tpList.PRTBIZ_IDX }&bplNo=${corpInfo.BPL_NO }&devlopIdx=${devlopIdx}&useFlag=use" class="btn-m01 btn-color03 depth2 fn_btn_modify">추천 활용</a>						
				<a href="<c:out value="${DEVELOP_WRITE_FORM_URL}"/>&tpIdx=${tpList.TP_IDX}&prtbizIdx=${tpList.PRTBIZ_IDX }&bplNo=${corpInfo.BPL_NO }&devlopIdx=${devlopIdx}&useFlag=reuse" class="btn-m01 btn-color03 depth2 fn_btn_modify">과정 수정</a>
				</c:if>
				<a href="#" title="이전" class="btn-m01 btn-color01 depth2 fn_btn_write" onclick="history.back();">이전</a>
				<%-- <a href="<c:out value="${DEVELOP_TRAINING_LIST_FORM_URL}&bplNo=${corpInfo.BPL_NO}&devlopIdx=${devlopIdx}"/>" title="목록" class="btn-m01 btn-color01 depth2 fn_btn_write">목록</a> --%>
			</div>
		</div>
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>