package bo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Reservation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * CREATE TABLE IF NOT EXISTS `reservations` (
  `id_reservation` int(11) NOT NULL AUTO_INCREMENT,
  `motif` varchar(50) NOT NULL,
  `datetime_debut` date NOT NULL,
  `datetime_fin` date NOT NULL,
  `commentaire` varchar(200) DEFAULT NULL,
  `immatriculation` varchar(10) NOT NULL,
  `id_lieu` int(11) NOT NULL,
  PRIMARY KEY (`id_reservation`),
  KEY `immatriculation` (`immatriculation`),
  KEY `reservations_ibfk_1` (`id_lieu`)
	 */
	private int id;
	private String motif;
	private LocalDateTime dateDebut;
	private LocalDateTime dateFin;
	private String commentaire;
	private Vehicule vehicule;
	private Lieu lieu;
	private List<Reservataire> reservataires;
	
	public Reservation(int id, String motif, LocalDateTime dateDebut, LocalDateTime dateFin, String commentaire,
			Vehicule vehicule, Lieu lieu, List<Reservataire> reservataires) {
		super();
		this.id = id;
		this.motif = motif;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.commentaire = commentaire;
		this.vehicule = vehicule;
		this.lieu = lieu;
		this.reservataires = reservataires;
	}

	public Reservation() {
		super();
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMotif() {
		return motif;
	}
	public void setMotif(String motif) {
		this.motif = motif;
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
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public Vehicule getVehicule() {
		return vehicule;
	}
	public void setVehicule(Vehicule vehicule) {
		this.vehicule = vehicule;
	}
	public List<Reservataire> getReservataires() {
		return reservataires;
	}
	public void setReservataires(List<Reservataire> reservataires) {
		this.reservataires = reservataires;
	}
	public void addReservataire(Reservataire reservataire) {
		if (this.reservataires == null) {
			this.reservataires = new ArrayList<Reservataire>();
		}
		this.reservataires.add(reservataire);
	}
	public Lieu getLieu() {
		return lieu;
	}

	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}
}
