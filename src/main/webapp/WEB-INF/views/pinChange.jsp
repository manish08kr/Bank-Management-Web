<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Change Pin</title>
</head>
<body>

	<h2>Change Pin</h2>
	<form action="/changePin" method="post">

		<label>Old PIN : </label> <input type="password" name="oldPin"
			required><br>
		<br> <label>New PIN : </label> <input type="password"
			name="NewPin" required><br>
		<br> <label>Confirm PIN : </label> <input type="password"
			name="confirmPin" required><br>
		<br>

		<button type="submit">Change Pin</button>
	</form>

	<%
	String error = (String) request.getAttribute("error");
	String success = (String) request.getAttribute("success");

	if (error != null) {
	%>
	<p style="color: red;"><%=error%></p>
	<%
	}
	if (success != null) {
	%>
	<p style="color: green;"><%=success%></p>
	<%
	}
	%>

</body>
</html>