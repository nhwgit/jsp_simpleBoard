<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>게시판</title>
</head>
<body>

<c:if test="${! empty authUser}">
	${authUser.name}님, 안녕하세요.
	<a href="logout.do">[로그아웃]</a>
	<a href="changePwd.do">[암호변경]</a>
</c:if>
<c:if test="${empty authUser}">
	<a href="join.do">[회원가입]</a>
	<a href="login.do"> [로그인]</a>
</c:if>
</body>
</html>