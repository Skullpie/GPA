package ctrl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.LieuManager;
import bll.ReservataireManager;
import bll.VehiculeManager;
import bo.Lieu;
import bo.Reservataire;
import bo.Vehicule;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class ModifierVehiculeServlet
 */
@WebServlet(name = "ModifierVehiculeServlet", urlPatterns = "/auth/modificationvehicule")
public class ModifierVehiculeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String CHAMP_ID = "id";
	private final String CHAMP_IMMATRICULATION = "immatriculation";
	private final String CHAMP_DESCRIPTION = "description";
	private final String CHAMP_DATE_ACHAT = "dateAchat";
	private final String CHAMP_NOMBRE_PLACE = "nombrePlace";
	private final String CHAMP_VENDU = "vendu";
	private final String CHAMP_COPIE_CARTE_GRISE = "copieCarteGrise";
	private final String CHAMP_RESERVATAIRE = "idReservataire";
	private final String CHAMP_LIEU = "idLieu";
	private final String VEHICULE = "vehicule";

	/*
	 * instanciation d'une map d'erreurs
	 */
	ErrorContainer erreurs = new ErrorContainer();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifierVehiculeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String immat = request.getParameter("vehexist");
		Vehicule veh = null;
		if (immat != null) {
			try {
				veh = VehiculeManager.get(immat);
			} catch (BLLException e) {
				erreurs.addErreur("SystemError", e.getMessages().get(0));
			}
			request.setAttribute("veh", veh);
		}

		List<Reservataire> reservataires = null;
		List<Lieu> lieux = null;
		try {
			reservataires = ReservataireManager.getall();
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		try {
			lieux = LieuManager.getall();
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		request.setAttribute("reservataires", reservataires);
		request.setAttribute("lieux", lieux);
		RequestDispatcher rd = request.getRequestDispatcher(URLNavigation.URL_JSP_MODIF_VEH);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Vehicule vehicule = null;

		String id = request.getParameter(CHAMP_ID);
		String immatriculation = request.getParameter(CHAMP_IMMATRICULATION);
		String description = request.getParameter(CHAMP_DESCRIPTION);
		String date = request.getParameter(CHAMP_DATE_ACHAT);
		String nombrePlace = request.getParameter(CHAMP_NOMBRE_PLACE);
		String vendu = request.getParameter(CHAMP_VENDU);
		String copieCarteGrise = request.getParameter(CHAMP_COPIE_CARTE_GRISE);
		String idReservataire = request.getParameter(CHAMP_RESERVATAIRE);
		String idLieu = request.getParameter(CHAMP_LIEU);

		/**
		 * Vide la map d'erreurs
		 */
		erreurs.clear();
		/**
		 * Réinitialise l'instance de véhicule dans le manager
		 */
		VehiculeManager.init();

		verifInfos(id, immatriculation, description, date, nombrePlace, vendu, copieCarteGrise, idReservataire, idLieu);

		vehicule = VehiculeManager.recuperationInstance();
		if (erreurs.isOk()) {
			try {
				VehiculeManager.maj(vehicule);
			} catch (BLLException e) {
				erreurs.addErreur(VEHICULE, e.getMessage());
			}
		}

		request.setAttribute("veh", vehicule);

		if (erreurs.isOk()) {
			erreurs.setResultat("Véhicule bien enregistré");
			request.setAttribute("erreurs", erreurs);
			request.getRequestDispatcher(URLNavigation.URL_PAGE_HOME).forward(request, response);
		} else {
			erreurs.setResultat("Enregistrement impossible, un problème a eu lieu");
			request.setAttribute("erreurs", erreurs);
			doGet(request, response);
			return;
		}

	}

	private void verifInfos(String id,String immatriculation, String description, String date, String nombrePlace, String vendu,
			String copieCarteGrise, String idReservataire, String idLieu) {
		try {
			VehiculeManager.verifId(id);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_ID, e.getMessage());
		}
		try {
			VehiculeManager.verifImmat(immatriculation);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_IMMATRICULATION, e.getMessage());
		}
		VehiculeManager.verifDescription(description);
		try {
			VehiculeManager.verifDateAchat(date);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_DATE_ACHAT, e.getMessage());
		}
		try {
			VehiculeManager.verifNombrePlace(nombrePlace);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_NOMBRE_PLACE, e.getMessage());
		}
		VehiculeManager.verifCopieCarteGrise(copieCarteGrise);
		try {
			VehiculeManager.verifReservataire(idReservataire);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_RESERVATAIRE, e.getMessage());
		}
		try {
			VehiculeManager.verifLieu(idLieu);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_LIEU, e.getMessage());
		}

	}

}
