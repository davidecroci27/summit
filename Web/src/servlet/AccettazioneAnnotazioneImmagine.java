package servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.Database;

@WebServlet("/AccettazioneAnnotazioneImmagine")
public class AccettazioneAnnotazioneImmagine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AccettazioneAnnotazioneImmagine() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = new Database();
		String canvas = request.getParameter("canvas");
		String campagna = request.getParameter("ncampagna");
		System.out.print("annotazione XXXXXX salvata per la campagna " + request.getParameter("ncampagna") + " dall'utente " + request.getParameter("user") + "\n");
		String path = "C:/Users/Davide/EclipseWorkspace/Web/WebContent/images/ImagesCampagne/" + campagna + "/Annotazioni";
		Path folder = Paths.get(path);
		if (!Files.exists(folder)) {
			(new File(path)).mkdir();
		}
		String immagine = request.getParameter("imm").replace("images/ImagesCampagne/" + campagna + "/", "");
		String pathCartellaImmagine = path + "/" + immagine;
		Path folderImmagine = Paths.get(pathCartellaImmagine);
		if (!Files.exists(folderImmagine)) {
			(new File(pathCartellaImmagine)).mkdir();
		}
		String base64Image = canvas.split(",")[1];
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
		File annotazione = new File(pathCartellaImmagine, request.getParameter("user") + ".png");
		ImageIO.write(img, "png", annotazione);
		try {
			db.cambiaStatoAnnotazioneWorker(Integer.parseInt(request.getParameter("idUt")), campagna,
					"C:/Users/Davide/EclipseWorkspace/Web/WebContent/images/ImagesCampagne/" + campagna + "/" + immagine);
			System.out.println("2");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.getWriter().write("go");	
	}
}
