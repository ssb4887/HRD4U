<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_authInputForm"/>
<c:set var="inputSubFormId" value="fn_inputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/inputDctEmploy.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
		
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
			
			<%-- <div class="contents-box pl0">
				<div class="title-wrapper">
					<h3 class="title-type02 ml0 fl">공단소속</h3>
					<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
					<div class="fr">
                        <a href="#" class="btn-m01 btn-color01" id="employSubmit" >등록</a>
                        <!-- <a href="#" class="btn-m01 btn-color01" id="employSubmit" onclick="return chk_form()">등록</a> -->
                    </div>
					<input type="hidden" name="memberIdx" id="memberIdx" value="<c:out value="${dt.MEMBER_IDX}"/>"/>
	        		<input type="hidden" name="ofcps" id="ofcps" value=""/>
	        		<input type="hidden" name="deptName" id="deptName" value=""/>
	        		<input type="hidden" name="telNo" id="telNo" value=""/>
					</form>	
				</div>
				
				<div  style="display:none;">
				<c:forEach var="listDt" items="${dtDoctor}" varStatus="i">
	        		<c:if test="${listDt.APPLY_YN == 'Y' }">
	        			<p id="preRegi">${listDt.INSTT_IDX}</p>
	        		</c:if>
				</c:forEach>
				</div>
				
				<div class="table-type01 horizontal-scroll">
					<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
						<caption><c:out value="${settingInfo.list_title}"/>공단소속 정보표 : No, 신청일, 활성화, 소속기관, 직급, 승인상태, 승인자명, 승인일에 관한 정보 제공표</caption>
						<colgroup>
                            <col style="width: 5%" />
                            <col style="width: 12%" />
                            <col style="width: 8%" />
                            <col style="width: 28%" />
                            <col style="width: 14%" />
                            <col style="width: 10%" />
                            <col style="width: 11%" />
                            <col style="width: 12%" />
                        </colgroup>
						<thead>
						<tr>
							<th scope="col">No</th>
							<th scope="col">신청일</th>
							<th scope="col">활성화</th>
							<th scope="col">소속기관</th>
							<th scope="col">직급</th>
							<th scope="col">승인상태</th>
							<th scope="col">승인자명</th>
							<th scope="col" class="end">승인일</th>
						</tr>
						</thead>
						<tbody class="alignC">
							<c:if test="${empty dtDoctor}">
							<tr>
								<td colspan="8" class="bllist"><spring:message code="message.no.list"/></td>
							</tr>
							</c:if>
							<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
							<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
							<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
							
							<c:forEach var="listDt" items="${dtDoctor}" varStatus="i">
							
							<c:set var="listKey" value="${listDt.MEMBER_IDX}"/>
							
							<tr>
								<td class="docIdx" style="display:none;">${listDt.DOCTOR_IDX}</td>
								<td class="num">${listNo}</td>
								<td class="regiDate"><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}"/></td>
								<td class="apply">${listDt.APPLY_YN }</td>
								<td>
									<c:choose>
									<c:when test="${empty listDt.INSTT_IDX && empty listDt.APPLY_YN}">
										<select id="insttIdx" name="insttIdx" style="width:150px;">
												<option value="">소속기관</option>
												<c:forEach var="codeList" items="${codeList }" varStatus="i">
														<option value="${codeList.INSTT_IDX }">${codeList.INSTT_NAME}</option>
												</c:forEach>
											</select>
									</c:when>
									<c:when test="${!empty listDt.INSTT_IDX && !empty listDt.APPLY_YN}">${listDt.INSTT_IDX }</c:when>
									<c:when test="${!empty listDt.INSTT_IDX && empty listDt.APPLY_YN}">
											<select id="insttIdx" name="insttIdx" style="width:150px;">
												<option value="">소속기관</option>
												<c:forEach var="codeList" items="${codeList }" varStatus="i">
													<c:if test="${codeList.INSTT_NAME == listDt.INSTT_IDX }">
														<option value="${codeList.INSTT_IDX }" selected="selected">${codeList.INSTT_NAME}</option>
													</c:if>
													<c:if test="${codeList.INSTT_NAME != listDt.INSTT_IDX }">
														<option value="${codeList.INSTT_IDX }">${codeList.INSTT_NAME}</option>
													</c:if>
												</c:forEach>
											</select>
									</c:when>
									</c:choose>	
								</td>
								<td>
									<c:choose>
									<c:when test="${empty listDt.CLSF_CD && listDt.STATUS == '2' && empty listDt.APPLY_YN}">
										<select id="gradeIdx" name="gradeIdx" style="width:100px;">
											<option value="">직급</option>
											<c:forEach var="graList" items="${gradeList }" varStatus="i">
												<option value="${graList.OPTION_CODE }">${graList.OPTION_NAME}</option>
											</c:forEach>
										</select>
									</c:when>
									<c:when test="${!empty listDt.CLSF_CD && !empty listDt.APPLY_YN}">${listDt.CLSF_CD }</c:when>
									<c:when test="${!empty listDt.CLSF_CD && listDt.STATUS == '2'}">
											<select id="gradeIdx" name="gradeIdx" style="width:100px;">
												<option value="">직급</option>
												<c:forEach var="graList" items="${gradeList }" varStatus="i">
													<c:if test="${graList.OPTION_NAME == listDt.CLSF_CD }">
														<option value="${graList.OPTION_CODE }" selected="selected">${graList.OPTION_NAME}</option>
													</c:if>
													<c:if test="${graList.OPTION_NAME != listDt.CLSF_CD }">
														<option value="${graList.OPTION_CODE }">${graList.OPTION_NAME}</option>
													</c:if>
												</c:forEach>
											</select>
									</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
									<c:when test="${listDt.STATUS == '1' || listDt.STATUS == '3'}">승인</c:when>
									<c:when test="${listDt.STATUS == '0'}">반려</c:when>
									<c:when test="${empty listDt.APPLY_YN && listDt.STATUS == '2'}">
										<select id="status" name="status" style="width:80px;">
											<option value="">선택</option>
											<option value="3">승인</option>
											<option value="0">반려</option>
										</select>
									</c:when>
									</c:choose>
								</td>
								<td>${listDt.CONFMER_NAME}</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.CONFM_DT}"/></td>
								</tr>
							<c:set var="listNo" value="${listNo - 1}"/>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div> --%>
			
			<div class="btns-area">
	           <div class="btns-right">
	               <a href="javascript:history.back();" class="btn-m01 btn-color01">목록</a>
	           </div>
	       </div>
		</div>
		
		<div class="mask"></div>
	    <div class="modal-wrapper" id="modal-action01">
	        <h2>
	            알림
	        </h2>
	        <div class="modal-area">
	        <form id="${inputSubFormId}" name="${inputSubFormId}" method="post" action="<c:out value="${URL_SECSUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
	        <input type="hidden" name="docIdx" id="docIdx" value=""/>
	        <input type="hidden" name="memIdx" id="memIdx" value="${dt.MEMBER_IDX}"/>
	        <input type="hidden" name="gradeVal" id="gradeVal" value=""/>
	        <input type="hidden" name="instt" id="instt" value=""/>
	        <input type="hidden" name="preInstt" id="preInstt" value=""/>
	        <input type="hidden" name="stat" id="stat" value=""/>
	            <div class="modal-alert">
	                <p>
	                    공단소속이 “울산지사”에서<br /> “서울지역본부＂로 변경됩니다.<br /> 변경하시겠습니까?
	                </p>
	            </div>
	            <div class="btns-area">
	                <button type="submit" class="btn-m02 round01 btn-color03" form="${inputSubFormId}">
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