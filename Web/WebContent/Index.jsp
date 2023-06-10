<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
<%
Boolean esitoUser=(Boolean)session.getAttribute("esitouser");
Boolean esitoPw=(Boolean)session.getAttribute("esitopw");
Boolean RegUtente=(Boolean)session.getAttribute("UsernameGiaEsistente");
Boolean RegMail=(Boolean)session.getAttribute("MailGiaEsistente");
%>
	<head>
		<title>Summit</title>
		<link rel="icon" href="images/logo.png">
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/css/main.css" />
		<link rel="stylesheet" href="assets/css/noscript.css">
	</head>
	<body>

		<!-- Wrapper -->
			<div id="wrapper">

				<!-- Header -->
					<header id="header">
						<div class="logo">
							<img src="images/logo.png" style="height: 100%; width: 100%; object-fit: contain">
						</div>
						<div class="content">
							<div class="inner">
								<h1>Summit</h1>
								<p>Partecipa all'analisi unendoti ad un gruppo <br>
								oppure crea personalmente la tua campagna
								</p>
							</div>
						</div>
						<nav>
							<ul>
								<li><a href="#login">    Login   </a></li>
								<li><a href="#registrazione">Registrati</a></li>
							</ul>
						</nav>
					</header>

				<!-- Main -->
					<div id="main">
					<!-- Login -->
					     <article id="login">
								<h2 class="major">Login</h2>
								<form action="servlet1" method="post">
									<div class="field half first">
										<input type="text" class="input" name="username" id="username" placeholder="Username" required="required"
										<% if(esitoUser!=null)
												out.print("style='border-color: #ff0000;'");
										%>/>
									</div>
									<div class="field half">
										<input type="password" class="input" name="password" id="pass" placeholder="Password" required="required" 
										<% if(esitoPw!=null)
											   	out.print("style='border-color: #ff0000;'");
										%>/>
									</div>
									<ul class="actions">
										<li><input type="submit" value="Login" class="special" /></li>
										<% if(esitoUser!=null){
												out.print("<li><h2 style='color:red;margin-top:15px;font-size:1rem;letter-spacing:0rem'>"
												+"           Non esiste nessun account con questo Username</h2></li>");
										}
										if(esitoPw!=null){
											out.print("<li><h2 style='color:red;margin-top:15px;font-size:1rem;letter-spacing:0rem'>"
											+"                                                                            Password errata</h2></li>");
										}
										%>
								</ul>
							</form>
						</article>
				   <!-- Registrazione -->
					    <article id="registrazione">
								<h2 class="major">Registrazione</h2>
								<form action="servlet2" method="post" autocomplete="off">
									<div class="field">
										<input id="user" type="text" class="input" name="username"
										<%if(RegUtente!=null)
												out.print("placeholder='Esiste già un account registrato con questo username!'");
										%>
										placeholder="Username" required="required"
										<% if(RegUtente!=null)
												out.print("style='border-color: #ff0000;'");
										%>/>
									</div>
									<div class="field">
										<input id="mail" type="email" class="input" name="mail"
										<% if(RegMail!=null)
											out.print("placeholder='Esiste già un account registrato con questa mail!'");
										%>
										placeholder="Indirizzo E-Mail" required="required"	
										<% if(RegMail!=null)
												out.print("style='border-color: #ff0000;'");
										%>/>
									</div>
									<div class="field">
										<input id="pass2" type="password" class="input" data-type="password" name="password2" placeholder="Password" required="required"/>
									</div>
									<div class="field">
										<input id="confirm_password" type="password" class="input" data-type="password" style="" placeholder="Ripeti Password" required="required"/>
									</div>
									<div class="field">
										<input id="name" type="text" class="input" name="nome" placeholder="Nome" required="required"/>
									</div>
									<div class="field">
										<input id="cognome" type="text" class="input" name="cognome" placeholder="Cognome" required="required"/>
									</div>
									<div class="group" style="width:200px">
										<label for="sesso">Sesso</label>
										<input id="maschio" name="sesso" type="radio" value="m" required="required">
										<label for="maschio">M</label>
										<input id="femmina" name="sesso" type="radio" value="f" required="required">
										<label for="femmina">F</label>
									</div>
									<div class="group" style="width:200px">
									<label for="gestore"><br>Gestore</label>
										<input id="gestore" class="check" type="checkbox" name="gestore">
										<label for="gestore"></label>
										</div>
									<ul class="actions">
										<li><input type="submit" value="Conferma" class="special" /></li>
									</ul>
								</form>
							</article>
						<!-- Contact -->
							<article id="contact">
								<h2 class="major">Contattaci</h2>
								<form action="mailto:davidecroci27@gmail.com" method="post" enctype="text/plain">
									<div class="field half first">
										<input type="text" name="name" id="name" placeholder="Nome" required="required"/>
									</div>
									<div class="field half">
										<input type="email" name="email" id="email" placeholder="Indirizzo E-Mail" required="required"/>
									</div>
									<div class="field">
										<textarea name="message" id="message" rows="4" placeholder="Scrivi qui il tuo messaggio" required="required"></textarea>
									</div>
									<ul class="actions">
										<li><input type="submit" value="Invia Messaggio" class="special" /></li>
										<li><input type="reset" value="Reset" /></li>
									</ul>
								</form>
								<ul class="icons">
									<li><a href="#" class="icon fa-twitter"><span class="label">Twitter</span></a></li>
									<li><a href="https://www.facebook.com/yavapais" class="icon fa-facebook"><span class="label">Facebook</span></a></li>
									<li><a href="#" class="icon fa-instagram"><span class="label">Instagram</span></a></li>
									<li><a href="#" class="icon fa-github"><span class="label">GitHub</span></a></li>
								</ul>
							</article>
					</div>

				<!-- Footer -->
					<footer id="footer">
						<p><a href="#contact">Contattaci</a></p>
					</footer>

			</div>

		<!-- BG -->
			<div id="bg"></div>

	<!-- Scripts -->
<%
session.removeAttribute("esitouser");
session.removeAttribute("esitopw");
session.removeAttribute("UsernameGiaEsistente");
session.removeAttribute("MailGiaEsistente");
%>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/skel.min.js"></script>
	<script src="assets/js/util.js"></script>
	<script src="assets/js/main.js"></script>
	<script type="text/javascript">
		var password = document.getElementById("pass2"), confirm_password = document.getElementById("confirm_password");
		function validatePassword() {
			if (password.value != confirm_password.value) {
				confirm_password.setCustomValidity("Le password non corrispondono!");
			} else {
				confirm_password.setCustomValidity('');
			}
		}
		password.onchange = validatePassword;
		confirm_password.onkeyup = validatePassword;
	</script>
</body>
</html>
    