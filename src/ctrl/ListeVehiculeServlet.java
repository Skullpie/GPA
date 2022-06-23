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

import bll.VehiculeManager;
import bo.Vehicule;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class ListeVehiculeServlet
 */
@WebServlet(name = "ListeVehiculeServlet", urlPatterns = { "/auth/listevehicule" })
public class ListeVehiculeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	/*
	 * instanciation d'une map d'erreurs
	 */
	ErrorContainer erreurs = new ErrorContainer();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListeVehiculeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Vehicule> vehs = new ArrayList<Vehicule>();
		
		erreurs.clear();
		
		try {
			vehs = VehiculeManager.getall();
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		request.setAttribute("vehs", vehs);
		
		RequestDispatcher rd = request.getRequestDispatcher(URLNavigation.URL_JSP_LISTE_VEH);
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
