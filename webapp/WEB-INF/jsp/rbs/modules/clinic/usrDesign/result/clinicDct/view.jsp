<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/result/view.jsp"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
<div class="contents-wrapper">
	<div class="contents-area">
		<h3 class="title-type01 ml0">능력개발클리닉 성과보고서</h3>
		<form id="clinic_result" name="clinic_result" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
		<div class="tabmenu-wrapper type03">
			<ul>
				<li>
					<a id="tab1" href="#" class="active" title="기업개요 선택됨">기업개요</a>
				</li>
				<li>
					<a id="tab2" href="#" class="" title="클리닉 운영 성과">클리닉 운영 성과</a>
				</li>
				<li>
					<a id="tab3" href="#" class="" title="설문조사 결과">설문조사 결과</a>
				</li>
				<li>
					<a id="tab4" href="#" class="" title="연간 훈련실시 결과">연간 훈련실시 결과</a>
				</li>
				<li>
					<a id="tab5" href="#" class="" title="자체 KPI 이행결과">자체 KPI 이행결과</a>
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
	            		<caption>능력개발클리닉 성과보고서 중 기업 정보 제공표 : 기업명, 대표자명, 사업자등록번호, 고용보험관리번호, 기업유형, 상시근로자 수, 업종에 관한 정보 제공표</caption>
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
	                    		<td class="left"><c:out value="${corpInfo.TOT_WORK_CNT}"/></td>
	                    	</tr>
	                    	<tr>
	                    		<th scope="row">업종<br>(주업종/기타업종)</th>
	                    		<td colspan="3" class="left"><c:out value="${corpInfo.INDUTY_NM}"/></td>
	                    	</tr>
	                    </tbody>	                    
	            	</table>
	            </div>
			</div>
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>능력개발클리닉 성과보고서 중 지원유형 정보표 : 구분, 지원항목, 지원금액(한도), 신청 여부에 관한 정보 제공표</caption>
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
	                    	<tr>
	                    		<th scope="col">구분</th>
	                    		<th scope="col">지원</th>
	                    		<th scope="col">지원금액(한도)</th>
	                    		<th scope="col">신청 여부</th>
	                    	</tr>
						</thead>
						<tbody>
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
				<itui:additionalHRDmanager objVal="${submitType}" itemInfo="${itemInfo}" content="${contents}" form="성과보고서"/>
			</div>
		</div>
		<div id="2" class="hide mb80">
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>능력개발클리닉 성과보고서 중 HRD담당자 역량개발 결과 정보 제공표</caption>
						<thead>
							<tr>
								<th scope="col">HRD담당자 역량개발 결과</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="left"><c:out value="${dt.HRD_PIC_ABILITY_DEVLOP_RESULT}" escapeXml="false"/></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>능력개발클리닉 성과보고서 중 자체훈련실시 결과 정보 제공표</caption>
						<thead>
							<tr>
								<th scope="col">자체훈련실시 결과</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="left"><c:out value="${dt.SLFTR_RESULT}" escapeXml="false"/></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div id="3" class="hide mb80">
			<div class="contents-box pl0 mt30">
				<h3 class="title-type02 ml0">자체훈련 만족도·현업성취도 조사 결과</h3>
				<div class="table-type02 horizontal-scroll">				
					<table class="width-type02">
						<caption>능력개발클리닉 성과보고서 중 자체훈련 만족도·현업성취도 조사 개요 정보표 : 조사대상, 실시 대상, 실시 예정 과정 수, 조사시기에 관한 정보 제공표</caption>
						<tbody>
							<tr>
								<th scope="row" colspan="6">자체훈련 만족도·현업성취도 조사 개요</th>
							</tr>
							<tr>
								<th scope="row">조사대상</th>
								<td colspan="5" class="left">자체훈련에 참여한 근로자</td>
							</tr>
							<tr>
								<th scope="row">실시 대상</th>
								<td colspan="2" class="left">${surveyTargetList[0].SURVEY_TARGET_CNT}명</td>
								<th scope="row">실시 예정 과정 수</th>
								<td colspan="2" class="left">${trainingCntList[0].TRAINING_CNT}개</td>
							</tr>
							<tr>
								<th scope="row">조사시기</th>
								<td colspan="5" class="left">자체훈련 종료 후</td>
							</tr>
						</tbody>
					</table>
					<div class="table-type02 horizontal-scroll mt10">
					<table class="width-type02">
						<caption>능력개발클리닉 성과보고서 중 만족도 조사 결과 정보표 : 구분, 항목, 조사결과(평균)에 관한 정보 제공표</caption>
						<colgroup>
							<col width="16.7%">
							<col width="auto">
							<col width="16.7%">
						</colgroup>
						<tbody id="survey">
							<tr>
								<th scope="row" colspan="3">만족도 조사 결과</th>
							</tr>										
							<tr>											
								<th scope="row">구분</th>
								<th scope="row">항목</th>
								<th scope="row">조사결과(평균)</th>
							</tr>
							<tr>
								<th scope="row" rowspan="3">운영</th>
								<td class="left">본 훈련과정에 대하여 전반적으로 만족한다.</td>
								<td>${answerList[0].AVG_GNRLZ }점</td>	
							</tr>
							<tr>
								<td class="left">본 훈련과정의 교육방법에 대해 만족하십니까?(이론, 실습, PBL 등)</td>
								<td>${answerList[0].AVG_EDC_CRSE1 }점</td>	
							</tr>
							<tr>
								<td class="left">본 훈련과정의 교육시간에 대해 만족하십니까?(교육 내용 대비 시간 부족, 과다 등)</td>
								<td>${answerList[0].AVG_EDC_CRSE2 }점</td>
							</tr>
							<tr>
								<th scope="row">환경</th>
								<td class="left">본 훈련과정의 교육환경에 대해 만족하십니까?(교육 장소, 시설, 장비, 교육자료 등)</td>
								<td>${answerList[0].AVG_EDC_CRSE3 }점</td>
							</tr>
							<tr>
								<th scope="row">훈련내용</th>
								<td class="left">본 훈련과정의 교육내용에 대해 만족하십니까?(기업 요구와의 부합여부, 구성 내용, 교육 수준 등)</td>
								<td>${answerList[0].AVG_EDC_CRSE4 }점</td>
							</tr>
							<tr>
								<th scope="row">강사</th>
								<td class="left">본 훈련과정의 교육강사에 대해 만족하십니까?(강사 전문성, 강의 내용 전달능력 등)</td>
								<td>${answerList[0].AVG_EDC_CRSE5 }점</td>
							</tr>
						</tbody>
					</table>
					</div>
					<div class="table-type02 horizontal-scroll mt10">
					<table class="width-type02">
						<caption>능력개발클리닉 성과보고서 중 성취도 조사 결과 정보표 : 구분, 항목, 조사결과(평균)에 관한 정보 제공표</caption>
						<colgroup>
							<col width="16.7%">
							<col width="auto">
							<col width="16.7%">
						</colgroup>
						<tbody id="survey">
							<tr>
								<th scope="row" colspan="3">성취도 조사결과</th>
							</tr>										
							<tr>											
								<th scope="row">구분</th>
								<th scope="row">항목</th>
								<th scope="row">조사결과(평균)</th>
							</tr>
							<tr>
								<th scope="row" rowspan="3">지식·기술습득</th>
								<td class="left">본 훈련과정의 학습목표 및 학습내용을 충분히 이해하였다.</td>
								<td>${answerList[0].AVG_KNWLDG_TCHLGY_PICKUP1 }점</td>	
							</tr>
							<tr>
								<td class="left">본 훈련과정을 통해 새로운 지식 및 기술을 습득하였다.</td>
								<td>${answerList[0].AVG_KNWLDG_TCHLGY_PICKUP2 }점</td>	
							</tr>
							<tr>
								<td class="left">본 훈련과정을 통해 습득한 지식 및 기술을 실무에 적용할 수 있다.</td>
								<td>${answerList[0].AVG_KNWLDG_TCHLGY_PICKUP3 }점</td>
							</tr>
							<tr>
								<th scope="row">태도변화</th>
								<td class="left">본 훈련과정을 통해 직무 전문성 및 업무 수행 자신감이 향상되었다.</td>
								<td>${answerList[0].AVG_KNWLDG_TCHLGY_PICKUP4 }점</td>
							</tr>											
						</tbody>
					</table>
					</div>
					<div class="contents-box pl0 mt40">
						<h3 class="title-type02 ml0">자체훈련 현업적용도 조사 결과</h3>
						<div class="table-type02 horizontal-scroll">	
						<table class="width-type02">
							<caption>능력개발클리닉 성과보고서 중 현업적용도 조사 개요 정보표 : 조사대상, 실시 대상, 실시 예정 과정 수, 조사시기에 관한 정보 제공표</caption>
							<tbody>
								<tr>
									<th scope="row" colspan="6">현업적용도 조사 개요</th>
								</tr>
								<tr>
									<th scope="row">조사대상</th>
									<td colspan="5" class="left">자체훈련에 참여한 근로자</td>
								</tr>
								<tr>
									<th scope="row">실시 대상</th>
									<td colspan="2" class="left">${surveyTargetList[1].SURVEY_TARGET_CNT}명</td>
									<th scope="row">실시 예정 과정 수</th>
									<td colspan="2" class="left">${trainingCntList[1].TRAINING_CNT}개</td>
								</tr>
								<tr>
									<th scope="row">조사시기</th>
									<td colspan="5" class="left">자체훈련 종료 1개월 후 <br> ※ 훈련종료 후 3개월 이내 실시</td>
								</tr>
							</tbody>
						</table>
						</div>
						<div class="table-type02 horizontal-scroll mt10">
						<table class="width-type02">
							<caption>능력개발클리닉 성과보고서 중 현업적용도 조사 결과 정보표 : 대상자, 조사시기, 구분, 항목, 조사결과(평균)에 관한 정보 제공표</caption>
							<colgroup>
								<col width="16.7%">
								<col width="auto">
								<col width="16.7%">
							</colgroup>
							<tbody id="survey">
								<tr>
									<th scope="row" colspan="3">현업적용도 조사 결과</th>
								</tr>		
								<tr>
									<th scope="row">대상자</th>
									<td colspan="2" class="left">인사담당자/부서(팀)장</td>
								</tr>
								<tr>
									<th scope="row">조사시기</th>
									<td colspan="2" class="left">훈련 종료 후 1~3개월 이내</td>
								</tr>								
								<tr>											
									<th scope="row">구분</th>
									<th scope="row">항목</th>
									<th scope="row">조사결과(평균)</th>
								</tr>
								<tr>
									<th scope="row" rowspan="3">훈련전이</th>
									<td class="left">본 훈련과정 이수 후 훈련생에게 관련 업무수행 기회를 제공하였나요?</td>
									<td>${answerList[1].AVG_STOPRT_APPLC1 }점</td>	
								</tr>
								<tr>
									<td class="left">본 훈련과정 이수 후 훈련생의 업무수행 역량이 향상되었나요?</td>
									<td>${answerList[1].AVG_STOPRT_APPLC2 }점</td>	
								</tr>
								<tr>
									<td class="left">본 훈련과정 이수 후 훈련생의 업무수행에 실질적인 도움이 되었나요?</td>
									<td>${answerList[1].AVG_STOPRT_APPLC3 }점</td>
								</tr>
								<tr>
									<th scope="row">조건전이</th>
									<td class="left">경영상 이슈(예. 불량률 감소/매출액 증대 등) 해결에 본 훈련과정이 기여했다고 생각하시나요?</td>
									<td>${answerList[1].AVG_STOPRT_APPLC4 }점</td>
								</tr>										
							</tbody>
						</table>
						</div>
					</div>
				</div>
			</div>
		</div>					
		<div id="4" class="hide mb80">
		<div class="contents-box pl0">
			<div class="table-type02 horizontal-scroll">
			<table class="width-type02">
				<caption>능력개발클리닉 성과보고서 중 훈련개요 정보 제공표 : 훈련직무, 정부 지원 과정 수, 정부 미지원 과정 수 정보 제공표</caption>
	    		<colgroup>
	                <col width="15%">
	                <col width="15%">
	                <col width="auto">
	            </colgroup>
	            <thead>
		            <tr>
				    	<th scope="col" colspan="3">훈련개요</th>
				    </tr>	
	            </thead>
	            <tbody>
				    <tr>
	               		<th scope="row" colspan="2">훈련직무</th>
	               		<td class="left">${dt.FYER_TR_RESULT_TR_DTY}</td>
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
<!-- 		               		<td id="trSum" class="left"></td> -->
<!-- 			            </tr> -->
			    </tbody>	 
			</table>
		 	</div>
	 	</div>
			<div class="contents-box pl0">
				<itui:additionalYearResult objVal="${submitType}" itemInfo="${itemInfo}" content="${contents}"/>
			</div>
		</div>
		<div id="5" class="hide mb80">
			<div class="contents-box pl0">
				<itui:additionalKPIResult objVal="${submitType}" itemInfo="${itemInfo}" content="${contents}"/>
			</div>
			<c:if test="${dt.CONFM_STATUS gt '10'}">
		 	<div class="contents-box pl0">
		 		<div class="board-write">
		 			<div class="one-box">
		 				<dl class="board-write-contents">
		 					<dt>
		 						<label for="doctorOpinion">주치의 의견</label>
		 					</dt>
		 					<dd>
		 						<c:choose>
									<c:when test="${loginVO.usertypeIdx ne '40' && ((dt.CONFM_STATUS eq '30' || dt.CONFM_STATUS eq '35'))}">
										<itui:objectTextarea itemId="doctorOpinion" itemInfo="${itemInfo}" objDt="${dt}" objClass="w100"/>
									</c:when>      
									<c:otherwise>${dt.DOCTOR_OPINION}</c:otherwise>             			
								</c:choose>
		 					</dd>
		 				</dl>
					</div>
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
							<a href="${RESULT_ACCEPT_URL}&resltIdx=${dt.RESLT_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color03 depth3" onclick="return confirm('해당 능력개발클리닉 성과보고서를 접수 처리하시겠습니까?')">접수</a>
						</c:when>
						<c:when test="${dt.CONFM_STATUS eq '30'}">
							<!-- 신청 상태가 접수(30)일 때 수정, 저장, 1차승인, 반려 -->
							<a href="${RESULT_MODIFY_FORM_URL}&resltIdx=${dt.RESLT_IDX}&bplNo=${corpInfo.BPL_NO}" class="btn-m01 btn-color03 depth3 fn_btn_modify">수정</a>
							<a href="${RESULT_APPROVE_URL}&resltIdx=${dt.RESLT_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color03 depth3" onclick="return confirm('해당 능력개발클리닉 성과보고서를 승인 처리하시겠습니까?')">승인</a>							
							<button type="submit" class="btn-m01 btn-color03 depth3 btn-fn-return">반려</button>
						</c:when>
						<c:when test="${dt.CONFM_STATUS eq '35'}">
							<!-- 신청 상태가 반려요청(35)일 때는 주치의 의견 입력 후 반려만 가능 -->
							<button type="submit" class="btn-m01 btn-color03 depth3 btn-fn-return">반려</button>
						</c:when>
						<c:when test="${dt.CONFM_STATUS eq '55' || dt.CONFM_STATUS eq '75'}">
							<!-- 최종승인 후 또는 중도포기 요청일 때 중도포기 가능  -->
							<a href="${RESULT_DROP_URL}&resltIdx=${dt.RESLT_IDX}&cliIdx=${dt.CLI_IDX}" class="btn-m01 btn-color03 depth3" onclick="return confirm('해당 능력개발클리닉 성과보고서를 중도포기 처리하시겠습니까?')">${confirmProgress['80']}</a>
						</c:when>
					</c:choose>
				</c:if>
				<a href="<c:out value="${RESULT_LIST_FORM_URL}"/>" title="목록으로 이동" class="btn-m01 btn-color01 depth3 fn_btn_write">목록</a>
			</div>
		</div>
		</form>
	</div>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>