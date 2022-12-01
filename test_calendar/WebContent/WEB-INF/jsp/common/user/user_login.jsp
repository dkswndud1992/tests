<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/jsp/include/include_header.jsp"/>

<script type="text/javascript">
   	<%-- 아이디 온 포커스 --%>
	$( document ).ready(function() {
		$("#user_id").focus();
		
		$("#user_id").keyup(function(e){
			if(e.keyCode == 13) $("#user_pw").focus();
		});
		
		$("#user_pw").keyup(function(e){
			if(e.keyCode == 13) selectData(); 
		});
	});
   	
	<%-- 로그인 요청. --%>
	function selectData() {
		var user_id = $("#user_id").val();
		var user_pw = $("#user_pw").val();
		
		$.ajax({
		    type: "POST",
		    url: "/userCont/insertUserSession.do",
		    cache : false,
			dataType : "json",
			data : {
				"userId" : user_id,
				"userPw" : user_pw,
			},
			success : function(data) {
				if(data.login == "true"){
					location.replace("/main/intro.do");
				}else{
					alert(data.msg);
				}
			},
		});
	}
</script>

<div id="login_container">
	
	<form id="login_form" action="javascript:selectData();" name="form" method="post">
		<div id="login_area">
			<div>
                <p>로그인/회원가입 페이지 입니다.</p>
            </div>
			
			<ul>
				<li>
					<label for="user_id">User Id</label>
					<input type="text" id="user_id" name="userId" maxlength="20"/>
				</li>
				<li>
					<label for="user_pw">Password</label>
					<input type="password" id="user_pw"  name="userPw" maxlength="16"/>
				</li>
			</ul>
			
			<div><a href="javascript:selectData();">Log In</a></div>
			<p><a href="/userMain/regist.do">회원가입</a></p>
		</div>
	</form>
      </div>

<jsp:include page="/WEB-INF/jsp/include/include_footer.jsp"/>
