package bo;

import java.io.Serializable;

public class Lieu implements Serializable{

	/*
  CREATE TABLE IF NOT EXISTS `lieux` (
  `id_lieu` int(11) NOT NULL AUTO_INCREMENT,
  `intitule` varchar(50) NOT NULL,
  `rue` varchar(50) NOT NULL,
  `code_postal` char(5) NOT NULL,
  `ville` varchar(50) NOT NULL,
  `information_complementaire` varchar(200) DEFAULT NULL,
  `latitude_gps` varchar(50) DEFAULT NULL,
  `longitude_gps` varchar(50) DEFAULT NULL,
  `code` char(5) DEFAULT NULL,
  PRIMARY KEY (`id_lieu`),
  UNIQUE KEY `intitule` (`intitule`),
  UNIQUE KEY `code` (`code`)
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String intitule;
	private String rue;
	private String codePostal;
	private String ville;
	private String informationComplementaire;
	private String latitude;
	private String longitude;
	
	public Lieu() {
		super();

	}
	
	public Lieu(int id, String intitule, String rue, String codePostal, String ville, String informationComplementaire,
			String latitude, String longitude) {
		super();
		this.id = id;
		this.intitule = intitule;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.informationComplementaire = informationComplementaire;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getInformationComplementaire() {
		return informationComplementaire;
	}
	public void setInformationComplementaire(String informationComplementaire) {
		this.informationComplementaire = informationComplementaire;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
