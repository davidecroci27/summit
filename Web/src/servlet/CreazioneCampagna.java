package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import server.Utente;
import server.Worker;
import server.Database;;

@MultipartConfig
public class CreazioneCampagna extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreazioneCampagna() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().append("Served at: ").append(req.getContextPath());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Database db = new Database();
		Utente UserSession = new Utente();
		String nome = req.getParameter("nomecampagna");
		try {
			if(db.controllaNomeCampagna(nome)){
				resp.sendRedirect("GesHome.jsp#ErroreNomeCampagna");
			}else{
				int n = Integer.parseInt(req.getParameter("ntask"));
				int m = Integer.parseInt(req.getParameter("nutval"));
				int k = Integer.parseInt(req.getParameter("nvalut"));
				boolean selettore = false;
				boolean annotatore = false;
				String[] selettori = req.getParameterValues("selettore");
				String[] annotatori = req.getParameterValues("annotatore");
				if(selettori.length < n || annotatori.length < m || k > selettori.length){
					resp.sendRedirect("GesHome.jsp#ErroreSelAnn");
				}else{
					UserSession = (Utente)req.getSession().getAttribute("Utente");
					int id = UserSession.GetId();
					try {
						db.creaCampagna(nome,n,m,k,id,false);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					List<Part> fileParts = req.getParts().stream().filter(part->"upload".equals(part.getName())).collect(Collectors.toList());
					for(Part filePart : fileParts){
						String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
						String newPath = "C:/Users/Davide/EclipseWorkspace/Web/WebContent/images/ImagesCampagne/" + nome;
						String newPath2 = "images/ImagesCampagne/" + nome;
						(new File(newPath)).mkdir();
						InputStream inputStreamFromRequestBody =filePart.getInputStream();
						File uploadedFile = new File(newPath + File.separator + fileName);
						@SuppressWarnings("resource")
						OutputStream outputStreamToTargetFile = new FileOutputStream(uploadedFile);
						int read = 0;
						final byte[] bytes = new byte[1024];
						while((read = inputStreamFromRequestBody.read(bytes)) != -1){
							outputStreamToTargetFile.write(bytes, 0 ,read);
						}
						String newPathImage = newPath2 + "/" + fileName;
						try {
							db.uploadImages(newPathImage, nome);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					ArrayList<Utente> lavoratori = new ArrayList<Utente>();
					try {
						lavoratori = db.caricaListaUtenti(false);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					for(int i = 0; i < lavoratori.size(); i++){
						for(int j = 0; j < selettori.length; j++){
							if(lavoratori.get(i).GetId()==Integer.parseInt(selettori[j])){
								selettore = true;
							}
						}
						for(int j = 0; j < annotatori.length; j++){
							if(lavoratori.get(i).GetId()==Integer.parseInt(annotatori[j])){
								annotatore = true;
							}
						}
						int idworker = lavoratori.get(i).GetId();
						if(annotatore!=false||selettore!=false){
							Worker worker = new Worker(idworker, annotatore, selettore, nome);
							try {
								db.setWorker(worker);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						selettore = false;
						annotatore = false;
					}
					resp.sendRedirect("GesHome.jsp#NuovaCampagnaAggiunta");
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}