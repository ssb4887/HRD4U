<%@page contentType="text/html; charset=euc-kr" %>
<%@page import="rbs.egovframework.LoginVO" %>
<%@page import="java.util.Enumeration" %>
<%
//String memberIdx = ((LoginVO)session.getAttribute("preLoginVO")).getMemberIdx();
out.println("<font color=red size=5>Session Get</font><br><p>");
out.println("Container ID : " + System.getProperty("jvmid") + "<br>");
out.println("Session ID : " + session.getId() + "<br>");
out.println("Session Get : " + (LoginVO)session.getAttribute("loginVO") + "<br><p>");
Enumeration enumeration = session.getAttributeNames();
while(enumeration.hasMoreElements()) {
	String name_ = enumeration.nextElement().toString();
	out.println(" - " + name_ + " : " + session.getAttribute(name_) + "<br>");
}
out.println("<br>");
//out.println("Session Get : " + memberIdx + "<br><p>");
out.println("Session Timeout : " + session.getMaxInactiveInterval() + " sec.<br>");
out.println("Session Timeout : " + session.getMaxInactiveInterval()/60 + " min.<br>");
out.println("Session Timeout : " + (double)session.getMaxInactiveInterval()/3600 + " hour.<br><p>");
out.println("session.getLastAccessedTime() : " + new java.util.Date(session.getLastAccessedTime()).toString() + "<br>");
out.println("session.getCreationTime() : " + new java.util.Date(session.getCreationTime()).toString() + "<br>");
%>