<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId" value="fn_authInputForm"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/inputWebCorp.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>

<style>
.mask {
    position: fixed;
    top: 0;
    left: 0;
    content: "";
    display: none;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.6);
    z-index: 1000;
}

.modal-wrapper {
    position: fixed;
    top: 50%;
    left: 50%;
    display: none;
    width: 600px;
    margin-left: -300px;
    background-color: #fff;
    box-shadow: 0px 5px 5px 0px rgba(0, 0, 0, 0.15);
    z-index: 1010;
    transform: translate(0, -50%);
}

.modal-wrapper>h2 {
    position: relative;
    display: flex;
    align-items: center;
    padding: 0 30px;
    height: 60px;
    background-color: #0579cc;
    font-size: 22px;
    font-weight: 700;
    color: #fefeff;
}

.modal-area {
    max-height: calc(100vh - 200px);
    padding: 32px 30px 65px 30px;
    overflow-x: hidden;
    overflow-y: auto;
}

.modal-wrapper .basic-search-wrapper {
    padding: 10px;
    margin-bottom: 10px;
    border-bottom: 1px solid #dddddd;
}

.modal-wrapper .basic-search-wrapper dl dt {
    justify-content: center;
    height: 35px;
}

.modal-wrapper .basic-search-wrapper dl>dd {
    min-height: 35px;
    font-size: 16px;
    line-height: 24px;
}

.modal-wrapper .basic-search-wrapper dl>dd:after {
    display: block;
    content: "";
    clear: both;
}

.modal-wrapper .basic-search-wrapper dl>dd input[type="text"],
.modal-wrapper .basic-search-wrapper dl>dd input[type="password"],
.modal-wrapper .basic-search-wrapper dl>dd input[type="tel"],
.modal-wrapper .basic-search-wrapper dl>dd input[type="number"],
.modal-wrapper .basic-search-wrapper dl>dd input[type="search"],
.modal-wrapper .basic-search-wrapper dl>dd input[type="email"],
.modal-wrapper .basic-search-wrapper dl>dd input[type="url"],
.modal-wrapper .basic-search-wrapper dl>dd select {
    height: 35px;
}

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
	
	@keyframes spin {
		0% { transform: translate(-50%, -50%) rotate(0deg); }
		100% { transform: translate(-50%, -50%) rotate(360deg); }
	}
	
	/* modal table style */
	.table-container {
		min-height: 300px;
		max-height: calc(100vh - 300px);
		overflow: auto;
	}
	
	.modal-table {
		table-layout: fixed;
	}
	
	.modal-thead {
		position: sticky;
		top:0;
		z-index:1;
	}
