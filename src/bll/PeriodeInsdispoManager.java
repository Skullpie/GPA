package bll;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import bo.MotifIndisponibilite;
import bo.PeriodeIndisponibiliteVehicule;
import bo.Reservation;
import bo.Vehicule;
import dal.DAOFactory;
import dal.PeriodeIndispoDAO;
import dal.ReservationDAO;
import exceptions.BLLException;
import exceptions.DAOException;
import tools.Format;

public abstract class PeriodeInsdispoManager {

	private static PeriodeIndisponibiliteVehicule indispo;

	public static void init() {
		indispo = null;
		indispo = new PeriodeIndisponibiliteVehicule();
	}

	public static PeriodeIndisponibiliteVehicule recuperationInstance() {
		return indispo;
	}

	public static PeriodeIndisponibiliteVehicule get(int id) throws BLLException {

		PeriodeIndispoDAO pManager = DAOFactory.getPeriodeIndispoDAO();

		try {
			indispo = pManager.selectById(id);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		return indispo;
	}

	public static List<PeriodeIndisponibiliteVehicule> getAll() throws BLLException {
		List<PeriodeIndisponibiliteVehicule> lst = new ArrayList<PeriodeIndisponibiliteVehicule>();

		PeriodeIndispoDAO pManager = DAOFactory.getPeriodeIndispoDAO();

		try {
			lst = pManager.selectAll();
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		return lst;
	}

	public static List<PeriodeIndisponibiliteVehicule> getAllByImmat(String Immatriculation) throws BLLException {
		List<PeriodeIndisponibiliteVehicule> lst = new ArrayList<PeriodeIndisponibiliteVehicule>();

		PeriodeIndispoDAO pManager = DAOFactory.getPeriodeIndispoDAO();

		try {
			lst = pManager.getAllByImmatriculation(Immatriculation);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		return lst;
	}

	public static void maj(PeriodeIndisponibiliteVehicule piv) throws BLLException {
		PeriodeIndispoDAO pManager = DAOFactory.getPeriodeIndispoDAO();

		try {
			pManager.update(piv);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}

	public static void inserer(PeriodeIndisponibiliteVehicule p) throws BLLException {

		PeriodeIndispoDAO pManager = DAOFactory.getPeriodeIndispoDAO();

		try {
			pManager.insert(p);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}
	public static void supprimer(PeriodeIndisponibiliteVehicule p) throws BLLException {
		PeriodeIndispoDAO pManager = DAOFactory.getPeriodeIndispoDAO();
		try {
			pManager.delete(p);
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
	}

	public static void verifMotif(String smotif) throws Exception {
		MotifIndisponibilite motif = null;
		try {
			motif = MotifManager.get(Integer.parseInt(smotif));
		} catch (NumberFormatException e) {
			throw new Exception("Merci de saisir un motif correct");
		} catch (BLLException e) {
			throw new Exception("Merci de saisir un motif correct");
		}
		indispo.setMotifIndisponibilite(motif);
	}

	public static void verifVehicule(String immatriculation) throws Exception {
		Vehicule vehicule = null;
		try {
			vehicule = VehiculeManager.get(immatriculation);
		} catch (BLLException e) {
			throw new Exception("Merci de saisir un véhicule correct");
		}
		indispo.setVehicule(vehicule);
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
		indispo.setDateDebut(dateDebut);
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
		indispo.setDateFin(dateFin);
	}

	public static void verifDate() throws Exception {

		if (indispo.getDateFin().isBefore(indispo.getDateDebut())) {
			indispo.setDateFin(null);
			throw new Exception("Veuillez saisir une date de fin supérieure à la date de début");
		}
	}

	public static void verifChevauchement() throws Exception {
		PeriodeIndispoDAO pManager = DAOFactory.getPeriodeIndispoDAO();
		List<PeriodeIndisponibiliteVehicule> liste = null;

		try {
			liste = pManager.getAllByImmatriculation(indispo.getVehicule().getImmatriculation());
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		for (PeriodeIndisponibiliteVehicule p : liste) {
			if (((indispo.getDateDebut().equals(p.getDateDebut()) || (indispo.getDateDebut().isAfter(p.getDateDebut()))
					&& (indispo.getDateDebut().isBefore(p.getDateFin()))))) {
				throw new Exception("Le créneau souhaité est déjà partiellement ou totalement occupé");
			}
			if (indispo.getDateFin().isAfter(p.getDateDebut()) && ((indispo.getDateFin().equals(p.getDateFin())
					|| (indispo.getDateFin().isBefore(p.getDateFin()))))) {
				throw new Exception("Le créneau souhaité est déjà partiellement ou totalement occupé");
			}
			if (indispo.getDateDebut().isBefore(p.getDateDebut()) && indispo.getDateFin().isAfter(p.getDateFin())) {
				throw new Exception("Une partie de ce créneau est déjà occupée");
			}
		}

		ReservationDAO rManager = DAOFactory.getReservationDAO();
		List<Reservation> liste2 = null;
		try {
			liste2 = rManager.selectByImmat(indispo.getVehicule().getImmatriculation());
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		for (Reservation p : liste2) {

			if (((indispo.getDateDebut().equals(p.getDateDebut()) || (indispo.getDateDebut().isAfter(p.getDateDebut()))
					&& (indispo.getDateDebut().isBefore(p.getDateFin()))))) {
				throw new Exception("Le véhicule est déjà réservé pendant ce créneau");
			}
			if (indispo.getDateFin().isAfter(p.getDateDebut()) && ((indispo.getDateFin().equals(p.getDateFin())
					|| (indispo.getDateFin().isBefore(p.getDateFin()))))) {
				throw new Exception("Le véhicule est déjà réservé pendant ce créneau");
			}
			if (indispo.getDateDebut().isBefore(p.getDateDebut()) && indispo.getDateFin().isAfter(p.getDateFin())) {
				throw new Exception("Le véhicule est déjà réservé pendant ce créneau");
			}

		}

	}

	public static void verifChevauchementModif(int id) throws Exception {
		PeriodeIndispoDAO pManager = DAOFactory.getPeriodeIndispoDAO();
		List<PeriodeIndisponibiliteVehicule> liste = null;

		try {
			liste = pManager.getAllByImmatriculation(indispo.getVehicule().getImmatriculation());
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}

		for (PeriodeIndisponibiliteVehicule p : liste) {
			if (id != p.getIdIndisponibilite()) {
				if (((indispo.getDateDebut().equals(p.getDateDebut())
						|| (indispo.getDateDebut().isAfter(p.getDateDebut()))
								&& (indispo.getDateDebut().isBefore(p.getDateFin()))))) {
					throw new Exception("Le créneau souhaité est déjà partiellement ou totalement occupé");
				}
				if (indispo.getDateFin().isAfter(p.getDateDebut()) && ((indispo.getDateFin().equals(p.getDateFin())
						|| (indispo.getDateFin().isBefore(p.getDateFin()))))) {
					throw new Exception("Le créneau souhaité est déjà partiellement ou totalement occupé");
				}
				if (indispo.getDateDebut().isBefore(p.getDateDebut()) && indispo.getDateFin().isAfter(p.getDateFin())) {
					throw new Exception("Une partie de ce créneau est déjà occupée");
				}
			}
		}
		ReservationDAO rManager = DAOFactory.getReservationDAO();
		List<Reservation> liste2 = null;
		try {
			liste2 = rManager.selectByImmat(indispo.getVehicule().getImmatriculation());
		} catch (DAOException e) {
			e.printStackTrace();
			BLLException bllex = new BLLException();
			bllex.addMessage(e.getMessage());
			throw bllex;
		}
		for (Reservation p : liste2) {

			if (((indispo.getDateDebut().equals(p.getDateDebut()) || (indispo.getDateDebut().isAfter(p.getDateDebut()))
					&& (indispo.getDateDebut().isBefore(p.getDateFin()))))) {
				throw new Exception("Le véhicule est déjà réservé pendant ce créneau");
			}
			if (indispo.getDateFin().isAfter(p.getDateDebut()) && ((indispo.getDateFin().equals(p.getDateFin())
					|| (indispo.getDateFin().isBefore(p.getDateFin()))))) {
				throw new Exception("Le véhicule est déjà réservé pendant ce créneau");
			}
			if (indispo.getDateDebut().isBefore(p.getDateDebut()) && indispo.getDateFin().isAfter(p.getDateFin())) {
				throw new Exception("Le véhicule est déjà réservé pendant ce créneau");
			}

		}
		indispo.setIdIndisponibilite(id);
	}
}
