<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/support/view.jsp"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
<div id="cms_board_article" class="contents-wrapper">
	<%-- table summary, 항목출력에 사용 --%>
	<form id="clinic_support" name="clinic_support" method="post" action="#" target="submit_target" enctype="multipart/form-data">
	<input type="hidden" name="cliIdx" id="cliIdx" value="${dt.CLI_IDX}">
	<input type="hidden" name="sportIdx" id="cliIdx" value="${dt.SPORT_IDX}">
	
	<%-- 2. 디자인에 맞게 필요한 항목만 출력하는 경우 --%>
	<div class="contents-box pl0">
		<div class="title-wrapper">
			<p class="state fl">신청상태 <strong>${confirmProgress[dt.CONFM_STATUS]}</strong></p>
		</div>
		<div class="table-type02 horizontal-scroll" id="requestForm">
        <table class="width-type02">
        	<caption>능력개발클리닉 지원금 신청서 정보표 : 기업명, 대표자명, 사업장등록번호, 참여연도, 은행명, 예금주, 계좌번호, 지원항목, 지급기준, 증빙서류, 당해연도 기지급금액, 지원금 신청여부, 신청금액, 신청가능 합계 금액, 주치의 의견에 관한 정보 제공표</caption>
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
					<td colspan="3" class="left">${reqList.REPER_NM}</td>
				</tr>
				<tr>
					<th scope="row">사업장등록번호</th>
					<td colspan="2" class="left">${corpInfo.BPL_NO}</td>
					<th scope="row">참여연도</th>
					<td colspan="3" class="left"><fmt:formatDate value="${dt.REGI_DATE}" pattern="yyyy"/>년</td>
				</tr>
				<tr>
					<th scope="row" rowspan="3">지원금 지급계좌</th>
					<th scope="row" colspan="2">은행명</th>
					<td colspan="4" class="left">${dt.BANK_NM}</td>
				</tr>
				<tr>
					<th scope="row" colspan="2">예금주</th>
					<td colspan="4" class="left">${dt.DPSTR_NM}</td>
				</tr>
				<tr>
					<th scope="row" colspan="2">계좌번호</th>
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
					<th scope="row">지원금 신청여부</th>
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
							<c:choose>
								<c:when test="${i.count eq '1'}">
									<c:forEach var="subDt" items="${subList}" varStatus="j">
										<c:if test="${subDt.SPORT_ITEM_CD eq '01'}">
											<a href="${contextPath}/${crtSiteId}/clinicDct/plan_view_form.do?mId=67&planIdx=${subDt.SPORT_ITEM_IDX}&bplNo=${corpInfo.BPL_NO}" target="_blank"><strong class="point-color01">훈련계획서</strong></a>
										</c:if>
									</c:forEach>
								</c:when>
								<c:when test="${i.count eq '2'}">
									<c:forEach var="subDt" items="${subList}" varStatus="j">
										<c:if test="${subDt.SPORT_ITEM_CD eq '05'}">
											<a href="${contextPath}/${crtSiteId}/clinicDct/result_view_form.do?mId=87&resltIdx=${subDt.SPORT_ITEM_IDX}&bplNo=${corpInfo.BPL_NO}" target="_blank"><strong class="point-color01">성과보고서</strong></a>
										</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach var="subDt" items="${subList}" varStatus="j">
										<c:if test="${listDt.SPORT_CD eq subDt.SPORT_ITEM_CD}">
											<c:choose>
												<c:when test="${subDt.SPORT_ITEM_CD eq '04'}"><c:set var="itemName" value="HRD 역량개발"/></c:when>
												<c:when test="${subDt.SPORT_ITEM_CD eq '08'}"><c:set var="itemName" value="판로개척/인력채용"/></c:when>
												<c:when test="${subDt.SPORT_ITEM_CD eq '09'}"><c:set var="itemName" value="HRD 성과교류"/></c:when>
												<c:otherwise><c:set var="itemName" value="훈련과정 자체개발"/></c:otherwise>
											</c:choose>
											<span style="display:block;"><a href="${contextPath}/${crtSiteId}/clinicDct/activity_view_form.do?mId=68&acmsltIdx=${subDt.SPORT_ITEM_IDX}&bplNo=${corpInfo.BPL_NO}" target="_blank"><strong class="point-color01">${itemName} 활동일지</strong></a></span>
										</c:if>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:set var="dataPay" value="0"/>
							<!-- 올해에 지급완료된 항목이 있을 때 -->
							<c:if test="${!empty alreadyPayList}">
							<c:forEach var="payDt" items="${alreadyPayList}" varStatus="j">
								<c:if test="${listDt.SPORT_CD eq payDt.SPORT_ITEM_CD}">
									<c:set var="dataPay" value="${payDt.SPT_PAY}"/>
								</c:if>
							</c:forEach>
							</c:if>
							<fmt:formatNumber value="${dataPay}"/>
						</td>
						<td>
							<c:set var="checked" value="" />
							<c:forEach var="subGroupDt" items="${subListGroup}" varStatus="j">
								<c:if test="${subGroupDt.SPORT_ITEM_CD eq listDt.SPORT_CD}">V</c:if>
							</c:forEach>
						</td>
						<td>
							<c:forEach var="subGroupDt" items="${subListGroup}" varStatus="j">
								<c:if test="${subGroupDt.SPORT_ITEM_CD eq listDt.SPORT_CD}">${subGroupDt.SPT_PAY}</c:if>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<th scope="row" colspan="6">신청가능 합계 금액(원)</th>
					<td id="totalPay">${dt.TOT_SPT_PAY}</td>
				</tr>
				<c:if test="${dt.CONFM_STATUS gt '10'}">
                   	<tr>
                  		<th scope="row">주치의 의견</th>
                   		<td colspan="6" class="left">
	                    	<c:choose>
								<c:when test="${(loginVO.clsfCd eq 'Y' && dt.CONFM_STATUS eq '50') || (loginVO.usertypeIdx ne '40' && (dt.CONFM_STATUS eq '30' || dt.CONFM_STATUS eq '35'))}">
									<itui:objectTextarea itemId="doctorOpinion" itemInfo="${itemInfo}" objDt="${dt}" objClass="w100"/>
								</c:when>        
								<c:otherwise>
									${dt.DOCTOR_OPINION}
								</c:otherwise>             			
	                 		</c:choose>
                   		</td>
                   	</tr>
               	</c:if>
			</tbody>
        </table>
    	</div>
	</div>
	<div class="btns-area">
		<div class="btns-right">
			<c:if test="${loginVO.usertypeIdx ne '40'}">
				<c:choose>
					<c:when test="${dt.CONFM_STATUS eq '10'}">
						<!-- 신청 상태가 신청(10)일 때만 접수 가능 -->
						<a href="${SUPPORT_ACCEPT_URL}&sportIdx=${dt.SPORT_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color03 depth2" onclick="return confirm('해당 능력개발클리닉 신청서를 접수 처리하시겠습니까?')">${confirmProgress['30']}</a>
					</c:when>
					<c:when test="${dt.CONFM_STATUS eq '30'}">
						<!-- 신청 상태가 접수(10)일 때 수정, 저장, 1차승인, 반려 -->
						<a href="${SUPPORT_MODIFY_FORM_URL}&sportIdx=${dt.SPORT_IDX}&bplNo=${corpInfo.BPL_NO}" class="btn-m01 btn-color03 depth2 fn_btn_modify">수정</a>
						<a href="${SUPPORT_FIRSTAPPROVE_URL}&sportIdx=${dt.SPORT_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color03 depth2" onclick="return confirm('해당 능력개발클리닉 지원금 신청서를 1차승인하시겠습니까?')">1차승인</a>
						<button type="submit" class="btn-m01 btn-color03 depth2 btn-fn-return">반려</button>
					</c:when>
					<c:when test="${dt.CONFM_STATUS eq '35'}">
						<!-- 신청 상태가 반려요청(35)일 때는 주치의 의견 입력 후 반려만 가능 -->
						<button type="submit" class="btn-m01 btn-color03 depth2 btn-fn-return">반려</button>
					</c:when>
					<c:when test="${dt.CONFM_STATUS eq '50'}">
						<c:choose>
							<c:when test="${loginVO.clsfCd eq 'Y' || loginVO.usertypeIdx eq '55'}">
								<!-- 1차승인 후 부장일 때 최종승인, 반려가능 -->
								<a href="${SUPPORT_APPROVE_URL}&sportIdx=${dt.SPORT_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color03 depth2" onclick="return confirm('해당 능력개발클리닉 지원금 신청서를 최종승인하시겠습니까?')">${confirmProgress['55']}</a>
								<button type="submit" class="btn-m01 btn-color03 depth2 btn-returnToAccept">반려</button>
							</c:when>
							<c:otherwise>
								<!-- 1차승인 후 전담주치의가 1차승인한 신청서를 회수가능(접수 상태로 되돌림) -->
								<a href="${SUPPORT_ACCEPT_URL}&sportIdx=${dt.SPORT_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color03 depth2" onclick="return confirm('해당 능력개발클리닉 지원금 신청서를 회수 처리하시겠습니까?')">회수</a>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:when test="${dt.CONFM_STATUS eq '55' || dt.CONFM_STATUS eq '75'}">
						<!-- 최종승인 후 중도포기 가능  -->
						<a href="${SUPPORT_DROP_URL}&sportIdx=${dt.SPORT_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color03 depth2" onclick="return confirm('해당 능력개발클리닉 지원금 신청서를 중도포기 처리하시겠습니까?')">${confirmProgress['80']}</a>
					</c:when>
				</c:choose>
			</c:if>
			<a href="<c:out value="${SUPPORT_LIST_FORM_URL}"/>" title="목록으로 이동" class="btn-m01 btn-color01 depth2">목록</a>
		</div>
	</div>
	</form>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>