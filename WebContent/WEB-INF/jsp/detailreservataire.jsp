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

<title>Détail Réservataire</title>
</head>
<body>
<%@ include file="/WEB-INF/jsp/structure/navbar.jspf"%>


<div class="container persoform">
<form class="form-horizontal persoform">
	<h1 class="ajusth1">Détail Réservataire :</h1>
	<div class="form-group row">
	<label class="control-label col-sm-2">Nom</label>
	<input type="text" readonly="readonly" class="form-control col-sm-6" value="${res.nom}">
	</div>
	<div class="form-group row">
	<label class="control-label col-sm-2">Prénom</label>
	<input type="text" readonly="readonly" class="form-control col-sm-6" value="${res.prenom}">
	</div>
	<div class="form-group row">
	<label class="control-label col-sm-2">Email</label>
	<input type="text" readonly="readonly" class="form-control col-sm-6" value="${res.email}">
	</div>	
	<div class="form-group row">
	<label class="control-label col-sm-2">Téléphone</label>
	<input type="text" readonly="readonly" class="form-control col-sm-6" value="${res.telephone}">
	</div>	
	<c:if test="${!empty res.rue}">
		<div class="form-group row">
		<label class="control-label col-sm-2">Rue</label>
		<input type="text" readonly="readonly" class="form-control col-sm-6" value="${res.rue}">
		</div>	
	</c:if>
	<c:if test="${!empty res.codePostal}">
		<div class="form-group row">
		<label class="control-label col-sm-2">Code Postal</label>
		<input type="text" readonly="readonly" class="form-control col-sm-6" value="${res.codePostal}">
		</div>	
	</c:if>
	<c:if test="${!empty res.ville}">
		<div class="form-group row">
		<label class="control-label col-sm-2">Ville</label>
		<input type="text" readonly="readonly" class="form-control col-sm-6" value="${res.ville}">
		</div>	
	</c:if>
	<div class="form-group row">
	<label class="control-label col-sm-2">Campus de rattachement</label>
	<input type="text" readonly="readonly" class="form-control col-sm-6" value="${res.lieu.intitule}">
	</div>
</form>
<div class="centrage">
<a class="btn btn-warning col-sm-2" href="${pageContext.request.contextPath}/auth/modificationreservataire?reservataire=${res.id}">Modifier</a>
<a class="btn btn-danger col-sm-2" href="${pageContext.request.contextPath}/auth/supprimerreservataire?id=${res.id}">Supprimer</a>
</div>
</div>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>