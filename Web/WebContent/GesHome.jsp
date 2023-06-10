<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ page import="server.Campagna"%>
<%@ page import="server.Utente"%>
<%@ page import="server.Database" %>
<%@ page import="java.util.ArrayList" %>
<html>
<%
Utente User=(Utente)session.getAttribute("Utente");
%>
<head>
	<title>Summit - Home</title>
	<link rel="icon" href="images/logo.png">
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<link rel="stylesheet" href="assets/css/mainGesHome.css"/>
	<link rel="stylesheet" href="assets/css/noscript.css">
<style>
.popup {
	position: relative;
	display: inline-block;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}

.popup .popuptext {
	visibility: hidden;
	width: 160px;
	background-color: #555;
	color: #fff;
	text-align: center;
	border-radius: 6px;
	padding: 8px 0;
	position: absolute;
	z-index: 1;
	bottom: 125%;
	left: 50%;
	margin-left: -80px;
}

.popup .popuptext::after {
	content: "";
	position: absolute;
	top: 100%;
	left: 50%;
	margin-left: -5px;
	border-width: 5px;
	border-style: solid;
	border-color: #555 transparent transparent transparent;
}

.popup .show {
	visibility: visible;
	-webkit-animation: fadeIn 1s;
	animation: fadeIn 1s;
}

@-webkit-keyframes fadeIn {
	from {opacity: 0;
	}

	to {
	opacity: 1;
	}

}

@keyframes fadeIn {
	from {opacity: 0;
	}

	to {
	opacity: 1;
	}
}

input[type="number"]{
	-moz-appearance: none;
	-webkit-appearance: none;
	-ms-appearance: none;
	appearance: none;
	-moz-transition: border-color 0.2s ease-in-out, box-shadow 0.2s ease-in-out, background-color 0.2s ease-in-out;
	-webkit-transition: border-color 0.2s ease-in-out, box-shadow 0.2s ease-in-out, background-color 0.2s ease-in-out;
	-ms-transition: border-color 0.2s ease-in-out, box-shadow 0.2s ease-in-out, background-color 0.2s ease-in-out;
	transition: border-color 0.2s ease-in-out, box-shadow 0.2s ease-in-out, background-color 0.2s ease-in-out;
	background: transparent;
	margin-right:300px;
	width:509px;
	height:2.75rem;
	border-radius:4px;
	border: solid 1px #ffffff;
	color: inherit;
		display: block;
		outline: 0;
		padding: 0 1rem;
		text-decoration: none;
}

input[type="number"]:focus {
	background: rgba(255, 255, 255, 0.075);
	border-color: #ffffff;
	box-shadow: 0 0 0 1px #ffffff;
	}

