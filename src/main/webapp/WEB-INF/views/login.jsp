<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Bank Login</title>
</head>
<body>

	<%
		String success = (String) request.getAttribute("success");
		String error = (String) request.getAttribute("error");

		if (success != null) {
	%>
			<p style="color: green;"><%=success%></p>
	<%
		}
		if (error != null) {
	%>
		<p style="color: red;"><%=error%></p>
	<%
		}
	%>

	<h2>Login</h2>

	<form action="/login" method="post">
		<label>Card Number:</label>
		<input type="text" name="cardno" required><br><br>
		
		<label>PIN:</label> 
		<input type="password" name="pin" required><br><br>

		<button type="submit">Login</button>
	</form>
</body>
</html>
