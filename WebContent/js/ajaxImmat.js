var requete;

function validerImmat() {

   var immat = document.getElementById("immatriculation").value;
   var url = "http://localhost:8080/gpa/auth/validerImmat?valeur=" + immat;
   
   if (window.XMLHttpRequest) {
       requete = new XMLHttpRequest();
   } else if (window.ActiveXObject) {
       requete = new ActiveXObject("Microsoft.XMLHTTP");
   }
   requete.open("GET", url, true);
   requete.onreadystatechange = majIHM;
   requete.send(null);
}

function majIHM() {
  var reponse = "";

  if (requete.readyState == 4) {
    if (requete.status == 200) {
      // exploitation des données de la réponse

    	reponse = JSON.parse(requete.responseText);
    	      
      mdiv = document.getElementById("reservataire");
        
	  while (mdiv.firstChild) {
		  mdiv.removeChild(mdiv.firstChild);
	  }
      
      
      var label = document.createElement("label"); //crée le label
      label.innerHTML = "Reservataire" //insère du texte dans le label
      label.setAttribute('class', 'paddingnull control-label col-sm-2')
      mdiv.appendChild(label); // attache le label au div
      
      for (var i = 0; i < reponse.nombreDePlace; i++) {
    	  var oSelect = document.createElement("select");//crée le select
    	  oSelect.id = "reservataire"+ i;
    	  oSelect.name = "reservataire"+ i; 
    	  oSelect.setAttribute('class', 'form-control col-sm-6')
    	  
    	  var oOption = document.createElement("option");//crée l'option
    	  oInner  = document.createTextNode("");// crée un texte
    	  oOption.value = "rien";
    	  oOption.appendChild(oInner);// attache le texte à l'option
    	  oSelect.appendChild(oOption);// attache l'option au select
    	  
    	  var reservataires = reponse.reservataires;
    	  
    	  for (var reservataire of reservataires) {
    		  var oOption = document.createElement("option");//crée l'option
	    	  oInner  = document.createTextNode(reservataire.nom + ' ' + reservataire.prenom);// crée un texte
	    	  oOption.value = reservataire.id;
    	           
	    	  oOption.appendChild(oInner);// attache le texte à l'option
	    	  oSelect.appendChild(oOption);// attache l'option au select    		  
    	  }

    	  mdiv.appendChild(oSelect);// attache le select au div
      }
    }
  }
}