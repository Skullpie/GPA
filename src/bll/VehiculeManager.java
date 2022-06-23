package bll;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


import bo.FluxJsonBis;
import bo.PeriodeIndisponibiliteVehicule;
import bo.Reservation;
import bo.Vehicule;
import dal.DAOFactory;
import dal.LieuDAO;
import dal.ReservataireDAO;
import dal.VehiculeDAO;
import exceptions.BLLException;
import exceptions.DAOException;
import tools.Format;

public abstract class VehiculeManager {

	private static Vehicule vehicule;

	
	/**
	 * Méthodes d'instanciation et récupération d'instance
	 */
	public static void init() {
		vehicule = null;
		vehicule = new Vehicule();
	}

	public static Vehicule recuperationInstance() {
		return vehicule;
	}

	
	/**
	 * Méthode de vérification
	 */
	public static void verifLieu(String idLieu) throws Exception {
		int id = Integer.parseInt(idLieu);
		LieuDAO ld = DAOFactory.getLieuDAO();

		try {
			vehicule.setLieu(ld.selectById(id));
		} catch (DAOException e) {
			throw new Exception("Lieu invalide");
		}
	}

	public static void verifReservataire(String idReservataire) throws Exception {
		int id = Integer.parseInt(idReservataire);
		if (id != 0) {
			ReservataireDAO rd = DAOFactory.getReservataireDAO();

			try {
				vehicule.setReservataire(rd.selectById(id));
			} catch (DAOException e) {
				throw new Exception("Reservataire invalide");
			}
		}

	}

	public static void verifCopieCarteGrise(String copieCarteGrise) {
		vehicule.setCopieCarteGrise(copieCarteGrise);
	}

	public static void verifDescription(String description) {
		vehicule.setDescription(description);
	}

	public static void verifImmat(String immatriculation) throws Exception {
		if (immatriculation != null) {
			if (!immatriculation.matches("[a-zA-Z]{2}-[0-9]{3}-[a-zA-Z]{2}")
					&& !immatriculation.matches("[0-9]{2,4} ?[a-zA-Z]{2,3} ?[0-9]{2}")) {
				throw new Exception("Merci de saisir une immatriculation valide");
			}
		} else {
			throw new Exception("Merci de saisir une immatriculation");
		}
		VehiculeManager.vehicule.setImmatriculation(immatriculation);
	}
	
