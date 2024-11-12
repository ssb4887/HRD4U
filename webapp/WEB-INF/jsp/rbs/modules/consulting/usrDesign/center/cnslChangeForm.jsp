
<c:choose>
	<c:when test="${changeCnsl.confmStatus eq '53'}">
		<c:choose>
			<c:when test="${cnsl.cnslType eq '1' or cnsl.cnslType eq '2' or cnsl.cnslType eq '3'}">
				<%@ include file="../corp/cnslTypeA/cnslChangeModifyFormTypeA.jsp"%>
			</c:when>	
			<c:when test="${cnsl.cnslType eq '4' or cnsl.cnslType eq '5' or cnsl.cnslType eq '6'}">
				<%@ include file="../corp/cnslTypeB/cnslChangeModifyFormTypeB.jsp"%>
			</c:when>
		</c:choose>
	</c:when>
	
	<c:otherwise>
		<c:choose>
			<c:when test="${cnsl.cnslType eq '1' or cnsl.cnslType eq '2' or cnsl.cnslType eq '3'}">
				<%@ include file="../corp/cnslTypeA/cnslChangeFormTypeA.jsp"%>
			</c:when>	
			<c:when test="${cnsl.cnslType eq '4' or cnsl.cnslType eq '5' or cnsl.cnslType eq '6'}">
				<%@ include file="../corp/cnslTypeB/cnslChangeFormTypeB.jsp"%>
			</c:when>
		</c:choose>
	</c:otherwise>


</c:choose>



	<div class="btns-area">
	
		<div class="btns-right">
			<button type="button" class="btn-m01 btn-color03" onclick="changeCnsl(`70`)">변경신청</button>
			<a href="/web/consulting/cnslListAll.do?mId=95" class="btn-m01 btn-color01 depth3"> 목록 </a>
		</div>
</div>

<script type="text/javascript" src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script>
function findAddr() {
	new daum.Postcode({
		oncomplete: function (data) {
			console.log(data);
			
			var roadAddr = data.roadAddress;
			var jibunAddr = data.jibunAddress;
			var extraRoadAddr = '';
			
			if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
				extraRoadAddr += data.bname;
			}
			
			if(data.buildingName !== '' && data.apartment === 'Y') {
				extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
			}
			
			if(extraRoadAddr !== '') {
				extraRoadAddr = '(' + extraRoadAddr + ')';
			}
			
			document.getElementById('trOprtnRegionZip').value = data.zonecode;
			insttMappingHandler(data.zonecode);
			if(roadAddr !== '') {
				document.getElementById('trOprtnRegionAddrDtl').value = extraRoadAddr;
				document.getElementById('trOprtnRegionAddr').value = roadAddr;
				
				
				
			}
			
			if(jibunAddr !== '') {
				document.getElementById('trOprtnRegionAddrDtl').value = extraRoadAddr;
				document.getElementById('trOprtnRegionAddr').value = jibunAddr;
			}
		}
	}).open();
}

</script>