<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Cache-Control" content="No-Cache"/>
	<title>Hecorea</title>
	<link rel="stylesheet" type="text/css" href="/js/plugin/jquery/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="/js/plugin/openlayers/ol.css" />

	<script type="text/javascript" src="/js/plugin/jquery/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="/js/plugin/jquery/jquery-ui.js"></script> 
	
	<script type="text/javascript" src="/js/plugin/highcharts/highcharts.js"></script>
	
	<script type="text/javascript" src="/js/plugin/openlayers/ol.js" ></script>
	
	<script type="text/javascript" src="/js/component/common.js"></script>
	<script type="text/javascript">
	<%-- 시작 함수 등록 --%>
	$(function(){
		<%-- 메인 메뉴 하이라이트 --%>
		$("#"+$("#container").data("value")).addClass("on");
		// $("#"+$("#container").data("value")).css("color", "red");
		
	});
	</script> 	
	
</head>

<body>
	<div>
		<header>
			<h1><a href="/main/intro.do">Home</a></h1>
			<div>
				<c:choose>
					<c:when test = "${!empty sessionScope.userId}">
						<p><strong>${sessionScope.userNm}</strong>님 로그인 되었습니다.</p>
						<span><a href="/userMain/modify.do">내정보수정</a></span>&nbsp;&nbsp;
						<span><a href="javascript: if(confirm('로그아웃 하시겠습니까?')) location.href='/userCont/deleteUserSession.do';">LOGOUT</a></span>
					</c:when>
				     
					<c:otherwise>
						<span><a href="/userMain/login.do">LOGIN</a></span>
					</c:otherwise>
				</c:choose>
			</div>
			
			<div>
				<ul>
					<li><a href="/main/intro.do" id="intro">Intro</a></li>
					<li><a href="/boardMain/list.do" id="board">Board</a></li>
				</ul>
			</div>
		</header>
	



