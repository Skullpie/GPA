package bo;

import java.io.Serializable;

public class MotifIndisponibilite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String intitule;
	public MotifIndisponibilite(int idMotifIndisponibilite, String intitule) {
		super();
		this.id = idMotifIndisponibilite;
		this.intitule = intitule;
	}
	public MotifIndisponibilite() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int idMotifIndisponibilite) {
		this.id = idMotifIndisponibilite;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	
	
}
