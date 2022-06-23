package bo;

import java.io.Serializable;

public class Reservataire implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
  CREATE TABLE IF NOT EXISTS `reservataires` (
  `id_reservataire` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `telephone` varchar(50) NOT NULL,
  `rue` varchar(200) DEFAULT NULL,
  `code_postal` char(5) DEFAULT NULL,
  `ville` varchar(50) DEFAULT NULL,
  `id_lieu` int(11) NOT NULL,
  PRIMARY KEY (`id_reservataire`),
  UNIQUE KEY `email` (`email`),
  KEY `id_lieu` (`id_lieu`)
	 */
	private int id;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String rue;
	private String codePostal;
	private String ville;
	private Lieu lieu;
	
	public Reservataire(int id, String nom, String prenom, String email, String telephone, String rue,
			String codePostal, String ville, Lieu lieu) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.lieu = lieu;
	}
	public Reservataire() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
	public Lieu getLieu() {
		return lieu;
	}
	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
	}
}
