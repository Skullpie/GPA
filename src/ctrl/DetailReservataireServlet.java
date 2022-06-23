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
 * Servlet implementation class DetailReservataireServlet
 */
@WebServlet(name = "DetailReservataireServlet", urlPatterns = "/auth/detailreservataire")
public class DetailReservataireServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	ErrorContainer erreurs = new ErrorContainer();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailReservataireServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("reservataire");
		
		Reservataire res = null;
		erreurs.clear();
		try {
			res = ReservataireManager.getById(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			erreurs.addErreur("SystemError", e.getMessage());
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		request.setAttribute("res", res);
		
		request.getRequestDispatcher(URLNavigation.URL_JSP_DETAIL_RESERVATAIRE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
