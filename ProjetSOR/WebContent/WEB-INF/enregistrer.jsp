<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Enregistrer</title>
</head>
<body>

<jsp:include page="/WEB-INF/menu.jsp"></jsp:include>
<h1>Enregistrement d'une image</h1>

<form action="Enregistrer" 
	method="post" 
	enctype="multipart/form-data" >
	Titre : <input type="text" name="titre" />
	<br>
	<input type="file" name="fic" value="" />
	<br>
	<input type="submit" value="Enregistrer" />
</form>

<br>

<img src="im.jpg">

</body>
</html>