<%@ include file="../../../../../include/commonTop.jsp"%>
<%@ taglib prefix="itui" tagdir="/WEB-INF/tags/item" %>
<%@ taglib prefix="pgui" tagdir="/WEB-INF/tags/pagination" %>
<c:set var="mngAuth" value="${elfn:isAuth('MNG')}"/>							
<c:set var="searchFormId" value="fn_calendarSearchForm"/>							
<c:set var="listFormId" value="fn_calendarListForm"/>						
<c:set var="btnModifyClass" value="fn_btn_modify${inputWinFlag}"/>
<c:set var="curPage" value="${empty param.page ? 1 : param.page }" />	
<c:set var="start" value="${(((curPage-1)/5.0)-(((curPage-1)/5.0)%1))*5+1 }" />
<c:set var="end" value="${(((curPage-1)/5.0)-(((curPage-1)/5.0)%1))*5+5 }" />
<c:if test="${!empty TOP_PAGE}">
	<jsp:include page="${TOP_PAGE}" flush="false">
		<jsp:param name="javascript_page" value="${moduleJspRPath}/list.jsp"/>	
		<jsp:param name="searchFormId" value="${searchFormId}"/>				
		<jsp:param name="listFormId" value="${listFormId}"/>					
	</jsp:include>
</c:if>
<div class="contents-wrapper">
<!-- CMS 시작 -->
	<jsp:include page="../header/${type_name }.jsp" flush="false">
		<jsp:param name="usertype_idx" value="${usertype_idx }" />
		<jsp:param name="cPath" value="corporation" />
	</jsp:include>
	<div class="tabmenu-wrapper01 depth6">
		<ul>
			<li style="width: 33.333%">
				<a href="${contextPath}/dct/taskhub/corp/corps.do?mId=48" class="active">기업 </a>
			</li>
			<li style="width: 33.333%">
				<a href="${contextPath}/dct/taskhub/corp/centers.do?mId=48">민간 센터</a>
			</li>
			<li style="width: 33.333%">
				<a href="${contextPath}/dct/taskhub/corp/agreement.do?mId=48">협약</a>
			</li>
		</ul>
	</div>
	<div class="contents-area02">
		<form action="${contextPath }/${siteId}/taskhub/corp/corps.do?mId=48" method="GET" id="main">
			<legend class="blind">기업 검색양식</legend>
			<input type="hidden" name="mId" value="48" />
			<input type="hidden" name="page" id="page" value="1" />
			<div class="basic-search-wrapper">
				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label for="textfield01">사업장명</label>
							</dt>
							<dd>
								<input type="text" id="textfield01" name="corpName" value="" title="사업장명 입력" placeholder="사업장명">
							</dd>
						</dl>
					</div>
					<div class="half-box">
						<dl>
							<dt>
								<label>회원가입</label>
							</dt>
							<dd>
								<div class="input-radio-wrapper ratio">
									<div class="input-radio-area">
										<input type="radio" class="radio-type01" id="radio0101" name="member_yn" value="Y">
										<label for="radio0101">가입</label>
									</div>
									<div class="input-radio-area">
										<input type="radio" class="radio-type01" id="radio0102" name="member_yn" value="N">
										<label for="radio0102">미가입</label>
									</div>
								</div>
							</dd>
						</dl>
					</div>
				</div>
				<div class="one-box">
					<div class="half-box">
						<dl>
							<dt>
								<label for="textfield02">고용보험 관리번호</label>
							</dt>
							<dd>
								<input type="text" id="bpl_no" name="bplNo" value="" title="고용보험 관리번호 입력" placeholder="고용보험 관리번호">
							</dd>
						</dl>
					</div>
					<div class="half-box">
						<dl>
							<dt>
								<label for="textfield03">경과일</label>
							</dt>
							<dd>
								<input type="text" id="passed" name="passed" value="" title="경과일 입력" placeholder="경과일" class="w-plus50 mr10">
								<p class="word-type02">일 이상</p>
							</dd>
						</dl>
					</div>
				</div>
				
				<div class="one-box">
					<c:if test="${type_name } eq 'headquarter'">
					<div class="half-box">
						<dl>
							<dt>
								<label for="textfield04">지부지사</label>
							</dt>
							<dd>
								<input type="text" id="instt_name" name="insttName" value="" title="지부지사 입력" placeholder="지부지사">
							</dd>
						</dl>
					</div>
					</c:if>
					<div class="half-box">
						<dl>
							<dt>
								<label for="textfield05">해시태그</label>
							</dt>
							<dd>
								<input type="text" id="hashtag" name="hashtag" value="" title="해시태그 입력" placeholder="해시태그">
							</dd>
						</dl>
					</div>
				</div>
			</div>
			<div class="btns-area">
				<button type="submit" class="btn-search02">
					<img src="../../../assets/img/icon/icon_search03.png" alt="">
					<strong>검색</strong>
				</button>
			</div>
		</form>
	</div>
	<div class="contents-area">
		<div class="title-wrapper">
			<p class="total fl">
				총 <strong>${list[0].TOTAL_COUNT}</strong> 건 ( 1 / ${list[0].TOTAL_PAGE } 페이지 )
			</p>
			<div class="fr">
		    	<a href="${contextPath}/dct/taskhub/corp/corpsExcel.do?mId=${param.mId}
		    	<c:if test="${not empty param.corpName}">&corpName=${param.corpName}</c:if>
		    	<c:if test="${not empty param.member_yn}">&member_yn=${param.member_yn}</c:if>
		    	<c:if test="${not empty param.bplNo}">&bplNo=${param.bplNo}</c:if>
		    	<c:if test="${not empty param.passed}">&passed=${param.passed}</c:if>
		    	<c:if test="${not empty param.hashtag}">&hashtag=${param.hashtag}</c:if>" class="btn-m01 btn-color01">엑셀다운로드</a>
		    </div>
		</div>
		<div class="table-type01 horizontal-scroll">
			<table>
				<caption>관할 기업표 : 연번, 고용보험 관리번호, 회원가입, 사업장명, 소속기관, 기업HRD이음컨설팅, 성과 컨설팅, 심화 컨설팅, 훈련맞춤 개발 컨설팅, S-OJT, 능력개발클리닉, #태그에 관한 정보 제공표</caption>
				<colgroup>
					<col style="width: 5%">
					<col style="width: 9%">
					<col style="width: 5%">
					<col style="width: 8%">
					<col style="width: 9%">
					<col style="width: 9%">
					<col style="width: 8%">
					<col style="width: 8%">
					<col style="width: 8%">
					<col style="width: 11%">
					<col style="width: 9%">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">연번</th>
						<th scope="col">고용보험 관리번호</th>
						<th scope="col">회원가입</th>
						<th scope="col">사업장명</th>
						<th scope="col">소속기관</th>
						<th scope="col">
							<div>기업HRD이음컨설팅</div>
							<a href="#" class="word-linked-notice02" id="open-modal01"></a>
						</th>
						<th scope="col">
							심화 컨설팅
							<a href="#" class="word-linked-notice02" id="open-modal01"></a>
						</th>
						<th scope="col">
							훈련 맞춤 개발 컨설팅
							<a href="#" class="word-linked-notice02" id="open-modal01"></a>
						</th>
						<th scope="col">
							S-OJT
							<a href="#" class="word-linked-notice02" id="open-modal01"></a>
						</th>
						<th scope="col">
							능력개발클리닉
							<a href="#" class="word-linked-notice02" id="open-modal01"></a>
						</th>
						<th scope="col">#태그</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list }" var="item">
					<tr>
						<td>${item.TOTAL_COUNT-item.RN+1 }</td>
						<td>${item.BPL_NO }</td>
						<td>${item.MEMBER_YN }</td>
						<td>${item.BPL_NM }</td>
						<td>${item.INSTT_NAME }
						<td>
							<c:choose>
								<c:when test="${item.BSIS_STAGE == '기초진단'}">
									<a class="btn-list" data-bsc="${item.BSC_IDX }" data-dest="bsc" href="${contextPath }/${siteId}/diagnosis/list.do?mId=36" >${item.BSIS_STAGE }</a>
								</c:when>
								<c:when test="${item.BSIS_STAGE == '설문조사'  }">
									<a class="btn-list" data-bsc="${item.BSC_IDX }" data-rslt="${item.RSLT_IDX }" data-dest="rslt" href="${contextPath }/${siteId}/bsisCnsl/list.do?mId=37">${item.BSIS_STAGE }</a>
								</c:when>
								<c:when test="${item.BSIS_STAGE == '기초컨설팅'  }">
									<a class="btn-list" data-bsc="${item.BSC_IDX }" data-rslt="${item.RSLT_IDX }" data-dest="bsis" href="${contextPath }/${siteId}/bsisCnsl/list.do?mId=37">${item.BSIS_STAGE }</a>
								</c:when>
								<c:otherwise>
								-
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${item.BSIS_DELAY >= 5 and item.BSIS_DELAY < 7}">
									<span class="fire" />
								</c:when>
								<c:when test="${item.BSIS_DELAY >= 7 and item.BSIS_DELAY < 10}">
									<span class="fire" /><span class="fire" />
								</c:when>
								<c:when test="${item.BSIS_DELAY >= 10}">
									<span class="fire" /><span class="fire" /><span class="fire" />
								</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${item.ADV_STAGE == '컨설팅 신청'}">
									<a href="${contextPath }/dct/consulting/viewAll.do?mId=124&cnslIdx=${item.ADV_IDX}#tab1" >${item.ADV_STAGE }</a>
								</c:when>
								<c:when test="${item.ADV_STAGE == '보고서 작성'  }">
									<a href="${contextPath }/dct/consulting/viewAll.do?mId=124&cnslIdx=${item.ADV_IDX}#tab4">${item.ADV_STAGE }</a>
								</c:when>
								<c:otherwise>
								-
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${item.ADV_DELAY >= 5 and item.ADV_DELAY < 7}">
									<span class="fire" />
								</c:when>
								<c:when test="${item.ADV_DELAY >= 7 and item.ADV_DELAY < 10}">
									<span class="fire" /><span class="fire" />
								</c:when>
								<c:when test="${item.ADV_DELAY >= 10}">
									<span class="fire" /><span class="fire" /><span class="fire" />
								</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${item.CUSTOM_STAGE == '컨설팅 신청'}">
									<a href="${contextPath }/dct/consulting/viewAll.do?mId=124&cnslIdx=${item.CUSTOM_IDX}#tab1" >${item.CUSTOM_STAGE }</a>
								</c:when>
								<c:when test="${item.CUSTOM_STAGE == '보고서 작성'  }">
									<a href="${contextPath }/dct/consulting/viewAll.do?mId=124&cnslIdx=${item.CUSTOM_IDX}#tab4">${item.CUSTOM_STAGE }</a>
								</c:when>
								<c:when test="${item.CUSTOM_STAGE == '비용 처리'  }">
									<a href="${contextPath }/dct/developDct/support_list_form.do?mId=63">${item.CUSTOM_STAGE }</a>
								</c:when>
								<c:otherwise>
								-
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${item.CUSTOM_DELAY >= 5 and item.CUSTOM_DELAY < 7}">
									<span class="fire" />
								</c:when>
								<c:when test="${item.CUSTOM_DELAY >= 7 and item.CUSTOM_DELAY < 10}">
									<span class="fire" /><span class="fire" />
								</c:when>
								<c:when test="${item.CUSTOM_DELAY >= 10}">
									<span class="fire" /><span class="fire" /><span class="fire" />
								</c:when>
							</c:choose>
						</td>
						<td>
							<a href="${contextPath }/dct/sojt/acceptList.do?mId=151">${empty item.SOJT_STAGE ? '-' : item.SOJT_STAGE }</a>
							<c:choose>
								<c:when test="${item.SOJT_DELAY >= 5 and item.SOJT_DELAY < 7}">
									<span class="fire" />
								</c:when>
								<c:when test="${item.SOJT_DELAY >= 7 and item.SOJT_DELAY < 10}">
									<span class="fire" /><span class="fire" />
								</c:when>
								<c:when test="${item.SOJT_DELAY >= 10}">
									<span class="fire" /><span class="fire" /><span class="fire" />
								</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${item.CLINIC_STAGE eq '클리닉 신청' }">
									<a href="${contextPath }/dct/clinicDct/request_view_form.do?mId=66&reqIdx=${item.CLINIC_STATUS_IDX }&bplNo=${item.BPL_NO}">${item.CLINIC_STAGE }</a>
								</c:when>
								<c:when test="${item.CLINIC_STAGE eq '계획 수립' }">
									<a href="${contextPath }/dct/clinicDct/plan_view_form.do?mId=67&planIdx=${item.CLINIC_STATUS_IDX }&bplNo=${item.BPL_NO}">${item.CLINIC_STAGE }</a>
								</c:when>
								<c:when test="${item.CLINIC_STAGE eq '활동 결과' }">
									<a href="${contextPath }/dct/clinicDct/result_view_form.do?mId=104&resltIdx=${item.CLINIC_STATUS_IDX }&bplNo=${item.BPL_NO}">${item.CLINIC_STAGE }</a>
								</c:when>
								<c:when test="${item.CLINIC_STAGE eq '비용 처리' }">
									<a href="${contextPath }/dct/clinicDct/support_view_form.do?mId=105&sportIdx=${item.CLINIC_STATUS_IDX }&bplNo=${item.BPL_NO}">${item.CLINIC_STAGE }</a>
								</c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${item.CLINIC_DELAY >= 5 and item.CLINIC_DELAY < 7}">
									<span class="fire" />
								</c:when>
								<c:when test="${item.CLINIC_DELAY >= 7 and item.CLINIC_DELAY < 10}">
									<span class="fire" /><span class="fire" />
								</c:when>
								<c:when test="${item.CLINIC_DELAY >= 10}">
									<span class="fire" /><span class="fire" /><span class="fire" />
								</c:when>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${not empty item.HASHTAGS }">
									<a href="#" class="btn-hashtag">#${item.HASHTAGS }</a>
								</c:when>
								<c:otherwise>
									<span>-</span>
								</c:otherwise>
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
				<a href="#" data-page="1" class="btn-first btn-page">맨 처음 페이지로 이동</a>
				<a href="#" data-page="${curPage - 1 eq 0 ? 1 : curPage-1}" class="btn-preview btn-page">이전 페이지로 이동</a>
				<c:forEach var="i" begin="${start }" end="${end >= list[0].TOTAL_PAGE ? list[0].TOTAL_PAGE : end }">
					<c:choose>
						<c:when test="${i eq curPage }">
							<strong>${i }</strong>
						</c:when>
						<c:otherwise>
							<a href="#" data-page="${i}" class="btn-page">${i }</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<a href="#" data-page="${curPage + 1 eq list[0].TOTAL_PAGE+1 ? list[0].TOTAL_PAGE : curPage+1}" class="btn-next btn-page">다음 페이지로 이동</a>
				<a href="#" data-page="${list[0].TOTAL_PAGE}" class="btn-last btn-page">맨 마지막 페이지로 이동</a>
			</p>
			<!-- //페이징 네비게이션 -->
		</div>
	</div>
		<!-- //CMS 끝 -->
