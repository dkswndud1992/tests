<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>data checker</title>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script src="/resources/js/util/highcharts.js"></script>
<script src="/resources/js/util/windbarb.js"></script>
<script src="/resources/js/util/accessibility.js"></script>
<script src="/resources/js/function/common.js"></script>
<script type="text/javascript">
	<%-- 페이지 로딩 후 실행 --%>
	$(function(){
		var now_type_10_min = selectNowType10Min();
		var now_type_1_hour = selectNowType1Hour();
		
		selectObsRainList(now_type_10_min);
		selectObsWindWithTmpList(now_type_1_hour);
		
		selectRnChart();
		selectTmpChart();
		selectWsChart();
		selectDustChart();
		
		$("#radar_img").attr("src", "https://www.kma.go.kr/repositary/image/rdr/img/RDR_CMP_WRC_"+now_type_10_min+".png");
	});
	

	<%-- 강우 관측 6시간이전부터 데이터 가져오기 --%>
	function selectObsRainList(yyyymmddhhmm){
		return $.ajax({
			url : "/selectObsRainList.do",
			dataType : "json",
			data: {
				yyyymmddhhmm: yyyymmddhhmm,
			},
			success : function(data){
				console.log(data);
			},
		});
	}

	<%-- 바람, 온도 관측 6시간이전부터 데이터 가져오기 --%>
	function selectObsWindWithTmpList(yyyymmddhh){
		return $.ajax({
			url : "/selectObsWindWithTmpList.do",
			dataType : "json",
			data: {
				yyyymmddhh: yyyymmddhh,
			},
			success : function(data){
				console.log(data);
			},
		});
	}

	<%-- 강우 차트 생성 --%>
	function selectRnChart(){
		Highcharts.chart("rn_chart", {
			chart: {
				type: "column",
			},
			credits:{
				enabled: false,
			},
			title: {
				text: null,
			},
			legend:{
				enabled: false,
			},
			xAxis: {
				type: "datetime",
				tickInterval: 36e5,
				labels:{
					format: "{value:%H:%M}",
				},
			},
			yAxis: {
				min: 0,
				title: {
					text: "Rainfall (mm)",
				}
			},
			tooltip: {
				headerFormat: "<b>{point.x:%Y-%m-%d %H:%M}</b><br>",
				pointFormat: "Rainfall: {point.y:.2f} mm",
			},
			plotOptions: {
				series: {
					marker: {
						enabled: false,
					}
				}
			},
			series: [
				{
					name: "rainfall",
					data: [
						[Date.UTC(1970, 10, 25), 0],
						[Date.UTC(1970, 11,  6), 0.25],
						[Date.UTC(1970, 11, 20), 1.41],
						[Date.UTC(1970, 11, 25), 1.64],
						[Date.UTC(1971, 0,  4), 1.6],
						[Date.UTC(1971, 0, 17), 2.55],
						[Date.UTC(1971, 0, 24), 2.62],
						[Date.UTC(1971, 1,  4), 2.5],
						[Date.UTC(1971, 1, 14), 2.42],
						[Date.UTC(1971, 2,  6), 2.74],
						[Date.UTC(1971, 2, 14), 2.62],
						[Date.UTC(1971, 2, 24), 2.6],
						[Date.UTC(1971, 3,  1), 2.81],
						[Date.UTC(1971, 3, 11), 2.63],
						[Date.UTC(1971, 3, 27), 2.77],
						[Date.UTC(1971, 4,  4), 2.68],
						[Date.UTC(1971, 4,  9), 2.56],
						[Date.UTC(1971, 4, 14), 2.39],
						[Date.UTC(1971, 4, 19), 2.3],
						[Date.UTC(1971, 5,  4), 2],
						[Date.UTC(1971, 5,  9), 1.85],
						[Date.UTC(1971, 5, 14), 1.49],
						[Date.UTC(1971, 5, 19), 1.27],
						[Date.UTC(1971, 5, 24), 0.99],
						[Date.UTC(1971, 5, 29), 0.67],
						[Date.UTC(1971, 6,  3), 0.18],
						[Date.UTC(1971, 6,  4), 0],
					],
				},
			],
		});
	}
	
	<%-- 기온 차트 생성 --%>
	function selectTmpChart(){
		Highcharts.chart("tmp_chart", {
			chart: {
				type: "line",
			},
			credits:{
				enabled: false,
			},
			title: {
				text: null,
			},
			legend:{
				enabled: false,
			},
			xAxis: {
				type: "datetime",
				tickInterval: 36e5,
				labels:{
					format: "{value:%H:%M}",
				},
			},
			yAxis: {
				title: {
					text: "Temperatures (°C)",
				}
			},
			tooltip: {
				headerFormat: "<b>{point.x:%Y-%m-%d %H:%M}</b><br>",
				pointFormat: "Temperatures: {point.y:.2f} °C",
			},
			plotOptions: {
				series: {
					marker: {
						enabled: false,
					}
				}
			},
			series: [
				{
					name: "temperatures",
					data: [
						[Date.UTC(1970, 10, 25), 0],
						[Date.UTC(1970, 11,  6), 0.25],
						[Date.UTC(1970, 11, 20), 1.41],
						[Date.UTC(1970, 11, 25), 1.64],
						[Date.UTC(1971, 0,  4), 1.6],
						[Date.UTC(1971, 0, 17), 2.55],
						[Date.UTC(1971, 0, 24), 2.62],
						[Date.UTC(1971, 1,  4), 2.5],
						[Date.UTC(1971, 1, 14), 2.42],
						[Date.UTC(1971, 2,  6), 2.74],
						[Date.UTC(1971, 2, 14), 2.62],
						[Date.UTC(1971, 2, 24), 2.6],
						[Date.UTC(1971, 3,  1), 2.81],
						[Date.UTC(1971, 3, 11), 2.63],
						[Date.UTC(1971, 3, 27), 2.77],
						[Date.UTC(1971, 4,  4), 2.68],
						[Date.UTC(1971, 4,  9), 2.56],
						[Date.UTC(1971, 4, 14), 2.39],
						[Date.UTC(1971, 4, 19), 2.3],
						[Date.UTC(1971, 5,  4), 2],
						[Date.UTC(1971, 5,  9), 1.85],
						[Date.UTC(1971, 5, 14), 1.49],
						[Date.UTC(1971, 5, 19), 1.27],
						[Date.UTC(1971, 5, 24), 0.99],
						[Date.UTC(1971, 5, 29), 0.67],
						[Date.UTC(1971, 6,  3), 0.18],
						[Date.UTC(1971, 6,  4), 0],
					],
				},
			],
		});
	}
	
	<%-- 풍속 차트 생성 --%>
	function selectWsChart(){
		Highcharts.chart("ws_chart", {
			credits:{
				enabled: false,
			},
			title: {
				text: null,
			},
			legend:{
				enabled: false,
			},
			xAxis: {
				type: "datetime",
				tickInterval: 36e5,
				labels:{
					format: "{value:%H:%M}",
				},
			},
			yAxis: {
				min: 0,
				title: {
					text: "Wind speed (m/s)",
				}
			},
			tooltip: {
				headerFormat: "<b>{point.x:%Y-%m-%d %H:%M}</b><br>",
				pointFormatter: function(){
					if(this.series.name == "wind_speed"){
						return "Wind speed: "+this.y.toFixed(2)+" m/s"
					} 
				},
				shared: true,
			},
			plotOptions: {
				series: {
					marker: {
						enabled: false,
					}
				}
			},
			series: [
				{
					name: "wind_speed",
					id: "wind_speed",
					type: "line",
					data: [
						[Date.UTC(1970, 10, 25), 0],
						[Date.UTC(1970, 11,  6), 0.25],
						[Date.UTC(1970, 11, 20), 1.41],
						[Date.UTC(1970, 11, 25), 1.64],
						[Date.UTC(1971, 0,  4), 1.6],
						[Date.UTC(1971, 0, 17), 2.55],
						[Date.UTC(1971, 0, 24), 2.62],
						[Date.UTC(1971, 1,  4), 2.5],
						[Date.UTC(1971, 1, 14), 2.42],
						[Date.UTC(1971, 2,  6), 2.74],
						[Date.UTC(1971, 2, 14), 2.62],
						[Date.UTC(1971, 2, 24), 2.6],
						[Date.UTC(1971, 3,  1), 2.81],
						[Date.UTC(1971, 3, 11), 2.63],
						[Date.UTC(1971, 3, 27), 2.77],
						[Date.UTC(1971, 4,  4), 2.68],
						[Date.UTC(1971, 4,  9), 2.56],
						[Date.UTC(1971, 4, 14), 2.39],
						[Date.UTC(1971, 4, 19), 2.3],
						[Date.UTC(1971, 5,  4), 2],
						[Date.UTC(1971, 5,  9), 1.85],
						[Date.UTC(1971, 5, 14), 1.49],
						[Date.UTC(1971, 5, 19), 1.27],
						[Date.UTC(1971, 5, 24), 0.99],
						[Date.UTC(1971, 5, 29), 0.67],
						[Date.UTC(1971, 6,  3), 0.18],
						[Date.UTC(1971, 6,  4), 0],
					],
				},
				{
					name: "wind_direction",
					type: "windbarb",
					showInLegend: false,
					onSeries: "wind_speed",
					data: [
						[Date.UTC(1970, 10, 25), 0, 177.9],
						[Date.UTC(1970, 11,  6), 0.25, 177.9],
						[Date.UTC(1970, 11, 20), 1.41, 177.9],
						[Date.UTC(1970, 11, 25), 1.64, 177.9],
						[Date.UTC(1971, 0,  4), 1.6, 177.9],
						[Date.UTC(1971, 0, 17), 2.55, 177.9],
						[Date.UTC(1971, 0, 24), 2.62, 177.9],
						[Date.UTC(1971, 1,  4), 2.5, 177.9],
						[Date.UTC(1971, 1, 14), 2.42, 177.9],
						[Date.UTC(1971, 2,  6), 2.74, 177.9],
						[Date.UTC(1971, 2, 14), 2.62, 177.9],
						[Date.UTC(1971, 2, 24), 2.6, 177.9],
						[Date.UTC(1971, 3,  1), 2.81, 177.9],
						[Date.UTC(1971, 3, 11), 2.63, 177.9],
						[Date.UTC(1971, 3, 27), 2.77, 177.9],
						[Date.UTC(1971, 4,  4), 2.68, 177.9],
						[Date.UTC(1971, 4,  9), 2.56, 177.9],
						[Date.UTC(1971, 4, 14), 2.39, 177.9],
						[Date.UTC(1971, 4, 19), 2.3, 177.9],
						[Date.UTC(1971, 5,  4), 2, 177.9],
						[Date.UTC(1971, 5,  9), 1.85, 177.9],
						[Date.UTC(1971, 5, 14), 1.49, 177.9],
						[Date.UTC(1971, 5, 19), 1.27, 177.9],
						[Date.UTC(1971, 5, 24), 0.99, 177.9],
						[Date.UTC(1971, 5, 29), 0.67, 177.9],
						[Date.UTC(1971, 6,  3), 0.18, 177.9],
						[Date.UTC(1971, 6,  4), 0, 177.9],
					],
				},
			],
		});
	}
	
	<%-- 미세먼지 차트 생성 --%>
	function selectDustChart(){
		Highcharts.chart("dust_chart", {
			chart: {
				type: "line",
			},
			credits:{
				enabled: false,
			},
			title: {
				text: null,
			},
			legend:{
				enabled: false,
			},
			xAxis: {
				type: "datetime",
				tickInterval: 36e5,
				labels:{
					format: "{value:%H:%M}",
				},
			},
			yAxis: {
				min: 0,
				title: {
					text: "Fine dust (mm)",
				}
			},
			tooltip: {
				headerFormat: "<b>{point.x:%Y-%m-%d %H:%M}</b><br>",
				pointFormat: "Fine dust: {point.y:.2f} mm",
			},
			plotOptions: {
				series: {
					marker: {
						enabled: false,
					}
				}
			},
			series: [
				{
					name: "fine_dust",
					data: [
						[Date.UTC(1970, 10, 25), 0],
						[Date.UTC(1970, 11,  6), 0.25],
						[Date.UTC(1970, 11, 20), 1.41],
						[Date.UTC(1970, 11, 25), 1.64],
						[Date.UTC(1971, 0,  4), 1.6],
						[Date.UTC(1971, 0, 17), 2.55],
						[Date.UTC(1971, 0, 24), 2.62],
						[Date.UTC(1971, 1,  4), 2.5],
						[Date.UTC(1971, 1, 14), 2.42],
						[Date.UTC(1971, 2,  6), 2.74],
						[Date.UTC(1971, 2, 14), 2.62],
						[Date.UTC(1971, 2, 24), 2.6],
						[Date.UTC(1971, 3,  1), 2.81],
						[Date.UTC(1971, 3, 11), 2.63],
						[Date.UTC(1971, 3, 27), 2.77],
						[Date.UTC(1971, 4,  4), 2.68],
						[Date.UTC(1971, 4,  9), 2.56],
						[Date.UTC(1971, 4, 14), 2.39],
						[Date.UTC(1971, 4, 19), 2.3],
						[Date.UTC(1971, 5,  4), 2],
						[Date.UTC(1971, 5,  9), 1.85],
						[Date.UTC(1971, 5, 14), 1.49],
						[Date.UTC(1971, 5, 19), 1.27],
						[Date.UTC(1971, 5, 24), 0.99],
						[Date.UTC(1971, 5, 29), 0.67],
						[Date.UTC(1971, 6,  3), 0.18],
						[Date.UTC(1971, 6,  4), 0],
					],
				},
			],
		});
	}
	
	
	<%-- x,y로 각도 계산 --%>
	function calcAngleDegrees(x, y) {
		return (Math.atan2(x, y) * 180 / Math.PI) + 180;
	}
	
