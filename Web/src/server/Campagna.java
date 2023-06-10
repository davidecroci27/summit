package server;

public class Campagna {
	private String nome;
	private int idMng;
	private int utTaskSel;
	private int utTaskAnn;
	private int valut;
	private int numeroImmagini;
	private boolean stato;
	private boolean selettore;
	private boolean annotatore;
	
	public void SetNome(String name){
		this.nome=name;
	}
	
	public String GetNome(){
		return this.nome;
	}
	
	public void SetIdMng(int num){
		this.idMng=num;
	}
	
	public int GetIdMng(){
		return this.idMng;
	}
	
	public void SetUtTaskSel(int num){
		this.utTaskSel=num;
	}
	
	public int GetUtTaskSel(){
		return this.utTaskSel;
	}
	
	public void SetUtTaskAnn(int num){
		this.utTaskAnn=num;
	}
	
	public int GetUtTaskAnn(){
		return this.utTaskAnn;
	}
	
	public void SetValut(int num){
		this.valut=num;
	}
	
	public int GetValut(){
		return this.valut;
	}
	
	public void SetNumeroImmagini(int num){
		this.numeroImmagini=num;
	}
	
	public int GetNumeroImmagini(){
		return this.numeroImmagini;
	}
	
	public void SetStato(boolean b){
		this.stato=b;
	}
	
	public boolean GetStato(){
		return this.stato;
	}
	
	public void SetSelettore(boolean b){
		this.selettore=b;
	}
	
	public boolean GetSelettore(){
		return this.selettore;
	}
	
	public void SetAnnotatore(boolean b){
		this.annotatore=b;
	}
	
	public boolean GetAnnotatore(){
		return this.annotatore;
	}
}
