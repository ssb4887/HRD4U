<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_authInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/inputWebConsult.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>

	<!-- 내정보 테이블 -->
	<div class="contents-area">
			<div class="contents-box pl0">
			<h3 class="title-type01 ml0">내정보</h3>
			<div class="table-type02 horizontal-scroll">
				<table summary="${summary}">
					<caption>
						내정보수정(민간센터) 정보표 : 성명, 아이디, 연락처, 이메일, 주소에 관한 정보 제공표
					</caption>
					<colgroup>
						 <col width="15%">
                         <col width="35%">
                         <col width="15%">
                         <col width="35%">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">성명</th>
							<td class="left">${dt.MEMBER_NAME }</td>
							<th scope="row">아이디</th>
							<td class="left">${dt.MEMBER_ID }</td>
						</tr>
						<tr>
							<th scope="row">연락처</th>
							<td id="mobile" class="left">${dt.MOBILE_PHONE }</td>
							<th scope="row">이메일</th>
							<td class="left">
								<strong class="point-color01">${dt.MEMBER_EMAIL }</strong>
								<a href="mailto:${dt.MEMBER_EMAIL }" class="btn-linked underline">
                                    <img src="../images/icon/icon_search04.png" alt="" />
                                </a>
                            </td>
						</tr>
						<tr>
							 <th scope="row">주소</th>
							<td colspan="3" class="left">${dt.ADDR1 } ${dt.ADDR2 } ${dt.ADDR3 }</td>
						</tr>
					</tbody>
				</table>	
			</div>
		</div>
		<div class="btns-area">
            <button type="button" class="btn-m01 btn-color01" id="movePage">수정</button>
        </div>
	</div>

	
	<!-- 보안각서 테이블 -->
	<div class="contents-area">
			<div class="contents-box pl0">
			<h3 class="title-type01 ml0">보안각서</h3>
			<div class="table-type02 horizontal-scroll">
			<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
			<input type="hidden" name="memberIdx" id="memberIdx" value="${dt.MEMBER_IDX}"/>
				<table summary="${summary}">
					<caption>
						소속기관 정보표 : 소속기관, 변경신청내역, 주치의, 클리닉주치의
					</caption>
					<colgroup>
						 <col style="width: 15%" />
                         <col style="width: 85%" />
					</colgroup>
					<tbody>
						<tr>
						<th scope="row">첨부파일</th>
						<td colspan="3">
							<itui:objectMultiFileForConsult itemId="file" itemInfo="${itemInfo}" />
						</td>
						</tr>
					</tbody>
				</table>	
				</form>
			</div>
		</div>
		
		<div class="btns-area">
	        <button type="submit" class="btn-m01 btn-color01" id="open-modal01" form="${inputFormId}">
	            	저장
	        </button>
	    </div>
	</div>

<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>