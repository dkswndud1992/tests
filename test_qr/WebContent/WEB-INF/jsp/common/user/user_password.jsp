<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/jsp/include/include_header.jsp"/>

<script type="text/javascript">
<%-- 비밀번호 온 포커스 --%>
$( document ).ready(function() {
	$("#userPwd").focus();
});

<%-- 사용자 비밀번호 수정 요청.--%>
function updateData(){
	var frmData = $("#frm").serialize();

	if ( !/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/.test($.trim($("#userPwd").val())) ) {
		alert("비밀번호는 문자, 숫자, 특수문자의 조합(8~16)으로 입력해주세요.");
		return;
	}

	if($.trim($("#userPwd1").val())==''){
		alert("비밀번호(확인)을 입력해주세요.");
		$("#userPwd1").focus();
		return;
	}

	if($("#userPwd").val()!=$("#userPwd1").val()){
		alert("비밀번호를 확인해주세요.");
		$("#userPwd1").focus();
		return;
	}
	
	if(!confirm("비밀번호를 수정하시겠습니까?")) return;
	
	$.ajax({
		url: "/userCont/updateUserPw.do",
		dataType: "json",
		type: "POST",
		async: false,
		data: frmData,
    	success: function(data) {
    		alert("비밀번호 수정이 완료되었습니다.");
    		
    		location.replace("/userMain/modify.do");
    		
    	},
    	error: function( e ) {
    	}
	});
	
};

</script>

<div id="container">
	<form method="post" id="frm">
      
		<div>
			<h2>비밀번호 변경</h2>
			<p class="txt">회원정보는 개인정보취급방침에 따라 안전하게 보호되며 사용자의 명백한 동의 없이 공개 또는 제3자에게 제공되지 않습니다.</p>
			
			<div>
				<table>
					<col width="" />
					<col width="" />
					
					<tbody>
						<tr>
							<td>비밀번호</td>
							<td>
								<input type="password" name="userPwd" id="userPwd" maxlength="16"/>
								<em><br />8자리 이상 16자리 이하 영어 대문자 또는 소문자, 숫자, 특수문자 등 3종류 조합으로 입력해 주세요.</em>
							</td>
						</tr>
						<tr>
							<td>비밀번호 확인</td>
							<td><input type="password" name="userPwd1" id="userPwd1" maxlength="16"/></td>
						</tr>
					</tbody>
				</table>
			</div>
	
			<div>
				<span><a href="javascript:updateData();">수정완료</a></span>
				<span><a href="javascript:if(confirm('비밀번호 변경을 취소하시겠습니까?'))  location.replace('/userMain/modify.do');">취소</a></span>
			</div>
	
		</div>
  		</form>
</div>
<jsp:include page="/WEB-INF/jsp/include/include_footer.jsp"/>
