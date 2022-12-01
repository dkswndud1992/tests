<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Detail</title>
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
		    	text-align: center;
		  	}
		  	div {
		  		float: right;
		  	}
		  	input{
		  		margin: 5px;
		  	}
		  	a:link {text-decoration: none; color: white;}
			a:visited {text-decoration: none; color: white;}
			a:active {text-decoration: none; color: white;}
			a:hover {text-decoration: underline; color: yellow;}
			
		</style>
		
		<script type="text/javascript">
			
	        function updateBoard(){
	        	var boardNo = document.getElementById("boardNo").innerHTML;
		        var boardTitle = document.getElementById("boardTitle").innerHTML;
		        var boardContents = document.getElementById("boardContents").innerHTML;
		        var parm = new Array();
		        parm.push( ['no', boardNo] );
		        parm.push( ['title', boardTitle] );
		        parm.push( ['contents', boardContents] );
		        createForm("updatePage.do", "post", parm);
	        }
	        
	        function deleteBoard(){
	        	var confirm_yn = confirm("삭제하시겠습니까?");
	        	if(confirm_yn == false){
	        		return
	        	}
	        	var boardNo = document.getElementById("boardNo").innerHTML;
		        var boardTitle = document.getElementById("boardTitle").innerHTML;
		        var boardContents = document.getElementById("boardContents").innerHTML;
		        var parm = new Array();
		        parm.push( ['no', Number(boardNo)] );
		        createForm("delete.do", "get", parm);
	        }
	        
	        function createForm(action, method, parm){
	        	var form = document.createElement("form");
		        var input = new Array();

	        	form.action = action;
		        form.method = method;
		        
		        for (var i = 0; i < parm.length; i++) {
		            input[i] = document.createElement("input");
		            input[i].setAttribute("type", "hidden");
		            input[i].setAttribute('name', parm[i][0]);
		            input[i].setAttribute("value", parm[i][1]);
		            form.appendChild(input[i]);
		        }
		        document.body.appendChild(form);
		        form.submit();
	        }
	        
    	</script>
	</head>
	<body  style="background-color:black; color:white">
		
		<h1><a href="home.do" style="text-decoration:none;">Hi Dark Board!</a></h1>
 
    <table>
        <tbody>
                <tr>
                    <th>No</th>
	                <td id="boardNo">${boardDetail.boardNo}</td>
                </tr>
                <tr>
	                <th>Title</th>
	                <td id="boardTitle">${boardDetail.boardTitle}</td>
	            </tr>
	            <tr>
	                <th>Contents</th>
	                <td id="boardContents">${boardDetail.boardContents}</td>
	            </tr>
	            <tr>
	                <th>Date</th>
	                <td>${boardDetail.boardDate}</td>
	            </tr>
        </tbody>
    </table>
    <div>
    	<c:if test="${not empty sessionScope.id}">
	        <input type="button" onclick="updateBoard()" value="수정" />
	    	<input type="button" onclick="deleteBoard()" value="삭제" />
	    </c:if>
	    <input type="button" value="뒤로가기" onclick="window.history.back();"/>
	</div>
	</body>
</html>