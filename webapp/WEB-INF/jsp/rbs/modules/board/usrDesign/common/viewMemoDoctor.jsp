			<c:set var="conMmgUTP"><spring:message code="Globals.code.USERTYPE_ADMIN"/></c:set>
			<c:set var="wrtUTPAuth" value="${elfn:getModuleUTP('MWT')}"/>
			<!-- <h3 class="titTypeC mgt20 mgb10">댓글</h3> -->
			<c:if test="${wrtUTPAuth < conMmgUTP}">
			<form id="${inputFormId}" name="${inputFormId}" method="post" action="<c:out value="${URL_SUBMITPROC}"/>" target="submit_target" enctype="multipart/form-data">
				<div class="comment-wrapper">
				</div>
				<a href="<c:out value="${URL_IDX_MEMOINPUT}"/>" title="취소" class="btnTypeB fn_btn_write_view" style="display:none;">취소</a>
			</form>
			</c:if>
			<div id="${listFormId}"></div>