</div>
<form id="mover" method="POST" action>
	<input type="hidden" id="idx" name="idx" value="">
	<input type="hidden" id="bsc" name="bsc" value="">
	<input type="hidden" id="rslt" name="rslt" value="">
</form>
<!-- //contents -->
<!-- 모달 창 -->
<div class="mask"></div>
<div class="modal-wrapper" id="modal-action01">
	<h2>HRD 기초 컨설팅</h2>
	<div class="modal-area">
		<div class="modal-alert type02">
			<p>
				진행 : 기초진단 -> 설문조사 -> 기초진단<br>
				경과일 : <span class="fire"></span> (5일),
				<span class="fire"></span><span class="fire"></span> (7일),
				<span class="span-mobile-br"></span>
				<span class="fire"></span><span class="fire"></span><span class="fire"></span> (10일)
			</p>
		</div>
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
	$("#open-modal02").on("click", function() {
		$(".mask").fadeIn(150, function() {
			$("#modal-action02").show();
 		});
	});
	$("#modal-action01 .btn-modal-close").on("click", function() {
		$("#modal-action01").hide();
		$(".mask").fadeOut(150);
 	});
	$("#modal-action02 .btn-modal-close").on("click", function() {
		$("#modal-action02").hide();
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
	$('.btn-list').click(function(e) {
		e.preventDefault();
		let dest = $(this).data('dest');
		let form = $('form#mover')
		switch(dest) {
		case 'bsc':
			form.attr('action', '${contextPath}/dct/diagnosis/view.do?mId=36')
			$('form#mover input#idx').val($(this).data('bsc'))
			form.submit();
			break;
		case 'rslt':
			form.attr('action', '${contextPath}/dct/bsisCnsl/qustnr.do?mId=37')
			$('form#mover input#bsc').val($(this).data('bsc'))
			$('form#mover input#rslt').val($(this).data('rslt'))
			form.submit();
			break;
		case 'bsis':
			form.attr('action', '${contextPath}/dct/bsisCnsl/cnslt.do?mId=37')
			$('form#mover input#bsc').val($(this).data('bsc'))
			$('form#mover input#rslt').val($(this).data('rslt'))
			form.submit();
			break;
		}
	})
	$('.btn-page').click(function(e) {
		e.preventDefault();
		$('form#main input#page').val($(this).data('page'))
		$('form#main').submit();
	})
	$('.btn-hashtag').click(function(e) {
		e.preventDefault();
		$('form#main input#hashtag').val($(this).text().replace('#', ''))
		$('form#main').submit();
	})
 });
 
 function cleanup() {
	 $('textarea#modal-textfield02').val('');
 }
 
</script>
<c:if test="${!empty BOTTOM_PAGE}"><jsp:include page="${BOTTOM_PAGE}" flush="false"/></c:if>
