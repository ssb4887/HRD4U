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
		<h3 class="title-type01 ml0">만족도 조사</h3>
		<form id="frm" method="post">
			<input type="hidden" id="id_devlopIdx" name="devlopIdx" value="<c:out value="${getDev.DEVLOP_IDX}" />" />
			<input type="hidden" id="id_status" name="status" value="" />
			<div class="table-type02 horizontal-scroll">
				<table>
					<colgroup>
						<col width="15%">
						<col width="35%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" class="th align_l">
								<label for="id_planLearner">
									기업명
								</label>
							</th>
							<td colspan="2">
								<itui:objectView itemId="trCorpNm" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
							<th scope="row" class="th align_l">
								<label for="id_planValuer">
									훈련기관(기업)
								</label>
							</th>
							<td colspan="3">
								훈련기관
							</td>
						</tr>
						<tr>
							<th scope="row" class="th align_l">
								<label for="id_planSubject">
									훈련과정명
								</label>
							</th>
							<td colspan="2">
								<itui:objectView itemId="tpNm" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
							
							<th scope="row" class="th align_l">
								<label for="id_planWay">
									NCS 소분류
								</label>
							</th>
							<td colspan="3">
								<itui:objectView itemId="ncsSclasCd" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
						</tr>
						<tr>
							<th scope="row" class="th align_l">
								<label for="id_planDtm">
									훈련기간
								</label>
							</th>
							<td>
								<itui:objectView itemId="trPeriod" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
							<th scope="row" class="th align_l">
								<label for="id_planDtm">
									훈련시간
								</label>
							</th>
							<td>
								<itui:objectView itemId="trtm" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
							<th scope="row" class="th align_l">
								<label for="id_planTime">
									훈련방법
								</label>
							</th>
							<td colspan="3">
								<itui:objectView itemId="trMth" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
						</tr>
						<tr>
							<th scope="row" class="th align_l">
								<label for="id_planStan">
									평가목적
								</label>
							</th>
							<td colspan="6">
								<itui:objectView itemId="trPurps" itemInfo="${itemInfo}" objDt="${listDt}" />
							</td>
						</tr>
					</tbody>
					<tbody id="survey">
						
					</tbody>
				</table>
			</div>
		</form>
        
 		<div class="btns-area" id="btns-area-1" style="margin-top: 30px;">
            <button type="button" class="btn-b01 round01 btn-color02 left btn-back">
                <span>목록</span>
                <img src="${contextPath}/dct/images/icon/icon_arrow_right03.png" alt="" class="arrow01"/>
            </button>
        </div>
	</div>
</div>


<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>