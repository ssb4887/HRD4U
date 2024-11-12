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
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/ntwrk/network.js"/>"></script>

<div id="overlay"></div>
<div class="loader"></div>

	<!-- contents  -->
	<div class="contents-wrapper">
		<div class="contents-area">
			<form action="${contextPath}/dct/ntwrk/insert.do?mId=71" method="POST" enctype="multipart/form-data" id="form-network">
				<input type="hidden" name="ntwrkIdx" value="<c:out value='${ntwrk.NTWRK_IDX}'/>" />
				<legend class="blind">네트워크 이력 등록</legend>

				<div class="board-write mb10">
					<div class="one-box">
						<dl>
							<dt>
								<label for="insttIdx">주관기관</label>
								<strong class="point-important"> * </strong>
							</dt>

							<dd>
								<div class="input-depth2-wrapper">
									<select id="insttIdx" name="insttIdx">
										<option value="">주관기관 선택</option>
										<c:forEach items="${insttList}" var="instt">
											<option value="<c:out value='${instt.INSTT_IDX}' />" <c:if test="${instt.INSTT_IDX eq ntwrk.INSTT_IDX}">selected</c:if>><c:out value='${instt.INSTT_NAME}'/></option>
										</c:forEach>
									</select> 
<!-- 									<select id="textfield01" name="" class="w50"> -->
<!-- 										<option value="">정보화지원국</option> -->
<!-- 									</select> -->
								</div>
							</dd>
						</dl>
					</div>
					<div class="one-box">
						<dl>
							<dt>
								<label for="textfield02">체결권자</label>
							</dt>
							<dd>
								<input type="text" id="textfield02" name="clprnName" value="<c:out value='${ntwrk.CLPRN_NAME}' />" placeholder="체결권자 입력" class="w100" maxlength="100">
							</dd>
						</dl>
					</div>
					<div class="one-box">
						<dl>
							<dt>
								<label for="cmptinstName"> 기관명 <strong
									class="point-important"> * </strong>
								</label>
							</dt>

							<dd>
								<input type="text" id="cmptinstName" name="cmptinstName" value="<c:out value='${ntwrk.CMPTINST_NAME}' />" placeholder="기관명(통칭) 입력" class="w100" maxlength="100">
							</dd>
							
							<dd style="border-top:1px solid #e5e5e5;">
								<div class="input-add-btns-wrapper03">
									<div>
										<button type="button" class="btn-m01 btn-color02 open-modal01 pl" id="btn-modal">추가하기</button>
										<span class="pl10">※ 네트워크에 참가하는 기관 추가 (협약체/유관기관)</span>
									</div>
								</div>
								
								<div class="btn-delete-wrapper mt10" id="btn-wrapper">
									<c:if test="${not empty cmptinstList}">
										<c:forEach items="${cmptinstList}" var="cmptinst">
											<p class="word">
												<strong>${cmptinst.CMPTINST_NAME}</strong>
												<button type="button" class="btn-delete" data-key="${cmptinst.CMPTINST_IDX}">삭제</button>
											</p>
										</c:forEach>
									</c:if>
								</div>
							</dd>
						</dl>
					</div>

					<div class="one-box">
						<dl>
							<dt>
								<label for="agremName">
									협약명
									<strong class="point-important"> * </strong>
								</label>
							</dt>
							<dd>
								<input type="text" id="agremName" name="agremName" value="<c:out value='${ntwrk.AGREM_NAME}' />" placeholder="협약명 입력" class="w100" maxlength="100">
							</dd>
						</dl>
					</div>

					<div class="one-box">
						<dl>
							<dt>
								<label for="textfield05"> 관련 사업 </label>
							</dt>
							<dd>
								<input type="text" id="textfield05" name="relateBiz" value="<c:out value='${ntwrk.RELATE_BIZ}' />" placeholder="관련 사업 입력" class="w100" maxlength="500">
							</dd>
						</dl>
					</div>
					<div class="one-box">
						<dl class="board-write-contents">
							<dt>
								<label for="textfield06">협약 내용</label>
							</dt>
							<dd>
								<textarea id="textfield06" name="agremCn" class="" placeholder="입력" maxlength="2000"><c:out value='${ntwrk.AGREM_CN}'/></textarea>
							</dd>
						</dl>
					</div>
					<div class="one-box">
						<dl class="board-write-contents">
							<dt>
								<label for="textfield07">체결 후 활동 실적</label>
							</dt>
							<dd>
								<textarea id="textfield07" name="rsltCn" class="" placeholder="입력" maxlength="2000"><c:out value='${ntwrk.RSLT_CN}' /></textarea>
							</dd>
						</dl>
					</div>

					<div class="one-box">
						<dl>
							<dt>
								<label for="textfield08">기간</label>
							</dt>
							<dd>
								<div class="input-calendar-wrapper">
									<div class="input-calendar-area">
										<input type="text" id="bgndt" name="bgndt" class="sdate" title="시작일 입력" placeholder="시작일" value="<c:out value='${ntwrk.BGNDT}'/>" autocomplete="off" />
									</div>
									<div class="word-unit">~</div>
									<div class="input-calendar-area">
										<input type="text" id="enddt" name="enddt" class="edate" title="종료일 입력" placeholder="종료일" value="<c:out value='${ntwrk.ENDDT}'/>" autocomplete="off" />
									</div>
								</div>
							</dd>
						</dl>
					</div>

					<div class="one-box">
						<dl class="board-write-contents">
							<dt>
								<label for="uploadBtn"> 관련 문서 </label>
							</dt>
							
							<c:if test="${not empty fileList}">
								<dd style="border-bottom:1px solid #e5e5e5;">
								<c:forEach items="${fileList}" var="ntwrkFile" varStatus="status">
									<p class="attached-file">
										<a href="${contextPath}/dct/ntwrk/download.do?mId=71&fleIdx=${ntwrkFile.FLE_IDX}" class="fn_filedown">${ntwrkFile.FILE_ORIGIN_NAME}</a>
									</p>
								</c:forEach>
								</dd>
							</c:if>
							
							<dd>
								<div class="fileBox">
									<input type="text" id="fileName" class="fileName" readonly="readonly" placeholder="파일찾기" />
