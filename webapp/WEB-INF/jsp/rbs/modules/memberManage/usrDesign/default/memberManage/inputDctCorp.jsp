<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<c:set var="inputFormId1" value="fn_InputForm1"/>
<c:set var="inputFormId2" value="fn_inputForm2"/>
<c:set var="updateDoctor" value="updateDoctorProc.do?mId=78"/>
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="page_tit" value="${settingInfo.input_title}"/>
		<jsp:param name="javascript_page" value="${moduleJspRPath}/inputDctCorp.jsp"/>
		<jsp:param name="inputFormId" value="${inputFormId}"/>
	</jsp:include>
</c:if>
<c:set var="itemOrderName" value="${submitType}_order"/>
<c:set var="itemOrder" value="${itemInfo[itemOrderName]}"/>
<c:set var="itemObjs" value="${itemInfo.items}"/>
<c:set var="isApplied" value="Y" />
<c:forEach var="listRegi" items="${dtRegi}">
	<c:if test="${empty listRegi.APPLY_YN}">
		<c:set var="isApplied" value="N" />
	</c:if>
</c:forEach>
<c:forEach var="listDoctor" items="${dtDoctor }">
	<c:if test="${listDoctor.CLI_DOCTOR_YN == 'N' }">
		<c:set var="doctorNormal" value="${listDoctor}" />
	</c:if>
	<c:if test="${listDoctor.CLI_DOCTOR_YN == 'Y' }">
		<c:set var="doctorCLI" value="${listDoctor }" />
	</c:if>
</c:forEach>

