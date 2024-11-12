<%@page import="com.clipsoft.clipreport.server.service.drm.ClipReportDRM"%><%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@include file="Property.jsp"%><%
//out.clear();
//out=pageContext.pushBody();

//파라미터명 reportKey , pageNum, isPageView
String strIsPageView = request.getParameter("isPageView");
//String strIsPageView ="true";
boolean isPageView = null == strIsPageView ? false : (strIsPageView.equalsIgnoreCase("true")? true : false);

if(isPageView){
	String pageView = ClipReportDRM.ClipBarcodeVerifyPage(request, propertyPath);	
	%><%=pageView%><%
}
else{
	String barcodeHash = ClipReportDRM.ClipBarcodeVerify(request, propertyPath);	
%>
<script>
function getHash(){
	
	var barcodeHash = "<%=barcodeHash%>";
	
	var message = {
            'fn': 'getHash',
            'hash': barcodeHash
        };
	
	return message;
}
</script>
<%
}
%>