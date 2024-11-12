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
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/ntwrk/expert.js"/>"></script>

<div id="overlay"></div>
<div class="loader"></div>

	<!-- contents  -->
	<div class="contents-wrapper">
		<div class="contents-area">
			<form action="${contextPath}/dct/expert/insert.do?mId=107" method="POST" enctype="multipart/form-data" id="form-expert">
				<input type="hidden" name="expertIdx" value="<c:out value='${expert.EXPERT_IDX}' />" />
				<legend class="blind">글쓰기</legend>
				
				<div class="board-write">
					<div class="one-box">
						<div class="half-box">
							<dl>
								<dt>
									<label for="name">
										성명
										<strong class="point-important"> * </strong>
									</label>
								</dt>
								<dd>
									<input type="text" id="name" name="name" value="<c:out value='${expert.NAME}'/>" placeholder="전문가 성명 입력" class="w100" maxlength="100" />
								</dd>
							</dl>
						</div>
						
						<div class="half-box">
							<dl>
								<dt>
									<label for="brthdy">
										생년월일
									</label>
								</dt>
								<dd>
									<div class="input-calendar-area">
										<input type="text" class="w100" id="brthdy" name="brthdy" value="<c:out value='${expert.BRTHDY}'/>" placeholder="생년월일 입력" class="w100" maxlength="8" />
									</div>
								</dd>
							</dl>
						</div>
					</div>
					
					<div class="one-box">
						<dl>
							<dt>
								<label for="psitnGrpName">
									소속단체명
								</label>
							</dt>
							<dd>
								<input type="text" id="psitnGrpName" name="psitnGrpName" value="<c:out value='${expert.PSITN_GRP_NAME}'/>" placeholder="소속단체명 입력" maxlength="100">
							</dd>
						</dl>
					</div>
					
					<div class="one-box">
						<dl>
							<dt>
								<label for="psitnGrpType">
									소속단체 유형
								</label>
							</dt>
							<dd>
								<input type="text" id="psitnGrpType" name="psitnGrpType" value="<c:out value='${expert.PSITN_GRP_TYPE}'/>" placeholder="소속단체 유형 입력" maxlength="50">
							</dd>
						</dl>
					</div>
		
					<div class="one-box">
						<dl>
							<dt>
								<label for="addrDtl">
									소속 단체 주소
								</label>
							</dt>
							<dd>
								<div class="input-add-btns-wrapper">
									<div style="display:flex">
										<input type="text" id="zip" name="zip" disabled style="flex:3; margin-right:10px;" data-label="우편번호" placeholder="우편번호" maxlength="10" value="<c:out value='${expert.ZIP}'/>" />
										<input type="text" id="addr" name="addr" disabled style="flex:7" data-label="주소" placeholder="주소" maxlength="200" value="<c:out value='${expert.ADDR}'/>">
									</div>
									<button type="button" class="btn-color02" onclick="searchAddr()">
										주소검색
									</button>
								</div>
								<input type="text" id="addrDtl" name="addrDtl" placeholder="추가 입력" class="w100 mt10" data-label="상세주소" maxlength="200" value="<c:out value='${expert.ADDR_DTL}'/>">
							</dd>
						</dl>
					</div>
		
					<div class="one-box">
						<dl>
							<dt>
								<label for="spcltyRealm">
									전문 분야
									<strong class="point-important"> * </strong>
								</label>
							</dt>
							<dd>
								<input type="text" id="spcltyRealm" name="spcltyRealm" value="<c:out value='${expert.SPCLTY_REALM}'/>" placeholder="전문 분야 입력" class="w100" maxlength="50">
							</dd>
						</dl>
					</div>
		
		
					<div class="one-box">
						<div class="half-box">
							<dl>
								<dt>
									<label for="dept">
										부서(학과)
									</label>
								</dt>
								<dd>
									<input type="text" id="dept" name="dept" value="<c:out value='${expert.DEPT}'/>" placeholder="부서(학과) 입력" maxlength="50">
								</dd>
							</dl>
						</div>
						<div class="half-box">
							<dl>
								<dt>
									<label for="ofcps">
										직위
									</label>
								</dt>
								<dd>
									<input type="text" id="ofcps" name="ofcps" value="<c:out value='${expert.OFCPS}'/>" placeholder="직위 입력" class="w100" maxlength="100">
								</dd>
							</dl>
						</div>
					</div>
					
					<div class="one-box">
						<div class="half-box">
							<dl>
								<dt>
									<label for="mbtlnum">
										연락처
									</label>
								</dt>
								<dd>
									<input type="text" id="mbtlnum" name="mbtlnum" value="<c:out value='${expert.MBTLNUM}'/>" placeholder="연락처 입력" oninput="formatPhoneNumber(this);" maxlength="30">
								</dd>
							</dl>
						</div>
						<div class="half-box">
							<dl>
								<dt>
									<label for="email">
										이메일
									</label>
								</dt>
								<dd>
									<input type="text" id="email" name="email" value="<c:out value='${expert.EMAIL}'/>" placeholder="이메일 입력" class="w100" maxlength="50">
								</dd>
							</dl>
						</div>
					</div>
					
					<div class="one-box">
						<dl class="board-write-contents">
							<dt>
								<label for="insttIdx">
									관할 기관
								</label>
							</dt>
							<dd>
								<select id="insttIdx" name="insttIdx" class="w50">
									<option value="">관할기관 선택</option>
									<c:forEach items="${insttList}" var="instt">
										<option value="<c:out value='${instt.INSTT_IDX}' />" <c:if test="${instt.INSTT_IDX eq expert.INSTT_IDX}">selected</c:if>><c:out value='${instt.INSTT_NAME}'/></option>
									</c:forEach>
								</select>
							</dd>
						</dl>
					</div>
					
					<div class="one-box">
						<dl class="board-write-contents">
							<dt>
								<label for="partnerInsttIdxs">
									소속 기관
								</label>
							</dt>
							<dd>
								<div class="input-add-btns-wrapper02">
									<select id="partnerInsttIdxs" class="w50">
										<option value="">소속기관 선택</option>
										<c:forEach items="${insttList}" var="instt">
											<option value="<c:out value='${instt.INSTT_IDX}' />"><c:out value='${instt.INSTT_NAME}'/></option>
										</c:forEach>
									</select>
									<button type="button" class="btn-m01 btn-color02" onclick="addInstt()">추가</button>
								</div>
							</dd>
							<dd style="border-top:1px solid #e5e5e5;">
								<p class="word-type02">※ 해당 전문가를 조회할 수 있는 소속기관</p>
								<div class="btn-delete-wrapper mt10" id="partnerInstt-wrapper">
									<c:if test="${not empty partnerInsttList}">
										<c:forEach items="${partnerInsttList}" var="partnerInsttList">
											<p class="word">
												<strong>${partnerInsttList.INSTT_NAME}</strong>
												<button type="button" name="partnerInstt" class="btn-delete" data-key="${partnerInsttList.INSTT_IDX}" onclick="deletePartnerInstt();">삭제</button>
											</p>
										</c:forEach>
									</c:if>
								</div>
							</dd>
						</dl>
					</div>
					
					<div class="one-box">
						<dl class="board-write-contents">
							<dt>
								<label for="fileName">
									파일
								</label>
							</dt>
							<dd>
								<div class="fileBox">
									<input type="text" id="fileName" class="fileName" readonly="readonly" placeholder="파일찾기" />
									<button type="button" id="upload-file" class="btn_file" onclick="addFile()">찾아보기</button>
								</div>
								
								<p class="mt10">첨부 파일 목록(최대 5개)</p>
								<div class="add-file-list-wrapper">
									<div class="add-file-list-area">
										<ul id="add-file-area">
											<c:if test="${not empty fileList}">
												<c:forEach items="${fileList}" var="ntwrkFile" varStatus="status">
													<li>
														<input type="checkbox" id="add-file${status.index}" name="" class="checkbox-type01" value="${ntwrkFile.FLE_IDX}" />
														<label for="add-file${status.index}">
															${ntwrkFile.FILE_ORIGIN_NAME}
														</label>
														<input type="hidden" name="uploaded-files" value="${ntwrkFile.FLE_IDX}" />
														<input type="file" name="files" style="display:none;" data-idx="${ntwrkFile.FLE_IDX}" />
													</li>
												</c:forEach>
											</c:if>
