<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="loginCheck" value="true" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Home</title>
		<style>
			table {
		    	width: 80%;
			    border: 1px solid #444444;
			    border-collapse: collapse;
			    border-color: white;
			    display: table;
			    margin-left: auto;  
			    margin-right: auto; 
		  	}
		  	th, td {
		    	border: 1px solid #444444;
		    	border-color: white;
		  	}
			.right {font-size: 20px; float:right; padding: 20px; border: 2px; border-style: dotted; border-color: white;}
	  		a:link {text-decoration: none; color: white;}
			a:visited {text-decoration: none; color: white;}
			a:active {text-decoration: none; color: white;}
			a:hover {text-decoration: underline; color: yellow;}

		</style>
		
	</head>
	
	<body  style="background-color:black; color:white; text-align: center;">
		<h1><a href="home.do" >Hi Dark Board!</a></h1>
		
		<c:if test="${not empty sessionScope.id}">
	        <a href="insert.do" class="right">+</a>
	    </c:if>

    <table>
        <thead>
            <tr>
                <th>No</th>
                <th>Title</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${boardList}" var="board">
                <tr>
                    <td>${board.boardNo}</td>
                    <td><a href='/detail.do?boardNo=${board.boardNo}'>${board.boardTitle}</a></td>
                    <td>${board.boardDate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
	<a href=${sessionScope.id != null ? "logout.do": "loginPage.do"}  class="right">${sessionScope.id != null ? "logout": "login"}</a> 

	</body>
	

</html>