package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.Database;
import server.Utente;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;    
    
    Database db = new Database();
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente Logga=db.LoginUtente(request.getParameter("username"), request.getParameter("password"));

		switch(Logga.GetResponse()){
		case 1:
			response.sendRedirect("Index.jsp#login");
			request.getSession().setAttribute("esitouser", true);
			break;
		case 2:
			response.sendRedirect("Index.jsp#login");
			request.getSession().setAttribute("esitopw", true);
			break;
		case 3:
			if(Logga.GetGestore()){
				response.sendRedirect("GesHome.jsp");
				request.getSession().setAttribute("Utente", Logga);
			}else{
				response.sendRedirect("WorkHome.jsp");
				request.getSession().setAttribute("Utente", Logga);
			}
			break;
		}
	}
}
