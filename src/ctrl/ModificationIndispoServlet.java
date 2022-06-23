package ctrl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.MotifManager;
import bll.PeriodeInsdispoManager;
import bll.VehiculeManager;
import bo.MotifIndisponibilite;
import bo.PeriodeIndisponibiliteVehicule;
import bo.Vehicule;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class ModificationIndispoServlet
 */
@WebServlet(name = "ModificationIndispoServlet", urlPatterns = { "/auth/modifierIndispo" })
public class ModificationIndispoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String CHAMP_ID = "id";
	private final String CHAMP_IMMATRICULATION = "immatriculation";
	private final String CHAMP_MOTIF = "idMotif";
	private final String CHAMP_DATE_DEBUT = "dateDebut";
	private final String CHAMP_DATE_FIN = "dateFin";
	private final String INDISPO = "indispo";
	private final String COMPARE_DATE = "compareDate";
	private final String CHEVAUCHEMENT = "chevauchement";

	ErrorContainer erreurs = new ErrorContainer();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModificationIndispoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<MotifIndisponibilite> motifs = null;
		List<Vehicule> vehicules = null;
		PeriodeIndisponibiliteVehicule piv = null;

		String sid = request.getParameter("id");

		try {
			piv = PeriodeInsdispoManager.get(Integer.parseInt(sid));
		} catch (NumberFormatException e) {
			erreurs.addErreur("SystemError", e.getMessage());
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}

		try {
			motifs = MotifManager.getAll();
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		try {
			vehicules = VehiculeManager.getall();
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		request.setAttribute("vehicules", vehicules);
		request.setAttribute("motifs", motifs);
		request.setAttribute("indispo", piv);
		RequestDispatcher rd = request.getRequestDispatcher(URLNavigation.URL_JSP_MODIFICATION_INDISPO);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PeriodeIndisponibiliteVehicule piv = null;

		String smotif = request.getParameter(CHAMP_MOTIF);
		String sdateDebut = request.getParameter(CHAMP_DATE_DEBUT);
		String sdateFin = request.getParameter(CHAMP_DATE_FIN);
		String immatriculation = request.getParameter(CHAMP_IMMATRICULATION);
		String sid = request.getParameter(CHAMP_ID);

		erreurs.clear();

		PeriodeInsdispoManager.init();

		verifInfos(smotif, sdateDebut, sdateFin, immatriculation, sid);

		piv = PeriodeInsdispoManager.recuperationInstance();

		if (erreurs.isOk()) {
			PeriodeIndisponibiliteVehicule pivtest = null;
			try {
				pivtest = PeriodeInsdispoManager.get(piv.getIdIndisponibilite());
			} catch (BLLException e) {
				erreurs.addErreur(INDISPO, e.getMessage());
			}
			if (pivtest == null) {
				try {
					PeriodeInsdispoManager.inserer(piv);
				} catch (BLLException e) {
					erreurs.addErreur(INDISPO, e.getMessage());
				}
			} else {
				try {
					PeriodeInsdispoManager.maj(piv);
				} catch (BLLException e) {
					erreurs.addErreur(INDISPO, e.getMessage());
				}
			}
		}
		request.setAttribute("indispo", piv);

		if (erreurs.isOk()) {
			erreurs.setResultat("Indisponibilité bien enregristrée");
			request.setAttribute("erreurs", erreurs);
			request.getRequestDispatcher(URLNavigation.URL_PAGE_HOME).forward(request, response);
		} else {
			erreurs.setResultat("Enregistrement impossible, un problème a eu lieu");
			request.setAttribute("erreurs", erreurs);
			doGet(request, response);
			return;

		}
	}

	public void verifInfos(String smotif, String sdateDebut, String sdateFin, String immatriculation, String sid) {
		try {
			PeriodeInsdispoManager.verifMotif(smotif);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_MOTIF, e.getMessage());
		}
		try {
			PeriodeInsdispoManager.verifDateDebut(sdateDebut);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_DATE_DEBUT, e.getMessage());
		}
		try {
			PeriodeInsdispoManager.verifDateFin(sdateFin);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_DATE_FIN, e.getMessage());
		}
		try {
			PeriodeInsdispoManager.verifDate();
		} catch (Exception e) {
			erreurs.addErreur(COMPARE_DATE, e.getMessage());
		}
		try {
			PeriodeInsdispoManager.verifVehicule(immatriculation);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_IMMATRICULATION, e.getMessage());
		}
		try {
			PeriodeInsdispoManager.verifChevauchementModif(Integer.parseInt(sid));
		} catch (Exception e) {
			erreurs.addErreur(CHEVAUCHEMENT, e.getMessage());
		}
	}
}
