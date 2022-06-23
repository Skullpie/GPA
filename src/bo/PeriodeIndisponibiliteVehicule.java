package bo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PeriodeIndisponibiliteVehicule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int idIndisponibilite;
	private LocalDateTime dateDebut;
	private LocalDateTime dateFin;
	private Vehicule vehicule;
	private MotifIndisponibilite motifIndisponibilite;
	public PeriodeIndisponibiliteVehicule(int idIndisponibilite, LocalDateTime dateDebut, LocalDateTime dateFin, Vehicule vehicule,
			MotifIndisponibilite motifIndisponibilite) {
		super();
		this.idIndisponibilite = idIndisponibilite;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.vehicule = vehicule;
		this.motifIndisponibilite = motifIndisponibilite;
	}
	public PeriodeIndisponibiliteVehicule() {
		super();
	}
	public int getIdIndisponibilite() {
		return idIndisponibilite;
	}
	public void setIdIndisponibilite(int idIndisponibilite) {
		this.idIndisponibilite = idIndisponibilite;
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
	public Vehicule getVehicule() {
		return vehicule;
	}
	public void setVehicule(Vehicule vehicule) {
		this.vehicule = vehicule;
	}
	public MotifIndisponibilite getMotifIndisponibilite() {
		return motifIndisponibilite;
	}
	public void setMotifIndisponibilite(MotifIndisponibilite motifIndisponibilite) {
		this.motifIndisponibilite = motifIndisponibilite;
	}
}
