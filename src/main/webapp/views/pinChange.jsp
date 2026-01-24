<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Change Pin</title>
</head>
<body>

	<h2>Change PIN</h2>
	<form action="/changePin" method="post">

		<label>Old PIN : </label>
		<input type="password" name="oldPin" required><br><br> 
		
		<label>New PIN : </label> 
		<input type="password" name="NewPin" required><br><br> 
		
		<label>Confirm PIN : </label> 
		<input type="password" name="confirmPin" required><br><br>

		<button type="submit">Change Pin</button>
	</form>

	<p style="color:green;">${success}</p>
	<p style="color:red;">${error}</p>
	
	<br>
	<a href="dashboard">Back to Dashboard</a>

</body>
</html>