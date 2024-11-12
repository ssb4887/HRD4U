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
	<div id="cms_board_article" class="contents-wrapper">
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
		<div class="board-write">
			<div class="one-box">
				<dl>
					<dt><itui:objectItemName itemId="tpName" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
					<dd><itui:objectText itemId="tpName" itemInfo="${itemInfo}" objClass="w100"/></dd>
				</dl>
			</div>
			<div class="one-box">
				<dl>
					<dt>작성자&nbsp;<strong class="point-important">*</strong></dt>
					<dd>${nameDt.NAME}</dd>
				</dl>
			</div>
			<div class="one-box">
				<dl>
					<dt><itui:objectItemName itemId="prtbizIdx" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
					<dd>
						<c:set var="prtbizIdxList" value="${fn:split(prtbizList.PRTBIZ_IDX_LIST, ',')}" />
						<c:set var="prtbizNameList" value="${fn:split(prtbizList.PRTBIZ_NAME_LIST, ',')}" />
						<c:set var="prtbizIdx_val" value=""/>
						<c:choose>
							<c:when test="${submitType eq 'write'}"><c:set var="prtbizIdx_val" value="${prtbizIdx}"/></c:when>
							<c:otherwise><c:set var="prtbizIdx_val" value="${dt.PRTBIZ_IDX}"/></c:otherwise>
						</c:choose>
						<c:forEach var="prtbiz" items="${prtbizIdxList}" varStatus="i">
							<c:if test="${prtbiz eq prtbizIdx_val}">
								<c:out value="${prtbizNameList[i.index]}" />
							</c:if>
						</c:forEach>
						<input type="hidden" name="prtbizIdx" id="prtbizIdx" value="${prtbizIdx_val}" />
					</dd>
					<!-- 사업을 선택할 수 있도록 바뀌면 아래 코드 적용 -->
					<%-- <dd><itui:objectSelect2 itemId="prtbizIdx" itemInfo="${itemInfo}" objDt="${prtbizList}" objVal="${submitType}" defVal="${dt}" idx="${prtbizIdx}" objClass="w100"/></dd> --%>
				</dl>
			</div>
			<div class="one-box">
				<div class="half-box">
					<dl>
						<dt><itui:objectItemName itemId="tpOprinst" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
						<dd><itui:objectText itemId="tpOprinst" itemInfo="${itemInfo}" objClass="w100"/></dd>
					</dl>
				</div>
				<div class="half-box">
					<dl>
						<dt><itui:objectItemName itemId="fthtecCate" itemInfo="${itemInfo}"/></dt>
						<dd><itui:objectText itemId="fthtecCate" itemInfo="${itemInfo}" objClass="w100"/></dd>
					</dl>
				</div>
			</div>
			<div class="one-box">
				<div class="half-box">
					<dl>
						<dt><itui:objectItemName itemId="trDaycnt" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
						<dd><itui:objectText itemId="trDaycnt" itemInfo="${itemInfo}" objClass="w100"/></dd>
					</dl>
				</div>
				<div class="half-box">
					<dl>
						<dt><itui:objectItemName itemId="trtm" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
						<dd><itui:objectText itemId="trtm" itemInfo="${itemInfo}" objClass="w100"/></dd>
					</dl>
				</div>
			</div>
			<div class="one-box">
				<dl>
					<dt><itui:objectItemName itemId="ncsSclasCode" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
					<dd style="display:flow"><itui:objectSelectClassCustom itemId="ncsSclasCode" itemInfo="${itemInfo}" optnHashMap="${optnHashMap}" objClass="three-select" objClass2="select-last"/></dd>
				</dl>
			</div>
			<div class="one-box">
				<dl>
					<dt><itui:objectItemName itemId="applyInduty" itemInfo="${itemInfo}"/></dt>
					<dd><itui:objectText itemId="applyInduty" itemInfo="${itemInfo}" objClass="w100"/></dd>
					<%-- 추후에 적용업종을 코드로 관리하는 걸로 변경될 때 아래 코드 사용 및 data/rbs/module/prtbiz/2/itemInfo.json 수정하기 > applyInduty 항목 중 required_write와 required_write 0으로 변경, object_type 2로 변경, writeproc_order와 modifyproc_order에서 applyInduty 제거--%>
					<%-- <dd style="display:flow" id="apply"><itui:objectMultiSelectClass itemId="applyInduty" itemInfo="${itemInfo}" objDt="${dt}" masterCode="${industMaster}" industList="${industList}" defVal="${prtbizIdx}" prtbizList="${prtbizList}"/></dd> --%>
				</dl>
			</div>
			<div class="one-box">
				<dl class="board-write-contents">
					<dt><itui:objectItemName itemId="file" itemInfo="${itemInfo}"/></dt>
					<dd><itui:objectMultiFileCustom itemId="file" itemInfo="${itemInfo}" objClass="${objClass}"/></dd>
				</dl>
			</div>
			<div class="one-box">
				<dl class="board-write-contents">
					<dt><itui:objectItemName itemId="trSfe" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
					<dd><itui:objectTextarea itemId="trSfe" itemInfo="${itemInfo}"/></dd>
				</dl>
			</div>
			<div class="one-box">
				<dl class="board-write-contents">
					<dt><itui:objectItemName itemId="xpteffect" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
					<dd><itui:objectTextarea itemId="xpteffect" itemInfo="${itemInfo}"/></dd>
				</dl>
			</div>
			<div class="one-box">
				<dl class="board-write-contents">
					<dt><itui:objectItemName itemId="trGoal" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
					<dd><itui:objectTextarea itemId="trGoal" itemInfo="${itemInfo}"/></dd>
				</dl>
			</div>
			<div class="one-box">
				<dl class="board-write-contents">
					<dt><itui:objectItemName itemId="trnreqm" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
					<dd><itui:objectTextarea itemId="trnreqm" itemInfo="${itemInfo}"/></dd>
				</dl>
			</div>
			<div class="one-box">
				<dl class="board-write-contents">
					<dt>훈련내용&nbsp;<strong class="point-important">*</strong></dt>
					<dd><itui:objectTableView objVal="${submitType}" itemInfo="${itemInfo}" pageContext="${pageContext}"/></dd>
				</dl>
			</div>
		</div>
		<div class="btns-area">
			<div class="btns-right">
				<button type="submit" class="btn-m01 btn-color03 depth3 fn_btn_submit" title="저장">저장</button>
				<button type="button" class="btn-m01 btn-color02 depth3 fn_btn_cancel">취소</button>
			</div>
		</div>
		</form>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>