package ctrl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.LieuManager;
import bll.ReservataireManager;
import bo.Lieu;
import bo.Reservataire;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class ModifReservataireServlet
 */
@WebServlet(name = "ModifReservataireServlet", urlPatterns = "/auth/modificationreservataire")
public class ModifReservataireServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String CHAMP_ID = "id";
	private final String CHAMP_NOM = "nom";
	private final String CHAMP_PRENOM = "prenom";
	private final String CHAMP_EMAIL = "email";
	private final String CHAMP_TELEPHONE = "telephone";
	private final String CHAMP_RUE = "rue";
	private final String CHAMP_CODE_POSTAL = "codePostal";
	private final String CHAMP_VILLE = "ville";
	private final String CHAMP_LIEU = "idLieu";
	private final String CHAMP_SYSTEM = "system";
       
	/*
	 * instanciation d'une map d'erreurs
	 */
	ErrorContainer erreurs = new ErrorContainer();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifReservataireServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Lieu> lieux = null;
		Reservataire res = null;
		try {
			lieux = LieuManager.getall();
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		try {
			res = ReservataireManager.getById(Integer.parseInt(request.getParameter("reservataire")));
		} catch (NumberFormatException e) {
			erreurs.addErreur("SystemError", e.getMessage());
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		request.setAttribute("lieux", lieux);
		request.setAttribute("res", res);
		
		request.getRequestDispatcher(URLNavigation.URL_JSP_MODIF_RESERVATAIRE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Reservataire reservataire = null;
		
		String sid = request.getParameter(CHAMP_ID);
		String snom = request.getParameter(CHAMP_NOM);
		String sprenom = request.getParameter(CHAMP_PRENOM);
		String semail = request.getParameter(CHAMP_EMAIL);
		String stelephone = request.getParameter(CHAMP_TELEPHONE);
		String srue = request.getParameter(CHAMP_RUE);
		String scodePostal = request.getParameter(CHAMP_CODE_POSTAL);
		String sville = request.getParameter(CHAMP_VILLE);
		String slieu = request.getParameter(CHAMP_LIEU);
		
		erreurs.clear();
		
ReservataireManager.init();
		
		verifInfos(snom, sprenom, semail, stelephone, srue, scodePostal, sville, slieu, sid);
		
		reservataire = ReservataireManager.recuperationInstance();
		
		if (erreurs.isOk()) {
			try {
				ReservataireManager.modifier(reservataire);
			} catch (BLLException e) {
				erreurs.addErreur(CHAMP_SYSTEM, e.getMessage());
			}
		}
		request.setAttribute("res", reservataire);

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
	private void verifInfos(String snom, String sprenom, String semail, String stelephone, String srue, String scodePostal, String sville, String slieu, String sid) {
		try {
			ReservataireManager.verifNom(snom);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_NOM, e.getMessage());
		}
		try {
			ReservataireManager.verifPrenom(sprenom);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_PRENOM, e.getMessage());
		}
		try {
			ReservataireManager.verifEmail(semail);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_EMAIL, e.getMessage());
		}
		try {
			ReservataireManager.verifTelephone(stelephone);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_TELEPHONE, e.getMessage());
		}
		try {
			ReservataireManager.verifRue(srue);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_RUE, e.getMessage());
		}
		try {
			ReservataireManager.verifCodePostal(scodePostal);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_CODE_POSTAL, e.getMessage());
		}
		try {
			ReservataireManager.verifVille(sville);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_VILLE, e.getMessage());
		}
		try {
			ReservataireManager.verifLieux(slieu);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_LIEU, e.getMessage());
		}
		try {
			ReservataireManager.verifId(sid);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_ID, e.getMessage());
		}
	}

}