</style>
</head>
	<body>
	<%Database db = new Database();%>
		<!-- Wrapper -->
			<div id="wrapper">

				<!-- Header -->
					<header id="header">
					<div class="logo">
						<img src="images/logo.png" style="height: 100%; width: 100%; object-fit: contain">
					</div>
						<h1 style="margin-top: 20px; margin-bottom: 0px;"><%out.print(User.GetUsername());%></h1>
						<div class="content" style="margin-top: 0px;">
							<div class="inner">
							<nav>
							<ul>
							<li><a href="#nuovacampagna">  Nuova campagna  </a></li>
							<li><a href="#campagne">Le mie campagne  </a></li>
							</ul>
							</nav>
							<nav>
							<ul style="margin-top:20px;">
							<li><a href="#ModificaCampagna" onclick="location.reload()">Modifica campagna</a></li>
							<li><a href="#EliminaCampagna" onclick="">Elimina campagna</a></li>
							</ul>
							</nav>
							<nav>
							<ul style="margin-top:20px;">
							<li><a href="Index.jsp">                           Logout                         </a></li>
							</ul>
							</nav>
							</div>
						</div>
					</header>

				<!-- Main -->
					<div id="main">
						<!-- Nuova Campagna -->
							<article id="nuovacampagna">
								<h2 class="major">Nuova Campagna</h2>
								<form enctype="multipart/form-data" action="servlet3" method="post" autocomplete="off">
								<div class="field">
									<div style="float:left;">
										<input id="namecampaign" type="text" class="input" name="nomecampagna" placeholder="Nome Campagna" required="required" style="margin-right:300px;"/>
									</div>
								</div>
								<div class="field">
									<div style="float:left;margin-top:20px;">
										<input id="numerotask" type="number" class="input" name="ntask" min="1" placeholder="Numero minimo di utenti per il task di selezione" required="required" style="margin-right:auto;"/>
									</div>
									<div class="popup" onclick="pop1()" style="float:right;margin-top:20px;">
										<img src="images/info.png" style="width:30px;height:30px;margin-top:6px">
										<span class="popuptext" id="myPopup1">Numero minimo di utenti che devono effettuare il task di selezione per<br> ogni immagine</span>
									</div>
								</div>	
								<div class="field">
									<div style="float:left">
										<input id="numeroutentivalut" type="number" class="input" name="nutval" min="1" placeholder="Numero minimo di utenti per il task di annotazione" required="required"
										style="margin-right:auto;margin-top:20px;margin-bottom:20px;"/>
									</div>
									<div class="popup" onclick="pop3()" style="float:right;margin-top:20px;">
										<img src="images/info.png" style="width:30px;height:30px;margin-top:6px">
										<span class="popuptext" id="myPopup3">Numero minimo di utenti che devono effettuare il task di annotazione per ogni immagine</span>
									</div>
								</div>
								<div class="field">
									<div style="float:left; margin-bottom:25px">
										<input id="numerovalutazioni" type="number" class="input" name="nvalut" min="1" placeholder="Numero di valutazioni positive" required="required" style="margin-right:auto;"/>
									</div>
									<div class="popup" onclick="pop2()" style="float:right">
										<img src="images/info.png" style="width:30px;height:30px;margin-top:8px">
										<span class="popuptext" id="myPopup2">Numero di valutazioni positive che deve ricevere un'immagine nel task Selezione di immagini per poter essere usata nel task Annotazione di immagini. DEVE ESSERE MINORE O UGUALE AL NUMERO DI UTENTI PER LA SELEZIONE.</span>
									</div>
								</div>
								<label>         </label>
								<label style="letter-spacing:0; text-transform: none; font-weight:bold; font-size: 0.9rem;">Adesso seleziona quali utenti sono autorizzati ad eseguire il task di selezione e di annotazione per la tua nuova campagna.<br>
								Ricorda che il numero di utenti di entrambe le categorie deve essere maggiore o uguale a quanto inserito precedentemente.
								</label>
								<% ArrayList<Utente> Workers = db.caricaListaUtenti(false); %>
								<table>
									<tr style="background-color:#f2f2f2">
										<td style="color:black; font-weight:bold; font-size:1.1rem"><center>Lista Workers</td>
										<td style="color:black; font-weight:bold; font-size:1.1rem"><center>Selettori</td>
										<td style="color:black; font-weight:bold; font-size:1.1rem"><center>Annotatori</td>
									</tr>
									<%for(Utente i:Workers) {%>
									<tr>
									<td><center><%=i.GetUsername()%></td>
									<td><center>
										<input name="selettore" id="<%=i.GetId()%>" type="checkbox" class="check" value="<%=i.GetId()%>">
										<label for="<%=i.GetId()%>"></label>
									</td>
									<td><center>
										<input name="annotatore" id="<%=i.GetId()*(-1)%>" type="checkbox" class="check" value="<%=i.GetId()%>">
										<label for="<%=i.GetId()*(-1)%>"></label>
									</td>
									</tr>
									<%} %>
								</table>		
								<input type="file" class="upload" id="upload" name="upload" multiple="multiple" required="required" />
								<ul class="actions">
									<li><input type="submit" value="Conferma" class="special" /></li>
								</ul>
								</form>
							</article>

						<!-- campagne -->
							<article id="campagne" style="width:2080px">
								<h2 class="major">Le mie campagne</h2>
								<% ArrayList<Campagna> Campagne = db.caricaListaCampagne(User.GetId()); %>	
								<table style="width:100%">
  									<tr style="background-color:#f2f2f2">
    									<th style="color:black; font-weight:bold; font-size:1rem"><center>Nome Campagna</th>
    									<th style="color:black; font-weight:bold; font-size:1rem"><center>Numero immagini</th> 
    									<th style="color:black; font-weight:bold; font-size:1rem"><center>Numero selettori</th>
    									<th style="color:black; font-weight:bold; font-size:1rem"><center>Numero annotatori</th>
    									<th style="color:black; font-weight:bold; font-size:1rem"><center>Numero valutazioni positive</th>
    									<th style="color:black; font-weight:bold; font-size:1rem"><center>Stato</th>
    									<th style="color:black; font-weight:bold; font-size:1rem"></th>
  									</tr>
  									<%for(Campagna i:Campagne) {%>
  									<tr>
    									<td><center><%=i.GetNome()%></td>
    									<td><center><%=i.GetNumeroImmagini()%></td> 
   										<td><center><%=i.GetUtTaskSel()%></td>
   										<td><center><%=i.GetUtTaskAnn()%></td>
   										<td><center><%=i.GetValut()%></td>
   										<%if(!i.GetStato()){%>
   										<td  id="<%=i.GetNome()%>"><input type="button" value="ATTIVA" onclick="AttivaCampagna('<%= i.GetNome()%>')"></td>
  										<%}else{%>
  										<td><center>Attivata</td>
  										<%}%>
  										<td><center><a class="button" href="#Stats" onclick="statistiche('<%= i.GetNome()%>')">STATISTICHE</a></td>
  									</tr>
  									<%} %>
									</table>
							</article>
							
							<!-- Modifica Campagna -->
							<article id="ModificaCampagna">
							<h2 class="major">Modifica Campagna</h2>
							<%ArrayList<Campagna> CampagneModifica = db.caricaListaCampagne(User.GetId());%>
							<table>
								<tr style="background-color:#f2f2f2">
    								<th style="color:black; font-weight:bold; font-size:1rem"><center>Nome Campagna</th>
    								<th style="color:black; font-weight:bold; font-size:1rem"><center>Azione</th>
    							</tr>
    							<%for(Campagna i:CampagneModifica) {%>
  								<tr>
    								<td><center><%=i.GetNome()%></td>
    								<%if(!i.GetStato()){%>
    								<td><center><a href="#EditCampagna" class="button" name="<%=i.GetNome()%>" onclick="ModificaCampagna('<%=i.GetNome()%>')">MODIFICA</a></td>
    								<%}else{%>
    								<td><center>Non editabile</td>
    								<%}%>
    							</tr>
    							<%}%>
							</table>	
							</article>
							
						<!-- Edit Campagna -->
						<article id="EditCampagna" name="EditCamp">
						<p style="font-weight:bold">Se non intendi modificare un parametro tra quelli sottoelencati, <span style="color:red">impostalo a 0</span></p>
						<form enctype="multipart/form-data" action="servlet8" method="post" autocomplete="off">
						<input id="nuovonumerotask" type="number" class="input" name="nNEWtask" min="0" placeholder="Nuovo numero minimo di utenti per il task di selezione" style="margin-right:auto;margin-top:15px" required="required"/>
						<input id="nuovonumeroutentivalut" type="number" class="input" name="nNEWutval" min="0" placeholder="Nuovo numero minimo di utenti per il task di annotazione"
										style="margin-right:auto;margin-top:20px;margin-bottom:20px;" required="required"/>
						<input id="nuovonumerovalutazioni" type="number" class="input" name="nNEWvalut" min="0" placeholder="Nuovo numero di valutazioni positive" style="margin-right:auto;margin-bottom:20px;" required="required"/>
						<input id="campagnaDaPassare" name="cDaPassare" class="input" style="visibility: hidden;"/>
						<p style="font-weight:bold">Seleziona gli utenti da abilitare</p>
						<p><span style="color:red; font-weight:bold">NOTA:</span> se non specificamente modificato nei campi qui sopra, il numero di selettori o annotatori deve corrispondere a quello rispettivamente impostato alla creazione della campagna.
						Se invece hai modificato il numero di una delle due categorie, devi necessariamente riselezionare gli utenti da abilitare della rispettiva tipologia.</p>
						<% ArrayList<Utente> NewWorkers = db.caricaListaUtenti(false); %>
						<table>
							<tr style="background-color:#f2f2f2">
								<td style="color:black; font-weight:bold; font-size:1.1rem"><center>Lista Workers</td>
								<td style="color:black; font-weight:bold; font-size:1.1rem"><center>Selettori</td>
								<td style="color:black; font-weight:bold; font-size:1.1rem"><center>Annotatori</td>
							</tr>
							<%for(Utente i:NewWorkers) {%>
							<tr>
							<td><center><%=i.GetUsername()%></td>
							<td><center>
								<input name="newSelettore" id="<%=i.GetId()+10000%>" type="checkbox" class="check" value="<%=i.GetId()%>">
								<label for="<%=i.GetId()+10000%>"></label>
							</td>
							<td><center>
								<input name="newAnnotatore" id="<%=i.GetId()*(-1)+10000%>" type="checkbox" class="check" value="<%=i.GetId()%>">
								<label for="<%=i.GetId()*(-1)+10000%>"></label>
							</td>
							</tr>
							<%} %>
						</table>
						<ul class="actions">
							<li style="margin-right:290px"><input type="submit" value="Conferma" class="special" /></li>
							<li><a href="#ModificaCampagna" class="button" onclick="BackToModifica()" style="float:right">Indietro</a></li>
						</ul>
						</form>
						</article>
						
						<!-- Elimina Campagna -->
							<article id="EliminaCampagna">
								<h2 class="major">Elimina campagna</h2>
								<% ArrayList<Campagna> CampagneElim = db.caricaListaCampagne(User.GetId()); %>
								<table>
									<tr style="background-color:#f2f2f2">
    									<th style="color:black; font-weight:bold; font-size:1rem"><center>Nome Campagna</th>
    									<th style="color:black; font-weight:bold; font-size:1rem"><center></th>
    								</tr>
    								<%for(Campagna j:CampagneElim) {%>
  									<tr>
    									<td><center><%=j.GetNome()%></td>
    									<td><center><a href="#CampagnaEliminata" class="button" onclick="eliminaCampagna('<%=j.GetNome()%>')">ELIMINA</a></td>
    								</tr>
    								<%}%>
								</table>	
							</article>
							
							<!-- Statistiche Campagna -->
							<article id="Stats" style="width:2080px">
								<h2 class="major" id="campStat"></h2>
								<table>
									<tr>
										<td>Numero di immagini della campagna:</td>
										<td id="nImmTot"></td>
    								</tr>
    								<tr>
										<td>Numero di immagini approvate:</td>
										<td id="nImmApp"></td>
    								</tr>
    								<tr>
										<td>Numero di immagini rifiutate:</td>
										<td id="nImmRif"></td>
    								</tr>
    								<tr>
										<td>Numero di immagini annotate da almeno un utente:</td>
										<td id="nImmAnn"></td>
    								</tr>
    								<tr>
										<td>Numero medio di annotazioni per immagine:</td>
										<td id="nAvgAnn"></td>
    								</tr>
								</table>
								<table style="margin-top:90px">
									<tr style="background-color:#f2f2f2">
    									<th style="color:black; font-weight:bold; font-size:1rem"><center>Nome immagine</th>
    									<th style="color:black; font-weight:bold; font-size:1rem"><center>Stato approvazione<center></th>
    									<th style="color:black; font-weight:bold; font-size:1rem"><center>Voti positivi<center></th>
    									<th style="color:black; font-weight:bold; font-size:1rem"><center>Voti negativi<center></th>
    									<th style="color:black; font-weight:bold; font-size:1rem"><center>Numero annotazioni<center></th>
    								</tr>
    								<tr>
    									<td></td>
    									<td></td>
    									<td></td>
    									<td></td>
    									<td></td>
    								</tr>
								</table>
								<a href="#campagne" class="button" style="margin-top:30px">Indietro</a>
							</article>

						<!-- Nuova Campagna Aggiunta -->
							<article id="NuovaCampagnaAggiunta">
								<h2 class="major">Nuova Campagna Aggiunta!</h2>
								<a href="#campagne" class="button">Vai alle tue campagne</a>		
							</article>
						
						<!-- Campagna Modificata -->
							<article id="CampagnaModificata">
								<h2 class="major">Campagna modificata!</h2>
								<a href="#campagne" class="button">Vai alle tue campagne</a>		
							</article>
							
						<!-- Campagna Eliminata -->
							<article id="CampagnaEliminata">
								<h2 class="major">Campagna Eliminata!</h2>
								<a href="#EliminaCampagna" class="button">Continua</a>		
							</article>	
							
						<!-- Errore Campagna Modificata -->
						<article id="ErrModificaCampagna">
							<h2 class="major" style="color:red">ERRORE!</h2>
							<p>Inserimento non valido. Controlla di aver rispettato le condizioni di modifica.</p>
							<a href="#ModificaCampagna" class="button">Continua</a>		
						</article>

						<!-- Errore numero selettori/annotatori -->
							<article id="ErroreSelAnn">
								<h2 class="major" style="color:red">ERRORE!</h2>
								<p>Devi selezionare un numero di selettori e annotatori maggiore o uguale a quanto indicato nei campi richiesti.</p>
								<a href="#nuovacampagna" class="button">Continua</a>
							</article>

						<!-- Errore nome campagna esistente -->
							<article id="ErroreNomeCampagna">
								<h2 class="major" style="color:red">ERRORE!</h2>
								<p>Esiste già una campagna con questo nome.</p>
								<a href="#nuovacampagna" class="button">Continua</a>
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
	<script>
		function pop1() {
		    var popup = document.getElementById("myPopup1");
		    popup.classList.toggle("show");
		}
		function pop2() {
		    var popup = document.getElementById("myPopup2");
		    popup.classList.toggle("show");
		}
		function pop3() {
		    var popup = document.getElementById("myPopup3");
		    popup.classList.toggle("show");
		}
		function AttivaCampagna(nome) {
			var stringa = nome;
			$.post("servlet4", {"ncampagna": stringa},{});
			document.getElementById(stringa).innerText+="Attivata";
		}
		function ModificaCampagna(nome){
			document.getElementById("campagnaDaPassare").setAttribute("value",nome);
		}
		function BackToModifica(){
			document.getElementById("EditCampagna").setAttribute("value","");
		}
		function eliminaCampagna(camp){
			var nCampE = camp;
			$.post("servlet10", {"campagnaElimina": nCampE}, {})
			location.reload();
		}
		function statistiche(stringa){
			var nome = stringa;
			document.getElementById("campStat").innerHTML = nome;
			$.post("servlet11", {"nomeCamp": nome}, function(data){
				var comma = data.indexOf(",");
				var at = data.indexOf("@");
				var hash = data.indexOf("#");
				var underscore = data.indexOf("_");
				var data1 = data.slice(0, comma);
				var data2 = data.slice(comma+1, at);
				var data3 = data.slice(at+1, hash);
				var data4 = data.slice(hash+1, underscore);
				var data5 = data.slice(underscore+1, data.lenght);
				document.getElementById("nImmTot").innerHTML = data1;
				document.getElementById("nImmApp").innerHTML = data2;
				document.getElementById("nImmRif").innerHTML = data3;
				document.getElementById("nImmAnn").innerHTML = data4;
				document.getElementById("nAvgAnn").innerHTML = data5;
			})
		}
		</script>		
	<script src="assets/js/jquery.min.js"></script>
			<script src="assets/js/skel.min.js"></script>
			<script src="assets/js/util.js"></script>
			<script src="assets/js/main.js"></script>

	</body>
</html>
