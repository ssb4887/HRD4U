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
<style>
	#overlay {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0,0,0,0.5);
		display: none;
		z-index: 9999;
	}
	
	.loader {
		border:4px solid #f3f3f3;
		border-top: 4px solid #3498db;
		border-radius: 50%;
		width: 50px;
		height: 50px;
		animation: spin 2s linear infinite;
		position: fixed;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
		z-index: 10000;
	}
	span.update-max { margin-left: 32px; border: solid 1px; padding: 4px; border-radius: 8px; color: white; background-color: gray; cursor: pointer; }
	
	@keyframes spin {
		0% { transform: translate(-50%, -50%) rotate(0deg); }
		100% { transform: translate(-50%, -50%) rotate(360deg); }
	}
</style>

<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/ntwrk/crdns.js"/>"></script>

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
					<legend class="blind">지역네트워크 유관기관 관리 검색양식</legend>
<%-- 					<itui:searchFormItemIn itemListSearch="${itemListSearch}" searchOptnHashMap="${searchOptnHashMap}" isUseRadio="${isUseRadio}" isUseMoreItem="${isUseMoreItem}"/> --%>
					<div class="basic-search-wrapper">

						<div class="one-box">
							<div class="half-box">
								<dl>
									<dt>
										<label for="is_cmptinstName">기관명</label>
									</dt>
									<dd>
										<input type="text" id="is_cmptinstName" name="is_cmptinstName" value="" title="기관명 입력" placeholder="기관명" />
									</dd>
								</dl>
							</div>
							<div class="half-box">
								<dl>
									<dt>
										<label for="is_cmptinstPicName">담당자명</label>
									</dt>
									<dd>
										<input type="text" id="is_cmptinstPicName" name="is_cmptinstPicName" value="" title="담당자명 입력" placeholder="담당자명" />
									</dd>
								</dl>
							</div>
						</div>
						
						<c:if test="${usertype >= 40}">
							<div class="one-box">
								<div class="half-box">
									<dl>
										<dt>
											<label for="is_insttIdx">소속기관</label>
										</dt>
										<dd>
											<select id="is_insttIdx" name="is_insttIdx">
												<option value="">전체</option>
												<c:forEach items="${searchOptnHashMap.ORG_CODE}" var="org">
													<option value="<c:out value='${org.OPTION_CODE}' />"><c:out value='${org.OPTION_NAME}' /></option>
												</c:forEach>
											</select>
										</dd>
									</dl>
								</div>
							</div>
						</c:if>
					</div>
					
					<div class="btns-area">
						<button type="submit" class="btn-search02 btnSearch" id="fn_btn_search" >
							<img src="${contextPath}${imgPath}/icon/icon_search03.png" alt="" />
								<strong>검색</strong>
						</button>
						<button type="button" class="btn-search02" id="btn-init" style="margin-left: 15px;">
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
					<a href="javascript:void(0);" class="btn-m01 btn-color01 depth3" id="btn-excelUpload">엑셀업로드</a>
					<a href="javascript:void(0);" class="btn-m01 btn-color01 depth3" id="btn-excelDownload">엑셀다운로드</a>
					<a href="javascript:void(0);" class="btn-m01 btn-color01 depth2 btn-view">등록</a>
					<a href="javascript:void(0);" class="btn-m01 btn-color02 depth2" id="btn-delete">선택삭제</a>
				</div>
			</div>
		</div>

		<div class="table-type01 horizontal-scroll">
			<table summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
				<caption><c:out value="${settingInfo.list_title}"/> 목록</caption>
				<colgroup>
					<col style="width: 5%" />
					<col style="width: 5%" />
					<col style="width: 20%" />
					<col style="width: 15%" />
					<col style="width: 15%" />
					<col style="width: 35%" />
					<col style="width: 15%" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col"><input type="checkbox" class="checkbox-type01" id="checkbox-all" name="checkbox-cmptinst" /></th>
						<th scope="col">번호</th>
						<th scope="col"><itui:objectItemName itemId="cmptinstName" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="cmptinstPicName" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="cmptinstPicTelno" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="addr" itemInfo="${itemInfo}"/></th>
						<th scope="col"><itui:objectItemName itemId="insttName" itemInfo="${itemInfo}"/></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty list}">
						<tr>
							<td colspan="7" class="bllist"><spring:message code="message.no.list"/></td>
						</tr>
					</c:if>
					
					<c:set var="listIdxName" value="${settingInfo.idx_name}"/>
					<c:set var="listColumnName" value="${settingInfo.idx_column}"/>
					
					<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
					
					<c:forEach var="listDt" items="${list}" varStatus="i">
						<c:set var="listKey" value="${listDt[listColumnName]}"/>
						
						<tr>
							<%-- <td class="num">${listDt}</td> --%>
							<td><input type="checkbox" name="checkbox-cmptinst" class="checkbox-type01" value="${listDt.CMPTINST_IDX}"/></td>
							<td class="num">${listNo}</td>
							<td class="subject">
								<a href="javascript:void(0);" class="btn-view" data-idx="<c:out value='${listDt.CMPTINST_IDX}'/>">
									<strong class="point-color01">
										<itui:objectView itemId="cmptinstName" itemInfo="${itemInfo}" objDt="${listDt}"/>
									</strong>
								</a>
								<a href="javascript:void(0);" data-idx="<c:out value='${listDt.CMPTINST_IDX}'/>" class="btn-linked btn-view">
									<img src="${contextPath}${imgPath}/icon/icon_search04.png" alt="" />
								</a>
							</td>
							<td><itui:objectView itemId="cmptinstPicName" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
							<td><itui:objectView itemId="cmptinstPicTelno" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
							<td>
								<c:if test="${not empty listDt.ZIP}"><c:out value="(${listDt.ZIP})" /></c:if>
								<c:out value="${listDt.ADDR} ${listDt.ADDR_DTL}" />
							</td>
							<td><itui:objectView itemId="insttName" itemInfo="${itemInfo}" objDt="${listDt}"/></td>
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

