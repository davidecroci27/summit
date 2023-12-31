package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.Database;

@WebServlet("/AttivazioneCampagna")
public class AttivazioneCampagna extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AttivazioneCampagna() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		Database db = new Database();
		String nomeCampagna = request.getParameter("ncampagna");
		try {
			db.attivaCampagna(nomeCampagna);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
}
