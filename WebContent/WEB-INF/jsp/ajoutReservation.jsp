<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

<!-- Personnal CSS-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

<title>Ajout Reservation</title>

<script src="${pageContext.request.contextPath}/js/ajaxImmat.js"></script>
<script src="${pageContext.request.contextPath}/js/ajaxDispo.js"></script>
</head>
<body <c:if test="${resa.vehicule eq null}" >onload="validerImmat()"</c:if>>
	<c:if test="${not empty erreurs}">
		<span class="alert alert-danger" role="alert">${erreurs.resultat}</span>
	</c:if>
		<%@ include file="/WEB-INF/jsp/structure/navbar.jspf"%>
<div class="container">
	<form class="form-horizontal persoform" action="${pageContext.request.contextPath}/auth/ajoutreservation" method="post">
	<h1 class="ajusth1">Ajout d'une réservation :</h1>
	<%@ include file="/WEB-INF/jsp/structure/reservation_form.jspf"%>
		<button class="centrageform" type="submit" name="bouton" value="reserver">Valider la réservation</button>
	</form>	
</div>

	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>