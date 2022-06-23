package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.ReservationManager;
import bo.Reservation;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class SupprimerReservation
 */
@WebServlet(name = "SupprimerReservation", urlPatterns = "/auth/supprimerReservation")
public class SupprimerReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String CHAMP_SYSTEM = "system";
	
	/*
	 * instanciation d'une map d'erreurs
	 */
	ErrorContainer erreurs = new ErrorContainer();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerReservationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Reservation r = null;
		erreurs.clear();
		try {
			r = ReservationManager.getById(Integer.parseInt(request.getParameter("id")));
		} catch (NumberFormatException e1) {
			erreurs.addErreur(CHAMP_SYSTEM, e1.getMessage());
		} catch (BLLException e) {
			erreurs.addErreur(CHAMP_SYSTEM, e.getMessages().get(0));
		}
		
		try {
			ReservationManager.supprimerReservation(r);
		} catch (BLLException e) {
			erreurs.addErreur(CHAMP_SYSTEM, e.getMessage());
		}
		if (erreurs.isOk()) {
			erreurs.setResultat("Réservation bien supprimée");
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
