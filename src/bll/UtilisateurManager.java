package bll;


import java.util.List;

import bo.Utilisateur;
import dal.DAOFactory;
import dal.UtilisateurDAO;
import exceptions.BLLException;
import exceptions.DAOException;



public abstract class UtilisateurManager{
	private static Utilisateur utilisateur;
	
	public static void init() {
		utilisateur = null;
		utilisateur = new Utilisateur();
	}
	
	public static Utilisateur recuperationInstance() {
		return utilisateur;
	}

	/**
	 * 
	 * @param pseudo String
	 * @param motDePasse String
	 * @return Utilisateur | null
	 * @throws BLLException
	 */
	public static Utilisateur authenticate(String pseudo, String motDePasse) throws BLLException {
		
		UtilisateurDAO uManager = DAOFactory.getUtilisateurDAO();
		
		Utilisateur u = null;
		
		try {
			u = uManager.authenticate(pseudo, motDePasse);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		
		return u;
	}
	public static List<Utilisateur> getAll() throws BLLException{
		UtilisateurDAO uManager = DAOFactory.getUtilisateurDAO();
		List<Utilisateur> liste = null;
		try {
			liste = uManager.selectAll();
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		return liste;
	}
	public static Utilisateur getById(int id) throws BLLException {
		UtilisateurDAO uManager = DAOFactory.getUtilisateurDAO();
		Utilisateur u = null;
		try {
			u = uManager.selectById(id);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		return u;
	}
	public static void insererUtilisateur(Utilisateur u) throws BLLException {
		UtilisateurDAO uManager = DAOFactory.getUtilisateurDAO();
		try {
			uManager.insert(u);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}
	public static void modifierUtilisateur(Utilisateur u) throws BLLException {
		UtilisateurDAO uManager = DAOFactory.getUtilisateurDAO();
		try {
			uManager.update(u);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}
	public static void supprimerUtilisateur(Utilisateur u) throws BLLException {
		UtilisateurDAO uManager = DAOFactory.getUtilisateurDAO();
		try {
			uManager.delete(u);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}
	public static void verifPseudo(String pseudo) throws Exception {
		if (pseudo != null) {
			if (pseudo.length() > 20) {
				throw new Exception("Pseudo trop long (max: 20 caractères)");
			}
		} else {
			throw new Exception("Merci de saisir un pseudo");
		}
		utilisateur.setPseudo(pseudo);
	}
	public static void verifEmail(String email) throws Exception {
		if (email != null) {
			if (email.length() > 50) {
				throw new Exception("Email trop long (max: 50 caractères)");
			}
			if (!email.matches(
					"(?:[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
				throw new Exception("Merci de saisir un email valide");
			}
		} else {
			throw new Exception("Merci de saisir un pseudo");
		}
		utilisateur.setEmail(email);
	}
	public static void verifMotDePasse(String mdp) throws Exception {
		if (mdp != null) {
			if (mdp.length() > 50) {
				throw new Exception("pseudo trop long (max: 50 caractères)");
			}
		} else {
			throw new Exception("Merci de saisir un pseudo");
		}
		utilisateur.setMotDePasse(mdp);
	}
	public static void verifRole(String role) throws Exception {
		if (role != null) {
			if (!role.equalsIgnoreCase("user") && !role.equalsIgnoreCase("admin")) {
				throw new Exception("Merci de saisir un rôle correct");
			}
		} else {
			throw new Exception("Merci de saisir un rôle");
		}
		utilisateur.setRole(role);
	}
	public static void verifId(String id) throws Exception{
		Utilisateur u = null;
		try {
			u = UtilisateurManager.getById(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			throw new Exception("Identifiant utilisateur erroné");

		} catch (BLLException e) {
			throw new Exception("Merci de saisir un utilisateur correct");
		}
		if (u == null) {
				throw new Exception("Merci de saisir un utilisateur correct");
			}
		utilisateur.setId(u.getId());;
	}
	
}
