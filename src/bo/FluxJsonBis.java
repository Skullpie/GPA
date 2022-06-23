package bo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FluxJsonBis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LocalDateTime dateDebut;
	private LocalDateTime dateFin;
	private String immatriculation;
	public FluxJsonBis(LocalDateTime dateDebut, LocalDateTime dateFin, String immatriculation) {
		super();
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.immatriculation = immatriculation;
	}
	public FluxJsonBis() {
		super();
	}
	public LocalDateTime getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(LocalDateTime dateDebut) {
		this.dateDebut = dateDebut;
	}
	public LocalDateTime getDateFin() {
		return dateFin;
	}
	public void setDateFin(LocalDateTime dateFin) {
		this.dateFin = dateFin;
	}
	public String getImmatriculation() {
		return immatriculation;
	}
	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}

}
