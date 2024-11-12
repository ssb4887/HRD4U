<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_insttInputForm"/>
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
		<c:set var="summary"><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/> 입력표</c:set>
		<div class="contents-area">
			<h3 class="title-type01 ml0">기본정보</h3>
			<div class="board-write">
				<div class="one-box">
					<dl>
						<dt><itui:objectItemName itemId="insttName" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
						<dd><itui:objectText itemId="insttName" itemInfo="${itemInfo}" objClass="w100"/></dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt><itui:objectItemName itemId="insttNo" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
						<dd><itui:objectText itemId="insttNo" itemInfo="${itemInfo}" objClass="w100"/></dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt><itui:objectItemName itemId="locCode" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
						<dd style="display:flow;"><itui:objectSelectClassCustom itemId="locCode" itemInfo="${itemInfo}" objClass="two-select" objClass2="select-last"/></dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt><itui:objectItemName itemId="phone" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
						<dd><itui:objectText itemId="phone" itemInfo="${itemInfo}" objClass="w100"/></dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt><itui:objectItemName itemId="homepage" itemInfo="${itemInfo}"/></dt>
						<dd><itui:objectText itemId="homepage" itemInfo="${itemInfo}" objClass="w100"/></dd>
					</dl>
				</div>
				<div class="one-box">
					<dl class="board-write-contents">
						<dt><itui:objectItemName itemId="remarks" itemInfo="${itemInfo}"/></dt>
						<dd><itui:objectTextarea itemId="remarks" itemInfo="${itemInfo}"/></dd>
					</dl>
				</div>
				<div class="one-box">
					<dl class="board-write-contents">
						<dt><itui:objectItemName itemId="orderIdx" itemInfo="${itemInfo}"/></dt>
						<dd><itui:objectText itemId="orderIdx" itemInfo="${itemInfo}" objClass="w100"/></dd>
					</dl>
				</div>
			</div>
		</div>
		<div class="contents-area">
			<div class="one-box">
				<div style="display:flex;"><h3 class="title-type01 ml0">관할구역</h3><span class="span-explain">※ 다른 지부지사의 관할구역은 선택할 수 없습니다.</span></div>
				<div class="board-write">
					<itui:itemMultiCheckbox itemId="blockCode" itemInfo="${itemInfo}" objDt="${dt}" objClass="checkbox-type01" objTotalList="${list}" objOptionList="${optionList}" submitType="${submitType}"/>
				</div>
			</div>
		</div>
		<div class="btns-area">
			<div class="btns-right">
				<button type="submit" class="btn-m01 btn-color03 depth3 fn_btn_submit" title="저장">저장</button>
				<button type=button class="btn-m01 btn-color02 depth3 fn_btn_cancel">취소</button>
			</div>
		</div>
		</form>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>