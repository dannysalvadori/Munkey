<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Hotel Booker</title>
	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/hb_core.css"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/hb_index.css"/>
</head>
<body>

	<!--img id="skyLogo" src="<%=request.getContextPath()%>/resources/img/images.jpg"/-->
	    
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
            		<form action="search" method="POST">
            			<table>
            				<tr>
            					<td>Where are you going?</td>
            					<td><input placeholder="city or postcode"></td>
            				</tr>
            				<tr>
            					<td>How many guests?</td>
            					<td><input placeholder="e.g. 4 ..."></td>
            				</tr>
            				<tr>
            					<td>Check-in</td>
            					<td><input placeholder="check-in..." type="date"></td>
            				</tr>
            				<tr>
            					<td>Check-out</td>
            					<td><input placeholder="check-out..." type="date"></td>
            				</tr>
            				<tr>
            				    <td></td>
            					<td><input class="SubmitButton" type="submit" value="Find Hotels"></td>
            				</tr>
            			</table>
            		</form>
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