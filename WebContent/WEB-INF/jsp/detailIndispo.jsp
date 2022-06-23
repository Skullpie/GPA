<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

<!-- Personnal CSS-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

<!--ressources nécessaires pour FullCalendar-->
<link href='${pageContext.request.contextPath}/js/fullcalendar-4.4.0/packages/core/main.css' rel='stylesheet' />
<link href='${pageContext.request.contextPath}/js/fullcalendar-4.4.0/packages/daygrid/main.css' rel='stylesheet' />
<link href='${pageContext.request.contextPath}/js/fullcalendar-4.4.0/packages/timegrid/main.css' rel='stylesheet' />
<script src='${pageContext.request.contextPath}/js/fullcalendar-4.4.0/packages/core/main.js'></script>
<script src='${pageContext.request.contextPath}/js/fullcalendar-4.4.0/packages/core/locales-all.js'></script>
<script src='${pageContext.request.contextPath}/js/fullcalendar-4.4.0/packages/interaction/main.js'></script>
<script src='${pageContext.request.contextPath}/js/fullcalendar-4.4.0/packages/daygrid/main.js'></script>
<script src='${pageContext.request.contextPath}/js/fullcalendar-4.4.0/packages/timegrid/main.js'></script>
<script src='${pageContext.request.contextPath}/js/fullcalendar-4.4.0/packages/list/main.js'></script>

<meta charset="UTF-8">
<title>Calendrier des Indisponibilités</title>

<script>

  document.addEventListener('DOMContentLoaded', function() {
	  var initialLocaleCode = 'fr';
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
      plugins: [ 'interaction', 'dayGrid', 'timeGrid','list' ],
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay'
      },
      defaultDate: new Date(),
      locale: initialLocaleCode,
      navLinks: true, // can click day/week names to navigate views
      selectable: true,
      selectMirror: true,
      select: function(arg) {
        var title = prompt('Event Title:');
        if (title) {
          calendar.addEvent({
            title: title,
            start: arg.start,
            end: arg.end,
            allDay: arg.allDay
          })
        }
        calendar.unselect()
      },
      editable: false,
      eventLimit: true,
      //Création du tableau javascript à partir de l'attribut de request "reservationsJSON"
      //${reservationsJSON} est une instruction EL traduite côté serveur
      events: ${reservationsJSON}});

    calendar.render();
  });
</script>
</head>
<body>
	<c:if test="${!erreurs.resultat eq null}">
	<div class="alert alert-success" role="alert">
	${erreurs.resultat}
	</div>
	</c:if>
	
	
	
	
<%@ include file="/WEB-INF/jsp/structure/navbar.jspf"%>
	
<%--
		<c:if test="${!empty sessionScope.user_inc}">
				<a class="dropdown-item" href="${pageContext.request.contextPath}/auth/ajoutvehicule">Ajouter un véhciule</a>
				<a class="dropdown-item" href="${pageContext.request.contextPath}/auth/listevehicule">Liste des véhicules</a>
				<a class="dropdown-item" href="${pageContext.request.contextPath}/auth/gestionindispo">Indisponibilité</a>
				<a class="dropdown-item" href="${pageContext.request.contextPath}/auth/ajoutreservation">Faire une réservation</a>
			<c:if test="${sessionScope.user_inc.role eq 'admin'}">
							<a class="dropdown-item" href="${pageContext.request.contextPath}/auth/AjoutVehiculeServlet"	alt="Ajouter un véhciule">Action admin</a>
			</c:if>

			<a class="dropdown-item" href="${pageContext.request.contextPath}/deconnexion">Me déconnecter</a>
		</c:if>
 --%>
	<div id='calendar'></div>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>