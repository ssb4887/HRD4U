<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_sampleInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/planWrite.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
	<div id="contents-wrapper">
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
		<input type="hidden" name="aractplnIdx" id="aractplnIdx" value="<c:out value="${dt.ARACTPLN_IDX}"/>"/>
		<legend class="blind">글쓰기</legend>
		<%-- table summary, 항목출력에 사용 --%>
		<c:set var="exceptIdStr">제외할 항목id를 구분자(,)로 구분하여 입력(예:name,notice,subject,file,contents,listImg)</c:set>
		<c:set var="exceptIds" value="${fn:split(exceptIdStr,',')}"/>
		<%-- 
			table summary값 setting - 테이블 사용하지 않는 경우는 필요 없음
			디자인 문제로 제외한 항목(exceptIdStr에 추가했으나 table내에 추가되는 항목)은 수동으로 summary에 추가
			예시)
			<c:set var="summary"><itui:objectItemName itemInfo="${itemInfo}" itemId="subject"/>, <spring:message code="item.reginame1.name"/>, <spring:message code="item.regidate1.name"/>, <spring:message code="item.board.views.name"/>, <c:if test="${useFile}"><spring:message code="item.file.name"/>, </c:if><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/><spring:message code="item.contents.name"/>을 제공하는 표</c:set>
		--%>
		<c:set var="summary"><itui:tableSummary items="${items}" itemOrder="${itemOrder}" exceptIds="${exceptIds}"/> 입력표</c:set>
		
		
		<div class="board-write">
			<div class="one-box">
				<dl>
					<itui:itemViewArea itemId="title" itemInfo="${itemInfo}" colspan="3"/>
				</dl>
			</div>
			<div class="one-box">
				<dl>
					<itui:itemViewArea itemId="year" itemInfo="${itemInfo}" />
				</dl>
			</div>
			<div class="one-box">
				<dl>
					<itui:itemViewArea itemId="insttIdx" itemInfo="${itemInfo}" colspan="3"/>
				</dl>
			</div>
		</div>
		
		<!-- TAB 메뉴 -->
		<div class="contents-area">
           <div class="tabmenu-wrapper type02">
               <ul>
                   <li>
                       <a href="#" id="tabmenu1" class="active">Ⅰ 환경분석 <br /> 1. 지역 내 주요 정책 분석</a>
                   </li>
                   <li>
                       <a href="#" id="tabmenu2">Ⅰ 환경분석 <br /> 2. 관할 내 기업현황 분석</a>
                   </li>
                   <li>
                       <a href="#" id="tabmenu3">Ⅱ 환경분석 결과 시사점 도출</a>
                   </li>
                   <li>
                       <a href="#" id="tabmenu4">Ⅲ 핵심전략</a>
                   </li>
               </ul>
           </div>

          <div class="contents-plan-wrapper">
          
          	<!-- 1. 지역 내 주요 정책 분석  -->
              <div class="contents-plan-area active" id="tab-plan-content1">
                  <div class="gray-box">
                      <h3>항목별 작성방법 가이드</h3>
                      <ul class="ul-list01">
                          <li>항목별 키워드는 예시로 지역 내 상황에 맞춰 <span class="point-color02">변경 가능(이하 동일)</span></li>
                          <li>정부의 <span class="point-color02">주요 정책 추진계획 발표 및 각 시도별 주요시책 보고서 활용</span>, 다양한 정부 및 지자체 발표자료 중 <span class="point-color02 unberline">관할 지역과 관련한 정책</span> 또는 <span class="point-color02 unberline">특정 분야 기업직업훈련 활성화 관련 항목 중심</span>으로도출</li>
                      </ul>
                  </div>
                  <c:forEach var="listDt" items="${list }" varStatus="i">
                  	<c:if test="${listDt.SMRIZE_INSTT_YN == 'Y' }">
                  		<c:set var="smRize" value="${listDt.INSTT_IDX}"/>
                  	</c:if>
                  </c:forEach>
                  <c:forEach var="listDt" items="${list }" varStatus="i">
                  <c:if test="${(loginVO.insttIdx == listDt.INSTT_IDX) || loginVO.insttIdx == smRize}">
                  	<h4 class="title-type06 ml0">정책 분석 - ${listDt.INSTT_NAME }</h4>
                  	<div>
                  	  <input type="hidden" name="instt" id="instt" value="<c:out value="${listDt.INSTT_IDX}"/>"/>
                      <textarea id="policy${listDt.INSTT_IDX }" name="policy" value="<c:out value="${listDt.POLICI_CN }"/>" class="w100 type02" style="height:500px;">${listDt.POLICI_CN }</textarea>
                  	</div>
                  </c:if>
                  </c:forEach>
              </div>
              
              <!-- 2. 관할 내 기업현황 분석(기업분포) -->
              <div class="contents-plan-area" id="tab-plan-content2">
                  <div class="gray-box">
                      <h3>항목별 작성방법 가이드</h3>
                      <ul class="ul-list01">
                          <li>기업분석은 <span class="point-color02">관할 지역 내 기업분포</span> 및 관할 지역 내 <span class="point-color02">기업직업훈련현황(HRD4U 중소기업DB, 1차 기업바구니) 분석 필수 포함</span></li>
                          <li>국가통계 및 본부제공(중소기업DB) 외 <span class="point-color02">지역별 자체 활용 가능한 분석자료</span>가 있을 경우 <span class="point-color02">추가 분석</span></li>
                          <li>계량 데이터 외에 관할 내 기업현황을 파악할 수 있는 <span class="point-color02">비계량 자료 활용 가능</span></li>
                      </ul>
                  </div>
                  <div class="contents-area">
                  	  <c:forEach var="listDt" items="${list }" varStatus="i">
                  	  	  <c:if test="${(loginVO.insttIdx == listDt.INSTT_IDX) || loginVO.insttIdx == smRize}">
		                      <div class="contents-box pl0">
		                          <h4 class="title-type06 ml0">기업분포 - ${listDt.INSTT_NAME }</h4>
				                  	<div>
				                      <textarea id="distri${listDt.INSTT_IDX }" name="distribution" value="<c:out value="${listDt.CORP_DISTRB_CN }"/>" class="w100 type02" style="height:500px;">${listDt.CORP_DISTRB_CN }</textarea>
				                  	</div>
		                      </div>
		                      
		                      <!-- 통계리스트 -->
		                      <div class="contents-box pl0">
		                          <div class="table-type02 horizontal-scroll">
		                              <table>
		                                  <caption>기업분포표 : 구분, 합계, 도매 및 소매업, 건설업, 숙박 및 음식점업, 제조업, 보건업 및 사회복지서비스업에 관한 정보 제공표</caption>
		                                  <colgroup>
		                                      <col style="width: 20%" />
		                                      <col style="width: 10%" />
		                                      <col style="width: 10%" />
		                                      <col style="width: 10%" />
		                                      <col style="width: 10%" />
		                                      <col style="width: 10%" />
		                                      <col style="width: 10%" />
		                                      <col style="width: 10%" />
		                                      <col style="width: 10%" />
		                                  </colgroup>
		                                  <thead>
		                                      <tr>
		                                          <th scope="col">구분</th>
		                                          <th scope="col">합계</th>
		                                          <th scope="col">도매 및 소매업</th>
		                                          <th scope="col">건설업</th>
		                                          <th scope="col">숙박 및 음식점업</th>
		                                          <th scope="col">제조업</th>
		                                          <th scope="col">보건업 및 사회복지 서비스업</th>
		                                          <th scope="col">부동산업</th>
		                                          <th scope="col">기타</th>
		                                      </tr>
		                                  </thead>
		                                  
		                                  <tbody>
		                                  	<c:set var="INDUTY_SUM1" value="0"/>
		                                  	<c:set var="INDUTY_SUM2" value="0"/>
		                                  	<c:set var="INDUTY_SUM3" value="0"/>
		                                  	<c:set var="INDUTY_SUM4" value="0"/>
		                                  	<c:set var="INDUTY_SUM5" value="0"/>
		                                  	<c:set var="INDUTY_SUM6" value="0"/>
		                                  	<c:set var="INDUTY_SUM7" value="0"/>
		                                  	<c:forEach var="accumListDt" items="${accumList }" varStatus="i">
						                  	  	  <c:if test="${accumListDt.INSTT_IDX == listDt.INSTT_IDX}">
						                  	  	  
								                      <c:set var="INDUTY_SUM1" value="${INDUTY_SUM1 + accumListDt.INDUTY_NM1 }"/>
								                      <c:set var="INDUTY_SUM2" value="${INDUTY_SUM2 + accumListDt.INDUTY_NM2 }"/>
								                      <c:set var="INDUTY_SUM3" value="${INDUTY_SUM3 + accumListDt.INDUTY_NM3 }"/>
								                      <c:set var="INDUTY_SUM4" value="${INDUTY_SUM4 + accumListDt.INDUTY_NM4 }"/>
								                      <c:set var="INDUTY_SUM5" value="${INDUTY_SUM5 + accumListDt.INDUTY_NM5 }"/>
								                      <c:set var="INDUTY_SUM6" value="${INDUTY_SUM6 + accumListDt.INDUTY_NM6 }"/>
								                      <c:set var="INDUTY_SUM7" value="${INDUTY_SUM7 + accumListDt.INDUTY_NM7 }"/>
								                      <tr>
				                                          <th scope="row" class="bg02">${accumListDt.BSK_NMPR_SE_NM }</th>
				                                          <td class="right"><fmt:formatNumber value="${accumListDt.INDUTY_NM1 + accumListDt.INDUTY_NM2 + accumListDt.INDUTY_NM3 + accumListDt.INDUTY_NM4 + accumListDt.INDUTY_NM5 + accumListDt.INDUTY_NM6 + accumListDt.INDUTY_NM7}" pattern="#,###,###,###,###"/></td>
				                                          <td class="right"><fmt:formatNumber value="${accumListDt.INDUTY_NM1 }" pattern="#,###,###,###,###"/></td>
				                                          <td class="right"><fmt:formatNumber value="${accumListDt.INDUTY_NM2 }" pattern="#,###,###,###,###"/></td>
				                                          <td class="right"><fmt:formatNumber value="${accumListDt.INDUTY_NM3 }" pattern="#,###,###,###,###"/></td>
				                                          <td class="right"><fmt:formatNumber value="${accumListDt.INDUTY_NM4 }" pattern="#,###,###,###,###"/></td>
				                                          <td class="right"><fmt:formatNumber value="${accumListDt.INDUTY_NM5 }" pattern="#,###,###,###,###"/></td>
				                                          <td class="right"><fmt:formatNumber value="${accumListDt.INDUTY_NM6 }" pattern="#,###,###,###,###"/></td>
				                                          <td class="right"><fmt:formatNumber value="${accumListDt.INDUTY_NM7 }" pattern="#,###,###,###,###"/></td>
				                                      </tr>
							                      </c:if>
					                      	</c:forEach>
		                                  </tbody>
		                                  <tfoot>
		                                      <tr class="bg01">
		                                          <th scope="row">합계</th>
		                                          <td class="right"><fmt:formatNumber value="${INDUTY_SUM1 + INDUTY_SUM2 + INDUTY_SUM3 + INDUTY_SUM4 + INDUTY_SUM5 + INDUTY_SUM6 + INDUTY_SUM7}" pattern="#,###,###,###,###"/></td>
		                                          <td class="right"><fmt:formatNumber value="${INDUTY_SUM1 }" pattern="#,###,###,###,###"/></td>
		                                          <td class="right"><fmt:formatNumber value="${INDUTY_SUM2 }" pattern="#,###,###,###,###"/></td>
		                                          <td class="right"><fmt:formatNumber value="${INDUTY_SUM3 }" pattern="#,###,###,###,###"/></td>
		                                          <td class="right"><fmt:formatNumber value="${INDUTY_SUM4 }" pattern="#,###,###,###,###"/></td>
		                                          <td class="right"><fmt:formatNumber value="${INDUTY_SUM5 }" pattern="#,###,###,###,###"/></td>
		                                          <td class="right"><fmt:formatNumber value="${INDUTY_SUM6 }" pattern="#,###,###,###,###"/></td>
		                                          <td class="right"><fmt:formatNumber value="${INDUTY_SUM7 }" pattern="#,###,###,###,###"/></td>
		                                      </tr>
		                                  </tfoot>
		                              </table>
		                          </div>
                      			</div>
	                      </c:if>
                      </c:forEach>
                  </div>



				  <!-- 2. 관할 내 기업현황 분석(기업직업훈련현황) -->
                  <div class="contents-area">
                  	  <c:forEach var="listDt" items="${list }" varStatus="i">
                  	  	  <c:if test="${(loginVO.insttIdx == listDt.INSTT_IDX) || loginVO.insttIdx == smRize}">
		                      <div class="contents-box pl0">
		                          <h4 class="title-type06 ml0">기업직업훈련 현황 - ${listDt.INSTT_NAME }</h4>
				                  	<div >
				                      <textarea id="training${listDt.INSTT_IDX }" name="training" value="<c:out value="${listDt.TR_CN }"/>" class="w100 type02" style="height:500px;">${listDt.TR_CN }</textarea>
				                  	</div>
		                      </div>
		                      
		                       <div class="contents-box pl0">
                          <div class="table-type02 horizontal-scroll">
                              <table>
                                  <caption>기업분포표 : 구분, 합계, 도매 및 소매업, 건설업, 숙박 및 음식점업, 제조업, 보건업 및 사회복지서비스업에 관한 정보 제공표</caption>
                                  <colgroup>
                                      <col style="width: 20%" />
                                      <col style="width: 20%" />
                                      <col style="width: 20%" />
                                      <col style="width: 20%" />
                                      <col style="width: 20%" />
                                  </colgroup>
                                  <thead>
                                      <tr>
                                          <th scope="col">구분</th>
                                          <th scope="col">합계</th>
                                          <th scope="col">${dt.YEAR - 3}년</th>
                                          <th scope="col">${dt.YEAR - 2}년</th>
                                          <th scope="col">${dt.YEAR - 1}년</th>
                                      </tr>
                                  </thead>
                                  <tbody>
                                  	  <c:set var="YEAR_SUM3" value="0"/>
                                  	  <c:set var="YEAR_SUM2" value="0"/>
                                      <c:set var="YEAR_SUM1" value="0"/>
                                      <c:forEach var="yearListDt" items="${yearList }" varStatus="i">
				                  	  	  <c:if test="${yearListDt.INSTT_IDX == listDt.INSTT_IDX}">
						                      <c:set var="YEAR_SUM3" value="${YEAR_SUM3 + yearListDt.YEAR3 }"/>
						                      <c:set var="YEAR_SUM2" value="${YEAR_SUM2 + yearListDt.YEAR2 }"/>
						                      <c:set var="YEAR_SUM1" value="${YEAR_SUM1 + yearListDt.YEAR1 }"/>
						                      <tr>
		                                          <th scope="row" class="bg02">${yearListDt.BSK_NMPR_SE_NM }</th>
		                                          <td class="right"><fmt:formatNumber value="${yearListDt.YEAR3 + yearListDt.YEAR2 + yearListDt.YEAR1}" pattern="#,###,###,###,###"/></td>
		                                          <td class="right"><fmt:formatNumber value="${yearListDt.YEAR3 }" pattern="#,###,###,###,###"/></td>
		                                          <td class="right"><fmt:formatNumber value="${yearListDt.YEAR2 }" pattern="#,###,###,###,###"/></td>
		                                          <td class="right"><fmt:formatNumber value="${yearListDt.YEAR1 }" pattern="#,###,###,###,###"/></td>
		                                      </tr>
					                      </c:if>
			                      	</c:forEach>
                                  </tbody>
                                  <tfoot>
                                      <tr class="bg01">
                                          <th scope="row">합계</th>
                                          <td class="right"><fmt:formatNumber value="${YEAR_SUM3 + YEAR_SUM2 + YEAR_SUM1}" pattern="#,###,###,###,###"/></td>
                                          <td class="right"><fmt:formatNumber value="${YEAR_SUM3 }" pattern="#,###,###,###,###"/></td>
                                          <td class="right"><fmt:formatNumber value="${YEAR_SUM2 }" pattern="#,###,###,###,###"/></td>
                                          <td class="right"><fmt:formatNumber value="${YEAR_SUM1 }" pattern="#,###,###,###,###"/></td>
                                      </tr>
                                  </tfoot>
                              </table>
                          </div>
                      </div>
	                      </c:if>
                      </c:forEach>
                     
                  </div>
              </div>

			 <!-- 환경분석 결과 시사점 도출 -->
              <div class="contents-plan-area" id="tab-plan-content3">
                  <div class="gray-box">
                      <h3>항목별 작성방법 가이드</h3>
                      <ul class="ul-list01">
                          <li>관할 지역 관련 주요 정책 및 기업현황 분석 결과를 토대로 <span class="point-color02">시사점 도출</span></li>
                          <li>시사점은 자유롭게 도출하되, <span class="point-color02">2. 핵심전략 도출 및 타깃그룹 설정의 기준이 되어야 함</span></li>
                      </ul>
                  </div>
                  <div>
                      <itui:itemTextareaForArea itemId="implication" itemInfo="${itemInfo}" colspan="3" objStyle="width:100%; height:500px;"/>
                  </div>
              </div>
              

			  <!-- 핵심전략 -->
              <div class="contents-plan-area" id="tab-plan-content4">
                  <div class="gray-box">
                      <h3>항목별 작성방법 가이드</h3>
                      <ul class="ul-list01">
                          <li>전략목표 도출 시 타깃그룹별 전략은 자율적으로 설정</li>
                          <li>1차 기업바구니에 대한 <span class="point-color02">재조정 기준 수립</span>, 재조정 결과 <span class="point-color02">타깃그룹 규모 및 그룹별 접근전략</span><sub>(대상별 훈련꾸러미 구성 기준 및 방법 등 포함) 등 필수 작성</sub></li>
                      </ul>
                  </div>
                  
                  <!-- 지역활성화계획 기업바구니 -->
                  <div class="contents-area">
                      <div class="contents-box pl0">
                          <div class="table-type02 horizontal-scroll">
                              <table>
                                  <caption>핵심전략 정보표 : 연번, 바구니, 기업바구니 조정에 관한 입력표</caption>
                                  <colgroup>
                                      <col style="width: 8%" />
                                      <col style="width: 15%" />
                                      <col style="width: 12%" />
                                      <col style="width: 10%" />
                                      <col style="width: 27.5%" />
                                      <col style="width: 27.5%" />
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
                                          <td>일반 홍보그룹</td>   
                                          <td class="right"><fmt:formatNumber value="${bskList.LCLAS_CO_1  }" pattern="#,###,###,###,###"/></td>
                                          <td class="right"><span class="point-color01"><fmt:formatNumber value="${bskList.LCLAS_MDAT_CO_1 }" pattern="#,###,###,###,###"/></span></td>
                                          <td><textarea id="goal1" name="goal" value="<c:out value="${ bskList.GOAL_CN_1}"/>" cols="50" rows="5" class="w100 type02">${bskList.GOAL_CN_1 }</textarea></td>
                                          <td><textarea id="detail1" name="detail" value="<c:out value="${ bskList.DETAIL_CN_1}"/>" cols="50" rows="5" class="w100 type02">${ bskList.DETAIL_CN_1}</textarea></td>
                                      </tr>
                                      <tr>
                                          <td>2</td>
                                          <td>적극발굴그룹</td>
                                          <td class="right"><fmt:formatNumber value="${bskList.LCLAS_CO_2  }" pattern="#,###,###,###,###"/></td>
                                          <td class="right"><span class="point-color01"><fmt:formatNumber value="${bskList.LCLAS_MDAT_CO_2 }" pattern="#,###,###,###,###"/></span></td>
                                          <td><textarea id="goal2" name="goal" value="<c:out value="${ bskList.GOAL_CN_2}"/>" cols="50" rows="5" class="w100 type02">${ bskList.GOAL_CN_2}</textarea></td>
                                          <td><textarea id="detail2" name="detail" value="<c:out value="${ bskList.DETAIL_CN_2}"/>" cols="50" rows="5" class="w100 type02">${ bskList.DETAIL_CN_2}</textarea></td>
                                      </tr>
                                      <tr>
                                          <td>3</td>
                                          <td>훈련확산그룹</td>
                                          <td class="right"><fmt:formatNumber value="${bskList.LCLAS_CO_3  }" pattern="#,###,###,###,###"/></td>
                                          <td class="right"><span class="point-color01"><fmt:formatNumber value="${bskList.LCLAS_MDAT_CO_3 }" pattern="#,###,###,###,###"/></span></td>
                                          <td><textarea id="goal3" name="goal" value="<c:out value="${ bskList.GOAL_CN_3}"/>" cols="50" rows="5" class="w100 type02">${ bskList.GOAL_CN_3}</textarea></td>
                                          <td><textarea id="detail3" name="detail" value="<c:out value="${ bskList.DETAIL_CN_3}"/>" cols="50" rows="5" class="w100 type02">${ bskList.DETAIL_CN_3}</textarea></td>
                                      </tr>
                                  </tbody>
                              </table>
                          </div>
                      </div>
                      
					  <!-- 지역활성화경제 조정내용  -->
                      <div class="contents-box pl0">
                          <h4 class="title-type06 ml0">기업바구니 조정기준</h4>
                          <div>
                              <itui:itemTextareaForArea itemId="adjustment" itemInfo="${itemInfo}" colspan="3" objStyle="width:100%; height:500px;"/>
                          </div>
                      </div>

					  <!-- 지역활성화경제 그룹현황내용  -->
                      <div class="contents-box pl0">
                          <h4 class="title-type06 ml0">내 타깃 그룹 기업 현황</h4>
                          <div>
                              <itui:itemTextareaForArea itemId="groupCurrent" itemInfo="${itemInfo}" colspan="3" objStyle="width:100%; height:500px;"/>
                          </div>
                      </div>

					  <!-- 지역활성화경제 그룹접근전략내용  -->
                      <div class="contents-box pl0">
                          <h4 class="title-type06 ml0">타깃 그룹별 접근 전략</h4>
                          <div>
                              <itui:itemTextareaForArea itemId="strategy" itemInfo="${itemInfo}" colspan="3" objStyle="width:100%; height:500px;"/>
                          </div>
                      </div>
                  </div>
              </div>
           </div>
        </div>
		
		<div class="btns-area">
        	<div class="btns-right">
            	<button type="submit" class="btn-m01 btn-color03 depth2">저장</button>
				<a href="javascript:history.back();	" class="btn-m01 btn-color01 depth2">취소</a>
            </div>
        </div>
		</form>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>