<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Balance Enquiry</title>
</head>
<body>

<h2>Balance Enquiry</h2>
	<form action="/balance" method="post">
	
		<label>Enter PIN : </label>
		<input type ="password" name = "pin" required><br><br>

    	<button type="submit">Check Balance</button>
	</form>
	
	<p style="color:green; font-size:18px;">${balanceMessage}</p>
	<p style="color:red">${error}</p>
	
		
	<br>
	<a href="dashboard">Back to Dashboard</a>
	

</body>
</html>