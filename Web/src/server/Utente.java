package server;

public class Utente{
	private String Username;
	private String Mail;
	private String Password;
	private String Nome;
	private String Cognome;
	private boolean Sesso;
	private boolean Gestore;
	private int Id;
	int Response;

	public Utente(String username, String mail, String password, String nome, String cognome, boolean sesso, boolean gestore){
		this.Username = username;
		this.Mail = mail;
		this.Password = password;
		this.Nome = nome;
		this.Cognome = cognome;
		this.Sesso = sesso;
		this.Gestore = gestore;
		this.Id = -1;
		this.Response = 0;
	}
	
	public Utente(){
		this.Username = null;
		this.Mail = null;
		this.Password = null;
		this.Nome = null;
		this.Cognome = null;
		this.Sesso = true;
		this.Gestore = false;
		this.Id = -1;
		this.Response = -1;
	}
	
	public String GetUsername(){
		return Username;
	}

	public void SetUsername(String Userx){
		this.Username=Userx;
	}

	public String GetMail(){
		return Mail;
	}

	public void SetMail(String Mailx){
		this.Mail=Mailx;
	}

	public String GetPassword(){
		return Password;
	}

	public void SetPassword(String Passx){
		this.Password=Passx;
	}

	public String GetNome(){
		return Nome;
	}

	public void SetNome(String Nomex){
		this.Nome=Nomex;
	}

	public String GetCognome(){
		return Cognome;
	}

	public void SetCognome(String Cognomex){
		this.Cognome=Cognomex;
	}

	public boolean GetSesso(){
		return Sesso;
	}

	public void SetSesso(boolean Sex){
		this.Sesso=Sex;
	}

	public boolean GetGestore(){
		return Gestore;
	}

	public void SetGestore(boolean Gesx){
		this.Gestore=Gesx;
	}
	
	public void SetId(int id){
		this.Id=id;
	}
	
	public int GetId(){
		return Id;
	}
	
	public void SetResponse(int Error){
		this.Response=Error;
	}
	
	public int GetResponse(){
		return Response;
	}
}
