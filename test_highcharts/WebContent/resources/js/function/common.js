/**
 * 기본 js
 */

$(window).load(function(){
	show_btn($("#btn_charts_db_view"),$("#view_db_charts"));
	
})


// db 데이터 담아줄때.
class db_data {
	constructor(table_name = " ", column_list = new Array(), data_list_hash = new Array()) {
	    this.table_name = table_name;
	    this.column_list = column_list;
	    this.data_list_hash = data_list_hash;
	}
}

var db_data1 = new db_data();
var db_data2 = new db_data();


// 메뉴버튼.
var show_btn = function(btn, view) {
	// 초기화
	$(".manu_btn").removeClass("active");
	$("[id^='view']").hide();
	
	//적용
	btn.addClass("active");
	view.show();
	console.log("click!!");
}

// 테이블로 보기
var view_table = function(table_selecter, hash_dataList, columnList){
	var table = make_table(hash_dataList, columnList);
	
    $(table_selecter).html(table);
}


// table 만들기.
var make_table = function(hash_list, column_list = " ") {
	
	b_key = Object.keys(hash_list[0]);
	
	if (!compareArrays(b_key, column_list) && column_list !== " "){
		return "컬럼 확인 필요.";
	}
	
	var keys = column_list === " " ? b_key :column_list;
	var show = "";
	
	show += "<tr>";
	$.each(keys, function(index, item){
        show += "<td>"+item+"</td>";
    });
	show += "</tr>";
	
    $.each(hash_list,function(index, item){
    	show += "<tr>";
    	$.each(keys, function(index2, item2){
    		item[item2] !== undefined ? show += "<td>"+item[item2]+"</td>": show +="<td>error : find keys</td>";
    	});
    	show += "</tr>";
    });
	
	return show;
}


//////////////////////////////////////////////////////////
// 외부 유틸
//////////////////////////////////////////////////////////

//배열 비교
function compareArrays(array1, array2) { 
	 var i, isA1, isA2;
	 isA1 = Array.isArray(array1);
	 isA2 = Array.isArray(array2);
	  
	 if (isA1 !== isA2) { // is one an array and the other not?
		  return false;      // yes then can not be the same
	  }
	 if (! (isA1 && isA2)) {      // Are both not arrays 
		  return array1 === array2;  // return strict equality
	  }
	 if (array1.length !== array2.length) { // if lengths differ then can not be the same
		  return false;
	 }
	  // iterate arrays and compare them
	 for (i = 0; i < array1.length; i += 1) {
		  if (!compareArrays(array1[i], array2[i]) && array2.indexOf(array1[i]) === -1) { // Do items compare recursively 순서가 다른것을 위해 추가
			   return false;
		  }           
	 }
	 return true; // must be equal
}


function selectNow () {
	var date_now = new Date();
	var now_year = date_now.getFullYear();
	var now_month = date_now.getMonth() + 1;
	var now_day = date_now.getDate();
	var now_hour = date_now.getHours();
	var now_minute = date_now.getMinutes();
	var result_param = "";
	
	now_minute = now_minute < 10 ? "0" + now_minute : now_minute;
	now_hour = now_hour < 10 ? "0" + now_hour : now_hour;
	now_day = now_day < 10 ? "0" + now_day : now_day;
	now_month = now_month < 10 ? "0" + now_month : now_month;
	
	result_param = now_year+"-"+now_month+"-"+now_day +" "+now_hour+":"+now_minute;
	return result_param;
}


function selectNowType10Min () {
	var date_now = new Date();
	var now_year = date_now.getFullYear();
	var now_month = date_now.getMonth() + 1;
	var now_day = date_now.getDate();
	var now_hour = date_now.getHours();
	var now_minute = date_now.getMinutes();
	var result_param = "";
	
	now_minute = now_minute < 10 ? "00" : Math.floor(now_minute/10)*10;
	now_hour = now_hour < 10 ? "0" + now_hour : now_hour;
	now_day = now_day < 10 ? "0" + now_day : now_day;
	now_month = now_month < 10 ? "0" + now_month : now_month;
	
	result_param = now_year+""+now_month+""+now_day +""+now_hour+""+now_minute;
	return result_param;
}


function selectNowType1Hour () {
	var date_now = new Date();
	var now_year = date_now.getFullYear();
	var now_month = date_now.getMonth() + 1;
	var now_day = date_now.getDate();
	var now_hour = date_now.getHours();
	var result_param = "";
	
	now_hour = now_hour < 10 ? "0" + now_hour : now_hour;
	now_day = now_day < 10 ? "0" + now_day : now_day;
	now_month = now_month < 10 ? "0" + now_month : now_month;
	
	result_param = now_year+""+now_month+""+now_day +""+now_hour;
	return result_param;
}
