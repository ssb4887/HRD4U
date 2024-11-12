<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_authInputForm"/>
<c:set var="inputSubFormId" value="fn_inputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/inputDctCenter.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
	<div id="cms_board_article" class="contents-wrapper">
				
		<c:set var="exceptIdStr"></c:set>
		<c:set var="exceptIds" value="${fn:split(exceptIdStr,',')}"/>
		
		<div class="contents-area">
			<div class="contents-box pl0">
				<h3 class="title-type02 ml0">기본정보</h3>
				<div class="table-type02 horizontal-scroll">
					<table summary="${summary}">
						<caption>기본정보표 : 성명, 아이디, 연락처, 이메일, 주소에 관한 정보 제공표</caption>
						<colgroup>
							<col style="width:15%">
							<col style="width:35%">
							<col style="width:15%">
							<col style="width:35%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">성명</th>
								<td class="left">${dt.MEMBER_NAME }</td>
								<th scope="row">아이디</th>
								<td>${dt.MEMBER_ID }</td>
							</tr>
							<tr>
								<th scope="row">연락처</th>
								<td class="left" id="mobile">${dt.MOBILE_PHONE }</td>
								<th scope="row">이메일</th>
								<td>${dt.MEMBER_EMAIL }</td>
							</tr>
							<tr>
								<th scope="row">주소</th>
								<td colspan="3" class="left">${dt.ADDR1 } ${dt.ADDR2 } ${dt.ADDR3 }</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="contents-box pl0">
				<h3 class="title-type02 ml0">기관정보</h3>
				<div class="table-type02 horizontal-scroll">
					<table summary="${summary}">
						<caption>기관정보표 : 성명, 아이디, 연락처, 이메일, 주소에 관한 정보 제공표</caption>
						<colgroup>
							<col style="width:15%">
							<col style="width:35%">
							<col style="width:15%">
							<col style="width:35%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">기관명</th>
								<td colspan="3" class="left">${dtCenter.CORP_NM}</td>
							</tr>
							<tr>
								<th scope="row">대표자명</th>
								<td class="left" id="mobile">${dtCenter.REPVE_NM}</td>
								<th scope="row">전화번호</th>
								<td>${dtCenter.CHRGR_WIRE_PHON_NUM}</td>
							</tr>
							<tr>
								<th scope="row">주소</th>
								<td colspan="3" class="left">${dtCenter.CO_ADDR}&nbsp;${dtCenter.CO_ADDR_DTL}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<%-- <div class="contents-box pl0">
				<div class="title-wrapper">
					<h3 class="title-type02 ml0 fl">현재권한</h3>
				</div>
				
				<div class="table-type01 horizontal-scroll">
					<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
						<caption><c:out value="${settingInfo.list_title}"/>권한신청정보표 : No, 신청일, 활성화, 시작일, 종료일, 권한그룹, 승인상태, 승인자명, 승인일에 관한 정보 제공표</caption>
						<colgroup>
							<col style="width:33%">
							<col style="width:33%">
							<col style="width:33%">
						</colgroup>
						<thead>
						<tr>
							<th scope="col">권한그룹</th>
							<th scope="col">승인자명(ID)</th>
							<th scope="col" class="end">승인일</th>
						</tr>
						</thead>
						<tbody class="alignC">
							<c:if test="${empty dtAuth}">
							<tr>
								<td colspan="3" class="bllist"><spring:message code="message.no.list"/></td>
							</tr>
							</c:if>
							<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
							<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
							<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
							
							<c:forEach var="listDt" items="${dtAuth}" varStatus="i">
							<tr>
								<td>${listDt.AUTH_NAME}</td>
								<td>${listDt.LAST_MODI_NAME}(${listDt.LAST_MODI_ID})</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.LAST_MODI_DATE}"/></td>
								</tr>
							<c:set var="listNo" value="${listNo - 1}"/>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div> --%>
			<%-- <div class="contents-box pl0">
				<div class="title-wrapper">
					<h3 class="title-type02 ml0 fl">권한신청</h3>
					<form id="${inputSubFormId}" name="${inputSubFormId}" method="post" action="<c:out value="${URL_SECSUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
					<div class="btns-right">
				        <button type="submit" class="btn-m01 btn-color01" id="centerSubmit" data-idx="${dt.MEMBER_IDX }">
				            	권한 신청
				        </button>
				        <a href="<c:out value="${URL_CENTERDELETEPROC}"/>" title="삭제" class="btnTD fn_btn_delete btn-m01 btn-color01">삭제</a>
			    	</div>		
					<input type="hidden" name="memberIdx" id="memberIdx" value="<c:out value="${dt.MEMBER_IDX}"/>"/>
					</form>	 
				</div>
				
				<div class="table-type01 horizontal-scroll">
					<form id="fn_inputForm" name="fn_inputForm" method=post target="list_target" enctype="multipart/form-data">
					<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
						<caption><c:out value="${settingInfo.list_title}"/>권한신청정보표 : No, 신청일, 활성화, 시작일, 종료일, 권한그룹, 승인상태, 승인자명, 승인일에 관한 정보 제공표</caption>
						<colgroup>
							<col style="width:5%">
							<col style="width:10%">
							<col style="width:8%">
							<col style="width:16%">
							<col style="width:16%">
							<col style="width:14%">
							<col style="width:10%">
							<col style="width:11%">
							<col style="width:10%">
						</colgroup>
						<thead>
						<tr>
							<th scope="col">No</th>
							<th scope="col">신청일</th>
							<th scope="col">활성화</th>
							<th scope="col">시작일</th>
							<th scope="col">종료일</th>
							<th scope="col">권한그룹</th>
							<th scope="col">승인상태</th>
							<th scope="col">승인자명</th>
							<th scope="col" class="end">승인일</th>
						</tr>
						</thead>
						<tbody class="alignC">
							<c:if test="${empty dtReq}">
							<tr>
								<td colspan="9" class="bllist"><spring:message code="message.no.list"/></td>
							</tr>
							</c:if>
							<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
							<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
							<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
							
							<c:forEach var="listDt" items="${dtReq}" varStatus="i">
							
							<c:set var="listKey" value="${listDt.MEMBER_IDX}"/>
							<c:set var="corpIdxName" value="corpNum"/>
							<c:set var="corpKey" value="${listDt.CORP_NUM}"/>
							
							<tr>
								<td><c:if test="${listDt.APPLY_YN != 'Y'}"><input type="checkbox" class="checkbox-type01" name="select" title="<spring:message code="item.select"/><c:out value="${listNo}"/>" value="${listDt.DOCTOR_IDX}"/></c:if></td>
								<td class="reqIdx" style="display:none;">${listDt.AUTH_REQ_IDX}</td>
								<td class="num">${listNo}</td>
								<td class="regiDate"><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
								<td class="apply">${listDt.APPLY_YN }</td>
								<td class="startDate">
								<c:choose>
									<c:when test="${!empty listDt.START_DATE }">${listDt.START_DATE }</c:when>
									<c:when test="${empty listDt.START_DATE && listDt.STATUS == '2'}">
											<itui:objectYMDForCenStart itemId="startDate" itemInfo="${itemInfo }" objStyle="width:120px;"/>
									</c:when>
								</c:choose>
								</td>
								<td class="endDate">
								<c:choose>
									<c:when test="${!empty listDt.END_DATE }">${listDt.END_DATE }</c:when>
									<c:when test="${empty listDt.END_DATE && listDt.STATUS == '2'}">
										<itui:objectYMDForCenEnd itemId="endDate" itemInfo="${itemInfo }" objStyle="width:120px;"/>
									</c:when>
								</c:choose>
								</td>
								<td>
									<c:choose>
									<c:when test="${!empty listDt.AUTH_NAME}">${listDt.AUTH_NAME }</c:when>
									<c:when test="${empty listDt.AUTH_NAME && listDt.STATUS == '2'}">
											<select id="authIdx" name="authIdx" style="width:120px;">
												<option value="">그룹명</option>
												<c:forEach var="authList" items="${authList }" varStatus="i">
													<option value="${authList.AUTH_IDX }">${authList.AUTH_NAME}</option>
												</c:forEach>
											</select>
									</c:when>
									</c:choose>					
								</td>
								<td>
									<c:choose>
									<c:when test="${listDt.STATUS == '1'}">승인</c:when>
									<c:when test="${listDt.STATUS == '0'}">반려</c:when>
									<c:when test="${listDt.STATUS == '2'}">
										<select id="status" name="status" style="width:80px;">
											<option value="">선택</option>
											<option value="Y">승인</option>
											<option value="N">반려</option>
										</select>
										
									</c:when>
									</c:choose>
								</td>
								<td>${listDt.CONFMER_NAME}</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.CONFMER_DATE}"/></td>
								<td style="display:none;"><input type="hidden" id="memberIdx" name="memberIdx" value="${dt.MEMBER_IDX }"></td>
								<td style="display:none;"><input type="hidden" id="centerList" name="centerList" value=""></td>
								</tr>
							<c:set var="listNo" value="${listNo - 1}"/>
							</c:forEach>
						</tbody>
					</table>
					</form>
				</div>
			</div> --%>
			
			<div class="btns-area">
	           <div class="btns-right">
	               <a href="javascript:history.back();" class="btn-m01 btn-color01">목록</a>
	           </div>
		     </div>
		</div>
	</div>
	
	
	<div class="mask"></div>
    <div class="modal-wrapper" id="modal-action01">
        <h2>
            알림
        </h2>
        <div class="modal-area">
        <form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
            <input type="hidden" id="reqIdx" name="reqIdx" value="">
            <input type="hidden" id="memberIdx" name="memberIdx" value="${dt.MEMBER_IDX }">
			<input type="hidden" id="startDateVal" name="startDateVal" value="">
			<input type="hidden" id="endDateVal" name="endDateVal" value="">
			<input type="hidden" id="auth" name="auth" value="">
			<input type="hidden" id="apply" name="apply" value="">
			
            <div class="modal-alert">
                <p>
                    2023-01-01부터 2023-12-31까지<br /> 권한이 부여됩니다.<br /> 변경하시겠습니까?
                </p>
            </div>


            <div class="btns-area">
                <button type="submit" class="btn-m02 round01 btn-color03" form="${inputFormId}">
                    <span>
                        확인
                    </span>
                </button>

                <button type="button" class="btn-m02 round01 btn-color02">
                    <span>
                        취소
                    </span>
                </button>
            </div>
            </form>
        </div>

        <button type="button" class="btn-modal-close">
            모달 창 닫기
        </button>
    </div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>