<!-- 엑셀 업로드 모달창 -->
	<div class="mask"></div>
    <div class="modal-wrapper" id="modal-excelupload">
    	<h2>유관기관 엑셀 업로드</h2>
        <div class="modal-area">
        	<form action="" id="excelUploadForm">
				<div class="contents-box pl0">
					<div class="basic-search-wrapper">
						<div class="one-box">
							<dl>
								<dt>
									<label for="sample">샘플 파일</label>
								</dt>
								<dd class="block">
									<p class="word-linked01" id="sample">
										<a href="${contextPath}/dct/crdns/downloadExcel.do?mId=70&sample=1">유관기관 업로드 샘플 파일.xlsx</a>
									</p>
								</dd>
							</dl>
						</div>
	
						<div class="one-box">
							<dl>
								<dt>
									<label for="excelFile">첨부 파일</label>
								</dt>
								<dd class="block">
									<div class="fileBox">
										<input type="text" id="fileName" class="fileName" readonly="readonly" placeholder="파일찾기">
										<label for="input-file" class="btn_file">찾아보기</label>
										<input type="file" id="input-file" class="input-file" required onchange="javascript:document.getElementById('fileName').value = this.value">
									</div>
								</dd>
							</dl>
						</div>
	
						<div class="one-box">
							<dl>
								<dt>
									<label> 비고 </label>
								</dt>
								<dd class="block">
									<p class="word">
										<strong>엑셀 업로드를 이용한 데이터입력은 다운받은 엑셀 서식을 이용하여 업로드해주시기 바랍니다.</strong>
									</p>
								</dd>
							</dl>
						</div>
					</div>
				</div>
	
				<div class="btns-area">
					<button type="button" class="btn-b02 round01 btn-color03 left" id="btn-upload">
						<span>업로드</span>
						<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="업로드 버튼 아이콘" class="arrow01" />
					</button>
				</div>
			</form>
		</div>

        <button type="button" class="btn-modal-close">모달 창 닫기</button>
  </div>
  
<form action="" method="post" style="display: none;" id="form-box">
	<input type="hidden" name="idx" id="idx" value="" />
</form>
	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>