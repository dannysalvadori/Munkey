<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Hotel Search - Results</title>
</head>
<body>

	<h1>Search results for ${user.firstname}...</h1>

	<div>
		<p>You have XYZ results! Yay! <span>${user.firstname} ${user.lastname}</span></p>
	</div>

</body>
</html>