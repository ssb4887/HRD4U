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
	<div class="board-view">
		<h3><itui:objectView itemId="title" itemInfo="${itemInfo}"/></h3>
		<%-- table summary, 항목출력에 사용 --%>
		<c:set var="exceptIdStr">제외할 항목id를 구분자(,)로 구분하여 입력(예:name,notice,subject,file,contents,listImg)</c:set>
		<c:set var="exceptIds" value="${fn:split(exceptIdStr,',')}"/>
		<%-- 
			table summary값 setting - 테이블 사용하지 않는 경우는 필요 없음
			디자인 문제로 제외한 항목(exceptIdStr에 추가했으나 table내에 추가되는 항목)은 수동으로 summary에 추가
			예시)
			<c:set var="summary"><itui:objectItemName itemInfo="${itemInfo}" itemId="subject"/>, <spring:message code="item.reginame1.name"/>, <spring:message code="item.regidate1.name"/>, <spring:message code="item.board.views.name"/>, <c:if test="${useFile}"><spring:message code="item.file.name"/>, </c:if><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/><spring:message code="item.contents.name"/>을 제공하는 표</c:set>
		--%>
		<c:set var="summary"><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/>을 제공하는 표</c:set>
		
		<div class="plan-information-wrapper">
			<!-- 환경분석 -->
	        <div class="contents-area">
	        
	        	<!-- 지역 내 주요 정책 분석  -->
	            <div class="contents-box pl0">
	                <h4 class="number-title-type01"><span class="number"> Ⅰ </span><strong> 환경분석</strong></h4>
	                <h5 class="title-type05">1. 지역 내 주요 정책 분석</h5>
	                <c:forEach var="listDt" items="${list }" varStatus="i">
	                <h6 class="title-type06"> ${listDt.INSTT_NAME } ‘${dt.YEAR }년 주요 시책</h6>
	                <div class="gray-box">
	                	<c:out value="${listDt.POLICI_CN }"/>
	                </div>
	                </c:forEach>
	            </div>
	            
	            <!-- 관할 내 기업현황 분석  -->
	            <div class="contents-box pl0">
	                <h5 class="title-type05">2. 관할 내 기업현황 분석</h5>
	                <c:forEach var="listDt" items="${list }" varStatus="i">
	                <h6 class="title-type06"> 기업분포 - ${listDt.INSTT_NAME }</h6>
	                <div class="gray-box">
		              	<c:out value="${listDt.CORP_DISTRB_CN }"/>
	                </div>
	                <h6 class="title-type06">기업직업훈련 현황 - ${listDt.INSTT_NAME }</h6>
	                <div class="gray-box">
		              	<c:out value="${listDt.TR_CN }"/>
	                </div>
	                </c:forEach>
	            </div>
	        </div>

			<!-- 환경분석 결과 시사점 도출 -->
            <div class="contents-area">
                <div class="contents-box pl0">
                    <h4 class="number-title-type01"><span class="number"> Ⅱ</span><strong> 환경분석 결과 시사점 도출</strong></h4>
                    <div class="gray-box">
                    	<itui:objectView itemId="implication" itemInfo="${itemInfo}"/>
                    </div>
                </div>
            </div>

			<!-- 핵심전략 -->

            <div class="contents-area">
                <div class="contents-box pl0">
                    <h4 class="number-title-type01"><span class="number">Ⅲ</span><strong>핵심전략</strong></h4>
                    <div class="table-type02 horizontal-scroll">
                        <table>
                            <caption>핵심전략표 : 번호, 바구니, 기업바구니, 조정, 전략명, 전략명에 관한 정보 제공표</caption>
                            <colgroup>
                                <col style="width: 8%" />
                                <col style="width: 15%" />
                                <col style="width: 10%" />
                                <col style="width: 10%" />
                                <col style="width: 30%" />
                                <col style="width: 17%" />
                            </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">연번</th>
                                    <th scope="col">바구니</th>
                                    <th scope="col">기업바구니</th>
                                    <th scope="col">조정</th>
                                    <th scope="col">전략 목표</th>
                                    <th scope="col">세부 목표</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td><span class="point-color01">일반홍보그룹</span></td>
                                    <td class="right"><fmt:formatNumber value="${bskList.LCLAS_CO_1  }" pattern="#,###,###,###,###"/></td>
                                    <td class="right">
                                    	<c:if test="${bskList.LCLAS_CO_1 == bskList.LCLAS_MDAT_CO_1}"><fmt:formatNumber value="${bskList.LCLAS_MDAT_CO_1 }" pattern="#,###,###,###,###"/></c:if>
                                    	<c:if test="${bskList.LCLAS_CO_1 != bskList.LCLAS_MDAT_CO_1}"><span class="point-color01"><fmt:formatNumber value="${bskList.LCLAS_MDAT_CO_1 }" pattern="#,###,###,###,###"/></span></c:if>
                                    </td>
                                    <td class="left"><c:out value="${ bskList.GOAL_CN_1}"/></td>
                                    <td class="left"><c:out value="${ bskList.DETAIL_CN_1}"/></td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td><span class="point-color01">적극발굴그룹</span></td>
                                    <td class="right"><fmt:formatNumber value="${bskList.LCLAS_CO_2  }" pattern="#,###,###,###,###"/></td>
                                    <td class="right">
                                    	<c:if test="${bskList.LCLAS_CO_2 == bskList.LCLAS_MDAT_CO_2}"><fmt:formatNumber value="${bskList.LCLAS_MDAT_CO_2 }" pattern="#,###,###,###,###"/></c:if>
                                    	<c:if test="${bskList.LCLAS_CO_2 != bskList.LCLAS_MDAT_CO_2}"><span class="point-color01"><fmt:formatNumber value="${bskList.LCLAS_MDAT_CO_2 }" pattern="#,###,###,###,###"/></span></c:if>
                                    </td>
                                    <td class="left"><c:out value="${ bskList.GOAL_CN_2}"/></td>
                                    <td class="left"><c:out value="${ bskList.DETAIL_CN_2}"/></td>
                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td><span class="point-color01">훈련확산그룹</span></td>
                                    <td class="right"><fmt:formatNumber value="${bskList.LCLAS_CO_3  }" pattern="#,###,###,###,###"/></td>
                                    <td class="right">
                                    	<c:if test="${bskList.LCLAS_CO_3 == bskList.LCLAS_MDAT_CO_3}"><fmt:formatNumber value="${bskList.LCLAS_MDAT_CO_3 }" pattern="#,###,###,###,###"/></c:if>
                                    	<c:if test="${bskList.LCLAS_CO_3 != bskList.LCLAS_MDAT_CO_3}"><span class="point-color01"><fmt:formatNumber value="${bskList.LCLAS_MDAT_CO_3 }" pattern="#,###,###,###,###"/></span></c:if>
                                    </td>
                                    <td class="left"><c:out value="${ bskList.GOAL_CN_3}"/></td>
                                    <td class="left"><c:out value="${ bskList.DETAIL_CN_3}"/></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

				<!-- 기업바구니 조정기준 -->
                <div class="contents-box pl0">
                    <h6 class="title-type06">기업바구니 조정기준</h6>
                    <div class="gray-box">
                       	<itui:objectView itemId="adjustment" itemInfo="${itemInfo}"/>
                    </div>
                </div>

				<!-- 지역 내 타깃 그룹 기업 현황 -->
                <div class="contents-box pl0">
                    <h6 class="title-type06">지역 내 타깃 그룹 기업 현황</h6>
                    <div class="gray-box">
                    	<itui:objectView itemId="groupCurrent" itemInfo="${itemInfo}"/>
                    </div>
                </div>
                
				<!-- 타깃 그룹별 접근 전략 -->
                <div class="contents-box pl0">
                    <h6 class="title-type06">타깃 그룹별 접근 전략</h6>
                    <div class="gray-box">
                    	<itui:objectView itemId="strategy" itemInfo="${itemInfo}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="btns-area">
        <div class="btns-right">
            <a href="<c:out value="${URL_LIST}"/>" class="btn-m01 btn-color01 depth4">목록</a>
        </div>
    </div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>