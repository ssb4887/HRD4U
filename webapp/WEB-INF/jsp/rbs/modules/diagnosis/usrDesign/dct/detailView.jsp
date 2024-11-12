<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:set var="searchFormId" value="fn_techSupportSearchForm"/>
<c:set var="listFormId" value="fn_techSupportListForm"/>
<c:set var="inputWinFlag" value=""/><%/* 등록/수정창 새창으로 띄울 경우 사용 */%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/><%/* 수정버튼 class */%>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>
<style>
	span.update-max { margin-left: 32px; border: solid 1px; padding: 4px; border-radius: 8px; color: white; background-color: gray; cursor: pointer; }
	 .redgrad { background: rgb(131,58,180); background: linear-gradient(90deg,rgba(131,58,180,1) 0%,rgba(253,29,29,1) 50%,rgba(252,176,69,1) 100%); -webkit-background-clip: text; color: transparent; line-height: 1.2; padding: 5px 0; }
</style>

	<div class="contents-wrapper">
		<!-- CMS 시작 -->
		<div class="contents-area">
			<h3 class="title-type01 ml0">HRD 기초진단지</h3>
			<form id="dgnForm">
				<input type="hidden" class="bscIdx" name="bscIdx" value="${bscIdx }" />
				<input type="hidden" class="siteId" name="siteId" value="${siteId }" />
				<div class="title-wrapper clear">
					<p class="word-issue fl">
						(발급일자)
						<fmt:formatDate value="${basket.SYSDATE }" pattern="yyyy-MM-dd" />
					</p>
					<p class="word-issue fr">(발급번호) ${issue.ISSUE_NO }</p>
				</div>
	
				<div class="contents-box pl0">
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="15%">
								<col width="10%">
								<col width="">
								<col width="">
								<col width="">
								<col width="">
								<col width="20%">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" rowspan="4" class="bg01">기업개요</th>
									<th scope="row">기업명</th>
									<td colspan="3"><c:out value='${basket.CORP_NAME }' /></td>
									<th scope="row">사업장 관리번호</th>
									<td colspan="1"><c:out value='${basket.BPL_NO }' /></td>
								</tr>
								<tr>
	
									<th scope="row" rowspan="2" class="line">소재지</th>
									<td colspan="3" rowspan="2"><c:out value='${basket.CORP_LOCATION }' /></td>
									<th scope="row">업종</th>
									<td colspan="1"><c:out value='${basket.INDUTY_NAME }' /></td>
								</tr>
								<tr>
									<th scope="row" class="line">업종코드</th>
									<td colspan="1"><c:out value='${basket.INDUTY_CD }' /></td>
								</tr>
								<tr>
	
									<th scope="row" class="line">고용보험<br> 성립일자
									</th>
									<td><fmt:formatDate value='${basket.INSURANCE_DT }' pattern="yyyy-MM-dd" /></td>
									<th scope="row">기업유형</th>
									<td><c:out value='${basket.SCALE_CD }' /></td>
									<th scope="row">상시 근로자수</th>
									<td><c:out value='${basket.TOT_WORK_CNT }' /></td>
								</tr>
								<tr>
									<th scope="row" rowspan="2" class="bg01">기업 담당자</th>
									<th scope="row" rowspan="2">직위</th>
									<td rowspan="2"><c:out value="${empty corInfo.corpOfcps? '-' : corInfo.corpOfcps}" /></td>
									<th scope="row" rowspan="2">성명</th>
									<td rowspan="2"><c:out value="${empty corInfo.corpPicNm? '-' : corInfo.corpPicNm}" /></td>
									<th scope="row">연락처</th>
									<td><c:out value="${empty corInfo.corpPicTel? '-' : corInfo.corpPicTel}" /></td>
								</tr>
								<tr>
	
									<th scope="row" class="line">메일</th>
									<td><c:out value="${empty corInfo.corpPicEmail? '-' : corInfo.corpPicEmail}" /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
	
				<div class="contents-box pl0">
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<colgroup>
								<col width="15%">
								<col width="10%">
								<col width="8%">
								<col width="">
								<col width="">
								<col width="">
								<col width="">
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" rowspan="9" class="bg01">기업훈련현황</th>
									<th scope="row" rowspan="4">훈련<br>실시<br>이력
									</th>
									<th scope="col" class="bg02">연도</th>
									<th scope="col" class="bg02">참여사업</th>
									<th scope="col" class="bg02">훈련과정명</th>
									<th scope="col" class="bg02">훈련방법</th>
									<th scope="col" class="bg02">훈련기간(일)</th>
								</tr>
								<c:choose>
									<c:when test="${empty trainHis eq true}">
										<c:forEach begin="0" end="2" step="1">
											<tr>
												<td class="line">-</td>
												<td>-</td>
												<td>-</td>
												<td>-</td>
												<td>-</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:forEach items="${trainHis }" var="item" varStatus="status" end="2">
											<tr>
												<td class="line"><c:out value="${empty item.YEAR? '-' : item.YEAR}" /></td>
												<td><c:out value="${empty item.PROGRAM? '-' : item.PROGRAM}" /></td>
												<td><c:out value="${empty item.TRPR_NM? '-' : item.TRPR_NM}" /></td>
												<td><c:out value="${empty item.TR_METH_CD? '-' : item.TR_METH_CD}" /></td>
												<td><c:out value="${empty item.DAY? '-' : item.DAY}" /></td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
								<tr>
									<th scope="row" rowspan="5" class="line">훈련<br>지원<br>이력</th>
									<th scope="col" class="bg02">연도</th>
									<th scope="col" class="bg02" colspan="2">연간 정부지원 한도금액(원)
										(A) <span class="update-max" id="update-max">금액 변경</span>
									</th>
									<th scope="col" class="bg02">지원받은 금액(원) (B)</th>
									<th scope="col" class="bg02">비율(B/A)</th>
								</tr>
								<c:forEach items="${moneyHis }" var="item" varStatus="status" end="2">
									<tr>
										<td class="line"><c:out value="${item.YEAR }" /></td>
										<td class="right" colspan="2">
											<input type="text" id="numberDisplay${status.index }" onkeyup="addComma(this, ${status.index })" value="<fmt:formatNumber value="${item.MAX_PAY }" pattern="#,###,###,###,###"/>" />
											<input type="hidden" id="actualNumber${status.index }" name="actualNumber_${item.YEAR }" value="${item.MAX_PAY }" />
										</td>
										<td class="right">
											<span id="tot-pay${status.index }">
												<fmt:formatNumber value="${item.TOT_PAY }" pattern="#,###,###,###,###" />
											</span>
										</td>
										<td>
											<span id="percent${status.index }">
												<c:out value="${item.PERCENT }" />
											</span>%
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
	
	
				<div class="contents-box pl0">
					<div class="table-type02 horizontal-scroll">
						<table class="width-type02">
							<caption></caption>
							<colgroup>
								<col width="50%">
								<col width="50%">
							</colgroup>
							<thead>
								<tr>
									<th scope="col" colspan="2">최근 훈련 동향</th>
	
								</tr>
								<tr>
									<th scope="col">최근 직업훈련 실시 현황</th>
									<th scope="col">최근 직업훈련 지원 현황</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>
										<div id="exec" style="width: auto; height: 300px" ></div>
									</td>
									<td>
										<div id="sprt" style="width: auto; height: 300px" ></div>
									</td>
								</tr>
	
							</tbody>
	
						</table>
					</div>
				</div>
			</form>
		</div>
	
		<div class="contents-area">
			<h3 class="title-type01 ml0">HRD 기초진단 결과</h3>
	
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
								<th scope="row" rowspan="3">참여가능사업</th>
								<c:forEach items="${recommend }" var="item">
									<th scope="col">${item.PRTBIZ_NAME }</th>
								</c:forEach>
							</tr>
							<tr>
								<c:forEach items="${recommend }" var="item" varStatus="i">
									<td class="${i.index eq 1 ? 'line' : ''} left">${item.DC }</td>
								</c:forEach>
							<tr>
								<c:forEach items="${recommend }" var="item" varStatus="i">
									<td class="${i.index eq 1 ? 'line' : ''} left">${item.CONSIDERATION }</td>
								</c:forEach>
							</tr>
							<tr>
								<td colspan="4">
									<strong class="point-color01">※ 추가 설문(기업 교육훈련 수요 설문지)을 진행하시면 더 정확한 진단을 받으실 수 있습니다.</strong>
								</td>
							</tr>
						</tbody>
	
					</table>
				</div>
			</div>
	
	
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption></caption>
						<colgroup>
							<col width="15%">
							<col width="">
							<col width="">
							<col width="">
							<col width="">
						</colgroup>
						<thead>
							<tr>
								<th scope="col" colspan="5" class="bg02">HRD4U와 HRD-Net을 가입하면 더욱 다양한 직업능력개발훈련 콘텐츠를 만나보실 수 있습니다!</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th scope="row" rowspan="2">관련 정보 확인<br> (QR코드)</th>
								<c:forEach items="${qrs }" var="item" end="3">
									<td>
										<a href="${item.url }" target="_blank">
											<img src="${item.image }" alt="${item.title } QR코드 - 새창열림" class="qrcode">
										</a>
									</td>
								</c:forEach>
							</tr>
							<tr>
								<c:forEach items="${qrs }" var="item" end="3">
									<td class="line">${item.title }</td>
								</c:forEach>
							</tr>
						</tbody>
	
					</table>
				</div>
			</div>
	
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption></caption>
						<colgroup>
							<col width="15%">
							<col width="8%">
							<col width="">
							<col width="8%">
							<col width="">
							<col width="8%">
							<col width="">
						</colgroup>
	
						<tbody>
							<tr>
								<th scope="row" rowspan="2">공단 담당자</th>
								<th scope="row">기관명</th>
								<td><c:out value="${doc.INSTT_NAME }" /></td>
								<th scope="row">직위</th>
								<td><c:out value="${doc.DOCTOR_OFCPS }" /></td>
								<th scope="row">연락처</th>
								<td><c:out value="${empty doc.DOCTOR_TELNO? '-' : doc.DOCTOR_TELNO }" /></td>
							</tr>
							<tr>
								<th scope="row" class="line">부서명</th>
								<td><c:out value="${doc.DOCTOR_DEPT_NAME }" /></td>
								<th scope="row">성명</th>
								<td><c:out value="${doc.DOCTOR_NAME }" /></td>
								<th scope="row">메일</th>
								<td><c:out value="${doc.DOCTOR_EMAIL }" /></td>
							</tr>
						</tbody>
	
					</table>
				</div>
			</div>
	
	
			<div class="btns-area">
				<button type="button" class="btn-b01 round01 btn-color02 left" onclick="location.href='list.do?mId=36'">
					<span>목록</span>
					<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01" onclick="location.href='list.do?mId=36'" />
				</button>
				<a href="<c:out value="${URL_CLIPREPORT}&bscIdx=${bscIdx }"/>" target="_blank">
					<button type="button" class="btn-b01 round01 btn-color02 left">
						<span>출력하기</span>
						<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01" onclick="window.open('${pageContext.request.contextPath }/dct/diagnosis/clipReport.do?mId=36&bscIdx=${bscIdx }')" />
					</button>
				</a>
				<button type="button" class="btn-b01 round01 btn-color03 left btn-qustnr" data-bsc="${bscIdx}">
					<span> HRD컨설팅 신청 </span>
					<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01">
				</button>
			</div>
		</div>
	
		<div class="contents-area">
			<div class="btns-area">
				<div class="btns-right right">
					<span style="cursor: pointer" class="word-linked-notice01" onclick="gohope()">
						희망 사업이 없다면?
						<img src="${contextPath}${imgPath}/icon/icon_arrow_right04.png" alt="" />
					</span>
					<form id="hope" method="post" action="hope.do?mId=36">
						<input type="hidden" class="bpl_no" name="bpl_no" value="${basket.BPL_NO}">
					</form>
				</div>
			</div>
		</div>
	
		<!-- //CMS 끝 -->
	</div>
    
<form action="" method="post" style="display: none;" id="form-box">
	<input type="hidden" name="rslt" id="rslt" value="" />
	<input type="hidden" name="bsc" id="bsc" value="" />
</form>

<script>
function addComma(inputElement, index) {
	let numbersOnly = inputElement.value.replace(/[^0-9]/g,'');
	let formatted = parseFloat(numbersOnly).toLocaleString('en-US');
	console.log(formatted, isFinite(formatted))
	if(formatted == 'NaN') {
		formatted = 0;
	}
	inputElement.value = formatted;
	let tot_pay_e = document.querySelector(`span#tot-pay\${index}`);
	let percent_e = document.querySelector(`span#percent\${index}`);
	let totPay = tot_pay_e.textContent.replace(/[^0-9]/g, '');
	let percent = (parseFloat(totPay)/parseFloat(numbersOnly) * 100).toFixed(1)
	document.getElementById(`actualNumber\${index}`).value = numbersOnly;
	percent_e.textContent = percent
}
</script>
<script src="${contextPath}/assets/js/echarts.js"></script>
<%-- <script src="${contextPath}/dct/js/diagnosis/diagnosis.js"></script> --%>
<script src="${contextPath}/dct/js/diagnosis/detailView.js"></script>

<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>