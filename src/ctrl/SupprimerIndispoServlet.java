package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.PeriodeInsdispoManager;
import bo.PeriodeIndisponibiliteVehicule;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class SupprimerIndispoServlet
 */
@WebServlet(name = "SupprimerIndispoServlet", urlPatterns = { "/auth/supprimerIndispo" })
public class SupprimerIndispoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String INDISPO = "indispo";

	ErrorContainer erreurs = new ErrorContainer();
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SupprimerIndispoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
			PeriodeInsdispoManager.supprimer(piv);
			if (erreurs.isOk()) {
				erreurs.setResultat("Indisponibilité bien supprimée");
				request.setAttribute("erreurs", erreurs);
				request.getRequestDispatcher(URLNavigation.URL_PAGE_HOME).forward(request, response);
			} else {
				erreurs.setResultat("Suppression impossible, un problème a eu lieu");
				request.setAttribute("erreurs", erreurs);
				request.getRequestDispatcher(URLNavigation.URL_PAGE_HOME).forward(request, response);
				return;
			}
		} catch (BLLException e) {
			erreurs.addErreur(INDISPO, e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
