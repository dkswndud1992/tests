/**
 *  하이차트 구현
 * 
 */



var chartCallback = function(chart_selecter, title, series_data, category, type = 'line', unit_str=' ') {
	
	var series_arr = [] // 값
	var legend_obj = {} // 범례
	var tooltip_obj = {} // hover시 나오는 tooltip
	var xAxis_obj = {categories: []}  //x축	
	var yAxis_obj = {}  //y축
	var title_obj = {} //제목
	var subtitle_obj = {} // 부제목
	var credits_obj = {} // 로고
	var chart_obj = {} // 차트종류
	var responsive_obj = {} //차트옵션
	
	
	////////////////////
	
	
	chart_obj = {
		type: type,
        // renderTo: 'chart', // 다시 그려질 영역 설정(id = 'chart')
        zoomType: "x", // X축이 줌인이 가능하게 설정
        panning: true,
        panKey: "shift"
   }
	
	responsive_obj = {
        rules: [{
            condition: {
                maxWidth: 500
            },
            chartOptions: {
                legend: {
                    align: 'center',
                    verticalAlign: 'bottom',
                    layout: 'horizontal'
                }
            }
        }]
   }
   
	credits_obj = {
		enabled: false,
        text: "logo", // 로고 표시
        href: "https://www.naver.com" // 로고 클릭시 URL
   }
   
   title_obj = {
	    text: title,
		x: -20 //center
   }
   
	subtitle_obj = {
      text: '부제목',
         x: -20
   }
   
   xAxis_obj.categories = category  //x축	
   
   yAxis_obj = {  //y축
	   // min: 0, // 0이상의 값만 표기
       // allowDecimals: false, // 정수로만 표기
        title: {
           	text: '단위 ('+ unit_str +')'
        },
        labels: { //라벨
            formatter: function() {
                return this.value + ' ' + unit_str;
            }
        },
        plotLines: [{   //선
            value: 0,
            width: 1,
            color: '#808080'
        }]
    }
	
	tooltip_obj = {// hover시 나오는 tooltip
        // shared: true, // 하나의 영역을 공유
        valueSuffix: ' '+unit_str,
        /*
        pointFormatter: function() {
            if (this.y >= 0) {
                  // 표시되는 tooltip을 마음대로 설정
                  return (
                    '<span style="color:' +
                    this.series.color +
                    ';">●</span> ' +
                    this.series.name +
                    ": " +
                    "<b>" +
                    this.y +
                    "</b><br/>"
                  )
             }
         }
         */
    }
	
	legend_obj = {  //범례
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle',
        borderWidth: 0
    }
	
	// TODO : 결측치일 경우 전시간에서 가져옴.
	series_arr = series_data  //값
	
	
	///////////////////
	
	
	var option = {
		series: series_arr,
		legend: legend_obj,
		tooltip: tooltip_obj,
		yAxis: yAxis_obj,
		xAxis: xAxis_obj,
		title: title_obj,
		// subtitle: subtitle_obj,
		credits: credits_obj,
		chart: chart_obj,
		responsive: responsive_obj
	}
	chart_selecter.highcharts(option);
}

// 차트 생성시 제목, 데이터, x, y, tooltip 받기 / 디폴트로 로고와 범례, 반응형 설정. 


	/////////////샘플 데이터//////////////////////////////////
	/*
	 
	series_data = [{   //값
        name: 'Tokyo',
        data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5]
    }, {
        name: 'New York',
        data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0]
    },{
        name: 'New York2',
        data: [1, 2, 7, 3, 4, 10]
    }, {
        name: 'Berlin',
        data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0]
    }, {
        name: 'London',
        data: [3.9, 4.2 , 8.4 , 17.0 , 11.9, 15.2]
    }];
	
	x_axis = ['가로1', '가로2', '가로3', '가로4', '가로5', '가로6']
	
	 */
	///////////////////////////////////////////////////
	

