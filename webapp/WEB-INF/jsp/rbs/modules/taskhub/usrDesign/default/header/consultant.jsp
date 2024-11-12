	<div class="enterprise-title-wrapper">
		<div class="name">
			<strong class="mr0" style="margin-right:16px;">${name }</strong> 님 
 		</div>
		<div class="info">
			<span class="point-color01">(컨설턴트)</span>
		</div>
	</div>
	<div class="tabmenu-wrapper">
		<ul>
			<li>
				<a href="${pageContext.request.contextPath}/web/taskhub/main.do?mId=48" class="${param.cPath eq 'main' ? 'active' : '' }">현황</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/web/taskhub/program/cnsl.do?mId=48" class="${param.cPath eq 'program' ? 'active' : '' }">사업 조회</a>
			</li>
			<li>
				<a href="${pageContext.request.contextPath}/web/taskhub/inquiry.do?mId=48" class="${param.cPath eq 'inquiry' ? 'active' : '' }">비용 조회</a>
			</li>
		</ul>
	</div>
