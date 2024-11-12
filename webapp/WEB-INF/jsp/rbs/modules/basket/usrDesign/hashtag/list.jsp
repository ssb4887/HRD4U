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
<script type="text/javascript" src="${contextPath}${jsPath}/basket.js"></script>
<link rel="stylesheet" href="${contextPath}${cssPath}/modal.css" />

<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp" />
		<jsp:param name="searchFormId" value="${searchFormId}" />
		<jsp:param name="listFormId" value="${listFormId}" />
	</jsp:include>
</c:if>

<div class="contents-area">
	<div class="title-wrapper">
		<div class="btns-area">
             <div class="btns-right">
				<c:if test="${wrtAuth}">
					<button class="btn-m01 btn-color01 fr" id="open-modal07" onclick="hashTagModalOpen(this.id)">등록</button>
				</c:if>
			</div>
		</div>
	</div>
	<div class="table-type02 horizontal-scroll">
	<form id="${listFormId}" action="${contextPath}/dct/basket/resList.do?mId=${mId}" method="post" name="${listFormId}" method="post" target="list_target">
		<input type="hidden" id="id_mId" name="mId" value="${mId}" />
		<table class="listTypeA" summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
			<caption>
				<c:out value="${settingInfo.list_title}" />
				목록
			</caption>
			<colgroup>
				<col style="width: 5%;">
				<col style="width: 10%;">
				<col style="width: 50%;">
				<col style="width: 15%;">
				<col style="width: 20%;">
			</colgroup>
			<thead>
				<tr id="column">
					<th scope="col">
						<input type="checkbox" name="allchkPrint" id="allchkPrint" class="checkbox-type01">
					</th>
					<th scope="col">
						번호
					</th>
					<th scope="col">
						<itui:objectItemName itemId="hashtagNm" itemInfo="${itemInfo}" />
					</th>
					<th scope="col">
						소속기관
					</th>
					<th scope="col">
						<itui:objectItemName itemId="regiDate" itemInfo="${itemInfo}" />
					</th>
				</tr>
			</thead>
			<tbody class="alignC">
				<c:if test="${empty list}">
					<tr>
						<td colspan="5" class="bllist">
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
							<input type="checkbox" name="hashtagCd" id="id_hashtagCd" value="${listDt.HASHTAG_CD}" class="checkbox-type01" />
						</td>
						<td class="num">
							<c:out value="${i.count}" />
						</td>
						<td class="hashtagNm">
							<a href="#" class="open-modal01" onclick="hashTagModalOpen(${listDt.HASHTAG_CD})">
								<strong class="point-color01">
									<itui:objectView itemId="hashtagNm" itemInfo="${itemInfo}" objDt="${listDt}" />
								</strong>
							</a>
						</td>
						<td class="insttName">
							<itui:objectView itemId="insttName" itemInfo="${itemInfo}" objDt="${listDt}" />
						</td>
						<td class="result">
							<itui:objectView itemId="regiDate" itemInfo="${itemInfo}" objDt="${listDt}" />
						</td>
					</tr>
					<c:set var="listNo" value="${listNo - 1}" />
				</c:forEach>
			</tbody>
		</table>

		<!-- 페이지 내비게이션 -->
		<div class="paging-navigation-wrapper">
			<p class="paging-navigation">
				<pgui:pagination listUrl="${contextPath}/dct/basket/resList.do?mId=${mId}" pgInfo="${paginationInfo}" imgPath="${imgPath}" pageName="${elfn:getString(settingInfo.page_name, 'page')}" />
			</p>
		</div>
		<!-- //페이지 내비게이션 -->
		
		<div class="btns-area mt20">
			<div class="btns-right">
				<a href="#" class="btn-m01 btn-color02 m-w100" onclick="delHash(this.id);">
					삭제
				</a>
			</div>
		</div>		
	</form>
</div>

