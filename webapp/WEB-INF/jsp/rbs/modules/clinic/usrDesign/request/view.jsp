<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/clinic/request/view.jsp"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
<div class="contents-wrapper">
	<div class="contents-area">
		<!-- 클리닉 지부지사 setting -->
		<c:set var="itemId" value="sido"/>
		<c:set var="itemObj" value="${itemInfo.items[itemId]}"/>
		<c:set var="classMasterCode" value="_class_${itemObj['master_code']}"/>
		<c:set var="optnOptList" value="${optnHashMap[classMasterCode]}"/>
		<select name="sido" id="sido" style="display:none;">
			<c:forEach var="optnDt" items="${optnOptList}" varStatus="i">
				<option value="<c:out value="${optnDt.OPTION_CODE}"/>" data-pcode="${optnDt.PARENT_OPTION_CODE}" data-level="${optnDt.OPTION_LEVEL}"><c:out value="${optnDt.OPTION_NAME}"/></option>
			</c:forEach>
		</select>
		<h3 class="title-type01 ml0">능력개발클리닉 참여 신청서</h3>
		<div class="title-wrapper">
			<p class="state fl">신청상태 <strong>${confirmProgressMap[dt.CONFM_STATUS]}</strong></p>
		</div>
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
            	<table class="width-type02">
            		<caption>능력개발클리닉 신청 목록표 : No, 기업명, 주치의 변경, 소속기관, 신청 상태, 신청일자에 관한 정보 제공표</caption>
            		<colgroup>
                        <col width="15%">
                        <col width="auto">
                        <col width="15%">
                        <col width="auto">
                    </colgroup>
                    <thead>
                    	<tr>
                    		<th scope="col" colspan="4">1. 신청 기업 현황</th>
                    	</tr>
                    </thead>
                    <tbody>
                    	<tr>
                    		<th scope="row">기업명</th>
                    		<td class="left"><c:out value="${corpInfo.BPL_NM}"/></td>
                    		<th scope="row">대표자명</th>
                    		<td class="left"><itui:objectViewCustom itemId="reperNm" itemInfo="${itemInfo}" /></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">사업자등록번호</th>
                    		<td class="left"><c:out value="${corpInfo.BIZR_NO}"/></td>
                    		<th scope="row">고용보험관리번호</th>
                    		<td class="left"><c:out value="${corpInfo.BPL_NO}"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">기업유형</th>
                    		<td class="left">
                    			<!-- 1: 개인 2: 법인 -->
								<itui:objectViewCustom itemId="corpType" itemInfo="${itemInfo}"/>
                    		</td>
                    		<th scope="row">상시근로자 수(명)</th>
                    		<td class="left"><itui:objectViewCustom itemId="totWorkCnt" itemInfo="${itemInfo}"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">업종<br>(주업종/기타업종)</th>
                    		<td colspan="3" class="left"><itui:objectViewCustom itemId="indutyNm" itemInfo="${itemInfo}"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">주소</th>
                    		<td colspan="3" class="left"><itui:objectViewCustom itemId="bplAddr" itemInfo="${itemInfo}"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">HRD부서<br>(설치장소)</th>
                    		<td class="left">
                    			<span class="clinicAddr">
                    				<c:if test="${empty dt.CLI_LOCPLC_CD}">회사 주소와 동일</c:if>
                    			</span>
                    		</td>
                    		<th scope="row">클리닉 지부지사</th>
                    		<td class="left">
                    			<span id="insttName"></span>
                    			<select name="insttIdx" id="insttIdx" style="display:none;">
                    				<c:forEach var="instt" items="${insttList}" varStatus="i">
                    					<option value="${instt.INSTT_IDX}" data-block="${instt.BLOCK_CD}">${instt.INSTT_NAME}</option>
                    				</c:forEach>
                    			</select>
                    		</td>
                    	</tr>
                    </tbody>
				</table>
			</div>
		</div>
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
				<table class="width-type02">
					<caption>능력개발클리닉 참여 신청서 중 HRD 담당자 정보 제공표 : 성명, 직위, 소속부서, 전화번호, 재직경력, 이메일 정보 제공표</caption>
            		<colgroup>
                        <col width="15%">
                        <col width="auto">
                        <col width="15%">
                        <col width="auto">
                    </colgroup>
                    <thead>
                    	<tr>
                    		<th scope="col" colspan="4">2. HRD 담당자 정보</th>
                    	</tr>
                    </thead>
                    <tbody>
                    	<tr>
                    		<th scope="row">성명</th>
                    		<td class="left"><itui:objectViewCustom itemId="picNm" itemInfo="${itemInfo}"/></td>
                    		<th scope="row">직위</th>
                    		<td class="left"><itui:objectViewCustom itemId="ofcps" itemInfo="${itemInfo}"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">소속부서</th>
                    		<td class="left"><itui:objectViewCustom itemId="psitnDept" itemInfo="${itemInfo}"/></td>
                    		<th scope="row">전화번호</th>
                    		<td class="left"><itui:objectViewCustom itemId="telNo" itemInfo="${itemInfo}"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">재직경력(년)</th>
                    		<td class="left"><itui:objectViewCustom itemId="hffcCareer" itemInfo="${itemInfo}"/></td>
                    		<th scope="row">E-mail</th>
                    		<td class="left"><itui:objectViewCustom itemId="email" itemInfo="${itemInfo}"/></td>
                    	</tr>
                    </tbody>
				</table>
			</div>
		</div>
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
				<table class="width-type02">
					<caption>능력개발클리닉 참여 신청서 중 기업 자가 확인 체크리스트 정보 제공표</caption>
            		<colgroup>
                        <col width="50%">
                        <col width="50%">
                    </colgroup>
                    <thead>
                    	<tr>
                    		<th scope="col" colspan="2">3. 기업 자가 확인 체크리스트</th>
                    	</tr>
                    </thead>
                    <tbody>
                    	<c:forEach var="checkListDt" items="${checkList}" varStatus="i">
	                    	<tr>
								<th scope="row">${checkListDt.CHKLST_CN}</th>
								<td>
									<c:choose>
										<c:when test="${checkListAnswer[i.index].CHKLST_ANSW_YN eq 'Y'}">
											<div class="radio-btns-type-area01"><label>예</label></div>
											<c:if test="${fn:contains(checkListDt.CHKLST_CN, 'HRD담당자') && !empty checkListAnswer[i.index].CHKLST_ANSWER_CN}">
												(인원 : ${checkListAnswer[i.index].CHKLST_ANSWER_CN}명)
											</c:if>
										</c:when>
										<c:when test="${checkListAnswer[i.index].CHKLST_ANSW_YN eq 'N'}">
											<div class="radio-btns-type-area01"><label>아니오</label></div>
											<c:if test="${fn:contains(checkListDt.CHKLST_CN, '결격사유') && !empty checkListAnswer[i.index].CHKLST_ANSWER_CN}">
												(결격사유 : ${checkListAnswer[i.index].CHKLST_ANSWER_CN})
											</c:if>
										</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
                    </tbody>
				</table>
			</div>
		</div>
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
				<table class="width-type02">
					<caption>능력개발클리닉 참여 신청서 중 증빙 파일 정보 제공표</caption>
            		<colgroup>
                        <col width="15%">
                        <col width="auto">
                    </colgroup>
                    <tbody>
                    	<tr>
                    		<th scope="row">증빙 파일</th>
                    		<td class="left"><itui:objectViewCustom itemId="file" itemInfo="${itemInfo}"/></td>
                    	</tr>
                    </tbody>
				</table>
			</div>
		</div>
		<c:if test="${!empty dt.DOCTOR_OPINION}">
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
                    		<td class="left">${dt.DOCTOR_OPINION}</td>
                    	</tr>
                    </tbody>
				</table>
			</div>
		</div>
		</c:if>
		<div class="btns-area">
			<div class="btns-right">
				<c:choose>
					<c:when test="${dt.CONFM_STATUS eq '5' || dt.CONFM_STATUS eq '20' || dt.CONFM_STATUS eq '40'}">
						<a href="<c:out value="${REQUEST_MODIFY_FORM_URL}"/>&reqIdx=${dt.REQ_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color03 depth2 fn_btn_modify">수정</a>
					</c:when>
					<c:when test="${dt.CONFM_STATUS eq '10'}">
						<a href="<c:out value="${REQUEST_WITHDRAW_URL}"/>&reqIdx=${dt.REQ_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color02 depth2 fn_btn_modify">회수</a>
					</c:when>
					<c:when test="${dt.CONFM_STATUS eq '30'}">
						<a href="<c:out value="${REQUEST_RETURNREQUEST_URL}"/>&reqIdx=${dt.REQ_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color03 depth2 fn_btn_modify">반려요청</a>
					</c:when>
					<c:when test="${dt.CONFM_STATUS eq '55'}">
						<a href="<c:out value="${REQUEST_DROPREQUEST_URL}"/>&reqIdx=${dt.REQ_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color02 depth2 fn_btn_modify">중도 포기</a>
					</c:when>
				</c:choose>
				<a href="<c:out value="${REQUEST_LIST_FORM_URL}"/>" title="목록으로 이동" class="btn-m01 btn-color01 depth2 fn_btn_write">목록</a>
			</div>
		</div>
	</div>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>