package ctrl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class DetailReservation
 */
@WebServlet(name = "DetailReservation", urlPatterns = "/auth/detailReservation")
public class DetailReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	/*
	 * instanciation d'une map d'erreurs
	 */
	ErrorContainer erreurs = new ErrorContainer();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailReservationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Reservation resa = null;
		erreurs.clear();
		try {
			resa = ReservationManager.getById(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			erreurs.addErreur("SystemError", e.getMessage());
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		request.setAttribute("resa", resa);
		
		RequestDispatcher rd = request.getRequestDispatcher(URLNavigation.URL_JSP_DETAIL_RESERVATION);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
