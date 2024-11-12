<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<%@ taglib prefix="elui" uri="/WEB-INF/tlds/el-tag.tld"%>

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
<style>
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
	span.update-max { margin-left: 32px; border: solid 1px; padding: 4px; border-radius: 8px; color: white; background-color: gray; cursor: pointer; }
	
	@keyframes spin {
		0% { transform: translate(-50%, -50%) rotate(0deg); }
		100% { transform: translate(-50%, -50%) rotate(360deg); }
	}
</style>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/ntwrk/cmptinst.js"/>"></script>

<div id="overlay"></div>
<div class="loader"></div>

	<!-- contents  -->
	<div class="contents-wrapper">
		<div class="contents-area">
			<form id="form-box" method="POST">
				<input type="hidden" id="idx" name="idx" />
				<input type="hidden" name="cmptinstIdx" value="<c:out value='${result.CMPTINST_IDX}' />" />
				<legend class="blind">협의체 등록</legend>
				<div class="board-write mb10">
					<div class="one-box">
						<dl>
							<dt>
								<label for="textfield01">
									기관명
									<strong class="point-important">*</strong>
								</label>
							</dt>
							<dd>
								<input type="text" id="textfield01" name="cmptinstName" value="<c:out value='${result.CMPTINST_NAME}'/>" placeholder="기관명 입력" class="w100" data-label="기관명" maxlength="100">
							</dd>
						</dl>
					</div>
					
					<div class="one-box">
						<dl>
							<dt>
								<label for="textfield02">
									소재지
								</label>
							</dt>
							
							<dd>
								<div class="input-add-btns-wrapper">
									<div style="display:flex">
										<input type="text" id="zip" name="zip" value="<c:out value='${result.ZIP}' />" disabled style="flex:3; margin-right:10px;" data-label="우편번호"/>
										<input type="text" id="addr" name="addr" value="<c:out value='${result.ADDR}' />" disabled style="flex:7" data-label="주소">
									</div>
									<button type="button" class="btn-color02" id="btn-addr">
										주소검색
									</button>
								</div>
								<input type="text" id="textfield02" name="addrDtl" value="<c:out value='${result.ADDR_DTL}' />" placeholder="추가 입력" class="w100 mt10" data-label="상세주소" maxlength="200">
							</dd>
						</dl>
					</div>
					
					<div class="one-box">
						<dl>
							<dt>
								<label for="textfield03">
									대표자(기관장)
								</label>
							</dt>
							<dd>
								<input type="text" id="textfield03" name="cmptinstReperNm" value="<c:out value='${result.CMPTINST_REPER_NM}' />" placeholder="대표자명 입력" class="w100" data-label="대표자(기관장))" maxlength="100">
							</dd>
						</dl>
					</div>
					
					<div class="one-box">
						<dl>
							<dt>
								<label for="textfield04">
									담당자
								</label>
							</dt>
							<dd>
								<input type="text" id="textfield04" name="cmptinstPicName" value="<c:out value='${result.CMPTINST_PIC_NAME}' />" placeholder="담당자명 입력" class="w100" data-label="담당자명" maxlength="100">
							</dd>
						</dl>
					</div>
					
					<div class="one-box">
						<dl>
							<dt>
								<label for="textfield05">
									담당자 연락처
								</label>
							</dt>
							<dd>
								<input type="text" id="textfield05" name="cmptinstPicTelno" value="<c:out value='${result.CMPTINST_PIC_TELNO}' />" placeholder="담당자 연락처 입력" class="w100" data-label="담당자 연락처" maxlength="30" oninput="formatPhoneNumber(this);" >
							</dd>
						</dl>
					</div>
					
					<div class="one-box">
						<dl>
							<dt>
								<label for="textfield06">
									담당자 이메일
								</label>
							</dt>
							<dd>
								<input type="text" id="textfield06" name="cmptinstPicEmail" value="<c:out value='${result.CMPTINST_PIC_EMAIL}' />" placeholder="담당자 이메일 입력" class="w100" data-label="담당자 이메일" maxlength="50" oninput="validationEmail(this);">
								<span id="emailError" style="display:none; color:red;"></span>
							</dd>
						</dl>
					</div>
					
					<div class="one-box">
						<dl>
							<dt>
								<label for="textfield08">
									소속 기관
								</label>
							</dt>
							<dd>
								<c:choose>
									<c:when test="${empty insttCodeList and empty result}">
										<select id="textfield08" name="insttIdx" class="w50" data-label="소속기관">
											<option value="<c:out value='${myInsttIdx}' />"><c:out value='${myInsttName}' /></option>
										</select>
									</c:when>
									<c:when test="${empty insttCodeList and not empty result}">
										<select id="textfield08" name="insttIdx" class="w50" data-label="소속기관">
											<option value="<c:out value='${result.INSTT_IDX}' />"><c:out value='${result.INSTT_NAME}' /></option>
										</select>
									</c:when>
									<c:otherwise>
										<select id="textfield08" name="insttIdx" class="w50" data-label="소속기관">
											<c:forEach items="${insttCodeList}" var="list">
												<option value="<c:out value="${list.INSTT_IDX}" />" <c:if test="${list.INSTT_IDX eq result.INSTT_IDX}">selected</c:if>>
													<c:out value="${list.INSTT_NAME}" />
												</option>
											</c:forEach>
										</select>
									</c:otherwise>
								</c:choose>
							</dd>
						</dl>
					</div>
				</div>
				
				<c:if test="${empty result}">
					<p class="word-type02 point-color04">※ 등록 완료 후 네트워크 이력을 작성하실 수 있습니다. </p>
				</c:if>
				
				<div class="btns-area mt60">
					<div class="btns-right">
						<c:choose>
							<c:when test="${not empty result}">
								<button type="button" id="btn-insert" class="btn-m01 btn-color03 depth2">
									수정
								</button>
							</c:when>
							<c:otherwise>
								<button type="button" id="btn-insert" class="btn-m01 btn-color03 depth2">
									등록
								</button>
							</c:otherwise>
						</c:choose>
						<a href="${contextPath}/dct/cmptinst/list.do?mId=69" class="btn-m01 btn-color01 depth2">
							목록
						</a>
					</div>
				</div>
			</form>
		</div>
		
		<c:if test="${not empty result}">
			<div class="contents-area">
				<div class="contents-box pl0">
					<div class="title-wrapper">
						<h3 class="title-type02 ml0 fl">
							네트워크 이력
						</h3>
						
						<div class="fr">
							<a href="javascript:void(0)" class="btn-m01 btn-color01" onclick="goNtwrk()">
								추가
							</a>
						</div>
					</div>
					
					<div class="table-type01 horizontal-scroll">
						<table>
							<caption>
								네트워크 이력표 : 번호, 주관기관, 기관명, 협약명, 기간, 유효에 관한 정보 제공표
							</caption>
							<colgroup>
	                            <col style="width: 8%">
	                            <col style="width: 14%">
	                            <col style="width: 18%">
	                            <col style="width: 30%">
	                            <col style="width: 20%">
	                            <col style="width: 10%">
	                        </colgroup>
	                        <thead>
	                        	<tr>
	                        		<th scope="col">번호</th>
	                       			<th scope="col">주관기관</th>
	                       			<th scope="col">기관명</th>
	                       			<th scope="col">협약명</th>
	                       			<th scope="col">기간</th>
	                       			<th scope="col">유효</th>
	                     		</tr>
	                   		</thead>
	                   		
	                   		<tbody>
	                   			<c:if test="${empty ntwrkList}">
	                   				<tr>
		                   				<td colspan="6">
		                   					<spring:message code="message.no.list"/>
		                   				</td>
	                   				</tr>
	                   			</c:if>
	                   			<c:forEach items="${ntwrkList}" var="ntwrk" varStatus="status">
	                   				<tr>
	                   					<td><c:out value='${fn:length(ntwrkList) - status.index}' /></td>
		                   				<td><c:out value='${ntwrk.INSTT_NAME}' /></td>
		                   				<td><c:out value='${ntwrk.CMPTINST_NAME}'/></td>
		                   				<td>
		                   					<a href="javascript:void(0)" onclick="goNtwrk(<c:out value='${ntwrk.NTWRK_IDX}' />)">
		                   						<strong class="point-color01"><c:out value='${ntwrk.AGREM_NAME}' /></strong>
		                 					</a>
		                 				</td>
		                 				<td>
		                 					<fmt:formatDate pattern="YYYY-MM-dd" value="${ntwrk.BGNDT}"/> ~ 
		                 					<fmt:formatDate pattern="YYYY-MM-dd" value="${ntwrk.ENDDT}"/>
		                 				</td>
		                 				<td><c:out value='${ntwrk.USE_YN}' /></td>
	                   				</tr>
	                   			</c:forEach>
	                 		</tbody>
	                 	</table>
	                 </div>
	            </div>
	        </div>
		</c:if>
        
        <c:if test="${not empty result}">
	        <div class="contents-area">
				<div class="contents-box pl0">
					<div class="title-wrapper">
						<h3 class="title-type02 ml0 fl">
							협약 기업
						</h3>
						<div class="fr">
							<a href="javascript:void(0);" class="btn-m01 btn-color01" onclick="openAgremCorpModal()">
								엑셀업로드
							</a>
							<a href="javascript:void(0);" class="btn-m01 btn-color01" onclick="openAgremCorpAddModal()" >
								추가
							</a>
							<a href="javascript:void(0);" class="btn-m01 btn-color02" onclick="deleteSelectedAgremCorp()">
								선택삭제
							</a>
						</div>
					</div>
					
					<div class="table-type01 horizontal-scroll">
						<table>
							<caption>
								협약기업표 : 번호, 관할기관명, 협약번호, 사업장번호, 회사명, 협약년도에 관한 정보 제공표
							</caption>
							<colgroup>
	                            <col style="width: 5%">
	                            <col style="width: 5%">
	                            <col style="width: 10%">
	                            <col style="width: 10%">
	                            <col style="width: 10%">
	                            <col style="width: 15%">
	                            <col style="width: 8%">
	                            <col style="width: 8%">
	                            <col style="width: 5%">
	                            <col style="width: 10%">
	                            <col style="width: 10%">
	                            <col style="width: 10%">
	                        </colgroup>
	                        <thead>
	                        	<tr>
	                        		<th scope="col"><input type="checkbox" class="checkbox-type01" name="checkbox-cmptinst" id="checkbox-all" /></th>
	                        		<th scope="col">번호</th>
	                       			<th scope="col">소속기관</th>
	                       			<th scope="col">협약번호</th>
	                       			<th scope="col">사업장번호</th>
	                       			<th scope="col">회사명 </th>
	                       			<th scope="col">협약년도</th>
	                       			<th scope="col">회원아이디</th>
	                       			<th scope="col">협약여부</th>
	                       			<th scope="col">협약구분</th>
	                       			<th scope="col">사업자번호</th>
	                       			<th scope="col">협약체결일</th>
	                     		</tr>
	                   		</thead>
	                   		
	                   		<tbody>
	                   			<c:if test="${empty agremCorpList}">
	                   				<tr>
	                   					<td colspan="12"><spring:message code="message.no.list"/></td>
	                   				</tr>
	                   			</c:if>
	                   			<c:forEach items="${agremCorpList}" var="agremCorp" varStatus="status">
	                   				<tr id="agremCorp_<c:out value='${agremCorp.AGREM_CORP_IDX}' />">
	                   					<td>
		                 					<input type="checkbox" class="checkbox-type01" name="checkbox-cmptinst" value="<c:out value='${agremCorp.AGREM_CORP_IDX}' />" />
		                 				</td>
		                 				<td><c:out value='${fn:length(agremCorpList) - status.index}' /></td>
		                 				<td data-key="insttIdx"><c:out value='${agremCorp.INSTT_NAME}' /></td>
		                 				<td data-key="agremNo"><c:out value='${agremCorp.AGREM_NO}' /></td>
		                 				<td data-key="bplNo"><c:out value='${agremCorp.BPL_NO}' /></td>
		                 				<td>
		                 					<a href="javascript:void(0)" data-idx="<c:out value='${agremCorp.AGREM_CORP_IDX}' />" onclick="openModalForModify(event)">
			                 					<strong class="point-color01" data-key="cmpnyNm"><c:out value='${agremCorp.CMPNY_NM}' /></strong>
		                 					</a>
		                 				</td>
		                 				<td data-key="agremYear"><c:out value='${agremCorp.AGREM_YEAR}' /></td>
		                 				<td data-key="mberId"><c:out value='${agremCorp.MBER_ID}' /></td>
		                 				<td data-key="agremYn"><c:out value='${agremCorp.AGREM_YN}' /></td>
		                 				<td data-key="agremSe"><c:out value='${agremCorp.AGREM_SE}' /></td>
		                 				<td data-key="bizrNo"><c:out value='${agremCorp.BIZR_NO}' /></td>
		                 				<td data-key="agremCnclsDe"><fmt:formatDate pattern="YYYY-MM-dd" value="${agremCorp.AGREM_CNCLS_DE}"/></td>
	                   				</tr>
	                   			</c:forEach>
	                 		</tbody>
	                 	</table>
					</div>
				</div>
			</div>
        </c:if>
	</div>

