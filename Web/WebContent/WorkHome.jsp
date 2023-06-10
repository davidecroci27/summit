<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ page import="server.Utente" %>
<%@ page import="server.Database" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="server.Campagna"%>
<html>
<%
Utente User=(Utente)session.getAttribute("Utente");
%>
	<head>
		<title>Summit - Home</title>
		<link rel="icon" href="images/logo.png">
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<link rel="stylesheet" href="assets/css/mainWorkHome.css">
		<link rel="stylesheet" href="assets/css/noscript.css">
	</head>
	<body>
	<p id="idUtente" style="visibility: hidden"><%out.print(User.GetId());%></p>
	<%Database db = new Database();%>
		<!-- Wrapper -->
			<div id="wrapper">

				<!-- Header -->
					<header id="header">
					<div class="logo">
							<img src="images/logo.png" style="height: 100%; width: 100%; object-fit: contain">
					</div>
					<h1 id="usernameTitle" style="margin-top: 20px; margin-bottom: 0px;"><%out.print(User.GetUsername());%></h1>
					<div class="content" style="margin-top: 0px;">
						<div class="inner">
							<nav>
								<ul>
									<li><a href="#CampagneAttive">     Campagne Attive     </a></li>
								</ul>
							</nav>							
							<nav>
								<ul style="margin-top:20px;">
									<li><a href="#statistiche" onclick="statisticheWorker('<%=User.GetNome()%>')">Visualizza Statistiche </a></li>
								</ul>
							</nav>
							<nav>
								<ul style="margin-top:20px;">
									<li><a href="Index.jsp">           Logout             </a></li>
								</ul>
							</nav>
						</div>
					</div>
					</header>

				<!-- Main -->
					<div id="main">

						<!-- Campagne Attive -->
							<article id="CampagneAttive">
							<h2 class="major">Campagne Attive</h2>
							<%
							ArrayList<Campagna> Campagne = db.caricaListaCampagneWorker(User.GetId());
							%>
								<table style="width=100%">
									<tr style="background-color:#f2f2f2">
										<th style="color:black; font-weight:bold; font-size:1.1rem"><center>Nome Campagna</th>
										<th style="color:black; font-weight:bold; font-size:1.1rem"><center>Selezione</th>
										<th style="color:black; font-weight:bold; font-size:1.1rem"><center>Annotazione</th>
									</tr>
  									<%for(Campagna i:Campagne) {%>
  									<tr>
    									<td><center><%=i.GetNome()%></td>
    									<%if(i.GetStato()){%>
    									<td><center><%if(i.GetSelettore()){%><a href="#selezione" class="button" onclick="prendiImmagineSelezione('<%=i.GetNome()%>')">DISPONIBILE</a><%}else{%>Non abilitato<%}%></td>
   										<td><center><%if(i.GetAnnotatore()){%><a href="#annotazione" class="button" onclick="prendiImmagineAnnotazione('<%=i.GetNome()%>')">DISPONIBILE</a><%}else{%>Non abilitato<%}%></td>
  										<%}else{%>
  										<td><center>Non attiva</td>
  										<td><center>Non attiva</td>
  									</tr>
  									<%}}%>
								</table>
								<a href="#" class="button">Indietro</a>
							</article>

						<!-- Selezione -->
							<article id="selezione" style="width:100%">
								<h2 class="major">Selezione</h2>
									<center><img id="immagineSelezione" style="width:700px">
									<p id="fineImmagini" style="visibility: hidden;">Hai già selezionato tutte le immagini per questa campagna.</p>
									<p id="domanda">Questa immagine contiene il profilo di una montagna?</p>
									<div>
										<a id="tastoSi" href="#selezione" class="button" onclick="accetta()">SI</a>
										<a id="tastoNo" href="#selezione" class="button" onclick="rifiuta()">NO</a>
									</div>
									<a href="#CampagneAttive" class="button" style="margin-top:20px">Annulla</a>			
							</article>

						<!-- Annotazione -->
							<article id="annotazione" style="width:100%">
								<h2 class="major">Annotazione</h2>
								<center>
								<canvas id="annotation_canvas" width=700px></canvas>
									<img id="immagineAnnotazione" align="center">
								<p id="mexAnn" style="visibility: hidden;"></p>
								<a href="#annotazione" class="button" onclick="salvaAnnotazione()">Conferma</a>
								<a class="button" onclick="resetCanvas()">Cancella</a>
							</article>

						<!-- Statistiche -->
							<article id="statistiche">
								<h2 class="major">Statistiche di <%out.print(User.GetUsername());%></h2>
								<table>
									<tr>
										<td>Numero di immagini approvate:</td>
										<td id="nImmApp"></td>
    								</tr>
    								<tr>
										<td>Numero di immagini rifiutate:</td>
										<td id="nImmRif"></td>
    								</tr>
    								<tr>
										<td>Numero di immagini annotate:</td>
										<td id="nImmAnn"></td>
    								</tr>
    								<tr>
										<td>Numero di immagini ancora da approvare:</td>
										<td id="nImmDaApp"></td>
    								</tr>
    								<tr>
										<td>Numero di immagini ancora da annotare:</td>
										<td id="nImmDaAnn"></td>
    								</tr>
								</table>
								<a href="#" class="button">Indietro</a>
							</article>
					</div>

				<!-- Footer -->
					<footer id="footer">
						<p class="copyright">&copy;Summit</p>
					</footer>
			</div>

		<!-- BG -->
			<div id="bg"></div>

		<!-- Scripts -->
			<script type="text/javascript">
			function prendiImmagineSelezione(camp){
				$.post("servlet5", {"campagna": camp}, function (data){	
					var Imm = data;
					if(Imm == "vuoto"){
						document.getElementById("fineImmagini").setAttribute("style", "");
						document.getElementById("domanda").setAttribute("style", "visibility: hidden;");
						document.getElementById("tastoSi").setAttribute("style", "visibility: hidden;");
						document.getElementById("tastoNo").setAttribute("style", "visibility: hidden;");
						document.getElementById("immagineSelezione").setAttribute("src", "");
					}else{
						document.getElementById("fineImmagini").setAttribute("style", "visibility: hidden;");
						document.getElementById("domanda").setAttribute("style", "");
						document.getElementById("tastoSi").setAttribute("style", "");
						document.getElementById("tastoNo").setAttribute("style", "");
						document.getElementById("immagineSelezione").setAttribute("src", Imm);
						document.getElementById("immagineSelezione").setAttribute("name", camp);
					}
				})
			}
			
			function accetta(){
				var camp = document.getElementById("immagineSelezione").name;
				var pathImm = document.getElementById("immagineSelezione").src;
				$.post("servlet6", {"campagna": camp, "path": pathImm}, function (data){
					var Imm = data;
					if(Imm == "vuoto"){
						document.getElementById("fineImmagini").setAttribute("style", "");
						document.getElementById("domanda").setAttribute("style", "visibility: hidden;");
						document.getElementById("tastoSi").setAttribute("style", "visibility: hidden;");
						document.getElementById("tastoNo").setAttribute("style", "visibility: hidden;");
					}else{
						document.getElementById("immagineSelezione").setAttribute("src", Imm);
					}
				})
			}
			
			function rifiuta(){
				var camp = document.getElementById("immagineSelezione").name;
				var pathImm = document.getElementById("immagineSelezione").src;
				$.post("servlet7", {"campagna": camp, "path": pathImm}, function (data){
					var Imm = data;
					if(Imm == "vuoto"){
						document.getElementById("fineImmagini").setAttribute("style", "");
						document.getElementById("domanda").setAttribute("style", "visibility: hidden;");
						document.getElementById("tastoSi").setAttribute("style", "visibility: hidden;");
						document.getElementById("tastoNo").setAttribute("style", "visibility: hidden;");
					}else{
						document.getElementById("immagineSelezione").setAttribute("src", Imm);
					}
				})
			}
			
			function statisticheWorker(stringa){
				var nome = stringa;
				$.post("servlet12", {"nomeUtente": nome}, function (data){
					var comma = data.indexOf(",");
					var at = data.indexOf("@");
					var hash = data.indexOf("#");
					var underscore = data.indexOf("_");
					var data1 = data.slice(0, comma);
					var data2 = data.slice(comma+1, at);
					var data3 = data.slice(at+1, hash);
					var data4 = data.slice(hash+1, underscore);
					var data5 = data.slice(underscore+1, data.lenght);
					document.getElementById("nImmApp").innerHTML = data1;
					document.getElementById("nImmRif").innerHTML = data2;
					document.getElementById("nImmAnn").innerHTML = data3;
					document.getElementById("nImmDaApp").innerHTML = data4;
					document.getElementById("nImmDaAnn").innerHTML = data5;
				});
			}
			
			</script>
			<script src="assets/js/jquery.min.js"></script>
			<script src="assets/js/workHomeScript.js"></script>
			<script src="assets/js/skel.min.js"></script>
			<script src="assets/js/util.js"></script>
			<script src="assets/js/main.js"></script>
	</body>
</html>
