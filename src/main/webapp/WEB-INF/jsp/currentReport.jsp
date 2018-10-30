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
      <h2>${message.location.name}, ${message.location.region}, ${message.location.country}</h2>
       <p><img src="http:${message.current.condition.icon}"/></p>
      <h2>${message.current.condition.text} | <fmt:formatNumber type = "number" value = "${message.current.tempC}"/>&#x2103;</h2> 
      <br/>
      
   </body>
   
</html>