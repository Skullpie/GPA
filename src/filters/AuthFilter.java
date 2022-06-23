package filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bo.Utilisateur;



/**
 * Servlet Filter implementation class EncheresFilter
 * Point de sécurité :
 * N'autorise l'accès aux ressources de type /enchere/* que si l'utilisateur est connecté.
 * Si l'utilisateur n'est pas connecté, le filtre établit une redirection vers la page d'accueil du site.
 */
@WebFilter(dispatcherTypes = {
		DispatcherType.REQUEST, 
		DispatcherType.FORWARD, 
		DispatcherType.INCLUDE
		}, urlPatterns = { "/auth/*" })
public class AuthFilter implements Filter {
	private static List<String> pageAdmin;

    /**
     * Default constructor. 
     */
    public AuthFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
				
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		HttpSession session = request.getSession();
		Utilisateur u = (Utilisateur) session.getAttribute("user_inc");
		
		if(u == null) {
			//redirection accueil
			response.sendRedirect(request.getContextPath() + "/");
		}
		else {
			if (pageAdmin.contains(request.getServletPath()) && !u.getRole().equalsIgnoreCase("admin")) {
				response.sendRedirect(request.getContextPath() + "/");
			}else {
				chain.doFilter(req, res);
			}
			
		}	
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		pageAdmin = new ArrayList<String>();
		pageAdmin.add("/auth/ajoutlieu");
		pageAdmin.add("/auth/detailreservataire");
		pageAdmin.add("/auth/ajoutreservataire");
		pageAdmin.add("/auth/listereservataire");
		pageAdmin.add("/auth/ajoutcampus");
		pageAdmin.add("/auth/ajoututilisateur");
		pageAdmin.add("/auth/listeutilisateur");
		
		
	}

}
