package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.ReservataireManager;
import bo.Reservataire;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class SupprimerReservataireServlet
 */
@WebServlet(name = "SupprimerReservataireServlet", urlPatterns = "/auth/supprimerReservataire")
public class SupprimerReservataireServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String CHAMP_SYSTEM = "system";
	
	/*
	 * instanciation d'une map d'erreurs
	 */
	ErrorContainer erreurs = new ErrorContainer();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerReservataireServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Reservataire r = null;
		erreurs.clear();
		try {
			r = ReservataireManager.getById(Integer.parseInt(request.getParameter("reservataire")));
		} catch (NumberFormatException e) {
			erreurs.addErreur("SystemError", e.getMessage());
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		try {
			ReservataireManager.supprimer(r);
		} catch (BLLException e) {
			erreurs.addErreur(CHAMP_SYSTEM, e.getMessage());
		}
		if (erreurs.isOk()) {
			erreurs.setResultat("Réservataire bien supprimée");
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
