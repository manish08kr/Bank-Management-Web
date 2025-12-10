<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Bank Login</title>
</head>
<body>

<h2>Login</h2>

<form action="/login" method="post">
    <label>Card Number:</label>
    <input type="text" name="cardNo" required><br><br>

    <label>PIN:</label>
    <input type="password" name="pin" required><br><br>

    <button type="submit">Login</button>
</form>

</body>
</html>
