
<c:choose>
	<c:when test="${cnsl.cnslType eq '1' or cnsl.cnslType eq '2' or cnsl.cnslType eq '3'}">
		<%@ include file="../corp/cnslTypeA/cnslChangeAdminViewTypeA.jsp"%>
	</c:when>	
	<c:when test="${cnsl.cnslType eq '4' or cnsl.cnslType eq '5' or cnsl.cnslType eq '6'}">
		<%@ include file="../corp/cnslTypeB/cnslChangeAdminViewTypeB.jsp"%>
	</c:when>
</c:choose>

	<div class="btns-area">
	
		<div class="btns-right">
			<button type="button" class="btn-m01 btn-color03 depth3" onclick="reqChangeCnslApprove(`${changeCnsl.cnslIdx}`)">승인</button>
			<button type="button" class="btn-m01 btn-color02 depth3" onclick="openModal('myModal')">반려</button>
			<a href="/web/consulting/cnslListAll.do?mId=95" class="btn-m01 btn-color01 depth3"> 목록 </a>
		</div>
		
</div>

