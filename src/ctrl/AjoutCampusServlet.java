package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.CampusManager;
import bo.Campus;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class AjoutCampusServlet
 */
@WebServlet(name = "AjoutCampusServlet", urlPatterns = { "/auth/ajoutcampus" })
public class AjoutCampusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final String CHAMP_INTITULE = "intitule";
	private final String CHAMP_RUE = "rue";
	private final String CHAMP_CODE_POSTAL = "codePostal";
	private final String CHAMP_VILLE = "ville";
	private final String CHAMP_INFORMATION_COMPLEMENTAIRE = "informationComplementaire";
	private final String CHAMP_LATITUDE = "latitude";
	private final String CHAMP_LONGITUDE = "longitude";
	private final String CHAMP_CODE = "code";
	private final String CAMPUS = "campus";
       
	ErrorContainer erreurs = new ErrorContainer();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjoutCampusServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	

		request.getRequestDispatcher(URLNavigation.URL_JSP_AJOUT_CAMPUS).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		erreurs.clear();
		Campus c = null;
		String intitule = request.getParameter(CHAMP_INTITULE);
		String rue = request.getParameter(CHAMP_RUE);
		String codePostal = request.getParameter(CHAMP_CODE_POSTAL);
		String ville = request.getParameter(CHAMP_VILLE);
		String informationComplementaire = request.getParameter(CHAMP_INFORMATION_COMPLEMENTAIRE);
		String latitude = request.getParameter(CHAMP_LATITUDE);
		String longitude = request.getParameter(CHAMP_LONGITUDE);
		String code = request.getParameter(CHAMP_CODE);
		
		CampusManager.init();
		
		verifInfos(intitule, rue, codePostal, ville, informationComplementaire, latitude, longitude, code);
		
		c=CampusManager.recuperationInstance();
		if (erreurs.isOk()) {
			try {
				CampusManager.insertCampus(c);
			} catch (BLLException e) {
				erreurs.addErreur(CAMPUS, e.getMessage());
			}
		}

		request.setAttribute("lieu", c);

		if (erreurs.isOk()) {
			erreurs.setResultat("Lieu bien enregistré");
			request.setAttribute("erreurs", erreurs);
			request.getRequestDispatcher(URLNavigation.URL_PAGE_HOME).forward(request, response);
		} else {
			erreurs.setResultat("Enregistrement impossible, un problème a eu lieu");
			request.setAttribute("erreurs", erreurs);
			doGet(request, response);
			return;
		}
	}

	private void verifInfos(String intitule, String rue, String codePostal, String ville, String informationComplementaire, String latitude, String longitude, String code) {
		try {
			CampusManager.verifIntitule(intitule);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_INTITULE, e.getMessage());
		}
		try {
			CampusManager.verifRue(rue);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_RUE, e.getMessage());
		}
		try {
			CampusManager.verifCodePostal(codePostal);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_CODE_POSTAL, e.getMessage());
		}
		try {
			CampusManager.verifVille(ville);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_VILLE, e.getMessage());
		}
		try {
			CampusManager.verifInformationComplementaire(informationComplementaire);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_INFORMATION_COMPLEMENTAIRE, e.getMessage());
		}
		try {
			CampusManager.verifLatitude(latitude);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_LATITUDE, e.getMessage());
		}
		try {
			CampusManager.verifLongitude(longitude);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_LONGITUDE, e.getMessage());
		}	
		try {
			CampusManager.verifCode(code);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_CODE, e.getMessage());
		}
	}

}
