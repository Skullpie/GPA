package ctrl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bll.UtilisateurManager;
import bo.Utilisateur;
import exceptions.BLLException;
import exceptions.ExceptionsContainer;



/**
 * Servlet implementation class ConnecterServlet
 */
@WebServlet(name = "ConnecterServlet", urlPatterns = { "/connexion" })
public class ConnecterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnecterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(URLNavigation.URL_JSP_LOGIN).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pseudo = request.getParameter("pseudo");
		String motDePasse = request.getParameter("password");
		Utilisateur u = null;
		
		ExceptionsContainer errors = new ExceptionsContainer(); //initier le conteneur des erreurs pour le traitement
		
		if(!pseudo.isEmpty() && !motDePasse.isEmpty()) {
			try {
				u = UtilisateurManager.authenticate(pseudo, motDePasse);	
			} catch (BLLException e) {
				e.printStackTrace();
				errors.addMessage(e.getMessages()); //collecter les erreurs
			}
		}
		
		//Test retour du manager
		if(u == null) {
			//le traitement n'a pas trouvé d'utilisateur correspondant au pseudo, password
			errors.addMessage("Ce compte utilisateur est inconnu !");
		}
		else {
			//Détruire la session éventuellement existante
			HttpSession session = request.getSession();
			session.removeAttribute("user_inc");
			session.invalidate();
			session = request.getSession();
			session.setAttribute("user_inc", u);
			//Si l'utilisateur à coché "se souvenir de moi"
			if (request.getParameter("remember")!=null) {
				String cookieNamePseudo = "rememberPseudo";
				String cookieValPseudo = pseudo;
				String cookieNamePwd = "rememberPwd";
				String cookieValPwd = motDePasse;
				
				Cookie cPseudo = new Cookie(cookieNamePseudo, cookieValPseudo);
				cPseudo.setMaxAge(300);	//durée de vie de 5 minutes
				cPseudo.setHttpOnly(true);
				response.addCookie(cPseudo);
				Cookie cPwd = new Cookie(cookieNamePwd, cookieValPwd);
				cPwd.setMaxAge(300);	//durée de vie de 5 minutes
				cPwd.setHttpOnly(true);
				response.addCookie(cPwd);
			}
		}
		
		if(errors.hasErrors()) {
			request.setAttribute("errors", errors.getErrorMessages()); //les erreurs collectées			
			doGet(request, response);
			return;
		}
		
		response.sendRedirect(request.getContextPath() + URLNavigation.URL_PAGE_HOME);
	}

}
