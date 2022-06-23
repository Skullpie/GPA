<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
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

<title>Liste des réservataires</title>
</head>
<body onload="modal()">
<%@ include file="/WEB-INF/jsp/structure/navbar.jspf"%>
<c:if test="${sessionScope.user_inc.role eq 'admin'}">
<div class="container">
<h1 class="ajusth1bis">Liste des réservataires :</h1>
		<hr>
		<ul class="list-group">
		<c:forEach items="${reservataires}" var="res">
			
				<li class="list-group-item col-sm-5"><a href="${pageContext.request.contextPath}/auth/detailreservataire?reservataire=${res.id}">${res.nom} ${res.prenom}</a> ${res.lieu.intitule}
				<span class="alignebouton">
				<a class="btn btn-success" href="${pageContext.request.contextPath}/auth/modificationreservataire?reservataire=${res.id}">Modifier</a>
	    		<a class="confirmModalLink btn btn-danger" href="${pageContext.request.contextPath}/auth/supprimerReservataire?reservataire=${res.id}">Supprimer</a>
				</span>
				</li>
				<hr>
		</c:forEach>
		</ul>
		</div>
</c:if>
	<%@ include file="/WEB-INF/jsp/structure/modal.jspf"%>
	<script src="${pageContext.request.contextPath}/js/modal.js"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>