<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Signup - Step2</title>
</head>
<body>

	<h2>Application Form - Step2</h2>

	<form action="/signup-step2" method="post">
	
		<label>Address : </label>
		<input type ="text" name = "address" required><br><br>
		
		<label>City :</label>
		<input type = "text" name="city" required><br><br>
		
		<label>State : </label>
		<input type="text" name= "state" required><br><br>
		
		<label>Pincode : </label>
		<input type="text" name="pincode"required><br><br>

    	<button type="submit">Next</button>
	</form>

</body>
</html>