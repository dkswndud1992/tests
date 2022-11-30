<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<META http-equiv="Pragma" content="no-cache">
	<META http-equiv="Cache-Control" content="No-Cache">
    <link rel="stylesheet" type="text/css" href="/css/style.css" />
    <script type="text/javascript" src="/js/plugin/jquery/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="/js/component/common.js" ></script>
	
	<script type="text/javascript" src="/js/plugin/pdf/bluebird.min.js"></script>
	<script type="text/javascript" src="/js/plugin/pdf/html2canvas.min.js"></script>
	<script type="text/javascript" src="/js/plugin/pdf/jspdf.min.js"></script>
	
	<script type="text/javascript" src="/js/plugin/excel/jquery.table2excel.min.js"></script>
    
    <!-- TableExport.js -->
    <link rel="stylesheet" type="text/css" href="/js/plugin/excel/TableExport/tableexport.min.css" />
    <script type="text/javascript" src="/js/plugin/excel/TableExport/xlsx.core.min.js"></script>
    <script type="text/javascript" src="/js/plugin/excel/TableExport/FileSaver.min.js"></script>
    <script type="text/javascript" src="/js/plugin/excel/TableExport/tableexport.min.js"></script>
    <script type="text/javascript" src="/js/plugin/excel/TableExport/Blob.min.js"></script>
    
    <!-- test -->
    <script type="text/javascript" src="/js/plugin/excel/jquery.techbytarun.excelexportjs.min.js"></script>
    
    
    <title>met pdf excel test</title>
    <style type="text/css">
    .top,
	.head {
	    caption-side: top;
	}
	
	.bottom {
	    caption-side: bottom;
	}
    </style>
</head>

<body style="background-color:buttonface;">
<script>
$(function() {
	$("input:radio[name=refreshOnOff]").click(function() {
		selectRadioProc();
    });
	
	selectRequestList();
});

//자료 리스트
function selectRequestList() {
	$.ajax({
	    type: "GET",
	    url: "/situationCont/selectMetList.do",
	    cache : false,
		dataType : "json",
	    data: {
	    	"selectPage" : "1",
	    },
		success : function(data) {
			var met_list = data.returnParamList;
			
			// 테이블 리스트 생성
			if( met_list.length == 0 ){
				$("#req_con").html("<tr><td colspan='4'>데이터가 존재하지 않습니다.</td></tr>");
				return null;
			}
			$("#req_con").html("");
			for (var i = 0; i < met_list.length; i++) {
				var req_items = [];
				req_items.push("<td class='file_id'>" + met_list[i].file_id + "</td>");
				req_items.push("<td class='file_name'>" + met_list[i].file_name + "</td>");
				req_items.push("<td class='file_dt'>" + met_list[i].file_dt + "</td>");
				req_items.push("<td class='file_info'>" + met_list[i].file_info + "</td>");
				$("<tr/>", {
					html : req_items 
				}).appendTo("#req_con"); 
			}
		}
	});
}

// 자동 갱신
var timer = "";
function selectRadioProc() {
	var st = $(":input:radio[name=refreshOnOff]:checked").val();
	if(st == 'on') {
		timer = setInterval(function() {
			selectRequestList();
		}, 1000 *60 );
	}  else{ 
		clearInterval(timer);
	}
}

//jspdf 익스플로러 의 경우 바로 다운받아야함./ 처음 로드시 아무것도 안나오는 문제가 있음.
var selectJspdf = function () { 
	html2canvas(document.body, {
		onrendered: function(canvas) {
         	// 캔버스를 이미지로 변환
         	var doc = new jsPDF('p', 'mm');
		    var imgData = canvas.toDataURL('image/png');
		    var imgWidth = 210-20; 
		    var pageHeight = 297;  
		    var imgHeight = canvas.height * imgWidth / canvas.width;
		    var heightLeft = imgHeight;
	        
	        var position = 10;
	        // 첫 페이지 출력
	        doc.addImage(imgData, 'PNG', 10, position, imgWidth, imgHeight-100);
	        heightLeft -= pageHeight;
	        // 한 페이지 이상일 경우 루프 돌면서 출력
	        while (heightLeft >= 20) {
		        position = heightLeft - imgHeight;
		        doc.addPage();
		        doc.addImage(imgData, 'PNG', 10, position+15, imgWidth, imgHeight);
		    	heightLeft -= pageHeight;
	        }
	        var blob = doc.output("blob");
            window.open(URL.createObjectURL(blob));
		}
	});
}

