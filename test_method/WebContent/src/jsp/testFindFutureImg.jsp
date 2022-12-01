<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="/src/js/testUpdateImg3.js"></script>
<title>test</title>
</head>
<body>
<h1>10분 단위 강우량 예측 이미지</h1>
<input type="text" id="searchDate" value="202005110000"/>
<input type="button" onclick="selectFutureImg();" value="예측 이미지 찾기"/>
<br> <br>
<img id="img1" src="">
<img id="img2" src="">
<img id="img3" src="">

</body>
</html>