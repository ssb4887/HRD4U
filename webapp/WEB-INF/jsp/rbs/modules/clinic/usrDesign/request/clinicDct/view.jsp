<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/request/view.jsp"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
<div class="contents-wrapper">
	<div class="contents-area">
		<form id="clinic_request" name="clinic_request" method="post" action="#" target="submit_target" enctype="multipart/form-data">
		<input type="hidden" name="doctor" id="doctor" value="${dt.DOCTOR_IDX}">
		<input type="hidden" name="cliIdx" id="cliIdx" value="${dt.CLI_IDX}">
		<input type="hidden" name="bplNo" id="bplNo" value="${corpInfo.BPL_NO}">
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
			<p class="state fl">신청상태 <strong>${confirmProgress[dt.CONFM_STATUS]}</strong></p>
		</div>
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
            	<table class="width-type02">
            		<caption>능력개발클리닉 참여 신청서 중 기업 신청 현황 정보표 : 기업명, 사업자등록번호, 고용보험관리번호, 대표자명, 기업유형, 상시근로자 수, 업종, 주소, HRD부서, 클리닉 지부지사에 관한 정보 제공표</caption>
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
										<c:otherwise>
											<div class="radio-btns-type-area01"><label>아니오</label></div>
											<c:if test="${fn:contains(checkListDt.CHKLST_CN, '결격사유') && !empty checkListAnswer[i.index].CHKLST_ANSWER_CN}">
												(결격사유 : ${checkListAnswer[i.index].CHKLST_ANSWER_CN})
											</c:if>
										</c:otherwise>
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
		<c:if test="${dt.CONFM_STATUS ne '10'}">
		<h3 class="title-type01 ml0 mt30">능력개발클리닉 기업 선정 심사</h3>
		<div class="contents-box pl0">
	        <div class="table-type02 horizontal-scroll" id="jdgMnTab">
	        	<table class="width-type02">
	        		<caption>능력개발클리닉 기업 선정 심사 입력표 : 구분, 세부내용, Pass/Fail 입력표</caption>
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
	                	<c:choose>
							<c:when test="${empty jdgMnTabListAnswer && loginVO.usertypeIdx ne '40'}">
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
		                   					<input type="radio" id="isJdgMn${i.count}-P" class="radio-type01" name="isJdgMn${i.count}" value="P" <c:if test="${jdgMnTabListAnswer[i.index].JDGMNTAB_RESULT eq 'P'}"> checked </c:if>/>
								    		<label for="isJdgMn${i.count}-P" class="mr10">PASS</label>
								   			<input type="radio" id="isJdgMn${i.count}-F" class="radio-type01 ml10" name="isJdgMn${i.count}" value="F" <c:if test="${jdgMnTabListAnswer[i.index].JDGMNTAB_RESULT eq 'F'}"> checked </c:if>/>
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
						</c:when>  
						<c:otherwise>
	                  	<c:forEach var="jdgMnTabDt" items="${jdgMnTabList}" varStatus="i">
	                  		<tr>
	                  			<!--  지원요건 및 제한요건의 갯수가 변경되면 아래의 rowspan 둘 다 조정해야 함 -->
	                   		<c:if test="${i.count eq 1}"><th scope="col" rowspan="3">지원 요건</th></c:if>
			                <c:if test="${i.count eq 4}"><th scope="col" rowspan="4">제한 요건</th></c:if>
	                   		<td colspan="2" class="left">${jdgMnTabDt.JDGMN_TABLE_CN}</td>
	                   		<td>
	                   			<c:if test="${!empty jdgMnTabListAnswer}">
	                    			<c:choose>
	                    				<c:when test="${jdgMnTabListAnswer[i.index].JDGMNTAB_RESULT eq 'P'}">PASS</c:when>
	                    				<c:otherwise>FAIL</c:otherwise>
	                    			</c:choose>
	                   			</c:if>
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
						</c:otherwise>                  	
	                	</c:choose>
	                </tbody>
	        	</table>
	        </div>
        </div>
        </c:if>
        <c:if test="${dt.CONFM_STATUS gt '10'}">
        <div class="contents-box pl0">
        	<div class="board-write">
        		<div class="one-box">
        			<dl class="board-write-contents">
        				<dt>주치의 의견</dt>
        				<dd>
        					<c:choose>
								<c:when test="${(loginVO.clsfCd eq 'Y' && dt.CONFM_STATUS eq '50') || (loginVO.usertypeIdx ne '40' && (dt.CONFM_STATUS eq '30' || dt.CONFM_STATUS eq '35'))}">
									<itui:objectTextarea itemId="doctorOpinion" itemInfo="${itemInfo}" objDt="${dt}" objClass="w100"/>
								</c:when>      
								<c:otherwise>
									${dt.DOCTOR_OPINION}
