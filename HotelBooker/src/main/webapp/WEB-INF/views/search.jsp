<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>HotelBooker Results</title>
	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/hb_core.css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/hb_search.css"/>
</head>
<body>
    
    <div id="nav">
        <table width="100%">
            <tr>
                <td class="Logo">HotelBooker.com</td>
                <td>
                    <table class="NavOptions" align="right">
                        <tr>
                            <td class="Option"><a href="http://www.google.com">Login</a></td>
                            <td>|</td>
                            <td class="Option"><a href="http://www.google.com">Register</a></td>
                            <td>|</td>
                            <td class="Option"><a href="http://www.google.com">My&nbsp;Reservations</a></td>
                            <td>|</td>
                            <td class="Option"><a href="http://www.google.com">Support</a></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
	
	<div id="searchForm" class="BoxLight">
		<h2>Your Search</h2>
		<form action="search" method="POST">
			<table>
				<tr>
					<td>Check in</td>
					<td>Check out</td>
					<td>Guests</td>
					<td>City / Postcode</td>
				</tr>
				    <td><input class="Input" type="date"></td>
				    <td><input class="Input" type="date"></td>
					<td><input class="Input" placeholder="4" type="Number" min="1" max="50"></td>
					<td><input class="Input" placeholder="London"></td>
					<td><input class="SearchSubmitBtn" type="submit" value="Update Search"></td>
				<tr>
			</table>
		</form>
	</div>
	
	<div id="SearchResultList">
	
		<c:forEach items="${optionList}" var="option">
		
		    <div class="SearchResultItem BoxLight">
	            <div>
	                <img src="https://media-cdn.tripadvisor.com/media/photo-o/0e/d5/8e/98/hotel-carlos-i.jpg" width="200px"/>
	            </div>
	            <div>
	                <span class="HotelName"><c:out value="${option.hotelName}" /></span><br/>
	                <span class="Price">&pound;<c:out value="${option.price}" /></span><br/>
	                <c:out value="${option.description}" />
	        	</div>
	        	<div align="center">
	        		<form action="select(optionId)" method="GET"><input class="Select Button" type="submit" value="Select"/></form>
	        		<form action="expand" method="POST"><input class="Detail Button" type="submit" value="Details"/></form>
	        		<form action="viewHotel(hotelId)" method="GET"><input class="Detail Button" type="submit" value="View Hotel"/></form>
	        	</div>
			</div>
			
		</c:forEach>

    </div>
	
</body>
</html>