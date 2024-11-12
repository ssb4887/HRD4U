<%@ include file="../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>							
<c:set var="searchFormId" value="fn_calendarSearchForm"/>							
<c:set var="listFormId" value="fn_calendarListForm"/>						
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/>
<c:set var="curPage" value="${empty param.page ? 1 : param.page le 0 ? 1 : param.page }" />				
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>	
		<jsp:param name="searchFormId" value="${searchFormId}"/>				
		<jsp:param name="listFormId" value="${listFormId}"/>					
	</jsp:include>
</c:if>
<div class="contents-wrapper">
<!-- CMS 시작 -->
	<c:choose>
	<c:when test="${loginVO.getUsertypeIdx() == 5 }">
		<jsp:include page="./header/corpo.jsp">
			<jsp:param name="cPath" value="support"/>
		</jsp:include>
	</c:when>
	<c:when test="${loginVO.getUsertypeIdx() == 30 || loginVO.getUsertypeIdx() == 31 }">
		<jsp:include page="./header/instt.jsp">
			<jsp:param name="cPath" value="support"/>
		</jsp:include>
		<div class="contents-area02">
			<form id="main" action="${contextPath }/${siteId}/taskhub/inquiry.do?mId=48" method="GET">
				<legend class="blind">비용 신청 검색양식</legend>
				<input type="hidden" name="mId" value="48" />
				<input type="hidden" name="page" id="page" value="1" />
				<div class="basic-search-wrapper">
					<div class="one-box">
						<div class="half-box">
							<dl>
								<dt>
									<label for="bpl_nm">사업장명</label>
								</dt>
								<dd>
									<input type="text" id="bpl_nm" name="bplNm" value="${param.bplNm }" title="사업장명 입력" placeholder="사업장명">
								</dd>
							</dl>
						</div>
						<div class="half-box">
							<dl>
								<dt>
									<label for="bpl_no">고용보험 관리번호</label>
								</dt>
								<dd>
									<input type="text" id="bpl_no" name="bplNo" value="${param.bplNo }" title="고용보험 관리번호 입력" placeholder="고용보험 관리번호" >
								</dd>
							</dl>
						</div>
					</div>
					<div class="one-box">
						<div class="half-box">
							<dl>
								<dt>
									<label for="instt">요청 업무</label>
								</dt>
								<dd>
									<input type="text" id="id_cn" name="cn" value="${param.cn }" title="요청 업무 입력" placeholder="요청 업무">
								</dd>
							</dl>
						</div>
						<div class="half-box">
							<dl>
								<dt>
									<label for="center">상태</label>
								</dt>
								<dd>
									<select id="is_confmStatus" name="status" title="상태 입력">
										<option value="10" <c:if test="${param.status eq 10}">selected="selected"</c:if>>요청</option>
										<option value="30" <c:if test="${param.status eq 30}">selected="selected"</c:if>>접수</option>
										<option value="55" <c:if test="${param.status eq 55}">selected="selected"</c:if>>최종승인</option>
									</select>
								</dd>
							</dl>
						</div>
					</div>
				</div>
				<div class="btns-area">
					<button type="submit" class="btn-search02">
						<img src="../../assets/img/icon/icon_search03.png" alt="">
						<strong>검색</strong>
					</button>
				</div>
			</form>
		</div>
	</c:when>
	<c:when test="${loginVO.getUsertypeIdx() >= 40 }">
		<jsp:include page="./header/headquarter.jsp">
			<jsp:param name="cPath" value="support"/>
		</jsp:include>
		<div class="contents-area02">
			<form id="main" action="${contextPath }/${siteId}/taskhub/reqlist.do?mId=48" method="GET">
				<legend class="blind">비용 신청 검색양식</legend>
				<input type="hidden" name="mId" value="48" />
				<input type="hidden" name="page" id="page" value="1" />
				<div class="basic-search-wrapper">
					<div class="one-box">
						<div class="half-box">
							<dl>
								<dt>
									<label for="bpl_nm">사업장명</label>
								</dt>
								<dd>
									<input type="text" id="bpl_nm" name="bplNm" value="${param.bplNm }" title="사업장명 입력" placeholder="사업장명">
								</dd>
							</dl>
						</div>
						<div class="half-box">
							<dl>
								<dt>
									<label for="bpl_no">고용보험 관리번호</label>
								</dt>
								<dd>
									<input type="text" id="bpl_no" name="bplNo" value="${param.bplNo }" title="고용보험 관리번호 입력" placeholder="고용보험 관리번호" >
								</dd>
							</dl>
						</div>
					</div>
					<div class="one-box">
						<div class="half-box">
							<dl>
								<dt>
									<label for="instt">요청 업무</label>
								</dt>
								<dd>
									<input type="text" id="id_cn" name="cn" value="${param.cn }" title="요청 업무 입력" placeholder="요청 업무">
								</dd>
							</dl>
						</div>
						<div class="half-box">
							<dl>
								<dt>
									<label for="center">상태</label>
								</dt>
								<dd>
									<select id="is_confmStatus" name="status" title="상태 입력">
										<option value="10" <c:if test="${param.status eq 10}">selected="selected"</c:if>>요청</option>
										<option value="30" <c:if test="${param.status eq 30}">selected="selected"</c:if>>접수</option>
										<option value="55" <c:if test="${param.status eq 55}">selected="selected"</c:if>>최종승인</option>
									</select>
								</dd>
							</dl>
						</div>
					</div>
				</div>
				<div class="btns-area">
					<button type="submit" class="btn-search02">
						<img src="../../assets/img/icon/icon_search03.png" alt="">
						<strong>검색</strong>
					</button>
				</div>
			</form>
		</div>
	</c:when>
	<c:otherwise>
	</c:otherwise>
	</c:choose>
	<div class="contents-area">
		<div class="title-wrapper">
			<p class="total fl">총 <strong>${list[0].TOTAL_COUNT}</strong> 건 ( ${curPage } / ${list[0].TOTAL_PAGE } 페이지 )</p>
			<c:if test="${loginVO.getUsertypeIdx() eq 5 }">
			<div class="fr btns-area">
				<div class="btns-right">
					<a href="#none" class="btn-m01 btn-color01 " id="open-modal01">지원 신청</a>
				</div>
			</div>
			</c:if>
			<c:if test="${loginVO.getUsertypeIdx() eq 40 }">
			<div class="fr">
		    	<a href="${contextPath}/${siteId}/taskhub/reqlistExcel.do?mId=${param.mId}" class="btn-m01 btn-color01">엑셀다운로드</a>
		    </div>
		    </c:if>
		</div>
		<div class="table-type01 horizontal-scroll">
			<table>
				<caption>지원 요청표 : 번호, 기업명, 고용보험 관리번호, 상태, 신청일, 주치의, 요청 업무, 지원 요청에 관한 정보 제공표</caption>
				<colgroup>
					<col style="width: 8%">
					<col style="width: auto">
					<col style="width: auto">
					<col style="width: auto">
					<col style="width: auto">
					<col style="width: auto">
					<col style="width: auto">
					<col style="width: auto">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">번호</th>
						<th scope="col">기업명</th>
						<th scope="col">고용보험 관리번호</th>
						<th scope="col">상태</th>
						<th scope="col">신청일</th>
						<th scope="col">주치의</th>
						<th scope="col">요청 업무</th>
						<th scope="col">지원 요청</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list }" var="item">
					<tr>
						<td>${item.REQ_IDX }</td>
						<td>${item.BPL_NM }</td>
						<td>${item.BPL_NO }</td>
						<td>${confirmProgress.get(item.STATUS) }</td>
						<td>${item.REQ_DATE }</td>
						<td>${empty item.DOCTOR_NAME ? '-' : item.DOCTOR_NAME 	}</td>
						<td>${item.JOB }</td>
						<td>
							<c:choose>
								<c:when test="${(loginVO.getUsertypeIdx() eq 5) and (item.STATUS eq 30) }">
									<button type="button" class="btn-m02 btn-color01" id="finish" data-idx="${item.REQ_IDX }">
										<span>요청 완료</span>
									</button>
								</c:when>
								<c:when test="${((loginVO.getUsertypeIdx() ge 30) and (item.STATUS eq 10)) }">
									<button type="button" class="btn-m02 btn-color07 " id="accept" data-idx="${item.REQ_IDX }">
										<span>접수</span>
									</button>
								</c:when>
								<c:when test="${((loginVO.getUsertypeIdx() ge 30) and (item.STATUS eq 30) and (item.OWN eq 'Y')) }">
									<button type="button" class="btn-m02 btn-color03 " id="support" data-idx="${item.REQ_IDX }">
										<span>지원</span>
									</button>
								</c:when>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="paging-navigation-wrapper">
			<!-- 페이징 네비게이션 -->
			<p class="paging-navigation">
				<a href="${contextPath }/${siteId}/taskhub/reqlist.do?mId=48&page=1" class="btn-first">맨 처음 페이지로 이동</a>
				<a href="${contextPath }/${siteId}/taskhub/reqlist.do?mId=48&page=${curPage - 1 eq 0 ? 1 : curPage-1}" class="btn-preview">이전 페이지로 이동</a>
				<c:forEach var="i" begin="${1 }" end="${list[0].TOTAL_PAGE}">
					<c:choose>
						<c:when test="${i eq curPage }">
							<strong>${i }</strong>
						</c:when>
						<c:otherwise>
							<a href="${contextPath }/${siteId}/taskhub/reqlist.do?mId=48&page=${i}">${i }</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<a href="${contextPath }/${siteId}/taskhub/reqlist.do?mId=48&page=${curPage + 1 eq list[0].TOTAL_PAGE+1 ? list[0].TOTAL_PAGE : curPage+1}" class="btn-next">다음 페이지로 이동</a>
				<a href="${contextPath }/${siteId}/taskhub/reqlist.do?mId=48&page=${list[0].TOTAL_PAGE}" class="btn-last">맨 마지막 페이지로 이동</a>
			</p>
			<!-- //페이징 네비게이션 -->
		</div>
	<!-- //CMS 끝 -->
