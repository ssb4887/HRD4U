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
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
	<div id="contents-area">
		
		<div class="contents-box pl0">
            <div class="temp-contets-area type02">알람 신청한 지부지사 공지사항의 게시글이 등록 될때 마다 카카오톡 알림톡으로 연락 받으 실 수 있습니다.</div>
        </div> 
	
		<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
        <div class="contents-box pl0">
            <div class="gray-box">
                <div class="input-checkbox-wrapper ratio">
                	<itui:objectRadioAlarm itemId="locplc" itemInfo="${itemInfo}" objDt="${dt}"/>
                </div>
            </div>
        </div>
		
		<div class="btns-area">
            <button type="submit" class="btn-b01 round01 btn-color03 left">
            	<span>신청하기</span>
                <img src="../img/icon/icon_arrow_right03.png" alt="" class="arrow01">
            </button>
            <div class="btns-right">
				<a href="<c:out value="${URL_DELETEPROC}"/>" title="삭제" class="btnTD fn_btn_delete btn-m01 btn-color01">알림신청 취소</a>
			</div>
        </div>
		</form>
	</div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>