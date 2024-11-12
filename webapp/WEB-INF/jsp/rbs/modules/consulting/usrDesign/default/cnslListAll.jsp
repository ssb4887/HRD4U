<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}" />
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}" />
<c:set var="searchFormId" value="fn_techSupportSearchForm" />
<c:set var="listFormId" value="fn_techSupportListForm" />
<c:set var="inputWinFlag" value="" />
<%
	/* 등록/수정창 새창으로 띄울 경우 사용 */
%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}" />
<%
	/* 수정버튼 class */
%>

<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp" />
		<jsp:param name="searchFormId" value="${searchFormId}" />
		<jsp:param name="listFormId" value="${listFormId}" />
	</jsp:include>
</c:if>


			<!-- CMS 시작 -->

			<div class="contents-area02">
				<form action="<c:out value=" ${formAction}"/>
				" method="get"
					id="fn_techSupportSearchForm" name="fn_techSupportSearchForm">
					<input type="hidden" name="mId" value="97">
					<legend class="blind"> 컨설팅 접수 관리 검색양식 </legend>
					<div class="basic-search-wrapper">
						<div class="one-box">
							<div class="half-box">
								<dl>
									<dt>
										<label for="textfield01"> 사업장관리번호 </label>
									</dt>
									<dd>
										<input type="text" id="textfield01" name="" value=""
											title="사업장관리번호 입력" placeholder="사업장관리번호">
									</dd>
								</dl>
							</div>
							<div class="half-box">
								<dl>
									<dt>
										<label for="textfield02"> 소속기관 </label>
									</dt>
									<dd>
										</select> <select id="textfield0202" name="" class="w200">
											<option value="">울산지사</option>
										</select>
									</dd>
								</dl>
							</div>
						</div>
						<div class="one-box">
							<div class="half-box">
								<dl>
									<dt>
										<label for="textfield03"> 상태 </label>
									</dt>
									<dd>
										<select id="textfield03" name="">
											<option value="">검토</option>
										</select>
									</dd>
								</dl>
							</div>
							<div class="half-box">
								<dl>
									<dt>
										<label for="calendar0101"> 기간 </label>
									</dt>
									<dd>
										<div class="input-calendar-wrapper">
											<div class="input-calendar-area">
												<input type="text" id="calendar0101" name="" class="sdate">
											</div>
											<div class="word-unit">~</div>
											<div class="input-calendar-area">
												<input type="text" id="calendar0102" name="" class="edate">
											</div>
										</div>
									</dd>
								</dl>
							</div>
						</div>


						<div class="one-box">
							<dl>
								<dt>
									<label for="textfield04"> 검색 </label>
								</dt>
								<dd>
									<div class="input-search-wrapper">
										<select id="textfield04" name="">
											<option value="">기업명</option>
										</select> <input type="search" id="" name="" value="">
									</div>
								</dd>
							</dl>
						</div>
					</div>

					<div class="btns-area">
						<button type="submit" class="btn-search02">
							<img src="../img/icon/icon_search03.png" alt="" /> <strong>
								검색 </strong>
						</button>
					</div>
				</form>
			</div>


			<div class="contents-area">
				<div class="title-wrapper">
					<p class="total fl">
						총 <strong>${paginationInfo.totalRecordCount}</strong> 건 (
						${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지
						)
					</p>
				</div>
				<div class="table-type01 horizontal-scroll">
					<table>

						<colgroup>
							<col style="width: 6%">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: auto">
							<col style="width: 10%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">번호</th>
								<th scope="col"><itui:objectItemName itemId="corpNm"
										itemInfo="${itemInfo}" /></th>
								<th scope="col"><itui:objectItemName itemId="bplNo"
										itemInfo="${itemInfo}" /></th>
								<th scope="col"><itui:objectItemName itemId="cmptncBrffcIdx"
										itemInfo="${itemInfo}" /></th>
								<th scope="col"><itui:objectItemName itemId="cnslType"
										itemInfo="${itemInfo}" /></th>
								<th scope="col"><spring:message code="item.regidate.name" /></th>
								<th scope="col"><itui:objectItemName itemId="confmStatus"
										itemInfo="${itemInfo}" /></th>
								<th scope="col">신청상태</th>
								<th scope="col">변경신청상태</th>
								<th scope="col">컨설팅 수행일지</th>
								<th scope="col">컨설팅 보고서 검토</th>
								<th scope="col">컨설팅 보고서 결재</th>
							</tr>
						</thead>
						<tbody class="alignC">
							<c:if test="${empty list}">
								<tr>
									<td colspan="9" class="bllist"><spring:message
											code="message.no.list" /></td>
								</tr>
							</c:if>
							<c:set var="listIdxName" value="${settingInfo.idx_name}" />
							<c:set var="listColumnName" value="${settingInfo.idx_column}" />
							<c:set var="listNo"
								value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
							<c:forEach var="listDt" items="${list}" varStatus="i">
								<c:set var="listKey" value="${listDt[listColumnName]}" />
								<tr>
									<td class="num">${listNo}</td>
									<td><itui:objectView itemId="corpNm" itemInfo="${itemInfo}" objDt="${listDt}" /></td>
									<td class="subject"><itui:objectView itemId="bplNo" itemInfo="${itemInfo}" objDt="${listDt}" /></td>
									<td><itui:objectView itemId="cmptncBrffcIdx" itemInfo="${itemInfo}" objDt="${listDt}" /></td>
									<td><itui:objectView itemId="cnslType" itemInfo="${itemInfo}" objDt="${listDt}" /></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.REGI_DATE}" /></td>
									<td><itui:objectView itemId="confmStatus" itemInfo="${itemInfo}" objDt="${listDt}" /></td>
									<td>변경신청 상태</td>
									<td>컨설팅팀 관리</td>
									<td>컨설팅 수행일지</td>
									<td>컨설팅보고서 검토</td>
									<td>컨설팅보고서 결재</td>
								</tr>
								<c:set var="listNo" value="${listNo - 1}" />
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div class="paging-navigation-wrapper">
					<!-- 페이징 네비게이션 -->
					<p class="paging-navigation">
						<a href="#none" class="btn-first">맨 처음 페이지로 이동</a> <a href="#none"
							class="btn-preview">이전 페이지로 이동</a> <strong>1</strong> <a href="#">2</a>
						<a href="#">3</a> <a href="#">4</a> <a href="#">5</a> <a
							href="#none" class="btn-next">다음 페이지로 이동</a> <a href="#none"
							class="btn-last">맨 마지막 페이지로 이동</a>
					</p>
					<!-- //페이징 네비게이션 -->
				</div>
			</div>

			<!-- //CMS 끝 -->

<script type="text/javascript" src="${contextPath}${jsPath}/cnsl.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>