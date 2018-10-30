<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Weather Report</title>
      <link rel="stylesheet" type="text/css"
                href="${pageContext.request.contextPath}/css/style.css"/>
   </head>
   <body> 
      <table>
        <tr>
        	<h2>${weather.location.name}, ${weather.location.region}, ${weather.location.country}</h2>
        </tr>
        <c:forEach  items="${weather.forecast.forecastday}" var ="forecastday">
        <tr>
          <td>${forecastday.date}</td>
          <td><img src="http:${forecastday.day.condition.icon}"/></td>
          <td>${forecastday.day.condition.text} | <fmt:formatNumber type = "number" value = "${forecastday.day.avgtempC}"/>&#x2103;</td>
        </tr>
        </c:forEach>
      </table>

   </body>
   
</html>