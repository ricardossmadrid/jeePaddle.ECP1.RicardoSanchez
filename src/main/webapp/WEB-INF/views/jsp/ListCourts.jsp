<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8" />
<title>Pistas</title>
</head>
<body>
	<H1>Muestra las pistas del recinto</H1>

	<h3>Pistas</h3>
	
	<table style="border: black 2px solid;">
		<tr>
			<th>Pista</th>
			<th>¿Libre?</th>
		</tr>
    	<c:forEach items="${courts}" var="court">
      		<tr>
      			<td>Pista <c:out value="${court.courtId}"/></td>
      			<td><c:out value='${court.active}'/></td>
      		</tr>
    	</c:forEach>
  	</table>
	
	
	
	<br/>
	<h3>Hoy</h3>
	<p>UPM-MIW --- ${now}</p>
</body>
</html>