<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_supportInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/support/input.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
<div class="contents-wrapper">
	<div class="contents-area">
		<div class="title-wrapper clear mb0">
			<h3 class="title-type01 ml0 fl">능력개발클리닉 지원금 신청서</h3>
		</div>
		<div class="title-wrapper">
			<p class="state fl">신청상태 <strong>${confirmProgress[dt.CONFM_STATUS]}</strong></p>
		</div>
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${SUPPORT_MODIFY_URL}"/>&sportIdx=${dt.SPORT_IDX}" target="submit_target" enctype="multipart/form-data">
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
			<input type="hidden" name="bplNo" id="bplNo" value="${corpInfo.BPL_NO}">
			<input type="hidden" name="sportIdx" value="${dt.SPORT_IDX}">
			<input type="hidden" name="cliIdx" value="${dt.CLI_IDX}">
			<input type="hidden" id="planIdx" value="${activityList01[0].PLAN_IDX}">
			<input type="hidden" id="resltIdx" value="${activityList05[0].RESLT_IDX}">
			
			<!-- 훈련계획서와 성과보고서가 지원금 신청이 가능한지 확인하는 데이터 -->
			<!-- SPT_POSBL > 1 : 신청 내역이 없어서 신청 가능 -->
			<!-- SPT_POSBL > 0 : 이전에 신청한 내역이 없어서 신청 불가능 -->
			<input type="hidden" id="activity1" value="${activityList01[0].SPT_POSBL}">
			<input type="hidden" id="activity2" value="${activityList05[0].SPT_POSBL}">
			
			<c:set var="count" value="0" />
			<c:if test="${submitType eq 'modify'}">
				<c:set var="count" value="${fn:length(subList)}" />
			</c:if>
			<input type="hidden" name="maxIdx" id="maxIdx" value="${count}">
			
			<table class="width-type02">
				<caption>능력개발클리닉 지원금 신청서 입력표 : 기업명, 대표자명, 사업장등록번호, 참여연도, 지원항목, 지급기준, 당해연도 기지급금액, 신청금액, 신청가능 합계 금액에 관한 정보와 은행명, 예금주, 계좌번호, 증빙서류, 지원금 신청여부 입력표</caption>
				<colgroup>
					<col width="14%">
					<col width="auto">
					<col width="auto">
					<col width="20%">
					<col width="auto">
					<col width="auto">
					<col width="auto">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" colspan="7">기업 현황</th>
					</tr>
					<tr>
						<th scope="row">기업명</th>
						<td colspan="2" class="left">${corpInfo.BPL_NM}</td>
						<th scope="row">대표자명</th>
						<td colspan="2" class="left">${reqList.REPER_NM}</td>
					</tr>
					<tr>
						<th scope="row">사업장등록번호</th>
						<td colspan="2" class="left">${corpInfo.BPL_NO}</td>
						<th scope="row">참여연도</th>
						<td colspan="2" class="left"><fmt:formatDate pattern="yyyy" value="${dt.REGI_DATE}"/>년</td>
					</tr>
					<tr>
						<th scope="row" rowspan="3">지원금 지급계좌</th>
						<th scope="row" colspan="2">은행명&nbsp;<strong class="point-important">*</strong></th>
						<td colspan="4" class="left">${dt.BANK_NM}</td>
					</tr>
					<tr>
						<th scope="row" colspan="2">예금주&nbsp;<strong class="point-important">*</strong></th>
						<td colspan="4" class="left">${dt.DPSTR_NM}</td>
					</tr>
					<tr>
						<th scope="row" colspan="2">계좌번호&nbsp;<strong class="point-important">*</strong></th>
						<td colspan="4" class="left">${dt.ACNUTNO}</td>
					</tr>
					<tr>
						<th scope="row" colspan="7">지원금 신청내역</th>
					</tr>
					<tr>
						<th scope="row">지원항목</th>
						<th scope="row" colspan="2">지급기준</th>
						<th scope="row">증빙서류</th>
						<th scope="row">당해연도<br>기지원금액(원)</th>
						<th scope="row">지원금 신청여부&nbsp;<strong class="point-important">*</strong></th>
						<th scope="row">신청금액(원)</th>
					</tr>
					<c:forEach var="listDt" items="${sportList}" varStatus="i">
						<tr>
							<th scope="col">${listDt.SPORT_NM}</th>
							<td id="totalCost${i.count}" <c:if test="${!empty listDt.TOTALCOST}"> data-cost="${listDt.TOTALCOST}"</c:if>>
								<c:choose>
									<c:when test="${i.count eq 1 || i.count eq 2}">
										<fmt:formatNumber value="${listDt.COST}"/>원
									</c:when>
									<c:when test="${i.count eq 3 || i.count eq 5}">
										회당 <fmt:formatNumber value="${listDt.COST}"/>원<br>
										(연 <fmt:formatNumber value="${listDt.TOTALCOST}"/>원)
									</c:when>
									<c:when test="${i.count eq 6}">
										과정당 <fmt:formatNumber value="${listDt.COST}"/>원<br>
										(연 <fmt:formatNumber value="${listDt.TOTALCOST}"/>원)
									</c:when>
									<c:otherwise>
										연 <fmt:formatNumber value="${listDt.TOTALCOST}"/>원
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${listDt.SILBIYN eq 'Y'}">
										<c:if test="${listDt.SPORT_CD eq '04'}">정액/</c:if>실비</c:when>
									<c:otherwise>정액</c:otherwise>
								</c:choose>
							</td>
							<td>
								<div>
									<c:choose>
										<c:when test="${i.count eq '1'}">
											<span id="actUrl${i.count}">
											<c:forEach var="subDt" items="${subList}" varStatus="j">
												<c:if test="${listDt.SPORT_CD eq subDt.SPORT_ITEM_CD}">
													<a href="${contextPath}/${crtSiteId}/clinicDct/plan_view_form.do?mId=67&planIdx=${subDt.SPORT_ITEM_IDX}" target="_blank"><strong class="point-color01">훈련계획서</strong></a>
													<input type="hidden" name="acmsltIdx${subDt.DTL_SN}" value="${subDt.SPORT_ITEM_IDX}">
													<input type="hidden" name="sportItemCd${subDt.DTL_SN}" value="${subDt.SPORT_ITEM_CD}">
													<input type="hidden" name="sptPay${subDt.DTL_SN}" id="sptPay${subDt.DTL_SN}" value="${subDt.SPT_PAY}">
													<input type="hidden" name="acexpYn${subDt.DTL_SN}" value="${subDt.ACEXP_SPORT_YN}">
												</c:if>
											</c:forEach>
											</span>
										</c:when>
										<c:when test="${i.count eq '2'}">
											<span id="actUrl${i.count}">
											<c:forEach var="subDt" items="${subList}" varStatus="j">
												<c:if test="${listDt.SPORT_CD eq subDt.SPORT_ITEM_CD}">
													<a href="${contextPath}/${crtSiteId}/clinicDct/result_view_form.do?mId=87&resltIdx=${subDt.SPORT_ITEM_IDX}" target="_blank"><strong class="point-color01">성과보고서</strong></a>
													<input type="hidden" name="acmsltIdx${subDt.DTL_SN}" value="${subDt.SPORT_ITEM_IDX}">
													<input type="hidden" name="sportItemCd${subDt.DTL_SN}" value="${subDt.SPORT_ITEM_CD}">
													<input type="hidden" name="sptPay${subDt.DTL_SN}" id="sptPay${subDt.DTL_SN}" value="${subDt.SPT_PAY}">
													<input type="hidden" name="acexpYn${subDt.DTL_SN}" value="${subDt.ACEXP_SPORT_YN}">
												</c:if>
											</c:forEach>
											</span>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${listDt.SPORT_CD eq '04'}"><c:set var="itemName" value="HRD담당자 역량개발"/></c:when>
												<c:when test="${listDt.SPORT_CD eq '08'}"><c:set var="itemName" value="판로개척/인력채용"/></c:when>
												<c:when test="${listDt.SPORT_CD eq '06'}"><c:set var="itemName" value="HRD 성과교류"/></c:when>
											</c:choose>
											<a href="#" class="btn-linked" id="open-modal${i.count}" data-idx="${i.count}"><img src="${contextPath}${imgPath}/icon/icon_search04.png" alt="${itemName} 활동일지 찾아보기" style="display:inline; margin-bottom:5px;"></a>
											<span id="actUrl${i.count}">
											<c:forEach var="subDt" items="${subList}" varStatus="j">
												<c:if test="${listDt.SPORT_CD eq subDt.SPORT_ITEM_CD}">
													<span style="display:block;"><a href="${contextPath}/${crtSiteId}/clinicDct/activity_view_form.do?mId=68&acmsltIdx=${subDt.SPORT_ITEM_IDX}" target="_blank"><strong class="point-color01">${itemName} 활동일지</strong></a></span>
													<input type="hidden" name="acmsltIdx${subDt.DTL_SN}" value="${subDt.SPORT_ITEM_IDX}">
													<input type="hidden" name="sportItemCd${subDt.DTL_SN}" value="${subDt.SPORT_ITEM_CD}">
													<input type="hidden" name="sptPay${subDt.DTL_SN}" id="sptPay${subDt.DTL_SN}" value="${subDt.SPT_PAY}">
													<input type="hidden" name="acexpYn${subDt.DTL_SN}" value="${subDt.ACEXP_SPORT_YN}">
												</c:if>
											</c:forEach>
											</span>
										</c:otherwise>
									</c:choose>
								</div>
							</td>
							<td>
								<!-- dataPay : 당해연도 기지원금액 -->
								<c:set var="dataPay" value="0"/>
								<!-- posblPay : 현재 신청가능한 총금액, alreadyPayList에 해당 지원항목이 있으면 신청가능한 총금액도 같이 있어서 그걸로 setting, alreadyPayList에 해당 지원항목이 없으면 지급기준 그대로 적용 -->
								<c:set var="posblPay" value="${listDt.TOTALCOST}"/>
								<!-- 올해에 지급완료된 항목이 있을 때 -->
								<c:if test="${!empty alreadyPayList}">
								<c:forEach var="payDt" items="${alreadyPayList}" varStatus="j">
									<c:if test="${listDt.SPORT_CD eq payDt.SPORT_ITEM_CD}">
										<c:set var="dataPay" value="${payDt.SPT_PAY}"/>
										<c:set var="posblPay" value="${payDt.POSBL_PAY}"/>
									</c:if>
								</c:forEach>
								</c:if>
								<span id="possiblePay${i.count}" data-posblPay="${posblPay}"><fmt:formatNumber value="${dataPay}"/></span>
							</td>
							<td>
								<!-- isPlan : 훈련계획서에서 해당 지원항목을 신청했는지 여부 확인 -->
								<c:set var="isPlan" value="0"/>
								<c:set var="check" value=""/>
								<c:forEach var="planSub" items="${planSubList}" varStatus="j">
									<c:if test="${listDt.SPORT_CD eq planSub.SPORT_ITEM_CD && planSub.REQST_YN eq 'Y'}"><c:set var="isPlan" value="1"/></c:if>
								</c:forEach>
								<c:if test="${submitType eq 'modify'}">
									<c:forEach var="subGroupDt" items="${subListGroup}" varStatus="j">
										<c:if test="${listDt.SPORT_CD eq subGroupDt.SPORT_ITEM_CD}">
											<c:set var="check" value="checked"/>
										</c:if>
									</c:forEach>
								</c:if>
								<input type="checkbox" name="reqstYn${i.count}" id="reqstYn${i.count}" class="checkbox-type01" value="Y" ${check} data-isPlan="${isPlan}">
							</td>
							<td id="pay${i.count}">
								<!-- payAmt : 신청금액 -->
								<c:set var="payAmt" value=""/>
								<c:if test="${submitType eq 'modify'}">
									<c:forEach var="subGroupDt" items="${subListGroup}" varStatus="j">
										<c:if test="${listDt.SPORT_CD eq subGroupDt.SPORT_ITEM_CD}">
											<c:set var="payAmt" value="${subGroupDt.SPT_PAY}"/>
										</c:if>
									</c:forEach>
									<fmt:formatNumber value="${payAmt}"/>
								</c:if>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<th scope="row" colspan="6">신청가능 합계 금액(원)<input type="hidden" name="totPay" id="totPay" value="${dt.TOT_SPT_PAY}"></th>
						<td>
							<c:set var="tot" value="0"/>
							<c:if test="${submitType eq 'modify'}"><c:set var="tot" value="${dt.TOT_SPT_PAY}"/></c:if>
