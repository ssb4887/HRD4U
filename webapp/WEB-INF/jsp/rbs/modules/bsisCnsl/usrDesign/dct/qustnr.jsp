<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/view.jsp"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/> 
<c:set var="itemObjs" value="${itemInfo.items}"/>
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
	
	@keyframes spin {
		0% { transform: translate(-50%, -50%) rotate(0deg); }
		100% { transform: translate(-50%, -50%) rotate(360deg); }
	}
	
	.checkbox-height {
		height: 35px;
	}
</style>

<script defer type="text/javascript" src="${contextPath }<c:out value="${jsPath}/bsisCnsl/qustnr.js"/>"></script>
<div id="overlay"></div>
<div class="loader"></div>
 <!-- contents  -->
		<div class="contents-wrapper">
			 <!-- CMS 시작 -->
			 <c:if test="${not empty answer[0] && answer[0].QUSTNR_STATUS eq 1}">
			 	<div class="title-wrapper clear">
			 		<p class="word-issue fl">(발급일자) <c:out value="${answer[0].ISSUE_DATE}" /></p>
			 		<p class="word-issue fr">(발급번호) <c:out value="${answer[0].ISSUE_NO}" /></p>
		 		</div>
			 </c:if>
		     <form id="qustnr-form">
		     	<input type="hidden" id="rsltIdx" name="rsltIdx" value="<c:out value="${rsltIdx}"/>" />
				<input type="hidden" id="qustnrIdx" name="qustnrIdx" value="<c:out value="${qustnrIdx}" />" />
				<input type="hidden" id="bscIdx" name="bscIdx" value="<c:out value="${bscIdx}" />" />
		         <div class="contents-area" id="qustnr-area">
		             <!-- <div class="contents-box pl0">
		                 <div class="survey-wrapper" id="qustnr-[something]">
		                     
		                 </div>
		             </div> -->
		
		
		             <div class="btns-area" id="btns-area-0" style="display: none">
		                 <button type="button" class="btn-b01 round01 btn-color02 left btn-back">
		                     <span>목록</span>
		                     <img src="${contextPath}/dct/images/icon/icon_arrow_right03.png" alt="" class="arrow01"/>
		                 </button>
		                 <button type="button" class="btn-b01 round01 btn-color02 left" id="btn-save">
		                     <span>저장하기</span>
		                     <img src="${contextPath}/dct/images/icon/icon_arrow_right03.png" alt="" class="arrow01"/>
		                 </button>
		
		                 <button type="button" class="btn-b01 round01 btn-color03 left" id="btn-apply">
		                     <span>신청하기</span>
		                     <img src="${contextPath}/dct/images/icon/icon_arrow_right03.png" alt="" class="arrow01"/>
		                 </button>
		             </div>
		             
		             <div class="btns-area" id="btns-area-1" style="display: none">
		                 <button type="button" class="btn-b01 round01 btn-color02 left btn-back">
		                     <span>목록</span>
		                     <img src="${contextPath}/dct/images/icon/icon_arrow_right03.png" alt="" class="arrow01"/>
		                 </button>
		             </div>
		         </div>
		     </form>
	     </div>
		<!-- //CMS 끝 -->
		
		<form action="" method="post" style="display: none;" id="form-box">
			<input type="hidden" name="rslt" id="rslt" value="" />
			<input type="hidden" name="bsc" id="bsc" value="" />
		</form>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>