<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Deposit Money</title>
</head>
<body>

	<form action="/deposit" method="post">
	
		<label>Enter Amount : </label>
		<input type ="number" name = "amount" required><br><br>

		<label>Enter PIN : </label><br>
		<input type ="password" name = "pin" required><br><br>

    	<button type="submit">Deposit</button>
	</form>
	
	<p style="color:red">${error}</p>
	<p style="color:green">${success}</p>

</body>
</html>