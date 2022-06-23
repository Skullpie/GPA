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

<title>Détail Réservation</title>
</head>
<body>
<%@ include file="/WEB-INF/jsp/structure/navbar.jspf"%>

<fmt:parseDate value="${resa.dateDebut}" pattern="yyyy-MM-dd'T'HH:mm" var="date_debut"/>
<fmt:parseDate value="${resa.dateFin}" pattern="yyyy-MM-dd'T'HH:mm" var="date_fin"/>
<fmt:formatDate value="${date_debut}" pattern="dd-MM-yyyy HH:mm" var="dateDebut" />
<fmt:formatDate value="${date_fin}" pattern="dd-MM-yyyy HH:mm" var="dateFin" />
<div class="container persoform">
<form class="form-horizontal persoform">
	<h1 class="ajusth1">Détail réservation</h1>
	<div class="form-group row">
	<label class="control-label col-sm-2">Motif</label>
	<input type="text" readonly="readonly" class="form-control col-sm-6" value="${resa.motif}">
	</div>
	<div class="form-group row">
	<label class="control-label col-sm-2">Date de début</label>
	<input type="text" readonly="readonly" class="form-control col-sm-6" value="${dateDebut}">
	</div>
	<div class="form-group row">
	<label class="control-label col-sm-2">Date de fin</label>
	<input type="text" readonly="readonly" class="form-control col-sm-6" value="${dateFin}">
	</div>	
	<c:if test="${!empty resa.commentaire}">
		<div class="form-group row">
		<label class="control-label col-sm-2">Commentaire</label>
		<input type="text" readonly="readonly" class="form-control col-sm-6" value="${resa.commentaire}">
		</div>	
	</c:if>
	<div class="form-group row">
	<label class="control-label col-sm-2">Véhicule réservé</label>
	<input type="text" readonly="readonly" class="form-control col-sm-6" value="${resa.vehicule.immatriculation} ${resa.vehicule.description}">
	</div>	
	<div class="form-group row">
	<label class="control-label col-sm-2">Destination</label>
	<input type="text" readonly="readonly" class="form-control col-sm-6" value="${resa.lieu.intitule}">
	</div>
	<div class="form-group row">
	<label class="control-label col-sm-2">Réservataire<c:if test="${resa.reservataires.size() > 1}">s</c:if></label>
	<c:forEach items="${resa.reservataires}" var="reservataire">
	<input type="text" readonly="readonly" class="form-control col-sm-4" value="${reservataire.nom} ${reservataire.prenom}">
	<c:if test="${resa.reservataires.size() > 1}"><div><label class="control-label col-sm-2"></label></div></c:if>
	</c:forEach>
	</div>
</form>
<div class="centrage">
<a class="btn btn-warning col-sm-2" href="${pageContext.request.contextPath}/auth/modificationReservation?id=${resa.id}">Modifier</a>
<a class="btn btn-danger col-sm-2" href="${pageContext.request.contextPath}/auth/supprimerReservation?id=${resa.id}">Supprimer</a>
</div>
</div>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>