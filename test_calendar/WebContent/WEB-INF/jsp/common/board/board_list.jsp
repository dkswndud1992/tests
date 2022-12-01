<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:include page="/WEB-INF/jsp/include/include_header.jsp"/>

<script type="text/javascript">
<%-- 초기 실행 함수. --%>
$(function() {
	selectData(1,true);
	
	<%-- 검색 엔터 이벤트 --%>
	$("#search_value").keyup(function(e){
		if(e.keyCode == 13) selectData(1,true); 
	});

});

<%-- 파일 다운로드 --%>
function selectFile(param){
	var $form = $("<form></form>");
	$form.attr("action", "/boardCont/selectFile.do");
	$form.attr("method", "post");
	$form.appendTo("body");
	
	$form.append("<input type='hidden' value='"+param+"' name='index'>");
	
	$form.submit();
	$form.remove();
}


<%-- 게시물 리스트. --%>
function selectData(page_num, is_search){
	if(is_search){
		$("#search_value").data("value", $("#search_value").val());
		$("#search_type").data("value", $("#search_type").val());
	}
	
	var search_value = $("#search_value").data("value");
	var search_type = $("#search_type").data("value");
	
	selectBoardListCnt(page_num);
	
	<%-- 데이터 요청. --%>
	$.ajax({
	    type: "POST",
	    url: "/boardCont/selectBoardList.do",
	    cache : false,
		dataType : "json",
		data : {
			"pageNum" : page_num,
			"searchType" : search_type,
			"searchValue" : search_value,
		},
		success : function(data) {
			<%-- 초기화. --%>
			$("#board_list").html("");
			
			if(data.boardList.length){
				$.each(data.boardList, function(index, item){
					<%-- 리스트 데이터 html 표출. --%>
					var board_list_html = "";
					var download_text = "";
					
					if(item.fileCode == "-999"){
						download_text = "자료없음";
					}else{
						download_text = "<a href='javascript:selectFile(\""+item.fileCode+"\");'>파일 다운로드</a>"
					}
					
					board_list_html += "<tr>";
					board_list_html += "<td>"+item.nttNo+"</td>";
					board_list_html += "<td class='txtlf'><a href='/boardMain/detail.do?index="+item.nttCode+"'>"+item.nttSj+"</a></td>";
					board_list_html += "<td>"+download_text+"</td>";
					board_list_html += "<td>"+item.userNm+"</td>";
					board_list_html += "<td>"+item.registDt+"</td>";
					board_list_html += "</tr>";
					
					$("#board_list").append(board_list_html);
				});
				
			}else{
				<%-- 데이터 없을시 표출. --%>
				$("#board_list").append("<tr><td colspan='5' align='center'>데이터가 없습니다.</td></tr>");
			}
			
		},
		error: function(jqXHR, textStatus, errorThrown){
			location.href="/userMain/login.do";
		},
	});
}


<%-- 게시물 리스트 개수. --%>
function selectBoardListCnt(page_num){
	var search_value = $("#search_value").data("value");
	var search_type = $("#search_type").data("value");
	
	<%-- 데이터 요청. --%>
	$.ajax({
	    type: "POST",
	    url: "/boardCont/selectBoardListCnt.do",
	    cache : false,
		dataType : "json",
		data : {
			"searchType" : search_type,
			"searchValue" : search_value,
		},
		success : function(data) {
			<%-- 페이징 표출 로직. --%>
			var limit_num = parseInt(data.limitNum);
			var page_limit = parseInt(data.pageLimit);
			var remain = data.boardListCnt%limit_num;
			var page_length = parseInt(data.boardListCnt/limit_num);
			page_length += remain? 1: 0;
			page_num = page_num? page_num: 1;
			var page_start_num = page_num%page_limit == 0? page_num-page_limit+1: page_num-(page_num%page_limit)+1;
			var page_end_num = page_length > page_start_num+page_limit-1? page_start_num+page_limit-1: page_length;
			
			<%-- 초기화. --%>
			$("#page_num").html("");
			
			<%-- 페이지 한개거나 없을경우 안보이게. --%>
			if(page_length <= 1){
				$("#paging").hide();
				return;
			}else{
				$("#paging").show();
			}
			
			<%-- 페이징 엘레먼트 생성. --%>
			for(i = page_start_num; page_end_num >= i; i++){
				$("#page_num").append("<li><a id='page_"+i+"' href='javascript:selectData("+i+");'>"+i+"</a></li>");
			}
			
			<%-- 선택 페이지 하이라이트. --%>
			$("#page_"+page_num).addClass("select");
			
			<%-- 좌측 페이징 --%>
			if(page_start_num <= 1){
				$("#page_left").hide();
				$("#first_page").hide();
			}else{
				$("#page_left").show();
				$("#first_page").show();
				$("#page_left").attr("onclick", "selectData("+(page_start_num-1)+")");
				$("#first_page").attr("onclick", "selectData(1)");
			}
			
			<%-- 우측 페이징 --%>
			if(page_end_num >= page_length){
				$("#page_right").hide();
				$("#last_page").hide();
			}else{
				$("#page_right").show();
				$("#last_page").show();
				$("#page_right").attr("onclick", "selectData("+(page_end_num+1)+")");
				$("#last_page").attr("onclick", "selectData("+page_length+")");
			}
			
		},
	});
}

</script>

<div id="container" data-value="board">
	<div>
		<h2>
			게시판
		</h2>
		
		<div>
			<select id="search_type">
				<option value="1">제목</option>
				<option value="2">내용</option>
			</select>
			<input type="text" id="search_value"/>
			<span><a href="javascript:selectData(1, true);">검색</a></span>
		</div>
		
		<div class="list">
			<table>
				<col width="100" />
		        <col width="*" />
		        <col width="110" />
		        <col width="110" />
		        <col width="110" />
				
				<thead>
					<tr>
						<th scope="col">번호</th>
						<th scope="col">제목</th>
						<th scope="col">파일</th>
						<th scope="col">작성자</th>
						<th scope="col">작성일</th>
					</tr>
				</thead>
				
				<tbody id="board_list"></tbody>
			</table>
		</div>
		
		<div>
			<div id="paging" style="display: none;">
				<button class="prev2" id="first_page"><span>처음으로</span></button>
				<button class="prev1" id="page_left"><span>이전목록</span></button>
				<ul id="page_num"></ul>
				<button class="next1" id="page_right"><span>다음목록</span></button>
				<button class="next2" id="last_page"><span>마지막으로</span></button>
			</div>
		</div>
		
		<c:if test="${!empty sessionScope.userId}">
			<div><span><a href="/boardMain/write.do">글쓰기</a></span></div>
		</c:if>
	</div>
</div>
<jsp:include page="/WEB-INF/jsp/include/include_footer.jsp"/>

