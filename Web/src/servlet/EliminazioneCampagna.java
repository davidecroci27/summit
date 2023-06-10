package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.Database;

@WebServlet("/EliminazioneCampagna")
public class EliminazioneCampagna extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EliminazioneCampagna() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = new Database();
		String nomeCampagna = request.getParameter("campagnaElimina");
		try {
			db.eliminaCampagna(nomeCampagna);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
