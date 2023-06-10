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

@WebServlet("/StatisticheCampagnaGes")
public class StatisticheCampagnaGes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StatisticheCampagnaGes() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = new Database();
		try {
			ArrayList<Float> dati = new ArrayList<Float>(db.statisticheCamp(request.getParameter("nomeCamp")));
			int dato1 = (int) (dati.get(0)/1);
			int dato2 = (int) (dati.get(1)/1);
			int dato3 = (int) (dati.get(2)/1);
			int dato4 = (int) (dati.get(3)/1);
			String data = Integer.toString(dato1)+","+Integer.toString(dato2)+"@"+Integer.toString(dato3)+"#"+Integer.toString(dato4)+"_"+dati.get(4).toString();
			response.getWriter().write(data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
