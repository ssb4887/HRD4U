<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_planInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/clinic/plan/input.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
	<div id="cms_board_article" class="contents-wrapper">
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
		<div class="contents-area">
		<h3 class="title-type01 ml0">능력개발클리닉 훈련계획서</h3>
		<input type="hidden" id="planIdx" name="planIdx" value="${dt.PLAN_IDX}" />
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
		<c:if test="${submitType eq 'modify'}">
			<div class="title-wrapper">
				<p class="state fl">신청상태 <strong>${confirmProgressMap[dt.CONFM_STATUS]}</strong></p>
			</div>
		</c:if>
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
                    <tbody>
                    	<tr>
                    		<th scope="col" colspan="4" >1. 신청 기업 현황</th>
                    	</tr>
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
								<itui:objectViewCustom itemId="corpType" itemInfo="${itemInfo}" objDt="${reqList}" />
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
	            <caption>능력개발클리닉 훈련계획서 중 지원유형 입력표 : 구분, 지원항목, 지원금액에 대한 정보 제공 및 신청여부 입력표</caption>
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
	                   		<td>훈련계획 수립 <input type="hidden" name="itemCd1" value="01"/></td>
	                   		<td>연 50만원 <input type="hidden" name="essntlSe1" value="R"/></td>
	                   		<td> <input type="checkbox" name="reqstYn1" id="reqstYn1" class="checkbox-type01" value="Y" checked/></td>
	                   	</tr>
	                   	<tr>
	                   		<td>기업직업훈련 실시<br>(자체훈련 1회 포함) <input type="hidden" name="itemCd2" value="03"/></td>
	                   		<td>사업별 지원 한도에 따름 <input type="hidden" name="essntlSe2" value="R"/></td>
	                   		<td> <input type="checkbox" name="reqstYn2" id="reqstYn2" class="checkbox-type01" value="Y" checked/></td>
	                   	</tr>
	                   	<tr>
	                   		<td>훈련성과분석 <input type="hidden" name="itemCd3" value="10"/></td>
	                   		<td>- <input type="hidden" name="essntlSe3" value="R"/></td>
	                   		<td> <input type="checkbox" name="reqstYn3" id="reqstYn3" class="checkbox-type01" value="Y" checked/></td>
	                   	</tr>
	                   	<tr>
	                   		<td>클리닉 성과보고 <input type="hidden" name="itemCd4" value="05"/></td>
	                   		<td>연 100만원 <input type="hidden" name="essntlSe4" value="R"/></td>
	                   		<td> <input type="checkbox" name="reqstYn4" id="reqstYn4" class="checkbox-type01" value="Y" checked/></td>
	                   	</tr>
	                   	<tr>
	                   		<td>HR+리더스클럽 활동 <input type="hidden" name="itemCd5" value="11"/></td>
	                   		<td>- <input type="hidden" name="essntlSe5" value="R"/></td>
	                   		<td> <input type="checkbox" name="reqstYn5" id="reqstYn5" class="checkbox-type01" value="Y" checked/></td>
	                   	</tr>
	                   	<!-- 선택사항 -->
	                   	<tr>
	                   		<th scope="col" rowspan="5">선택사항</th>
	                   		<td>HRD담당자 역량개발 <input type="hidden" name="itemCd7" value="04"/></td>
	                   		<td>연 100만원 <input type="hidden" name="essntlSe7" value="C"/></td>
	                   		<td> <input type="checkbox" name="reqstYn7" id="reqstYn7" class="checkbox-type01" value="Y" <c:if test="${submitType eq 'modify' && subList[0].REQST_YN eq 'Y'}">checked</c:if>/></td>
	                   	</tr>
	                   	<tr>
	                   		<td>심화단계 컨설팅 <input type="hidden" name="itemCd6" value="06"/></td>
	                   		<td>사업별 지원 한도에 따름 <input type="hidden" name="essntlSe6" value="C"/></td>
	                   		<td> <input type="checkbox" name="reqstYn6" id="reqstYn6" class="checkbox-type01" value="Y" <c:if test="${submitType eq 'modify' && subList[1].REQST_YN eq 'Y'}">checked</c:if>/></td>
	                   	</tr>
	                   	<tr>
	                   		<td>과정개발 컨설팅<br>(S-OJT 맞춤과정 개발) <input type="hidden" name="itemCd8" value="07"/></td>
	                   		<td>사업별 지원 한도에 따름 <input type="hidden" name="essntlSe8" value="C"/></td>
	                   		<td> <input type="checkbox" name="reqstYn8" id="reqstYn8" class="checkbox-type01" value="Y" <c:if test="${submitType eq 'modify' && subList[2].REQST_YN eq 'Y'}">checked</c:if>/></td>
	                   	</tr>
	                   	<tr>
	                   		<td>판로개척/인력채용 <input type="hidden" name="itemCd10" value="08"/></td>
	                   		<td>연 200만원 <input type="hidden" name="essntlSe10" value="C"/></td>
	                   		<td> <input type="checkbox" name="reqstYn10" id="reqstYn10" class="checkbox-type01" value="Y" <c:if test="${submitType eq 'modify' && subList[3].REQST_YN eq 'Y'}">checked</c:if>/></td>
	                   	</tr>
	                   	<tr>
	                   		<td>HRD성과교류 <input type="hidden" name="itemCd9" value="09"/></td>
	                   		<td>연 80만원 <input type="hidden" name="essntlSe9" value="C"/></td>
	                   		<td> <input type="checkbox" name="reqstYn9" id="reqstYn9" class="checkbox-type01" value="Y" <c:if test="${submitType eq 'modify' && subList[4].REQST_YN eq 'Y'}">checked</c:if>/></td>
	                   	</tr>
		            </tbody>
	        	</table>
	    		</div>
			</div>
			<div class="contents-box pl0">
				<itui:additionalHRDmanager objDt="${objDt}" objVal="${submitType}" itemInfo="${itemInfo}" content="${contents}" form="훈련계획서"/>
			</div>
        </div>
		<div id="2" class="hide mb80">
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>능력개발클리닉 훈련계획서 중  HRD담당자 역량개발계획 입력표</caption>
						<tbody>
							<tr>
								<th scope="col">HRD담당자 역량개발계획&nbsp;<strong class="point-important">*</strong></th>
							</tr>
							<tr>
								<td>
									<div class="fr"><span class="hrdPicCount">0</span>/1000자</div>
									<itui:objectTextarea itemId="hrdPic" itemInfo="${itemInfo}" />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<caption>능력개발클리닉 훈련계획서 중 자체훈련계획 입력표</caption>
						<tbody>
							<tr>
								<th scope="col">자체훈련계획&nbsp;<strong class="point-important">*</strong></th>
							</tr>
							<tr>
								<td>
									<div class="fr"><span class="slftrCount">0</span>/1000자</div>
									<itui:objectTextarea itemId="slftr" itemInfo="${itemInfo}" />
								</td>
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
						<caption>능력개발클리닉 훈련계획서 중 자체훈련 만족도·현업성취도 조사 계획 입력표</caption>
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
								<th scope="row">실시 예정자 수(명)&nbsp;<strong class="point-important">*</strong></tf>
								<td><itui:objectText itemId="sfdgPeple" itemInfo="${itemInfo}" objClass="w100 onlyNum"/></td>
							</tr>
							<tr>
								<th scope="row">실시 예정 과정 수(개)&nbsp;<strong class="point-important">*</strong></th>
								<td><itui:objectText itemId="sfdgCourse" itemInfo="${itemInfo}" objClass="w100 onlyNum"/></td>
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
					<caption>능력개발클리닉 훈련계획서 중 현업적용도 조사 개요 입력표</caption>
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
							<th scope="row">실시 예정자 수(명)&nbsp;<strong class="point-important">*</strong></th>
							<td><itui:objectText itemId="spapPeople" itemInfo="${itemInfo}" objClass="w100 onlyNum"/></td>
						</tr>
						<tr>
							<th scope="row">실시 예정 과정 수(개)&nbsp;<strong class="point-important">*</strong></th>
							<td><itui:objectText itemId="spapCourse" itemInfo="${itemInfo}" objClass="w100 onlyNum"/></td>
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
					 	<caption>능력개발클리닉 훈련계획서 중 훈련개요 입력표 : 훈련직무, 정부 지원 과정 수, 정부 미지원 과정 수 입력표</caption>
			    		<colgroup>
			                <col width="15%">
			                <col width="15%">
			                <col width="auto">
			            </colgroup>
			            <tbody>
				            <tr>
						    	<th scope="row" colspan="3">훈련개요</th>
						    </tr>	
						    <tr>
			               		<th scope="row" colspan="2">훈련직무&nbsp;<strong class="point-important">*</strong></th>
			               		<td><itui:objectText itemId="fyerTr" itemInfo="${itemInfo}" objClass="w100"/></td>
				            </tr>
						    <tr>
			               		<th scope="row" rowspan="2">훈련과정 수&nbsp;<strong class="point-important">*</strong></th>
			               		<th scope="row">정부 지원 과정 수&nbsp;<strong class="point-important">*</strong></th>
			               		<td ><itui:objectText itemId="gvrnSport" itemInfo="${itemInfo}" objClass="w100 onlyNum"/></td>
				            </tr>
						    <tr>
			               		<th scope="row">정부 미지원 과정 수&nbsp;<strong class="point-important">*</strong></th>
			               		<td ><itui:objectText itemId="gvrnUnSport" itemInfo="${itemInfo}" objClass="w100 onlyNum"/></td>
				            </tr>
					    </tbody>	 
					 </table>
			 	</div>
			</div>
		 	<div class="contents-box pl0">
				<itui:additionalYearPlan objDt="${objDt}" objVal="${submitType}" itemInfo="${itemInfo}" content="${contents}"/>
			</div>
		</div>
		<div id="5" class="hide mb80">
			<div class="contents-box pl0">
				<itui:additionalKPI objDt="${objDt}" objVal="${submitType}" itemInfo="${itemInfo}" content="${contents}"/>
			</div>
		 	<c:if test="${submitType eq 'modify' && !empty dt.DOCTOR_OPINION}">
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
								<td class="left">${dt.DOCTOR_OPINION}</td>
							</tr>
						</tbody>	                    
					</table>
				</div>
			</div>
       		</c:if>
		</div>
		</div>
		<div class="btns-area">
			<div class="btns_center mb50">
				<span id="nowPage" style="font-size:1.5em;"></span>
			</div>
			<div class="btns-right">
				<button type="button" title="이전 탭으로 이동" id="goBefore" class="btn-m01 btn-color01 depth3" style="display:none;">이전</button>				
				<button type="button" title="다음 탭으로 이동" id="goNext" class="btn-m01 btn-color01 depth3">다음</button>			
				<c:choose>
					<c:when test="${dt.CONFM_STATUS eq '55' || dt.CONFM_STATUS eq '53'}">						
<%-- 						<a href="<c:out value="${PLAN_MODIFYREQUEST_URL}"/>&planIdx=${dt.PLAN_IDX}" class="btn-m01 btn-color03 depth3 fn_btn_modify">변경요청</a> --%>
						<button type="submit" class="btn-m01 btn-color03 depth3 fn_btn_modifyRequest">변경요청</button>
					</c:when>
					<c:otherwise>
						<button type="submit" class="btn-m01 btn-color02 depth3 fn_btn_submit" data-status="<%= ConfirmProgress.TEMPSAVE.getCode()%>">임시저장</button>
						<button type="submit" class="btn-m01 btn-color03 depth3 fn_btn_submit" data-status="<%= ConfirmProgress.APPLY.getCode()%>">신청</button>
					</c:otherwise>
				</c:choose>
				<a href="<c:out value="${PLAN_LIST_FORM_URL}"/>" title="목록으로 이동" class="btn-m01 btn-color01 depth3 fn_btn_cancel" onclick="return confirm('목록으로 이동하시겠습니까? \n임시저장을 하지 않으면 작성한 내용은 저장되지 않습니다.')">목록</a>
			</div>
		</div>
		</form>
	</div>
<%@ include file="imageModal.jsp" %>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>