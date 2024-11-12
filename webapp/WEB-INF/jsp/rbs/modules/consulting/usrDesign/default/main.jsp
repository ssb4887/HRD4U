<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination"%>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}" />
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}" />
<c:set var="searchFormId" value="fn_techSupportSearchForm" />
<c:set var="listFormId" value="fn_techSupportListForm" />
<c:set var="inputWinFlag" value="" />
<%
	/* 등록/수정창 새창으로 띄울 경우 사용 */
%>
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}" />
<%
	/* 수정버튼 class */
%>
<link type="text/css" rel="stylesheet" href="<c:out value="${contextPath}${cssPath}/contents02.css"/>"></link>
<link type="text/css" rel="stylesheet" href="<c:out value="${contextPath}${cssPath}/contents02_pc.css"/>"></link>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp" />
		<jsp:param name="searchFormId" value="${searchFormId}" />
		<jsp:param name="listFormId" value="${listFormId}" />
	</jsp:include>
</c:if>

<div class="contents-area pl0">
	<div class="slogan-wrapper">
		<dl>
			<dt>
				<strong>심화단계 컨설팅</strong>
			</dt>
			<dd>
				기업의 경영성과 달성 저해 요소를 진단하고<span class="span-mobile-br"></span> 개선과제도출 및
				이를 <span class="span-br"></span> 해결하기 위한<span class="span-mobile-br"></span>
				솔루션을 제공하는 컨설팅
			</dd>
		</dl>
	</div>
</div>

<div class="contents-area pl0">
	<div class="images-box">
		<img src="${contextPath}/web/img/sub04/img_sub040301.png" alt="심화단계 컨설팅" />
		<div class="blind">
			<table>
				<caption>심화단계 컨설팅표 : 구분, 목적, 주요 내용, 컨설팅 결과에 관한 정보 제공표</caption>
				<thead>
					<tr>
						<th scope="col">구분</th>
						<th scope="col">목적</th>
						<th scope="col">주요 내용</th>
						<th scope="col">컨설팅 결과</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th scope="row">심층진단</th>
						<td>HR현장이슈 진단</td>
						<td>현장문제 분류 및 문제별 개선 과제 도출</td>
						<td>과제 기술서</td>
					</tr>
					<tr>
						<th scope="row">훈련체계</th>
						<td>체계적 훈련</td>
						<td>직문분석, 역량 모델링을 통한 훈련체계도 수립</td>
						<td>훈련체계 및 추가 훈련계획서</td>
					</tr>
					<tr>
						<th scope="row">현장활용</th>
						<td>훈련성과의 현장활용</td>
						<td>훈련결과를 현업에 적용해보고 보완 방안 제시</td>
						<td>현장개선 결과 및 추가 훈련계획서</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<div class="only-mobile mt20">
		<div class="gray-box center">
			<p class="word-type01">
				본 이미지는 PC버전에서 최적화되어 서비스됩니다.<br /> 이미지 확인을 원하신다면 <span
					class="point-color09">'새창으로 보기'</span> 버튼을<br /> 클릭하신 후 확인하실 수
				있습니다.
			</p>

			<div class="btns-area mt20">
				<a href="${contextPath}/web/img/sub04/img_sub040301.png" target="_blank"
					title="새창 열림" class="btn-m01 btn-color03"> 새창으로 보기 </a>
			</div>
		</div>
	</div>
</div>

	<div class="btns-area">
		<c:if test ="${loginVO.usertypeIdx eq 5}">
		<button type="button" id="open-modal01" class="btn-b01 round01 btn-color03 left" onclick="location.href='${contextPath}/web/consulting/list.do?mId=64'">
			<span>신청하기</span>
			<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01">
		</button>
		</c:if>
		
		<button type="button" id="open-modal01" class="btn-b01 round01 btn-color03 left" onclick="location.href='${contextPath}/web/consulting/cnslListAll.do?mId=95'">
			<span>목록</span>
			<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="" class="arrow01">
		</button>
	</div>


<c:if test="${!empty BOTTOM_PAGE}"><jsp:include
		page="${BOTTOM_PAGE}" flush="false" /></c:if>