</style>

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
	</div>
	
	<!-- 기업정보 테이블 -->
	<div class="contents-area">
		<!-- 내정보 테이블 -->
		<div class="contents-box pl0">
			<h3 class="title-type01 ml0">기업정보</h3>
			<div class="table-type02 horizontal-scroll">
				<table summary="${summary}">
					<caption>
						기업정보 정보표 : 유형, 법인명(사업자명), 사업자등록번호, 사업장관리번호, 대표자명, 전화번호 주소에 관한 정보 제공표
					</caption>
					<colgroup>
						 <col width="15%">
                         <col width="35%">
                         <col width="15%">
                         <col width="35%">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">유형</th>
							<td class="left">
								<strong class="point-color01">
									<c:out value="${dtCorp.CHK}" />
								</strong>
							</td>
							<th scope="row">법인명(사업자명)</th>
							<td class="left">${dtCorp.CORP_NM }</td>
						</tr>
						<tr>
							<th scope="row">사업자등록번호</th>
							<td class="left">${dtCorp.BIZ_NUM}</td>
							<th scope="row">사업장관리번호</th>
							<td class="left">${dtCorp.IND_LOCATIONNUM }</td>
						</tr>
						<tr>
							<th scope="row">대표자명</th>
							<td class="left">${dtCorp.REPVE_NM }</td>
							<th scope="row">전화번호</th>
							<td id="mobile" class="left">${dtCorp.REPVE_WIRE_PHON_NUM }</td>
						</tr>
						<tr>
							 <th scope="row">주소</th>
							<td colspan="3" class="left">
								<c:out value="${dtCorp.CO_ADDR }" /> &nbsp;
								<c:out value="${dtCorp.CO_ADDR_DTL }" />
							</td>
						</tr>
					</tbody>
				</table>	
			</div>
		</div>
		
		<div class="btns-area">
            <button type="button" class="btn-m01 btn-color01" id="movePage">수정</button>
        </div>
	</div>
	
	<!-- 소속기관 테이블 -->
	<div class="contents-area">
			<div class="contents-box pl0">
			<h3 class="title-type01 ml0">소속기관</h3>
			<div class="table-type02 horizontal-scroll">
				<table summary="${summary}">
					<caption>
						소속기관 정보표 : 소속기관, 변경신청내역, 주치의, 클리닉주치의
					</caption>
					<colgroup>
						 <col style="width: 15%" />
                         <col style="width: 85%" />
					</colgroup>
					<tbody>
						<c:choose>
							<c:when test="${empty dtRegi}">
								<tr>
									<th scope="row">소속기관</th>
									<td class="left">현재 소속된 기관이 없습니다.</td>
								</tr>
							</c:when>
							<c:when test="${!empty dtRegi}">
								<tr>
									<th scope="row">소속기관</th>
									<td class="left">
									<c:forEach var="listRegi" items="${dtRegi}" varStatus="i">
									<c:if test="${listRegi.APPLY_YN == 'Y'}">${listRegi.INSTT_IDX }</c:if>
									<c:if test="${listRegi.APPLY_YN != 'Y'}"></c:if>
									</c:forEach>
									</td>
								</tr>
							</c:when>
						</c:choose>
						<tr>
							<th scope="row">변경신청내역</th>
							<td class="left">
								<c:forEach var="listRegi" items="${dtRegi}" varStatus="i">
									<c:if test="${listRegi.STATUS == '2'}"><div class="btn-m03 btn-color02" style="margin:0 5px 5px 0;">신청</div></c:if>
									<c:if test="${listRegi.STATUS == '1'}"><div class="btn-m03 btn-color03" style="margin:0 5px 5px 0;">승인</div></c:if>
									<c:if test="${listRegi.STATUS == '0'}"><div class="btn-m03 btn-color04" style="margin:0 5px 5px 0;">반려</div></c:if>
                                     <span>
                                     	<fmt:formatDate value="${listRegi.REGI_DATE }" pattern="yyyy-MM-dd"/> &nbsp;
										<c:choose>
											<c:when test="${!empty listRegi.PRE_INSTT_IDX }">${listRegi.PRE_INSTT_IDX } > </c:when>
											<c:when test="${empty listRegi.PRE_INSTT_IDX }"></c:when>
										</c:choose>
										${listRegi.INSTT_IDX }<br/>
                                     </span>
								</c:forEach>
							</td>
						</tr>
						<c:choose>
							<c:when test="${empty dtDoctor }">
									<tr>
										<th scope="row">주치의</th>
										<td class="left">할당된 주치의가 없습니다.</td>
									</tr>
							</c:when>
							<c:when test="${!empty dtDoctor }">
								<c:forEach var="listDoctor" items="${dtDoctor}" varStatus="i">
									<tr>
										<th scope="row">주치의</th>
										<td class="left">${listDoctor.MEMBER_NAME } /  ${listDoctor.MEMBER_EMAIL }</td>
									</tr>
								</c:forEach>
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${!empty dtCliDoctor && empty dtCliDoctor.DOCTOR_IDX}">
								<tr>
									<th scope="row">전담주치의</th>
									<td class="left">현재 담당된 전담주치의가 없습니다.</td>
								</tr>
							</c:when>
							<c:when test="${!empty dtCliDoctor}">
								<tr>
									<th scope="row">전담주치의</th>
									<td class="left">${dtCliDoctor.MEMBER_NAME } /  ${dtCliDoctor.MEMBER_EMAIL }</td>
								</tr>
							</c:when>
						</c:choose>
					</tbody>
				</table>	
			</div>
		</div>
		
		<div class="btns-area">
	        <button type="button" class="btn-m01 btn-color01" id="open-modal01">
	            	소속기관 변경신청
	        </button>
	    </div>
	</div>
		
	
	<!-- 기업바구니 모달 창 -->
	<div class="mask"></div>
    <div class="modal-wrapper" id="modal-action01">
        <h2>
            소속기관 변경신청
        </h2>
        <div class="modal-area">
            <form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
            <input type="hidden" name="bplNo" id="bplNo" value="<c:out value="${dtCorp.IND_LOCATIONNUM }"/>"/>
                <div class="contents-box pl0">
                    <div class="basic-search-wrapper">
                        <div class="one-box">
                            <dl>
                                <dt>
                                    <label>현재 소속기관</label>
                                </dt>
                                <dd>
                                    <c:choose>
										<c:when test="${empty dtRegi}">
											<p class="word"></p>
										</c:when>
										<c:when test="${!empty dtRegi}">
											<tr>
												<c:forEach var="listRegi" items="${dtRegi}" varStatus="i">
													<c:if test="${listRegi.APPLY_YN == 'Y'}"><p class="word">${listRegi.INSTT_IDX }</p></c:if>
													<c:if test="${listRegi.APPLY_YN != 'Y'}"><p class="word"></p></c:if>
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
                    </div>
                </div>

                <div class="btns-area">
                    <button type="submit" class="btn-m02 round01 btn-color03" form="${inputFormId}">
                        <span>신청</span>
                    </button>
                    <button type="button" class="btn-m02 round01 btn-color02" onclick="closeModal();">
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