// jqueryExcel(익스플로러 사용가능/ css내보내기 기능이 안됨)
function jqueryExcel() {
	$("#metdataList").table2excel({
		exclude: ".noExl", 
		name: "Excel Document Name", 
		filename: "report" +'.xls', //확장자를 여기서 붙여줘야한다. 
		fileext: ".xls", 
		exclude_img: false, 
		exclude_links: false, 
		exclude_inputs: false,
		preserveColors: true
	}); 
};

// TableExport (엑셀 에러가 안뜸 - css 초기화 하지 말아야함.) 
function TableExportTest() {
	console.log("TableExport");
	// $("#metdataList").tableExport();
	$("table").tableExport();
	/* Defaults 
	TableExport(document.getElementsByTagName("table"), {
	  headers: true,                      // (Boolean), display table headers (th or td elements) in the <thead>, (default: true)
	  footers: true,                      // (Boolean), display table footers (th or td elements) in the <tfoot>, (default: false)
	  formats: ["xlsx", "csv", "txt"],    // (String[]), filetype(s) for the export, (default: ['xlsx', 'csv', 'txt'])
	  filename: "id",                     // (id, String), filename for the downloaded file, (default: 'id')
	  bootstrap: false,                   // (Boolean), style buttons using bootstrap, (default: true)
	  exportButtons: true,                // (Boolean), automatically generate the built-in export buttons for each of the specified formats (default: true)
	  position: "bottom",                 // (top, bottom), position of the caption element relative to table, (default: 'bottom')
	  ignoreRows: null,                   // (Number, Number[]), row indices to exclude from the exported file(s) (default: null)
	  ignoreCols: null,                   // (Number, Number[]), column indices to exclude from the exported file(s) (default: null)
	  trimWhitespace: true,               // (Boolean), remove all leading/trailing newlines, spaces, and tabs from cell text in the exported file(s) (default: false)
	  RTL: false,                         // (Boolean), set direction of the worksheet to right-to-left (default: false)
	  sheetname: "id"                     // (id, String), sheet name for the exported spreadsheet, (default: 'id')
	});
	*/
};

// tableToExcelTest (css 적용 가능. - inline만 가능, 경고 메세지 뜸) 
var tableToExcelTest = (function() {
	 var uri = 'data:application/vnd.ms-excel;base64,'
		   , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
		   , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
		   , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
	 return function(table, name) {
		   if (!table.nodeType) table = document.getElementById(table);
		   var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML};
		   window.location.href = uri + base64(format(template, ctx));
	 }
})();

