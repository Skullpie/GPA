package bll;

import java.util.ArrayList;
import java.util.List;

import bo.Lieu;
import dal.DAOFactory;
import dal.LieuDAO;
import exceptions.BLLException;
import exceptions.DAOException;

public abstract class LieuManager {
	
	private static Lieu lieu;
	
	public static void init() {
		lieu=null;
		lieu= new Lieu();
	}
	public static Lieu recuperationInstance() {
		return lieu;
	}
	
	public static List<Lieu> getall() throws BLLException
	{
		List<Lieu> list = new ArrayList<Lieu>();
		
		LieuDAO lManager = DAOFactory.getLieuDAO();
		
		try {
			list = lManager.selectAll();
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		return list;
	}
	public static Lieu getLieu(int id) throws BLLException
	{
		Lieu lieu = new Lieu();
		LieuDAO lManager = DAOFactory.getLieuDAO();
		try {
			lieu = lManager.selectById(id);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		return lieu;
	}
	public static void insertLieu(Lieu l) throws BLLException {
		LieuDAO lManager = DAOFactory.getLieuDAO();
		try {
			lManager.insert(l);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}
	public static void majLieu(Lieu l) throws BLLException {
		LieuDAO lManager = DAOFactory.getLieuDAO();
		try {
			lManager.update(l);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}
	public static void supprimerLieu(Lieu l) throws BLLException {
		LieuDAO lManager = DAOFactory.getLieuDAO();
		try {
			lManager.delete(l);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}
	public static void verifIntitule(String intitule) throws Exception {
		if (intitule != null) {
			if (intitule.length() > 50) {
				throw new Exception("Intitulé trop long (max: 50 caractères)");
			}
		} else {
			throw new Exception("Merci de saisir un intitulé");
		}
		lieu.setIntitule(intitule);
	}

	public static void verifRue(String rue) throws Exception {
		if (rue != null) {
			if (rue.length() > 50) {
				throw new Exception("Rue trop longue (max: 50 caractères)");
			}
		} else {
			throw new Exception("Merci de saisir une rue");
		}
		lieu.setRue(rue);
	}

	public static void verifCodePostal(String codePostal) throws Exception {

		if (codePostal != null) {
			if (!codePostal.matches("^(([0-8][0-9])|(9[0-7]))[0-9]{3}$")) {
				throw new Exception("Merci de saisir un code postal valide");
			} 
		}else {
			throw new Exception("Merci de saisir un code postal");
		}
		lieu.setCodePostal(codePostal);
	}

	public static void verifVille(String ville) throws Exception {
		if (ville != null) {
			if (ville.length() > 50) {
				throw new Exception("ville trop longue (max: 50 caractères)");
			}
		} else {
			throw new Exception("Merci de saisir une ville");
		}
		lieu.setVille(ville);
	}

	public static void verifInformationComplementaire(String informationComplementaire) throws Exception {

		if (informationComplementaire.length() > 200) {
			throw new Exception("Information complementaire trop longue (max: 200 caractères)");
		}
		lieu.setInformationComplementaire(informationComplementaire);
	}

	public static void verifLatitude(String latitude) throws Exception {

		if (latitude.length() > 50) {
			throw new Exception("Latitude trop longue (max: 50 caractères)");
		}
		lieu.setLatitude(latitude);
	}
	public static void verifLongitude(String longitude) throws Exception {

		if (longitude.length() > 50) {
			throw new Exception("Longitude trop longue (max: 50 caractères)");
		}
		lieu.setLongitude(longitude);
	}
	public static void verifId(String id) throws Exception {
		Lieu l = null;
		try {
			l = LieuManager.getLieu(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			throw new Exception("Identifiant lieu erroné");
		} catch (BLLException e) {
			throw new Exception("Merci de saisir un réservataire correct");
		}
		lieu.setId(l.getId());
	}
}
