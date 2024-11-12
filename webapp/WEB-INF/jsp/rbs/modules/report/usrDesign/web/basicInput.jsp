<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="inputFormId" value="fn_sampleInputForm" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}" />
		<jsp:param name="javascript_page" value="${moduleJspRPath}/input.jsp" />
		<jsp:param name="inputFormId" value="${inputFormId}" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />
<style>
	.modal-wrapper {
		width: 1000px;
		top: 40%;
		left: 40%;
	}
	
	.btns-area {
		text-align: right;
		margin-top: 20px;
	}
	
	#id_tpCd {
		width: 65%;
		margin-right: 10px;
	}
	
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
</style>
<jsp:scriptlet>pageContext.setAttribute("newLine", "\n");</jsp:scriptlet>
<script defer type="text/javascript" src="${contextPath }<c:out value="${jsPath}/report.js"/>"></script>
<div id="overlay"></div>
<div class="loader"></div>
<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<form id="${inputFormId}" name="${inputFormId}" method="post" action="${contextPath}/web/report/inputProc.do?mId=100<c:out value="${modify}"/>" target="submit_target" enctype="multipart/form-data">
	<input type="hidden" name="cnslIdx" value="<c:out value="${cnsl.cnslIdx}"/>" />
	<input type="hidden" name="bplNo" value="<c:out value="${cnsl.bplNo}"/>" />
	<c:if test="${not empty tpList}"><input type="hidden" name="reprtIdx" value="<c:out value="${tpList.REPRT_IDX}"/>" /></c:if>
	<input type="hidden" name="prtbizIdx" value="<c:out value="${cnsl.cnslType eq '1' ? '1' : cnsl.cnslType eq '2' ? '4' : '1'}"/>" />
	<input type="hidden" name="trType" id="id_trType" value="<c:out value="${not empty tpList ? tpList.TR_TYPE : cnsl.cnslType}"/>" />
	<input type="hidden" name="trMth" id="id_trMth" value="<c:out value="${not empty tpList ? tpList.TR_MTH : cnsl.cnslType}"/>" />
	<div class="contents-area">
		<h3 class="title-type01 ml0"><c:out value="${cnsl.cnslType eq '1' ? '사업주' : '일반직무전수 OJT'}"/> 컨설팅 보고서 작성</h3>
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
                    		<td>
                    			<c:out value="${cnsl.corpNm}" />
                    		</td>
                    		<th scope="row">사업장관리번호</th>
                    		<td class="left">
                    			<c:out value="${cnsl.bplNo}" />
                    		</td>
                    	</tr>
                    	<tr>
                    		<th scope="col" colspan="4">훈련실시 지역정보</th>
                    	</tr>     
                    	<tr>
                    		<th scope="row">훈련실시 주소</th>
                    		<td class="left">
                    			<c:out value="${cnsl.trOprtnRegionAddr}" /><br/>
                    			<c:out value="${cnsl.trOprtnRegionAddrDtl}" />
                    		</td>
                    		<th scope="row">
                    			관할 지부지사
                    		</th>
                    		<td class="left">
                    			<span id="insttName">
                    				<c:out value="${cnsl.cmptncBrffcNm}" />
                    				<input type="hidden" name="brffcCd" value="<c:out value="${cnsl.cmptncBrffcPicIdx}"/>"/>
                    			</span>
                    		</td>
                    	</tr>    
                    	<tr>
                    		<th scope="col" colspan="4">훈련과정 신청내용</th>
                    	</tr>                     	                	                   	
                    	<tr>
                    		<th scope="row">훈련과정명&nbsp;<strong class="point-important">*</strong></th>
                    		<td colspan="3">
                    			<itui:objectText itemId="tpNm" itemInfo="${itemInfo}" objVal="${tpList.TP_NM}" objClass="w100"/>
                    		</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">NCS 적용여부</th>
                    		<td class="left" colspan="3">아니오</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">NCS 분류</th>
							<td class="left" colspan="3">	
								<itui:objectSelectClassConsult itemId="ncsSclasCode" itemInfo="${itemInfo}" optnHashMap="${optnHashMap}" objVal="${tpList.NCS_SCLAS_CD}" objStyle="width:32%; font-size:15px;" objClass="three-select" objClass2="select-last"/>
							</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">과정담당자명&nbsp;<strong class="point-important">*</strong></th>
                    		<td>
                    			<itui:objectText itemId="tpPicName" itemInfo="${itemInfo}" objVal="${tpList.TP_PIC_NAME}" objClass="w100"/>
                    		</td>
                    		<th scope="row">과정담당자 전화번호&nbsp;<strong class="point-important">*</strong></th>
                    		<td>
                    			<input type="text" id="id_tpPicTelNo" name="tpPicTelNo" title="훈련과정담당자 전화번호" class="inputTxt w100 onlyNumDash" maxlength="30" value="<c:out value="${tpList.DECTEL}" />"> 
                    		</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">훈련사업</th>
                    		<td class="left">
                    			<div class="input-radio-wrapper center">
                   					<div class="input-radio-area center">
										<input type="radio" class="radio-type01" id="id_trBiz1" name="trBiz" value="1" onclick="trBiz1();" <c:if test="${not empty tpList && tpList.TR_BIZ eq '1'}">checked</c:if> <c:if test="${empty tpList && cnsl.cnslType eq '1'}">checked</c:if>> 
										<label for="id_trBiz1">사업주</label>
									</div>
									<div class="input-radio-area center">
										<input type="radio" class="radio-type01" id="id_trBiz2" name="trBiz" value="2" onclick="trBiz2();" <c:if test="${not empty tpList && tpList.TR_BIZ eq '2'}">checked</c:if> <c:if test="${empty tpList && cnsl.cnslType eq '2'}">checked</c:if>> 
										<label for="id_trBiz2">S-OJT</label>
									</div>
                    			</div>
                    		</td>
                    		<th scope="row">훈련유형</th>
                    		<td class="left" id="trainingType">
                    			<c:out value="${cnsl.cnslType eq '1' ? '사업주' : cnsl.cnslType eq '2' ? '일반' : ''}" />
                    		</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">훈련방법</th>
                    		<td class="left trainingMethod">
                    			<c:out value="${cnsl.cnslType eq '1' ? '집체훈련' : cnsl.cnslType eq '2' ? 'S-OJT훈련' : ''}" />	
                    		</td>
                    		<th scope="row">훈련대상</th>
                    		<td class="left">
                    			<div class="input-radio-wrapper center">
                   					<div class="input-radio-area center">
										<input type="radio" class="radio-type01" id="id_trTrget1" name="trTrget" value="1" <c:if test="${tpList.TR_TRGET eq '1'}">checked</c:if>> 
										<label for="id_trTrget1">자체</label>
									</div>
									<div class="input-radio-area center">
										<input type="radio" class="radio-type01" id="id_trTrget2" name="trTrget" value="2" <c:if test="${tpList.TR_TRGET eq '2'}">checked</c:if>> 
										<label for="id_trTrget2">재직자</label>
									</div>
                    			</div>
                    		</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">법정훈련 구분</th>
                    		<td class="left">
                    			<input type="hidden" value="N" name="legalTrType" />해당없음
                    		</td>
                    		<th scope="row">고숙련기술 과정구분</th>
                    		<td class="left">
                    			<input type="hidden" value="N" name="hghExprtNtcnqCrseType">해당없음(일반)
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
                        <col width="20%">
                        <col width="20%">
                        <col width="auto">
                    </colgroup>
                    <tbody>
                    	<tr>
                    		<th scope="row">구분</th>
                    		<th scope="row">훈련방법</th>
                    		<th scope="row">훈련일수(일)&nbsp;<strong class="point-important">*</strong></th>
                    		<th scope="row">훈련시간(시간)</th>
                    		<th scope="row">기숙사제공여부</th>
                    	</tr>        
                    	<tr>
                    		<th scope="row">훈련일수(시간)</th>
                    		<td class="trainingMethod">
                    			
                    		</td>
                    		<td>
                    			<itui:objectText itemId="trDayCnt" itemInfo="${itemInfo}" objVal="${tpList.TR_DAYCNT}" objClass="w100 onlyNum"/>
                    		</td>
                    		<td>
                    			<itui:objectText itemId="trTm" itemInfo="${itemInfo}" objVal="${tpList.TRTM}" objClass="w100 onlyNum"/>
                    		</td>
                    		<td>
                    			아니오
                    		</td>
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
                        		<th>학급정원&nbsp;<strong class="point-important">*</strong></th>
                        		<th>학급수&nbsp;<strong class="point-important">*</strong></th>
                        		<th>훈련생 출결방법</th>
                        	</tr>
                            <tr>
                                <th scope="row">학급정원(학급수)</th>
                                <td>
                                	<itui:objectText itemId="clasNmpr" itemInfo="${itemInfo}" objVal="${tpList.CLAS_NMPR}" objClass="w100 onlyNum"/>
                                </td>                                
                                <td>
                                	<itui:objectText itemId="clasCnt" itemInfo="${itemInfo}" objVal="${tpList.CLAS_CNT}" objClass="w100 onlyNum"/>
                                </td>
                                <td>
                                	<input type="hidden" value="QR" name="trneeAtabManageMth" />QR코드
                                </td>
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
	                            <th scope="row" colspan="5">훈련 상세정보</th>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련특징&nbsp;<strong class="point-important">*</strong></th>
	                            <td colspan="4" class="left">
									<c:set var="array1" value="${fn:replace(tpList.TR_SFE,'<br>',newline)}"/>
									<c:set var="array2" value="${fn:replace(array1,'<p>','')}"/>
									<c:set var="array3" value="${fn:replace(array2,'</p>',newline)}"/>
									<textarea id="id_trSfe" name="trSfe" class="w100 h200"><c:out value="${fn:replace(array3,'<br>',newline)}"></c:out></textarea>
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련목표&nbsp;<strong class="point-important">*</strong></th>
	                            <td colspan="4" class="left">
									<c:set var="array1" value="${fn:replace(tpList.TR_GOAL,'<br>',newline)}"/>
									<c:set var="array2" value="${fn:replace(array1,'<p>','')}"/>
									<c:set var="array3" value="${fn:replace(array2,'</p>',newline)}"/>
									<textarea id="id_trGoal" name="trGoal" class="w100 h200"><c:out value="${fn:replace(array3,'<br>',newline)}"></c:out></textarea>
									
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">기대효과&nbsp;<strong class="point-important">*</strong></th>
	                            <td colspan="4" class="left">
									<c:set var="array1" value="${fn:replace(tpList.XPTEFFECT,'<br>',newline)}"/>
									<c:set var="array2" value="${fn:replace(array1,'<p>','')}"/>
									<c:set var="array3" value="${fn:replace(array2,'</p>',newline)}"/>
									<textarea id="id_xptEffect" name="xptEffect" class="w100 h200"><c:out value="${fn:replace(array3,'<br>',newline)}"></c:out></textarea>
	                            
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련대상요건&nbsp;<strong class="point-important">*</strong></th>
	                            <td colspan="4" class="left">
                         			<c:set var="array1" value="${fn:replace(tpList.TRNREQM,'<br>',newline)}"/>
									<c:set var="array2" value="${fn:replace(array1,'<p>','')}"/>
									<c:set var="array3" value="${fn:replace(array2,'</p>',newline)}"/>
									<textarea id="id_trnReqm" name="trnReqm" class="w100 h200"><c:out value="${fn:replace(array3,'<br>',newline)}"></c:out></textarea>	                            
	                            </td>
	                        </tr>
	                    </tbody>
	                </table>
           			<div class="one-box">
						<dl class="board-write-contents">
							<dd>
								<itui:addCnslTpSub objDt="${objDt}" objVal="${submitType}" itemInfo="${itemInfo}" content="${contents}"/>
							</dd>
						</dl>
					</div>	                
	            </div>
	        </div>
	    </div>
	    
	    <div class="contents-area">
	        <div class="contents-box pl0">
	            <h3 class="title-type02 ml0">주치의 의견</h3>
            	<div class="table-type02 horizontal-scroll">
           			<table>
	                    <colgroup>
	                        <col style="width: 15%">
	                        <col style="width: auto">
	                    </colgroup>
	                    <tbody>
	                    	<tr>
	                    		<th scope="row">주치의 의견</th>
	                    		<td>
			            			<c:set var="array1" value="${fn:replace(tpList.DOCTOR_OPINION,'<br>',newline)}"/>
									<c:set var="array2" value="${fn:replace(array1,'<p>','')}"/>
									<c:set var="array3" value="${fn:replace(array2,'</p>',newline)}"/>
									<textarea id="id_doctorOpinion" name="doctorOpinion" class="w100 h200"><c:out value="${fn:replace(array3,'<br>',newline)}"></c:out></textarea>
	                    		</td>
	                    	</tr>
						</tbody>
					</table>   	         		
	            </div>
	        </div>
	    </div>
		
		<div class="btns-area">
			<div class="btns-right">
				<c:if test="${progress eq '10' && loginVO.usertypeIdx eq '5'}">
					<button type="button" class="btn-m01 btn-color03 depth2 fn_btn_submit" onclick="approve();">승인</button>
					<button type="button" class="btn-m01 btn-color02 depth2 fn_btn_submit" onclick="openReject('openReject')">반려</button>
				</c:if>
				<c:if test="${empty tpList || (progress eq '5' && loginVO.usertypeIdx eq '10') || (progress eq '40' && loginVO.usertypeIdx eq '10')}">
				<button type="button" onclick="save(5);" class="btn-m01 btn-color02 depth2 fn_btn_submit" data-status="<%= ConfirmProgress.TEMPSAVE.getCode()%>" value="<%= ConfirmProgress.TEMPSAVE.getCode()%>" <c:if test="${progress eq '10'}">style="display: none;"</c:if>>임시저장</button>
				<button type="button" onclick="save(10);" class="btn-m01 btn-color03 depth2 fn_btn_submit" data-status="<%= ConfirmProgress.APPLY.getCode()%>" value="<%= ConfirmProgress.APPLY.getCode()%>" <c:if test="${progress eq '10'}">style="display: none;"</c:if>>제출</button>
				</c:if>
				<a href="${contextPath}/web/consulting/viewAll.do?mId=95&bplNo=${param.bplNo}&cnslIdx=${param.cnslIdx}#tab4" title="목록" class="btn-m01 btn-color01 depth2 fn_btn_write">목록</a>
			</div>
		</div>
	</form>
	</div>
	
<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="openReject">
	<h2>
    	반려의견 등록
    </h2>
	<div class="modal-area">
    	<div class="contents-box pl0">
           	<div class="basic-search-wrapper">
           		<div class="one-box">
           			<dl>
           				<dt>
							<label>
								의견
							</label>
           				</dt>
           				<dd>
           					<textarea id="id_confmCn" name="confmCn" rows="4" placeholder="의견을 입력하세요"></textarea>
           				</dd>
           			</dl>
           		</div>
           </div>
           <div class="btns-area">
			<button type="button" id="id_progress" name="progress" onclick="reject();" class="btn-m02 btn-color03 three-select codeLevel" data-status="<%= ConfirmProgress.RETURN.getCode()%>" value="<%= ConfirmProgress.RETURN.getCode()%>">
				<span>
					반려
				</span>
			</button>
			<button type="button" id="closeBtn_05" onclick="closeReject('openReject')" class="btn-m02 btn-color02 three-select">
				<span>
					취소
				</span>
			</button>
			</div>
		</div>
	</div>
</div>
	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>




