package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.Database;

@WebServlet("/StatisticheCampagnaWork")
public class StatisticheCampagnaWork extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StatisticheCampagnaWork() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = new Database();
		try {
			ArrayList<Integer> dati = new ArrayList<Integer>(db.statisticheWorker(request.getParameter("nomeUtente")));
			String data = dati.get(0).toString()+","+dati.get(1).toString()+"@"+dati.get(2).toString()+"#"+dati.get(3).toString()+"_"+dati.get(4).toString();
			response.getWriter().write(data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
