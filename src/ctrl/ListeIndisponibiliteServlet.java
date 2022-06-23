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

import bll.PeriodeInsdispoManager;
import bo.EvenementFullCalendar;
import bo.PeriodeIndisponibiliteVehicule;
import exceptions.BLLException;

/**
 * Servlet implementation class ListeIndisponibiliteServlet
 */
@WebServlet(name = "ListeIndisponibiliteServlet", urlPatterns = { "/auth/gestionindispo" })
public class ListeIndisponibiliteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListeIndisponibiliteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<PeriodeIndisponibiliteVehicule> lst = null;
		
		try {
			lst = PeriodeInsdispoManager.getAll();
		} catch (BLLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<EvenementFullCalendar> reservations = new ArrayList<EvenementFullCalendar>();
		for (PeriodeIndisponibiliteVehicule p : lst) {
			StringBuffer sb = new StringBuffer();
			sb.append(p.getVehicule().getImmatriculation());
			sb.append(" pour ");
			sb.append(p.getMotifIndisponibilite().getIntitule());
			
			EvenementFullCalendar e = new EvenementFullCalendar(p.getDateDebut().toString(), p.getDateFin().toString(), sb.toString(), request.getContextPath()+"/auth/modifierIndispo?id="+p.getIdIndisponibilite());
			reservations.add(e);
		}
		Gson gson = new Gson();
		request.setAttribute("reservationsJSON", gson.toJson(reservations));

		RequestDispatcher rd = request.getRequestDispatcher(URLNavigation.URL_JSP_DETAIL_INDISPO);
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
