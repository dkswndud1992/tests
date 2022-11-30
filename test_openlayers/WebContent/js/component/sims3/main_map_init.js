// Product by JooyoungAhn. [2022.04.19]
// Version: v0.0.1
;(function (root, factory) {
	if(!$){
		console.log("main_map.js를 jquery.js 뒤에 정의해 주세요.");
		
	}else if(!ol){
		console.log("main_map.js를 ol.js 뒤에 정의해 주세요.");
		
	}else{
		root.main_map = $("script[src*='main_map.js']").prop("defer") ?
			// 알아서 정의.
			factory().defer():
			
			// 따로 정의.
			factory().not_defer();
	}
	
}(this, function () {
	var return_factory = {
		defer: function(){},
		not_defer: function(){},
		function: {},
	};
	
	// [레이어정보] 한강공원 리스트
	function selectParkLonlatList(){
		return [
			{name:"양화", lon:126.902131, lat:37.5384666},
			{name:"강서", lon:126.8168938, lat:37.5860901},
			{name:"난지", lon:126.8851795, lat:37.5627454},
			{name:"여의도", lon:126.9189299, lat:37.5337156},
			{name:"망원", lon:126.8994546, lat:37.5525412},
			{name:"반포", lon:126.9951401, lat:37.5098203},
			{name:"이촌", lon:126.9705874, lat:37.5171951},
			{name:"잠원", lon:127.0101485, lat:37.5197966},
			{name:"뚝섬", lon:127.079, lat:37.5277157},
			{name:"잠실", lon:127.0823793, lat:37.517773},
			{name:"광나루", lon:127.1222839, lat:37.5500514},
		];
	}
	
	// [레이어정보] 한강 시작 팔당댐
	function selectDamLonlatList(){
		return [
			{name:"팔당댐", lon:127.277571184, lat:37.524422112},
		];
	}

	// [레이어정보] 한강 끝 강화대교
	function selectLastBridgeLonlatList(){
		return [
			{name:"강화대교", lon:126.522847219, lat:37.733989422},
		];
	}
	
	
	// [레이어] 마커 레이어 생성기
	function selectVectorIconLayer(lonlat_list){
		var icon_features=[];
		
		lonlat_list.forEach(function(item){
			var icon_feature = new ol.Feature({
				geometry: new ol.geom.Point(ol.proj.transform([item.lon, item.lat], 'EPSG:4326', 'EPSG:3857')),
				name: item.name,
			});
			
			var icon_image = new ol.style.Icon(({
				anchor: [0.5, 0.5],
				anchorXUnits: 'fraction',
		 		anchorYUnits: 'fraction',
				opacity: 0.7,
				src: '/image/icon.png',
				scale: 0.05,
			}));
			
			var icon_text = new ol.style.Text({
				font: "bold 15px Verdana", 
				textAlign: "center",
				text:  item.name,
				fill: new ol.style.Fill({
					color: "#000000",
				}),
				stroke: new ol.style.Stroke({
					color: "#FFFFFF",
					width: 4.0,
				}),
				offsetX: 0,
				offsetY: -20,
			});
			
			icon_feature.setStyle(new ol.style.Style({
				image: icon_image,
				text: icon_text,
			}));
			
			icon_features.push(icon_feature);
		});
		
		var vector_source = new ol.source.Vector({
			features: icon_features,
		});
	
		var vector_layer = new ol.layer.Vector({
		  	source: vector_source,
		});
		
		return vector_layer;
	}
	
	// [레이어] 타일 레이어 생성기
	function selectWmsTileLayer(){
		var dt = '210501021600';
		var st_yy = dt.substr(0, 4);
		var st_mm = dt.substr(4, 2);
		var st_dd = dt.substr(6, 2);
		var st_hh = dt.substr(8, 2);
		var mm = dt.substr(10, 2);
		var st_dt = st_yy + "" + st_mm + "" + st_dd + "" + st_hh + "" + mm;
		
		var wms_tile_layer  = new ol.layer.Tile({
	        source: new ol.source.TileWMS({
	          url: 'http://geornd.hecorea.co.kr/sims2/wms',
	          params: {
	        	  'FORMAT': 'image/png', 
	              'VERSION': '1.1.1',
	              tiled: true,
	              STYLES: '',
	              LAYERS: 'sims2:scedist_lv01',
	              TIME: st_yy + '-' + st_mm + '-' + st_dd + 'T' + st_hh + ':' + mm + ':00.0Z',
	              tilesOrigin: 191598.8281 + "," + 462169.5625
	          }
	       })
	    });
	    
	    return wms_tile_layer;
	}
	
	// [레이어] vworld 지도 레이어 베이스생성. 
	function selectVworldHybridLayer(){
		return new ol.layer.Tile({
		    name : "hybrid",
		    visible: true,
			extent: [-20037508.3428, -20037508.3428, 20037508.3428, 20037508.3428],
		    source: new ol.source.XYZ({
		    	url: "https://xdworld.vworld.kr/2d/Hybrid/service/{z}/{x}/{y}.png"
		    })
		});
	}
	
	// [레이어] vworld 지도 레이어 베이스생성. 
	function selectVworldSatelliteLayer(){
		return new ol.layer.Tile({
			name : "satellite",
			visible: true,
			extent: [-20037508.3428, -20037508.3428, 20037508.3428, 20037508.3428],
			source: new ol.source.XYZ({
				url: "https://xdworld.vworld.kr/2d/Satellite/service/{z}/{x}/{y}.jpeg"
			}),
		});
	}
	
	
	// [뷰] 뷰 생성. 
	function selectMapView(){
		return new ol.View({
	    	projection: ol.proj.get('EPSG:2097'),
	    	center: ol.proj.fromLonLat([126.911, 37.599]), 
	      	zoom: 11.5,
	      	maxZoom :17,
	      	minZoom: 7
	    });
	}
	
	
	// [오버레이] 맵 오버레이 생성
	function selectMapOverlay(){
		return new ol.Overlay({element: $("<div></div>").css("background-color", "white").get(0)});
	}
	
	
	// [맵] 맵 생성. 
	function selectMap(){
		return new ol.Map({
			target: 'map',
	        logo: false,
		});
	}
	
	
	// [이벤트] 클릭시 이벤트 발생. 
	function selectClickEvent(target_map){
		target_map.on('click', function (evt) {
			var feature = target_map.forEachFeatureAtPixel(evt.pixel,
				function (feature) {
					return feature;
				}
		    );
		        
		    if (feature) {
		        alert(feature.get('name'));
		    }
		});
	}
	
	// [이벤트] 마커에 마우스 호버 이벤트. 
	function selectPointermoveEvent(target_map, target_overlay){
		target_map.on('pointermove', function (evt) {
			if (!evt.dragging) {
				var pixel = target_map.getEventPixel(evt.originalEvent);
				var hit = target_map.hasFeatureAtPixel(pixel);
				this.getTargetElement().style.cursor = hit ? 'pointer' : '';
		    }
		    
		    // 호버된 레이어와 마커 가져오기. 
		    var target_pixel = target_map.getEventPixel(evt.originalEvent);
		    var target_feature = target_map.forEachFeatureAtPixel(target_pixel, function(feature, layer) {
		    	return feature;
		    });
		    
		    if(target_feature){
		    	target_overlay.setPosition(evt.coordinate);
		    	
			}else{
				target_overlay.setPosition(undefined);
			}
		    
		});
	}
	
	// [이벤트] map화면 밖 호버시 오버레이안보이게 
	function selectMouseleaveEvent(target_map, target_overlay){
		target_map.on('mouseleave', function(evt){
			$(this).css("cursor", "default");
			target_overlay.setPosition(undefined);
		});
	}
	
	
	// [시작] 초기 시작 함수 정리.
	function selectInitFunction (){
		var map_view = selectMapView();
		var map_overlay = selectMapOverlay();
		
		var park_lonlat_list = selectParkLonlatList();
		var dam_lonlat_list = selectDamLonlatList();
		var last_bridge_lonlat_list = selectLastBridgeLonlatList();
		
		var vector_park_layer = selectVectorIconLayer(park_lonlat_list);
		var vector_dam_layer = selectVectorIconLayer(dam_lonlat_list);
		var vector_last_bridge_layer = selectVectorIconLayer(last_bridge_lonlat_list);
		
		var test_wms_tile_layer = selectWmsTileLayer();
		
		var vworld_hybrid_layer = selectVworldHybridLayer();
		var vworld_satellite_layer = selectVworldSatelliteLayer();
		
		var map = selectMap();
		
		map.setView(map_view);
		map.addOverlay(map_overlay);
		
		map.addLayer(vworld_satellite_layer);
		map.addLayer(vworld_hybrid_layer);
		
		map.addLayer(test_wms_tile_layer);
		
		map.addLayer(vector_park_layer);
		map.addLayer(vector_dam_layer);
		map.addLayer(vector_last_bridge_layer);
		
		selectClickEvent(map);
		selectPointermoveEvent(map, map_overlay);
		selectMouseleaveEvent(map, map_overlay);
	}
	
	
	// 내보낼 함수 정리.
	return_factory.function.selectMapView = selectMapView;
	return_factory.function.selectMapOverlay = selectMapOverlay;
	return_factory.function.selectParkLonlatList = selectParkLonlatList;
	return_factory.function.selectDamLonlatList = selectDamLonlatList;
	return_factory.function.selectLastBridgeLonlatList = selectLastBridgeLonlatList;
	return_factory.function.selectVectorIconLayer = selectVectorIconLayer;
	return_factory.function.selectWmsTileLayer = selectWmsTileLayer;
	return_factory.function.selectVworldHybridLayer = selectVworldHybridLayer;
	return_factory.function.selectVworldSatelliteLayer = selectVworldSatelliteLayer;
	return_factory.function.selectMap = selectMap;
	return_factory.function.selectInitFunction = selectInitFunction;
	return_factory.function.selectClickEvent = selectClickEvent;
	return_factory.function.selectPointermoveEvent = selectPointermoveEvent;
	return_factory.function.selectMouseleaveEvent = selectMouseleaveEvent;
	
	
	// 자동 생성
	return_factory.defer = function(){
		selectInitFunction();
		return return_factory.function;
	}
	
	// 사용자 정의 생성
	return_factory.not_defer = function(){
		return return_factory.function;
	}
	
	
	return return_factory;
}));