	public static void verifId(String id) throws Exception {
		try {
			VehiculeManager.vehicule.setId(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			throw new Exception("Merci de laisser l'id d'origine");
		}
	}

	public static void verifDateAchat(String sdateAchat) throws Exception {
		Date dateAchat;
		if (sdateAchat == null) {
			throw new Exception("Merci de saisir une date d'achat");
		} else {

			try {
				dateAchat = Date.valueOf(sdateAchat);
			} catch (ParseException e) {
				throw new Exception("Merci de saisir une date d'achat correcte");
			}
			if (dateAchat.after(new Date(System.currentTimeMillis()))) {
				throw new Exception("Merci d'enregistrer le véhicule quand il sera acheté");
			}
			vehicule.setDateAchat(dateAchat);
		}
	}

	public static void verifNombrePlace(String nombrePlace) throws Exception {
		int nbPlace = 0;
		if (nombrePlace == null) {
			throw new Exception("Merci de saisir un nombre de place");
		} else {
			try {
				nbPlace = Integer.parseInt(nombrePlace);
			} catch (NumberFormatException e) {
				throw new Exception("Merci de saisir un nombre de place correct");
			}
			if (nbPlace >= 9 || nbPlace <= 0) {
				throw new Exception("Merci de saisir un nombre de place cohérent avec la carte grise du véhicule");
			}
			vehicule.setNombrePlace(nbPlace);

		}
	}

	
	/**
	 * Méthodes du CRUD
	 */
	
	public static void ajouterVehicule(Vehicule veh) throws BLLException {

		VehiculeDAO vManager = DAOFactory.getVehiculeDAO();

		try {
			vManager.insert(veh);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}

	public static void delete(Vehicule veh) throws BLLException {
		VehiculeDAO vManager = DAOFactory.getVehiculeDAO();

		try {
			vManager.delete(veh);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}
	public static void reinstate(Vehicule veh) throws BLLException {
		VehiculeDAO vManager = DAOFactory.getVehiculeDAO();

		try {
			vManager.reinstate(veh);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}

	public static List<Vehicule> getall() throws BLLException {
		List<Vehicule> list = new ArrayList<Vehicule>();

		VehiculeDAO vManager = DAOFactory.getVehiculeDAO();

		try {
			list = vManager.selectAll();
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		return list;
	}
	public static List<Vehicule> getVehiculeDispo (String dateDebut, String dateFin) throws BLLException{
		List<Reservation> reservations = new ArrayList<Reservation>();
		List<PeriodeIndisponibiliteVehicule> indispos = new ArrayList<PeriodeIndisponibiliteVehicule>();
		List<FluxJsonBis> flux = new ArrayList<FluxJsonBis>();
		List<Vehicule>vehs = new ArrayList<Vehicule>();
		List<Vehicule>vehsdispos = new ArrayList<Vehicule>();
		
		FluxJsonBis json = null;
		LocalDateTime debut = null;
		LocalDateTime fin = null;
		
			try {
				vehs = VehiculeManager.getall();
			} catch (BLLException e) {
				e.printStackTrace();
				BLLException bllex = new BLLException();
				bllex.addMessage(e.getMessage());
				throw bllex;
			}
			for (Vehicule vehicule : vehs) {
				vehsdispos.add(vehicule);
			}
		try {
			reservations = ReservationManager.getall();
		} catch (BLLException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		try {
			indispos = PeriodeInsdispoManager.getAll();
		} catch (BLLException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		
		for (PeriodeIndisponibiliteVehicule piv : indispos) {
			json = new FluxJsonBis();
			json.setDateDebut(piv.getDateDebut());
			json.setDateFin(piv.getDateFin());
			json.setImmatriculation(piv.getVehicule().getImmatriculation());
			flux.add(json);
		}
		for (Reservation resa : reservations) {
			json = new FluxJsonBis();
			json.setDateDebut(resa.getDateDebut());
			json.setDateFin(resa.getDateFin());
			json.setImmatriculation(resa.getVehicule().getImmatriculation());
			flux.add(json);
		}
		debut = Format.convertStringToLocalDateTime(dateDebut);
		fin = Format.convertStringToLocalDateTime(dateFin);
		
		for (FluxJsonBis p : flux) {
			boolean ok = true;
			if (((debut.equals(p.getDateDebut()) || (debut.isAfter(p.getDateDebut())) && (debut.isBefore(p.getDateFin()))))) {
				ok = false;
			}
			if (fin.isAfter(p.getDateDebut()) && ((fin.equals(p.getDateFin()) || (fin.isBefore(p.getDateFin())))) ) {
				ok = false;
			}
			if (debut.isBefore(p.getDateDebut()) && fin.isAfter(p.getDateFin())) {
				ok = false;
			}
			if (!ok) {
				for (Vehicule vehicule : vehs) {
					if (p.getImmatriculation().equals(vehicule.getImmatriculation())) {
						if (vehsdispos.contains(vehicule)) {
							vehsdispos.remove(vehicule);
						}
					}
				}
			}
		}
		return vehsdispos;
		
	}

	public static Vehicule get(String immatriculation) throws BLLException {
		Vehicule v = new Vehicule();

		VehiculeDAO vManager = DAOFactory.getVehiculeDAO();

		try {
			v = vManager.selectByImmatricualtion(immatriculation);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		return v;
	}

	public static void maj(Vehicule veh) throws BLLException {
		VehiculeDAO vManager = DAOFactory.getVehiculeDAO();

		try {
			vManager.update(veh);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}

}
