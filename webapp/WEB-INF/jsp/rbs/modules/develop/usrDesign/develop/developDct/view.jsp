<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_developInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/develop/view.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
<% pageContext.setAttribute("newline","\r\n"); %>
<% pageContext.setAttribute("space","\f"); %>
	<div id="cms_board_article" class="contents-wrapper">
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="#"/>" target="submit_target" enctype="multipart/form-data">
		<input type="hidden" value="${tpList.PRTBIZ_IDX }" name="prtbizIdx">
		<input type="hidden" value="${corpInfo.BPL_NO }" name="bplNo">
		<input type="hidden" value="${devlopList.DEVLOP_IDX }" name="devlopIdx">
		
		<div class="contents-area">
		<h3 class="title-type01 ml0 mt5">훈련과정 개발 신청서</h3>
		<div class="title-wrapper clear">
			<p class="state fl">
                              상태 <strong> ${confirmProgress[devlopList.CONFM_STATUS]}</strong>
             </p>
        </div>		
		<div class="title-wrapper clear" style="margin-bottom:0px;">
			<h3 class="title-type02 ml0 mt5 fl">기본정보</h3>		
        </div>		
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
            	<table class="width-type02">
            		<colgroup>
                        <col width="15%">
                        <col width="auto">
                        <col width="15%">
                        <col width="auto">
                    </colgroup>
                    <tbody>
                    	<tr>
                    		<th scope="col" colspan="4">훈련기관(기업) 정보</th>
                    	</tr>                    
                    	<tr>
                    		<th scope="row">기업명</th>
                    		<td class=""><c:out value="${corpInfo.BPL_NM }"/></td>
                    		<th scope="row">사업장관리번호</th>
                    		<td class="left"><c:out value="${corpInfo.BPL_NO }"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="col" colspan="4">훈련실시 지역정보</th>
                    	</tr>     
                    	<tr>
                    		<th scope="row">훈련실시 주소</th>
                    		<td class="left"><itui:objectViewCustom itemId="sido" itemInfo="${itemInfo}" objVal="${devlopList.TR_OPRTN_GUGUN_CD }"/></td>
                    		<th scope="row">관할 지부지사</th>
                    		<td class="left"><c:out value="${devlopList.INSTT_NAME }"/></td>
                    	</tr>    
                    	<tr>
                    		<th scope="col" colspan="4">훈련과정 신청내용</th>
                    	</tr>                     	                	                   	
                    	<tr>
                    		<th scope="row">훈련과정명</th>
                    		<td colspan="3" class="left"><c:out value="${tpList.TP_NM }"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">NCS 적용여부</th>
                    		<td class="left" colspan="3">아니오</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">NCS 분류</th>
                    		<td class="left" colspan="3">	
								<itui:objectView itemId="ncsSclasCode" itemInfo="${itemInfo}" optnHashMap="${optnHashMap}" objVal="${tpList.NCS_SCLAS_CD }"/>(${tpList.NCS_SCLAS_CD})											
							</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">과정담당자명</th>
                    		<td class="left"><c:out value="${tpList.TP_PIC_NAME }"/></td>
                    		<th scope="row">과정담당자 전화번호</th>
                    		<td class="left"><c:out value="${tpList.TP_PIC_TELNO }"/></td>
                    	</tr>
                    	<tr>
                    	</tr>
                    	<tr>
                    		<th scope="row">훈련사업</th>
                    		<td class="left">
                    			<c:choose>
                    				<c:when test="${tpList.TR_BIZ eq 1 }">S-OJT</c:when>
                    				<c:when test="${tpList.TR_BIZ eq 4 }">사업주</c:when>
                    			</c:choose>
							</td>
                    		<th scope="row">훈련유형</th>
                    		<td class="left"><c:out value="${tpList.TR_TYPE}"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">훈련방법</th>
                    		<td class="left"><c:out value="${tpList.TR_MTH}"/></td>
                    		<th scope="row">훈련대상</th>
                    		<td class="left"><c:out value="${tpList.TR_TRGET}"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">법정훈련 구분</th>
                    		<td class="left">해당없음</td>
                    		<th scope="row">고숙련기술 과정구분</th>
                    		<td class="left">해당없음(일반)</td>
                    	</tr>

                    </tbody>
				</table>
			</div>
		</div>
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
				<table class="width-type02">
					<caption>맞춤훈련과정개발 표준개발 신청서 중 HRD 담당자 정보 입력 폼</caption>
            		<colgroup>
                        <col width="15%">
                        <col width="auto">
                        <col width="20%">
                        <col width="20%">
                        <col width="auto">
                    </colgroup>
                    <tbody>
                    	<tr>
                    		<th scope="row">구분</th>
                    		<th scope="row">훈련방법</th>
                    		<th scope="row">훈련일수(일)</th>
                    		<th scope="row">훈련시간(시간)</th>
                    		<th scope="row">기숙사제공여부</th>
                    	</tr>        
                    	<tr>
                    		<th scope="row">훈련일수(시간)</th>
                    		<td><c:out value="${tpList.TR_MTH}"/></td>
                    		<td><c:out value="${tpList.TR_DAYCNT}"/></td>
                    		<td><c:out value="${tpList.TRTM}"/></td>
                    		<td>아니오</td>
                    	</tr>            	

                    </tbody>
				</table>
			</div>
		</div>
 		<div class="contents-box pl0">
                <div class="table-type02 horizontal-scroll">
                    <table>
                        <colgroup>
                            <col style="width: 15%">
                            <col style="width: auto">
                            <col style="width: auto">
                            <col style="width: 15%">
                        </colgroup>
                        <tbody>
                        	<tr>
                        		<th>구분</th>
                        		<th>학급정원</th>
                        		<th>학급수</th>
                        		<th>훈련생 출결방법</th>
                        	</tr>
                            <tr>
                                <th scope="row">학급정원(학급수)</th>
                                <td><c:out value="${tpList.CLAS_NMPR}"/>명</td>                                
                                <td><c:out value="${tpList.CLAS_CNT}"/>개</td>
                                <td>QR코드</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
		<div class="contents-area">
	        <div class="contents-box pl0">
	            <h3 class="title-type02 ml0">훈련과정 내용</h3>
	            <div class="table-type02 horizontal-scroll">
	                <table>
	                    <colgroup>
	                        <col style="width: 15%">
	                        <col style="width: 22%">
	                        <col style="width: 39%">
	                        <col style="width: 8%">
	                    </colgroup>
	                    <tbody>
	                        <tr>
	                            <th scope="row" colspan="4">훈련 상세정보</th>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련목표</th>
	                            <td colspan="3" class="left">
	                            ${tpList.TR_GOAL }
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">주요 훈련내용</th>
	                            <td colspan="3" class="left">	                            
	                            ${tpList.TR_SFE }
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련대상요건</th>
	                            <td colspan="3" class="left">
	                            ${tpList.TRNREQM }
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row" colspan="4">교과프로필</th>
	                        </tr>
	                        <tr>
	                            <th scope="row">연번</th>
	                            <th scope="row">교과목명</th>
	                            <th scope="row">세부내용 단원(과제명)</th>
	                            <th scope="row">시간</th>
	                        </tr>
	                    	<c:forEach items="${tpSubList }" varStatus="i">
		                    	<tr>
		                    		<td> 			                    		
			                    		<c:out value="${tpSubList[i.index].COURSE_NO}"/>
		                    		</td>
		                    		<td>		                    			
		                    			<c:out value="${tpSubList[i.index].COURSE_NAME}"/>
		                    		</td>
		                    		<td class="left">		                    			
		                    			${tpSubList[i.index].TR_CN}
		                    		</td>
		                    		<td>		                    			
		                    			<c:out value="${tpSubList[i.index].TRTM}"/>시간
		                    		</td>
		                    	</tr>
	                    	</c:forEach>
	                    </tbody>
	                </table>
	            </div>
	        </div>
	    </div>
	    <c:if test="${devlopList.CONFM_STATUS ne '10' && devlopList.CONFM_STATUS ne '33' && devlopList.CONFM_STATUS ne '37'}">
	    	<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>맞춤훈련과정개발 표준개발 신청서에 대한 주치의 의견</caption>
	            		<colgroup>
	                        <col width="15%">
	                        <col width="auto">
	                    </colgroup>
	                    <tbody>
	                    	<tr>
	                    		<th scope="row">주치의 의견</th>
	                    		<td class="left">
	                    			<c:choose>
										<c:when test="${(loginVO.usertypeIdx ne '40' || loginVO.usertypeIdx ne '55') && (devlopList.CONFM_STATUS eq '30' || devlopList.CONFM_STATUS eq '35')}">
											<c:set var="doctorOpinion" value="${fn:replace(devlopList.DOCTOR_OPINION,'<br>',newline)}"/>
											<itui:objectTextarea itemId="doctorOpinion" itemInfo="${itemInfo}" objVal="${doctorOpinion}" objClass="w100"/>
										</c:when>      
										<c:otherwise>
											${devlopList.DOCTOR_OPINION}
										</c:otherwise>             			
									</c:choose>
								</td>
	                    	</tr>
	                    </tbody>
					</table>
				</div>
			</div>
	    </c:if>    
		<c:if test="${devlopList.CONFM_STATUS eq '37'}">
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>맞춤훈련과정개발 표준개발 신청서에 대한 기업 의견</caption>
	            		<colgroup>
	                        <col width="15%">
	                        <col width="auto">
	                    </colgroup>
	                    <tbody>
	                    	<tr>
	                    		<th scope="row">기업 의견</th>
	                    		<td class="left"><c:out value="${devlopList.CORP_OPINION}"/></td>
	                    	</tr>
	                    </tbody>
					</table>
				</div>
			</div>
		</c:if>
		<div class="btns-area">
			<div class="btns-right">
				<c:if test="${(loginVO.usertypeIdx ne '40' && loginVO.usertypeIdx ne '55')}">
					<c:choose>
						<c:when test="${devlopList.CONFM_STATUS eq '10'}">
							<!-- 신청 상태가 신청(10)일 때만 접수 가능 -->
							<a href="${DEVELOP_ACCEPT_URL}&devlopIdx=${devlopList.DEVLOP_IDX}" class="btn-m01 btn-color03 depth2" onclick="return confirm('해당 표준개발 신청서를 접수 처리하시겠습니까?')">${confirmProgress['30']}</a>
						</c:when>
						<c:when test="${devlopList.CONFM_STATUS eq '30'}">
							<!-- 신청 상태가 접수(30)일 때 수정, 최종승인, 반려 -->
							<%-- <a href="${DEVELOP_MODIFY_FORM_URL}&devlopIdx=${devlopList.DEVLOP_IDX}&bplNo=${corpInfo.BPL_NO}" class="btn-m01 btn-color03 depth2 fn_btn_modify">수정</a> --%>
							<a href="${DEVELOP_APPROVE_URL}&devlopIdx=${devlopList.DEVLOP_IDX}" class="btn-m01 btn-color03 depth3" onclick="return confirm('해당 표준개발 신청서를 승인 처리하시겠습니까?')">승인</a>
							<button type="submit" class="btn-m01 btn-color08 depth2 btn-return">반려</button>
						</c:when>
						<c:when test="${devlopList.CONFM_STATUS eq '37'}">
							<!-- 신청 상태가 검토요청 반려(37)일 때 수정만 가능 -->
							<a href="${DEVELOP_MODIFY_FORM_URL}&devlopIdx=${devlopList.DEVLOP_IDX}&bplNo=${corpInfo.BPL_NO}" class="btn-m01 btn-color03 depth2 fn_btn_modify">수정</a>
						</c:when>
						<c:when test="${devlopList.CONFM_STATUS eq '35'}">
							<!-- 신청 상태가 반려요청(35)일 때는 주치의 의견 입력 후 반려만 가능 -->
							<button type="submit" class="btn-m01 btn-color08 depth2 btn-return">반려</button>
						</c:when>
						<c:when test="${devlopList.CONFM_STATUS eq '55' || devlopList.CONFM_STATUS eq '75'}">
							<!-- 최종승인 후 중도포기 가능  -->
							<a href="${DEVELOP_DROP_URL}&devlopIdx=${devlopList.DEVLOP_IDX}" class="btn-m01 btn-color08 depth2" onclick="return confirm('해당 표준개발 신청서를 중도 포기 처리하시겠습니까?')">중도 포기</a>
						</c:when>
					</c:choose>
				</c:if>
				<a href="<c:out value="${DEVELOP_REPORT_FORM_URL}&devlopIdx=${devlopList.DEVLOP_IDX}&bplNo=${corpInfo.BPL_NO}"/>" target="_blank" title="출력하기" class="btn-m01 btn-color01 depth2">출력하기</a>
				<a href="<c:out value="${DEVELOP_LIST_FORM_URL}"/>" title="목록" class="btn-m01 btn-color01 depth2 fn_btn_write">목록</a>
			</div>
		</div>
		</form>
		</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>