package ctrl.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bll.VehiculeManager;
import bo.Vehicule;
import exceptions.BLLException;
import exceptions.ErrorContainer;

/**
 * Servlet implementation class ValiderDisponibilite
 */

@WebServlet(name = "ValiderDisponibilite", urlPatterns = { "/auth/validerDisponibilite" })
public class ValiderDisponibilite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	ErrorContainer erreurs = new ErrorContainer();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValiderDisponibilite() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dateDebut = request.getParameter("dateDebut");
		String dateFin = request.getParameter("dateFin");
		List<Vehicule> fluxJson = null;
		try {
			fluxJson = VehiculeManager.getVehiculeDispo(dateDebut, dateFin);
		} catch (BLLException e) {
			erreurs.addErreur("SystemError", e.getMessages().get(0));
		}
		Gson gson = new Gson();
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(gson.toJson(fluxJson));
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
