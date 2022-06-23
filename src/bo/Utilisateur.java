package bo;

import java.io.Serializable;

public class Utilisateur implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
 	CREATE TABLE IF NOT EXISTS `utilisateurs` (
  	`id_utilisateur` int(11) NOT NULL AUTO_INCREMENT,
  	`pseudo` varchar(20) NOT NULL,
  	`email` varchar(50) NOT NULL,
  	`mot_passe` varchar(50) NOT NULL,
  	`role` varchar(20) NOT NULL
	 */

	private int id;
	private String pseudo;
	private String email;
	private String motDePasse;
	private String role;
	
	
	
	public Utilisateur(int id, String pseudo, String email, String motDePasse, String role) {
		super();
		this.setId(id);
		this.setPseudo(pseudo);
		this.setEmail(email);
		this.setMotDePasse(motDePasse);
		this.setRole(role);
		
	}
	public Utilisateur() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