<%-- 									<c:out value="${dt.DOCTOR_OPINION}" escapeXml="false" default="-"/> --%>
								</c:otherwise>             			
	                		</c:choose>
        				</dd>
        			</dl>
        		</div>
        	</div>
        </div>
        </c:if>
		<div class="btns-area">
			<div class="btns-right">
				<c:if test="${loginVO.usertypeIdx ne '40'}">
					<c:choose>
						<c:when test="${dt.CONFM_STATUS eq '10'}">
							<!-- 신청 상태가 신청(10)일 때만 접수 가능 -->
							<!-- 처음에 주치의 지정을 하더라도 추후에 주치의 변경 가능(주치의 변경은 목록 화면에서만 가능) -->
							<a href="#" class="btn-m01 btn-color05 depth2 appointDoc" id="open-modal01">주치의 지정</a>
<%-- 							<a href="${REQUEST_ACCEPT_URL}&reqIdx=${dt.REQ_IDX}&cliIdx=${dt.CLI_IDX}&isWithdraw=n" class="btn-m01 btn-color03 depth2" onclick="checkDoctor();">${confirmProgress['30']}</a> --%>
							<button type="submit" class="btn-m01 btn-color03 depth2 btn-fn-accept">접수</button>
						</c:when>
						<c:when test="${dt.CONFM_STATUS eq '30'}">
							<!-- 신청 상태가 접수(30)일 때 수정, 저장, 1차승인, 반려 -->
							<a href="${REQUEST_MODIFY_FORM_URL}&reqIdx=${dt.REQ_IDX}&bplNo=${corpInfo.BPL_NO}" class="btn-m01 btn-color03 depth2 fn_btn_modify">수정</a>
							<!-- 저장은 상세보기 화면에 처음 들어와서 기업 선정 심사표를 작성할 때만 가능 >> 한번 저장 후에는 수정화면으로 이동해야 수정가능 -->
							<c:if test="${empty jdgMnTabListAnswer}">
							<button type="submit" class="btn-m01 btn-color03 depth2 fn_btn_submit">저장</button>
							</c:if>
							<button type="submit" class="btn-m01 btn-color03 depth2 btn-fn-firstApprove">1차승인</button>
							<button type="submit" class="btn-m01 btn-color08 depth2 btn-fn-return">반려</button>
						</c:when>
						<c:when test="${dt.CONFM_STATUS eq '35'}">
							<!-- 신청 상태가 반려요청(35)일 때는 주치의 의견 입력 후 반려만 가능 -->
							<button type="submit" class="btn-m01 btn-color08 depth2 btn-fn-return">반려</button>
						</c:when>
						<c:when test="${dt.CONFM_STATUS eq '50'}">
							<c:choose>
								<c:when test="${loginVO.clsfCd eq 'Y' || loginVO.usertypeIdx eq '55'}">
									<!-- 1차승인 후 부장일 때 최종승인, 반려가능 -->
									<a href="${REQUEST_APPROVE_URL}&reqIdx=${dt.REQ_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color03 depth2" onclick="return confirm('해당 능력개발클리닉 신청서를 최종승인하시겠습니까?')">${confirmProgress['55']}</a>
									<button type="submit" class="btn-m01 btn-color08 depth2 btn-returnToAccept">반려</button>
								</c:when>
								<c:otherwise>
									<!-- 1차승인 후 전담주치의가 1차승인한 신청서를 회수가능(접수 상태로 되돌림) -->
									<a href="${REQUEST_ACCEPT_URL}&reqIdx=${dt.REQ_IDX}&cliIdx=${dt.CLI_IDX}&isWithdraw=y" class="btn-m01 btn-color08 depth2" onclick="return confirm('해당 능력개발클리닉 신청서를 회수 처리하시겠습니까?')">회수</a>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${dt.CONFM_STATUS eq '55' || dt.CONFM_STATUS eq '75'}">
							<!-- 최종승인 후 중도포기 가능  -->
							<a href="${REQUEST_DROP_URL}&reqIdx=${dt.REQ_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color08 depth2" onclick="return confirm('해당 능력개발클리닉 신청서를 중도 포기 처리하시겠습니까?')">중도 포기</a>
						</c:when>
					</c:choose>
				</c:if>
				<a href="<c:out value="${REQUEST_LIST_FORM_URL}"/>" title="목록으로 이동" class="btn-m01 btn-color01 depth2">목록</a>
			</div>
		</div>
		</form>
	</div>
