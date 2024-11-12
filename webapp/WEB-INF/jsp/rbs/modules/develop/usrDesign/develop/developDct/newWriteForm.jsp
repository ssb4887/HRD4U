<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_developInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/develop/input.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
<% pageContext.setAttribute("newline","\r\n"); %>

	<div id="cms_board_article" class="contents-wrapper">
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
		<input type="hidden" value="${PRTBIZ_IDX }" name="prtbizIdx">
		<input type="hidden" value="${corpInfo.BPL_NO }" name="bplNo">
		<input type="hidden" value="${devlopList.DEVLOP_IDX }" name="devlopIdx">
		<input type="hidden" value="new" name="inputType">
		
		<div class="contents-area mt50">
		<div class="title-wrapper clear mb0">
			<h3 class="title-type01 ml0 mt5">훈련과정 표준개발 신청서</h3>
		</div>		
		<div class="contents-box pl0">
			<div class="title-wrapper clear">
				<h3 class="title-type02 ml0 mt5 mb0 fl">기본정보</h3>	
				<div class="fr">
					<a href="#none" class="btn-m01 btn-color01 depth3" id="open-modal02">AI추천</a>	
					<!-- 기업HRD이음컨설팅IDX가 있을 때(기업HRD이음컨설팅을 완료했을 때)만 기업HRD이음컨설팅보고서 버튼 보여줌 -->
					<c:if test="${bsisCnsl ne '0'}">
					<a href="${contextPath}/${crtSiteId}/bsisCnsl/consultingClipReport.do?mId=37&bsiscnslIdx=${bsisCnsl}" class="btn-m01 btn-color01 depth3" target="_blank">기업HRD이음컨설팅보고서</a>                                                
					</c:if>
				</div>	
			</div>
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
                    		<td>${corpInfo.BPL_NM }</td>
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
                    		<td class="left"><itui:objectSelectClass itemId="sido" objStyle="width:49%; font-size:15px;" itemInfo="${itemInfo}" objVal="${devlopList.TR_OPRTN_GUGUN_CD }"/></td>
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
                    		<th scope="row">훈련과정명&nbsp;<strong class="point-important">*</strong></th>
                    		<td colspan="3"><itui:objectText itemId="tpNm" itemInfo="${itemInfo}" objVal="${tpList.TP_NAME}" objClass="w100"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">NCS 적용여부</th>
                    		<td class="left" colspan="3">아니오</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">NCS 분류&nbsp;<strong class="point-important">*</strong></th>
							<td class="left" colspan="3">									
								<itui:objectSelectClassCustom itemId="ncsSclasCode" itemInfo="${itemInfo}" optnHashMap="${optnHashMap}" objStyle="width:32%; font-size:15px;" objClass="three-select" objClass2="select-last"/>											
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
                    		<td class="left"><input type="hidden" value="${PRTBIZ_IDX }" name="trBiz">${BIZ_TYPE2}</td>
                    		<th scope="row">훈련유형</th>
                    		<td class="left"><input type="hidden" value="${PRTBIZ_IDX }" name="trType">${TRAINING_TYPE}</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">훈련방법</th>
                    		<td class="left"><input type="hidden" value="${TRAINING_METHOD}" name="trMth">${TRAINING_METHOD}</td>
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
                    		<th scope="row">훈련일수(일)&nbsp;<strong class="point-important">*</strong></th>
                    		<th scope="row">훈련시간(시간)&nbsp;<strong class="point-important">*</strong></th>
                    		<th scope="row">기숙사제공여부</th>
                    	</tr>        
                    	<tr>
                    		<th scope="row">훈련일수(시간)</th>
                    		<td>${TRAINING_METHOD}</td>
                    		<td><itui:objectText itemId="trDayCnt" itemInfo="${itemInfo}" objVal="${tpList.TR_DAYCNT }" objClass="w100 onlyNum"/></td>
                    		<td><itui:objectText itemId="trTm" itemInfo="${itemInfo}" objVal="${tpList.TRTM }" objClass="w100 onlyNum readonly"/></td>
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
	                        <col style="width: auto">
	                        <col style="width: auto">
	                        <col style="width: auto">
	                    </colgroup>
	                    <tbody>
	                        <tr>
	                            <th scope="row" colspan="4">훈련 상세정보</th>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련목표&nbsp;<strong class="point-important">*</strong></th>
	                            <td colspan="3" class="left">
									<c:set var="array1" value="${fn:replace(tpList.TR_GOAL,'<br>',newline)}"/>
									<c:set var="array2" value="${fn:replace(array1,'<p>','')}"/>
									<c:set var="array3" value="${fn:replace(array2,'</p>',newline)}"/>
									<textarea name="trGoal" id="trGoal" class="w100"><c:out value="${fn:replace(array3,'<br>',newline)}"></c:out></textarea>
									
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">주요 훈련내용&nbsp;<strong class="point-important">*</strong></th>
	                            <td colspan="3" class="left">
									<c:set var="array1" value="${fn:replace(tpList.TR_SFE,'<br>',newline)}"/>
									<c:set var="array2" value="${fn:replace(array1,'<p>','')}"/>
									<c:set var="array3" value="${fn:replace(array2,'</p>',newline)}"/>
									<textarea name="trSfe" id="trSfe" class="w100"><c:out value="${fn:replace(array3,'<br>',newline)}"></c:out></textarea>
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련대상요건&nbsp;<strong class="point-important">*</strong></th>
	                            <td colspan="3" class="left">
                         			<c:set var="array1" value="${fn:replace(tpList.TRNREQM,'<br>',newline)}"/>
									<c:set var="array2" value="${fn:replace(array1,'<p>','')}"/>
									<c:set var="array3" value="${fn:replace(array2,'</p>',newline)}"/>
									<textarea name="trnReqm" id="trnReqm" class="w100"><c:out value="${fn:replace(array3,'<br>',newline)}"></c:out></textarea>	                            
	                            </td>
	                        </tr>
	                    </tbody>
	                </table>
           			<div class="one-box">
						<dl class="board-write-contents">
							<dd><itui:additionalTpSub objDt="${objDt}" objVal="${submitType}" itemInfo="${itemInfo}" pageContext="${pageContext}"/></dd>
						</dl>
					</div>	                
	            </div>
	        </div>
	    </div>
		<div class="btns-area">
			<div class="btns-right">
				<button type="submit" class="btn-m01 btn-color02 depth2 fn_btn_submit" data-status="<%= ConfirmProgress.CONSIDERREQUEST.getCode()%>">검토요청</button>
				<a href="#" title="이전" class="btn-m01 btn-color01 depth2 fn_btn_write" onclick="confirm('이전화면으로 이동하시겠습니까? \n 작성한 내용은 저장되지 않습니다.') ? history.back() : false;">이전</a>
				<%-- <a href="<c:out value="${DEVELOP_TRAINING_LIST_FORM_URL}&bplNo=${corpInfo.BPL_NO}&devlopIdx=${devlopIdx}"/>" title="목록" class="btn-m01 btn-color03 depth2 fn_btn_write" onclick="return confirm('목록으로 이동하시겠습니까? \n작성한 내용은 저장되지 않습니다.')">목록</a> --%>
			</div>
		</div>
		</form>
		</div>
<%@ include file="aiRecommendModal.jsp" %>			
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>