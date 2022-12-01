/**
 * test
 */
window.onload = function() {
	selectFutureImg();
}

// 날짜 들어가면 path 반환
function updateFutureImg (selectDateStr, preDateStr) {
	var http = new XMLHttpRequest();
	
	var y = selectDateStr.substr(0, 4);
    var M = selectDateStr.substr(4, 2);
    var d = selectDateStr.substr(6, 2);
    var h = selectDateStr.substr(8, 2);
    var m = selectDateStr.substr(10, 2);
    
    var strToDate = new Date(y,M-1,d,h,m);
    
    var datePath = y+"/"+M+"/"+d+"/";
	var rootPath = location.href+"ncl_data_image/radar_qpf_10m_map/";
	var imgName = "RADAR_QPF_10m_MAP_PRCP_"+selectDateStr+"_"+preDateStr+"_SEOUL.png";
	
	var searchImg = rootPath+datePath+imgName;
	var defaultImg = searchImg;
	
    http.open('HEAD', searchImg, false);
    http.send();
    var imgBool =  http.status!=404;
    
    if(imgBool == true){
    	return searchImg;
    }
	
    for(i=1;i<=18;i++){
    	strToDate.setMinutes(strToDate.getMinutes() - 10);
        
        y = strToDate.getFullYear();
        M = strToDate.getMonth()+1;
        d = strToDate.getDate();
        h = strToDate.getHours();
        m = strToDate.getMinutes();
        
        M = M<10 ? "0"+M: M;
        d = d<10 ? "0"+d: d;
        h = h<10 ? "0"+h: h;
        m = m<10 ? "0"+m: m;
        selectDateStr = y+""+M+""+d+""+h+""+m;
        
        datePath = y+"/"+M+"/"+d+"/";
    	imgName = "RADAR_QPF_10m_MAP_PRCP_"+selectDateStr+"_"+preDateStr+"_SEOUL.png";
    	
    	searchImg = rootPath+datePath+imgName;
    	
        http.open('HEAD', searchImg, false);
        http.send();
        var imgBool =  http.status!=404;
        if(imgBool == true){
        	return searchImg;
        }
    }
	return defaultImg;
}

// 미래 이미지 표출 3개
function selectFutureImg () {
	var searchDate = $("#searchDate");
	var selectDateStr1 = searchDate.val();
	var selectDateStr2 = "";
	var y = selectDateStr1.substr(0, 4);
    var M = selectDateStr1.substr(4, 2);
    var d = selectDateStr1.substr(6, 2);
    var h = selectDateStr1.substr(8, 2);
    var m = selectDateStr1.substr(10, 2);
    var strToDate = new Date(y,M-1,d,h,m);
    
	for (var i = 1; i <= 3; i++) {
		strToDate.setMinutes(strToDate.getMinutes() + 10);
        
        y = strToDate.getFullYear();
        M = strToDate.getMonth()+1;
        d = strToDate.getDate();
        h = strToDate.getHours();
        m = strToDate.getMinutes();
        
        M = M<10 ? "0"+M: M;
        d = d<10 ? "0"+d: d;
        h = h<10 ? "0"+h: h;
        m = m<10 ? "0"+m: m;
        selectDateStr2 = y+""+M+""+d+""+h+""+m;
		
		var ImgUrl = updateFutureImg(selectDateStr1, selectDateStr2);
		$("#img"+i).attr("src", ImgUrl);
	}
}

