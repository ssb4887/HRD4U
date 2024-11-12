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

<jsp:scriptlet>pageContext.setAttribute("newLine", "\n");</jsp:scriptlet>
<script defer type="text/javascript" src="${contextPath }<c:out value="${jsPath}/report.js"/>"></script>
<style>
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
	
	@media print {
	* {
		-webkit-print-color-adjust: exact !important;
	}
	body {
		width: 210mm;
		height: 297mm;
		margin: 0;
		padding: 20mm;
	}
	@page :first {
		size: A4;
		margin: 0;
	}
	@page {
		size: A4;
		margin: 20mm 0;
	}
	table {
		width: 100%;
	}
	.page-avoid {
		page-break-inside: avoid;
	}
	.page-start {
		page-break-before: always;
	}
	tbody tr {
		page-break-inside: avoid;
	}
	.exclude-from-print, .sub-visual, header, .contents-title, footer,
		.contents-navigation-wrapper {
		display: none;
	}
	.wrapper, .container {
		padding-top: 0;
	}
	.hidden {
		display: block;
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
}
</style>
<div id="overlay"></div>
<div class="loader"></div>
<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<form id="${inputFormId}" name="${inputFormId}" method="post" action="${contextPath}/web/report/inputProc.do?mId=100<c:out value="${modify}"/>" target="submit_target" enctype="multipart/form-data">
	<input type="hidden" name="cnslIdx" value="<c:out value="${cnsl.cnslIdx}"/>" />
	<input type="hidden" name="bplNo" value="<c:out value="${cnsl.bplNo}"/>" />
	<input type="hidden" name="reprtIdx" value="<c:out value="${dt.REPRT_IDX}"/>" />
	<input type="hidden" name="prtbizIdx" value="<c:out value="${cnsl.cnslType eq '1' ? '1' : cnsl.cnslType eq '2' ? '4' : '1'}"/>" />
	<input type="hidden" name="trType" id="id_trType" value="<c:out value="${not empty tpList ? tpList.TR_TYPE : cnsl.cnslType}"/>" />
	<input type="hidden" name="trMth" id="id_trMth" value="<c:out value="${not empty tpList ? tpList.TR_MTH : cnsl.cnslType}"/>" />
	<div class="page" id="page1">
	<div class="contents-area">
		<h3 class="title-type01 ml0">보고서 작성</h3>
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
                    			관할 지부지사&nbsp;<strong class="point-important">*</strong>
                    		</th>
                    		<td class="left">
                    			<span id="insttName">
                    				<c:out value="${cnsl.cmptncBrffcNm}" />
                    			</span>
                    		</td>
                    	</tr>    
                    	<tr>
                    		<th scope="col" colspan="4">훈련과정 신청내용</th>
                    	</tr>                     	                	                   	
                    	<tr>
                    		<th scope="row">훈련과정명&nbsp;<strong class="point-important">*</strong></th>
                    		<td colspan="3">
                    			<itui:objectView itemId="tpNm" itemInfo="${itemInfo}" objDt="${dt}" />
                    		</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">NCS 적용여부</th>
                    		<td class="left" colspan="3">아니오</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">NCS 분류</th>
							<td class="left" colspan="3">	
								<itui:objectView itemId="ncsSclasCode" itemInfo="${itemInfo}" optnHashMap="${optnHashMap}" objVal="${dt.NCS_SCLAS_CD }"/>(${dt.NCS_SCLAS_CD})
							</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">과정담당자명&nbsp;<strong class="point-important">*</strong></th>
                    		<td>
                    			<itui:objectView itemId="tpPicName" itemInfo="${itemInfo}" objDt="${dt}" />
                    		</td>
                    		<th scope="row">과정담당자 전화번호<strong class="point-important">*</strong></th>
                    		<td>
                    			<itui:objectView itemId="tpPicTelNo" itemInfo="${itemInfo}" objDt="${dt}" />
                    		</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">훈련사업</th>
                    		<td class="left">
								<c:out value="${dt.TR_BIZ eq '1' ? '사업주' : dt.TR_BIZ eq '2' ? 'S-OJT' : ''}" />
                    		</td>
                    		<th scope="row">훈련유형</th>
                    		<td class="left" id="trainingType">
                    			<c:out value="${dt.cnslType eq '1' ? '사업주' : cnsl.cnslType eq '2' ? '일반' : ''}" />
                    		</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">훈련방법</th>
                    		<td class="left trainingMethod">
                    			<c:out value="${cnsl.cnslType eq '1' ? '집체훈련' : cnsl.cnslType eq '2' ? 'S-OJT훈련' : ''}" />	
                    		</td>
                    		<th scope="row">훈련대상</th>
                    		<td class="left">
                    			<c:out value="${dt.TR_TRGET eq '1' ? '자체' : dt.TR_TRGET eq '2' ? '재직자' : ''}" />
                    		</td>
                    	</tr>
                    	<tr>
                    		<th scope="row">법정훈련 구분</th>
                    		<td class="left">
                    			해당없음
                    		</td>
                    		<th scope="row">고숙련기술 과정구분</th>
                    		<td class="left">
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
                    			<c:out value="${cnsl.cnslType eq '1' ? '집체훈련' : cnsl.cnslType eq '2' ? 'S-OJT훈련' : ''}" />
                    		</td>
                    		<td>
                    			<itui:objectView itemId="trDayCnt" itemInfo="${itemInfo}" objDt="${dt}" />일
                    		</td>
                    		<td>
                    			<itui:objectView itemId="trTm" itemInfo="${itemInfo}" objDt="${dt}" />시간
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
                                	<itui:objectView itemId="clasNmpr" itemInfo="${itemInfo}" objDt="${dt}" />
                                </td>                                
                                <td>
                                	<itui:objectView itemId="clasCnt" itemInfo="${itemInfo}" objDt="${dt}" />
                                </td>
                                <td>
                                	QR코드
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="page hidden page-start" id="page2">
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
	                            	<c:out value="${dt.TR_SFE}" />
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련목표&nbsp;<strong class="point-important">*</strong></th>
	                            <td colspan="4" class="left">
									<c:out value="${dt.TR_GOAL}" />
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">기대효과&nbsp;<strong class="point-important">*</strong></th>
	                            <td colspan="4" class="left">
	                            	<c:out value="${dt.XPTEFFECT}" />
	                            </td>
	                        </tr>
	                        <tr>
	                            <th scope="row">훈련대상요건&nbsp;<strong class="point-important">*</strong></th>
	                            <td colspan="4" class="left">
	                            	<c:out value="${dt.TRNREQM}" />
	                            </td>
	                        </tr>
	                        <tr>
                    		<th scope="row">훈련내용 </th>
                    		<td colspan="4">
                    			<table>
                    				<colgroup>
				                        <col width="5%">
				                        <col width="">
				                        <col width="10%">
				                        <col width="40%">
				                        <col width="">
				                    </colgroup>
				                    <tbody>
				                 		<tr>
				                    		<th>번호</th>
				                    		<th>교과목</th>
				                    		<th>시간</th>
				                    		<th>내용</th>
				                    		<th>교수 기법</th>
				                    	</tr>
				                        <c:forEach items="${tpSubList }" varStatus="i">
			                     		<tr>
				                    		<td>
				                    			<c:out value="${tpSubList[i.index].COURSE_NO }" />
				                    		</td>
				                    		<td>
				                    			<c:out value="${tpSubList[i.index].COURSE_NAME }" />
				                    		</td>
				                    		<td>
				                    			<c:out value="${tpSubList[i.index].TRTM }" />시간
				                    		</td>
				                    		<td>
				                    			<c:out value="${tpSubList[i.index].TR_CN }" />
				                    		</td>
				                    		<td>
				                    			<c:out value="${tpSubList[i.index].TCHMETHOD }" />
				                    		</td>
				                    	</tr>
				                    	</c:forEach>
				                    </tbody>
				        		</table>
				        	</td>
				        </tr>
					</tbody>
                </table>                
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
									<c:out value="${dt.DOCTOR_OPINION}"></c:out>
	                    		</td>
	                    	</tr>
						</tbody>
					</table>   	         		
	            </div>
	        </div>
	    </div>
    </div>
	</form>
</div>

<!-- //CMS 끝 -->
<div class="fr mt50 exclude-from-print">
	<c:if test="${loginVO.usertypeIdx eq 5 and progress.CONFM_STATUS eq '10'}">
		<button type="button" class="btn-m01 btn-color03 depth2 fn_btn_submit" onclick="approve(50);">승인</button>
		<button type="button" class="btn-m01 btn-color04 depth2 fn_btn_submit" onclick="openReject('openReject')">반려</button>
	</c:if>
	<a href="javascript:void(0);" class="btn-m01 btn-color01" onclick="history.go(-1);">목록</a>
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
           					<textarea id="id_confmCn" name="confmCn" rows="4" title="반려 의견" placeholder="의견을 입력하세요"></textarea>
           				</dd>
           			</dl>
           		</div>
           </div>
           <div class="btns-area">
			<button type="button" id="id_progress" name="progress" onclick="reject();" class="btn-m02 btn-color03 three-select codeLevel" data-status="<%= ConfirmProgress.RETURN.getCode()%>" value="<%= ConfirmProgress.RETURN.getCode()%>">
				<span title="반려">
					반려
				</span>
			</button>
			<button type="button" id="closeBtn_05" onclick="closeReject('openReject')" class="btn-m02 btn-color02 three-select">
				<span title="취소">
					취소
				</span>
			</button>
			</div>
		</div>
	</div>
</div>

<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>