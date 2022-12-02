<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<META http-equiv="Expires" content="-1">
	<META http-equiv="Pragma" content="no-cache">
	<META http-equiv="Cache-Control" content="No-Cache">
	<title>오픈레이어스 test</title>
	<link rel="stylesheet" type="text/css" href="/js/plugin/openlayers/ol.css"/>
	
    <script type="text/javascript" src="/js/plugin/jquery/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="/js/plugin/openlayers/ol.js"></script>
	
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1a223af3w2a26b13c3c9f2b28181f717d11"></script>
	
<script type="text/javascript">
var map = "";
var polygon_layer = "";
var overlay = "";

$(function(){
	selectMap();
	selectData();
});

<%-- 맵 생성 --%>
function selectMap(){
	var init_lon_lat = [126.97842012354243, 37.5667852426329];
	var init_coordinate = [];

	init_coordinate = ol.proj.fromLonLat(init_lon_lat);
	
	<%-- 맵 뷰 설정 --%>
	var view = new ol.View({
		projection: ol.proj.get("EPSG:2097"),
		center: init_coordinate,
		zoom: 9,
		maxZoom :20,
		minZoom :7,
		extent: [13866306.365025746, 4063691.023244139, 14479025.58375972, 4774249.638183137],
	});
	
	<%-- basemap 레이어 생성 --%>
	var satellite = new ol.layer.Tile({
		name : "satellite",
		visible: true,
		extent: [13866306.365025746, 4063691.023244139, 14479025.58375972, 4774249.638183137],
		source: new ol.source.XYZ({
			url: "https://xdworld.vworld.kr/2d/Satellite/service/{z}/{x}/{y}.jpeg"
		})
	});
	
	overlay = new ol.Overlay({
		element: $("#overlay").get(0),
	});
	
	<%-- 맵 생성 --%>
	map = new ol.Map({
		logo: false,
		controls: ol.control.defaults({attribution: false, zoom: false}),
		interactions: ol.interaction.defaults({pinchRotate: false}),
		target: "map",
		layers: [satellite],
		view: view,
		overlays: [overlay],
	});
	
	<%-- 클릭시 이벤트. --%>
	map.on("pointerup", function (evt) {
		var marker_lonlat = ol.proj.toLonLat(evt.coordinate);
		map.forEachFeatureAtPixel(evt.pixel, function (f) {
			console.log(f.getProperties().no);
			return true;
		});
		console.log(marker_lonlat);
		selectRoadview(marker_lonlat[1], marker_lonlat[0]);
	});
	
	map.on('pointermove', function (evt) {
		var hit = map.forEachFeatureAtPixel(evt.pixel, function (f) {
			$("#overlayContents").html('code: '+f.getProperties().code);
			return true;
		});
		
		if(hit){
			this.getTargetElement().style.cursor = 'pointer';
			overlay.setPosition(evt.coordinate);
			$("#overlay").show();
		}else{
			this.getTargetElement().style.cursor = '';
			overlay.setPosition(undefined);
			$("#overlay").hide();
		}

	});
}


<%-- polygon 스타일 생성 --%>
function polygon_style_function (feature) {
	var feature_id = feature.getProperties().no;
	var feature_name = feature.getProperties().info;
	var img_src = '/image/box_nm4.png';
	var line_color = '#FFFF00';
	var fill_color = ol.color.asArray(line_color).slice();
	var text_center = ol.extent.getCenter(feature.getGeometry().getExtent());
	var line_width = feature.getGeometry().getType() == 'MultiLineString'? 3.5: 0.01;
	fill_color[3] = 0.5;
	
	var icon_image = new ol.style.Icon(({
		anchor: [0.5, 0.5],
		anchorXUnits: 'fraction',
 		anchorYUnits: 'fraction',
		opacity: 0.7,
		src: img_src,
		scale: 1,
	}));
	
	var icon_text = new ol.style.Text({
		font: 'bold 15px Arial', 
		textAlign: 'center',
		text:  feature_name,
		padding: [3, 3, 3, 3],
		fill: new ol.style.Fill({
			color: '#000000',
		}),
		stroke: new ol.style.Stroke({
			color: '#FFFFFF',
			width: 1.0,
		}),
		offsetX: 0,
		offsetY: 0.5,
	});
	
	var polygon_style = new ol.style.Style({
		stroke: new ol.style.Stroke({
			color: line_color,
			width: line_width,
		}),
		fill: new ol.style.Fill({
			color: fill_color,
		}),
	});
	
	var text_style = new ol.style.Style({
		text: icon_text,
		image: icon_image,
		geometry: function(feature) {
			var feature_center = [];
			if( feature.getGeometry().getType() == 'MultiLineString'){
				var line_strings = feature.getGeometry().getLineStrings();
				var mid = Math.floor(line_strings.length/2);
				
				switch(feature_name){
				case '동해선':
					feature_center = ol.proj.fromLonLat([129.09483418183171, 37.475137767693354]);
					break;
				case '서울양양선':
					feature_center = ol.proj.fromLonLat([128.16099629120674, 37.797041650493966]);
					break;
				case '중부내륙선':
					feature_center = ol.proj.fromLonLat([128.10606465058171, 36.703942140495585]);
					break;
				case '당진영덕선':
					feature_center = ol.proj.fromLonLat([129.25962910370671, 36.448082610656726]);
					break;
				case '중앙선':
					feature_center = ol.proj.fromLonLat([128.54551777558171, 36.377351579153995]);
					break;
				case '서해안선':
					feature_center = ol.proj.fromLonLat([126.55699238495671, 36.46575530327803]);
					break;
				case '순천완주선':
					feature_center = ol.proj.fromLonLat([127.55674824433171, 35.18307617212322]);
					break;
				case '호남선':
					feature_center = ol.proj.fromLonLat([126.95250019745673, 35.69328084664633]);
					break;
				case '평택제천선':
					feature_center = ol.proj.fromLonLat([126.93052754120673, 37.055458352408095]);
					break;
				case '경부선':
					feature_center = ol.proj.fromLonLat([128.23790058808171, 36.18250942888827]);
					break;
				case '중부선':
					feature_center = ol.proj.fromLonLat([127.46885761933171, 36.90626168296659]);
					break;
				case '영동선':
					feature_center = ol.proj.fromLonLat([128.27085957245671, 37.52743310413784]);
					break;
				case '제2중부선':
					feature_center = ol.proj.fromLonLat([127.3480080099567, 37.35297294292927]);
					break;
				case '제2중부선':
					feature_center = ol.proj.fromLonLat([127.3480080099567, 37.35297294292927]);
					break;
				case '울산선':
					feature_center = ol.proj.fromLonLat([129.50132832245671, 35.66650865162708]);
					break;
				case '부산외곽선':
					feature_center = ol.proj.fromLonLat([129.85289082245674, 35.31765388561806]);
					break;
				case '남해제2지선':
					feature_center = ol.proj.fromLonLat([129.23765644745674, 34.94927958608356]);
					break;
				case '중앙의지선':
					feature_center = ol.proj.fromLonLat([128.49058613495671, 35.263849609723906]);
					break;
				case '함양울산선':
					feature_center = ol.proj.fromLonLat([128.64439472870674, 35.52357200142434]);
					break;
				case '제2경인선':
					feature_center = ol.proj.fromLonLat([126.32627949433171, 37.43153034762436]);
					break;
				default:
					feature_center = line_strings[mid].getFirstCoordinate();
				}
				
			}else{
				feature_center = text_center;
			}
			
			return new ol.geom.Point(feature_center);
		}
	});
	
	return [text_style, polygon_style];
}


