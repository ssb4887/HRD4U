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
	<div id="cms_board_article">
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
		<div class="contents-box pl0">
			<h3 class="title-type02 ml0">기본정보</h3>
			<div class="board-write">
				<div class="one-box">
					<dl>
						<dt><label class="textfiled01">성명</label></dt>
						<dd>${dt.HNAME}</dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt>아이디</dt>
						<dd>${dt.LOGINID}</dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt>이메일</dt>
						<dd><strong class="point-color01">${dt.EMAIL}</strong></dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt>주소</dt>
						<dd>${dt.ADDRESS}</dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt><itui:objectItemName itemId="clsfCd" itemInfo="${itemInfo}"/></dt>
						<dd>
							<c:choose>
								<c:when test="${dt.CLSF_CD eq 'Y'}">예</c:when>
								<c:otherwise>아니오</c:otherwise>
							</c:choose>
						</dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt><itui:objectItemName itemId="doctorOfcps" itemInfo="${itemInfo}"/></dt>
						<dd><itui:objectView itemId="doctorOfcps" itemInfo="${itemInfo}"/></dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt><itui:objectItemName itemId="doctorDept" itemInfo="${itemInfo}"/></dt>
						<dd><itui:objectView itemId="doctorDept" itemInfo="${itemInfo}"/></dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt><itui:objectItemName itemId="doctorTel" itemInfo="${itemInfo}"/></dt>
						<dd><itui:objectView itemId="doctorTel" itemInfo="${itemInfo}"/></dd>
					</dl>
				</div>
			</div>
		</div>
		<div class="contents-box pl0">
			<h3 class="title-type02 ml0">주치의 요건</h3>
			<ul class="form-raido-list01">
				<c:forEach var="reqDt" items="${reqMngList}" varStatus="i">
					<li>
						<p>${reqDt.RQISIT_CN}</p>
						<div class="radio-btns-type-wrapper01">
							<c:choose>
								<c:when test="${reqList[i.index].ANSWER_CN eq '1'}">
									<div class="radio-btns-type-area01"><label>예</label></div>
								</c:when>
								<c:otherwise>
									<div class="radio-btns-type-area01"><label>아니오</label></div>
								</c:otherwise>
							</c:choose>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="contents-box pl0">
			<h3 class="title-type02 ml0">공단소속</h3>
			<div class="board-write">
				<div class="one-box" id="insttBlock">
					<dl>
						<dt>소속기관 </dt>
						<dd>${dt.INSTT_NAME}</dd>
					</dl>
					<div id="blockList">
						<dl>
							<dt>관할구역</dt>
							<dd style="display: flow;">
								<div class="input-checkbox-wrapper ratio type02">
								<c:choose>
									<c:when test="${dt.ISCHANGE eq '1'}"><span style="color:red;"><c:out value="관할구역 변경 필요"/></span></c:when>
									<c:otherwise>
										<c:forEach var="block" items="${blockInfo}" varStatus="i">
											<c:choose>
												<c:when test="${i.first || block.SIDO eq blockInfo[i.index -1].SIDO}">
													<c:if test="${i.first}">${block.SIDO}&nbsp;</c:if>
														${block.CLASS_NAME}&nbsp;
													<c:if test="${block.SIDO ne blockInfo[i.index +1].SIDO || empty blockInfo[i.index +1]}"><br></c:if>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${(block.SIDO ne blockInfo[i.index -1].SIDO && block.SIDO eq blockInfo[i.index +1].SIDO) || (block.SIDO ne blockInfo[i.index -1].SIDO && i.last)}">
															${block.SIDO}&nbsp;${block.CLASS_NAME}&nbsp;
														</c:when>
														<c:otherwise>${block.CLASS_NAME}</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:otherwise>
								</c:choose>
								</div>
							</dd>
						</dl>
					</div>
				</div>
			</div>
		</div>
		<div class="btns-area">
			<div class="btns-right">
				<a href="<c:out value="${URL_MODIFY}"/>&doctorIdx=${dt.DOCTOR_IDX}" class="btn-m01 btn-color03 depth2 fn_btn_modify">수정</a>
				<a href="<c:out value="${URL_DELETEPROC}"/>&doctorIdx=${dt.DOCTOR_IDX}" class="btn-m01 btn-color02 depth2 fn_btn_delete">삭제</a>
				<a href="<c:out value="${URL_LIST}"/>" class="btn-m01 btn-color01 depth2 fn_btn_write">목록</a>
			</div>
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>