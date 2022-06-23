package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.LieuManager;
import bo.Lieu;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class ModifLieuServlet
 */
@WebServlet(name = "ModifLieuServlet", urlPatterns = "/auth/modiflieu")
public class ModifLieuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final String CHAMP_ID = "id";
	private final String CHAMP_INTITULE = "intitule";
	private final String CHAMP_RUE = "rue";
	private final String CHAMP_CODE_POSTAL = "codePostal";
	private final String CHAMP_VILLE = "ville";
	private final String CHAMP_INFORMATION_COMPLEMENTAIRE = "informationComplementaire";
	private final String CHAMP_LATITUDE = "latitude";
	private final String CHAMP_LONGITUDE = "longitude";
	private final String LIEU = "lieu";
	
	ErrorContainer erreurs = new ErrorContainer();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifLieuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Lieu lieu = null;
		try {
			lieu = LieuManager.getLieu(Integer.parseInt(request.getParameter("lieu")));
		} catch (NumberFormatException e) {
			erreurs.addErreur("SystemError", e.getMessage());
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		request.setAttribute("lieu", lieu);
		
		request.getRequestDispatcher(URLNavigation.URL_JSP_MODIF_LIEU).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		erreurs.clear();
		Lieu l = null;
		String id = request.getParameter(CHAMP_ID);
		String intitule = request.getParameter(CHAMP_INTITULE);
		String rue = request.getParameter(CHAMP_RUE);
		String codePostal = request.getParameter(CHAMP_CODE_POSTAL);
		String ville = request.getParameter(CHAMP_VILLE);
		String informationComplementaire = request.getParameter(CHAMP_INFORMATION_COMPLEMENTAIRE);
		String latitude = request.getParameter(CHAMP_LATITUDE);
		String longitude = request.getParameter(CHAMP_LONGITUDE);
		
		LieuManager.init();
		verifInfos(intitule, rue, codePostal, ville, informationComplementaire, latitude, longitude, id);
		
		l=LieuManager.recuperationInstance();
		if (erreurs.isOk()) {
			try {
				LieuManager.majLieu(l);
			} catch (BLLException e) {
				erreurs.addErreur(LIEU, e.getMessage());
			}
		}

		request.setAttribute("lieu", l);

		if (erreurs.isOk()) {
			erreurs.setResultat("Lieu bien modifié");
			request.setAttribute("erreurs", erreurs);
			request.getRequestDispatcher(URLNavigation.URL_PAGE_HOME).forward(request, response);
		} else {
			erreurs.setResultat("Modification impossible, un problème a eu lieu");
			request.setAttribute("erreurs", erreurs);
			doGet(request, response);
			return;
		}
		
	}
	private void verifInfos(String intitule, String rue, String codePostal, String ville, String informationComplementaire, String latitude, String longitude, String id) {
		try {
			LieuManager.verifIntitule(intitule);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_INTITULE, e.getMessage());
		}
		try {
			LieuManager.verifRue(rue);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_RUE, e.getMessage());
		}
		try {
			LieuManager.verifCodePostal(codePostal);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_CODE_POSTAL, e.getMessage());
		}
		try {
			LieuManager.verifVille(ville);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_VILLE, e.getMessage());
		}
		try {
			LieuManager.verifInformationComplementaire(informationComplementaire);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_INFORMATION_COMPLEMENTAIRE, e.getMessage());
		}
		try {
			LieuManager.verifLatitude(latitude);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_LATITUDE, e.getMessage());
		}
		try {
			LieuManager.verifLongitude(longitude);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_LONGITUDE, e.getMessage());
		}		
		try {
			LieuManager.verifId(id);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_ID, e.getMessage());
		}
	}
}
