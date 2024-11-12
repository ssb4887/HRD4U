<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_resultInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/clinic/result/input.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
<div class="contents-wrapper">
	<div class="contents-area">
		<h3 class="title-type01 ml0">능력개발클리닉 성과보고서</h3>
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
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
		<c:if test="${submitType eq 'modify'}">
			<div class="title-wrapper">
				<p class="state fl">신청상태 <strong>${confirmProgress[dt.CONFM_STATUS]}</strong></p>
			</div>
		</c:if>
		<input type="hidden" id="resltIdx" name="resltIdx" value="${dt.RESLT_IDX}" />
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
			                <th scope="col" colspan="4">1. 기업 정보</th>
			            </tr>
			        </thead>
			        <tbody>
			            <tr>
			                <th scope="row">기업명</th>
			                <td class="left">${corpInfo.BPL_NM}</td>
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
								<itui:objectViewCustom itemId="corpType" itemInfo="${itemInfo}" objDt="${reqList}" />
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
		                    <th scope="col">지원항목</th>
		                    <th scope="col">지원금액(한도)</th>
		                    <th scope="col">신청 여부&nbsp;<strong class="point-important">*</strong></th>
		                </tr>
		            </thead>
		            <tbody>
		            	<!-- 필수사항 -->
	                   	<tr>
	                   		<th scope="col" rowspan="5">필수사항 </th>
	                   		<td>훈련계획 수립</td>
	                   		<td>연 50만원</td>
	                   		<td> <input type="checkbox" id="reqstYn1" class="checkbox-type01" checked/></td>
	                   	</tr>
	                   	<tr>
	                   		<td>기업직업훈련 실시<br>(자체훈련 1회 포함)</td>
	                   		<td>사업별 지원 한도에 따름</td>
	                   		<td> <input type="checkbox" id="reqstYn2" class="checkbox-type01" checked/></td>
	                   	</tr>
	                   	<tr>
	                   		<td>훈련성과분석</td>
	                   		<td>-</td>
	                   		<td> <input type="checkbox" id="reqstYn3" class="checkbox-type01" checked/></td>
	                   	</tr>
	                   	<tr>
	                   		<td>클리닉 성과보고</td>
	                   		<td>연 100만원</td>
	                   		<td> <input type="checkbox" id="reqstYn4" class="checkbox-type01" checked/></td>
	                   	</tr>
	                   	<tr>
	                   		<td>HR+리더스클럽 활동</td>
	                   		<td>-</td>
	                   		<td> <input type="checkbox" id="reqstYn5" class="checkbox-type01" checked/></td>
	                   	</tr>
	                   	<!-- 선택사항 -->
	                   	<tr>
	                   		<th scope="col" rowspan="5">선택사항</th>
	                   		<td>HRD담당자 역량개발</td>
	                   		<td>연 100만원</td>
	                   		<td> <input type="checkbox" id="reqstYn6" <c:if test="${subList[0].REQST_YN eq 'Y'}">checked</c:if> class="checkbox-type01"/></td>
	                   	</tr>
	                   	<tr>
	                   		<td>심화단계 컨설팅</td>
	                   		<td>사업별 지원 한도에 따름</td>
	                   		<td> <input type="checkbox" id="reqstYn7" <c:if test="${subList[1].REQST_YN eq 'Y'}">checked</c:if> class="checkbox-type01"/></td>
	                   	</tr>
	                   	<tr>
	                   		<td>과정개발 컨설팅<br>(S-OJT 맞춤과정 개발)</td>
	                   		<td>사업별 지원 한도에 따름</td>
	                   		<td> <input type="checkbox" id="reqstYn8" <c:if test="${subList[2].REQST_YN eq 'Y'}">checked</c:if> class="checkbox-type01"/></td>
	                   	</tr>
	                   	<tr>
	                   		<td>판로개척/인력채용</td>
	                   		<td>연 200만원</td>
	                   		<td> <input type="checkbox" id="reqstYn9" <c:if test="${subList[3].REQST_YN eq 'Y'}">checked</c:if> class="checkbox-type01"/></td>
	                   	</tr>
	                   	<tr>
	                   		<td>HRD성과교류</td>
	                   		<td>연 80만원</td>
	                   		<td> <input type="checkbox" id="reqstYn10" <c:if test="${subList[4].REQST_YN eq 'Y'}">checked</c:if> class="checkbox-type01"/></td>
	                   	</tr>
		            </tbody>
	        	</table>
	    		</div>
			</div>
			<div class="contents-box pl0">
				<itui:additionalHRDmanager objVal="view" itemInfo="${itemInfo}" content="${contents}" form="성과보고서"/>
			</div>
		</div>
		<div id="2" class="hide mb80">
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>능력개발클리닉 성과보고서 중 HRD담당자 역량개발 결과 입력표</caption>
						<thead>
							<tr>
								<th scope="col">HRD담당자 역량개발 결과&nbsp;<strong class="point-important">*</strong></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									<div class="fr"><span class="hrdPicCount">0</span>/1500자</div>
									<itui:objectTextarea itemId="hrdPic" itemInfo="${itemInfo}"/>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>		
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>능력개발클리닉 성과보고서 중 자체훈련실시 결과 입력표</caption>
						<thead>
							<tr>
								<th scope="col">자체훈련실시 결과&nbsp;<strong class="point-important">*</strong></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									<div class="fr"><span class="slftrCount">0</span>/1500자</div>
									<itui:objectTextarea itemId="slftr" itemInfo="${itemInfo}"/>
								</td>
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
					<caption>능력개발클리닉 성과보고서 중 훈련개요 입력표 : 훈련직무, 정부 지원 과정 수, 정부 미지원 과정 수 입력표</caption>
		    		<colgroup>
		                <col width="15%">
		                <col width="15%">
		                <col width="auto">
		            </colgroup>
		            <tbody>
			            <tr>
					    	<th scope="col" colspan="3">훈련개요</th>
					    </tr>	
					    <tr>
		               		<th scope="row" colspan="2">훈련직무&nbsp;<strong class="point-important">*</strong></th>
		               		<td><itui:objectText itemId="fyerTr" itemInfo="${itemInfo}" objClass="w100"/></td>
			            </tr>
					    <tr>
		               		<th scope="row" rowspan="3">훈련과정 수</th>
		               		<th scope="row">정부 지원 과정 수&nbsp;<strong class="point-important">*</strong></th>
		               		<td ><itui:objectText itemId="gvrnSport" itemInfo="${itemInfo}" objClass="w100 onlyNum"/></td>
			            </tr>
					    <tr>
		               		<th scope="row">정부 미지원 과정 수&nbsp;<strong class="point-important">*</strong></th>
		               		<td><itui:objectText itemId="gvrnUnSport" itemInfo="${itemInfo}" objClass="w100 onlyNum"/></td>
			            </tr>
				    </tbody>	 
				</table>
		 		</div>
		 	</div>
		 	<div class="contents-box pl0">
				<itui:additionalYearResult objDt="${objDt}" objVal="${submitType}" itemInfo="${itemInfo}" content="${contents}"/>
			</div>
		</div>
		<div id="5" class="hide mb80">
			<div class="contents-box pl0">
				<itui:additionalKPIResult objDt="${objDt}" objVal="${submitType}" itemInfo="${itemInfo}" content="${contents}"/>
			</div>
		 	<c:if test="${submitType eq 'modify' && !empty dt.DOCTOR_OPINION}">
		 	<div class="contents-box pl0">
		 		<div class="board-write">
				 	<div class="one-box">
					 	<dl class="board-write-contents">
					 		<dt>주치의 의견</dt>
					 		<dd>${dt.DOCTOR_OPINION}</dd>
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
				<button type="submit" class="btn-m01 btn-color02 depth3 fn_btn_submit" data-status="<%= ConfirmProgress.TEMPSAVE.getCode()%>">임시저장</button>
				<button type="submit" class="btn-m01 btn-color03 depth3 fn_btn_submit" data-status="<%= ConfirmProgress.APPLY.getCode()%>">신청</button>
				<a href="<c:out value="${RESULT_LIST_FORM_URL}"/>" title="목록으로 이동" class="btn-m01 btn-color01 depth3 fn_btn_write" onclick="return confirm('목록으로 이동하시겠습니까? \n임시저장을 하지 않으면 작성한 내용은 저장되지 않습니다.')">목록</a>
			</div>
		</div>
		</form>
	</div>
</div>
<%@ include file="imageModal.jsp" %>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>