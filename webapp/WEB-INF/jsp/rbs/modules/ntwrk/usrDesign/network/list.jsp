<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>

<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:set var="searchFormId" value="fn_techSupportSearchForm"/>
<c:set var="listFormId" value="fn_techSupportListForm"/>
<c:set var="inputWinFlag" value=""/><%/* 등록/수정창 새창으로 띄울 경우 사용 */%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/><%/* 수정버튼 class */%>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/ntwrk/network.js"/>"></script>
<div id="overlay"></div>
<div class="loader"></div>
	<!-- contents  -->
	<div class="contents-wrapper">

		<!-- CMS 시작 -->
		
		<!-- search -->
<%-- 		<itui:searchFormItem divClass="tbMSearch fn_techSupportSearch" formId="${searchFormId}" formName="${searchFormId}" formAction="${URL_DEFAULT_LIST}" itemListSearch="${itemInfo.list_search}" searchOptnHashMap="${searchOptnHashMap}" searchFormExceptParams="${searchFormExceptParams}" submitBtnId="fn_btn_search" submitBtnClass="btnSearch" submitBtnValue="검색" listAction="${URL_DEFAULT_LIST}" listBtnId="fn_btn_totallist" listBtnClass="btnTotalList"/> --%>
		<!-- //search -->

		<div class="contents-area02">
			<form action="<c:out value="${formAction}"/>" method="get" id="fn_techSupportSearchForm" name="fn_techSupportSearchForm">
				<elui:hiddenInput inputInfo="${queryString}" exceptNames="${searchFormExceptParams}"/>
				<fieldset>
					<legend class="blind">네트워크 이력 검색양식</legend>
<%-- 					<itui:searchFormItemIn itemListSearch="${itemListSearch}" searchOptnHashMap="${searchOptnHashMap}" isUseRadio="${isUseRadio}" isUseMoreItem="${isUseMoreItem}"/> --%>
					
					<div class="basic-search-wrapper">

						<div class="one-box">
							<div class="half-box">
								<dl>
									<dt>
										<label for="is_cmptinstName">기관명</label>
									</dt>
									<dd>
										<input type="text" id="is_cmptinstName" name="is_cmptinstName" title="기관명" placeholder="기관명 입력" value="" />
									</dd>
								</dl>
							</div>
							
							<div class="half-box">
								<dl>
									<dt>
										<label for="is_agremName">협약명</label>
									</dt>
									<dd>
										<input type="text" id="is_agremName" name="is_agremName" title="협약명" placeholder="협약명 입력" value="" />
									</dd>
								</dl>
							</div>
						</div>
						
						<div class="one-box">
							<div class="half-box">
								<dl>
									<dt>
										<label for="is_useYn">유효여부</label>
									</dt>
									<dd>
										<select id="is_useYn" name="is_useYn">
											<option value="">전체</option>
											<option value="Y">유효</option>
											<option value="N">유효하지않음</option>
										</select>
									</dd>
								</dl>
							</div>
							
							<div class="half-box">
								<dl>
									<dt>
										<label for="is_insttIdx">주관기관</label>
									</dt>
									<dd>
										<select id="is_insttIdx" name="is_insttIdx">
											<option value="">전체</option>
											<c:forEach items="${insttList}" var="instt">
												<option value="<c:out value='${instt.INSTT_IDX}' />" ><c:out value='${instt.INSTT_NAME}' /></option>