// test
function test(targetId, SaveFileName) {
        var browser = navigator.userAgent.toLowerCase();
        // ie 구분
        if (-1 != browser.indexOf('trident')) {
            downloadExcelIe(targetId, SaveFileName);
        } else {
            var cssText = '.aaaa {font-size:11px; color:#333333; border:2px solid black; padding:10px 5px 8px 5px; background-color:#F3F5E0;}';
            $("#"+targetId).excelexportjs({
                containerid : targetId,
                datatype :'table',
                cssStyle : cssText
            });
        }
    }
 
    function downloadExcelIe(targetId, SaveFileName) {
 
        // 방법 1이나 2 중 아무거나 되는거 쓰자
 
        // ********************** 
        // 방법 1 


        // 방법 1 

 
        // 스타일 변경 outline 스타일 가져와서 적용 가능
        /*
        var targetObj = document.getElementById(targetId);
        var outputCss = targetObj.currentStyle;
        targetObj.style.backgroundColor = outputCss.getAttribute('background-color');
        targetObj.style.borderStyle = outputCss.getAttribute('border-style');
        targetObj.style.border = outputCss.getAttribute('border');
         */
 
        // 스타일 변경 수동 적용 가능
        var cssText = '<style type="text/css">';
        cssText += '.aaaa {font-size:11px; color:#333333; border:2px solid black; padding:10px 5px 8px 5px; background-color:#F3F5E0;}';
        cssText += '</style>';
 
        var output = document.getElementById(targetId).outerHTML;
 
        if (!SaveFileName) {
            SaveFileName = 'FilteredReport.xls';
        }
 
        var oWin = window.open("about:blank", "_blank");
 
        oWin.document.write(cssText);
        oWin.document.write(output);
        oWin.document.close();
        // success = true, false
        var success = oWin.document.execCommand('SaveAs', false, SaveFileName);
        oWin.close();
 
        // ********************** 
        // 방법 2     


        // 방법 2   

        if (document.all.excelExportFrame == null) // 프레임이 없으면 만들자~!
        {
            var excelFrame = document.createElement("iframe");
            excelFrame.id = "excelExportFrame";
            excelFrame.name = "excelExportFrame";
            excelFrame.position = "absolute";
            excelFrame.style.zIndex = -1;
            excelFrame.style.visibility = "hidden";
            excelFrame.style.top = "-10px";
            excelFrame.style.left = "-10px";
            excelFrame.style.height = "0px";
            excelFrame.style.width = "0px";
            document.body.appendChild(excelFrame); // 아이프레임을 현재 문서에 쑤셔넣고..
        }
        var frmTarget = document.all.excelExportFrame.contentWindow.document; // 해당 아이프레임의 문서에 접근
 
        if (!SaveFileName) {
            SaveFileName = 'test.xls';
        }
 
        frmTarget.open("text/html", "replace");
        frmTarget.write('<html>');
        frmTarget
                .write('<meta http-equiv="Content-Type" content="application/vnd.ms-excel; charset=euc-kr"/>\r\n'); // 별로..
        frmTarget.write('<body onload="saveFile();">');
        frmTarget.write(document.getElementById(targetId).outerHTML); // tag를 포함한 데이터를 쑤셔넣고
        frmTarget.write('<script language="javascript">');
        frmTarget
                .write('function saveFile(){document.execCommand("SaveAs", true, "'
                        + SaveFileName + '");}');
        frmTarget.write('<\/script>');
        frmTarget.write('</body>');
        frmTarget.write('</html>');
        frmTarget.close();
        //frmTarget.charset="UTF-8"; // 자 코드셋을 원하는걸로 맞추시고..
        frmTarget.charset = "euc-kr";
        frmTarget.focus();
 
    }

</script>

<h2 align="center">
	<img alt="met pdf excel test" src="/images/icon.png" width="20px">
	met pdf excel test
</h2>
<!-- container -->
<div id="container">
    <!-- maincontents -->
	<div id="maincontents">
		<div class="data_subm">
	    	<table class="grp" style="padding-right:60px;">
	    		<thead>
		            <tr style="color: blue;">
		                <th >자동갱신</th>
		                <th >pdf</th>
		                <th >excel</th>
		                <th >test</th>
		            </tr>
		        </thead>
		        	
		        <tbody >
		        	<tr>
		                <td >
			                <label for="refreshOn">자동갱신 On</label>
							<input name="refreshOnOff" id="refreshOn"  type="radio" value="on" />
							<label for="refreshOff">자동갱신 Off</label>
   							<input name="refreshOnOff" id="refreshOff" type="radio" value="off" checked="checked" />
						</td>
		                <td >
		                	<input type="button" id="jspdf" value="&nbsp;jspdf로 보기&nbsp;" onclick="selectJspdf();" />
						</td>
		                <td >
							<input type="button" id="jqueryExcel" value="&nbsp;jqueryExcel로 다운로드&nbsp;" onclick="jqueryExcel();" />
			   				&nbsp;
			   				<input type="button" id="tableExport" value="&nbsp;TableExport로 다운로드&nbsp;" onclick="TableExportTest();" />
			   				&nbsp;
			   				<input type="button" id="tableToExcelTest" value="&nbsp;tableToExcelTest로 다운로드&nbsp;" onclick="tableToExcelTest('metdataList','test');" />
						</td>
		                <td >
							<input type="button" id="test" value="test" onclick="test('metdataList','testexcel');" />
						</td>
		            </tr>
				</tbody>
   				 	</table>
		</div>
		
		<div class="list" >
		    <table id="metdataList" data-tableName="Test Table 1">
				<colgroup>
					<col width="150" />
					<col width="200" />
					<col width="*" />
					<col width="150" />
				</colgroup>
		        <thead>
		            <tr style="color: red;">
		                <th scope="col">No.</th>
		                <th scope="col">file_name</th>
		                <th scope="col">file_dt</th>
		                <th scope="col">file_info</th>
		            </tr>
		        </thead>
		        
		        <tbody id="req_con">
				</tbody>
		    </table>
		</div>
	</div>

</div>

</body>
</html>
