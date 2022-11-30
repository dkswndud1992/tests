/**
 *  ajax 구현
 */

$( document ).ready(function(){
	$('.table_selecter').on('change', function(){
        if(this.value !== ""){
            var optVal = $(this).find(":selected").text(); // .val() 1,2,3...
            var selecterId = $(this).attr('id');
            var base = selecterId === 'table1' ? db_data1 : db_data2;
            var selecter = selecterId === 'table1' ? $('#a') : $('#c');

            ajax_db(optVal, base);
            view_table(selecter, base.data_list_hash, base.column_list);
            
            selecter = selecterId === 'table1' ? $('#chart1') : $('#chart2');
            
            var renew_list = new Array();
            
            $.each(base.column_list,function(index, item){
            	renew_list[index] = {name:item, data: new Array()};
            })
            
            $.each(base.data_list_hash, function(index, item){
            	$.each(item, function(key, value){
            		$.each(renew_list, function(index2, item2){
            			
            			if (item2.name === key && item2.data[index] === undefined){
            				item2.data.push(value);
            			}
            		})
            	});
            });
            
            console.log(renew_list);
            
            chartCallback(selecter, optVal, renew_list, base.column_list, "line", "컬럼")

        }
    });
})


var ajax_test = function(table_selecter){
    $.ajax({
        url : "test.ajax",
        dataType : "json",
        success : function(data){
        	table = make_table(data);
            $(table_selecter).html(table);
        },
        error(){alert("ajax실패")}
    });
}

var ajax_db = function(table_name, base){
    $.ajax({
        url : "dbList.ajax",
        dataType : "json",
        type: "POST",
        async: false,
        data: "table_name="+table_name,
        success : function(data){
        	var columnList = new Array();
        	
        	$.each(data.columnList, function(index, item){
        		columnList.push(item.Field);
            });
        	
        	base.table_name = table_name;
        	base.column_list = columnList;
        	base.data_list_hash = data.dataList;
            console.log(base);
        	
        },
        error : function( e ) {
            console.log("err : ");
            console.log(e);
            return false;
        }
    });
}