</script>
</head>
<body>
	<%-- header --%>
	<div>
		<h1>건설 현장 자연재해 실황, 예측 정보제공 파일럿 시스템</h1>
	</div>
	
	
	<%-- contents --%>
	<div style="display: flex; justify-content: space-around;">
		<div>
			<%-- 안전정보 --%>
			<div>
				<table border="1px">
					<tbody>
						<tr>
							<th>강수</th>
							<th>기온</th>
							<th>풍속</th>
							<th>미세먼지</th>
							<th rowspan="2">침수예측정보</th>
						</tr>
						<tr>
							<td>안전</td>
							<td>안전</td>
							<td>안전</td>
							<td>안전</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<%-- 강우 실황 및 예측 --%>
			<div>
				<h2>강우 실황 및 예측</h2>
				<table border="1px">
					<tbody>
						<tr>
							<th>실황</th>
							<th colspan="4">예측</th>
						</tr>
						<tr>
							<th>1시간</th>
							<th>3시간</th>
							<th>6시간</th>
							<th>12시간</th>
							<th>24시간</th>
						</tr>
						<tr>
							<td id="obs_rn">default</td>
							<td id="fc_rn_3hr">default</td>
							<td id="fc_rn_6hr">default</td>
							<td id="fc_rn_12hr">default</td>
							<td id="fc_rn_24hr">default</td>
						</tr>
					</tbody>
				</table>
			</div>
			<br/>
			
			<div>
				<table border="1px">
					<tbody>
						<tr>
							<th colspan="2">기온</th>
							<th colspan="2">풍속</th>
							<th>미세먼지</th>
						</tr>
						<tr>
							<th>실황</th>
							<th>1시간 예보</th>
							<th>실황</th>
							<th>1시간 예보</th>
							<th>실황</th>
						</tr>
						<tr>
							<td id="obs_tmp">default</td>
							<td id="fc_tmp_1hr">default</td>
							<td id="obs_ws">default</td>
							<td id="fc_ws_1hr">default</td>
							<td id="obs_dust">default</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div>
				<h2>레이더, 기상특보</h2>
				<img  id="radar_img" src="https://www.kma.go.kr/repositary/image/rdr/img/RDR_CMP_WRC_202205021100.png" alt="radar_img" style="width: 300px;">
				<b id="weather_warning">default</b>
			</div>
		</div>
		
		<div>
			<div>
				<h2>현장정보</h2>
				<div>경남 통영시 광도면 안정리 2050 정보</div>
			</div>
			
			<div>
				<h2>강수 실황 및 예측</h2>
				<div id="rn_chart" style="width: 500px; height: 100px;"></div>
			</div>
			
			<div>
				<h2>기온</h2>
				<div id="tmp_chart" style="width: 500px; height: 100px;"></div>
			</div>
			
			<div>
				<h2>풍속</h2>
				<div id="ws_chart" style="width: 500px; height: 200px;"></div>
			</div>
			
			<div>
				<h2>미세먼지</h2>
				<div id="dust_chart" style="width: 500px; height: 100px;"></div>
			</div>
		</div>
	</div>
</body>

</html>