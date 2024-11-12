<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>							
<c:set var="searchFormId" value="fn_calendarSearchForm"/>							
<c:set var="listFormId" value="fn_calendarListForm"/>						
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/>				
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>	
		<jsp:param name="searchFormId" value="${searchFormId}"/>				
		<jsp:param name="listFormId" value="${listFormId}"/>					
	</jsp:include>
</c:if>
<style>
	button#send { background-color: #1e89e9; color: white; }
	button#send:disabled { background-color: #a3c2e9; color: white; cursor: default; }
	 .redgrad {
 	background: rgb(131,58,180);
 	background: linear-gradient(90deg,
 		rgba(131,58,180,1) 0%,
 		rgba(253,29,29,1) 50%,
 		rgba(252,176,69,1) 100%);
 	-webkit-background-clip: text;
 	color: transparent;
 	line-height: 1.2;
 	padding: 5px 0;
 }
</style>
	<!-- CMS 시작 -->
	<div class="contents-area pl0">
		<h3 class="title-type01 ml0">체계적 현장훈련 참여신청서</h3>
		<form id="main" method="POST">
			<input type="hidden" name="id" value="${sojt_idx }">
			<input type="hidden" name="status" value="${content.CONFM_STATUS }">
			
			<c:if test="${not empty content.CONFM_CN}">
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<colgroup>
							<col width="15%">
							<col width="auto">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">
									반려사유
								</th>
								<td class="left">
									<c:out value="${content.CONFM_CN }" />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			</c:if>
			
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<colgroup>
							<col width="15%">
							<col width="auto">
							<col width="15%">
							<col width="auto">
						</colgroup>
						<tbody>
							<tr>
								<th scope="col" colspan="4">1. 신청기업 현황</th>
							</tr>
							<tr>
								<th scope="row">기업명</th>
								<td class="left">${content.CORP_NAME }</td>
								<th scope="row">대표자명</th>
								<td class="left">${content.REPVE_NM }</td>
							</tr>
							<tr>
								<th scope="row">사업자등록번호</th>
								<td class="left">${content.BIZR_NO }</td>
								<th scope="row">고용보험관리번호</th>
								<td class="left">${content.BPL_NO }</td>
							</tr>
							<tr>
								<th scope="row" rowspan="2">업종</th>
								<td colspan="3" class="left">업종코드 : ${content.INDUTY_CD }</td>
							</tr>
							<tr>
								<td colspan="3" class="left">주업종(주된 사업) : ${content.INDUTY_NAME }</td>
							</tr>
							<tr>
								<th scope="row">주소</th>
								<td class="left">${content.CORP_LOCATION }</td>
								<th scope="row">상시근로자수</th>
								<td class="left">${content.TOT_WORK_CNT } 명</td>
							</tr>
							<tr>
								<th scope="row">훈련실시주소</th>
								<td class="left">
									<div class=" flex-box">
										<span id="train-addr">
										
										</span>
									</div>
								</td>
								<th scope="row">추천기관명</th>
								<td class="left">${empty content.RECOMMEND_CENTER ? '없음' : content.RECOMMEND_CENTER }</td>
							</tr>
							<tr>
								<th scope="row">관할 지부·지사</th>
								<td class="left">${content.INSTT_NAME }</td>
								<th scope="row">연락처</th>
								<td class="left">${content.DOCTOR_TELNO }</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<colgroup>
							<col width="15%">
							<col width="auto">
							<col width="15%">
							<col width="auto">
						</colgroup>
						<tbody>
							<tr>
								<th scope="col" colspan="4">2. 담당자 정보</th>
							</tr>
							<tr>
								<th scope="row">성명</th>
								<td class="left">
									<c:out value="${content.CORP_PIC_NM }" />
								</td>
								<th scope="row">직위</th>
								<td class="left">
									<c:out value="${content.CORP_PIC_OFCPS }" />
								</td>
							</tr>
							<tr>
								<th scope="row">전화번호</th>
								<td class="left">
									<c:out value="${content.CORP_PIC_TELNO }" />
								</td>
								<th scope="row">E-mail</th>
								<td class="left">
									<c:out value="${content.CORP_PIC_EMAIL }" />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="contents-box pl0">
				<div class="table-type02 horizontal-scroll">
					<table class="width-type02">
						<colgroup>
							<col width="20%">
							<col width="auto">
						</colgroup>
						<tbody>
							<tr>
								<th scope="col" colspan="2">참여요건 체크리스트</th>
							</tr>
							<tr>
								<th scope="row">우선지원대상기업 여부</th>
								<td class="left">
									<c:out value="${content.PRI_SUP_TRGET_CORP_YN eq 2 ? '우선지원 기업' : '대규모 기업' }" />
								</td>
							</tr>
							<tr>
								<th scope="row">휴·폐업 사업장 여부</th>
								<td class="left">
									<c:out value="${content.SPCSS_BPL_YN eq 1 ? '해당' : '미해당' }" />
								</td>
							</tr>
							<tr>
								<th scope="row">임금체불명단 공개 사업장 여부</th>
								<td class="left">
									<c:out value="${content.WGDLY_NM_STG_OTHBC_BPL_YN eq 1 ? '해당' : '미해당'}" />
								</td>
							</tr>
							<tr>
								<th scope="row">산재다발 사업장 여부</th>
								<td class="left">
									<c:out value="${content.INDACMT_BPL_YN eq 1 ? '해당' : '미해당' }" />
								</td>
							</tr>
							<tr>
								<th scope="row">지원 제외 업종 여부</th>
								<td class="left">
									<c:out value="${content.SPORT_EXCL_INDUTY_YN eq 1 ? 'Y' : 'N' }" />
								</td>
							</tr>
							<tr>
								<th scope="row" colspan="2">3. 선정결과</th>
							</tr>
							<tr>
								<th scope="row">선정결과</th>
								<td scope="row">
									<div class="input-radio-wrapper center">
										<span>PASS</span>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div class="sign-wrapper">
										<p class="notice" style="font-size:17px; margin-bottom: 8px;">
											※ 심사 결과가 다른 경우 고용보험시스템(EI) 또는 고용노동부 홈페이지를 통해 한번 더 확인을 해주시기 바랍니다.
										</p>
										<div class="sign-name">
											<p class="notice" id="last-date">${content.LAST_DATE }</p>
										</div>
									</div>
								</td>
							</tr>
							<c:choose>
								<c:when test="content.CONFM_STATUS eq 55">
									<tr>
										<td colspan="2">
											<span class="redgrad">최종 승인 됨</span>
										</td>
									</tr>
								</c:when>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>
		</form>
	</div>
	<!-- //CMS 끝 -->
	<script>
		window.onload = () => {
			let last_date_ = '${content.LAST_DATE}';
			let date_ = last_date_.split('-')
			let p_e = document.querySelector('p#last-date')
			let result = `\${date_[0]}년 \${date_[1]}월 \${date_[2]}일`;
			console.log(result)
			p_e.textContent = result;
		}
		
		var train_addr_e = $('span#train-addr');
		var train_addr = '${content.TR_OPRTN_ADDR}';
		addrs = train_addr.split('||')
		addr_text = `(\${addrs[0] != null ? addrs[0] : ''}) \${addrs[1] != null ? addrs[1] : ''} \${addrs[2] != null ? addrs[2] : ''}`;
		train_addr_e.text(addr_text);
	</script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>