<div id="cms_board_article" class="contents-wrapper">
		
		<div class="contents-area">
			<div class="contents-box pl0">
				<h3 class="title-type02 ml0">내정보</h3>
				<div class="table-type02 horizontal-scroll">
					<table summary="${summary}">
						<caption>기본정보표 : 성명, 아이디, 연락처, 이메일, 주소에 관한 정보 제공표</caption>
						<colgroup>
							<col style="width:15%">
							<col style="width:35%">
							<col style="width:15%">
							<col style="width:35%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">성명</th>
								<td class="left">${dt.MEMBER_NAME }</td>
								<th scope="row">아이디</th>
								<td>${dt.MEMBER_ID }</td>
							</tr>
							<tr>
								<th scope="row">연락처</th>
								<td class="left" id="mobile">${dt.MOBILE_PHONE }</td>
								<th scope="row">이메일</th>
								<td>${dt.MEMBER_EMAIL }</td>
							</tr>
							<tr>
								<th scope="row">주소</th>
								<td colspan="3" class="left">${dt.ADDR1 } ${dt.ADDR2 } ${dt.ADDR3 }</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="contents-box pl0">
				<h3 class="title-type02 ml0">기업정보</h3>
				<div class="table-type02 horizontal-scroll">
					<table summary="${summary}">
						<caption>기업정보 정보표 : 유형, 법인명(사업자명), 사업자등록번호, 사업장관리번호, 대표자명, 전화번호 주소에 관한 정보 제공표</caption>
						<colgroup>
							<col style="width:15%">
							<col style="width:35%">
							<col style="width:15%">
							<col style="width:35%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">유형</th>
								<td class="left"><c:out value="${dtCorp.CHK}" /></td>
								<th scope="row">법인명(사업자명)</th>
								<td colspan="3" class="left">${dtCorp.CORP_NM}</td>
							</tr>
							<tr>
								<th scope="row">사업자등록번호</th>
								<td class="left">${dtCorp.BIZ_NUM}</td>
								<th scope="row">사업장관리번호</th>
								<td class="left">${dtCorp.IND_LOCATIONNUM}</td>
							</tr>
							<tr>
								<th scope="row">대표자명</th>
								<td class="left">${dtCorp.REPVE_NM }</td>
								<th scope="row">전화번호</th>
								<td class="left">${dtCorp.REPVE_WIRE_PHON_NUM }</td>
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
			
			<!-- 소속기관 -->
			<div class="contents-box pl0">
				<h3 class="title-type02 ml0">소속기관</h3>
				<div class="table-type02 horizontal-scroll">
				<%-- <form id="${inputFormId1}" name="${inputFormId1}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data"> --%>
				<input type="hidden" name="statusVal" id="statusVal" value=""/>
				<input type="hidden" name="PSITN" id="PSITN" value=""/>
					<table summary="${summary}">
						<caption>소속기관 정보표 : 소속기관, 변경신청내역, 주치의, 클리닉주치의</caption>
						<colgroup>
							<col style="width:15%">
							<col style="width:85%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">소속기관</th>
								<c:choose>
									<c:when test="${empty dtRegi}">
										<td class="left">
												<select id="region" name="region" style="width:150px;">
													<option value="">소속기관 선택</option>
													<c:forEach var="codeList" items="${codeList }" varStatus="i">
														<option value="${codeList.INSTT_IDX }">${codeList.INSTT_NAME}</option>
													</c:forEach>
												</select>
												<button type="submit" class="btn-m01 btn-color02 open-modal04">저장</button>
											</td>
									</c:when>
									<c:when test="${!empty dtRegi}">
										<c:set var="listOrg" value="${dtRegi }"/>
										<c:forEach var="listOrg" items="${dtRegi}" varStatus="i">
										<c:if test="${listOrg.APPLY_YN == 'Y'}">
											<td class="left">
												<select id="region" name="region" style="width:150px;">
													<option value="">소속기관 선택</option>
													<c:forEach var="codeList" items="${codeList }" varStatus="i">
														<c:if test="${codeList.INSTT_IDX == listOrg.INSTT_CODE }">
															<option value="${codeList.INSTT_IDX }" selected="selected">${codeList.INSTT_NAME}</option>
														</c:if>
														<c:if test="${codeList.OPTION_CODE != listOrg.INSTT_CODE }">
															<option value="${codeList.INSTT_IDX }">${codeList.INSTT_NAME}</option>
														</c:if>
													</c:forEach>
												</select>
												<button type="submit" class="btn-m01 btn-color02 open-modal04">저장</button>
											</td>
										</c:if>
										</c:forEach>
									</c:when>
								</c:choose>
							</tr>
							<tr>
								<th scope="row">변경신청내역</th>
								<td class="left">
									<c:forEach var="listRegi" items="${dtRegi}" varStatus="i">
									<input type="hidden" name="PSITN_INSTT_IDX" class="PSITN_INSTT_IDX" value="<c:out value="${listRegi.PSITN_INSTT_IDX}"/>"/>
									<c:if test="${listRegi.STATUS == 2}">
										<select class="status" name="status" style="width:80px; margin:0 5px 5px 0;">
											<option value="">선택</option>
											<option value="Y">승인</option>
											<option value="N">반려</option>
										</select>
									</c:if>
									<c:if test="${listRegi.STATUS == 1}"><div class="btn-m03 btn-color03" style="width:80px; height:50px; line-height:50px; font-size:17px; margin:0 5px 5px 0;">승인</div></c:if>
									<c:if test="${listRegi.STATUS == 0}"><div class="btn-m03 btn-color04" style="width:80px; height:50px; line-height:50px; font-size:17px; margin:0 5px 5px 0;">반려</div></c:if>
										<fmt:formatDate value="${listRegi.CONFM_DT }" pattern="yyyy-MM-dd"/> &nbsp;
										<c:choose>
											<c:when test="${!empty listRegi.PRE_INSTT_IDX }"><span id="PRE_INSTT_IDX${i.index}" data-val="${listRegi.PRE_INSTT_CODE }">${listRegi.PRE_INSTT_IDX }</span> > </c:when>
											<c:when test="${empty listRegi.PRE_INSTT_IDX }">무소속 > </c:when>
										</c:choose>
										<span id="INSTT_IDX${i.index}" data-val="${listRegi.INSTT_CODE }">${listRegi.INSTT_IDX }</span>&nbsp;
										<span>[승인자(ID) : ${listRegi.CONFMER_NAME}(${listRegi.CONFMER_ID})]<br/></span>
									</c:forEach>
								</td>
							</tr>
						</tbody>
					</table>
				<!-- </form> -->
			</div>
		</div>
		
		<!-- 주치의 -->
		<div class="contents-box pl0">
				<h3 class="title-type02 ml0">주치의</h3>
				<div class="table-type02 horizontal-scroll">
				<form id="doctorNormal" method="post" action="<c:out value="${updateDoctor}"/>" target="submit_target">
					<table summary="${summary}">
						<caption>소속기관 정보표 : 소속기관, 변경신청내역, 주치의, 클리닉주치의</caption>
						<colgroup>
							<col style="width:15%">
							<col style="width:85%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">주치의 검색</th>
								<td class="left">
									<input type="text" id="doctor-normal-input" style="width:400px" value="" required>
									<input type="hidden" name="DOCTOR_IDX" id="doctor-normal-idx" value="" />
									<input type="hidden" name="BPL_NO" id="doctor-bpl-no" value="${dtOrg.BPL_NO }" />
									<input type="hidden" name="CLI_DOCTOR_YN" value="N" />
									<button type="button" class="btn-m01 btn-color03 open-modal13">찾기</button>
									<button type="submit" class="btn-m01 btn-color02" form="doctorNormal">저장</button>
								</td>
							</tr>
							<tr>
								<th scope="row">주치의</th>
								<td class="left">
									<c:choose>
										<c:when test="${!empty doctorNormal}">
											<fmt:formatDate value="${doctorNormal.LAST_MODI_DATE }" pattern="yyyy-MM-dd"/> &nbsp;/ ${dtDoctor.MEMBER_NAME } / ${dtDoctor.MEMBER_EMAIL }  [승인자(ID) : ${doctorNormal.LAST_MODI_NAME }(${doctorNormal.LAST_MODI_ID })] 
										</c:when>
									</c:choose>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
		<!-- 전담 주치의 -->
		<c:if test="${!empty dtCliDoctor }">
		<div class="contents-box pl0">
				<h3 class="title-type02 ml0">전담 주치의</h3>
				<div class="table-type02 horizontal-scroll">
				<table summary="${summary}">
					<caption>소속기관 정보표 : 소속기관, 변경신청내역, 주치의, 클리닉주치의</caption>
					<colgroup>
						<col style="width:15%">
						<col style="width:85%">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">전담 주치의</th>
							<td class="left">
								<c:choose>
									<c:when test="${empty dtCliDoctor.DOCTOR_IDX}">
										<span>현재 담당된 전담주치의가 없습니다.</span> 
									</c:when>
									<c:otherwise>
										<fmt:formatDate value="${dtCliDoctor.REGI_DATE }" pattern="yyyy-MM-dd"/> &nbsp;/ ${dtCliDoctor.MEMBER_NAME } /  ${dtCliDoctor.MEMBER_EMAIL } 
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		</c:if>
		
		<div class="btns-area">
		           <div class="btns-right">
		               <a href="javascript:history.back();" class="btn-m01 btn-color01">목록</a>
		           </div>
		       </div>
		<!-- 클리닉 메뉴에서 등록으로 변경 -->
		<%-- <div class="contents-box pl0">
				<h3 class="title-type02 ml0">전담 주치의</h3>
				<div class="table-type02 horizontal-scroll">
				<form id="doctorCli" method="post" action="<c:out value="${updateDoctor}"/>" target="submit_target">
					<table summary="${summary}">
						<caption>소속기관 정보표 : 소속기관, 변경신청내역, 주치의, 클리닉주치의</caption>
						<colgroup>
							<col style="width:15%">
							<col style="width:85%">
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">전담 주치의</th>
								<td class="left">
									<input type="text" id="doctor-cli-input" style="width:400px" value="" required>
									<input type="hidden" name="DOCTOR_IDX" id="doctor-cli-idx" value="" />
									<input type="hidden" name="BPL_NO" id="doctor-bpl-no" value="${dtOrg.BPL_NO }" />
									<input type="hidden" name="CLI_DOCTOR_YN" value="Y" />
									<button type="button" class="btn-m01 btn-color03 open-modal05">찾기</button>
									<button type="submit" class="btn-m01 btn-color02" form="doctorCli">저장</button>
								</td>
							</tr>
							<tr>
								<th scope="row">주치의변경내역</th>
								<td class="left">
									<c:choose>
										<c:when test="${!empty doctorCLI}">
											<fmt:formatDate value="${doctorCLI.REGI_DATE }" pattern="yyyy-MM-dd"/> &nbsp;/ ${doctorCLI.MEMBER_NAME } / ${doctorCLI.MOBILE_PHONE } / ${doctorCLI.MEMBER_EMAIL } 
										</c:when>
									</c:choose>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div> --%>
	</div>
		
	<!-- 소속기관 모달 창 -->
	<div class="mask"></div>
    <div class="modal-wrapper" id="modal-alert01">
        <h2>
            소속기관 변경신청
        </h2>
        <div class="modal-area">
            <form id="${inputFormId1}" name="${inputFormId1}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
            	<input type="hidden" name="statusVal" id="statusVal" value=""/>
				<input type="hidden" name="PSITN" id="PSITN" value=""/>
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
                                    
                                    <select id="region" name="region" style="width:150px;">
										<option value="">소속기관 선택</option>
										<c:forEach var="codeList" items="${codeList }" varStatus="i">
											<option value="${codeList.OPTION_CODE }">${codeList.OPTION_NAME}</option>
										</c:forEach>
									</select>
                                </dd>
                            </dl>
                        </div>
                    </div>
                </div>

                <div class="btns-area">
                    <button type="submit" class="btn-m02 round01 btn-color03" form="${inputFormId1}">
                        <span>신청</span>
                    </button>
                    <button type="button" class="btn-m02 round01 btn-color02 modal-close">
                        <span>취소</span>
                    </button>
                </div>
            </form>
        </div>
        
	        <div class="modal-wrapper" id="modal-action02">
	        <h2>
	            소속기관 변경신청
	        </h2>
	        <div class="modal-area">
	            <form id="${inputFormId1}" name="${inputFormId1}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
	            	<input type="hidden" name="statusVal" id="statusVal" value=""/>
					<input type="hidden" name="PSITN" id="PSITN" value=""/>
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
	                                    
	                                    <select id="region" name="region" style="width:150px;">
											<option value="">소속기관 선택</option>
											<c:forEach var="codeList" items="${codeList }" varStatus="i">
												<option value="${codeList.OPTION_CODE }">${codeList.OPTION_NAME}</option>
											</c:forEach>
										</select>
	                                </dd>
	                            </dl>
	                        </div>
	                    </div>
	                </div>
	
	                <div class="btns-area">
	                    <button type="submit" class="btn-m02 round01 btn-color03" form="${inputFormId1}">
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
		</div>
	</div>
		
