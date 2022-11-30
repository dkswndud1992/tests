<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>data checker</title>

<!-- 순서가 달라지면 안나옴.  -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>

<script src="./resources/js/util/highcharts.js"></script> <!-- 하이차트 기본 -->
<script src="./resources/js/util/exporting.js"></script>  <!--우측상단에 차트를 이미지로 출력할 수 있게하는 버튼을 넣는다.-->

<script src="./resources/js/util/series-label.js"></script> <!-- 데이터에 라벨링 -->
<script src="./resources/js/util/export-data.js"></script> <!-- csv 로 -->


<link rel="stylesheet" type="text/css" href="./resources/css/common/common.css" /> <!-- 기본 css 사용 -->
<script src="./resources/js/function/common.js"></script> <!-- 기본 js 사용 -->

<script src="./resources/js/function/charts_test.js"></script> <!-- 하이차트 사용 -->
<script src="./resources/js/function/ajax_test.js"></script> <!-- ajax 사용 -->

</head>
<body>

	<!-- 헤더 -->
	<header>
		<br/><br/>Data Check!<br/><br/>
		<div id="menu" style="display:inline;">
			<div class="tab_btn" style="background-color: rgba(100,0,0,0.7); display: inline-block; width: 200px;">(db, db 비교)</div>
			<div class="tab_btn" style="background-color: rgba(100,0,0,0.7); color:rgba(255,255,255,0.7); display: inline-block; width: 200px; text-decoration: line-through;">(db, csv 비교)</div>
		</div>
		<br/><br/>
		<form action="#">
			<select name="table1" id="table1" class="table_selecter">
				<option value="">table선택</option>
				<c:forEach var="table" items="${tableList}" varStatus="status">
				    <option value="${status.count}"> <c:out value="${table.TABLELIST}" /> </option>
				</c:forEach>
			</select>
			<select name="table2" id="table2" class="table_selecter">
			    <option value="">table선택</option>
			    <c:forEach var="table" items="${tableList}" varStatus="status">
				    <option value="${status.count}"> <c:out value="${table.TABLELIST}" /> </option>
				</c:forEach>
			</select>
			<input type="submit" value="합쳐 비교하기(토글)" style="text-decoration: line-through;">		
		</form>
	</header>
	
	<!-- 콘텐츠 -->
	<div class="content">
		<div id="view_db_charts">
			<div id="chart1" class="chart"></div>
			<br/>
			<div id="chart2" class="chart"></div>
		</div>
		<div id="view_db_table">
			<table id="a"></table>
			<br/>
			<table id="c"></table>
		</div>
		<div id="view_test_json">
			<table id="b"></table>
		</div>
	</div>
	
	<!-- 풋터 -->
	<footer>
        <ul>
            <li><a id="btn_charts_db_view" class="manu_btn" href="javascript: show_btn($('#btn_charts_db_view'),$('#view_db_charts'));">그래프로 보기</a></li>
            <li><a id="btn_table_db_view" class="manu_btn" href="javascript: show_btn($('#btn_table_db_view'),$('#view_db_table'));">테이블로 보기</a></li>
            <li><a id="btn_test_view" class="manu_btn" href="javascript: show_btn($('#btn_test_view'),$('#view_test_json')); ajax_test($('#b'));">json test</a></li>
        </ul>
    </footer>
	
</body>

</html>