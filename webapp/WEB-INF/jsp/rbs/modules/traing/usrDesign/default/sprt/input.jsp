<%@ include file="../../../../../include/commonTop.jsp"%>
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
	<div id="cms_board_article" class="contents-wrapper">
		<h3 class="title-type02 h3_textbox">최근 직업훈련 지원 현황 등록</h3>
		<div class="one-box space">
			<div class="half-box half-box-margin">
				<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
				<%-- table summary, 항목출력에 사용 --%>
				<c:set var="exceptIdStr">제외할 항목id를 구분자(,)로 구분하여 입력(예:name,notice,subject,file,contents,listImg)</c:set>
				<c:set var="exceptIds" value="${fn:split(exceptIdStr,',')}"/>
				<%-- 
					table summary값 setting - 테이블 사용하지 않는 경우는 필요 없음
					디자인 문제로 제외한 항목(exceptIdStr에 추가했으나 table내에 추가되는 항목)은 수동으로 summary에 추가
					예시)
					<c:set var="summary"><itui:objectItemName itemInfo="${itemInfo}" itemId="subject"/>, <spring:message code="item.reginame1.name"/>, <spring:message code="item.regidate1.name"/>, <spring:message code="item.board.views.name"/>, <c:if test="${useFile}"><spring:message code="item.file.name"/>, </c:if><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/><spring:message code="item.contents.name"/>을 제공하는 표</c:set>
				--%>
				<c:set var="summary"><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/> 입력표</c:set>
				<%-- 2. 디자인에 맞게 필요한 항목만 출력하는 경우 --%>
					<div class="table-type02">
					<table summary="${summary}" id="sprt">
						<caption>
						글쓰기 서식
						</caption>
						<colgroup>
						<col style="width:30%;" />
						<col style="width:70%;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="col"><itui:objectItemName itemId="indutyCode" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></th>
								<td colspan="2"><itui:objectSelect itemId="indutyCode" itemInfo="${itemInfo}"/></td>
							</tr>
							<tr>
								<th scope="col"><itui:objectItemName itemId="year" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></th>
								<td colspan="2"><itui:objectYEAR itemId="year" itemInfo="${itemInfo}" objClass="w100 padding10"/></td>
							</tr>
							<tr>
								<th scope="col"><itui:objectItemName itemId="range" itemInfo="${itemInfo}"/></th>
								<th scope="col"><itui:objectItemName itemId="pay" itemInfo="${itemInfo}"/>(원)&nbsp;<strong class="point-important">*</strong></th>
							</tr>
							<tr id="trRange1">
								<th scope="col">5인 미만 <input type="hidden" name="range" value="5"></th>
								<td><input type="text" name="pay" id="pay1" class="right w100 padding10"></td>
							</tr>
							<tr id="trRange2">
								<th scope="col">5인 이상 30인 미만 <input type="hidden" name="range" value="30"></th>
								<td><input type="text" name="pay" id="pay2" class="right w100 padding10"></td>
							</tr>
							<tr id="trRange3">
								<th scope="col">30인 이상 100인 미만 <input type="hidden" name="range" value="100"></th>
								<td><input type="text" name="pay" id="pay3" class="right w100 padding10"></td>
							</tr>
							<tr id="trRange4">
								<th scope="col">100인 이상 300인 미만 <input type="hidden" name="range" value="300"></th>
								<td><input type="text" name="pay" id="pay4" class="right w100 padding10"></td>
							</tr>
							<tr id="trRange5">
								<th scope="col">300인 이상 1000인 미만 <input type="hidden" name="range" value="1000"></th>
								<td><input type="text" name="pay" id="pay5" class="right w100 padding10"></td>
							</tr>
							<tr id="trRange6">
								<th scope="col">1000인 이상 <input type="hidden" name="range" value="10000000"></th>
								<td><input type="text" name="pay" id="pay6" class="right w100 padding10"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="btns-area mt60">
					<input type="hidden" name="industCdName" id="industCdName">
					<button type="submit" class="btn-m01 btn-color03 depth3_1 fn_btn_submit">저장</button>
					<a href="${contextPath}/${crtSiteId}/traing/list.do?mId=40" class="btn-m01 depth3_2 btn-color01">목록</a>
				</div>
				</form>
			</div>
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>