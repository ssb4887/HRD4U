<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_developInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/develop/develop/input.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
<% pageContext.setAttribute("newline","\r\n"); %>
<% pageContext.setAttribute("space","\f"); %>
	<div id="cms_board_article" class="contents-wrapper">
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
		<input type="hidden" value="${tpList.PRTBIZ_IDX }" name="prtbizIdx">
		<input type="hidden" value="${corpInfo.BPL_NO }" name="bplNo">
		<input type="hidden" value="${devlopList.DEVLOP_IDX }" name="devlopIdx">
		<input type="hidden" value="${tpList.TP_IDX }" name="tpIdx">
		<input type="hidden" value="${devlopList.TP_DEVLOP_SE}" name="inputType">
		
		<div class="contents-area">

		<h3 class="title-type01 ml0 mt5">훈련과정 표준개발 신청서</h3>
		<div class="title-wrapper clear">
			<p class="state fl">
                              상태 <strong> ${confirmProgress[devlopList.CONFM_STATUS]}</strong>
             </p>
        </div>
		<h3 class="title-type02 ml0 mt5 fl">기본정보</h3>		
		<div class="fr mb10">
			<a href="#none" class="btn-m01 btn-color01" id="open-modal01">주치의 지원 (HELP)</a>
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
                    		<td class="left"><c:out value="${corpInfo.BPL_NM }"/></td>
                    		<th scope="row">사업장관리번호</th>
                    		<td class="left"><c:out value="${corpInfo.BPL_NO }"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="col" colspan="4">훈련실시 지역정보</th>
                    	</tr>     
                    	<tr>
                    		<th scope="row">훈련실시 주소&nbsp;<strong class="point-important">*</strong></th>
                    		<td class="left"><itui:objectSelectClass itemId="sido" objStyle="width:49%; font-size:15px;" itemInfo="${itemInfo}" objVal="${devlopList.TR_OPRTN_GUGUN_CD}"/></td>
                    		<th scope="row">관할 지부지사&nbsp;<strong class="point-important">*</strong></th>
                    		<td class="left">
                    			<span id="insttName"></span>
                    			<select name="insttIdx" id="insttIdx" style="display:none;">
                    				<option value="">지부지사 선택</option> 
                    				<c:forEach var="instt" items="${insttList}" varStatus="i">
                    					<option value="${instt.INSTT_IDX}" data-block="${instt.BLOCK_CD}" <c:if test="${devlopList.TR_OPRTN_GUGUN_CD eq instt.BLOCK_CD}">selected</c:if>><c:out value="${instt.INSTT_NAME}"/></option>
                    				</c:forEach>
                    			</select>
                    		</td>
                    	</tr>    
                    	<tr>
                    		<th scope="col" colspan="4">훈련과정 신청내용</th>
                    	</tr>                     	                	                   	
                    	<tr>
                    		<th scope="row">훈련과정명</th>
                    		<td colspan="3" class="left"><input type="hidden" value="${tpList.TP_NM}" name="tpNm">${tpList.TP_NM}</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">NCS 적용여부</th>
                    		<td class="left" colspan="3">아니오</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">NCS 분류</th>
                    		<td class="left" colspan="3">	
                    			<input type="hidden" value="${tpList.NCS_SCLAS_CD }" name="ncsSclasCode">								
								<itui:objectView itemId="ncsSclasCode" itemInfo="${itemInfo}" optnHashMap="${optnHashMap}" objVal="${tpList.NCS_SCLAS_CD}"/>(${tpList.NCS_SCLAS_CD})											
							</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">과정담당자명&nbsp;<strong class="point-important">*</strong></th>
                    		<td ><itui:objectText itemId="tpPicName" itemInfo="${itemInfo}" objVal="${tpList.TP_PIC_NAME }" objClass="w100"/></td>
                    		<th scope="row">과정담당자 전화번호<strong class="point-important">*</strong></th>
                    		<td ><itui:objectText itemId="tpPicTelNo" itemInfo="${itemInfo}" objVal="${tpList.TP_PIC_TELNO }"  objClass="w100 onlyNumDash"/></td>
                    	</tr>
                    	<tr>
                    	</tr>
                    	<tr>
                    		<th scope="row">훈련사업</th>
                    		<td class="left">
                    		<input type="hidden" value="${tpList.TR_BIZ }" name="trBiz">
                    		   	<c:choose>
                    				<c:when test="${tpList.TR_BIZ eq 1 }">S-OJT</c:when>
                    				<c:when test="${tpList.TR_BIZ eq 4 }">사업주</c:when>
                    			</c:choose>
                    		</td>
                    		<th scope="row">훈련유형</th>
                    		<td class="left">
                    		<input type="hidden" value="${tpList.TR_BIZ }" name="trType">
                    		   <c:choose>
                    				<c:when test="${tpList.TR_BIZ eq 1 }">일반훈련</c:when>
                    				<c:when test="${tpList.TR_BIZ eq 4 }">사업주훈련</c:when>
                    			</c:choose>
							</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">훈련방법</th>
                    		<td class="left">
                    		   <c:choose>
                    				<c:when test="${tpList.TR_BIZ eq 1 }">
                    				<input type="hidden" value="S-OJT훈련" name="trMth">
                    				일반훈련
                    				</c:when>
                    				<c:when test="${tpList.TR_BIZ eq 4 }">
                    				<input type="hidden" value="집체훈련" name="trMth">
                    				집체훈련
                    				</c:when>
                    			</c:choose>                  
                    		</td>
                    		<th scope="row">훈련대상</th>
                    		<td class="left">
                    		<input type="hidden" value="자체  / 재직자" name="trTrget">
                    		자체  / 재직자
                    		</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">법정훈련 구분</th>
                    		<td class="left">
                    		<input type="hidden" value="N" name="legalTrType">
                    		해당없음
                    		</td>
                    		<th scope="row">고숙련기술 과정구분</th>
                    		<td class="left">
                    		<input type="hidden" value="N" name="hghExprtNtcnqCrseType">
                    		해당없음(일반)
                    		</td>
                    	</tr>

                    </tbody>
				</table>
			</div>
		</div>
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
				<table class="width-type02">
					<caption>능력개발클리닉 참여 신청서 중 HRD 담당자 정보 입력 폼</caption>
            		<colgroup>
                        <col width="15%">
                        <col width="15%">
                        <col width="auto">
                        <col width="auto">
                        <col width="15%">
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
                    		<td><input type="hidden" value="${tpList.TR_DAYCNT }" name="trDayCnt">${tpList.TR_DAYCNT}</td>
                    		<td><input type="hidden" value="${tpList.TRTM }" name="trTm">${tpList.TRTM}</td>
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
                        		<th>학급정원(명)&nbsp;<strong class="point-important">*</strong></th>
                        		<th>학급수(개)&nbsp;<strong class="point-important">*</strong></th>
                        		<th>훈련생 출결방법</th>
                        	</tr>
                            <tr>
                                <th scope="row">학급정원(학급수)</th>
                                 <td><itui:objectText itemId="clasNmpr" itemInfo="${itemInfo}" objVal="${tpList.CLAS_NMPR}" objClass="w100 onlyNum"/></td>                                
                                <td><itui:objectText itemId="clasCnt" itemInfo="${itemInfo}" objVal="${tpList.CLAS_CNT}" objClass="w100 onlyNum"/></td>
                                <td><input type="hidden" value="QR" name="trneeAtabManageMth">QR코드</td>
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
	                        <col style="width: auto">
	                        <col style="width: auto">
	                        <col style="width: auto">
	                    </colgroup>
	                    <tbody>
	                        <tr>
	                            <th scope="row" colspan="4">훈련 상세정보</th>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련목표</th>
	                            <td colspan="3" class="left">
	                            	<input type="hidden" value="${tpList.TR_GOAL}" name="trGoal">
	                            	${tpList.TR_GOAL}
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">주요 훈련내용</th>
	                            <td colspan="3" class="left">
		                            <input type="hidden" value="${tpList.TR_SFE}" name="trSfe">
		                            ${tpList.TR_SFE}
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련대상요건</th>
	                            <td colspan="3" class="left">
	                            	<input type="hidden" value="${tpList.TRNREQM}" name="trnReqm">
	                            	${tpList.TRNREQM}
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
			                    		<input type="hidden" value="${i.count}" name="courseNo${i.index }">
			                    		${i.count}
		                    		</td>
		                    		<td>
		                    			<input type="hidden" value="${tpSubList[i.index].COURSE_NAME }" name="courseName${i.index }">
		                    			${tpSubList[i.index].COURSE_NAME}
		                    		</td>
		                    		<td class="left">
		                    			<input type="hidden" value="${tpSubList[i.index].TR_CN }" name="cn${i.index }">
		                    			${tpSubList[i.index].TR_CN}
		                    		</td>
		                    		<td>
		                    			<input type="hidden" value="${tpSubList[i.index].TRTM }" name="time${i.index }">
		                    			${tpSubList[i.index].TRTM}시간
		                    		</td>
		                    		<c:if test="${i.last}">
		                    		<input type="hidden" value="${i.count}" name="tpSubLength">
		                    		</c:if>
		                    	</tr>
	                    	</c:forEach>
	                    </tbody>
	                </table>
	            </div>
	        </div>
	    </div>
		<c:if test="${devlopList.CONFM_STATUS eq '40'}">
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>능력개발클리닉 참여 신청서에 대한 주치의 의견</caption>
	            		<colgroup>
	                        <col width="15%">
	                        <col width="auto">
	                    </colgroup>
	                    <tbody>
	                    	<tr>
	                    		<th scope="row">주치의 의견</th>
	                    		<td class="left"><c:out value="${devlopList.DOCTOR_OPINION}"/></td>
	                    	</tr>
	                    </tbody>
					</table>
				</div>
			</div>
		</c:if>
		<c:if test="${devlopList.CONFM_STATUS eq '33'}">
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>능력개발클리닉 참여 신청서에 대한 기업 의견</caption>
	            		<colgroup>
	                        <col width="15%">
	                        <col width="auto">
	                    </colgroup>
	                    <tbody>
                    	<tr>
                    		<th scope="row">기업 의견</th>
                    		<td class="left">
                    			<c:set var="doctorOpinion" value="${fn:replace(devlopList.CORP_OPINION,'<br>',newline)}"/>
                    			<itui:objectTextarea itemId="corpOpinion" itemInfo="${itemInfo}" objVal="${doctorOpinion}" objClass="w100 h100"/>
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
						<caption>능력개발클리닉 참여 신청서에 대한 기업 의견</caption>
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
				<button type="submit" class="btn-m01 btn-color02 depth2 fn_btn_submit" data-status="<%= ConfirmProgress.TEMPSAVE.getCode()%>">임시저장</button>
				<button type="submit" class="btn-m01 btn-color03 depth2 fn_btn_submit" data-status="<%= ConfirmProgress.APPLY.getCode()%>">신청</button>
				<a href="<c:out value="${DEVELOP_LIST_FORM_URL}"/>" title="목록" class="btn-m01 btn-color01 depth2 fn_btn_write" onclick="return confirm('목록으로 이동하시겠습니까? \n임시저장을 하지 않으면 작성한 내용은 저장되지 않습니다.')">목록</a>
			</div>
		</div>
		</form>
		</div>
<%@ include file="doctorHelpModal.jsp" %>		
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>