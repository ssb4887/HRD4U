<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}" />
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page"
			value="${moduleJspRPath}/develop/recommendView.jsp" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />
<div class="contents-wrapper">
	<div class="contents-area02">
		<h3 class="word-type04 center">
			${corpInfo.BPL_NM}의 
			<c:choose>
				<c:when test="${bsisCnsl eq '0'}"> 기업 정보를</c:when>
				<c:otherwise>기업HRD이음컨설팅 결과를</c:otherwise>
			</c:choose>
			 참고하여 각 사업별 추천 훈련과정을 제시하였습니다.<br> 개발에 활용할 훈련과정을 선택해주십시오.
		</h3>
		<c:set var="tpList" value="0"/>
		<c:forEach var="tpDt" items="${trainingRecommend}" varStatus="i">
			<c:if test="${!empty tpDt.TP_IDX}">
				<c:set var="tpList" value="${tpList} , ${tpDt.TP_IDX}"/>
			</c:if>
		</c:forEach>
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
				<table class="width-type02">
					<caption></caption>
					<colgroup>
						<col width="">
						<col width="">
						<col width="">
					</colgroup>
					<tbody>
						<tr>
							<th scope="col"><div>
									<span>추천 1순위</span>
								</div></th>
							<th scope="col"><div>
									<span>추천 2순위</span>
								</div></th>
							<th scope="col"><div>
									<span>추천 3순위</span>
								</div></th>
						</tr>
						<tr>
							<c:forEach var="trainingDt" items="${trainingRecommend}" varStatus="i">
								<c:if
									test="${trainingDt.RANK lt 4 && (i.index eq 0 or trainingRecommend[i.index - 1].RANK ne trainingRecommend[i.index].RANK)}">
									<th scope="col">
										<div class="title-wrapper02">
											<span id="name" class="point-color01 name">${trainingRecommend[i.index].RCTR_NAME}</span>
										</div>
									</th>
								</c:if>
							</c:forEach>
						</tr>
						<tr>
							<c:forEach var="trainingDt" items="${trainingRecommend}" varStatus="i">
								<c:if
									test="${trainingDt.RANK lt 4 && (i.index eq 0 or trainingRecommend[i.index - 1].RANK ne trainingRecommend[i.index].RANK)}">
									<td class="left"><span id="desc0" class="desc">${trainingRecommend[i.index].INTRO}</span></td>
								</c:if>
							</c:forEach>
						</tr>
						<tr>
							<c:forEach var="trainingDt" items="${trainingRecommend}" varStatus="i">
								<c:if
									test="${trainingDt.RANK lt 4 && (i.index eq 0 or trainingRecommend[i.index - 1].RANK ne trainingRecommend[i.index].RANK)}">
									<td class="left"><span id="desc0" class="desc">${trainingRecommend[i.index].CONSIDER}</span></td>
								</c:if>
							</c:forEach>
						</tr>
						<tr>
							<th scope="row" colspan="3">사업별 추천 훈련과정</th>
						</tr>
						<tr>
							<c:forEach var="trainingDt" items="${trainingRecommend}" varStatus="i">
								<c:if test="${trainingDt.RANK lt 4 && (i.index eq 0 or trainingRecommend[i.index - 1].RANK ne trainingRecommend[i.index].RANK)}">
									<td class="left v-align-top">
									<span id="train1" class="train"> 
									<c:choose>
										<c:when test="${trainingDt.RCTR_NAME eq '외부 교육기관 훈련' || trainingDt.PRTBIZ_IDX eq '3'}">
											<a href="https://www.hrd.go.kr" class="point-color01 btn-linked" style="display:inline;">
                              					www.hrd.go.kr&nbsp;<img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;">
                              				</a>
											<br>HRD-NET 직업훈련지식포털에서 정부지원 훈련과정(기관)을 검색하세요.<br>
											(메뉴안내 : 훈련과정 → 기업훈련 과정 → 사업주훈련지원) 
										</c:when>
										<c:otherwise>
											<c:set var="tpCount" value="0"/>
											<c:forEach items="${trainingRecommend}" varStatus="j">
												<c:if test="${tpCount lt 3 && trainingRecommend[i.index].RANK eq trainingRecommend[j.index].RANK}">
													<c:if test="${!empty trainingRecommend[j.index].TP_NAME}">
		                               					<a href="${DEVELOP_TRAINING_VIEW_FORM_URL}&tpIdx=${trainingRecommend[j.index].TP_IDX}&prtbizIdx=${trainingRecommend[j.index].PRTBIZ_IDX}&bplNo=${corpInfo.BPL_NO}&isFromDevelop=Y&devlopIdx=${devlopIdx}" class="point-color01 btn-linked" style="display:inline;">
		                               						<span><c:out value="${trainingRecommend[j.index].TP_CD_RANK}. ${trainingRecommend[j.index].TP_NAME}"/></span>
		                               						<img src="${contextPath}${imgPath}/icon/icon_search04.png" style="display:inline;">
		                               					</a>
		                               					<c:set var="tpCount" value="${tpCount + 1}"/>
		                              					</c:if>
		                              					<br>
												</c:if>
											</c:forEach>
										</c:otherwise>
									</c:choose>
									</span>
									</td>
								</c:if>
							</c:forEach>
						</tr>
						<%-- <tr>
							<c:forEach items="${trainingRecommend }" varStatus="i">
								<c:if
									test="${i.index eq 0 or trainingRecommend[i.index - 1].RANK ne trainingRecommend[i.index].RANK}">
									<c:if test="${empty trainingRecommend[i.index].TP_IDX }">
										<td></td>
									</c:if>
									<c:if test="${!empty trainingRecommend[i.index].TP_IDX }">
										<td><a
											href="${DEVELOP_TRAINING_VIEW_FORM_URL}&tpIdx=${trainingRecommend[i.index].TP_IDX}&prtbizIdx=${trainingRecommend[i.index].PRTBIZ_IDX}&bplNo=${corpInfo.BPL_NO}&devlopIdx=${devlopIdx}"
											class="btn-m01 btn-color03">과정 상세보기</a></td>
									</c:if>
								</c:if>
							</c:forEach>
						</tr> --%>
					</tbody>
				</table>
			</div>
		</div>
		<div class="btns-area">
			<div class="btns-right right">
				<a href="${DEVELOP_TRAINING_LIST_FORM_URL}&bplNo=${corpInfo.BPL_NO}&devlopIdx=${devlopIdx}&isFromDevelop=Y"
					class="word-linked-notice03"> 희망하시는 훈련과정이 없으신가요? <span
					class="span-mobile-br"></span><span class="point-color01 underline">훈련과정 목록</span>에서 직접 선택 가능합니다. <img src="${contextPath}${imgPath}/icon/icon_arrow_right04.png"	alt="">
				</a>
			</div>
		</div>
	</div>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>