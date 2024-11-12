<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:set var="rwtAuth" value="${elfn:isAuth('RWT')}"/>
<c:set var="conMmgUTP"><spring:message code="Globals.code.USERTYPE_ADMIN"/></c:set>
<c:set var="conNmUTP"><spring:message code="Globals.code.USERTYPE_SMEMBER"/></c:set>
<c:set var="wrtUTPAuth" value="${elfn:getModuleUTP('WRT')}"/>
<c:set var="rwtUTPAuth" value="${elfn:getModuleUTP('RWT')}"/>
<c:set var="idxName" value="${settingInfo.idx_name}"/>
<c:set var="idxColumnName" value="${settingInfo.idx_column}"/>
<c:if test="${empty chkListDt}"><c:set var="chkListDt" value="${dt}"/></c:if>
<c:set var="listDtBrdIdx" value="${chkListDt[idxColumnName]}"/>
<c:set var="listDtPntIdx" value="${chkListDt['PNT_IDX']}"/>
<c:set var="listDtQnaCnt" value="${chkListDt['QNA_CNT']}"/>
<div class="listBtn">
	<c:set var="idxParam" value="&${idxName}=${listDtBrdIdx}"/>
	<c:set var="dsetInputBtn" value="${settingInfo.dset_input_btn}"/>
	<%-- 본인이 작성한 글 또는 최고관리자일시 --%>
	<c:if test="${(listDt.REGI_IDX eq loginVO.memberIdx) or loginVO.usertypeIdx gt '50'}">
	<a href="<c:out value="${URL_MODIFY}${idxParam}"/>" title="수정" class="btn-m01 btn-color02 depth4"<c:if test="${isNoMemberAuthPage}"> data-nm="<c:out value="${listDtBrdIdx}"/>"</c:if>>수정</a>
	<a href="<c:out value="${URL_DELETEPROC}${idxParam}"/>" title="삭제" class="btn-m01 btn-color02 depth4"<c:if test="${isNoMemberAuthPage}"> data-nm="<c:out value="${listDtBrdIdx}"/>"</c:if>>삭제</a>
	</c:if>
	<c:set var="useQna" value="${settingInfo.use_qna eq '1'}"/>
	<c:if test="${((settingInfo.use_reply eq '1' and !useQna) or (useQna and listDtBrdIdx eq listDtPntIdx and listDtQnaCnt < 2)) and rwtAuth}">
		<a href="<c:out value="${URL_REPLY}${idxParam}"/>" title="답글" class="btn-m01 btn-color03 depth4">답글</a>
	</c:if>
	
	<a href="<c:out value="${URL_LIST}"/>" title="목록" class="btn-m01 btn-color01 depth4">목록</a>
</div>