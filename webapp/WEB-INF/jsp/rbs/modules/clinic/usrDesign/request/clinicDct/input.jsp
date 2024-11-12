<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_sampleInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/request/input.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
	<div id="cms_board_article" class="contents-wrapper">
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${REQUEST_MODIFY_URL}"/>&reqIdx=${dt.REQ_IDX}&isUpateAll=y" target="submit_target" enctype="multipart/form-data">
		<input type="hidden" name="cliIdx" value="${dt.CLI_IDX}">
		<input type="hidden" name="bplNo" value="${corpInfo.BPL_NO}">
		<h3 class="title-type01 ml0">능력개발클리닉 참여 신청서</h3>
		<div class="title-wrapper">
			<p class="state fl">신청상태 <strong>${confirmProgress[dt.CONFM_STATUS]}</strong></p>
		</div>
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
            	<table class="width-type02">
            		<caption>능력개발클리닉 참여 신청서 중 기업 신청 현황 입력표 : 기업명, 사업자등록번호, 고용보험관리번호에 관한 정보와 대표자명, 기업유형, 상시근로자 수, 업종, 주소, HRD부서 입력표</caption>
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
                    		<th scope="row">대표자명&nbsp;<strong class="point-important">*</strong></th>
                    		<td class="left"><itui:objectText itemId="reperNm" itemInfo="${itemInfo}" objDt="${dt}" objClass="w100"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">사업자등록번호</th>
                    		<td class="left"><c:out value="${corpInfo.BIZR_NO}"/></td>
                    		<th scope="row">고용보험관리번호</th>
                    		<td class="left"><c:out value="${corpInfo.BPL_NO}"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">기업유형&nbsp;<strong class="point-important">*</strong></th>
                    		<td class="left">
                    			<!-- 1: 개인 2: 법인 -->
								<itui:objectRadioCustom itemId="corpType" itemInfo="${itemInfo}" objDt="${dt}"/>
                    		</td>
                    		<th scope="row">상시근로자 수(명)&nbsp;<strong class="point-important">*</strong></th>
                    		<td><itui:objectText itemId="totWorkCnt" itemInfo="${itemInfo}" objDt="${data}" objClass="w100 onlyNum hrdPeople"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">업종&nbsp;<strong class="point-important">*</strong><br>(주업종/기타업종)</th>
                    		<td colspan="3"><itui:objectText itemId="indutyNm" itemInfo="${itemInfo}" objDt="${data}" objClass="w100"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">주소&nbsp;<strong class="point-important">*</strong></th>
                    		<td colspan="3"><itui:objectText itemId="bplAddr" itemInfo="${itemInfo}" objDt="${data}" objClass="w100"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">HRD부서&nbsp;<strong class="point-important">*</strong><br>(설치장소)</th>
                    		<td class="left"><itui:objectViewCustom itemId="sido" itemInfo="${itemInfo}"/></td>
                    		<th scope="row">클리닉 지부지사&nbsp;<strong class="point-important">*</strong></th>
                    		<td class="left">
                    			<span id="insttName"></span>
                    			<select name="insttIdx" id="insttIdx" style="display:none;">
                    				<option value="">지부지사 선택</option> 
                    				<c:forEach var="instt" items="${insttList}" varStatus="i">
                    					<option value="${instt.INSTT_IDX}">${instt.INSTT_NAME}</option>
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
					<caption>능력개발클리닉 참여 신청서 중 HRD 담당자 정보 입력표 : 성명, 직위, 소속부서, 전화번호, 재직경력, 이메일 입력표</caption>
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
                    		<th scope="row">성명&nbsp;<strong class="point-important">*</strong></th>
                    		<td><itui:objectText itemId="picNm" itemInfo="${itemInfo}" objDt="${dt}" objClass="w100"/></td>
                    		<th scope="row">직위&nbsp;<strong class="point-important">*</strong></th>
                    		<td><itui:objectText itemId="ofcps" itemInfo="${itemInfo}" objDt="${dt}" objClass="w100"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">소속부서&nbsp;<strong class="point-important">*</strong></th>
                    		<td><itui:objectText itemId="psitnDept" itemInfo="${itemInfo}" objDt="${dt}" objClass="w100"/></td>
                    		<th scope="row">전화번호&nbsp;<strong class="point-important">*</strong></th>
                    		<td><itui:objectText itemId="telNo" itemInfo="${itemInfo}" objDt="${dt}" objClass="w100 onlyNumDash"/></td>
                    	</tr>
                    	<tr>
                    		<th scope="row">재직경력(년)&nbsp;<strong class="point-important">*</strong></th>
                    		<td class="left"><itui:objectText itemId="hffcCareer" itemInfo="${itemInfo}" objDt="${dt}" objClass="w100 onlyNum"/></td>
                    		<th scope="row">E-mail&nbsp;<strong class="point-important">*</strong></th>
                    		<td class="left"><itui:objectEMAILCustom itemId="email" itemInfo="${itemInfo}" objDt="${dt}"/></td>
                    	</tr>
                    </tbody>
				</table>
			</div>
		</div>
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
				<table class="width-type02">
					<caption>능력개발클리닉 참여 신청서 중 기업 자가 확인 체크리스트 입력 폼</caption>
            		<colgroup>
                        <col width="50%">
                        <col width="50%">
                    </colgroup>
                    <thead>
                    	<tr>
                    		<th scope="col" colspan="2">3. 기업 자가 확인 체크리스트&nbsp;<strong class="point-important">*</strong></th>
                    	</tr>
                    </thead>
                    <tbody>
                    	<c:forEach var="checkListDt" items="${checkList}" varStatus="i">
	                    	<c:if test="${i.last}">
	                    		<input type="hidden" name="checkListLength" value="${i.count}">
	                    		<input type="hidden" name="checkVer" value="${checkListDt.CHKLST_VER}">
	                    	</c:if>
		                <tr id="checkListNo${checkListDt.CHKLST_NO }">
                    		<th scope="row">${checkListDt.CHKLST_CN}<input type="hidden" name="checkListIdx${i.count}" value="${checkListDt.CHKLST_NO}"></th>
                    		<td class="left">
                    		<div class="input-radio-wrapper ratio">
                    			<div class="input-radio-area">
		                    		<input type="radio" id="isSelfEmp${i.count}-Y" class="radio-type01" name="isSelfEmp${i.count}" value="Y" <c:if test="${checkListAnswer[i.index].CHKLST_ANSW_YN eq 'Y'}"> checked </c:if>/>
								    <label for="isSelfEmp${i.count}-Y">Y</label>
								    <!-- HRD담당자  보유 여부가 Y일 때 인원 입력받기-->
								    <c:if test="${fn:contains(checkListDt.CHKLST_CN, 'HRD담당자')}">
								    <span id="peopleCount" style="display:none;" class="ml10"><input type="text" name="selfEmpCn${i.count}" id="hrdPeople" class="onlyNum" style="width:60px; height:38px;" data-pcode="${i.count}" <c:if test="${!empty checkListAnswer[i.index].CHKLST_ANSWER_CN}"> value="${checkListAnswer[i.index].CHKLST_ANSWER_CN}" </c:if>> 명&nbsp;<strong class="point-important">*</strong></span>
									</c:if>
								</div>
								<div class="input-radio-area">
									<input type="radio" id="isSelfEmp${i.count}-N" class="radio-type01" name="isSelfEmp${i.count}" value="N" <c:if test="${checkListAnswer[i.index].CHKLST_ANSW_YN eq 'N'}"> checked </c:if>/>
								    <label for="isSelfEmp${i.count}-N">N</label>
								    <!-- 결격사유 해당할 때 결겨사유 입력받기 -->
								    <c:if test="${fn:contains(checkListDt.CHKLST_CN, '결격사유')}">
								    <span id="disqualify" style="display:none;" class="ml10">결격사유&nbsp;<strong class="point-important">*</strong> : &nbsp;<input type="text" name="selfEmpCn${i.count}" style="width:73%; height:45px;" data-pcode="${i.count}" <c:if test="${!empty checkListAnswer[i.index].CHKLST_ANSWER_CN}"> value="${checkListAnswer[i.index].CHKLST_ANSWER_CN}" </c:if>></span>
		                    		</c:if>
                    			</div>
                    		</div>
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
					<caption>능력개발클리닉 참여 신청서 중 증빙 파일 업로드표</caption>
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
		<c:if test="${dt.CONFM_STATUS ne '10'}">
		<h3 class="title-type01 ml0 mt30">능력개발클리닉 기업 선정 심사</h3>
		<div class="contents-box pl0">
	        <div class="table-type02 horizontal-scroll" id="jdgMnTab">
	        	<table class="width-type02">
	        		<caption>능력개발클리닉 기업 선정 심사표 입력표 : 구분, 세부내용, Pass/Fail 입력표</caption>
	        		<colgroup>
	                    <col width="15%">
	                    <col width="auto">
	                    <col width="auto">
	                    <col width="25%">
	                </colgroup>
	                <thead>
	                	<tr>
	                		<th scope="col" colspan="4">참여요건 체크리스트</th>
	                	</tr>
	                </thead>
	                <tbody>
	                	<tr>
	                		<th scope="col">구분</th>
	                		<th scope="col" colspan="2">세부내용</th>
	                		<th scope="col">PASS/FAIL</th>
	                	</tr>
	                	<!-- 지원 요건과 제한 요건을 구분하는 컬럼이 없음 체크리스트 내용으로 구분하기(~아니어야 함 >> 제한 요건 4~7번째 항목, 나머지 >> 지원 요건 1~3번째 항목)  -->
						<c:forEach var="jdgMnTabDt" items="${jdgMnTabList}" varStatus="i">
                  		<c:if test="${i.last}">
                   		<input type="hidden" name="jdgListLength" value="${i.count}">
                   		<input type="hidden" name="jdgVer" value="${jdgMnTabDt.JDGMNTAB_VER}">
                   		</c:if>
                  		<tr>
                  			<!--  지원요건 및 제한요건의 갯수가 변경되면 아래의 rowspan 둘 다 조정해야 함 -->
	                   		<c:if test="${i.count eq 1}"><th scope="col" rowspan="3">지원 요건</th></c:if>
	                   		<c:if test="${i.count eq 4}"><th scope="col" rowspan="4">제한 요건</th></c:if>
	                   		<td colspan="2" class="left">${jdgMnTabDt.JDGMN_TABLE_CN}<input type="hidden" name="jdgListIdx${i.count}" value="${jdgMnTabDt.JDGMNTAB_NO}"></td>
	                   		<td>
	                   			<div class="input-radio-area">
                   					<input type="radio" id="isJdgMn${i.count}-P" class="radio-type01" name="isJdgMn${i.count}" value="P" <c:if test="${jdgMnTabListAnswer[i.index].JDGMNTAB_RESULT eq 'P'}"> checked </c:if> />
						    		<label for="isJdgMn${i.count}-P" class="mr10">PASS</label>
						   			<input type="radio" id="isJdgMn${i.count}-F" class="radio-type01 ml10" name="isJdgMn${i.count}" value="F" <c:if test="${jdgMnTabListAnswer[i.index].JDGMNTAB_RESULT eq 'F'}"> checked </c:if> />
						    		<label for="isJdgMn${i.count}-F">FAIL</label>
	                   			</div>
							</td>
                   		</tr>
                 			</c:forEach>
	                  	<tr>
	                  		<th scope="col" colspan="2">선정 결과<input type="hidden" name="slctnResult" id="slctnResult" value="${dt.SLCTN_RESULT}"></th>
	                  		<td id="jdgResult" colspan="2">
	                  			<c:if test="${!empty dt.SLCTN_RESULT}">
	                   			<c:choose>
	                   				<c:when test="${dt.SLCTN_RESULT eq 'P'}">PASS</c:when>
	                   				<c:otherwise>FAIL</c:otherwise>
	                   			</c:choose>
	                  			</c:if>
	                  		</td>
	                  	</tr>
	                </tbody>
	        	</table>
	        </div>
        </div>
        </c:if>
		<div class="btns-area">
			<div class="btns-right">
				<button type="submit" class="btn-m01 btn-color03 depth2 fn_btn_submit">저장</button>
				<a href="#" title="이전 화면으로 이동" class="btn-m01 btn-color01 depth2 fn_btn_write" onclick="history.back();">이전</a>
				<a href="<c:out value="${REQUEST_LIST_FORM_URL}"/>" title="목록으로 이동" class="btn-m01 btn-color01 depth2 fn_btn_cancel" onclick="return confirm('목록으로 이동하시겠습니까? \n저장을 하지 않으면 수정한 내용은 저장되지 않습니다.')">목록</a>
			</div>
		</div>
		</form>
		</div>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>