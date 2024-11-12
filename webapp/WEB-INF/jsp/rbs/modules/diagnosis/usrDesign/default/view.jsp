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
<c:set var="exceptIdStr">제외할 항목id를 구분자(,)로 구분하여 입력(예:name,notice,subject,file,contents,listImg)</c:set>
<c:set var="exceptIds" value="${fn:split(exceptIdStr,',')}"/>
<c:set var="summary"><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/>을 제공하는 표</c:set>
<head>
        <title>
            기초진단관리 &lt; 기초진단 &lt; 기업진단 &lt; 기업직업훈련 지원시스템
        </title>
        <style>
	 		.redgrad { background: rgb(131,58,180); background: linear-gradient(90deg,rgba(131,58,180,1) 0%,rgba(253,29,29,1) 50%,rgba(252,176,69,1) 100%); -webkit-background-clip: text; color: transparent; line-height: 1.2; padding: 5px 0; }
		</style>
</head>
<body>

    <!-- skip navigation -->
    <p class="skip-navigation">
        <a href="#contents">본문 바로가기</a>
    </p>
    <!-- //skip navigation -->

            <section>
                        <div class="container-wrapper">
                            <div class="lnb-wrapper">
                                <div class="lnb-area">
                                    <jsp:include page="/pub/include/lnb02.html"/>
                                </div>
                            </div>
                            <!-- contents  -->
                            <article>
                                <div class="contents" id="contents">
                   					<input type="hidden" class="siteId" name="siteId" value="${siteId }" />
                                    <div class="contents-wrapper">
                                        <!-- CMS 시작 -->
										<c:set var="listBsc" value="${bsc }"/>
                                        <div class="contents-area">
                                            <h3 class="title-type01 ml0">
                                                HRD 기초진단지
                                            </h3>
                                            <div class="title-wrapper clear">
                                                <p class="word-issue fl">
                                                    (발급일자) <fmt:formatDate pattern="yyyy-MM-dd" value="${listBsc.ISSUE_DATE}"/>
                                                </p>

                                                <p class="word-issue fr">
                                                    (발급번호) <c:out value="${listBsc.ISSUE_NO}"/>
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
                                                                <th scope="row" rowspan="4" class="bg01">
                                                                    기업개요
                                                                </th>
                                                                <th scope="row">
                                                                    기업명
                                                                </th>
                                                                <td colspan="3"><c:out value="${listBsc.CORP_NAME}"/></td>
                                                                <th scope="row">
                                                                    사업장 관리번호
                                                                </th>
                                                                <td colspan="1"><c:out value="${listBsc.BPL_NO}"/></td>
                                                            </tr>
                                                            <tr>

                                                                <th scope="row" rowspan="2" class="line">
                                                                    소재지
                                                                </th>
                                                                <td colspan="3" rowspan="2"><c:out value="${listBsc.CORP_LOCATION}"/></td>
                                                                <th scope="row">
                                                                    업종
                                                                </th>
                                                                <td colspan="1"><c:out value="${listBsc.INDUTY_NAME}"/></td>
                                                            </tr>
                                                            <tr>
                                                                <th scope="row" class="line">
                                                                    업종코드
                                                                </th>
                                                                <td colspan="1"><c:out value="${listBsc.INDUTY_CD}"/></td>
                                                            </tr>
                                                            <tr>

                                                                <th scope="row" class="line">
                                                                    고용보험<br> 성립일자
                                                                </th>
                                                                <td>
                                                                	<fmt:parseDate var="empins_date" value="${listBsc.EMPINS_START_DATE}" pattern="yyyyMMdd"/>
                                                                	<fmt:formatDate value="${empins_date}" pattern="yyyy-MM-dd"/>
                                                               	</td>
                                                                <th scope="row">
                                                                    기업유형
                                                                </th>
                                                                <td><c:out value="${listBsc.SCALE_CD}"/></td>
                                                                <th scope="row">
                                                                    상시 근로자수
                                                                </th>
                                                                <td><c:out value="${listBsc.TOT_WORK_CNT}"/></td>
                                                            </tr>
                                                            <tr>
                                                                <th scope="row" rowspan="2" class="bg01">
                                                                    기업 담당자
                                                                </th>
                                                                <th scope="row" rowspan="2">
                                                                    직위
                                                                </th>
                                                                <td rowspan="2"><c:out value="${empty listBsc.CORP_PIC_OFCPS? '-' : listBsc.CORP_PIC_OFCPS}"/></td>
                                                                <th scope="row" rowspan="2">
                                                                    성명
                                                                </th>
                                                                <td rowspan="2"><c:out value="${empty listBsc.CORP_PIC_NAME? '-' : listBsc.CORP_PIC_NAME}"/></td>
                                                                <th scope="row">
                                                                    연락처
                                                                </th>
                                                                <td><c:out value="${empty listBsc.CORP_PIC_TELNO? '-' : listBsc.CORP_PIC_TELNO}"/></td>
                                                            </tr>
                                                            <tr>

                                                                <th scope="row" class="line">
                                                                    메일
                                                                </th>
                                                                <td><c:out value="${empty listBsc.CORP_PIC_EMAIL? '-' : listBsc.CORP_PIC_EMAIL}"/></td>
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
                                                                <th scope="row" rowspan="9" class="bg01">
                                                                    기업훈련현황
                                                                </th>
                                                                <th scope="row" rowspan="4">
                                                                	훈련<br>실시<br>이력
                                                                </th>
                                                                <th scope="col" class="bg02">연도</th>
                                                                <th scope="col" class="bg02">참여사업</th>
                                                                <th scope="col" class="bg02">훈련과정명</th>
                                                                <th scope="col" class="bg02">훈련방법</th>
                                                                <th scope="col" class="bg02">훈련기간(일)</th>
                                                            </tr>
                                                            
                                                            <c:choose>
		                                                    	<c:when test="${empty trHis}">
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
		                                                            <c:forEach var="listTr" items="${trHis}" begin="0"  end="2" step="1">
		                                                            <tr>
		                                                            <c:set var="listNo"  value="${listTr.TR_HST_IDX+1}"/>
		                                                                <td class="line">${empty listTr.YEAR? '-' : listTr.YEAR}</td>
		                                                                <td><c:out value="${empty listTr.RCTBIZ_NAME? '-' : listTr.RCTBIZ_NAME}"/></td>
		                                                                <td><c:out value="${empty listTr.TP_NAME? '-' : listTr.TP_NAME}"/></td>
		                                                                <td><c:out value="${empty listTr.METHOD? '-' : listTr.METHOD}"/></td>
		                                                                <td><c:out value="${empty listTr.PERIOD? '-' : listTr.PERIOD}"/></td> 
		                                                            </tr>
		                                                            </c:forEach>
			                                                 	</c:otherwise>
		                                                    </c:choose>
                                                            
                                                            <tr>

                                                                <th scope="row" rowspan="5" class="line">
                                                                    훈련<br>지원<br>이력
                                                                </th>
                                                                <th scope="col" class="bg02">연도</th>
                                                                <th scope="col" class="bg02" colspan="2">연간 정부지원 한도금액(원) (A)</th>
                                                                <th scope="col" class="bg02">지원받은 금액(원) (B)</th>
                                                                <th scope="col" class="bg02">비율(B/A)</th>
                                                            </tr>
                                                            <c:forEach var="listFund" items="${fundHis}" begin="0" end="2" step="1">
                                                            <c:set var="SPT_PAY" value="${listFund.SPT_PAY}"/>
                                                            <c:set var="FUND_LIMIT" value="${listFund.FUND_LIMIT}"/>
                                                            <tr>
                                                                <td class="line">${listFund.YEAR}</td>
                                                                <td class="right" colspan="2"><fmt:formatNumber value="${FUND_LIMIT}" pattern="#,###"/></td>
                                                                <td class="right"><fmt:formatNumber value="${SPT_PAY}" pattern="#,###"/></td>
