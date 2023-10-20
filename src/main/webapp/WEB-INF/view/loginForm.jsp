<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>로그인</title>
</head>
<body>
<form action="login.do" method="post">
<c:if test="${errors.notExistId}"> 아이디가 존재하지 않습니다. </c:if>
<c:if test="${errors.notMatchIdAndPasswd}"> 비밀번호가 일치하지 않습니다.</c:if>
<p>
	아이디:<br><input type="text" name="id" value="${param.id}">
	<c:if test="${errors.id}"> ID를 입력하세요. </c:if>
</p>
<p>
	암호:<br><input type="password" name="password">
	<c:if test="${errors.password}">암호를 입력하세요.</c:if>
</p>
<input type="submit" value="로그인">
</form>
</body>
</html>