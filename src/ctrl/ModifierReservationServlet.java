package ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.LieuManager;
import bll.ReservataireManager;
import bll.ReservationManager;
import bll.VehiculeManager;
import bo.Lieu;
import bo.Reservataire;
import bo.Reservation;
import bo.Vehicule;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class ModifierReservation
 */
@WebServlet(name = "ModifierReservation", urlPatterns = "/auth/modificationReservation")
public class ModifierReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String CHAMP_MOTIF = "motif";
	private final String CHAMP_DATE_DEBUT = "dateDebut";
	private final String CHAMP_DATE_FIN = "dateFin";
	private final String CHAMP_DATE = "date";
	private final String CHAMP_CHEVAUCHEMENT = "chevauchement";
	private final String CHAMP_COMMENTAIRE = "commentaire";
	private final String CHAMP_VEHICULE = "immatriculation";
	private final String CHAMP_LIEU = "lieu";
	private final String CHAMP_RESERVATAIRE = "reservataire";
	private final String CHAMP_SYSTEM = "system";

	/*
	 * instanciation d'une map d'erreurs
	 */
	ErrorContainer erreurs = new ErrorContainer();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierReservationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Lieu> lieux = null;
		List<Vehicule> vehicules = null;
		List<Reservataire> reservataires = null;
		Reservation resa = null;
		
		try {
			resa = ReservationManager.getById(Integer.parseInt(request.getParameter("id")));
		} catch (NumberFormatException e1) {
			erreurs.addErreur("SystemError", e1.getMessage());
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		request.setAttribute("resa", resa);

		try {
			lieux = LieuManager.getall();
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		try {
			vehicules = VehiculeManager.getall();
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		try {
			reservataires = ReservataireManager.getall();
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		request.setAttribute("lieux", lieux);
		request.setAttribute("vehicules", vehicules);
		request.setAttribute("reservataires", reservataires);


		request.getRequestDispatcher(URLNavigation.URL_JSP_MODIF_RESERVATION).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Reservation reservation = null;

		String smotif = request.getParameter(CHAMP_MOTIF);
		String sdatedebut = request.getParameter(CHAMP_DATE_DEBUT);
		String sdatefin = request.getParameter(CHAMP_DATE_FIN);
		String scommentaire = request.getParameter(CHAMP_COMMENTAIRE);
		String svehicule = request.getParameter(CHAMP_VEHICULE);
		String slieu = request.getParameter(CHAMP_LIEU);
		List<String> reservataires = recupReservataires(request, response);
		String sid = request.getParameter("id");

		/**
		 * Vide la map d'erreurs
		 */
		erreurs.clear();

		/**
		 * Réinitialise l'instance de réservation dans le manager
		 */
		ReservationManager.init();

		verifInfos(smotif, sdatedebut, sdatefin, scommentaire, svehicule, slieu, reservataires, sid);

		reservation = ReservationManager.recuperationInstance();
		if (erreurs.isOk()) {
			try {
				ReservationManager.modifReservation(reservation);
			} catch (BLLException e) {
				erreurs.addErreur(CHAMP_SYSTEM, e.getMessage());
			}
		}
		request.setAttribute("resa", reservation);

		if (erreurs.isOk()) {
			erreurs.setResultat("Modification bien enregistrée");
			request.setAttribute("erreurs", erreurs);
			request.getRequestDispatcher(URLNavigation.URL_PAGE_HOME).forward(request, response);
		} else {
			erreurs.setResultat("Enregistrement impossible, un problème a eu lieu");
			request.setAttribute("erreurs", erreurs);
			doGet(request, response);
			return;
		}
	}

	private void verifInfos(String smotif, String sdatedebut, String sdatefin, String scommentaire, String svehicule,
			String slieu, List<String> reservataires, String sid) {
		try {
			ReservationManager.verifMotif(smotif);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_MOTIF, e.getMessage());
		}
		try {
			ReservationManager.verifDateDebut(sdatedebut);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_DATE_DEBUT, e.getMessage());
		}
		try {
			ReservationManager.verifDateFin(sdatefin);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_DATE_FIN, e.getMessage());
		}
		try {
			ReservationManager.verifDate();
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_DATE, e.getMessage());
		}
		try {
			ReservationManager.verifVehicule(svehicule);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_VEHICULE, e.getMessage());
		}
		try {
			ReservationManager.verifChevauchementModif(Integer.parseInt(sid));;
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_CHEVAUCHEMENT, e.getMessage());
		}
		try {
			ReservationManager.verifIndispo();
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_CHEVAUCHEMENT, e.getMessage());
		}
		try {
			ReservationManager.verifCommentaire(scommentaire);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_COMMENTAIRE, e.getMessage());
		}
		try {
			ReservationManager.verifLieux(slieu);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_LIEU, e.getMessage());
		}
		try {
			ReservationManager.verifReservataires(reservataires);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_RESERVATAIRE, e.getMessage());
		}

	}

	private List<String> recupReservataires(HttpServletRequest request, HttpServletResponse response) {
		List<String> reservataires = new ArrayList<String>();
		for (int i = 0; i <= 9; i++) {
			String r = request.getParameter(CHAMP_RESERVATAIRE + i);
			if (r != null) {
				if (!r.equalsIgnoreCase("rien")) {
					reservataires.add(r);
				}
			}
		}
		return reservataires;
	}

}
