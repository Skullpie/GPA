package bll;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import bo.Lieu;
import bo.PeriodeIndisponibiliteVehicule;
import bo.Reservataire;
import bo.Reservation;
import bo.Vehicule;
import dal.DAOFactory;
import dal.PeriodeIndispoDAO;
import dal.ReservationDAO;
import exceptions.BLLException;
import exceptions.DAOException;
import tools.Format;

public abstract class ReservationManager {

	private static Reservation reservation;

	public static void init() {
		reservation = null;
		reservation = new Reservation();
	}

	public static Reservation recuperationInstance() {
		return reservation;
	}

	public static List<Reservation> getall() throws BLLException {
		List<Reservation> list = new ArrayList<Reservation>();

		ReservationDAO rManager = DAOFactory.getReservationDAO();

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

	public static Reservation getById(int id) throws BLLException {
		Reservation r = null;
		ReservationDAO rManager = DAOFactory.getReservationDAO();

		try {
			r = rManager.selectById(id);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		return r;

	}

	public static void ajoutReservation(Reservation r) throws BLLException {
		ReservationDAO rManager = DAOFactory.getReservationDAO();
		try {
			rManager.insert(r);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}
	
	public static void supprimerReservation(Reservation r) throws BLLException {
		ReservationDAO rManager = DAOFactory.getReservationDAO();
		try {
			rManager.delete(r);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}
	

	public static void modifReservation(Reservation r) throws BLLException {
		ReservationDAO rManager = DAOFactory.getReservationDAO();
		try {
			rManager.update(r);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}

	public static void verifMotif(String smotif) throws Exception {
		if (smotif.length() > 50) {
			throw new Exception("Motif trop long (max: 50 caractères)");
		}
		reservation.setMotif(smotif);
	}

	public static void verifDateDebut(String sdateDebut) throws Exception {
		LocalDateTime dateDebut = null;
		if (sdateDebut.isEmpty()) {
			throw new Exception("Merci de saisir une date de début");
		}
		try {
			dateDebut = Format.convertStringToLocalDateTime(sdateDebut);
		} catch (ParseException e) {
			throw new Exception("Merci de saisir une date de début correcte");
		}
		reservation.setDateDebut(dateDebut);
	}

	public static void verifDateFin(String sdateFin) throws Exception {
		LocalDateTime dateFin = null;
		if (sdateFin.isEmpty()) {
			throw new Exception("Merci de saisir une date de fin");
		}
		try {
			dateFin = Format.convertStringToLocalDateTime(sdateFin);
		} catch (ParseException e) {
			throw new Exception("Merci de saisir une date de fin correcte");
		}
		reservation.setDateFin(dateFin);
	}

	public static void verifDate() throws Exception {

		if (reservation.getDateFin().isBefore(reservation.getDateDebut())) {
			reservation.setDateFin(null);
			throw new Exception("Veuillez saisir une date de fin supérieure à la date de début");
		}
	}

	public static void verifIndispo() throws Exception {
		PeriodeIndispoDAO pManager = DAOFactory.getPeriodeIndispoDAO();
		List<PeriodeIndisponibiliteVehicule> liste = null;

		try {
			liste = pManager.getAllByImmatriculation(reservation.getVehicule().getImmatriculation());
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		for (PeriodeIndisponibiliteVehicule p : liste) {
			if (((reservation.getDateDebut().equals(p.getDateDebut())
					|| (reservation.getDateDebut().isAfter(p.getDateDebut()))
							&& (reservation.getDateDebut().isBefore(p.getDateFin()))))) {
				throw new Exception("Le véhicule est indisponible pendant ce créneau");
			}
			if (reservation.getDateFin().isAfter(p.getDateDebut()) && ((reservation.getDateFin().equals(p.getDateFin())
					|| (reservation.getDateFin().isBefore(p.getDateFin()))))) {
				throw new Exception("Le véhicule est indisponible pendant ce créneau");
			}
			if (reservation.getDateDebut().isBefore(p.getDateDebut())
					&& reservation.getDateFin().isAfter(p.getDateFin())) {
				throw new Exception("Le véhicule est indisponible pendant ce créneau");
			}
		}
	}

	public static void verifChevauchement() throws Exception {
		ReservationDAO rManager = DAOFactory.getReservationDAO();
		List<Reservation> liste = null;
		try {
			liste = rManager.selectByImmat(reservation.getVehicule().getImmatriculation());
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		for (Reservation p : liste) {

			if (((reservation.getDateDebut().equals(p.getDateDebut())
					|| (reservation.getDateDebut().isAfter(p.getDateDebut()))
							&& (reservation.getDateDebut().isBefore(p.getDateFin()))))) {
				throw new Exception("Le véhicule est déjà réservé pendant ce créneau");
			}
			if (reservation.getDateFin().isAfter(p.getDateDebut()) && ((reservation.getDateFin().equals(p.getDateFin())
					|| (reservation.getDateFin().isBefore(p.getDateFin()))))) {
				throw new Exception("Le véhicule est déjà réservé pendant ce créneau");
			}
			if (reservation.getDateDebut().isBefore(p.getDateDebut())
					&& reservation.getDateFin().isAfter(p.getDateFin())) {
				throw new Exception("Le véhicule est déjà réservé pendant ce créneau");
			}

		}
	}

	public static void verifChevauchementModif(int id) throws Exception {
		ReservationDAO rManager = DAOFactory.getReservationDAO();
		List<Reservation> liste = null;
		try {
			liste = rManager.selectByImmat(reservation.getVehicule().getImmatriculation());
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		for (Reservation p : liste) {

			if (id!=p.getId()) {
				if (((reservation.getDateDebut().equals(p.getDateDebut())
						|| (reservation.getDateDebut().isAfter(p.getDateDebut()))
								&& (reservation.getDateDebut().isBefore(p.getDateFin()))))) {
					throw new Exception("Le véhicule est déjà réservé pendant ce créneau");
				}
				if (reservation.getDateFin().isAfter(p.getDateDebut())
						&& ((reservation.getDateFin().equals(p.getDateFin())
								|| (reservation.getDateFin().isBefore(p.getDateFin()))))) {
					throw new Exception("Le véhicule est déjà réservé pendant ce créneau");
				}
				if (reservation.getDateDebut().isBefore(p.getDateDebut())
						&& reservation.getDateFin().isAfter(p.getDateFin())) {
					throw new Exception("Le véhicule est déjà réservé pendant ce créneau");
				} 
			}
		}
		reservation.setId(id);
	}

	public static void verifCommentaire(String scommentaire) throws Exception {
		if (scommentaire.length() > 200) {
			throw new Exception("Commentaire trop long (max: 200 caractères)");
		}
		reservation.setCommentaire(scommentaire);
	}

	public static void verifVehicule(String immatriculation) throws Exception {
		Vehicule vehicule = null;
		try {
			vehicule = VehiculeManager.get(immatriculation);
		} catch (BLLException e) {
			throw new Exception("Merci de saisir un véhicule correct");
		}
		reservation.setVehicule(vehicule);
	}

	public static void verifLieux(String idLieu) throws Exception {
		Lieu lieu = null;
		int id = Integer.parseInt(idLieu);
		try {
			lieu = LieuManager.getLieu(id);
		} catch (BLLException e) {
			throw new Exception("Merci de saisir un lieu correct");
		}
		reservation.setLieu(lieu);
	}

	public static void verifReservataires(List<String> reservataires) throws Exception {
		Reservataire reservataire = new Reservataire();
		if (reservataires.size() != 0) {
			for (String sid : reservataires) {
				int id = Integer.parseInt(sid);
				try {
					reservataire = ReservataireManager.getById(id);
				} catch (BLLException e) {
					throw new Exception("Merci de saisir un réservataire correct");
				}
				reservation.addReservataire(reservataire);
			}
		} else {
			throw new Exception("Merci de saisir au moins un réservataire");
		}

	}
}
