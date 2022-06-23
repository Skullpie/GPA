package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.VehiculeManager;
import bo.Vehicule;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class DetailVehiculeServlet
 */
@WebServlet(name = "DetailVehiculeServlet", urlPatterns = "/auth/detailvehicule")
public class DetailVehiculeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ErrorContainer erreurs = new ErrorContainer();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailVehiculeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String immat = request.getParameter("immatriculation");
		Vehicule veh = null;
		try {
			veh= VehiculeManager.get(immat);
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		request.setAttribute("veh", veh);
		request.getRequestDispatcher(URLNavigation.URL_JSP_DETAIL_VEHICULE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
