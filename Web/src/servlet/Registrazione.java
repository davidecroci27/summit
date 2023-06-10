package servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.Database;
import server.Utente;

@WebServlet("/Registrazione")
public class Registrazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Registrazione() {
        super();
    }
    
    Database db = new Database();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean Sex=true, Ges=true;
		String Sesso=request.getParameter("sesso");
		String Gestore=request.getParameter("gestore");
		if(Sesso.equals("f"))
			Sex=false;
		if(Gestore==null)
			Ges=false;
		try{
			Utente Registra=db.RegistraUtente(request.getParameter("username"), request.getParameter("mail"), request.getParameter("password2"),
					request.getParameter("nome"), request.getParameter("cognome"), Sex, Ges);
			switch(Registra.GetResponse()){
			case 1:
				response.sendRedirect("Index.jsp#registrazione");
				request.getSession().setAttribute("UsernameGiaEsistente", true);
				break;
			case 2:
				response.sendRedirect("Index.jsp#registrazione");
				request.getSession().setAttribute("MailGiaEsistente", true);
				break;
			case 3:
				response.sendRedirect("Index.jsp#registrazione");
				request.getSession().setAttribute("UsernameGiaEsistente", true);
				request.getSession().setAttribute("MailGiaEsistente", true);
				break;
			case 4:
				response.sendRedirect("WorkHome.jsp");
				request.getSession().setAttribute("Utente", Registra);
				break;
			case 5:
				response.sendRedirect("GesHome.jsp");
				request.getSession().setAttribute("Utente", Registra);
				break;
			}
			return;	
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
