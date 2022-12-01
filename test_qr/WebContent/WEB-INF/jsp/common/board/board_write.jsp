<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:include page="/WEB-INF/jsp/include/include_header.jsp"/>

<script type="text/javascript">
var remove_before = "false";

<%-- 제목 온 포커스 --%>
$( document ).ready(function() {
	$("#ntt_sj").focus();
	
});

<c:if test = "${!empty nttCode}">
	<%-- 파일제거. --%>
	function deleteFile () {
		if(!confirm('기존 등록된 파일을 제거하시겠습니까?')) return;
		$("#file_nm").before("<input type='file' name='file' id='file_info' />");
		$("#file_nm").remove();
		remove_before = "true";
	}
</c:if>

<%-- 데이터 저장. --%>
function updateData () {
	var formData = new FormData();
   	
	<c:choose>
		<c:when test = "${!empty nttCode}">
			var url = "/boardCont/updateBoard.do";
			var ntt_code = "${nttCode}";
			var file_code = "${result.fileCode}";
			formData.append("nttCode", ntt_code);
			formData.append("fileCode", file_code);
		</c:when>
		<c:otherwise>
			 var url = "/boardCont/insertBoard.do";
		</c:otherwise>
	</c:choose>
	
	var ntt_sj = $("#ntt_sj").val();
	var ntt_cn = $("#ntt_cn").val();
	var file_info = {type:"application/pdf", size: 0};
	
	if($.trim(ntt_sj)==""){
		alert("제목을 입력해주세요.");
		$("#ntt_sj").focus();
		return;
	}
	
	if($.trim(ntt_cn)==""){
		alert("내용을 입력해주세요.");
		$("#ntt_cn").focus();
		return;
	}
	
	file_info = $("#file_info")[0].files[0];
	
	if(!$("#file_nm").length && file_info && file_info.size > 1024*1024*500){
		alert("파일 크기가 500MB 초과시 업로드를 할 수 없습니다.");
		return;
	}
	
	if(!$("#file_nm").length && file_info && !/(.*?)\.(jpg|jpeg|png|gif|bmp|pdf)$/.test(file_info.name)){
		alert("파일은 이미지 및 PDF파일만 업로드 가능합니다.(jpg, jpeg, png, gif, bmp, pdf)");
		return;
	}
	
	if(!confirm("게시물을 ${!empty nttCode? '수정': '작성'} 하시겠습니까?")) return;
	
	formData.append("nttSj", ntt_sj);
	formData.append("nttCn", ntt_cn);
	formData.append("fileInfo", file_info);
	formData.append("removeBefore", remove_before);
	
	$.ajax({
		type: "POST",
 	    url: url,
 	    cache : false,
 		dataType : "json",
 		data : formData,
 		processData: false,
 		contentType: false,
 		success : function(data) {
 			location.replace("/boardMain/detail.do?index="+data.nttCode);
 			
 		},
 		error: function(data) {
			location.replace("/userMain/login.do");
 		},
	});
}

</script>

<div id="container" data-value="board">
	<form id="upload_form" name="upload_form" method="post" enctype="multipart/form-data">
		<div>
		
			<h2>
				게시판
			</h2>
			
			<div>
				<div>
					<input type="text" id="ntt_sj" placeholder="제목을 입력하세요" value="${!empty result.nttSj? result.nttSj: ''}" maxlength="100"/>
				</div>
				
				<div>
					<textarea id="ntt_cn">${!empty result.nttCn? result.nttCn: ''}</textarea>
				</div>
				
				<div>
					<p>첨부파일 업로드</p>
					<c:choose>
						<c:when test = "${empty result.fileCode || result.fileCode eq '-999'}">
							<input type="file" name="file" id="file_info" />
						</c:when>
						<c:otherwise>
							<b id="file_nm">
								${result.realFileNm}
								<a href="javascript:deleteFile();">x</a>
							 </b>
						</c:otherwise>
					</c:choose>
				</div>
				
			</div>
			
			<div>
				<span><a href="javascript:updateData();">${!empty nttCode? '수정': '작성'}</a></span>
				<span><a href="javascript:if(confirm('취소하시겠습니까?')) history.back();">취소</a></span>
			</div>
		
		</div>
	</form>
</div>

<jsp:include page="/WEB-INF/jsp/include/include_footer.jsp"/>
