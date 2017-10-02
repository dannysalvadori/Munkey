<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Hotel Booker</title>
	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/hb_core.css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/hb_index.css"/>
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
	
	<table>
	    <tr>
	        <td>
            	<div id="searchForm" class="MainItem BoxLight">
            		<h2>Find a Hotel</h2>
            		<form:form action="search" method="POST" modelAttribute="searchParameter">
            			<table>
            				<tr>
            					<td>Where are you going?</td>
            					<td><form:input path="locationString" class="Input" placeholder="city or postcode"/></td>
            				</tr>
            				<tr>
            					<td>How many guests?</td>
            					<td><form:input path="numberOfGuests" class="Input" placeholder="e.g. 4 ..."  type="Number" min="1" max="50"/></td>
            					<td><form:errors path="numberOfGuests" cssClass="error" /></td>
            				</tr>
            				<tr>
            					<td>Check-in</td>
            					<td><form:input path="checkin" class="Input" type="date"/></td>
            				</tr>
            				<tr>
            					<td>Check-out</td>
            					<td><form:input path="checkout" class="Input" type="date"/></td>
            				</tr>
            				<tr>
            				    <td></td>
            					<td><input class="SearchSubmitBtn" type="submit" value="Find Hotels"/></td>
            				</tr>
            			</table>
            		</form:form>
            	</div>
            </td>
            <td width="30px">
                <!--divider-->
            </td>
            <td>
                <div class="MainItem BoxVibrant">
                    <p>
                        Some kind of content...<br/><br/>Pretty picture carousel...
                    </p>
                    <div style="border: 1px solid black;">
                        <!--Carousel Pictures or stuff...-->&nbsp;<br/><br/><br/><br/><br/><br/><br/>&nbsp;
                    </div>
                </div>
            </td>
        </tr>
    </table>
	
</body>
</html>