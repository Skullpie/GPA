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
 * Servlet implementation class SupprimerUtilisateurServlet
 */
@WebServlet(name = "SupprimerUtilisateurServlet", urlPatterns = { "/auth/supprimerutilisateur" })
public class SupprimerUtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	ErrorContainer erreurs = new ErrorContainer();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerUtilisateurServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur u = null;
		try {
			u = UtilisateurManager.getById(Integer.parseInt(request.getParameter("id")));
		} catch (NumberFormatException e) {
			erreurs.addErreur("SystemError", e.getMessage());
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		try {
			UtilisateurManager.supprimerUtilisateur(u);
		} catch (BLLException e) {
			erreurs.addErreur("system", e.getMessage());
		}
		if (erreurs.isOk()) {
			erreurs.setResultat("Utilisateur bien supprimé");
			request.setAttribute("erreurs", erreurs);
			request.getRequestDispatcher(URLNavigation.URL_PAGE_HOME).forward(request, response);
		} else {
			erreurs.setResultat("Suppression impossible, un problème est survenu");
			request.setAttribute("erreurs", erreurs);
			request.getRequestDispatcher(URLNavigation.URL_PAGE_HOME).forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
