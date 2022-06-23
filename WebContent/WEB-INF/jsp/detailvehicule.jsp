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

<title>Détail Véhicule</title>
</head>
<body>
<%@ include file="/WEB-INF/jsp/structure/navbar.jspf"%>


<div class="container persoform">
<form class="form-horizontal persoform">
	<h1 class="ajusth1">Détail Véhicule :</h1>
	<div class="form-group row">
	<label class="control-label col-sm-2">Immatriculation</label>
	<input type="text" readonly="readonly" class="form-control col-sm-6" value="${veh.immatriculation}">
	</div>
	<div class="form-group row">
	<label class="control-label col-sm-2">Description</label>
	<input type="text" readonly="readonly" class="form-control col-sm-6" value="${veh.description}">
	</div>
	<div class="form-group row">
	<label class="control-label col-sm-2">Date d'achat</label>
	<input type="text" readonly="readonly" class="form-control col-sm-6" value="${veh.dateAchat}">
	</div>	
	<div class="form-group row">
	<label class="control-label col-sm-2">Nombre de places</label>
	<input type="text" readonly="readonly" class="form-control col-sm-6" value="${veh.nombrePlace}">
	</div>	
	<c:if test="${!empty veh.copieCarteGrise}">
		<div class="form-group row">
		<label class="control-label col-sm-2">Copie carte grise</label>
		<a href="E:/Program Files/eclipse_workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/gpa/CCG/${veh.copieCarteGrise}">${veh.copieCarteGrise}</a>
		</div>	
	</c:if>
	<c:if test="${!empty veh.reservataire}">
		<div class="form-group row">
		<label class="control-label col-sm-2">Propriétaire</label>
		<input type="text" readonly="readonly" class="form-control col-sm-6" value="${veh.reservataire.nom} ${veh.reservataire.prenom}">
		</div>	
	</c:if>
		<div class="form-group row">
		<label class="control-label col-sm-2">Campus de rattachement</label>
		<input type="text" readonly="readonly" class="form-control col-sm-6" value="${veh.lieu.intitule}">
		</div>	

</form>
<div class="centrage">
<a class="btn btn-success col-sm-2" href="${pageContext.request.contextPath}/auth/listevehicule">Retour</a>
</div>
</div>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>