<%--                                                                 <td class="right"><fmt:formatNumber type="percent" value="${(SPT_PAY div FUND_LIMIT)}" pattern="0.0%"/></td> --%>
                                                                <td><c:out value="${listFund.PERCENT }"/>%</td>
                                                            </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>


                                            <div class="contents-box pl0">
                                                <div class="table-type02 horizontal-scroll">
                                                    <table class="width-type02">
                                                        <caption>

                                                        </caption>
                                                        <colgroup>
                                                            <col width="50%">
                                                            <col width="50%">
                                                        </colgroup>
                                                        <thead>
                                                            <tr>
                                                                <th scope="col" colspan="2">
                                                                    최근 훈련 동향
                                                                </th>

                                                            </tr>
                                                            <tr>
                                                                <th scope="col">
                                                                    최근 직업훈련 실시 현황
                                                                </th>
                                                                <th scope="col">
                                                                    최근 직업훈련 지원 현황
                                                                </th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <tr>
                                                                <td>
                                                                    <div id="exec" style="width:auto;height:300px" alt="최근 직업훈련 실시 현황">
                                                                </td>
                                                                <td> 
                                                                	<div id="sprt" style="width:auto;height:300px" alt="최근 직업훈련 지원 현황">
                                                                </td>
                                                            </tr>

                                                        </tbody>

                                                    </table>
                                                </div>
                                            </div>

                                        </div>


                                        <div class="contents-area">
                                            <h3 class="title-type01 ml0">
                                                HRD 기초진단 결과
                                            </h3>

                                            <div class="contents-box pl0">
                                                <div class="table-type02 horizontal-scroll">
                                                    <table class="width-type02">
                                                        <caption>

                                                        </caption>
                                                        <colgroup>
                                                            <col width="15%">
                                                            <col width="">
                                                            <col width="">
                                                            <col width="">
                                                        </colgroup>
                                                        <tbody>
                                                            <tr>
                                                                <th scope="row" rowspan="3">참여가능사업</th>
                                                                <c:forEach var="listPBiz" items="${prtbiz}" begin="0"  end="2" step="1">
                                                                	<th scope="col"><c:out value="${listPBiz.PRTBIZ_NAME}"/></th>
                                                                </c:forEach>
                                                            </tr>
                                                            <tr>
                                                            <c:forEach var="listPBiz" items="${prtbiz}" begin="0"  end="2" step="1">
                                                                <td class="line left"><c:out value="${listPBiz.DC}" escapeXml="false"/></td>
                                                            </c:forEach>
                                                            </tr>
                                                            <tr>
															<c:forEach var="listPBiz" items="${prtbiz}" begin="0"  end="2" step="1">
                                                                <td class="line left">
                                                                	<c:out value="${listPBiz.CONSIDER}" escapeXml="false"/>
                                                                	<c:if test="${listPBiz.PRTBIZ_NAME eq '기업 자체 훈련 [체계적 현장훈련(S-OJT)]' }">
                                                        				<p style="text-align:center;margin: 4px 0px;"><a href="${contextPath }/web/sojt/init.do?mId=110" class="redgrad">체계적 현장훈련 신청! [CLICK]</a></p>
                                                        			</c:if>
                                                                </td>
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
                                                        <caption>

                                                        </caption>
                                                        <colgroup>
                                                            <col width="15%">
                                                            <col width="">
                                                            <col width="">
                                                            <col width="">
                                                            <col width="">
                                                        </colgroup>
                                                        <thead>
                                                            <tr>
                                                                <th scope="col" colspan="5" class="bg02">
                                                                    HRD4U와 HRD-Net을 가입하면 더욱 다양한 직업능력개발훈련 콘텐츠를 만나보실 수 있습니다!
                                                                </th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <tr>
                                                                <th scope="row" rowspan="2">관련 정보 확인<br> (QR코드)
                                                                </th>
                                                                <c:forEach items="${qrs }" var="item" end="3">
                                                                	<td><a href="${item.url }" target="_blank"><img src="${item.image }" alt="${item.title } QR코드 - 새창열림" class="qrcode"></a></td>
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
                                                        <caption>

                                                        </caption>
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
                                                                <td><c:out value="${listBsc.INSTT_NAME}"/></td>
                                                                <th scope="row">직위</th>
                                                                <td><c:out value="${listBsc.DOCTOR_OFCPS}"/></td>
                                                                <th scope="row">연락처</th>
                                                                <td><c:out value="${listBsc.DOCTOR_TELNO}"/></td>
                                                            </tr>
                                                            <tr>

                                                                <th scope="row" class="line">부서명</th>
                                                                <td><c:out value="${listBsc.DOCTOR_DEPT_NAME}"/></td>
                                                                <th scope="row">성명</th>
                                                                <td><c:out value="${listBsc.DOCTOR_NAME}"/></td>
                                                                <th scope="row">메일</th>
                                                                <td><c:out value="${listBsc.DOCTOR_EMAIL}"/></td>
                                                            </tr>
                                                        </tbody>

                                                    </table>
                                                </div>
                                            </div>


                                            <div class="btns-area">
                                               	<button type="button" class="btn-b01 round01 btn-color02 left" onclick="window.history.back();">
                                                   	<span>
                                                       	목록
                                                   	</span>
                                                   	<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01" />
                                               	</button>
                                            </div>
                                        </div>
                                        <div class="contents-area">
		                                    <div class="btns-area">
		                                        <div class="btns-right right">
		                                        	<span style="cursor:pointer" class="word-linked-notice01" onclick="gohope()">
		                                                희망 사업이 없다면?
		                                                <img src="${contextPath}${imgPath}/icon/icon_arrow_right04.png" alt="" />
		                                            </span>
		                                            <form id="hope" method="post" action="hope.do?mId=54">
		                                            	<input type="hidden" class="bpl_no" name="bpl_no" value="${listBsc.BPL_NO}" >
		                                        	</form>
		                                        </div>
		                                    </div>
		                                </div>
                                        <!-- //CMS 끝 -->

                                    </div>
                                </div>
                            </article>
                        </div>
            </section>
            
</body>

<script src="${contextPath}/assets/js/echarts.js"></script>
<script src="${contextPath}/web/js/diagnosis/view.js"></script>

<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>