<%-- 							<input type="hidden" name="totPay" id="totPay" value="${tot}"> --%>
							<span id="totalPay"><fmt:formatNumber value="${tot}"/></span>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="btns-area">
		<div class="btns-right">
			<button type="submit" class="btn-m01 btn-color03 depth2 fn_btn_submit">저장</button>
			<a href="<c:out value="${SUPPORT_LIST_FORM_URL}"/>" title="목록으로 이동" class="btn-m01 btn-color01 depth2 fn_btn_write" onclick="return confirm('목록으로 이동하시겠습니까? \n저장을 하지 않으면 수정한 내용은 저장되지 않습니다.')">목록</a>
		</div>
	</div>
	</form>
</div>
<!-- HRD역량개발 활동일지 선택 모달창 -->
	<div class="mask3"></div>
	<div class="modal-wrapper type02" id="modal-action03">
		<!-- 다른 지원금 신청서에서 HRD역량개발 활동일지 전부를 비용신청 했는지 확인 -->
		<c:set var="activity3" value="1"/>
		<c:if test="${empty activityList04}">
		<c:set var="activity3" value="0"/>
		</c:if>
		<input type="hidden" id="activity3" value="${activity3}"/>
		<h2>HRD담당자 역량개발 활동일지 목록</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<div class="title-wrapper">
					<div class="fr">
						<button type="button" class="btn-m02 btn-color02 fn-search-submit" data-idx="3">선택</button>
					</div>
				</div>
				<div class="contents-box pl0">
					<div class="table-type01 horizontal-scroll">
						<table class="width-type02">
						<caption>HRD담당자 역량개발 활동일지 목록 정보표 : 활동시작일, 제목, 등록일에 관한 정보 제공표</caption>
						<colgroup>
							<col style="width:5%">
							<col style="width:auto">
							<col style="width:40%">
							<col style="width:auto">
						</colgroup>
						<thead>
						<tr>										
							<th scope="col"><input type="checkbox" id="selectAll" name="selectAll" class="checkbox-type01" title="<spring:message code="item.select.all"/>"/></th>
							<th scope="col">활동시작일</th>
							<th scope="col">제목</th>
							<th scope="col">등록일</th>
						</tr>
						</thead>
						<tbody class="alignC">
							<c:if test="${empty activityList04}">
							<tr>
								<td colspan="4" class="bllist"><spring:message code="message.no.list"/></td>
							</tr>
							</c:if>
							<c:forEach var="act04" items="${activityList04}" varStatus="i">
							<tr>
								<td><input type="checkbox" name="select" value="${act04.ACMSLT_IDX}" id="check03${act04.ACMSLT_IDX}" class="checkbox-type01" data-pay="${act04.ACMSLT_SPORT_AMT}" data-acexp="${act04.ACEXP_SPORT_YN}"/></td>
								<td><fmt:formatDate pattern="yyyy-MM-dd" value="${act04.ACMSLT_START_DT}"/></td>
								<td>
									<a href="${contextPath}/${crtSiteId}/clinicDct/activity_view_form.do?mId=68&acmsltIdx=${act04.ACMSLT_IDX}" target="_blank" class="btn-linked" style="display:inline;">
										<strong class="point-color01"><fmt:formatDate pattern="yyyy" value="${act04.REGI_DATE}"/>년 HRD담당자 역량개발 활동일지</strong>
									</a>
								</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd" value="${act04.REGI_DATE}"/></td>
							</tr>	
							</c:forEach>							
						</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<button type="button" class="btn-modal-close" data-idx="3">모달 창 닫기</button>
	</div>
	<!-- 판로개척/인력채용 활동일지 선택 모달창 -->
	<div class="mask4"></div>
	<div class="modal-wrapper type02" id="modal-action04">
		<!-- 다른 지원금 신청서에서 판로개척/인력채용 활동일지 전부를 비용신청 했는지 확인 -->
		<c:set var="activity4" value="1"/>
		<c:if test="${empty activityList08}">
		<c:set var="activity4" value="0"/>
		</c:if>
		<input type="hidden" id="activity4" value="${activity4}"/>
		<h2>판로개척/인력채용 활동일지 목록</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<div class="title-wrapper">
					<div class="fr">
						<button type="button" class="btn-m02 btn-color02 fn-search-submit" data-idx="4">선택</button>
					</div>
				</div>
				<div class="contents-box pl0">
					<div class="table-type01 horizontal-scroll">
						<table class="width-type02">
							<caption>판로개척/인력채용 활동일지 목록 정보표 : 활동시작일, 제목, 등록일에 관한 정보 제공표</caption>
							<colgroup>
								<col style="width:5%">
								<col style="width:auto">
								<col style="width:40%">
								<col style="width:auto">
							</colgroup>
							<thead>
							<tr>										
								<th scope="col"><input type="checkbox" id="selectAll" name="selectAll" class="checkbox-type01" title="<spring:message code="item.select.all"/>"/></th>
								<th scope="col">활동시작일</th>
								<th scope="col">제목</th>
								<th scope="col">등록일</th>
							</tr>
							</thead>
							<tbody class="alignC">
								<c:if test="${empty activityList08}">
								<tr>
									<td colspan="4" class="bllist"><spring:message code="message.no.list"/></td>
								</tr>
								</c:if>
								<c:forEach var="act08" items="${activityList08}" varStatus="i">
								<tr>
									<td><input type="checkbox" name="select" value="${act08.ACMSLT_IDX}" id="check04${act08.ACMSLT_IDX}" class="checkbox-type01" data-pay="${act08.ACMSLT_SPORT_AMT}" data-acexp="${act08.ACEXP_SPORT_YN}"/></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${act08.ACMSLT_START_DT}"/></td>
									<td>
										<a href="${contextPath}/${crtSiteId}/clinicDct/activity_view_form.do?mId=68&acmsltIdx=${act08.ACMSLT_IDX}" target="_blank" class="btn-linked" style="display:inline;">
											<strong class="point-color01"><fmt:formatDate pattern="yyyy" value="${act08.REGI_DATE}"/>년 판로개척/인력채용 활동일지</strong>
										</a>
									</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${act08.REGI_DATE}"/></td>
								</tr>	
								</c:forEach>							
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<button type="button" class="btn-modal-close" data-idx="4">모달 창 닫기</button>
	</div>
	<!-- HRD 성과교류 활동일지 선택 모달창 -->
	<div class="mask5"></div>
	<div class="modal-wrapper type02" id="modal-action05">
		<!-- 다른 지원금 신청서에서 HRD 성과교류 활동일지 전부를 비용신청 했는지 확인 -->
		<c:set var="activity5" value="1"/>
		<c:if test="${empty activityList09}">
		<c:set var="activity5" value="0"/>
		</c:if>
		<input type="hidden" id="activity5" value="${activity5}"/>
		<h2>HRD 성과교류 활동일지 목록</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<div class="title-wrapper">
					<div class="fr">
						<button type="button" class="btn-m02 btn-color02 fn-search-submit" data-idx="5">선택</button>
					</div>
				</div>
				<div class="contents-box pl0">
					<div class="table-type01 horizontal-scroll">
						<table class="width-type02">
							<caption>HRD 성과교류 활동일지 목록 정보표 : 활동시작일, 제목, 등록일에 관한 정보 제공표</caption>
							<colgroup>
								<col style="width:5%">
								<col style="width:auto">
								<col style="width:40%">
								<col style="width:auto">
							</colgroup>
							<thead>
							<tr>										
								<th scope="col"><input type="checkbox" id="selectAll" name="selectAll" class="checkbox-type01" title="<spring:message code="item.select.all"/>"/></th>
								<th scope="col">활동시작일</th>
								<th scope="col">제목</th>
								<th scope="col">등록일</th>
							</tr>
							</thead>
							<tbody class="alignC">
								<c:if test="${empty activityList09}">
								<tr>
									<td colspan="4" class="bllist"><spring:message code="message.no.list"/></td>
								</tr>
								</c:if>
								<c:forEach var="act09" items="${activityList09}" varStatus="i">
								<tr>
									<td><input type="checkbox" name="select" value="${act09.ACMSLT_IDX}" id="check05${act09.ACMSLT_IDX}" class="checkbox-type01" data-pay="${act09.ACMSLT_SPORT_AMT}" data-acexp="${act09.ACEXP_SPORT_YN}"/></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${act09.ACMSLT_START_DT}"/></td>
									<td>
										<a href="${contextPath}/${crtSiteId}/clinicDct/activity_view_form.do?mId=68&acmsltIdx=${act09.ACMSLT_IDX}" target="_blank" class="btn-linked" style="display:inline;">
											<strong class="point-color01"><fmt:formatDate pattern="yyyy" value="${act09.REGI_DATE}"/>년 HRD 성과교류 활동일지</strong>
										</a>
									</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${act09.REGI_DATE}"/></td>
								</tr>	
								</c:forEach>							
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<button type="button" class="btn-modal-close" data-idx="5">모달 창 닫기</button>
	</div>
	<!-- 훈련과정 자체개발 활동일지 선택 모달창 -->
	<div class="mask6"></div>
	<div class="modal-wrapper type02" id="modal-action06">
		<!-- 다른 지원금 신청서에서 훈련과정 자체개발 활동일지 전부를 비용신청 했는지 확인 -->
		<c:set var="activity6" value="1"/>
		<c:if test="${empty activityList10}">
		<c:set var="activity6" value="0"/>
		</c:if>
		<input type="hidden" id="activity6" value="${activity6}"/>
		<h2>훈련과정 자체개발 활동일지 목록</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<div class="title-wrapper">
					<div class="fr">
						<button type="button" class="btn-m02 btn-color02 fn-search-submit" data-idx="6">선택</button>
					</div>
				</div>
				<div class="contents-box pl0">
					<div class="table-type01 horizontal-scroll">
						<table class="width-type02">
							<caption>훈련과정 자체개발 활동일지 목록 정보표 : 활동시작일, 제목, 등록일에 관한 정보 제공표</caption>
							<colgroup>
								<col style="width:5%">
								<col style="width:auto">
								<col style="width:40%">
								<col style="width:auto">
							</colgroup>
							<thead>
							<tr>										
								<th scope="col"><input type="checkbox" id="selectAll" name="selectAll" class="checkbox-type01" title="<spring:message code="item.select.all"/>"/></th>
								<th scope="col">활동시작일</th>
								<th scope="col">제목</th>
								<th scope="col">등록일</th>
							</tr>
							</thead>
							<tbody class="alignC">
								<c:if test="${empty activityList10}">
								<tr>
									<td colspan="4" class="bllist"><spring:message code="message.no.list"/></td>
								</tr>
								</c:if>
								<c:forEach var="act10" items="${activityList10}" varStatus="i">
								<tr>
									<td><input type="checkbox" name="select" value="${act10.ACMSLT_IDX}" id="check06${act10.ACMSLT_IDX}" class="checkbox-type01" data-pay="${act10.ACMSLT_SPORT_AMT}" data-acexp="${act10.ACEXP_SPORT_YN}"/></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${act10.ACMSLT_START_DT}"/></td>
									<td>
										<a href="${contextPath}/${crtSiteId}/clinicDct/activity_view_form.do?mId=68&acmsltIdx=${act10.ACMSLT_IDX}" target="_blank" class="btn-linked" style="display:inline;">
											<strong class="point-color01"><fmt:formatDate pattern="yyyy" value="${act10.REGI_DATE}"/>년 훈련과정 자체개발 활동일지</strong>
										</a>
									</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${act10.REGI_DATE}"/></td>
								</tr>	
								</c:forEach>							
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<button type="button" class="btn-modal-close" data-idx="6">모달 창 닫기</button>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>