<!-- 									<label for="upload-file" class="btn_file">찾아보기</label> -->
<!-- 									<input type="file" id="upload-file" name="upload-file" class="uploadBtn" required onchange="addFile()" multiple> -->
									<button type="button" id="upload-file" class="btn_file" onclick="addFile()">찾아보기</button>
								</div>
								
								<p style="margin-top:10px">첨부 파일 목록(최대 5개)</p>
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
						<dl>
							<dt>
								<label for="textfield09"> 비고 </label>
							</dt>
							<dd>
								<input type="text" id="textfield09" name="remarks" value="" placeholder="비고 입력" class="w100" maxlength="2000">
							</dd>
						</dl>
					</div>

					<div class="one-box">
						<dl>
							<dt>
								<label for="textfield10"> 유효 여부 </label>
							</dt>
							<dd>
								<select id="textfield10" name="useYn" class="w50">
									<option value="Y">유효함</option>
									<option value="N">유효하지않음</option>
								</select>
							</dd>
						</dl>
					</div>
					<div class="one-box">
						<dl>
							<dt>
								<label for="textfield11"> 사유 </label>
							</dt>
							<dd>
								<input type="text" id="textfield11" name="reason" value="" placeholder="사유 입력" class="w100" maxlength="2000">
								<p class="word-type02 point-color04 mt10">※ ‘유효하지않음’의 경우에만 사유 입력</p>
							</dd>
						</dl>
					</div>

				</div>


				<div class="btns-area mt60">
					<div class="btns-right">
						<c:choose>
							<c:when test="${empty ntwrk}">
								<button type="button" class="btn-m01 btn-color03 depth2" onclick="saveNtwrk()">등록</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn-m01 btn-color03 depth2" onclick="saveNtwrk()">수정</button>
							</c:otherwise>
						</c:choose>
						<a href="${contextPath}/dct/ntwrk/list.do?mId=71" class="btn-m01 btn-color01 depth2">목록</a>
					</div>
				</div>
			</form>
		</div>
		
	</div>

