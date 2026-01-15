<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.bank.model.Transaction"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Mini Statement</title>
</head>
<body>

	<h2>Mini Statement</h2>
	<form action="/miniStatement" method="post">

		<label>Enter PIN : </label> 
		<input type="password" name="pin" required><br><br>

		<button type="submit">submit</button>
	</form>

	<br>
	
	<p style="color: red;">${error}</p>
	<%
		List<Transaction> trans = (List<Transaction>) request.getAttribute("trans");

		if (trans != null && !trans.isEmpty()) {
	%>

	<h3>Last 10 Transactions</h3>
	<table border="1">
		<tr>
			<th>Date</th>
			<th>Type</th>
			<th>Amount</th>
		</tr>

		<%
			for (Transaction t : trans) {
		%>
		<tr>
			<td><%=t.getDate()%></td>
			<td><%=t.getType()%></td>
			<td><%=t.getAmount()%></td>
		</tr>
		<%
			}
		%>
	</table>
	<%
		}
	%>

<br>
<a href="dashboard">Back to Dashboard</a>

</body>
</html>