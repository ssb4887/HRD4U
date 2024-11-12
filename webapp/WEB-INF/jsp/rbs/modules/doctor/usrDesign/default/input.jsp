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
		<div class="contents-box pl0">
			<h3 class="title-type02 ml0">기본정보</h3>
			<div class="board-write">
				<div class="one-box">
					<dl>
						<dt><label class="textfiled01">성명</label>&nbsp;<strong class="point-important">*</strong></dt>
						<dd>
							<c:choose>
								<c:when test="${submitType eq 'write'}">
									<div class="input-add-btns-wrapper">
										<input type="text" name="userName" id="userName" readonly placeholder="주치의 명">
										<button type="button" class="btn-color02" id="open-modal01" style="height:50px !important;">찾기</button>
									</div>
								</c:when>
								<c:otherwise>${dt.HNAME}</c:otherwise>
							</c:choose>
							<input type="hidden" name="memberIdx" id="memberIdx" <c:if test="${submitType eq 'modify'}">value="${dt.USERID}"</c:if>>
						</dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt>아이디&nbsp;<strong class="point-important">*</strong></dt>
						<dd id="loginId"><c:if test="${submitType eq 'modify'}">${dt.LOGINID}</c:if></dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt>이메일</dt>
						<dd id="email"><strong class="point-color01"><c:if test="${submitType eq 'modify'}">${dt.EMAIL}</c:if></strong></dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt>주소</dt>
						<dd id="address"><c:if test="${submitType eq 'modify'}">${dt.ADDRESS}</c:if></dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt><itui:objectItemName itemId="clsfCd" itemInfo="${itemInfo}"/></dt>
						<dd style="display:flow;"><itui:objectRadioCustom itemId="clsfCd" itemInfo="${itemInfo}"/></dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt><itui:objectItemName itemId="doctorOfcps" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
						<dd><itui:objectText itemId="doctorOfcps" itemInfo="${itemInfo}" objClass="w100"/></dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt><itui:objectItemName itemId="doctorDept" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
						<dd><itui:objectText itemId="doctorDept" itemInfo="${itemInfo}" objClass="w100"/></dd>
					</dl>
				</div>
				<div class="one-box">
					<dl>
						<dt><itui:objectItemName itemId="doctorTel" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt>
						<dd><itui:objectText itemId="doctorTel" itemInfo="${itemInfo}" objClass="w100"/></dd>
					</dl>
				</div>
				<!-- 공단소속 변경신청 메뉴에서 등록되는 내용과 구분하기 위해 사용 -->
				<input type="hidden" name="applyYn" value="Y">
				<input type="hidden" name="status" value="1">
			</div> 
		</div>
		<div class="contents-box pl0">
			<h3 class="title-type02 ml0">주치의 요건</h3>
			<input type="hidden" name="doctorYn" id="doctorYn">
			<ul class="form-raido-list01">
				<c:forEach var="reqDt" items="${reqMngList}" varStatus="i">
					<li>
						<p>${reqDt.RQISIT_CN}<input type="hidden" name="reqMngIdx${i.count}" value="${reqDt.DOCTOR_RQISIT_IDX}"></p>
						<div class="radio-btns-type-wrapper01">
							<div class="radio-btns-type-area01">
								<input type="radio" id="isSatisfy${i.count}-Y" name="isSatisfy${i.count}" value="1" <c:if test="${reqList[i.index].ANSWER_CN eq '1'}"> checked </c:if>/>
					            <label for="isSatisfy${i.count}-Y">예</label>
							</div>
							<div class="radio-btns-type-area01">
								<input type="radio" id="isSatisfy${i.count}-N" name="isSatisfy${i.count}" value="0" <c:if test="${reqList[i.index].ANSWER_CN eq '0'}"> checked </c:if>/>
					            <label for="isSatisfy${i.count}-N">아니오</label>
							</div>
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
						<dt>소속기관 <input type="hidden" id="insttIdx" name="insttIdx" <c:if test="${submitType eq 'modify'}">value="${dt.INSTT_IDX}"</c:if>/></dt>
						<dd id="instt"><c:if test="${submitType eq 'modify'}">${dt.INSTT_NAME}</c:if></dd>
					</dl>
					<div id="blockList">
						<c:if test="${submitType eq 'modify'}">						
							<c:forEach var="block" items="${allBlockList}" varStatus="i">
								<c:set var="check" value="false"/>
								<c:choose>
									<c:when test="${i.first || block.SIDO eq allBlockList[i.index -1].SIDO}">
										<c:if test="${i.first}"><dl><dt>${allBlockList[0].SIDO}</dt><dd style="display: flow;"><div class="input-checkbox-wrapper ratio type02"></c:if>
										<c:forEach var="info" items="${blockInfo}" varStatus="j">
											<c:if test="${info.BLOCK_CD eq block.BLOCK_CD}">
												<c:set var="check" value="true"/>
												<div class="input-checkbox-area">
													<input type="checkbox" name="blockCode" id="blockCode${i.count}" class="checkbox-type01 margin10" value="${block.BLOCK_CD}" data-count="${doctorList[i.index].DOCTOR_COUNT}" checked><label for="blockCode${i.count}">${block.CLASS_NAME}</label>
												</div>
											</c:if>
										</c:forEach>
										<c:if test="${check eq false}"><div class="input-checkbox-area"><input type="checkbox" name="blockCode" id="blockCode${i.count}" class="checkbox-type01 margin10" value="${block.BLOCK_CD}" data-count="${doctorList[i.index].DOCTOR_COUNT}"><label for="blockCode${i.count}">${block.CLASS_NAME}</label></div></c:if>
										<c:if test="${block.SIDO ne allBlockList[i.index +1].SIDO || empty allBlockList[i.index +1]}"></div></dd></dl></c:if>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${(block.SIDO ne allBlockList[i.index -1].SIDO && block.SIDO eq allBlockList[i.index +1].SIDO) || (block.SIDO ne allBlockList[i.index -1].SIDO && i.last)}">
												<dl><dt>${block.SIDO}</dt>
													<dd style="display: flow;"><div class="input-checkbox-wrapper ratio type02">
													<c:forEach var="info" items="${blockInfo}" varStatus="j">
														<c:if test="${info.BLOCK_CD eq block.BLOCK_CD}">
															<c:set var="check" value="true"/>
															<div class="input-checkbox-area">
																<input type="checkbox" name="blockCode" id="blockCode${i.count}" class="checkbox-type01 margin10" value="${block.BLOCK_CD}" data-count="${doctorList[i.index].DOCTOR_COUNT}" checked><label for="blockCode${i.count}">${block.CLASS_NAME}</label>
															</div>
														</c:if>
													</c:forEach>
													<c:if test="${check eq false}"><div class="input-checkbox-area"><input type="checkbox" name="blockCode" id="blockCode${i.count}" class="checkbox-type01 margin10" value="${block.BLOCK_CD}" data-count="${doctorList[i.index].DOCTOR_COUNT}"><label for="blockCode${i.count}">${block.CLASS_NAME}</label></div></c:if>
											</c:when>
											<c:otherwise><div class="input-checkbox-area"><input type="checkbox" name="blockCode" id="blockCode${i.count}" class="checkbox-type01 margin10" value="${block.BLOCK_CD}" data-count="${doctorList[i.index].DOCTOR_COUNT}"><label for="blockCode${i.count}">${block.CLASS_NAME}</label></div></c:otherwise>
										</c:choose>
										<c:if test="${empty allBlockList[i.index +1].SIDO}"></div></dd></dl></c:if>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<div class="btns-area">
			<div class="btns-right">
				<button type="submit" class="btn-m01 btn-color03 depth2 fn_btn_submit">저장</button>
				<a href="<c:out value="${URL_LIST}"/>" title="목록" class="btn-m01 btn-color01 depth2 fn_btn_write">목록</a>
			</div>
		</div>
		</form>
	</div>
	
	<!-- 회원 찾기 모달창 -->
	<div class="mask"></div>
	<div class="modal-wrapper" id="modal-action01">
		<h2>회원 찾기</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<div class="basic-search-wrapper">
					<div class="one-box">
						<dl>
							<dt><label for="hName">이름</label></dt>
							<dd><input type="text" name="hName" id="hName" class="hName" value="" title="이름 입력" placeholder="이름"></dd>
						</dl>
					</div>
					<div class="btns-area">
						<button type="button" class="btn-m02 btn-color02 round01 fn-search-name">검색</button>
					</div>
				</div>
			</div>
			<div class="contents-box pl0">
				<p class="total mb05">총 <strong id="cnt">0</strong>건</p>
			</div>
			<div class="table-type01 horizontal-scroll">
				<table class="width-type03">
	            <caption>회원정보표 : 아이디, 이름에 관한 정보 제공표</caption>
	                <colgroup>
	                    <col style="width: 10%" />
	                    <col style="width: 15%" />
	                    <col style="width: 35%" />
	                    <col style="width: 40%" />
	                </colgroup>
	                <thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">선택</th>
							<th scope="col">이름</th>
							<th scope="col">아이디</th>
						</tr>
	                </thead>
	                <tbody id="searchList">
		                <tr id="empty">
		                	<td colspan="4"></td>
		                </tr>
	                </tbody>
                </table>
			</div>
		</div>
		<button type="button" class="btn-modal-close">모달 창 닫기</button>
	</div>
	<!-- 회원 찾기 모달창 끝 -->
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>