<!-- 정민 추가 -->
<div class="modal-wrapper" id="modal-action04">
	<h2>알림</h2>
	<div class="modal-area">
		<div class="modal-alert">
			<p id="ment">공단소속이 “울산지사”에서<br /> “서울지역본부＂로 변경됩니다.<br /> 변경하시겠습니까?</p>
		</div>
		<div class="btns-area">
			<button type="submit" class="btn-m02 round01 btn-color03" form="inputCorp2Proc">
				<span>확인</span>
			</button>		
			<button type="button" class="btn-m02 round01 btn-color02" onclick="closeModal();">
				<span>취소</span>
			</button>
		</div>
	</div>
	<button type="button" class="btn-modal-close modal-close">모달 창 닫기</button>
</div>
<!-- 수빈 추가 수정 -->
<div class="modal-wrapper" id="modal-action03">
	<h2>알림</h2>
	<div class="modal-area">
		<div class="modal-alert">
			<p id="mentUpdate">공단소속이 “울산지사”에서<br /> “서울지역본부＂로 변경됩니다.<br /> 변경하시겠습니까?</p>
		</div>
		<div class="btns-area">
			<button type="submit" class="btn-m02 round01 btn-color03" form="inputCorp1Proc">
				<span>확인</span>
			</button>		
			<button type="button" class="btn-m02 round01 btn-color02" onclick="closeModal();">
				<span>취소</span>
			</button>
		</div>
	</div>
	<button type="button" class="btn-modal-close modal-close">모달 창 닫기</button>
