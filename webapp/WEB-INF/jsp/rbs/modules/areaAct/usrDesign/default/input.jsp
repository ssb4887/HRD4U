<%@ include file="../../../../include/commonTop.jsp"%>
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
	<div id="contents-wrapper">
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
		<legend class="blind">글쓰기</legend>
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
		
		
		<div class="board-write">
			<div class="one-box">
				<dl>
					<itui:itemTextForArea itemId="title" itemInfo="${itemInfo}" colspan="3" objStyle="width:400px"/>
				</dl>
			</div>
			<div class="one-box">
				<dl>
					<itui:itemYEARForArea itemId="year" itemInfo="${itemInfo}" />
				</dl>
			</div>
			<div class="one-box">
				<dl>
					<itui:itemCheckboxArea itemId="insttIdx" itemInfo="${itemInfo}" colspan="3"/>
				</dl>
			</div>
			<div class="one-box">
				<dl>
					<c:choose>
						<c:when test="${!empty multiDataHashMap }">
							<itui:itemRadioArea itemId="insttIdx" itemInfo="${itemInfo}" colspan="3"/>
						</c:when>
						<c:otherwise>
							<dt>총괄소속기관<strong class="point-important">*</strong></dt>
							<dd id="regi"><div class="input-checkbox-wrapper ratio"></div></dd>
						</c:otherwise>
					</c:choose>
				</dl>
			</div>
		</div>
		<table class="tbWriteA" summary="${summary}">
			<caption>
			글쓰기 서식
			</caption>
			<colgroup>
			<col style="width:150px;" />
			<col />
			<col style="width:150px;" />
			<col />
			</colgroup>
			<tbody>
				
				
				<%-- <tr>
					<itui:itemTextarea itemId="sampleItemId" itemInfo="${itemInfo}" colspan="3" objStyle="width:400px"/>
					<itui:itemRadio itemId="sampleItemId" itemInfo="${itemInfo}"/>
				</tr>
				
				<tr>
					<itui:itemYMD itemId="sampleItemId" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr>
					<itui:itemPHONE itemId="sampleItemId" itemInfo="${itemInfo}" objType="0" objClass1="select" objStyle1="width:80px" objStyle2="width:80px" objStyle3="width:80px"/>
					<itui:itemPHONE itemId="sampleItemId" itemInfo="${itemInfo}" objType="1" objClass1="select" objStyle1="width:80px" objStyle2="width:80px" objStyle3="width:80px"/>
				</tr>
				<tr>
					<itui:itemEMAIL itemId="sampleItemId" itemInfo="${itemInfo}" objStyle="width:95%" colspan="3"/>
				</tr>
				<tr>
					<itui:itemADDR itemId="sampleItemId" itemInfo="${itemInfo}" colspan="3"/>
				</tr> --%>
			</tbody>
		</table>
		<div class="btns-area">
        	<div class="btns-right">
            	<button type="submit" class="btn-m01 btn-color03 depth2">저장</button>
				<a href="javascript:history.back();	" class="btn-m01 btn-color01 depth2">취소</a>
            </div>
        </div>
		</form>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>