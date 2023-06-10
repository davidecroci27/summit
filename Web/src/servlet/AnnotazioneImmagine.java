package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import server.Database;
import server.Utente;

@WebServlet("/AnnotazioneImmagine")
public class AnnotazioneImmagine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AnnotazioneImmagine() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String nomeCampagna = request.getParameter("campagnaAnn");
		Database db = new Database();
		int utente = ((Utente) session.getAttribute("Utente")).GetId();
		String data = null;
		try {
			data = db.immAnnotazione(utente,nomeCampagna);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.getWriter().write(data);
	}
}
