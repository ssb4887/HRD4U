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
<script defer type="text/javascript" src="${contextPath}<c:out value="${jsPath}/ntwrk/crdns.js"/>"></script>

<div id="overlay"></div>
<div class="loader"></div>

	<!-- contents  -->
	<div class="contents-wrapper">
		<div class="contents-area">
			<form id="form-box" method="POST">
				<input type="hidden" id="idx" name="idx" />
				<input type="hidden" name="cmptinstIdx" value="<c:out value='${result.CMPTINST_IDX}' />" />
				<legend class="blind">유관기관 등록</legend>
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
								<input type="text" id="textfield01" name="cmptinstName" value="<c:out value='${result.CMPTINST_NAME}'/>" placeholder="기관명 입력" class="w100" data-label="기관명">
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
								<input type="text" id="textfield02" name="addrDtl" value="<c:out value='${result.ADDR_DTL}' />" placeholder="추가 입력" class="w100 mt10" data-label="상세주소">
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
								<input type="text" id="textfield03" name="cmptinstReperNm" value="<c:out value='${result.CMPTINST_REPER_NM}' />" placeholder="대표자명 입력" class="w100" data-label="대표자(기관장))">
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
								<input type="text" id="textfield04" name="cmptinstPicName" value="<c:out value='${result.CMPTINST_PIC_NAME}' />" placeholder="담당자명 입력" class="w100" data-label="담당자명">
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
						<a href="${contextPath}/dct/crdns/list.do?mId=70" class="btn-m01 btn-color01 depth2">
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
        
	</div>
	
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>