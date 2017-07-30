<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Spring Example site</title>
</head>
<body>

	<h1>Welcome, you friggin noob! Nyahahahaa</h1>

	<!-- The below Spring Form will populate the user object that it was given when the
		 information is submitted by calling the objects setters (as denoted by the path attribute) -->
	<sf:form method="POST" modelAttribute="userObj" action="submit">
		<sf:label path="firstname">First name</sf:label>
		<sf:input path="firstname" id="first_name" size="30" />
		<br />
		<sf:label path="lastname">Last name</sf:label>
		<sf:input path="lastname" id="last_name" size="30" />
		<input type="submit" name="commit" value="Submit" />
	</sf:form>

</body>
</html>