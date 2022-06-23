<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">

<!-- Personnal CSS-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">

<title>Connexion</title>
</head>
<body>
<img alt="logo ENI" src="${pageContext.request.contextPath}/images/logoENI.jpg" width="100px" height="100px">
	<div class="container">
		<div class="centrage">
			<h1 class="ajusth1bis">Connexion</h1>
		</div>
		<!--erreur-->
		<%@ include file="/WEB-INF/jsp/structure/message_error.jspf"%>
		<!--formulaire-->
		<form action="${pageContext.request.contextPath}/connexion"	method="POST" class="centrage">
			<label for="inputIdentifiant" class="sr-only">Identifiant</label>
			<input type="text" id="inputIdentifiant" class="form-control col-sm-4" name="pseudo"	placeholder="Pseudo" required autofocus	value="${cookie['rememberPseudo'].value }">
			<label for="inputPassword" class="sr-only">Password</label>
			<input type="password" id="inputPassword" class="form-control col-sm-4" name="password" placeholder="Mot de passe" required value="${cookie['rememberPwd'].value }">
			<div class="checkbox mb-3">
				<label> <input type="checkbox" name="remember"
					value="remember"> Se souvenir de moi
				</label>
			</div>
			<button class="btn btn-lg btn-primary btn-block col-sm-4" type="submit"
				title="Me connecter">Me connecter</button>
			<a href="#">Mot de passe oublié</a>
		</form>
	</div>
</body>
</html>