<%-- 												<option value="<c:out value='${instt.INSTT_IDX}' />" <c:if test="${instt.INSTT_IDX eq myInsttIdx}">selected</c:if>><c:out value='${instt.INSTT_NAME}' /></option> --%>
											</c:forEach>
										</select>
									</dd>
								</dl>
							</div>
						</div>
					</div>
					<div class="btns-area">
						<button type="submit" class="btn-search02 btnSearch" id="fn_btn_search" >
							<img src="${contextPath}${imgPath}/icon/icon_search03.png" alt="" />
								<strong>검색</strong>
						</button>
						<button type="button" class="btn-search02" style="margin-left: 15px;" onclick="initSearhParams()">
							<strong>초기화</strong>
						</button>
					</div>
				</fieldset>
			</form>
		</div>


		<div class="title-wrapper">
			<p class="total fl">
				총 <strong><c:out value="${paginationInfo.totalRecordCount}" /></strong> 건 (${paginationInfo.currentPageNo}/${paginationInfo.totalPageCount}페이지)
			</p>
		
			<div class="fr btns-area">
				<div class="btns-left">
					<a href="javascript:void(0);" class="btn-m01 btn-color01 depth3" onclick="downloadExcel()">엑셀다운로드</a>
					<a href="javascript:void(0);" class="btn-m01 btn-color01 depth2 btn-view">등록</a>
					<a href="javascript:void(0);" class="btn-m01 btn-color02 depth2" onclick="deleteSelectedNtwrk()">선택삭제</a>
				</div>
			</div>
		</div>
		
		
		<div class="table-type01 horizontal-scroll">
			<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
				<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
				<colgroup>
					<col style="width: 5%" />
					<col style="width: 5%" />
					<col style="width: 10%" />
					<col style="width: 20%" />
					<col style="width: 30%" />
					<col style="width: 20%" />
					<col style="width: 10%" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col"><input type="checkbox" class="checkbox-type01" id="checkbox-all" name="checkbox-network" /></th>
						<th scope="col">번호</th>
						<th scope="col"><itui:objectItemName itemId="insttName" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="cmptinstName" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="agremName" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="fromBgndtToEnddt" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="useYn" itemInfo="${itemInfo}"/></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty list}">
						<tr>
							<td colspan="7" class="bllist"><spring:message code="message.no.list"/></td>
						</tr>
					</c:if>
					
					<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
					<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
					
					<c:forEach var="listDt" items="${list}" varStatus="i">
						<tr>
							<td><input type="checkbox" class="checkbox-type01" name="checkbox-network" value="<c:out value='${listDt.NTWRK_IDX}' />" /></td>
							<td class="num">${listNo}</td>
							<td><itui:objectView itemId="insttName" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
							<td>
								<a href="javascript:void(0);" class="btn-view" data-idx="<c:out value='${listDt.NTWRK_IDX}' />">
									<strong class="point-color01">
										<itui:objectView itemId="cmptinstName" itemInfo="${itemInfo}" objDt="${listDt}"/>
									</strong>
								</a>
							</td>
							<td><itui:objectView itemId="agremName" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
							<td>
								<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.BGNDT}"/>
								~
								<fmt:formatDate pattern="yyyy-MM-dd" value="${listDt.ENDDT}"/>
							</td>
							<td><itui:objectView itemId="useYn" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
						</tr>
						<c:set var="listNo" value="${listNo - 1}"/>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="paging-navigation-wrapper">
			<!-- 페이징 네비게이션 -->
			<pgui:pagination listUrl="${URL_PAGE_LIST}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}"/>
			
			<!-- <p class="paging-navigation">
				<a href="#none" class="btn-first">맨 처음 페이지로 이동</a> <a
					href="#none" class="btn-preview">이전 페이지로 이동</a> <strong>1</strong>
				<a href="#">2</a> <a href="#">3</a> <a href="#">4</a> <a
					href="#">5</a> <a href="#none" class="btn-next">다음 페이지로
					이동</a> <a href="#none" class="btn-last">맨 마지막 페이지로 이동</a>
			</p> -->

			<!-- //페이징 네비게이션 -->
		</div>


		<!-- //CMS 끝 -->
	</div>
<!-- //contents  -->
<form action="" method="post" style="display: none;" id="form-box">
	<input type="hidden" name="idx" id="idx" value="" />
</form>

<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>