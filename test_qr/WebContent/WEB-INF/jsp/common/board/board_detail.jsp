<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:include page="/WEB-INF/jsp/include/include_header.jsp"/>

<c:if test="${!empty sessionScope.userId}">
	<script type="text/javascript">
		function deleteBoard(){
			if(!confirm('게시물을 삭제 하시겠습니까?')) return;

			$.ajax({
				type: "POST",
				url: "/boardCont/deleteBoard.do",
				cache : false,
				dataType : "json",
				data : {
					"nttCode" : "${nttCode}",
				},
				success : function(data) {
					location.replace("/boardMain/list.do");
				},
				error: function(data) {
					location.replace("/userMain/login.do");
				},
			});
		}
	</script>
</c:if>

<c:choose>
	<c:when test = "${result.fileCode eq -999}">
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			function selectFile(){
				var $form = $("<form></form>");
				$form.attr("action", "/boardCont/selectFile.do");
				$form.attr("method", "post");
				$form.appendTo("body");

				$form.append("<input type='hidden' value='${result.fileCode}' name='index'>");

				$form.submit();
				$form.remove();
			}
		</script>
	</c:otherwise>
</c:choose>

<div id="container" data-value="board">
	<div>
	
		<h2>
			게시판
		</h2>
		
		<div>
			<div>
				<p>${result.nttSj}</p>
			</div>
			
			<div>
				<table>
					<tr>
						<th>등록자명</th>
						<td>${result.userNm}</td>
						<th>등록일자</th>
						<td>${result.registDt}</td>
					</tr>
				</table>
			</div>
			
			<div>
				${result.nttCn}
			</div>
			
			<div>
				<p>첨부파일</p>
				<c:choose>
					<c:when test = "${result.fileCode eq '-999'}">
			            <a>없음</a>
			         </c:when>
			         <c:otherwise>
			            <a href="javascript:selectFile();">${result.realFileNm}</a>
					</c:otherwise>
				</c:choose>
			</div>
			
			<div>
				<c:if test="${!empty sessionScope.userId}">
					<span><a href="/boardMain/update.do?index=${nttCode}">게시물 수정</a></span>
					<span><a href="javascript:deleteBoard();">게시물 삭제</a></span>
				</c:if>
				<span><a href="/boardMain/list.do">목록</a></span>
			</div>
			
		</div>
	</div>
</div>

<jsp:include page="/WEB-INF/jsp/include/include_footer.jsp"/>