<%-- geo data 가져오기 --%>
function selectData(){
	<%-- 데이터 요청.--%>
	$.ajax({
		type: "POST",
		url: "/water/selectData.do",
		cache : false,
		dataType : "json",
		data : {},
		success : function(data) {
			if(!data || !data.geojson){
				return;
			}
			
			if(polygon_layer){
				map.removeLayer(polygon_layer);
			}
			
			var source_polygon = new ol.source.Vector({
				features: (new ol.format.GeoJSON({featureProjection: 'EPSG:3857'})).readFeatures(JSON.parse(data.geojson)),
		  	});
			
			polygon_layer = new ol.layer.Vector({
			  	source: source_polygon,
			  	style: polygon_style_function,
			});
			
			map.addLayer(polygon_layer);
			
		},
		error : function(e) {
		},
	});
}


<%-- shp zip 파일 업로드 --%>
function selectUpload(){
	var formData = new FormData();
	var file_info = $("#upload")[0].files[0];
	
	if(!file_info){
		alert("선택된 파일이 없습니다.");
		return;
	}
	
	if(file_info.size > 1024*1024*500){
		alert("파일 크기가 500MB 초과시 업로드를 할 수 없습니다.");
		return;
	}
	
	if(!/(.*?)\.(zip)$/.test(file_info.name)){
		alert("파일은 압축파일만 업로드 가능합니다.(.zip)");
		return;
	}
	
	$("#loading").show();
	formData.append("fileInfo", file_info);
	
	var dataState = {
		'init': function(){
			alert('파일을 업로드 시작중에 문제가 발생했습니다.');
		},
		'no_file': function(){
			alert('파일을 전송하는데 실패했습니다.');
		},
		'no_zip': function(){
			alert('파일이 zip파일인지 확인이 필요합니다.');
		},
		'no_data': function(){
			alert('데이터가 없거나, 컬럼명 이상 또는 shp 파일을 찾을수 없습니다.');
		},
		'error': function(){
			alert('예상치 못한 에러가 발생했습니다.');
		},
		'success': function(){
			alert('파일을 업로드 완료했습니다.');
			selectData();
		},
	}; 
	
	$.ajax({
		type: "POST",
		url: '/water/test.do',
		cache : false,
		dataType : "json",
		data : formData,
		processData: false,
		contentType: false,
		success : function(data) {
			if(!data || !data.state){
				alert('서버에 에러가 발생했습니다.');
				return;
			}

			$("#upload").val('');
			$("#loading").hide();
			dataState[data.state]();
			
		},
		error: function(data) {
			alert('파일을 업로드하는 중에 에러가 발생했습니다.');
		},
	});
}


<%-- Roadview 가져오기 --%>
function selectRoadview(lat, lng){
	lat = lat? lat: 33.450701;
	lng = lng? lng: 126.570667;
	
	var roadviewContainer = document.getElementById('roadview'); //로드뷰를 표시할 div
	var roadview = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
	var roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체

	var position = new kakao.maps.LatLng(lat, lng);

	// 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
	roadviewClient.getNearestPanoId(position, 50, function(panoId) {
		if(panoId) roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
	});
}
	
</script>
</head>

<body>
	<div>
		<div>
			<input type="file" id="upload" value="SHP 파일 로드"/>
			<input type="button" id="test" value="파일 업로드" onclick="selectUpload()"/>
			<p id="loading" style="display: none;">파일을 업로드 중입니다.</p>
		</div>
	</div>
	
	<div class="mapContent" id="map" style="width: 98%; height: 800px; padding: 10px; margin: 10px;" ></div>
	<div id="overlay" style="display: none; width: 300px; height: 200px;">
		<div id="overlayContents" style="background-color: white;"></div>
	</div>
	<div id="roadview" style="width:100%;height:300px;"></div>
	
</body>
</html>