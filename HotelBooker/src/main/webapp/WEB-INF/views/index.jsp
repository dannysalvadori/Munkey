<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome Page</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/hb_core.css"/>
<style>
	.red-border {
		border: 2pt solid #FF0000;
	}
</style>


</head>
<body>
	<h1 class="red-border text-center">Welcome Noob!</h1>
	
	<div>
		<p>To log in, please click the button below:</p>
		<form action="login" method="POST">
			<input type="submit" value="Login Page">
		</form>
		
	</div>

</body>
</html>