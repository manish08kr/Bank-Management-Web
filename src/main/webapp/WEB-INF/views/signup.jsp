<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Signup - Step1</title>
</head>
<body>

	<h2>Application Form - Step1</h2>

	<form action="/signup-step1" method="post">
	
		<label>Name : </label>
		<input type ="text" name = "name" required><br><br>
		
		<label>Father's Name :</label>
		<input type = "text" name="fname" required><br><br>
		
		<label>Date of birth : </label>
		<input type="date" name= "dob" required><br><br>
		
		<label>Gender : </label>
		<input type = "radio" name="gender" value="male">Male
		<input type = "radio" name="gender" value="female">Female
		<br><br>
		
		<label>Email : </label>
		<input type="email" name="email"required><br><br>
		
		<label>Marital Status:</label>
   		<input type="radio" name="marital" value="Married">Married
    	<input type="radio" name="marital" value="Unmarried">Unmarried
	    <br><br>

    	<button type="submit">Next</button>
	</form>

</body>
</html>