</div>

<!-- 모달 창3 -->
<div class="modal-wrapper" id="modal-action13">
	<h2>주치의 변경</h2>
	<div class="modal-area">
		<div class="contents-box pl0">
			<form id="doctor-search">
			<div class="basic-search-wrapper">
				<div class="one-box">
					<dl>
						<dt>
							<label for="modal-textfield00">지부지사</label>
						</dt>
						<dd>
							<select id="modal-textfield00" name="insttName" class="select-type02">
								<option value="">선택</option>
								<c:forEach var="item" items="${codeList }" varStatus="i">
								<option value="${item.INSTT_NAME }">${item.INSTT_NAME }</option>
								</c:forEach>
							</select>
						</dd>
					</dl>
				</div>
                   <div class="one-box">
                       <dl>
                           <dt>
                           <label for="modal-textfield04">
                              	이름
                           </label>
                       </dt>
                           <dd>
                               <input type="text" id="modal-textfield04" name="doctorName" value="" title="이름 입력" placeholder="이름 입력" />
                           </dd>
                       </dl>
                   </div>
                   </div>
                   </form>
                   <div class="btns-area">
                       <button type="button" class="btn-m02 btn-color02 round01" onclick="doctorSearch()">검색</button>
                   </div>
               </div>

               <div class="contents-box pl0">
                   <p class="total mb05">
                       총 <strong id="doctor-cnt">0</strong> 건
                   </p>

                   <div class="table-type02 horizontal-scroll">
                       <table class="width-type03">
                           <caption>
                           	주치의에 해당하는 회원의 이름, 지부지사에 해당하는 정보 제공표
                           </caption>
                           <colgroup>
                               <col style="width: 20%" />
                               <col style="width: 50%" />
                               <col style="width: 10%" />
                               <col style="width: *" />
                           </colgroup>
                           <thead>
                               <tr>
                                   <th scope="col" colspan="3">
                                   	주치의 이름 및 지부지사
                                   </th>
                                   <th scope="col">
                                   	선택
                                   </th>
                               </tr>
                           </thead>
                           <tbody id="docbody"></tbody>
                       </table>
                   </div>
               </div>
		</div>
	<button type="button" class="btn-modal-close modal-close">모달 창 닫기</button>
