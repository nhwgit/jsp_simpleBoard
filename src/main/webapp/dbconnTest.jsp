<%@ page language="java" contentType="text/html; charset=utf8"%>
<%@ page import="jdbc.connection.ConnectionProvider" %>
<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
<title>연결 테스트</title>
</head>
<body>
<%
	try(Connection conn = ConnectionProvider.getConnection()) {
		out.println("연결 성공");
	} catch(SQLException e) {
		out.println("연결 실패" + e.getMessage());
	}
%>
</body>
</html>