</div>
		<!-- 주치의 지정 모달창 -->
	<div class="mask zIndex850"></div>
	<div class="modal-wrapper zIndex900" id="modal-action01">
		<h2>주치의 지정</h2>
		<div class="modal-area">
			<div id="overlay"></div>
			<div class="loader"></div>
			<div class="contents-box pl0">
				<div class="table-type01 horizontal-scroll">
					<table class="width-type02" summary="<c:out value="${settingInfo.list_title}"/> 목록을 볼 수 있고 수정 링크를 통해서 수정페이지로 이동합니다.">
					<colgroup>
						<col style="width:8%">
						<col style="width:auto">
						<col style="width:auto">
						<col style="width:15%">
						<col style="width:15%">
						<col style="width:15%">
					</colgroup>
					<thead>
					<tr>										
						<th scope="col">No</th>
						<th scope="col">지부지사</th>
						<th scope="col">부서</th>
						<th scope="col">직책</th>
						<th scope="col">이름</th>
						<th scope="col">최종배정</th>
					</tr>
					</thead>
					<tbody class="alignC">
						<c:if test="${empty doctorList}">
						<tr>
							<td colspan="6" class="bllist"><spring:message code="message.no.list"/></td>
						</tr>
						</c:if>
						<c:forEach var="doctorListDt" items="${doctorList}" varStatus="i">
						<c:set var="insttIdx" value="${doctorListDt.INSTT_IDX}"/>																																
						<tr>
							<td class="num">${i.count}</td>
							<td><c:out value="${doctorListDt.INSTT_NAME}"></c:out></td>
							<td><c:out value="${doctorListDt.DOCTOR_DEPT_NAME}"></c:out></td>
							<td><c:out value="${doctorListDt.DOCTOR_OFCPS}"></c:out></td>
							<td><c:out value="${doctorListDt.DOCTOR_NAME}"></c:out></td>
							<td><input type="radio" name="selectDoc" title="<spring:message code="item.select"/><c:out value="${i.count}"/>" value="${doctorListDt.DOCTOR_IDX}" id="doctorCheck${i.count}" /></td>
						</tr>	
						</c:forEach>							
					</tbody>
					</table>
					<div class="btns-area">
						<button type="button" class="btn-m02 btn-color02 round01 fn-search-submit">주치의 지정</button>
						<button type="button" class="btn-m02 btn-color02 round01 fn-search-cancel">취소</button>
					</div>
				</div>
			</div>
		</div>
		<button type="button" class="btn-modal-close">모달 창 닫기</button>
	</div>
	<!-- 주치의 지정 모달창 끝 -->	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>