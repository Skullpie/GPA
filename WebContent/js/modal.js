function modal(){
	var theHREF
	const modalTriggers = document.querySelectorAll(".confirmModalLink")
	modalTriggers.forEach(trigger=>{trigger.addEventListener("click", ()=>{
    event.preventDefault();
    theHREF = document.querySelector(".confirmModalLink").getAttribute("href");
    document.querySelector("#confirmModal").removeAttribute("class");
    document.querySelector("#confirmModal").setAttribute("class", "modal show fade");
    document.querySelector("#confirmModal").setAttribute("aria-modal", "true");
    document.querySelector("#confirmModal").setAttribute("style", "display: block;")
    
}, false)});


document.querySelector("#confirmModalNo").addEventListener("click", function(event) {
    document.querySelector("#confirmModal").removeAttribute("class");
    document.querySelector("#confirmModal").setAttribute("class", "modal hide fade");
    document.querySelector("#confirmModal").removeAttribute("aria-modal");
    document.querySelector("#confirmModal").removeAttribute("style");
}, false);

document.querySelector("#confirmModalCross").addEventListener("click", function(event) {
    document.querySelector("#confirmModal").removeAttribute("class");
    document.querySelector("#confirmModal").setAttribute("class", "modal hide fade");
    document.querySelector("#confirmModal").removeAttribute("aria-modal");
    document.querySelector("#confirmModal").removeAttribute("style");
}, false);


document.querySelector("#confirmModalYes").addEventListener("click", function(event) {
	window.location.href = theHREF;
}, false);
}
