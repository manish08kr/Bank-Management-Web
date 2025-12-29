<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
</head>
<body>

	<h2>Welcome to Bank Management Dashboard</h2>
	
	<%
	String cardno = (String) session.getAttribute("cardNo");
	%>
	<h3>Welcome, Card No : <%= cardno %></h3>

	<ul>
		<li><a href="/deposit">Deposit</a></li>
		<li><a href="/withdraw">Withdraw</a></li>
		<li><a href="/fastCash">Fast Cash</a></li>
		<li><a href="/balance">Balance Enquiry</a></li>
		<li><a href="/miniStatement">Mini Statement</a></li>
		<li><a href="/pinChange">Change PIN</a></li>
	</ul>
	
	<br><br>
	
	<a href="/logout">Logout</a>
</body>
</html>