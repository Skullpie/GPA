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

/**
 * Servlet implementation class SupprimerReintegrerVehicule
 */
@WebServlet(name = "SupprimerReintegrerVehicule", urlPatterns = { "/auth/SupprimerReintegrerVehicule" })
public class SupprimerReintegrerVehicule extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerReintegrerVehicule() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Vehicule veh = null;
		String action = request.getParameter("action");
		try {
			
			veh = VehiculeManager.get(request.getParameter("vehexist"));
		} catch (BLLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (veh != null) {
			switch (action) {
			case "su":
				try {
					VehiculeManager.delete(veh);
				} catch (BLLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "re":
				try {
					VehiculeManager.reinstate(veh);
				} catch (BLLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		request.getRequestDispatcher(URLNavigation.URL_PAGE_LISTE_VEH).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
