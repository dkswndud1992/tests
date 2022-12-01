<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Update</title>
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
		  	h1 {
		    	text-align:center;
		  	}
		  	div {
		  		float: right;
		  	}
		  	input{
		  		margin: 5px;
		  	}
		  	textarea{
		  		display: table;
		  		margin-left: auto;  
		  		margin-right: auto;
		  		margin-top: 10px;
		  		margin-bottom: 10px;
		  	}
		  	a:link {text-decoration: none; color: white;}
			a:visited {text-decoration: none; color: white;}
			a:active {text-decoration: none; color: white;}
			a:hover {text-decoration: underline; color: yellow;}
			
		</style>
	</head>
	<body  style="background-color:black; color:white">
		
		<h1><a href="home.do" style="text-decoration:none;">Hi Dark Board!</a></h1>
		<form action="update.do" method="post">
		    <table>
		        <tbody>
		        		<tr>
			                <th>No</th>
			                <td  ><input type="hidden" name="no" value="${boardNo}"/>${boardNo}</td>
			            </tr>
		                <tr>
			                <th>Title</th>
			                <td><input type="text" placeholder="제목을 입력하세요." name="title" value="${boardTitle}" required></td>
			            </tr>
			            <tr>
			                <th>Contents</th>
			                <td><textarea placeholder="내용을 입력하세요." rows="8" cols="85"  name="contents" required>${boardContents}</textarea></td>
			            </tr>
		        </tbody>
		    </table>
		    <div>
			    <input type="submit" value="수정" />
			    <input type="button" value="뒤로가기" onclick="window.history.back();" />
		    </div>
		</form>
	</body>
</html>