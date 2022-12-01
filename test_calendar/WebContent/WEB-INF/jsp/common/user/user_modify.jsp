<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/include/include_header.jsp"/>

<script type="text/javascript">
<%-- 초기 세팅 --%>
$(function(){
	$("#userNm").focus();
});

<%-- 사용자 정보 수정 요청.--%>
function updateData(){
	var frmData = $("#frm").serialize();
	
	if($.trim($("#userNm").val())==""){
		alert("이름을 입력해주세요.");
		$("#userNm").focus();
		return;
	}
	
	if($("#userNm").val().length > 20){
		alert("이름은 20글자를 초과할 수 없습니다.");
		$("#userNm").focus();
		return;
	}
	
	if ( !/^[^ !"`'#%&,:;<>=@{}~\$\(\)\*\+\/\\\?\[\]\^\|]+$/.test($("#userNm").val()) ) {
		alert("이름에는 특수문자를 넣을 수 없습니다.");
		$("#userNm").focus();
		return;
	}
	
	if(!confirm("회원정보를 수정하시겠습니까?")) return;
	
	$.ajax({
		url: "/userCont/updateUserInfo.do",
		dataType: "json",
		type: "POST",
		async: false,
		data: frmData,
    	success: function(data) {
    		alert("회원정보 수정이 완료되었습니다.");
    		
    		location.replace("/main/intro.do");
    		
    	},
    	error: function( e ) {
    	}
	});
	
};
</script>

<div id="container">
	<form method="post" id="frm">
      
		<div>
			<h2>회원정보수정</h2>
			<p>회원정보는 개인정보취급방침에 따라 안전하게 보호되며 사용자의 명백한 동의 없이 공개 또는 제3자에게 제공되지 않습니다.</p>
			
			<div>
				<table>
					<col width="" />
					<col width="" />
					
					<tbody>
						<tr>
							<td>아이디</td>
							<td>${userInfo.userId}</td>
						</tr>
						<tr>
							<td>이름</td>
							<td><input type="text" name="userNm" id="userNm" maxlength="20" value="${userInfo.userNm}" /></td>
						</tr>
						<tr>
							<td>비밀번호</td>
							<td><a href="javascript:location.replace('/userMain/modifyPassword.do');">비밀번호 변경</a></td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div>
				<span><a href="javascript:updateData();">수정완료</a></span>
				<span><a href="javascript:if(confirm('회원정보 변경을 취소하시겠습니까?')) history.back();">취소</a></span>
			</div>
			
		</div>

  		</form>
</div>
<jsp:include page="/WEB-INF/jsp/include/include_footer.jsp"/>
