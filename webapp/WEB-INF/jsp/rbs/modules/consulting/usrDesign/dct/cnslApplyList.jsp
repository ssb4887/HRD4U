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

<div class="contents-area2">
	<form action="<c:out value="${formAction}"/>" method="get" id="fn_techSupportSearchForm" name="fn_techSupportSearchForm">
	<input type="hidden" name="mId" value="<c:out value="${mId}" />" />
	<fieldset>
		<legend class="blind"> 기업훈련 성과리포트 검색양식 </legend>
			<div class="basic-search-wrapper">
				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label for="isInsttIdx"> 소속기관 </label>
							</dt>
							<dd>
								<select id="id_getInsttIdx" name="getInsttIdx" title="소속기관">
									<option value="">소속기관 선택</option>
									<c:forEach var="instt" items="${insttList}">
										<option value="${instt.INSTT_IDX}"
											<c:if test="${instt.INSTT_IDX eq param.insttIdx}">selected</c:if>><c:out
												value="${instt.INSTT_NAME}" /></option>
									</c:forEach>
								</select>
							</dd>
						</dl>
					</div>
					<div class="half-box">
						<dl>
							<dt>
								<label for="isInsttIdx"> 상태 </label>
							</dt>
							<dd>
								<div class="input-radio-wrapper ratio">
									<div class="input-radio-area">
										<input type="radio" class="radio-type01" id="n_status" name="getStatus" value="" <c:out value="${empty param.getStatus ? 'checked' : ''}"/>>
										<label for="n_status">전체</label>
									</div>
									<div class="input-radio-area">
										<input type="radio" class="radio-type01" id="n_status" name="getStatus" value="0" <c:out value="${param.getStatus eq '0' ? 'checked' : ''}"/>>
										<label for="n_status">미처리</label>
									</div>
									<div class="input-radio-area">
										<input type="radio" class="radio-type01" id="y_status" name="getStatus" value="1" <c:out value="${param.getStatus eq '1' ? 'checked' : ''}"/>>
										<label for="y_status">처리완료</label>
									</div>
								</div>
							</dd>
						</dl>
					</div>
				</div>
				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label for="id_schBplNo"> 사업장관리번호 </label>
							</dt>
							<dd>
								<input type="text" id="id_schBplNo" name="schBplNo" value="<c:out value="${param.schBplNo}" />" title="사업장관리번호 입력" placeholder="사업장관리번호">
							</dd>
						</dl>
					</div>
					<div class="half-box">
						<dl>
							<dt>
								<label for="id_trCorpNm"> 기업명 </label>
							</dt>
							<dd>
								<input type="search" id="id_trCorpNm" name="trCorpNm" value="<c:out value="${param.trCorpNm}" />" title="기업명" placeholder="기업명">
							</dd>
						</dl>
					</div>
				</div>
			</div>
		
			<div class="btns-area">
		<button type="submit" class="btn-search02 btnSearch" id="fn_btn_search">
			<img src="${contextPath}/dct/images/icon/icon_search03.png" alt="" /> 
				<strong>
					검색 
				</strong>
		</button>
	</div>
	</fieldset>
	</form>
