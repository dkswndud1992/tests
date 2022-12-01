<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="main.project.util.CommonUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>test</title>
</head>
<body style="background-color: black; color: white; text-align: center">
	<br/><br/>hello world!<br/><br/>
	<jsp:forward page="src/jsp/testFindFutureImg.jsp"/>
	<%--
	<% 
		String a = "2019121204";
		// CommmonUtil test = new CommmonUtil();
		String[] b = CommmonUtil.selectArrayCalHour(a,"p",3);
		String value = CommmonUtil.selectNull(b);
		System.out.println("value: "+value);
	%>
	--%>
</body>
</html>