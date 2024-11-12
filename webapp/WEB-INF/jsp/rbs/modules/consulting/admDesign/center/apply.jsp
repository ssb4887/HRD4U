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

<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<div class="contents-area">
		<h3 class="title-type01 ml0">기업HRD이음컨설팅 결과</h3>

		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
				<table class="width-type02">
					<caption></caption>
					<colgroup>
						<col width="15%">
						<col width="">
						<col width="">
						<col width="">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" rowspan="5">추천훈련사업</th>
							<th scope="col">
								<div>
									<span>추천 1순위</span>
								</div>
							</th>
							<th scope="col">
								<div>
									<span>추천 2순위</span>
								</div>
							</th>
							<th scope="col">
								<div>
									<span>추천 3순위</span>
								</div>
							</th>
						</tr>
						<tr>
							<th scope="col">
								<div class="title-wrapper02">
									<span id="name0" class="point-color01 name">${recommendedDatas[0].RCTR_NAME}</span>
								</div>
							</th>
							<th scope="col">
								<div class="title-wrapper02">
									<span id="name1" class="point-color01 name">${recommendedDatas[1].RCTR_NAME}</span>
								</div>
							</th>
							<th scope="col">
								<div class="title-wrapper02">
									<span id="name2" class="point-color01 name">${recommendedDatas[2].RCTR_NAME}</span>
								</div>
							</th>
						</tr>
						<tr>
							<td class="line left"><span id="desc0" class="desc">${recommendedDatas[0].INTRO}</span></td>
							<td class="left"><span id="desc1" class="desc">${recommendedDatas[1].INTRO}</span></td>
							<td class="left"><span id="desc2" class="desc">${recommendedDatas[2].INTRO}</span></td>
						</tr>
						<tr>
							<td class="line left"><span id="cons0" class="cons">${fn:replace(recommendedDatas[0].CONSIDER, newLine, '<br>')}</span></td>
							<td class="left"><span id="cons1" class="cons">${fn:replace(recommendedDatas[1].CONSIDER, newLine, '<br>')}</span></td>
							<td class="left"><span id="cons2" class="cons">${fn:replace(recommendedDatas[0].CONSIDER, newLine, '<br>')}</span></td>
						</tr>
						<tr>

						</tr>
						<tr>
							<th scope="row" rowspan="2">HRD 제안<br />(적합 훈련 및 과정제안)
							</th>
							<td class="left v-align-top">
								<ul id="train0" class="train">
								</ul>
							</td>
							<td class="left v-align-top">
								<ul id="train1" class="train">
								</ul>
							</td>
							<td class="left v-align-top">
								<ul id="train2" class="train">
								</ul>
							</td>
						</tr>
					</tbody>
				</table>


			</div>
			<div class="btns-area">
				<a href="${contextPath}/web/consulting/applyForm.do?mId=85&bplNo=${bplNo}&bsiscnslIdx=${bsiscnslIdx}">
					<button type="button" class="btn-b01 round01 btn-color03 left">
						<span>심층진단 신청</span> <img
							src="${contextPath}/web/images/icon/icon_arrow_right03.png" alt="" class="arrow01">
						
					</button>
				</a>
			</div>
		</div>
	</div>
</div>


<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>