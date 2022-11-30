<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sample</title>
<script type="text/javascript" src="/js/plugin/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/js/component/common.js"></script>
<script type="text/javascript">
$(function (){
	$("#now").attr("title",selectNow());
	$("#now").text("현재 : "+selectNow().substr(8,2)+"시");
});
// ajax Sample
function ajaxSample (){
	var Param = $("#ajaxParam").val();
    $.ajax({
        url : "/sampleCont/ajaxSample.do",
        dataType : "json",
        type: "POST",
        data: "Param="+Param,
        success : function(data){
        	$("#param").text(data.Param);
        },
        error : function( e ) {
            return false;
        }
    });
}
// db test Sample
function dbTestSample (selectDB){
    $.ajax({
        url : "/sampleCont/dbTestSample.do",
        dataType : "json",
        type: "POST",
        data: "Param="+selectDB,
        success : function(data){
        	if(data.Param == 1){
        		alert("연결 성공!");
        	}else{
        		alert("연결 실패!");
        	}
        },
        error : function( e ) {
            return false;
        }
    });
}
</script>
</head>
<body>

	<h1>Hello Sample!</h1>
	<h6 id="now">now</h6>
	
	- 파라미터 확인 <br>
	returnParam : <div id = "param">${Param == null ? "null" : Param}</div>
	
	<br>
	- db 연결 테스트
	<input type="button" value="mysql" onclick="dbTestSample('mysql')" />
	<input type="button" value="oracle" onclick="dbTestSample('oracle')" />
	<input type="button" value="postgresql" onclick="dbTestSample('postgresql')" />
	<br>
	
	<br>
	- select 테스트
	<form action="/sample/selectSample.do" method="post">
		selectSample.do <br>
		selectParam : <input type="text" name="selectParam"> <br>
		<input type="submit" value = "selectSample">
	</form>
	
	<br>
	- insert 테스트
	<form action="/sample/insertSample.do" method="post">
		insertSample.do <br>
		insertParam : <input type="text" name="insertParam"> <br>
		<input type="submit" value = "insertSample">
	</form>
	
	<br>
	- update 테스트
	<form action="/sample/updateSample.do" method="post">
		updateSample.do <br>
		beforeParam : <input type="text" name="beforeParam"> <br>
		afterParam : <input type="text" name="afterParam"> <br>
		updateEncryptionParam : <input type="text" name="updateEncryptionParam"> <br>
		<input type="submit" value = "updateSample">
	</form>
	
	<br>
	- delete 테스트
	<form action="/sample/deleteSample.do" method="post">
		deleteSample.do <br>
		deleteParam : <input type="text" name="deleteParam"> <br>
		<input type="submit" value = "deleteSample">
	</form>
	
	<br>
	- redirect 테스트
	<form action="/sample/redirectSample.do">
		<input type="submit" value = "redirectSample">
	</form>
	
	<br>
	- interceptor 테스트
	<form action="/sample/mainSample.do">
		requestParam : <input type="text" name="requestParam"> <br>
		<input type="submit" value = "interceptorSample">
	</form>
	
	<br>
	- session 테스트
	<form action="/sample/sessionSample.do">
		<input type="submit" value = "sessionSample toggle">
		<input type="button" value = "session 확인" onclick="alert('${sessionScope.SampleKey}');">
	</form>
	
	<br>
	- ajax 테스트 <br>
	ajaxParam : <input type="text" id="ajaxParam"> 
	<input type="button" value = "ajax 확인" onclick="ajaxSample();">
	<br>
	
	<br>
	- list 테스트
	<form action="/sample/selectSampleList.do" method="get">
		selectSampleList.do <br>
		startNum : <input type="text" name="startNum"> <br>
		limitNum : <input type="text" name="limitNum"> <br>
		<input type="submit" value = "selectSampleList">
	</form>
	
	<table>
        <thead>
            <tr>
            	<th>sample_date</th>
                <th>sample_id</th>
                <th>sample_password</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${ParamList}" var="item">
                <tr>
                	<td>${item.sample_date}</td>
                	<td>${item.sample_id}</td>
                    <td>${item.sample_password}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>