<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/plan/view.jsp"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
<div class="contents-wrapper">
	<div class="contents-area">
		<h3 class="title-type01 ml0">능력개발클리닉 훈련계획서</h3>
		<form id="plan_request" name="plan_request" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
		<div class="tabmenu-wrapper type03">
			<ul>
				<li>
					<a id="tab1" href="#" class="active" title="기업개요 선택됨">기업개요</a>
				</li>
				<li>
					<a id="tab2" href="#" class="" title="클리닉 운영 계획">클리닉 운영 계획</a>
				</li>
				<li>
					<a id="tab3" href="#" class="" title="설문 조사 계획">설문 조사 계획</a>
				</li>
				<li>
					<a id="tab4" href="#" class="" title="연간 훈련계획">연간 훈련계획</a>
				</li>
				<li>
					<a id="tab5" href="#" class="" title="자체 KPI목표">자체 KPI목표</a>
				</li>
			</ul>
		</div>
		<div class="title-wrapper">
			<p class="state fl">신청상태 <strong>${confirmProgress[dt.CONFM_STATUS]}</strong></p>
		</div>
		<div id="1" class="mb80">
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
	            	<table class="width-type02">
	            		<caption>능력개발클리닉 훈련계획서 중 신청 기업현황 정보표 : 기업명, 대표자명, 사업자등록번호, 고용보험관리번호, 기업유형, 상시근로자 수, 업종에 관한 정보 제공표</caption>
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
	                    		<td class="left"><c:out value="${reqList.REPER_NM}"/></td>
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
									<itui:objectViewCustom itemId="corpType" itemInfo="${itemInfo}" objDt="${reqList}"/>
	                    		</td>
	                    		<th scope="row">상시근로자 수(명)</th>
	                    		<td class="left"><c:out value="${reqList.TOT_WORK_CNT}"/></td>
	                    	</tr>
	                    	<tr>
	                    		<th scope="row">업종<br>(주업종/기타업종)</th>
	                    		<td colspan="3" class="left"><c:out value="${reqList.INDUTY_NM}"/></td>
	                    	</tr>
	                    </tbody>	                    
	            	</table>
	            </div>
			</div>
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>능력개발클리닉 훈련계획서 중 지원유형 정보표 : 구분, 지원항목, 지원금액, 신청여부에 관한 정보 제공표</caption>
						<colgroup>
							<col width="15%">
							<col width="auto">
							<col width="auto">
							<col width="auto">
						</colgroup>
						<thead>
							<tr>
								<th scope="col" colspan="4">2. 능력개발클리닉 지원유형</th>
							</tr>
						</thead>
						<tbody>
							<tr>
	                    		<th scope="row">구분</th>
	                    		<th scope="row">지원</th>
	                    		<th scope="row">지원금액(한도)</th>
	                    		<th scope="row">신청 여부</th>
	                    	</tr>
	                    	<!-- 필수사항 -->
	                    	<tr>
	                    		<th scope="row" rowspan="5">필수사항</th>
	                    		<td>훈련계획 수립</td>
	                    		<td>연 50만원</td>
	                    		<td>V</td>
	                    	</tr>
	                    	<tr>
	                    		<td>기업직업훈련 실시<br>(자체훈련 1회 포함)</td>
	                    		<td>사업별 지원 한도에 따름</td>
	                    		<td>V</td>
	                    	</tr>
	                    	<tr>
	                    		<td>훈련성과분석</td>
	                    		<td>-</td>
	                    		<td>V</td>
	                    	</tr>
	                    	<tr>
	                    		<td>클리닉 성과보고</td>
	                    		<td>연 100만원</td>
	                    		<td>V</td>
	                    	</tr>
	                    	<tr>
	                    		<td>HR+리더스클럽 활동</td>
	                    		<td>-</td>
	                    		<td>V</td>
	                    	</tr>
	                    	<!-- 선택사항 -->
	                    	<tr>
	                    		<th scope="row" rowspan="5">선택사항</th>
	                    		<td>HRD담당자 역량개발</td>
	                    		<td>연 100만원</td>
	                    		<td><c:if test="${subList[0].REQST_YN eq 'Y'}">V</c:if></td>
	                    	</tr>
	                    	<tr>
	                    		<td>심화단계 컨설팅</td>
	                    		<td>사업별 지원 한도에 따름</td>
	                    		<td><c:if test="${subList[1].REQST_YN eq 'Y'}">V</c:if></td>
	                    	</tr>
	                    	<tr>
	                    		<td>과정개발 컨설팅<br>(S-OJT 맞춤과정 개발)</td>
	                    		<td>사업별 지원 한도에 따름</td>
	                    		<td><c:if test="${subList[2].REQST_YN eq 'Y'}">V</c:if></td>
	                    	</tr>
	                    	<tr>
	                    		<td>판로개척/인력채용</td>
	                    		<td>연 200만원</td>
	                    		<td><c:if test="${subList[3].REQST_YN eq 'Y'}">V</c:if></td>
	                    	</tr>
	                    	<tr>
	                    		<td>HRD성과교류</td>
	                    		<td>연 80만원</td>
	                    		<td><c:if test="${subList[4].REQST_YN eq 'Y'}">V</c:if></td>
	                    	</tr>
						</tbody>	                    
					</table>
				</div>
			</div>
			<div class="contents-box pl0">
				<itui:additionalHRDmanager objVal="${submitType}" itemInfo="${itemInfo}" content="${contents}" form="훈련계획서"/>
			</div>
		</div>
		<div id="2" class="hide mb80">
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>능력개발클리닉 훈련계획서 중 HRD담당자 역량개발계획에 관한 정보 제공표</caption>
						<thead>
							<tr>
								<th scope="col">HRD담당자 역량개발계획</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="left"><c:out value="${dt.HRD_PIC_ABLTDEVLP_PLAN}" escapeXml="false"/></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>능력개발클리닉 훈련계획서 중 자체훈련계획에 관한 정보 제공표</caption>
						<thead>
							<tr>
								<th scope="col">자체훈련계획</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="left"><c:out value="${dt.SLFTR_PLAN}" escapeXml="false"/></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div id="3" class="hide mb80">
			<div class="contents-box pl0 mt30">
				<h3 class="title-type02 ml0">자체훈련 만족도·현업성취도 조사 계획</h3>
				<div class="table-type02 horizontal-scroll">
				<table class="width-type02">
					<caption>능력개발클리닉 훈련계획서 중 자체훈련 만족도·현업성취도 조사 계획에 관한 정보 제공표</caption>
					<thead>
						<tr>
							<th scope="row" colspan="2">만족도·현업성취도 조사 개요</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th scope="row">조사대상</th>
							<td class="left">자체훈련에 참여한 근로자</td>
						</tr>
						<tr>
							<th scope="row">실시 예정자 수</th>
							<td class="left">${dt.SLFTR_SFDGSPSCDG_OPRPRENGER_CO}명</td>
						</tr>
						<tr>
							<th scope="row">실시 예정 과정 수</th>
							<td class="left">${dt.SLFTR_SFDGSPSCDG_OPRCRSE_CO}개</td>
						</tr>
						<tr>
							<th scope="row">조사시기</th>
							<td class="left">자체훈련 종료 후</td>
						</tr>
						<tr>
							<td colspan="2">
								<a href="#" class="btn-m01 btn-color01" id="open-image01" data-idx="1">만족도 조사지 양식보기</a>
								<a href="#" class="btn-m01 btn-color01" id="open-image02" data-idx="2">현업성취도 조사지 양식보기</a>
							</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			<div class="contents-box pl0 mt30">
				<h3 class="title-type02 ml0">자체훈련 현업적용도 조사 계획</h3>
				<div class="table-type02 horizontal-scroll">
				<table class="width-type02">
					<caption>능력개발클리닉 훈련계획서 중 현업적용도 조사 개요에 관한 정보 제공표</caption>
					<thead>
						<tr>
							<th scope="row" colspan="2">현업적용도 조사 개요</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th scope="row">조사대상</th>
							<td class="left">자체훈련에 참여한 근로자</td>
						</tr>
						<tr>
							<th scope="row">실시 예정자 수</th>
							<td class="left">${dt.SLFTR_SPAPPLCDG_OPRREARNGER_CO}명</td>
						</tr>
						<tr>
							<th scope="row">실시 예정 과정 수</th>
							<td class="left">${dt.SLFTR_SPAPPLCDG_OPRCRSE_CO}개</td>
						</tr>
						<tr>
							<th scope="row">조사시기</th>
							<td class="left">자체훈련 종료 1개월 후 <br>※ 자체훈련종료 후 3개월 이내 실시</td>
						</tr>
						<tr>
							<td colspan="2"><a href="#" class="btn-m01 btn-color01" id="open-image03" data-idx="3">현업적용도 조사지 양식보기</a></td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
		</div>
		<div id="4" class="hide mb80">
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					 <table class="width-type02">
					 	<caption>능력개발클리닉 훈련계획서 중 훈련개요 정보표 : 훈련직무, 정부 지원 과정 수, 정부 미지원 과정 수에 관한 정보 제공표</caption>
			    		<colgroup>
			                <col width="15%">
			                <col width="15%">
			                <col width="auto">
			            </colgroup>
			            <thead>
				            <tr>
						    	<th scope="row" colspan="3">훈련개요</th>
						    </tr>	
			            </thead>
			            <tbody>
						    <tr>
			               		<th scope="row" colspan="2">훈련직무</th>
			               		<td class="left">${dt.FYER_TR_PLAN_TR_DTY}</td>
				            </tr>
						    <tr>
			               		<th scope="row" rowspan="2">훈련과정 수</th>
			               		<th scope="row">정부 지원 과정 수</th>
			               		<td class="left">${dt.GVRN_SPORT_CRSE_CO}개</td>
				            </tr>
						    <tr>
			               		<th scope="row">정부 미지원 과정 수</th>
			               		<td class="left">${dt.GVRN_UN_SPORT_CRSE_CO}개</td>
				            </tr>
	<!-- 					    <tr> -->
	<!-- 		               		<th scope="row">합계</th> -->
	<%-- 		               		<td id="trSum"><c:out value="${dt.GVRN_SPORT_CRSE_CO + dt.GVRN_UN_SPORT_CRSE_CO}" /></td> --%>
	<!-- 			            </tr> -->
					    </tbody>	 
					 </table>
			 	</div>
			</div>
			<div class="contents-box pl0">
				<itui:additionalYearPlan objVal="${submitType}" itemInfo="${itemInfo}" content="${contents}"/>
			</div>
		</div>
		<div id="5" class="hide mb80">
			<div class="contents-box pl0">
				<itui:additionalKPI objVal="${submitType}" itemInfo="${itemInfo}" content="${contents}"/>
			</div>
			<c:if test="${dt.CONFM_STATUS gt '10'}">
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>능력개발클리닉 훈련계획서 중 주치의 의견 정보표</caption>
						<colgroup>
							<col width="15%">
							<col width="auto">
						</colgroup>
						<tbody>
							<tr>
								<th scope="col">주치의 의견</th>
								<td class="left">
									<c:choose>
										<c:when test="${loginVO.usertypeIdx ne '40' && (dt.CONFM_STATUS eq '30' || dt.CONFM_STATUS eq '35')}"><itui:objectTextarea itemId="doctorOpinion" itemInfo="${itemInfo}" objDt="${dt}" objClass="w100"/></c:when>      
										<c:otherwise><c:out value="${dt.DOCTOR_OPINION}" escapeXml="false"/></c:otherwise>             			
									</c:choose>
								</td>
							</tr>
						</tbody>	                    
					</table>
				</div>
			</div>
			</c:if>
		</div>
		<div class="btns-area">
			<div class="btns_center mb50">
				<span id="nowPage" style="font-size:1.5em;"></span>
			</div>
			<div class="btns-right">
				<button type="button" title="이전 탭으로 이동" id="goBefore" class="btn-m01 btn-color01 depth3" style="display:none;">이전</button>				
				<button type="button" title="다음 탭으로 이동" id="goNext" class="btn-m01 btn-color01 depth3">다음</button>			
				<c:if test="${loginVO.usertypeIdx ne '40'}">
					<c:choose>
						<c:when test="${dt.CONFM_STATUS eq '10'}">
							<!-- 신청 상태가 신청(10)일 때만 접수 가능 -->
							<a href="${PLAN_ACCEPT_URL}&planIdx=${dt.PLAN_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color03 depth3" onclick="return confirm('해당 능력개발클리닉 신청서를 접수 처리하시겠습니까?')">접수</a>
						</c:when>
						<c:when test="${dt.CONFM_STATUS eq '30'}">
							<!-- 신청 상태가 접수(10)일 때 수정, 저장, 1차승인, 반려 -->
							<a href="${PLAN_MODIFY_FORM_URL}&planIdx=${dt.PLAN_IDX}&bplNo=${corpInfo.BPL_NO}" class="btn-m01 btn-color03 depth3 fn_btn_modify">수정</a>
							<a href="${PLAN_APPROVE_URL}&planIdx=${dt.PLAN_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color03 depth3" onclick="return confirm('해당 능력개발클리닉 신청서를 승인 처리하시겠습니까?')">승인</a>							
							<button type="submit" class="btn-m01 btn-color03 depth3 btn-fn-return">반려</button>
						</c:when>
						<c:when test="${dt.CONFM_STATUS eq '35'}">
							<!-- 신청 상태가 반려요청(35)일 때는 주치의 의견 입력 후 반려만 가능 -->
							<button type="submit" class="btn-m01 btn-color03 depth3 btn-fn-return">반려</button>
						</c:when>
						<c:when test="${dt.CONFM_STATUS eq '55' || dt.CONFM_STATUS eq '53' || dt.CONFM_STATUS eq '75'}">
							<!-- 최종승인 후 중도포기 가능  -->
							<a href="${PLAN_DROP_URL}&planIdx=${dt.PLAN_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color03 depth3" onclick="return confirm('해당 능력개발클리닉 신청서를 중도포기 처리하시겠습니까?')">${confirmProgress['80']}</a>
						</c:when>
						<c:when test="${dt.CONFM_STATUS eq '70'}">
							<!-- 기업이 최종승인 후 변경요청을 할 때 변경승인하거나 반려가능 -->
							<a href="${PLAN_MODIFYAPPROVE_URL}&planIdx=${dt.PLAN_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color03 depth3" onclick="return confirm('해당 능력개발클리닉 신청서를 변경승인 처리하시겠습니까?')">승인</a>
							<button type="submit" class="btn-m01 btn-color03 depth3 btn-fn-returnModify">반려</button>
						</c:when>
					</c:choose>
				</c:if>
				<a href="<c:out value="${PLAN_LIST_FORM_URL}"/>" title="목록으로 이동" class="btn-m01 btn-color01 depth3 fn_btn_write">목록</a>
			</div>
		</div>
		</form>
	</div>
</div>
<%@ include file="../imageModal.jsp" %>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>