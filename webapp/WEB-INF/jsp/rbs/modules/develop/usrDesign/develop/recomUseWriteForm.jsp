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
	<div id="cms_board_article" class="contents-wrapper">
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="#"/>" target="submit_target" enctype="multipart/form-data">
		<input type="hidden" value="${tpList.PRTBIZ_IDX }" name="prtbizIdx">
		<input type="hidden" value="${corpInfo.BPL_NO }" name="bplNo">
		<input type="hidden" value="use" name="inputType">
		
		<div class="contents-area mt50">
			<div class="title-wrapper clear mb0">
			<h3 class="title-type01 ml0 mt5">훈련과정 표준개발 신청서</h3>
			</div>		
		<h3 class="title-type02 ml0 mt5 fl">기본정보</h3>	
		<div class="fr mb10">
			<a href="#none" class="btn-m01 btn-color01 depth3" id="open-modal02">AI추천</a>	
			<!-- 기업HRD이음컨설팅IDX가 있을 때(기업HRD이음컨설팅을 완료했을 때)만 기업HRD이음컨설팅보고서 버튼 보여줌 -->
			<c:if test="${bsisCnsl ne '0'}">
			<a href="${contextPath}/${crtSiteId}/bsisCnsl/consultingClipReport.do?mId=56&bsiscnslIdx=${bsisCnsl}" class="btn-m01 btn-color01 depth3" target="_blank">기업HRD이음컨설팅보고서</a>                                                
			</c:if>                                                   
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
                    		<td class="left">${corpInfo.BPL_NM }</td>
