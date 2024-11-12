<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_authInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/inputWebCenter.jsp"/>
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
			<div class="btns-area">
	            <button type="button" class="btn-m01 btn-color01" id="movePage" >수정</button>
	        </div>
	        
	        <%-- <!-- 적용권한 테이블 -->
			<div class="contents-area">
					<div class="contents-box pl0">
					<h3 class="title-type01 ml0">적용권한</h3><div class="table-type02 horizontal-scroll">
						<table summary="${summary}">
							<caption>
								적용권한 정보표
							</caption>
							<colgroup>
								 <col style="width: 15%" />
		                         <col style="width: 85%" />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">적용권한</th>
									<td class="left">
										<c:forEach var="listDt" items="${dtAuth}" varStatus="i">
											<p>
											<c:if test="${listDt.APPLY_YN == 'Y'}"><div class="btn-m03 btn-color03" style="margin:0 5px 5px 0; display:inline-block">적용</div>${listDt.AUTH_NAME } 승인자 : ${listDt.LAST_MODI_NAME}(${listDt.LAST_MODI_ID})
								<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.LAST_MODI_DATE}"/></c:if>
		                                    </p>
										</c:forEach>
									</td>
								</tr> 
							</tbody>
						</table>	
					</div>
					 
				</div> --%>
				<%-- <div class="contents-box pl0">
					<h3 class="title-type01 ml0">적용권한</h3><div class="table-type02 horizontal-scroll">
						<table summary="${summary}">
							<caption>
								적용권한 정보표
							</caption>
							<colgroup>
								 <col style="width: 15%" />
		                         <col style="width: 85%" />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">적용권한</th>
									<td class="left">
										<c:forEach var="listDt" items="${dtAuth}" varStatus="i">
											<p>
											<c:if test="${listDt.APPLY_YN == 'Y'}"><div class="btn-m03 btn-color03" style="margin:0 5px 5px 0; display:inline-block">적용</div>${listDt.START_DATE } ~ ${listDt.END_DATE } :  ${listDt.AUTH_NAME }</c:if>
											<c:if test="${listDt.APPLY_YN == 'N' && (!empty listDt.START_DATE && !empty listDt.END_DATE && !empty listDt.AUT_NAME)}"><div class="btn-m03 btn-color04" style="margin:0 5px 5px 0; display:inline-block">미적용</div>${listDt.START_DATE } ~ ${listDt.END_DATE } :  ${listDt.AUTH_NAME }</c:if>
		                                    </p>
										</c:forEach>
									</td>
								</tr> 
							</tbody>
						</table>	
					</div>
					 
				</div> --%>
				<%-- <form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
					<input type="hidden" name="memberIdx" id="memberIdx" value="<c:out value="${dt.MEMBER_IDX}"/>"/>
				</form>
				<div class="btns-area">
			        <button type="submit" class="btn-m01 btn-color01" form="${inputFormId}">
			            	권한 신청
			        </button>
			    </div> --%>
			</div> 
		</div>
	</div>
		
	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>