</div>
<!-- //contents -->
<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="modal-action01">
	<h2>지원요청</h2>
	<div class="modal-area">
		<form action="">
			<div class="modal-alert">
				<p>능력개발전담주치의에게 지원 요청을 하시겠습니까?</p>
			</div>
			<div class="basic-search-wrapper">
				<div class="one-box mt0">
					<dl class="pl0">
						<dt style="display:none">
							<label for="modal-textfield01"></label>
						</dt>
						<dd>
							<select id="modal-textfield01" name="item" class="w100">
								<option value="HRD 기초 컨설팅">HRD 기초 컨설팅</option>
								<option value="심화 컨설팅">심화 컨설팅</option>
								<option value="맞춤개발 컨설팅">맞춤개발 컨설팅</option>
								<option value="능력개발 클리닉">능력개발 클리닉</option>
							</select>
						</dd>
					</dl>
				</div>
				<div class="one-box">
					<dl class="pl0">
						<dt style="display:none">
							<label for="modal-textfield02"></label>
						</dt>
						<dd>
							<textarea id="modal-textfield02" name="content" placeholder="문의 사항"></textarea>
						</dd>
						</dl>
				</div>
			</div>
			<div class="btns-area">
				<button type="button" class="btn-m02 round01 btn-color03" id="submit">
					<span>확인</span>
				</button>
	 			<button type="button" class="btn-m02 round01 btn-color02" id="cancel">
	 				<span>취소</span>
				</button>
			</div>
		</form>
	</div>
 	<button type="button" class="btn-modal-close">모달 창 닫기</button>