<!-- 엑셀 업로드 모달창 -->
	<div class="mask"></div>
    <div class="modal-wrapper" id="modal-excelupload">
    	<h2>협약기업 엑셀 업로드</h2>
        <div class="modal-area">
        	<form action="" id="excelUploadForm">
				<div class="contents-box pl0">
					<div class="basic-search-wrapper">
						<div class="one-box">
							<dl>
								<dt>
									<label for="sample">샘플 파일</label>
								</dt>
								<dd class="block">
									<p class="word-linked01" id="sample">
										<a href="${contextPath}/dct/cmptinst/downloadExcel.do?mId=69&sample=2">협약기업 업로드 샘플 파일.xlsx</a>
									</p>
								</dd>
							</dl>
						</div>
	
						<div class="one-box">
							<dl>
								<dt>
									<label for="excelFile">첨부 파일</label>
								</dt>
								<dd class="block">
									<div class="fileBox">
										<input type="text" id="fileName" class="fileName" readonly="readonly" placeholder="파일찾기">
										<label for="input-file" class="btn_file">찾아보기</label>
										<input type="file" id="input-file" class="input-file" required onchange="javascript:document.getElementById('fileName').value = this.value">
									</div>
								</dd>
							</dl>
						</div>
	
						<div class="one-box">
							<dl>
								<dt>
									<label> 비고 </label>
								</dt>
								<dd class="block">
									<p class="word">
										<strong>엑셀 업로드를 이용한 데이터입력은 다운받은 엑셀 서식을 이용하여 업로드해주시기 바랍니다.</strong>
									</p>
								</dd>
							</dl>
						</div>
					</div>
				</div>
	
				<div class="btns-area">
					<button type="button" class="btn-b02 round01 btn-color03 left" onclick="uploadAgremCorpExcel()">
						<span>업로드</span>
						<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="업로드 버튼 아이콘" class="arrow01" />
					</button>
				</div>
			</form>
		</div>

        <button type="button" class="btn-modal-close">모달 창 닫기</button>
  </div>
