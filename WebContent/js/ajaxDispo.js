var requetebis;

function validerDispo() {

   var dateDebut = document.getElementById("dateDebut").value;
   var dateFin = document.getElementById("dateFin").value;
   
   if (dateDebut && dateFin){
   var url = "http://localhost:8080/gpa/auth/validerDisponibilite?dateDebut="+dateDebut+"&dateFin="+dateFin;
   
   if (window.XMLHttpRequest) {
	   requetebis = new XMLHttpRequest();
   } else if (window.ActiveXObject) {
	   requetebis = new ActiveXObject("Microsoft.XMLHTTP");
   }
   requetebis.open("GET", url, true);
   requetebis.onreadystatechange = majIHMbis;
   requetebis.send(null);
   }
}

function majIHMbis() {
  var reponse = "";

  if (requetebis.readyState == 4) {
    if (requetebis.status == 200) {
      // exploitation des données de la réponse

    	reponse = JSON.parse(requetebis.responseText);
    	console.log(reponse);      
      mdiv = document.getElementById("immat");
        
	  while (mdiv.firstChild) {
		  mdiv.removeChild(mdiv.firstChild);
	  }
      
    	  
      var label = document.createElement("label"); //crée le label
      label.innerHTML = "Vehicule" //insère du texte dans le label
      label.setAttribute('class', 'control-label col-sm-2')
      mdiv.appendChild(label); // attache le label au div
      
    	  var oSelect = document.createElement("select");//crée le select
    	  oSelect.id = "immatriculation";
    	  oSelect.name = "immatriculation"; 
    	  oSelect.setAttribute('class', 'form-control col-sm-6');
    	  oSelect.setAttribute('onchange','validerImmat()');
    	  
    	  
    	  for (var veh of reponse) {
    		  var oOption = document.createElement("option");//crée l'option
	    	  oInner  = document.createTextNode(veh.immatriculation + ' ' + veh.description);// crée un texte
	    	  oOption.value = veh.immatriculation;
    	           
	    	  oOption.appendChild(oInner);// attache le texte à l'option
	    	  oSelect.appendChild(oOption);// attache l'option au select    		  
    	  }

    	  mdiv.appendChild(oSelect);// attache le select au div
    	  validerImmat();
      }
    }
}