</div>
<!-- //모달 창2 -->

<form id="inputCorp1Proc" method="post" action="inputDctCorp1Proc.do?mId=78" target="submit_target" enctype="multipart/form-data">
	<input type="hidden" name="memIdx" id="memIdx" value="${dt.MEMBER_IDX}"/>
	<input type="hidden" name="bplNo" id="bplNo" value="${dtOrg.BPL_NO }"/>
	<input type="hidden" name="PSITN" id="PSITN" value=""/>
	<input type="hidden" name="statusVal" id="statusVal" value=""/>
	<input type="hidden" id="is-applied" value="${isApplied }" />
</form>
<form id="inputCorp2Proc" method="post" action="inputDctCorp2Proc.do?mId=78" target="submit_target" enctype="multipart/form-data">
	<input type="hidden" name="bplNo" id="bplNo" value="${dtOrg.BPL_NO }" />
	<input type="hidden" name="pre_instt" id="pre_instt" value="" />
	<input type="hidden" name="instt" id="instt" value="" />
	<input type="hidden" name="memIdx" id="memIdx" value="${dt.MEMBER_IDX}"/>
	<input type="hidden" name="docIdx" id="docIdx" value=""/>
</form>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page = "${BOTTOM_PAGE}" flush = "false"/></c:if>