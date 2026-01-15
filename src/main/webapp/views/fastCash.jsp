<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Fast Cash</title>
</head>
<body>

	<h2>Fast Cash</h2>
	<form action="/fastCash" method="post">
	
		<label>Enter PIN : </label>
		<input type="password" name="pin" required><br><br>
		
		<p>Select Amount : </p>
		
		<button type="submit" name="amount" value="100">Rs 100</button>
		<button type="submit" name="amount" value="500">Rs 500</button>
		<button type="submit" name="amount" value="1000">Rs 1000</button>
		<button type="submit" name="amount" value="2000">Rs 2000</button>
		<button type="submit" name="amount" value="5000">Rs 5000</button>
		<button type="submit" name="amount" value="10000">Rs 10000</button>
	</form>
	
	<p style="color:red">${error}</p>
	<p style="color:green">${success}</p>

	<br>
	<a href="dashboard">Back to Dashboard</a>

</body>
</html>