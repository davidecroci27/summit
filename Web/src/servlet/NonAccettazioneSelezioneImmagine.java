package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import server.Utente;
import server.Database;

@WebServlet("/NonAccettazioneSelezioneImmagine")
public class NonAccettazioneSelezioneImmagine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NonAccettazioneSelezioneImmagine() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = new Database();
		HttpSession session = request.getSession();
		String nomeCampagna = request.getParameter("campagna");
		String path = request.getParameter("path");
		if(path.contains("%20")){
			path = path.replaceAll("%20", " ");
		}
		if(path.startsWith("http://localhost:8085/Web/")){
			path = path.replaceAll("http://localhost:8085/Web/", "");
		}
		System.out.println(path);
		int utente = ((Utente) session.getAttribute("Utente")).GetId();
		String data = null;
		try {
			data = db.rifiutaERitornaNuovaImmSelezione(path, utente, nomeCampagna);
			db.cambiaStatoAccettazioneSelezione(path, nomeCampagna);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(data);
		response.getWriter().write(data);	
	}

}