</div>
<!-- //모달 창 -->
<script>
$(function() {
	$("#open-modal01").on("click", function() {
		$(".mask").fadeIn(150, function() {
			$("#modal-action01").show();
 		});
 	});
	$("#modal-action01 .btn-modal-close").on("click", function() {
		$("#modal-action01").hide();
		$(".mask").fadeOut(150);
 	});
	$('span#item').text($("select#modal-textfield01 option:selected").val());
	$("select#modal-textfield01").on("change", function() {
		console.log($(this).val());
		$('span#item').text($(this).val());
	})
	$('button#cancel').on("click", function() {
		$("#modal-action01").hide();
		$(".mask").fadeOut(150);
	})
	$('button#submit').on('click', function() {
		const contextPath = '${contextPath}'
		const text = '{{'+$('select#modal-textfield01 option:selected').val() + '}}' + $('textarea#modal-textfield02').val() 
		$.ajax({
			url: `\${contextPath}/web/taskhub/req.do?mId=48`,
			type: 'POST',
			contentType: 'application/x-www-form-urlencoded',
			data: {cn: text},
			success: function(response) {
				alert('지원요청 등록 완료')
			},
			error: function(xhr, status, error) {
				console.error('Error occurred:', error);
			}
		});
		$("#modal-action01").hide();
		$(".mask").fadeOut(150);
		cleanup();
	})
 });
 
 function cleanup() {
	 $('textarea#modal-textfield02').val('');
 } 
</script>
 <c:if test="${loginVO.getUsertypeIdx() ge 30}">
<script type="text/javascript">
	console.log('c:if tag executed inside the script tag')
	$(function() {
		$('button#accept').on('click', function() {
			const contextPath = "<%=request.getContextPath()%>";
			$.ajax({
				url: `\${contextPath}/dct/taskhub/support.do?mId=48`,
				type: 'POST',
				contentType: 'application/x-www-form-urlencoded',
				data: {req_idx: $(this).data("idx")},
				success: function(response) {
					let data_ = JSON.parse(response);
					if(data_.status == "success") {
						alert(data_.message)
						location.reload()
					} else {
						alert(data_.message)
					}
					
				},
				error: function(xhr, status, error) {
					console.error('Error occurred:', error);
				}
			});
		});
		$('button#support').on('click', function() {
			const contextPath = "<%=request.getContextPath()%>";
			$.ajax({
				url: `\${contextPath}/dct/taskhub/layer.do?mId=48`,
				type: 'POST',
				contentType: 'application/x-www-form-urlencoded',
				data: {req_idx: $(this).data("idx")},
				success: function(response) {
					console.log(response)
					let data = JSON.parse(response);
					if(data.status == "success") {
						window.location.href = `\${data.message}`;
					}
				},
				error: function(xhr, status, error) {
					console.error('Error occurred:', error);
				}
			})
		});
	});
</script>
 </c:if>
 <c:if test="${loginVO.getUsertypeIdx() eq 5}">
 <script>
	$('button#finish').on('click', function() {
		const contextPath = "<%=request.getContextPath()%>";
		$.ajax({
			url: `\${contextPath}/web/taskhub/finish.do?mId=48`,
			type: 'POST',
			contentType: 'application/x-www-form-urlencoded',
			data: {req_idx: $(this).data("idx")},
			success: function(response) {
				let data = JSON.parse(response);
				if(data.status=="success") {
					alert("요청 완료 했습니다.");
				}
				location.reload();
			},
			error: function(xhr, status, error) {
				console.error('Error occurred:', error);
			}
		})
	});
 </script>
 </c:if>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