<!-- 모달 창 -->
<div class="mask"></div>
<!-- 분류 예외 지정 모달 -->
<div class="modal-wrapper" id="modal-action05">
	<h2>
    	해시태그 등록
    </h2>
	<div class="modal-area">
        <form action="" id="addHashForm">
        	<button type="button" class="btn-modal-close" onclick="closeModal('closeBtn_05')">모달창 닫기</button>
            <div class="contents-box pl0">
            	<div class="basic-search-wrapper">
            		<div class="one-box">
            			<dl>
            				<dt>
								<label>
									등록자
								</label>
            				</dt>
            				<dd>
            					<p class="word">
            						<c:out value="${loginVO.memberName}" />      
            					</p>
            				</dd>
            			</dl>
            		</div>
            		<div class="one-box">
            			<dl>
            				<dt>
								<label>
									등록일
								</label>
            				</dt>
            				<dd>
            					<p class="word" id="id_regiDate">
            						
            					</p>
            				</dd>
            			</dl>
            		</div>
            		<div class="one-box">
            			<dl>
            				<dt>
								<label>
									해시태그명
								</label>
            				</dt>
            				<dd>
            					<input type="text" id=id_hashtagNm name="hashtagNm" value="" maxlength="50" placeholder="해시태그명 입력" />
            				</dd>
            			</dl>
            		</div>
            		<div class="one-box">
            			<dl>
            				<dt>
								<label>
									비고 (Comment)
								</label>
            				</dt>
            				<dd>
            					<input type="text" id=id_remarks name="hashtagRemarks" value="" maxlength="100" placeholder="비고 (Comment) 입력" />
            				</dd>
            			</dl>
            		</div>
            	</div>
            </div>
            <div class="btns-area">
				<button type="button" id="id_regiHash" onclick="regiHash()" class="btn-m02 round01 btn-color03">
					<span>
						등록
					</span>
				</button>
				<button type="button" id="closeBtn_05" onclick="closeModal(this.id)" class="btn-m02 round01 btn-color02">
					<span>
						취소
					</span>
				</button>
			</div>
			</form>
		</div>
	</div>
</div>

<!-- 모달 창 -->
<div class="mask"></div>
<!-- 분류 예외 지정 모달 -->
<div class="modal-wrapper" id="modal-action06">
	<h2>
    	해시태그 수정
    </h2>
	<div class="modal-area">
        <form action="" id="editHashForm">
        	<button type="button" class="btn-modal-close" onclick="closeModal('closeBtn_06')">모달창 닫기</button>
            <div class="contents-box pl0">
            	<div class="basic-search-wrapper">
            		<div class="one-box">
            			<dl>
            				<dt>
								<label>
									등록자
								</label>
            				</dt>
            				<dd>
            					<p class="word">
            						<c:out value="${loginVO.memberName}" />      
            					</p>
            				</dd>
            			</dl>
            		</div>
            		<div class="one-box">
            			<dl>
            				<dt>
								<label>
									등록일
								</label>
            				</dt>
            				<dd>
            					<p class="word" id="id_editDate">
            						
            					</p>
            				</dd>
            			</dl>
            		</div>
            		<div class="one-box">
            			<dl>
            				<dt>
								<label>
									해시태그명
								</label>
            				</dt>
            				<dd>
            					<input type="text" id="id_editHashNm" name="hashtagNm" value="" maxlength="50" placeholder="해시태그명 입력" />
            				</dd>
            			</dl>
            		</div>
            		<div class="one-box">
            			<dl>
            				<dt>
								<label>
									비고 (Comment)
								</label>
            				</dt>
            				<dd>
            					<input type="text" id="id_editRemarks" name="hashtagRemarks" value="" maxlength="100" placeholder="비고 (Comment) 입력" />
            				</dd>
            			</dl>
            		</div>
            	</div>
            </div>
            <div class="btns-area">
				<button type="button" id="id_editHash" onclick="editHash()" class="btn-m02 round01 btn-color03">
					<span>
						수정
					</span>
				</button>
				<button type="button" id="closeBtn_06" onclick="closeModal(this.id)" class="btn-m02 round01 btn-color02">
					<span>
						취소
					</span>
				</button>
			</div>
			</form>
		</div>
	</div>


<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>