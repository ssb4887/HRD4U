<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="inputFormId" value="fn_sampleInputForm" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}" />
		<jsp:param name="javascript_page" value="${moduleJspRPath}/input.jsp" />
		<jsp:param name="inputFormId" value="${inputFormId}" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />

<jsp:scriptlet>pageContext.setAttribute("newLine", "\n");</jsp:scriptlet>
<script defer type="text/javascript" src="${contextPath }<c:out value="${jsPath}/analysis/apply.js"/>"></script>
<div id="overlay"></div>
<div class="loader"></div>
<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<div class="contents-area">
		<h3 class="title-type01 ml0">훈련성과분석</h3>
		<form id="frm" method="post">
			<input type="hidden" id="id_status" name="status" value="" />
			<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
				<table class="width-type02">
					<colgroup>
						<col width="15%">
                        <col width="16%">
                        <col width="15%">
                        <col width="16%">
                        <col width="auto">
                        <col width="auto">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" colspan="2">
								기업명
							</th>
							<td colspan="2">
								<itui:objectView itemId="trCorpNm" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
							<th scope="row">
								훈련기관(기업)
							</th>
							<td>
								<itui:objectView itemId="bizSe" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
						</tr>
						<tr>
							<th scope="row" colspan="2">
								훈련과정명
							</th>
							<td colspan="2">
								<itui:objectView itemId="tpNm" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
							
							<th scope="row">
								NCS 소분류
							</th>
							<td>
								<itui:objectView itemId="ncsSclasCd" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								훈련기간
							</th>
							<td>
								<itui:objectView itemId="trPeriod" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
							<th scope="row">
								훈련시간
							</th>
							<td>
								<itui:objectView itemId="trtm" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
							<th scope="row">
								훈련방법
							</th>
							<td>
								<itui:objectView itemId="trMth" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								평가목적
							</th>
							<td colspan="5">
								<itui:objectView itemId="trPurps" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
						</tr>
						<tr>
							<th scope="row">
								실시훈련과정코드
							</th>
							<td colspan="3">
								<itui:objectView itemId="tpCd" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
							<th scope="row">
								작성항목
							</th>
							<td>
								<c:out value="${dt.TR_RESULT_REPRT_SE eq '1' ? '만족도 및 성취도 조사' : '현업적용'}" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
				<table class="width-type02">
					<colgroup>
						<col width="10%">
						<col width="40%">
						<col width="auto">
						<col width="auto">
						<col width="auto">
						<col width="auto">
						<col width="auto">
					</colgroup>
					<tbody id="survey">
					
					</tbody>
				</table>
			</div>
		</div>
		</form>
        
 		<div class="btns-area" id="btns-area-1" style="margin-top: 30px;">
 			<div class="btns-right">
	            <button type="button" class="btn-m01 btn-color01 btn-back">
	            	목록
	            </button>
 			</div>
        </div>
	</div>
</div>


<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>