<!-- 											<li> -->
<!-- 												<input type="checkbox" id="add-file0101" name="" value="" class="checkbox-type01" /> -->
<!-- 												<label for="add-file0101">파일명</label> -->
<!-- 											</li> -->
										</ul>
									</div>
									
									<div class="btns">
										<button type="button" onclick="deleteChecked()">삭제</button>
										<button type="button" onclick="moveUp()">위</button>
										<button type="button" onclick="moveDown()">아래</button>
									</div>
								</div>
							</dd>
						</dl>
					</div>
					
					<div class="one-box">
						<dl class="board-write-contents">
							<dt>
								<label for="remarks">
									비고
								</label>
							</dt>
							<dd>
								<textarea id="remarks" name="remarks" class="" placeholder="입력" maxlength="2000"><c:out value='${expert.REMARKS}' /></textarea>
							</dd>
						</dl>
					</div>
					
				</div>
		
				<div class="btns-area">
					<div class="btns-right">
						<button type="button" class="btn-m01 btn-color03 depth3" onclick="saveExpert()">저장</button>
						<a href="${contextPath}/dct/expert/list.do?mId=107" class="btn-m01 btn-color01 depth3">목록</a>
					</div>
				</div>
		
			</form>
		</div>
	</div>

<!-- //contents -->
<form id="form-box" style="display:none;">
	<input type="hidden" id="idx" name="idx" />
</form>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>