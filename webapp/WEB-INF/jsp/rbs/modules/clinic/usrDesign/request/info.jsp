<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>
<c:set var="wrtAuth" value="${elfn:isAuth('WRT')}"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/clinic/request/list.jsp"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
<div class="contents-wrapper">
	<div class="contents-area pl0">
	    <div class="slogan-wrapper">
	        <dl>
	            <dt>
	                <span >능력개발전담주치의와 함께 성장하는</span>
	                <strong>능력개발클리닉</strong>
	            </dt>
	            <dd>능력개발전담주치의가 기업을 진단하고 여건에 맞게<span class="span-mobile-br"></span> 훈련계획을 수립하고 <span class="span-br"></span>실행할 수 있도록<span class="span-mobile-br"></span> 3년간 밀착관리 합니다.</dd>
	        </dl>
	    </div>
	</div>
	<div class="contents-area">
	    <div class="contents-box" id="request">
	        <h4 class="title-type02">지원대상</h4>
	        <p class="word-type01">우선지원대상기업</p>
	    </div>
	    <div class="contents-box">
	        <h4 class="title-type02">지원요건</h4>
	        <p class="word-type01">기업HRD이음컨설팅 참여기업으로 HRD담당자(재직경력 1년 이상)를 1명 이상 보유하고 있는 기업</p>
	    </div>
	    <div class="contents-box" >
	        <h4 class="title-type02">지원내용</h4>
	        <p class="word-type01">
	            <strong class="point-color10">1,590만원 상당 지원</strong> (3년간 1개 기업 기준)
	        </p>
	    </div>
	    <div class="contents-box">
	        <h4 class="title-type02">기업선정 절차</h4>
	        <div class="process-wrapper01">
	            <dl>
	                <dt class="bg-color01">1. 모집공고</dt>
	                <dd class="center type02"><span>공단 본부</span></dd>
	            </dl>
	            <dl>
	                <dt class="bg-color02">2. 참여기업신청 </dt>
	                <dd class="center type02">
	                	<span>기업</span>
	                    <img src="${contextPath}${imgPath}/icon/icon_arrow_right07.png" alt="">
	                    <span>공단 지부&middot;지사</span>
	                </dd>
	            </dl>
	            <dl>
	                <dt class="bg-color04">3. 선정심사(요건확인)</dt>
	                <dd class="center type02"><span>공단 지부&middot;지사</span></dd>
	            </dl>
	
	            <dl>
	                <dt class="bg-color03">4. 기업선정</dt>
	                <dd class="center type02">
	                	<span>공단 지부&middot;지사  </span>
	                	<img src="${contextPath}${imgPath}/icon/icon_arrow_right07.png" alt="">
	                    <span>기업</span>
	                </dd>
	            </dl>
	        </div>
	    </div>
	</div>
	
	<div class="btns-area">
		<button type="button" class="btn-b01 round01 btn-color03 left btn-agreeInfo" >
			<span>신청하기</span>
			<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="화살표" class="arrow01">
		</button>
		<button type="button" class="btn-b01 round01 btn-color02 left btn-list">
			<span>목록</span>
			<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="화살표" class="arrow01">
		</button>
    </div>
</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>