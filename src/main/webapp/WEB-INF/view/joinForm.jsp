<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>가입 페이지</title>
</head>
<body>
<form action="join.do" method="post">
	<p>
	아이디:<br><input type="text" name="id" value="${param.id}">
	<c:if test="${errors.id }">ID를 입력하세요.</c:if>
	<c:if test="${errors.duplicateId }">이미 사용중인 아이디입니다.</c:if>
	</p>
	<p>
	이름:<br><input type="text" name="name" value="${param.name}">
	<c:if test="${errors.name}"> 이름을 입력하시오. </c:if>
	</p>
	<p>
	암호:<br><input type="text" name="password">
	<c:if test="${errors.password}">암호를 입력하세요.</c:if>	
	</p>
	<p>
	확인:<br><input type="text" name="confirmPassword">
	<c:if test="${errors.confirmPassword}">확인를 입력하세요.</c:if>
	<c:if test="${errors.notMatch }">두 암호가 일치하지 않습니다.</c:if>
	</p>
	<input type="submit" value="가입">
</form>
</body>
</html>