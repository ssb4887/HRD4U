<%@ include file="../../../../include/commonTop.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<meta http-equiv="Content-Script-Type" content="text/javascript" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
	
	<link rel="stylesheet" href="${moduleResourcePath}/certificate.css" />
	<link rel="stylesheet" href="${moduleResourcePath}/reset.css" />
	<link rel='stylesheet' href='//cdn.jsdelivr.net/npm/font-kopub@1.0/kopubbatang.min.css'>
	
	<title>교육과정 수료증</title>
	<style type="text/css" media="print">

		* {
			-webkit-print-color-adjust: exact !important;
		}

		body {
			background: #fff;
			padding-top: 50px;
			overflow: hidden;
			height: 863px;
		}

	    @page
	    {
	        margin: 0mm;  /* this affects the margin in the printer settings */
	    }
	</style>
	<script type="text/javascript">
		function closeWindow(){
			window.close();
		}
		
		window.print();
	</script>
</head>
<body>
	<!-- 전체 -->
	<div class="wrapper">
		<img src="${moduleResourcePath}/images/bg01.gif" class="bg-image" />
		<h1>
			<img src="${moduleResourcePath}/images/title.png" alt="수료증" />
		</h1>
		<h2>제 <c:out value='${member.CTFHV_NO}' /> 호</h2>
			
		<div class="user-info-box">
			<dl class="user-info01">
				<dt>성<span style="display: inline-block; padding-left: 50px"></span>명 </dt>
				<dd><c:out value='${member.MEMBER_NAME}' /></dd>
			</dl>

			<dl class="user-info01">
				<dt>소<span style="display: inline-block; padding-left: 16px;padding-right: 16px;">속</span>명 </dt>
				<dd><c:out value='${member.ORG_NAME}' /></dd>
			</dl>

			<dl class="user-info02">
				<dt>교육과정명 <span style="display: inline-block; padding-left: 12px"></span></dt>
				<dd><c:out value='${edc.EDC_NAME}' /></dd>
			</dl>

			<dl class="user-info03">
				<dt>교육<span style="display: inline-block; padding-left: 15px"></span>기간</dt>
				<dd>
					<fmt:formatDate pattern="yyyy-MM-dd" value="${edc.EDC_START_DATE}"/>
					~
					<fmt:formatDate pattern="yyyy-MM-dd" value="${edc.EDC_END_DATE}"/>
				</dd>
			</dl>	

			<p class="word01">
				위 사람은 한국산업인력공단이 개설한<br />
				<c:out value='${edc.EDC_CD_NAME}' />을 정히<br />
				수료하였기에 이 증서를 수여합니다.
			</p>
	
			<p class="certificate-date">
				<fmt:formatDate pattern="yyyy년 MM월 dd일" value="${member.CTFHV_ISSUE_DATE}"/>
			</p>
		</div>

		<p class="logo">
			<img src="${moduleResourcePath}/images/logo.png" alt="한국산업인력공단" />
		</p>
	</div>
	<!-- //전체 -->
</body>
</html>