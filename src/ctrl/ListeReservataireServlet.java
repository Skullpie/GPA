package ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
 * Servlet implementation class ListeReservataireServlet
 */
@WebServlet(name = "ListeReservataireServlet", urlPatterns = { "/auth/listereservataire" })
public class ListeReservataireServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*
	 * instanciation d'une map d'erreurs
	 */
	ErrorContainer erreurs = new ErrorContainer();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListeReservataireServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Reservataire> reservataires = new ArrayList<Reservataire>();
		erreurs.clear();
		try {
			reservataires = ReservataireManager.getall();
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		request.setAttribute("reservataires", reservataires);
		
		request.getRequestDispatcher(URLNavigation.URL_JSP_LISTE_RESERVATAIRES).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
