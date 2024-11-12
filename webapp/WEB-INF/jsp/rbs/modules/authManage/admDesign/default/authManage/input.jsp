<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_sampleInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/input.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
	<div id="cms_board_article">
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
		
		<%-- 1. 전체 항목 출력하는 경우 --%>
		<%-- <table class="tbWriteA" summary="${summary}">
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
					<itui:itemTextButton itemId="memberIdx" itemInfo="${itemInfo}" colspan="3" objStyle="width:400px"/>
				</tr>
				<tr>
					<itui:itemText itemId="authName" itemInfo="${itemInfo}" colspan="3" objStyle="width:400px"/>
				</tr>
				<tr>
					<itui:itemRadio itemId="siteId" itemInfo="${itemInfo}" colspan="3"/>
				</tr>
				<tr style="display:none;"><td><input type="hidden" id="authList" name="authList" value=""></td></tr>
				<tr>
					<th scope="row"><label for="auth" class="required"><em class="point01">(필수)</em><span>권한</span></label></th>
					<td colspan="3">
						<div style="width:99%; border:0.5px solid;">
								<table id="web">
									<colgroup>
										<col width="10%"/>
										<col width="15%"/>
										<col width="22%"/>
										<col width="24%" />
										<col />
										<col/>
										<col />
										<col />
										<col />
										<col />
										<col />
									</colgroup>
									<thead>
										<tr>
											<th colspan="4" style="background-color:#f9f0f9; text-align:center;">메뉴명</th>
											<th>전체<input type="checkbox" class="webAllCheck" name="${listKey}" value="N" ></th>
											<th>메뉴</th>
											<th>등록</th>
											<th>조회</th>
											<th>수정</th>
											<th>삭제</th>
											<th>파일</th>
										</tr>
										
									</thead>
									
									<tbody class="checkList">	
									<c:forEach var="listDt" items="${list }" varStatus="i">
									<c:set var="listKey" value="${listDt.MENU_IDX }"/>
									<c:set var="preinfoList" value="${multiDataHashMap[listKey]}"/>
									<c:set var="infoList" value="${preinfoList[1]}"/>
									<c:choose>
										<c:when test="${listDt.SITE_ID == 'web'}">
											<tr>
											<td><c:if test="${listDt.MENU_LEVEL == 1}">${listDt.MENU_NAME}</c:if></td>
											<td><c:if test="${listDt.MENU_LEVEL == 2}">${listDt.MENU_NAME}</c:if></td>
											<td><c:if test="${listDt.MENU_LEVEL == 3}">${listDt.MENU_NAME}</c:if></td>
											<td><c:if test="${listDt.MENU_LEVEL == 4}">${listDt.MENU_NAME}</c:if></td>
											<td><label for="${listKey}"><input type="checkbox" id="${listKey }" class="lineChk" name="web${listKey}" value="" ><span style="display:none;">${listDt.VER_IDX }</span></label></td>
											<td><label for="${listKey}"><input type="checkbox" class="menu${listKey }" name="web${listKey }" value="<c:out value='${infoList.M }'/>" ><span style="display:none;">${listDt.SITE_ID }</span></label></td>
											<td><label for="${listKey}"><input type="checkbox" class="create${listKey }" name="web${listKey }" value="<c:out value='${infoList.C }'/>" ></label></td>
											<td><label for="${listKey}"><input type="checkbox" class="read${listKey }"  name="web${listKey }" value="<c:out value='${infoList.R }'/>" ></label></td>
											<td><label for="${listKey}"><input type="checkbox" class="modify${listKey }" name="web${listKey }" value="<c:out value='${infoList.U }'/>" ></label></td>
											<td><label for="${listKey}"><input type="checkbox" class="del${listKey }"  name="web${listKey }" value="<c:out value='${infoList.D }'/>" ></label></td>
											<td><label for="${listKey}"><input type="checkbox" class="file${listKey }"  name="web${listKey }" value="<c:out value='${infoList.F }'/>" ></label></td>
											<%-- <itui:objectCheckbox2 itemId="menuAuth" itemInfo="${itemInfo}" name="${listKey}"/> --%>
										</tr>
										</c:when>
									</c:choose>
										
										</c:forEach>
									</tbody>
									
								</table>
								
								<table id="dct" style="display:none;">
									<colgroup>
										<col width="10%"/>
										<col width="15%"/>
										<col width="22%"/>
										<col width="24%" />
										<col />
										<col/>
										<col />
										<col />
										<col />
										<col />
										<col />
									</colgroup>
									<thead>
										<tr>
											<th colspan="4" style="background-color:#f9f0f9; text-align:center;">메뉴명</th>
											<th>전체<input type="checkbox" class="dctAllCheck" name="${listKey}" value="N" ></th>
											<th>메뉴</th>
											<th>등록</th>
											<th>조회</th>
											<th>수정</th>
											<th>삭제</th>
											<th>파일</th>
										</tr>
										
									</thead>
									
									<tbody class="checkList">	
									<c:forEach var="listDt" items="${list }" varStatus="i">
									<c:set var="listKey" value="${listDt.MENU_IDX }"/>
									<c:set var="preinfoList" value="${multiDataHashMap[listKey]}"/>
									<c:set var="infoList" value="${preinfoList[0]}"/>
									<c:choose>
										<c:when test="${listDt.SITE_ID == 'dct'}">
											<tr>
											<td><c:if test="${listDt.MENU_LEVEL == 1}">${listDt.MENU_NAME}</c:if></td>
											<td><c:if test="${listDt.MENU_LEVEL == 2}">${listDt.MENU_NAME}</c:if></td>
											<td><c:if test="${listDt.MENU_LEVEL == 3}">${listDt.MENU_NAME}</c:if></td>
											<td><c:if test="${listDt.MENU_LEVEL == 4}">${listDt.MENU_NAME}</c:if></td>
											<td><label for="${listKey}"><input type="checkbox" id="${listKey }" class="lineChk" name="dct${listKey}" value="" ><span style="display:none;">${listDt.VER_IDX }</span></label></td>
											<td><label for="${listKey}"><input type="checkbox" class="menu${listKey }" name="dct${listKey }" value="<c:out value='${infoList.M }'/>" ><span style="display:none;">${listDt.SITE_ID }</span></label></td>
											<td><label for="${listKey}"><input type="checkbox" class="create${listKey }" name="dct${listKey }" value="<c:out value='${infoList.C }'/>" ></label></td>
											<td><label for="${listKey}"><input type="checkbox" class="read${listKey }"  name="dct${listKey }" value="<c:out value='${infoList.R }'/>" ></label></td>
											<td><label for="${listKey}"><input type="checkbox" class="modify${listKey }" name="dct${listKey }" value="<c:out value='${infoList.U }'/>" ></label></td>
											<td><label for="${listKey}"><input type="checkbox" class="del${listKey }"  name="dct${listKey }" value="<c:out value='${infoList.D }'/>" ></label></td>
											<td><label for="${listKey}"><input type="checkbox" class="file${listKey }"  name="dct${listKey }" value="<c:out value='${infoList.F }'/>" ></label></td>
											<%-- <itui:objectCheckbox2 itemId="menuAuth" itemInfo="${itemInfo}" name="${listKey}"/> --%>
										</tr>
										</c:when>
									</c:choose>
										
										</c:forEach>
									</tbody>
								</table>
						</div>
						
					</td>
				</tr>
				<tr>
					<itui:itemText itemId="note" itemInfo="${itemInfo}" colspan="3" objStyle="width:400px"/>
				</tr>
				
			</tbody>
		</table>
		<div class="btnCenter">
			<input type="submit" class="btnTypeA fn_btn_submit" value="저장" title="저장"/>
			<input type=button value="취소"  class="btnTypeB fn_btn_cancel">
		</div>
		</form>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>