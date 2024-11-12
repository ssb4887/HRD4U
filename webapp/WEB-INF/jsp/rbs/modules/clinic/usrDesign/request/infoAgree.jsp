<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}" />
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page"
			value="${moduleJspRPath}/clinic/request/infoAgree.jsp" />
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order" />
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}" />
<c:set var="itemObjs" value="${itemInfo.items}" />
<!-- 	<div id="cms_board_article">  id 필요시 참고용-->
<div class="contents-wrapper">
	<div class="contents-box pl0">
		<div class="agreement-wrapper">
			<h4>능력개발클리닉 운영에 관한 안내사항</h4>
			<p class="word-type03 mb20">참여기업의 원활한 능력개발클리닉 운영을 위하여 다음과 같이
				안내드립니다.</p>
			<ol class="ol-list02">
				<li>
					<span class="number">1.</span> 
					우리공단은 참여기업의 HRD 우수기업으로 성장을 목표로 자체 HRD역량 배양에 필요한 컨설팅 및 능력개발활동(비용 포함)을 지원합니다.
				</li>
				<li>
					<span class="number">2.</span> 
					참여기업은 원활한 클리닉 운영을 위하여 필수적으로 수행해야 하는 과업에 적극적으로 임해야 합니다.
				</li>
				<li>
					<span class="number">3.</span> 
					공단은 참여기업의 부정/부실 훈련이 의심되는 경우 조사가 종료될 때까지 지원을 중단할 수 있습니다.
				</li>
				<li>
					<span class="number">4.</span> 
					참여기업은 지원 제한 요건의 해당하는 다음의 사유가 발생하였을 경우 즉시 그 사실을 공단에 통보해야 합니다.
					<ul class="ul-list02">
						<li>고용보험료 체납 기업</li>
						<li>휴폐업 중인 기업</li>
						<li>임금체불명단 공개 사업장(근로기준법 제 43조 2)</li>
						<li>산재다발 사업장(산업안전보건법 제 9조의 2)</li>
					</ul>
				</li>
				<li>
					<span class="number">5.</span> 
					참여기업이 거짓이나 부정한 방법으로 능력개발클리닉 지원을 받은 경우 각종 제재 및 지원금 반환, 추가금 납부 등이 있을 수 있습니다.
				</li>
			</ol>

			<p class="word-type03 center mt20">공단은 귀사의 HRD 우수기업으로서 성장을 응원합니다.</p>

			<p class="title-type03 center mt20 mb0">HRDK 한국산업인력공단</p>
		</div>

		<div class="agree-check-box">
			<p class="word-type03 mr15">귀사는 위 사항을 숙지하여 능력개발클리닉을 신청하는 바입니다.</p>
			<div class="input-checkbox-wrapper">
				<div class="input-checkbox-area">
					<input type="checkbox" id="agree" name="checkbox01" class="checkbox-type01" title="능력개발클리닉 신청을 위해 안내사항에 동의하는 버튼"> 
					<label for="agree">동의 </label>
				</div>
			</div>
		</div>
		
		<div class="btns-area mt50">
			<button type="button" class="btn-b01 round01 btn-color03 left btn-write" id="next">
				<span>다음</span>
				<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="화살표" class="arrow01">
			</button>
			<button type="button" class="btn-b01 round01 btn-color02 left btn-cancel">
				<span>취소</span>
				<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="화살표" class="arrow01">
			</button>
	    </div>
	</div>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false" /></c:if>