</div>

	<div class="title-wrapper">
		<p class="total fl">
			총 <strong><c:out value="${totalCount}" /></strong> 건 ( <c:out value="${paginationInfo.currentPageNo}" /> / <c:out value="${paginationInfo.lastPageNo}" /> 페이지 )
		</p>
		<div class="fr">
           	<a href="${contextPath}/dct/consulting/cnslApplyExcel.do?mId=${mId}<c:if test="${not empty param.insttIdx}">&insttIdx=${param.insttIdx}</c:if><c:if test="${not empty param.schBplNo}">&schBplNo=${param.schBplNo}</c:if><c:if test="${not empty param.trCorpNm}">&trCorpNm=${param.trCorpNm}</c:if>" class="btn-m01 btn-color01">엑셀다운로드</a>
        	<a href="#" class="btn-m01 btn-color02 m-w100" onclick="cnslUpdate(this.id);">
				처리완료
			</a>
        </div>
	</div>
	<div class="table-type01 horizontal-scroll">
          <table>
              <caption>
                  	주치의 컨설팅 신청 목록 정보표 : No, 기업명, 사업장관리번호, 기업담당자, 추천인 정보 등에 관한 정보 제공표
              </caption>
              <colgroup>
              	<col width="5%" />
				<col width="5%" />
				<col width="15%" />
				<col width="10%" />
				<col width="10%" />
				<col width="8%" />
				<col width="17%" />
				<col width="10%" />
				<col width="10%" />
				<col width="8%" />
              </colgroup>
              <thead class="no-thead">
					<tr>
						<th scope="col">
							<input type="checkbox" name="allChk" id="id_allChk" class="checkbox-type01">
						</th>
						<th scope="col" class="number">번호</th>
						<th scope="col" class="title">기업명</th>
						<th scope="col" class="writer">사업장관리번호</th>
						<th scope="col" class="date">관할지부지사</th>
						<th scope="col" class="date">담당자명</th>
						<th scope="col" class="date">연락처·이메일</th>
						<th scope="col" class="date">신청일자</th>
						<th scope="col" class="date">처리일자·처리자명</th>
						<th scope="col" class="date">상태</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${empty list}">
					<tr>
						<td colspan="10" class="bllist">
							<spring:message code="message.no.list" />
						</td>
					</tr>
				</c:if>
				<c:set var="listIdxName" value="${settingInfo.idx_name}" />
				<c:set var="listColumnName" value="${settingInfo.idx_column}" />
				<c:set var="listNo" value="${paginationInfo.totalRecordCount - paginationInfo.firstRecordIndex}" />
					<c:forEach var="listDt" items="${list}" varStatus="i">
						<c:set var="listKey" value="${listDt[listColumnName]}" />
						<tr id="column">
							<td>
								<c:if test="${listDt.STATUS eq '0'}">
									<input type="checkbox" name="cnslApplyList" id="id_cnslApplyList" value="${listDt.APPLY_IDX}" class="checkbox-type01" />
								</c:if>
							</td>
							<td class="num">
								<c:out value="${listNo}" />
							</td>
							<td class="title">
								<c:out value="${listDt.CORP_NM}" />
							</td>
							<td class="title">
								<c:out value="${listDt.BPL_NO}" />
							</td>
							<td class="writer">
								<c:out value="${listDt.INSTT_NAME}" />
							</td>	
							<td class="title">
								<c:out value="${listDt.CORP_PIC_NM}" />
							</td>
							<td class="date">
								<c:out value="${listDt.CORP_PIC_TELNO}" /><br/>
								<c:out value="${listDt.CORP_PIC_EMAIL}" />
							</td>
							<td class="date">
								<c:out value="${fn:substring(listDt.REGI_DATE,0,10)}" />
							</td>
							<td class="date">
								<c:out value="${listDt.LAST_MODI_NAME}" /><br/>
								<c:out value="${fn:substring(listDt.LAST_MODI_DATE,0,10)}" />
							</td>
							<td>
								<c:out value="${listDt.STATUS eq '0' ? '미처리' : '처리완료'}" />
							</td>
						</tr>
						<c:set var="listNo" value="${listNo - 1}" />
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="paging-navigation-wrapper">
			<form action="list.do" name="pageForm" method="get">
				<!-- 페이징 네비게이션 -->
				<p class="paging-navigation" onclick="paginationHandler('pageForm')">
					<pgui:pagination listUrl="${contextPath}/dct/consult/diaryList.do?mId=${mId}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}" />
				</p>
				<!-- //페이징 네비게이션 -->
			</form>
		</div>
	
<script>
$("#id_allChk").click(function(){
    // 클릭되었으면
    if($("#id_allChk").prop("checked")){
        // input태그의 name이 chk인 태그들을 찾아서 checked옵션을 true로 정의
        $("input[name=cnslApplyList]").prop("checked",true);
        // 클릭이 안되있으면
    }else{
        // input태그의 name이 chk인 태그들을 찾아서 checked옵션을 false로 정의
        $("input[name=cnslApplyList]").prop("checked",false);
    }
});

function cnslUpdate() {
	if($("input:checkbox[name='cnslApplyList']:checked").length<1){
		alert("하나 이상의 체크박스를 선택 하시기 바랍니다."); 
		return false;
	}
	
	var result = confirm("처리완료하시겠습니까?");
	
	if(result) {
		$("input[name=cnslApplyList]").each(function(index){
			if($(this).is(":checked")){
				val = $(this).val();
				var formData = {applyIdx : val}
				console.log(formData);
				$.ajax({
					url : `${contextPath}/dct/consulting/cnslApplyUpdate.do?mId=154`,
					type : "POST", 
					data : formData,
					success: function(data) {
						window.location.reload();
					}, error: function(err){
						alert(err);
					}
				});
			}
		});
	} else {
		return;
	}
}
</script>
	


<!-- //CMS 끝 -->
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>