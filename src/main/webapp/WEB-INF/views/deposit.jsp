<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Deposit Money</title>
</head>
<body>

<h2>Deposit Money</h2>

	<form action="/deposit" method="post">
	
		<label>Enter Amount : </label>
		<input type ="number" name = "amount" required><br><br>

    	<button type="submit">Deposit</button>
	</form>
	
	
	<!-- ${success} ==> EL (Expression Language) , For Spring mvc ==> Recommended -->
	<p style="color:green">${success}</p> 
	<p style="color:red">${error}</p>

</body>
</html>