
<c:choose>
	<c:when test="${cnsl.cnslType eq '1' or cnsl.cnslType eq '2' or cnsl.cnslType eq '3'}">
		<%@ include file="../corp/cnslTypeA/cnslChangeViewFormTypeA.jsp"%>
	</c:when>	
	<c:when test="${cnsl.cnslType eq '4' or cnsl.cnslType eq '5' or cnsl.cnslType eq '6'}">
		<%@ include file="../corp/cnslTypeB/cnslChangeViewFormTypeB.jsp"%>
	</c:when>
</c:choose>

	<div class="btns-area">
	
		<div class="btns-right">
			<button type="button" class="btn-m01 btn-color03">승인대기중</button>
			<a href="/web/consulting/cnslListAll.do?mId=95" class="btn-m01 btn-color01 depth3"> 목록 </a>
		</div>
</div>