<div class="mask"></div>
<div class="modal-wrapper" id="modal-search">
	<h2>네트워크 기관 검색</h2>
	<div class="modal-area">
		<form action="">
			<div class="contents-box pl0">
				<div class="basic-search-wrapper">
					<div class="one-box mt0">
						<dl>
							<dt>
								<label for="modal-searchName"> 기관명 </label>
							</dt>
							<dd>
								<input type="text" id="modal-searchName" name="searchName" value="" title="기관명 입력" placeholder="기관명">
							</dd>
						</dl>
					</div>
					<div class="one-box">
						<dl>
							<dt>
								<label for="modal-searchInstt"> 소속기관 </label>
							</dt>
							<dd>
								<select id="modal-searchInstt" name="searchInstt" class="w100">
									<option value="">전체</option>
									<c:forEach items="${insttList}" var="instt">
										<option value="<c:out value='${instt.INSTT_IDX}' />"><c:out value='${instt.INSTT_NAME}'/></option>
									</c:forEach>
								</select>
							</dd>
						</dl>
					</div>
				</div>
				<div class="btns-area">
					<button type="button" class="btn-m02 btn-color02 round01" id="btn-search">검색</button>
				</div>
			</div>

			<div class="contents-box pl0">
				<p class="total mb05">
					총 <strong id="searchCount">0</strong> 건 ( <span id="currentPage">1</span> / <span id="totalPage">1</span> 페이지 )
				</p>

				<div class="table-type01 horizontal-scroll">
					<table class="width-type03">
						<caption>네트워크 기관 검색표 : 번호, 기관명, 소속기관, 선택에 관한 정보 제공표</caption>
						<colgroup>
							<col style="width: 15%">
							<col style="width: 40%">
							<col style="width: 30%">
							<col style="width: 15%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">번호</th>
								<th scope="col">기관명</th>
								<th scope="col">소속기관</th>
								<th scope="col">
									<input type="checkbox" class="checkbox-type01" id="cmptinst-all" name="checkbox-cmptinst" />
								</th>
							</tr>
						</thead>
						<tbody id="modal-tbody">
							<tr>
								<td colspan="4">추가할 기관을 검색해주세요.</td>
							</tr>
<!-- 							<tr> -->
<!-- 								<td>4</td> -->
<!-- 								<td>부산항만공사</td> -->
<!-- 								<td>부산본부</td> -->
<!-- 								<td> -->
<!-- 									<input type="checkbox" id="" name="" value="" class="checkbox-type01"> -->
<!-- 								</td> -->
<!-- 							</tr> -->
						</tbody>
					</table>

				</div>

				<!-- 페이징 네비게이션 -->
				<p class="paging-navigation mt20" id="modal-paging-navigation">
<!-- 					<a href="#none" class="btn-first">맨 처음 페이지로 이동</a> -->
<!-- 					<a href="#none" class="btn-preview">이전 페이지로 이동</a> -->
<!-- 					<strong>1</strong> -->
<!-- 					<a href="#">2</a> -->
<!-- 					<a href="#">3</a> -->
<!-- 					<a href="#">4</a> -->
<!-- 					<a href="#">5</a> -->
<!-- 					<a href="#none" class="btn-next">다음 페이지로 이동</a> -->
<!-- 					<a href="#none" class="btn-last">맨 마지막 페이지로 이동</a> -->
				</p>
				<!-- //페이징 네비게이션 -->
			</div>


			<div class="contents-box pl0">
				<div class="basic-search-wrapper">
					<div class="one-box mt0">
						<dl>
							<dt>
								<label for="selectedCmptinst"> 선택 </label>
							</dt>
							<dd>
								<span id="selectedCmptinst"></span>
							</dd>
						</dl>
					</div>

				</div>
			</div>

			<div class="btns-area">
				<button type="button" class="btn-m02 round01 btn-color03" id="btn-add">
					<span> 추가 </span>
				</button>

				<button type="button" class="btn-m02 round01 btn-color02" id="btn-cancel">
					<span> 취소 </span>
				</button>
			</div>
		</form>
	</div>

	<button type="button" class="btn-modal-close">모달 창 닫기</button>
</div>

<form id="form-box" style="display:none;">
	<input type="hidden" id="idx" name="idx" />
</form>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>