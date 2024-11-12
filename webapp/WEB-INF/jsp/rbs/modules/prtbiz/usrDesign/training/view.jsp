<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/view.jsp"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
	<div id="cms_board_article" class="contents-wrapper">
		<div class="table-type02 horizontal-scroll">
		<%-- table summary, 항목출력에 사용 --%>
		<c:set var="exceptIdStr">제외할 항목id를 구분자(,)로 구분하여 입력(예:name,notice,subject,file,contents,listImg)</c:set>
		<c:set var="exceptIds" value="${fn:split(exceptIdStr,',')}"/>
		<%-- 
			table summary값 setting - 테이블 사용하지 않는 경우는 필요 없음
			디자인 문제로 제외한 항목(exceptIdStr에 추가했으나 table내에 추가되는 항목)은 수동으로 summary에 추가
			예시)
			<c:set var="summary"><itui:objectItemName itemInfo="${itemInfo}" itemId="subject"/>, <spring:message code="item.reginame1.name"/>, <spring:message code="item.regidate1.name"/>, <spring:message code="item.board.views.name"/>, <c:if test="${useFile}"><spring:message code="item.file.name"/>, </c:if><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/><spring:message code="item.contents.name"/>을 제공하는 표</c:set>
		--%>
		<c:set var="summary"><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/>을 제공하는 표</c:set>
		
		<%-- 2. 디자인에 맞게 필요한 항목만 출력하는 경우 --%>
		<table summary="${summary}" class="width-type02">
			<caption>
			상세보기
			</caption>
			<colgroup>
			<col style="width:15%;" />
			<col style="width:35%;" />
			<col style="width:15%;" />
			<col style="width:35%;" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col" colspan="4" class="bg02"><itui:objectView itemId="tpName" itemInfo="${itemInfo}" objDt="${dt}" objVal="${dt[NAME]}" optnHashMap="${optnHashMap}"/></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th scope="row">작성자</th>
					<td class="left">${dt.REGI_NAME}</td>
					<th scope="row">작성일</th>
					<td class="left"><fmt:formatDate value="${dt.REGI_DATE}" pattern="yyyy-MM-dd"/></td>
				</tr>
				<tr>
					<th scope="row">사업구분</th>
					<td colspan="3" class="left"><itui:objectSelect2 itemId="prtbizIdx" itemInfo="${itemInfo}" objDt="${prtbizList}" objVal="${submitType}" defVal="${dt}" /></td>
				</tr>
				<tr>
					<itui:itemViewCustom itemId="tpOprinst" itemInfo="${itemInfo}" />
					<itui:itemViewCustom itemId="fthtecCate" itemInfo="${itemInfo}" />
				</tr>
				<tr>
					<th scope="row">NCS 대분류</th>
					<td class="left">${dt.NCS_LCLAS_NAME}</td>
					<th scope="row">NCS 소분류</th>
					<td class="left">${dt.NCS_SCLAS_NAME}(${dt.NCS_SCLAS_CD})</td>
				</tr>
				<tr>
					<th scope="row">훈련일수</th>
					<td class="left">${dt.TR_DAYCNT}일(${dt.TRTM}시간)</td>
					<itui:itemViewCustom itemId="applyInduty" itemInfo="${itemInfo}"/>
					<%-- 추후에 적용업종을 코드로 관리하는 걸로 변경될 때 아래 코드 사용 --%>
					<%-- <th><itui:objectItemName itemId="applyInduty" itemInfo="${itemInfo}"/></th>
					<td colspan="3" class="left"><itui:objectViewMultiSelect itemId="applyInduty" objDt="${dt}" industList="${industList}"/></td> --%>
				</tr>
				<tr>
					<itui:itemViewCustom itemId="file" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<itui:itemViewCustom itemId="trSfe" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<itui:itemViewCustom itemId="xpteffect" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<itui:itemViewCustom itemId="trGoal" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<itui:itemViewCustom itemId="trnreqm" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<th scope="row">훈련내용</th>
					<td colspan="3">
						<div class="table-type02">
							<table id="tpSubContent">
								<colgroup>
									<col style="width:6%">	
									<col style="width:25%">	
									<col style="width:6%">	
									<col style="width:46%">	
									<col style="width:20%">	
								</colgroup>
								<thead>
									<tr>
										<th scope="col">번호</th>
										<th scope="col">교과목</th>
										<th scope="col">시간</th>
										<th scope="col">내용</th>
										<th scope="col">교수 기법</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="subDt" items="${tpSubList}" varStatus="i">
										<tr>
											<td><c:out value="${subDt.COURSE_NO}" /></td>
											<td><c:out value="${subDt.COURSE_NAME}" /></td>
											<td><c:out value="${subDt.TIME}" /></td>
											<td class="left">${subDt.CN}</td>
											<td><c:out value="${subDt.TCHMETHOD}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="btns-area mt60">
			<div class="btns-right">
				<a href="<c:out value="${URL_MODIFY}"/>&tpIdx=${dt.TP_IDX}" class="btn-m01 btn-color03">수정</a>
				<a href="<c:out value="${URL_DELETEPROC}&prtbizIdx=${dt.PRTBIZ_IDX}&tpIdx=${dt.TP_IDX}"/>" title="삭제" class="btn-m01 btn-color02 fn_btn_delete">삭제</a>
				<a href="${contextPath}/${crtSiteId}/prtbiz/view.do?mId=89&prtbizIdx=${dt.PRTBIZ_IDX}" title="목록" class="btn-m01 btn-color01 fn_btn_write">목록</a>
			</div>
		</div>
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>