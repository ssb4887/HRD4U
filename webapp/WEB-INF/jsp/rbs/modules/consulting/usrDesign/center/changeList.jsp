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

<!-- contents  -->
<article>
	<div class="contents" id="contents">
		<div class="contents-wrapper">


			<!-- CMS 시작 -->

			<div class="contents-area02">
				<form action="<c:out value=" ${formAction}"/>
				" method="get"
					id="fn_techSupportSearchForm" name="fn_techSupportSearchForm">
					<input type="hidden" name="mId" value="96">

					<legend class="blind"> 컨설팅 신청 내역 검색양식 </legend>
					<div class="basic-search-wrapper">
						<div class="one-box">
							<div class="half-box">
								<dl>
									<dt>
										<label for="is_bplNo"> 사업장관리번호 </label>
									</dt>
									<dd>
										<input type="text" id="is_bplNo" name="is_bplNo" value=""
											title="사업장관리번호 입력" placeholder="사업장관리번호">
									</dd>
								</dl>
							</div>
							<div class="half-box">
								<dl>
									<dt>
										<label for="is_cmptncBrffcIdx"> 소속기관 </label>
									</dt>
									<dd>
										</select> <select id="is_cmptncBrffcIdx" name="is_cmptncBrffcIdx"
											class="w200">
											<option value="">소속기관 선택</option>
											<option value="33">본부</option>
											<option value="1">서울지역본부</option>
											<option value="2">서울서부지사</option>
											<option value="3">서울남부지사</option>
											<option value="4">서울강남지사</option>
											<option value="5">강원지사</option>
											<option value="6">강원동부지사</option>
											<option value="13">부산지역본부</option>
											<option value="14">부산남부지사</option>
											<option value="15">경남지사</option>
											<option value="17">경남서부지사</option>
											<option value="16">울산지사</option>
											<option value="18">대구지역본부</option>
											<option value="20">경북동부지사</option>
											<option value="21">경북서부지사</option>
											<option value="19">경북지사</option>
											<option value="7">경인지역본부</option>
											<option value="8">인천지사</option>
											<option value="9">경기북부지사</option>
											<option value="10">경기동부지사</option>
											<option value="11">경기남부지사</option>
											<option value="12">경기서부지사</option>
											<option value="22">광주지역본부</option>
											<option value="26">제주지사</option>
											<option value="23">전북지사</option>
											<option value="27">전북서부지사</option>
											<option value="24">전남지사</option>
											<option value="25">전남서부지사</option>
											<option value="28">대전지역본부</option>
											<option value="31">세종지사</option>
											<option value="29">충북지사</option>
											<option value="32">충북북부지사</option>
											<option value="30">충남지사</option>
											<option value="36">원주기업인재혁신센터</option>
										</select>
									</dd>
								</dl>
							</div>
						</div>
						<div class="one-box">
							<div class="half-box">
								<dl>
									<dt>
										<label for="is_confmStatus"> 상태 </label>
									</dt>
									<dd>
										<select id="is_confmStatus" name="is_confmStatus">
											<option value="">상태</option>
											<option value="10">신청</option>
											<option value="30">접수</option>
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
												<input type="text" id="startDate" name="startDate"
													class="sdate">
											</div>
											<div class="word-unit">~</div>
											<div class="input-calendar-area">
												<input type="text" id="endDate" name="endDate" class="edate">
											</div>
										</div>
									</dd>
								</dl>
							</div>
						</div>


						<div class="one-box">
							<dl>
								<dt>
									<label for="is_corpNm"> 기업명 </label>
								</dt>
								<dd>
									<input type="text" id="is_corpNm" name="is_corpNm" value=""
										title="기업명 입력" placeholder="기업명">
								</dd>
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
					<div class="fr">
						<a href="#none" class="btn-m01 btn-color01"> 일괄접수 </a>
					</div>
				</div>
				<div class="table-type01 horizontal-scroll">
					<table>
						<caption>컨설팅 신청 내역 접수표 : 체크, 번호, 기업명, 사업장관리번호, 소속기관,
							신청일, 상태, 접수에 관한 정보 제공표</caption>
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
						</colgroup>
						<thead>
							<tr>
								<th scope="col"><input type="checkbox" id="" name=""
									value="" class="checkbox-type01"></th>
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
								<th scope="col">접수</th>
								<th scope="col">기초컨설팅</th>
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
									<td><input type="checkbox" id="" name="" value=""
										class="checkbox-type01"></td>
									<td class="num">${listNo}</td>
									<td><a
										href="${contextPath}/web/consulting/view.do?mId=97&bplNo=${listDt.BPL_NO}&bsiscnslIdx=${listDt.BSISCNSL_IDX}&cnslType=${listDt.CNSL_TYPE}&cnslIdx=${listDt.CNSL_IDX}" />
										<strong class="point-color01"> <itui:objectView
												itemId="corpNm" itemInfo="${itemInfo}" objDt="${listDt}" /></strong></a></td>
									<td class="subject"><itui:objectView itemId="bplNo"
											itemInfo="${itemInfo}" objDt="${listDt}" /></td>
									<td><itui:objectView itemId="cmptncBrffcIdx"
											itemInfo="${itemInfo}" objDt="${listDt}" /></td>
									<td><itui:objectView itemId="cnslType"
											itemInfo="${itemInfo}" objDt="${listDt}" /></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd"
											value="${listDt.REGI_DATE}" /></td>
									<td><itui:objectView itemId="confmStatus"
											itemInfo="${itemInfo}" objDt="${listDt}" /></td>
									<td><c:choose>
											<c:when test="${listDt.CONFM_STATUS eq 10}">
												<button type="button" class="btn-m02 btn-color03"
													onClick="receiveCnsl(`${listDt.CNSL_IDX}`, 30)">
													<span> 접수 </span>
												</button>
											</c:when>
											<c:otherwise>
												<button type="button" class="btn-m02 btn-color03"
													onClick="location.href='${contextPath}/web/consulting/view.do?mId=96&cnslIdx=${listDt.CNSL_IDX}'">상세보기</button>
											</c:otherwise>
										</c:choose></td>
									<td>
										<button type="button" class="btn-m02 btn-color03"
											onClick="window.open('${contextPath}/web/bsisCnsl/consultingClipReport.do?mId=56&bsiscnslIdx=${listDt.BSISCNSL_IDX}', '_blank')">
											<span> 열기 </span>
										</button>
									</td>
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
		</div>
	</div>
</article>



<script type="text/javascript" src="${contextPath}${jsPath}/cnsl.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>