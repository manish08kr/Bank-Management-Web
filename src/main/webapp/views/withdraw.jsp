<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Withdraw Money</title>
</head>
<body>

	<h2>Withdraw Money</h2>

	<form action="/withdraw" method="post">

		<label>Enter Amount : </label>
		<input type="number" name="amount" required><br>
		
		<br> <label>Enter PIN : </label> 
		<input type="password" name="pin" required><br>
		<br>

		<button type="submit">Withdraw</button>
	</form>

	<p style="color: green">${success}</p>
	<p style="color: red">${error}</p>

	<br>
	<a href="dashboard">Back to Dashboard</a>

</body>
</html>