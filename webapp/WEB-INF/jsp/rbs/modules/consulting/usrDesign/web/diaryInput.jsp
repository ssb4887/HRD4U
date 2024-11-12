<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="inputFormId" value="frm" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}" />
		<jsp:param name="javascript_page" value="${moduleJspRPath}/diaryInput.jsp" />
		<jsp:param name="inputFormId" value="${inputFormId}" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />
<jsp:scriptlet>pageContext.setAttribute("newLine", "\n");</jsp:scriptlet>
<script defer type="text/javascript" src="${contextPath }<c:out value="${jsPath}/consulting/diary.js"/>"></script>
<style>
	.input-calendar-wrapper.type02 .input-calendar-area {
		padding-right: 36px;
		height: 36px;
	}
	.input-calendar-wrapper.type02 .input-calendar-area button {
		top: 50%;
		margin-top: -16.5px;
		width: 32px;
		height: 32px;
		margin-right: 0px;
	}
	
	.input-calendar-area button>img {
	    margin-top: 0px;
	}
	
	.modal {
		display: none;
		position: fixed;
		z-index: 1;
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
		overflow: auto;
		background-color: rgba(0, 0, 0, 0.5);
	}
	
	.modal-content {
		background-color: #fff;
		margin: 15% auto;
		padding: 20px;
		border: 1px solid #888;
		width: 80%
	}
	
	.close {
		color: #888;
		float: right;
		font-size: 20px;
		cursor: pointer;
	}
	
	.page {margin-bottom: 20px;}
	.hidden {display: none;}
	
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
<div id="overlay"></div>
<div class="loader"></div>
<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<div class="contents-area">
		<h3 class="title-type02 ml0">컨설팅 수행일지</h3>
			<form id="${inputFormId}" name="${inputFormId}" method="post" target="submit_target" action="${contextPath}/web/consulting/inputProc.do?mId=102<c:out value="${mode}"/>" enctype="multipart/form-data">
			<input type="hidden" name="cnslIdx" id="id_cnslIdx" value="<c:out value="${cnsl.cnslIdx}"/>"/>
			<input type="hidden" name="bplNo" id="id_bplNo" value="<c:out value="${cnsl.bplNo}"/>"/>
			<input type="hidden" name="diaryIdx" id="id_diaryIdx" value="<c:out value="${dt.DIARY_IDX}"/>"/>
			<input type="hidden" id="id_status" name="status" value="" />
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
                        <caption>
                           	컨설팅 수행일지 정보표
                        </caption>
                        <colgroup>
	                        <col width="15%">
	                        <col width="auto">
	                        <col width="15%">
	                        <col width="auto">
                        </colgroup>
						<tbody>
							<tr>
								<th scope="row">기업명</th>
								<td>
									<c:if test="${empty dt.BPL_NM}"><c:out value="${cnsl.corpNm}"/></c:if><c:out value="${dt.BPL_NM}" />
									<input type="hidden" name="bplNm" value="<c:out value="${cnsl.corpNm}" />" />
								</td>
								<th scope="row">컨설팅 유형</th>
								<c:choose>
								<c:when test="${cnsl.cnslType eq '1' || cnsl.cnslType eq '2' || cnsl.cnslType eq '3'}">
								<td>
									<select id="id_sportType" name="sportTypes" class="w100" title="컨설팅 유형" disabled>
										<option value="1" <c:if test="${cnsl.cnslType eq '1'}">selected="selected"</c:if>>사업주자체 과정</option>
										<option value="2" <c:if test="${cnsl.cnslType eq '2'}">selected="selected"</c:if>>일반직무전수 OJT</option>
										<option value="3" <c:if test="${cnsl.cnslType eq '3'}">selected="selected"</c:if>>과제수행 OJT</option>
									</select>
									<input type="hidden" name="sportType" value="<c:out value="${cnsl.cnslType}" />" />
								</td>
								</c:when>
								<c:when test="${cnsl.cnslType eq '4' || cnsl.cnslType eq '5' || cnsl.cnslType eq '6'}">
								<td>
									<select id="id_sportType" name="sportTypes" class="w100" title="컨설팅 유형" disabled>
										<option value="4" <c:if test="${cnsl.cnslType eq '4'}">selected="selected"</c:if>>심층진단</option>
										<option value="5" <c:if test="${cnsl.cnslType eq '5'}">selected="selected"</c:if>>훈련체계 수립</option>
										<option value="6" <c:if test="${cnsl.cnslType eq '6'}">selected="selected"</c:if>>현장활용</option>
									</select>
									<input type="hidden" name="sportType" value="<c:out value="${cnsl.cnslType}" />" />
								</td>
								</c:when>
								</c:choose>
							</tr>
							<tr>
								<th scope="row">수행일시</th>
								<td class="left" colspan="3">
									<div class="flex-box">
										<div class="input-calendar-wrapper type02 mr10">
											<div class="input-calendar-area" style="height: 50px !important;">
												<input type="text" name="mtgStartDt" id="id_mtgStartDt" class="sdate act" title="수행일자" value="<fmt:formatDate value="${dt.MTG_START_DT}" pattern="yyyy-MM-dd" />" />
											</div>
										</div>
										<div class="input-people-wrapper w100">
											<input type="time" name="mtgStartTime" id="id_mtgStartTime" class="w100" title="시작시간" value="<fmt:formatDate value="${dt.MTG_START_DT}" pattern="HH:mm" />" />
											<span class="word-unit">~</span>
											<input type="time" name="mtgEndTime" id="id_mtgEndTime" class="w100" title="종료시간" value="<fmt:formatDate value="${dt.MTG_END_DT}" pattern="HH:mm" />" />
										</div>
										<input type="hidden" name="mtgEndDt" id="id_mtgEndDt" value="<fmt:formatDate value="${dt.MTG_END_DT}" pattern="yyyy-MM-dd" />" />
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">수행차수</th>
								<td class="left" colspan="3">
									<c:out value="${empty mode ? totalCount : dt.EXC_ODR}" />차
									<input type="hidden" name="excOdr" id="id_excOdr" value="<c:out value="${empty mode ? totalCount : dt.EXC_ODR}" />" />
								</td>
							</tr>
							<tr>
								<th scope="row">수행방법</th>
								<td class="left">
									<div class="input-radio-wrapper center">
										<div class="input-radio-area">
											<input type="radio" class="radio-type01" id="id_excMth1" name="excMth" title="수행방법 회의" value="1" <c:if test="${dt.EXC_MTH eq '1'}">checked</c:if>> 
											<label for="excMth1">회의</label>
										</div>
										<div class="input-radio-area center">
											<input type="radio" class="radio-type01" id="id_excMth2" name="excMth" title="수행방법 워크숍" value="2" <c:if test="${dt.EXC_MTH eq '2'}">checked</c:if>> 
											<label for="excMth2">워크숍</label>
										</div>
										<div class="input-radio-area center">
											<input type="radio" class="radio-type01" id="id_excMth3" name="excMth" title="수행방법 FGI" value="3" <c:if test="${dt.EXC_MTH eq '3'}">checked</c:if>> 
											<label for="excMth2">FGI</label>
										</div>
									</div>
								</td>
								<th scope="row">운영방식</th>
								<td class="left">
									<div class="input-radio-wrapper center">
										<div class="input-radio-area">
											<input type="radio" class="radio-type01" id="id_operMthd1" name="operMthd" title="운영방식 대면" value="1" <c:if test="${dt.OPER_MTHD eq '1'}">checked</c:if> />
											<label for="id_operMthd1">대면</label>
										</div>

										<div class="input-radio-area center">
											<input type="radio" class="radio-type01" id="id_operMthd2" name="operMthd" title="운영방식 비대면" value="2" <c:if test="${dt.OPER_MTHD eq '2'}">checked</c:if> /> 
											<label for="id_operMthd2">비대면 </label>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row" rowspan="5">참석자</th>
								<th scope="row">구분</th>
								<th scope="row" colspan="2">성명</th>
							</tr>
							<tr>
								<th scope="row" class="bg01">컨설팅책임자(PM)</th>
								<td colspan="2" class="left">
									<input type="text" name="pmNm" id="id_pmNm" class="w100" maxlength="50" title="컨설팅책임자(PM)" value="<c:out value="${dt.PM_NM}" />" />
								</td>
							</tr>
							<tr>
								<th scope="row" class="bg01">내용전문가</th>
								<td colspan="2" class="left">
									<input type="text" name="cnExpert" id="id_cnExpert" class="w100" maxlength="50" title="내용전문가" value="<c:out value="${dt.CN_EXPERT}" />" />
								</td>
							</tr>
							<tr>
								<th scope="row" class="bg01">기업 내부전문가</th>
								<td colspan="2" class="left">
									<input type="text" name="corpInnerExpert" id="id_corpInnerExpert" class="w100" title="기업 내부전문가" maxlength="50" value="<c:out value="${dt.CORP_INNER_EXPERT}" />" />
								</td>
							</tr>
							<tr>
								<th scope="row" class="bg01">기타 참석자(기업 등)</th>
								<td colspan="2" class="left">
									<input type="text" name="spntPic" id="id_spntPic" class="w100" maxlength="50" title="기타 참석자(기업 등)" value="<c:out value="${dt.SPNT_PIC}" />" />
								</td>
							</tr>
							<tr>
								<th scope="row" rowspan="2">수행내용</th>
								<td colspan="3" class="left">
									<input type="text" name="mtgWeekExplsn1" id="id_mtgWeekExplsn1" class="w100" title="수행제목1" maxlength="100" value="<c:out value="${dt.MTG_WEEK_EXPLSN1}" />" placeholder="○ 회의주제명(예 : 교육체계(직무 기반) 및 활용방안 논의)" />
								</td>
							</tr>
							<tr>
								<td colspan="3" class="left">
									<textarea name="mtgCn1" id="id_mtgCn1" class="w100" maxlength="1000" title="수행내용1" placeholder=" - 직무기반 교육 체계 수립"><c:out value="${dt.MTG_CN1}" /></textarea>
								</td>
							</tr>
							<tr>
								<itui:itemMultiCnslFile itemId="photo" itemInfo="${itemInfo}" colspan="3" />
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="btns-area" id="btns-area-0" style="margin-top: 30px;" style="display: none">
				<div class="btns-right">
		            <button type="button" class="btn-m01 btn-color01 depth2 btn-back">
		            	목록
		            </button>
					<c:if test="${loginVO.usertypeIdx eq '10'}">
						<button type="button" class="btn-m01 btn-color02 depth2" onclick="saveDiary(0);">
			            	저장
			            </button>
			            <button type="submit" class="btn-m01 btn-color03 depth2" onclick="saveDiary(1);">
			            	<c:out value="${empty mode ? '작성' : '수정'}" />
			            </button>
		            </c:if>
	            </div>
	        </div>
		</form>
	</div>
</div>

<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>