<!-- 엑셀 업로드 모달창 끝 -->

<!-- 협약기업 추가 모달창 -->
<div class="mask"></div>
    <div class="modal-wrapper" id="modal-agremCorp">
    	<h2>협약기업 정보</h2>
        <div class="modal-area">
        	<form action="" id="agremCorpForm">
				<div class="contents-box pl0">
					<div class="basic-search-wrapper">
						<input type="hidden" id="agremCorpIdx" name="agremCorpIdx" value="" />
						<div class="one-box">
							<dl>
								<dt>
									<label for="agrem-textfield01">회사명</label>
									<strong class="point-important">*</strong>
	                            </dt>
	                            <dd>
	                            	<input type="text" id="agrem-textfield01" name="cmpnyNm" value="" maxlength="100" title="회사명 입력" placeholder="회사명" />
                            	</dd>
                           	</dl>
                       	</div>
						<div class="one-box">
							<dl>
								<dt>
									<label for="agrem-textfield02">사업장 관리번호</label>
	                            </dt>
	                            <dd>
	                            	<input type="number" name="bplNo" id="agrem-textfield02" value="" maxlength="11" title="사업장 관리번호 입력" placeholder="사업장 관리번호" onKeyup="this.value=this.value.replace(/[^0-9]/g,'').substring(0,11);" />
                            	</dd>
                           	</dl>
                       	</div>
						<div class="one-box">
							<dl>
								<dt>
									<label for="agrem-textfield03">사업자 번호</label>
	                            </dt>
	                            <dd>
	                            	<input type="number" name="bizrNo" id="agrem-textfield03" value="" maxlength="20" title="사업자번호 입력" placeholder="사업자번호 " onKeyup="this.value=this.value.replace(/[^0-9]/g,'').substring(0,10);" />
                            	</dd>
                           	</dl>
                       	</div>
						<div class="one-box">
							<dl>
								<dt>
									<label for="agrem-textfield04">소속기관</label>
	                            </dt>
	                            <dd>
	                            	<select name="insttIdx" id="agrem-textfield04">
	                            		<option value="">소속기관</option>
	                            		<c:forEach items="${insttCodeList}" var="list">
											<option value="<c:out value="${list.INSTT_IDX}" />">
												<c:out value="${list.INSTT_NAME}" />
											</option>
										</c:forEach>
	                            	</select>
                            	</dd>
                           	</dl>
                       	</div>
						<div class="one-box">
							<dl>
								<dt>
									<label for="agrem-textfield05">협약번호</label>
	                            </dt>
	                            <dd>
	                            	<input type="number" id="agrem-textfield05" name="agremNo" value="" title="협약번호 입력" placeholder="협약번호" onKeyup="this.value=this.value.replace(/[^0-9]/g,'').substring(0,10);" />
                            	</dd>
                           	</dl>
                       	</div>
						<div class="one-box">
							<dl>
								<dt>
									<label for="agrem-textfield06">협약년도</label>
	                            </dt>
	                            <dd>
	                            	<input type="number" id="agrem-textfield06" min="1900" max="2200" name="agremYear" value="" title="협약년도 입력" placeholder="협약년도" onKeyup="this.value=this.value.replace(/[^0-9]/g,'').substring(0,4);" />
                            	</dd>
                           	</dl>
                       	</div>
						<div class="one-box">
							<dl>
								<dt>
									<label for="agrem-textfield07">협약체결일</label>
	                            </dt>
	                            <dd class="input-calendar-area">
	                            	<input type="text" id="agrem-textfield07" name="agremCnclsDe" class="sdate" value="" placeholder="협약체결일" autocomplete="off" />
                            	</dd>
                           	</dl>
                       	</div>
						<div class="one-box">
							<dl>
								<dt>
									<label for="agrem-textfield08">협약구분</label>
	                            </dt>
	                            <dd>
	                            	<input type="text" id="agrem-textfield08" name="agremSe" maxlength="20" class="" value="" title="협약구분 입력" placeholder="협약구분" />
                            	</dd>
                           	</dl>
                       	</div>
						<div class="one-box">
							<dl>
								<dt>
									<label for="agrem-textfield09">협약여부</label>
	                            </dt>
	                            <dd>
	                            	<select id="agrem-textfield09" name="agremYn">
	                            		<option value="Y" selected>Y</option>
	                            		<option value="N">N</option>
	                            	</select>
                            	</dd>
                           	</dl>
                       	</div>
						<div class="one-box">
							<dl>
								<dt>
									<label for="agrem-textfield10">회원아이디</label>
	                            </dt>
	                            <dd>
	                            	<input type="text" id="agrem-textfield10" name="mberId" maxlength="50" class="" value="" title="회원아이디 입력" placeholder="회원아이디" />
                            	</dd>
                           	</dl>
                       	</div>
                   </div>
				</div>	
				<div class="btns-area">
					<button type="button" class="btn-b02 round01 btn-color03 left" onclick="saveAgremCorp()">
						<span>저장</span>
						<img src="${contextPath}${imgPath}/icon/icon_arrow_right03.png" alt="추가 버튼 아이콘" class="arrow01" />
					</button>
				</div>
			</form>
		</div>

        <button type="button" class="btn-modal-close">모달 창 닫기</button>
  </div>
<!-- 협약기업 추가 모달창 끝 -->

<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>