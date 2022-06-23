package bll;

import java.util.ArrayList;
import java.util.List;

import bo.Campus;
import dal.CampusDAO;
import dal.DAOFactory;
import exceptions.BLLException;
import exceptions.DAOException;

public abstract class CampusManager {

	private static Campus campus;

	public static void init() {
		campus = null;
		campus = new Campus();
	}

	public static Campus recuperationInstance() {
		return campus;
	}

	public static List<Campus> getall() throws BLLException {
		List<Campus> list = new ArrayList<Campus>();

		CampusDAO cManager = DAOFactory.getCampusDAO();

		try {
			list = cManager.selectAll();
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		return list;
	}

	public static Campus get(int id) throws BLLException {
		Campus c = new Campus();

		CampusDAO cManager = DAOFactory.getCampusDAO();

		try {
			c = cManager.selectById(id);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		return c;
	}

	public static void insertCampus(Campus c) throws BLLException {
		CampusDAO cManager = DAOFactory.getCampusDAO();
		try {
			cManager.insert(c);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}

	public static void majCampus(Campus c) throws BLLException {
		CampusDAO cManager = DAOFactory.getCampusDAO();
		try {
			cManager.update(c);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}

	public static void supprimerCampus(Campus c) throws BLLException {
		CampusDAO cManager = DAOFactory.getCampusDAO();
		try {
			cManager.delete(c);
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
		campus.setIntitule(intitule);
	}

	public static void verifRue(String rue) throws Exception {
		if (rue != null) {
			if (rue.length() > 50) {
				throw new Exception("Rue trop longue (max: 50 caractères)");
			}
		} else {
			throw new Exception("Merci de saisir une rue");
		}
		campus.setRue(rue);
	}

	public static void verifCodePostal(String codePostal) throws Exception {

		if (codePostal != null) {
			if (!codePostal.matches("^(([0-8][0-9])|(9[0-7]))[0-9]{3}$")) {
				throw new Exception("Merci de saisir un code postal valide");
			} 
		}else {
			throw new Exception("Merci de saisir un code postal");
		}
		campus.setCodePostal(codePostal);
	}

	public static void verifVille(String ville) throws Exception {
		if (ville != null) {
			if (ville.length() > 50) {
				throw new Exception("ville trop longue (max: 50 caractères)");
			}
		} else {
			throw new Exception("Merci de saisir une ville");
		}
		campus.setVille(ville);
	}

	public static void verifInformationComplementaire(String informationComplementaire) throws Exception {

		if (informationComplementaire.length() > 200) {
			throw new Exception("Information complementaire trop longue (max: 200 caractères)");
		}
		campus.setInformationComplementaire(informationComplementaire);
	}

	public static void verifLatitude(String latitude) throws Exception {

		if (latitude.length() > 50) {
			throw new Exception("Latitude trop longue (max: 50 caractères)");
		}
		campus.setLatitude(latitude);
	}
	public static void verifLongitude(String longitude) throws Exception {

		if (longitude.length() > 50) {
			throw new Exception("Longitude trop longue (max: 50 caractères)");
		}
		campus.setLongitude(longitude);
	}
	public static void verifCode(String code) throws Exception {

		if (code.length() > 5) {
			throw new Exception("code trop long (max: 5 caractères)");
		}
		campus.setCode(code);
	}
	public static void verifId(String id) throws Exception {
		Campus c = null;
		try {
			c = CampusManager.get(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			throw new Exception("Identifiant lieu erroné");
		} catch (BLLException e) {
			throw new Exception("Merci de saisir un réservataire correct");
		}
		campus.setId(c.getId());
	}
}
