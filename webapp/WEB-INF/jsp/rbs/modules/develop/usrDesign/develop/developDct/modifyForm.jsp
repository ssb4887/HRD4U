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
                    		<td><c:out value="${corpInfo.BPL_NM }"/></td>
                    		<th scope="row">사업장관리번호</th>
                    		<td class="left"><c:out value="${corpInfo.BPL_NO }"/></td>
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
                    					<option value="${instt.INSTT_IDX}" data-block="${instt.BLOCK_CD}" <c:if test="${devlopList.TR_OPRTN_GUGUN_CD eq instt.BLOCK_CD}">selected</c:if>><c:out value="${instt.INSTT_NAME}"/></option>
                    				</c:forEach>
                    			</select>
                    		</td>
                    	</tr>    
                    	<tr>
                    		<th scope="col" colspan="4">훈련과정 신청내용</th>
                    	</tr>                     	                	                   	
                    	<tr>
                    		<th scope="row">훈련과정명&nbsp;<strong class="point-important">*</strong></th>
                    		<td colspan="3"><itui:objectText itemId="tpNm" itemInfo="${itemInfo}" objVal="${tpList.TP_NM}" objClass="w100"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">NCS 적용여부</th>
                    		<td class="left" colspan="3">아니오</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">NCS 분류</th>
                    		<td class="left" colspan="3">
								<itui:objectSelectClassCustom itemId="ncsSclasCode" itemInfo="${itemInfo}" optnHashMap="${optnHashMap}" objVal="${tpList.NCS_SCLAS_CD}" objStyle="width:32%; font-size:15px;" objClass="three-select" objClass2="select-last"/>											
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
                    		<input type="hidden" value="${tpList.TR_BIZ }" name="trBiz">${tpList.BIZ_TYPE2}
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
                        <col width="auto">
                        <col width="auto">
                        <col width="auto">
                        <col width="auto">
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
                    		<td><c:out value="${tpList.TR_MTH}"/></td>
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
                            <col style="width: auto">
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
	                            <th scope="row">훈련목표&nbsp;<strong class="point-important">*</strong></th>
	                            <td colspan="3" class="left">
	                            	<c:set var="array1" value="${fn:replace(tpList.TR_GOAL,'<br>',newline)}"/>
									<c:set var="array2" value="${fn:replace(array1,'<p>',space)}"/>	                            
									<textarea name="trGoal" class="w100 h200"><c:out value="${fn:replace(array2,'</p>',newline)}"></c:out></textarea>
									
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">주요 훈련내용&nbsp;<strong class="point-important">*</strong></th>
	                            <td colspan="3" class="left">
	                            	<c:set var="array1" value="${fn:replace(tpList.TR_SFE,'<br>',newline)}"/>
									<c:set var="array2" value="${fn:replace(array1,'<p>',space)}"/>
									<textarea name="trSfe" class="w100 h200"><c:out value="${fn:replace(array2,'</p>',newline)}"></c:out></textarea>
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련대상요건&nbsp;<strong class="point-important">*</strong></th>
	                            <td colspan="3" class="left">
	                            	<c:set var="array1" value="${fn:replace(tpList.TRNREQM,'<br>',newline)}"/>
									<c:set var="array2" value="${fn:replace(array1,'<p>',space)}"/>	 	                            
									<textarea name="trnReqm" class="w100 h200"><c:out value="${fn:replace(array2,'</p>',newline)}"></c:out></textarea>	                            
	                            </td>
	                        </tr>
	                    </tbody>
	                </table>
           			<div class="one-box">
						<dl class="board-write-contents">							
							<dd><itui:additionalTpSub objDt="${objDt}" objVal="${submitType}" itemInfo="${itemInfo}" content="${contents}"/></dd>
						</dl>
					</div>	                
	            </div>
	        </div>
	    </div>
<%-- 	    <c:if test="${devlopList.CONFM_STATUS ne '33' && devlopList.CONFM_STATUS ne '37'}"> --%>
<!-- 		<div class="contents-box pl0"> -->
<!-- 			<div class="table-type02 horizontal-scroll"> -->
<!-- 				<table class="width-type02"> -->
<!-- 					<caption>능력개발클리닉 참여 신청서에 대한 주치의 의견</caption> -->
<!--             		<colgroup> -->
<!--                         <col width="15%"> -->
<!--                         <col width="auto"> -->
<!--                     </colgroup> -->
<!--                     <tbody> -->
<!--                     	<tr> -->
<!--                     		<th scope="row">주치의 의견</th> -->
<%--                     		<td class="left">${devlopList.DOCTOR_OPINION}</td> --%>
<!--                     	</tr> -->
<!--                     </tbody> -->
<!-- 				</table> -->
<!-- 			</div> -->
<!-- 		</div> -->
<%-- 		</c:if> --%>
		<c:if test="${devlopList.CONFM_STATUS eq '37'}">
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>주치의가 작성한 표준개발 신청서에 대한 기업 의견 : 기업의견 정보 제공표</caption>
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
				<button type="submit" class="btn-m01 btn-color03 depth2 fn_btn_modify" data-status="<%= ConfirmProgress.CONSIDERREQUEST.getCode()%>">검토요청</button>
				<a href="<c:out value="${DEVELOP_LIST_FORM_URL}"/>" title="목록" class="btn-m01 btn-color01 depth2 fn_btn_write" onclick="return confirm('목록으로 이동하시겠습니까? \n 작성한 내용은 저장되지 않습니다.')">목록</a>
			</div>
		</div>
		</form>
		</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>