<!--                     		<td class="left"> -->
<!-- 								<div class="one-box"> -->
<!-- 									<dl> -->
<%-- 										<dt><itui:objectItemName itemId="ncsSclasCode" itemInfo="${itemInfo}"/>&nbsp;<strong class="point-important">*</strong></dt> --%>
<%-- 										<dd style="display:flow"><itui:objectSelectClassCustom itemId="ncsSclasCode" itemInfo="${itemInfo}" optnHashMap="${optnHashMap}" objClass="three-select" objClass2="select-last"/></dd> --%>
<!-- 									</dl> -->
<!-- 								</div>												 -->
<!-- 							</td> -->
                    		<th scope="row">사업장관리번호</th>
                    		<td class="left">${corpInfo.BPL_NO }</td>
                    	</tr>
                    	<tr>
                    		<th scope="col" colspan="4">훈련실시 지역정보</th>
                    	</tr>     
                    	<tr>
                    		<th scope="row">훈련실시 주소&nbsp;<strong class="point-important">*</strong></th>
                    		<td class="left"><itui:objectSelectClass itemId="sido" objStyle="width:49%; font-size:15px;" itemInfo="${itemInfo}"/></td>
                    		<th scope="row">관할 지부지사&nbsp;<strong class="point-important">*</strong></th>
                    		<td class="left">
                    			<span id="insttName"></span>
                    			<select name="insttIdx" id="insttIdx" style="display:none;">
                    				<option value="">지부지사 선택</option> 
                    				<c:forEach var="instt" items="${insttList}" varStatus="i">
                    					<option value="${instt.INSTT_IDX}" data-block="${instt.BLOCK_CD}">${instt.INSTT_NAME}</option>
                    				</c:forEach>
                    			</select>
                    		</td>
                    	</tr>    
                    	<tr>
                    		<th scope="col" colspan="4">훈련과정 신청내용</th>
                    	</tr>                     	                	                   	
                    	<tr>
                    		<th scope="row">훈련과정명</th>
                    		<td colspan="3" class="left"><input type="hidden" value="${tpList.TP_NAME }" name="tpNm">${tpList.TP_NAME }</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">NCS 적용여부</th>
                    		<td class="left" colspan="3">아니오</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">NCS 분류&nbsp;<strong class="point-important">*</strong></th>
                    		<td class="left" colspan="3">	
                    			<input type="hidden" value="${tpList.NCS_SCLAS_CD }" name="ncsSclasCode">								
								<itui:objectView itemId="ncsSclasCode" itemInfo="${itemInfo}" optnHashMap="${optnHashMap}" objVal="${tpList.NCS_SCLAS_CD }"/>(${tpList.NCS_SCLAS_CD})											
							</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">과정담당자명&nbsp;<strong class="point-important">*</strong></th>
                    		<td ><itui:objectText itemId="tpPicName" itemInfo="${itemInfo}" objClass="w100"/></td>
                    		<th scope="row">과정담당자 전화번호<strong class="point-important">*</strong></th>
                    		<td ><itui:objectText itemId="tpPicTelNo" itemInfo="${itemInfo}"  objClass="w100 onlyNumDash"/></td>
                    	</tr>
                    	<tr>
                    	</tr>
                    	<tr>
                    		<th scope="row">훈련사업</th>
                    		<td class="left"><input type="hidden" value="${tpList.PRTBIZ_IDX }" name="trBiz">${tpList.BIZ_TYPE2}</td>
                    		<th scope="row">훈련유형</th>
                    		<td class="left"><input type="hidden" value="${tpList.PRTBIZ_IDX }" name="trType">${tpList.TRAINING_TYPE}</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">훈련방법</th>
                    		<td class="left"><input type="hidden" value="${tpList.TRAINING_METHOD}" name="trMth">${tpList.TRAINING_METHOD}</td>
                    		<th scope="row">훈련대상</th>
                    		<td class="left"><input type="hidden" value="자체 / 재직자" name="trTrget">자체 / 재직자</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">법정훈련 구분</th>
                    		<td class="left"><input type="hidden" value="N" name="legalTrType">해당없음</td>
                    		<th scope="row">고숙련기술 과정구분</th>
                    		<td class="left"><input type="hidden" value="N" name="hghExprtNtcnqCrseType">해당없음(일반)</td>
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
                    		<td>${tpList.TRAINING_METHOD}</td>
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
                                <td><itui:objectText itemId="clasNmpr" itemInfo="${itemInfo}" objClass="w100 onlyNum"/></td>                                
                                <td><itui:objectText itemId="clasCnt" itemInfo="${itemInfo}" objClass="w100 onlyNum"/></td>
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
	                        <col style="width: 22%">
	                        <col style="width: auto">
	                        <col style="width: 8%">
	                    </colgroup>
	                    <tbody>
	                        <tr>
	                            <th scope="row" colspan="4">훈련 상세정보</th>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련목표</th>
	                            <td colspan="3" class="left">
	                            <input type="hidden" value="${tpList.TR_GOAL }" name="trGoal">
	                            ${tpList.TR_GOAL }
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">주요 훈련내용</th>
	                            <td colspan="3" class="left">
	                            <input type="hidden" value="${tpList.TR_SFE }" name="trSfe">
	                            ${tpList.TR_SFE }
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련대상요건</th>
	                            <td colspan="3" class="left">
	                            <input type="hidden" value="${tpList.TRNREQM }" name="trnReqm">
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
			                    		<input type="hidden" value="${i.count }" name="courseNo${i.index }">
			                    		${i.count }
		                    		</td>
		                    		<td>
		                    			<input type="hidden" value="${tpSubList[i.index].COURSE_NAME }" name="courseName${i.index }">
		                    			${tpSubList[i.index].COURSE_NAME }
		                    		</td>
		                    		<td class="left">
		                    			<input type="hidden" value="${tpSubList[i.index].CN }" name="cn${i.index }">
		                    			${tpSubList[i.index].CN}
		                    		</td>
		                    		<td>
		                    			<input type="hidden" value="${tpSubList[i.index].TIME }" name="time${i.index }">
		                    			${tpSubList[i.index].TIME}시간
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
		<div class="btns-area">
			<div class="btns-right">
				<button type="submit" class="btn-m01 btn-color02 depth2 fn_btn_submit" data-status="<%= ConfirmProgress.TEMPSAVE.getCode()%>">임시저장</button>
				<button type="submit" class="btn-m01 btn-color03 depth2 fn_btn_submit" data-status="<%= ConfirmProgress.APPLY.getCode()%>">신청</button>
				<a href="#" title="이전" class="btn-m01 btn-color01 depth2 fn_btn_write" onclick="confirm('이전화면으로 이동하시겠습니까? \n 작성한 내용은 저장되지 않습니다.') ? history.back() : false;">이전</a>
				<%-- <a href="<c:out value="${DEVELOP_TRAINING_LIST_FORM_URL}&bplNo=${corpInfo.BPL_NO}&devlopIdx=${devlopIdx}"/>" title="목록" class="btn-m01 btn-color01 depth2 fn_btn_write" onclick="return confirm('목록으로 이동하시겠습니까? \n 작성한 내용은 저장되지 않습니다.')">목록</a> --%>
			</div>
		</div>
		</form>
		</div>
<%@ include file="doctorHelpModal.jsp" %>		
<%@ include file="aiRecommendModal.jsp" %>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>