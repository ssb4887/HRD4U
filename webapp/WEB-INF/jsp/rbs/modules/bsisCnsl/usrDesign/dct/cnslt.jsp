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
<c:set var="corp_pic_name" value="${basket.CORP_PIC_NAME ? basket.CORP_PIC_NAME : corpPIC.CORP_PIC_NAME ? corPIC.CORP_PIC_NAME : '-' }" />
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
	span.update-max { margin-left: 32px; border: solid 1px; padding: 4px; border-radius: 8px; color: white; background-color: gray; cursor: pointer; }
	
	@keyframes spin {
		0% { transform: translate(-50%, -50%) rotate(0deg); }
		100% { transform: translate(-50%, -50%) rotate(360deg); }
	}
	
	/* modal table style */
	.table-container {
		min-height: 300px;
		max-height: calc(100vh - 400px);
		overflow: auto;
	}
	
	.modal-table {
		table-layout: fixed;
	}
	
	.modal-thead {
		position: sticky;
		top:0;
		z-index:1;
	}
</style>
<script type="text/javascript"  src="${pageContext.request.contextPath }/assets/js/echarts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }<c:out value="${jsPath}/bsisCnsl/cnslt.js"/>"></script>

<div id="overlay"></div>
<div class="loader"></div>

	<%-- <a href="<c:out value="${URL_CLIPREPORT}&bscIdx=80"/>" target="_blank">출력하기</a> --%>
	
	<div class="contents-wrapper">

		<!-- CMS 시작 -->
		<form action="/dct/recommend/saveConsult.do?mId=37" id="consult">
			<div class="contents-area">
				<h3 class="title-type01 ml0">기업HRD이음컨설팅 보고서</h3>
	
				<div class="title-wrapper clear">
					<p class="word-issue fl">
						(발급일자)
						<c:out value="${basket.ISSUE_DATE }" />
					</p>
	
					<p class="word-issue fr">
						(발급번호)
						<c:out value="${basket.ISSUE_NO }" />
					</p>
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
									<td colspan="3"><c:out value="${basket.CORP_NAME }" /></td>
									<th scope="row">사업장 관리번호</th>
									<td colspan="1"><c:out value="${basket.BPL_NO }" /></td>
								</tr>
								<tr>
	
									<th scope="row" rowspan="2" class="line">소재지</th>
									<td colspan="3" rowspan="2"><c:out value="${basket.CORP_LOCATION }" /></td>
									<th scope="row">업종</th>
									<td colspan="1"><c:out value="${basket.INDUTY_NAME }" /></td>
								</tr>
								<tr>
									<th scope="row" class="line">업종코드</th>
									<td colspan="1">
										<c:out value="${basket.INDUTY_CD}" />
									</td>
								</tr>
								<tr>
	
									<th scope="row" class="line">고용보험<br> 성립일자</th>
									<td>
										<c:out value="${fn:substring(basket.INSURANCE_DT,0,10)}" />
									</td>
									<th scope="row">기업유형</th>
									<td id="prisup_code"><c:out value="${basket.SCALE_CD }" /></td>
									<th scope="row">상시 근로자수</th>
									<td><c:out value="${basket.TOT_WORK_CNT}" /></td>
								</tr>
								<tr>
									<th scope="row" rowspan="2" class="bg01">기업 담당자</th>
									<th scope="row" rowspan="2">직위</th>
									<td rowspan="2">
										<input type="text" id="corp_pic_ofcps" name="corp_pic_ofcps" placeholder="입력해주세요." class="input-type01 w100"
										value="<c:out value='${corpPIC.CORP_PIC_OFCPS }' />" maxlength="100" />
									</td>
									<th scope="row" rowspan="2">성명</th>
									<td rowspan="2">
										<input type="text" id="corp_pic_name" name="corp_pic_name" placeholder="입력해주세요." class="input-type01 w100"
										value="<c:out value='${corpPIC.CORP_PIC_NAME }' />" maxlength="100" />
									</td>
									<th scope="row">연락처</th>
									<td>
										<input type="text" id="corp_pic_tel" name="corp_pic_tel" placeholder="입력해주세요." class="input-type01 w100"
										value="<c:out value='${corpPIC.CORP_PIC_TELNO }' />" maxlength="30" oninput="formatPhoneNumber(this);" />
									</td>
								</tr>
								<tr>
	
									<th scope="row" class="line">메일</th>
									<td>
										<input type="text" id="corp_pic_email" name="corp_pic_email" placeholder="입력해주세요." class="input-type01 w100"
										value="<c:out value='${corpPIC.CORP_PIC_EMAIL }' />" maxlength="60" />
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
									<th scope="row" rowspan="4">훈련<br>실시<br>이력</th>
									<th scope="col" class="bg02">연도</th>
									<th scope="col" class="bg02">참여사업</th>
									<th scope="col" class="bg02">훈련과정명</th>
									<th scope="col" class="bg02">훈련방법</th>
									<th scope="col" class="bg02">훈련기간(일)</th>
								</tr>
								<c:choose>
									<c:when test="${empty trainHis}">
										<c:forEach begin="0"  end="2" step="1">
											<tr>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:forEach items="${trainHis}" var="item" varStatus="status" begin="0"  end="2" step="1">
											<tr>
												<td class="line"><c:out value="${empty item.YEAR ? '-' : item.YEAR }" /></td>
												<td><c:out value="${empty item.PROGRAM ? '-' : item.PROGRAM}" /></td>
												<td><c:out value="${empty item.TRPR_NM ? '-' : item.TRPR_NM}" /></td>
												<td><c:out value="${empty item.TR_METH_CD ? '-' : item.TR_METH_CD}" /></td>
												<td><c:out value="${empty item.DAY ? '-' : item.DAY }" /></td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
								<tr>
									<th scope="row" rowspan="5" class="line">훈련<br>지원<br>이력</th>
									<th scope="col" class="bg02">연도</th>
									<th scope="col" class="bg02" colspan="2">연간 정부지원 한도금액(원) (A) <span class="update-max" id="update-max">금액 변경</span></th>
									<th scope="col" class="bg02">지원받은 금액(원) (B)</th>
									<th scope="col" class="bg02">비율(B/A)</th>
								</tr>
								<c:forEach items="${moneyHis }" var="item"
									varStatus="status" end="2">
									<tr>
										<td class="line"><c:out value="${item.YEAR }" /></td>
										<td class="right" colspan="2">
											<!--<fmt:formatNumber value="${item.MAX_PAY }" pattern="#,###,###,###,###"/> -->
 											<input class="input-type01 w100" style="text-align: right; padding-right: 12px" type="text" id="numberDisplay" onkeyup="addComma(this, ${status.index})" value="<fmt:formatNumber value="${item.MAX_PAY }" pattern="#,###,###,###,###"/>" maxlength="19" /> 
 											<input class="input-type01 w100" type="hidden" id="actualNumber${status.index }" name="actualNumber_${item.YEAR }" value="${item.MAX_PAY }" />
										</td>
										<td class="right">
											<span id="tot-pay${status.index }"><fmt:formatNumber value="${item.TOT_PAY }" pattern="#,###,###,###,###"/></span>
										</td>
										<td><span id="percent${status.index }"><c:out value="${item.PERCENT }"/></span>%</td>
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
										<div id="exec" style="width: auto; height: 300px;" alt="최근 직업훈련 실시 현황"></div>
									</td>
									<td>
										<div id="sprt" style="width: auto; height: 300px" alt="최근 직업훈련 지원 현황"></div>
									</td>
								</tr>
	
							</tbody>
	
						</table>
					</div>
				</div>
	
			</div>
	
	
			<div class="contents-area">
				<h3 class="title-type01 ml0">기업HRD이음컨설팅 결과
					<c:if test="${basket.STATUS eq 0}">
						<button type="button" class="fr btn-m01 btn-color01" onclick="callAiRecommend()">AI추천 불러오기</button>
					</c:if>
				</h3>
	
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
											<span id="name0" class="point-color01 name"></span>
											<button type="button" id="0" class="btn-m03 btn-color03 open-modal01">수정</button>
										</div>
									</th>
									<th scope="col">
										<div class="title-wrapper02">
											<span id="name1" class="point-color01 name"></span>
											<button type="button" id="1" class="btn-m03 btn-color03 open-modal01">수정</button>
										</div>
									</th>
									<th scope="col">
										<div class="title-wrapper02">
											<span id="name2" class="point-color01 name"></span>
											<button type="button" id="2" class="btn-m03 btn-color03 open-modal01">수정</button>
										</div>
									</th>
								</tr>
								<tr>
									<td class="line left"><span id="desc0" class="desc">${item.DESCRIPTION }</span></td>
									<td class="left"><span id="desc1" class="desc">${item.DESCRIPTION }</span></td>
									<td class="left"><span id="desc2" class="desc">${item.DESCRIPTION }</span></td>
								</tr>
								<tr>
									<td class="line left"><span id="cons0" class="cons">${item.CONSIDERATION }</span></td>
									<td class="left"><span id="cons1" class="cons">${item.CONSIDERATION }</span></td>
									<td class="left"><span id="cons2" class="cons">${item.CONSIDERATION }</span></td>
								</tr>
								<tr>
									<td>
										<button type="button" class="btn-m02 btn-color03 btn-detail" data-idx="0">
											<span>사업 상세 보기</span>
										</button>
									</td>
									<td>
										<button type="button" class="btn-m02 btn-color03 btn-detail" data-idx="1">
											<span>사업 상세 보기</span>
										</button>
									</td>
									<td>
										<button type="button" class="btn-m02 btn-color03 btn-detail" data-idx="2">
											<span>사업 상세 보기</span>
										</button>
									</td>
								</tr>
								<tr>
									<th scope="row" rowspan="2">HRD 제안<br />(적합 훈련 및 과정제안)</th>
									<td class="v-align-top">
										<ul id="train0" class="train left">
										</ul>
										<button type="button" id="0" class="btn-m02 btn-color02 open-modal02" style="min-width: 0">+ 훈련추천과정 추가</button>
										<div id="ext-contents-0">
											<a href="https://www.hrd.go.kr" class="point-color01" >
												www.hrd.go.kr
												<img class="btn-linked" src="${contextPath}${imgPath}/icon/icon_search04.png">
											</a>
											<p>HRD-NET 직업훈련지식포털에서 정부지원 훈련과정(기관)을 검색하세요.<br>(메뉴안내 : 훈련과정 → 기업훈련 과정 → 사업주훈련지원)</p>
										</div>
									</td>
									<td class="v-align-top">
										<ul id="train1" class="train left">
										</ul>
										<button type="button" id="1" class="btn-m02 btn-color02 open-modal02" style="min-width: 0">+ 훈련추천과정 추가</button>
										<div id="ext-contents-1">
											<a href="https://www.hrd.go.kr" class="point-color01" >
												www.hrd.go.kr
												<img class="btn-linked" src="${contextPath}${imgPath}/icon/icon_search04.png">
											</a>
											<p>HRD-NET 직업훈련지식포털에서 정부지원 훈련과정(기관)을 검색하세요.<br>(메뉴안내 : 훈련과정 → 기업훈련 과정 → 사업주훈련지원)</p>
										</div>
									</td>
									<td class="v-align-top">
										<ul id="train2" class="train left">
										</ul>
										<button type="button" id="2" class="btn-m02 btn-color02 open-modal02" style="min-width: 0">+ 훈련추천과정 추가</button>
										<div id="ext-contents-2">
											<a href="https://www.hrd.go.kr" class="point-color01" >
												www.hrd.go.kr
												<img class="btn-linked" src="${contextPath}${imgPath}/icon/icon_search04.png">
											</a>
											<p>HRD-NET 직업훈련지식포털에서 정부지원 훈련과정(기관)을 검색하세요.<br>(메뉴안내 : 훈련과정 → 기업훈련 과정 → 사업주훈련지원)</p>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="comment-wrapper">
											<textarea id="sgst0" name="" data-name="추천 1순위 HRD제안 입력칸" cols="50" rows="5" placeholder="추가 입력" maxlength="1000"></textarea>
										</div>
									</td>
									<td>
										<div class="comment-wrapper">
											<textarea id="sgst1" name="" data-name="추천 2순위 HRD제안 입력칸" cols="50" rows="5" placeholder="추가 입력" maxlength="1000"></textarea>
										</div>
									</td>
									<td>
										<div class="comment-wrapper">
											<textarea id="sgst2" name="" data-name="추천 3순위 HRD제안 입력칸" cols="50" rows="5" placeholder="추가 입력" maxlength="1000"></textarea>
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row">향후지원절차</th>
									<td class="left">
										<textarea id="proc0" name="" data-name="추천 1순위 향후지원절차 입력칸" cols="50" rows="5" placeholder="입력해주세요." maxlength="1000"></textarea>
									</td>
									<td class="left">
										<textarea id="proc1" name="" data-name="추천 2순위 향후지원절차 입력칸" cols="50" rows="5" placeholder="입력해주세요." maxlength="1000"></textarea>
									</td>
									<td class="left">
										<textarea id="proc2" name="" data-name="추천 3순위 향후지원절차 입력칸" cols="50" rows="5" placeholder="입력해주세요." maxlength="1000"></textarea>
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
									<td>
										<input type="hidden" id="instt_idx" name="instt_idx" value="<c:out value='${basket.INSTT_IDX}'/>" />
										<input type="text" id="instt_name" name="instt_name" placeholder="입력해주세요." class="input-type01 w100 btn-change"
										value="<c:out value='${doc.INSTT_NAME }' />" readonly maxlength="100" />
									</td>
									<th scope="row">직위</th>
									<td>
										<input type="text" id="doctor_ofcps" name="doctor_ofcps" placeholder="입력해주세요." class="input-type01 w100"
										value="<c:out value='${doc.DOCTOR_OFCPS }' />" maxlength="100" />
									</td>
									<th scope="row">연락처</th>
									<td>
										<input type="text" id="doctor_telno" name="doctor_telno" placeholder="입력해주세요." class="input-type01 w100"
										value="<c:out value='${doc.DOCTOR_TELNO }' />" maxlength="30" oninput="formatPhoneNumber(this);" />
									</td>
								</tr>
								<tr>
	
									<th scope="row" class="line">부서명</th>
									<td>
										<input type="text" id="doctor_dept_name" name="doctor_dept_name" placeholder="입력해주세요." class="input-type01 w100"
										value="<c:out value='${doc.DOCTOR_DEPT_NAME }' />" readonly maxlength="100" />
									</td>
									<th scope="row">성명</th>
									<td>
										<input type="text" id="doctor_name" name="doctor_name" placeholder="입력해주세요." class="input-type01 w100"
										value="<c:out value='${doc.DOCTOR_NAME }' />" maxlength="100" />
									</td>
									<th scope="row">메일</th>
									<td>
										<input type="text" id="doctor_email" name="doctor_email" placeholder="입력해주세요." class="input-type01 w100"
										value="<c:out value='${doc.DOCTOR_EMAIL }' />" maxlength="60" />
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
							</colgroup>
							<tbody>
								<tr>
									<th scope="row">기타 의견</th>
									<td class="left">
										<textarea id="etc_opinion" name="etc_opinion" cols="50" rows="5" placeholder="입력해주세요." maxlength="2000"><c:out value='${doc.DOCTOR_ETC_OPINION }' /></textarea>
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
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" class="bg01">보고서 전달방식</th>
									<td class="left">
										<select class="w30" name="ftf_yn" id="ftf_yn">
											<option value="" <c:if test="${empty basket.FTF_YN or basket.FTF_YN eq ''}">selected</c:if>>선택</option>
											<option value="Y" <c:if test="${basket.FTF_YN eq 'Y'}">selected</c:if>>대면</option>
											<option value="N" <c:if test="${basket.FTF_YN eq 'N'}">selected</c:if>>비대면</option>
										</select>
										<c:if test="${basket.STATUS eq 1}">
											<button type="button" class="btn-m01 btn-color02" onclick="saveFtfYn();">저장</button>
										</c:if>
										<span class="pl10">※ 보고서 전달 방식은 기업에게 노출되지 않습니다.</span>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
                                        
				<div class="btns-area">
					<div class="btns-left">
						<button type="button" class="btn-m01 btn-color02 depth3" id="btn-qustnr" data-rslt="${RSLT_IDX}" data-bsc="${BSC_IDX}">설문결과</button>
					</div>
					<div class="btns-right">
						<c:if test="${basket.STATUS eq 0}">
							<button type="button" class="btn-m01 btn-color01 depth3 btn-change">담당자 변경</button>
							<button type="button" class="btn-m01 btn-color01 depth3" onclick="saveConsult('temp')">임시저장</button>
							<button type="button" class="btn-m01 btn-color03 depth3" onclick="saveConsult('done')">완료</button>
						</c:if>
						<a href="<c:out value="${URL_CONSULTINGCLIPREPORT}&bsiscnslIdx=${BSISCNSL_IDX}"/>" target="_blank">
							<button type="button" class="btn-m01 btn-color02 depth3" id="btn-print">출력</button>
						</a>
						<!-- <button type="button" class="btn-m01 btn-color03 depth3" >리포트 전송</button> -->
						<a href="<c:out value="${URL_LIST}"/>">
							<button type="button" class="btn-m01 btn-color01 depth3">목록</button>
						</a>
					</div>
				</div>
			</div>
			<input type="hidden" name="recommends" id="recommends" value="" />
			<input type="hidden" name="bpl_no" id="bpl_no" value='<c:out value="${basket.BPL_NO }" />' />
			<input type="hidden" name="rslt_idx" id="rslt_idx" value='<c:out value="${RSLT_IDX }" />' />
			<input type="hidden" name="instt_idx" id="instt_idx" value='<c:out value="${basket.INSTT_IDX }" />' />
			<input type="hidden" name="bsis_idx" id="bsis_idx" value='<c:out value="${BSISCNSL_IDX }" />' />
			<input type="hidden" name="bsis_status" id="bsis_status" value='<c:out value="${basket.STATUS}" />' />
		</form>
		<!-- //CMS 끝 -->
	
	</div>
	<!-- //wrapper -->
	<form id="save"></form>
	<!-- 모달 창 -->
	<div class="mask"></div>
	<div class="modal-wrapper" id="modal-action01">
		<h2>훈련사업 및 과정</h2>
		<div class="modal-area">
			<div class="contents-box pl0">
				<form id="formNCS">
				<div class="basic-search-wrapper">
					<div class="one-box">
						<dl>
							<dt>
								<label for="modal-textfield00"> 사업구분 </label>
							</dt>
							<dd>
								<select id="modal-textfield00" class="select-type02">
									<option value="">선택</option>
								</select>
								<input type="hidden" id="prtbiz" name="prtbiz" value>
							</dd>
						</dl>
					</div>
					<div class="one-box">
                            <dl>
                                <dt>
                                <label for="modal-textfield01">
                                    NCS 대분류
                                </label>
                            </dt>
                                <dd>
                                    <select id="modal-textfield01" name="lclas" class="select-type02">
                                        <option value="">
                                            선택
                                        </option>
                                    </select>
                                </dd>
                            </dl>
                        </div>
                        <div class="one-box">
                            <dl>
                                <dt>
                                <label for="modal-textfield02">
                                    NCS 중분류
                                </label>
                            </dt>
                                <dd>
                                    <select id="modal-textfield02" name="mclas" class="select-type02">
                                        <option value="">
                                            선택
                                        </option>
                                    </select>
                                </dd>
                            </dl>
                        </div>
                        <div class="one-box">
                            <dl>
                                <dt>
                                <label for="modal-textfield03">
                                    NCS 소분류
                                </label>
                            </dt>
                                <dd>
                                    <select id="modal-textfield03" name="sclas" class="select-type02">
                                        <option value="">
                                            선택
                                        </option>
                                    </select>
                                </dd>
                            </dl>
                        </div>
                        <div class="one-box">
                            <dl>
                                <dt>
                                <label for="modal-textfield04">
                                    훈련과정검색
                                </label>
                            </dt>
                                <dd>
                                    <input type="text" id="modal-textfield04" name="name" value="" title="훈련과정검색 입력" placeholder="키워드 입력" />
                                </dd>
                            </dl>
                        </div>
                    </div>
                    </form>
                    <div class="btns-area">
                        <button type="button" class="btn-m02 btn-color02 round01" onclick="tr_search()">검색</button>
                    </div>
                </div>

                <div class="contents-box pl0">
                    <p class="total mb05">
                        총 <strong id="tr-search-number">0</strong> 건
                    </p>

                    <div class="table-type02 horizontal-scroll table-container">
                        <table class="width-type03 modal-table">
                            <caption>
                                훈련사업 및 과정표 : 훈련과정(과정명 및 제공기관, 적용업종), 시간, 선택에 관한 정보 제공표
                            </caption>
                            <colgroup>
                                <col style="width: 20%" />
                                <col style="width: 50%" />
                                <col style="width: 20%" />
                                <col style="width: 10%" />
                            </colgroup>
                            <thead class="modal-thead">
                                <tr>
                                    <th scope="col" colspan="2">
                                        훈련과정
                                    </th>
                                    <th scope="col">
                                        시간
                                    </th>
                                    <th scope="col">
                                        선택
                                    </th>
                                </tr>
                            </thead>
                            <tbody id="trbody"></tbody>
                        </table>

                    </div>

                </div>


                <div class="btns-area">
                    <button type="button" class="btn-m01 btn-color03" onclick="setTrainList()">
                        등록
                    </button>


                    <button type="button" class="btn-m01 btn-color02" onclick="closeModal()">
                        취소
                    </button>
				</div>
			</div>
		<button type="button" class="btn-modal-close">모달 창 닫기</button>
	</div>
	<!-- //모달 창 -->
	
	<!-- 모달 창2 -->
	<div class="modal-wrapper" id="modal-action02">
		<h2>담당자 변경</h2>
		<div class="modal-area">
			<div class="contents-box pl0">
				<form id="doctor-search">
				<div class="basic-search-wrapper">
					<div class="one-box">
						<dl>
							<dt>
								<label for="modal-textfield00">지부지사</label>
							</dt>
							<dd>
								<select id="modal-textfield00" name="insttName" class="select-type02">
									<option value="">선택</option>
									<c:forEach items="${instt}" var="item">
									<option value="${item }">${item }</option>
									</c:forEach>
								</select>
							</dd>
						</dl>
					</div>
                    <div class="one-box">
                        <dl>
                            <dt>
                            <label for="modal-textfield04">
                               	이름
                            </label>
                        </dt>
                            <dd>
                                <input type="text" id="modal-textfield04" name="doctorName" value="" title="이름 입력" placeholder="이름 입력" />
                            </dd>
                        </dl>
                    </div>
                    </div>
                    </form>
                    <div class="btns-area">
                        <button type="button" class="btn-m02 btn-color02 round01" onclick="doctorSearch()">검색</button>
                    </div>
                </div>

                <div class="contents-box pl0">
                    <p class="total mb05">
                        총 <strong id="doctor-cnt">0</strong> 건
                    </p>

                    <div class="table-type02 horizontal-scroll">
                        <table class="width-type03">
                            <caption>
                            	주치의에 해당하는 회원의 이름, 지부지사에 해당하는 정보 제공표
                            </caption>
                            <colgroup>
                                <col style="width: 20%" />
                                <col style="width: 50%" />
                                <col style="width: 10%" />
                                <col style="width: *" />
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col" colspan="3">
                                    	주치의 이름 및 지부지사
                                    </th>
                                    <th scope="col">
                                    	선택
                                    </th>
                                </tr>
                            </thead>
                            <tbody id="docbody"></tbody>
                        </table>
                    </div>
                </div>
			</div>
		<button type="button" class="btn-modal-close">모달 창 닫기</button>
	</div>
	<!-- //모달 창2 -->
	
	<form action="" method="post" style="display: none;" id="form-box">
		<input type="hidden" name="rslt" id="rslt" value="" />
		<input type="hidden" name="bsc" id="bsc" value="" />
	</form>
	
	<c:if test="${not empty isRecommended and isRecommended eq false}">
		<script>
			alert('AI추천 결과가 없습니다. 추천훈련사업 우측상단의 [AI추천 불러오기] 버튼을 눌러 재시도 하거나 직접 입력해주세요.');
		</script>
	</c:if>
	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>