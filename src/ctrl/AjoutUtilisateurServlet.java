package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.UtilisateurManager;
import bo.Utilisateur;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class AjoutUtilisateurServlet
 */
@WebServlet(name = "AjoutUtilisateurServlet", urlPatterns = { "/auth/ajoututilisateur" })
public class AjoutUtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String CHAMP_PSEUDO = "pseudo";
	private final String CHAMP_EMAIL = "email";
	private final String CHAMP_MOT_DE_PASSE = "motDePasse";
	private final String CHAMP_ROLE = "role";
	private final String UTILISATEUR = "utilisateur";
	
	ErrorContainer erreurs = new ErrorContainer();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjoutUtilisateurServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(URLNavigation.URL_JSP_AJOUT_UTILISATEUR).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		erreurs.clear();
		Utilisateur u = null;
		String pseudo = request.getParameter(CHAMP_PSEUDO);
		String email = request.getParameter(CHAMP_EMAIL);
		String motDePasse = request.getParameter(CHAMP_MOT_DE_PASSE);
		String role = request.getParameter(CHAMP_ROLE);
		
		UtilisateurManager.init();
		
		verifInfos(pseudo, email, motDePasse, role);
		
		u=UtilisateurManager.recuperationInstance();
		if (erreurs.isOk()) {
			try {
				UtilisateurManager.insererUtilisateur(u);
			} catch (BLLException e) {
				erreurs.addErreur(UTILISATEUR, e.getMessage());
			}
		}

		request.setAttribute("utilisateur", u);

		if (erreurs.isOk()) {
			erreurs.setResultat("Utilisateur bien enregistré");
			request.setAttribute("erreurs", erreurs);
			request.getRequestDispatcher(URLNavigation.URL_PAGE_HOME).forward(request, response);
		} else {
			erreurs.setResultat("Enregistrement impossible, un problème a eu lieu");
			request.setAttribute("erreurs", erreurs);
			doGet(request, response);
			return;
		}
	}
	private void verifInfos(String pseudo, String email, String motDePasse, String role) {
		try {
			UtilisateurManager.verifPseudo(pseudo);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_PSEUDO, e.getMessage());
		}
		try {
			UtilisateurManager.verifEmail(email);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_EMAIL, e.getMessage());
		}
		try {
			UtilisateurManager.verifMotDePasse(motDePasse);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_MOT_DE_PASSE, e.getMessage());
		}
		try {
			UtilisateurManager.verifRole(role);
		} catch (Exception e) {
			erreurs.addErreur(CHAMP_ROLE, e.getMessage());
		}
	}

}
