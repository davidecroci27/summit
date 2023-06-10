var campagnaSelezionata;
function prendiImmagineAnnotazione(campA){
				campagnaSelezionata = campA;
				$.post("servlet9", {"campagnaAnn": campA}, function (data){
					var ImmA = data;
					var log = ImmA+"@"+campA;
					if(ImmA=="vuoto"){
						document.getElementById("annotation_canvas").setAttribute("style", "width:1px;height:1px;");
						document.getElementById("immagineAnnotazione").setAttribute("src", "");
						document.getElementById("mexAnn").innerHTML = "Hai già annotato tutte le immagini per questa campagna.";
						document.getElementById("mexAnn").setAttribute("style", "");
					}
					if(ImmA=="noVoti"){
						document.getElementById("immagineAnnotazione").setAttribute("src", "");
						document.getElementById("annotation_canvas").setAttribute("style", "width:1px;height:1px;");
						document.getElementById("mexAnn").innerHTML = "Sembra che nessuna immagine sia ancora stata approvata per l'annotazione. Riprova piu' tardi!";
						document.getElementById("mexAnn").setAttribute("style", "");
					}else{
						document.getElementById("mexAnn").innerHTML = "";
						document.getElementById("mexAnn").setAttribute("style", "visibility: hidden;");
						document.getElementById("immagineAnnotazione").setAttribute("src", data);
						document.getElementById("annotation_canvas").setAttribute("style", "width:700px;");
					}
				})
			}

var canvas;			// riferimento all'elemento <canvas>
var context;		// contesto del canvas
var imageSource;	// riferimento all'elemento <img> che contiene l'immagine
var lastIndex = -1;				// indice dell'ultimo elemento negli array
var clickX = new Array();		// array di coordinate X
var clickY = new Array();		// array di coordinate Y
var clickDrag = new Array();	// array per segnalare le diverse componenti della linea
var paint = false;				// flag che indica se stiamo disegnando

var val = document.getElementById("immagineAnnotazione").name;
var ris = val.split("@");
var immagine = ris[0];
var campagna = ris[1];
var ris2 = immagine.split("\\");
var lung = ris2.length;
var imm = ris2[lung-1];
var linea;

$(document).ready(function () {
	canvas = document.getElementById("annotation_canvas");
	context = canvas.getContext("2d");
	imageSource = document.getElementById("immagineAnnotazione");
	// funzione da eseguire quando l'immagine è stata caricata
	imageSource.onload = function () {
		// adatta le dimensioni del canvas a quelle del tag <img>
		var largImm = imageSource.width;
		var fattore = 70000/largImm;
		canvas.width = imageSource.width*fattore/100;
		canvas.height = imageSource.height*fattore/100;
		
		// inserisci l'immagine dentro il canvas
		context.drawImage(imageSource, 0, 0, imageSource.naturalWidth, imageSource.naturalHeight,  // source rectangle
	  		  						   0, 0, canvas.width, canvas.height);  // destination rectangle
	  		  						   
		// nascondi il tag <img>
		imageSource.style.display = 'none';
	};

function addClick(x, y, dragging) {
	++lastIndex;
	clickX.push(x);
	clickY.push(y);
	clickDrag.push(dragging);
}

function draw() {
	context.strokeStyle = "#FF0000";
	context.lineJoin = "round";
	context.lineWidth = 7;
	context.globalAlpha = 0.6;
	context.beginPath();
	if (clickDrag[lastIndex] && lastIndex) {
	    context.moveTo(clickX[lastIndex-1], clickY[lastIndex-1]);
	} else {
		context.moveTo(clickX[lastIndex]-1, clickY[lastIndex]);
	}
	context.lineTo(clickX[lastIndex], clickY[lastIndex]);
	context.stroke();
	context.closePath();
}

$('#annotation_canvas').mousedown(function(e) {
	var mouseX = e.clientX - canvas.getBoundingClientRect().left;
	var mouseY = e.clientY - canvas.getBoundingClientRect().top;

	paint = true;
	addClick(mouseX, mouseY, false);
	draw();
});

$('#annotation_canvas').mousemove(function(e) {
	if (paint) {
		var mouseX = e.clientX - canvas.getBoundingClientRect().left;
		var mouseY = e.clientY - canvas.getBoundingClientRect().top;

		addClick(mouseX, mouseY, true);
		draw();
	}
});

$('#annotation_canvas').mouseup(function(e) {
	paint = false;
});

$('#annotation_canvas').mouseleave(function(e) {
	paint = false;
});
});

function salvaAnnotazione(){
	//chiama funzione che salva linea canvas e setta "annotata"=1 in tabella annotazione
	var utente = document.getElementById("usernameTitle").innerHTML;
	var immagine = document.getElementById("immagineAnnotazione").getAttribute("src");
	var canvas = document.getElementById("annotation_canvas");
	var idUser = document.getElementById("idUtente").innerHTML;
	var image = new Image();
	image = canvas.toDataURL("image/png");
	$.post("servlet13", {"canvas": image, "ncampagna": campagnaSelezionata, "user": utente, "imm": immagine, "idUt": idUser},
			function (data){
			prendiImmagineAnnotazione(campagnaSelezionata);
	})
}


















function resetCanvas() {
	// cancella il contenuto del canvas
	context.clearRect(0, 0, canvas.width, canvas.height);

	// reinserisci l'immagine
	context.drawImage(imageSource, 0, 0, imageSource.naturalWidth, imageSource.naturalHeight,  // source rectangle
			0, 0, canvas.width, canvas.height);  // destination rectangle

	// resetta le variabili
	lastIndex = -1;
	clickX = new Array();
	clickY = new Array();
	clickDrag = new Array();
	paint = false;
}

function sendAnnotation(idworker) {
	if (lastIndex == -1) {
		alert("You did not draw the skyline!");
		return;
	}	

	var annotation = {
			worker: idworker,
			width: canvas.width,
			height: canvas.height,
			x: clickX,
			y: clickY,
			drag: clickDrag
	};
	var jsonAnnotation = JSON.stringify(annotation);

	$.post("Annotazione", {
		"json_annotation": jsonAnnotation,
		"campaign": campagna,
		"image_name": immagine,
		"immagine": imm
	}, function (data){

		campagnaGiaAnnotata();
	})
}

function campagnaGiaAnnotata(){
	$.post("servlet10", {"campagna": campagna}, function (data){

		var d = data;
		var res = data.split("@");
		immagine = res[0];
		linea = res[1];
		var log = immagine+"@"+campagna+"@"+linea;
		if(immagine == "vuoto"){
			immagine = "images/stop.png";
			document.getElementById("10").setAttribute("src", immagine);
			document.getElementById("10").setAttribute("name", log);
			document.getElementById("11").style.display="none";
			document.getElementById("12").style.display="none";
		}else{
			var log = immagine+"@"+campagna;
			document.getElementById("10").setAttribute("src", immagine);
			document.getElementById("10").setAttribute("name", log);
		}
	})
}