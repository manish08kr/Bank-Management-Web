<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Signup - Step3</title>
</head>
<body>

	<h2>Application Form - Step3</h2>

	<form action="signup-complete" method="post">
	
		<label>Account Type : </label>
		<input type ="radio" name = "account_type" value ="saving">Saving<br>
		<input type ="radio" name = "account_type" value ="current">Current<br>
		<input type ="radio" name = "account_type" value ="Fixed Deposit">Fixed Deposit<br><br>
		
		<label>Services Required : </label><br>
		
		<input type ="checkbox" name = services value ="ATM Card">ATM Card<br>
		<input type ="checkbox" name = services value ="Mobile Banking">Mobile Banking<br>
		<input type ="checkbox" name = services value ="Internet Banking">Internet Banking<br>
		<input type ="checkbox" name = services value ="Cheque Book">Cheque Book<br>

    	<button type="submit">Submit Application</button>
	</form>

</body>
</html>