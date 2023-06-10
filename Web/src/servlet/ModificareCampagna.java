package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.Database;

@WebServlet("/ModificareCampagna")
@MultipartConfig
public class ModificareCampagna extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ModificareCampagna() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = new Database();
		int n = -1;
		int m = -1;
		int k = -1;
		String nomeC = request.getParameter("cDaPassare");
		n = Integer.parseInt(request.getParameter("nNEWtask"));
		m = Integer.parseInt(request.getParameter("nNEWutval"));
		k = Integer.parseInt(request.getParameter("nNEWvalut"));
		String[] selettori = request.getParameterValues("newSelettore");
		String[] annotatori = request.getParameterValues("newAnnotatore");
		if(n!=0){
			if(selettori!=null){
				if(k!=0){
					if(n<k){
						response.sendRedirect("GesHome.jsp#ErrModificaCampagna");
					}else{
						if(selettori.length!=n){
							response.sendRedirect("GesHome.jsp#ErrModificaCampagna");
						}else{
							//aggiorna n e k nel database e nuovi selettori
							try {
								db.aggiornaNeK(nomeC,n,k);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							try {
								db.pulisciSelettori(nomeC);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							for(int l=0;l<selettori.length;l++){
								try {
									db.aggiornaSelettori(Integer.parseInt(selettori[l]), nomeC);
								} catch (NumberFormatException | SQLException e) {
									e.printStackTrace();
								}
							}
							try {
								db.eliminaInutili();
							} catch (SQLException e) {
								e.printStackTrace();
							}
							try {
								db.accorpaWorkers();
							} catch (SQLException e) {
								e.printStackTrace();
							}
							response.sendRedirect("GesHome.jsp#CampagnaModificata");
						}
					}
				}else{
					//trova k nel database e paragona con nuovo n
					try {
						if(db.verificaNMaggioreDiK(n, nomeC)){
							//aggiorna solo n e nuovi selettori
							db.aggiornaN(nomeC, n);
							db.pulisciSelettori(nomeC);
							for(int l=0;l<selettori.length;l++){
								try {
									db.aggiornaSelettori(Integer.parseInt(selettori[l]), nomeC);
								} catch (NumberFormatException | SQLException e) {
									e.printStackTrace();
								}
							}
							db.eliminaInutili();
						}else{
							response.sendRedirect("GesHome.jsp#ErrModificaCampagna");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}else{
				response.sendRedirect("GesHome.jsp#ErrModificaCampagna");
			}	
		}else{
			if(selettori!=null){
				try {
					if(db.trovaN(nomeC)==selettori.length){
						db.pulisciSelettori(nomeC);
						for(int i=0;i<selettori.length;i++){
							db.aggiornaSelettori(Integer.parseInt(selettori[i]), nomeC);
						}
						db.eliminaInutili();
						response.sendRedirect("GesHome.jsp#CampagnaModificata");
					}else{
						response.sendRedirect("GesHome.jsp#ErrModificaCampagna");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(k!=0){
				//trova n nel database e paragona con nuovo k
				try {
					if(db.verificaKMaggioreDiN(k, nomeC)){
						//aggiorna solo k
						db.aggiornaK(nomeC, k);
					}else{
						response.sendRedirect("GesHome.jsp#ErrModificaCampagna");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if(m!=0){
			if(annotatori!=null){
				if(annotatori.length != m){
					response.sendRedirect("GesHome.jsp#ErrModificaCampagna");
				}else{
					//aggiorna m e annotatori nel database
					try {
						db.aggiornaM(nomeC, m);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					try {
						db.pulisciAnnotatori(nomeC);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					for(int l=0;l<selettori.length;l++){
						try {
							db.aggiornaAnnotatori(Integer.parseInt(annotatori[l]), nomeC);
						} catch (NumberFormatException | SQLException e) {
							e.printStackTrace();
						}
					}
					try {
						db.accorpaWorkers();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					response.sendRedirect("GesHome.jsp#CampagnaModificata");
				}
			}else{
				response.sendRedirect("GesHome.jsp#ErrModificaCampagna");
			}		
		}else{
			if(annotatori!=null){
				try {
					if(db.trovaM(nomeC)==annotatori.length){
						db.pulisciAnnotatori(nomeC);
						for(int i=0;i<annotatori.length;i++){
							db.aggiornaAnnotatori(Integer.parseInt(selettori[i]), nomeC);
						}
						db.eliminaInutili();
						response.sendRedirect("GesHome.jsp#CampagnaModificata");
					}else{
						response.sendRedirect("GesHome.jsp#ErrModificaCampagna");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
