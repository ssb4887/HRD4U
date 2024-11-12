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
<div id="overlay"></div>
<div class="loader"></div>
<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<div class="contents-area">
		<h3 class="title-type02 ml0">컨설팅 수행일지</h3>
			<form id="${inputFormId}" name="${inputFormId}" method="post" target="submit_target" action="/web/consulting/inputProc.do?mId=102<c:out value="${mode}"/>" enctype="multipart/form-data">
			<input type="hidden" name="cnslIdx" id="id_cnslIdx" value="<c:out value="${cnsl.cnslIdx}" /> " />
			<input type="hidden" name="bplNo" id="id_bplNo" value="<c:out value="${cnsl.bplNo}" /> " />
			<input type="hidden" name="diaryIdx" id="id_diaryIdx" value="<c:out value="${dt.DIARY_IDX}" />" />
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table>
                        <caption>
                           	컨설팅 수행일지 정보표
                        </caption>
                        <colgroup>
                            <col style="width: 15%" />
                            <col style="width: 21.25%" />
                            <col style="width: 21.25%" />
                            <col style="width: 21.25%" />
                            <col style="width: 21.25%" />
                        </colgroup>
						<tbody>
							<tr>
								<th scope="row" rowspan="2">지원유형</th>
								<th scope="row" colspan="2">훈련프로그램 개발</th>
								<th scope="row" colspan="2">심화컨설팅</th>
							</tr>
							<tr>
								<td colspan="2">
									<div class="input-radio-wrapper center">
										<div class="input-radio-area">
											<input type="radio" class="radio-type01" id="sportType1" name="sportType" value="1" <c:if test="${dt.SPORT_TYPE eq '1'}">checked</c:if> />
											<label for="sportType1">사업주자체 과정 </label>
										</div>
										<div class="input-radio-area">
											<input type="radio" class="radio-type01" id="sportType2" name="sportType" value="2" <c:if test="${dt.SPORT_TYPE eq '2'}">checked</c:if>> 
											<label for="sportType2">일반직무전수 OJT </label>
										</div>
										<div class="input-radio-area">
											<input type="radio" class="radio-type01" id="sportType3" name="sportType" value="3" <c:if test="${dt.SPORT_TYPE eq '3'}">checked</c:if>> 
											<label for="sportType3">과제수행 OJT </label>
										</div>
									</div>
								</td>
								<td colspan="2">
									<div class="input-radio-wrapper center">
										<div class="input-radio-area">
											<input type="radio" class="radio-type01" id="sportType4" name="sportType" value="4" <c:if test="${dt.SPORT_TYPE eq '4'}">checked</c:if>> 
											<label for="sportType4">심층진단 </label>
										</div>
										<div class="input-radio-area">
											<input type="radio" class="radio-type01" id="sportType5" name="sportType" value="5" <c:if test="${dt.SPORT_TYPE eq '5'}">checked</c:if>> 
											<label for="sportType5">훈련체계 수립 </label>
										</div>
										<div class="input-radio-area">
											<input type="radio" class="radio-type01" id="sportType6" name="sportType" value="6" <c:if test="${dt.SPORT_TYPE eq '6'}">checked</c:if>> 
											<label for="sportType6">현장활용</label>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">회의명</th>
									<td class="left" colspan="2">
										<input type="text" name="mtgNm" id="id_mtgNm" class="w100" maxlength="50" value="<c:out value="${dt.MTG_NM}" />" />
									</td>
								<th scope="row">회의회차</th>
								<td class="left" colspan="2">
									<input type="text" name="mtgTme" id="id_mtgTme" class="w100" maxlength="5" value="<c:out value="${dt.MTG_TME}" />" onkeypress="isNumber();" />
								</td>
							</tr>
							<tr>
								<th scope="row">훈련과정명</th>
								<td colspan="4" class="left">
									<input type="text" name="tpNm" id="id_tpNm" class="w100" maxlength="100" value="<c:out value="${dt.TP_NM}" />" />
								</td>
							</tr>
							<tr>
								<th scope="row">시작일시</th>
								<td class="left" colspan="2">
									<div class="input-calendar-area">
										<input type="text" name="mtgStartDt" id="id_mtgStartDt" class="sdate" value="<fmt:formatDate value="${dt.MTG_START_DT}" pattern="yyyy-MM-dd" />" />
									</div>
									<div class="input-time-area">
										<input type="time" name="mtgStartTime" id="id_mtgStartTime" step="1" value="<fmt:formatDate value="${dt.MTG_START_DT}" pattern="HH:MM" />" />
									</div>
								</td>
								<th scope="row">종료일시</th>
								<td class="left" colspan="2">
									<div class="input-calendar-area">
										<input type="text" name="mtgEndDt" id="id_mtgEndDt" class="edate"  value="<fmt:formatDate value="${dt.MTG_START_DT}" pattern="yyyy-MM-dd" />" />
									</div>
									<div class="input-time-area">
										<input type="time" name="mtgEndTime" id="id_mtgEndTime" step="1" value="<fmt:formatDate value="${dt.MTG_START_DT}" pattern="HH:MM" />" />
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row">회의장소</th>
								<td colspan="2" class="left">
									<input type="text" name="mtgPlace" id="id_mtgPlace" class="w100" maxlength="100" value="<c:out value="${dt.MTG_PLACE}" />" />
								</td>
								<th scope="row">회의방법</th>
								<td colspan="2" class="left">
									<div class="input-radio-wrapper center">
										<div class="input-radio-area">
											<input type="radio" class="radio-type01" id="id_intvYn1" name="intvYn" value="1" <c:if test="${dt.INTV_YN eq '1'}">checked</c:if>> 
											<label for="intvYn1">대면</label>
										</div>

										<div class="input-radio-area center">
											<input type="radio" class="radio-type01" id="id_intvYn2" name="intvYn" value="2" <c:if test="${dt.INTV_YN eq '2'}">checked</c:if>> 
											<label for="intvYn2">비대면 </label>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row" rowspan="5">참석위원</th>
								<th scope="row">구분</th>
								<th scope="row" colspan="3">성명</th>
							</tr>
							<tr>
								<th scope="row" class="bg01">컨설팅책임자(PM)</th>
								<td colspan="3" class="left">
									<input type="text" name="pmNm" id="id_pmNm" class="w100" value="<c:out value="${dt.PM_NM}" />" />
								</td>
							</tr>
							<tr>
								<th scope="row" class="bg01">내용전문가</th>
								<td colspan="3" class="left">
									<input type="text" name="cnExpert" id="id_cnExpert" class="w100" maxlength="50" value="<c:out value="${dt.CN_EXPERT}" />" />
								</td>
							</tr>
							<tr>
								<th scope="row" class="bg01">기업 내부전문가</th>
								<td colspan="3" class="left">
									<input type="text" name="corpInnerExpert" id="id_corpInnerExpert" class="w100" maxlength="50" value="<c:out value="${dt.CORP_INNER_EXPERT}" />" />
								</td>
							</tr>
							<tr>
								<th scope="row" class="bg01">지원기관 담당자</th>
								<td colspan="3" class="left">
									<input type="text" name="spntPic" id="id_spntPic" class="w100" maxlength="50" value="<c:out value="${dt.MTG_PLACE}" />" />
								</td>
							</tr>
							<tr>
								<itui:itemTextarea itemId="mtgCn" itemInfo="${itemInfo}" colspan="4" />
							</tr>
							<tr>
								<itui:itemMultiCnslFile itemId="photo" itemInfo="${itemInfo}" colspan="4" />
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
		            	<button type="submit" class="btn-m01 btn-color03 depth2">
		            		<c:out value="${empty mode ? '작성' : '수정'}" />
		            	</button>
		            </c:if>
	            </div>
	        </div>
		</form>
	</div>
</div>


<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>