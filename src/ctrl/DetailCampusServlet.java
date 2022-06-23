package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.CampusManager;
import bo.Campus;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class DetailCampusServlet
 */
@WebServlet(name = "DetailCampusServlet", urlPatterns = "/auth/detailcampus")
public class DetailCampusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	ErrorContainer erreurs = new ErrorContainer();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailCampusServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sid = request.getParameter("lieu");
		Campus lieu = null;
		try {
			lieu = CampusManager.get(Integer.parseInt(sid));
		} catch (NumberFormatException e) {
			erreurs.addErreur("SystemError", e.getMessage());
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		request.setAttribute("lieu", lieu);
		request.getRequestDispatcher(URLNavigation.URL_JSP_DETAIL_CAMPUS).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
