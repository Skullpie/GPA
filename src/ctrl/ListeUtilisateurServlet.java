package ctrl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.UtilisateurManager;
import bo.Utilisateur;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class ListeUtilisateurServlet
 */
@WebServlet(name = "ListeUtilisateurServlet", urlPatterns = { "/auth/listeutilisateur" })
public class ListeUtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	ErrorContainer erreurs = new ErrorContainer();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListeUtilisateurServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Utilisateur> utilisateurs = null;
		
		try {
			utilisateurs = UtilisateurManager.getAll();
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		request.setAttribute("utilisateurs", utilisateurs);
		
		request.getRequestDispatcher(URLNavigation.URL_JSP_LISTE_UTILISATEUR).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
