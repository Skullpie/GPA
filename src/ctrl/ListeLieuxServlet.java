package ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.CampusManager;
import bll.LieuManager;
import bo.Campus;
import bo.Lieu;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class ListeLieuxServlet
 */
@WebServlet(name = "ListeLieuxServlet", urlPatterns = { "/auth/listelieux" })
public class ListeLieuxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ErrorContainer erreurs = new ErrorContainer();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListeLieuxServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Campus> liste1 = new ArrayList<Campus>();
		List<Lieu> liste2 = new ArrayList<Lieu>();
		try {
			liste1 = CampusManager.getall();
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		try {
			liste2 = LieuManager.getall();
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}

		request.setAttribute("lieux", liste2);
		request.setAttribute("campus", liste1);
		
		request.getRequestDispatcher(URLNavigation.URL_JSP_LISTE_LIEUX).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
