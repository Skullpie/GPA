package bll;

import java.util.ArrayList;
import java.util.List;

import bo.Lieu;
import bo.Reservataire;
import dal.DAOFactory;
import dal.ReservataireDAO;
import exceptions.BLLException;
import exceptions.DAOException;

public abstract class ReservataireManager {

	private static Reservataire reservataire;

	public static void init() {
		reservataire = null;
		reservataire = new Reservataire();
	}

	public static Reservataire recuperationInstance() {
		return reservataire;
	}

	public static List<Reservataire> getall() throws BLLException {
		List<Reservataire> list = new ArrayList<Reservataire>();

		ReservataireDAO rManager = DAOFactory.getReservataireDAO();

		try {
			list = rManager.selectAll();
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		return list;
	}

	public static Reservataire getById(int id) throws BLLException {
		Reservataire reservataire = new Reservataire();

		ReservataireDAO rManager = DAOFactory.getReservataireDAO();

		try {
			reservataire = rManager.selectById(id);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		return reservataire;
	}

	public static void inserer(Reservataire r) throws BLLException {
		ReservataireDAO rManager = DAOFactory.getReservataireDAO();
		try {
			rManager.insert(r);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}

	public static void modifier(Reservataire r) throws BLLException {
		ReservataireDAO rManager = DAOFactory.getReservataireDAO();
		try {
			rManager.update(r);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}

	public static void supprimer(Reservataire r) throws BLLException {

		ReservataireDAO rManager = DAOFactory.getReservataireDAO();
		try {
			rManager.delete(r);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}

	public static void verifNom(String snom) throws Exception {
		if (snom != null) {
			if (snom.length() > 50) {
				throw new Exception("Nom trop long (max: 50 caractères)");
			}
		} else {
			throw new Exception("Merci de saisir un nom");
		}
		reservataire.setNom(snom);
	}

	public static void verifPrenom(String sprenom) throws Exception {
		if (sprenom != null) {
			if (sprenom.trim().length() > 50) {
				throw new Exception("Prénom trop long (max: 50 caractères)");
			}
		} else {
			throw new Exception("Merci de saisir un prénom");
		}
		reservataire.setPrenom(sprenom);
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
			throw new Exception("Merci de saisir un email");
		}
		reservataire.setEmail(email);
	}

	public static void verifTelephone(String telephone) throws Exception {
		if (telephone != null) {
			if (telephone.length() > 50) {
				throw new Exception("Téléphone trop long (max: 50 caractères)");
			}
			if (!telephone.matches("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$")) {
				throw new Exception("Merci de saisir un téléphone valide");
			}
		} else {
			throw new Exception("Merci de saisir un téléphone");
		}
		reservataire.setTelephone(telephone);
	}

	public static void verifRue(String rue) throws Exception {
		if (rue.length() > 200) {
			throw new Exception("Rue trop longue (max: 200 caractères)");
		}
		reservataire.setRue(rue);
	}

	public static void verifCodePostal(String codePostal) throws Exception {

		if (codePostal.length()>1) {
			if (!codePostal.matches("^(([0-8][0-9])|(9[0-7]))[0-9]{3}$")) {
				throw new Exception("Merci de saisir un code postal valide");
			} 
		}
		reservataire.setCodePostal(codePostal);
	}

	public static void verifVille(String ville) throws Exception {
		if (ville.length() > 50) {
			throw new Exception("Rue trop longue (max: 50 caractères)");
		}
		reservataire.setVille(ville);
	}

	public static void verifLieux(String idLieu) throws Exception {
		Lieu lieu = null;
		int id = Integer.parseInt(idLieu);
		try {
			lieu = LieuManager.getLieu(id);
		} catch (BLLException e) {
			throw new Exception("Merci de saisir un lieu correct");
		}
		reservataire.setLieu(lieu);
	}

	public static void verifId(String sid) throws Exception {
		Reservataire res = null;
		try {
			res = ReservataireManager.getById(Integer.parseInt(sid));
		} catch (NumberFormatException e) {
			throw new Exception("Identifiant réservataire erroné");
		} catch (BLLException e) {
			throw new Exception("Merci de saisir un réservataire correct");
		}
		reservataire.setId(res.getId());
		
	}

}
