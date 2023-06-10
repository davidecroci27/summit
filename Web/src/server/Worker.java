package server;

public class Worker {
	private int id;
	private int idworker;
	private boolean annotatore;
	private boolean selettore;
	private String nomecampagna;
	
	public Worker(int idworker, boolean annotatore, boolean selettore, String nomecampagna) {
		this.idworker = idworker;
		this.annotatore = annotatore;
		this.selettore = selettore;
		this.nomecampagna = nomecampagna;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdworker() {
		return idworker;
	}
	
	public void setIdworker(int idworker) {
		this.idworker = idworker;
	}
	
	public boolean isAnnotatore() {
		return annotatore;
	}
	
	public void setAnnotatore(boolean annotatore) {
		this.annotatore = annotatore;
	}
	
	public boolean isSelettore() {
		return selettore;
	}
	
	public void setSelettore(boolean selettore) {
		this.selettore = selettore;
	}
	
	public String getNomecampagna() {
		return nomecampagna;
	}
	
	public void setNomecampagna(String nomecampagna) {
		this.nomecampagna = nomecampagna;
	}	
}