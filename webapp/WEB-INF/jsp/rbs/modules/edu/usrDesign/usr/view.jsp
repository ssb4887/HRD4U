<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/view.jsp"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
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
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/edu/eduUsr.js"/>"></script>
<div id="overlay"></div>
<div class="loader"></div>

<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<div class="contents-area">
		<div class="table-type02 horizontal-scroll">
			<elui:hiddenInput inputInfo="${queryString}" exceptNames="${searchFormExceptParams}"/>
			<table class="width-type02">
				<caption>교육과정 신청 정보표 : 번호, 교육과정명, 분류, 교육기간, 접수기간, 교육장소, 접수상태, 소속기관, 작성자에 관한 정보 제공표</caption>
				<colgroup>
					<col style="width: 20%">
					<col style="width: 20%">
					<col style="width: 60%">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">교육명</th>
						<td colspan="2" class="left" id="edcName"><c:out value='${edc.EDC_NAME}' /></td>
					</tr>
					<tr>
						<th scope="row">교육 장소</th>
						<td colspan="2" class="left"><c:out value='${edc.EDC_PLACE}' /></td>
					</tr>
					<tr>
						<th scope="row">교육기간</th>
						<td colspan="2" class="left">
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${edc.EDC_START_DATE}"/>
							~
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${edc.EDC_END_DATE}"/>
						</td>
					</tr>
					<tr>
						<th scope="row">총교육시간</th>
						<td colspan="2" class="left"><c:out value='${edc.TOT_EDC_TIME}' /> 시간</td>
					</tr>
					<tr>
						<th rowspan="4" scope="row">강사 정보</th>
						<th scope="row" class="bg01">강사</th>
						<td class="left">
							<c:out value='${edc.INSTRCTR_NAME}' />
						</td>
					</tr>
					<tr>
						<th scope="row" class="bg01">연락처</th>
						<td class="left">
							<c:out value='${edc.INSTRCTR_TELNO}' />
						</td>
					</tr>
					<tr>
						<th scope="row" class="bg01">이메일</th>
						<td class="left">
							<c:out value='${edc.INSTRCTR_EMAIL}' />
						</td>
					</tr>
					<tr>
						<th scope="row" class="bg01">강사 소개</th>
						<td class="left">
							<c:out value='${edc.INSTRCTR_INTRCN}' />
						</td>
					</tr>
					<tr>
						<th scope="row">공개여부</th>
						<td colspan="2" class="left">
							<c:if test="${edc.OTHBC_YN eq 'Y'}">공개</c:if>
							<c:if test="${edc.OTHBC_YN eq 'N'}">비공개</c:if>
						</td>
					</tr>
					<tr>
						<th scope="row">접수기간</th>
						<td colspan="2" class="left">
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${edc.RECPT_BGNDT}"/>
							~
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${edc.RECPT_ENDDT}"/>
						</td>
					</tr>
					<tr>
						<th scope="row">수료증 발급여부</th>
						<td colspan="2" class="left">
							<c:if test="${edc.CTFHV_ISSUE_YN eq 'Y'}">발급</c:if>
							<c:if test="${edc.CTFHV_ISSUE_YN eq 'N'}">미발급</c:if>
						</td>
					</tr>
					<tr>
						<th scope="row">신청최대인원</th>
						<td colspan="2" class="left">
							<c:out value='${edc.MAX_RECPT_NMPR}' />
						</td>
					</tr>
					<tr>
						<th scope="row">첨부파일</th>
						<td colspan="2" class="left">
							<c:if test="${empty fileList}">등록된 첨부파일이 없습니다.</c:if>
							<c:forEach items="${fileList}" var="file">
								<p class="attached-file">
									<a href="javascript:void(0);" data-idx="<c:out value='${file.FLE_IDX}' />" class="fn_filedown" onclick="downloadFile(event);" >${file.FILE_ORIGIN_NAME}</a>
								</p>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th scope="row">주요 내용</th>
						<td colspan="2" class="left">
							<c:out value='${edc.CN}' />
						</td>
					</tr>
					
					<c:choose>
						<c:when test="${!check}">
							<tr>
								<th scope="col" colspan="4" class="bg01">
									이미 신청한 교육입니다.
								</th>
							</tr>
						</c:when>
						<c:when test="${!isValid}">
							<tr>
								<th scope="col" colspan="4" class="bg01">
									접수기간이 아닙니다.
								</th>
							</tr>
						</c:when>
					</c:choose>
					
				</tbody>
			</table>
		</div>
	</div>
	
	<input type="hidden" name="edcCd" value="<c:out value='${edc.EDC_CD}' />" />
	
	<div class="btns-area">
		<c:if test="${check and isValid}">
			<button type="button" class="btn-b01 round01 btn-color03 left" onclick="registerMember(event)">
				<span> 신청하기 </span>
				<img src="${contextPath}${imgePath}/icon/icon_arrow_right03.png" class="arrow01">
			</button>
		</c:if>
		<c:if test="${!check and isValid}">
			<button type="button" class="btn-b01 round01 btn-color06 left" onclick="cancelRegister(event)">
				<span> 취소하기 </span>
				<img src="${contextPath}${imgePath}/icon/icon_arrow_right03.png" alt="" class="arrow01">
			</button>
		</c:if>
		<button type="button" class="btn-b01 round01 btn-color02 left" onclick="history.back();">
			<span> 목록으로 </span>
			<img src="${contextPath}${imgePath}/icon/icon_arrow_right03.png" alt="" class="arrow01">
		</button>
	</div>
	
	<!-- //CMS 끝 -->
	</div>
<form action="" method="post" style="display: none;" id="form-box">
	<input type="hidden" name="idx" id="idx" value="" />
</form>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>