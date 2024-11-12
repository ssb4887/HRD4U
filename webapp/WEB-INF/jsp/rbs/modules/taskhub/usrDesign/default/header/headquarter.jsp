	<div class="enterprise-title-wrapper">
		<div class="name">
			<strong class="mr0">${name }</strong>
		</div>
		<div class="info">
			님
			<span class="point-color01">(공단본부)</span>
		</div>
	</div>
	<div class="tabmenu-wrapper">
		<ul>
			<li>
				<a href="${pageContext.request.contextPath }/dct/taskhub/main.do?mId=48" class="${param.cPath eq 'main' ? 'active' : '' }">현황</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/dct/taskhub/program/hrdbsis.do?mId=48" class="${param.cPath eq 'program' ? 'active' : '' }">사업 조회</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/dct/taskhub/corp/corps.do?mId=48" class="${param.cPath eq 'corporation' ? 'active' : '' }">기업 조회</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/dct/taskhub/inquiry.do?mId=48" class="${param.cPath eq 'inquiry' ? 'active' : '' }">비용 조회</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath }/dct/taskhub/reqlist.do?mId=48" class="${param.cPath eq 'support' ? 'active' : '' }">지원 요청</a>
			</li>
		</ul>
	</div>
