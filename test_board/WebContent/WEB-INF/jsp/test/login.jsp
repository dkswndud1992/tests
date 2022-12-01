<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>login</title>
		<script type="text/javascript" src="/resources/js/common.js"></script>
		<link href="/resources/css/common.css" rel="stylesheet" />

	</head>
	<body>
		
		<h1><a href="home.do" style="text-decoration:none;">Hi Dark Board!</a></h1>
		<form action="login.do" method="post">
		    <table>
		        <tbody>
		                <tr>
			                <th>ID</th>
			                <td><input type="text" placeholder="아이디를 입력하세요." name="id" required /></td>
			            </tr>
			            <tr>
			                <th>PASSWORD</th>
			                <td><input type="password" placeholder="비밀번호를 입력하세요." name="password" required /></td>
			            </tr>
		        </tbody>
		    </table>
		    <div>
			    <input type="submit" value="로그인" />
			    <input type="button" value="뒤로가기" onclick="window.history.back();" />
		    </div>
		</form>
	</body>
</html>