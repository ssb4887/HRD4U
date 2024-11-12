<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_sampleInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/input.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
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
</style>
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/edu/eduDct.js"/>"></script>

<div id="overlay"></div>
<div class="loader"></div>

	<!-- contents  -->
	<div class="contents-wrapper">
		<div class="contents-area">
			<form action="" method="" id="form-input">
				<input type="hidden" name="edcIdx" value="<c:out value='${edc.EDC_IDX}' />" />
				<input type="hidden" name="edcCd" value="<c:out value='${settingInfo.edc_cd}' />" />
				<legend class="blind">글쓰기</legend>
				<div class="board-write">
					<div class="one-box">
						<dl>
							<dt>
								<label for="edcName"> 교육명 
									<strong class="point-important"> * </strong>
								</label>
							</dt>
							<dd>
								<input type="text" id="edcName" name="edcName" value="<c:out value='${edc.EDC_NAME}' />" placeholder="교육명 입력" class="w100" />
							</dd>
						</dl>
					</div>
					<div class="one-box">
						<dl>
							<dt>
								<label for="insttIdx"> 소속기관
									<strong class="point-important"> * </strong>
								</label>
							</dt>
							<dd>
								<select id="insttIdx" name="insttIdx" class="w50">
									<option value="">선택</option>
									<c:forEach items="${insttList}" var="instt">
										<option value="<c:out value='${instt.INSTT_IDX}' />" <c:if test="${instt.INSTT_IDX eq edc.INSTT_IDX}">selected</c:if> ><c:out value='${instt.INSTT_NAME}'/></option>
									</c:forEach>
								</select>
							</dd>
						</dl>
					</div>
					<div class="one-box">
						<dl>
							<dt>
								<label for="edcPlace"> 교육 장소 
									<strong class="point-important"> * </strong>
								</label>
							</dt>
							<dd>
								<input type="text" id="edcPlace" name="edcPlace" value="<c:out value='${edc.EDC_PLACE}' />" placeholder="교육 장소 입력" class="w100" />
							</dd>
						</dl>
					</div>

					<div class="one-box">
						<dl>
							<dt>
								<label for="edcStartDate"> 교육기간
									<strong class="point-important"> * </strong>
								</label>
							</dt>
							<dd>
								<div class="input-calendar-wrapper">
									<div class="input-calendar-area">
										<input type="text" id="edcStartDate" name="edcStartDate" class="sdate" title="교육시작일 입력" placeholder="교육시작일" value="<c:out value='${edc.EDC_START_DATE}' />" autocomplete="off" />
									</div>
									<div class="input-time-area">
										<select id="edcStartHour" name="edcStartHour">
											<c:forEach begin="0" end="23" var="hour">
												<fmt:formatNumber type="number" var="formattedHour" value="${hour}" pattern="00" />
												<option value="${formattedHour}" <c:if test="${formattedHour eq edc.EDC_START_HOUR}">selected</c:if>>${formattedHour}</option>
											</c:forEach>
										</select>
										<div class="word-time-unit">:</div>
										<select id="edcStartMin" name="edcStartMin">
											<c:forEach begin="0" end="59" var="min">
												<fmt:formatNumber type="number" var="formattedMin" value="${min}" pattern="00" />
												<option value="${formattedMin}" <c:if test="${formattedMin eq edc.EDC_START_MIN}">selected</c:if>>${formattedMin}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								
								<div class="word-unit">~</div>
								
								<div class="input-calendar-wrapper">
									<div class="input-calendar-area">
										<input type="text" id="edcEndDate" name="edcEndDate" class="edate" title="교육종료일 입력" placeholder="교육종료일" value="<c:out value='${edc.EDC_END_DATE}' />" autocomplete="off"/>
									</div>
									<div class="input-time-area">
										<select id="edcEndHour" name="edcEndHour">
											<c:forEach begin="0" end="23" var="hour">
												<fmt:formatNumber type="number" var="formattedHour" value="${hour}" pattern="00" />
												<option value="${formattedHour}" <c:if test="${formattedHour eq edc.EDC_END_HOUR}">selected</c:if>>${formattedHour}</option>
											</c:forEach>
										</select>
										<div class="word-time-unit">:</div>
										<select id="edcEndMin" name="edcEndMin">
											<c:forEach begin="0" end="59" var="min">
												<fmt:formatNumber type="number" var="formattedMin" value="${min}" pattern="00" />
												<option value="${formattedMin}" <c:if test="${formattedMin eq edc.EDC_END_MIN}">selected</c:if> >${formattedMin}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</dd>
						</dl>
					</div>

					<div class="one-box">
						<dl>
							<dt>
								<label for="totEdcTime"> 총교육시간 </label>
							</dt>
							<dd>
								<input type="number" min="0" id="totEdcTime" name="totEdcTime" value="<c:out value='${edc.TOT_EDC_TIME}' />" placeholder="총교육시간 입력" class="w50" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
							</dd>
						</dl>
					</div>

					<div class="one-box">
						<dl>
							<dt>
								<label for="instrctrName"> 강사 정보 </label>
							</dt>
							<dd>
								<div class="table-type02">
									<table>
										<caption>강사 정보 정보표 : 강사, 연락처, 이메일, 강사소개에 관한 정보표
										</caption>
										<colgroup>
											<col style="width: 25%">
											<col style="width: 75%">
										</colgroup>
										<tbody>
											<tr>
												<th>강사</th>
												<td class="left">
													<input type="text" id="instrctrName" name="instrctrName" value="<c:out value='${edc.INSTRCTR_NAME}' />" placeholder="강사 입력" class="w100" />
												</td>
											</tr>
											<tr>
												<th>연락처</th>
												<td class="left">
													<input type="text" id="instrctrTelno" name="instrctrTelno" value="<c:out value='${edc.INSTRCTR_TELNO}' />" placeholder="연락처 입력" class="w100" maxlength="30" oninput="formatPhoneNumber(this);" />
												</td>
											</tr>
											<tr>
												<th>이메일</th>
												<td class="left">
													<input type="text" id="instrctrEmail" name="instrctrEmail" value="<c:out value='${edc.INSTRCTR_EMAIL}' />" placeholder="이메일 입력" class="w100" maxlength="40" oninput="validationEmail(this);" />
													<span id="emailError" style="display:none; color:red;"></span>
												</td>
											</tr>
											<tr>
												<th>강사 소개</th>
												<td class="left">
													<input type="text" id="instrctrIntrcn" name="instrctrIntrcn" value="<c:out value='${edc.INSTRCTR_INTRCN}' />" placeholder="강사 소개 입력" class="w100" />
												</td>
											</tr>
										</tbody>
									</table>
								</div>

							</dd>
						</dl>
					</div>

					<div class="one-box">
						<dl>
							<dt>
								<label for="othbcYn"> 공개여부 
									<strong class="point-important"> * </strong>
								</label>
							</dt>
							<dd>
								<div class="input-radio-wrapper ratio">
									<div class="input-radio-area">
										<input type="radio" class="radio-type01" id="othbcYn_Y" name="othbcYn" value="Y" <c:if test="${edc.OTHBC_YN eq 'Y'}">checked</c:if> />
										<label for="othbcYn_Y">공개</label>
									</div>

									<div class="input-radio-area">
										<input type="radio" class="radio-type01" id="othbcYn_N" name="othbcYn" value="N" <c:if test="${edc.OTHBC_YN eq 'N' or empty edc}">checked</c:if> />
										<label for="othbcYn_N">비공개</label>
									</div>
								</div>
							</dd>
						</dl>
					</div>

					<div class="one-box">
						<dl>
							<dt>
								<label for="recptBgndt"> 접수기간 
									<strong class="point-important"> * </strong>
								</label>
							</dt>
							<dd>
								<div class="input-calendar-wrapper">
									<div class="input-calendar-area">
										<input type="text" id="recptBgndt" name="recptBgndt" class="sdate1" title="접수시작일 입력" placeholder="접수시작일" value="<c:out value='${edc.RECPT_BGNDT}' />" autocomplete="off" />
									</div>
									<div class="input-time-area">
										<select id="recptBgndtHour" name="recptBgndtHour">
											<c:forEach begin="0" end="23" var="hour">
												<fmt:formatNumber type="number" var="formattedHour" value="${hour}" pattern="00" />
												<option value="${formattedHour}" <c:if test="${formattedHour eq edc.RECPT_BGNDT_HOUR}">selected</c:if>>${formattedHour}</option>
											</c:forEach>
										</select>
										<div class="word-time-unit">:</div>
										<select id="recptBgndtMin" name="recptBgndtMin">
											<c:forEach begin="0" end="59" var="min">
												<fmt:formatNumber type="number" var="formattedMin" value="${min}" pattern="00" />
												<option value="${formattedMin}" <c:if test="${formattedMin eq edc.RECPT_BGNDT_MIN}">selected</c:if>>${formattedMin}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								
								<div class="word-unit">~</div>
								
								<div class="input-calendar-wrapper">
									<div class="input-calendar-area">
										<input type="text" id="recptEnddt" name="recptEnddt" class="edate1" title="접수종료일 입력" placeholder="접수종료일" value="<c:out value='${edc.RECPT_ENDDT}' />" autocomplete="off" />
									</div>
									<div class="input-time-area">
										<select id="recptEnddtHour" name="recptEnddtHour">
											<c:forEach begin="0" end="23" var="hour">
												<fmt:formatNumber type="number" var="formattedHour" value="${hour}" pattern="00" />
												<option value="${formattedHour}" <c:if test="${formattedHour eq edc.RECPT_ENDDT_HOUR}">selected</c:if>>${formattedHour}</option>
											</c:forEach>
										</select>
										<div class="word-time-unit">:</div>
										<select id="recptEnddtMin" name="recptEnddtMin">
											<c:forEach begin="0" end="59" var="min">
												<fmt:formatNumber type="number" var="formattedMin" value="${min}" pattern="00" />
												<option value="${formattedMin}" <c:if test="${formattedMin eq edc.RECPT_ENDDT_MIN}">selected</c:if> >${formattedMin}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</dd>
						</dl>
					</div>

					<div class="one-box">
						<dl>
							<dt>
								<label for="textfield08"> 수료증 발급여부 
									<strong class="point-important"> * </strong>
								</label>
							</dt>
							<dd>
								<div class="input-radio-wrapper ratio">
									<div class="input-radio-area">
										<input type="radio" class="radio-type01" id="ctfhvIssueYn_Y" name="ctfhvIssueYn" value="Y" <c:if test="${edc.CTFHV_ISSUE_YN eq 'Y'}">checked</c:if> />
										<label for="ctfhvIssueYn_Y">발급</label>
									</div>

									<div class="input-radio-area">
										<input type="radio" class="radio-type01" id="ctfhvIssueYn_N" name="ctfhvIssueYn" value="N" <c:if test="${edc.CTFHV_ISSUE_YN eq 'N' or empty edc}">checked</c:if> />
										<label for="ctfhvIssueYn_N">미발급</label>
									</div>
								</div>
							</dd>
						</dl>
					</div>


					<div class="one-box">
						<dl>
							<dt>
								<label for="maxRecptNmpr"> 신청최대인원 </label>
							</dt>
							<dd>
								<div class="change-application-wrapper">
									<input type="number" min="0" id="maxRecptNmpr" name="maxRecptNmpr" value="<c:out value='${edc.MAX_RECPT_NMPR}' />" placeholder="신청최대인원" class="w50" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />
									<p>*0으로 입력 시 제한없음</p>
								</div>

							</dd>
						</dl>
					</div>

					<div class="one-box">
						<dl class="board-write-contents">
							<dt>
								<label for="uploadBtn"> 첨부 파일 </label>
							</dt>
							<c:if test="${not empty fileList}">
								<dd style="border-bottom:1px solid #e5e5e5;">
								<c:forEach items="${fileList}" var="edcFile" varStatus="status">
									<p class="attached-file">
										<a href="${contextPath}/dct/eduDct/download.do?mId=<c:out value='${crtMenu.menu_idx}' />&fleIdx=${edcFile.FLE_IDX}" class="fn_filedown">${edcFile.FILE_ORIGIN_NAME}</a>
									</p>
								</c:forEach>
								</dd>
							</c:if>
							
							<dd>
								<div class="fileBox">
									<input type="text" id="fileName" class="fileName" readonly="readonly" placeholder="파일찾기" />
									<button type="button" id="upload-file" class="btn_file" onclick="addFile()">찾아보기</button>
								</div>
								
								<p class="mt10">첨부 파일 목록(최대 5개)</p>
								<div class="add-file-list-wrapper">
									<div class="add-file-list-area">
										<ul id="add-file-area">
											<c:if test="${not empty fileList}">
												<c:forEach items="${fileList}" var="edcFile" varStatus="status">
													<li>
														<input type="checkbox" id="add-file${status.index}" name="" class="checkbox-type01" value="${edcFile.FLE_IDX}" />
														<label for="add-file${status.index}">
															${edcFile.FILE_ORIGIN_NAME}
														</label>
														<input type="hidden" name="uploaded-files" value="${edcFile.FLE_IDX}" />
														<input type="file" name="files" style="display:none;" data-idx="${edcFile.FLE_IDX}" />
													</li>
												</c:forEach>
											</c:if>
										</ul>
									</div>

									<div class="btns">
										<button type="button" onclick="deleteChecked()">삭제</button>
										<button type="button" onclick="moveUp()">위</button>
										<button type="button" onclick="moveDown()">아래</button>
									</div>
								</div>
							</dd>
						</dl>
					</div>
					<div class="one-box">
						<dl class="board-write-contents">
							<dt>
								<label for="cn"> 주요 내용 </label>
							</dt>
							<dd>
								<textarea id="cn" name="cn" cols="50" rows="5" class=""><c:out value='${edc.CN}' /></textarea>
							</dd>
						</dl>
					</div>
				</div>

				<div class="btns-area">
					<div class="btns-right">
						<c:choose>
							<c:when test="${empty edc}">
								<button type="button" class="btn-m01 btn-color03 depth2" onclick="saveEdc()">등록</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn-m01 btn-color03 depth2" onclick="saveEdc()">저장</button>
							</c:otherwise>
						</c:choose>
						<a href="${URL_LIST}" class="btn-m01 btn-color01 depth2">목록</a>
					</div>
				</div>

			</form>
		</div>
	</div>
	
<elui:hiddenInput inputInfo="${queryString}" exceptNames="${searchFormExceptParams}"/>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>