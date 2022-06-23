package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.LieuManager;
import bo.Lieu;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class DetailLieuServlet
 */
@WebServlet(name = "DetailLieuServlet", urlPatterns = "/auth/detaillieu")
public class DetailLieuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	ErrorContainer erreurs = new ErrorContainer();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailLieuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sid = request.getParameter("lieu");
		Lieu lieu = null;
		try {
			lieu = LieuManager.getLieu(Integer.parseInt(sid));
		} catch (NumberFormatException e) {
			erreurs.addErreur("SystemError", e.getMessage());
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		request.setAttribute("lieu", lieu);
		request.getRequestDispatcher(URLNavigation.URL_JSP_DETAIL_LIEU).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
