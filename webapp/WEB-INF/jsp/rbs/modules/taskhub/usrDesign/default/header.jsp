<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<c:when test="${param.usertype_idx >= 40 }">
		<jsp:include page="./header/headquarter.jsp" flush="false">
			<jsp:param name="cPath" value="${param.cName }" />
		</jsp:include>
	</c:when>
	<c:when test="${param.usertype_idx == 30 or param.usertype_idx == 31 }">
		<jsp:include page="./header/instt.jsp" flush="false">
			<jsp:param name="cPath" value="${param.cName }" />
		</jsp:include>
	</c:when>
	<c:when test="${param.usertype_idx == 20 }">
		<jsp:include page="./header/center.jsp" flush="false">
			<jsp:param name="cPath" value="${param.cName }" />
		</jsp:include>
	</c:when>
	<c:when test="${param.usertype_idx == 10 }">
		<jsp:include page="./header/consultant.jsp" flush="false">
			<jsp:param name="cPath" value="${param.cName }" />
		</jsp:include>
	</c:when>
	<c:when test="${param.usertype_idx == 5 }">
		<jsp:include page="./header/corpo.jsp" flush="false">
			<jsp:param name="cPath" value="${param.cName }" />
		</jsp:include>
	</c:when>
</c:choose>
	