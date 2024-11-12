<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ include file="../../../../../include/top.jsp"%>
<c:set var="page_tit" value="${settingInfo.input_title}"/>
<%@ include file="../../../authManage/input.jsp"%>
<c:set var="inputFormId" value="fn_authInputForm"/>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
	<div id="cms_board_article">
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
		<%-- table summary, 항목출력에 사용 --%>
		<c:set var="exceptIdStr"></c:set>
		<c:set var="exceptIds" value="${fn:split(exceptIdStr,',')}"/>
		<%-- 
			table summary값 setting - 테이블 사용하지 않는 경우는 필요 없음
			디자인 문제로 제외한 항목(exceptIdStr에 추가했으나 table내에 추가되는 항목)은 수동으로 summary에 추가
			예시)
			<c:set var="summary"><itui:objectItemName itemInfo="${itemInfo}" itemId="subject"/>, <spring:message code="item.reginame1.name"/>, <spring:message code="item.regidate1.name"/>, <spring:message code="item.board.views.name"/>, <c:if test="${useFile}"><spring:message code="item.file.name"/>, </c:if><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/><spring:message code="item.contents.name"/>을 제공하는 표</c:set>
		--%>
		<c:set var="summary"><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/> 입력표</c:set>
		
		<%-- 1. 전체 항목 출력하는 경우
		<table class="tbWriteA" summary="${summary}">
			<caption>
			글쓰기 서식
			</caption>
			<colgroup>
			<col style="width:150px;" />
			<col />
			</colgroup>
			<tbody>
				<itui:itemInputAll itemInfo="${itemInfo}" itemOrderName="${submitType}_order" exceptIds="${exceptIds}"/>
			</tbody>
		</table> --%>
		
		<%-- 2. 디자인에 맞게 필요한 항목만 출력하는 경우 --%>
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
				<tr>
					<th>권한그룹</th>
					<td id="authIdx" colspan="3">
					<select name="authIdx" id="authIdx" class="select" title="소속그룹" style="width:150px;">
						<option value="">그룹명</option>	
						<c:forEach var="listDt" items="${list }">
							<option value="${listDt.AUTH_IDX}">${listDt.AUTH_NAME}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<itui:itemTextButton itemId="memberIdx" itemInfo="${itemInfo}" colspan="3" objStyle="width:400px"/>
				</tr>
				<tr>
					<th>아이디</th>
					<td id="id" colspan="3"><c:out value="${dt.MEMBER_ID }"/><c:out value="${dt.REGIMENT }"/></td>
				</tr>
				<tr>
					<th>연락처</th>
					<td id="tel" colspan="3"><c:out value="${dt.MOBILE_PHONE }"/></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td id="email" colspan="3"><c:out value="${dt.MEMBER_EMAIL }"/></td>
				</tr>
				<tr>
					<th>소속기관</th>
					<td id="position" colspan="3"><c:out value="${dt.REGIMENT }"/></td>
				</tr>
				
				
			</tbody>
		</table>
		<div class="btnCenter">
			<input type="submit" class="btnTypeA fn_btn_submit" value="저장" title="저장"/>
			<input type=button value="취소"  class="btnTypeB fn_btn_cancel">
		</div>
		</form>
	</div>
