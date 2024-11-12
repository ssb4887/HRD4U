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
<script type="text/javascript"  src="${pageContext.request.contextPath }/assets/js/echarts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }<c:out value="${jsPath}/bsisCnsl/cnslt.js"/>"></script>

	<%-- <a href="<c:out value="${URL_CLIPREPORT}&bscIdx=80"/>" target="_blank">출력하기</a> --%>
	
	<div class="contents-wrapper">

		<!-- CMS 시작 -->
		<form action="${contextPath}/web/recommend/saveConsult.do?mId=56" id="consult">
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
										<c:out value="${basket.INDUTY_CD }" />
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
										<c:out value='${corpPIC.CORP_PIC_OFCPS }'/>
									</td>
									<th scope="row" rowspan="2">성명</th>
									<td rowspan="2">
										<c:out value='${corpPIC.CORP_PIC_NAME }'/>
									</td>
									<th scope="row">연락처</th>
									<td>
										<c:out value='${corpPIC.CORP_PIC_TELNO }' />
									</td>
								</tr>
								<tr>
	
									<th scope="row" class="line">메일</th>
									<td>
										<c:out value='${corpPIC.CORP_PIC_EMAIL }' />
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
										<c:forEach items="${trainHis}" var="item" varStatus="status">
											<tr>
												<td class="line"><c:out value="${empty item.YEAR ? '-' : item.YEAR }" /></td>
												<td><c:out value="${item.PROGRAM }" /></td>
												<td><c:out value="${item.TRPR_NM }" /></td>
												<td><c:out value="${item.TR_METH_CD }" /></td>
												<td><c:out value="${empty item.DAY ? '-' : item.DAY }" /></td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>

								<tr>
									<th scope="row" rowspan="5" class="line">훈련<br>지원<br>이력</th>
									<th scope="col" class="bg02">연도</th>
									<th scope="col" class="bg02" colspan="2">연간 정부지원 한도금액(원) (A)</th>
									<th scope="col" class="bg02">지원받은 금액(원) (B)</th>
									<th scope="col" class="bg02">비율(B/A)</th>
								</tr>
								<c:forEach items="${moneyHis }" var="item"
									varStatus="status" end="2">
									<tr>
										<td class="line"><c:out value="${item.YEAR }" /></td>
										<td class="right" colspan="2">
											<fmt:formatNumber value="${item.MAX_PAY }" pattern="#,###,###,###,###"/>	
										</td>
										<td class="right">
											<fmt:formatNumber value="${item.TOT_PAY }" pattern="#,###,###,###,###"/>
										</td>
										<td>
											<span id="percent${status.index }"><c:out value="${item.PERCENT }"/></span>%
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
										<span id="name0" class="point-color01 name"></span>
<!-- 											<button type="button" id="0" class="btn-m03 btn-color03 open-modal01">수정</button> -->
									</th>
									<th scope="col">
										<span id="name1" class="point-color01 name"></span>
<!-- 											<button type="button" id="1" class="btn-m03 btn-color03 open-modal01">수정</button> -->
									</th>
									<th scope="col">
										<span id="name2" class="point-color01 name"></span>
<!-- 											<button type="button" id="2" class="btn-m03 btn-color03 open-modal01">수정</button> -->
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
									<td class="left v-align-top">
										<ul id="train0" class="train">
										</ul>
										<div class="center" id="ext-contents-0" style="display:none;">
											<a href="https://www.hrd.go.kr" class="point-color01" >
												www.hrd.go.kr
												<img class="btn-linked" src="${contextPath}${imgPath}/icon/icon_search04.png">
											</a>
											<p>HRD-NET 직업훈련지식포털에서 <br/>정부지원 훈련과정(기관)을 검색하세요.<br>(메뉴안내 : 훈련과정 → 기업훈련 과정 → 사업주훈련지원)</p>
										</div>
									</td>
									<td class="left v-align-top">
										<ul id="train1" class="train">
										</ul>
										<div class="center" id="ext-contents-1" style="display:none;">
											<a href="https://www.hrd.go.kr" class="point-color01" >
												www.hrd.go.kr
												<img class="btn-linked" src="${contextPath}${imgPath}/icon/icon_search04.png">
											</a>
											<p>HRD-NET 직업훈련지식포털에서 <br/>정부지원 훈련과정(기관)을 검색하세요.<br>(메뉴안내 : 훈련과정 → 기업훈련 과정 → 사업주훈련지원)</p>
										</div>
									</td>
									<td class="left v-align-top">
										<ul id="train2" class="train">
										</ul>
										<div class="center" id="ext-contents-2" style="display:none;">
											<a href="https://www.hrd.go.kr" class="point-color01" >
												www.hrd.go.kr
												<img class="btn-linked" src="${contextPath}${imgPath}/icon/icon_search04.png">
											</a>
											<p>HRD-NET 직업훈련지식포털에서 <br/>정부지원 훈련과정(기관)을 검색하세요.<br>(메뉴안내 : 훈련과정 → 기업훈련 과정 → 사업주훈련지원)</p>
										</div>
									</td>
								</tr>
								<tr>
									<td class="left">
										<span id="sgst0"></span>
									</td>
									<td class="left">
										<span id="sgst1"></span>
									</td>
									<td class="left">
										<span id="sgst2"></span>
									</td>
								</tr>
								<tr>
									<th scope="row">향후지원절차</th>
									<td class="left">
										<pre id="proc0"></pre>
									</td>
									<td class="left">
										<pre id="proc1"></pre>
									</td>
									<td class="left">
										<pre id="proc2"></pre>
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
										<c:out value='${doc.INSTT_NAME }' />
									</td>
									<th scope="row">직위</th>
									<td>
										<c:out value='${doc.DOCTOR_OFCPS }' />
									</td>
									<th scope="row">연락처</th>
									<td>
										<c:out value='${doc.DOCTOR_TELNO }' />
									</td>
								</tr>
								<tr>
	
									<th scope="row" class="line">부서명</th>
									<td>
										<c:out value='${doc.DOCTOR_DEPT_NAME }' />
									</td>
									<th scope="row">성명</th>
									<td>
										<c:out value='${doc.DOCTOR_NAME }' />
									</td>
									<th scope="row">메일</th>
									<td>
										<c:out value='${doc.DOCTOR_EMAIL }' />
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
										<c:out value='${doc.DOCTOR_ETC_OPINION }' />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
                                        
				<div class="btns-area">
					<a href="<c:out value="${URL_LIST}"/>">
						<button type="button" class="btn-b01 round01 btn-color02 left">
							<span>목록</span>
							<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01"/>
						</button>
					</a>
					
					<a href="<c:out value="${URL_CONSULTINGCLIPREPORT}&bsiscnslIdx=${BSISCNSL_IDX}"/>" target="_blank">
						<button type="button" class="btn-b01 round01 btn-color03 left">
							<span>출력하기</span>
							<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01"/>
						</button>
					</a>
				</div>
				
			</div>
			<input type="hidden" name="recommends" id="recommends" value="" />
			<input type="hidden" name="bpl_no" id="bpl_no" value='<c:out value="${basket.BPL_NO }" />' />
			<input type="hidden" name="rslt_idx" id="rslt_idx" value='<c:out value="${RSLT_IDX }" />' />
			<input type="hidden" name="instt_idx" id="instt_idx" value='<c:out value="${basket.INSTT_IDX }" />' />
			<input type="hidden" name="bsis_idx" id="bsis_idx" value='<c:out value="${BSISCNSL_IDX }" />' />
		</form>
		<!-- //CMS 끝 -->
	
	</div>
	<!-- //wrapper -->
	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>