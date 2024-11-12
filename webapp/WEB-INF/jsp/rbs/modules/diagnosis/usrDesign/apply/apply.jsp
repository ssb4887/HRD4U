<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:set var="searchFormId" value="fn_techSupportSearchForm"/>
<c:set var="listFormId" value="fn_techSupportListForm"/>
<c:set var="inputWinFlag" value=""/><%/* 등록/수정창 새창으로 띄울 경우 사용 */%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/><%/* 수정버튼 class */%>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>
		<jsp:param name="searchFormId" value="${searchFormId}"/>
		<jsp:param name="listFormId" value="${listFormId}"/>
	</jsp:include>
</c:if>

<div class="contents-wrapper">
	<!-- CMS 시작 -->
	<div class="contents-area">
		<div class="contents-box pl0">
			<div class="contents-area">
				<div class="contents-box pl0">
					<!-- 2023.10.18 기초진단 소개 추가 -->
					<dl class="introduction-wrapper001">
						<dt>
							<img src="${contextPath}${imgPath}/icon/icon_symbol01.png" alt="" />
							<strong>HRD기초진단이란?</strong>
						</dt>
						<dd>
							기업의 현재 상태를 진단하여, 참여할 수 있는 정부지원 교육훈련 사업을 알려드립니다.<span class="span-br"></span> 고용보험에 가입된 사업장이라면 누구나 참여 가능하며, 매년 최소 <strong class="point-color08">500만원 이상</strong> 지원받으실 수 있습니다.<br /> 지금 바로 우리 기업의 상태를
							진단해보세요.
						</dd>
					</dl>
				</div>
				
				<c:if test="${usertype ne 20}">
					<div class="btns-area">
						<c:if test="${!empty bplNo and constl eq false and noneBplNo eq false}">
							<button type="button" id="open-modal01" class="btn-b01 round01 btn-color03 left" onclick="applyBtn('${bplNo },${memberName},${memberEmail}');">
								<span>신청하기</span>
								<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01"/>
							</button>
						</c:if>
					</div>
				</c:if>
				
				<c:if test="${usertype eq 20}">
					<p class="center word-type01 mb10"><strong class="point-color08">협약맺은 기업에 한해</strong> HRD기초진단을 조회할 수 있습니다.<br/>HRD기초진단을 신청하려면 기업회원으로 로그인해주세요.</p>
					<div class="btns-area">
						<button type="button" class="btn-b01 round01 btn-color03 left" onclick="location.href='${contextPath}/web/diagnosis/list.do?mId=54'">
							<span>협약기업 조회하기</span>
							<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01" />
						</button>
					</div>
				</c:if>
			</div>
		</div>
	</div>
	<!-- //CMS 끝 -->
</div>

<!-- 기업 담당자 입력 모달 창 -->
<div class="mask"></div>
   <div class="modal-wrapper" id="modal-action01" >
       <h2>
           HRD기초진단 실행
       </h2>
       <div class="modal-area">
		<div id="overlay"></div>
		<div class="loader"></div>
 			<!-- 기업담당자 정보 조회 -->	
		<div id="infoBox"></div>
 			<!-- 기업담당자 정보 조회 -->	
       </div>

       <button type="button" class="btn-modal-close">
           모달 창 닫기
       </button>
   </div>
<c:if test="${(usertype ne 20 and login eq true) and (empty bplNo || bplNo eq '00000000000')}">
	<script>
		alert("마이페이지>사업장관리번호를 확인해주세요.");
	</script>
</c:if>
<script src="${contextPath}/web/js/diagnosis/apply.js"></script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>