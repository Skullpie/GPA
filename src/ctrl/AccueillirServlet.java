package ctrl;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bll.ReservationManager;
import bo.EvenementFullCalendar;
import bo.Reservataire;
import bo.Reservation;
import exceptions.BLLException;
import exceptions.ErrorContainer;


/**
 * Servlet implementation class AccueillirServlet
 */
@WebServlet(name = "WelcomeServlet", urlPatterns = { "/auth/accueil" })
public class AccueillirServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*
	 * instanciation d'une map d'erreurs
	 */
	ErrorContainer erreurs = new ErrorContainer();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccueillirServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		erreurs.clear();
		List<Reservation> list  = new ArrayList<Reservation>();
		try {
			list = ReservationManager.getall();
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		List<EvenementFullCalendar> reservations = new ArrayList<EvenementFullCalendar>();
		for (Reservation reservation : list) {
			StringBuffer sb = new StringBuffer();
			sb.append(reservation.getVehicule().getImmatriculation());
			sb.append(" par ");
			int index = 1;
			for (Reservataire reservataire : reservation.getReservataires()) {
				sb.append(reservataire.getPrenom());
				if (reservation.getReservataires().size()>1 && index < reservation.getReservataires().size()) {
					sb.append(", ");
				}
				index ++;
			}
			
			EvenementFullCalendar e = new EvenementFullCalendar(reservation.getDateDebut().toString(), reservation.getDateFin().toString(), sb.toString(), request.getContextPath()+"/auth/detailReservation?id="+reservation.getId());
			reservations.add(e);
		}
		Gson gson = new Gson();
		request.setAttribute("reservationsJSON", gson.toJson(reservations));

		RequestDispatcher rd = request.getRequestDispatcher(URLNavigation.URL_JSP_HOME);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		//récupérer l'utilisateur connecté en session
//		HttpSession session = request.getSession();	
//		Utilisateur u = (Utilisateur) session.getAttribute("user_inc");
		
		doGet(request, response);
		
	}

}
