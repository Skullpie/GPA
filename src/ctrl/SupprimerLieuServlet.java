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
 * Servlet implementation class SupprimerLieuServlet
 */

@WebServlet(name = "SupprimerLieuServlet", urlPatterns = { "/auth/supprimerlieu" })
public class SupprimerLieuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String CHAMP_SYSTEM = "system";
       
	ErrorContainer erreurs = new ErrorContainer();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerLieuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Lieu l = null;
		
		try {
			l = LieuManager.getLieu(Integer.parseInt(request.getParameter("lieu")));
		} catch (NumberFormatException e) {
			erreurs.addErreur("CHAMP_SYSTEM", e.getMessage());
		} catch (BLLException e) {
			erreurs.addErreur("CHAMP_SYSTEM", e.getMessages().get(0));
		}
		
		try {
			LieuManager.supprimerLieu(l);
		} catch (BLLException e) {
			erreurs.addErreur(CHAMP_SYSTEM, e.getMessage());
		}
		if (erreurs.isOk()) {
			erreurs.setResultat("Lieu bien supprimée");
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
