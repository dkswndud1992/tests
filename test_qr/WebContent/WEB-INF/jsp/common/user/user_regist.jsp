<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/jsp/include/include_header.jsp"/>

<script type="text/javascript">
<%-- 초기 세팅. --%>
$(function(){
	<%-- 아이디 변화시 중복확인 리셋. --%>
	$("#userId").change(function(){
		$("#checkId").val("0");
		$("#txtMsg").text("");
		return;
	});
	
	$("#userId").focus();
});

<%-- 사용자 등록.--%>
function insertData(){
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
	
	if($.trim($("#userId").val())==""){
		alert("아이디를 입력해주세요.");
		$("#userId").focus();
		return;
	}
	
	if($("#userId").val().length > 20){
		alert("아이디는 20글자를 초과할 수 없습니다.");
		$("#checkId").focus();
		return;
	}
	
	if ( !/^[^ !"`'#%&,:;<>=@{}~\$\(\)\*\+\/\\\?\[\]\^\|]+$/.test($("#userId").val()) ) {
		alert("아이디에는 특수문자를 넣을 수 없습니다.");
		$("#userId").focus();
		return;
	}
	
	if($("#checkId").val()=="0"){
		alert("아이디 중복확인을 체크해주세요.");
		return;
	}
	
	if ( !/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/.test($.trim($("#userPwd").val())) ) {
		alert("비밀번호는 문자, 숫자, 특수문자(!@#$%^*+=-)의 조합(8~16)으로 입력해주세요.");
		return;
	}
	
	if($.trim($("#userPwd1").val())==""){
		alert("비밀번호(확인)을 입력해주세요.");
		$("#userPwd1").focus();
		return;
	}
	
	if($("#userPwd").val()!=$("#userPwd1").val()){
		alert("비밀번호를 확인해주세요.");
		$("#userPwd1").focus();
		return;
	}
	
	
	if(!confirm("회원가입을 하시겠습니까?")) return;
	
	$.ajax({
		url: '/userCont/insertUserInfo.do',
		dataType: "json",
		type: "POST",
		async: false,
		data: frmData,
    	success: function( data ) {
    		alert("회원가입이 완료되었습니다.");
    		
    		location.replace("/userMain/login.do");
    		
    	},
    	error : function( e ) {
    	}
	});
	
};

	
<%-- 아이디 체크.--%>
function selectData() {
	if($("#userId").val()=='') {
		alert("아이디를 입력해주세요.");
		$("#userId").focus();
		return;
	}
	
	if($("#userId").val().length > 20){
		alert("아이디는 20글자를 초과할 수 없습니다.");
		$("#checkId").focus();
		return;
	}
	
	if ( !/^[^ !"`'#%&,:;<>=@{}~\$\(\)\*\+\/\\\?\[\]\^\|]+$/.test($("#userId").val()) ) {
		alert("아이디에는 특수문자를 넣을 수 없습니다.");
		$("#userId").focus();
		return;
	}
	
	$.ajax({
		url:'/userCont/selectUserIdConfirm.do',
		dataType:"json",
		type:"POST",
		async:false,
		data:{userId : $("#userId").val()},
    	success:function( data ) {
    		if(data.useYn == "Y") {
    			$("#checkId").val("1");
    			$("#txtMsg").text("사용가능한 아이디 입니다.");
    			
    		} else {
    			$("#checkId").val("0");
    			$("#txtMsg").text("사용중인 아이디입니다.");
    		}
    		
    		return;
    	},
    	error : function( e ) {
    	}
	});
	return;
};

</script>
	
<div id="container">
	<form method="post" id="frm">
		<input type="hidden" name="checkId" id="checkId" value="0"/>
		
		<div>
			<h2>회원가입</h2>
			<p>회원정보는 개인정보취급방침에 따라 안전하게 보호되며 사용자의 명백한 동의 없이 공개 또는 제3자에게 제공되지 않습니다.</p>
			
			<div>
			 	
				<table>
					<col width="" />
					<col width="" />
              			
					<tbody>
						<tr>
							<td>아이디</td>
							<td>
								<input type="text" name="userId" id="userId" maxlength="20"/>
								<a href="javascript:selectData();">중복확인</a>
								<span id="txtMsg"></span>
							</td>
						</tr>
						<tr>
							<td>이름</td>
							<td><input type="text" name="userNm" id="userNm" maxlength="20"/></td>
						</tr>
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
				<span><a href="javascript:insertData();">회원가입</a></span>
				<span><a href="javascript:if(confirm('회원가입을 취소하시겠습니까?')) history.back();">취소</a></span>
			</div>
			
		</div>
	</form>
</div>
<jsp:include page="/WEB-INF/jsp/include/include_footer.jsp"/>
