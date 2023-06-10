package server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.Utente;
import server.Campagna;

public class Database {
	private Connection con=null;

	public Database(){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/progetto", "root", "davidecroci123");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	Utente User=new Utente();

	public Utente RegistraUtente(String username, String mail, String password, String nome, String cognome, boolean sesso, boolean gestore)
			throws RemoteException, SQLException{
		boolean Esistente=false, Usr=false;
		try {
			String Controllo = "SELECT username FROM progetto.utenti WHERE username=?";
			PreparedStatement preparedStmt;
			preparedStmt = con.prepareStatement(Controllo);
			preparedStmt.setString(1, username);
			ResultSet rs=preparedStmt.executeQuery();
			if(rs.next()){
				Esistente=true;
				Usr=true;
				System.out.print("Username già esistente!\n");
			}
			try {
				Controllo = "SELECT username FROM progetto.utenti WHERE mail=?";
				preparedStmt = con.prepareStatement(Controllo);
				preparedStmt.setString(1, mail);
				rs=preparedStmt.executeQuery();
				if(rs.next()){
					Esistente=true;
					System.out.print("E-Mail già esistente!\n");
					if(Usr){
						User.SetResponse(3);
						return User;
					}else{
						User.SetResponse(2);
						return User;
					}
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
			if(Usr){
				User.SetResponse(1);
				return User;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(!Esistente){
			try {
				String query = " INSERT INTO utenti (username, mail, password, nome, cognome, sesso, gestore) VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt;
				System.out.print("Connesso per la registrazione\n");
				preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, username);
				preparedStmt.setString(2, mail);
				preparedStmt.setString(3, password);
				preparedStmt.setString(4, nome);
				preparedStmt.setString(5, cognome);
				preparedStmt.setBoolean(6, sesso);
				preparedStmt.setBoolean(7, gestore);
				preparedStmt.executeUpdate();
				System.out.print("Registrato\n");
				preparedStmt.close();
				User.SetUsername(username);
				User.SetMail(mail);
				User.SetPassword(password);
				User.SetNome(nome);
				User.SetCognome(cognome);
				User.SetSesso(sesso);
				User.SetGestore(gestore);
				String queryid = " SELECT id FROM utenti WHERE username=?";
				PreparedStatement preparedStmt1;
				preparedStmt1 = con.prepareStatement(queryid);
				preparedStmt1.setString(1, username);
				preparedStmt1.executeUpdate();
				preparedStmt1.close();
				User.SetId(Integer.parseInt(queryid));
				if(gestore){
					User.SetResponse(5);
					return User;
				}else{
					User.SetResponse(4);
					return User;
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
	}

	public Utente LoginUtente(String Username, String Pass) throws IOException{
		try{
			String query = "SELECT username FROM progetto.utenti WHERE username=?";
			PreparedStatement preparedStmt;
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, Username);
			ResultSet rs=preparedStmt.executeQuery();
			if(rs.next()){
				String query2 = "SELECT password FROM progetto.utenti WHERE password=?";
				PreparedStatement preparedStmt2;
				preparedStmt2 = con.prepareStatement(query2);
				preparedStmt2.setString(1, Pass);
				ResultSet rs2=preparedStmt2.executeQuery();
				if(rs2.next()){
					System.out.print("Loggato\n");
					String query3 = "SELECT * FROM progetto.utenti WHERE username=?";
					PreparedStatement preparedStmt3;
					preparedStmt3 = con.prepareStatement(query3);
					preparedStmt3.setString(1, Username);
					ResultSet rs3=preparedStmt3.executeQuery();
					rs3.next();
					User.SetResponse(3);
					User.SetUsername(Username);
					User.SetPassword(Pass);
					User.SetMail(rs3.getString("mail"));
					User.SetNome(rs3.getString("nome"));
					User.SetCognome(rs3.getString("cognome"));
					User.SetSesso(rs3.getBoolean("sesso"));
					User.SetGestore(rs3.getBoolean("gestore"));
					User.SetId(rs3.getInt("id"));
					return User;
				}else{
					System.out.print("Password errata\n");
					User.SetResponse(2);
					return User;
				}
			}else{
				System.out.print("Username errato\n");
				User.SetResponse(1);
				return User;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void attivaCampagna(String nomeCampagna) throws RemoteException, SQLException {
		String query = "UPDATE campagne SET attiva=? WHERE NomeCampagna=?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setBoolean(1, true);
			ps.setString(2, nomeCampagna);
			ps.executeUpdate();
			ps.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		return;
	}
	
	public void creaCampagna(String nome, int n, int m, int k, int idg, boolean stato) throws RemoteException, SQLException {
		String query = "INSERT INTO campagne (IDManager, NomeCampagna, TaskUtentiSel, ValutazioniPositive, TaskUtentiAnn, attiva) VALUES (?,?,?,?,?,?)";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1,idg);  
			ps.setString(2,nome);
			ps.setInt(3,n);
			ps.setInt(4,k);
			ps.setInt(5,m);
			ps.setBoolean(6,stato);
			ps.executeUpdate();
			ps.close();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	public boolean controllaNomeCampagna(String nomeC) throws RemoteException, SQLException {
		String query = "SELECT id FROM campagne WHERE NomeCampagna =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, nomeC);
			ResultSet rs =ps.executeQuery();			
			while (rs.next()){
				return true;
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public void uploadImages(String path, String nomecampagna) throws RemoteException, SQLException {
		String query = "INSERT INTO immagine (path, nomecampagna, attiva) VALUES (?,?,?)";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1,path);  
			ps.setString(2,nomecampagna);
			ps.setInt(3, 0);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	public ArrayList<Utente> caricaListaUtenti(boolean gestore) throws RemoteException, SQLException {
		ArrayList<Utente> lavoratori = new ArrayList<Utente>();		
		String query = "SELECT * FROM utenti WHERE gestore =?";
		try{
			PreparedStatement ps =con.prepareStatement(query);
			ps.setBoolean(1, gestore);
			ResultSet rs =ps.executeQuery();			
			while (rs.next()){
				Utente User = new Utente();
				User.SetId(rs.getInt("id"));
				User.SetUsername(rs.getString("username"));
				User.SetNome(rs.getString("nome"));
				User.SetCognome(rs.getString("cognome"));
				User.SetMail(rs.getString("mail"));
				User.SetPassword(rs.getString("password"));
				User.SetSesso(rs.getBoolean("sesso"));
				User.SetGestore(gestore);				
				lavoratori.add(User);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lavoratori;
	}
	
	public void setWorker(Worker w) throws RemoteException, SQLException{
		String query = "INSERT INTO workers (idUtente, annotatore, selettore, nomeCampagna) VALUES (?,?,?,?)";
		try{
			PreparedStatement ps = con.prepareStatement(query);	
	    	ps.setInt(1,w.getIdworker());  
	    	ps.setBoolean(2,w.isAnnotatore());        
	    	ps.setBoolean(3,w.isSelettore());
	    	ps.setString(4,w.getNomecampagna());
	    	ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(w.isSelettore()){
			aggiungiSelettore(w.getIdworker(),w.getNomecampagna());
		}
		if(w.isAnnotatore()){
			aggiungiAnnotatore(w.getIdworker(),w.getNomecampagna());
		}
		return;
	}
	
	public void aggiungiSelettore(int idWorker, String campagnaName) throws RemoteException, SQLException{
		ArrayList<String> immagini = new ArrayList<String>();
		String query = "SELECT * FROM immagine WHERE nomecampagna =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, campagnaName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				immagini.add(rs.getString("path"));
			}	
		} catch(Exception e){
			e.printStackTrace();
		}
		String query2 = "INSERT INTO selezione (idWorker, pathSelezione, nomeCampagna, valutazione, valutata) VALUES (?,?,?,?,?)";
		for(int i=0;i<immagini.size();i++){
			try{		
				PreparedStatement ps2 = con.prepareStatement(query2);	
				ps2.setInt(1,idWorker);  
				ps2.setString(2, immagini.get(i));        
				ps2.setString(3,campagnaName);
				ps2.setBoolean(4,false);
				ps2.setBoolean(5,false);
				ps2.executeUpdate();
				ps2.close();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
	
	public void aggiungiAnnotatore(int idWorker, String campagnaName) throws RemoteException, SQLException{
		ArrayList<String> immagini = new ArrayList<String>();
		String query = "SELECT * FROM immagine WHERE nomecampagna =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, campagnaName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				immagini.add(rs.getString("path"));
			}	
		} catch(Exception e){
			e.printStackTrace();
		}
		String query2 = "INSERT INTO annotazione (idWorker, pathAnnotazione, nomeCampagna, annotata) VALUES (?,?,?,?)";
		for(int i=0;i<immagini.size();i++){
			try{		
				PreparedStatement ps2 = con.prepareStatement(query2);	
				ps2.setInt(1,idWorker);  
				ps2.setString(2, immagini.get(i));        
				ps2.setString(3,campagnaName);
				ps2.setBoolean(4,false);
				ps2.executeUpdate();
				ps2.close();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
	
	public ArrayList<Campagna> caricaListaCampagne(int idMan) throws RemoteException, SQLException {
		ArrayList<Campagna> CampagneAttive = new ArrayList<Campagna>();		
		String query = "SELECT * FROM campagne WHERE IDManager =?";
		int count;
		try{
			PreparedStatement ps =con.prepareStatement(query);
			ps.setInt(1, idMan);
			ResultSet rs = ps.executeQuery();			
			while (rs.next()){
				count=0;
				Campagna campagna = new Campagna();
				campagna.SetNome(rs.getString("NomeCampagna"));
				campagna.SetUtTaskSel(rs.getInt("TaskUtentiSel"));
				campagna.SetUtTaskAnn(rs.getInt("TaskUtentiAnn"));
				campagna.SetValut(rs.getInt("ValutazioniPositive"));
				campagna.SetStato(rs.getBoolean("attiva"));
				String query2 = "SELECT nomecampagna FROM immagine WHERE nomecampagna =?";
				try{
					PreparedStatement ps2 =con.prepareStatement(query2);
					ps2.setString(1, campagna.GetNome());
					ResultSet rs2 = ps2.executeQuery();
					while (rs2.next()){
						count++;
					}
					campagna.SetNumeroImmagini(count);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				CampagneAttive.add(campagna);
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CampagneAttive;
	}
	
	public ArrayList<Campagna> caricaListaCampagneWorker(int idWor) throws RemoteException, SQLException {
		ArrayList<Campagna> campagneAttive = new ArrayList<Campagna>();
		String query = "SELECT * FROM workers AS W JOIN campagne AS C ON W.nomeCampagna=C.NomeCampagna WHERE idUtente =?";
		try{
			PreparedStatement ps =con.prepareStatement(query);
			ps.setInt(1, idWor);
			ResultSet rs = ps.executeQuery();			
			while (rs.next()){
				Campagna campagna = new Campagna();
				campagna.SetNome(rs.getString("nomeCampagna"));
				campagna.SetSelettore(rs.getBoolean("selettore"));
				campagna.SetAnnotatore(rs.getBoolean("annotatore"));
				campagna.SetStato(rs.getBoolean("attiva"));
				campagneAttive.add(campagna);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return campagneAttive;	
	}
	
	public String immSelezione(int idSelettore, String nomeCampagna) throws RemoteException, SQLException {
		ArrayList<String> pathImmaginiDaSelezionare = new ArrayList<String>();
		String query = "SELECT pathSelezione FROM selezione WHERE idWorker =? AND nomeCampagna =? AND valutata =?";
		try{
			PreparedStatement ps =con.prepareStatement(query);
			ps.setInt(1, idSelettore);
			ps.setString(2, nomeCampagna);
			ps.setBoolean(3, false);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				pathImmaginiDaSelezionare.add(rs.getString("pathSelezione"));
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		if(pathImmaginiDaSelezionare.isEmpty())
			return "vuoto";
		else
			return pathImmaginiDaSelezionare.get(0);
	}
	
	public String immAnnotazione(int idSelettore, String nomeCampagna) throws RemoteException, SQLException {
		ArrayList<String> pathImmaginiDaAnnotare = new ArrayList<String>();
		String query = "SELECT pathAnnotazione FROM annotazione WHERE idWorker =? AND nomeCampagna =? AND annotata=false";
		boolean esito=false;
		try{
			PreparedStatement ps =con.prepareStatement(query);
			ps.setInt(1, idSelettore);
			ps.setString(2, nomeCampagna);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				esito=okAnnotazione(rs.getString("pathAnnotazione"),nomeCampagna);
				if(esito){
					pathImmaginiDaAnnotare.add(rs.getString("pathAnnotazione"));
				}
				esito=false;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		if(pathImmaginiDaAnnotare.isEmpty()){
			if(esito)
				return "vuoto";
			else
				return "noVoti";
		}else
			return pathImmaginiDaAnnotare.get(0);
	}
	
	public String accettaERitornaNuovaImmSelezione(String pathAccettata, int idSelettore, String nomeCampagna) throws RemoteException, SQLException{
		String query="UPDATE selezione SET valutazione=1, valutata=1 WHERE pathSelezione =? AND idWorker =? AND nomeCampagna =?";
		try{
			PreparedStatement ps =con.prepareStatement(query);
			ps.setString(1, pathAccettata);
			ps.setInt(2, idSelettore);
			ps.setString(3, nomeCampagna);
			ps.executeUpdate();
			ps.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		ArrayList<String> pathImmaginiDaSelezionare = new ArrayList<String>();
		String query2 = "SELECT pathSelezione FROM selezione WHERE idWorker =? AND nomeCampagna =? AND valutata =?";
		try{
			PreparedStatement ps2 = con.prepareStatement(query2);
			ps2.setInt(1, idSelettore);
			ps2.setString(2, nomeCampagna);
			ps2.setBoolean(3, false);
			ResultSet rs = ps2.executeQuery();
			while(rs.next()){
				pathImmaginiDaSelezionare.add(rs.getString("pathSelezione"));
			}
		} catch (Exception e2){
			e2.printStackTrace();
		}
		if(pathImmaginiDaSelezionare.isEmpty())
			return "vuoto";
		else
			return pathImmaginiDaSelezionare.get(0);
	}
	
	public boolean okAnnotazione(String path, String campagna) throws RemoteException, SQLException{
		String query = "SELECT * FROM selezione WHERE pathSelezione =? AND valutazione=1";
		int votiPositiviRicevuti=0;
		int votiPositiviRichiesti=0;
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, path);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				votiPositiviRicevuti++;
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		String query2 = "SELECT ValutazioniPositive FROM campagne WHERE NomeCampagna =?";
		try{
			PreparedStatement ps2 = con.prepareStatement(query2);
			ps2.setString(1, campagna);
			ResultSet rs2 = ps2.executeQuery();
			while(rs2.next()){
				votiPositiviRichiesti=rs2.getInt("ValutazioniPositive");
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		if(votiPositiviRicevuti<votiPositiviRichiesti){
			return false;
		}else{
			return true;
		}
	}
	
	public String rifiutaERitornaNuovaImmSelezione(String pathAccettata, int idSelettore, String nomeCampagna) throws RemoteException, SQLException{
		String query="UPDATE selezione SET valutata=1 WHERE pathSelezione =? AND idWorker =? AND nomeCampagna =?";
		try{
			PreparedStatement ps =con.prepareStatement(query);
			ps.setString(1, pathAccettata);
			ps.setInt(2, idSelettore);
			ps.setString(3, nomeCampagna);
			ps.executeUpdate();
			ps.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		ArrayList<String> pathImmaginiDaSelezionare = new ArrayList<String>();
		String query2 = "SELECT pathSelezione FROM selezione WHERE idWorker =? AND nomeCampagna =? AND valutata =?";
		try{
			PreparedStatement ps2 =con.prepareStatement(query2);
			ps2.setInt(1, idSelettore);
			ps2.setString(2, nomeCampagna);
			ps2.setBoolean(3, false);
			ResultSet rs = ps2.executeQuery();
			while(rs.next()){
				pathImmaginiDaSelezionare.add(rs.getString("pathSelezione"));
			}
		} catch (Exception e2){
			e2.printStackTrace();
		}
		if(pathImmaginiDaSelezionare.isEmpty())
			return "vuoto";
		else
			return pathImmaginiDaSelezionare.get(0);
	}
	
	public void aggiornaNeK(String nomeC, int n, int k) throws RemoteException, SQLException{
		String query = "UPDATE campagne SET TaskUtentiSel =?, ValutazioniPositive =? WHERE NomeCampagna =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, n);
			ps.setInt(2, k);
			ps.setString(3, nomeC);
			ps.executeUpdate();
			ps.close();
		} catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public void pulisciSelettori(String nomeC) throws RemoteException, SQLException{
		String query = "UPDATE workers SET selettore=0 WHERE nomeCampagna =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, nomeC);
			ps.executeUpdate();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void aggiornaSelettori(int idUt, String nomeC) throws RemoteException, SQLException{
		String query = "INSERT INTO workers (idUtente, annotatore, selettore, nomeCampagna) VALUES (?,?,?,?)";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, idUt);
			ps.setBoolean(2, false);
			ps.setBoolean(3, true);
			ps.setString(4, nomeC);
			ps.executeUpdate();
			ps.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void eliminaInutili() throws RemoteException, SQLException{
		String query = "DELETE FROM workers WHERE annotatore=0 AND selettore=0";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean verificaNMaggioreDiK(int n, String nomeC) throws RemoteException, SQLException{
		String query = "SELECT ValutazioniPositive FROM campagne WHERE NomeCampagna =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, nomeC);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				if(n<rs.getInt("ValutazioniPositive"))
					return false;
				else
					return true;
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public void aggiornaN(String nomeC, int n) throws RemoteException, SQLException{
		String query = "UPDATE campagne SET TaskUtentiSel =? WHERE NomeCampagna =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, n);
			ps.setString(2, nomeC);
			ps.executeUpdate();
			ps.close();
		} catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public boolean verificaKMaggioreDiN(int k, String nomeC) throws RemoteException, SQLException{
		String query = "SELECT TaskUtentiSel FROM campagne WHERE NomeCampagna =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, nomeC);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				if(k>rs.getInt("TaskUtentiSel"))
					return false;
				else
					return true;
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public void aggiornaK(String nomeC, int k) throws RemoteException, SQLException{
		String query = "UPDATE campagne SET ValutazioniPositive =? WHERE NomeCampagna =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, k);
			ps.setString(2, nomeC);
			ps.executeUpdate();
			ps.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void aggiornaM(String nomeC, int m) throws RemoteException, SQLException{
		String query = "UPDATE campagne SET TaskUtentiAnn =? WHERE NomeCampagna =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, m);
			ps.setString(2, nomeC);
			ps.executeUpdate();
			ps.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void pulisciAnnotatori(String nomeC) throws RemoteException, SQLException{
		String query = "UPDATE workers SET annotatore=0 WHERE nomeCampagna =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, nomeC);
			ps.executeUpdate();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void aggiornaAnnotatori(int idUt, String nomeC) throws RemoteException, SQLException{
		String query = "INSERT INTO workers (idUtente, annotatore, selettore, nomeCampagna) VALUES (?,?,?,?)";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, idUt);
			ps.setBoolean(2, false);
			ps.setBoolean(3, true);
			ps.setString(4, nomeC);
			ps.executeUpdate();
			ps.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void accorpaWorkers() throws RemoteException, SQLException{
		ArrayList<String> camps = new ArrayList<String>();
		ArrayList<Integer> iduts = new ArrayList<Integer>();
		String campagna;
		int utente;
		int id1;
		String query = "SELECT id, idUtente, nomeCampagna FROM workers";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				campagna = rs.getString("nomeCampagna");
				utente = rs.getInt("idUtente");
				id1 = rs.getInt("id");
				String query2 = "SELECT * FROM workers WHERE idUtente =? AND nomeCampagna =?";
				try{
					PreparedStatement ps2 = con.prepareStatement(query2);
					ps2.setInt(1, utente);
					ps2.setString(2, campagna);
					ResultSet rs2 = ps2.executeQuery();
					while(rs2.next()){
						if(id1!=rs2.getInt("id")){
							camps.add(campagna);
							iduts.add(utente);
							String query3 = "DELETE FROM workers WHERE idUtente =? AND nomeCampagna =?";
							try{
								PreparedStatement ps3 = con.prepareStatement(query3);
								ps3.setInt(1, utente);
								ps3.setString(2, campagna);
								ps3.executeUpdate();
								ps3.close();
							} catch(Exception e){
								e.printStackTrace();
							}
						}
					}
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		for(int i=0;i<iduts.size();i++){
			String query4 = "INSERT INTO workers (idUtente, annotatore, selettore, nomeCampagna) VALUES (?,?,?,?)";
			try{
				PreparedStatement ps4 = con.prepareStatement(query4);
				ps4.setInt(1, iduts.get(i));
				ps4.setBoolean(2, true);
				ps4.setBoolean(3, true);
				ps4.setString(4, camps.get(i));
				ps4.executeUpdate();
				ps4.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public int trovaN(String nomeC) throws RemoteException, SQLException{
		String query = "SELECT TaskUtentiSel FROM campagne WHERE NomeCampagna =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, nomeC);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				return rs.getInt("TaskUtentiSel");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return -1;
	}
	
	public int trovaM(String nomeC) throws RemoteException, SQLException{
		String query = "SELECT TaskUtentiAnn FROM campagne WHERE NomeCampagna =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, nomeC);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				return rs.getInt("TaskUtentiAnn");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return -1;
	}
	
	public void eliminaCampagna(String nomeC) throws RemoteException, SQLException{
		String query = "DELETE FROM campagne WHERE NomeCampagna =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, nomeC);
			ps.executeUpdate();
			ps.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<Float> statisticheCamp(String nomeC) throws RemoteException, SQLException{
		ArrayList<Float> stats = new ArrayList<Float>();
		ArrayList<String> pathsImmCamp = new ArrayList<String>();
		int immaginiCampagna=0, valutNecessarie=0, immaginiApprovate=0, immaginiRifiutate=0, immaginiAnnotate=0, contaPerAvg=0;
		float avgAnnPerImm=0;
		String query = "SELECT * FROM immagine WHERE nomecampagna =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, nomeC);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				immaginiCampagna++;
				pathsImmCamp.add(rs.getString("path"));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		stats.add(Float.valueOf(immaginiCampagna));
		String query2 = "SELECT * FROM campagne WHERE NomeCampagna =?";
		try{
			PreparedStatement ps2 = con.prepareStatement(query2);
			ps2.setString(1, nomeC);
			ResultSet rs2 = ps2.executeQuery();
			while(rs2.next()){
				valutNecessarie = rs2.getInt("ValutazioniPositive");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		for(int i=0;i<pathsImmCamp.size();i++){
			String query3 = "SELECT * FROM selezione WHERE nomeCampagna =? AND pathSelezione =?";
			int sommaVotiPos=0;
			int sommaNonVoti=0;
			try{
				PreparedStatement ps3 = con.prepareStatement(query3);
				ps3.setString(1, nomeC);
				ps3.setString(2, pathsImmCamp.get(i));
				ResultSet rs3 = ps3.executeQuery();
				while(rs3.next()){
					if(rs3.getBoolean("valutata")){
						if(rs3.getBoolean("valutazione")){
							sommaVotiPos++;
						}
					}else{
						sommaNonVoti++;
					}
				}
			}catch (Exception e){
				e.printStackTrace();
			}
			if(sommaVotiPos>=valutNecessarie){
				immaginiApprovate++;
			}
			if(sommaVotiPos+sommaNonVoti<valutNecessarie){
				immaginiRifiutate++;
			}			
		}
		stats.add(Float.valueOf(immaginiApprovate));
		stats.add(Float.valueOf(immaginiRifiutate));
		for(int i=0;i<pathsImmCamp.size();i++){
			String query4 = "SELECT * FROM annotazione WHERE nomeCampagna =? AND pathAnnotazione =? AND annotata=1";
			boolean annotata=false;
			try{
				PreparedStatement ps4 = con.prepareStatement(query4);
				ps4.setString(1, nomeC);
				ps4.setString(2, pathsImmCamp.get(i));
				ResultSet rs4 = ps4.executeQuery();
				while(rs4.next()){
					annotata=true;
				}
			} catch(Exception e){
				e.printStackTrace();
			}
			if(annotata){
				immaginiAnnotate++;
			}
		}
		stats.add(Float.valueOf(immaginiAnnotate));
		String query5 = "SELECT * FROM annotazione WHERE nomeCampagna =? AND annotata=1";
		try{
			PreparedStatement ps5 = con.prepareStatement(query5);
			ps5.setString(1, nomeC);
			ResultSet rs5 = ps5.executeQuery();
			while(rs5.next()){
				contaPerAvg++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		avgAnnPerImm=contaPerAvg/pathsImmCamp.size();
		stats.add(avgAnnPerImm);
		return stats;
	}
	
	public ArrayList<Integer> statisticheWorker(String nomeW) throws RemoteException, SQLException{
		ArrayList<Integer> stats = new ArrayList<Integer>();
		int idWorker=-1, nImmaginiApprovate=0, nImmaginiRifiutate=0, nImmaginiAnnotate=0, nImmaginiDaApprovare=0, nImmaginiDaAnnotare=0;
		String query = "SELECT * FROM utenti WHERE username =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, nomeW);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				idWorker = rs.getInt("id");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		String query2 = "SELECT * FROM selezione WHERE idWorker =?";
		try{
			PreparedStatement ps2 = con.prepareStatement(query2);
			ps2.setInt(1, idWorker);
			ResultSet rs2 = ps2.executeQuery();
			while(rs2.next()){
				if(rs2.getBoolean("valutata")){
					if(rs2.getBoolean("valutazione")){
						nImmaginiApprovate++;
					}else{
						nImmaginiRifiutate++;
					}
				}else{
					nImmaginiDaApprovare++;
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		stats.add(nImmaginiApprovate);
		stats.add(nImmaginiRifiutate);
		String query3 = "SELECT * FROM annotazione WHERE idWorker =?";
		try{
			PreparedStatement ps3 = con.prepareStatement(query3);
			ps3.setInt(1, idWorker);
			ResultSet rs3 = ps3.executeQuery();
			while(rs3.next()){
				if(rs3.getBoolean("annotata")){
					nImmaginiAnnotate++;
				}else{
					nImmaginiDaAnnotare++;
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		stats.add(nImmaginiAnnotate);
		stats.add(nImmaginiDaApprovare);
		stats.add(nImmaginiDaAnnotare);	
		return stats;
	}
	
	public void cambiaStatoAccettazioneSelezione(String path, String nomeC) throws RemoteException, SQLException{
		int valPos=0, countPos=0, count=0, countValut=0;
		String query = "SELECT ValutazioniPositive FROM campagne WHERE NomeCampagna =?";
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, nomeC);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				valPos = rs.getInt("ValutazioniPositive");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		String query2 = "SELECT * FROM selezione WHERE pathSelezione =?";
		try{
			PreparedStatement ps2 = con.prepareStatement(query2);
			ps2.setString(1, path);
			ResultSet rs2 = ps2.executeQuery();
			while(rs2.next()){
				count++;
				if(rs2.getBoolean("valutata")){
					countValut++;
					if(rs2.getBoolean("valutazione"))
						countPos++;
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		if(countPos>=valPos){
			String query3 = "UPDATE immagine SET attiva=2 WHERE path =?";
			try{
				PreparedStatement ps3 = con.prepareStatement(query3);
				ps3.setString(1, path);
				ps3.executeUpdate();
				ps3.close();
			} catch(Exception e){
				e.printStackTrace();
			}
			return;
		}else{
			if((count-countValut)+countPos>=valPos){
				//in fase di approvazione
				return;
			}else{
				String query3 = "UPDATE immagine SET attiva=1 WHERE path =?";
				try{
					PreparedStatement ps3 = con.prepareStatement(query3);
					ps3.setString(1, path);
					ps3.executeUpdate();
					ps3.close();
				} catch(Exception e){
					e.printStackTrace();
				}
				return;
			}
		}	
	}
	
	public void cambiaStatoAnnotazioneWorker(int user, String campagna, String path) throws RemoteException, SQLException{
		String query = "UPDATE annotazione SET annotata=1 WHERE idWorker=?, pathAnnotazione=?, nomeCampagna=?";
		try{
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, user);
		ps.setString(2, path);
		ps.setString(3, campagna);
		ps.executeUpdate();
		ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}