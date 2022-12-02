<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:include page="/WEB-INF/jsp/include/include_header.jsp"/>


<script type="text/javascript">
$(document).ready(function() {
	calendarInit();
});
/*
	달력 렌더링 할 때 필요한 정보 목록 
	- 현재 월(초기값 : 현재 시간)
	- 금월 마지막일 날짜와 요일
	- 전월 마지막일 날짜와 요일
*/

function calendarInit() {
	// 날짜 정보 가져오기
	var date = new Date(); // 현재 날짜(로컬 기준) 가져오기
	var utc = date.getTime() + (date.getTimezoneOffset() * 60 * 1000); // uct 표준시 도출
	var kstGap = 9 * 60 * 60 * 1000; // 한국 kst 기준시간 더하기
	var today = new Date(utc + kstGap); // 한국 시간으로 date 객체 만들기(오늘)
  
	// 달력에서 표기하는 날짜 객체
	var thisMonth = new Date(today.getFullYear(), today.getMonth(), today.getDate());
	
	var currentYear = thisMonth.getFullYear(); // 달력에서 표기하는 연
	var currentMonth = thisMonth.getMonth(); // 달력에서 표기하는 월
	var currentDate = thisMonth.getDate(); // 달력에서 표기하는 일

	// kst 기준 현재시간
	// console.log(thisMonth);

	// 캘린더 렌더링
	renderCalender(thisMonth);

	function renderCalender(thisMonth) {

		// 렌더링을 위한 데이터 정리
		currentYear = thisMonth.getFullYear();
		currentMonth = thisMonth.getMonth();
		currentDate = thisMonth.getDate();
		
		// 년도 선택기 갱신
		insertAroundYear(currentYear);

		// 이전 달의 마지막 날 날짜와 요일 구하기
		var startDay = new Date(currentYear, currentMonth, 0);
		var prevDate = startDay.getDate();
		var prevDay = startDay.getDay();

		// 이번 달의 마지막날 날짜와 요일 구하기
		var endDay = new Date(currentYear, currentMonth + 1, 0);
		var nextDate = endDay.getDate();
		var nextDay = endDay.getDay();

		// console.log(prevDate, prevDay, nextDate, nextDay);

		// 현재 월 표기
		$('#year').val(currentYear);
		$('#month').val((currentMonth + 1).toString().padStart(2, "0"));

		// 렌더링 html 요소 생성
		$('#day').html('');
		
		var col_index = 0;
		
		// 지난달
		for (var i = prevDate - prevDay + 1; i <= prevDate; i++) {
			if(col_index == 0){
				$('#day').append('<tr>');
			}
			
			var inner_html = '';
			inner_html += '<div>'+i+'</div>';
			inner_html += '<div>●</div>';
			
			$('#day').append('<td><del>' + inner_html + '</del></td>');
			
			if(col_index == 6){
				$('#day').append('</tr>');
				col_index = 0;
			}else{
				col_index++;
			}
		}
		
		// 이번달
		for (var i = 1; i <= nextDate; i++) {
			if(col_index == 0){
				$('#day').append('<tr>');
			}
			
			var inner_html = '';
			inner_html += '<div>'+i+'</div>';
			inner_html += '<div>●●●</div>';
			
			$('#day').append('<td class="day current">' + inner_html + '</td>');

			if(col_index == 6){
				$('#day').append('</tr>');
				col_index = 0;
			}else{
				col_index++;
			}
		}
		
		// 다음달
		for (var i = 1; i <= (7 - nextDay == 7 ? 0 : 7 - nextDay); i++) {
			if(col_index == 0){
				$('#day').append('<tr>');
			}
			
			var inner_html = '';
			inner_html += '<div>'+i+'</div>';
			inner_html += '<div>●●</div>';
			
			$('#day').append('<td><del>' + inner_html + '</del></td>');

			if(col_index == 6){
				$('#day').append('</tr>');
				col_index = 0;
			}else{
				col_index++;
			}
		}

		// 오늘 날짜 표기
		/* 
		if (today.getMonth() == currentMonth) {
			todayDate = today.getDate();
			var currentMonthDate = document.querySelectorAll('.dates .current');
			currentMonthDate[todayDate -1].classList.add('today');
		}
		 */
	}

	// 이전달로 이동
	$('#prev').on('click', function() {
		thisMonth = new Date(currentYear, currentMonth - 1, 1);
		renderCalender(thisMonth);
	});
	
	// 다음달로 이동
	$('#next').on('click', function() {
		thisMonth = new Date(currentYear, currentMonth + 1, 1);
		renderCalender(thisMonth); 
	});
	
	// 선택 년으로 이동
	$('#year').on('change', function() {
		thisMonth = new Date(parseInt($(this).val()), currentMonth, 1);
		renderCalender(thisMonth);
	});
	
	// 선택 달로 이동
	$('#month').on('change', function() {
		thisMonth = new Date(currentYear, parseInt($(this).val()) - 1, 1);
		renderCalender(thisMonth); 
	});
}


// 년도 선택기에 년도 채워넣기
function insertAroundYear (target_year){
	var prev_year = target_year - 1;
	var next_year = target_year + 1;
	
	$('#year').html('');
	
	$('#year').append('<option value="'+prev_year+'">'+prev_year+'</option>');
	$('#year').append('<option value="'+target_year+'">'+target_year+'</option>');
	$('#year').append('<option value="'+next_year+'">'+next_year+'</option>');
}

</script>

<div id="container" data-value="intro">
	<b id="prev">&lt;</b>
	<select id="year">
		<option value="2022">2022</option>
	</select>
	-
	<select id="month">
		<option value="01">1</option>
		<option value="02">2</option>
		<option value="03">3</option>
		<option value="04">4</option>
		<option value="05">5</option>
		<option value="06">6</option>
		<option value="07">7</option>
		<option value="08">8</option>
		<option value="09">9</option>
		<option value="10">10</option>
		<option value="11">11</option>
		<option value="12">12</option>
	</select>
	<b id="next">&gt;</b>
	<table border="1">
		<thead>
			<tr>
				<th>월</th>
				<th>화</th>
				<th>수</th>
				<th>목</th>
				<th>금</th>
				<th>토</th>
				<th>일</th>
			</tr>
		</thead>
		<tbody id="day"></tbody>
	</table>
</div>

<jsp:include page="/WEB-INF/jsp/include/include_footer.jsp"/>

