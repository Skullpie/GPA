package bo;

import java.sql.Date;

public class Vehicule {
/*
*CREATE TABLE IF NOT EXISTS `vehicules` (
  `id_vehicule` int(11) NOT NULL AUTO_INCREMENT,
  `immatriculation` char(10) NOT NULL,
  `description` varchar(200) NOT NULL,
  `date_achat` date DEFAULT NULL,
  `nombre_place` int(11) NOT NULL,
  `vendu` tinyint(1) NOT NULL,
  `copie_carte_grise` varbinary(10) DEFAULT NULL,
  `id_reservataire` int(11) DEFAULT NULL,
  `id_lieu` int(11) NOT NULL,
  PRIMARY KEY (`id_vehicule`),
  UNIQUE KEY `immatriculation` (`immatriculation`),
  KEY `FK_vehicule_reservataire` (`id_reservataire`),
  KEY `id_lieu` (`id_lieu`)
 */
	private int id;
	private String immatriculation;
	private String description;
	private Date dateAchat;
	private int nombrePlace;
	private boolean vendu;
	private String copieCarteGrise;
	private Reservataire reservataire;
	private Lieu lieu;

	public Vehicule(int id, String immatriculation, String description, Date dateAchat, int nombrePlace, boolean vendu,
			String copieCarteGrise, Reservataire reservataire, Lieu lieu) {
		super();
		this.id = id;
		this.immatriculation = immatriculation;
		this.description = description;
		this.dateAchat = dateAchat;
		this.nombrePlace = nombrePlace;
		this.vendu = vendu;
		this.copieCarteGrise = copieCarteGrise;
		this.reservataire = reservataire;
		this.lieu = lieu;
	}

	public Vehicule() {
		super();
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateAchat() {
		return dateAchat;
	}

	public void setDateAchat(Date dataAchat) {
		this.dateAchat = dataAchat;
	}

	public int getNombrePlace() {
		return nombrePlace;
	}

	public void setNombrePlace(int nombrePlace) {
		this.nombrePlace = nombrePlace;
	}

	public boolean isVendu() {
		return vendu;
	}

	public void setVendu(boolean vendu) {
		this.vendu = vendu;
	}

	public String getCopieCarteGrise() {
		return copieCarteGrise;
	}

	public void setCopieCarteGrise(String copieCarteGrise) {
		this.copieCarteGrise = copieCarteGrise;
	}

	public Reservataire getReservataire() {
		return reservataire;
	}

	public void setReservataire(Reservataire reservataire) {
		this.reservataire = reservataire;
	}

	public Lieu getLieu() {
		return lieu;
	}

	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
