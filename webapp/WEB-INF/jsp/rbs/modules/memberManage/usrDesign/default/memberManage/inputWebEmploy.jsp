<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_authInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/inputWebEmploy.jsp"/>
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
							<td class="left" id="mobile">${dt.MOBILE_PHONE }</td>
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
		
	<!-- 공단소속 테이블 -->
	<%-- <div class="contents-area">
			<div class="contents-box pl0">
			<h3 class="title-type01 ml0">공단소속</h3>
			<div class="table-type02 horizontal-scroll">
				<table summary="${summary}">
					<caption>
						공단소속 정보표 : 소속기관, 신청내역 정보표
					</caption>
					<colgroup>
						 <col style="width: 15%" />
                         <col style="width: 85%" />
					</colgroup>
					<tbody>
						<c:choose>
							<c:when test="${empty dtDoctor}">
								<tr>
									<th scope="row">소속기관</th>
									<td class="left"></td>
								</tr>
							</c:when>
							<c:when test="${!empty dtDoctor}">
								<tr>
									<th scope="row">소속기관</th>
									<c:forEach var="listDoctor" items="${dtDoctor}" varStatus="i">
									<c:if test="${listDoctor.APPLY_YN == 'Y'}"><td class="left"><span id ="regiVal" data-idx="${listDoctor.INSTT_CODE }">${listDoctor.INSTT_IDX }</span> &nbsp;${listDoctor.CLSF_CD }</td></c:if>
									<c:if test="${listDoctor.APPLY_YN != 'Y'}"></c:if>
									</c:forEach>
								</tr>
							</c:when>
						</c:choose>
						<tr>
							<th scope="row">신청내역</th>
							<td class="left">
								<c:forEach var="listDoctor" items="${dtDoctor}" varStatus="i">
									<c:if test="${empty listDoctor.APPLY_YN && listDoctor.STATUS == '2'}"><div class="btn-m03 btn-color02" style="margin:0 5px 5px 0;">신청</div></c:if>
									<c:if test="${listDoctor.STATUS == '1' || listDoctor.STATUS == '3'}"><div class="btn-m03 btn-color03" style="margin:0 5px 5px 0;">승인</div></c:if>
									<c:if test="${listDoctor.STATUS == '0'}"><div class="btn-m03 btn-color04" style="margin:0 5px 5px 0;">반려</div></c:if>
                                     <span>
                                     	<fmt:formatDate value="${listDoctor.REGI_DATE }" pattern="yyyy-MM-dd"/> &nbsp;
										<span class ="regiVal" data-idx="${listDoctor.INSTT_CODE }"> : ${listDoctor.INSTT_IDX }</span>
										<span class ="gradeVal">${listDoctor.CLSF_CD }</span><br/>
                                     </span>
								</c:forEach>
							</td>
						</tr> 
					</tbody>
				</table>	
			</div>
		</div>
		
		<div class="btns-area">
	        <button type="button" class="btn-m01 btn-color01" id="open-modal01">
	            	변경신청
	        </button>
	    </div>
	</div> --%>
		
	
	<!-- 기업바구니 모달 창 -->
	<div class="mask"></div>
    <div class="modal-wrapper" id="modal-action01">
        <h2>
            소속기관 변경신청
        </h2>
        <div class="modal-area">
            <form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
            <input type="hidden" name="memberIdx" id="memberIdx" value="<c:out value="${dt.MEMBER_IDX }"/>"/>
            <input type="hidden" name="preInsttIdx" id="preInsttIdx" value=""/>
            <input type="hidden" name="ofcps" id="ofcps" value=""/>
       		<input type="hidden" name="deptName" id="deptName" value=""/>
       		<input type="hidden" name="telNo" id="telNo" value=""/>
                <div class="contents-box pl0">
                    <div class="basic-search-wrapper">
                        <div class="one-box">
                            <dl>
                                <dt>
                                    <label>현재 소속기관</label>
                                </dt>
                                <dd>
                                    <c:choose>
										<c:when test="${empty dtDoctor}">
											<p class="word"></p>
										</c:when>
										<c:when test="${!empty dtDoctor}">
											<tr>
												<c:forEach var="listDoctor" items="${dtDoctor}" varStatus="i">
													<c:if test="${listDoctor.APPLY_YN == 'Y'}"><p class="word">${listDoctor.INSTT_IDX }</p></c:if>
												</c:forEach>
											</tr>
										</c:when>
									</c:choose>
                                </dd>
                            </dl>
                        </div>
                        
                        <div class="one-box">
                            <dl>
                                <dt>
                                    <label for="modal-textfield0101">변경소속기관</label>
                                </dt>
                                <dd>
                                    <!-- <select id="modal-textfield0101" name="">
                                        <option value=""> 서울지역본부</option>
                                    </select> -->
                                    
                                    <select id="insttIdx" name="insttIdx" style="width:150px;">
										<option value="">소속기관 선택</option>
										<c:forEach var="codeList" items="${codeList }" varStatus="i">
											<option value="${codeList.INSTT_IDX }">${codeList.INSTT_NAME}</option>
										</c:forEach>
									</select>
                                </dd>
                            </dl>
                        </div>
                        
                        <div class="one-box">
                            <dl>
                                <dt>
                                    <label for="modal-textfield0102">직급</label>
                                </dt>
                                <dd>
                                    <select id="grade" name="grade" style="width:150px;">
										<option value="">직급</option>
										<c:forEach var="gradeList" items="${gradeList }" varStatus="i">
											<option value="${gradeList.OPTION_CODE }">${gradeList.OPTION_NAME}</option>
										</c:forEach>
									</select>
                                </dd>
                            </dl>
                        </div>
                    </div>
                </div>

                <div class="btns-area">
                    <button type="submit" class="btn-m02 round01 btn-color03" form="${inputFormId}">
                        <span>신청</span>
                    </button>
                    <button type="button" class="btn-m02 round01 btn-color02">
                        <span>취소</span>
                    </button>
                </div>
            </form>
        </div>


        <button type="button" class="btn-modal-close">
            모달 창 닫기
        </button>
    </div>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>